package cn.ryanalexander.psl.controller;

import cn.ryanalexander.common.domain.dto.Result;
import cn.ryanalexander.psl.domain.po.AchievementPO;
import cn.ryanalexander.psl.mapper.AchievementMapper;
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

    @Require(RoleEnum.TEACHER)
    @ApiOperation("get Achievement By TeacherId")
    @GetMapping("/getByTeacherIdAndYear")
    public Result getByTeacherId(String accountId, String year){
        return new Result(achievementMapper.selectList(new QueryWrapper<AchievementPO>()
                        .eq("achievement_year",year)
                        .eq("achievement_teacher_id", accountId)
                ));
    }

    @Require(RoleEnum.TEACHER)
    @ApiOperation("get years record by teacher Id")
    @GetMapping("/getYears")
    public Result getYears(String accountId){
        return new Result(achievementMapper.selectObjs(new QueryWrapper<AchievementPO>()
                .select("distinct achievement_year")
                .eq("achievement_teacher_id", accountId)));
    }
}
