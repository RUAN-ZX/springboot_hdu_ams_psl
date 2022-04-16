package cn.ryanalexander.alibaba.controller;

import cn.ryanalexander.alibaba.domain.dto.Result;
import cn.ryanalexander.alibaba.service.SDetailService;
import cn.ryanalexander.alibaba.service.SFinalService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * <p><b>包括SFinal + SDetail</b></p>
 *
 * <p>2022/4/16 </p>
 *
 * @author ryan 2022/4/16 20:08
 * @since 1.0.0
 **/
@RestController
@RequestMapping("/s")
public class SController {
    @Resource
    private SFinalService sFinalService;

    @Resource
    private SDetailService sDetailService;

    @ApiOperation("get SDetail By TeacherId")
    @PostMapping("/getByTeacherIdAndYear")
    public Result getSDetailByTeacherId(String teacherId, String year){
        ArrayList<Object> result = new ArrayList<>();
        result.add(sDetailService.getSDetailByTeacherId(teacherId, year));
        result.add(sFinalService.getSFinalByTeacherId(teacherId, year));
        return new Result(result);
    }
}
