package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.BoardDetailsForm;

public interface IBoardDetailsTransaction {

	List<Student> getStudentDetails(String query) throws Exception;

	Map<String,Integer> getList(String query) throws Exception ;

	boolean updateDetails(BoardDetailsForm boardDetailsForm) throws Exception;
    
	public Map<Integer,Integer> getYearMap()throws Exception;
	
	public Map<Integer,String> getProgramMap()throws Exception;
	
	public String getClassSchemwiseIds(String search)throws Exception;
	
	public List<Integer> getDetainedOrDiscontinuedStudents() throws Exception;
}
