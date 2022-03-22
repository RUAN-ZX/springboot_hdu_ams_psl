package cn.ryanalexander.alibaba.service.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

// 过去几年等级录入
@Data
public class AchievementYearsEntity {


    // 可以这里匹配到电子信息学院 就往数据库中存1 这样的代号就完事了！！ 数据库不存重复内容
    @ExcelProperty(value = "工号")
    private String aytid;


    @ExcelProperty(value = "2018-2019")
    private String aygrade2018;

    @ExcelProperty(value = "2017-2018")
    private String aygrade2017;

    @ExcelProperty(value = "2017-2016")
    private String aygrade2016;

    @ExcelProperty(value = "2015-2016")
    private String aygrade2015;


    @ExcelProperty(value = "2014-2015")
    private String aygrade2014;

    @ExcelProperty(value = "2013-2014")
    private String aygrade2013;

    @ExcelProperty(value = "2012-2013")
    private String aygrade2012;

    @ExcelProperty(value = "2011-2012")
    private String aygrade2011;

    @ExcelProperty(value = "2010-2009")
    private String aygrade2009;

}
