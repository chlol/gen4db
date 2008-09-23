package org.hibernate.tool.ant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.tool.ant.GenericExporterTask;
import org.hibernate.tool.hbm2x.Exporter;
import org.hibernate.tool.hbm2x.ProfileExporter;
import org.hibernate.util.ReflectHelper;
import org.apache.tools.ant.BuildException;


public class ProfileExporterTask extends GenericExporterTask {
	protected Log log = LogFactory.getLog(ProfileExporterTask.class);

	private String profile = null;

	private boolean isEjb3 = false;

	public ProfileExporterTask(Gen4dbToolTask parent) {
		super(parent);
	}

	public Exporter createExporter() {
		if (exporterClass == null) {
			if (isEjb3) {
				return new ProfileExporter();
			}
			else {
				return new ProfileExporter();
			}
			
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
		log.debug("begin set ProfileExporter......");
		ProfileExporter exp = (ProfileExporter) exporter;
		super.configureExporter(exporter);
		
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
			exp.getProperties().setProperty("ejb3", "true" );
			exp.getProperties().setProperty("jdk5", "true");
		}

		return exp;
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


	public String getName() {
		return "Profile Exporter";
	}

}
