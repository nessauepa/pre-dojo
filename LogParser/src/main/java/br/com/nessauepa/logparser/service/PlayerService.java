package br.com.nessauepa.logparser.service;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.nessauepa.logparser.entity.DeathHistoryEntry;
import br.com.nessauepa.logparser.entity.MurderHistoryEntry;
import br.com.nessauepa.logparser.entity.Player;
import br.com.nessauepa.logparser.repository.PlayerRepository;

@Named
public class PlayerService {

	@Inject 
	private PlayerRepository repository;

	public void saveKillAction(String sourceName, String targetName, Calendar current) {
	    
	    // Obtem jogador
	    Player sourcePlayer = new Player();
	    sourcePlayer.setName(sourceName);
	    Player targetPlayer = new Player();
	    targetPlayer.setName(targetName);
	    sourcePlayer = repository.findOrCreate(sourcePlayer);
	    targetPlayer = repository.findOrCreate(targetPlayer);
	    
	    // Salva acao de assassinato
	    MurderHistoryEntry murderEntry = new MurderHistoryEntry();
	    murderEntry.setDate(current);
	    murderEntry.setTarget(repository.findOrCreate(targetPlayer));
	    sourcePlayer.addHistory(murderEntry);
	    repository.save(sourcePlayer);
	    
	    // Salva acao de morte
	    DeathHistoryEntry deathEntry = new DeathHistoryEntry();
	    deathEntry.setDate(current);
	    deathEntry.setSource(repository.findOrCreate(sourcePlayer));
	    targetPlayer.addHistory(deathEntry);
	    repository.save(targetPlayer);
	}
}
