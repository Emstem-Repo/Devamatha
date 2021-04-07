package com.kp.cms.handlers.hostel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.forms.hostel.HostelStudentDetailsForm;
import com.kp.cms.helpers.hostel.HostelStudentDetailsHelper;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.transactions.hostel.IHostelStudentDetailsTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelStudentDetailsTxnImpl;

public class HostelStudentDetailsHandler {

	IHostelStudentDetailsTransaction impl=new HostelStudentDetailsTxnImpl();	

	HostelStudentDetailsHelper helper=new HostelStudentDetailsHelper();

   public boolean checkHostelStudentIsCanceled(HostelStudentDetailsForm studentDetailsForm) throws Exception
   {
	return impl.checkHostelStudentIsCanceled(studentDetailsForm);
   }
   public List<HlAdmissionTo> getHostelSearchedStudents(HostelStudentDetailsForm studentDetailsForm) throws Exception
   {
		StringBuffer query = impl.getSerchedHostelStudentsQuery(studentDetailsForm);
		List<HlAdmissionBo> admissionBoList=impl.getSerchedHostelStudents(query);
		
		List<ExamStudentDetentionRejoinDetails> examStudentDetentionRejoinDetails=impl.getExamHostelStudentDetentionRejoinDetails();
		
		Map<String,ExamStudentDetentionRejoinDetails> detentionRejoinMap=new HashMap<String, ExamStudentDetentionRejoinDetails>();
		if(examStudentDetentionRejoinDetails!=null){
			Iterator<ExamStudentDetentionRejoinDetails> iterator=examStudentDetentionRejoinDetails.iterator();
			while(iterator.hasNext()){
				ExamStudentDetentionRejoinDetails details=iterator.next();
				if(details!=null){
					Student student=details.getStudent();
					if(student!=null && String.valueOf(student.getId())!=null &&!String.valueOf(student.getId()).isEmpty()){
						detentionRejoinMap.put(String.valueOf(student.getId()), details);
					}
				}
			}
		}
	//List<Integer> studentPhotoList = impl.getSerchedHostelStudentsPhotoList(studentDetailsForm);
		
        List<HlAdmissionTo> admissionToList = helper.convertHostelStudentBOtoTO(admissionBoList,detentionRejoinMap);
        return admissionToList;
   }
}
