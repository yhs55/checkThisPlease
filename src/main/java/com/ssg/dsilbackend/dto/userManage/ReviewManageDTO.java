package com.ssg.dsilbackend.dto.userManage;

import com.ssg.dsilbackend.domain.Reply;
import com.ssg.dsilbackend.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewManageDTO {
    private Long id;
    private String email;
    private Review review;
    private Reply reply;
}
