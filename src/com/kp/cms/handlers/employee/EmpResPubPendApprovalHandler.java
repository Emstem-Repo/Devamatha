package com.kp.cms.handlers.employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.employee.EmployeeInfoEditAction;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.employee.EmpResearchPublicDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;
import com.kp.cms.forms.employee.EmpResPubPendApprovalForm;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.helpers.employee.EmpResPubDetailsHelper;
import com.kp.cms.helpers.employee.EmpResPubPendApprovalHelper;
import com.kp.cms.helpers.employee.EmployeeInfoEditHelper;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.transactions.employee.IEmpResPubDetailsTransaction;
import com.kp.cms.transactions.employee.IEmpResPubPendApprovalTransaction;
import com.kp.cms.transactions.employee.IEmployeeInfoEditTransaction;
import com.kp.cms.transactionsimpl.employee.EmpResPubDetailsImpl;
import com.kp.cms.transactionsimpl.employee.EmpResPubPendApprovalImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoEditTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.print.HtmlPrinter;

public class EmpResPubPendApprovalHandler {
	
	private static final Log log = LogFactory.getLog(EmpResPubPendApprovalHandler.class);
	IEmpResPubPendApprovalTransaction empTransaction=new EmpResPubPendApprovalImpl();
	EmpResPubPendApprovalHelper helper= EmpResPubPendApprovalHelper.getInstance();
	private static volatile EmpResPubPendApprovalHandler instance=null;
	/**
	 * 
	 */
	private EmpResPubPendApprovalHandler(){
	}
	
	/**
	 * @return
	 */
	public static EmpResPubPendApprovalHandler getInstance(){
		log.info("Start getInstance of EmpResPubPendApprovalHandler");
		if(instance==null){
			instance=new EmpResPubPendApprovalHandler();
		}
		log.info("End getInstance of EmpResPubPendApprovalHandler");
		return instance;
	}
	
	public List<EmployeeTO> getSearchedEmployeePending(int approverId)throws Exception {
		log.info("enter getSearchedStudents");
		String query ="select empres.employeeId from EmpResearchPublicDetails empres where empres.isApproved=0  and empres.isRejected=0" +
				"and empres.isActive=1 and empres.approverId= "+approverId+" group by empres.employeeId";
		List<Employee> employeelist=empTransaction.getSerchedEmployee(query);
		List<EmployeeTO> employeeToList = helper.convertEmployeeTOtoBO(employeelist);
		log.info("exit getSearchedStudents");
		return employeeToList;
	}
	public List<EmployeeTO> getSearchedEmployeeApproved(int approverId)throws Exception {
		log.info("enter getSearchedStudents");
		String query ="select empres.employeeId from EmpResearchPublicDetails empres " +
				"where empres.isApproved=1  and empres.isRejected=0 and empres.approverId= "+approverId+" and empres.isActive=1 group by empres.employeeId";
		List<Employee> employeelist=empTransaction.getSerchedEmployee(query);
		List<EmployeeTO> employeeToList = helper.convertEmployeeTOtoBO(employeelist);
		log.info("exit getSearchedStudents");
		return employeeToList;
	}

	public void getEmployeeResearchDetails(EmpResPubPendApprovalForm objform ) throws Exception {
		//boolean flag=false;
		List<EmpResearchPublicDetails> empApplicantDetails=empTransaction.GetResearchDetails(Integer.parseInt(objform.getSelectedEmployeeId()));
		
		if (empApplicantDetails != null) {
		//	flag=true;
			helper.convertBoToForm(empApplicantDetails,objform);
		}
		Employee emp=empTransaction.GetEmployee(objform);
		if (emp != null) {
			//	flag=true;
				helper.convertBoToFormEmployee(emp,objform);
			}
		
		//return flag;
	}
	public void getEmployeeResearchDetailsApproved(EmpResPubPendApprovalForm objform ) throws Exception {
		//boolean flag=false;
		List<EmpResearchPublicDetails> empApplicantDetails=empTransaction.getEmployeeResearchDetailsApproved(Integer.parseInt(objform.getSelectedEmployeeId()));
		
		if (empApplicantDetails != null) {
		//	flag=true;
			helper.convertBoToForm(empApplicantDetails,objform);
		}
		Employee emp=empTransaction.GetEmployee(objform);
		if (emp != null) {
			//	flag=true;
				helper.convertBoToFormEmployee(emp,objform);
			}
		
		//return flag;
	}
	
	
	public boolean saveEmpResPub(EmpResPubPendApprovalForm objform) throws Exception
	{
		boolean flag=false;
		
		
		List<EmpResearchPublicDetails> empResPubDetails=helper.convertFormToBo(objform);
		flag=empTransaction.saveEmpResPub(empResPubDetails);
		
		return flag;
	}
	
	public boolean saveResPubApproved(EmpResPubPendApprovalForm objform) throws Exception
	{
		boolean flag=false;
		List<EmpResearchPublicDetails> empResPubDetails=helper.convertFormToBoApproved(objform);
		flag=empTransaction.saveEmpResPub(empResPubDetails);
		return flag;
	}
	
	public List<EmployeeTO> getSearchedEmployee(EmpResPubPendApprovalForm stForm)throws Exception {
		log.info("enter getSearchedStudents");
		int departmentId = 0;
		if (stForm.getTempDepartmentId() != null
				&& !StringUtils.isEmpty(stForm.getTempDepartmentId().trim())
				&& StringUtils.isNumeric(stForm.getTempDepartmentId())) {
			departmentId = Integer.parseInt(stForm.getTempDepartmentId());
		}
		StringBuffer query = empTransaction.getSerchedEmployeeQuery(stForm.getUserId(), stForm.getTempFingerPrintId(),
				departmentId , stForm.getTempName(), stForm.getTempActive(), stForm.isApprovedList(),stForm.isPendingList());
		List<Employee> employeelist=empTransaction.getSerchedEmployee(query);
		
		List<EmployeeTO> employeeToList = helper.convertEmployeeTOtoBO(employeelist, departmentId);
		log.info("exit getSearchedStudents");
		return employeeToList;
	}
	
	public boolean sendMailToEmployee(EmpResPubPendApprovalForm admForm) throws Exception {
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
			if(admForm.getIsReject().equalsIgnoreCase("true")){
				list= temphandle.getDuplicateCheckList(CMSConstants.EMPLOYEE_RESEARCH_REJECTED_TEMPLATE);
			}else{
			 list= temphandle.getDuplicateCheckList(CMSConstants.EMPLOYEE_RESEARCH_APPROVED_TEMPLATE);
			}
			if(list != null && !list.isEmpty()) {

				String desc = list.get(0).getTemplateDescription();
				//send mail to applicant
				String approverAddress=admForm.getEmployeeEmailId();
				String fromName = prop.getProperty(CMSConstants.EMPLOYEE_RESEARCH_PUBLICATION_FROMNAME);
				String fromAddress=CMSConstants.MAIL_USERID;
				String toAddress=approverAddress;
				String name=admForm.getEmployeeName();
				String subject="";
				if(admForm.getIsReject().equalsIgnoreCase("true")){
					subject="Research and Publication Details has been Rejected";
				}
				else{
					subject= "Research and Publication details has been approved";
				}
				String message =desc;
				if(admForm.getIsReject().equalsIgnoreCase("true")){
					if(admForm.getRejectReason()!=null)
					message = message.replace(CMSConstants.TEMPLATE_REJECTED_REASON ,admForm.getRejectReason());
					if(name!=null)
					message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME,name);
				}else
				{
					message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME,name);
				}
				sent=sendMail(toAddress, subject, message, fromName,fromAddress);
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
	
	public void getEmployeeResearchDetailsByEmployeeId(EmpResPubPendApprovalForm objform ) throws Exception {
		//boolean flag=false;
		List<EmpResearchPublicDetails> empApplicantDetails=empTransaction.getEmployeeResearchDetailsApproved(Integer.parseInt(objform.getSelectedEmployeeId()));
		
		if (empApplicantDetails != null) {
		//	flag=true;
			helper.convertBoToForm(empApplicantDetails,objform);
		}
		Employee emp=empTransaction.GetEmployee(objform);
		if (emp != null) {
			//	flag=true;
				helper.convertBoToFormEmployee(emp,objform);
			}
		
		//return flag;
	}
	public void getEmployeeApprovalPendingByEmployeeId(EmpResPubPendApprovalForm objform ) throws Exception {
		//boolean flag=false;
		List<EmpResearchPublicDetails> empApplicantDetails=empTransaction.getEmployeeApprovalPendingByEmployeeId(Integer.parseInt(objform.getSelectedEmployeeId()));
		
		if (empApplicantDetails != null) {
		//	flag=true;
			helper.convertBoToForm(empApplicantDetails,objform);
		}
		Employee emp=empTransaction.GetEmployee(objform);
		if (emp != null) {
			//	flag=true;
				helper.convertBoToFormEmployee(emp,objform);
			}
		
		//return flag;
	}
	
}
