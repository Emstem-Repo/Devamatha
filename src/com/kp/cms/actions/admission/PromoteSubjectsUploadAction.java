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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.PromoteSubjectsUploadForm;
import com.kp.cms.handlers.admission.PromoteSubjectsUploadHandler;
import com.kp.cms.to.admission.PromoteSubjectsUploadTo;

public class PromoteSubjectsUploadAction extends BaseDispatchAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPromoteSubjectsUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromoteSubjectsUploadForm proSubjectsUploadForm = (PromoteSubjectsUploadForm)form;
		proSubjectsUploadForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_PROMOTE_SUBJECTS_UPLOAD);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward UploadPromoteSubjects(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromoteSubjectsUploadForm proSubjectsUploadForm = (PromoteSubjectsUploadForm)form;
		 ActionErrors errors = proSubjectsUploadForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request, proSubjectsUploadForm);
		try{
			if(errors.isEmpty()){
				boolean duplicate = PromoteSubjectsUploadHandler.getInstance().checkDuplicate(proSubjectsUploadForm);
				if(duplicate){
					errors.add("error", new ActionError("knowledgepro.admin.PromoteSubject.Year.exists"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_PROMOTE_SUBJECTS_UPLOAD);
				}
				FormFile formFile = proSubjectsUploadForm.getTheFile();
				String contentType = formFile.getContentType();
				String fileName = formFile.getFileName();
				Properties prop = new Properties();
				File file = null;
				InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inputStream);
				List<PromoteSubjectsUploadTo> promoteSubjectsList = null;
				 	if(fileName.endsWith(".xls")){
				 		byte[] fileData = formFile.getFileData();
				 		String source = prop.getProperty(CMSConstants.UPLOAD_PROMOTE_SUBJECTS);
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
						String source1=prop.getProperty(CMSConstants.UPLOAD_PROMOTE_SUBJECTS);
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
					    promoteSubjectsList = new ArrayList<PromoteSubjectsUploadTo>();
					    for(int r = 1; r < rows; r++) {
					    	row= sheet.getRow(r);
					    	PromoteSubjectsUploadTo promoteSubjectsUploadTo = new PromoteSubjectsUploadTo();
					    	if(row!=null){
					    		for(int c = 0; c < cols;c++){
					    			cell=row.getCell((byte)c);
					    			 if(cell != null && !StringUtils.isEmpty(cell.toString().trim())) {
					    				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					    					 promoteSubjectsUploadTo.setClassCode(removeFileExtension(cell.toString().trim()));
					    				 }
					    				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					    					 promoteSubjectsUploadTo.setSection(removeFileExtension(cell.toString().trim()));
					    				 }
					    				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					    					 promoteSubjectsUploadTo.setStartNo(removeFileExtension(cell.toString().trim()));
					    				 }
					    				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					    					 promoteSubjectsUploadTo.setEndNo(removeFileExtension(cell.toString().trim()));
					    				 }
										 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setGroupCode(removeFileExtension(cell.toString().trim()));			    					 
										 }
										 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubCde1(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubject1(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubCde3(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubject3(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubCde4(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubject4(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubCde5(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubject5(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubCde6(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubject6(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubCde7(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
											 promoteSubjectsUploadTo.setSubject7(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setHasPract1(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setHasPract3(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 19 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setHasPract4(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 20 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setHasPract5(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 21 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setHasPract6(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 22 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setHasPract7(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 23 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setUnSubCd1(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 24 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setUnSubcd3(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 25 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setUnSubcd4(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 26 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setUnSubcd5(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 27 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setUnSubcd6(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 28 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()).trim())){
											 promoteSubjectsUploadTo.setUnSubcd7(removeFileExtension(cell.toString().trim()));
										 }
										 if(proSubjectsUploadForm.getAcademicYear()!=null && !proSubjectsUploadForm.getAcademicYear().isEmpty()){
											 promoteSubjectsUploadTo.setAcademicYear(proSubjectsUploadForm.getAcademicYear());
										 }
					    			 }
					    		}
					    		if(promoteSubjectsUploadTo!=null){
					    			promoteSubjectsList.add(promoteSubjectsUploadTo);
					    		}
					    	}
					    }
					    if(promoteSubjectsList!=null && !promoteSubjectsList.isEmpty()){
					    	isAdded = PromoteSubjectsUploadHandler.getInstance().UploadPromoteSubjects(promoteSubjectsList);
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
			proSubjectsUploadForm.setErrorMessage(msg);
			proSubjectsUploadForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_PROMOTE_SUBJECTS_UPLOAD);
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
