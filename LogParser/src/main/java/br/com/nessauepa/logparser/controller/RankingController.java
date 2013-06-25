package br.com.nessauepa.logparser.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.nessauepa.logparser.entity.Player;
import br.com.nessauepa.logparser.repository.PlayerRepository;

@Named
@Path("/ranking")
public class RankingController {

	@Inject
	private PlayerRepository repository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public List<Player> list() {
		System.out.println("Dentro de controller");
		return repository.listAll();
	}
}
