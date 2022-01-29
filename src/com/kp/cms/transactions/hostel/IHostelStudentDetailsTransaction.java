package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.forms.hostel.HostelStudentDetailsForm;

public interface IHostelStudentDetailsTransaction {

	public boolean checkHostelStudentIsCanceled(HostelStudentDetailsForm detailsForm) throws Exception;
	
	public StringBuffer getSerchedHostelStudentsQuery(HostelStudentDetailsForm detailsForm) throws Exception;
	
	List<HlAdmissionBo> getSerchedHostelStudents(StringBuffer querry) throws Exception;
	
	List<ExamStudentDetentionRejoinDetails> getExamHostelStudentDetentionRejoinDetails();
	
	List<Integer> getSerchedHostelStudentsPhotoList(HostelStudentDetailsForm studentDetailsForm) throws Exception;
}
