package com.kp.cms.actions.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.apache.struts.upload.FormFile;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.UploadEmpAttendanceForm;
import com.kp.cms.handlers.employee.UploadEmpAttendanceHandler;
import com.kp.cms.to.employee.UploadEmpAttendanceTO;

public class UploadEmpAttendanceAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UploadEmpAttendanceAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadEmpAttendance(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Upload Emp Attendance input");
		UploadEmpAttendanceForm uploadEmpAttendanceForm = (UploadEmpAttendanceForm) form;
		uploadEmpAttendanceForm.resetFields();
		log.info("Exit Upload Emp Attendance input");
		
		return mapping.findForward(CMSConstants.INIT_UPLOAD_EMP_ATTENDANCE);
	}
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadEmpAttendance(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UploadEmpAttendanceForm uploadEmpAttendanceForm = (UploadEmpAttendanceForm) form;
		log.info("call of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
		 ActionErrors errors = uploadEmpAttendanceForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, uploadEmpAttendanceForm);
			if(uploadEmpAttendanceForm.getThefile()!=null){
				if(uploadEmpAttendanceForm.getThefile().getFileName()!=null && !StringUtils.isEmpty(uploadEmpAttendanceForm.getThefile().getFileName().trim())){
					String extn="";
					int index = uploadEmpAttendanceForm.getThefile().getFileName().lastIndexOf(".");
					if(index != -1){
						extn = uploadEmpAttendanceForm.getThefile().getFileName().substring(index, uploadEmpAttendanceForm.getThefile().getFileName().length());
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
				FormFile myFile = uploadEmpAttendanceForm.getThefile();
				String contentType = myFile.getContentType();
		
				String fileName = myFile.getFileName();
				File file = null;
				Properties prop = new Properties();
				InputStream stream = Thread.currentThread() .getContextClassLoader().getResourceAsStream( CMSConstants.APPLICATION_PROPERTIES);
				prop.load(stream);
				// getting Employee details.
				Map<Integer,String> empMap =UploadEmpAttendanceHandler.getInstance().getAllEmployees();
				
				List<UploadEmpAttendanceTO> results = null;
		
				// if the uploaded document is excel file.
				 if (fileName.endsWith(".csv")) { // if the uploaded
														// document is csv file.
					 UploadEmpAttendanceTO to = null;
		
					byte[] fileData = myFile.getFileData();
					String source1 = prop.getProperty(CMSConstants.UPLOAD_EMPATT_CSV);
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
					String source = prop .getProperty(CMSConstants.UPLOAD_EMPATT_CSV);
		
					file = new File(filePath+source);
					FileInputStream stream1 = new FileInputStream(file);
					LabeledCSVParser parser = new LabeledCSVParser(
							new CSVParser(stream1));
		
					results = new ArrayList<UploadEmpAttendanceTO>();
		
					while (parser.getLine() != null) {
						if (parser.getValueByLabel("fingerPrintId") != null && !StringUtils.isEmpty(parser.getValueByLabel("fingerPrintId")))
							if (empMap.containsKey(Integer.parseInt(parser.getValueByLabel("fingerPrintId")))) {
								to =new UploadEmpAttendanceTO();
								String[] ids=empMap.get(Integer.parseInt(parser.getValueByLabel("fingerPrintId"))).split("_");
								to.setEmpId(Integer.parseInt(ids[0]));
								to.setEmpTypeId(Integer.parseInt(ids[1]));
								to.setFingerTypeId(Integer.parseInt(parser.getValueByLabel("fingerPrintId")));
							} else {
								to=null;
							}
						if (parser.getValueByLabel("TerminalId") != null && !StringUtils.isEmpty(parser.getValueByLabel("TerminalId"))  && to!=null) {
								to.setTerminalId(parser.getValueByLabel("TerminalId").trim());
						}
						if (parser.getValueByLabel("Date") != null && !StringUtils.isEmpty(parser.getValueByLabel("Date")) && to!=null) {
								to.setDate(parser.getValueByLabel("Date").trim());
						}
						if (parser.getValueByLabel("time") != null && !StringUtils.isEmpty(parser.getValueByLabel("time")) && to!=null) {
							to.setTime(parser.getValueByLabel("time").trim());
						}
						if (parser.getValueByLabel("status") != null && !StringUtils.isEmpty(parser.getValueByLabel("status")) && to!=null) {
								to.setStatus(parser.getValueByLabel("status").trim());
						}
						if (to != null && to.getEmpId() != 0) {
							results.add(to);
						}
					}
					boolean isAdded = false;
					if (results != null) {
						isAdded = UploadEmpAttendanceHandler.getInstance().addUploadEmpAttendance(results, uploadEmpAttendanceForm.getUserId());
						if (isAdded) {
							// if adding is success.
							ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess"," Upload Employee Attendance");
							uploadEmpAttendanceForm.resetFields();
							messages.add("messages", message);
							saveMessages(request, messages);
						} else {
							// if adding is failure.
							ActionMessage message = new ActionMessage("kknowledgepro.admin.addfailure"," Upload Employee Attendance");
							errors.add(CMSConstants.ERROR, message);
							addErrors(request, errors);
						}
					}else{
						// if adding is failure.
						ActionMessage message = new ActionMessage(""," Upload Employee Attendance");
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
			uploadEmpAttendanceForm.setErrorMessage(msg);
			uploadEmpAttendanceForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		// setting the programType details to form
		log.info("end of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
		return mapping.findForward(CMSConstants.INIT_UPLOAD_EMP_ATTENDANCE);
	}
}
