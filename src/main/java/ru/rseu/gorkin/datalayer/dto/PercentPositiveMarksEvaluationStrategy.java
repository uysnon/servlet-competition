package ru.rseu.gorkin.datalayer.dto;

import java.util.List;
import java.util.Objects;

public class PercentPositiveMarksEvaluationStrategy implements CompetitionResultable {
    private static final double PERCENTAGE_COEFFICIENT = 100;

    private double percentPositiveMarks;

    public PercentPositiveMarksEvaluationStrategy() {
    }

    public PercentPositiveMarksEvaluationStrategy(double percentPositiveMarks) {
        this.percentPositiveMarks = percentPositiveMarks;
    }

    public double getPercentPositiveMarks() {
        return percentPositiveMarks;
    }

    public void setPercentPositiveMarks(double percentPositiveMarks) {
        this.percentPositiveMarks = percentPositiveMarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PercentPositiveMarksEvaluationStrategy)) return false;
        PercentPositiveMarksEvaluationStrategy that = (PercentPositiveMarksEvaluationStrategy) o;
        return Double.compare(that.percentPositiveMarks, percentPositiveMarks) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentPositiveMarks);
    }

    @Override
    public Marks calculateMark(List<Marks> allMarks) {
        int countALlMarks = allMarks.size();
        int countPositiveMarks = (int) allMarks.stream().filter(mark -> mark == Marks.POSITIVE).count();
        double percentage = calculatePercentage(countPositiveMarks, countALlMarks);
        if (percentage >= percentPositiveMarks) {
            return Marks.POSITIVE;
        }
        int countNotDefinedMarks = (int) allMarks.stream().filter(mark -> mark == Marks.NOT_DEFINED).count();
        int countMaximumPossiblePositiveMarks = countPositiveMarks + countNotDefinedMarks;
        double maximumPossiblePercentage = calculatePercentage(countMaximumPossiblePositiveMarks, countALlMarks);
        if (maximumPossiblePercentage >= percentPositiveMarks) {
            return Marks.NOT_DEFINED;
        } else {
            return Marks.NEGATIVE;
        }
    }

    private double calculatePercentage(int valuePart, int valueFull) {
        return ((double) valuePart / valueFull) * PERCENTAGE_COEFFICIENT;
    }

    @Override
    public String toString() {
        return "PercentEvaluationStrategy{" +
                "percentPositiveMarks=" + percentPositiveMarks +
                '}';
    }
}
