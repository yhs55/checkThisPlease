package com.ssg.dsilbackend.dto.myDinig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private String reviewContents;
    private Long reservationId;
    private LocalDate registerDate;
    private Long reviewScore;
    private String userEmail;
}