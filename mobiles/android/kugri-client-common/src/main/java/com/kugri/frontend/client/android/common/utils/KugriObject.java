package com.kugri.frontend.client.android.common.utils;

import java.util.LinkedList;

public class KugriObject {
	private LinkedList<KugriField> fields;
	public int length;
	private double screen;
	private int layout;
	
	public KugriObject(){
		
	}
	
	public KugriObject(double screen, int layout) {
		this.fields = new LinkedList<KugriField>();
		this.length = 0;
		this.screen = screen;
		this.layout = layout;
	}
	
	public void addField(KugriField field){
		this.fields.add(field);
		length++;
	}
	
	public void deleteField(int index){
		this.fields.remove(index);
		if(length > 0) length--;
	}
	
	public KugriField getField(int index){
		return this.fields.get(index);
	}
	
	public LinkedList<KugriField> getFields() {
		return fields;
	}
	
	public double getScreen() {
		return screen;
	}
	
	public int getLayout() {
		return layout;
	}
}
