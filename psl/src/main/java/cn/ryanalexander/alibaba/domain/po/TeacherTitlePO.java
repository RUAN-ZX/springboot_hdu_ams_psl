package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.TitleInfo;
import cn.ryanalexander.alibaba.service.tool.ExcelDataProcessUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName teacher_title
 */
@TableName(value ="teacher_title")
@Data
public class TeacherTitlePO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer titleId;

    /**
     * 
     */
    private Integer titleTeacherId;

    /**
     * 
     */
    private String titleTeacherName;

    /**
     * 
     */
    private String titleTeam;

    /**
     * 
     */
    private String titleType;

    /**
     * 
     */
    private String titleName;

    /**
     * 
     */
    private Integer titleLevel;

    /**
     * 
     */
    private Integer titleYear;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public TeacherTitlePO(TitleInfo titleInfo){
        titleTeacherName = titleInfo.getTitleTeacherName();
        titleTeacherId = titleInfo.getTitleTeacherId();

        titleLevel = ExcelDataProcessUtil.transformTitleGrade(titleInfo.getTitleLevel());
        titleType = titleInfo.getTitleType();
        titleName = titleInfo.getTitleName();

        titleTeam = titleInfo.getTitleTeam();
        titleYear = titleInfo.getTitleYear();

    }

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
        TeacherTitlePO other = (TeacherTitlePO) that;
        return (this.getTitleId() == null ? other.getTitleId() == null : this.getTitleId().equals(other.getTitleId()))
            && (this.getTitleTeacherId() == null ? other.getTitleTeacherId() == null : this.getTitleTeacherId().equals(other.getTitleTeacherId()))
            && (this.getTitleTeacherName() == null ? other.getTitleTeacherName() == null : this.getTitleTeacherName().equals(other.getTitleTeacherName()))
            && (this.getTitleTeam() == null ? other.getTitleTeam() == null : this.getTitleTeam().equals(other.getTitleTeam()))
            && (this.getTitleType() == null ? other.getTitleType() == null : this.getTitleType().equals(other.getTitleType()))
            && (this.getTitleName() == null ? other.getTitleName() == null : this.getTitleName().equals(other.getTitleName()))
            && (this.getTitleLevel() == null ? other.getTitleLevel() == null : this.getTitleLevel().equals(other.getTitleLevel()))
            && (this.getTitleYear() == null ? other.getTitleYear() == null : this.getTitleYear().equals(other.getTitleYear()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTitleId() == null) ? 0 : getTitleId().hashCode());
        result = prime * result + ((getTitleTeacherId() == null) ? 0 : getTitleTeacherId().hashCode());
        result = prime * result + ((getTitleTeacherName() == null) ? 0 : getTitleTeacherName().hashCode());
        result = prime * result + ((getTitleTeam() == null) ? 0 : getTitleTeam().hashCode());
        result = prime * result + ((getTitleType() == null) ? 0 : getTitleType().hashCode());
        result = prime * result + ((getTitleName() == null) ? 0 : getTitleName().hashCode());
        result = prime * result + ((getTitleLevel() == null) ? 0 : getTitleLevel().hashCode());
        result = prime * result + ((getTitleYear() == null) ? 0 : getTitleYear().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", titleId=").append(titleId);
        sb.append(", titleTeacherId=").append(titleTeacherId);
        sb.append(", titleTeacherName=").append(titleTeacherName);
        sb.append(", titleTeam=").append(titleTeam);
        sb.append(", titleType=").append(titleType);
        sb.append(", titleName=").append(titleName);
        sb.append(", titleLevel=").append(titleLevel);
        sb.append(", titleYear=").append(titleYear);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}