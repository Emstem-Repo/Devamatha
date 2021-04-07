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
import javax.servlet.http.HttpSession;

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
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.UploadBypassInterviewForm;
import com.kp.cms.handlers.admission.UploadFinalMeritListHandler;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.utilities.CommonUtil;

public class UploadFinalMeritListAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(UploadFinalMeritListAction.class);
	
	/**
	 * This method will get the details of programType when link is clicked.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  The forward to which control should be transferred.
	 * @throws Exception
	 */
	public ActionForward initFinalMeritListUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of initInterviewResult method in UploadInterviewResultAction class.");
		UploadBypassInterviewForm bypassInterviewForm = (UploadBypassInterviewForm)form; 
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","upload_final_meritlist");
		
		try {
				setUserId(request,bypassInterviewForm);
			}catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				bypassInterviewForm.setErrorMessage(msg);
				bypassInterviewForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}	
		resetFields(bypassInterviewForm);
		log.info("end of initFinalMeritListUpload method in UploadFinalMeritListAction class.");
		return mapping.findForward(CMSConstants.INIT_FINAL_MERIT_LIST_UPLOAD);
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
		bypassInterviewForm.setApplicationYear(null);
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
	
	public ActionForward uploadFinalMeritList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("call of uploadFinalMeritList method in UploadFinalMeritListAction class.");
		UploadBypassInterviewForm bypassInterviewForm = (UploadBypassInterviewForm)form;
		
		 ActionErrors errors = bypassInterviewForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			
			setUserId(request,bypassInterviewForm);
			 if (errors.isEmpty()) {
				 FormFile myFile = bypassInterviewForm.getThefile();
				    String contentType = myFile.getContentType();
				    String fileName    = myFile.getFileName();
				   	File file = null;
				    Properties prop = new Properties();
				    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				    prop.load(stream);
				    
				    int applicationYear = Integer.parseInt(bypassInterviewForm.getApplicationYear());
				    
				    List<AdmAppln> results = null;
				    List<AdmapplnAdditionalInfo> admAdditionalList=null;
				    /*A list to display the application numbers which have not been uploaded*/
				    StringBuilder notUploadedNumbersList = new StringBuilder();
				    boolean isAdded = false;
				    Map<Integer,Integer> applicationMap = UploadInterviewSelectionHandler.getInstance().getAdmAppDetailsForNotSelected(applicationYear, bypassInterviewForm);
				    Map<String, Integer> courseMap = UploadFinalMeritListHandler.getInstance().getCourseMap();
				    //if document is excel file.
				    if(fileName.endsWith(".xls")){
				    	
				    	 byte[] fileData    = myFile.getFileData();
				    	 String source1=prop.getProperty(CMSConstants.UPLOAD_FINAL_MERIT_LIST);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_FINAL_MERIT_LIST);
					String appNotselect=prop.getProperty("knowledgepro.Admission.upload.meritlist.notselected");
					String appWaiting=prop.getProperty("knowledgepro.Admission.upload.meritlist.waitlisted");
					
					file = new File(filePath+source);
				
				POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
			    HSSFWorkbook workbook = new HSSFWorkbook(system);
			    HSSFSheet sheet = workbook.getSheetAt(0);
			    HSSFRow row;
			    HSSFCell cell;
			    
				AdmAppln admAppln = null;
				AdmapplnAdditionalInfo admAddln=null;
				
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
			    int i=0;
			    int j=0;
			    results = new ArrayList<AdmAppln>();
			    String statusList="";
			    String remarkList="";
			    admAdditionalList= new ArrayList<AdmapplnAdditionalInfo>();
			    for(int r = 1; r < rows; r++) {
			        row = sheet.getRow(r);
			        if(row != null) {
			        	boolean selected = false;
			        	boolean notSelected = false;
			        	boolean waiting = false;
			        	boolean status = true;
			        	boolean remark = true;
			        	String applnNo="";
			            for(int c = 0; c < cols;c++) {
			                cell = row.getCell((byte)c);
			                if(cell != null && !cell.toString().isEmpty()) {
								if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
									if(applicationMap!=null && applicationMap.containsKey((int)Float.parseFloat(cell.toString()))){
										admAppln = new AdmAppln();
										admAppln.setId(applicationMap.get((int)Float.parseFloat(cell.toString())));
										applnNo=String.valueOf((int)Float.parseFloat(cell.toString()));
			                		}
									else{
										if(notUploadedNumbersList.length()>0){
											notUploadedNumbersList.append(",");
											notUploadedNumbersList.append(String.valueOf((int)Float.parseFloat(cell.toString())));
										}else notUploadedNumbersList.append(String.valueOf((int)Float.parseFloat(cell.toString())));
			        					break;
			                		}
								}
								
								if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString())){
		                   			if("selected".equalsIgnoreCase(cell.toString().trim())){
		                   				admAppln.setAdmStatus(null);
		                   				admAppln.setIsSelected(true);
		                   				admAppln.setIsWaiting(false);
				                		admAppln.setNotSelected(false);
		                   				selected = true;
		                   				notSelected=false;
		                   				waiting=false;
		                   				status = false;
				                	}else if(appNotselect.equalsIgnoreCase(cell.toString().trim())){
				                		admAppln.setIsSelected(false);
				                		admAppln.setIsWaiting(false);
				                		admAppln.setNotSelected(true);
				                		selected = false;
		                   				notSelected=true;
		                   				waiting=false;
		                   				status = false;
				                	}else if(appWaiting.equalsIgnoreCase(cell.toString().trim())){
					                		admAppln.setIsSelected(false);
					                		admAppln.setIsWaiting(true);
					                		admAppln.setNotSelected(false);
					                		selected = false;
			                   				notSelected=false;
			                   				waiting=true;
			                   				status = false;
					                  }
			                     	}
							if(applnNo!=null && !applnNo.isEmpty()){
								if(cell.getCellNum() == 1 && status){
									statusList=statusList+","+applnNo;
									 i++;
										if(i==19){
											statusList=statusList+"<br>";
											i=0;
										}
								 }
			                  }
								if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString()) && waiting){
				                		admAppln.setAdmStatus(cell.toString());
				                		remark = false;
				                }
								if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString()) && notSelected){
										admAppln.setAdmStatus(cell.toString());
										remark = false;
										}
								if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString()) && selected){
									Course course = new Course();
									course.setId(courseMap.get(cell.toString().trim()));
									admAppln.setCourseBySelectedCourseId(course);
			                   	} 
								if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString())){
									 admAddln=new AdmapplnAdditionalInfo();
									admAddln.setAdmAppln(admAppln);
									if(cell.getCellType()==0 && cell.getDateCellValue()!=null){
										String date=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(cell.getDateCellValue()), "dd-MMM-yyyy",
										"dd/MM/yyyy");
									admAddln.setAdmissionScheduledDate(CommonUtil.ConvertStringToSQLDate(date));
									}
									else admAddln.setAdmissionScheduledDate(CommonUtil.ConvertStringToSQLDate(cell.toString()));
			                   	} 
								if(admAddln!=null && cell.getCellNum() ==5 && !StringUtils.isEmpty(cell.toString())){
									if(cell.getCellType()==0 && cell.getDateCellValue()!=null )
										admAddln.setAdmissionScheduledTime(CommonUtil.getTimeByDate(cell.getDateCellValue()));
									else admAddln.setAdmissionScheduledTime(cell.toString());
			                   	} 
			                }else if(applnNo!=null && !applnNo.isEmpty()){
			                    if(!selected && remark){
			                    if(!remarkList.contains(applnNo)){
			                      remarkList=remarkList+","+ applnNo;
			                      j++;
									if(j==19){
										 remarkList=remarkList+"<br>";
										j=0;
									}
							   }
			                }
			                }
			            }
			            if(admAppln!=null)
			            	results.add(admAppln);
			            if(admAddln!=null)
			            	admAdditionalList.add(admAddln);
			        	}else {
			        		continue;
			        	}
			    	}if(statusList!=null && !statusList.isEmpty()){
			    		statusList=statusList.substring(1);
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_FINAL_MERIT_LIST_STATUS,statusList);
			    		errors.add("error",message);
			    		saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_FINAL_MERIT_LIST_UPLOAD);	
			    	}if(remarkList!=null && !remarkList.isEmpty()){
			    		remarkList=remarkList.substring(1);
			    		 ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_FINAL_MERIT_LIST_REMARKS,remarkList);
			    		 errors.add("error",message);
			    		 saveErrors(request, errors);
						 return mapping.findForward(CMSConstants.INIT_FINAL_MERIT_LIST_UPLOAD);		    		
			    	}
		    		String user = bypassInterviewForm.getUserId();
		    		if(results!= null && results.size() > 0){
		    			isAdded = UploadFinalMeritListHandler.getInstance().addUploadFinalMeritList(results, user,admAdditionalList);
		    		}
			    	if(isAdded){
			    		//if adding is success.
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_FINAL_MERIT_LIST_UPLOAD_SUCCESS);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    		resetFields(bypassInterviewForm);
			    	}else{
			    		//if adding is failure.
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_FINAL_MERIT_LIST_UPLOAD_FAILURE);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    	}
			    	if(notUploadedNumbersList.length()>0){
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_FINAL_MERIT_LIST_NOT_UPLOADED_NUMS,notUploadedNumbersList.toString());
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
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
		return mapping.findForward(CMSConstants.INIT_FINAL_MERIT_LIST_UPLOAD);
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
