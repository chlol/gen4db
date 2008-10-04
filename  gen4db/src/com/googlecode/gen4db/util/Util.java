package com.googlecode.gen4db.util;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.mapping.Property;
import org.hibernate.mapping.ToOne;

public class Util {
	public String lower(String name) {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	public String upper(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public Set set() {
		return new HashSet();
	}

	public String replaceSession(String name) {
		return name.replaceAll(".session", ".entity");
	}
	
	public String replacePackageDeclaration(String base,String profile,String module) {
		return base.substring(0,base.length()-1) + "." + profile + "." + module + ";";
	}
	
	public String replacePackage(String base,String profile,String module) {
		return base + "." + profile + "." + module;
	}
	
	public String getPrimaryKeyType() {
		String result = "@javax.persistence.GeneratedValue(strategy=javax.persistence.GenerationType.AUTO)";
		String type = Configuration.PRIMARY_KEY_TYPE.getPrimaryKeyType();
		if ("IDENTITY".equals(type)) {
			result = "@javax.persistence.GeneratedValue(strategy=javax.persistence.GenerationType.IDENTITY)";
		}
		else if ("SEQUENCE".equals(type)) {
			result = "@javax.persistence.GeneratedValue(strategy=javax.persistence.GenerationType.SEQUENCE)";
		}
		else if ("TABLE".equals(type)) {
			result = "@javax.persistence.GeneratedValue(strategy=javax.persistence.GenerationType.TABLE)";
		}
		else {
			//do nothing
		}
		return result;
	}
	
	public String getObjectType(String baseType) {
		if ("int".equals(baseType)) {
			return "Integer";
		}
		else if ("long".equals(baseType)) {
			return "Long";
		}
		else if ("float".equals(baseType)) {
			return "Float";
		}
		else if ("boolean".equals(baseType)) {
			return "Boolean";
		}
		else if ("char".equals(baseType)) {
			return "Character";
		}
		else if ("double".equals(baseType)) {
			return "Double";
		}
		else if ("short".equals(baseType)) {
			return "Short";
		}
		else {
			return baseType;
		}
	}
	
	//TODO
	public String getSerialVersionUID() {
		return "";
	}
	
	public boolean isToOne(Property property)
   {
      return (property.getValue() != null) && (property.getValue() instanceof ToOne);
   }
}
