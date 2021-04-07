package com.kp.cms.actions.admission;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

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
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantLateralDetails;
import com.kp.cms.bo.admin.ApplicantTransferDetails;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.SelectionDateNotAvailableException;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admin.EntranceDetailsHandler;
import com.kp.cms.handlers.admin.GuidelinesEntryHandler;
import com.kp.cms.handlers.admin.LanguageHandler;
import com.kp.cms.handlers.admin.MaintenanceAlertHandler;
import com.kp.cms.handlers.admin.OccupationTransactionHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.admin.StateHandler;
import com.kp.cms.handlers.admin.SubjectGroupHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.OfflineDetailsHandler;
import com.kp.cms.handlers.admission.OnlineApplicationHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.to.admin.AdmSubjectMarkForRankTO;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplicantWorkExperienceTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.CoursePrerequisiteTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.EntrancedetailsTO;
import com.kp.cms.to.admin.ExamCenterTO;
import com.kp.cms.to.admin.GuideLinesCheckListTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ParishTo;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.TermsConditionChecklistTO;
import com.kp.cms.to.admin.UGCoursesTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AddressTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.admission.PreferenceTO;
import com.kp.cms.transactions.admin.IProgramTransaction;
import com.kp.cms.transactions.admin.ISubReligionTransaction;
import com.kp.cms.transactions.admin.ITemplatePassword;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactionsimpl.admin.DioceseTransactionImpl;
import com.kp.cms.transactionsimpl.admin.ParishTransactionImpl;
import com.kp.cms.transactionsimpl.admin.ProgramTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SubReligionTransactionImpl;
import com.kp.cms.transactionsimpl.admin.TemplateImpl;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.utilities.CSVUpdater;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.SubjectGroupDetailsComparator;
/**
 * 
 * 
 * Class implemented to handle admission and application(both off line and online) related activities.
 * 
 */

@SuppressWarnings("deprecation")
public class AdmissionFormAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(AdmissionFormAction.class);
	private static final String OTHER="other";
	private static final String TO_DATEFORMAT="MM/dd/yyyy";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final String COUNTID="countID";
	private static final String PHOTOBYTES="PhotoBytes";
	private static Map<Integer,String> prefNameMap=null;
	private static SubjectGroupDetailsComparator comparator=new SubjectGroupDetailsComparator();
	static {
		comparator.setCompare(1);
		prefNameMap = new HashMap<Integer, String>();

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
	 * saves student details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitAdmissionFormStudentInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("entered submit student info..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		ActionMessages errors=admForm.validate(mapping, request);
		HttpSession session= request.getSession(false);
		//It use for Help,Don't Remove
		session.setAttribute("field","admission_form");

		AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
		try {
			//validations
			validatePhone(admForm, errors);
			validateCommAddress(admForm, errors);
			if(admForm.isOnlineApply()){
				validateOnlineRequireds(admForm, errors);
			}
			validateOtherOptions(admForm, errors);
			validatePassportIfNRI(admForm, errors);

			validateWorkExperience(admForm.getFirstExp(), errors);
			validateWorkExperience(admForm.getSecExp(), errors);
			validateWorkExperience(admForm.getThirdExp(), errors);
			//email comparision
			if(admForm.getEmailId()!=null && !StringUtils.isEmpty(admForm.getEmailId())){
				if(admForm.getConfirmEmailId()!=null && !StringUtils.isEmpty(admForm.getConfirmEmailId())){
						if(!admForm.getEmailId().equals(admForm.getConfirmEmailId())){
							if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
								errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
							}
						}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
					}
				}
			}else if(admForm.getConfirmEmailId()!=null && !StringUtils.isEmpty(admForm.getConfirmEmailId())){
				if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
				}
			}

			if(admForm.getTrainingDuration()!=null && !StringUtils.isEmpty(admForm.getTrainingDuration()) && !StringUtils.isNumeric(admForm.getTrainingDuration())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DURATION_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_DURATION_INVALID));
				}
			}

			// validate height and weight
			if(admForm.getHeight()!=null && !StringUtils.isEmpty(admForm.getHeight()) && !StringUtils.isNumeric(admForm.getHeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID));
				}
			}

			if(admForm.getWeight()!=null && !StringUtils.isEmpty(admForm.getWeight()) && !CommonUtil.isValidDecimal(admForm.getWeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID));
				}
			}
			if(admForm.getDateOfBirth()!=null && !StringUtils.isEmpty(admForm.getDateOfBirth())&& !CommonUtil.isValidDate(admForm.getDateOfBirth())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
				}
			}


			if (admForm.getDateOfBirth()!=null && !StringUtils.isEmpty(admForm.getDateOfBirth())&& CommonUtil.isValidDate(admForm.getDateOfBirth())) {

				final boolean	isValid = validatefutureDate(admForm.getDateOfBirth());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
					}

				}
			}
			if(admForm.isOnlineApply() && admForm.getAgeToLimit()!=0 && admForm.getDateOfBirth()!=null && !StringUtils.isEmpty(admForm.getDateOfBirth()) && CommonUtil.isValidDate(admForm.getDateOfBirth())){
					boolean valid=validateOnlineDOB(admForm.getAgeFromLimit(),admForm.getAgeToLimit(),admForm.getDateOfBirth());
					if(!valid){
						if (errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS, new ActionError(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS));
						}
					}
			}

			//passport validation
			if(admForm.getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getPassportValidity())){


				if(CommonUtil.isValidDate(admForm.getPassportValidity())){
					boolean isValid=validatePastDate(admForm.getPassportValidity());
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
			if(admForm.getPermitDate()!=null && !StringUtils.isEmpty(admForm.getPermitDate())&& !CommonUtil.isValidDate(admForm.getPermitDate())){
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID,new ActionError(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID));
				}
			}
			if(!admForm.isOnlineApply() && (admForm.getApplicationNumber()==null || StringUtils.isEmpty(admForm.getApplicationNumber()))){
				if (errors.get(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED));
				}
			}


			//validation moved to application info submit

//			if(errors==null|| errors.isEmpty() ){
//				final Calendar cal= Calendar.getInstance();
//				cal.setTime(new Date());
//				int year=cal.get(cal.YEAR);
//				if(admForm.getChallanNo()!=null && !StringUtils.isEmpty(admForm.getChallanNo().trim()) 
//				&& admForm.getJournalNo()!=null && !StringUtils.isEmpty(admForm.getJournalNo().trim())
//				&& admForm.getApplicationDate()!=null && !StringUtils.isEmpty(admForm.getApplicationDate().trim())
//				&& CommonUtil.isValidDate(admForm.getApplicationDate())){
//					
//					boolean duplicate = handler.checkPaymentDetails(admForm.getChallanNo(),admForm.getJournalNo(),admForm.getApplicationDate(),year);
//					if(duplicate){
//						if(errors==null){
//							errors= new ActionMessages();
//						}
//						if (errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE).hasNext()) {
//							errors.add(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE, new ActionError(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE));
//						}	
//					}
//				}
//			}

			// if errors

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_STUDENTPAGE);
				else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_STUDENTPAGE);
			}

			AdmissionFormHelper helper = AdmissionFormHelper.getInstance();
			PersonalDataTO studentpersonaldata = helper
					.getStudentPersonaldataTofromForm(admForm);
			if (session != null) {
				int appliedyear=0;
				if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
				{
					log.info("application no set session application data...");
					AdmAppln applicationdata=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);
					if(applicationdata!=null){
						appliedyear=applicationdata.getAppliedYear();
					}
				}
				admForm.setApplicationYear(String.valueOf(appliedyear));
				boolean appNoConfigured=handler.checkApplicationNoConfigured(appliedyear, admForm.getCourseId());
				if(!appNoConfigured){
					ActionMessages message = new ActionMessages();
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTCONFIGURED);
					message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTCONFIGURED,error);
					saveErrors(request, message);
					admForm.setApplicationError(true);
					if(admForm.isOnlineApply())
						return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_STUDENTPAGE);
					else
						return mapping.findForward(CMSConstants.ADMISSIONFORM_STUDENTPAGE);
				}
				if (!admForm.isOnlineApply()) {
					boolean applnNoInrange = handler.checkApplicationNoInRange(
							admForm.getApplicationNumber(), admForm
									.isOnlineApply(), appliedyear, admForm
									.getCourseId());
					if (!applnNoInrange) {
						ActionMessages message = new ActionMessages();
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTINRANGE);
						message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTINRANGE,error);
						saveErrors(request, message);
						admForm.setApplicationError(true);
						return mapping.findForward(CMSConstants.ADMISSIONFORM_STUDENTPAGE);
					}
				}
				boolean result = handler.saveStudentPersonaldataToSession(
						studentpersonaldata, admForm, session);

				if(!result){
					ActionMessages message = new ActionMessages();
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE);
					message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE, error);
					saveErrors(request, message);
					admForm.setApplicationError(true);
					if(admForm.isOnlineApply())
						return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_STUDENTPAGE);
					else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_STUDENTPAGE);
				}
			} else {
				cleanupSessionData(session);
				return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
			}
		}catch (ApplicationException e) {

			log.error("error submit student page...",e);
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		catch (Exception e) {

			log.error("error submit student page...",e);
				throw e;
		}
		log.info("exit submit student info..");

		return mapping.findForward(CMSConstants.ADMISSIONFORM_INIT_EDUCATION_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INIT_EDUCATION_PAGE);
	}
	/**
	 * checks year difference
	 * @param ageFromLimit
	 * @param ageToLimit
	 * @param dateOfBirth 
	 * @return
	 */
	private boolean validateOnlineDOB(int ageFromLimit, int ageToLimit, String dateOfBirth) {

		String formattedString=CommonUtil.ConvertStringToDateFormat(dateOfBirth, AdmissionFormAction.FROM_DATEFORMAT,AdmissionFormAction.TO_DATEFORMAT);
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
	 * validates online required fields
	 * @param admForm
	 * @param errors
	 */
	private void validateOnlineRequireds(AdmissionFormForm admForm,
			ActionMessages errors) {/*
		log.info("entered validateOnlineRequireds..");
		if(errors==null){
			errors= new ActionMessages();
		}
		if((admForm.getBirthPlace()==null || StringUtils.isEmpty(admForm.getBirthPlace())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED, error);
			}
		}
		if(admForm.getCountry()==null || StringUtils.isEmpty(admForm.getCountry()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED, error);
			}
		}
		if((admForm.getBloodGroup()==null || StringUtils.isEmpty(admForm.getBloodGroup())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED, error);
			}
		}

		if((admForm.getEmailId()==null || StringUtils.isEmpty(admForm.getEmailId())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED, error);
			}
		}

		if(admForm.getAreaType()==' ')
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED, error);
			}
		}

		if((admForm.getBirthState()==null || StringUtils.isEmpty(admForm.getBirthState())|| admForm.getBirthState().equalsIgnoreCase(AdmissionFormAction.OTHER))&& (admForm.getOtherBirthState()==null ||StringUtils.isEmpty(admForm.getOtherBirthState()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED, error);
			}
		}

		if((admForm.getPhone1()==null || StringUtils.isEmpty(admForm.getPhone1())) && (admForm.getPhone2()==null || StringUtils.isEmpty(admForm.getPhone2())) && (admForm.getPhone3()==null || StringUtils.isEmpty(admForm.getPhone3())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED, error);
			}
		}


		AddressTO tempAddTo=admForm.getPermAddr();
		if (!admForm.isSameTempAddr()) {
			if (tempAddTo.getAddrLine1() == null
					|| StringUtils.isEmpty(tempAddTo.getAddrLine1())) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED,
						error);
			}
			if (tempAddTo.getCity() == null
					|| StringUtils.isEmpty(tempAddTo.getCity())) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED,
						error);
			}
			if (tempAddTo.getCountryId() == null
					|| StringUtils.isEmpty(tempAddTo.getCountryId())) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED,
						error);
			}
			if (tempAddTo.getPinCode() == null
					|| StringUtils.isEmpty(tempAddTo.getPinCode())) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED, error);
			}else if(!StringUtils.isNumeric(tempAddTo.getPinCode())){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID,error);
			}
			if ((tempAddTo.getStateId() == null || StringUtils
					.isEmpty(tempAddTo.getStateId()))
					&& ((admForm.getPermAddr().getOtherState()) == null || StringUtils
							.isEmpty(tempAddTo.getOtherState()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED,
							error);
				}
			}


			//raghu
			if ((tempAddTo.getDistrictId() == null || StringUtils.isEmpty(tempAddTo.getDistrictId()))	
					&& ((admForm.getPermAddr().getOtherDistrict()) == null || StringUtils.isEmpty(tempAddTo.getOtherDistrict()))) {


				if (errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMDISTRICT_REQUIRED, error);
				}
			}


		}
		log.info("exit validateOnlineRequireds..");
			 */}
	/**
	 * future date validation
	 * @param dateString
	 * @return
	 */
	public static boolean validatefutureDate(String dateString) {
		log.info("enter validatefutureDate..");
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, AdmissionFormAction.FROM_DATEFORMAT,AdmissionFormAction.TO_DATEFORMAT);
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
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, AdmissionFormAction.FROM_DATEFORMAT,AdmissionFormAction.TO_DATEFORMAT);
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
	 * application form validation
	 * @param TO
	 * @param errors
	 */
	private void validateWorkExperience(ApplicantWorkExperienceTO TO,ActionMessages errors) {/*
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
				String formdate=CommonUtil.ConvertStringToDateFormat(TO.getFromDate(), AdmissionFormAction.FROM_DATEFORMAT,AdmissionFormAction.TO_DATEFORMAT);
				String todate=CommonUtil.ConvertStringToDateFormat(TO.getToDate(), AdmissionFormAction.FROM_DATEFORMAT,AdmissionFormAction.TO_DATEFORMAT);
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
	 */}
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateOtherOptions(AdmissionFormForm admForm,
			ActionMessages errors) throws Exception{/*
		log.info("enter validateOtherOptions..");
		if(errors==null){
			errors= new ActionMessages();
		}

		if((admForm.getReligionId()==null || StringUtils.isEmpty(admForm.getReligionId())|| (admForm.getReligionId().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getOtherReligion()==null ||StringUtils.isEmpty(admForm.getOtherReligion()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED, error);
			}
		}
		if(admForm.getReligionId()!=null && !StringUtils.isEmpty(admForm.getReligionId()) && StringUtils.isNumeric(admForm.getReligionId())){
			ISubReligionTransaction relTxn=new SubReligionTransactionImpl();
			//if master entry exists
			if(relTxn.checkSubReligionExists(Integer.parseInt(admForm.getReligionId()))){
				if((admForm.getSubReligion()==null || StringUtils.isEmpty(admForm.getSubReligion())|| (admForm.getSubReligion().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getOtherSubReligion()==null ||StringUtils.isEmpty(admForm.getOtherSubReligion())) )
					if(admForm.isCasteDisplay()){
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

		if((admForm.getCastCategory()==null || StringUtils.isEmpty(admForm.getCastCategory())|| (admForm.getCastCategory().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getOtherCastCategory()==null ||StringUtils.isEmpty(admForm.getOtherCastCategory()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_CASTE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED, error);
			}
		}



		//raghu
		if((admForm.getApplicantDetails().getPersonalData().getCasteId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteId())|| (admForm.getApplicantDetails().getPersonalData().getCasteId().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getCasteOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED, error);
			}
		}


		if (admForm.getPermAddr().getPinCode() != null
				&& !StringUtils.isEmpty(admForm.getPermAddr().getPinCode())&& !StringUtils.isNumeric(admForm.getPermAddr().getPinCode())){
			if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID,error);
			}
		}


		log.info("exit validateOtherOptions..");
			 */}
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditOtherOptions(AdmissionFormForm admForm,
			ActionMessages errors) throws Exception {/*
		log.info("enter validateEditOtherOptions..");
		if(errors==null){
			errors= new ActionMessages();
		}
//		if((admForm.getApplicantDetails().getPersonalData().getBirthState()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthState())|| admForm.getApplicantDetails().getPersonalData().getBirthState().equalsIgnoreCase(AdmissionFormAction.OTHER))&& (admForm.getApplicantDetails().getPersonalData().getStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getStateOthers()) ))
//		{
//			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED).hasNext()) {
//				ActionMessage error = new ActionMessage(
//						CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED);
//				errors.add(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED, error);
//			}
//		}
		if(admForm.getApplicantDetails().getPersonalData().isHandicapped()){
			if((admForm.getApplicantDetails().getPersonalData().getHadnicappedDescription()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getHadnicappedDescription()))){
				if (errors.get(CMSConstants.ERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage("errors.required","Hadicapped Description");
					errors.add(CMSConstants.ERROR, error);
				}
			}
		}
		if((admForm.getApplicantDetails().getPersonalData().getReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId())|| (admForm.getApplicantDetails().getPersonalData().getReligionId().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionOthers()) ))
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
					if((admForm.getApplicantDetails().getPersonalData().getSubReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getSubReligionId())|| (admForm.getApplicantDetails().getPersonalData().getSubReligionId().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers())) )
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED, error);
						}
					}


					//raghu
					if((admForm.getApplicantDetails().getPersonalData().getCasteId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteId())|| (admForm.getApplicantDetails().getPersonalData().getCasteId().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getCasteOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteOthers()) ))
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
		if(!admForm.getIsPresidance()){
			if((admForm.getApplicantDetails().getPersonalData().getCasteId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteId())|| (admForm.getApplicantDetails().getPersonalData().getCasteId().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getCasteOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteOthers()) ))
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_CASTE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED, error);
				}
			}
		}
		//raghu change
		//if(!admForm.getIsPresidance()){
		if((admForm.getApplicantDetails().getPersonalData().getCasteId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteId())|| (admForm.getApplicantDetails().getPersonalData().getCasteId().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getCasteOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED, error);
			}
		}
	//}
		if((admForm.getApplicantDetails().getPersonalData().getPermanentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentStateId()) || admForm.getApplicantDetails().getPersonalData().getPermanentStateId().equalsIgnoreCase("0"))&& ((admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers())==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED, error);
			}
		}
		log.info("exit validateEditOtherOptions..");
			 */}
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	private void validatePhone(AdmissionFormForm admForm, ActionMessages errors) {/*
		log.info("enter validatePhone..");
		if(errors==null)
			errors= new ActionMessages();

				if(admForm.getPhone1()!=null && !StringUtils.isEmpty(admForm.getPhone1()) && !StringUtils.isNumeric(admForm.getPhone1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				if(admForm.getPhone2()!=null && !StringUtils.isEmpty(admForm.getPhone2()) && !StringUtils.isNumeric(admForm.getPhone2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				if(admForm.getPhone3()!=null && !StringUtils.isEmpty(admForm.getPhone3()) && !StringUtils.isNumeric(admForm.getPhone3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}

//				if(admForm.getMobile1()!=null && !StringUtils.isEmpty(admForm.getMobile1()) && !StringUtils.isNumeric(admForm.getMobile1()) )
//				{
//					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
//						ActionMessage error = new ActionMessage(
//								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
//						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
//					}
//				}
				if(admForm.getMobile2()!=null && !StringUtils.isEmpty(admForm.getMobile2()) && admForm.getMobile2().trim().length()!=10 )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				if(admForm.getMobile2()!=null && !StringUtils.isEmpty(admForm.getMobile2()) && !StringUtils.isNumeric(admForm.getMobile2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
//				if(admForm.getMobile3()!=null && !StringUtils.isEmpty(admForm.getMobile3()) && !StringUtils.isNumeric(admForm.getMobile3()) )
//				{
//					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
//						ActionMessage error = new ActionMessage(
//								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
//						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
//					}
//				}
				log.info("exit validatePhone..");
	 */}
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPhone(AdmissionFormForm admForm, ActionMessages errors) {/*
		log.info("enter validateEditPhone..");
		if(errors==null)
			errors= new ActionMessages();

				if((admForm.getApplicantDetails().getPersonalData().getPhNo1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1()))
						&& (admForm.getApplicantDetails().getPersonalData().getPhNo2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2()))
						&& (admForm.getApplicantDetails().getPersonalData().getPhNo3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3())))
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED, error);
					}
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

//				if(admForm.getApplicantDetails().getPersonalData().getMobileNo1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) )
//				{
//					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
//						ActionMessage error = new ActionMessage(
//								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
//						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
//					}
//				}


				if((admForm.getResidentCategoryForOnlineAppln()!=null && !admForm.getResidentCategoryForOnlineAppln().isEmpty()) 
						&& CMSConstants.INDIAN_RESIDENT_LIST.contains(Integer.parseInt(admForm.getResidentCategoryForOnlineAppln()))){
					if(admForm.getMobileNo2()!=null && !admForm.getMobileNo2().isEmpty() && admForm.getMobileNo2().length()>10){
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED));
					}
					admForm.setIndianCandidate(true);

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
				log.info("exit validateEditPhone..");
	 */}
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	public static void validateParentPhone(AdmissionFormForm admForm, ActionMessages errors) {/*
		log.info("enter validateParentPhone..");
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
				log.info("exit validateParentPhone..");
	 */}
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	public static void validateGuardianPhone(AdmissionFormForm admForm, ActionMessages errors) {/*
		log.info("enter validateGuardianPhone..");
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
				log.info("exit validateGuardianPhone..");
	 */}

	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditGuardianPhone(AdmissionFormForm admForm, ActionMessages errors) {/*
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
	 */}




	private void validateEditParentPhoneMandatory(AdmissionFormForm admForm, ActionMessages errors) {/*
		log.info("enter validateEditParentPhone..");
		if(errors==null)
			errors= new ActionMessages();
				if((admForm.getApplicantDetails().getPersonalData().getParentMob3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentMob3())) && 
						(admForm.getApplicantDetails().getPersonalData().getGuardianMob3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianMob3())) && 
						(admForm.getApplicantDetails().getPersonalData().getGuardianPh3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianPh3())) &&
						(admForm.getApplicantDetails().getPersonalData().getParentPh3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentPh3())))
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTGUARDIAN_CONTACT_NUMBER_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTGUARDIAN_CONTACT_NUMBER_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTGUARDIAN_CONTACT_NUMBER_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTGUARDIAN_CONTACT_NUMBER_REQUIRED, error);
					}
				}



				log.info("exit validateEditParentPhone..");
	 */}



	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditParentPhone(AdmissionFormForm admForm, ActionMessages errors) {/*
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
	 */}
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	public static void validateCommAddress(AdmissionFormForm admForm,
			ActionMessages errors) {/*
		log.info("enter validateCommAddress..");
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

			//raghu
			if ((tempAddTo.getDistrictId() == null || StringUtils.isEmpty(tempAddTo.getDistrictId()))	
					&& ((admForm.getPermAddr().getOtherDistrict()) == null || StringUtils.isEmpty(tempAddTo.getOtherDistrict()))) {


				if (errors.get(CMSConstants.ADMISSIONFORM_COMM_DISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_COMM_DISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_DISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_COMM_DISTRICT_REQUIRED, error);
				}
			}


			log.info("exit validateCommAddress..");
			 */}
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditCommAddress(AdmissionFormForm admForm,
			ActionMessages errors) {/*
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
			if((admForm.getApplicantDetails().getPersonalData().getCurrentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentStateId()) || admForm.getApplicantDetails().getPersonalData().getCurrentStateId().equalsIgnoreCase("0"))&& (admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED, error);
				}
			}


			if((admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()))&& (admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED, error);
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




			log.info("exit validateEditCommAddress..");
			 */}
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditCoursePreferences(AdmissionFormForm admForm,
			ActionMessages errors) {/*
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
			 */}

	/**
	 * application form PASSPORT DATA validation
	 * @param admForm
	 * @param errors
	 */
	private  void validatePassportIfNRI(AdmissionFormForm admForm,
			ActionMessages errors) {/*
		log.info("enter validatePassportIfNRI..");
		if(errors==null)
			errors= new ActionMessages();
		if(admForm.getResidentCategory()!=null && !StringUtils.isEmpty(admForm.getResidentCategory())){
			if(admForm.getResidentCategory()!=null && StringUtils.isNumeric(admForm.getResidentCategory())&& !CMSConstants.INDIAN_RESIDENT_LIST.contains(Integer.valueOf((Integer.parseInt(admForm.getResidentCategory()))))){
				if(admForm.getPassportNo()==null || StringUtils.isEmpty(admForm.getPassportNo()))
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORTNO_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORTNO_REQUIRED,error);
				}
				if(admForm.getPassportcountry()==null || StringUtils.isEmpty(admForm.getPassportcountry()))
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORT_COUNTRY_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_COUNTRY_REQUIRED,error);
				}
				if(admForm.getPassportValidity()==null || StringUtils.isEmpty(admForm.getPassportValidity()))
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_REQUIRED,error);
				}else if(admForm.getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getPassportValidity())){
					if(CommonUtil.isValidDate(admForm.getPassportValidity())){
					boolean isValid=validatePastDate(admForm.getPassportValidity());
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
		log.info("exit validatePassportIfNRI..");
			 */}
	/**
	 * admission form PASSPORT DATA validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPassportIfNRI(AdmissionFormForm admForm,
			ActionMessages errors) {/*
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
			 */}
	/**
	 * saves student EDUCATION details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEducationPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init education info..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			admForm.setTcDate(null);
			admForm.setTcNo(null);
			admForm.setMarkcardDate(null);
			admForm.setMarkcardNo(null);
			admForm.setEntranceId(null);
			admForm.setEntranceMarkObtained(null);
			admForm.setEntranceTotalMark(null);
			admForm.setEntranceMonthPass(null);
			admForm.setEntranceYearPass(null);
			admForm.setEntranceRollNo(null);
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			List<EdnQualificationTO> qualifications = handler
					.getEdnQualifications(admForm);
			admForm.setQualifications(qualifications);
			// Admitted Through
			List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
			admForm.setAdmittedThroughList(admittedList);
			UniversityHandler unhandler = UniversityHandler.getInstance();
			List<UniversityTO> universities = unhandler.getUniversity();
			admForm.setUniversities(universities);

			List<StateTO> ednstates=StateHandler.getInstance().getNativeStates(CMSConstants.KP_DEFAULT_COUNTRY_ID);
			admForm.setEdnStates(ednstates);

			List<EntrancedetailsTO> entrnaceList=EntranceDetailsHandler.getInstance().getEntranceDeatilsByProgram(Integer.parseInt(admForm.getProgramId()));
			admForm.setEntranceList(entrnaceList);

		}catch(ApplicationException e){
			log.error("error in init education page...",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in init education page...",e);
			throw e;

		}
		log.info("exit init education info..");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_EDUCATION_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_EDUCATION_PAGE);
	}
	/**
	 * saves parent details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitAdmissionFormParentInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submit full application..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		ActionMessages errors=admForm.validate(mapping, request);
		validateParentPhone(admForm, errors);
		validateGuardianPhone(admForm, errors);
		if(admForm.isOnlineApply()){
			validateParentOnlineRequireds(admForm, errors);
		}
		if(errors!=null && !errors.isEmpty() )
				{
					saveErrors(request, errors);
					if(admForm.isOnlineApply())
						return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_PARENT_PAGE);
					else
						return mapping.findForward(CMSConstants.ADMISSIONFORM_PARENT_PAGE);
				}
		try {
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			HttpSession session = request.getSession(false);
			if (session == null) {
				return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
			}
			int appliedyear=0;
			AdmAppln applicationdata=null;
			if (session != null) {
				// get applied year
				if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
				{
					log.info("application no set session application data...");
					applicationdata=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);
					if(applicationdata!=null){
						appliedyear=applicationdata.getAppliedYear();
						applicationdata.setIsApproved(!admForm.isNeedApproval());
						applicationdata.setIsLig(false);
					}
				}
			}
			// if online,generate online application number
			if(admForm.isOnlineApply()){
				String applnNOgenerated=handler.getGeneratedOnlineAppNo(admForm.getCourseId(),appliedyear,true);
				if(applnNOgenerated!=null && applicationdata!=null && !StringUtils.isEmpty(applnNOgenerated) && StringUtils.isNumeric(applnNOgenerated) )
				{
					applicationdata.setApplnNo(Integer.parseInt(applnNOgenerated));
					admForm.setApplicationNumber(applnNOgenerated);
					session.setAttribute(CMSConstants.APPLICATION_DATA, applicationdata);
				}else{
					// sends mail to admin to configure applnno.
					handler.sendMailToAdmin(admForm,appliedyear);
					ActionMessages message = new ActionMessages();
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL);
					message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL, error);
					saveErrors(request, message);
					cleanUpPageData(admForm);
					if(admForm.isOnlineApply()){
						return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
					}else{
					return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
					}
				}
			}else{
				String applnNOoffLine=admForm.getApplicationNumber();
				if(applnNOoffLine!=null && applicationdata!=null  && !StringUtils.isEmpty(applnNOoffLine) && StringUtils.isNumeric(applnNOoffLine) )
				{
					applicationdata.setApplnNo(Integer.parseInt(applnNOoffLine));
					session.setAttribute(CMSConstants.APPLICATION_DATA, applicationdata);
				}
			}




//			boolean result = false;
//				
//				result = handler.saveCompleteApplication(session, admForm);
//			
//			if (result) {
//				// sends mail to student after successful submit
//				handler.sendMailToStudent(admForm);
//				request.setAttribute("transactionstatus", "success");
//				ActionMessages messages = new ActionMessages();
//				ActionMessage message = new ActionMessage(
//						CMSConstants.KNOWLEDGEPRO_SUCCESS_STATUS,applicationdata.getApplnNo());
//				messages.add("messages", message);
//				saveMessages(request, messages);
//				cleanupSessionData(session);
//				cleanUpPageData(admForm);
//			}
//			else {
//				ActionMessages message = new ActionMessages();
//				ActionMessage error = new ActionMessage(
//						CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE);
//				message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE, error);
//				saveErrors(request, message);
//				return mapping.findForward(CMSConstants.ADMISSIONFORM_PARENT_PAGE);
//			}


			// above section will be implemented as confirm page >> edit >> submit, print

			AdmApplnTO applicantdetails=handler.getCompleteApplication(session, admForm);
			admForm.setApplicantDetails(applicantdetails);

			setOtherReviewRequireds(admForm,request);

			log.info("exit submit full application..");
			if(admForm.isOnlineApply())
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
			else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);

		}catch (BusinessException e) {

			log.error("error in final submit of application page...",e);
			String msgKey=super.handleBusinessException(e);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = new ActionMessage(
					msgKey);
			messages.add("messages", message);
			if(admForm.isOnlineApply())
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_PARENT_PAGE);
			else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_PARENT_PAGE);
		} catch (ApplicationException e) {

			log.error("error in final submit of application page...",e);
			String msg = super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		catch (Exception e) {

			log.error("error in final submit of application page...",e);
			throw e;	
		}

			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_PARENT_PAGE);	
	}

	/**
	 * online parent mandatory check
	 * @param admForm
	 * @param errors
	 */
	private void validateParentOnlineRequireds(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("entered validateParentOnlineRequireds..");
		if(errors==null){
			errors= new ActionMessages();
		}
		if((admForm.getFatherName()==null || StringUtils.isEmpty(admForm.getFatherName())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED, error);
			}
		}

		if((admForm.getMotherName()==null || StringUtils.isEmpty(admForm.getMotherName())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED, error);
			}
		}
		if((admForm.getFatherIncome()==null || StringUtils.isEmpty(admForm.getFatherIncome())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED, error);
			}
		}
		if((admForm.getMotherIncome()==null || StringUtils.isEmpty(admForm.getMotherIncome())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED, error);
			}
		}
		log.info("exit validateParentOnlineRequireds..");
	}

	/**
	 * online parent mandatory check
	 * @param admForm
	 * @param errors
	 */
	private void validateParentConfirmOnlineRequireds(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("entered validateParentConfirmOnlineRequireds..");
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
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED, error);
			}
		}
		/*if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getTitleOfMother()!=null && !admForm.getApplicantDetails().getTitleOfMother().isEmpty()
				&& !admForm.getApplicantDetails().getTitleOfMother().equalsIgnoreCase("Late")
				&&(admForm.getApplicantDetails().getPersonalData().getMotherIncomeId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMotherIncomeId())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED, error);
			}
		}*/
		log.info("exit validateParentConfirmOnlineRequireds..");
	}
	/**
	 * @param admForm
	 */
	private void setOtherReviewRequireds(AdmissionFormForm admForm, HttpServletRequest request) throws Exception{/*

		if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getDob()!=null )
			admForm.getApplicantDetails().getPersonalData().setDob(CommonUtil.ConvertStringToDateFormat(admForm.getApplicantDetails().getPersonalData().getDob(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));
		if(admForm.getApplicantDetails().getChallanDate()!=null )
			admForm.getApplicantDetails().setChallanDate(CommonUtil.ConvertStringToDateFormat(admForm.getApplicantDetails().getChallanDate(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));

		admForm.setApplicantDetails(admForm.getApplicantDetails());
		String workExpNeeded=admForm.getApplicantDetails().getCourse().getIsWorkExperienceRequired();
		if(admForm.getApplicantDetails().getCourse()!=null && "Yes".equalsIgnoreCase(workExpNeeded))
		{
			admForm.setWorkExpNeeded(true);
		}else{
			admForm.setWorkExpNeeded(false);
		}
		admForm.setWorkExpMandatory(admForm.getApplicantDetails().getCourse().isWorkExpMandatory());
			admForm.setApplicantDetails(admForm.getApplicantDetails());
		ProgramTypeHandler programtypehandler = ProgramTypeHandler.getInstance();
			List<ProgramTypeTO> programtypes = programtypehandler
						.getProgramType();
		CourseTO applicantCourse = admForm.getApplicantDetails().getCourse();
		CourseTO selectedCourse=admForm.getApplicantDetails().getSelectedCourse();
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
										}
									}
								}
						}	
					}
				}
			}

			checkExtradetailsDisplay(admForm);
			checkLateralTransferDisplay(admForm);

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
					admForm.setEditGuardianStates(stateList);
				}
			}
		}

		if(admForm.getApplicantDetails().getPersonalData()!=null){

			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
					if(orgTO.getExtracurriculars()!=null)
						admForm.getApplicantDetails().getPersonalData().setStudentExtracurricularsTos(orgTO.getExtracurriculars());
				}

		}

		//Nationality
			AdmissionFormHandler formhandler = AdmissionFormHandler.getInstance();
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
		// caste category
		List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
		admForm.setCasteList(castelist);

		// Admitted Through
		List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
		admForm.setAdmittedThroughList(admittedList);
		// subject Group
		List<SubjectGroupTO> sujectgroupList=SubjectGroupHandler.getInstance().getSubjectGroupDetails(selectedCourse.getId());
		admForm.setSubGroupList(sujectgroupList);
		String[] subjectgroups=admForm.getApplicantDetails().getSubjectGroupIds();
		if (subjectgroups!=null && subjectgroups.length==0 && sujectgroupList!=null) {
			subjectgroups=new String[sujectgroupList.size()];
			admForm.getApplicantDetails().setSubjectGroupIds(subjectgroups);
		}

		//incomes
		List<IncomeTO> incomeList = AdmissionFormHandler.getInstance().getIncomes();
		admForm.setIncomeList(incomeList);

		//currencies
		List<CurrencyTO> currencyList = AdmissionFormHandler.getInstance().getCurrencies();
		admForm.setCurrencyList(currencyList);

		UniversityHandler unhandler = UniversityHandler.getInstance();
		List<UniversityTO> universities = unhandler.getUniversity();
		admForm.setUniversities(universities);

		OccupationTransactionHandler occhandler = OccupationTransactionHandler
		.getInstance();
		admForm.setOccupations(occhandler.getAllOccupation());

		if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportValidity()) )
			admForm.getApplicantDetails().getPersonalData().setPassportValidity(CommonUtil.ConvertStringToDateFormat(admForm.getApplicantDetails().getPersonalData().getPassportValidity(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));

		// set photo to session
		if(admForm.getApplicantDetails().getEditDocuments()!=null){
			Iterator<ApplnDocTO> docItr=admForm.getApplicantDetails().getEditDocuments().iterator();
			while (docItr.hasNext()) {
				ApplnDocTO docTO = (ApplnDocTO) docItr.next();
				if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
				{
					admForm.setSubmitDate(docTO.getSubmitDate());
				}
				if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
					HttpSession session= request.getSession(false);
					if(session!=null){
						session.setAttribute(AdmissionFormAction.PHOTOBYTES, docTO.getPhotoBytes());
					}
				}
			}
		}



		// preferences

		if (admForm.getApplicantDetails().getOriginalPreferences() != null) {
			List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
			Iterator<CandidatePreference> iterator = admForm.getApplicantDetails().getOriginalPreferences().iterator();
			while (iterator.hasNext()) {
				CandidatePreference preferenceBO = (CandidatePreference) iterator.next();
				if(preferenceBO.getPrefNo() == 1){
					CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(firstTo,applicantCourse);
					firstTo.setPrefNo(1);
					// as course name is in fomr set it to display
					firstTo.setCoursName(admForm.getCourseName());
					firstTo.setCoursId(String.valueOf(applicantCourse.getId()));
					prefTos.add(firstTo);
				}else if (preferenceBO.getPrefNo() == 2) {
					CandidatePreferenceTO secTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(secTo,applicantCourse);
					secTo.setPrefNo(2);
					secTo.setCoursId(String.valueOf(preferenceBO.getCourse().getId()));
					prefTos.add(secTo);
				} else if (preferenceBO.getPrefNo() == 3) {
					CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(thirdTo,applicantCourse);
					thirdTo.setCoursId(String.valueOf(preferenceBO.getCourse().getId()));
					thirdTo.setPrefNo(3);
					prefTos.add(thirdTo);
				}

			}
			if(prefTos!=null && prefTos.size()<3){
				int sizediff=3-prefTos.size();
				if(sizediff==2){
					CandidatePreferenceTO secTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(secTo,applicantCourse);
					secTo.setCoursId(null);
					secTo.setPrefNo(2);
					prefTos.add(secTo);
					CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(thirdTo,applicantCourse);
					thirdTo.setCoursId(null);
					thirdTo.setPrefNo(3);
					prefTos.add(thirdTo);
				}else if(sizediff==1){
					CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(thirdTo,applicantCourse);
					thirdTo.setCoursId(null);
					thirdTo.setPrefNo(3);
					prefTos.add(thirdTo);
				}
			}
			Collections.sort(prefTos);
			admForm.setPreferenceList(prefTos);
		}

		// Re-position the education list
		if (admForm.getQualifications() != null) {
			Iterator<EdnQualificationTO> itr= admForm.getApplicantDetails().getEdnQualificationList().iterator();


			while (itr.hasNext()) {
				EdnQualificationTO qualificationTO = (EdnQualificationTO) itr
						.next();
				Iterator<EdnQualificationTO> itr2= admForm.getQualifications().iterator();
				while (itr2.hasNext()) {
					EdnQualificationTO origTO = (EdnQualificationTO) itr2.next();
					if(origTO.getDocCheckListId()!=0 && 
							origTO.getDocCheckListId()==qualificationTO.getDocCheckListId()){
						qualificationTO.setCountId(origTO.getCountId());
					}

				}
			}
		}


	 */}
	/**
	 * cleaned up data
	 * @param admForm
	 */
	private void cleanUpPageData(AdmissionFormForm admForm) {
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
		}
		log.info("exit cleanUpPageData..");
	}

	public ActionForward savePreferences(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = admForm.validate(mapping, request);
		setUserId(request,admForm);
		admForm.setCourseName(admForm.getCourseId());
		if(errors.isEmpty()){
			try{
				List<CourseTO> courselist= admForm.getPrefcourses(); 
				if (courselist != null) {
					Iterator<CourseTO> qualItr=courselist.iterator();
					while (qualItr.hasNext()) {
						CourseTO qualTO = (CourseTO) qualItr.next();
						CandidatePreferenceTO candidatePreferenceTO=new CandidatePreferenceTO();
						if(qualTO.getId()!=0){

							qualTO.setPrefNo(qualTO.getPrefNo());
						}
					}
				}
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		//return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);

	}


	/**
	 * submit uploaded docs.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitAdmissionFormAttachments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit attachment...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		ActionMessages errors= admForm.validate(mapping, request);
		if(errors==null)
			errors= new ActionMessages();
		try {
			validateDocumentSize(admForm, errors);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_ATTACHMENT_PAGE);
				else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ATTACHMENT_PAGE);
			}
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			HttpSession session = request.getSession(false);
			if (session == null)
				return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
			handler.persistAdmissionFormAttachments(admForm, session);
		} catch(ApplicationException e){
			log.error("error in submit education page...",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in submit education page...",e);
			throw e;

		}
		log.info("exit submit attachment...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INIT_PARENT_PAGE);
	}

	/**
	 * Validate document size
	 * @param admForm
	 * @param errors
	 */
	public static void validateDocumentSize(AdmissionFormForm admForm,
			ActionMessages errors) {/*
		log.info("enter validate dcument size...");
		List<ApplnDocTO> uploadlist=admForm.getUploadDocs();
		InputStream propStream=AdmissionFormAction.class.getResourceAsStream("/resources/application.properties");
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

			 */}
	/**
	 * Validate document size
	 * @param admForm
	 * @param errors
	 */
	private void validateEditDocumentSize(AdmissionFormForm admForm,
			ActionMessages errors,HttpServletRequest request) throws Exception {/*
		log.info("enter validate dcument size...");
		List<ApplnDocTO> uploadlist=admForm.getApplicantDetails().getEditDocuments();
		InputStream propStream=AdmissionFormAction.class.getResourceAsStream("/resources/application.properties");
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

			 */}
	/**
	 * initialize admission form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdmissionForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init student page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			boolean isCjc = CMSConstants.LINK_FOR_CJC;
			admForm.setIsCjc1(isCjc);
			List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
			admForm.setCasteList(castelist);
			//set caste show or not
			admForm.setCasteDisplay(CMSConstants.CASTE_ENABLED);
			ProgramTypeHandler programtypehandler = ProgramTypeHandler
					.getInstance();
			List<ProgramTypeTO> programtypes = programtypehandler
					.getProgramType();
			admForm.setProgramtypeList(programtypes);

			CountryHandler countryhandler = CountryHandler.getInstance();
			admForm.setCountries(countryhandler.getCountries());

			CourseHandler crsHandler= CourseHandler.getInstance();
			List<CourseTO> courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
			String courseName="";
			//SET ALL PROGRAM LEVEL CONFIGURATIONS
			if(courselist!=null && ! courselist.isEmpty()){
				CourseTO to=courselist.get(0);
				if(to!=null){
					ProgramTO progTo=to.getProgramTo();
					if(progTo!=null){ 

						admForm.setDisplayMotherTongue(progTo.getIsMotherTongue());

						admForm.setDisplayLanguageKnown(progTo.getIsDisplayLanguageKnown());
						admForm.setDisplayHeightWeight(progTo.getIsHeightWeight());
						admForm.setDisplayTrainingDetails(progTo.getIsDisplayTrainingCourse());
						admForm.setDisplayAdditionalInfo(progTo.getIsAdditionalInfo());
						admForm.setDisplayExtracurricular(progTo.getIsExtraDetails());
						admForm.setDisplaySecondLanguage(progTo.getIsSecondLanguage());
						admForm.setDisplayFamilyBackground(progTo.getIsFamilyBackground());
						admForm.setDisplayLateralDetails(progTo.getIsLateralDetails());
						admForm.setDisplayTransferCourse(progTo.getIsTransferCourse());
						admForm.setDisplayEntranceDetails(progTo.getIsEntranceDetails());
						admForm.setDisplayTCDetails(progTo.getIsTCDetails());
						admForm.setProgramId(String.valueOf(progTo.getId()));
						if(progTo.getProgramTypeTo()!=null){
							admForm.setAgeFromLimit(Integer.parseInt(progTo.getProgramTypeTo().getAgeFrom()));
							admForm.setAgeToLimit(Integer.parseInt(progTo.getProgramTypeTo().getAgeTo()));
						}
					}
					courseName=to.getName();
					if(to.getIsDetailMarkPrint()!=null && "Yes".equalsIgnoreCase(to.getIsDetailMarkPrint()))
						admForm.setDetailMarksPrint(true);
					else
						admForm.setDetailMarksPrint(false);
				}
			}
			admForm.setCourseName(courseName);

			// EXTRA DETAILS BLOCK DISPLAY CHECK
			checkExtradetailsDisplay(admForm);
			// LATERAL AND TRANSFER COURSE LINK DISPLAY CHECK
			checkLateralTransferDisplay(admForm);
			//PREPARE ALL REQUIRED DATAS TO SELECT
			AdmissionFormHandler formhandler = AdmissionFormHandler
					.getInstance();
			formhandler.populatePreferenceList(admForm);
			formhandler.setDefaultPreferences(admForm);
			//for ajax setting put preference lists in session
			HttpSession session= request.getSession(false);
			if(session!=null){
				session.setAttribute(CMSConstants.COURSE_PREFERENCES, admForm.getPrefcourses());
			}
			//			formhandler.setWorkExpNeeded(admForm);
			admForm.setResidentTypes(formhandler.getResidentTypes());
			admForm.setNationalities(formhandler.getNationalities());
			ReligionHandler religionhandler = ReligionHandler.getInstance();
			admForm.setReligions(religionhandler.getReligion());
			LanguageHandler langHandler=LanguageHandler.getHandler();
			admForm.setMothertongues(langHandler.getLanguages());
			admForm.setLanguages(langHandler.getOriginalLanguages());
			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();


			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
				admForm.setNeedApproval(orgTO.isNeedApproval());
				if(admForm.isDisplayAdditionalInfo())
				{
					admForm.setOrganizationName(orgTO.getOrganizationName());

				}
				if(admForm.isDisplayExtracurricular()){
					if(orgTO.getExtracurriculars()!=null)
						admForm.setExtracurriculars(orgTO.getExtracurriculars());
				}
			}


		} catch(ApplicationException e){
			log.error("error in init student page...",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in init studentpage...",e);
			throw e;

		}
		log.info("exit init student page...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_STUDENTPAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_STUDENTPAGE);
	}
	/**
	 * EXTRA DETAILS BLOCK DISPLAY CHECK
	 * @param admForm
	 */
	private void checkExtradetailsDisplay(AdmissionFormForm admForm) {
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
		//athira
		admForm.setDisplayExtraDetails(false);

	}
	/**
	 * LATERAL AND TRANSFER COURSE LINK DISPLAY CHECK
	 * @param admForm
	 */
	private void checkLateralTransferDisplay(AdmissionFormForm admForm) {
		boolean isextra=false;

		if(admForm.isDisplayLateralDetails())
			isextra=true;
		if(admForm.isDisplayTransferCourse())
			isextra=true;
		admForm.setDisplayLateralTransfer(isextra);

	}
	/**
	 * initialize application form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApplicationForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init application detail page...");
		HttpSession session= request.getSession(true);
		try {
			cleanupSessionData(session);
			cleanupFormFromSession(session);
			cleanupEditSessionData(request);
		} 
		catch (Exception e) {
			log.error("error in init studentpage...",e);
			throw e;

		}

		log.info("exit init application detail page...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_FIRST_PAGE);
	}

	/**
	 * initialize application form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadInitApplicationForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter loadInitApplicationForm...");
		AdmissionFormForm admissionFormForm=(AdmissionFormForm)form;
		ActionMessages errors=admissionFormForm.validate(mapping, request);
		if (admissionFormForm.getApplicationNumber() != null && admissionFormForm.getApplicationYear() == null) {
			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED));
			}
		}
		if (admissionFormForm.getApplicationNumber() != null && !StringUtils.isEmpty(admissionFormForm.getApplicationNumber()) && !StringUtils.isNumeric(admissionFormForm.getApplicationNumber())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID));
			}
		}
		if(admissionFormForm.getApplicationNumber() != null && !StringUtils.isEmpty(admissionFormForm.getApplicationNumber().trim()) && StringUtils.isNumeric(admissionFormForm.getApplicationNumber()) && admissionFormForm.getYear() != null && !StringUtils.isEmpty(admissionFormForm.getYear().trim())){
			AdmissionFormHandler handler= AdmissionFormHandler.getInstance();

			boolean applnNoInrange = handler.checkValidOfflineNumber(Integer.parseInt(admissionFormForm.getApplicationNumber()), Integer.parseInt(admissionFormForm.getYear()));
			if (!applnNoInrange) {
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID));
				}

			}
		}
		if(errors!=null && !errors.isEmpty()){
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_FIRST_PAGE);
		} 

		if(admissionFormForm.getApplicationNumber() == null || admissionFormForm.getApplicationNumber().length() == 0) {
			return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSE_SELCTION_PAGE);
		}

		try {
			boolean result = OfflineDetailsHandler.getInstance().getApplicationOfflineDetails(admissionFormForm);
			if(result) {
				setProgramAndCourseMap(admissionFormForm,request);
				request.setAttribute("operation", "load");
				return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSE_SELCTION_PAGE);
			} else {
				if(!admissionFormForm.isCheckOfflineAppNo()) {
					errors.add("error", new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINEDATA_NOTEXIST));
					saveErrors(request,errors);
					admissionFormForm.setCheckOfflineAppNo(true);
					return mapping.findForward(CMSConstants.ADMISSIONFORM_FIRST_PAGE);
				}
			}

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE));
			saveErrors(request,errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_FIRST_PAGE);
		}catch(ApplicationException e){
			log.error("error in loadInitApplicationForm...",e);
			String msg=super.handleApplicationException(e);
			admissionFormForm.setErrorMessage(msg);
			admissionFormForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in init studentpage...",e);
			throw e;

		}
		log.info("exit loadInitApplicationForm...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSE_SELCTION_PAGE);
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
	 * FORWARDS STUDENT INFO PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardStudentAdmissionForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.ADMISSIONFORM_STUDENTPAGE);
	}
	/**
	 * FORWARDS APPLICATION INFO PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardApplicationForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
	}
	/**
	 * FORWARDS EDUCATION INFO PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEducationAdmissionForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_EDUCATION_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_EDUCATION_PAGE);
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
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		if(admForm.getPucApplicationEdit()){
			return mapping.findForward("PUCApplicantEdit");
		}
		if(admForm.getPucApplicationDetailEdit()){
			return mapping.findForward("PUCApplicantDetailEdit");
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE);
	}



	/**
	 * FORWARDS ADMISSION FORM PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEditApplicationPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		if(admForm.getPucApplicationEdit()){
			return mapping.findForward("PUCApplicantEdit");
		}
		if(admForm.getPucApplicationDetailEdit()){
			return mapping.findForward("PUCApplicantDetailEdit");
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
	}
	/**
	 * FORWARDS DOC UPLOAD PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardAttachmentAdmissionForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ATTACHMENT_PAGE);
	}
	/**
	 * FORWARDS PARENT INFO PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardParentAdmissionForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.ADMISSIONFORM_PARENT_PAGE);
	}
	/**
	 * FORWARD CHALLAN TEMPLATE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardChallanTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter forward challan page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			CourseHandler crsHandler= CourseHandler.getInstance();
			String courseName="";
			String payCode="";
			String courseAmount="";
			List<CourseTO> courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
			if(courselist!=null && ! courselist.isEmpty()){
				CourseTO to=courselist.get(0);
				courseName=to.getName();
				if(to.getPayCode()!=null )
					payCode=to.getPayCode();
				if(to.getAmount()!=null)
					courseAmount=String.valueOf(to.getAmount().doubleValue());
			}
			admForm.setCourseName(courseName);
			admForm.setCoursePayCode(payCode);
			admForm.setCourseAmount(courseAmount);
			AdmissionFormHandler handler=AdmissionFormHandler.getInstance();
			IProgramTransaction transaction=new ProgramTransactionImpl();
			Program program=transaction.getProgramDetails(Integer.parseInt(admForm.getProgramId()));
			int year =(program.getAcademicYear()!=null)?program.getAcademicYear():0;
			if(year==0){
				year=Calendar.YEAR;
			}
			String y= String.valueOf(year);
			int year1=Integer.parseInt(y.substring(2));
			//String latestChallanNo=handler.getNewChallanNo();
			String latestChallanNo="";
			admForm.setChallanRefNo(latestChallanNo+"/"+year1+"/"+(year1+1));
		} catch (Exception e) {
			log.error("error in submit application detail page...",e);
			if(e instanceof BusinessException){
				String msgKey=super.handleBusinessException(e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINELINKS_PAGE);
			}
			else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit forward chaalan page...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CHALLAN_PAGE);
	}
	/**
	 * FORWARDS ORIGINALLY SUBMITTED DOC LIST PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardDoclist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into forwardDoclist method");
		//---------------fetch details to print admission details-----------------//
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
			String applicationNumber = (admForm.getApplicationNumber()!=null && admForm.getApplicationNumber().trim().length()>0?admForm.getApplicationNumber():"0");
			int applicationYear = Integer.parseInt(admForm.getApplicationYear());
			AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicationPendingDocDetails(applicationNumber, applicationYear,request);
			if( applicantDetails == null){
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_NORECORDS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_STUDENTPRINT_NORECORD);
			} else {
				//set applicant details
				admForm.setApplicantDetails(applicantDetails);
				String template=AdmissionFormHandler.getInstance().getDeclarationTemplateForPendingDoc(applicantDetails,request);
				admForm.setPendingDocumentTemplate(template);
			}
		}catch (Exception e){
			e.printStackTrace();
			log.error("Error in  getApplicantDetails application form approval page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				throw e;
			}
		}
		log.info("exiting forwardDoclist method");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ORIGINAL_DOCLIST_PAGE);
	}
	/**
	 * saves Application details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public ActionForward submitApplicationFormInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submit application detail page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		setUserId(request, admForm);
		//presently , it is true. it will be false after login implementation
		HttpSession session= request.getSession(false);
		AdmissionFormHandler handler = AdmissionFormHandler.getInstance();

	//	ActionMessages errors=admForm.validate(mapping, request);
		ActionMessages errors= new ActionErrors();
		if(!admForm.getIsPresidance()){
		validateProgramCourse(errors,admForm,false);

		if(admForm.isOnlineApply()){
			//raghu remove
			validatePaymentDetails(errors,admForm);
		}
	int currentyear;
	int year1;
	String date=admForm.getApplicationDate();
	 SimpleDateFormat yourDateFormat = new SimpleDateFormat(
     "mm/dd/yyyy");
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
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE);
		}

		if(errors==null|| errors.isEmpty() ){

			final Calendar cal= Calendar.getInstance();
			cal.setTime(new Date());
			int year=cal.get(cal.YEAR);

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
			//if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() && admForm.getSelectedFeePayment().equalsIgnoreCase("SBI")){
			//raghu	
			if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() && admForm.getSelectedFeePayment().equalsIgnoreCase("CHALLAN")){
				admForm.setDdDate(null);
				admForm.setDdDrawnOn(null);
				admForm.setDdAmount(null);
				admForm.setDdBankCode(null);
				admForm.setDdIssuingBank(null);
				admForm.setDdNo(null);
				admForm.setInternationalApplnFeeCurrencyId(null);
				admForm.setEquivalentApplnFeeINR(null);
				duplicate = handler.checkPaymentDetails(admForm.getChallanNo(),admForm.getJournalNo(),admForm.getApplicationDate(),year);
			}
			else if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() && admForm.getSelectedFeePayment().equalsIgnoreCase("DD")){
				admForm.setChallanNo(null);
				admForm.setJournalNo(null);
				admForm.setApplicationDate(null);
				admForm.setApplicationAmount(null);
				admForm.setBankBranch(null);
				duplicate = handler.checkPaymentDetails("SelectedDDPayment",admForm.getDdNo(),admForm.getDdDate(),year);
			}
			boolean preRequisiteduplicate=false;
			if(admForm.getCoursePrerequisites()!= null && admForm.getCoursePrerequisites().size() > 0){
				List<CoursePrerequisiteTO> prerequisites=admForm.getCoursePrerequisites();				
				Iterator<CoursePrerequisiteTO> reqItr2=prerequisites.iterator();
				while (reqItr2.hasNext()) {
					CoursePrerequisiteTO reqTO = (CoursePrerequisiteTO) reqItr2.next();
					//AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
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
			}
			else{
				if(duplicate){
					if(errors==null){
						errors= new ActionMessages();
					}
					if (errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE, new ActionError(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE));
					}	
				}
			}

		}
		}

		if(errors!=null && !errors.isEmpty() )
		{
			saveErrors(request, errors);
			setProgramAndCourseMap(admForm,request);
			request.setAttribute("operation", "load");
			if(admForm.isOnlineApply())
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE);
			else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
		}

		// validation done
		try {
			handler.setWorkExpNeeded(admForm);
			handler.saveApplicationDetailsToSession(admForm,session);
			ExamGenHandler genHandler = new ExamGenHandler();
			HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
			admForm.setSecondLanguageList(secondLanguage);
		} 
		catch (Exception e) {
			log.error("error in submit application detail...",e);
				throw e;

		}
		log.info("exit submit application detail page...");

		if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.INIT_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_INIT_STUDENT_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INIT_STUDENT_PAGE);
	}

	/**
	 * VALIDATES PAYMENT RELATED INFORMATIONS
	 * @param errors
	 * @param admForm
	 */
	private void validatePaymentDetails(ActionMessages errors,
			AdmissionFormForm admForm) {/*
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
		//if(admForm.getSelectedFeePayment().equalsIgnoreCase("SBI")){
			//raghu put comment
		if(admForm.getSelectedFeePayment().equalsIgnoreCase("CHALLAN")){
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
	else if(admForm.getSelectedFeePayment().equalsIgnoreCase("DD")){
				if (admForm.getDdNo()==null || StringUtils.isEmpty(admForm.getDdNo())) {

					if (errors.get(CMSConstants.ADMISSIONFORM_DDNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DDNO_REQUIRED).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DDNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_DDNO_REQUIRED));
					}

			}
			if (admForm.getDdDrawnOn()==null || StringUtils.isEmpty(admForm.getDdDrawnOn())) {

				if (errors.get(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_DD_DRAWN_ON_REQUIRED));
				}

			}
			if (admForm.getDdDate()==null || StringUtils.isEmpty(admForm.getDdDate())) {

				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED));
				}

			}
			if (admForm.getDdAmount()==null || StringUtils.isEmpty(admForm.getDdAmount())) {

				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED));
				}

			}
			if(admForm.getInternationalApplnFeeCurrencyId()==null || admForm.getInternationalApplnFeeCurrencyId().isEmpty()){
				errors.add("knowledgepro.admission.online.currency.reqd", new ActionError("knowledgepro.admission.online.currency.reqd"));
			}
			if(admForm.getDdIssuingBank()== null || admForm.getDdIssuingBank().isEmpty()){
				if (errors.get(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED));
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
			if(admForm.getDdAmount()!=null && !StringUtils.isEmpty(admForm.getDdAmount().trim())){
					if(!CommonUtil.isValidDecimal(admForm.getDdAmount().trim())){
						if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID));
						}
					}
			}
		}
}
	log.info("exit validatePaymentDetails...");
			 */}
	/**
	 * validate programtype,course and program
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateProgramCourse(ActionMessages errors,
			AdmissionFormForm admForm,boolean isFirstprefLable) {
		log.info("enter validate program course...");
		if(admForm.getProgramTypeId() ==null || admForm.getProgramTypeId().length()==0)
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
		/*if(admForm.getProgramId() ==null || admForm.getProgramId().length()==0)
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
		}*/
		if(admForm.getCourseId()==null ||admForm.getCourseId().length()==0 )
		{
			if(errors==null){
				errors= new ActionMessages();
				if(isFirstprefLable){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED, error);
				}else{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
				}
			}else{
				if(isFirstprefLable){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED, error);
				}else{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
				}
			}
		}
		log.info("exit validate program course...");
		return errors;
	}
	/**
	 * validate programtype,course and program
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateEditProgramCourse(ActionMessages errors,
			AdmissionFormForm admForm) {
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
	 * saves education details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitEducationInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit education page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		//validation if needed
		ActionMessages errors=admForm.validate(mapping, request);
		if(errors==null)
			errors= new ActionMessages();
		try {
			validateEducationDetails(errors, admForm);
			if(admForm.isDisplayTCDetails())
				validateTCdetails(errors, admForm);
			if(admForm.isDisplayEntranceDetails())
				validateEntranceDetails(errors, admForm);

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_EDUCATION_PAGE);
				else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_EDUCATION_PAGE);
			}
			// save to session education Data
			HttpSession session = request.getSession(false);
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			if (session == null){
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_EDUCATION_PAGE);
				else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_EDUCATION_PAGE);
			}
			handler.saveEducationDetailsToSession(admForm, session,false);
		} 
		catch (Exception e) {
			log.error("error in submit education page...",e);
			throw e;


		}
		log.info("exit submit education page...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INIT_ATTACHMENT_PAGE);
	}
	/**
	 * ENTRANCE DETAILS VALIDATION
	 * @param errors
	 * @param admForm
	 */
	private void validateEntranceDetails(ActionMessages errors,
			AdmissionFormForm admForm) {/*
		if(admForm.getEntranceTotalMark()!=null && !StringUtils.isEmpty(admForm.getEntranceTotalMark().trim()) && !StringUtils.isNumeric(admForm.getEntranceTotalMark().trim()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER, error);
				}
		}

		if(admForm.getEntranceMarkObtained()!=null && !StringUtils.isEmpty(admForm.getEntranceMarkObtained().trim()) && !StringUtils.isNumeric(admForm.getEntranceMarkObtained().trim()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER, error);
				}
		}

		if(admForm.getEntranceMarkObtained()!=null && !StringUtils.isEmpty(admForm.getEntranceMarkObtained().trim()) && StringUtils.isNumeric(admForm.getEntranceMarkObtained().trim()) 
				&& admForm.getEntranceTotalMark()!=null && !StringUtils.isEmpty(admForm.getEntranceTotalMark().trim()) && StringUtils.isNumeric(admForm.getEntranceTotalMark().trim())
				&& Integer.parseInt(admForm.getEntranceMarkObtained())> Integer.parseInt(admForm.getEntranceTotalMark()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE, error);
				}
		}

		//check date of birth cross and present date cross
		if((admForm.getEntranceYearPass()!=null && !StringUtils.isEmpty(admForm.getEntranceYearPass()) && StringUtils.isNumeric(admForm.getEntranceYearPass())) 
				&& admForm.getEntranceMonthPass()!=null && !StringUtils.isEmpty(admForm.getEntranceMonthPass()) && StringUtils.isNumeric(admForm.getEntranceMonthPass())){
			if(CommonUtil.isValidDate(admForm.getDateOfBirth())){
				boolean futurethanBirth=isPassYearGreaterThanBirth(Integer.parseInt(admForm.getEntranceYearPass()),Integer.parseInt(admForm.getEntranceMonthPass()),admForm.getDateOfBirth());
				if(!futurethanBirth){
					if (errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE);
						errors.add(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE,error);
					}
				}
			}
			Calendar cal= Calendar.getInstance();
			Date today= cal.getTime();
			boolean futurethantoday=isPassYearGreaterThanToday(Integer.parseInt(admForm.getEntranceYearPass()),Integer.parseInt(admForm.getEntranceMonthPass()),today);
			if(futurethantoday){
				if (errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE);
					errors.add(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE,error);
				}
			}
		}
			 */}
	/**
	 * TC DETAILS VALIDATION
	 * @param errors
	 * @param admForm
	 */
	private void validateTCdetails(ActionMessages errors,
			AdmissionFormForm admForm) {
		if(admForm.getTcDate()!=null && !StringUtils.isEmpty(admForm.getTcDate().trim()))
		{
			if(CommonUtil.isValidDate(admForm.getTcDate().trim()) ){
				if(!validatefutureDate(admForm.getTcDate().trim())){
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

		if(admForm.getMarkcardDate()!=null && !StringUtils.isEmpty(admForm.getMarkcardDate().trim()))
		{
			if(CommonUtil.isValidDate(admForm.getMarkcardDate().trim()) ){
				if(!validatefutureDate(admForm.getMarkcardDate())){
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
	 * INIT DETAILMARK PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init detail mark page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {

			String indexString = request.getParameter(AdmissionFormAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(AdmissionFormAction.COUNTID, indexString);
				}else
					session.removeAttribute(AdmissionFormAction.COUNTID);
			}
			List<EdnQualificationTO> quals=admForm.getQualifications();
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

			Map<Integer,String> subjectMap = AdmissionFormHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);

			if(admForm.getDetailMark()==null){
				CandidateMarkTO markTo=new CandidateMarkTO();
				AdmissionFormHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
				admForm.setDetailMark(markTo);
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
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_DETAIL_MARK_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAIL_MARK_PAGE);
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
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			Map<Integer,String> subjectMap = AdmissionFormHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
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
									AdmissionFormHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
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
		return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_DETAIL_MARK_PAGE);
	}

	/**
	 * DETAIL MARK VIEW PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewDetailMarkPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter viewDetailMarkPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
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
								if(qualTO.getDetailmark()!=null)
									admForm.setDetailMark(qualTO.getDetailmark());
								else
									admForm.setDetailMark(new CandidateMarkTO());
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
		log.info("exit viewDetailMarkPage...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_VIEW_DETAIL_MARK_PAGE);
	}

	/**
	 * SUBMITS DETAIL MARK ENTRY
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitDetailMark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit detail mark page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(AdmissionFormAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			ActionMessages errors = validateMarks(detailmark,new ActionMessages());
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_DETAIL_MARK_PAGE);
				else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAIL_MARK_PAGE);
			}
			List<EdnQualificationTO> qualifications = admForm
					.getQualifications();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> QualItr=qualifications.iterator();
				while (QualItr.hasNext()) {
					EdnQualificationTO qualificationto = QualItr.next();
					if (qualificationto != null && qualificationto.getCountId()==detailMarkIndex)
						qualificationto.setDetailmark(detailmark);
				}

			}
			admForm.setDetailMark(null);
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
		log.info("exit submit detail mark page...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_EDUCATION_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_EDUCATION_PAGE);
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
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute("editcountID");
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			ActionMessages errors = validateMarks(detailmark,new ActionMessages());
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_DETAIL_MARK_PAGE);
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getId()==detailMarkIndex){
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
		if(admForm.getPucApplicationEdit()){
			return mapping.findForward("PUCApplicantEdit");
		}
		if(admForm.getPucApplicationDetailEdit()){
			return mapping.findForward("PUCApplicantDetailEdit");
		}
		if(!admForm.isAdmissionEdit())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
	}
	/**
	 * INIT SEMESTER MARK PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSemesterMarkPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter initSemesterMarkPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {

			String indexString = request.getParameter(AdmissionFormAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					session.setAttribute(AdmissionFormAction.COUNTID, indexString);
					index=Integer.parseInt(indexString);
				}else
					session.removeAttribute(AdmissionFormAction.COUNTID);
			}
			List<EdnQualificationTO> quals=admForm.getQualifications();
			admForm.setIsLanguageWiseMarks("false");
			if(quals!=null ){

				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if (qualTO.getCountId()==index) {
								if (qualTO.getDetailmark() != null) {
									admForm.setDetailMark(qualTO.getDetailmark());
								}	
								if(qualTO.isLanguage()) {
									  admForm.setIsLanguageWiseMarks("true");
								 }

							}

				}
			}

			if(admForm.getDetailMark()==null){
				admForm.setDetailMark(new CandidateMarkTO());
			}
		} catch (Exception e) {
			log.error("error in initSemesterMarkPage...",e);
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
		log.info("exit initSemesterMarkPage...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_DETAIL_SEMESTER_PAGE);
		else
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAIL_SEMESTER_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAIL_SEMESTER_PAGE);
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
			HttpServletResponse response) throws Exception {/*
		log.info("enter initSemesterMarkEditPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
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
		return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_SEMESTER_MARK_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_SEMESTER_MARK_PAGE);
	}

	/**
	 * VIEW SEMESTER ENTRIES
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewSemesterMarkPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter viewSemesterMarkPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
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
									admForm.setSemesterList(new ArrayList<ApplicantMarkDetailsTO>());
								}
							  admForm.setIsLanguageWiseMarks(String.valueOf(qualTO.isLanguage()));

							}
						}
					}
				}
				else
					session.removeAttribute("editcountID");
			}
		} catch (Exception e) {
			log.error("error in viewSemesterMarkPage...",e);
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
		log.info("exit viewSemesterMarkPage...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_VIEW_SEMESTER_MARK_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_VIEW_SEMESTER_MARK_PAGE);
	}

	/**
	 * SUBMITS SEMESTER MARK ENTRY
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitSemesterMark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submit semester mark page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(AdmissionFormAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			ActionMessages errors = validateMarks(detailmark,new ActionMessages());
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_DETAIL_SEMESTER_PAGE);
				else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAIL_SEMESTER_PAGE);
			}
			List<EdnQualificationTO> qualifications = admForm.getQualifications();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> QualItr=qualifications.iterator();
				while (QualItr.hasNext()) {
					EdnQualificationTO qualificationto = QualItr.next();
					if (qualificationto != null && qualificationto.getCountId()==detailMarkIndex){
					detailmark.setLastExam(qualificationto.isLastExam());
					//sets semester mark with language indicator
					if(admForm.getIsLanguageWiseMarks()!=null && admForm.getIsLanguageWiseMarks().equalsIgnoreCase("true"))
					detailmark.setSemesterMark(true);
					else
						detailmark.setSemesterMark(false);
					qualificationto.setDetailmark(detailmark);
				}
				}
			}
			admForm.setDetailMark(null);
		} catch (Exception e) {
			log.error("error in submit semester mark page...",e);
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
		log.info("exit submit semester mark page...");

		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_EDUCATION_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_EDUCATION_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_EDUCATION_PAGE);
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
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitSemesterEditMark...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
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
				return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_SEMESTER_MARK_PAGE);
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
		if(admForm.getPucApplicationEdit()){
			return mapping.findForward("PUCApplicantEdit");
		}
		if(admForm.getPucApplicationDetailEdit()){
			return mapping.findForward("PUCApplicantDetailEdit");
		}
		if(!admForm.isAdmissionEdit())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
	}



	/**
	 * validate semester mark in edit page 
	 * @param semesterMarks
	 * @return
	 */
	private ActionMessages validateEditSemesterMarks(
			List<ApplicantMarkDetailsTO> semesterMarks) {
		ActionMessages errors= new ActionMessages();
		/*
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

		}
		log.info("exit validateEditSemesterMarks...");
		return errors;

		 */
		return errors;
	}
	/**
	 * @param detailmark
	 * @return
	 */
	public static ActionMessages validateMarks(CandidateMarkTO detailmark,ActionMessages errors) {
		log.info("enter validateMarks...");
		//ActionMessages errors= new ActionMessages();
		if(detailmark!=null){/*

			mandatory subject mark check start 
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


			//raghu write newly
			if(detailmark.getSubject1TotalMarks()!=null && StringUtils.isEmpty(detailmark.getSubject1TotalMarks())){
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



			mandatory subject mark check end 

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
		 */}
		log.info("exit validateMarks...");
		return errors;
	}






	public static ActionMessages validateMarksFor12thClass(CandidateMarkTO detailmark,ActionMessages errors){
		log.info("enter validateMarks...");

		//ActionMessages errors= new ActionMessages();
		if(detailmark!=null){/*
		if(detailmark.getSubject1()!=null && !detailmark.getSubject1().equalsIgnoreCase("") && 
				detailmark.getSubject1ObtainedMarks()!=null && !detailmark.getSubject1ObtainedMarks().equalsIgnoreCase("") && 
				detailmark.getSubject1TotalMarks()!=null && !detailmark.getSubject1TotalMarks().equalsIgnoreCase(""))
				{
				}
		else if((detailmark.getSubject1()==null || detailmark.getSubject1().equalsIgnoreCase("")) && 
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
		if(detailmark.getSubject2()!=null && !detailmark.getSubject2().equalsIgnoreCase("") && 
				detailmark.getSubject2ObtainedMarks()!=null && !detailmark.getSubject2ObtainedMarks().equalsIgnoreCase("") && 
				detailmark.getSubject2TotalMarks()!=null && !detailmark.getSubject2TotalMarks().equalsIgnoreCase(""))
				{
				}
		else if((detailmark.getSubject2()==null || detailmark.getSubject2().equalsIgnoreCase("")) && 
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
		if(detailmark.getSubject3()!=null && !detailmark.getSubject3().equalsIgnoreCase("") && 
				detailmark.getSubject3ObtainedMarks()!=null && !detailmark.getSubject3ObtainedMarks().equalsIgnoreCase("") && 
				detailmark.getSubject3TotalMarks()!=null && !detailmark.getSubject3TotalMarks().equalsIgnoreCase(""))
				{
				}
		else if((detailmark.getSubject3()==null || detailmark.getSubject3().equalsIgnoreCase("")) && 
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
		if(detailmark.getSubject4()!=null && !detailmark.getSubject4().equalsIgnoreCase("") && 
				detailmark.getSubject4ObtainedMarks()!=null && !detailmark.getSubject4ObtainedMarks().equalsIgnoreCase("") && 
				detailmark.getSubject4TotalMarks()!=null && !detailmark.getSubject4TotalMarks().equalsIgnoreCase(""))
				{
				}
		else if((detailmark.getSubject4()==null || detailmark.getSubject4().equalsIgnoreCase("")) && 
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
		if(detailmark.getSubject5()!=null && !detailmark.getSubject5().equalsIgnoreCase("") && 
				detailmark.getSubject5ObtainedMarks()!=null && !detailmark.getSubject5ObtainedMarks().equalsIgnoreCase("") && 
				detailmark.getSubject5TotalMarks()!=null && !detailmark.getSubject5TotalMarks().equalsIgnoreCase(""))
				{
				}
		else if((detailmark.getSubject5()==null || detailmark.getSubject5().equalsIgnoreCase("")) && 
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
		if(detailmark.getSubject6()!=null && !detailmark.getSubject6().equalsIgnoreCase("") && 
				detailmark.getSubject6ObtainedMarks()!=null && !detailmark.getSubject6ObtainedMarks().equalsIgnoreCase("") && 
				detailmark.getSubject6TotalMarks()!=null && !detailmark.getSubject6TotalMarks().equalsIgnoreCase(""))
				{
				}
		else if((detailmark.getSubject6()==null || detailmark.getSubject6().equalsIgnoreCase("")) && 
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
		if(detailmark.getSubject7()!=null && !detailmark.getSubject7().equalsIgnoreCase("") && 
				detailmark.getSubject7ObtainedMarks()!=null && !detailmark.getSubject7ObtainedMarks().equalsIgnoreCase("") && 
				detailmark.getSubject7TotalMarks()!=null && !detailmark.getSubject7TotalMarks().equalsIgnoreCase(""))
				{
				}
		else if((detailmark.getSubject7()==null || detailmark.getSubject7().equalsIgnoreCase("")) && 
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


		mandatory subject mark check start 
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


		mandatory subject mark check end 

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

		//raghu write newly
		if(detailmark.getSubject1TotalMarks()!=null && StringUtils.isEmpty(detailmark.getSubject1TotalMarks())){
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
		 */}
		log.info("exit validateMarks...");
		return errors;
	}

	/**
	 * INITIALIZES ATTACHMENT PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttachMentPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init attachment page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			int appliedyear=0;
			HttpSession session = request.getSession(false);
			if (session == null) {
				return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
			}

			AdmAppln applicationdata=null;
			if (session != null) {

				if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
				{
					log.info("application no set session application data...");
					applicationdata=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);
					if(applicationdata!=null)
						appliedyear=applicationdata.getAppliedYear();
				}
			}
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			admForm.setUploadDocs(handler.getRequiredDocList(admForm
					.getCourseId(),appliedyear));
		} catch (Exception e) {
			log.error("error in init attachment page...",e);
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
		log.info("enter exit attachment page...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_ATTACHMENT_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ATTACHMENT_PAGE);
	}
	/**
	 * validate programtype,course and program
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateEducationDetails(ActionMessages errors,
			AdmissionFormForm admForm) {/*
		log.info("enter validate education...");
		List<EdnQualificationTO> qualifications=admForm.getQualifications();
		if(qualifications!=null){
			Iterator<EdnQualificationTO> qualificationTO= qualifications.iterator();
			while (qualificationTO.hasNext()) {
				EdnQualificationTO qualfTO = (EdnQualificationTO) qualificationTO
						.next();
				if((qualfTO.getUniversityId()==null ||(qualfTO.getUniversityId().length()==0 ) || qualfTO.getUniversityId().equalsIgnoreCase("Other")) && (qualfTO.getUniversityOthers()==null ||(qualfTO.getUniversityOthers().trim().length()==0 )))
				{
						if(errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED,error);
						}
				}
				if((qualfTO.getInstitutionId()==null ||(qualfTO.getInstitutionId().length()==0 )||(qualfTO.getInstitutionId().equalsIgnoreCase("Other")) ) && (qualfTO.getOtherInstitute()==null ||(qualfTO.getOtherInstitute().trim().length()==0 )))
				{
						//if (errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED).hasNext()) {
						//	ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED);
						//	errors.add(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED,error);
						//}
				}

				//raghu
				if(qualfTO.getOtherInstitute()==null ||(qualfTO.getOtherInstitute().trim().length()==0 )){
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED,error);

				}

				if(qualfTO.isExamRequired()qualfTO.isExamConfigured() && (qualfTO.getSelectedExamId()==null || StringUtils.isEmpty(qualfTO.getSelectedExamId())))
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
				if(qualfTO.getNoOfAttempts()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED,error);
						}
				}
				if(qualfTO.isConsolidated()){
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

				}else{
					if(qualfTO.getDetailmark()!=null && !qualfTO.getDetailmark().isPopulated())
					{
						if(!qualfTO.isSemesterWise()){
							if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,error);
							}
						}else if(qualfTO.isSemesterWise()){
							if (errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED,error);
							}
						}
					}
				}


				if(qualfTO.getYearPassing()!=0 && qualfTO.getMonthPassing()!=0){
					if(CommonUtil.isValidDate(admForm.getDateOfBirth())){
						boolean futurethanBirth=isPassYearGreaterThanBirth(qualfTO.getYearPassing(),qualfTO.getMonthPassing(),admForm.getDateOfBirth());
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
			 */
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
			String formattedString=CommonUtil.ConvertStringToDateFormat(dateOfBirth, AdmissionFormAction.FROM_DATEFORMAT,AdmissionFormAction.TO_DATEFORMAT);
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
			AdmissionFormForm admForm) {/*
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
				//if((qualfTO.getInstitutionId()==null ||qualfTO.getInstitutionId().length()==0 )||(qualfTO.getInstitutionId().equalsIgnoreCase("Other") && (qualfTO.getOtherInstitute()==null ||(qualfTO.getOtherInstitute().trim().length()==0 ))))
					if(qualfTO.getOtherInstitute()==null || qualfTO.getOtherInstitute().trim().length()==0 )

				{
						if (errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED,error);
						}
				}
				if(qualfTO.isExamRequired()qualfTO.isExamConfigured()&& (qualfTO.getSelectedExamId()==null || StringUtils.isEmpty(qualfTO.getSelectedExamId())))
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
				if(qualfTO.getNoOfAttempts()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED,error);
						}
				}
				if(qualfTO.isConsolidated()){
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
					 if(qualfTO.getTotalMarks()!=null && qualfTO.getTotalMarks().equalsIgnoreCase("0") || qualfTO.getTotalMarks().startsWith("0.0")){
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
					}
					else if((qualfTO.getDetailmark().getSubject1() == null || qualfTO.getDetailmark().getSubject1().isEmpty()) &&
							(qualfTO.getDetailmark().getSubject2() == null || qualfTO.getDetailmark().getSubject2().isEmpty()) &&
							(qualfTO.getDetailmark().getSubject3() == null || qualfTO.getDetailmark().getSubject3().isEmpty()) &&
							(qualfTO.getDetailmark().getSubject4() == null || qualfTO.getDetailmark().getSubject4().isEmpty()) &&
							(qualfTO.getDetailmark().getSubject5() == null || qualfTO.getDetailmark().getSubject5().isEmpty()) &&
							(qualfTO.getDetailmark().getSubject6() == null || qualfTO.getDetailmark().getSubject6().isEmpty()) &&
							(qualfTO.getDetailmark().getSubject7() == null || qualfTO.getDetailmark().getSubject7().isEmpty()) &&
							(qualfTO.getDetailmark().getSubject8() == null || qualfTO.getDetailmark().getSubject8().isEmpty()) &&  
							(qualfTO.getDetailmark().getSubject9() == null || qualfTO.getDetailmark().getSubject9().isEmpty()) &&
							(qualfTO.getDetailmark().getSubject10() == null || qualfTO.getDetailmark().getSubject10().isEmpty())) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,error);

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

			 */
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
	public ActionForward initParentPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init parent page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			OccupationTransactionHandler occhandler = OccupationTransactionHandler
					.getInstance();
			admForm.setOccupations(occhandler.getAllOccupation());
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			admForm.setCurrencies(handler.getCurrencies());
			admForm.setIncomes(handler.getIncomes());
		} catch (Exception e) {
			log.error("error in init parent page...",e);
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
		log.info("exit init parent page...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_PARENT_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_PARENT_PAGE);
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
		AdmissionFormForm admForm=(AdmissionFormForm)form;
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
		if(session!= null && session.getAttribute(AdmissionFormAction.PHOTOBYTES)!=null)
			session.removeAttribute(AdmissionFormAction.PHOTOBYTES);

		session.removeAttribute("baseActionForm");
		log.info("session data cleaned...");
	}

	/**
	 * initialize online application form 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOnlineApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session= request.getSession(false);

		AdmissionFormForm admForm=null;
		try {
			cleanupSessionData(session);
			cleanupFormFromSession(session);
			admForm=(AdmissionFormForm)form;
			admForm.setOnlineApply(true);
			admForm.setApplicationConfirm(false);
			admForm.setResidentTypes(OnlineApplicationHandler.getInstance().getResidentTypes());
			request.setAttribute("residentTypes", OnlineApplicationHandler.getInstance().getResidentTypes());
			admForm.setPrefcourses(CourseHandler.getInstance().getActiveCourses());
		} catch (Exception e) {
			log.error("error in init online application page...",e);
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
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
	}

	/**
	 * forwards first page of online course selection
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardOnlineFirstPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("enter  forwardOnlineFirstPage...");
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		cleanUpPageData(admForm);
		log.info("exit  forwardOnlineFirstPage...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
	}

	/**
	 * Link from outside sites
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOutsideAccess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session= request.getSession(false);

		AdmissionFormForm admForm=null;
		try {
			cleanupSessionData(session);
			cleanupFormFromSession(session);
			admForm=(AdmissionFormForm)form;
			admForm.setOnlineApply(true);
			setUserId(request,admForm);
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if(organisation!=null){
				// set photo to session
				if(organisation.getLogoContentType()!=null){
					if(session!=null){
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar());
					}
				}
			}
		} catch (Exception e) {
			log.error("error in initOutsideAccess...",e);
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

		log.info("exit init initOutsideAccess...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
	}


	/**
	 * Link from outside sites -single page Form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOutsideSinglePageAccess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session= request.getSession(false);

		AdmissionFormForm admForm=null;
		try {
			cleanupSessionData(session);
			admForm=(AdmissionFormForm)form;
			admForm.setCoursePrerequisites(new ArrayList<CoursePrerequisiteTO>());
			cleanUpPageData(admForm);
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

			/*admForm.setSubid_0(null);
			admForm.setSubid_1(null);
			admForm.setSubid_2(null);
			admForm.setSubid_3(null);
			admForm.setSubid_4(null);
			admForm.setSubid_5(null);
			admForm.setSubid_6(null);
			admForm.setSubid_7(null);
			admForm.setSubid_8(null);

			admForm.setMaxmark_0(null);
			admForm.setMaxmark_1(null);
			admForm.setMaxmark_2(null);
			admForm.setMaxmark_3(null);
			admForm.setMaxmark_4(null);
			admForm.setMaxmark_5(null);
			admForm.setMaxmark_6(null);
			admForm.setMaxmark_7(null);
			admForm.setMaxmark_8(null);


			admForm.setObtainedmark_0(null);
			admForm.setObtainedmark_1(null);
			admForm.setObtainedmark_2(null);
			admForm.setObtainedmark_3(null);
			admForm.setObtainedmark_4(null);
			admForm.setObtainedmark_5(null);
			admForm.setObtainedmark_6(null);
			admForm.setObtainedmark_7(null);
			admForm.setObtainedmark_8(null);

			admForm.setDegsubid_0(null);
			admForm.setDegsubid_1(null);
			admForm.setDegsubid_2(null);
			admForm.setDegsubid_3(null);
			admForm.setDegsubid_4(null);
			admForm.setDegsubid_5(null);
			admForm.setDegsubid_6(null);
			admForm.setDegsubid_7(null);
			admForm.setDegsubid_8(null);
			admForm.setDegsubid_9(null);
			admForm.setDegsubid_10(null);
			admForm.setDegsubid_11(null);
			admForm.setDegsubid_12(null);
			admForm.setDegsubid_13(null);
			admForm.setDegsubid_14(null);

			admForm.setDegobtainedmark_0(null);
			admForm.setDegobtainedmark_1(null);
			admForm.setDegobtainedmark_2(null);
			admForm.setDegobtainedmark_3(null);
			admForm.setDegobtainedmark_4(null);
			admForm.setDegobtainedmark_5(null);
			admForm.setDegobtainedmark_6(null);
			admForm.setDegobtainedmark_7(null);
			admForm.setDegobtainedmark_8(null);
			admForm.setDegobtainedmark_9(null);
			admForm.setDegobtainedmark_10(null);
			admForm.setDegobtainedmark_11(null);
			admForm.setDegobtainedmark_12(null);
			admForm.setDegobtainedmark_13(null);
			admForm.setDegobtainedmark_14(null);

			admForm.setDegmaxmark_0(null);
			admForm.setDegmaxmark_1(null);
			admForm.setDegmaxmark_2(null);
			admForm.setDegmaxmark_3(null);
			admForm.setDegmaxmark_4(null);
			admForm.setDegmaxmark_5(null);
			admForm.setDegmaxmark_6(null);
			admForm.setDegmaxmark_7(null);
			admForm.setDegmaxmark_8(null);
			admForm.setDegmaxmark_9(null);
			admForm.setDegmaxmark_10(null);
			admForm.setDegmaxmark_11(null);
			admForm.setDegmaxmark_12(null);
			admForm.setDegmaxmark_13(null);
			admForm.setDegmaxmark_14(null);

			admForm.setDegmaxcgpa_0(null);
			admForm.setDegmaxcgpa_1(null);
			admForm.setDegmaxcgpa_2(null);
			admForm.setDegmaxcgpa_3(null);
			admForm.setDegmaxcgpa_4(null);
			admForm.setDegmaxcgpa_5(null);
			admForm.setDegmaxcgpa_6(null);
			admForm.setDegmaxcgpa_7(null);
			admForm.setDegmaxcgpa_14(null);
			 */
			admForm.setDegtotalobtainedmark(null);
			admForm.setDegtotalmaxmark(null);
			admForm.setDegtotalobtainedmarkother(null);
			admForm.setDegtotalmaxmarkother(null);
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

			if(request.getParameter("presidance")!=null && request.getParameter("presidance").equals("true")){
				admForm.setIsPresidance(true);

			}else{
				admForm.setIsPresidance(false);
			}




			// checking admission close or not
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = new Date();
			String dateString= sdf.format(date1);
			date1 = sdf.parse(dateString);

			Date date2 = (Date) sdf.parse("2015-11-30");

			System.out.println(sdf.format(date1));
			System.out.println(sdf.format(date2));

			if(date1.compareTo(date2)>0)
			{
				// ActionMessages errors=admForm.validate(mapping, request);
				// errors.add("knowledgepro.adminssions.closed",new ActionError("knowledgepro.adminssions.closed"));

				System.out.println("Date1 is after Date2");
				log.info("exit admissions closed in time init initOutsideSinglePageAccess...");
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE_DATE);
			}



			//for applying preferences
			List<CourseTO> list=new ArrayList<CourseTO>();
			admForm.setPrefcourses(list);

			setUserId(request,admForm);
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if(organisation!=null){
				// set photo to session
				if(organisation.getLogoContentType()!=null){
					if(session!=null){
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar());
					}
				}
			}
			//resident category list
			admForm.setResidentTypes(AdmissionFormHandler.getInstance().getResidentTypes());
			admForm.setNativeCountry(CMSConstants.INDIAN_RESIDENT_ID);
			//added by mahi start
			admForm.setServerDownMessage(null);
			String maintenanceMessage =  MaintenanceAlertHandler.getInstance().getMaintenanceDetailsByDate();
			if(maintenanceMessage!=null){
				admForm.setServerDownMessage(maintenanceMessage);
				session.setAttribute("serverDownMessage", maintenanceMessage);
			}
			//end
		} catch (Exception e) {
			log.error("error in initOutsideSinglePageAccess...",e);
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

		log.info("exit init initOutsideSinglePageAccess...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitOnlineApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		admForm.setOnlineApply(true);
		HttpSession session=null;
		String courseName="";
		String progName="";
		String progTypeName="";
		ActionMessages errors=admForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
				String decCourseID=(String)request.getParameter("decText");
				CourseHandler crsHandler= CourseHandler.getInstance();
				boolean isValidDOB=validatefutureDate(admForm.getApplicantDob());


		        if(!isValidDOB){
		        	errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
				}
				validateProgramCourse(errors,admForm,true);
				//email comparision
				if(admForm.getEmail()!=null && !StringUtils.isEmpty(admForm.getEmail())){
					if(admForm.getConfirmEmail()!=null && !StringUtils.isEmpty(admForm.getConfirmEmail())){
							if(!admForm.getEmail().equals(admForm.getConfirmEmail())){
								if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
									errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
								}
							}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
						}
					}
				}else if(admForm.getConfirmEmail()!=null && !StringUtils.isEmpty(admForm.getConfirmEmail())){
					if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
					}
				}

				if(admForm.getMobileNo1()==null || admForm.getMobileNo1().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Mobile Country Code"));
				}

				if((admForm.getResidentCategoryForOnlineAppln()!=null && !admForm.getResidentCategoryForOnlineAppln().isEmpty()) 
							&& CMSConstants.INDIAN_RESIDENT_LIST.contains(Integer.parseInt(admForm.getResidentCategoryForOnlineAppln()))){
						if(admForm.getMobileNo2()!=null && !admForm.getMobileNo2().isEmpty() && admForm.getMobileNo2().length()>10){
							errors.add(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED));
						}
						admForm.setIndianCandidate(true);

				}

				if(errors!=null && !errors.isEmpty() )
				{
					if(admForm.getProgramTypeId()!=null && !admForm.getProgramTypeId().isEmpty()){
						Map<Integer, String> programMap=CommonAjaxHandler.getInstance().getApplnProgramsByProgramType(Integer.parseInt(admForm.getProgramTypeId()));
						admForm.setProgramMap(programMap);
					}
					if(admForm.getProgramId()!=null && !admForm.getProgramId().isEmpty()){
						Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByProgramForOnline(Integer.parseInt(admForm.getProgramId()));
						admForm.setCourseMap(courseMap);
					}
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
				}	

				String programId = AdmissionFormHandler.getInstance().getProgramId(Integer.parseInt(admForm.getCourseId()));
				if(programId!=null){
				admForm.setProgramId(programId);
				}
				int appliedYear = AdmissionFormHandler.getInstance().getApplicationYear(Integer.parseInt(admForm.getProgramId()));
				admForm.setApplicationYear(Integer.toString(appliedYear));

				if(decCourseID!=null && !StringUtils.isEmpty(decCourseID.trim())){

				int courseID=-1;
				int progID=-1;
				int progtypeID=-1;

				if(decCourseID!=null && !StringUtils.isEmpty(decCourseID.trim()) && StringUtils.isNumeric(decCourseID.trim()))
				{
					courseID=Integer.parseInt(decCourseID);
					admForm.setOutsideCourseSelected(true);
					List<CourseTO> courselist=crsHandler.getCourse(courseID);
					if(courselist!=null && !courselist.isEmpty()){
						CourseTO to= courselist.get(0);
						//PROGRAM LEVEL CONFIG SETTINGS
						if(to!=null){
							courseName=to.getName();
							if(to.getProgramTo()!=null){
							progID=to.getProgramTo().getId();
							progName=to.getProgramTo().getName();
								admForm.setDisplayMotherTongue(to.getProgramTo().getIsMotherTongue());
								admForm.setDisplayLanguageKnown(to.getProgramTo().getIsDisplayLanguageKnown());
								admForm.setDisplayHeightWeight(to.getProgramTo().getIsHeightWeight());
								admForm.setDisplayTrainingDetails(to.getProgramTo().getIsDisplayTrainingCourse());
								admForm.setDisplayAdditionalInfo(to.getProgramTo().getIsAdditionalInfo());
								admForm.setDisplayExtracurricular(to.getProgramTo().getIsExtraDetails());
								admForm.setDisplaySecondLanguage(to.getProgramTo().getIsSecondLanguage());
								admForm.setDisplayFamilyBackground(to.getProgramTo().getIsFamilyBackground());
								admForm.setDisplayLateralDetails(to.getProgramTo().getIsLateralDetails());
								admForm.setDisplayTransferCourse(to.getProgramTo().getIsTransferCourse());
								admForm.setDisplayEntranceDetails(to.getProgramTo().getIsEntranceDetails());
								admForm.setDisplayTCDetails(to.getProgramTo().getIsTCDetails());
								admForm.setDisplayExamCenterDetails(to.getProgramTo().getIsExamCenterRequired());
							}
							if(to.getProgramTo()!=null && to.getProgramTo().getProgramTypeTo()!=null ){
							progtypeID=to.getProgramTo().getProgramTypeTo().getProgramTypeId();
							progTypeName=to.getProgramTo().getProgramTypeTo().getProgramTypeName();
							}
						}
					}
				}
				if(courseID!=-1){
					session= request.getSession(true);
					admForm.setUserId(CMSConstants.ONLINE_USERID);
					admForm.setCourseId(String.valueOf(courseID));
					admForm.setCourseName(courseName);
				}
				else{
					admForm.setCourseId(null);
					admForm.setCourseName("");
				}
				if(progID!=-1){
					admForm.setProgramId(String.valueOf(progID));
					admForm.setProgramName(progName);
				}
				else{
					admForm.setProgramId(null);
					admForm.setProgramName("");
				}
				if(progtypeID!=-1){
					admForm.setProgramTypeId(String.valueOf(progtypeID));
					admForm.setProgTypeName(progTypeName);
				}
				else{
					admForm.setProgramTypeId(null);
					admForm.setProgTypeName("");
				}
				}
				session=request.getSession(false);
				if(session==null){
					return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
				}
					 boolean isadded=false;
					 ActionMessages message = new ActionMessages();
					CourseTO qualTO = new CourseTO();
				List<CourseTO> prefcourselist= admForm.getPrefcourses(); 
				Iterator<CourseTO> itr=prefcourselist.iterator();
				while(itr.hasNext()){
					CourseTO courseTO=(CourseTO) itr .next();
				if(courseTO.getId()==Integer.parseInt(admForm.getCourseId()) && Integer.parseInt(courseTO.getPrefNo())==1){
					isadded =true;

				}
				}
				if(isadded==false){
				    qualTO.setId(Integer.parseInt(admForm.getCourseId()));
				    qualTO.setPrefNo("1");
					prefcourselist.add(qualTO);
					admForm.setPrefcourses(prefcourselist);
				}
				Iterator<CourseTO> itr1=prefcourselist.iterator();
				while(itr1.hasNext()){
					CourseTO courseTO=(CourseTO) itr1.next();
					if(courseTO.getId()==0 || String.valueOf(courseTO.getId()).equalsIgnoreCase("") || String.valueOf(courseTO.getId()).isEmpty() ){
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_EMPTY_INVALID);
						message.add(CMSConstants.ADMISSIONFORM_COURSE_EMPTY_INVALID,error);
						saveErrors(request, message);
						return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
					}

				}


				//dup checking for pref
						final Set<Integer> setToReturn = new HashSet<Integer>();
						final Set<Integer> set1 = new HashSet<Integer>();

						for (CourseTO courseTO1 : prefcourselist) {
						if (!set1.add(courseTO1.getId())) {
						setToReturn.add(courseTO1.getId());
						}
						}
						if(setToReturn.size()!=0){
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSEPREF_DUP_INVALID);
							message.add(CMSConstants.ADMISSIONFORM_COURSEPREF_DUP_INVALID,error);
							saveErrors(request, message);
							return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
						}


				ActionMessages errors=admForm.validate(mapping, request);
				validateProgramCourse(errors,admForm,true);

				if(errors!=null && !errors.isEmpty() )
				{
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
				}
				setUserId(request,admForm);
				List<CurrencyTO> currencyList = AdmissionFormHandler.getInstance().getCurrencies();
				admForm.setCurrencyList(currencyList);

				if((admForm.getCourseName()==null || StringUtils.isEmpty(admForm.getCourseName())) && (admForm.getProgramName()==null || StringUtils.isEmpty(admForm.getProgramName())) && (admForm.getProgramTypeName()==null || StringUtils.isEmpty(admForm.getProgramTypeName()))){
				List<CourseTO> courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
				if(courselist!=null && ! courselist.isEmpty()){
					CourseTO to=courselist.get(0);
					// PROGRAM LEVEL CONFIG SETTINGS
					courseName=to.getName();
					if (to.getAmount() != null) {
						//raghu
					//	admForm.setApplicationAmount(to.getAmount().toPlainString());
					}
					if(to.getProgramTo()!=null){
						progName=to.getProgramTo().getName();
						if(to.getProgramTo().getIsMotherTongue()==true)
							admForm.setDisplayMotherTongue(true);
						if(to.getProgramTo().getIsDisplayLanguageKnown()==true)
							admForm.setDisplayLanguageKnown(true);
						if(to.getProgramTo().getIsHeightWeight()==true)
							admForm.setDisplayHeightWeight(true);
						if(to.getProgramTo().getIsDisplayTrainingCourse()==true)
							admForm.setDisplayTrainingDetails(true);
						if(to.getProgramTo().getIsAdditionalInfo()==true)
							admForm.setDisplayAdditionalInfo(true);
						if(to.getProgramTo().getIsExtraDetails()==true)
							admForm.setDisplayExtracurricular(true);
						if(to.getProgramTo().getIsSecondLanguage()==true)
							admForm.setDisplaySecondLanguage(true);
						if(to.getProgramTo().getIsFamilyBackground()==true)
							admForm.setDisplayFamilyBackground(true);
						if(to.getProgramTo().getIsLateralDetails()==true)
							admForm.setDisplayLateralDetails(true);
						if(to.getProgramTo().getIsTransferCourse()==true)
							admForm.setDisplayTransferCourse(true);
						if(to.getProgramTo().getIsEntranceDetails()==true)
							admForm.setDisplayEntranceDetails(true);
						if(to.getProgramTo().getIsTCDetails()==true)
							admForm.setDisplayTCDetails(true);
						if(to.getProgramTo().getIsExamCenterRequired()==true)
							admForm.setDisplayExamCenterDetails(true);

					}
					if(to.getProgramTo()!=null && to.getProgramTo().getProgramTypeTo()!=null)
						progTypeName=to.getProgramTo().getProgramTypeTo().getProgramTypeName();
				}
				// CHECK EXTRA BLOCK DISPLAY
				checkExtradetailsDisplay(admForm);
				// CHECK LATERAL AND TRANSFER LINK DISPLAY
				checkLateralTransferDisplay(admForm);
				admForm.setCourseName(courseName);
				admForm.setProgramName(progName);
				admForm.setProgTypeName(progTypeName);
				}


					List<CourseTO> courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
					if(courselist!=null && ! courselist.isEmpty()){
						CourseTO to=courselist.get(0);
						// Amount setting make sure
						if (to.getAmount() != null) {
							//raghu
							//admForm.setApplicationAmount(to.getAmount().toPlainString());
						}
						if(to.getIntApplicationFees()==null || to.getCurrencyId()==null){
							errors.add("knowledgepro.admission.online.exchangeRateApi.error",new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
						}
						//set international application fee for international students
						if(to.getIntApplicationFees()!=null){

							//raghu set here common course fee
							//admForm.setDdAmount(to.getIntApplicationFees().toPlainString());
							if(to.getAmount()!=null){
								admForm.setDdAmount(to.getAmount().toPlainString());
							}else{
								admForm.setDdAmount(to.getIntApplicationFees().toPlainString());
							}


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
					if(errors!=null && !errors.isEmpty() )
					{
						if(admForm.getProgramTypeId()!=null && !admForm.getProgramTypeId().isEmpty()){
							Map<Integer, String> programMap=CommonAjaxHandler.getInstance().getApplnProgramsByProgramType(Integer.parseInt(admForm.getProgramTypeId()));
							admForm.setProgramMap(programMap);
						}
						if(admForm.getProgramId()!=null && !admForm.getProgramId().isEmpty()){
							Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByProgramForOnline(Integer.parseInt(admForm.getProgramId()));
							admForm.setCourseMap(courseMap);
						}
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
					}		

					GuidelinesEntryHandler guidelineHandler=GuidelinesEntryHandler.getInstance();

					Calendar cal= Calendar.getInstance();
					cal.setTime(new Date());
					int year =cal.get(cal.YEAR);

					int year = Integer.parseInt(admForm.getApplicationYear());
					admForm.setGuidelineExist(guidelineHandler.isGuidelinesExist(Integer.parseInt(admForm.getCourseId()),year));

					boolean applnFeePaid=AdmissionFormHandler.getInstance().checkApplnFeePaidThroughOnline(admForm);
					if(applnFeePaid){

						if(admForm.getProgramTypeId()!=null && !admForm.getProgramTypeId().isEmpty()){
							Map<Integer, String> programMap=CommonAjaxHandler.getInstance().getApplnProgramsByProgramType(Integer.parseInt(admForm.getProgramTypeId()));
							admForm.setProgramMap(programMap);
						}
						if(admForm.getProgramId()!=null && !admForm.getProgramId().isEmpty()){
							Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByProgramForOnline(Integer.parseInt(admForm.getProgramId()));
							admForm.setCourseMap(courseMap);
						}
						log.info("exit init application detail page...");
						return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
					}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
			}
		} catch (Exception e) {
			log.error("error in init online application page...",e);
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
		log.info("exit init application detail page...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INITGUIDELINE_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INITGUIDELINE_PAGE);
	}

	/**
	 * gets exchange rate and calculated amount from a third party 
	 * @param errors 
	 * @param plainString
	 * @return
	 * @throws Exception
	 */
	private String getCalulatedInternationalApplnFee(String internationalApplnFee,String currencyFrom, ActionMessages errors) throws Exception {
		String calculatedINR="";
		try {
			/*if(internationalApplnFee!=null && !internationalApplnFee.trim().isEmpty()){
					Float amount=Float.valueOf(internationalApplnFee);
					URL convert = new URL("http://www.exchangerate-api.com/"+currencyFrom+"/inr/"+amount+"?k="+CMSConstants.EXCHANGE_RATE_API_KEY);
					BufferedReader in = new BufferedReader(new InputStreamReader(convert.openStream()));
					calculatedINR = in.readLine();
		// below if condition code has to be commented  once the api enables are query limit or testing is completed			
					if(calculatedINR.equalsIgnoreCase("-1") || calculatedINR.equalsIgnoreCase("-2") ||
								calculatedINR.equalsIgnoreCase("-3")|| calculatedINR.equalsIgnoreCase("-4")){
						 calculatedINR=String.valueOf(amount*55.5200) ;
					 }
					in.close();
				}*/
			if(internationalApplnFee!=null && !internationalApplnFee.trim().isEmpty()){
				Float amount=Float.valueOf(internationalApplnFee);
				// URL changed by chandra 09/12/2013
				//					URL convert = new URL("http://rate-exchange.appspot.com/currency?from="+currencyFrom+"&to=INR&q="+amount);
				// new URL
				URL convert = new URL("http://www.exchangerate-api.com/"+currencyFrom+"/INR/"+amount+"?k=RZivR-qNixz-qb7CQ");
				BufferedReader in = new BufferedReader(new InputStreamReader(convert.openStream()));
				String calculatedValue = in.readLine().trim();
				if(calculatedValue != null){
					/*String[] calDatails = calculatedValue.split(",");
						String indrupes = calDatails[3];
						for (char c: indrupes.toCharArray()){
					        if(Character.isDigit(c) || c == '.'){
					        	calculatedINR = calculatedINR + c;
					        }
					    }
						if(calculatedINR!=null && !calculatedINR.isEmpty() && calculatedINR.length() >=7){
							calculatedINR = calculatedINR.substring(0, calculatedINR.indexOf('.')+3);
						}*/
					amount = amount * 35; // approximate INR is calculated to check if the conversion rate has correctly  executed minimum conversion rate could be 35.

					Float calRup =  Float.valueOf(calculatedValue);
					//code for getting indian currency  rounded value
					//int calIndRup = (int) Math.round(calRup);
					//calculatedINR=String.valueOf(calIndRup);
					calRup = (float) (Math.round(calRup * 100.0) / 100.0);
					calculatedINR = calRup.toString();
					if(calRup<amount){
						errors.add("knowledgepro.admission.online.exchangeRateApi.error",new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
					}
				}
				in.close();
			}
		}
		catch (MalformedURLException mue) {
			log.error("error in getting exchange rate from third party online application page...",mue);
			errors.add("knowledgepro.admission.online.exchangeRateApi.error",new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
			throw new ApplicationException();
		}
		catch (IOException ioe) {
			log.error("error in getting exchange rate from third party online application page...",ioe);
			errors.add("knowledgepro.admission.online.exchangeRateApi.error",new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
			throw new ApplicationException();
		}catch (Exception e) {
			log.error("error in getting exchange rate from third party online application page...",e);
			errors.add("knowledgepro.admission.online.exchangeRateApi.error",new ActionError("knowledgepro.admission.online.exchangeRateApi.error"));
		} 
		return calculatedINR;
	}
	/**
	 * INITIALIZES GUIDELINES PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGuidelinesPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initGuidelinesPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		// fetch guideline template for course and display

		//if template empty,go to terms condition
		String guidelines="";
		//get template for term condition
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list = new ArrayList<GroupTemplate>();
		/*if(admForm.getIndianCandidate()){

			list= temphandle.getTemplateForNRI(CMSConstants.GUIDELINES_TEMPLATE);
		}else{
			list= temphandle.getTemplateForNRI(CMSConstants.GUIDELINES_NRI_TEMPLATE);
		}*/
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();

		if(admForm.getIndianCandidate()){
			list = ITemplatePassword.getGroupTemplateForPT(Integer.parseInt(admForm.getCourseId()), CMSConstants.GUIDELINES_TEMPLATE, Integer.parseInt(admForm.getProgramId()), Integer.parseInt(admForm.getProgramTypeId()));
		}else{
			list = ITemplatePassword.getGroupTemplateForPT(Integer.parseInt(admForm.getCourseId()), CMSConstants.GUIDELINES_NRI_TEMPLATE, Integer.parseInt(admForm.getProgramId()), Integer.parseInt(admForm.getProgramTypeId()));
		}

		if(list != null && !list.isEmpty()) {

			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null)
				guidelines = list.get(0).getTemplateDescription();

			String logoPath = "";
			byte[] logo = null;
			HttpSession session = request.getSession(false);

			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if (organisation != null) {
				logo = organisation.getLogo();
			}
			if (session != null) {
				session.setAttribute("LogoBytes", logo);
			}
			logoPath = request.getContextPath();
			logoPath = "<img src="
					+ logoPath
					+ "/LogoServlet alt='Logo not available' width='210' height='100' >";

			//REPLACE CHALLAN LINK
			String link=CMSConstants.PRINT_CHALLANLINK;

			guidelines=guidelines.replace(CMSConstants.TEMPLATE_PRINT_CHALLAN,link);
			guidelines=guidelines.replace(CMSConstants.TEMPLATE_LOGO,logoPath);

		}
		//fetch checklists
		List<GuideLinesCheckListTO> guidelist = new ArrayList<GuideLinesCheckListTO>();
		/*if(admForm.getIndianCandidate()){
			GuideLinesCheckListTO guideLinesCheckListTO = new GuideLinesCheckListTO();
			guideLinesCheckListTO.setDescription("Yes, I have understood the application fee terms and procedure and I have remitted the application fee and have two copies of the SBI Application Fee Challan with the Reference Number, Journal Number, Branch Code and Date of Transaction.");
			guidelist.add(guideLinesCheckListTO);
			/*GuideLinesCheckListTO guideLinesCheckListTO1 = new GuideLinesCheckListTO();
			guideLinesCheckListTO1.setDescription("Yes, I have understood the application fee terms and procedure and have the credit/debit cards or the required details for Net banking for online payment gateway option.");
			guidelist.add(guideLinesCheckListTO1);
			admForm.setGuidelines(guidelines
			admForm.setGuidelineChecklists(guidelist);*/
		///*}else{
		GuideLinesCheckListTO guideLinesCheckListTO = new GuideLinesCheckListTO();
		guideLinesCheckListTO.setDescription("Yes, I have understood the application fee terms and procedure and I have remitted the application fee  through  Challan");
		guidelist.add(guideLinesCheckListTO);

		/*GuideLinesCheckListTO guideLinesCheckListTO1 = new GuideLinesCheckListTO();
			guideLinesCheckListTO1.setDescription("Yes, I have understood the application fee terms and procedure and have the credit/debit cards or the required details for Net banking for online payment gateway option. ");
			guidelist.add(guideLinesCheckListTO1);*/
		admForm.setGuidelines(guidelines);
		admForm.setGuidelineChecklists(guidelist);
		//}
		if((guidelines!=null && !StringUtils.isEmpty(guidelines)) || (guidelist!=null && !guidelist.isEmpty()))
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINELINKS_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_INIT_TERMCONDITION_PAGE);

	}


	/**
	 * SUBMIT GUIDELINES PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitGuidelinesPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitGuidelinesPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		//check for mandatory check box check
		List<GuideLinesCheckListTO> guidelist=admForm.getGuidelineChecklists();
		ActionErrors errors=admForm.validate(mapping, request);
		if(guidelist!=null){
			Iterator<GuideLinesCheckListTO> guidItr=guidelist.iterator();
			boolean check = false;
			while (guidItr.hasNext()) {
				GuideLinesCheckListTO guideCheckTO = (GuideLinesCheckListTO) guidItr.next();
				if(guideCheckTO.isChecked()){
					check = true;
				}
			}
			if(!check){
				if(errors==null)errors=new ActionErrors();
				if (errors.get(CMSConstants.ADMISSIONFORM_GUIDELINES_NOTCHECKED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUIDELINES_NOTCHECKED).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_GUIDELINES_NOTCHECKED);
					errors.add(CMSConstants.ADMISSIONFORM_GUIDELINES_NOTCHECKED, error);
				}
			}
		}
		if(errors!=null && !errors.isEmpty())
		{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINELINKS_PAGE);
		}else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_INIT_TERMCONDITION_PAGE);

	}

	/**
	 * INITIALIZES PREREQUISITE APPLY
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPrerequisiteApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter initPrerequisiteApply...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		setDataToInitForm(admForm);
		if(admForm.getPaymentSuccess()){
			admForm.setSelectedFeePayment("OnlinePayment");
		}
		List<CoursePrerequisiteTO> prerequisites=handler.getCoursePrerequisites(Integer.parseInt(admForm.getCourseId()));
		if (prerequisites!=null && !prerequisites.isEmpty()) {
			admForm.setCoursePrerequisites(prerequisites);
			admForm.setPreRequisiteExists(true);
			return mapping
					.findForward(CMSConstants.ADMISSIONFORM_PREREQUISITE_PAGE);
		}else{
			admForm.setPreRequisiteExists(false);
			if(admForm.getIsPresidance()){
				return mapping.findForward(CMSConstants.ADMISSIONFORM_PRESIDANCE_ONLINE_APPLICATIONDETAIL_PAGE);
			}


			//raghu added for transaction failed message
			if(admForm.getError1()!=null && !admForm.getError1().equalsIgnoreCase("")){

				ActionErrors errors=new ActionErrors();
				ActionMessage error= new ActionMessage(CMSConstants.TRANSACTION_FAILED);
				errors.add(CMSConstants.TRANSACTION_FAILED, error);
				saveErrors(request, errors);

			}


			if(admForm.isOnlineApply())
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE);
			else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
		}
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
	}
	private void setDataToInitForm(AdmissionFormForm admForm)throws Exception {
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
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOfflinePrerequisiteApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter initOfflinePrerequisiteApply...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;

		try{

			 ActionErrors errors = admForm.validate(mapping, request);
			validateProgramCourse(errors, admForm,false);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				return mapping.findForward(CMSConstants.ADMISSIONFORM_COURSE_SELCTION_PAGE);
			}
			String courseName=null;
			String progName=null;
			String progTypeName=null;
			List<CourseTO> courselist=null;
			CourseHandler crsHandler=CourseHandler.getInstance();
			if(admForm.getCourseId()!=null && !StringUtils.isEmpty(admForm.getCourseId()) && StringUtils.isNumeric(admForm.getCourseId())){
				courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
			}
			if(courselist!=null && !courselist.isEmpty()){
				CourseTO to= courselist.get(0);
				if(to!=null){
					courseName=to.getName();
					if(to.getProgramTo()!=null){
						progName=to.getProgramTo().getName();

					}
					if(to.getProgramTo()!=null && to.getProgramTo().getProgramTypeTo()!=null ){
						progTypeName=to.getProgramTo().getProgramTypeTo().getProgramTypeName();
					}
				}
			}
			admForm.setCourseName(courseName);
			admForm.setProgramName(progName);
			admForm.setProgTypeName(progTypeName);
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		setDataToInitForm(admForm);
		if(CMSConstants.LINK_FOR_CJC){
			return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE_CJC);
		}
		// GET ALL PREREQUISTES FOR COURSE
		List<CoursePrerequisiteTO> prerequisites=handler.getCoursePrerequisites(Integer.parseInt(admForm.getCourseId()));
		if (prerequisites!=null && !prerequisites.isEmpty()) {
			admForm.setCoursePrerequisites(prerequisites);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_PREREQUISITE_PAGE);
		}else{

			return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
		}
		}catch(Exception e){
			log.error("error initOfflinePrerequisiteApply...",e);
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
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitPreRequisiteApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitPreRequisiteApply...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
		List<CoursePrerequisiteTO> prerequisites=admForm.getCoursePrerequisites();
		//Validate prerequisite and back if not
		ActionMessages messages= new ActionMessages();
		// VALIDATE PREREQUISTES REQUIREDS
		messages= validatePrerequisiteRequireds(prerequisites,messages);
		if(messages!=null && messages.isEmpty() )
			// VALIDATE PREREQUISTES ELIGIBILITY
		messages= validatePrerequisite(admForm,messages);

		if(messages!=null && !messages.isEmpty() )
		{
			setDataToInitForm(admForm);
			saveErrors(request, messages);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_PREREQUISITE_PAGE);
		}
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		HttpSession session= request.getSession(false);
		if(session==null)
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
		// SAVE THE PREREQUISITE DETAILS
		handler.savePrerequisitesToSession(session,admForm.getEligPrerequisites(),admForm.getUserId());
		}catch(ApplicationException e){
			log.error("error in submitPreRequisiteApply...",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in submitPreRequisiteApply...",e);
				throw e;

		}
		if(admForm.getIsPresidance()){
			return mapping.findForward(CMSConstants.ADMISSIONFORM_PRESIDANCE_ONLINE_APPLICATIONDETAIL_PAGE);
		}else{
			// else forward to applicationdetail page 
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE);
		}
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitOfflinePreRequisiteApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitOfflinePreRequisiteApply...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
		List<CoursePrerequisiteTO> prerequisites=admForm.getCoursePrerequisites();
		//Validate prerequisite and back if not
		ActionMessages messages= new ActionMessages();
		// VALIDATE PREREQUISTES REQUIREDS
		messages= validatePrerequisiteRequireds(prerequisites,messages);
		if(messages!=null && messages.isEmpty() )
			// VALIDATE PREREQUISTES ELIGIBILITY
		messages= validatePrerequisite(admForm,messages);

		if(messages!=null && !messages.isEmpty() )
		{
			saveErrors(request, messages);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_PREREQUISITE_PAGE);
		}
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		HttpSession session= request.getSession(false);
		if(session==null)
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
		// SAVE THE PREREQUISITE DETAILS
		handler.savePrerequisitesToSession(session,admForm.getEligPrerequisites(),admForm.getUserId());
		}catch(ApplicationException e){
			log.error("error in submitOfflinePreRequisiteApply...",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in submitOfflinePreRequisiteApply...",e);
				throw e;

		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE);


			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
	}
	/**
	 * checks pre requisite requireds
	 * @param prerequisites
	 * @param messages
	 * @return
	 */
	private ActionMessages validatePrerequisiteRequireds(
			List<CoursePrerequisiteTO> prerequisites, ActionMessages messages) {/*
		log.info("enter validatePrerequisiteRequireds...");
		if(messages==null){
			messages= new ActionMessages();
		}
		if(prerequisites!=null)
		{
			Iterator<CoursePrerequisiteTO> reqItr=prerequisites.iterator();
			while (reqItr.hasNext()) {
				CoursePrerequisiteTO reqTO = (CoursePrerequisiteTO) reqItr.next();
				if(reqTO.getUserMark()!=0.0 && reqTO.getTotalMark()!=0.0){
					if(reqTO.getExamMonth()==0){
						if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_REQUIRED)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_REQUIRED).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_REQUIRED);
							messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_REQUIRED, error);
							return messages;
						}
					}
					if(reqTO.getExamYear()==null || StringUtils.isEmpty(reqTO.getExamYear())){
						if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED);
							messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED, error);
							return messages;
						}
					}
					if(reqTO.getRollNo()==null || StringUtils.isEmpty(reqTO.getRollNo().trim())){
						if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED);
							messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED, error);
						}
					}

					if(reqTO.getExamYear()!=null && !StringUtils.isEmpty(reqTO.getExamYear()) && StringUtils.isNumeric(reqTO.getExamYear())){
						if(CommonUtil.isFutureYear(Integer.parseInt(reqTO.getExamYear()))){
							if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE).hasNext()) {
								ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE);
								messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE, error);
							}
						}
					}

					if(reqTO.getExamMonth()!=0){
						if(CommonUtil.isFutureOrCurrentYear(Integer.parseInt(reqTO.getExamYear()))){
							if(CommonUtil.isFutureMonth(reqTO.getExamMonth()-1)){
								if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE).hasNext()) {
									ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE);
									messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE, error);
								}
							}
						}
					}


				}
				if(reqTO.getUserMark()>reqTO.getTotalMark()){
					if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER);
						messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER, error);
					}
				}
			}
			 code added by sudhir 
			if(messages!=null && messages.isEmpty()){
				boolean isRequired = true;
				Iterator<CoursePrerequisiteTO> iterator=prerequisites.iterator();
				while (iterator.hasNext()) {
					CoursePrerequisiteTO coursePrerequisiteTO = (CoursePrerequisiteTO) iterator .next();
					if(coursePrerequisiteTO.getUserMark()!=0.0 && coursePrerequisiteTO.getTotalMark()!=0.0){
						if(isRequired){
							isRequired = false;
						}else{
							if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED_EITHER_ONE)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED_EITHER_ONE).hasNext()) {
								ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED_EITHER_ONE);
								messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED_EITHER_ONE, error);
							}
						}
					}
				 }
			}
			 code added by sudhir 
		}
		return messages;
			 */

		return messages;
	}
	/**
	 * checks pre requisite eligibility
	 * @param admForm
	 * @param messages
	 * @return
	 */
	private ActionMessages validatePrerequisite(
			AdmissionFormForm admForm,ActionMessages messages) throws Exception {/*
		log.info("enter validatePrerequisite...");
		if(messages==null){
			messages= new ActionMessages();
		}

		boolean required=true;
		List<CoursePrerequisiteTO> finalprereqList= new ArrayList<CoursePrerequisiteTO>();
		List<CoursePrerequisiteTO> prerequisites=admForm.getCoursePrerequisites();
		if(prerequisites!=null)
		{
			Iterator<CoursePrerequisiteTO> reqItr=prerequisites.iterator();
			while (reqItr.hasNext()) {
				CoursePrerequisiteTO reqTO = (CoursePrerequisiteTO) reqItr.next();
				if(reqTO.getUserMark()!=0.0){
					required=false;
					break;
				}
			}
			// IF ANY THING EMPTY
			if(required){
				if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED);
					messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED, error);
					}
			}else {
				// CHECK DUPLICATE ENTRY
				boolean duplicate=false;
				Iterator<CoursePrerequisiteTO> reqItr2=prerequisites.iterator();
				while (reqItr2.hasNext()) {
					CoursePrerequisiteTO reqTO = (CoursePrerequisiteTO) reqItr2.next();
					AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
					boolean duplicateroll=handler.checkDuplicatePrerequisite(reqTO.getExamYear(),reqTO.getRollNo());
					if(duplicateroll){
						duplicate=true;
						break;
					}
				}
				if(duplicate){
					if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_DUPLICATE)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_DUPLICATE).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_DUPLICATE);
						messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_DUPLICATE, error);
						}

				}else{
					//CHECK ELIGIBITY
						boolean eligible=false;
						Iterator<CoursePrerequisiteTO> reqItr3=prerequisites.iterator();
						while (reqItr3.hasNext()) {
							CoursePrerequisiteTO reqTO = (CoursePrerequisiteTO) reqItr3.next();
							double percentage=0.0;
							if(reqTO.getTotalMark()!=0.0)
								percentage=(reqTO.getUserMark()/reqTO.getTotalMark())*100;
							reqTO.setUserPercentage(percentage);
							if(reqTO.getPercentage()!=0.0)
							{
								if(reqTO.getUserPercentage()>=reqTO.getPercentage())
									{
										eligible=true;
										finalprereqList.add(reqTO);
										break;
									}
							}else
							{
								if(reqTO.getUserPercentage()>reqTO.getPercentage())
								{

									eligible=true;
									finalprereqList.add(reqTO);
									break;
								}
							}
						}
						if(eligible==false){
							if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID);
							messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID, error);
							}
						}
					//}
			}
		}
		admForm.setEligPrerequisites(finalprereqList);
		return messages;
			 */
		return messages;
	}



	/**
	 * init terms and conditions
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTermConditions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initTermConditions...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		CourseHandler crsHandle=CourseHandler.getInstance();
		String termcondition="";
		//get template for term condition
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(Integer.parseInt(admForm.getCourseId()),CMSConstants.TERMS_AND_CONDITION_TEMPLATE);
		if(list != null && !list.isEmpty()) {

			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null)
				termcondition = list.get(0).getTemplateDescription();

			String logoPath = "";
			byte[] logo = null;
			HttpSession session = request.getSession(false);

			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if (organisation != null) {
				logo = organisation.getLogo();
			}
			if (session != null) {
				session.setAttribute("LogoBytes", logo);
			}
			logoPath = request.getContextPath();
			logoPath = "<img src="
					+ logoPath
					+ "/LogoServlet alt='Logo not available' width='210' height='100'>";

			//REPLACE LOGO
			termcondition=termcondition.replace(CMSConstants.TEMPLATE_LOGO,logoPath);
		}
		/*
		Calendar cal= Calendar.getInstance();
		cal.setTime(new Date());
		int year =cal.get(cal.YEAR);*/
		int year = Integer.parseInt(admForm.getApplicationYear());

		List<TermsConditionChecklistTO> checkList=crsHandle.getTermsConditionCheckLists(Integer.parseInt(admForm.getCourseId()),year);

		admForm.setTermConditions(termcondition);

		admForm.setConditionChecklists(checkList);
		if((checkList==null || checkList.isEmpty() ) && (termcondition==null || StringUtils.isEmpty(termcondition) ) )
		{
			return mapping.findForward(CMSConstants.ADMISSIONFORM_INIT_PREREQUISITE_PAGE);
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_TERMCONDITION_PAGE);
	}
	/**
	 * Accept terms and conditions
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward acceptTermsConditions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
			ActionErrors errors = admForm.validate(mapping, request);
			validateOtherConditions(errors,admForm.getConditionChecklists());
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				resetTermChecklistSubmit(admForm);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_TERMCONDITION_PAGE);
			}
		}catch (Exception e) {
			log.error("error in acceptTermsConditions...",e);
			throw e;

		}

		return mapping.findForward(CMSConstants.ADMISSIONFORM_INIT_PREREQUISITE_PAGE);
	}
	/**
	 * validates other mandatory conditions
	 * @param errors
	 * @param conditionChecklists
	 */
	private void validateOtherConditions(ActionErrors errors,
			List<TermsConditionChecklistTO> conditionChecklists) {
		//check whether mandatory is checked or not
		if(conditionChecklists!=null){
			Iterator<TermsConditionChecklistTO> chkItr=conditionChecklists.iterator();
			while (chkItr.hasNext()) {
				TermsConditionChecklistTO chkTO = (TermsConditionChecklistTO) chkItr.next();
				if(chkTO.isMandatory() && !chkTO.isChecked())
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_TERMCHKLIST_NOTCHECKED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TERMCHKLIST_NOTCHECKED).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TERMCHKLIST_NOTCHECKED);
						errors.add(CMSConstants.ADMISSIONFORM_TERMCHKLIST_NOTCHECKED, error);
					}
				}

			}
		}
	}
	// admission section starts

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
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","admission_form");

		setUserId(request, admForm);
		cleanupEditData(admForm);
		boolean isCjc = CMSConstants.LINK_FOR_CJC;
		admForm.setIsCjc1(isCjc);
		admForm.setApplicationYear(null);
		if(request.getParameter("PUC")!=null){
			admForm.setPucApplicationEdit(true);
		}else{
			admForm.setPucApplicationEdit(false);
		}
		admForm.setPucApplicationDetailEdit(false);
		admForm.setPucApplicationDetailView(false);
		admForm.setOnlineApply(false);
		log.info("exit initApplicationEdit..");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INITEDIT_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApplicationModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initApplicationEdit..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		setUserId(request, admForm);
		cleanupEditData(admForm);
		boolean isCjc = CMSConstants.LINK_FOR_CJC;
		admForm.setIsCjc1(isCjc);
		admForm.setApplicationYear(null);
		admForm.setOnlineApply(false);
		if(request.getParameter("PUCEdit")!=null){
			admForm.setPucApplicationDetailEdit(true);
			admForm.setPucApplicationDetailView(false);
		}
		else if(request.getParameter("PUCView")!=null){
			admForm.setPucApplicationDetailView(true);
			admForm.setPucApplicationDetailEdit(false);
		}
		else{
			admForm.setPucApplicationDetailEdit(false);
			admForm.setPucApplicationDetailView(false);
		}
		admForm.setPucApplicationEdit(false);
		log.info("exit initApplicationEdit..");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INITMODIFY_PAGE);
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
			HttpServletResponse response) throws Exception {/*
		log.info("entered detailApplicationEdit..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		String prgId=null;
		admForm.setPrgId(prgId);
		try{
			 ActionErrors errors = admForm.validate(mapping, request);
			boolean isCjc = CMSConstants.LINK_FOR_CJC;
			admForm.setIsCjc1(isCjc);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				if(!admForm.isAdmissionEdit())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_INITMODIFY_PAGE);
				else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_INITEDIT_PAGE);
			}
				String applicationNumber = admForm.getApplicationNumber().trim();
				int applicationYear = Integer.parseInt(admForm.getApplicationYear());
				AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicantDetails(applicationNumber, applicationYear,admForm.isAdmissionEdit());

				if( applicantDetails == null){
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
						message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						messages.add("messages", message);
						saveMessages(request, messages);

						if(!admForm.isAdmissionEdit())
							return mapping.findForward(CMSConstants.ADMISSIONFORM_INITMODIFY_PAGE);
						else
						return mapping.findForward(CMSConstants.ADMISSIONFORM_INITEDIT_PAGE);

				} else {
					admForm.setBackLogs(applicantDetails.isBackLogs());
					//raghu added newly
					admForm.setIsSaypass(applicantDetails.getIsSaypass());
					List<ExamCenterTO> examCenterList = AdmissionFormHandler.getInstance().getExamCenters(applicantDetails.getSelectedCourse().getProgramId());
					admForm.setExamCenters(examCenterList);

					//get states list for edn qualification
					List<StateTO> ednstates=StateHandler.getInstance().getNativeStates(CMSConstants.KP_DEFAULT_COUNTRY_ID);
					admForm.setEdnStates(ednstates);
					List<EntrancedetailsTO> entrnaceList=EntranceDetailsHandler.getInstance().getEntranceDeatilsByProgram(applicantDetails.getSelectedCourse().getProgramId());
					admForm.setEntranceList(entrnaceList);
					if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getDob()!=null )
						applicantDetails.getPersonalData().setDob(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getDob(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));
					if(applicantDetails.getChallanDate()!=null )
						applicantDetails.setChallanDate(CommonUtil.ConvertStringToDateFormat(applicantDetails.getChallanDate(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));

					if(applicantDetails.getCourseChangeDate()!=null )
						applicantDetails.setCourseChangeDate(CommonUtil.ConvertStringToDateFormat(applicantDetails.getCourseChangeDate(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));

					if((applicantDetails.getAdmissionDate() == null || !applicantDetails.getAdmissionDate().trim().isEmpty()) && admForm.isAdmissionEdit()){
						applicantDetails.setAdmissionDate(CommonUtil.getTodayDate());
					}
					if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getRecommendedBy()!=null )
						admForm.setRecomendedBy(applicantDetails.getPersonalData().getRecommendedBy());
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
														if(programTO.getIsExamCenterRequired()==true){
															admForm.setExamCenterRequired(true);
														}
														else
														{
															admForm.setExamCenterRequired(false);
														}
													}
												}
											}
									}	
								}
							}
						}

						checkExtradetailsDisplay(admForm);
						checkLateralTransferDisplay(admForm);

						//raghu
						admForm.setCheckReligionId(CMSConstants.RELIGION_CHRISTIAN_TYPE);
						//parish and dioceses

						DioceseTransactionImpl txn=new DioceseTransactionImpl();
						List<DioceseTo> dioceseList=txn.getDiocesesList();
						admForm.setDioceseList(dioceseList);

						ParishTransactionImpl parishtxn=new ParishTransactionImpl();
						List<ParishTo> parishList=parishtxn.getParishes();
						admForm.setParishList(parishList);


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
						AdmissionFormHandler formhandler = AdmissionFormHandler.getInstance();
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
					List<IncomeTO> incomeList = AdmissionFormHandler.getInstance().getIncomes();
					admForm.setIncomeList(incomeList);

					//currencies
					List<CurrencyTO> currencyList = AdmissionFormHandler.getInstance().getCurrencies();
					admForm.setCurrencyList(currencyList);

					UniversityHandler unhandler = UniversityHandler.getInstance();
					List<UniversityTO> universities = unhandler.getUniversity();
					admForm.setUniversities(universities);

					OccupationTransactionHandler occhandler = OccupationTransactionHandler
					.getInstance();
					admForm.setOccupations(occhandler.getAllOccupation());

					if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(applicantDetails.getPersonalData().getPassportValidity()) )
						applicantDetails.getPersonalData().setPassportValidity(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getPassportValidity(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));

					// set photo to session
					if(applicantDetails.getEditDocuments()!=null){
						Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
						while (docItr.hasNext()) {
							ApplnDocTO docTO = (ApplnDocTO) docItr.next();
							if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
							{
								admForm.setSubmitDate(docTO.getSubmitDate());
							}
							if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
								HttpSession session= request.getSession(false);
								if(session!=null){
									session.setAttribute(AdmissionFormAction.PHOTOBYTES, docTO.getPhotoBytes());
								}
							}
						}
					}
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
						admForm.setPreferenceList(prefTos);
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
						admForm.setPreferenceList(prefTos);
					}
					ExamGenHandler genHandler = new ExamGenHandler();
					HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
					admForm.setSecondLanguageList(secondLanguage);
					// this condition is for Christ Puc  Admission Edit
					if(admForm.getPucApplicationEdit()==true){
						Iterator<SubjectGroupTO> subList=sujectgroupList.iterator();
						String id[] = new String[sujectgroupList.size()];
						int count=0;
						Set<Integer> subSet=AdmissionFormHelper.getInstance().getSubjectGroupByYearAndCourse(applicantDetails.getAppliedYear(),selectedCourse.getId());
						while (subList.hasNext()) {
							SubjectGroupTO subjectGroupTO = (SubjectGroupTO) subList.next();
							if(subjectGroupTO.getIsCommonSubGrp()!=null && subjectGroupTO.getIsCommonSubGrp() && subSet.contains(subjectGroupTO.getId())){
								id[count]=String.valueOf(subjectGroupTO.getId());
								count=count+1;
							}
						}
						admForm.getApplicantDetails().setSubjectGroupIds(id);
						String interviewDate=AdmissionFormHandler.getInstance().getInterviewDateOfApplicant(applicationNumber,applicationYear);
						if(interviewDate!=null)
						admForm.setPucInterviewDate(interviewDate);
					}
					if(CMSConstants.SHOW_LINK.equals("true")){
						admForm.setIsPresidance(false);
					}else{
						admForm.setIsPresidance(true);
					}
					//for ajax setting put preference lists in session
					HttpSession session= request.getSession(false);
					if(session!=null){
						session.setAttribute(CMSConstants.COURSE_PREFERENCES, preferences);
					}

					 String programId=CMSConstants.ENGINEERING_PROGRAM_ID;
						if(String.valueOf(applicantCourse.getProgramId()).equalsIgnoreCase(programId))
							admForm.setPrgId("Engineering");
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
			return mapping.findForward("PUCApplicantDetailView");
		}
		else if(admForm.getPucApplicationDetailEdit()){
			return mapping.findForward("PUCApplicantDetailEdit");
		}
		else if(admForm.getPucApplicationEdit()){
			return mapping.findForward("PUCApplicantEdit");
		}
		else if(!admForm.isAdmissionEdit())
 			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);

			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);

	}

	/**
	 * admission form submit after confirmation
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveConfirmedApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*

		log.info("entered updateApplicationEdit..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		String year=admForm.getApplicationYear();
		setUserId(request, admForm);
		try{
			admForm.setTempChecked(admForm.isAcceptAll());
			//validattion if needed
			ActionMessages errors=admForm.validate(mapping, request);
			validateEditProgramCourse(errors, admForm);
			if(admForm.isOnlineApply()){
				validateOnlineConfirmRequireds(admForm, errors);
				validateParentConfirmOnlineRequireds(admForm, errors);
				validateConfirmPaymentDetails(errors,admForm);

			}
			if(admForm.isSameTempAddr()){
				copyCurrToPermAddress(admForm);
			}

		  	validatePhoneValidity(admForm, errors);
			validateEditParentPhone(admForm, errors);
			validateEditGuardianPhone(admForm, errors);
			validateEditPassportIfNRI(admForm, errors);
			validateEditOtherOptions(admForm, errors);
			validateConfirmOtherOptions(admForm, errors);
			validateEditCommAddress(admForm, errors);
			validateEditCoursePreferences(admForm, errors);
			validateSubjectGroups(admForm, errors);
			if(admForm.isDisplayTCDetails())
			validateTcDetailsEdit(admForm, errors);
			//added by vishnu
			if((admForm.getApplicantDetails().getPersonalData().getGuardianMob1()!=null &&
					!admForm.getApplicantDetails().getPersonalData().getGuardianMob1().isEmpty() && 
					admForm.getApplicantDetails().getPersonalData().getGuardianMob2()!=null &&
					!admForm.getApplicantDetails().getPersonalData().getGuardianMob2().isEmpty()) || 

			    (admForm.getApplicantDetails().getPersonalData().getParentMob1()!=null &&
			    !admForm.getApplicantDetails().getPersonalData().getParentMob1().isEmpty()  && 
			    admForm.getApplicantDetails().getPersonalData().getParentMob2()!=null &&
			    !admForm.getApplicantDetails().getPersonalData().getParentMob2().isEmpty()))
			{
			}
			else
			{

				errors.add(CMSConstants.ADMISSIONFORM_PARENTGUARDIAN_CONTACT_NUMBER_REQUIRED,new ActionError(CMSConstants.ADMISSIONFORM_PARENTGUARDIAN_CONTACT_NUMBER_REQUIRED));
			}



//			if(admForm.getApplicantDetails().getPersonalData().isHandicapped()){
//				if((admForm.getApplicantDetails().getPersonalData().getHadnicappedDescription()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getHadnicappedDescription()))){
//					if (errors.get(CMSConstants.ERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED).hasNext()) {
//						ActionMessage error = new ActionMessage("errors.required","Hadicapped Description");
//						errors.add(CMSConstants.ERROR, error);
//					}
//				}
//			}

			//if(admForm.isDisplayEntranceDetails())
			//validateEntanceDetailsEdit(admForm, errors);
			//email comparision



			if(admForm.getApplicantDetails().getPersonalData().getEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getEmail())){
				if(admForm.getConfirmEmailId()!=null && !StringUtils.isEmpty(admForm.getConfirmEmailId())){
						if(!admForm.getApplicantDetails().getPersonalData().getEmail().equals(admForm.getConfirmEmailId())){
							if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
								errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
							}
						}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
					}
				}
			}else if(admForm.getConfirmEmailId()!=null && !StringUtils.isEmpty(admForm.getConfirmEmailId())){
				if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
				}
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

			// if admission validate admission date
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
			}


			if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())&& !CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
				}
			}
			//ra
			if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getReligionSection().getId()!=0){
				if (errors.get(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED)!=null ) {
					errors.add(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED));
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
				// online age range check

					if(admForm.isOnlineApply() && admForm.getAgeToLimit()!=0 && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())){
						boolean valid=validateOnlineDOB(admForm.getAgeFromLimit(),admForm.getAgeToLimit(),admForm.getApplicantDetails().getPersonalData().getDob());
						if(!valid){
							if (errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS).hasNext()) {
								errors.add(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS, new ActionError(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS));
							}
						}
				}




			}
//				else{
//				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
//					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
//				}
//			}
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
			validateEditEducationDetails(errors, admForm);
//			validateEditDocumentSize(admForm, errors);
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
			if(!admForm.isOnlineApply()){
				if(admForm.getMode() == null || admForm.getMode().trim().isEmpty()){
					errors.add(CMSConstants.ADMISSIONFORM_MODE_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_MODE_REQUIRED));
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

			if(admForm.getIsInterviewSelectionSchedule()!=null && admForm.getIsInterviewSelectionSchedule().equalsIgnoreCase("true"))
			{
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


			//raghu caste map
			if(admForm.getApplicantDetails().getPersonalData().getReligionId()!=null && !admForm.getApplicantDetails().getPersonalData().getReligionId().equalsIgnoreCase("")){
			Map<Integer,String>	subCasteMap=CommonAjaxHandler.getInstance().getSubCasteByReligion(Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getReligionId()));
				admForm.setSubCasteMap(subCasteMap);
			}

			if(errors==null)
				errors= new ActionMessages();
				if (errors != null && !errors.isEmpty()) {
					resetHardCopySubmit(admForm.getApplicantDetails());

					List<EdnQualificationTO> qualificationsList=new ArrayList();
					List<EdnQualificationTO> qualifications=admForm.getApplicantDetails().getEdnQualificationList();
					if(qualifications!=null){
						Iterator<EdnQualificationTO> qualificationTO= qualifications.iterator();
						while (qualificationTO.hasNext()) {
							EdnQualificationTO qualfTO = (EdnQualificationTO) qualificationTO.next();

							if(qualfTO.getUniversityId()!=null && !qualfTO.getUniversityId().isEmpty() && StringUtils.isNumeric(qualfTO.getUniversityId()))
							{
								List<CollegeTO> collegeMap = CommonAjaxHandler.getInstance().getCollegeByUniversityList(Integer.parseInt(qualfTO.getUniversityId()));
								qualfTO.setInstituteList(collegeMap);
							}
							qualificationsList.add(qualfTO);
						}
						admForm.setQualifications(qualificationsList);
						}

					saveErrors(request, errors);
					admForm.setMode(admForm.getMode());
					if(admForm.isOnlineApply())
						return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
					else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);

				}
		HttpSession session = request.getSession(false);
				if (session == null) {
					return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
				}

		AdmApplnTO applicantDetail=admForm.getApplicantDetails();
		//remove pasport country default check if passport number not selected
		removePassportCountryDefault(applicantDetail);
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		//check for seat allocation exceeded for admitted through or not

//		if(admForm.isAdmissionEdit()){
//			if(!admForm.isQuotaCheck()){
//			if(applicantDetail.getAdmittedThroughId()!=null && !StringUtils.isEmpty(applicantDetail.getAdmittedThroughId()) && StringUtils.isNumeric(applicantDetail.getAdmittedThroughId()) && applicantDetail.getCourse()!=null && applicantDetail.getCourse().getId()!=0){
//				exceeded=handler.checkSeatAllocationExceeded(Integer.parseInt(applicantDetail.getAdmittedThroughId()),applicantDetail.getCourse().getId());
//			}
//			}
//		}
		if(!admForm.isReviewWarned())
		{
			admForm.setReviewWarned(true);
			admForm.setReviewed("true");
			resetHardCopySubmit(applicantDetail);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = new ActionMessage(
					CMSConstants.APPLICATION_REVIEW_WARN);
			messages.add("messages", message);
			saveMessages(request, messages);

			if(admForm.isOnlineApply())
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
			else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
		}
//		else{
//			if(admForm.isAdmissionEdit() && !admForm.isEligibleCheck()){
//				boolean eligible=handler.checkEligibility(applicantDetail);
//				
//					if(!eligible)
//					{
//						admForm.setQuotaCheck(true);
//						admForm.setEligibleCheck(true);
//						resetHardCopySubmit(applicantDetail);
//						ActionMessages messages = new ActionMessages();
//						ActionMessage message = new ActionMessage(
//								CMSConstants.ADMISSIONFORM_ELIGIBILITY_WARN);
//						messages.add("messages", message);
//						saveErrors(request, messages);
//						return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
//					}
//				
//			}
//			if(admForm.isAdmissionEdit()){
//				boolean admitted=handler.checkAdmitted(applicantDetail);
//				if(admitted)
//				{
//					admForm.setQuotaCheck(true);
//					admForm.setEligibleCheck(true);
//					resetHardCopySubmit(applicantDetail);
//					ActionMessages messages = new ActionMessages();
//					ActionMessage message = new ActionMessage(
//							CMSConstants.ADMISSIONFORM_ADMISSION_DONE);
//					messages.add("messages", message);
//					saveErrors(request, messages);
//					return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
//				}
//			}
			// set preferences to one to
			PreferenceTO preferenceTO = new PreferenceTO();
			if(admForm.getPreferenceList()!=null){
				Iterator<CandidatePreferenceTO> iterator = admForm.getPreferenceList().iterator();

				while (iterator.hasNext()) {
					CandidatePreferenceTO prefTO = (CandidatePreferenceTO) iterator.next();
					if (prefTO.getPrefNo() == 1) {
						preferenceTO.setId(prefTO.getId());
						preferenceTO.setFirstPerfId(prefTO.getId());
						preferenceTO.setFirstPrefCourseId(String.valueOf(applicantDetail.getCourse().getId()));						
					} else if (prefTO.getPrefNo() == 2) {
						preferenceTO.setId(prefTO.getId());
						preferenceTO.setSecondPerfId(prefTO.getId());
						preferenceTO.setSecondPrefCourseId(String.valueOf(prefTO.getCoursId()));
					} else if (prefTO.getPrefNo() == 3) {
						preferenceTO.setId(prefTO.getId());
						preferenceTO.setThirdPerfId(prefTO.getId());
						preferenceTO.setThirdPrefCourseId(String.valueOf(prefTO.getCoursId()));

					}
				}
			}

			applicantDetail.setPreference(preferenceTO);
			applicantDetail.setMode(admForm.getMode());


			boolean result=handler.saveCompleteApplication(applicantDetail,admForm,session,false);

			if(result){
				IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();                                                                    
				String admApplnId=txn.getAdmApplnId(admForm);
				admForm.setAdmApplnId(admApplnId);
				if(admForm.getIsInterviewSelectionSchedule()!=null && admForm.getIsInterviewSelectionSchedule().equalsIgnoreCase("true")){
					handler.addUploadInterviewSelectedData(applicantDetail, admForm,  request);
				}

				admForm.setQuotaCheck(false);
				admForm.setEligibleCheck(false);
			//	if(admForm.isOnlineApply()) - Commented as mail has to be sent for students applying through offline also-newly added modification -Smitha
				handler.sendMailToStudent(admForm);

				request.setAttribute("transactionstatus", "success");
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_SUCCESS_STATUS,applicantDetail.getApplnNo(),applicantDetail.getPersonalData().getDob());
				messages.add("messages", message);
				saveMessages(request, messages);
				cleanupSessionData(session);
				cleanUpPageData(admForm);
			}else {
				ActionMessages message = new ActionMessages();
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE);
				message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE, error);
				saveErrors(request, message);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
				else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
			}


//		}
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
		cleanupEditData(admForm);
		cleanupEditSessionData(request);
		admForm.setApplicationYear(year);
		log.info("exit updateApplicationEdit..");
			if(admForm.isOnlineApply())
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMPRINT_PAGE);
			else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMPRINT_PAGE);


			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMPRINT_PAGE);
	}


	/**
	 * @param applicantDetail
	 */
	private void removePassportCountryDefault(AdmApplnTO applicantDetail) {
		if(applicantDetail!=null && applicantDetail.getPersonalData()!=null)
		{
			if((applicantDetail.getPersonalData().getPassportNo()==null || StringUtils.isEmpty(applicantDetail.getPersonalData().getPassportNo())
					)&& (applicantDetail.getPersonalData().getPassportCountry()!=0)){
				//clear the default value
				applicantDetail.getPersonalData().setPassportCountry(0);
			}

		}
	}
	/**
	 * @param errors
	 * @param admForm
	 */
	private void validateConfirmPaymentDetails(ActionMessages errors,
			AdmissionFormForm admForm) {

		log.info("enter validatePaymentDetails...");
		if(errors==null){
			errors= new ActionMessages();
		}
		if (admForm.getApplicantDetails().getChallanRefNo()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getChallanRefNo())) {
			//raghu
			//if (errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED).hasNext()) {
			//errors.add(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED));
			//}

		}
		if (admForm.getApplicantDetails().getJournalNo()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getJournalNo())) {

			if (errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED));
			}

		}
		if (admForm.getApplicantDetails().getChallanDate()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getChallanDate())) {

			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_REQUIRED));
			}

		}
		if (admForm.getApplicantDetails().getAmount()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getAmount())) {

			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED));
			}

		}

		log.info("exit validatePaymentDetails...");

	}
	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateConfirmOtherOptions(AdmissionFormForm admForm,
			ActionMessages errors) throws Exception {/*
		log.info("enter validateEditOtherOptions..");
		if(errors==null){
			errors= new ActionMessages();
		}

		if((admForm.getApplicantDetails().getPersonalData().getReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId())|| (admForm.getApplicantDetails().getPersonalData().getReligionId().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionOthers()) ))
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
				if((admForm.getApplicantDetails().getPersonalData().getSubReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getSubReligionId())|| (admForm.getApplicantDetails().getPersonalData().getSubReligionId().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers())) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED, error);
					}
				}
			}
			}

		if((admForm.getApplicantDetails().getPersonalData().getCasteId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteId())|| (admForm.getApplicantDetails().getPersonalData().getCasteId().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getCasteOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_CASTE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED, error);
			}
		}

		//raghu
		if((admForm.getApplicantDetails().getPersonalData().getCasteId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteId())|| (admForm.getApplicantDetails().getPersonalData().getCasteId().equalsIgnoreCase(AdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getCasteOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED, error);
			}
		}




		if(admForm.isOnlineApply()){
			if((admForm.getApplicantDetails().getPersonalData().getPermanentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentStateId()) )&& ((admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers())==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers()) ))
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED, error);
				}
			}
		}
		log.info("exit validateEditOtherOptions..");
			 */}
	/**
	 * @param admForm
	 * @param errors
	 */
	private void validatePhoneValidity(AdmissionFormForm admForm,
			ActionMessages errors) {/*
		log.info("enter validateEditPhone..");
		if(errors==null)
			errors= new ActionMessages();



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
			 */}
	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateOnlineConfirmRequireds(AdmissionFormForm admForm,
			ActionMessages errors) {/*
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

		if(admForm.getApplicantDetails().getPersonalData().getAreaType()==' ')
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getPersonalData().getBirthCountry()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthCountry()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED, error);
			}
		}
		if((admForm.getApplicantDetails().getPersonalData().getBirthState()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthState())|| admForm.getApplicantDetails().getPersonalData().getBirthState().equalsIgnoreCase(AdmissionFormAction.OTHER))&& (admForm.getApplicantDetails().getPersonalData().getStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getStateOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED, error);
			}
		}

		if((admForm.getApplicantDetails().getPersonalData().getPhNo1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1())) && (admForm.getApplicantDetails().getPersonalData().getPhNo2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2())) && (admForm.getApplicantDetails().getPersonalData().getPhNo3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED, error);
			}
		}


		if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED,
						error);
			}
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


				if((admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentDistricId()))&& (admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers())) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_CURRDISTRICT_REQUIRED, error);
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



		log.info("exit validateOnlineRequireds..");
			 */}
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
			HttpServletResponse response) throws Exception {/*
		log.info("entered updateApplicationEdit..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		setUserId(request, admForm);
		try{
			//validattion if needed
			ActionMessages errors=admForm.validate(mapping, request);
			validateEditProgramCourse(errors, admForm);
			//if(admForm.getPucApplicationEdit() || admForm.getPucApplicationDetailEdit()){
			//	validateEditPhoneForPUC(admForm, errors);
			//}else{
			//	validateEditPhone(admForm, errors);
			//}
			validateEditParentPhone(admForm, errors);
			validateEditParentPhoneMandatory(admForm, errors);
			validateEditGuardianPhone(admForm, errors);
			validateEditPassportIfNRI(admForm, errors);
			validateEditOtherOptions(admForm, errors);
			validateEditCommAddress(admForm, errors);
			validateEditCoursePreferences(admForm, errors);
			validateSubjectGroups(admForm, errors);
			if(admForm.isDisplayTCDetails())
			validateTcDetailsEdit(admForm, errors);
			if(admForm.isDisplayEntranceDetails())
			validateEntanceDetailsEdit(admForm, errors);

			if(admForm.getApplicantDetails().getPersonalData().getPermanentCountryId()==0){
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED, error);
				}
			}
			if(!admForm.getPucApplicationEdit() && !admForm.getPucApplicationDetailEdit()){
				String at=String.valueOf(admForm.getApplicantDetails().getPersonalData().getAreaType());
				if(at.isEmpty() || at.equals(" "))
				errors.add("admissionFormForm.areaType.required",new ActionError("admissionFormForm.areaType.required"));

				validatePaymentDetailsForAdmForm(errors, admForm);
				// commented by vishnu for pimin client 
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


			if(admForm.isAdmissionEdit() && (admForm.getApplicantDetails().getAdmissionDate() == null || admForm.getApplicantDetails().getAdmissionDate().isEmpty())){
				errors.add("knowledgepro.admission.date", new ActionError("knowledgepro.admission.date"));
			}
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
			if(admForm.getPucApplicationEdit()==true && admForm.getPucInterviewDate()!=null){
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
			}

			// if admission validate admission date
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

			if(!admForm.isAdmissionEdit()){
				if(admForm.isExamCenterRequired() && admForm.getApplicantDetails().getExamCenterId() == 0){
					if (errors.get("knowledgepro.admission.appln.exam.center.required") != null&& !errors.get("knowledgepro.admission.appln.exam.center.required").hasNext()) {
						errors.add("knowledgepro.admission.appln.exam.center.required",new ActionError("knowledgepro.admission.appln.exam.center.required"));
					}
				}
			}

			if(errors==null)
				errors= new ActionMessages();

				if (errors != null && !errors.isEmpty()) {
					resetHardCopySubmit(admForm.getApplicantDetails());
					saveErrors(request, errors);
					if(admForm.getPucApplicationDetailEdit()){
						return mapping.findForward("PUCApplicantDetailEdit");
					}
					if(admForm.getPucApplicationEdit()){
						return mapping.findForward("PUCApplicantEdit");
					}
					if(!admForm.isAdmissionEdit())
						return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE);
					else
						return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);

				}
		AdmApplnTO applicantDetail=admForm.getApplicantDetails();
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		//check for seat allocation exceeded for admitted through or not
		boolean exceeded=false;
		if(admForm.isAdmissionEdit()){
			if(!admForm.isQuotaCheck()){
			if(applicantDetail.getAdmittedThroughId()!=null && !StringUtils.isEmpty(applicantDetail.getAdmittedThroughId()) && StringUtils.isNumeric(applicantDetail.getAdmittedThroughId()) && applicantDetail.getCourse()!=null && applicantDetail.getCourse().getId()!=0){
				exceeded=handler.checkSeatAllocationExceeded(Integer.parseInt(applicantDetail.getAdmittedThroughId()),applicantDetail.getCourse().getId());
			}
			}
		}
		if(exceeded)
		{
			admForm.setQuotaCheck(true);
			resetHardCopySubmit(applicantDetail);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = new ActionMessage(
					CMSConstants.ADMISSIONFORM_EDIT_WARN);
			messages.add("messages", message);
			saveMessages(request, messages);
			if(admForm.getPucApplicationDetailEdit()){
				return mapping.findForward("PUCApplicantDetailEdit");
			}
			if(admForm.getPucApplicationEdit()){
				return mapping.findForward("PUCApplicantEdit");
			}
			if(!admForm.isAdmissionEdit())
				return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE);
			else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
		}else{
			if(admForm.isAdmissionEdit() && !admForm.isEligibleCheck()){
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
						if(admForm.getPucApplicationDetailEdit()){
							return mapping.findForward("PUCApplicantDetailEdit");
						}
						if(admForm.getPucApplicationEdit()){
							return mapping.findForward("PUCApplicantEdit");
						}
						if(!admForm.isAdmissionEdit())
							return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE);
						else
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
					if(admForm.getPucApplicationDetailEdit()){
						return mapping.findForward("PUCApplicantDetailEdit");
					}
					if(admForm.getPucApplicationEdit()){
						return mapping.findForward("PUCApplicantEdit");
					}
					if(!admForm.isAdmissionEdit())
						return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE);
					else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
				}
			}
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
		if(admForm.getPucApplicationDetailEdit()){
			return mapping.findForward(CMSConstants.ADMISSIONFORM_MODIFY_CONFIRM_PAGE);
		}
		if(admForm.getPucApplicationEdit()){
			return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_CONFIRM_PAGE);
		}
		log.info("exit updateApplicationEdit..");
		if(!admForm.isAdmissionEdit())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_MODIFY_CONFIRM_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_CONFIRM_PAGE);

			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_CONFIRM_PAGE);
	}
	/**
	 * @param errors
	 * @param admForm
	 */
	private void validatePaymentDetailsForAdmForm(ActionMessages errors,AdmissionFormForm admForm) throws Exception {

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
				//raghu
				//if (errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED).hasNext()) {
				//errors.add(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED));
				//}

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
	 * entrance validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEntanceDetailsEdit(AdmissionFormForm admForm,
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
	private void validateTcDetailsEdit(AdmissionFormForm admForm,
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
	 * reset check option for term condition
	 * @param applicantDetail
	 */
	private void resetTermChecklistSubmit(AdmissionFormForm admform) {
		log.info("enter resetTermChecklistSubmit...");
		if(admform.getConditionChecklists()!=null){
			Iterator<TermsConditionChecklistTO> chkItr=admform.getConditionChecklists().iterator();
			while (chkItr.hasNext()) {
				TermsConditionChecklistTO chkTO = (TermsConditionChecklistTO) chkItr.next();
				if(chkTO.isChecked()){
					chkTO.setChecked(false);
					chkTO.setTempChecked(true);
				}else{
					chkTO.setChecked(false);
					chkTO.setTempChecked(false);
				}

			}
		}
	}
	/**
	 * clean up session after edit done
	 * @param request
	 */
	private void cleanupEditSessionData(HttpServletRequest request) {
		log.info("enter cleanupEditSessionData...");
		HttpSession session=request.getSession(false);
		if(session==null){
			return;
		}else{
			if(session.getAttribute(AdmissionFormAction.PHOTOBYTES)!=null)
				session.removeAttribute(AdmissionFormAction.PHOTOBYTES);
			if(session.getAttribute(CMSConstants.KNOWLEDGEPRO_LOGO)!=null){
				session.removeAttribute(CMSConstants.KNOWLEDGEPRO_LOGO);}

		}
	}
	/**
	 * validate subject groups
	 * @param admForm
	 * @param errors
	 */
	private void validateSubjectGroups(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("enter validateSubjectGroups...");
		if(errors==null)
			errors= new ActionMessages();
		if(admForm.isAdmissionEdit()){
			/*if((admForm.getApplicantDetails().getSubjectGroupIds()==null || admForm.getApplicantDetails().getSubjectGroupIds().length==0))
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBJECTGROUP_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBJECTGROUP_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_SUBJECTGROUP_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_SUBJECTGROUP_REQUIRED, error);
					}
				}*/
			if((admForm.getApplicantDetails().getAdmittedThroughId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getAdmittedThroughId())))
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED, error);
				}
			}
			if((admForm.getApplicantDetails().getIsFreeShip()==null))
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED, error);
				}
			}

			if((admForm.getApplicantDetails().getIsLIG()==null))
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_LIG_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LIG_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_LIG_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_LIG_REQUIRED, error);
				}
			}
		}
	}
	/**
	 * reset blank display after admission submit
	 * @param admForm
	 */
	private void cleanupEditData(AdmissionFormForm admForm) {
		log.info("enter cleanupEditData...");
		admForm.setProgramId("");
		admForm.setProgramTypeId("");
		admForm.setCourseId("");
		admForm.setQuotaCheck(false);
		admForm.setEligibleCheck(false);
		admForm.setReviewWarned(false);
		admForm.setReviewed("false");
		admForm.setSubmitDate(null);
		admForm.setLateralDetails(null);
		admForm.setLateralInstituteAddress(null);
		admForm.setLateralStateName(null);
		admForm.setLateralUniversityName(null);
		admForm.setApplicationConfirm(false);
		admForm.setExamCenterRequired(false);
		admForm.setPucApplicationEdit(false);
		admForm.setPucInterviewDate(null);
		admForm.setRecomendedBy(null);
		admForm.setTempState(null);
	}

	//csv Section
	/**
	 * OMR DATA Upload initialization
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCSVUpload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initCSVUpload...");
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		admForm.setProgramId("");
		admForm.setProgramTypeId("");
		admForm.setCourseId("");
		admForm.setCsvFile(null);
		admForm.setApplicationYear(null);
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVUPLOADPAGE);
	}
	/**
	 * Upload OMR DATA To DB
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward updateCSV(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter updateCSV...");
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		FormFile csvfile=admForm.getCsvFile();
		ActionMessages errors= admForm.validate(mapping, request);
		validateProgramType(errors, admForm);
		if(errors==null)
			errors=new ActionMessages();
		//validate the csv type
		if(csvfile!=null){
			if(csvfile.getFileName()!=null && !StringUtils.isEmpty(csvfile.getFileName())){
				String extn="";
				int indx=csvfile.getFileName().lastIndexOf(".");
				if(indx!=-1)
					extn=csvfile.getFileName().substring(indx, csvfile.getFileName().length());
				if(!extn.equalsIgnoreCase(".CSV")){

					if(errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR).hasNext()){
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR);
						errors.add(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR, error);
					}
				}
			}else{
				if(errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED).hasNext()){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OMR_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_OMR_REQUIRED, error);
				}
			}
		}

		if (errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVUPLOADPAGE);
		}	
		Map<String,Integer> courseMap=null;
		if(admForm.getCourseId()!=null && !admForm.getCourseId().isEmpty()){
			courseMap=AdmissionFormHandler.getInstance().getCoursesById(Integer.parseInt(admForm.getCourseId()),1);
		}else if(admForm.getProgramId()!=null && !admForm.getProgramId().isEmpty()){
			courseMap=AdmissionFormHandler.getInstance().getCoursesById(Integer.parseInt(admForm.getProgramId()),2);
		}else{
			courseMap=AdmissionFormHandler.getInstance().getCoursesById(Integer.parseInt(admForm.getProgramTypeId()),3);
		}
		List<AdmAppln> applications=null;
		if(csvfile!=null){
			applications=CSVUpdater.parseFile(csvfile.getInputStream(),Integer.parseInt(admForm.getApplicationYear()),courseMap,admForm.getUserId());
		}
		if(applications!=null && !applications.isEmpty())
			CSVUpdater.persistCompleteApplicationData(applications,admForm);
		List<Integer> applnNos=admForm.getApplnNos();
		if(applnNos!=null){
			String appNos=null;
			Iterator it=applnNos.iterator();
			while(it.hasNext()){
				appNos= it.next().toString()+",";
			}
			ActionMessage message=new ActionMessage(appNos);
			errors.add(appNos, message);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVUPLOADCONFIRMPAGE);


		}else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVUPLOADCONFIRMPAGE);
	}
	/**
	 * This is used to diplay fields in UI when u click on cancel admission in left menu.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApplicationCancel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initApplicationCancel..");
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		admForm.setApplicationNumber(null);
		admForm.setCancellationReason(null);
		admForm.setRegNumber(null);
		admForm.setRollNo(null);
		cleanupEditData(admForm);
		admForm.setCancellationReason(null);
		admForm.setCancellationDate(null);
		admForm.setApplicationYear(null);
		admForm.setRemoveRegNo(null);
		log.info("exit initApplicationCancel..");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CANCELLATION);
	}

	/**
	 * This method is called when you click on cancel admission button.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward cancelApplicantDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered cancelApplicantDetails..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		setUserId(request, admForm);
		try{
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = admForm.validate(mapping, request);
			//			validateProgramCourse(errors,admForm,false);
			validateAppRegRollNumber(admForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_CANCELLATION_NEW);
			}
			//				int applicationNumber = Integer.parseInt(admForm.getApplicationNumber().trim());
			int applicationYear = Integer.parseInt(admForm.getApplicationYear());
			String remarks = admForm.getCancellationReason().trim();
			String cancelDate=admForm.getCancellationDate();
			AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
			//calling handler method by passing values.
			List<Integer> applno = handler.checkApplicationCancel(admForm);
			if(applno != null && applno.size() != 0){
				//if record exists in DB, call of handler method for cancel admission. 
				boolean isUpdated = handler.updateApplicationCancel(applno.get(0), applicationYear, remarks,cancelDate,admForm.getRemoveRegNo(),admForm.getUserId());
				if(isUpdated){
					//if success
					ActionMessage message = new ActionMessage(
							CMSConstants.ADMISSION_CANCEL_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
				}
				else{
					//if failure
					ActionMessage message = new ActionMessage(
							CMSConstants.ADMISSION_CANCEL_FAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
				}
			}
			else{
				//if record is not there with that application number.
				ActionMessage message = new ActionMessage(
						CMSConstants.APPLICATION_REGISTRATION_ROLL_NUMBER_NOTEXIST);
				messages.add("messages", message);
				errors.add(messages);
				saveErrors(request, messages);
			}
		}catch (Exception e) {
			log.error("Error in  cancelApplicantDetails application form ...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		admForm.setApplicationNumber(null);
		admForm.setCancellationReason(null);
		admForm.setRegNumber(null);
		admForm.setRollNo(null);
		cleanupEditData(admForm);
		admForm.setCancellationReason(null);
		admForm.setCancellationDate(null);
		admForm.setRemoveRegNo(null);
		log.info("exit cancelApplicantDetails..");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CANCELLATION);
	}

	public void validateAppRegRollNumber(AdmissionFormForm admForm, ActionErrors errors) throws Exception{
		log.info("entering into validateAppRegRollNumber in AdmissionFormAction class..");
		if (errors == null)
			errors = new ActionErrors();
		if(StringUtils.isEmpty(admForm.getApplicationNumber()) && StringUtils.isEmpty(admForm.getRegNumber()) && StringUtils.isEmpty(admForm.getRollNo())){
			errors.add(CMSConstants.ERROR, new ActionError(
					CMSConstants.APPLICATION_REGISTRATION_ROLL_NUMBER_REQUIRE));
		}
		if(!StringUtils.isEmpty(admForm.getApplicationNumber()) && !StringUtils.isNumeric(admForm.getApplicationNumber().trim())){
			errors.add(CMSConstants.ERROR, new ActionError(
					CMSConstants.ADMISSION_CANCEL_APPNUMBER_INT));
		}
		log.info("exit of validateAppRegRollNumber in AdmissionFormAction class..");	
	}


	/**
	 * @param admissionFormForm
	 * @param request
	 * @throws Exception
	 */
	public void setProgramAndCourseMap(AdmissionFormForm admissionFormForm,HttpServletRequest request) throws Exception {
		Map<Integer,String> programMap = new HashMap<Integer,String>();
		Map<Integer,String> courseMap = new HashMap<Integer,String>();
		if(admissionFormForm.getProgramTypeId().length() != 0 )
			programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(admissionFormForm.getProgramTypeId()));
		if(admissionFormForm.getProgramId().length() != 0)
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(admissionFormForm.getProgramId()));
		request.setAttribute("programMap", programMap);
		request.setAttribute("courseMap", courseMap);
	}	

	/**
	 * This method is to proceed to view details for admission approval
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward initAdmissionApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initAdmissionApproval..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		//clean up
		admForm.setApplicationNumber(null);
		admForm.setApplicationYear(null);
		admForm.setApplicantDetails(null);
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INITAPPROVAL);
	}


	/**
	 * This method is to proceed to view details for admission approval
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward submitAdmissionApprovalInit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitAdmissionApprovalInit..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
			ActionErrors errors = admForm.validate(mapping, request);

			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				return mapping.findForward(CMSConstants.ADMISSIONFORM_INITAPPROVAL);
			}
			String applicationNumber = admForm.getApplicationNumber().trim();
			int applicationYear = Integer.parseInt(admForm.getApplicationYear());
			AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApprovalApplicantDetails(applicationNumber, applicationYear,true);

			if( applicantDetails == null){

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.ADMISSIONFORM_NOAPPROVAL_PENDING);
				messages.add("messages", message);
				saveMessages(request, messages);

				return mapping.findForward(CMSConstants.ADMISSIONFORM_INITAPPROVAL);

			} else {
				if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getDob()!=null )
					applicantDetails.getPersonalData().setDob(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getDob(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));
				if(applicantDetails.getChallanDate()!=null )
					applicantDetails.setChallanDate(CommonUtil.ConvertStringToDateFormat(applicantDetails.getChallanDate(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));
				//set applicant details
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
				// set flags
				if (programtypes!=null) {
					admForm.setEditProgramtypes(programtypes);
					Iterator<ProgramTypeTO> typeitr= programtypes.iterator();
					while (typeitr.hasNext()) {
						ProgramTypeTO typeTO = (ProgramTypeTO) typeitr.next();
						if(typeTO.getProgramTypeId()==admForm.getApplicantDetails().getCourse().getProgramTypeId()){
							if(typeTO.getPrograms()!=null){
								admForm.setEditprograms(typeTO.getPrograms());
								Iterator<ProgramTO> programitr= typeTO.getPrograms().iterator();
								while (programitr.hasNext()) {
									ProgramTO programTO = (ProgramTO) programitr
											.next();
									if(programTO.getId()== admForm.getApplicantDetails().getCourse().getProgramId()){
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
										}
									}
								}
							}	
						}
					}
				}


				checkExtradetailsDisplay(admForm);
				checkLateralTransferDisplay(admForm);
				// Admitted Through
				List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
				admForm.setAdmittedThroughList(admittedList);
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				//						if(admForm.isDisplayAdditionalInfo())
				//						{

				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);
					admForm.setOrganizationName(orgTO.getOrganizationName());
					admForm.setNeedApproval(orgTO.isNeedApproval());
				}
				//						}

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
				if(admForm.getApplicantDetails().getSelectedCourse().getProgramTypeId()==Integer.parseInt(fileName)){
					admForm.setViewextradetails(true);
				}

				if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(applicantDetails.getPersonalData().getPassportValidity()) )
					applicantDetails.getPersonalData().setPassportValidity(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getPassportValidity(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));

				// set photo to session
				if(applicantDetails.getEditDocuments()!=null){
					Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
					while (docItr.hasNext()) {
						ApplnDocTO docTO = (ApplnDocTO) docItr.next();
						if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
						{
							admForm.setSubmitDate(docTO.getSubmitDate());
						}
						if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
							HttpSession session= request.getSession(false);
							if(session!=null){
								session.setAttribute(AdmissionFormAction.PHOTOBYTES, docTO.getPhotoBytes());
							}
						}
					}
				}
			}

		}catch (Exception e){
			log.error("Error in  getApplicantDetails application form approval page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				throw e;
			}
		}
		log.info("exit detailAdmissionApproval..");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_APPROVALDETAILS);
	}

	/**
	 * This method is to proceed to view details for admission approval
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward updateApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered updateApproval..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
			ActionErrors errors = admForm.validate(mapping, request);

			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				return mapping.findForward(CMSConstants.ADMISSIONFORM_APPROVALDETAILS);
			}
			AdmApplnTO applicantDetail=admForm.getApplicantDetails();
			AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
			int appNO=applicantDetail.getApplnNo();
			int appYear=applicantDetail.getAppliedYear();
			String appRemark=applicantDetail.getApprovalRemark();
			//raghu
			String admitThroughid=applicantDetail.getAdmittedThroughId();
			boolean updated=handler.updateApproval(appNO,appYear,appRemark,admitThroughid);
			if(!updated){
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.ADMISSIONFORM_APPROVAL_FAIL);
				messages.add("messages", message);
				saveMessages(request, messages);

				return mapping.findForward(CMSConstants.ADMISSIONFORM_APPROVALDETAILS);
			}
		}catch (Exception e){
			log.error("Error in  getApplicantDetails application form approval page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				throw e;
			}
		}
		log.info("exit updateApproval..");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_APPROVALCONFIRM);
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initlateralEntryPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter initlateralEntryPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			if(admForm.getLateralDetails()==null || admForm.getLateralDetails().isEmpty() ){
			List<ApplicantLateralDetailsTO> lateralDetails= new ArrayList<ApplicantLateralDetailsTO>();
			for(int i=0;i<CMSConstants.MAX_CANDIDATE_LATERALS;i++){
				ApplicantLateralDetailsTO to= new ApplicantLateralDetailsTO();
				to.setSemesterNo(i);
				lateralDetails.add(to);
			}
			Collections.sort(lateralDetails);
			admForm.setLateralDetails(lateralDetails);
			}
		} catch (Exception e) {
			log.error("error in initlateralEntryPage...",e);
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
		log.info("exit initlateralEntryPage...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_LATERAL_SEMESTER_PAGE);
		else
		return mapping.findForward(CMSConstants.ADMISSIONFORM_LATERAL_SEMESTER_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_LATERAL_SEMESTER_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitLateralEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitLateralEntry...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			 ActionErrors errors = admForm.validate(mapping, request);
			//Validate marks
			validateLateralMarks(admForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_LATERAL_SEMESTER_PAGE);
				else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_LATERAL_SEMESTER_PAGE);
			}
			// process lateral details
			List<ApplicantLateralDetailsTO> lateralDetails=admForm.getLateralDetails();
			if(lateralDetails!=null && !lateralDetails.isEmpty()){
				Set<ApplicantLateralDetails> lateralBos= new HashSet<ApplicantLateralDetails>();
				Iterator<ApplicantLateralDetailsTO> latItr=lateralDetails.iterator();
				while (latItr.hasNext()) {
					ApplicantLateralDetailsTO lateralTO = (ApplicantLateralDetailsTO) latItr.next();
					if(lateralTO!=null){
						ApplicantLateralDetails bo= new ApplicantLateralDetails();
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
						bo.setStateName(admForm.getLateralStateName());
						bo.setUniversityName(admForm.getLateralUniversityName());
						lateralBos.add(bo);
					}

				}

				if(lateralBos!=null && !lateralBos.isEmpty()){
					HttpSession session = request.getSession(false);
					if (session == null) {
						return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
					}
					session.setAttribute(CMSConstants.STUDENT_LATERALDETAILS, lateralBos);
				}
			}
		} catch (Exception e) {
			log.error("error in submitLateralEntry...",e);
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
		log.info("exit submitLateralEntry...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_EDUCATION_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_EDUCATION_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_EDUCATION_PAGE);
	}
	/**
	 * validate lateral marks
	 * @param admForm
	 * @param errors
	 */
	private void validateLateralMarks(AdmissionFormForm admForm,
			ActionErrors errors) {/*
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
			 */}

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
			HttpServletResponse response) throws Exception {/*
		log.info("enter initlateralEntryEditPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
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
		return mapping.findForward(CMSConstants.ADMISSIONFORM_LATERAL_SEMESTEREDIT_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_LATERAL_SEMESTEREDIT_PAGE);
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
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitLateralEntryEdit...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
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
		if(!admForm.isAdmissionEdit())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewLateralEntryPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter viewLateralEntryPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
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
			log.error("error in viewLateralEntryPage...",e);
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
		log.info("exit viewLateralEntryPage...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_LATERAL_SEMESTERVIEW_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_LATERAL_SEMESTERVIEW_PAGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTransferEntryPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter initTransferEntryPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			if(admForm.getTransferDetails()==null || admForm.getTransferDetails().isEmpty() ){
			List<ApplicantTransferDetailsTO> transferDetails= new ArrayList<ApplicantTransferDetailsTO>();
			for(int i=0;i<CMSConstants.MAX_CANDIDATE_TRANSFERS;i++){
				ApplicantTransferDetailsTO to= new ApplicantTransferDetailsTO();
				to.setSemesterNo(i);
				transferDetails.add(to);
			}
			Collections.sort(transferDetails);
			admForm.setTransferDetails(transferDetails);
			}
		} catch (Exception e) {
			log.error("error in initTransferEntryPage...",e);
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
		log.info("exit initTransferEntryPage...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_TRANSFER_SEMESTER_PAGE);
		else
		return mapping.findForward(CMSConstants.ADMISSIONFORM_TRANSFER_SEMESTER_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_TRANSFER_SEMESTER_PAGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitTransferEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitTransferEntry...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			 ActionErrors errors = admForm.validate(mapping, request);
			//Validate marks
			validateTransferMarks(admForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_TRANSFER_SEMESTER_PAGE);
				else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_TRANSFER_SEMESTER_PAGE);
			}
			// process lateral details
			List<ApplicantTransferDetailsTO> transferDetails=admForm.getTransferDetails();
			if(transferDetails!=null && !transferDetails.isEmpty()){
				Set<ApplicantTransferDetails> transferBos= new HashSet<ApplicantTransferDetails>();
				Iterator<ApplicantTransferDetailsTO> latItr=transferDetails.iterator();
				while (latItr.hasNext()) {
					ApplicantTransferDetailsTO trnTO = (ApplicantTransferDetailsTO) latItr.next();
					if(trnTO!=null){
						ApplicantTransferDetails bo= new ApplicantTransferDetails();
						bo.setInstituteAddr(admForm.getTransInstituteAddr());
						if(trnTO.getMarksObtained()!=null && !StringUtils.isEmpty(trnTO.getMarksObtained().trim()) && CommonUtil.isValidDecimal(trnTO.getMarksObtained().trim()))
						bo.setMarksObtained(new BigDecimal(trnTO.getMarksObtained().trim()));
						if(trnTO.getMaxMarks()!=null && !StringUtils.isEmpty(trnTO.getMaxMarks().trim()) && CommonUtil.isValidDecimal(trnTO.getMaxMarks().trim()))
						bo.setMaxMarks(new BigDecimal(trnTO.getMaxMarks().trim()));
						if(trnTO.getMinMarks()!=null && !StringUtils.isEmpty(trnTO.getMinMarks().trim()) && CommonUtil.isValidDecimal(trnTO.getMinMarks().trim()))
							bo.setMinMarks(new BigDecimal(trnTO.getMinMarks().trim()));
						if(trnTO.getMonthPass()!=null && !StringUtils.isEmpty(trnTO.getMonthPass().trim()) && StringUtils.isNumeric(trnTO.getMonthPass().trim()))
							bo.setMonthPass(Integer.parseInt(trnTO.getMonthPass().trim()));

						if(trnTO.getYearPass()!=null && !StringUtils.isEmpty(trnTO.getYearPass().trim()) && StringUtils.isNumeric(trnTO.getYearPass().trim()))
							bo.setYearPass(Integer.parseInt(trnTO.getYearPass().trim()));
						bo.setSemesterName(trnTO.getSemesterName());
						bo.setSemesterNo(trnTO.getSemesterNo());
						//COMMON DETAILS SET FROM FORM
						bo.setArrearDetail(admForm.getTransArrearDetail());
						bo.setUniversityAppNo(admForm.getTransUnvrAppNo());
						bo.setRegistationNo(admForm.getTransRegistationNo());
						transferBos.add(bo);
					}

				}

				if(transferBos!=null && !transferBos.isEmpty()){
					HttpSession session = request.getSession(false);
					if (session == null) {
						return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
					}
					session.setAttribute(CMSConstants.STUDENT_TRANSFERDETAILS, transferBos);
				}
			}
		} catch (Exception e) {
			log.error("error in submitLateralEntry...",e);
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
		log.info("exit submitTransferEntry...");

		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_EDUCATION_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_EDUCATION_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_EDUCATION_PAGE);
	}

	/**
	 * validate lateral marks
	 * @param admForm
	 * @param errors
	 */
	private void validateTransferMarks(AdmissionFormForm admForm,
			ActionErrors errors) {/*
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
			 */}

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
			HttpServletResponse response) throws Exception {/*
		log.info("enter initTransferEntryEditPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
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
		return mapping.findForward(CMSConstants.ADMISSIONFORM_TRANSFER_SEMESTEREDIT_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_TRANSFER_SEMESTEREDIT_PAGE);	
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
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitTransferEditEntry...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			 ActionErrors errors = admForm.validate(mapping, request);
			//Validate marks
			validateTransferMarks(admForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				return mapping.findForward(CMSConstants.ADMISSIONFORM_TRANSFER_SEMESTEREDIT_PAGE);
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
		if(!admForm.isAdmissionEdit())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILMODIFY_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_DETAILEDIT_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTransferEntryPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter viewTransferEntryPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			if(admForm.getApplicantDetails().getTransferDetails()==null || admForm.getApplicantDetails().getTransferDetails().isEmpty() ){
				List<ApplicantTransferDetailsTO> lateralDetails= new ArrayList<ApplicantTransferDetailsTO>();
				for(int i=0;i<CMSConstants.MAX_CANDIDATE_TRANSFERS;i++){
					ApplicantTransferDetailsTO to= new ApplicantTransferDetailsTO();
					to.setSemesterNo(i);
					lateralDetails.add(to);
				}
				Collections.sort(lateralDetails);
				admForm.setTransferDetails(lateralDetails);
			}else{
				if(admForm.getApplicantDetails().getTransferDetails()!=null && !admForm.getApplicantDetails().getTransferDetails().isEmpty()){

					Iterator<ApplicantTransferDetailsTO> latItr=admForm.getApplicantDetails().getTransferDetails().iterator();
					while (latItr.hasNext()) {
						ApplicantTransferDetailsTO lateralTO = (ApplicantTransferDetailsTO) latItr.next();
						if(lateralTO!=null){
							if(lateralTO.getInstituteAddr()!=null && !StringUtils.isEmpty(lateralTO.getInstituteAddr()))
							admForm.setTransInstituteAddr(lateralTO.getInstituteAddr());
							if(lateralTO.getRegistationNo()!=null && !StringUtils.isEmpty(lateralTO.getRegistationNo()))
								admForm.setTransRegistationNo(lateralTO.getRegistationNo());
								if(lateralTO.getUniversityAppNo()!=null && !StringUtils.isEmpty(lateralTO.getUniversityAppNo()))
								admForm.setTransUnvrAppNo(lateralTO.getUniversityAppNo());
								if(lateralTO.getArrearDetail()!=null && !StringUtils.isEmpty(lateralTO.getArrearDetail()))
									admForm.setTransArrearDetail(lateralTO.getArrearDetail());
						}

					}
					Collections.sort(admForm.getApplicantDetails().getTransferDetails());
					admForm.setTransferDetails(admForm.getApplicantDetails().getTransferDetails());
				}




			}
		} catch (Exception e) {
			log.error("error in viewTransferEntryPage...",e);
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
		log.info("exit viewTransferEntryPage...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_TRANSFER_SEMESTERVIEW_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_TRANSFER_SEMESTERVIEW_PAGE);
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardPrintWindow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AdmissionFormForm admForm = (AdmissionFormForm) form;
		//set caste show or not
		admForm.setCasteDisplay(CMSConstants.CASTE_ENABLED);
		try{


			String applicationNumber ="";
			if(admForm.getApplicationNumber()!=null && !admForm.getApplicationNumber().equalsIgnoreCase("")){
				applicationNumber = admForm.getApplicationNumber().trim();

			}
			if(admForm.getApplnNo()!=null && !admForm.getApplnNo().equalsIgnoreCase("")){
				applicationNumber = admForm.getApplnNo().trim();

			}
			//String applicationNumber = admForm.getApplicationNumber().trim();
			int applicationYear = Integer.parseInt(admForm.getApplicationYear());

			//make values null
			admForm.setApplicationYear(null);
			admForm.setApplnNo(null);
			admForm.setApplicationNumber(null);
			admForm.setApplicantDetails(null);

			AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicationDetails(applicationNumber, applicationYear);

			if( applicantDetails == null){

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_NORECORDS);
				messages.add("messages", message);
				saveMessages(request, messages);

				return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMPRINT_PAGE);

			} else {
				if(applicantDetails.getId()>0)
				{
					String txnRefNo = AdmissionFormHandler.getInstance().getCandidatePGIDetails(applicantDetails.getId());
					if(txnRefNo!=null && !txnRefNo.isEmpty())
						admForm.setTxnRefNo(txnRefNo);
					AdmissionFormHandler.getInstance().getOnlinePaymentAcknowledgementDetails(admForm,applicantDetails.getId());
				}
				if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getDob()!=null )
					applicantDetails.getPersonalData().setDob(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getDob(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));
				if(applicantDetails.getChallanDate()!=null )
					applicantDetails.setChallanDate(CommonUtil.ConvertStringToDateFormat(applicantDetails.getChallanDate(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));
				//set applicant details
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
				// set flags
				if (programtypes!=null) {
					admForm.setEditProgramtypes(programtypes);
					Iterator<ProgramTypeTO> typeitr= programtypes.iterator();
					while (typeitr.hasNext()) {
						ProgramTypeTO typeTO = (ProgramTypeTO) typeitr.next();
						if(typeTO.getProgramTypeId()==admForm.getApplicantDetails().getCourse().getProgramTypeId()){
							if(typeTO.getPrograms()!=null){
								admForm.setEditprograms(typeTO.getPrograms());
								Iterator<ProgramTO> programitr= typeTO.getPrograms().iterator();
								while (programitr.hasNext()) {
									ProgramTO programTO = (ProgramTO) programitr
											.next();
									if(programTO.getId()== admForm.getApplicantDetails().getCourse().getProgramId()){
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
										}
									}
								}
							}	
						}
					}
				}


				checkExtradetailsDisplay(admForm);
				checkLateralTransferDisplay(admForm);
				// Admitted Through
				List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
				admForm.setAdmittedThroughList(admittedList);
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				//				if(admForm.isDisplayAdditionalInfo())
				//				{

				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);
					admForm.setOrganizationName(orgTO.getOrganizationName());
					admForm.setNeedApproval(orgTO.isNeedApproval());
				}
				//				}


				if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(applicantDetails.getPersonalData().getPassportValidity()) )
					applicantDetails.getPersonalData().setPassportValidity(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getPassportValidity(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));

				// set photo to session
				HttpSession session= request.getSession(false);
				/*			session.setAttribute("STUDENT_IMAGE", "images/StudentPhotos/"+admForm.getStudentId()+".jpg?"+applicantDetails.getLastModifiedDate());
				 */

				if(applicantDetails.getEditDocuments()!=null){
					Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
					while (docItr.hasNext()) {
						ApplnDocTO docTO = (ApplnDocTO) docItr.next();
						if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
						{
							admForm.setSubmitDate(docTO.getSubmitDate());
						}
						if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){

							//						/*HttpSession*/ session= request.getSession(false);
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
										session.setAttribute(AdmissionFormAction.PHOTOBYTES,fileData );
								}
							}else if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){

								if(session!=null){
									session.setAttribute(AdmissionFormAction.PHOTOBYTES, docTO.getPhotoBytes());
								}
							}
						}
					}
				}

				//get template and replace the data
				String template=AdmissionFormHandler.getInstance().getDeclarationTemplate(applicantDetails,request);
				admForm.setInstrTemplate(template);

				Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
				if(organisation!=null){
					StringBuffer orgAdd=new StringBuffer();
					orgAdd.append(organisation.getAddressLine1());
					if(organisation.getAddressLine2()!=null && !StringUtils.isEmpty(organisation.getAddressLine2())){
						orgAdd.append(",");
						orgAdd.append(organisation.getAddressLine2());
					}
					if(organisation.getAddressLine3()!=null && !StringUtils.isEmpty(organisation.getAddressLine3())){
						orgAdd.append(",");
						orgAdd.append(organisation.getAddressLine3());
					}

					admForm.setOrgAddress(orgAdd.toString());
					// set photo to session
					if(organisation.getLogoContentType()!=null){
						if(session!=null){
							session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());

						}
					}
				}

				//------------bar code creation
				if(!applicationNumber.trim().isEmpty()){
					String imgPath=request.getRealPath("");
					imgPath = imgPath + "//BarCode//"+ applicationNumber + ".jpeg";
					File barCodeImgFile =  new File(imgPath);
					if(barCodeImgFile.exists()){
						barCodeImgFile.delete();
					}
					Barcode code = BarcodeFactory.createCode128A(applicationNumber);
					code.setBarWidth(1);
					code.setBarHeight(40);
					code.setDrawingText(false);
					BarcodeImageHandler.saveJPEG(code, barCodeImgFile);
				}					
				if(admForm.isWorkExpNeeded()){
					String totalExp=getTotalYearsOfExperience(applicantDetails.getWorkExpList());
					admForm.setTotalYearOfExp(totalExp);
				}
			}
		}catch (Exception e){
			log.error("Error in  getApplicantDetails application form approval page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.ADMISSIONFORM_PRINT_PAGE);
	}

	/**
	 * @param admForm
	 * @return
	 * @throws Exception
	 */
	public static String getTotalYearsOfExperience(List<ApplicantWorkExperienceTO> workExperiences) throws Exception{
		String years = null;
		int totalDays=0;
		if(workExperiences!=null){
			for(ApplicantWorkExperienceTO workExperience:workExperiences)
			{/*
				if(workExperience.getFromDate()!=null && workExperience.getToDate()!=null){
					Date startDate=CommonUtil.ConvertStringToSQLDate(workExperience.getFromDate());
					Date endDate=CommonUtil.ConvertStringToSQLDate(workExperience.getToDate());
					months+=CommonUtil.monthsBetweenTwoDates(startDate,endDate);
				}
			 */
				/*Code added by Sudhir */
				Calendar c1 = new GregorianCalendar();
				Calendar c2 = new GregorianCalendar();				

				if(workExperience.getFromDate()!=null)
				{
					String startDate =workExperience.getFromDate();
					Date startDate1= CommonUtil.ConvertStringToDate(startDate);
					c1.setTime(startDate1);
				}
				if(workExperience.getToDate()!=null)
				{
					String endDate = workExperience.getToDate();
					Date	endDate1= CommonUtil.ConvertStringToDate(endDate);
					c2.setTime(endDate1);
				}

				if(c1.after(c2))
				{
					throw new RuntimeException("End Date earlier than Start Date");
				}
				totalDays += c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
				int y2 = c2.get(Calendar.YEAR);

				while (c1.get(Calendar.YEAR) != y2)
				{
					totalDays += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
					c1.add(Calendar.YEAR, 1);
				}
			}
			double noOfYear=(double)totalDays/365;
			BigDecimal bd = new BigDecimal(Double.toString(noOfYear));
			bd = bd.setScale(1, BigDecimal.ROUND_UP);
			years= bd.toString();
		}
		return years;
		/*Code added by Sudhir */
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardConfirmPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		if(admForm.isOnlineApply()&& !admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
	}

	/**
	 * INIT SEMESTER MARK Review PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSemesterMarkConfirmPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter initSemesterMarkConfirmPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {

			String indexString = request.getParameter(AdmissionFormAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					session.setAttribute(AdmissionFormAction.COUNTID, indexString);
					index=Integer.parseInt(indexString);
				}else
					session.removeAttribute(AdmissionFormAction.COUNTID);
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
		} catch (Exception e) {
			log.error("error in initSemesterMarkConfirmPage...",e);
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
		log.info("exit initSemesterMarkConfirmPage...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_SEM_MARK_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_SEM_MARK_PAGE);

			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_SEM_MARK_PAGE);
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
	public ActionForward submitSemesterConfirmMark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitSemesterConfirmMark...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(AdmissionFormAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			List<ApplicantMarkDetailsTO> semesterMarks = admForm.getSemesterList();
			ActionMessages errors = validateEditSemesterMarks(semesterMarks);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_SEM_MARK_PAGE);
				else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_SEM_MARK_PAGE);
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
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit submitSemesterConfirmMark...");
		if(admForm.isOnlineApply()&& !admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
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
			HttpServletResponse response) throws Exception {/*
		log.info("enter initDetailMarkConfirmPage page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {

			String indexString = request.getParameter(AdmissionFormAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(AdmissionFormAction.COUNTID, indexString);
				}else
					session.removeAttribute(AdmissionFormAction.COUNTID);
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
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE);
		else
		return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
	}



	public ActionForward initDetailMarkConfirmPage12(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter initDetailMarkConfirmPage page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {

			String indexString = request.getParameter(AdmissionFormAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(AdmissionFormAction.COUNTID, indexString);
				}else
					session.removeAttribute(AdmissionFormAction.COUNTID);
			}
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			List<AdmSubjectMarkForRankTO> admsubList= new LinkedList<AdmSubjectMarkForRankTO>();
			if( admForm.getAdmsubMarkList()!=null && admForm.getAdmsubMarkList().size()!=0){
				//admForm.setAdmsubMarkList(admsubList);
				List<AdmSubjectMarkForRankTO> admSubList=new LinkedList<AdmSubjectMarkForRankTO>();
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
				for(int i=1;i<10;i++){
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
								Map<Integer,String> admsubjectMap=AdmissionFormHandler.getInstance().get12thclassSubjects(qualTO.getDocName(),language);
								admForm.setAdmSubjectMap(admsubjectMap);
								Map<Integer,String> admsubjectLangMap=AdmissionFormHandler.getInstance().get12thclassLangSubjects(qualTO.getDocName(),language);
								admForm.setAdmSubjectLangMap(admsubjectLangMap);

								//find id from english in Map
						        String strValue="English";
						        String intKey = null;
						        for(Map.Entry entry: admsubjectLangMap.entrySet()){
						            if(strValue.equals(entry.getValue())){
						            	intKey =entry.getKey().toString();
						            	//admForm.setSubid_0(intKey);
						                break; //breaking because its one to one map
						            }
						        }



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
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH);
		else
		return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);

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
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitDetailMarkConfirm page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(AdmissionFormAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();

			ActionMessages errors = new ActionMessages();


			//raghu write newly
			if(admForm.getTotalmaxmark()!=null &&  !CommonUtil.isValidDecimal(admForm.getTotalmaxmark())){
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}

			}

			if(admForm.getTotalobtainedmark()!=null && StringUtils.isEmpty(admForm.getTotalobtainedmark()) && !CommonUtil.isValidDecimal(admForm.getTotalobtainedmark())) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}

			}

			if(admForm.getTotalobtainedmark()!=null &&  !CommonUtil.isValidDecimal(admForm.getTotalobtainedmark())) {
				if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
					}

			}


			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE);
				else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
			}

			//raghu added newly
			errors = validateMarks(detailmark,new ActionMessages());

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE);
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
		if(admForm.isOnlineApply()&& !admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);

			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
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
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitDetailMarkConfirm page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(AdmissionFormAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);

			CandidateMarkTO detailmark = admForm.getDetailMark();

			Map<Integer,String> admlangmap=admForm.getAdmSubjectLangMap();
			Map<Integer,String> admsubmap=admForm.getAdmSubjectMap();
			List<AdmSubjectMarkForRankTO>  admsubList=admForm.getAdmsubMarkList();



				//if(detailmark.getSubject1()==null){

					if(admForm.getSubid_0()!=null ){
						if(admForm.getSubid_0().equalsIgnoreCase("")){
							detailmark.setSubject1("");
							detailmark.setSubject1ObtainedMarks("");
							detailmark.setSubject1TotalMarks("");

						}else{


						if(admlangmap.containsKey(Integer.parseInt(admForm.getSubid_0()))){

							String s=(String)admlangmap.get(Integer.parseInt(admForm.getSubid_0()));
							detailmark.setSubject1(s);
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_0()))){

							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_0()));
							detailmark.setSubject1(s);
						}
						}

					}

					if(admForm.getMaxmark_0()!=null && !admForm.getMaxmark_0().equalsIgnoreCase("")){
						detailmark.setSubject1TotalMarks(admForm.getMaxmark_0());
					}

					if(admForm.getObtainedmark_0()!=null && !admForm.getObtainedmark_0().equalsIgnoreCase("")){
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
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_1()))){

							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_1()));
							detailmark.setSubject2(s);
						}

						}	
					}

					if(admForm.getMaxmark_1()!=null && !admForm.getMaxmark_1().equalsIgnoreCase("")){
						detailmark.setSubject2TotalMarks(admForm.getMaxmark_1());
					}

					if(admForm.getObtainedmark_1()!=null && !admForm.getObtainedmark_1().equalsIgnoreCase("")){
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
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_2()))){

							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_2()));
							detailmark.setSubject3(s);
						}

					}
					}

					if(admForm.getMaxmark_2()!=null && !admForm.getMaxmark_2().equalsIgnoreCase("")){
						detailmark.setSubject3TotalMarks(admForm.getMaxmark_2());
					}

					if(admForm.getObtainedmark_2()!=null && !admForm.getObtainedmark_2().equalsIgnoreCase("")){
						detailmark.setSubject3ObtainedMarks(admForm.getObtainedmark_2());
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
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_3()))){

							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_3()));
							detailmark.setSubject4(s);
						}

					}
					}

					if(admForm.getMaxmark_3()!=null && !admForm.getMaxmark_3().equalsIgnoreCase("")){
						detailmark.setSubject4TotalMarks(admForm.getMaxmark_3());
					}

					if(admForm.getObtainedmark_3()!=null && !admForm.getObtainedmark_3().equalsIgnoreCase("")){
						detailmark.setSubject4ObtainedMarks(admForm.getObtainedmark_3());
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
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_4()))){

							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_4()));
							detailmark.setSubject5(s);
						}

						}

					}

					if(admForm.getMaxmark_4()!=null && !admForm.getMaxmark_4().equalsIgnoreCase("")){
						detailmark.setSubject5TotalMarks(admForm.getMaxmark_4());
					}

					if(admForm.getObtainedmark_4()!=null && !admForm.getObtainedmark_4().equalsIgnoreCase("")){
						detailmark.setSubject5ObtainedMarks(admForm.getObtainedmark_4());
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
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_5()))){

							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_5()));
							detailmark.setSubject6(s);
						}

						}	
					}

					if(admForm.getMaxmark_5()!=null && !admForm.getMaxmark_5().equalsIgnoreCase("")){
						detailmark.setSubject6TotalMarks(admForm.getMaxmark_5());
					}

					if(admForm.getObtainedmark_5()!=null && !admForm.getObtainedmark_5().equalsIgnoreCase("")){
						detailmark.setSubject6ObtainedMarks(admForm.getObtainedmark_5());
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
						}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_6()))){

							String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_6()));
							detailmark.setSubject7(s);
						}

					}
					}

					if(admForm.getMaxmark_6()!=null && !admForm.getMaxmark_6().equalsIgnoreCase("")){
						detailmark.setSubject7TotalMarks(admForm.getMaxmark_6());
					}

					if(admForm.getObtainedmark_6()!=null && !admForm.getObtainedmark_6().equalsIgnoreCase("")){
						detailmark.setSubject7ObtainedMarks(admForm.getObtainedmark_6());
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
			}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_7()))){

				String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_7()));
				detailmark.setSubject8(s);
			}
			}

		}

		if(admForm.getMaxmark_7()!=null && !admForm.getMaxmark_7().equalsIgnoreCase("")){
			detailmark.setSubject8TotalMarks(admForm.getMaxmark_7());
		}

		if(admForm.getObtainedmark_7()!=null && !admForm.getObtainedmark_7().equalsIgnoreCase("")){
			detailmark.setSubject8ObtainedMarks(admForm.getObtainedmark_7());
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
			}else if(admsubmap.containsKey(Integer.parseInt(admForm.getSubid_8()))){

				String s=(String)admsubmap.get(Integer.parseInt(admForm.getSubid_8()));
				detailmark.setSubject9(s);
			}

			}

		}

		if(admForm.getMaxmark_8()!=null && !admForm.getMaxmark_8().equalsIgnoreCase("")){
			detailmark.setSubject9TotalMarks(admForm.getMaxmark_8());
		}

		if(admForm.getObtainedmark_8()!=null && !admForm.getObtainedmark_8().equalsIgnoreCase("")){
			detailmark.setSubject9ObtainedMarks(admForm.getObtainedmark_8());
		}





	//}//sub 9 over




		ActionMessages errors = new ActionMessages();


		//raghu write newly
		if(admForm.getTotalmaxmark()!=null && StringUtils.isEmpty(admForm.getTotalmaxmark()) && !CommonUtil.isValidDecimal(admForm.getTotalmaxmark())){
			if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}

		}

		//raghu write newly
		if(admForm.getTotalmaxmark()!=null &&  !CommonUtil.isValidDecimal(admForm.getTotalmaxmark())){
			if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}

		}

		if(admForm.getTotalobtainedmark()!=null && StringUtils.isEmpty(admForm.getTotalobtainedmark()) && !CommonUtil.isValidDecimal(admForm.getTotalobtainedmark())) {
			if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}

		}

		if(admForm.getTotalobtainedmark()!=null &&  !CommonUtil.isValidDecimal(admForm.getTotalobtainedmark())) {
			if(errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);
				}

		}


		if (errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
			if(admForm.isOnlineApply())
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH);
			else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
		}

		//raghu added newly
		validateMarksFor12thClass(detailmark,errors);
		if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH);
				else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
			}

			if(admForm.getTotalmaxmark()!=null){
				detailmark.setTotalMarks(admForm.getTotalmaxmark());
			}
			if(admForm.getTotalobtainedmark()!=null){
				detailmark.setTotalObtainedMarks(admForm.getTotalobtainedmark());
			}

			int i=0;
			Iterator<AdmSubjectMarkForRankTO> itr=admsubList.iterator();
			while(itr.hasNext()){
				AdmSubjectMarkForRankTO admSubjectMarkForRankTO=(AdmSubjectMarkForRankTO) itr.next();
			if(i==0){
				if(admForm.getSubid_0()!=null && !admForm.getSubid_0().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_0());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_0());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_0());

			}
				i++;
			}
			else if(i==1){
				if(admForm.getSubid_1()!=null && !admForm.getSubid_1().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_1());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_1());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_1());

				}
				i++;
			}
			else if(i==2){
				if(admForm.getSubid_2()!=null && !admForm.getSubid_2().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_2());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_2());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_2());

				}
				i++;
			}
			else if(i==3){
				if(admForm.getSubid_3()!=null && !admForm.getSubid_3().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_3());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_3());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_3());

				}
				i++;
			}
			else if(i==4){
				if(admForm.getSubid_4()!=null && !admForm.getSubid_4().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_4());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_4());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_4());
				}
				i++;
			}
			else if(i==5){
				if(admForm.getSubid_5()!=null && !admForm.getSubid_5().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_5());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_5());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_5());
				}
				i++;
			}
			else if(i==6){
				if(admForm.getSubid_6()!=null && !admForm.getSubid_6().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_6());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_6());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_6());
				}
				i++;
			}
			else if(i==7){
				if(admForm.getSubid_7()!=null && !admForm.getSubid_7().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_7());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_7());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_7());
				}
				i++;
			}
			else if(i==8){
				if(admForm.getSubid_8()!=null && !admForm.getSubid_8().equalsIgnoreCase("")){
				admSubjectMarkForRankTO.setSubid(admForm.getSubid_8());
				admSubjectMarkForRankTO.setMaxmark(admForm.getMaxmark_8());
				admSubjectMarkForRankTO.setObtainedmark(admForm.getObtainedmark_8());
				}
				i++;
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
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("enter ssubmitDetailMarkConfirm page...");
		if(admForm.isOnlineApply()&& !admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);

			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initlateralEntryConfirmPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter initlateralEntryConfirmPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
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
			log.error("error in initlateralEntryConfirmPage...",e);
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
		log.info("exit initlateralEntryConfirmPage...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_LATERAL_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_LATERAL_PAGE);

			 */

		return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_LATERAL_PAGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitLateralEntryConfirm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitLateralEntryConfirm...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			 ActionErrors errors = admForm.validate(mapping, request);
			//Validate marks
			validateLateralMarks(admForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_LATERAL_PAGE);
				else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_LATERAL_PAGE);
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
			log.error("error in submitLateralEntryConfirm...",e);
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
		log.info("exit submitLateralEntryConfirm...");
		if(admForm.isOnlineApply()&& !admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
			 */

		return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTransferEntryConfirmPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter initTransferEntryConfirmPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
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
			log.error("error in initTransferEntryConfirmPage...",e);
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
		log.info("exit initTransferEntryConfirmPage...");
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_TRANSFER_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_TRANSFER_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_TRANSFER_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitTransferConfirmEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitTransferConfirmEntry...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			 ActionErrors errors = admForm.validate(mapping, request);
			//Validate marks
			validateTransferMarks(admForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				if(admForm.isOnlineApply())
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_TRANSFER_PAGE);
				else
					return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_TRANSFER_PAGE);
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
			log.error("error in submitTransferConfirmEntry...",e);
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
		log.info("exit submitTransferConfirmEntry...");
		if(admForm.isOnlineApply()&& !admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
		else if(admForm.isSinglePageAppln())
			return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
			 */
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardQualifyExamPrint(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.ADMISSIONFORM_QUALIFY_PRINT_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApplicationPrintWindow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		admForm.setApplicationNumber(null);
		admForm.setApplicationYear(null);
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INITSTUDENTPRINT_PAGE);
	}


	public ActionForward initMemoPrintWindow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		admForm.setApplicationNumber(null);
		admForm.setApplicationYear(null);
		admForm.setMemo(true);
		return mapping.findForward(CMSConstants.ADMISSIONFORM_INITSTUDENTPRINT_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardApplicationPrintWindow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AdmissionFormForm admForm = (AdmissionFormForm) form;
		//set caste show or not
		admForm.setCasteDisplay(CMSConstants.CASTE_ENABLED);
		try{

			ActionErrors errors = new ActionErrors();

			if(admForm.getApplicationNumber()==null|| StringUtils.isEmpty(admForm.getApplicationNumber()) || !StringUtils.isNumeric(admForm.getApplicationNumber()) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED));
				}
			}
			if(admForm.getApplicationYear()==null|| StringUtils.isEmpty(admForm.getApplicationYear())|| !StringUtils.isNumeric(admForm.getApplicationYear())){
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED));
				}
			}

			// if errors

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_STUDENTPRINT_NORECORD);
			}


			String applicationNumber = admForm.getApplicationNumber().trim();
			int applicationYear = Integer.parseInt(admForm.getApplicationYear());
			AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicationDetails(applicationNumber, applicationYear);

			if( applicantDetails == null){

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_NORECORDS);
				messages.add("messages", message);
				saveMessages(request, messages);

				return mapping.findForward(CMSConstants.ADMISSIONFORM_STUDENTPRINT_NORECORD);

			} else {
				if(applicantDetails.getId()>0)
				{
					String txnRefNo = AdmissionFormHandler.getInstance().getCandidatePGIDetails(applicantDetails.getId());
					if(txnRefNo!=null && !txnRefNo.isEmpty())
						admForm.setTxnRefNo(txnRefNo);
				}
				if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getDob()!=null )
					applicantDetails.getPersonalData().setDob(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getDob(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));
				if(applicantDetails.getChallanDate()!=null )
					applicantDetails.setChallanDate(CommonUtil.ConvertStringToDateFormat(applicantDetails.getChallanDate(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));
				//set applicant details
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
				// set flags
				if (programtypes!=null) {
					admForm.setEditProgramtypes(programtypes);
					Iterator<ProgramTypeTO> typeitr= programtypes.iterator();
					while (typeitr.hasNext()) {
						ProgramTypeTO typeTO = (ProgramTypeTO) typeitr.next();
						if(typeTO.getProgramTypeId()==admForm.getApplicantDetails().getCourse().getProgramTypeId()){
							if(typeTO.getPrograms()!=null){
								admForm.setEditprograms(typeTO.getPrograms());
								Iterator<ProgramTO> programitr= typeTO.getPrograms().iterator();
								while (programitr.hasNext()) {
									ProgramTO programTO = (ProgramTO) programitr
											.next();
									if(programTO.getId()== admForm.getApplicantDetails().getCourse().getProgramId()){
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
										}
									}
								}
							}	
						}
					}
				}


				checkExtradetailsDisplay(admForm);
				checkLateralTransferDisplay(admForm);
				// Admitted Through
				List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
				admForm.setAdmittedThroughList(admittedList);
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				//				if(admForm.isDisplayAdditionalInfo())
				//				{

				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);
					admForm.setOrganizationName(orgTO.getOrganizationName());
					admForm.setNeedApproval(orgTO.isNeedApproval());
				}
				//				}



				if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(applicantDetails.getPersonalData().getPassportValidity()) )
					applicantDetails.getPersonalData().setPassportValidity(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getPassportValidity(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));

				// set photo to session
				if(applicantDetails.getEditDocuments()!=null){
					Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
					while (docItr.hasNext()) {
						ApplnDocTO docTO = (ApplnDocTO) docItr.next();
						if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
						{
							admForm.setSubmitDate(docTO.getSubmitDate());
						}
						HttpSession session= request.getSession(false);
						if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo") && docTO.isDefaultPhoto()  && (docTO.getPhotoBytes() == null || docTO.getPhotoBytes().length <= 0))
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
									session.setAttribute(AdmissionFormAction.PHOTOBYTES,fileData );
							}
						}else if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){

							if(session!=null){
								session.setAttribute(AdmissionFormAction.PHOTOBYTES, docTO.getPhotoBytes());
							}
						}
					}
				}

				//get template and replace the data
				String template=AdmissionFormHandler.getInstance().getDeclarationTemplate(applicantDetails,request);
				admForm.setInstrTemplate(template);

				//get office instruction 
				String officetemplate=AdmissionFormHandler.getInstance().getOfficeInstructionTemplate(applicantDetails,request);
				admForm.setOfficeInstrTemplate(officetemplate);
				Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
				if(organisation!=null){
					StringBuffer orgAdd=new StringBuffer();
					orgAdd.append(organisation.getAddressLine1());
					if(organisation.getAddressLine2()!=null && !StringUtils.isEmpty(organisation.getAddressLine2())){
						orgAdd.append(",");
						orgAdd.append(organisation.getAddressLine2());
					}
					if(organisation.getAddressLine3()!=null && !StringUtils.isEmpty(organisation.getAddressLine3())){
						orgAdd.append(",");
						orgAdd.append(organisation.getAddressLine3());
					}

					admForm.setOrgAddress(orgAdd.toString());
					// set photo to session
					if(organisation.getLogoContentType()!=null){
						HttpSession session= request.getSession(false);
						if(session!=null){
							session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
						}
					}
				}
				//------------bar code creation
				if(!applicationNumber.trim().isEmpty()){
					String imgPath=request.getRealPath("");
					imgPath = imgPath + "//BarCode//"+ applicationNumber + ".jpeg";
					File barCodeImgFile =  new File(imgPath);
					if(barCodeImgFile.exists()){
						barCodeImgFile.delete();
					}
					Barcode code = BarcodeFactory.createCode128A(applicationNumber);
					code.setBarWidth(1);
					code.setBarHeight(40);
					code.setDrawingText(false);
					BarcodeImageHandler.saveJPEG(code, barCodeImgFile);
				}	
				if(admForm.isWorkExpNeeded()){
					String totalExp=getTotalYearsOfExperience(applicantDetails.getWorkExpList());
					admForm.setTotalYearOfExp(totalExp);
				}
			}
		}catch (Exception e){

			log.error("Error in  getApplicantDetails application form approval page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				throw e;
			}
		}
		if(admForm.getMemo()){

			return mapping.findForward(CMSConstants.ADMISSIONFORM_STUDENTMEMOPRINT_PAGE);
		}else{
			return mapping.findForward(CMSConstants.ADMISSIONFORM_STUDENTAPPLNPRINT_PAGE);	
		}
	}

	/*
	 * Single Form Start
	 */

	/**
	 * INIT STUDENT BIODATA ENTRY
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApplicantCreation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initApplicantCreation...");
		AdmissionFormForm stForm=(AdmissionFormForm)form;
		HttpSession session= request.getSession(false);
		stForm.setCasteDisplay(CMSConstants.CASTE_ENABLED);
		if(session!=null){
			cleanupSessionData(session);
			//		cleanupFormFromSession(session);
			cleanupEditSessionData(request);
		}
		stForm.setProgramId(null);
		stForm.setCourseId(null);
		stForm.setProgramTypeId(null);
		stForm.setApplicantDetails(null);
		stForm.setDetailMark(null);
		stForm.setDisplayMotherTongue(false);

		stForm.setDisplayLanguageKnown(false);

		stForm.setDisplayHeightWeight(false);

		stForm.setDisplayTrainingDetails(false);

		stForm.setDisplayAdditionalInfo(false);

		stForm.setDisplayExtracurricular(false);

		stForm.setDisplaySecondLanguage(false);

		stForm.setDisplayFamilyBackground(false);

		stForm.setDisplayTCDetails(false);

		stForm.setDisplayEntranceDetails(false);

		stForm.setDisplayLateralDetails(false);

		stForm.setDisplayTransferCourse(false);
		stForm.setSameTempAddr(false);
		stForm.setSinglePageAppln(true);
		stForm.setExamCenterRequired(false);
		stForm.setIsInterviewSelectionSchedule("false");
		stForm.setInterviewSelectionDate(null);
		stForm.setInterviewVenue(null);
		stForm.setInterviewTime(null);
		stForm.setAcceptAll(false);
		stForm.setTempChecked(false);

		log.info("exit initApplicantCreation...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_FIRST_PAGE);
		//		return mapping.findForward(CMSConstants.INIT_APPLICANT_SINGLE_PAGE);
	}


	/**
	 * INITIALIZES Application Single page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApplicantCreationDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter initApplicantCreationDetail...");
		AdmissionFormForm stForm=(AdmissionFormForm)form; 
		try{
			HttpSession session = request.getSession(false);
			AdmissionFormHandler handler=AdmissionFormHandler.getInstance();
			stForm.setIsInterviewSelectionSchedule("false");
			stForm.setInterviewSelectionDate(null);
			stForm.setInterviewVenue(null);
			stForm.setInterviewTime(null);
			stForm.setAcceptAll(false);
			stForm.setTempChecked(false);
			stForm.setViewextradetails(false);
			stForm.setClass12check(false);
			stForm.setClassdegcheck(false);

			//commented by vishnu for pimin
			//setDisplaySessionDetails(stForm, errors, request);
			AdmApplnTO applicantDetails = handler.getNewStudent(session,stForm);

			stForm.setCheckReligionId(CMSConstants.RELIGION_CHRISTIAN_TYPE);

			if(applicantDetails!=null){

				if(applicantDetails.getSelectedCourse()!=null){
					ProgramTO progTo=applicantDetails.getSelectedCourse().getProgramTo();
					if(progTo!=null){ 

						if(progTo.getProgramTypeTo()!=null){
							stForm.setPgmName(applicantDetails.getCourse().getProgramTo().getName());
							stForm.setPgmTypeName(applicantDetails.getCourse().getProgramTo().getProgramTypeTo().getProgramTypeName());
							stForm.setCourseName1(applicantDetails.getCourse().getName());

							stForm.setProgramId(String.valueOf(progTo.getId()));
							stForm.setAgeFromLimit(Integer.parseInt(progTo.getProgramTypeTo().getAgeFrom()));
							stForm.setAgeToLimit(Integer.parseInt(progTo.getProgramTypeTo().getAgeTo()));
						}
					}

					if(applicantDetails.getSelectedCourse().getIsDetailMarkPrint()!=null && "Yes".equalsIgnoreCase(applicantDetails.getSelectedCourse().getIsDetailMarkPrint()))
						stForm.setDetailMarksPrint(true);
					else
						stForm.setDetailMarksPrint(false);
				}
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

				if(stForm.getProgramTypeId().equals(fileName)){
					stForm.setViewextradetails(true);
				}


				Calendar cal2= Calendar.getInstance();
				cal2.setTime(new Date());
				int appliedyear=Integer.valueOf(cal2.get(cal2.YEAR));

				int appliedyear=Integer.parseInt(stForm.getApplicationYear());

				applicantDetails.setAppliedYear(appliedyear);
				String applnNOgenerated=handler.getGeneratedOnlineAppNo(stForm.getCourseId(),appliedyear,true);
				if(applnNOgenerated!=null && !StringUtils.isEmpty(applnNOgenerated) && StringUtils.isNumeric(applnNOgenerated) )
				{
					applicantDetails.setApplnNo(Integer.parseInt(applnNOgenerated));
					stForm.setApplicationNumber(applnNOgenerated);

				}else{
					// sends mail to admin to configure applnno.
					handler.sendMailToAdmin(stForm,appliedyear);
					ActionMessages message = new ActionMessages();
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL);
					message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL, error);
					saveErrors(request, message);
					if(stForm.isOnlineApply())
						return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE);
					else
						return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
				} 
				//get states list for edn qualification
				List<StateTO> ednstates=StateHandler.getInstance().getNativeStates(CMSConstants.KP_DEFAULT_COUNTRY_ID);
				stForm.setEdnStates(ednstates);

				//parish and dioceses

				List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
				stForm.setUgcourseList(ugcourseList);

				DioceseTransactionImpl txn=new DioceseTransactionImpl();
				List<DioceseTo> dioceseList=txn.getDiocesesList();
				stForm.setDioceseList(dioceseList);

				ParishTransactionImpl parishtxn=new ParishTransactionImpl();
				List<ParishTo> parishList=parishtxn.getParishes();
				stForm.setParishList(parishList);

				stForm.setApplicantDetails(applicantDetails);
				String workExpNeeded=applicantDetails.getCourse().getIsWorkExperienceRequired();
				if(stForm.getApplicantDetails().getCourse()!=null && "Yes".equalsIgnoreCase(workExpNeeded))
				{
					stForm.setWorkExpNeeded(true);
				}else{
					stForm.setWorkExpNeeded(false);
				}
				stForm.setWorkExpMandatory(applicantDetails.getCourse().isWorkExpMandatory());

				CourseTO applicantCourse = applicantDetails.getCourse();
				ProgramTO tempMainProgramTO =applicantCourse.getProgramTo();
				if(tempMainProgramTO!=null){
					applicantCourse.setProgramId(tempMainProgramTO.getId());
					if(tempMainProgramTO.getProgramTypeTo()!=null){
						applicantCourse.setProgramTypeId(tempMainProgramTO.getProgramTypeTo().getProgramTypeId());
					}
				}
				CourseTO selectedCourse=applicantDetails.getSelectedCourse();
				ProgramTO tempProgramTO =selectedCourse.getProgramTo();
				if(tempProgramTO!=null){
					selectedCourse.setProgramId(tempProgramTO.getId());
					if(tempProgramTO.getProgramTypeTo()!=null){
						selectedCourse.setProgramTypeId(tempProgramTO.getProgramTypeTo().getProgramTypeId());
					}
				}
				applicantDetails.setSelectedCourse(selectedCourse);
				applicantDetails.setCourse(applicantCourse);
				List<EntrancedetailsTO> entrnaceList=EntranceDetailsHandler.getInstance().getEntranceDeatilsByProgram(selectedCourse.getProgramId());
				stForm.setEntranceList(entrnaceList);
				setSelectedCourseAsPreference(stForm);
				Calendar cal= Calendar.getInstance();
				cal.setTime(new Date());

				applicantDetails.setHasWorkExp(false);
				applicantDetails.setCreatedBy(stForm.getUserId());
				applicantDetails.setCreatedDate(cal.getTime());

											ProgramTO programTO =selectedCourse.getProgramTo();
												if(programTO!=null){ 
													if(programTO.getIsMotherTongue()==true)
														stForm.setDisplayMotherTongue(true);
													if(programTO.getIsDisplayLanguageKnown()==true)
														stForm.setDisplayLanguageKnown(true);
													if(programTO.getIsHeightWeight()==true)
														stForm.setDisplayHeightWeight(true);
													if(programTO.getIsDisplayTrainingCourse()==true)
														stForm.setDisplayTrainingDetails(true);
													if(programTO.getIsAdditionalInfo()==true)
														stForm.setDisplayAdditionalInfo(true);
													if(programTO.getIsExtraDetails()==true)
														stForm.setDisplayExtracurricular(true);
													if(programTO.getIsSecondLanguage()==true)
														stForm.setDisplaySecondLanguage(true);
													if(programTO.getIsFamilyBackground()==true)
														stForm.setDisplayFamilyBackground(true);
													if(programTO.getIsTCDetails()==true)
														stForm.setDisplayTCDetails(true);
													if(programTO.getIsEntranceDetails()==true)
														stForm.setDisplayEntranceDetails(true);
													if(programTO.getIsLateralDetails()==true)
														stForm.setDisplayLateralDetails(true);
													if(programTO.getIsTransferCourse()==true)
														stForm.setDisplayTransferCourse(true);
													if(programTO.getIsExamCenterRequired()==true)
														stForm.setExamCenterRequired(true);													
												   }

												if(programTO.getAcademicYear()!=null && !programTO.getAcademicYear().isEmpty()){
													stForm.setProgramYear(programTO.getAcademicYear());
												}

					checkExtradetailsDisplay(stForm);
					checkLateralTransferDisplay(stForm);

				if(CountryHandler.getInstance().getCountries()!=null){
					//birthCountry & states
					List<CountryTO> birthCountries= CountryHandler.getInstance().getCountries();
					if (!birthCountries.isEmpty()) {
						stForm.setCountries(birthCountries);

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


				//Nationality
					AdmissionFormHandler formhandler = AdmissionFormHandler.getInstance();
					stForm.setNationalities(formhandler.getNationalities());
				// languages	
					LanguageHandler langHandler=LanguageHandler.getHandler();
					stForm.setMothertongues(langHandler.getLanguages());
					stForm.setLanguages(langHandler.getOriginalLanguages());

					if(stForm.isDisplayAdditionalInfo())
					{
						List<OrganizationTO> orgtos=orgHandler.getOrganizationDetails();
						if(orgtos!=null && !orgtos.isEmpty())
						{
							OrganizationTO orgTO=orgtos.get(0);
							stForm.setOrganizationName(orgTO.getOrganizationName());
							stForm.setNeedApproval(orgTO.isNeedApproval());
						}
					}

				// res. catg
					stForm.setResidentTypes(formhandler.getResidentTypes());	

					ReligionHandler religionhandler = ReligionHandler.getInstance();
					if(religionhandler.getReligion()!=null){
						List<ReligionTO> religionList=religionhandler.getReligion();
						stForm.setReligions(religionList);
					}


					//raghu subReligion Map

					Map<Integer, String> subReligionMap=CommonAjaxHandler.getInstance().getSubReligion();
					stForm.setSubReligionMap(subReligionMap);



				// caste category
				List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
				stForm.setCasteList(castelist);

				// Admitted Through
				List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
				stForm.setAdmittedThroughList(admittedList);
				// subject Group
				List<SubjectGroupTO> sujectgroupList=SubjectGroupHandler.getInstance().getSubjectGroupDetails(selectedCourse.getId());
				stForm.setSubGroupList(sujectgroupList);
				String[] subjectgroups=applicantDetails.getSubjectGroupIds();
				if (subjectgroups!=null && subjectgroups.length==0 && sujectgroupList!=null) {
					subjectgroups=new String[sujectgroupList.size()];
					applicantDetails.setSubjectGroupIds(subjectgroups);
				}

				//incomes
				List<IncomeTO> incomeList = AdmissionFormHandler.getInstance().getIncomes();
				stForm.setIncomeList(incomeList);

				//currencies
				List<CurrencyTO> currencyList = AdmissionFormHandler.getInstance().getCurrencies();
				stForm.setCurrencyList(currencyList);

				UniversityHandler unhandler = UniversityHandler.getInstance();
				List<UniversityTO> universities = unhandler.getUniversity();
				stForm.setUniversities(universities);

				OccupationTransactionHandler occhandler = OccupationTransactionHandler
				.getInstance();
				stForm.setOccupations(occhandler.getAllOccupation());

				List<ExamCenterTO> examCenterList = AdmissionFormHandler.getInstance().getExamCenters(selectedCourse.getProgramId());
				stForm.setExamCenters(examCenterList);

				//set applicantFeedbackList
				List<SingleFieldMasterTO> applicantFeedbackList=AdmissionFormHandler.getInstance().getApplicantFeedback();
				stForm.setApplicantFeedbackList(applicantFeedbackList);

				// set photo to session
				if(applicantDetails.getEditDocuments()!=null){
					Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
					while (docItr.hasNext()) {
						ApplnDocTO docTO = (ApplnDocTO) docItr.next();
						if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
						{
							stForm.setSubmitDate(docTO.getSubmitDate());
							if(docTO.getDocName().equalsIgnoreCase("Class 12")){
								stForm.setClass12check(true);
							}
                        if(docTO.getDocName().equalsIgnoreCase("DEG")){
                        	stForm.setClassdegcheck(true);
							}
						}
					}
				}

				//this is for work experience mandatory checking 

				AdmissionFormHandler.getInstance().checkWorkExperianceMandatory(stForm);

				// preferences

//					List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
//					CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
//					firstTo.setCoursId(String.valueOf(selectedCourse.getId()));
//					firstTo.setPrefNo(1);
//					prefTos.add(firstTo);
//					
//					stForm.setPreferenceList(prefTos);



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
							firstTo.setCoursId(String.valueOf(applicantCourse.getId()));
							firstTo.setCoursName(applicantCourse.getName());
							firstTo.setProgId(String.valueOf(applicantCourse.getProgramId()));
							firstTo.setProgramtypeId(String.valueOf(applicantCourse.getProgramTypeId()));
							firstTo.setPrefNo(1);
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							preferences=firstTo.getPrefcourses();
							if(firstTo.getPrefcourses().size() > 1){
								firstTo.setIsMandatory(true);
							}
							prefTos.add(firstTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
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
							formhandler.populatePreferenceTO(secTo,applicantCourse);
							preferences=secTo.getPrefcourses();
							if(secTo.getPrefcourses().size() > 1){
								secTo.setIsMandatory(true);
							}
							prefTos.add(secTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
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
							formhandler.populatePreferenceTO(thirdTo,applicantCourse);
							preferences=thirdTo.getPrefcourses();
							if(thirdTo.getPrefcourses().size() > 1){
								thirdTo.setIsMandatory(true);
							}
							prefTos.add(thirdTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							firstTo.setPrefNo(3);
							preferences=firstTo.getPrefcourses();
							if(firstTo.getPrefcourses().size() > 1){
								firstTo.setIsMandatory(true);
							}
							prefTos.add(firstTo);
						}
						stForm.setPreferenceList(prefTos);
					}else{
						List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
						CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(firstTo,applicantCourse);
						preferences=firstTo.getPrefcourses();
						firstTo.setPrefNo(1);
						if(firstTo.getPrefcourses().size() > 1){
							firstTo.setIsMandatory(true);
						}
						prefTos.add(firstTo);
						CandidatePreferenceTO secTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(secTo,applicantCourse);
						secTo.setPrefNo(2);
						if(secTo.getPrefcourses().size() > 1){
							secTo.setIsMandatory(true);
						}
						prefTos.add(secTo);
						CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(thirdTo,applicantCourse);
						thirdTo.setPrefNo(3);
						if(thirdTo.getPrefcourses().size() > 1){
							thirdTo.setIsMandatory(true);
						}
						prefTos.add(thirdTo);
						stForm.setPreferenceList(prefTos);
					}

					//for ajax setting put preference lists in session

					if(session!=null){
						session.setAttribute(CMSConstants.COURSE_PREFERENCES, preferences);
					}

					stForm.setApplicantDetails(applicantDetails);

			}else{
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
					message = new ActionMessage("knowledgepro.admission.NoCourseSelected");
					messages.add("messages", message);
					saveMessages(request, messages);


					return mapping.findForward(CMSConstants.INIT_APPLICANT_SINGLE_PAGE);
			}
			ExamGenHandler genHandler = new ExamGenHandler();
			HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
			stForm.setSecondLanguageList(secondLanguage);
		}catch(Exception e){
			log.error("Error in  initApplicantCreationDetail...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				stForm.setErrorMessage(msg);
				stForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else if(e instanceof SelectionDateNotAvailableException ){
					//handler.sendMailToAdmin(stForm,appliedyear);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage("Contact Administrator");
					messages.add("messages", message);
					saveMessages(request, messages);
					if(stForm.isOnlineApply())
						return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE);
					else
						return mapping.findForward(CMSConstants.ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
			}
			else {
				throw e;
			}	
		}
		log.info("exit initApplicantCreationDetail...");


			 */


		return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
	}


	/**
	 * sets selected course as default preference
	 * @param stForm
	 */
	private void setSelectedCourseAsPreference(AdmissionFormForm stForm) {
		PreferenceTO to= new PreferenceTO();
		if(stForm.getApplicantDetails()!=null && stForm.getApplicantDetails().getSelectedCourse()!=null){

			to.setFirstPrefCourseId(String.valueOf(stForm.getApplicantDetails().getSelectedCourse().getId()));
		}
		stForm.getApplicantDetails().setPreference(to);

	}


	/**
	 * creates a applicant record
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitApplicantCreation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitApplicantCreation...");
		AdmissionFormForm stForm=(AdmissionFormForm)form;

		try{
			stForm.setTempChecked(stForm.isAcceptAll());
			if(stForm.isDataSaved()){
				ActionMessages messages = new ActionMessages(); 
				ActionMessage message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_SUCCESS_STATUS,stForm.getApplicantDetails().getApplnNo(),stForm.getApplicantDetails().getPersonalData().getDob());
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.APPLICANT_SINGLE_CONFIRM_PAGE);
			}			
			setConfirmationPageDetails(stForm, request);
			ActionMessages errors=stForm.validate(mapping, request);
			if(stForm.getApplicantDetails().getApplnNo()==0){

				if (errors.get(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED) != null&& !errors.get(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED,new ActionError(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED));
				}
			}
			if(stForm.isExamCenterRequired() &&	stForm.getApplicantDetails().getExamCenterId() == 0){
				if (errors.get("knowledgepro.admission.appln.exam.center.required") != null && !errors.get("knowledgepro.admission.appln.exam.center.required").hasNext()) {
					errors.add("knowledgepro.admission.appln.exam.center.required",new ActionError("knowledgepro.admission.appln.exam.center.required"));
				}
			}

			//added by vishnu
			if((stForm.getApplicantDetails().getPersonalData().getGuardianMob1()!=null
					&& !stForm.getApplicantDetails().getPersonalData().getGuardianMob1().isEmpty()
					&& stForm.getApplicantDetails().getPersonalData().getGuardianMob2()!=null &&
					!stForm.getApplicantDetails().getPersonalData().getGuardianMob2().isEmpty()) || 

			( stForm.getApplicantDetails().getPersonalData().getParentMob1()!=null && 
					!stForm.getApplicantDetails().getPersonalData().getParentMob1().isEmpty() &&
					stForm.getApplicantDetails().getPersonalData().getParentMob2()!=null &&
					!stForm.getApplicantDetails().getPersonalData().getParentMob2().isEmpty()))
			{

			}
			else
			{
				errors.add(CMSConstants.ADMISSIONFORM_PARENTGUARDIAN_CONTACT_NUMBER_REQUIRED,new ActionError(CMSConstants.ADMISSIONFORM_PARENTGUARDIAN_CONTACT_NUMBER_REQUIRED));
			}


			if(stForm.isDisplaySecondLanguage() && (stForm.getApplicantDetails().getPersonalData().getSecondLanguage() == null || stForm.getApplicantDetails().getPersonalData().getSecondLanguage().trim().isEmpty())){
				if (errors.get("knowledgepro.admin._Exam_Second_Language_Master.required") != null&& !errors.get("knowledgepro.admin._Exam_Second_Language_Master.required").hasNext()) {
					errors.add("knowledgepro.admin._Exam_Second_Language_Master.required",new ActionError("knowledgepro.admin._Exam_Second_Language_Master.required"));
				}	
			}
		if(stForm.getIsInterviewSelectionSchedule()!=null && stForm.getIsInterviewSelectionSchedule().equalsIgnoreCase("true")){
			if(stForm.getInterviewSelectionDate()== null || stForm.getInterviewSelectionDate().isEmpty()){
				if (errors.get("knowledgepro.admission.appln.interview.date.required") != null && !errors.get("knowledgepro.admission.appln.interview.date.required").hasNext()) {
					errors.add("knowledgepro.admission.appln.interview.date.required",new ActionError("knowledgepro.admission.appln.interview.date.required"));
					}
				}	
				if(stForm.getInterviewVenue()==null || stForm.getInterviewVenue().isEmpty()){
					if (errors.get("knowledgepro.admission.appln.interview.venue.required") != null && !errors.get("knowledgepro.admission.appln.interview.venue.required").hasNext()) {
						errors.add("knowledgepro.admission.appln.interview.venue.required",new ActionError("knowledgepro.admission.appln.interview.venue.required"));
				}
			}
		}

			if(stForm.isSameTempAddr()){
				copyCurrToPermAddress(stForm);
			}

			validateOnlineConfirmRequireds(stForm, errors);

			if(stForm.getApplicantDetails().getTitleOfFather()==null || stForm.getApplicantDetails().getTitleOfFather().isEmpty()){
				errors.add("knowledgepro.admin.titleOfFather.required",new ActionError("knowledgepro.admin.titleOfFather.required"));
			}
			if(stForm.getApplicantDetails().getTitleOfMother()==null || stForm.getApplicantDetails().getTitleOfMother().isEmpty()){
				errors.add("knowledgepro.admin.titleOfMother.required",new ActionError("knowledgepro.admin.titleOfMother.required"));

			}
			validateParentConfirmOnlineRequireds(stForm, errors);

			//email comparision
			if(stForm.getApplicantDetails().getPersonalData()!=null && stForm.getApplicantDetails().getPersonalData().getEmail()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getEmail())){
				if(stForm.getApplicantDetails().getPersonalData().getConfirmEmail()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
						if(!stForm.getApplicantDetails().getPersonalData().getEmail().equals(stForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
							if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
								errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
							}
						}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
					}
				}
			}else if(stForm.getApplicantDetails().getPersonalData().getConfirmEmail()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
				if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
				}
			}

			if(stForm.getApplicantDetails().getPersonalData().getMobileNo1()==null || stForm.getApplicantDetails().getPersonalData().getMobileNo1().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Mobile Country Code"));
			}
			if(stForm.getApplicantDetails().getPersonalData().getMobileNo2()==null || stForm.getApplicantDetails().getPersonalData().getMobileNo2().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Mobile No "));
			}

			// online age range check

			if(stForm.getAgeToLimit()!=0 && stForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getDob())){
				boolean valid=validateOnlineDOB(stForm.getAgeFromLimit(),stForm.getAgeToLimit(),stForm.getApplicantDetails().getPersonalData().getDob());
				if(!valid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS, new ActionError(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS));
					}
				}
		}
			//ra
			if(stForm.getApplicantDetails().getPersonalData()!=null && stForm.getApplicantDetails().getPersonalData().getSubReligionId()!=null && StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getSubReligionId()) ){
				if (errors.get(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED)!=null ) {
					errors.add(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_RELIGIONSECTION_REQUIRED));
				}
		}
			//validateEditPhone(stForm, errors);
			validateEditParentPhone(stForm, errors);
			validateEditGuardianPhone(stForm, errors);
			validateEditPassportIfNRI(stForm, errors);
			validateEditOtherOptions(stForm, errors);

			validateEditCommAddress(stForm, errors);
			validatePermAddress(stForm, errors);
//			validateSubjectGroups(stForm, errors);
			if(stForm.isDisplayTCDetails())
			validateTcDetailsEdit(stForm, errors);
			if(stForm.isDisplayEntranceDetails())
			validateEntanceDetailsEdit(stForm, errors);
			if (stForm.getApplicantDetails().getChallanDate()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getChallanDate())) {
				if(CommonUtil.isValidDate(stForm.getApplicantDetails().getChallanDate())){
				boolean	isValid = validatefutureDate(stForm.getApplicantDetails().getChallanDate());
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
			if (stForm.getApplicantDetails().getAdmissionDate()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getAdmissionDate())) {
				if(CommonUtil.isValidDate(stForm.getApplicantDetails().getAdmissionDate())){
				boolean	isValid = validatefutureDate(stForm.getApplicantDetails().getAdmissionDate());
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


			if (stForm.getApplicantDetails().getPersonalData()!=null && stForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getDob())) {
				if(CommonUtil.isValidDate(stForm.getApplicantDetails().getPersonalData().getDob())){
				boolean	isValid = validatefutureDate(stForm.getApplicantDetails().getPersonalData().getDob());
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
			if(stForm.getApplicantDetails().getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getPassportValidity())){

				if(CommonUtil.isValidDate(stForm.getApplicantDetails().getPersonalData().getPassportValidity())){
					boolean isValid=validatePastDate(stForm.getApplicantDetails().getPersonalData().getPassportValidity());
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
			if(stForm.getApplicantDetails().getPersonalData().getResidentPermitDate()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getResidentPermitDate())&& !CommonUtil.isValidDate(stForm.getApplicantDetails().getPersonalData().getResidentPermitDate())){

				if (errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID,new ActionError(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID));
				}
			}

			if (stForm.getSubmitDate()!=null && !StringUtils.isEmpty(stForm.getSubmitDate())) {
				if(CommonUtil.isValidDate(stForm.getSubmitDate())){
				boolean	isValid = validatePastDate(stForm.getSubmitDate());
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
			if(stForm.getApplicantDetails().getHasWorkExp()==null){
				errors.add("knowledgepro.admission.online.workExp.yes.no.reqd", new ActionError("knowledgepro.admission.online.workExp.yes.no.reqd"));
			}

			List<ApplicantWorkExperienceTO> expList=stForm.getApplicantDetails().getWorkExpList();
			if(expList!=null){
				Iterator<ApplicantWorkExperienceTO> toItr=expList.iterator();
				int count=0;
				while (toItr.hasNext()) {
					ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) toItr	.next();
					validateWorkExperience(expTO, errors);
					if(stForm.isWorkExpMandatory() || stForm.getApplicantDetails().getHasWorkExp()){
						if(expTO.getFromDate()!=null && !StringUtils.isEmpty(expTO.getFromDate()) && CommonUtil.isValidDate(expTO.getFromDate()) &&
								expTO.getToDate()!=null && !StringUtils.isEmpty(expTO.getToDate()) && CommonUtil.isValidDate(expTO.getToDate()) &&
								expTO.getOrganization()!=null && !StringUtils.isEmpty(expTO.getOrganization())){
							count++;
						}
					}
				}
				if((stForm.isWorkExpMandatory() || stForm.getApplicantDetails().getHasWorkExp())  && count==0){
					errors.add(CMSConstants.ERROR,new ActionError("errors.required","Work Experience"));
				}
			}
			validateEditEducationDetails(errors, stForm);
			//validateEditDocumentSize(stForm, errors);
			validateEditDocumentSizeOnline(stForm, errors,request); //semester marks card required msg is coming. could not replicate the problem. so created another method without that validation

			//validate candidate pre-requisite details if exists
			if(stForm.getPreRequisiteExists()){
				validatePreRequisteForFinalSubmit(errors,stForm);
			}


			if(stForm.getApplicantDetails().getPersonalData().getTrainingDuration()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getTrainingDuration()) && !StringUtils.isNumeric(stForm.getApplicantDetails().getPersonalData().getTrainingDuration())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DURATION_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_DURATION_INVALID));
				}
			}

			// validate height and weight
			if(stForm.getApplicantDetails().getPersonalData().getHeight()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getHeight()) && !StringUtils.isNumeric(stForm.getApplicantDetails().getPersonalData().getHeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID));
				}
			}

			if(stForm.getApplicantDetails().getPersonalData().getWeight()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getWeight()) && !CommonUtil.isValidDecimal(stForm.getApplicantDetails().getPersonalData().getWeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID));
				}
			}
			if(Integer.parseInt(stForm.getProgramTypeId())==2){
			if((stForm.getApplicantDetails().getPersonalData().getUgcourse()!=null && StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getUgcourse())) || (!StringUtils.isNumeric(stForm.getApplicantDetails().getPersonalData().getUgcourse()))){
				errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Ug Course "));
			}
			}
			int prefLength = 0;
			int selectedLen = 0;
			List<Integer> notSelectedPref = new ArrayList<Integer>();
			List<CandidatePreferenceTO> prfList = stForm.getPreferenceList();
			int count = 0;
			if(prfList!= null && prfList.size() > 0){
				Iterator<CandidatePreferenceTO> prItr =  prfList.iterator();
				while (prItr.hasNext()) {
					CandidatePreferenceTO candidatePreferenceTO = (CandidatePreferenceTO) prItr
							.next();
					if(candidatePreferenceTO.getPrefcourses()!= null && candidatePreferenceTO.getPrefcourses().size() > 0){
						prefLength = candidatePreferenceTO.getPrefcourses().size();

						if(candidatePreferenceTO.getCoursId()!= null && !candidatePreferenceTO.getCoursId().trim().isEmpty() && candidatePreferenceTO.getPrefNo() > 1){
							selectedLen++;
						}
						else if (candidatePreferenceTO.getPrefNo() > 1 && count <= prefLength){
							notSelectedPref.add(candidatePreferenceTO.getPrefNo());
						}
					}
					count++;

				}
			}
			if(prefLength > selectedLen){
				Iterator<Integer> strItr  = notSelectedPref.iterator();
				while (strItr.hasNext()) {
					Integer prefNo = (Integer) strItr.next();
					errors.add("knowledgepro.admission.online.apply.pref.required",new ActionError("knowledgepro.admission.online.apply.pref.required", prefNo));				
				}
			}
			//check for applicant feedabck -smitha
			if(stForm.getApplicantDetails().getApplicantFeedbackId()==null || stForm.getApplicantDetails().getApplicantFeedbackId().trim().isEmpty()){
				errors.add("knowledgepro.admission.online.apply.applicantFeedback.required",new ActionError("knowledgepro.admission.online.apply.applicantFeedback.required"));
			}
			if(stForm.getInterviewSelectionDate()!=null && !stForm.getInterviewSelectionDate().isEmpty()){
				Map<Integer, String> interviewSelectionScheduleMap=CommonAjaxHandler.getInstance().getDatesBySelectionVenueOnline(stForm.getInterviewVenue(), stForm.getProgramId(),stForm.getProgramYear());
				stForm.setInterviewSelectionSchedule(interviewSelectionScheduleMap);
				//stForm.setInterviewSelectionD)
			}
			if (errors != null && !errors.isEmpty()) {
				resetHardCopySubmit(stForm.getApplicantDetails());
				if(stForm.isReviewWarned()){
					setDocumentForView(stForm, request);	
				}
				saveErrors(request, errors);
				stForm.setReviewWarned(false);
				stForm.setReviewed("false");
				if (stForm.isRemove()) {
					removePhotoDoc(stForm, request);
				}
				return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);

			}


			AdmApplnTO applicantDetail=stForm.getApplicantDetails();

			Calendar cal= Calendar.getInstance();
			cal.setTime(new Date());
			int year=cal.get(cal.YEAR);

			int year=Integer.parseInt(stForm.getApplicationYear());

			//set current year
			if(applicantDetail!=null){
				applicantDetail.setAppliedYear(year);
				//raghu
				//applicantDetail.setMode("Online");
			}
			AdmissionFormHandler admHandler = AdmissionFormHandler.getInstance();
			if(!stForm.isReviewWarned())
			{
				stForm.setReviewWarned(true);
				stForm.setReviewed("true");
				resetHardCopySubmit(applicantDetail);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.APPLICATION_REVIEW_WARN);
				messages.add("messages", message);
				saveMessages(request, messages);
				setDocumentForView(stForm, request);
				return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
			}

			boolean updated=admHandler.createApplicant(applicantDetail,stForm,false);

			if(updated){
				admHandler.sendMailToStudentSinglePage(stForm);
				if(stForm.getApplicantDetails().getSelectedCourse().isApplicationProcessSms()){
					admHandler.sendSMSToStudent(stForm);
				}
				try{
					if(stForm.getStudentId() !=0 && request.getSession().getAttribute("PhotoBytes") != null){
						FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_FOLDER_PATH+stForm.getStudentId()+".jpg");
						fos.write((byte[])request.getSession().getAttribute("PhotoBytes"));
						fos.close();
					}
				}catch (Exception f) {
					f.printStackTrace();
				}
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_SUCCESS_STATUS,stForm.getApplicantDetails().getApplnNo(),applicantDetail.getPersonalData().getDob());
				messages.add("messages", message);
				saveMessages(request, messages);
				stForm.setIsPresidance(false);
			}


		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			stForm.setErrorMessage(msg);
			stForm.setErrorStack(e.getMessage());
			//ra
			System.out.println(e+"              ######################  ERROR IN ONLINE APPLICATION FORM        "+e.getMessage()+"#####################################");
			return mapping.findForward(CMSConstants.NEW_ERROR_PAGE);
		}
		log.info("exit submitStudentCreation...");
//		return mapping.findForward(CMSConstants.SINGLE_PAGE_APP_VERIFY);


			 */
		return mapping.findForward(CMSConstants.APPLICANT_SINGLE_CONFIRM_PAGE);	
	}


	/**
	 * @param errors
	 * @param stForm
	 * @throws Exception
	 */
	private void validatePreRequisteForFinalSubmit(ActionMessages messages, AdmissionFormForm stForm) throws Exception{/*

		if(stForm.getApplicantDetails().getPreRequisiteObtMarks()==null || stForm.getApplicantDetails().getPreRequisiteObtMarks().isEmpty()){
			ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED);
			messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED, error);
		}

		if(stForm.getApplicantDetails().getPreRequisiteExamMonth()==null || stForm.getApplicantDetails().getPreRequisiteExamMonth().isEmpty() || stForm.getApplicantDetails().getPreRequisiteExamMonth().equalsIgnoreCase("0")){
			ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_REQUIRED);
			messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_REQUIRED, error);
		}
		if(stForm.getApplicantDetails().getPreRequisiteExamYear()==null || StringUtils.isEmpty(stForm.getApplicantDetails().getPreRequisiteExamYear())){
			if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED);
				messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED, error);
			}
		}
		if(stForm.getApplicantDetails().getPreRequisiteRollNo()==null || StringUtils.isEmpty(stForm.getApplicantDetails().getPreRequisiteRollNo())){
			if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED);
				messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED, error);
			}
		}

		if(stForm.getApplicantDetails().getPreRequisiteExamYear()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPreRequisiteExamYear()) 
				&& StringUtils.isNumeric(stForm.getApplicantDetails().getPreRequisiteExamYear())){
			if(CommonUtil.isFutureYear(Integer.parseInt(stForm.getApplicantDetails().getPreRequisiteExamYear()))){
				if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE);
					messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE, error);
				}
			}
		}

		if(stForm.getApplicantDetails().getPreRequisiteExamMonth()!=null && !stForm.getApplicantDetails().getPreRequisiteExamMonth().isEmpty()  && stForm.getApplicantDetails().getPreRequisiteExamYear()!=null && !stForm.getApplicantDetails().getPreRequisiteExamYear().isEmpty()){
			if(CommonUtil.isFutureOrCurrentYear(Integer.parseInt(stForm.getApplicantDetails().getPreRequisiteExamYear()))){
				if(CommonUtil.isFutureMonth(Integer.parseInt(stForm.getApplicantDetails().getPreRequisiteExamMonth())-1)){
					if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE);
						messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE, error);
					}
				}
			}
		}
		//check for eligibility
		if(stForm.getApplicantDetails().getPreRequisiteObtMarks()!=null){
			boolean eligible=false;
			Iterator<CoursePrerequisiteTO> reqItr=stForm.getEligPrerequisites().iterator();
			while (reqItr.hasNext()) {
				CoursePrerequisiteTO reqTO = (CoursePrerequisiteTO) reqItr.next();
				double percentage=0.0;
				if(reqTO.getTotalMark()!=0.0)
					percentage=(Double.parseDouble(stForm.getApplicantDetails().getPreRequisiteObtMarks())/reqTO.getTotalMark())*100;
				reqTO.setUserPercentage(percentage);
				if(reqTO.getPercentage()!=0.0)
				{
					if(reqTO.getUserPercentage()>=reqTO.getPercentage())
						{
							eligible=true;
						}
				}else
				{
					if(reqTO.getUserPercentage()>reqTO.getPercentage())
					{

						eligible=true;
					}
				}

				// total mark is less than entered marks check	
				if(Double.parseDouble(stForm.getApplicantDetails().getPreRequisiteObtMarks())>reqTO.getTotalMark()){
					if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER);
						messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER, error);
					}
				}
				break;
			}
			if(!eligible){
				if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID);
				messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID, error);
				}
			}

		}

	 */}
	/**
	 * COPIES CURENT ADDRESS TO PERMANENT ADDRESS
	 * @param stForm
	 */
	private void copyCurrToPermAddress(AdmissionFormForm stForm) {/*
		stForm.getApplicantDetails().getPersonalData().setPermanentAddressLine1(stForm.getApplicantDetails().getPersonalData().getCurrentAddressLine1());
		stForm.getApplicantDetails().getPersonalData().setPermanentAddressLine2(stForm.getApplicantDetails().getPersonalData().getCurrentAddressLine2());
		stForm.getApplicantDetails().getPersonalData().setPermanentCityName(stForm.getApplicantDetails().getPersonalData().getCurrentCityName());
		stForm.getApplicantDetails().getPersonalData().setPermanentCountryId(stForm.getApplicantDetails().getPersonalData().getCurrentCountryId());
		if(stForm.getApplicantDetails().getPersonalData().getCurrentStateId()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getCurrentStateId().trim()))
		stForm.getApplicantDetails().getPersonalData().setPermanentStateId(stForm.getApplicantDetails().getPersonalData().getCurrentStateId());
		if(stForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()))
		stForm.getApplicantDetails().getPersonalData().setPermanentAddressStateOthers(stForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers());
		stForm.getApplicantDetails().getPersonalData().setPermanentAddressZipCode(stForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode());
		//raghu
		if(stForm.getApplicantDetails().getPersonalData().getCurrentDistricId()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getCurrentDistricId().trim()))
			stForm.getApplicantDetails().getPersonalData().setPermanentDistricId(stForm.getApplicantDetails().getPersonalData().getCurrentDistricId());

		if(stForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers()))
			stForm.getApplicantDetails().getPersonalData().setPermanentAddressDistrictOthers(stForm.getApplicantDetails().getPersonalData().getCurrentAddressDistrictOthers());

	 */}
	/**
	 * permanent address validation
	 * @param stForm
	 * @param errors
	 */
	private void validatePermAddress(AdmissionFormForm admForm,
			ActionMessages errors) {/*
		log.info("enter validatePermAddress..");
		if(errors==null)
			errors= new ActionMessages();

			if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()))
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED,error);
				}
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


			log.info("exit validatePermAddress..");
			 */}
	/**
	 * 
	 * @param stForm
	 * @param request
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void setConfirmationPageDetails(AdmissionFormForm stForm, HttpServletRequest request) throws FileNotFoundException, IOException{
		HttpSession session=request.getSession(false);
		if(session!= null && session.getAttribute(AdmissionFormAction.PHOTOBYTES)!=null)
			session.removeAttribute(AdmissionFormAction.PHOTOBYTES);

		if(session!= null && session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS);


		if(stForm.getApplicantDetails().getEditDocuments()!= null){
			Iterator<ApplnDocTO> itr =stForm.getApplicantDetails().getEditDocuments().iterator();
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
									stForm.setRemove(false);
									session.setAttribute(AdmissionFormAction.PHOTOBYTES, applnDocTO.getEditDocument().getFileData());
								}
							}
							else{
								if(session!=null){
									stForm.setRemove(false);
									session.setAttribute(AdmissionFormAction.PHOTOBYTES, applnDocTO.getCurrDocument());
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
	 * @param stForm
	 * @param request
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void setDocumentForView(AdmissionFormForm stForm, HttpServletRequest request) throws FileNotFoundException, IOException{
		HttpSession session=request.getSession(false);
		if(stForm.getApplicantDetails().getEditDocuments()!= null){
			Iterator<ApplnDocTO> itr =stForm.getApplicantDetails().getEditDocuments().iterator();
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

	/*
	 * Single Form End
	 */
	/**
	 * validate programtype,course and program
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateProgramType(ActionMessages errors,
			AdmissionFormForm admForm) {
		log.info("enter validate validateProgramType..");
		if(admForm.getProgramTypeId() ==null || admForm.getProgramTypeId().length()==0)
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
		log.info("exit validate validateProgramType...");
		return errors;
	}
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPhoneForPUC(AdmissionFormForm admForm, ActionMessages errors) {/*
		log.info("enter validateEditPhone..");
		if(errors==null)
			errors= new ActionMessages();

				if((admForm.getApplicantDetails().getPersonalData().getPhNo1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1()))
						&& (admForm.getApplicantDetails().getPersonalData().getPhNo2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2()))
						&& (admForm.getApplicantDetails().getPersonalData().getPhNo3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3())))
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED, error);
					}
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
	 */}
	/**
	 * This is used to diplay fields in UI when u click on cancel admission in left menu.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getApplicationDetailsForCancel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("entered cancelApplicantDetails..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
			ActionMessages messages=new ActionMessages();
			ActionErrors errors = admForm.validate(mapping, request);
			validateAppRegRollNumber(admForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				admForm.setRemoveRegNo(null);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_CANCELLATION);
			}
			//calling handler method by passing values.
			AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicantDetailsForCancel(admForm);
			if(applicantDetails==null){
				ActionMessage message = new ActionMessage(
						CMSConstants.APPLICATION_REGISTRATION_ROLL_NUMBER_NOTEXIST);
				messages.add("messages", message);
				errors.add(messages);
				saveErrors(request, messages);
				admForm.setRemoveRegNo(null);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_CANCELLATION);
			}
			admForm.setApplicantDetails(applicantDetails);
		}catch (Exception e) {
			log.error("Error in  cancelApplicantDetails application form ...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("exit cancelApplicantDetails..");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CANCELLATION_NEW);

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
		/*if(fileData!=null && fileName != null && !StringUtils.isEmpty(fileName) && contentType!=null && !StringUtils.isEmpty(contentType) ){

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
		 */
		return remove;
	}

	public ActionForward sessionExpired(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		log.info("entering into sessionExpired method");


		log.info("exiting forwardDoclist method");
		return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);
	}
	/**
	 * 
	 * @param stForm
	 * @param request
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void removePhotoDoc(AdmissionFormForm stForm, HttpServletRequest request) throws FileNotFoundException, IOException{/*
		HttpSession session=request.getSession(false);
		if(session!= null && session.getAttribute(AdmissionFormAction.PHOTOBYTES)!=null)
			session.removeAttribute(AdmissionFormAction.PHOTOBYTES);

		if(session!= null && session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS);

		List<ApplnDocTO> applnDocTOs=stForm.getApplicantDetails().getEditDocuments();
		if(applnDocTOs!= null){
			Iterator<ApplnDocTO> itr =applnDocTOs.iterator();
			ApplnDocTO applnDocTO;

			while(itr.hasNext()){
				applnDocTO= itr.next();
				if(applnDocTO.isPhoto()){
					applnDocTO.setEditDocument(null);
					applnDocTO.setCurrDocument(null);
				}
			}
			stForm.getApplicantDetails().setEditDocuments(applnDocTOs);
		}
	 */}		
	/**
	 * Validate document size
	 * @param admForm
	 * @param errors
	 */
	private void validateEditDocumentSizeOnline(AdmissionFormForm admForm,
			ActionMessages errors,HttpServletRequest request) throws Exception {/*
		log.info("enter validate dcument size...");
		List<ApplnDocTO> uploadlist=admForm.getApplicantDetails().getEditDocuments();
		InputStream propStream=AdmissionFormAction.class.getResourceAsStream("/resources/application.properties");
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
						docTo.setEditDocument(null);
						docTo.setName(docTo.getPrintName());
					}


					//raghu
					if(docTo.getCurrDocument()==null ){
						if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD);
							errors.add(CMSConstants.ADMISSIONFORM_PHOTO_UPLOAD,error);
						}
						//docTo.setEditDocument(null);
						//docTo.setName(docTo.getPrintName());
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
			}

		}

			 */}
	public ActionForward initCSVUploadForCJC(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initCSVUpload...");
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		admForm.setCsvFile(null);
		if(request.getParameter("Cjc")!=null && request.getParameter("Cjc").equalsIgnoreCase("true"))
			admForm.setCjc(true);
		else admForm.setCjc(false);
		return mapping.findForward(CMSConstants.ADMISSIONFORM_UPLOADCJCDATA);
	}
	@SuppressWarnings("unchecked")
	public ActionForward updateCSVForCJC(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter updateCSV...");
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		FormFile csvfile=admForm.getCsvFile();
		ActionMessages errors=new ActionMessages();
		ActionErrors errorr=new ActionErrors();
		setUserId(request, admForm);
		try{
			if(errors==null)
				errors=new ActionMessages();
			//validate the csv type
			if(csvfile!=null){
				if(admForm.getYear()==null || admForm.getYear().isEmpty()){
					ActionMessage error= new ActionMessage(CMSConstants.FEE_ACADEMICYEAR_REQUIRED);
					errors.add(CMSConstants.FEE_ACADEMICYEAR_REQUIRED, error);
				}
				if(csvfile.getFileName()!=null && !StringUtils.isEmpty(csvfile.getFileName())){
					String extn="";
					int indx=csvfile.getFileName().lastIndexOf(".");
					if(indx!=-1)
						extn=csvfile.getFileName().substring(indx, csvfile.getFileName().length());
					if(!extn.equalsIgnoreCase(".CSV")){

						if(errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR).hasNext()){
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR);
							errors.add(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR, error);
						}
					}
				}else{
					if(errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED).hasNext()){
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OMR_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_OMR_REQUIRED, error);
					}
				}
			}

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_UPLOADCJCDATA);
			}	
			Map<String,Integer> courseMap=AdmissionFormHandler.getInstance().getCourses();
			List<AdmAppln> applications=null;
			if(csvfile!=null){
				applications=CSVUpdater.parseOMRData(csvfile.getInputStream(),admForm,courseMap);
			}
			if(applications!=null && !applications.isEmpty())
				CSVUpdater.persistCompleteApplicationData(applications,admForm);
			else{
				errorr.add("error", new ActionError("knowledgepro.admission.norecords"));
				addErrors(request, errorr);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_UPLOADCJCDATA);
			}
			List<Integer> applnNos=admForm.getApplnNos();
			if(applnNos!=null){
				String appNos=null;
				Iterator it=applnNos.iterator();
				while(it.hasNext()){
					appNos= it.next().toString()+",";
				}
				ActionMessage message=new ActionMessage(appNos);
				errors.add(appNos, message);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVUPLOADCONFIRMPAGE);


			}else
				return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVUPLOADCONFIRMPAGE);
		}catch(Exception exception){
			String msg = super.handleApplicationException(exception);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}



	/**
	 * initialize application form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOfflineApplicationForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init application detail page...");
		AdmissionFormForm admissionFormForm=(AdmissionFormForm)form;
		HttpSession session= request.getSession(true);
		//It use for Help,Don't Remove
		session.setAttribute("field","Offline_Application_Form");

		try {
			cleanupSessionData(session);
			cleanupFormFromSession(session);
			cleanupEditSessionData(request);
			admissionFormForm.setIsInterviewSelectionSchedule("false");
		} 
		catch (Exception e) {
			log.error("error in init studentpage...",e);
			throw e;

		}
		request.setAttribute("tempyr",admissionFormForm.getApplicationYear());
		log.info("exit init application detail page...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_FIRST_PAGE);
	}



	public ActionForward getPreferences(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
					throws Exception {
		ActionMessages message = new ActionMessages();
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		if (admForm.getCourseId()==null  || admForm.getCourseId().isEmpty() ) {
			ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_INVALID);
			message.add(CMSConstants.ADMISSIONFORM_COURSE_INVALID,error);
			saveErrors(request, message);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
		}
		String programId = AdmissionFormHandler.getInstance().getProgramId(Integer.parseInt(admForm.getCourseId()));
		if(programId!=null){
			admForm.setProgramId(programId);
		}
		AdmissionFormHandler formhandler = AdmissionFormHandler.getInstance();
		formhandler.getPreference(admForm,admForm.getProgramTypeId());
		if(admForm.getProgramTypeId()!=null && !admForm.getProgramTypeId().isEmpty()){
			Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByProgramForOnline(Integer.parseInt(admForm.getProgramId()));
			admForm.setCourseMap(courseMap);
			admForm.setCourseName1(courseMap.get(Integer.parseInt(admForm.getCourseId())));
		}

		return mapping.findForward(CMSConstants.ADMISSIONFORM_PREFERENCE_PAGE);
	}

	/**
	 * initialize application form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadInitOfflineApplicationForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter loadInitApplicationForm...");
		AdmissionFormForm admissionFormForm=(AdmissionFormForm)form;
		ActionMessages errors=admissionFormForm.validate(mapping, request);
		if (admissionFormForm.getApplicationNumber() != null && admissionFormForm.getApplicationYear() == null) {
			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED));
			}
		}
		if (admissionFormForm.getApplicationNumber() != null && !StringUtils.isEmpty(admissionFormForm.getApplicationNumber()) && !StringUtils.isNumeric(admissionFormForm.getApplicationNumber())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID));
			}
		}
		if(admissionFormForm.getApplicationNumber() != null && !StringUtils.isEmpty(admissionFormForm.getApplicationNumber().trim()) && StringUtils.isNumeric(admissionFormForm.getApplicationNumber()) && admissionFormForm.getYear() != null && !StringUtils.isEmpty(admissionFormForm.getYear().trim())){
			AdmissionFormHandler handler= AdmissionFormHandler.getInstance();

			boolean applnNoInrange = handler.checkValidOfflineNumber(Integer.parseInt(admissionFormForm.getApplicationNumber()), Integer.parseInt(admissionFormForm.getYear()));
			if (!applnNoInrange) {
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID));
				}

			}
		}
		if(errors!=null && !errors.isEmpty()){
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_FIRST_PAGE);
		} 

		//		if(admissionFormForm.getApplicationNumber() == null || admissionFormForm.getApplicationNumber().length() == 0) {
		//			return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_COURSE_SELCTION_PAGE);
		//		}

		try {
			admissionFormForm.setYear(admissionFormForm.getApplicationYear());
			boolean result = OfflineDetailsHandler.getInstance().getApplicationOfflineDetails(admissionFormForm);
			if(result) {
				setProgramAndCourseMap(admissionFormForm,request);
				request.setAttribute("operation", "load");
				return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_COURSE_SELCTION_PAGE);
			}
			//			else {
			//				if(!admissionFormForm.isCheckOfflineAppNo()) {
			//					errors.add("error", new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINEDATA_NOTEXIST));
			//		    		saveErrors(request,errors);
			//		    		admissionFormForm.setCheckOfflineAppNo(true);
			//		    		return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_FIRST_PAGE);
			//				}
			//			}

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE));
			saveErrors(request,errors);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_FIRST_PAGE);
		}catch(ApplicationException e){
			log.error("error in loadInitApplicationForm...",e);
			String msg=super.handleApplicationException(e);
			admissionFormForm.setErrorMessage(msg);
			admissionFormForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in init studentpage...",e);
			throw e;

		}
		log.info("exit loadInitApplicationForm...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_COURSE_SELCTION_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward offlinePrerequisiteApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initOfflinePrerequisiteApply...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		//admForm.setNcccertificate(false);
		try{

			ActionErrors errors = admForm.validate(mapping, request);
			validateProgramCourse(errors, admForm,false);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_COURSE_SELCTION_PAGE);
			}
			boolean applnNoInrange = AdmissionFormHandler.getInstance().checkApplicationNoInRange(
					admForm.getApplicationNumber(), admForm
					.isOnlineApply(), Integer.parseInt(admForm.getApplicationYear()), admForm
					.getCourseId());
			if (!applnNoInrange) {
				ActionMessages message = new ActionMessages();
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTINRANGE);
				message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTINRANGE,error);
				saveErrors(request, message);
				admForm.setApplicationError(true);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_COURSE_SELCTION_PAGE);
			}
			String courseName=null;
			String progName=null;
			String progTypeName=null;
			List<CourseTO> courselist=null;
			CourseHandler crsHandler=CourseHandler.getInstance();
			if(admForm.getCourseId()!=null && !StringUtils.isEmpty(admForm.getCourseId()) && StringUtils.isNumeric(admForm.getCourseId())){
				courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
			}
			if(courselist!=null && !courselist.isEmpty()){
				CourseTO to= courselist.get(0);
				if(to!=null){
					courseName=to.getName();
					if(to.getProgramTo()!=null){
						progName=to.getProgramTo().getName();

					}
					if(to.getProgramTo()!=null && to.getProgramTo().getProgramTypeTo()!=null ){
						progTypeName=to.getProgramTo().getProgramTypeTo().getProgramTypeName();
					}
				}
			}
			admForm.setCourseName(courseName);
			admForm.setProgramName(progName);
			admForm.setProgTypeName(progTypeName);
			AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
			setDataToInitForm(admForm);
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
			if(admForm.getProgramTypeId().equals(fileName)){
				admForm.setViewextradetails(true);
			}
			//athira
			admForm.setCheckReligionId(CMSConstants.RELIGION_CHRISTIAN_TYPE);
			//parish and dioceses


			DioceseTransactionImpl txn=new DioceseTransactionImpl();
			List<DioceseTo> dioceseList=txn.getDiocesesList();
			admForm.setDioceseList(dioceseList);

			ParishTransactionImpl parishtxn=new ParishTransactionImpl();
			List<ParishTo> parishList=parishtxn.getParishes();
			admForm.setParishList(parishList);
			// GET ALL PREREQUISTES FOR COURSE
			List<CoursePrerequisiteTO> prerequisites=handler.getCoursePrerequisites(Integer.parseInt(admForm.getCourseId()));
			if (prerequisites!=null && !prerequisites.isEmpty()) {
				admForm.setCoursePrerequisites(prerequisites);
				return mapping.findForward(CMSConstants.OFFLINE_PREREQUISITE_PAGE);
			}else{
				HttpSession session= request.getSession(false);
				setRequiredDataToForm(admForm,session);

				//			setOtherReviewRequireds(admForm,request);
				return mapping.findForward(CMSConstants.DETAIL_APPLICATION_PAGE);
			}
		}catch(Exception e){
			log.error("error initOfflinePrerequisiteApply...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else if(e instanceof SelectionDateNotAvailableException ){
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage("Contact Administrator");
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_OFFLINE_COURSE_SELCTION_PAGE);
			}else
			{
				throw e;
			}
		}
	}

	/**
	 * @param admForm
	 * @param session
	 * @throws Exception
	 */
	private void setRequiredDataToForm(AdmissionFormForm admForm,
			HttpSession session) throws Exception {

		AdmissionFormHandler.getInstance().setWorkExpNeeded(admForm);
		AdmissionFormHandler.getInstance().saveApplicationDetailsToSession(admForm,session);
		ExamGenHandler genHandler = new ExamGenHandler();
		HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
		admForm.setSecondLanguageList(secondLanguage);

		AdmissionFormHandler handler=AdmissionFormHandler.getInstance();
		AdmApplnTO applicantDetails = handler.getNewStudent(session,admForm);


		if(applicantDetails.getSelectedCourse()!=null){
			ProgramTO progTo=applicantDetails.getSelectedCourse().getProgramTo();
			if(progTo!=null){ 

				if(progTo.getProgramTypeTo()!=null){
					admForm.setAgeFromLimit(Integer.parseInt(progTo.getProgramTypeTo().getAgeFrom()));
					admForm.setAgeToLimit(Integer.parseInt(progTo.getProgramTypeTo().getAgeTo()));
				}
			}

			if(applicantDetails.getSelectedCourse().getIsDetailMarkPrint()!=null && "Yes".equalsIgnoreCase(applicantDetails.getSelectedCourse().getIsDetailMarkPrint()))
				admForm.setDetailMarksPrint(true);
			else
				admForm.setDetailMarksPrint(false);
		}
		//get states list for edn qualification
		List<StateTO> ednstates=StateHandler.getInstance().getNativeStates(CMSConstants.KP_DEFAULT_COUNTRY_ID);
		admForm.setEdnStates(ednstates);

		ProgramTypeHandler programtypehandler = ProgramTypeHandler.getInstance();
		List<ProgramTypeTO> programtypes = programtypehandler
				.getProgramType();


		admForm.setApplicantDetails(applicantDetails);
		String workExpNeeded=applicantDetails.getCourse().getIsWorkExperienceRequired();
		if(admForm.getApplicantDetails().getCourse()!=null && "Yes".equalsIgnoreCase(workExpNeeded))
		{
			admForm.setWorkExpNeeded(true);
		}else{
			admForm.setWorkExpNeeded(false);
		}
		admForm.setWorkExpMandatory(applicantDetails.getCourse().isWorkExpMandatory());

		CourseTO applicantCourse = applicantDetails.getCourse();
		ProgramTO tempMainProgramTO =applicantCourse.getProgramTo();
		if(tempMainProgramTO!=null){
			applicantCourse.setProgramId(tempMainProgramTO.getId());
			if(tempMainProgramTO.getProgramTypeTo()!=null){
				applicantCourse.setProgramTypeId(tempMainProgramTO.getProgramTypeTo().getProgramTypeId());
			}
		}
		CourseTO selectedCourse=applicantDetails.getSelectedCourse();
		ProgramTO tempProgramTO =selectedCourse.getProgramTo();
		if(tempProgramTO!=null){
			selectedCourse.setProgramId(tempProgramTO.getId());
			if(tempProgramTO.getProgramTypeTo()!=null){
				selectedCourse.setProgramTypeId(tempProgramTO.getProgramTypeTo().getProgramTypeId());
			}
		}
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
							}
						}
					}	
				}
			}
		}
		applicantDetails.setSelectedCourse(selectedCourse);
		applicantDetails.setCourse(applicantCourse);
		List<EntrancedetailsTO> entrnaceList=EntranceDetailsHandler.getInstance().getEntranceDeatilsByProgram(selectedCourse.getProgramId());
		admForm.setEntranceList(entrnaceList);
		setSelectedCourseAsPreference(admForm);
		Calendar cal= Calendar.getInstance();
		cal.setTime(new Date());


		applicantDetails.setCreatedBy(admForm.getUserId());
		applicantDetails.setCreatedDate(cal.getTime());

		ProgramTO programTO =selectedCourse.getProgramTo();
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
			if(programTO.getIsExamCenterRequired()==true){
				admForm.setExamCenterRequired(true);	
			}else{
				admForm.setExamCenterRequired(false);
			}
		}



		checkExtradetailsDisplay(admForm);
		checkLateralTransferDisplay(admForm);

		if(CountryHandler.getInstance().getCountries()!=null){
			//birthCountry & states
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


		//Nationality
		AdmissionFormHandler formhandler = AdmissionFormHandler.getInstance();
		admForm.setNationalities(formhandler.getNationalities());
		// languages	
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

		// res. catg
		admForm.setResidentTypes(formhandler.getResidentTypes());	

		ReligionHandler religionhandler = ReligionHandler.getInstance();
		if(religionhandler.getReligion()!=null){
			List<ReligionTO> religionList=religionhandler.getReligion();
			admForm.setReligions(religionList);
		}
		// caste category
		List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
		admForm.setCasteList(castelist);

		// Admitted Through
		List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
		admForm.setAdmittedThroughList(admittedList);
		// subject Group
		List<SubjectGroupTO> sujectgroupList=SubjectGroupHandler.getInstance().getSubjectGroupDetails(selectedCourse.getId());
		admForm.setSubGroupList(sujectgroupList);
		String[] subjectgroups=applicantDetails.getSubjectGroupIds();
		if (subjectgroups!=null && subjectgroups.length==0 && sujectgroupList!=null) {
			subjectgroups=new String[sujectgroupList.size()];
			applicantDetails.setSubjectGroupIds(subjectgroups);
		}

		//incomes
		List<IncomeTO> incomeList = AdmissionFormHandler.getInstance().getIncomes();
		admForm.setIncomeList(incomeList);

		//currencies
		List<CurrencyTO> currencyList = AdmissionFormHandler.getInstance().getCurrencies();
		admForm.setCurrencyList(currencyList);

		UniversityHandler unhandler = UniversityHandler.getInstance();
		List<UniversityTO> universities = unhandler.getUniversity();
		admForm.setUniversities(universities);

		OccupationTransactionHandler occhandler = OccupationTransactionHandler
				.getInstance();
		admForm.setOccupations(occhandler.getAllOccupation());

		List<ExamCenterTO> examCenterList = AdmissionFormHandler.getInstance().getExamCenters(selectedCourse.getProgramId());
		admForm.setExamCenters(examCenterList);

		// set photo to session
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
				firstTo.setCoursId(String.valueOf(applicantCourse.getId()));
				firstTo.setCoursName(applicantCourse.getName());
				firstTo.setProgId(String.valueOf(applicantCourse.getProgramId()));
				firstTo.setProgramtypeId(String.valueOf(applicantCourse.getProgramTypeId()));
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
			admForm.setPreferenceList(prefTos);
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
			admForm.setPreferenceList(prefTos);
		}

		//for ajax setting put preference lists in session

		if(session!=null){
			session.setAttribute(CMSConstants.COURSE_PREFERENCES, preferences);
		}

		admForm.setApplicantDetails(applicantDetails);




	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitNewOfflinePreRequisiteApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*
		log.info("enter submitOfflinePreRequisiteApply...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
		List<CoursePrerequisiteTO> prerequisites=admForm.getCoursePrerequisites();
		//Validate prerequisite and back if not
		ActionMessages messages= new ActionMessages();
		// VALIDATE PREREQUISTES REQUIREDS
		messages= validatePrerequisiteRequireds(prerequisites,messages);
		if(messages!=null && messages.isEmpty() )
			// VALIDATE PREREQUISTES ELIGIBILITY
		messages= validatePrerequisite(admForm,messages);

		if(messages!=null && !messages.isEmpty() )
		{

			saveErrors(request, messages);
			return mapping.findForward(CMSConstants.OFFLINE_PREREQUISITE_PAGE);
		}
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		HttpSession session= request.getSession(false);
		if(session==null)
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
		// SAVE THE PREREQUISITE DETAILS
		handler.savePrerequisitesToSession(session,admForm.getEligPrerequisites(),admForm.getUserId());

		setRequiredDataToForm(admForm, session);

//		setOtherReviewRequireds(admForm,request);

		}catch(ApplicationException e){
			log.error("error in submitOfflinePreRequisiteApply...",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch(SelectionDateNotAvailableException e){
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				//admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.OFFLINE_PREREQUISITE_PAGE);
		}
		catch (Exception e) {
			log.error("error in submitOfflinePreRequisiteApply...",e);
				throw e;

		}
		return mapping.findForward(CMSConstants.DETAIL_APPLICATION_PAGE);


			 */
		return mapping.findForward(CMSConstants.DETAIL_APPLICATION_PAGE);
	}

	public ActionForward addMorePreference(ActionMapping mapping,ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		//log.info("Befinning of addMorePreference ");
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		ActionMessages message = new ActionMessages();


		if (admForm.getCourseId()==null  || admForm.getCourseId().isEmpty() ) {
			ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_INVALID);
			message.add(CMSConstants.ADMISSIONFORM_COURSE_INVALID,error);
			saveErrors(request, message);
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
		}

		if(admForm.getProgramTypeId()!=null && !admForm.getProgramTypeId().isEmpty() && !admForm.getProgramTypeId().equalsIgnoreCase("")){
			Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByProgramType(Integer.parseInt(admForm.getProgramTypeId()));
			admForm.setCourseMap(courseMap);
			//admForm.setCourseName1(courseMap.get(Integer.parseInt(admForm.getCourseId())));
			//CourseTO courseTO1=new CourseTO();
			List<CourseTO> list=new LinkedList<CourseTO>();
			list=admForm.getPrefcourses();
			Iterator<CourseTO> itr=list.iterator();


			while(itr.hasNext()){
				CourseTO courseTO=(CourseTO) itr.next();
				if(courseTO.getId()==Integer.parseInt(admForm.getCourseId()) && Integer.parseInt(courseTO.getPrefNo())==1 ){
					//list.remove(courseTO);
					itr.remove();
				}

			}
			//raghu set preference limit for 5 only
			if(list.size()>=2){
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID);
				message.add(CMSConstants.ADMISSIONFORM_COURSE_PREFSIZE_INVALID,error);
				saveErrors(request, message);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
			}

			List<CourseTO> list1=new LinkedList<CourseTO>();
			list1=admForm.getPrefcourses();
			Iterator<CourseTO> itr1=list1.iterator();

			while(itr1.hasNext()){
				CourseTO courseTO=(CourseTO) itr1.next();
				if(courseTO.getId()==0 || String.valueOf(courseTO.getId()).equalsIgnoreCase("") || String.valueOf(courseTO.getId()).isEmpty() ){
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_EMPTY_INVALID);
					message.add(CMSConstants.ADMISSIONFORM_COURSE_EMPTY_INVALID,error);
					saveErrors(request, message);
					return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
				}

			}
			while(itr.hasNext()){
				CourseTO courseTO=(CourseTO) itr.next();
				if(courseTO.getId()==Integer.parseInt(admForm.getCourseId()) && Integer.parseInt(courseTO.getPrefNo())==1 ){
					//list.remove(courseTO);
					itr.remove();
				}

			}

			CourseTO courseTO=new CourseTO();
			courseTO.setCourseMap(courseMap);
			courseTO.setPrefNo(String.valueOf(list.size()+2));
			list.add(courseTO);
			admForm.setPrefcourses(list);
			//raghu set preference limit for 5 only
			for(int i=0;i<=2;i++){

				if(list.size()==i){
					courseTO.setPrefName(prefNameMap.get(list.size()));

				}
			}

			//admForm.setCourseId("");
		}


		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE);
	}

	/**
	 * Method generates the necessary parameters and redirects to the third party 
 		payment gateway interface for online payment
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward redirectToPGI(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter redirectToPGI...");
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		ActionErrors errors=new ActionErrors();
		//raghu
		if(admForm.getApplicationAmount1()!=null)
			admForm.setApplicationAmount(admForm.getApplicationAmount1());

		validatePgi(admForm,errors);
		try{
			if(errors!=null && !errors.isEmpty()){
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE);
			}
			String hash= AdmissionFormHandler.getInstance().getParameterForPGI(admForm);
			//request.setAttribute("pgiMsg", msg);
			request.setAttribute("user_code", CMSConstants.PGI_MERCHANT_ID);
			request.setAttribute("pass_code", CMSConstants.PGI_SECURITY_ID);
			request.setAttribute("tran_id", CMSConstants.PGI_MERCHANT_ID+admForm.getRefNo());
			request.setAttribute("amount", Integer.parseInt(admForm.getApplicationAmount())*100);
			request.setAttribute("charge_code", "A");
			request.setAttribute("hash_value", hash);




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
	private void validatePgi(AdmissionFormForm admForm,ActionErrors errors) {
		if((admForm.getApplicationAmount()== null || admForm.getApplicationAmount().isEmpty())
				&& (admForm.getEquivalentCalcApplnFeeINR()== null || admForm.getEquivalentCalcApplnFeeINR().isEmpty())){
			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_REQUIRED));
			}
		}
	}
	/*
	public ActionForward printApplnPDF(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("printPDF");
	}

	 */
	public ActionForward printApplnPDF(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AdmissionFormForm admForm = (AdmissionFormForm) form;
		//set caste show or not
		admForm.setCasteDisplay(CMSConstants.CASTE_ENABLED);
		try{

			String applicationNumber ="";
			if(admForm.getApplicationNumber()!=null && !admForm.getApplicationNumber().equalsIgnoreCase("")){
				applicationNumber = admForm.getApplicationNumber().trim();

			}
			if(admForm.getApplnNo()!=null && !admForm.getApplnNo().equalsIgnoreCase("")){
				applicationNumber = admForm.getApplnNo().trim();

			}

			//String applicationNumber = admForm.getApplicationNumber().trim();
			int applicationYear = Integer.parseInt(admForm.getApplicationYear());

			//make values null
			admForm.setApplicationYear(null);
			admForm.setApplnNo(null);
			admForm.setApplicationNumber(null);
			admForm.setApplicantDetails(null);

			AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicationDetails(applicationNumber, applicationYear);

			if( applicantDetails == null){

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_NORECORDS);
				messages.add("messages", message);
				saveMessages(request, messages);

				return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMPRINT_PAGE);

			} else {
				if(applicantDetails.getId()>0)
				{
					String txnRefNo = AdmissionFormHandler.getInstance().getCandidatePGIDetails(applicantDetails.getId());
					if(txnRefNo!=null && !txnRefNo.isEmpty())
						admForm.setTxnRefNo(txnRefNo);
					AdmissionFormHandler.getInstance().getOnlinePaymentAcknowledgementDetails(admForm,applicantDetails.getId());
				}
				if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getDob()!=null )
					applicantDetails.getPersonalData().setDob(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getDob(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));
				if(applicantDetails.getChallanDate()!=null )
					applicantDetails.setChallanDate(CommonUtil.ConvertStringToDateFormat(applicantDetails.getChallanDate(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));
				//set applicant details
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
				// set flags
				if (programtypes!=null) {
					admForm.setEditProgramtypes(programtypes);
					Iterator<ProgramTypeTO> typeitr= programtypes.iterator();
					while (typeitr.hasNext()) {
						ProgramTypeTO typeTO = (ProgramTypeTO) typeitr.next();
						if(typeTO.getProgramTypeId()==admForm.getApplicantDetails().getCourse().getProgramTypeId()){
							if(typeTO.getPrograms()!=null){
								admForm.setEditprograms(typeTO.getPrograms());
								Iterator<ProgramTO> programitr= typeTO.getPrograms().iterator();
								while (programitr.hasNext()) {
									ProgramTO programTO = (ProgramTO) programitr
											.next();
									if(programTO.getId()== admForm.getApplicantDetails().getCourse().getProgramId()){
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
										}
									}
								}
							}	
						}
					}
				}


				checkExtradetailsDisplay(admForm);
				checkLateralTransferDisplay(admForm);
				// Admitted Through
				List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
				admForm.setAdmittedThroughList(admittedList);
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				//				if(admForm.isDisplayAdditionalInfo())
				//				{

				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);
					admForm.setOrganizationName(orgTO.getOrganizationName());
					admForm.setNeedApproval(orgTO.isNeedApproval());
				}
				//				}



				if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(applicantDetails.getPersonalData().getPassportValidity()) )
					applicantDetails.getPersonalData().setPassportValidity(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getPassportValidity(), AdmissionFormAction.SQL_DATEFORMAT,AdmissionFormAction.FROM_DATEFORMAT));

				// set photo to session
				HttpSession session= request.getSession(false);
				//session.setAttribute("STUDENT_IMAGE", "images/StudentPhotos/"+admForm.getStudentId()+".jpg?"+applicantDetails.getLastModifiedDate());

				if(applicantDetails.getEditDocuments()!=null){
					Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
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
										session.setAttribute(AdmissionFormAction.PHOTOBYTES,fileData );
								}
							}else if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){

								if(session!=null){
									session.setAttribute(AdmissionFormAction.PHOTOBYTES, docTO.getPhotoBytes());
								}
							}
						}
					}
				}//*/

				//get template and replace the data
				String template=AdmissionFormHandler.getInstance().getDeclarationTemplate(applicantDetails,request);
				admForm.setInstrTemplate(template);

				Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
				if(organisation!=null){
					StringBuffer orgAdd=new StringBuffer();
					orgAdd.append(organisation.getAddressLine1());
					if(organisation.getAddressLine2()!=null && !StringUtils.isEmpty(organisation.getAddressLine2())){
						orgAdd.append(",");
						orgAdd.append(organisation.getAddressLine2());
					}
					if(organisation.getAddressLine3()!=null && !StringUtils.isEmpty(organisation.getAddressLine3())){
						orgAdd.append(",");
						orgAdd.append(organisation.getAddressLine3());
					}

					admForm.setOrgAddress(orgAdd.toString());
					// set photo to session
					if(organisation.getLogoContentType()!=null){
						if(session!=null){
							session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo1());

						}
					}
				}

				//------------bar code creation
				if(!applicationNumber.trim().isEmpty()){
					String imgPath=request.getRealPath("");
					imgPath = imgPath + "//BarCode//"+ applicationNumber + ".jpeg";
					File barCodeImgFile =  new File(imgPath);
					if(barCodeImgFile.exists()){
						barCodeImgFile.delete();
					}
					Barcode code = BarcodeFactory.createCode128A(applicationNumber);
					code.setBarWidth(1);
					code.setBarHeight(40);
					code.setDrawingText(false);
					BarcodeImageHandler.saveJPEG(code, barCodeImgFile);
				}					
				if(admForm.isWorkExpNeeded()){
					String totalExp=getTotalYearsOfExperience(applicantDetails.getWorkExpList());
					admForm.setTotalYearOfExp(totalExp);
				}
			}
		}catch (Exception e){
			log.error("Error in  getApplicantDetails application form approval page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				throw e;
			}
		}
		return mapping.findForward("printPDF");
		//		return mapping.findForward(CMSConstants.ADMISSIONFORM_PRINT_PAGE);
	}


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
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();

		try{
			boolean isUpdated= AdmissionFormHandler.getInstance().updateResponse(admForm);
			if(admForm.getIsTnxStatusSuccess()){
				AdmissionFormHandler.getInstance().sendMailForOnlinePaymentConformation(admForm);
			}
			if(isUpdated){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.empty.err.message",admForm.getPgiStatus()));
				saveMessages(request, messages);
			}
			else{
				errors.add("error", new ActionError("knowledgepro.admission.pgi.update.failure"));
				saveErrors(request, errors);
			}
		}
		catch (BusinessException e) {
			errors.add("error", new ActionError("knowledgepro.admission.pgi.rejected"));
			saveErrors(request, errors);
		}
		catch (Exception e) {
			log.error("error in updatePGIResponse-AdmissionFormAction...",e);
			throw e;
		}
		log.info("exit updatePGIResponse-AdmissionFormAction...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE);
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkConfirmPagedeg(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {


		log.info("enter initDetailMarkConfirmPage page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {

			String indexString = request.getParameter(AdmissionFormAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(AdmissionFormAction.COUNTID, indexString);
				}else
					session.removeAttribute(AdmissionFormAction.COUNTID);
			}
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			List<AdmSubjectMarkForRankTO> admsubList= new LinkedList<AdmSubjectMarkForRankTO>();
			if( admForm.getAdmsubMarkList()!=null && admForm.getAdmsubMarkList().size()!=0){
				//admForm.setAdmsubMarkList(admsubList);
				List<AdmSubjectMarkForRankTO> admSubList=new LinkedList<AdmSubjectMarkForRankTO>();
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
						if(admSubjectMarkForRankTO.getCredit()!=null)
							admSubjectMarkForRankTO.setCredit(admSubjectMarkForRankTO.getCredit());

						admSubList.add(admSubjectMarkForRankTO);
					}
				}
				admForm.setAdmsubMarkList(admSubList);
			}
			else{
				for(int i=0;i<14;i++){
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

					if(qualTO.getDocName().equalsIgnoreCase("DEG")){
						String Core="Core";
						String Compl="Complimentary";
						String Common="Common";
						String Main="Open";
						String Sub="Vocational";
						Map<Integer,String> admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Core);
						admForm.setAdmCoreMap(admCoreMap);
						Map<Integer,String> admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Compl);
						admForm.setAdmComplMap(admComplMap);
						Map<Integer,String> admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Common);
						admForm.setAdmCommonMap(admCommonMap);
						Map<Integer,String> admOpenMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Main);
						admForm.setAdmMainMap(admOpenMap);
						Map<Integer,String> admVocMap=AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(),Sub);
						admForm.setAdmSubMap(admVocMap);

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
		if(admForm.isOnlineApply())
			return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORDEG);
		else
			return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);

	}

	public ActionForward submitDetailMarkConfirmfordeg(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {/*


		log.info("enter submitDetailMarkConfirm page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		HttpSession session=request.getSession(false);

		try {

				String indexString = null;

				if (session != null)
					indexString = (String) session.getAttribute(AdmissionFormAction.COUNTID);

				int detailMarkIndex = -1;

				if (indexString != null)
					detailMarkIndex = Integer.parseInt(indexString);

				CandidateMarkTO detailmark = admForm.getDetailMark();

				Map<Integer,String> admcoremap=admForm.getAdmCoreMap();
				Map<Integer,String> admcomplmap=admForm.getAdmComplMap();
				Map<Integer,String> admcommmap=admForm.getAdmCommonMap();
				Map<Integer,String> admmainmap=admForm.getAdmMainMap();
				Map<Integer,String> admsubmap=admForm.getAdmSubMap();
				List<AdmSubjectMarkForRankTO>  admsubList=admForm.getAdmsubMarkList();


					//if(detailmark.getSubject1()==null){
						if(admForm.getPatternofStudy().equalsIgnoreCase("CBCSS")){

							if(admForm.getDegsubid_0()!=null && !admForm.getDegsubid_0().equalsIgnoreCase("")){ 
								//	&& admForm.getDegobtainedmark_0()!=null && !admForm.getDegobtainedmark_0().equalsIgnoreCase("") &&
								//admForm.getDegmaxmark_0()!=null && !admForm.getDegmaxmark_0().equalsIgnoreCase("")){
							if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_0()))){
								String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_0()));
								detailmark.setSubject1(s);
							}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_0()))){
								String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_0()));
								detailmark.setSubject1(s);
							}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_0()))){
								String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_0()));
								detailmark.setSubject1(s);
							}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_0()))){
								String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_0()));
								detailmark.setSubject1(s);
							}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_0()))){
								String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_0()));
								detailmark.setSubject1(s);
							}


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
								}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_1()))){
									String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_1()));
									detailmark.setSubject2(s);
								}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_1()))){
									String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_1()));
									detailmark.setSubject2(s);
								}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_1()))){
									String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_1()));
									detailmark.setSubject2(s);
								}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_1()))){
									String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_1()));
									detailmark.setSubject2(s);
								}


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
									}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_2()))){
										String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_2()));
										detailmark.setSubject3(s);
									}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_2()))){
										String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_2()));
										detailmark.setSubject3(s);
									}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_2()))){
										String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_2()));
										detailmark.setSubject3(s);
									}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_2()))){
										String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_2()));
										detailmark.setSubject3(s);
									}


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
										}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_3()))){
											String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_3()));
											detailmark.setSubject4(s);
										}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_3()))){
											String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_3()));
											detailmark.setSubject4(s);
										}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_3()))){
											String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_3()));
											detailmark.setSubject4(s);
										}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_3()))){
											String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_3()));
											detailmark.setSubject4(s);
										}


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
											}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_4()))){
												String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_4()));
												detailmark.setSubject5(s);
											}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_4()))){
												String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_4()));
												detailmark.setSubject5(s);
											}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_4()))){
												String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_4()));
												detailmark.setSubject5(s);
											}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_4()))){
												String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_4()));
												detailmark.setSubject5(s);
											}


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
												}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_5()))){
													String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_5()));
													detailmark.setSubject6(s);
												}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_5()))){
													String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_5()));
													detailmark.setSubject6(s);
												}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_5()))){
													String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_5()));
													detailmark.setSubject6(s);
												}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_5()))){
													String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_5()));
													detailmark.setSubject6(s);
												}


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
												}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_6()))){
													String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_6()));
													detailmark.setSubject7(s);
												}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_6()))){
													String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_6()));
													detailmark.setSubject7(s);
												}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_6()))){
													String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_6()));
													detailmark.setSubject7(s);
												}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_6()))){
													String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_6()));
													detailmark.setSubject7(s);
												}

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
												}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_14()))){
													String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_14()));
													detailmark.setSubject15(s);
												}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_14()))){
													String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_14()));
													detailmark.setSubject15(s);
												}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_14()))){
													String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_14()));
													detailmark.setSubject15(s);
												}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_14()))){
													String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_14()));
													detailmark.setSubject15(s);
												}

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
												if(admForm.isOnlineApply())
													return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORDEG);
												else
												return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
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
													if(admForm.isOnlineApply())
														return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORDEG);
													else
													return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
												}

















						int i=0;
						Iterator<AdmSubjectMarkForRankTO> itr=admsubList.iterator();
						String s="";
						while(itr.hasNext()){


							AdmSubjectMarkForRankTO admSubjectMarkForRankTO=(AdmSubjectMarkForRankTO) itr.next();
							if(i==0){
							if(admForm.getDegsubid_0()!=null && !admForm.getDegsubid_0().equalsIgnoreCase("") && admForm.getDegobtainedmark_0()!=null && !admForm.getDegobtainedmark_0().equalsIgnoreCase("") &&
									admForm.getDegmaxmark_0()!=null && !admForm.getDegmaxmark_0().equalsIgnoreCase("") && admForm.getDegmaxcgpa_0()!=null && !admForm.getDegmaxcgpa_0().equalsIgnoreCase("")){


								admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_0());
							admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_0());
							admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_0());
							admSubjectMarkForRankTO.setCredit(admForm.getDegmaxcgpa_0());
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
								}
								i++;
								}


							//admsubList.add(admSubjectMarkForRankTO);
						}


						}
						else{







							if(admForm.getDegsubid_7()!=null && !admForm.getDegsubid_7().equalsIgnoreCase("") ){
									//&& admForm.getDegobtainedmark_7()!=null && !admForm.getDegobtainedmark_7().equalsIgnoreCase("") &&
									//admForm.getDegmaxmark_7()!=null && !admForm.getDegmaxmark_7().equalsIgnoreCase("")){
								if(admcoremap.containsKey(Integer.parseInt(admForm.getDegsubid_7()))){
									String s=(String)admcoremap.get(Integer.parseInt(admForm.getDegsubid_7()));
									detailmark.setSubject8(s);
								}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_7()))){
									String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_7()));
									detailmark.setSubject8(s);
								}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_7()))){
									String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_7()));
									detailmark.setSubject8(s);
								}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_7()))){
									String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_7()));
									detailmark.setSubject8(s);
								}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_7()))){
									String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_7()));
									detailmark.setSubject8(s);
								}


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
								}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_8()))){
									String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_8()));
									detailmark.setSubject9(s);
								}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_8()))){
									String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_8()));
									detailmark.setSubject9(s);
								}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_8()))){
									String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_8()));
									detailmark.setSubject9(s);
								}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_8()))){
									String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_8()));
									detailmark.setSubject9(s);
								}


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
								}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_9()))){
									String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_9()));
									detailmark.setSubject10(s);
								}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_9()))){
									String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_9()));
									detailmark.setSubject10(s);
								}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_9()))){
									String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_9()));
									detailmark.setSubject10(s);
								}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_9()))){
									String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_9()));
									detailmark.setSubject10(s);
								}


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
								}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_10()))){
									String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_10()));
									detailmark.setSubject11(s);
								}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_10()))){
									String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_10()));
									detailmark.setSubject11(s);
								}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_10()))){
									String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_10()));
									detailmark.setSubject11(s);
								}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_10()))){
									String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_10()));
									detailmark.setSubject11(s);
								}


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
								}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_11()))){
									String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_11()));
									detailmark.setSubject12(s);
								}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_11()))){
									String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_11()));
									detailmark.setSubject12(s);
								}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_11()))){
									String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_11()));
									detailmark.setSubject12(s);
								}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_11()))){
									String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_11()));
									detailmark.setSubject12(s);
								}


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
								}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_12()))){
									String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_12()));
									detailmark.setSubject13(s);
								}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_12()))){
									String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_12()));
									detailmark.setSubject13(s);
								}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_12()))){
									String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_12()));
									detailmark.setSubject13(s);
								}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_12()))){
									String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_12()));
									detailmark.setSubject13(s);
								}


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
								}else if(admmainmap.containsKey(Integer.parseInt(admForm.getDegsubid_13()))){
									String s=(String)admmainmap.get(Integer.parseInt(admForm.getDegsubid_13()));
									detailmark.setSubject14(s);
								}else if(admcommmap.containsKey(Integer.parseInt(admForm.getDegsubid_13()))){
									String s=(String)admcommmap.get(Integer.parseInt(admForm.getDegsubid_13()));
									detailmark.setSubject14(s);
								}else if(admcomplmap.containsKey(Integer.parseInt(admForm.getDegsubid_13()))){
									String s=(String)admcomplmap.get(Integer.parseInt(admForm.getDegsubid_13()));
									detailmark.setSubject14(s);
								}else if(admsubmap.containsKey(Integer.parseInt(admForm.getDegsubid_13()))){
									String s=(String)admsubmap.get(Integer.parseInt(admForm.getDegsubid_13()));
									detailmark.setSubject14(s);
								}


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
								if(admForm.isOnlineApply())
									return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORDEG);
								else
								return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
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
									if(admForm.isOnlineApply())
										return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORDEG);
									else
									return mapping.findForward(CMSConstants.ADMISSIONFORM_REVIEW_DET_MARK_PAGE);
								}













		int i=8;
		Iterator<AdmSubjectMarkForRankTO> itr=admsubList.iterator();
		String s="";
		while(itr.hasNext()){


			AdmSubjectMarkForRankTO admSubjectMarkForRankTO=(AdmSubjectMarkForRankTO) itr.next();


							if(i==8){
								if(admForm.getDegsubid_7()!=null && !admForm.getDegsubid_7().equalsIgnoreCase("") && admForm.getDegobtainedmark_7()!=null && !admForm.getDegobtainedmark_7().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_7()!=null && !admForm.getDegmaxmark_7().equalsIgnoreCase("")){


									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_7());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_7());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_7());
								}

								i++;
								}


							else if(i==9){
								if(admForm.getDegsubid_8()!=null && !admForm.getDegsubid_8().equalsIgnoreCase("") && admForm.getDegobtainedmark_8()!=null && !admForm.getDegobtainedmark_8().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_8()!=null && !admForm.getDegmaxmark_8().equalsIgnoreCase("")){


									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_8());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_8());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_8());
								}

								i++;
								}



							else if(i==10){
								if(admForm.getDegsubid_9()!=null && !admForm.getDegsubid_9().equalsIgnoreCase("") && admForm.getDegobtainedmark_9()!=null && !admForm.getDegobtainedmark_9().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_9()!=null && !admForm.getDegmaxmark_9().equalsIgnoreCase("")){


									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_9());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_9());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_9());
								}

								i++;
								}



							else if(i==11){
								if(admForm.getDegsubid_10()!=null && !admForm.getDegsubid_10().equalsIgnoreCase("") && admForm.getDegobtainedmark_10()!=null && !admForm.getDegobtainedmark_10().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_10()!=null && !admForm.getDegmaxmark_10().equalsIgnoreCase("")){


									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_10());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_10());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_10());
								}

								i++;
								}
							else if(i==12){
								if(admForm.getDegsubid_11()!=null && !admForm.getDegsubid_11().equalsIgnoreCase("") && admForm.getDegobtainedmark_11()!=null && !admForm.getDegobtainedmark_11().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_11()!=null && !admForm.getDegmaxmark_11().equalsIgnoreCase("")){


									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_11());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_11());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_11());
								}

								i++;
								}

							else if(i==13){
								if(admForm.getDegsubid_12()!=null && !admForm.getDegsubid_12().equalsIgnoreCase("") && admForm.getDegobtainedmark_12()!=null && !admForm.getDegobtainedmark_12().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_12()!=null && !admForm.getDegmaxmark_12().equalsIgnoreCase("")){


									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_12());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_12());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_12());
								}

								i++;
								}
							else if(i==14){
								if(admForm.getDegsubid_13()!=null && !admForm.getDegsubid_13().equalsIgnoreCase("") && admForm.getDegobtainedmark_13()!=null && !admForm.getDegobtainedmark_13().equalsIgnoreCase("") &&
										admForm.getDegmaxmark_13()!=null && !admForm.getDegmaxmark_13().equalsIgnoreCase("")){


									admSubjectMarkForRankTO.setSubid(admForm.getDegsubid_13());
								admSubjectMarkForRankTO.setMaxmark(admForm.getDegmaxmark_13());
								admSubjectMarkForRankTO.setObtainedmark(admForm.getDegobtainedmark_13());
								}

								i++;
								}
			}//while close

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



			}//try close

		catch (Exception e) {
			// TODO: handle exception
		}






	return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);				


			 */

		return mapping.findForward(CMSConstants.DETAIL_APPLICANT_SINGLE_PAGE);	
	}// method close







	/**
	 * @param detailmark
	 * @param errors
	 * @return
	 */
	public static ActionMessages validateMarksForUG(CandidateMarkTO detailmark,ActionMessages errors,String CBCSS){/*




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

		}// other close




			if(CBCSS.equalsIgnoreCase("CBCSS")){



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

			//cbscc close
			}else{


			if(  detailmark.getSubject8ObtainedMarks()!=null && !(detailmark.getSubject8ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID, error);

				return errors;
			}
			if(  detailmark.getSubject9ObtainedMarks()!=null &&!(detailmark.getSubject9ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);

				return errors;
			}

			if(  detailmark.getSubject10ObtainedMarks()!=null &&!(detailmark.getSubject10ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);

				return errors;
			}

			if(  detailmark.getSubject11ObtainedMarks()!=null &&!(detailmark.getSubject11ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);

				return errors;
			}
			if(  detailmark.getSubject12ObtainedMarks()!=null &&!(detailmark.getSubject12ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);

				return errors;
			}

			if(  detailmark.getSubject13ObtainedMarks()!=null &&!(detailmark.getSubject13ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);

				return errors;
			}
			if(  detailmark.getSubject14ObtainedMarks()!=null &&!(detailmark.getSubject14ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?") )){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_VALID);
				errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY, error);

				return errors;
			}
			}//other close


			mandatory subject mark check start 
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


			mandatory subject mark check end 

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

			//raghu write newly
			if(detailmark.getSubject1TotalMarks()!=null && StringUtils.isEmpty(detailmark.getSubject1TotalMarks())){
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


	 */
		return errors;	
	}

	public ActionForward initPendingOnlineApp(ActionMapping mapping,
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init initPendingOnlineApp");
		AdmissionFormForm admForm = (AdmissionFormForm) form;

		admForm.setCandidateRefNo(null);
		admForm.setTransactionRefNO(null);
		admForm.setNameOfStudent(null);
		admForm.setMobile2(null);
		admForm.setDateOfBirth(null);
		admForm.setCourseName(null);
		admForm.setResidentCategory(null);
		admForm.setEmailId(null);
		admForm.setPageTogle("first");

		setUserId(request, admForm);

		return mapping.findForward(CMSConstants.ADMISSIONFORM_PENDING_ONLINE_APP);
	}

	public ActionForward getPendingOnlineApp(ActionMapping mapping,
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init getPendingOnlineApp");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		ActionMessages errors= new ActionMessages();
		try {

			errors=admForm.validate(mapping, request);
			if(admForm.getCorrectionFor().equalsIgnoreCase("Online Application")) {
				if(admForm.getTxnDate() == null ||admForm.getTxnDate().isEmpty()) {
					errors.add("error", new ActionError("knowledgepro.inventory.date.required"));
				}
				/*if(admForm.getAmount() == null ||admForm.getAmount().isEmpty()) {
					errors.add("error", new ActionError("knowledgepro.admin.FineCategoryamount.required"));
				}*/
				if(admForm.getCandidateRefNo() == null ||admForm.getCandidateRefNo().isEmpty()) {
					errors.add("error", new ActionError("application.reference.number.required"));
				}
			}
			if(errors.isEmpty()){



				boolean isUpdated= AdmissionFormHandler.getInstance().getPendingOnlineApp(admForm);
				if(isUpdated){
					ActionMessages messages = new ActionMessages();
					ActionMessage message = new ActionMessage("knowledgepro.admission.empty.error.message","Payment successfully updated.");
					messages.add("messages", message);
					saveMessages(request, messages);
					
				}else{

					errors.add("knowledgepro.admission.empty.error.message", new ActionError("knowledgepro.admission.empty.error.message","No pending records are found"));
					saveErrors(request, errors);

				}

			}
			else{
				saveErrors(request, errors);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("error in init getPendingOnlineApp...",e);
			throw e;

		}
		admForm.setPageTogle("first");
		log.info("exit init getPendingOnlineApp..");

		return mapping.findForward(CMSConstants.ADMISSIONFORM_PENDING_ONLINE_APP);
	}
	public ActionForward getAppplicantDetails(ActionMapping mapping,
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init getPendingOnlineApp");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		ActionMessages errors= new ActionMessages();
		boolean flag;
		try {

			if(admForm.getCorrectionFor().equalsIgnoreCase("Online Application")) {
				if(admForm.getTxnDate() == null ||admForm.getTxnDate().isEmpty()) {
					errors.add("error", new ActionError("knowledgepro.inventory.date.required"));
				}
				if(admForm.getCandidateRefNo() == null ||admForm.getCandidateRefNo().isEmpty()) {
					errors.add("error", new ActionError("application.reference.number.required"));
				}
			}
			if(errors.isEmpty()){



				flag=AdmissionFormHandler.getInstance().getApplicantDeatails(admForm);
				if(!flag){
					ActionMessages messages = new ActionMessages();
					ActionMessage message = new ActionMessage("knowledgepro.admission.empty.error.message","Applicant not found");
					messages.add("messages", message);
					saveMessages(request, messages);
				}else{
					admForm.setPageTogle("confirm");
				}
			}	
			else{
				saveErrors(request, errors);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("error in init getPendingOnlineApp...",e);
			throw e;

		}

		log.info("exit init getPendingOnlineApp..");

		return mapping.findForward(CMSConstants.ADMISSIONFORM_PENDING_ONLINE_APP);
	}

}


// =========================================== class over



class ByteArrayStreamInfo implements StreamInfo {	

	protected String contentType;
	protected byte[] bytes;	

	public ByteArrayStreamInfo(String contentType, byte[] myDfBytes) {
		this.contentType = contentType;
		this.bytes = myDfBytes;
	}	
	public String getContentType() {
		return contentType;
	}	
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(bytes);
	}



	/*private void setDisplaySessionDetails(AdmissionFormForm objForm,
			ActionErrors errors, HttpServletRequest request) throws Exception {
		Map<String, List<ExamRegistrationDetailsTo>> examRegDetailsMap = ExamRegistrationDetailsHandler
				.getInstance().getDateAndSessionsOfWorkLocation(objForm);
		if(objForm.getWorkLocationId()>0){
			if (examRegDetailsMap != null && !examRegDetailsMap.isEmpty()) {
				objForm.setDateSessionMap(examRegDetailsMap);
			} else {
				objForm.setDateSessionMap(null);
				objForm.setMessage("message");
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
			}
		}else{
			objForm.setDateSessionMap(null);
		}
	}*/					 




}


