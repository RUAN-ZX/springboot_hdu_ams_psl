package cn.ryanalexander.alibaba.dao;


import cn.ryanalexander.alibaba.entity.Teacher;
import cn.ryanalexander.alibaba.service.excel_ali.entity.TeacherEntity_;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 对接数据库 真正存储 读取硬盘的操作
 */
@Repository
public interface TeacherDao {

	/* insert */

	void TinsertByIdName(List<TeacherEntity_> list);
	void TinsertByIdNameEmail(String Tid, String Tname, String Tmail);

	void insert(TeacherEntity_ teacherEntity);
	/* update */
	void TupdatePwd(String Tid, String Tpwd);



	void TupdateDid(String TDid,String Tid);

	void TupdateEmail(String Tid, String Tmail);
	/* select */


	Teacher TgetById(String Tid);



	// 函数1 别的表可能对第一次导入的Tid表有补充 于是需要先找原来是否存在再录入新的信息 那么需要接口找原来是否存在 然后录入新信息（updata or insert！）
}



