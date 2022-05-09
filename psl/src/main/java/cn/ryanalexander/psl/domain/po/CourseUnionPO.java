package cn.ryanalexander.psl.domain.po;

import cn.ryanalexander.psl.domain.bo.excel.Course;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName course_union
 */
@TableName(value ="course_union")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUnionPO implements Serializable {
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
    private Double coursePoints;

    /**
     * 
     */
    private String courseClass;

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
    private String courseMajor;

    /**
     * 
     */
    private Object courseTeacherId;

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
    private Double courseCapacityFactor;

    /**
     * 
     */
    private Integer courseHours;

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
    private Double courseHoursStd;

    /**
     * 
     */
    private String courseData;

    /**
     * 
     */
    private String courseDataOthers;

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

    public CourseUnionPO(Course course){
        this.courseTerm = course.getCourseTerm();
        this.courseNum = course.getCourseNum();
        this.courseName = course.getCourseName();

        this.coursePoints = course.getCoursePoints();
        this.courseClass = course.getCourseClass();
        this.courseTime = course.getCourseTime();
        this.courseAddress = course.getCourseAddress();
        this.courseMajor = course.getCourseMajor();

        this.courseTeacherId = course.getCourseTeacherId();
        this.courseTeacherName = course.getCourseTeacherName();

        this.courseType = course.getCourseType();
        this.courseCapacity = course.getCourseCapacity();
        this.courseHours = course.getCourseHours();

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
        CourseUnionPO other = (CourseUnionPO) that;
        return (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
            && (this.getCourseNum() == null ? other.getCourseNum() == null : this.getCourseNum().equals(other.getCourseNum()))
            && (this.getCourseTerm() == null ? other.getCourseTerm() == null : this.getCourseTerm().equals(other.getCourseTerm()))
            && (this.getCoursePoints() == null ? other.getCoursePoints() == null : this.getCoursePoints().equals(other.getCoursePoints()))
            && (this.getCourseClass() == null ? other.getCourseClass() == null : this.getCourseClass().equals(other.getCourseClass()))
            && (this.getCourseTime() == null ? other.getCourseTime() == null : this.getCourseTime().equals(other.getCourseTime()))
            && (this.getCourseName() == null ? other.getCourseName() == null : this.getCourseName().equals(other.getCourseName()))
            && (this.getCourseAddress() == null ? other.getCourseAddress() == null : this.getCourseAddress().equals(other.getCourseAddress()))
            && (this.getCourseMajor() == null ? other.getCourseMajor() == null : this.getCourseMajor().equals(other.getCourseMajor()))
            && (this.getCourseTeacherId() == null ? other.getCourseTeacherId() == null : this.getCourseTeacherId().equals(other.getCourseTeacherId()))
            && (this.getCourseTeacherName() == null ? other.getCourseTeacherName() == null : this.getCourseTeacherName().equals(other.getCourseTeacherName()))
            && (this.getCourseType() == null ? other.getCourseType() == null : this.getCourseType().equals(other.getCourseType()))
            && (this.getCourseCapacity() == null ? other.getCourseCapacity() == null : this.getCourseCapacity().equals(other.getCourseCapacity()))
            && (this.getCourseCapacityFactor() == null ? other.getCourseCapacityFactor() == null : this.getCourseCapacityFactor().equals(other.getCourseCapacityFactor()))
            && (this.getCourseHours() == null ? other.getCourseHours() == null : this.getCourseHours().equals(other.getCourseHours()))
            && (this.getCourseHoursTheory() == null ? other.getCourseHoursTheory() == null : this.getCourseHoursTheory().equals(other.getCourseHoursTheory()))
            && (this.getCourseHoursExp() == null ? other.getCourseHoursExp() == null : this.getCourseHoursExp().equals(other.getCourseHoursExp()))
            && (this.getCourseHoursStd() == null ? other.getCourseHoursStd() == null : this.getCourseHoursStd().equals(other.getCourseHoursStd()))
            && (this.getCourseData() == null ? other.getCourseData() == null : this.getCourseData().equals(other.getCourseData()))
            && (this.getCourseDataOthers() == null ? other.getCourseDataOthers() == null : this.getCourseDataOthers().equals(other.getCourseDataOthers()))
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
        result = prime * result + ((getCoursePoints() == null) ? 0 : getCoursePoints().hashCode());
        result = prime * result + ((getCourseClass() == null) ? 0 : getCourseClass().hashCode());
        result = prime * result + ((getCourseTime() == null) ? 0 : getCourseTime().hashCode());
        result = prime * result + ((getCourseName() == null) ? 0 : getCourseName().hashCode());
        result = prime * result + ((getCourseAddress() == null) ? 0 : getCourseAddress().hashCode());
        result = prime * result + ((getCourseMajor() == null) ? 0 : getCourseMajor().hashCode());
        result = prime * result + ((getCourseTeacherId() == null) ? 0 : getCourseTeacherId().hashCode());
        result = prime * result + ((getCourseTeacherName() == null) ? 0 : getCourseTeacherName().hashCode());
        result = prime * result + ((getCourseType() == null) ? 0 : getCourseType().hashCode());
        result = prime * result + ((getCourseCapacity() == null) ? 0 : getCourseCapacity().hashCode());
        result = prime * result + ((getCourseCapacityFactor() == null) ? 0 : getCourseCapacityFactor().hashCode());
        result = prime * result + ((getCourseHours() == null) ? 0 : getCourseHours().hashCode());
        result = prime * result + ((getCourseHoursTheory() == null) ? 0 : getCourseHoursTheory().hashCode());
        result = prime * result + ((getCourseHoursExp() == null) ? 0 : getCourseHoursExp().hashCode());
        result = prime * result + ((getCourseHoursStd() == null) ? 0 : getCourseHoursStd().hashCode());
        result = prime * result + ((getCourseData() == null) ? 0 : getCourseData().hashCode());
        result = prime * result + ((getCourseDataOthers() == null) ? 0 : getCourseDataOthers().hashCode());
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
        sb.append(", coursePoints=").append(coursePoints);
        sb.append(", courseClass=").append(courseClass);
        sb.append(", courseTime=").append(courseTime);
        sb.append(", courseName=").append(courseName);
        sb.append(", courseAddress=").append(courseAddress);
        sb.append(", courseMajor=").append(courseMajor);
        sb.append(", courseTeacherId=").append(courseTeacherId);
        sb.append(", courseTeacherName=").append(courseTeacherName);
        sb.append(", courseType=").append(courseType);
        sb.append(", courseCapacity=").append(courseCapacity);
        sb.append(", courseCapacityFactor=").append(courseCapacityFactor);
        sb.append(", courseHours=").append(courseHours);
        sb.append(", courseHoursTheory=").append(courseHoursTheory);
        sb.append(", courseHoursExp=").append(courseHoursExp);
        sb.append(", courseHoursStd=").append(courseHoursStd);
        sb.append(", courseData=").append(courseData);
        sb.append(", courseDataOthers=").append(courseDataOthers);
        sb.append(", courseProperties=").append(courseProperties);
        sb.append(", courseNote1=").append(courseNote1);
        sb.append(", courseNote2=").append(courseNote2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}