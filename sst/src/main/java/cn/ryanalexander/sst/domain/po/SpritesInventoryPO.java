package cn.ryanalexander.sst.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName sprites_inventory
 */
@TableName(value ="sprites_inventory")
@Data
public class SpritesInventoryPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer spritesInventoryId;

    /**
     * 
     */
    private Integer spritesInventoryUserId;

    /**
     * 
     */
    private String spritesInventorySpriteName;

    /**
     * 
     */
    private Object spritesInventoryLevel;

    /**
     * 
     */
    private Object spritesInventoryAttack;

    /**
     * 
     */
    private Object spritesInventoryDefence;

    /**
     * 
     */
    private Object spritesInventoryFullHealth;

    /**
     * 
     */
    private Object spritesInventoryTrueHealth;

    /**
     * 
     */
    private Object spritesInventoryFullExperience;

    /**
     * 
     */
    private Object spritesInventoryTrueExperience;

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
        SpritesInventoryPO other = (SpritesInventoryPO) that;
        return (this.getSpritesInventoryId() == null ? other.getSpritesInventoryId() == null : this.getSpritesInventoryId().equals(other.getSpritesInventoryId()))
            && (this.getSpritesInventoryUserId() == null ? other.getSpritesInventoryUserId() == null : this.getSpritesInventoryUserId().equals(other.getSpritesInventoryUserId()))
            && (this.getSpritesInventorySpriteName() == null ? other.getSpritesInventorySpriteName() == null : this.getSpritesInventorySpriteName().equals(other.getSpritesInventorySpriteName()))
            && (this.getSpritesInventoryLevel() == null ? other.getSpritesInventoryLevel() == null : this.getSpritesInventoryLevel().equals(other.getSpritesInventoryLevel()))
            && (this.getSpritesInventoryAttack() == null ? other.getSpritesInventoryAttack() == null : this.getSpritesInventoryAttack().equals(other.getSpritesInventoryAttack()))
            && (this.getSpritesInventoryDefence() == null ? other.getSpritesInventoryDefence() == null : this.getSpritesInventoryDefence().equals(other.getSpritesInventoryDefence()))
            && (this.getSpritesInventoryFullHealth() == null ? other.getSpritesInventoryFullHealth() == null : this.getSpritesInventoryFullHealth().equals(other.getSpritesInventoryFullHealth()))
            && (this.getSpritesInventoryTrueHealth() == null ? other.getSpritesInventoryTrueHealth() == null : this.getSpritesInventoryTrueHealth().equals(other.getSpritesInventoryTrueHealth()))
            && (this.getSpritesInventoryFullExperience() == null ? other.getSpritesInventoryFullExperience() == null : this.getSpritesInventoryFullExperience().equals(other.getSpritesInventoryFullExperience()))
            && (this.getSpritesInventoryTrueExperience() == null ? other.getSpritesInventoryTrueExperience() == null : this.getSpritesInventoryTrueExperience().equals(other.getSpritesInventoryTrueExperience()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSpritesInventoryId() == null) ? 0 : getSpritesInventoryId().hashCode());
        result = prime * result + ((getSpritesInventoryUserId() == null) ? 0 : getSpritesInventoryUserId().hashCode());
        result = prime * result + ((getSpritesInventorySpriteName() == null) ? 0 : getSpritesInventorySpriteName().hashCode());
        result = prime * result + ((getSpritesInventoryLevel() == null) ? 0 : getSpritesInventoryLevel().hashCode());
        result = prime * result + ((getSpritesInventoryAttack() == null) ? 0 : getSpritesInventoryAttack().hashCode());
        result = prime * result + ((getSpritesInventoryDefence() == null) ? 0 : getSpritesInventoryDefence().hashCode());
        result = prime * result + ((getSpritesInventoryFullHealth() == null) ? 0 : getSpritesInventoryFullHealth().hashCode());
        result = prime * result + ((getSpritesInventoryTrueHealth() == null) ? 0 : getSpritesInventoryTrueHealth().hashCode());
        result = prime * result + ((getSpritesInventoryFullExperience() == null) ? 0 : getSpritesInventoryFullExperience().hashCode());
        result = prime * result + ((getSpritesInventoryTrueExperience() == null) ? 0 : getSpritesInventoryTrueExperience().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", spritesInventoryId=").append(spritesInventoryId);
        sb.append(", spritesInventoryUserId=").append(spritesInventoryUserId);
        sb.append(", spritesInventorySpriteName=").append(spritesInventorySpriteName);
        sb.append(", spritesInventoryLevel=").append(spritesInventoryLevel);
        sb.append(", spritesInventoryAttack=").append(spritesInventoryAttack);
        sb.append(", spritesInventoryDefence=").append(spritesInventoryDefence);
        sb.append(", spritesInventoryFullHealth=").append(spritesInventoryFullHealth);
        sb.append(", spritesInventoryTrueHealth=").append(spritesInventoryTrueHealth);
        sb.append(", spritesInventoryFullExperience=").append(spritesInventoryFullExperience);
        sb.append(", spritesInventoryTrueExperience=").append(spritesInventoryTrueExperience);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}