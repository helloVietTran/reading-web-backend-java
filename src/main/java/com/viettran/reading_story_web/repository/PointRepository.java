package com.viettran.reading_story_web.repository;

import com.viettran.reading_story_web.entity.mysql.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, String> {
    Optional<Point> findByUserId(String userId);
}
