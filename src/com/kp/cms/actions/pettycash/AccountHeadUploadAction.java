package com.kp.cms.actions.pettycash;

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
import com.kp.cms.forms.pettycash.AccountHeadsForm;
import com.kp.cms.handlers.admission.PromoteSubjectsUploadHandler;
import com.kp.cms.handlers.pettycash.AccountHeadsHandler;
import com.kp.cms.helpers.admin.AdmBioDataCJCHelper;
import com.kp.cms.to.pettycash.AccountHeadsTo;
import com.kp.cms.to.pettycash.PettyCashCollectionDetailsTo;
import com.kp.cms.to.pettycash.PettyCashCollectionTo;

/**
 * @author christ
 *
 */
public class AccountHeadUploadAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAccountHeadsUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AccountHeadsForm accountHeadsForm = (AccountHeadsForm) form;
		accountHeadsForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_ACCOUNT_HEADS_UPLOAD);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadAccountHeads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AccountHeadsForm accountHeadsForm = (AccountHeadsForm) form;
		 ActionErrors errors = accountHeadsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request,accountHeadsForm);
		try{
			if(errors.isEmpty()){
				boolean duplicate = AccountHeadsHandler.getInstance().checkDuplicate(accountHeadsForm);
				if(duplicate){
					errors.add("error", new ActionError("knowledgepro.admin.PromoteSubject.Year.exists"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_ACCOUNT_HEADS_UPLOAD);
				}
				FormFile myFile = accountHeadsForm.getTheFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<AccountHeadsTo> accountHeadsList= null;
			   
			    if(fileName.endsWith(".xls")){
			    	byte[] fileData = myFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_ACCOUNT_HEADS);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_ACCOUNT_HEADS);
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
				    accountHeadsList=new ArrayList<AccountHeadsTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        AccountHeadsTo accountHeadsTo =new AccountHeadsTo();
				        if(row != null) {
				        	 for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 accountHeadsTo.setAccCode(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 accountHeadsTo.setAccName(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 accountHeadsTo.setBankAccNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 accountHeadsTo.setAmount(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 accountHeadsTo.setFixedAmt(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 accountHeadsTo.setUserCode(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 accountHeadsTo.setAtStation(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 accountHeadsTo.setAtTime(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 String atDate= cell.toString().trim();
				        					 accountHeadsTo.setAtDate(AdmBioDataCJCHelper.ConvertStringToSQLDate(atDate));
				        				 }
				        				 
				        				 if(accountHeadsForm.getAcademicYear()!=null && !accountHeadsForm.getAcademicYear().isEmpty()){
				        					 accountHeadsTo.setAcademicYear(accountHeadsForm.getAcademicYear());
				        				 }
				        			 }
		                         
		                     } 
				        		 accountHeadsList.add(accountHeadsTo);
				        	 }
				        }
	            	 if(!accountHeadsList.isEmpty()){
	            		 isAdded=AccountHeadsHandler.getInstance().uploadAccountHeads(accountHeadsList);
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
			accountHeadsForm.setErrorMessage(msg);
			accountHeadsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ACCOUNT_HEADS_UPLOAD);
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
	public ActionForward initUploadPettyCashCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AccountHeadsForm accountHeadsForm = (AccountHeadsForm) form;
		accountHeadsForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_PETTY_CASH_COLLECTION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadPettyCashCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AccountHeadsForm accountHeadsForm = (AccountHeadsForm) form;
		 ActionErrors errors = accountHeadsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request,accountHeadsForm);
		try{
			if(errors.isEmpty()){
				FormFile myFile = accountHeadsForm.getTheFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<PettyCashCollectionTo>  pettyCashCollectionList= null;
			   
			    if(fileName.endsWith(".xls")){
			    	byte[] fileData = myFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_PETTYCASH_COLLECTION);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_PETTYCASH_COLLECTION);
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
				    pettyCashCollectionList=new ArrayList<PettyCashCollectionTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        PettyCashCollectionTo pettyCashCollectionTo =new PettyCashCollectionTo();
				        if(row != null) {
				        	 for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 try{
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 pettyCashCollectionTo.setReceiptNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 pettyCashCollectionTo.setAplRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 pettyCashCollectionTo.setName(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 String date=cell.toString().trim();
				        					 pettyCashCollectionTo.setDate(AdmBioDataCJCHelper.ConvertStringToSQLDate(date));
				        				 }
				        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 pettyCashCollectionTo.setTime(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 pettyCashCollectionTo.setUserCode(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 pettyCashCollectionTo.setAtStation(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 String str=cell.toString().trim();
				        					 pettyCashCollectionTo.setAtDate(AdmBioDataCJCHelper.ConvertStringToSQLDate(str));
				        				 }
				        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 pettyCashCollectionTo.setAtTime(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(accountHeadsForm.getAcademicYear()!=null && !accountHeadsForm.getAcademicYear().isEmpty()){
				        					 pettyCashCollectionTo.setAcademicYear(accountHeadsForm.getAcademicYear());
				        				 }
				        		 }
				        			 }catch(Exception e){
					        			 e.printStackTrace();
					        		 }
		                     } 
				        	 if(pettyCashCollectionTo!=null){
				        		 pettyCashCollectionList.add(pettyCashCollectionTo);
				        	 	}
				        	 }
				        }
	            	 if(pettyCashCollectionList!=null && !pettyCashCollectionList.isEmpty()){
	            		 isAdded=AccountHeadsHandler.getInstance().uploadPettyCashCollection(pettyCashCollectionList,accountHeadsForm);
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
			accountHeadsForm.setErrorMessage(msg);
			accountHeadsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<String> duplicate=accountHeadsForm.getRegNos();
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
			errors.add("error",new ActionError( "knowledgepro.pettycash.collection.duplicate" ,ids.toString().trim()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_PETTY_CASH_COLLECTION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPettyCashCollectionsDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AccountHeadsForm accountHeadsForm = (AccountHeadsForm) form;
		accountHeadsForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_PETTY_CASH_COLLECTION_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadPettyCashCollectionDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AccountHeadsForm accountHeadsForm = (AccountHeadsForm) form;
		 ActionErrors errors = accountHeadsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request,accountHeadsForm);
		try{
			if(errors.isEmpty()){
				FormFile myFile = accountHeadsForm.getTheFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<PettyCashCollectionDetailsTo>  pettyCashCollectionDetailsList= null;
			   
			    if(fileName.endsWith(".xls")){
			    	byte[] fileData = myFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_PETTYCASH_COLLECTION_DETAILS);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_PETTYCASH_COLLECTION_DETAILS);
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
				    pettyCashCollectionDetailsList=new ArrayList<PettyCashCollectionDetailsTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        PettyCashCollectionDetailsTo cashCollectionDetailsTo =new PettyCashCollectionDetailsTo();
				        if(row != null) {
				        	 for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 cashCollectionDetailsTo.setReceiptNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 cashCollectionDetailsTo.setAccCode(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 cashCollectionDetailsTo.setAmount(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 cashCollectionDetailsTo.setAplRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 cashCollectionDetailsTo.setPuDgPg(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 String str = removeFileExtension(cell.toString().trim());
				        					 cashCollectionDetailsTo.setDate(AdmBioDataCJCHelper.ConvertStringToSQLDate(str));
				        				 }
				        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 cashCollectionDetailsTo.setUserCode(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 cashCollectionDetailsTo.setAtStation(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 String str = removeFileExtension(cell.toString().trim());
				        					 cashCollectionDetailsTo.setAtDate(AdmBioDataCJCHelper.ConvertStringToSQLDate(str));
				        				 }
				        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 cashCollectionDetailsTo.setAtTime(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(accountHeadsForm.getAcademicYear()!=null && !accountHeadsForm.getAcademicYear().isEmpty()){
				        					 cashCollectionDetailsTo.setAcademicYear(accountHeadsForm.getAcademicYear());
				        				 }
				        			 }
		                         
		                     } 
				        	 if(cashCollectionDetailsTo!=null){
				        		 pettyCashCollectionDetailsList.add(cashCollectionDetailsTo);
				        	 	}
				        	 }
				        }
	            	 if(pettyCashCollectionDetailsList!=null && !pettyCashCollectionDetailsList.isEmpty()){
	            		 isAdded=AccountHeadsHandler.getInstance().uploadPettyCashCollectionDetails(pettyCashCollectionDetailsList,accountHeadsForm);
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
			accountHeadsForm.setErrorMessage(msg);
			accountHeadsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<String> duplicate=accountHeadsForm.getReceiptNos();
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
			errors.add("error",new ActionError( "knowledgepro.pettycash.uploadCollectionDetails.duplicate" ,ids.toString().trim()));
			//addErrors(request, errors);
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_PETTY_CASH_COLLECTION_DETAILS);
	}
}
