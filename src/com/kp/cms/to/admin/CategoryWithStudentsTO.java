package com.kp.cms.to.admin;

import java.util.List;

public class CategoryWithStudentsTO {
	private String categoryName;
	private List<CategoryWiseStudentTO> students;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<CategoryWiseStudentTO> getStudents() {
		return students;
	}
	public void setStudents(List<CategoryWiseStudentTO> students) {
		this.students = students;
	}
	
	
}
