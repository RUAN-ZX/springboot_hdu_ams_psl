package cn.ryanalexander.sst.controller;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/5 </p>
 *
 * @author ryan 2022/5/5 16:58
 * @since 1.0.0
 **/

import cn.ryanalexander.sst.mapper.ItemInventoryMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "装备")
@RestController
public class ItemController {
    @Resource
    private ItemInventoryMapper itemInventoryMapper;

}
