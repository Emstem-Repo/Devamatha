package com.kp.cms.actions.admission;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmSubjectMarkForRank;
import com.kp.cms.bo.admin.ApplicantLateralDetails;
import com.kp.cms.bo.admin.ApplicantTransferDetails;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.StudentIndexMark;
import com.kp.cms.bo.admin.StudentRank;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.forms.admission.ApplicationEditForm;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.forms.admission.StudentSpecialPromotionForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.EntranceDetailsHandler;
import com.kp.cms.handlers.admin.LanguageHandler;
import com.kp.cms.handlers.admin.OccupationTransactionHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.admin.StateHandler;
import com.kp.cms.handlers.admin.SubReligionHandler;
import com.kp.cms.handlers.admin.SubjectGroupHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.ApplicationEditHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.helpers.admission.ApplicationEditHelper;
import com.kp.cms.to.admin.AdmSubjectMarkForRankTO;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplicantWorkExperienceTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidatePreferenceEntranceDetailsTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.DistrictTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.EntrancedetailsTO;
import com.kp.cms.to.admin.ExamCenterTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ParishTo;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admin.StudentCourseAllotmentTo;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.UGCoursesTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.AdmSubjectForRankTo;
import com.kp.cms.to.admission.PreferenceTO;
import com.kp.cms.transactions.admin.ISubReligionTransaction;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactions.admission.IApplicationEditTransaction;
import com.kp.cms.transactionsimpl.admin.DioceseTransactionImpl;
import com.kp.cms.transactionsimpl.admin.ParishTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SubReligionTransactionImpl;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.transactionsimpl.admission.ApplicationEditTransactionimpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ApplicationEditAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ApplicationEditAction.class);
	private static final String OTHER="other";
	private static final String TO_DATEFORMAT="MM/dd/yyyy";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final String PHOTOBYTES="PhotoBytes";
	//raghu added newly
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
	

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApplicationEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initApplicationEdit..");
		ApplicationEditForm applicationForm = (ApplicationEditForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","application_edit");
		
		setUserId(request, applicationForm);
		cleanupEditData(applicationForm);
		applicationForm.setApplicationYear(null);
		applicationForm.setOnlineApply(false);
		if(request.getParameter("PUCEdit")!=null){
			applicationForm.setPucApplicationDetailEdit(true);
			applicationForm.setPucApplicationDetailView(false);
		}
		else if(request.getParameter("PUCView")!=null){
			applicationForm.setPucApplicationDetailView(true);
			applicationForm.setPucApplicationDetailEdit(false);
		}
		else{
			applicationForm.setPucApplicationDetailEdit(false);
			applicationForm.setPucApplicationDetailView(false);
		}
		applicationForm.setPucApplicationEdit(false);
		log.info("exit initApplicationEdit..");
		return mapping.findForward(CMSConstants.APPLICATIONEDIT_INIT_PAGE);
	}
	
	/**
	 * reset blank display after admission submit
	 * @param admForm
	 */
	private void cleanupEditData(ApplicationEditForm applicationForm) {
		log.info("enter cleanupEditData...");
		applicationForm.setProgramId("");
		applicationForm.setProgramTypeId("");
		applicationForm.setCourseId("");
		applicationForm.setQuotaCheck(false);
		applicationForm.setEligibleCheck(false);
		applicationForm.setReviewWarned(false);
		applicationForm.setSubmitDate(null);
		applicationForm.setLateralDetails(null);
		applicationForm.setLateralInstituteAddress(null);
		applicationForm.setLateralStateName(null);
		applicationForm.setLateralUniversityName(null);
		applicationForm.setApplicationConfirm(false);
		applicationForm.setExamCenterRequired(false);
		applicationForm.setPucApplicationEdit(false);
		applicationForm.setPucInterviewDate(null);
		applicationForm.setRecomendedBy(null);
		applicationForm.setInterviewSelectionDate(null);
		applicationForm.setInterviewTime(null);
		applicationForm.setInterviewVenue(null);
		applicationForm.setSelectedDate(null);
		applicationForm.setSelectedVenue(null);
		applicationForm.setIsInterviewSelectionSchedule("false");
		
		//ra
		applicationForm.setSubid_0("");
		applicationForm.setSubid_1("");
		applicationForm.setSubid_2("");
		applicationForm.setSubid_3("");
		applicationForm.setSubid_4("");
		applicationForm.setSubid_5("");
		applicationForm.setSubid_6("");
		applicationForm.setSubid_7("");
		applicationForm.setSubid_8("");
		
		applicationForm.setObtainedmark_0("");
		applicationForm.setObtainedmark_1("");
		applicationForm.setObtainedmark_2("");
		applicationForm.setObtainedmark_3("");
		applicationForm.setObtainedmark_4("");
		applicationForm.setObtainedmark_5("");
		applicationForm.setObtainedmark_6("");
		applicationForm.setObtainedmark_7("");
		applicationForm.setObtainedmark_8("");
		
		applicationForm.setMaxmark_0("");
		applicationForm.setMaxmark_1("");
		applicationForm.setMaxmark_2("");
		applicationForm.setMaxmark_3("");
		applicationForm.setMaxmark_4("");
		applicationForm.setMaxmark_5("");
		applicationForm.setMaxmark_6("");
		applicationForm.setMaxmark_7("");
		applicationForm.setMaxmark_8("");
		
		
		applicationForm.setAdmsubmarkid_0("");
		applicationForm.setAdmsubmarkid_1("");
		applicationForm.setAdmsubmarkid_2("");
		applicationForm.setAdmsubmarkid_3("");
		applicationForm.setAdmsubmarkid_4("");
		applicationForm.setAdmsubmarkid_5("");
		applicationForm.setAdmsubmarkid_6("");
		applicationForm.setAdmsubmarkid_7("");
		applicationForm.setAdmsubmarkid_8("");
		applicationForm.setAdmsubmarkid_9("");
		applicationForm.setAdmsubmarkid_10("");
		applicationForm.setAdmsubmarkid_11("");
		applicationForm.setAdmsubmarkid_12("");
		applicationForm.setAdmsubmarkid_13("");
		applicationForm.setAdmsubmarkid_14("");
		applicationForm.setAdmsubmarkid_15("");
		applicationForm.setAdmsubmarkid_16("");
		applicationForm.setAdmsubmarkid_17("");
		
		
		applicationForm.setAdmsubgrpname_0("");
		applicationForm.setAdmsubgrpname_1("");
		applicationForm.setAdmsubgrpname_2("");
		applicationForm.setAdmsubgrpname_3("");
		applicationForm.setAdmsubgrpname_4("");
		applicationForm.setAdmsubgrpname_5("");
		applicationForm.setAdmsubgrpname_6("");
		applicationForm.setAdmsubgrpname_7("");
		applicationForm.setAdmsubgrpname_8("");
		applicationForm.setAdmsubgrpname_9("");
		applicationForm.setAdmsubgrpname_10("");
		applicationForm.setAdmsubgrpname_11("");
		applicationForm.setAdmsubgrpname_12("");
		applicationForm.setAdmsubgrpname_13("");
		applicationForm.setAdmsubgrpname_14("");
		applicationForm.setAdmsubgrpname_15("");
		applicationForm.setAdmsubgrpname_16("");
		applicationForm.setAdmsubgrpname_17("");
		
		applicationForm.setAdmsubname_0("");
		applicationForm.setAdmsubname_1("");
		applicationForm.setAdmsubname_2("");
		applicationForm.setAdmsubname_3("");
		applicationForm.setAdmsubname_4("");
		applicationForm.setAdmsubname_5("");
		applicationForm.setAdmsubname_6("");
		applicationForm.setAdmsubname_7("");
		applicationForm.setAdmsubname_8("");
		applicationForm.setAdmsubname_9("");
		applicationForm.setAdmsubname_10("");
		applicationForm.setAdmsubname_11("");
		applicationForm.setAdmsubname_12("");
		applicationForm.setAdmsubname_13("");
		applicationForm.setAdmsubname_14("");
		applicationForm.setAdmsubname_15("");
		applicationForm.setAdmsubname_16("");
		applicationForm.setAdmsubname_17("");
		
		applicationForm.setAllot_0(false);
		applicationForm.setAllot_1(false);
		applicationForm.setAllot_2(false);
		applicationForm.setAllot_3(false);
		applicationForm.setAllot_4(false);
		applicationForm.setAllot_5(false);
		applicationForm.setAllot_6(false);
		applicationForm.setAllot_7(false);
		applicationForm.setAllot_8(false);
		applicationForm.setAllot_9(false);
		applicationForm.setAllot_10(false);
		applicationForm.setAllot_11(false);
		applicationForm.setAllot_12(false);
		applicationForm.setAllot_13(false);
		applicationForm.setAllot_14(false);
		applicationForm.setAllot_15(false);
		applicationForm.setAllot_16(false);
		applicationForm.setAllot_17(false);
		
		//for ug
		
		applicationForm.setDegsubid_0(null);
		applicationForm.setDegsubid_1(null);
		applicationForm.setDegsubid_2(null);
		applicationForm.setDegsubid_3(null);
		applicationForm.setDegsubid_4(null);
		applicationForm.setDegsubid_5(null);
		applicationForm.setDegsubid_6(null);
		applicationForm.setDegsubid_7(null);
		applicationForm.setDegsubid_8(null);
		applicationForm.setDegsubid_9(null);
		applicationForm.setDegsubid_10(null);
		applicationForm.setDegsubid_11(null);
		applicationForm.setDegsubid_12(null);
		applicationForm.setDegsubid_13(null);
		applicationForm.setDegsubid_14(null);
		//applicationForm.setDegsubid_15(null);
		//applicationForm.setDegsubid_16(null);
		
		applicationForm.setDegobtainedmark_0(null);
		applicationForm.setDegobtainedmark_1(null);
		applicationForm.setDegobtainedmark_2(null);
		applicationForm.setDegobtainedmark_3(null);
		applicationForm.setDegobtainedmark_4(null);
		applicationForm.setDegobtainedmark_5(null);
		applicationForm.setDegobtainedmark_6(null);
		applicationForm.setDegobtainedmark_7(null);
		applicationForm.setDegobtainedmark_8(null);
		applicationForm.setDegobtainedmark_9(null);
		applicationForm.setDegobtainedmark_10(null);
		applicationForm.setDegobtainedmark_11(null);
		applicationForm.setDegobtainedmark_12(null);
		applicationForm.setDegobtainedmark_13(null);
		applicationForm.setDegobtainedmark_14(null);
		//applicationForm.setDegobtainedmark_15(null);
		//applicationForm.setDegobtainedmark_16(null);
		
		applicationForm.setDegmaxmark_0(null);
		applicationForm.setDegmaxmark_1(null);
		applicationForm.setDegmaxmark_2(null);
		applicationForm.setDegmaxmark_3(null);
		applicationForm.setDegmaxmark_4(null);
		applicationForm.setDegmaxmark_5(null);
		applicationForm.setDegmaxmark_6(null);
		applicationForm.setDegmaxmark_7(null);
		applicationForm.setDegmaxmark_8(null);
		applicationForm.setDegmaxmark_9(null);
		applicationForm.setDegmaxmark_10(null);
		applicationForm.setDegmaxmark_11(null);
		applicationForm.setDegmaxmark_12(null);
		applicationForm.setDegmaxmark_13(null);
		applicationForm.setDegmaxmark_14(null);
		//applicationForm.setDegmaxmark_15(null);
		//applicationForm.setDegmaxmark_16(null);
		
	
		
		applicationForm.setDegmaxcgpa_0(null);
		applicationForm.setDegmaxcgpa_1(null);
		applicationForm.setDegmaxcgpa_2(null);
		applicationForm.setDegmaxcgpa_3(null);
		applicationForm.setDegmaxcgpa_4(null);
		applicationForm.setDegmaxcgpa_5(null);
		applicationForm.setDegmaxcgpa_6(null);
		applicationForm.setDegmaxcgpa_7(null);
		applicationForm.setDegmaxcgpa_14(null);
		//applicationForm.setDegmaxcgpa_15(null);
		
		applicationForm.setPatternofStudy(null);
		applicationForm.setDegtotalmaxmark(null);
		applicationForm.setDegtotalmaxmarkother(null);
		applicationForm.setDegtotalobtainedmark(null);
		applicationForm.setDegtotalobtainedmarkother(null);

		applicationForm.setAdmsubmarkid_0("");
		applicationForm.setAdmsubmarkid_1("");
		applicationForm.setAdmsubmarkid_2("");
		applicationForm.setAdmsubmarkid_3("");
		applicationForm.setAdmsubmarkid_4("");
		applicationForm.setAdmsubmarkid_5("");
		applicationForm.setAdmsubmarkid_6("");
		applicationForm.setAdmsubmarkid_7("");
		applicationForm.setAdmsubmarkid_8("");
		
		applicationForm.setAdmsubgrpname_0("");
		applicationForm.setAdmsubgrpname_1("");
		applicationForm.setAdmsubgrpname_2("");
		applicationForm.setAdmsubgrpname_3("");
		applicationForm.setAdmsubgrpname_4("");
		applicationForm.setAdmsubgrpname_5("");
		applicationForm.setAdmsubgrpname_6("");
		applicationForm.setAdmsubgrpname_7("");
		applicationForm.setAdmsubgrpname_8("");
		
		applicationForm.setAllot_0(false);
		applicationForm.setAllot_1(false);
		applicationForm.setAllot_2(false);
		applicationForm.setAllot_3(false);
		applicationForm.setAllot_4(false);
		applicationForm.setAllot_5(false);
		applicationForm.setAllot_6(false);
		applicationForm.setAllot_7(false);
		applicationForm.setAllot_8(false);


	}
	/**
	 * prepare admission data to display
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getApplicantDetailsForEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered detailApplicationEdit..");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		admForm.setCheckReligionId(CMSConstants.RELIGION_CHRISTIAN_TYPE);
		try{
			 ActionErrors errors = admForm.validate(mapping, request);
			
			 HttpSession session=request.getSession();
				
				//raghu removing attribute from session first mark entry detail for degree 
				
				session.removeAttribute(admForm.getApplicationNumber());
				
				String applNo=admForm.getApplicationNumber()+12;
				session.removeAttribute(applNo);
				
			 //raghu added newly
			 admForm.setClass12check(false);
			 admForm.setClassdegcheck(false);
			 admForm.setAdmsubMarkList(null);
			 admForm.setPrefcourses(null);
			 admForm.setCourseMap(null);
			 
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.APPLICATIONEDIT_INIT_PAGE);
			}
				
				String applicationNumber = admForm.getApplicationNumber().trim();
				int applicationYear = Integer.parseInt(admForm.getApplicationYear());
				AdmApplnTO applicantDetails = ApplicationEditHandler.getInstance().getApplicantDetails(applicationNumber, applicationYear,admForm);
				
				if(applicantDetails != null){
					// set candidate prerequisite marks 
					AdmissionFormHandler.getInstance().setPrerequisiteMarks(applicationNumber,applicantDetails,admForm);
					setDataToInitForm(admForm);
					admForm.setBackLogs(applicantDetails.isBackLogs());
					//raghu added newly
					admForm.setIsSaypass(applicantDetails.getIsSaypass());
					
					//raghu added newly
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
					
				
				}
				if( applicantDetails == null){
					
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
						message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						messages.add("messages", message);
						saveMessages(request, messages);
						
							return mapping.findForward(CMSConstants.APPLICATIONEDIT_INIT_PAGE);
					
				} else {
					List<ExamCenterTO> examCenterList = ApplicationEditHandler.getInstance().getExamCenters(applicantDetails.getSelectedCourse().getProgramId());
					admForm.setExamCenters(examCenterList);

					//get states list for edn qualification
					List<StateTO> ednstates=StateHandler.getInstance().getNativeStates(CMSConstants.KP_DEFAULT_COUNTRY_ID);
					admForm.setEdnStates(ednstates);
					List<EntrancedetailsTO> entrnaceList=EntranceDetailsHandler.getInstance().getEntranceDeatilsByProgram(applicantDetails.getSelectedCourse().getProgramId());
					admForm.setEntranceList(entrnaceList);
					if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getDob()!=null )
						applicantDetails.getPersonalData().setDob(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getDob(), ApplicationEditAction.SQL_DATEFORMAT,ApplicationEditAction.FROM_DATEFORMAT));
					if(applicantDetails.getChallanDate()!=null )
						applicantDetails.setChallanDate(CommonUtil.ConvertStringToDateFormat(applicantDetails.getChallanDate(), ApplicationEditAction.SQL_DATEFORMAT,ApplicationEditAction.FROM_DATEFORMAT));
					
					if(applicantDetails.getCourseChangeDate()!=null )
						applicantDetails.setCourseChangeDate(CommonUtil.ConvertStringToDateFormat(applicantDetails.getCourseChangeDate(), ApplicationEditAction.SQL_DATEFORMAT,ApplicationEditAction.FROM_DATEFORMAT));
					
					if((applicantDetails.getAdmissionDate() == null || !applicantDetails.getAdmissionDate().trim().isEmpty()) && admForm.isAdmissionEdit()){
						applicantDetails.setAdmissionDate(CommonUtil.getTodayDate());
					}
					if((applicantDetails.getAdmissionDate() == null || !applicantDetails.getAdmissionDate().trim().isEmpty()) && admForm.isAdmissionEdit()){
						applicantDetails.setAdmissionDate(CommonUtil.getTodayDate());
					}
					if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getRecommentedBy()!=null )
						admForm.setRecomendedBy(applicantDetails.getPersonalData().getRecommentedBy());
					if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getRecommendDeatails()!=null )
						admForm.setRecomendedBy(applicantDetails.getPersonalData().getRecommendDeatails());
					admForm.setApplicantDetails(applicantDetails);
					String workExpNeeded=admForm.getApplicantDetails().getCourse().getIsWorkExperienceRequired();
					if(admForm.getApplicantDetails().getCourse()!=null && "Yes".equalsIgnoreCase(workExpNeeded))
					{
						admForm.setWorkExpNeeded(true);
					}else{
						admForm.setWorkExpNeeded(false);
					}
						admForm.setApplicantDetails(applicantDetails);
					ProgramTypeHandler programtypehandler = ProgramTypeHandler.getInstance();
						List<ProgramTypeTO> programtypes = programtypehandler
									.getProgramType();
					CourseTO applicantCourse = applicantDetails.getCourse();
					CourseTO selectedCourse=applicantDetails.getSelectedCourse();
						if (programtypes!=null) {
							admForm.setEditProgramtypes(programtypes);
							Iterator<ProgramTypeTO> typeitr= programtypes.iterator();
							while (typeitr.hasNext()) {
								ProgramTypeTO typeTO = (ProgramTypeTO) typeitr.next();
								if(typeTO.getProgramTypeId()==selectedCourse.getProgramTypeId()){
									if(typeTO.getPrograms()!=null){
										admForm.setEditprograms(typeTO.getPrograms());
										Iterator<ProgramTO> programitr= typeTO.getPrograms().iterator();
											while (programitr.hasNext()) {
												ProgramTO programTO = (ProgramTO) programitr
														.next();
												// PROGRAM LEVEL CONFIG SETTINGS
												if(programTO.getId()== selectedCourse.getProgramId()){
								 					admForm.setEditcourses(programTO.getCourseList());
													if(programTO!=null){ 
														if(programTO.getIsMotherTongue()==true)
														admForm.setDisplayMotherTongue(true);
														if(programTO.getIsDisplayLanguageKnown()==true)
															admForm.setDisplayLanguageKnown(true);
														if(programTO.getIsHeightWeight()==true)
															admForm.setDisplayHeightWeight(true);
														if(programTO.getIsDisplayTrainingCourse()==true)
															admForm.setDisplayTrainingDetails(true);
														if(programTO.getIsAdditionalInfo()==true)
															admForm.setDisplayAdditionalInfo(true);
														if(programTO.getIsExtraDetails()==true)
															admForm.setDisplayExtracurricular(true);
														if(programTO.getIsSecondLanguage()==true)
															admForm.setDisplaySecondLanguage(true);
														if(programTO.getIsFamilyBackground()==true)
															admForm.setDisplayFamilyBackground(true);
														if(programTO.getIsTCDetails()==true)
															admForm.setDisplayTCDetails(true);
														if(programTO.getIsEntranceDetails()==true)
															admForm.setDisplayEntranceDetails(true);
														if(programTO.getIsLateralDetails()==true)
															admForm.setDisplayLateralDetails(true);
														if(programTO.getIsTransferCourse()==true)
															admForm.setDisplayTransferCourse(true);
														if(programTO.getIsExamCenterRequired()==true)
															admForm.setExamCenterRequired(true);
													}
												}
											}
									}	
								}
							}
						}
						
						checkExtradetailsDisplay(admForm);
						checkLateralTransferDisplay(admForm);
						
						Properties prop = new Properties();
						try {
							InputStream inputStream = CommonUtil.class.getClassLoader()
									.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
							prop.load(inputStream);
						} 
						catch (IOException e) {
							log.error("Error occured at convertToExcel of studentDetailsReportHelper ",e);
							throw new IOException(e);
						}
						String fileName=prop.getProperty(CMSConstants.PRG_TYPE);
						if(admForm.getApplicantDetails().getSelectedCourse().getProgramTypeId()==(Integer.parseInt(fileName))){
							admForm.setViewextradetails(true);
						}else{
							admForm.setViewextradetails(false);
						}
						
						//parish and dioceses

						
						DioceseTransactionImpl txn=new DioceseTransactionImpl();
						List<DioceseTo> dioceseList=txn.getDiocesesList();
						admForm.setDioceseList(dioceseList);
						
						ParishTransactionImpl parishtxn=new ParishTransactionImpl();
						List<ParishTo> parishList=parishtxn.getParishes();
						admForm.setParishList(parishList);
						
						//raghu
						
						/*List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
						if(ugcourseList!=null && ugcourseList.size()!=0){
							admForm.setUgcourseList(ugcourseList);
						}else{
							admForm.setUgcourseList(new ArrayList<UGCoursesTO>());
						}
						*/
						List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
						admForm.setUgcourseList(ugcourseList);
						
						
						
					if(CountryHandler.getInstance().getCountries()!=null){
						//birthCountry & states
						List<CountryTO> birthCountries= CountryHandler.getInstance().getCountries();
						if (!birthCountries.isEmpty()) {
							admForm.setCountries(birthCountries);
							Iterator<CountryTO> cntitr= birthCountries.iterator();
							while (cntitr.hasNext()) {
								CountryTO countryTO = (CountryTO) cntitr.next();
								if(admForm.getApplicantDetails().getPersonalData().getBirthCountry()!=null && countryTO.getId()== Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getBirthCountry()) && admForm.getApplicantDetails().getPersonalData()!= null){
									List<StateTO> stateList=countryTO.getStateList();
									Collections.sort(stateList);
									admForm.setEditStates(stateList);
								}
							}
						}
						
						
						//permanentCountry & states
						List<CountryTO> permanentCountries = CountryHandler.getInstance().getCountries();
						if (permanentCountries!=null) {
							admForm.setCountries(permanentCountries);
							Iterator<CountryTO> cntitr= permanentCountries.iterator();
							while (cntitr.hasNext()) {
								CountryTO countryTO = (CountryTO) cntitr.next();
								if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getPermanentCountryId()){
									List<StateTO> stateList = countryTO.getStateList();
									Collections.sort(stateList);
									admForm.setEditPermanentStates(stateList);
								}
							}
						}
						
						//currentCountry & states
						List<CountryTO> currentCountries = CountryHandler.getInstance().getCountries();
						if (currentCountries!=null) {
							admForm.setCountries(currentCountries);
							Iterator<CountryTO> cntitr= currentCountries.iterator();
							while (cntitr.hasNext()) {
								CountryTO countryTO = (CountryTO) cntitr.next();
								if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getCurrentCountryId()){
									List<StateTO> stateList = countryTO.getStateList();
									Collections.sort(stateList);
									admForm.setEditCurrentStates(stateList);
								}
							}
						}
						
						//parentCountry & states
						List<CountryTO> parentCountries = CountryHandler.getInstance().getCountries();
						if (parentCountries!=null) {
							admForm.setCountries(parentCountries);
							Iterator<CountryTO> cntitr= parentCountries.iterator();
							while (cntitr.hasNext()) {
								CountryTO countryTO = (CountryTO) cntitr.next();
								if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getParentCountryId()){
									admForm.setEditParentStates(null);
									List<StateTO> stateList = countryTO.getStateList();
									Collections.sort(stateList);
									admForm.setEditParentStates(stateList);
								}
							}
						}
					}
					
					
					
					//guardian states
					
					List<CountryTO> guardianCountries = CountryHandler.getInstance().getCountries();
					if (guardianCountries!=null) {
						admForm.setCountries(guardianCountries);
						Iterator<CountryTO> cntitr= guardianCountries.iterator();
						while (cntitr.hasNext()) {
							CountryTO countryTO = (CountryTO) cntitr.next();
							if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getCountryByGuardianAddressCountryId()){
								admForm.setEditGuardianStates(null);
								List<StateTO> stateList = countryTO.getStateList();
								Collections.sort(stateList);
								admForm.setEditGuardianStates(stateList);
							}
						}
					}
				
					
					//raghu
					//permanent district & states
					List<StateTO> permanentStates = StateHandler.getInstance().getStates();
					if (permanentStates!=null) {
						//admForm.setCountries(permanentCountries);
						Iterator<StateTO> stitr= permanentStates.iterator();
						while (stitr.hasNext()) {
							StateTO stateTO = (StateTO) stitr.next();
							if(admForm.getApplicantDetails().getPersonalData().getPermanentStateId()!=null && !admForm.getApplicantDetails().getPersonalData().getPermanentStateId().equalsIgnoreCase("Other")){
							if(stateTO.getId()== Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getPermanentStateId())){
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
							if(admForm.getApplicantDetails().getPersonalData().getCurrentStateId()!=null && !admForm.getApplicantDetails().getPersonalData().getCurrentStateId().equalsIgnoreCase("Other")){
								
							
							if(stateTO.getId()== Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getCurrentStateId())){
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
					
					
					
					
					if(applicantDetails.getPersonalData()!=null){
						
						OrganizationHandler orgHandler= OrganizationHandler.getInstance();
						List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
						if(tos!=null && !tos.isEmpty())
						{
							OrganizationTO orgTO=tos.get(0);
								if(orgTO.getExtracurriculars()!=null)
									applicantDetails.getPersonalData().setStudentExtracurricularsTos(orgTO.getExtracurriculars());
							}
						
					}
					
					//Nationality
						ApplicationEditHandler formhandler = ApplicationEditHandler.getInstance();
						admForm.setNationalities(formhandler.getNationalities());
					// languages	
						LanguageHandler langHandler=LanguageHandler.getHandler();
						admForm.setMothertongues(langHandler.getLanguages());
						admForm.setLanguages(langHandler.getOriginalLanguages());
						
						if(admForm.isDisplayAdditionalInfo())
						{
							OrganizationHandler orgHandler= OrganizationHandler.getInstance();
							List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
							if(tos!=null && !tos.isEmpty())
							{
								OrganizationTO orgTO=tos.get(0);
								admForm.setOrganizationName(orgTO.getOrganizationName());
								admForm.setNeedApproval(orgTO.isNeedApproval());
							}
						}
						
					// res. catg
						admForm.setResidentTypes(formhandler.getResidentTypes());	
						
						ReligionHandler religionhandler = ReligionHandler.getInstance();
						if(religionhandler.getReligion()!=null){
							List<ReligionTO> religionList=religionhandler.getReligion();
							admForm.setReligions(religionList);
							Iterator<ReligionTO> relItr=religionList.iterator();
							while (relItr.hasNext()) {
								ReligionTO relTO = (ReligionTO) relItr.next();
								if(admForm.getApplicantDetails().getPersonalData().getReligionId() !=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId())  
										&& StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getReligionId() ) && relTO.getReligionId()== Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getReligionId()) ){
									List<ReligionSectionTO> subreligions=relTO.getSubreligions();
									admForm.setSubReligions(subreligions);
								}
							}
						}
					
						
					//raghu
						
					List<ReligionSectionTO> subreligions=SubReligionHandler.getInstance().getSubReligion();
					admForm.setSubReligions(subreligions);
						
					// caste category
					List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
					admForm.setCasteList(castelist);
					
					// Admitted Through
					List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
					admForm.setAdmittedThroughList(admittedList);
					// subject Group
					List<SubjectGroupTO> sujectgroupList=SubjectGroupHandler.getInstance().getSubjectGroupDetailsByCourseAndTermNo(selectedCourse.getId(),applicantDetails.getAppliedYear(),1);
					admForm.setSubGroupList(sujectgroupList);
					String[] subjectgroups=applicantDetails.getSubjectGroupIds();
					if (subjectgroups!=null && subjectgroups.length==0 && sujectgroupList!=null) {
						subjectgroups=new String[sujectgroupList.size()];
						applicantDetails.setSubjectGroupIds(subjectgroups);
					}
					
					//incomes
					List<IncomeTO> incomeList = ApplicationEditHandler.getInstance().getIncomes();
					admForm.setIncomeList(incomeList);
						
					//currencies
					List<CurrencyTO> currencyList = ApplicationEditHandler.getInstance().getCurrencies();
					admForm.setCurrencyList(currencyList);
					
					UniversityHandler unhandler = UniversityHandler.getInstance();
					List<UniversityTO> universities = unhandler.getUniversity();
					admForm.setUniversities(universities);
					
					OccupationTransactionHandler occhandler = OccupationTransactionHandler
					.getInstance();
					admForm.setOccupations(occhandler.getAllOccupation());
					
					if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(applicantDetails.getPersonalData().getPassportValidity()) )
						applicantDetails.getPersonalData().setPassportValidity(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getPassportValidity(), ApplicationEditAction.SQL_DATEFORMAT,ApplicationEditAction.FROM_DATEFORMAT));
					
					// set photo to session
					
					if(CMSConstants.LINK_FOR_CJC){
						if(applicantDetails.getEditDocuments()!=null){
							Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
							while (docItr.hasNext()) {
								ApplnDocTO docTO = (ApplnDocTO) docItr.next();
								if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
								{
									admForm.setSubmitDate(docTO.getSubmitDate());
								}
								if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
									session= request.getSession(false);
									if(session!=null){
										session.setAttribute(ApplicationEditAction.PHOTOBYTES, docTO.getPhotoBytes());
									}
								}
							}
						}
					}else{
					
						request.setAttribute("STUDENT_IMAGE", "images/StudentPhotos/"+applicantDetails.getStudentId()+".jpg?"+applicantDetails.getLastModifiedDate());
					}
					
				
					List<CourseTO> preferences=null;
					// preferences
					if(applicantDetails.getPreference()!=null){
						PreferenceTO prefTo= applicantDetails.getPreference();
						List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
						if(prefTo.getFirstPrefCourseId()!=null || !StringUtils.isEmpty(prefTo.getFirstPrefCourseId()))
						{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							firstTo.setId(prefTo.getFirstPerfId());
							firstTo.setAdmApplnid(applicantDetails.getId());
							firstTo.setCoursId(prefTo.getFirstPrefCourseId());
							firstTo.setCoursName(prefTo.getFirstprefCourseName());
							firstTo.setProgId(prefTo.getFirstPrefProgramId());
							firstTo.setProgramtypeId(prefTo.getFirstPrefProgramTypeId());
							firstTo.setPrefNo(1);
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							preferences=firstTo.getPrefcourses();
							prefTos.add(firstTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							firstTo.setPrefNo(1);
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
							formhandler.populatePreferenceTO(secTo,applicantCourse);
							preferences=secTo.getPrefcourses();
							prefTos.add(secTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							firstTo.setPrefNo(2);
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
							formhandler.populatePreferenceTO(thirdTo,applicantCourse);
							preferences=thirdTo.getPrefcourses();
							prefTos.add(thirdTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							firstTo.setPrefNo(3);
							preferences=firstTo.getPrefcourses();
							prefTos.add(firstTo);
						}
						//raghu put comment and already storing
						//admForm.setPreferenceList(prefTos);
					}else{
						List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
						CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(firstTo,applicantCourse);
						preferences=firstTo.getPrefcourses();
						firstTo.setPrefNo(1);
						prefTos.add(firstTo);
						CandidatePreferenceTO secTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(secTo,applicantCourse);
						secTo.setPrefNo(2);
						prefTos.add(secTo);
						CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(thirdTo,applicantCourse);
						thirdTo.setPrefNo(3);
						prefTos.add(thirdTo);
						//raghu put comment and already storing
						//admForm.setPreferenceList(prefTos);

					}
					ExamGenHandler genHandler = new ExamGenHandler();
					HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
					admForm.setSecondLanguageList(secondLanguage);
					// this condition is for Christ Puc  Admission Edit
					/*if(admForm.getPucApplicationEdit()==true){
						Iterator<SubjectGroupTO> subList=sujectgroupList.iterator();
						String id[] = new String[sujectgroupList.size()];
						int count=0;
						Set<Integer> subSet=ApplicationEditHelper.getInstance().getSubjectGroupByYearAndCourse(applicantDetails.getAppliedYear(),selectedCourse.getId());
						while (subList.hasNext()) {
							SubjectGroupTO subjectGroupTO = (SubjectGroupTO) subList.next();
							if(subjectGroupTO.getIsCommonSubGrp()!=null && subjectGroupTO.getIsCommonSubGrp() && subSet.contains(subjectGroupTO.getId())){
								id[count]=String.valueOf(subjectGroupTO.getId());
								count=count+1;
							}
						}
						admForm.getApplicantDetails().setSubjectGroupIds(id);
						String interviewDate=ApplicationEditHandler.getInstance().getInterviewDateOfApplicant(applicationNumber,applicationYear);
						if(interviewDate!=null)
						admForm.setPucInterviewDate(interviewDate);
					}*/
					if(CMSConstants.SHOW_LINK.equals("true")){
						admForm.setIsPresidance(false);
					}else{
						admForm.setIsPresidance(true);
					}
					//for ajax setting put preference lists in session
					session= request.getSession(false);
					if(session!=null){
						session.setAttribute(CMSConstants.COURSE_PREFERENCES, preferences);
					}
				}
			
			}catch(ApplicationException e){
				log.error("error in detailApplicationEdit...",e);
				String msg=super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
				log.error("error in detailApplicationEdit...",e);
					throw e;
				
			}
		log.info("exit detailApplicationEdit..");
		if(admForm.isPucApplicationDetailView()){
			return mapping.findForward("PUCApplicantDetailViewNew");
		}
		else if(admForm.getPucApplicationDetailEdit()){
			return mapping.findForward("PUCApplicantDetailEditNew");
		}
		else 
 			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
	}

	/**
	 * @param admForm
	 */
	private void checkLateralTransferDisplay(ApplicationEditForm admForm) {
		boolean isextra=false;
		
		if(admForm.isDisplayLateralDetails())
			isextra=true;
		if(admForm.isDisplayTransferCourse())
			isextra=true;
		admForm.setDisplayLateralTransfer(isextra);
		
	}

	/**
	 * @param admForm
	 */
	private void checkExtradetailsDisplay(ApplicationEditForm admForm) {
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
	 * admission form submit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateApplicationEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered updateApplicationEdit..");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		setUserId(request, admForm);
		try{
			//validation if needed
			ActionMessages errors=admForm.validate(mapping, request);
			
			//raghu removing attribute from session first mark entry detail for degree 
			HttpSession session = request.getSession(false);
			session.removeAttribute(admForm.getApplicationNumber());
			//raghu added newly
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
					return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
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
					return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
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
	        	errors.add(CMSConstants.ADMISSIONFORM_COURSEPREF_DUP_INVALID,error);
	       		saveErrors(request, errors);
	       		//if(admForm.isOnlineApply())
	       		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
	    		//else
	    		//	return mapping.findForward("OfflineAppBasicPage");

	        }
	        

			validateEditProgramCourse(errors, admForm);
			if(admForm.getPucApplicationEdit() || admForm.getPucApplicationDetailEdit()){
				validateEditPhoneForPUC(admForm, errors);
			}else{
				validateEditPhone(admForm, errors);
			}
			validateEditParentPhone(admForm, errors);
			validateEditGuardianPhone(admForm, errors);
			validateEditPassportIfNRI(admForm, errors);
			validateEditOtherOptions(admForm, errors);
			validateEditCommAddress(admForm, errors);
			//validateEditCoursePreferences(admForm, errors);
		//	validateSubjectGroups(admForm, errors);
			if(admForm.isDisplayTCDetails())
			validateTcDetailsEdit(admForm, errors);
			if(admForm.isDisplayEntranceDetails())
			validateEntanceDetailsEdit(admForm, errors);
			
			if(!admForm.getPucApplicationEdit() && !admForm.getPucApplicationDetailEdit()){
				String at=String.valueOf(admForm.getApplicantDetails().getPersonalData().getAreaType());
				if(at.isEmpty() || at.equals(" "))
				errors.add("admissionFormForm.areaType.required",new ActionError("admissionFormForm.areaType.required"));
				
				validatePaymentDetails(errors, admForm);
			if (admForm.getApplicantDetails().getJournalNo()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getJournalNo())) {
				
				if (errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED));
				}
			
			}
			if (admForm.getApplicantDetails().getChallanDate()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getChallanDate())) {
					errors.add("admissionFormForm.applicationDate.required", new ActionError("admissionFormForm.applicationDate.required"));
			}
			if (admForm.getApplicantDetails().getAmount()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getAmount())) {
		
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED));
				}

			}
			}
			
			/*if(admForm.isAdmissionEdit() && (admForm.getApplicantDetails().getAdmissionDate() == null || admForm.getApplicantDetails().getAdmissionDate().isEmpty())){
				errors.add("knowledgepro.admission.date", new ActionError("knowledgepro.admission.date"));
			}*/
			if(!admForm.isAdmissionEdit() && (admForm.getRecomendedBy()!= null || !admForm.getRecomendedBy().isEmpty())){
				if(admForm.getRecomendedBy().length()>200)
				errors.add("knowledgepro.admission.recommended.By.maxlength", new ActionError("knowledgepro.admission.recommended.By.maxlength"));
			}
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
			// this validation for puc christ college
			/*if(admForm.getPucApplicationEdit()==true && admForm.getPucInterviewDate()!=null){
				Date startDate = new Date();
				Date endDate = CommonUtil.ConvertStringToDate(admForm.getPucInterviewDate());

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if(daysBetween <= 0) {
					errors.add("error", new ActionError("knowledgePro.interview.date.puc.admissionForm"));
				}
			}*/
			
			/*// if admission validate admission date
			if(admForm.isAdmissionEdit()){
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
			}*/
			
			
			
			
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
			
			
//			List<ApplicantWorkExperienceTO> expList=admForm.getApplicantDetails().getWorkExpList();
//			if(expList!=null){
//				Iterator<ApplicantWorkExperienceTO> toItr=expList.iterator();
//				while (toItr.hasNext()) {
//					ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) toItr	.next();
//					validateWorkExperience(expTO, errors);
//				}
//			}
			
			List<ApplicantWorkExperienceTO> expList=admForm.getApplicantDetails().getWorkExpList();
			if(expList!=null){
				Iterator<ApplicantWorkExperienceTO> toItr=expList.iterator();
				int count=0;
				while (toItr.hasNext()) {
					ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) toItr	.next();
					validateWorkExperience(expTO, errors);
					if(admForm.isWorkExpMandatory()){
						if(expTO.getFromDate()!=null && !StringUtils.isEmpty(expTO.getFromDate()) && CommonUtil.isValidDate(expTO.getFromDate()) &&
								expTO.getToDate()!=null && !StringUtils.isEmpty(expTO.getToDate()) && CommonUtil.isValidDate(expTO.getToDate()) &&
								expTO.getOrganization()!=null && !StringUtils.isEmpty(expTO.getOrganization())){
							count++;
						}
					}
				}
				if(admForm.isWorkExpMandatory()&&count==0){
					errors.add(CMSConstants.ERROR,new ActionError("errors.required","Work Experience"));
				}
			}
			
			if(!admForm.isApplicationConfirm())
			validateEditEducationDetails(errors, admForm);
			validateEditDocumentSize(admForm, errors,request);
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

		/*	if(!admForm.isAdmissionEdit()){
				if(admForm.isExamCenterRequired() && admForm.getApplicantDetails().getExamCenterId() == 0){
					if (errors.get("knowledgepro.admission.appln.exam.center.required") != null&& !errors.get("knowledgepro.admission.appln.exam.center.required").hasNext()) {
						errors.add("knowledgepro.admission.appln.exam.center.required",new ActionError("knowledgepro.admission.appln.exam.center.required"));
					}
				}
			}*/
			
			
			//raghu
			
			if(admForm.getApplicantDetails().getPersonalData().getParentAddressLine1()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressLine1().isEmpty()|| 
					admForm.getApplicantDetails().getPersonalData().getParentAddressLine2()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressLine2().isEmpty()||
					//admForm.getApplicantDetails().getPersonalData().getParentAddressLine3()==null || admForm.getApplicantDetails().getPersonalData().getParentAddressLine3().isEmpty()||
					admForm.getApplicantDetails().getPersonalData().getParentCityName()==null || admForm.getApplicantDetails().getPersonalData().getParentCityName().isEmpty()||
					admForm.getApplicantDetails().getPersonalData().getParentCountryId()==0 || admForm.getApplicantDetails().getPersonalData().getParentStateId()==null ||
					admForm.getApplicantDetails().getPersonalData().getParentStateId().isEmpty()){
				
				errors.add(CMSConstants.ADMISSIONFORM_PARENT_ADDRESS_REQUIRED,new ActionError(CMSConstants.ADMISSIONFORM_PARENT_ADDRESS_REQUIRED));
				
			}
			
			//if( !admForm.getApplicantDetails().getPersonalData().getReligionId().equalsIgnoreCase("") && Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getReligionId())==3){
				//if((admForm.getApplicantDetails().getPersonalData().getParishId()==null || admForm.getApplicantDetails().getPersonalData().getParishId().isEmpty())|| 
					//	(admForm.getApplicantDetails().getPersonalData().getDioceseId()==null || admForm.getApplicantDetails().getPersonalData().getDioceseId()==null)){
					//raghu put comment
					//errors.add(CMSConstants.ADMISSIONFORM_PARISHANDDIOESE_REQUIRED,new ActionError(CMSConstants.ADMISSIONFORM_PARISHANDDIOESE_REQUIRED));
				//}
			//}
			
			if(Integer.parseInt(admForm.getProgramTypeId())==2){
				if((admForm.getApplicantDetails().getPersonalData().getUgcourse()!=null && StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getUgcourse())) || (!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getUgcourse()))){
					errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Ug Course "));
				}
			}
			
			
			if(admForm.getIsInterviewSelectionSchedule()!=null && admForm.getIsInterviewSelectionSchedule().equalsIgnoreCase("true")){
				if(admForm.getInterviewSelectionDate()== null || admForm.getInterviewSelectionDate().isEmpty()){
					if (errors.get("knowledgepro.admission.appln.interview.date.required") != null && !errors.get("knowledgepro.admission.appln.interview.date.required").hasNext()) {
						errors.add("knowledgepro.admission.appln.interview.date.required",new ActionError("knowledgepro.admission.appln.interview.date.required"));
						}
					}	
					if(admForm.getInterviewVenue()==null || admForm.getInterviewVenue().isEmpty()){
						if (errors.get("knowledgepro.admission.appln.interview.venue.required") != null && !errors.get("knowledgepro.admission.appln.interview.venue.required").hasNext()) {
							errors.add("knowledgepro.admission.appln.interview.venue.required",new ActionError("knowledgepro.admission.appln.interview.venue.required"));
					}
				}
			}
			AdmApplnTO applicantDetail=admForm.getApplicantDetails();
			if(errors==null)
				errors= new ActionMessages();

				if (errors != null && !errors.isEmpty()) {
					
					
				
					//raghu
					/*if(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId().trim())){
						Map<Integer,String> curdistMap=CommonAjaxHandler.getInstance().getDistrictByStateId(Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()));
						admForm.setCurdistMap(curdistMap);
					}
						
					if(admForm.getApplicantDetails().getPersonalData().getPermanentDistricId()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentDistricId())){
						Map<Integer,String> perdistMap=CommonAjaxHandler.getInstance().getDistrictByStateId(Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getPermanentDistricId()));
						admForm.setPerdistMap(perdistMap);
					}
					*/	
					resetHardCopySubmit(admForm.getApplicantDetails());
					saveErrors(request, errors);
					request.setAttribute("STUDENT_IMAGE", "images/StudentPhotos/"+applicantDetail.getStudentId()+".jpg?"+new Date());
					if(admForm.getPucApplicationDetailEdit()){
						return mapping.findForward("PUCApplicantDetailEditNew");
					}
					else if(!admForm.isAdmissionEdit())
						return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
					
					
				}
		ApplicationEditHandler handler= ApplicationEditHandler.getInstance();
		//check for seat allocation exceeded for admitted through or not
		boolean exceeded=false;
		/*if(admForm.isAdmissionEdit()){
			if(!admForm.isQuotaCheck()){
			if(applicantDetail.getAdmittedThroughId()!=null && !StringUtils.isEmpty(applicantDetail.getAdmittedThroughId()) && StringUtils.isNumeric(applicantDetail.getAdmittedThroughId()) && applicantDetail.getCourse()!=null && applicantDetail.getCourse().getId()!=0){
				exceeded=handler.checkSeatAllocationExceeded(Integer.parseInt(applicantDetail.getAdmittedThroughId()),applicantDetail.getCourse().getId());
			}
			}
		}*/
		if(exceeded)
		{
			admForm.setQuotaCheck(true);
			resetHardCopySubmit(applicantDetail);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = new ActionMessage(
					CMSConstants.ADMISSIONFORM_EDIT_WARN);
			messages.add("messages", message);
			saveMessages(request, messages);
			if(!admForm.isAdmissionEdit())
				return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
		}else{
		/*	if(admForm.isAdmissionEdit() && !admForm.isEligibleCheck()){
				boolean eligible=handler.checkEligibility(applicantDetail);
				
					if(!eligible)
					{
						admForm.setQuotaCheck(true);
						admForm.setEligibleCheck(true);
						resetHardCopySubmit(applicantDetail);
						ActionMessages messages = new ActionMessages();
						ActionMessage message = new ActionMessage(
								CMSConstants.ADMISSIONFORM_ELIGIBILITY_WARN);
						messages.add("messages", message);
						saveErrors(request, messages);
						return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
					}
				
			}
			if(admForm.isAdmissionEdit()){
				boolean admitted=handler.checkAdmitted(applicantDetail);
				if(admitted)
				{
					admForm.setQuotaCheck(true);
					admForm.setEligibleCheck(true);
					resetHardCopySubmit(applicantDetail);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = new ActionMessage(
							CMSConstants.ADMISSIONFORM_ADMISSION_DONE);
					messages.add("messages", message);
					saveErrors(request, messages);
					return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
				}
			}*/
			boolean result=handler.updateCompleteApplication(applicantDetail,admForm,false);

			if(result){
				admForm.setQuotaCheck(false);
				admForm.setEligibleCheck(false);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.ADMISSIONFORM_EDIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
			}
		}
		}catch (Exception e){
		log.error("Error in  getApplicantDetails application form edit page...",e);
		if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else {
			throw e;
		}
	}
		boolean pucAdmissionEdit=admForm.getPucApplicationEdit();
		boolean pucApplicationDetailEdit=admForm.getPucApplicationDetailEdit();
		cleanupEditData(admForm);
		cleanupEditSessionData(request);
		admForm.setPucApplicationEdit(pucAdmissionEdit);
		admForm.setPucApplicationDetailEdit(pucApplicationDetailEdit);
		log.info("exit updateApplicationEdit..");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_MODIFY_CONFIRM_PAGE_NEW);
	}


	/**
	 * @param errors
	 * @param admForm
	 * @throws Exception
	 */
	private void validatePaymentDetails(ActionMessages errors,ApplicationEditForm admForm) throws Exception{
		if(admForm.getApplicantDetails().getDdPayment()){
        if (admForm.getApplicantDetails().getDdDrawnOn()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getDdDrawnOn())) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED));
				}
			}
        if(admForm.getApplicantDetails().getDdIssuingBank()== null || admForm.getApplicantDetails().getDdIssuingBank().isEmpty()){
			if (errors.get(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED));
			}
		 }
		}
		else if(admForm.getApplicantDetails().getChallanPayment()){
			if (admForm.getApplicantDetails().getChallanRefNo()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getChallanRefNo())) {
				
				if (errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED));
				}
			
		}
			if(admForm.getApplicantDetails().getChallanRefNo()!=null && !admForm.getApplicantDetails().getChallanRefNo().isEmpty() 
					&& admForm.getApplicantDetails().getChallanRefNo().length()>30){
				if (errors.get(CMSConstants.ADMISSIONFORM_CHALLAN_NO_MAX_LEN_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CHALLAN_NO_MAX_LEN_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_CHALLAN_NO_MAX_LEN_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_CHALLAN_NO_MAX_LEN_REQUIRED));
				}
			}
		}
		
	}

	/**
	 * @param request
	 */
	private void cleanupEditSessionData(HttpServletRequest request) {
		log.info("enter cleanupEditSessionData...");
		HttpSession session=request.getSession(false);
		if(session==null){
			return;
		}else{
			if(session.getAttribute(ApplicationEditAction.PHOTOBYTES)!=null)
				session.removeAttribute(ApplicationEditAction.PHOTOBYTES);
			if(session.getAttribute(CMSConstants.KNOWLEDGEPRO_LOGO)!=null){
				session.removeAttribute(CMSConstants.KNOWLEDGEPRO_LOGO);}
			
		}
	}

	/**
	 * @param applicantDetails
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
	 * @param admForm
	 * @param errors
	 * @param request
	 */
	private void validateEditDocumentSize(ApplicationEditForm admForm,
			ActionMessages errors, HttpServletRequest request) throws Exception {
		log.info("enter validate dcument size...");
		List<ApplnDocTO> uploadlist=admForm.getApplicantDetails().getEditDocuments();
		InputStream propStream=ApplicationEditAction.class.getResourceAsStream("/resources/application.properties");
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
				if(docTo.isPhoto() && file!=null ){
					boolean remove=validateImageHeightWidth(file.getFileData(), file.getFileName(), file.getContentType(), errors,request);
					admForm.setRemove(remove);
					if(maxPhotoSize< file.getFileSize()){
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
				}
				if(docTo.isNeedToProduceSemWiseMC() && !admForm.isOnlineApply()){
					if(docTo.isHardSubmitted()){
					if(docTo.getSemisterNo()==null || docTo.getSemisterNo().isEmpty()){
						errors.add(CMSConstants.ERROR,new ActionMessage("errors.required","Sem No in Document(s)"));
					}
					}
				}
			}
			
		}
		
	}

	/**
	 * @param fileData
	 * @param fileName
	 * @param contentType
	 * @param errors
	 * @param request
	 * @return
	 */
	private boolean validateImageHeightWidth(byte[] fileData, String fileName,String contentType, ActionMessages errors,HttpServletRequest request) throws Exception {
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
		      if(width > 97 || height > 97){
		    	  remove=true;
		    	  errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_DIMENSION));
		      }
		    if(file.exists()){
		    	file.delete();
		    }
		}
		return remove;
		}

	/**
	 * @param errors
	 * @param admForm
	 */
	private ActionMessages validateEditEducationDetails(ActionMessages errors,
			ApplicationEditForm admForm) {
		int doctypeId12=CMSConstants.CLASS12_DOCTYPEID;
		int doctypeIdDEG=CMSConstants.DEGREE_DOCTYPEID;
		log.info("enter validate education...");
		List<EdnQualificationTO> qualifications=admForm.getApplicantDetails().getEdnQualificationList();
		if(qualifications!=null){
			Iterator<EdnQualificationTO> qualificationTO= qualifications.iterator();
			while (qualificationTO.hasNext()) {
				EdnQualificationTO qualfTO = (EdnQualificationTO) qualificationTO
						.next();
				if((qualfTO.getUniversityId()==null ||(qualfTO.getUniversityId().length()==0 )|| qualfTO.getUniversityId().equalsIgnoreCase("Other")) && (qualfTO.getUniversityOthers()==null ||(qualfTO.getUniversityOthers().trim().length()==0 )))
				{
						if(errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED,error);
						}
				}
				if((qualfTO.getInstitutionId()==null ||qualfTO.getInstitutionId().length()==0 )||(qualfTO.getInstitutionId().equalsIgnoreCase("Other") && (qualfTO.getOtherInstitute()==null ||(qualfTO.getOtherInstitute().trim().length()==0 ))))
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
				if(!admForm.getPucApplicationEdit() && !admForm.getPucApplicationDetailEdit()){
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
				}
				if(qualfTO.getDocTypeId()==doctypeId12 || qualfTO.getDocTypeId()==doctypeIdDEG){
					
					if(qualfTO.getNoOfAttempts()==0)
					{
							if (errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED,error);
							}
					}
					}
				if(qualfTO.isConsolidated()){/*
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
					if(qualfTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualfTO.getMarksObtained()) 
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
					
				*/}else{
					if(qualfTO.isSemesterWise()){
						if(qualfTO.getSemesterList()==null)
						{
								if (errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED).hasNext()) {
									ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED);
									errors.add(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED,error);
								}
						}
					}else if(qualfTO.getDetailmark()==null)
					{
							if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,error);
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
				
			}
			log.info("exit validate education...");
		}
		

		return errors;
	}

	/**
	 * @param expTO
	 * @param errors
	 */
	private void validateWorkExperience(ApplicantWorkExperienceTO TO,
			ActionMessages errors) {
		log.info("enter validateWorkExperience..");
		if(errors==null)
			errors= new ActionMessages();
		if(TO!=null)
		{
			boolean validTODate=false;
			boolean validFromDate=false;
			if(TO.getFromDate()!=null && !StringUtils.isEmpty(TO.getFromDate()) && !CommonUtil.isValidDate(TO.getFromDate())){
				validFromDate=false;
			}else{
				validFromDate=true;
			}
			if(TO.getToDate()!=null && !StringUtils.isEmpty(TO.getToDate()) && !CommonUtil.isValidDate(TO.getToDate())){
				validTODate=false;
			}else{
				validTODate=true;
			}
			if(validTODate && validFromDate){
			if(TO.getFromDate()!=null && !TO.getFromDate().isEmpty() && TO.getToDate()!=null && !TO.getToDate().isEmpty())
			{
				String formdate=CommonUtil.ConvertStringToDateFormat(TO.getFromDate(), ApplicationEditAction.FROM_DATEFORMAT,ApplicationEditAction.TO_DATEFORMAT);
				String todate=CommonUtil.ConvertStringToDateFormat(TO.getToDate(), ApplicationEditAction.FROM_DATEFORMAT,ApplicationEditAction.TO_DATEFORMAT);
				Date startdate=new Date(formdate);
				Date enddate=new Date(todate);
				
				
					if(startdate.compareTo(enddate)==1)
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE, new ActionError(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE));
						}
					}
					if(enddate.compareTo(startdate)==0)
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_EXP_DATESAME)!=null && !errors.get(CMSConstants.ADMISSIONFORM_EXP_DATESAME).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_EXP_DATESAME, new ActionError(CMSConstants.ADMISSIONFORM_EXP_DATESAME));
						}
						
					}

			}
		}else if(!validTODate){
			if (errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID));
			}
		}else if(!validFromDate){
			if (errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID));
			}
		}
			if(TO.getSalary()!=null && !StringUtils.isEmpty(TO.getSalary()) && !CommonUtil.isValidDecimal(TO.getSalary()) ){
				if (errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID));
				}
			}
		}
		log.info("exit validateWorkExperience..");
	}

	/**
	 * @param passportValidity
	 * @return
	 */
	private boolean validatePastDate(String dateString) {
		log.info("enter validatePastDate..");
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, ApplicationEditAction.FROM_DATEFORMAT,ApplicationEditAction.TO_DATEFORMAT);
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
	 * @param admForm
	 * @param errors
	 */
	private void validateEntanceDetailsEdit(ApplicationEditForm admForm,
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
	 * @param parseInt
	 * @param parseInt2
	 * @param today
	 * @return
	 */
	private boolean isPassYearGreaterThanToday(int yearPassing, int monthPassing,
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
	 * @param parseInt
	 * @param parseInt2
	 * @param dob
	 * @return
	 */
	private boolean isPassYearGreaterThanBirth(int yearPassing, int monthPassing,
			String dateOfBirth) {
		boolean result=false;
		if(yearPassing!=0 && dateOfBirth!=null && !StringUtils.isEmpty(dateOfBirth)){
			String formattedString=CommonUtil.ConvertStringToDateFormat(dateOfBirth, ApplicationEditAction.FROM_DATEFORMAT,ApplicationEditAction.TO_DATEFORMAT);
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
	 * @param admForm
	 * @param errors
	 */
	private void validateTcDetailsEdit(ApplicationEditForm admForm,
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
	 * @param tcDate
	 * @return
	 */
	private boolean validatefutureDate(String dateString) {
		log.info("enter validatefutureDate..");
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, ApplicationEditAction.FROM_DATEFORMAT,ApplicationEditAction.TO_DATEFORMAT);
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
	 * @param admForm
	 * @param errors
	 */
	private void validateEditCoursePreferences(ApplicationEditForm admForm,ActionMessages errors) {
		log.info("enter validateEditCoursePreferences..");
		List<CandidatePreferenceTO> prefcourses = admForm.getPreferenceList();
		Iterator<CandidatePreferenceTO> itr = prefcourses.iterator();
		CandidatePreferenceTO candidatePreferenceTO;
		while(itr.hasNext()){
			candidatePreferenceTO = itr.next();
			if(candidatePreferenceTO.getPrefNo() == 1 && candidatePreferenceTO.getCoursId()!=null && candidatePreferenceTO.getCoursId().length() == 0) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED, error);
			}
		}
		log.info("exit validateEditCoursePreferences..");
	}

	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateEditCommAddress(ApplicationEditForm admForm,ActionMessages errors) {
		log.info("enter validateEditCommAddress..");
		if(errors==null)
			errors= new ActionMessages();
		
			if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine1()))
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_ADDRESS1_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_ADDRESS1_REQUIRED,error);
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
			if((admForm.getApplicantDetails().getPersonalData().getCurrentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentStateId()))&& (admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED, error);
				}
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
		
			log.info("exit validateEditCommAddress..");
	}

	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateEditOtherOptions(ApplicationEditForm admForm,ActionMessages errors) throws Exception {
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
		if((admForm.getApplicantDetails().getPersonalData().getReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId())|| (admForm.getApplicantDetails().getPersonalData().getReligionId().equalsIgnoreCase(ApplicationEditAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionOthers()) ))
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
			//if(relTxn.checkSubReligionExists(Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getReligionId()))){
				//if(admForm.isCasteDisplay()){
					if((admForm.getApplicantDetails().getPersonalData().getSubReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getSubReligionId())|| (admForm.getApplicantDetails().getPersonalData().getSubReligionId().equalsIgnoreCase(ApplicationEditAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers())) )
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_CASTE_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED, error);
						}
					}
				//}
			//}
		}
		if(!admForm.getIsPresidance()){
			if((admForm.getApplicantDetails().getPersonalData().getCasteId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteId())|| (admForm.getApplicantDetails().getPersonalData().getCasteId().equalsIgnoreCase(ApplicationEditAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getCasteOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteOthers()) ))
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED, error);
				}
			}
		}
		/*if((admForm.getApplicantDetails().getPersonalData().getPermanentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentStateId()) || admForm.getApplicantDetails().getPersonalData().getPermanentStateId().equalsIgnoreCase("0") )&& ((admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers())==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED, error);
			}
		}*/
		log.info("exit validateEditOtherOptions..");
	}

	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPassportIfNRI(ApplicationEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditPassportIfNRI..");
		if(errors==null)
			errors= new ActionMessages();
		if(admForm.getApplicantDetails().getPersonalData().getResidentCategory()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getResidentCategory())){
			if(admForm.getApplicantDetails().getPersonalData().getResidentCategory()!=null && StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getResidentCategory())&& !CMSConstants.INDIAN_RESIDENT_LIST.contains(Integer.valueOf((Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getResidentCategory()))))){
				if(admForm.getApplicantDetails().getPersonalData().getPassportNo()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportNo()))
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORTNO_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORTNO_REQUIRED,error);
				}
				if(admForm.getApplicantDetails().getPersonalData().getPassportCountry()==0)
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORT_COUNTRY_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_COUNTRY_REQUIRED,error);
				}
				if(admForm.getApplicantDetails().getPersonalData().getPassportValidity()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportValidity()))
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_REQUIRED,error);
				}else if(admForm.getApplicantDetails().getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
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
				
			}
			
		}
		log.info("exit validateEditPassportIfNRI..");
}

	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateEditGuardianPhone(ApplicationEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditGuardianPhone..");
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
				log.info("exit validateEditGuardianPhone..");
	}

	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateEditParentPhone(ApplicationEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditParentPhone..");
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
				log.info("exit validateEditParentPhone..");
	}

	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPhone(ApplicationEditForm admForm,ActionMessages errors) {
		log.info("enter validateEditPhone..");
		if(errors==null)
			errors= new ActionMessages();
		
				/*if((admForm.getApplicantDetails().getPersonalData().getPhNo1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1()))
						&& (admForm.getApplicantDetails().getPersonalData().getPhNo2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2()))
						&& (admForm.getApplicantDetails().getPersonalData().getPhNo3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3())))
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED, error);
					}
				}*/

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
				
//				if(admForm.getApplicantDetails().getPersonalData().getMobileNo1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) )
//				{
//					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
//						ActionMessage error = new ActionMessage(
//								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
//						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
//					}
//				}
				
				if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) && admForm.getApplicantDetails().getPersonalData().getMobileNo2().trim().length()!=10 )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
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
				log.info("exit validateEditPhone..");
	}

	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPhoneForPUC(ApplicationEditForm admForm,ActionMessages errors) {
		log.info("enter validateEditPhone..");
		if(errors==null)
			errors= new ActionMessages();
		
				/*if((admForm.getApplicantDetails().getPersonalData().getPhNo1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1()))
						&& (admForm.getApplicantDetails().getPersonalData().getPhNo2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2()))
						&& (admForm.getApplicantDetails().getPersonalData().getPhNo3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3())))
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED, error);
					}
				}*/

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
				
//				if(admForm.getApplicantDetails().getPersonalData().getMobileNo1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) )
//				{
//					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
//						ActionMessage error = new ActionMessage(
//								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
//						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
//					}
//				}
				
				if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) && admForm.getApplicantDetails().getPersonalData().getMobileNo2().trim().length()!=10 )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
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
				log.info("exit validateEditPhone..");
	}

	/**
	 * @param errors
	 * @param admForm
	 */
	private ActionMessages validateEditProgramCourse(ActionMessages errors,
			ApplicationEditForm admForm) {
		log.info("enter validate program course...");
		if(admForm.getApplicantDetails().getCourse().getProgramTypeId() ==0)
		{
			if(errors==null){
				errors= new ActionMessages();
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED, error);
			}else{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getCourse().getProgramId() ==0)
		{
			if(errors==null){
				errors= new ActionMessages();
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
			}else
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getCourse().getId() ==0 )
		{
			if(errors==null){
				errors= new ActionMessages();
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
			}else{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
			}
		}
		log.info("exit validate program course...");
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
		ApplicationEditForm admForm=(ApplicationEditForm)form;
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
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_LANDINGPAGE);
		
	}
	/**
	 * cleans up session data
	 * @param session
	 */
	private void cleanupSessionData(HttpSession session) {
		log.info("cleaning up session data...");
		if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
			session.removeAttribute(CMSConstants.APPLICATION_DATA);
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
		if(session!= null && session.getAttribute(ApplicationEditAction.PHOTOBYTES)!=null)
			session.removeAttribute(ApplicationEditAction.PHOTOBYTES);
		
		session.removeAttribute("baseActionForm");
		log.info("session data cleaned...");
	}
	/**
	 * CLEANS UP OLD FORM DATA FROM SESSION
	 * @param session
	 */
	public static void cleanupFormFromSession(HttpSession session) {
		log.info("enter cleanupFormFromSession...");
		if(session.getAttribute("applicationEditForm")!=null)
			session.removeAttribute("applicationEditForm");
		log.info("exit cleanupFormFromSession...");
	}
	/**
	 * INIT SEMESTER MARK EDIT
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSemesterMarkEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initSemesterMarkEditPage...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		try {
			
			String indexString = request.getParameter("editcountID");
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null){
					session.setAttribute("editcountID", indexString);
					int index=Integer.parseInt(indexString);
					List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
					
					if(quals!=null ){
						Iterator<EdnQualificationTO> qualItr=quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if(qualTO.getId()==index){
								if(qualTO.getSemesterList()!=null){
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
									Collections.sort(semList);
									admForm.setSemesterList(semList);
								}
								else{
									List<ApplicantMarkDetailsTO> semList=new ArrayList<ApplicantMarkDetailsTO>();
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
						int totalobtainedMarkWithLanguage=0;
						int totalMarkWithLanguage=0;
						int totalobtainedMarkWithoutLan=0;
						int totalMarkWithoutLan=0;
						for(ApplicantMarkDetailsTO detailsTO:admForm.getSemesterList())
						{
							if(admForm.getIsLanguageWiseMarks().equalsIgnoreCase("true"))
							{
								totalobtainedMarkWithLanguage+=Integer.parseInt(detailsTO.getMarksObtained_languagewise());
								totalMarkWithLanguage+=Integer.parseInt(detailsTO.getMaxMarks_languagewise());
							}
							if(detailsTO.getMarksObtained()!=null && !detailsTO.getMarksObtained().isEmpty())
							totalobtainedMarkWithoutLan+=Integer.parseInt(detailsTO.getMarksObtained());
							if(detailsTO.getMaxMarks()!=null && !detailsTO.getMaxMarks().isEmpty())
							totalMarkWithoutLan+=Integer.parseInt(detailsTO.getMaxMarks());	
						}
						
						if(admForm.getIsLanguageWiseMarks().equalsIgnoreCase("true"))
						{
							admForm.setTotalobtainedMarkWithLanguage(""+totalobtainedMarkWithLanguage);
							admForm.setTotalMarkWithLanguage(""+totalMarkWithLanguage);
						}
						admForm.setTotalobtainedMarkWithoutLan(""+totalobtainedMarkWithoutLan);
						admForm.setTotalMarkWithoutLan(""+totalMarkWithoutLan);
					}
				}
				else
					session.removeAttribute("editcountID");
			}
		} catch (Exception e) {
			log.error("error in initSemesterMarkEditPage...",e);
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
		log.info("exit initSemesterMarkEditPage...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_SEMESTER_MARK_PAGE_NEW);
	}
	/**
	 * FORWARDS APPLICATION EDIT PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardModifyApplicationPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ApplicationEditForm admForm=(ApplicationEditForm)form;
		if(admForm.getPucApplicationDetailEdit()){
			return mapping.findForward("PUCApplicantDetailEditNew");
		}
		else return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
	}
	/**
	 * SUBMITS SUBMIT MARK EDIT
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitSemesterEditMark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitSemesterEditMark...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute("editcountID");
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			List<ApplicantMarkDetailsTO> semesterMarks = admForm.getSemesterList();
			ActionMessages errors = validateEditSemesterMarks(semesterMarks);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_SEMESTER_MARK_PAGE_NEW);
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getId()==detailMarkIndex){
						Set<ApplicantMarkDetailsTO> semDetails=new HashSet<ApplicantMarkDetailsTO>();
						semDetails.addAll(semesterMarks);
						qualTO.setSemesterList(semDetails);
					}
				}
			}
		} catch (Exception e) {
			log.error("error in submitSemesterEditMark...",e);
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
		log.info("exit submitSemesterEditMark...");
		if(admForm.getPucApplicationDetailEdit()){
			return mapping.findForward("PUCApplicantDetailEditNew");
		}
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
		
	}

	/**
	 * @param semesterMarks
	 * @return
	 */
	private ActionMessages validateEditSemesterMarks(List<ApplicantMarkDetailsTO> semesterMarks) {
		log.info("enter validateEditSemesterMarks...");
		ActionMessages errors= new ActionMessages();
		if(semesterMarks!=null){
			Iterator<ApplicantMarkDetailsTO> semItr=semesterMarks.iterator();
			while (semItr.hasNext()) {
				ApplicantMarkDetailsTO semMarkTO = (ApplicantMarkDetailsTO) semItr.next();
				
				
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
		
				
			}
			
			
		}
		log.info("exit validateEditSemesterMarks...");
		return errors;
	
	}
	/**
	 * INT DETAIL MARK EDIT
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init detail mark page...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		try {
			Map<Integer,String> subjectMap = ApplicationEditHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);
			String indexString = request.getParameter("editcountID");
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null){
					session.setAttribute("editcountID", indexString);
					int index=Integer.parseInt(indexString);
					List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
					if(quals!=null ){
						Iterator<EdnQualificationTO> qualItr=quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if(qualTO.getId()==index){
								if(qualTO.getDetailmark()!=null)
								admForm.setDetailMark(qualTO.getDetailmark());
								else{
									CandidateMarkTO markTo=new CandidateMarkTO();
									ApplicationEditHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
									admForm.setDetailMark(markTo);
								}
							}
						}
					}
				}
				else
					session.removeAttribute("editcountID");
			}
			

		} catch (Exception e) {
			log.error("error in init detail mark page...",e);
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
		log.info("exit init detail mark page...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_DETAIL_MARK_PAGE_NEW);
	}
	/**
	 * SUBMITS DETAIL MARK EDIT
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitDetailMarkEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitDetailMarkEdit page...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute("editcountID");
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			ActionMessages errors = validateMarks(detailmark);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_DETAIL_MARK_PAGE_NEW);
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getId()==detailMarkIndex){
						
						//raghu
						if(qualTO.getDetailmark()!=null)
						detailmark.setId(qualTO.getDetailmark().getId());
						qualTO.setDetailmark(detailmark);
					}
				}
			}
		} catch (Exception e) {
			log.error("error in submit detail mark page...",e);
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
		log.info("enter submitDetailMarkEdit page...");
		if(admForm.getPucApplicationDetailEdit()){
			return mapping.findForward("PUCApplicantDetailEditNew");
		}
		else return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
		
	}
	
	
	
	
	
	// raghu added newly
	
	

	
	
	
	

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
			
			if(detailmark.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && !CommonUtil.isValidDecimal(detailmark.getSubject1TotalMarks()) ||
					detailmark.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject2TotalMarks()) ||
					detailmark.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject3TotalMarks()) ||
					detailmark.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject4TotalMarks()) ||
					detailmark.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject5TotalMarks()) ||
					detailmark.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject6TotalMarks()) ||
					detailmark.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject7TotalMarks()) ||
					detailmark.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject8TotalMarks()) ||
					detailmark.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject9TotalMarks()) ||
					detailmark.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject10TotalMarks()) ||
					detailmark.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject11TotalMarks()) ||
					detailmark.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject12TotalMarks()) ||
					detailmark.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject13TotalMarks()) ||
					detailmark.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject14TotalMarks()) ||
					detailmark.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject15TotalMarks()) ||
					detailmark.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject16TotalMarks()) ||
					detailmark.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject17TotalMarks()) ||
					detailmark.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject18TotalMarks()) ||
					detailmark.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject19TotalMarks()) ||
					detailmark.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject20TotalMarks()) ||
					detailmark.getTotalMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getTotalMarks()))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER, error);
				}
				return errors;
			}
			if(detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && !CommonUtil.isValidDecimal(detailmark.getSubject1ObtainedMarks()) ||
					detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject2ObtainedMarks()) ||
					detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject3ObtainedMarks()) ||
					detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject4ObtainedMarks()) ||
					detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject5ObtainedMarks()) ||
					detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject6ObtainedMarks()) ||
					detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject7ObtainedMarks()) ||
					detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject8ObtainedMarks()) ||
					detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject9ObtainedMarks()) ||
					detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject10ObtainedMarks()) ||
					detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject11ObtainedMarks()) ||
					detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject12ObtainedMarks()) ||
					detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject13ObtainedMarks()) ||
					detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject14ObtainedMarks()) ||
					detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject15ObtainedMarks()) ||
					detailmark.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject16ObtainedMarks()) ||
					detailmark.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject17ObtainedMarks()) ||
					detailmark.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject18ObtainedMarks()) ||
					detailmark.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject19ObtainedMarks()) ||
					detailmark.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject20ObtainedMarks()) ||
					detailmark.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks()))
			{
				if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER, error);
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
			
			
			//////
			if(detailmark.isSemesterMark()){
				if(detailmark.getSubject1_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks()) && !CommonUtil.isValidDecimal(detailmark.getSubject1_languagewise_TotalMarks()) ||
						detailmark.getSubject2_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject2TotalMarks()) ||
						detailmark.getSubject3_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject3TotalMarks()) ||
						detailmark.getSubject4_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject4TotalMarks()) ||
						detailmark.getSubject5_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject5TotalMarks()) ||
						detailmark.getSubject6_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject6_languagewise_TotalMarks()) ||
						detailmark.getSubject7_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject7_languagewise_TotalMarks()) ||
						detailmark.getSubject8_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject8_languagewise_TotalMarks()) ||
						detailmark.getSubject9_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject9_languagewise_TotalMarks()) ||
						detailmark.getSubject10_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject10_languagewise_TotalMarks()) ||
						detailmark.getTotal_languagewise_Marks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks())&& !CommonUtil.isValidDecimal(detailmark.getTotal_languagewise_Marks()))
				{
					if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
					errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER, error);
					}
					return errors;
				}
				if(detailmark.getSubject1_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks()) && !CommonUtil.isValidDecimal(detailmark.getSubject1_languagewise_ObtainedMarks()) ||
						detailmark.getSubject2_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject2_languagewise_ObtainedMarks()) ||
						detailmark.getSubject3_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject3_languagewise_ObtainedMarks()) ||
						detailmark.getSubject4_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject4_languagewise_ObtainedMarks()) ||
						detailmark.getSubject5_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject5_languagewise_ObtainedMarks()) ||
						detailmark.getSubject6_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject6_languagewise_ObtainedMarks()) ||
						detailmark.getSubject7_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject7_languagewise_ObtainedMarks()) ||
						detailmark.getSubject8_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject8_languagewise_ObtainedMarks()) ||
						detailmark.getSubject9_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject9_languagewise_ObtainedMarks()) ||
						detailmark.getSubject10_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getSubject10_languagewise_ObtainedMarks()) ||
						detailmark.getTotal_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks())&& !CommonUtil.isValidDecimal(detailmark.getTotal_languagewise_ObtainedMarks()))
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
				
				if(detailmark.getSubject1_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks())&& detailmark.getSubject1_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks()) && Float.parseFloat(detailmark.getSubject1_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject1_languagewise_ObtainedMarks())||
						detailmark.getSubject2_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_TotalMarks())&& detailmark.getSubject2_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks()) && Float.parseFloat(detailmark.getSubject2_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject2_languagewise_ObtainedMarks())||
						detailmark.getSubject3_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_TotalMarks())&& detailmark.getSubject3_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks()) && Float.parseFloat(detailmark.getSubject3_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject3_languagewise_ObtainedMarks())||
						detailmark.getSubject4_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_TotalMarks())&&  detailmark.getSubject4_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks()) && Float.parseFloat(detailmark.getSubject4_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject4_languagewise_ObtainedMarks())||
						detailmark.getSubject5_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_TotalMarks())&& detailmark.getSubject5_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject5_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject5_languagewise_ObtainedMarks())||
						detailmark.getSubject6_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks())&& detailmark.getSubject6_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject6_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject6_languagewise_ObtainedMarks())||
						detailmark.getSubject7_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks())&& detailmark.getSubject7_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject7_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject7_languagewise_ObtainedMarks())||
						detailmark.getSubject8_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks())&& detailmark.getSubject8_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject8_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject8_languagewise_ObtainedMarks())||
						detailmark.getSubject9_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks())&& detailmark.getSubject9_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject9_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject9_languagewise_ObtainedMarks())||
						detailmark.getSubject10_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks())&& detailmark.getSubject10_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject10_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject10_languagewise_ObtainedMarks())||
						detailmark.getTotal_languagewise_Marks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks())&& detailmark.getTotal_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks()) && Float.parseFloat(detailmark.getTotal_languagewise_Marks())< Float.parseFloat(detailmark.getTotal_languagewise_ObtainedMarks())
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
	
	

	
	
	
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTransferEntryEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initTransferEntryEditPage...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		try {
			if(admForm.getApplicantDetails().getTransferDetails()==null || admForm.getApplicantDetails().getTransferDetails().isEmpty() ){
				List<ApplicantTransferDetailsTO> transferDetails= new ArrayList<ApplicantTransferDetailsTO>();
				for(int i=0;i<CMSConstants.MAX_CANDIDATE_TRANSFERS;i++){
					ApplicantTransferDetailsTO to= new ApplicantTransferDetailsTO();
					to.setSemesterNo(i);
					transferDetails.add(to);
				}
				Collections.sort(transferDetails);
				admForm.setTransferDetails(transferDetails);
			}else{
				if(admForm.getApplicantDetails().getTransferDetails()!=null && !admForm.getApplicantDetails().getTransferDetails().isEmpty()){

					Iterator<ApplicantTransferDetailsTO> latItr=admForm.getApplicantDetails().getTransferDetails().iterator();
					while (latItr.hasNext()) {
						ApplicantTransferDetailsTO tranfrTO = (ApplicantTransferDetailsTO) latItr.next();
						if(tranfrTO!=null){
							if(tranfrTO.getInstituteAddr()!=null && !StringUtils.isEmpty(tranfrTO.getInstituteAddr()))
							admForm.setTransInstituteAddr(tranfrTO.getInstituteAddr());
							if(tranfrTO.getRegistationNo()!=null && !StringUtils.isEmpty(tranfrTO.getRegistationNo()))
							admForm.setTransRegistationNo(tranfrTO.getRegistationNo());
							if(tranfrTO.getUniversityAppNo()!=null && !StringUtils.isEmpty(tranfrTO.getUniversityAppNo()))
							admForm.setTransUnvrAppNo(tranfrTO.getUniversityAppNo());
							if(tranfrTO.getArrearDetail()!=null && !StringUtils.isEmpty(tranfrTO.getArrearDetail()))
								admForm.setTransArrearDetail(tranfrTO.getArrearDetail());
						}
						
					}
					Collections.sort(admForm.getApplicantDetails().getTransferDetails());
					admForm.setTransferDetails(admForm.getApplicantDetails().getTransferDetails());
				}
				
				
				
				
			}
		} catch (Exception e) {
			log.error("error in initTransferEntryEditPage...",e);
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
		log.info("exit initTransferEntryEditPage...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_TRANSFER_SEMESTEREDIT_PAGE_NEW);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitTransferEditEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitTransferEditEntry...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		try {
			 ActionErrors errors = admForm.validate(mapping, request);
			//Validate marks
			validateTransferMarks(admForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				
				return mapping.findForward(CMSConstants.ADMISSIONFORM_TRANSFER_SEMESTEREDIT_PAGE_NEW);
			}
			// process lateral details
			List<ApplicantTransferDetailsTO> transferDetails=admForm.getTransferDetails();
			if(transferDetails!=null && !transferDetails.isEmpty()){
				admForm.getApplicantDetails().setTransferDetails(transferDetails);
				Set<ApplicantTransferDetails> transferBos= new HashSet<ApplicantTransferDetails>();
				Iterator<ApplicantTransferDetailsTO> latItr=transferDetails.iterator();
				while (latItr.hasNext()) {
					ApplicantTransferDetailsTO transferTO = (ApplicantTransferDetailsTO) latItr.next();
					if(transferTO!=null){
						ApplicantTransferDetails bo= new ApplicantTransferDetails();
						bo.setId(transferTO.getId());
						if(transferTO.getAdmApplnId()!=0)
						{
							AdmAppln app= new AdmAppln();
							app.setId(transferTO.getAdmApplnId());
							bo.setAdmAppln(app);
						}
						bo.setInstituteAddr(admForm.getTransInstituteAddr());
						if(transferTO.getMarksObtained()!=null && !StringUtils.isEmpty(transferTO.getMarksObtained().trim()) && CommonUtil.isValidDecimal(transferTO.getMarksObtained().trim()))
						bo.setMarksObtained(new BigDecimal(transferTO.getMarksObtained().trim()));
						if(transferTO.getMaxMarks()!=null && !StringUtils.isEmpty(transferTO.getMaxMarks().trim()) && CommonUtil.isValidDecimal(transferTO.getMaxMarks().trim()))
						bo.setMaxMarks(new BigDecimal(transferTO.getMaxMarks().trim()));
						if(transferTO.getMinMarks()!=null && !StringUtils.isEmpty(transferTO.getMinMarks().trim()) && CommonUtil.isValidDecimal(transferTO.getMinMarks().trim()))
							bo.setMinMarks(new BigDecimal(transferTO.getMinMarks().trim()));
						if(transferTO.getMonthPass()!=null && !StringUtils.isEmpty(transferTO.getMonthPass().trim()) && StringUtils.isNumeric(transferTO.getMonthPass().trim()))
							bo.setMonthPass(Integer.parseInt(transferTO.getMonthPass().trim()));
						
						if(transferTO.getYearPass()!=null && !StringUtils.isEmpty(transferTO.getYearPass().trim()) && StringUtils.isNumeric(transferTO.getYearPass().trim()))
							bo.setYearPass(Integer.parseInt(transferTO.getYearPass().trim()));
						bo.setSemesterName(transferTO.getSemesterName());
						bo.setSemesterNo(transferTO.getSemesterNo());
						bo.setRegistationNo(admForm.getTransRegistationNo());
						bo.setUniversityAppNo(admForm.getTransUnvrAppNo());
						bo.setArrearDetail(admForm.getTransArrearDetail());
						transferBos.add(bo);
					}
					
				}
				
				if(transferBos!=null && !transferBos.isEmpty()){
					admForm.getApplicantDetails().setTransferDetailBos(transferBos);
				}
			}
		} catch (Exception e) {
			log.error("error in submitTransferEditEntry...",e);
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
		log.info("exit submitLateralEntryEdit...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
		
	}

	/**
	 * validate lateral marks
	 * @param admForm
	 * @param errors
	 */
	private void validateTransferMarks(ApplicationEditForm admForm,
			ActionErrors errors) {
		log.info("enter validateTransferMarks...");
		List<ApplicantTransferDetailsTO> transferDetails=admForm.getTransferDetails();
		if(transferDetails!=null && !transferDetails.isEmpty()){
			Iterator<ApplicantTransferDetailsTO> latItr=transferDetails.iterator();
			while (latItr.hasNext()) {
				ApplicantTransferDetailsTO transferTO = (ApplicantTransferDetailsTO) latItr.next();
				if(transferTO!=null){
					if(transferTO.getMarksObtained()!=null && !StringUtils.isEmpty(transferTO.getMarksObtained().trim())){
						if(!CommonUtil.isValidDecimal(transferTO.getMarksObtained())){
							//error in valid mark obtain
							if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID).hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID);
								errors.add(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID, error);
							}
						}
					}
					if(transferTO.getMaxMarks()!=null && !StringUtils.isEmpty(transferTO.getMaxMarks().trim())){
						 if(!CommonUtil.isValidDecimal(transferTO.getMaxMarks())){
								//error in valid max mark
							 if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID).hasNext()) {
									ActionMessage error = new ActionMessage(
											CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID);
									errors.add(CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID, error);
								}
							 }
					}
					if(transferTO.getMinMarks()!=null && !StringUtils.isEmpty(transferTO.getMinMarks().trim())){
						 if(!CommonUtil.isValidDecimal(transferTO.getMinMarks())){
								//error in valid min mark
							 if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID).hasNext()) {
									ActionMessage error = new ActionMessage(
											CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID);
									errors.add(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID, error);
								}
							 }
					}
					if(transferTO.getMarksObtained()!=null && !StringUtils.isEmpty(transferTO.getMarksObtained().trim()) && transferTO.getMaxMarks()!=null && !StringUtils.isEmpty(transferTO.getMaxMarks().trim()))
					{
						
						
						if(CommonUtil.isValidDecimal(transferTO.getMarksObtained())&& CommonUtil.isValidDecimal(transferTO.getMaxMarks())){
							if(Double.parseDouble(transferTO.getMarksObtained())> Double.parseDouble(transferTO.getMaxMarks())){
								//error mark obtain large than max mark
								 if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER).hasNext()) {
										ActionMessage error = new ActionMessage(
												CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER);
										errors.add(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER, error);
									}
							}
						}
						
					}
					
					if(transferTO.getMinMarks()!=null && !StringUtils.isEmpty(transferTO.getMinMarks().trim()) && transferTO.getMaxMarks()!=null && !StringUtils.isEmpty(transferTO.getMaxMarks().trim()))
					{
						
						
						if(CommonUtil.isValidDecimal(transferTO.getMinMarks())&& CommonUtil.isValidDecimal(transferTO.getMaxMarks())){
							if(Double.parseDouble(transferTO.getMinMarks())> Double.parseDouble(transferTO.getMaxMarks())){
								//error min mark large than max mark
								 if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER).hasNext()) {
										ActionMessage error = new ActionMessage(
												CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER);
										errors.add(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER, error);
									}
							}
						}
						
					}
					
					if(transferTO.getYearPass()!=null && !StringUtils.isEmpty(transferTO.getYearPass()) && StringUtils.isNumeric(transferTO.getYearPass())){
						if(CommonUtil.isFutureYear(Integer.parseInt(transferTO.getYearPass()))){
							if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE).hasNext()) {
								ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE);
								errors.add(CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE, error);
							}
						}
					}
					
					if(transferTO.getMonthPass()!=null && !StringUtils.isEmpty(transferTO.getMonthPass()) && StringUtils.isNumeric(transferTO.getMonthPass())){
						if(transferTO.getYearPass()!=null && !StringUtils.isEmpty(transferTO.getYearPass()) && StringUtils.isNumeric(transferTO.getYearPass())){
							if(!CommonUtil.isPastYear(Integer.parseInt(transferTO.getYearPass()))){
						
								if(CommonUtil.isFutureMonth(Integer.parseInt(transferTO.getMonthPass())-1)){
									if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE).hasNext()) {
										ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE);
										errors.add(CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE, error);
									}
								}
							}
						}
					}
				}
			}
		}
		
		log.info("exit validateTransferMarks...");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initlateralEntryEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initlateralEntryEditPage...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		try {
			if(admForm.getApplicantDetails().getLateralDetails()==null || admForm.getApplicantDetails().getLateralDetails().isEmpty() ){
				List<ApplicantLateralDetailsTO> lateralDetails= new ArrayList<ApplicantLateralDetailsTO>();
				for(int i=0;i<CMSConstants.MAX_CANDIDATE_LATERALS;i++){
					ApplicantLateralDetailsTO to= new ApplicantLateralDetailsTO();
					to.setSemesterNo(i);
					lateralDetails.add(to);
				}
				Collections.sort(lateralDetails);
				admForm.setLateralDetails(lateralDetails);
			}else{
				if(admForm.getApplicantDetails().getLateralDetails()!=null && !admForm.getApplicantDetails().getLateralDetails().isEmpty()){

					Iterator<ApplicantLateralDetailsTO> latItr=admForm.getApplicantDetails().getLateralDetails().iterator();
					while (latItr.hasNext()) {
						ApplicantLateralDetailsTO lateralTO = (ApplicantLateralDetailsTO) latItr.next();
						if(lateralTO!=null){
							//COMMON DETAILS SET FROM FORM
							if(lateralTO.getInstituteAddress()!=null && !StringUtils.isEmpty(lateralTO.getInstituteAddress()))
							admForm.setLateralInstituteAddress(lateralTO.getInstituteAddress());
							if(lateralTO.getStateName()!=null && !StringUtils.isEmpty(lateralTO.getStateName()))
							admForm.setLateralStateName(lateralTO.getStateName());
							if(lateralTO.getUniversityName()!=null && !StringUtils.isEmpty(lateralTO.getUniversityName()))
							admForm.setLateralUniversityName(lateralTO.getUniversityName());
						}
						
					}
					Collections.sort(admForm.getApplicantDetails().getLateralDetails());
					admForm.setLateralDetails(admForm.getApplicantDetails().getLateralDetails());
				}
			}
		} catch (Exception e) {
			log.error("error in initlateralEntryEditPage...",e);
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
		log.info("exit initlateralEntryEditPage...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_LATERAL_SEMESTEREDIT_PAGE_NEW);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitLateralEntryEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitLateralEntryEdit...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		try {
			 ActionErrors errors = admForm.validate(mapping, request);
			//Validate marks
			validateLateralMarks(admForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				
				return mapping.findForward(CMSConstants.ADMISSIONFORM_LATERAL_SEMESTEREDIT_PAGE);
			}
			// process lateral details
			List<ApplicantLateralDetailsTO> lateralDetails=admForm.getLateralDetails();
			if(lateralDetails!=null && !lateralDetails.isEmpty()){
				admForm.getApplicantDetails().setLateralDetails(lateralDetails);
				Set<ApplicantLateralDetails> lateralBos= new HashSet<ApplicantLateralDetails>();
				Iterator<ApplicantLateralDetailsTO> latItr=lateralDetails.iterator();
				while (latItr.hasNext()) {
					ApplicantLateralDetailsTO lateralTO = (ApplicantLateralDetailsTO) latItr.next();
					if(lateralTO!=null){
						ApplicantLateralDetails bo= new ApplicantLateralDetails();
						bo.setId(lateralTO.getId());
						if(lateralTO.getAdmApplnId()!=0)
						{
							AdmAppln app= new AdmAppln();
							app.setId(lateralTO.getAdmApplnId());
							bo.setAdmAppln(app);
						}
						bo.setInstituteAddress(admForm.getLateralInstituteAddress());
						if(lateralTO.getMarksObtained()!=null && !StringUtils.isEmpty(lateralTO.getMarksObtained().trim()) && CommonUtil.isValidDecimal(lateralTO.getMarksObtained().trim()))
						bo.setMarksObtained(new BigDecimal(lateralTO.getMarksObtained().trim()));
						if(lateralTO.getMaxMarks()!=null && !StringUtils.isEmpty(lateralTO.getMaxMarks().trim()) && CommonUtil.isValidDecimal(lateralTO.getMaxMarks().trim()))
						bo.setMaxMarks(new BigDecimal(lateralTO.getMaxMarks().trim()));
						if(lateralTO.getMinMarks()!=null && !StringUtils.isEmpty(lateralTO.getMinMarks().trim()) && CommonUtil.isValidDecimal(lateralTO.getMinMarks().trim()))
							bo.setMinMarks(new BigDecimal(lateralTO.getMinMarks().trim()));
						if(lateralTO.getMonthPass()!=null && !StringUtils.isEmpty(lateralTO.getMonthPass().trim()) && StringUtils.isNumeric(lateralTO.getMonthPass().trim()))
							bo.setMonthPass(Integer.parseInt(lateralTO.getMonthPass().trim()));
						
						if(lateralTO.getYearPass()!=null && !StringUtils.isEmpty(lateralTO.getYearPass().trim()) && StringUtils.isNumeric(lateralTO.getYearPass().trim()))
							bo.setYearPass(Integer.parseInt(lateralTO.getYearPass().trim()));
						bo.setSemesterName(lateralTO.getSemesterName());
						bo.setSemesterNo(lateralTO.getSemesterNo());
						//COMMON DETAILS SET FROM FORM
						bo.setStateName(admForm.getLateralStateName());
						bo.setUniversityName(admForm.getLateralUniversityName());
						lateralBos.add(bo);
					}
					
				}
				
				if(lateralBos!=null && !lateralBos.isEmpty()){
					admForm.getApplicantDetails().setLateralDetailBos(lateralBos);
				}
			}
		} catch (Exception e) {
			log.error("error in submitLateralEntryEdit...",e);
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
		log.info("exit submitLateralEntryEdit...");
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE);
	}

	/**
	 * validate lateral marks
	 * @param admForm
	 * @param errors
	 */
	private void validateLateralMarks(ApplicationEditForm admForm,
			ActionErrors errors) {
		log.info("enter validateLateralMarks...");
		List<ApplicantLateralDetailsTO> lateralDetails=admForm.getLateralDetails();
		if(lateralDetails!=null && !lateralDetails.isEmpty()){
			Iterator<ApplicantLateralDetailsTO> latItr=lateralDetails.iterator();
			while (latItr.hasNext()) {
				ApplicantLateralDetailsTO lateralTO = (ApplicantLateralDetailsTO) latItr.next();
				if(lateralTO!=null){
					if(lateralTO.getMarksObtained()!=null && !StringUtils.isEmpty(lateralTO.getMarksObtained().trim())){
						if(!CommonUtil.isValidDecimal(lateralTO.getMarksObtained())){
							//error in valid mark obtain
							if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID).hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID);
								errors.add(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID, error);
							}
						}
					}
					if(lateralTO.getMaxMarks()!=null && !StringUtils.isEmpty(lateralTO.getMaxMarks().trim())){
						 if(!CommonUtil.isValidDecimal(lateralTO.getMaxMarks())){
								//error in valid max mark
							 if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID).hasNext()) {
									ActionMessage error = new ActionMessage(
											CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID);
									errors.add(CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID, error);
								}
							 }
					}
					if(lateralTO.getMinMarks()!=null && !StringUtils.isEmpty(lateralTO.getMinMarks().trim())){
						 if(!CommonUtil.isValidDecimal(lateralTO.getMinMarks())){
								//error in valid min mark
							 if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID).hasNext()) {
									ActionMessage error = new ActionMessage(
											CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID);
									errors.add(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID, error);
								}
							 }
					}
					if(lateralTO.getMarksObtained()!=null && !StringUtils.isEmpty(lateralTO.getMarksObtained().trim()) && lateralTO.getMaxMarks()!=null && !StringUtils.isEmpty(lateralTO.getMaxMarks().trim()))
					{
						
						
						if(CommonUtil.isValidDecimal(lateralTO.getMarksObtained())&& CommonUtil.isValidDecimal(lateralTO.getMaxMarks())){
							if(Double.parseDouble(lateralTO.getMarksObtained())> Double.parseDouble(lateralTO.getMaxMarks())){
								//error mark obtain large than max mark
								 if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER).hasNext()) {
										ActionMessage error = new ActionMessage(
												CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER);
										errors.add(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER, error);
									}
							}
						}
						
					}
					
					if(lateralTO.getMinMarks()!=null && !StringUtils.isEmpty(lateralTO.getMinMarks().trim()) && lateralTO.getMaxMarks()!=null && !StringUtils.isEmpty(lateralTO.getMaxMarks().trim()))
					{
						
						
						if(CommonUtil.isValidDecimal(lateralTO.getMinMarks())&& CommonUtil.isValidDecimal(lateralTO.getMaxMarks())){
							if(Double.parseDouble(lateralTO.getMinMarks())> Double.parseDouble(lateralTO.getMaxMarks())){
								//error min mark large than max mark
								 if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER).hasNext()) {
										ActionMessage error = new ActionMessage(
												CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER);
										errors.add(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER, error);
									}
							}
						}
						
					}
					
					if(lateralTO.getYearPass()!=null && !StringUtils.isEmpty(lateralTO.getYearPass()) && StringUtils.isNumeric(lateralTO.getYearPass())){
						if(CommonUtil.isFutureYear(Integer.parseInt(lateralTO.getYearPass()))){
							if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE).hasNext()) {
								ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE);
								errors.add(CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE, error);
							}
						}
					}
					
					if(lateralTO.getMonthPass()!=null && !StringUtils.isEmpty(lateralTO.getMonthPass()) && StringUtils.isNumeric(lateralTO.getMonthPass())){
						if(lateralTO.getYearPass()!=null && !StringUtils.isEmpty(lateralTO.getYearPass()) && StringUtils.isNumeric(lateralTO.getYearPass())){
							if(!CommonUtil.isPastYear(Integer.parseInt(lateralTO.getYearPass()))){
						
								if(CommonUtil.isFutureMonth(Integer.parseInt(lateralTO.getMonthPass())-1)){
									if (errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE).hasNext()) {
										ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE);
										errors.add(CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE, error);
									}
								}
							}
						}
					}
				}
			}
		}
		
		
		log.info("exit validateLateralMarks...");
	}
	/**
	 * @param admForm
	 * @throws Exception
	 */
	private void setDataToInitForm(ApplicationEditForm admForm)throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
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
	
	
	//raghu added newly
	public ActionForward getPreferences(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ApplicationEditForm admForm=(ApplicationEditForm)form;
		ApplicationEditHandler formhandler = ApplicationEditHandler.getInstance();
		formhandler.getPreference(admForm,admForm.getProgramTypeId());
		return mapping.findForward(CMSConstants.ADMISSIONFORM_EDITPREFERENCE_PAGE);
	}
	
	public ActionForward savePreferences(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = admForm.validate(mapping, request);
		setUserId(request,admForm);
		if(errors.isEmpty()){
			try{
				List<CourseTO> courselist= admForm.getPrefcourses(); 
				if (courselist != null) {
					Iterator<CourseTO> qualItr=courselist.iterator();
					while (qualItr.hasNext()) {
						CourseTO qualTO = (CourseTO) qualItr.next();
						CandidatePreferenceTO candidatePreferenceTO=new CandidatePreferenceTO();
						if(qualTO.getId()!=0){
							//qualTO.setCoursId(String.valueOf(qualTO.getId()));
							qualTO.setPrefNo(qualTO.getPrefNo());
						}
					}
				}
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
	}
	
	


	
	
	
	private ActionMessages validateMarksFor12thClass(CandidateMarkTO detailmark,ActionMessages errors){
		log.info("enter validateMarks...");
		//ActionMessages errors= new ActionMessages();
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

	
	public ActionForward initDetailMarkConfirmPage12(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		try {
			
			String indexString = request.getParameter("editcountID");
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute("editcountID", indexString);
				}else
					session.removeAttribute("editcountID");
			}
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			List<AdmSubjectMarkForRankTO> admsubList= new ArrayList<AdmSubjectMarkForRankTO>();
			if( admForm.getAdmsubMarkList()!=null && admForm.getAdmsubMarkList().size()!=0){
				//admForm.setAdmsubMarkList(admsubList);
				List<AdmSubjectMarkForRankTO> admSubList=new ArrayList<AdmSubjectMarkForRankTO>();
				List<AdmSubjectMarkForRankTO> subList=admForm.getAdmsubMarkList();
				if(subList!=null){
					Iterator<AdmSubjectMarkForRankTO> iterator=subList.iterator();
					while(iterator.hasNext()){
						AdmSubjectMarkForRankTO admSubjectMarkForRankTO=(AdmSubjectMarkForRankTO) iterator.next();
						if(admSubjectMarkForRankTO.getSubid()!=null)
						admSubjectMarkForRankTO.setSubid(admSubjectMarkForRankTO.getSubid());
						if(admSubjectMarkForRankTO.getMaxmark()!=null)
						admSubjectMarkForRankTO.setMaxmark(admSubjectMarkForRankTO.getMaxmark());
						if(admSubjectMarkForRankTO.getObtainedmark()!=null)
						admSubjectMarkForRankTO.setObtainedmark(admSubjectMarkForRankTO.getObtainedmark());
						admSubList.add(admSubjectMarkForRankTO);
					}
				}
				admForm.setAdmsubMarkList(admSubList);
			}
			else{
				for(int i=1;i<7;i++){
					AdmSubjectMarkForRankTO admSubjectMarkForRankTO=new AdmSubjectMarkForRankTO();
					
					admsubList.add(admSubjectMarkForRankTO);
				}
				admForm.setAdmsubMarkList(admsubList);
			}
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if (qualTO.getCountId()==index) {
								if (qualTO.getDetailmark() != null)
									admForm.setDetailMark(qualTO
											.getDetailmark());
							}
							
							if(qualTO.getDocName().equalsIgnoreCase("Class 12")){
								String language="Language";
								Map<Integer,String> admsubjectMap=ApplicationEditHandler.getInstance().get12thclassSubjects(qualTO.getDocName(),language);
								admForm.setAdmSubjectMap(admsubjectMap);
								Map<Integer,String> admsubjectLangMap=ApplicationEditHandler.getInstance().get12thclassLangSubjects(qualTO.getDocName(),language);
								admForm.setAdmSubjectLangMap(admsubjectLangMap);
							}
						}
			}
			
			Map<Integer,String> subjectMap = AdmissionFormHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);
			
			
			
			if(admForm.getDetailMark()==null){
				CandidateMarkTO markTo=new CandidateMarkTO();
				AdmissionFormHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
				admForm.setDetailMark(markTo);
			}
		
		} catch (Exception e) {
			log.error("error initDetailMarkConfirmPage...",e);
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
		log.info("exit initDetailMarkConfirmPage...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH_FOREDIT);
		//return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
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
	
	public ActionForward submitDetailMarkConfirmfor12th(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitDetailMarkConfirm page...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute("editcountID");
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			
			CandidateMarkTO detailmark = admForm.getDetailMark();
			
			Map<Integer,String> admlangmap=admForm.getAdmSubjectLangMap();
			Map<Integer,String> admsubmap=admForm.getAdmSubjectMap();
			List<AdmSubjectMarkForRankTO>  admsubList=admForm.getAdmsubMarkList();
			
			Iterator<AdmSubjectMarkForRankTO> itr=admsubList.iterator();
			while(itr.hasNext()){
				AdmSubjectMarkForRankTO admSubjectMarkForRankTO=(AdmSubjectMarkForRankTO) itr.next();
				
				if(detailmark.getSubject1()==null){
					
					if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
						
						if(admlangmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject1(s);
						}else if(admsubmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject1(s);
						}
						
						
					}
					
					if(admSubjectMarkForRankTO.getMaxmark()!=null && !admSubjectMarkForRankTO.getMaxmark().equalsIgnoreCase("")){
						detailmark.setSubject1TotalMarks(admSubjectMarkForRankTO.getMaxmark());
					}
					
					if(admSubjectMarkForRankTO.getObtainedmark()!=null && !admSubjectMarkForRankTO.getObtainedmark().equalsIgnoreCase("")){
						detailmark.setSubject1ObtainedMarks(admSubjectMarkForRankTO.getObtainedmark());
					}
					
					
					
					
				
				}//sub1 over
				
				else if(detailmark.getSubject2()==null){
					
					if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
						
						if(admlangmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject2(s);
						}else if(admsubmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject2(s);
						}
						
						
					}
					
					if(admSubjectMarkForRankTO.getMaxmark()!=null && !admSubjectMarkForRankTO.getMaxmark().equalsIgnoreCase("")){
						detailmark.setSubject2TotalMarks(admSubjectMarkForRankTO.getMaxmark());
					}
					
					if(admSubjectMarkForRankTO.getObtainedmark()!=null && !admSubjectMarkForRankTO.getObtainedmark().equalsIgnoreCase("")){
						detailmark.setSubject2ObtainedMarks(admSubjectMarkForRankTO.getObtainedmark());
					}
					
					
					
					
				
				}//sub1 over
				
				else if(detailmark.getSubject3()==null){
					
					if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
						
						if(admlangmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject3(s);
						}else if(admsubmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject3(s);
						}
						
						
					}
					
					if(admSubjectMarkForRankTO.getMaxmark()!=null && !admSubjectMarkForRankTO.getMaxmark().equalsIgnoreCase("")){
						detailmark.setSubject3TotalMarks(admSubjectMarkForRankTO.getMaxmark());
					}
					
					if(admSubjectMarkForRankTO.getObtainedmark()!=null && !admSubjectMarkForRankTO.getObtainedmark().equalsIgnoreCase("")){
						detailmark.setSubject3ObtainedMarks(admSubjectMarkForRankTO.getObtainedmark());
					}
					
					
					
					
				
				}//sub1 over
				

				else if(detailmark.getSubject4()==null){
					
					if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
						
						if(admlangmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject4(s);
						}else if(admsubmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject4(s);
						}
						
						
					}
					
					if(admSubjectMarkForRankTO.getMaxmark()!=null && !admSubjectMarkForRankTO.getMaxmark().equalsIgnoreCase("")){
						detailmark.setSubject4TotalMarks(admSubjectMarkForRankTO.getMaxmark());
					}
					
					if(admSubjectMarkForRankTO.getObtainedmark()!=null && !admSubjectMarkForRankTO.getObtainedmark().equalsIgnoreCase("")){
						detailmark.setSubject4ObtainedMarks(admSubjectMarkForRankTO.getObtainedmark());
					}
					
					
					
					
				
				}//sub1 over
				

				else if(detailmark.getSubject5()==null){
					
					if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
						
						if(admlangmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject5(s);
						}else if(admsubmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject5(s);
						}
						
						
					}
					
					if(admSubjectMarkForRankTO.getMaxmark()!=null && !admSubjectMarkForRankTO.getMaxmark().equalsIgnoreCase("")){
						detailmark.setSubject5TotalMarks(admSubjectMarkForRankTO.getMaxmark());
					}
					
					if(admSubjectMarkForRankTO.getObtainedmark()!=null && !admSubjectMarkForRankTO.getObtainedmark().equalsIgnoreCase("")){
						detailmark.setSubject5ObtainedMarks(admSubjectMarkForRankTO.getObtainedmark());
					}
					
					
					
					
				
				}//sub1 over
				

				else if(detailmark.getSubject6()==null){
					
					if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
						
						if(admlangmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject6(s);
						}else if(admsubmap.containsKey(Integer.parseInt(admSubjectMarkForRankTO.getSubid()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
							detailmark.setSubject6(s);
						}
						
						
					}
					
					if(admSubjectMarkForRankTO.getMaxmark()!=null && !admSubjectMarkForRankTO.getMaxmark().equalsIgnoreCase("")){
						detailmark.setSubject6TotalMarks(admSubjectMarkForRankTO.getMaxmark());
					}
					
					if(admSubjectMarkForRankTO.getObtainedmark()!=null && !admSubjectMarkForRankTO.getObtainedmark().equalsIgnoreCase("")){
						detailmark.setSubject6ObtainedMarks(admSubjectMarkForRankTO.getObtainedmark());
					}
					
					
					
					
				
				}//sub1 over
				
				
				
			}// total while close
			
			
			
			if(admForm.getTotalmaxmark()!=null){
				detailmark.setTotalMarks(admForm.getTotalmaxmark());
			}
			if(admForm.getTotalobtainedmark()!=null){
				detailmark.setTotalObtainedMarks(admForm.getTotalobtainedmark());
			}
			
			
			
			
			
			
			ActionMessages errors = validateMarksFor12thClass(detailmark,new ActionMessages());
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH);
				else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
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
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("enter ssubmitDetailMarkConfirm page...");
		
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
			
	}
	
	
	
	
	
	
	//at
	public ActionForward initStudentIndexMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ApplicationEditForm admForm=(ApplicationEditForm) form;
		admForm.setProgramId("");
		admForm.setProgramTypeId("");
		admForm.setCourseId("");
		try {
			setUserId(request, admForm);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admForm.setProgramTypeList(programTypeList);
		} catch (ApplicationException e) {
			log.error("error in initStudentEdit...", e);
			String msg = super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INDEXMARK);
	}
	
	//at
	public ActionForward initStudentRank(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ApplicationEditForm admForm=(ApplicationEditForm) form;
		admForm.setProgramId("");
		admForm.setProgramTypeId("");
		admForm.setCourseId("");
		try {
			setUserId(request, admForm);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admForm.setProgramTypeList(programTypeList);
		} catch (ApplicationException e) {
			log.error("error in initStudentEdit...", e);
			String msg = super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_RANK);
	}
	
	
	//at
	public ActionForward initStudentCourseAllotment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ApplicationEditForm admForm=(ApplicationEditForm) form;
		admForm.setProgramId("");
		admForm.setProgramTypeId("");
		admForm.setCourseId("");
		try {
			setUserId(request, admForm);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admForm.setProgramTypeList(programTypeList);
		} catch (ApplicationException e) {
			log.error("error in initStudentEdit...", e);
			String msg = super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSE_ALLOTEMENT);
	}
	
	
	
	//at
	public ActionForward initAssignCoursesToStudent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ApplicationEditForm admForm=(ApplicationEditForm) form;
		admForm.setProgramId("");
		admForm.setProgramTypeId("");
		admForm.setCourseId("");
		admForm.setStudentList(null);
		try {
			setUserId(request, admForm);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admForm.setProgramTypeList(programTypeList);
		} catch (ApplicationException e) {
			log.error("error in initStudentEdit...", e);
			String msg = super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT);
	}
	
	

	//at
	public ActionForward calculateIndexMark(ActionMapping mapping,
	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		ApplicationEditForm admForm=(ApplicationEditForm) form;
		ActionMessages errors=admForm.validate(mapping, request);
		IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
		
		if (errors.isEmpty()) {
		try{
			List marklist=txn.getIndexMarksOnCourse(admForm);
			
			if(marklist.size()==0){
				List<AdmAppln> applicantDetails = ApplicationEditHandler.getInstance().getApplicantDetail(admForm);
				boolean iscreated=ApplicationEditHandler.getInstance().calculateIndexMark(applicantDetails,admForm);
			
				if(!iscreated){
					errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
					
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ADMISSIONFORM_INDEXMARK);
				}else{/*
					
					List groupmarklist=txn.getGroupMarksOnCourse(admForm);
					if(groupmarklist.size()==0){	
					ApplicationEditHandler.getInstance().calculateGroupMarks(admForm);
					}
				*/}
			}else{
				errors.add("admissionFormForm.indexmarks",new ActionError("admissionFormForm.indexmarks"));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_INDEXMARK);
			}
			}
		catch(Exception exception){
			exception.printStackTrace();
			errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_INDEXMARK);
		}
		}else{
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INDEXMARK);
		}
 		return mapping.findForward(CMSConstants.ADMISSIONFORM_INDEXMARK_CONFIRM_PAGE_NEW);
	}
	
	//at
	public ActionForward calculateRank(ActionMapping mapping,
	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		ApplicationEditForm admForm=(ApplicationEditForm) form;
		ActionMessages errors=admForm.validate(mapping, request);
		if (errors.isEmpty()) {
		try{
			
			IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
			List marklist=txn.getRanksOnCourse(admForm);
			
			if(marklist.size()==0){
			
			boolean iscreated=ApplicationEditHandler.getInstance().calculateRank(admForm);
			if(!iscreated){
				errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_RANK);
			}
			}else{
				
				errors.add("admissionFormForm.rank",new ActionError("admissionFormForm.rank"));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_RANK);
			}
		}
		catch(Exception exception){
			System.out.println("action  ======================================"+exception);
			errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
			
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_RANK);
		}
		}
	else{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_RANK);
		}
 		return mapping.findForward(CMSConstants.ADMISSIONFORM_RANK_CONFIRM_PAGE_NEW);
	}
	
	
	
	//at
	public ActionForward generateCourseAllotment(ActionMapping mapping,
	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		ApplicationEditForm admForm=(ApplicationEditForm) form;
		ActionMessages errors=admForm.validate(mapping, request);
		admForm.setAllotedNo(0);
		if (errors.isEmpty()) {
			
		try{
			IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
			List rankDetails = txn.getAllotedStudentsOnCourse(admForm);
			if(rankDetails.size()==0){
			
			//allotting courses for all people based on student highest index mark priority
			//boolean iscreated=ApplicationEditHandler.getInstance().generateCourseAllotmentOnIndexMark(admForm);
			//allotting courses for all people based on preferences priority
			//boolean iscreated=ApplicationEditHandler.getInstance().generateCourseAllotmentOnPreference(admForm);
			//allotting courses for all people based on Rank and Preference
			boolean iscreated=ApplicationEditHandler.getInstance().generateCourseAllotmentOnRankPreference(admForm);
			
			
			/*if(!iscreated){
				errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSE_ALLOTEMENT);
			}*/
				
			}else{
				txn=ApplicationEditTransactionimpl.getInstance();
				Integer allotedNo= txn.getAllotedNoOnCourse(admForm);
				admForm.setAllotedNo(allotedNo);
				
				errors.add("admissionFormForm.coursealloted",new ActionError("admissionFormForm.already.coursealloted",allotedNo));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSEALLOTMENT_MULTIPLE_PAGE);
			}
			}
		catch(Exception exception){
			System.out.println("action  ======================================"+exception);
			errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
			
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSE_ALLOTEMENT);
		}
		}else{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSE_ALLOTEMENT);
		}
 		return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSEALLOTMENT_CONFIRM_PAGE_NEW);
	}
	
	
	
	
	
	//at
	public ActionForward assignCoursesToStudent(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		
	
		ApplicationEditForm admForm=(ApplicationEditForm) form;
		
		
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		
		errors = admForm.validate(mapping, request);
		validateAssignCourse(admForm,errors);
		
		if (errors.isEmpty()) {
				
			try{
				
				
				//setting user id to form.
				setUserId(request, admForm);				
				boolean isAdded=ApplicationEditHandler.getInstance().assignStudentsToCourse(admForm);
				
				if(!isAdded){
					errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
					
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT);
				}
				
			}catch (Exception e) {
				System.out.println("action  ======================================"+e);
				errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT);
			}
				
			
		} else{
			
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT);
		
		}
		
		
		
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSEALLOTMENT_CONFIRM_PAGE_NEW);
	}
	
	
	
	private void validateAssignCourse(ApplicationEditForm admForm, ActionErrors errors) throws Exception{
		boolean isChecked=false;
		List<StudentCourseAllotmentTo> list = admForm.getStudentList();
		
		if (list != null && !list.isEmpty()) {
			
				Iterator<StudentCourseAllotmentTo> itr = list.iterator();
			
				int count = 0;
				
				while (itr.hasNext()) {
					
					StudentCourseAllotmentTo  allotmentTo = (StudentCourseAllotmentTo) itr.next();
				
				if (allotmentTo.getChecked()!=null && allotmentTo.getChecked().equalsIgnoreCase("on")) {
					isChecked=true;
					break;
				}
				
				}
		
				if(!isChecked){
					errors.add(CMSConstants.ERROR,new ActionError("interviewProcessForm.checkbox.select"));
				}
				
		}
		
	}
	
	

	
	
	
	
	// getting students for allotting course
	public ActionForward getStudentsForCourseAssignment(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	 
 	   ApplicationEditForm admForm = (ApplicationEditForm)form;
 	  
		ActionMessages errors=admForm.validate(mapping, request);
		if (errors.isEmpty()) {
		
		try 
		{
			admForm.setStudentList(null);
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
			
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT);
			}
			
			
			
			IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
			List rankDetails = txn.getAllotedStudentsOnCourseCheck(admForm);
			if(rankDetails!=null && rankDetails.size()==0){
				//boolean iscreated=ApplicationEditHandler.getInstance().generateCourseAllotment(admForm);
				admForm.setAllotedNo(0);
			}else{
				
				txn=ApplicationEditTransactionimpl.getInstance();
				Integer allotedNo= txn.getAllotedNoOnCourse(admForm);
				admForm.setAllotedNo(allotedNo);
				errors.add("admissionFormForm.already.courseassigned",new ActionError("admissionFormForm.already.courseassigned",admForm.getAllotedNo()));
				saveErrors(request, errors);
				admForm.setStudentList(null);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT_MULTIPLE);
			}
			
		List<StudentCourseAllotmentTo> studentList=ApplicationEditHandler.getInstance().getStudentDetails(admForm);
		
		System.out.println(studentList.size());
		if(studentList.size()==0 || studentList.isEmpty()){
		
			errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
			saveErrors(request, errors);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admForm.setProgramTypeList(programTypeList);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT);
		}
		
		admForm.setStudentList(studentList);
		
		}
		catch(Exception exception)
		{
			String msg = super.handleApplicationException(exception);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT);
	}
	
	
	
	
	
	// getting students for allotting course
	public ActionForward getStudentsForCourseAssignmentMultiple(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	 
 	   ApplicationEditForm admForm = (ApplicationEditForm)form;
 	  
		ActionMessages errors=admForm.validate(mapping, request);
		if (errors.isEmpty()) {
		
		try 
		{
			admForm.setStudentList(null);
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
			
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT);
			}
			
			
		List<StudentCourseAllotmentTo> studentList=ApplicationEditHandler.getInstance().getStudentDetailsMultipleTime(admForm);
		
		System.out.println(studentList.size());
		if(studentList.size()==0 || studentList.isEmpty()){
		
			errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
			saveErrors(request, errors);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admForm.setProgramTypeList(programTypeList);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT);
		}
		
		admForm.setStudentList(studentList);
		
		}
		catch(Exception exception)
		{
			String msg = super.handleApplicationException(exception);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT);
	}
	
	
	
	
	//at
	public ActionForward generateCourseAllotmentMultipleTimes(ActionMapping mapping,
	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		ApplicationEditForm admForm=(ApplicationEditForm) form;
		ActionMessages errors=admForm.validate(mapping, request);
		
			
		try{
			
			//allotting courses for all people based on student highest index mark priority
			//boolean iscreated=ApplicationEditHandler.getInstance().generateCourseAllotmentOnIndexMarkMultipleTimes(admForm);
			//allotting courses for all people based on preferences priority
			//boolean iscreated=ApplicationEditHandler.getInstance().generateCourseAllotmentOnPreference(admForm);
			//allotting courses for all people based on rank and preferenceno
			boolean iscreated=ApplicationEditHandler.getInstance().generateCourseAllotmentOnRankPreferenceMultipleTimes(admForm);
			
			/*if(!iscreated){
				errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSE_ALLOTEMENT);
			}*/
				
			
			}
		catch(Exception exception){
			System.out.println(admForm.getAllotedNo()+"action multiple ======================================"+exception);
			errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
			
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSE_ALLOTEMENT);
		}
		
 		return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSEALLOTMENT_CONFIRM_PAGE_NEW);
 		
	}
	
	
	
	//at
	public ActionForward initAssignStudentsToEntranceExam(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ApplicationEditForm admForm=(ApplicationEditForm) form;
		admForm.setProgramId("");
		admForm.setProgramTypeId("");
		admForm.setCourseId("");
		admForm.setStudentList(null);
		try {
			setUserId(request, admForm);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admForm.setProgramTypeList(programTypeList);
		} catch (ApplicationException e) {
			log.error("error in initStudentEdit...", e);
			String msg = super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMALLOTEMENT);
	}
	

	
	
	// getting students for allotting course
	public ActionForward getStudentsForExamAssignment(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	 
 	   ApplicationEditForm admForm = (ApplicationEditForm)form;
 	  
		ActionMessages errors=admForm.validate(mapping, request);
		if (errors.isEmpty()) {
		
		try 
		{
			admForm.setStudentList(null);
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
			
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMALLOTEMENT);
			}
			
			
			
			
			
		List<StudentCourseAllotmentTo> studentList=ApplicationEditHandler.getInstance().getStudentDetailsForExam(admForm);
		
		System.out.println(studentList.size());
		if(studentList.size()==0 || studentList.isEmpty()){
		
			errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
			saveErrors(request, errors);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admForm.setProgramTypeList(programTypeList);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMALLOTEMENT);
		}
		
		admForm.setStudentList(studentList);
		
		}
		catch(Exception exception)
		{
			String msg = super.handleApplicationException(exception);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMALLOTEMENT);
	}
	
	
	

	
	
	
	//at
	public ActionForward assignStudentsToEntranceExam(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		
	
		ApplicationEditForm admForm=(ApplicationEditForm) form;
		
		
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		
		errors = admForm.validate(mapping, request);
		validateAssignCourse(admForm,errors);
		
		if (errors.isEmpty()) {
				
			try{
				
				
				//setting user id to form.
				setUserId(request, admForm);				
				boolean isAdded=ApplicationEditHandler.getInstance().assignStudentsForExam(admForm);
				
				if(!isAdded){
					errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
					
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMALLOTEMENT);
				}
				
			}catch (Exception e) {
				System.out.println("action  ======================================"+e);
				errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMALLOTEMENT);
			}
				
			
		} else{
			
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMALLOTEMENT);
		
		}
		
		
		
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMALLOTEMENT_CONFIRM_PAGE_NEW);
	}
	
	
	
	
		
	//at
	public ActionForward initAssignStudentsToEntranceExamMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ApplicationEditForm admForm=(ApplicationEditForm) form;
		admForm.setProgramId("");
		admForm.setProgramTypeId("");
		admForm.setCourseId("");
		admForm.setPreferenceEntranceDetailsList(null);
		try {
			setUserId(request, admForm);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admForm.setProgramTypeList(programTypeList);
		} catch (ApplicationException e) {
			log.error("error in initStudentEdit...", e);
			String msg = super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMMARKSALLOTEMENT);
	}
	

	
	
	// getting students for allotting course
	public ActionForward getStudentsForExamMarksAssignment(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	 
 	   ApplicationEditForm admForm = (ApplicationEditForm)form;
 	  
		ActionMessages errors=admForm.validate(mapping, request);
		if (errors.isEmpty()) {
		
		try 
		{
			admForm.setPreferenceEntranceDetailsList(null);
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
			
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMMARKSALLOTEMENT);
			}
			
			
			
			
		List<CandidatePreferenceEntranceDetailsTO> studentList=ApplicationEditHandler.getInstance().getStudentDetailsForExamMarks(admForm);
		
		System.out.println(studentList.size());
		if(studentList.size()==0 || studentList.isEmpty()){
		
			errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
			saveErrors(request, errors);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admForm.setProgramTypeList(programTypeList);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMMARKSALLOTEMENT);
		}
		
		admForm.setPreferenceEntranceDetailsList(studentList);
		
		}
		catch(Exception exception)
		{
			String msg = super.handleApplicationException(exception);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMMARKSALLOTEMENT);
	}
	
	
	

	
	
	
	//at
	public ActionForward assignStudentsToEntranceExamMarks(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		
	
		ApplicationEditForm admForm=(ApplicationEditForm) form;
		
		
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		
		errors = admForm.validate(mapping, request);
		validateAssignMarks(admForm,errors);
		
		if (errors.isEmpty()) {
				
			try{
				
				
				//setting user id to form.
				setUserId(request, admForm);				
				boolean isAdded=ApplicationEditHandler.getInstance().assignStudentsForExamMarks(admForm);
				
				if(!isAdded){
					errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
					
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMMARKSALLOTEMENT);
				}
				
			}catch (Exception e) {
				System.out.println("action  ======================================"+e);
				errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMMARKSALLOTEMENT);
			}
				
			
		} else{
			
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMMARKSALLOTEMENT);
		
		}
		
		
		
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ASSIGNSTUDENTS_EXAMMARKSALLOTEMENT_CONFIRM_PAGE_NEW);
	}
	
	
	
	private void validateAssignMarks(ApplicationEditForm admForm, ActionErrors errors) throws Exception{
		boolean isChecked=false;
		List<CandidatePreferenceEntranceDetailsTO> list = admForm.getPreferenceEntranceDetailsList();
		
		if (list != null && !list.isEmpty()) {
			
				Iterator<CandidatePreferenceEntranceDetailsTO> itr = list.iterator();
			
				int count = 0;
				
				while (itr.hasNext()) {
					
					CandidatePreferenceEntranceDetailsTO  allotmentTo = (CandidatePreferenceEntranceDetailsTO) itr.next();
				
				if (allotmentTo.getMarksObtained()!=null && !allotmentTo.getMarksObtained().equalsIgnoreCase("")) {
					isChecked=splCharValidation(allotmentTo.getMarksObtained());
					if(!isChecked)
					break;
				}
				
				if (allotmentTo.getTotalMarks()!=null && !allotmentTo.getTotalMarks().equalsIgnoreCase("")) {
					isChecked=splCharValidation(allotmentTo.getTotalMarks());
					if(!isChecked)
					break;
				}
				
				if(isChecked)
				if (allotmentTo.getMarksObtained()!=null && !allotmentTo.getMarksObtained().equalsIgnoreCase("") && allotmentTo.getTotalMarks()!=null && !allotmentTo.getTotalMarks().equalsIgnoreCase("")) {

					if(Double.parseDouble(allotmentTo.getMarksObtained())<=Double.parseDouble(allotmentTo.getTotalMarks())){
						isChecked=true;
					}else{
						isChecked=false;
						break;
					}
						
					
				}
				
				}
		
				if(!isChecked){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.marks"));
				}
				
		}
		
	}
	
	
	
	
	private static boolean splCharValidation(String name) {
		boolean haveSplChar = false;
		//Pattern pattern = Pattern.compile("[^0-9]\\.+");
		//Pattern pattern = Pattern.compile("\\d{0,3}(\\.\\d{1,2})?");
	 
		//Matcher matcher = pattern.matcher(name);
		//haveSplChar = matcher.find();
		haveSplChar=name.matches("\\d{0,3}(\\.\\d{1,2})?");
		return haveSplChar;

	}
	
	
	
	public ActionForward submitDetailMarkConfirmfor12thForEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitDetailMarkConfirm page...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null){
				// getting ednqlfn id from session
				indexString = (String) session.getAttribute("editcountID");
			}
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			
         CandidateMarkTO detailmark = admForm.getDetailMark();
			
			Map<Integer,String> admlangmap=admForm.getAdmSubjectLangMap();
			Map<Integer,String> admsubmap=admForm.getAdmSubjectMap();
			Map<String,String> groupSubjectsMap=admForm.getGroupSubjectsMap();
			Map<String,String> groupLangSubjectsMap=admForm.getGroupLangSubjectsMap();
			List<AdmSubjectMarkForRankTO>  admsubList=admForm.getAdmsubMarkList();
			
			List<AdmSubjectMarkForRankTO> admSubList= new ArrayList<AdmSubjectMarkForRankTO>();
				
				//if(detailmark.getSubject1()==null){
					
					if(admForm.getSubid_0()!=null ){
						
						if(admForm.getSubid_0().equalsIgnoreCase(""))
						{
							detailmark.setSubject1("");
							detailmark.setSubject1ObtainedMarks("");
							detailmark.setSubject1TotalMarks("");
							
						}
						else
						{
						
						
						if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_0()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_0()));
							detailmark.setSubject1(s);
							String g=groupLangSubjectsMap.get(s);
							admForm.setAdmsubgrpname_0(g);
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_0()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_0()));
							detailmark.setSubject1(s);
							String g=groupSubjectsMap.get(s);
							admForm.setAdmsubgrpname_0(g);
						}
						
						
						
						}
						
					}else{
						detailmark.setSubject1("");
					}
					
					if(admForm.getMaxmark_0()!=null && !admForm.getMaxmark_0().equalsIgnoreCase("")){
						detailmark.setSubject1TotalMarks(admForm.getMaxmark_0());
					}else{
						detailmark.setSubject1TotalMarks(admForm.getMaxmark_0());
					}
					
					if(admForm.getObtainedmark_0()!=null && !admForm.getObtainedmark_0().equalsIgnoreCase("")){
						detailmark.setSubject1ObtainedMarks(admForm.getObtainedmark_0());
					}else{
						detailmark.setSubject1ObtainedMarks(admForm.getObtainedmark_0());
					}
					
					
					
					
				
				//}//sub 1 over
				
				//else if(detailmark.getSubject2()==null){
					
					if(admForm.getSubid_1()!=null ){
						if(admForm.getSubid_1().equalsIgnoreCase("")){
							detailmark.setSubject2("");
							detailmark.setSubject2ObtainedMarks("");
							detailmark.setSubject2TotalMarks("");
							
						}else{
						
						if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_1()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_1()));
							detailmark.setSubject2(s);
							String g=groupLangSubjectsMap.get(s);
							admForm.setAdmsubgrpname_1(g);
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_1()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_1()));
							detailmark.setSubject2(s);
							String g=groupSubjectsMap.get(s);
							admForm.setAdmsubgrpname_1(g);
						}
						
						}	
					}else{
						detailmark.setSubject2("");
					}
					
					if(admForm.getMaxmark_1()!=null && !admForm.getMaxmark_1().equalsIgnoreCase("")){
						detailmark.setSubject2TotalMarks(admForm.getMaxmark_1());
					}else{
						detailmark.setSubject2TotalMarks(admForm.getMaxmark_1());
					}
					
					if(admForm.getObtainedmark_1()!=null && !admForm.getObtainedmark_1().equalsIgnoreCase("")){
						detailmark.setSubject2ObtainedMarks(admForm.getObtainedmark_1());
					}else{
						detailmark.setSubject2ObtainedMarks(admForm.getObtainedmark_1());
					}
					
					
					
					
				
				//}//sub 2 over
				
				//else if(detailmark.getSubject3()==null){
					
					if(admForm.getSubid_2()!=null ){
						if(admForm.getSubid_2().equalsIgnoreCase("")){
							detailmark.setSubject3("");
							detailmark.setSubject3ObtainedMarks("");
							detailmark.setSubject3TotalMarks("");
							
						}else{
						
						if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_2()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_2()));
							detailmark.setSubject3(s);
							String g=groupLangSubjectsMap.get(s);
							admForm.setAdmsubgrpname_2(g);
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_2()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_2()));
							detailmark.setSubject3(s);
							String g=groupSubjectsMap.get(s);
							admForm.setAdmsubgrpname_2(g);
						}
						
					}
					}else{
						detailmark.setSubject3("");
					}
					
					if(admForm.getMaxmark_2()!=null && !admForm.getMaxmark_2().equalsIgnoreCase("")){
						detailmark.setSubject3TotalMarks(admForm.getMaxmark_2());
					}else{
						detailmark.setSubject3TotalMarks(admForm.getMaxmark_1());
					}
					
					if(admForm.getObtainedmark_2()!=null && !admForm.getObtainedmark_2().equalsIgnoreCase("")){
						detailmark.setSubject3ObtainedMarks(admForm.getObtainedmark_2());
					}else{
						detailmark.setSubject3ObtainedMarks(admForm.getObtainedmark_1());
					}
					
					
					
					
				
				//}//sub 3 over
				
				//else if(detailmark.getSubject4()==null){
					
					if(admForm.getSubid_3()!=null ){
						
						if(admForm.getSubid_3().equalsIgnoreCase("")){
							detailmark.setSubject4("");
							detailmark.setSubject4ObtainedMarks("");
							detailmark.setSubject4TotalMarks("");
							
						}else{
						
						
						if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_3()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_3()));
							detailmark.setSubject4(s);
							String g=groupLangSubjectsMap.get(s);
							admForm.setAdmsubgrpname_3(g);
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_3()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_3()));
							detailmark.setSubject4(s);
							String g=groupSubjectsMap.get(s);
							admForm.setAdmsubgrpname_3(g);
						}
						
					}
					}else{
						detailmark.setSubject4("");
					}
					
					if(admForm.getMaxmark_3()!=null && !admForm.getMaxmark_3().equalsIgnoreCase("")){
						detailmark.setSubject4TotalMarks(admForm.getMaxmark_3());
					}else{
						detailmark.setSubject4TotalMarks(admForm.getMaxmark_1());
					}
					
					if(admForm.getObtainedmark_3()!=null && !admForm.getObtainedmark_3().equalsIgnoreCase("")){
						detailmark.setSubject4ObtainedMarks(admForm.getObtainedmark_3());
					}else{
						detailmark.setSubject4ObtainedMarks(admForm.getObtainedmark_1());
					}
					
					
					
					
				
				//}//sub 4 over
				

				//else if(detailmark.getSubject5()==null){
					
					if(admForm.getSubid_4()!=null ){
						
						if(admForm.getSubid_4().equalsIgnoreCase("")){
							detailmark.setSubject5("");
							detailmark.setSubject5ObtainedMarks("");
							detailmark.setSubject5TotalMarks("");
							
						}else{
						
						
						if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_4()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_4()));
							detailmark.setSubject5(s);
							String g=groupLangSubjectsMap.get(s);
							admForm.setAdmsubgrpname_4(g);
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_4()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_4()));
							detailmark.setSubject5(s);
							String g=groupSubjectsMap.get(s);
							admForm.setAdmsubgrpname_4(g);
						}
						
						}
						
					}else{
						detailmark.setSubject5("");
					}
					
					if(admForm.getMaxmark_4()!=null && !admForm.getMaxmark_4().equalsIgnoreCase("")){
						detailmark.setSubject5TotalMarks(admForm.getMaxmark_4());
					}else{
						detailmark.setSubject5TotalMarks(admForm.getMaxmark_1());
					}
					
					if(admForm.getObtainedmark_4()!=null && !admForm.getObtainedmark_4().equalsIgnoreCase("")){
						detailmark.setSubject5ObtainedMarks(admForm.getObtainedmark_4());
					}else{
						detailmark.setSubject5ObtainedMarks(admForm.getObtainedmark_1());
					}
					
					
					
					
				
				//}//sub 5 over
				

				//else if(detailmark.getSubject6()==null){
					
					if(admForm.getSubid_5()!=null ){
						
						if(admForm.getSubid_5().equalsIgnoreCase("")){
							detailmark.setSubject6("");
							detailmark.setSubject6ObtainedMarks("");
							detailmark.setSubject6TotalMarks("");
							
						}else{
						
						
						if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_5()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_5()));
							detailmark.setSubject6(s);
							String g=groupLangSubjectsMap.get(s);
							admForm.setAdmsubgrpname_5(g);
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_5()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_5()));
							detailmark.setSubject6(s);
							String g=groupSubjectsMap.get(s);
							admForm.setAdmsubgrpname_5(g);
						}
						
						}	
					}else{
						detailmark.setSubject6("");
					}
					
					if(admForm.getMaxmark_5()!=null && !admForm.getMaxmark_5().equalsIgnoreCase("")){
						detailmark.setSubject6TotalMarks(admForm.getMaxmark_5());
					}else{
						detailmark.setSubject6TotalMarks(admForm.getMaxmark_1());
					}
					
					if(admForm.getObtainedmark_5()!=null && !admForm.getObtainedmark_5().equalsIgnoreCase("")){
						detailmark.setSubject6ObtainedMarks(admForm.getObtainedmark_5());
					}else{
						detailmark.setSubject6ObtainedMarks(admForm.getObtainedmark_1());
					}
					
					
					
					
				
				//}//sub 6 over
				

				//else if(detailmark.getSubject7()==null){
					
					if(admForm.getSubid_6()!=null ){
						if(admForm.getSubid_6().equalsIgnoreCase("")){
							detailmark.setSubject7("");
							detailmark.setSubject7ObtainedMarks("");
							detailmark.setSubject7TotalMarks("");
							
						}else{
						
						if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_6()))){
							
							String s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_6()));
							detailmark.setSubject7(s);
							String g=groupLangSubjectsMap.get(s);
							admForm.setAdmsubgrpname_6(g);
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_6()))){
							
							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_6()));
							detailmark.setSubject7(s);
							String g=groupSubjectsMap.get(s);
							admForm.setAdmsubgrpname_6(g);
						}
						
					}
					}else{
						detailmark.setSubject7("");
					}
					
					if(admForm.getMaxmark_6()!=null && !admForm.getMaxmark_6().equalsIgnoreCase("")){
						detailmark.setSubject7TotalMarks(admForm.getMaxmark_6());
					}else{
						detailmark.setSubject7TotalMarks(admForm.getMaxmark_1());
					}
					
					if(admForm.getObtainedmark_6()!=null && !admForm.getObtainedmark_6().equalsIgnoreCase("")){
						detailmark.setSubject7ObtainedMarks(admForm.getObtainedmark_6());
					}else{
						detailmark.setSubject7ObtainedMarks(admForm.getObtainedmark_1());
					}
					
					
					
					
				
				//}//sub 7 over
				
	//else if(detailmark.getSubject8()==null){
		
		if(admForm.getSubid_7()!=null ){
			if(admForm.getSubid_7().equalsIgnoreCase("")){
				detailmark.setSubject8("");
				detailmark.setSubject8ObtainedMarks("");
				detailmark.setSubject8TotalMarks("");
				
			}else{
			if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_7()))){
				
				String s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_7()));
				detailmark.setSubject8(s);
				String g=groupLangSubjectsMap.get(s);
				admForm.setAdmsubgrpname_7(g);
			}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_7()))){
				
				String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_7()));
				detailmark.setSubject8(s);
				String g=groupSubjectsMap.get(s);
				admForm.setAdmsubgrpname_7(g);
			}
			}
			
		}else{
			detailmark.setSubject8("");
		}
		
		if(admForm.getMaxmark_7()!=null && !admForm.getMaxmark_7().equalsIgnoreCase("")){
			detailmark.setSubject8TotalMarks(admForm.getMaxmark_7());
		}else{
			detailmark.setSubject8TotalMarks(admForm.getMaxmark_7());
		}
		
		if(admForm.getObtainedmark_7()!=null && !admForm.getObtainedmark_7().equalsIgnoreCase("")){
			detailmark.setSubject8ObtainedMarks(admForm.getObtainedmark_7());
		}else{
			detailmark.setSubject8ObtainedMarks(admForm.getObtainedmark_1());
		}
		
		
		
		
	
	//}//sub 8 over
				
	//else if(detailmark.getSubject9()==null){
		
		if(admForm.getSubid_8()!=null ){
			
			if(admForm.getSubid_8().equalsIgnoreCase("")){
				detailmark.setSubject9("");
				detailmark.setSubject9ObtainedMarks("");
				detailmark.setSubject9TotalMarks("");
				
			}else{
			
			
			if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_8()))){
				
				String s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_8()));
				detailmark.setSubject9(s);
				String g=groupLangSubjectsMap.get(s);
				admForm.setAdmsubgrpname_8(g);
			}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_8()))){
				
				String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_8()));
				detailmark.setSubject9(s);
				String g=groupSubjectsMap.get(s);
				admForm.setAdmsubgrpname_8(g);
			}
			
			}
			
		}else{
			detailmark.setSubject9("");
		}
		
		if(admForm.getMaxmark_8()!=null && !admForm.getMaxmark_8().equalsIgnoreCase("")){
			detailmark.setSubject9TotalMarks(admForm.getMaxmark_8());
		}else{
			detailmark.setSubject9TotalMarks(admForm.getMaxmark_8());
		}
		
		if(admForm.getObtainedmark_8()!=null && !admForm.getObtainedmark_8().equalsIgnoreCase("")){
			detailmark.setSubject9ObtainedMarks(admForm.getObtainedmark_8());
		}else{
			detailmark.setSubject9ObtainedMarks(admForm.getObtainedmark_1());
		}
		
		
		
		
	
	//}//sub 9 over
				
	
			
			
			//ActionMessages errors = validateMarksFor12thClass(detailmark);
			/*if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH);
				else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
			}
			*/
			if(admForm.getTotalmaxmark()!=null){
				detailmark.setTotalMarks(admForm.getTotalmaxmark());
			}
			if(admForm.getTotalobtainedmark()!=null){
				detailmark.setTotalObtainedMarks(admForm.getTotalobtainedmark());
			}
			
			int i=0;
			//Iterator<AdmSubjectMarkForRankTO> itr=admsubList.iterator();
			//while(itr.hasNext()){
				
				for(i=0;i<9;){
					AdmSubjectMarkForRankTO admSubjectMarkForRankTO=new AdmSubjectMarkForRankTO() ;
			if(i==0){
				if(admForm.getSubid_0()!=null && !admForm.getSubid_0().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_0());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_0());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_0());
				if(admForm.getAdmsubmarkid_0()!=null && !admForm.getAdmsubmarkid_0().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_0()));
				}
				AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
				admSubjectForRankTo.setId(Integer.parseInt(admForm.getSubid_0()));
				admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_0());
				
				String s="";
				if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_0()))){
					s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_0()));	
					
				}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_0()))){
					s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_0()));
					
				}
				admSubjectForRankTo.setSubjectname(s);
				
				
				admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
				
				//admSubList.add(admSubjectMarkForRankTO);
			}
				i++;
			}
			else if(i==1){
				if(admForm.getSubid_1()!=null && !admForm.getSubid_1().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_1());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_1());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_1());
				if(admForm.getAdmsubmarkid_1()!=null && !admForm.getAdmsubmarkid_1().equalsIgnoreCase("")){
					admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_1()));
					
					}
				AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
				admSubjectForRankTo.setId(Integer.parseInt(admForm.getSubid_1()));
				admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_1());
				
				String s="";
				if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_1()))){
					s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_1()));	
					
				}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_1()))){
					s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_1()));
					
				}
				admSubjectForRankTo.setSubjectname(s);
				
				admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
				
				//admSubList.add(admSubjectMarkForRankTO);
				}
				i++;
			}
			else if(i==2){
				if(admForm.getSubid_2()!=null && !admForm.getSubid_2().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_2());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_2());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_2());
				if(admForm.getAdmsubmarkid_2()!=null && !admForm.getAdmsubmarkid_2().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_2()));
				
					}
				AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
				admSubjectForRankTo.setId(Integer.parseInt(admForm.getSubid_2()));
				admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_2());
				
				String s="";
				if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_2()))){
					s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_2()));	
					
				}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_2()))){
					s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_2()));
					
				}
				admSubjectForRankTo.setSubjectname(s);
				
				admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
				
				//admSubList.add(admSubjectMarkForRankTO);
				}
				i++;
			}
			else if(i==3){
				if(admForm.getSubid_3()!=null && !admForm.getSubid_3().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_3());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_3());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_3());
				if(admForm.getAdmsubmarkid_3()!=null && !admForm.getAdmsubmarkid_3().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_3()));
				
					}
				AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
				admSubjectForRankTo.setId(Integer.parseInt(admForm.getSubid_3()));
				admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_3());
				
				String s="";
				if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_3()))){
					s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_3()));	
					
				}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_3()))){
					s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_3()));
					
				}
				admSubjectForRankTo.setSubjectname(s);
				
				admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
				
				//admSubList.add(admSubjectMarkForRankTO);
				}
				i++;
			}
			else if(i==4){
				if(admForm.getSubid_4()!=null && !admForm.getSubid_4().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_4());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_4());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_4());
				if(admForm.getAdmsubmarkid_4()!=null && !admForm.getAdmsubmarkid_4().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_4()));
					
					}
				
				AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
				admSubjectForRankTo.setId(Integer.parseInt(admForm.getSubid_4()));
				admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_4());
				
				String s="";
				if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_4()))){
					s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_4()));	
					
				}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_4()))){
					s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_4()));
					
				}
				
				admSubjectForRankTo.setSubjectname(s);
				
				admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
				
				//admSubList.add(admSubjectMarkForRankTO);
				}
				i++;
			}
			else if(i==5){
				if(admForm.getSubid_5()!=null && !admForm.getSubid_5().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_5());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_5());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_5());
				if(admForm.getAdmsubmarkid_5()!=null && !admForm.getAdmsubmarkid_5().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_5()));
				
					}
				AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
				admSubjectForRankTo.setId(Integer.parseInt(admForm.getSubid_5()));
				admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_5());
				
				String s="";
				if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_5()))){
					s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_5()));	
					
				}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_5()))){
					s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_5()));
					
				}
				admSubjectForRankTo.setSubjectname(s);
				
				admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
				
				//admSubList.add(admSubjectMarkForRankTO);
				}
				i++;
			}
			else if(i==6){
				if(admForm.getSubid_6()!=null && !admForm.getSubid_6().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_6());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_6());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_6());
				if(admForm.getAdmsubmarkid_6()!=null && !admForm.getAdmsubmarkid_6().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_6()));
				
					}
				AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
				admSubjectForRankTo.setId(Integer.parseInt(admForm.getSubid_6()));
				admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_6());
				
				String s="";
				if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_6()))){
					s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_6()));	
					
				}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_6()))){
					s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_6()));
					
				}
				admSubjectForRankTo.setSubjectname(s);
				
				admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
				
				//admSubList.add(admSubjectMarkForRankTO);
				}
				i++;
			}
			else if(i==7){
				if(admForm.getSubid_7()!=null && !admForm.getSubid_7().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_7());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_7());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_7());
				if(admForm.getAdmsubmarkid_7()!=null && !admForm.getAdmsubmarkid_7().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_7()));
				
					}
				AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
				admSubjectForRankTo.setId(Integer.parseInt(admForm.getSubid_7()));
				admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_7());
				
				String s="";
				if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_7()))){
					s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_7()));	
					
				}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_7()))){
					s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_7()));
					
				}
				admSubjectForRankTo.setSubjectname(s);
				
				admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
				
				//admSubList.add(admSubjectMarkForRankTO);
				}
				i++;
			}
			else if(i==8){
				if(admForm.getSubid_8()!=null && !admForm.getSubid_8().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_8());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_8());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_8());
				if(admForm.getAdmsubmarkid_8()!=null && !admForm.getAdmsubmarkid_8().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_8()));
				
					}
				AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
				admSubjectForRankTo.setId(Integer.parseInt(admForm.getSubid_8()));
				admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_8());
				
				String s="";
				if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_8()))){
					s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_8()));	
					
				}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_8()))){
					s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_8()));
					
				}
				admSubjectForRankTo.setSubjectname(s);
				
				admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
				
				//admSubList.add(admSubjectMarkForRankTO);
				}
				i++;
			}
			
			admSubList.add(admSubjectMarkForRankTO);
			}
			
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getId()==detailMarkIndex){
						detailmark.setId(qualTO.getDetailmark().getId());
						qualTO.setDetailmark(detailmark);
						//raghu
						admForm.setDetailMark(qualTO.getDetailmark());
					}
				}
			}
			
			admForm.setAdmsubMarkList(admSubList);
			 
		
		//}  
		}
	catch (Exception e) {
			log.error("error in submitDetailMarkConfirm page...",e);
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
		log.info("enter ssubmitDetailMarkConfirm page...");
		
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
			
	}
	
	
	
	
			public ActionForward forwardConfirmPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		if(admForm.isOnlineApply()&& !admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
	}
	
	
	
	
	
	public ActionForward initEditDetailMarkConfirmPage12(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		IApplicationEditTransaction txn=  ApplicationEditTransactionimpl.getInstance();
		AdmSubjectMarkForRankTO admSubjectMarkForRankTO=null;
		double totalobt=0;
		double totalmax=0;
		try {
			
			String indexString = request.getParameter("editcountID");
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute("editcountID", indexString);
				}else
					session.removeAttribute("editcountID");
			}
			
			Map<String,String> groupSubjectsMap=ApplicationEditHandler.getInstance().get12thclassGroupSubjects("");
			admForm.setGroupSubjectsMap(groupSubjectsMap);
			Map<String,String> groupLangSubjectsMap=ApplicationEditHandler.getInstance().get12thclassGroupSubjects("Language");
			admForm.setGroupLangSubjectsMap(groupLangSubjectsMap);

			
			admForm.setAllot_0(false) ;
			admForm.setAllot_1(false) ;
			admForm.setAllot_2(false) ;
			admForm.setAllot_3(false) ;
			admForm.setAllot_4(false) ;
			admForm.setAllot_5(false) ;
			admForm.setAllot_6(false) ;
			admForm.setAllot_7(false) ;
			admForm.setAllot_8(false) ;
			String applNo=admForm.getApplicationNumber()+12;
			
			List<EdnQualificationTO> ednQualificationTO=admForm.getApplicantDetails().getEdnQualificationList();
			Iterator<EdnQualificationTO> itr=ednQualificationTO.iterator();
			
			while(itr.hasNext()){
				
				EdnQualificationTO ednQualificationTO1=itr.next();
			if(ednQualificationTO1.getDocName()!=null && !ednQualificationTO1.getDocName().isEmpty() && ednQualificationTO1.getDocName().equalsIgnoreCase("Class 12")){
				
				
				
				
	
				
				//List<AdmSubjectMarkForRank> subList=txn.getAdmSubjectMarkList(ednQualificationTO1.getId());
				List<AdmSubjectMarkForRankTO> subList1=	admForm.getAdmsubMarkList();
				
				if(subList1!=null){
					
					
					if(session.getAttribute(applNo)!=null && session.getAttribute(applNo).toString().equalsIgnoreCase(applNo))
						
					{
						
						
						int i=0;
						Iterator<AdmSubjectMarkForRankTO> itr1=subList1.iterator();
						while(itr1.hasNext()){
							AdmSubjectMarkForRankTO admSubjectMarkForRank=itr1.next();
							 if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
							 if(admSubjectMarkForRank.getAdmSubjectForRankTo().getSubjectname().equalsIgnoreCase("English")){
								
							if(i==0){ 
							 if(!admForm.isAllot_0()){
								 
								  
							  admForm.setSubid_0(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
							  admForm.setObtainedmark_0(admSubjectMarkForRank.getObtainedmark());
							  admForm.setMaxmark_0(admSubjectMarkForRank.getMaxmark());
							  admForm.setAdmsubmarkid_0(String.valueOf(admSubjectMarkForRank.getId()));
							  admForm.setAdmsubgrpname_0(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
							  
							  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
							  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
							  admForm.setAllot_0(true);
								
								}	
							 admForm.setAllot_0(true);
							 i++;
								}
							 } else  if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getSubjectname().equalsIgnoreCase("English")){
								 if(i==1){ 	
								 if(!admForm.isAllot_1()){
										
								  admForm.setSubid_1(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_1(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_1(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_1(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_1(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_1(true);
								 }
								 admForm.setAllot_1(true);
								 i++;
									}
									
								 }
							 }
							 
							 // language close
							
							 /* if(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								 if(!admForm.isAllot_1()){
								  admForm.setSubid_1(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_1(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_1(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_1(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_1(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  i++;
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_1(true);
								 }
							}
							*/ 
							 
							 else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && !admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								 if(i==2){ 	
								 if(!admForm.isAllot_2()){
								  admForm.setSubid_2(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_2(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_2(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_2(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_2(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_2(true);
								 }
								 admForm.setAllot_2(true);
								 i++;
									}
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								 else if(i==3){ 	
								 if(!admForm.isAllot_3()){
								  admForm.setSubid_3(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_3(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_3(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_3(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_3(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_3(true);
								 }
								 admForm.setAllot_3(true);
								 i++;
									}
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								 else if(i==4){ 	
								  if(!admForm.isAllot_4()){
								  admForm.setSubid_4(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_4(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_4(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_4(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_4(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_4(true);
								 }
								 admForm.setAllot_4(true);
								 i++;
									}
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								 else if(i==5){ 	
								  if(!admForm.isAllot_5()){
								  admForm.setSubid_5(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_5(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_5(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_5(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_5(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_5(true);
								 }
								 admForm.setAllot_5(true);
								 i++;
									}
								 
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								 else if(i==6){ 	
								  if(!admForm.isAllot_6()){
								  admForm.setSubid_6(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_6(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_6(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_6(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_6(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_6(true);
								 }
								 admForm.setAllot_6(true);
								 i++;
									}
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								 else if(i==7){ 	
								  if(!admForm.isAllot_7()){
								  admForm.setSubid_7(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_7(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_7(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_7(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_7(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_7(true);
								 }
								 admForm.setAllot_7(true);
								 i++;
									}
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								 else if(i==8){ 	
								  if(!admForm.isAllot_8()){
								  admForm.setSubid_8(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_8(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_8(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_8(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_8(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_8(true);
								 }
								 admForm.setAllot_8(true);
								 i++;
									}
								 else{
									 i++;
								 }
								 
							//}
							
							 
					} // !language close
					else{
						i++;
					}
							 
					}// while close
						
					
					}
					
					else
					{
						
						
						
						Iterator<AdmSubjectMarkForRankTO> itr1=subList1.iterator();
						while(itr1.hasNext()){
							AdmSubjectMarkForRankTO admSubjectMarkForRank=itr1.next();
							 if(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
							 if(admSubjectMarkForRank.getAdmSubjectForRankTo().getSubjectname().equalsIgnoreCase("English")){
							   
							 if(!admForm.isAllot_0()){
								 
								  
							  admForm.setSubid_0(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
							  admForm.setObtainedmark_0(admSubjectMarkForRank.getObtainedmark());
							  admForm.setMaxmark_0(admSubjectMarkForRank.getMaxmark());
							  admForm.setAdmsubmarkid_0(String.valueOf(admSubjectMarkForRank.getId()));
							  admForm.setAdmsubgrpname_0(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
							  
							  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
							  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
							  admForm.setAllot_0(true);
								
								}	
								
							 } else  if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getSubjectname().equalsIgnoreCase("English")){
									
								 if(!admForm.isAllot_1()){
										
								  admForm.setSubid_1(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_1(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_1(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_1(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_1(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_1(true);
								 }
								
									
								 }
							 }// language close
							
							 /* if(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								 if(!admForm.isAllot_1()){
								  admForm.setSubid_1(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_1(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_1(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_1(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_1(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  i++;
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_1(true);
								 }
							}
							*/ 
							 
							 else if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								
								 if(!admForm.isAllot_2()){
								  admForm.setSubid_2(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_2(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_2(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_2(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_2(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_2(true);
								 }
								
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								
								 else if(!admForm.isAllot_3()){
								  admForm.setSubid_3(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_3(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_3(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_3(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_3(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_3(true);
								 }
								 
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								
								 else if(!admForm.isAllot_4()){
								  admForm.setSubid_4(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_4(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_4(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_4(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_4(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_4(true);
								 }
								
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								
								 else if(!admForm.isAllot_5()){
								  admForm.setSubid_5(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_5(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_5(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_5(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_5(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_5(true);
								 }
								
								 
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								
								 else if(!admForm.isAllot_6()){
								  admForm.setSubid_6(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_6(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_6(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_6(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_6(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_6(true);
								 }
								
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								 
								 else if(!admForm.isAllot_7()){
								  admForm.setSubid_7(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_7(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_7(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_7(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_7(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_7(true);
								 }
								 
							//}
							// if(!admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Language")){
								 
								 else if(!admForm.isAllot_8()){
								  admForm.setSubid_8(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setObtainedmark_8(admSubjectMarkForRank.getObtainedmark());
								  admForm.setMaxmark_8(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_8(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_8(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_8(true);
								 }
								
							//}
							
							 
					} // !language close
							 
					}// while close
						
					
						
					}
						
						
						
						
						
					
								admForm.setTotalmaxmark(String.valueOf(totalmax));
								admForm.setTotalobtainedmark(String.valueOf(totalobt));
								
								String language="Language";
								Map<Integer,String> admsubjectMap=ApplicationEditHandler.getInstance().get12thclassSubjects(ednQualificationTO1.getDocName(),language);
								admForm.setAdmSubjectMap(admsubjectMap);
								Map<Integer,String> admsubjectLangMap=ApplicationEditHandler.getInstance().get12thclassLangSubjects(ednQualificationTO1.getDocName(),language);
								admForm.setAdmSubjectLangMap(admsubjectLangMap);
				}// sub list close
						
				
				// raghu added from old code
				Map<Integer,String> subjectMap = ApplicationEditHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
				admForm.setDetailedSubjectsMap(subjectMap);
				
				List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
				if(quals!=null ){
					Iterator<EdnQualificationTO> qualItr=quals.iterator();
					while (qualItr.hasNext()) {
						EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
						if(qualTO.getId()==index){
							if(qualTO.getDetailmark()!=null)
							admForm.setDetailMark(qualTO.getDetailmark());
							else{
								CandidateMarkTO markTo=new CandidateMarkTO();
								ApplicationEditHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
								admForm.setDetailMark(markTo);
							}
						}
					}
				}
				
			/*Map<Integer,String> subjectMap = AdmissionFormHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);*/
			
			
			
			/*if(admForm.getDetailMark()==null){
				CandidateMarkTO markTo=new CandidateMarkTO();
				AdmissionFormHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
				admForm.setDetailMark(markTo);
			}
		*/
				
		}//edn to close
			
		} // while close
			
			//raghu setting first time it excutes marks entry
			session.setAttribute(admForm.getApplicationNumber()+"12", admForm.getApplicationNumber()+"12");
		
		
		} catch (Exception e) {
			log.error("error initDetailMarkConfirmPage...",e);
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
		log.info("exit initDetailMarkConfirmPage...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH_FOREDIT);
		//return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
	}
	
	
	
	
	
// for pg
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEditDetailMarkConfirmPagedeg(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		
		
		double totalobt=0;
		double totalmax=0;
		
		try 
		{
			String indexString = request.getParameter("editcountID");
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) 
			{
				if (indexString != null)
				{
					index=Integer.parseInt(indexString);
					session.setAttribute("editcountID", indexString);
				}
				else
					session.removeAttribute("editcountID");
			}
			
			String Core="Core";
			String Compl="Complimentary";
			String Common="Common";
			String Main="Open";
			String Sub="Vocational";
			
			Map<Integer,String> degGroupCoreMap=ApplicationEditHandler.getInstance().getDegClassGroupSubject(Core);
			admForm.setDegGroupCoreMap(degGroupCoreMap);
			Map<Integer,String> degGroupCommonMap=ApplicationEditHandler.getInstance().getDegClassGroupSubject(Common);
			admForm.setDegGroupCommonMap(degGroupCommonMap);
			Map<Integer,String> degGroupComplMap=ApplicationEditHandler.getInstance().getDegClassGroupSubject(Compl);
			admForm.setDegGroupComplMap(degGroupComplMap);
			Map<Integer,String> degGroupOpenMap=ApplicationEditHandler.getInstance().getDegClassGroupSubject(Main);
			admForm.setDegGroupOpenMap(degGroupOpenMap);
			Map<Integer,String> degGroupSubMap=ApplicationEditHandler.getInstance().getDegClassGroupSubject(Sub);
			admForm.setDegGroupSubMap(degGroupSubMap);

			admForm.setAllot_0(false) ;
			admForm.setAllot_1(false) ;
			admForm.setAllot_2(false) ;
			admForm.setAllot_3(false) ;
			admForm.setAllot_4(false) ;
			admForm.setAllot_5(false) ;
			admForm.setAllot_6(false) ;
			admForm.setAllot_7(false) ;
			admForm.setAllot_8(false) ;
			admForm.setAllot_9(false) ;
			admForm.setAllot_10(false) ;
			admForm.setAllot_11(false) ;
			admForm.setAllot_12(false) ;
			admForm.setAllot_13(false) ;
			admForm.setAllot_14(false) ;
			admForm.setAllot_15(false) ;
			admForm.setAllot_16(false) ;
			admForm.setAllot_17(false) ;
			
			
			List<EdnQualificationTO> ednQualificationTO=admForm.getApplicantDetails().getEdnQualificationList();
			Iterator<EdnQualificationTO> itr=ednQualificationTO.iterator();
			
			while(itr.hasNext()){
			EdnQualificationTO ednQualificationTO1=itr.next();
			if(ednQualificationTO1.getDocName()!=null && !ednQualificationTO1.getDocName().isEmpty() && ednQualificationTO1.getDocName().equalsIgnoreCase("DEG"))
			{
				if(ednQualificationTO1.getUgPattern()!=null){
					
				
				admForm.setPatternofStudy(ednQualificationTO1.getUgPattern());
				
				if(ednQualificationTO1.getUgPattern().equalsIgnoreCase("CBCSS"))
				{
					List<AdmSubjectMarkForRankTO> subList1=	admForm.getAdmsubMarkListUG();
					int i=0;
					if(subList1!=null)
					{
						
						if(session.getAttribute(admForm.getApplicationNumber())!=null && session.getAttribute(admForm.getApplicationNumber()).toString().equalsIgnoreCase(admForm.getApplicationNumber()))
						
						{
						Iterator<AdmSubjectMarkForRankTO> itr1=subList1.iterator();
						while(itr1.hasNext())
						{
							AdmSubjectMarkForRankTO admSubjectMarkForRank=itr1.next();
							
							
							if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Core"))
							 {
								if(i==0){
							 if(!admForm.isAllot_0())
							 {
								 //if(admForm.getDegsubid_0()!=null && !admForm.getDegsubid_0().equalsIgnoreCase(""))
									admForm.setDegsubid_0(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								// if(admForm.getDegmaxcgpa_0()!=null && !admForm.getDegmaxcgpa_0().equalsIgnoreCase(""))
								 	admForm.setDegmaxcgpa_0(admSubjectMarkForRank.getCredit());
								// if(admForm.getDegmaxmark_0()!=null && !admForm.getDegmaxmark_0().equalsIgnoreCase(""))
								 	admForm.setDegmaxmark_0(admSubjectMarkForRank.getMaxmark());
								 //if(admForm.getDegobtainedmark_0()!=null && !admForm.getDegobtainedmark_0().equalsIgnoreCase(""))
								 	admForm.setDegobtainedmark_0(admSubjectMarkForRank.getObtainedmark());
								 
								 	admForm.setAdmsubmarkid_0(String.valueOf(admSubjectMarkForRank.getId()));
								 	admForm.setAdmsubgrpname_0(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								 	
								 	totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								 	totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								 	admForm.setAllot_0(true);
							 }
							 admForm.setAllot_0(true);
							 i++;
								}
							 }
								else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Vocational"))
								 {
								
								if(i==1){
							  if(!admForm.isAllot_1())
							 {
								 //if(admForm.getDegsubid_1()!=null && !admForm.getDegsubid_1().equalsIgnoreCase(""))
									admForm.setDegsubid_1(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								// if(admForm.getDegmaxcgpa_1()!=null && !admForm.getDegmaxcgpa_1().equalsIgnoreCase(""))
									admForm.setDegmaxcgpa_1(admSubjectMarkForRank.getCredit());
								 //if(admForm.getDegmaxmark_1()!=null && !admForm.getDegmaxmark_1().equalsIgnoreCase(""))
									admForm.setDegmaxmark_1(admSubjectMarkForRank.getMaxmark());
								// if(admForm.getDegobtainedmark_1()!=null && !admForm.getDegobtainedmark_1().equalsIgnoreCase(""))
									admForm.setDegobtainedmark_1(admSubjectMarkForRank.getObtainedmark());
									 
									admForm.setAdmsubmarkid_1(String.valueOf(admSubjectMarkForRank.getId()));
									admForm.setAdmsubgrpname_1(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									
									totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									admForm.setAllot_1(true);
								 
							 }
							  admForm.setAllot_1(true);
							  i++;
								}
								else if(i==2){
							 if(!admForm.isAllot_2())
							 {
								// if(admForm.getDegsubid_2()!=null && !admForm.getDegsubid_2().equalsIgnoreCase(""))
									admForm.setDegsubid_2(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								// if(admForm.getDegmaxcgpa_2()!=null && !admForm.getDegmaxcgpa_2().equalsIgnoreCase(""))
									admForm.setDegmaxcgpa_2(admSubjectMarkForRank.getCredit());
								// if(admForm.getDegmaxmark_2()!=null && !admForm.getDegmaxmark_2().equalsIgnoreCase(""))
									admForm.setDegmaxmark_2(admSubjectMarkForRank.getMaxmark());
								 //if(admForm.getDegobtainedmark_2()!=null && !admForm.getDegobtainedmark_2().equalsIgnoreCase(""))
									admForm.setDegobtainedmark_2(admSubjectMarkForRank.getObtainedmark());
								 
									admForm.setAdmsubmarkid_2(String.valueOf(admSubjectMarkForRank.getId()));
									admForm.setAdmsubgrpname_2(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									
									totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									admForm.setAllot_2(true);
								 
							 }
							 admForm.setAllot_2(true);
							 i++;
								}
							 }// vocational close
							
							
							else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Complimentary"))
							 {
								if(i==3){
							 if(!admForm.isAllot_3())
							 {
								// if(admForm.getDegsubid_3()!=null && !admForm.getDegsubid_3().equalsIgnoreCase(""))
									admForm.setDegsubid_3(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								// if(admForm.getDegmaxcgpa_3()!=null && !admForm.getDegmaxcgpa_3().equalsIgnoreCase(""))
									admForm.setDegmaxcgpa_3(admSubjectMarkForRank.getCredit());
								 //if(admForm.getDegmaxmark_3()!=null && !admForm.getDegmaxmark_3().equalsIgnoreCase(""))
									admForm.setDegmaxmark_3(admSubjectMarkForRank.getMaxmark());
								// if(admForm.getDegobtainedmark_3()!=null && !admForm.getDegobtainedmark_3().equalsIgnoreCase(""))
									admForm.setDegobtainedmark_3(admSubjectMarkForRank.getObtainedmark());
								
									admForm.setAdmsubmarkid_3(String.valueOf(admSubjectMarkForRank.getId()));
									admForm.setAdmsubgrpname_3(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									
									totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									admForm.setAllot_3(true);
							 }
							 admForm.setAllot_3(true);
							 i++;
								}	
								else if(i==4){
							 if(!admForm.isAllot_4())
							 {
								// if(admForm.getDegsubid_4()!=null && !admForm.getDegsubid_4().equalsIgnoreCase(""))
									admForm.setDegsubid_4(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								// if(admForm.getDegmaxcgpa_4()!=null && !admForm.getDegmaxcgpa_4().equalsIgnoreCase(""))
									admForm.setDegmaxcgpa_4(admSubjectMarkForRank.getCredit());
								// if(admForm.getDegmaxmark_4()!=null && !admForm.getDegmaxmark_4().equalsIgnoreCase(""))
									admForm.setDegmaxmark_4(admSubjectMarkForRank.getMaxmark());
								// if(admForm.getDegobtainedmark_4()!=null && !admForm.getDegobtainedmark_4().equalsIgnoreCase(""))
									admForm.setDegobtainedmark_4(admSubjectMarkForRank.getObtainedmark());
									 
									admForm.setAdmsubmarkid_4(String.valueOf(admSubjectMarkForRank.getId()));
									admForm.setAdmsubgrpname_4(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									
									totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									admForm.setAllot_4(true);
								 
							 }
							 admForm.setAllot_4(true);
							 i++;
								}
							 }// Complimentary close
							
							
							else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Common"))
							 {
							
								 if(i==5){
							 if(!admForm.isAllot_5())
							 {  
								// if(admForm.getDegsubid_5()!=null && !admForm.getDegsubid_5().equalsIgnoreCase(""))
									admForm.setDegsubid_5(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								// if(admForm.getDegmaxcgpa_5()!=null && !admForm.getDegmaxcgpa_5().equalsIgnoreCase(""))
									admForm.setDegmaxcgpa_5(admSubjectMarkForRank.getCredit());
								// if(admForm.getDegmaxmark_5()!=null && !admForm.getDegmaxmark_5().equalsIgnoreCase(""))
									admForm.setDegmaxmark_5(admSubjectMarkForRank.getMaxmark());
								// if(admForm.getDegobtainedmark_5()!=null && !admForm.getDegobtainedmark_5().equalsIgnoreCase(""))
									admForm.setDegobtainedmark_5(admSubjectMarkForRank.getObtainedmark());
									
									admForm.setAdmsubmarkid_5(String.valueOf(admSubjectMarkForRank.getId()));
									admForm.setAdmsubgrpname_5(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									
									totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									admForm.setAllot_5(true);
							 }
							 admForm.setAllot_5(true);
							 i++;
								 }		
							 
							
								 else if(i==6){
								if(!admForm.isAllot_6())
								 {	
								// if(admForm.getDegsubid_6()!=null && !admForm.getDegsubid_6().equalsIgnoreCase(""))
									admForm.setDegsubid_6(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								// if(admForm.getDegmaxcgpa_6()!=null && !admForm.getDegmaxcgpa_6().equalsIgnoreCase(""))
									admForm.setDegmaxcgpa_6(admSubjectMarkForRank.getCredit());
								// if(admForm.getDegmaxmark_6()!=null && !admForm.getDegmaxmark_6().equalsIgnoreCase(""))
									admForm.setDegmaxmark_6(admSubjectMarkForRank.getMaxmark());
								// if(admForm.getDegobtainedmark_6()!=null && !admForm.getDegobtainedmark_6().equalsIgnoreCase(""))
									admForm.setDegobtainedmark_6(admSubjectMarkForRank.getObtainedmark());
									
									admForm.setAdmsubmarkid_6(String.valueOf(admSubjectMarkForRank.getId()));
								    admForm.setAdmsubgrpname_6(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								    
								    totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								    totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								    admForm.setAllot_6(true);
								 }
								admForm.setAllot_6(true);
								i++;
								 }
							 
							 }// common close
							
							 else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Open"))
							 {
							
								 if(i==7){
							 if(!admForm.isAllot_14())
							 {
								// if(admForm.getDegsubid_14()!=null && !admForm.getDegsubid_14().equalsIgnoreCase(""))
									admForm.setDegsubid_14(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								// if(admForm.getDegmaxcgpa_14()!=null && !admForm.getDegmaxcgpa_14().equalsIgnoreCase(""))
									admForm.setDegmaxcgpa_14(admSubjectMarkForRank.getCredit());
								// if(admForm.getDegmaxmark_14()!=null && !admForm.getDegmaxmark_14().equalsIgnoreCase(""))
									admForm.setDegmaxmark_14(admSubjectMarkForRank.getMaxmark());
								// if(admForm.getDegobtainedmark_14()!=null && !admForm.getDegobtainedmark_14().equalsIgnoreCase(""))
									admForm.setDegobtainedmark_14(admSubjectMarkForRank.getObtainedmark());
									admForm.setAdmsubmarkid_14(String.valueOf(admSubjectMarkForRank.getId()));
								    admForm.setAdmsubgrpname_14(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								    
								    totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								    totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								    admForm.setAllot_14(true);
								 
							 }
							 admForm.setAllot_14(true);
							 i++;
								 }
							
							 }// Complimentary close
							
							
							
							
							
							/*
							 else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Vocational"))
							 {
							
								 if(i==8){
							 if(!admForm.isAllot_15())
							 {
								// if(admForm.getDegsubid_15()!=null && !admForm.getDegsubid_15().equalsIgnoreCase(""))
									admForm.setDegsubid_15(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								// if(admForm.getDegmaxcgpa_15()!=null && !admForm.getDegmaxcgpa_15().equalsIgnoreCase(""))
									admForm.setDegmaxcgpa_15(admSubjectMarkForRank.getCredit());
								// if(admForm.getDegmaxmark_15()!=null && !admForm.getDegmaxmark_15().equalsIgnoreCase(""))
									admForm.setDegmaxmark_15(admSubjectMarkForRank.getMaxmark());
								// if(admForm.getDegobtainedmark_15()!=null && !admForm.getDegobtainedmark_15().equalsIgnoreCase(""))
									admForm.setDegobtainedmark_15(admSubjectMarkForRank.getObtainedmark());
									admForm.setAdmsubmarkid_15(String.valueOf(admSubjectMarkForRank.getId()));
								    admForm.setAdmsubgrpname_15(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								    
								    totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								    totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								    admForm.setAllot_15(true);
								 
							 }
							 admForm.setAllot_15(true);
							 i++;
								 }
							
							 }*/// Vocational sub close
							
							 else{
								 i++;
							 }
					
					}// while close
					
					}
						else
					{
					
							
							Iterator<AdmSubjectMarkForRankTO> itr1=subList1.iterator();
							while(itr1.hasNext())
							{
								AdmSubjectMarkForRankTO admSubjectMarkForRank=itr1.next();
								
								
								if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Core"))
								 {
									
								 if(!admForm.isAllot_0())
								 {
									 //if(admForm.getDegsubid_0()!=null && !admForm.getDegsubid_0().equalsIgnoreCase(""))
										admForm.setDegsubid_0(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									// if(admForm.getDegmaxcgpa_0()!=null && !admForm.getDegmaxcgpa_0().equalsIgnoreCase(""))
									 	admForm.setDegmaxcgpa_0(admSubjectMarkForRank.getCredit());
									// if(admForm.getDegmaxmark_0()!=null && !admForm.getDegmaxmark_0().equalsIgnoreCase(""))
									 	admForm.setDegmaxmark_0(admSubjectMarkForRank.getMaxmark());
									 //if(admForm.getDegobtainedmark_0()!=null && !admForm.getDegobtainedmark_0().equalsIgnoreCase(""))
									 	admForm.setDegobtainedmark_0(admSubjectMarkForRank.getObtainedmark());
									 
									 	admForm.setAdmsubmarkid_0(String.valueOf(admSubjectMarkForRank.getId()));
									 	admForm.setAdmsubgrpname_0(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									 	
									 	totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									 	totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									 	admForm.setAllot_0(true);
								 }
								 
								 }
								
								
								 
								 else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Vocational"))
								 {
								
								
									
								 if(!admForm.isAllot_1())
								 {
									 //if(admForm.getDegsubid_1()!=null && !admForm.getDegsubid_1().equalsIgnoreCase(""))
										admForm.setDegsubid_1(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									// if(admForm.getDegmaxcgpa_1()!=null && !admForm.getDegmaxcgpa_1().equalsIgnoreCase(""))
										admForm.setDegmaxcgpa_1(admSubjectMarkForRank.getCredit());
									 //if(admForm.getDegmaxmark_1()!=null && !admForm.getDegmaxmark_1().equalsIgnoreCase(""))
										admForm.setDegmaxmark_1(admSubjectMarkForRank.getMaxmark());
									// if(admForm.getDegobtainedmark_1()!=null && !admForm.getDegobtainedmark_1().equalsIgnoreCase(""))
										admForm.setDegobtainedmark_1(admSubjectMarkForRank.getObtainedmark());
										 
										admForm.setAdmsubmarkid_1(String.valueOf(admSubjectMarkForRank.getId()));
										admForm.setAdmsubgrpname_1(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
										
										totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
										totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
										admForm.setAllot_1(true);
									 
								 }
								 
									
								 else if(!admForm.isAllot_2())
								 {
									// if(admForm.getDegsubid_2()!=null && !admForm.getDegsubid_2().equalsIgnoreCase(""))
										admForm.setDegsubid_2(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									// if(admForm.getDegmaxcgpa_2()!=null && !admForm.getDegmaxcgpa_2().equalsIgnoreCase(""))
										admForm.setDegmaxcgpa_2(admSubjectMarkForRank.getCredit());
									// if(admForm.getDegmaxmark_2()!=null && !admForm.getDegmaxmark_2().equalsIgnoreCase(""))
										admForm.setDegmaxmark_2(admSubjectMarkForRank.getMaxmark());
									 //if(admForm.getDegobtainedmark_2()!=null && !admForm.getDegobtainedmark_2().equalsIgnoreCase(""))
										admForm.setDegobtainedmark_2(admSubjectMarkForRank.getObtainedmark());
									 
										admForm.setAdmsubmarkid_2(String.valueOf(admSubjectMarkForRank.getId()));
										admForm.setAdmsubgrpname_2(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
										
										totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
										totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
										admForm.setAllot_2(true);
									 
								 }
								 
								 }// vocational close
								
								
								else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Complimentary"))
								 {
									
								 if(!admForm.isAllot_3())
								 {
									// if(admForm.getDegsubid_3()!=null && !admForm.getDegsubid_3().equalsIgnoreCase(""))
										admForm.setDegsubid_3(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									// if(admForm.getDegmaxcgpa_3()!=null && !admForm.getDegmaxcgpa_3().equalsIgnoreCase(""))
										admForm.setDegmaxcgpa_3(admSubjectMarkForRank.getCredit());
									 //if(admForm.getDegmaxmark_3()!=null && !admForm.getDegmaxmark_3().equalsIgnoreCase(""))
										admForm.setDegmaxmark_3(admSubjectMarkForRank.getMaxmark());
									// if(admForm.getDegobtainedmark_3()!=null && !admForm.getDegobtainedmark_3().equalsIgnoreCase(""))
										admForm.setDegobtainedmark_3(admSubjectMarkForRank.getObtainedmark());
									
										admForm.setAdmsubmarkid_3(String.valueOf(admSubjectMarkForRank.getId()));
										admForm.setAdmsubgrpname_3(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
										
										totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
										totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
										admForm.setAllot_3(true);
								 }
								 
									
								 else if(!admForm.isAllot_4())
								 {
									// if(admForm.getDegsubid_4()!=null && !admForm.getDegsubid_4().equalsIgnoreCase(""))
										admForm.setDegsubid_4(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									// if(admForm.getDegmaxcgpa_4()!=null && !admForm.getDegmaxcgpa_4().equalsIgnoreCase(""))
										admForm.setDegmaxcgpa_4(admSubjectMarkForRank.getCredit());
									// if(admForm.getDegmaxmark_4()!=null && !admForm.getDegmaxmark_4().equalsIgnoreCase(""))
										admForm.setDegmaxmark_4(admSubjectMarkForRank.getMaxmark());
									// if(admForm.getDegobtainedmark_4()!=null && !admForm.getDegobtainedmark_4().equalsIgnoreCase(""))
										admForm.setDegobtainedmark_4(admSubjectMarkForRank.getObtainedmark());
										 
										admForm.setAdmsubmarkid_4(String.valueOf(admSubjectMarkForRank.getId()));
										admForm.setAdmsubgrpname_4(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
										
										totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
										totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
										admForm.setAllot_4(true);
									 
								 }
								
								 }// Complimentary close
								
								
								else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Common"))
								 {
								
									
								 if(!admForm.isAllot_5())
								 {  
									// if(admForm.getDegsubid_5()!=null && !admForm.getDegsubid_5().equalsIgnoreCase(""))
										admForm.setDegsubid_5(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									// if(admForm.getDegmaxcgpa_5()!=null && !admForm.getDegmaxcgpa_5().equalsIgnoreCase(""))
										admForm.setDegmaxcgpa_5(admSubjectMarkForRank.getCredit());
									// if(admForm.getDegmaxmark_5()!=null && !admForm.getDegmaxmark_5().equalsIgnoreCase(""))
										admForm.setDegmaxmark_5(admSubjectMarkForRank.getMaxmark());
									// if(admForm.getDegobtainedmark_5()!=null && !admForm.getDegobtainedmark_5().equalsIgnoreCase(""))
										admForm.setDegobtainedmark_5(admSubjectMarkForRank.getObtainedmark());
										
										admForm.setAdmsubmarkid_5(String.valueOf(admSubjectMarkForRank.getId()));
										admForm.setAdmsubgrpname_5(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
										
										totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
										totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
										admForm.setAllot_5(true);
								 }
									
								  
								 
									 
								 else if(!admForm.isAllot_6())
									 {	
									// if(admForm.getDegsubid_6()!=null && !admForm.getDegsubid_6().equalsIgnoreCase(""))
										admForm.setDegsubid_6(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									// if(admForm.getDegmaxcgpa_6()!=null && !admForm.getDegmaxcgpa_6().equalsIgnoreCase(""))
										admForm.setDegmaxcgpa_6(admSubjectMarkForRank.getCredit());
									// if(admForm.getDegmaxmark_6()!=null && !admForm.getDegmaxmark_6().equalsIgnoreCase(""))
										admForm.setDegmaxmark_6(admSubjectMarkForRank.getMaxmark());
									// if(admForm.getDegobtainedmark_6()!=null && !admForm.getDegobtainedmark_6().equalsIgnoreCase(""))
										admForm.setDegobtainedmark_6(admSubjectMarkForRank.getObtainedmark());
										
										admForm.setAdmsubmarkid_6(String.valueOf(admSubjectMarkForRank.getId()));
									    admForm.setAdmsubgrpname_6(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									    
									    totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									    totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									    admForm.setAllot_6(true);
									 }
									
								 
								 }// common close
								
								 else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Open"))
								 {
								
									
								 if(!admForm.isAllot_14())
								 {
									// if(admForm.getDegsubid_14()!=null && !admForm.getDegsubid_14().equalsIgnoreCase(""))
										admForm.setDegsubid_14(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									// if(admForm.getDegmaxcgpa_14()!=null && !admForm.getDegmaxcgpa_14().equalsIgnoreCase(""))
										admForm.setDegmaxcgpa_14(admSubjectMarkForRank.getCredit());
									// if(admForm.getDegmaxmark_14()!=null && !admForm.getDegmaxmark_14().equalsIgnoreCase(""))
										admForm.setDegmaxmark_14(admSubjectMarkForRank.getMaxmark());
									// if(admForm.getDegobtainedmark_14()!=null && !admForm.getDegobtainedmark_14().equalsIgnoreCase(""))
										admForm.setDegobtainedmark_14(admSubjectMarkForRank.getObtainedmark());
										admForm.setAdmsubmarkid_14(String.valueOf(admSubjectMarkForRank.getId()));
									    admForm.setAdmsubgrpname_14(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									    
									    totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									    totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									    admForm.setAllot_14(true);
									 
								 }
								
								
								 }// Complimentary close
								
								 /*else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Vocational"))
								 {
								
									
								 if(!admForm.isAllot_15())
								 {
									// if(admForm.getDegsubid_15()!=null && !admForm.getDegsubid_15().equalsIgnoreCase(""))
										admForm.setDegsubid_15(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									// if(admForm.getDegmaxcgpa_15()!=null && !admForm.getDegmaxcgpa_15().equalsIgnoreCase(""))
										admForm.setDegmaxcgpa_15(admSubjectMarkForRank.getCredit());
									// if(admForm.getDegmaxmark_15()!=null && !admForm.getDegmaxmark_15().equalsIgnoreCase(""))
										admForm.setDegmaxmark_15(admSubjectMarkForRank.getMaxmark());
									// if(admForm.getDegobtainedmark_15()!=null && !admForm.getDegobtainedmark_15().equalsIgnoreCase(""))
										admForm.setDegobtainedmark_15(admSubjectMarkForRank.getObtainedmark());
										admForm.setAdmsubmarkid_15(String.valueOf(admSubjectMarkForRank.getId()));
									    admForm.setAdmsubgrpname_15(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									    
									    totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									    totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									    admForm.setAllot_15(true);
									 
								 }
								
								
								 }// vocational sub close
								 */
						
						}// while close
						
							
							
					}
						//String Sub="Sub";
						Map<Integer,String> admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Core);
						admForm.setAdmCoreMap(admCoreMap);
						Map<Integer,String> admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Compl);
						admForm.setAdmComplMap(admComplMap);
						Map<Integer,String> admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Common);
						admForm.setAdmCommonMap(admCommonMap);
						Map<Integer,String> admOpenMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Main);
						admForm.setAdmMainMap(admOpenMap);
						Map<Integer,String> admVocMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Sub);
						admForm.setAdmSubMap(admVocMap);
						
					}// sublist if close
					
					if(admForm.getDegtotalmaxmark()!=null && !admForm.getDegtotalmaxmark().equalsIgnoreCase("") && admForm.getDegtotalobtainedmark()!=null && !admForm.getDegtotalobtainedmark().equalsIgnoreCase("")){
						admForm.setDegtotalmaxmark(admForm.getDegtotalmaxmark());
						admForm.setDegtotalobtainedmark(admForm.getDegtotalobtainedmark());
					}
					else 
					{
						admForm.setDegtotalmaxmark(ednQualificationTO1.getTotalMarks());
						admForm.setDegtotalobtainedmark(ednQualificationTO1.getMarksObtained());
					}
					
				
				}
				else
				{
					
					List<AdmSubjectMarkForRankTO> subList1=	admForm.getAdmsubMarkListUG();
					int i=7;
					
					if(session.getAttribute(admForm.getApplicationNumber())!=null && session.getAttribute(admForm.getApplicationNumber()).toString().equalsIgnoreCase(admForm.getApplicationNumber()))
						
					{
						
						Iterator<AdmSubjectMarkForRankTO> itr1=subList1.iterator();
						while(itr1.hasNext())
						{
							AdmSubjectMarkForRankTO admSubjectMarkForRank=itr1.next();
							
							 if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Core"))
							 {
								 if(i==7){
							 if(!admForm.isAllot_7())
							 {
							  admForm.setDegsubid_7(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
							  admForm.setDegobtainedmark_7(admSubjectMarkForRank.getObtainedmark());
							  admForm.setDegmaxmark_7(admSubjectMarkForRank.getMaxmark());
							  admForm.setAdmsubmarkid_7(String.valueOf(admSubjectMarkForRank.getId()));
							  admForm.setAdmsubgrpname_7(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
							  
							  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
							  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
							  admForm.setAllot_7(true);
							 }
							 admForm.setAllot_7(true);
							 i++;
								 }
							 }
							 
							 else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Vocational"))
							 {
							
								 if(i==8){
							 if(!admForm.isAllot_8())
							 {
								  admForm.setDegsubid_8(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setDegobtainedmark_8(admSubjectMarkForRank.getObtainedmark());
								  admForm.setDegmaxmark_8(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_8(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_8(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_8(true);
								 
							 }
							 admForm.setAllot_8(true);
							 i++;
								 }
								 else if(i==9){
							 if(!admForm.isAllot_9())
							 {
								  admForm.setDegsubid_9(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setDegobtainedmark_9(admSubjectMarkForRank.getObtainedmark());
								  admForm.setDegmaxmark_9(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_9(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_9(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_9(true);
								 
							 }
							 admForm.setAllot_9(true);
							 i++;
								 }
							 
							 }// main close
							
					
							 else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Complimentary"))
							 {
								 if(i==10){
							 if(!admForm.isAllot_10())
							 {
							  admForm.setDegsubid_10(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
							  admForm.setDegobtainedmark_10(admSubjectMarkForRank.getObtainedmark());
							  admForm.setDegmaxmark_10(admSubjectMarkForRank.getMaxmark());
							  admForm.setAdmsubmarkid_10(String.valueOf(admSubjectMarkForRank.getId()));
							  admForm.setAdmsubgrpname_10(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
							 
							  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
							  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
							  admForm.setAllot_10(true);
							 }
							 admForm.setAllot_10(true);
							 i++;
								 }
								 else if(i==11){
							 if(!admForm.isAllot_11())
							 {
								  admForm.setDegsubid_11(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setDegobtainedmark_11(admSubjectMarkForRank.getObtainedmark());
								  admForm.setDegmaxmark_11(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_11(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_11(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_11(true);
								 
							 }
							 admForm.setAllot_11(true);
							 i++;
								 }
							 
							 
							 }// core close
							
							
							
							 else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Common"))
							 {
							
							if(i==12){
							 if(!admForm.isAllot_12())
							 {
							  admForm.setDegsubid_12(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
							  admForm.setDegobtainedmark_12(admSubjectMarkForRank.getObtainedmark());
							  admForm.setDegmaxmark_12(admSubjectMarkForRank.getMaxmark());
							  admForm.setAdmsubmarkid_12(String.valueOf(admSubjectMarkForRank.getId()));
							  admForm.setAdmsubgrpname_12(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
							  
							  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
							  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
							  admForm.setAllot_12(true);
							 }
							 admForm.setAllot_12(true);
							 i++;
								 	
							 } 
							else if(i==13){
								 if(!admForm.isAllot_13())
								 {
								  admForm.setDegsubid_13(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setDegobtainedmark_13(admSubjectMarkForRank.getObtainedmark());
								  admForm.setDegmaxmark_13(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_13(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_13(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_13(true);
								 }
								 admForm.setAllot_13(true);
								 i++;
								 }
								 
							 }// common close
							 
							 /*else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Vocational"))
							 {
							
								 if(i==14){
							 if(!admForm.isAllot_16())
							 {
								// if(admForm.getDegsubid_16()!=null && !admForm.getDegsubid_16().equalsIgnoreCase(""))
									admForm.setDegsubid_16(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								// if(admForm.getDegmaxmark_16()!=null && !admForm.getDegmaxmark_16().equalsIgnoreCase(""))
									admForm.setDegmaxmark_16(admSubjectMarkForRank.getMaxmark());
								// if(admForm.getDegobtainedmark_16()!=null && !admForm.getDegobtainedmark_16().equalsIgnoreCase(""))
									admForm.setDegobtainedmark_16(admSubjectMarkForRank.getObtainedmark());
									admForm.setAdmsubmarkid_16(String.valueOf(admSubjectMarkForRank.getId()));
								    admForm.setAdmsubgrpname_16(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								    
								    totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								    totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								    admForm.setAllot_16(true);
								 
							 }
							 admForm.setAllot_16(true);
							 i++;
								 }
							
							 }// Vocational sub close
*/							 else{
								 i++;
							 }
					
							 
					}// while close
					
						
					}	
						else
						{
							
							
							
							Iterator<AdmSubjectMarkForRankTO> itr1=subList1.iterator();
							while(itr1.hasNext())
							{
								AdmSubjectMarkForRankTO admSubjectMarkForRank=itr1.next();
								
								 if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Core"))
								 {
									
								 if(!admForm.isAllot_7())
								 {
								  admForm.setDegsubid_7(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setDegobtainedmark_7(admSubjectMarkForRank.getObtainedmark());
								  admForm.setDegmaxmark_7(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_7(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_7(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_7(true);
								 }
								 }
								 else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Vocational"))
								 {
								
								 
								 if(!admForm.isAllot_8())
								 {
									  admForm.setDegsubid_8(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									  admForm.setDegobtainedmark_8(admSubjectMarkForRank.getObtainedmark());
									  admForm.setDegmaxmark_8(admSubjectMarkForRank.getMaxmark());
									  admForm.setAdmsubmarkid_8(String.valueOf(admSubjectMarkForRank.getId()));
									  admForm.setAdmsubgrpname_8(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									  
									  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									  admForm.setAllot_8(true);
									 
								 }
								 
									 
								 else if(!admForm.isAllot_9())
								 {
									  admForm.setDegsubid_9(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									  admForm.setDegobtainedmark_9(admSubjectMarkForRank.getObtainedmark());
									  admForm.setDegmaxmark_9(admSubjectMarkForRank.getMaxmark());
									  admForm.setAdmsubmarkid_9(String.valueOf(admSubjectMarkForRank.getId()));
									  admForm.setAdmsubgrpname_9(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									  
									  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									  admForm.setAllot_9(true);
									 
								 }
								 
								 }// main close
								
						
								 else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Complimentary"))
								 {
									 
								 if(!admForm.isAllot_10())
								 {
								  admForm.setDegsubid_10(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setDegobtainedmark_10(admSubjectMarkForRank.getObtainedmark());
								  admForm.setDegmaxmark_10(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_10(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_10(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								 
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_10(true);
								 }
								
									
								 else if(!admForm.isAllot_11())
								 {
									  admForm.setDegsubid_11(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									  admForm.setDegobtainedmark_11(admSubjectMarkForRank.getObtainedmark());
									  admForm.setDegmaxmark_11(admSubjectMarkForRank.getMaxmark());
									  admForm.setAdmsubmarkid_11(String.valueOf(admSubjectMarkForRank.getId()));
									  admForm.setAdmsubgrpname_11(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									  
									  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									  admForm.setAllot_11(true);
									 
								 }
								
								 
								 
								 }// core close
								
								
								
								 else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Common"))
								 {
								 
									
								 if(!admForm.isAllot_12())
								 {
								  admForm.setDegsubid_12(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
								  admForm.setDegobtainedmark_12(admSubjectMarkForRank.getObtainedmark());
								  admForm.setDegmaxmark_12(admSubjectMarkForRank.getMaxmark());
								  admForm.setAdmsubmarkid_12(String.valueOf(admSubjectMarkForRank.getId()));
								  admForm.setAdmsubgrpname_12(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
								  
								  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
								  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
								  admForm.setAllot_12(true);
								 }
								 
								
									 
								 else	 if(!admForm.isAllot_13())
									 {
									  admForm.setDegsubid_13(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									  admForm.setDegobtainedmark_13(admSubjectMarkForRank.getObtainedmark());
									  admForm.setDegmaxmark_13(admSubjectMarkForRank.getMaxmark());
									  admForm.setAdmsubmarkid_13(String.valueOf(admSubjectMarkForRank.getId()));
									  admForm.setAdmsubgrpname_13(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									  
									  totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									  totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									  admForm.setAllot_13(true);
									 }
									
									 
								 }// common close
								
								 
								 /*else if(admSubjectMarkForRank.getAdmSubjectForRankTo()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname()!=null && admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname().equalsIgnoreCase("Vocational"))
								 {
								
									
								 if(!admForm.isAllot_16())
								 {
									// if(admForm.getDegsubid_16()!=null && !admForm.getDegsubid_16().equalsIgnoreCase(""))
										admForm.setDegsubid_16(String.valueOf(admSubjectMarkForRank.getAdmSubjectForRankTo().getId()));
									// if(admForm.getDegmaxmark_16()!=null && !admForm.getDegmaxmark_16().equalsIgnoreCase(""))
										admForm.setDegmaxmark_16(admSubjectMarkForRank.getMaxmark());
									// if(admForm.getDegobtainedmark_16()!=null && !admForm.getDegobtainedmark_16().equalsIgnoreCase(""))
										admForm.setDegobtainedmark_16(admSubjectMarkForRank.getObtainedmark());
										admForm.setAdmsubmarkid_16(String.valueOf(admSubjectMarkForRank.getId()));
									    admForm.setAdmsubgrpname_16(admSubjectMarkForRank.getAdmSubjectForRankTo().getGroupname());
									    
									    totalobt=totalobt+Double.valueOf(admSubjectMarkForRank.getObtainedmark());
									    totalmax=totalmax+Double.valueOf(admSubjectMarkForRank.getMaxmark());
									    admForm.setAllot_16(true);
									 
								 }
								
								 }// Vocational sub close
						*/
						}// while close
						
						
							
						}
						
						
					Map<Integer,String> admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Core);
					admForm.setAdmCoreMap(admCoreMap);
					Map<Integer,String> admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Compl);
					admForm.setAdmComplMap(admComplMap);
					Map<Integer,String> admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Common);
					admForm.setAdmCommonMap(admCommonMap);
					Map<Integer,String> admOpenMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Main);
					admForm.setAdmMainMap(admOpenMap);
					Map<Integer,String> admVocMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Sub);
					admForm.setAdmSubMap(admVocMap);
						
					}// sublist if close
					
				
					admForm.setDegtotalmaxmarkother(String.valueOf(totalmax));
					admForm.setDegtotalobtainedmarkother(String.valueOf(totalobt));


		

				// raghu added from old code
				Map<Integer,String> subjectMap = ApplicationEditHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
				admForm.setDetailedSubjectsMap(subjectMap);

				List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
				if(quals!=null )
				{
					Iterator<EdnQualificationTO> qualItr=quals.iterator();
					while (qualItr.hasNext())
					{
						EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
						if(qualTO.getId()==index)
						{
							if(qualTO.getDetailmark()!=null)
								admForm.setDetailMark(qualTO.getDetailmark());
							else
							{
								CandidateMarkTO markTo=new CandidateMarkTO();
								ApplicationEditHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
								admForm.setDetailMark(markTo);
							}
						}
					}
				}

			}// new record close
				else{
					List<AdmSubjectMarkForRankTO> admsubList=new LinkedList<AdmSubjectMarkForRankTO>();
					
					for(int i=0;i<14;i++){
						AdmSubjectMarkForRankTO admSubjectMarkForRankTO=new AdmSubjectMarkForRankTO();
						
						admsubList.add(admSubjectMarkForRankTO);
					}
					admForm.setAdmsubMarkListUG(admsubList);
					
					
					
					
					Map<Integer,String> admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Core);
					admForm.setAdmCoreMap(admCoreMap);
					Map<Integer,String> admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Compl);
					admForm.setAdmComplMap(admComplMap);
					Map<Integer,String> admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Common);
					admForm.setAdmCommonMap(admCommonMap);
					Map<Integer,String> admOpenMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Main);
					admForm.setAdmMainMap(admOpenMap);
					Map<Integer,String> admVocMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationTO1.getDocName(),Sub);
					admForm.setAdmSubMap(admVocMap);
			
					Map<Integer,String> subjectMap = AdmissionFormHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
					admForm.setDetailedSubjectsMap(subjectMap);
					
					
					
					if(admForm.getDetailMark()==null){
						CandidateMarkTO markTo=new CandidateMarkTO();
						AdmissionFormHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
						admForm.setDetailMark(markTo);
					}
			}
			}// deg check close
			}// while close
		
		
		//raghu setting first time it excutes marks entry
			session.setAttribute(admForm.getApplicationNumber(), admForm.getApplicationNumber());
		
		} catch (Exception e) {
			log.error("error initDetailMarkConfirmPage...",e);
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
		log.info("exit initDetailMarkConfirmPage...");
	
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORUG_FOREDIT);
		}
	
	public ActionForward submitDetailMarkConfirmfordegEdit(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.info("enter submitDetailMarkConfirm page...");
			ApplicationEditForm admForm = (ApplicationEditForm) form;
			HttpSession session=request.getSession(false);
			
			try {
				
					String indexString = null;
					
					if (session != null)
						indexString = (String) session.getAttribute("editcountID");
					
					int detailMarkIndex = -1;
					
					if (indexString != null)
						detailMarkIndex = Integer.parseInt(indexString);
					
					CandidateMarkTO detailmark = admForm.getDetailMark();
					
					Map<Integer,String> admcoremap=admForm.getAdmCoreMap();
					Map<Integer,String> admcomplmap=admForm.getAdmComplMap();
					Map<Integer,String> admcommmap=admForm.getAdmCommonMap();
					Map<Integer,String> admmainmap=admForm.getAdmMainMap();
					Map<Integer,String> admsubmap=admForm.getAdmSubMap();
					Map<Integer,String> degGroupCoreMap=admForm.getDegGroupCoreMap();
					Map<Integer,String> degGroupCommonMap=admForm.getDegGroupCommonMap();
					Map<Integer,String> degGroupComplMap=admForm.getDegGroupComplMap();
					Map<Integer,String> degGroupOpenMap=admForm.getDegGroupOpenMap();
					Map<Integer,String> degGroupSubMap=admForm.getDegGroupSubMap();
					List<AdmSubjectMarkForRankTO>  admsubList=admForm.getAdmsubMarkListUG();
					List<AdmSubjectMarkForRankTO> admSubList= new ArrayList<AdmSubjectMarkForRankTO>();
					
					
						//if(detailmark.getSubject1()==null){
							if(admForm.getPatternofStudy().equalsIgnoreCase("CBCSS")){
								
								if(admForm.getDegsubid_0()!=null && !admForm.getDegsubid_0().equalsIgnoreCase("")){ 
									//	&& admForm.getDegobtainedmark_0()!=null && !admForm.getDegobtainedmark_0().equalsIgnoreCase("") &&
									//admForm.getDegmaxmark_0()!=null && !admForm.getDegmaxmark_0().equalsIgnoreCase("")){
								if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_0()))){
									String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_0()));
									detailmark.setSubject1(s);
									admForm.setAdmsubname_0(s);
								}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_0()))){
									String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_0()));
									detailmark.setSubject1(s);
									admForm.setAdmsubname_0(s);
								}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_0()))){
									String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_0()));
									detailmark.setSubject1(s);
									admForm.setAdmsubname_0(s);
								}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_0()))){
									String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_0()));
									detailmark.setSubject1(s);
									admForm.setAdmsubname_0(s);
								}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_0()))){
									String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_0()));
									detailmark.setSubject1(s);
									admForm.setAdmsubname_0(s);
								}
								
								if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_0())))
									admForm.setAdmsubgrpname_0(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_0())));
								if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_0())))
									admForm.setAdmsubgrpname_0(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_0())));
								if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_0())))
									admForm.setAdmsubgrpname_0(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_0())));
								if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_0())))
									admForm.setAdmsubgrpname_0(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_0())));
								if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_0())))
									admForm.setAdmsubgrpname_0(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_0())));
								
								
							}else{
								detailmark.setSubject1("");
							}
							
								
								if(admForm.getDegobtainedmark_0()!=null && !admForm.getDegobtainedmark_0().equalsIgnoreCase("")){
									detailmark.setSubject1ObtainedMarks(admForm.getDegobtainedmark_0());
								}else{
									detailmark.setSubject1ObtainedMarks("");
								}
								if(admForm.getDegmaxmark_0()!=null && !admForm.getDegmaxmark_0().equalsIgnoreCase("")){
									detailmark.setSubject1TotalMarks(admForm.getDegmaxmark_0());
								}else{
									detailmark.setSubject1TotalMarks("");
								}
								if(admForm.getDegmaxcgpa_0()!=null && !admForm.getDegmaxcgpa_0().equalsIgnoreCase("")){
									detailmark.setSubject1Credit(admForm.getDegmaxcgpa_0());
								}else{
									detailmark.setSubject1Credit("");
								}
								
								
								
						
								if(admForm.getDegsubid_1()!=null && !admForm.getDegsubid_1().equalsIgnoreCase("")){
										//&& admForm.getDegobtainedmark_1()!=null && !admForm.getDegobtainedmark_1().equalsIgnoreCase("") &&
										//admForm.getDegmaxmark_1()!=null && !admForm.getDegmaxmark_1().equalsIgnoreCase("")){
									if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_1()))){
										String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_1()));
										detailmark.setSubject2(s);
										admForm.setAdmsubname_1(s);
									}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_1()))){
										String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_1()));
										detailmark.setSubject2(s);
										admForm.setAdmsubname_1(s);
									}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_1()))){
										String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_1()));
										detailmark.setSubject2(s);
										admForm.setAdmsubname_1(s);
									}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_1()))){
										String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_1()));
										detailmark.setSubject2(s);
										admForm.setAdmsubname_1(s);
									}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_1()))){
										String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_1()));
										detailmark.setSubject2(s);
										admForm.setAdmsubname_1(s);
									}
									
									
									if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_1())))
										admForm.setAdmsubgrpname_1(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_1())));
									if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_1())))
										admForm.setAdmsubgrpname_1(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_1())));
									if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_1())))
										admForm.setAdmsubgrpname_1(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_1())));
									if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_1())))
										admForm.setAdmsubgrpname_1(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_1())));
									if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_1())))
										admForm.setAdmsubgrpname_1(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_1())));
									
									
								}else{
									detailmark.setSubject2("");
								}
								
								
								if(admForm.getDegobtainedmark_1()!=null && !admForm.getDegobtainedmark_1().equalsIgnoreCase("")){
									detailmark.setSubject2ObtainedMarks(admForm.getDegobtainedmark_1());
								}else{
									detailmark.setSubject2ObtainedMarks("");
								}
								
								if(admForm.getDegmaxmark_1()!=null && !admForm.getDegmaxmark_1().equalsIgnoreCase("")){
									detailmark.setSubject2TotalMarks(admForm.getDegmaxmark_1());
								}else{
									detailmark.setSubject2TotalMarks("");
								}
								
								if(admForm.getDegmaxcgpa_1()!=null && !admForm.getDegmaxcgpa_1().equalsIgnoreCase("")){
									detailmark.setSubject2Credit(admForm.getDegmaxcgpa_1());
								}else{
									detailmark.setSubject2Credit("");
								}
								
								
								
									if(admForm.getDegsubid_2()!=null && !admForm.getDegsubid_2().equalsIgnoreCase("") ){
										//&& admForm.getDegobtainedmark_2()!=null && !admForm.getDegobtainedmark_2().equalsIgnoreCase("") &&
									//	admForm.getDegmaxmark_2()!=null && !admForm.getDegmaxmark_2().equalsIgnoreCase("")){
										if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_2()))){
											String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_2()));
											detailmark.setSubject3(s);
											admForm.setAdmsubname_2(s);
										}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_2()))){
											String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_2()));
											detailmark.setSubject3(s);
											admForm.setAdmsubname_2(s);
										}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_2()))){
											String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_2()));
											detailmark.setSubject3(s);
											admForm.setAdmsubname_2(s);
										}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_2()))){
											String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_2()));
											detailmark.setSubject3(s);
											admForm.setAdmsubname_2(s);
										}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_2()))){
											String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_2()));
											detailmark.setSubject3(s);
											admForm.setAdmsubname_2(s);
										}
										
										
										if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_2())))
											admForm.setAdmsubgrpname_2(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_2())));
										if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_2())))
											admForm.setAdmsubgrpname_2(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_2())));
										if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_2())))
											admForm.setAdmsubgrpname_2(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_2())));
										if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_2())))
											admForm.setAdmsubgrpname_2(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_2())));
										if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_2())))
											admForm.setAdmsubgrpname_2(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_2())));
										
										
									}else{
										detailmark.setSubject3("");
									}
								
									
									if(admForm.getDegobtainedmark_2()!=null && !admForm.getDegobtainedmark_2().equalsIgnoreCase("")){
										detailmark.setSubject3ObtainedMarks(admForm.getDegobtainedmark_2());
									}else{
										detailmark.setSubject3ObtainedMarks("");
									}
									
									if(admForm.getDegmaxmark_2()!=null && !admForm.getDegmaxmark_2().equalsIgnoreCase("")){
										detailmark.setSubject3TotalMarks(admForm.getDegmaxmark_2());
									}else{
										detailmark.setSubject3TotalMarks("");
									}
									if(admForm.getDegmaxcgpa_2()!=null && !admForm.getDegmaxcgpa_2().equalsIgnoreCase("")){
										detailmark.setSubject3Credit(admForm.getDegmaxcgpa_2());
									}else{
										detailmark.setSubject3Credit("");
									}
									
									
										if(admForm.getDegsubid_3()!=null && !admForm.getDegsubid_3().equalsIgnoreCase("") ){
												//&& admForm.getDegobtainedmark_3()!=null && !admForm.getDegobtainedmark_3().equalsIgnoreCase("") &&
												//admForm.getDegmaxmark_3()!=null && !admForm.getDegmaxmark_3().equalsIgnoreCase("")){
											if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_3()))){
												String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_3()));
												detailmark.setSubject4(s);
												admForm.setAdmsubname_3(s);
											}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_3()))){
												String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_3()));
												detailmark.setSubject4(s);
												admForm.setAdmsubname_3(s);
											}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_3()))){
												String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_3()));
												detailmark.setSubject4(s);
												admForm.setAdmsubname_3(s);
											}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_3()))){
												String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_3()));
												detailmark.setSubject4(s);
												admForm.setAdmsubname_3(s);
											}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_3()))){
												String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_3()));
												detailmark.setSubject4(s);
												admForm.setAdmsubname_3(s);
											}
											
											
											if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_3())))
												admForm.setAdmsubgrpname_3(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_3())));
											if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_3())))
												admForm.setAdmsubgrpname_3(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_3())));
											if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_3())))
												admForm.setAdmsubgrpname_3(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_3())));
											if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_3())))
												admForm.setAdmsubgrpname_3(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_3())));
											if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_3())))
												admForm.setAdmsubgrpname_3(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_3())));
											
											
										}else{
											detailmark.setSubject4("");
										}
									
										
										if(admForm.getDegobtainedmark_3()!=null && !admForm.getDegobtainedmark_3().equalsIgnoreCase("")){
											detailmark.setSubject4ObtainedMarks(admForm.getDegobtainedmark_3());
										}else{
											detailmark.setSubject4ObtainedMarks("");
										}
										
										if(admForm.getDegmaxmark_3()!=null && !admForm.getDegmaxmark_3().equalsIgnoreCase("")){
											detailmark.setSubject4TotalMarks(admForm.getDegmaxmark_3());
										}else{
											detailmark.setSubject4TotalMarks("");
										}
										if(admForm.getDegmaxcgpa_3()!=null && !admForm.getDegmaxcgpa_3().equalsIgnoreCase("")){
											detailmark.setSubject4Credit(admForm.getDegmaxcgpa_3());
										}else{
											detailmark.setSubject4Credit("");
										}
										
										
										
											if(admForm.getDegsubid_4()!=null && !admForm.getDegsubid_4().equalsIgnoreCase("") ){
													//&& admForm.getDegobtainedmark_4()!=null && !admForm.getDegobtainedmark_4().equalsIgnoreCase("") &&
													//admForm.getDegmaxmark_4()!=null && !admForm.getDegmaxmark_4().equalsIgnoreCase("")){
												if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_4()))){
													String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_4()));
													detailmark.setSubject5(s);
													admForm.setAdmsubname_4(s);
												}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_4()))){
													String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_4()));
													detailmark.setSubject5(s);
													admForm.setAdmsubname_4(s);
												}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_4()))){
													String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_4()));
													detailmark.setSubject5(s);
													admForm.setAdmsubname_4(s);
												}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_4()))){
													String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_4()));
													detailmark.setSubject5(s);
													admForm.setAdmsubname_4(s);
												}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_4()))){
													String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_4()));
													detailmark.setSubject5(s);
													admForm.setAdmsubname_4(s);
												}
												
												
												if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_4())))
													admForm.setAdmsubgrpname_4(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_4())));
												if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_4())))
													admForm.setAdmsubgrpname_4(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_4())));
												if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_4())))
													admForm.setAdmsubgrpname_4(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_4())));
												if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_4())))
													admForm.setAdmsubgrpname_4(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_4())));
												if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_4())))
													admForm.setAdmsubgrpname_4(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_4())));
												
												
											}else{
												detailmark.setSubject5("");
											}
										
											
											if(admForm.getDegobtainedmark_4()!=null && !admForm.getDegobtainedmark_4().equalsIgnoreCase("")){
												detailmark.setSubject5ObtainedMarks(admForm.getDegobtainedmark_4());
											}else{
												detailmark.setSubject5ObtainedMarks("");
											}
											
											if(admForm.getDegmaxmark_4()!=null && !admForm.getDegmaxmark_4().equalsIgnoreCase("")){
												detailmark.setSubject5TotalMarks(admForm.getDegmaxmark_4());
											}else{
												detailmark.setSubject5TotalMarks("");
											}
											if(admForm.getDegmaxcgpa_4()!=null && !admForm.getDegmaxcgpa_4().equalsIgnoreCase("")){
												detailmark.setSubject5Credit(admForm.getDegmaxcgpa_4());
											}else{
												detailmark.setSubject5Credit("");
											}
											
											
												if(admForm.getDegsubid_5()!=null && !admForm.getDegsubid_5().equalsIgnoreCase("") ){
														//&& admForm.getDegobtainedmark_5()!=null && !admForm.getDegobtainedmark_5().equalsIgnoreCase("") &&
														//admForm.getDegmaxmark_5()!=null && !admForm.getDegmaxmark_5().equalsIgnoreCase("")){
													if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_5()))){
														String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_5()));
														detailmark.setSubject6(s);
														admForm.setAdmsubname_5(s);
													}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_5()))){
														String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_5()));
														detailmark.setSubject6(s);
														admForm.setAdmsubname_5(s);
													}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_5()))){
														String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_5()));
														detailmark.setSubject6(s);
														admForm.setAdmsubname_5(s);
													}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_5()))){
														String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_5()));
														detailmark.setSubject6(s);
														admForm.setAdmsubname_5(s);
													}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_5()))){
														String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_5()));
														detailmark.setSubject6(s);
														admForm.setAdmsubname_5(s);
													}
													
													
													if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_5())))
														admForm.setAdmsubgrpname_5(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_5())));
													if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_5())))
														admForm.setAdmsubgrpname_5(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_5())));
													if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_5())))
														admForm.setAdmsubgrpname_5(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_5())));
													if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_5())))
														admForm.setAdmsubgrpname_5(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_5())));
													if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_5())))
														admForm.setAdmsubgrpname_5(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_5())));
													
													
												}else{
													detailmark.setSubject6("");
												}
												
												
												if(admForm.getDegobtainedmark_5()!=null && !admForm.getDegobtainedmark_5().equalsIgnoreCase("")){
													detailmark.setSubject6ObtainedMarks(admForm.getDegobtainedmark_5());
												}else{
													detailmark.setSubject6ObtainedMarks("");
												}
												
												if(admForm.getDegmaxmark_5()!=null && !admForm.getDegmaxmark_5().equalsIgnoreCase("")){
													detailmark.setSubject6TotalMarks(admForm.getDegmaxmark_5());
												}else{
													detailmark.setSubject6TotalMarks("");
												}
												
												if(admForm.getDegmaxcgpa_5()!=null && !admForm.getDegmaxcgpa_5().equalsIgnoreCase("")){
													detailmark.setSubject6Credit(admForm.getDegmaxcgpa_5());
												}else{
													detailmark.setSubject6Credit("");
												}
												
												

												if(admForm.getDegsubid_6()!=null && !admForm.getDegsubid_6().equalsIgnoreCase("")){
														//&& admForm.getDegobtainedmark_6()!=null && !admForm.getDegobtainedmark_6().equalsIgnoreCase("") &&
														//admForm.getDegmaxmark_6()!=null && !admForm.getDegmaxmark_6().equalsIgnoreCase("")){
													if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_6()))){
														String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_6()));
														detailmark.setSubject7(s);
														admForm.setAdmsubname_6(s);
													}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_6()))){
														String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_6()));
														detailmark.setSubject7(s);
														admForm.setAdmsubname_6(s);
													}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_6()))){
														String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_6()));
														detailmark.setSubject7(s);
														admForm.setAdmsubname_6(s);
													}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_6()))){
														String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_6()));
														detailmark.setSubject7(s);
														admForm.setAdmsubname_6(s);
													}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_6()))){
														String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_6()));
														detailmark.setSubject7(s);
														admForm.setAdmsubname_6(s);
													}
													
													
													if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_6())))
														admForm.setAdmsubgrpname_6(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_6())));
													if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_6())))
														admForm.setAdmsubgrpname_6(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_6())));
													if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_6())))
														admForm.setAdmsubgrpname_6(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_6())));
													if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_6())))
														admForm.setAdmsubgrpname_6(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_6())));
													if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_6())))
														admForm.setAdmsubgrpname_6(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_6())));
													
													
												}else{
													detailmark.setSubject7("");
												}
												
											
												if(admForm.getDegobtainedmark_6()!=null && !admForm.getDegobtainedmark_6().equalsIgnoreCase("")){
													detailmark.setSubject7ObtainedMarks(admForm.getDegobtainedmark_6());
												}else{
													detailmark.setSubject7ObtainedMarks("");
												}
												
												if(admForm.getDegmaxmark_6()!=null && !admForm.getDegmaxmark_6().equalsIgnoreCase("")){
													detailmark.setSubject7TotalMarks(admForm.getDegmaxmark_6());
												}else{
													detailmark.setSubject7TotalMarks("");
												}
												if(admForm.getDegmaxcgpa_6()!=null && !admForm.getDegmaxcgpa_6().equalsIgnoreCase("")){
													detailmark.setSubject7Credit(admForm.getDegmaxcgpa_6());
												}else{
													detailmark.setSubject7Credit("");
												}
												
												


												if(admForm.getDegsubid_14()!=null && !admForm.getDegsubid_14().equalsIgnoreCase("") ){
														//&& admForm.getDegobtainedmark_14()!=null && !admForm.getDegobtainedmark_14().equalsIgnoreCase("") &&
														//admForm.getDegmaxmark_14()!=null && !admForm.getDegmaxmark_14().equalsIgnoreCase("")){
													if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_14()))){
														String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_14()));
														detailmark.setSubject15(s);
														admForm.setAdmsubname_14(s);
													}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_14()))){
														String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_14()));
														detailmark.setSubject15(s);
														admForm.setAdmsubname_14(s);
													}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_14()))){
														String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_14()));
														detailmark.setSubject15(s);
														admForm.setAdmsubname_14(s);
													}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_14()))){
														String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_14()));
														detailmark.setSubject15(s);
														admForm.setAdmsubname_14(s);
													}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_14()))){
														String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_14()));
														detailmark.setSubject15(s);
														admForm.setAdmsubname_14(s);
													}
													
													
													if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_14())))
														admForm.setAdmsubgrpname_14(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_14())));
													if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_14())))
														admForm.setAdmsubgrpname_14(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_14())));
													if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_14())))
														admForm.setAdmsubgrpname_14(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_14())));
													if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_14())))
														admForm.setAdmsubgrpname_14(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_14())));
													if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_14())))
														admForm.setAdmsubgrpname_14(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_14())));
													
													
												}else{
													detailmark.setSubject15("");
												}
												
												
												if(admForm.getDegobtainedmark_14()!=null && !admForm.getDegobtainedmark_14().equalsIgnoreCase("")){
													detailmark.setSubject15ObtainedMarks(admForm.getDegobtainedmark_14());
												}else{
													detailmark.setSubject15ObtainedMarks("");
												}
												
												if(admForm.getDegmaxmark_14()!=null && !admForm.getDegmaxmark_14().equalsIgnoreCase("")){
													detailmark.setSubject15TotalMarks(admForm.getDegmaxmark_14());
												}else{
													detailmark.setSubject15TotalMarks("");
												}
												if(admForm.getDegmaxcgpa_14()!=null && !admForm.getDegmaxcgpa_14().equalsIgnoreCase("")){
													detailmark.setSubject15Credit(admForm.getDegmaxcgpa_14());
												}else{
													detailmark.setSubject15Credit("");
												}
												
												
												
												
												/*if(admForm.getDegsubid_15()!=null && !admForm.getDegsubid_15().equalsIgnoreCase("") ){
													//&& admForm.getDegobtainedmark_15()!=null && !admForm.getDegobtainedmark_15().equalsIgnoreCase("") &&
													//admForm.getDegmaxmark_15()!=null && !admForm.getDegmaxmark_15().equalsIgnoreCase("")){
												if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_15()))){
													String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_15()));
													detailmark.setSubject16(s);
													admForm.setAdmsubname_15(s);
												}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_15()))){
													String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_15()));
													detailmark.setSubject16(s);
													admForm.setAdmsubname_15(s);
												}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_15()))){
													String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_15()));
													detailmark.setSubject16(s);
													admForm.setAdmsubname_15(s);
												}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_15()))){
													String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_15()));
													detailmark.setSubject16(s);
													admForm.setAdmsubname_15(s);
												}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_15()))){
													String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_15()));
													detailmark.setSubject16(s);
													admForm.setAdmsubname_15(s);
												}
												
												
												if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_15())))
													admForm.setAdmsubgrpname_15(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_15())));
												if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_15())))
													admForm.setAdmsubgrpname_15(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_15())));
												if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_15())))
													admForm.setAdmsubgrpname_15(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_15())));
												if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_15())))
													admForm.setAdmsubgrpname_15(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_15())));
												if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_15())))
													admForm.setAdmsubgrpname_15(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_15())));
												
											}else{
												detailmark.setSubject16("");
											}
											
											
											if(admForm.getDegobtainedmark_15()!=null && !admForm.getDegobtainedmark_15().equalsIgnoreCase("")){
												detailmark.setSubject16ObtainedMarks(admForm.getDegobtainedmark_15());
											}else{
												detailmark.setSubject16ObtainedMarks("");
											}
											
											if(admForm.getDegmaxmark_15()!=null && !admForm.getDegmaxmark_15().equalsIgnoreCase("")){
												detailmark.setSubject16TotalMarks(admForm.getDegmaxmark_15());
											}else{
												detailmark.setSubject16TotalMarks("");
											}
											if(admForm.getDegmaxcgpa_15()!=null && !admForm.getDegmaxcgpa_15().equalsIgnoreCase("")){
												detailmark.setSubject16Credit(admForm.getDegmaxcgpa_15());
											}else{
												detailmark.setSubject16Credit("");
											}
											*/
											
												ActionMessages errors = new ActionMessages();
												
												
												//raghu write newly
												
												if(admForm.getDegtotalmaxmark()!=null && admForm.getDegtotalmaxmark().equalsIgnoreCase("") ){
													if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
														ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
														errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
														}
														
												}
												
												if(admForm.getDegtotalmaxmark()!=null && StringUtils.isEmpty(admForm.getDegtotalmaxmark()) && !CommonUtil.isValidDecimal(admForm.getDegtotalmaxmark())){
													if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
														ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
														errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
														}
														
												}
												
												//raghu write newly
												if(admForm.getDegtotalmaxmark()!=null &&  !CommonUtil.isValidDecimal(admForm.getDegtotalmaxmark())){
													if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
														ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
														errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
														}
														
												}
												
												if(admForm.getDegtotalobtainedmark()!=null && admForm.getDegtotalobtainedmark().equalsIgnoreCase("")) {
													if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
														ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
														errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
														}
														
												}
												
												
												if(admForm.getDegtotalobtainedmark()!=null && StringUtils.isEmpty(admForm.getDegtotalobtainedmark()) && !CommonUtil.isValidDecimal(admForm.getDegtotalobtainedmark())) {
													if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
														ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
														errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
														}
														
												}
												
												if(admForm.getDegtotalobtainedmark()!=null &&  !CommonUtil.isValidDecimal(admForm.getDegtotalobtainedmark())) {
													if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
														ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
														errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
														}
														
												}
												
												
												if (errors != null && !errors.isEmpty()) {
													saveErrors(request, errors);
													
													return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORUG_FOREDIT);
												}
												
												
												
												if(admForm.getDegtotalobtainedmark()!=null && !admForm.getDegtotalobtainedmark().equalsIgnoreCase("")){
													detailmark.setTotalObtainedMarks(admForm.getDegtotalobtainedmark());
												}else{
													detailmark.setTotalObtainedMarks("");
													
												}
												if(admForm.getDegtotalmaxmark()!=null && !admForm.getDegtotalmaxmark().equalsIgnoreCase("")){
													detailmark.setTotalMarks(admForm.getDegtotalmaxmark());
												}else{
													detailmark.setTotalMarks("");
													
												}
												
												
												
												
												//raghu added newly
												validateMarksForUG(detailmark, errors, admForm.getPatternofStudy());
												if (errors != null && !errors.isEmpty()) {
														saveErrors(request, errors);
														
														return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORUG_FOREDIT);
													}
												
												
												
												
												

																	
												
												
												
												
												
												
												
												
												
												
							int i=0;
							Iterator<AdmSubjectMarkForRankTO> itr=admsubList.iterator();
							
							for(i=0;i<8;){
								AdmSubjectMarkForRankTO admSubjectMarkForRankTO=new AdmSubjectMarkForRankTO() ;
								
								//AdmSubjectMarkForRankTO admSubjectMarkForRankTO=(AdmSubjectMarkForRankTO) itr.next();
								if(i==0){
								if(admForm.getDegsubid_0()!=null && !admForm.getDegsubid_0().equalsIgnoreCase("") && admForm.getDegobtainedmark_0()!=null && !admForm.getDegobtainedmark_0().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_0()!=null && !admForm.getDegmaxmark_0().equalsIgnoreCase("") && admForm.getDegmaxcgpa_0()!=null && !admForm.getDegmaxcgpa_0().equalsIgnoreCase("")){
									
									
								admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_0());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_0());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_0());
								admSubjectMarkForRankTO.setCredit(admForm.getDegmaxcgpa_0());
								
								if(admForm.getAdmsubmarkid_0()!=null && !admForm.getAdmsubmarkid_0().equalsIgnoreCase(""))
								admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_0()));
								admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_0());
								
								
								AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
								admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_0()));
								admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_0());
								admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_0());
								admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
								
								
								
							}
								i++;
								}
								
								else if(i==1){
								 if(admForm.getDegsubid_1()!=null && !admForm.getDegsubid_1().equalsIgnoreCase("") && admForm.getDegobtainedmark_1()!=null && !admForm.getDegobtainedmark_1().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_1()!=null && !admForm.getDegmaxmark_1().equalsIgnoreCase("") && admForm.getDegmaxcgpa_1()!=null && !admForm.getDegmaxcgpa_1().equalsIgnoreCase("")){

								
								admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_1());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_1());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_1());
								admSubjectMarkForRankTO.setCredit(admForm.getDegmaxcgpa_1());
								
								if(admForm.getAdmsubmarkid_1()!=null && !admForm.getAdmsubmarkid_1().equalsIgnoreCase(""))
								admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_1()));
								admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_1());
								
								
								AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
								admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_1()));
								admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_1());
								admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_1());
								admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
								
								}
								 i++;
								}
								
								 else if(i==2){
								if(admForm.getDegsubid_2()!=null && !admForm.getDegsubid_2().equalsIgnoreCase("") && admForm.getDegobtainedmark_2()!=null && !admForm.getDegobtainedmark_2().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_2()!=null && !admForm.getDegmaxmark_2().equalsIgnoreCase("") && admForm.getDegmaxcgpa_2()!=null && !admForm.getDegmaxcgpa_2().equalsIgnoreCase("")){

									
									
								admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_2());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_2());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_2());
								admSubjectMarkForRankTO.setCredit(admForm.getDegmaxcgpa_2());
								
								if(admForm.getAdmsubmarkid_2()!=null && !admForm.getAdmsubmarkid_2().equalsIgnoreCase(""))
								admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_2()));
								admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_2());
								
								
								AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
								admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_2()));
								admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_2());
								admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_2());
								admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
								}
								i++;
									}
									
								else if(i==3){
								if(admForm.getDegsubid_3()!=null && !admForm.getDegsubid_3().equalsIgnoreCase("") && admForm.getDegobtainedmark_3()!=null && !admForm.getDegobtainedmark_3().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_3()!=null && !admForm.getDegmaxmark_3().equalsIgnoreCase("") && admForm.getDegmaxcgpa_3()!=null && !admForm.getDegmaxcgpa_3().equalsIgnoreCase("")){

									
								admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_3());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_3());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_3());
								admSubjectMarkForRankTO.setCredit(admForm.getDegmaxcgpa_3());
								
								if(admForm.getAdmsubmarkid_3()!=null && !admForm.getAdmsubmarkid_3().equalsIgnoreCase(""))
								admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_3()));
								admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_3());
								
								
								AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
								admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_3()));
								admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_3());
								admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_3());
								admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
								}
								i++;
								}
								
								else if(i==4){
								if(admForm.getDegsubid_4()!=null && !admForm.getDegsubid_4().equalsIgnoreCase("") && admForm.getDegobtainedmark_4()!=null && !admForm.getDegobtainedmark_4().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_4()!=null && !admForm.getDegmaxmark_4().equalsIgnoreCase("") && admForm.getDegmaxcgpa_4()!=null && !admForm.getDegmaxcgpa_4().equalsIgnoreCase("")){

									
									
								admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_4());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_4());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_4());
								admSubjectMarkForRankTO.setCredit(admForm.getDegmaxcgpa_4());
								
								if(admForm.getAdmsubmarkid_4()!=null && !admForm.getAdmsubmarkid_4().equalsIgnoreCase(""))
								admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_4()));
								admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_4());
								
								
								AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
								admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_4()));
								admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_4());
								admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_4());
								admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
								}
								i++;
								}
								
								else if(i==5){
								if(admForm.getDegsubid_5()!=null && !admForm.getDegsubid_5().equalsIgnoreCase("") && admForm.getDegobtainedmark_5()!=null && !admForm.getDegobtainedmark_5().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_5()!=null && !admForm.getDegmaxmark_5().equalsIgnoreCase("") && admForm.getDegmaxcgpa_5()!=null && !admForm.getDegmaxcgpa_5().equalsIgnoreCase("")){

								
									
								admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_5());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_5());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_5());
								admSubjectMarkForRankTO.setCredit(admForm.getDegmaxcgpa_5());
								
								if(admForm.getAdmsubmarkid_5()!=null && !admForm.getAdmsubmarkid_5().equalsIgnoreCase(""))
								admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_5()));
								admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_5());
								
								
								AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
								admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_5()));
								admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_5());
								admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_5());
								admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
								}
								i++;
								}
								
								else if(i==6){
								if(admForm.getDegsubid_6()!=null && !admForm.getDegsubid_6().equalsIgnoreCase("") && admForm.getDegobtainedmark_6()!=null && !admForm.getDegobtainedmark_6().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_6()!=null && !admForm.getDegmaxmark_6().equalsIgnoreCase("") && admForm.getDegmaxcgpa_6()!=null && !admForm.getDegmaxcgpa_6().equalsIgnoreCase("")){

									
									
								admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_6());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_6());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_6());
								admSubjectMarkForRankTO.setCredit(admForm.getDegmaxcgpa_6());
								
								if(admForm.getAdmsubmarkid_6()!=null && !admForm.getAdmsubmarkid_6().equalsIgnoreCase(""))
								admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_6()));
								admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_6());
								
								
								AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
								admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_6()));
								admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_6());
								admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_6());
								admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
								}
								i++;
								}
								
								else if(i==7){
									if(admForm.getDegsubid_14()!=null && !admForm.getDegsubid_14().equalsIgnoreCase("") && admForm.getDegobtainedmark_14()!=null && !admForm.getDegobtainedmark_14().equalsIgnoreCase("") &&
											admForm.getDegmaxmark_14()!=null && !admForm.getDegmaxmark_14().equalsIgnoreCase("") && admForm.getDegmaxcgpa_14()!=null && !admForm.getDegmaxcgpa_14().equalsIgnoreCase("")){

										
									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_14());
									admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_14());
									admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_14());
									admSubjectMarkForRankTO.setCredit(admForm.getDegmaxcgpa_14());
									
									if(admForm.getAdmsubmarkid_14()!=null && !admForm.getAdmsubmarkid_14().equalsIgnoreCase(""))
									admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_14()));
									admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_14());
									
									
									AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
									admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_14()));
									admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_14());
									admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_14());
									admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
									}
									i++;
									}
								/*else if(i==8){
									if(admForm.getDegsubid_15()!=null && !admForm.getDegsubid_15().equalsIgnoreCase("") && admForm.getDegobtainedmark_15()!=null && !admForm.getDegobtainedmark_15().equalsIgnoreCase("") &&
											admForm.getDegmaxmark_15()!=null && !admForm.getDegmaxmark_15().equalsIgnoreCase("") && admForm.getDegmaxcgpa_15()!=null && !admForm.getDegmaxcgpa_15().equalsIgnoreCase("")){

										
									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_15());
									admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_15());
									admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_15());
									admSubjectMarkForRankTO.setCredit(admForm.getDegmaxcgpa_15());
									
									if(admForm.getAdmsubmarkid_15()!=null && !admForm.getAdmsubmarkid_15().equalsIgnoreCase(""))
									admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_15()));
									admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_15());
									
									
									AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
									admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_15()));
									admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_15());
									admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_15());
									admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
									}
									i++;
									}
							*/									
								admSubList.add(admSubjectMarkForRankTO);
							}
							
							admForm.setAdmsubMarkListUG(admSubList);
							}
							else{
								
								
								
								
								
								
								
								if(admForm.getDegsubid_7()!=null && !admForm.getDegsubid_7().equalsIgnoreCase("") ){
										//&& admForm.getDegobtainedmark_7()!=null && !admForm.getDegobtainedmark_7().equalsIgnoreCase("") &&
										//admForm.getDegmaxmark_7()!=null && !admForm.getDegmaxmark_7().equalsIgnoreCase("")){
									if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_7()))){
										String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_7()));
										detailmark.setSubject8(s);
										admForm.setAdmsubname_7(s);
									}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_7()))){
										String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_7()));
										detailmark.setSubject8(s);
										admForm.setAdmsubname_7(s);
									}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_7()))){
										String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_7()));
										detailmark.setSubject8(s);
										admForm.setAdmsubname_7(s);
									}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_7()))){
										String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_7()));
										detailmark.setSubject8(s);
										admForm.setAdmsubname_7(s);
									}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_7()))){
										String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_7()));
										detailmark.setSubject8(s);
										admForm.setAdmsubname_7(s);
									}
									
									if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_7())))
										admForm.setAdmsubgrpname_7(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_7())));
									if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_7())))
										admForm.setAdmsubgrpname_7(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_7())));
									if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_7())))
										admForm.setAdmsubgrpname_7(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_7())));
									if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_7())))
										admForm.setAdmsubgrpname_7(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_7())));
									if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_7())))
										admForm.setAdmsubgrpname_7(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_7())));
									
									
								}else{
									detailmark.setSubject8("");
								}
			
								
								if(admForm.getDegobtainedmark_7()!=null && !admForm.getDegobtainedmark_7().equalsIgnoreCase("")){
									detailmark.setSubject8ObtainedMarks(admForm.getDegobtainedmark_7());
								}else{
									detailmark.setSubject8ObtainedMarks("");
								}
								
								if(admForm.getDegmaxmark_7()!=null && !admForm.getDegmaxmark_7().equalsIgnoreCase("")){
									detailmark.setSubject8TotalMarks(admForm.getDegmaxmark_7());
								}else{
									detailmark.setSubject8TotalMarks("");
								}
			
								
								if(admForm.getDegsubid_8()!=null && !admForm.getDegsubid_8().equalsIgnoreCase("")){
										//&& admForm.getDegobtainedmark_8()!=null && !admForm.getDegobtainedmark_8().equalsIgnoreCase("") &&
										//admForm.getDegmaxmark_8()!=null && !admForm.getDegmaxmark_8().equalsIgnoreCase("")){
									if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_8()))){
										String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_8()));
										detailmark.setSubject9(s);
										admForm.setAdmsubname_8(s);
									}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_8()))){
										String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_8()));
										detailmark.setSubject9(s);
										admForm.setAdmsubname_8(s);
									}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_8()))){
										String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_8()));
										detailmark.setSubject9(s);
										admForm.setAdmsubname_8(s);
									}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_8()))){
										String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_8()));
										detailmark.setSubject9(s);
										admForm.setAdmsubname_8(s);
									}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_8()))){
										String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_8()));
										detailmark.setSubject9(s);
										admForm.setAdmsubname_8(s);
									}
									
									if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_8())))
										admForm.setAdmsubgrpname_8(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_8())));
									if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_8())))
										admForm.setAdmsubgrpname_8(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_8())));
									if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_8())))
										admForm.setAdmsubgrpname_8(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_8())));
									if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_8())))
										admForm.setAdmsubgrpname_8(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_8())));
									
								
								
								}else{
									detailmark.setSubject9("");
								}
			
								if(admForm.getDegobtainedmark_8()!=null && !admForm.getDegobtainedmark_8().equalsIgnoreCase("")){
									detailmark.setSubject9ObtainedMarks(admForm.getDegobtainedmark_8());
								}else{
									detailmark.setSubject9ObtainedMarks("");
								}
								
								if(admForm.getDegmaxmark_8()!=null && !admForm.getDegmaxmark_8().equalsIgnoreCase("")){
									detailmark.setSubject9TotalMarks(admForm.getDegmaxmark_8());
								}else{
									detailmark.setSubject9TotalMarks("");
								}
			
								
								
								if(admForm.getDegsubid_9()!=null && !admForm.getDegsubid_9().equalsIgnoreCase("")){
										//&& admForm.getDegobtainedmark_9()!=null && !admForm.getDegobtainedmark_9().equalsIgnoreCase("") &&
										//admForm.getDegmaxmark_9()!=null && !admForm.getDegmaxmark_9().equalsIgnoreCase("")){
									if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_9()))){
										String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_9()));
										detailmark.setSubject10(s);
										admForm.setAdmsubname_9(s);
									}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_9()))){
										String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_9()));
										detailmark.setSubject10(s);
										admForm.setAdmsubname_9(s);
									}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_9()))){
										String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_9()));
										detailmark.setSubject10(s);
										admForm.setAdmsubname_9(s);
									}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_9()))){
										String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_9()));
										detailmark.setSubject10(s);
										admForm.setAdmsubname_9(s);
									}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_9()))){
										String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_9()));
										detailmark.setSubject10(s);
										admForm.setAdmsubname_9(s);
									}
									
									if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_9())))
										admForm.setAdmsubgrpname_9(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_9())));
									if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_9())))
										admForm.setAdmsubgrpname_9(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_9())));
									if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_9())))
										admForm.setAdmsubgrpname_9(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_9())));
									if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_9())))
										admForm.setAdmsubgrpname_9(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_9())));
									if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_9())))
										admForm.setAdmsubgrpname_9(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_9())));
									
									
								
								}else{
									detailmark.setSubject10("");
								}
								
								
								if(admForm.getDegobtainedmark_9()!=null && !admForm.getDegobtainedmark_9().equalsIgnoreCase("")){
									detailmark.setSubject10ObtainedMarks(admForm.getDegobtainedmark_9());
								}else{
									detailmark.setSubject10ObtainedMarks("");
								}
								
								if(admForm.getDegmaxmark_9()!=null && !admForm.getDegmaxmark_9().equalsIgnoreCase("")){
									detailmark.setSubject10TotalMarks(admForm.getDegmaxmark_9());
								}else{
									detailmark.setSubject10TotalMarks("");
								}
			
								
								if(admForm.getDegsubid_10()!=null && !admForm.getDegsubid_10().equalsIgnoreCase("") ){
										//&& admForm.getDegobtainedmark_10()!=null && !admForm.getDegobtainedmark_10().equalsIgnoreCase("") &&
										//admForm.getDegmaxmark_10()!=null && !admForm.getDegmaxmark_10().equalsIgnoreCase("")){
									if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_10()))){
										String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_10()));
										detailmark.setSubject11(s);
										admForm.setAdmsubname_10(s);
									}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_10()))){
										String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_10()));
										detailmark.setSubject11(s);
										admForm.setAdmsubname_10(s);
									}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_10()))){
										String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_10()));
										detailmark.setSubject11(s);
										admForm.setAdmsubname_10(s);
									}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_10()))){
										String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_10()));
										detailmark.setSubject11(s);
										admForm.setAdmsubname_10(s);
									}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_10()))){
										String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_10()));
										detailmark.setSubject11(s);
										admForm.setAdmsubname_10(s);
									}
									
									if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_10())))
										admForm.setAdmsubgrpname_10(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_10())));
									if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_10())))
										admForm.setAdmsubgrpname_10(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_10())));
									if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_10())))
										admForm.setAdmsubgrpname_10(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_10())));
									if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_10())))
										admForm.setAdmsubgrpname_10(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_10())));
									if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_10())))
										admForm.setAdmsubgrpname_10(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_10())));
									
								
									
								}else{
									detailmark.setSubject11("");
								}
			
								
								if(admForm.getDegobtainedmark_10()!=null && !admForm.getDegobtainedmark_10().equalsIgnoreCase("")){
									detailmark.setSubject11ObtainedMarks(admForm.getDegobtainedmark_10());
								}else{
									detailmark.setSubject11ObtainedMarks("");
								}
								
								if(admForm.getDegmaxmark_10()!=null && !admForm.getDegmaxmark_10().equalsIgnoreCase("")){
									detailmark.setSubject11TotalMarks(admForm.getDegmaxmark_10());
								}else{
									detailmark.setSubject11TotalMarks("");
								}
								
								

								if(admForm.getDegsubid_11()!=null && !admForm.getDegsubid_11().equalsIgnoreCase("")){
										//&& admForm.getDegobtainedmark_11()!=null && !admForm.getDegobtainedmark_11().equalsIgnoreCase("") &&
										//admForm.getDegmaxmark_11()!=null && !admForm.getDegmaxmark_11().equalsIgnoreCase("")){
									if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_11()))){
										String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_11()));
										detailmark.setSubject12(s);
										admForm.setAdmsubname_11(s);
									}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_11()))){
										String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_11()));
										detailmark.setSubject12(s);
										admForm.setAdmsubname_11(s);
									}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_11()))){
										String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_11()));
										detailmark.setSubject12(s);
										admForm.setAdmsubname_11(s);
									}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_11()))){
										String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_11()));
										detailmark.setSubject12(s);
										admForm.setAdmsubname_11(s);
									}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_11()))){
										String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_11()));
										detailmark.setSubject12(s);
										admForm.setAdmsubname_11(s);
									}
									
									if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_11())))
										admForm.setAdmsubgrpname_11(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_11())));
									if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_11())))
										admForm.setAdmsubgrpname_11(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_11())));
									if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_11())))
										admForm.setAdmsubgrpname_11(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_11())));
									if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_11())))
										admForm.setAdmsubgrpname_11(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_11())));
									if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_11())))
										admForm.setAdmsubgrpname_11(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_11())));
									
									
								}else{
									detailmark.setSubject12("");
								}
			
								
								if(admForm.getDegobtainedmark_11()!=null && !admForm.getDegobtainedmark_11().equalsIgnoreCase("")){
									detailmark.setSubject12ObtainedMarks(admForm.getDegobtainedmark_11());
								}else{
									detailmark.setSubject12ObtainedMarks("");
								}
								
								if(admForm.getDegmaxmark_11()!=null && !admForm.getDegmaxmark_11().equalsIgnoreCase("")){
									detailmark.setSubject12TotalMarks(admForm.getDegmaxmark_11());
								}else{
									detailmark.setSubject12TotalMarks("");
								}
			
								

								if(admForm.getDegsubid_12()!=null && !admForm.getDegsubid_12().equalsIgnoreCase("") ){
										//&& admForm.getDegobtainedmark_12()!=null && !admForm.getDegobtainedmark_12().equalsIgnoreCase("") &&
										//admForm.getDegmaxmark_12()!=null && !admForm.getDegmaxmark_12().equalsIgnoreCase("")){
									if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_12()))){
										String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_12()));
										detailmark.setSubject13(s);
										admForm.setAdmsubname_12(s);
									}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_12()))){
										String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_12()));
										detailmark.setSubject13(s);
										admForm.setAdmsubname_12(s);
									}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_12()))){
										String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_12()));
										detailmark.setSubject13(s);
										admForm.setAdmsubname_12(s);
									}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_12()))){
										String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_12()));
										detailmark.setSubject13(s);
										admForm.setAdmsubname_12(s);
									}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_12()))){
										String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_12()));
										detailmark.setSubject13(s);
										admForm.setAdmsubname_12(s);
									}
									
									if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_12())))
										admForm.setAdmsubgrpname_12(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_12())));
									if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_12())))
										admForm.setAdmsubgrpname_12(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_12())));
									if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_12())))
										admForm.setAdmsubgrpname_12(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_12())));
									if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_12())))
										admForm.setAdmsubgrpname_12(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_12())));
									if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_12())))
										admForm.setAdmsubgrpname_12(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_12())));
									
									
								
								}else{
									detailmark.setSubject13("");
								}
			
								
								if(admForm.getDegobtainedmark_12()!=null && !admForm.getDegobtainedmark_12().equalsIgnoreCase("")){
									detailmark.setSubject13ObtainedMarks(admForm.getDegobtainedmark_12());
								}else{
									detailmark.setSubject13ObtainedMarks("");
								}
								
								if(admForm.getDegmaxmark_12()!=null && !admForm.getDegmaxmark_12().equalsIgnoreCase("")){
									detailmark.setSubject13TotalMarks(admForm.getDegmaxmark_12());
								}else{
									detailmark.setSubject13TotalMarks("");
								}
			
								

								if(admForm.getDegsubid_13()!=null && !admForm.getDegsubid_13().equalsIgnoreCase("") ){
										//&& admForm.getDegobtainedmark_13()!=null && !admForm.getDegobtainedmark_13().equalsIgnoreCase("") &&
										//admForm.getDegmaxmark_13()!=null && !admForm.getDegmaxmark_13().equalsIgnoreCase("")){
									if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_13()))){
										String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_13()));
										detailmark.setSubject14(s);
										admForm.setAdmsubname_13(s);
									}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_13()))){
										String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_13()));
										detailmark.setSubject14(s);
										admForm.setAdmsubname_13(s);
									}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_13()))){
										String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_13()));
										detailmark.setSubject14(s);
										admForm.setAdmsubname_13(s);
									}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_13()))){
										String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_13()));
										detailmark.setSubject14(s);
										admForm.setAdmsubname_13(s);
									}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_13()))){
										String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_13()));
										detailmark.setSubject14(s);
										admForm.setAdmsubname_13(s);
									}
									
									if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_13())))
										admForm.setAdmsubgrpname_13(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_13())));
									if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_13())))
										admForm.setAdmsubgrpname_13(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_13())));
									if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_13())))
										admForm.setAdmsubgrpname_13(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_13())));
									if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_13())))
										admForm.setAdmsubgrpname_13(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_13())));
									if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_13())))
										admForm.setAdmsubgrpname_13(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_13())));
									
									
								}else{
									detailmark.setSubject14("");
								}
			
								
								if(admForm.getDegobtainedmark_13()!=null && !admForm.getDegobtainedmark_13().equalsIgnoreCase("")){
									detailmark.setSubject14ObtainedMarks(admForm.getDegobtainedmark_13());
								}else{
									detailmark.setSubject14ObtainedMarks("");
								}
								
								if(admForm.getDegmaxmark_13()!=null && !admForm.getDegmaxmark_13().equalsIgnoreCase("")){
									detailmark.setSubject14TotalMarks(admForm.getDegmaxmark_13());
								}else{
									detailmark.setSubject14TotalMarks("");
								}
								
			
								
								/*if(admForm.getDegsubid_16()!=null && !admForm.getDegsubid_16().equalsIgnoreCase("") ){
									//&& admForm.getDegobtainedmark_16()!=null && !admForm.getDegobtainedmark_16().equalsIgnoreCase("") &&
									//admForm.getDegmaxmark_16()!=null && !admForm.getDegmaxmark_16().equalsIgnoreCase("")){
								if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_16()))){
									String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_16()));
									detailmark.setSubject17(s);
									admForm.setAdmsubname_16(s);
								}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_16()))){
									String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_16()));
									detailmark.setSubject17(s);
									admForm.setAdmsubname_16(s);
								}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_16()))){
									String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_16()));
									detailmark.setSubject17(s);
									admForm.setAdmsubname_16(s);
								}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_16()))){
									String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_16()));
									detailmark.setSubject17(s);
									admForm.setAdmsubname_16(s);
								}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_16()))){
									String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_16()));
									detailmark.setSubject17(s);
									admForm.setAdmsubname_16(s);
								}
								
								if(degGroupCoreMap.containsKey(Integer.parseInt(admForm.getDegsubid_16())))
									admForm.setAdmsubgrpname_16(degGroupCoreMap.get(Integer.parseInt(admForm.getDegsubid_16())));
								if(degGroupCommonMap.containsKey(Integer.parseInt(admForm.getDegsubid_16())))
									admForm.setAdmsubgrpname_16(degGroupCommonMap.get(Integer.parseInt(admForm.getDegsubid_16())));
								if(degGroupComplMap.containsKey(Integer.parseInt(admForm.getDegsubid_16())))
									admForm.setAdmsubgrpname_16(degGroupComplMap.get(Integer.parseInt(admForm.getDegsubid_16())));
								if(degGroupOpenMap.containsKey(Integer.parseInt(admForm.getDegsubid_16())))
									admForm.setAdmsubgrpname_16(degGroupOpenMap.get(Integer.parseInt(admForm.getDegsubid_16())));
								if(degGroupSubMap.containsKey(Integer.parseInt(admForm.getDegsubid_16())))
									admForm.setAdmsubgrpname_16(degGroupSubMap.get(Integer.parseInt(admForm.getDegsubid_16())));
							
							}else{
								detailmark.setSubject17("");
							}
		
							
							if(admForm.getDegobtainedmark_16()!=null && !admForm.getDegobtainedmark_16().equalsIgnoreCase("")){
								detailmark.setSubject17ObtainedMarks(admForm.getDegobtainedmark_16());
							}else{
								detailmark.setSubject17ObtainedMarks("");
							}
							
							if(admForm.getDegmaxmark_16()!=null && !admForm.getDegmaxmark_16().equalsIgnoreCase("")){
								detailmark.setSubject17TotalMarks(admForm.getDegmaxmark_16());
							}else{
								detailmark.setSubject17TotalMarks("");
							}
							
*/
								
								// validation starts
								ActionMessages errors = new ActionMessages();
								
								
								//raghu write newly
								if(admForm.getDegtotalmaxmarkother()!=null && admForm.getDegtotalmaxmarkother().equalsIgnoreCase("")) {
									if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
										ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
										errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
										}
										
								}
								
								if(admForm.getDegtotalmaxmarkother()!=null && StringUtils.isEmpty(admForm.getDegtotalmaxmarkother()) && !CommonUtil.isValidDecimal(admForm.getDegtotalmaxmarkother())){
									if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
										ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
										errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
										}
										
								}
								
								//raghu write newly
								if(admForm.getDegtotalmaxmarkother()!=null &&  !CommonUtil.isValidDecimal(admForm.getDegtotalmaxmarkother())){
									if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
										ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
										errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
										}
										
								}
								
								if(admForm.getDegtotalobtainedmarkother()!=null && admForm.getDegtotalobtainedmarkother().equalsIgnoreCase("")) {
										if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
										ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
										errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
										}
										
								}
								
								if(admForm.getDegtotalobtainedmarkother()!=null && StringUtils.isEmpty(admForm.getDegtotalobtainedmarkother()) && !CommonUtil.isValidDecimal(admForm.getDegtotalobtainedmarkother())) {
									if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
										ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
										errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
										}
										
								}
								
								if(admForm.getDegtotalobtainedmarkother()!=null &&  !CommonUtil.isValidDecimal(admForm.getDegtotalobtainedmarkother())) {
									if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
										ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
										errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
										}
										
								}
								
								
								if (errors != null && !errors.isEmpty()) {
									saveErrors(request, errors);
									
									return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORUG_FOREDIT);
								}
								
								
								if(admForm.getDegtotalobtainedmarkother()!=null && !admForm.getDegtotalobtainedmarkother().equalsIgnoreCase("")){
									detailmark.setTotalObtainedMarks(admForm.getDegtotalobtainedmarkother());
								}else{
									detailmark.setTotalObtainedMarks("");
								}
								
								if(admForm.getDegtotalmaxmarkother()!=null && !admForm.getDegtotalmaxmarkother().equalsIgnoreCase("")){
									detailmark.setTotalMarks(admForm.getDegtotalmaxmarkother());
								}else{
									detailmark.setTotalMarks("");
								}
								
								
								//raghu added newly
								validateMarksForUG(detailmark, errors, admForm.getPatternofStudy());
								if (errors != null && !errors.isEmpty()) {
										saveErrors(request, errors);
										
										return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORUG_FOREDIT);
									}
								

								
								
								
								
								
								
								
								
								
								
								
			int i=8;
			Iterator<AdmSubjectMarkForRankTO> itr=admsubList.iterator();
			String s="";
			for(i=8;i<15;){
				
				AdmSubjectMarkForRankTO admSubjectMarkForRankTO=new AdmSubjectMarkForRankTO();
				//AdmSubjectMarkForRankTO admSubjectMarkForRankTO=(AdmSubjectMarkForRankTO) itr.next();
				
								
								if(i==8){
									if(admForm.getDegsubid_7()!=null && !admForm.getDegsubid_7().equalsIgnoreCase("") && admForm.getDegobtainedmark_7()!=null && !admForm.getDegobtainedmark_7().equalsIgnoreCase("") &&
											admForm.getDegmaxmark_7()!=null && !admForm.getDegmaxmark_7().equalsIgnoreCase("")){

										
									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_7());
									admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_7());
									admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_7());
									
									if(admForm.getAdmsubmarkid_7()!=null && !admForm.getAdmsubmarkid_7().equalsIgnoreCase(""))
									admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_7()));
									admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_7());
									
									
									AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
									admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_7()));
									admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_7());
									admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_7());
									admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
									
									}
									
									i++;
									}
								

								else if(i==9){
									if(admForm.getDegsubid_8()!=null && !admForm.getDegsubid_8().equalsIgnoreCase("") && admForm.getDegobtainedmark_8()!=null && !admForm.getDegobtainedmark_8().equalsIgnoreCase("") &&
											admForm.getDegmaxmark_8()!=null && !admForm.getDegmaxmark_8().equalsIgnoreCase("")){

										
									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_8());
									admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_8());
									admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_8());
									
									if(admForm.getAdmsubmarkid_8()!=null && !admForm.getAdmsubmarkid_8().equalsIgnoreCase(""))
									admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_8()));
									admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_8());
									
									
									AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
									admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_8()));
									admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_8());
									admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_8());
									admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
									
									}
									
									i++;
									}
								
								
								
								else if(i==10){
									if(admForm.getDegsubid_9()!=null && !admForm.getDegsubid_9().equalsIgnoreCase("") && admForm.getDegobtainedmark_9()!=null && !admForm.getDegobtainedmark_9().equalsIgnoreCase("") &&
											admForm.getDegmaxmark_9()!=null && !admForm.getDegmaxmark_9().equalsIgnoreCase("")){

										
									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_9());
									admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_9());
									admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_9());
									
									if(admForm.getAdmsubmarkid_9()!=null && !admForm.getAdmsubmarkid_9().equalsIgnoreCase(""))
									admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_9()));
									admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_9());
									
									
									AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
									admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_9()));
									admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_9());
									admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_9());
									admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
									
									}
									
									i++;
									}
								
								
								
								else if(i==11){
									if(admForm.getDegsubid_10()!=null && !admForm.getDegsubid_10().equalsIgnoreCase("") && admForm.getDegobtainedmark_10()!=null && !admForm.getDegobtainedmark_10().equalsIgnoreCase("") &&
											admForm.getDegmaxmark_10()!=null && !admForm.getDegmaxmark_10().equalsIgnoreCase("")){

										
									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_10());
									admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_10());
									admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_10());
									
									if(admForm.getAdmsubmarkid_10()!=null && !admForm.getAdmsubmarkid_10().equalsIgnoreCase(""))
									admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_10()));
									admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_10());
									
									
									AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
									admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_10()));
									admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_10());
									admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_10());
									admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
									
									}
									
									i++;
									}
								else if(i==12){
									if(admForm.getDegsubid_11()!=null && !admForm.getDegsubid_11().equalsIgnoreCase("") && admForm.getDegobtainedmark_11()!=null && !admForm.getDegobtainedmark_11().equalsIgnoreCase("") &&
											admForm.getDegmaxmark_11()!=null && !admForm.getDegmaxmark_11().equalsIgnoreCase("")){

										
									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_11());
									admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_11());
									admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_11());
									
									if(admForm.getAdmsubmarkid_11()!=null && !admForm.getAdmsubmarkid_11().equalsIgnoreCase(""))
									admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_11()));
									admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_11());
									
									
									AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
									admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_11()));
									admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_11());
									admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_11());
									admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
									
									}
									
									i++;
									}

								else if(i==13){
									if(admForm.getDegsubid_12()!=null && !admForm.getDegsubid_12().equalsIgnoreCase("") && admForm.getDegobtainedmark_12()!=null && !admForm.getDegobtainedmark_12().equalsIgnoreCase("") &&
											admForm.getDegmaxmark_12()!=null && !admForm.getDegmaxmark_12().equalsIgnoreCase("")){

										
									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_12());
									admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_12());
									admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_12());
									
									if(admForm.getAdmsubmarkid_12()!=null && !admForm.getAdmsubmarkid_12().equalsIgnoreCase(""))
									admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_12()));
									admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_12());
									
									
									AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
									admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_12()));
									admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_12());
									admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_12());
									admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
									
									}
									
									i++;
									}
								else if(i==14){
									if(admForm.getDegsubid_13()!=null && !admForm.getDegsubid_13().equalsIgnoreCase("") && admForm.getDegobtainedmark_13()!=null && !admForm.getDegobtainedmark_13().equalsIgnoreCase("") &&
											admForm.getDegmaxmark_13()!=null && !admForm.getDegmaxmark_13().equalsIgnoreCase("")){

										
									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_13());
									admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_13());
									admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_13());
									
									if(admForm.getAdmsubmarkid_13()!=null && !admForm.getAdmsubmarkid_13().equalsIgnoreCase(""))
									admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_13()));
									admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_13());
									
									
									AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
									admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_13()));
									admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_13());
									admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_13());
									admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
									
									}
									
									i++;
									}
								/*else if(i==15){
									if(admForm.getDegsubid_16()!=null && !admForm.getDegsubid_16().equalsIgnoreCase("") && admForm.getDegobtainedmark_16()!=null && !admForm.getDegobtainedmark_16().equalsIgnoreCase("") &&
											admForm.getDegmaxmark_16()!=null && !admForm.getDegmaxmark_16().equalsIgnoreCase("")){

										
									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_16());
									admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_16());
									admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_16());
									
									if(admForm.getAdmsubmarkid_16()!=null && !admForm.getAdmsubmarkid_16().equalsIgnoreCase(""))
									admSubjectMarkForRankTO.setId(Integer.parseInt(admForm.getAdmsubmarkid_16()));
									admSubjectMarkForRankTO.setGroupname(admForm.getAdmsubgrpname_16());
									
									
									AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo() ;
									admSubjectForRankTo.setId(Integer.parseInt(admForm.getDegsubid_16()));
									admSubjectForRankTo.setGroupname(admForm.getAdmsubgrpname_16());
									admSubjectForRankTo.setSubjectname(admForm.getAdmsubname_16());
									admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
									
									}
									
									i++;
									}*/
								
								admSubList.add(admSubjectMarkForRankTO);
				}//for close
			
							
							
							admForm.setAdmsubMarkListUG(admSubList);
			
				}
							
							
							
							
							
							List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
							if (qualifications != null) {
								Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
								while (qualItr.hasNext()) {
									EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
									if(qualTO.getId()==detailMarkIndex){
										if(qualTO.getDetailmark()!=null)
										detailmark.setId(qualTO.getDetailmark().getId());
										
										qualTO.setDetailmark(detailmark);
										//raghu
										admForm.setDetailMark(qualTO.getDetailmark());
									}
								}
							}
									
							
						
				}//try close
			
			catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
				
							
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);				
	
	}// method close
	

	
	
	
	
	
	/**
	 * @param detailmark
	 * @param errors
	 * @return
	 */
	public static ActionMessages validateMarksForUG(CandidateMarkTO detailmark,ActionMessages errors,String CBCSS){
		



		log.info("enter validateMarks...");
		
		//ActionMessages errors= new ActionMessages();
		
			
		if(detailmark!=null){
			
			if(CBCSS.equalsIgnoreCase("CBCSS")){
				
				
			if(detailmark.getSubject1()!=null && !detailmark.getSubject1().equalsIgnoreCase("") && 
					detailmark.getSubject1ObtainedMarks()!=null && !detailmark.getSubject1ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject1TotalMarks()!=null && !detailmark.getSubject1TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject1Credit()!=null && !detailmark.getSubject1Credit().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject1()==null || detailmark.getSubject1().equalsIgnoreCase("")) && 
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
			if(detailmark.getSubject2()!=null && !detailmark.getSubject2().equalsIgnoreCase("") && 
					detailmark.getSubject2ObtainedMarks()!=null && !detailmark.getSubject2ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject2TotalMarks()!=null && !detailmark.getSubject2TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject2Credit()!=null && !detailmark.getSubject2Credit().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject2()==null || detailmark.getSubject2().equalsIgnoreCase("")) && 
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
			if(detailmark.getSubject3()!=null && !detailmark.getSubject3().equalsIgnoreCase("") && 
					detailmark.getSubject3ObtainedMarks()!=null && !detailmark.getSubject3ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject3TotalMarks()!=null && !detailmark.getSubject3TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject3Credit()!=null && !detailmark.getSubject3Credit().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject3()==null || detailmark.getSubject3().equalsIgnoreCase("")) && 
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
			if(detailmark.getSubject4()!=null && !detailmark.getSubject4().equalsIgnoreCase("") && 
					detailmark.getSubject4ObtainedMarks()!=null && !detailmark.getSubject4ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject4TotalMarks()!=null && !detailmark.getSubject4TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject4Credit()!=null && !detailmark.getSubject4Credit().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject4()==null || detailmark.getSubject4().equalsIgnoreCase("")) && 
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
			if(detailmark.getSubject5()!=null && !detailmark.getSubject5().equalsIgnoreCase("") && 
					detailmark.getSubject5ObtainedMarks()!=null && !detailmark.getSubject5ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject5TotalMarks()!=null && !detailmark.getSubject5TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject5Credit()!=null && !detailmark.getSubject5Credit().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject5()==null || detailmark.getSubject5().equalsIgnoreCase("")) && 
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
			if(detailmark.getSubject6()!=null && !detailmark.getSubject6().equalsIgnoreCase("") && 
					detailmark.getSubject6ObtainedMarks()!=null && !detailmark.getSubject6ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject6TotalMarks()!=null && !detailmark.getSubject6TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject6Credit()!=null && !detailmark.getSubject6Credit().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject6()==null || detailmark.getSubject6().equalsIgnoreCase("")) && 
					(detailmark.getSubject6ObtainedMarks()==null || detailmark.getSubject6ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject6TotalMarks()==null || detailmark.getSubject6TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject6Credit()==null || detailmark.getSubject6Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubject7()!=null && !detailmark.getSubject7().equalsIgnoreCase("") && 
					detailmark.getSubject7ObtainedMarks()!=null && !detailmark.getSubject7ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject7TotalMarks()!=null && !detailmark.getSubject7TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject7Credit()!=null && !detailmark.getSubject7Credit().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject7()==null || detailmark.getSubject7().equalsIgnoreCase("")) && 
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
			
			if(detailmark.getSubject15()!=null && !detailmark.getSubject15().equalsIgnoreCase("") && 
					detailmark.getSubject15ObtainedMarks()!=null && !detailmark.getSubject15ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject15TotalMarks()!=null && !detailmark.getSubject15TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject15Credit()!=null && !detailmark.getSubject15Credit().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject15()==null || detailmark.getSubject15().equalsIgnoreCase("")) && 
					(detailmark.getSubject15ObtainedMarks()==null || detailmark.getSubject15ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject15TotalMarks()==null || detailmark.getSubject15TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject15Credit()==null || detailmark.getSubject15Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			
			/*if(detailmark.getSubject16()!=null && !detailmark.getSubject16().equalsIgnoreCase("") && 
					detailmark.getSubject16ObtainedMarks()!=null && !detailmark.getSubject16ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject16TotalMarks()!=null && !detailmark.getSubject16TotalMarks().equalsIgnoreCase("")
					&& detailmark.getSubject16Credit()!=null && !detailmark.getSubject16Credit().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject16()==null || detailmark.getSubject16().equalsIgnoreCase("")) && 
					(detailmark.getSubject16ObtainedMarks()==null || detailmark.getSubject16ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject16TotalMarks()==null || detailmark.getSubject16TotalMarks().equalsIgnoreCase(""))
					&&( detailmark.getSubject16Credit()==null || detailmark.getSubject16Credit().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}*/
			
			//cbssc close
		}else{
			
		
		
			if(detailmark.getSubject8()!=null && !detailmark.getSubject8().equalsIgnoreCase("") && 
					detailmark.getSubject8ObtainedMarks()!=null && !detailmark.getSubject8ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject8TotalMarks()!=null && !detailmark.getSubject8TotalMarks().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject8()==null || detailmark.getSubject8().equalsIgnoreCase("")) && 
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
			
			
			if(detailmark.getSubject9()!=null && !detailmark.getSubject9().equalsIgnoreCase("") && 
					detailmark.getSubject9ObtainedMarks()!=null && !detailmark.getSubject9ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject9TotalMarks()!=null && !detailmark.getSubject9TotalMarks().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject9()==null || detailmark.getSubject9().equalsIgnoreCase("")) && 
					(detailmark.getSubject9ObtainedMarks()==null || detailmark.getSubject9ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject9TotalMarks()==null || detailmark.getSubject9TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubject10()!=null && !detailmark.getSubject10().equalsIgnoreCase("") && 
					detailmark.getSubject10ObtainedMarks()!=null && !detailmark.getSubject10ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject10TotalMarks()!=null && !detailmark.getSubject10TotalMarks().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject10()==null || detailmark.getSubject10().equalsIgnoreCase("")) && 
					(detailmark.getSubject10ObtainedMarks()==null || detailmark.getSubject10ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject10TotalMarks()==null || detailmark.getSubject10TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			
			if(detailmark.getSubject11()!=null && !detailmark.getSubject11().equalsIgnoreCase("") && 
					detailmark.getSubject11ObtainedMarks()!=null && !detailmark.getSubject11ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject11TotalMarks()!=null && !detailmark.getSubject11TotalMarks().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject11()==null || detailmark.getSubject11().equalsIgnoreCase("")) && 
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
			if(detailmark.getSubject12()!=null && !detailmark.getSubject12().equalsIgnoreCase("") && 
					detailmark.getSubject12ObtainedMarks()!=null && !detailmark.getSubject12ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject12TotalMarks()!=null && !detailmark.getSubject12TotalMarks().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject12()==null || detailmark.getSubject12().equalsIgnoreCase("")) && 
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
			
			if(detailmark.getSubject13()!=null && !detailmark.getSubject13().equalsIgnoreCase("") && 
					detailmark.getSubject13ObtainedMarks()!=null && !detailmark.getSubject13ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject13TotalMarks()!=null && !detailmark.getSubject13TotalMarks().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject13()==null || detailmark.getSubject13().equalsIgnoreCase("")) && 
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
			
			if(detailmark.getSubject14()!=null && !detailmark.getSubject14().equalsIgnoreCase("") && 
					detailmark.getSubject14ObtainedMarks()!=null && !detailmark.getSubject14ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject14TotalMarks()!=null && !detailmark.getSubject14TotalMarks().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject14()==null || detailmark.getSubject14().equalsIgnoreCase("")) && 
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
			
			/*if(detailmark.getSubject17()!=null && !detailmark.getSubject17().equalsIgnoreCase("") && 
					detailmark.getSubject17ObtainedMarks()!=null && !detailmark.getSubject17ObtainedMarks().equalsIgnoreCase("") && 
					detailmark.getSubject17TotalMarks()!=null && !detailmark.getSubject17TotalMarks().equalsIgnoreCase(""))
					{
					}
			else if((detailmark.getSubject17()==null || detailmark.getSubject17().equalsIgnoreCase("")) && 
					(detailmark.getSubject17ObtainedMarks()==null || detailmark.getSubject17ObtainedMarks().equalsIgnoreCase("")) && 
					(detailmark.getSubject17TotalMarks()==null || detailmark.getSubject17TotalMarks().equalsIgnoreCase(""))){
			}
			else{
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}*/
			
		}// other close
		
			
			
			
			if(CBCSS.equalsIgnoreCase("CBCSS")){
				
			
			
			if( detailmark.getSubject1ObtainedMarks()!=null && !(detailmark.getSubject1ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
			    return errors;
			}
			if(  detailmark.getSubject2ObtainedMarks()!=null && !(detailmark.getSubject2ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject3ObtainedMarks()!=null && !(detailmark.getSubject3ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject4ObtainedMarks()!=null &&  !(detailmark.getSubject4ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject5ObtainedMarks()!=null && !(detailmark.getSubject5ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject6ObtainedMarks()!=null && !(detailmark.getSubject6ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			if(  detailmark.getSubject7ObtainedMarks()!=null && !(detailmark.getSubject7ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			
			if(  detailmark.getSubject15ObtainedMarks()!=null && !(detailmark.getSubject15ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}
			
			/*if(  detailmark.getSubject16ObtainedMarks()!=null && !(detailmark.getSubject16ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}*/
			
			//cbscc close
			}else{
				
			
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
			/*if(  detailmark.getSubject17ObtainedMarks()!=null && !(detailmark.getSubject17ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
				
				return errors;
			}*/
			}//other close
			
			
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
			
			//raghu write newly
			/*if(detailmark.getSubject1TotalMarks()!=null && StringUtils.isEmpty(detailmark.getSubject1TotalMarks())){
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getSubject1ObtainedMarks()!=null && StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) ) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			*/
			
			//raghu write newly
			if(detailmark.getTotalMarks()!=null && StringUtils.isEmpty(detailmark.getTotalMarks()) && !CommonUtil.isValidDecimal(detailmark.getSubject1TotalMarks())){
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}
					return errors;
			}
			if(detailmark.getTotalObtainedMarks()!=null && StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && !CommonUtil.isValidDecimal(detailmark.getSubject1TotalMarks())) {
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
			
			
			
			if((detailmark.getSubject1TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()))&& (detailmark.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())) && (detailmark.getSubject1Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject1Credit())) ||
					(detailmark.getSubject2TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()))&& (detailmark.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())) && (detailmark.getSubject2Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject2Credit())) ||
					(detailmark.getSubject3TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()))&& (detailmark.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())) && (detailmark.getSubject3Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject3Credit())) ||
					(detailmark.getSubject4TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()))&& (detailmark.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())) && (detailmark.getSubject4Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject4Credit())) ||
					(detailmark.getSubject5TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()))&& (detailmark.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())) && (detailmark.getSubject5Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject5Credit())) ||
					(detailmark.getSubject6TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()))&& (detailmark.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())) && (detailmark.getSubject6Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject6Credit())) ||
					(detailmark.getSubject7TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()))&& (detailmark.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()))&& (detailmark.getSubject7Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject7Credit())) ||
					(detailmark.getSubject8TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()))&& (detailmark.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())) ||
					(detailmark.getSubject9TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()))&& (detailmark.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())) ||
					(detailmark.getSubject10TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()))&& (detailmark.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())) ||
					(detailmark.getSubject11TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()))&& (detailmark.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) ||
					(detailmark.getSubject12TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()))&& (detailmark.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) ||
					(detailmark.getSubject13TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()))&& (detailmark.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) ||
					(detailmark.getSubject14TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()))&& (detailmark.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) ||
					(detailmark.getSubject15TotalMarks()==null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()))&& (detailmark.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) && (detailmark.getSubject15Credit()!=null && !StringUtils.isEmpty(detailmark.getSubject15Credit())) ||
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
				}  //.matches("\\d{0,4}(\\.\\d{1,2})?")
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
				
				if(detailmark.getSubject1_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks())&& detailmark.getSubject1_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks()) && Float.parseFloat(detailmark.getSubject1_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject1_languagewise_ObtainedMarks())||
						detailmark.getSubject2_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_TotalMarks())&& detailmark.getSubject2_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks()) && Float.parseFloat(detailmark.getSubject2_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject2_languagewise_ObtainedMarks())||
						detailmark.getSubject3_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_TotalMarks())&& detailmark.getSubject3_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks()) && Float.parseFloat(detailmark.getSubject3_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject3_languagewise_ObtainedMarks())||
						detailmark.getSubject4_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_TotalMarks())&&  detailmark.getSubject4_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks()) && Float.parseFloat(detailmark.getSubject4_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject4_languagewise_ObtainedMarks())||
						detailmark.getSubject5_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_TotalMarks())&& detailmark.getSubject5_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject5_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject5_languagewise_ObtainedMarks())||
						detailmark.getSubject6_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks())&& detailmark.getSubject6_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject6_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject6_languagewise_ObtainedMarks())||
						detailmark.getSubject7_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks())&& detailmark.getSubject7_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject7_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject7_languagewise_ObtainedMarks())||
						detailmark.getSubject8_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks())&& detailmark.getSubject8_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject8_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject8_languagewise_ObtainedMarks())||
						detailmark.getSubject9_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks())&& detailmark.getSubject9_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject9_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject9_languagewise_ObtainedMarks())||
						detailmark.getSubject10_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks())&& detailmark.getSubject10_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks()) &&  Float.parseFloat(detailmark.getSubject10_languagewise_TotalMarks())< Float.parseFloat(detailmark.getSubject10_languagewise_ObtainedMarks())||
						detailmark.getTotal_languagewise_Marks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks())&& detailmark.getTotal_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks()) && Float.parseFloat(detailmark.getTotal_languagewise_Marks())< Float.parseFloat(detailmark.getTotal_languagewise_ObtainedMarks())
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

	
	//raghu added newly 
	
	public ActionForward initDetailMarkEditPageClass12New(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		ActionMessages errors=new ActionMessages();
		try {
			
			/*String indexString = request.getParameter(OnlineApplicationAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(OnlineApplicationAction.COUNTID, indexString);
				}else
					session.removeAttribute(OnlineApplicationAction.COUNTID);
			}*/
			
			
			String indexString = request.getParameter("editcountID");
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute("editcountID", indexString);
				}else
					session.removeAttribute("editcountID");
			}
			
			//class 12
			int doctypeId=CMSConstants.CLASS12_DOCTYPEID;
			
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							//if (qualTO.getCountId()==index) {
							if (qualTO.getId()==index) {
							
								
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
				System.out.println("************************ error details in online application edit in class 12 mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);				
			 	
				
			}else
			{
				//throw e;
				System.out.println("************************ error details in online application edit in class 12 mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     
			     return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);				
			 	
			}
		}
			
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH_FOREDIT_NEW);
		
		
	}
	
	
	
	//raghu
	public ActionForward submitDetailMarkEditClass12New(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitDetailMarkConfirm page...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		HttpSession session=request.getSession(false);
		ActionMessages errors=new ActionMessages();
		
		try {
			
			
			String indexString = null;
			
			if (session != null)
				indexString = (String) session.getAttribute("editcountID");
			
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
			
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH_FOREDIT_NEW);
			
			}
			
			errors = validateMarksFor12thClass(detailmark, new ActionMessages());
			
			if (errors != null && !errors.isEmpty()) {saveErrors(request, errors);
			
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH_FOREDIT_NEW);
			
			
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					//raghu added newly
					if(qualTO.getId()==detailMarkIndex){
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
				System.out.println("************************ error details in online application edit in class 12 submit mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			    
			     return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);				
			 	
			}else
			{
				//throw e;
				System.out.println("************************ error details in online application edit in submit class 12 mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     
			     return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);				
			 		
			}
		}
		log.info("enter ssubmitDetailMarkConfirm page...");
			
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);				
		
		
	}
	

	
	
	public ActionForward initDetailMarkEditPageDegreeNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		ActionMessages errors=new ActionMessages();
		try {
			
			/*String indexString = request.getParameter(OnlineApplicationAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(OnlineApplicationAction.COUNTID, indexString);
				}else
					session.removeAttribute(OnlineApplicationAction.COUNTID);
			}
			*/
			
			
			String indexString = request.getParameter("editcountID");
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute("editcountID", indexString);
				}else
					session.removeAttribute("editcountID");
			}
			
			//deg
			int doctypeId=CMSConstants.DEGREE_DOCTYPEID;
			
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							//if (qualTO.getCountId()==index) {
							if (qualTO.getId()==index) {
								
								//new raghu
								if(qualTO.getDocTypeId()==doctypeId){
								
								
									admForm.setPatternofStudy(qualTO.getUgPattern());
									String Core="Core";
									String Compl="Complimentary";
									String Common="Common";
									String Open="Open";
									String Sub="Sub";
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
				System.out.println("************************ error details in online application edit in degree mark*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);				
			 	
			}else
			{
				//throw e;
				System.out.println("************************ error details in online application edit in degree mark************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);				
			 	
			}
		}
			
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORUG_FOREDIT_NEW);
	}
	
	
	//raghu
	public ActionForward submitDetailMarkEditDegreeNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitDetailMarkConfirm page...");
		ApplicationEditForm admForm = (ApplicationEditForm) form;
		HttpSession session=request.getSession(false);
		ActionMessages errors=new ActionMessages();
		try {
			/*String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(OnlineApplicationAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			*/
			
			String indexString = null;
			
			if (session != null)
				indexString = (String) session.getAttribute("editcountID");
			
			int detailMarkIndex = -1;
			
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			
			
			
			CandidateMarkTO detailmark = admForm.getDetailMark();
			
			 
			// validation starts
		 errors = new ActionMessages();
			
			
			if(admForm.getPatternofStudy().equalsIgnoreCase("CBCSS")){
				
			
			
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
			
			
			
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORUG_FOREDIT_NEW);
			}
			
			
			
			
			//errors = validateMarksForUG(detailmark, errors,admForm.getPatternofStudy());
			errors = validateMarksDegree(detailmark, errors,admForm.getPatternofStudy());
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORUG_FOREDIT_NEW);
				}
			
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getId()==detailMarkIndex){
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
				System.out.println("************************ error details in online application edit in submit degree*************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			 
			     return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);				
			 	
			}else
			{
				//throw e;
				System.out.println("************************ error details in online application edit in submit degree *************************"+e);
				
			     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			     return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);				
			 	
			}
		}
		log.info("enter ssubmitDetailMarkConfirm page...");
				
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);				
		
		
	}
	

	
	//raghu for degree
		
		/**
		 * @param detailmark
		 * @return
		 */
		public static ActionMessages validateMarksDegree(CandidateMarkTO detailmark,ActionMessages errors,String CBCSS) {
			log.info("enter validateMarks...");
			
			if(detailmark!=null){
				
				
				//raghu new
				
				
		    	if(CBCSS.equalsIgnoreCase("CBCSS")){
					
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
					if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
						errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
						}
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
					if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
						errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
						}
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
			
				
		    	if(CBCSS.equalsIgnoreCase("CBCSS")){
				
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
		    	
				
				
				if(CBCSS.equalsIgnoreCase("CBCSS")){
					
				
				
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
				
				if(  detailmark.getSubject8ObtainedMarks()!=null && !(detailmark.getSubject8ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);
					
					return errors;
				}
				if(  detailmark.getSubject9ObtainedMarks()!=null &&!(detailmark.getSubject9ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					
					return errors;
				}
				
				if(  detailmark.getSubject10ObtainedMarks()!=null &&!(detailmark.getSubject10ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
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
					
				
				
				if(  detailmark.getSubject11ObtainedMarks()!=null &&!(detailmark.getSubject11ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					
					return errors;
				}
				if(  detailmark.getSubject12ObtainedMarks()!=null &&!(detailmark.getSubject12ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					
					return errors;
				}

				if(  detailmark.getSubject13ObtainedMarks()!=null &&!(detailmark.getSubject13ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					
					return errors;
				}
				if(  detailmark.getSubject14ObtainedMarks()!=null &&!(detailmark.getSubject14ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					
					return errors;
				}
				if(  detailmark.getSubject15ObtainedMarks()!=null &&!(detailmark.getSubject15ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					
					return errors;
				}
				if(  detailmark.getSubject16ObtainedMarks()!=null &&!(detailmark.getSubject16ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					
					return errors;
				}
				if(  detailmark.getSubject17ObtainedMarks()!=null &&!(detailmark.getSubject17ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					
					return errors;
				}
				if(  detailmark.getSubject18ObtainedMarks()!=null &&!(detailmark.getSubject18ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					
					return errors;
				}
				if(  detailmark.getSubject19ObtainedMarks()!=null &&!(detailmark.getSubject19ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					
					return errors;
				}
				if(  detailmark.getSubject20ObtainedMarks()!=null &&!(detailmark.getSubject20ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,2})?") )){
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
				
				
			}//checking over
			
			log.info("exit validateMarks...");
			return errors;
		}
		
		
	//raghu write newly for preferences
		
		public ActionForward addPrefereneces(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Begining of Add preferences in Form");
			ApplicationEditForm admForm=(ApplicationEditForm)form;
			ActionMessages message = new ActionMessages();
			
			Map<Integer, String> courseMap=admForm.getCourseMap();
			/*if(admForm.getGender()!=null && admForm.getGender().equalsIgnoreCase("FEMALE")){
				courseMap=CommonAjaxHandler.getInstance().getCourseByProgramTypeForOnlineNewforFemeale(Integer.parseInt(admForm.getProgramTypeId()));
				admForm.setCourseMap(courseMap);
			}else{
				courseMap=CommonAjaxHandler.getInstance().getCourseByProgramTypeForOnlineNew(Integer.parseInt(admForm.getProgramTypeId()));
				admForm.setCourseMap(courseMap);
			}*/
			
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
						return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
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
						return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
					//else
					//	return mapping.findForward("OfflineAppBasicPage");
			}
			
			
			 
		    //checking course excceded
			if(admForm.getProgramTypeId()!=null && admForm.getProgramTypeId().equalsIgnoreCase("1")){
	        if(list.size()>=CMSConstants.MAX_CANDIDATE_PREFERENCES){
	    	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID);
	   		message.add(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID,error);
	   		saveErrors(request, message);
	   		//if(admForm.isOnlineApply())
				return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
			//else
			//	return mapping.findForward("OfflineAppBasicPage");
	       }
			} else{

		        if(list.size()>=CMSConstants.MAX_CANDIDATE_PREFERENCES_PG){
		    	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID);
		   		message.add(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID,error);
		   		saveErrors(request, message);
		   		//if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
				//else
				//	return mapping.findForward("OfflineAppBasicPage");
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
	    			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
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
				return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
			//else
				//return mapping.findForward("OfflineAppBasicPage");
				
		}
			

		

		public ActionForward removePreferences(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of Remove preferences in Form");
			
			ApplicationEditForm admForm=(ApplicationEditForm)form;
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
				return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE_NEW);
			//else
			//	return mapping.findForward("OfflineAppBasicPage");
		}	
		
		

	

}
