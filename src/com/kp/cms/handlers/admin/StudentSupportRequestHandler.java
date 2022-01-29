package com.kp.cms.handlers.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentSupportRequestBo;
import com.kp.cms.bo.supportrequest.CategoryBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.StudentSupportRequestForm;
import com.kp.cms.handlers.hostel.AbsentiesListHandler;
import com.kp.cms.helpers.admin.StudentSupportRequestHelper;
import com.kp.cms.to.admin.StudentSupportRequestTo;
import com.kp.cms.transactions.admin.IStudentSupportRequestTransaction;
import com.kp.cms.transactionsimpl.admin.StudentSupportRequestTransImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;

public class StudentSupportRequestHandler {
	IStudentSupportRequestTransaction transaction=StudentSupportRequestTransImpl.getInstance();
	StudentSupportRequestHelper helper=StudentSupportRequestHelper.getInstance();
	public static volatile StudentSupportRequestHandler studentSupportRequestHandler = null;
	
	private StudentSupportRequestHandler(){
		
	}
	
	public static StudentSupportRequestHandler getInstance() {
		if (studentSupportRequestHandler == null) {
			studentSupportRequestHandler = new StudentSupportRequestHandler();
			return studentSupportRequestHandler;
		}
		return studentSupportRequestHandler;
	}
	public Map<Integer, String> getSupportCategory() throws Exception{
		Map<Integer, String> map= new HashMap<Integer, String>();
		List<CategoryBo> list=transaction.getCategoryForStudent();
		if(list!=null){
			Iterator<CategoryBo> iterator=list.iterator();
			while (iterator.hasNext()) {
				CategoryBo categoryBo = (CategoryBo) iterator.next();
				map.put(categoryBo.getId(), categoryBo.getName());
			}
		}
		return map;
	}
	
	public boolean addStudentRequest(
		StudentSupportRequestForm studentSupportRequestForm, String studentId, HttpServletRequest request) throws Exception{
		boolean flag;
		StudentSupportRequestBo studentSupportRequestBo=helper.convertFormToBo(studentSupportRequestForm,studentId);
		flag=transaction.saveSupportRequest(studentSupportRequestBo,request);
		return flag;
	}
	
	public void getStudentSupportRequest(
			String studentId, StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("from StudentSupportRequestBo a where a.isActive=1 and a.studentId="+studentId+" order by a.dateOfSubmission");
		List<StudentSupportRequestBo> list=transaction.getStudentSupportRequest(stringBuilder.toString());
		List<StudentSupportRequestTo> studentSupportRequestTos=helper.convertBotoTos(list,"student");
		studentSupportRequestForm.setList(studentSupportRequestTos);
	}
	
	public void getAdminSupportRequest(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		List<StudentSupportRequestBo> list=transaction.getAdminSupportRequest(studentSupportRequestForm.getUserId());
		List<StudentSupportRequestTo> studentSupportRequestTos=helper.convertBotoTos(list,"admin");
		studentSupportRequestForm.setList(studentSupportRequestTos);
	}
	
	public Map<Integer, String> getSupportCategoryForAdmin() throws Exception{
		Map<Integer, String> map= new HashMap<Integer, String>();
		List<CategoryBo> list=transaction.getCategoryForAdmin();
		if(list!=null){
			Iterator<CategoryBo> iterator=list.iterator();
			while (iterator.hasNext()) {
				CategoryBo categoryBo = (CategoryBo) iterator.next();
				map.put(categoryBo.getId(), categoryBo.getName());
			}
		}
		return map;
	}
	public boolean addAdminSupportRequest(
			StudentSupportRequestForm studentSupportRequestForm, HttpServletRequest request) throws Exception{
		boolean flag;
		StudentSupportRequestBo studentSupportRequestBo=helper.convertFormToBoForAdmin(studentSupportRequestForm);
		flag=transaction.saveSupportRequest(studentSupportRequestBo,request);
		return flag;
	}
	public boolean getPendingListOfSupportReq(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		boolean flag=false;
		Map<Integer, String> map=getSupportCategoryMap();
		List<StudentSupportRequestBo> list=transaction.getPendingSupportReq(studentSupportRequestForm.getUserId());
		if(list!=null && !list.isEmpty()){
			flag=true;
			List<StudentSupportRequestTo> studentSupportRequestTos=helper.convertSupportBoToTos(list,CommonUtil.sortMapByValue(map));
			studentSupportRequestForm.setList(studentSupportRequestTos);
		}
		return flag;
	}
	private Map<Integer, String> getSupportCategoryMap() throws Exception{
		Map<Integer, String> map= new HashMap<Integer, String>();
		List<CategoryBo> list=transaction.getSupportCategory();
		if(list!=null){
			Iterator<CategoryBo> iterator=list.iterator();
			while (iterator.hasNext()) {
				CategoryBo categoryBo = (CategoryBo) iterator.next();
				map.put(categoryBo.getId(), categoryBo.getName());
			}
		}
		return map;
	}
	public void updateCategory(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		boolean flag=transaction.updateCategory(studentSupportRequestForm.getId(),studentSupportRequestForm.getCategoryId(),studentSupportRequestForm.getUserId());
	}
	public boolean updateStatusAndRemarks(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		boolean flag=transaction.updateStatusAndRemarks(studentSupportRequestForm.getId(),studentSupportRequestForm.getStatus(),
				studentSupportRequestForm.getRemarks(),studentSupportRequestForm.getUserId());
		if(flag && !studentSupportRequestForm.getStatus().equalsIgnoreCase("In Progress")){
			StudentSupportRequestBo studentSupportRequestBo=transaction.getStudentSupportRequestBoById(studentSupportRequestForm.getId());
			StudentSupportRequestTo studentSupportRequestTo=helper.convertBotoTo(studentSupportRequestBo);
			sendMailAndSms(studentSupportRequestTo);
		}
		return flag;
	}
	private void sendMailAndSms(StudentSupportRequestTo studentSupportRequestTo)throws Exception {
		Properties prop1 = new Properties();
        InputStream in1 = AbsentiesListHandler.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
        prop1.load(in1);
		String senderNumber=prop1.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop1.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		String desc1="";
		SMSTemplateHandler temphandle1=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list1= null;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		String fromName = prop.getProperty(CMSConstants.STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME);
		String fromAddress=CMSConstants.MAIL_USERID;
			List<GroupTemplate> list=null;
			//get template and replace dynamic data
			TemplateHandler temphandle=TemplateHandler.getInstance();
			String var=null;
			if(studentSupportRequestTo.getRegNo()!=null){
				var="forStudent";
				list1=temphandle1.getDuplicateCheckList(0,CMSConstants.SUPPORT_REQUEST_STATUS_STUDENT_SMS);
				 list= temphandle.getDuplicateCheckList(CMSConstants.SUPPORT_REQUEST_STATUS_STUDENT_MAIL);
			}else{
				var="forSaff";
				list1=temphandle1.getDuplicateCheckList(0,CMSConstants.SUPPORT_REQUEST_STATUS_STAFF_SMS);
				 list= temphandle.getDuplicateCheckList(CMSConstants.SUPPORT_REQUEST_STATUS_STAFF_MAIL);
			}
			String emailID="";
			String regNo="";
			String category="";
			String status="";
			String employeeId="";
			String SubDate=""; 
			String phoneNum="";
			String requestId="";
			String des="";
			
			if(studentSupportRequestTo.getEmail()!=null && !studentSupportRequestTo.getEmail().isEmpty()){
				if(list != null && !list.isEmpty()) {
					des =list.get(0).getTemplateDescription();
						if(studentSupportRequestTo.getEmail()!=null){
							emailID=studentSupportRequestTo.getEmail();
						}
						if(studentSupportRequestTo.getRegNo()!=null){
							regNo=studentSupportRequestTo.getRegNo();
						}
						if(studentSupportRequestTo.getCategoryName()!=null){
							category=studentSupportRequestTo.getCategoryName();
						}
						if(studentSupportRequestTo.getStatus()!=null){
							status=studentSupportRequestTo.getStatus();
						}
						if(studentSupportRequestTo.getDateOfSubmssion()!=null){
							SubDate=studentSupportRequestTo.getDateOfSubmssion();
						}
						if(studentSupportRequestTo.getEmployeeId()!=null){
							employeeId=studentSupportRequestTo.getEmployeeId();
						}
						if(studentSupportRequestTo.getRequestId()!=null){
							requestId=studentSupportRequestTo.getRequestId();
						}
					des=des.replace(CMSConstants.TEMPLATE_CATEGORY_NAME,category);
					des=des.replace(CMSConstants.SMS_TEMPLATE_STATUS,status);
					des=des.replace(CMSConstants.SUBMISSION_DATE,SubDate);
					des=des.replace(CMSConstants.REQUEST_ID,requestId);
					if(var.equalsIgnoreCase("forStudent")){
						des=des.replace(CMSConstants.TEMPLATE_SMS_REGISTERNO,regNo);
					}else{
						des=des.replace(CMSConstants.TEMPLATE_EMPLOYEE_ID,employeeId);
					}
					String subject="Support Request Status";
					boolean flag=sendMail(emailID, subject, des, fromName, fromAddress);
					 regNo="";
					 category="";
					 status="";
					 employeeId="";
					 SubDate="";
					 requestId="";
				}
			}
			if(studentSupportRequestTo.getMobileNo()!=null && !studentSupportRequestTo.getMobileNo().isEmpty()){
				if(list1!=null && !list1.isEmpty()){
					desc1 = list1.get(0).getTemplateDescription();
					if(studentSupportRequestTo.getRegNo()!=null){
						regNo=studentSupportRequestTo.getRegNo();
					}
					if(studentSupportRequestTo.getMobileNo()!=null){
						phoneNum="91"+studentSupportRequestTo.getMobileNo();
					}
					if(studentSupportRequestTo.getCategoryName()!=null){
						category=studentSupportRequestTo.getCategoryName();
					}
					if(studentSupportRequestTo.getStatus()!=null){
						status=studentSupportRequestTo.getStatus();
					}
					if(studentSupportRequestTo.getDateOfSubmssion()!=null){
						SubDate=studentSupportRequestTo.getDateOfSubmssion();
					}
					if(studentSupportRequestTo.getEmployeeId()!=null){
						employeeId=studentSupportRequestTo.getEmployeeId();
					}
					if(studentSupportRequestTo.getRequestId()!=null){
						requestId=studentSupportRequestTo.getRequestId();
					}
					desc1=desc1.replace(CMSConstants.TEMPLATE_CATEGORY_NAME,category);
					desc1=desc1.replace(CMSConstants.SMS_TEMPLATE_STATUS,status);
					desc1=desc1.replace(CMSConstants.SUBMISSION_DATE,SubDate);
					desc1=desc1.replace(CMSConstants.REQUEST_ID,requestId);
				if(var.equalsIgnoreCase("forStudent")){
					desc1=desc1.replace(CMSConstants.TEMPLATE_SMS_REGISTERNO,regNo);
				}else{
					desc1=desc1.replace(CMSConstants.TEMPLATE_EMPLOYEE_ID,employeeId);
				}
				boolean flag1=sendSMS(phoneNum,desc1,senderNumber,senderName);
				}
			}
		}
	
	private boolean sendSMS(String phoneNum, String body,String senderNumber,String senderName)throws Exception {
		boolean sentSms=false;
		if(StringUtils.isNumeric(phoneNum) && (phoneNum.length()==12 && body.length()<=160)){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(phoneNum);
			mob.setMessageBody(body);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			sentSms=PropertyUtil.getInstance().save(mob);
		}
		return sentSms;
	}
	
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
	public Map<Integer, String> getDeptMapForAdmin() throws Exception{

		Map<Integer, String> map= new HashMap<Integer, String>();
		List<CategoryBo> list=transaction.getSupportCategory();
		if(list!=null){
			Iterator<CategoryBo> iterator=list.iterator();
			while (iterator.hasNext()) {
				CategoryBo categoryBo = (CategoryBo) iterator.next();
				map.put(categoryBo.getDepartmentId().getId(), categoryBo.getDepartmentId().getName());
			}
		}
		return map;
	
	}
	
	public boolean searchTheSupportRequestList(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		boolean flag=false;
		Map<Integer, String> map=getSupportCategoryMap();
		String query=helper.queryForSearchList(studentSupportRequestForm);
		List<StudentSupportRequestBo> list=transaction.getSearchList(query);
		if(list!=null && !list.isEmpty()){
			flag=true;
			List<StudentSupportRequestTo> studentSupportRequestTos=helper.convertSupportBoToTos(list,map);
			studentSupportRequestForm.setList(studentSupportRequestTos);
			/*studentSupportRequestForm.setNoOfDays(null);
			studentSupportRequestForm.setDeptId(null);
			studentSupportRequestForm.setStatus("Pending");*/
		}
		return flag;
	}
	
	public void updateCategoryByAdmin(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		boolean flag=transaction.updateCategory(studentSupportRequestForm.getId(),studentSupportRequestForm.getCategoryId(),studentSupportRequestForm.getUserId());
		if(flag){
			List<StudentSupportRequestTo> list1=new ArrayList<StudentSupportRequestTo>();
			List<StudentSupportRequestTo> list=studentSupportRequestForm.getList();
			Iterator<StudentSupportRequestTo> iterator=list.iterator();
			while (iterator.hasNext()) {
				StudentSupportRequestTo studentSupportRequestTo = (StudentSupportRequestTo) iterator.next();
				if(studentSupportRequestTo.getId()==studentSupportRequestForm.getId()){
					studentSupportRequestTo.setCatgryId(Integer.parseInt(studentSupportRequestForm.getCategoryId()));
				}
				list1.add(studentSupportRequestTo);
			}
			studentSupportRequestForm.setList(null);
			studentSupportRequestForm.setList(list1);
			studentSupportRequestForm.setStatus("Pending");
		}
	}
	
	public boolean updateStatusAndRemarksByAdmin(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		boolean flag=transaction.updateStatusAndRemarks(studentSupportRequestForm.getId(),studentSupportRequestForm.getStatus(),
				studentSupportRequestForm.getRemarks(),studentSupportRequestForm.getUserId());
		if(flag){
			StudentSupportRequestBo studentSupportRequestBo=transaction.getStudentSupportRequestBoById(studentSupportRequestForm.getId());
			StudentSupportRequestTo studentSupportRequestTo=helper.convertBotoTo(studentSupportRequestBo);
			sendMailAndSms(studentSupportRequestTo);
			//remove modified record and add modified data record
			List<StudentSupportRequestTo> list1=new ArrayList<StudentSupportRequestTo>();
			List<StudentSupportRequestTo> list=studentSupportRequestForm.getList();
			Iterator<StudentSupportRequestTo> iterator=list.iterator();
			while (iterator.hasNext()) {
				StudentSupportRequestTo studentSupportRequestTo2 = (StudentSupportRequestTo) iterator.next();
				if(studentSupportRequestTo2.getId()==studentSupportRequestForm.getId()){
					studentSupportRequestTo2.setStatus(studentSupportRequestForm.getStatus());
					studentSupportRequestTo2.setRemarks(studentSupportRequestForm.getRemarks());
				}
					list1.add(studentSupportRequestTo2);
			}
			studentSupportRequestForm.setList(null);
			studentSupportRequestForm.setList(list1);
			studentSupportRequestForm.setStatus("Pending");
		}
		return flag;
	}
	/**
	 * getting the absenties list
	 * @param absentiesListForm
	 * @throws Exception
	 */
	public Date getPreviousDate(Date date,int days)throws Exception{
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, -days);
		return c.getTime();
	}
	public Object[] getAdminSupportRequestId(String id) throws Exception{
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("select sup_requests.id,sup_requests.user_id,sup_requests.description, date_Format(sup_requests.date_of_submission,'%d/%m/%y')," +
				" sup_category.email from sup_requests left join sup_category ON sup_requests.sup_category_id = sup_category.id" +
				" where sup_requests.is_active=1 and sup_requests.id="+Integer.parseInt(id));
		Object[] objects=transaction.getAdminSupportRequestId(stringBuilder);
		return objects;
	}
	public void sendMailWhoseMailForCategory(String email, String dateOfSubmission,
			String description, String strngId) throws Exception{
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		String descrition="";
		String fromName = prop.getProperty(CMSConstants.STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME);
		String fromAddress=CMSConstants.MAIL_USERID;
		List<GroupTemplate> list=null;
		TemplateHandler temphandle=TemplateHandler.getInstance();
		list=temphandle.getDuplicateCheckList(CMSConstants.CATEGORY_MAIL_SUPPORT_REQUEST);
		if(list!=null && !list.isEmpty()){
			descrition =list.get(0).getTemplateDescription();
			descrition=descrition.replace(CMSConstants.SUBMISSION_DATE,dateOfSubmission);
			descrition=descrition.replace(CMSConstants.REQUEST_ID,strngId);
			descrition=descrition.replace(CMSConstants.TEMPLATE_DESCRIPTION,description);
			String subject1="Support Request "+strngId;
			boolean flag=sendMail(email, subject1, descrition, fromName, fromAddress);
		}
	
	}
	public void getPreviousSupportRequests(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("from StudentSupportRequestBo s where s.isActive=1");
		if(studentSupportRequestForm.getDeptId().startsWith("ST")){
			stringBuilder.append(" and s.studentId.registerNo='"+studentSupportRequestForm.getRegOrUserId()+"'");
		}else{
			stringBuilder.append(" and s.userId.employee.fingerPrintId='"+studentSupportRequestForm.getRegOrUserId()+"'");
		}
		List<StudentSupportRequestBo> list=transaction.getSearchList(stringBuilder.toString());
		if(list!=null && !list.isEmpty()){
			List<StudentSupportRequestTo> list1=helper.convertBostoTos(list, studentSupportRequestForm);
			studentSupportRequestForm.setPreviousList(list1);
		}
	}
	public void supportRequestForStudent(
			StudentSupportRequestForm studentSupportRequestForm,
			HttpServletRequest request) throws Exception{
		boolean flag=false;
		Student student=transaction.checkRegisterNoAvailable(studentSupportRequestForm.getRegNo());
		if(student!=null){
			StringBuilder stringBuilder=new StringBuilder();
			studentSupportRequestForm.setId(student.getId());
			if(student.getAdmAppln()!=null){
				if(student.getAdmAppln().getPersonalData()!=null){
					if(student.getAdmAppln().getPersonalData().getFirstName()!=null){
						stringBuilder.append(student.getAdmAppln().getPersonalData().getFirstName());
					}
					if(student.getAdmAppln().getPersonalData().getMiddleName()!=null){
						stringBuilder.append(student.getAdmAppln().getPersonalData().getMiddleName());
					}
				}
			}
			request.setAttribute("admOperation", "add");
			studentSupportRequestForm.setNameOfStudent(stringBuilder.toString());
			flag=true;
		}
		if(flag){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("from StudentSupportRequestBo s where s.isActive=1 and s.studentId.registerNo='"+studentSupportRequestForm.getRegNo()+"'");
			List<StudentSupportRequestBo> list=transaction.getSearchList(stringBuilder.toString());
			if(list!=null && !list.isEmpty()){
				List<StudentSupportRequestTo> list1=helper.convertBotoTos(list, "student");
				studentSupportRequestForm.setList(list1);
			}

			Map<Integer, String> map= new HashMap<Integer, String>();
			List<CategoryBo> list1=transaction.getCategoryForStudent();
			if(list1!=null){
				Iterator<CategoryBo> iterator=list1.iterator();
				while (iterator.hasNext()) {
					CategoryBo categoryBo = (CategoryBo) iterator.next();
					map.put(categoryBo.getId(), categoryBo.getName());
				}
			}
		studentSupportRequestForm.setCategoryMap(map);
		studentSupportRequestForm.setFlag("true");
		}else{
			request.setAttribute("list", "list");
			throw new Exception();
		}
	}
	
	public String getOpenIssuesToMail(java.sql.Date sqlDate) throws Exception{
		StringBuilder stringBuilder=null;
		try {
			List<StudentSupportRequestBo> list=transaction.getNoOfIssuesInOpen(sqlDate);
			if(list!=null && !list.isEmpty()){
				stringBuilder=new StringBuilder();
				/*this is showing no of open issues on category
				 * Map<String,Map<String,Integer>> map=new HashMap<String, Map<String,Integer>>();
				Map<String,Integer> subMap=null;
				for (StudentSupportRequestBo studentSupportRequestBo : list) {
					if(map.containsKey(studentSupportRequestBo.getCategoryId().getName())){
						subMap=map.get(studentSupportRequestBo.getCategoryId().getName());
					}else{
						subMap=new HashMap<String, Integer>();
					}
					if(subMap.containsKey(studentSupportRequestBo.getCategoryId().getDepartmentId().getName())){
						int count=subMap.get(studentSupportRequestBo.getCategoryId().getDepartmentId().getName());
						subMap.put(studentSupportRequestBo.getCategoryId().getDepartmentId().getName(), count+1);
					}else{
						subMap.put(studentSupportRequestBo.getCategoryId().getDepartmentId().getName(),1);
					}
					map.put(studentSupportRequestBo.getCategoryId().getName(), subMap);
				}
				stringBuilder.append("<table width = '100%'> <tr> <td width='20'> </td><td><table style='border:1px solid #000000; font-family: verdana; font-size:10pt;' rules='all'><tr> <th> Category </th> <th> Department </th> <th> No of issues open</th></tr> ");
				Iterator<Entry<String, Map<String,Integer>>> iterator=map.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<java.lang.String, java.util.Map<java.lang.String, java.lang.Integer>> entry = (Map.Entry<java.lang.String, java.util.Map<java.lang.String, java.lang.Integer>>) iterator.next();
					Map<String,Integer> entryMap=entry.getValue();
					Iterator<Entry<String, Integer>> iterator2=entryMap.entrySet().iterator();
					while (iterator2.hasNext()) {
						Map.Entry<java.lang.String, java.lang.Integer> entry2 = (Map.Entry<java.lang.String, java.lang.Integer>) iterator2.next();
						stringBuilder.append("<tr><td align='left'> "+entry.getKey()+"</td> <td align='left'>"+entry2.getKey()+"</td><td align='center'>"+entry2.getValue() +"</td></tr>");
					}
				}*/
				stringBuilder.append("<table width = '100%'> <tr> <td width='20'> </td><td><table style='border:1px solid #000000; font-family: verdana; font-size:10pt;' rules='all'><tr> <th> Request Id </th> <th> Date </th> <th> Issue Raised By</th><th> Campus</th><th>Category</th><th>Department</th><th>Issue</th></tr> ");
				for (StudentSupportRequestBo studentSupportRequestBo : list) {
					stringBuilder.append("<tr><td align='left'>");
					if(studentSupportRequestBo.getStudentId()!=null){
						stringBuilder.append("ST"+studentSupportRequestBo.getId());
					}else if(studentSupportRequestBo.getUserId()!=null){
						stringBuilder.append("S"+studentSupportRequestBo.getId());
					}
					stringBuilder.append("</td>");
					stringBuilder.append("<td align='center'>");
					stringBuilder.append(CommonUtil.formatDates(studentSupportRequestBo.getDateOfSubmission()));
					stringBuilder.append("</td>");
					stringBuilder.append("<td align='left'>");
					if(studentSupportRequestBo.getStudentId()!=null){
						if(studentSupportRequestBo.getStudentId().getRegisterNo()!=null){
							stringBuilder.append(studentSupportRequestBo.getStudentId().getRegisterNo());
						}
					}else if(studentSupportRequestBo.getUserId()!=null){
						if(studentSupportRequestBo.getUserId().getEmployee()!=null){
							if(studentSupportRequestBo.getUserId().getEmployee().getFingerPrintId()!=null){
								stringBuilder.append(studentSupportRequestBo.getUserId().getEmployee().getFingerPrintId());
							}
						}
					}
					stringBuilder.append("</td>");
					stringBuilder.append("<td align='left'>");
					if(studentSupportRequestBo.getStudentId()!=null){
						if(studentSupportRequestBo.getStudentId().getAdmAppln()!=null){
							if(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId()!=null){
								if(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getWorkLocation()!=null){
									if(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getWorkLocation().getName()!=null){
										stringBuilder.append(studentSupportRequestBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getWorkLocation().getName());
									}
								}
							}
						}
					}else if(studentSupportRequestBo.getUserId()!=null){
						if(studentSupportRequestBo.getUserId().getEmployee()!=null){
							if(studentSupportRequestBo.getUserId().getEmployee().getWorkLocationId()!=null){
								if(studentSupportRequestBo.getUserId().getEmployee().getWorkLocationId().getName()!=null){
									stringBuilder.append(studentSupportRequestBo.getUserId().getEmployee().getWorkLocationId().getName());
								}
							}
						}
					}
					stringBuilder.append("</td>");
					stringBuilder.append("<td align='left'>");
					if(studentSupportRequestBo.getCategoryId()!=null){
						if(studentSupportRequestBo.getCategoryId().getName()!=null){
							stringBuilder.append(studentSupportRequestBo.getCategoryId().getName());
						}
					}
					stringBuilder.append("</td>");
					stringBuilder.append("<td align='left'>");
					if(studentSupportRequestBo.getCategoryId()!=null){
						if(studentSupportRequestBo.getCategoryId().getDepartmentId()!=null){
							if(studentSupportRequestBo.getCategoryId().getDepartmentId().getName()!=null){
								stringBuilder.append(studentSupportRequestBo.getCategoryId().getDepartmentId().getName());
							}
						}
					}
					stringBuilder.append("</td>");
					stringBuilder.append("<td align='left'>");
					if(studentSupportRequestBo.getDescription()!=null){
						stringBuilder.append(studentSupportRequestBo.getDescription());
					}
					stringBuilder.append("</td></tr>");
				}
				stringBuilder.append("</table> </td> </tr> </table>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
}
