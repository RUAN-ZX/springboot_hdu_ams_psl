package cn.ryanalexander.alibaba.entity.excel;

import java.util.List;

public interface ExcelEntity {
    public Object transformAndSave(List list, Object dao);
}
