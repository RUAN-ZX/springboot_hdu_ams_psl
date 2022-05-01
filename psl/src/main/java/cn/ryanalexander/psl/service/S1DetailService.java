package cn.ryanalexander.psl.service;

import cn.ryanalexander.psl.domain.po.S1DetailPO;
import cn.ryanalexander.psl.domain.po.SDetailPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【s1_detail】的数据库操作Service
* @createDate 2022-04-18 09:58:44
*/
public interface S1DetailService extends IService<S1DetailPO> {
    ArrayList<Integer> getAllYears();
    ArrayList<S1DetailPO> getS1DetailByYear(Integer year);
}
