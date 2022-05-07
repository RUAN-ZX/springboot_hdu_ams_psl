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
 * @TableName sprite_record
 */
@TableName(value ="sprite_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpriteRecordPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer spriteRecordId;

    /**
     * 
     */
    private Integer spriteRecordUserId;

    /**
     * 
     */
    private Integer spriteRecordSpriteId;

    /**
     * 
     */
    private Integer spriteRecordHealth;

    /**
     * 
     */
    private Integer spriteRecordExperience;

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
        SpriteRecordPO other = (SpriteRecordPO) that;
        return (this.getSpriteRecordId() == null ? other.getSpriteRecordId() == null : this.getSpriteRecordId().equals(other.getSpriteRecordId()))
            && (this.getSpriteRecordUserId() == null ? other.getSpriteRecordUserId() == null : this.getSpriteRecordUserId().equals(other.getSpriteRecordUserId()))
            && (this.getSpriteRecordSpriteId() == null ? other.getSpriteRecordSpriteId() == null : this.getSpriteRecordSpriteId().equals(other.getSpriteRecordSpriteId()))
            && (this.getSpriteRecordHealth() == null ? other.getSpriteRecordHealth() == null : this.getSpriteRecordHealth().equals(other.getSpriteRecordHealth()))
            && (this.getSpriteRecordExperience() == null ? other.getSpriteRecordExperience() == null : this.getSpriteRecordExperience().equals(other.getSpriteRecordExperience()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSpriteRecordId() == null) ? 0 : getSpriteRecordId().hashCode());
        result = prime * result + ((getSpriteRecordUserId() == null) ? 0 : getSpriteRecordUserId().hashCode());
        result = prime * result + ((getSpriteRecordSpriteId() == null) ? 0 : getSpriteRecordSpriteId().hashCode());
        result = prime * result + ((getSpriteRecordHealth() == null) ? 0 : getSpriteRecordHealth().hashCode());
        result = prime * result + ((getSpriteRecordExperience() == null) ? 0 : getSpriteRecordExperience().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", spriteRecordId=").append(spriteRecordId);
        sb.append(", spriteRecordUserId=").append(spriteRecordUserId);
        sb.append(", spriteRecordSpriteId=").append(spriteRecordSpriteId);
        sb.append(", spriteRecordHealth=").append(spriteRecordHealth);
        sb.append(", spriteRecordExperience=").append(spriteRecordExperience);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}