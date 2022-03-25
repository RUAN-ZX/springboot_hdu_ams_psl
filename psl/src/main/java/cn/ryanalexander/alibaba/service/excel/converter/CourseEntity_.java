package cn.ryanalexander.alibaba.service.excel.converter;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
// 学评教明细表
@Data
public class CourseEntity_ {


    @ExcelProperty(value = "教师职工号")
    private String ctid;


    // 唯一的  所以可以作为course的唯一标识
    @ExcelProperty(value = "选课课号")
    private String cid;

    @ExcelProperty(value = "课程名称")
    private String cname;


    @ExcelProperty(value = "一级指标得分1")
    private String cscore_1;


    @ExcelProperty(value = "一级指标得分2")
    private String cscore_2;


    @ExcelProperty(value = "一级指标得分3")
    private String cscore_3;


    @ExcelProperty(value = "一级指标得分4")
    private String cscore_4;

    @ExcelProperty(value = "参评人数")
    private String cparticipate;

}