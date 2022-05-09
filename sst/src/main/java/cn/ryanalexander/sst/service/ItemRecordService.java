package cn.ryanalexander.sst.service;

import cn.ryanalexander.sst.domain.po.ItemRecordPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ryan
* @description 针对表【item_record】的数据库操作Service
* @createDate 2022-05-09 19:03:51
*/
public interface ItemRecordService extends IService<ItemRecordPO> {

    int changeItemNum(int userId, int deltaNum, String itemName);
}
