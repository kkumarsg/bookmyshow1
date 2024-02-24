package com.example.demo.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public enum BookingStatus {
    BOOKED, WAITING, FAILED, CANCELLED
}
