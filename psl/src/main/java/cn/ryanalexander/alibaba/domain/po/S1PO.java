package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.S1PostGraduate;
import cn.ryanalexander.alibaba.domain.bo.excel.S1Practical;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName s1
 */
@TableName(value ="s1")
@Data
public class S1PO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer s1Id;

    /**
     * 
     */
    private Integer s1TeacherId;

    /**
     *
     */
    private String s1TeacherName;

    /**
     * 
     */
    private Integer s1Year;

    /**
     * 
     */
    private Double s1KpiCourse;

    /**
     * 
     */
    private Double s1KpiPractical1;

    /**
     * 
     */
    private Double s1KpiPractical2;

    /**
     * 
     */
    private Double s1KpiPractical3;

    /**
     * 
     */
    private Double s1KpiPractical4;

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
    private Double s1HoursStd;

    /**
     * 
     */
    private Double s1Score;

    public S1PO(S1PostGraduate s1PostGraduate){
        s1TeacherId = s1PostGraduate.getS1TeacherId();
        s1TeacherName = s1PostGraduate.getS1TeacherName();
        s1KpiPostgraduate = s1PostGraduate.getS1KpiPostgraduate();
    }

    public S1PO(S1Practical s1Practical){
        s1KpiPractical1 = s1Practical.getS1KpiPractical1();
        s1KpiPractical2 = s1Practical.getS1KpiPractical2();
        s1KpiPractical3 = s1Practical.getS1KpiPractical3();
        s1KpiPractical4 = s1Practical.getS1KpiPractical4();
        s1TeacherId = s1Practical.getS1TeacherId();
        s1TeacherName = s1Practical.getS1TeacherName();
        s1Year = s1Practical.getS1Year();
    }

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
        S1PO other = (S1PO) that;
        return (this.getS1Id() == null ? other.getS1Id() == null : this.getS1Id().equals(other.getS1Id()))
            && (this.getS1TeacherId() == null ? other.getS1TeacherId() == null : this.getS1TeacherId().equals(other.getS1TeacherId()))
            && (this.getS1Year() == null ? other.getS1Year() == null : this.getS1Year().equals(other.getS1Year()))
            && (this.getS1KpiCourse() == null ? other.getS1KpiCourse() == null : this.getS1KpiCourse().equals(other.getS1KpiCourse()))
            && (this.getS1KpiPractical1() == null ? other.getS1KpiPractical1() == null : this.getS1KpiPractical1().equals(other.getS1KpiPractical1()))
            && (this.getS1KpiPractical2() == null ? other.getS1KpiPractical2() == null : this.getS1KpiPractical2().equals(other.getS1KpiPractical2()))
            && (this.getS1KpiPractical3() == null ? other.getS1KpiPractical3() == null : this.getS1KpiPractical3().equals(other.getS1KpiPractical3()))
            && (this.getS1KpiPractical4() == null ? other.getS1KpiPractical4() == null : this.getS1KpiPractical4().equals(other.getS1KpiPractical4()))
            && (this.getS1KpiPostgraduate() == null ? other.getS1KpiPostgraduate() == null : this.getS1KpiPostgraduate().equals(other.getS1KpiPostgraduate()))
            && (this.getS1Kpi() == null ? other.getS1Kpi() == null : this.getS1Kpi().equals(other.getS1Kpi()))
            && (this.getS1HoursStd() == null ? other.getS1HoursStd() == null : this.getS1HoursStd().equals(other.getS1HoursStd()))
            && (this.getS1Score() == null ? other.getS1Score() == null : this.getS1Score().equals(other.getS1Score()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getS1Id() == null) ? 0 : getS1Id().hashCode());
        result = prime * result + ((getS1TeacherId() == null) ? 0 : getS1TeacherId().hashCode());
        result = prime * result + ((getS1Year() == null) ? 0 : getS1Year().hashCode());
        result = prime * result + ((getS1KpiCourse() == null) ? 0 : getS1KpiCourse().hashCode());
        result = prime * result + ((getS1KpiPractical1() == null) ? 0 : getS1KpiPractical1().hashCode());
        result = prime * result + ((getS1KpiPractical2() == null) ? 0 : getS1KpiPractical2().hashCode());
        result = prime * result + ((getS1KpiPractical3() == null) ? 0 : getS1KpiPractical3().hashCode());
        result = prime * result + ((getS1KpiPractical4() == null) ? 0 : getS1KpiPractical4().hashCode());
        result = prime * result + ((getS1KpiPostgraduate() == null) ? 0 : getS1KpiPostgraduate().hashCode());
        result = prime * result + ((getS1Kpi() == null) ? 0 : getS1Kpi().hashCode());
        result = prime * result + ((getS1HoursStd() == null) ? 0 : getS1HoursStd().hashCode());
        result = prime * result + ((getS1Score() == null) ? 0 : getS1Score().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", s1Id=").append(s1Id);
        sb.append(", s1TeacherId=").append(s1TeacherId);
        sb.append(", s1Year=").append(s1Year);
        sb.append(", s1KpiCourse=").append(s1KpiCourse);
        sb.append(", s1KpiPractical1=").append(s1KpiPractical1);
        sb.append(", s1KpiPractical2=").append(s1KpiPractical2);
        sb.append(", s1KpiPractical3=").append(s1KpiPractical3);
        sb.append(", s1KpiPractical4=").append(s1KpiPractical4);
        sb.append(", s1KpiPostgraduate=").append(s1KpiPostgraduate);
        sb.append(", s1Kpi=").append(s1Kpi);
        sb.append(", s1HoursStd=").append(s1HoursStd);
        sb.append(", s1Score=").append(s1Score);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}