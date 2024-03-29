package cn.ryanalexander.sst.controller;

import cn.ryanalexander.common.domain.dto.Result;
import cn.ryanalexander.sst.domain.dto.QuestionDTO;
import cn.ryanalexander.sst.domain.po.MissionPO;
import cn.ryanalexander.sst.domain.po.QuestionPO;
import cn.ryanalexander.sst.domain.po.SubjectPO;
import cn.ryanalexander.sst.domain.po.UserPO;
import cn.ryanalexander.sst.mapper.MissionMapper;
import cn.ryanalexander.sst.mapper.QuestionMapper;
import cn.ryanalexander.sst.mapper.SubjectMapper;
import cn.ryanalexander.sst.mapper.UserMapper;
import cn.ryanalexander.sst.processor.annotationIntercept.Require;
import cn.ryanalexander.sst.processor.annotationIntercept.RoleEnum;
import cn.ryanalexander.sst.service.MissionService;
import cn.ryanalexander.sst.service.QuestionService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * @author ryan 2022/5/4 22:50
 * @since 1.0.0
 **/
@Api(tags = "问题 题库")
@RestController
public class QuestionController {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionService questionService;


    @Resource
    private SubjectMapper subjectMapper;

    // 前端根据classId自己分类//
//    @Require(RoleEnum.TEACHER)
    @ApiOperation("老师查看所有设计的题目 注意不是mission是question")
    @GetMapping("/getAllQuestion")
    public List<JSONObject> getAllQuestion(
            @RequestHeader String access,
            int userId){
        List<QuestionPO> questionPOS = questionMapper.selectList(
                new QueryWrapper<QuestionPO>()
                .eq("question_teacher_id", userId));
        if(questionPOS.size() == 0) return new ArrayList<>();

        List<JSONObject> result = new ArrayList<>();
// todo 同样需要Union All解决 in会去重· 另外考虑结果的顺序问题 否则对应全错！
//        List<Integer> subjectIds = new ArrayList<>();
//        for(QuestionPO questionPO : questionPOS ){
//            subjectIds.add(questionPO.getQuestionSubjectId());
//        }
//        List<Object> subjectNames = subjectMapper.selectObjs(new QueryWrapper<SubjectPO>()
//                .select("subject_name")
//                .in("subject_id", subjectIds).last("limit 1"));

        for(QuestionPO question : questionPOS){
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(question);

            jsonObject.put("subject_name", subjectMapper.selectObjs(new QueryWrapper<SubjectPO>()
                .select("subject_name")
                .in("subject_id", question.getQuestionSubjectId()).last("limit 1")));
            result.add(jsonObject);
        }
        return result;
    }

    // --------------------------------------------------------------------------------
    // @RequestBody @Parameter(schema = @Schema(implementation = QuestionDTO.class)) QuestionPO questionPO
//    @Require(RoleEnum.TEACHER)
    @ApiOperation("教师添加题目 返回添加完的id") // 返回添加的题目id
    @PostMapping("/addQuestion")
    public Integer addQuestion(
            @RequestHeader String access,
            @RequestParam int userId,
            @Parameter(schema = @Schema(implementation = QuestionDTO.class)) QuestionPO questionPO){
        // schema是questionDTO 但是对象可以被解析为QuestionPO 这样不香？没有对象转换太爽了！！！
        questionPO.setQuestionTeacherId(userId);
        // JSONObject的方式转对象 毕竟除了一个属性不一样 其他都相同 写构造函数实在太憋屈了
//        String jsonObject = JSONObject.toJSONString(questionDTO);
//        QuestionPO questionPO = JSONObject.parseObject(jsonObject, QuestionPO.class);
        questionMapper.insert(questionPO);
        return questionPO.getQuestionId(); // mybatis自动填充id的
    }

//    @Require(RoleEnum.TEACHER)
    @ApiOperation("修改题目 前端已经获取原题目的数据 改完以后打包过来完成修改")
    @PostMapping("/modifyQuestion")
    public Result modifyQuestion(
            @RequestHeader String access,
            @RequestParam int userId,
            @Parameter(schema = @Schema(implementation = QuestionPO.class)) QuestionPO questionPO){
        questionService.saveOrUpdate(questionPO); // 肯定是update的
        return new Result();
    }
}
