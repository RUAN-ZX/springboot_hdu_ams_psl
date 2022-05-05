package cn.ryanalexander.sst.controller;

import cn.ryanalexander.common.domain.dto.Result;
import cn.ryanalexander.common.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.sst.domain.dto.MissionDTO;
import cn.ryanalexander.sst.domain.po.*;
import cn.ryanalexander.sst.mapper.*;
import cn.ryanalexander.sst.processor.annotationIntercept.Require;
import cn.ryanalexander.sst.processor.annotationIntercept.RoleEnum;
import cn.ryanalexander.sst.service.MissionService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/4 </p>
 *
 * @author ryan 2022/5/4 13:58
 * @since 1.0.0
 **/
@Api(tags = "任务相关")
@RestController
public class MissionController {

    @Resource
    private MissionMapper missionMapper;

    @Resource
    private MissionService missionService;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private RecordMapper recordMapper;

//    @Require(RoleEnum.STUDENT)
    @ApiOperation("学生获取自己的Mission")
    @GetMapping("/getMissionByStudentId")
    public List<JSONObject> getMissionByStudentId(int userId){
        List<MissionPO> missionPOS = missionMapper.selectList(new QueryWrapper<MissionPO>()
                .eq("mission_student_id", userId));
        int size = missionPOS.size();

        List<Integer> questionIds = new ArrayList<>(size);
        List<Integer> teacherIds = new ArrayList<>(size);
        List<Integer> subjectIds = new ArrayList<>(size);

        List<JSONObject> result = new ArrayList<>();

        for(MissionPO missionPO : missionPOS){
            questionIds.add(missionPO.getMissionQuestionId());
            teacherIds.add(missionPO.getMissionTeacherId());
            subjectIds.add(missionPO.getMissionSubjectId());
        }

        List<Object> questionStems = questionMapper.selectObjs(new QueryWrapper<QuestionPO>()
                .select("question_stem")
                .in("question_id",questionIds).last("limit 1"));

        List<Object> teacherNames = userMapper.selectObjs(new QueryWrapper<UserPO>()
                .select("user_name")
                .in("user_id",teacherIds).last("limit 1"));

        List<Object> subjectsNames = subjectMapper.selectObjs(new QueryWrapper<SubjectPO>()
                .select("subject_name")
                .in("subject_id",subjectIds).last("limit 1"));


        for(int i = 0 ; i < missionPOS.size() ; ++i){
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(missionPOS.get(i));
            jsonObject.put("questionStem", questionStems.get(i));
            jsonObject.put("teacherName", teacherNames.get(i));
            jsonObject.put("subjectName", subjectsNames.get(i));
            result.add(jsonObject);
        }
        return result;

    }

    // 前端可以筛选 根据班级 | 根据那个班级 那个问题 | 根据科目 那个问题
//        @Require(RoleEnum.TEACHER)
    @ApiOperation("老师获取自己布置的所有Mission")
    @GetMapping("/getMissionByTeacherId")
    public List<JSONObject> getMissionByTeacherId(int teacherId){
        List<MissionPO> missionPOS = missionMapper.selectList(new QueryWrapper<MissionPO>()
                .eq("mission_teacher_id", teacherId));
        int size = missionPOS.size();
        List<Integer> questionIds = new ArrayList<>(size);
        List<Integer> studentIds = new ArrayList<>(size);
        List<Integer> subjectIds = new ArrayList<>(size);

        List<JSONObject> result = new ArrayList<>();

        for(MissionPO missionPO : missionPOS){
            questionIds.add(missionPO.getMissionQuestionId());
            studentIds.add(missionPO.getMissionStudentId());
            subjectIds.add(missionPO.getMissionSubjectId());
        }

        List<Object> questionStems = questionMapper.selectObjs(new QueryWrapper<QuestionPO>()
                .select("question_stem")
                .in("question_id",questionIds).last("limit 1"));

        List<Object> studentNames = userMapper.selectObjs(new QueryWrapper<UserPO>()
                .select("user_name")
                .in("user_id",studentIds).last("limit 1"));

        List<Object> subjectsNames = subjectMapper.selectObjs(new QueryWrapper<SubjectPO>()
                .select("subject_name")
                .in("subject_id",subjectIds).last("limit 1"));


        for(int i = 0 ; i < missionPOS.size() ; ++i){
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(missionPOS.get(i));
            jsonObject.put("questionStem", questionStems.get(i));
            jsonObject.put("studentName", studentNames.get(i));
            jsonObject.put("subjectName", subjectsNames.get(i));
            result.add(jsonObject);
        }
        return result;

    }

    // ------------------------------------------------------------------

//    @Require(RoleEnum.STUDENT)
    @ApiOperation("学生完成任务")
    @GetMapping("/finishMission")
    public boolean finishMission(int missionId){
        // todo 其实应该验证是否为那个人的mission
        return missionService.update(new UpdateWrapper<MissionPO>()
                .eq("mission_id", missionId)
                .set("mission_is_finished", 1));
    }

    @ApiOperation("老师查看那道题目相关的所有任务完成情况") // 也可以前端在所有Mission里边筛选
    @GetMapping("/getMissionByQuestionId")
    public List<JSONObject> getMissionByQuestionId(int questionId) {
        // todo 其实应该检测这个question是否属于老师
        List<MissionPO> missionPOS = missionMapper.selectList(new QueryWrapper<MissionPO>()
                .eq("mission_question_id", questionId));

        if(missionPOS.size() == 0) return new ArrayList<>();

        List<Integer> studentIds = new ArrayList<>();
        for (MissionPO missionPo: missionPOS) {
            studentIds.add(missionPo.getMissionStudentId());
        }
        List<Object> studentNames = userMapper.selectObjs(new QueryWrapper<UserPO>()
                .select("user_name")
                .in("user_id", studentIds).last("limit 1"));

        List<JSONObject> result = new ArrayList<>();
        for(int i = 0 ; i < missionPOS.size() ; ++i){
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(missionPOS.get(i));
            jsonObject.put("studentName", studentNames.get(i));
        }
        return result;
    }
//    @Require(RoleEnum.TEACHER)
    @ApiOperation("添加任务 给整个班群发任务 | 不需要发送")
    @PostMapping("/assignMission")
    public Result assignMission(@RequestHeader String access,
                                @Parameter(description = "老师ID 与access配对") int userId,
                                @Parameter(schema = @Schema(implementation = MissionDTO.class)) MissionDTO missionDTO){
        int classId = missionDTO.getMissionClassId();

        List<Object> missionStudentIds = recordMapper.selectObjs(new QueryWrapper<RecordPO>()
                .eq("record_class_id", classId));

        int size = missionStudentIds.size();

        if(size == 0) return new Result(new ErrorCode(), "没有同学 没什么好发布的");

        List<MissionPO> missionPOS = new ArrayList<>(size);

        for(Object studentId : missionStudentIds ){
            MissionPO missionPO = new MissionPO(missionDTO);
            missionPO.setMissionStudentId((Integer) studentId);
            missionPO.setMissionTeacherId(userId);
            missionPOS.add(missionPO);
        }
        missionService.saveBatch(missionPOS);
        return new Result();
    }
}
