package br.com.nessauepa.logparser.entity;

public class DeathHistoryEntry extends HistoryEntry {

	private String sourcePlayerName;
	private String weapon;
	
	public String getSourcePlayerName() {
		return sourcePlayerName;
	}

	public void setSourcePlayerName(String sourcePlayerName) {
		this.sourcePlayerName = sourcePlayerName;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
}
