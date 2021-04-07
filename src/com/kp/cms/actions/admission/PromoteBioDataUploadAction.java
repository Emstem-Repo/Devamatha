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
import com.kp.cms.actions.admission.AdmissionTcDetailsAction.ByteArrayStreamInfo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.PromoteBioDataUploadForm;
import com.kp.cms.handlers.admission.PromoteBioDataUploadHandler;
import com.kp.cms.to.admission.PromoteBioDataUploadTo;

public class PromoteBioDataUploadAction extends BaseDispatchAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPromoteBioDataUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromoteBioDataUploadForm promoteBioDataForm = (PromoteBioDataUploadForm) form;
		promoteBioDataForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_PROMOTE_BIODATA);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward promoteBioDataUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromoteBioDataUploadForm promoteBioDataForm = (PromoteBioDataUploadForm) form;
		 ActionErrors errors = promoteBioDataForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request, promoteBioDataForm);
		try{
			if(errors.isEmpty()){
				FormFile formFile = promoteBioDataForm.getTheFile();
				String contentType = formFile.getContentType();
				String fileName = formFile.getFileName();
				Properties prop = new Properties();
				File file = null;
				InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inputStream);
				List<PromoteBioDataUploadTo> promoteBiodataList = null;
				 	if(fileName.endsWith(".xls")){
				 		byte[] fileData = formFile.getFileData();
				 		String source = prop.getProperty(CMSConstants.UPLOAD_PROMOTE_BIODATA);
				 		File file1 = new File(request.getRealPath("")+"//TempFiles//"+source);
				 		InputStream stream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
						OutputStream out = new FileOutputStream(file1);
						byte buffer[] = new byte[1024];
						int len;
						while ((len = stream.read(buffer)) > 0){
							out.write(buffer, 0, len);
						}
						out.close();
						stream.close();
						String source1=prop.getProperty(CMSConstants.UPLOAD_PROMOTE_BIODATA);
						file = new File(request.getRealPath("")+"//TempFiles//"+source1);
						
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
					    promoteBiodataList = new ArrayList<PromoteBioDataUploadTo>();
					    for(int r = 1; r < rows; r++) {
					    	row= sheet.getRow(r);
					    	PromoteBioDataUploadTo bioDataUploadTo = new PromoteBioDataUploadTo();
					    	if(row!=null){
					    		for(int c = 0; c < cols;c++){
					    			cell=row.getCell((byte)c);
					    			 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
					    				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
					    					 bioDataUploadTo.setRegNo(removeFileExtension(cell.toString()));
					    				 }
					    				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString())){
					    					 bioDataUploadTo.setClassCode(cell.toString());
					    				 }
					    				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString())){
					    					 bioDataUploadTo.setName(cell.toString());
					    				 }
					    				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString())){
					    					 bioDataUploadTo.setSection(cell.toString());
					    				 }
										 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString())){
											 bioDataUploadTo.setFatherName(cell.toString());				    					 
										 }
										 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString())){
											 bioDataUploadTo.setSecndLang(cell.toString());
										 }
										 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 bioDataUploadTo.setRank(removeFileExtension(cell.toString()));
										 }
										 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 bioDataUploadTo.setStudentNo(removeFileExtension(cell.toString()));
										 }
										 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString())){
											 bioDataUploadTo.setMotherName(cell.toString());
										 }
										 if(promoteBioDataForm.getAcademicYear()!=null && !promoteBioDataForm.getAcademicYear().isEmpty()){
											 bioDataUploadTo.setAcademicYear(promoteBioDataForm.getAcademicYear());
										 }
					    			 }
					    		}
					    		promoteBiodataList.add(bioDataUploadTo);
					    	}
					    }
					    if(promoteBiodataList!=null && !promoteBiodataList.isEmpty()){
					    	isAdded = PromoteBioDataUploadHandler.getInstance().uploadPromoteBioData(promoteBiodataList);
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
				 	}
				 	else{
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
			promoteBioDataForm.setErrorMessage(msg);
			promoteBioDataForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_PROMOTE_BIODATA);
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
		
}
