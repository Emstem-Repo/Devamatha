package com.kp.cms.handlers.employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.EmpResumeSubmissionForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.employee.EmpResumeSubmissionHelper;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction;
import com.kp.cms.transactions.employee.IEmployeeInfoNewTransaction;
import com.kp.cms.transactionsimpl.employee.EmpResumeSubmissionTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoNewTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.print.HtmlPrinter;

public class EmpResumeSubmissionHandler {
	
	private static final Log log = LogFactory.getLog(EmpResumeSubmissionHandler.class);
	IEmpResumeSubmissionTransaction empResumeTransaction=EmpResumeSubmissionTransaction.getInstance();
	IEmployeeInfoNewTransaction empTransaction=EmployeeInfoNewTransactionImpl.getInstance();
	
	private static volatile EmpResumeSubmissionHandler instance=null;
	
	/**
	 * 
	 */
	private EmpResumeSubmissionHandler(){
		
	}
	
	/**
	 * @return
	 */
	public static EmpResumeSubmissionHandler getInstance(){
		log.info("Start getInstance of EmpResumeSubmissionHandler");
		if(instance==null){
			instance=new EmpResumeSubmissionHandler();
		}
		log.info("End getInstance of EmpResumeSubmissionHandler");
		return instance;
	}

	public void getInitialData(EmpResumeSubmissionForm empResumeSubmissionForm)throws Exception {
		log.info("Start getInitialData of EmpResumeSubmissionHandler");
		 Map<String,String> lakhsAndThousands=new LinkedHashMap<String, String>();
		 
		 //added newly for religion map by Smitha
		 Map<String, String> religionMap=empTransaction.getReligionMap();
		 if(religionMap!=null && !religionMap.isEmpty())
			 empResumeSubmissionForm.setReligionMap(religionMap);
		 
		 Map<String,String> stateMap=empTransaction.getStateMap();
		 if(stateMap!=null && !stateMap.isEmpty())
			 empResumeSubmissionForm.setCurrentStateMap(stateMap);
		 	 empResumeSubmissionForm.setStateMap(stateMap);
		 
		 Map<String,String> designationMap=empResumeTransaction.getDesignationMap();
		 if(designationMap!=null){
			 empResumeSubmissionForm.setDesignationMap(designationMap);
			 empResumeSubmissionForm.setPostAppliedMap(designationMap);
		 }
		 Map<String,String> departmentMap=empResumeTransaction.getDepartmentMap();
		 if(departmentMap!=null)
			 empResumeSubmissionForm.setDepartmentMap(departmentMap);
		 
		 Map<String,String> countryMap=empResumeTransaction.getCountryMap();
		 if(countryMap!=null){
			 empResumeSubmissionForm.setCountryMap(countryMap);
			 empResumeSubmissionForm.setCurrentCountryMap(countryMap);
		 }
		 Map<String,String> nationalityMap=empResumeTransaction.getNationalityMap();
		 if(nationalityMap!=null)
			 empResumeSubmissionForm.setNationalityMap(nationalityMap);
		 Map<String,String> qualificationLevelMap=empResumeTransaction.getQualificationLevelMap();
		 if(qualificationLevelMap!=null){
			 empResumeSubmissionForm.setQualificationLevelMap(qualificationLevelMap);
		 }
		 /*qualificationFixedMap=empResumeTransaction.getQualificationFixedMap();
		 if(qualificationFixedMap!=null){
			 empResumeSubmissionForm.setQualificationFixedMap(qualificationFixedMap);
		 }*/
		 
		 Map<String,String> qualificationMap=empResumeTransaction.getQualificationMap();
		 if(qualificationMap!=null){
			 empResumeSubmissionForm.setQualificationMap(qualificationMap);
		 }
		 
		 List<EmpQualificationLevelTo> qualificationFixedTo=empResumeTransaction.getQualificationFixedMap();
		 if(qualificationFixedTo!=null){
			 empResumeSubmissionForm.getEmpResumeSubmissionTo().setEmpQualificationFixedTo(qualificationFixedTo);
		 }
		 Map<String,String> subjectAreaMap=empResumeTransaction.getSubjectAreaMap();
		 if(subjectAreaMap!=null){
			 empResumeSubmissionForm.setSubjectAreaMap(subjectAreaMap);
		 }
		 
		 Map<String,String> jobTypeMap=empResumeTransaction.getJobType();
		 if(jobTypeMap!=null){
			 empResumeSubmissionForm.setJobTypeMap(jobTypeMap);
		 }
		 for(int i=0;i<100;i++){
			 lakhsAndThousands.put(String.valueOf(i),String.valueOf(i));
		 }
		 if(lakhsAndThousands!=null){
			 empResumeSubmissionForm.setLakhsAndThousands(lakhsAndThousands);
		 }
		 if(empResumeSubmissionForm.getIsCjc()){
			 Map<Integer,String> empSubjectMap=empResumeTransaction.getEmployeeSubjectMap();
			 if(empSubjectMap!=null)
				 empResumeSubmissionForm.setEmpSubjectMap(empSubjectMap);
		 }
		
		log.info("End getInitialData of EmpResumeSubmissionHandler");
	}

	/**
	 * @param empResumeSubmissionForm
	 * @return
	 * @throws Exception 
	 */
	public boolean saveEmpResume(EmpResumeSubmissionForm empResumeSubmissionForm,HttpSession session) throws Exception {
		boolean flag=false;
		EmpOnlineResume empOnlineResume=EmpResumeSubmissionHelper.getInstance().convertFormToBo(empResumeSubmissionForm,session);
		 if(empResumeSubmissionForm.getUserId()!=null && !empResumeSubmissionForm.getUserId().isEmpty())
		 /*applicationNumber = empResumeTransaction.getApplicationNumber(userid);
		 if(applicationNumber!=null && !applicationNumber.isEmpty()){
			 empOnlineResume.setApplicationNo(applicationNumber);
			 empResumeSubmissionForm.setApplicationNO(applicationNumber);
		 }*/
		flag=empResumeTransaction.saveEmpResume(empOnlineResume,empResumeSubmissionForm );
		return flag;
	}

	public boolean sendMailToEmployee(EmpResumeSubmissionForm empResumeSubmissionForm)throws Exception {
		boolean sent=false;
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.EMPLOYEE_EMP_APPLICANT_MAIL);
		if(list != null && !list.isEmpty()) {

			String desc = list.get(0).getTemplateDescription();
			//send mail to applicant

					String name = "";
					String applnno = "";
				
					if(empResumeSubmissionForm.getEmail()!=null && !StringUtils.isEmpty(empResumeSubmissionForm.getEmail().trim())){
						name=empResumeSubmissionForm.getName();
						applnno=empResumeSubmissionForm.getApplicationNO();
						String program = "";
						String appliedYear = "";
						
						//replace dyna data
						String subject= "Application Submission: "+applnno;
						
						String message =desc;
						message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,name);
						message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,applnno);
//						send mail
						MailTO mailTo=sendMail(empResumeSubmissionForm.getEmail(),subject,message);
						if(mailTo!=null){
							CommonUtil.sendMail(mailTo);
						}else
							return sent;
					}
				
		
		} 
		return sent;
	}

	public MailTO sendMail(String email, String sub, String message)throws Exception {
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
			return null;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return null;
		}
			String adminmail=prop.getProperty(CMSConstants.EMPLOYEE_ONLINE_FROM_ADDRESS);
			String toAddress=email;
			// MAIL TO CONSTRUCTION
			String subject=sub;
			String msg=message;
		
			MailTO mailto=new MailTO();
			mailto.setFromAddress(adminmail);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
		return mailto;
	}

	public boolean sendMailToAdmin(EmpResumeSubmissionForm empResumeSubmissionForm)throws Exception {
		boolean sent=false;
		String department = "";
		String applnno = "";
		String toAddress="";
		String postApplied="";
		String qualificationLevel="";
		String adminmail="";
		String fromName="";
			TemplateHandler temphandle=TemplateHandler.getInstance();
			List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.EMPLOYEE_EMP_ADMIN_MAIL);
			if(list != null && !list.isEmpty()) {

				String desc = list.get(0).getTemplateDescription();
				//send mail to applicant
						try {
							Properties prop=new Properties();
							InputStream ins=EmpResumeSubmissionHandler.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
							prop.load(ins);
							toAddress=toAddress+prop.getProperty(CMSConstants.EMPLOYEE_TO_ADDRESS);
							adminmail=prop.getProperty(CMSConstants.EMPLOYEE_ONLINE_FROM_ADDRESS);
							fromName=prop.getProperty("knowledgepro.admission.studentmail.fromName");
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(toAddress!=null && !StringUtils.isEmpty(toAddress.trim())){
							applnno=empResumeSubmissionForm.getApplicationNO();
							department=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(empResumeSubmissionForm.getDepartmentId()),"Department",true,"name");
							postApplied=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(empResumeSubmissionForm.getDesignationId()),"Designation",true,"name");
							qualificationLevel=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(empResumeSubmissionForm.getQualificationId()),"QualificationLevelBO",true,"name");
							//replace dyna data
							String subject= "Application Submission: "+applnno;
							
							String message =desc;
							message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,applnno);
							message = message.replace(CMSConstants.EMPLOYEE_DEPARTMENT_TEMPLATE,department);
							message = message.replace(CMSConstants.EMPLOYEE_POST_APPLIED_TEMP,postApplied);
							message = message.replace(CMSConstants.EMPLOYEE_QUALIFICATION_LEVEL_TAG,qualificationLevel);
//							send mail
							CommonUtil.sendMailToMoreRecepients(fromName, adminmail, toAddress, subject, message);
							sent=true;
						}
			}
		
		return sent;
	}


}
