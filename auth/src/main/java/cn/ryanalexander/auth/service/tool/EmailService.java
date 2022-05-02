package cn.ryanalexander.auth.service.tool;

import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.common.domain.exceptions.ExceptionInfo;
import cn.ryanalexander.common.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.common.domain.exceptions.code.SubjectEnum;
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
    private StaticConfiguration StaticConfiguration;

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


    public void sendCaptchaMails(String captcha, String name, String mailTo, String mailHtmlUrl) throws MessagingException, IOException {
        MimeMessage mimiMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimiMessage, true);
        String content = readFile(mailHtmlUrl);

        helper.setText(content.replace("老师，", name + "，").replace("123456", captcha),
                true);

        String mailTitle = "[教务查系统] 验证码："+captcha;
        String mailFrom = "ryan_innerpeace@foxmail.com";
//        helper.setTo(mailTo); todo 系统上线再说！
        helper.setTo(mailFrom);
        helper.setFrom(mailFrom);
        helper.setSubject(mailTitle);
        mailSender.send(mimiMessage);


    }
    public void sendFeedbackMails(String Tid, String Tname, String Tmail, String Fcontent) throws MessagingException, IOException {
        MimeMessage mimiMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimiMessage, true);

        String content = readFile(StaticConfiguration.getMailUrl());
        helper.setText(content.replace("老师",Tname+"老师").replace("123456",Fcontent),
                true);
        // todo 怎么弄 怎么设计title
        String mailTitle = "处理结果"+Fcontent+" [ "+Tname+"老师 (职工号:"+Tid+") ]";
        String mailFrom = "ryan_innerpeace@foxmail.com";
        helper.setTo(Tmail);
        helper.setFrom(mailFrom);
        helper.setSubject(mailTitle);
        mailSender.send(mimiMessage);


    }

}
