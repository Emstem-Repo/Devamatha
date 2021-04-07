package com.kp.cms.actions.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.UploadEmployeeDetailsForm;
import com.kp.cms.handlers.employee.UploadEmployeeDetailsHandler;
import com.kp.cms.handlers.exam.UploadInternalOverAllHandler;
import com.kp.cms.to.employee.UploadEmployeeDetailsTo;
import com.kp.cms.to.exam.UploadInternalOverAllTo;
import com.kp.cms.utilities.CommonUtil;

public class UploadEmployeeDetailsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UploadEmployeeDetailsAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadEmployeeDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initUploadEmployeeDetails input");
		UploadEmployeeDetailsForm uploadEmployeeDetailsForm = (UploadEmployeeDetailsForm) form;
		uploadEmployeeDetailsForm.resetFields();
		log.info("Exit inituploadEmployeeDetails input");
		
		return mapping.findForward(CMSConstants.UPLOAD_EMPLOYEE_DETAILS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateEmployeeDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		log.info("Entered updateEmployeeDetails ");
		UploadEmployeeDetailsForm uploadEmployeeDetailsForm=(UploadEmployeeDetailsForm)form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		//errors=uploadEmployeeDetailsForm.validate(mapping, request);
		setUserId(request, uploadEmployeeDetailsForm);
		if(errors.isEmpty()){
			try{
				Map<String, Employee> employeeMap=UploadEmployeeDetailsHandler.getInstance().getEmployeeList();
				Map<String,String> departmentMap=UploadEmployeeDetailsHandler.getInstance().getDepartmentMap();
				Map<String, String> designationMap=UploadEmployeeDetailsHandler.getInstance().getDesignationMap();
				Map<String,String> CountryMap=UploadEmployeeDetailsHandler.getInstance().getCountryMap();
				Map<String, String> StateMap=UploadEmployeeDetailsHandler.getInstance().getStateMap();
				Map<String,String> empTypeMap = UploadEmployeeDetailsHandler.getInstance().getEmpTypeMap();
				Map<String,String> jobTitleMap = UploadEmployeeDetailsHandler.getInstance().getJobTitleMap();
				Map<String,String> workLocationMap = UploadEmployeeDetailsHandler.getInstance().getWorkLocationMap();
				Map<String,String> streamMap = UploadEmployeeDetailsHandler.getInstance().getStreamMap();
				FormFile myFile = uploadEmployeeDetailsForm.getTheFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
				File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<UploadEmployeeDetailsTo> result=new ArrayList<UploadEmployeeDetailsTo>();
			    List<String> duplicateCheck=new ArrayList<String>();
			    if(fileName.endsWith(".csv")){
			         //if the uploaded document is csv file.
			     	  
			     	byte[] fileData    = myFile.getFileData();
				    String source1=prop.getProperty(CMSConstants.UPLOAD_INTERVIEW_CSV);
				    String filePath=request.getRealPath("");
			    	filePath = filePath + "//TempFiles//";
					File file1 = new File(filePath+source1);
				    	 
					InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
					OutputStream out = new FileOutputStream(file1);
					byte buffer[] = new byte[1024];
					int len;
					while ((len = inputStream.read(buffer)) > 0){
						out.write(buffer, 0, len);
					}
					out.close();
					inputStream.close();
					String source=prop.getProperty(CMSConstants.UPLOAD_INTERVIEW_CSV);
					
					file = new File(filePath+source);
					FileInputStream stream1 = new FileInputStream(file);
					LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(stream1));
					
					
					while(parser.getLine()!=null){
						 UploadEmployeeDetailsTo uploadEmployeeDetailsTo = new UploadEmployeeDetailsTo();
						 if(parser.getValueByLabel("employee_id")!=null&& !StringUtils.isEmpty(parser.getValueByLabel("employee_id"))){
		                		uploadEmployeeDetailsTo=new UploadEmployeeDetailsTo();
		                		String employeeId=parser.getValueByLabel("employee_id").trim();
		                		if(employeeId.endsWith(".0"))
		                		{
		                			employeeId=StringUtils.chop(employeeId);
		                			employeeId=StringUtils.chop(employeeId);
		                		}
		                		if(employeeMap!=null && employeeMap.containsKey(employeeId)){
		                			Employee emp=employeeMap.get(employeeId);
		                			if(emp.getId()>0 && emp.getId()==Integer.parseInt(employeeId))
		                			uploadEmployeeDetailsTo.setEid(employeeId);
		                		}
							} 
		                	
		                	if(parser.getValueByLabel("Name")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Name")) ){
		                		if(parser.getValueByLabel("Name").trim().length()>0){
		                			uploadEmployeeDetailsTo.setName(parser.getValueByLabel("Name").trim());
		                		}
		                	}
		                	String name=parser.getValueByLabel("Name").trim();
		                	if(name.length()>=30){
		                		System.out.println(name.length());
		                	}
		                	if(parser.getValueByLabel("emailId")!=null && !StringUtils.isEmpty(parser.getValueByLabel("emailId")) ){
		                		if(parser.getValueByLabel("emailId").trim().length()>0){
		                			uploadEmployeeDetailsTo.setEmailId(parser.getValueByLabel("emailId").trim());
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("dateOfBirth")!=null && !StringUtils.isEmpty(parser.getValueByLabel("dateOfBirth")) && CommonUtil.isValidDate(parser.getValueByLabel("dateOfBirth")) ){
		                		if(parser.getValueByLabel("dateOfBirth").trim().length()>0){
		                			uploadEmployeeDetailsTo.setDob(parser.getValueByLabel("dateOfBirth").trim());
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("bloodGroup")!=null && !StringUtils.isEmpty(parser.getValueByLabel("bloodGroup")) ){
		                		if(parser.getValueByLabel("bloodGroup").trim().length()>0){
		                			uploadEmployeeDetailsTo.setBloodGroup(parser.getValueByLabel("bloodGroup").trim());
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("mobile")!=null && !StringUtils.isEmpty(parser.getValueByLabel("mobile"))&& StringUtils.isNumeric(parser.getValueByLabel("mobile"))){
		                		if(parser.getValueByLabel("mobile").trim().length()>0){
		                			String mobile=parser.getValueByLabel("mobile").trim();
		                			if(mobile.endsWith(".0")){
		                				mobile=StringUtils.chop(mobile);
		                				mobile=StringUtils.chop(mobile);
		                			}
		                			uploadEmployeeDetailsTo.setMobile(mobile);
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("department")!=null&& !StringUtils.isEmpty(parser.getValueByLabel("department")) ){
		                		if(parser.getValueByLabel("department").trim().length()>0){
		                			if(departmentMap!=null && departmentMap.containsKey(parser.getValueByLabel("department").trim()))
		                			uploadEmployeeDetailsTo.setDeptId(departmentMap.get(parser.getValueByLabel("department").trim()));
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("designation")!=null && !StringUtils.isEmpty(parser.getValueByLabel("designation")) ){
		                		if(parser.getValueByLabel("designation").trim().length()>0){
		                			if(designationMap!=null && designationMap.containsKey(parser.getValueByLabel("designation").trim()))
		                			uploadEmployeeDetailsTo.setDesignationId(designationMap.get(parser.getValueByLabel("designation").trim()));
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("permanentAddressLine1")!=null && !StringUtils.isEmpty(parser.getValueByLabel("permanentAddressLine1")) ){
		                		if(parser.getValueByLabel("permanentAddressLine1").trim().length()>0){
		                			uploadEmployeeDetailsTo.setPermanentAddressLine1(parser.getValueByLabel("permanentAddressLine1").trim());
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("permanentAddressLine2")!=null  && !StringUtils.isEmpty(parser.getValueByLabel("permanentAddressLine2")) ){
		                		if(parser.getValueByLabel("permanentAddressLine2").trim().length()>0){
		                			uploadEmployeeDetailsTo.setPermanentAddressLine2(parser.getValueByLabel("permanentAddressLine2").trim());
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("permanentCountry")!=null && !StringUtils.isEmpty(parser.getValueByLabel("permanentCountry")) ){
		                		if(parser.getValueByLabel("permanentCountry").trim().length()>0){
		                			if(CountryMap!=null && CountryMap.containsKey(parser.getValueByLabel("permanentCountry").trim()))
		                			uploadEmployeeDetailsTo.setPermanentCountryId(CountryMap.get(parser.getValueByLabel("permanentCountry").trim()));
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("permanentState")!=null && !StringUtils.isEmpty(parser.getValueByLabel("permanentState")) ){
		                		if(parser.getValueByLabel("permanentState").trim().length()>0){
		                			if(StateMap!=null && StateMap.containsKey(parser.getValueByLabel("permanentState").trim()))
		                			uploadEmployeeDetailsTo.setPermanentStateId(StateMap.get(parser.getValueByLabel("permanentState").trim()));
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("permanentCity")!=null && !StringUtils.isEmpty(parser.getValueByLabel("permanentCity")) ){
		                		if(parser.getValueByLabel("permanentCity").trim().length()>0){
		                			uploadEmployeeDetailsTo.setPermanentCity(parser.getValueByLabel("permanentCity").trim());
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("permanentZipCode")!=null && !StringUtils.isEmpty(parser.getValueByLabel("permanentZipCode")) ){
		                		if(parser.getValueByLabel("permanentZipCode").trim().length()>0){
		                			String pin=parser.getValueByLabel("permanentZipCode").trim();
		                			if(pin.endsWith(".0")){
		                				pin=StringUtils.chop(pin);
		                				pin=StringUtils.chop(pin);
		                			}
		                			uploadEmployeeDetailsTo.setPermanentPinCode(pin);
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("currentAddressLine1")!=null && !StringUtils.isEmpty(parser.getValueByLabel("currentAddressLine1")) ){
		                		if(parser.getValueByLabel("currentAddressLine1").trim().length()>0){
		                			uploadEmployeeDetailsTo.setCurrentAddressLine1(parser.getValueByLabel("currentAddressLine1").trim());
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("currentAddressLine2")!=null && !StringUtils.isEmpty(parser.getValueByLabel("currentAddressLine2")) ){
		                		if(parser.getValueByLabel("currentAddressLine2").trim().length()>0){
		                			uploadEmployeeDetailsTo.setCurrentAddressLine2(parser.getValueByLabel("currentAddressLine2").trim());
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("currentCountry")!=null && !StringUtils.isEmpty(parser.getValueByLabel("currentCountry")) ){
		                		if(parser.getValueByLabel("currentCountry").trim().length()>0){
		                			if(CountryMap!=null && CountryMap.containsKey(parser.getValueByLabel("currentCountry").trim()))
		                			uploadEmployeeDetailsTo.setCurrentCountryId(CountryMap.get(parser.getValueByLabel("currentCountry").trim()));
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("currentState")!=null && !StringUtils.isEmpty(parser.getValueByLabel("currentState")) ){
		                		if(parser.getValueByLabel("currentState").trim().length()>0){
		                			if(StateMap!=null && StateMap.containsKey(parser.getValueByLabel("currentState").trim()))
		                			uploadEmployeeDetailsTo.setCurrentStateId(StateMap.get(parser.getValueByLabel("currentState").trim()));
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("currentCity")!=null && !StringUtils.isEmpty(parser.getValueByLabel("currentCity")) ){
		                		if(parser.getValueByLabel("currentCity").trim().length()>0){
		                			uploadEmployeeDetailsTo.setCurrentCity(parser.getValueByLabel("currentCity").trim());
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("currentZipCode")!=null && !StringUtils.isEmpty(parser.getValueByLabel("currentZipCode")) ){
		                		if(parser.getValueByLabel("currentZipCode").trim().length()>0){
		                			String pin=parser.getValueByLabel("currentZipCode").trim();
		                			if(pin.endsWith(".0")){
		                				pin=StringUtils.chop(pin);
		                				pin=StringUtils.chop(pin);
		                			}
		                			uploadEmployeeDetailsTo.setCurrentPinCode(pin);
		                		}
		                	}
		                	
		                	if(parser.getValueByLabel("fingerPrintId")!=null && !StringUtils.isEmpty(parser.getValueByLabel("fingerPrintId")) ){
		                		if(parser.getValueByLabel("fingerPrintId").trim().length()>0){
		                			String fingerPrint=parser.getValueByLabel("fingerPrintId").trim();
		                			if(fingerPrint.endsWith(".0")){
		                				fingerPrint=StringUtils.chop(fingerPrint);
		                				fingerPrint=StringUtils.chop(fingerPrint);
		                			}
		                			uploadEmployeeDetailsTo.setFingerPrintId(fingerPrint);
		                		}
		                	}
		                	if(parser.getValueByLabel("emptype")!=null&& !StringUtils.isEmpty(parser.getValueByLabel("emptype")) ){
		                		if(parser.getValueByLabel("emptype").trim().length()>0){
		                			if(empTypeMap!=null && empTypeMap.containsKey(parser.getValueByLabel("emptype").trim()))
		                			uploadEmployeeDetailsTo.setEmpTypeId(empTypeMap.get(parser.getValueByLabel("emptype").trim()));
		                		}
		                	}
		                	if(parser.getValueByLabel("jobTitle")!=null && !StringUtils.isEmpty("jobTitle")){
		                		if(parser.getValueByLabel("jobTitle").trim().length()>0){
		                			if(jobTitleMap!=null && jobTitleMap.containsKey(parser.getValueByLabel("jobTitle").trim()))
		                				uploadEmployeeDetailsTo.setJobTitleId(jobTitleMap.get(parser.getValueByLabel("jobTitle").trim()));
		                		}
		                	}
		                	if(parser.getValueByLabel("workLocation")!=null && !StringUtils.isEmpty("workLocation")){
		                		if(parser.getValueByLabel("workLocation").trim().length()>0){
		                			if(workLocationMap!=null && workLocationMap.containsKey(parser.getValueByLabel("workLocation").trim()))
		                				uploadEmployeeDetailsTo.setWorkLocationId(workLocationMap.get(parser.getValueByLabel("workLocation").trim()));
		                		}
		                	}
		                	if(parser.getValueByLabel("stream")!=null && !StringUtils.isEmpty("stream")){
		                		if(parser.getValueByLabel("stream").trim().length()>0){
		                			if(streamMap!=null && streamMap.containsKey(parser.getValueByLabel("stream").trim()))
		                				uploadEmployeeDetailsTo.setStreamId(streamMap.get(parser.getValueByLabel("stream").trim()));
		                		}
		                	}
		                	if(parser.getValueByLabel("isTeachingStaff")!=null && !StringUtils.isEmpty(parser.getValueByLabel("isTeachingStaff")) ){
		                		if(parser.getValueByLabel("isTeachingStaff").trim().length()>0){
		                			String isTeachingStaff=parser.getValueByLabel("isTeachingStaff").trim();
		                			uploadEmployeeDetailsTo.setIsTeachingStaff(isTeachingStaff);
		                		}
		                	}
		                	if(parser.getValueByLabel("grade")!=null && !StringUtils.isEmpty(parser.getValueByLabel("grade")) ){
		                		if(parser.getValueByLabel("grade").trim().length()>0){
		                			String grade=parser.getValueByLabel("grade").trim();
		                			uploadEmployeeDetailsTo.setGrade(grade);
		                		}
		                	}
		                	if(parser.getValueByLabel("highestQualification")!=null && !StringUtils.isEmpty(parser.getValueByLabel("highestQualification")) ){
		                		if(parser.getValueByLabel("highestQualification").trim().length()>0){
		                			String highestQualification=parser.getValueByLabel("highestQualification").trim();
		                			uploadEmployeeDetailsTo.setHighestQualification(highestQualification);
		                		}
		                	}
		                	if(parser.getValueByLabel("reservationCategory")!=null && !StringUtils.isEmpty(parser.getValueByLabel("reservationCategory")) ){
		                		if(parser.getValueByLabel("reservationCategory").trim().length()>0){
		                			String reservationCategory=parser.getValueByLabel("reservationCategory").trim();
		                			uploadEmployeeDetailsTo.setReservationCategory(reservationCategory);
		                		}
		                	}
		                	if(parser.getValueByLabel("joiningDate")!=null && !StringUtils.isEmpty(parser.getValueByLabel("joiningDate")) && CommonUtil.isValidDate(parser.getValueByLabel("joiningDate"))){
		                		if(parser.getValueByLabel("joiningDate").trim().length()>0){
		                			String joingingDate=parser.getValueByLabel("joiningDate").trim();
		                			uploadEmployeeDetailsTo.setJoiningDate(joingingDate);
		                		}
		                	}
		                	if(parser.getValueByLabel("dateOfRetirement")!=null && !StringUtils.isEmpty(parser.getValueByLabel("dateOfRetirement")) && CommonUtil.isValidDate(parser.getValueByLabel("dateOfRetirement"))){
		                		if(parser.getValueByLabel("dateOfRetirement").trim().length()>0){
		                			String dateOfRetirement=parser.getValueByLabel("dateOfRetirement").trim();
		                			uploadEmployeeDetailsTo.setDateOfRetirement(dateOfRetirement);
		                		}
		                	}
		                	if(parser.getValueByLabel("bankAccountNumber")!=null && !StringUtils.isEmpty(parser.getValueByLabel("bankAccountNumber")) ){
		                		if(parser.getValueByLabel("bankAccountNumber").trim().length()>0){
		                			String bankAccount=parser.getValueByLabel("bankAccountNumber").trim();
		                			uploadEmployeeDetailsTo.setBankAccountNo(bankAccount);
		                		}
		                	}
		                	if(parser.getValueByLabel("gender")!=null && !StringUtils.isEmpty(parser.getValueByLabel("gender")) ){
		                		if(parser.getValueByLabel("gender").trim().length()>0){
		                			if(parser.getValueByLabel("gender").trim().equalsIgnoreCase("M"))
		                				uploadEmployeeDetailsTo.setGender("MALE");
		                			else if(parser.getValueByLabel("gender").trim().equalsIgnoreCase("F"))
		                				uploadEmployeeDetailsTo.setGender("FEMALE");
		                		}
		                	}
						 if(uploadEmployeeDetailsTo!=null && uploadEmployeeDetailsTo.getEid()!=null && !uploadEmployeeDetailsTo.getEid().isEmpty()){
							 String checkDuplicate=uploadEmployeeDetailsTo.getEid();
		 		             if(!duplicateCheck.contains(checkDuplicate))
		 		            	result.add(uploadEmployeeDetailsTo);
		 		            	duplicateCheck.add(checkDuplicate);
		 		            }else if(uploadEmployeeDetailsTo.getEid()==null && uploadEmployeeDetailsTo!=null){
				            	result.add(uploadEmployeeDetailsTo);
				            }
			            }
			      	}else{
			      		ActionMessage message = new ActionMessage(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    		return mapping.findForward(CMSConstants.UPLOAD_EMPLOYEE_DETAILS);
			      	}
					boolean isAdded = false;
						if(result!= null){
//							setUserId(request, uploadEmployeeDetailsForm);
							isAdded=UploadEmployeeDetailsHandler.getInstance().addUploadedData(result,uploadEmployeeDetailsForm.getUserId(),employeeMap);
				    	if(isAdded){
				    		//if adding is success.
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EMPLOYEE_SUCCESS);
				    		messages.add("messages", message);
				    		saveMessages(request, messages);
				    	}else{
				    		//if adding is failure.
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EMPLOYEE_FAILED);
				    		errors.add(CMSConstants.ERROR, message);
				    		addErrors(request, errors);
				    	}
				    }
				      	
			}catch (Exception exception) {
				// TODO: handle exception
				String msg = super.handleApplicationException(exception);
				uploadEmployeeDetailsForm.setErrorMessage(msg);
				uploadEmployeeDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE1);
			}
		}else{
			addErrors(request, errors);
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.UPLOAD_EMPLOYEE_DETAILS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.UPLOAD_EMPLOYEE_DETAILS);
	}

}
