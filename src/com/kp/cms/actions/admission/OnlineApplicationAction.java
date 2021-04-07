package com.kp.cms.actions.admission;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

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

import com.awl.merchanttoolkit.dto.ReqMsgDTO;
import com.awl.merchanttoolkit.transaction.AWLMEAPI;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidateEntranceDetails;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.bo.admin.PasswordMobileMessaging;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.SeatAllocation;
import com.kp.cms.bo.admin.Student;
//import com.kp.cms.bo.admission.CourseApplicationOpenSession;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.constants.KPPropertiesConfiguration;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.OnlineApplicationForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.EntranceDetailsHandler;
import com.kp.cms.handlers.admin.LanguageHandler;
//import com.kp.cms.handlers.admin.MuncipalityCorporationEntryHandler;
import com.kp.cms.handlers.admin.MBAEntranceExamHandler;
import com.kp.cms.handlers.admin.OccupationTransactionHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.admin.StateHandler;
import com.kp.cms.handlers.admin.SubReligionHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.OnlineApplicationHandler;
import com.kp.cms.handlers.admission.UniqueIdRegistrationHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.usermanagement.LoginHandler;
import com.kp.cms.helpers.admin.CourseHelper;
import com.kp.cms.helpers.admission.OnlineApplicationHelper;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidateEntranceDetailsTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.CoursePrerequisiteTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyMasterTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DistrictTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.EntrancedetailsTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.SeatAllocationTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admin.StudentVehicleDetailsTO;
import com.kp.cms.to.admin.UGCoursesTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AddressTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.transactions.admin.ICourseTransaction;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.admin.ISubReligionTransaction;
import com.kp.cms.transactions.admission.IOnlineApplicationTxn;
import com.kp.cms.transactions.admission.IUniqueIdRegistration;
import com.kp.cms.transactions.usermanagement.ILoginTransaction;
import com.kp.cms.transactionsimpl.admin.CourseTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SubReligionTransactionImpl;
import com.kp.cms.transactionsimpl.admission.OnlineApplicationImpl;
import com.kp.cms.transactionsimpl.admission.UniqueIdRegistrationImpl;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ConverationUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtil;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.SMS_Message;

@SuppressWarnings({"deprecation","static-access"})
public class OnlineApplicationAction extends BaseDispatchAction {
	
	
	public static final String ONLINE_APPLICATION_CHALLAN_PAGE_FOR_LAVASA="OnlineChallanPageForLavasa";
	private static final Log log = LogFactory.getLog(OnlineApplicationAction.class);
	private static final String OTHER="other";
	private static final String TO_DATEFORMAT="MM/dd/yyyy";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final String COUNTID="countID";
	private static final String PHOTOBYTES="PhotoBytes";
	private static final String MNGFORMBYTES="MngFormBytes";
	private static Map<Integer,String> prefNameMap=null;
	
	
	static {
		
		prefNameMap = new HashMap<Integer, String>();
		prefNameMap.put(0, "First Preference");
		prefNameMap.put(1, "Second Preference");
		prefNameMap.put(2, "Third Preference");
		prefNameMap.put(3, "Fourth Preference");
		prefNameMap.put(4, "Fifth Preference");
		prefNameMap.put(5, "Sixth Preference");
		prefNameMap.put(6, "Seventh Preference");
		prefNameMap.put(7, "Eighth Preference");
		prefNameMap.put(8, "Ninth Preference");
		prefNameMap.put(9, "Tenth Preference");
		prefNameMap.put(10, "Eleventh Preference");
		prefNameMap.put(11, "Twelfth Preference");
		prefNameMap.put(12, "Thirteenth Preference");
		prefNameMap.put(13, "Fourteenth Preference");
		prefNameMap.put(14, "Fifteenth Preference");
		prefNameMap.put(15, "Sixteenth Preference");
		prefNameMap.put(16, "Seventeenth Preference");
		
		
	}
	
	
	
	OnlineApplicationHandler handler= OnlineApplicationHandler.getInstance();
	OnlineApplicationHelper helper= OnlineApplicationHelper.getInstance();
	IOnlineApplicationTxn txn= new OnlineApplicationImpl();
	
	
	
	
	//apply new cource page
	public ActionForward initStudentOnlineBasicInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session= request.getSession(false);
		ActionMessages errors = new ActionMessages();
		
		OnlineApplicationForm admForm=(OnlineApplicationForm)form;
		try {
			//int admApplnId=0;
			saveToken(request);
			cleanupSessionData(session);
			admForm.setCoursePrerequisites(new ArrayList<CoursePrerequisiteTO>());
			cleanUpPageData(admForm);
			admForm.setAdmApplnId(null);
			setDataForForm(admForm,session);
			admForm.setOnlineApply(true);
			setUserId(request,admForm);
			//List<AdmAppln> admAppln=new ArrayList<AdmAppln>();
			//admForm.setResidentCategoryForOnlineAppln(null);
			
			if(session!=null){
				session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, CMSConstants.LOGIN_LOGO);
			   session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, CMSConstants.TOP_BAR);
			}
			admForm.setResidentTypes(OnlineApplicationHandler.getInstance().getResidentTypes());
			admForm.setNativeCountry(CMSConstants.INDIAN_RESIDENT_ID);
			if(session.getAttribute("UNIQUE_ID")!=null && !session.getAttribute("UNIQUE_ID").toString().isEmpty()){
				 admForm.setUniqueId(session.getAttribute("UNIQUE_ID").toString());
				 session.setAttribute("UNIQUE_ID", null);
			 }else{
				 admForm.setUniqueId(null);
			 }
			session.setAttribute("isOnline", "1");
			//raghu write newly duplication check application
			if(admForm.getUniqueId()!=null && !admForm.getUniqueId().equalsIgnoreCase("")){
				LinkedList<AdmAppln> applnsList =  txn.getAdmApplnList(admForm.getUniqueId());
				if(applnsList.size()!=0){
					
					System.out.println("************************ dupliucate application in online admission start page*************************");
					
					return mapping.findForward("logoutFromOnlineApplication");
				}
			}
			
			/* admApplnId = Integer.parseInt(admForm.getAdmApplnId());
			// Get already saved Details....... for draft applications....	
				if(admForm.getUniqueId()!=null && !admForm.getUniqueId().isEmpty()){
				admAppln=OnlineApplicationHandler.getInstance().getSavedApplicantDetails(admForm);
					if(admAppln!=null){
						List<AdmApplnTO> applnTo=setRequiredDataForStart(admAppln,admForm,session);
						admForm.setApplnToList(applnTo);
					}
				}*/
			}catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				System.out.println("************************ error details in online admission start page*************************"+e);
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission start page*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			
			}
		}
		admForm.setCurrentPageNo("start");
		admForm.setDisplayPage("basic");
		handler.getCourses(admForm);
		//if(admForm.isOnlineApply()){
			return mapping.findForward("onlineAppBasicPage");
		//}else{
			//return mapping.findForward("OfflineAppBasicPage");	
		//}
	}
	
	
	
	
	//raghu added newly
	//submit basic page
	public ActionForward submitBasicInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OnlineApplicationForm admForm=(OnlineApplicationForm)form;
		//admForm.setOnlineApply(true);
		ActionMessages errors = new ActionMessages();
		
		try{
			
			//over
			if (isTokenValid(request)) {
				HttpSession session=request.getSession(false);
				
				//ActionMessages errors=admForm.validate(mapping, request);
				boolean isValidDOB=validatefutureDate(admForm.getApplicantDob());
				if(!isValidDOB){
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
				}
				admForm.setAutoSave(null);
				//validateProgramCourse(errors,admForm,true);
				
				if(admForm.getProgramTypeId() ==null || admForm.getProgramTypeId().length()==0)
				{
					if(errors==null){
						errors= new ActionMessages();
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
						errors.add("programTypeId", error);
					}else{
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
						errors.add("programTypeId", error);
					}
				}
				
				if(admForm.getUniqueId()==null || admForm.getUniqueId().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Unable to proceed with the application. Please login with your Unique ID and start again."));
				}
				
				if(admForm.getEmail()!=null && !StringUtils.isEmpty(admForm.getEmail())){
					if(admForm.getConfirmEmail()!=null && !StringUtils.isEmpty(admForm.getConfirmEmail())){
						if(!admForm.getEmail().equals(admForm.getConfirmEmail())){
							if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null && !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
								errors.add("confirmEmail",new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
							}
						}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null && !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
							errors.add("confirmEmail",new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
						}
					}
				}
				
				/*else if(admForm.getConfirmEmail()!=null && !StringUtils.isEmpty(admForm.getConfirmEmail())){
					if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null && !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
						errors.add("confirmEmail",new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
					}
				}*/
				
				if(admForm.getMobileNo1()==null || admForm.getMobileNo1().isEmpty()){
					ActionMessage error= new ActionMessage("knowledgepro.appln.issue.countrycode.required");
					errors.add("mobileNo1", error);
				}
				
				if((admForm.getResidentCategoryForOnlineAppln()!=null && !admForm.getResidentCategoryForOnlineAppln().isEmpty()) ){
					//if( CMSConstants.INDIAN_RESIDENT_LIST.contains(Integer.parseInt(admForm.getResidentCategoryForOnlineAppln()))){
						if(admForm.getMobileNo2()!=null && !admForm.getMobileNo2().isEmpty() && admForm.getMobileNo2().length()>10){
							errors.add("mobileNo2", new ActionError(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED));
						}
						//admForm.setIndianCandidate(true);
					//}
				}
				
				/*else{
					errors.add("residentCategoryForOnlineAppln", new ActionError("admissionFormForm.rescatg.select"));
				}*/
				
				if(errors.isEmpty()){
					
					int courseId=0;
					if(admForm.getCourseId()!=null && !admForm.getCourseId().equalsIgnoreCase("")){
						courseId=Integer.parseInt(admForm.getCourseId());
					}
					
					boolean isCourseDraft=OnlineApplicationHandler.getInstance().checkCourseInDraftMode(admForm.getUniqueId(),courseId);
					
					//if records not there to store new record
					if (!isCourseDraft) {
						admForm.setIsCourseDraft("No");
						//if(errors!=null && !errors.isEmpty())
						//{
						if(admForm.getProgramTypeId()!=null && !admForm.getProgramTypeId().isEmpty()){
							Map<Integer, String> programMap=CommonAjaxHandler.getInstance().getApplnProgramsByProgramTypeNew(Integer.parseInt(admForm.getProgramTypeId()));
							admForm.setProgramMap(programMap);
						}
						if(admForm.getProgramId()!=null && !admForm.getProgramId().isEmpty()){
							//raghu
							//Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByProgramForOnlineNew(Integer.parseInt(admForm.getProgramId()));
							Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByProgramTypeForOnlineNew(Integer.parseInt(admForm.getProgramTypeId()));
							
							
							admForm.setCourseMap(courseMap);
						}
						
						saveErrors(request, errors);
						//admForm.setDisplayPage("payment");
						admForm.setCurrentPageNo("basic");
						//raghu
						//int appliedYear = AdmissionFormHandler.getInstance().getApplicationYearByProgramtype(Integer.parseInt(admForm.getProgramTypeId()));
						IUniqueIdRegistration txnImpl = UniqueIdRegistrationImpl.getInstance();
						String academicYear = txnImpl.getAdmissionAcademicYear();
						
						admForm.setApplicationYear(academicYear);
						
						/*session=request.getSession(false);
				if(session==null){
					return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
				}*/
						setUserId(request,admForm);
						
						boolean result=handler.saveBasicPage(admForm);
						if(result){
							admForm.setDisplayPage("payment");
							AdmApplnTO applnTo=null;
							if(admForm.isOnlineApply()){
								applnTo=setRequiredDataTOForm(admForm,session,request,errors,"");
							}else{
								//raghu
								//applnTo=setRequiredDataTOFormOffline(admForm,session,request,errors,"");	
							}
							admForm.setApplicantDetails(applnTo);
							
							
							//if(admForm.isOnlineApply())
							//return byPassMethodForPayment(mapping, admForm, request, response);
							return mapping.findForward("onlineAppBasicPage");
						//else
							//return mapping.findForward("OfflineAppBasicPage");*/
							
							
						}
						
					}//new record store close
					
					//old record is there throw error
					else{
						admForm.setIsCourseDraft("Yes");
						if(admForm.getProgramTypeId()!=null && !admForm.getProgramTypeId().isEmpty()){
							Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getApplnProgramsByProgramTypeNew(Integer.parseInt(admForm.getProgramTypeId()));
							if(programMap!=null && !programMap.isEmpty()){
								admForm.setProgramMap(programMap);
							}
						}
						if(admForm.getProgramId()!=null && !admForm.getProgramId().isEmpty()){
							//raghu
							Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByProgramTypeForOnlineNew(Integer.parseInt(admForm.getProgramTypeId()));
							
						if(courseMap!=null && !courseMap.isEmpty()){
								admForm.setCourseMap(courseMap);
							}
						}
						
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","You have a pending application to complete"));
						saveErrors(request, errors);
					}
					
					
					
				}//errors check close
				
				//errors are there
				else{
					
					if(admForm.getProgramTypeId()!=null && !admForm.getProgramTypeId().isEmpty()){
						Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getApplnProgramsByProgramTypeNew(Integer.parseInt(admForm.getProgramTypeId()));
						if(programMap!=null && !programMap.isEmpty()){
							admForm.setProgramMap(programMap);
						}
					}
					if(admForm.getProgramId()!=null && !admForm.getProgramId().isEmpty()){
						//raghu
						Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByProgramTypeForOnlineNew(Integer.parseInt(admForm.getProgramTypeId()));
						//Map<Integer, String> courseMap=new HashMap<Integer, String>();
					if(courseMap!=null && !courseMap.isEmpty()){
							admForm.setCourseMap(courseMap);
						}
					}
					
					
					saveErrors(request, errors);
					
					
					
				}
				
				resetToken(request);
				saveToken(request);
				
			}
		} catch (Exception e) {
			log.error("error in init online application page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
				System.out.println("************************ error details in online admission in basic info*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			   
				return mapping.findForward("logoutFromOnlineApplication");
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in basic info*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("logoutFromOnlineApplication");
			}
		}
		//if(admForm.isOnlineApply())
			return mapping.findForward("logoutFromOnlineApplication");
		//else
		//	return mapping.findForward("OfflineAppBasicPage");
	}
	
	
	
	
	
	//set data for each and every page for editing
	private AdmApplnTO setRequiredDataTOForm(OnlineApplicationForm admForm, HttpSession session, HttpServletRequest request, ActionMessages errors,String mode) throws Exception 
	{
		try{
		AdmApplnTO applnTo=new AdmApplnTO();
		AdmAppln admAppln=new AdmAppln();
		String courseName="";
		String progName="";
		String progTypeName="";
		//String decCourseID="";
		//String workExpNeeded=null;
		int year=0;
		admForm.setBirthCountryId(0);
		admForm.setCurAddrCountyId(0);
		admForm.setPerAddrCountyId(0);
		admForm.setParentAddrCountyId(0);
		admForm.setGuardianAddrCountyId(0);
		
		admForm.setOnlineApply(true);
		 //decCourseID=(String)request.getParameter("decText");
	 if(admForm.getAdmApplnId()!=null && !admForm.getAdmApplnId().isEmpty()){
		admAppln=OnlineApplicationHandler.getInstance().getAppliedApplicationDetails(admForm);
	 }else
	 {
		 List<AdmAppln> admApplnlist= OnlineApplicationHandler.getInstance().getSavedApplicantDetails(admForm);
		 if(admApplnlist!=null && !admApplnlist.isEmpty()){
			 admAppln=admApplnlist.get(0);
		 }
	 }
	if(admAppln!=null){
			if(admAppln.getId()>0){
				applnTo.setId(admAppln.getId());
				admForm.setAdmApplnId(String.valueOf(admAppln.getId()));
			}
	AdmAppln appln=null;
	if (mode==null || mode.isEmpty()) {
		LinkedList<AdmAppln> applnsList =  txn.getAdmApplnList(admForm.getUniqueId());
		if(applnsList == null || applnsList.isEmpty()){
			applnsList = new LinkedList<AdmAppln>();
			
		}
		if(applnsList!=null && !applnsList.isEmpty()){
			appln =applnsList.getLast();
			
		}
	}
	String candidateRefNo = txn.getcandidatepgi(admForm.getUniqueId());
	admForm.setCandidateRefNo(candidateRefNo);
	if(appln==null ){
		appln=admAppln;
	}
			applnTo.setCreatedBy(admAppln.getCreatedBy());
			applnTo.setCreatedDate(admAppln.getCreatedDate());
			applnTo.setIsFinalMeritApproved(admAppln.getIsFinalMeritApproved());
			applnTo.setRemark(admAppln.getRemarks());
			int studentId=0;
			if(admAppln.getStudents()!=null && admAppln.getStudents().size()>0){
				for(Student student:admAppln.getStudents()){
					studentId=student.getId();
					
				}
				
			}
			if( studentId>0 )
				applnTo.setStudentId(studentId);
			applnTo.setIsSelected(admAppln.getIsSelected());
			if(admAppln.getMode()!=null && !admAppln.getMode().isEmpty()){
				admForm.setMode(admAppln.getMode());
			}else{
				if(admForm.isOnlineApply())
				{
					admForm.setMode("Online");
				}else
				{
					admForm.setMode("Offline");
				}
			}
			
		
			if(admAppln.getNotSelected()==null){
				applnTo.setNotSelected(false);
			}
			else{
				applnTo.setNotSelected(admAppln.getNotSelected());
			}
			if(admAppln.getIsWaiting()==null){
				applnTo.setIsWaiting(false);
			}
			else{
				applnTo.setIsWaiting(admAppln.getIsWaiting());
			}
			applnTo.setNotSelected(admAppln.getNotSelected());
			applnTo.setIsWaiting(admAppln.getIsWaiting());
			//
			applnTo.setIsBypassed(admAppln.getIsBypassed());
			applnTo.setIsCancelled(admAppln.getIsCancelled());
			applnTo.setIsFreeShip(admAppln.getIsFreeShip());
			applnTo.setIsApproved(admAppln.getIsApproved());
			applnTo.setIsLIG(admAppln.getIsLig());
		    applnTo.setIsInterviewSelected(admAppln.getIsInterviewSelected());
		    
		  //raghu neft
		    if(admAppln.getMode()!=null && (admAppln.getMode().equalsIgnoreCase("Challan") || admAppln.getMode().equalsIgnoreCase("NEFT"))){
				
		    if(admAppln.getIsChallanRecieved()!=null){
		    	applnTo.setIsChallanRecieved(admAppln.getIsChallanRecieved());
		    	admForm.setPaymentSuccess(false);
		    }else{
		    	admForm.setPaymentSuccess(false);
		    	applnTo.setIsChallanRecieved(false);
		    }
		    
		    applnTo.setRecievedChallanNo(admAppln.getRecievedChallanNo());
			if(admAppln.getRecievedChallanDate()!=null )
				applnTo.setRecievedChallanDate(CommonUtil.ConvertStringToDateFormat(admAppln.getRecievedChallanDate().toString(), "yyyy-MM-dd","dd/MM/yyyy"));
			
		    
		    }else{
		    	applnTo.setIsChallanRecieved(false);
		    }
		    
		    if(admAppln.getMode()!=null && admAppln.getMode().equalsIgnoreCase("Online")){
		    	
			    if(admAppln.getAmount()!=null && admAppln.getJournalNo()!=null  && admAppln.getDate()!=null){
			    	
			    	admForm.setTxnAmt(admAppln.getAmount().toPlainString());
					admForm.setTxnRefNo(admAppln.getJournalNo());
					admForm.setTxnDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admAppln.getDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
					admForm.setPaymentSuccess(true);
					
			    }
		    	
		    }
		    
		   // admForm.setJournalNo(admAppln.getJournalNo());
		    
			if(admAppln.getIsDraftMode()!=null){
				if(admAppln.getIsDraftMode()){
					admForm.setIsDraftMode(true);
					if(admAppln.getCurrentPageName()!=null && admAppln.getCurrentPageName()!=null){
						admForm.setCurrentPageNo(admAppln.getCurrentPageName());
						
						/*if(admAppln.getCurrentPageName().equalsIgnoreCase("start")){
							admForm.setDisplayPage("basic");
						}else */if(admAppln.getCurrentPageName().equalsIgnoreCase("basic")){
							admForm.setDisplayPage("payment");
							//IUniqueIdRegistration idRegistration=UniqueIdRegistrationImpl.getInstance();
							//String amount=idRegistration.getApplicationFees(admAppln.getAppliedYear()+"",admForm.getProgramTypeId(),admForm.getSubReligion());
							
							String amount="0";
							
							/*if(admAppln.getStudentOnlineApplication().getIsAided()){
								amount = String.valueOf(CMSConstants.AIDED_FEE);
							}else{
								amount = String.valueOf(CMSConstants.UN_AIDED_FEE);
							}*/
							admForm.setIsCommuQuota(OnlineApplicationHandler.getInstance().getIsCommunityquota(admForm.getAdmApplnId()));
							admForm.setMngQuota(OnlineApplicationHandler.getInstance().getIsManagementquota(admForm.getAdmApplnId()));
							if(admForm.getProgramTypeId()!=null && admForm.getProgramTypeId().equalsIgnoreCase("1")){
								if (admForm.getIsCommuQuota()=="1" || admForm.getIsCommuQuota().equals("1")) {
									amount=String.valueOf((Integer.parseInt(amount)+Integer.parseInt(CMSConstants.COMMUNITYUG_FEE)));
								}
								if (admForm.getMngQuota()=="1" || admForm.getMngQuota().equals("1")) {
									amount=String.valueOf((Integer.parseInt(amount)+Integer.parseInt(CMSConstants.ONINE_APPLICATION_UG_FEE)));
								}
							}else{
								if (admForm.getIsCommuQuota()=="1" || admForm.getIsCommuQuota().equals("1")) {
									amount=String.valueOf((Integer.parseInt(amount)+Integer.parseInt(CMSConstants.COMMUNITYUG_FEE)));
								}
								if (admForm.getMngQuota()=="1" || admForm.getMngQuota().equals("1")) {
									amount=String.valueOf((Integer.parseInt(amount)+Integer.parseInt(CMSConstants.ONINE_APPLICATION_PG_FEE)));
								}
							}
							
							admForm.setApplicationAmount(amount);
							admForm.setApplicationAmount1(amount);
							//neft
							admForm.setDdAmount(amount);
							//newly added
							admForm.setDdNo(admAppln.getStudentOnlineApplication().getChallanNumber());
							admForm.setJournalNo(admAppln.getStudentOnlineApplication().getChallanNumber());
							
							
						}else if(admAppln.getCurrentPageName().equalsIgnoreCase("payment")){

							admForm.setDisplayPage("paymentsuccess");
						}else if(admAppln.getCurrentPageName().equalsIgnoreCase("paymentsuccess")){
							admForm.setDisplayPage("preferences");
						}else if(admAppln.getCurrentPageName().equalsIgnoreCase("preferences")){
							admForm.setDisplayPage("personaldetail");
						}else if(admAppln.getCurrentPageName().equalsIgnoreCase("personaldetail")){
							admForm.setDisplayPage("educationaldetail");
						}else if(admAppln.getCurrentPageName().equalsIgnoreCase("educationaldetail")){
							admForm.setDisplayPage("attachment");
						}else if(admAppln.getCurrentPageName().equalsIgnoreCase("attachment")){
							admForm.setDisplayPage("confirmPage");
						}else{
							//admForm.setDisplayPage("preferences");
							admForm.setDisplayPage("confirmPage");
						}
						
					}
					else
					{
						admForm.setCurrentPageNo("start");
						if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
							session.removeAttribute(CMSConstants.APPLICATION_DATA);
					}
				
				}
			}else{
				admForm.setIsDraftMode(false);
				admForm.setCurrentPageNo("start");
				if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
					session.removeAttribute(CMSConstants.APPLICATION_DATA);
			}
			
			
			
			
			//set photo
			Set<ApplnDoc> docUploadSet=admAppln.getApplnDocs();
			

			Iterator<ApplnDoc> iterator = docUploadSet.iterator();
			while (iterator.hasNext()) {
				
				ApplnDoc applnDocBO = (ApplnDoc) iterator.next();

				
				if(applnDocBO.getIsPhoto()!=null && applnDocBO.getIsPhoto()){
					//raghu
					if(applnDocBO.getDocument()!=null){
						byte [] myFileBytes = applnDocBO.getDocument();
						session.setAttribute("PhotoBytes", myFileBytes);	
					}
				}
				else if(applnDocBO.getIsMngQuotaForm()){
					//Arun Sudhakaran
					if(applnDocBO.getDocument()!=null){
						byte [] myFileBytes = applnDocBO.getDocument();
						session.setAttribute("MngFormBytes", myFileBytes);	
					}
				}
			}
			
			
			
			
			//setting educatonal ane personal details setting here if data is there
			//-----==============================================================================================================================================================================================
			
			Map<Integer,EdnQualificationTO> eduQualificationMap = null;
			PersonalData personalData = null;
			PersonalDataTO personalTo=new PersonalDataTO();
			
			
			
			if(appln.getPersonalData()!=null){
				personalData= appln.getPersonalData();
				
				//personalData.setIsmgquota(admForm.getMgquota());
				//raghu write new getting personal data
				personalTo=OnlineApplicationHelper.getInstance().copyPropertiesValue(personalData, admAppln);
			
				
				if(personalData.getDateOfBirth()!=null){
					admForm.setDob(CommonUtil.formatDates(personalData.getDateOfBirth()));
					admForm.setApplicantDob(CommonUtil.formatDates(personalData.getDateOfBirth()));
					personalTo.setDateOfBirth(personalData.getDateOfBirth());
					personalTo.setDob(CommonUtil.formatDates(personalData.getDateOfBirth()));
				}
				
				if(personalData.getEmail()!=null && !personalData.getEmail().isEmpty()){
					admForm.setEmail(personalData.getEmail());
					admForm.setConfirmEmail(personalData.getEmail());
					personalTo.setEmail(personalData.getEmail());
					personalTo.setConfirmEmail(personalData.getEmail());
				}
				
				
				/*if(personalData.getIsHostelAdmission()!= null){
					admForm.setHostelcheck(personalData.getIsHostelAdmission());
				}
				*/
				if(personalData.getIsNsscertificate()!= null){
					admForm.setNcccertificate(personalData.getIsNsscertificate());
				}
				
				
				//personal data check over
				
				
				if(personalData.getPermanentAddressLine1()!= null && !personalData.getPermanentAddressLine1().equalsIgnoreCase("")){
					admForm.setSameTempAddr(false);
					
				}else{
					admForm.setSameTempAddr(true);
				}
				
				if(personalData.getParentAddressLine1()!= null && !personalData.getParentAddressLine1().equalsIgnoreCase("")){
					admForm.setSameParentAddr(false);
					
				}else{
					admForm.setSameParentAddr(true);
				}
				
				
				if(admAppln.getCourseBySelectedCourseId()!=null && admAppln.getCourseBySelectedCourseId().getId()>0){
					admForm.setCourseId(String.valueOf(admAppln.getCourseBySelectedCourseId().getId()));
					admForm.setProgramId(String.valueOf(admAppln.getCourseBySelectedCourseId().getProgram().getId()));
					admForm.setProgramTypeId(String.valueOf(admAppln.getCourseBySelectedCourseId().getProgram().getProgramType().getId()));
					admForm.setProgramTypeName(String.valueOf(admAppln.getCourseBySelectedCourseId().getProgram().getProgramType().getName()));
					
				}
				
				
				
				
				//set AdmapplnAdditionalInfo
				if(appln.getAdmapplnAdditionalInfo()!=null && !appln.getAdmapplnAdditionalInfo().isEmpty()){
					List<AdmapplnAdditionalInfo> additionalInfos = new ArrayList<AdmapplnAdditionalInfo>(appln.getAdmapplnAdditionalInfo());
					if(additionalInfos.get(0).getTitleFather()!=null && !additionalInfos.get(0).getTitleFather().isEmpty()){
						applnTo.setTitleOfFather(additionalInfos.get(0).getTitleFather());
					}
					if(additionalInfos.get(0).getTitleMother()!=null && !additionalInfos.get(0).getTitleMother().isEmpty()){
						applnTo.setTitleOfMother(additionalInfos.get(0).getTitleMother());
					}
					if(additionalInfos.get(0).getBackLogs()!=null ){
						applnTo.setBackLogs(additionalInfos.get(0).getBackLogs());
					}
					//raghu
					if(additionalInfos.get(0).getIsSaypass()!=null ){
						applnTo.setIsSaypass(additionalInfos.get(0).getIsSaypass());
					}
					
					admForm.setBackLogs(additionalInfos.get(0).getBackLogs());
					//raghu added newly
					admForm.setIsSaypass(additionalInfos.get(0).getIsSaypass());
					
					if(additionalInfos.get(0).getApplicantFeedback()!=null ){
						applnTo.setApplicantFeedbackId(String.valueOf(additionalInfos.get(0).getApplicantFeedback().getId()));
					}
					
					//raghu
					if(appln.getAdmapplnAdditionalInfo()!=null && appln.getAdmapplnAdditionalInfo().size()!=0)
					applnTo.setAdmapplnAdditionalInfos(appln.getAdmapplnAdditionalInfo());
					
					
				}
				
				
				/*if(admForm.getPanchayatList()!=null && admForm.getCorporationList()!=null && admForm.getMunicipalityList()!=null 
						&& admForm.getPanchayatList().size()!=0 && admForm.getCorporationList().size()!=0 && admForm.getMunicipalityList().size()!=0){
				
				}
				else{
					
				
				List<MuncipalityCorporationEntryTo> muncipalityCorporationEntry = MuncipalityCorporationEntryHandler.getInstance().getMuncipalityCorporationEntries(); 
				List<MuncipalityCorporationEntryTo> muncipalityList = new ArrayList<MuncipalityCorporationEntryTo>();
				List<MuncipalityCorporationEntryTo> corporationList = new ArrayList<MuncipalityCorporationEntryTo>();
				List<MuncipalityCorporationEntryTo> panchayatList = new ArrayList<MuncipalityCorporationEntryTo>();
				
				
				Iterator entries = muncipalityCorporationEntry.iterator();
				while (entries.hasNext()) {
				  MuncipalityCorporationEntryTo entryTo=new MuncipalityCorporationEntryTo();
				  entryTo = (MuncipalityCorporationEntryTo) entries.next();
				  String typeOfArea = entryTo.getTypeOfArea();		  
				  if(typeOfArea.equalsIgnoreCase("Municipality")){				  
					  muncipalityList.add(entryTo);
				  }
				  else if(typeOfArea.equalsIgnoreCase("Corporation")){
					  corporationList.add(entryTo);
				  }
				  else if(typeOfArea.equalsIgnoreCase("Panchayat")){			  
					  panchayatList.add(entryTo);
				  }
				  
				}
				Collections.sort(panchayatList);
				Collections.sort(corporationList);
				Collections.sort(muncipalityList);
				admForm.setPanchayatList(panchayatList);
				admForm.setCorporationList(corporationList);
				admForm.setMunicipalityList(muncipalityList);
				
				}
				
			*/
				//stream map
				if(admForm.getStreamMap()!=null && admForm.getStreamMap().size()!=0 ){
					
				}else{
					Map<Integer,String> streamMap=AdmissionFormHandler.getInstance().getStreamMap();
					if(streamMap.size()!=0){
						admForm.setStreamMap(streamMap);
					}else{
						admForm.setStreamMap(new HashMap<Integer, String>());
					}
					
					
				}
				
				//raghu write new
				String language="Language";
				String docName="Class 12";
				
				if(admForm.getAdmSubjectMap()!=null && admForm.getAdmSubjectMap().size()!=0 ){
					
				}else{
					Map<Integer,String> admsubjectMap=AdmissionFormHandler.getInstance().get12thclassSubjects(docName,language);
					admForm.setAdmSubjectMap(admsubjectMap);
					
				}
				
				if(admForm.getAdmSubjectLangMap()!=null && admForm.getAdmSubjectLangMap().size()!=0 ){
					
				}else{
					Map<Integer,String> admsubjectLangMap=AdmissionFormHandler.getInstance().get12thclassLangSubjects(docName,language);
					admForm.setAdmSubjectLangMap(admsubjectLangMap);

					
				}
				
				
				docName="DEG";
				String Core="Core";
				String Compl="Complimentary";
				String Common="Common";
				String Open="Open";
				
				if(admForm.getAdmCoreMap()!=null && admForm.getAdmCoreMap().size()!=0 ){
					
				}else{
					Map<Integer,String> admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(docName,Core);
					admForm.setAdmCoreMap(admCoreMap);
					
					
				}

				if(admForm.getAdmComplMap()!=null && admForm.getAdmComplMap().size()!=0 ){
	
				}else{
					Map<Integer,String> admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(docName,Compl);
					admForm.setAdmComplMap(admComplMap);
					
	
				}

				if(admForm.getAdmCommonMap()!=null && admForm.getAdmCommonMap().size()!=0 ){
	
				}else{
					Map<Integer,String> admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(docName,Common);
					admForm.setAdmCommonMap(admCommonMap);
					
	
				}

				if(admForm.getAdmMainMap()!=null && admForm.getAdmMainMap().size()!=0 ){
	
				}else{
					Map<Integer,String> admopenMap=AdmissionFormHandler.getInstance().get12thclassSub(docName,Open);
					admForm.setAdmMainMap(admopenMap);
					
	
				}

				if(admForm.getAdmSubMap()!=null && admForm.getAdmSubMap().size()!=0 ){
	
				}else{
					Map<Integer,String> admSubMap=AdmissionFormHandler.getInstance().get12thclassSub1(docName,Common);
					admForm.setAdmSubMap(admSubMap);

	
				}
				
				
				//education details set
				if(personalData.getEdnQualifications()!=null && !personalData.getEdnQualifications().isEmpty()){
					eduQualificationMap = helper.prepareEduQualificationMap(personalData.getEdnQualifications(),admForm);
				}
				
				
				if((personalData.getResidentCategory()!=null && personalData.getResidentCategory().getId()>0) 
						&& CMSConstants.INDIAN_RESIDENT_LIST.contains(personalData.getResidentCategory().getId())){
					admForm.setIndianCandidate(true);
					}
			}
			
		//=======================================================================================================================================================================================================
		//personal data and education data setting over	
			
			
			Calendar cal= Calendar.getInstance();
			cal.setTime(new Date());
			if(appln.getAppliedYear()!=null && appln.getAppliedYear()>0){
				admForm.setApplicationYear(String.valueOf(appln.getAppliedYear()));
				year=appln.getAppliedYear();
			}
			
			
			
			/*	if(admForm.getIsInterDisciplinary()!=null && admForm.getIsInterDisciplinary().equalsIgnoreCase("Yes")){
					Map<Integer, String> intrAdmSessionMap=OnlineApplicationHandler.getInstance().getIntrAdmissionSession(Integer.parseInt(admForm.getCourseId()));
					admForm.setInterAdmissionSessionMap(intrAdmSessionMap);
				}
					//courseID=Integer.parseInt(decCourseID);
					admForm.setOutsideCourseSelected(true);
				*/
					//get course list
					if(admForm.getCourseId()!=null && !admForm.getCourseId().isEmpty()){
					 List<CourseTO> courselist=OnlineApplicationHandler.getInstance().getCourse(Integer.parseInt(admForm.getCourseId()));
					if(courselist!=null && !courselist.isEmpty()){
						CourseTO to= courselist.get(0);
						//COURSE LEVEL CONFIG SETTINGS
						if(to!=null){
							courseName=to.getName();
							if(to.getProgramTo()!=null){
								//progID=to.getProgramTo().getId();
								progName=to.getProgramTo().getName();
							}
							
							courseName=to.getName();
							if (to.getAmount() != null) {/*
								admForm.setApplicationAmount(to.getAmount().toPlainString());
								if(admForm.getInstitute().equalsIgnoreCase("LV") || admForm.getInstitute().equalsIgnoreCase("GH")){
									if(admForm.getIndianCandidate()){
										admForm.setEquivalentApplnFeeINR(to.getAmount().toPlainString());
									}
								}
							*/}
							
							
							if(to!=null){
								progName=to.getProgramTo().getName();
								if(to.getIsMotherTongue()!=null && to.getIsMotherTongue()==true)
									admForm.setDisplayMotherTongue(true);
								if(to.getIsDisplayLanguageKnown()!=null && to.getIsDisplayLanguageKnown()==true)
									admForm.setDisplayLanguageKnown(true);
								if(to.getIsHeightWeight()!=null && to.getIsHeightWeight()==true)
									admForm.setDisplayHeightWeight(true);
								if(to.getIsDisplayTrainingCourse()!=null && to.getIsDisplayTrainingCourse()==true)
									admForm.setDisplayTrainingDetails(true);
								if(to.getIsAdditionalInfo()!=null && to.getIsAdditionalInfo()==true)
									admForm.setDisplayAdditionalInfo(true);
								if(to.getIsExtraDetails()!=null && to.getIsExtraDetails()==true)
									admForm.setDisplayExtracurricular(true);
								if(to.getIsSecondLanguage()!=null && to.getIsSecondLanguage()==true)
									admForm.setDisplaySecondLanguage(true);
								if(to.getIsFamilyBackground()!=null && to.getIsFamilyBackground()==true)
									admForm.setDisplayFamilyBackground(true);
								if(to.getIsLateralDetails()!=null && to.getIsLateralDetails()==true)
									admForm.setDisplayLateralDetails(true);
								if(to.getIsTransferCourse()!=null && to.getIsTransferCourse()==true)
									admForm.setDisplayTransferCourse(true);
								if(to.getIsEntranceDetails()!=null && to.getIsEntranceDetails()==true)
									admForm.setDisplayEntranceDetails(true);
								if(to.getIsTCDetails()!=null && to.getIsTCDetails()==true)
									admForm.setDisplayTCDetails(true);
								if(to.getIsExamCenterRequired()!=null && to.getIsExamCenterRequired()==true)
									admForm.setDisplayExamCenterDetails(true);
								if(admForm.isOnlineApply()){
									admForm.setAppStatusOnSubmition(to.getStatusTextOnSubmisstionOfApplnOnline());
								}else{
									admForm.setAppStatusOnSubmition(to.getStatusTextOnSubmisstionOfApplnOffline());
								}
									
							}
							
							if(to.getProgramTo()!=null && to.getProgramTo().getProgramTypeTo()!=null){
								admForm.setPgmName(to.getProgramTo().getName());
								admForm.setPgmTypeName(to.getProgramTo().getProgramTypeTo().getProgramTypeName());
								admForm.setCourseName1(to.getName());
								admForm.setAgeFromLimit(Integer.parseInt(to.getProgramTo().getProgramTypeTo().getAgeFrom()));
								admForm.setAgeToLimit(Integer.parseInt(to.getProgramTo().getProgramTypeTo().getAgeTo()));
								//progtypeID=to.getProgramTo().getProgramTypeTo().getProgramTypeId();
								progTypeName=to.getProgramTo().getProgramTypeTo().getProgramTypeName();
								if(to.getProgramTo().getAcademicYear()!=null && !to.getProgramTo().getAcademicYear().isEmpty()){
									 admForm.setProgramYear(to.getProgramTo().getAcademicYear());
								 }
							}
							
							if((admForm.getDisplayPage()!=null && !admForm.getDisplayPage().isEmpty())){
							if (to.getAmount() != null) {/*
								admForm.setApplicationAmount(to.getAmount().toPlainString());
							*/}
							if(to.getIntApplicationFees()==null || to.getCurrencyId()==null){
								errors.add("knowledgepro.admission.online.exchangeRateApi.error",new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
							}
							
							//set international application fee for international students
							/*if(to.getIntApplicationFees()!=null){
								admForm.setDdAmount(to.getIntApplicationFees().toPlainString());
								if(!admForm.getIndianCandidate()){
									String currencyFrom=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(to.getCurrencyId().getId(), "Currency", true, "currencyCode");
									if(currencyFrom!=null && !currencyFrom.isEmpty()){
										admForm.setInternationalCurrencyName(currencyFrom);
										String calculatedINR=getCalulatedInternationalApplnFee(to.getIntApplicationFees().toPlainString(),currencyFrom.toLowerCase(), errors);
										if(calculatedINR.isEmpty()){
											errors.add("knowledgepro.admission.online.exchangeRateApi.error",new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
										}else{
											admForm.setEquivalentCalcApplnFeeINR(calculatedINR);
										}
									}
								}
							}*/
							
							if(to.getCurrencyId()!=null){
								admForm.setInternationalApplnFeeCurrencyId(String.valueOf(to.getCurrencyId().getId()));
							}
							if (to.getAmount() != null){/*
								admForm.setApplicationAmount(to.getAmount().toPlainString());
								if(admForm.getInstitute()!=null && !admForm.getInstitute().isEmpty()){
									if(admForm.getInstitute().equalsIgnoreCase("LV") || admForm.getInstitute().equalsIgnoreCase("GH")){
										if(admForm.getIndianCandidate()){
											admForm.setEquivalentApplnFeeINR(to.getAmount().toPlainString());
										}
									}
								}
							*/}
							
							
							if(to.getCurrencyId()!=null){
								admForm.setInternationalApplnFeeCurrencyId(String.valueOf(to.getCurrencyId().getId()));
							}
							
					/*workExpNeeded=to.getIsWorkExperienceRequired();
					admForm.setWorkExpMandatory(to.isWorkExpMandatory());*/
					
					}
							
							
			}// course to setting over
						
		    applnTo.setCourse(to);
		
				admForm.setCourseName(courseName);
				admForm.setProgramName(progName);
				admForm.setProgTypeName(progTypeName);
			}////course list empty close
					
		}//course list close
					
					
					
				//start by mahi only some code we need because of some problem copy pasted whole code 
				//no need this much code but dont know what problem will get copy pasted..
				if (admAppln.getCourseBySelectedCourseId()!=null) {
					Course course=admAppln.getCourseBySelectedCourseId();
					CourseTO courseTO = new CourseTO();
					ProgramTO programTO = new ProgramTO();
					ProgramTypeTO programTypeTO = new ProgramTypeTO();
					CurrencyMasterTO currenctTo=new CurrencyMasterTO();
					courseTO.setCourseBo(course);
					courseTO.setLocationId(course.getWorkLocation().getId());
					courseTO.setId(course.getId());
					courseTO.setName(course.getName());
					courseTO.setCourseBo(course);
					courseTO.setCode(course.getCode());
					courseTO.setIsActive(course.getIsActive());
					courseTO.setAmount(course.getAmount());
					courseTO.setPayCode(course.getPayCode());
					courseTO.setWorkExpMandatory(course.getIsWorkExperienceMandatory());
					//raghu
					//courseTO.setInterdisiplinary(String.valueOf(course.getIsInterdisciplinary()));
					if(course.getBankIncludeSection()!=null)
					{
						courseTO.setBankIncludeSection(course.getBankIncludeSection());
					}if(course.getCommencementDate()!=null){
						courseTO.setCommencementDate(CommonUtil.formatDate(course.getCommencementDate(),"dd/MM/yyyy"));
					}if(course.getCurrencyId()!=null){
						currenctTo.setId(course.getCurrencyId().getId());
						courseTO.setCurrencyId(currenctTo);
					}if(course.getApplicationFees()!=null){
						courseTO.setIntApplicationFees(course.getApplicationFees());
					}
					courseTO.setAppearInOnline(course.getIsAppearInOnline());
					courseTO.setApplicationProcessSms(course.getIsApplicationProcessSms());
					if(course.getOnlyForApplication()!=null)
						courseTO.setOnlyForApplication(course.getOnlyForApplication());
					
					programTypeTO.setProgramTypeId(course.getProgram().getProgramType().getId());
					programTypeTO.setProgramTypeName(course.getProgram().getProgramType().getName());
					
					programTypeTO.setAgeFrom(String.valueOf(course.getProgram().getProgramType().getAgeFrom()));
					programTypeTO.setAgeTo(String.valueOf(course.getProgram().getProgramType().getAgeTo()));
					
					programTO.setId(course.getProgram().getId());
					programTO.setName(course.getProgram().getName());
					programTO.setIsMotherTongue(course.getProgram().getIsMotherTongue());
					programTO.setIsDisplayLanguageKnown(course.getProgram().getIsDisplayLanguageKnown());
					programTO.setIsHeightWeight(course.getProgram().getIsHeightWeight());
					programTO.setIsDisplayTrainingCourse(course.getProgram().getIsDisplayTrainingCourse());
					programTO.setIsAdditionalInfo(course.getProgram().getIsAdditionalInfo());
					programTO.setIsExtraDetails(course.getProgram().getIsExtraDetails());
					programTO.setIsSecondLanguage(course.getProgram().getIsSecondLanguage());
					programTO.setIsFamilyBackground(course.getProgram().getIsFamilyBackground());
					programTO.setIsLateralDetails(course.getProgram().getIsLateralDetails());
					programTO.setIsTransferCourse(course.getProgram().getIsTransferCourse());
					programTO.setIsEntranceDetails(course.getProgram().getIsEntranceDetails());
					programTO.setIsTCDetails(course.getProgram().getIsTCDetails());
					programTO.setIsExamCenterRequired(course.getProgram().getIsExamCenterRequired());
					programTO.setProgramTypeTo(programTypeTO);
					courseTO.setProgramTo(programTO);
					courseTO.setMaxInTake(course.getMaxIntake());
					if (course.getIsAutonomous().equals(true)) {
						courseTO.setIsAutonomous("Yes");
					} else {
						courseTO.setIsAutonomous("No");
					}
					if (course.getIsWorkExperienceRequired().equals(true)) {
						courseTO.setIsWorkExperienceRequired("Yes");
					} else {
						courseTO.setIsWorkExperienceRequired("No");
					}
					if (course.getIsDetailMarksPrint().equals(true)) {
						courseTO.setIsDetailMarkPrint("Yes");
					} else {
						courseTO.setIsDetailMarkPrint("No");
					}
					
					
					
					Set<SeatAllocation> seatAllocSet = course.getSeatAllocations();
					Iterator<SeatAllocation> it = seatAllocSet.iterator();
					List<SeatAllocationTO> tempList = new ArrayList<SeatAllocationTO>();
					while (it.hasNext()) {
						SeatAllocationTO seatAllocationTo = new SeatAllocationTO();
						AdmittedThroughTO admittedThroughTO = new AdmittedThroughTO();
						SeatAllocation seatAlloc = (SeatAllocation) it.next();
						seatAllocationTo.setId(seatAlloc.getId());
						
						admittedThroughTO.setId(seatAlloc.getAdmittedThrough().getId());
						admittedThroughTO.setName(seatAlloc.getAdmittedThrough()
								.getName());
						seatAllocationTo.setAdmittedThroughTO(admittedThroughTO);
						
						seatAllocationTo.setAdmittedThroughId(seatAlloc
								.getAdmittedThrough().getId());
						seatAllocationTo.setCourseId(seatAlloc.getCourse().getId());
						seatAllocationTo.setNoofSeats(seatAlloc.getNoOfSeats());
						tempList.add(seatAllocationTo);
					}
					courseTO.setSeatAllocation(tempList);
					
					
					
					
					courseTO.setCertificateCourseName(course.getCertificateCourseName());
					if(course.getBankName()!=null && !course.getBankName().isEmpty()){
						courseTO.setBankName(course.getBankName());
					}
					if(course.getBankNameFull()!=null && !course.getBankNameFull().isEmpty()){
						courseTO.setBankNameFull(course.getBankNameFull());
					}
					if(course.getCourseMarksCard()!=null && !course.getCourseMarksCard().isEmpty()){
						courseTO.setCourseMarksCard(course.getCourseMarksCard());
					}
					if(course.getNoOfAttemtsMidSem()!=null && course.getNoOfAttemtsMidSem()!=0){
						courseTO.setNoOfMidsemAttempts(course.getNoOfAttemtsMidSem());
					}
					
					//end by giri
					applnTo.setSelectedCourse(courseTO);
				}
				
		//end by mahi 	
				
				//raghu newly
				if (admAppln.getAdmittedCourseId()!=null) {
					Course course=admAppln.getAdmittedCourseId();
					CourseTO courseTO = new CourseTO();
					courseTO.setId(course.getId());
					courseTO.setName(course.getName());
					applnTo.setAdmittedCourse(courseTO);		
				}
				
		//here all ajax code set adn default value set wil happen 		
		 if((admForm.getDisplayPage()!=null && !admForm.getDisplayPage().isEmpty())&& (!admForm.getDisplayPage().equalsIgnoreCase("basic") && !admForm.getDisplayPage().equalsIgnoreCase("payment") && !admForm.getDisplayPage().equalsIgnoreCase("paymentsuccess"))){
		
			 UniversityHandler unhandler = UniversityHandler.getInstance();
			List<UniversityTO> universities = unhandler.getUniversity();
			admForm.setUniversities(universities);
			
			
			//all address map setting
			Map<Integer,String> stateMap =new HashMap<Integer,String>();
			
			if(admForm.getBirthCountryId()!=0){
				stateMap = CommonAjaxImpl.getInstance().getStates(admForm.getBirthCountryId());
			}else{
				 stateMap = CommonAjaxImpl.getInstance().getStates(0);
			}
			admForm.setStateMap(stateMap);
			Map<Integer,String> curAddrStateMap =new HashMap<Integer,String>();
			
			if(admForm.getCurAddrCountyId()!=0){
				curAddrStateMap = CommonAjaxImpl.getInstance().getStates(admForm.getCurAddrCountyId());
			}else{
				curAddrStateMap = CommonAjaxImpl.getInstance().getStates(0);
			}
			admForm.setCurAddrStateMap(curAddrStateMap);
			
			
			Map<Integer,String> perAddrStateMap =new HashMap<Integer,String>();
			
			if(admForm.getPerAddrCountyId()!=0){
				perAddrStateMap = CommonAjaxImpl.getInstance().getStates(admForm.getPerAddrCountyId());
			}else{
				perAddrStateMap = CommonAjaxImpl.getInstance().getStates(0);
			}
			admForm.setPerAddrStateMap(perAddrStateMap);
			Map<Integer,String> parentAddstateMap =new HashMap<Integer,String>();
			
			if(admForm.getParentAddrCountyId()!=0){
				parentAddstateMap = CommonAjaxImpl.getInstance().getStates(admForm.getParentAddrCountyId());
			}else{
				parentAddstateMap = CommonAjaxImpl.getInstance().getStates(0);
			}
			admForm.setParentStateMap(parentAddstateMap);
			
			
			Map<Integer,String> guardianAddstateMap =new HashMap<Integer,String>();
			
			if(admForm.getGuardianAddrCountyId()!=0){
				guardianAddstateMap = CommonAjaxImpl.getInstance().getStates(admForm.getGuardianAddrCountyId());
			}else{
				guardianAddstateMap = CommonAjaxImpl.getInstance().getStates(0);
			}
			admForm.setGuardianStateMap(guardianAddstateMap);
			
			//raghu
			//permanent district & states
			List<StateTO> permanentStates = StateHandler.getInstance().getStates();
			if (permanentStates!=null) {
				//admForm.setCountries(permanentCountries);
				Iterator<StateTO> stitr= permanentStates.iterator();
				while (stitr.hasNext()) {
					StateTO stateTO = (StateTO) stitr.next();
					if(personalData.getStateByPermanentAddressStateId()!=null ){
					if(stateTO.getId()== personalData.getStateByPermanentAddressStateId().getId()){
						List<DistrictTO> districtList = stateTO.getDistrictList();
						Collections.sort(districtList);
						admForm.setEditPermanentDistrict(districtList);
					}
				
					}else{
						
						List<DistrictTO> districtList = new ArrayList<DistrictTO>();
						admForm.setEditPermanentDistrict(districtList);
					
					}
				}
			}
			
			
			//currentDistrict & states
			List<StateTO> currentStates = StateHandler.getInstance().getStates();
			if (currentStates!=null) {
				//admForm.setCountries(permanentCountries);
				Iterator<StateTO> stitr= currentStates.iterator();
				while (stitr.hasNext()) {
					StateTO stateTO = (StateTO) stitr.next();
					if(personalData.getStateByCurrentAddressStateId()!=null ){
						
					
					if(stateTO.getId()== personalData.getStateByCurrentAddressStateId().getId()){
						List<DistrictTO> districtList = stateTO.getDistrictList();
						Collections.sort(districtList);
						admForm.setEditCurrentDistrict(districtList);
					}
				}
					else{
						List<DistrictTO> districtList = new ArrayList<DistrictTO>();
						
						admForm.setEditCurrentDistrict(districtList);
					}
				}
			}
			
			
			//raghu
			
			
			/*ReligionHandler religionhandler = ReligionHandler.getInstance();
			if(religionhandler.getReligion()!=null){
				List<ReligionTO> religionList=religionhandler.getReligion();
				admForm.setReligions(religionList);
				Iterator<ReligionTO> relItr=religionList.iterator();
				while (relItr.hasNext()) {
					ReligionTO relTO = (ReligionTO) relItr.next();
					if(personalData.getReligionSection() !=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId())  
							&& StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getReligionId() ) && relTO.getReligionId()== Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getReligionId()) ){
						List<ReligionSectionTO> subreligions=relTO.getSubreligions();
						admForm.setSubReligions(subreligions);
					}
				}
			}*/
			
			
			List<ReligionSectionTO> subreligions=SubReligionHandler.getInstance().getSubReligion();
			admForm.setSubReligions(subreligions);
			
			
			
			
			
			
			OccupationTransactionHandler occhandler = OccupationTransactionHandler.getInstance();
			admForm.setOccupations(occhandler.getAllOccupation());
			applnTo.setVehicleDetail(new StudentVehicleDetailsTO());
			
			if(admForm.getCourseId()!=null && !admForm.getCourseId().equalsIgnoreCase("")){
				
			
			
			//prerequisite details
			/*List<CoursePrerequisiteTO> prerequisites=handler.getCoursePrerequisites(Integer.parseInt(admForm.getCourseId()));
			if (prerequisites!=null && !prerequisites.isEmpty()) {
				CandidatePrerequisiteMarks prerequisiteMarks=txn.getCandidatePreRequisiteMarks(Integer.parseInt(admForm.getAdmApplnId()));
				if (prerequisiteMarks!=null) {
					applnTo.setPreRequisiteObtMarks(prerequisiteMarks.getPrerequisiteMarksObtained()!=null?prerequisiteMarks.getPrerequisiteMarksObtained().toPlainString():"");
					applnTo.setPreRequisiteRollNo(prerequisiteMarks.getRollNo()!=null?prerequisiteMarks.getRollNo():"");
					applnTo.setPreRequisiteExamMonth(prerequisiteMarks.getExamMonth()!=null?String.valueOf(prerequisiteMarks.getExamMonth()):"");
					applnTo.setPreRequisiteExamYear(prerequisiteMarks.getExamYear()!=null?String.valueOf(prerequisiteMarks.getExamYear()):"");
					Set<CandidatePrerequisiteMarks> marks=new HashSet<CandidatePrerequisiteMarks>();
					marks.add(prerequisiteMarks);
					applnTo.setCandidatePrerequisiteMarks(marks);
					//admForm.setPrerequisitesValidated(true);
					admForm.setPreRequisiteExists(true);
				
				}else{
					admForm.setPreRequisiteExists(true);
					//admForm.setPrerequisitesValidated(false);
				}
				admForm.setCoursePrerequisites(prerequisites);
				//admForm.setEligPrerequisites(finalprereqList);
				//admForm.setChristStudent("No");
			}else{
				//admForm.setPrerequisitesValidated(true);
				admForm.setPreRequisiteExists(false);
			}*/
			
			
			//raghu
			if(appln.getApplnDocs()!=null && appln.getApplnDocs().size()!=0){
				 List<ApplnDocTO> editDocuments =OnlineApplicationHelper.getInstance().copyPropertiesEditDocValue(appln, Integer.parseInt(admForm.getCourseId()), applnTo, year);
				 applnTo.setEditDocuments(editDocuments);
					//editDocuments = copyPropertiesEditDocValue(admApplnBo.getApplnDocs(),adminAppTO.getSelectedCourse().getId(),adminAppTO,admApplnBo.getAppliedYear());
					//adminAppTO.setEditDocuments(editDocuments);
				
			}else{
				List<ApplnDocTO> reqList=handler.getRequiredDocList(String.valueOf(admForm.getCourseId()),year);
				applnTo.setEditDocuments(reqList);
			
			}
			
			
			
			
			//education details setting here either default or new ones
			if(eduQualificationMap!=null && !eduQualificationMap.isEmpty()){
				//this is for  adding education info from map
				//List<DocChecklist> exambos= txn.getExamtypes(Integer.parseInt(admForm.getCourseId()),year);
				//raghu write newly
				List<DocChecklist> exambos= txn.getExamtypes(appln.getAdmittedCourseId().getId(),year);
				List<EdnQualificationTO> eduQualificationList = helper.getFinalEduQualificationList(exambos,eduQualificationMap);
				applnTo.setEdnQualificationList(eduQualificationList);
				session.setAttribute("eduQualificationListSize", eduQualificationList.size());
			}else{
				//this is for newly adding education info
				List<EdnQualificationTO> ednQualificationList = helper.getEdnQualifications(admForm, year);
				applnTo.setEdnQualificationList(ednQualificationList);
				session.setAttribute("eduQualificationListSize", ednQualificationList.size());
			}
			
			
				
			}
				
			//candidate entrance details
				if(appln.getCandidateEntranceDetailses()!=null && appln.getCandidateEntranceDetailses().size()>0){
					CandidateEntranceDetailsTO to=new CandidateEntranceDetailsTO();
					for(CandidateEntranceDetails entDetails:appln.getCandidateEntranceDetailses()){
						if(entDetails!=null){
							to= new CandidateEntranceDetailsTO();
							if(entDetails.getId()!=0)
							to.setId(entDetails.getId());
							if(entDetails.getAdmAppln()!=null){
							to.setAdmApplnId(entDetails.getAdmAppln().getId());
							}
							if(entDetails.getEntrance()!=null){
								to.setEntranceId(entDetails.getEntrance().getId());
								to.setEntranceName(entDetails.getEntrance().getName());
							}
							to.setMonthPassing(String.valueOf(entDetails.getMonthPassing()));
							to.setYearPassing(String.valueOf(entDetails.getYearPassing()));
							to.setEntranceRollNo(entDetails.getEntranceRollNo());
							
							if(entDetails.getMarksObtained()!=null)
							to.setMarksObtained(entDetails.getMarksObtained().toString());
							if(entDetails.getTotalMarks()!=null)
							to.setTotalMarks(entDetails.getTotalMarks().toString());
						}
						if(to!=null)
						{
							applnTo.setEntranceDetail(to);
						}
					}
				}else{
					applnTo.setEntranceDetail(new CandidateEntranceDetailsTO());
				}
				
				/*boolean workExpNeed=false;
				if(workExpNeeded!=null && "Yes".equalsIgnoreCase(workExpNeeded)){
					workExpNeed=true;
					admForm.setWorkExpNeeded(true);
				}else{
					admForm.setWorkExpNeeded(false);
				}
				
				//work experience setting detail
				List<ApplicantWorkExperienceTO> workExpList=new ArrayList<ApplicantWorkExperienceTO>();
				if(workExpNeed)
				{
					
					if(appln.getApplicantWorkExperiences()!=null && !appln.getApplicantWorkExperiences().isEmpty() && appln.getApplicantWorkExperiences().size() >0 ){
					
						Set<ApplicantWorkExperience> workExperiencesSet=appln.getApplicantWorkExperiences();
						if(workExperiencesSet !=null && workExperiencesSet.size() >0){
							for(ApplicantWorkExperience workExperiences:workExperiencesSet){
								if(workExperiences!=null){
									ApplicantWorkExperienceTO workExperienceTo=new ApplicantWorkExperienceTO();
									if(workExperiences.getId()!=0)
									workExperienceTo.setId(workExperiences.getId());
									
									if(workExperiences.getOrganization()!=null && !workExperiences.getOrganization().isEmpty())
									 workExperienceTo.setOrganization(workExperiences.getOrganization());
									 if(workExperiences.getAddress()!=null && !workExperiences.getAddress().isEmpty())
									 workExperienceTo.setAddress(workExperiences.getAddress());
									 if(workExperiences.getPhoneNo()!=null && !workExperiences.getPhoneNo().isEmpty())
									 workExperienceTo.setPhoneNo(workExperiences.getPhoneNo());
									 if(workExperiences.getOccupation()!=null )
									 workExperienceTo.setOccupationId(String.valueOf(workExperiences.getOccupation().getId()));
									 DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
									 if(workExperiences.getFromDate()!=null && !workExperiences.getFromDate().toString().isEmpty()){
									 String fromDate = formatter.format(workExperiences.getFromDate());
									 workExperienceTo.setFromDate(fromDate);
									 }
									 if(workExperiences.getToDate()!=null && !workExperiences.getToDate().toString().isEmpty()){
										 String toDate = formatter.format(workExperiences.getToDate());
										 workExperienceTo.setToDate(toDate);
									 }
									 if(workExperiences.getSalary()!=null && !workExperiences.getSalary().equals(0))
									  workExperienceTo.setSalary(String.valueOf(workExperiences.getSalary()));
									 workExpList.add(workExperienceTo);
									}
								}
							}
					}else{
						for(int i=0;i<CMSConstants.MAX_CANDIDATE_WORKEXP;i++){
							ApplicantWorkExperienceTO expTo = new ApplicantWorkExperienceTO();
							workExpList.add(expTo);
						}
					}
				}
				
				//applnTo.setWorkExpList(workExpList);
				//newly added
				admForm.setWorkExpList(workExpList);
				if(CMSConstants.MAX_CANDIDATE_WORKEXP <= workExpList.size()){
					applnTo.setWorkExpList(workExpList);
				}else{
					for(int j=workExpList.size();j<CMSConstants.MAX_CANDIDATE_WORKEXP;j++){
						ApplicantWorkExperienceTO expTo = new ApplicantWorkExperienceTO();
						workExpList.add(expTo);
					}
					applnTo.setWorkExpList(workExpList);
				}
				
				//over
				session.setAttribute("workExpListSize",workExpList.size());
				*/
				
				
				if(appln.getTcNo()!=null && !appln.getTcNo().isEmpty())
				applnTo.setTcNo(appln.getTcNo());
				if(appln.getTcDate()!=null && !appln.getTcDate().toString().isEmpty())
				applnTo.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(appln.getTcDate()), OnlineApplicationAction.SQL_DATEFORMAT,OnlineApplicationAction.FROM_DATEFORMAT));
				
				if(appln.getMarkscardNo()!= null){
					applnTo.setMarkscardNo(appln.getMarkscardNo());
				}
				if(appln.getMarkscardDate()!=null && !StringUtils.isEmpty(String.valueOf(appln.getMarkscardDate())) )
					applnTo.setMarkscardDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(appln.getMarkscardDate()), OnlineApplicationAction.SQL_DATEFORMAT,OnlineApplicationAction.FROM_DATEFORMAT));
				
				
				
					
					
				
				List<ApplicantLateralDetailsTO> lateralTos= new ArrayList<ApplicantLateralDetailsTO>();
				applnTo.setLateralDetails(lateralTos);
				List<ApplicantTransferDetailsTO> transferTos= new ArrayList<ApplicantTransferDetailsTO>();
				applnTo.setTransferDetails(transferTos);
				
				ISingleFieldMasterTransaction singleFieldTx = SingleFieldMasterTransactionImpl.getInstance();
				List<MotherTongue> motherTongueList = singleFieldTx.getMotherTongueFields();
				Map<Integer, String> motherTongues = new HashMap<Integer, String>();
				Iterator<MotherTongue> mtItr = motherTongueList.iterator();
				while(mtItr.hasNext()) {
					
					MotherTongue motherTongue = mtItr.next();
					motherTongues.put(motherTongue.getId(), motherTongue.getName());
				}
				personalTo.setMotherTongues(motherTongues);
		 }	
		 // detail page check over
		//here all ajax code set adn default value set wil happen 		
		 
		 
			applnTo.setPersonalData(personalTo);
	
			
			// set prefenrences list
			Set<CandidatePreference> preferencesList=appln.getCandidatePreferences();
			/*PreferenceTO preferenceTO = new PreferenceTO();
			if(preferencesList!=null && preferencesList.size() > 0){
				for(CandidatePreference preferenceBo:preferencesList){
					if(preferenceBo.getPrefNo()!=null && preferenceBo.getPrefNo()>0 && preferenceBo.getPrefNo()==1 ){
						preferenceTO.setFirstPerfId(preferenceBo.getId());
						preferenceTO.setFirstPrefCourseId(String.valueOf(preferenceBo.getCourse().getId()));
						preferenceTO.setFirstprefCourseName(preferenceBo.getCourse().getName());
						//raghu
						if(preferenceBo.getId()!=0)
						preferenceTO.setId(preferenceBo.getId());
						
					}else if(preferenceBo.getPrefNo()!=null && preferenceBo.getPrefNo()>0 && preferenceBo.getPrefNo()==2 ){
						preferenceTO.setSecondPerfId(preferenceBo.getId());
						preferenceTO.setSecondPrefCourseId(String.valueOf(preferenceBo.getCourse().getId()));
						//raghu
						if(preferenceBo.getId()!=0)
						preferenceTO.setId(preferenceBo.getId());
					}else if(preferenceBo.getPrefNo()!=null && preferenceBo.getPrefNo()>0 && preferenceBo.getPrefNo()==3){
						preferenceTO.setThirdPerfId(preferenceBo.getId());
						preferenceTO.setThirdPrefCourseId(String.valueOf(preferenceBo.getCourse().getId()));
						//raghu
						if(preferenceBo.getId()!=0)
						preferenceTO.setId(preferenceBo.getId());
					}
				}
				applnTo.setPreference(preferenceTO);
			}*/
			
			
			
			
			//raghu here write new code for preferences
			ICourseTransaction courseTx = CourseTransactionImpl.getInstance();
			Map<Integer, String> courseMap=CourseHelper.getInstance().getCourseMap(courseTx.getCoursesForAdmission(Integer.parseInt(admForm.getProgramTypeId()), admAppln.getStudentOnlineApplication().getIsAided()));
			
			
			admForm.setCourseMap(courseMap)	;
			
			if(preferencesList!=null && preferencesList.size() > 0){
				List<CourseTO> list=new ArrayList<CourseTO>();
				
				for(CandidatePreference preferenceBo:preferencesList){
					if(preferenceBo.getPrefNo()!=null && preferenceBo.getPrefNo()>0){
						CourseTO courseTO=new CourseTO();
						if(preferenceBo.getId()!=0)
						courseTO.setPrefId(preferenceBo.getId());
					    courseTO.setId(preferenceBo.getCourse().getId());
						courseTO.setCourseMap(courseMap);
						courseTO.setPrefNo(preferenceBo.getPrefNo()+"");
						courseTO.setName(preferenceBo.getCourse().getName());
						courseTO.setPrefName(prefNameMap.get(preferenceBo.getPrefNo()-1));
						list.add(courseTO);	
					}
					
				}
				Collections.sort(list);
				admForm.setPrefcourses(list);
				admForm.setPreferenceSize(list.size());
				
				}else{
					
					//add fresh one
					//adding first preference
					List<CourseTO> list=new ArrayList<CourseTO>();
					 //adding new course
			        CourseTO courseTO=new CourseTO();
			        
			        if(admForm.getCourseId()!=null && !admForm.getCourseId().equalsIgnoreCase("")){
			        	courseTO.setId(Integer.parseInt(admForm.getCourseId()));
			        }
					courseTO.setCourseMap(courseMap);
					courseTO.setPrefNo(String.valueOf(list.size()+1));
					courseTO.setPrefName(prefNameMap.get(list.size()));
					list.add(courseTO);
					admForm.setPrefcourses(list);
					admForm.setPreferenceSize(list.size());
				}
			
			
			
			
			
			
			
			
			//raghu added from old code
			
			//Ug Course

			List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
			admForm.setUgcourseList(ugcourseList);
			
			admForm.setCheckReligionId(CMSConstants.RELIGION_CHRISTIAN_TYPE);
			admForm.setCheckRCSCId(CMSConstants.CASTE_RCSC_TYPE);
			admForm.setCategoryRCSCId(CMSConstants.CATEGORY_RCSC_TYPE);
			
			/*DioceseTransactionImpl txn=new DioceseTransactionImpl();
			List<DioceseTo> dioceseList=txn.getDiocesesList();
			admForm.setDioceseList(dioceseList);
			
			ParishTransactionImpl parishtxn=new ParishTransactionImpl();
			List<ParishTo> parishList=parishtxn.getParishes();
			admForm.setParishList(parishList);
			*/
			
			//raghu subReligion Map
			
			Map<Integer, String> subReligionMap=CommonAjaxHandler.getInstance().getSubReligion();
			admForm.setSubReligionMap(subReligionMap);
			
			
			
			session.setAttribute(CMSConstants.APPLICATION_DATA, admAppln);
			admForm.setApplicantDetails(applnTo);
			
			
			
			
			//raghu
			//here we can check which page we have to execute
			if(admForm.getCurrentPageNo()!=null && !admForm.getCurrentPageNo().isEmpty()){
				
				
				
				 /*if(admForm.getDisplayPage().equalsIgnoreCase("payment")){
					 initPaymentApplyOnline(admForm, request);
				}else if(admForm.getDisplayPage().equalsIgnoreCase("preferences")){
					initPriferencePageOnline(admForm, request);
				}else*/ 
				if(admForm.getDisplayPage().equalsIgnoreCase("personaldetail")){
					initApplicantCreationDetail(admForm, request);
				}else if(admForm.getDisplayPage().equalsIgnoreCase("educationaldetail")){
					initApplicantCreationDetail(admForm, request);
				}else if(admForm.getDisplayPage().equalsIgnoreCase("attachment")){
					initApplicantCreationDetail(admForm, request);
				}else if(admForm.getDisplayPage().equalsIgnoreCase("confirmPage")){
					initApplicantCreationDetail(admForm, request);
				}
				
			
				
				/*if(admForm.getCurrentPageNo().equalsIgnoreCase("basic"))
				{
					 if(admForm.getDisplayPage()!=null && !admForm.getDisplayPage().isEmpty()){
						 if(admForm.getDisplayPage().equalsIgnoreCase("guidelines")){
							 initGuidelinesPage(admForm,request);
							 
						 }else if(admForm.getDisplayPage().equalsIgnoreCase("terms")){
							 initTermConditions(admForm,request);
						 }
					 }
				}
				else if(admForm.getCurrentPageNo().equalsIgnoreCase("guidelines")){
					if(admForm.getDisplayPage()!=null && !admForm.getDisplayPage().isEmpty()){
						if(admForm.getDisplayPage().equalsIgnoreCase("terms")){
							 initTermConditions(admForm,request);
						 }
						else if(admForm.getDisplayPage().equalsIgnoreCase("details")){
							 initApplicantCreationDetail(admForm,request);
							 //applnTo=admForm.getApplicantDetails();
						 }
					 }
				}else if(admForm.getCurrentPageNo().equalsIgnoreCase("terms")){
					if(admForm.getDisplayPage()!=null && !admForm.getDisplayPage().isEmpty()){
						 if(admForm.getDisplayPage().equalsIgnoreCase("details")){
							 initApplicantCreationDetail(admForm,request);
							// applnTo=admForm.getApplicantDetails();
						 }
					 }
				}else if(admForm.getCurrentPageNo().equalsIgnoreCase("details"))
				{
					initPaymentApply(admForm,request);
				}*/
				
			}
			
			
			else
			{
				admForm.setDisplayPage("basic");
				 //admForm.setDisplayPage("terms");
			}
		 
			
		 
		}else
		{
			//admForm.setCurrentPageNo("start");
			admForm.setDisplayPage("basic");
			if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
				session.removeAttribute(CMSConstants.APPLICATION_DATA);
		}
		if(admForm.getMbaEntranceExamMap() == null || admForm.getMbaEntranceExamMap().isEmpty()) {
			admForm.setMbaEntranceExamMap(MBAEntranceExamHandler.getInstance().getMBAEntranceExamMap());
		}
		handler.getCourses(admForm);
		return applnTo;
		
		
		}//try close
		
	     catch (Exception e) {
	    	 e.printStackTrace();
			throw e;
		}
	
	
	
	
	}
	
	
	
	
	
	//dashboard page to mainpage
	public ActionForward initOutsideStudentOnilineAccessOfAdmission(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session= request.getSession(false);
		OnlineApplicationForm admForm=(OnlineApplicationForm)form;
		ActionMessages errors=new ActionMessages();
		try {
			/*if((admForm.getAutoSave()!=null && !admForm.getAutoSave().isEmpty()) && admForm.getAutoSave().equalsIgnoreCase("autoSave")){
				return mapping.findForward("onlineAppBasicPage");
			}else
			{*/
			String mode=null;
			Integer admApplnId=0;
			if (request.getAttribute("mode")!=null) {
				mode=(String)request.getAttribute("mode");
			}
			if (request.getAttribute("admApplnId")!=null) {
				admApplnId=(Integer)request.getAttribute("admApplnId");
				admForm.setAdmApplnId(admApplnId+"");
			}
			
			//session.setAttribute("isOnline", "1");
			admForm.setOnlineApply(true);
			cleanupSessionData(session);
			admForm.setCoursePrerequisites(new ArrayList<CoursePrerequisiteTO>());
			cleanUpPageData(admForm);
			setDataForForm(admForm,session);
			setUserId(request,admForm);
			session.removeAttribute("PhotoBytes");
			if(session!=null){
				session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, CMSConstants.LOGIN_LOGO);
			   session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, CMSConstants.TOP_BAR);
			}
			
			admForm.setResidentTypes(OnlineApplicationHandler.getInstance().getResidentTypes());
			admForm.setNativeCountry(CMSConstants.INDIAN_RESIDENT_ID);
			if(session.getAttribute("UNIQUE_ID")!=null && !session.getAttribute("UNIQUE_ID").toString().isEmpty()){
				 admForm.setUniqueId(session.getAttribute("UNIQUE_ID").toString());
				 session.setAttribute("UNIQUE_ID", null);
			 }else{
				 admForm.setUniqueId(null);
			 }
			DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
			long startTime = fmt.parse(CMSConstants.RADIOS_HIDE_DATE_SPORTS).getTime();
			long presentTime = Calendar.getInstance().getTime().getTime();
			if(presentTime > startTime) {
				admForm.setDateExpired(true);
			}
			else {
				admForm.setDateExpired(false);
			}
			// Get already saved Details....... for draft applications....	
				if(admForm.getUniqueId()!=null && !admForm.getUniqueId().isEmpty()){
					AdmApplnTO applnTo=setRequiredDataTOForm(admForm,session,request,errors,mode);
					admForm.setApplicantDetails(applnTo);
					
					
					//raghu write new for edit
					
					if(admForm.getDisplayPage().equalsIgnoreCase("basic")){
						
					}
					else if(admForm.getDisplayPage().equalsIgnoreCase("payment")){
						
						/**
						 * Code for getting all pending transaction for those who does not have any success transaction.
						 * Candidate in front end will get verify buttons based on the list of pending transactions.
						 * The payment gateway has given an offlineAPI to which the verification will be redirected.
						 * If the verification returns success the candidate will be taken to payment success page.
						 * By Arun Sudhakaran.
						 */
						
						admForm.setCandidatePendingTransaction(OnlineApplicationHandler.getInstance().getCandidatesPendingStatus(admForm));
					}else if(admForm.getDisplayPage().equalsIgnoreCase("paymentsuccess")){
						
					}else if(admForm.getDisplayPage().equalsIgnoreCase("preferences")){
						handler.getCourses(admForm);
						//admForm.setCoursesList(courseList);
						//return mapping.findForward("courselist");
					}else{
						
						//this is for dispalying default pages
						//admForm.setDisplayPage("preferences");
						//admForm.setDisplayPage("personaldetail");	
						
					}
				}
			//}
		} catch (Exception e) {
			log.error("error in initOutsideSinglePageAccess...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				System.out.println("************************ error details in online admission in outside access*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     if(admForm.getMethod()!=null && admForm.getMethod().equalsIgnoreCase("initOutsideSinglePageAccess")){
			    	 return mapping.findForward("logoutFromOnlineApplication");
			     }else{
			    	 return mapping.findForward("onlineAppBasicPage");
			     }
			     
				
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in outside access*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     if(admForm.getMethod()!=null && admForm.getMethod().equalsIgnoreCase("initOutsideSinglePageAccess")){
			    	 return mapping.findForward("logoutFromOnlineApplication");
			     }else{
			    	 return mapping.findForward("onlineAppBasicPage");
			     }
			}
		}
		
		return mapping.findForward("onlineAppBasicPage");
	}
	
	
	
	
	
	private void cleanUpPageData(OnlineApplicationForm admForm) {
		log.info("enter cleanUpPageData..");
		if(admForm!=null){
			admForm.setProgramId("");
			admForm.setProgramTypeId("");
			admForm.setCourseId("");
			admForm.setChallanNo(null);
			admForm.setJournalNo(null);
			admForm.setApplicationDate(null);
			admForm.setApplicationAmount(null);
			admForm.setBankBranch("");
			admForm.setDdAmount(null);
			admForm.setDdBankCode(null);
			admForm.setDdDate(null);
			admForm.setDdDrawnOn(null);
			admForm.setDdIssuingBank(null);
			admForm.setDdNo(null);
			admForm.setCourseName(null);
			admForm.setProgramName(null);
			admForm.setReviewed("false");
			admForm.setServerDownMessage(null);
			admForm.setAutoSave(null);
			admForm.setApplicationYear(null);
			admForm.setApplicationNumber(null);
			admForm.setIsCourseDraft(null);
			admForm.setPreferenceSize(0);
			admForm.setWorkExpList(null);
			admForm.setPreferenceList(null);
			admForm.setSavedDraftAlertMsg(false);
			
			//raghu old code
			admForm.setProgramTypeId(null);
			admForm.setReviewWarned(false);
			admForm.setReviewed("false");
			admForm.setOnlineApply(true);
			admForm.setSinglePageAppln(true);
			admForm.setDataSaved(false);
			admForm.setApplicantName(null);
			admForm.setApplicantDob(null);
			admForm.setResidentCategoryForOnlineAppln(null);
			admForm.setMobileNo1(null);
			admForm.setMobileNo2(null);
			admForm.setEmail(null);
			admForm.setConfirmEmail(null);
			admForm.setPaymentSuccess(false);
			admForm.setSelectedFeePayment(null);
			admForm.setIndianCandidate(false);
			admForm.setExamCenterRequired(false);
			admForm.setInternationalApplnFeeCurrencyId(null);
			admForm.setEquivalentApplnFeeINR(null);
			admForm.setEquivalentCalcApplnFeeINR(null);
			
			
			admForm.setAdmsubMarkList(null);
			admForm.setPrefcourses(null);
			admForm.setAdditionalCharges(null);
			admForm.setApplicationAmount(null);
			admForm.setAmount(null);
			admForm.setBank_ref_num(null);
			admForm.setCandidateRefNo(null);
			admForm.setError1(null);
			admForm.setMihpayid(null);
			admForm.setMode1(null);
			admForm.setPG_TYPE(null);
			admForm.setPgiStatus(null);
			admForm.setPayuMoneyId(null);
			admForm.setTxnAmt(null);
			admForm.setTxnAmt(null);
			admForm.setTxnid(null);
			admForm.setTxnRefNo(null);
			admForm.setUnmappedstatus(null);
			admForm.setMode(null);
			admForm.setSelectedFeePayment(null);
		
			admForm.setDegtotalmaxmark(null);
			admForm.setDegtotalmaxmarkother(null);
			admForm.setDegtotalobtainedmark(null);
			admForm.setDegtotalobtainedmarkother(null);
			
			
			admForm.setTotalmaxmark(null);
			admForm.setTotalobtainedmark(null);
			
			
			admForm.setPatternofStudy(null);
			admForm.setSameParentAddr(false);
			admForm.setSameTempAddr(false);
			admForm.setPaymentMail(null);
			//admForm.setIsSaypass(false);
			admForm.setPreferenceSize(0);
			admForm.setApplicantDetails(null);
			
		}
	}
	
	
	
	
	private void setDataForForm(OnlineApplicationForm admForm,HttpSession session) 
	{
		if(admForm!=null){
			admForm.setProgramTypeId(null);
			admForm.setReviewWarned(false);
			admForm.setReviewed("false");
			admForm.setSinglePageAppln(true);
			admForm.setDataSaved(false);
			admForm.setApplicantName(null);
			admForm.setApplicantDob(null);
			admForm.setResidentCategoryForOnlineAppln(null);
			admForm.setMobileNo1(null);
			admForm.setMobileNo2(null);
			admForm.setEmail(null);
			admForm.setConfirmEmail(null);
			admForm.setPaymentSuccess(false);
			admForm.setSelectedFeePayment(null);
			admForm.setIndianCandidate(false);
			admForm.setExamCenterRequired(false);
			admForm.setInternationalApplnFeeCurrencyId(null);
			admForm.setEquivalentApplnFeeINR(null);
			admForm.setEquivalentCalcApplnFeeINR(null);
			admForm.setApplicationNumber(null);
			admForm.setInstitute(CMSConstants.COLLEGE_NAME);
			admForm.setDisplayPage(null);
			admForm.setCurrentPageNo(null);
			admForm.setAmount(null);
			admForm.setApplicantDetails(null);
			admForm.setAutoSave(null);
			admForm.setGender(null);
			//admForm.setSubReligion(null);
			admForm.setJournalNo(null);
			admForm.setDdNo(null);
			
		}
		 if(session.getAttribute("APPLICANT_NAME")!=null && !session.getAttribute("APPLICANT_NAME").toString().isEmpty()){
			 admForm.setApplicantName(session.getAttribute("APPLICANT_NAME").toString());
			 session.setAttribute("APPLICANT_NAME", null);
		 }
		 if(session.getAttribute("APPLICANT_MOBILECODE")!=null && !session.getAttribute("APPLICANT_MOBILECODE").toString().isEmpty()){
			 admForm.setMobileNo1(session.getAttribute("APPLICANT_MOBILECODE").toString());
			 session.setAttribute("APPLICANT_MOBILECODE", null);
		 }
		 if(session.getAttribute("APPLICANT_MOBILENO")!=null && !session.getAttribute("APPLICANT_MOBILENO").toString().isEmpty()){
			 admForm.setMobileNo2(session.getAttribute("APPLICANT_MOBILENO").toString());
			 session.setAttribute("APPLICANT_MOBILENO", null);
		 }
		 if(session.getAttribute("GENDER")!=null && !session.getAttribute("GENDER").toString().isEmpty()){
			 admForm.setGender(session.getAttribute("GENDER").toString());
			 session.setAttribute("GENDER", null);
		 }
		 if(session.getAttribute("APPLICANT_EMAIL")!=null && !session.getAttribute("APPLICANT_EMAIL").toString().isEmpty()){
			 admForm.setEmail(session.getAttribute("APPLICANT_EMAIL").toString());
			 admForm.setConfirmEmail(session.getAttribute("APPLICANT_EMAIL").toString());
			 session.setAttribute("APPLICANT_EMAIL", null);
		 }
		 if(session.getAttribute("APPLICANT_DOB")!=null && !session.getAttribute("APPLICANT_DOB").toString().isEmpty()){
			 admForm.setApplicantDob(session.getAttribute("APPLICANT_DOB").toString());
			 session.setAttribute("APPLICANT_DOB", null);
		 }
		 if(session.getAttribute("APPLICANT_RESIDENT_CATEGORY_ID")!=null && !session.getAttribute("APPLICANT_RESIDENT_CATEGORY_ID").toString().isEmpty()){
			 admForm.setResidentCategoryForOnlineAppln(session.getAttribute("APPLICANT_RESIDENT_CATEGORY_ID").toString());
			 session.setAttribute("APPLICANT_RESIDENT_CATEGORY_ID", null);
		 }
		 
		 if(session.getAttribute("APPLICATION_NUMBER")!=null && !session.getAttribute("APPLICATION_NUMBER").toString().isEmpty()){
			 admForm.setApplnNo(session.getAttribute("APPLICATION_NUMBER").toString());
			 session.setAttribute("APPLICATION_NUMBER",null);
		 }
		 if(session.getAttribute("APPLICANT_PROGRAMTYPE")!=null && !session.getAttribute("APPLICANT_PROGRAMTYPE").toString().isEmpty()){
			 admForm.setProgramTypeId(session.getAttribute("APPLICANT_PROGRAMTYPE").toString());
			 session.setAttribute("APPLICANT_PROGRAMTYPE",null);
		 }
		/* if(session.getAttribute("SubReligion")!=null && !session.getAttribute("SubReligion").toString().isEmpty()){
			 admForm.setSubReligion(session.getAttribute("SubReligion").toString());
			 session.setAttribute("SubReligion", null);
		 }*/
		 if(session.getAttribute("CHALLAN_NO")!=null && !session.getAttribute("CHALLAN_NO").toString().isEmpty()){
			 admForm.setJournalNo(session.getAttribute("CHALLAN_NO").toString());
			 admForm.setDdNo(session.getAttribute("CHALLAN_NO").toString());
			 session.setAttribute("CHALLAN_NO", null);
		 }
		 if(session.getAttribute("COURSE_AIDED")!=null &&!session.getAttribute("COURSE_AIDED").toString().isEmpty()) {
			 admForm.setAided((Boolean)session.getAttribute("COURSE_AIDED"));
			 session.setAttribute("COURSE_AIDED", null);
		 }
	}
	
	
	
	
	
	
	//intial application details
	/**
	 * @param admForm
	 * @param request
	 * @throws Exception
	 */
	public void initApplicantCreationDetail(OnlineApplicationForm admForm ,HttpServletRequest request)throws Exception {
		log.info("enter initApplicantCreationDetail...");
		try{
			//HttpSession session = request.getSession(false);
			//OnlineApplicationHandler handler=OnlineApplicationHandler.getInstance();
			if(admForm.isReviewWarned()){
				admForm.setDisplayPage("personaldetail");
			}else
			{
			
			//AdmApplnTO applicantDetails = handler.getNewStudent(session,admForm);
			AdmApplnTO applicantDetails=admForm.getApplicantDetails();
			setDataToInitForm(admForm);
			
			if(applicantDetails!=null){
				
				if(applicantDetails.getSelectedCourse()!=null){
					ProgramTO progTo=applicantDetails.getSelectedCourse().getProgramTo();
					if(progTo!=null){ 
					
						if(progTo.getProgramTypeTo()!=null){
							
						}
					}
					if(applicantDetails.getSelectedCourse().getIsDetailMarkPrint()!=null && "Yes".equalsIgnoreCase(applicantDetails.getSelectedCourse().getIsDetailMarkPrint()))
						admForm.setDetailMarksPrint(true);
					else
						admForm.setDetailMarksPrint(false);
				}
				int appliedyear=Integer.parseInt(admForm.getApplicationYear());
				applicantDetails.setAppliedYear(appliedyear);
				List<StateTO> ednstates=StateHandler.getInstance().getNativeStates(CMSConstants.KP_DEFAULT_COUNTRY_ID);
				admForm.setEdnStates(ednstates);
				
				setDataToInitForm(admForm);
				/*List<CoursePrerequisiteTO> prerequisites=handler.getCoursePrerequisites(Integer.parseInt(admForm.getCourseId()));
				if (prerequisites!=null && !prerequisites.isEmpty()) {
					admForm.setCoursePrerequisites(prerequisites);
					admForm.setPreRequisiteExists(true);
					admForm.setChristStudent("No");
					admForm.setPrerequisitesValidated(false);
					
				}else{
					admForm.setPreRequisiteExists(false);
					admForm.setPrerequisitesValidated(true);
				}*/
				List<EntrancedetailsTO> entrnaceList=EntranceDetailsHandler.getInstance().getEntranceDeatilsByProgram(Integer.parseInt(admForm.getProgramId()));
				admForm.setEntranceList(entrnaceList);
			//	setSelectedCourseAsPreference(admForm);
				Calendar cal= Calendar.getInstance();
				cal.setTime(new Date());
				applicantDetails.setHasWorkExp(false);
				applicantDetails.setCreatedBy(admForm.getUserId());
				applicantDetails.setCreatedDate(cal.getTime());
					checkExtradetailsDisplay(admForm);
					checkLateralTransferDisplay(admForm);
				if(CountryHandler.getInstance().getCountries()!=null){
					List<CountryTO> birthCountries= CountryHandler.getInstance().getCountries();
					if (!birthCountries.isEmpty()) {
						admForm.setCountries(birthCountries);
					}
				}
					OrganizationHandler orgHandler= OrganizationHandler.getInstance();
					List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
					if(tos!=null && !tos.isEmpty())
					{
						OrganizationTO orgTO=tos.get(0);
							if(orgTO.getExtracurriculars()!=null)
								applicantDetails.getPersonalData().setStudentExtracurricularsTos(orgTO.getExtracurriculars());
					}
					OnlineApplicationHandler formhandler = OnlineApplicationHandler.getInstance();
					admForm.setNationalities(formhandler.getNationalities());
					LanguageHandler langHandler=LanguageHandler.getHandler();
					admForm.setMothertongues(langHandler.getLanguages());
					admForm.setLanguages(langHandler.getOriginalLanguages());
					if(admForm.isDisplayAdditionalInfo())
					{
						List<OrganizationTO> orgtos=orgHandler.getOrganizationDetails();
						if(orgtos!=null && !orgtos.isEmpty())
						{
							OrganizationTO orgTO=orgtos.get(0);
							admForm.setOrganizationName(orgTO.getOrganizationName());
							admForm.setNeedApproval(orgTO.isNeedApproval());
						}
					}
					admForm.setResidentTypes(formhandler.getResidentTypes());	
					ReligionHandler religionhandler = ReligionHandler.getInstance();
					if(religionhandler.getReligion()!=null){
						List<ReligionTO> religionList=religionhandler.getReligion();
						admForm.setReligions(religionList);
					}
				List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
				admForm.setCasteList(castelist);
				List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
				admForm.setAdmittedThroughList(admittedList);
				/*List<SubjectGroupTO> sujectgroupList=SubjectGroupHandler.getInstance().getSubjectGroupDetails(Integer.parseInt(admForm.getCourseId()));
				admForm.setSubGroupList(sujectgroupList);
				String[] subjectgroups=applicantDetails.getSubjectGroupIds();
				if (subjectgroups!=null && subjectgroups.length==0 && sujectgroupList!=null) {
					subjectgroups=new String[sujectgroupList.size()];
					applicantDetails.setSubjectGroupIds(subjectgroups);
				}*/
				List<IncomeTO> incomeList = OnlineApplicationHandler.getInstance().getIncomes();
				admForm.setIncomeList(incomeList);
				List<CurrencyTO> currencyList = OnlineApplicationHandler.getInstance().getCurrencies();
				Map<Integer,String> currencyMap=new HashMap<Integer,String>();
				if(currencyList!=null && currencyList.size()>0){
					for(CurrencyTO curTo:currencyList){
						if(curTo!=null){
							currencyMap.put(curTo.getId(), curTo.getName());
						}
						
					}
					
				}
				admForm.setCurrencyList(currencyList);
				admForm.setCurrencyMap(currencyMap);
				
					if(applicantDetails.getEditDocuments()!=null){
					Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
					while (docItr.hasNext()) {
						ApplnDocTO docTO = (ApplnDocTO) docItr.next();
						if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
						{
							admForm.setSubmitDate(docTO.getSubmitDate());
						}
					}
				}
				/*OnlineApplicationHandler.getInstance().checkWorkExperianceMandatory(admForm);
					List<CourseTO> preferences=null;
					if(applicantDetails.getPreference()!=null){
						PreferenceTO prefTo= applicantDetails.getPreference();
						List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
						if(prefTo.getFirstPrefCourseId()!=null || !StringUtils.isEmpty(prefTo.getFirstPrefCourseId()))
						{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							firstTo.setId(prefTo.getFirstPerfId());
							firstTo.setAdmApplnid(applicantDetails.getId());
							firstTo.setCoursId(admForm.getCourseId());
							firstTo.setCoursName(admForm.getCourseName());
							firstTo.setProgId(admForm.getProgramId());
							firstTo.setProgramtypeId(admForm.getProgramTypeId());
							firstTo.setPrefNo(1);
							formhandler.populatePreferenceTO(firstTo,admForm.getCourseId());
							preferences=firstTo.getPrefcourses();
							if(firstTo.getPrefcourses().size() > 1){
								firstTo.setIsMandatory(true);
							}
							prefTos.add(firstTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,admForm.getCourseId());
							firstTo.setPrefNo(1);
							if(firstTo.getPrefcourses().size() > 1){
								firstTo.setIsMandatory(true);
							}
							prefTos.add(firstTo);
						}
						if(prefTo.getSecondPrefCourseId()!=null || !StringUtils.isEmpty(prefTo.getSecondPrefCourseId()))
						{
							CandidatePreferenceTO secTo=new CandidatePreferenceTO();
							secTo.setId(prefTo.getSecondPerfId());
							secTo.setAdmApplnid(applicantDetails.getId());
							secTo.setCoursId(prefTo.getSecondPrefCourseId());
							secTo.setProgId(prefTo.getSecondPrefProgramId());
							secTo.setProgramtypeId(prefTo.getSecondPrefProgramTypeId());
							secTo.setPrefNo(2);
							formhandler.populatePreferenceTO(secTo,admForm.getCourseId());
							preferences=secTo.getPrefcourses();
							if(secTo.getPrefcourses().size() > 1){
								secTo.setIsMandatory(true);
							}
							prefTos.add(secTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,admForm.getCourseId());
							firstTo.setPrefNo(2);
							if(firstTo.getPrefcourses().size() > 1){
								firstTo.setIsMandatory(true);
							}
							prefTos.add(firstTo);
						}
						if(prefTo.getThirdPrefCourseId()!=null || !StringUtils.isEmpty(prefTo.getThirdPrefCourseId()))
						{
							CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
							thirdTo.setId(prefTo.getThirdPerfId());
							thirdTo.setPrefNo(3);
							thirdTo.setAdmApplnid(applicantDetails.getId());
							thirdTo.setCoursId(prefTo.getThirdPrefCourseId());
							thirdTo.setProgId(prefTo.getThirdPrefProgramId());
							thirdTo.setProgramtypeId(prefTo.getThirdPrefProgramTypeId());
							formhandler.populatePreferenceTO(thirdTo,admForm.getCourseId());
							preferences=thirdTo.getPrefcourses();
							if(thirdTo.getPrefcourses().size() > 1){
								thirdTo.setIsMandatory(true);
							}
							prefTos.add(thirdTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,admForm.getCourseId());
							firstTo.setPrefNo(3);
							preferences=firstTo.getPrefcourses();
							if(firstTo.getPrefcourses().size() > 1){
								firstTo.setIsMandatory(true);
							}
							prefTos.add(firstTo);
						}
						admForm.setPreferenceList(prefTos);
						if(prefTos.size()>0)
							admForm.setPreferenceListSize(true);
						else
							admForm.setPreferenceListSize(false);
					}else{
						List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
						CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(firstTo,admForm.getCourseId());
						preferences=firstTo.getPrefcourses();
						firstTo.setCoursId(admForm.getCourseId());
						firstTo.setCoursName(admForm.getCourseName());
						firstTo.setProgId(admForm.getProgramId());
						firstTo.setProgramtypeId(admForm.getProgramTypeId());
						firstTo.setPrefNo(1);
						if(firstTo.getPrefcourses().size() > 1){
							firstTo.setIsMandatory(true);
						}
						prefTos.add(firstTo);
						CandidatePreferenceTO secTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(secTo,admForm.getCourseId());
						secTo.setPrefNo(2);
						if(secTo.getPrefcourses().size() > 1){
							secTo.setIsMandatory(true);
						}
						prefTos.add(secTo);
						CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(thirdTo,admForm.getCourseId());
						thirdTo.setPrefNo(3);
						if(thirdTo.getPrefcourses().size() > 1){
							thirdTo.setIsMandatory(true);
						}
						prefTos.add(thirdTo);
						admForm.setPreferenceList(prefTos);
						if(prefTos.size()>0)
							admForm.setPreferenceListSize(true);
						else
							admForm.setPreferenceListSize(false);
					}
					
					if(session!=null){
						session.setAttribute(CMSConstants.COURSE_PREFERENCES, preferences);
					}*/
					
					
					//raghu added from old code
					
					//Ug Course

					List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
					admForm.setUgcourseList(ugcourseList);
					
					admForm.setCheckReligionId(CMSConstants.RELIGION_CHRISTIAN_TYPE);
					
					
					/*DioceseTransactionImpl txn=new DioceseTransactionImpl();
					List<DioceseTo> dioceseList=txn.getDiocesesList();
					admForm.setDioceseList(dioceseList);
					
					ParishTransactionImpl parishtxn=new ParishTransactionImpl();
					List<ParishTo> parishList=parishtxn.getParishes();
					admForm.setParishList(parishList);
					*/
					
					//raghu subReligion Map
					
					Map<Integer, String> subReligionMap=CommonAjaxHandler.getInstance().getSubReligion();
					admForm.setSubReligionMap(subReligionMap);
					
					if(applicantDetails.getPersonalData().getReligionId()!=null && !admForm.getApplicantDetails().getPersonalData().getReligionId().equalsIgnoreCase("") && !admForm.getApplicantDetails().getPersonalData().getReligionId().equalsIgnoreCase("Other")){
						Map<Integer,String>	subCasteMap=CommonAjaxHandler.getInstance().getSubCasteByReligion(Integer.parseInt(applicantDetails.getPersonalData().getReligionId()));
							admForm.setSubCasteMap(subCasteMap);
						}else{
							admForm.setSubCasteMap(new HashMap<Integer, String>());
						}
					
				
					admForm.setApplicantDetails(applicantDetails);
					
			}else{
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
					message = new ActionMessage("knowledgepro.admission.NoCourseSelected");
					messages.add("messages", message);
					saveMessages(request, messages);
					admForm.setDisplayPage("basic");
			}
			ExamGenHandler genHandler = new ExamGenHandler();
			HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
			admForm.setSecondLanguageList(secondLanguage);
			}
		}catch(Exception e){
			log.error("Error in  initApplicantCreationDetail...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
			//	return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else {
				throw e;
			}	
		}
		log.info("exit initApplicantCreationDetail...");
		//admForm.setDisplayPage("details");
		/*return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);*/
	}

	
	
	
	/*public void initPaymentApplyOnline(OnlineApplicationForm admForm ,HttpServletRequest request)throws Exception {
		
		setDataToInitForm(admForm);
		String courseName="";
		String progName="";
		String progTypeName="";
		if(admForm.getPaymentSuccess()){
			admForm.setSelectedFeePayment("OnlinePayment");
		}
		ActionMessages errors=new ActionMessages();
		CourseHandler crsHandler= CourseHandler.getInstance();
		setUserId(request,admForm);
		List<CurrencyTO> currencyList = OnlineApplicationHandler.getInstance().getCurrencies();
		Map<Integer,String> currencyMap=new HashMap<Integer,String>();
		if(currencyList!=null && currencyList.size()>0){
			for(CurrencyTO curTo:currencyList){
				if(curTo!=null){
					currencyMap.put(curTo.getId(), curTo.getName());
				}
				
			}
			
		}
		admForm.setCurrencyList(currencyList);
		admForm.setCurrencyMap(currencyMap);
		
		if((admForm.getCourseName()==null || StringUtils.isEmpty(admForm.getCourseName())) && (admForm.getProgramName()==null || StringUtils.isEmpty(admForm.getProgramName())) && (admForm.getProgramTypeName()==null || StringUtils.isEmpty(admForm.getProgramTypeName()))){
		List<CourseTO> courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
		if(courselist!=null && ! courselist.isEmpty()){
			CourseTO to=courselist.get(0);
			courseName=to.getName();
			if (to.getAmount() != null) {
				admForm.setApplicationAmount(to.getAmount().toPlainString());
				if(admForm.getInstitute().equalsIgnoreCase("LV") || admForm.getInstitute().equalsIgnoreCase("GH")){
					if(admForm.getIndianCandidate()){
						admForm.setEquivalentApplnFeeINR(to.getAmount().toPlainString());
					}
				}
			}
			if(to.getProgramTo()!=null){
				progName=to.getProgramTo().getName();
				if(to.getProgramTo().getIsExamCenterRequired()==true)
					admForm.setDisplayExamCenterDetails(true);
				
			}
			if(to.getProgramTo()!=null && to.getProgramTo().getProgramTypeTo()!=null)
				progTypeName=to.getProgramTo().getProgramTypeTo().getProgramTypeName();
		}
		
		
		admForm.setCourseName(courseName);
		admForm.setProgramName(progName);
		admForm.setProgTypeName(progTypeName);
		}
		
		
		
		List<CourseTO> courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
		if(courselist!=null && ! courselist.isEmpty()){
			CourseTO to=courselist.get(0);
			// Amount setting make sure
			if (to.getAmount() != null) {
				admForm.setApplicationAmount(to.getAmount().toPlainString());
			}
			if(to.getIntApplicationFees()==null || to.getCurrencyId()==null){
				errors.add("knowledgepro.admission.online.exchangeRateApi.error",new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
			}
			//set international application fee for international students
			if(to.getIntApplicationFees()!=null){
				admForm.setDdAmount(to.getIntApplicationFees().toPlainString());
				if(!admForm.getIndianCandidate()){
					String currencyFrom=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(to.getCurrencyId().getId(), "Currency", true, "currencyCode");
					if(currencyFrom!=null && !currencyFrom.isEmpty()){
						admForm.setInternationalCurrencyName(currencyFrom);
						String calculatedINR=getCalulatedInternationalApplnFee(to.getIntApplicationFees().toPlainString(),currencyFrom.toLowerCase(), errors);
						if(calculatedINR.isEmpty()){
							errors.add("knowledgepro.admission.online.exchangeRateApi.error",new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
						}else{
							admForm.setEquivalentCalcApplnFeeINR(calculatedINR);
						}
					}
				}
			}
			if(to.getCurrencyId()!=null){
				admForm.setInternationalApplnFeeCurrencyId(String.valueOf(to.getCurrencyId().getId()));
			}
		}
		
				admForm.setPreRequisiteExists(false);
				admForm.setCurrentPageNo("basic");
				admForm.setDisplayPage("payment");
	}*/
	
	
	//enter into online paymnet
	public ActionForward redirectToPGI(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter redirectToPGI...");
		OnlineApplicationForm admForm=(OnlineApplicationForm)form;
		ActionErrors errors=new ActionErrors();
		//raghu
		if(admForm.getApplicationAmount1()!=null)
		admForm.setApplicationAmount(admForm.getApplicationAmount1());
		validatePgi(admForm,errors);
		try{
			if(errors!=null && !errors.isEmpty()){
				saveErrors(request, errors);
				return mapping.findForward("onlineAppBasicPage");
				//return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE);
			}
			String hash= OnlineApplicationHandler.getInstance().getParameterForPGI(admForm);
			/*//request.setAttribute("pgiMsg", msg);
			request.setAttribute("hash", hash);
			request.setAttribute("txnid", admForm.getRefNo());
			request.setAttribute("productinfo", admForm.getProductinfo());
			request.setAttribute("amount",admForm.getApplicationAmount()); 
			request.setAttribute("email", admForm.getEmail());
			request.setAttribute("firstname", admForm.getApplicantName());
			request.setAttribute("phone",admForm.getMobileNo2());
			request.setAttribute("test",admForm.getTest());
			if(admForm.isAided())
				request.setAttribute("key",CMSConstants.PGI_MERCHANT_ID_AIDED);
			else
				request.setAttribute("key",CMSConstants.PGI_MERCHANT_ID);
			request.setAttribute("surl",CMSConstants.PAYUMONEY_SUCCESSURL);
			request.setAttribute("furl",CMSConstants.PAYUMONEY_FAILUREURL);*/
			
			ReqMsgDTO objReqMsgDTO = new ReqMsgDTO();
			objReqMsgDTO.setOrderId(hash);
			objReqMsgDTO.setMid(CMSConstants.PGI_WLS_MERCHANT_ID);
			objReqMsgDTO. setTrnAmt(admForm.getApplicationAmount()+"00");
			objReqMsgDTO.setTrnCurrency("INR");
			objReqMsgDTO. setMeTransReqType("S");
			objReqMsgDTO. setEnckey(CMSConstants.PGI_WLS_ENCRIPTION_KEY);
			objReqMsgDTO. setResponseUrl(CMSConstants.PAYUMONEY_SUCCESSURL);
			objReqMsgDTO.setTrnRemarks("Admission fee payment");
			objReqMsgDTO.setAddField1(admForm.getApplicantName());
			objReqMsgDTO.setAddField2(admForm.getEmail());
			objReqMsgDTO.setAddField3(admForm.getMobileNo2());
			
			AWLMEAPI objAWLMEAPI = new AWLMEAPI(); 
			objReqMsgDTO = objAWLMEAPI. generateTrnReqMsg(objReqMsgDTO);
			
			String merchantRequest = null;
			
			if (objReqMsgDTO.getStatusDesc().equals("Success")) {
				System.out.println("success In");
				merchantRequest = objReqMsgDTO.getReqMsg();
			}else{
				System.out.println("success out");
			}
			request.setAttribute("merchantRequest",merchantRequest);
		}
		catch (Exception e) {
			log.error("error in redirectToPGI...",e);
			throw e;
		}
		return mapping.findForward(CMSConstants.REDIRECT_TO_PGI_PAGE);
	}
	/**
	 * validates the form for PGI redirection
	 * @param errors
	 */
	private void validatePgi(OnlineApplicationForm admForm,ActionErrors errors) {
		if((admForm.getApplicationAmount()== null || admForm.getApplicationAmount().isEmpty())
				&& (admForm.getEquivalentCalcApplnFeeINR()== null || admForm.getEquivalentCalcApplnFeeINR().isEmpty())){
			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED));
			}
		}
		//added by giri
		if(CMSConstants.PGI_MERCHANT_ID == null || CMSConstants.PGI_MERCHANT_ID.isEmpty()
				|| CMSConstants.PGI_MERCHANT_ID=="" || CMSConstants.PGI_MERCHANT_ID_AIDED == null || CMSConstants.PGI_MERCHANT_ID_AIDED.isEmpty()
				|| CMSConstants.PGI_MERCHANT_ID_AIDED==""){
			if (errors.get(CMSConstants.PGI_MERCHANT_ID_REQUIRED)!=null && !errors.get(CMSConstants.PGI_MERCHANT_ID_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PGI_MERCHANT_ID_REQUIRED, new ActionError(CMSConstants.PGI_MERCHANT_ID_REQUIRED));
			}
		}
		if(CMSConstants.PGI_SECURITY_ID == null || CMSConstants.PGI_SECURITY_ID.isEmpty()
				|| CMSConstants.PGI_SECURITY_ID=="" || CMSConstants.PGI_SECURITY_ID_AIDED == null || CMSConstants.PGI_SECURITY_ID_AIDED.isEmpty()
				|| CMSConstants.PGI_SECURITY_ID_AIDED==""){
			if (errors.get(CMSConstants.PGI_SECURITY_ID_REQUIRED)!=null && !errors.get(CMSConstants.PGI_SECURITY_ID_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PGI_SECURITY_ID_REQUIRED, new ActionError(CMSConstants.PGI_SECURITY_ID_REQUIRED));
			}
		}
		if(CMSConstants.PGI_CHECKSUM_KEY == null || CMSConstants.PGI_CHECKSUM_KEY.isEmpty()
				|| CMSConstants.PGI_CHECKSUM_KEY=="" || CMSConstants.PGI_CHECKSUM_KEY_AIDED == null || CMSConstants.PGI_CHECKSUM_KEY_AIDED.isEmpty()
				|| CMSConstants.PGI_CHECKSUM_KEY_AIDED==""){
			if (errors.get(CMSConstants.PGI_CHECKSUM_KEY_REQUIRED)!=null && !errors.get(CMSConstants.PGI_CHECKSUM_KEY_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PGI_CHECKSUM_KEY_REQUIRED, new ActionError(CMSConstants.PGI_CHECKSUM_KEY_REQUIRED));
			}
		}
		//end by giri
	}
	
	
	//online payment update
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePGIResponse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("enter updatePGIResponse-AdmissionFormAction...");
		OnlineApplicationForm admForm=(OnlineApplicationForm)form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		
		try{
			
			boolean isUpdated= OnlineApplicationHandler.getInstance().updateResponse(admForm);
			
			if(isUpdated && admForm.getIsTnxStatusSuccess()){
				boolean	result=handler.saveApplicationDetailsToSession(admForm);
				
				
				if(admForm.getIsTnxStatusSuccess()) {
					admForm.setDisplayPage("paymentsuccess");
					smsForPaymentSucess(admForm);
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.empty.err.message",admForm.getPgiStatus()));
					saveMessages(request, messages);
				} else {
					admForm.setPgiStatus("Payment Failure");
					errors.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.empty.err.message",admForm.getPgiStatus()));
					saveErrors(request, errors);
					admForm.setDisplayPage("payment");
				}

				/*messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.empty.err.message",admForm.getPgiStatus()));
				saveMessages(request, messages);*/
			}
			else{
				errors.add("error", new ActionError("knowledgepro.admission.pgi.update.failure"));
				saveErrors(request, errors);
			}
		}
		catch (BusinessException e) {
			e.printStackTrace();
			errors.add("error", new ActionError("knowledgepro.admission.pgi.rejected"));
			saveErrors(request, errors);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("error in updatePGIResponse-AdmissionFormAction...",e);
		    errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		    saveErrors(request, errors);
		}
		
		log.info("exit updatePGIResponse-AdmissionFormAction...");
		return mapping.findForward("onlineAppBasicPage");
	}
	
	//submit Challan payment details
	public ActionForward submitApplicationFormInfoOnline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		setUserId(request, admForm);
		//presently , it is true. it will be false after login implementation
		HttpSession session= request.getSession(false);
		OnlineApplicationHandler handler = OnlineApplicationHandler.getInstance();
		ActionMessages errors= new ActionErrors();
		
		validateProgramCourse(errors,admForm,false);
		
		if(admForm.isOnlineApply()){
			validatePaymentDetails(errors,admForm);
		}
		if(errors==null|| errors.isEmpty() ){
			
			
			//athira old code
			/*int currentyear;
			int year1;
			String date="";
			
			if(admForm.getApplicationDate()!=null && !admForm.getApplicationDate().equalsIgnoreCase("")){
				date=admForm.getApplicationDate();
			}else if(admForm.getDdDate()!=null && !admForm.getDdDate().equalsIgnoreCase("")){
				date=admForm.getDdDate();
			}else if(admForm.getTxnDate()!=null && !admForm.getTxnDate().equalsIgnoreCase("")){
				date=admForm.getTxnDate();
			}
			
			if(!date.equalsIgnoreCase("")){
				
			
			 SimpleDateFormat yourDateFormat = new SimpleDateFormat( "dd/mm/yyyy");
			 Date trdate=yourDateFormat.parse(date);
				final Calendar cal1= Calendar.getInstance();
				cal1.setTime(trdate);
				currentyear=cal1.get(cal1.YEAR);
				
				final Calendar cal2= Calendar.getInstance();
				cal2.setTime(new Date());
				year1=cal2.get(cal2.YEAR);	
				if(currentyear!=year1){
					
					errors.add(CMSConstants.ADMISSIONFORM_TRANSACTIONDATE_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_TRANSACTIONDATE_INVALID));
					saveErrors(request, errors);
					//return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE);
				}
				
			}
			*/
			//old date check over
				
			//new date check for past application
			
			java.sql.Timestamp appdate=OnlineApplicationImpl.getInstance().getDateofApp(admForm.getUniqueId());
			//System.out.println(date);
			
			String sdate=CommonUtil.formatSqlDate(appdate.toString());
			//System.out.println(sdate);
			
			String enterDate="";
			if(admForm.getApplicationDate()!=null && !admForm.getApplicationDate().equalsIgnoreCase("")){
				enterDate=admForm.getApplicationDate();
			}else if(admForm.getDdDate()!=null && !admForm.getDdDate().equalsIgnoreCase("")){
				enterDate=admForm.getDdDate();
			}
			
			if(CommonUtil.ConvertStringToSQLDate(sdate).getTime()<=CommonUtil.ConvertStringToSQLDate(enterDate).getTime()){
				//System.out.println("ok");
			}else{
				errors.add(CMSConstants.ADMISSIONFORM_TRANSACTIONDATE_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_TRANSACTIONDATE_INVALID));
				saveErrors(request, errors);
				
			}
		
			
			int year; 
			if(admForm.getApplicationYear()!= null &&  !admForm.getApplicationYear().trim().isEmpty()){
				year = Integer.parseInt(admForm.getApplicationYear()); 	
			}
			else
			{
				final Calendar cal= Calendar.getInstance();
				cal.setTime(new Date());
				year=cal.get(cal.YEAR);	
			}
			
			
			boolean duplicate=false;
			if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() && 
					(admForm.getSelectedFeePayment().equalsIgnoreCase("SBI") )){
				
				//admForm.setDdDate(null);
				//admForm.setDdDrawnOn(null);
				//admForm.setDdAmount(null);
				admForm.setDdBankCode(null);
				admForm.setDdIssuingBank(null);
				//admForm.setDdNo(null);
				admForm.setInternationalApplnFeeCurrencyId(null);
				admForm.setEquivalentApplnFeeINR(null);
				duplicate = handler.checkPaymentDetails(admForm.getChallanNo(),admForm.getJournalNo(),admForm.getApplicationDate(),year);
			}
			else if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() && admForm.getSelectedFeePayment().equalsIgnoreCase("NEFT")){
				//admForm.setChallanNo(null);
				admForm.setJournalNo(admForm.getDdNo());
				admForm.setApplicationDate(admForm.getDdDate());
				admForm.setApplicationAmount(admForm.getDdAmount());
				admForm.setBankBranch(admForm.getDdDrawnOn());
				duplicate = handler.checkPaymentDetails("SelectedDDPayment",admForm.getDdNo(),admForm.getDdDate(),year);
			}
			/*boolean preRequisiteduplicate=false;
			if(admForm.getCoursePrerequisites()!= null && admForm.getCoursePrerequisites().size() > 0){
				List<CoursePrerequisiteTO> prerequisites=admForm.getCoursePrerequisites();				
				Iterator<CoursePrerequisiteTO> reqItr2=prerequisites.iterator();
				while (reqItr2.hasNext()) {
					CoursePrerequisiteTO reqTO = (CoursePrerequisiteTO) reqItr2.next();
					//OnlineApplicationHandler handler= OnlineApplicationHandler.getInstance();
					boolean duplicateroll=handler.checkDuplicatePrerequisite(reqTO.getExamYear(),reqTO.getRollNo());
					if(duplicateroll){
						preRequisiteduplicate=true;
						break;
					}
				}
			}
			if(preRequisiteduplicate){
				if(duplicate){
					if(errors==null){
						errors= new ActionMessages();
					}
					if (errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE, new ActionError(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE));
					}	
				}
			}*/
			
			//else{
				if(duplicate){
					/*if(errors==null){
						errors= new ActionMessages();
					}*/
					if (errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE, new ActionError(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE));
					}	
				}
			}
		
		//}
		
		if(errors!=null && !errors.isEmpty() )
		{
			saveErrors(request, errors);
			request.setAttribute("operation", "load");
			//if(admForm.isOnlineApply()){
				setProgramAndCourseMap(admForm, request);
				admForm.setDisplayPage("payment");
				//if(admForm.isOnlineApply()){
					
					return mapping.findForward("onlineAppBasicPage");
				//}else{
					//return mapping.findForward("OfflineAppBasicPage");	
				//}
			//}
		}
		// validation done
		try {
			
			ActionMessages err = new ActionMessages();
		boolean	result=handler.saveApplicationDetailsToSession(admForm);
		if(result)
		{
			    setRequiredDataTOForm(admForm,session,request,errors,"CurrentID");
				admForm.setDisplayPage("paymentsuccess");
		}else
		{
			saveErrors(request, err);
			admForm.setDisplayPage("payment");
		}
		} 
		catch (Exception e) {
			log.error("error in submit application detail...",e);
			//throw e;
			System.out.println("************************ error details in online admission in challan payment submitapllicationforminfo*************************"+e);
			
		     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		     saveErrors(request, errors);
		
		}
		log.info("exit submit application detail page...");
		if(admForm.isSinglePageAppln())
			
			return mapping.findForward("onlineAppBasicPage");
		else
			//offline app link here ....to be placed...
			return mapping.findForward("onlineAppBasicPage");
	}
	
	
	/**
	 * @param admissionFormForm
	 * @param request
	 * @throws Exception
	 */
	public void setProgramAndCourseMap(OnlineApplicationForm admissionFormForm,HttpServletRequest request) throws Exception {
		Map<Integer,String> programMap = new HashMap<Integer,String>();
		Map<Integer,String> courseMap = new HashMap<Integer,String>();
		if(admissionFormForm.getProgramTypeId().length() != 0 )
			programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(admissionFormForm.getProgramTypeId()));
		if(admissionFormForm.getProgramId().length() != 0)
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(admissionFormForm.getProgramId()));
		request.setAttribute("programMap", programMap);
		request.setAttribute("courseMap", courseMap);
	}
	
	public ActionForward addPrefereneces(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Begining of Add preferences in Form");
		OnlineApplicationForm admForm=(OnlineApplicationForm)form;
		ActionMessages message = new ActionMessages();
		ICourseTransaction courseTx = CourseTransactionImpl.getInstance();		
		Map<Integer, String> courseMap=CourseHelper.getInstance().getCourseMap(courseTx.getCoursesForAdmission(Integer.parseInt(admForm.getProgramTypeId()), admForm.isAided()));
		admForm.setCourseMap(courseMap);
	   
		//set first preferencs as orig courseid and check they selected course id or not
		List<CourseTO> list=admForm.getPrefcourses();
		Iterator<CourseTO> itr=list.iterator();
    	while(itr.hasNext()){
    		CourseTO courseTO=(CourseTO) itr.next();
    		if(courseTO.getId()==0){
    			ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_INVALID);
				message.add(CMSConstants.ADMISSIONFORM_COURSE_INVALID,error);
				saveErrors(request, message);
				//if(admForm.isOnlineApply())
					return mapping.findForward("onlineAppBasicPage");
				//else
				//	return mapping.findForward("OfflineAppBasicPage");
    		}
    		if(courseTO.getPrefNo().equalsIgnoreCase("1")){
    			admForm.setCourseId(courseTO.getId()+"");
    		}
    	}
		//checking select course
		if (admForm.getCourseId()==null  || admForm.getCourseId().isEmpty() ) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_INVALID);
				message.add(CMSConstants.ADMISSIONFORM_COURSE_INVALID,error);
				saveErrors(request, message);
				//if(admForm.isOnlineApply())
					return mapping.findForward("onlineAppBasicPage");
				//else
				//	return mapping.findForward("OfflineAppBasicPage");
		}
		
		
		 
	    //checking course excceded
		if(admForm.getProgramTypeId()!=null && admForm.getProgramTypeId().equalsIgnoreCase("1")){
			 if(list.size()>=CMSConstants.MAX_CANDIDATE_PREFERENCES && admForm.isAided()){
			    	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID);
			   		message.add(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID,error);
			   		saveErrors(request, message);
			   		//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
			       }
			 else if(list.size()>= CMSConstants.MAX_CANDIDATE_PREFERENCES && !admForm.isAided()){
			    	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID);
			   		message.add(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID,error);
			   		saveErrors(request, message);
						return mapping.findForward("onlineAppBasicPage");
			 }
		}else{
			
			if (Integer.parseInt(admForm.getCourseId())==16|| Integer.parseInt(admForm.getCourseId())==17) {
				if (list.size()>=2) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID);
			   		message.add(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID,error);
			   		saveErrors(request, message);
						return mapping.findForward("onlineAppBasicPage");
				}
			}
			else{
				if(list.size()>=CMSConstants.MAX_CANDIDATE_PREFERENCES_PG){
			    	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID);
			   		message.add(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID,error);
			   		saveErrors(request, message);
			   		//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
			       }
		}
		}
       
        
        
        //checking duplicates
        List<CourseTO> origList = new ArrayList<CourseTO>();
        Set<Integer> titles = new HashSet<Integer>();
        for( CourseTO courseTO : admForm.getPrefcourses() ) {
            if( titles.add( courseTO.getId())) {
            	origList.add( courseTO );
            }
        }
        
        
        //if duplicates is there send error
        if(list.size()!=origList.size()){
        	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSEPREF_DUP_INVALID);
       		message.add(CMSConstants.ADMISSIONFORM_COURSEPREF_DUP_INVALID,error);
       		saveErrors(request, message);
       		//if(admForm.isOnlineApply())
    			return mapping.findForward("onlineAppBasicPage");
    		//else
    		//	return mapping.findForward("OfflineAppBasicPage");

        }
        
      //adding orig list
        admForm.setPrefcourses(origList);
        List<CourseTO> newList=admForm.getPrefcourses();
        
        //adding new course
        CourseTO courseTO=new CourseTO();
		courseTO.setCourseMap(courseMap);
		courseTO.setPrefNo(String.valueOf(newList.size()+1));
		courseTO.setPrefName(prefNameMap.get(newList.size()));
		newList.add(courseTO);
		admForm.setPrefcourses(newList);
		admForm.setPreferenceSize(newList.size());
	
		log.info("End of Add preferences in Form");
		//if(admForm.isOnlineApply())
			return mapping.findForward("onlineAppBasicPage");
		//else
			//return mapping.findForward("OfflineAppBasicPage");
			
	}
		

	

	public ActionForward removePreferences(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of Remove preferences in Form");
		
		OnlineApplicationForm admForm=(OnlineApplicationForm)form;
		List<CourseTO> list=new ArrayList<CourseTO>();
	    list=admForm.getPrefcourses();
		
		if(list.size()>1){
		list.remove(list.size()-1);
		//employeeInfoFormNew.setPfGratuityListSize(String.valueOf(list.size()));
		admForm.setPreferenceSize(list.size());
		}
		//employeeInfoFormNew.setPfGratuityListSize(String.valueOf(list.size()-1));
		//admForm.setPreferenceSize(list.size()-1);
		//admForm.setPrefcourses(list);
		log.info("End of Remove preferences in Form");
		//if(admForm.isOnlineApply())
			return mapping.findForward("onlineAppBasicPage");
		//else
		//	return mapping.findForward("OfflineAppBasicPage");
	}	
	
	
	
	//submit payment success
	public ActionForward submitPaymentSucess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit preference page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors = new ActionMessages();
		
			//new
			try{
				
				String focusval=admForm.getFocusValue();
				//ActionMessages errors=new ActionMessages();
				HttpSession session= request.getSession(false);
						
				AdmApplnTO applicantDetail=admForm.getApplicantDetails();
				int year=Integer.parseInt(admForm.getApplicationYear());
				if(applicantDetail!=null){
					applicantDetail.setAppliedYear(year);
					//if(admForm.isOnlineApply())
						//applicantDetail.setMode("Online");
					//else
						//applicantDetail.setMode("Offline");
				}
				OnlineApplicationHandler admHandler = OnlineApplicationHandler.getInstance();
				
					//admForm.setReviewWarned(true);
					//admForm.setReviewed("true");
					resetHardCopySubmit(applicantDetail);
					//ActionMessages messages = new ActionMessages();
					//ActionMessage message = new ActionMessage(CMSConstants.APPLICATION_REVIEW_WARN);
					///messages.add("messages", message);
					//saveMessages(request, messages);
					setDocumentForView(admForm, request);
					
					//boolean result=admHandler.saveFeeSuccessPage(admForm, session, errors);
					boolean result=admHandler.createApplicant(applicantDetail,admForm,false,"Draft");
					
					if(result)
					{
						    setRequiredDataTOForm(admForm,session,request,errors,"CurrentID");
						
							admForm.setDisplayPage("preferences");
					}else
					{
						saveErrors(request, errors);
						admForm.setDisplayPage("paymentsuccess");
					}
						
					admForm.setAutoSave("autoSave");
					if(focusval!=null && !focusval.isEmpty()){
						admForm.setFocusValue(focusval);
					}
					
						//if(admForm.isOnlineApply())
							return mapping.findForward("onlineAppBasicPage");
						//else
							//return mapping.findForward("OfflineAppBasicPage");
				
				
			
				
				
		}//try close		
		catch (Exception e) {
			log.error("error in submit preference page...",e);
			//throw e;
			System.out.println("************************ error details in online admission paymnet success*************************"+e);
			
		     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		     saveErrors(request, errors);
		
		     return mapping.findForward("onlineAppBasicPage");
		
		}
		
		

	}
	
	
	
	//submit cource preferencess online
	public ActionForward submitPriferencePageOnline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit preference page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
		
			//new
			try{
				
			String focusval=admForm.getFocusValue();
			HttpSession session= request.getSession(false);
					
			AdmApplnTO applicantDetail=admForm.getApplicantDetails();
			int year=Integer.parseInt(admForm.getApplicationYear());
			
			
			//raghu validate preferences
			//set first preferencs as orig courseid and check they selected course id or not
			List<CourseTO> list=admForm.getPrefcourses();
			Iterator<CourseTO> itr=list.iterator();
	    	while(itr.hasNext()){
	    		CourseTO courseTO=(CourseTO) itr.next();
	    		if(courseTO.getId()==0){
	    			ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_INVALID);
	    			errors.add(CMSConstants.ADMISSIONFORM_COURSE_INVALID,error);
					saveErrors(request, errors);
					//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
						//return mapping.findForward("OfflineAppBasicPage");
	    		}
	    		if(courseTO.getPrefNo().equalsIgnoreCase("1")){
	    			admForm.setCourseId(courseTO.getId()+"");
	    		}
	    	}
	    	
	    	
	    	
			//checking select course
			if (admForm.getCourseId()==null  || admForm.getCourseId().isEmpty() ) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_INVALID);
					errors.add(CMSConstants.ADMISSIONFORM_COURSE_INVALID,error);
					saveErrors(request, errors);
					//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
			}
			
			
			 
		    //checking course excceded
	       /* if(list.size()>=CMSConstants.MAX_CANDIDATE_PREFERENCES){
	    	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID);
	   		errors.add(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID,error);
	   		saveErrors(request, errors);
	   		//if(admForm.isOnlineApply())
				return mapping.findForward("onlineAppBasicPage");
			//else
				//return mapping.findForward("OfflineAppBasicPage");
	       }*/
	        
	        
	        //checking duplicates
	        List<CourseTO> origList = new ArrayList<CourseTO>();
	        Set<Integer> titles = new HashSet<Integer>();
	        for( CourseTO courseTO : admForm.getPrefcourses() ) {
	            if( titles.add( courseTO.getId())) {
	            	origList.add( courseTO );
	            }
	        }
	        
	        
	        //if duplicates is there send error
	        if(list.size()!=origList.size()){
	        	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSEPREF_DUP_INVALID);
	        	errors.add(CMSConstants.ADMISSIONFORM_COURSEPREF_DUP_INVALID,error);
	       		saveErrors(request, errors);
	       		//if(admForm.isOnlineApply())
	    			return mapping.findForward("onlineAppBasicPage");
	    		//else
	    		//	return mapping.findForward("OfflineAppBasicPage");

	        }

			if(applicantDetail!=null){
				applicantDetail.setAppliedYear(year);
				//if(admForm.isOnlineApply())
					//applicantDetail.setMode("Online");
				//else
					//applicantDetail.setMode("Offline");
			}
			
			if(errors!=null && !errors.isEmpty() )
			{
				saveErrors(request, errors);
				request.setAttribute("operation", "load");
				
					admForm.setDisplayPage("preferences");
					//if(admForm.isOnlineApply()){
						
						return mapping.findForward("onlineAppBasicPage");
					//}else{
						//return mapping.findForward("OfflineAppBasicPage");	
					//}
				
			}
			// validation done
			
			
			
			OnlineApplicationHandler admHandler = OnlineApplicationHandler.getInstance();
			
				//admForm.setReviewWarned(true);
				//admForm.setReviewed("true");
				resetHardCopySubmit(applicantDetail);
				//ActionMessages messages = new ActionMessages();
				//ActionMessage message = new ActionMessage(CMSConstants.APPLICATION_REVIEW_WARN);
				///messages.add("messages", message);
				//saveMessages(request, messages);
				setDocumentForView(admForm, request);
				
				boolean updated=admHandler.createApplicant(applicantDetail,admForm,false,"Draft");
				if(updated){
					try{
						if(admForm.getStudentId() !=0 && request.getSession().getAttribute("PhotoBytes") != null){
							FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_PATH+admForm.getStudentId()+".jpg");
							fos.write((byte[])request.getSession().getAttribute("PhotoBytes"));
							fos.close();
						}
					}catch (Exception f) {
						f.printStackTrace();
					}
					
					admForm.setAutoSave("autoSave");
					if(focusval!=null && !focusval.isEmpty()){
						admForm.setFocusValue(focusval);
					}
					if(admForm.isOnlineApply()){
						setRequiredDataTOForm(admForm,session,request,errors,"CurrentID");
					}else{
						//setRequiredDataTOFormOffline(admForm,session,request,errors,"CurrentID");
					}
					admForm.setDisplayPage("personaldetail");	
					admForm.setSavedDraftAlertMsg(true);
					
					//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
			
			
		}
				
				
				
		}//try close		
		catch (Exception e) {
			e.printStackTrace();
			log.error("error in submit preference page...",e);
			//throw e;
			System.out.println("************************ error details in online admission in submit preference online*************************"+e);
			
		     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		     saveErrors(request, errors);
		     return mapping.findForward("onlineAppBasicPage");
			
		
		}
		log.info("exit submit preference page...");
		//if(admForm.isOnlineApply())
			return mapping.findForward("onlineAppBasicPage");
		//else
		//	return mapping.findForward("OfflineAppBasicPage");
	}

	//submit personal info
	public ActionForward submitPersonalDataPageOnline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit preference page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
			//new
			try{
				
			String focusval=admForm.getFocusValue();
			//ActionMessages errors=new ActionMessages();
			HttpSession session= request.getSession(false);
					
			
			setConfirmationPageDetails(admForm, request);
			//ActionMessages errors=admForm.validate(mapping, request);
			
			
			/* if(admForm.getApplicantDetails().getPersonalData().getAreaType()== 'P' && admForm.getApplicantDetails().getPersonalData().getPancAreaId().equalsIgnoreCase("")){
					
					errors.add(CMSConstants.ADMISSIONFORM_SELECT_PANCHAYAT,new ActionError(CMSConstants.ADMISSIONFORM_SELECT_PANCHAYAT));
				}
	            if(admForm.getApplicantDetails().getPersonalData().getAreaType()== 'M' && (admForm.getApplicantDetails().getPersonalData().getMuncAreaId().equalsIgnoreCase(""))){
					
	            	errors.add(CMSConstants.ADMISSIONFORM_SELECT_MUNICIPALITY,new ActionError(CMSConstants.ADMISSIONFORM_SELECT_MUNICIPALITY));
				}
	            if(admForm.getApplicantDetails().getPersonalData().getAreaType()== 'C' && (admForm.getApplicantDetails().getPersonalData().getCorpAreaId().equalsIgnoreCase(""))){
		
	                errors.add(CMSConstants.ADMISSIONFORM_SELECT_CORPORATION,new ActionError(CMSConstants.ADMISSIONFORM_SELECT_CORPORATION));
	            }

			
			
			//athira
			if(admForm.getApplicantDetails().getPersonalData().isHosteladmission()==true){
				if(admForm.isHostelcheck()==false){
					errors.add(CMSConstants.ADMISSIONFORM_HOSTELCHECK_REQUIRED,new ActionError(CMSConstants.ADMISSIONFORM_HOSTELCHECK_REQUIRED));
				}
				
			}
			*/
			
			//ra
			if( admForm.getApplicantDetails().getPersonalData().getSubReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getSubReligionId()) ){
				if (errors.get(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED)!=null ) {
					errors.add(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED));
				}
			}
			
						
			if(admForm.isSameTempAddr()){
				copyCurrToPermAddress(admForm);
			}
			if(admForm.isSameParentAddr()){
				copyCurrToParentAddress(admForm);
			}
			
			//raghu
			if((admForm.getApplicantDetails().getPersonalData().getPermanentDistricId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentDistricId()))&& (admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED, error);
				}
			}
			
			if((admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()))&& (admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED, error);
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getPermanentDistricId()!=null && admForm.getApplicantDetails().getPersonalData().getPermanentDistricId().equalsIgnoreCase("Other") )
			{
				if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers()==null || admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers().equalsIgnoreCase("") )
					
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED, error);
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()!=null && admForm.getApplicantDetails().getPersonalData().getCurrentDistricId().equalsIgnoreCase("Other") )
			{
				if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()==null || admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers().equalsIgnoreCase("") )
				if (errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED, error);
				}
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData().getParentMob1()==null || admForm.getApplicantDetails().getPersonalData().getParentMob1().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Mobile Code is required."));
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData().getParentMob2()==null || admForm.getApplicantDetails().getPersonalData().getParentMob2().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Mobile Number is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getFatherMobile()==null || admForm.getApplicantDetails().getPersonalData().getFatherMobile().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Father Mobile Number is required."));
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressLine1()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressLine1().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent House no/House name is requirec."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressLine2()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressLine2().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Post Office Name is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentCountryId()==0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Country is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentStateId()==null || admForm.getApplicantDetails().getPersonalData().getParentStateId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent State is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentStateId()!=null && admForm.getApplicantDetails().getPersonalData().getParentStateId().equalsIgnoreCase("Other"))
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressStateOthers()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressStateOthers().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent State is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressZipCode()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressZipCode().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Pin Number required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentCityName()==null || admForm.getApplicantDetails().getPersonalData().getParentCityName().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Ciry required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getMobileNo1()==null || admForm.getApplicantDetails().getPersonalData().getMobileNo1().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Mobile Country Code"));
			}
			if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()==null || admForm.getApplicantDetails().getPersonalData().getMobileNo2().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Mobile No "));
			}
			
			
			
			if(admForm.getApplicantDetails().getPersonalData().getMobileNo1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
					errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
				}
			}
			
			 if(admForm.getApplicantDetails().getPersonalData().getMobileNo1()!=null && !admForm.getApplicantDetails().getPersonalData().getMobileNo1().isEmpty() && 
					 (admForm.getApplicantDetails().getPersonalData().getMobileNo1().length()>6 || admForm.getApplicantDetails().getPersonalData().getMobileNo1().length()<2)){
					errors.add(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED));
				}
			 
			if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) && 
					!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
					errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
				}
			}
			
			 if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()!=null && !admForm.getApplicantDetails().getPersonalData().getMobileNo2().isEmpty() && 
					 (admForm.getApplicantDetails().getPersonalData().getMobileNo2().length()>14 || admForm.getApplicantDetails().getPersonalData().getMobileNo2().length()<9)){
					errors.add(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED));
				}
			 
			 
			 if(admForm.getApplicantDetails().getPersonalData().getParentMob1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentMob1()) && 
					 !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentMob1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				
			 if(admForm.getApplicantDetails().getPersonalData().getParentMob1()!=null && !admForm.getApplicantDetails().getPersonalData().getMobileNo1().isEmpty() && 
					 (admForm.getApplicantDetails().getPersonalData().getMobileNo1().length()>6 || admForm.getApplicantDetails().getPersonalData().getMobileNo1().length()<2)){
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED));
				}
				 
			if(admForm.getApplicantDetails().getPersonalData().getParentMob2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentMob2()) &&
					!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentMob2()) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
							errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
				}
			}
					
			if(admForm.getApplicantDetails().getPersonalData().getParentMob2()!=null && !admForm.getApplicantDetails().getPersonalData().getParentMob2().isEmpty() && 
					(admForm.getApplicantDetails().getPersonalData().getParentMob2().length()>14 || admForm.getApplicantDetails().getPersonalData().getParentMob2().length()<9)){
					errors.add(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED));
				}
					 
			if(admForm.getApplicantDetails().getPersonalData().getFatherMobile()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getFatherMobile()) &&
					!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getFatherMobile()) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
							errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
				}
			}
					
			if(admForm.getApplicantDetails().getPersonalData().getFatherMobile()!=null && !admForm.getApplicantDetails().getPersonalData().getFatherMobile().isEmpty() && 
					(admForm.getApplicantDetails().getPersonalData().getFatherMobile().length()>14 || admForm.getApplicantDetails().getPersonalData().getFatherMobile().length()<9)){
					errors.add(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED));
				}
			
			if(admForm.getApplicantDetails().getPersonalData().getMotherMobile()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMotherMobile()) &&
					!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMotherMobile()) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
							errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
				}
			}
					
			if(admForm.getApplicantDetails().getPersonalData().getMotherMobile()!=null && !admForm.getApplicantDetails().getPersonalData().getMotherMobile().isEmpty() && 
					(admForm.getApplicantDetails().getPersonalData().getMotherMobile().length()>14 || admForm.getApplicantDetails().getPersonalData().getMotherMobile().length()<9)){
					errors.add(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED));
				}
			 
			/**if(admForm.getApplicantDetails().getPersonalData().isNcccertificate()){
				if(admForm.getApplicantDetails().getPersonalData().getNccgrades()!=null && admForm.getApplicantDetails().getPersonalData().getNccgrades().equalsIgnoreCase(""))
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Select NCC Grade"));
		     }
			
			if(admForm.getApplicantDetails().getPersonalData().isNsscertificate()){
				if(admForm.getApplicantDetails().getPersonalData().getNssgrades()!=null && admForm.getApplicantDetails().getPersonalData().getNssgrades().equalsIgnoreCase(""))
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Select NSS Grade"));
		     }
			
			
			if(admForm.getApplicantDetails().getTitleOfFather()==null || admForm.getApplicantDetails().getTitleOfFather().isEmpty()){
				errors.add("knowledgepro.admin.titleOfFather.required",new ActionError("knowledgepro.admin.titleOfFather.required"));
			}
			if(admForm.getApplicantDetails().getTitleOfMother()==null || admForm.getApplicantDetails().getTitleOfMother().isEmpty()){
				errors.add("knowledgepro.admin.titleOfMother.required",new ActionError("knowledgepro.admin.titleOfMother.required"));
			
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode()) )
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Invalid CurrentAddressZipCode"));
			    
			}
			if(admForm.getApplicantDetails().getPersonalData().getCurrentCityName()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentCityName()) && !StringUtils.isAlphaSpace(admForm.getApplicantDetails().getPersonalData().getCurrentCityName()) )
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Invalid CurrentCityName"));
			    
			}
			if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode()) )
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Invalid PermanentAddressZipCode"));
			    
			}
			if(admForm.getApplicantDetails().getPersonalData().getPermanentCityName()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentCityName()) && !StringUtils.isAlphaSpace(admForm.getApplicantDetails().getPersonalData().getPermanentCityName()) )
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Invalid PermanentCityName"));
			    
			}
			if(admForm.getApplicantDetails().getPersonalData().getParentCityName()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentCityName()) && !StringUtils.isAlphaSpace(admForm.getApplicantDetails().getPersonalData().getParentCityName()) )
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Invalid ParentCityName"));
			    
			}
			if(admForm.getApplicantDetails().getPersonalData().getFatherName()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getFatherName()) && !CommonUtil.isAlphaSpaceDot(admForm.getApplicantDetails().getPersonalData().getFatherName()) )
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Invalid FatherName"));
			    
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData().getMotherName()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMotherName()) && !CommonUtil.isAlphaSpaceDot(admForm.getApplicantDetails().getPersonalData().getMotherName()) )
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Invalid MotherName"));
			    
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getBirthPlace()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthPlace()) && !StringUtils.isAlphaSpace(admForm.getApplicantDetails().getPersonalData().getBirthPlace()) )
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Invalid Birth Place"));
			    
			}
			
			/*if(admForm.getApplicantDetails().getPersonalData().getResidence()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getResidence())  )
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Enter Residence"));
			    
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getDistFromCollege()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDistFromCollege())  )
				{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Enter distance"));
			    
			}
			*/
			
			//email comparision
			if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getEmail())){
				if(admForm.getApplicantDetails().getPersonalData().getConfirmEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
						if(!admForm.getApplicantDetails().getPersonalData().getEmail().equals(admForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
							if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
								//errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
							}
						}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
						//errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
					}
				}
			}/*else if(admForm.getApplicantDetails().getPersonalData().getConfirmEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
				if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
				}
			}*/
			
			
			
			// online age range check
			
			if(admForm.getAgeToLimit()!=0 && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())){
				boolean valid=validateOnlineDOB(admForm.getAgeFromLimit(),admForm.getAgeToLimit(),admForm.getApplicantDetails().getPersonalData().getDob());
				if(!valid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS, new ActionError(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS));
					}
				}
		}
			
			
			
			
			validateParentConfirmOnlineRequireds(admForm, errors);
			
			validateOnlineConfirmRequireds(admForm, errors);
			
			validateEditPhone(admForm, errors);
			validateEditParentPhone(admForm, errors);
			validateEditGuardianPhone(admForm, errors);
			//validateEditPassportIfNRI(admForm, errors);
			validateEditOtherOptions(admForm, errors);
			
			validateEditCommAddress(admForm, errors);
			validatePermAddress(admForm, errors);
//			validateSubjectGroups(admForm, errors);
			//if(admForm.isDisplayTCDetails())
			//validateTcDetailsEdit(admForm, errors);
			//if(admForm.isDisplayEntranceDetails())
			//validateEntanceDetailsEdit(admForm, errors);
			if (admForm.getApplicantDetails().getChallanDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getChallanDate())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getChallanDate())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getChallanDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
					}
				}
			}
			// validate Admission Date
			if (admForm.getApplicantDetails().getAdmissionDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getAdmissionDate())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getAdmissionDate())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getAdmissionDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID));
					}
				}
			}
			
			
			if (admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getPersonalData().getDob());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
					}
				}
			}else{
				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
				}
			}
			}
			if(admForm.getApplicantDetails().getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
				
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
					boolean isValid=validatePastDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
						}
					}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
						}
					}
			}
			if(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate())&& !CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate())){
				
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID,new ActionError(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID));
				}
			}
			
			if (admForm.getSubmitDate()!=null && !StringUtils.isEmpty(admForm.getSubmitDate())) {
				if(CommonUtil.isValidDate(admForm.getSubmitDate())){
				boolean	isValid = validatePastDate(admForm.getSubmitDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID));
					}
				}
			}
			if(admForm.getApplicantDetails().getHasWorkExp()==null){
				//errors.add("knowledgepro.admission.online.workExp.yes.no.reqd", new ActionError("knowledgepro.admission.online.workExp.yes.no.reqd"));
			}
			/*int count1=0;
			List<ApplicantWorkExperienceTO> expList=admForm.getApplicantDetails().getWorkExpList();
			if(expList!=null){
				Iterator<ApplicantWorkExperienceTO> toItr=expList.iterator();
				
				while (toItr.hasNext()) {
					ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) toItr	.next();
					validateWorkExperience(expTO, errors);
					if(admForm.getApplicantDetails().getHasWorkExp()){
					 int	count2 =validateWorkExperience1(expTO, errors);
						if(count2!=0){
							count1=count1+1;
						}
					}
				
				}
					if(count1 ==3){
						errors.add(CMSConstants.ERROR,new ActionError("errors.required","Work Experience"));
					}
				
				
				
			}*/
			//validateEditEducationDetails(errors, admForm);
			//validateEditDocumentSize(admForm, errors);
			//validateEditDocumentSizeOnline(admForm, errors,request); //semester marks card required msg is coming. could not replicate the problem. so created another method without that validation
			
			//validate candidate pre-requisite details if exists
			/*if(admForm.getPreRequisiteExists()){
				validatePreRequisteForFinalSubmit(errors,admForm);
			}*/
			
			
			if(admForm.getApplicantDetails().getPersonalData().getTrainingDuration()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getTrainingDuration()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getTrainingDuration())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DURATION_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_DURATION_INVALID));
				}
			}
			
			// validate height and weight
			if(admForm.getApplicantDetails().getPersonalData().getHeight()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getHeight()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getHeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID));
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getWeight()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getWeight()) && !CommonUtil.isValidDecimal(admForm.getApplicantDetails().getPersonalData().getWeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID));
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getAadhaarCardNumber()!=null && !admForm.getApplicantDetails().getPersonalData().getAadhaarCardNumber().isEmpty()) {
				if(admForm.getApplicantDetails().getPersonalData().getAadhaarCardNumber().length() != 12) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.invalidAadharNumber"));
				}
			}
			if(admForm.getApplicantDetails().getPersonalData().getFatherPANNumber()!=null && !admForm.getApplicantDetails().getPersonalData().getFatherPANNumber().isEmpty()) {
				validatePANCardNumber(admForm.getApplicantDetails().getPersonalData().getFatherPANNumber(), errors, "father");
			}
			if(admForm.getApplicantDetails().getPersonalData().getFatherAadhaarNumber()!=null && !admForm.getApplicantDetails().getPersonalData().getFatherAadhaarNumber().isEmpty()) {
				if(admForm.getApplicantDetails().getPersonalData().getFatherAadhaarNumber().length() != 12) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.invalidFatherAadharNumber"));
				}
			}
			if(Integer.parseInt(admForm.getProgramId()) == 9) {
				if(admForm.getApplicantDetails().getPersonalData().getMbaEntranceExamId() == null || admForm.getApplicantDetails().getPersonalData().getMbaEntranceExamId().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.mbaEntranceExam", " is required!"));
				}
				if(admForm.getApplicantDetails().getPersonalData().getEntranceMarksSecured() == null || admForm.getApplicantDetails().getPersonalData().getEntranceMarksSecured().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.mbaEntranceExam.securedMarks", " is required!"));
				}
			}
			/**if(admForm.getApplicantDetails().getPersonalData().getIsmgquota()) {
				if(admForm.getApplicantDetails().getPersonalData().getRecommentedBy() == null || admForm.getApplicantDetails().getPersonalData().getRecommentedBy().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.recommendedByRequired"));
				}
				if(admForm.getApplicantDetails().getPersonalData().getRecommentedPersonDesignation() == null || admForm.getApplicantDetails().getPersonalData().getRecommentedPersonDesignation().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.recommendedPersonDesigRequired"));
				}
				if(admForm.getApplicantDetails().getPersonalData().getRecommentedPersonMobile() == null || admForm.getApplicantDetails().getPersonalData().getRecommentedPersonMobile().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.recommendedPersonMobRequired"));
				}
			}**/
			int prefLength = 0;
			
			//int selectedLen = 0;
			List<Integer> notSelectedPref = new ArrayList<Integer>();
			List<CandidatePreferenceTO> prfList = admForm.getPreferenceList();
			int count = 0;
			if(prfList!= null && prfList.size() > 0){
				Iterator<CandidatePreferenceTO> prItr =  prfList.iterator();
				while (prItr.hasNext()) {
					CandidatePreferenceTO candidatePreferenceTO = (CandidatePreferenceTO) prItr
							.next();
					if(candidatePreferenceTO.getPrefcourses()!= null && candidatePreferenceTO.getPrefcourses().size() > 0){
						prefLength = candidatePreferenceTO.getPrefcourses().size();
						
						if(candidatePreferenceTO.getCoursId()!= null && !candidatePreferenceTO.getCoursId().trim().isEmpty() && candidatePreferenceTO.getPrefNo() > 1){
							//selectedLen++;
						}
						else if (candidatePreferenceTO.getPrefNo() > 1 && count <= prefLength){
							notSelectedPref.add(candidatePreferenceTO.getPrefNo());
						}
					}
					count++;
					
				}
			}
			
			
			if (errors != null && !errors.isEmpty()) {
				resetHardCopySubmit(admForm.getApplicantDetails());
				if(admForm.isReviewWarned()){
					setDocumentForView(admForm, request);	
				}
				saveErrors(request, errors);
				admForm.setReviewWarned(false);
				admForm.setReviewed("false");
				if (admForm.isRemove()) {
					removePhotoDoc(admForm, request);
				}
				//if(admForm.isOnlineApply())
					return mapping.findForward("onlineAppBasicPage");
				//else
					//return mapping.findForward("OfflineAppBasicPage");
				
			}
			//errors check over
			
			AdmApplnTO applicantDetail=admForm.getApplicantDetails();
			int year=Integer.parseInt(admForm.getApplicationYear());
			if(applicantDetail!=null){
				applicantDetail.setAppliedYear(year);
				//if(admForm.isOnlineApply())
					//applicantDetail.setMode("Online");
				//else
				//	applicantDetail.setMode("Offline");
			}
			OnlineApplicationHandler admHandler = OnlineApplicationHandler.getInstance();
			
				//admForm.setReviewWarned(true);
				//admForm.setReviewed("true");
				resetHardCopySubmit(applicantDetail);
				//ActionMessages messages = new ActionMessages();
				//ActionMessage message = new ActionMessage(CMSConstants.APPLICATION_REVIEW_WARN);
				///messages.add("messages", message);
				//saveMessages(request, messages);
				setDocumentForView(admForm, request);
				
				boolean updated=admHandler.createApplicant(applicantDetail,admForm,false,"Draft");
				if(updated){
					try{
						if(admForm.getStudentId() !=0 && request.getSession().getAttribute("PhotoBytes") != null){
							FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_PATH+admForm.getStudentId()+".jpg");
							fos.write((byte[])request.getSession().getAttribute("PhotoBytes"));
							fos.close();
						}
					}catch (Exception f) {
						f.printStackTrace();
					}
					
					admForm.setAutoSave("autoSave");
					if(focusval!=null && !focusval.isEmpty()){
						admForm.setFocusValue(focusval);
					}
					if(admForm.isOnlineApply()){
						setRequiredDataTOForm(admForm,session,request,errors,"CurrentID");
					}else{
						//setRequiredDataTOFormOffline(admForm,session,request,errors,"CurrentID");
					}
					admForm.setDisplayPage("educationaldetail");	
					admForm.setSavedDraftAlertMsg(true);
					//admForm.setDisplayPage("personaldetail");	
					//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
		}
				
				
				
		}//try close		
		catch (Exception e) {
			log.error("error in submit preference page...",e);
			//throw e;
			e.printStackTrace();
			System.out.println("************************ error details in online admission in submit personal info*************************"+e);
			
		     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		     saveErrors(request, errors);
		
		     return mapping.findForward("onlineAppBasicPage");
		
		}
		log.info("exit submit preference page...");
		//if(admForm.isOnlineApply())
			return mapping.findForward("onlineAppBasicPage");
		//else
			//return mapping.findForward("OfflineAppBasicPage");
	}
		
	//submit education online
	public ActionForward submitEducationPageOnline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit preference page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
			//new
			try{
				
			String focusval=admForm.getFocusValue();
			//ActionMessages errors=new ActionMessages();
			HttpSession session= request.getSession(false);
		
			
			errors=validateEditEducationDetails(errors, admForm);
			
			if(Integer.parseInt(admForm.getProgramTypeId())==1){
				if((admForm.getApplicantDetails().getPersonalData().getSecondLanguage()!=null && StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getSecondLanguage())) )
				if (errors.get("knowledgepro.admin._Exam_Second_Language_Master.required") != null&& !errors.get("knowledgepro.admin._Exam_Second_Language_Master.required").hasNext()) {
					errors.add("knowledgepro.admin._Exam_Second_Language_Master.required",new ActionError("knowledgepro.admin._Exam_Second_Language_Master.required"));
				}	
			}
			
			if(Integer.parseInt(admForm.getProgramTypeId())==2)
				if((admForm.getApplicantDetails().getPersonalData().getUgcourse()!=null && StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getUgcourse())) || (!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getUgcourse()))){
	                errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Ug Course "));
				}
			
			if(Integer.parseInt(admForm.getProgramTypeId())==1)
				if((admForm.getApplicantDetails().getPersonalData().getStream()!=null && StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getStream())) || (!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getStream()))){
	                errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Stream under Class 12 "));
				}
			
			
			    if(admForm.isDisplayTCDetails())
				validateTcDetailsEdit(admForm, errors);
			    
				if(admForm.isDisplayEntranceDetails())
				validateEntanceDetailsEdit(admForm, errors);
			
			
			
			
			if (errors != null && !errors.isEmpty()) {
			
				saveErrors(request, errors);
			
				//if(admForm.isOnlineApply())
					return mapping.findForward("onlineAppBasicPage");
				//else
				//	return mapping.findForward("OfflineAppBasicPage");
				
			}

			AdmApplnTO applicantDetail=admForm.getApplicantDetails();
			int year=Integer.parseInt(admForm.getApplicationYear());
			if(applicantDetail!=null){
				applicantDetail.setAppliedYear(year);
				//if(admForm.isOnlineApply())
					//applicantDetail.setMode("Online");
				//else
				//	applicantDetail.setMode("Offline");
			}
			OnlineApplicationHandler admHandler = OnlineApplicationHandler.getInstance();
			
				//admForm.setReviewWarned(true);
				//admForm.setReviewed("true");
				resetHardCopySubmit(applicantDetail);
				//ActionMessages messages = new ActionMessages();
				//ActionMessage message = new ActionMessage(CMSConstants.APPLICATION_REVIEW_WARN);
				///messages.add("messages", message);
				//saveMessages(request, messages);
				setDocumentForView(admForm, request);
				
				boolean updated=admHandler.createApplicant(applicantDetail,admForm,false,"Draft");
				if(updated){
					try{
						if(admForm.getStudentId() !=0 && request.getSession().getAttribute("PhotoBytes") != null){
							FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_PATH+admForm.getStudentId()+".jpg");
							fos.write((byte[])request.getSession().getAttribute("PhotoBytes"));
							fos.close();
						}
					}catch (Exception f) {
						f.printStackTrace();
					}
					
					admForm.setAutoSave("autoSave");
					if(focusval!=null && !focusval.isEmpty()){
						admForm.setFocusValue(focusval);
					}
					if(admForm.isOnlineApply()){
						setRequiredDataTOForm(admForm,session,request,errors,"CurrentID");
					}else{
						//setRequiredDataTOFormOffline(admForm,session,request,errors,"CurrentID");
					}
						
					admForm.setSavedDraftAlertMsg(true);
					admForm.setDisplayPage("attachment");
					//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
			
			
		}
				
				
				
		}//try close		
		catch (Exception e) {
			log.error("error in submit preference page...",e);
			//throw e;
			System.out.println("************************ error details in online admission submit education info*************************"+e);
			
		     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		     saveErrors(request, errors);
		
		     return mapping.findForward("onlineAppBasicPage");
		
		}
		log.info("exit submit preference page...");
		//if(admForm.isOnlineApply())
			return mapping.findForward("onlineAppBasicPage");
		//else
		//	return mapping.findForward("OfflineAppBasicPage");
	}

	//submit documents photo
	public ActionForward submitAttachMentPageOnline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit preference page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
			//new
			try{
				
			String focusval=admForm.getFocusValue();
			
			HttpSession session= request.getSession(false);
			if(admForm.getApplicantDetails().getPersonalData().getPreferenceNoCAP() == null || admForm.getApplicantDetails().getPersonalData().getPreferenceNoCAP().isEmpty()) {
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.capPreferenceNumber.required"));
			}
				
			validateEditDocumentSizeOnline(admForm, errors,request); //semester marks card required msg is coming. could not replicate the problem. so created another method without that validation
			
			
			if (errors != null && !errors.isEmpty()) {
				resetHardCopySubmit(admForm.getApplicantDetails());
				if(admForm.isReviewWarned()){
					setDocumentForView(admForm, request);	
				}
				saveErrors(request, errors);
				admForm.setReviewWarned(false);
				admForm.setReviewed("false");
				if (admForm.isRemove()) {
					removePhotoDoc(admForm, request);
				}
				//if(admForm.isOnlineApply())
					return mapping.findForward("onlineAppBasicPage");
				//else
				//	return mapping.findForward("OfflineAppBasicPage");
				
			}

			
			//errors check over
			
			
			
			
			AdmApplnTO applicantDetail=admForm.getApplicantDetails();
			int year=Integer.parseInt(admForm.getApplicationYear());
			if(applicantDetail!=null){
				applicantDetail.setAppliedYear(year);
				//if(admForm.isOnlineApply())
					//applicantDetail.setMode("Online");
				//else
				//	applicantDetail.setMode("Offline");
			}
			OnlineApplicationHandler admHandler = OnlineApplicationHandler.getInstance();
			
				//admForm.setReviewWarned(true);
				//admForm.setReviewed("true");
				resetHardCopySubmit(applicantDetail);
				//ActionMessages messages = new ActionMessages();
				//ActionMessage message = new ActionMessage(CMSConstants.APPLICATION_REVIEW_WARN);
				///messages.add("messages", message);
				//saveMessages(request, messages);
				setDocumentForView(admForm, request);
				
				boolean updated=admHandler.createApplicant(applicantDetail,admForm,false,"Draft");
				if(updated){
					try{
						if(admForm.getStudentId() !=0 && request.getSession().getAttribute("PhotoBytes") != null){
							FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_PATH+admForm.getStudentId()+".jpg");
							fos.write((byte[])request.getSession().getAttribute("PhotoBytes"));
							fos.close();
						}
					}catch (Exception f) {
						f.printStackTrace();
					}
					
					admForm.setAutoSave("autoSave");
					if(focusval!=null && !focusval.isEmpty()){
						admForm.setFocusValue(focusval);
					}
					if(admForm.isOnlineApply()){
						setRequiredDataTOForm(admForm,session,request,errors,"CurrentID");
					}else{
						//setRequiredDataTOFormOffline(admForm,session,request,errors,"CurrentID");
					}
					admForm.setDisplayPage("confirmPage");	
					admForm.setSavedDraftAlertMsg(true);
					
					//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
			//calling login page
					//return mapping.findForward("logoutFromOnlineApplication");
		}
				
				
				
		}//try close		
		catch (Exception e) {
			
			log.error("error in submit preference page...",e);
				//throw e;
			System.out.println("************************ error details in online admission in submit documents photo*************************"+e);
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
				return mapping.findForward("onlineAppBasicPage");
		
		}
		log.info("exit submit preference page...");
		//if(admForm.isOnlineApply())
			return mapping.findForward("onlineAppBasicPage");
		//else
		//	return mapping.findForward("OfflineAppBasicPage");
		
	}

	
	//submit save in confirm page
	public ActionForward submitConfirmPageOnline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit preference page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors = new ActionMessages();
		

			//new
			try{
				
			String focusval=admForm.getFocusValue();
			//ActionMessages errors=new ActionMessages();
			//HttpSession session= request.getSession(false);
					
			
			
			setConfirmationPageDetails(admForm, request);
			//ActionMessages errors=admForm.validate(mapping, request);
			
			
			
			if(admForm.isDisplaySecondLanguage() && (admForm.getApplicantDetails().getPersonalData().getSecondLanguage() == null || admForm.getApplicantDetails().getPersonalData().getSecondLanguage().trim().isEmpty())){
				if (errors.get("knowledgepro.admin._Exam_Second_Language_Master.required") != null&& !errors.get("knowledgepro.admin._Exam_Second_Language_Master.required").hasNext()) {
					errors.add("knowledgepro.admin._Exam_Second_Language_Master.required",new ActionError("knowledgepro.admin._Exam_Second_Language_Master.required"));
				}	
			}
			
			//ra
			if( admForm.getApplicantDetails().getPersonalData().getSubReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getSubReligionId()) ){
				if (errors.get(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED)!=null ) {
					errors.add(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED));
				}
			}
			
						
			if(admForm.isSameTempAddr()){
				copyCurrToPermAddress(admForm);
			}
			
			//raghu
			if((admForm.getApplicantDetails().getPersonalData().getPermanentDistricId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentDistricId()))&& (admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED, error);
				}
			}
			
			if((admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()))&& (admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED, error);
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getPermanentDistricId()!=null && admForm.getApplicantDetails().getPersonalData().getPermanentDistricId().equalsIgnoreCase("Other") )
			{
				if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers()==null || admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers().equalsIgnoreCase("") )
					
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED, error);
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()!=null && admForm.getApplicantDetails().getPersonalData().getCurrentDistricId().equalsIgnoreCase("Other") )
			{
				if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()==null || admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers().equalsIgnoreCase("") )
				if (errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED, error);
				}
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData().getParentMob1()==null || admForm.getApplicantDetails().getPersonalData().getParentMob1().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Mobile Code is required."));
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData().getParentMob2()==null || admForm.getApplicantDetails().getPersonalData().getParentMob2().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Mobile Number is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getFatherMobile()==null || admForm.getApplicantDetails().getPersonalData().getFatherMobile().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Father Mobile Number is required."));
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressLine1()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressLine1().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent House no/House name is requirec."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressLine2()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressLine2().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Post Office Name is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentCountryId()==0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Country is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentStateId()==null || admForm.getApplicantDetails().getPersonalData().getParentStateId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent State is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentStateId()!=null && admForm.getApplicantDetails().getPersonalData().getParentStateId().equalsIgnoreCase("Other"))
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressStateOthers()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressStateOthers().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent State is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressZipCode()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressZipCode().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Pin Number required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentCityName()==null || admForm.getApplicantDetails().getPersonalData().getParentCityName().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent City required."));
			}
			
			if(Integer.parseInt(admForm.getProgramTypeId())==2)
			if((admForm.getApplicantDetails().getPersonalData().getUgcourse()!=null && StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getUgcourse())) || (!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getUgcourse()))){
                errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Ug Course "));
			}
			
						
			
			validateOnlineConfirmRequireds(admForm, errors);
			
			if(admForm.getApplicantDetails().getTitleOfFather()==null || admForm.getApplicantDetails().getTitleOfFather().isEmpty()){
				errors.add("knowledgepro.admin.titleOfFather.required",new ActionError("knowledgepro.admin.titleOfFather.required"));
			}
			if(admForm.getApplicantDetails().getTitleOfMother()==null || admForm.getApplicantDetails().getTitleOfMother().isEmpty()){
				errors.add("knowledgepro.admin.titleOfMother.required",new ActionError("knowledgepro.admin.titleOfMother.required"));
			
			}
			validateParentConfirmOnlineRequireds(admForm, errors);
			
			//email comparision
			if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getEmail())){
				if(admForm.getApplicantDetails().getPersonalData().getConfirmEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
						if(!admForm.getApplicantDetails().getPersonalData().getEmail().equals(admForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
							if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
								errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
							}
						}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
					}
				}
			}/*else if(admForm.getApplicantDetails().getPersonalData().getConfirmEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
				if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
				}
			}*/
			
			if(admForm.getApplicantDetails().getPersonalData().getMobileNo1()==null || admForm.getApplicantDetails().getPersonalData().getMobileNo1().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Mobile Country Code"));
			}
			if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()==null || admForm.getApplicantDetails().getPersonalData().getMobileNo2().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Mobile No "));
			}
			
			// online age range check
			
			if(admForm.getAgeToLimit()!=0 && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())){
				boolean valid=validateOnlineDOB(admForm.getAgeFromLimit(),admForm.getAgeToLimit(),admForm.getApplicantDetails().getPersonalData().getDob());
				if(!valid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS, new ActionError(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS));
					}
				}
		}
			
			
			
			//raghu validate preferences
			//set first preferencs as orig courseid and check they selected course id or not
			List<CourseTO> list=admForm.getPrefcourses();
			Iterator<CourseTO> itr=list.iterator();
	    	while(itr.hasNext()){
	    		CourseTO courseTO=(CourseTO) itr.next();
	    		if(courseTO.getId()==0){
	    			ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_INVALID);
	    			errors.add(CMSConstants.ADMISSIONFORM_COURSE_INVALID,error);
					saveErrors(request, errors);
					//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
	    		}
	    		if(courseTO.getPrefNo().equalsIgnoreCase("1")){
	    			admForm.setCourseId(courseTO.getId()+"");
	    		}
	    	}
	    	
	    	
	    	
			//checking select course
			if (admForm.getCourseId()==null  || admForm.getCourseId().isEmpty() ) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_INVALID);
					errors.add(CMSConstants.ADMISSIONFORM_COURSE_INVALID,error);
					saveErrors(request, errors);
				//	if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
				//	else
					//	return mapping.findForward("OfflineAppBasicPage");
			}
			
			
			 
		    //checking course excceded
	       /* if(list.size()>=CMSConstants.MAX_CANDIDATE_PREFERENCES){
	    	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID);
	   		errors.add(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID,error);
	   		saveErrors(request, errors);
	   	//	if(admForm.isOnlineApply())
				return mapping.findForward("onlineAppBasicPage");
			//else
				//return mapping.findForward("OfflineAppBasicPage");
	       }*/
	        
	        
	        //checking duplicates
	        List<CourseTO> origList = new ArrayList<CourseTO>();
	        Set<Integer> titles = new HashSet<Integer>();
	        for( CourseTO courseTO : admForm.getPrefcourses() ) {
	            if( titles.add( courseTO.getId())) {
	            	origList.add( courseTO );
	            }
	        }
	        
	        
	        //if duplicates is there send error
	        if(list.size()!=origList.size()){
	        	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSEPREF_DUP_INVALID);
	        	errors.add(CMSConstants.ADMISSIONFORM_COURSEPREF_DUP_INVALID,error);
	       		saveErrors(request, errors);
	       		//if(admForm.isOnlineApply())
	    			return mapping.findForward("onlineAppBasicPage");
	    		//else
	    		//	return mapping.findForward("OfflineAppBasicPage");

	        }

	      //this check for only maenglish
			if (admForm.getCourseId()!=null && admForm.getCourseId().equalsIgnoreCase("18") )
			if(admForm.getApplicantDetails().getPersonalData().getNoofenglishCoreCourses()==null || admForm.getApplicantDetails().getPersonalData().getNoofenglishCoreCourses().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","No of English Core Courses required."));
			}
			
			validateEditPhone(admForm, errors);
			validateEditParentPhone(admForm, errors);
			validateEditGuardianPhone(admForm, errors);
			//validateEditPassportIfNRI(admForm, errors);
			validateEditOtherOptions(admForm, errors);
			
			validateEditCommAddress(admForm, errors);
			validatePermAddress(admForm, errors);
//			validateSubjectGroups(admForm, errors);
			if(admForm.isDisplayTCDetails())
			validateTcDetailsEdit(admForm, errors);
			if(admForm.isDisplayEntranceDetails())
			validateEntanceDetailsEdit(admForm, errors);
			if (admForm.getApplicantDetails().getChallanDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getChallanDate())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getChallanDate())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getChallanDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
					}
				}
			}
			// validate Admission Date
			if (admForm.getApplicantDetails().getAdmissionDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getAdmissionDate())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getAdmissionDate())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getAdmissionDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID));
					}
				}
			}
			
			
			if (admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getPersonalData().getDob());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
					}
				}
			}else{
				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
				}
			}
			}
			if(admForm.getApplicantDetails().getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
				
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
					boolean isValid=validatePastDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
						}
					}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
						}
					}
			}
			if(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate())&& !CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate())){
				
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID,new ActionError(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID));
				}
			}
			
			if (admForm.getSubmitDate()!=null && !StringUtils.isEmpty(admForm.getSubmitDate())) {
				if(CommonUtil.isValidDate(admForm.getSubmitDate())){
				boolean	isValid = validatePastDate(admForm.getSubmitDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID));
					}
				}
			}
			if(admForm.getApplicantDetails().getHasWorkExp()==null){
				//errors.add("knowledgepro.admission.online.workExp.yes.no.reqd", new ActionError("knowledgepro.admission.online.workExp.yes.no.reqd"));
			}
			/*int count1=0;
			List<ApplicantWorkExperienceTO> expList=admForm.getApplicantDetails().getWorkExpList();
			if(expList!=null){
				Iterator<ApplicantWorkExperienceTO> toItr=expList.iterator();
				
				while (toItr.hasNext()) {
					ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) toItr	.next();
					validateWorkExperience(expTO, errors);
					if(admForm.getApplicantDetails().getHasWorkExp()){
					 int	count2 =validateWorkExperience1(expTO, errors);
						if(count2!=0){
							count1=count1+1;
						}
					}
				
				}
					if(count1 ==3){
						errors.add(CMSConstants.ERROR,new ActionError("errors.required","Work Experience"));
					}
				
				
				
			}*/
			//errors=validateEditEducationDetails(errors, admForm);
			//validateEditDocumentSize(admForm, errors);
			validateEditDocumentSizeOnline(admForm, errors,request); //semester marks card required msg is coming. could not replicate the problem. so created another method without that validation
			
			//validate candidate pre-requisite details if exists
			/*if(admForm.getPreRequisiteExists()){
				validatePreRequisteForFinalSubmit(errors,admForm);
			}*/
			
			
			if(admForm.getApplicantDetails().getPersonalData().getTrainingDuration()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getTrainingDuration()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getTrainingDuration())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DURATION_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_DURATION_INVALID));
				}
			}
			
			// validate height and weight
			if(admForm.getApplicantDetails().getPersonalData().getHeight()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getHeight()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getHeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID));
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getWeight()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getWeight()) && !CommonUtil.isValidDecimal(admForm.getApplicantDetails().getPersonalData().getWeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID));
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getAadhaarCardNumber()!=null && !admForm.getApplicantDetails().getPersonalData().getAadhaarCardNumber().isEmpty()) {
				if(admForm.getApplicantDetails().getPersonalData().getAadhaarCardNumber().length() != 12) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.invalidAadharNumber"));
				}
			}
			if(admForm.getApplicantDetails().getPersonalData().getFatherPANNumber()!=null && !admForm.getApplicantDetails().getPersonalData().getFatherPANNumber().isEmpty()) {
				validatePANCardNumber(admForm.getApplicantDetails().getPersonalData().getFatherPANNumber(), errors, "father");
			}
			if(admForm.getApplicantDetails().getPersonalData().getFatherAadhaarNumber()!=null && !admForm.getApplicantDetails().getPersonalData().getFatherAadhaarNumber().isEmpty()) {
				if(admForm.getApplicantDetails().getPersonalData().getFatherAadhaarNumber().length() != 12) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.invalidFatherAadharNumber"));
				}
			}

			if(admForm.getApplicantDetails().getPersonalData().getIsmgquota()) {
				/*if(admForm.getApplicantDetails().getPersonalData().getRecommentedBy() == null || admForm.getApplicantDetails().getPersonalData().getRecommentedBy().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.recommendedByRequired"));
				}
				if(admForm.getApplicantDetails().getPersonalData().getRecommentedPersonDesignation() == null || admForm.getApplicantDetails().getPersonalData().getRecommentedPersonDesignation().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.recommendedPersonDesigRequired"));
				}
				if(admForm.getApplicantDetails().getPersonalData().getRecommentedPersonMobile() == null || admForm.getApplicantDetails().getPersonalData().getRecommentedPersonMobile().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.recommendedPersonMobRequired"));
				}*/
				/*if(admForm.getApplicantDetails().getPersonalData().getRecommendDeatails()== null || admForm.getApplicantDetails().getPersonalData().getRecommendDeatails().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.recommendedByRequired"));
				}*/
			}
			if (errors != null && !errors.isEmpty()) {
				resetHardCopySubmit(admForm.getApplicantDetails());
				if(admForm.isReviewWarned()){
					setDocumentForView(admForm, request);	
				}
				saveErrors(request, errors);
				admForm.setReviewWarned(false);
				admForm.setReviewed("false");
				if (admForm.isRemove()) {
					removePhotoDoc(admForm, request);
				}
				//if(admForm.isOnlineApply())
					return mapping.findForward("onlineAppBasicPage");
				//else
				//	return mapping.findForward("OfflineAppBasicPage");
				
			}

			
			//errors check over
			
			
			
			
			
			
			AdmApplnTO applicantDetail=admForm.getApplicantDetails();
			int year=Integer.parseInt(admForm.getApplicationYear());
			if(applicantDetail!=null){
				applicantDetail.setAppliedYear(year);
				//if(admForm.isOnlineApply())
					//applicantDetail.setMode("Online");
				//else
				//	applicantDetail.setMode("Offline");
			}
			OnlineApplicationHandler admHandler = OnlineApplicationHandler.getInstance();
			
				//admForm.setReviewWarned(true);
				//admForm.setReviewed("true");
				resetHardCopySubmit(applicantDetail);
				//ActionMessages messages = new ActionMessages();
				//ActionMessage message = new ActionMessage(CMSConstants.APPLICATION_REVIEW_WARN);
				///messages.add("messages", message);
				//saveMessages(request, messages);
				setDocumentForView(admForm, request);
				boolean updated=admHandler.saveCompleteApplicationGenerateNo(admForm, errors);
				
				if(updated){
					try{
						if(admForm.getStudentId() !=0 && request.getSession().getAttribute("PhotoBytes") != null){
							FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_PATH+admForm.getStudentId()+".jpg");
							fos.write((byte[])request.getSession().getAttribute("PhotoBytes"));
							fos.close();
						}
					}catch (Exception f) {
						f.printStackTrace();
					}
					admForm.setDisplayPage("");
					admForm.setAutoSave("");
					if(focusval!=null && !focusval.isEmpty()){
						admForm.setFocusValue(focusval);
					}
					//if(admForm.isOnlineApply()){
					//	setRequiredDataTOForm(admForm,session,request,errors,"CurrentID");
					//}else{
						//setRequiredDataTOFormOffline(admForm,session,request,errors,"CurrentID");
					//}
						
					admForm.setSavedDraftAlertMsg(true);
					HttpSession session=request.getSession(false);
					cleanupSessionData(session);
					//clean up data of form
					cleanUpPageData(admForm);
					admForm=null;
					//call garbage collector forcly to clean data
					System.gc();
					
					OnlineApplicationForm newForm =new OnlineApplicationForm();
					newForm.setMethod("logoutFromOnlineApplication");
					newForm.setUserId("9999");
					session.invalidate();
					//calling login page
					return mapping.findForward("logoutFromOnlineApplication");
			
			
		}
				
				saveErrors(request, errors);
				admForm.setDisplayPage("confirmPage");		
				
		}//try close		
		catch (Exception e) {
			log.error("error in submit preference page...",e);
			//throw e;
			System.out.println("************************ error details in online admission in submit confirn page for save*************************"+e);
			
		     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		     saveErrors(request, errors);
		     return mapping.findForward("onlineAppBasicPage");
			
		
		}
		log.info("exit submit preference page...");
		//if(admForm.isOnlineApply())
			return mapping.findForward("onlineAppBasicPage");
		//else
			//return mapping.findForward("OfflineAppBasicPage");
	}

	
	//submit complete application no more edit
	public ActionForward submitCompleteApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit preference page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
			//new
		ActionMessages errors=new ActionMessages();
			try{
				
			String focusval=admForm.getFocusValue();
			
			//HttpSession session= request.getSession(false);
				
			
			
			setConfirmationPageDetails(admForm, request);
			//ActionMessages errors=admForm.validate(mapping, request);
			
			
			
			if(admForm.isDisplaySecondLanguage() && (admForm.getApplicantDetails().getPersonalData().getSecondLanguage() == null || admForm.getApplicantDetails().getPersonalData().getSecondLanguage().trim().isEmpty())){
				if (errors.get("knowledgepro.admin._Exam_Second_Language_Master.required") != null&& !errors.get("knowledgepro.admin._Exam_Second_Language_Master.required").hasNext()) {
					errors.add("knowledgepro.admin._Exam_Second_Language_Master.required",new ActionError("knowledgepro.admin._Exam_Second_Language_Master.required"));
				}	
			}
			
			//ra
			if( admForm.getApplicantDetails().getPersonalData().getSubReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getSubReligionId()) ){
				if (errors.get(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED)!=null ) {
					errors.add(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED));
				}
			}
			
						
			if(admForm.isSameTempAddr()){
				copyCurrToPermAddress(admForm);
			}
			
			//raghu
			if((admForm.getApplicantDetails().getPersonalData().getPermanentDistricId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentDistricId()))&& (admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED, error);
				}
			}
			
			if((admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()))&& (admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED, error);
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getPermanentDistricId()!=null && admForm.getApplicantDetails().getPersonalData().getPermanentDistricId().equalsIgnoreCase("Other") )
			{
				if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers()==null || admForm.getApplicantDetails().getPersonalData().getPermanentAddressDistrictOthers().equalsIgnoreCase("") )
					
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED, error);
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()!=null && admForm.getApplicantDetails().getPersonalData().getCurrentDistricId().equalsIgnoreCase("Other") )
			{
				if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()==null || admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers().equalsIgnoreCase("") )
				if (errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED, error);
				}
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData().getParentMob1()==null || admForm.getApplicantDetails().getPersonalData().getParentMob1().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Mobile Code is required."));
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData().getParentMob2()==null || admForm.getApplicantDetails().getPersonalData().getParentMob2().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Mobile Number is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getFatherMobile()==null || admForm.getApplicantDetails().getPersonalData().getFatherMobile().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Father Mobile Number is required."));
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressLine1()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressLine1().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent House no/House name is requirec."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressLine2()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressLine2().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Post Office Name is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentCountryId()==0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Country is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentStateId()==null || admForm.getApplicantDetails().getPersonalData().getParentStateId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent State is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentStateId()!=null && admForm.getApplicantDetails().getPersonalData().getParentStateId().equalsIgnoreCase("Other"))
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressStateOthers()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressStateOthers().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent State is required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressZipCode()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressZipCode().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent Pin Number required."));
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getParentCityName()==null || admForm.getApplicantDetails().getPersonalData().getParentCityName().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Parent City required."));
			}
			
			if(Integer.parseInt(admForm.getProgramTypeId())==2)
			if((admForm.getApplicantDetails().getPersonalData().getUgcourse()!=null && StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getUgcourse())) || (!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getUgcourse()))){
                errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Ug Course "));
			}
			
			
			validateOnlineConfirmRequireds(admForm, errors);
			
			if(admForm.getApplicantDetails().getTitleOfFather()==null || admForm.getApplicantDetails().getTitleOfFather().isEmpty()){
				errors.add("knowledgepro.admin.titleOfFather.required",new ActionError("knowledgepro.admin.titleOfFather.required"));
			}
			if(admForm.getApplicantDetails().getTitleOfMother()==null || admForm.getApplicantDetails().getTitleOfMother().isEmpty()){
				errors.add("knowledgepro.admin.titleOfMother.required",new ActionError("knowledgepro.admin.titleOfMother.required"));
			
			}
			validateParentConfirmOnlineRequireds(admForm, errors);
			
			//email comparision
			if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getEmail())){
				if(admForm.getApplicantDetails().getPersonalData().getConfirmEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
						if(!admForm.getApplicantDetails().getPersonalData().getEmail().equals(admForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
							if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
								errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
							}
						}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
					}
				}
			}/*else if(admForm.getApplicantDetails().getPersonalData().getConfirmEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
				if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
				}
			}*/
			
			if(admForm.getApplicantDetails().getPersonalData().getMobileNo1()==null || admForm.getApplicantDetails().getPersonalData().getMobileNo1().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Mobile Country Code"));
			}
			if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()==null || admForm.getApplicantDetails().getPersonalData().getMobileNo2().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Mobile No "));
			}
			
			// online age range check
			
			if(admForm.getAgeToLimit()!=0 && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())){
				boolean valid=validateOnlineDOB(admForm.getAgeFromLimit(),admForm.getAgeToLimit(),admForm.getApplicantDetails().getPersonalData().getDob());
				if(!valid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS, new ActionError(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS));
					}
				}
		}
			
			
			
			//raghu validate preferences
			//set first preferencs as orig courseid and check they selected course id or not
			List<CourseTO> list=admForm.getPrefcourses();
			Iterator<CourseTO> itr=list.iterator();
	    	while(itr.hasNext()){
	    		CourseTO courseTO=(CourseTO) itr.next();
	    		if(courseTO.getId()==0){
	    			ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_INVALID);
	    			errors.add(CMSConstants.ADMISSIONFORM_COURSE_INVALID,error);
					saveErrors(request, errors);
				//	if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
				//	else
					//	return mapping.findForward("OfflineAppBasicPage");
	    		}
	    		if(courseTO.getPrefNo().equalsIgnoreCase("1")){
	    			admForm.setCourseId(courseTO.getId()+"");
	    		}
	    	}
	    	
	    	
	    	
			//checking select course
			if (admForm.getCourseId()==null  || admForm.getCourseId().isEmpty() ) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_INVALID);
					errors.add(CMSConstants.ADMISSIONFORM_COURSE_INVALID,error);
					saveErrors(request, errors);
					//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
			}
			
			
			 
		    //checking course excceded
	       /* if(list.size()>=CMSConstants.MAX_CANDIDATE_PREFERENCES){
	    	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID);
	   		errors.add(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID,error);
	   		saveErrors(request, errors);
	   		//if(admForm.isOnlineApply())
				return mapping.findForward("onlineAppBasicPage");
			//else
			//	return mapping.findForward("OfflineAppBasicPage");
	       }*/
	        
	        
	        //checking duplicates
	        List<CourseTO> origList = new ArrayList<CourseTO>();
	        Set<Integer> titles = new HashSet<Integer>();
	        for( CourseTO courseTO : admForm.getPrefcourses() ) {
	            if( titles.add( courseTO.getId())) {
	            	origList.add( courseTO );
	            }
	        }
	        
	        
	        //if duplicates is there send error
	        if(list.size()!=origList.size()){
	        	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSEPREF_DUP_INVALID);
	        	errors.add(CMSConstants.ADMISSIONFORM_COURSEPREF_DUP_INVALID,error);
	       		saveErrors(request, errors);
	       		//if(admForm.isOnlineApply())
	    			return mapping.findForward("onlineAppBasicPage");
	    		//else
	    		//	return mapping.findForward("OfflineAppBasicPage");

	        }

			validateEditPhone(admForm, errors);
			validateEditParentPhone(admForm, errors);
			validateEditGuardianPhone(admForm, errors);
			//validateEditPassportIfNRI(admForm, errors);
			validateEditOtherOptions(admForm, errors);
			
			validateEditCommAddress(admForm, errors);
			validatePermAddress(admForm, errors);
//			validateSubjectGroups(admForm, errors);
			if(admForm.isDisplayTCDetails())
			validateTcDetailsEdit(admForm, errors);
			if(admForm.isDisplayEntranceDetails())
			validateEntanceDetailsEdit(admForm, errors);
			if (admForm.getApplicantDetails().getChallanDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getChallanDate())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getChallanDate())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getChallanDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
					}
				}
			}
			// validate Admission Date
			if (admForm.getApplicantDetails().getAdmissionDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getAdmissionDate())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getAdmissionDate())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getAdmissionDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID));
					}
				}
			}
			
			
			if (admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getPersonalData().getDob());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
					}
				}
			}else{
				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
				}
			}
			}
			if(admForm.getApplicantDetails().getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
				
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
					boolean isValid=validatePastDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
						}
					}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
						}
					}
			}
			if(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate())&& !CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate())){
				
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID,new ActionError(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID));
				}
			}
			
			if (admForm.getSubmitDate()!=null && !StringUtils.isEmpty(admForm.getSubmitDate())) {
				if(CommonUtil.isValidDate(admForm.getSubmitDate())){
				boolean	isValid = validatePastDate(admForm.getSubmitDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID));
					}
				}
			}
			if(admForm.getApplicantDetails().getHasWorkExp()==null){
				//errors.add("knowledgepro.admission.online.workExp.yes.no.reqd", new ActionError("knowledgepro.admission.online.workExp.yes.no.reqd"));
			}
			/*int count1=0;
			List<ApplicantWorkExperienceTO> expList=admForm.getApplicantDetails().getWorkExpList();
			if(expList!=null){
				Iterator<ApplicantWorkExperienceTO> toItr=expList.iterator();
				
				while (toItr.hasNext()) {
					ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) toItr	.next();
					validateWorkExperience(expTO, errors);
					if(admForm.getApplicantDetails().getHasWorkExp()){
					 int	count2 =validateWorkExperience1(expTO, errors);
						if(count2!=0){
							count1=count1+1;
						}
					}
				
				}
					if(count1 ==3){
						errors.add(CMSConstants.ERROR,new ActionError("errors.required","Work Experience"));
					}
				
				
				
			}*/
			//errors=validateEditEducationDetails(errors, admForm);
			//validateEditDocumentSize(admForm, errors);
			validateEditDocumentSizeOnline(admForm, errors,request); //semester marks card required msg is coming. could not replicate the problem. so created another method without that validation
			
			//validate candidate pre-requisite details if exists
			/*if(admForm.getPreRequisiteExists()){
				validatePreRequisteForFinalSubmit(errors,admForm);
			}*/
			
			
			if(admForm.getApplicantDetails().getPersonalData().getTrainingDuration()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getTrainingDuration()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getTrainingDuration())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DURATION_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_DURATION_INVALID));
				}
			}
			
			// validate height and weight
			if(admForm.getApplicantDetails().getPersonalData().getHeight()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getHeight()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getHeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID));
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getWeight()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getWeight()) && !CommonUtil.isValidDecimal(admForm.getApplicantDetails().getPersonalData().getWeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID));
				}
			}
			/*if(Integer.parseInt(admForm.getProgramTypeId()) == 2) {
				if(admForm.getApplicantDetails().getPersonalData().getPreferenceNoCAP() == null || admForm.getApplicantDetails().getPersonalData().getPreferenceNoCAP().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.capPreferenceNumber.required"));
				}
			}*/
			if(Integer.parseInt(admForm.getProgramId()) == 9) {
				if(admForm.getApplicantDetails().getPersonalData().getMbaEntranceExamId() == null || admForm.getApplicantDetails().getPersonalData().getMbaEntranceExamId().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.mbaEntranceExam", " is required!"));
				}
				if(admForm.getApplicantDetails().getPersonalData().getEntranceMarksSecured() == null || admForm.getApplicantDetails().getPersonalData().getEntranceMarksSecured().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.mbaEntranceExam.securedMarks", " is required!"));
				}
			}
			
			if (errors != null && !errors.isEmpty()) {
				resetHardCopySubmit(admForm.getApplicantDetails());
				if(admForm.isReviewWarned()){
					setDocumentForView(admForm, request);	
				}
				saveErrors(request, errors);
				admForm.setReviewWarned(false);
				admForm.setReviewed("false");
				if (admForm.isRemove()) {
					removePhotoDoc(admForm, request);
				}
			//	if(admForm.isOnlineApply())
					return mapping.findForward("onlineAppBasicPage");
			//	else
				//	return mapping.findForward("OfflineAppBasicPage");
				
			}

			
			//errors check over
			
			
			
			
			AdmApplnTO applicantDetail=admForm.getApplicantDetails();
			int year=Integer.parseInt(admForm.getApplicationYear());
			if(applicantDetail!=null){
				applicantDetail.setAppliedYear(year);
				//if(admForm.isOnlineApply())
					//applicantDetail.setMode("Online");
				//else
					//applicantDetail.setMode("Offline");
			}
			OnlineApplicationHandler admHandler = OnlineApplicationHandler.getInstance();
			
				//admForm.setReviewWarned(true);
				//admForm.setReviewed("true");
				resetHardCopySubmit(applicantDetail);
				//ActionMessages messages = new ActionMessages();
				//ActionMessage message = new ActionMessage(CMSConstants.APPLICATION_REVIEW_WARN);
				///messages.add("messages", message);
				//saveMessages(request, messages);
				setDocumentForView(admForm, request);
				boolean updated=admHandler.saveCompleteApplicationGenerateNoWithNoMoreEdit(applicantDetail, admForm, errors, "SaveApplicationNoMoreEdit");
				//boolean updated=admHandler.createApplicant(applicantDetail,admForm,false,"SaveApplicationNoMoreEdit");
				
				if(updated){
					try{
						if(admForm.getStudentId() !=0 && request.getSession().getAttribute("PhotoBytes") != null){
							FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_PATH+admForm.getStudentId()+".jpg");
							fos.write((byte[])request.getSession().getAttribute("PhotoBytes"));
							fos.close();
						}
					}catch (Exception f) {
						f.printStackTrace();
					}
					admForm.setDisplayPage("");
					admForm.setAutoSave("");
					if(focusval!=null && !focusval.isEmpty()){
						admForm.setFocusValue(focusval);
					}
					//if(admForm.isOnlineApply()){
						//setRequiredDataTOForm(admForm,session,request,errors,"CurrentID");
					//}else{
						//setRequiredDataTOFormOffline(admForm,session,request,errors,"CurrentID");
					//}
						
					admForm.setSavedDraftAlertMsg(true);
					
					//if(admForm.isOnlineApply())
						//return mapping.findForward("onlineAppBasicPage");
					//else
						//return mapping.findForward("OfflineAppBasicPage");
					//calling login page
					
					//clean up data of form
					cleanUpPageData(admForm);
					//call garbage collector forcly to clean data
					admForm=null;
					System.gc();
					OnlineApplicationForm newForm =new OnlineApplicationForm();
					newForm.setMethod("logoutFromOnlineApplication");
					newForm.setUserId("9999");
					HttpSession session=request.getSession(false);
					session.setAttribute("onlineApplicationForm",null);
					session.setAttribute("STUDENTDOCUMENTDETAILS",null);
					session.setAttribute("APPLICATIONDATA",null);
					return mapping.findForward("dashBoardPage");
					//return mapping.findForward("logoutFromOnlineApplication");
			
		}
				
				saveErrors(request, errors);
				admForm.setDisplayPage("confirmPage");
		}//try close		
		catch (Exception e) {
			log.error("error in submit preference page...",e);
			//throw e;
			System.out.println("************************ error details in online admission in submit complete app nomore edit*************************"+e);
			
		     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		     saveErrors(request, errors);
		
  		     return mapping.findForward("onlineAppBasicPage");
		
		}
		
		
		//if(admForm.isOnlineApply())
			return mapping.findForward("onlineAppBasicPage");
		//else
			//return mapping.findForward("OfflineAppBasicPage");


	}
	
	
	
	//go to edit aonline app
	public ActionForward submitEditApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit preference page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
			//new
		ActionMessages errors=new ActionMessages();
		
			try{
				
			String focusval=admForm.getFocusValue();
			HttpSession session= request.getSession(false);
					
			AdmApplnTO applicantDetail=admForm.getApplicantDetails();
			int year=Integer.parseInt(admForm.getApplicationYear());
			if(applicantDetail!=null){
				applicantDetail.setAppliedYear(year);
				//if(admForm.isOnlineApply())
					//applicantDetail.setMode("Online");
				//else
					//applicantDetail.setMode("Offline");
			}
			
				admForm.setReviewWarned(false);
				admForm.setReviewed("false");
				resetHardCopySubmit(applicantDetail);
				//ActionMessages messages = new ActionMessages();
				//ActionMessage message = new ActionMessage(CMSConstants.APPLICATION_REVIEW_WARN);
				///messages.add("messages", message);
				//saveMessages(request, messages);
				setDocumentForView(admForm, request);
				
				//boolean updated=admHandler.createApplicant(applicantDetail,admForm,false,"Draft");
				//if(updated){
					try{
						if(admForm.getStudentId() !=0 && request.getSession().getAttribute("PhotoBytes") != null){
							FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_PATH+admForm.getStudentId()+".jpg");
							fos.write((byte[])request.getSession().getAttribute("PhotoBytes"));
							fos.close();
						}
					}catch (Exception f) {
						f.printStackTrace();
					}
					
					admForm.setAutoSave("autoSave");
					if(focusval!=null && !focusval.isEmpty()){
						admForm.setFocusValue(focusval);
					}
					if(admForm.isOnlineApply()){
						setRequiredDataTOForm(admForm,session,request,errors,"CurrentID");
					}else{
						//setRequiredDataTOFormOffline(admForm,session,request,errors,"CurrentID");
					}
						
					admForm.setSavedDraftAlertMsg(true);
					admForm.setDisplayPage("preferences");
					//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
			
			
		//}
				
				
				
		}//try close		
		catch (Exception e) {
			log.error("error in submit preference page...",e);
			//throw e;
			System.out.println("************************ error details in online admission in edit Application*************************"+e);
			
		     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		     saveErrors(request, errors);
		
		     return mapping.findForward("onlineAppBasicPage");
		
		}
		
	}
	
	
	
	//go to preview application
	public ActionForward submitPeviewApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit preference page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
			//new
		ActionMessages errors=new ActionMessages();
			try{
				
			String focusval=admForm.getFocusValue();
			//ActionMessages errors=new ActionMessages();
			HttpSession session= request.getSession(false);
					
			AdmApplnTO applicantDetail=admForm.getApplicantDetails();
			int year=Integer.parseInt(admForm.getApplicationYear());
			if(applicantDetail!=null){
				applicantDetail.setAppliedYear(year);
				//if(admForm.isOnlineApply())
					//applicantDetail.setMode("Online");
				//else
					//applicantDetail.setMode("Offline");
			}
			
			session.setAttribute("PhotoBytes",null);
						try{
						if(admForm.getStudentId() !=0 && request.getSession().getAttribute("PhotoBytes") != null){
							FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_PATH+admForm.getStudentId()+".jpg");
							fos.write((byte[])request.getSession().getAttribute("PhotoBytes"));
							fos.close();
						}
						
						
						

						if(applicantDetail.getEditDocuments()!=null){
										Iterator<ApplnDocTO> docItr=applicantDetail.getEditDocuments().iterator();
										while (docItr.hasNext()) {
											ApplnDocTO docTO = (ApplnDocTO) docItr.next();
											if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
											{
												admForm.setSubmitDate(docTO.getSubmitDate());
											}
											if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
											
												//HttpSession session= request.getSession(false);
												if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo") && docTO.isDefaultPhoto() )
												{
													if(session!=null){
														byte[] fileData= null;
														// set default photo image
														try {
															InputStream photoin=AdmissionFormAction.class.getClassLoader().getResourceAsStream(CMSConstants.PRINT_PHOTO_PATH);
															if(photoin!=null){
																fileData= new byte[photoin.available()];
																photoin.read(fileData, 0, photoin.available());	
															}
														} catch (Exception e) {
															log.error(e);
														}
														
														if(fileData!=null)
														session.setAttribute("PhotoBytes",fileData );
													}
												}else if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
													
													if(session!=null){
														session.setAttribute("PhotoBytes", docTO.getPhotoBytes());
													}
												}else if(docTO.isMngQuotaForm()){
													
													if(session!=null){
														session.setAttribute("MngFormBytes", docTO.getPhotoBytes());
													}
												}
											}
										}
									}





						
						
						
					}catch (Exception f) {
						f.printStackTrace();
					}
					
					admForm.setAutoSave("autoSave");
					if(focusval!=null && !focusval.isEmpty()){
						admForm.setFocusValue(focusval);
					}
					if(admForm.isOnlineApply()){
						setRequiredDataTOForm(admForm,session,request,errors,"CurrentID");
					}else{
						//setRequiredDataTOFormOffline(admForm,session,request,errors,"CurrentID");
					}
					admForm.setDisplayPage("previewpage");	
					admForm.setSavedDraftAlertMsg(true);
					
					//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
						//return mapping.findForward("OfflineAppBasicPage");
			
			
		
				
				
				
		}//try close		
		catch (Exception e) {
			log.error("error in submit preference page...",e);
			//throw e;
			System.out.println("************************ error details in online admission in preview application*************************"+e);
			
		     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		     saveErrors(request, errors);
		     return mapping.findForward("onlineAppBasicPage");
			
		
		}
		
	}
	
	
	
	//goto back confirm page
	public ActionForward backToConfirmPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit preference page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
		
			//new
			try{
				
			String focusval=admForm.getFocusValue();
			HttpSession session= request.getSession(false);
					
			AdmApplnTO applicantDetail=admForm.getApplicantDetails();
			int year=Integer.parseInt(admForm.getApplicationYear());
			if(applicantDetail!=null){
				applicantDetail.setAppliedYear(year);
				//if(admForm.isOnlineApply())
					//applicantDetail.setMode("Online");
				//else
					//applicantDetail.setMode("Offline");
			}
				
					admForm.setAutoSave("autoSave");
					if(focusval!=null && !focusval.isEmpty()){
						admForm.setFocusValue(focusval);
					}
					if(admForm.isOnlineApply()){
						setRequiredDataTOForm(admForm,session,request,errors,"CurrentID");
					}else{
						//setRequiredDataTOFormOffline(admForm,session,request,errors,"CurrentID");
					}
					
					admForm.setDisplayPage("confirmPage");	
					admForm.setSavedDraftAlertMsg(true);
					
					//if(admForm.isOnlineApply())
						return mapping.findForward("onlineAppBasicPage");
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
			
			
		//}
				
				
				
		}//try close		
		catch (Exception e) {
			log.error("error in submit preference page...",e);
			//throw e;
			System.out.println("************************ error details in online admission in back to confirm page*************************"+e);
			
		     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		     saveErrors(request, errors);
		
		     return mapping.findForward("onlineAppBasicPage");
		
		}
		
	}
	
	
	
	public ActionForward initSemesterMarkConfirmPage( ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
		
		try {
			String indexString = request.getParameter(OnlineApplicationAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					session.setAttribute(OnlineApplicationAction.COUNTID, indexString);
					index=Integer.parseInt(indexString);
				}else
					session.removeAttribute(OnlineApplicationAction.COUNTID);
			}
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			admForm.setIsLanguageWiseMarks("false");
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if (qualTO.getCountId()==index) {
								if(qualTO.getSemesterList()!=null){
									int totalObtainWithLan = 0;
									int totalMarkWithLan = 0;
									int totalObtainWithoutLan = 0;
									int totalMarkWithOutLan = 0;
									
									List<ApplicantMarkDetailsTO> semList=new ArrayList<ApplicantMarkDetailsTO>();
									semList.addAll(qualTO.getSemesterList());
									int listSize=semList.size();
									int sizeDiff=CMSConstants.MAX_CANDIDATE_SEMESTERS-listSize;
									if(sizeDiff>0){
										for(int i=listSize+1; i<=CMSConstants.MAX_CANDIDATE_SEMESTERS;i++){
											ApplicantMarkDetailsTO to= new ApplicantMarkDetailsTO();
											to.setSemesterNo(i);
											to.setSemesterName("Semester"+i);
											semList.add(to);
										}
									}
									Iterator<ApplicantMarkDetailsTO> semItr = semList.iterator();
									ApplicantMarkDetailsTO applicantMarkDetailsTO ;
									while (semItr.hasNext()){
										applicantMarkDetailsTO = semItr.next();
										if(applicantMarkDetailsTO.getMarksObtained_languagewise()!= null && !applicantMarkDetailsTO.getMarksObtained_languagewise().trim().isEmpty()){
											totalObtainWithLan = totalObtainWithLan + Integer.parseInt(applicantMarkDetailsTO.getMarksObtained_languagewise());
										}
										if(applicantMarkDetailsTO.getMaxMarks_languagewise()!= null && !applicantMarkDetailsTO.getMaxMarks_languagewise().trim().isEmpty()){
											totalMarkWithLan = totalMarkWithLan +  Integer.parseInt(applicantMarkDetailsTO.getMaxMarks_languagewise());
										}
										if(applicantMarkDetailsTO.getMarksObtained()!= null && !applicantMarkDetailsTO.getMarksObtained().trim().isEmpty()){
											totalObtainWithoutLan = totalObtainWithoutLan + Integer.parseInt(applicantMarkDetailsTO.getMarksObtained());
										}
										if(applicantMarkDetailsTO.getMaxMarks()!= null && !applicantMarkDetailsTO.getMaxMarks().trim().isEmpty()){
											totalMarkWithOutLan = totalMarkWithOutLan + Integer.parseInt(applicantMarkDetailsTO.getMaxMarks());
										}
										
										if(applicantMarkDetailsTO.getMarksObtained() !=null && !applicantMarkDetailsTO.getMarksObtained().trim().isEmpty()  && Integer.parseInt(applicantMarkDetailsTO.getMarksObtained()) ==0){
											applicantMarkDetailsTO.setMarksObtained(null);
										}
										if(applicantMarkDetailsTO.getMaxMarks()!=null && !applicantMarkDetailsTO.getMaxMarks().trim().isEmpty()  && Integer.parseInt(applicantMarkDetailsTO.getMaxMarks())==0){
											applicantMarkDetailsTO.setMaxMarks(null);
										}
										if(applicantMarkDetailsTO.getMaxMarks_languagewise()!=null && !applicantMarkDetailsTO.getMaxMarks_languagewise().trim().isEmpty() && Integer.parseInt(applicantMarkDetailsTO.getMaxMarks_languagewise())==0){
											applicantMarkDetailsTO.setMaxMarks_languagewise(null);
										}
										if(applicantMarkDetailsTO.getMarksObtained_languagewise() != null && !applicantMarkDetailsTO.getMarksObtained_languagewise().trim().isEmpty() && Integer.parseInt(applicantMarkDetailsTO.getMarksObtained_languagewise())==0){
											applicantMarkDetailsTO.setMarksObtained_languagewise(null);
										}
										if( applicantMarkDetailsTO.getTotalMark_languagewise() !=null && !applicantMarkDetailsTO.getTotalMark_languagewise().trim().isEmpty()  && Integer.parseInt(applicantMarkDetailsTO.getTotalMark_languagewise())==0){
											applicantMarkDetailsTO.setTotalMark_languagewise(null);
										}
										if(applicantMarkDetailsTO.getTotalObtainedMark_languagewise_()!=null && !applicantMarkDetailsTO.getTotalObtainedMark_languagewise_().trim().isEmpty() && Integer.parseInt(applicantMarkDetailsTO.getTotalObtainedMark_languagewise_())==0){
											applicantMarkDetailsTO.setTotalObtainedMark_languagewise_(null);
										}
										
									}
									Collections.sort(semList);
									admForm.setSemesterList(semList);
									admForm.setTotalobtainedMarkWithLanguage(totalObtainWithLan>0?Integer.toString(totalObtainWithLan):null);
									admForm.setTotalMarkWithLanguage(totalMarkWithLan>0?Integer.toString(totalMarkWithLan):null);
									admForm.setTotalobtainedMarkWithoutLan(totalObtainWithoutLan>0?Integer.toString(totalObtainWithoutLan):null);
									admForm.setTotalMarkWithoutLan(totalMarkWithOutLan>0?Integer.toString(totalMarkWithOutLan):null);
								}
								else{
									List<ApplicantMarkDetailsTO> semList=new ArrayList<ApplicantMarkDetailsTO>();
									admForm.setTotalobtainedMarkWithLanguage(null);
									admForm.setTotalMarkWithLanguage(null);
									admForm.setTotalobtainedMarkWithoutLan(null);
									admForm.setTotalMarkWithoutLan(null);
									for(int i=1; i<=CMSConstants.MAX_CANDIDATE_SEMESTERS;i++){
										ApplicantMarkDetailsTO to= new ApplicantMarkDetailsTO();
										to.setSemesterNo(i);
										to.setSemesterName("Semester"+i);
										semList.add(to);
									}
									Collections.sort(semList);
									admForm.setSemesterList(semList);
								}
							  admForm.setIsLanguageWiseMarks(String.valueOf(qualTO.isLanguage()));
									
							}
							
				}
			}
			
			if(admForm.getDetailMark()==null)
				admForm.setDetailMark(new CandidateMarkTO());
			// code added by chandra
			//raghu
			Map<Integer, String> interviewVenueSelection=new HashMap<Integer, String>();
			//Map<Integer, String> interviewVenueSelection=CommonAjaxHandler.getInstance().getDatesBySelectionVenueOnline(admForm.getInterviewVenue(), admForm.getProgramId(),admForm.getProgramYear(),String.valueOf(admForm.getApplicantDetails().getCourse().getId()) );
			if(interviewVenueSelection!=null && interviewVenueSelection.size()>0){
				admForm.setInterviewSelectionSchedule(interviewVenueSelection);
			}
		} catch (Exception e) {
			log.error("error in initSemesterMarkConfirmPage...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				System.out.println("************************ error details in online admission in semester mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			
				return mapping.findForward("onlineAppBasicPage");
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in semester mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("onlineAppBasicPage");
			}
		}
		admForm.setDisplayPage("semesterMarkPage");
		//if(admForm.isOnlineApply()){
			return mapping.findForward("onlineAppBasicPage");
		//}else{
			//return mapping.findForward("OfflineAppBasicPage");	
		//}
		
		/*if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_REVIEW_SEM_MARK_PAGE);
		else
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_REVIEW_SEM_MARK_PAGE);*/
		
	}
	
	/**
	 * SUBMITS SUBMIT MARK REVIEW
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitSemesterConfirmMark(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.info("enter submitSemesterConfirmMark...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		HttpSession session=request.getSession(false);
		ActionMessages errors=new ActionMessages();
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(OnlineApplicationAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			List<ApplicantMarkDetailsTO> semesterMarks = admForm.getSemesterList();
			errors = validateEditSemesterMarks(semesterMarks);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				admForm.setDisplayPage("semesterMarkPage");
				if(admForm.isOnlineApply()){
					return mapping.findForward("onlineAppBasicPage");
				}else{
					return mapping.findForward("OfflineAppBasicPage");	
				}
				
					/*return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_REVIEW_SEM_MARK_PAGE);
				else
					return mapping.findForward(CMSConstants.ONLINE_APPLICATION_REVIEW_SEM_MARK_PAGE);*/
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getCountId()==detailMarkIndex){
						Set<ApplicantMarkDetailsTO> semDetails=new HashSet<ApplicantMarkDetailsTO>();
						semDetails.addAll(semesterMarks);
						qualTO.setSemesterList(semDetails);
					}
				}
			}
		} catch (Exception e) {
			log.error("error in submitSemesterConfirmMark...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				
				System.out.println("************************ error details in online admission in submit semester*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("onlineAppBasicPage");
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in submit semester*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("onlineAppBasicPage");
			}
		}
		log.info("exit submitSemesterConfirmMark...");
		/*if(admForm.isOnlineApply()&& !admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_CONFIRMSUBMIT_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ONLINE_DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_CONFIRMSUBMIT_PAGE);*/
		admForm.setDisplayPage("educationaldetail");
		///if(admForm.isOnlineApply()){
			
			return mapping.findForward("onlineAppBasicPage");
		//}else{
			//return mapping.findForward("OfflineAppBasicPage");	
		//}
	}
	
	/**
	 * INIT DETAILMARK REVIEW PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkConfirmPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
		
		try {
			
			String indexString = request.getParameter(OnlineApplicationAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(OnlineApplicationAction.COUNTID, indexString);
				}else
					session.removeAttribute(OnlineApplicationAction.COUNTID);
			}
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if (qualTO.getCountId()==index) {
								if (qualTO.getDetailmark() != null)
									admForm.setDetailMark(qualTO
											.getDetailmark());
							}
							
						}
			}
			
			Map<Integer,String> subjectMap = OnlineApplicationHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);
			
			if(admForm.getDetailMark()==null){
				CandidateMarkTO markTo=new CandidateMarkTO();
				OnlineApplicationHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
				admForm.setDetailMark(markTo);
			}
		
		} catch (Exception e) {
			log.error("error initDetailMarkConfirmPage...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
				System.out.println("************************ error details in online admission in class 10 mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			  
				return mapping.findForward("onlineAppBasicPage");
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in class 10 mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("onlineAppBasicPage");
			}
		}
		admForm.setDisplayPage("detailMarkPage");
		//if(admForm.isOnlineApply()){
			
			return mapping.findForward("onlineAppBasicPage");
		//}else{
			//return mapping.findForward("OfflineAppBasicPage");	
		//}
		/*if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_REVIEW_DET_MARK_PAGE);
		else
		return mapping.findForward(CMSConstants.ONLINE_APPLICATION_REVIEW_DET_MARK_PAGE);*/
	}
	
	
	
	/**
	 * SUBMITS DETAIL MARK REVIEW
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitDetailMarkConfirm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitDetailMarkConfirm page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		HttpSession session=request.getSession(false);
		ActionMessages errors=new ActionMessages();
		
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(OnlineApplicationAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			errors = validateMarks(detailmark);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				admForm.setDisplayPage("detailMarkPage");
				if(admForm.isOnlineApply()){
					return mapping.findForward("onlineAppBasicPage");
				}else{
					return mapping.findForward("OfflineAppBasicPage");	
				}
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getCountId()==detailMarkIndex){
						qualTO.setDetailmark(detailmark);
					}
				}
			}
		} catch (Exception e) {
			log.error("error in submitDetailMarkConfirm page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
				System.out.println("************************ error details in online admission in submit class 10 mark*************************"+e);
				return mapping.findForward("onlineAppBasicPage");
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in submit class 10 mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("onlineAppBasicPage");
			}
		}
		log.info("enter ssubmitDetailMarkConfirm page...");
		/*if(admForm.isOnlineApply()&& !admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_CONFIRMSUBMIT_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ONLINE_DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_CONFIRMSUBMIT_PAGE);*/
		admForm.setDisplayPage("educationaldetail");
		//if(admForm.isOnlineApply()){
			
			return mapping.findForward("onlineAppBasicPage");
		//}else{
		//	return mapping.findForward("OfflineAppBasicPage");	
		//}
	}
	
	
	
	/**
	 * INIT DETAILMARK REVIEW PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkConfirmPageView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
		
		try {
			
			String indexString = request.getParameter(OnlineApplicationAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(OnlineApplicationAction.COUNTID, indexString);
				}else
					session.removeAttribute(OnlineApplicationAction.COUNTID);
			}
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if (qualTO.getCountId()==index) {
								if (qualTO.getDetailmark() != null)
									admForm.setDetailMark(qualTO
											.getDetailmark());
							}
							
						}
			}
			
			Map<Integer,String> subjectMap = OnlineApplicationHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);
			
			if(admForm.getDetailMark()==null){
				CandidateMarkTO markTo=new CandidateMarkTO();
				OnlineApplicationHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
				admForm.setDetailMark(markTo);
			}
		
		} catch (Exception e) {
			log.error("error initDetailMarkConfirmPage...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
				System.out.println("************************ error details in online admission in view class 10 mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			  
				return mapping.findForward("onlineAppBasicPage");
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in view class 10 mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("onlineAppBasicPage");
			}
		}
		admForm.setDisplayPage("detailMarkPageView");
		//if(admForm.isOnlineApply()){
			
			return mapping.findForward("onlineAppBasicPage");
		//}else{
			//return mapping.findForward("OfflineAppBasicPage");	
		//}
		/*if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_REVIEW_DET_MARK_PAGE);
		else
		return mapping.findForward(CMSConstants.ONLINE_APPLICATION_REVIEW_DET_MARK_PAGE);*/
	}
	
	
	
	
	//raghu
	
	/**
	 * INIT DETAILMARK REVIEW PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkConfirmPageClass12(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
		try {
			
			String indexString = request.getParameter(OnlineApplicationAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(OnlineApplicationAction.COUNTID, indexString);
				}else
					session.removeAttribute(OnlineApplicationAction.COUNTID);
			}
			
			//class 12
			int doctypeId=CMSConstants.CLASS12_DOCTYPEID;
			
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if (qualTO.getCountId()==index) {
								
								//new raghu
								if(qualTO.getDocTypeId()==doctypeId){
									
									String language="Language";
									Map<Integer,String> admsubjectMap=null;
									Map<Integer,String> admsubjectLangMap=null;
								
									
									
									if(admForm.getAdmSubjectMap()!=null && admForm.getAdmSubjectMap().size()!=0 ){
										
									}else{
										admsubjectMap=AdmissionFormHandler.getInstance().get12thclassSubjects(qualTO.getDocName(),language);
										admForm.setAdmSubjectMap(admsubjectMap);
										
									}
									
									if(admForm.getAdmSubjectLangMap()!=null && admForm.getAdmSubjectLangMap().size()!=0 ){
										admsubjectLangMap=admForm.getAdmSubjectLangMap();
									}else{
										admsubjectLangMap=AdmissionFormHandler.getInstance().get12thclassLangSubjects(qualTO.getDocName(),language);
										admForm.setAdmSubjectLangMap(admsubjectLangMap);

										
									}
									
									
									
									
									if (qualTO.getDetailmark() != null){
										admForm.setDetailMark(qualTO.getDetailmark());
									}else{
										CandidateMarkTO markTo=new CandidateMarkTO();
										
										//find id from english in Map
								        String strValue="English";
								        String intKey = null;
								        for(Map.Entry entry: admsubjectLangMap.entrySet()){
								            if(strValue.equals(entry.getValue())){
								            	intKey =entry.getKey().toString();
								            	markTo.setSubjectid1(intKey);
								                break; //breaking because its one to one map
								            }
								        }
					
										admForm.setDetailMark(markTo);
										qualTO.setDetailmark(markTo);
									}//else
										
								
										
								}//close
								
							}//over setting candimarks to from
							
						}//while close
			}
			
			/*Map<Integer,String> subjectMap = OnlineApplicationHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);
			
			if(admForm.getDetailMark()==null){
				CandidateMarkTO markTo=new CandidateMarkTO();
				OnlineApplicationHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
				admForm.setDetailMark(markTo);
			}*/
		
		} catch (Exception e) {
			log.error("error initDetailMarkConfirmPage...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
				System.out.println("************************ error details in online admission in class 12 mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			   
				return mapping.findForward("onlineAppBasicPage");
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in class 12 mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     
			     return mapping.findForward("onlineAppBasicPage");
			}
		}
		admForm.setDisplayPage("detailMarkPage12");
		//if(admForm.isOnlineApply()){
			
			return mapping.findForward("onlineAppBasicPage");
		//}else{
		//	return mapping.findForward("OfflineAppBasicPage");	
		//}
		/*if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_REVIEW_DET_MARK_PAGE);
		else
		return mapping.findForward(CMSConstants.ONLINE_APPLICATION_REVIEW_DET_MARK_PAGE);*/
	}
	
	
	
	//raghu
	public ActionForward submitDetailMarkConfirmClass12(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitDetailMarkConfirm page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		HttpSession session=request.getSession(false);
		ActionMessages errors=new ActionMessages();
		
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(OnlineApplicationAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			
			
			// validation starts
			//ActionMessages errors = new ActionMessages();
			
			
			//raghu write newly
			if(detailmark.getTotalMarks()!=null && detailmark.getTotalMarks().equalsIgnoreCase("")) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			if(detailmark.getTotalMarks()!=null && StringUtils.isEmpty(detailmark.getTotalMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarks())){
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			//raghu write newly
			if(detailmark.getTotalMarks()!=null &&  !CommonUtil.isValidDecimal(detailmark.getTotalMarks())){
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			if(detailmark.getTotalObtainedMarks()!=null && detailmark.getTotalObtainedMarks().equalsIgnoreCase("")) {
					if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			if(detailmark.getTotalObtainedMarks()!=null && StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks())) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			if(detailmark.getTotalObtainedMarks()!=null &&  !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks())) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			
			if (errors != null && !errors.isEmpty()) {saveErrors(request, errors);
			
			//if(admForm.isOnlineApply()){
				return mapping.findForward("onlineAppBasicPage");
			//}else{
			//	return mapping.findForward("OfflineAppBasicPage");	
			//}
			}
			
			errors = validateMarksClass12(detailmark);
			if (errors != null && !errors.isEmpty()) {saveErrors(request, errors);
			admForm.setDisplayPage("detailMarkPage12");
			//if(admForm.isOnlineApply()){
				return mapping.findForward("onlineAppBasicPage");
			//}else{
				//return mapping.findForward("OfflineAppBasicPage");	
			//}
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getCountId()==detailMarkIndex){
						qualTO.setDetailmark(detailmark);
					}
				}
			}
		} catch (Exception e) {
			log.error("error in submitDetailMarkConfirm page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
				System.out.println("************************ error details in online admission in class 12 submit mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			    
				return mapping.findForward("onlineAppBasicPage");
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in submit class 12 mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     
			     return mapping.findForward("onlineAppBasicPage");	
			}
		}
		log.info("enter ssubmitDetailMarkConfirm page...");
		/*if(admForm.isOnlineApply()&& !admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_CONFIRMSUBMIT_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ONLINE_DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_CONFIRMSUBMIT_PAGE);*/
		admForm.setDisplayPage("educationaldetail");
		//if(admForm.isOnlineApply()){
			
			return mapping.findForward("onlineAppBasicPage");
		//}else{
			//return mapping.findForward("OfflineAppBasicPage");	
		//}
	}
	
	
	/**
	 * INIT DETAILMARK REVIEW PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkConfirmPageClass12View(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
		try {
			
			String indexString = request.getParameter(OnlineApplicationAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(OnlineApplicationAction.COUNTID, indexString);
				}else
					session.removeAttribute(OnlineApplicationAction.COUNTID);
			}
			
			//class 12
			int doctypeId=CMSConstants.CLASS12_DOCTYPEID;
			
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if (qualTO.getCountId()==index) {
								
								//new raghu
								if(qualTO.getDocTypeId()==doctypeId){
									
									String language="Language";
									Map<Integer,String> admsubjectMap=null;
									Map<Integer,String> admsubjectLangMap=null;
								
									
									
									if(admForm.getAdmSubjectMap()!=null && admForm.getAdmSubjectMap().size()!=0 ){
										
									}else{
										admsubjectMap=AdmissionFormHandler.getInstance().get12thclassSubjects(qualTO.getDocName(),language);
										admForm.setAdmSubjectMap(admsubjectMap);
										
									}
									
									if(admForm.getAdmSubjectLangMap()!=null && admForm.getAdmSubjectLangMap().size()!=0 ){
										
									}else{
										admsubjectLangMap=AdmissionFormHandler.getInstance().get12thclassLangSubjects(qualTO.getDocName(),language);
										admForm.setAdmSubjectLangMap(admsubjectLangMap);

										
									}
									
									
									
									
									if (qualTO.getDetailmark() != null){
										admForm.setDetailMark(qualTO.getDetailmark());
									}else{
										CandidateMarkTO markTo=new CandidateMarkTO();
										
										//find id from english in Map
								        String strValue="English";
								        String intKey = null;
								        for(Map.Entry entry: admsubjectLangMap.entrySet()){
								            if(strValue.equals(entry.getValue())){
								            	intKey =entry.getKey().toString();
								            	markTo.setSubjectid1(intKey);
								                break; //breaking because its one to one map
								            }
								        }
					
										admForm.setDetailMark(markTo);
										qualTO.setDetailmark(markTo);
									}//else
										
								
										
								}//close
								
							}//over setting candimarks to from
							
						}//while close
			}
			
			/*Map<Integer,String> subjectMap = OnlineApplicationHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);
			
			if(admForm.getDetailMark()==null){
				CandidateMarkTO markTo=new CandidateMarkTO();
				OnlineApplicationHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
				admForm.setDetailMark(markTo);
			}*/
		
		} catch (Exception e) {
			log.error("error initDetailMarkConfirmPage...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
				System.out.println("************************ error details in online admission in class 12 view*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			   
				return mapping.findForward("onlineAppBasicPage");
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in class 12 view*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     
			     return mapping.findForward("onlineAppBasicPage");
			}
		}
		admForm.setDisplayPage("detailMarkPage12View");
		//if(admForm.isOnlineApply()){
			
			return mapping.findForward("onlineAppBasicPage");
		//}else{
		//	return mapping.findForward("OfflineAppBasicPage");	
		//}
		/*if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_REVIEW_DET_MARK_PAGE);
		else
		return mapping.findForward(CMSConstants.ONLINE_APPLICATION_REVIEW_DET_MARK_PAGE);*/
	}
	
	
	/**
	 * INIT DETAILMARK REVIEW PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkConfirmPageDegree(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
		try {
			
			String indexString = request.getParameter(OnlineApplicationAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(OnlineApplicationAction.COUNTID, indexString);
				}else
					session.removeAttribute(OnlineApplicationAction.COUNTID);
			}
			
			//deg
			int doctypeId=CMSConstants.DEGREE_DOCTYPEID;
			
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if (qualTO.getCountId()==index) {
								//new raghu
								if(qualTO.getDocTypeId()==doctypeId){
								
								
									admForm.setPatternofStudy(qualTO.getUgPattern());
									String Core="Core";
									String Compl="Complimentary";
									String Common="Common";
									String Open="Open";
									//String Sub="Sub";
									Map<Integer,String> admCoreMap=null;
									Map<Integer,String> admComplMap=null;
									Map<Integer,String> admCommonMap=null;
									Map<Integer,String> admopenMap=null;
									Map<Integer,String> admSubMap=null;
									
									
									if(admForm.getAdmCoreMap()!=null && admForm.getAdmCoreMap().size()!=0 ){
										
									}else{
										admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Core);
										admForm.setAdmCoreMap(admCoreMap);
										
										
									}

									if(admForm.getAdmComplMap()!=null && admForm.getAdmComplMap().size()!=0 ){

									}else{
										admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Compl);
										admForm.setAdmComplMap(admComplMap);
										

									}

									if(admForm.getAdmCommonMap()!=null && admForm.getAdmCommonMap().size()!=0 ){
										admCommonMap=admForm.getAdmCommonMap();
									}else{
										admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Common);
										admForm.setAdmCommonMap(admCommonMap);
										

									}

									if(admForm.getAdmMainMap()!=null && admForm.getAdmMainMap().size()!=0 ){

									}else{
										admopenMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Open);
										admForm.setAdmMainMap(admopenMap);
										

									}

									if(admForm.getAdmSubMap()!=null && admForm.getAdmSubMap().size()!=0 ){

									}else{
										admSubMap=AdmissionFormHandler.getInstance().get12thclassSub1(qualTO.getDocName(),Common);
										admForm.setAdmSubMap(admSubMap);


									}
									
									
									if (qualTO.getDetailmark() != null){
										admForm.setDetailMark(qualTO.getDetailmark());
									}else{
										CandidateMarkTO markTo=new CandidateMarkTO();
										//find id from english in Map
								        String strValue="English";
								        String intKey = null;
								        for(Map.Entry entry: admCommonMap.entrySet()){
								            if(strValue.equals(entry.getValue())){
								            	intKey =entry.getKey().toString();
								            	markTo.setSubjectid6(intKey);
								            	markTo.setSubjectid16(intKey);
								                break; //breaking because its one to one map
								            }
								        }
								        admForm.setDetailMark(markTo);
										qualTO.setDetailmark(markTo);
									}//else
									
								}//close
								
								
							}//over setting candimark to form
							
							
							
							
							
						}//while close
			}
			
			/*Map<Integer,String> subjectMap = OnlineApplicationHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);
			
			if(admForm.getDetailMark()==null){
				CandidateMarkTO markTo=new CandidateMarkTO();
				OnlineApplicationHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
				admForm.setDetailMark(markTo);
			}*/
		
		} catch (Exception e) {
			log.error("error initDetailMarkConfirmPage...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
				System.out.println("************************ error details in online admission in degree mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("onlineAppBasicPage");	
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in degree mark************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("onlineAppBasicPage");	
			}
		}
		admForm.setDisplayPage("detailMarkPageDegree");
		//if(admForm.isOnlineApply()){
			
			return mapping.findForward("onlineAppBasicPage");
	//	}else{
			//return mapping.findForward("OfflineAppBasicPage");	
		//}
		/*if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_REVIEW_DET_MARK_PAGE);
		else
		return mapping.findForward(CMSConstants.ONLINE_APPLICATION_REVIEW_DET_MARK_PAGE);*/
	}
	
	
	//raghu
	public ActionForward submitDetailMarkConfirmDegree(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitDetailMarkConfirm page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		HttpSession session=request.getSession(false);
		ActionMessages errors=new ActionMessages();
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(OnlineApplicationAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			
			 
			// validation starts
		 errors = new ActionMessages();
			
			
			if(admForm.getPatternofStudy().equalsIgnoreCase("CBCSS") || admForm.getPatternofStudy().equalsIgnoreCase("CBCSS NEW")){
				
			
			
			//raghu write newly
			if(detailmark.getTotalMarksCGPA()!=null && detailmark.getTotalMarksCGPA().equalsIgnoreCase("")) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			if(detailmark.getTotalMarksCGPA()!=null && StringUtils.isEmpty(detailmark.getTotalMarksCGPA()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarksCGPA())){
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			//raghu write newly
			if(detailmark.getTotalMarksCGPA()!=null &&  !CommonUtil.isValidDecimal(detailmark.getTotalMarksCGPA())){
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			if(detailmark.getTotalObtainedMarksCGPA()!=null && detailmark.getTotalObtainedMarksCGPA().equalsIgnoreCase("")) {
					if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			if(detailmark.getTotalObtainedMarksCGPA()!=null && StringUtils.isEmpty(detailmark.getTotalObtainedMarksCGPA()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarksCGPA())) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			if(detailmark.getTotalObtainedMarksCGPA()!=null &&  !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarksCGPA())) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					
			}
			
			}//cbcss over
			
			//other
			else{
				
				

				//raghu write newly
				if(detailmark.getTotalMarks()!=null && detailmark.getTotalMarks().equalsIgnoreCase("")) {
					if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
						errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
						}
						
				}
				
				if(detailmark.getTotalMarks()!=null && StringUtils.isEmpty(detailmark.getTotalMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarks())){
					if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
						errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
						}
						
				}
				
				//raghu write newly
				if(detailmark.getTotalMarks()!=null &&  !CommonUtil.isValidDecimal(detailmark.getTotalMarks())){
					if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
						errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
						}
						
				}
				
				if(detailmark.getTotalObtainedMarks()!=null && detailmark.getTotalObtainedMarks().equalsIgnoreCase("")) {
						if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
						errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
						}
						
				}
				
				if(detailmark.getTotalObtainedMarks()!=null && StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks())) {
					if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
						errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
						}
						
				}
				
				if(detailmark.getTotalObtainedMarks()!=null &&  !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks())) {
					if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
						errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
						}
						
				}
			}//other over
			
			
			
			
			if (errors != null && !errors.isEmpty()) {saveErrors(request, errors);
			admForm.setDisplayPage("detailMarkPageDegree");
			//if(admForm.isOnlineApply()){
				return mapping.findForward("onlineAppBasicPage");
			//}else{
				//return mapping.findForward("OfflineAppBasicPage");	
			//}
			}
			
			
			
			
			errors = validateMarksDegree(detailmark,admForm.getPatternofStudy());
			if (errors != null && !errors.isEmpty()) {saveErrors(request, errors);
			admForm.setDisplayPage("detailMarkPageDegree");
			//if(admForm.isOnlineApply()){
				return mapping.findForward("onlineAppBasicPage");
			//}else{
				//return mapping.findForward("OfflineAppBasicPage");	
			//}
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getCountId()==detailMarkIndex){
						qualTO.setDetailmark(detailmark);
						qualTO.setUgPattern(admForm.getPatternofStudy());
					}
				}
			}
		} catch (Exception e) {
			log.error("error in submitDetailMarkConfirm page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
				System.out.println("************************ error details in online admission in submit degree*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			 
				return mapping.findForward("onlineAppBasicPage");
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in submit degree *************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("onlineAppBasicPage");
			}
		}
		log.info("enter ssubmitDetailMarkConfirm page...");
		/*if(admForm.isOnlineApply()&& !admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_CONFIRMSUBMIT_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ONLINE_DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_CONFIRMSUBMIT_PAGE);*/
		admForm.setDisplayPage("educationaldetail");
		//if(admForm.isOnlineApply()){
			
			return mapping.findForward("onlineAppBasicPage");
		//}else{
		//	return mapping.findForward("OfflineAppBasicPage");	
		//}
	}
	
	
	
	/**
	 * INIT DETAILMARK REVIEW PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkConfirmPageDegreeView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		ActionMessages errors=new ActionMessages();
		try {
			
			String indexString = request.getParameter(OnlineApplicationAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(OnlineApplicationAction.COUNTID, indexString);
				}else
					session.removeAttribute(OnlineApplicationAction.COUNTID);
			}
			
			//deg
			int doctypeId=CMSConstants.DEGREE_DOCTYPEID;
			
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if (qualTO.getCountId()==index) {
								//new raghu
								if(qualTO.getDocTypeId()==doctypeId){
								
								
									admForm.setPatternofStudy(qualTO.getUgPattern());
									String Core="Core";
									String Compl="Complimentary";
									String Common="Common";
									String Open="Open";
									//String Sub="Sub";
									Map<Integer,String> admCoreMap=null;
									Map<Integer,String> admComplMap=null;
									Map<Integer,String> admCommonMap=null;
									Map<Integer,String> admopenMap=null;
									Map<Integer,String> admSubMap=null;
									
									
									if(admForm.getAdmCoreMap()!=null && admForm.getAdmCoreMap().size()!=0 ){
										
									}else{
										admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Core);
										admForm.setAdmCoreMap(admCoreMap);
										
										
									}

									if(admForm.getAdmComplMap()!=null && admForm.getAdmComplMap().size()!=0 ){

									}else{
										admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Compl);
										admForm.setAdmComplMap(admComplMap);
										

									}

									if(admForm.getAdmCommonMap()!=null && admForm.getAdmCommonMap().size()!=0 ){

									}else{
										admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Common);
										admForm.setAdmCommonMap(admCommonMap);
										

									}

									if(admForm.getAdmMainMap()!=null && admForm.getAdmMainMap().size()!=0 ){

									}else{
										admopenMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Open);
										admForm.setAdmMainMap(admopenMap);
										

									}

									if(admForm.getAdmSubMap()!=null && admForm.getAdmSubMap().size()!=0 ){

									}else{
										admSubMap=AdmissionFormHandler.getInstance().get12thclassSub1(qualTO.getDocName(),Common);
										admForm.setAdmSubMap(admSubMap);


									}
									
									
									if (qualTO.getDetailmark() != null){
										admForm.setDetailMark(qualTO.getDetailmark());
									}else{
										CandidateMarkTO markTo=new CandidateMarkTO();
										//find id from english in Map
								        String strValue="English";
								        String intKey = null;
								        for(Map.Entry entry: admCommonMap.entrySet()){
								            if(strValue.equals(entry.getValue())){
								            	intKey =entry.getKey().toString();
								            	markTo.setSubjectid6(intKey);
								            	markTo.setSubjectid16(intKey);
								                break; //breaking because its one to one map
								            }
								        }
								        admForm.setDetailMark(markTo);
										qualTO.setDetailmark(markTo);
									}//else
									
								}//close
								
								
							}//over setting candimark to form
							
							
							
							
							
						}//while close
			}
			
			/*Map<Integer,String> subjectMap = OnlineApplicationHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);
			
			if(admForm.getDetailMark()==null){
				CandidateMarkTO markTo=new CandidateMarkTO();
				OnlineApplicationHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
				admForm.setDetailMark(markTo);
			}*/
		
		} catch (Exception e) {
			log.error("error initDetailMarkConfirmPage...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
				System.out.println("************************ error details in online admission in degree view*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("onlineAppBasicPage");	
			}else
			{
				//throw e;
				System.out.println("************************ error details in online admission in degree view*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward("onlineAppBasicPage");	
			}
		}
		admForm.setDisplayPage("detailMarkPageDegreeView");
		//if(admForm.isOnlineApply()){
			
			return mapping.findForward("onlineAppBasicPage");
	//	}else{
			//return mapping.findForward("OfflineAppBasicPage");	
		//}
		/*if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ONLINE_APPLICATION_ONLINE_REVIEW_DET_MARK_PAGE);
		else
		return mapping.findForward(CMSConstants.ONLINE_APPLICATION_REVIEW_DET_MARK_PAGE);*/
	}
	
	
	
	/**
 * @param admForm
 * @throws Exception
 */
private void setDataToInitForm(OnlineApplicationForm admForm)throws Exception {
	IOnlineApplicationTxn txn= new OnlineApplicationImpl();
	 Map<String,String> monthMap=txn.getMonthMap();
	 Map<String, String> yearMap = txn.getYear();
	 if(monthMap!=null)
	 {
		 admForm.setMonthMap(monthMap);
	 }
	if(yearMap!=null)
	 {
		 admForm.setYearMap(yearMap);
	 }
	
}

/**
	 * checks year difference
	 * @param ageFromLimit
	 * @param ageToLimit
	 * @param dateOfBirth 
	 * @return					
	 */
	private boolean validateOnlineDOB(int ageFromLimit, int ageToLimit, String dateOfBirth)
	{
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateOfBirth, OnlineApplicationAction.FROM_DATEFORMAT,OnlineApplicationAction.TO_DATEFORMAT);
		Date birthdate = new Date(formattedString);
		Calendar cal2= Calendar.getInstance();
		cal2.setTime(birthdate);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		Date brtdate=cal2.getTime();
		Date curdate = new Date();
		Calendar cal= Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date todaydate=cal.getTime();
		int yearDiff=CommonUtil.getYearsDiff(brtdate, todaydate);
		if(yearDiff>=ageFromLimit && yearDiff<=ageToLimit)
			return true;
		else
			return false;
	}
	/**
	 * future date validation
	 * @param dateString
	 * @return
	 */
	public static boolean validatefutureDate(String dateString) {
		log.info("enter validatefutureDate..");
			String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, OnlineApplicationAction.FROM_DATEFORMAT,OnlineApplicationAction.TO_DATEFORMAT);
			Date date = new Date(formattedString);
			Date curdate = new Date();
			Calendar cal= Calendar.getInstance();
			cal.setTime(curdate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date origdate=cal.getTime();
			log.info("exit validatefutureDate..");
			return !(date.compareTo(origdate) == 1);
	}
	/**
	 * past date validation
	 * @param dateString
	 * @return
	 */
	private boolean validatePastDate(String dateString) {
		log.info("enter validatePastDate..");
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, OnlineApplicationAction.FROM_DATEFORMAT,OnlineApplicationAction.TO_DATEFORMAT);
		Date date = new Date(formattedString);
		Date curdate = new Date();
		Calendar cal= Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date origdate=cal.getTime();
		
		log.info("exit validatePastDate..");
		return !(date.compareTo(origdate) < 0);
			
	
	}
	
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditOtherOptions(OnlineApplicationForm admForm,
			ActionMessages errors) throws Exception {
		log.info("enter validateEditOtherOptions..");
		if(errors==null){
			errors= new ActionMessages();
		}
		if(admForm.getApplicantDetails().getPersonalData().isHandicapped()){
			if((admForm.getApplicantDetails().getPersonalData().getHadnicappedDescription()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getHadnicappedDescription()))){
				if (errors.get(CMSConstants.ERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage("errors.required","Hadicapped Description");
					errors.add(CMSConstants.ERROR, error);
				}
			}
		}
		if((admForm.getApplicantDetails().getPersonalData().getReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId())|| (admForm.getApplicantDetails().getPersonalData().getReligionId().equalsIgnoreCase(OnlineApplicationAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getPersonalData().getReligionId()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId()) && StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getReligionId())){
			ISubReligionTransaction relTxn=new SubReligionTransactionImpl();
			//if master entry exists
			if(relTxn.checkSubReligionExists(Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getReligionId()))){
				if(admForm.isCasteDisplay()){
					if((admForm.getApplicantDetails().getPersonalData().getSubReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getSubReligionId())|| (admForm.getApplicantDetails().getPersonalData().getSubReligionId().equalsIgnoreCase(OnlineApplicationAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers())) )
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED, error);
						}
					}
				}
			}
		}
		
			if((admForm.getApplicantDetails().getPersonalData().getCasteId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteId())|| (admForm.getApplicantDetails().getPersonalData().getCasteId().equalsIgnoreCase(OnlineApplicationAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getCasteOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteOthers()) ))
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_CASTE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED, error);
				}
			}
		
		if((admForm.getApplicantDetails().getPersonalData().getPermanentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentStateId()) || admForm.getApplicantDetails().getPersonalData().getPermanentStateId().equalsIgnoreCase("0"))&& ((admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers())==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED, error);
			}
		}
		log.info("exit validateEditOtherOptions..");
	}
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPhone(OnlineApplicationForm admForm, ActionMessages errors) {
		if(errors==null)
			errors= new ActionMessages();
		
				if((admForm.getApplicantDetails().getPersonalData().getPhNo1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1()))
						&& (admForm.getApplicantDetails().getPersonalData().getPhNo2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2()))
						&& (admForm.getApplicantDetails().getPersonalData().getPhNo3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3())))
				{
					/*if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED, error);
					}*/
				}

				if(admForm.getApplicantDetails().getPersonalData().getPhNo1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPhNo1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getPhNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPhNo2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getPhNo3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPhNo3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				
			
	   if((admForm.getResidentCategoryForOnlineAppln()!=null && !admForm.getResidentCategoryForOnlineAppln().isEmpty()) 
						&& CMSConstants.INDIAN_RESIDENT_LIST.contains(Integer.parseInt(admForm.getResidentCategoryForOnlineAppln()))){
				if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) && admForm.getApplicantDetails().getPersonalData().getMobileNo2().trim().length()!=10 )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
	   }else
	   {
		   if(admForm.getMobileNo2()!=null && !admForm.getMobileNo2().isEmpty() && admForm.getMobileNo2().length()>10){
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED));
			}
	   }
				if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getMobileNo3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
	}
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	public static void validateParentPhone(OnlineApplicationForm admForm, ActionMessages errors) {
		if(errors==null)
			errors= new ActionMessages();
		

				if(admForm.getParentPhone1()!=null && !StringUtils.isEmpty(admForm.getParentPhone1()) && !StringUtils.isNumeric(admForm.getParentPhone1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID, error);
					}
				}
				if(admForm.getParentPhone2()!=null && !StringUtils.isEmpty(admForm.getParentPhone2()) && !StringUtils.isNumeric(admForm.getParentPhone2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID, error);
					}
				}
				if(admForm.getParentPhone3()!=null && !StringUtils.isEmpty(admForm.getParentPhone3()) && !StringUtils.isNumeric(admForm.getParentPhone3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID, error);
					}
				}
				
				if(admForm.getParentMobile1()!=null && !StringUtils.isEmpty(admForm.getParentMobile1()) && !StringUtils.isNumeric(admForm.getParentMobile1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID, error);
					}
				}
				if(admForm.getParentMobile2()!=null && !StringUtils.isEmpty(admForm.getParentMobile2()) && !StringUtils.isNumeric(admForm.getParentMobile2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID, error);
					}
				}
				if(admForm.getParentMobile3()!=null && !StringUtils.isEmpty(admForm.getParentMobile3()) && !StringUtils.isNumeric(admForm.getParentMobile3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID, error);
					}
				}
	}
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	public static void validateGuardianPhone(OnlineApplicationForm admForm, ActionMessages errors) {
		if(errors==null)
			errors= new ActionMessages();
		

				if(admForm.getGuardianPhone1()!=null && !StringUtils.isEmpty(admForm.getGuardianPhone1()) && !StringUtils.isNumeric(admForm.getGuardianPhone1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID, error);
					}
				}
				if(admForm.getGuardianPhone2()!=null && !StringUtils.isEmpty(admForm.getGuardianPhone2()) && !StringUtils.isNumeric(admForm.getGuardianPhone2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID, error);
					}
				}
				if(admForm.getGuardianPhone3()!=null && !StringUtils.isEmpty(admForm.getGuardianPhone3()) && !StringUtils.isNumeric(admForm.getGuardianPhone3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID, error);
					}
				}
				
				if(admForm.getGuardianMobile1()!=null && !StringUtils.isEmpty(admForm.getGuardianMobile1()) && !StringUtils.isNumeric(admForm.getGuardianMobile1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID, error);
					}
				}
				if(admForm.getGuardianMobile2()!=null && !StringUtils.isEmpty(admForm.getGuardianMobile2()) && !StringUtils.isNumeric(admForm.getGuardianMobile2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID, error);
					}
				}
				if(admForm.getGuardianMobile3()!=null && !StringUtils.isEmpty(admForm.getGuardianMobile3()) && !StringUtils.isNumeric(admForm.getGuardianMobile3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID, error);
					}
				}
	}
	
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditGuardianPhone(OnlineApplicationForm admForm, ActionMessages errors) {
		if(errors==null)
			errors= new ActionMessages();
		

				if(admForm.getApplicantDetails().getPersonalData().getGuardianPh1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianPh1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianPh1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getGuardianPh2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianPh2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianPh2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getGuardianPh3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianPh3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianPh3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID, error);
					}
				}
				
				if(admForm.getApplicantDetails().getPersonalData().getGuardianMob1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianMob1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianMob1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getGuardianMob2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianMob2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianMob2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getGuardianMob3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianMob3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianMob3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID, error);
					}
				}
	}
	
	
	
	
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditParentPhone(OnlineApplicationForm admForm, ActionMessages errors) {
		if(errors==null)
			errors= new ActionMessages();
		

				if(admForm.getApplicantDetails().getPersonalData().getParentPh1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentPh1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentPh1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getParentPh2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentPh2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentPh2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getParentPh3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentPh3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentPh3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID, error);
					}
				}
				
				if(admForm.getApplicantDetails().getPersonalData().getParentMob1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentMob1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentMob1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getParentMob2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentMob2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentMob2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getParentMob3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentMob3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentMob3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID, error);
					}
				}
	}
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	public static void validateCommAddress(OnlineApplicationForm admForm,
			ActionMessages errors) {
		if(errors==null)
			errors= new ActionMessages();
			AddressTO tempAddTo=admForm.getTempAddr();
			if(tempAddTo.getAddrLine1()==null || StringUtils.isEmpty(tempAddTo.getAddrLine1()))
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_ADDRESS1_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_ADDRESS1_REQUIRED,error);
			}
			if(tempAddTo.getCity()==null || StringUtils.isEmpty(tempAddTo.getCity()) )
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_CITY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_CITY_REQUIRED,error);
			}
			
			if(tempAddTo.getCountryId()==null || StringUtils.isEmpty(tempAddTo.getCountryId()) )
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_COUNTRY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_COUNTRY_REQUIRED,error);
			}
			if(tempAddTo.getPinCode()==null || StringUtils.isEmpty(tempAddTo.getPinCode()) )
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_ZIP_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_ZIP_REQUIRED,error);
			}else if(tempAddTo.getPinCode()!=null && !StringUtils.isEmpty(tempAddTo.getPinCode())  && !StringUtils.isNumeric(tempAddTo.getPinCode())){
				if (errors.get(CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID);
					errors.add(CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID, error);
				}
			}
			if((tempAddTo.getStateId()==null || StringUtils.isEmpty(tempAddTo.getStateId()))&& (tempAddTo.getOtherState()==null ||StringUtils.isEmpty(tempAddTo.getOtherState())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED, error);
				}
			}
	}
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditCommAddress(OnlineApplicationForm admForm,
			ActionMessages errors) {
		if(errors==null)
			errors= new ActionMessages();
		
			if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine1()))
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","House Name/House Number is required."));
				
			}
			if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine2()))
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Post Offiece Name is required."));
				
			}
			if(admForm.getApplicantDetails().getPersonalData().getCurrentCityName()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentCityName()) )
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_CITY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_CITY_REQUIRED,error);
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getCurrentCountryId()==0 )
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_COUNTRY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_COUNTRY_REQUIRED,error);
			}
			if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode()) )
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_ZIP_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_ZIP_REQUIRED,error);
			}else if(!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode())){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID,error);
			}
			if((admForm.getApplicantDetails().getPersonalData().getCurrentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentStateId()) || admForm.getApplicantDetails().getPersonalData().getCurrentStateId().equalsIgnoreCase("0"))&& (admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED, error);
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getCurrentStreet()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentStreet()) )
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_Street_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_Street_REQUIRED,error);
			}
			
	}

	/**
	 * online parent mandatory check
	 * @param admForm
	 * @param errors
	 */
	private void validateParentConfirmOnlineRequireds(OnlineApplicationForm admForm,
			ActionMessages errors) {
		if(errors==null){
			errors= new ActionMessages();
		}
		if(admForm.getApplicantDetails().getPersonalData()!=null && (admForm.getApplicantDetails().getPersonalData().getFatherName()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getFatherName())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED, error);
			}
		}
		
		
		
		if(admForm.getApplicantDetails().getPersonalData()!=null && (admForm.getApplicantDetails().getPersonalData().getMotherName()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMotherName())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getTitleOfFather()!=null && !admForm.getApplicantDetails().getTitleOfFather().isEmpty()
				&& !admForm.getApplicantDetails().getTitleOfFather().equalsIgnoreCase("Late") &&
				(admForm.getApplicantDetails().getPersonalData().getFatherIncomeId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getFatherIncomeId())))
				//(admForm.getApplicantDetails().getPersonalData().getFatherIncome1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getFatherIncome1())))
				
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getTitleOfMother()!=null && !admForm.getApplicantDetails().getTitleOfMother().isEmpty()
				&& !admForm.getApplicantDetails().getTitleOfMother().equalsIgnoreCase("Late")
				&&(admForm.getApplicantDetails().getPersonalData().getMotherIncomeId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMotherIncomeId())))
		{
			/*if (errors.get(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED, error);
			}*/
		}
	}
	/**
	 * Validate document size
	 * @param admForm
	 * @param errors
	 */
	public static void validateDocumentSize(OnlineApplicationForm admForm,
			ActionMessages errors) {
		List<ApplnDocTO> uploadlist=admForm.getUploadDocs();
		InputStream propStream=OnlineApplicationAction.class.getResourceAsStream("/resources/application.properties");
		int maXSize=0;
		int maxPhotoSize=0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maXSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_FILESIZE_KEY));
			 maxPhotoSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		 }catch (IOException e) {
			 log.error("Error in Reading key from properties file....",e);
		}
		if(uploadlist!=null){
			Iterator<ApplnDocTO> uploaditr=uploadlist.iterator();
			while (uploaditr.hasNext()) {
				ApplnDocTO docTo = (ApplnDocTO) uploaditr.next();
				FormFile file=null;
				if(docTo!=null)
					file=docTo.getDocument();
				if(file!=null)
				{
					String filename=file.getFileName();
					if(!StringUtils.isEmpty(filename)&& filename.length()>30)
					{
						if(errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME).hasNext()){
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME);
						errors.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME, error);
						}
					}
				}
				if(docTo.isPhoto() && file!=null && maxPhotoSize< file.getFileSize()){
					if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
						errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE,error);
					}
				}
				else if(file!=null && maXSize< file.getFileSize())
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE);
						errors.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE,error);
					}
				}
				if(docTo.isPhoto() && file!=null ){
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
			}
			
		}
		
	}
	/**
	 * EXTRA DETAILS BLOCK DISPLAY CHECK
	 * @param admForm
	 */
	private void checkExtradetailsDisplay(OnlineApplicationForm admForm) {
		boolean isextra=false;
		if(admForm.isDisplayMotherTongue())
			isextra=true;
		if(admForm.isDisplayHeightWeight())
			isextra=true;
		if(admForm.isDisplayTrainingDetails())
			isextra=true;
		if(admForm.isDisplayAdditionalInfo())
			isextra=true;
		if(admForm.isDisplayExtracurricular())
			isextra=true;
		if(admForm.isDisplayLanguageKnown())
			isextra=true;
		
		admForm.setDisplayExtraDetails(isextra);
		
	}
	/**
	 * LATERAL AND TRANSFER COURSE LINK DISPLAY CHECK
	 * @param admForm
	 */
	private void checkLateralTransferDisplay(OnlineApplicationForm admForm) {
		boolean isextra=false;
		
		if(admForm.isDisplayLateralDetails())
			isextra=true;
		if(admForm.isDisplayTransferCourse())
			isextra=true;
		admForm.setDisplayLateralTransfer(isextra);
		
	}
	/**
	 * CLEANS UP OLD FORM DATA FROM SESSION
	 * @param session
	 */
	public static void cleanupFormFromSession(HttpSession session) {
		log.info("enter cleanupFormFromSession...");
		if(session.getAttribute("admissionFormForm")!=null)
			session.removeAttribute("admissionFormForm");
		log.info("exit cleanupFormFromSession...");
	}
	/**
	 * VALIDATES PAYMENT RELATED INFORMATIONS
	 * @param errors
	 * @param admForm
	 */
	private void validatePaymentDetails(ActionMessages errors,
			OnlineApplicationForm admForm) {
		log.info("enter validatePaymentDetails...");
		if(errors==null){
			errors= new ActionMessages();
		}
		if(admForm.getSelectedFeePayment()==null || StringUtils.isEmpty(admForm.getSelectedFeePayment())){
			if (errors.get(CMSConstants.ADMISSIONFORM_FEEPAY_TYPE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_FEEPAY_TYPE_REQUIRED).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_FEEPAY_TYPE_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_FEEPAY_TYPE_REQUIRED));
			}	
		}
	if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty()){
		if(admForm.getSelectedFeePayment().equalsIgnoreCase("SBI")){
				if (admForm.getChallanNo()==null || StringUtils.isEmpty(admForm.getChallanNo())) {
					
					//if (errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED).hasNext()) {
						//errors.add(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED));
					//}
				
			}
			if (admForm.getJournalNo()==null || StringUtils.isEmpty(admForm.getJournalNo())) {
				
				if (errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED));
				}
			
			}
			if (admForm.getApplicationDate()==null || StringUtils.isEmpty(admForm.getApplicationDate())) {
				
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED));
				}
			
			}
			if (admForm.getApplicationAmount()==null || StringUtils.isEmpty(admForm.getApplicationAmount())) {
		
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED));
				}

			}
			if (admForm.getApplicationDate()!=null && !StringUtils.isEmpty(admForm.getApplicationDate())) {
				if(CommonUtil.isValidDate(admForm.getApplicationDate())){
				boolean	isValid = validatefutureDate(admForm.getApplicationDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
					}
				}
			}
			if(admForm.getApplicationAmount()!=null && !StringUtils.isEmpty(admForm.getApplicationAmount().trim())){
					if(!CommonUtil.isValidDecimal(admForm.getApplicationAmount().trim())){
						if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID));
						}
					}
			}
			if(admForm.getChallanNo()!=null && !admForm.getChallanNo().trim().isEmpty() && admForm.getChallanNo().length()>30){
				errors.add("knowledgepro.admission.online.Challan.no.maxlength.error",new ActionError("knowledgepro.admission.online.Challan.no.maxlength.error"));
			}
			if(admForm.getJournalNo()!=null && !admForm.getJournalNo().trim().isEmpty() && admForm.getJournalNo().length()>30){
				errors.add("knowledgepro.admission.online.journal.no.maxlength.error",new ActionError("knowledgepro.admission.online.journal.no.maxlength.error"));
			}
		}
		//neft checking but properties r using like dd
	else if(admForm.getSelectedFeePayment().equalsIgnoreCase("NEFT")){
				if (admForm.getDdNo()==null || StringUtils.isEmpty(admForm.getDdNo())) {
					
					if (errors.get(CMSConstants.ADMISSIONFORM_DDNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DDNO_REQUIRED).hasNext()) {
						//errors.add(CMSConstants.ADMISSIONFORM_DDNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_DDNO_REQUIRED));
						errors.add(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED));
						
					}
				
			}
				
			if (admForm.getDdDate()==null || StringUtils.isEmpty(admForm.getDdDate())) {
				
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED));
				}
			
			}
			
			if (admForm.getDdDate()!=null && !StringUtils.isEmpty(admForm.getDdDate())) {
				if(CommonUtil.isValidDate(admForm.getDdDate())){
				boolean	isValid = validatefutureDate(admForm.getDdDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
					}
				}
			}
			
			if (admForm.getDdAmount()==null || StringUtils.isEmpty(admForm.getDdAmount())) {
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED));
				}
			}
			
			if(admForm.getDdAmount()!=null && !StringUtils.isEmpty(admForm.getDdAmount().trim())){
					if(!CommonUtil.isValidDecimal(admForm.getDdAmount().trim())){
						if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID));
						}
					}
			}
			
			
			
			
			//added by giri
			/*
			 * 
			 * 
			 if (admForm.getDdDrawnOn()==null || StringUtils.isEmpty(admForm.getDdDrawnOn())) {
				
				if (errors.get(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED));
				}
			
			}
			
			 
			 if(admForm.getInstitute().equalsIgnoreCase("LV") || admForm.getInstitute().equalsIgnoreCase("GH")){
				if (admForm.getIndianCandidate()) {
					if (admForm.getEquivalentApplnFeeINR()==null || StringUtils.isEmpty(admForm.getEquivalentApplnFeeINR())) {
						if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED));
						}
					}
				} else {
					if (admForm.getDdAmount()==null || StringUtils.isEmpty(admForm.getDdAmount())) {
						
						if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED));
						}
					}
				}
			}else {
				if (admForm.getDdAmount()==null || StringUtils.isEmpty(admForm.getDdAmount())) {
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED));
					}
				}
			//}
			//end by giri
			/*if(admForm.getInternationalApplnFeeCurrencyId()==null || admForm.getInternationalApplnFeeCurrencyId().isEmpty()){
				errors.add("knowledgepro.admission.online.currency.reqd", new ActionError("knowledgepro.admission.online.currency.reqd"));
			}
			if(admForm.getDdIssuingBank()== null || admForm.getDdIssuingBank().isEmpty()){
				if (errors.get(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED));
				}
			}*/
			
			
		}
	}
	log.info("exit validatePaymentDetails...");
}
	/**
	 * validate programtype,course and program
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateProgramCourse(ActionMessages errors,
			OnlineApplicationForm admForm,boolean isFirstprefLable) {
		log.info("enter validate program course...");
		if(admForm.getProgramTypeId() ==null || admForm.getProgramTypeId().length()==0)
		{
			if(errors==null){
				errors= new ActionMessages();
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add("programTypeId", error);
			}else{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add("programTypeId", error);
			}
		}
		/*if(admForm.getProgramId() ==null || admForm.getProgramId().length()==0)
		{
			if(errors==null){
				errors= new ActionMessages();
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add("programId", error);
			}else
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add("programId", error);
			}
		}
		if(admForm.getCourseId()==null ||admForm.getCourseId().length()==0 )
		{
			if(errors==null){
				errors= new ActionMessages();
				if(isFirstprefLable){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED);
					errors.add("courseId", error);
				}else{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add("courseId", error);
				}
			}else{
				if(isFirstprefLable){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED);
					errors.add("courseId", error);
				}else{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add("courseId", error);
				}
			}
		}*/
		
		log.info("exit validate program course...");
		return errors;
	}
	/**
	 * validate semester mark in edit page 
	 * @param semesterMarks
	 * @return
	 */
	private ActionMessages validateEditSemesterMarks(
			List<ApplicantMarkDetailsTO> semesterMarks) {
		log.info("enter validateEditSemesterMarks...");
		ActionMessages errors= new ActionMessages();
		if(semesterMarks!=null && !semesterMarks.isEmpty()){
			int currentSize=semesterMarks.size();
			int count=0;
			Iterator<ApplicantMarkDetailsTO> semItr=semesterMarks.iterator();
			while (semItr.hasNext()) {
				ApplicantMarkDetailsTO semMarkTO = (ApplicantMarkDetailsTO) semItr.next();
				if (semMarkTO.getMarksObtained()==null || semMarkTO.getMarksObtained().isEmpty() || semMarkTO.getMaxMarks()==null || semMarkTO.getMaxMarks().isEmpty()) {
					count++;
				}
				
				if(semMarkTO!=null && semMarkTO.getMarksObtained()!=null && !StringUtils.isEmpty(semMarkTO.getMarksObtained()) && !StringUtils.isNumeric(semMarkTO.getMarksObtained())){
					if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
						errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER, error);
					}
				}
				
				if(semMarkTO!=null && semMarkTO.getMaxMarks()!=null && !StringUtils.isEmpty(semMarkTO.getMaxMarks()) && !StringUtils.isNumeric(semMarkTO.getMaxMarks())){
					if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
					errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER, error);
					}
				}
				if(semMarkTO!=null && semMarkTO.getMarksObtained()!=null && (semMarkTO.getMaxMarks()==null || StringUtils.isEmpty(semMarkTO.getMaxMarks())) && !StringUtils.isEmpty(semMarkTO.getMarksObtained()) ){
					if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
						errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
					}
				}
				
				if(semMarkTO!=null && semMarkTO.getMarksObtained()!=null && semMarkTO.getMaxMarks()!=null && !StringUtils.isEmpty(semMarkTO.getMarksObtained()) && StringUtils.isNumeric(semMarkTO.getMarksObtained())
					&& !StringUtils.isEmpty(semMarkTO.getMaxMarks()) && StringUtils.isNumeric(semMarkTO.getMaxMarks())){
					if(Integer.parseInt(semMarkTO.getMarksObtained())>Integer.parseInt(semMarkTO.getMaxMarks())){
						if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
						errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
						}
					}
				}
				
				
					if(semMarkTO!=null && semMarkTO.getMarksObtained_languagewise()!=null && !StringUtils.isEmpty(semMarkTO.getMarksObtained_languagewise()) && !StringUtils.isNumeric(semMarkTO.getMarksObtained_languagewise())){
						if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER, error);
						}
					}
					
					if(semMarkTO!=null && semMarkTO.getMaxMarks_languagewise()!=null && !StringUtils.isEmpty(semMarkTO.getMaxMarks_languagewise()) && !StringUtils.isNumeric(semMarkTO.getMaxMarks_languagewise())){
						if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
						errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER, error);
						}
					}
					if(semMarkTO!=null && semMarkTO.getMarksObtained_languagewise()!=null && (semMarkTO.getMaxMarks_languagewise()==null || StringUtils.isEmpty(semMarkTO.getMaxMarks_languagewise())) && !StringUtils.isEmpty(semMarkTO.getMarksObtained_languagewise()) ){
						if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
						}
					}
					
					if(semMarkTO!=null && semMarkTO.getMarksObtained_languagewise()!=null && semMarkTO.getMaxMarks_languagewise()!=null && !StringUtils.isEmpty(semMarkTO.getMarksObtained_languagewise()) && StringUtils.isNumeric(semMarkTO.getMarksObtained_languagewise())
						&& !StringUtils.isEmpty(semMarkTO.getMaxMarks_languagewise()) && StringUtils.isNumeric(semMarkTO.getMaxMarks_languagewise())){
						if(Integer.parseInt(semMarkTO.getMarksObtained_languagewise())>Integer.parseInt(semMarkTO.getMaxMarks_languagewise())){
							if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
							}
						}
					}
					if(semMarkTO.getMaxMarks_languagewise()!=null && !semMarkTO.getMaxMarks_languagewise().isEmpty()
							&& (semMarkTO.getMaxMarks_languagewise().equalsIgnoreCase("0") || semMarkTO.getMaxMarks_languagewise().startsWith("0.0"))){
						if (errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMAXMARKWITHLAN_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMAXMARKWITHLAN_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_SEMESTERMAXMARKWITHLAN_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_SEMESTERMAXMARKWITHLAN_REQUIRED,error);
						}
					}
					if(semMarkTO.getMaxMarks()!=null && !semMarkTO.getMaxMarks().isEmpty()
							&& (semMarkTO.getMaxMarks().equalsIgnoreCase("0") || semMarkTO.getMaxMarks().startsWith("0.0"))){
						if (errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMAXMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMAXMARK_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_SEMESTERMAXMARK_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_SEMESTERMAXMARK_REQUIRED,error);
						}
					}
				
			}
			if (currentSize==count) {
				errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Please fill in the Semester wise marks .."));
			}
		}else{
			errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Please fill in the Semester wise Marks.."));
		}
		
		log.info("exit validateEditSemesterMarks...");
		return errors;
	
	}
	/**
	 * @param detailmark
	 * @return
	 */
	public static ActionMessages validateMarks(CandidateMarkTO detailmark) {
		log.info("enter validateMarks...");
		ActionMessages errors= new ActionMessages();
		if(detailmark!=null){
			
			/*mandatory subject mark check start */
			if(detailmark.isSubject1Mandatory() &&  (detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks())|| 
					detailmark.getSubject1ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			
			if(detailmark.isSubject2Mandatory() &&  (detailmark.getSubject2TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks())|| 
					detailmark.getSubject2ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			
			if(detailmark.isSubject3Mandatory() &&  (detailmark.getSubject3TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks())|| 
					detailmark.getSubject3ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject4Mandatory() &&  (detailmark.getSubject4TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks())|| 
					detailmark.getSubject4ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject5Mandatory() &&  (detailmark.getSubject5TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks())|| 
					detailmark.getSubject5ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject6Mandatory() &&  (detailmark.getSubject6TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks())|| 
					detailmark.getSubject6ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject7Mandatory() &&  (detailmark.getSubject7TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks())|| 
					detailmark.getSubject7ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject8Mandatory() &&  (detailmark.getSubject8TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks())|| 
					detailmark.getSubject8ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject9Mandatory() &&  (detailmark.getSubject9TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks())|| 
					detailmark.getSubject9ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject10Mandatory() && ( detailmark.getSubject10TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks())|| 
					detailmark.getSubject10ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject11Mandatory() &&  (detailmark.getSubject11TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks())|| 
					detailmark.getSubject11ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject12Mandatory() && ( detailmark.getSubject12TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks())|| 
					detailmark.getSubject12ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject13Mandatory() &&  (detailmark.getSubject13TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks())|| 
					detailmark.getSubject13ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject14Mandatory() && ( detailmark.getSubject14TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks())|| 
					detailmark.getSubject14ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject15Mandatory() &&  (detailmark.getSubject15TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks())|| 
					detailmark.getSubject15ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject16Mandatory() &&  (detailmark.getSubject16TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks())|| 
					detailmark.getSubject16ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject17Mandatory() &&  (detailmark.getSubject17TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks())|| 
					detailmark.getSubject17ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject18Mandatory() &&  (detailmark.getSubject18TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks())|| 
					detailmark.getSubject18ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject19Mandatory() &&  (detailmark.getSubject19TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks())|| 
					detailmark.getSubject1ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject20Mandatory() &&  (detailmark.getSubject20TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks())|| 
					detailmark.getSubject20ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			
			
			/*mandatory subject mark check end */
			
			//added by mahi start
			int count=20;
			int compareCount=0;
			if(detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) || detailmark.getSubject1ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject2TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) || detailmark.getSubject2ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject3TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) || detailmark.getSubject3ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject4TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) || detailmark.getSubject4ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject5TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) || detailmark.getSubject5ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject6TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) || detailmark.getSubject6ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject7TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) || detailmark.getSubject7ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject8TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) || detailmark.getSubject8ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject9TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) || detailmark.getSubject9ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject10TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) || detailmark.getSubject10ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject11TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) || detailmark.getSubject11ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject12TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) || detailmark.getSubject12ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject13TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) || detailmark.getSubject13ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject14TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) || detailmark.getSubject14ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject15TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) || detailmark.getSubject15ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject16TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) || detailmark.getSubject16ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject17TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) || detailmark.getSubject17ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject18TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) || detailmark.getSubject18ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject19TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) || detailmark.getSubject19ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject20TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) || detailmark.getSubject20ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())){
				compareCount++;
			}
			if (compareCount==count) {
				errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Please fill the Marks.."));
			}
			
			if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject1TotalMarks()) ||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject2TotalMarks()) ||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject3TotalMarks()) ||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject4TotalMarks()) ||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject5TotalMarks()) ||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject6TotalMarks()) ||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject7TotalMarks()) ||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject8TotalMarks()) ||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject9TotalMarks()) ||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject10TotalMarks()) ||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject11TotalMarks()) ||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject12TotalMarks()) ||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject13TotalMarks()) ||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject14TotalMarks()) ||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject15TotalMarks()) ||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject16TotalMarks()) ||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject17TotalMarks()) ||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject18TotalMarks()) ||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject19TotalMarks()) ||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject20TotalMarks()) ||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& !StringUtils.isNumeric(detailmark.getTotalMarks()))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER, error);
				}
				return errors;
			}
			if(detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject1ObtainedMarks()) ||
					detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject2ObtainedMarks()) ||
					detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject3ObtainedMarks()) ||
					detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject4ObtainedMarks()) ||
					detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject5ObtainedMarks()) ||
					detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject6ObtainedMarks()) ||
					detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject7ObtainedMarks()) ||
					detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject8ObtainedMarks()) ||
					detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject9ObtainedMarks()) ||
					detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject10ObtainedMarks()) ||
					detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject11ObtainedMarks()) ||
					detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject12ObtainedMarks()) ||
					detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject13ObtainedMarks()) ||
					detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject14ObtainedMarks()) ||
					detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject15ObtainedMarks()) ||
					detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject16ObtainedMarks()) ||
					detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject17ObtainedMarks()) ||
					detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject18ObtainedMarks()) ||
					detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject19ObtainedMarks()) ||
					detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject20ObtainedMarks()) ||
					detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks())&& !StringUtils.isNumeric(detailmark.getTotalObtainedMarks()))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER, error);
				}
				return errors;
			}
			
			if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && (detailmark.getSubject1TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject1TotalMarks().startsWith("0")) ||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& (detailmark.getSubject2TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject2TotalMarks().startsWith("0")) ||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& (detailmark.getSubject3TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject3TotalMarks().startsWith("0")) ||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& (detailmark.getSubject4TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject4TotalMarks().startsWith("0")) ||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& (detailmark.getSubject5TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject5TotalMarks().startsWith("0"))||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& (detailmark.getSubject6TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject6TotalMarks().startsWith("0")) ||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& (detailmark.getSubject7TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject7TotalMarks().startsWith("0")) ||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& (detailmark.getSubject8TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject8TotalMarks().startsWith("0")) ||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& (detailmark.getSubject9TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject9TotalMarks().startsWith("0")) ||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& (detailmark.getSubject10TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject10TotalMarks().startsWith("0"))||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& (detailmark.getSubject11TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject11TotalMarks().startsWith("0")) ||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& (detailmark.getSubject12TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject12TotalMarks().startsWith("0")) ||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& (detailmark.getSubject13TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject13TotalMarks().startsWith("0")) ||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& (detailmark.getSubject14TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject14TotalMarks().startsWith("0")) ||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& (detailmark.getSubject15TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject15TotalMarks().startsWith("0")) ||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& (detailmark.getSubject16TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject16TotalMarks().startsWith("0")) ||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& (detailmark.getSubject17TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject17TotalMarks().startsWith("0")) ||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& (detailmark.getSubject18TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject18TotalMarks().startsWith("0")) ||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& (detailmark.getSubject19TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject19TotalMarks().startsWith("0")) ||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& (detailmark.getSubject20TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject20TotalMarks().startsWith("0")) ||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& (detailmark.getTotalMarks().equalsIgnoreCase("0") || detailmark.getTotalMarks().startsWith("0")))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO);
				errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO, error);
				}
				return errors;
			}
			
			if((detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()))&& (detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())) ||
					(detailmark.getSubject2TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()))&& (detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())) ||
					(detailmark.getSubject3TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()))&& (detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())) ||
					(detailmark.getSubject4TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()))&& (detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())) ||
					(detailmark.getSubject5TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()))&& (detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())) ||
					(detailmark.getSubject6TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()))&& (detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())) ||
					(detailmark.getSubject7TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()))&& (detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())) ||
					(detailmark.getSubject8TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()))&& (detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())) ||
					(detailmark.getSubject9TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()))&& (detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())) ||
					(detailmark.getSubject10TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()))&& (detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())) ||
					(detailmark.getSubject11TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()))&& (detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) ||
					(detailmark.getSubject12TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()))&& (detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) ||
					(detailmark.getSubject13TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()))&& (detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) ||
					(detailmark.getSubject14TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()))&& (detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) ||
					(detailmark.getSubject15TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()))&& (detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) ||
					(detailmark.getSubject16TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()))&& (detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())) ||
					(detailmark.getSubject17TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()))&& (detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())) ||
					(detailmark.getSubject18TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()))&& (detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())) ||
					(detailmark.getSubject19TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()))&& (detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())) ||
					(detailmark.getSubject20TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()))&& (detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())) ||
					(detailmark.getTotalMarks()==null || StringUtils.isEmpty(detailmark.getTotalMarks())) && (detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()))
			){
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
				}
			}
			
			if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks())&& detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && Integer.parseInt(detailmark.getSubject1TotalMarks())< Integer.parseInt(detailmark.getSubject1ObtainedMarks())||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks()) && Integer.parseInt(detailmark.getSubject2TotalMarks())< Integer.parseInt(detailmark.getSubject2ObtainedMarks())||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks()) && Integer.parseInt(detailmark.getSubject3TotalMarks())< Integer.parseInt(detailmark.getSubject3ObtainedMarks())||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&&  detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks()) && Integer.parseInt(detailmark.getSubject4TotalMarks())< Integer.parseInt(detailmark.getSubject4ObtainedMarks())||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject5TotalMarks())< Integer.parseInt(detailmark.getSubject5ObtainedMarks())||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject6TotalMarks())< Integer.parseInt(detailmark.getSubject6ObtainedMarks())||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject7TotalMarks())< Integer.parseInt(detailmark.getSubject7ObtainedMarks())||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject8TotalMarks())< Integer.parseInt(detailmark.getSubject8ObtainedMarks())||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject9TotalMarks())< Integer.parseInt(detailmark.getSubject9ObtainedMarks())||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject10TotalMarks())< Integer.parseInt(detailmark.getSubject10ObtainedMarks())||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject11TotalMarks())< Integer.parseInt(detailmark.getSubject11ObtainedMarks())||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject12TotalMarks())< Integer.parseInt(detailmark.getSubject12ObtainedMarks())||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject13TotalMarks())< Integer.parseInt(detailmark.getSubject13ObtainedMarks())||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject14TotalMarks())< Integer.parseInt(detailmark.getSubject14ObtainedMarks())||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject15TotalMarks())< Integer.parseInt(detailmark.getSubject15ObtainedMarks())||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject16TotalMarks())< Integer.parseInt(detailmark.getSubject16ObtainedMarks())||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject17TotalMarks())< Integer.parseInt(detailmark.getSubject17ObtainedMarks())||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject18TotalMarks())< Integer.parseInt(detailmark.getSubject18ObtainedMarks())||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject19TotalMarks())< Integer.parseInt(detailmark.getSubject19ObtainedMarks())||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject20TotalMarks())< Integer.parseInt(detailmark.getSubject20ObtainedMarks())||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && Integer.parseInt(detailmark.getTotalMarks())< Integer.parseInt(detailmark.getTotalObtainedMarks())
			){
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
				}
			}
			
			
			//////
			if(detailmark.isSemesterMark()){
				if(detailmark.getSubject1_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject1_languagewise_TotalMarks()) ||
						detailmark.getSubject2_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject2TotalMarks()) ||
						detailmark.getSubject3_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject3TotalMarks()) ||
						detailmark.getSubject4_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject4TotalMarks()) ||
						detailmark.getSubject5_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject5TotalMarks()) ||
						detailmark.getSubject6_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject6_languagewise_TotalMarks()) ||
						detailmark.getSubject7_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject7_languagewise_TotalMarks()) ||
						detailmark.getSubject8_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject8_languagewise_TotalMarks()) ||
						detailmark.getSubject9_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject9_languagewise_TotalMarks()) ||
						detailmark.getSubject10_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject10_languagewise_TotalMarks()) ||
						detailmark.getTotal_languagewise_Marks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks())&& !StringUtils.isNumeric(detailmark.getTotal_languagewise_Marks()))
				{
					if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
					errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER, error);
					}
					return errors;
				}
				if(detailmark.getSubject1_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject1_languagewise_ObtainedMarks()) ||
						detailmark.getSubject2_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject2_languagewise_ObtainedMarks()) ||
						detailmark.getSubject3_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject3_languagewise_ObtainedMarks()) ||
						detailmark.getSubject4_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject4_languagewise_ObtainedMarks()) ||
						detailmark.getSubject5_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject5_languagewise_ObtainedMarks()) ||
						detailmark.getSubject6_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject6_languagewise_ObtainedMarks()) ||
						detailmark.getSubject7_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject7_languagewise_ObtainedMarks()) ||
						detailmark.getSubject8_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject8_languagewise_ObtainedMarks()) ||
						detailmark.getSubject9_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject9_languagewise_ObtainedMarks()) ||
						detailmark.getSubject10_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject10_languagewise_ObtainedMarks()) ||
						detailmark.getTotal_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getTotal_languagewise_ObtainedMarks()))
				{
					if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
					errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER, error);
					}
					return errors;
				}
				
				if((detailmark.getSubject1_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks()))&& (detailmark.getSubject1_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject2_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2_languagewise_TotalMarks()))&& (detailmark.getSubject2_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject3_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3_languagewise_TotalMarks()))&& (detailmark.getSubject3_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject4_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4_languagewise_TotalMarks()))&& (detailmark.getSubject4_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject5_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5_languagewise_TotalMarks()))&& (detailmark.getSubject5_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject6_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks()))&& (detailmark.getSubject6_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject7_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks()))&& (detailmark.getSubject7_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject8_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks()))&& (detailmark.getSubject8_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject9_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks()))&& (detailmark.getSubject9_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject10_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks()))&& (detailmark.getSubject10_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks())) ||
						(detailmark.getTotal_languagewise_Marks()==null || StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks())) && (detailmark.getTotal_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks()))
				){
					if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
					errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
					}
				}
				
				if(detailmark.getSubject1_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks())&& detailmark.getSubject1_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject1_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject1_languagewise_ObtainedMarks())||
						detailmark.getSubject2_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_TotalMarks())&& detailmark.getSubject2_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject2_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject2_languagewise_ObtainedMarks())||
						detailmark.getSubject3_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_TotalMarks())&& detailmark.getSubject3_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject3_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject3_languagewise_ObtainedMarks())||
						detailmark.getSubject4_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_TotalMarks())&&  detailmark.getSubject4_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject4_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject4_languagewise_ObtainedMarks())||
						detailmark.getSubject5_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_TotalMarks())&& detailmark.getSubject5_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject5_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject5_languagewise_ObtainedMarks())||
						detailmark.getSubject6_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks())&& detailmark.getSubject6_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject6_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject6_languagewise_ObtainedMarks())||
						detailmark.getSubject7_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks())&& detailmark.getSubject7_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject7_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject7_languagewise_ObtainedMarks())||
						detailmark.getSubject8_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks())&& detailmark.getSubject8_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject8_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject8_languagewise_ObtainedMarks())||
						detailmark.getSubject9_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks())&& detailmark.getSubject9_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject9_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject9_languagewise_ObtainedMarks())||
						detailmark.getSubject10_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks())&& detailmark.getSubject10_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject10_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject10_languagewise_ObtainedMarks())||
						detailmark.getTotal_languagewise_Marks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks())&& detailmark.getTotal_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getTotal_languagewise_Marks())< Integer.parseInt(detailmark.getTotal_languagewise_ObtainedMarks())
				){
					if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
					errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
					}
				}
			}
		}
		log.info("exit validateMarks...");
		return errors;
	}
	
	
	//raghu for class 12
	/**
	 * @param detailmark
	 * @return
	 */
	public static ActionMessages validateMarksClass12(CandidateMarkTO detailmark) {
		log.info("enter validateMarks...");
		ActionMessages errors= new ActionMessages();
		if(detailmark!=null){
			int totalSubjectCount=0;
			Set<String> dupSet=new HashSet<String>();
			//raghu new code
			if(detailmark.getSubjectid1()!=null && !detailmark.getSubjectid1().equalsIgnoreCase("") && 
					detailmark.getSubject1ObtainedMarks()!=null && !detailmark.getSubject1ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject1TotalMarks()!=null && !detailmark.getSubject1TotalMarks().equalsIgnoreCase(""))
					{
				     dupSet.add(detailmark.getSubjectid1());
				     totalSubjectCount++;
				     
					}
			else if((detailmark.getSubjectid1()==null || detailmark.getSubjectid1().equalsIgnoreCase("")) && 
					(detailmark.getSubject1ObtainedMarks()==null || detailmark.getSubject1ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject1TotalMarks()==null || detailmark.getSubject1TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid2()!=null && !detailmark.getSubjectid2().equalsIgnoreCase("") && 
					detailmark.getSubject2ObtainedMarks()!=null && !detailmark.getSubject2ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject2TotalMarks()!=null && !detailmark.getSubject2TotalMarks().equalsIgnoreCase(""))
					{
				     dupSet.add(detailmark.getSubjectid2());
			         totalSubjectCount++;
					}
			else if((detailmark.getSubjectid2()==null || detailmark.getSubjectid2().equalsIgnoreCase("")) && 
					(detailmark.getSubject2ObtainedMarks()==null || detailmark.getSubject2ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject2TotalMarks()==null || detailmark.getSubject2TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid3()!=null && !detailmark.getSubjectid3().equalsIgnoreCase("") && 
					detailmark.getSubject3ObtainedMarks()!=null && !detailmark.getSubject3ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject3TotalMarks()!=null && !detailmark.getSubject3TotalMarks().equalsIgnoreCase(""))
					{
				     dupSet.add(detailmark.getSubjectid3());
			         totalSubjectCount++;
					}
			else if((detailmark.getSubjectid3()==null || detailmark.getSubjectid3().equalsIgnoreCase("")) && 
					(detailmark.getSubject3ObtainedMarks()==null || detailmark.getSubject3ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject3TotalMarks()==null || detailmark.getSubject3TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid4()!=null && !detailmark.getSubjectid4().equalsIgnoreCase("") && 
					detailmark.getSubject4ObtainedMarks()!=null && !detailmark.getSubject4ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject4TotalMarks()!=null && !detailmark.getSubject4TotalMarks().equalsIgnoreCase(""))
					{
				     dupSet.add(detailmark.getSubjectid4());
			         totalSubjectCount++;
					}
			else if((detailmark.getSubjectid4()==null || detailmark.getSubjectid4().equalsIgnoreCase("")) && 
					(detailmark.getSubject4ObtainedMarks()==null || detailmark.getSubject4ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject4TotalMarks()==null || detailmark.getSubject4TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid5()!=null && !detailmark.getSubjectid5().equalsIgnoreCase("") && 
					detailmark.getSubject5ObtainedMarks()!=null && !detailmark.getSubject5ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject5TotalMarks()!=null && !detailmark.getSubject5TotalMarks().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid5());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid5()==null || detailmark.getSubjectid5().equalsIgnoreCase("")) && 
					(detailmark.getSubject5ObtainedMarks()==null || detailmark.getSubject5ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject5TotalMarks()==null || detailmark.getSubject5TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid6()!=null && !detailmark.getSubjectid6().equalsIgnoreCase("") && 
					detailmark.getSubject6ObtainedMarks()!=null && !detailmark.getSubject6ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject6TotalMarks()!=null && !detailmark.getSubject6TotalMarks().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid6());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid6()==null || detailmark.getSubjectid6().equalsIgnoreCase("")) && 
					(detailmark.getSubject6ObtainedMarks()==null || detailmark.getSubject6ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject6TotalMarks()==null || detailmark.getSubject6TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid7()!=null && !detailmark.getSubjectid7().equalsIgnoreCase("") && 
					detailmark.getSubject7ObtainedMarks()!=null && !detailmark.getSubject7ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject7TotalMarks()!=null && !detailmark.getSubject7TotalMarks().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid7());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid7()==null || detailmark.getSubjectid7().equalsIgnoreCase("")) && 
					(detailmark.getSubject7ObtainedMarks()==null || detailmark.getSubject7ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject7TotalMarks()==null || detailmark.getSubject7TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid8()!=null && !detailmark.getSubjectid8().equalsIgnoreCase("") && 
					detailmark.getSubject8ObtainedMarks()!=null && !detailmark.getSubject8ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject8TotalMarks()!=null && !detailmark.getSubject8TotalMarks().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid8());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid8()==null || detailmark.getSubjectid8().equalsIgnoreCase("")) && 
					(detailmark.getSubject8ObtainedMarks()==null || detailmark.getSubject8ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject8TotalMarks()==null || detailmark.getSubject8TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			
			//checking duplicate subjects
			if(totalSubjectCount!=dupSet.size()){
				errors.add("error", new ActionError("knowledgepro.admission.empty.err.message",
				"Duplicate Subjects should not select."));
	
				return errors;
			}
			
			
			
			if( detailmark.getSubject1ObtainedMarks()!=null && !(detailmark.getSubject1ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
			    return errors;
			}
			if(  detailmark.getSubject2ObtainedMarks()!=null && !(detailmark.getSubject2ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject3ObtainedMarks()!=null && !(detailmark.getSubject3ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject4ObtainedMarks()!=null &&  !(detailmark.getSubject4ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject5ObtainedMarks()!=null && !(detailmark.getSubject5ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject6ObtainedMarks()!=null && !(detailmark.getSubject6ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject7ObtainedMarks()!=null && !(detailmark.getSubject7ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject8ObtainedMarks()!=null && !(detailmark.getSubject8ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject9ObtainedMarks()!=null &&!(detailmark.getSubject9ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			
			//over
			
			
			/*mandatory subject mark check start */
			if(detailmark.isSubject1Mandatory() &&  (detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks())|| 
					detailmark.getSubject1ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			/*
			if(detailmark.isSubject2Mandatory() &&  (detailmark.getSubject2TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks())|| 
					detailmark.getSubject2ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			
			if(detailmark.isSubject3Mandatory() &&  (detailmark.getSubject3TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks())|| 
					detailmark.getSubject3ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject4Mandatory() &&  (detailmark.getSubject4TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks())|| 
					detailmark.getSubject4ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject5Mandatory() &&  (detailmark.getSubject5TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks())|| 
					detailmark.getSubject5ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject6Mandatory() &&  (detailmark.getSubject6TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks())|| 
					detailmark.getSubject6ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject7Mandatory() &&  (detailmark.getSubject7TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks())|| 
					detailmark.getSubject7ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject8Mandatory() &&  (detailmark.getSubject8TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks())|| 
					detailmark.getSubject8ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject9Mandatory() &&  (detailmark.getSubject9TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks())|| 
					detailmark.getSubject9ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject10Mandatory() && ( detailmark.getSubject10TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks())|| 
					detailmark.getSubject10ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject11Mandatory() &&  (detailmark.getSubject11TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks())|| 
					detailmark.getSubject11ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject12Mandatory() && ( detailmark.getSubject12TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks())|| 
					detailmark.getSubject12ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject13Mandatory() &&  (detailmark.getSubject13TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks())|| 
					detailmark.getSubject13ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject14Mandatory() && ( detailmark.getSubject14TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks())|| 
					detailmark.getSubject14ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject15Mandatory() &&  (detailmark.getSubject15TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks())|| 
					detailmark.getSubject15ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject16Mandatory() &&  (detailmark.getSubject16TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks())|| 
					detailmark.getSubject16ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject17Mandatory() &&  (detailmark.getSubject17TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks())|| 
					detailmark.getSubject17ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject18Mandatory() &&  (detailmark.getSubject18TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks())|| 
					detailmark.getSubject18ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject19Mandatory() &&  (detailmark.getSubject19TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks())|| 
					detailmark.getSubject1ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			if(detailmark.isSubject20Mandatory() &&  (detailmark.getSubject20TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks())|| 
					detailmark.getSubject20ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}*/
			
			
			/*mandatory subject mark check end */
			
			
			//raghu write newly
			if(detailmark.getTotalMarks()!=null && StringUtils.isEmpty(detailmark.getTotalMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarks())){
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getTotalObtainedMarks()!=null && StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks())) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && (detailmark.getSubject1TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject1TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& (detailmark.getSubject2TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject2TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& (detailmark.getSubject3TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject3TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& (detailmark.getSubject4TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject4TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& (detailmark.getSubject5TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject5TotalMarks().equalsIgnoreCase("."))||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& (detailmark.getSubject6TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject6TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& (detailmark.getSubject7TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject7TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& (detailmark.getSubject8TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject8TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& (detailmark.getSubject9TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject9TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& (detailmark.getSubject10TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject10TotalMarks().equalsIgnoreCase("."))||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& (detailmark.getSubject11TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject11TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& (detailmark.getSubject12TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject12TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& (detailmark.getSubject13TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject13TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& (detailmark.getSubject14TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject14TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& (detailmark.getSubject15TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject15TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& (detailmark.getSubject16TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject16TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& (detailmark.getSubject17TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject17TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& (detailmark.getSubject18TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject18TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& (detailmark.getSubject19TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject19TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& (detailmark.getSubject20TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject20TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& (detailmark.getTotalMarks().equalsIgnoreCase("0") || detailmark.getTotalMarks().equalsIgnoreCase(".")))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO);
				errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO, error);
				}
				return errors;
			}
			
			if((detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()))&& (detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())) ||
					(detailmark.getSubject2TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()))&& (detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())) ||
					(detailmark.getSubject3TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()))&& (detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())) ||
					(detailmark.getSubject4TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()))&& (detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())) ||
					(detailmark.getSubject5TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()))&& (detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())) ||
					(detailmark.getSubject6TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()))&& (detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())) ||
					(detailmark.getSubject7TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()))&& (detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())) ||
					(detailmark.getSubject8TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()))&& (detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())) ||
					(detailmark.getSubject9TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()))&& (detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())) ||
					(detailmark.getSubject10TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()))&& (detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())) ||
					(detailmark.getSubject11TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()))&& (detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) ||
					(detailmark.getSubject12TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()))&& (detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) ||
					(detailmark.getSubject13TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()))&& (detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) ||
					(detailmark.getSubject14TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()))&& (detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) ||
					(detailmark.getSubject15TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()))&& (detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) ||
					(detailmark.getSubject16TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()))&& (detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())) ||
					(detailmark.getSubject17TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()))&& (detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())) ||
					(detailmark.getSubject18TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()))&& (detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())) ||
					(detailmark.getSubject19TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()))&& (detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())) ||
					(detailmark.getSubject20TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()))&& (detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())) ||
					(detailmark.getTotalMarks()==null || StringUtils.isEmpty(detailmark.getTotalMarks())) && (detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()))
			){
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
				}
			}
			
			if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks())&& detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && Float.parseFloat(detailmark.getSubject1TotalMarks())< Float.parseFloat(detailmark.getSubject1ObtainedMarks())||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks()) && Float.parseFloat(detailmark.getSubject2TotalMarks())< Float.parseFloat(detailmark.getSubject2ObtainedMarks())||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks()) && Float.parseFloat(detailmark.getSubject3TotalMarks())< Float.parseFloat(detailmark.getSubject3ObtainedMarks())||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&&  detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks()) && Float.parseFloat(detailmark.getSubject4TotalMarks())< Float.parseFloat(detailmark.getSubject4ObtainedMarks())||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject5TotalMarks())< Float.parseFloat(detailmark.getSubject5ObtainedMarks())||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject6TotalMarks())< Float.parseFloat(detailmark.getSubject6ObtainedMarks())||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject7TotalMarks())< Float.parseFloat(detailmark.getSubject7ObtainedMarks())||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject8TotalMarks())< Float.parseFloat(detailmark.getSubject8ObtainedMarks())||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject9TotalMarks())< Float.parseFloat(detailmark.getSubject9ObtainedMarks())||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject10TotalMarks())< Float.parseFloat(detailmark.getSubject10ObtainedMarks())||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject11TotalMarks())< Float.parseFloat(detailmark.getSubject11ObtainedMarks())||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject12TotalMarks())< Float.parseFloat(detailmark.getSubject12ObtainedMarks())||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject13TotalMarks())< Float.parseFloat(detailmark.getSubject13ObtainedMarks())||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject14TotalMarks())< Float.parseFloat(detailmark.getSubject14ObtainedMarks())||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject15TotalMarks())< Float.parseFloat(detailmark.getSubject15ObtainedMarks())||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject16TotalMarks())< Float.parseFloat(detailmark.getSubject16ObtainedMarks())||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject17TotalMarks())< Float.parseFloat(detailmark.getSubject17ObtainedMarks())||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject18TotalMarks())< Float.parseFloat(detailmark.getSubject18ObtainedMarks())||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject19TotalMarks())< Float.parseFloat(detailmark.getSubject19ObtainedMarks())||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject20TotalMarks())< Float.parseFloat(detailmark.getSubject20ObtainedMarks())||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && Float.parseFloat(detailmark.getTotalMarks())< Float.parseFloat(detailmark.getTotalObtainedMarks())
			){
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
				}
			}
			
			
			//added by mahi start
			int count=20;
			int compareCount=0;
			if(detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) || detailmark.getSubject1ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject2TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) || detailmark.getSubject2ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject3TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) || detailmark.getSubject3ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject4TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) || detailmark.getSubject4ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject5TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) || detailmark.getSubject5ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject6TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) || detailmark.getSubject6ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject7TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) || detailmark.getSubject7ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject8TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) || detailmark.getSubject8ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject9TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) || detailmark.getSubject9ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject10TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) || detailmark.getSubject10ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject11TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) || detailmark.getSubject11ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject12TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) || detailmark.getSubject12ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject13TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) || detailmark.getSubject13ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject14TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) || detailmark.getSubject14ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject15TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) || detailmark.getSubject15ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject16TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) || detailmark.getSubject16ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject17TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) || detailmark.getSubject17ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject18TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) || detailmark.getSubject18ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject19TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) || detailmark.getSubject19ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject20TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) || detailmark.getSubject20ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())){
				compareCount++;
			}
			if (compareCount==count) {
				errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Please fill the Marks.."));
			}
			
			/*if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject1TotalMarks()) ||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject2TotalMarks()) ||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject3TotalMarks()) ||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject4TotalMarks()) ||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject5TotalMarks()) ||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject6TotalMarks()) ||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject7TotalMarks()) ||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject8TotalMarks()) ||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject9TotalMarks()) ||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject10TotalMarks()) ||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject11TotalMarks()) ||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject12TotalMarks()) ||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject13TotalMarks()) ||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject14TotalMarks()) ||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject15TotalMarks()) ||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject16TotalMarks()) ||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject17TotalMarks()) ||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject18TotalMarks()) ||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject19TotalMarks()) ||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject20TotalMarks()) ||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& !StringUtils.isNumeric(detailmark.getTotalMarks()))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER, error);
				}
				return errors;
			}*/
			
			
			/*if(detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject1ObtainedMarks()) ||
					detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject2ObtainedMarks()) ||
					detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject3ObtainedMarks()) ||
					detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject4ObtainedMarks()) ||
					detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject5ObtainedMarks()) ||
					detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject6ObtainedMarks()) ||
					detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject7ObtainedMarks()) ||
					detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject8ObtainedMarks()) ||
					detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject9ObtainedMarks()) ||
					detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject10ObtainedMarks()) ||
					detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject11ObtainedMarks()) ||
					detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject12ObtainedMarks()) ||
					detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject13ObtainedMarks()) ||
					detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject14ObtainedMarks()) ||
					detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject15ObtainedMarks()) ||
					detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject16ObtainedMarks()) ||
					detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject17ObtainedMarks()) ||
					detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject18ObtainedMarks()) ||
					detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject19ObtainedMarks()) ||
					detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject20ObtainedMarks()) ||
					detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks())&& !StringUtils.isNumeric(detailmark.getTotalObtainedMarks()))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER, error);
				}
				return errors;
			}*/
			
		/*	if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && (detailmark.getSubject1TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject1TotalMarks().startsWith("0")) ||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& (detailmark.getSubject2TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject2TotalMarks().startsWith("0")) ||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& (detailmark.getSubject3TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject3TotalMarks().startsWith("0")) ||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& (detailmark.getSubject4TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject4TotalMarks().startsWith("0")) ||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& (detailmark.getSubject5TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject5TotalMarks().startsWith("0"))||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& (detailmark.getSubject6TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject6TotalMarks().startsWith("0")) ||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& (detailmark.getSubject7TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject7TotalMarks().startsWith("0")) ||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& (detailmark.getSubject8TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject8TotalMarks().startsWith("0")) ||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& (detailmark.getSubject9TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject9TotalMarks().startsWith("0")) ||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& (detailmark.getSubject10TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject10TotalMarks().startsWith("0"))||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& (detailmark.getSubject11TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject11TotalMarks().startsWith("0")) ||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& (detailmark.getSubject12TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject12TotalMarks().startsWith("0")) ||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& (detailmark.getSubject13TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject13TotalMarks().startsWith("0")) ||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& (detailmark.getSubject14TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject14TotalMarks().startsWith("0")) ||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& (detailmark.getSubject15TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject15TotalMarks().startsWith("0")) ||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& (detailmark.getSubject16TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject16TotalMarks().startsWith("0")) ||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& (detailmark.getSubject17TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject17TotalMarks().startsWith("0")) ||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& (detailmark.getSubject18TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject18TotalMarks().startsWith("0")) ||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& (detailmark.getSubject19TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject19TotalMarks().startsWith("0")) ||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& (detailmark.getSubject20TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject20TotalMarks().startsWith("0")) ||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& (detailmark.getTotalMarks().equalsIgnoreCase("0") || detailmark.getTotalMarks().startsWith("0")))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO);
				errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO, error);
				}
				return errors;
			}*/
			
			/*if((detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()))&& (detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())) ||
					(detailmark.getSubject2TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()))&& (detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())) ||
					(detailmark.getSubject3TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()))&& (detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())) ||
					(detailmark.getSubject4TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()))&& (detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())) ||
					(detailmark.getSubject5TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()))&& (detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())) ||
					(detailmark.getSubject6TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()))&& (detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())) ||
					(detailmark.getSubject7TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()))&& (detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())) ||
					(detailmark.getSubject8TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()))&& (detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())) ||
					(detailmark.getSubject9TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()))&& (detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())) ||
					(detailmark.getSubject10TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()))&& (detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())) ||
					(detailmark.getSubject11TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()))&& (detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) ||
					(detailmark.getSubject12TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()))&& (detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) ||
					(detailmark.getSubject13TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()))&& (detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) ||
					(detailmark.getSubject14TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()))&& (detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) ||
					(detailmark.getSubject15TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()))&& (detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) ||
					(detailmark.getSubject16TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()))&& (detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())) ||
					(detailmark.getSubject17TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()))&& (detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())) ||
					(detailmark.getSubject18TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()))&& (detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())) ||
					(detailmark.getSubject19TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()))&& (detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())) ||
					(detailmark.getSubject20TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()))&& (detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())) ||
					(detailmark.getTotalMarks()==null || StringUtils.isEmpty(detailmark.getTotalMarks())) && (detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()))
			){
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
				}
			}*/
			
			/*if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks())&& detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && Integer.parseInt(detailmark.getSubject1TotalMarks())< Integer.parseInt(detailmark.getSubject1ObtainedMarks())||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks()) && Integer.parseInt(detailmark.getSubject2TotalMarks())< Integer.parseInt(detailmark.getSubject2ObtainedMarks())||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks()) && Integer.parseInt(detailmark.getSubject3TotalMarks())< Integer.parseInt(detailmark.getSubject3ObtainedMarks())||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&&  detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks()) && Integer.parseInt(detailmark.getSubject4TotalMarks())< Integer.parseInt(detailmark.getSubject4ObtainedMarks())||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject5TotalMarks())< Integer.parseInt(detailmark.getSubject5ObtainedMarks())||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject6TotalMarks())< Integer.parseInt(detailmark.getSubject6ObtainedMarks())||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject7TotalMarks())< Integer.parseInt(detailmark.getSubject7ObtainedMarks())||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject8TotalMarks())< Integer.parseInt(detailmark.getSubject8ObtainedMarks())||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject9TotalMarks())< Integer.parseInt(detailmark.getSubject9ObtainedMarks())||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject10TotalMarks())< Integer.parseInt(detailmark.getSubject10ObtainedMarks())||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject11TotalMarks())< Integer.parseInt(detailmark.getSubject11ObtainedMarks())||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject12TotalMarks())< Integer.parseInt(detailmark.getSubject12ObtainedMarks())||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject13TotalMarks())< Integer.parseInt(detailmark.getSubject13ObtainedMarks())||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject14TotalMarks())< Integer.parseInt(detailmark.getSubject14ObtainedMarks())||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject15TotalMarks())< Integer.parseInt(detailmark.getSubject15ObtainedMarks())||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject16TotalMarks())< Integer.parseInt(detailmark.getSubject16ObtainedMarks())||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject17TotalMarks())< Integer.parseInt(detailmark.getSubject17ObtainedMarks())||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject18TotalMarks())< Integer.parseInt(detailmark.getSubject18ObtainedMarks())||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject19TotalMarks())< Integer.parseInt(detailmark.getSubject19ObtainedMarks())||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject20TotalMarks())< Integer.parseInt(detailmark.getSubject20ObtainedMarks())||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && Integer.parseInt(detailmark.getTotalMarks())< Integer.parseInt(detailmark.getTotalObtainedMarks())
			){
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
				}
			}
			*/
			
			
			
		}//checking over
		log.info("exit validateMarks...");
		return errors;
	}
	
	//raghu for degree
	
	/**
	 * @param detailmark
	 * @return
	 */
	public static ActionMessages validateMarksDegree(CandidateMarkTO detailmark,String CBCSS) {
		log.info("enter validateMarks...");
		ActionMessages errors= new ActionMessages();
		if(detailmark!=null){
			
			
			//raghu new
			
			
	    	if(CBCSS.equalsIgnoreCase("CBCSS") || CBCSS.equalsIgnoreCase("CBCSS NEW")){
				
	    		int totalSubjectCount=0;
				Set<String> dupSet=new HashSet<String>();
				
			if(detailmark.getSubjectid1()!=null && !detailmark.getSubjectid1().equalsIgnoreCase("") && 
					detailmark.getSubject1ObtainedMarks()!=null && !detailmark.getSubject1ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject1TotalMarks()!=null && !detailmark.getSubject1TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject1Credit()!=null && !detailmark.getSubject1Credit().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid1());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid1()==null || detailmark.getSubjectid1().equalsIgnoreCase("")) && 
					(detailmark.getSubject1ObtainedMarks()==null || detailmark.getSubject1ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject1TotalMarks()==null || detailmark.getSubject1TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject1Credit()==null || detailmark.getSubject1Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid2()!=null && !detailmark.getSubjectid2().equalsIgnoreCase("") && 
					detailmark.getSubject2ObtainedMarks()!=null && !detailmark.getSubject2ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject2TotalMarks()!=null && !detailmark.getSubject2TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject2Credit()!=null && !detailmark.getSubject2Credit().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid2());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid2()==null || detailmark.getSubjectid2().equalsIgnoreCase("")) && 
					(detailmark.getSubject2ObtainedMarks()==null || detailmark.getSubject2ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject2TotalMarks()==null || detailmark.getSubject2TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject2Credit()==null || detailmark.getSubject2Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid3()!=null && !detailmark.getSubjectid3().equalsIgnoreCase("") && 
					detailmark.getSubject3ObtainedMarks()!=null && !detailmark.getSubject3ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject3TotalMarks()!=null && !detailmark.getSubject3TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject3Credit()!=null && !detailmark.getSubject3Credit().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid3());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid3()==null || detailmark.getSubjectid3().equalsIgnoreCase("")) && 
					(detailmark.getSubject3ObtainedMarks()==null || detailmark.getSubject3ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject3TotalMarks()==null || detailmark.getSubject3TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject3Credit()==null || detailmark.getSubject3Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid4()!=null && !detailmark.getSubjectid4().equalsIgnoreCase("") && 
					detailmark.getSubject4ObtainedMarks()!=null && !detailmark.getSubject4ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject4TotalMarks()!=null && !detailmark.getSubject4TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject4Credit()!=null && !detailmark.getSubject4Credit().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid4());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid4()==null || detailmark.getSubjectid4().equalsIgnoreCase("")) && 
					(detailmark.getSubject4ObtainedMarks()==null || detailmark.getSubject4ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject4TotalMarks()==null || detailmark.getSubject4TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject4Credit()==null || detailmark.getSubject4Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid5()!=null && !detailmark.getSubjectid5().equalsIgnoreCase("") && 
					detailmark.getSubject5ObtainedMarks()!=null && !detailmark.getSubject5ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject5TotalMarks()!=null && !detailmark.getSubject5TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject5Credit()!=null && !detailmark.getSubject5Credit().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid5());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid5()==null || detailmark.getSubjectid5().equalsIgnoreCase("")) && 
					(detailmark.getSubject5ObtainedMarks()==null || detailmark.getSubject5ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject5TotalMarks()==null || detailmark.getSubject5TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject5Credit()==null || detailmark.getSubject5Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid6()!=null && !detailmark.getSubjectid6().equalsIgnoreCase("") && 
					detailmark.getSubject6ObtainedMarks()!=null && !detailmark.getSubject6ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject6TotalMarks()!=null && !detailmark.getSubject6TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject6Credit()!=null && !detailmark.getSubject6Credit().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid6());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid6()==null || detailmark.getSubjectid6().equalsIgnoreCase("")) && 
					(detailmark.getSubject6ObtainedMarks()==null || detailmark.getSubject6ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject6TotalMarks()==null || detailmark.getSubject6TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject6Credit()==null || detailmark.getSubject6Credit().equalsIgnoreCase(""))){
			}
			else{
				//Commented to avoid mandatory of English Subject
				/*if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}*/
					return errors;
			}
			if(detailmark.getSubjectid7()!=null && !detailmark.getSubjectid7().equalsIgnoreCase("") && 
					detailmark.getSubject7ObtainedMarks()!=null && !detailmark.getSubject7ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject7TotalMarks()!=null && !detailmark.getSubject7TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject7Credit()!=null && !detailmark.getSubject7Credit().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid7());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid7()==null || detailmark.getSubjectid7().equalsIgnoreCase("")) && 
					(detailmark.getSubject7ObtainedMarks()==null || detailmark.getSubject7ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject7TotalMarks()==null || detailmark.getSubject7TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject7Credit()==null || detailmark.getSubject7Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubjectid8()!=null && !detailmark.getSubjectid8().equalsIgnoreCase("") && 
					detailmark.getSubject8ObtainedMarks()!=null && !detailmark.getSubject8ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject8TotalMarks()!=null && !detailmark.getSubject8TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject8Credit()!=null && !detailmark.getSubject8Credit().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid8());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid8()==null || detailmark.getSubjectid8().equalsIgnoreCase("")) && 
					(detailmark.getSubject8ObtainedMarks()==null || detailmark.getSubject8ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject8TotalMarks()==null || detailmark.getSubject8TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject8Credit()==null || detailmark.getSubject8Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			
			if(detailmark.getSubjectid9()!=null && !detailmark.getSubjectid9().equalsIgnoreCase("") && 
					detailmark.getSubject9ObtainedMarks()!=null && !detailmark.getSubject9ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject9TotalMarks()!=null && !detailmark.getSubject9TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject9Credit()!=null && !detailmark.getSubject9Credit().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid9());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid9()==null || detailmark.getSubjectid9().equalsIgnoreCase("")) && 
					(detailmark.getSubject9ObtainedMarks()==null || detailmark.getSubject9ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject9TotalMarks()==null || detailmark.getSubject9TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject9Credit()==null || detailmark.getSubject9Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubjectid10()!=null && !detailmark.getSubjectid10().equalsIgnoreCase("") && 
					detailmark.getSubject10ObtainedMarks()!=null && !detailmark.getSubject10ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject10TotalMarks()!=null && !detailmark.getSubject10TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject10Credit()!=null && !detailmark.getSubject10Credit().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid10());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid10()==null || detailmark.getSubjectid10().equalsIgnoreCase("")) && 
					(detailmark.getSubject10ObtainedMarks()==null || detailmark.getSubject10ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject10TotalMarks()==null || detailmark.getSubject10TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject10Credit()==null || detailmark.getSubject10Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			
			//checking duplicate subjects
			if(totalSubjectCount!=dupSet.size()){
				errors.add("error", new ActionError("knowledgepro.admission.empty.err.message",
				"Duplicate Subjects should not select."));
	
				return errors;
			}
			
			
			//cbssc close
		}else{
			
		
			int totalSubjectCount=0;
			Set<String> dupSet=new HashSet<String>();
			
			
			
			if(detailmark.getSubjectid11()!=null && !detailmark.getSubjectid11().equalsIgnoreCase("") && 
					detailmark.getSubject11ObtainedMarks()!=null && !detailmark.getSubject11ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject11TotalMarks()!=null && !detailmark.getSubject11TotalMarks().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid11());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid11()==null || detailmark.getSubjectid11().equalsIgnoreCase("")) && 
					(detailmark.getSubject11ObtainedMarks()==null || detailmark.getSubject11ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject11TotalMarks()==null || detailmark.getSubject11TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubjectid12()!=null && !detailmark.getSubjectid12().equalsIgnoreCase("") && 
					detailmark.getSubject12ObtainedMarks()!=null && !detailmark.getSubject12ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject12TotalMarks()!=null && !detailmark.getSubject12TotalMarks().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid12());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid12()==null || detailmark.getSubjectid12().equalsIgnoreCase("")) && 
					(detailmark.getSubject12ObtainedMarks()==null || detailmark.getSubject12ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject12TotalMarks()==null || detailmark.getSubject12TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubjectid13()!=null && !detailmark.getSubjectid13().equalsIgnoreCase("") && 
					detailmark.getSubject13ObtainedMarks()!=null && !detailmark.getSubject13ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject13TotalMarks()!=null && !detailmark.getSubject13TotalMarks().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid13());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid13()==null || detailmark.getSubjectid13().equalsIgnoreCase("")) && 
					(detailmark.getSubject13ObtainedMarks()==null || detailmark.getSubject13ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject13TotalMarks()==null || detailmark.getSubject13TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubjectid14()!=null && !detailmark.getSubjectid14().equalsIgnoreCase("") && 
					detailmark.getSubject14ObtainedMarks()!=null && !detailmark.getSubject14ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject14TotalMarks()!=null && !detailmark.getSubject14TotalMarks().equalsIgnoreCase(""))
					{
				dupSet.add(detailmark.getSubjectid14());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid14()==null || detailmark.getSubjectid14().equalsIgnoreCase("")) && 
					(detailmark.getSubject14ObtainedMarks()==null || detailmark.getSubject14ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject14TotalMarks()==null || detailmark.getSubject14TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubjectid15()!=null && !detailmark.getSubjectid15().equalsIgnoreCase("") && 
					detailmark.getSubject15ObtainedMarks()!=null && !detailmark.getSubject15ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject15TotalMarks()!=null && !detailmark.getSubject15TotalMarks().equalsIgnoreCase("")
					)
					{
				dupSet.add(detailmark.getSubjectid15());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid15()==null || detailmark.getSubjectid15().equalsIgnoreCase("")) && 
					(detailmark.getSubject15ObtainedMarks()==null || detailmark.getSubject15ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject15TotalMarks()==null || detailmark.getSubject15TotalMarks().equalsIgnoreCase(""))
					){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubjectid16()!=null && !detailmark.getSubjectid16().equalsIgnoreCase("") && 
					detailmark.getSubject16ObtainedMarks()!=null && !detailmark.getSubject16ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject16TotalMarks()!=null && !detailmark.getSubject16TotalMarks().equalsIgnoreCase("")
					)
					{
				dupSet.add(detailmark.getSubjectid16());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid16()==null || detailmark.getSubjectid16().equalsIgnoreCase("")) && 
					(detailmark.getSubject16ObtainedMarks()==null || detailmark.getSubject16ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject16TotalMarks()==null || detailmark.getSubject16TotalMarks().equalsIgnoreCase(""))
					){
			}
			else{
				//Commented to avoid Mandotory of English Subject
				/*if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}*/
					return errors;
			}
			
			if(detailmark.getSubjectid17()!=null && !detailmark.getSubjectid17().equalsIgnoreCase("") && 
					detailmark.getSubject17ObtainedMarks()!=null && !detailmark.getSubject17ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject17TotalMarks()!=null && !detailmark.getSubject17TotalMarks().equalsIgnoreCase("")
					)
					{
				dupSet.add(detailmark.getSubjectid17());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid17()==null || detailmark.getSubjectid17().equalsIgnoreCase("")) && 
					(detailmark.getSubject17ObtainedMarks()==null || detailmark.getSubject17ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject17TotalMarks()==null || detailmark.getSubject17TotalMarks().equalsIgnoreCase(""))
					){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubjectid18()!=null && !detailmark.getSubjectid18().equalsIgnoreCase("") && 
					detailmark.getSubject18ObtainedMarks()!=null && !detailmark.getSubject18ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject18TotalMarks()!=null && !detailmark.getSubject18TotalMarks().equalsIgnoreCase("")
					)
					{
				dupSet.add(detailmark.getSubjectid18());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid18()==null || detailmark.getSubjectid18().equalsIgnoreCase("")) && 
					(detailmark.getSubject18ObtainedMarks()==null || detailmark.getSubject18ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject18TotalMarks()==null || detailmark.getSubject18TotalMarks().equalsIgnoreCase(""))
					){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubjectid19()!=null && !detailmark.getSubjectid19().equalsIgnoreCase("") && 
					detailmark.getSubject19ObtainedMarks()!=null && !detailmark.getSubject19ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject19TotalMarks()!=null && !detailmark.getSubject19TotalMarks().equalsIgnoreCase("")
					)
					{
				dupSet.add(detailmark.getSubjectid19());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid19()==null || detailmark.getSubjectid19().equalsIgnoreCase("")) && 
					(detailmark.getSubject19ObtainedMarks()==null || detailmark.getSubject19ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject19TotalMarks()==null || detailmark.getSubject19TotalMarks().equalsIgnoreCase(""))
					){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubjectid20()!=null && !detailmark.getSubjectid20().equalsIgnoreCase("") && 
					detailmark.getSubject20ObtainedMarks()!=null && !detailmark.getSubject20ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject20TotalMarks()!=null && !detailmark.getSubject20TotalMarks().equalsIgnoreCase("")
					)
					{
				dupSet.add(detailmark.getSubjectid20());
			     totalSubjectCount++;
					}
			else if((detailmark.getSubjectid20()==null || detailmark.getSubjectid20().equalsIgnoreCase("")) && 
					(detailmark.getSubject20ObtainedMarks()==null || detailmark.getSubject20ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject20TotalMarks()==null || detailmark.getSubject20TotalMarks().equalsIgnoreCase(""))
					){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			
			//checking duplicate subjects
			if(totalSubjectCount!=dupSet.size()){
				errors.add("error", new ActionError("knowledgepro.admission.empty.err.message",
				"Duplicate Subjects should not select."));
	
				return errors;
			}
			
		}// other close
		
			
	    	if(CBCSS.equalsIgnoreCase("CBCSS") || CBCSS.equalsIgnoreCase("CBCSS NEW")){
			
			//for other checking
			if(detailmark.getSubjectid1()!=null && !detailmark.getSubjectid1().equalsIgnoreCase("") && 
					(detailmark.getSubjectid1().equalsIgnoreCase("0") || detailmark.getSubjectid1().equalsIgnoreCase("0") || 
					detailmark.getSubjectid1().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther1()!=null && detailmark.getSubjectOther1().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther1()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;		
					}
				}
			
			if(detailmark.getSubjectid2()!=null && !detailmark.getSubjectid2().equalsIgnoreCase("") && 
					(detailmark.getSubjectid2().equalsIgnoreCase("0") || detailmark.getSubjectid2().equalsIgnoreCase("0") || 
					detailmark.getSubjectid2().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther2()!=null && detailmark.getSubjectOther2().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther2()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;	
					}
				}
			
			if(detailmark.getSubjectid3()!=null && !detailmark.getSubjectid3().equalsIgnoreCase("") && 
					(detailmark.getSubjectid3().equalsIgnoreCase("0") || detailmark.getSubjectid3().equalsIgnoreCase("0") || 
					detailmark.getSubjectid3().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther3()!=null && detailmark.getSubjectOther3().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther3()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;	
					}
				}
			
			if(detailmark.getSubjectid4()!=null && !detailmark.getSubjectid4().equalsIgnoreCase("") && 
					(detailmark.getSubjectid4().equalsIgnoreCase("0") || detailmark.getSubjectid4().equalsIgnoreCase("0") || 
					detailmark.getSubjectid4().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther4()!=null && detailmark.getSubjectOther4().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther4()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid5()!=null && !detailmark.getSubjectid5().equalsIgnoreCase("") && 
					(detailmark.getSubjectid5().equalsIgnoreCase("0") || detailmark.getSubjectid5().equalsIgnoreCase("0") || 
					detailmark.getSubjectid5().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther5()!=null && detailmark.getSubjectOther5().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther5()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid6()!=null && !detailmark.getSubjectid6().equalsIgnoreCase("") && 
					(detailmark.getSubjectid6().equalsIgnoreCase("0") || detailmark.getSubjectid6().equalsIgnoreCase("0") || 
					detailmark.getSubjectid6().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther6()!=null && detailmark.getSubjectOther6().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther6()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid7()!=null && !detailmark.getSubjectid7().equalsIgnoreCase("") && 
					(detailmark.getSubjectid7().equalsIgnoreCase("0") || detailmark.getSubjectid7().equalsIgnoreCase("0") || 
					detailmark.getSubjectid7().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther7()!=null && detailmark.getSubjectOther7().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther7()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid8()!=null && !detailmark.getSubjectid8().equalsIgnoreCase("") && 
					(detailmark.getSubjectid8().equalsIgnoreCase("0") || detailmark.getSubjectid8().equalsIgnoreCase("0") || 
					detailmark.getSubjectid8().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther8()!=null && detailmark.getSubjectOther8().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther8()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid9()!=null && !detailmark.getSubjectid9().equalsIgnoreCase("") && 
					(detailmark.getSubjectid9().equalsIgnoreCase("0") || detailmark.getSubjectid9().equalsIgnoreCase("0") || 
					detailmark.getSubjectid9().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther9()!=null && detailmark.getSubjectOther9().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther9()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							return errors;
							
					}
				}
			
			if(detailmark.getSubjectid10()!=null && !detailmark.getSubjectid10().equalsIgnoreCase("") && 
					(detailmark.getSubjectid10().equalsIgnoreCase("0") || detailmark.getSubjectid10().equalsIgnoreCase("0") || 
					detailmark.getSubjectid10().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther10()!=null && detailmark.getSubjectOther10().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther10()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							return errors;
							
					}
				}
			
	    	}//cbcsss close
	    	
	    	
	    	//other start
	    	else{
	    		
	    	
	    	
			if(detailmark.getSubjectid11()!=null && !detailmark.getSubjectid11().equalsIgnoreCase("") && 
					(detailmark.getSubjectid11().equalsIgnoreCase("0") || detailmark.getSubjectid11().equalsIgnoreCase("0") || 
					detailmark.getSubjectid11().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther11()!=null && detailmark.getSubjectOther11().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther11()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid12()!=null && !detailmark.getSubjectid12().equalsIgnoreCase("") && 
					(detailmark.getSubjectid12().equalsIgnoreCase("0") || detailmark.getSubjectid12().equalsIgnoreCase("0") || 
					detailmark.getSubjectid12().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther12()!=null && detailmark.getSubjectOther12().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther12()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid13()!=null && !detailmark.getSubjectid13().equalsIgnoreCase("") && 
					(detailmark.getSubjectid13().equalsIgnoreCase("0") || detailmark.getSubjectid13().equalsIgnoreCase("0") || 
					detailmark.getSubjectid13().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther13()!=null && detailmark.getSubjectOther13().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther13()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid14()!=null && !detailmark.getSubjectid14().equalsIgnoreCase("") && 
					(detailmark.getSubjectid14().equalsIgnoreCase("0") || detailmark.getSubjectid14().equalsIgnoreCase("0") || 
					detailmark.getSubjectid14().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther14()!=null && detailmark.getSubjectOther14().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther14()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid15()!=null && !detailmark.getSubjectid15().equalsIgnoreCase("") && 
					(detailmark.getSubjectid15().equalsIgnoreCase("0") || detailmark.getSubjectid15().equalsIgnoreCase("0") || 
					detailmark.getSubjectid15().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther15()!=null && detailmark.getSubjectOther15().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther15()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid16()!=null && !detailmark.getSubjectid16().equalsIgnoreCase("") && 
					(detailmark.getSubjectid16().equalsIgnoreCase("0") || detailmark.getSubjectid16().equalsIgnoreCase("0") || 
					detailmark.getSubjectid16().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther16()!=null && detailmark.getSubjectOther16().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther16()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid17()!=null && !detailmark.getSubjectid17().equalsIgnoreCase("") && 
					(detailmark.getSubjectid17().equalsIgnoreCase("0") || detailmark.getSubjectid17().equalsIgnoreCase("0") || 
					detailmark.getSubjectid17().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther17()!=null && detailmark.getSubjectOther17().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther17()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							return errors;
							
					}
				}
			
			if(detailmark.getSubjectid18()!=null && !detailmark.getSubjectid18().equalsIgnoreCase("") && 
					(detailmark.getSubjectid18().equalsIgnoreCase("0") || detailmark.getSubjectid18().equalsIgnoreCase("0") || 
					detailmark.getSubjectid18().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther18()!=null && detailmark.getSubjectOther18().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther18()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid19()!=null && !detailmark.getSubjectid19().equalsIgnoreCase("") && 
					(detailmark.getSubjectid19().equalsIgnoreCase("0") || detailmark.getSubjectid19().equalsIgnoreCase("0") || 
					detailmark.getSubjectid19().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther19()!=null && detailmark.getSubjectOther19().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther19()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			if(detailmark.getSubjectid20()!=null && !detailmark.getSubjectid20().equalsIgnoreCase("") && 
					(detailmark.getSubjectid20().equalsIgnoreCase("0") || detailmark.getSubjectid20().equalsIgnoreCase("0") || 
					detailmark.getSubjectid20().equalsIgnoreCase("0") ))
					{
				if(detailmark.getSubjectOther20()!=null && detailmark.getSubjectOther20().equalsIgnoreCase("") ){	
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				return errors;
					}else if(detailmark.getSubjectOther20()==null  ){	
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
							errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
							
							return errors;
					}
				}
			
			
	    	}//other close
	    	
			
			
			if(CBCSS.equalsIgnoreCase("CBCSS") || CBCSS.equalsIgnoreCase("CBCSS NEW")){
				
			
			
			if( detailmark.getSubject1ObtainedMarks()!=null && !(detailmark.getSubject1ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
			    return errors;
			}
			if(  detailmark.getSubject2ObtainedMarks()!=null && !(detailmark.getSubject2ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject3ObtainedMarks()!=null && !(detailmark.getSubject3ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject4ObtainedMarks()!=null &&  !(detailmark.getSubject4ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject5ObtainedMarks()!=null && !(detailmark.getSubject5ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject6ObtainedMarks()!=null && !(detailmark.getSubject6ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject7ObtainedMarks()!=null && !(detailmark.getSubject7ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			
			if(  detailmark.getSubject8ObtainedMarks()!=null && !(detailmark.getSubject8ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject9ObtainedMarks()!=null &&!(detailmark.getSubject9ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			
			if(  detailmark.getSubject10ObtainedMarks()!=null &&!(detailmark.getSubject10ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			
			
			//raghu write newly
			if(detailmark.getTotalMarksCGPA()!=null && StringUtils.isEmpty(detailmark.getTotalMarksCGPA()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarksCGPA())){
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getTotalObtainedMarksCGPA()!=null && StringUtils.isEmpty(detailmark.getTotalObtainedMarksCGPA()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarksCGPA())) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			
			
			
			//cbscc close
			}else{
				
			
			
			if(  detailmark.getSubject11ObtainedMarks()!=null &&!(detailmark.getSubject11ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			if(  detailmark.getSubject12ObtainedMarks()!=null &&!(detailmark.getSubject12ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}

			if(  detailmark.getSubject13ObtainedMarks()!=null &&!(detailmark.getSubject13ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			if(  detailmark.getSubject14ObtainedMarks()!=null &&!(detailmark.getSubject14ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			if(  detailmark.getSubject15ObtainedMarks()!=null &&!(detailmark.getSubject15ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			if(  detailmark.getSubject16ObtainedMarks()!=null &&!(detailmark.getSubject16ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			if(  detailmark.getSubject17ObtainedMarks()!=null &&!(detailmark.getSubject17ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			if(  detailmark.getSubject18ObtainedMarks()!=null &&!(detailmark.getSubject18ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			if(  detailmark.getSubject19ObtainedMarks()!=null &&!(detailmark.getSubject19ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			if(  detailmark.getSubject20ObtainedMarks()!=null &&!(detailmark.getSubject20ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				
				return errors;
			}
			
			
			//raghu write newly
			if(detailmark.getTotalMarks()!=null && StringUtils.isEmpty(detailmark.getTotalMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarks())){
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getTotalObtainedMarks()!=null && StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks())) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			
			}//other close
			
			//over
			
			
			/*mandatory subject mark check start */
			if(detailmark.isSubject1Mandatory() &&  (detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks())|| 
					detailmark.getSubject1ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())))		
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}
				return errors;
			}
			
			
			/*mandatory subject mark check end */
			
			
			
			
			
			
			
			
			
			if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && (detailmark.getSubject1TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject1TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& (detailmark.getSubject2TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject2TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& (detailmark.getSubject3TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject3TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& (detailmark.getSubject4TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject4TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& (detailmark.getSubject5TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject5TotalMarks().equalsIgnoreCase("."))||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& (detailmark.getSubject6TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject6TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& (detailmark.getSubject7TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject7TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& (detailmark.getSubject8TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject8TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& (detailmark.getSubject9TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject9TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& (detailmark.getSubject10TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject10TotalMarks().equalsIgnoreCase("."))||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& (detailmark.getSubject11TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject11TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& (detailmark.getSubject12TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject12TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& (detailmark.getSubject13TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject13TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& (detailmark.getSubject14TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject14TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& (detailmark.getSubject15TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject15TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& (detailmark.getSubject16TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject16TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& (detailmark.getSubject17TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject17TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& (detailmark.getSubject18TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject18TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& (detailmark.getSubject19TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject19TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& (detailmark.getSubject20TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject20TotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& (detailmark.getTotalMarks().equalsIgnoreCase("0") || detailmark.getTotalMarks().equalsIgnoreCase(".")) ||
					detailmark.getTotalMarksCGPA()!=null && !StringUtils.isEmpty(detailmark.getTotalMarksCGPA())&& (detailmark.getTotalMarksCGPA().equalsIgnoreCase("0") || detailmark.getTotalMarksCGPA().equalsIgnoreCase(".")))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO);
				errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO, error);
				}
				return errors;
			}
			
			
			
			if((detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()))&& (detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())) && (detailmark.getSubject1Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject1Credit())) ||
					(detailmark.getSubject2TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()))&& (detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())) && (detailmark.getSubject2Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject2Credit())) ||
					(detailmark.getSubject3TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()))&& (detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())) && (detailmark.getSubject3Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject3Credit())) ||
					(detailmark.getSubject4TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()))&& (detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())) && (detailmark.getSubject4Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject4Credit())) ||
					(detailmark.getSubject5TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()))&& (detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())) && (detailmark.getSubject5Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject5Credit())) ||
					(detailmark.getSubject6TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()))&& (detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())) && (detailmark.getSubject6Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject6Credit())) ||
					(detailmark.getSubject7TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()))&& (detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())) && (detailmark.getSubject7Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject7Credit())) ||
					(detailmark.getSubject8TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()))&& (detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())) && (detailmark.getSubject8Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject8Credit())) ||
					(detailmark.getSubject9TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()))&& (detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())) && (detailmark.getSubject9Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject9Credit())) ||
					(detailmark.getSubject10TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()))&& (detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())) && (detailmark.getSubject10Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject10Credit())) ||
					(detailmark.getSubject11TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()))&& (detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) ||
					(detailmark.getSubject12TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()))&& (detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) ||
					(detailmark.getSubject13TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()))&& (detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) ||
					(detailmark.getSubject14TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()))&& (detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) ||
					(detailmark.getSubject15TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()))&& (detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) ||
					(detailmark.getSubject16TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()))&& (detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())) ||
					(detailmark.getSubject17TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()))&& (detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())) ||
					(detailmark.getSubject18TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()))&& (detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())) ||
					(detailmark.getSubject19TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()))&& (detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())) ||
					(detailmark.getSubject20TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()))&& (detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())) ||
					(detailmark.getTotalMarks()==null || StringUtils.isEmpty(detailmark.getTotalMarks())) && (detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks())) || 
					(detailmark.getTotalMarks()==null || StringUtils.isEmpty(detailmark.getTotalMarksCGPA())) && (detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarksCGPA()))
			){
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
				}
			}
			
			if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks())&& detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && Float.parseFloat(detailmark.getSubject1TotalMarks())< Float.parseFloat(detailmark.getSubject1ObtainedMarks())||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks()) && Float.parseFloat(detailmark.getSubject2TotalMarks())< Float.parseFloat(detailmark.getSubject2ObtainedMarks())||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks()) && Float.parseFloat(detailmark.getSubject3TotalMarks())< Float.parseFloat(detailmark.getSubject3ObtainedMarks())||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&&  detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks()) && Float.parseFloat(detailmark.getSubject4TotalMarks())< Float.parseFloat(detailmark.getSubject4ObtainedMarks())||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject5TotalMarks())< Float.parseFloat(detailmark.getSubject5ObtainedMarks())||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject6TotalMarks())< Float.parseFloat(detailmark.getSubject6ObtainedMarks())||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject7TotalMarks())< Float.parseFloat(detailmark.getSubject7ObtainedMarks())||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject8TotalMarks())< Float.parseFloat(detailmark.getSubject8ObtainedMarks())||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject9TotalMarks())< Float.parseFloat(detailmark.getSubject9ObtainedMarks())||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject10TotalMarks())< Float.parseFloat(detailmark.getSubject10ObtainedMarks())||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject11TotalMarks())< Float.parseFloat(detailmark.getSubject11ObtainedMarks())||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject12TotalMarks())< Float.parseFloat(detailmark.getSubject12ObtainedMarks())||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject13TotalMarks())< Float.parseFloat(detailmark.getSubject13ObtainedMarks())||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject14TotalMarks())< Float.parseFloat(detailmark.getSubject14ObtainedMarks())||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject15TotalMarks())< Float.parseFloat(detailmark.getSubject15ObtainedMarks())||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject16TotalMarks())< Float.parseFloat(detailmark.getSubject16ObtainedMarks())||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject17TotalMarks())< Float.parseFloat(detailmark.getSubject17ObtainedMarks())||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject18TotalMarks())< Float.parseFloat(detailmark.getSubject18ObtainedMarks())||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject19TotalMarks())< Float.parseFloat(detailmark.getSubject19ObtainedMarks())||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject20TotalMarks())< Float.parseFloat(detailmark.getSubject20ObtainedMarks())||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && Float.parseFloat(detailmark.getTotalMarks())< Float.parseFloat(detailmark.getTotalObtainedMarks()) || 
					detailmark.getTotalMarksCGPA()!=null && !StringUtils.isEmpty(detailmark.getTotalMarksCGPA())&& detailmark.getTotalObtainedMarksCGPA()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarksCGPA()) && Float.parseFloat(detailmark.getTotalMarksCGPA())< Float.parseFloat(detailmark.getTotalObtainedMarksCGPA())
					
			){
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
				}
			}
			
			
			
			
			//added by mahi start
			int count=20;
			int compareCount=0;
			if(detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) || detailmark.getSubject1ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject2TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) || detailmark.getSubject2ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject3TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) || detailmark.getSubject3ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject4TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) || detailmark.getSubject4ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject5TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) || detailmark.getSubject5ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject6TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) || detailmark.getSubject6ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject7TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) || detailmark.getSubject7ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject8TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) || detailmark.getSubject8ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject9TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) || detailmark.getSubject9ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject10TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) || detailmark.getSubject10ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject11TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) || detailmark.getSubject11ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject12TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) || detailmark.getSubject12ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject13TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) || detailmark.getSubject13ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject14TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) || detailmark.getSubject14ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject15TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) || detailmark.getSubject15ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject16TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) || detailmark.getSubject16ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject17TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) || detailmark.getSubject17ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject18TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) || detailmark.getSubject18ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject19TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) || detailmark.getSubject19ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())){
				compareCount++;
			}if(detailmark.getSubject20TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) || detailmark.getSubject20ObtainedMarks()==null || StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())){
				compareCount++;
			}
			if (compareCount==count) {
				errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Please fill the Marks.."));
			}
			
			/*if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject1TotalMarks()) ||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject2TotalMarks()) ||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject3TotalMarks()) ||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject4TotalMarks()) ||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject5TotalMarks()) ||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject6TotalMarks()) ||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject7TotalMarks()) ||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject8TotalMarks()) ||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject9TotalMarks()) ||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject10TotalMarks()) ||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject11TotalMarks()) ||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject12TotalMarks()) ||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject13TotalMarks()) ||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject14TotalMarks()) ||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject15TotalMarks()) ||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject16TotalMarks()) ||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject17TotalMarks()) ||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject18TotalMarks()) ||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject19TotalMarks()) ||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject20TotalMarks()) ||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& !StringUtils.isNumeric(detailmark.getTotalMarks()))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER, error);
				}
				return errors;
			}*/
			
			/*if(detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject1ObtainedMarks()) ||
					detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject2ObtainedMarks()) ||
					detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject3ObtainedMarks()) ||
					detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject4ObtainedMarks()) ||
					detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject5ObtainedMarks()) ||
					detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject6ObtainedMarks()) ||
					detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject7ObtainedMarks()) ||
					detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject8ObtainedMarks()) ||
					detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject9ObtainedMarks()) ||
					detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject10ObtainedMarks()) ||
					detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject11ObtainedMarks()) ||
					detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject12ObtainedMarks()) ||
					detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject13ObtainedMarks()) ||
					detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject14ObtainedMarks()) ||
					detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject15ObtainedMarks()) ||
					detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject16ObtainedMarks()) ||
					detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject17ObtainedMarks()) ||
					detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject18ObtainedMarks()) ||
					detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject19ObtainedMarks()) ||
					detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject20ObtainedMarks()) ||
					detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks())&& !StringUtils.isNumeric(detailmark.getTotalObtainedMarks()))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER, error);
				}
				return errors;
			}*/
			
			/*if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && (detailmark.getSubject1TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject1TotalMarks().startsWith("0")) ||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& (detailmark.getSubject2TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject2TotalMarks().startsWith("0")) ||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& (detailmark.getSubject3TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject3TotalMarks().startsWith("0")) ||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& (detailmark.getSubject4TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject4TotalMarks().startsWith("0")) ||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& (detailmark.getSubject5TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject5TotalMarks().startsWith("0"))||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& (detailmark.getSubject6TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject6TotalMarks().startsWith("0")) ||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& (detailmark.getSubject7TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject7TotalMarks().startsWith("0")) ||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& (detailmark.getSubject8TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject8TotalMarks().startsWith("0")) ||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& (detailmark.getSubject9TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject9TotalMarks().startsWith("0")) ||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& (detailmark.getSubject10TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject10TotalMarks().startsWith("0"))||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& (detailmark.getSubject11TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject11TotalMarks().startsWith("0")) ||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& (detailmark.getSubject12TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject12TotalMarks().startsWith("0")) ||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& (detailmark.getSubject13TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject13TotalMarks().startsWith("0")) ||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& (detailmark.getSubject14TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject14TotalMarks().startsWith("0")) ||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& (detailmark.getSubject15TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject15TotalMarks().startsWith("0")) ||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& (detailmark.getSubject16TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject16TotalMarks().startsWith("0")) ||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& (detailmark.getSubject17TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject17TotalMarks().startsWith("0")) ||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& (detailmark.getSubject18TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject18TotalMarks().startsWith("0")) ||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& (detailmark.getSubject19TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject19TotalMarks().startsWith("0")) ||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& (detailmark.getSubject20TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject20TotalMarks().startsWith("0")) ||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& (detailmark.getTotalMarks().equalsIgnoreCase("0") || detailmark.getTotalMarks().startsWith("0")))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO);
				errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_GRTZERO, error);
				}
				return errors;
			}
			*/
			
			/*if((detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()))&& (detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())) ||
					(detailmark.getSubject2TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()))&& (detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())) ||
					(detailmark.getSubject3TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()))&& (detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())) ||
					(detailmark.getSubject4TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()))&& (detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())) ||
					(detailmark.getSubject5TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()))&& (detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())) ||
					(detailmark.getSubject6TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()))&& (detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())) ||
					(detailmark.getSubject7TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()))&& (detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())) ||
					(detailmark.getSubject8TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()))&& (detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())) ||
					(detailmark.getSubject9TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()))&& (detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())) ||
					(detailmark.getSubject10TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()))&& (detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())) ||
					(detailmark.getSubject11TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()))&& (detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) ||
					(detailmark.getSubject12TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()))&& (detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) ||
					(detailmark.getSubject13TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()))&& (detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) ||
					(detailmark.getSubject14TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()))&& (detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) ||
					(detailmark.getSubject15TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()))&& (detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) ||
					(detailmark.getSubject16TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()))&& (detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())) ||
					(detailmark.getSubject17TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()))&& (detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())) ||
					(detailmark.getSubject18TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()))&& (detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())) ||
					(detailmark.getSubject19TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()))&& (detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())) ||
					(detailmark.getSubject20TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()))&& (detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())) ||
					(detailmark.getTotalMarks()==null || StringUtils.isEmpty(detailmark.getTotalMarks())) && (detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()))
			){
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
				}
			}
			*/
			/*if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks())&& detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && Integer.parseInt(detailmark.getSubject1TotalMarks())< Integer.parseInt(detailmark.getSubject1ObtainedMarks())||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks()) && Integer.parseInt(detailmark.getSubject2TotalMarks())< Integer.parseInt(detailmark.getSubject2ObtainedMarks())||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks()) && Integer.parseInt(detailmark.getSubject3TotalMarks())< Integer.parseInt(detailmark.getSubject3ObtainedMarks())||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&&  detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks()) && Integer.parseInt(detailmark.getSubject4TotalMarks())< Integer.parseInt(detailmark.getSubject4ObtainedMarks())||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject5TotalMarks())< Integer.parseInt(detailmark.getSubject5ObtainedMarks())||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject6TotalMarks())< Integer.parseInt(detailmark.getSubject6ObtainedMarks())||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject7TotalMarks())< Integer.parseInt(detailmark.getSubject7ObtainedMarks())||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject8TotalMarks())< Integer.parseInt(detailmark.getSubject8ObtainedMarks())||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject9TotalMarks())< Integer.parseInt(detailmark.getSubject9ObtainedMarks())||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject10TotalMarks())< Integer.parseInt(detailmark.getSubject10ObtainedMarks())||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject11TotalMarks())< Integer.parseInt(detailmark.getSubject11ObtainedMarks())||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject12TotalMarks())< Integer.parseInt(detailmark.getSubject12ObtainedMarks())||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject13TotalMarks())< Integer.parseInt(detailmark.getSubject13ObtainedMarks())||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject14TotalMarks())< Integer.parseInt(detailmark.getSubject14ObtainedMarks())||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject15TotalMarks())< Integer.parseInt(detailmark.getSubject15ObtainedMarks())||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject16TotalMarks())< Integer.parseInt(detailmark.getSubject16ObtainedMarks())||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject17TotalMarks())< Integer.parseInt(detailmark.getSubject17ObtainedMarks())||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject18TotalMarks())< Integer.parseInt(detailmark.getSubject18ObtainedMarks())||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject19TotalMarks())< Integer.parseInt(detailmark.getSubject19ObtainedMarks())||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject20TotalMarks())< Integer.parseInt(detailmark.getSubject20ObtainedMarks())||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && Integer.parseInt(detailmark.getTotalMarks())< Integer.parseInt(detailmark.getTotalObtainedMarks())
			){
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
				}
			}
			*/
			
			/*//////
			if(detailmark.isSemesterMark()){
				if(detailmark.getSubject1_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject1_languagewise_TotalMarks()) ||
						detailmark.getSubject2_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject2TotalMarks()) ||
						detailmark.getSubject3_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject3TotalMarks()) ||
						detailmark.getSubject4_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject4TotalMarks()) ||
						detailmark.getSubject5_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject5TotalMarks()) ||
						detailmark.getSubject6_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject6_languagewise_TotalMarks()) ||
						detailmark.getSubject7_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject7_languagewise_TotalMarks()) ||
						detailmark.getSubject8_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject8_languagewise_TotalMarks()) ||
						detailmark.getSubject9_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject9_languagewise_TotalMarks()) ||
						detailmark.getSubject10_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks())&& !StringUtils.isNumeric(detailmark.getSubject10_languagewise_TotalMarks()) ||
						detailmark.getTotal_languagewise_Marks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks())&& !StringUtils.isNumeric(detailmark.getTotal_languagewise_Marks()))
				{
					if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
					errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER, error);
					}
					return errors;
				}
				if(detailmark.getSubject1_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject1_languagewise_ObtainedMarks()) ||
						detailmark.getSubject2_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject2_languagewise_ObtainedMarks()) ||
						detailmark.getSubject3_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject3_languagewise_ObtainedMarks()) ||
						detailmark.getSubject4_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject4_languagewise_ObtainedMarks()) ||
						detailmark.getSubject5_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject5_languagewise_ObtainedMarks()) ||
						detailmark.getSubject6_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject6_languagewise_ObtainedMarks()) ||
						detailmark.getSubject7_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject7_languagewise_ObtainedMarks()) ||
						detailmark.getSubject8_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject8_languagewise_ObtainedMarks()) ||
						detailmark.getSubject9_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject9_languagewise_ObtainedMarks()) ||
						detailmark.getSubject10_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getSubject10_languagewise_ObtainedMarks()) ||
						detailmark.getTotal_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks())&& !StringUtils.isNumeric(detailmark.getTotal_languagewise_ObtainedMarks()))
				{
					if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
					errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER, error);
					}
					return errors;
				}
				
				if((detailmark.getSubject1_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks()))&& (detailmark.getSubject1_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject2_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2_languagewise_TotalMarks()))&& (detailmark.getSubject2_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject3_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3_languagewise_TotalMarks()))&& (detailmark.getSubject3_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject4_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4_languagewise_TotalMarks()))&& (detailmark.getSubject4_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject5_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5_languagewise_TotalMarks()))&& (detailmark.getSubject5_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject6_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks()))&& (detailmark.getSubject6_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject7_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks()))&& (detailmark.getSubject7_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject8_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks()))&& (detailmark.getSubject8_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject9_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks()))&& (detailmark.getSubject9_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks())) ||
						(detailmark.getSubject10_languagewise_TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks()))&& (detailmark.getSubject10_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks())) ||
						(detailmark.getTotal_languagewise_Marks()==null || StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks())) && (detailmark.getTotal_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks()))
				){
					if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
					errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
					}
				}
				
				if(detailmark.getSubject1_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks())&& detailmark.getSubject1_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject1_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject1_languagewise_ObtainedMarks())||
						detailmark.getSubject2_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_TotalMarks())&& detailmark.getSubject2_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject2_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject2_languagewise_ObtainedMarks())||
						detailmark.getSubject3_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_TotalMarks())&& detailmark.getSubject3_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject3_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject3_languagewise_ObtainedMarks())||
						detailmark.getSubject4_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_TotalMarks())&&  detailmark.getSubject4_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject4_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject4_languagewise_ObtainedMarks())||
						detailmark.getSubject5_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_TotalMarks())&& detailmark.getSubject5_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject5_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject5_languagewise_ObtainedMarks())||
						detailmark.getSubject6_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks())&& detailmark.getSubject6_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject6_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject6_languagewise_ObtainedMarks())||
						detailmark.getSubject7_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks())&& detailmark.getSubject7_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject7_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject7_languagewise_ObtainedMarks())||
						detailmark.getSubject8_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks())&& detailmark.getSubject8_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject8_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject8_languagewise_ObtainedMarks())||
						detailmark.getSubject9_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks())&& detailmark.getSubject9_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject9_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject9_languagewise_ObtainedMarks())||
						detailmark.getSubject10_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks())&& detailmark.getSubject10_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks()) &&  Integer.parseInt(detailmark.getSubject10_languagewise_TotalMarks())< Integer.parseInt(detailmark.getSubject10_languagewise_ObtainedMarks())||
						detailmark.getTotal_languagewise_Marks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks())&& detailmark.getTotal_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getTotal_languagewise_Marks())< Integer.parseInt(detailmark.getTotal_languagewise_ObtainedMarks())
				){
					if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
					errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
					}
				}
			}*/
		}//checking over
		
		log.info("exit validateMarks...");
		return errors;
	}
	
	
	
	/**
	 * compares birth date with pass year,month
	 * @param yearPassing
	 * @param monthPassing
	 * @param dateOfBirth
	 * @return
	 */
	private boolean isPassYearGreaterThanBirth(int yearPassing,int monthPassing,
			String dateOfBirth) {
		boolean result=false;
		if(yearPassing!=0 && dateOfBirth!=null && !StringUtils.isEmpty(dateOfBirth)){
			String formattedString=CommonUtil.ConvertStringToDateFormat(dateOfBirth, OnlineApplicationAction.FROM_DATEFORMAT,OnlineApplicationAction.TO_DATEFORMAT);
			Date birthdate = new Date(formattedString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(birthdate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Calendar cal2 = Calendar.getInstance();
//			cal2.setTime(birthdate);
			cal2.set(Calendar.DATE, 1);
			if(monthPassing >1)
			cal2.set(Calendar.MONTH, monthPassing-1);
			else
				cal2.set(Calendar.MONTH,1);
			cal2.set(Calendar.YEAR, yearPassing);
			cal2.set(Calendar.HOUR_OF_DAY, 0);
			cal2.set(Calendar.MINUTE, 0);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND, 0);
			// if pass year larger than birth year
//			if(yearPassing== cal.get(Calendar.YEAR)|| yearPassing> cal.get(Calendar.YEAR))
			if(cal2.getTime().after(cal.getTime()))
				result=true;
		}
		return result;
	}
	
	/**
	 * compares curent date with pass year,month
	 * @param yearPassing
	 * @param monthPassing
	 * @param dateOfBirth
	 * @return
	 */
	private boolean isPassYearGreaterThanToday(int yearPassing,int monthPassing,
			Date today) {
		boolean result=false;
		if(yearPassing!=0 && monthPassing!=0 && today!=null){
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Calendar cal2 = Calendar.getInstance();
//			cal2.setTime(birthdate);
			cal2.set(Calendar.DATE, 1);
			if(monthPassing >1)
				cal2.set(Calendar.MONTH, monthPassing-1);
			else
				cal2.set(Calendar.MONTH,1);
			cal2.set(Calendar.YEAR, yearPassing);
			cal2.set(Calendar.HOUR_OF_DAY, 0);
			cal2.set(Calendar.MINUTE, 0);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND, 0);
			// if pass year larger than birth year
//			if(yearPassing== cal.get(Calendar.YEAR)|| yearPassing> cal.get(Calendar.YEAR))
			if(cal2.getTime().after(cal.getTime()))
				result=true;
		}
		return result;
	}
	/**
	 * validate programtype,course and program
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateEditEducationDetails(ActionMessages errors,
			OnlineApplicationForm admForm) {
		log.info("enter validate education...");
		
		int doctypeId12=CMSConstants.CLASS12_DOCTYPEID;
		int doctypeIdDEG=CMSConstants.DEGREE_DOCTYPEID;
		int year10=0;
		int year12=0;
		int yearDeg=0;
		
		List<EdnQualificationTO> qualifications=admForm.getApplicantDetails().getEdnQualificationList();
		if(qualifications!=null){
			Iterator<EdnQualificationTO> qualificationTO= qualifications.iterator();
			while (qualificationTO.hasNext()) {
				EdnQualificationTO qualfTO = (EdnQualificationTO) qualificationTO
						.next();
				if(admForm.getCourseId()!=null && !admForm.getCourseId().isEmpty()){
			
				
				if((qualfTO.getUniversityId()==null ||(qualfTO.getUniversityId().length()==0 )|| qualfTO.getUniversityId().equalsIgnoreCase("Other")) && (qualfTO.getUniversityOthers()==null ||(qualfTO.getUniversityOthers().trim().length()==0 )))
				{
						if(errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED,error);
						}
				}
				/*if((qualfTO.getInstitutionId()==null ||qualfTO.getInstitutionId().length()==0 )||(qualfTO.getInstitutionId().equalsIgnoreCase("Other") && (qualfTO.getOtherInstitute()==null ||(qualfTO.getOtherInstitute().trim().length()==0 ))))
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED,error);
						}
				}
				*/if( qualfTO.getOtherInstitute()==null ||(qualfTO.getOtherInstitute().trim().length()==0 ))
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED,error);
						}
				}
				if(/*qualfTO.isExamRequired()*/qualfTO.isExamConfigured()&& (qualfTO.getSelectedExamId()==null || StringUtils.isEmpty(qualfTO.getSelectedExamId())))
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED,error);
						}
				}
				//if(!qualfTO.isBlockedMarks()){
				if(qualfTO.getYearPassing()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED,error);
						}
				}else{
					boolean valid=CommonUtil.isFutureNotCurrentYear(qualfTO.getYearPassing());
					if(valid){
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE);
							errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE,error);
						}
					}
					
				}
				if(qualfTO.getMonthPassing()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED,error);
						}
				}
				if(qualfTO.isLastExam() && (qualfTO.getPreviousRegNo()==null || StringUtils.isEmpty(qualfTO.getPreviousRegNo().trim()) ))
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED,error);
					}
				}
				
				
				//setting years
				
				if(qualfTO.getDocTypeId()==doctypeIdDEG){
					yearDeg=qualfTO.getYearPassing();
				}else if(qualfTO.getDocTypeId()==doctypeId12){
					year12=qualfTO.getYearPassing();
				}
				 else{
					year10=qualfTO.getYearPassing();
				}
					
				//this is for class 12 and degree check only
				if(qualfTO.getDocTypeId()==doctypeId12 || qualfTO.getDocTypeId()==doctypeIdDEG){
				
				if(qualfTO.getNoOfAttempts()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED,error);
						}
				}
				}
				
				
				if(qualfTO.isConsolidated()){
					
					//this is for class 12 and degree
					if(qualfTO.getDocTypeId()==doctypeId12 || qualfTO.getDocTypeId()==doctypeIdDEG){
					
					if(qualfTO.getTotalMarks()==null || StringUtils.isEmpty(qualfTO.getTotalMarks()))
					{
							if (errors.get(CMSConstants.ADMISSIONFORM_MAXMARKS_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MAXMARKS_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MAXMARKS_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_MAXMARKS_REQUIRED,error);
							}
					}else if(!CommonUtil.isValidDecimal(qualfTO.getTotalMarks())){
						if (errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
							errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER,error);
						}
						
					}else if(qualfTO.getTotalMarks()!=null && qualfTO.getTotalMarks().equalsIgnoreCase("0") || qualfTO.getTotalMarks().startsWith("0.0")){
						if (errors.get(CMSConstants.ADMISSIONFORM_MAXMARKSGRT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MAXMARKSGRT_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MAXMARKSGRT_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_MAXMARKSGRT_REQUIRED,error);
						}
					}
					 
					if(qualfTO.getMarksObtained()==null || StringUtils.isEmpty(qualfTO.getMarksObtained()))
					{
							if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED,error);
							}
					}else if(!CommonUtil.isValidDecimal(qualfTO.getMarksObtained())){
						if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER,error);
						}
					}
					else if(qualfTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualfTO.getMarksObtained()) 
							&& CommonUtil.isValidDecimal(qualfTO.getMarksObtained()) &&
							qualfTO.getTotalMarks()!=null && !StringUtils.isEmpty(qualfTO.getTotalMarks()) 
							&& CommonUtil.isValidDecimal(qualfTO.getTotalMarks())
							&& Double.parseDouble(qualfTO.getTotalMarks())< Double.parseDouble(qualfTO.getMarksObtained()))
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,error);
						}
					}
					
					}
					
					//calculate only percenatge
					
					if(qualfTO.getPercentage()==null || StringUtils.isEmpty(qualfTO.getPercentage()))
					{
							if (errors.get(CMSConstants.ADMISSIONFORM_PERCENTAGE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERCENTAGE_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PERCENTAGE_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_PERCENTAGE_REQUIRED,error);
							}
					}else if(!CommonUtil.isValidDecimal(qualfTO.getPercentage())){
						if (errors.get(CMSConstants.ADMISSIONFORM_PERCENTAGE_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERCENTAGE_NOTINTEGER).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PERCENTAGE_NOTINTEGER);
							errors.add(CMSConstants.ADMISSIONFORM_PERCENTAGE_NOTINTEGER,error);
						}
					}else if(Float.parseFloat(qualfTO.getPercentage())>100){
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message"," Percentage should not be  more than 100."));
					}
					
					
				}else{
					if(qualfTO.isSemesterWise()){
						if(qualfTO.getSemesterList()==null)
						{
								if (errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED).hasNext()) {
									ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED);
									errors.add(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED,error);
								}
						}
						if(admForm.getTotalMarkWithLanguage()!=null && !admForm.getTotalMarkWithLanguage().isEmpty() 
								&& (admForm.getTotalMarkWithLanguage().equalsIgnoreCase("0") || admForm.getTotalMarkWithLanguage().startsWith("0.0"))){
							if (errors.get(CMSConstants.ADMISSIONFORM_SEMESTERTOTALMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SEMESTERTOTALMARK_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_SEMESTERTOTALMARK_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_SEMESTERTOTALMARK_REQUIRED,error);
							}
						}
						if(admForm.getTotalMarkWithoutLan()!=null && !admForm.getTotalMarkWithoutLan().isEmpty() 
								&& (admForm.getTotalMarkWithoutLan().equalsIgnoreCase("0") || admForm.getTotalMarkWithoutLan().startsWith("0.0"))){
							if (errors.get(CMSConstants.ADMISSIONFORM_SEMESTERTOTALMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SEMESTERTOTALMARK_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_SEMESTERTOTALMARK_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_SEMESTERTOTALMARK_REQUIRED,error);
							}
						}	
							
					}else if(qualfTO.getDetailmark()==null)
					{
							if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,error);
							}
					}//raghu
					
					
					//this is for class 12 and degree
					if(qualfTO.getDocTypeId()==doctypeId12 || qualfTO.getDocTypeId()==doctypeIdDEG){
						
					
					 if((qualfTO.getDetailmark().getSubjectid1() == null || qualfTO.getDetailmark().getSubjectid1().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid2() == null || qualfTO.getDetailmark().getSubjectid2().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid3() == null || qualfTO.getDetailmark().getSubjectid3().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid4() == null || qualfTO.getDetailmark().getSubjectid4().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid5() == null || qualfTO.getDetailmark().getSubjectid5().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid6() == null || qualfTO.getDetailmark().getSubjectid6().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid7() == null || qualfTO.getDetailmark().getSubjectid7().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid8() == null || qualfTO.getDetailmark().getSubjectid8().isEmpty()) &&  
							(qualfTO.getDetailmark().getSubjectid9() == null || qualfTO.getDetailmark().getSubjectid9().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid10() == null || qualfTO.getDetailmark().getSubjectid10().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid11() == null || qualfTO.getDetailmark().getSubjectid11().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid12() == null || qualfTO.getDetailmark().getSubjectid12().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid13() == null || qualfTO.getDetailmark().getSubjectid13().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid14() == null || qualfTO.getDetailmark().getSubjectid14().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid15() == null || qualfTO.getDetailmark().getSubjectid15().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid16() == null || qualfTO.getDetailmark().getSubjectid16().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid17() == null || qualfTO.getDetailmark().getSubjectid17().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid18() == null || qualfTO.getDetailmark().getSubjectid18().isEmpty()) &&  
							(qualfTO.getDetailmark().getSubjectid19() == null || qualfTO.getDetailmark().getSubjectid19().isEmpty()) &&
							(qualfTO.getDetailmark().getSubjectid20() == null || qualfTO.getDetailmark().getSubjectid20().isEmpty()) ) {
							
					
						    ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
						    errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,error);
						
					}
					
					else if((qualfTO.getDetailmark().getTotalMarks() == null || qualfTO.getDetailmark().getTotalMarks().isEmpty()) &&
							(qualfTO.getDetailmark().getTotalObtainedMarks() == null || qualfTO.getDetailmark().getTotalObtainedMarks().isEmpty()) &&
							(qualfTO.getDetailmark().getTotalMarksCGPA() == null || qualfTO.getDetailmark().getTotalMarksCGPA().isEmpty()) &&
							(qualfTO.getDetailmark().getTotalObtainedMarksCGPA() == null || qualfTO.getDetailmark().getTotalObtainedMarksCGPA().isEmpty()) ) {
						
						
					        ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
					        errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,error);
					
				    }
					 
					 if(errors.size()==0){
						 
					 if(qualfTO.getDocTypeId()==doctypeId12 ){
						 errors=validateMarksClass12(qualfTO.getDetailmark());
						 if(errors.size()!=0){
							 return errors;
						 }
					 }
					 if(qualfTO.getDocTypeId()==doctypeIdDEG){
						 errors=validateMarksDegree(qualfTO.getDetailmark(),admForm.getPatternofStudy());
						 if(errors.size()!=0){
							 return errors;
						 }
					 }
						 
					 } 
					 
					
					}
					
					//this is for claas 10 and others
					if(qualfTO.getDocTypeId()!=doctypeId12 && qualfTO.getDocTypeId()!=doctypeIdDEG){
						
						
						 if((qualfTO.getDetailmark().getSubject1() == null || qualfTO.getDetailmark().getSubject1().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject2() == null || qualfTO.getDetailmark().getSubject2().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject3() == null || qualfTO.getDetailmark().getSubject3().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject4() == null || qualfTO.getDetailmark().getSubject4().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject5() == null || qualfTO.getDetailmark().getSubject5().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject6() == null || qualfTO.getDetailmark().getSubject6().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject7() == null || qualfTO.getDetailmark().getSubject7().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject8() == null || qualfTO.getDetailmark().getSubject8().isEmpty()) &&  
								(qualfTO.getDetailmark().getSubject9() == null || qualfTO.getDetailmark().getSubject9().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject10() == null || qualfTO.getDetailmark().getSubject10().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject11() == null || qualfTO.getDetailmark().getSubject11().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject12() == null || qualfTO.getDetailmark().getSubject12().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject13() == null || qualfTO.getDetailmark().getSubject13().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject14() == null || qualfTO.getDetailmark().getSubject14().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject15() == null || qualfTO.getDetailmark().getSubject15().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject16() == null || qualfTO.getDetailmark().getSubject16().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject17() == null || qualfTO.getDetailmark().getSubject17().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject18() == null || qualfTO.getDetailmark().getSubject18().isEmpty()) &&  
								(qualfTO.getDetailmark().getSubject19() == null || qualfTO.getDetailmark().getSubject19().isEmpty()) &&
								(qualfTO.getDetailmark().getSubject20() == null || qualfTO.getDetailmark().getSubject20().isEmpty()) ) {
								
						
							    ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
							    errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,error);
							
						}
						
						else if((qualfTO.getDetailmark().getTotalMarks() == null || qualfTO.getDetailmark().getTotalMarks().isEmpty()) &&
								(qualfTO.getDetailmark().getTotalObtainedMarks() == null || qualfTO.getDetailmark().getTotalObtainedMarks().isEmpty()) ) {
							
							
						        ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
						        errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,error);
						
					    }
						 
						 
						 if(errors.size()==0){
							 errors=validateMarks(qualfTO.getDetailmark()); 
							 if(errors.size()!=0){
								 return errors;
							 }
						 } 
						
					
				}
					
					
				}	
				
				if(qualfTO.getYearPassing()!=0 && qualfTO.getMonthPassing()!=0){
					if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
						boolean futurethanBirth=isPassYearGreaterThanBirth(qualfTO.getYearPassing(),qualfTO.getMonthPassing(),admForm.getApplicantDetails().getPersonalData().getDob());
						if(!futurethanBirth){
							if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE);
								errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE,error);
							}
						}
					}
				}
				
			//}
		  }
		}
			
			
			//year check
			if(year10!=0 && year12!=0){
				if(year12<=year10){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message"," 10 Class year of passing is more than 12 Class."));
					
				}
			}
			if(year12!=0 && yearDeg!=0){
				if(yearDeg<=year12){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","12 Class year of passing is more than Degree."));
					
				}
			}
			if(yearDeg!=0 && year10!=0){
					if(yearDeg<=year10){
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","10 Class year of passing is more than Degree."));
						
					}
			}
			
			log.info("exit validate education...");
		}
		

		return errors;
	}
	/**
	 * CALCELS FULL APPLICATION PROCESS
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter cancel action...");
		HttpSession session= request.getSession(false);
		OnlineApplicationForm admForm=(OnlineApplicationForm)form;
		try {
			if (session != null) {
				cleanupSessionData(session);
				cleanupFormFromSession(session);
			}
		} catch (Exception e) {
			log.error("error in cancelling page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit cancel action...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
		else
			return mapping.findForward("initApplicationEditPage");
		
	}
	/**
	 * cleans up session data
	 * @param session
	 */
	private void cleanupSessionData(HttpSession session) {
		log.info("cleaning up session data...");
		if(session.getAttribute(CMSConstants.STUDENT_PERMANENT_ADDRESS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_PERMANENT_ADDRESS);
		if(session.getAttribute(CMSConstants.STUDENT_COMM_ADDRESS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_COMM_ADDRESS);
			
		if(session.getAttribute(CMSConstants.STUDENT_PREFERENCES)!=null)
			session.removeAttribute(CMSConstants.STUDENT_PREFERENCES);
		if(session.getAttribute(CMSConstants.STUDENT_EDUCATION_DETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_EDUCATION_DETAILS);
		if(session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS);
		if(session.getAttribute(CMSConstants.STUDENT_PREREQUISITES)!=null)
			session.removeAttribute(CMSConstants.STUDENT_PREREQUISITES);
		if(session.getAttribute(CMSConstants.STUDENT_LATERALDETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_LATERALDETAILS);
		if(session.getAttribute(CMSConstants.COURSE_PREFERENCES)!=null)
			session.removeAttribute(CMSConstants.COURSE_PREFERENCES);
		if(session!= null && session.getAttribute(OnlineApplicationAction.PHOTOBYTES)!=null)
			session.removeAttribute(OnlineApplicationAction.PHOTOBYTES);
		
		session.removeAttribute("baseActionForm");
		log.info("session data cleaned...");
	}

	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateOnlineConfirmRequireds(OnlineApplicationForm admForm,
			ActionMessages errors) {
		log.info("entered validateOnlineRequireds..");
		if(errors==null){
			errors= new ActionMessages();
		}
		if((admForm.getApplicantDetails().getPersonalData().getBirthPlace()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthPlace())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED, error);
			}
		}
		
		if((admForm.getApplicantDetails().getPersonalData().getBloodGroup()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBloodGroup())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED, error);
			}
		}
		
		if((admForm.getApplicantDetails().getPersonalData().getEmail()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getEmail())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED, error);
			}
		}
		
		/*if(admForm.getApplicantDetails().getPersonalData().getAreaType()==' ')
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED, error);
			}
		}*/
		
		if(admForm.getApplicantDetails().getPersonalData().getBirthCountry()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthCountry()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED, error);
			}
		}
		if((admForm.getApplicantDetails().getPersonalData().getBirthState()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthState())|| admForm.getApplicantDetails().getPersonalData().getBirthState().equalsIgnoreCase(OnlineApplicationAction.OTHER))&& (admForm.getApplicantDetails().getPersonalData().getStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getStateOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED, error);
			}
		}
		
		/*if((admForm.getApplicantDetails().getPersonalData().getPhNo1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1())) && (admForm.getApplicantDetails().getPersonalData().getPhNo2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2())) && (admForm.getApplicantDetails().getPersonalData().getPhNo3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED, error);
			}
		}*/
		
		
		if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()))
		{
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","House Name/House Number is required."));
			
		}
		if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine2()))
		{
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Post Offiece Name is required."));
			
		}
		if(admForm.getApplicantDetails().getPersonalData().getPermanentCityName()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentCityName()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED).hasNext()) {
			ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED,error);
			}
		}
		
		if(admForm.getApplicantDetails().getPersonalData().getPermanentCountryId()==0 )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED).hasNext()) {
			ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED,error);
			}
		}
		if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED).hasNext()) {
			ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED,error);
			}
		}
		if((admForm.getApplicantDetails().getPersonalData().getPermanentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentStateId()))&& (admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers())) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED, error);
			}
		}
	
		log.info("exit validateOnlineRequireds..");
	}
	/**
	 * entrance validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEntanceDetailsEdit(OnlineApplicationForm admForm,
			ActionMessages errors) {
		if(admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getEntranceDetail()!=null && 
				admForm.getApplicantDetails().getEntranceDetail().getTotalMarks()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getTotalMarks().trim()) && !CommonUtil.isValidDecimal(admForm.getApplicantDetails().getEntranceDetail().getTotalMarks().trim()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER, error);
				}
		}
		
		if(admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getEntranceDetail()!=null &&
				admForm.getApplicantDetails().getEntranceDetail().getMarksObtained()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getMarksObtained().trim()) && !CommonUtil.isValidDecimal(admForm.getApplicantDetails().getEntranceDetail().getMarksObtained().trim()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER, error);
				}
		}
		
		if(admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getEntranceDetail()!=null &&
				admForm.getApplicantDetails().getEntranceDetail().getMarksObtained()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getMarksObtained().trim()) && CommonUtil.isValidDecimal(admForm.getApplicantDetails().getEntranceDetail().getMarksObtained().trim()) 
				&& admForm.getApplicantDetails().getEntranceDetail().getTotalMarks()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getTotalMarks().trim()) && CommonUtil.isValidDecimal(admForm.getApplicantDetails().getEntranceDetail().getTotalMarks().trim())
				&& Double.parseDouble(admForm.getApplicantDetails().getEntranceDetail().getMarksObtained())> Double.parseDouble(admForm.getApplicantDetails().getEntranceDetail().getTotalMarks()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE, error);
				}
		}
		
		//check date of birth cross and present date cross
		if((admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getEntranceDetail()!=null && admForm.getApplicantDetails().getEntranceDetail().getYearPassing()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getYearPassing()) && StringUtils.isNumeric(admForm.getApplicantDetails().getEntranceDetail().getYearPassing())) 
				&& admForm.getApplicantDetails().getEntranceDetail().getMonthPassing()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getMonthPassing()) && StringUtils.isNumeric(admForm.getApplicantDetails().getEntranceDetail().getMonthPassing())){
			if(admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob()) 
					&& CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
				boolean futurethanBirth=isPassYearGreaterThanBirth(Integer.parseInt(admForm.getApplicantDetails().getEntranceDetail().getYearPassing()),Integer.parseInt(admForm.getApplicantDetails().getEntranceDetail().getMonthPassing()),admForm.getApplicantDetails().getPersonalData().getDob());
				if(!futurethanBirth){
					if (errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE);
						errors.add(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE,error);
					}
				}
			}
			Calendar cal= Calendar.getInstance();
			Date today= cal.getTime();
			boolean futurethantoday=isPassYearGreaterThanToday(Integer.parseInt(admForm.getApplicantDetails().getEntranceDetail().getYearPassing()),Integer.parseInt(admForm.getApplicantDetails().getEntranceDetail().getMonthPassing()),today);
			if(futurethantoday){
				if (errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE);
					errors.add(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE,error);
				}
			}
		}
	}
	/**
	 * tc detail validation
	 * @param admForm
	 * @param errors
	 */
	private void validateTcDetailsEdit(OnlineApplicationForm admForm,
			ActionMessages errors) {
		if(admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getTcDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getTcDate().trim()))
		{
			if(CommonUtil.isValidDate(admForm.getApplicantDetails().getTcDate().trim()) ){
				if(!validatefutureDate(admForm.getApplicantDetails().getTcDate())){
					if(errors.get(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE);
						errors.add(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE,error);
					}
				}
			
				}else{
					if(errors.get(CMSConstants.ADMISSIONFORM_TCDATE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TCDATE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_TCDATE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_TCDATE_INVALID,error);
					}
				}
		}
		
		if(admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getMarkscardDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getMarkscardDate().trim()))
		{
			if(CommonUtil.isValidDate(admForm.getApplicantDetails().getMarkscardDate().trim()) ){
			if(!validatefutureDate(admForm.getApplicantDetails().getMarkscardDate().trim())){
					if(errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE);
						errors.add(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE,error);
					}
				}
			
				}else{
					if(errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID,error);
					}
				}
		}
	}
	/**
	 * reset check option for validation fail
	 * @param applicantDetail
	 */
	private void resetHardCopySubmit(AdmApplnTO applicantDetail) {
		log.info("enter resetHardCopySubmit...");
		if(applicantDetail!=null && applicantDetail.getEditDocuments()!=null){
			Iterator<ApplnDocTO> docItr=applicantDetail.getEditDocuments().iterator();
			while (docItr.hasNext()) {
				ApplnDocTO docTO = (ApplnDocTO) docItr.next();
				if(docTO.isHardSubmitted()){
					docTO.setHardSubmitted(false);
					docTO.setTemphardSubmitted(true);
				}else{
					docTO.setHardSubmitted(false);
					docTO.setTemphardSubmitted(false);
				}
				if(docTO.isNotApplicable()){
					docTO.setNotApplicable(false);
					docTO.setTempNotApplicable(true);
				}else{
					docTO.setNotApplicable(false);
					docTO.setTempNotApplicable(false);
				}
			}
		}
	}
	
	/**
	 * COPIES CURENT ADDRESS TO PERMANENT ADDRESS
	 * @param admForm
	 */
	private void copyCurrToPermAddress(OnlineApplicationForm admForm) {
		admForm.getApplicantDetails().getPersonalData().setPermanentAddressLine1(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine1());
		admForm.getApplicantDetails().getPersonalData().setPermanentAddressLine2(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine2());
		admForm.getApplicantDetails().getPersonalData().setPermanentCityName(admForm.getApplicantDetails().getPersonalData().getCurrentCityName());
		admForm.getApplicantDetails().getPersonalData().setPermanentCountryId(admForm.getApplicantDetails().getPersonalData().getCurrentCountryId());
		if(admForm.getApplicantDetails().getPersonalData().getCurrentStateId()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentStateId().trim()))
		admForm.getApplicantDetails().getPersonalData().setPermanentStateId(admForm.getApplicantDetails().getPersonalData().getCurrentStateId());
		if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()))
		admForm.getApplicantDetails().getPersonalData().setPermanentAddressStateOthers(admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers());
		admForm.getApplicantDetails().getPersonalData().setPermanentAddressZipCode(admForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode());
		
		//raghu
		if(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId().trim()))
			admForm.getApplicantDetails().getPersonalData().setPermanentDistricId(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId());
			
		if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()))
			admForm.getApplicantDetails().getPersonalData().setPermanentAddressDistrictOthers(admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers());
		
		if(admForm.getApplicantDetails().getPersonalData().getCurrentStreet()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentStreet()))
			admForm.getApplicantDetails().getPersonalData().setPermanentStreet(admForm.getApplicantDetails().getPersonalData().getCurrentStreet());
		
	}


	
	/**
	 * COPIES CURENT ADDRESS TO PERMANENT ADDRESS
	 * @param admForm
	 */
	private void copyCurrToParentAddress(OnlineApplicationForm admForm) {
		admForm.getApplicantDetails().getPersonalData().setParentAddressLine1(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine1());
		admForm.getApplicantDetails().getPersonalData().setParentAddressLine2(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine2());
		admForm.getApplicantDetails().getPersonalData().setParentCityName(admForm.getApplicantDetails().getPersonalData().getCurrentCityName());
		admForm.getApplicantDetails().getPersonalData().setParentCountryId(admForm.getApplicantDetails().getPersonalData().getCurrentCountryId());
		if(admForm.getApplicantDetails().getPersonalData().getCurrentStateId()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentStateId().trim()))
		admForm.getApplicantDetails().getPersonalData().setParentStateId(admForm.getApplicantDetails().getPersonalData().getCurrentStateId());
		if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()))
		admForm.getApplicantDetails().getPersonalData().setParentAddressStateOthers(admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers());
		admForm.getApplicantDetails().getPersonalData().setParentAddressZipCode(admForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode());
		
		//raghu
		if(admForm.getApplicantDetails().getPersonalData().getFatherMobile()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getFatherMobile().trim())){
			admForm.getApplicantDetails().getPersonalData().setParentMob2(admForm.getApplicantDetails().getPersonalData().getFatherMobile());
			
		}else if(admForm.getApplicantDetails().getPersonalData().getMotherMobile()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMotherMobile().trim())){
			admForm.getApplicantDetails().getPersonalData().setParentMob2(admForm.getApplicantDetails().getPersonalData().getMotherMobile());
			
		}
			
			
	}


	
	/**
	 * permanent address validation
	 * @param admForm
	 * @param errors
	 */
	private void validatePermAddress(OnlineApplicationForm admForm,
			ActionMessages errors) {
		log.info("enter validatePermAddress..");
		if(errors==null)
			errors= new ActionMessages();
		
			
			if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()))
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","House Name/House Number is required."));
				
			}
			if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine2()))
			{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Post Offiece Name is required."));
				
			}
			if(admForm.getApplicantDetails().getPersonalData().getPermanentCityName()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentCityName()) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED,error);
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getPermanentCountryId()==0 )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED,error);
				}
			}
			if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode()) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED,error);
				}
			}else if(!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode())){
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID,error);
				}
			}
			if((admForm.getApplicantDetails().getPersonalData().getPermanentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentStateId()))&& (admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED, error);
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getPermanentStreet()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentStreet()) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM__Street_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM__Street_REQUIRED).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM__Street_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM__Street_REQUIRED,error);
				}
			}
			
			
			log.info("exit validatePermAddress..");
	}
	/**
	 * 
	 * @param admForm
	 * @param request
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void setConfirmationPageDetails(OnlineApplicationForm admForm, HttpServletRequest request) throws FileNotFoundException, IOException{
		HttpSession session=request.getSession(false);
		if(session!= null && session.getAttribute(OnlineApplicationAction.PHOTOBYTES)!=null)
			session.removeAttribute(OnlineApplicationAction.PHOTOBYTES);
		
		if(session!= null && session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS);
	
		
		if(admForm.getApplicantDetails().getEditDocuments()!= null){
			Iterator<ApplnDocTO> itr =admForm.getApplicantDetails().getEditDocuments().iterator();
			ApplnDocTO applnDocTO;
			
			while(itr.hasNext()){
				applnDocTO= itr.next();
				if(applnDocTO.getEditDocument()!= null && applnDocTO.getEditDocument().getFileName()!= null && !StringUtils.isEmpty(applnDocTO.getEditDocument().getFileName())){
					applnDocTO.setCurrDocument(applnDocTO.getEditDocument().getFileData());
					if(!applnDocTO.isPhoto()){
						applnDocTO.setDocumentPresent(true);
					}
					applnDocTO.setName(applnDocTO.getEditDocument().getFileName());
					applnDocTO.setContentType(applnDocTO.getEditDocument().getContentType());					
					
				}
				if(applnDocTO.isPhoto()){
					if(applnDocTO.getEditDocument()!= null){
						if(applnDocTO.getDocName()!=null && applnDocTO.getDocName().equalsIgnoreCase("Photo")){
							if(applnDocTO.getEditDocument().getFileName()!=null && !StringUtils.isEmpty(applnDocTO.getEditDocument().getFileName())){
								if(session!=null){
									admForm.setRemove(false);
									session.setAttribute(OnlineApplicationAction.PHOTOBYTES, applnDocTO.getEditDocument().getFileData());
								}
							}
							else{
								if(session!=null){
									admForm.setRemove(false);
									session.setAttribute(OnlineApplicationAction.PHOTOBYTES, applnDocTO.getCurrDocument());
								}
							}
						}
						if(applnDocTO.getDocName()!=null && applnDocTO.getDocName().equalsIgnoreCase("MgQuotaForm")){
							if(applnDocTO.getEditDocument().getFileName()!=null && !StringUtils.isEmpty(applnDocTO.getEditDocument().getFileName())){
								if(session!=null){
									admForm.setRemove(false);
									session.setAttribute(OnlineApplicationAction.MNGFORMBYTES, applnDocTO.getEditDocument().getFileData());
								}
							}
							else{
								if(session!=null){
									admForm.setRemove(false);
									session.setAttribute(OnlineApplicationAction.MNGFORMBYTES, applnDocTO.getCurrDocument());
								}
							}
						}
					}
				}
				
				
			}
		}
	}		
	
	/**
	 * 
	 * @param admForm
	 * @param request
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void setDocumentForView(OnlineApplicationForm admForm, HttpServletRequest request) throws FileNotFoundException, IOException{
		HttpSession session=request.getSession(false);
		if(admForm.getApplicantDetails().getEditDocuments()!= null){
			Iterator<ApplnDocTO> itr =admForm.getApplicantDetails().getEditDocuments().iterator();
			ApplnDocTO applnDocTO;
			ApplnDoc applnDoc;
			List<ApplnDoc> upLoadList = new ArrayList<ApplnDoc>();
			DocType docType;
			while(itr.hasNext()){
				applnDocTO= itr.next();
				applnDoc = new ApplnDoc();
				docType = new DocType();
				if(applnDocTO.getEditDocument()!= null && applnDocTO.getEditDocument().getFileName()!= null && !StringUtils.isEmpty(applnDocTO.getEditDocument().getFileName())){
					applnDoc.setDocument(applnDocTO.getEditDocument().getFileData());
					applnDoc.setName(applnDocTO.getName());
					applnDoc.setContentType(applnDocTO.getEditDocument().getContentType());
					docType.setId(applnDocTO.getDocTypeId());
					applnDoc.setDocType(docType);
					upLoadList.add(applnDoc);
				}
				else
				{
					applnDoc.setDocument(applnDocTO.getCurrDocument());
					applnDoc.setName(applnDocTO.getName());
					applnDoc.setContentType(applnDocTO.getContentType());
					docType.setId(applnDocTO.getDocTypeId());
					applnDoc.setDocType(docType);
					upLoadList.add(applnDoc);
					
				}

				
				
			}
			session.setAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS, upLoadList);
		}
	}		
	
	/**
	 * 
	 * @param fileData
	 * @param contentType
	 * @param fileName
	 * @param errors
	 * @returns dimension validation error
	 * Checks for an image height and width
	 * Only allows to upload image of dimension 238*100
	 * @throws Exception
	 */
	public boolean validateImageHeightWidth(byte[] fileData,String fileName,String contentType, ActionMessages errors,HttpServletRequest request)throws Exception{
		boolean remove=false;
		if(fileData!=null && fileName != null && !StringUtils.isEmpty(fileName) && contentType!=null && !StringUtils.isEmpty(contentType) ){
		
			File file = null;
			String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			File file1 = new File(filePath+fileName);
			InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
			OutputStream out = new FileOutputStream(file1);
			byte buffer[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, len);
			}
			out.close();
			inputStream.close();
			file = new File(fileName);
			String path = file.getAbsolutePath();
			Image image = Toolkit.getDefaultToolkit().getImage(path);
			ImageIcon icon = new ImageIcon(image);
		    int height = icon.getIconHeight();
		    int width = icon.getIconWidth();
		    //if(width > 97 || height > 97){
		    if(width > 600 || height > 600){
		    	  remove=true;
		    	  errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_DIMENSION));
		      }
		     
		    if(file.exists()){
		    	file.delete();
		    }
		    
		    
		    
		  //raghu write new
			path = file1.getAbsolutePath();
			image = Toolkit.getDefaultToolkit().getImage(path);
			icon = new ImageIcon(image);
		    height = icon.getIconHeight();
		    width = icon.getIconWidth();
		    //if(width > 97 || height > 97){
		    if(width > 600 || height > 600){
		   	  remove=true;
		    	  errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_DIMENSION));
		      }
		      
		   if(file1.exists()){
		    	file1.delete();
		   }
		   
		   
		   
		}
		return remove;
		}
	
	public ActionForward sessionExpired(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		log.info("entering into sessionExpired method");
		
		
		log.info("exiting forwardDoclist method");
		//return mapping.findForward(CMSConstants.ONLINE_DETAIL_APPLICANT_SINGLE_PAGE);
		return mapping.findForward("logoutFromOnlineApplication");
	}
	/**
	 * 
	 * @param admForm
	 * @param request
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void removePhotoDoc(OnlineApplicationForm admForm, HttpServletRequest request) throws FileNotFoundException, IOException{
		HttpSession session=request.getSession(false);
		if(session!= null && session.getAttribute(OnlineApplicationAction.PHOTOBYTES)!=null)
			session.removeAttribute(OnlineApplicationAction.PHOTOBYTES);
		
		if(session!= null && session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS);
		
		List<ApplnDocTO> applnDocTOs=admForm.getApplicantDetails().getEditDocuments();
		if(applnDocTOs!= null){
			Iterator<ApplnDocTO> itr =applnDocTOs.iterator();
			ApplnDocTO applnDocTO;
			
			while(itr.hasNext()){
				applnDocTO= itr.next();
				if(applnDocTO.isPhoto()){
					//raghu
					applnDocTO.setEditDocument(null);
					applnDocTO.setCurrDocument(null);
				}
			}
			admForm.getApplicantDetails().setEditDocuments(applnDocTOs);
		}
	}		
	/**
	 * Validate document size
	 * @param admForm
	 * @param errors
	 */
	private void validateEditDocumentSizeOnline(OnlineApplicationForm admForm,
			ActionMessages errors,HttpServletRequest request) throws Exception {
		log.info("enter validate dcument size...");
		List<ApplnDocTO> uploadlist=admForm.getApplicantDetails().getEditDocuments();
		InputStream propStream=OnlineApplicationAction.class.getResourceAsStream("/resources/application.properties");
		int maXSize=0;
		int maxPhotoSize=0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maXSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_FILESIZE_KEY));
			 maxPhotoSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		 }catch (IOException e) {
			 log.error("Error in Reading key from properties file....",e);
		}
		if(uploadlist!=null){
			Iterator<ApplnDocTO> uploaditr=uploadlist.iterator();
			while (uploaditr.hasNext()) {
				ApplnDocTO docTo = (ApplnDocTO) uploaditr.next();
				FormFile file=null;
				if(docTo!=null)
					file=docTo.getEditDocument();
				if (file!=null) {
					if (file.getFileSize()!=0) {
				if(file!=null)
				{
					String filename=file.getFileName();
					if(!StringUtils.isEmpty(filename)&& filename.length()>30)
					{
						//raghu write newly
						docTo.setEditDocument(null);
						
						if(errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME).hasNext()){
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME);
						errors.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME, error);
						}
					}
				}
				if(docTo.isPhoto() && file!=null ){
					
					//proper image validate
					try{
						if(!StringUtils.isEmpty(file.getFileName()))
						{
					    Image img = ImageIO.read(file.getInputStream());
				       if(img==null){
							if(errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR).hasNext()){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR));
							}
					     }  
						}
					}
					catch (Exception e) {
						if(errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR).hasNext()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR));
						}
					}
					
					/*boolean remove=validateImageHeightWidth(file.getFileData(), file.getFileName(), file.getContentType(), errors,request);
					if(remove){
						//raghu write newly
						docTo.setEditDocument(null);
					}
					admForm.setRemove(remove);*/
					if(maxPhotoSize< file.getFileSize() || (file.getFileSize()!=0 && 30720>file.getFileSize())){
						//raghu write newly
						docTo.setEditDocument(null);
						
						if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
							errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE,error);
						}
						docTo.setEditDocument(null);
						docTo.setName(docTo.getPrintName());
					}
					
					
					//raghu photo upload
					if(docTo.getCurrDocument()==null ){
						
						if( docTo.getEditDocument()==null){
							
						if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD);
							errors.add(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD,error);
						}
						//docTo.setEditDocument(null);
						//docTo.setName(docTo.getPrintName());
						}else if(docTo.getEditDocument().getFileSize()==0){
							if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD);
								errors.add(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD,error);
							}
						}
					}
					
					if(file.getFileName()!=null && !StringUtils.isEmpty(file.getFileName().trim())){
						String extn="";
						int index = file.getFileName().lastIndexOf(".");
						if(index != -1){
							extn = file.getFileName().substring(index, file.getFileName().length());
						}
						if(!extn.isEmpty() && !extn.equalsIgnoreCase(".jpg")){
							//raghu write newly
							docTo.setEditDocument(null);
							
							if(errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR).hasNext()){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR));
							}
						}
					}
					
				}
				else if(file!=null && maXSize< file.getFileSize())
				{
					//raghu write newly
					docTo.setEditDocument(null);
					
					if (errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE);
						errors.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE,error);
					}
				}
			}
		}
			}
			}
		/*if(admForm.getApplicantDetails().getPersonalData().getIsmgquota())
		{
			String recomendedBy = admForm.getApplicantDetails().getPersonalData().getRecommentedBy();
			String RPDesignation = admForm.getApplicantDetails().getPersonalData().getRecommentedPersonDesignation();
			if(recomendedBy == null || recomendedBy == ""||recomendedBy.isEmpty())
			{
				ActionMessage error = new ActionMessage(CMSConstants.ADMIN_RECOMMENDEDBY_REQUIRED );
				errors.add(CMSConstants.ADMIN_RECOMMENDEDBY_REQUIRED ,error);
			}
			if( RPDesignation == null || RPDesignation.isEmpty() || RPDesignation == "")
			{
				ActionMessage error = new ActionMessage(CMSConstants.ADMIN_RECOMMENDED_DESIGNATION_REQUIRED);
				errors.add(CMSConstants.ADMIN_RECOMMENDEDBY_REQUIRED ,error);
			}
			String recomendedBy = admForm.getApplicantDetails().getPersonalData().getRecommendDeatails();
			if(recomendedBy == null || recomendedBy == ""||recomendedBy.isEmpty())
			{
				ActionMessage error = new ActionMessage(CMSConstants.ADMIN_RECOMMENDEDBY_REQUIRED );
				errors.add(CMSConstants.ADMIN_RECOMMENDEDBY_REQUIRED ,error);
			}
		}*/
		
	}
	
	public ActionForward forwardCancelPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		//admForm.setDisplayPage("details");
		admForm.setDisplayPage("educationaldetail");
		//if(admForm.isOnlineApply()){
			return mapping.findForward("onlineAppBasicPage");
		//}else{
			//return mapping.findForward("OfflineAppBasicPage");	
		//}
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward logoutFromOnlineApplication(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OnlineApplicationForm admForm = (OnlineApplicationForm) form;
		HttpSession session=request.getSession(false);
		cleanupSessionData(session);
		//clean up data of form
		cleanUpPageData(admForm);
		admForm=null;
		//call garbage collector forcly to clean data
		System.gc();
		
		OnlineApplicationForm newForm =new OnlineApplicationForm();
		newForm.setMethod("logoutFromOnlineApplication");
		newForm.setUserId("9999");
		session.invalidate();
		return mapping.findForward("logoutFromOnlineApplication");
	}
	
	private void validatePANCardNumber(String panCardNumber, ActionMessages errors, String person) {
		
		if(panCardNumber.length() != 10) {
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.invalidPANLength"));
		}
		else {
			
			String firstHalf = panCardNumber.substring(0,5);
			String secondHalf = panCardNumber.substring(5,9);
			String lastLetter = panCardNumber.substring(9,10);
			
			if(!firstHalf.matches("[a-zA-Z]+") || !secondHalf.matches("[0-9]+") || !lastLetter.matches("[a-zA-Z]")) {
				if(person.equalsIgnoreCase("father")) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.invalidPANNumber", " for father"));
				}
				else if(person.equalsIgnoreCase("mother")) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.invalidPANNumber", " for mother"));
				}
				else {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.invalidPANNumber", " for student"));
				}
			}
		}		
	}
	
	public ActionForward verifyCandidatePendingTransaction(ActionMapping mapping,
														   ActionForm form, HttpServletRequest request,
														   HttpServletResponse response) throws Exception {
		log.info("enter verifyCandidatePendingTransaction...");
		OnlineApplicationForm admForm=(OnlineApplicationForm)form;
		ActionMessages errors = new ActionMessages();
		
		try{

			String verificationURL = CMSConstants.VERIFICATION_URL;
			verificationURL = verificationURL.replace("name", CMSConstants.PGI_MERCHANT_ID)
											 .replace("pwd", URLEncoder.encode(CMSConstants.PGI_SECURITY_ID, "UTF-8"));
			verificationURL += admForm.getVerificationRefNo();
			String responseXML = sendRequest(verificationURL);
			/**
			 * Inspect the responseXML to see the xml tags specified below
			 */
			if(responseXML != null && !responseXML.isEmpty()) {
				
				int startIndex = responseXML.indexOf("<ns1:V_STATUS>");
				int endIndex  = responseXML.indexOf("</ns1:V_STATUS>");
				String responseString = responseXML.substring(startIndex+14, endIndex);
				
				if(responseString != null && !responseString.isEmpty()) {
					
					String[] res = responseString.split("\\|");
					
					if(res != null && res.length != 0) {
					
						/**
						 * Asper the offilneAPI doc given by Federal Bank Customer support, transacction status is in 4th index of the array
						 */
						String transactionId = res[0];
						String transactionAmount = String.valueOf((Integer.parseInt(res[1])/100));
						String paymentMode = res[2];
						String transactionDate = res[3];
						String newTransactionStatus = res[4];
						if("S".equals(newTransactionStatus)) {
							
							try {
								IOnlineApplicationTxn txn = OnlineApplicationImpl.getInstance();
								boolean isUpdated = txn.updatePendingOnlineApp(admForm, 
																			   transactionId, 
																			   transactionAmount,
																			   paymentMode,
																			   transactionDate,
																			   newTransactionStatus);
								if(isUpdated)
									setRequiredDataTOForm(admForm, request.getSession(), request, errors, "CurrentID");
								else {
									errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
									saveErrors(request, errors);
								}
							}
							catch(Exception ex) {
								
								/**
								 * Internal coding error
								 */
								ex.printStackTrace();
								errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
								saveErrors(request, errors);
								return mapping.findForward("onlineAppBasicPage");
							}
						}
						else {
							
							/**
							 * transaction not success
							 */
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.tranStatusStillPending"));
							saveErrors(request, errors);
							return mapping.findForward("onlineAppBasicPage");
						}
					}
					else {
						
						/**
						 * bank response issue - response present, but data empty
						 */
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.error.offlineAPIFedBank"));
						saveErrors(request, errors);
						return mapping.findForward("onlineAppBasicPage");
					}
				}
				else {
					/**
					 * bank response issue - response empty
					 */
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.error.offlineAPIFedBank"));
					saveErrors(request, errors);
					return mapping.findForward("onlineAppBasicPage");
				}
			}
			else {
				
				/**
				 * bank response issue - response empty
				 */
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.error.offlineAPIFedBank"));
				saveErrors(request, errors);
				return mapping.findForward("onlineAppBasicPage");
			}			
		}
		catch (Exception e) {
			
			e.printStackTrace();
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
			saveErrors(request, errors);
		}
		return mapping.findForward("onlineAppBasicPage");
	}
	
	public String sendRequest(String verificationURL){
		String response =null;
		try {
			System.out.println("#############   Inside senRequest   #############");
			System.out.println("VerificationURL : " + verificationURL);
			URL url = new URL(verificationURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			System.out.println("#############   "+ connection +"   #############");
			System.out.println("#############   Got connection   #############");
			byte buffer[] = new byte[8192];
			int read = 0;
			// do request
			long time = System.currentTimeMillis();
			connection.connect();
			InputStream responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			while ((read = responseBodyStream.read(buffer)) != -1) {
				responseBody.append(new String(buffer, 0, read));
			}
			connection.disconnect();
			time = System.currentTimeMillis() - time;
			System.out.println("=============================  MESSAGE TAKEN TIME ===============================: "+time);
			response = responseBody.toString();

		} catch (Exception e) {
			
			System.out.println("#############   Inside catch Block   #############");
			e.printStackTrace();
			return null;
		}
		return response;

	}

	private void smsForPaymentSucess(OnlineApplicationForm admForm)throws Exception{
		
		String sendSms= KPPropertiesConfiguration.INSERT_SMS;
		
		if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
			Properties prop = new Properties();
			try {
				InputStream in = UniqueIdRegistrationHandler.class.getClassLoader()
				.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
				prop.load(in);
			} catch (FileNotFoundException e) {	
			log.error("Unable to read properties file...", e);
				
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				
			}
			String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
			String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
			
			StringBuffer temp = new StringBuffer();
			
			temp=temp.append("Dear Candidate " + admForm.getApplicantDetails().getPersonalData().getFirstName() + ",");
			temp=temp.append(" your Recommendation Reference No: ");
			temp=temp.append("For " + (admForm.isAided() ? "Aided" : "Unaided") + " course is " + admForm.getTxnRefNo());
			temp=temp.append(" (This is For candidate submitting recommendation).");
			String temp1= URLEncoder.encode(temp.toString(),"UTF-8");
			
			PasswordMobileMessaging mob=new PasswordMobileMessaging();						
			mob.setDestinationNumber(admForm.getApplicantDetails().getPersonalData().getMobileNo2());
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);									
			mob.setMessageBody(temp1);
			
			PropertyUtil.getInstance().save(mob);
			SMSUtil s=new SMSUtil();
			SMSUtils smsUtils=new SMSUtils();

			ConverationUtil converationUtil=new ConverationUtil();
			List<SMS_Message> listSms=converationUtil.convertBotoTOPassword(s.getListOfSMSPassword());
			List<SMS_Message> mobList=smsUtils.sendSMSAutomatic(listSms);
			s.updateSMSPassword(converationUtil.convertTotoBOPassword(mobList));

			
		}
		
	}
	public ActionForward personalDataEdit(ActionMapping mapping,ActionForm form,
												HttpServletRequest request,HttpServletResponse response)
												throws Exception{
		HttpSession session= request.getSession(false);
		session.setAttribute("isOnline", "1");
		Integer stu =(Integer) request.getSession().getAttribute("stuId");
		boolean isEdited=(Boolean) request.getSession().getAttribute("photoEdited");
		OnlineApplicationForm onlineApplicationForm = (OnlineApplicationForm)form;
		onlineApplicationForm.setStudentId(stu);
		onlineApplicationForm.setIsUpdated(false);
		onlineApplicationForm.setPhotoEdited(isEdited);
		return mapping.findForward(CMSConstants.STUDENT_DETAILS_PHOTO_EDIT);

	}
	public ActionForward saveStudentPhoto(ActionMapping mapping,ActionForm form,
										HttpServletRequest request,HttpServletResponse response)
										throws Exception{
		OnlineApplicationForm onlineApplicationForm = (OnlineApplicationForm)form;
		ILoginTransaction loginTransaction = new LoginTransactionImpl();
		ActionMessages errors=new ActionMessages();
		try{
			validateEditDocumentSizeOnlineForStudentLogin(onlineApplicationForm, errors,request);
			setUserId(request, onlineApplicationForm);
			onlineApplicationForm.setIsUpdated(false);
			if(errors!=null && !errors.isEmpty()){
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.STUDENT_DETAILS_PHOTO_EDIT);
			}
			Student student = LoginHandler.getInstance().getStudentObj(String.valueOf(onlineApplicationForm.getStudentId())); 
			boolean isUpdated=OnlineApplicationHandler.getInstance().uploadPhoto(onlineApplicationForm,student);
			if(isUpdated){
				PersonalData data=student.getAdmAppln().getPersonalData();
				data.setIsPhotoEdited(true);
				
				loginTransaction.savePersonalData(data);
				onlineApplicationForm.setIsUpdated(true);
				return mapping.findForward(CMSConstants.STUDENT_DETAILS_PHOTO_EDIT);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(CMSConstants.STUDENT_DETAILS_PHOTO_EDIT);
	}




	private void validateEditDocumentSizeOnlineForStudentLogin(OnlineApplicationForm onlineApplicationForm, ActionMessages errors,
																HttpServletRequest request)throws Exception {
		
		InputStream propStream=OnlineApplicationAction.class.getResourceAsStream("/resources/application.properties");
		int maXSize=0;
		int maxPhotoSize=0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maXSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_FILESIZE_KEY));
			 maxPhotoSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		 }catch (IOException e) {
		 	}
				FormFile file=onlineApplicationForm.getStudentPhoto();
				if(file!=null)
				{
					String filename=file.getFileName();
					if(!StringUtils.isEmpty(filename)&& filename.length()>30)
					{
						
						if(errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME).hasNext()){
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME);
						errors.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME, error);
						}
					}
				}
				if(file!=null ){
					
					//proper image validate
					try{
						if(!StringUtils.isEmpty(file.getFileName()))
						{
					    Image img = ImageIO.read(file.getInputStream());
				       if(img==null){
							if(errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR).hasNext()){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR));
							}
					     }  
						}
					}
					catch (Exception e) {
						if(errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR).hasNext()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR));
						}
					}
					
					boolean remove=validateImageHeightWidthForStudentLogin(file.getFileData(), file.getFileName(), file.getContentType(), errors,request);
					if(remove){
						onlineApplicationForm.setRemove(remove);
					}
					if(maxPhotoSize< file.getFileSize() || (file.getFileSize()==0 )){
						
						if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
							errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE,error);
						}
					}
					
					
					
					
					if(file.getFileName()!=null && !StringUtils.isEmpty(file.getFileName().trim())){
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
					
				}
				else if(file!=null && maXSize< file.getFileSize())
				{
					
					if (errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE);
						errors.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE,error);
					}
				}else if( file==null){
					ActionMessage error = new ActionMessage(CMSConstants.PERSONAL_DATA_PHOTO);
					errors.add(CMSConstants.PERSONAL_DATA_PHOTO,error);
				}
		
	}




	private boolean validateImageHeightWidthForStudentLogin(byte[] fileData,String fileName, String contentType, ActionMessages errors,
															HttpServletRequest request)throws Exception {
		
		boolean remove=false;
		if(fileData!=null && fileName != null && !StringUtils.isEmpty(fileName) && contentType!=null && !StringUtils.isEmpty(contentType) ){
		
			File file = null;
			String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			File file1 = new File(filePath+fileName);

			file = new File(fileName);
			String path = file.getAbsolutePath();
			Image image = Toolkit.getDefaultToolkit().getImage(path);
			ImageIcon icon = new ImageIcon(image);
		    int height = icon.getIconHeight();
		    int width = icon.getIconWidth();
		    if(width > 600 || height > 600){
		    	  remove=true;
		    	  errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_DIMENSION));
		      }
		     
		    if(file.exists()){
		    	file.delete();
		    }
			path = file1.getAbsolutePath();
			image = Toolkit.getDefaultToolkit().getImage(path);
			icon = new ImageIcon(image);
		    height = icon.getIconHeight();
		    width = icon.getIconWidth();
		    if(width > 600 || height > 600){
		   	  remove=true;
		    	  errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_DIMENSION));
		      }
		      
		   if(file1.exists()){
		    	file1.delete();
		   }
		   
		   
		   
		}
		return remove;
	}
	
	public ActionForward byPassMethodForPayment(ActionMapping mapping,
												ActionForm form,
												HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		OnlineApplicationForm onlineApplicationForm = (OnlineApplicationForm)form;
		//onlineApplicationForm.setByPass(true);
		
		OnlineApplicationHandler.getInstance().updateResponseByPass(onlineApplicationForm);
		
		return mapping.findForward("onlineAppBasicPage"); 
	}
	
}