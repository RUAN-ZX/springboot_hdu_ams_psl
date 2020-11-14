package com.ryanalexander.minipro.dao;

import com.ryanalexander.minipro.entries.C;
import com.ryanalexander.minipro.service.excel_ali.entity.TeacherEntity_;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CDao {
    void Cinsert(List<C> list);

    C CGetById(String Cid);
//            String Cid,
//            String Cname,
//            String Cscore,
//            String Cparticipate,
//            String CTid,
//            String Cscore_1,
//            String Cscore_2,
//            String Cscore_3,
//            String Cscore_4


}