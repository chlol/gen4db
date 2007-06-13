package com.googlecode.gen4db.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

public class MyReverseEngineeringDelegator extends
		DelegatingReverseEngineeringStrategy {
	public static final Log LOG = LogFactory
			.getLog(MyReverseEngineeringDelegator.class);

	public MyReverseEngineeringDelegator(ReverseEngineeringStrategy arg0) {
		super(arg0);
	}

	public String tableToClassName(TableIdentifier tableIdentifier) {

		String tableName = tableIdentifier.getName();
		String[] prefixes = Configuration.PREFIX.getTablePrefixes();
		TableIdentifier newTableIdentifier = tableIdentifier;

		if (prefixes != null && !prefixes.equals("")) {
			for (int i = 0; i < prefixes.length; i++) {
				if (tableName.startsWith(prefixes[i])) {
					tableName = tableName.substring(prefixes[i].length());
					newTableIdentifier = new TableIdentifier(tableIdentifier
							.getCatalog(), tableIdentifier.getSchema(),
							tableName);
				}

			}
		}

		String className = super.tableToClassName(newTableIdentifier);

		return className;
	}

}
