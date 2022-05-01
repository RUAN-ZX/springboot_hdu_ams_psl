package cn.ryanalexander.auth.mapper;

import cn.ryanalexander.auth.domain.po.AccountPO;
import cn.ryanalexander.common.domain.dto.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【account】的数据库操作Mapper
* @createDate 2022-04-28 12:10:26
* @Entity cn.ryanalexander.auth.domain.po.AccountPO
*/
public interface AccountMapper extends BaseMapper<AccountPO> {
    void saveOrIgnoreBatchByAccount(@Param("items") ArrayList<Account> accounts);

    // 因为对于insert ignore
//    void getIdsByAccount(@Param("items") ArrayList<Account> accounts);
}




