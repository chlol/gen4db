package org.hibernate.tool.hbm2x;

import java.io.File;
import java.util.Map;

import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.tool.hbm2x.GenericExporter;
import org.hibernate.tool.hbm2x.TemplateProducer;
import org.hibernate.tool.hbm2x.pojo.POJOClass;
import org.hibernate.util.StringHelper;

import com.googlecode.gen4db.util.Configuration;

public class ProfileExporter extends GenericExporter {
	private String profile = null;//example:service,model,web

	public String getProfile() {
		if (profile == null) {
			return "";
		}
		return profile;
	}

	public ProfileExporter() {		
		super();
	}

	public ProfileExporter(org.hibernate.cfg.Configuration cfg, File outputdir) {
		super(cfg, outputdir);
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	protected void exportPOJO(Map additionalContext, POJOClass element) {try {
		TemplateProducer producer = new TemplateProducer(getTemplateHelper(),getArtifactCollector());
		log.debug("POJOClass Instance : " + element);
		log.debug("POJOClass GetDecoratedObject Instance : " + element);
		additionalContext.put("pojo", element);
		additionalContext.put("clazz", element.getDecoratedObject());
		//if generate java class ,the package=basePackage + profile + module,the filename = the package + name;
		//else the filename = profile + module + name
		
		String tableName = null;
		Object obj = element.getDecoratedObject();
		if (obj instanceof PersistentClass) {
			PersistentClass pClazz = (PersistentClass) obj ;		
			tableName = pClazz.getTable().getName();
		}
		else if (obj instanceof Component) {
			Component pClazz = (Component) obj ;		
			tableName = pClazz.getTable().getName();
		}
		if (tableName == null || tableName.equals("")) {
			log.error("Cann't get the table name");
		}
		String moduleName = this.getModuleName(tableName);
		
		additionalContext.put("profile", this.getProfile());		
		additionalContext.put("module", moduleName);
		String filename = StringHelper.replace(this.getFilePattern(), "{class-name}", getClassNameForFile( element ));

		filename = StringHelper.replace(filename, "{module-name}", moduleName.equals("")?null:moduleName); 
		filename = StringHelper.replace(filename, "{profile-name}", this.getProfile());	
		String packageLocation = StringHelper.replace(getPackageNameForFile( element ),".", "/");
		if(StringHelper.isEmpty(packageLocation)) {
			packageLocation = "."; // done to ensure default package classes doesn't end up in the root of the filesystem when outputdir=""
		}
		filename = StringHelper.replace(filename, "{package-name}", packageLocation);		
		if(filename.endsWith(".java") && filename.indexOf('$')>=0) {
			log.warn("Filename for " + getClassNameForFile( element ) + " contains a $. Innerclass generation is not supported.");
		}
		producer.produce(additionalContext, getTemplateName(), new File(getOutputDirectory(),filename), this.getTemplateName());}catch(Exception e){e.printStackTrace();}
	}
	
	protected String getModuleName(String tableName) {
		String module = Configuration.MODULE.getTableModule(tableName);
		if (module == null) {
			module = "";
		}
		return module;
	}
	
	protected void setupContext() {
		if(!getProperties().containsKey("ejb3")) {
			getProperties().put("ejb3", "false");
		}
		if(!getProperties().containsKey("jdk5")) {
			getProperties().put("jdk5", "false");
		}	
		super.setupContext();
	}
}
