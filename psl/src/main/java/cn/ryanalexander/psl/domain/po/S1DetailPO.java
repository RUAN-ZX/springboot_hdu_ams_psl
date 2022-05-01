package cn.ryanalexander.psl.domain.po;

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
 * @TableName s1_detail
 */
@TableName(value ="s1_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class S1DetailPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer s1DetailId;

    /**
     * 
     */
    private Integer s1DetailTeacherId;

    /**
     * 
     */
    private String s1DetailTeacherName;

    /**
     * 
     */
    private Integer s1DetailYear;

    /**
     * 
     */
    private Double s1KpiCourseTheory;

    /**
     * 
     */
    private Double s1KpiCourseExperiment;

    /**
     * 
     */
    private Double s1KpiShortTerm;

    /**
     * 
     */
    private Double s1KpiThesisDesign;

    /**
     * 
     */
    private Double s1KpiAchievement1;

    /**
     * 
     */
    private Double s1KpiAchievement2;

    /**
     * 
     */
    private Double s1KpiSpecialAssignment;

    /**
     * 
     */
    private Double s1KpiShoulderBoth;

    /**
     * 
     */
    private Double s1KpiPostgraduate;

    /**
     * 
     */
    private Double s1Kpi;

    /**
     * 
     */
    private Double s1Score;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}