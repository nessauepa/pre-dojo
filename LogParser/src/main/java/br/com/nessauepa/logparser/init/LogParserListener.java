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

import br.com.nessauepa.logparser.service.PlayerService;

public class LogParserListener implements ServletContextListener {

	private PlayerService service;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Injeta repository no contexto do spring
		ServletContext ctx = sce.getServletContext(); 
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
		service = (PlayerService) springContext.getBean(PlayerService.class);
		
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

	private void parseAction(Calendar date, String action) {
		System.out.println(" > Parsing action: " + action);

		ActionLogParser[] actionParsers = {new KillActionLogParser()};
		
		int parser = 0;
		while (parser < actionParsers.length && !actionParsers[parser].parse(date, action)) {
			parser++;
		}
	}

	private interface ActionLogParser{
		boolean parse(Calendar date, String logEntry);
		
	}
	
	private class KillActionLogParser implements ActionLogParser {

		String pattern = "^([\\w]+) (killed) ([\\w]+) (.+?)";
		
		@Override
		public boolean parse(Calendar date, String logEntry) {
			Pattern p = Pattern.compile(pattern);
		    Matcher matcher = p.matcher(logEntry);
		    if (!matcher.matches()) {
		    	System.out.println("Skiping: is not a kill action.");
		    	return false;
		    }

		    String sourcePlayer = matcher.group(1);
		    String targetPlayer = matcher.group(3);
		    service.saveKillAction(sourcePlayer, targetPlayer, date);
		    return true;
		}
	}
}
