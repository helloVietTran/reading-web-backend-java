package com.viettran.reading_story_web.repository;

import com.viettran.reading_story_web.entity.mysql.Story;
import com.viettran.reading_story_web.enums.Gender;
import com.viettran.reading_story_web.enums.StoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {
    Page<Story> findAll(Pageable pageable);

    @Query("SELECT s FROM Story s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.otherName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.authorName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Story> searchByKeyword(String keyword, Pageable pageable);
    //LIKE LOWER(CONCAT('%', :keyword, '%')) cú pháp tìm kiếm substring

    @Query("SELECT s FROM Story s JOIN s.genres g " +
            "WHERE g.queryCode = :queryCode " +
            "AND (:status IS NULL OR s.status = :status) ")
    Page<Story> findStoriesByGenreQueryCodeAndFilters(
            @Param("queryCode") Integer queryCode,
            @Param("status") StoryStatus status,
            Pageable pageable
    );

    @Query("SELECT s FROM Story s ORDER BY s.viewCount DESC")
    List<Story> findTop10ByOrderByViewCountDesc(Pageable pageable);

    Page<Story> findByGenderNot(Gender Gender, Pageable pageable);

    Page<Story> findByHotTrue(Pageable pageable);
}
