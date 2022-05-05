package cn.ryanalexander.sst.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName sprite
 */
@TableName(value ="sprite")
@Data
public class SpritePO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer spriteId;

    /**
     * 
     */
    private String spriteName;

    /**
     * 
     */
    private Integer spriteAttack;

    /**
     * 
     */
    private Integer spriteDefence;

    /**
     * 
     */
    private Integer spriteHealth;

    /**
     * 
     */
    private Integer spriteExperience;

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
        SpritePO other = (SpritePO) that;
        return (this.getSpriteId() == null ? other.getSpriteId() == null : this.getSpriteId().equals(other.getSpriteId()))
            && (this.getSpriteName() == null ? other.getSpriteName() == null : this.getSpriteName().equals(other.getSpriteName()))
            && (this.getSpriteAttack() == null ? other.getSpriteAttack() == null : this.getSpriteAttack().equals(other.getSpriteAttack()))
            && (this.getSpriteDefence() == null ? other.getSpriteDefence() == null : this.getSpriteDefence().equals(other.getSpriteDefence()))
            && (this.getSpriteHealth() == null ? other.getSpriteHealth() == null : this.getSpriteHealth().equals(other.getSpriteHealth()))
            && (this.getSpriteExperience() == null ? other.getSpriteExperience() == null : this.getSpriteExperience().equals(other.getSpriteExperience()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSpriteId() == null) ? 0 : getSpriteId().hashCode());
        result = prime * result + ((getSpriteName() == null) ? 0 : getSpriteName().hashCode());
        result = prime * result + ((getSpriteAttack() == null) ? 0 : getSpriteAttack().hashCode());
        result = prime * result + ((getSpriteDefence() == null) ? 0 : getSpriteDefence().hashCode());
        result = prime * result + ((getSpriteHealth() == null) ? 0 : getSpriteHealth().hashCode());
        result = prime * result + ((getSpriteExperience() == null) ? 0 : getSpriteExperience().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", spriteId=").append(spriteId);
        sb.append(", spriteName=").append(spriteName);
        sb.append(", spriteAttack=").append(spriteAttack);
        sb.append(", spriteDefence=").append(spriteDefence);
        sb.append(", spriteHealth=").append(spriteHealth);
        sb.append(", spriteExperience=").append(spriteExperience);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}