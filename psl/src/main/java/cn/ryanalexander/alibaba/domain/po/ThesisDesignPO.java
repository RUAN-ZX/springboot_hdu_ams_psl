package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.S1ThesisDesign;
import cn.ryanalexander.alibaba.service.tool.ExcelDataProcessUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName thesis_design
 */
@TableName(value ="thesis_design")
@Data
public class ThesisDesignPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer thesisDesignId;

    /**
     * 
     */
    private Integer thesisDesignYear;

    /**
     * 
     */
    private String thesisDesignNote;

    /**
     * 
     */
    private Integer thesisDesignTeacherId;

    /**
     * 
     */
    private String thesisDesignTeacherName;

    /**
     * 
     */
    private String thesisDesignStudentName;

    /**
     * 
     */
    private Object thesisDesignStudentId;

    /**
     * 
     */
    private Integer thesisDesignGrade;

    /**
     * 
     */
    private Double thesisDesignFactor1;

    /**
     * 
     */
    private Double thesisDesignFactor2;

    /**
     * 
     */
    private Double thesisDesignT1;

    /**
     * 
     */
    private Integer thesisDesignStd;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    public ThesisDesignPO(S1ThesisDesign ctd){

        thesisDesignYear = ctd.getThesisDesignYear();
        thesisDesignNote = ctd.getThesisDesignNote();
        thesisDesignTeacherId = ctd.getThesisDesignTeacherId();
        thesisDesignTeacherName = ctd.getThesisDesignTeacherName();
        thesisDesignStudentName = ctd.getThesisDesignStudentName();
        thesisDesignStudentId = ctd.getStudentId();
        thesisDesignGrade = ExcelDataProcessUtil.transformThesisDesignGrade(ctd.getThesisDesignGrade());
        thesisDesignFactor1 = ctd.getThesisDesignFactor1();
        thesisDesignFactor2 = ctd.getThesisDesignFactor2();
        thesisDesignT1 = ctd.getThesisDesignT1();
        thesisDesignStd = ctd.getThesisDesignStd();
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
        ThesisDesignPO other = (ThesisDesignPO) that;
        return (this.getThesisDesignId() == null ? other.getThesisDesignId() == null : this.getThesisDesignId().equals(other.getThesisDesignId()))
            && (this.getThesisDesignYear() == null ? other.getThesisDesignYear() == null : this.getThesisDesignYear().equals(other.getThesisDesignYear()))
            && (this.getThesisDesignNote() == null ? other.getThesisDesignNote() == null : this.getThesisDesignNote().equals(other.getThesisDesignNote()))
            && (this.getThesisDesignTeacherId() == null ? other.getThesisDesignTeacherId() == null : this.getThesisDesignTeacherId().equals(other.getThesisDesignTeacherId()))
            && (this.getThesisDesignTeacherName() == null ? other.getThesisDesignTeacherName() == null : this.getThesisDesignTeacherName().equals(other.getThesisDesignTeacherName()))
            && (this.getThesisDesignStudentName() == null ? other.getThesisDesignStudentName() == null : this.getThesisDesignStudentName().equals(other.getThesisDesignStudentName()))
            && (this.getThesisDesignStudentId() == null ? other.getThesisDesignStudentId() == null : this.getThesisDesignStudentId().equals(other.getThesisDesignStudentId()))
            && (this.getThesisDesignGrade() == null ? other.getThesisDesignGrade() == null : this.getThesisDesignGrade().equals(other.getThesisDesignGrade()))
            && (this.getThesisDesignFactor1() == null ? other.getThesisDesignFactor1() == null : this.getThesisDesignFactor1().equals(other.getThesisDesignFactor1()))
            && (this.getThesisDesignFactor2() == null ? other.getThesisDesignFactor2() == null : this.getThesisDesignFactor2().equals(other.getThesisDesignFactor2()))
            && (this.getThesisDesignT1() == null ? other.getThesisDesignT1() == null : this.getThesisDesignT1().equals(other.getThesisDesignT1()))
            && (this.getThesisDesignStd() == null ? other.getThesisDesignStd() == null : this.getThesisDesignStd().equals(other.getThesisDesignStd()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getThesisDesignId() == null) ? 0 : getThesisDesignId().hashCode());
        result = prime * result + ((getThesisDesignYear() == null) ? 0 : getThesisDesignYear().hashCode());
        result = prime * result + ((getThesisDesignNote() == null) ? 0 : getThesisDesignNote().hashCode());
        result = prime * result + ((getThesisDesignTeacherId() == null) ? 0 : getThesisDesignTeacherId().hashCode());
        result = prime * result + ((getThesisDesignTeacherName() == null) ? 0 : getThesisDesignTeacherName().hashCode());
        result = prime * result + ((getThesisDesignStudentName() == null) ? 0 : getThesisDesignStudentName().hashCode());
        result = prime * result + ((getThesisDesignStudentId() == null) ? 0 : getThesisDesignStudentId().hashCode());
        result = prime * result + ((getThesisDesignGrade() == null) ? 0 : getThesisDesignGrade().hashCode());
        result = prime * result + ((getThesisDesignFactor1() == null) ? 0 : getThesisDesignFactor1().hashCode());
        result = prime * result + ((getThesisDesignFactor2() == null) ? 0 : getThesisDesignFactor2().hashCode());
        result = prime * result + ((getThesisDesignT1() == null) ? 0 : getThesisDesignT1().hashCode());
        result = prime * result + ((getThesisDesignStd() == null) ? 0 : getThesisDesignStd().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", thesisDesignId=").append(thesisDesignId);
        sb.append(", thesisDesignYear=").append(thesisDesignYear);
        sb.append(", thesisDesignNote=").append(thesisDesignNote);
        sb.append(", thesisDesignTeacherId=").append(thesisDesignTeacherId);
        sb.append(", thesisDesignTeacherName=").append(thesisDesignTeacherName);
        sb.append(", thesisDesignStudentName=").append(thesisDesignStudentName);
        sb.append(", thesisDesignStudentId=").append(thesisDesignStudentId);
        sb.append(", thesisDesignGrade=").append(thesisDesignGrade);
        sb.append(", thesisDesignFactor1=").append(thesisDesignFactor1);
        sb.append(", thesisDesignFactor2=").append(thesisDesignFactor2);
        sb.append(", thesisDesignT1=").append(thesisDesignT1);
        sb.append(", thesisDesignStd=").append(thesisDesignStd);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}