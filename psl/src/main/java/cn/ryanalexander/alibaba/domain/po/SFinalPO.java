package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.out.SFinal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName s_final
 */
@TableName(value ="s_final")
@Data
public class SFinalPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer sFinalId;

    /**
     * 
     */
    private String sFinalTeacherName;

    /**
     * 
     */
    private Integer sFinalTeacherId;

    /**
     * 
     */
    private Double sFinalScore;

    /**
     * 
     */
    private Integer sFinalYear;

    /**
     * 
     */
    private String sFinalGrade;

    /**
     * 
     */
    private Integer sFinalTitleLevel;

    /**
     * 
     */
    private Integer sFinalHoursAll;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    public SFinalPO(SFinal sFinal){

    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sFinalId=").append(sFinalId);
        sb.append(", sFinalTeacherName=").append(sFinalTeacherName);
        sb.append(", sFinalTeacherId=").append(sFinalTeacherId);
        sb.append(", sFinalScore=").append(sFinalScore);
        sb.append(", sFinalYear=").append(sFinalYear);
        sb.append(", sFinalGrade=").append(sFinalGrade);
        sb.append(", sFinalTitleLevel=").append(sFinalTitleLevel);
        sb.append(", sFinalHoursAll=").append(sFinalHoursAll);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}