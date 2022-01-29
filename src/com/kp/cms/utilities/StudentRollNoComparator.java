package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.admin.StudentTO;

public class StudentRollNoComparator implements Comparator<StudentTO>{

	private boolean isRegNoCheck;
	
    @Override
 	public int compare(StudentTO s1, StudentTO s2) {
    	if(s1.getRollNo() != null && s2.getRollNo() != null && !s1.getRollNo().equalsIgnoreCase("") && !s2.getRollNo().equalsIgnoreCase(""))
       	/* return new Integer(s1.getRollNo()).compareTo(new Integer(s2.getRollNo()));*/
    		 return  s1.getRollNo().compareTo(s2.getRollNo());
     
     else if(this.isRegNoCheck && s1.getRegisterNo() != null && s2.getRegisterNo() != null)
    	 return s1.getRegisterNo().compareTo(s2.getRegisterNo());
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
