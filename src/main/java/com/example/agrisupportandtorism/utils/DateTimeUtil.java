package com.example.agrisupportandtorism.utils;

import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss a");

}
