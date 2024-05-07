package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.dto.myDinig.MydiningBookmarkDTO;
import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.dto.myDinig.ReservationUpdateRequest;
import com.ssg.dsilbackend.dto.myDinig.ReviewRequest;

import java.util.List;

public interface MyDiningService {
    List<MydiningReserveDTO> getMydiningReserveListById(Long id);

    void registerReview(ReviewRequest reviewRequest);

    boolean cancelReservation(Long reservationId, ReservationUpdateRequest reservationUpdateRequest);

    List<MydiningBookmarkDTO> getMydiningBookmarksListById(Long id);

    boolean removeBookmark(Long bookmarkId);
}
