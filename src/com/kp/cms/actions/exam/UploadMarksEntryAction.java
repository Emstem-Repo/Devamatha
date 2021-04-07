package com.kp.cms.actions.exam;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
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
import com.kp.cms.actions.admin.StudentUploadPhotoAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CasteForm;
import com.kp.cms.forms.exam.UploadMarksEntryForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.NewExamMarksEntryHandler;
import com.kp.cms.handlers.exam.UploadMarksEntryHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.to.exam.UploadMarksEntryTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class UploadMarksEntryAction extends BaseDispatchAction
{
	private static final Log log = LogFactory.getLog(UploadMarksEntryAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initMarksUpload(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		log.info("Entered initMarksUpload");
		UploadMarksEntryForm marksEntryForm=(UploadMarksEntryForm)form;
		marksEntryForm.resetFields();
		//added by smitha for input academic year addition
		setRequiredDataToForm(marksEntryForm);
		return mapping.findForward(CMSConstants.INIT_MARK_UPLOAD);
	}
	
	/**
	 * setting the list of exam types and exam names to form-Smitha
	 * @param marksEntryForm
	 * @throws Exception
	 */
	private void setRequiredDataToForm(UploadMarksEntryForm marksEntryForm) throws Exception{
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
			Map<Integer, String> programMap=CommonAjaxHandler.getInstance().getProgramsByExamName(currentExam);
			marksEntryForm.setProgramList(programMap);
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		
		log.info("Entered upload marks");
		UploadMarksEntryForm marksEntryForm=(UploadMarksEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = marksEntryForm.validate(mapping, request);
		setUserId(request, marksEntryForm);
		Double maxMark=0.0;
		if (errors.isEmpty())
		{
			try 
			{
				marksEntryForm.setDummySchemeId(marksEntryForm.getSchemeId());
				String schemeId=marksEntryForm.getSchemeId();
			 	String splitScheme[] = marksEntryForm.getSchemeId().split("_");
			 	if(splitScheme.length==2)
			 		marksEntryForm.setSchemeId(splitScheme[1]);
				Map<String,Integer> studnetMap=UploadMarksEntryHandler.getInstance().getStudentMap(Integer.parseInt(marksEntryForm.getProgramId()),Integer.parseInt(marksEntryForm.getCourseId()),Integer.parseInt(marksEntryForm.getSchemeId()),Integer.parseInt(marksEntryForm.getSubjectId()),marksEntryForm.getIsPrevious(),Integer.parseInt(marksEntryForm.getExamId()),marksEntryForm.getExamType(),marksEntryForm.getSubjectType());
				maxMark=UploadMarksEntryHandler.getInstance().getMaxMarkOfSubject(marksEntryForm);
				if(maxMark==null){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.notDefined"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_MARK_UPLOAD);
				}else{
				
				// reading xlsx code from here
				FormFile myFile = marksEntryForm.getUploadedFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
				File file = null;
			    Properties prop = new Properties();
			    InputStream stream = UploadMarksEntryAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<UploadMarksEntryTO> evaluator1List=new ArrayList<UploadMarksEntryTO>();
			    List<UploadMarksEntryTO> evaluator2List=new ArrayList<UploadMarksEntryTO>();
			    List<Integer> studentIdList=new ArrayList<Integer>();
			    //if the uploaded document is excel file.
			    if(fileName.endsWith(".xls"))
			    {
			    	byte[] fileData = myFile.getFileData();
			       	String source1=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL);
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
				 	String source=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL);
				 	file = new File(request.getRealPath("")+ "//TempFiles//"+source);
				 	POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
				 	HSSFWorkbook workbook = new HSSFWorkbook(system);
				 	HSSFSheet sheet = workbook.getSheetAt(0);
				 	HSSFRow row;
				 	HSSFCell cell;
				 	int rows = sheet.getPhysicalNumberOfRows();
				 	int cols = 0; // No of columns
				 	int tmp = 0;
				 	UploadMarksEntryTO evaluator1Marks=null;
				 	UploadMarksEntryTO evaluator2Marks=null;
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
				 	
				 	List<Integer>evaluatorIds=null;
				 	if(marksEntryForm.getExamType().equalsIgnoreCase("internal"))
				 		evaluatorIds=new ArrayList<Integer>();
				 	else
				 		evaluatorIds=UploadMarksEntryHandler.getInstance().getEvaluatorIds(Integer.parseInt(marksEntryForm.getSubjectId()),Integer.parseInt(marksEntryForm.getCourseId()),Integer.parseInt(marksEntryForm.getSchemeId()),Integer.parseInt(marksEntryForm.getYear()));
				 	boolean isAdded = false;
				 	for(int r = 1; r < rows; r++) 
				 	{
				 		row = sheet.getRow(r);
				 		evaluator1Marks=null;
				 		evaluator2Marks=null;
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
	 			                		if(regNo.startsWith("9"))
	 			                			regNo="0"+regNo;
				 						if(regNo.endsWith(".0"))
	 			                		{
	 			                			regNo=StringUtils.chop(regNo);
	 			                			regNo=StringUtils.chop(regNo);
	 			                		}
				 						if(studnetMap!=null && studnetMap.containsKey(regNo))
				 						{
				 							if(!studentIdList.contains(studnetMap.containsKey(regNo))){
				 								studentIdList.add(studnetMap.get(regNo));
				 							}
			 								evaluator1Marks = new UploadMarksEntryTO();
				 							evaluator1Marks.setStudentId(String.valueOf(studnetMap.get(regNo)));
				 							evaluator1Marks.setExamId(marksEntryForm.getExamId());
				 							evaluator1Marks.setSubjectId(marksEntryForm.getSubjectId());
				 							evaluator1Marks.setSubjectType(marksEntryForm.getSubjectType());
				 							if(evaluatorIds.size()!=0)
				 							{	
				 								evaluator1Marks.setEvaluatorId(evaluatorIds.get(0));
				 							}	
				 							if(evaluatorIds.size()==2)
				 							{	
				 								evaluator2Marks = new UploadMarksEntryTO(); 
				 								evaluator2Marks.setStudentId(String.valueOf(studnetMap.get(regNo)));
				 								evaluator2Marks.setExamId(marksEntryForm.getExamId());
				 								evaluator2Marks.setSubjectId(marksEntryForm.getSubjectId());
				 								evaluator2Marks.setSubjectType(marksEntryForm.getSubjectType());
				 								evaluator2Marks.setEvaluatorId(evaluatorIds.get(1));
				 							}	
				 						}	
				 						else
				 						{
				 							break;
				 						}
				 					} 
				 					if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString()))
				 					{	
				 						if(cell.toString().trim().length()>0)
				 						{	
				 							String marks=cell.toString().trim();
				 							if(CommonUtil.isStringContainsNumbers(marks)){
				 								if(Double.parseDouble(marks)> maxMark){
					 								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.exeeded"));
					 								addErrors(request, errors);
					 								marksEntryForm.setSchemeId(schemeId);
					 								retainDropDowns(marksEntryForm,errors);
					 								marksEntryForm.setUploadedFile(null);
					 								return mapping.findForward(CMSConstants.INIT_MARK_UPLOAD);
					 							}else{
					 								
					 							
			 			                		if(marks.endsWith(".0")){
			 			                			marks=StringUtils.chop(marks);
			 			                			marks=StringUtils.chop(marks);
			 			                		}
			 			                		if(CommonUtil.isValidDecimal(marks))
			 			                			evaluator1Marks.setMarks(""+Math.round(Double.parseDouble(marks)));
			 			                		else
			 			                			evaluator1Marks.setMarks(marks);
					 							}
				 							}else{
				 								if(marks.endsWith(".0")){
			 			                			marks=StringUtils.chop(marks);
			 			                			marks=StringUtils.chop(marks);
			 			                		}
			 			                		if(CommonUtil.isValidDecimal(marks))
			 			                			evaluator1Marks.setMarks(""+Math.round(Double.parseDouble(marks)));
			 			                		else
			 			                			evaluator1Marks.setMarks(marks);
				 							}
				 						}
				 						else
				 						{
				 							break;
				 						}
				 					}
				 					if(cell.getCellNum() ==2 && !StringUtils.isEmpty(cell.toString()))
							       	{
							       		if(cell.toString().trim().length()>0)
				 						{	
				 							if(evaluator2Marks!=null)
				 							{	
				 								String marks=cell.toString().trim();
				 								if(CommonUtil.isStringContainsNumbers(marks)){
				 									if(Double.parseDouble(marks)> maxMark){
				 										errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.exeeded"));
				 										addErrors(request, errors);
				 										marksEntryForm.setSchemeId(schemeId);
				 										retainDropDowns(marksEntryForm,errors);
				 										marksEntryForm.setUploadedFile(null);
				 										return mapping.findForward(CMSConstants.INIT_MARK_UPLOAD);
				 									}else{
				 										if(marks.endsWith(".0"))
				 										{
				 											marks=StringUtils.chop(marks);
				 											marks=StringUtils.chop(marks);
				 										}
				 										if(CommonUtil.isValidDecimal(marks))
				 											evaluator2Marks.setMarks(""+Math.round(Double.parseDouble(marks)));
				 										else
				 											evaluator2Marks.setMarks(marks);
				 									}	
				 								}else{
				 									if(marks.endsWith(".0"))
			 										{
			 											marks=StringUtils.chop(marks);
			 											marks=StringUtils.chop(marks);
			 										}
			 										if(CommonUtil.isValidDecimal(marks))
			 											evaluator2Marks.setMarks(""+Math.round(Double.parseDouble(marks)));
			 										else
			 											evaluator2Marks.setMarks(marks);
				 								}
				 						}}
				 						else
				 						{
				 							break;
				 						}
							       	}
				 				}
				 			}	
		                    if((evaluator1Marks !=null && evaluator1Marks.getMarks()!=null) || (evaluator2Marks !=null && evaluator2Marks.getMarks()!=null))
		                    {
		                    	if(evaluator1Marks !=null && evaluator1Marks.getMarks()!=null)
		                    		evaluator1List.add(evaluator1Marks);
		                    	if(evaluator2Marks !=null && evaluator2Marks.getMarks()!=null)
		                    		evaluator2List.add(evaluator2Marks);
		                    }
		                    else
		                    {
		                    	continue;
		                    }
				 		}
				 	}	
				// validateMaxMarks(marksEntryForm,errors);
				
				 	if(evaluator1List.size()!= 0 || evaluator2List.size()!=0)
				 	{
				 		validateMarksAlreadyEntered(marksEntryForm,errors,studentIdList);
				 		
				 		
				 			Map<String,Integer> classIdMap=UploadMarksEntryHandler.getInstance().getClassIdForStudents(Integer.parseInt(marksEntryForm.getProgramId()),Integer.parseInt(marksEntryForm.getCourseId()),Integer.parseInt(marksEntryForm.getSchemeId()),Integer.parseInt(marksEntryForm.getSubjectId()),marksEntryForm.getIsPrevious(),Integer.parseInt(marksEntryForm.getExamId()),marksEntryForm.getExamType(),marksEntryForm.getSubjectType());
				 			if(errors.isEmpty()){
				 			isAdded = UploadMarksEntryHandler.getInstance().saveUploadedData(evaluator1List, evaluator2List, marksEntryForm.getSubjectType(), classIdMap, marksEntryForm);
					 		if(isAdded)
					 		{
					 			ActionMessage message = new ActionMessage("knowldgepro.exam.upload.marks.success");
					 			messages.add("messages", message);
					 			saveMessages(request, messages);
					 			marksEntryForm.resetFieldsAfterUpload();
					 			retainDropDownsAfterUpload(marksEntryForm,errors);
					 		}
					 		else
					 		{
					 			marksEntryForm.setSchemeId(schemeId);
					 			retainDropDowns(marksEntryForm,errors);
					 			ActionMessage message = new ActionMessage("knowldgepro.exam.upload.marks.failed");
					 			errors.add(CMSConstants.ERROR, message);
					 			addErrors(request, errors);
					 		}
				 		}else{
				 			marksEntryForm.setSchemeId(schemeId);
				 			retainDropDowns(marksEntryForm,errors);
				 			marksEntryForm.setEvaluator1List(evaluator1List);
				 			marksEntryForm.setEvaluator2List(evaluator2List);
				 			marksEntryForm.setClassIdMap(classIdMap);
				 			addErrors(request, errors);
				 			return mapping.findForward(CMSConstants.INIT_MARK_UPLOAD);
				 		}
				 	}
				 
				 	else
				 	{
				 		retainDropDowns(marksEntryForm,errors);
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
			}
			}catch (Exception exception) 
			{
				String msg = super.handleApplicationException(exception);
				marksEntryForm.setErrorMessage(msg);
				marksEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		else 
		{
			addErrors(request, errors);
			retainDropDowns(marksEntryForm,errors);
			marksEntryForm.setUploadedFile(null);
			return mapping.findForward(CMSConstants.INIT_MARK_UPLOAD);
		}
		return mapping.findForward(CMSConstants.INIT_MARK_UPLOAD);
	}

	/**
	 * @param marksEntryForm
	 * @param errors
	 */
	private void validateMarksAlreadyEntered(UploadMarksEntryForm marksEntryForm, ActionErrors errors,List<Integer> studentIdList) throws Exception {
		boolean isAlreadyExists=UploadMarksEntryHandler.getInstance().checkMarksAlreadyExists(studentIdList,marksEntryForm);
		if(isAlreadyExists){
			marksEntryForm.setValidateMsg("Marks Already Uploaded For this Exam And Subject. Press Cancel to Cancel the Operation . Ok will override the Marks");
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.upload.marks.already.exists"));
		}
	}

	private void retainDropDownsAfterUpload(UploadMarksEntryForm marksEntryForm,ActionErrors errors) throws Exception
	{
		CommonAjaxExamHandler ajaxExamHandler=new CommonAjaxExamHandler();
		CommonAjaxHandler ajaxHandler=new CommonAjaxHandler();
		if(marksEntryForm.getExamType()!=null && marksEntryForm.getExamType().trim().length()!=0 && marksEntryForm.getYear()!=null && !marksEntryForm.getYear().trim().isEmpty())
		{
			Map<Integer, String>examNameMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(marksEntryForm.getExamType(),Integer.parseInt(marksEntryForm.getYear()));
			marksEntryForm.setListExamName(examNameMap);
		}
		if(marksEntryForm.getExamId()!=null &&marksEntryForm.getExamId().trim().length()!=0)
		{
			Map<Integer, String>programNameMap=ajaxExamHandler.getProgramsByExamName(marksEntryForm.getExamId());
			marksEntryForm.setProgramList(programNameMap);
		}
		if(marksEntryForm.getProgramId()!=null &&marksEntryForm.getProgramId().trim().length()!=0)
		{
			
			Map<Integer, String>courseMap=ajaxHandler.getCourseByProgram(Integer.parseInt(marksEntryForm.getProgramId()));
			marksEntryForm.setCourseList(courseMap);
		}
		if(marksEntryForm.getCourseId()!=null &&marksEntryForm.getCourseId().trim().length()!=0)
		{
			
			Map<String, String>schemeMap=ajaxExamHandler.getSchemeNoByExamIdCourseId(Integer.parseInt(marksEntryForm.getExamId()),Integer.parseInt(marksEntryForm.getCourseId()));
			marksEntryForm.setSchemeList(schemeMap);
		}
		if(marksEntryForm.getSchemeId()!=null &&marksEntryForm.getSchemeId().trim().length()!=0)
		{
			String schemeSplit[] = null;
			if(errors.isEmpty())
				schemeSplit = marksEntryForm.getDummySchemeId().split("_");
			else
				schemeSplit = marksEntryForm.getSchemeId().split("_");
			int schemeNo = Integer.parseInt(schemeSplit[1]);
			int schemeId = Integer.parseInt(schemeSplit[0]);
			Map<Integer, String>subjectMap=ajaxExamHandler.getSubjectsByCourseSchemeExamId(Integer.parseInt(marksEntryForm.getCourseId()), schemeId, schemeNo, Integer.parseInt(marksEntryForm.getExamId()));
			marksEntryForm.setSubjectList(subjectMap);
		}
		/*if(marksEntryForm.getSubjectId()!=null &&marksEntryForm.getSubjectId().trim().length()!=0)
		{
			String value=ajaxExamHandler.getSubjectsTypeBySubjectId(Integer.parseInt(marksEntryForm.getSubjectId()));
			Map<String, String>subjectTypeMap=new HashMap<String, String>();
			if (value.equalsIgnoreCase("T")) {
				subjectTypeMap.put("T", "Theory");
			}
			if (value.equalsIgnoreCase("P")) {
				subjectTypeMap.put("P", "Practical");
			}
			if (value.equalsIgnoreCase("B")) {
				subjectTypeMap.put("T", "Theory");
				subjectTypeMap.put("P", "Practical");
				value = "t";
			}
			marksEntryForm.setSubjectTypeList(subjectTypeMap);
		}*/
		if(errors.isEmpty())
			marksEntryForm.setSchemeId(marksEntryForm.getDummySchemeId());
		
	}
	
	
	
	/**
	 * @param marksEntryForm
	 * @param errors
	 */
	private void retainDropDowns(UploadMarksEntryForm marksEntryForm,ActionErrors errors) throws Exception
	{
		CommonAjaxExamHandler ajaxExamHandler=new CommonAjaxExamHandler();
		CommonAjaxHandler ajaxHandler=new CommonAjaxHandler();
		if(marksEntryForm.getExamType()!=null && marksEntryForm.getExamType().trim().length()!=0 && marksEntryForm.getYear()!=null && !marksEntryForm.getYear().trim().isEmpty())
		{
			Map<Integer, String>examNameMap=ajaxHandler.getExamNameByExamTypeAndYear(marksEntryForm.getExamType(),Integer.parseInt(marksEntryForm.getYear()));
			marksEntryForm.setListExamName(examNameMap);
		}
		if(marksEntryForm.getExamId()!=null &&marksEntryForm.getExamId().trim().length()!=0)
		{
			Map<Integer, String>programNameMap=ajaxExamHandler.getProgramsByExamName(marksEntryForm.getExamId());
			marksEntryForm.setProgramList(programNameMap);
		}
		if(marksEntryForm.getProgramId()!=null &&marksEntryForm.getProgramId().trim().length()!=0)
		{
			
			Map<Integer, String>courseMap=ajaxHandler.getCourseByProgram(Integer.parseInt(marksEntryForm.getProgramId()));
			marksEntryForm.setCourseList(courseMap);
		}
		if(marksEntryForm.getCourseId()!=null &&marksEntryForm.getCourseId().trim().length()!=0)
		{
			
			Map<String, String>schemeMap=ajaxExamHandler.getSchemeNoByExamIdCourseId(Integer.parseInt(marksEntryForm.getExamId()),Integer.parseInt(marksEntryForm.getCourseId()));
			marksEntryForm.setSchemeList(schemeMap);
		}
		if(marksEntryForm.getSchemeId()!=null &&marksEntryForm.getSchemeId().trim().length()!=0)
		{
			String schemeSplit[] = null;
			if(errors.isEmpty())
				schemeSplit = marksEntryForm.getDummySchemeId().split("_");
			else
				schemeSplit = marksEntryForm.getSchemeId().split("_");
				int schemeNo = Integer.parseInt(schemeSplit[1]);
			int schemeId = Integer.parseInt(schemeSplit[0]);
			Map<Integer, String>subjectMap=ajaxExamHandler.getSubjectsByCourseSchemeExamId(Integer.parseInt(marksEntryForm.getCourseId()), schemeId, schemeNo, Integer.parseInt(marksEntryForm.getExamId()));
			marksEntryForm.setSubjectList(subjectMap);
		}
		if(marksEntryForm.getSubjectId()!=null &&marksEntryForm.getSubjectId().trim().length()!=0)
		{
			String value=ajaxExamHandler.getSubjectsTypeBySubjectId(Integer.parseInt(marksEntryForm.getSubjectId()));
			Map<String, String>subjectTypeMap=new HashMap<String, String>();
			if (value.equalsIgnoreCase("T")) {
				subjectTypeMap.put("T", "Theory");
			}
			if (value.equalsIgnoreCase("P")) {
				subjectTypeMap.put("P", "Practical");
			}
			if (value.equalsIgnoreCase("B")) {
				subjectTypeMap.put("T", "Theory");
				subjectTypeMap.put("P", "Practical");
				value = "t";
			}
			marksEntryForm.setSubjectTypeList(subjectTypeMap);
		}
		if(errors.isEmpty())
			marksEntryForm.setSchemeId(marksEntryForm.getDummySchemeId());
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UploadMarksEntryForm marksEntryForm=(UploadMarksEntryForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
	//	validateMaxMarks(marksEntryForm,errors);
		if(errors.isEmpty()){
		boolean isAdded = UploadMarksEntryHandler.getInstance().saveUploadedData(marksEntryForm.getEvaluator1List(),marksEntryForm.getEvaluator2List(), marksEntryForm.getSubjectType(), marksEntryForm.getClassIdMap(), marksEntryForm);
 		if(isAdded)
 		{
 			ActionMessage message = new ActionMessage("knowldgepro.exam.upload.marks.success");
 			messages.add("messages", message);
 			saveMessages(request, messages);
 			//marksEntryForm.resetFields();
 			marksEntryForm.resetFieldsAfterUpload();
 			retainDropDownsAfterUpload(marksEntryForm,errors);
 		}
 		else
 		{
 			retainDropDowns(marksEntryForm,errors);
 			ActionMessage message = new ActionMessage("knowldgepro.exam.upload.marks.failed");
 			errors.add(CMSConstants.ERROR, message);
 			addErrors(request, errors);
 		}}
		else
 		{
 			retainDropDowns(marksEntryForm,errors);
 			ActionMessage message = new ActionMessage("knowldgepro.exam.upload.marks.failed");
 			errors.add(CMSConstants.ERROR, message);
 			addErrors(request, errors);
 		}
 		return mapping.findForward(CMSConstants.INIT_MARK_UPLOAD);
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
	/**
	 * @param marksEntryForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateMaxMarks(UploadMarksEntryForm marksEntryForm ,ActionErrors errors) throws Exception{
		Set<StudentMarksTO> studentList=UploadMarksEntryHandler.getInstance().getStudentForInput(marksEntryForm);
		List<StudentMarksTO> list1 = new ArrayList<StudentMarksTO>(studentList);
		Collections.sort(list1);
		marksEntryForm.setStudentList(list1);
		Double maxMark=UploadMarksEntryHandler.getInstance().getMaxMarkOfSubject(marksEntryForm);
		if(maxMark==null){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.notDefined"));
		}else{
		List<StudentMarksTO> list=marksEntryForm.getStudentList();
		Iterator<StudentMarksTO> itr=list.iterator();
		String reg="";
		String regValid="";
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
		while (itr.hasNext()) {
			StudentMarksTO to = (StudentMarksTO) itr.next();
			if((to.getIsTheory() && to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty())){
				if(!StringUtils.isNumeric(to.getTheoryMarks())){
					if(examAbscentCode!=null && !examAbscentCode.contains(to.getTheoryMarks())){
						if(regValid.isEmpty()){
							regValid=to.getRegisterNo();
						}else{
							regValid=regValid+","+to.getRegisterNo();
						}
					}
				}else{
					double marks=Double.parseDouble(to.getTheoryMarks());
					if(marks>maxMark){
						if(reg.isEmpty()){
							reg=to.getRegisterNo();
						}else{
							reg=reg+","+to.getRegisterNo();
						}
					}
				}
				
			}else if(to.getIsPractical() && to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty()){
				if(!StringUtils.isNumeric(to.getPracticalMarks())){
					if(examAbscentCode!=null && !examAbscentCode.contains(to.getPracticalMarks())){
						if(regValid.isEmpty()){
							regValid=to.getRegisterNo();
						}else{
							regValid=regValid+","+to.getRegisterNo();
						}
					}
				}else{
					double marks=Double.parseDouble(to.getPracticalMarks());
					if(marks>maxMark){
						if(reg.isEmpty()){
							reg=to.getRegisterNo();
						}else{
							reg=reg+","+to.getRegisterNo();
						}
					}
				}
			}
		}
		if(!reg.isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.registerNo",reg));
		}
		if(!regValid.isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.registerNo.validMarks",regValid));
		}
		}
	}
	
}
