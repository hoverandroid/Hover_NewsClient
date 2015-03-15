package com.hover.newsclientapp2.constant;

public class NewsConstant {
	public static NewsConstant instance;

	public NewsConstant() {
	}

	public static NewsConstant getInstance() {
		if (instance == null) {
			instance = new NewsConstant();
		}
		return instance;
	}

	public static final String CATEGORY_ACTION = "com.hover.app";
}
