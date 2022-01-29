package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.admin.StudentTO;

public class StudentNameComparator implements Comparator<StudentTO>{
private boolean isRegNoCheck;
	
    @Override
 	public int compare(StudentTO s1, StudentTO s2) {
     if(this.isRegNoCheck && s1.getStudentName() != null && s2.getStudentName() != null)
    	 return s1.getStudentName().compareTo(s2.getStudentName());
     else if(s1.getStudentName() != null && s2.getStudentName() != null)
    	 return s1.getStudentName().compareTo(s2.getStudentName());
     else 
    	 return 0;
    }

	/**
	 * @return the isRegNoCheck
	 */
	public boolean isRegNoCheck() {
		return isRegNoCheck;
	}

	/**
	 * @param isRegNoCheck the isRegNoCheck to set
	 */
	public void setRegNoCheck(boolean isRegNoCheck) {
		this.isRegNoCheck = isRegNoCheck;
	}
}
