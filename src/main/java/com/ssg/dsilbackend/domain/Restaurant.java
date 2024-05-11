package com.ssg.dsilbackend.domain;

import com.ssg.dsilbackend.dto.Crowd;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(name = "restaurant_name", length = 50, nullable = false)
    private String name;

    @Column(name = "restaurant_address", length = 100, nullable = false)
    private String address;

    @Column(name = "restaurant_tel", length = 20, nullable = false)
    private String tel;

    @Column(name = "restaurant_crowd", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Crowd crowd;

    @Column(name = "restaurant_img", length = 500)
    private String img;

    @Column(name = "restaurant_deposit")
    private Long deposit;

    @Column(name = "restaurant_table_count", nullable = false)
    private Long tableCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members member;

    @Column(name = "restaurant_description", length = 100)
    private String description;
}

