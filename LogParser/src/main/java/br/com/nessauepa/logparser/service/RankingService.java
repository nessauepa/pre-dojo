package br.com.nessauepa.logparser.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.nessauepa.logparser.entity.DeathHistoryEntry;
import br.com.nessauepa.logparser.entity.Match;
import br.com.nessauepa.logparser.entity.MurderHistoryEntry;
import br.com.nessauepa.logparser.entity.PlayerHistory;
import br.com.nessauepa.logparser.entity.Ranking;
import br.com.nessauepa.logparser.entity.RankingEntry;
import br.com.nessauepa.logparser.repository.MatchRepository;

@Named
public class RankingService {

	@Inject
	private MatchRepository repository;
	
	/**
	 * @return Lista de rankings por partida.
	 */
	public List<Ranking> rankAll() {
		
		List<Ranking> list = new ArrayList<Ranking>();
		
		for (Match match : repository.listAll()) {
			Ranking ranking = new Ranking();
			ranking.setMatchId(match.getId());
			ranking.setRankingEntries(rank(match));
			list.add(ranking);
		}
		
		return list;
	}
	
	/**
	 * @param match
	 * @return Ranking da partida informada.
	 */
	public List<RankingEntry> rank(Match match) {
		List<RankingEntry> list = new ArrayList<RankingEntry>();
	
		for (PlayerHistory playerHistory : match.getPlayersHistory()) {
			RankingEntry entry = new RankingEntry();
			entry.setName(playerHistory.getName());
			entry.setDeathCount(playerHistory.countHistoryEntries(DeathHistoryEntry.class));
			entry.setMurderCount(playerHistory.countHistoryEntries(MurderHistoryEntry.class));
			list.add(entry);
		}
		
		Collections.sort(list, new RankingEntry.OrderByMurderDesc());
		
		return list;
	}
}
