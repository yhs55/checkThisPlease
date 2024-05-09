package com.ssg.dsilbackend.dto.myDinig;

import com.ssg.dsilbackend.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MydiningReviewsDTO {
    private Long reviewId;
    private Long restaurantId;
    private String restaurantName;
    private Long replyId = null;
    private String reviewContent;
    private String replyContent;
    private LocalDate registerDate;
    private Long score;
    private boolean deleteStatus;
    private String name; // 식당이름
    private LocalDate replyRegisterDate;

    public static MydiningReviewsDTO from(Review review){
        return MydiningReviewsDTO.builder()
                .reviewId(review.getId())
                .restaurantId(review.getReservation().getRestaurant().getId())
                .restaurantName(review.getReservation().getRestaurant().getName())
                .replyId(review.getReply() != null ? review.getReply().getId() : null)
                .reviewContent(review.getContent())
                .replyContent(review.getReply() != null ? review.getReply().getContent() : null)
                .registerDate(review.getRegisterDate())
                .score(review.getScore())
                .deleteStatus(review.isDeleteStatus())
                .replyRegisterDate(review.getReply().getRegisterDate())
                .build();
    }

}
