package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.CourseTheory;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 *
 * @TableName course
 */
@TableName(value ="course")
@Data
public class Course implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer courseId;

    /**
     *
     */
    private String courseNum;

    /**
     *
     */
    private String courseTerm;

    /**
     *
     */
    private String courseTime;

    /**
     *
     */
    private String courseName;

    /**
     *
     */
    private String courseAddress;

    /**
     *
     */
    private Integer courseTeacherId;

    /**
     *
     */
    private String courseTeacherName;

    /**
     *
     */
    private Integer courseType;

    /**
     *
     */
    private Integer courseCapacity;

    /**
     *
     */
    private Double courseCapacityFactor1;

    /**
     *
     */
    private Double courseCapacityFactor2;

    /**
     *
     */
    private Double courseHours;

    /**
     *
     */
    private Double courseHoursTheory;

    /**
     *
     */
    private Double courseHoursExp;

    /**
     *
     */
    private Integer courseHoursExpStd;

    /**
     *
     */
    private Integer courseHoursTheoryStd;

    /**
     *
     */
    private Integer courseHoursStd;

    /**
     *
     */
    private String courseBilingual;

    /**
     *
     */
    private String courseReform;

    /**
     *
     */
    private Double courseFactor;

    /**
     *
     */
    private Double coursePrior;

    /**
     *
     */
    private Double courseHoursOp;

    /**
     *
     */
    private Double coursePoints;

    /**
     *
     */
    private String courseProperties;

    /**
     *
     */
    private String courseNote1;

    /**
     *
     */
    private String courseNote2;

    @TableField(exist = false)

    private static final long serialVersionUID = 1L;

    public Course(CourseTheory c){
        this.courseTerm = c.getCourseTerm();
        this.courseAddress = c.getCourseAddress();
        this.courseBilingual = c.getCourseBilingual();
        this.courseCapacity = c.getCourseCapacity();
        this.courseFactor = c.getCourseFactor();
        this.courseCapacityFactor1 = c.getCourseCapacityFactor1();
        this.courseHours = c.getCourseHours();
        this.courseHoursTheory = c.getCourseHoursTheory();
        this.courseHoursExp = c.getCourseHoursExp();
        this.courseHoursStd = c.getCourseHoursStd();
        this.courseHoursExpStd = c.getCourseHoursExpStd();
        this.courseHoursTheoryStd = c.getCourseHoursTheoryStd();
        this.courseName = c.getCourseName();
        this.courseNum = c.getCourseNum();
        this.coursePrior = c.getCoursePrior();
        this.courseReform = c.getCourseReform();
        this.courseTeacherId = c.getCourseTeacherId();
        this.courseTeacherName = c.getCourseTeacherName();
        this.courseType = 1;
        this.courseTime = c.getCourseTime();
        this.courseNote1 = c.getCourseNote1();
        this.courseNote2 = c.getCourseNote2();
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
        Course other = (Course) that;
        return (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
                && (this.getCourseNum() == null ? other.getCourseNum() == null : this.getCourseNum().equals(other.getCourseNum()))
                && (this.getCourseTerm() == null ? other.getCourseTerm() == null : this.getCourseTerm().equals(other.getCourseTerm()))
                && (this.getCourseTime() == null ? other.getCourseTime() == null : this.getCourseTime().equals(other.getCourseTime()))
                && (this.getCourseName() == null ? other.getCourseName() == null : this.getCourseName().equals(other.getCourseName()))
                && (this.getCourseAddress() == null ? other.getCourseAddress() == null : this.getCourseAddress().equals(other.getCourseAddress()))
                && (this.getCourseTeacherId() == null ? other.getCourseTeacherId() == null : this.getCourseTeacherId().equals(other.getCourseTeacherId()))
                && (this.getCourseTeacherName() == null ? other.getCourseTeacherName() == null : this.getCourseTeacherName().equals(other.getCourseTeacherName()))
                && (this.getCourseType() == null ? other.getCourseType() == null : this.getCourseType().equals(other.getCourseType()))
                && (this.getCourseCapacity() == null ? other.getCourseCapacity() == null : this.getCourseCapacity().equals(other.getCourseCapacity()))
                && (this.getCourseCapacityFactor1() == null ? other.getCourseCapacityFactor1() == null : this.getCourseCapacityFactor1().equals(other.getCourseCapacityFactor1()))
                && (this.getCourseCapacityFactor2() == null ? other.getCourseCapacityFactor2() == null : this.getCourseCapacityFactor2().equals(other.getCourseCapacityFactor2()))
                && (this.getCourseHours() == null ? other.getCourseHours() == null : this.getCourseHours().equals(other.getCourseHours()))
                && (this.getCourseHoursTheory() == null ? other.getCourseHoursTheory() == null : this.getCourseHoursTheory().equals(other.getCourseHoursTheory()))
                && (this.getCourseHoursExp() == null ? other.getCourseHoursExp() == null : this.getCourseHoursExp().equals(other.getCourseHoursExp()))
                && (this.getCourseHoursExpStd() == null ? other.getCourseHoursExpStd() == null : this.getCourseHoursExpStd().equals(other.getCourseHoursExpStd()))
                && (this.getCourseHoursTheoryStd() == null ? other.getCourseHoursTheoryStd() == null : this.getCourseHoursTheoryStd().equals(other.getCourseHoursTheoryStd()))
                && (this.getCourseHoursStd() == null ? other.getCourseHoursStd() == null : this.getCourseHoursStd().equals(other.getCourseHoursStd()))
                && (this.getCourseBilingual() == null ? other.getCourseBilingual() == null : this.getCourseBilingual().equals(other.getCourseBilingual()))
                && (this.getCourseReform() == null ? other.getCourseReform() == null : this.getCourseReform().equals(other.getCourseReform()))
                && (this.getCourseFactor() == null ? other.getCourseFactor() == null : this.getCourseFactor().equals(other.getCourseFactor()))
                && (this.getCoursePrior() == null ? other.getCoursePrior() == null : this.getCoursePrior().equals(other.getCoursePrior()))
                && (this.getCourseHoursOp() == null ? other.getCourseHoursOp() == null : this.getCourseHoursOp().equals(other.getCourseHoursOp()))
                && (this.getCoursePoints() == null ? other.getCoursePoints() == null : this.getCoursePoints().equals(other.getCoursePoints()))
                && (this.getCourseProperties() == null ? other.getCourseProperties() == null : this.getCourseProperties().equals(other.getCourseProperties()))
                && (this.getCourseNote1() == null ? other.getCourseNote1() == null : this.getCourseNote1().equals(other.getCourseNote1()))
                && (this.getCourseNote2() == null ? other.getCourseNote2() == null : this.getCourseNote2().equals(other.getCourseNote2()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getCourseNum() == null) ? 0 : getCourseNum().hashCode());
        result = prime * result + ((getCourseTerm() == null) ? 0 : getCourseTerm().hashCode());
        result = prime * result + ((getCourseTime() == null) ? 0 : getCourseTime().hashCode());
        result = prime * result + ((getCourseName() == null) ? 0 : getCourseName().hashCode());
        result = prime * result + ((getCourseAddress() == null) ? 0 : getCourseAddress().hashCode());
        result = prime * result + ((getCourseTeacherId() == null) ? 0 : getCourseTeacherId().hashCode());
        result = prime * result + ((getCourseTeacherName() == null) ? 0 : getCourseTeacherName().hashCode());
        result = prime * result + ((getCourseType() == null) ? 0 : getCourseType().hashCode());
        result = prime * result + ((getCourseCapacity() == null) ? 0 : getCourseCapacity().hashCode());
        result = prime * result + ((getCourseCapacityFactor1() == null) ? 0 : getCourseCapacityFactor1().hashCode());
        result = prime * result + ((getCourseCapacityFactor2() == null) ? 0 : getCourseCapacityFactor2().hashCode());
        result = prime * result + ((getCourseHours() == null) ? 0 : getCourseHours().hashCode());
        result = prime * result + ((getCourseHoursTheory() == null) ? 0 : getCourseHoursTheory().hashCode());
        result = prime * result + ((getCourseHoursExp() == null) ? 0 : getCourseHoursExp().hashCode());
        result = prime * result + ((getCourseHoursExpStd() == null) ? 0 : getCourseHoursExpStd().hashCode());
        result = prime * result + ((getCourseHoursTheoryStd() == null) ? 0 : getCourseHoursTheoryStd().hashCode());
        result = prime * result + ((getCourseHoursStd() == null) ? 0 : getCourseHoursStd().hashCode());
        result = prime * result + ((getCourseBilingual() == null) ? 0 : getCourseBilingual().hashCode());
        result = prime * result + ((getCourseReform() == null) ? 0 : getCourseReform().hashCode());
        result = prime * result + ((getCourseFactor() == null) ? 0 : getCourseFactor().hashCode());
        result = prime * result + ((getCoursePrior() == null) ? 0 : getCoursePrior().hashCode());
        result = prime * result + ((getCourseHoursOp() == null) ? 0 : getCourseHoursOp().hashCode());
        result = prime * result + ((getCoursePoints() == null) ? 0 : getCoursePoints().hashCode());
        result = prime * result + ((getCourseProperties() == null) ? 0 : getCourseProperties().hashCode());
        result = prime * result + ((getCourseNote1() == null) ? 0 : getCourseNote1().hashCode());
        result = prime * result + ((getCourseNote2() == null) ? 0 : getCourseNote2().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", courseId=").append(courseId);
        sb.append(", courseNum=").append(courseNum);
        sb.append(", courseTerm=").append(courseTerm);
        sb.append(", courseTime=").append(courseTime);
        sb.append(", courseName=").append(courseName);
        sb.append(", courseAddress=").append(courseAddress);
        sb.append(", courseTeacherId=").append(courseTeacherId);
        sb.append(", courseTeacherName=").append(courseTeacherName);
        sb.append(", courseType=").append(courseType);
        sb.append(", courseCapacity=").append(courseCapacity);
        sb.append(", courseCapacityFactor1=").append(courseCapacityFactor1);
        sb.append(", courseCapacityFactor2=").append(courseCapacityFactor2);
        sb.append(", courseHours=").append(courseHours);
        sb.append(", courseHoursTheory=").append(courseHoursTheory);
        sb.append(", courseHoursExp=").append(courseHoursExp);
        sb.append(", courseHoursExpStd=").append(courseHoursExpStd);
        sb.append(", courseHoursTheoryStd=").append(courseHoursTheoryStd);
        sb.append(", courseHoursStd=").append(courseHoursStd);
        sb.append(", courseBilingual=").append(courseBilingual);
        sb.append(", courseReform=").append(courseReform);
        sb.append(", courseFactor=").append(courseFactor);
        sb.append(", coursePrior=").append(coursePrior);
        sb.append(", courseHoursOp=").append(courseHoursOp);
        sb.append(", coursePoints=").append(coursePoints);
        sb.append(", courseProperties=").append(courseProperties);
        sb.append(", courseNote1=").append(courseNote1);
        sb.append(", courseNote2=").append(courseNote2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}