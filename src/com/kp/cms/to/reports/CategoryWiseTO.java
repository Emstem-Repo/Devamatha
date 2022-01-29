package com.kp.cms.to.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryWiseTO implements Serializable {

	private String courseName;
	private String courseIntake;
	private Map<Integer, CategoryWiseMapTO> categoryMap = new HashMap<Integer, CategoryWiseMapTO>();
	private List<CategoryWiseMapTO> categoryWiseList = new ArrayList<CategoryWiseMapTO>();
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
	public Map<Integer, CategoryWiseMapTO> getCategoryMap() {
		return categoryMap;
	}
	public void setCategoryMap(Map<Integer, CategoryWiseMapTO> categoryMap) {
		this.categoryMap = categoryMap;
	}
	public List<CategoryWiseMapTO> getCategoryWiseList() {
		return categoryWiseList;
	}
	public void setCategoryWiseList(List<CategoryWiseMapTO> categoryWiseList) {
		this.categoryWiseList = categoryWiseList;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
}