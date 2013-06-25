package br.com.nessauepa.logparser.entity;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.mongodb.core.mapping.Document;

@XmlRootElement
@Document(collection = "actions")
public abstract class Action {
	private Calendar date;

	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
}
