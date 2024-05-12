package com.ssg.dsilbackend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MimeMessageHelperService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MimeMessageHelperService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String email) throws MessagingException {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            // 콜백 메서드 구현
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                // MimeMessageHelper 생성
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                // 받는 사람 이메일 
                helper.setTo("");
                // 이메일 제목
                helper.setSubject("");
                // 메일 내용
                helper.setText("");

            }
        };
        try {
            // 메일 전송
            this.javaMailSender.send(preparator);
        } catch (MailException e) {
            throw e;
        }
    }
}