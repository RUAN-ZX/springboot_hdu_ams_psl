package cn.ryanalexander.sst.service.impl;

import cn.ryanalexander.common.domain.exceptions.AppException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.sst.domain.po.ItemRecordPO;
import cn.ryanalexander.sst.service.ItemRecordService;
import cn.ryanalexander.sst.mapper.ItemRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author ryan
* @description 针对表【item_record】的数据库操作Service实现
* @createDate 2022-05-09 19:03:51
*/
@Service
public class ItemRecordServiceImpl extends ServiceImpl<ItemRecordMapper, ItemRecordPO>
    implements ItemRecordService{

    @Resource
    private ItemRecordMapper itemRecordMapper;

    @Resource
    private ItemRecordService itemRecordService;

    @Override
    public int changeItemNum(int userId, int deltaNum, String itemName) {
        ItemRecordPO itemRecordPO = itemRecordMapper.selectOne(new QueryWrapper<ItemRecordPO>()
                .eq("item_record_user_id", userId)
                .eq("item_record_item_name",itemName)
                .last("limit 1"));

        int numRemain = itemRecordPO.getItemRecordItemNum() + deltaNum;

        if(numRemain < 0) throw new AppException(null, "道具不够啦", "changeItemNum");

        itemRecordPO.setItemRecordItemNum(numRemain);
        itemRecordService.saveOrUpdate(itemRecordPO);
        return numRemain;
    }
}




