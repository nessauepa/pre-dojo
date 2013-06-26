package br.com.nessauepa.logparser.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayerHistory {
	private String name;
	
	private List<HistoryEntry> historyEntries = new ArrayList<HistoryEntry>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<HistoryEntry> getHistoryEntries() {
		return historyEntries;
	}

	public void setHistoryEntries(List<HistoryEntry> historyEntries) {
		this.historyEntries = historyEntries;
	}

	public void addHistoryEntry(HistoryEntry historyEntry) {
		historyEntries.add(historyEntry);
	}
	
	private Map<Class<? extends HistoryEntry>, Integer> getHistoryMapReduce() {
		Map<Class<? extends HistoryEntry>, Integer> map = new HashMap<Class<? extends HistoryEntry>, Integer>();
		
		if (historyEntries != null) {
			for (HistoryEntry entry : historyEntries) {
				Integer current = map.get(entry.getClass());
				current = (current == null) ? 0 : current;
				map.put(entry.getClass(), ++current);
			}
		}
		
		return map;
	}

	public int countHistoryEntries(Class<? extends HistoryEntry> historyEntryType) {
		Map<Class<? extends HistoryEntry>, Integer> historyCounter = getHistoryMapReduce();
		if (historyCounter != null) {
			Integer entriesCount = historyCounter.get(historyEntryType);
			return (entriesCount == null) ? 0 : entriesCount;
		}
		return 0;
	}
}
