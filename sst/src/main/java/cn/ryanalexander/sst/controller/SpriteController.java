package cn.ryanalexander.sst.controller;

import cn.ryanalexander.common.domain.dto.Result;
import cn.ryanalexander.sst.domain.dto.QuestionDTO;
import cn.ryanalexander.sst.domain.po.ItemInventoryPO;
import cn.ryanalexander.sst.domain.po.QuestionPO;
import cn.ryanalexander.sst.domain.po.SpritePO;
import cn.ryanalexander.sst.domain.po.SpriteRecordPO;
import cn.ryanalexander.sst.mapper.ItemInventoryMapper;
import cn.ryanalexander.sst.mapper.SpriteMapper;
import cn.ryanalexander.sst.mapper.SpriteRecordMapper;
import cn.ryanalexander.sst.processor.annotationIntercept.Require;
import cn.ryanalexander.sst.processor.annotationIntercept.RoleEnum;
import cn.ryanalexander.sst.service.ItemInventoryService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/5 </p>
 *
 * @author ryan 2022/5/5 16:57
 * @since 1.0.0
 **/

@Api(tags = "皮卡丘")
@RestController
public class SpriteController {
    @Resource
    private SpriteMapper spriteMapper;

    @Resource
    private SpriteRecordMapper spriteRecordMapper;

    @Resource
    private ItemInventoryMapper itemInventoryMapper;

    @Resource
    private ItemInventoryService itemInventoryService;

    //    @Require
    @ApiOperation("查询所有已获得的精灵（记录）的id")
    @GetMapping("/getAllSpriteRecord")
    public List<JSONObject> getAllSpriteRecord(
            @RequestHeader String access,
            int userId) {
        List<SpriteRecordPO> spriteRecordPOS =
                spriteRecordMapper.selectList(new QueryWrapper<SpriteRecordPO>()
                        .eq("sprite_record_user_id", userId));

        int size = spriteRecordPOS.size();
        if(size == 0) return new ArrayList<>();

        List<JSONObject> result = new ArrayList<>(size);
        for (SpriteRecordPO spriteRecordPO : spriteRecordPOS) {
            JSONObject spriteRecord = (JSONObject) JSONObject.toJSON(spriteRecordPO);
            SpritePO spritePO = spriteMapper.selectOne(new QueryWrapper<SpritePO>()
                    .eq("sprite_id", spriteRecordPO.getSpriteRecordSpriteId())
                    .last("limit 1"));

            JSONObject sprite = (JSONObject) JSONObject.toJSON(spritePO);
            spriteRecord.putAll(sprite);

            result.add(spriteRecord);
        }
        return result;
    }

    //    @Require
    @ApiOperation("修改自己精灵的属性")
    @PostMapping("/modifyMySprite")
    public Result modifyMySprite(
            @RequestHeader String access,
            int userId,
            @Parameter(schema = @Schema(implementation = SpriteRecordPO.class)) SpriteRecordPO spriteRecordPO) {
        spriteRecordMapper.insert(spriteRecordPO);
        return new Result();
    }

//    @Require()
    @ApiOperation("捕捉精灵") // 返回添加的题目id
    @PostMapping("/addSpriteRecord")
    public Integer addSpriteRecord(
            @RequestHeader String access,
            int userId,
            int spriteId) {
        SpriteRecordPO spriteRecordPO = new SpriteRecordPO();
        spriteRecordPO.setSpriteRecordUserId(userId);
        spriteRecordPO.setSpriteRecordSpriteId(spriteId);

        // sprite调Health数据
        Object spriteHealth = spriteMapper.selectObjs(new QueryWrapper<SpritePO>()
                .select("sprite_health")
                .eq("sprite_id", spriteId)
                .last("limit 1"));

        spriteRecordPO.setSpriteRecordHealth((Integer) spriteHealth);
        spriteRecordPO.setSpriteRecordExperience(0);

        spriteRecordMapper.insert(spriteRecordPO);

        // 道具-1
        ItemInventoryPO itemInventoryPO = itemInventoryMapper.selectOne(new QueryWrapper<ItemInventoryPO>()
                .eq("item_record_user_id", userId)
                .eq("item_record_item_name", "ball"));
        Integer num = itemInventoryPO.getItemInventoryItemNum();
        itemInventoryPO.setItemInventoryItemNum(num - 1);

        itemInventoryService.saveOrUpdate(itemInventoryPO);

        return spriteRecordPO.getSpriteRecordId();
    }
}