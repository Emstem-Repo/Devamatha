package com.kp.cms.actions.phd;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admission.AdmissionFormAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInfoFormNew;
import com.kp.cms.forms.phd.PhdEmployeeForms;
import com.kp.cms.handlers.phd.PhdEmployeeHandelers;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.phd.PhdEmployeeTo;
import com.kp.cms.utilities.CommonUtil;

public class PhdEmployeeAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(PhdEmployeeAction.class);
	
	private static final String MESSAGE_KEY = "messages";
	private static final String PHOTOBYTES = "PhotoBytes";
	private static final String TO_DATEFORMAT = "MM/dd/yyyy";
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	/**
	 * @param dateOfBirth
	 * @return
	 */
	private boolean validatefutureDate(String dateOfBirth) {
		log.info("enter validatefutureDate..");
		String formattedString = CommonUtil.ConvertStringToDateFormat(
				dateOfBirth, PhdEmployeeAction.FROM_DATEFORMAT,
				PhdEmployeeAction.TO_DATEFORMAT);
		Date date = new Date(formattedString);
		Date curdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date origdate = cal.getTime();
		log.info("exit validatefutureDate..");
		return !(date.compareTo(origdate) == 1);

	}
	public ActionForward initPhdEmployeesearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    throws Exception{
	log.info("Entering into the initPhdEmployee in PhdEmployeeAction");
	PhdEmployeeForms objform=(PhdEmployeeForms) form;
	objform.setPhdEmployeeDetails(null);
	cleanupEditSessionData(request);
	cleanUpPageData(objform,request);
	objform.clear1();
	setUserId(request,objform);
	PhdEmployeeHandelers.getInstance().getInitialData(objform);
	initializeQualificationAndEducation(objform);
	log.info("Exit from the initEmployeeInfo in EmployeeInfoAction");
	return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM_SEARCH);
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPhdEmployee(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	      throws Exception{
		log.info("Entering into the initPhdEmployee in PhdEmployeeAction");
		PhdEmployeeForms objform=(PhdEmployeeForms) form;
		objform.setPhdEmployeeDetails(null);
		cleanupEditSessionData(request);
		cleanUpPageData(objform,request);
		objform.clear1();
		setDataToForm(objform);
		setUserId(request,objform);
		objform.setNationalityId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		objform.setCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		initializeQualificationAndEducation(objform);
		log.info("Exit from the initEmployeeInfo in EmployeeInfoAction");
		return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
	}


	private void initializeQualificationAndEducation(PhdEmployeeForms objform) {
		
		EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
		
		List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
		empPreviousOrgTo.settNameOfUniversity("");
		empPreviousOrgTo.settNameOfInstitution("");
		empPreviousOrgTo.settSubject("");
		empPreviousOrgTo.settYearsOfExpe("");
		objform.setTeachingExpLength(String.valueOf(teachingList.size()));
		teachingList.add(empPreviousOrgTo);
		objform.setTeachingExperience(teachingList);
		
		List<EmpPreviousOrgTo> researchList=new ArrayList<EmpPreviousOrgTo>();
		empPreviousOrgTo.setrNameOfTheUniversity("");
		empPreviousOrgTo.setrNameOfInstitution("");
		empPreviousOrgTo.setrSubject("");
		empPreviousOrgTo.setrYearOfExper("");
		objform.setResearchlength(String.valueOf(researchList.size()));
		researchList.add(empPreviousOrgTo);
		objform.setResearchExperience(researchList);
		
		List<EmpPreviousOrgTo> publicationList=new ArrayList<EmpPreviousOrgTo>();
		empPreviousOrgTo.setpNameOfTitles("");
		empPreviousOrgTo.setpJournalPubli("");
		empPreviousOrgTo.setPyear("");
		objform.setPublicationLength(String.valueOf(publicationList.size()));
		publicationList.add(empPreviousOrgTo);
		objform.setPublicationExperience(publicationList);
		
	}


	/**
	 * @param objform
	 * @param request 
	 * @throws Exception
	 */
	private void setDataToForm(PhdEmployeeForms objform) throws Exception{
		PhdEmployeeHandelers.getInstance().getInitialData(objform);
		if(objform.getPhdEmployeequalificationFixedTo()!=null){
			Iterator<EmpQualificationLevelTo> iterator=objform.getPhdEmployeequalificationFixedTo().iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo phdqualificationTo=iterator.next();
				if(phdqualificationTo!=null){
					phdqualificationTo.setDegree("");
					phdqualificationTo.setNameOfUniversity("");
					phdqualificationTo.setQstate("");
					phdqualificationTo.setPercentage("");
					phdqualificationTo.setYearOfComp("");
					phdqualificationTo.setAttempts("");
				}
			}
		}
	}


	/**
	 * @param request
	 */
	private void cleanupEditSessionData(HttpServletRequest request) {
		log.info("enter cleanupEditSessionData...");
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		} else {
			if (session.getAttribute(PhdEmployeeAction.PHOTOBYTES) != null)
				session.removeAttribute(PhdEmployeeAction.PHOTOBYTES);
		}
	}


	/**
	 * @param objform
	 * @param request 
	 */
	private void cleanUpPageData(PhdEmployeeForms objform, HttpServletRequest request) {
		if(objform!=null){
			HttpSession sessions=request.getSession();
			sessions.setAttribute("phdEmplUpdate", null);
			objform.setId(0);
			objform.setName(null);
			objform.setGender(null);
			objform.setEmpanelmentNo(null);
			objform.setDateOfBirth(null);
			objform.setPlaceOfBirth(null);
			objform.setNationalityId(null);
			objform.setReligionId(null);
			objform.setBloodGroup(null);
			objform.setDomicialStatus(null);
			objform.setPassPortNo(null);
			objform.setPanNo(null);
			objform.setEmail(null);
			objform.setDateOfAward(null);
			objform.setQualificationId(null);
			objform.setNoOfresearch(null);
			objform.setNoOfBookAuthored(null);
			objform.setNameAddress(null);
			objform.setDepartmentId(null);
			objform.setDesiginitionId(null);
			objform.setYearOfExp(null);
			objform.setPermanentAddress(null);
			objform.setpAddressPhonNo(null);
			objform.setContactAddress(null);
			objform.setcAddressPhonNo(null);
			objform.setBankAccNo(null);
			objform.setBankName(null);
			objform.setBankBranch(null);
			objform.setqExamName(null);
			objform.setDegree(null);
			objform.setNameOfUniversity(null);
			objform.setQstate(null);
			objform.setPercentage(null);
			objform.setYearOfComp(null);
			objform.setAttempts(null);
			objform.settNameOfInstitution(null);
			objform.settNameOfUniversity(null);
			objform.settSubject(null);
			objform.settYearsOfExpe(null);
			objform.setrNameOfInstitution(null);
			objform.setrNameOfTheUniversity(null);
			objform.setrSubject(null);
			objform.setrYearOfExper(null);
			objform.setpNameOfTitles(null);
			objform.setpJournalPubli(null);
			objform.setPyear(null);
			objform.setSubjectGuideShip(null);
			objform.setPhotoBytes(null);
			objform.setEmpImageId(null);
			objform.setNoMphilScolars(0);
			objform.setNoPhdScolars(0);
			objform.setNoPhdScolarOutside(0);
			objform.setPhdEmployeequalificationTos(null);
		}
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetTeachingExperienceInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpInfoAction");
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		HttpSession sessions=request.getSession();
		if(sessions.getAttribute("phdEmplUpdate")!=null && !sessions.getAttribute("phdEmplUpdate").toString().isEmpty()){
		String test=sessions.getAttribute("phdEmplUpdate").toString();
		if(test!=null && !test.isEmpty()){
			request.setAttribute("phdEmployee", "edit");
		}
	}
		if(objform.getTeachingExperience()!=null){
		if(objform.getMode()!=null){
			if (objform.getMode().equalsIgnoreCase("ExpAddMore")) {
				List<EmpPreviousOrgTo> list=objform.getTeachingExperience();
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				empPreviousOrgTo.settNameOfInstitution("");
				empPreviousOrgTo.settNameOfUniversity("");
				empPreviousOrgTo.settSubject("");
				empPreviousOrgTo.settYearsOfExpe("");
				objform.setTeachingExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				objform.setMode(null);
				String size=String.valueOf(list.size()-1);
				objform.setFocusValue("nameofinstitute_"+size);
			}
			}
		}
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
   }
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeTeachingExperienceInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		HttpSession sessions=request.getSession();
		if(sessions.getAttribute("phdEmplUpdate")!=null && !sessions.getAttribute("phdEmplUpdate").toString().isEmpty()){
		String test=sessions.getAttribute("phdEmplUpdate").toString();
		if(test!=null && !test.isEmpty()){
			request.setAttribute("phdEmployee", "edit");
		}
		}
		List<EmpPreviousOrgTo> list=null;
		if(objform.getTeachingExperience()!=null)
		if(objform.getTeachingExperience().size()>0){
				list=objform.getTeachingExperience();
				list.remove(list.size()-1);
				objform.setTeachingExpLength(String.valueOf(list.size()));
				objform.setTeaching("true");
		}
		objform.setTeachingExpLength(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of PHD_INFO_APPLICATIONFORM");
		return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetResearchExperienceInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpInfoAction");
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		HttpSession sessions=request.getSession();
		if(sessions.getAttribute("phdEmplUpdate")!=null && !sessions.getAttribute("phdEmplUpdate").toString().isEmpty()){
		String test=sessions.getAttribute("phdEmplUpdate").toString();
		if(test!=null && !test.isEmpty()){
			request.setAttribute("phdEmployee", "edit");
		}}
		if(objform.getResearchExperience()!=null){
		if(objform.getMode()!=null){
			if (objform.getMode().equalsIgnoreCase("ExpAddMore")) {
				List<EmpPreviousOrgTo> list=objform.getResearchExperience();
				EmpPreviousOrgTo phdResearchTo=new EmpPreviousOrgTo();
				phdResearchTo.setrNameOfInstitution("");
				phdResearchTo.setrNameOfTheUniversity("");
				phdResearchTo.setrSubject("");
				phdResearchTo.setrYearOfExper("");
				objform.setResearchlength(String.valueOf(list.size()));
				list.add(phdResearchTo);
				objform.setMode(null);
				String size=String.valueOf(list.size()-1);
				objform.setFocusValue("rnameofinstitute_"+size);
			}
			}
		}
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
   }
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeResearchExperienceInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		HttpSession sessions=request.getSession();
		if(sessions.getAttribute("phdEmplUpdate")!=null && !sessions.getAttribute("phdEmplUpdate").toString().isEmpty()){
		String test=sessions.getAttribute("phdEmplUpdate").toString();
		if(test!=null && !test.isEmpty()){
			request.setAttribute("phdEmployee", "edit");
		}}
		List<EmpPreviousOrgTo> list=null;
		if(objform.getResearchExperience()!=null)
		if(objform.getResearchExperience().size()>0){
				list=objform.getResearchExperience();
				list.remove(list.size()-1);
				objform.setResearchlength(String.valueOf(list.size()));
		}
		objform.setResearchlength(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of PHD_INFO_APPLICATIONFORM");
		return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetResearchpublication(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpInfoAction");
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		HttpSession sessions=request.getSession();
		if(sessions.getAttribute("phdEmplUpdate")!=null && !sessions.getAttribute("phdEmplUpdate").toString().isEmpty()){
		String test=sessions.getAttribute("phdEmplUpdate").toString();
		if(test!=null && !test.isEmpty()){
			request.setAttribute("phdEmployee", "edit");
		}}
		if(objform.getPublicationExperience()!=null){
		if(objform.getMode()!=null){
			if (objform.getMode().equalsIgnoreCase("ExpAddMore")) {
				List<EmpPreviousOrgTo> list=objform.getPublicationExperience();
				EmpPreviousOrgTo phdPublicationTo=new EmpPreviousOrgTo();
				phdPublicationTo.setpNameOfTitles("");
				phdPublicationTo.setpJournalPubli("");
				phdPublicationTo.setPyear("");
				objform.setPublicationLength(String.valueOf(list.size()));
				list.add(phdPublicationTo);
				objform.setMode(null);
				String size=String.valueOf(list.size()-1);
				objform.setFocusValue("pNameOfTitles_"+size);
			}
			}
		}
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
   }
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeResearchpublication(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		HttpSession sessions=request.getSession();
		if(sessions.getAttribute("phdEmplUpdate")!=null && !sessions.getAttribute("phdEmplUpdate").toString().isEmpty()){
		String test=sessions.getAttribute("phdEmplUpdate").toString();
		if(test!=null && !test.isEmpty()){
			request.setAttribute("phdEmployee", "edit");
		}}
		List<EmpPreviousOrgTo> list=null;
		if(objform.getPublicationExperience()!=null)
		if(objform.getPublicationExperience().size()>0){
				list=objform.getPublicationExperience();
				list.remove(list.size()-1);
				objform.setPublicationLength(String.valueOf(list.size()));
		}
		objform.setPublicationLength(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of PHD_INFO_APPLICATIONFORM");
		return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePhdEmpDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		HttpSession sessions=request.getSession();
		sessions.setAttribute("phdEmplUpdate",null);
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=objform.validate(mapping, request);
		validateDate(objform,errors);
		validateData(objform,errors);
		validateEditPhone1(objform,errors);
		objform.setPhdEmployeeDetails(null);
		boolean flag=false;
		if(errors.isEmpty()){
			try {
				flag=PhdEmployeeHandelers.getInstance().savePhdEmployee(objform);
				if(flag){
					ActionMessage message=new ActionMessage(CMSConstants.PHD_INFO_SUBMIT_CONFIRM);
					messages.add(CMSConstants.MESSAGES,message);
					saveMessages(request, messages);
					cleanUpPageData(objform,request);
					objform.clear1();
					setDataToForm(objform);
					initializeQualificationAndEducation(objform);
					cleanupEditSessionData(request);
					return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM_SEARCH);
				}else{
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.PHD_INFO_APPLICATIONFORM));
					saveMessages(request, messages);
					cleanupEditSessionData(request);
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
				}
			} catch (Exception exception) {
				if (exception instanceof ApplicationException) {
					String msg = super.handleApplicationException(exception);
					objform.setErrorMessage(msg);
					objform.setErrorStack(exception.getMessage());
					cleanupEditSessionData(request);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
				throw exception;
			}
				
		}else{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
		}
	}


	/**
	 * @param objform
	 * @param errors
	 */
	private void validateEditPhone1(PhdEmployeeForms objform,ActionErrors errors) {
		log.info("enter validateEditPhone..");
		if (errors == null)
			errors = new ActionErrors();

	  if (objform.getcAddressPhonNo() != null && !StringUtils.isEmpty(objform.getcAddressPhonNo())
					&& objform.getcAddressPhonNo().trim().length() != 10) {
				if (errors.get(CMSConstants.PHD_EMPLOYEE_MOBILE_INVALID) != null
						&& !errors.get(CMSConstants.PHD_EMPLOYEE_MOBILE_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.PHD_EMPLOYEE_MOBILE_INVALID);
					errors.add(CMSConstants.PHD_EMPLOYEE_MOBILE_INVALID, error);
				}
		}
		    if (objform.getPanNo() != null && !objform.getPanNo().isEmpty() && objform.getPanNo().trim().length()> 10)  {
				if (errors.get(CMSConstants.EMPLOYEE_PANNO_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_PANNO_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_PANNO_INVALID);
					errors.add(CMSConstants.EMPLOYEE_PANNO_INVALID, error);
				}
			}
		    if (objform.getBankAccNo() != null && !objform.getBankAccNo().isEmpty() && objform.getBankAccNo().trim().length()>15)  {
				if (errors.get(CMSConstants.PHD_BANKACCOUNTNO_INVALID) != null
						&& !errors.get(CMSConstants.PHD_BANKACCOUNTNO_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.PHD_BANKACCOUNTNO_INVALID);
					errors.add(CMSConstants.PHD_BANKACCOUNTNO_INVALID, error);
				}
			}
		    if (objform.getNameAddress() != null && !objform.getNameAddress().isEmpty() && objform.getNameAddress().trim().length()>149)  {
				if (errors.get(CMSConstants.PHD_NAMEADDRESS_INVALID) != null
						&& !errors.get(CMSConstants.PHD_NAMEADDRESS_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.PHD_NAMEADDRESS_INVALID);
					errors.add(CMSConstants.PHD_NAMEADDRESS_INVALID, error);
				}
			}
		    if (objform.getContactAddress() != null && !objform.getContactAddress().isEmpty() && objform.getContactAddress().trim().length()>499)  {
				if (errors.get(CMSConstants.PHD_PRESENT_ADDRESS_INVALID) != null
						&& !errors.get(CMSConstants.PHD_PRESENT_ADDRESS_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.PHD_PRESENT_ADDRESS_INVALID);
					errors.add(CMSConstants.PHD_PRESENT_ADDRESS_INVALID, error);
				}
			}  if (objform.getPermanentAddress() != null && !objform.getPermanentAddress().isEmpty() && objform.getPermanentAddress().trim().length()>499)  {
				if (errors.get(CMSConstants.PHD_PERMANENT_ADDRESS_INVALID) != null
						&& !errors.get(CMSConstants.PHD_PERMANENT_ADDRESS_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.PHD_PERMANENT_ADDRESS_INVALID);
					errors.add(CMSConstants.PHD_PERMANENT_ADDRESS_INVALID, error);
				}
			}
		log.info("exit validateEditPhone..");
	}
	/**
	 * @param objform
	 * @param errors
	 */
	private void validateData(PhdEmployeeForms objform, ActionErrors errors) {
		try{
			InputStream propStream=AdmissionFormAction.class.getResourceAsStream("/resources/application.properties");
			int maxPhotoSize=0;
			Properties prop=new Properties();
			prop.load(propStream);
			maxPhotoSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
			FormFile file=null;
			if(objform.getEmpPhoto()!=null)
				file=objform.getEmpPhoto();
			
			if( file!=null && maxPhotoSize< file.getFileSize()){
				if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
					errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE,error);
				}
			}
			if(file!=null ){
				String extn="";
				int index = file.getFileName().lastIndexOf(".");
				if(index != -1){
					extn = file.getFileName().substring(index, file.getFileName().length());
				}
				if(!extn.isEmpty() && !extn.equalsIgnoreCase(".jpg")){
					if(errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR).hasNext()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR));
					}
				}
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	/**
	 * @param objform
	 * @param errors
	 */
	private void validateDate(PhdEmployeeForms objform, ActionErrors errors) {
		
		if(objform.getDateOfBirth()!=null && !objform.getDateOfBirth().isEmpty() ){

			if (CommonUtil.isValidDate(objform.getDateOfBirth())) {
				boolean isValid = validatefutureDate(objform.getDateOfBirth());
				if (!isValid) {
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE) != null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
					}
				}
			} else {
				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID) != null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
				}
			}
		}
		if(objform.getDateOfAward()!=null && !objform.getDateOfAward().isEmpty() ){

			if (CommonUtil.isValidDate(objform.getDateOfAward())) {
				boolean isValid = validatefutureDate(objform.getDateOfAward());
				if (!isValid) {
					if (errors.get(CMSConstants.PHD_AWARD_DATE_LARGE) != null && !errors.get(CMSConstants.PHD_AWARD_DATE_LARGE).hasNext()) {
						errors.add(CMSConstants.PHD_AWARD_DATE_LARGE, new ActionError(CMSConstants.PHD_AWARD_DATE_LARGE));
					}
				}
			} else {
				if (errors.get(CMSConstants.PHD_AWARD_DATE_INVALID) != null && !errors.get(CMSConstants.PHD_AWARD_DATE_INVALID).hasNext()) {
					errors.add(CMSConstants.PHD_AWARD_DATE_INVALID, new ActionError(CMSConstants.PHD_AWARD_DATE_INVALID));
				}
			}
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchPhdDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	   throws Exception{
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		ActionErrors errors=new ActionErrors();
		HttpSession sessions=request.getSession();
		sessions.setAttribute("phdEmplUpdate",null);
	try {
		     setPhdEmployeeDetails(objform,errors);
			} catch (Exception exception) {
				if (exception instanceof ApplicationException) {
					String msg = super.handleApplicationException(exception);
					objform.setErrorMessage(msg);
					objform.setErrorStack(exception.getMessage());
					cleanupEditSessionData(request);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
				throw exception;
			}
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM_SEARCH);
		
	}
	
	
	private void setPhdEmployeeDetails(PhdEmployeeForms objform,ActionErrors errors) throws Exception{
		   List<PhdEmployeeTo> phdEmpList=PhdEmployeeHandelers.getInstance().searchPhdDetails(objform);
		   if(phdEmpList!=null && !phdEmpList.isEmpty()){
		   objform.setPhdEmployeeDetails(phdEmpList);
		   }else{
			    errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
		   }
		 
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPhdemployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
     {
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		HttpSession sessions=request.getSession();
		log.debug("Entering Phd Employee ");
		try {
			request.setAttribute("phdEmployee", "edit");
			sessions.setAttribute("phdEmplUpdate", "edit");
			PhdEmployeeHandelers.getInstance().getInitialData(objform);
			PhdEmployeeHandelers.getInstance().editPhdemployee(objform);
			log.debug("Leaving editPhdemployee ");
			if (objform.getPhotoBytes()!= null)
			{
			HttpSession session = request.getSession(false);
				if (session != null) {
					session.setAttribute(PhdEmployeeAction.PHOTOBYTES, objform.getPhotoBytes());
				}	
			}else{
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.setAttribute(PhdEmployeeAction.PHOTOBYTES, null);
				}
			}
		} catch (Exception e) {
			log.error("error in editing editPhdemployee...", e);
			String msg = super.handleApplicationException(e);
			objform.setErrorMessage(msg);
			objform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
	}
	
	
	 /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePhdEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
 {
		log.debug("Enter: updatevaluatorCharges Action");
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=objform.validate(mapping, request);
		validateDate(objform,errors);
		validateData(objform,errors);
		validateEditPhone1(objform,errors);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			if (isCancelled(request)) {
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				PhdEmployeeHandelers.getInstance().editPhdemployee(objform);
				request.setAttribute("phdEmployee", "edit");
				return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
			}
			setUserId(request, objform); // setting user id to update
			isUpdated = PhdEmployeeHandelers.getInstance().savePhdEmployee(objform);
			if (isUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.phd.employee.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				setPhdEmployeeDetails(objform,errors);
				cleanupEditSessionData(request);
				cleanUpPageData(objform,request);
				setDataToForm(objform);
				setUserId(request,objform);
				objform.setNationalityId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
				objform.setCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
				initializeQualificationAndEducation(objform);
				
			} 
			else{
				request.setAttribute("phdEmployee", "edit");
				addErrors(request, errors);
				//invSubCategoryForm.reset();
			}
		} catch (Exception e) {
			log.error("Error occured in edit PhdEmployee", e);
			String msg = super.handleApplicationException(e);
			objform.setErrorMessage(msg);
			objform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			request.setAttribute("phdEmployee", "edit");
			return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
		}
		log.debug("Exit: action class updatevaluatorCharges");
		return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM_SEARCH);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePhdemployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
         throws Exception {
	     log.debug("Action class. Delete valuatorCharges ");
	     PhdEmployeeForms objform=(PhdEmployeeForms)form;
         ActionMessages messages = new ActionMessages();
         ActionErrors errors = new ActionErrors();
       try
       {
        boolean isDeleted = PhdEmployeeHandelers.getInstance().deletePhdemployee(objform);
        if(isDeleted)
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.employee.deletesuccess");
            messages.add("messages", message);
            saveMessages(request, messages);
            setPhdEmployeeDetails(objform,errors);
        } else
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.employee.deletefailure");
            messages.add("messages", message);
            saveMessages(request, messages);
        }
             
    }
    catch(Exception e)
    {
        log.error("error submit PhdEmployee...", e);
        if(e instanceof ApplicationException)
        {
            String msg = super.handleApplicationException(e);
            objform.setErrorMessage(msg);
            objform.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        } else
        {
            String msg = super.handleApplicationException(e);
            objform.setErrorMessage(msg);
            objform.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
    }
    log.debug("Action class. Delete PhdEmployee ");
    return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addQualificationLevel(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		List<EmpQualificationLevelTo> list=null;
		HttpSession sessions=request.getSession();
		if(sessions.getAttribute("phdEmplUpdate")!=null && !sessions.getAttribute("phdEmplUpdate").toString().isEmpty()){
		String test=sessions.getAttribute("phdEmplUpdate").toString();
		if(test!=null && !test.isEmpty()){
			request.setAttribute("phdEmployee", "edit");
		}}
		EmpQualificationLevelTo phdQualificationLevelTo=new EmpQualificationLevelTo();
		phdQualificationLevelTo.setEducationId("");
		phdQualificationLevelTo.setDegree("");
		phdQualificationLevelTo.setNameOfUniversity("");
		phdQualificationLevelTo.setQstate("");
		phdQualificationLevelTo.setPercentage("");
		phdQualificationLevelTo.setAttempts("");
		
		if(objform.getPhdEmployeequalificationTos()!=null){
		if(objform.getMode()!=null){
			if (objform.getMode().equalsIgnoreCase("ExpAddMore")) {
				list=objform.getPhdEmployeequalificationTos();
				list.add(phdQualificationLevelTo);
				objform.setMode(null);
			}
		}
		}else{
			list=new ArrayList<EmpQualificationLevelTo>();
			list.add(phdQualificationLevelTo);
			objform.setMode(null);
		}
		objform.setLevelSize(String.valueOf(list.size()));
		objform.setPhdEmployeequalificationTos(list);
		String size=String.valueOf(list.size()-1);
		objform.setFocusValue("degreea_"+size);
		return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
	}	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeQualificationLevel(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		PhdEmployeeForms objform=(PhdEmployeeForms)form;
		HttpSession sessions=request.getSession();
		if(sessions.getAttribute("phdEmplUpdate")!=null && !sessions.getAttribute("phdEmplUpdate").toString().isEmpty()){
		String test=sessions.getAttribute("phdEmplUpdate").toString();
		if(test!=null && !test.isEmpty()){
			request.setAttribute("phdEmployee", "edit");
		}}
		List<EmpQualificationLevelTo> list=null;
		if(objform.getPhdEmployeequalificationTos()!=null){
				if(objform.getPhdEmployeequalificationTos().size()>0){
						list=objform.getPhdEmployeequalificationTos();
						list.remove(objform.getPhdEmployeequalificationTos().size()-1);
				}
			}
		objform.setLevelSize(String.valueOf(list.size()));
		objform.setPhdEmployeequalificationTos(list);
		return mapping.findForward(CMSConstants.PHD_INFO_APPLICATIONFORM);
	}
	
   }
