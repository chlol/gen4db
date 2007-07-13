package com.googlecode.gen4db.util;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * @author chenh
 * 
 */
public class Configuration {
	private static final Log LOG = LogFactory.getLog(Configuration.class);
	
	public static String SEPARATOR = ",";
	// for jdbc configuration
	public static String HIBERNATE_DIALECT = "hibernate.dialect";

	public static String JDBC_DRIVERCLASSNAME = "hibernate.connection.driver_class";

	public static String JDBC_URL = "hibernate.connection.url";

	public static String JDBC_USERNAME = "hibernate.connection.username";

	public static String JDBC_PASSWORD = "hibernate.connection.password";

	private static String CONFIG_FILE = "gen4db.xml";

	private static Document MY_DOC = null;

	private static HashMap TABLE_MODULE = new HashMap();

	public static Database DATABASE = new Database();
	
	public static Modules MODULE = new Modules();

	public static Project PROJECT = new Project();
	
	public static Prefix PREFIX = new Prefix();

	// init configuration
	static {
		SAXReader saxReader = new SAXReader();
		InputStream is = Configuration.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
		LOG.debug("Gen4db.xml InputStream : " + is);
		try {
			MY_DOC = saxReader.read(is);
			//for database
			DATABASE.hibernateDialect = MY_DOC.selectSingleNode(
					"//database/hibernate.dialect").getText();
			DATABASE.driver = MY_DOC.selectSingleNode(
					"//database/jdbc.driverClassName").getText();
			DATABASE.url = MY_DOC.selectSingleNode("//database/jdbc.url")
					.getText();
			DATABASE.username = MY_DOC.selectSingleNode(
					"//database/jdbc.username").getText();
			DATABASE.password = MY_DOC.selectSingleNode(
					"//database/jdbc.password").getText();
			
			//for project
			PROJECT.name = MY_DOC.selectSingleNode("//project/@name").getText();
			PROJECT.version = MY_DOC.selectSingleNode("//project/@version")
					.getText();
			PROJECT.basePackage = MY_DOC.selectSingleNode(
					"//project/@basePackage").getText();
			
			//for prefix
			String prefixes = MY_DOC.selectSingleNode("//prefix").getText();
			if (prefixes != null && !prefixes.equals("")) {
				PREFIX.tablePrefixes = prefixes.split(SEPARATOR);
			}

			// debug
			if (LOG.isDebugEnabled()) {
				LOG.debug("hibernateDialect = " + DATABASE.hibernateDialect);
				LOG.debug("jdbcDriver = " + DATABASE.driver);
				LOG.debug("jdbcUrl = " + DATABASE.url);
				LOG.debug("jdbcUsername = " + DATABASE.username);
				LOG.debug("jdbcPassword = " + DATABASE.password);
				LOG.debug("projectName = " + PROJECT.name);
				LOG.debug("projectVersion = " + PROJECT.version);
				LOG.debug("projectBasePackage = " + PROJECT.basePackage);
			}

			List list = MY_DOC.selectNodes("//modules/module");

			for (int i = 0; i < list.size(); i++) {
				Node moduleNode = (Node) list.get(i);
				String module = getNodeText(moduleNode
						.selectSingleNode("@name"));
				String tables = moduleNode.getText();
				if (tables != null && !tables.equals("")) {
					String[] temp = tables.split(SEPARATOR);
					for (int j = 0; j < temp.length; j++) {
						TABLE_MODULE.put(temp[j].trim(), module.trim());
					}
				}
			}

		} catch (DocumentException e) {
			LOG.error("parse the myseam.xml failure", e);
		}
	}

	private static String getNodeText(Node node) {

		if (node == null) {
			return null;
		}

		return node.getText();

	}

	// for the project info
	public static class Project {
		private String name;

		private String version;

		private String basePackage;

		public String getName() {
			return name;
		}

		public String getVersion() {
			return version;
		}

		public String getBasePackage() {
			return basePackage;
		}
	}

	// for the database configuration
	public static class Database {
		private String hibernateDialect;

		private String driver;

		private String password;

		private String url;

		private String username;

		public Properties getHibernateDbConfiguration() {
			Properties properties = new Properties();
			properties.setProperty(HIBERNATE_DIALECT, hibernateDialect);
			properties.setProperty(JDBC_DRIVERCLASSNAME, driver);
			properties.setProperty(JDBC_URL, url);
			properties.setProperty(JDBC_USERNAME, username);
			properties.setProperty(JDBC_PASSWORD, password);
			return properties;
		}

		public String getHibernateDialect() {
			return hibernateDialect;
		}

		public String getDriver() {
			return driver;
		}

		public String getPassword() {
			return password;
		}

		public String getUrl() {
			return url;
		}

		public String getUsername() {
			return username;
		}
	}

	public static class Modules {
		public String getTableModule(String tableName) {
			return (String) TABLE_MODULE.get(tableName);
		}
	}
	
	public static class Prefix {
		private String[] tablePrefixes = null;
		public String[] getTablePrefixes() {
			return tablePrefixes;
		}
	}

}
