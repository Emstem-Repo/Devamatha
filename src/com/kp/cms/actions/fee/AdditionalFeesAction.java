package com.kp.cms.actions.fee;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.kp.cms.forms.fee.AdditionalFeesForm;
import com.kp.cms.handlers.admin.AdmBioDataCJCHandler;
import com.kp.cms.handlers.fee.AdditionalFeesHandler;
import com.kp.cms.handlers.pettycash.AccountHeadsHandler;
import com.kp.cms.helpers.admin.AdmBioDataCJCHelper;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.to.fee.AdditionalFeesTo;
import com.kp.cms.to.fee.FeesClassFeeTo;
import com.kp.cms.to.fee.FeesDetailsFeeTo;

public class AdditionalFeesAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAddtionalFeesUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdditionalFeesForm additionalFeesForm = (AdditionalFeesForm)form;
		additionalFeesForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_ADDITIONAL_FEES);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward additionalFeesUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdditionalFeesForm additionalFeesForm = (AdditionalFeesForm)form;
		 ActionErrors errors = additionalFeesForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request,additionalFeesForm);
		try{
			if(errors.isEmpty()){
				FormFile myFile = additionalFeesForm.getTheFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<AdditionalFeesTo> additionalFeesList= null;
			   
			    if(fileName.endsWith(".xls")){
			    	byte[] fileData = myFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_ADDITIONAL_FEES);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_ADDITIONAL_FEES);
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
				    additionalFeesList=new ArrayList<AdditionalFeesTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        AdditionalFeesTo feesTo =new AdditionalFeesTo();
				        if(row != null) {
				        	 for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 feesTo.setFeesCode(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 feesTo.setFeesName(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 feesTo.setAccGia(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 feesTo.setAccNgia(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 feesTo.setAmount(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 feesTo.setClasses(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 feesTo.setLoadToHelp(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 
				        				 if(additionalFeesForm.getAcademicYear()!=null && !additionalFeesForm.getAcademicYear().isEmpty()){
				        					 feesTo.setAcademicYear(additionalFeesForm.getAcademicYear());
				        				 }
				        			 }
		                         
		                     } 
				        	 if(feesTo!=null){
				        		 additionalFeesList.add(feesTo);
				        	 	}
				        	 }
				        }
	            	 if(!additionalFeesList.isEmpty()){
	            		 isAdded=AdditionalFeesHandler.getInstance().uploadAdditionalFees(additionalFeesList,additionalFeesForm);
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
			additionalFeesForm.setErrorMessage(msg);
			additionalFeesForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<String> duplicate=additionalFeesForm.getFeeCodeList();
		if(duplicate!=null && !duplicate.isEmpty()){
			StringBuilder ids = new StringBuilder();
			Iterator it=duplicate.iterator();
			while(it.hasNext()){
				ids.append(it.next().toString()).append(", ");
			}
			int len=ids.length()-2;
	        if(ids.toString().endsWith(", ")){
	        	ids.setCharAt(len, ' ');
	        }
			errors.add("error",new ActionError( "knowledgepro.admission.additionalfee.duplicate" ,ids.toString().trim()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_ADDITIONAL_FEES);
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
		public ActionForward initFeesDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			AdditionalFeesForm additionalFeesForm = (AdditionalFeesForm)form;
			additionalFeesForm.resetFields();
			return mapping.findForward(CMSConstants.INIT_FEES_DETAILS);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward uploadFeeDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			AdditionalFeesForm additionalFeesForm = (AdditionalFeesForm)form;
			 ActionErrors errors = additionalFeesForm.validate(mapping, request);
			ActionMessages messages = new ActionMessages();
			setUserId(request,additionalFeesForm);
			try{
				if(errors.isEmpty()){
					FormFile myFile = additionalFeesForm.getTheFile();
				    String contentType = myFile.getContentType();
				    String fileName    = myFile.getFileName();
				   	File file = null;
				    Properties prop = new Properties();
				    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				    prop.load(stream);
				    List<FeesDetailsFeeTo> detailsFeeTos= null;
				   
				    if(fileName.endsWith(".xls")){
				    	byte[] fileData = myFile.getFileData();
				    	String source1=prop.getProperty(CMSConstants.UPLOAD_FEES_DETAILS);
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
						String source=prop.getProperty(CMSConstants.UPLOAD_FEES_DETAILS);
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
					    detailsFeeTos=new ArrayList<FeesDetailsFeeTo>();
					    for(int r = 1; r < rows; r++) {
					        row = sheet.getRow(r);
					        FeesDetailsFeeTo feesTo =new FeesDetailsFeeTo();
					        if(row != null) {
					        	 for(int c = 0; c < cols;c++) {
					        		 cell = row.getCell((byte)c);
					        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
					        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setBillNo(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setFeesCode(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setAddFee22(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setAddFee993(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(additionalFeesForm.getAcademicYear()!=null && !additionalFeesForm.getAcademicYear().isEmpty()){
					        					 feesTo.setAcademicYear(additionalFeesForm.getAcademicYear());
					        				 }
					        			 }
			                         
			                     } 
					        	 if(feesTo!=null){
					        		 detailsFeeTos.add(feesTo);
					        	 	}
					        	 }
					        }
		            	 if(detailsFeeTos!=null && !detailsFeeTos.isEmpty()){
		            		 isAdded=AdditionalFeesHandler.getInstance().uploadDetailsFee(detailsFeeTos,additionalFeesForm);
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
				additionalFeesForm.setErrorMessage(msg);
				additionalFeesForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			List<Integer> duplicate=additionalFeesForm.getBillNoList();
			if(duplicate!=null && !duplicate.isEmpty()){
				StringBuilder ids = new StringBuilder();
				Iterator it=duplicate.iterator();
				while(it.hasNext()){
					ids.append(it.next().toString()).append(", ");
				}
				int len=ids.length()-2;
		        if(ids.toString().endsWith(", ")){
		        	ids.setCharAt(len, ' ');
		        }
				errors.add("error",new ActionError( "knowledgepro.admission.feedetails.duplicate" ,ids.toString().trim()));
				saveErrors(request, errors);
			}
			return mapping.findForward(CMSConstants.INIT_FEES_DETAILS);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initUploadClassFees(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			AdditionalFeesForm additionalFeesForm = (AdditionalFeesForm)form;
			additionalFeesForm.resetFields();
			return mapping.findForward(CMSConstants.INIT_CLASS_FEES);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward uploadClassFees(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			AdditionalFeesForm additionalFeesForm = (AdditionalFeesForm)form;
			 ActionErrors errors = additionalFeesForm.validate(mapping, request);
			ActionMessages messages = new ActionMessages();
			setUserId(request,additionalFeesForm);
			try{
				if(errors.isEmpty()){
					boolean duplicate = AdditionalFeesHandler.getInstance().checkDuplicate(additionalFeesForm);
					if(duplicate){
						errors.add("error", new ActionError("knowledgepro.admin.PromoteSubject.Year.exists"));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_CLASS_FEES);
					}
					FormFile myFile = additionalFeesForm.getTheFile();
				    String contentType = myFile.getContentType();
				    String fileName    = myFile.getFileName();
				   	File file = null;
				    Properties prop = new Properties();
				    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				    prop.load(stream);
				    List<FeesClassFeeTo> list= null;
				   
				    if(fileName.endsWith(".xls")){
				    	byte[] fileData = myFile.getFileData();
				    	String source1=prop.getProperty(CMSConstants.UPLOAD_CLASS_FEES);
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
						String source=prop.getProperty(CMSConstants.UPLOAD_CLASS_FEES);
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
					    list=new ArrayList<FeesClassFeeTo>();
					    for(int r = 1; r < rows; r++) {
					        row = sheet.getRow(r);
					        FeesClassFeeTo feesTo =new FeesClassFeeTo();
					        if(row != null) {
					        	 for(int c = 0; c < cols;c++) {
					        		 cell = row.getCell((byte)c);
					        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
					        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setClasses(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setGia(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setFees(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setMaStringFee(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setOutKarFe(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setSelFnFee(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setApplForNri(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setAplForAdad(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setSlfnSpFee(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setCetFees(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setScstbtFee(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setSscstMaString(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setNriFees(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setNriMFees(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setForgnFees(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setForgnMFees(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					        					 feesTo.setInsSpFees(removeFileExtension(cell.toString().trim()));
					        				 }
					        				 if(additionalFeesForm.getAcademicYear()!=null && !additionalFeesForm.getAcademicYear().isEmpty()){
					        					 feesTo.setAcademicYear(additionalFeesForm.getAcademicYear());
					        				 }
					        			 }
			                     } 
					        	 if(feesTo!=null){
					        		 list.add(feesTo);
					        	 	}
					        	 }
					        }
		            	 if(list!=null && !list.isEmpty()){
		            		 isAdded=AdditionalFeesHandler.getInstance().uploadClassFees(list);
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
				additionalFeesForm.setErrorMessage(msg);
				additionalFeesForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.INIT_CLASS_FEES);
}
}
