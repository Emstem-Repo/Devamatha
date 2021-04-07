package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;

public class PasswordPrintHelper {
	public static volatile PasswordPrintHelper passwordPrintHelper = null;

	public static PasswordPrintHelper getInstance() {
		if (passwordPrintHelper == null) {
			passwordPrintHelper = new PasswordPrintHelper();
			return passwordPrintHelper;
		}
		return passwordPrintHelper;
	}

	/**
	 * 
	 * @param studList
	 * @return
	 * @throws Exception
	 */
	public List<String> copyBosToList(List<Student> studList, Boolean isStudent,  HttpServletRequest request) throws Exception {
		Iterator<Student> iterator = studList.iterator();
		Student student;
		StudentLogin studentLogin;
		
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.PASSWORD_TEMPLATE);
		
		ArrayList<String> messageList = new ArrayList<String>();
		Boolean result;
		byte[] logo = null;
		byte[] logo1 = null;
		String logoPath = "";
		String logoPath1= "";
		HttpSession session = request.getSession(false);
		Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
		if (organisation != null) {
			logo = organisation.getLogo();
			logo1 = organisation.getLogo1();
		}
		if (session != null) {
			session.setAttribute("LogoBytes", logo);
			session.setAttribute("LogoBytes1", logo1);
		}
		
		logoPath = request.getContextPath();
		logoPath = "<img src="
				+ logoPath
				+ "/LogoServlet?count=1 alt='Logo not available' width='210' height='100' >";
		
		logoPath1 = request.getContextPath();
		logoPath1 = "<img src="
				+ logoPath1
				+ "/LogoServlet?count=2 alt='Logo not available' width='210' height='100' >";
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (iterator.hasNext()) {
				student = (Student) iterator.next();
				boolean isCjc = CMSConstants.LINK_FOR_CJC;
				if(isCjc){
					if(!list.isEmpty()) {
						Iterator<GroupTemplate> iterator2 = list.iterator();
						while (iterator2.hasNext()) {
							GroupTemplate groupTemplate = (GroupTemplate) iterator2.next();
							if(groupTemplate.getCourse() != null && groupTemplate.getCourse().getId() == student.getAdmAppln().getCourseBySelectedCourseId().getId()){
								desc = groupTemplate.getTemplateDescription();
							}
						}
					}
				}
				String message = desc;
				if(student!= null && student.getStudentLogins()!= null){
					Iterator<StudentLogin> iterator2 = student.getStudentLogins().iterator();
					while (iterator2.hasNext()) {
						message = desc;
						studentLogin = (StudentLogin) iterator2.next();
						if(studentLogin.getIsStudent()!= null ){
							if(isStudent && studentLogin.getIsStudent()  && studentLogin.getIsActive()){
								message = message.replace(CMSConstants.TEMPLATE_LOGO, logoPath);
								message = message.replace(CMSConstants.TEMPLATE_LOGO1, logoPath1);
								
								if(student.getAdmAppln()!= null && student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getFatherName()!= null){
									message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME,student.getAdmAppln().getPersonalData().getFatherName());
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
//									if(student.getAdmAppln().getPersonalData().getCurrentAddressZipCode()!= null){
//										address.append(student.getAdmAppln().getPersonalData().getCurrentAddressZipCode());
//									}
									message = message.replace(CMSConstants.TEMPLATE_ADDRESS, address.toString());
									if(student.getAdmAppln().getPersonalData().getCurrentAddressZipCode()!= null){
										message = message.replace(CMSConstants.TEMPLATE_PINCODE, student.getAdmAppln().getPersonalData().getCurrentAddressZipCode());
									}
									if(student.getAdmAppln().getPersonalData().getFirstName()!= null){
										message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,student.getAdmAppln().getPersonalData().getFirstName());
									}
								}
								if(studentLogin.getUserName()!= null){
									message = message.replace(CMSConstants.TEMPLATE_USERNAME,studentLogin.getUserName());
								}
								if(studentLogin.getPassword()!= null){
									message = message.replace(CMSConstants.TEMPLATE_PASSWORD,EncryptUtil.getInstance().decryptDES(studentLogin.getPassword()));
								}
								if(student.getAdmAppln().getPersonalData().getUniversityEmail()!= null){
									String universityEmail=student.getAdmAppln().getPersonalData().getUniversityEmail();
									String email[]=universityEmail.split("@");
									message = message.replace(CMSConstants.TEMPLATE_EMAIL,email[0]);
								}
								if(student.getClassSchemewise().getClasses().getName()!= null){
									message = message.replace(CMSConstants.TEMPLATE_CLASS,student.getClassSchemewise().getClasses().getName());
								}
									
								message = message.replace(CMSConstants.TEMPLATE_DATE, CommonUtil.getStringDateWithFullMonthName(new Date()));
								messageList.add(message);
							}
							if(!isStudent && !studentLogin.getIsStudent()){
								if(student.getAdmAppln()!= null && student.getAdmAppln().getPersonalData()!= null){
									if(student.getAdmAppln().getPersonalData().getFatherName()!= null){
										message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME,student.getAdmAppln().getPersonalData().getFatherName());
									}
									message = message.replace(CMSConstants.TEMPLATE_LOGO, logoPath);
									message = message.replace(CMSConstants.TEMPLATE_LOGO1, logoPath1);
									StringBuffer parentAddress = new StringBuffer();
									if(student.getAdmAppln().getPersonalData().getParentAddressLine1()!= null && !student.getAdmAppln().getPersonalData().getParentAddressLine1().trim().isEmpty()){
										parentAddress.append(student.getAdmAppln().getPersonalData().getParentAddressLine1() + "<BR>");
									}	
									if(student.getAdmAppln().getPersonalData().getParentAddressLine2()!= null && !student.getAdmAppln().getPersonalData().getParentAddressLine2().trim().isEmpty()){
										parentAddress.append( student.getAdmAppln().getPersonalData().getParentAddressLine2()+ "<BR>");
									}
									if(student.getAdmAppln().getPersonalData().getParentAddressLine3()!= null && !student.getAdmAppln().getPersonalData().getParentAddressLine3().trim().isEmpty()){
										parentAddress.append( student.getAdmAppln().getPersonalData().getParentAddressLine3()+ "<BR>");
									}
									message = message.replace(CMSConstants.TEMPLATE_ADDRESS, parentAddress);
									
									if(student.getAdmAppln().getPersonalData().getParentAddressZipCode()!= null){
										message = message.replace(CMSConstants.TEMPLATE_PINCODE, student.getAdmAppln().getPersonalData().getParentAddressZipCode());
									}
									if(student.getAdmAppln().getPersonalData().getFirstName()!= null){
										message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,student.getAdmAppln().getPersonalData().getFirstName());
									}
								}
								if(studentLogin.getUserName()!= null){
									message = message.replace(CMSConstants.TEMPLATE_USERNAME,studentLogin.getUserName());
								}
								if(studentLogin.getPassword()!= null){
									message = message.replace(CMSConstants.TEMPLATE_PASSWORD,EncryptUtil.getInstance().decryptDES(studentLogin.getPassword()));
								}
								if(student.getAdmAppln().getPersonalData().getUniversityEmail()!= null){
									String universityEmail=student.getAdmAppln().getPersonalData().getUniversityEmail();
									String email[]=universityEmail.split("@");
									message = message.replace(CMSConstants.TEMPLATE_EMAIL,email[0]);
								}
								if(student.getClassSchemewise().getClasses().getName()!= null){
									message = message.replace(CMSConstants.TEMPLATE_CLASS,student.getClassSchemewise().getClasses().getName());
								}
								message = message.replace(CMSConstants.TEMPLATE_DATE, CommonUtil.getStringDateWithFullMonthName(new Date()));
								
								messageList.add(message);
							}
//							HtmlPrinter.printHtml(message);
							result = true;
						}
					}
				}
			}
		}
		return messageList;
	}

}
