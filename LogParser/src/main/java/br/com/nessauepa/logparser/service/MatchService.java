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
		addWeaponKillHistory(matchId, sourcePlayerName, targetPlayerName, date, null);
	}

	public void addWeaponKillHistory(Long matchId, String sourcePlayerName,
			String targetPlayerName, Calendar date, String weapon) {
		// Obtem partida.
		Match match = repository.findOrCreate(matchId);
		
		// Cria historico de assassinato.
		match.addPlayerHistoryEntry(sourcePlayerName, createMurderHistory(targetPlayerName, date, weapon));
		
		// Cria historico de morte. 
		match.addPlayerHistoryEntry(targetPlayerName, createDeathHistory(sourcePlayerName, date, weapon));
		
		repository.save(match);	
	}

	private MurderHistoryEntry createMurderHistory(String targetPlayerName, Calendar date, String weapon) {
		MurderHistoryEntry entry = new MurderHistoryEntry();
		entry.setDate(date);
		entry.setTargetPlayerName(targetPlayerName);
		entry.setWeapon(weapon);
		return entry;
	}

	private DeathHistoryEntry createDeathHistory(String sourcePlayerName, Calendar date, String weapon) {
		DeathHistoryEntry entry = new DeathHistoryEntry();
		entry.setDate(date);
		entry.setSourcePlayerName(sourcePlayerName);
		entry.setWeapon(weapon);
		return entry;
	}
}