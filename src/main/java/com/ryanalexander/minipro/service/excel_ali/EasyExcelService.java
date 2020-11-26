package com.ryanalexander.minipro.service.excel_ali;

import com.ryanalexander.minipro.dao.ADao;
import com.ryanalexander.minipro.dao.CDao;
import com.ryanalexander.minipro.dao.EDao;
import com.ryanalexander.minipro.dao.TDao;
import com.ryanalexander.minipro.entries.A;
import com.ryanalexander.minipro.entries.C;
import com.ryanalexander.minipro.service.MathService;
import com.ryanalexander.minipro.service.excel_ali.entity.*;
import com.ryanalexander.minipro.service.SpringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EasyExcelService {

    @Autowired
    private TDao tDao;

    @Autowired
    private EDao eDao;

    @Autowired
    private CDao cDao;
    @Autowired
    private ADao aDao;

//    private TDao tDao = (TDao) SpringUtil.getBean("TDao");
//    private EDao eDao = (EDao) SpringUtil.getBean("EDao");
//    private CDao cDao = (CDao) SpringUtil.getBean("CDao");
//    private ADao aDao = (ADao) SpringUtil.getBean("ADao");

    public String CCidTrim(String ccid){
        Pattern r3 = Pattern.compile("^\\((.*?)\\)");

        Matcher m = r3.matcher(ccid);
        if(m.find())
            return m.group(1);
        else {
//            System.out.println("123123123");
            return "";
        }
    }
    public List<String> strTrim (Object o){
        Pattern r1 = Pattern.compile("\\((.*?)\\)$");

        Pattern r2 = Pattern.compile("=(.*)$");

        Matcher m = r1.matcher(o.toString());
        List<String> result = new ArrayList<String>();
        if (m.find()) {
            String temp = m.group(1);
            String[] temp_ = temp.split(",");

            for (String s : temp_) {
                Matcher target = r2.matcher(s);
                if (target.find()) {
                    result.add(target.group(1));
                }
            }
            // 部门	教师职工号	姓名	参评人次	总得分	全校排名	学院排名
//            System.out.println(result);
        }
        return result;
    }
    private void updateTFromOthers(String Tid, String Tname){
        if(tDao.TgetById(Tid)==null){
//                        System.out.println(result.get(5));
//                        System.out.println("112321123123123123");
            List<TeacherEntity_> temp_ = new ArrayList<TeacherEntity_>();
            temp_.add(new TeacherEntity_(
                    Tid,
                    Tname));
            tDao.TinsertByIdName(temp_);
        }
    }
    public void save(List list, String class_) {
        switch (class_){
            case "EmailEntity":{
                System.out.println("EmailEntity");
                for (Object o : list) {
                    List<String> result = strTrim(o);
                    String Tid = result.get(0);
                    String Tname = result.get(1);
                    String Tmail = result.get(2);
                    if(tDao.TgetById(Tid)==null) tDao.TinsertByIdNameEmail(Tid,Tname,Tmail);
                    else tDao.TupdateEmail(Tid,Tmail);
//                    System.out.println(result);
                }
                break;
            }
            case "AchievementEntity":{
                System.out.println("AchievementEntity");
                List<A> list_ = new ArrayList<A>();

                for (Object o : list) {
                    List<String> result = strTrim(o);
                    updateTFromOthers(result.get(0),result.get(6));
                    if(result.get(2).equals("null")) result.set(2,"0");
                    if(result.get(3).equals("null")) result.set(3,"0");
                    if(result.get(5).equals("null")) result.set(5,"无");

                    list_.add(new A(
                       result.get(0),
                        "2018",
                        result.get(1),
                        result.get(2), // null ->0
                        MathService.rounded(result.get(3)),
                        result.get(4),
                        result.get(5)
                    ));
                }
                aDao.Ainsert(list_);
                break;
            }
            case "CourseEntity":{
                System.out.println("CourseEntity");
                List<C> list_ = new ArrayList<C>();
                for (Object o : list) {
                    List<String> result = strTrim(o);
                    updateTFromOthers(result.get(5),result.get(0));
                    list_.add(
                        new C(
                                result.get(1),
                                CCidTrim(result.get(1)),
                                result.get(2),
                                MathService.rounded(result.get(3)),
                                result.get(4),
                                result.get(5),
                                MathService.rounded(result.get(6)),
                                MathService.rounded(result.get(7)),
                                MathService.rounded(result.get(8)),
                                MathService.rounded(result.get(9))
                    ));
                }
//                System.out.println(list_);
                try{
                    cDao.Cinsert(list_);
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            }
            case "EvaluationEntity":{
                System.out.println("EvaluationEntity_");
                List<EvaluationEntity_> list_ = new ArrayList<EvaluationEntity_>();
                for (Object o : list) {
                    List<String> temp_s = strTrim(o);
                        // 部门	教师职工号	姓名	参评人次	总得分	全校排名	学院排名
//                    System.out.println(temp_s);
                    if(temp_s.size()>5){
                        if(tDao.TgetById(temp_s.get(1))==null){
                            List<TeacherEntity_> list1 = new ArrayList<>();
                            list1.add(new TeacherEntity_(temp_s.get(1),temp_s.get(6)));
                            tDao.TinsertByIdName(list1);
                        }
                        list_.add(new EvaluationEntity_(
                                temp_s.get(1),
                                "2019-1",
                                temp_s.get(2),
                                temp_s.get(3),
                                temp_s.get(4),
                                temp_s.get(5)
                        ));
                    }


                    // 每个老师的信息修改 这里补充其部门编号
                    if(temp_s.get(0).equals("电子信息学院（微电子学院）")){
                        try{
                            if(tDao!=null) tDao.TupdateDid("01",temp_s.get(1));

                        }
                        catch (Exception e){
                            System.out.println(e);
                        }
                    }

                }
                try{
                    // 更新学评教明细
                    EDao eDao = (EDao) SpringUtil.getBean("EDao");
                    if(eDao!=null) eDao.Einsert(list_);
                }
                catch (Exception e){

                }
                break;
            }
            case "TeacherEntity":{
                System.out.println("TeacherEntity");
                List<TeacherEntity_> list_ = new ArrayList<TeacherEntity_>();

                for (Object o : list) {
                    List<String> temp_s = strTrim(o);
//                    System.out.println(temp_s);
                    list_.add(new TeacherEntity_(temp_s.get(0), temp_s.get(1)));
                }
                try{
                    if(tDao!=null) tDao.TinsertByIdName(list_);
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            }
            case "AchievementYearsEntity":{
                System.out.println("AchievementYearsEntity");
                List<TeacherEntity_> list_ = new ArrayList<TeacherEntity_>();
                List<String> result = new ArrayList<String>();
                for (Object o : list) {
                    result = strTrim(o);
                }
                break;
//                return list_;

            }
            default:{
                break;
            }
        }
    }


}