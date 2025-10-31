package com.java.crv.BibleReaderCommentary.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app")
@Configuration
public class BRCProperties {

	private String version;
	private String name;
	private String bibleFilePath;
	private String databasePath;
	private String seedUserUsername;
	private String seedUserPassword;
	
	public String getBibleFilePath() {
		return bibleFilePath;
	}
	public void setBibleFilePath(String bibleFilePath) {
		this.bibleFilePath = bibleFilePath;
	}
	public String getDatabasePath() {
		return databasePath;
	}
	public void setDatabasePath(String databasePath) {
		this.databasePath = databasePath;
	}
	public String getSeedUserUsername() {
		return seedUserUsername;
	}
	public void setSeedUserUsername(String seedUserUsername) {
		this.seedUserUsername = seedUserUsername;
	}
	public String getSeedUserPassword() {
		return seedUserPassword;
	}
	public void setSeedUserPassword(String seedUserPassword) {
		this.seedUserPassword = seedUserPassword;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
