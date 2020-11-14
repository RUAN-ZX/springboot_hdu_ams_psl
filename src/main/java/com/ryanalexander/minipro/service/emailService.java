package com.ryanalexander.minipro.service;

import com.ryanalexander.minipro.dao.TDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


@Service
public class emailService {

    final
    JavaMailSender mailSender;

    @Autowired
    private TDao tDao;

    public emailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public static String readFileByLines(String fileName) {
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


    public void sendMails(String Tid) throws MessagingException {

        String addr1 = "D:\\Users\\Lenovo\\IdeaProjects\\psl_project\\src\\main\\resources\\mail.html";
        String a = null;
        a = readFileByLines(addr1);


        MimeMessage mimiMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimiMessage, true);

        helper.setText(a, true);
        String mailTo = Tid+"@hdu.edu.cn";



        String mailTitle = tDao.TgetById(Tid).getTname()+"老师（职工号:"+Tid+"）您好";

        String mailFrom = "ryan_innerpeace@foxmail.com";

        helper.setTo(mailTo);
        helper.setFrom(mailFrom);


        helper.setSubject(mailTitle);

        mailSender.send(mimiMessage);


    }
}
