package cn.ryanalexander.psl.controller;

import cn.ryanalexander.psl.domain.dto.Result;
import cn.ryanalexander.psl.domain.po.CoursePO;
import cn.ryanalexander.psl.domain.po.ShortTermPO;
import cn.ryanalexander.psl.domain.po.ThesisDesignPO;
import cn.ryanalexander.psl.mapper.CourseMapper;
import cn.ryanalexander.psl.mapper.ShortTermMapper;
import cn.ryanalexander.psl.mapper.ThesisDesignMapper;
import cn.ryanalexander.psl.processor.annotationIntercept.Require;
import cn.ryanalexander.psl.processor.annotationIntercept.RoleEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p><b></b></p>
 *
 * <p>2022/3/24 </p>
 *
 * @author ryan 2022/3/24 19:28
 * @since 1.0.0
 **/
@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private ShortTermMapper shortTermMapper;

    @Resource
    private ThesisDesignMapper thesisDesignMapper;

    // todo 这里小心term往往不准确 估计需要写入的时候特别照顾 不从excel读而是直接从课号推断 万无一失！
    // 打算本地存储老师查询过得课 点进去课程详情也能显示清楚！
    @Require(RoleEnum.TEACHER)
    @ApiOperation("get course By TeacherId And type And year")
    @GetMapping("/get")
    public Result get(String accountId, String time, String type){
        switch (type) {
            case "0":  // 理论
                return new Result(courseMapper.selectList(new QueryWrapper<CoursePO>()
                        .eq("course_teacher_id", accountId)
                        .eq("course_term", time)
                        .eq("course_properties", "I"))
                );
            case "1":
                return new Result(courseMapper.selectList(new QueryWrapper<CoursePO>()
                        .eq("course_teacher_id", accountId)
                        .eq("course_term", time)
                        .ne("course_properties", "I"))
                );
            case "2":
                return new Result(shortTermMapper.selectList(new QueryWrapper<ShortTermPO>()
                        .eq("short_term_teacher_id", accountId)
                        .eq("short_term_term", time))
                );
            default:
                return new Result(thesisDesignMapper.selectList(new QueryWrapper<ThesisDesignPO>()
                        .eq("thesis_design_teacher_id", accountId)
                        .eq("thesis_design_year", time))
                );
        }

    }
    // todo 不知道行不行 selectObjs 因为不知道出来的obj是啥。。
    @Require(RoleEnum.TEACHER)
    @ApiOperation("get years range of course")
    @GetMapping("/getYears")
    public Result get(String accountId, String type) {
        switch (type) {
            case "0":
                return new Result(courseMapper.selectObjs(new QueryWrapper<CoursePO>()
                        .select("distinct course_term")
                        .eq("course_teacher_id", accountId)
                        .eq("course_properties", "I")));
            case "1":
                return new Result(courseMapper.selectObjs(new QueryWrapper<CoursePO>()
                        .select("distinct course_term")
                        .eq("course_teacher_id", accountId)
                        .ne("course_properties", "I")));
            case "2":
                return new Result(shortTermMapper.selectObjs(new QueryWrapper<ShortTermPO>()
                        .select("distinct short_term_term")
                        .eq("short_term_teacher_id", accountId)));
            default:
                return new Result(thesisDesignMapper.selectObjs(new QueryWrapper<ThesisDesignPO>()
                        .select("distinct thesis_design_year")
                        .eq("thesis_design_teacher_id", accountId)));
        }
    }
}