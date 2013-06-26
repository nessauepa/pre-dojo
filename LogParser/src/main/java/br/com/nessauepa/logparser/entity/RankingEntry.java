package br.com.nessauepa.logparser.entity;

import java.util.Comparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RankingEntry {

	private String name;
	private int murderCount;
	private int deathCount;
	
	@XmlAttribute
	public boolean isNotDiedAward() {
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
			// Se numero de assassinatos empatado, prioriza quem morreu menos.
			if (o2.getMurderCount() == o1.getMurderCount()) {
				return o1.getDeathCount() - o2.getDeathCount();
			}
			return o2.getMurderCount() - o1.getMurderCount();
		}
	}
}
