import entity.BookingInformationWrapper;
import pojo.BookingRequest;
import service.BookingInformationPrettier;
import service.BookingService;
import service.implementation.BookingInformationPrettierImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static String OFFICE_HOUR;
    private BookingService bookingService;
    private BookingInformationPrettier bookingInformationPrettier;

    private static final String[] STEP_CAPTIONS = new String[]{
            "TYPE IN SUBMISSION TIME AT FORMAT \" YYYY-MM-DD HH:MM:SS name\":",
            "TYPE IN MEETING BECING TIME AND DURATION AT FORMAT \"YYYY-MM-DD HH:MM duration\":",
    };

    private static final String COMMAND_CAPTION = "BOOKED SUCCESSFULLY, NOW YOU CAN:\n1.TYPE \"quit\" TO QUIT\n2.PRESS \"enter\" TO CONTINUE BOOKING";


    public Main() {
        this.bookingService = new BookingService();
        bookingInformationPrettier = new BookingInformationPrettierImpl();
    }

    public static void main(String[] args) throws Exception {
        new Main().start();
    }

    private void start() throws Exception {
        //TODO: validate the format of each input
        Scanner scanner = new Scanner(System.in);
        System.out.println("TYPE IN OFFICE HOURS AT FORMAT \"HHmm HHmm\":");
        OFFICE_HOUR = scanner.nextLine();

        String[] content = new String[STEP_CAPTIONS.length];

        do {
            for (int i = 0; i < content.length; i++) {
                System.out.println(STEP_CAPTIONS[i]);
                content[i] = scanner.nextLine();
            }

            BookingRequest bookingRequest;
            try {
                bookingRequest = createBookingRequest(content);
            } catch (Exception e) {
                System.out.println("input processing exception." + e.getLocalizedMessage());
                throw e;
            }

            if (bookingService.addRoomBooking(bookingRequest)) {
                list();
                System.out.println(COMMAND_CAPTION);
                String command = scanner.nextLine();

                if ("quit".equalsIgnoreCase(command)) break;
            } else {
                System.out.println("BOOKING TIME VALIDATION FAILED");
                list();
            }
        } while (true);
    }

    private void list() {
        List<BookingInformationWrapper> list = bookingService.fetchRoomAvailabilities();
        System.out.println(bookingInformationPrettier.getPrettiedOutput(list));
    }

    private BookingRequest createBookingRequest(String[] content) throws Exception {
        BookingRequest bookingRequest = new BookingRequest();

        String[] officeHours = OFFICE_HOUR.split(" ");
        bookingRequest.setOfficeBeginHour(officeHours[0]);
        bookingRequest.setOfficeEndHour(officeHours[1]);

        String[] submissionTimeAndUserId = content[0].split(" ");
        bookingRequest.setSubmissionTime(submissionTimeAndUserId[0].concat(" ").concat(submissionTimeAndUserId[1]));
        bookingRequest.setBookerId(submissionTimeAndUserId[2]);

        String[] meetingBeginTimeAndDuration = content[1].split(" ");
        bookingRequest.setMeetingBeginTime(meetingBeginTimeAndDuration[0].concat(" ").concat(meetingBeginTimeAndDuration[1]).concat(":00"));
        bookingRequest.setDuration(Integer.parseInt(meetingBeginTimeAndDuration[2]));

        return bookingRequest;
    }
}
