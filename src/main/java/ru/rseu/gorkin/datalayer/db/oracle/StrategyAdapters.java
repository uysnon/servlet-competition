package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dto.AllPositiveEvaluationStrategy;
import ru.rseu.gorkin.datalayer.dto.CompetitionResultable;
import ru.rseu.gorkin.datalayer.dto.MaxNegativeMarksCountEvaluationStrategy;
import ru.rseu.gorkin.datalayer.dto.PercentPositiveMarksEvaluationStrategy;


public enum StrategyAdapters {
    ALL_POSITIVE(1, "Все положительные оценки") {
        @Override
        boolean isMe(CompetitionResultable competitionResultable) {
            return competitionResultable instanceof AllPositiveEvaluationStrategy;
        }

        @Override
        public StrategySQL generate(CompetitionResultable competitionResultable) {
            return new StrategySQL(id, 0);
        }

        @Override
        public CompetitionResultable generate(double value) {
            return new AllPositiveEvaluationStrategy();
        }

        @Override
        public String generateDescriptionLocal(CompetitionResultable competitionResultable) {
            return "Все положительные оценки";
        }
    },
    MAX_NEGATIVE_MARKS_COUNT(2, "Допустимое количество негативных оценок") {
        @Override
        boolean isMe(CompetitionResultable competitionResultable) {
            return competitionResultable instanceof MaxNegativeMarksCountEvaluationStrategy;
        }

        @Override
        public StrategySQL generate(CompetitionResultable competitionResultable) {
            return new StrategySQL(id, ((MaxNegativeMarksCountEvaluationStrategy) competitionResultable).getMaxNegativeMarksCount());
        }

        @Override
        public CompetitionResultable generate(double value) {
            return new MaxNegativeMarksCountEvaluationStrategy((int) Math.round(value));
        }

        @Override
        public String generateDescriptionLocal(CompetitionResultable competitionResultable) {
            return String.format("Максимальное количество отрицательных оценок: %d",
                    ((MaxNegativeMarksCountEvaluationStrategy) competitionResultable).getMaxNegativeMarksCount());
        }
    },
    PERCENT_POSITIVE_MARKS(3, "Требуемый процент положительных оценок") {
        @Override
        boolean isMe(CompetitionResultable competitionResultable) {
            return competitionResultable instanceof PercentPositiveMarksEvaluationStrategy;
        }

        @Override
        public StrategySQL generate(CompetitionResultable competitionResultable) {
            return new StrategySQL(id, ((PercentPositiveMarksEvaluationStrategy) competitionResultable).getPercentPositiveMarks());
        }

        @Override
        public CompetitionResultable generate(double value) {
            return new PercentPositiveMarksEvaluationStrategy(value);
        }

        @Override
        public String generateDescriptionLocal(CompetitionResultable competitionResultable) {
            return String.format("Необходимый процент положительных оценок: %3.2f ",
                    ((PercentPositiveMarksEvaluationStrategy) competitionResultable).getPercentPositiveMarks());
        }
    };

    int id;
    private String title;


    StrategyAdapters(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public abstract String generateDescriptionLocal(CompetitionResultable competitionResultable);


    abstract boolean isMe(CompetitionResultable competitionResultable);

    public abstract StrategySQL generate(CompetitionResultable competitionResultable);

    public abstract CompetitionResultable generate(double value);

    public static CompetitionResultable generateStrategyDTO(int id, double value) {
        for (StrategyAdapters strategy : StrategyAdapters.values()) {
            if (strategy.id == id) {
                return strategy.generate(value);
            }
        }
        return null;
    }

    public static StrategySQL generateStrategySQL(CompetitionResultable competitionResultable) {
        for (StrategyAdapters strategy : StrategyAdapters.values()) {
            if (strategy.isMe(competitionResultable)) {
                return strategy.generate(competitionResultable);
            }
        }
        return null;
    }

    public static String generateDescription(CompetitionResultable competitionResultable) {
        for (StrategyAdapters strategy : StrategyAdapters.values()) {
            if (strategy.isMe(competitionResultable)) {
                return strategy.generateDescriptionLocal(competitionResultable);
            }
        }
        return null;
    }

    public static class StrategySQL {
        private int id;
        private double value;

        public StrategySQL(int id, double value) {
            this.id = id;
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

}


