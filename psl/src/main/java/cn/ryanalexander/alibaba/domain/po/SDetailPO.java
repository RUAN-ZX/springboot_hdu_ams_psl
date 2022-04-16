package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.out.S1234;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 *
 */
@TableName(value ="s_detail")
@Data
public class SDetailPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer sDetailId;

    /**
     * 
     */
    private Integer sDetailTeacherId;

    /**
     * 
     */
    private String sDetailTeacherName;

    /**
     * 
     */
    private Integer sDetailYear;

    /**
     * 
     */
    private Double s1KpiCourseTheory;

    /**
     * 
     */
    private Double s1KpiCourseExperiment;

    /**
     * 
     */
    private Double s1KpiAchievement1;

    /**
     * 
     */
    private Double s1KpiAchievement2;

    /**
     * 
     */
    private Double s1KpiSpecialAssignment;

    /**
     * 
     */
    private Double s1KpiShoulderBoth;

    /**
     * 
     */
    private Double s1KpiPostgraduate;

    /**
     * 
     */
    private Double s1Kpi;

    /**
     * 
     */
    private Double s1Score;

    /**
     * 
     */
    private Double s2Score1;

    /**
     * 
     */
    private Double s2Score2;

    /**
     * 
     */
    private Double s2ScoreAvg;

    /**
     * 
     */
    private Integer s2Rank;

    /**
     * 
     */
    private Double s2Score;

    /**
     * 
     */
    private Integer sRank;

    /**
     * 
     */
    private String s3Data;

    /**
     * 
     */
    private Double s3Score;

    /**
     * 
     */
    private String s4Data;

    /**
     * 
     */
    private Double s4Score;

    /**
     * 
     */
    private Double sScore;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public SDetailPO(S1234 s1234){

    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sDetailId=").append(sDetailId);
        sb.append(", sDetailTeacherId=").append(sDetailTeacherId);
        sb.append(", sDetailTeacherName=").append(sDetailTeacherName);
        sb.append(", sDetailYear=").append(sDetailYear);
        sb.append(", s1KpiCourseTheory=").append(s1KpiCourseTheory);
        sb.append(", s1KpiCourseExperiment=").append(s1KpiCourseExperiment);
        sb.append(", s1KpiAchievement1=").append(s1KpiAchievement1);
        sb.append(", s1KpiAchievement2=").append(s1KpiAchievement2);
        sb.append(", s1KpiSpecialAssignment=").append(s1KpiSpecialAssignment);
        sb.append(", s1KpiShoulderBoth=").append(s1KpiShoulderBoth);
        sb.append(", s1KpiPostgraduate=").append(s1KpiPostgraduate);
        sb.append(", s1Kpi=").append(s1Kpi);
        sb.append(", s1Score=").append(s1Score);
        sb.append(", s2Score1=").append(s2Score1);
        sb.append(", s2Score2=").append(s2Score2);
        sb.append(", s2ScoreAvg=").append(s2ScoreAvg);
        sb.append(", s2Rank=").append(s2Rank);
        sb.append(", s2Score=").append(s2Score);
        sb.append(", sRank=").append(sRank);
        sb.append(", s3Data=").append(s3Data);
        sb.append(", s3Score=").append(s3Score);
        sb.append(", s4Data=").append(s4Data);
        sb.append(", s4Score=").append(s4Score);
        sb.append(", sScore=").append(sScore);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}