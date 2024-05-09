package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Category;
import com.ssg.dsilbackend.domain.Members;
import com.ssg.dsilbackend.domain.Restaurant;
import com.ssg.dsilbackend.domain.Review;
import com.ssg.dsilbackend.dto.userManage.OwnerManageDTO;
import com.ssg.dsilbackend.dto.userManage.RestaurantRegisterDTO;
import com.ssg.dsilbackend.dto.userManage.ReviewManageDTO;
import com.ssg.dsilbackend.dto.userManage.UserManageDTO;
import com.ssg.dsilbackend.repository.ReviewManageRepository;
import com.ssg.dsilbackend.repository.UserManageRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Builder
public class UserManageServiceImpl implements UserManageService {

    private final UserManageRepository userManageRepository;
    private final ReviewManageRepository reviewManageRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Restaurant restaurant;
    private final ModelMapper modelMapper;


    //    ----------------------------------------------- User

    // 회원가입
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

    // 사용자 이메일로 회원 정보 갖고오기
    @Override
    public UserManageDTO getUserInfoByEmail(UserManageDTO userManageDTO) {
        Members userData = userManageRepository.findByEmail(userManageDTO.getEmail());

        UserManageDTO userInfo = new UserManageDTO().builder()
                .email(userData.getEmail())
                .name(userManageDTO.getName())
                .tel(userManageDTO.getTel())
                .address(userManageDTO.getAddress())
                .postcode(userManageDTO.getPostcode())
                .build();

        return userInfo;
    }

    // 회원 정보 수정
    @Override
    public void modifyUserInfo(UserManageDTO userManageDTO) {
        Members userInfo = userManageRepository.findByEmail(userManageDTO.getEmail());

        userManageRepository.save(userInfo.builder()
                .email(userInfo.getEmail())
                .name(userManageDTO.getName())
                .tel(userManageDTO.getTel())
                .address(userManageDTO.getAddress())
                .postcode(userManageDTO.getPostcode())
                .build());
    }

    // 회원 정보 탈퇴
    @Override
    public void deleteUserInfo(UserManageDTO userManageDTO) {
        userManageRepository.updateStatusByEmail(userManageDTO.getEmail(), false);
    }

//    ----------------------------------------------- Owner

    // 하나의 식당 관리자가 여러 식당들을 갖고 있음 -> 식당을 선택할 수 있는 사이드 바
    @Override
    public List<String> getRestaurantNameByEmail(UserManageDTO userManageDTO) {
        List<String> restaurantNameList = userManageRepository.getAllRestaurantByEmail(userManageDTO.getEmail());
        return restaurantNameList;
    }

    // 식당 관리자의 회원 정보 수정
    @Override
    public void modifyOwnerInfo(OwnerManageDTO ownerManageDTO) {
        Members userData = userManageRepository.findByEmailAndRestaurantName(ownerManageDTO.getEmail(), restaurant.getName());

        userData.builder()
                .password(bCryptPasswordEncoder.encode(ownerManageDTO.getPassword()))
                .tel(ownerManageDTO.getTel())
                .address(ownerManageDTO.getAddress())
                .postcode(ownerManageDTO.getPostcode())
                .build();
    }

    //    ----------------------------------------------- Admin

    // 일반회원 목록
    @Override
    public List<UserManageDTO> getUserInfoList() {

        List<Members> userDataList = userManageRepository.findAllByPermission("USER");

        List<UserManageDTO> userList = userDataList.stream()
                .map(member -> modelMapper.map(member, UserManageDTO.class))
                .collect(Collectors.toList());

        return userList;
    }

    // 식당 목록
    @Override
    public List<OwnerManageDTO> getOwnerInfoList() {

        List<Members> ownerDataList = userManageRepository.findAllByPermission("OWNER");

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

//        userManageRepository.save(restaurantData);

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

    @Override
    public List<ReviewManageDTO> getReviewInfoList() {
        List<Review> reviewList = reviewManageRepository.getReviewByDeleteStatus(true);

        return List.of();
    }


}
