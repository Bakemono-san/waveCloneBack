package org.odc.demo.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateComparison {
    public static boolean plusDe30mn(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime givenDateTime = LocalDateTime.parse(dateTimeStr, formatter);

        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(givenDateTime, now);

        return duration.toMinutes() <= 30;
    }

}
