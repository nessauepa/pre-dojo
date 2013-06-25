package br.com.nessauepa.logparser.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.mongodb.core.MongoTemplate;

import br.com.nessauepa.logparser.entity.Action;
import br.com.nessauepa.logparser.entity.KillAction;
import br.com.nessauepa.logparser.entity.Player;

@Named
public class ActionRepository extends MongoBaseRepository<Action> {

	@Inject
	private MongoTemplate mongoTemplate;
	
	@Override
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	
	public List<Action> getKillActionsByTarget(Player target) {
		
		List<Action> killedActions = new ArrayList<Action>();
		List<Action> actions = listAll();
		if (actions != null) {
			for (Action a : actions) {
				if (a instanceof KillAction) {
					//if (((KillAction) a).getTarget().equals(target)) {
						killedActions.add(a);
					//}
				}
			}
		}
		
		return killedActions;
	}
}