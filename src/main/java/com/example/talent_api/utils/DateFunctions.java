package com.example.talent_api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFunctions {

    public static Date formatDate(String nodeDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date dateListed = formatter.parse(nodeDate);
        return dateListed;
    }
}
