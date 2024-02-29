package com.example.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public enum BookingStatus {
    BOOKED, WAITING, FAILED, CANCELLED
}
