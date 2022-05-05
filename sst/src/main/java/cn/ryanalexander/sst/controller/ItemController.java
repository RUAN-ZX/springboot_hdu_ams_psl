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
import cn.ryanalexander.common.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.sst.domain.dto.QuestionDTO;
import cn.ryanalexander.sst.domain.po.ItemInventoryPO;
import cn.ryanalexander.sst.domain.po.QuestionPO;
import cn.ryanalexander.sst.mapper.ItemInventoryMapper;
import cn.ryanalexander.sst.processor.annotationIntercept.Require;
import cn.ryanalexander.sst.processor.annotationIntercept.RoleEnum;
import cn.ryanalexander.sst.service.ItemInventoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "装备")
@RestController
public class ItemController {
    @Resource
    private ItemInventoryMapper itemInventoryMapper;

    @Resource
    private ItemInventoryService itemInventoryService;

//    @Require(RoleEnum.STUDENT)
    @ApiOperation("更改道具数量") // 返回添加的题目id
    @PostMapping("/changeItemNum")
    public Integer changeItemNum(
            @RequestHeader String access,
            int userId,
            int deltaItemRecordItemNum,
            String itemRecordItemName){
        ItemInventoryPO itemInventoryPO = itemInventoryMapper.selectOne(new QueryWrapper<ItemInventoryPO>()
                .eq("item_record_id", userId)
                .eq("item_record_item_name",itemRecordItemName)
                .last("limit 1"));

        int numRemain = itemInventoryPO.getItemInventoryItemNum() + deltaItemRecordItemNum;

        if(numRemain < 0) throw new AppException(null, "道具不够啦", "changeItemNum");

        itemInventoryPO.setItemInventoryItemNum(numRemain);

        itemInventoryService.saveOrUpdate(itemInventoryPO);
        return numRemain;
    }

    @ApiOperation("查询所有道具") // 返回添加的题目id
    @PostMapping("/getAllItem")
    public List<ItemInventoryPO> getAllItem(
            @RequestHeader String access,
            int userId){
        return itemInventoryMapper.selectList(new QueryWrapper<ItemInventoryPO>()
                .eq("item_record_user_id", userId));
    }
}