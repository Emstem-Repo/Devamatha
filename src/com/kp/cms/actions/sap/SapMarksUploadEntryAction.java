package com.kp.cms.actions.sap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.sap.SapMarksUploadEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.sap.SapMarksUploadEntryHandler;
import com.kp.cms.to.sap.SapMarksUploadEntryTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class SapMarksUploadEntryAction extends BaseDispatchAction {
  
	private static final Log log = LogFactory.getLog(SapMarksUploadEntryAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * init method for Sap Marks Upload just display the page
	 * @return
	 */
	public ActionForward initSapMarksUpload(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		log.info("Entered initMarksUpload");
		SapMarksUploadEntryForm marksEntryForm=(SapMarksUploadEntryForm)form;
		marksEntryForm.resetFields();
		setRequiredDataToForm(marksEntryForm);
		return mapping.findForward(CMSConstants.INIT_SAP_MARK_UPLOAD);
	}
	
	/**
	 * setting the list of exam types and exam names to form-mehaboob
	 * @param marksEntryForm
	 * @throws Exception
	 */
	private void setRequiredDataToForm(SapMarksUploadEntryForm marksEntryForm) throws Exception{
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(marksEntryForm.getYear()!=null && !marksEntryForm.getYear().isEmpty()){
			year=Integer.parseInt(marksEntryForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(marksEntryForm.getExamType(),year); 
		marksEntryForm.setListExamName(examMap);
		
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(marksEntryForm.getExamType());
		if((marksEntryForm.getExamId()==null || marksEntryForm.getExamId().trim().isEmpty()) && currentExam!=null){
			marksEntryForm.setExamId(currentExam);
		}
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * uploading student sap marks according to register number
	 */
	public ActionForward sapUploadMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered Sap upload marks");
		SapMarksUploadEntryForm marksEntryForm=(SapMarksUploadEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = marksEntryForm.validate(mapping, request);
		setUserId(request, marksEntryForm);
		String uploadedRegNo="";
		String notUploadedRegNo="";
		int count=0;
		if (errors.isEmpty())
		{
			try 
			{
				List<Integer> studentCourseList=SapMarksUploadEntryHandler.getInstance().getStudentCourseByExamId(marksEntryForm.getExamId());
				// reading xlsx code from here
				FormFile myFile = marksEntryForm.getUploadedFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
				File file = null;
			    Properties prop = new Properties();
			    InputStream stream = SapMarksUploadEntryAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<SapMarksUploadEntryTO> sapMarksUploadEntryTOList=new ArrayList<SapMarksUploadEntryTO>();
			    String marksString=null;
			    String marksStringUploaded=null;
			    Object[] objects=SapMarksUploadEntryHandler.getInstance().getExamSettings();
					if(objects!=null){
						if(objects[0]!=null && !objects[0].toString().isEmpty())
							marksString=objects[0].toString();
						else
							marksString=null;
						if(objects[1]!=null && !objects[1].toString().isEmpty())
							marksStringUploaded=objects[1].toString();
						else
							marksStringUploaded=null;
					}
			    //if the uploaded document is excel file.
			    if(fileName.endsWith(".xls"))
			    {
			    	byte[] fileData = myFile.getFileData();
			       	String source1=prop.getProperty(CMSConstants.UPLOAD_SAP_MARKS_INTERNAL);
				 	File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);
				 	InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
				 	OutputStream out = new FileOutputStream(file1);
				 	byte buffer[] = new byte[1024];
				 	int len;
				 	while ((len = inputStream.read(buffer)) > 0)
				 	{
				 		out.write(buffer, 0, len);
				 	}
				 	out.close();
				 	inputStream.close();
				 	String source=prop.getProperty(CMSConstants.UPLOAD_SAP_MARKS_INTERNAL);
				 	String maxMark=prop.getProperty(CMSConstants.UPLOAD_SAP_MAX_MARKS_UPLOAD);
//				 	String subjectId=prop.getProperty(CMSConstants.UPLOAD_SAP_MARKS_UPLOAD_SUBJECT_ID);
				 	String subjectId = String.valueOf(CMSConstants.UPLOAD_SAP_MARKS_UPLOAD_SUBJECT_ID);
				 	file = new File(request.getRealPath("")+ "//TempFiles//"+source);
				 	POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
				 	HSSFWorkbook workbook = new HSSFWorkbook(system);
				 	HSSFSheet sheet = workbook.getSheetAt(0);
				 	HSSFRow row;
				 	HSSFCell cell;
				 	int rows = sheet.getPhysicalNumberOfRows();
				 	int cols = 0; // No of columns
				 	int tmp = 0;
				 	SapMarksUploadEntryTO marksUploadEntryTO=null;
				 	// This trick ensures that we get the data properly even if it doesn't start from first few rows
				 	for(int i = 0; i < rows; i++) 
				 	{
				 		row = sheet.getRow(i);
				 		if(row != null) 
				 		{
				 			tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				 			if(tmp > cols) 
				 			{
				 				cols = tmp;
				 				break;
				 			}
				 		}
				 	}
				 	boolean isAdded = false;
				 	for(int r = 1; r < rows; r++) 
				 	{
				 		row = sheet.getRow(r);
				 		marksUploadEntryTO=null;
				 		if(row != null) 
				 		{
				 			for(int c = 0; c < cols;c++) 
				 			{
				 				cell = row.getCell((byte)c);
				 				if(cell != null && !StringUtils.isEmpty(cell.toString())) 
				 				{
				 					if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString()))
				 					{
				 						String regNo=cell.toString().trim();
				 						try
										{
											Double a = Double.parseDouble(regNo);
											Long x=(a).longValue(); 
											regNo=String.valueOf(x);
										}
										catch(NumberFormatException e)
										{
											regNo=cell.toString().trim();
										}
	 			                			Student student=SapMarksUploadEntryHandler.getInstance().getStudentDetailsByRegNo(regNo);
	 			                			if(student!=null && studentCourseList!=null && !studentCourseList.isEmpty()){
	 			                				if(student.getClassSchemewise()!=null){
	 			                					if(student.getClassSchemewise().getClasses()!=null){
	 			                						if(student.getClassSchemewise().getClasses().getCourse()!=null){
	 			                							if(student.getClassSchemewise().getClasses().getCourse().getId()!=0){
	 			                								if(studentCourseList.contains(student.getClassSchemewise().getClasses().getCourse().getId())){
	 			                									marksUploadEntryTO=new SapMarksUploadEntryTO();
	 			   	 			                				    marksUploadEntryTO.setStudentId(String.valueOf(student.getId()));
	 			   	 			                				    marksUploadEntryTO.setExamId(marksEntryForm.getExamId());
	 			   	 			                				    marksUploadEntryTO.setSubjectId(subjectId);
	 			   	 			                					marksUploadEntryTO.setClassId(student.getClassSchemewise().getClasses().getId());
	 			   	 			                					uploadedRegNo=uploadedRegNo+", "+regNo;
	 			   	 			                				    count=1;
	 			                								}else{
	 			                									count=2;
	 			                									notUploadedRegNo=notUploadedRegNo+", "+regNo;
	 			                								}
	 			                							}
	 			                						}
	 			                					}
	 			                				}
	 			                			}else{
	 			                				errors.add(CMSConstants.ERROR,new ActionError("knowldgepro.sap.exam.upload.marks.invalid",regNo));
				 								addErrors(request, errors);
				 								marksEntryForm.setUploadedFile(null);
				 								return mapping.findForward(CMSConstants.INIT_SAP_MARK_UPLOAD);
	 			                			}
				 					} 
				 					if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString()))
				 					{	if(count==1){
				 						if(cell.toString().trim().length()>0)
				 						{	
				 							String marks=cell.toString().trim();
				 							if(marks.endsWith(".0"))
	 										{
	 											marks=StringUtils.chop(marks);
	 											marks=StringUtils.chop(marks);
	 										}
				 							if(CommonUtil.isStringContainsNumbers(marks)){
				 								if(Double.parseDouble(marks)> Double.parseDouble(maxMark)){
					 								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.exeeded"));
					 								addErrors(request, errors);
					 								marksEntryForm.setUploadedFile(null);
					 								marksEntryForm.setValidateMsg(null);
					 								return mapping.findForward(CMSConstants.INIT_SAP_MARK_UPLOAD);
					 							}else{
			 			                		if(marks.endsWith(".0")){
			 			                			marks=StringUtils.chop(marks);
			 			                			marks=StringUtils.chop(marks);
			 			                		}
			 			                		if(CommonUtil.isValidDecimal(marks))
			 			                			marksUploadEntryTO.setMarks(""+Math.round(Double.parseDouble(marks)));
			 			                		else
			 			                			marksUploadEntryTO.setMarks(marks);
					 							}
				 							}else{
				 								if(marksString.equalsIgnoreCase(marks)|| marksStringUploaded.equalsIgnoreCase(marks)){
				 									if(marks.endsWith(".0")){
				 			                			marks=StringUtils.chop(marks);
				 			                			marks=StringUtils.chop(marks);
				 			                		}
				 			                		if(CommonUtil.isValidDecimal(marks))
				 			                			marksUploadEntryTO.setMarks(""+Math.round(Double.parseDouble(marks)));
				 			                		else
				 			                			marksUploadEntryTO.setMarks(marks);
				 								}else{
				 									  errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.sap.exam.marks.enter.correct.without.string",marks));
					 								  addErrors(request, errors);
					 								  marksEntryForm.setUploadedFile(null);
					 								  marksEntryForm.setValidateMsg(null);
					 								  return mapping.findForward(CMSConstants.INIT_SAP_MARK_UPLOAD);
				 								}
				 							}
				 						}
				 						else
				 						{
				 							break;
				 						}
				 					}
				 					}
				 				}
				 			}
				 			if((marksUploadEntryTO !=null && marksUploadEntryTO.getMarks()!=null))
		                    {
		                    	if(marksUploadEntryTO !=null && marksUploadEntryTO.getMarks()!=null)
		                    		sapMarksUploadEntryTOList.add(marksUploadEntryTO);
		                    }
		                    else
		                    {
		                    	continue;
		                    }
				 		}
				 	}	
				// validateMaxMarks(marksEntryForm,errors);
				
				 	if(sapMarksUploadEntryTOList.size()!= 0 || sapMarksUploadEntryTOList.size()!=0)
				 	{
				 		 marksEntryForm.setSubjectId(subjectId);
				 		String checking=request.getParameter("checking");
				 		 if(checking==null){
				 			validateMarksAlreadyEntered(marksEntryForm,errors,sapMarksUploadEntryTOList);
				 		 }
				 			if(errors.isEmpty()){
				 			isAdded = SapMarksUploadEntryHandler.getInstance().saveSapUploadedData(sapMarksUploadEntryTOList,marksEntryForm);
					 		if(isAdded)
					 		{
					 			uploadedRegNo=uploadedRegNo.substring(1);
					 			ActionMessage message = new ActionMessage("knowldgepro.sap.exam.upload.marks.success.for.registerNo",uploadedRegNo);
					 			messages.add("messages", message);
					 			saveMessages(request, messages);
					 			if(notUploadedRegNo!=null && !notUploadedRegNo.isEmpty()){
					 				notUploadedRegNo=notUploadedRegNo.substring(1);
					 				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.sap.exam.marks.upload.failure.for.registerNo",notUploadedRegNo));
									addErrors(request, errors);   					 				
					 			}
					 			marksEntryForm.resetFields();
					 			setRequiredDataToForm(marksEntryForm);
					 		}
					 		else
					 		{
					 			setRequiredDataToForm(marksEntryForm);
					 			ActionMessage message = new ActionMessage("knowldgepro.exam.upload.marks.failed");
					 			errors.add(CMSConstants.ERROR, message);
					 			addErrors(request, errors);
					 		}
				 		}else{
				 			setRequiredDataToForm(marksEntryForm);
				 			marksEntryForm.setMarksUploadEntryTOList(sapMarksUploadEntryTOList);
				 			addErrors(request, errors);
				 		}
				 	}
				 	else
				 	{
				 		setRequiredDataToForm(marksEntryForm);
				 		ActionMessage message = new ActionMessage("knowldgepro.exam.upload.marks.invalid");
			 			errors.add(CMSConstants.ERROR, message);
			 			addErrors(request, errors);
				 	}
			}else
		     {		//if the uploaded document is not excel/csv file.
	      		 ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_DOCUMENT);
	    		 errors.add(CMSConstants.ERROR,message);
	    		 saveErrors(request, errors);
		      }
			}catch (Exception exception) 
			{
				String msg = super.handleApplicationException(exception);
				marksEntryForm.setErrorMessage(msg);
				marksEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else 
		{
			addErrors(request, errors);
			setRequiredDataToForm(marksEntryForm);
			marksEntryForm.setUploadedFile(null);
		}
		return mapping.findForward(CMSConstants.INIT_SAP_MARK_UPLOAD);
	}
	
	/**
	 * @param marksEntryForm
	 * @param errors
	 */
	private void validateMarksAlreadyEntered(SapMarksUploadEntryForm marksEntryForm, ActionErrors errors,List<SapMarksUploadEntryTO> marksUploadEntryTOList) throws Exception {
		List<MarksEntryDetails> marksEntryDetailsList=SapMarksUploadEntryHandler.getInstance().checkSapMarksAlreadyExists(marksUploadEntryTOList,marksEntryForm);
		if(marksEntryDetailsList!=null && !marksEntryDetailsList.isEmpty()){
			marksEntryForm.setValidateMsg("Marks Already Uploaded For this Exam And Subject. Press Cancel to Cancel the Operation . Ok will override the Marks");
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.upload.marks.already.exists"));
		}
	}
		/**
		 * @author Administrator
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
