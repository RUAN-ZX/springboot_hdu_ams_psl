package cn.ryanalexander.sst.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName class
 */
@TableName(value ="class")
@Data
public class ClassPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer classId;

    /**
     * 
     */
    private Object classTeacherId;

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
        ClassPO other = (ClassPO) that;
        return (this.getClassId() == null ? other.getClassId() == null : this.getClassId().equals(other.getClassId()))
            && (this.getClassTeacherId() == null ? other.getClassTeacherId() == null : this.getClassTeacherId().equals(other.getClassTeacherId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getClassId() == null) ? 0 : getClassId().hashCode());
        result = prime * result + ((getClassTeacherId() == null) ? 0 : getClassTeacherId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", classId=").append(classId);
        sb.append(", classTeacherId=").append(classTeacherId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}