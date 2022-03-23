package cn.ryanalexander.alibaba.mapper;

import cn.ryanalexander.alibaba.domain.bo.excel.TidAndEmail;
import cn.ryanalexander.alibaba.domain.po.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Mapper
* @createDate 2022-03-22 16:11:53
* @Entity cn.ryanalexander.alibaba.domain.po.Teacher
*/
public interface TeacherMapper extends BaseMapper<Teacher> {
    /**
     * <p><b>通过TidAndMail执行saveOrUpdateBatch操作
     * 使用duplicate update方式 由java指定pk</b></p>
     * <p>todo 2022-03-23 </p>
     * @param list desc
     * @return void
     * @throws
     * @since 1.0.0
    */
    void saveOrUpdateBatchByMail(@Param("items") List<TidAndEmail> list);
}




