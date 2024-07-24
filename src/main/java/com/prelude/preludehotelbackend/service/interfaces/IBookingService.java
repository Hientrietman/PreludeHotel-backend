package com.prelude.preludehotelbackend.service.interfaces;

import com.prelude.preludehotelbackend.dto.Response;
import com.prelude.preludehotelbackend.model.Booking;

public interface IBookingService {
    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
