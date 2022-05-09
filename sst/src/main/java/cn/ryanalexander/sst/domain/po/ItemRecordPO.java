package cn.ryanalexander.sst.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName item_record
 */
@TableName(value ="item_record")
@Data
public class ItemRecordPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer itemRecordId;

    /**
     * 
     */
    private Integer itemRecordUserId;

    /**
     * 
     */
    private String itemRecordItemName;

    /**
     * 
     */
    private Integer itemRecordItemNum;

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
        ItemRecordPO other = (ItemRecordPO) that;
        return (this.getItemRecordId() == null ? other.getItemRecordId() == null : this.getItemRecordId().equals(other.getItemRecordId()))
            && (this.getItemRecordUserId() == null ? other.getItemRecordUserId() == null : this.getItemRecordUserId().equals(other.getItemRecordUserId()))
            && (this.getItemRecordItemName() == null ? other.getItemRecordItemName() == null : this.getItemRecordItemName().equals(other.getItemRecordItemName()))
            && (this.getItemRecordItemNum() == null ? other.getItemRecordItemNum() == null : this.getItemRecordItemNum().equals(other.getItemRecordItemNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getItemRecordId() == null) ? 0 : getItemRecordId().hashCode());
        result = prime * result + ((getItemRecordUserId() == null) ? 0 : getItemRecordUserId().hashCode());
        result = prime * result + ((getItemRecordItemName() == null) ? 0 : getItemRecordItemName().hashCode());
        result = prime * result + ((getItemRecordItemNum() == null) ? 0 : getItemRecordItemNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", itemRecordId=").append(itemRecordId);
        sb.append(", itemRecordUserId=").append(itemRecordUserId);
        sb.append(", itemRecordItemName=").append(itemRecordItemName);
        sb.append(", itemRecordItemNum=").append(itemRecordItemNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}