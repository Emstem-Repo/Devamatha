package com.kp.cms.helpers.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.DateOfBirthPrintForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.utilities.CommonUtil;

public class DateOfBirthPrintHelper {
	public static volatile DateOfBirthPrintHelper dateOfBirthPrintHelper =null;
	/**
	 * @return
	 */
	public static DateOfBirthPrintHelper getInstance() {
		if(dateOfBirthPrintHelper == null){
			dateOfBirthPrintHelper = new DateOfBirthPrintHelper();
			return dateOfBirthPrintHelper;
		}
		return dateOfBirthPrintHelper;
	}
	/**
	 * @param stuList
	 * @param isStudent
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToList(List<Student> stuList, HttpServletRequest request, DateOfBirthPrintForm dateOfBirthPrintForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.DOB_TEMPLATE);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(dateOfBirthPrintForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
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
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "his");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "her");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getDateOfBirth() != null){
					Date dateOfBirth= student.getAdmAppln().getPersonalData().getDateOfBirth();
					String dob = new SimpleDateFormat("dd/MM/yyyy").format(dateOfBirth);
					message = message.replace(CMSConstants.TEMPLATE_DOB, dob);
				}
				messageList.add(message);
			   }
		   }
		return messageList;
	}	
}
