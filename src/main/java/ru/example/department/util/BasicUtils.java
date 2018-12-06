package ru.example.department.util;


import org.apache.commons.lang3.StringUtils;
import ru.example.department.model.core.dto.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.example.department.util.BasicConst.DATE_FORMAT_DD_MM_YYYY;
import static ru.example.department.util.BasicConst.DATE_FORMAT_DD_MM_YYYY_HH_MM;

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


    public static ReadRequest mappingBasicReadRequest(Integer page, Integer size, String sort, String filtersStr) {
        List<String> filterList = splitFilters(filtersStr);

        String[] sortArr = StringUtils.split(sort, '-');
        SortData sortData = null;
        if (sortArr != null && sortArr.length == 2) {
            sortData = new SortData(sortArr[0], sortArr[1]);
        }
        List<Filter> filters = new ArrayList<>();
        for(String filterStr : filterList) {
            filters.add(splitTypeFieldComparisonValue(filterStr));
        }
        return new ReadRequest(page, size, sortData, filters);
    }

    private static List<String> splitFilters(String filtersStr) {
        List<String> filterList = new ArrayList<>();
        if (StringUtils.isNoneEmpty(filtersStr)) {
            Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
            Matcher matcher = pattern.matcher(filtersStr);
            while(matcher.find()) {
                filterList.add(matcher.group(1));
            }
        }
        return filterList;
    }

    private static Filter splitTypeFieldComparisonValue(String filterStr) {
        String[] filterArr = StringUtils.split(filterStr, '|');
        String type = filterArr[0].toUpperCase();
        String field = filterArr[1];
        String comparison = filterArr[2].toUpperCase();
        String valuesStr = filterArr[3];

        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(valuesStr);
        ArrayList<String> values = new ArrayList<>();
        while (matcher.find()) {
            values.add(matcher.group(1));
        }
        return new Filter(FilterTypeEnum.valueOf(type), field, FilterComparisonEnum.valueOf(comparison), values);
    }

}
