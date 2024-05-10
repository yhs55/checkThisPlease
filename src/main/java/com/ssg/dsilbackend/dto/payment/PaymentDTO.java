package com.ssg.dsilbackend.dto.payment;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private String pg;
    private String pay_method;
    private String merchant_uid;
    private String name;
    private Long amount;
    private String buyer_email;
    private String buyer_name;
    private String buyer_tel;


}
