package cn.ryanalexander.psl.domain.po;

import cn.ryanalexander.psl.domain.bo.excel.out.SFinal;
import cn.ryanalexander.psl.service.tool.DataUtil;
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
 * @TableName s_final
 */
@TableName(value ="s_final")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SFinalPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer sFinalId;

    /**
     * 
     */
    private String sFinalTeacherName;

    /**
     * 
     */
    private Integer sFinalTeacherId;

    /**
     * 
     */
    private Double sFinalScore;

    /**
     * 
     */
    private Integer sFinalYear;

    /**
     * 
     */
    private String sFinalGrade;

    /**
     * 
     */
    private Integer sFinalTitleLevel;

    /**
     * 
     */
    private Integer sFinalCourseMain;

    /**
     * 
     */
    private String sFinalNote;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public SFinalPO(SFinal sFinal){
        sFinalTeacherId = DataUtil.string2integer(sFinal.getSFinalTeacherId());
        sFinalTeacherName = sFinal.getSFinalTeacherName();
        sFinalYear = sFinal.getSFinalYear();
        sFinalNote = sFinal.getSNote();
        sFinalCourseMain = DataUtil.string2integer(sFinal.getSFinalCourseMain());
        sFinalGrade = sFinal.getSFinalGrade();
        sFinalScore = DataUtil.string2double(sFinal.getSFinalScore());
        sFinalTitleLevel = DataUtil.transformTitleGrade(sFinal.getSFinalTitleLevel());
    }
}