package com.example.services;

import com.example.exceptions.InvalidShowException;
import com.example.exceptions.InvalidUserException;
import com.example.exceptions.SeatsUnavailableException;
import com.example.models.*;
import com.example.dtos.BookMovieRequestDto;
import com.example.repositories.BookingRepository;
import com.example.repositories.ShowRepository;
import com.example.repositories.ShowSeatRepository;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Booking bookMovie(BookMovieRequestDto request)
            throws InvalidUserException, InvalidShowException, SeatsUnavailableException {

        Long userId = request.getUserId();
        List<Long> showSeatIds = request.getShowSeatIds();
        Long showId = request.getShowId();

        // 1. Get user details
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new InvalidUserException();
        }

        // 2. Get show details
        Optional<Show> show = showRepository.findById(showId);

        if(show.isEmpty()){
            throw new InvalidShowException();
        }

        List<ShowSeat> showSeats = reserveShowSeats(showSeatIds);

        // create the booking object
        return createBooking(user, show, showSeats);
    }

    private Booking createBooking(Optional<User> user, Optional<Show> show, List<ShowSeat> showSeats) {
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.WAITING);
        booking.setShowSeatList(showSeats);
        booking.setPaymentList(new ArrayList<>());
        booking.setAmount(calculatePrice(showSeats));
        booking.setUser(user.get());
        booking.setShow(show.get());

        return bookingRepository.save(booking);
    }

    private int calculatePrice(List<ShowSeat> showSeats) {
        // need to implement it.
        return 0;
    }

    // start transaction with isolation level as serializable.
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<ShowSeat> reserveShowSeats(List<Long> showSeatIds)
            throws SeatsUnavailableException {

        /*
        Get showseat with showSeatIds
        Check the availability
            If Locked, then check duration for which it's being locked < 10
               Just throw an error saying seat is not available
        If it's not locked or lock has expired
            mark the status as locked
            lockedAt -> set to current time.
            update showSeat record in DB.

         */

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        for(ShowSeat showSeat: showSeats){
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                    (showSeat.getShowSeatStatus().equals(ShowSeatStatus.LOCKED) &&
                            Duration.between(new Date().toInstant(),
                                    showSeat.getLockedAt().toInstant()).toMinutes()<10))){
                // throw an error saying seat is already blocked
                throw new SeatsUnavailableException();
            }
        }

        for(ShowSeat showSeat: showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
            showSeat.setLockedAt(new Date());
        }

        return showSeatRepository.saveAll(showSeats);
    }
}
