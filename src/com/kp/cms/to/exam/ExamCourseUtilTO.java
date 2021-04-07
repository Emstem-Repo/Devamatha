package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamCourseUtilTO implements Serializable,Comparable<ExamCourseUtilTO> {
	private Integer id;
	private String display;

	public ExamCourseUtilTO() {
		super();
	}

	public ExamCourseUtilTO(Integer id, String display) {
		super();
		this.id = id;
		this.display = display;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	@Override
	public int compareTo(ExamCourseUtilTO arg0) {
		if(arg0!=null && this!=null && arg0.getDisplay()!=null
				 && this.getDisplay()!=null){
			return this.getDisplay().compareTo(arg0.getDisplay());
		}		
		else
		return 0;
	}

}
