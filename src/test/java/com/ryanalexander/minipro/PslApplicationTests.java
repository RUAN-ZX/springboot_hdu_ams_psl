package com.ryanalexander.minipro;

import com.ryanalexander.minipro.dao.TDao;
import com.ryanalexander.minipro.service.excel_ali.entity.TeacherEntity_;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class PslApplicationTests {

    @Autowired TDao tDao;
    @Test
    void contextLoads() {
//        System.out.println(jwtService.getToken("123123"));
//
//        System.out.println(jwtService.verifyToken(jwtService.getToken("123123")));
//
        TeacherEntity_ teacherEntity = new TeacherEntity_("00430","24");
        List<TeacherEntity_> list_ = new ArrayList<TeacherEntity_>();
        list_.add(teacherEntity);

//        tDao.Tinsert(list_);
    }

}
