package com.ryanalexander.minipro.service.excel_ali.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


// 学评教明细表
@Data
public class CourseEntity {


    @ExcelProperty(value = "姓名")
    private String ctname;

    @ExcelProperty(value = "选课课号")
    private String cid;
    @ExcelProperty(value = "课程名称")
    private String cname;

    @ExcelProperty(value = "总得分")
    private String cscore;
    @ExcelProperty(value = "参评人数")
    private String cparticipate;
    @ExcelProperty(value = "教师职工号")
    private String ctid;


    /**
     * 指标固定 即便不固定 设定一次就可以应用于整个表 因此问题不大
     *
     */
//    @ExcelProperty(value = "一级指标1")
//    private String cevaluation_1;
    @ExcelProperty(value = "一级指标得分1")
    private String cscore_1;
    
//    @ExcelProperty(value = "一级指标2")
//    private String cevaluation_2;
    @ExcelProperty(value = "一级指标得分2")
    private String cscore_2;
    
//    @ExcelProperty(value = "一级指标3")
//    private String cevaluation_3;
    @ExcelProperty(value = "一级指标得分3")
    private String cscore_3;
    
//    @ExcelProperty(value = "一级指标4")
//    private String cevaluation_4;
    @ExcelProperty(value = "一级指标得分4")
    private String cscore_4;
    


//    private Double doubleData;
}




