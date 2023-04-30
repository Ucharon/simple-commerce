package asia.oxox.charon.simplecommerce.service;

import asia.oxox.charon.simplecommerce.entity.DO.BalanceDetail;
import asia.oxox.charon.simplecommerce.entity.DTO.BalanceDetailPageDto;
import asia.oxox.charon.simplecommerce.entity.VO.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author charon
* @description 针对表【tb_balance_detail】的数据库操作Service
* @createDate 2023-04-30 18:11:54
*/
public interface BalanceDetailService extends IService<BalanceDetail> {

    PageVo<?> balanceDetailPage(BalanceDetailPageDto balanceDetailDto);
}
