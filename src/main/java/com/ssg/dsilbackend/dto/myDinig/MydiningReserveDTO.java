package com.ssg.dsilbackend.dto.myDinig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MydiningReserveDTO {
    private String name; // 식당이름 // 식당
    private String reservationState; // 예약상태 // 예약
    private String reservationTime; // 예약 시간 // 예약
    private Long peopleCount; // 인원수 // 예약
    private Long deposit; // 예약금 // 식당

    public void setRestaurantName(String name) {
        this.name = name;

    }

    public void setRestaurantDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public void setReservationState(String reservationState) {
        this.reservationState = reservationState;
    }
}
