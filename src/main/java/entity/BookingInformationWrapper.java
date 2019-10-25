package entity;

import util.TimeUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BookingInformationWrapper extends BookingInformation implements Comparable<BookingInformationWrapper> {

    private Date beginDate, endDate;
    private String meetingDateText;

    @Override
    public void setMeetingBeginTime(String meetingBeginTime) throws ParseException {
        super.setMeetingBeginTime(meetingBeginTime);
        this.beginDate = TimeUtil.timeStringToDate(meetingBeginTime);
        this.meetingDateText = meetingBeginTime.split(" ")[0];
    }

    @Override
    public void setMeetingDurationInHour(int meetingDurationInHour) throws ParseException {
        super.setMeetingDurationInHour(meetingDurationInHour);
        this.endDate = new Date(this.beginDate.getTime() + TimeUnit.HOURS.toMillis(meetingDurationInHour));
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public String getMeetingDateText() {
        return meetingDateText;
    }

    @Override
    public int compareTo(BookingInformationWrapper o) {
        return beginDate.compareTo(o.getBeginDate());
    }
}
