package cn.ryanalexander.sst.domain.po;

import cn.ryanalexander.sst.domain.dto.MissionDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName mission
 */
@TableName(value ="mission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissionPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer missionId;

    private Integer missionQuestionId;

    /**
     * 
     */
    private Integer missionTeacherId;

    /**
     * 
     */
    private Integer missionStudentId;

    /**
     * 
     */
    private Integer missionClassId;

    /**
     * 
     */
    private Integer missionSubjectId;

    /**
     * 
     */
    private Integer missionIsFinished;

    /**
     * 
     */
    private Date missionTimeFinished;

    /**
     * 
     */
    private Date missionTimeStart;

    /**
     * 
     */
    private Date missionTimeEnd;

    /**
     * 
     */
    private String missionDescription;

    public MissionPO(MissionDTO missionDTO){

        this.missionDescription = missionDTO.getMissionDescription();

        this.missionIsFinished = 0;

        this.missionQuestionId = missionDTO.getMissionQuestionId();
        this.missionSubjectId = missionDTO.getMissionSubjectId();
        this.missionClassId = missionDTO.getMissionClassId();

        this.missionTimeStart = missionDTO.getMissionTimeStart();
        this.missionTimeEnd = missionDTO.getMissionTimeEnd();
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
        MissionPO other = (MissionPO) that;
        return (this.getMissionId() == null ? other.getMissionId() == null : this.getMissionId().equals(other.getMissionId()))
            && (this.getMissionQuestionId() == null ? other.getMissionQuestionId() == null : this.getMissionQuestionId().equals(other.getMissionQuestionId()))
            && (this.getMissionTeacherId() == null ? other.getMissionTeacherId() == null : this.getMissionTeacherId().equals(other.getMissionTeacherId()))
            && (this.getMissionStudentId() == null ? other.getMissionStudentId() == null : this.getMissionStudentId().equals(other.getMissionStudentId()))
            && (this.getMissionClassId() == null ? other.getMissionClassId() == null : this.getMissionClassId().equals(other.getMissionClassId()))
            && (this.getMissionSubjectId() == null ? other.getMissionSubjectId() == null : this.getMissionSubjectId().equals(other.getMissionSubjectId()))
            && (this.getMissionIsFinished() == null ? other.getMissionIsFinished() == null : this.getMissionIsFinished().equals(other.getMissionIsFinished()))
            && (this.getMissionTimeFinished() == null ? other.getMissionTimeFinished() == null : this.getMissionTimeFinished().equals(other.getMissionTimeFinished()))
            && (this.getMissionTimeStart() == null ? other.getMissionTimeStart() == null : this.getMissionTimeStart().equals(other.getMissionTimeStart()))
            && (this.getMissionTimeEnd() == null ? other.getMissionTimeEnd() == null : this.getMissionTimeEnd().equals(other.getMissionTimeEnd()))
            && (this.getMissionDescription() == null ? other.getMissionDescription() == null : this.getMissionDescription().equals(other.getMissionDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMissionId() == null) ? 0 : getMissionId().hashCode());
        result = prime * result + ((getMissionQuestionId() == null) ? 0 : getMissionQuestionId().hashCode());
        result = prime * result + ((getMissionTeacherId() == null) ? 0 : getMissionTeacherId().hashCode());
        result = prime * result + ((getMissionStudentId() == null) ? 0 : getMissionStudentId().hashCode());
        result = prime * result + ((getMissionClassId() == null) ? 0 : getMissionClassId().hashCode());
        result = prime * result + ((getMissionSubjectId() == null) ? 0 : getMissionSubjectId().hashCode());
        result = prime * result + ((getMissionIsFinished() == null) ? 0 : getMissionIsFinished().hashCode());
        result = prime * result + ((getMissionTimeFinished() == null) ? 0 : getMissionTimeFinished().hashCode());
        result = prime * result + ((getMissionTimeStart() == null) ? 0 : getMissionTimeStart().hashCode());
        result = prime * result + ((getMissionTimeEnd() == null) ? 0 : getMissionTimeEnd().hashCode());
        result = prime * result + ((getMissionDescription() == null) ? 0 : getMissionDescription().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", missionId=").append(missionId);
        sb.append(", missionQuestionId=").append(missionQuestionId);
        sb.append(", missionTeacherId=").append(missionTeacherId);
        sb.append(", missionStudentId=").append(missionStudentId);
        sb.append(", missionClassId=").append(missionClassId);
        sb.append(", missionSubjectId=").append(missionSubjectId);
        sb.append(", missionIsFinished=").append(missionIsFinished);
        sb.append(", missionTimeFinished=").append(missionTimeFinished);
        sb.append(", missionTimeStart=").append(missionTimeStart);
        sb.append(", missionTimeEnd=").append(missionTimeEnd);
        sb.append(", missionDescription=").append(missionDescription);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}