package com.kp.cms.actions.admission;

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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.kp.cms.forms.admission.AdmissionSubjectForm;
import com.kp.cms.handlers.admission.AdmissionSubjectHandler;
import com.kp.cms.to.admission.AdmissionSubjectTo;


public class AdmissionSubjectAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AdmissionSubjectAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdmissionSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdmissionSubjectForm admSubjectForm = (AdmissionSubjectForm) form;
		admSubjectForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_ADM_SUBJECT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadAdmissionSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		AdmissionSubjectForm admSubjectForm = (AdmissionSubjectForm) form;
 		log.info("call of uploadAdmissionSubject method in AdmissionSubjectAction class.");
		 ActionErrors errors = admSubjectForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request,admSubjectForm);
		try{
			if(errors.isEmpty()){
		}
			FormFile myFile = admSubjectForm.getTheFile();
		    String contentType = myFile.getContentType();
		    String fileName    = myFile.getFileName();
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    List<AdmissionSubjectTo> admissionSubjectTo= null;
		    if(admSubjectForm.getAcademicYear()!=null && !admSubjectForm.getAcademicYear().isEmpty())
		    {
		    if(fileName.endsWith(".xls")){
		       byte[] fileData = myFile.getFileData();
		      String source1=prop.getProperty(CMSConstants.UPLOAD_ADM_SUBJECT);
		      File file1 = new File(request.getRealPath("")+"//TempFiles//"+source1);
		      InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
		      OutputStream out = new FileOutputStream(file1);
		      byte buffer[] = new byte[1024];
				int len;
				while ((len = inputStream.read(buffer)) > 0){
					out.write(buffer, 0, len);
				}
				out.close();
				inputStream.close();
				String source=prop.getProperty(CMSConstants.UPLOAD_ADM_SUBJECT);
				file = new File(request.getRealPath("")+"//TempFiles//"+source);
				
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
			    admissionSubjectTo=new ArrayList<AdmissionSubjectTo>();
			    for(int r = 1; r < rows; r++) {
			        row = sheet.getRow(r);
			        AdmissionSubjectTo admSubjectTo=new AdmissionSubjectTo();
			        if(row != null) {
			        	for(int c = 0; c < cols;c++) {
			        		cell = row.getCell((byte)c);
			        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
			        			 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
			        				 admSubjectTo.setClasses(cell.toString().trim());
		        				 }
			        			 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
			        				 admSubjectTo.setSubject1(cell.toString().trim());
		        				 }
		        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
		        					 admSubjectTo.setSubcode1(removeFileExtension(cell.toString().trim()));
		        				 }
		        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
		        					 admSubjectTo.setSubject2(cell.toString().trim());
		        				 }
		        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
		        					 admSubjectTo.setSubcode2(removeFileExtension(cell.toString().trim()));
		        				 }
		        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
		        					 admSubjectTo.setSubject3(cell.toString().trim());
		        				 }
		        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
		        					 admSubjectTo.setSubcode3(removeFileExtension(cell.toString().trim()));
		        				 }
		        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
		        					 admSubjectTo.setSubject4(cell.toString().trim());
		        				 }
		        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
		        					 admSubjectTo.setSubcode4(removeFileExtension(cell.toString().trim()));
		        				 }
		        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell.toString().trim())){
		        					 admSubjectTo.setSubject5(cell.toString().trim());
		        				 }
		        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
		        					 admSubjectTo.setSubcode5(removeFileExtension(cell.toString().trim()));
		        				 }
		        				 if(admSubjectForm.getAcademicYear()!=null && !admSubjectForm.getAcademicYear().isEmpty()){
		        					 admSubjectTo.setAcademicYear(admSubjectForm.getAcademicYear());
		        				 }
			        		 }
			        	}
			        	admissionSubjectTo.add(admSubjectTo);
			        }
			    }
			    if(!admissionSubjectTo.isEmpty()){
           		 isAdded=AdmissionSubjectHandler.getInstance().uploadAdmSubject(admissionSubjectTo);
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
		}else
		{   ActionMessage message = new ActionMessage(CMSConstants.PLEASE_SELECT_YEAR);
    		errors.add(CMSConstants.ERROR, message);
    		addErrors(request, errors);	
		}
			
		}catch(BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			admSubjectForm.setErrorMessage(msg);
			admSubjectForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ADM_SUBJECT);
	}
	
/**
 * @author Administrator
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
}
