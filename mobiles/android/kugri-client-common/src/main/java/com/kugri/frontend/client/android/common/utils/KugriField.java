package com.kugri.frontend.client.android.common.utils;

public class KugriField {
	private Class<?> fieldClass;
	private String fieldValue;
	private int fieldId;
	
	public KugriField(Class<?> fieldClass, int fieldId, String fieldValue) {
		super();
		this.fieldClass = fieldClass;
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
	}
	
	public Class<?> getFieldClass() {
		return fieldClass;
	}
	
	public String getFieldValue() {
		return fieldValue;
	}
	
	public int getFieldId() {
		return fieldId;
	}
}
