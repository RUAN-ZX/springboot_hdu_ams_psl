package cn.ryanalexander.alibaba.domain.bo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: TitleInfo
 * @Description
 * @Author ryan
 * @Date 2022/3/10 20:00
 * @Version 1.0.0-Beta
 **/
@Data
@ToString
@AllArgsConstructor
@ApiModel("职称信息表")
public class TitleInfo {
    @ExcelProperty(value = "团队")
    private String teacherTeam;

    @ExcelProperty(value = "姓名")
    private String teacherName;

    @ExcelProperty(value = "职称")
    private String teacherTitle;

    @ExcelProperty(value = "系列")
    private String teacherType;

    @ExcelProperty(value = "职称级别")
    private String teacherTitleLevel;
}
