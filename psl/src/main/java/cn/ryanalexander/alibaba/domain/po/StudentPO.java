package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.CourseThesisDesign;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName student
 */
@TableName(value ="student")
@Data
public class StudentPO implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer studentId;

    /**
     * 
     */
    private String studentName;

    /**
     * 
     */
    private String studentMajor;

    private Integer studentGraduateYear;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;



    public StudentPO(CourseThesisDesign ctd){
        studentId = Integer.valueOf(ctd.getStudentId());
        studentName = ctd.getThesisDesignStudentName();
        studentMajor = ctd.getStudentMajor();
        studentGraduateYear = ctd.getThesisDesignYear();
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
        StudentPO other = (StudentPO) that;
        return (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getStudentName() == null ? other.getStudentName() == null : this.getStudentName().equals(other.getStudentName()))
            && (this.getStudentMajor() == null ? other.getStudentMajor() == null : this.getStudentMajor().equals(other.getStudentMajor()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getStudentName() == null) ? 0 : getStudentName().hashCode());
        result = prime * result + ((getStudentMajor() == null) ? 0 : getStudentMajor().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", studentId=").append(studentId);
        sb.append(", studentName=").append(studentName);
        sb.append(", studentMajor=").append(studentMajor);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}