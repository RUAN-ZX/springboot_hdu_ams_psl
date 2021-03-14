package com.ryanalexander.minipro.service.excel_ali.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AchievementEntity {

    @ExcelProperty(value = "工号")
    private String atid;

    @ExcelProperty(value = "姓名")
    private String atname;

    @ExcelProperty(value = "承担主讲课程学时数是否不低于64学时")
    private String ahours;

    @ExcelProperty(value = "是否出现教学事故")
    private String aaccident;

    @ExcelProperty(value = "考核分数")
    private String ascore;

    @ExcelProperty(value = "考核等级")
    private String agrade;

    @ExcelProperty(value = "备注")
    private String ainfo;

    @ExcelProperty(value = "学年")
    private String atime;
//    private Double doubleData;
}


