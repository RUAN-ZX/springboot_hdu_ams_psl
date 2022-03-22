package cn.ryanalexander.alibaba.domain.excel;

import java.util.ArrayList;

public interface ExcelEntity {
    public Object transformAndSave(ArrayList<?> list);
}
