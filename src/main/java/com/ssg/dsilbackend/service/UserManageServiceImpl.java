package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.*;
import com.ssg.dsilbackend.dto.userManage.OwnerManageDTO;
import com.ssg.dsilbackend.dto.userManage.RestaurantRegisterDTO;
import com.ssg.dsilbackend.dto.userManage.ReviewManageDTO;
import com.ssg.dsilbackend.dto.userManage.UserManageDTO;
import com.ssg.dsilbackend.repository.ReplyManageRepository;
import com.ssg.dsilbackend.repository.RestaurantManageRepository;
import com.ssg.dsilbackend.repository.ReviewManageRepository;
import com.ssg.dsilbackend.repository.UserManageRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Builder
public class UserManageServiceImpl implements UserManageService {

    private final UserManageRepository userManageRepository;
    private final ReviewManageRepository reviewManageRepository;
    private final RestaurantManageRepository restaurantManageRepository;
    private final ReplyManageRepository replyManageRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Restaurant restaurant;
    private final ModelMapper modelMapper;


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

        return restaurantList.stream().map(restaurant->restaurant.getName()).collect(Collectors.toList());
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

    // 등록해줘야 하는 식당 목록
    @Override
    public void registerRestaurantInfo(RestaurantRegisterDTO restaurantRegisterDTO) {
        String email = restaurantRegisterDTO.getEmail();

        Boolean isExist = userManageRepository.existsByEmail(email);

        if (isExist) {
            return;
        }
        Restaurant restaurantData = new Restaurant()
                .builder()
                .name(restaurantRegisterDTO.getRestaurantName())
                .tel(restaurantRegisterDTO.getTel())
                .address(restaurantRegisterDTO.getAddress())
                .img(restaurantRegisterDTO.getImg())
                .deposit(restaurantRegisterDTO.getDeposit())
                .tableCount(restaurantRegisterDTO.getTableCount())
                .build();

        restaurantManageRepository.save(restaurantData);

        Members newMember = new Members()
                .builder()
                .email(restaurantRegisterDTO.getEmail())
                .password(restaurantRegisterDTO.getPassword())
                .name(restaurantRegisterDTO.getName())
                .tel(restaurantRegisterDTO.getTel())
                .address(restaurantRegisterDTO.getAddress())
                .postcode(restaurantRegisterDTO.getPostcode())
                .registerNumber(restaurantRegisterDTO.getRegisterNumber())
                .build();
        userManageRepository.save(newMember);

        Category category = new Category();

        category.builder()
                .name(restaurantRegisterDTO.getCategory().getName())
                .restaurant(restaurantData)
                .build();

//        userManageRepository.save(category);
    }


    // 리뷰 및 댓글 삭제 관리 -> 다시 확인 필요

    @Override
    public List<ReviewManageDTO> getReviewInfoList() {
        List<Review> reviewList = reviewManageRepository.getReviewByDeleteStatus(true);

        // Review 객체들의 reply_id를 추출하여 해당하는 Reply 객체를 가져옴
        List<Reply> replyList = reviewList.stream()
                .map(review -> review.getReply().getId())
                .map(replyId -> replyManageRepository.findById(replyId.intValue()))
                .filter(Optional::isPresent) // Optional 객체가 존재하는지 확인
                .map(Optional::get) // Optional 객체를 추출하여 Reply 객체로 변환
                .collect(Collectors.toList());


        List<ReviewManageDTO> reviewReplyPairDTOList = reviewList.stream()
                .map(review -> {
                    Long replyId = review.getReply().getId();
                    Reply reply = replyManageRepository.findById(replyId.intValue()).orElse(null);

                    return new ReviewManageDTO(
//                            review, reply
                    );
                })
                .collect(Collectors.toList());

        return reviewReplyPairDTOList;
    }


}
