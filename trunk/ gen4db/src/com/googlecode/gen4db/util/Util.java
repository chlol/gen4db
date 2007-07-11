package com.googlecode.gen4db.util;

import java.util.HashSet;
import java.util.Set;

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
}
