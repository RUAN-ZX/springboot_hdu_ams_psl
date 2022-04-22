package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.S1Achievement;
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
 * @TableName achievement
 */
@TableName(value ="achievement")
@Data
@NoArgsConstructor
@AllArgsConstructor
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


    private String achievementEvidence1;
    private String achievementEvidence2;
    private String achievementEvidence3;


    private Double achievementKpiS34;
    private String achievementKpiCategory;

    private Integer achievementRank;
    private Double achievementKpi;
    private String achievementNote;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    public AchievementPO(S1Achievement s1Achievement){
        achievementCategory = s1Achievement.getAchievementCategory();
        achievementName = s1Achievement.getAchievementName();
        achievementKpi = s1Achievement.getAchievementKpi();
        achievementLevel = s1Achievement.getAchievementLevel();
        achievementNote = s1Achievement.getAchievementNote();
        achievementRank = s1Achievement.getAchievementRank();
        achievementTeacherId = s1Achievement.getAchievementTeacherId();
        achievementTeacherName = s1Achievement.getAchievementTeacherName();
//        achievementType = s1Achievement.getAchievementType();
        achievementYear = s1Achievement.getAchievementYear();
        achievementKpiS34 = s1Achievement.getAchievementKpiS34();
        achievementKpiCategory = s1Achievement.getAchievementKpiCategory();
    }
}