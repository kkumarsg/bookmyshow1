package com.example.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Booking extends BaseModel{

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;
    private int amount;
    @OneToMany
    private List<Payment> paymentList;
    @OneToMany
    private List<ShowSeat> showSeatList;
    @OneToOne
    private User user;
    @OneToOne
    private Show show;
}
