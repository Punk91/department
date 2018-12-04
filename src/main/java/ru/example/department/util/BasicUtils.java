package ru.example.department.util;


import java.text.SimpleDateFormat;
import java.util.TimeZone;
import static ru.example.department.util.BasicConst.*;

public class BasicUtils {

    private BasicUtils() {
        throw new IllegalStateException("Utility class");
    }


    public static final ThreadLocal<SimpleDateFormat> simpleDateFormat = ThreadLocal.withInitial(() -> {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return dateFormat;
    });

    public static final ThreadLocal<SimpleDateFormat> simpleDateTimeFormat = ThreadLocal.withInitial(() -> {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY_HH_MM);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return dateFormat;
    });
}
