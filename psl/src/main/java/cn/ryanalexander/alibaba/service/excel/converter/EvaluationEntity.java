package cn.ryanalexander.alibaba.service.excel.converter;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EvaluationEntity {


    // 可以这里匹配到电子信息学院 就往数据库中存1 这样的代号就完事了！！ 数据库不存重复内容
    @ExcelProperty(value = "部门")
    private String edname;
    //草 名字不能首字母大写

    @ExcelProperty(value = "教师职工号")
    private String etid;



    @ExcelProperty(value = "参评人次")
    private String eparticipate;

    @ExcelProperty(value = "总得分")
    private String escore;

    @ExcelProperty(value = "全校排名")
    private String esrank;

    @ExcelProperty(value = "学院排名")
    private String eprank;

    @ExcelProperty(value = "姓名")
    private String ename;


    @ExcelProperty(value = "年份")
    private String etime;
//    private Double doubleData;
}

