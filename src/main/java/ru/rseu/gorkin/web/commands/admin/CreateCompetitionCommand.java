package ru.rseu.gorkin.web.commands.admin;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.db.oracle.StrategyAdapters;
import ru.rseu.gorkin.datalayer.dto.CompetitionResultable;
import ru.rseu.gorkin.web.FrontController;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.validators.*;
import ru.rseu.gorkin.web.validators.competition.dates.CompetitionDatesValidatorResults;
import ru.rseu.gorkin.web.validators.competition.dates.DateInFutureValidator;
import ru.rseu.gorkin.web.validators.competition.dates.DateSendingAnswersValidator;
import ru.rseu.gorkin.web.validators.competition.experts.ExpertsValidationResults;
import ru.rseu.gorkin.web.validators.competition.strategy.ValueStrategyValidator;
import ru.rseu.gorkin.web.validators.user.password.Pair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateCompetitionCommand implements Command {
    private static final String TASK_PARAM = "task";
    private static final String END_REGISTRATION_DATE_PARAM = "end-registration-date";
    private static final String END_REGISTRATION_TIME_PARAM = "end-registration-time";
    private static final String END_SENDING_ANSWERS_DATE_PARAM = "end-sending-answers-date";
    private static final String END_SENDING_ANSWERS_TIME_PARAM = "end-sending-answers-time";
    private static final String STRATEGY_PARAM = "selected-strategy-name";
    private static final String STRATEGY_VALUE_PARAM = "strategy-value";
    private static final String EXPERTS_PARAM = "selected-experts";

    private static final String TASK_ATTRIBUTE = "task";
    private static final String END_REGISTRATION_DATE_ATTRIBUTE = "end_registration_date";
    private static final String END_REGISTRATION_TIME_ATTRIBUTE = "end_registration_time";
    private static final String END_SENDING_ANSWERS_DATE_ATTRIBUTE = "end_sending_answers_date";
    private static final String END_SENDING_ANSWERS_TIME_ATTRIBUTE = "end_sending_answers_time";
    private static final String STRATEGY_ATTRIBUTE = "selected_strategy";
    private static final String STRATEGY_VALUE_ATTRIBUTE = "strategy_value";
    private static final String EXPERTS_ATTRIBUTE = "selected_experts";

    private static final String NAME_SUCCESS_CREATION_ATTRIBUTE = "success_creation";
    private static final String END_REGISTRATION_DATE_VALIDATION_RESULT_ATTRIBUTE = "end_registration_date_validation_result";
    private static final String END_SENDING_ANSWERS_DATE_VALIDATION_RESULT_ATTRIBUTE = "end_sending_answers_date_validation_result";
    private static final String STRATEGY_VALUE_VALIDATION_RESULT_ATTRIBUTE = "strategy_value_validation_result";
    private static final String EXPERTS_RESULT_ATTRIBUTE = "experts_validation_result";


    private Validator<Pair<Integer, String>> valueStrategyValidator;
    private Validator<Instant> dateInFutureValidator;
    private Validator<Pair<Instant, Instant>> dateSendingAnswersValidator;

    public CreateCompetitionCommand() {
        valueStrategyValidator = new ValueStrategyValidator();
        dateInFutureValidator = new DateInFutureValidator();
        dateSendingAnswersValidator = new DateSendingAnswersValidator();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);


        String task = request.getParameter(TASK_PARAM);
        String endRegistrationDate = request.getParameter(END_REGISTRATION_DATE_PARAM);
        String endRegistrationTime = request.getParameter(END_REGISTRATION_TIME_PARAM);
        String endSendingAnswersDate = request.getParameter(END_SENDING_ANSWERS_DATE_PARAM);
        String endSendingAnswersTime = request.getParameter(END_SENDING_ANSWERS_TIME_PARAM);
        String strategyName = request.getParameter(STRATEGY_PARAM);
        String strategyValue = request.getParameter(STRATEGY_VALUE_PARAM);
        String[] expertsIdsArray = request.getParameterValues(EXPERTS_PARAM);

        List<Integer> expertsIdstIntList = Arrays.stream(expertsIdsArray).map(stringId -> Integer.parseInt(stringId)).collect(Collectors.toList());
        Instant endRegistrationInstant = null;
        Instant endSendingAnswersInstant = null;
        StrategyAdapters strategyAdapter = StrategyAdapters.valueOf(strategyName);
        int countFatalErrors = 0;
        try {
            endRegistrationInstant = parse(endRegistrationDate, endRegistrationTime);
        } catch (DateTimeParseException e) {
            countFatalErrors++;
            request.setAttribute(END_REGISTRATION_DATE_VALIDATION_RESULT_ATTRIBUTE, CompetitionDatesValidatorResults.INVALID_FORMAT);
        }
        try {
            endSendingAnswersInstant = parse(endSendingAnswersDate, endSendingAnswersTime);
        } catch (DateTimeParseException e) {
            countFatalErrors++;
            request.setAttribute(END_SENDING_ANSWERS_DATE_VALIDATION_RESULT_ATTRIBUTE, CompetitionDatesValidatorResults.INVALID_FORMAT);
        }

        if (expertsIdsArray == null || expertsIdsArray.length <= 0){
            countFatalErrors++;
            request.setAttribute(EXPERTS_RESULT_ATTRIBUTE, ExpertsValidationResults.NO_EXPERTS);
        } else {
            request.setAttribute(EXPERTS_RESULT_ATTRIBUTE, new NormalValidationResultable());
        }

        if (countFatalErrors > 0) {
            request.setAttribute(TASK_ATTRIBUTE, task);
            request.setAttribute(END_REGISTRATION_DATE_ATTRIBUTE, endRegistrationDate);
            request.setAttribute(END_REGISTRATION_TIME_ATTRIBUTE, endRegistrationTime);
            request.setAttribute(END_SENDING_ANSWERS_DATE_ATTRIBUTE, endSendingAnswersDate);
            request.setAttribute(END_SENDING_ANSWERS_TIME_ATTRIBUTE, endSendingAnswersTime);
            request.setAttribute(STRATEGY_ATTRIBUTE, strategyAdapter);
            request.setAttribute(STRATEGY_VALUE_ATTRIBUTE, strategyValue);
            request.setAttribute(EXPERTS_ATTRIBUTE, expertsIdstIntList);
            new ShowCreateCompetitionPageCommand().execute(request, response);
            return;
        }

        ValidationResultable endRegistrationDateValidationResult = dateInFutureValidator.validate(endRegistrationInstant);
        ValidationResultable endSendingAnswersDateValidationResult = dateInFutureValidator.validate(endSendingAnswersInstant);
        if (endSendingAnswersDateValidationResult.getValidationClass() == ValidationResultClasses.OK) {
            endSendingAnswersDateValidationResult = dateSendingAnswersValidator.validate(Pair.of(endRegistrationInstant, endSendingAnswersInstant));
        }
        ValidationResultable strategyValueValidationResult = valueStrategyValidator.validate(Pair.of(strategyAdapter.getId(), strategyValue));


        request.setAttribute(END_REGISTRATION_DATE_VALIDATION_RESULT_ATTRIBUTE, endRegistrationDateValidationResult);
        request.setAttribute(END_SENDING_ANSWERS_DATE_VALIDATION_RESULT_ATTRIBUTE, endSendingAnswersDateValidationResult);
        request.setAttribute(STRATEGY_VALUE_VALIDATION_RESULT_ATTRIBUTE, strategyValueValidationResult);


        if (ValidationUtils.validate(Stream.of(endRegistrationDateValidationResult, endSendingAnswersDateValidationResult, strategyValueValidationResult).collect(Collectors.toList()))) {
            Double value = 0.0;
            try {
                value = Double.parseDouble(strategyValue);
            } catch (NumberFormatException e) {
                value = 0.0;
            }
            CompetitionResultable strategy = strategyAdapter.generate(value);
            daoFactory.getCompetitionDAO().add(
                    task,
                    strategy,
                    endRegistrationInstant,
                    endSendingAnswersInstant,
                    Arrays.stream(expertsIdsArray).map(id -> Integer.parseInt(id)).collect(Collectors.toList())
            );
            request.setAttribute(NAME_SUCCESS_CREATION_ATTRIBUTE, "Конкурс создан!");

        } else {
            request.setAttribute(TASK_ATTRIBUTE, task);
            request.setAttribute(END_REGISTRATION_DATE_ATTRIBUTE, endRegistrationDate);
            request.setAttribute(END_REGISTRATION_TIME_ATTRIBUTE, endRegistrationTime);
            request.setAttribute(END_SENDING_ANSWERS_DATE_ATTRIBUTE, endSendingAnswersDate);
            request.setAttribute(END_SENDING_ANSWERS_TIME_ATTRIBUTE, endSendingAnswersTime);
            request.setAttribute(STRATEGY_ATTRIBUTE, strategyAdapter);
            request.setAttribute(STRATEGY_VALUE_ATTRIBUTE, strategyValue);
            request.setAttribute(EXPERTS_ATTRIBUTE, expertsIdstIntList);
        }

        new ShowCreateCompetitionPageCommand().execute(request, response);
    }

    private Instant parse(String date, String time) {
        String dateTime = String.format("%s %s", date, time);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(dateTime, f);
        Instant instant = ldt.toInstant(ZoneOffset.UTC);
        return instant;
    }
}
