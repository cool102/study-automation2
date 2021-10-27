package at.study.redmine.utils;

import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CompareUtils {


    private static final Comparator<String> LOGIN_COMPARATOR = (l1, l2) -> {
        return l1.compareTo(l2);
    };
    private static final Comparator<String> DATE_DESC_COMPARATOR = (s1, s2) -> {
        LocalDateTime d1 = LocalDateTime.parse(s1, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        LocalDateTime d2 = LocalDateTime.parse(s2, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        return d2.compareTo(d1);
    };
    private static Comparator<String> TEXT_DECS_COMPARATOR = (l1, l2) -> l2.toLowerCase().compareTo(l1.toLowerCase());
    private static Comparator<String> TEXT_ASC_COMPARATOR = TEXT_DECS_COMPARATOR.reversed();

    public static void assertListSortedByDateDesc(List<String> dates) {

        List<String> sortedDates = new ArrayList<>(dates);
        sortedDates.sort(DATE_DESC_COMPARATOR);
        Assert.assertEquals(dates, sortedDates);

    }

    public static void assertEqualsListSortedByTextDesc(List<String> logins) {
        List<String> sortedLogins = new ArrayList<>(logins);
        sortedLogins.sort(TEXT_DECS_COMPARATOR);
        Assert.assertEquals(logins, sortedLogins);
    }
    public static void assertEqualsListSortedByTextAsc(List<String> text) {
        List<String> sortedText = new ArrayList<>(text);
        sortedText.sort(TEXT_ASC_COMPARATOR);
        Assert.assertEquals(text, sortedText);
    }

    public static void assertFalseListSortedByTextAsc(List<String> text){
        List<String> sortedText = new ArrayList<>(text);
        sortedText.sort(TEXT_ASC_COMPARATOR);
        Assert.assertFalse(text.equals(sortedText));

    }




}
