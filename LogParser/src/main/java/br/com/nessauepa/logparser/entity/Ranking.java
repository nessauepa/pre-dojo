package br.com.nessauepa.logparser.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ranking {

	private Long matchId;
	private List<RankingEntry> rankingEntries;
	public Long getMatchId() {
		return matchId;
	}
	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	public List<RankingEntry> getRankingEntries() {
		return rankingEntries;
	}
	public void setRankingEntries(List<RankingEntry> rankingEntries) {
		this.rankingEntries = rankingEntries;
	}
}
