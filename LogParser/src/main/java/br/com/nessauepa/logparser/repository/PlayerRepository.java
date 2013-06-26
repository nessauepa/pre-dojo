package br.com.nessauepa.logparser.repository;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.mongodb.core.MongoTemplate;

import br.com.nessauepa.logparser.entity.Player;

@Named
public class PlayerRepository extends MongoBaseRepository<Player> {

	@Inject
	private MongoTemplate mongoTemplate;
	
	@Override
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public Player findOrCreate(Player player) {
		Player attached = findById(player.getName());
		if (attached != null) return attached;
		save(player);
		return player;
	}
}