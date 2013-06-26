package br.com.nessauepa.logparser.entity;

public class MurderHistoryEntry extends HistoryEntry {

	private Player target;

	public Player getTarget() {
		return target;
	}

	public void setTarget(Player target) {
		this.target = target;
	}
}
