package br.com.nessauepa.logparser.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.nessauepa.logparser.entity.KillAction;
import br.com.nessauepa.logparser.entity.Player;
import br.com.nessauepa.logparser.repository.ActionRepository;
import br.com.nessauepa.logparser.repository.PlayerRepository;

public class LogParserListener implements ServletContextListener {

	private PlayerRepository repository;
	private ActionRepository actionRepository;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Injeta repository
		ServletContext ctx = sce.getServletContext(); 
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
		repository = (PlayerRepository) springContext.getBean(PlayerRepository.class);
		actionRepository = (ActionRepository) springContext.getBean(ActionRepository.class);
		
		try {
			parseLog(sce.getServletContext());
		} catch (Exception e) {
			throw new RuntimeException("Failed parsing text log.", e);
		}
	}

	private void parseLog(ServletContext ctx) throws FileNotFoundException, ParseException {
		System.out.println("Initializing log parser...");
		File file = new File(ctx.getRealPath("/WEB-INF/classes/logs/game.log"));

		Scanner scanner = new Scanner(new FileReader(file));
	    try {
	      while ( scanner.hasNextLine() ){
	    	  parseLine( scanner.nextLine() );
	      }
	    }
	    finally {
	      scanner.close();
	    }
	    System.out.println("Log parsed sucessfully.");
	}

	private void parseLine(String line) throws ParseException {
		System.out.println(" > Parsing line: " + line);
		
		String logEntryPattern = "^([\\w/]+\\s[\\w:]+) (\\S+) (.+?)";
		Pattern p = Pattern.compile(logEntryPattern);
	    Matcher matcher = p.matcher(line);
	    if (!matcher.matches() || 
	      3 != matcher.groupCount()) {
	      System.err.println("Bad log entry (or problem with RE?):");
	      return;
	    }

	    Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(matcher.group(1));
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    
	    String action = matcher.group(3);
	    
	    parseAction(cal, action);
	}

	private void parseAction(Calendar current, String action) {
		System.out.println(" > Parsing action: " + action);

		String logEntryPattern = "^([\\w]+) (killed) ([\\w]+) (.+?)";
		Pattern p = Pattern.compile(logEntryPattern);
	    Matcher matcher = p.matcher(action);
	    if (!matcher.matches()) {
	    	System.out.println("   Skiping: is not a kill action.");
	    	return;
	    }

	    // Obtem jogador
	    Player sourcePlayer = new Player();
	    sourcePlayer.setName(matcher.group(1));
	    Player targetPlayer = new Player();
	    targetPlayer.setName(matcher.group(3));
	    repository.findOrCreate(sourcePlayer);
	    repository.findOrCreate(targetPlayer);
	    
	    // Salva acao
	    KillAction killAction = new KillAction();
	    killAction.setDate(current);
	    killAction.setTarget(targetPlayer);

	    sourcePlayer.addAction(killAction);
	    
	    repository.save(sourcePlayer);
	    actionRepository.save(killAction);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
