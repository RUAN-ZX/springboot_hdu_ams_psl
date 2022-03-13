package cn.ryanalexander.alibaba.service.excel_ali.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EmailEntity {

    @ExcelProperty(value = "职工号")
    public String tid;
    //草 名字不能首字母大写

    @ExcelProperty(value = "姓名")
    public String tname;

    @ExcelProperty(value = "邮箱")
    public String temail;

//    private Double doubleData;
}
