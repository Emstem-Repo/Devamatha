package com.kp.cms.actions.exam;

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
import com.kp.cms.forms.exam.UploadStudentBlockForm;
import com.kp.cms.handlers.exam.ExamBlockUnblockHallTicketHandler;
import com.kp.cms.handlers.exam.UploadStudentBlockHandler;
import com.kp.cms.to.exam.ExamBlockUnBlockCandidatesTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;

public class UploadStudentBlockAction extends BaseDispatchAction {
	private static final Log log = LogFactory
			.getLog(UploadStudentBlockAction.class);

	/**
	 * Method to set the required data to the form to display it in
	 * uploadStudentBlockUnblock.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadStudentBlock(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Entered initUploadStudentBlock");
		UploadStudentBlockForm uploadStudentBlockForm = (UploadStudentBlockForm) form;
		uploadStudentBlockForm.resetFields();
		setRequiredDatatoForm(uploadStudentBlockForm, request);
		log.info("Exit initUploadStudentBlock");

		return mapping.findForward(CMSConstants.UPLOAD_STUDENT_BLOCK);
	}

	/**
	 * @param uploadStudentBlockForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(UploadStudentBlockForm uploadStudentBlockForm,HttpServletRequest request) throws Exception {
		ExamBlockUnblockHallTicketHandler handler = new ExamBlockUnblockHallTicketHandler();
		List<KeyValueTO> examList = handler.getExamNameList();
		uploadStudentBlockForm.setListExamName(examList);
		int examId = 0;
		if (uploadStudentBlockForm.getExamId() != null
				&& !uploadStudentBlockForm.getExamId().isEmpty()) {
			examId = Integer.parseInt(uploadStudentBlockForm.getExamId());
		} else {
			if (examList != null && !examList.isEmpty()) {
				KeyValueTO to = uploadStudentBlockForm.getListExamName().get(0);
				examId = to.getId();
			}
		}
		uploadStudentBlockForm.setMapClass(CommonUtil.sortMapByValue(handler.getClassCodeByExamName(examId)));
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

	public ActionForward uploadStudentBlock(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UploadStudentBlockForm uploadStudentBlockForm = (UploadStudentBlockForm) form;
		log.info("call of uploadStudentBlock method in UploadInterviewResultAction class.");
		
		 ActionErrors errors = uploadStudentBlockForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		uploadStudentBlockForm.setNotUploaded(false);
		try {
			setUserId(request, uploadStudentBlockForm);
			String[] str = request.getParameterValues("classIds");
			if(str==null || str.length==0){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Class"));
			}
			if (errors.isEmpty()) {
				Map<String, String> admMap = UploadStudentBlockHandler.getInstance().getAdmAppDetails(uploadStudentBlockForm);
				FormFile myFile = uploadStudentBlockForm.getThefile();
				String contentType = myFile.getContentType();
				
				String fileName = myFile.getFileName();
				File file = null;
				Properties prop = new Properties();
				InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(stream);
				// getting adm_appln, grade, interview status details.
				List<ExamBlockUnBlockCandidatesTO> results = new ArrayList<ExamBlockUnBlockCandidatesTO>();;
				int examId=Integer.parseInt(uploadStudentBlockForm.getExamId());
				String type=uploadStudentBlockForm.getTypeId();
				
				List<String> notUploaded=new ArrayList<String>();

				// if the uploaded document is excel file.
				if (fileName.endsWith(".xls")) {
					byte[] fileData = myFile.getFileData();

					String source1 = prop.getProperty("knowledgepro.exam.excelupload");

					File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);

					InputStream inputStream = new ByteArrayStreamInfo(contentType, fileData).getInputStream();
					OutputStream out = new FileOutputStream(file1);
					byte buffer[] = new byte[1024];
					int len;
					while ((len = inputStream.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					out.close();
					inputStream.close();
					String source = prop.getProperty("knowledgepro.exam.excelupload");

					file = new File(request.getRealPath("")+ "//TempFiles//"+source);

					POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
					HSSFWorkbook workbook = new HSSFWorkbook(system);
					HSSFSheet sheet = workbook.getSheetAt(0);
					HSSFRow row;
					HSSFCell cell;

					int rows = sheet.getPhysicalNumberOfRows();

					int cols = 0; // No of columns
					int tmp = 0;

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
					

					for (int r = 1; r < rows; r++) {
						ExamBlockUnBlockCandidatesTO to=null;
						row = sheet.getRow(r);
						if (row != null) {
							for (int c = 0; c < cols; c++) {
								cell = row.getCell((byte) c);
								if (cell != null && !StringUtils.isEmpty(cell.toString())) {
									if (cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString())) {
										if (admMap != null && admMap.containsKey(cell.toString().trim())) {
											to = new ExamBlockUnBlockCandidatesTO();
											String data=admMap.get(cell.toString().trim());
											String[] d=data.split("_");
											to.setStudentId(Integer.parseInt(d[0]));
											to.setClassId(Integer.parseInt(d[1]));
											to.setExamId(examId);
											to.setType(type);
										}else{
											notUploaded.add(cell.toString());
										} 
									}
									if (cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())) {
										if(to!=null){
											to.setReason(cell.toString().trim());
										}
									}
								}
							}
							if (to != null && to.getStudentId() != 0 && to.getExamId()!=0 ) {
								results.add(to);
							}
						} else {
							continue;
						}
					}
					if (results != null) {
						String user = uploadStudentBlockForm.getUserId();
						isAdded = UploadStudentBlockHandler.getInstance().addUploadedData(results, user);
						if (isAdded) {
							// if adding is success
							ActionMessage message = new ActionMessage("knowledgepro.exam.excelupload.success");
							messages.add("messages", message);
							saveMessages(request, messages);
							uploadStudentBlockForm.resetFields();
						} else {
							// if adding is failure
							ActionMessage message = new ActionMessage("knowledgepro.exam.excelupload.failure");
							errors.add(CMSConstants.ERROR, message);
							addErrors(request, errors);
						}
					}
				} else if (fileName.endsWith(".csv")) { // if the uploaded
														// document is csv file.
					byte[] fileData = myFile.getFileData();
					String source1 = prop.getProperty(CMSConstants.UPLOAD_INTERVIEW_CSV);

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
							.getProperty(CMSConstants.UPLOAD_INTERVIEW_CSV);

					file = new File(request.getRealPath("")+ "//TempFiles//"+source);
					FileInputStream stream1 = new FileInputStream(file);
					LabeledCSVParser parser = new LabeledCSVParser(new CSVParser(stream1));


					while (parser.getLine() != null) {
						ExamBlockUnBlockCandidatesTO to=null;
						if (parser.getValueByLabel("RegisterNo") != null && !StringUtils.isEmpty(parser.getValueByLabel("RegisterNo")))
							if (admMap != null && admMap.containsKey(parser.getValueByLabel("RegisterNo"))) {
								if (admMap.containsKey(parser.getValueByLabel("RegisterNo").trim())) {
									to = new ExamBlockUnBlockCandidatesTO();
									String data=admMap.get(parser.getValueByLabel("RegisterNo").trim());
									String[] d=data.split("_");
									to.setStudentId(Integer.parseInt(d[0]));
									to.setClassId(Integer.parseInt(d[1]));
									to.setExamId(examId);
									to.setType(type);
								} 
							} else{
								notUploaded.add(parser.getValueByLabel("RegisterNo").trim());
								continue;
							} 
						if (parser.getValueByLabel("Reason") != null && !StringUtils.isEmpty(parser.getValueByLabel("Reason")))
							to.setReason(parser.getValueByLabel("Reason"));
						
						if (to != null && to.getStudentId() != 0 && to.getExamId()!=0 ) {
							results.add(to);
						}else {
							continue;
						}
					}
					boolean isAdded = false;
					if (results != null) {
						String user = uploadStudentBlockForm.getUserId();
						isAdded = UploadStudentBlockHandler.getInstance().addUploadedData(results, user);
						if (isAdded) {
							// if adding is success.
							ActionMessage message = new ActionMessage("knowledgepro.exam.excelupload.success");
							uploadStudentBlockForm.resetFields();
							messages.add("messages", message);
							saveMessages(request, messages);
						} else {
							// if adding is failure.
							ActionMessage message = new ActionMessage("knowledgepro.exam.excelupload.failure");
							errors.add(CMSConstants.ERROR, message);
							addErrors(request, errors);
						}
						if(notUploaded!=null && !notUploaded.isEmpty()){
							UploadStudentBlockHandler.getInstance().exportTOCSV(notUploaded,request);
							uploadStudentBlockForm.setNotUploaded(true);
						}
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
			uploadStudentBlockForm.setErrorMessage(msg);
			uploadStudentBlockForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		// setting the programType details to form
		setRequiredDatatoForm(uploadStudentBlockForm, request);
		log.info("end of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
		return mapping.findForward(CMSConstants.UPLOAD_STUDENT_BLOCK);
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
