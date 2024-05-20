package com.ssg.dsilbackend.jwt;

import com.ssg.dsilbackend.domain.Members;
import com.ssg.dsilbackend.domain.Permission;
import com.ssg.dsilbackend.dto.PermissionRole;
import com.ssg.dsilbackend.dto.userManage.UserManageDTO;
import com.ssg.dsilbackend.oAuth2.CustomOAuth2User;
import com.ssg.dsilbackend.repository.PermissionManageRepository;
import com.ssg.dsilbackend.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final PermissionManageRepository permissionManageRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // OAuth2 방식: 쿠키에서 JWT를 찾음
//        String authorization = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("Authorization")) {
//                    authorization = cookie.getValue();
//                }
//            }
//        }
//
//        // 폼 로그인 방식: 헤더에서 JWT를 찾음
//        if (authorization == null) {
//            authorization = request.getHeader("Authorization");
//            if (authorization != null && authorization.startsWith("Bearer ")) {
//                authorization = authorization.substring(7);
//            }
//        }
//
//        // JWT가 없거나 형식이 잘못된 경우
//        if (authorization == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = authorization;
//
//        // 토큰 소멸 시간 검증
//        if (jwtUtil.isExpired(token)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // 토큰에서 정보 획득
//        String email = jwtUtil.getEmail(token);
//        String role = jwtUtil.getRole(token);
//
//
//        PermissionRole permissionRole = PermissionRole.valueOf(role);
//        Permission permission = permissionManageRepository.findByPermission(permissionRole);
//        // 폼 로그인에서는 Permission 객체 생성
//        if (role != null) {
//            Members members = Members.builder()
//                    .email(email)
//                    .permission(permission)
//                    .build();
//
//            // UserDetails에 회원 정보 객체 담기
//            CustomUserDetails customUserDetails = new CustomUserDetails(members);
//
//            // 스프링 시큐리티 인증 토큰 생성
//            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//
//            // 세션에 사용자 등록
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//        } else {
//            // OAuth2 사용자 정보 설정
////            UserDTO userDTO = new UserDTO();
////            userDTO.setUsername(email);
////            userDTO.setRole(role);
//            UserManageDTO userDTO = UserManageDTO.builder()
//                    .email(email)
//                    .permission(permission)
//                    .build();
//
//            CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);
//
//            // 스프링 시큐리티 인증 토큰 생성
//            Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
//
//            // 세션에 사용자 등록
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//        }
//
//        filterChain.doFilter(request, response);
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    authorization = cookie.getValue();
                }
            }
        }

        if (authorization == null) {
            authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.startsWith("Bearer ")) {
                authorization = authorization.substring(7);
            }
        }

        if (authorization == null || jwtUtil.isExpired(authorization)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization;
        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        if (email != null && role != null) {
            PermissionRole permissionRole = PermissionRole.valueOf(role);
            Permission permission = permissionManageRepository.findByPermission(permissionRole);

            Members members = Members.builder()
                    .email(email)
                    .permission(permission)
                    .build();

            CustomUserDetails customUserDetails = new CustomUserDetails(members);
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}



