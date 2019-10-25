package pojo;

public class BookingRequest {
    private String officeBeginHour, officeEndHour, submissionTime, bookerId, meetingBeginTime;
    private int duration;

    public String getOfficeBeginHour() {
        return officeBeginHour;
    }

    public void setOfficeBeginHour(String officeBeginHour) {
        this.officeBeginHour = officeBeginHour;
    }

    public String getOfficeEndHour() {
        return officeEndHour;
    }

    public void setOfficeEndHour(String officeEndHour) {
        this.officeEndHour = officeEndHour;
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getBookerId() {
        return bookerId;
    }

    public void setBookerId(String bookerId) {
        this.bookerId = bookerId;
    }

    public String getMeetingBeginTime() {
        return meetingBeginTime;
    }

    public void setMeetingBeginTime(String meetingBeginTime) {
        this.meetingBeginTime = meetingBeginTime;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}