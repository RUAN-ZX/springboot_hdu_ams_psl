package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.dao.StorageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: StorageService
 * @Description
 * @Author Ryan
 * @Date 2021/12/12 10:49
 * @Version 1.0.0-Beta
 **/

@Service
public class StorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class);

    @Resource
    private StorageDao storageDao;

    /**
     * 扣减库存
     */
    public void decrease(Long productId, Integer count) {
        LOGGER.info("------->storage-service中扣减库存开始");
        storageDao.decrease(productId,count);
        LOGGER.info("------->storage-service中扣减库存结束");
    }
}
