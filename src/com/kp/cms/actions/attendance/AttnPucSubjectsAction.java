package com.kp.cms.actions.attendance;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.PromoteMarksUploadForm;
import com.kp.cms.forms.attendance.AttendanceDataMigrationForm;
import com.kp.cms.forms.attendance.AttnPucSubjectForm;
import com.kp.cms.handlers.admin.AdmBioDataCJCHandler;
import com.kp.cms.handlers.admission.PromoteMarksUploadHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceDataMigrationReportHandler;
import com.kp.cms.handlers.attendance.AttnPucSubjectsHandler;
import com.kp.cms.helpers.admin.AdmBioDataCJCHelper;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.to.attendance.AttnMarksUploadTo;
import com.kp.cms.to.attendance.AttnPucCetMarksTo;
import com.kp.cms.to.attendance.AttnPucDefineRangeTo;
import com.kp.cms.to.attendance.AttnPucSubjectsTo;
import com.kp.cms.utilities.CurrentAcademicYear;

public class AttnPucSubjectsAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttnPucSubjectUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnPucSubjectForm attnPucSubjectForm =(AttnPucSubjectForm)form;
		attnPucSubjectForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_ATTN_SUBJECT_UPLOAD);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadAttnPucSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnPucSubjectForm attnPucSubjectForm =(AttnPucSubjectForm)form;
		 ActionErrors errors = attnPucSubjectForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request,attnPucSubjectForm);
		try{
			if(errors.isEmpty()){
				FormFile myFile = attnPucSubjectForm.getTheFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<AttnPucSubjectsTo> attnPucSubjectsTos= null;
			   
			    if(fileName.endsWith(".xls")){
			    	byte[] fileData = myFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_ATTN_SUBJECTS);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_ATTN_SUBJECTS);
					file = new File(filePath+source);
					
					POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
				    HSSFWorkbook workbook = new HSSFWorkbook(system);
				    
				    HSSFSheet sheet = workbook.getSheetAt(0);
				    HSSFRow row;
				    HSSFCell cell;
				    int rows = sheet.getPhysicalNumberOfRows();

				    int cols = 0; // No of columns
				    int tmp = 0;
				    // This trick ensures that we get the data properly even if it doesn't start from first few rows
				    for(int i = 0; i < rows; i++) {
				        row = sheet.getRow(i);
				        if(row != null) {
				            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				            if(tmp > cols) {
					            cols = tmp;
					            break;
				            }
				        }
				    }
				    boolean isAdded = false;
				    attnPucSubjectsTos=new ArrayList<AttnPucSubjectsTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        AttnPucSubjectsTo attnSubjectsTo =new AttnPucSubjectsTo();
				        if(row != null) {
				        	 for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setClasses(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubCde1(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubject1(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubCde2(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubject2(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubCde3(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubject3(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubCde4(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubject4(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubCde5(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubject5(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubCde6(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubject6(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubCde7(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubject7(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubCde8(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubject8(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubCde9(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubject9(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 19 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubcde10(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 20 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setSubject10(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 21 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setPraCode1(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 22 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setPractical1(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 23 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setPraCode2(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 24 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setPractical2(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 25 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setPraCode3(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 26 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setPractical3(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 27 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setPraCode4(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 28 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setPractical4(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 29 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setMaintFees(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 30 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 attnSubjectsTo.setElecPre(cell.toString().trim());
				        				 }
				        				 if(attnPucSubjectForm.getAcademicYear()!=null && !attnPucSubjectForm.getAcademicYear().isEmpty()){
				        					 attnSubjectsTo.setAcademicYear(attnPucSubjectForm.getAcademicYear());
				        				 }
				        			 }
		                         
		                     } 
				        	attnPucSubjectsTos.add(attnSubjectsTo);
				        	 }
				        }
	            	 if(!attnPucSubjectsTos.isEmpty()){
	            		 isAdded= AttnPucSubjectsHandler.getInstance().uploadPucSubjects(attnPucSubjectsTos);
	            	 }
			   
			    if(isAdded){
		    		//if adding is success
		    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ADM_BIODATA_SUCCESS);
		    		messages.add("messages", message);
		    		saveMessages(request, messages);
		    		
		    	}else{
		    		//if adding is failure
		    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ADM_BIODATA_FAILURE);
		    		errors.add(CMSConstants.ERROR, message);
		    		addErrors(request, errors);
		    	}
		 }else{
		    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
		    }
			}else{
		    	saveErrors(request, errors);
		    }
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			attnPucSubjectForm.setErrorMessage(msg);
			attnPucSubjectForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ATTN_SUBJECT_UPLOAD);
		
	}
	/**
	 * @author christ
	 *
	 */
	protected class ByteArrayStreamInfo implements StreamInfo {
	
	protected String contentType;
	protected byte[] bytes;

	public ByteArrayStreamInfo(String contentType, byte[] myDfBytes) {
		this.contentType = contentType;
		this.bytes = myDfBytes;
	}

	public String getContentType() {
		return contentType;
	}

	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(bytes);
	}
}
	/**
	 * @param fileName
	 * @return
	 */
	public String removeFileExtension(String fileName)
	{ 
	if(null != fileName && fileName.contains("."))
	{
	return fileName.substring(0, fileName.lastIndexOf("."));
	}
	return fileName;
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttnCetMarksUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnPucSubjectForm attnPucSubjectForm =(AttnPucSubjectForm)form;
		attnPucSubjectForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_CET_MARKS_UPLOAD);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadAttnPucCetMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnPucSubjectForm attnPucSubjectForm =(AttnPucSubjectForm)form;
		 ActionErrors errors = attnPucSubjectForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request,attnPucSubjectForm);
		try{
			if(errors.isEmpty()){
				FormFile myFile = attnPucSubjectForm.getTheFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<AttnPucCetMarksTo> attnCetMarksToList= null;
			   
			    if(fileName.endsWith(".xls")){
			    	byte[] fileData = myFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_ATTN_CET_MARKS);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_ATTN_CET_MARKS);
					file = new File(filePath+source);
					
					POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
				    HSSFWorkbook workbook = new HSSFWorkbook(system);
				    
				    HSSFSheet sheet = workbook.getSheetAt(0);
				    HSSFRow row;
				    HSSFCell cell;
				    int rows = sheet.getPhysicalNumberOfRows();

				    int cols = 0; // No of columns
				    int tmp = 0;
				    // This trick ensures that we get the data properly even if it doesn't start from first few rows
				    for(int i = 0; i < rows; i++) {
				        row = sheet.getRow(i);
				        if(row != null) {
				            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				            if(tmp > cols) {
					            cols = tmp;
					            break;
				            }
				        }
				    }
				    boolean isAdded = false;
				    attnCetMarksToList=new ArrayList<AttnPucCetMarksTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        AttnPucCetMarksTo attnCetMarksTo =new AttnPucCetMarksTo();
				        if(row != null) {
				        	 for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 attnCetMarksTo.setRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 attnCetMarksTo.setClasses(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 attnCetMarksTo.setTestId(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 attnCetMarksTo.setMrkSub1(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 attnCetMarksTo.setMrkSub2(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 attnCetMarksTo.setMrkSub3(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 attnCetMarksTo.setMrkSub4(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 attnCetMarksTo.setUserCode(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 attnCetMarksTo.setPcbRank(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 attnCetMarksTo.setPcmRank(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 attnCetMarksTo.setAieee(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(attnPucSubjectForm.getAcademicYear()!=null && !attnPucSubjectForm.getAcademicYear().isEmpty()){
				        					 attnCetMarksTo.setAcademicYear(attnPucSubjectForm.getAcademicYear());
				        				 }
				        			 }
		                         
		                     } 
				        		attnCetMarksToList.add(attnCetMarksTo);
				        	 }
				        }
	            	 if(!attnCetMarksToList.isEmpty()){
	            		 isAdded= AttnPucSubjectsHandler.getInstance().uploadPucCetMarks(attnCetMarksToList,attnPucSubjectForm);
	            	 }
			   
			    if(isAdded){
		    		//if adding is success
		    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ADM_BIODATA_SUCCESS);
		    		messages.add("messages", message);
		    		saveMessages(request, messages);
		    		
		    	}else{
		    		//if adding is failure
		    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ADM_BIODATA_FAILURE);
		    		errors.add(CMSConstants.ERROR, message);
		    		addErrors(request, errors);
		    	}
		 }else{
		    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
		    }
			}else{
		    	saveErrors(request, errors);
		    }
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			attnPucSubjectForm.setErrorMessage(msg);
			attnPucSubjectForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<String> duplicate=attnPucSubjectForm.getRegNosList();
		if(duplicate!=null && !duplicate.isEmpty()){
			StringBuilder ids=new StringBuilder();
			Iterator it=duplicate.iterator();
			while(it.hasNext()){
				ids.append(it.next().toString()).append(", ");
			}
			int len=ids.length()-2;
	        if(ids.toString().endsWith(", ")){
	            ids.setCharAt(len, ' ');
	        }
			errors.add("error",new ActionError( "knowledgepro.attendance.duplicate" ,ids.toString().trim()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_CET_MARKS_UPLOAD);
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttnCetMarksReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnPucSubjectForm attnPucSubjectForm =(AttnPucSubjectForm)form;
		attnPucSubjectForm.resetFields();
		setClassMapToForm(attnPucSubjectForm);
		return mapping.findForward(CMSConstants.INIT_CET_MARKS_REPORT);
	}
	/**
	 * @param attnPucSubjectForm
	 * @throws Exception
	 */
	public void setClassMapToForm(AttnPucSubjectForm attnPucSubjectForm)throws Exception{
//		Calendar calendar = Calendar.getInstance();
//		int currentYear = calendar.get(Calendar.YEAR);
//		
//		int year = CurrentAcademicYear.getInstance().getAcademicyear();
//		if (year != 0) {
//			currentYear = year;
//		}
//		if (attnPucSubjectForm.getAcademicYear() != null && !attnPucSubjectForm.getAcademicYear().isEmpty()) {
//			currentYear=Integer.parseInt(attnPucSubjectForm.getAcademicYear());
//		}
//		Map<Integer, String> classMap=CommonAjaxHandler.getInstance().getCourseByYear(currentYear);
//		attnPucSubjectForm.setClassesMap(classMap);
		String mode = "attnPucSub";
		Map<String,String> courses=PromoteMarksUploadHandler.getInstance().getCourses(mode);
		attnPucSubjectForm.setCourses(courses);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAttnCetMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AttnPucSubjectForm attnPucSubjectForm =(AttnPucSubjectForm)form;
		 ActionErrors errors = attnPucSubjectForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {	
			try {
			     List<AttnPucCetMarksTo> attnCetMarksToList=AttnPucSubjectsHandler.getInstance().getAttnCetMarks(attnPucSubjectForm, session);
			     session.setAttribute("attnCetMarksToList", attnCetMarksToList);
			}catch(ApplicationException ae){
				String msg = super.handleApplicationException(ae);
				attnPucSubjectForm.setErrorMessage(msg);
				attnPucSubjectForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
					throw e;
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_CET_MARKS_REPORT);
		}
		return mapping.findForward(CMSConstants.INIT_CET_MARKS_REPORT_RESULT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttnDefineRangeUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnPucSubjectForm attnPucSubjectForm =(AttnPucSubjectForm)form;
		attnPucSubjectForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_DEFINE_RANGE_UPLOAD);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadAttnDefineRange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnPucSubjectForm attnPucSubjectForm =(AttnPucSubjectForm)form;
		 ActionErrors errors = attnPucSubjectForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request,attnPucSubjectForm);
		try{
			if(errors.isEmpty()){
				FormFile myFile = attnPucSubjectForm.getTheFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<AttnPucDefineRangeTo> attnDefineRangeTo= null;
			   
			    if(fileName.endsWith(".xls")){
			    	byte[] fileData = myFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_ATTN_DEFINE_RANGE);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_ATTN_DEFINE_RANGE);
					file = new File(filePath+source);
					
					POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
				    HSSFWorkbook workbook = new HSSFWorkbook(system);
				    
				    HSSFSheet sheet = workbook.getSheetAt(0);
				    HSSFRow row;
				    HSSFCell cell;
				    int rows = sheet.getPhysicalNumberOfRows();

				    int cols = 0; // No of columns
				    int tmp = 0;
				    // This trick ensures that we get the data properly even if it doesn't start from first few rows
				    for(int i = 0; i < rows; i++) {
				        row = sheet.getRow(i);
				        if(row != null) {
				            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				            if(tmp > cols) {
					            cols = tmp;
					            break;
				            }
				        }
				    }
				    boolean isAdded = false;
				    attnDefineRangeTo=new ArrayList<AttnPucDefineRangeTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        AttnPucDefineRangeTo defineRangeTo =new AttnPucDefineRangeTo();
				        if(row != null) {
				        	 for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClasses(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setStartRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setEndRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setTheoryOrPractical(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setCombineClass1(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass1StartRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass1EndRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setCombineClass2(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass2StartRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass2EndRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setCombineClass3(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass3StartRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass3EndRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setCombineClass4(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass4StartRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass4EndRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setCombineClass5(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass5StartRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass5EndRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 19 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setCombineClass6(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 20 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass6StartRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 21 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass6EndRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 22 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setCombineClass7(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 23 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass7StartRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 24 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass7EndRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 25 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setCombineClass8(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 26 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass8StartRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 27 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass8EndRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 28 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setCombineClass9(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 29 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass9StartRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 30 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 defineRangeTo.setClass9EndRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(attnPucSubjectForm.getAcademicYear()!=null && !attnPucSubjectForm.getAcademicYear().isEmpty()){
				        					 defineRangeTo.setAcademicYear(attnPucSubjectForm.getAcademicYear());
				        				 }
				        			 }
		                         
		                     } 
				        	 if(defineRangeTo!=null){
				        		 attnDefineRangeTo.add(defineRangeTo);
				        	 	}
				        	 }
				        }
	            	 if(!attnDefineRangeTo.isEmpty()){
	            		 isAdded= AttnPucSubjectsHandler.getInstance().uploadDefineRange(attnDefineRangeTo);
	            	 }
			   
			    if(isAdded){
		    		//if adding is success
		    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ADM_BIODATA_SUCCESS);
		    		messages.add("messages", message);
		    		saveMessages(request, messages);
		    		
		    	}else{
		    		//if adding is failure
		    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ADM_BIODATA_FAILURE);
		    		errors.add(CMSConstants.ERROR, message);
		    		addErrors(request, errors);
		    	}
		 }else{
		    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
		    }
			}else{
		    	saveErrors(request, errors);
		    }
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			attnPucSubjectForm.setErrorMessage(msg);
			attnPucSubjectForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_DEFINE_RANGE_UPLOAD);
		
	}
}
