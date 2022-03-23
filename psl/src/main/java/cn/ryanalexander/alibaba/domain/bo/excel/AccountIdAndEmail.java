package cn.ryanalexander.alibaba.domain.bo.excel;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;
import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.exceptions.ExceptionInfo;
import cn.ryanalexander.alibaba.domain.po.AccountPO;
import cn.ryanalexander.alibaba.mapper.AccountMapper;
import cn.ryanalexander.alibaba.service.AccountService;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

/**
 * <p><b></b></p>

 * <p>2022-03-23 </p>

 * @since 1.0.0
 * @see com.alibaba.excel.converters.Converter
 * @author RyanAlexander 2022-03-23 11:20
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel("工号和邮箱")
public class AccountIdAndEmail implements ExcelEntity<AccountIdAndEmail> {
    @ExcelProperty(value = "职工号")
    public Integer accountId;

    @ExcelProperty(value = "姓名")
    public String accountName;

    @ExcelProperty(value = "邮箱")
    public String accountMail;

    @Override
    public boolean isValidated() {
        return accountId != null && accountMail != null;
    }

    @Override
    public void transformAndSave(ArrayList<AccountIdAndEmail> list, int size) {
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");
        try{
            accountMapper.saveOrUpdateBatchByMail(list);
        }
        catch (Exception e){
            ExceptionInfo exceptionInfo = new ExceptionInfo(
                    "saveOrUpdateBatchByMail", "MySQL disconnected? NPE?",
                    "accountMapper.saveOrUpdateBatchByMail");
            throw new AppException(exceptionInfo, ErrorCodeEnum.SQL_EXEC_FAIL);
        }
    }
}
