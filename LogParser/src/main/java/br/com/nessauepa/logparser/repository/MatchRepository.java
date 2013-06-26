package br.com.nessauepa.logparser.repository;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.mongodb.core.MongoTemplate;

import br.com.nessauepa.logparser.entity.Match;

@Named
public class MatchRepository extends MongoBaseRepository<Match> {

	@Inject
	private MongoTemplate mongoTemplate;
	
	@Override
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public Match findOrCreate(Long id) {
		Match match = findById(id);

		if (match == null) {
			match = new Match();
			match.setId(id);
			save(match);
		}
		
		return match;
	}
}