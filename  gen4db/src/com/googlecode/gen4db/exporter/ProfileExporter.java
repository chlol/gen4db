package com.googlecode.gen4db.exporter;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.mapping.Component;
import org.hibernate.tool.hbm2x.AbstractExporter;
import org.hibernate.tool.hbm2x.ConfigurationNavigator;
import org.hibernate.tool.hbm2x.ExporterException;
import org.hibernate.tool.hbm2x.TemplateProducer;
import org.hibernate.tool.hbm2x.pojo.ComponentPOJOClass;
import org.hibernate.tool.hbm2x.pojo.POJOClass;
import org.hibernate.util.StringHelper;

import com.googlecode.gen4db.util.Configuration;

public class ProfileExporter extends AbstractExporter {
	private String templateName = null;
	private String basePackage = null;
	private String profile = null;//example:service,model,web
	private boolean isJavaClass = true;

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
		if (this.basePackage == null) {
			this.basePackage = Configuration.PROJECT.getBasePackage();
		}
		this.basePackage = basePackage;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	protected void doStart() {
		if(templateName == null) throw new ExporterException("Template name not set on " + this.getClass());
		
		exportClasses();
	}
	
	private void exportClasses() {
		Map components = new HashMap();
		
		Iterator iterator = getCfg2JavaTool().getPOJOIterator(getConfiguration().getClassMappings());
		Map additionalContext = new HashMap();
		while ( iterator.hasNext() ) {					
			POJOClass element = (POJOClass) iterator.next();
			ConfigurationNavigator.collectComponents(components, element);						
			exportPersistentClass( additionalContext, element );
		}
				
		iterator = components.values().iterator();
		while ( iterator.hasNext() ) {					
			Component component = (Component) iterator.next();
			ComponentPOJOClass element = new ComponentPOJOClass(component,getCfg2JavaTool());
			exportComponent( additionalContext, element );
		}
				        
	}

	protected void exportComponent(Map additionalContext, POJOClass element) {
		exportPOJO(additionalContext, element);		
	}

	protected void exportPersistentClass(Map additionalContext, POJOClass element) {
		exportPOJO(additionalContext, element);		
	}

	protected void exportPOJO(Map additionalContext, POJOClass element) {
		TemplateProducer producer = new TemplateProducer(getTemplateHelper(),getArtifactCollector());					
		additionalContext.put("pojo", element);
		additionalContext.put("clazz", element.getDecoratedObject());
		//if generate java class ,the package=basePackage + profile + module,the filename = the package + name;
		//else the filename = profile + module + name
		String filename = null; 
		String tableName = "";
		if (this.isJavaClass) {
			String thePackage = this.basePackage + (this.profile == null?"":"."+this.profile) + this.getModuleName(tableName) == null?"":"." + this.getModuleName(tableName);
			
			filename = thePackage + element.getDeclarationName(); 
		}
		else {
			filename = (this.profile == null?"":"."+this.profile) + this.getModuleName(tableName) == null?"":"." + this.getModuleName(tableName) + element.getDeclarationName();
		}
		filename = StringHelper.replace(filename, ".", "/");
		
		producer.produce(additionalContext, getTemplateName(), new File(getOutputDirectory(),filename), templateName);
	}
	
	protected String getModuleName(String tableName) {
		return Configuration.MODULE.getTableModule(tableName);
	}
}
