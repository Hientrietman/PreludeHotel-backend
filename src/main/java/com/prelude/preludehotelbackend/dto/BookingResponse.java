package com.prelude.preludehotelbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse {
    Long id;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    int numberOfAdults;
    int numberOfChildren;
    int numberOfGuests;
    String bookingConfirmationCode;
    UserResponse user;
    RoomResponse room;
}
