package cn.ryanalexander.alibaba.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: Email
 * @Description
 * @Author ryan
 * @Date 2022/3/10 20:01
 * @Version 1.0.0-Beta
 **/
@Data
@ToString
@AllArgsConstructor
@ApiModel("工号和邮箱")
public class TidAndEmail {
    @ExcelProperty(value = "职工号")
    public String t_id;

    @ExcelProperty(value = "姓名")
    public String t_name;

    @ExcelProperty(value = "邮箱")
    public String t_email;
}
