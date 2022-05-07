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
 * @TableName item_inventory
 */
@TableName(value ="item_inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemInventoryPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer itemInventoryId;

    /**
     * 
     */
    private Integer itemInventoryUserId;

    /**
     * 
     */
    private String itemInventoryItemName;

    /**
     * 
     */
    private Integer itemInventoryItemNum;

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
        ItemInventoryPO other = (ItemInventoryPO) that;
        return (this.getItemInventoryId() == null ? other.getItemInventoryId() == null : this.getItemInventoryId().equals(other.getItemInventoryId()))
            && (this.getItemInventoryUserId() == null ? other.getItemInventoryUserId() == null : this.getItemInventoryUserId().equals(other.getItemInventoryUserId()))
            && (this.getItemInventoryItemName() == null ? other.getItemInventoryItemName() == null : this.getItemInventoryItemName().equals(other.getItemInventoryItemName()))
            && (this.getItemInventoryItemNum() == null ? other.getItemInventoryItemNum() == null : this.getItemInventoryItemNum().equals(other.getItemInventoryItemNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getItemInventoryId() == null) ? 0 : getItemInventoryId().hashCode());
        result = prime * result + ((getItemInventoryUserId() == null) ? 0 : getItemInventoryUserId().hashCode());
        result = prime * result + ((getItemInventoryItemName() == null) ? 0 : getItemInventoryItemName().hashCode());
        result = prime * result + ((getItemInventoryItemNum() == null) ? 0 : getItemInventoryItemNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", itemInventoryId=").append(itemInventoryId);
        sb.append(", itemInventoryUserId=").append(itemInventoryUserId);
        sb.append(", itemInventoryItemName=").append(itemInventoryItemName);
        sb.append(", itemInventoryItemNum=").append(itemInventoryItemNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}