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
import java.util.Map;
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

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.UploadBypassInterviewForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.UploadBypassInterviewResultHandler;
import com.kp.cms.handlers.admission.UploadInterviewResultHandler;
import com.kp.cms.to.admission.AdmApplnTO;

@SuppressWarnings("deprecation")
public class UploadBypassInterviewAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(UploadBypassInterviewAction.class);
	
	/**
	 * This method will get the details of programType when link is clicked.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  The forward to which control should be transferred.
	 * @throws Exception
	 */
	public ActionForward initBypassInterviewResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of initInterviewResult method in UploadInterviewResultAction class.");
		UploadBypassInterviewForm bypassInterviewForm = (UploadBypassInterviewForm)form; 
		try {
				setProgramType(bypassInterviewForm);
				setUserId(request,bypassInterviewForm);
				bypassInterviewForm.setApplicationYear(null);
			}catch (BusinessException businessException) {
				String msgKey = super.handleBusinessException(businessException);
				bypassInterviewForm.setErrorMessage(msgKey);
				bypassInterviewForm.setErrorStack(businessException.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				bypassInterviewForm.setErrorMessage(msg);
				bypassInterviewForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}	
		resetFields(bypassInterviewForm);
		log.info("end of initInterviewResult method in UploadInterviewResultAction class.");
		return mapping.findForward(CMSConstants.UPLOAD_BYPASS_INTERVIEW_RESULT);
	}
	
	/**
	 * This method is used to get the programType details and set to form.
	 * @param bypassInterviewForm
	 * @throws Exception
	 */
	
	public void setProgramType(UploadBypassInterviewForm bypassInterviewForm) throws Exception{
		log.info("call of setProgramType method in UploadInterviewResultAction class.");
		if(ProgramTypeHandler.getInstance().getProgramType() != null){
			bypassInterviewForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
		}
		log.info("end of setProgramType method in UploadInterviewResultAction class.");
	}
	
	/**
	 * Method to reset the form fields.
	 * @param interviewResultEntryForm
	 */
	
	public void resetFields(UploadBypassInterviewForm bypassInterviewForm) {
		log.info("call of resetFields method in UploadInterviewResultAction class.");
		bypassInterviewForm.setProgramTypeId(null);
		bypassInterviewForm.setProgramId(null);
		bypassInterviewForm.setCourseId(null);
		log.info("end of resetFields method in UploadInterviewResultAction class.");
	}
	
	/**
	 * This method is used to save the uploaded document.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  The forward to which control should be transferred.
	 * @throws Exception
	 */
	
	public ActionForward uploadBypassInterviewResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("call of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
		UploadBypassInterviewForm bypassInterviewForm = (UploadBypassInterviewForm)form; 
		 ActionErrors errors = bypassInterviewForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
//			setUserId(request,bypassInterviewForm);
			 if (errors.isEmpty()) {
				 FormFile myFile = bypassInterviewForm.getThefile();
				    String contentType = myFile.getContentType();
				    String fileName    = myFile.getFileName();
				   	File file = null;
				    Properties prop = new Properties();
				    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				    prop.load(stream);
				    
				    int applicationYear = Integer.parseInt(bypassInterviewForm.getApplicationYear());
					int courseId = Integer.parseInt(bypassInterviewForm.getCourseId());
				    
				    List<AdmApplnTO> results = null;
				    boolean isAdded = false;
				    //getting adm_appln details based on course and year.
				    Map<Integer,Integer> admMap = UploadInterviewResultHandler.getInstance().getAdmAppDetails(applicationYear,courseId);
				    //if document is excel file.
				    if(fileName.endsWith(".xls")){
				    	
				    	 byte[] fileData    = myFile.getFileData();
				    	 String source1=prop.getProperty(CMSConstants.UPLOAD_BYPASS_INTERVIEW);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_BYPASS_INTERVIEW);
					
					file = new File(filePath+source);
				
				POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
			    HSSFWorkbook workbook = new HSSFWorkbook(system);
			    HSSFSheet sheet = workbook.getSheetAt(0);
			    HSSFRow row;
			    HSSFCell cell;
			    AdmApplnTO admApplnTO = null;
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
			    results = new ArrayList<AdmApplnTO>();
			    for(int r = 1; r < rows; r++) {
			        row = sheet.getRow(r);
			        if(row != null) {
			        	admApplnTO = new AdmApplnTO();
			            for(int c = 0; c < cols;c++) {
			                cell = row.getCell((byte)c);
			                if(cell != null) {
								if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString())){
									if(admMap!=null && admMap.containsKey((int)Float.parseFloat(cell.toString()))){
										admApplnTO.setId(admMap.get((int)Float.parseFloat(cell.toString())));
			                		}else{
			        					break;
			                		}
								} 
								if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString())){
		                   			if("selected".equalsIgnoreCase(cell.toString())){
			                   				admApplnTO.setIsBypassed(true);
			                   				admApplnTO.setIsSelected(true);
				                	}else{
				                			admApplnTO.setIsBypassed(false);
				                			admApplnTO.setIsSelected(false);
				                	}
			                   	} 
			                }
			            }
			            if(admApplnTO.getId() != 0)
			            	results.add(admApplnTO);
			        	}else {
			        		continue;
			        	}
			    	}
			    	if(admApplnTO!= null){
			    		String user = bypassInterviewForm.getUserId();
			    		isAdded = UploadBypassInterviewResultHandler.getInstance().addUploadBypassInterviewData(results, user);
			    	if(isAdded){
			    		//if adding is success.
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_SUCCESS);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    		resetFields(bypassInterviewForm);
			    	}else{
			    		//if adding is failure.
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_FAILURE);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    	}
			    	}
			      	}else if(fileName.endsWith(".csv")){          //if the uploaded file is csv
			     	    AdmApplnTO admApplnTO = null;
			     	  
				     	byte[] fileData    = myFile.getFileData();
					    String source1=prop.getProperty(CMSConstants.UPLOAD_BYPASS_INTERVIEW_CSV);
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
						String source=prop.getProperty(CMSConstants.UPLOAD_BYPASS_INTERVIEW_CSV);
						
						file = new File(filePath+source);
						FileInputStream stream1 = new FileInputStream(file);
						LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(stream1));
						
						results = new ArrayList<AdmApplnTO>();
						while(parser.getLine()!=null){
							admApplnTO = new AdmApplnTO();
							if(parser.getValueByLabel("ApplicationNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("ApplicationNumber")))
				      		if(admMap!=null && admMap.containsKey(Integer.parseInt(parser.getValueByLabel("ApplicationNumber")))){
				      			admApplnTO = new AdmApplnTO();
				      			admApplnTO.setId(admMap.get(Integer.parseInt(parser.getValueByLabel("ApplicationNumber"))).intValue());
				      		}else{
				      			break;
				      		}
							if(parser.getValueByLabel("Status") != null && !StringUtils.isEmpty(parser.getValueByLabel("Status")))
				      		if("selected".equalsIgnoreCase(parser.getValueByLabel("Status"))){
	               				admApplnTO.setIsBypassed(true);
	               				admApplnTO.setIsSelected(true);
	                		}else{
		                			admApplnTO.setIsBypassed(false);
		                			admApplnTO.setIsSelected(false);
	                			}
				      		if(admApplnTO.getId() != 0){
				      			results.add(admApplnTO);
				      		}else{
				      			continue;
				      		}
					}if(admApplnTO!= null){
				    		String user = bypassInterviewForm.getUserId();
				    		isAdded = UploadBypassInterviewResultHandler.getInstance().addUploadBypassInterviewData(results, user);
				    	if(isAdded){
				    		//if adding is success.
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_SUCCESS);
				    		messages.add("messages", message);
				    		saveMessages(request, messages);
				    		resetFields(bypassInterviewForm);
				    	}else{
				    		//if adding is failure.
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_FAILURE);
				    		messages.add("messages", message);
				    		saveMessages(request, messages);
				    	}
				    	}else {
							ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_SUCCESS);
				    		messages.add("messages", message);
				    		saveMessages(request, messages);
				    		resetFields(bypassInterviewForm);
				    	}
				      	}else{          //if the uploaded file is not excel/csv.
				      		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC);
				    		errors.add("error",message);
				    		saveErrors(request, errors);
					    }
			 		}else{
			 			saveErrors(request, errors);
			 		}
		}catch (BusinessException businessException) {
			log.info("Exception uploadBypassInterviewResult");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			bypassInterviewForm.setErrorMessage(msg);
			bypassInterviewForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setProgramType(bypassInterviewForm);
		return mapping.findForward(CMSConstants.UPLOAD_BYPASS_INTERVIEW_RESULT);
	}
	
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
}