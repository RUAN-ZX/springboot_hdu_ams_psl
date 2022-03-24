package cn.ryanalexander.alibaba.domain.bo.excel;

import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCodeEnum;
import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.exceptions.ExceptionInfo;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.alibaba.mapper.AccountMapper;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.poi.ss.formula.functions.T;

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
    public boolean multiStart(){
        return false;
    }

    @Override
    public boolean multiContinue(){
        return false;
    }

    @Override
    public ExcelEntity copyFromMasterMask(ExcelEntity data) {
        return this;
    }

    @Override
    public void transformAndSave(ArrayList<AccountIdAndEmail> list, int size) {
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");
        try{
            accountMapper.saveOrUpdateBatchByMail(list);
        }
        catch (AppException e){
            ExceptionInfo exceptionInfo = new ExceptionInfo(
                    "saveOrUpdateBatchByMail", "MySQL disconnected? NPE?",
                    "accountMapper.saveOrUpdateBatchByMail");
            throw new AppException(e, exceptionInfo, new ErrorCode(SubjectEnum.USER));
        }
    }
}
