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
import com.kp.cms.forms.admin.AdmBioDataCJCForm;
import com.kp.cms.forms.admin.AdmMeritListForm;
import com.kp.cms.forms.admission.AdmissionTcDetailsForm;
import com.kp.cms.handlers.admin.AdmBioDataCJCHandler;
import com.kp.cms.handlers.admin.AdmMeritHandler;
import com.kp.cms.handlers.admission.AdmissionTcDetailsHandler;
import com.kp.cms.handlers.admission.PromoteMarksUploadHandler;
import com.kp.cms.helpers.admin.AdmBioDataCJCHelper;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.to.admin.AdmMeritTO;
import com.kp.cms.to.admission.AdmissionTcDetailsTo;

public class AdmissionTcDetailsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AdmissionTcDetailsAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdmTcDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdmissionTcDetailsForm admTcDetailsForm = (AdmissionTcDetailsForm) form;
		admTcDetailsForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_ADM_TC_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadAdmTcDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdmissionTcDetailsForm admTcDetailsForm = (AdmissionTcDetailsForm) form;
		log.info("call of uploadAdmTcDetails method in AdmissionTcDetailsAction class.");
		 ActionErrors errors = admTcDetailsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request,admTcDetailsForm);
		
		try{
			if(errors.isEmpty()){
				FormFile myFile = admTcDetailsForm.getTheFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<AdmissionTcDetailsTo> admissionTcDetailsTo= null;
			   
			    if(fileName.endsWith(".xls")){
			    	byte[] fileData = myFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_ADM_TCDETAILS);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_ADM_TCDETAILS);
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
				    admissionTcDetailsTo=new ArrayList<AdmissionTcDetailsTo>();
				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        AdmissionTcDetailsTo tcDetailsTo =new AdmissionTcDetailsTo();
				        if(row != null) {
				        	 for(int c = 0; c < cols;c++) {
				        		 cell = row.getCell((byte)c);
				        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
				        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 tcDetailsTo.setRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 String dateAdm= cell.toString().trim();
				        					 tcDetailsTo.setDateAdm(AdmBioDataCJCHelper.ConvertStringToSQLDate(dateAdm));
				        				 }
				        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 tcDetailsTo.setOrderNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 String orderDate = cell.toString().trim();
				        					 tcDetailsTo.setOrderDate(AdmBioDataCJCHelper.ConvertStringToSQLDate(orderDate));
				        				 }
				        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setName(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setSex(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setFatherName(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
				        					tcDetailsTo.setMotherName(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setNationality(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setReligion(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setScStBcBt(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 String dob = cell.toString().trim();
				        					 tcDetailsTo.setDateOfBirth(AdmBioDataCJCHelper.ConvertStringToSQLDate(dob));
				        				 }
				        				 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setClasses(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setYear(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setSection(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setSecndLang(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setSubject1(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setSubject2(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setSubject3(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 19 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setSubject4(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 20 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setPublicExam(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 21 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 tcDetailsTo.setExmRegNo(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 22 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setExmMonyr(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 23 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setPassed(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 24 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setPassedLang(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 25 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setPassedSubj(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 26 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setGovtSchlp(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 27 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setFreeShip(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 28 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setLastAttnDt(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 29 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setFeesPaid(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 30 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setDegClass(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 31 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 tcDetailsTo.setFirstYearMrk(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 32 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 tcDetailsTo.setSecondYearMrk(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 33 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 tcDetailsTo.setThirdYearMrk(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(cell.getCellNum() == 34 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 tcDetailsTo.setOutOfMrk(removeFileExtension(cell.toString().trim()));
				        					 
				        				 }
				        				 if(cell.getCellNum() == 35 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setLeftStudnt(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 36 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 String dateLeave = cell.toString().trim();
				        					 tcDetailsTo.setDateLeave(AdmBioDataCJCHelper.ConvertStringToSQLDate(dateLeave));
				        				 }
				        				 if(cell.getCellNum() == 37 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setReasonLeave(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 38 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setCaste(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 39 && !StringUtils.isEmpty(cell.toString().trim())){
				        					 tcDetailsTo.setTcGiven(cell.toString().trim());
				        				 }
				        				 if(cell.getCellNum() == 40 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
				        					 tcDetailsTo.setFourthYearMrk(removeFileExtension(cell.toString().trim()));
				        				 }
				        				 if(admTcDetailsForm.getAcademicYear()!=null && !admTcDetailsForm.getAcademicYear().isEmpty()){
				        					 tcDetailsTo.setAcademicYear(admTcDetailsForm.getAcademicYear());
				        				 }
				        			 }
		                         
		                     } 
				        	 if(tcDetailsTo!=null){
				        		 admissionTcDetailsTo.add(tcDetailsTo);
				        	 	}
				        	 }
				        }
	            	 if(!admissionTcDetailsTo.isEmpty()){
	            		 isAdded=AdmissionTcDetailsHandler.getInstance().uploadAdmTcDetails(admissionTcDetailsTo);
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
			admTcDetailsForm.setErrorMessage(msg);
			admTcDetailsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ADM_TC_DETAILS);
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
	public ActionForward initTcDetailsReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		AdmissionTcDetailsForm tcDetailsForm = (AdmissionTcDetailsForm)form;
		tcDetailsForm.setCourseName(null);
		HttpSession session = request.getSession(false);
		session.removeAttribute("TcDetailsTo");
		session.removeAttribute(CMSConstants.EXCEL_BYTES);
		setRequiredDataTOForm(tcDetailsForm);
		return mapping.findForward(CMSConstants.INIT_TCDETAILS_REPORT);
		}
	/**
	 * @param admMeritListForm
	 * @throws Exception
	 */
	private void setRequiredDataTOForm(AdmissionTcDetailsForm tcDetailsForm) throws Exception {
		String mode = "tcDetails";
		Map<String,String> courses=PromoteMarksUploadHandler.getInstance().getCourses(mode);
		tcDetailsForm.setCourses(courses);
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchAdmTcDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdmissionTcDetailsForm tcDetailsForm = (AdmissionTcDetailsForm)form;
		ActionMessages errors = new ActionErrors();
		tcDetailsForm.setMode(null);
		tcDetailsForm.setDownloadExcel(null);
		if (tcDetailsForm.getAcademicYear()!=null && !tcDetailsForm.getAcademicYear().isEmpty()) {	
			try {
			HttpSession session = request.getSession();
			 List<AdmissionTcDetailsTo> tcDetailsTo = AdmissionTcDetailsHandler.getInstance().getAdmTcDetailsSearch(tcDetailsForm,request);
			if(tcDetailsTo !=null){	
				tcDetailsForm.setAdmTcDetails(tcDetailsTo);
				session.setAttribute("TcDetailsTo",tcDetailsTo);
				}
			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				tcDetailsForm.setErrorMessage(msg);
				tcDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			errors.add("error", new ActionError("knowledgepro.exam.examDefinition.academicYear.required"));
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_TCDETAILS_REPORT);
		}
		return mapping.findForward(CMSConstants.TCDETAILS_SEARCH);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered initexportToExcel..");
		AdmissionTcDetailsForm tcDetailsForm = (AdmissionTcDetailsForm)form;
		tcDetailsForm.setSelectedColumnsArray(null);
		tcDetailsForm.setUnselectedColumnsArray(null);
		log.info("exit initexportToExcel..");
		return mapping.findForward(CMSConstants.INIT_EXCEL_ADM_TCDETAILS_REPORT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdmissionTcDetailsForm tcDetailsForm = (AdmissionTcDetailsForm)form;
		log.info("entered exportToExcel..");
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		tcDetailsForm.setMode(null);
		tcDetailsForm.setDownloadExcel(null);
			try {
				if(tcDetailsForm.getSelectedColumnsArray()!=null && !tcDetailsForm.getSelectedColumnsArray().toString().isEmpty()){
					setUserId(request, tcDetailsForm);
					boolean isUpdated =	AdmissionTcDetailsHandler.getInstance().exportTOExcel(tcDetailsForm,request);
			 		if(isUpdated){
			 			tcDetailsForm.setMode("excel");
			 			tcDetailsForm.setDownloadExcel("download");
						ActionMessage message = new ActionMessage("knowledgepro.reports.columnsUpdate");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
				}else {
					errors.add("error", new ActionError("knowledgepro.select.atleast.onecolumn"));
					addErrors(request, errors);			
					return mapping.findForward(CMSConstants.INIT_EXCEL_ADM_TCDETAILS_REPORT);
				}
		}catch (ApplicationException ae) {
			log.error("error occured in exportToExcel in AdmissionTcDetailsAction",ae);
			String msg = super.handleApplicationException(ae);
			tcDetailsForm.setErrorMessage(msg);
			tcDetailsForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error occured in exportToExcel in AdmissionTcDetailsAction",e);
			String msg = super.handleApplicationException(e);
			tcDetailsForm.setErrorMessage(msg);
			tcDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit exportToExcel..");
		return mapping.findForward(CMSConstants.TCDETAILS_SEARCH);
	}
}
