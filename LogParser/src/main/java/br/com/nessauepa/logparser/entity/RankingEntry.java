package br.com.nessauepa.logparser.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RankingEntry {

	private Player player;
	private long murderNumber;
	private long deathNumber;

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public long getMurderCount() {
		return murderNumber;
	}
	public void setMurderCount(long murderNumber) {
		this.murderNumber = murderNumber;
	}
	public long getDeathCount() {
		return deathNumber;
	}
	public void setDeathCount(long deathNumber) {
		this.deathNumber = deathNumber;
	}
}
