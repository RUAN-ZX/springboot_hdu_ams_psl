package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.CourseShortTerm;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName short_term
 */
@TableName(value ="short_term")
@Data
public class ShortTermPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer shortTermId;

    /**
     * 
     */
    private String shortTermNum;

    /**
     * 
     */
    private String shortTermTerm;

    /**
     * 
     */
    private String shortTermTime;

    /**
     * 
     */
    private String shortTermName;

    /**
     * 
     */
    private String shortTermAddress;

    /**
     * 
     */
    private Integer shortTermTeacherId;

    /**
     * 
     */
    private String shortTermTeacherName;

    /**
     * 
     */
    private Integer shortTermCapacity;

    /**
     * 
     */
    private Double shortTermCapacityFactor;

    /**
     * 
     */
    private Double shortTermReform;

    /**
     * 
     */
    private Double shortTermFactor;

    /**
     * 
     */
    private Double shortTermHours;

    /**
     * 
     */
    private Integer shortTermHoursStd;

    /**
     * 
     */
    private String shortTermProperties;

    /**
     * 
     */
    private String shortTermNote1;

    /**
     * 
     */
    private String shortTermNote2;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public ShortTermPO(CourseShortTerm cst){
        shortTermNum = cst.getShortTermNum();
        shortTermTerm = cst.getShortTermTerm();
        shortTermTime = cst.getShortTermTime();

        shortTermName = cst.getShortTermName();
        shortTermAddress = cst.getShortTermAddress();
        shortTermTeacherId = cst.getShortTermTeacherId();
        shortTermTeacherName = cst.getShortTermTeacherName();
        shortTermCapacity = cst.getShortTermCapacity();
        shortTermCapacityFactor = cst.getShortTermCapacityFactor();
        shortTermReform = cst.getShortTermReform();
        shortTermFactor = cst.getShortTermFactor();
        shortTermHours = cst.getShortTermHours();

        if(cst.getShortTermHoursStd() != null)
            shortTermHoursStd = (int) Math.round(cst.getShortTermHoursStd());

        shortTermProperties = cst.getShortTermProperties();
        shortTermNote1 = cst.getShortTermNote1();
        shortTermNote2 = cst.getShortTermNote2();
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
        ShortTermPO other = (ShortTermPO) that;
        return (this.getShortTermId() == null ? other.getShortTermId() == null : this.getShortTermId().equals(other.getShortTermId()))
            && (this.getShortTermNum() == null ? other.getShortTermNum() == null : this.getShortTermNum().equals(other.getShortTermNum()))
            && (this.getShortTermTerm() == null ? other.getShortTermTerm() == null : this.getShortTermTerm().equals(other.getShortTermTerm()))
            && (this.getShortTermTime() == null ? other.getShortTermTime() == null : this.getShortTermTime().equals(other.getShortTermTime()))
            && (this.getShortTermName() == null ? other.getShortTermName() == null : this.getShortTermName().equals(other.getShortTermName()))
            && (this.getShortTermAddress() == null ? other.getShortTermAddress() == null : this.getShortTermAddress().equals(other.getShortTermAddress()))
            && (this.getShortTermTeacherId() == null ? other.getShortTermTeacherId() == null : this.getShortTermTeacherId().equals(other.getShortTermTeacherId()))
            && (this.getShortTermTeacherName() == null ? other.getShortTermTeacherName() == null : this.getShortTermTeacherName().equals(other.getShortTermTeacherName()))
            && (this.getShortTermCapacity() == null ? other.getShortTermCapacity() == null : this.getShortTermCapacity().equals(other.getShortTermCapacity()))
            && (this.getShortTermCapacityFactor() == null ? other.getShortTermCapacityFactor() == null : this.getShortTermCapacityFactor().equals(other.getShortTermCapacityFactor()))
            && (this.getShortTermReform() == null ? other.getShortTermReform() == null : this.getShortTermReform().equals(other.getShortTermReform()))
            && (this.getShortTermFactor() == null ? other.getShortTermFactor() == null : this.getShortTermFactor().equals(other.getShortTermFactor()))
            && (this.getShortTermHours() == null ? other.getShortTermHours() == null : this.getShortTermHours().equals(other.getShortTermHours()))
            && (this.getShortTermHoursStd() == null ? other.getShortTermHoursStd() == null : this.getShortTermHoursStd().equals(other.getShortTermHoursStd()))
            && (this.getShortTermProperties() == null ? other.getShortTermProperties() == null : this.getShortTermProperties().equals(other.getShortTermProperties()))
            && (this.getShortTermNote1() == null ? other.getShortTermNote1() == null : this.getShortTermNote1().equals(other.getShortTermNote1()))
            && (this.getShortTermNote2() == null ? other.getShortTermNote2() == null : this.getShortTermNote2().equals(other.getShortTermNote2()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShortTermId() == null) ? 0 : getShortTermId().hashCode());
        result = prime * result + ((getShortTermNum() == null) ? 0 : getShortTermNum().hashCode());
        result = prime * result + ((getShortTermTerm() == null) ? 0 : getShortTermTerm().hashCode());
        result = prime * result + ((getShortTermTime() == null) ? 0 : getShortTermTime().hashCode());
        result = prime * result + ((getShortTermName() == null) ? 0 : getShortTermName().hashCode());
        result = prime * result + ((getShortTermAddress() == null) ? 0 : getShortTermAddress().hashCode());
        result = prime * result + ((getShortTermTeacherId() == null) ? 0 : getShortTermTeacherId().hashCode());
        result = prime * result + ((getShortTermTeacherName() == null) ? 0 : getShortTermTeacherName().hashCode());
        result = prime * result + ((getShortTermCapacity() == null) ? 0 : getShortTermCapacity().hashCode());
        result = prime * result + ((getShortTermCapacityFactor() == null) ? 0 : getShortTermCapacityFactor().hashCode());
        result = prime * result + ((getShortTermReform() == null) ? 0 : getShortTermReform().hashCode());
        result = prime * result + ((getShortTermFactor() == null) ? 0 : getShortTermFactor().hashCode());
        result = prime * result + ((getShortTermHours() == null) ? 0 : getShortTermHours().hashCode());
        result = prime * result + ((getShortTermHoursStd() == null) ? 0 : getShortTermHoursStd().hashCode());
        result = prime * result + ((getShortTermProperties() == null) ? 0 : getShortTermProperties().hashCode());
        result = prime * result + ((getShortTermNote1() == null) ? 0 : getShortTermNote1().hashCode());
        result = prime * result + ((getShortTermNote2() == null) ? 0 : getShortTermNote2().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", shortTermId=").append(shortTermId);
        sb.append(", shortTermNum=").append(shortTermNum);
        sb.append(", shortTermTerm=").append(shortTermTerm);
        sb.append(", shortTermTime=").append(shortTermTime);
        sb.append(", shortTermName=").append(shortTermName);
        sb.append(", shortTermAddress=").append(shortTermAddress);
        sb.append(", shortTermTeacherId=").append(shortTermTeacherId);
        sb.append(", shortTermTeacherName=").append(shortTermTeacherName);
        sb.append(", shortTermCapacity=").append(shortTermCapacity);
        sb.append(", shortTermCapacityFactor=").append(shortTermCapacityFactor);
        sb.append(", shortTermReform=").append(shortTermReform);
        sb.append(", shortTermFactor=").append(shortTermFactor);
        sb.append(", shortTermHours=").append(shortTermHours);
        sb.append(", shortTermHoursStd=").append(shortTermHoursStd);
        sb.append(", shortTermProperties=").append(shortTermProperties);
        sb.append(", shortTermNote1=").append(shortTermNote1);
        sb.append(", shortTermNote2=").append(shortTermNote2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}