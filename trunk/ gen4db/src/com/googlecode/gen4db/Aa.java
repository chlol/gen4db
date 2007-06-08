package com.googlecode.gen4db;

import java.util.List;

import com.googlecode.gen4db.util.Configuration;

public class Aa {
	public static void main(String[] args) {
		List list = Configuration.MODULES.getModules();
		System.out.println(list.size());
	}
}
