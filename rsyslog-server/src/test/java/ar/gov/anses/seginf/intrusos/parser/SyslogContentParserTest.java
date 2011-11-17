package ar.gov.anses.seginf.intrusos.parser;

import org.junit.Test;
import static org.junit.Assert.*;

public class SyslogContentParserTest{

	@Test
	public void testSudoCommand() {
		SyslogContentParser parser = new SyslogContentParser("sudo:    gbrey : TTY=pts/1 ; PWD=/home/gbrey ; USER=root ; COMMAND=/bin/cat /var/log/message");
		assertEquals("sudo",parser.getLogReporter());
		assertEquals("gbrey",parser.getUser());
		assertEquals("pts/1",parser.getParams().get("TTY"));
		assertEquals("/home/gbrey",parser.getParams().get("PWD"));
		assertEquals("root",parser.getParams().get("USER"));
		assertEquals("/bin/cat /var/log/message",parser.getParams().get("COMMAND"));
	}

}
