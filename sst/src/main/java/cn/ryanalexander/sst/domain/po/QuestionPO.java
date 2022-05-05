package cn.ryanalexander.sst.domain.po;

import cn.ryanalexander.sst.domain.dto.QuestionDTO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName question
 */
@TableName(value ="question")
@Data
public class QuestionPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer questionId;

    /**
     * 
     */
    private Integer questionTeacherId;

    /**
     * 
     */
    private Integer questionSubjectId;

    /**
     * 
     */
    private String questionStem;

    /**
     * 
     */
    private String questionAnswerA;

    /**
     * 
     */
    private String questionAnswerB;

    /**
     * 
     */
    private String questionAnswerC;

    /**
     * 
     */
    private String questionAnswerD;

    /**
     * 
     */
    private Integer questionTrueAnswer;



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
        QuestionPO other = (QuestionPO) that;
        return (this.getQuestionId() == null ? other.getQuestionId() == null : this.getQuestionId().equals(other.getQuestionId()))
            && (this.getQuestionTeacherId() == null ? other.getQuestionTeacherId() == null : this.getQuestionTeacherId().equals(other.getQuestionTeacherId()))
            && (this.getQuestionSubjectId() == null ? other.getQuestionSubjectId() == null : this.getQuestionSubjectId().equals(other.getQuestionSubjectId()))
            && (this.getQuestionStem() == null ? other.getQuestionStem() == null : this.getQuestionStem().equals(other.getQuestionStem()))
            && (this.getQuestionAnswerA() == null ? other.getQuestionAnswerA() == null : this.getQuestionAnswerA().equals(other.getQuestionAnswerA()))
            && (this.getQuestionAnswerB() == null ? other.getQuestionAnswerB() == null : this.getQuestionAnswerB().equals(other.getQuestionAnswerB()))
            && (this.getQuestionAnswerC() == null ? other.getQuestionAnswerC() == null : this.getQuestionAnswerC().equals(other.getQuestionAnswerC()))
            && (this.getQuestionAnswerD() == null ? other.getQuestionAnswerD() == null : this.getQuestionAnswerD().equals(other.getQuestionAnswerD()))
            && (this.getQuestionTrueAnswer() == null ? other.getQuestionTrueAnswer() == null : this.getQuestionTrueAnswer().equals(other.getQuestionTrueAnswer()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getQuestionId() == null) ? 0 : getQuestionId().hashCode());
        result = prime * result + ((getQuestionTeacherId() == null) ? 0 : getQuestionTeacherId().hashCode());
        result = prime * result + ((getQuestionSubjectId() == null) ? 0 : getQuestionSubjectId().hashCode());
        result = prime * result + ((getQuestionStem() == null) ? 0 : getQuestionStem().hashCode());
        result = prime * result + ((getQuestionAnswerA() == null) ? 0 : getQuestionAnswerA().hashCode());
        result = prime * result + ((getQuestionAnswerB() == null) ? 0 : getQuestionAnswerB().hashCode());
        result = prime * result + ((getQuestionAnswerC() == null) ? 0 : getQuestionAnswerC().hashCode());
        result = prime * result + ((getQuestionAnswerD() == null) ? 0 : getQuestionAnswerD().hashCode());
        result = prime * result + ((getQuestionTrueAnswer() == null) ? 0 : getQuestionTrueAnswer().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", questionId=").append(questionId);
        sb.append(", questionTeacherId=").append(questionTeacherId);
        sb.append(", questionSubjectId=").append(questionSubjectId);
        sb.append(", questionStem=").append(questionStem);
        sb.append(", questionAnswerA=").append(questionAnswerA);
        sb.append(", questionAnswerB=").append(questionAnswerB);
        sb.append(", questionAnswerC=").append(questionAnswerC);
        sb.append(", questionAnswerD=").append(questionAnswerD);
        sb.append(", questionTrueAnswer=").append(questionTrueAnswer);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}