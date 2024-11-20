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
public class BookingService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean bookClass(Long scheduleId, Long userId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getCredits() < schedule.getRequiredCredits()) {
            throw new IllegalStateException("Not enough credits to book this class.");
        }

        if (schedule.getAvailableSlots() > 0) {
            schedule.setAvailableSlots(schedule.getAvailableSlots() - 1);
            user.deductCredits(schedule.getRequiredCredits());
            Booking booking = new Booking(user, schedule, false);
            scheduleRepository.save(schedule);
            bookingRepository.save(booking);
            return true;
        } else {
            // Add to waitlist
            Booking booking = new Booking(user, schedule, true);
            bookingRepository.save(booking);
            return false;
        }
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        Schedule schedule = booking.getSchedule();

        LocalDateTime now = LocalDateTime.now();
        if (Duration.between(now, schedule.getClassTime()).toHours() >= 4) {
            User user = booking.getUser();
            user.addCredits(schedule.getRequiredCredits());
            userRepository.save(user);
        }

        if (!booking.isWaitlisted()) {
            schedule.setAvailableSlots(schedule.getAvailableSlots() + 1);
            List<Booking> waitlist = bookingRepository.findByScheduleAndIsWaitlisted(schedule, true);
            if (!waitlist.isEmpty()) {
                Booking waitlistedBooking = waitlist.get(0);
                waitlistedBooking.setWaitlisted(false);
                waitlistedBooking.setBookingTime(LocalDateTime.now());
                schedule.setAvailableSlots(schedule.getAvailableSlots() - 1);
                bookingRepository.save(waitlistedBooking);
            }
        }

        scheduleRepository.save(schedule);
        bookingRepository.delete(booking);
    }

    @Transactional
    public void handleClassEnd(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        List<Booking> waitlist = bookingRepository.findByScheduleAndIsWaitlisted(schedule, true);
        for (Booking waitlistedBooking : waitlist) {
            // Refund credits to all waitlisted users
            User user = waitlistedBooking.getUser();
            user.addCredits(schedule.getRequiredCredits());
            userRepository.save(user);
        }

        // Clean up the waitlist after class ends
        bookingRepository.deleteAll(waitlist);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}



