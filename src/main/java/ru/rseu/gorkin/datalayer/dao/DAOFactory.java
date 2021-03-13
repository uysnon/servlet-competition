package ru.rseu.gorkin.datalayer.dao;

public abstract class DAOFactory {
	public static DAOFactory getInstance(DBType dbType) {
		DAOFactory result = dbType.getDAOFactory();
		return result;
	}

	public abstract UserDAO getUserDAO();
	public abstract DecisionDAO getDecisionDAO();
	public abstract CompetitionDAO getCompetitionDAO();
	public abstract CompetitionParticipationDAO getCompetitionParticipationDAO();
}
