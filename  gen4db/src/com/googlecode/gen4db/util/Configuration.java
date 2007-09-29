package com.googlecode.gen4db.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

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
@SuppressWarnings("unchecked")
public class Configuration {
	private static final Log LOG = LogFactory.getLog(Configuration.class);
	
	public static String SEPARATOR = ",";
	private static String CONFIG_FILE = "gen4db.xml";
	private static Document MY_DOC = null;
	private static HashMap<String,String> TABLE_MODULE = new HashMap<String,String>();	
	public static Modules MODULE = new Modules();	
	public static Prefix PREFIX = new Prefix();
	
	public static PrimaryKeyType PRIMARY_KEY_TYPE = new PrimaryKeyType();

	// init configuration
	static {
		SAXReader saxReader = new SAXReader();
		InputStream is = Configuration.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
		LOG.debug("Gen4db.xml InputStream : " + is);
		try {
			MY_DOC = saxReader.read(is);
			
			//for prefix
			String prefixes = MY_DOC.selectSingleNode("//prefix").getText();
			if (prefixes != null && !prefixes.equals("")) {
				PREFIX.tablePrefixes = prefixes.split(SEPARATOR);
			}
			
			//for PrimaryKeyType
			String primaryKeyType = MY_DOC.selectSingleNode("//primaryKeyType").getText();
			if (primaryKeyType != null && !primaryKeyType.equals("")) {
				PRIMARY_KEY_TYPE.primaryKeyType = primaryKeyType;
			}

			List<Node> list = MY_DOC.selectNodes("//modules/module");

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
	
	public static class PrimaryKeyType {
		private String primaryKeyType = "AUTO";
		public String getPrimaryKeyType() {
			return primaryKeyType;
		}
	}

}
