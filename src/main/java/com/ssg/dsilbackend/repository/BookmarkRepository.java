package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByMembersId(Long memberId);
}
