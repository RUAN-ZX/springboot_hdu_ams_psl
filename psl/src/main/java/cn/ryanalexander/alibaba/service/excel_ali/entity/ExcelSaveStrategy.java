package cn.ryanalexander.alibaba.service.excel_ali.entity;

import java.util.List;

public interface ExcelSaveStrategy {
    public Object transformAndSave(List list, Object dao);
}
