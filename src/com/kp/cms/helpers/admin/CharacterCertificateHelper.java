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
import com.kp.cms.forms.admin.CharacterCertificateForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.utilities.CommonUtil;

public class CharacterCertificateHelper {
	public static volatile CharacterCertificateHelper characterCertificateHelper =null;
	/**
	 * @return
	 */
	private CharacterCertificateHelper(){
		
	}
	public static CharacterCertificateHelper getInstance() {
		if(characterCertificateHelper == null){
			characterCertificateHelper = new CharacterCertificateHelper();
			return characterCertificateHelper;
		}
		return characterCertificateHelper;
	}
	/**
	 * @param stuList
	 * @param isStudent
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToList(List<Student> stuList, HttpServletRequest request, CharacterCertificateForm characterCertificateForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.CHARACTER_TEMPLATE);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(characterCertificateForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourse() != null && student.getAdmAppln().getCourse().getProgram() != null && student.getAdmAppln().getCourse().getProgram().getStream() != null){
					message = message.replace(CMSConstants.TEMPLATE_STREAM, student.getAdmAppln().getCourse().getProgram().getStream());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getName() != null){
					message = message.replace(CMSConstants.TEMPLATE_CLASS, student.getClassSchemewise().getClasses().getName());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getCourse() != null && student.getClassSchemewise().getClasses().getCourse().getName() != null && !student.getClassSchemewise().getClasses().getCourse().getName().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_COURSE, student.getClassSchemewise().getClasses().getCourse().getName());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getTermNumber() != null){
					if( student.getClassSchemewise().getClasses().getTermNumber()== 1){
						message = message.replace(CMSConstants.TEMPLATE_SEMISTER,"I");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 2){
						message = message.replace(CMSConstants.TEMPLATE_SEMISTER,"II");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 3){
						message = message.replace(CMSConstants.TEMPLATE_SEMISTER,"III");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 4){
						message = message.replace(CMSConstants.TEMPLATE_SEMISTER,"IV");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 5){
						message = message.replace(CMSConstants.TEMPLATE_SEMISTER,"V");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 6){
						message = message.replace(CMSConstants.TEMPLATE_SEMISTER,"VI");
					}
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
				if(student.getAdmAppln() != null && student.getAdmAppln().getAppliedYear() != null){
					String firstYear = String.valueOf(student.getAdmAppln().getAppliedYear());
					message = message.replace(CMSConstants.TEMPLATE_FIRST_YEAR ,firstYear );
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getCurriculumSchemeDuration() != null && student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null){
					String acedamicYear = String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
					String nextYear=""+String.valueOf(Integer.parseInt(acedamicYear)+1);
					message = message.replace(CMSConstants.TEMPLATE_LAST_YEAR ,nextYear );
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
				if(characterCertificateForm.getCharacter() != null && !characterCertificateForm.getCharacter().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_CHARACTER, characterCertificateForm.getCharacter());
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
				messageList.add(message);
			   }
		   }
		return messageList;
	}	
}
