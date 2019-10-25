package service.implementation;

import entity.BookingInformationWrapper;
import service.BookingInformationPrettier;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingInformationPrettierImpl implements BookingInformationPrettier {
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    @Override
    public String getPrettiedOutput(List<BookingInformationWrapper> bookingInformationWrapperList) {
        if (bookingInformationWrapperList.isEmpty()) return "booking list is empty";

        //convert the list<BookingInformation> to Map<MeetingBeginTime, List<BookingInformation>>
        Map<String, List<BookingInformationWrapper>> map =
                bookingInformationWrapperList.stream().collect(Collectors.groupingBy(BookingInformationWrapper::getMeetingDateText));

        //construct the outputs
        StringBuilder stringBuilder = new StringBuilder();
        map.entrySet().forEach(entry -> {
            stringBuilder.append(entry.getKey()).append("\n");
            entry.getValue().stream().forEach(bookingInfo -> {

                String beginTime = sdf.format(bookingInfo.getBeginDate());
                String endTime = sdf.format(bookingInfo.getEndDate());
                String booker = bookingInfo.getBookerId();

                stringBuilder.append(beginTime).append(" ")
                        .append(endTime).append(" ")
                        .append(booker).append("\n");
            });
        });

        return stringBuilder.toString();
    }
}
