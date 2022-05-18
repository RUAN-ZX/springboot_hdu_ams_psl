package cn.ryanalexander.psl.controller;

import cn.ryanalexander.common.domain.dto.Result;
import cn.ryanalexander.psl.domain.po.CourseUnionPO;
import cn.ryanalexander.psl.domain.po.ThesisDesignPO;
import cn.ryanalexander.psl.mapper.CourseMapper;
import cn.ryanalexander.psl.mapper.CourseUnionMapper;
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
    private CourseUnionMapper courseUnionMapper;

    @Resource
    private ShortTermMapper shortTermMapper;

    @Resource
    private ThesisDesignMapper thesisDesignMapper;

    // todo 这里小心term往往不准确 估计需要写入的时候特别照顾 不从excel读而是直接从课号推断 万无一失！
    // 打算本地存储老师查询过得课 点进去课程详情也能显示清楚！

    @Require(RoleEnum.TEACHER)
    @ApiOperation("get course By TeacherId And type And year")
    @GetMapping("/get/v2")
    public Result getV2(String accountId, String time, String type){
        if(type.equals("3")) return new Result(thesisDesignMapper.selectList(new QueryWrapper<ThesisDesignPO>()
                    .eq("thesis_design_year", time)
                    .eq("thesis_design_teacher_id", accountId)
                )
        );

        return new Result(courseUnionMapper.selectList(new QueryWrapper<CourseUnionPO>()
                .eq("course_term", time)
                .eq("course_type", type)
                .eq("course_teacher_id", accountId))
        );
    }
    @Require(RoleEnum.TEACHER)
    @ApiOperation("get years range of course")
    @GetMapping("/getYears")
    public Result getYears(String accountId, String type) {
        if(type.equals("3")) return new Result(thesisDesignMapper.selectObjs(new QueryWrapper<ThesisDesignPO>()
                .select("distinct thesis_design_year")
                .eq("thesis_design_teacher_id", accountId)));

        return new Result(courseUnionMapper.selectObjs(new QueryWrapper<CourseUnionPO>()
                .select("distinct course_term")
                .eq("course_type", type)
                .eq("course_teacher_id", accountId)));
    }
}
