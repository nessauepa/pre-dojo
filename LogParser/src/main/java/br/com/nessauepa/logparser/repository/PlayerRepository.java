package br.com.nessauepa.logparser.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.mongodb.core.MongoTemplate;

import br.com.nessauepa.logparser.entity.Action;
import br.com.nessauepa.logparser.entity.Player;

@Named
public class PlayerRepository extends MongoBaseRepository<Player> {

	@Inject
	private MongoTemplate mongoTemplate;

	public List<Player> listAll() {
		return mongoTemplate.findAll(Player.class);
	}
	
	@Override
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	
	public List<Action> getKillActionsByTarget(Player target) {
		/*List<Action> killedActions = new ArrayList<Action>();
		
		if (actions != null) {
			for (Action a : actions) {
				if (a instanceof KillAction) {
					if (((KillAction) a).getTarget().equals(target)) {
						killedActions.add(a);
					}
				}
			}
		}
		
		return killedActions;*/
		return null;
	}

	public Player findOrCreate(Player player) {
		Player attached = findById(player.getName());
		if (attached != null) {
			player = attached;
		} else {
			save(player);
		}
		return player;
	}
}