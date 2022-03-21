package cn.ryanalexander.alibaba;

import cn.ryanalexander.alibaba.enums.ErrorCodeEnum;
import cn.ryanalexander.alibaba.exceptions.AppException;
import cn.ryanalexander.alibaba.service.TService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2022/2/13
 * Author:ybc
 * Description:
 */
@SpringBootTest
public class MyBatisPlusServiceTest {

    @Autowired
    private TService tService;

    private final Logger logger = LoggerFactory.getLogger(MyBatisPlusServiceTest.class);

    @Test
    public void testGetCount(){
        logger.warn("dfgdfg");
        throw new AppException(ErrorCodeEnum.RPC_FAIL, "rpc接口rpcDepot.getDepot()返回信息失败");
    }


}
