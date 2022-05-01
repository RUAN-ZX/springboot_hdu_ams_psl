package cn.ryanalexander.psl.domain.po;

import cn.ryanalexander.psl.domain.bo.excel.TitleInfo;
import cn.ryanalexander.psl.service.tool.DataUtil;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName teacher
 */
@TableName(value ="teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer teacherTitleId;

    /**
     * 
     */
    private Integer teacherId;

    /**
     * 
     */
    private String teacherName;

    /**
     * 
     */
    private String teacherTeam;

    /**
     * 
     */
    private String teacherType;

    /**
     * 
     */
    private String teacherTitleName;

    /**
     * 
     */
    private Integer teacherLevel;

    /**
     * 
     */
    private Integer teacherTitleYear;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public TeacherPO(TitleInfo titleInfo){
        teacherName = titleInfo.getTeacherName();
        teacherId = DataUtil.string2integer(titleInfo.getTeacherId());

        teacherLevel = DataUtil.transformTitleGrade(titleInfo.getTeacherLevel());
        teacherType = titleInfo.getTeacherType();
        teacherTitleName = titleInfo.getTeacherTitleName();

        teacherTeam = titleInfo.getTeacherTeam();
        teacherTitleYear = DataUtil.string2integer(titleInfo.getTeacherTitleYear());
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
        TeacherPO other = (TeacherPO) that;
        return (this.getTeacherTitleId() == null ? other.getTeacherTitleId() == null : this.getTeacherTitleId().equals(other.getTeacherTitleId()))
            && (this.getTeacherId() == null ? other.getTeacherId() == null : this.getTeacherId().equals(other.getTeacherId()))
            && (this.getTeacherName() == null ? other.getTeacherName() == null : this.getTeacherName().equals(other.getTeacherName()))
            && (this.getTeacherTeam() == null ? other.getTeacherTeam() == null : this.getTeacherTeam().equals(other.getTeacherTeam()))
            && (this.getTeacherType() == null ? other.getTeacherType() == null : this.getTeacherType().equals(other.getTeacherType()))
            && (this.getTeacherTitleName() == null ? other.getTeacherTitleName() == null : this.getTeacherTitleName().equals(other.getTeacherTitleName()))
            && (this.getTeacherLevel() == null ? other.getTeacherLevel() == null : this.getTeacherLevel().equals(other.getTeacherLevel()))
            && (this.getTeacherTitleYear() == null ? other.getTeacherTitleYear() == null : this.getTeacherTitleYear().equals(other.getTeacherTitleYear()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTeacherTitleId() == null) ? 0 : getTeacherTitleId().hashCode());
        result = prime * result + ((getTeacherId() == null) ? 0 : getTeacherId().hashCode());
        result = prime * result + ((getTeacherName() == null) ? 0 : getTeacherName().hashCode());
        result = prime * result + ((getTeacherTeam() == null) ? 0 : getTeacherTeam().hashCode());
        result = prime * result + ((getTeacherType() == null) ? 0 : getTeacherType().hashCode());
        result = prime * result + ((getTeacherTitleName() == null) ? 0 : getTeacherTitleName().hashCode());
        result = prime * result + ((getTeacherLevel() == null) ? 0 : getTeacherLevel().hashCode());
        result = prime * result + ((getTeacherTitleYear() == null) ? 0 : getTeacherTitleYear().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", teacherTitleId=").append(teacherTitleId);
        sb.append(", teacherId=").append(teacherId);
        sb.append(", teacherName=").append(teacherName);
        sb.append(", teacherTeam=").append(teacherTeam);
        sb.append(", teacherType=").append(teacherType);
        sb.append(", teacherTitleName=").append(teacherTitleName);
        sb.append(", teacherLevel=").append(teacherLevel);
        sb.append(", teacherTitleYear=").append(teacherTitleYear);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}