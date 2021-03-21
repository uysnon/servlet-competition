package ru.rseu.gorkin.web.utils;

import ru.rseu.gorkin.datalayer.dto.Competition;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimeUtils {
    private DateTimeFormatter dateTimeFormatter;

    public DateTimeUtils() {
        dateTimeFormatter =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .withLocale(Locale.UK)
                        .withZone(ZoneId.systemDefault());
    }

    public boolean isInFuture(Instant instant) {
        return instant.isAfter(Instant.now());
    }

    public String calculateCompetitionStatus(Competition competition) {
        if (isInFuture(competition.getEndRegistrationDate())) {
            return "Открыта регистрация";
        }

        if (isInFuture(competition.getEndSendingAnswerDate())) {
            return "Прием работ";
        }
        return "Окончен";
    }

    public String format(Instant instant){
        return dateTimeFormatter.format(instant);
    }
}
