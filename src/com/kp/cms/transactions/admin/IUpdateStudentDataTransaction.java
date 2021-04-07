package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.forms.admin.UpdateStudentDataForm;
import com.kp.cms.to.admin.UpdateStudentDataTO;

public interface IUpdateStudentDataTransaction {

	List<UpdateStudentDataTO> getStudentDetailsList(UpdateStudentDataForm updateStudentDataForm) throws Exception;
	List<UpdateStudentDataTO> getPrevStudent(UpdateStudentDataForm updateStudentDataForm) throws Exception;
	String getClassName(UpdateStudentDataForm updateStudentDataForm) throws Exception;
	PersonalData getData(int studentId) throws Exception;
	boolean updateStudentData(List<PersonalData> bo) throws Exception;
	String getParentMob(int studentId) throws Exception;
	

}
