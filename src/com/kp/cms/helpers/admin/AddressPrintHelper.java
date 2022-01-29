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
import com.kp.cms.forms.admin.AddressPrintForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.utilities.CommonUtil;

public class AddressPrintHelper {
	public static volatile AddressPrintHelper addressPrintHelper =null;
	/**
	 * @return
	 */
	private AddressPrintHelper(){
		
	}
	public static AddressPrintHelper getInstance() {
		if(addressPrintHelper == null){
			addressPrintHelper = new AddressPrintHelper();
			return addressPrintHelper;
		}
		return addressPrintHelper;
	}
	/**
	 * @param stuList
	 * @param isStudent
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToList(List<Student> stuList, HttpServletRequest request, AddressPrintForm addressPrintForm) throws Exception{
		Iterator<Student> iterator = stuList.iterator();
		Student student;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(CMSConstants.ADDRESS_TEMPLATE);
		
		ArrayList<String> messageList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				String message = desc;
				Date date = CommonUtil.ConvertStringToDate(addressPrintForm.getDate());
				SimpleDateFormat formatter= 
					  new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				if(student.getAdmAppln()!=null && student.getAdmAppln().getPersonalData()!=null){
					if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
						message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
					}
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
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourse() != null && student.getAdmAppln().getCourse().getProgram() != null && student.getAdmAppln().getCourse().getProgram().getStream() != null){
					message = message.replace(CMSConstants.TEMPLATE_STREAM, student.getAdmAppln().getCourse().getProgram().getStream());
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
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourse() != null && student.getAdmAppln().getCourse().getName() != null){
					message = message.replace(CMSConstants.TEMPLATE_COURSE, student.getAdmAppln().getCourse().getName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER_HE_SMALL, "he");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER_HE_SMALL, "she");
					}
				}
				messageList.add(message);
			}
		}
		return messageList;
	}	
}
