package br.com.nessauepa.logparser.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MatchRanking {

	private Match match;
	private List<RankingEntry> entries;

	public List<RankingEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<RankingEntry> entries) {
		this.entries = entries;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
}
