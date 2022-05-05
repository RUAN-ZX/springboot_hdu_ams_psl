package cn.ryanalexander.sst.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.sst.domain.po.ItemInventoryPO;
import cn.ryanalexander.sst.service.ItemInventoryService;
import cn.ryanalexander.sst.mapper.ItemInventoryMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【item_inventory】的数据库操作Service实现
* @createDate 2022-05-04 15:31:41
*/
@Service
public class ItemInventoryServiceImpl extends ServiceImpl<ItemInventoryMapper, ItemInventoryPO>
    implements ItemInventoryService{

}




