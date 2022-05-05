package cn.ryanalexander.sst.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @TableName record
 */
@TableName(value ="record")
@Data
@AllArgsConstructor
public class RecordPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer recordId;

    /**
     * 
     */
    private Integer recordStudentId;

    /**
     * 
     */
    private Integer recordClassId;

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
        RecordPO other = (RecordPO) that;
        return (this.getRecordId() == null ? other.getRecordId() == null : this.getRecordId().equals(other.getRecordId()))
            && (this.getRecordStudentId() == null ? other.getRecordStudentId() == null : this.getRecordStudentId().equals(other.getRecordStudentId()))
            && (this.getRecordClassId() == null ? other.getRecordClassId() == null : this.getRecordClassId().equals(other.getRecordClassId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRecordId() == null) ? 0 : getRecordId().hashCode());
        result = prime * result + ((getRecordStudentId() == null) ? 0 : getRecordStudentId().hashCode());
        result = prime * result + ((getRecordClassId() == null) ? 0 : getRecordClassId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", recordId=").append(recordId);
        sb.append(", recordStudentId=").append(recordStudentId);
        sb.append(", recordClassId=").append(recordClassId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}