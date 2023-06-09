package asia.oxox.charon.simplecommerce.service.impl;

import asia.oxox.charon.simplecommerce.entity.DO.BalanceDetail;
import asia.oxox.charon.simplecommerce.entity.DO.User;
import asia.oxox.charon.simplecommerce.entity.DTO.UserLoginDto;
import asia.oxox.charon.simplecommerce.entity.VO.UserInfoVo;
import asia.oxox.charon.simplecommerce.entity.mq.BalanceUpdateDto;
import asia.oxox.charon.simplecommerce.enums.OrderStatusEnum;
import asia.oxox.charon.simplecommerce.enums.ResultCodeEnum;
import asia.oxox.charon.simplecommerce.exception.BizException;
import asia.oxox.charon.simplecommerce.mapper.UserMapper;
import asia.oxox.charon.simplecommerce.service.BalanceDetailService;
import asia.oxox.charon.simplecommerce.service.RedisService;
import asia.oxox.charon.simplecommerce.service.UserService;
import asia.oxox.charon.simplecommerce.utils.ArithmeticUtils;
import asia.oxox.charon.simplecommerce.utils.RedisIdGenerator;
import asia.oxox.charon.simplecommerce.utils.UserHolder;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.BeanCopyUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static asia.oxox.charon.simplecommerce.constants.RedisConstants.*;

/**
 * @author charon
 * @description 针对表【sys_user】的数据库操作Service实现
 * @createDate 2023-04-29 22:02:07
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private RedisService redisService;
    private BalanceDetailService balanceDetailService;
    private RedisIdGenerator redisIdGenerator;

    @Override
    public Map<String, String> login(UserLoginDto userDto) {
        //根据用户名查询用户，错误则直接返回错误
        User user = Optional.ofNullable(lambdaQuery()
                .eq(User::getUserName, userDto.getUserName())
                .one()).orElseThrow(() -> new BizException(ResultCodeEnum.LOGIN_ERROR));

        //验证密码
        if (!DigestUtil.md5Hex(userDto.getPassword()).equals(user.getPassword())) {
            throw new BizException(ResultCodeEnum.LOGIN_ERROR);
        }

        //生成token
        String token = UUID.randomUUID().toString(true);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        //将token：id存入redis
        String key = LOGIN_USER_KEY + token;
        redisService.set(key, user.getId(), LOGIN_USER_TTL, TimeUnit.MINUTES);
        return map;
    }

    @Override
    public UserInfoVo getInfo() {
        //封装基本信息
        User user = UserHolder.getUser();
        String key = USER_BALANCE_KEY + user.getId();
        BigDecimal balance = (BigDecimal) redisService.get(key);
        user.setBalance(balance);

        return BeanCopyUtils.copyProperties(user, UserInfoVo.class);
    }

    @Override
    public void updateBalance(BalanceUpdateDto balanceUpdateDto) {
        //1. 扣减余额
        BigDecimal balance = getById(balanceUpdateDto.getUserId()).getBalance();
        lambdaUpdate()
                .eq(User::getId, balanceUpdateDto.getUserId())
                .setSql("balance=balance+" + balanceUpdateDto.getChangeBalance())
                .update();

        //2. 记录余额明细
        Long id = redisIdGenerator.nextId("balance_detail");
        BalanceDetail balanceDetail = BalanceDetail.builder()
                .id(id)
                .userId(balanceUpdateDto.getUserId())
                .orderId(balanceUpdateDto.getOrderId())
                .originalBalance(balance)
                .finalBalance(ArithmeticUtils
                        .add(balance.toString(), balanceUpdateDto.getChangeBalance().toString()))
                .statusEnum(OrderStatusEnum.getByCode(balanceUpdateDto.getStatusEnum()))
                .build();
        //3. 存入数据库
        balanceDetailService.save(balanceDetail);
    }
}




