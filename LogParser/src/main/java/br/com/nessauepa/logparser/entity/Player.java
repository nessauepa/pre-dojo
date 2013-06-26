package br.com.nessauepa.logparser.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players")
@XmlRootElement
public class Player {
	@Id
	private String name;
	
	private List<HistoryEntry> historyList = new ArrayList<HistoryEntry>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HistoryEntry> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<HistoryEntry> historyList) {
		this.historyList = historyList;
	}

	public void addHistory(HistoryEntry historyEntry) {
		System.out.println("HISTORICO DE " + name + " = " + historyList.size());
		historyList.add(historyEntry);
	}
	
	public Map<Class<? extends HistoryEntry>, Integer> getHistoryCounter() {
		Map<Class<? extends HistoryEntry>, Integer> map = new HashMap<Class<? extends HistoryEntry>, Integer>();
		
		if (historyList != null) {
			for (HistoryEntry historyEntry : historyList) {
				Integer current = map.get(historyEntry.getClass());
				current = (current == null) ? 0 : current;
				map.put(historyEntry.getClass(), ++current);
			}
		}
		
		return map;
	}
}
