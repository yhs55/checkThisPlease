package com.ssg.dsilbackend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Members members;

    @ManyToOne
    @JoinColumn(name = "reservation_status", nullable = false)
    private ReservationState reservationState;

    @Column(name = "people_count", nullable = false)
    private Long peopleCount;

    @Column(name = "reservation_time", nullable = false)
    private LocalDateTime reservationTime;

    @Column(name = "reservation_name", length = 100, nullable = false)
    private String reservationName;

    @Column(name = "reservation_request")
    private String requestContent;

}
