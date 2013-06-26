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

	public List<?> getHistoryEntries(Class<? extends HistoryEntry> historyEntryType) {
		List<HistoryEntry> list = new ArrayList<HistoryEntry>();
		if (historyEntries != null) {
			for (HistoryEntry entry : historyEntries) {
				if (entry.getClass().equals(historyEntryType)) {
					list.add(entry);
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Integer> getWeaponMapReduce() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<MurderHistoryEntry> murderHistoryEntries = (List<MurderHistoryEntry>) getHistoryEntries(MurderHistoryEntry.class);
		if (murderHistoryEntries != null) {
			for (MurderHistoryEntry entry : murderHistoryEntries) {
				Integer current = map.get(entry.getWeapon());
				current = (current == null) ? 0 : current;
				map.put(entry.getWeapon(), ++current);
			}
		}
		
		return map;
	}
	
	public String getFavoriteWeapon() {
		String favoriteWeapon = null;
		Map<String, Integer> weaponCounter = getWeaponMapReduce();
		if (weaponCounter != null && weaponCounter.size() > 0) {
			Map.Entry<String, Integer> maxEntry = null;

			for (Map.Entry<String, Integer> entry : weaponCounter.entrySet())
			{
			    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
			    {
			        maxEntry = entry;
			    }
			}
			favoriteWeapon = maxEntry.getKey();
		}
		return favoriteWeapon;
	}

	public int getMaxStreak() {
		int maxStreak = 0;
		if (historyEntries != null) {
			int parcialMaxStreak = 0;
			for (HistoryEntry entry : historyEntries) {
				if (entry instanceof MurderHistoryEntry) {
					parcialMaxStreak++;
				} else if (entry instanceof DeathHistoryEntry) {
					parcialMaxStreak = 0;
				}
				if (parcialMaxStreak > maxStreak) maxStreak = parcialMaxStreak;
			}
		}
		return maxStreak;
	}
}
