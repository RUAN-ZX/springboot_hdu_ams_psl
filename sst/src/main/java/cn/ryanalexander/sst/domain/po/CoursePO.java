package cn.ryanalexander.sst.domain.po;

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
 * @TableName course
 */
@TableName(value ="course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer courseId;

    /**
     * 
     */
    private Integer courseUserId;

    /**
     * 
     */
    private String courseClass1;

    /**
     * 
     */
    private String courseClass2;

    /**
     * 
     */
    private String courseClass3;

    /**
     * 
     */
    private String courseClass4;

    /**
     * 
     */
    private String courseClass5;

    /**
     * 
     */
    private String courseClass6;

    /**
     * 
     */
    private String courseClass7;

    /**
     * 
     */
    private String courseClass8;

    /**
     * 
     */
    private Integer courseDayWeek;

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
        CoursePO other = (CoursePO) that;
        return (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
            && (this.getCourseUserId() == null ? other.getCourseUserId() == null : this.getCourseUserId().equals(other.getCourseUserId()))
            && (this.getCourseClass1() == null ? other.getCourseClass1() == null : this.getCourseClass1().equals(other.getCourseClass1()))
            && (this.getCourseClass2() == null ? other.getCourseClass2() == null : this.getCourseClass2().equals(other.getCourseClass2()))
            && (this.getCourseClass3() == null ? other.getCourseClass3() == null : this.getCourseClass3().equals(other.getCourseClass3()))
            && (this.getCourseClass4() == null ? other.getCourseClass4() == null : this.getCourseClass4().equals(other.getCourseClass4()))
            && (this.getCourseClass5() == null ? other.getCourseClass5() == null : this.getCourseClass5().equals(other.getCourseClass5()))
            && (this.getCourseClass6() == null ? other.getCourseClass6() == null : this.getCourseClass6().equals(other.getCourseClass6()))
            && (this.getCourseClass7() == null ? other.getCourseClass7() == null : this.getCourseClass7().equals(other.getCourseClass7()))
            && (this.getCourseClass8() == null ? other.getCourseClass8() == null : this.getCourseClass8().equals(other.getCourseClass8()))
            && (this.getCourseDayWeek() == null ? other.getCourseDayWeek() == null : this.getCourseDayWeek().equals(other.getCourseDayWeek()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getCourseUserId() == null) ? 0 : getCourseUserId().hashCode());
        result = prime * result + ((getCourseClass1() == null) ? 0 : getCourseClass1().hashCode());
        result = prime * result + ((getCourseClass2() == null) ? 0 : getCourseClass2().hashCode());
        result = prime * result + ((getCourseClass3() == null) ? 0 : getCourseClass3().hashCode());
        result = prime * result + ((getCourseClass4() == null) ? 0 : getCourseClass4().hashCode());
        result = prime * result + ((getCourseClass5() == null) ? 0 : getCourseClass5().hashCode());
        result = prime * result + ((getCourseClass6() == null) ? 0 : getCourseClass6().hashCode());
        result = prime * result + ((getCourseClass7() == null) ? 0 : getCourseClass7().hashCode());
        result = prime * result + ((getCourseClass8() == null) ? 0 : getCourseClass8().hashCode());
        result = prime * result + ((getCourseDayWeek() == null) ? 0 : getCourseDayWeek().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", courseId=").append(courseId);
        sb.append(", courseUserId=").append(courseUserId);
        sb.append(", courseClass1=").append(courseClass1);
        sb.append(", courseClass2=").append(courseClass2);
        sb.append(", courseClass3=").append(courseClass3);
        sb.append(", courseClass4=").append(courseClass4);
        sb.append(", courseClass5=").append(courseClass5);
        sb.append(", courseClass6=").append(courseClass6);
        sb.append(", courseClass7=").append(courseClass7);
        sb.append(", courseClass8=").append(courseClass8);
        sb.append(", courseDayWeek=").append(courseDayWeek);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}