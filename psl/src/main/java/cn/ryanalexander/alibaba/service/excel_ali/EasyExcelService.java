package cn.ryanalexander.alibaba.service.excel_ali;


import cn.ryanalexander.alibaba.dao.TeacherDao;
import cn.ryanalexander.alibaba.entity.ShortTerm;
import cn.ryanalexander.alibaba.entity.ThesisDesign;
import cn.ryanalexander.alibaba.entity.Course;
import cn.ryanalexander.alibaba.service.excel_ali.entity.EvaluationEntity_;
import cn.ryanalexander.alibaba.service.excel_ali.entity.TeacherEntity_;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EasyExcelService {

    @Autowired
    private TeacherDao teacherDao;


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
//            System.out.println("123123123");;'/
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
        if(teacherDao.TgetById(Tid)==null){
//                        System.out.println(result.get(5));
//                        System.out.println("112321123123123123");
            List<TeacherEntity_> temp_ = new ArrayList<TeacherEntity_>();
            temp_.add(new TeacherEntity_(
                    Tid,
                    Tname));
            teacherDao.TinsertByIdName(temp_);
        }
    }
    public void save(List list) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println(list.toArray()[0]); // 每一批抽1个
        for(Object o : list){
            Method method = o.getClass().getMethod("transformAndSave");
            method.setAccessible(false);
            System.out.println(method.invoke(o));
        }
        switch ("f"){
            case "CourseTheory1": {
//                System.out.println("CourseTheory");
                List<Course> list_ = new ArrayList<Course>();
                System.out.println(list.toArray()[0]); // 每一批抽1个

                try{
//                    cDao.Cinsert(list_);
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            }
            case "CourseExperiment1": {
//                System.out.println("CourseExperiment");
                List<Course> list_ = new ArrayList<Course>();
//                for (Object o : list) {
//                    List<String> result = strTrim(o);
////                    updateTFromOthers(result.get(5),result.get(0));
//
//                }
                System.out.println(list.toArray()[0]); // 每一批抽1个
                try{
//                    cDao.Cinsert(list_);
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            }
            case "CourseShortTerm1": {
                System.out.println("CourseShortTerm");
                List<ShortTerm> list_ = new ArrayList<ShortTerm>();
                System.out.println(list.toArray()[0]);

                try{
//                    cDao.Cinsert(list_);
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            }
            case "CourseThesisDesign1": {
                System.out.println("CourseThesisDesign");
                List<ThesisDesign> list_ = new ArrayList<ThesisDesign>();
                System.out.println(list.toArray()[0]);
//                System.out.println(list_);
                try{
//                    cDao.Cinsert(list_);
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            }
            case "TidAndEmail1":{
                System.out.println("TidAndEmail");
                System.out.println(list.toArray()[0]);
//                for (Object o : list) {
//                    List<String> result = strTrim(o);
//                    String Tid = result.get(0);
//                    String Tname = result.get(1);
//                    String Tmail = result.get(2);
//                    if(tDao.TgetById(Tid)==null) tDao.TinsertByIdNameEmail(Tid,Tname,Tmail);
//                    else tDao.TupdateEmail(Tid,Tmail);
////                    System.out.println(result);
//                }
                break;
            }
            case "S1PostGraduate1":{
                System.out.println("S1PostGraduate");
                List<ThesisDesign> list_ = new ArrayList<ThesisDesign>();
                System.out.println(list);
                for (Object o : list) {
                    List<String> result = strTrim(o);
                    updateTFromOthers(result.get(0),result.get(1));
                    if(result.get(2).equals("null")) result.set(2,"0");
                    if(result.get(4).equals("null")) result.set(4,"0");
                    if(result.get(6).equals("null")) result.set(6,"无");

//                    list_.add(new ThesisDesign(
//                       result.get(0),
//                        result.get(2),
//                        result.get(3),
//                        MathService.rounded(result.get(4)), // null ->0
//                        result.get(5),
//                        result.get(6),
//                        result.get(7)
//                    ));
                }
//                aDao.Ainsert(list_);
                break;
            }
            case "EvaluationEntity1":{
                System.out.println("EvaluationEntity_");
                List<EvaluationEntity_> list_ = new ArrayList<EvaluationEntity_>();
                for (Object o : list) {
                    List<String> temp_s = strTrim(o);
                        // 部门	教师职工号	姓名	参评人次	总得分	全校排名	学院排名
//                    System.out.println(temp_s);
                    if(temp_s.size()>5){
                        if(teacherDao.TgetById(temp_s.get(1))==null){
                            List<TeacherEntity_> list1 = new ArrayList<>();
                            list1.add(new TeacherEntity_(temp_s.get(1),temp_s.get(6)));
                            teacherDao.TinsertByIdName(list1);
                        }
                        System.out.println(temp_s);
                        list_.add(new EvaluationEntity_(
                                temp_s.get(1),
                                temp_s.get(7),
                                temp_s.get(2),
                                temp_s.get(3),
                                temp_s.get(4),
                                temp_s.get(5)
                        ));
                        System.out.println("111"+list_);
                    }


                    // 每个老师的信息修改 这里补充其部门编号
                    if(temp_s.get(0).equals("电子信息学院（微电子学院）")){
                        try{
                            if(teacherDao !=null) teacherDao.TupdateDid("01",temp_s.get(1));

                        }
                        catch (Exception e){
                            System.out.println(e);
                        }
                    }

                }
                try{
//                    if(eDao!=null) eDao.Einsert(list_);
                }
                catch (Exception e){

                }
                break;
            }
            case "TeacherEntity1":{
                System.out.println("TeacherEntity");
                List<TeacherEntity_> list_ = new ArrayList<TeacherEntity_>();

                for (Object o : list) {
                    List<String> temp_s = strTrim(o);
//                    System.out.println(temp_s);
                    list_.add(new TeacherEntity_(temp_s.get(0), temp_s.get(1)));
                }
                try{
                    if(teacherDao !=null) teacherDao.TinsertByIdName(list_);
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            }
            case "AchievementYearsEntity1":{
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