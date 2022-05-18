package cn.ryanalexander.psl.controller;

import cn.ryanalexander.common.domain.dto.Result;
import cn.ryanalexander.common.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.common.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.psl.service.tool.BlockHandler;
import cn.ryanalexander.psl.service.tool.FallbackHandler;
import cn.ryanalexander.psl.domain.po.EvaluationPO;
import cn.ryanalexander.psl.domain.po.S1DetailPO;
import cn.ryanalexander.psl.domain.po.SFinalPO;
import cn.ryanalexander.psl.mapper.SFinalMapper;
import cn.ryanalexander.psl.processor.annotationIntercept.Require;
import cn.ryanalexander.psl.processor.annotationIntercept.RoleEnum;
import cn.ryanalexander.psl.service.EvaluationService;
import cn.ryanalexander.psl.service.SDetailService;
import cn.ryanalexander.psl.service.SFinalService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Resource
    private EvaluationService evaluationService;

    @Require(RoleEnum.TEACHER)
    @ApiOperation("get Years choice By TeacherId")
    @GetMapping("/getYearsByTeacherId")
    public Object getYearsByTeacherId(String accountId){
        return sFinalMapper.selectObjs(new QueryWrapper<SFinalPO>()
                .select("distinct s_final_year")
                .eq("s_final_teacher_id", accountId));
    }
    public static Result fallbackHandler(String accountId, Integer year, BlockException e) {
        return new Result(new ErrorCode(SubjectEnum.INTERNAL), "fallBack!!!!!!");
    }
    public static Result blockHandler(String accountId, Integer year, BlockException e) {
        return new Result(new ErrorCode(SubjectEnum.USER), "Blocked!!!!");
    }


//    @SentinelResource(value = "/s/getByTeacherIdAndYear",
//            blockHandler = "blockHandler",
//            fallback = "fallbackHandler",
//            exceptionsToIgnore = {IllegalArgumentException.class}
//    )
    @SentinelResource(value = "/s/getByTeacherIdAndYear")
    @ApiOperation("get SFinal and SDetail By TeacherId")
    @GetMapping("/getByTeacherIdAndYear")
    public Result getByTeacherIdAndYear(String accountId, Integer year){
        String term1 = (year - 1) + "-" + year + "-2";
        String term2 = year + "-" + (year + 1) + "-1";
        ArrayList<Object> result = new ArrayList<>();
        result.add(sFinalService.getSFinalByTeacherId(accountId, String.valueOf(year)));
        result.add(sDetailService.getSDetailByTeacherId(accountId, String.valueOf(year)));
        // todo 这些查询应该融合在一个查询里边！
        result.add(new S1DetailPO());
        // 两个学期的信息 sDetail只用S2 其他的均取自这里
        result.add(evaluationService.getOne(new QueryWrapper<EvaluationPO>()
                .eq("evaluation_term", term1)
                .eq("evaluation_teacher_id", accountId)
        ));
        result.add(evaluationService.getOne(new QueryWrapper<EvaluationPO>()
                .eq("evaluation_term", term2)
                .eq("evaluation_teacher_id", accountId)
        ));
        return new Result(JSONObject.toJSON(result));
    }
}
