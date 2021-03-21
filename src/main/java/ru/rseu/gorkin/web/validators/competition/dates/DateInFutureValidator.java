package ru.rseu.gorkin.web.validators.competition.dates;

import ru.rseu.gorkin.web.validators.NormalValidationResultable;
import ru.rseu.gorkin.web.validators.ValidationResultable;
import ru.rseu.gorkin.web.validators.Validator;

import java.time.Instant;

public class DateInFutureValidator implements Validator<Instant> {
    @Override
    public ValidationResultable validate(Instant valueToValidate) {
        if (valueToValidate.isAfter(Instant.now())) {
            return new NormalValidationResultable();
        } else {
            return CompetitionDatesValidatorResults.DATE_IS_NOT_IN_FUTURE;
        }
    }
}
