package entity;

import java.text.ParseException;

public class BookingInformation {
    private long bookingSubmissionTimestamp;
    private String meetingBeginTime;
    private int meetingDurationInHour;
    private String bookerId;

    public long getBookingSubmissionTimestamp() {
        return bookingSubmissionTimestamp;
    }

    public void setBookingSubmissionTimestamp(long bookingSubmissionTimestamp) {
        this.bookingSubmissionTimestamp = bookingSubmissionTimestamp;
    }

    public String getMeetingBeginTime() {
        return meetingBeginTime;
    }

    public void setMeetingBeginTime(String meetingBeginTime) throws ParseException {
        this.meetingBeginTime = meetingBeginTime;
    }

    public int getMeetingDurationInHour() {
        return meetingDurationInHour;
    }

    public void setMeetingDurationInHour(int meetingDurationInHour) throws ParseException {
        this.meetingDurationInHour = meetingDurationInHour;
    }

    public String getBookerId() {
        return bookerId;
    }

    public void setBookerId(String bookerId) {
        this.bookerId = bookerId;
    }
}
