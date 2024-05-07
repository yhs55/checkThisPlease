package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Bookmark;
import com.ssg.dsilbackend.domain.Members;
import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.domain.Review;
import com.ssg.dsilbackend.dto.myDinig.MydiningBookmarkDTO;
import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.dto.myDinig.ReservationUpdateRequest;
import com.ssg.dsilbackend.dto.myDinig.ReviewRequest;
import com.ssg.dsilbackend.exception.MemberNotFoundException;
import com.ssg.dsilbackend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MyDiningServiceImpl implements MyDiningService {
    private final ModelMapper modelMapper;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final MembersRepository membersRepository;
    private final ReviewRepository reviewRepository;
    private final BookmarkRepository bookmarkRepository;


    // 사용자 아이디번호 받아서 예약리스트 출력
    public List<MydiningReserveDTO> getMydiningReserveListById(Long id) {
        Members member = membersRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + id));

        List<Reservation> reservations = reservationRepository.findByMembers(member);

        return reservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    //식당 평점 가져오기, 카운트 구해서 빌더로 생성하기(MydiningReserveDTO)
    private MydiningReserveDTO convertToDto(Reservation reservation) {
        Double averageScore = reviewRepository.findAverageScoreByRestaurantId(reservation.getRestaurant().getId());
        if (averageScore == null) averageScore = 0.0; // 평균 점수가 없는 경우 0.0으로 처리
        long reviewCount = reviewRepository.countByReservationRestaurantId(reservation.getRestaurant().getId()); // 리뷰 개수 조회

        return MydiningReserveDTO.from(reservation, averageScore, reviewCount);
    }

    // 사용자 아이디번호 받아서 즐겨찾기 리스트 출력
    public List<MydiningBookmarkDTO> getMydiningBookmarksListById(Long id) {

        List<Bookmark> bookmarks = bookmarkRepository.findByMembersId(id);
        return bookmarks.stream()
                .map(b -> convertToDto(b))
                .collect(Collectors.toList());
    }

    //식당 평점 가져오기, 카운트 구해서 빌더로 생성하기(MydiningBookmarkDTO)
    private MydiningBookmarkDTO convertToDto(Bookmark bookmark) {
        Double averageScore = reviewRepository.findAverageScoreByRestaurantId(bookmark.getRestaurant().getId());
        long reviewCount = reviewRepository.countByReservationRestaurantId(bookmark.getRestaurant().getId()); // 리뷰 개수 조회
        return MydiningBookmarkDTO.from(bookmark, averageScore, reviewCount);

    }

    // 리뷰 등록하기
    @Transactional
    public void registerReview(ReviewRequest reviewRequest) {
        // 예약 객체 조회
        Reservation reservation = reservationRepository.findById(reviewRequest.getReservationId())
                .orElseThrow(() -> new RuntimeException("예약을 찾을 수 없습니다."));

        // Review 객체 생성
        Review review = Review.builder()
                .reservation(reservation) // 연결된 예약 객체
                .content(reviewRequest.getReviewContents()) // 리뷰 내용
                .registerDate(reviewRequest.getRegisterDate()) // 리뷰 등록 날짜
                .score(reviewRequest.getReviewScore()) // 평점
                .deleteStatus(false) // 삭제 상태 초기화
                .build();

        // Review 객체 저장
        reviewRepository.save(review);
    }

    // 예약 아이디 받아서 예약 cancle로 만들기
    @Transactional
    public boolean cancelReservation(Long reservationId, ReservationUpdateRequest updateRequest) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElse(null);

        if (reservation != null) {
            reservation.setReservationStateName(updateRequest.getReservationState());
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    // 즐겨찾기 아이디 받아서 즐겨찾기 삭제
    @Transactional
    public boolean removeBookmark(Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElse(null);
        if (bookmark != null) {
            bookmarkRepository.delete(bookmark);
            return true;
        }
        return false;
    }

}



