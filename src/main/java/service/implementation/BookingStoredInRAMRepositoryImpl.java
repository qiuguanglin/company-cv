package service.implementation;

import entity.BookingInformationWrapper;
import service.BookingRepository;

import java.util.ArrayList;
import java.util.List;

public class BookingStoredInRAMRepositoryImpl implements BookingRepository {
    private static List<BookingInformationWrapper> bookingInformationList;

    public BookingStoredInRAMRepositoryImpl() {
        bookingInformationList = new ArrayList<>();
    }

    @Override
    public void save(BookingInformationWrapper bookingInformation) {
        this.bookingInformationList.add(bookingInformation);
    }

    @Override
    public List<BookingInformationWrapper> findAll() {
        return this.bookingInformationList;
    }
}
