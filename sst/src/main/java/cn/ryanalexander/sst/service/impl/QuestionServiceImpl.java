package cn.ryanalexander.sst.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.sst.domain.po.QuestionPO;
import cn.ryanalexander.sst.service.QuestionService;
import cn.ryanalexander.sst.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【question】的数据库操作Service实现
* @createDate 2022-05-04 15:31:47
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, QuestionPO>
    implements QuestionService{

}




