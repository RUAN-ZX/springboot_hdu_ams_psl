package cn.ryanalexander.psl.service.tool;

import cn.ryanalexander.common.domain.dto.Result;
import cn.ryanalexander.common.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.common.domain.exceptions.code.SubjectEnum;
import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/13 </p>
 *
 * @author ryan 2022/5/13 12:35
 * @since 1.0.0
 **/

//xhzhang@hdu.edu.cn
public class FallbackHandler {
    public static Result fallbackHandler(String accountId, Integer year, BlockException e) {
        return new Result(new ErrorCode(SubjectEnum.INTERNAL), "fallBack!!!!!!");
    }
}
