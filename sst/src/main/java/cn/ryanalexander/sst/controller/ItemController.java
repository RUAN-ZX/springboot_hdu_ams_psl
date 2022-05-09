package cn.ryanalexander.sst.controller;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/5 </p>
 *
 * @author ryan 2022/5/5 16:58
 * @since 1.0.0
 **/

import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.sst.domain.po.ItemRecordPO;
import cn.ryanalexander.sst.mapper.ItemRecordMapper;
import cn.ryanalexander.sst.service.ItemRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "装备")
@RestController
public class ItemController {
    @Resource
    private ItemRecordMapper itemRecordMapper;

    @Resource
    private ItemRecordService itemRecordService;

//    @Require(RoleEnum.STUDENT)
    @ApiOperation("更改道具数量") // 返回添加的题目id
    @PostMapping("/changeItemNum")
    public Integer changeItemNum(
            @RequestHeader String access,
            int userId,
            int deltaItemRecordItemNum,
            String itemRecordItemName){
        return itemRecordService.changeItemNum(
                userId, deltaItemRecordItemNum, itemRecordItemName);
    }

    @ApiOperation("查询所有道具") // 返回添加的题目id
    @PostMapping("/getAllItem")
    public List<ItemRecordPO> getAllItem(
            @RequestHeader String access,
            int userId){
        return itemRecordMapper.selectList(new QueryWrapper<ItemRecordPO>()
                .eq("item_record_user_id", userId));
    }
}