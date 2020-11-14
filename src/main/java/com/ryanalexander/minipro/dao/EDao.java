package com.ryanalexander.minipro.dao;

import com.ryanalexander.minipro.service.excel_ali.entity.EvaluationEntity;
import com.ryanalexander.minipro.service.excel_ali.entity.EvaluationEntity_;
import com.ryanalexander.minipro.service.excel_ali.entity.TeacherEntity_;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EDao {
    void Einsert(List<EvaluationEntity_> list);
	/* insert */

}