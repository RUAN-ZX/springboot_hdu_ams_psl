package cn.ryanalexander.alibaba.domain.bo.excel.out;

import cn.ryanalexander.alibaba.domain.po.SDetailPO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p><b></b></p>
 *
 * <p>2022/4/11 </p>
 *
 * @author ryan 2022/4/11 14:21
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("S1工作量表")
@ToString
@ExcelIgnoreUnannotated
public class S1Workload{
    // List index就完事了
    @ExcelProperty(value = "序号")
    private String id;

    @ExcelProperty(value = "团队")
    private String teacherTeam;

    @ExcelProperty(value = "姓名")
    private String teacherName;

    @ExcelProperty(value = "职工号")
    private String teacherId;

    // 除了研究生 双肩挑的所有。。。
    @ExcelProperty(value = "本科教学业绩点")
    private String teacherHoursStd;

    @ExcelProperty(value = "研究生标准课时总计")
    private String postGraduateHours; // postGraduateKpi * 100

    @ExcelProperty(value = "双肩挑")
    private String shoulderBothHours;

    @ExcelProperty(value = "小计")
    private String s1Hours;

    @ExcelProperty(value = "S1")
    private String s1Score_;

    @ExcelProperty(value = "S1封顶")
    private String s1Score; // 100最多拉

    @ExcelProperty(value = "备注")
    private String note; // 没数据的 让老师后边自个儿往excel里边加得了

    public S1Workload(SDetailPO sDetailPO){

    }
}
