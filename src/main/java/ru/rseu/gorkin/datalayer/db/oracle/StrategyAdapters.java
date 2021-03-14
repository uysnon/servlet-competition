package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dto.AllPositiveEvaluationStrategy;
import ru.rseu.gorkin.datalayer.dto.CompetitionResultable;
import ru.rseu.gorkin.datalayer.dto.MaxNegativeMarksCountEvaluationStrategy;
import ru.rseu.gorkin.datalayer.dto.PercentPositiveMarksEvaluationStrategy;


public enum StrategyAdapters {
    ALL_POSITIVE(1) {
        @Override
        boolean isMe(CompetitionResultable competitionResultable) {
            return competitionResultable instanceof AllPositiveEvaluationStrategy;
        }

        @Override
        StrategySQL generate(CompetitionResultable competitionResultable) {
            return new StrategySQL(id, 0);
        }

        @Override
        CompetitionResultable generate(double value) {
            return new AllPositiveEvaluationStrategy();
        }
    },
    MAX_NEGATIVE_MARKS_COUNT(2) {
        @Override
        boolean isMe(CompetitionResultable competitionResultable) {
            return competitionResultable instanceof MaxNegativeMarksCountEvaluationStrategy;
        }

        @Override
        StrategySQL generate(CompetitionResultable competitionResultable) {
            return new StrategySQL(id, ((MaxNegativeMarksCountEvaluationStrategy) competitionResultable).getMaxNegativeMarksCount());
        }

        @Override
        CompetitionResultable generate(double value) {
            return new MaxNegativeMarksCountEvaluationStrategy((int) Math.round(value));
        }
    },
    PERCENT_POSITIVE_MARKS(3) {
        @Override
        boolean isMe(CompetitionResultable competitionResultable) {
            return competitionResultable instanceof PercentPositiveMarksEvaluationStrategy;
        }

        @Override
        StrategySQL generate(CompetitionResultable competitionResultable) {
            return new StrategySQL(id, ((PercentPositiveMarksEvaluationStrategy) competitionResultable).getPercentPositiveMarks());
        }

        @Override
        CompetitionResultable generate(double value) {
            return new PercentPositiveMarksEvaluationStrategy(value);
        }
    };

    int id;


    StrategyAdapters(int id) {
        this.id = id;
    }

    abstract boolean isMe(CompetitionResultable competitionResultable);

    abstract StrategySQL generate(CompetitionResultable competitionResultable);

    abstract CompetitionResultable generate(double value);

    static CompetitionResultable generateStrategyDTO(int id, double value) {
        for (StrategyAdapters strategy : StrategyAdapters.values()) {
            if (strategy.id == id) {
                return strategy.generate(value);
            }
        }
        return null;
    }

    static StrategySQL generateStrategySQL(CompetitionResultable competitionResultable) {
        for (StrategyAdapters strategy : StrategyAdapters.values()) {
            if (strategy.isMe(competitionResultable)) {
                return strategy.generate(competitionResultable);
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


