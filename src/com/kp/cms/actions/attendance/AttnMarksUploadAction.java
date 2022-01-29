package com.kp.cms.actions.attendance;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.AttnMarksUploadForm;
import com.kp.cms.handlers.attendance.AttnMarksUploadHandler;
import com.kp.cms.helpers.admin.AdmBioDataCJCHelper;
import com.kp.cms.to.attendance.AttnMarksUploadTo;
import com.kp.cms.to.attendance.PucAttnInternalMarksTo;
import com.kp.cms.to.attendance.PucttnAttendanceTo;

public class AttnMarksUploadAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttnMarksUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnMarksUploadForm attnMarksUploadForm =(AttnMarksUploadForm)form;
		attnMarksUploadForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_ATTN_MARKS_UPLOAD);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadAttnMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnMarksUploadForm attnMarksUploadForm =(AttnMarksUploadForm)form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = attnMarksUploadForm.validate(mapping, request);
		setUserId(request, attnMarksUploadForm);
		try{
			if(errors.isEmpty()){
				FormFile formFile = attnMarksUploadForm.getTheFile();
				String contentType= formFile.getContentType();
				String fileName = formFile.getFileName();
				File file =null;
				Properties prop=new Properties();
				InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(stream);
				List<AttnMarksUploadTo> attnMarksUploadToList=null;
				if(fileName.endsWith(".xls")){
					byte[] fileData = formFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_ATTN_MARKS);
					File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);
					InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
					OutputStream out = new FileOutputStream(file1);
					byte buffer[] = new byte[1024];
					int len;
					while ((len = inputStream.read(buffer)) > 0){
						out.write(buffer, 0, len);
					}
					out.close();
					inputStream.close();
					String source=prop.getProperty(CMSConstants.UPLOAD_ATTN_MARKS);
					file = new File(request.getRealPath("")+ "//TempFiles//"+source);
					
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
				    attnMarksUploadToList = new ArrayList<AttnMarksUploadTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        AttnMarksUploadTo attnMarksUploadTo = new AttnMarksUploadTo();
				        if(row != null) {
				        	for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        			 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setRegNo(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setClasses(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setTestIdent(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkSub1(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkSub2(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkSub3(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkSub4(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkSub5(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkSub6(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkSub7(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkSub8(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkSub9(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkSub10(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkLang(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkPrac1(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkPrac2(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkPrac3(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setMrkPrac4(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setUserCode(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(attnMarksUploadForm.getAcademicYear()!=null && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnMarksUploadTo.setAcademicYear(attnMarksUploadForm.getAcademicYear());
				        			 }
				        		 }
				        	}
				        	attnMarksUploadToList.add(attnMarksUploadTo);
				        }
				    }
				    if(!attnMarksUploadToList.isEmpty()){
				    	isAdded=AttnMarksUploadHandler.getInstance().addAttnMarksUpload(attnMarksUploadToList);
				    }
				    if(isAdded){
			    		//if adding is success
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ATTN_MARKS_SUCCESS);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    		
			    	}else{
			    		//if adding is failure
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ATTN_MARKS_FAILURE);
			    		errors.add(CMSConstants.ERROR, message);
			    		addErrors(request, errors);
			    	}
				}else{
			    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
		    		errors.add(CMSConstants.ERROR, message);
		    		addErrors(request, errors);
		    		return mapping.findForward(CMSConstants.INIT_ATTN_MARKS_UPLOAD);
			    }
			 }else{
			    	saveErrors(request, errors);
			    	return mapping.findForward(CMSConstants.INIT_ATTN_MARKS_UPLOAD);
			    }
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			attnMarksUploadForm.setErrorMessage(msg);
			attnMarksUploadForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ATTN_MARKS_UPLOAD);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadPucttnAttendance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnMarksUploadForm attnMarksUploadForm =(AttnMarksUploadForm)form;
		attnMarksUploadForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_PUCTTN_ATTENDANCE_UPLOAD);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadPucttnAttendance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnMarksUploadForm attnMarksUploadForm =(AttnMarksUploadForm)form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = attnMarksUploadForm.validate(mapping, request);
		setUserId(request, attnMarksUploadForm);
		try{
			if(errors.isEmpty()){
				FormFile formFile = attnMarksUploadForm.getTheFile();
				String contentType= formFile.getContentType();
				String fileName = formFile.getFileName();
				File file =null;
				Properties prop=new Properties();
				InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(stream);
				List<PucttnAttendanceTo> attnUploadToList=null;
				if(fileName.endsWith(".xls")){
					byte[] fileData = formFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_PUCTTN_ATTN);
					File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);
					InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
					OutputStream out = new FileOutputStream(file1);
					byte buffer[] = new byte[1024];
					int len;
					while ((len = inputStream.read(buffer)) > 0){
						out.write(buffer, 0, len);
					}
					out.close();
					inputStream.close();
					String source=prop.getProperty(CMSConstants.UPLOAD_PUCTTN_ATTN);
					file = new File(request.getRealPath("")+ "//TempFiles//"+source);
					
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
				    attnUploadToList = new ArrayList<PucttnAttendanceTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        PucttnAttendanceTo attnUploadTo = new PucttnAttendanceTo();
				        if(row != null) {
				        	for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        			 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setRegNo(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setClasses(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsSub1(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsSub2(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsSub3(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsSub4(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsSub5(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsSub6(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsSub7(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsSub8(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsSub9(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsSub10(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsLang(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsPra1(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsPra2(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsPra3(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAbsPra4(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setPrnRemarks(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setSplAchvmt(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 19 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 String date = removeFileExtension(cell.toString().trim());
				        				 attnUploadTo.setLastUpdte(AdmBioDataCJCHelper.getInstance().ConvertStringToSQLDate(date));
				        			 }
				        			 if(cell.getCellNum() == 20 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setUserCode(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(attnMarksUploadForm.getAcademicYear()!=null && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnUploadTo.setAcademicYear(attnMarksUploadForm.getAcademicYear());
				        			 }
				        		 }
				        	}
				        		attnUploadToList.add(attnUploadTo);
				        }
				    }
				    if(!attnUploadToList.isEmpty()){
				    	isAdded=AttnMarksUploadHandler.getInstance().addAttnUpload(attnUploadToList);
				    }
				    if(isAdded){
			    		//if adding is success
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ATTN_MARKS_SUCCESS);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    		
			    	}else{
			    		//if adding is failure
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ATTN_MARKS_FAILURE);
			    		errors.add(CMSConstants.ERROR, message);
			    		addErrors(request, errors);
			    	}
				}else{
			    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
		    		errors.add(CMSConstants.ERROR, message);
		    		addErrors(request, errors);
		    		return mapping.findForward(CMSConstants.INIT_PUCTTN_ATTENDANCE_UPLOAD);
			    }
			 }else{
			    	saveErrors(request, errors);
			    	return mapping.findForward(CMSConstants.INIT_PUCTTN_ATTENDANCE_UPLOAD);
			    }
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			attnMarksUploadForm.setErrorMessage(msg);
			attnMarksUploadForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_PUCTTN_ATTENDANCE_UPLOAD);
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
	
	public ActionForward initUploadPucattnInternalMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnMarksUploadForm attnMarksUploadForm =(AttnMarksUploadForm)form;
		attnMarksUploadForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_ATTN_INTERNAL_MARKS_UPLOAD);
	}
	
	public ActionForward uploadPucAttnInternalMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnMarksUploadForm attnMarksUploadForm =(AttnMarksUploadForm)form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = attnMarksUploadForm.validate(mapping, request);
		setUserId(request, attnMarksUploadForm);
		try{
			if(errors.isEmpty()){
				FormFile formFile = attnMarksUploadForm.getTheFile();
				String contentType= formFile.getContentType();
				String fileName = formFile.getFileName();
				File file =null;
				Properties prop=new Properties();
				InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(stream);
				List<PucAttnInternalMarksTo> attnInternalMarksToList=null;
				if(fileName.endsWith(".xls")){
					byte[] fileData = formFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_ATTN_INTERNAL_MARKS);
					File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);
					InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
					OutputStream out = new FileOutputStream(file1);
					byte buffer[] = new byte[1024];
					int len;
					while ((len = inputStream.read(buffer)) > 0){
						out.write(buffer, 0, len);
					}
					out.close();
					inputStream.close();
					String source=prop.getProperty(CMSConstants.UPLOAD_ATTN_INTERNAL_MARKS);
					file = new File(request.getRealPath("")+ "//TempFiles//"+source);
					
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
				    attnInternalMarksToList = new ArrayList<PucAttnInternalMarksTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        PucAttnInternalMarksTo attnInternalMarksTo = new PucAttnInternalMarksTo();
				        if(row != null) {
				        	for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        			 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setRegNo(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setClasses(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setTestIdent(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setMrkSub1(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setMrkSub2(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setMrkSub3(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setMrkSub4(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setMrkSub5(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setMrkSub6(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setMrkSub7(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setMrkSub8(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setMrkSub9(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setMrkSub10(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setMrkLang(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setUserCode(cell.toString().trim());
				        			 }
				        			 if(attnMarksUploadForm.getAcademicYear()!=null && !StringUtils.isEmpty(cell.toString().trim())){
				        				 attnInternalMarksTo.setAcademicYear(attnMarksUploadForm.getAcademicYear());
				        			 }
				        		 }
				        	}
				        	attnInternalMarksToList.add(attnInternalMarksTo);
				        }
				    }
				    if(!attnInternalMarksToList.isEmpty()){
				    	isAdded=AttnMarksUploadHandler.getInstance().addAttnInternalMarksUpload(attnInternalMarksToList,attnMarksUploadForm);
				    }
				    if(isAdded){
			    		//if adding is success
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ATTN_MARKS_SUCCESS);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    		
			    	}else{
			    		//if adding is failure
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ATTN_MARKS_FAILURE);
			    		errors.add(CMSConstants.ERROR, message);
			    		addErrors(request, errors);
			    	}
				}else{
			    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
		    		errors.add(CMSConstants.ERROR, message);
		    		addErrors(request, errors);
		    		return mapping.findForward(CMSConstants.INIT_ATTN_INTERNAL_MARKS_UPLOAD);
			    }
			 }else{
			    	saveErrors(request, errors);
			    	return mapping.findForward(CMSConstants.INIT_ATTN_INTERNAL_MARKS_UPLOAD);
			    }
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			attnMarksUploadForm.setErrorMessage(msg);
			attnMarksUploadForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ATTN_INTERNAL_MARKS_UPLOAD);
	}
}
