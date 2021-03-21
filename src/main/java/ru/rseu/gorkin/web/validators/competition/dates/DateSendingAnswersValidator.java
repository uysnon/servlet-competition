package ru.rseu.gorkin.web.validators.competition.dates;

import ru.rseu.gorkin.web.validators.NormalValidationResultable;
import ru.rseu.gorkin.web.validators.ValidationResultable;
import ru.rseu.gorkin.web.validators.Validator;
import ru.rseu.gorkin.web.validators.user.password.Pair;

import java.time.Instant;

public class DateSendingAnswersValidator implements Validator<Pair<Instant, Instant>> {
    @Override
    public ValidationResultable validate(Pair<Instant, Instant> valueToValidate) {
        if (!valueToValidate.fst.isAfter(valueToValidate.snd)) {
            return new NormalValidationResultable();
        } else {
            return CompetitionDatesValidatorResults.DATE_MUST_BE_AFTER_END_REGISTRATION;
        }
    }
}
