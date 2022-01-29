package com.kp.cms.to.exam;

import java.io.Serializable;
/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSubjectSectionMasterTO implements Serializable,Comparable<ExamSubjectSectionMasterTO> {
	private int id;
	private String name;
	private int isinitialise;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsinitialise() {
		return isinitialise;
	}
	public void setIsinitialise(int isinitialise) {
		this.isinitialise = isinitialise;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int compareTo(ExamSubjectSectionMasterTO arg0) {
		if(arg0!=null && this!=null && arg0.getName()!=null
				 && this.getName()!=null){
			return this.getName().compareTo(arg0.getName());
		}else
			return 0;
	}

}
