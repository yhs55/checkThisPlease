package com.ssg.dsilbackend.dto.restaurantManage;

import com.ssg.dsilbackend.domain.Reply;
import com.ssg.dsilbackend.domain.Reservation;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {
    private Long id;
    private Reply reply;

    private Reservation reservation;

    private String content;

    private LocalDate registerDate;

    private Long score;

    private boolean deleteStatus;

    private byte[] img;
}
