package br.com.nessauepa.logparser.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.nessauepa.logparser.service.MatchService;

public class LogParserListener implements ServletContextListener {

	private MatchService service;
	
	private ActionParser[] actionParsers = {
			new EnterActionParser(), 
			new KillActionParser(), 
			new EndActionParser()};
	
	private Long currentGameId;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Injeta repository no contexto do spring
		ServletContext ctx = sce.getServletContext(); 
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
		service = (MatchService) springContext.getBean(MatchService.class);
		
		try {
			parseLog(sce.getServletContext());
		} catch (Exception e) {
			throw new RuntimeException("Failed parsing text log.", e);
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
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
		
		int parser = 0;
		while (parser < actionParsers.length && !actionParsers[parser].parse(line)) {
			parser++;
		}
	}

	private interface ActionParser{
		boolean parse(String line) throws ParseException;
		
	}
	
	private class KillActionParser implements ActionParser {

		public static final String PATTERN = "^([\\w/]+\\s[\\w:]+) (\\S+) ([\\w]+) (killed) ([\\w]+) (using|by) (.+?)";
		
		@Override
		public boolean parse(String line) throws ParseException {
			Pattern p = Pattern.compile(PATTERN);
		    Matcher matcher = p.matcher(line);
		    if (!matcher.matches()) {
		    	System.out.println("Skiping: is not a kill action.");
		    	return false;
		    }

		    Calendar date = Calendar.getInstance();
		    date.setTime(new SimpleDateFormat("dd/MM/yyy hh:mm:ss").parse(matcher.group(1)));
		    String sourcePlayer = matcher.group(3);
		    String targetPlayer = matcher.group(5);
		    if (matcher.group(6).equals("using")) {
		    	service.addWeaponKillHistory(currentGameId, sourcePlayer, targetPlayer, date, matcher.group(7));
		    } else {
		    	service.addKillHistory(currentGameId, sourcePlayer, targetPlayer, date);
		    }
		    return true;
		}
	}

	private class EnterActionParser implements ActionParser {

		public static final String PATTERN = "^([\\w/]+\\s[\\w:]+) (\\S+) (New match) ([\\w]+) (has started)";
		
		@Override
		public boolean parse(String line) {
			Pattern p = Pattern.compile(PATTERN);
		    Matcher matcher = p.matcher(line);
		    if (!matcher.matches()) {
		    	System.out.println("Skiping: is not a enter action.");
		    	return false;
		    }

		    currentGameId = Long.parseLong(matcher.group(4));
		    return true;
		}
	}
	
	private class EndActionParser implements ActionParser {

		public static final String PATTERN = "^([\\w/]+\\s[\\w:]+) (\\S+) (Match) ([\\w]+) (has ended)";
		
		@Override
		public boolean parse(String line) {
			Pattern p = Pattern.compile(PATTERN);
		    Matcher matcher = p.matcher(line);
		    if (!matcher.matches()) {
		    	System.out.println("Skiping: is not a end action.");
		    	return false;
		    }

		    currentGameId = null;
		    return true;
		}
	}
}
