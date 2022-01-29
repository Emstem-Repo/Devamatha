package com.kp.cms.actions.employee;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.dom4j.tree.BackedList;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.employee.EmployeeInfoEditNewForm;
import com.kp.cms.handlers.employee.EmployeeInfoEditHandler;
import com.kp.cms.handlers.employee.EmployeeInfoEditNewHandler;
import com.kp.cms.to.admin.EmpDependentsTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.PfGratuityNomineesTO;
import com.kp.cms.to.auditorium.EmpPaperPresentationTO;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmpAcademicQualificationTO;
import com.kp.cms.to.employee.EmpAwardTO;
import com.kp.cms.to.employee.EmpBooksPublishedTO;
import com.kp.cms.to.employee.EmpDutiesPerformedTO;
import com.kp.cms.to.employee.EmpFieldResearchTO;
import com.kp.cms.to.employee.EmpGuideShipDetailsTO;
import com.kp.cms.to.employee.EmpInterestsTO;
import com.kp.cms.to.employee.EmpMemberShipAcademicBodyTO;
import com.kp.cms.to.employee.EmpProfessionalDevelopmentTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.EmpResearchPublicationTO;
import com.kp.cms.to.employee.EmpResearchTO;
import com.kp.cms.to.employee.EmpSeminarAttendedDetailsTO;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeInfoEditNewAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(EmployeeInfoEditNewAction.class);
	private static final String MESSAGE_KEY = "messages";
	private static final String PHOTOBYTES = "PhotoBytes";
	private static final String TO_DATEFORMAT = "MM/dd/yyyy";
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	
	public ActionForward getSearchedEmployee(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EmployeeInfoEditNewForm stForm = (EmployeeInfoEditNewForm) form;
		HttpSession session=request.getSession(false);
		String eid1=(String)session.getAttribute("employeeId");
		String rid1=(String)session.getAttribute("rid");
		String guestId = (String)session.getAttribute("guestId");
		String userId = (String)session.getAttribute("userId");
		Integer rid2=new Integer(rid1);
		if(eid1 != null){
	    Integer eid=new Integer(eid1);
		}
	    int uId=0;
	    if(userId!=null)
	    	uId = Integer.parseInt(userId);
	    Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			log.error("properties ",e);
			throw new IOException(e);
		}
		String fileName1=prop.getProperty(CMSConstants.ROLE_ID_PRINCIPLE);
		String fileName2=prop.getProperty(CMSConstants.ROLE_ID_ADMIN);
		String fileName3=prop.getProperty(CMSConstants.ROLE_ID_TEST);
		int principleid=Integer.parseInt(fileName1);
		int adminid=Integer.parseInt(fileName2);
		int testid=Integer.parseInt(fileName3);
		ActionMessages errors = stForm.validate(mapping, request);
	try {
		
		if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);
			}
		EmployeeInfoEditNewHandler handler = EmployeeInfoEditNewHandler.getInstance();
		List<UserInfoTO> userToList = new ArrayList<UserInfoTO>();
			//List<EmployeeTO> employeeToList = handler.getSearchedEmployeeNew(eid,rid2,stForm,principleid,adminid,testid);
		if(uId == 3 || rid2 == 17 ){
			 userToList = handler.getSearchedUsers( stForm.getSelectedEmployeeId(),guestId);
		}
		if(eid1 != null && uId != 3 && rid2 != 17){
			 userToList = handler.getSearchedUsers(eid1, guestId);
		}
		if(guestId != null && uId != 3 && rid2 != 17){
			 userToList = handler.getSearchedUsers(eid1 ,guestId);
		}
			if (userToList == null || userToList.isEmpty()) {
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				//message = new ActionMessage(
						//CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				//messages.add(EmployeeInfoEditNewAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_EDITLISTPAGE_NEW);
			}
			stForm.setUserList(userToList);
		} catch (ApplicationException e) {
			log.error("error in getSearchedStudents...", e);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
			message = new ActionMessage(
					CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
			messages.add(EmployeeInfoEditNewAction.MESSAGE_KEY, message);
			saveMessages(request, messages);

			return mapping
					.findForward(CMSConstants.EMPLOYEE_EDITLISTPAGE_NEW);

		} catch (Exception e) {
			log.error("error in getSearchedStudents...", e);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
			message = new ActionMessage(
					CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
			messages.add(EmployeeInfoEditNewAction.MESSAGE_KEY, message);
			saveMessages(request, messages);

			return mapping
					.findForward(CMSConstants.EMPLOYEE_EDITLISTPAGE_NEW);

		}
		log.info("exit getSearchedStudents..");
		return mapping.findForward(CMSConstants.EMPLOYEE_EDITLISTPAGE_NEW);
	}
	
	public ActionForward loadEmployeeInfoNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
 		EmployeeInfoEditNewForm objform = (EmployeeInfoEditNewForm) form;
		
		ActionMessages messages=new ActionMessages();
		HttpSession session = request.getSession(false);
		try
		{
		boolean flag=false;
		if( StringUtils.isNotEmpty(objform.getSelectedEmployeeId()))
		{
		setUserId(request,objform);
		initialiseAcademicQualification(objform);
		flag=EmployeeInfoEditNewHandler.getInstance().getApplicantDetails(objform);
		objform.setEmpTypeIdOld(objform.getEmpTypeId());
		
		
		}
		String rid1=(String)session.getAttribute("rid");
		//Properties prop = new Properties();
		//String fileName2=prop.getProperty(CMSConstants.ROLE_ID_ADMIN);
		//String fileName3=prop.getProperty(CMSConstants.ROLE_ID_TEST);
		//int adminid=Integer.parseInt(fileName2);
		//int testid=Integer.parseInt(fileName3);
		if(flag){
			if(Integer.parseInt(rid1)==129 || Integer.parseInt(rid1)==17){
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
			}else {
				return mapping.findForward(CMSConstants.INDIVIDUAL_EMPLOYEE_INFO_EDIT_NEW);
			}
		}
		else
		{
			messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EMPLOYEE_NOT_VALID));
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);
		}
	}catch (Exception exception) {
			if (exception instanceof ApplicationException) {
				String msg = super.handleApplicationException(exception);
				objform.setErrorMessage(msg);
				objform.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else
			throw exception;
		}
		
	}	
	
	
	private void initialiseAcademicQualification(EmployeeInfoEditNewForm objform) {
		List<EmpAcademicQualificationTO> list= new ArrayList<EmpAcademicQualificationTO>();
		EmpAcademicQualificationTO empAcademicQualificationTO=new EmpAcademicQualificationTO();
		empAcademicQualificationTO.setCourseName("");
		empAcademicQualificationTO.setGrade("");
		empAcademicQualificationTO.setUniversityName("");
		empAcademicQualificationTO.setYear("");
		objform.setEmpQualificationListSize(String.valueOf(list.size()));
		list.add(empAcademicQualificationTO);
		objform.getEmployeeInfoEditNewTO().setEmpqualification(list);
		
		List<EmpInterestsTO> list1=new ArrayList<EmpInterestsTO>();
		EmpInterestsTO empInterestsTO=new EmpInterestsTO();
		empInterestsTO.setTopic("");
		objform.setEmpIntrestListSize(String.valueOf(list.size()));
		list1.add(empInterestsTO);
		objform.getEmployeeInfoEditNewTO().setInterest(list1);
		
		
		List<EmpFieldResearchTO> list2= new ArrayList<EmpFieldResearchTO>();
		EmpFieldResearchTO empFieldResearchTO=new EmpFieldResearchTO();
		empFieldResearchTO.setTopic("");
		objform.setEmpResearchListSize(String.valueOf(list.size()));
		list2.add(empFieldResearchTO);
		objform.getEmployeeInfoEditNewTO().setResearch(list2);
		
		List<EmpGuideShipDetailsTO> list3= new ArrayList<EmpGuideShipDetailsTO>();
		EmpGuideShipDetailsTO empGuideShipDetailsTO=new EmpGuideShipDetailsTO();
		empGuideShipDetailsTO.setAwarded("");
		empGuideShipDetailsTO.setPhdScholarName("");
		empGuideShipDetailsTO.setRegistrationYear("");
		empGuideShipDetailsTO.setThesisName("");
		empGuideShipDetailsTO.setYear("");
		objform.setEmpGuidshipListSize(String.valueOf(list.size()));
		list3.add(empGuideShipDetailsTO);
		objform.getEmployeeInfoEditNewTO().setGuideship(list3);
		
		List<EmpDutiesPerformedTO> list4= new ArrayList<EmpDutiesPerformedTO>();
		EmpDutiesPerformedTO empDutiesPerformedTO=new EmpDutiesPerformedTO();
		empDutiesPerformedTO.setPositionName("");
		empDutiesPerformedTO.setFromDate("");
		empDutiesPerformedTO.setToDate("");
		objform.setEmpDutiesPerformedListSize(String.valueOf(list.size()));
		list4.add(empDutiesPerformedTO);
		objform.getEmployeeInfoEditNewTO().setDuties(list4);
		
		List<EmpResearchTO> list5= new ArrayList<EmpResearchTO>();
		EmpResearchTO empResearchTO=new EmpResearchTO();
		empResearchTO.setAmount("");
		empResearchTO.setFindingAgencyName("");
		empResearchTO.setFromDate1("");
		empResearchTO.setProjectName("");
		empResearchTO.setTitle("");
		empResearchTO.setToDate1("");
		objform.setEmpResearchProjectListSize(String.valueOf(list.size()));
		list5.add(empResearchTO);
		objform.getEmployeeInfoEditNewTO().setResearchProject(list5);
		
		List<EmpResearchPublicationTO> list6= new ArrayList<EmpResearchPublicationTO>();
		EmpResearchPublicationTO empResearchPublicationTO=new EmpResearchPublicationTO();
		empResearchPublicationTO.setISBNISSNNo("");
		empResearchPublicationTO.setJournalName("");
		empResearchPublicationTO.setPaperTitle("");
		empResearchPublicationTO.setUGCNonUGC("");
		empResearchPublicationTO.setYear("");
		objform.setEmpResearchPublicationListSize(String.valueOf(list.size()));
		list6.add(empResearchPublicationTO);
		objform.getEmployeeInfoEditNewTO().setResearchPubliction(list6);
		
		List<EmpPaperPresentationTO> list7= new ArrayList<EmpPaperPresentationTO>();
		EmpPaperPresentationTO empPaperPresentationTO=new EmpPaperPresentationTO();
		empPaperPresentationTO.setDate1("");
		empPaperPresentationTO.setOrganisation("");
		empPaperPresentationTO.setPaperName("");
		empPaperPresentationTO.setRegional("");
		empPaperPresentationTO.setSeminarName("");
		objform.setEmpPaperPrsentationListSize(String.valueOf(list.size()));
		list7.add(empPaperPresentationTO);
		objform.getEmployeeInfoEditNewTO().setEmpPaper(list7);
		
		
		List<EmpBooksPublishedTO> list8= new ArrayList<EmpBooksPublishedTO>();
		EmpBooksPublishedTO empBooksPublishedTO=new EmpBooksPublishedTO();
		empBooksPublishedTO.setContibutionName("");
		empBooksPublishedTO.setISBNISSN("");
		empBooksPublishedTO.setPublisherName("");
		empBooksPublishedTO.setTitleName("");
		empBooksPublishedTO.setYear("");
		objform.setEmpBooksPublishListSize(String.valueOf(list.size()));
		list8.add(empBooksPublishedTO);
		objform.getEmployeeInfoEditNewTO().setEmpBooks(list8);
		
		List<EmpSeminarAttendedDetailsTO> list9= new ArrayList<EmpSeminarAttendedDetailsTO>();
		EmpSeminarAttendedDetailsTO empSeminarAttendedDetailsTO=new EmpSeminarAttendedDetailsTO();
		empSeminarAttendedDetailsTO.setFromDate2("");
		empSeminarAttendedDetailsTO.setOrganisation("");
		empSeminarAttendedDetailsTO.setInterRegional("");
		empSeminarAttendedDetailsTO.setSeminar("");
		empSeminarAttendedDetailsTO.setSeminarName("");
		empSeminarAttendedDetailsTO.setParticipation("");
		empSeminarAttendedDetailsTO.setToDate2("");
		objform.setEmpSeminarattendedListSize(String.valueOf(list.size()));
		list9.add(empSeminarAttendedDetailsTO);
		objform.getEmployeeInfoEditNewTO().setEmpSeminarAttendedDetails(list9);
		
		List<EmpProfessionalDevelopmentTO> list10= new ArrayList<EmpProfessionalDevelopmentTO>();
		EmpProfessionalDevelopmentTO empProfessionalDevelopmentTO=new EmpProfessionalDevelopmentTO();
		empProfessionalDevelopmentTO.setFromDate3("");
		empProfessionalDevelopmentTO.setName("");
		empProfessionalDevelopmentTO.setOrganisation("");
		empProfessionalDevelopmentTO.setToDate3("");
		objform.setEmpProfessionalDevelopmentListSize(String.valueOf(list.size()));
		list10.add(empProfessionalDevelopmentTO);
		objform.getEmployeeInfoEditNewTO().setEmpPersonaldevelopment(list10);
		
		List<EmpAwardTO> list11= new ArrayList<EmpAwardTO>();
		EmpAwardTO empAwardTO=new EmpAwardTO();
		empAwardTO.setActivityname("");
		empAwardTO.setAwardbodyName("");
		empAwardTO.setRecognitionName("");
		empAwardTO.setYear("");
		objform.setEmpAwardListSize(String.valueOf(list.size()));
		list11.add(empAwardTO);
		objform.getEmployeeInfoEditNewTO().setEmpAward(list11);
		
		List<EmpMemberShipAcademicBodyTO> list12= new ArrayList<EmpMemberShipAcademicBodyTO>();
		EmpMemberShipAcademicBodyTO empMemberShipAcademicBodyTO=new EmpMemberShipAcademicBodyTO();
		empMemberShipAcademicBodyTO.setFromDate4("");
		empMemberShipAcademicBodyTO.setName("");
		empMemberShipAcademicBodyTO.setTodate4("");
		objform.setEmpMembershipAcademicListSize(String.valueOf(list.size()));
		list12.add(empMemberShipAcademicBodyTO);
		objform.getEmployeeInfoEditNewTO().setEmpMemberShip(list12);
	}

	public ActionForward addQualifications(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpqualification()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("QualificationsAddMore")) {
				List<EmpAcademicQualificationTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpqualification();
				EmpAcademicQualificationTO empAcademicQualificationTO=new EmpAcademicQualificationTO();
				empAcademicQualificationTO.setCourseName("");
				empAcademicQualificationTO.setGrade("");
				empAcademicQualificationTO.setUniversityName("");
				empAcademicQualificationTO.setYear("");
				employeeInfoEditForm.setEmpQualificationListSize(String.valueOf(list.size()));
				list.add(empAcademicQualificationTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("courseName"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeQualificationRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpAcademicQualificationTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpqualification()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpqualification().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpqualification();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpQualificationListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpQualificationListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}	
	
	public ActionForward addInterests(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getInterest()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("InterestaddMore")) {
				List<EmpInterestsTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getInterest();
				EmpInterestsTO empInterestsTO=new EmpInterestsTO();
				empInterestsTO.setTopic("");
				employeeInfoEditForm.setEmpIntrestListSize(String.valueOf(list.size()));
				list.add(empInterestsTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("topic"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeInterests(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpInterestsTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getInterest()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getInterest().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getInterest();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpIntrestListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpIntrestListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}	
	
	public ActionForward addFieldResearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearch()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("ReserachaddMore")) {
				List<EmpFieldResearchTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearch();
				EmpFieldResearchTO empFieldResearchTO=new EmpFieldResearchTO();
				empFieldResearchTO.setTopic("");
				employeeInfoEditForm.setEmpResearchListSize(String.valueOf(list.size()));
				list.add(empFieldResearchTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("topic"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeFieldResearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpFieldResearchTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearch()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearch().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearch();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpResearchListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpResearchListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}	
	
	public ActionForward addGuideShip(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getGuideship()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("guideshipAddMore")) {
				List<EmpGuideShipDetailsTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getGuideship();
				EmpGuideShipDetailsTO empGuideShipDetailsTO=new EmpGuideShipDetailsTO();
				empGuideShipDetailsTO.setAwarded("");
				empGuideShipDetailsTO.setPhdScholarName("");
				empGuideShipDetailsTO.setRegistrationYear("");
				empGuideShipDetailsTO.setThesisName("");
				empGuideShipDetailsTO.setYear("");
				employeeInfoEditForm.setEmpGuidshipListSize(String.valueOf(list.size()));
				list.add(empGuideShipDetailsTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("phdScholarName"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeGuideShip(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpGuideShipDetailsTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getGuideship()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getGuideship().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getGuideship();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpGuidshipListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpGuidshipListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}		
	
	public ActionForward addDuties(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getDuties()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("dutiesAddMore")) {
				List<EmpDutiesPerformedTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getDuties();
				EmpDutiesPerformedTO empDutiesPerformedTO=new EmpDutiesPerformedTO();
				empDutiesPerformedTO.setPositionName("");
				empDutiesPerformedTO.setFromDate("");
				empDutiesPerformedTO.setToDate("");
				employeeInfoEditForm.setEmpDutiesPerformedListSize(String.valueOf(list.size()));
				list.add(empDutiesPerformedTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("positionName"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeDuties(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpDutiesPerformedTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getDuties()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getDuties().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getDuties();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpDutiesPerformedListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpDutiesPerformedListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}
	
	public ActionForward addResearchProject(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearchProject()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("projectAddMore")) {
				List<EmpResearchTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearchProject();
				EmpResearchTO empResearchTO=new EmpResearchTO();
				empResearchTO.setAmount("");
				empResearchTO.setFindingAgencyName("");
				empResearchTO.setFromDate1("");
				empResearchTO.setProjectName("");
				empResearchTO.setTitle("");
				empResearchTO.setToDate1("");
				employeeInfoEditForm.setEmpResearchProjectListSize(String.valueOf(list.size()));
				list.add(empResearchTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("title"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeResearchProject(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpResearchTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearchProject()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearchProject().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearchProject();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpResearchProjectListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpResearchProjectListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}	
	
	
	public ActionForward addResearchPub(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearchPubliction()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("ResearchPub")) {
				List<EmpResearchPublicationTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearchPubliction();
				EmpResearchPublicationTO empResearchPublicationTO=new EmpResearchPublicationTO();
				empResearchPublicationTO.setISBNISSNNo("");
				empResearchPublicationTO.setJournalName("");
				empResearchPublicationTO.setPaperTitle("");
				empResearchPublicationTO.setUGCNonUGC("");
				empResearchPublicationTO.setYear("");
				
				employeeInfoEditForm.setEmpResearchPublicationListSize(String.valueOf(list.size()));
				list.add(empResearchPublicationTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("paperTitle"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeResearchPub(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpResearchPublicationTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearchPubliction()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearchPubliction().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearchPubliction();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpResearchPublicationListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpResearchPublicationListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}		
	
	public ActionForward addBooks(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpBooks()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("books")) {
				List<EmpBooksPublishedTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpBooks();
				EmpBooksPublishedTO empBooksPublishedTO=new EmpBooksPublishedTO();
				empBooksPublishedTO.setContibutionName("");
				empBooksPublishedTO.setISBNISSN("");
				empBooksPublishedTO.setPublisherName("");
				empBooksPublishedTO.setTitleName("");
				empBooksPublishedTO.setYear("");
				
				employeeInfoEditForm.setEmpBooksPublishListSize(String.valueOf(list.size()));
				list.add(empBooksPublishedTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("titleName"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeBooks(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpBooksPublishedTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpBooks()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getResearchPubliction().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpBooks();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpBooksPublishListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpBooksPublishListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}	
	
	
	
	public ActionForward addpaper(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpPaper()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("addpaper")) {
				List<EmpPaperPresentationTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpPaper();
				EmpPaperPresentationTO empPaperPresentationTO=new EmpPaperPresentationTO();
				empPaperPresentationTO.setDate1("");
				empPaperPresentationTO.setOrganisation("");
				empPaperPresentationTO.setPaperName("");
				empPaperPresentationTO.setRegional("");
				empPaperPresentationTO.setSeminarName("");
				
				employeeInfoEditForm.setEmpPaperPrsentationListSize(String.valueOf(list.size()));
				list.add(empPaperPresentationTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("paperName"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removepaper(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpPaperPresentationTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpPaper()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpPaper().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpPaper();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpPaperPrsentationListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpPaperPrsentationListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}	
	
	public ActionForward addSeminar(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpSeminarAttendedDetails()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("addsem")) {
				List<EmpSeminarAttendedDetailsTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpSeminarAttendedDetails();
				EmpSeminarAttendedDetailsTO empSeminarAttendedDetailsTO=new EmpSeminarAttendedDetailsTO();
				empSeminarAttendedDetailsTO.setFromDate2("");
				empSeminarAttendedDetailsTO.setOrganisation("");
				empSeminarAttendedDetailsTO.setInterRegional("");
				empSeminarAttendedDetailsTO.setSeminar("");
				empSeminarAttendedDetailsTO.setSeminarName("");
				empSeminarAttendedDetailsTO.setParticipation("");
				empSeminarAttendedDetailsTO.setToDate2("");
				
				employeeInfoEditForm.setEmpSeminarattendedListSize(String.valueOf(list.size()));
				list.add(empSeminarAttendedDetailsTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("seminar"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeSeminar(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpSeminarAttendedDetailsTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpSeminarAttendedDetails()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpSeminarAttendedDetails().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpSeminarAttendedDetails();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpSeminarattendedListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpSeminarattendedListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}	
	
	
	public ActionForward addProDev(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpPersonaldevelopment()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("addprodev")) {
				List<EmpProfessionalDevelopmentTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpPersonaldevelopment();
				EmpProfessionalDevelopmentTO empProfessionalDevelopmentTO=new EmpProfessionalDevelopmentTO();
				empProfessionalDevelopmentTO.setFromDate3("");
				empProfessionalDevelopmentTO.setName("");
				empProfessionalDevelopmentTO.setOrganisation("");
				empProfessionalDevelopmentTO.setToDate3("");
				
				employeeInfoEditForm.setEmpProfessionalDevelopmentListSize(String.valueOf(list.size()));
				list.add(empProfessionalDevelopmentTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("name"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeProDev(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpProfessionalDevelopmentTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpPersonaldevelopment()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpPersonaldevelopment().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpPersonaldevelopment();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpProfessionalDevelopmentListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpProfessionalDevelopmentListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}		
	
	public ActionForward addAward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpAward()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("addAwardrow")) {
				List<EmpAwardTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpAward();
				EmpAwardTO empAwardTO=new EmpAwardTO();
				empAwardTO.setActivityname("");
				empAwardTO.setAwardbodyName("");
				empAwardTO.setRecognitionName("");
				empAwardTO.setYear("");
				
				
				employeeInfoEditForm.setEmpAwardListSize(String.valueOf(list.size()));
				list.add(empAwardTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("awardName"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeAward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpAwardTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpAward()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpAward().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpAward();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpAwardListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpAwardListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}		
	
	
	public ActionForward addMemField(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpMemberShip()!=null)
		if(employeeInfoEditForm.getMode()!=null){
			if (employeeInfoEditForm.getMode().equalsIgnoreCase("addMembership")) {
				List<EmpMemberShipAcademicBodyTO> list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpMemberShip();
				EmpMemberShipAcademicBodyTO empMemberShipAcademicBodyTO=new EmpMemberShipAcademicBodyTO();
				empMemberShipAcademicBodyTO.setFromDate4("");
				empMemberShipAcademicBodyTO.setName("");
				empMemberShipAcademicBodyTO.setTodate4("");
				employeeInfoEditForm.setEmpMembershipAcademicListSize(String.valueOf(list.size()));
				list.add(empMemberShipAcademicBodyTO);
				employeeInfoEditForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoEditForm.setFocusValue("name"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
}	
	
	public ActionForward removeMemField(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		List<EmpMemberShipAcademicBodyTO> list=null;
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpMemberShip()!=null)
		if(employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpMemberShip().size()>0){
				list=employeeInfoEditForm.getEmployeeInfoEditNewTO().getEmpMemberShip();
				list.remove(list.size()-1);
				employeeInfoEditForm.setEmpMembershipAcademicListSize(String.valueOf(list.size()));
		}
		employeeInfoEditForm.setEmpMembershipAcademicListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
	}
	
	public ActionForward saveEmployeeDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		EmployeeInfoEditNewForm employeeInfoEditForm=(EmployeeInfoEditNewForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=employeeInfoEditForm.validate(mapping, request);
		boolean flag=false;
		if(errors.isEmpty()){
			try {
				flag=EmployeeInfoEditNewHandler.getInstance().saveEmp(employeeInfoEditForm);
				if(flag){
					ActionMessage message=new ActionMessage(CMSConstants.EMP_INFO_SUBMIT_CONFIRM_NEW);
					messages.add(CMSConstants.MESSAGES,message);
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.EMP_INFO_SUBMIT_CONFIRM_NEW);
				}else{
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_INFO_ERRORSUBMIT_CONFIRM));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
				}
			} catch (Exception exception) {
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			//request.setAttribute("EMP_IMAGE", "images/EmployeePhotos/E"+employeeInfoEditForm.getSelectedEmployeeId()+".jpg?"+new Date());
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_NEW);
		}
	}
}
