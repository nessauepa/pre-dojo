package br.com.nessauepa.logparser.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.nessauepa.logparser.entity.DeathHistoryEntry;
import br.com.nessauepa.logparser.entity.HistoryEntry;
import br.com.nessauepa.logparser.entity.MurderHistoryEntry;
import br.com.nessauepa.logparser.entity.Player;
import br.com.nessauepa.logparser.entity.MatchRanking;
import br.com.nessauepa.logparser.entity.RankingEntry;
import br.com.nessauepa.logparser.repository.PlayerRepository;

@Named
public class RankingService {

	@Inject
	private PlayerRepository playerRepository;
	
	public List<MatchRanking> rankAll() {
		List<RankingEntry> list = new ArrayList<RankingEntry>();
	
		// TODO: obter historico por game
		for (Player player : playerRepository.listAll()) {
			RankingEntry entry = new RankingEntry();
			entry.setPlayer(player);
			Map<Class<? extends HistoryEntry>, Integer> historyCounter = player.getHistoryCounter();
			if (historyCounter != null) {
				Integer deathCount = historyCounter.get(DeathHistoryEntry.class);
				Integer murderCount = historyCounter.get(MurderHistoryEntry.class);
				entry.setDeathCount((deathCount == null) ? 0 : deathCount);
				entry.setMurderCount((murderCount == null) ? 0 : murderCount);
			}
			list.add(entry);
		}
		
		Collections.sort(list, new RankingEntry.RankByMurderNumber());
		
		MatchRanking matchRanking = new MatchRanking();
		matchRanking.setEntries(list);
		return Arrays.asList(matchRanking);
	}
}
