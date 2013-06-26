package br.com.nessauepa.logparser.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.nessauepa.logparser.entity.MatchRanking;
import br.com.nessauepa.logparser.service.RankingService;

@Named
@Path("/ranking")
public class RankingController {

	@Inject
	private RankingService service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public List<MatchRanking> all() {
		return service.rankAll();
	}
}
