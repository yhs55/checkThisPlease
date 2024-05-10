package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.dto.userManage.*;

import java.util.List;

public interface UserManageService {

    void signUp(UserManageDTO userManageDTO);
    UserManageDTO getUserInfoByEmail(UserManageDTO userManageDTO);
    void modifyUserInfo(UserManageDTO userManageDTO);
    void deleteUserInfo(UserManageDTO userManageDTO);

    List<String> getRestaurantNameByEmail(UserManageDTO userManageDTO);
    void modifyOwnerInfo(OwnerManageDTO ownerManageDTO);

    List<UserManageDTO> getUserInfoList();
    List<OwnerManageDTO> getOwnerInfoList();

    List<ReviewManageDTO> getReviewInfoList();

    void registerRestaurantInfo(RestaurantRegisterDTO dto);

}
