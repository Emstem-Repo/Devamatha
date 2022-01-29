package com.kp.cms.actions.employee;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.employee.EmpPayAllowanceDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.UploadPayScaleGradeForm;
import com.kp.cms.handlers.employee.UploadPayScaleGradeHandler;

public class UploadPayScaleGradeAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UploadPayScaleGradeAction.class);
	
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
	
	public ActionForward initUploadPayScaleGrade(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	log.info("call of initUploadPayScaleGrade method in UploadPayScaleGradeAction class.");
	UploadPayScaleGradeForm uploadPayScaleGradeForm=(UploadPayScaleGradeForm)form;
	uploadPayScaleGradeForm.resetFields();
	log.info("end of initUploadPayScaleGrade method in UploadPayScaleGradeAction class.");
	return mapping.findForward(CMSConstants.UPLOAD_PAY_SCALE_GRADE);
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
	
	public ActionForward uploadPayScaleGrade(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		UploadPayScaleGradeForm uploadPayScaleGradeForm = (UploadPayScaleGradeForm) form;
		log.info("call of uploadPayScaleGrade method in UploadPayScaleGradeAction class.");
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, uploadPayScaleGradeForm);
			if(uploadPayScaleGradeForm.getThefile()!=null){
				if(uploadPayScaleGradeForm.getThefile().getFileName()!=null && !StringUtils.isEmpty(uploadPayScaleGradeForm.getThefile().getFileName().trim())){
					String extn="";
					int index = uploadPayScaleGradeForm.getThefile().getFileName().lastIndexOf(".");
					if(index != -1){
						extn = uploadPayScaleGradeForm.getThefile().getFileName().substring(index, uploadPayScaleGradeForm.getThefile().getFileName().length());
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
				FormFile myFile = uploadPayScaleGradeForm.getThefile();
				String contentType = myFile.getContentType();
		
				String fileName = myFile.getFileName();
				File file = null;
				Properties prop = new Properties();
				InputStream stream = Thread.currentThread()
						.getContextClassLoader().getResourceAsStream(
								CMSConstants.APPLICATION_PROPERTIES);
				prop.load(stream);
				// getting personal Data details.
				Map<String, Integer> employeeMap = UploadPayScaleGradeHandler.getInstance().getEmpDetails();
				Map<String, Integer> payScaleIDMap=UploadPayScaleGradeHandler.getInstance().getPayScaleBo();
				Map<String,Integer> allowanceMap=UploadPayScaleGradeHandler.getInstance().getAllowanceDetails();
				Map<String, String> payScaleMap=UploadPayScaleGradeHandler.getInstance().getPayScaleMap();
				
				
		
				// if the uploaded document is excel file.
				 if (fileName.endsWith(".csv")) { // if the uploaded
														// document is csv file.
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
					List<Employee> empBoList=new ArrayList<Employee>();
					Employee empBo=null;
					while (parser.getLine() != null) {
						if (parser.getValueByLabel("EmployeeID") != null && !StringUtils.isEmpty(parser.getValueByLabel("EmployeeID")))
							if (employeeMap != null && employeeMap.containsKey(parser.getValueByLabel("EmployeeID").toUpperCase())) {
								empBo= new Employee();
								empBo.setId(employeeMap.get(parser.getValueByLabel("EmployeeID").toUpperCase()));
							} else {
								empBo=null;
							}
						if (empBo != null && empBo.getId() != 0) {
						if (parser.getValueByLabel("grade") != null && !StringUtils.isEmpty(parser.getValueByLabel("grade"))) {
							if(payScaleIDMap!=null && payScaleIDMap.containsKey(parser.getValueByLabel("grade"))){
								PayScaleBO payScaleBo=new PayScaleBO();	
								payScaleBo.setId(payScaleIDMap.get(parser.getValueByLabel("grade")));
								empBo.setPayScaleId(payScaleBo);
							}
							if(payScaleMap!=null && payScaleMap.containsKey(parser.getValueByLabel("grade"))){
								empBo.setScale(payScaleMap.get(parser.getValueByLabel("grade")));
							}
						}
						Set<EmpPayAllowanceDetails> empPayAllowanceDetailsSet=null;
						if(allowanceMap!=null && !allowanceMap.isEmpty()){
							Iterator itrmap=allowanceMap.entrySet().iterator();
							empPayAllowanceDetailsSet=new HashSet<EmpPayAllowanceDetails>();
							while (itrmap.hasNext()) {
								Map.Entry pairs = (Map.Entry) itrmap.next();
								if(pairs.getKey()!=null){
									if(parser.getValueByLabel(pairs.getKey().toString())!=null && !StringUtils.isEmpty(parser.getValueByLabel(pairs.getKey().toString()))){
										EmpPayAllowanceDetails empPayAllowanceDetails=new EmpPayAllowanceDetails();
										EmpAllowance empAllowance=new EmpAllowance();
										empAllowance.setId(Integer.parseInt(pairs.getValue().toString()));
										empPayAllowanceDetails.setEmpAllowance(empAllowance);
										empPayAllowanceDetails.setAllowanceValue(parser.getValueByLabel(pairs.getKey().toString()));
										empPayAllowanceDetails.setIsActive(true);
										empPayAllowanceDetails.setCreatedBy(uploadPayScaleGradeForm.getUserId());
										empPayAllowanceDetails.setModifiedBy(uploadPayScaleGradeForm.getUserId());
										empPayAllowanceDetails.setCreatedDate(new Date());
										empPayAllowanceDetails.setModifiedDate(new Date());
										empPayAllowanceDetailsSet.add(empPayAllowanceDetails);
									}
								}
							}
							empBo.setEmpPayAllowance(empPayAllowanceDetailsSet);
						}
						if (parser.getValueByLabel("Gross") != null && !StringUtils.isEmpty(parser.getValueByLabel("Gross"))) {
							empBo.setGrossPay(parser.getValueByLabel("Gross"));
						}
							empBoList.add(empBo);
						}
					}
					boolean isAdded = false;
					if (empBoList != null) {
						String user = uploadPayScaleGradeForm.getUserId();
						isAdded = UploadPayScaleGradeHandler.getInstance().addUploadedData(empBoList, user);
						if (isAdded) {
							// if adding is success.
							ActionMessage message = new ActionMessage("knowledgepro.employee.upload.payscaleGrade.successful");
							uploadPayScaleGradeForm.resetFields();
							messages.add("messages", message);
							saveMessages(request, messages);
						} else {
							// if adding is failure.
							ActionMessage message = new ActionMessage("knowledgepro.employee.upload.payscaleGrade.failure");
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
			log.info("Exception uploadInterviewResultEntry");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			uploadPayScaleGradeForm.setErrorMessage(msg);
			uploadPayScaleGradeForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		log.info("end of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
		return mapping.findForward(CMSConstants.UPLOAD_PAY_SCALE_GRADE);
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


