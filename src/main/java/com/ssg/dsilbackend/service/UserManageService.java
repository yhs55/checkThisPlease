package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.dto.userManage.OwnerManageDTO;
import com.ssg.dsilbackend.dto.userManage.RestaurantRegisterDTO;
import com.ssg.dsilbackend.dto.userManage.ReviewManageDTO;
import com.ssg.dsilbackend.dto.userManage.UserManageDTO;

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
    void registerRestaurantInfo(RestaurantRegisterDTO restaurantRegisterDTO);
    List<ReviewManageDTO> getReviewInfoList();

}
