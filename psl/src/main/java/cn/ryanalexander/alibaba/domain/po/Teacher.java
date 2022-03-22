package cn.ryanalexander.alibaba.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName teacher
 */
@TableName(value ="teacher")
@Data
public class Teacher implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer teacherId;

    /**
     * 
     */
    private String teacherName;

    /**
     * 
     */
    private String teacherMail;

    /**
     * 
     */
    private String teacherPhone;

    /**
     * 
     */
    private String teacherPwd;

    /**
     * 
     */
    private Integer teacherAcademyId;

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
    private String teacherTitle;

    /**
     * 
     */
    private Integer teacherTitleLevel;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
        Teacher other = (Teacher) that;
        return (this.getTeacherId() == null ? other.getTeacherId() == null : this.getTeacherId().equals(other.getTeacherId()))
            && (this.getTeacherName() == null ? other.getTeacherName() == null : this.getTeacherName().equals(other.getTeacherName()))
            && (this.getTeacherMail() == null ? other.getTeacherMail() == null : this.getTeacherMail().equals(other.getTeacherMail()))
            && (this.getTeacherPhone() == null ? other.getTeacherPhone() == null : this.getTeacherPhone().equals(other.getTeacherPhone()))
            && (this.getTeacherPwd() == null ? other.getTeacherPwd() == null : this.getTeacherPwd().equals(other.getTeacherPwd()))
            && (this.getTeacherAcademyId() == null ? other.getTeacherAcademyId() == null : this.getTeacherAcademyId().equals(other.getTeacherAcademyId()))
            && (this.getTeacherTeam() == null ? other.getTeacherTeam() == null : this.getTeacherTeam().equals(other.getTeacherTeam()))
            && (this.getTeacherType() == null ? other.getTeacherType() == null : this.getTeacherType().equals(other.getTeacherType()))
            && (this.getTeacherTitle() == null ? other.getTeacherTitle() == null : this.getTeacherTitle().equals(other.getTeacherTitle()))
            && (this.getTeacherTitleLevel() == null ? other.getTeacherTitleLevel() == null : this.getTeacherTitleLevel().equals(other.getTeacherTitleLevel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTeacherId() == null) ? 0 : getTeacherId().hashCode());
        result = prime * result + ((getTeacherName() == null) ? 0 : getTeacherName().hashCode());
        result = prime * result + ((getTeacherMail() == null) ? 0 : getTeacherMail().hashCode());
        result = prime * result + ((getTeacherPhone() == null) ? 0 : getTeacherPhone().hashCode());
        result = prime * result + ((getTeacherPwd() == null) ? 0 : getTeacherPwd().hashCode());
        result = prime * result + ((getTeacherAcademyId() == null) ? 0 : getTeacherAcademyId().hashCode());
        result = prime * result + ((getTeacherTeam() == null) ? 0 : getTeacherTeam().hashCode());
        result = prime * result + ((getTeacherType() == null) ? 0 : getTeacherType().hashCode());
        result = prime * result + ((getTeacherTitle() == null) ? 0 : getTeacherTitle().hashCode());
        result = prime * result + ((getTeacherTitleLevel() == null) ? 0 : getTeacherTitleLevel().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", teacherId=").append(teacherId);
        sb.append(", teacherName=").append(teacherName);
        sb.append(", teacherMail=").append(teacherMail);
        sb.append(", teacherPhone=").append(teacherPhone);
        sb.append(", teacherPwd=").append(teacherPwd);
        sb.append(", teacherAcademyId=").append(teacherAcademyId);
        sb.append(", teacherTeam=").append(teacherTeam);
        sb.append(", teacherType=").append(teacherType);
        sb.append(", teacherTitle=").append(teacherTitle);
        sb.append(", teacherTitleLevel=").append(teacherTitleLevel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}