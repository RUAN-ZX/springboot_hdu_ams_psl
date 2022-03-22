package cn.ryanalexander.alibaba.service.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class TeacherEntity {

    @ExcelProperty(value = "职工号")
    public String tid;
    //草 名字不能首字母大写

    @ExcelProperty(value = "姓名")
    public String tname;

//    private Double doubleData;
}





