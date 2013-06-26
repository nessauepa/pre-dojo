package br.com.nessauepa.logparser.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.mongodb.core.mapping.Document;

@XmlRootElement
@Document(collection = "matchs")
public class Match {

	private Long id;

	private List<PlayerHistory> playersHistory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PlayerHistory> getPlayersHistory() {
		return playersHistory;
	}

	public void setPlayersHistory(List<PlayerHistory> playersHistory) {
		this.playersHistory = playersHistory;
	}

	public void addPlayerHistory(PlayerHistory playerHistory) {
		
	}

	private PlayerHistory findOrCreatePlayerHistory(String playerName) {
		if (playersHistory == null) playersHistory = new ArrayList<PlayerHistory>();
		
		// Tenta encontrar o historico do jogador...
		for (PlayerHistory history : playersHistory) {
			if (history.getName().equals(playerName)) {
				return history;
			}
		}
		
		// ...ou cria.
		PlayerHistory history = new PlayerHistory();
		history.setName(playerName);
		playersHistory.add(history);

		return history;
	}

	public void addPlayerHistoryEntry(String playerName,
			HistoryEntry historyEntry) {
		PlayerHistory playerHistory = findOrCreatePlayerHistory(playerName);
		playerHistory.addHistoryEntry(historyEntry);
	}
}
