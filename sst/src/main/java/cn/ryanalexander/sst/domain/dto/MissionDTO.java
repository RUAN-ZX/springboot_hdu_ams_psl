package cn.ryanalexander.sst.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/5 </p>
 *
 * @author ryan 2022/5/5 9:22
 * @since 1.0.0
 **/
@Data
public class MissionDTO {
    private Integer missionQuestionId;
    private Integer missionClassId;
    private Integer missionSubjectId;

    private Date missionTimeStart;
    private Date missionTimeEnd;
    private String missionDescription;
}
