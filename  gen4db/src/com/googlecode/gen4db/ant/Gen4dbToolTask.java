package com.googlecode.gen4db.ant;

import org.hibernate.tool.ant.ExporterTask;
import org.hibernate.tool.ant.HibernateToolTask;
import org.hibernate.tool.ant.ProfileExporterTask;


public class Gen4dbToolTask extends HibernateToolTask {
	public ExporterTask createProfileTemplate() {
		ExporterTask generator = new ProfileExporterTask(this);
		addGenerator( generator );
		return generator;
	}
}
