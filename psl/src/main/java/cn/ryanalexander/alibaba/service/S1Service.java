package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.domain.po.SDetailPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【s1】的数据库操作Service
* @createDate 2022-04-09 21:48:57
*/
public interface S1Service extends IService<SDetailPO> {
    ArrayList<Integer> getAllYears();
    ArrayList<SDetailPO> getS1ByYear(Integer year);
}
