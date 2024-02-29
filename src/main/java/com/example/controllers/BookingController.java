package com.example.controllers;

import com.example.dtos.BookMovieRequestDto;
import com.example.dtos.BookMovieResponseDto;
import com.example.dtos.ResponseStatus;
import com.example.exceptions.InvalidShowException;
import com.example.exceptions.InvalidUserException;
import com.example.exceptions.SeatsUnavailableException;
import com.example.models.Booking;
import com.example.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;


    public BookMovieResponseDto bookMovie(BookMovieRequestDto request){
        try {
            Booking booking = bookingService.bookMovie(request);
            return new BookMovieResponseDto(booking.getId(), booking.getAmount(),
                    ResponseStatus.SUCCESS, "Successfully booked the ticket ");
        } catch (InvalidUserException e) {
            return new BookMovieResponseDto(null, 0, ResponseStatus.FAILURE,
                    "User is not present");
        } catch (InvalidShowException e) {
            return new BookMovieResponseDto(null, 0, ResponseStatus.FAILURE,
                    "show is not present");
        } catch (SeatsUnavailableException e) {
            return new BookMovieResponseDto(null, 0, ResponseStatus.FAILURE,
                    "seats are not available");
        }
    }
}
