package br.com.nessauepa.logparser.entity;

public class MurderHistoryEntry extends HistoryEntry {

	private String targetPlayerName;
	private String weapon;

	public String getTargetPlayerName() {
		return targetPlayerName;
	}

	public void setTargetPlayerName(String targetPlayerName) {
		this.targetPlayerName = targetPlayerName;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
}
