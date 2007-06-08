package com.googlecode.gen4db.util;

import java.util.ArrayList;
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
	//for jdbc configuration
	public static String HIBERNATE_DIALECT = "hibernate.dialect";
	public static String JDBC_DRIVERCLASSNAME = "hibernate.connection.driver_class";
	public static String JDBC_URL = "hibernate.connection.url";
	public static String JDBC_USERNAME = "hibernate.connection.username";
	public static String JDBC_PASSWORD = "hibernate.connection.password";

	private static String CONFIG_FILE = "/gen4db.xml";

	private static Document MY_DOC = null;

	private static HashMap TABLE_PREFIX = new HashMap();

	private static HashMap TABLE_MODULE = new HashMap();

	public static Database DATABASE = new Database();

	public static Project PROJECT = new Project();

	public static Modules MODULES = new Modules();

	// init configuration
	static {
		SAXReader saxReader = new SAXReader();
		String inputXml = Configuration.class.getClass().getResource(
				CONFIG_FILE).getFile();
		try {
			MY_DOC = saxReader.read(inputXml);
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

			PROJECT.name = MY_DOC.selectSingleNode("//project/@name")
					.getText();
			PROJECT.version = MY_DOC.selectSingleNode("//project/@version")
					.getText();
			PROJECT.basePackage = MY_DOC.selectSingleNode(
					"//project/@basePackage").getText();

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

			List list = MY_DOC.selectNodes("//modules/module/table");
			for (int i = 0; i < list.size(); i++) {
				Node table = (Node) list.get(i);
				String prefix = "";
				String tableName = table.selectSingleNode("@name").getText();
				String tablePrefix = getNodeText(table
						.selectSingleNode("@prefix"));
				String module = getNodeText(table.getParent().selectSingleNode(
						"@name"));
				String modulePrefix = getNodeText(table.getParent()
						.selectSingleNode("@prefix"));
				String modulesPrefix = getNodeText(table.getParent()
						.getParent().selectSingleNode("@prefix"));

				if (tablePrefix != null && !tablePrefix.equals("")) {
					prefix = tablePrefix;
				} else if (modulePrefix != null && !modulePrefix.equals("")) {
					prefix = modulePrefix;
				} else {
					prefix = modulesPrefix;
				}

				TABLE_PREFIX.put(tableName, prefix);
				TABLE_MODULE.put(tableName, module);

				// debug
				if (LOG.isDebugEnabled()) {
					LOG.debug("tableName = " + tableName + "; prefix = "
							+ prefix + "; module = " + module);
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
			properties.setProperty(HIBERNATE_DIALECT,hibernateDialect);
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
		public List getModules() {
			List modules = new ArrayList();
			List list = MY_DOC.selectNodes("//modules/module");

			for (int i = 0; i < list.size(); i++) {
				Node moduleNode = (Node) list.get(i);
				Module module = new Module();
				module
						.setName(getNodeText(moduleNode
								.selectSingleNode("@name")));
				List tableNodes = moduleNode.selectNodes("table");
				for (int j = 0; j < tableNodes.size(); j++) {
					Node tableNode = (Node) tableNodes.get(j);
					Table table = new Table();
					table.setName(getNodeText(tableNode
							.selectSingleNode("@name")));
					table.setPrefix(getNodeText(tableNode
							.selectSingleNode("@prefix")));
					module.addTable(table);
				}
				modules.add(module);
			}

			return modules;
		}

		public String getTablePrefix(String tableName) {
			return (String) TABLE_PREFIX.get(tableName);
		}

		public String getTableModule(String tableName) {
			return (String) TABLE_MODULE.get(tableName);
		}
	}

	public static class Module {
		private String name = null;

		private List tables = new ArrayList();

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List getTables() {
			return tables;
		}

		public void setTables(List tables) {
			this.tables = tables;
		}

		public void addTable(Table table) {
			this.tables.add(table);
		}

	}

	public static class Table {
		private String name = null;

		private String prefix = null;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

	}

}
