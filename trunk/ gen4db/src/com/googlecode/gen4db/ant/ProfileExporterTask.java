package com.googlecode.gen4db.ant;

import org.hibernate.tool.ant.GenericExporterTask;
import org.hibernate.tool.ant.HibernateToolTask;
import org.hibernate.tool.hbm2x.Exporter;

import com.googlecode.gen4db.exporter.ProfileExporter;

public class ProfileExporterTask extends GenericExporterTask {
	private String templateName = null;

	private String filePattern = null;

	private String profile = null;

	private boolean isEjb3 = false;

	public ProfileExporterTask(HibernateToolTask parent) {
		super(parent);
	}

	protected Exporter createExporter() {
		return new ProfileExporter();
	}

	protected Exporter configureExporter(Exporter exp) {
		super.configureExporter(exp);

		if (exp instanceof ProfileExporter) {
			ProfileExporter exporter = (ProfileExporter) exp;
			if (filePattern != null) {
				exporter.setFilePattern(filePattern);
			}
			if (templateName != null) {
				exporter.setTemplateName(templateName);
			}
			if (profile != null) {
				exporter.setProfile(profile);
			}
			if (isEjb3) {
				exporter.getProperties().setProperty("ejb3", "" + isEjb3);
				exporter.getProperties().setProperty("jdk5", "" + isEjb3);
			}
		}

		return exp;
	}

	public String getName() {
		return "profile exporter";
	}

	/**
	 * @return the filePattern
	 */
	public String getFilePattern() {
		return filePattern;
	}

	/**
	 * @param filePattern
	 *            the filePattern to set
	 */
	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName
	 *            the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * @return the isEjb3
	 */
	public boolean isEjb3() {
		return isEjb3;
	}

	/**
	 * @param isEjb3
	 *            the isEjb3 to set
	 */
	public void setEjb3(boolean isEjb3) {
		this.isEjb3 = isEjb3;
	}

	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}

}
