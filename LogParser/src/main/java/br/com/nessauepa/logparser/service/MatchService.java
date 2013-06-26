package br.com.nessauepa.logparser.service;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.nessauepa.logparser.entity.DeathHistoryEntry;
import br.com.nessauepa.logparser.entity.Match;
import br.com.nessauepa.logparser.entity.MurderHistoryEntry;
import br.com.nessauepa.logparser.repository.MatchRepository;

@Named
public class MatchService {

	@Inject 
	private MatchRepository repository;
	
	public void addKillHistory(Long matchId, String sourcePlayerName, String targetPlayerName, Calendar date) {
		// Obtem partida.
		Match match = repository.findOrCreate(matchId);
		
		// Cria historico de assassinato.
		match.addPlayerHistoryEntry(sourcePlayerName, createMurderHistory(targetPlayerName, date));
		
		// Cria historico de morte. 
		match.addPlayerHistoryEntry(targetPlayerName, createDeathHistory(sourcePlayerName, date));
		
		repository.save(match);
	}

	private MurderHistoryEntry createMurderHistory(String targetPlayerName, Calendar date) {
		MurderHistoryEntry murderEntry = new MurderHistoryEntry();
		murderEntry.setDate(date);
		murderEntry.setTargetPlayerName(targetPlayerName);
		return murderEntry;
	}

	private DeathHistoryEntry createDeathHistory(String sourcePlayerName, Calendar date) {
		DeathHistoryEntry deathEntry = new DeathHistoryEntry();
		deathEntry.setDate(date);
		deathEntry.setSourcePlayerName(sourcePlayerName);
		return deathEntry;
	}
}