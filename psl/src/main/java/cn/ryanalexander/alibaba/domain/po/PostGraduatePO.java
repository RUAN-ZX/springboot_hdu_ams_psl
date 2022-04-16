package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.S1PostGraduate;
import cn.ryanalexander.alibaba.domain.bo.excel.out.S1Workload;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName post_graduate
 */
@TableName(value ="post_graduate")
@Data
public class PostGraduatePO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer postGraduateId;

    /**
     * 
     */
    private Integer postGraduateYear;

    /**
     * 
     */
    private Integer postGraduateTeacherId;

    /**
     * 
     */
    private String postGraduateTeacherName;

    /**
     * 
     */
    private Double postGraduateKpi;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    public PostGraduatePO(S1PostGraduate s1PostGraduate) {
        postGraduateKpi = s1PostGraduate.getPostGraduateKpi();
        postGraduateTeacherId = s1PostGraduate.getPostGraduateTeacherId();
        postGraduateYear = s1PostGraduate.getPostGraduateYear();
        postGraduateTeacherName = s1PostGraduate.getPostGraduateTeacherName();
    }

    public PostGraduatePO(S1Workload s1Workload) {
        postGraduateKpi = s1Workload.getPostGraduateHours() / 100.0;
        postGraduateTeacherId = s1Workload.getTeacherId();
        postGraduateYear = s1Workload.getSYear();
        postGraduateTeacherName = s1Workload.getTeacherName();
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
        PostGraduatePO other = (PostGraduatePO) that;
        return (this.getPostGraduateId() == null ? other.getPostGraduateId() == null : this.getPostGraduateId().equals(other.getPostGraduateId()))
            && (this.getPostGraduateYear() == null ? other.getPostGraduateYear() == null : this.getPostGraduateYear().equals(other.getPostGraduateYear()))
            && (this.getPostGraduateTeacherId() == null ? other.getPostGraduateTeacherId() == null : this.getPostGraduateTeacherId().equals(other.getPostGraduateTeacherId()))
            && (this.getPostGraduateTeacherName() == null ? other.getPostGraduateTeacherName() == null : this.getPostGraduateTeacherName().equals(other.getPostGraduateTeacherName()))
            && (this.getPostGraduateKpi() == null ? other.getPostGraduateKpi() == null : this.getPostGraduateKpi().equals(other.getPostGraduateKpi()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPostGraduateId() == null) ? 0 : getPostGraduateId().hashCode());
        result = prime * result + ((getPostGraduateYear() == null) ? 0 : getPostGraduateYear().hashCode());
        result = prime * result + ((getPostGraduateTeacherId() == null) ? 0 : getPostGraduateTeacherId().hashCode());
        result = prime * result + ((getPostGraduateTeacherName() == null) ? 0 : getPostGraduateTeacherName().hashCode());
        result = prime * result + ((getPostGraduateKpi() == null) ? 0 : getPostGraduateKpi().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", postGraduateId=").append(postGraduateId);
        sb.append(", postGraduateYear=").append(postGraduateYear);
        sb.append(", postGraduateTeacherId=").append(postGraduateTeacherId);
        sb.append(", postGraduateTeacherName=").append(postGraduateTeacherName);
        sb.append(", postGraduateKpi=").append(postGraduateKpi);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}