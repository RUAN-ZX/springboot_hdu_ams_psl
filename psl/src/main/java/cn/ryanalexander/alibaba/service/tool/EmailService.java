package cn.ryanalexander.alibaba.service.tool;

import cn.ryanalexander.alibaba.mapper.TeacherMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    @Resource
    private TeacherMapper tDao;

    public static String readFile(String fileName) {
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
                if (tempString != null && allString != null) allString = allString.concat(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {

                    reader.close();

                } catch (IOException e1) {
                }
            }
        }
        return allString;
    }


    public void sendCaptchaMails(String Tid, String Tcaptcha, String Tname, String Tmail) throws MessagingException {
        MimeMessage mimiMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimiMessage, true);

        String content = readFile(StaticConfiguration.getMailUrl());
        helper.setText(content.replace("老师",Tname+"老师").replace("123456",Tcaptcha),
                true);

        String mailTitle = "[教务查系统] 验证码："+Tcaptcha;
        String mailFrom = "ryan_innerpeace@foxmail.com";
        helper.setTo(Tmail);
        helper.setFrom(mailFrom);
        helper.setSubject(mailTitle);
        mailSender.send(mimiMessage);


    }
    public void sendFeedbackMails(String Tid, String Tname, String Tmail, String Fcontent) throws MessagingException {
        MimeMessage mimiMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimiMessage, true);

        String content = readFile(StaticConfiguration.getMailUrl());
        helper.setText(content.replace("老师",Tname+"老师").replace("123456",Fcontent),
                true);
        // 怎么弄 怎么设计title
        String mailTitle = "处理结果"+Fcontent+" [ "+Tname+"老师 (职工号:"+Tid+") ]";
        String mailFrom = "ryan_innerpeace@foxmail.com";
        helper.setTo(Tmail);
        helper.setFrom(mailFrom);
        helper.setSubject(mailTitle);
        mailSender.send(mimiMessage);


    }

}
