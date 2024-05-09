package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.dto.reserve.ReserveDTO;

public interface ReserveService {
    void processReservation(ReserveDTO reservationDTO);
}
