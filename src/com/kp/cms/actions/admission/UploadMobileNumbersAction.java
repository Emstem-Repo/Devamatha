package com.kp.cms.actions.admission;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
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
import com.kp.cms.forms.admission.UploadMobileNumbersForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.UploadMobileNumbersHandler;
import com.kp.cms.handlers.admission.UploadSecondLanguageHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.PersonalDataTO;

public class UploadMobileNumbersAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UploadInterviewResultAction.class);
		
		/**
		* This method is called when u click on the link.
		* 
		* @param mapping
		* @param form
		* @param request
		* @param response
		* @return
		* @throws Exception
		*/
		
		public ActionForward initUploadMobileNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UploadMobileNumbersForm uploadMobileNumbersForm = (UploadMobileNumbersForm) form;
		log.info("call of initInterviewResult method in UploadInterviewResultAction class.");
		try {
			setRequiredDatatoForm(uploadMobileNumbersForm, request);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			uploadMobileNumbersForm.setErrorMessage(msgKey);
			uploadMobileNumbersForm.setErrorStack(businessException
					.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			uploadMobileNumbersForm.setErrorMessage(msg);
			uploadMobileNumbersForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("end of initInterviewResult method in UploadInterviewResultAction class.");
		return mapping.findForward(CMSConstants.UPLOAD_MOBILE_NUMBER);
		}
		
		/**
		* Method to set all active Program Types to the form
		* 
		* @param interviewBatchEntryForm
		* @throws Exception
		*/
		private void setRequiredDatatoForm(UploadMobileNumbersForm uploadMobileNumbersForm,HttpServletRequest request) throws Exception {
		if (ProgramTypeHandler.getInstance().getProgramType() != null) {
			uploadMobileNumbersForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
		}
		if (uploadMobileNumbersForm.getProgramId() != null
				&& uploadMobileNumbersForm.getAcademicYear() != null
				&& uploadMobileNumbersForm.getProgramId().length() > 0
				&& uploadMobileNumbersForm.getAcademicYear().length() > 0) {
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
					Integer.valueOf(uploadMobileNumbersForm.getProgramId()));
			request.setAttribute("coursesMap", courseMap);
		}
		if (uploadMobileNumbersForm.getProgramTypeId() != null
				&& uploadMobileNumbersForm.getProgramTypeId().length() > 0) {
			Map collegeMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(uploadMobileNumbersForm.getProgramTypeId()));
			request.setAttribute("programMap", collegeMap);
		}
		}
		
		/**
		* This method is used to save the upload document.
		* 
		* @param mapping
		* @param form
		* @param request
		* @param response
		* @return
		* @throws Exception
		*/
		
		public ActionForward uploadMobileNumbersForStudent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			UploadMobileNumbersForm uploadMobileNumbersForm = (UploadMobileNumbersForm) form;
			log.info("call of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
			 ActionErrors errors = uploadMobileNumbersForm.validate(mapping, request);
			ActionMessages messages = new ActionMessages();
			try {
				setUserId(request, uploadMobileNumbersForm);
				if(uploadMobileNumbersForm.getThefile()!=null){
					if(uploadMobileNumbersForm.getThefile().getFileName()!=null && !StringUtils.isEmpty(uploadMobileNumbersForm.getThefile().getFileName().trim())){
						String extn="";
						int index = uploadMobileNumbersForm.getThefile().getFileName().lastIndexOf(".");
						if(index != -1){
							extn = uploadMobileNumbersForm.getThefile().getFileName().substring(index, uploadMobileNumbersForm.getThefile().getFileName().length());
						}
						if(!extn.equalsIgnoreCase(".CSV")){
							if(errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR).hasNext()){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR));
								saveErrors(request, errors);
							}
						}
					}else{
						if(errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED).hasNext()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_OMR_REQUIRED));
							saveErrors(request, errors);
						}
					}
				}
				if (errors.isEmpty()) {
					int applicationYear = Integer.parseInt(uploadMobileNumbersForm
							.getAcademicYear());
					int courseId = Integer.parseInt(uploadMobileNumbersForm
							.getCourseId());
					FormFile myFile = uploadMobileNumbersForm.getThefile();
					String contentType = myFile.getContentType();
			
					String fileName = myFile.getFileName();
					File file = null;
					Properties prop = new Properties();
					InputStream stream = Thread.currentThread()
							.getContextClassLoader().getResourceAsStream(
									CMSConstants.APPLICATION_PROPERTIES);
					prop.load(stream);
					// getting personal Data details.
					Map<String, Integer> pdMap = UploadSecondLanguageHandler.getInstance().getAppDetails(applicationYear, courseId);
					
					List<PersonalDataTO> results = null;
			
					// if the uploaded document is excel file.
					 if (fileName.endsWith(".csv")) { // if the uploaded
															// document is csv file.
						PersonalDataTO personalDataTO = null;
			
						byte[] fileData = myFile.getFileData();
						String source1 = prop.getProperty(CMSConstants.UPLOAD_SECONDLANGUAGE_CSV);
						String filePath=request.getRealPath("");
				    	filePath = filePath + "//TempFiles//";
						File file1 = new File(filePath+source1);
			
						InputStream inputStream = new ByteArrayStreamInfo(
								contentType, fileData).getInputStream();
						OutputStream out = new FileOutputStream(file1);
						byte buffer[] = new byte[1024];
						int len;
						while ((len = inputStream.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
						out.close();
						inputStream.close();
						String source = prop
								.getProperty(CMSConstants.UPLOAD_SECONDLANGUAGE_CSV);
			
						file = new File(filePath+source);
						FileInputStream stream1 = new FileInputStream(file);
						LabeledCSVParser parser = new LabeledCSVParser(
								new CSVParser(stream1));
			
						results = new ArrayList<PersonalDataTO>();
			
						while (parser.getLine() != null) {
							if (parser.getValueByLabel("RegisterNo") != null && !StringUtils.isEmpty(parser.getValueByLabel("RegisterNo")))
								if (pdMap != null && pdMap.containsKey(parser.getValueByLabel("RegisterNo").toUpperCase())) {
									personalDataTO = new PersonalDataTO();
									personalDataTO.setId(pdMap.get(parser.getValueByLabel("RegisterNo").toUpperCase()));
								} else {
									personalDataTO=null;
								}
							if (parser.getValueByLabel("MobileCountryCode") != null && !StringUtils.isEmpty(parser.getValueByLabel("MobileCountryCode"))) {
									personalDataTO.setMobileNo1(parser.getValueByLabel("MobileCountryCode").trim());
							}
							if (parser.getValueByLabel("Mobile") != null && !StringUtils.isEmpty(parser.getValueByLabel("Mobile"))) {
									personalDataTO.setMobileNo2(parser.getValueByLabel("Mobile").trim());
							}
							if (parser.getValueByLabel("ParentMobileCountryCode") != null && !StringUtils.isEmpty(parser.getValueByLabel("ParentMobileCountryCode"))) {
								personalDataTO.setParentMob1(parser.getValueByLabel("ParentMobileCountryCode").trim());
							}
							if (parser.getValueByLabel("ParentMobile") != null && !StringUtils.isEmpty(parser.getValueByLabel("ParentMobile"))) {
									personalDataTO.setParentMob2(parser.getValueByLabel("ParentMobile").trim());
							}
							if (parser.getValueByLabel("PhoneCountryCode") != null && !StringUtils.isEmpty(parser.getValueByLabel("PhoneCountryCode"))) {
								personalDataTO.setPhNo1(parser.getValueByLabel("PhoneCountryCode").trim());
							}
							if (parser.getValueByLabel("PhoneAreaCode") != null && !StringUtils.isEmpty(parser.getValueByLabel("PhoneAreaCode"))) {
									personalDataTO.setPhNo2(parser.getValueByLabel("PhoneAreaCode").trim());
							}
							if (parser.getValueByLabel("Phone") != null && !StringUtils.isEmpty(parser.getValueByLabel("Phone"))) {
								personalDataTO.setPhNo3(parser.getValueByLabel("Phone").trim());
							}
							if (parser.getValueByLabel("ParentPhoneCountryCode") != null && !StringUtils.isEmpty(parser.getValueByLabel("ParentPhoneCountryCode"))) {
								personalDataTO.setParentPh1(parser.getValueByLabel("ParentPhoneCountryCode").trim());
							}
							if (parser.getValueByLabel("ParentPhoneAreaCode") != null && !StringUtils.isEmpty(parser.getValueByLabel("ParentPhoneAreaCode"))) {
									personalDataTO.setParentPh2(parser.getValueByLabel("ParentPhoneAreaCode").trim());
							}
							if (parser.getValueByLabel("ParentPhone") != null && !StringUtils.isEmpty(parser.getValueByLabel("ParentPhone"))) {
								personalDataTO.setParentPh3(parser.getValueByLabel("ParentPhone").trim());
							}
							if (personalDataTO != null && personalDataTO.getId() != 0) {
								results.add(personalDataTO);
							}
						}
						boolean isAdded = false;
						if (results != null) {
							String user = uploadMobileNumbersForm.getUserId();
							isAdded = UploadMobileNumbersHandler.getInstance()
									.addUploadedData(results, user);
							if (isAdded) {
								// if adding is success.
								ActionMessage message = new ActionMessage(
										CMSConstants.UPLOAD_MOBILENUMBER_SUCCESS);
								uploadMobileNumbersForm.resetFields();
								messages.add("messages", message);
								saveMessages(request, messages);
							} else {
								// if adding is failure.
								ActionMessage message = new ActionMessage(
										CMSConstants.UPLOAD_MOBILENUMBER_FAILURE);
								errors.add(CMSConstants.ERROR, message);
								addErrors(request, errors);
							}
						}else{
							// if adding is failure.
							ActionMessage message = new ActionMessage(
									CMSConstants.UPLOAD_MOBILENUMBER_FAILURE);
							errors.add(CMSConstants.ERROR, message);
							addErrors(request, errors);
						}
					} else { // if the uploaded document is not excel/csv file.
						ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_DOCUMENT);
						errors.add(CMSConstants.ERROR, message);
						saveErrors(request, errors);
					}
				} else
					saveErrors(request, errors);
			} catch (BusinessException businessException) {
				log.info("Exception uploadInterviewResultEntry");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				uploadMobileNumbersForm.setErrorMessage(msg);
				uploadMobileNumbersForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			// setting the programType details to form
			setRequiredDatatoForm(uploadMobileNumbersForm, request);
			
			log.info("end of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
			return mapping.findForward(CMSConstants.UPLOAD_MOBILE_NUMBER);
		}
		
		/**
		 * @author balaji
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

}
