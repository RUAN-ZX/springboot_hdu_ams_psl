package com.ryanalexander.minipro.service.excel_ali.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TeacherEntity {

    @ExcelProperty(value = "职工号")
    public String tid;
    //草 名字不能首字母大写

    @ExcelProperty(value = "姓名")
    public String tname;

//    private Double doubleData;
}





