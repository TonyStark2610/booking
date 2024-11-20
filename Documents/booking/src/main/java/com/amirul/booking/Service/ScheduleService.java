package com.amirul.booking.Service;

import com.amirul.booking.Entity.Booking;
import com.amirul.booking.Entity.Schedule;
import com.amirul.booking.Entity.User;
import com.amirul.booking.Repo.BookingRepository;
import com.amirul.booking.Repo.ScheduleRepository;
import com.amirul.booking.Repo.UserRepository;
import jakarta.persistence.Cacheable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

   // @Cacheable(value = "scheduleSlots", key = "#scheduleId")
    public int getAvailableSlots(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
        return schedule.getAvailableSlots();
    }

    public List<Schedule> getSchedulesByCountry(String country) {
        return scheduleRepository.findByCountry(country);
    }

    public boolean checkOverlappingClass(Long userId, LocalDateTime classTime) {
        List<Schedule> overlappingSchedules = scheduleRepository.findOverlappingClasses(userId, classTime);
        return overlappingSchedules.isEmpty(); // True if no overlap exists
    }
}


