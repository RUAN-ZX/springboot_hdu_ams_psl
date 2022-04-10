package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.S1Achievement;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName achievement
 */
@TableName(value ="achievement")
@Data
public class AchievementPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer achievementId;

    /**
     * 
     */
    private Integer achievementTeacherId;

    /**
     * 
     */
    private String achievementTeacherName;

    /**
     * 
     */
    private Integer achievementYear;

    /**
     * 
     */
    private String achievementName;

    /**
     * 
     */
    private Integer achievementType;

    /**
     * 
     */
    private String achievementLevel;

    /**
     * 
     */
    private String achievementCategory;

    /**
     * 
     */
    private String achievementEvidence1;

    /**
     * 
     */
    private String achievementEvidence2;

    /**
     * 
     */
    private String achievementEvidence3;

    /**
     * 
     */
    private Integer achievementRank;

    /**
     * 
     */
    private Double achievementKpi;

    /**
     * 
     */
    private String achievementNote;

    public AchievementPO(S1Achievement s1Achievement){
        achievementCategory = s1Achievement.getAchievementCategory();
        achievementName = s1Achievement.getAchievementName();
        achievementKpi = s1Achievement.getAchievementKpi();
        achievementLevel = s1Achievement.getAchievementLevel();
        achievementNote = s1Achievement.getAchievementNote();
        achievementRank = s1Achievement.getAchievementRank();
        achievementTeacherId = s1Achievement.getAchievementTeacherId();
        achievementTeacherName = s1Achievement.getAchievementTeacherName();
        achievementType = s1Achievement.getAchievementType();
        achievementYear = s1Achievement.getAchievementYear();

    }

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
        AchievementPO other = (AchievementPO) that;
        return (this.getAchievementId() == null ? other.getAchievementId() == null : this.getAchievementId().equals(other.getAchievementId()))
            && (this.getAchievementTeacherId() == null ? other.getAchievementTeacherId() == null : this.getAchievementTeacherId().equals(other.getAchievementTeacherId()))
            && (this.getAchievementTeacherName() == null ? other.getAchievementTeacherName() == null : this.getAchievementTeacherName().equals(other.getAchievementTeacherName()))
            && (this.getAchievementYear() == null ? other.getAchievementYear() == null : this.getAchievementYear().equals(other.getAchievementYear()))
            && (this.getAchievementName() == null ? other.getAchievementName() == null : this.getAchievementName().equals(other.getAchievementName()))
            && (this.getAchievementType() == null ? other.getAchievementType() == null : this.getAchievementType().equals(other.getAchievementType()))
            && (this.getAchievementLevel() == null ? other.getAchievementLevel() == null : this.getAchievementLevel().equals(other.getAchievementLevel()))
            && (this.getAchievementCategory() == null ? other.getAchievementCategory() == null : this.getAchievementCategory().equals(other.getAchievementCategory()))
            && (this.getAchievementEvidence1() == null ? other.getAchievementEvidence1() == null : this.getAchievementEvidence1().equals(other.getAchievementEvidence1()))
            && (this.getAchievementEvidence2() == null ? other.getAchievementEvidence2() == null : this.getAchievementEvidence2().equals(other.getAchievementEvidence2()))
            && (this.getAchievementEvidence3() == null ? other.getAchievementEvidence3() == null : this.getAchievementEvidence3().equals(other.getAchievementEvidence3()))
            && (this.getAchievementRank() == null ? other.getAchievementRank() == null : this.getAchievementRank().equals(other.getAchievementRank()))
            && (this.getAchievementKpi() == null ? other.getAchievementKpi() == null : this.getAchievementKpi().equals(other.getAchievementKpi()))
            && (this.getAchievementNote() == null ? other.getAchievementNote() == null : this.getAchievementNote().equals(other.getAchievementNote()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAchievementId() == null) ? 0 : getAchievementId().hashCode());
        result = prime * result + ((getAchievementTeacherId() == null) ? 0 : getAchievementTeacherId().hashCode());
        result = prime * result + ((getAchievementTeacherName() == null) ? 0 : getAchievementTeacherName().hashCode());
        result = prime * result + ((getAchievementYear() == null) ? 0 : getAchievementYear().hashCode());
        result = prime * result + ((getAchievementName() == null) ? 0 : getAchievementName().hashCode());
        result = prime * result + ((getAchievementType() == null) ? 0 : getAchievementType().hashCode());
        result = prime * result + ((getAchievementLevel() == null) ? 0 : getAchievementLevel().hashCode());
        result = prime * result + ((getAchievementCategory() == null) ? 0 : getAchievementCategory().hashCode());
        result = prime * result + ((getAchievementEvidence1() == null) ? 0 : getAchievementEvidence1().hashCode());
        result = prime * result + ((getAchievementEvidence2() == null) ? 0 : getAchievementEvidence2().hashCode());
        result = prime * result + ((getAchievementEvidence3() == null) ? 0 : getAchievementEvidence3().hashCode());
        result = prime * result + ((getAchievementRank() == null) ? 0 : getAchievementRank().hashCode());
        result = prime * result + ((getAchievementKpi() == null) ? 0 : getAchievementKpi().hashCode());
        result = prime * result + ((getAchievementNote() == null) ? 0 : getAchievementNote().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", achievementId=").append(achievementId);
        sb.append(", achievementTeacherId=").append(achievementTeacherId);
        sb.append(", achievementTeacherName=").append(achievementTeacherName);
        sb.append(", achievementYear=").append(achievementYear);
        sb.append(", achievementName=").append(achievementName);
        sb.append(", achievementType=").append(achievementType);
        sb.append(", achievementLevel=").append(achievementLevel);
        sb.append(", achievementCategory=").append(achievementCategory);
        sb.append(", achievementEvidence1=").append(achievementEvidence1);
        sb.append(", achievementEvidence2=").append(achievementEvidence2);
        sb.append(", achievementEvidence3=").append(achievementEvidence3);
        sb.append(", achievementRank=").append(achievementRank);
        sb.append(", achievementKpi=").append(achievementKpi);
        sb.append(", achievementNote=").append(achievementNote);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}