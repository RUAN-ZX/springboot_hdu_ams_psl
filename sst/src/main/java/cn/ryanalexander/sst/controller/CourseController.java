package cn.ryanalexander.sst.controller;

import cn.ryanalexander.sst.domain.po.ClassPO;
import cn.ryanalexander.sst.domain.po.CoursePO;
import cn.ryanalexander.sst.mapper.CourseMapper;
import cn.ryanalexander.sst.processor.annotationIntercept.Require;
import cn.ryanalexander.sst.processor.annotationIntercept.RoleEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/5 </p>
 *
 * @author ryan 2022/5/5 16:54
 * @since 1.0.0
 **/
@Api(tags = "课程表")
@RestController
public class CourseController {
//    @Require(RoleEnum.STUDENT)
    @Resource
    private CourseMapper courseMapper;

//    @Require
    @ApiOperation("我的课程表 时尚时尚最时尚")
    @GetMapping("/getMyCourse")
    public List<CoursePO> getMyCourse(
            @RequestHeader String access,
            int userId){
        return courseMapper.selectList(new QueryWrapper<CoursePO>()
                .eq("course_user_id", userId)
                .last("limit 1"));
    }
}
