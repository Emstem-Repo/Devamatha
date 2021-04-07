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
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.UploadUniversityEmailForm;
import com.kp.cms.handlers.admission.SmartCardNumberUploadHandler;
import com.kp.cms.handlers.admission.UploadUniversityEmailHandler;

public class UploadUniversityEmailAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UploadUniversityEmailAction.class);
	/**
	 * Method to redirect to UploadUniversityEmailInit.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadUniversityEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initUploadUniversityEmail - UploadUniversityEmailAction Batch input");
		UploadUniversityEmailForm uploadForm = (UploadUniversityEmailForm) form;
		uploadForm.resetFields();
		log.info("Exit initUploadUniversityEmail - UploadUniversityEmailAction input");
		
		return mapping.findForward(CMSConstants.UPLOAD_UNIVERSITY_EMAIL_INIT);
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
	
	public ActionForward uploadUniversityEmail(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		UploadUniversityEmailForm uploadForm = (UploadUniversityEmailForm) form;
		log.info("call of uploadUniversityEmail method in UploadUniversityEmailAction class.");
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, uploadForm);
			if(uploadForm.getThefile()!=null){
				if(uploadForm.getThefile().getFileName()!=null && !StringUtils.isEmpty(uploadForm.getThefile().getFileName().trim())){
					String extn="";
					int index = uploadForm.getThefile().getFileName().lastIndexOf(".");
					if(index != -1){
						extn = uploadForm.getThefile().getFileName().substring(index, uploadForm.getThefile().getFileName().length());
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
			if (errors != null && errors.isEmpty()) {
				FormFile myFile = uploadForm.getThefile();
				String contentType = myFile.getContentType();
		
				String fileName = myFile.getFileName();
				File file = null;
				Properties prop = new Properties();
				InputStream stream = Thread.currentThread()
						.getContextClassLoader().getResourceAsStream(
								CMSConstants.APPLICATION_PROPERTIES);
				prop.load(stream);
		
				// if the uploaded document is excel file.
				 if (fileName.endsWith(".csv")) { // if the uploaded
														// document is csv file.
					 Map<String, Integer> map=UploadUniversityEmailHandler.getInstance().getPersonalDataMap();
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
					String source = prop.getProperty(CMSConstants.UPLOAD_SECONDLANGUAGE_CSV);
		
					file = new File(filePath+source);
					FileInputStream stream1 = new FileInputStream(file);
					LabeledCSVParser parser = new LabeledCSVParser(
							new CSVParser(stream1));
					PersonalData perosnalDataBo=null;
					List<PersonalData> personalDataBOList= new ArrayList<PersonalData>();
					while (parser.getLine() != null) {
						if (parser.getValueByLabel("RegisterNo") != null && !StringUtils.isEmpty(parser.getValueByLabel("RegisterNo"))){
							if(map.containsKey(parser.getValueByLabel("RegisterNo"))){
								perosnalDataBo=new PersonalData();
								perosnalDataBo.setId(map.get(parser.getValueByLabel("RegisterNo")));
							}else
								perosnalDataBo=null;
						}
						if (parser.getValueByLabel("UniversityEmail") != null && !StringUtils.isEmpty(parser.getValueByLabel("UniversityEmail"))) {
							if(perosnalDataBo!=null){
								perosnalDataBo.setUniversityEmail(parser.getValueByLabel("UniversityEmail"));
							}
						}
						if (perosnalDataBo != null) {
							personalDataBOList.add(perosnalDataBo);
						}
					}
					boolean isAdded = false;
					if (personalDataBOList != null) {
						String user = uploadForm.getUserId();
						isAdded = UploadUniversityEmailHandler.getInstance().addUploadedData(personalDataBOList, user);
						if (isAdded) {
							// if adding is success.
							ActionMessage message = new ActionMessage("knowledgepro.admission.upload.universityEmail.successful");
							uploadForm.resetFields();
							messages.add("messages", message);
							saveMessages(request, messages);
						} else {
							// if adding is failure.
							ActionMessage message = new ActionMessage("knowledgepro.admission.upload.universityEmail.failure");
							errors.add(CMSConstants.ERROR, message);
							addErrors(request, errors);
						}
					}else{
						// if adding is failure.
						ActionMessage message = new ActionMessage("knowledgepro.employee.upload.payscaleGrade.failure");
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
			log.info("Exception in uploadUniversityEmail method in UploadUniversityEmailAction");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			uploadForm.setErrorMessage(msg);
			uploadForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		log.info("end of uploadUniversityEmail method in UploadUniversityEmailAction class.");
		return mapping.findForward(CMSConstants.UPLOAD_UNIVERSITY_EMAIL_INIT);
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
