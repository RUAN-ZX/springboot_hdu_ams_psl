package cn.ryanalexander.alibaba.domain.bo.excel;

import java.util.ArrayList;

public interface ExcelEntity<T, E> {
    E transform();
    void transformAndSave(ArrayList<T> list, int size);
}
