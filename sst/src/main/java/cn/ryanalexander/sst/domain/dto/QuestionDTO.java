package cn.ryanalexander.sst.domain.dto;

import lombok.Data;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/5 </p>
 *
 * @author ryan 2022/5/5 9:59
 * @since 1.0.0
 **/
@Data
public class QuestionDTO {
    private Integer questionSubjectId;
    private String questionStem;
    private String questionAnswerA;
    private String questionAnswerB;
    private String questionAnswerC;
    private String questionAnswerD;
    private Integer questionTrueAnswer;
}
