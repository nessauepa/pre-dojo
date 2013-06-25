package br.com.nessauepa.logparser.entity;

import java.util.Calendar;

import javax.inject.Named;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Named
@XmlRootElement
@Document(collection = "actions")
public abstract class Action {
	@Id
	private Long id;
	private Calendar date;
	
	public Action() {
		// TODO: melhorar 
		this.id = Calendar.getInstance().getTimeInMillis();
	}

	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
}
