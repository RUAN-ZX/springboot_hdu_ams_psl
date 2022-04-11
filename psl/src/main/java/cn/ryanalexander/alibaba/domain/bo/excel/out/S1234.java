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
 * @author ryan 2022/4/11 14:37
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("汇总表")
@ToString
@ExcelIgnoreUnannotated
public class S1234 {
    @ExcelProperty(value = "部门")
    private String teacherTeam;

    @ExcelProperty(value = "工号")
    private String teacherId;

    @ExcelProperty(value = "姓名")
    private String teacherName;

    @ExcelProperty(value = "职称")
    private String teacherTitleName;

    @ExcelProperty(value = "系列")
    private String teacherType;

    @ExcelProperty(value = "职称等级")
    private String teacherLevel;

    // ------------------------------------------------------------------------

    @ExcelProperty(value = {"工作量S1","合计教学工作量"})
    private String s1HoursStd;

    @ExcelProperty(value = {"工作量S1","S1"})
    private String s1Score;

    // ------------------------------------------------------------------------
    // todo 表头需要变量？
    @ExcelProperty(value = {"学评教S2", "第一学期"})
    private String s2Score1;

    @ExcelProperty(value = {"学评教S2", "第二学期"})
    private String s2Score2;

    @ExcelProperty(value = {"学评教S2", "平均值"})
    private String s2ScoreAvg;

    @ExcelProperty(value = {"学评教S2", "平均\n排名"})
    private String s2Rank;

    @ExcelProperty(value = {"学评教S2", "S2"})
    private String s2Score;

    // ------------------------------------------------------------------------

    @ExcelProperty(value = {"S3", "学科竞赛"})
    private String s31First;

    @ExcelProperty(value = {"S3", "其它省级比赛"})
    private String s31Second;

    @ExcelProperty(value = {"S3", "S31"})
    private String s31;

    @ExcelProperty(value = {"S3", "教学成果奖\n"})
    private String s32First;

    @ExcelProperty(value = {"S3", "教学名师奖\n"})
    private String s32Second;

    @ExcelProperty(value = {"S3", "其它教学奖励\n"})
    private String s32Third;

    @ExcelProperty(value = {"S3", "教学技能奖\n"})
    private String s32Fourth;

    @ExcelProperty(value = {"S3", "教学事故\n"})
    private String s32Fifth;

    @ExcelProperty(value = {"S3", "S32\n"})
    private String s32;

    @ExcelProperty(value = {"S3", "S3"})
    private String s3;

    // ------------------------------------------------------------------------

    @ExcelProperty(value = {"S4", "教改项目"})
    private String s41First;

    @ExcelProperty(value = {"S4", "实验教学示范中心建设项目\n"})
    private String s41Second;

    @ExcelProperty(value = {"S4", "教学团队"})
    private String s41Third;

    @ExcelProperty(value = {"S4", "S41"})
    private String s41;

    @ExcelProperty(value = {"S4", "专业建设"})
    private String s42First;

    @ExcelProperty(value = {"S4", "课程建设\n"})
    private String s42Second;

    @ExcelProperty(value = {"S4", "教材建设\n"})
    private String s42Third;

    @ExcelProperty(value = {"S4", "S42\n"})
    private String s42;

    @ExcelProperty(value = {"S4", "公开发表论文\n"})
    private String s43First;

    @ExcelProperty(value = {"S3", "S43\n"})
    private String s43;

    @ExcelProperty(value = {"S3", "S4\n"})
    private String s4;

    // ------------------------------------------------------------------------

    @ExcelProperty(value = "总分")
    private String sScore;

    // todo 这个备注信息哪里来的。。不需要的话我当没有了
//    @ExcelProperty(value = "备注1（本人提出不参与）\n", index = 20)
//    private String note;

    public S1234(SDetailPO sDetailPO){

    }
}
