package com.example.talent_api.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateFunctions {

    // convert "2024-01-01 09:00:00" to Mon Jan 01 09:00:00 GMT 2024
    public static Date getDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        Date date = Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
        return date;
    }

    // convert Mon Jan 01 09:00:00 GMT 2024 to "2024-01-01 09:00:00"
    public static String getDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}
