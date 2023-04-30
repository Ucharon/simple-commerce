package asia.oxox.charon.simplecommerce.service.impl;

import asia.oxox.charon.simplecommerce.entity.DO.BalanceDetail;
import asia.oxox.charon.simplecommerce.entity.DTO.BalanceDetailPageDto;
import asia.oxox.charon.simplecommerce.entity.VO.PageVo;
import asia.oxox.charon.simplecommerce.mapper.BalanceDetailMapper;
import asia.oxox.charon.simplecommerce.service.BalanceDetailService;
import asia.oxox.charon.simplecommerce.utils.UserHolder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author charon
* @description 针对表【tb_balance_detail】的数据库操作Service实现
* @createDate 2023-04-30 18:11:54
*/
@Service
public class BalanceDetailServiceImpl extends ServiceImpl<BalanceDetailMapper, BalanceDetail>
    implements BalanceDetailService{

    @Override
    public PageVo<?> balanceDetailPage(BalanceDetailPageDto pageDto) {
        Page<BalanceDetail> page = new Page<>(pageDto.getPageNum(), pageDto.getPageSize());
        lambdaQuery()
                .eq(BalanceDetail::getUserId, UserHolder.getUser().getId())
                .eq(Objects.nonNull(pageDto.getStatusEnum()),
                        BalanceDetail::getStatusEnum, pageDto.getStatusEnum())
                .orderByDesc(BalanceDetail::getCreateTime)
                .page(page);

        return new PageVo<>(page.getRecords(), page.getTotal());
    }
}




