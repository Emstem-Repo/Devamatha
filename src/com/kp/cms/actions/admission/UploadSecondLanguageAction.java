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
import com.kp.cms.forms.admission.UploadSecondLanguageForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.UploadSecondLanguageHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.PersonalDataTO;

public class UploadSecondLanguageAction extends BaseDispatchAction {
	private static final Log log = LogFactory
			.getLog(UploadInterviewResultAction.class);

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

	public ActionForward initUploadSecondLanguage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UploadSecondLanguageForm uploadSecondLanguageForm = (UploadSecondLanguageForm) form;
		log
				.info("call of initInterviewResult method in UploadInterviewResultAction class.");
		try {
			setRequiredDatatoForm(uploadSecondLanguageForm, request);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			uploadSecondLanguageForm.setErrorMessage(msgKey);
			uploadSecondLanguageForm.setErrorStack(businessException
					.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			uploadSecondLanguageForm.setErrorMessage(msg);
			uploadSecondLanguageForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log
				.info("end of initInterviewResult method in UploadInterviewResultAction class.");
		return mapping.findForward(CMSConstants.UPLOAD_SECOND_LANGUAGE);
	}

	/**
	 * Method to set all active Program Types to the form
	 * 
	 * @param interviewBatchEntryForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(
			UploadSecondLanguageForm uploadSecondLanguageForm,
			HttpServletRequest request) throws Exception {
		if (ProgramTypeHandler.getInstance().getProgramType() != null) {
			uploadSecondLanguageForm.setProgramTypeList(ProgramTypeHandler
					.getInstance().getProgramType());
		}
		if (uploadSecondLanguageForm.getProgramId() != null
				&& uploadSecondLanguageForm.getAcademicYear() != null
				&& uploadSecondLanguageForm.getProgramId().length() > 0
				&& uploadSecondLanguageForm.getAcademicYear().length() > 0) {
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
					Integer.valueOf(uploadSecondLanguageForm.getProgramId()));
			request.setAttribute("coursesMap", courseMap);
		}
		if (uploadSecondLanguageForm.getProgramTypeId() != null
				&& uploadSecondLanguageForm.getProgramTypeId().length() > 0) {
			Map collegeMap = CommonAjaxHandler.getInstance()
					.getProgramsByProgramType(
							Integer.parseInt(uploadSecondLanguageForm
									.getProgramTypeId()));
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

	public ActionForward uploadSecondLanguageForStudent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UploadSecondLanguageForm uploadSecondLanguageForm = (UploadSecondLanguageForm) form;
		log
				.info("call of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
		 ActionErrors errors = uploadSecondLanguageForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, uploadSecondLanguageForm);
			if (errors.isEmpty()) {
				int applicationYear = Integer.parseInt(uploadSecondLanguageForm
						.getAcademicYear());
				int courseId = Integer.parseInt(uploadSecondLanguageForm
						.getCourseId());
				FormFile myFile = uploadSecondLanguageForm.getThefile();
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
				Map<String, String> secondMap=UploadSecondLanguageHandler.getInstance().getSeondLanguage();
				
				List<PersonalDataTO> results = null;

				// if the uploaded document is excel file.
				if (fileName.endsWith(".xls")) {
					byte[] fileData = myFile.getFileData();

					String source1 = prop
							.getProperty(CMSConstants.UPLOAD_SECONDLANGUAGE);
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
							.getProperty(CMSConstants.UPLOAD_SECONDLANGUAGE);

					file = new File(filePath+source);

					POIFSFileSystem system = new POIFSFileSystem(
							new FileInputStream(file));
					HSSFWorkbook workbook = new HSSFWorkbook(system);
					HSSFSheet sheet = workbook.getSheetAt(0);
					HSSFRow row;
					HSSFCell cell;

					int rows = sheet.getPhysicalNumberOfRows();

					int cols = 0; // No of columns
					int tmp = 0;
					PersonalDataTO personalDataTO;

					// This trick ensures that we get the data properly even if
					// it doesn't start from first few rows
					for (int i = 0; i < rows; i++) {
						row = sheet.getRow(i);
						if (row != null) {
							tmp = sheet.getRow(i).getPhysicalNumberOfCells();
							if (tmp > cols) {
								cols = tmp;
								break;
							}
						}
					}
					boolean isAdded = false;

					results = new ArrayList<PersonalDataTO>();
					for (int r = 1; r < rows; r++) {
						row = sheet.getRow(r);
						if (row != null) {
							personalDataTO = new PersonalDataTO();
							for (int c = 0; c < cols; c++) {
								cell = row.getCell((byte) c);
								if (cell != null
										&& !StringUtils
												.isEmpty(cell.toString())) {
									if (cell.getCellNum() == 0
											&& !StringUtils.isEmpty(cell
													.toString())) {
										if (pdMap != null && pdMap.containsKey(cell.toString().trim())) {
											personalDataTO.setId((Integer) pdMap.get((int) Float.parseFloat(cell.toString().trim())));
										} else {
											break;
										}
									}
									if (cell.getCellNum() == 1
											&& !StringUtils.isEmpty(cell
													.toString())) {
										if (secondMap != null && secondMap.containsKey(cell.toString().trim()) && personalDataTO != null) {
											personalDataTO.setSecondLanguage(cell.toString().trim());
										} else {
											break;
										}
									}
								}
							}
							if (personalDataTO != null
									&& personalDataTO.getId() != 0
									&& personalDataTO.getSecondLanguage() != null) {
								results.add(personalDataTO);
							}
						} else {
							continue;
						}
					}
					if (results != null && !results.isEmpty()) {
						String user = uploadSecondLanguageForm.getUserId();
						isAdded = UploadSecondLanguageHandler.getInstance()
								.addUploadedData(results, user);
						if (isAdded) {
							// if adding is success
							ActionMessage message = new ActionMessage(
									CMSConstants.UPLOAD_SECONDLANGUAGE_SUCCESS);
							messages.add("messages", message);
							saveMessages(request, messages);
						} else {
							// if adding is failure
							ActionMessage message = new ActionMessage(
									CMSConstants.UPLOAD_SECONDLANGUAGE_FAILURE);
							errors.add(CMSConstants.ERROR, message);
							addErrors(request, errors);
						}
					}else{
						// if adding is failure
						ActionMessage message = new ActionMessage(
								CMSConstants.UPLOAD_SECONDLANGUAGE_FAILURE);
						errors.add(CMSConstants.ERROR, message);
						addErrors(request, errors);
					}
				} else if (fileName.endsWith(".csv")) { // if the uploaded
														// document is csv file.
					PersonalDataTO personalDataTO = null;

					byte[] fileData = myFile.getFileData();
					String source1 = prop
							.getProperty(CMSConstants.UPLOAD_SECONDLANGUAGE_CSV);
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
							if (pdMap != null && pdMap.containsKey(parser.getValueByLabel("RegisterNo").trim())) {
								personalDataTO = new PersonalDataTO();
								personalDataTO.setId(pdMap.get(parser.getValueByLabel("RegisterNo").trim()));
							} else {
								personalDataTO=null;
							}
						if (parser.getValueByLabel("SecondLanguage") != null && !StringUtils.isEmpty(parser.getValueByLabel("SecondLanguage"))) {
							if (secondMap != null && secondMap.containsKey(parser.getValueByLabel("SecondLanguage").trim()) && personalDataTO != null) {
								personalDataTO.setSecondLanguage(parser.getValueByLabel("SecondLanguage").trim());
							} else {
								personalDataTO=null;
							}
						}
						if (personalDataTO != null
								&& personalDataTO.getId() != 0
								&& personalDataTO.getSecondLanguage() != null) {
							results.add(personalDataTO);
						}
					}
					boolean isAdded = false;
					if (results != null) {
						String user = uploadSecondLanguageForm.getUserId();
						isAdded = UploadSecondLanguageHandler.getInstance()
								.addUploadedData(results, user);
						if (isAdded) {
							// if adding is success.
							ActionMessage message = new ActionMessage(
									CMSConstants.UPLOAD_SECONDLANGUAGE_SUCCESS);
							uploadSecondLanguageForm.resetFields();
							messages.add("messages", message);
							saveMessages(request, messages);
						} else {
							// if adding is failure.
							ActionMessage message = new ActionMessage(
									CMSConstants.UPLOAD_SECONDLANGUAGE_FAILURE);
							errors.add(CMSConstants.ERROR, message);
							addErrors(request, errors);
						}
					}else{
						// if adding is failure.
						ActionMessage message = new ActionMessage(
								CMSConstants.UPLOAD_SECONDLANGUAGE_FAILURE);
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
			uploadSecondLanguageForm.setErrorMessage(msg);
			uploadSecondLanguageForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		// setting the programType details to form
		setRequiredDatatoForm(uploadSecondLanguageForm, request);

		log
				.info("end of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
		return mapping.findForward(CMSConstants.UPLOAD_SECOND_LANGUAGE);
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
