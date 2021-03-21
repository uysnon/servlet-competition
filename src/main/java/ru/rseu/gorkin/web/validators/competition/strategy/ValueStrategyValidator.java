package ru.rseu.gorkin.web.validators.competition.strategy;

import ru.rseu.gorkin.datalayer.db.oracle.StrategyAdapters;
import ru.rseu.gorkin.web.validators.NormalValidationResultable;
import ru.rseu.gorkin.web.validators.ValidationResultable;
import ru.rseu.gorkin.web.validators.Validator;
import ru.rseu.gorkin.web.validators.user.password.Pair;

public class ValueStrategyValidator implements Validator<Pair<Integer, String>> {
    @Override
    public ValidationResultable validate(Pair<Integer, String> valueToValidate) {
        int strategyId = valueToValidate.fst;
        String strategyValue = valueToValidate.snd;
        if (strategyId == StrategyAdapters.ALL_POSITIVE.getId()) {
            if ("".equals(strategyValue)){
                return new NormalValidationResultable();
            } else {
                return StrategyValidatorResults.VALUE_MUST_BE_EMPTY;
            }
        }
        if (strategyId == StrategyAdapters.MAX_NEGATIVE_MARKS_COUNT.getId()){
            try {
                int value = Integer.parseInt(strategyValue);
            } catch ( NumberFormatException e){
                return StrategyValidatorResults.VALUE_MUST_BE_INTEGER;
            }
        }
        if (strategyId == StrategyAdapters.PERCENT_POSITIVE_MARKS.getId()){
            try {
                double value = Double.parseDouble(strategyValue);
            } catch ( NumberFormatException e){
                return StrategyValidatorResults.VALUE_MUST_BE_FLOAT;
            }
        }
        return new NormalValidationResultable();
    }
}
