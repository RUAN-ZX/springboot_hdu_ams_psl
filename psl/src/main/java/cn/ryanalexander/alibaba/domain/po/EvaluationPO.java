package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.S2Evaluation;
import com.alibaba.excel.annotation.ExcelProperty;
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
 * @TableName evaluation
 */
@TableName(value ="evaluation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer evaluationId;

    @ExcelProperty
    private String evaluationTeacherName;

    @ExcelProperty
    private Integer evaluationParticipate;

    @ExcelProperty
    private Double evaluationScore;

    @ExcelProperty
    private Integer evaluationSchoolRank;

    @ExcelProperty
    private Integer evaluationAcademyRank;

    private String evaluationTerm;

    private Integer evaluationTeacherId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public EvaluationPO(S2Evaluation s2Evaluation){
        evaluationAcademyRank = s2Evaluation.getEvaluationAcademyRank();
        evaluationSchoolRank = s2Evaluation.getEvaluationSchoolRank();

        evaluationTeacherName = s2Evaluation.getEvaluationTeacherName();
        evaluationTeacherId = s2Evaluation.getEvaluationTeacherId();

        evaluationParticipate = s2Evaluation.getEvaluationParticipate();

        evaluationScore = s2Evaluation.getEvaluationScore();

        evaluationTerm = s2Evaluation.getEvaluationTerm();
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
        EvaluationPO other = (EvaluationPO) that;
        return (this.getEvaluationId() == null ? other.getEvaluationId() == null : this.getEvaluationId().equals(other.getEvaluationId()))
            && (this.getEvaluationTerm() == null ? other.getEvaluationTerm() == null : this.getEvaluationTerm().equals(other.getEvaluationTerm()))
            && (this.getEvaluationTeacherId() == null ? other.getEvaluationTeacherId() == null : this.getEvaluationTeacherId().equals(other.getEvaluationTeacherId()))
            && (this.getEvaluationTeacherName() == null ? other.getEvaluationTeacherName() == null : this.getEvaluationTeacherName().equals(other.getEvaluationTeacherName()))
            && (this.getEvaluationParticipate() == null ? other.getEvaluationParticipate() == null : this.getEvaluationParticipate().equals(other.getEvaluationParticipate()))
            && (this.getEvaluationScore() == null ? other.getEvaluationScore() == null : this.getEvaluationScore().equals(other.getEvaluationScore()))
            && (this.getEvaluationSchoolRank() == null ? other.getEvaluationSchoolRank() == null : this.getEvaluationSchoolRank().equals(other.getEvaluationSchoolRank()))
            && (this.getEvaluationAcademyRank() == null ? other.getEvaluationAcademyRank() == null : this.getEvaluationAcademyRank().equals(other.getEvaluationAcademyRank()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEvaluationId() == null) ? 0 : getEvaluationId().hashCode());
        result = prime * result + ((getEvaluationTerm() == null) ? 0 : getEvaluationTerm().hashCode());
        result = prime * result + ((getEvaluationTeacherId() == null) ? 0 : getEvaluationTeacherId().hashCode());
        result = prime * result + ((getEvaluationTeacherName() == null) ? 0 : getEvaluationTeacherName().hashCode());
        result = prime * result + ((getEvaluationParticipate() == null) ? 0 : getEvaluationParticipate().hashCode());
        result = prime * result + ((getEvaluationScore() == null) ? 0 : getEvaluationScore().hashCode());
        result = prime * result + ((getEvaluationSchoolRank() == null) ? 0 : getEvaluationSchoolRank().hashCode());
        result = prime * result + ((getEvaluationAcademyRank() == null) ? 0 : getEvaluationAcademyRank().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", evaluationId=").append(evaluationId);
        sb.append(", evaluationTerm=").append(evaluationTerm);
        sb.append(", evaluationTeacherId=").append(evaluationTeacherId);
        sb.append(", evaluationTeacherName=").append(evaluationTeacherName);
        sb.append(", evaluationParticipate=").append(evaluationParticipate);
        sb.append(", evaluationScore=").append(evaluationScore);
        sb.append(", evaluationSchoolRank=").append(evaluationSchoolRank);
        sb.append(", evaluationAcademyRank=").append(evaluationAcademyRank);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}