package com.googlecode.gen4db.exporter;

import org.hibernate.tool.hbm2x.AbstractExporter;

public class ProfileExporter extends AbstractExporter {
	private String templateName;
	private String basePackage;
	private String profile = null;//example:service,model,web

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	protected void doStart() {
				
	}
}
