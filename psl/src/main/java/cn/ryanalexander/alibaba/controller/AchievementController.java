package cn.ryanalexander.alibaba.controller;

import cn.ryanalexander.alibaba.domain.dto.Result;
import cn.ryanalexander.alibaba.domain.po.AchievementPO;
import cn.ryanalexander.alibaba.domain.po.CoursePO;
import cn.ryanalexander.alibaba.mapper.AchievementMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p><b></b></p>
 *
 * <p>2022/4/16 </p>
 *
 * @author ryan 2022/4/16 20:49
 * @since 1.0.0
 **/
@RestController
@RequestMapping("/achievement")
public class AchievementController {
    @Resource
    private AchievementMapper achievementMapper;

    @ApiOperation("get Achievement By TeacherId")
    @PostMapping("/getByTeacherIdAndYear")
    public Result getByTeacherId(String teacherId, String year){
        return new Result(achievementMapper.selectList(new QueryWrapper<AchievementPO>()
                .eq("achievement_teacher_id", teacherId)
                .eq("achievement_year",year)));
    }

    @ApiOperation("get years record by teacher Id")
    @PostMapping("/getYears")
    public Result getYears(String teacherId){
        return new Result(achievementMapper.selectObjs(new QueryWrapper<AchievementPO>()
                .select("distinct achievement_year")
                .eq("achievement_teacher_id", teacherId)));
    }
}
