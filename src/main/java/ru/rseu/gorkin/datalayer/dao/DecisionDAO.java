package ru.rseu.gorkin.datalayer.dao;

import ru.rseu.gorkin.datalayer.dto.Decision;

import java.util.List;

public interface DecisionDAO {
    void makeDecision(Decision decision);
    List<Decision> getAll();
    List<Decision> getExpertDecisions(String expertLogin);
}
