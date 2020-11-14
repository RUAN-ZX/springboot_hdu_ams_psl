package com.ryanalexander.minipro.dao;

import com.ryanalexander.minipro.entries.A;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ADao {
    void Ainsert(List<A> list);

}