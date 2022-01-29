package com.kp.cms.handlers.employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.employee.EmpResPubDetailsHelper;
import com.kp.cms.helpers.employee.EmployeeInfoEditHelper;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.employee.EmpResearchPublicDetails;
import com.kp.cms.bo.employee.EmpResearchPublicMaster;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmpResPublicMasterTO;
import com.kp.cms.transactions.employee.IEmpResPubDetailsTransaction;
import com.kp.cms.transactions.employee.IEmployeeInfoEditTransaction;
import com.kp.cms.transactionsimpl.employee.EmpResPubDetailsImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoEditTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.print.HtmlPrinter;

public class EmpResPubDetailsHandler {
	private static final Log log = LogFactory.getLog(EmpResPubDetailsHandler.class);
	IEmpResPubDetailsTransaction empTransaction=EmpResPubDetailsImpl.getInstance();
	
	private static volatile EmpResPubDetailsHandler instance=null;
	
	/**
	 * 
	 */
	private EmpResPubDetailsHandler(){
		
	}
	
	/**
	 * @return
	 */
	public static EmpResPubDetailsHandler getInstance(){
		log.info("Start getInstance of EmployeeInfoEditHandler");
		if(instance==null){
			instance=new EmpResPubDetailsHandler();
		}
		log.info("End getInstance of EmployeeInfoEditHandler");
		return instance;
	}
	
	public void getEmployeeResearchDetails(EmpResPubDetailsForm empResPubForm) throws Exception {
		//boolean flag=false;
		List<EmpResearchPublicDetails> empApplicantDetails=empTransaction.GetResearchDetails(empResPubForm);
		if (empApplicantDetails != null) {
		//	flag=true;
			EmpResPubDetailsHelper.getInstance().convertBoToForm(empApplicantDetails,empResPubForm);
		}
		//return flag;
	}
	
	public void getEmployeeResearchDetailsRej(EmpResPubDetailsForm empResPubForm) throws Exception {
		//boolean flag=false;
		List<EmpResearchPublicDetails> empApplicantDetails=empTransaction.GetResearchDetailsRej(empResPubForm);
		if (empApplicantDetails != null) {
		//	flag=true;
			EmpResPubDetailsHelper.getInstance().convertBoToForm(empApplicantDetails,empResPubForm);
		}
		//return flag;
	}
	
	
	public boolean saveEmpResPub(EmpResPubDetailsForm empResPubForm,ActionErrors errors) throws Exception
	{
		boolean flag=false;
		List<EmpResearchPublicDetails> empResPubDetails=EmpResPubDetailsHelper.getInstance().convertFormToBo(empResPubForm,errors);
		flag=empTransaction.saveEmpResPub(empResPubDetails);
		
		return flag;
	}
	public List<EmpResPublicMasterTO> getEmpResPublicList()throws Exception{
    	List<EmpResearchPublicMaster> resPubMasterToList=empTransaction.getEmpResPublicList();
    	List<EmpResPublicMasterTO> allowanceTO=EmpResPubDetailsHelper.getInstance().convertBOtoTO(resPubMasterToList);
    	return allowanceTO;
    }
	
	 public boolean duplicateCheck(EmpResPubDetailsForm empResPubMasterForm,ActionErrors errors,HttpSession session){
			boolean duplicate=empTransaction.duplicateCheck(empResPubMasterForm.getEmpResPubName(),session,errors,empResPubMasterForm);
			return duplicate;
		}
	    public boolean addResPubMaster(EmpResPubDetailsForm empResPubMasterForm,String mode)throws Exception{
	    	EmpResearchPublicMaster allowance=EmpResPubDetailsHelper.getInstance().convertFormTOBO(empResPubMasterForm,mode);
			boolean isAdded=empTransaction.addResPubMaster(allowance);
			return isAdded;
		}
	    public void editResPubMaster(EmpResPubDetailsForm empResPubMasterForm)throws Exception{
	    	EmpResearchPublicMaster allowance=empTransaction.getResPubMasterById(Integer.parseInt(empResPubMasterForm.getId()));
	    	EmpResPubDetailsHelper.getInstance().setBotoForm(empResPubMasterForm, allowance);
		}
	    public boolean updateResPubMaster(EmpResPubDetailsForm empResPubMasterForm,String mode)throws Exception{
	    	EmpResearchPublicMaster allowance=EmpResPubDetailsHelper.getInstance().convertFormTOBO(empResPubMasterForm,mode);
			boolean isUpdated=empTransaction.updateResPubMaster(allowance);
			return isUpdated;
		}
	    public boolean deleteResPubMaster(EmpResPubDetailsForm empResPubMasterForm)throws Exception{
			
			boolean isDeleted=empTransaction.deleteResPubMaster(Integer.parseInt(empResPubMasterForm.getId()));
			return isDeleted;
	    }
	    public boolean reactivateResPubMaster(EmpResPubDetailsForm empResPubMasterForm,String userId)
		 throws Exception{
	       return empTransaction.reactivateResPubMaster(empResPubMasterForm);
	   }
		/**
		 * Send mail to student after successful submit of application
		 * @param admForm
		 * @return
		 */
		public boolean sendMailToApprover(EmpResPubDetailsForm admForm) throws Exception {
			boolean sent=false;
			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {
			log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
				List<GroupTemplate> list=null;
				//get template and replace dynamic data
				TemplateHandler temphandle=TemplateHandler.getInstance();
				 list= temphandle.getDuplicateCheckList(CMSConstants.EMPLOYEE_RESEARCH_APPROVER_MAIL_TEMPLATE);
				
				if(list != null && !list.isEmpty()) {

					String desc = list.get(0).getTemplateDescription();
					//send mail to applicant
					//String adminmail=prop.getProperty(CMSConstants.EMPLOYEE_RESEARCH_PUBLICATION_TOID);
					String approverAddress=admForm.getApproverEmailId();
					String fromName = prop.getProperty(CMSConstants.EMPLOYEE_RESEARCH_PUBLICATION_FROMNAME);
					String fromAddress = CMSConstants.MAIL_USERID;
					//String[] mailIds = adminmail.split(",");
					//for (int i = 0; i < mailIds.length; i++) {
						//String toAddress=mailIds[i];	
					String toAddress=approverAddress;
					String name=admForm.getEmployeeName();
					name=name+ "(" + admForm.getFingerPrintId() +")";
					String subject= "Research and Publication details submitted by "+name;
					String message =desc;
								//message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_MESSAGE,name);
					message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME,name);
					sent=sendMail(toAddress, subject, message, fromName, fromAddress);
					HtmlPrinter.printHtml(message);

						//	}
							
					}
							 
				return sent;
		}
		public boolean sendMailToEmployee(EmpResPubDetailsForm admForm) throws Exception {
			boolean sent=false;
			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {
			log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
				List<GroupTemplate> list=null;
				//get template and replace dynamic data
				TemplateHandler temphandle=TemplateHandler.getInstance();
				 list= temphandle.getDuplicateCheckList(CMSConstants.EMPLOYEE_RESEARCH_SUBMIT_EMPLOYEE_TEMPLATE);
				
				if(list != null && !list.isEmpty()) {

					String desc = list.get(0).getTemplateDescription();
					//send mail to applicant
					//String adminmail=prop.getProperty(CMSConstants.EMPLOYEE_RESEARCH_PUBLICATION_TOID);
					String approverAddress=admForm.getEmployeeEmailId();
					String fromName = prop.getProperty(CMSConstants.EMPLOYEE_RESEARCH_PUBLICATION_FROMNAME);
					String fromAddress=CMSConstants.MAIL_USERID;
					//String[] mailIds = adminmail.split(",");
					//for (int i = 0; i < mailIds.length; i++) {
						//String toAddress=mailIds[i];	
					String toAddress=approverAddress;
					String name=admForm.getEmployeeName();
					name=name+ "(" + admForm.getFingerPrintId() +")";
					String subject= "Research and Publication details submitted by "+name;
					String message =desc;
								//message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_MESSAGE,name);
					message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME,name);
					sent=sendMail(toAddress, subject, message, fromName, fromAddress);
					HtmlPrinter.printHtml(message);

						//	}
							
					}
				 
				return sent;
		}
		
		
		/**
		 * Common Send mail
		 * @param admForm
		 * @return
		 */
		public boolean sendMail(String mailID, String sub,String message, String fromName, String fromAddress) {
				boolean sent=false;
					String toAddress=mailID;
					// MAIL TO CONSTRUCTION
					String subject=sub;
					String msg=message;
				
					MailTO mailto=new MailTO();
					mailto.setFromAddress(fromAddress);
					mailto.setToAddress(toAddress);
					mailto.setSubject(subject);
					mailto.setMessage(msg);
					mailto.setFromName(fromName);
					
					sent=CommonUtil.sendMail(mailto);
				return sent;
		}
		
		public List<EmployeeTO> getSearchedEmployee(EmpResPubDetailsForm stForm)throws Exception {
			log.info("enter getSearchedStudents");
			List<EmployeeTO> employeeToList=null;
			int designationId = 0;
			int departmentId = 0;
			int streamId = 0;
			int empTypeId=0;
				
			if (stForm.getTempDepartmentId() != null
					&& !StringUtils.isEmpty(stForm.getTempDepartmentId().trim())
					&& StringUtils.isNumeric(stForm.getTempDepartmentId())) {
				departmentId = Integer.parseInt(stForm.getTempDepartmentId());
			}
			if (stForm.getTempDesignationPfId() != null
					&& !StringUtils.isEmpty(stForm.getTempDesignationPfId().trim())
					&& StringUtils.isNumeric(stForm.getTempDesignationPfId())) {
				designationId = Integer.parseInt(stForm.getTempDesignationPfId());
			}
			if (stForm.getTempStreamId() != null
					&& !StringUtils.isEmpty(stForm.getTempStreamId().trim())
					&& StringUtils.isNumeric(stForm.getTempStreamId())) {
				streamId = Integer.parseInt(stForm.getTempStreamId());
			}
			if (stForm.getTempEmptypeId() != null
					&& !StringUtils.isEmpty(stForm.getTempEmptypeId().trim())
					&& StringUtils.isNumeric(stForm.getTempEmptypeId())) {
				empTypeId = Integer.parseInt(stForm.getTempEmptypeId());
			}
			if(stForm.getIsEmployee().equalsIgnoreCase("true")){
				StringBuffer query = empTransaction.getSerchedEmployeeQueryAdmin(stForm.getTempFingerPrintId(),
					departmentId , designationId, stForm.getTempName(), stForm.getTempActive(), streamId, stForm.getTempTeachingStaff(),empTypeId);
				List<Employee> employeelist=empTransaction.getSerchedEmployeeAdmin(query);
				employeeToList = EmpResPubDetailsHelper.getInstance().convertEmployeeTOtoBO(employeelist, departmentId , designationId);
			}
			else
			{
				StringBuffer query = empTransaction.getSerchedGuestQueryAdmin(stForm.getTempFingerPrintId(),
					departmentId , designationId, stForm.getTempName(), stForm.getTempActive(), streamId, stForm.getTempTeachingStaff(),empTypeId);
				List<GuestFaculty> guestlist=empTransaction.getSerchedGuestAdmin(query);
				 employeeToList = EmpResPubDetailsHelper.getInstance().convertGuestTOtoBO(guestlist, departmentId , designationId);
			}
			
			
			log.info("exit getSearchedStudents");
			return employeeToList;
		}
		
		public void getInitialPageData(EmpResPubDetailsForm employeeInfoEditForm)throws Exception
		{
			 Map<String,String> streamMap=empTransaction.getStreamMap();
			 if(streamMap!=null)
			 {
				 employeeInfoEditForm.setTempStreamMap(streamMap);
			 }
			 Map<String,String> designationMap=empTransaction.getDesignationMap();
			 if(designationMap!=null){
				 employeeInfoEditForm.setTempDesignationMap(designationMap);
			 }
			 Map<String,String> departmentMap=empTransaction.getDepartmentMap();
			 if(departmentMap!=null)
			 {
				 employeeInfoEditForm.setTempDepartmentMap(departmentMap);
			 }
			 Map<String,String> empTypeMap=empTransaction.getEmpTypeMap();
			 if(empTypeMap!=null){
				 employeeInfoEditForm.setTempEmpTypeMap(empTypeMap);
			 }
			 
		}
		
		public boolean getEmpResDetailsAdminEdit(EmpResPubDetailsForm empResPubForm) throws Exception {
			boolean flag=false;
			List<EmpResearchPublicDetails> empApplicantDetails=empTransaction.GetResearchDetailsAdmin(empResPubForm);
			if (empApplicantDetails != null) {
				flag=true;
				EmpResPubDetailsHelper.getInstance().convertBoToFormAdmin(empApplicantDetails,empResPubForm);
			}
			if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
			Employee emp=empTransaction.GetEmployee(empResPubForm);
			if (emp != null) {
				//	flag=true;
				EmpResPubDetailsHelper.getInstance().convertBoToFormEmployee(emp,empResPubForm);
				}
			}
			else{
				GuestFaculty emp=empTransaction.GetGuest(empResPubForm);
			if (emp != null) {
				//	flag=true;
				EmpResPubDetailsHelper.getInstance().convertBoToFormGuest(emp,empResPubForm);
				}
			}
				
			return flag;
		}
		
		public boolean saveEmpResPubAdmin(EmpResPubDetailsForm empResPubForm,ActionErrors errors) throws Exception
		{
			boolean flag=false;
			List<EmpResearchPublicDetails> empResPubDetails=EmpResPubDetailsHelper.getInstance().convertFormToBoAdmin(empResPubForm,errors);
			flag=empTransaction.saveEmpResPub(empResPubDetails);
			
			return flag;
		}

}
