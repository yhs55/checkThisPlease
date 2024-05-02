package com.ssg.dsilbackend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "reservation_state")
public class ReservationState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_state_id")
    private Long id;

    @Column(name = "reservation_state_name", length = 100, nullable = false)
    private String stateName;
}
