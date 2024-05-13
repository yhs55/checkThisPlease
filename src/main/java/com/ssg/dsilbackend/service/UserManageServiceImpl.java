package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.*;
import com.ssg.dsilbackend.dto.PermissionRole;
import com.ssg.dsilbackend.dto.userManage.OwnerManageDTO;
import com.ssg.dsilbackend.dto.userManage.RestaurantRegisterDTO;
import com.ssg.dsilbackend.dto.userManage.ReviewManageDTO;
import com.ssg.dsilbackend.dto.userManage.UserManageDTO;
import com.ssg.dsilbackend.repository.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Builder
@Service
@Log4j2
public class UserManageServiceImpl implements UserManageService {

    private final UserManageRepository userManageRepository;
    private final ReviewManageRepository reviewManageRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReplyManageRepository replyManageRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
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
        Members userData = Members
                .builder()
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
    public UserManageDTO getUserInfoByEmail(String email) {
        Optional<Members> userData = userManageRepository.findByEmail(email);

        Members members = userData.orElseThrow(() -> new RuntimeException("User not found"));

        UserManageDTO userInfo = UserManageDTO
                .builder()
                .email(members.getEmail())
                .name(members.getName())
                .tel(members.getTel())
                .address(members.getAddress())
                .postcode(members.getPostcode())
                .point(members.getPoint())
                .build();

        return userInfo;
    }

    // 회원 정보 수정 #테스트 성공
    @Override
    public void modifyUserInfo(UserManageDTO userManageDTO) {
        Optional<Members> userInfo = userManageRepository.findByEmail(userManageDTO.getEmail());
        Members members = userInfo.orElseThrow(() -> new RuntimeException("User not found"));

        log.info(members);
        members.updatePassword(bCryptPasswordEncoder.encode(userManageDTO.getPassword()));
        members.updateMemberInfo(userManageDTO);
        log.info(members);
        userManageRepository.save(members);
    }

    // 회원 정보 탈퇴 #테스트 성공
    @Override
    public void deleteUserInfo(String email) {

        Optional<Members> userInfo = userManageRepository.findByEmail(email);
        Members members = userInfo.orElseThrow(() -> new RuntimeException("User not found"));

        members.setMemberState(false);
        userManageRepository.save(members);
    }

//    ----------------------------------------------- Owner

    // 하나의 식당 관리자가 여러 식당들을 갖고 있음 -> 식당을 선택할 수 있는 사이드 바
    @Override
    public List<OwnerManageDTO> getRestaurantByEmail(String email) {
        Members member = userManageRepository.findMembersByPermissionRoleAndStatusAndEmail(PermissionRole.OWNER, true,email);
        // 권한이 owner 이고, status 가 1이고, email 이 a@a1인 사장
        List<Restaurant> restaurantList = restaurantRepository.findByMemberId(member.getId());
        // 그 사장의 member_id 를 갖고 있는 매장 목록

        List<OwnerManageDTO> ownerWithRestaurants = new ArrayList<>();
        // ownerManageDTO 에 매장 목록을 담고 그에 맞는 사장 정보 꽂기
        for (Restaurant restaurant: restaurantList){
            OwnerManageDTO ownerManageDTO = OwnerManageDTO.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .password(member.getPassword())
                    .permission(member.getPermission())
                    .ownerName(member.getName())
                    .name(restaurant.getName())
                    .tel(restaurant.getTel())
                    .address(restaurant.getAddress())
                    .registerNumber(member.getRegisterNumber())
                    .build();
            // OwnerManageDTO 를 ownerWithRestaurants 리스트에 추가합니다.
            ownerWithRestaurants.add(ownerManageDTO);
        }

        log.info(ownerWithRestaurants);
        return ownerWithRestaurants;
    }

    @Override
    public void modifyOwnerData(OwnerManageDTO ownerManageDTO) {
        Optional<Members> userInfo = userManageRepository.findByEmail(ownerManageDTO.getEmail());
        Members members = userInfo.orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurantInfo = restaurantRepository.findRestaurantByName(ownerManageDTO.getName());
        restaurantInfo.updateRestaurantInfo(ownerManageDTO);
        members.updatePassword(bCryptPasswordEncoder.encode(ownerManageDTO.getPassword()));
        members.setOwnerPostcode();

        userManageRepository.save(members);
        log.info(members);
        restaurantRepository.save(restaurantInfo);
        log.info(restaurantInfo);
    }


    // 식당 관리자의 회원 정보 수정
    @Override
    public void modifyOwnerInfo(OwnerManageDTO ownerManageDTO) {
        Optional<Members> userInfo = userManageRepository.findByEmail(ownerManageDTO.getEmail());
        Members members = userInfo.orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurantInfo = restaurantRepository.getRestaurantById(ownerManageDTO.getId());
        restaurantInfo.updateRestaurantInfo(ownerManageDTO);
        members.updatePassword(bCryptPasswordEncoder.encode(ownerManageDTO.getPassword()));

        userManageRepository.save(members);
        log.info(members);
        restaurantRepository.save(restaurantInfo);
        log.info(restaurantInfo);
    }

    //    ----------------------------------------------- Admin

    // 일반회원 목록 # 테스트 성공
    @Override
    public List<UserManageDTO> getUserInfoList() {

        List<Members> userDataList = userManageRepository.findMembersByPermissionRoleAndStatus(PermissionRole.USER, true);

        List<UserManageDTO> userList = userDataList.stream()
                .map(member -> modelMapper.map(member, UserManageDTO.class))
                .collect(Collectors.toList());

        return userList;
    }


    // 식당 목록 # 테스트 성공
    @Override
    public List<OwnerManageDTO> getOwnerInfoList() {
        List<OwnerManageDTO> ownersWithRestaurants = new ArrayList<>();

        List<Members> membersList = userManageRepository.findMembersByPermissionRoleAndStatus(PermissionRole.OWNER, true);
        for (Members member : membersList) {
            List<Restaurant> restaurants = restaurantRepository.findByMemberId(member.getId());
            for (Restaurant restaurant : restaurants) {
                OwnerManageDTO ownerDTO = getOwnerManageDTO(member, restaurant);

                ownersWithRestaurants.add(ownerDTO);
            }
        }
        log.info(ownersWithRestaurants);
        return ownersWithRestaurants;
    }

    private static OwnerManageDTO getOwnerManageDTO(Members member, Restaurant restaurant) {
        OwnerManageDTO ownerDTO = new OwnerManageDTO();
        ownerDTO.setId(restaurant.getId());
        ownerDTO.setEmail(member.getEmail());
        ownerDTO.setName(restaurant.getName()); // 식당의 이름으로 설정
        ownerDTO.setOwnerName(member.getName()); // 회원의 이름으로 설정
        ownerDTO.setPassword(member.getPassword());
        ownerDTO.setPermission(member.getPermission());
        ownerDTO.setTel(restaurant.getTel());
        ownerDTO.setAddress(restaurant.getAddress());
        ownerDTO.setRegisterNumber(member.getRegisterNumber());
        return ownerDTO;
    }

    @Override
    public void removeRestaurantByName(String name) {
        restaurantRepository.removeByName(name);
    }

    // 등록해줘야 하는 식당 목록
    @Override
    public void registerRestaurantInfo(RestaurantRegisterDTO restaurantRegisterDTO) {
        String email = restaurantRegisterDTO.getEmail();

        Boolean isExist = userManageRepository.existsByEmail(email);

        if (isExist) {
            return;
        }
        Restaurant restaurantData = Restaurant
                .builder()
                .name(restaurantRegisterDTO.getRestaurantName())
                .tel(restaurantRegisterDTO.getTel())
                .address(restaurantRegisterDTO.getAddress())
                .img(restaurantRegisterDTO.getImg())
                .deposit(restaurantRegisterDTO.getDeposit())
                .tableCount(restaurantRegisterDTO.getTableCount())
                .build();

        restaurantRepository.save(restaurantData);

        Members newMember = Members
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

//        Category category = new Category();
//
//        category.builder()
//                .name(restaurantRegisterDTO.getCategory().getName())
//                .restaurant(restaurantData)
//                .build();

//        userManageRepository.save(category);
    }


    // 리뷰 및 댓글 삭제 관리 -> 다시 확인 필요

    @Override
    public List<ReviewManageDTO> getReviewInfoList() {
        List<Review> reviewList = reviewManageRepository.getReviewByDeleteStatus(true);

        // Review 객체들의 reply_id를 추출하여 해당하는 Reply 객체를 가져옴
        List<Reply> replyList = reviewList.stream()
                .map(review -> review.getReply().getId())
                .map(replyId -> replyManageRepository.findById(replyId))
                .filter(Optional::isPresent) // Optional 객체가 존재하는지 확인
                .map(Optional::get) // Optional 객체를 추출하여 Reply 객체로 변환
                .collect(Collectors.toList());


        List<ReviewManageDTO> reviewReplyPairDTOList = reviewList.stream()
                .map(review -> {
                    Long replyId = review.getReply().getId();
                    Reply reply = replyManageRepository.findById(replyId).orElse(null);

                    return new ReviewManageDTO(
//                            review, reply
                    );
                })
                .collect(Collectors.toList());

        return reviewReplyPairDTOList;
    }


}
