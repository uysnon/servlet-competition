package ru.rseu.gorkin.datalayer.dto;

import java.util.List;
import java.util.Objects;

public class MaxNegativeMarksCountEvaluationStrategy implements CompetitionResultable {
    private int maxNegativeMarksCount;

    public MaxNegativeMarksCountEvaluationStrategy() {
    }

    public MaxNegativeMarksCountEvaluationStrategy(int maxNegativeMarks) {
        this.maxNegativeMarksCount = maxNegativeMarks;
    }

    public int getMaxNegativeMarksCount() {
        return maxNegativeMarksCount;
    }

    public void setMaxNegativeMarksCount(int maxNegativeMarksCount) {
        this.maxNegativeMarksCount = maxNegativeMarksCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaxNegativeMarksCountEvaluationStrategy)) return false;
        MaxNegativeMarksCountEvaluationStrategy that = (MaxNegativeMarksCountEvaluationStrategy) o;
        return maxNegativeMarksCount == that.maxNegativeMarksCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxNegativeMarksCount);
    }

    @Override
    public Marks calculateMark(List<Marks> allMarks) {
        int countNegativeMarks = (int) allMarks.stream().filter(mark -> mark == Marks.NEGATIVE).count();
        if (countNegativeMarks > maxNegativeMarksCount) {
            return Marks.NEGATIVE;
        }
        int countNotDefinedMarks = (int) allMarks.stream().filter(mark -> mark == Marks.NOT_DEFINED).count();
        int countMaximumPossibleNegativeMarks = countNegativeMarks + countNotDefinedMarks;
        if (countMaximumPossibleNegativeMarks > maxNegativeMarksCount) {
            return Marks.NOT_DEFINED;
        } else {
            return Marks.POSITIVE;
        }
    }

    @Override
    public String toString() {
        return "MaxNegativeMarksCountEvaluationStrategy{" +
                "maxNegativeMarks=" + maxNegativeMarksCount +
                '}';
    }
}
