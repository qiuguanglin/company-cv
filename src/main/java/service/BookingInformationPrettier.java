package service;

import entity.BookingInformationWrapper;

import java.util.List;

public interface BookingInformationPrettier {
    String getPrettiedOutput(List<BookingInformationWrapper> bookingInformationWrapperList);
}
