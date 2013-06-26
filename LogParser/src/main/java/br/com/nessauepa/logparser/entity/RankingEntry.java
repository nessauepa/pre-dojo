package br.com.nessauepa.logparser.entity;

import java.util.Comparator;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RankingEntry {

	private Player player;
	private int murderNumber;
	private int deathNumber;

	// TODO: adicionar no xml de retorno
	public Boolean notDiedAward() {
		return deathNumber == 0;
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public int getMurderCount() {
		return murderNumber;
	}
	public void setMurderCount(int murderNumber) {
		this.murderNumber = murderNumber;
	}
	public int getDeathCount() {
		return deathNumber;
	}
	public void setDeathCount(int deathNumber) {
		this.deathNumber = deathNumber;
	}
	
	public static class RankByMurderNumber implements Comparator<RankingEntry> {

		@Override
		public int compare(RankingEntry o1, RankingEntry o2) {
			return o2.getMurderCount() - o1.getMurderCount();
		}

		
	}
}
