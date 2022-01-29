package com.kp.cms.handlers.phd;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.DocumentDetailsBO;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.helpers.phd.PhdDocumentSubmissionScheduleHelper;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.phd.DocumentDetailsSubmissionTO;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.transactions.phd.IPhdDocumentSubmissionSchedule;
import com.kp.cms.transactionsimpl.phd.PhdDocumentSubmissionScheduleTransactionImpl;

public class PhdDocumentSubmissionScheduleHandler {
	private static final Log log = LogFactory .getLog(PhdDocumentSubmissionScheduleHandler.class);
 private static volatile PhdDocumentSubmissionScheduleHandler documentSubmissionScheduleHandler = null;
 public static PhdDocumentSubmissionScheduleHandler getInstance(){
	 if(documentSubmissionScheduleHandler == null){
		 documentSubmissionScheduleHandler = new PhdDocumentSubmissionScheduleHandler();
		 return documentSubmissionScheduleHandler;
	 }
	 return documentSubmissionScheduleHandler;
 }
 
 IPhdDocumentSubmissionSchedule transactions= PhdDocumentSubmissionScheduleTransactionImpl.getInstance();
/**
 * @param assignSubGrpHistory
 * @return
 * @throws Exception
 */
	public List<PhdDocumentSubmissionScheduleTO> getDocumentSubmissionScheduleList(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception{
	log.info("call of getSubjectGroupsList method in AssignSubjectGroupHistoryHandler class.");
	List<Object[]> utilBOs=transactions.getDocumentSubmissionScheduleList(documentSubmissionScheduleForm);
	List<PhdDocumentSubmissionScheduleTO> groupDetailsTos = PhdDocumentSubmissionScheduleHelper.getInstance().populateSubGrpDetailsToTO(utilBOs);
	log.info("end of getSubjectGroupsList method in AssignSubjectGroupHistoryHandler class.");
	return groupDetailsTos;
}
public List<CourseTO> getCoursesByProgramTypes(String programTypeId) throws Exception{
	List<Object[]> course=transactions.getCoursesByProgramTypes(programTypeId);
	List<CourseTO> courseTo=PhdDocumentSubmissionScheduleHelper.getInstance().SetCourses(course);
	return courseTo;
}
public List<DocumentDetailsSubmissionTO> getDocumentDetailsList() throws Exception{
	List<DocumentDetailsBO> documentDetailsBOs=transactions.getDocumentDetailsList();
	List<DocumentDetailsSubmissionTO> documentDetailsTO=PhdDocumentSubmissionScheduleHelper.getInstance().convertBOsToTO(documentDetailsBOs);
	return documentDetailsTO;
	
}
public boolean addSubCategory(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm,String mode, ActionErrors errors) throws Exception{
	boolean isAdded=false;
	List<PhdDocumentSubmissionSchedule> documentDetails=PhdDocumentSubmissionScheduleHelper.getInstance().convertFormTOBO(documentSubmissionScheduleForm,errors);
	if(documentDetails!=null && !documentDetails.isEmpty()){
	isAdded=transactions.addSubCategory(documentDetails,mode);
	return isAdded;
	}
	return isAdded;
}
public void editStudentDetails(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception{
	List<PhdDocumentSubmissionSchedule> subStudentBo=transactions.getStudentDetailsById(documentSubmissionScheduleForm);
	List<Object[]> utilBOs=transactions.getDocumentSubmissionSchedule(documentSubmissionScheduleForm);
	PhdDocumentSubmissionScheduleHelper.getInstance().setBotoForm(documentSubmissionScheduleForm, subStudentBo,utilBOs);
}
public DocumentDetailsSubmissionTO getDocumentDetails(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception{
	DocumentDetailsBO documentDetailsBOs=transactions.getDocumentDetails(documentSubmissionScheduleForm);
    DocumentDetailsSubmissionTO documentDetailsTO=PhdDocumentSubmissionScheduleHelper.getInstance().convertBOsToTOs(documentDetailsBOs);
	return documentDetailsTO;
	
}
public List<DocumentDetailsSubmissionTO> getDocumentDetailsTo(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception{
	List<DocumentDetailsBO> documentDetailsBOs=transactions.getDocumentDetailsBo(documentSubmissionScheduleForm);
	List<DocumentDetailsSubmissionTO> documentDetailsTO=PhdDocumentSubmissionScheduleHelper.getInstance().convertBOsToTO(documentDetailsBOs);
	return documentDetailsTO;
	
}
public List<DocumentDetailsSubmissionTO> getDocumentList(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception{
	List<DocumentDetailsBO> documentDetailsBOs=transactions.getDocumentList(documentSubmissionScheduleForm);
	List<DocumentDetailsSubmissionTO> documentDetailsTO=PhdDocumentSubmissionScheduleHelper.getInstance().convertDocumentToTO(documentDetailsBOs);
	return documentDetailsTO;
	
}
public boolean updateDocumentDetails(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm,String mode) throws Exception{
	List<PhdDocumentSubmissionSchedule> subStudentBo=PhdDocumentSubmissionScheduleHelper.getInstance().convertFormToBO(documentSubmissionScheduleForm);
	boolean isUpdated=transactions.addSubCategory(subStudentBo,mode);
	return isUpdated;
}
public boolean deletePhdDocumentDetails(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm, String mode) throws Exception{
	boolean isDeleted=transactions.deletePhdDocumentDetails(documentSubmissionScheduleForm,mode);
	return isDeleted;
}
public List<PhdDocumentSubmissionScheduleTO> getDocumentSubmissionNotChange(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) {
	List<PhdDocumentSubmissionScheduleTO> groupDetailsTos = PhdDocumentSubmissionScheduleHelper.getInstance().getDocumentSubmissionNotChange(documentSubmissionScheduleForm);
	return groupDetailsTos;
}
public List<PhdDocumentSubmissionScheduleTO> getDocumentSubmissionByReg(PhdDocumentSubmissionScheduleForm objForm) throws Exception{
	List<PhdDocumentSubmissionSchedule> subStudentBo=transactions.getDocumentSubmissionByReg(objForm);
	List<PhdDocumentSubmissionScheduleTO> groupDetailsTos = PhdDocumentSubmissionScheduleHelper.getInstance().SetDocumentSubmissionByReg(subStudentBo,objForm);
	return groupDetailsTos;
}
public boolean upDateDocumentSubmission(PhdDocumentSubmissionScheduleForm objForm) throws Exception{
	List<PhdDocumentSubmissionSchedule> documentList=PhdDocumentSubmissionScheduleHelper.getInstance().documentFormToBO(objForm);
	boolean isUpdated=transactions.addDocumentList(documentList);
	return isUpdated;
}
public List<PhdDocumentSubmissionScheduleTO> getPendingDocumentSearch(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception{
	log.info("call of getSubjectGroupsList method in AssignSubjectGroupHistoryHandler class.");
	List<PhdDocumentSubmissionSchedule> documentBo=transactions.getPendingDocumentSearch(documentSubmissionScheduleForm);
	List<PhdDocumentSubmissionScheduleTO> documentTos = PhdDocumentSubmissionScheduleHelper.getInstance().setDataBotoTo(documentBo);
	log.info("end of getSubjectGroupsList method in AssignSubjectGroupHistoryHandler class.");
	return documentTos;
}

}
