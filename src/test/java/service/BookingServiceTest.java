package service;

import entity.BookingInformationWrapper;
import org.junit.Before;
import org.junit.Test;
import pojo.BookingRequest;
import service.implementation.BookingInformationPrettierImpl;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BookingServiceTest {
    private BookingService bookingService;
    private BookingInformationPrettier bookingInformationPrettier;
    private List<BookingInformationWrapper> bookingInformationWrappers;

    @Before
    public void setUp() {
        bookingService = new BookingService();
        bookingInformationPrettier = new BookingInformationPrettierImpl();
    }

    @Test
    public void testOutOfOfficeHour() throws ParseException {
        BookingRequest charlesBooking = createBookingRequest("2019-09-11 15:00:00", "2019-10-17 08:50:00", 2, "Charles");
        BookingRequest lisaBooking = createBookingRequest("2019-09-11 10:00:00", "2019-10-19 17:40:00", 1, "Lisa");

        bookingService.addRoomBooking(charlesBooking);
        bookingService.addRoomBooking(lisaBooking);

        List<BookingInformationWrapper> list = bookingService.fetchRoomAvailabilities();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testSameDayTimeConflict() throws ParseException {
        BookingRequest joeBooking = createBookingRequest("2019-10-18 07:00:00", "2019-10-18 15:00:00", 2, "Joe");
        BookingRequest roseBooking = createBookingRequest("2019-10-17 09:00:00", "2019-10-18 14:00:00", 2, "Rose");
        BookingRequest evaBooking = createBookingRequest("2019-10-16 09:00:00", "2019-10-18 13:30:00", 2, "Eva");

        bookingService.addRoomBooking(roseBooking); //Rose gets to submit request first
        bookingService.addRoomBooking(joeBooking);  //Joe could've had it but he's submitted after Rose
        bookingService.addRoomBooking(evaBooking); //Eva's meeting time conflicts with Rose's

        List<BookingInformationWrapper> list = bookingService.fetchRoomAvailabilities();
        assertEquals(1, list.size());
        assertTrue(existsBooker("Rose", list));
    }

    @Test
    public void testSameSubmissionTime() throws ParseException {

        BookingRequest kenBooking = createBookingRequest("2019-10-18 06:00:00", "2019-10-19 11:00:00", 3, "Ken");
        BookingRequest alaxBooking = createBookingRequest("2019-10-18 06:00:00", "2019-10-20 12:00:00", 1, "Alax");


        bookingService.addRoomBooking(kenBooking);
        bookingService.addRoomBooking(alaxBooking); //Alex submitted failed because his submission time is same as Ken's

        List<BookingInformationWrapper> list = bookingService.fetchRoomAvailabilities();
        assertEquals(1, list.size());
        assertTrue(!existsBooker("Alax", list));
    }

    private BookingRequest createBookingRequest(String submissionTime, String beginTime, int duration, String booker) {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setSubmissionTime(submissionTime);
        bookingRequest.setMeetingBeginTime(beginTime);
        bookingRequest.setDuration(duration);
        bookingRequest.setBookerId(booker);
        bookingRequest.setOfficeBeginHour("0900");
        bookingRequest.setOfficeEndHour("1730");
        return bookingRequest;
    }

    private boolean existsBooker(String booker, List<BookingInformationWrapper> outputList) {
        return outputList.stream().filter(o -> o.getBookerId().equals(booker)).findAny().isPresent();
    }

    private BookingInformationWrapper createBookingRequests(String bookerId, int durationHour, String beginDateStr) throws ParseException {
        BookingInformationWrapper bookingInformationWrapper = new BookingInformationWrapper();
        bookingInformationWrapper.setBookerId(bookerId);
        bookingInformationWrapper.setBookingSubmissionTimestamp(System.currentTimeMillis());
        bookingInformationWrapper.setMeetingBeginTime(beginDateStr);
        bookingInformationWrapper.setMeetingDurationInHour(durationHour);
        return bookingInformationWrapper;
    }
}