package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.*;
import com.ssg.dsilbackend.dto.CategoryName;
import com.ssg.dsilbackend.dto.Crowd;
import com.ssg.dsilbackend.dto.FacilityName;
import com.ssg.dsilbackend.dto.PermissionRole;
import com.ssg.dsilbackend.dto.userManage.*;
import com.ssg.dsilbackend.repository.*;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Builder
public class UserManageServiceImpl implements UserManageService {

    private final UserManageRepository userManageRepository;
    private final ReviewManageRepository reviewManageRepository;
    private final RestaurantManageRepository restaurantManageRepository;
    private final ReplyManageRepository replyManageRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final PointManageRepository pointManageRepository;
    private final PermissionManageRepository permissionManageRepository;
    private final FacilityRepository facilityRepository;


    //    ----------------------------------------------- User

    // 회원가입 #테스트 성공
    @Override
    public void signUp(UserManageDTO userManageDTO) {

        String email = userManageDTO.getEmail();

        Boolean isExist = userManageRepository.existsByEmail(email);

        if (isExist) {
            return;
        }
        Members userData = new Members();

        userData.builder()
                .email(userManageDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(userManageDTO.getPassword()))
                .name(userManageDTO.getName())
                .tel(userManageDTO.getTel())
                .address(userManageDTO.getAddress())
                .postcode(userManageDTO.getPostcode())
                .build();

        userManageRepository.save(userData);
    }

    // 사용자 이메일로 회원 정보 갖고오기 #테스트 성공
    @Override
    public UserManageDTO getUserInfoByEmail(UserManageDTO userManageDTO) {
        Optional<Members> userData = userManageRepository.findByEmail(userManageDTO.getEmail());

        Members members = userData.orElseThrow(() -> new RuntimeException("User not found"));

        UserManageDTO userInfo = new UserManageDTO().builder()
                .email(members.getEmail())
                .name(members.getName())
                .tel(members.getTel())
                .address(members.getAddress())
                .postcode(members.getPostcode())
                .build();

        return userInfo;
    }

    // 회원 정보 수정 #테스트 성공 -> 수정이 아닌 새로운 객체 생성됨
    @Override
    public void modifyUserInfo(UserManageDTO userManageDTO) {
        Optional<Members> userInfo = userManageRepository.findByEmail(userManageDTO.getEmail());

        Members members = userInfo.orElseThrow(() -> new RuntimeException("User not found"));

        Members userData = members.builder()
                .email(members.getEmail())
                .name(members.getName())
                .tel(members.getTel())
                .address(members.getAddress())
                .postcode(members.getPostcode())
                .build();

        userManageRepository.save(userData);
    }

    // 회원 정보 탈퇴 #테스트 성공
    @Override
    public void deleteUserInfo(UserManageDTO userManageDTO) {

        Optional<Members> userInfo = userManageRepository.findByEmail(userManageDTO.getEmail());
        Members members = userInfo.orElseThrow(() -> new RuntimeException("User not found"));

        members.setMemberState(false);
    }

//    ----------------------------------------------- Owner

    // 하나의 식당 관리자가 여러 식당들을 갖고 있음 -> 식당을 선택할 수 있는 사이드 바 #테스트 성공
    @Override
    public List<String> getRestaurantNameByEmail(UserManageDTO userManageDTO) {
        List<Restaurant> restaurantList = restaurantManageRepository.getRestaurantsByMemberId(userManageDTO.getId());

        return restaurantList.stream().map(restaurant -> restaurant.getName()).collect(Collectors.toList());
    }

    // 식당 관리자의 회원 정보 수정
    @Override
    public void modifyOwnerInfo(OwnerManageDTO ownerManageDTO) {
//        Members userData = userManageRepository.findByEmailAndRestaurantName(ownerManageDTO.getEmail(), restaurant.getName());

//        Optional<Members> userInfo = userManageRepository.findByEmail(ownerManageDTO.getEmail());
//        Members members = userInfo.orElseThrow(() -> new RuntimeException("User not found"));
//
//        restaurantManageRepository.getRestaurantsByMemberId(members.getId());
//
//        userData.builder()
//                .password(bCryptPasswordEncoder.encode(ownerManageDTO.getPassword()))
//                .tel(ownerManageDTO.getTel())
//                .address(ownerManageDTO.getAddress())
//                .postcode(ownerManageDTO.getPostcode())
//                .build();
    }

    //    ----------------------------------------------- Admin

    // 일반회원 목록
    @Override
    public List<UserManageDTO> getUserInfoList() {

        List<Members> userDataList = userManageRepository.findMembersByPermissionName("USER");

        List<UserManageDTO> userList = userDataList.stream()
                .map(member -> modelMapper.map(member, UserManageDTO.class))
                .collect(Collectors.toList());

        return userList;
    }

    // 식당 목록
    @Override
    public List<OwnerManageDTO> getOwnerInfoList() {

        List<Members> ownerDataList = userManageRepository.findMembersByPermissionName("OWNER");

        List<OwnerManageDTO> ownerList = ownerDataList.stream()
                .map(member -> modelMapper.map(member, OwnerManageDTO.class))
                .collect(Collectors.toList());

        return ownerList;
    }

    // 등록해줘야 하는 식당
    @Transactional
    public void registerRestaurantInfo(RestaurantRegisterDTO dto) {

        // 회원 정보 생성 및 저장
        Permission permission = permissionManageRepository.findByPermission(PermissionRole.OWNER);
        Members newMember = Members.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getUserName())
                .tel(dto.getUserTel())
                .address(dto.getUserAddress())
                .postcode(dto.getPostcode())
                .registerNumber(dto.getRegisterNumber())
                .permission(permission)  // 저장된 Permission 객체 사용
                .status(true)
                .build();
        userManageRepository.save(newMember);

        // 레스토랑 정보 생성 및 저장
        Restaurant newRestaurant = Restaurant.builder()
                .member(newMember)
                .name(dto.getRestaurantName())
                .address(dto.getRestaurantAddress())
                .tel(dto.getRestaurantTel())
                .img(dto.getImg())
                .deposit(dto.getDeposit())
                .tableCount(dto.getTableCount())
                .crowd(Crowd.AVAILABLE)
                .description(dto.getDescription())
                .build();
        restaurantManageRepository.save(newRestaurant);

        // 카테고리 정보 생성 및 저장
        if (dto.getCategories() != null) {
            for (String categoryName : dto.getCategories()) {
                Category category = Category.builder()
                        .name(CategoryName.valueOf(categoryName))
                        .restaurant(newRestaurant)
                        .build();
                categoryRepository.save(category);
            }
        }

        // 메뉴 정보 생성 및 저장
        if (dto.getMenuDTOs() != null) {
            for (RegisterMenuDTO menuDTO : dto.getMenuDTOs()) {
                Menu menu = Menu.builder()
                        .name(menuDTO.getName())
                        .price(menuDTO.getPrice())
                        .img(menuDTO.getImg())
                        .menuInfo(menuDTO.getMenuInfo())
                        .restaurant(newRestaurant)
                        .build();
                menuRepository.save(menu);
            }
        }

        // 편의시설 정보 생성 및 저장
        if (dto.getFacilities() != null) {
            for (String facilityName : dto.getFacilities()) {
                Facility facility = Facility.builder()
                        .name(FacilityName.valueOf(facilityName))
                        .restaurant(newRestaurant)
                        .build();
                facilityRepository.save(facility);
            }
        }
    }

    // 리뷰, 댓글 정보 리스트 가져오기
    @Transactional
    public List<ReviewReplyDTO> getReviewReplyList() {
        List<ReviewReplyDTO> reviewReplyDTOS = reviewManageRepository.findReviewDetails();
        return reviewReplyDTOS;
    }

    @Transactional
    public void removeReview(Long reviewId) {
        System.out.println(reviewId+" 서비스에서 받음");
        Review review = reviewManageRepository.getById(reviewId);
        Reply reply = review.getReply();
        reviewManageRepository.deleteById(reviewId);
        replyManageRepository.delete(reply);
    }

    @Transactional
    public void removeReply(Long reviewId) {
        Review review = reviewManageRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다. ID=" + reviewId));
        Reply reply = review.getReply();
        // Reply 연관관계 제거
        review.setReplyNUll();
        reviewManageRepository.save(review);  // 업데이트된 Review 저장
        replyManageRepository.delete(reply);
    }

}
