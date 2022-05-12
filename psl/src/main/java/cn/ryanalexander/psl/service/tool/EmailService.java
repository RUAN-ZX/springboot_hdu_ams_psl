package cn.ryanalexander.psl.service.tool;

import cn.ryanalexander.psl.domain.exceptions.AppException;
import cn.ryanalexander.psl.domain.exceptions.ExceptionInfo;
import cn.ryanalexander.psl.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.psl.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.psl.mapper.AccountMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


@Service
public class EmailService {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private StaticConfiguration staticConfiguration;
    @Resource
    private AccountMapper tDao;

    public static String readFile(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = null;
        String allString = null;
        try {
//            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
//            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            allString = reader.readLine();
//            System.out.println(allString);
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
//                System.out.println(tempString);
//                line++;
                if (allString != null) allString = allString.concat(tempString);
            }
            reader.close();
        } catch (IOException e) {
            throw new AppException(null,
                    new ExceptionInfo("mail.html","readFile","file url invalid"),
                    new ErrorCode(SubjectEnum.INTERNAL));
        } finally {
            if(reader!= null) reader.close();
        }
        return allString;
    }

    @Async
    public void sendCaptchaMails(String Tcaptcha, String Tname, String Tmail) throws MessagingException, IOException {
        MimeMessage mimiMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimiMessage, true);
        String content = readFile(staticConfiguration.getCaptchaMailUrl());

        helper.setText(content.replace("老师",Tname+"老师").replace("123456",Tcaptcha),
                true);

        String mailTitle = "[教务查系统] 验证码："+Tcaptcha;
        String mailFrom = "ryan_innerpeace@foxmail.com";
        if(staticConfiguration.getMailSendEnable()) helper.setTo(Tmail);
        else helper.setTo(mailFrom);

        helper.setFrom(mailFrom);
        helper.setSubject(mailTitle);
        mailSender.send(mimiMessage);
    }

    @Async // 给我看的！
    public void sendFeedbackMails(String accountId, String accountName, String feedback, String topic) throws MessagingException, IOException {
        MimeMessage mimiMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimiMessage, true);

        String content = readFile(staticConfiguration.getFeedbackMailUrl());

        helper.setText(content.replace("反馈内容", feedback)
                .replace("反馈主题", topic), true);

        String mailTitle = "来自["+accountName+"老师 ("+accountId+")]的反馈";
        String mail = "ryan_innerpeace@foxmail.com";
        helper.setTo(mail);
        helper.setFrom(mail);
        helper.setSubject(mailTitle);
        mailSender.send(mimiMessage);

    }

}
