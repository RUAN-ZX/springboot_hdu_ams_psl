package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.S1SpecialAssignment;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName special_assignment
 */
@TableName(value ="special_assignment")
@Data
public class SpecialAssignmentPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer specialAssignmentId;

    /**
     * 
     */
    private Integer specialAssignmentYear;

    /**
     * 
     */
    private Integer specialAssignmentTeacherId;

    /**
     * 
     */
    private String specialAssignmentTeacherName;

    /**
     * 
     */
    private Double specialAssignmentKpi;

    /**
     * 
     */
    private String specialAssignmentName;

    /**
     * 
     */
    private String specialAssignmentProject;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public SpecialAssignmentPO(S1SpecialAssignment s1SpecialAssignment){
        specialAssignmentKpi = s1SpecialAssignment.getSpecialAssignmentKpi();
        specialAssignmentName = s1SpecialAssignment.getSpecialAssignmentName();

        specialAssignmentTeacherId = s1SpecialAssignment.getSpecialAssignmentTeacherId();
        specialAssignmentTeacherName = s1SpecialAssignment.getSpecialAssignmentTeacherName();

        specialAssignmentProject = s1SpecialAssignment.getSpecialAssignmentProject();
        specialAssignmentYear = s1SpecialAssignment.getSpecialAssignmentYear();
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
        SpecialAssignmentPO other = (SpecialAssignmentPO) that;
        return (this.getSpecialAssignmentId() == null ? other.getSpecialAssignmentId() == null : this.getSpecialAssignmentId().equals(other.getSpecialAssignmentId()))
            && (this.getSpecialAssignmentYear() == null ? other.getSpecialAssignmentYear() == null : this.getSpecialAssignmentYear().equals(other.getSpecialAssignmentYear()))
            && (this.getSpecialAssignmentTeacherId() == null ? other.getSpecialAssignmentTeacherId() == null : this.getSpecialAssignmentTeacherId().equals(other.getSpecialAssignmentTeacherId()))
            && (this.getSpecialAssignmentTeacherName() == null ? other.getSpecialAssignmentTeacherName() == null : this.getSpecialAssignmentTeacherName().equals(other.getSpecialAssignmentTeacherName()))
            && (this.getSpecialAssignmentKpi() == null ? other.getSpecialAssignmentKpi() == null : this.getSpecialAssignmentKpi().equals(other.getSpecialAssignmentKpi()))
            && (this.getSpecialAssignmentName() == null ? other.getSpecialAssignmentName() == null : this.getSpecialAssignmentName().equals(other.getSpecialAssignmentName()))
            && (this.getSpecialAssignmentProject() == null ? other.getSpecialAssignmentProject() == null : this.getSpecialAssignmentProject().equals(other.getSpecialAssignmentProject()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSpecialAssignmentId() == null) ? 0 : getSpecialAssignmentId().hashCode());
        result = prime * result + ((getSpecialAssignmentYear() == null) ? 0 : getSpecialAssignmentYear().hashCode());
        result = prime * result + ((getSpecialAssignmentTeacherId() == null) ? 0 : getSpecialAssignmentTeacherId().hashCode());
        result = prime * result + ((getSpecialAssignmentTeacherName() == null) ? 0 : getSpecialAssignmentTeacherName().hashCode());
        result = prime * result + ((getSpecialAssignmentKpi() == null) ? 0 : getSpecialAssignmentKpi().hashCode());
        result = prime * result + ((getSpecialAssignmentName() == null) ? 0 : getSpecialAssignmentName().hashCode());
        result = prime * result + ((getSpecialAssignmentProject() == null) ? 0 : getSpecialAssignmentProject().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", specialAssignmentId=").append(specialAssignmentId);
        sb.append(", specialAssignmentTerm=").append(specialAssignmentYear);
        sb.append(", specialAssignmentTeacherId=").append(specialAssignmentTeacherId);
        sb.append(", specialAssignmentTeacherName=").append(specialAssignmentTeacherName);
        sb.append(", specialAssignmentKpi=").append(specialAssignmentKpi);
        sb.append(", specialAssignmentName=").append(specialAssignmentName);
        sb.append(", specialAssignmentProject=").append(specialAssignmentProject);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}