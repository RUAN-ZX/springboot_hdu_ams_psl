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
public class CourseUnionPO implements Serializable, Cloneable{
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
    private String courseProperty;

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
        this.courseCapacityFactor = course.getCourseCapacityFactor();
        this.courseHours = course.getCourseHours();
        this.courseProperty = String.valueOf(course.getCourseProperty());

        this.courseData = course.getCourseData();
    }

    @Override
    public CourseUnionPO clone() {
        try {
            CourseUnionPO clone = (CourseUnionPO) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}