package service;

import entity.BookingInformationWrapper;
import pojo.BookingRequest;
import service.implementation.BookingStoredInRAMRepositoryImpl;
import util.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BookingService {
    private BookingRepository bookingRepository;

    public BookingService() {
        this.bookingRepository = new BookingStoredInRAMRepositoryImpl();
    }

    public synchronized boolean addRoomBooking(BookingRequest bookingRequest) throws ParseException {
        BookingInformationWrapper bookingInformation = createBookingInformationByRequest(bookingRequest);
        //TODO: need to validate the fields of the format
        return isWithinOfficeHour(bookingInformation, bookingRequest) && validateTimeConflictAndSave(bookingInformation);
    }

    public List<BookingInformationWrapper> fetchRoomAvailabilities() {
        List<BookingInformationWrapper> bookingInformationWrappers = bookingRepository.findAll();
        Collections.sort(bookingInformationWrappers);
        return bookingInformationWrappers;
    }

    private boolean isWithinOfficeHour(BookingInformationWrapper bookingInformation, BookingRequest bookingRequest) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HHmm");
        Date officeBeginHour = dateFormat.parse(bookingInformation.getMeetingDateText().concat(" ").concat(bookingRequest.getOfficeBeginHour()));
        Date officeEndHour = dateFormat.parse(bookingInformation.getMeetingDateText().concat(" ").concat(bookingRequest.getOfficeEndHour()));

        Date meetingBeginDate = bookingInformation.getBeginDate();
        Date meetingEndDate = bookingInformation.getEndDate();
        return (meetingBeginDate.equals(officeBeginHour) || meetingBeginDate.after(officeBeginHour))
                && (meetingEndDate.equals(officeEndHour) || meetingEndDate.before(officeEndHour));
    }

    private boolean validateTimeConflictAndSave(BookingInformationWrapper newBookingRequest) {
        List<BookingInformationWrapper> bookedList = bookingRepository.findAll();
        int size = bookedList.size();

        for (int i = 0; i < size; i++) {
            BookingInformationWrapper bookedInformation = bookedList.get(i);
            if (newBookingRequest.getBookingSubmissionTimestamp() == bookedInformation.getBookingSubmissionTimestamp())
                return false;

            if (!isBookingTimeValid(newBookingRequest, bookedInformation)) {
                //TODO: use logging
                return false;
            }
        }
        bookingRepository.save(newBookingRequest);
        return true;
    }

    private boolean isBookingTimeValid(BookingInformationWrapper bookingRequest, BookingInformationWrapper bookedInformation) {
        Date requestBeginDate = bookingRequest.getBeginDate();
        Date requestEndDate = bookingRequest.getEndDate();
        Date bookedBeginDate = bookedInformation.getBeginDate();
        Date bookedEndDate = bookedInformation.getEndDate();

        return (requestBeginDate.before(bookedBeginDate) && (requestEndDate.before(bookedBeginDate) || requestEndDate.equals(bookedBeginDate)))
                || (requestEndDate.after(bookedEndDate) && (requestBeginDate.after(bookedEndDate) || requestBeginDate.equals(bookedEndDate)));
    }

    private BookingInformationWrapper createBookingInformationByRequest(BookingRequest bookingRequest) throws ParseException {
        BookingInformationWrapper bookingInformation = new BookingInformationWrapper();

        Date submission = TimeUtil.timeStringToDate(bookingRequest.getSubmissionTime());
        bookingInformation.setBookingSubmissionTimestamp(submission.getTime());

        bookingInformation.setMeetingBeginTime(bookingRequest.getMeetingBeginTime());
        bookingInformation.setMeetingDurationInHour(bookingRequest.getDuration());
        bookingInformation.setBookerId(bookingRequest.getBookerId());

        return bookingInformation;
    }
}
