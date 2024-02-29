package com.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity(name = "user_bms")
public class User extends BaseModel {
    private String name;
    private String phoneNumber;
    private String email;
    private String password;

    @OneToMany
    private List<Booking> bookingList;
}
