package com.kp.cms.to.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformaIIITO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String courseName;
	private String courseIntake;
	private Map<Integer, PerformaIIIMapTO> categoryMap = new HashMap<Integer, PerformaIIIMapTO>();
	private List<PerformaIIIMapTO> categoryList = new ArrayList<PerformaIIIMapTO>();
	private String total;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseIntake() {
		return courseIntake;
	}
	public void setCourseIntake(String courseIntake) {
		this.courseIntake = courseIntake;
	}
	public Map<Integer, PerformaIIIMapTO> getCategoryMap() {
		return categoryMap;
	}
	public void setCategoryMap(Map<Integer, PerformaIIIMapTO> categoryMap) {
		this.categoryMap = categoryMap;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public List<PerformaIIIMapTO> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<PerformaIIIMapTO> categoryList) {
		this.categoryList = categoryList;
	}
}
