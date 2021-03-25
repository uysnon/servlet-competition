package ru.rseu.gorkin.web.viewclasses;

import ru.rseu.gorkin.datalayer.dto.*;

import java.util.ArrayList;
import java.util.List;

public class CompetitionParticipantsView {
    private List<String> expertNames;
    private List<ParticipationInfo> participationInfos;

    private CompetitionParticipantsView() {
        expertNames = new ArrayList<>();
        participationInfos = new ArrayList<>();
        participationInfos = new ArrayList<>();
    }

    public static CompetitionParticipantsView createOf(Competition competition, List<CompetitionParticipation> competitionParticipations, List<Decision> decisions) {
        CompetitionParticipantsView view = new CompetitionParticipantsView();
        view.addExpertNames(competition.getExperts());
        view.createParticipationInfos(competition.getExperts(), competitionParticipations, decisions);
        return view;
    }

    public List<String> getExpertNames() {
        return expertNames;
    }

    public void setExpertNames(List<String> expertNames) {
        this.expertNames = expertNames;
    }

    public List<ParticipationInfo> getParticipationInfos() {
        return participationInfos;
    }

    public void setParticipationInfos(List<ParticipationInfo> participationInfos) {
        this.participationInfos = participationInfos;
    }

    private void createParticipationInfos(List<User> experts, List<CompetitionParticipation> competitionParticipations, List<Decision> decisions) {
        for (CompetitionParticipation competitionParticipation : competitionParticipations) {
            ParticipationInfo participationInfo = new ParticipationInfo();
            participationInfo.setParticipantName(competitionParticipation.getParticipant().getName());
            participationInfo.setTotalMark(competitionParticipation.getMark());
            int idCompetitionParticipation = competitionParticipation.getId();
            participationInfo.setId(idCompetitionParticipation);
            experts.forEach(expert -> participationInfo.getMarks().add(Marks.NOT_DEFINED));
            for (Decision decision : decisions) {
                if (decision.getCompetitionParticipation().getId() == idCompetitionParticipation) {
                    participationInfo.getMarks().set(indexOfExert(experts, decision.getExpert().getId()), decision.getMark());
                }
            }
            participationInfos.add(participationInfo);
        }
    }

    private void addExpertNames(List<User> users) {
        users.forEach(user -> expertNames.add(user.getName()));
    }

    private int indexOfExert(List<User> experts, int needExpertId) {
        for (int i = 0; i < experts.size(); i++) {
            if (experts.get(i).getId() == needExpertId) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

}
