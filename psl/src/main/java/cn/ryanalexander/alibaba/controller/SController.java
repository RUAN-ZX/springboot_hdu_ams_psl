package cn.ryanalexander.alibaba.controller;

import cn.ryanalexander.alibaba.domain.dto.Result;
import cn.ryanalexander.alibaba.domain.po.CoursePO;
import cn.ryanalexander.alibaba.domain.po.SFinalPO;
import cn.ryanalexander.alibaba.mapper.SFinalMapper;
import cn.ryanalexander.alibaba.processor.annotationIntercept.Require;
import cn.ryanalexander.alibaba.processor.annotationIntercept.RoleEnum;
import cn.ryanalexander.alibaba.service.SDetailService;
import cn.ryanalexander.alibaba.service.SFinalService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Resource
    private SFinalMapper sFinalMapper;


    @ApiOperation("get SFinal and SDetail By TeacherId")
    @GetMapping("/getByTeacherIdAndYear")
    public Result getSDetailByTeacherId(String id, String year){
        ArrayList<Object> result = new ArrayList<>();
        result.add(sFinalService.getSFinalByTeacherId(id, year));
        result.add(sDetailService.getSDetailByTeacherId(id, year));
        return new Result(JSONObject.toJSON(result));
    }
    @Require(RoleEnum.TEACHER)
    @ApiOperation("get Years choice By TeacherId")
    @GetMapping("/getYearsByTeacherId")
    public Object getYearsByTeacherId(String id){
        return sFinalMapper.selectObjs(new QueryWrapper<SFinalPO>()
                .select("distinct s_final_year")
                .eq("s_final_teacher_id", id));
    }
}
