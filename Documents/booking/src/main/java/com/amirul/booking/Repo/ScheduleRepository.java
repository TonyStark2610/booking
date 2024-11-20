package com.amirul.booking.Repo;

import com.amirul.booking.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByCountry(String country);

    @Query("SELECT s FROM Schedule s JOIN s.bookings b WHERE b.user.id = :userId AND s.classTime = :classTime")
    List<Schedule> findOverlappingClasses(@Param("userId") Long userId, @Param("classTime") LocalDateTime classTime);
}
