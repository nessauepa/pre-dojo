package br.com.nessauepa.logparser.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players")
@XmlRootElement
public class Player {
	@Id
	private String name;
	
	private List<Action> actions;

	//@Inject
	//private PlayerRepository repository;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void addAction(Action action) {
		if (actions == null) actions = new ArrayList<Action>();	
		actions.add(action);
	}
	
	/*@XmlAttribute
	public Integer getKillNumber() {
		int killNumber = 0;
		if (actions != null) {
			for (Action action : actions) {
				if (action instanceof KillAction) {
					killNumber++;
				}
			}
		}
		
		return killNumber;
	}

	@XmlAttribute
	public int getKilledNumber() {
		
		//List<Action> killedActions = repository.getKillActionsByTarget(this);
		//return (killedActions == null) ? 0 : killedActions.size();
		return 0;
	}*/
}
