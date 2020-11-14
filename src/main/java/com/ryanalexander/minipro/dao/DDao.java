package com.ryanalexander.minipro.dao;

import com.ryanalexander.minipro.entries.D;
import org.springframework.stereotype.Repository;

@Repository
public interface DDao {

	/* insert */



	/* select */


	String DgetById(String Did);

	void Dinsert(D d);
}