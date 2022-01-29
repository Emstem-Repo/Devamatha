package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.employee.InterviewRatingFactor;
import com.kp.cms.bo.employee.NewInterviewComments;
import com.kp.cms.bo.employee.NewInterviewCommentsDetails;
import com.kp.cms.forms.employee.NewInterviewCommentsForm;

public interface INewInterviewCommentsTransaction {


	public boolean addInterviewComments(NewInterviewComments newInterviewComments, NewInterviewCommentsForm interviewCommentsForm) throws Exception;

	public List<InterviewRatingFactor> getInterviewRatingFactor(NewInterviewCommentsForm interviewCommentsForm) throws Exception;

	public	List<Object[]> getEmpOnlineResumeList() throws Exception;

	public List<Object[]> getEmpOnlineResumeList1(NewInterviewCommentsForm interviewCommentsForm) throws Exception;

	public NewInterviewComments getEmpInfo(NewInterviewCommentsForm interviewCommentsForm) throws Exception;

	public List<NewInterviewComments> getPrintDetailsList(NewInterviewCommentsForm interviewCommentsForm)throws Exception;

	public List<NewInterviewCommentsDetails> getPrintCommentsDetailsList(NewInterviewCommentsForm interviewCommentsForm)throws Exception;

	public Object[] getEmpDetails(NewInterviewCommentsForm interviewCommentsForm)throws Exception;

	public List<EmpOnlineResume> getEmpInfoDetails(NewInterviewCommentsForm interviewCommentsForm)throws Exception;

	public Object[] getNoOfInterviewers(NewInterviewCommentsForm interviewCommentsForm)throws Exception;

	public List<EmpOnlineResume> getEmpEduDetails(NewInterviewCommentsForm interviewCommentsForm) throws Exception;

	public List<Object[]> getEmpOnlineEducationalList( NewInterviewCommentsForm interviewCommentsForm)throws Exception;

	public void updateDepartmentIdInEmpOnlineResume(NewInterviewCommentsForm interviewCommentsForm)throws Exception;
}
