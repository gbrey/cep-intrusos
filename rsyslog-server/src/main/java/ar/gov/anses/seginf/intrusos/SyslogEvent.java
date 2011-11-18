package ar.gov.anses.seginf.intrusos;

import java.util.Date;

public class SyslogEvent {
	private Date createdAt;
	private String logReporter;
	private String user;
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getLogReporter() {
		return logReporter;
	}
	public void setLogReporter(String logReporter) {
		this.logReporter = logReporter;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	

}
