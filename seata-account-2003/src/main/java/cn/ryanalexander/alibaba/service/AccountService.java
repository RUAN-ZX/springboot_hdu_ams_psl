package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.dao.AccountDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ClassName: AccountService
 * @Description
 * @Author Ryan
 * @Date 2021/12/12 10:54
 * @Version 1.0.0-Beta
 **/
@Service
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Resource
    AccountDao accountDao;

    /**
     * 扣减账户余额
     */
    public void decrease(Long userId, BigDecimal money) {
        LOGGER.info("------->account-service中扣减账户余额开始");
        accountDao.decrease(userId,money);
        LOGGER.info("------->account-service中扣减账户余额结束");
    }
}
