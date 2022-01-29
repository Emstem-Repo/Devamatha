package com.kp.cms.helpers.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.phd.DocumentDetailsBO;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.phd.DocumentDetailsSubmissionTO;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.utilities.CommonUtil;

public class PhdDocumentSubmissionScheduleHelper {
	private static final Log log = LogFactory .getLog(PhdDocumentSubmissionScheduleHelper.class);
 private static volatile PhdDocumentSubmissionScheduleHelper subjectGroupHistoryHelper = null;
 public static PhdDocumentSubmissionScheduleHelper getInstance(){
	 if(subjectGroupHistoryHelper == null){
		 subjectGroupHistoryHelper = new PhdDocumentSubmissionScheduleHelper();
		 return subjectGroupHistoryHelper;
	 }
	 return subjectGroupHistoryHelper;
 }
/**
 * @param utilBOs
 * @return
 */
public List<PhdDocumentSubmissionScheduleTO> populateSubGrpDetailsToTO(List<Object[]> utilBOs) {
	List<PhdDocumentSubmissionScheduleTO> documentlist=new ArrayList<PhdDocumentSubmissionScheduleTO>();
	if(utilBOs!=null){
		Iterator<Object[]> itr = utilBOs.iterator();
	 while (itr.hasNext()) {
		Object[] object = (Object[]) itr.next();
		PhdDocumentSubmissionScheduleTO documentTo= new PhdDocumentSubmissionScheduleTO();
		if(object[0]!=null){
			documentTo.setCourseName(object[0].toString());
		}if(object[1]!=null){
			documentTo.setRegisterNo(object[1].toString());
		}if(object[2]!=null){
			documentTo.setStudentName(object[2].toString());
		}if(object[3]!=null){
			documentTo.setFeePaidDate(object[3].toString());
		}if(object[4]!=null){
			documentTo.setStudentId(Integer.parseInt(object[4].toString()));
		}if(object[4]!=null){
			documentTo.setId(Integer.parseInt(object[4].toString()));
		}if(object[5]!=null){
			documentTo.setCourseId(object[5].toString());
		}if(object[6]!=null){
			if(object[7].equals(true))
			{
				documentTo.setEditCheck("Yes");
			}	
		}if(object[8]!=null){
			if(object[9]!=null || object[10]!=null)
			{
				documentTo.setStatus("Completed");
			}	
		}
		documentlist.add(documentTo);
	}
	}
	return documentlist;
}
public List<CourseTO> SetCourses(List<Object[]> course) {
	List<CourseTO> courseList=new ArrayList<CourseTO>();
	Iterator<Object[]> itr=course.iterator();
	while (itr.hasNext()) {
		CourseTO courseto=new CourseTO();
		Object[] courses = (Object[]) itr.next();
		courseto.setName(courses[2].toString()+"-"+courses[0].toString());
		courseto.setId(Integer.parseInt(courses[1].toString()));
		courseList.add(courseto);
	}
	return courseList;
}
public List<DocumentDetailsSubmissionTO> convertBOsToTO(List<DocumentDetailsBO> documentDetailsBOs) {
	List<DocumentDetailsSubmissionTO> documentDetailsTOList=new ArrayList<DocumentDetailsSubmissionTO>();
    Iterator<DocumentDetailsBO> iterator=documentDetailsBOs.iterator();
    while(iterator.hasNext())
    {
    DocumentDetailsBO documentDetailsBO=(DocumentDetailsBO) iterator.next()	;
    DocumentDetailsSubmissionTO documentDetailsTO=new DocumentDetailsSubmissionTO();
    documentDetailsTO.setId(documentDetailsBO.getId());
    documentDetailsTO.setDocumentName(documentDetailsBO.getDocumentName());
    documentDetailsTOList.add(documentDetailsTO);
    }
	return documentDetailsTOList;
}
public List<PhdDocumentSubmissionSchedule> convertFormTOBO(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm, ActionErrors errors) {
	List<PhdDocumentSubmissionSchedule> documentList=new ArrayList<PhdDocumentSubmissionSchedule>();
	Iterator<PhdDocumentSubmissionScheduleTO> itr=documentSubmissionScheduleForm.getStudentDetailsList().iterator();
	boolean flag=false;
	while (itr.hasNext()) {
		PhdDocumentSubmissionScheduleTO details = (PhdDocumentSubmissionScheduleTO) itr.next();
		if(details.getChecked()!=null && !details.getChecked().isEmpty()){
		if(details.getChecked().equalsIgnoreCase("on")){
			flag=true;
			Iterator<DocumentDetailsSubmissionTO> itrr=documentSubmissionScheduleForm.getDocumentList().iterator();
			while (itrr.hasNext()) {
				DocumentDetailsSubmissionTO document = (DocumentDetailsSubmissionTO) itrr.next();
		    	if((document.getChecked1()!=null && !document.getChecked1().isEmpty()) ||
				   (document.getChecked2()!=null && !document.getChecked2().isEmpty()) || 
				   (document.getChecked3()!=null && !document.getChecked3().isEmpty()) || 
				   (document.getChecked4()!=null && !document.getChecked4().isEmpty()) ||
				   (document.getAssignDate()!=null && !document.getAssignDate().isEmpty()))
				       {
				PhdDocumentSubmissionSchedule documentBo=new PhdDocumentSubmissionSchedule();
				DocumentDetailsBO doc=new DocumentDetailsBO();
				Student stu=new Student();
				Course course=new Course();
				stu.setId(details.getStudentId());
				course.setId(Integer.parseInt(details.getCourseId()));
				if(document.getDocumentId()==null){
					document.setDocumentId(Integer.toString(document.getId()));
					document.setId(0);
				}
				doc.setId(Integer.parseInt(document.getDocumentId()));
				documentBo.setStudentId(stu);
				documentBo.setCourseId(course);
				documentBo.setDocumentId(doc);
				if(document.getAssignDate()!=null && !document.getAssignDate().isEmpty()){
				documentBo.setAssignDate(CommonUtil.ConvertStringToDate(document.getAssignDate()));
				}
				if(document.getChecked1()!=null && !document.getChecked1().isEmpty()){
					documentBo.setIsReminderMail(true);
				}else{
					documentBo.setIsReminderMail(false);
				}if(document.getChecked2()!=null && !document.getChecked2().isEmpty()){
					documentBo.setGuidesFee(true);
				}else{
					documentBo.setGuidesFee(false);
				}if(document.getChecked3()!=null && !document.getChecked3().isEmpty()){
					documentBo.setCanSubmitOnline(true);
				}else{
					documentBo.setCanSubmitOnline(false);
				}if(document.getChecked4()!=null && !document.getChecked4().isEmpty()){
					documentBo.setSubmited(true);
				}else{
					documentBo.setSubmited(false);
				}if(document.getSubmittedDate()!=null && !document.getSubmittedDate().isEmpty()){
				documentBo.setSubmittedDate(CommonUtil.ConvertStringToDate(document.getSubmittedDate()));
				}if(document.getGuideFeeGenerated()!=null && !document.getGuideFeeGenerated().isEmpty()){
				documentBo.setGuideFeeGenerated(CommonUtil.ConvertStringToDate(document.getGuideFeeGenerated()));
				}documentBo.setId(document.getId());				
				documentBo.setCreatedBy(documentSubmissionScheduleForm.getUserId());
				documentBo.setCreatedDate(new Date());
				documentBo.setLastModifiedDate(new Date());
				documentBo.setModifiedBy(documentSubmissionScheduleForm.getUserId());
				documentBo.setIsActive(true);
				documentList.add(documentBo);
				}
						
			}
		}}
	}if(!flag){
		errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.student.norecord"));
	}
	return documentList;
}
public void setBotoForm(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm,List<PhdDocumentSubmissionSchedule> subStudentBo, List<Object[]> utilBOs) {
	List<DocumentDetailsSubmissionTO> editdocumentList=new ArrayList<DocumentDetailsSubmissionTO>();
	List<PhdDocumentSubmissionScheduleTO> documentlist=new ArrayList<PhdDocumentSubmissionScheduleTO>();
	if(utilBOs!=null){
		Iterator<Object[]> itr = utilBOs.iterator();
	while (itr.hasNext()) {
		Object[] object = (Object[]) itr.next();
		PhdDocumentSubmissionScheduleTO documentTo= new PhdDocumentSubmissionScheduleTO();
		if(object[0]!=null){
			documentTo.setCourseName(object[0].toString());
		}if(object[1]!=null){
			documentTo.setRegisterNo(object[1].toString());
		}if(object[2]!=null){
			documentTo.setStudentName(object[2].toString());
		}if(object[3]!=null){
		   documentTo.setFeePaidDate(object[3].toString());
	   }if(object[4]!=null){
			documentTo.setStudentId(Integer.parseInt(object[4].toString()));
		}if(object[5]!=null){
			documentTo.setCourseId(object[5].toString());
		}if(object[6]!=null){
		      if(object[7].equals(true)){
			documentTo.setEditCheck("Yes");
		      }
		}if(object[4]!=null){
			documentTo.setId(Integer.parseInt(object[4].toString()));
		}if(documentSubmissionScheduleForm.getId()==Integer.parseInt(object[4].toString()) 
			&& object[6]!=null && object[7].equals(true)){
			documentTo.setChecked("on");
			documentTo.setTempChecked("on");
		}if(object[8]!=null){
			if(object[9]!=null || object[10]!=null)
			{
				documentTo.setStatus("Completed");
			}	
		}
		documentlist.add(documentTo);
	}
	}
	
	Iterator<PhdDocumentSubmissionSchedule> itrr=subStudentBo.iterator();
	while (itrr.hasNext()) {
	PhdDocumentSubmissionSchedule object = (PhdDocumentSubmissionSchedule) itrr.next();
	DocumentDetailsSubmissionTO documentDetailsTO=new DocumentDetailsSubmissionTO();
	if(object.getAssignDate()!=null && object.getIsActive()){
		documentDetailsTO.setAssignDate(CommonUtil.ConvertStringToDateFormat(object.getAssignDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	}else{
		documentDetailsTO.setAssignDate(null);
	}
	if(object.getIsReminderMail() && object.getIsActive()){
		documentDetailsTO.setTempChecked1("on");
	}else{
		documentDetailsTO.setTempChecked1(null);
	}if(object.getGuidesFee() && object.getIsActive()){
		documentDetailsTO.setTempChecked2("on");
	}else{
		documentDetailsTO.setTempChecked2(null);
	}if(object.getCanSubmitOnline() && object.getIsActive()){
		documentDetailsTO.setTempChecked3("on");
	}else{
		documentDetailsTO.setTempChecked3(null);
	}if(object.getSubmited() && object.getIsActive()){
		documentDetailsTO.setTempChecked4("on");
	}else{
		documentDetailsTO.setTempChecked4(null);
		
	}if(object.getSubmittedDate()!=null && object.getIsActive()){
		documentDetailsTO.setSubmittedDate(CommonUtil.ConvertStringToDateFormat(object.getSubmittedDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	}else{
		documentDetailsTO.setSubmittedDate(null);
	}if(object.getGuideFeeGenerated()!=null && object.getIsActive()){
		documentDetailsTO.setGuideFeeGenerated(CommonUtil.ConvertStringToDateFormat(object.getGuideFeeGenerated().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	}else{
		documentDetailsTO.setGuideFeeGenerated(null);
	}if(object.getIsActive()){
	documentDetailsTO.setId(object.getId());
	}
  	documentDetailsTO.setDocumentName(object.getDocumentId().getDocumentName());
	documentDetailsTO.setDocumentId(Integer.toString(object.getDocumentId().getId()));
	
	editdocumentList.add(documentDetailsTO);
	}
	documentSubmissionScheduleForm.setDocumentList(editdocumentList);
	documentSubmissionScheduleForm.setStudentDetailsList(documentlist);
}
public DocumentDetailsSubmissionTO convertBOsToTOs(DocumentDetailsBO documentDetailsBOs) {
    DocumentDetailsSubmissionTO documentDetailsTO=new DocumentDetailsSubmissionTO();
    documentDetailsTO.setId(documentDetailsBOs.getId());
    documentDetailsTO.setDocumentName(documentDetailsBOs.getDocumentName());
 	return documentDetailsTO;
}
public List<DocumentDetailsSubmissionTO> convertDocumentToTO(List<DocumentDetailsBO> documentDetailsBOs) {
	List<DocumentDetailsSubmissionTO> documentDetailsTOList=new ArrayList<DocumentDetailsSubmissionTO>();
    Iterator<DocumentDetailsBO> iterator=documentDetailsBOs.iterator();
    while(iterator.hasNext())
    {
    DocumentDetailsBO documentDetailsBO=(DocumentDetailsBO) iterator.next()	;
    DocumentDetailsSubmissionTO documentDetailsTO=new DocumentDetailsSubmissionTO();
    documentDetailsTO.setId(documentDetailsBO.getId());
    documentDetailsTO.setDocumentName(documentDetailsBO.getDocumentName());
    documentDetailsTOList.add(documentDetailsTO);
    }
	return documentDetailsTOList;
}
public List<PhdDocumentSubmissionSchedule> convertFormToBO(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) {

	List<PhdDocumentSubmissionSchedule> documentList=new ArrayList<PhdDocumentSubmissionSchedule>();
	Iterator<PhdDocumentSubmissionScheduleTO> itr=documentSubmissionScheduleForm.getStudentDetailsList().iterator();
	while (itr.hasNext()) {
		PhdDocumentSubmissionScheduleTO details = (PhdDocumentSubmissionScheduleTO) itr.next();
		if(details.getChecked()!=null && !details.getChecked().isEmpty()){
		if(details.getChecked().equalsIgnoreCase("on")){
			Iterator<DocumentDetailsSubmissionTO> itrr=documentSubmissionScheduleForm.getDocumentList().iterator();
			while (itrr.hasNext()) {
				DocumentDetailsSubmissionTO document = (DocumentDetailsSubmissionTO) itrr.next();
		    	if((document.getChecked1()!=null && !document.getChecked1().isEmpty()) ||
				   (document.getChecked2()!=null && !document.getChecked2().isEmpty()) || 
				   (document.getChecked3()!=null && !document.getChecked3().isEmpty()) || 
				   (document.getChecked4()!=null && !document.getChecked4().isEmpty()) ||
				   (document.getAssignDate()!=null && !document.getAssignDate().isEmpty()))
				       {
				PhdDocumentSubmissionSchedule documentBo=new PhdDocumentSubmissionSchedule();
				Student stu=new Student();
				Course course=new Course();
				DocumentDetailsBO doc=new DocumentDetailsBO();
				stu.setId(details.getStudentId());
				course.setId(Integer.parseInt(details.getCourseId()));
				doc.setId(Integer.parseInt(document.getDocumentId()));
				
				documentBo.setStudentId(stu);
				documentBo.setCourseId(course);
				documentBo.setDocumentId(doc);
				if(document.getAssignDate()!=null && !document.getAssignDate().isEmpty()){
				documentBo.setAssignDate(CommonUtil.ConvertStringToDate(document.getAssignDate()));
				}
				if(document.getChecked1()!=null && !document.getChecked1().isEmpty()){
					documentBo.setIsReminderMail(true);
				}else{
					documentBo.setIsReminderMail(false);
				}if(document.getChecked2()!=null && !document.getChecked2().isEmpty()){
					documentBo.setGuidesFee(true);
				}else{
					documentBo.setGuidesFee(false);
				}if(document.getChecked3()!=null && !document.getChecked3().isEmpty()){
					documentBo.setCanSubmitOnline(true);
				}else{
					documentBo.setCanSubmitOnline(false);
				}if(document.getChecked4()!=null && !document.getChecked4().isEmpty()){
					documentBo.setSubmited(true);
				}else{
					documentBo.setSubmited(false);
				}if(document.getSubmittedDate()!=null && !document.getSubmittedDate().isEmpty()){
				documentBo.setSubmittedDate(CommonUtil.ConvertStringToDate(document.getSubmittedDate()));
				}if(document.getGuideFeeGenerated()!=null && !document.getGuideFeeGenerated().isEmpty()){
				documentBo.setGuideFeeGenerated(CommonUtil.ConvertStringToDate(document.getGuideFeeGenerated()));
				}
				documentBo.setCreatedBy(documentSubmissionScheduleForm.getUserId());
				documentBo.setCreatedDate(new Date());
				documentBo.setLastModifiedDate(new Date());
				documentBo.setModifiedBy(documentSubmissionScheduleForm.getUserId());
				documentBo.setIsActive(true);
				if(document.getDocumentId()==null){
					document.setDocumentId(Integer.toString(document.getId()));
					document.setId(0);
				}
				documentBo.setId(document.getId());
				documentList.add(documentBo);
				}
						
			}
		}}
	}
	return documentList;
}
public List<PhdDocumentSubmissionScheduleTO> getDocumentSubmissionNotChange(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) {
	List<PhdDocumentSubmissionScheduleTO> list=new ArrayList<PhdDocumentSubmissionScheduleTO>();
	Iterator<PhdDocumentSubmissionScheduleTO> form=documentSubmissionScheduleForm.getStudentDetailsList().iterator();
	while (form.hasNext()) {
		PhdDocumentSubmissionScheduleTO  phdform= (PhdDocumentSubmissionScheduleTO) form.next();
		PhdDocumentSubmissionScheduleTO phdto=new PhdDocumentSubmissionScheduleTO();
		if(phdform.getRegisterNo()!=null && !phdform.getRegisterNo().isEmpty()){
		phdto.setRegisterNo(phdform.getRegisterNo());
		}if(phdform.getStudentId()>0){
		phdto.setStudentId(phdform.getStudentId());
		}if(phdform.getFeePaidDate()!=null && !phdform.getFeePaidDate().isEmpty()){
		phdto.setFeePaidDate(phdform.getFeePaidDate());
		}if(phdform.getId()>0){
		phdto.setId(phdform.getId());
		}if(phdform.getStudentName()!=null && !phdform.getStudentName().isEmpty()){
		phdto.setStudentName(phdform.getStudentName());
		}if(phdform.getCourseId()!=null && !phdform.getCourseId().isEmpty()){
		phdto.setCourseId(phdform.getCourseId());
		}if(phdform.getCourseName()!=null && !phdform.getCourseName().isEmpty()){
		phdto.setCourseName(phdform.getCourseName());
		}if(phdform.getDocumentId()!=null && !phdform.getDocumentId().isEmpty()){
		phdto.setDocumentId(phdform.getDocumentId());
		}if(phdform.getProgramTypeId()!=null && !phdform.getProgramTypeId().isEmpty()){
		phdto.setProgramTypeId(phdform.getProgramTypeId());
		}if(phdform.getAssignDate()!=null && !phdform.getAssignDate().isEmpty()){
		phdto.setAssignDate(phdform.getAssignDate());
		}if(phdform.getIsReminderMail()!=null && !phdform.getIsReminderMail().isEmpty()){
		phdto.setIsReminderMail(phdform.getIsReminderMail());
		}if(phdform.getGuidesFee()!=null && !phdform.getGuidesFee().isEmpty()){
		phdto.setGuidesFee(phdform.getGuidesFee());
		}if(phdform.getCanSubmitOnline()!=null && !phdform.getCanSubmitOnline().isEmpty()){
		phdto.setCanSubmitOnline(phdform.getCanSubmitOnline());
		}if(phdform.getSubmited()!=null && !phdform.getSubmited().isEmpty()){
		phdto.setSubmited(phdform.getSubmited());
		}if(phdform.getSubmittedDate()!=null && !phdform.getSubmittedDate().isEmpty()){
		phdto.setSubmittedDate(phdform.getSubmittedDate());
		}if(phdform.getGuideFeeGenerated()!=null && !phdform.getGuideFeeGenerated().isEmpty()){
		phdto.setGuideFeeGenerated(phdform.getGuideFeeGenerated());
		}if(phdform.getYear()!=null && !phdform.getYear().isEmpty()){
		phdto.setYear(phdform.getYear());
		}
		if(phdform.getChecked()!=null){
			phdto.setChecked(null);
			phdto.setTempChecked("on");
		}if(phdform.getDocumentName()!=null && !phdform.getDocumentName().isEmpty()){
		phdto.setDocumentName(phdform.getDocumentName());
		}if(phdform.getEditCheck()!=null && !phdform.getEditCheck().isEmpty()){
		phdto.setEditCheck(phdform.getEditCheck());
		}if(phdform.getStatus()!=null && !phdform.getStatus().isEmpty()){
		phdto.setStatus(phdform.getStatus());
		}
		list.add(phdto);
	}
	
	return list;
}
public List<PhdDocumentSubmissionScheduleTO> SetDocumentSubmissionByReg(List<PhdDocumentSubmissionSchedule> subStudentBo,PhdDocumentSubmissionScheduleForm objForm) {
	List<PhdDocumentSubmissionScheduleTO> documentList= new ArrayList<PhdDocumentSubmissionScheduleTO>();
	Iterator<PhdDocumentSubmissionSchedule> itr=subStudentBo.iterator();
	while (itr.hasNext()) {
		PhdDocumentSubmissionSchedule documentBo = (PhdDocumentSubmissionSchedule) itr.next();
		PhdDocumentSubmissionScheduleTO documentTo=new PhdDocumentSubmissionScheduleTO();
		documentTo.setId(documentBo.getId());
		objForm.setStudentName(documentBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
		objForm.setCourseName(documentBo.getCourseId().getName());
		documentTo.setStudentId(documentBo.getStudentId().getId());
		documentTo.setCourseId(Integer.toString(documentBo.getCourseId().getId()));
		documentTo.setDocumentName(documentBo.getDocumentId().getDocumentName());
		documentTo.setDocumentId(Integer.toString(documentBo.getDocumentId().getId()));
		if(documentBo.getAssignDate()!=null && !documentBo.getAssignDate().toString().isEmpty()){
    		documentTo.setAssignDate(CommonUtil.ConvertStringToDateFormat(documentBo.getAssignDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
			documentTo.setDocumentAssiDate(documentBo.getAssignDate());
		}if(documentBo.getIsReminderMail()!=null){
			documentTo.setIsReminderMail(documentBo.getIsReminderMail() ? "Yes":"No");
		}if(documentBo.getGuidesFee()!=null){
		 documentTo.setGuidesFee(documentBo.getGuidesFee() ? "Yes":"No");
		}if(documentBo.getCanSubmitOnline()!=null){
			documentTo.setCanSubmitOnline(documentBo.getCanSubmitOnline() ? "Yes":"No");
		}if(documentBo.getSubmited()!=null){
			documentTo.setTempChecked(documentBo.getSubmited() ? "on": null);
		}if(documentBo.getSubmittedDate()!=null && documentBo.getSubmited()!=null){
    		documentTo.setSubmittedDate(CommonUtil.ConvertStringToDateFormat(documentBo.getSubmittedDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		}if(documentBo.getGuideFeeGenerated()!=null && documentBo.getGuideFeeGenerated()!=null){
    		documentTo.setGuideFeeGenerated(CommonUtil.ConvertStringToDateFormat(documentBo.getGuideFeeGenerated().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		}
		documentList.add(documentTo);
	}
	return documentList;
}
public List<PhdDocumentSubmissionSchedule> documentFormToBO(PhdDocumentSubmissionScheduleForm objForm) {
	List<PhdDocumentSubmissionSchedule> documentList=new ArrayList<PhdDocumentSubmissionSchedule>();
	Iterator<PhdDocumentSubmissionScheduleTO> irr=objForm.getStudentDetailsList().iterator();
	while (irr.hasNext()) {
		PhdDocumentSubmissionScheduleTO documentTO = (PhdDocumentSubmissionScheduleTO) irr.next();
		PhdDocumentSubmissionSchedule documentBo=new PhdDocumentSubmissionSchedule();
		documentBo.setId(documentTO.getId());
		Student stu=new Student();
		Course course=new Course();
		DocumentDetailsBO doc=new DocumentDetailsBO();
		stu.setId(documentTO.getStudentId());
		course.setId(Integer.parseInt(documentTO.getCourseId()));
		doc.setId(Integer.parseInt(documentTO.getDocumentId()));
		documentBo.setStudentId(stu);
		documentBo.setCourseId(course);
		documentBo.setDocumentId(doc);
		if(documentTO.getDocumentAssiDate()!=null){
		documentBo.setAssignDate(documentTO.getDocumentAssiDate());
		}
		if(documentTO.getIsReminderMail().equalsIgnoreCase("Yes") && documentTO.getIsReminderMail()!=null && !documentTO.getIsReminderMail().isEmpty()){
			documentBo.setIsReminderMail(true);
		}else{
			documentBo.setIsReminderMail(false);
		}if(documentTO.getGuidesFee().equalsIgnoreCase("Yes") && documentTO.getGuidesFee()!=null){
			documentBo.setGuidesFee(true);
		}else{
			documentBo.setGuidesFee(false);
		}if(documentTO.getCanSubmitOnline().equalsIgnoreCase("Yes") && documentTO.getCanSubmitOnline()!=null && !documentTO.getCanSubmitOnline().isEmpty()){
			documentBo.setCanSubmitOnline(true);
		}else{
			documentBo.setCanSubmitOnline(false);
		}if(documentTO.getChecked()!=null){
		  documentBo.setSubmited(true);
		}else{
			documentBo.setSubmited(false);
		}if(documentTO.getSubmittedDate()!=null && !documentTO.getSubmittedDate().isEmpty()){
			documentBo.setSubmittedDate(CommonUtil.ConvertStringToDate(documentTO.getSubmittedDate()));
		}if(documentTO.getGuideFeeGenerated()!=null && !documentTO.getGuideFeeGenerated().isEmpty()){
			documentBo.setGuideFeeGenerated(CommonUtil.ConvertStringToDate(documentTO.getGuideFeeGenerated()));
		}
		documentBo.setCreatedBy(objForm.getUserId());
		documentBo.setCreatedDate(new Date());
		documentBo.setLastModifiedDate(new Date());
		documentBo.setModifiedBy(objForm.getUserId());
		documentBo.setIsActive(true);
		documentList.add(documentBo);
	}
	return documentList;
}
public List<PhdDocumentSubmissionScheduleTO> setDataBotoTo(	List<PhdDocumentSubmissionSchedule> documentBo) {
	List<PhdDocumentSubmissionScheduleTO> documentList= new ArrayList<PhdDocumentSubmissionScheduleTO>();
	Iterator<PhdDocumentSubmissionSchedule> itr=documentBo.iterator();
	while (itr.hasNext()) {
		PhdDocumentSubmissionSchedule document = (PhdDocumentSubmissionSchedule) itr.next();
		PhdDocumentSubmissionScheduleTO documentTo=new PhdDocumentSubmissionScheduleTO();
		documentTo.setId(document.getId());
		if(document.getStudentId().getRegisterNo()!=null && !document.getStudentId().getRegisterNo().isEmpty()){
		documentTo.setRegisterNo(document.getStudentId().getRegisterNo());
		}
		documentTo.setStudentName(document.getStudentId().getAdmAppln().getPersonalData().getFirstName());
		documentTo.setCourseName(document.getCourseId().getName());
		documentTo.setStudentId(document.getStudentId().getId());
		documentTo.setCourseId(Integer.toString(document.getCourseId().getId()));
		if(document.getDocumentId().getDocumentName()!=null && !document.getDocumentId().getDocumentName().isEmpty()){
		documentTo.setDocumentName(document.getDocumentId().getDocumentName());
		}
		documentTo.setDocumentId(Integer.toString(document.getDocumentId().getId()));
		if(document.getAssignDate()!=null && !document.getAssignDate().toString().isEmpty()){
    		documentTo.setAssignDate(CommonUtil.ConvertStringToDateFormat(document.getAssignDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
			documentTo.setDocumentAssiDate(document.getAssignDate());
		}
		documentList.add(documentTo);
	}
	return documentList;
}

}
