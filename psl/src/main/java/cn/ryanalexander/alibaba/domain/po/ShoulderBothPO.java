package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.S1ShoulderBoth;
import cn.ryanalexander.alibaba.domain.bo.excel.out.S1234;
import cn.ryanalexander.alibaba.domain.bo.excel.out.S1Workload;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName shoulder_both
 */
@TableName(value ="shoulder_both")
@Data
public class ShoulderBothPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer shoulderBothId;

    /**
     * 
     */
    private Integer shoulderBothYear;

    /**
     * 
     */
    private Integer shoulderBothTeacherId;

    /**
     * 
     */
    private String shoulderBothTeacherName;

    /**
     * 
     */
    private Integer shoulderBothHours;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public ShoulderBothPO(S1ShoulderBoth s1ShoulderBoth){
        shoulderBothHours = s1ShoulderBoth.getShoulderBothHours();
        shoulderBothTeacherId = s1ShoulderBoth.getShoulderBothTeacherId();
        shoulderBothTeacherName = s1ShoulderBoth.getShoulderBothTeacherName();
        shoulderBothYear = s1ShoulderBoth.getShoulderBothYear();
    }
    public ShoulderBothPO(S1Workload s1Workload){
        shoulderBothHours = s1Workload.getShoulderBothHours();
        shoulderBothTeacherId = s1Workload.getTeacherId();
        shoulderBothTeacherName = s1Workload.getTeacherName();
        shoulderBothYear = s1Workload.getSYear();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ShoulderBothPO other = (ShoulderBothPO) that;
        return (this.getShoulderBothId() == null ? other.getShoulderBothId() == null : this.getShoulderBothId().equals(other.getShoulderBothId()))
            && (this.getShoulderBothYear() == null ? other.getShoulderBothYear() == null : this.getShoulderBothYear().equals(other.getShoulderBothYear()))
            && (this.getShoulderBothTeacherId() == null ? other.getShoulderBothTeacherId() == null : this.getShoulderBothTeacherId().equals(other.getShoulderBothTeacherId()))
            && (this.getShoulderBothTeacherName() == null ? other.getShoulderBothTeacherName() == null : this.getShoulderBothTeacherName().equals(other.getShoulderBothTeacherName()))
            && (this.getShoulderBothHours() == null ? other.getShoulderBothHours() == null : this.getShoulderBothHours().equals(other.getShoulderBothHours()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShoulderBothId() == null) ? 0 : getShoulderBothId().hashCode());
        result = prime * result + ((getShoulderBothYear() == null) ? 0 : getShoulderBothYear().hashCode());
        result = prime * result + ((getShoulderBothTeacherId() == null) ? 0 : getShoulderBothTeacherId().hashCode());
        result = prime * result + ((getShoulderBothTeacherName() == null) ? 0 : getShoulderBothTeacherName().hashCode());
        result = prime * result + ((getShoulderBothHours() == null) ? 0 : getShoulderBothHours().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", shoulderBothId=").append(shoulderBothId);
        sb.append(", shoulderBothYear=").append(shoulderBothYear);
        sb.append(", shoulderBothTeacherId=").append(shoulderBothTeacherId);
        sb.append(", shoulderBothTeacherName=").append(shoulderBothTeacherName);
        sb.append(", shoulderBothKpi=").append(shoulderBothHours);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}