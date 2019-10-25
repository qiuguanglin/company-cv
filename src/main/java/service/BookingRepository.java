package service;

import entity.BookingInformationWrapper;

import java.util.List;

public interface BookingRepository {
    void save(BookingInformationWrapper bookingInformationWrapper);

    List<BookingInformationWrapper> findAll();
}
