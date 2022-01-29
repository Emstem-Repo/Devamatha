package com.kp.cms.actions.hostel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.kp.cms.actions.reports.ScoreSheetAction;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.UploadHostelStudentsForm;
import com.kp.cms.handlers.hostel.HostelCheckinHandler;
import com.kp.cms.handlers.hostel.UploadHostelStudentsHandler;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.HostelCSVUpdater;

public class UploadHostelStudentsAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(ScoreSheetAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadHostelStudents(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered initUploadHostelStudents input");
		UploadHostelStudentsForm uploadHostelStudentsForm = (UploadHostelStudentsForm) form;
		try {
			uploadHostelStudentsForm.resetFields();
			setRequiredDatatoForm(uploadHostelStudentsForm, request);
			log.info("Exit initUploadHostelStudents input");
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			uploadHostelStudentsForm.setErrorMessage(msg);
			uploadHostelStudentsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		
		}
		return mapping.findForward(CMSConstants.UPLOAD_HOSTEL_STUDENTS);
	}

	/**
	 * @param uploadHostelStudentsForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(UploadHostelStudentsForm uploadHostelStudentsForm,
			HttpServletRequest request) throws Exception{
		List<HostelTO> hostelList = HostelCheckinHandler.getInstance().getHostelDetails();
		uploadHostelStudentsForm.setHostelList(hostelList);
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

	public ActionForward uploadHostelData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UploadHostelStudentsForm uploadHostelStudentsForm = (UploadHostelStudentsForm) form;
		log.info("call of uploadHostelData method in UploadHostelStudentsAction class.");
		 ActionErrors errors = uploadHostelStudentsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, uploadHostelStudentsForm);
			if (errors.isEmpty()) {
				int applicationYear = Integer.parseInt(uploadHostelStudentsForm
						.getAcademicYear());
				FormFile myFile = uploadHostelStudentsForm.getThefile();
				String contentType = myFile.getContentType();

				String fileName = myFile.getFileName();
				File file = null;
				Properties prop = new Properties();
				InputStream stream = Thread.currentThread()
						.getContextClassLoader().getResourceAsStream(
								CMSConstants.APPLICATION_PROPERTIES);
				prop.load(stream);
				// getting personal Data details.

				// if the uploaded document is excel file.
				if (fileName.endsWith(".csv")) { // if the uploaded
														// document is csv file.

					byte[] fileData = myFile.getFileData();
					String source1 = prop
							.getProperty(CMSConstants.UPLOAD_HOSTELSTUDENT);

					File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);

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
							.getProperty(CMSConstants.UPLOAD_HOSTELSTUDENT);

					file = new File(request.getRealPath("")+ "//TempFiles//"+source);
					FileInputStream stream1 = new FileInputStream(file);
					
					Map<String, Integer> pdMap = UploadHostelStudentsHandler.getInstance().getAppDetails(applicationYear,uploadHostelStudentsForm.getApplnRegno());
					Map<Integer, Integer> maxCountMap=UploadHostelStudentsHandler.getInstance().getMaxCountForRoomType(uploadHostelStudentsForm.getHostelId());
					Map<String, Integer> rTypeMap = UploadHostelStudentsHandler.getInstance().getRoomTypeForHostel(uploadHostelStudentsForm.getHostelId());
					List<HlRoomTransaction> results = HostelCSVUpdater.getInstance().parseFile(stream1,pdMap,maxCountMap,rTypeMap,uploadHostelStudentsForm);
					
					boolean isAdded = false;
					if (results != null) {
						isAdded = UploadHostelStudentsHandler.getInstance()
								.addUploadedData(results);
						if (isAdded) {
							// if adding is success.
							ActionMessage message = new ActionMessage(
									CMSConstants.UPLOAD_HOSTELSTUDENT_SUCCESS);
							uploadHostelStudentsForm.resetFields();
							messages.add("messages", message);
							saveMessages(request, messages);
						} else {
							// if adding is failure.
							ActionMessage message = new ActionMessage(
									CMSConstants.UPLOAD_HOSTELSTUDENT_FAILURE);
							errors.add(CMSConstants.ERROR, message);
							addErrors(request, errors);
						}
					}else{
						// if adding is failure.
						ActionMessage message = new ActionMessage(
								CMSConstants.UPLOAD_HOSTELSTUDENT_FAILURE);
						errors.add(CMSConstants.ERROR, message);
						addErrors(request, errors);
					}
				} else { // if the uploaded document is not excel/csv file.
					ActionMessage message = new ActionMessage(
							CMSConstants.UPLOAD_INTERVIEW_DOCUMENT);
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
			uploadHostelStudentsForm.setErrorMessage(msg);
			uploadHostelStudentsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		// setting the programType details to form
		setRequiredDatatoForm(uploadHostelStudentsForm, request);

		log.info("end of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
		return mapping.findForward(CMSConstants.UPLOAD_HOSTEL_STUDENTS);
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
