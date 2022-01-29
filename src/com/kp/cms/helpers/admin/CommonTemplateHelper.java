package com.kp.cms.helpers.admin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.WordUtils;

import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.bo.admin.FeePaymentDetailFeegroup;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Sports;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.CommonTemplateForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.to.admin.CommonTemplateTO;
import com.kp.cms.to.admin.SportsTO;
import com.kp.cms.transactionsimpl.admin.CommonTemplateTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class CommonTemplateHelper {
	public static volatile CommonTemplateHelper commonTemplateHelper =null;
	/**
	 * @return
	 */
	private CommonTemplateHelper(){
		
	}
	public static CommonTemplateHelper getInstance() {
		if(commonTemplateHelper == null){
			commonTemplateHelper = new CommonTemplateHelper();
			return commonTemplateHelper;
		}
		return commonTemplateHelper;
	}
	/**
	 * @param stuList
	 * @param isStudent
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToList(List<Student> stuList, HttpServletRequest request, CommonTemplateForm commonTemplateForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.TEMPLATE_NODUE);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, student.getAdmAppln().getPersonalData().getFatherName());
				}
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,student.getRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,"---");
				}
				if(student.getExamRegisterNo() != null && !student.getExamRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,student.getExamRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,"---");
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "S/o");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "D/o");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream() != null){
					message = message.replace(CMSConstants.TEMPLATE_STREAM, student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getName() != null){
					message = message.replace(CMSConstants.TEMPLATE_CLASS, student.getClassSchemewise().getClasses().getName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getName() != null){
					message = message.replace(CMSConstants.TEMPLATE_COURSE, student.getAdmAppln().getCourseBySelectedCourseId().getName());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getTermNumber() != null){
					if( student.getClassSchemewise().getClasses().getTermNumber()== 1){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"one");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 2){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"one");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 3){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"two");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 4){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"two");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 5){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"three");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 6){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"three");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "He");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "She");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER2, "his");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER2, "her");
					}
				}
				messageList.add(message);
			   }
		   }
		return messageList;
	}
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToListForVisa(List<Student> stuList, HttpServletRequest request, CommonTemplateForm commonTemplateForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.TEMPLATE_VISALETTER);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln().getId() != 0 ){
					message =message.replace(CMSConstants.TEMPLATE_APPLICATION_NO, String.valueOf(student.getAdmAppln().getApplnNo()));
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null &&student.getAdmAppln().getCourseBySelectedCourseId().getCode() != null ){
					String courseApplied  = student.getAdmAppln().getCourseBySelectedCourseId().getCode();
					if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getTermNumber() != null){
						if( student.getClassSchemewise().getClasses().getTermNumber()== 1){
							courseApplied = courseApplied+" (I PU)";
						}
						if( student.getClassSchemewise().getClasses().getTermNumber()== 2){
							courseApplied = courseApplied+" (II PU)";
						}
					}
					message = message.replace(CMSConstants.TEMPLATE_COURSE_CODE,  courseApplied);
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getPassportNo() != null){
					message = message.replace(CMSConstants.TEMPLATE_PASSPORTNO, student.getAdmAppln().getPersonalData().getPassportNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_PASSPORTNO, "-------");
				}
				StringBuffer address = new StringBuffer();
				if(student.getAdmAppln()!= null && student.getAdmAppln().getPersonalData()!= null){
					if(student.getAdmAppln().getPersonalData().getPermanentAddressLine1()!= null && !student.getAdmAppln().getPersonalData().getPermanentAddressLine1().trim().isEmpty()){
						address.append(student.getAdmAppln().getPersonalData().getPermanentAddressLine1() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getPermanentAddressLine2()!= null && !student.getAdmAppln().getPersonalData().getPermanentAddressLine2().trim().isEmpty()){
						address.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+student.getAdmAppln().getPersonalData().getPermanentAddressLine2() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId()!= null && !student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId().trim().isEmpty()){
						address.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()!= null && student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName()!= null && !student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName().trim().isEmpty()){
						address.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getPermanentAddressStateOthers()!=null){
						address.append( student.getAdmAppln().getPersonalData().getPermanentAddressStateOthers() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId()!= null && student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName()!= null && !student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName().trim().isEmpty()){
						address.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getPermanentAddressCountryOthers()!=null){
						address.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getPermanentAddressCountryOthers() + "<BR>");
					}
					message = message.replace(CMSConstants.TEMPLATE_PERMANENT_ADDRESS, address.toString());
				}
				StringBuffer currentAddress = new StringBuffer();
				if(student.getAdmAppln()!= null && student.getAdmAppln().getPersonalData()!= null){
					if(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!= null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine1().trim().isEmpty()){
						currentAddress.append(student.getAdmAppln().getPersonalData().getCurrentAddressLine1() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!= null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine2().trim().isEmpty()){
						currentAddress.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCurrentAddressLine2() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId()!= null && !student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId().trim().isEmpty()){
						currentAddress.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!= null && student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()!= null && !student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName().trim().isEmpty()){
						currentAddress.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null){
						currentAddress.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId()!= null && student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName()!= null && !student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName().trim().isEmpty()){
						currentAddress.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getCurrentAddressCountryOthers()!=null){
						currentAddress.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCurrentAddressCountryOthers() + "<BR>");
					}
					message = message.replace(CMSConstants.TEMPLATE_CURRENT_ADDRESS, currentAddress.toString());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "he");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "she");
					}
				}
				messageList.add(message);
			   }
		   }
		return messageList;
	}
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @return
	 */
	public List<String> copyBosToListForAttemptCertificate(List<Student> stuList, HttpServletRequest request, CommonTemplateForm commonTemplateForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.TEMPLATE_ATTEMPTCERTIFICATE);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getAppliedYear() != null){
					String firstYear = String.valueOf(student.getAdmAppln().getAppliedYear());
					message = message.replace(CMSConstants.TEMPLATE_FIRST_YEAR ,firstYear );
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getCurriculumSchemeDuration() != null && student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null){
					String acedamicYear = String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
					String nextYear=""+String.valueOf(Integer.parseInt(acedamicYear)+1);
					message = message.replace(CMSConstants.TEMPLATE_LAST_YEAR ,nextYear );
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "He");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "She");
					}
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getTermNumber() != null){
					if( student.getClassSchemewise().getClasses().getTermNumber()== 1){
						message = message.replace(CMSConstants.TEMPLATE_SEMISTER,"I");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 2){
						message = message.replace(CMSConstants.TEMPLATE_SEMISTER,"II");
					}
				}
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,student.getRegisterNo() );
				}else{
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,"---");
				}
				if(student.getExamRegisterNo() != null && !student.getExamRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,student.getExamRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,"---");
				}
				if(commonTemplateForm.getExamDate() != null && !commonTemplateForm.getExamDate().isEmpty()){
					Date examDate = CommonUtil.ConvertStringToDate(commonTemplateForm.getExamDate());
					SimpleDateFormat formatter1 =  new SimpleDateFormat("MMMMMMMMM");
					String month = "";
					String examdate = "";
					if(examDate != null){
						month = formatter1.format(examDate);
						SimpleDateFormat formatter2 =  new SimpleDateFormat("yyyy");
						examdate = month +" "+formatter2.format(examDate);
					}
					message = message.replace(CMSConstants.TEMPLATE_EXAM_YEAR ,examdate);
				}
				if(commonTemplateForm.getAttempts() != null && !commonTemplateForm.getAttempts().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_NO_ATTEMPT, commonTemplateForm.getAttempts());
				}
				messageList.add(message);
			   }
		   }
		return messageList;
	}
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @param feePayment 
	 * @return
	 */
	public List<String> copyBosToListForFeeDetails(Student student,
			HttpServletRequest request, CommonTemplateForm commonTemplateForm, List<Object[]> feeList) throws Exception{
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.TEMPLATE_FEEDETAILS);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, student.getAdmAppln().getPersonalData().getFatherName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "S/o");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "D/o");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER1, "He");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER1, "She");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream() != null ){
						message = message.replace(CMSConstants.TEMPLATE_STREAM, student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream());
				}
			/*	if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getCode() != null){
					String className = "";
					if(student.getClassSchemewise().getClasses().getTermNumber()== 1){
						className = className + "I ";
					}
					if(student.getClassSchemewise().getClasses().getTermNumber()== 2){
						className = className + "II ";
					}
					className = className +student.getAdmAppln().getCourseBySelectedCourseId().getCode();
					message = message.replace(CMSConstants.TEMPLATE_CLASS, className);
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getTermNumber() != null){
					if( student.getClassSchemewise().getClasses().getTermNumber()== 1){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"one");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 2){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"two");
					}
				}*/
				if(commonTemplateForm.getAcademicYear() != null && !commonTemplateForm.getAcademicYear().isEmpty()){
					String acedamicYear = commonTemplateForm.getAcademicYear();
					String result="";
					String nextYear=""+String.valueOf(Integer.parseInt(acedamicYear)+1);
					result=acedamicYear+"-"+nextYear.substring(0);
					message =message.replace(CMSConstants.TEMPLATE_ACADEMICYEAR, result);
				}
				String s = "<table width = '100%'> <tr> <td width='20'> </td><td><table style='border:1px solid #000000; font-family: verdana; font-size:10pt;' rules='all'><tr> <th> PARTICULARS </th> <th> AMOUNT </th> </tr> ";
				if(feeList != null && !feeList.isEmpty()){
					Iterator<Object[]> feeIterator = feeList.iterator();
					int total = 0;
					String billNO = "";
					String paidDate = "";
					int feePaymentDetailId =0;
					int feePaymentDetailId1 =0;
					int concessionAmount = 0;
					while (feeIterator.hasNext()) {
						Object[] objects = (Object[]) feeIterator.next();
						if(objects[3] != null){
							StringTokenizer st2 = new StringTokenizer(objects[3].toString(), ".");
							int tempamount = Integer.parseInt(st2.nextToken());
							if(tempamount != 0){
								concessionAmount = tempamount;
							}
						}
						if(objects[6] != null && objects[9] != null){
							if(objects[6].toString().equalsIgnoreCase("Development Fee")){
								s = s + "<tr> <td> "+objects[6]+"* </td> <td align='right'>"+objects[9]+"</td> </tr>";
							}else{
								s = s + "<tr> <td> "+objects[6]+" </td> <td align='right'>"+objects[9]+"</td> </tr>";
							}
						}
						String totalFee = "";
						if(objects[9] !=null){
							totalFee  = objects[9].toString();
							StringTokenizer st = new StringTokenizer(totalFee, ".");
							total = total + Integer.parseInt(st.nextToken());
						}
						if(objects[0] != null){
							billNO = objects[0].toString();
						}
						if(objects[1] != null){
							paidDate = objects[1].toString();
						}
						
						if(objects[10] != null){
							if(feePaymentDetailId == 0){
								feePaymentDetailId = Integer.parseInt(objects[10].toString());
							}
							if(feePaymentDetailId != Integer.parseInt(objects[10].toString())){
								feePaymentDetailId1=Integer.parseInt(objects[10].toString());
							}
							
						}
						if(objects[11]!=null){
							String className = "";
							if(Integer.parseInt(objects[11].toString())== 1){
								className = className + "I ";
							}
							if(Integer.parseInt(objects[11].toString())== 2){
								className = className + "II ";
							}
							className = className +student.getAdmAppln().getCourseBySelectedCourseId().getCode();
							message = message.replace(CMSConstants.TEMPLATE_CLASS, className);
							
							if( Integer.parseInt(objects[11].toString())== 1){
								message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"one");
							}
							if(Integer.parseInt(objects[11].toString())== 2){
								message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"two");
							}
						}
					}
					List<FeePaymentDetailFeegroup> detailFeeGroup = CommonTemplateTransactionImpl.getInstance().getAdditionalFeeDetails(feePaymentDetailId,feePaymentDetailId1);
					if(detailFeeGroup != null){
						Iterator<FeePaymentDetailFeegroup> iterator = detailFeeGroup.iterator();
						while (iterator.hasNext()) {
							FeePaymentDetailFeegroup feePaymentDetailFeegroup = (FeePaymentDetailFeegroup) iterator.next();
							s = s + "<tr> <td> "+feePaymentDetailFeegroup.getFeeGroup().getName()+" </td> <td align='right'>"+feePaymentDetailFeegroup.getAmount()+"</td> </tr>";
							total = total + feePaymentDetailFeegroup.getAmount().intValue();
						}
					}
					String feePaid = String.valueOf(total);
					feePaid = CommonUtil.insertCommas(feePaid);
					int netTotal = total-concessionAmount;
					String netAmount = CommonUtil.insertCommas(String.valueOf(netTotal));
					s = s + "<tr> <th> TOTAL </th> <th>"+ feePaid +".00</th> </tr> ";
					if(concessionAmount != 0){
						s = s + "<tr> <td> Concession Amount </td> <td align='right'>"+ concessionAmount +".00</td> </tr>";
						s = s + "<tr> <th> Net Total </th> <th>"+ netAmount +".00</th> </tr></table> </td> </tr> </table>";
						message =message.replace(CMSConstants.TEMPLATE_TOTAL_FEE_PAID, netAmount+"/-");
					}else{
						s = s + "</table> </td> </tr> </table>";
						message =message.replace(CMSConstants.TEMPLATE_TOTAL_FEE_PAID, feePaid+"/-");
					}
					message =message.replace(CMSConstants.FEE_GROUPS, s);
					message =message.replace(CMSConstants.FEE_RECEIPTNO, billNO);
					if(paidDate != null && !paidDate.isEmpty()){
						Date feePaidDate = CommonUtil.ConvertStringToDate(paidDate);
						SimpleDateFormat formatter1 = new SimpleDateFormat("MMMMMMMMM dd, yyyy");
						String feeDate = formatter1.format(feePaidDate);
						message =message.replace(CMSConstants.TEMPLATE_FEE_DATE, feeDate);
					}
				}
				messageList.add(message);
		   }
		return messageList;
	}	
	
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @param feePayment
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToListForScholarship(List<Student> stuList,
			HttpServletRequest request, CommonTemplateForm commonTemplateForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.TEMPLATE_SCHOLARSHIP);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, student.getAdmAppln().getPersonalData().getFatherName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "S/o");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "D/o");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream() != null ){
						message = message.replace(CMSConstants.TEMPLATE_STREAM, student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getName() != null){
					message = message.replace(CMSConstants.TEMPLATE_CLASS, student.getClassSchemewise().getClasses().getName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null &&student.getAdmAppln().getCourseBySelectedCourseId().getCode() != null){
					message = message.replace(CMSConstants.TEMPLATE_COURSE,student.getAdmAppln().getCourseBySelectedCourseId().getCode());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getCurriculumSchemeDuration() != null && student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null){
					String acedamicYear = String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
					String result="";
					String nextYear=""+String.valueOf(Integer.parseInt(acedamicYear)+1);
					result=acedamicYear+" - "+nextYear.substring(0);
					message =message.replace(CMSConstants.TEMPLATE_ACADEMICYEAR, result);
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "he");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "she");
					}
				}
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,student.getRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,"---");
				}
				if(student.getExamRegisterNo() != null && !student.getExamRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,student.getExamRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,"---");
				}
				messageList.add(message);
			   }
		   }
		return messageList;
	}
	
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToListForMedium(List<Student> stuList,
			HttpServletRequest request, CommonTemplateForm commonTemplateForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.TEMPLATE_MEDIUM);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "S/o");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "D/o");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, student.getAdmAppln().getPersonalData().getFatherName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, student.getAdmAppln().getPersonalData().getFatherName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream() != null ){
						message = message.replace(CMSConstants.TEMPLATE_STREAM, student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getName() != null){
					message = message.replace(CMSConstants.TEMPLATE_CLASS, student.getAdmAppln().getCourseBySelectedCourseId().getName());
				}
				String firstYear="";
				if(student.getAdmAppln() != null && student.getAdmAppln().getAppliedYear() != null){
					firstYear = String.valueOf(student.getAdmAppln().getAppliedYear());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getCurriculumSchemeDuration() != null && student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null){
					String acedamicYear = String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
					String nextYear=""+String.valueOf(Integer.parseInt(acedamicYear)+1);
					message = message.replace(CMSConstants.TEMPLATE_ACADEMICYEAR ,firstYear+" - "+nextYear );
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "His");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "Her");
					}
				}
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,student.getRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,"---");
				}
				if(student.getExamRegisterNo() != null && !student.getExamRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,student.getExamRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,"---");
				}
				messageList.add(message);
			   }
		   }
		return messageList;
	}
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToListForSports(List<Student> stuList,
			HttpServletRequest request, CommonTemplateForm commonTemplateForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.TEMPLATE_SPORTS);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, student.getAdmAppln().getPersonalData().getFatherName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "S/o");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "D/o");
					}
				}
				String firstYear="";
				if(student.getAdmAppln() != null && student.getAdmAppln().getAppliedYear() != null){
					firstYear = String.valueOf(student.getAdmAppln().getAppliedYear());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getCurriculumSchemeDuration() != null && student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null){
					String acedamicYear = String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
					String nextYear=""+String.valueOf(Integer.parseInt(acedamicYear)+1);
					message = message.replace(CMSConstants.TEMPLATE_ACADEMIC_YEAR ,firstYear+" - "+nextYear );
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "he");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "she");
					}
				}
				if(commonTemplateForm.getSportsId() != null && !commonTemplateForm.getSportsId().isEmpty()){
					
					message = message.replace(CMSConstants.TEMPLATE_SPORTS_NAMES,commonTemplateForm.getSportsId());
				}
				messageList.add(message);
			   }
		   }
		return messageList;
	}
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToListForForeignNOC(List<Student> stuList,
			HttpServletRequest request, CommonTemplateForm commonTemplateForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.TEMPLATE_FOREIGN);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, student.getAdmAppln().getPersonalData().getFatherName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "S/o");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "D/o");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER1, "He");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER1, "She");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER_HIS, "His");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER_HIS, "Her");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER2, "his");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER2, "her");
					}
				}
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,student.getRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,"---");
				}
				if(student.getExamRegisterNo() != null && !student.getExamRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,student.getExamRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,"---");
				}
				if(commonTemplateForm.getStartDate() != null && !commonTemplateForm.getStartDate().isEmpty()){
					Date startDate = CommonUtil.ConvertStringToDate(commonTemplateForm.getStartDate());
					String sdate = formatter.format(startDate);
					message = message.replace(CMSConstants.TEMPLATE_START_DATE, sdate);
				}
				if(commonTemplateForm.getEndDate() != null && !commonTemplateForm.getEndDate().isEmpty()){
					Date endDate = CommonUtil.ConvertStringToDate(commonTemplateForm.getEndDate());
					String enddate = formatter.format(endDate);
					message = message.replace(CMSConstants.TEMPLATE_END_DATE, enddate);
				}
				if(commonTemplateForm.getEndDate() != null && !commonTemplateForm.getEndDate().isEmpty()){
					String endDate = commonTemplateForm.getEndDate();
					String nextDate = CommonUtil.getNextDate(endDate);
					Date nextdate = CommonUtil.ConvertStringToDate(nextDate);
					String nextday = formatter.format(nextdate);
					message = message.replace(CMSConstants.TEMPLATE_COLLEGE_OPENDATE, nextday);
				}
				messageList.add(message);
			   }
		   }
		return messageList;
	}
	
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToListForTutionFee(Student student,
			HttpServletRequest request, CommonTemplateForm commonTemplateForm, List<FeePaymentDetailFeegroup> feegroups, List<FeeAccountAssignment> feeAccountAssignments) throws Exception{
		
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.TEMPLATE_TUTIONFEE);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream() != null){
					message = message.replace(CMSConstants.TEMPLATE_STREAM, student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream());
				}
				if(commonTemplateForm.getAcademicYear() != null && !commonTemplateForm.getAcademicYear().isEmpty()){
					String acedamicYear = commonTemplateForm.getAcademicYear();
					String result="";
					String nextYear=""+String.valueOf(Integer.parseInt(acedamicYear)+1);
					result=acedamicYear+"-"+nextYear.substring(0);
					message =message.replace(CMSConstants.TEMPLATE_ACADEMICYEAR, result);
				}
				/*if(feegroups != null && !feegroups.isEmpty()){
					Iterator<FeePaymentDetailFeegroup> iterator = feegroups.iterator();
					while (iterator.hasNext()) {
						FeePaymentDetailFeegroup feePaymentDetailFeegroup = (FeePaymentDetailFeegroup) iterator.next();
						if(feePaymentDetailFeegroup.getFeeGroup().getName().equalsIgnoreCase("Tuition Fee") && feePaymentDetailFeegroup.getFeeGroup().getName() != null && feePaymentDetailFeegroup.getAmount() != null){
							BigDecimal tuitionFee = feePaymentDetailFeegroup.getAmount();
							if(tuitionFee != null ){
								StringBuffer fee = new StringBuffer(tuitionFee.toString());
								message = message.replace(CMSConstants.TUITION_FEE, fee);
								fee.reverse();
								fee.replace(0, 3, "");
								fee.reverse();
								int tuFee = Integer.parseInt(fee.toString());
								String feeInWords = CommonUtil.numberToWord(tuFee);
								message = message.replace(CMSConstants.FEE_INWORDS, feeInWords);
							}
						}
					}
					
				}*/
				if(feeAccountAssignments != null && !feeAccountAssignments.isEmpty()){
					Iterator<FeeAccountAssignment> iterator = feeAccountAssignments.iterator();
					while (iterator.hasNext()) {
						FeeAccountAssignment feeAccountAssignment = (FeeAccountAssignment) iterator.next();
						BigDecimal tuitionFee = feeAccountAssignment.getAmount();
						if(tuitionFee != null ){
							StringBuffer fee = new StringBuffer(tuitionFee.toString());
							message = message.replace(CMSConstants.TUITION_FEE, fee);
							StringTokenizer fee1 = new StringTokenizer(fee.toString() , ".");
							int tuFee = Integer.parseInt(fee1.nextToken());
							String feeInWords = CommonUtil.numberToWord1(tuFee);
							message = message.replace(CMSConstants.FEE_INWORDS, feeInWords);
						}
					}
				}
					
				messageList.add(message);
		   }
		return messageList;
	}
	/**
	 * @param programbolist
	 * @return
	 */
	public static List<SportsTO> convertBOstoTos(List<Sports> sportsbolist)  throws Exception{
		List<SportsTO> sportsList = new ArrayList<SportsTO>();
		if (sportsbolist != null) {
			Iterator<Sports> iterator = sportsbolist.iterator();
			while (iterator.hasNext()) {
				Sports sports = (Sports) iterator.next();
				SportsTO sportsTO = new SportsTO();
				sportsTO.setId(sports.getId());
				sportsTO.setName(sports.getName());
				sportsList.add(sportsTO);
			}
			
		}
		return sportsList;
	}
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToListForMarkTranscript(Student student,
			HttpServletRequest request, CommonTemplateForm commonTemplateForm, List<Object[]> marksDetails) throws Exception{
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.EMPLATE_MARKTRANSCRIPT_ONE);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, WordUtils.capitalize(student.getAdmAppln().getPersonalData().getFirstName().toLowerCase()));
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, WordUtils.capitalize(student.getAdmAppln().getPersonalData().getFatherName().toLowerCase()));
				}
				if(student.getAdmAppln() != null && student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getTermNumber() != null){
					String className = null;
					if( student.getClassSchemewise().getClasses().getTermNumber() == 1){
						className = "I PUC";
					}
					if( student.getClassSchemewise().getClasses().getTermNumber() == 2){
						className = "II PUC";
					}
					message = message.replace(CMSConstants.TEMPLATE_CLASS, className);
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null &&student.getAdmAppln().getCourseBySelectedCourseId().getCode() != null){
					message = message.replace(CMSConstants.TEMPLATE_COURSE,student.getAdmAppln().getCourseBySelectedCourseId().getCode());
				}
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,student.getRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,"---");
				}
				if(student.getExamRegisterNo() != null && !student.getExamRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,student.getExamRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,"---");
				}
				String s = "";
				
				if(marksDetails != null && !marksDetails.isEmpty()){
					s = s+"<table style='border:1px solid #000000' rules='all'> <tr> <th> Subject </th> <th> Maximum Marks </th> <th colspan='2' width = '20px'> Marks obtained </th></tr> ";
					s = s+"</tr> <tr> <td> </td> <td> </td> <th> In figures </th> <th> In words </th> </tr>";
					Iterator<Object[]> iterator = marksDetails.iterator();
					s = s+"<tr><td> Part I </td></tr>";
					int totalMaxMarks = 0;
					int totalObtainedMarks = 0;
					int month = 0;
					String result = "";
					int minMark = 0;
					List<Integer> monthList = new ArrayList<Integer>();
					List<Integer> yearList = new ArrayList<Integer>();
					while (iterator.hasNext()) {
						Object[] objects = (Object[]) iterator.next();
						if(objects != null){
							String monthNo = objects[3].toString();
							StringTokenizer monthNo1 = new StringTokenizer(monthNo,".");
							month = Integer.parseInt(monthNo1.nextToken());
							monthList.add(month);
							yearList.add(Integer.parseInt(objects[2].toString()));
						}
						if(objects != null && objects[5].equals("Part1")){
							int maxMarks = 0;
							int marksObtained = 0;
							String marks = "";
							if(objects[7] != null){
								marks =objects[7].toString();
								StringTokenizer marks1 = new StringTokenizer(marks , ".");
								maxMarks = maxMarks + Integer.parseInt(marks1.nextToken());
							}
							if(objects[8] != null){
								String minmarks = objects[6].toString();
								StringTokenizer minmarks1 = new StringTokenizer(minmarks , ".");
								minMark = Integer.parseInt(minmarks1.nextToken());
							}
							
							totalMaxMarks = totalMaxMarks +maxMarks;
							if(objects[6] != null){
								String marksobtained = objects[6].toString();
								StringTokenizer marksobtained1 = new StringTokenizer(marksobtained , ".");
								String marksObtain = marksobtained1.nextToken();
								if(CommonUtil.isStringContainsNumbers(marksObtain)){
									marksObtained = Integer.parseInt(marksObtain);
									totalObtainedMarks = totalObtainedMarks+marksObtained;
									String marksInWords = CommonUtil.numberToWord1(marksObtained);
									s = s+"<tr><td>"+objects[4] +"</td> <td align='center'> "+maxMarks+"</td> <td align='center'>"+marksObtained+"</td><td>"+marksInWords+"</tr>";
								}else{
									s = s+"<tr><td>"+objects[4] +"</td> <td align='center'> "+maxMarks+"</td> <td align='center'>"+objects[6].toString()+"</td><td>---</tr>";
								}
							}
							if(marksObtained < minMark){
								result = "Fail";
							}
						}
					}
					Iterator<Object[]> iterator1 = marksDetails.iterator();
					s = s+"<tr><td> Part II </td></tr>";
					while (iterator1.hasNext()) {
						Object[] objects = (Object[]) iterator1.next();
						if(objects != null && objects[5].equals("Part2")){
							int maxMarks = 0;
							int marksObtained = 0;
							String marks ="";
							if(objects[7] != null){
								marks = objects[7].toString();
								StringTokenizer marks1 = new StringTokenizer(marks , ".");
								maxMarks = maxMarks + Integer.parseInt(marks1.nextToken());
							}
							if(objects[6] != null){
								String marksobtained =objects[6].toString();
								StringTokenizer marksobtained1 = new StringTokenizer(marksobtained , ".");
								marksObtained = Integer.parseInt(marksobtained1.nextToken());
							}
							if(objects[8] != null){
								String minmarks = objects[6].toString();
								StringTokenizer minmarks1 = new StringTokenizer(minmarks , ".");
								minMark = Integer.parseInt(minmarks1.nextToken());
							}
							
							totalMaxMarks = totalMaxMarks + maxMarks;
							if(objects[6] != null){
								String marksobtained = objects[6].toString();
								StringTokenizer marksobtained1 = new StringTokenizer(marksobtained , ".");
								String marksObtain = marksobtained1.nextToken();
								if(CommonUtil.isStringContainsNumbers(marksObtain)){
									marksObtained = Integer.parseInt(marksObtain);
									totalObtainedMarks = totalObtainedMarks+marksObtained;
									String marksInWords = CommonUtil.numberToWord1(marksObtained);
									s = s+"<tr><td>"+objects[4] +"</td> <td align='center'> "+maxMarks+"</td> <td align='center'>"+marksObtained+"</td><td>"+marksInWords+"</tr>";
								}else{
									s = s+"<tr><td>"+objects[4] +"</td> <td align='center'> "+maxMarks+"</td> <td align='center'>"+objects[6].toString()+"</td><td>---</tr>";
								}
							}
							if(marksObtained < minMark){
								result = "Fail";
							}
						}
					}
					s = s +"<tr><th> Grand Total</th> <th>"+totalMaxMarks+" </th> <th> "+totalObtainedMarks+"</th><th>"+CommonUtil.numberToWord1(totalObtainedMarks)+"</th></tr></table>";
					message = message.replace(CMSConstants.TEMPLATE_MARKSHEET, s);
					Iterator<Integer> monthIterator = monthList.iterator();
					int monthNO = 0;
					int bigMonthNo = 0;
					while (monthIterator.hasNext()) {
						Integer integer = (Integer) monthIterator.next();
						if(monthNO < integer){
							bigMonthNo = integer;
						}
						monthNO = integer;
					}
					int yearNO = 0;
					int bigYearNo = 0;
					if(yearList != null && !yearList.isEmpty()){
						Iterator<Integer> yearIterator = yearList.iterator();
						
						while (yearIterator.hasNext()) {
							Integer integer = (Integer) yearIterator.next();
							if(yearNO < integer){
								bigYearNo = integer;
							}
							yearNO = integer;
						}
					}
					
					String monthAndYear = CommonUtil.getMonthForNumber(bigMonthNo);
					monthAndYear = WordUtils.capitalize(monthAndYear.toLowerCase()) +" "+ bigYearNo;
					message = message.replace(CMSConstants.TEMPLATE_EXAM_YEAR, monthAndYear);
					if(result != null && result.equalsIgnoreCase("Fail")){
						message = message.replace(CMSConstants.TEMPLATE_RESULT, "Fail");
					}else if(totalObtainedMarks >=510){
						message = message.replace(CMSConstants.TEMPLATE_RESULT, "Distinction");
					}else if(totalObtainedMarks <=509 && totalObtainedMarks >=360){
						message = message.replace(CMSConstants.TEMPLATE_RESULT, "First Class");
					}else if(totalObtainedMarks < 360 && totalObtainedMarks >=300){
						message = message.replace(CMSConstants.TEMPLATE_RESULT, "Second Class");
					}else{
						message = message.replace(CMSConstants.TEMPLATE_RESULT, "Third Class");
					}
				}
				messageList.add(message);
		   }
		return messageList;
	}
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @return
	 * @throws Exception 
	 */
	public List<String> copyBosToListForAddressAndDOB(List<Student> stuList,
			HttpServletRequest request, CommonTemplateForm commonTemplateForm) throws Exception {
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.ADDRESS_AND_DOB_TEMPLATE);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln()!=null && student.getAdmAppln().getPersonalData()!=null){
					if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
						message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getDateOfBirth() != null){
					Date dateOfBirth= student.getAdmAppln().getPersonalData().getDateOfBirth();
					String dob = new SimpleDateFormat("dd/MM/yyyy").format(dateOfBirth);
					message = message.replace(CMSConstants.TEMPLATE_DOB, dob);
				}
				StringBuffer address = new StringBuffer();
				if(student.getAdmAppln()!= null && student.getAdmAppln().getPersonalData()!= null){
					if(student.getAdmAppln().getPersonalData().getPermanentAddressLine1()!= null && !student.getAdmAppln().getPersonalData().getPermanentAddressLine1().trim().isEmpty()){
						address.append(student.getAdmAppln().getPersonalData().getPermanentAddressLine1() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getPermanentAddressLine2()!= null && !student.getAdmAppln().getPersonalData().getPermanentAddressLine2().trim().isEmpty()){
						address.append( student.getAdmAppln().getPersonalData().getPermanentAddressLine2() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId()!= null && !student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId().trim().isEmpty()){
						address.append( student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()!= null && student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName()!= null && !student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName().trim().isEmpty()){
						address.append( student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getPermanentAddressStateOthers()!=null){
						address.append( student.getAdmAppln().getPersonalData().getPermanentAddressStateOthers() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId()!= null && student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName()!= null && !student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName().trim().isEmpty()){
						address.append( student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getPermanentAddressCountryOthers()!=null){
						address.append( student.getAdmAppln().getPersonalData().getPermanentAddressCountryOthers() + "<BR>");
					}
					message = message.replace(CMSConstants.TEMPLATE_PERMANENT_ADDRESS, address.toString());
				}
				StringBuffer currentAddress = new StringBuffer();
				if(student.getAdmAppln()!= null && student.getAdmAppln().getPersonalData()!= null){
					if(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!= null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine1().trim().isEmpty()){
						currentAddress.append(student.getAdmAppln().getPersonalData().getCurrentAddressLine1() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!= null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine2().trim().isEmpty()){
						currentAddress.append( student.getAdmAppln().getPersonalData().getCurrentAddressLine2() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId()!= null && !student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId().trim().isEmpty()){
						currentAddress.append( student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!= null && student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()!= null && !student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName().trim().isEmpty()){
						currentAddress.append( student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null){
							currentAddress.append( student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId()!= null && student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName()!= null && !student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName().trim().isEmpty()){
						currentAddress.append( student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getCurrentAddressCountryOthers()!=null){
						currentAddress.append( student.getAdmAppln().getPersonalData().getCurrentAddressCountryOthers() + "<BR>");
					}
					message = message.replace(CMSConstants.TEMPLATE_CURRENT_ADDRESS, currentAddress.toString());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getCurriculumSchemeDuration() != null && student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null){
					String acedamicYear = String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
					String result="";
					String nextYear=""+String.valueOf(Integer.parseInt(acedamicYear)+1);
					result=acedamicYear+" - "+nextYear.substring(0);
					message =message.replace(CMSConstants.TEMPLATE_ACADEMICYEAR, result);
				  }
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream() != null){
					message = message.replace(CMSConstants.TEMPLATE_STREAM, student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream());
				}
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,student.getRegisterNo() );
				}else{
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,"---");
				}
				if(student.getExamRegisterNo() != null && !student.getExamRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,student.getExamRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,"---");
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getName() != null){
					message = message.replace(CMSConstants.TEMPLATE_CLASS, student.getClassSchemewise().getClasses().getName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getName() != null){
					message = message.replace(CMSConstants.TEMPLATE_COURSE, student.getAdmAppln().getCourseBySelectedCourseId().getName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER_HIS, "His");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER_HIS, "Her");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER2, "his");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER2, "her");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER_HE, "He");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER_HE, "She");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER_HE_SMALL, "he");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER_HE_SMALL, "she");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, WordUtils.capitalize(student.getAdmAppln().getPersonalData().getFatherName().toLowerCase()));
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "S/o");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "D/o");
					}
				}
				messageList.add(message);
			}
		}
		return messageList;
	}
	/**
	 * @param student
	 * @param commonTemplateForm
	 * @param marksDetails 
	 * @return
	 * @throws Exception 
	 */
	public List<String> copyBosToListForSecondPUMarkTranscript(Student student,
			CommonTemplateForm commonTemplateForm, List<Object[]> marksDetails) throws Exception {

		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.EMPLATE_MARKTRANSCRIPT_TWO);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, WordUtils.capitalize(student.getAdmAppln().getPersonalData().getFirstName().toLowerCase()));
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, WordUtils.capitalize(student.getAdmAppln().getPersonalData().getFatherName().toLowerCase()));
				}
				if(student.getAdmAppln() != null && student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getTermNumber() != null){
					String className = null;
					if( student.getClassSchemewise().getClasses().getTermNumber() == 1){
						className = "I PUC";
					}
					if( student.getClassSchemewise().getClasses().getTermNumber() == 2){
						className = "II PUC";
					}
					message = message.replace(CMSConstants.TEMPLATE_CLASS, className);
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null &&student.getAdmAppln().getCourseBySelectedCourseId().getCode() != null){
					message = message.replace(CMSConstants.TEMPLATE_COURSE,student.getAdmAppln().getCourseBySelectedCourseId().getCode());
				}
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,student.getRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,"---");
				}
				if(commonTemplateForm.getExamRegNo() != null && !commonTemplateForm.getExamRegNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,commonTemplateForm.getExamRegNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,"---");
				}
				String s = "";
				
				List<CommonTemplateTO> templateTOs = new ArrayList<CommonTemplateTO>();
				if(marksDetails != null && !marksDetails.isEmpty()){
					Iterator<Object[]> listIterator = marksDetails.iterator();
					while (listIterator.hasNext()) {
						CommonTemplateTO commonTemplateTO = new CommonTemplateTO();
						Object[] objects = (Object[]) listIterator.next();
						commonTemplateTO.setSubjectName(objects[4].toString());
						commonTemplateTO.setSubjectPart(objects[5].toString());
						String maxMarks = objects[7].toString();
						StringTokenizer st = new StringTokenizer(maxMarks, ".");
						commonTemplateTO.setMaxMarks(st.nextToken());
						templateTOs.add(commonTemplateTO);
					}
				}
				int count = 1;
				List<CommonTemplateTO> commonTemplateTOs = new ArrayList<CommonTemplateTO>();
				if(templateTOs != null && !templateTOs.isEmpty()){
					Iterator<CommonTemplateTO> iterator= templateTOs.iterator();
					while (iterator.hasNext()) {
						CommonTemplateTO commonTemplateTO2 = (CommonTemplateTO) iterator.next();
						
						switch (count) {
						case 1:
							commonTemplateTO2.setMarks(commonTemplateForm.getSubjectone());
							break;
						case 2:
							commonTemplateTO2.setMarks(commonTemplateForm.getSubjecttwo());
							break;	
						case 3:
							commonTemplateTO2.setMarks(commonTemplateForm.getSubjectthree());
							break;
						case 4:
							commonTemplateTO2.setMarks(commonTemplateForm.getSubjectfore());
							break;
						case 5:
							commonTemplateTO2.setMarks(commonTemplateForm.getSubjectfive());
							break;
						case 6:
							commonTemplateTO2.setMarks(commonTemplateForm.getSubjectsix());
							break;

						default:
							break;
						}
						count++;
						commonTemplateTOs.add(commonTemplateTO2);
					}
				}
				s = s+"<table style='border:1px solid #000000' rules='all'> <tr> <th> Subject </th> <th> Maximum Marks </th> <th colspan='2' width = '20px'> Marks obtained </th></tr> ";
				s = s+"</tr> <tr> <td> </td> <td> </td> <th> In figures </td> <th> In words </td> </tr>";
				s = s+"<tr><td> Part I </td></tr>";
				if(commonTemplateTOs != null && !commonTemplateTOs.isEmpty()){
					Iterator<CommonTemplateTO> commonIterator = commonTemplateTOs.iterator();
					while (commonIterator.hasNext()) {
						CommonTemplateTO commonTemplateTO = (CommonTemplateTO) commonIterator.next();
						if(commonTemplateTO.getSubjectPart().equalsIgnoreCase("Part1")){
							if(commonTemplateTO.getMarks() != null && !commonTemplateTO.getMarks().isEmpty() && commonTemplateTO.getSubjectName() != null && !commonTemplateTO.getSubjectName().isEmpty()){
								if(CommonUtil.isStringContainsNumbers(commonTemplateTO.getMarks())){
									s = s+"<tr><td>"+commonTemplateTO.getSubjectName()+"</td> <td align='center'> "+commonTemplateTO.getMaxMarks()+"</td> <td align='center'>"+commonTemplateTO.getMarks()+"</td><td>"+CommonUtil.numberToWord1(Integer.parseInt(commonTemplateTO.getMarks()))+"</tr>";
								}else{
									s = s+"<tr><td>"+commonTemplateTO.getSubjectName()+"</td> <td align='center'>"+commonTemplateTO.getMaxMarks()+"</td> <td align='center'>"+commonTemplateTO.getMarks()+"</td><td>----</td></tr>";
								}
							}
						}
					}
					Iterator<CommonTemplateTO> commonIterator1 = commonTemplateTOs.iterator();
					s = s+"<tr><td> Part II </td></tr>";
					while (commonIterator1.hasNext()) {
						CommonTemplateTO commonTemplateTO = (CommonTemplateTO) commonIterator1.next();
						if(commonTemplateTO.getSubjectPart().equalsIgnoreCase("Part2")){
							if(commonTemplateTO.getMarks() != null && !commonTemplateTO.getMarks().isEmpty() && commonTemplateTO.getSubjectName() != null && !commonTemplateTO.getSubjectName().isEmpty()){
								if(CommonUtil.isStringContainsNumbers(commonTemplateTO.getMarks())){
									s = s+"<tr><td>"+commonTemplateTO.getSubjectName()+"</td> <td align='center'> "+commonTemplateTO.getMaxMarks()+"</td> <td align='center'>"+commonTemplateTO.getMarks()+"</td><td>"+CommonUtil.numberToWord1(Integer.parseInt(commonTemplateTO.getMarks()))+"</tr>";
								}else{
									s = s+"<tr><td>"+commonTemplateTO.getSubjectName()+"</td> <td align='center'>"+commonTemplateTO.getMaxMarks()+"</td> <td align='center'>"+commonTemplateTO.getMarks()+"</td><td>----</td></tr>";
								}
							}
						}
					}
					int totalMaxMarks = 0;
					int totalObtainedMarks = 0;
					Iterator<CommonTemplateTO> commonIterator2 = commonTemplateTOs.iterator();
					while (commonIterator2.hasNext()) {
						CommonTemplateTO commonTemplateTO = (CommonTemplateTO) commonIterator2.next();
						if(commonTemplateTO.getMaxMarks() != null && !commonTemplateTO.getMaxMarks().isEmpty()){
							totalMaxMarks = totalMaxMarks + Integer.parseInt(commonTemplateTO.getMaxMarks());
						}
						if(commonTemplateTO.getMarks() != null && !commonTemplateTO.getMarks().isEmpty()){
							if(CommonUtil.isStringContainsNumbers(commonTemplateTO.getMarks())){
								totalObtainedMarks = totalObtainedMarks + Integer.parseInt(commonTemplateTO.getMarks());
							}
						}
					}
					s = s +"<tr><th> Grand Total</th> <th>"+totalMaxMarks+" </th> <th> "+totalObtainedMarks+"</th><th>"+CommonUtil.numberToWord1(totalObtainedMarks)+"</th></tr></table>";
				}
				if(commonTemplateForm.getExamYear() != null && !commonTemplateForm.getExamYear().isEmpty()){
					Date examDate = CommonUtil.ConvertStringToSQLDate(commonTemplateForm.getExamYear());
					SimpleDateFormat formatter1 =  new SimpleDateFormat("MMMMMMMMM");
					String month = "";
					String examdate = "";
					if(examDate != null){
						month = formatter1.format(examDate);
						SimpleDateFormat formatter2 =  new SimpleDateFormat("yyyy");
						examdate = WordUtils.capitalize(month.toLowerCase()) +" "+formatter2.format(examDate);
					}
					message = message.replace(CMSConstants.TEMPLATE_EXAM_YEAR ,examdate);
				}
				message = message.replace(CMSConstants.TEMPLATE_MARKSHEET, s);
				message = message.replace(CMSConstants.TEMPLATE_RESULT, WordUtils.capitalize(commonTemplateForm.getResult().toLowerCase()));
				messageList.add(message);
		   }
		return messageList;
	}
	
	/**
	 * @param student
	 * @param commonTemplateForm
	 * @param marksDetails
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToListForPUMarkTranscript(Student student,
			CommonTemplateForm commonTemplateForm, List<Object[]> marksDetails) throws Exception {

		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.EMPLATE_MARKTRANSCRIPT_ONE_AND_TWO);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, WordUtils.capitalize(student.getAdmAppln().getPersonalData().getFirstName().toLowerCase()));
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, WordUtils.capitalize(student.getAdmAppln().getPersonalData().getFatherName().toLowerCase()));
				}
				if(student.getAdmAppln() != null && student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getTermNumber() != null){
					String className = null;
					if( student.getClassSchemewise().getClasses().getTermNumber() == 1){
						className = "I PUC";
					}
					if( student.getClassSchemewise().getClasses().getTermNumber() == 2){
						className = "II PUC";
					}
					message = message.replace(CMSConstants.TEMPLATE_CLASS, className);
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null &&student.getAdmAppln().getCourseBySelectedCourseId().getCode() != null){
					message = message.replace(CMSConstants.TEMPLATE_COURSE,student.getAdmAppln().getCourseBySelectedCourseId().getCode());
				}
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,student.getRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,"---");
				}
				if(commonTemplateForm.getExamRegNo() != null && !commonTemplateForm.getExamRegNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,commonTemplateForm.getExamRegNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,"---");
				}
				String s = "";
				int month = 0;
				List<CommonTemplateTO> templateTOs = new ArrayList<CommonTemplateTO>();
				List<Integer> monthList = new ArrayList<Integer>();
				List<Integer> yearList = new ArrayList<Integer>();
				if(marksDetails != null && !marksDetails.isEmpty()){
					Iterator<Object[]> listIterator = marksDetails.iterator();
					while (listIterator.hasNext()) {
						CommonTemplateTO commonTemplateTO = new CommonTemplateTO();
						Object[] objects = (Object[]) listIterator.next();
						if(objects != null){
							String monthNo = objects[3].toString();
							StringTokenizer monthNo1 = new StringTokenizer(monthNo,".");
							month = Integer.parseInt(monthNo1.nextToken());
							monthList.add(month);
							yearList.add(Integer.parseInt(objects[2].toString()));
						}
						commonTemplateTO.setSubjectName(objects[4].toString());
						commonTemplateTO.setSubjectPart(objects[5].toString());
						String maxMarks = objects[7].toString();
						StringTokenizer st = new StringTokenizer(maxMarks, ".");
						commonTemplateTO.setMaxMarks(st.nextToken());
						String minMarks = objects[8].toString();
						StringTokenizer st1 = new StringTokenizer(minMarks, ".");
						commonTemplateTO.setMinMarks(st1.nextToken());
						String firstYearMarks = objects[6].toString();
						StringTokenizer st2 = new StringTokenizer(firstYearMarks, ".");
						commonTemplateTO.setFirstYearMarks(st2.nextToken());
						templateTOs.add(commonTemplateTO);
					}
					int count = 1;
					List<CommonTemplateTO> commonTemplateTOs = new ArrayList<CommonTemplateTO>();
					if(templateTOs != null && !templateTOs.isEmpty()){
						Iterator<CommonTemplateTO> iterator= templateTOs.iterator();
						while (iterator.hasNext()) {
							CommonTemplateTO commonTemplateTO2 = (CommonTemplateTO) iterator.next();
							
							switch (count) {
							case 1:
								commonTemplateTO2.setMarks(commonTemplateForm.getSubjectone());
								break;
							case 2:
								commonTemplateTO2.setMarks(commonTemplateForm.getSubjecttwo());
								break;	
							case 3:
								commonTemplateTO2.setMarks(commonTemplateForm.getSubjectthree());
								break;
							case 4:
								commonTemplateTO2.setMarks(commonTemplateForm.getSubjectfore());
								break;
							case 5:
								commonTemplateTO2.setMarks(commonTemplateForm.getSubjectfive());
								break;
							case 6:
								commonTemplateTO2.setMarks(commonTemplateForm.getSubjectsix());
								break;
								
							default:
								break;
							}
							count++;
							commonTemplateTOs.add(commonTemplateTO2);
						}
					}
					s = s+"<table width = '100%'> <tr> <td width='20'> </td> <td><table style='border:1px solid #000000' rules='all' width='90%'> <tr> <th> Subject </th> <th> Maximum Marks </th> <th colspan='2' width = '20px' align = 'center'> Marks obtained </th></tr> ";
					s = s+"</tr> <tr> <td> </td> <td> </td> <th colspan='2'> I YEAR </th> <th colspan='2' > II YEAR </th> </tr>";
					s = s+"</tr> <tr> <td> </td> <td> </td> <th> In figures </th> <th> In words </th> <th> In figures </th> <th> In words </th></tr>"; 
					s = s+"<tr><td> Part I </td></tr>";
					if(commonTemplateTOs != null && !commonTemplateTOs.isEmpty()){
						Iterator<CommonTemplateTO> commonIterator = commonTemplateTOs.iterator();
						while (commonIterator.hasNext()) {
							CommonTemplateTO commonTemplateTO = (CommonTemplateTO) commonIterator.next();
							if(commonTemplateTO.getSubjectPart().equalsIgnoreCase("Part1")){
								if(commonTemplateTO.getMarks() != null && !commonTemplateTO.getMarks().isEmpty() && commonTemplateTO.getSubjectName() != null && !commonTemplateTO.getSubjectName().isEmpty()){
									
									if(CommonUtil.isStringContainsNumbers(commonTemplateTO.getFirstYearMarks())){
										s = s+"<tr><td>"+commonTemplateTO.getSubjectName()+"</td> <td align='center'> "+commonTemplateTO.getMaxMarks()+"</td> <td align='center'>"+commonTemplateTO.getFirstYearMarks()+"</td><td>"+CommonUtil.numberToWord1(Integer.parseInt(commonTemplateTO.getFirstYearMarks()));
									}else{
										s = s+"<tr><td>"+commonTemplateTO.getSubjectName()+"</td> <td align='center'>"+commonTemplateTO.getMaxMarks()+"</td> <td align='center'>"+commonTemplateTO.getFirstYearMarks()+"</td><td>----</td>";
									}
									
									if(CommonUtil.isStringContainsNumbers(commonTemplateTO.getMarks())){
										s = s+"<td align='center'>"+commonTemplateTO.getMarks()+"</td><td>"+CommonUtil.numberToWord1(Integer.parseInt(commonTemplateTO.getMarks()))+"</td></tr>";
									}else{
										s = s+"</td> <td align='center'>"+commonTemplateTO.getMarks()+"</td><td>----</td></tr>";
									}
								}
							}
						}
						Iterator<CommonTemplateTO> commonIterator1 = commonTemplateTOs.iterator();
						s = s+"<tr><td> Part II </td></tr>";
						while (commonIterator1.hasNext()) {
							CommonTemplateTO commonTemplateTO = (CommonTemplateTO) commonIterator1.next();
							if(commonTemplateTO.getSubjectPart().equalsIgnoreCase("Part2")){
								if(commonTemplateTO.getMarks() != null && !commonTemplateTO.getMarks().isEmpty() && commonTemplateTO.getSubjectName() != null && !commonTemplateTO.getSubjectName().isEmpty()){
									if(CommonUtil.isStringContainsNumbers(commonTemplateTO.getFirstYearMarks())){
										s = s+"<tr><td>"+commonTemplateTO.getSubjectName()+"</td> <td align='center'> "+commonTemplateTO.getMaxMarks()+"</td> <td align='center'>"+commonTemplateTO.getFirstYearMarks()+"</td><td>"+CommonUtil.numberToWord1(Integer.parseInt(commonTemplateTO.getFirstYearMarks()));
									}else{
										s = s+"<tr><td>"+commonTemplateTO.getSubjectName()+"</td> <td align='center'>"+commonTemplateTO.getMaxMarks()+"</td> <td align='center'>"+commonTemplateTO.getFirstYearMarks()+"</td><td>----</td>";
									}
									
									if(CommonUtil.isStringContainsNumbers(commonTemplateTO.getMarks())){
										s = s+"<td align='center'>"+commonTemplateTO.getMarks()+"</td><td>"+CommonUtil.numberToWord1(Integer.parseInt(commonTemplateTO.getMarks()))+"</td></tr>";
									}else{
										s = s+"</td> <td align='center'>"+commonTemplateTO.getMarks()+"</td><td>----</td></tr>";
									}
								}
							}
						}
						int totalMaxMarks = 0;
						int totalObtainedMarks1 = 0;
						int totalObtainedMarks2 = 0;
						Iterator<CommonTemplateTO> commonIterator2 = commonTemplateTOs.iterator();
						while (commonIterator2.hasNext()) {
							CommonTemplateTO commonTemplateTO = (CommonTemplateTO) commonIterator2.next();
							if(commonTemplateTO.getMaxMarks() != null && !commonTemplateTO.getMaxMarks().isEmpty()){
								totalMaxMarks = totalMaxMarks + Integer.parseInt(commonTemplateTO.getMaxMarks());
							}
							if(commonTemplateTO.getFirstYearMarks() != null && !commonTemplateTO.getFirstYearMarks().isEmpty()){
								if(CommonUtil.isStringContainsNumbers(commonTemplateTO.getFirstYearMarks())){
									totalObtainedMarks1 = totalObtainedMarks1 + Integer.parseInt(commonTemplateTO.getFirstYearMarks());
								}
							}
							if(commonTemplateTO.getMarks() != null && !commonTemplateTO.getMarks().isEmpty()){
								if(CommonUtil.isStringContainsNumbers(commonTemplateTO.getMarks())){
									totalObtainedMarks2 = totalObtainedMarks2 + Integer.parseInt(commonTemplateTO.getMarks());
								}
							}
						}
						s = s +"<tr><th> Grand Total</th> <th>"+totalMaxMarks+" </th> <th> "+totalObtainedMarks1+"</th><th>"+CommonUtil.numberToWord1(totalObtainedMarks1)+" </th> <th> "+totalObtainedMarks2+"</th><th>"+CommonUtil.numberToWord1(totalObtainedMarks2)+"</th></tr></table></td></tr></table>";
					}
					Iterator<Integer> monthIterator = monthList.iterator();
					int monthNO = 0;
					int bigMonthNo = 0;
					while (monthIterator.hasNext()) {
						Integer integer = (Integer) monthIterator.next();
						if(monthNO < integer){
							bigMonthNo = integer;
						}
						monthNO = integer;
					}
					int yearNO = 0;
					int bigYearNo = 0;
					if(yearList != null && !yearList.isEmpty()){
						Iterator<Integer> yearIterator = yearList.iterator();
						
						while (yearIterator.hasNext()) {
							Integer integer = (Integer) yearIterator.next();
							if(yearNO < integer){
								bigYearNo = integer;
							}
							yearNO = integer;
						}
					}
					String monthAndYear = WordUtils.capitalize(CommonUtil.getMonthForNumber(bigMonthNo).toLowerCase());
					monthAndYear = monthAndYear +" "+ bigYearNo;
					if(commonTemplateForm.getExamYear() != null && !commonTemplateForm.getExamYear().isEmpty()){
						Date examDate = CommonUtil.ConvertStringToSQLDate(commonTemplateForm.getExamYear());
						SimpleDateFormat formatter1 =  new SimpleDateFormat("MMMMMMMMM");
						String month1 = "";
						String examdate = "";
						if(examDate != null){
							month1 = formatter1.format(examDate);
							SimpleDateFormat formatter2 =  new SimpleDateFormat("yyyy");
							examdate = WordUtils.capitalize(month1.toLowerCase()) +" "+formatter2.format(examDate);
						}
						monthAndYear = monthAndYear +" & "+ examdate;
						message = message.replace(CMSConstants.TEMPLATE_EXAM_YEAR ,monthAndYear);
					}
				}
				message = message.replace(CMSConstants.TEMPLATE_MARKSHEET, s);
				message = message.replace(CMSConstants.TEMPLATE_RESULT, WordUtils.capitalize(commonTemplateForm.getResult().toLowerCase()));
				messageList.add(message);
		   }
		return messageList;
	}
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToListForNCC(List<Student> stuList,
			HttpServletRequest request, CommonTemplateForm commonTemplateForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.TEMPLATE_NCC);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, student.getAdmAppln().getPersonalData().getFatherName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "S/o");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "D/o");
					}
				}
				/*String firstYear="";
				if(student.getAdmAppln() != null && student.getAdmAppln().getAppliedYear() != null){
					firstYear = String.valueOf(student.getAdmAppln().getAppliedYear());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getCurriculumSchemeDuration() != null && student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null){
					String acedamicYear = String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
					String nextYear=""+String.valueOf(Integer.parseInt(acedamicYear)+1);
					message = message.replace(CMSConstants.TEMPLATE_ACADEMIC_YEAR ,firstYear+" - "+nextYear );
				}*/
				message = message.replace(CMSConstants.TEMPLATE_ACADEMIC_YEAR ,commonTemplateForm.getCampYear());
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER1, "he");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER1, "she");
					}
				}
				if(commonTemplateForm.getNcc() != null && !commonTemplateForm.getNcc().isEmpty()){
					
					message = message.replace(CMSConstants.TEMPLATE_NCC_CAMPS,commonTemplateForm.getNcc());
				}
				messageList.add(message);
			   }
		   }
		return messageList;
	}
	
	/**
	 * @param stuList
	 * @param request
	 * @param commonTemplateForm
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToListForMotherTongue(List<Student> stuList,
			HttpServletRequest request, CommonTemplateForm commonTemplateForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.TEMPLATE_MOTHER_TONGUE);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, student.getAdmAppln().getPersonalData().getFatherName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "S/o");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "D/o");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getName() != null){
					String admittedClass = "I "+student.getAdmAppln().getCourseBySelectedCourseId().getName();
					message = message.replace(CMSConstants.TEMPLATE_ADMITTEDCLASS, admittedClass); 
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getName() != null){
					if( student.getClassSchemewise().getClasses().getTermNumber()== 1){
						String currentClass = "I "+student.getAdmAppln().getCourseBySelectedCourseId().getName();
						message = message.replace(CMSConstants.TEMPLATE_CURRENTCLASS, currentClass); 
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 2){
						String currentClass = "II "+student.getAdmAppln().getCourseBySelectedCourseId().getName();
						message = message.replace(CMSConstants.TEMPLATE_CURRENTCLASS, currentClass); 
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getAppliedYear() != null){
					message = message.replace(CMSConstants.TEMPLATE_ADMITTEDYEAR, String.valueOf(student.getAdmAppln().getAppliedYear()));
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getCurriculumSchemeDuration() != null && student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null){
					message = message.replace(CMSConstants.TEMPLATE_CURRENTYEAR, String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear()));
				}
				/*String firstYear="";
				if(student.getAdmAppln() != null && student.getAdmAppln().getAppliedYear() != null){
					firstYear = String.valueOf(student.getAdmAppln().getAppliedYear());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getCurriculumSchemeDuration() != null && student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null){
					String acedamicYear = String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
					String nextYear=""+String.valueOf(Integer.parseInt(acedamicYear)+1);
					message = message.replace(CMSConstants.TEMPLATE_ACADEMIC_YEAR ,firstYear+" - "+nextYear );
				}*/
				/*if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "he");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "she");
					}
				}*/
				if(commonTemplateForm.getMotherTongue() != null && !commonTemplateForm.getMotherTongue().isEmpty()){
					
					message = message.replace(CMSConstants.TEMPLATE_MOTHER_TONGUE_NAME,commonTemplateForm.getMotherTongue());
				}
				messageList.add(message);
			   }
		   }
		return messageList;
	}
}
