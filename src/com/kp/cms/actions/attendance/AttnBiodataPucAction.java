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
import com.kp.cms.forms.attendance.AttnBiodataPucForm;
import com.kp.cms.handlers.attendance.AttnBiodataPucHandler;
import com.kp.cms.helpers.admin.AdmBioDataCJCHelper;
import com.kp.cms.to.attendance.AttnBioDataPucTo;

public class AttnBiodataPucAction extends BaseDispatchAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttnBioDataUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//attnBioDataForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_ATTN_BIODATA);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadAttnBioData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttnBiodataPucForm attnBioDataForm = (AttnBiodataPucForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = attnBioDataForm.validate(mapping, request);
		setUserId(request, attnBioDataForm);
		try{
			if(errors.isEmpty()){
				FormFile formFile = attnBioDataForm.getTheFile();
				String contentType= formFile.getContentType();
				String fileName = formFile.getFileName();
				File file =null;
				Properties prop=new Properties();
				InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(stream);
				List<AttnBioDataPucTo> bioDataList=null;
				if(fileName.endsWith(".xls")){
					byte[] fileData = formFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_ATTN_BIODATA);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_ATTN_BIODATA);
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
				    bioDataList = new ArrayList<AttnBioDataPucTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        AttnBioDataPucTo to = new AttnBioDataPucTo();
				        if(row != null) {
				        	for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        			 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
				        				to.setAppNo(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setPercentage(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setRegNo(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setClasses(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setName(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setYear(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setSection(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setFatherName(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setSecndLang(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setReligion(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setCaste(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setScstbcbt(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setSex(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 String str=cell.toString().trim();
				        				 to.setDob(AdmBioDataCJCHelper.ConvertStringToSQLDate(str));
				        			 }
				        			 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setNationality(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setState(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setLastInst(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setTelephone(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setAddress1(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 19 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setAddress2(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 20 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setAddress3(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 21 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setAddress4(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 22 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setOffRemarks(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 23 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setPrnRemarks(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 24 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setScholarship(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 25 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 String str= cell.toString().trim();
				        				 to.setDateAdm(AdmBioDataCJCHelper.ConvertStringToSQLDate(str));
				        			 }
				        			 if(cell.getCellNum() == 26 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setAnnIncome(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 27 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setMaintFees(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 28 && !StringUtils.isEmpty(cell.toString().trim())){
					        			to.setFailed(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 29 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setDmmyNotUsd(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 30 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setDmmyNotUsd1(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 31 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setElecCode1(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 32 && !StringUtils.isEmpty(cell.toString().trim())){
					        			to.setElecPos1(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 33 && !StringUtils.isEmpty(cell.toString().trim())){
					        			to.setElecCode2(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 34 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecPos2(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 35 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecCode3(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 36 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecPos3(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 37 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecCode4(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 38 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecPos4(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 39 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecCode5(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 40 && !StringUtils.isEmpty(cell.toString().trim())){
				        				 	to.setElecPos5(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 41 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecCode6(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 42 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecPos6(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 43 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecCode7(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 44 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecPos7(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 45 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecCode8(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 46 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setElecPos8(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 47 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setBloodGroup(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 48 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setCetFeePaid(cell.toString().trim());
				        			 }
				        			 if(cell.getCellNum() == 49 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setAieeeFee(removeFileExtension(cell.toString().trim()));
				        			 }
				        			 if(cell.getCellNum() == 50 && !StringUtils.isEmpty(cell.toString().trim())){
					        				to.setUserCode(cell.toString().trim());
				        			 }
				        			 if(attnBioDataForm.getAcademicYear()!=null && !StringUtils.isEmpty(cell.toString().trim())){
				        				 to.setAcademicYear(attnBioDataForm.getAcademicYear());
				        			 }
				        		 }
				        	}
				        	if(to!=null){
				        		bioDataList.add(to);
				        	}
				        }
				    }
				    if(!bioDataList.isEmpty()){
				    	isAdded=AttnBiodataPucHandler.getInstance().attnBioDataUpload(bioDataList,attnBioDataForm);
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
			attnBioDataForm.setErrorMessage(msg);
			attnBioDataForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ATTN_BIODATA);
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
