package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admission.PromoteMarks;
import com.kp.cms.bo.admission.PromoteSubjects;
import com.kp.cms.bo.admission.PromoteSupliMarks;
import com.kp.cms.forms.admission.PromoteMarksUploadForm;

public interface IPromoteMarksUploadTransaction {

	boolean uploadPromoteMarks(List<PromoteMarks> promoteMarks,List<String> regNos,PromoteMarksUploadForm marksUploadForm)throws Exception;
	public List<PromoteMarks> getPromoteMarks(PromoteMarksUploadForm marksUploadForm)throws Exception;
	public PromoteSubjects getPromoteSubjects(String course)throws Exception;
	public String getSecondLang(String regNo, Integer academicYear)throws Exception;
	public List<PromoteSupliMarks> getPromoteSupliMarks(PromoteMarksUploadForm marksUploadForm)throws Exception;
	public Map<String,String> getCourses(String mode)throws Exception;
	public List<String> getPromoteMarksRegNos(int year)throws Exception;
}
