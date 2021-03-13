package ru.rseu.gorkin.datalayer.dto;

import java.util.List;

public class AllPositiveEvaluationStrategy implements CompetitionResultable {
    @Override
    public Marks calculateMark(List<Marks> allMarks) {
        Marks result = Marks.POSITIVE;
        boolean endSearch = false;
        for (int i = 0; i < allMarks.size() && !endSearch; i++) {
            Marks mark = allMarks.get(i);
            if (mark == Marks.NEGATIVE) {
                result = Marks.NEGATIVE;
                endSearch = true;
            }
            if (mark == Marks.POSITIVE) {
                result = Marks.NOT_DEFINED;
                endSearch = true;
            }
        }
        return result;
    }
}
