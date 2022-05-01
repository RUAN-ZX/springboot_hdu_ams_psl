package cn.ryanalexander.sst.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName subject
 */
@TableName(value ="subject")
@Data
public class SubjectPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer subjectId;

    /**
     * 
     */
    private String subjectName;

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
        SubjectPO other = (SubjectPO) that;
        return (this.getSubjectId() == null ? other.getSubjectId() == null : this.getSubjectId().equals(other.getSubjectId()))
            && (this.getSubjectName() == null ? other.getSubjectName() == null : this.getSubjectName().equals(other.getSubjectName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSubjectId() == null) ? 0 : getSubjectId().hashCode());
        result = prime * result + ((getSubjectName() == null) ? 0 : getSubjectName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", subjectId=").append(subjectId);
        sb.append(", subjectName=").append(subjectName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}