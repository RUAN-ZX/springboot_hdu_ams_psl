package cn.ryanalexander.sst.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName sprite_inventory
 */
@TableName(value ="sprite_inventory")
@Data
public class SpriteInventoryPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer spriteInventoryId;

    /**
     * 
     */
    private Integer spriteInventoryUserId;

    /**
     * 
     */
    private String spriteInventorySpriteName;

    /**
     * 
     */
    private Integer spriteInventoryLevel;

    /**
     * 
     */
    private Integer spriteInventoryAttack;

    /**
     * 
     */
    private Integer spriteInventoryDefence;

    /**
     * 
     */
    private Integer spriteInventoryFullHealth;

    /**
     * 
     */
    private Integer spriteInventoryTrueHealth;

    /**
     * 
     */
    private Integer spriteInventoryFullExperience;

    /**
     * 
     */
    private Integer spriteInventoryTrueExperience;

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
        SpriteInventoryPO other = (SpriteInventoryPO) that;
        return (this.getSpriteInventoryId() == null ? other.getSpriteInventoryId() == null : this.getSpriteInventoryId().equals(other.getSpriteInventoryId()))
            && (this.getSpriteInventoryUserId() == null ? other.getSpriteInventoryUserId() == null : this.getSpriteInventoryUserId().equals(other.getSpriteInventoryUserId()))
            && (this.getSpriteInventorySpriteName() == null ? other.getSpriteInventorySpriteName() == null : this.getSpriteInventorySpriteName().equals(other.getSpriteInventorySpriteName()))
            && (this.getSpriteInventoryLevel() == null ? other.getSpriteInventoryLevel() == null : this.getSpriteInventoryLevel().equals(other.getSpriteInventoryLevel()))
            && (this.getSpriteInventoryAttack() == null ? other.getSpriteInventoryAttack() == null : this.getSpriteInventoryAttack().equals(other.getSpriteInventoryAttack()))
            && (this.getSpriteInventoryDefence() == null ? other.getSpriteInventoryDefence() == null : this.getSpriteInventoryDefence().equals(other.getSpriteInventoryDefence()))
            && (this.getSpriteInventoryFullHealth() == null ? other.getSpriteInventoryFullHealth() == null : this.getSpriteInventoryFullHealth().equals(other.getSpriteInventoryFullHealth()))
            && (this.getSpriteInventoryTrueHealth() == null ? other.getSpriteInventoryTrueHealth() == null : this.getSpriteInventoryTrueHealth().equals(other.getSpriteInventoryTrueHealth()))
            && (this.getSpriteInventoryFullExperience() == null ? other.getSpriteInventoryFullExperience() == null : this.getSpriteInventoryFullExperience().equals(other.getSpriteInventoryFullExperience()))
            && (this.getSpriteInventoryTrueExperience() == null ? other.getSpriteInventoryTrueExperience() == null : this.getSpriteInventoryTrueExperience().equals(other.getSpriteInventoryTrueExperience()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSpriteInventoryId() == null) ? 0 : getSpriteInventoryId().hashCode());
        result = prime * result + ((getSpriteInventoryUserId() == null) ? 0 : getSpriteInventoryUserId().hashCode());
        result = prime * result + ((getSpriteInventorySpriteName() == null) ? 0 : getSpriteInventorySpriteName().hashCode());
        result = prime * result + ((getSpriteInventoryLevel() == null) ? 0 : getSpriteInventoryLevel().hashCode());
        result = prime * result + ((getSpriteInventoryAttack() == null) ? 0 : getSpriteInventoryAttack().hashCode());
        result = prime * result + ((getSpriteInventoryDefence() == null) ? 0 : getSpriteInventoryDefence().hashCode());
        result = prime * result + ((getSpriteInventoryFullHealth() == null) ? 0 : getSpriteInventoryFullHealth().hashCode());
        result = prime * result + ((getSpriteInventoryTrueHealth() == null) ? 0 : getSpriteInventoryTrueHealth().hashCode());
        result = prime * result + ((getSpriteInventoryFullExperience() == null) ? 0 : getSpriteInventoryFullExperience().hashCode());
        result = prime * result + ((getSpriteInventoryTrueExperience() == null) ? 0 : getSpriteInventoryTrueExperience().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", spriteInventoryId=").append(spriteInventoryId);
        sb.append(", spriteInventoryUserId=").append(spriteInventoryUserId);
        sb.append(", spriteInventorySpriteName=").append(spriteInventorySpriteName);
        sb.append(", spriteInventoryLevel=").append(spriteInventoryLevel);
        sb.append(", spriteInventoryAttack=").append(spriteInventoryAttack);
        sb.append(", spriteInventoryDefence=").append(spriteInventoryDefence);
        sb.append(", spriteInventoryFullHealth=").append(spriteInventoryFullHealth);
        sb.append(", spriteInventoryTrueHealth=").append(spriteInventoryTrueHealth);
        sb.append(", spriteInventoryFullExperience=").append(spriteInventoryFullExperience);
        sb.append(", spriteInventoryTrueExperience=").append(spriteInventoryTrueExperience);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}