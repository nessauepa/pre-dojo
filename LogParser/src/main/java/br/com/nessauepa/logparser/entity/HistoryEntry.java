package br.com.nessauepa.logparser.entity;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class HistoryEntry {

	private Calendar date;

	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}

	public Long getTimeImMillis() {
		return date.getTimeInMillis();
	}
}
