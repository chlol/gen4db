package org.hibernate.tool.ant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.tool.ant.ExporterTask;
import org.hibernate.tool.hbm2x.Exporter;
import org.hibernate.util.ReflectHelper;
import org.apache.tools.ant.BuildException;

import com.googlecode.gen4db.ant.Gen4dbToolTask;
import com.googlecode.gen4db.exporter.ProfileExporter;

public class ProfileExporterTask extends ExporterTask {
	protected Log log = LogFactory.getLog(ProfileExporterTask.class);

	private String templateName = null;

	private String filePattern = null;

	private String exporterClass;

	private String profile = null;

	private boolean isEjb3 = false;

	public ProfileExporterTask(Gen4dbToolTask parent) {
		super(parent);
	}

	public Exporter createExporter() {
		if (exporterClass == null) {
			return new ProfileExporter();
		} else {
			try {
				Class theClass = ReflectHelper.classForName(exporterClass);
				return (Exporter) theClass.newInstance();
			} catch (ClassNotFoundException e) {
				throw new BuildException(
						"Could not find custom exporter class: "
								+ exporterClass, e);
			} catch (InstantiationException e) {
				throw new BuildException(
						"Could not create custom exporter class: "
								+ exporterClass, e);
			} catch (IllegalAccessException e) {
				throw new BuildException(
						"Could not access custom exporter class: "
								+ exporterClass, e);
			}
		}
	}

	public Exporter configureExporter(Exporter exporter) {
		super.configureExporter(exporter);

		if (exporter instanceof ProfileExporter) {
			log.debug("begin set ProfileExporter......");
			ProfileExporter exp = (ProfileExporter) exporter;
			if (filePattern != null) {
				exp.setFilePattern(filePattern);
			}
			if (templateName != null) {
				exp.setTemplateName(templateName);
			}
			if (profile != null) {
				exp.setProfile(profile);
			}
			if (isEjb3) {
				exp.getProperties().setProperty("ejb3", "" + isEjb3);
				exp.getProperties().setProperty("jdk5", "" + isEjb3);
			}
		}

		return exporter;
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
	public String getTemplate() {
		return templateName;
	}

	/**
	 * @param templateName
	 *            the templateName to set
	 */
	public void setTemplate(String templateName) {
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
	 * @param profile
	 *            the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}

	/**
	 * @return the exporterClass
	 */
	public String getExporterClass() {
		return exporterClass;
	}

	/**
	 * @param exporterClass
	 *            the exporterClass to set
	 */
	public void setExporterClass(String exporterClass) {
		this.exporterClass = exporterClass;
	}

	String getName() {
		return "Profile Exporter";
	}

}
