package com.ssg.dsilbackend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class MimeMessageHelperService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MimeMessageHelperService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String email, String reservationInfo) throws MessagingException {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            // 콜백 메서드 구현
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                // MimeMessageHelper 생성
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                // 받는 사람 이메일 
                helper.setTo(email);
                // 이메일 제목
                helper.setSubject("DSIL 서비스 예약 완료 알림");
                // 메일 내용
                helper.setText(reservationInfo);
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