package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.out.S1234;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName s_detail
 */
@TableName(value ="s_detail")
@Data
@NoArgsConstructor
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
    private String s34Data;

    /**
     * 
     */
    private Double s3Score;

    /**
     * 
     */
    private Double s4Score;

    /**
     * 
     */
    private Double sScore;

    /**
     * 
     */
    private String sNote;

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
        sb.append(", s1Kpi=").append(s1Kpi);
        sb.append(", s1Score=").append(s1Score);
        sb.append(", s2Score1=").append(s2Score1);
        sb.append(", s2Score2=").append(s2Score2);
        sb.append(", s2ScoreAvg=").append(s2ScoreAvg);
        sb.append(", s2Rank=").append(s2Rank);
        sb.append(", s2Score=").append(s2Score);
        sb.append(", s34Data=").append(s34Data);
        sb.append(", s3Score=").append(s3Score);
        sb.append(", s4Score=").append(s4Score);
        sb.append(", sScore=").append(sScore);
        sb.append(", sNote=").append(sNote);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}