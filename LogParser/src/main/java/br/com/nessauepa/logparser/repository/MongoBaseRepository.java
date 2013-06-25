package br.com.nessauepa.logparser.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class MongoBaseRepository<T> extends BaseRepository<T> {
	
	public abstract MongoTemplate getMongoTemplate();

	public T save(T o) {
		getMongoTemplate().save(o);
		return o;
	}

	public T findById(Object id) {
		return getMongoTemplate().findById(id, getParameterizedEntity());
	}

	public void delete(T o) {
		getMongoTemplate().remove(o);
		
	}

	public List<T> listAll() {
		return getMongoTemplate().findAll(getParameterizedEntity());
	}
	
	
}
