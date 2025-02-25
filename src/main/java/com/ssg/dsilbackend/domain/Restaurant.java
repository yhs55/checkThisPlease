package com.ssg.dsilbackend.domain;

import com.ssg.dsilbackend.dto.Crowd;
import com.ssg.dsilbackend.dto.userManage.OwnerManageDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
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

    @Column(name = "restaurant_description", length = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members member;


    public void updateRestaurantInfo(OwnerManageDTO ownerManageDTO) {
        this.tel = ownerManageDTO.getTel();
        this.address = ownerManageDTO.getAddress();
    }


    public void setRestaurantCrowd(Crowd crowd) {
        this.crowd = crowd;
    }

    public void updateRestaurant(String tel, String img, Long deposit, Long tableCount, String description){
        this.tel = tel;
        this.img = img;
        this.deposit = deposit;
        this.tableCount = tableCount;
        this.description = description;
    }


}

