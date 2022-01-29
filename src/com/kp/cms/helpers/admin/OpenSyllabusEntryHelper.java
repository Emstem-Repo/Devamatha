package com.kp.cms.helpers.admin;


public class OpenSyllabusEntryHelper {
	public static volatile OpenSyllabusEntryHelper openSyllabusEntryHelper=null;
	//private constructor
	private OpenSyllabusEntryHelper(){
		
	}
	//singleton object
	public static OpenSyllabusEntryHelper getInstance(){
		if(openSyllabusEntryHelper==null){
			openSyllabusEntryHelper=new OpenSyllabusEntryHelper();
			return openSyllabusEntryHelper;
		}
		return openSyllabusEntryHelper;
	}
}
