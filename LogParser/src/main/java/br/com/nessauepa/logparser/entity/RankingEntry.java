package br.com.nessauepa.logparser.entity;

import java.util.Comparator;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RankingEntry {

	private String name;
	private int murderCount;
	private int deathCount;

	// TODO: adicionar no xml de retorno
	public Boolean notDiedAward() {
		return deathCount == 0;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMurderCount() {
		return murderCount;
	}

	public void setMurderCount(int murderCount) {
		this.murderCount = murderCount;
	}

	public int getDeathCount() {
		return deathCount;
	}

	public void setDeathCount(int deathCount) {
		this.deathCount = deathCount;
	}

	public static class OrderByMurderDesc implements Comparator<RankingEntry> {

		@Override
		public int compare(RankingEntry o1, RankingEntry o2) {
			return o2.getMurderCount() - o1.getMurderCount();
		}
	}
}
