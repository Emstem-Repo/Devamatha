package com.kp.cms.actions.exam;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.kp.cms.forms.exam.UploadExamInternalMarkSupplementaryForm;
import com.kp.cms.handlers.exam.UploadExamInternalMarkSupplementaryHandler;
import com.kp.cms.handlers.exam.UploadInternalOverAllHandler;
import com.kp.cms.to.exam.UploadExamInternalMarkSupplementaryTO;
import com.kp.cms.utilities.CommonUtil;

	public class UploadExamInternalMarkSupplementaryAction extends BaseDispatchAction{
		private static final Log log = LogFactory.getLog(UploadExamInternalMarkSupplementaryAction.class);
		
		/**
		 * Method to set the required data to the form to display it in uploadInternalMarkSupplementary.jsp
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initUploadInternalMarkSupplementary(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			log.info("Entered initUploadInternalMarkSupplementary ");
			UploadExamInternalMarkSupplementaryForm uInternalMarkSupplementaryForm = (UploadExamInternalMarkSupplementaryForm) form;
			uInternalMarkSupplementaryForm.resetFields();
			log.info("Exit initUploadInternalMarkSupplementary ");
			
			return mapping.findForward(CMSConstants.UPLOAD_INTERNAL_SUPPLEMENTARY_INFO);
		}
		/**
		 * Method to save the internal supplementary mark
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward saveInternalMarkSupplementary(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			log.info("Entered saveInternalMarkSupplementary - getCandidates");
			
			UploadExamInternalMarkSupplementaryForm uInternalMarkSupplementaryForm = (UploadExamInternalMarkSupplementaryForm) form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = uInternalMarkSupplementaryForm.validate(mapping, request);
			setUserId(request, uInternalMarkSupplementaryForm);
			if (errors.isEmpty()) {
			
				try {
					Map<String,Integer> studnetMap=UploadInternalOverAllHandler.getInstance().getStudentMap();
					Map<String,Integer> classMap=UploadInternalOverAllHandler.getInstance().getClassMap();
					Map<String,Integer> subjectMap=UploadInternalOverAllHandler.getInstance().getSubjectCodeMap();
					Map<String,Integer> examMap=UploadInternalOverAllHandler.getInstance().getExamMap();
					
					// reading xlsx code from here
					FormFile myFile = uInternalMarkSupplementaryForm.getThefile();
				    String contentType = myFile.getContentType();
				    String fileName    = myFile.getFileName();
					File file = null;
				    Properties prop = new Properties();
				    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				    prop.load(stream);
				    List<UploadExamInternalMarkSupplementaryTO> result=new ArrayList<UploadExamInternalMarkSupplementaryTO>();
				    List<UploadExamInternalMarkSupplementaryTO> internalMarkNotExistList=new ArrayList<UploadExamInternalMarkSupplementaryTO>();
				    List<String>duplicateList=new ArrayList<String>();
				    UploadExamInternalMarkSupplementaryTO uploadExamInternalMarkSupplementaryTO=null;
				    UploadExamInternalMarkSupplementaryTO uploadExamInternalMarkSupplementaryNotExistTO=null;
				    String examName=null;
				    String regNo=null;
				    String className=null;
				    //if the uploaded document is excel file.
				    if(fileName.endsWith(".xlsx")){
				    	byte[] fileData = myFile.getFileData();
				    	String source1=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL);
						File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);
						InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
						OutputStream out = new FileOutputStream(file1);
						byte buffer[] = new byte[1024];
						int len;
						while ((len = inputStream.read(buffer)) > 0){
							out.write(buffer, 0, len);
						}
						out.close();
						inputStream.close();
						String source=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL);
						file = new File(request.getRealPath("")+ "//TempFiles//"+source);
						String year=null;
						XSSFWorkbook work=new XSSFWorkbook(new FileInputStream(request.getRealPath("")+ "//TempFiles//"+source));
		            	XSSFSheet sh=work.getSheetAt(0);
		            	Iterator rows = sh.rowIterator();
		            	 while (rows.hasNext()) {
		                 	XSSFRow row = (XSSFRow) rows.next();
		                     Iterator cells = row.cellIterator();
		                     while (cells.hasNext()) {
		                         XSSFCell cell = (XSSFCell) cells.next();
			                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
			                	if(cell.getColumnIndex() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
			                		uploadExamInternalMarkSupplementaryNotExistTO = new UploadExamInternalMarkSupplementaryTO();
			                		uploadExamInternalMarkSupplementaryTO = new UploadExamInternalMarkSupplementaryTO();
			                		examName=cell.toString().trim();
			                		if(examName.endsWith(".0"))
			                		{	
			                			examName=StringUtils.chop(examName);
			                			examName=StringUtils.chop(examName);
			                		}	
			                		if(examMap!=null && examMap.containsKey(examName)){
			                			uploadExamInternalMarkSupplementaryTO.setExamId(String.valueOf(examMap.get(examName)));
			                		}else{
			                			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
			                		}
								} 
			                	if(cell.getColumnIndex() == 2 && !StringUtils.isEmpty(cell.toString()))
			                	{	
				                		 regNo=cell.toString().trim();
				                		 if(regNo.startsWith("9"))
		 			                		regNo="0"+regNo;
				                		 if(regNo.endsWith(".0"))
				                		{
				                			regNo=StringUtils.chop(regNo);
				                			regNo=StringUtils.chop(regNo);
				                		}
			                			if(studnetMap!=null && studnetMap.containsKey(regNo)){
			                				uploadExamInternalMarkSupplementaryTO.setStudentId(String.valueOf(studnetMap.get(regNo)));
				                		}else{
				                			uploadExamInternalMarkSupplementaryNotExistTO.setRegNo(regNo);
				                			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
				                		}
								}
			                	if(cell.getColumnIndex() ==3 && !StringUtils.isEmpty(cell.toString())){
			                		String semesterNo=cell.toString().trim();
			                		if(semesterNo.endsWith(".0"))
			                		{
			                			semesterNo=StringUtils.chop(semesterNo);
			                			semesterNo=StringUtils.chop(semesterNo);
			                			uploadExamInternalMarkSupplementaryTO.setSemesterNo(semesterNo);
			                		}else
			                		uploadExamInternalMarkSupplementaryTO.setSemesterNo(semesterNo);
			                	}
			                	if(cell.getColumnIndex() == 4 && !StringUtils.isEmpty(cell.toString())){
			                		year=cell.toString().trim();
			                		year=year.substring(0, 4);
			                	}
			                	if(cell.getColumnIndex() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
			                		className=cell.toString().trim();
			                		if(classMap!=null && classMap.containsKey(className+"_"+year)){
			                			uploadExamInternalMarkSupplementaryTO.setClassId(String.valueOf(classMap.get(className+"_"+year)));
			                		}else{
			                			uploadExamInternalMarkSupplementaryNotExistTO.setClassName(className);
			                			uploadExamInternalMarkSupplementaryNotExistTO.setRegNo(regNo);
			                			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
			                		}
								} 
			                	if(cell.getColumnIndex() == 6 && !StringUtils.isEmpty(cell.toString())){
			                		if(subjectMap!=null && subjectMap.containsKey(cell.toString().trim())){
			                			uploadExamInternalMarkSupplementaryTO.setSubjectId(String.valueOf(subjectMap.get(cell.toString().trim())));
			                		}else{
			                			uploadExamInternalMarkSupplementaryNotExistTO.setSubjectCode(cell.toString().trim());
			                			uploadExamInternalMarkSupplementaryNotExistTO.setRegNo(regNo);
			                			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
			                			uploadExamInternalMarkSupplementaryNotExistTO.setClassName(className);
			                			uploadExamInternalMarkSupplementaryNotExistTO.setYear(year);
			                		}
								} 
			                	if(cell.getColumnIndex() == 7 && !StringUtils.isEmpty(cell.toString())){
			                		String theorySubInternalMark=cell.toString().trim();
			                		if(theorySubInternalMark.endsWith(".0"))
			                		{
			                			theorySubInternalMark=StringUtils.chop(theorySubInternalMark);
			                			theorySubInternalMark=StringUtils.chop(theorySubInternalMark);
			                			uploadExamInternalMarkSupplementaryTO.setTheoryTotalSubInternalMarks(theorySubInternalMark);
			                		}else
			                		uploadExamInternalMarkSupplementaryTO.setTheoryTotalSubInternalMarks(theorySubInternalMark);
			                	}
			                	if(cell.getColumnIndex() == 8 && !StringUtils.isEmpty(cell.toString())){
			                		String pSubInternalMark=cell.toString().trim();
			                		if(pSubInternalMark.endsWith(".0"))
			                		{
			                			pSubInternalMark=StringUtils.chop(pSubInternalMark);
			                			pSubInternalMark=StringUtils.chop(pSubInternalMark);
			                			uploadExamInternalMarkSupplementaryTO.setPracticalTotalSubInternalMarks(pSubInternalMark);
			                		}else
			                		uploadExamInternalMarkSupplementaryTO.setPracticalTotalSubInternalMarks(pSubInternalMark);
			                	}
			                	if(cell.getColumnIndex() == 9 && !StringUtils.isEmpty(cell.toString())){
			                		String tAttendanceMark=cell.toString().trim();
			                		if(tAttendanceMark.endsWith(".0"))
			                		{
			                			tAttendanceMark=StringUtils.chop(tAttendanceMark);
			                			tAttendanceMark=StringUtils.chop(tAttendanceMark);
			                			uploadExamInternalMarkSupplementaryTO.setTheoryTotalAttendenceMarks(tAttendanceMark);
			                		}else
			                		uploadExamInternalMarkSupplementaryTO.setTheoryTotalAttendenceMarks(tAttendanceMark);
			                	}
			                	if(cell.getColumnIndex() == 10 && !StringUtils.isEmpty(cell.toString())){
			                		String pAttendanceMark=cell.toString().trim();
			                		if(pAttendanceMark.endsWith(".0"))
			                		{
			                			pAttendanceMark=StringUtils.chop(pAttendanceMark);
			                			pAttendanceMark=StringUtils.chop(pAttendanceMark);
			                			uploadExamInternalMarkSupplementaryTO.setPracticalTotalAttendenceMarks(pAttendanceMark);
			                		}else
			                		uploadExamInternalMarkSupplementaryTO.setPracticalTotalAttendenceMarks(pAttendanceMark);
			                	}
			                	if(cell.getColumnIndex() == 11 && !StringUtils.isEmpty(cell.toString())){
			                		uploadExamInternalMarkSupplementaryTO.setMarksType(cell.toString().trim());
			                	}
			                	if(cell.getColumnIndex() == 12 && !StringUtils.isEmpty(cell.toString())){
			                		if(cell.toString().trim().equalsIgnoreCase("pass"))
			                			uploadExamInternalMarkSupplementaryTO.setPass(true);
			                		else
			                			uploadExamInternalMarkSupplementaryTO.setPass(false);
			                	}
			                }
			            }
			            if(uploadExamInternalMarkSupplementaryTO !=null 
			            	&& uploadExamInternalMarkSupplementaryTO.getClassId()!=null 
			            	&& uploadExamInternalMarkSupplementaryTO.getStudentId()!=null 
			            	&& uploadExamInternalMarkSupplementaryTO.getExamId()!=null 
			            	&& uploadExamInternalMarkSupplementaryTO.getSubjectId()!=null)
			            {
			            	
			            	String checkDuplicate=uploadExamInternalMarkSupplementaryTO.getStudentId()+"_"+uploadExamInternalMarkSupplementaryTO.getExamId()+"_"+uploadExamInternalMarkSupplementaryTO.getClassId()+"_"+uploadExamInternalMarkSupplementaryTO.getSubjectId();
	 		            	if(!duplicateList.contains(checkDuplicate))
	 		            	{	
	 		            		result.add(uploadExamInternalMarkSupplementaryTO);
	 		            		duplicateList.add(checkDuplicate);
	 		            		uploadExamInternalMarkSupplementaryTO=null;
	 		            	}
			        	}
			            else
			            {
			        		if(uploadExamInternalMarkSupplementaryNotExistTO!=null){
			        			if(uploadExamInternalMarkSupplementaryNotExistTO.getRegNo()!=null){
			        				internalMarkNotExistList.add(uploadExamInternalMarkSupplementaryNotExistTO);
			        				uploadExamInternalMarkSupplementaryNotExistTO=null;
			        			}
			        		}
			        	}
			    	}
		            	 boolean isAdded;
			    	if(result!= null && result.size()!=0){
			    		String user = uInternalMarkSupplementaryForm.getUserId();
			    		isAdded = UploadExamInternalMarkSupplementaryHandler.getInstance().addUploadedData(result, user);
			    	if(isAdded){
			    		//if adding is success
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_SUPPLEMENTARY_SUCCESS);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    	}else{
			    		//if adding is failure
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_SUPPLEMENTARY_FAILURE);
			    		errors.add(CMSConstants.ERROR, message);
			    		addErrors(request, errors);
			    		}
		    		}if(internalMarkNotExistList.size() >0){
	 		    		writeToFile(internalMarkNotExistList,request);
	 		    		request.setAttribute("fileType","Excel");
	 		    		request.setAttribute("fileName", "ExamInternalMarkSupplementaryExcel");
	 		    	}
			    }else if(fileName.endsWith(".xls")){
				    	 byte[] fileData = myFile.getFileData();
				    
				    	 String source1=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL_SUPPLEMENTARY);
							
						File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);
					InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
					OutputStream out = new FileOutputStream(file1);
					byte buffer[] = new byte[1024];
					int len;
					while ((len = inputStream.read(buffer)) > 0){
						out.write(buffer, 0, len);
					}
					out.close();
					inputStream.close();
					String source=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL_SUPPLEMENTARY);
					
					file = new File(request.getRealPath("")+ "//TempFiles//"+source);
				
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
			    for(int r = 1; r < rows; r++) {
			        row = sheet.getRow(r);
			        String year="";
			        if(row != null) {
			            for(int c = 0; c < cols;c++) {
			                cell = row.getCell((byte)c);
			                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
			                	if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString())){
			                		uploadExamInternalMarkSupplementaryNotExistTO = new UploadExamInternalMarkSupplementaryTO();
			                		uploadExamInternalMarkSupplementaryTO = new UploadExamInternalMarkSupplementaryTO();
			                		examName=cell.toString().trim();
			                		if(examName.endsWith(".0"))
			                		{	
			                			examName=StringUtils.chop(examName);
			                			examName=StringUtils.chop(examName);
			                		}
			                		if(examMap!=null && examMap.containsKey(examName)){
			                			uploadExamInternalMarkSupplementaryTO.setExamId(String.valueOf(examMap.get(examName)));
			                		}else{
			                			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
			                		}
								} 
			                	if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString()))
			                	{	
			                		regNo=cell.toString().trim();
			                		if(regNo.startsWith("9"))
 			                			regNo="0"+regNo;
			                		if(regNo.endsWith(".0"))
			                		{
			                			regNo=StringUtils.chop(regNo);
			                			regNo=StringUtils.chop(regNo);
			                		}
		                			if(studnetMap!=null && studnetMap.containsKey(regNo)){
		                				uploadExamInternalMarkSupplementaryTO.setStudentId(String.valueOf(studnetMap.get(regNo)));
			                		}else{
			                			uploadExamInternalMarkSupplementaryNotExistTO.setRegNo(regNo);
			                			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
			                		}
								}
			                	if(cell.getCellNum() ==3 && !StringUtils.isEmpty(cell.toString())){
			                		String semesterNo=cell.toString().trim();
			                		if(semesterNo.endsWith(".0"))
			                		{
			                			semesterNo=StringUtils.chop(semesterNo);
			                			semesterNo=StringUtils.chop(semesterNo);
			                			uploadExamInternalMarkSupplementaryTO.setSemesterNo(semesterNo);
			                		}else
			                		uploadExamInternalMarkSupplementaryTO.setSemesterNo(semesterNo);
			                	}
			                	if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString())){
			                		year=cell.toString().trim();
			                		year=year.substring(0, 4);
			                	}
			                	if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString())){
			                		className=cell.toString().trim();
			                		if(classMap!=null && classMap.containsKey(className+"_"+year)){
			                			uploadExamInternalMarkSupplementaryTO.setClassId(String.valueOf(classMap.get(className+"_"+year)));
			                		}else{
			                			uploadExamInternalMarkSupplementaryNotExistTO.setClassName(className);
			                			uploadExamInternalMarkSupplementaryNotExistTO.setRegNo(regNo);
			                			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
			                		}
								} 
			                	if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString())){
			                		if(subjectMap!=null && subjectMap.containsKey(cell.toString().trim())){
			                			uploadExamInternalMarkSupplementaryTO.setSubjectId(String.valueOf(subjectMap.get(cell.toString().trim())));
			                		}else{
			                			uploadExamInternalMarkSupplementaryNotExistTO.setSubjectCode(cell.toString().trim());
			                			uploadExamInternalMarkSupplementaryNotExistTO.setRegNo(regNo);
			                			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
			                			uploadExamInternalMarkSupplementaryNotExistTO.setClassName(className);
			                			uploadExamInternalMarkSupplementaryNotExistTO.setYear(year);
			                		}
								} 
			                	if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString())){
			                		String theorySubInternalMark=cell.toString().trim();
			                		if(theorySubInternalMark.endsWith(".0"))
			                		{
			                			theorySubInternalMark=StringUtils.chop(theorySubInternalMark);
			                			theorySubInternalMark=StringUtils.chop(theorySubInternalMark);
			                			uploadExamInternalMarkSupplementaryTO.setTheoryTotalSubInternalMarks(theorySubInternalMark);
			                		}else
			                		uploadExamInternalMarkSupplementaryTO.setTheoryTotalSubInternalMarks(theorySubInternalMark);
			                	}
			                	if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString())){
			                		String pSubInternalMark=cell.toString().trim();
			                		if(pSubInternalMark.endsWith(".0"))
			                		{
			                			pSubInternalMark=StringUtils.chop(pSubInternalMark);
			                			pSubInternalMark=StringUtils.chop(pSubInternalMark);
			                			uploadExamInternalMarkSupplementaryTO.setPracticalTotalSubInternalMarks(pSubInternalMark);
			                		}else
			                		uploadExamInternalMarkSupplementaryTO.setPracticalTotalSubInternalMarks(pSubInternalMark);
			                	}
			                	if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell.toString())){
			                		String tAttendanceMark=cell.toString().trim();
			                		if(tAttendanceMark.endsWith(".0"))
			                		{
			                			tAttendanceMark=StringUtils.chop(tAttendanceMark);
			                			tAttendanceMark=StringUtils.chop(tAttendanceMark);
			                			uploadExamInternalMarkSupplementaryTO.setTheoryTotalAttendenceMarks(tAttendanceMark);
			                		}else
			                		uploadExamInternalMarkSupplementaryTO.setTheoryTotalAttendenceMarks(tAttendanceMark);
			                	}
			                	if(cell.getCellNum() == 10 && !StringUtils.isEmpty(cell.toString())){
			                		String pAttendanceMark=cell.toString().trim();
			                		if(pAttendanceMark.endsWith(".0"))
			                		{
			                			pAttendanceMark=StringUtils.chop(pAttendanceMark);
			                			pAttendanceMark=StringUtils.chop(pAttendanceMark);
			                			uploadExamInternalMarkSupplementaryTO.setPracticalTotalAttendenceMarks(pAttendanceMark);
			                		}else
			                		uploadExamInternalMarkSupplementaryTO.setPracticalTotalAttendenceMarks(pAttendanceMark);
			                	}
			                	if(cell.getCellNum() == 11 && !StringUtils.isEmpty(cell.toString())){
			                		uploadExamInternalMarkSupplementaryTO.setMarksType(cell.toString().trim());
			                	}
			                	if(cell.getCellNum() == 12 && !StringUtils.isEmpty(cell.toString())){
			                		if(cell.toString().trim().equalsIgnoreCase("pass"))
			                			uploadExamInternalMarkSupplementaryTO.setPass(true);
			                		else
			                			uploadExamInternalMarkSupplementaryTO.setPass(false);
			                	}
			                }
			            }
			            if(uploadExamInternalMarkSupplementaryTO !=null && uploadExamInternalMarkSupplementaryTO.getClassId()!=null 
			            		&& uploadExamInternalMarkSupplementaryTO.getStudentId()!=null && uploadExamInternalMarkSupplementaryTO.getExamId()!=null 
			            		&& uploadExamInternalMarkSupplementaryTO.getSubjectId()!=null){
			            	String checkDuplicate=uploadExamInternalMarkSupplementaryTO.getStudentId()+"_"+uploadExamInternalMarkSupplementaryTO.getExamId()+"_"+uploadExamInternalMarkSupplementaryTO.getClassId()+"_"+uploadExamInternalMarkSupplementaryTO.getSubjectId();
	 		            	if(!duplicateList.contains(checkDuplicate))
	 		            	{	
	 		            		result.add(uploadExamInternalMarkSupplementaryTO);
	 		            		duplicateList.add(checkDuplicate);
	 		            		uploadExamInternalMarkSupplementaryTO=null;
	 		            	}

			        	}else{
			        		if(uploadExamInternalMarkSupplementaryNotExistTO!=null){
			        			if(uploadExamInternalMarkSupplementaryNotExistTO.getRegNo()!=null){
			        				internalMarkNotExistList.add(uploadExamInternalMarkSupplementaryNotExistTO);
			        				uploadExamInternalMarkSupplementaryNotExistTO=null;
			        			}
			        		}
			        	}
			    	}
			    }
			    	if(result!= null && result.size()!=0){
			    		String user = uInternalMarkSupplementaryForm.getUserId();
			    		isAdded = UploadExamInternalMarkSupplementaryHandler.getInstance().addUploadedData(result, user);
				    	if(isAdded){
				    		//if adding is success
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_SUPPLEMENTARY_SUCCESS);
				    		messages.add("messages", message);
				    		saveMessages(request, messages);
				    	}else{
				    		//if adding is failure
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_SUPPLEMENTARY_FAILURE);
				    		errors.add(CMSConstants.ERROR, message);
				    		addErrors(request, errors);
				    		}
			    		}if(internalMarkNotExistList.size() >0){
		 		    		writeToFile(internalMarkNotExistList,request);
		 		    		request.setAttribute("fileType","Excel");
		 		    		request.setAttribute("fileName", "ExamInternalMarkSupplementaryExcel");
		 		    	}
			      	}else if(fileName.endsWith(".csv")){         //if the uploaded document is csv file.
			     	  
			     	byte[] fileData    = myFile.getFileData();
				    String source1=prop.getProperty(CMSConstants.UPLOAD_INTERNAL_SUPPLEMENTARY_CSV);
					
					File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);
				    	 
					InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
					OutputStream out = new FileOutputStream(file1);
					byte buffer[] = new byte[1024];
					int len;
					while ((len = inputStream.read(buffer)) > 0){
						out.write(buffer, 0, len);
					}
					out.close();
					inputStream.close();
					String source=prop.getProperty(CMSConstants.UPLOAD_INTERNAL_SUPPLEMENTARY_CSV);
					
					file = new File(request.getRealPath("")+ "//TempFiles//"+source);
					FileInputStream stream1 = new FileInputStream(file);
					LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(stream1));
					while(parser.getLine()!=null){
						  uploadExamInternalMarkSupplementaryTO = new UploadExamInternalMarkSupplementaryTO();
						  uploadExamInternalMarkSupplementaryNotExistTO = new UploadExamInternalMarkSupplementaryTO();
						if(parser.getValueByLabel("ExamName") != null && !StringUtils.isEmpty(parser.getValueByLabel("ExamName"))){
			      		if(examMap!=null && examMap.containsKey(parser.getValueByLabel("ExamName"))){
			      			examName=parser.getValueByLabel("ExamName");
			      			uploadExamInternalMarkSupplementaryTO.setExamId(String.valueOf(examMap.get(examName)));
			      		}else{
			      			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
			      		}
						}
						if(parser.getValueByLabel("RegisterNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("RegisterNumber"))){
							regNo=parser.getValueByLabel("RegisterNumber");
				      		if(studnetMap!=null && studnetMap.containsKey(regNo)){
				      			uploadExamInternalMarkSupplementaryTO.setStudentId(String.valueOf(studnetMap.get(regNo)));
				      		}else{
				      			uploadExamInternalMarkSupplementaryNotExistTO.setRegNo(regNo);
				      			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
				      		}
						}
						if(parser.getValueByLabel("Semester") != null && !StringUtils.isEmpty(parser.getValueByLabel("Semester"))){
							uploadExamInternalMarkSupplementaryTO.setSemesterNo(parser.getValueByLabel("Semester"));
						}
						String year="";
						if(parser.getValueByLabel("AcademicYear") != null && !StringUtils.isEmpty(parser.getValueByLabel("AcademicYear")) && parser.getValueByLabel("Class") != null && !StringUtils.isEmpty(parser.getValueByLabel("Class"))){
							year=parser.getValueByLabel("AcademicYear").trim();
	                		year=year.substring(0, 4);
	                		className=parser.getValueByLabel("Class");
				      		if(classMap!=null && classMap.containsKey(className+"_"+year)){
				      			uploadExamInternalMarkSupplementaryTO.setClassId(String.valueOf(classMap.get(className+"_"+year)));
				      		}else{
				      			uploadExamInternalMarkSupplementaryNotExistTO.setClassName(className);
				      			uploadExamInternalMarkSupplementaryNotExistTO.setRegNo(regNo);
				      			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
				      			uploadExamInternalMarkSupplementaryNotExistTO.setYear(year);
				      		}
						}
						if(parser.getValueByLabel("SubjectCode") != null && !StringUtils.isEmpty(parser.getValueByLabel("SubjectCode"))){
				      		if(subjectMap!=null && subjectMap.containsKey(parser.getValueByLabel("SubjectCode"))){
				      			uploadExamInternalMarkSupplementaryTO.setSubjectId(String.valueOf(subjectMap.get(parser.getValueByLabel("SubjectCode"))));
				      		}else{
				      			uploadExamInternalMarkSupplementaryNotExistTO.setSubjectCode(parser.getValueByLabel("SubjectCode"));
				      			uploadExamInternalMarkSupplementaryNotExistTO.setClassName(className);
				      			uploadExamInternalMarkSupplementaryNotExistTO.setRegNo(regNo);
				      			uploadExamInternalMarkSupplementaryNotExistTO.setExamName(examName);
				      			uploadExamInternalMarkSupplementaryNotExistTO.setYear(year);
				      		}
						}
						if(parser.getValueByLabel("SubInternal-Theorymark") != null && !StringUtils.isEmpty(parser.getValueByLabel("SubInternal-Theorymark"))){
							uploadExamInternalMarkSupplementaryTO.setTheoryTotalSubInternalMarks(parser.getValueByLabel("SubInternal-Theorymark"));
						}
						if(parser.getValueByLabel("SubInternal-PracticalMark") != null && !StringUtils.isEmpty(parser.getValueByLabel("SubInternal-PracticalMark"))){
							uploadExamInternalMarkSupplementaryTO.setPracticalTotalSubInternalMarks(parser.getValueByLabel("SubInternal-PracticalMark"));
						}
						if(parser.getValueByLabel("Attendance-Theorymark") != null && !StringUtils.isEmpty(parser.getValueByLabel("Attendance-Theorymark"))){
							uploadExamInternalMarkSupplementaryTO.setTheoryTotalAttendenceMarks(parser.getValueByLabel("Attendance-Theorymark"));
						}
						if(parser.getValueByLabel("Attendance-PracticalMark") != null && !StringUtils.isEmpty(parser.getValueByLabel("Attendance-PracticalMark"))){
							uploadExamInternalMarkSupplementaryTO.setPracticalTotalAttendenceMarks(parser.getValueByLabel("Attendance-PracticalMark"));
						}
						if(parser.getValueByLabel("MarkType") != null && !StringUtils.isEmpty(parser.getValueByLabel("MarkType"))){
							uploadExamInternalMarkSupplementaryTO.setMarksType(parser.getValueByLabel("MarkType"));
						}
						
						if(parser.getValueByLabel("PassFail") != null && !StringUtils.isEmpty(parser.getValueByLabel("PassFail"))){
							if(parser.getValueByLabel("PassFail").trim().equals("pass"))
								uploadExamInternalMarkSupplementaryTO.setPass(true);
		            		else
		            			uploadExamInternalMarkSupplementaryTO.setPass(false);
						}
						 if(uploadExamInternalMarkSupplementaryTO !=null && uploadExamInternalMarkSupplementaryTO.getClassId()!=null 
				            		&& uploadExamInternalMarkSupplementaryTO.getStudentId()!=null && uploadExamInternalMarkSupplementaryTO.getExamId()!=null 
				            		&& uploadExamInternalMarkSupplementaryTO.getSubjectId()!=null){
				            	String checkDuplicate=uploadExamInternalMarkSupplementaryTO.getStudentId()+"_"+uploadExamInternalMarkSupplementaryTO.getExamId()+"_"+uploadExamInternalMarkSupplementaryTO.getClassId()+"_"+uploadExamInternalMarkSupplementaryTO.getSubjectId();
		 		            	if(!duplicateList.contains(checkDuplicate))
		 		            	{	
		 		            		result.add(uploadExamInternalMarkSupplementaryTO);
		 		            		duplicateList.add(checkDuplicate);
		 		            		uploadExamInternalMarkSupplementaryTO=null;
		 		            	}

				            }else{
				        		if(uploadExamInternalMarkSupplementaryNotExistTO!=null){
				        			if(uploadExamInternalMarkSupplementaryNotExistTO.getRegNo()!=null){
				        				internalMarkNotExistList.add(uploadExamInternalMarkSupplementaryNotExistTO);
				        				uploadExamInternalMarkSupplementaryNotExistTO=null;
				        			}
				        		}
				        	}
			      	}
					boolean isAdded = false;
						if(result!= null){
				    		String user = uInternalMarkSupplementaryForm.getUserId();
				    		isAdded = UploadExamInternalMarkSupplementaryHandler.getInstance().addUploadedData(result, user);
				    	if(isAdded){
				    		//if adding is success.
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_SUPPLEMENTARY_SUCCESS);
				    		messages.add("messages", message);
				    		saveMessages(request, messages);
				    	}else{
				    		//if adding is failure.
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_SUPPLEMENTARY_FAILURE);
				    		errors.add(CMSConstants.ERROR, message);
				    		addErrors(request, errors);
				    	}
				    	}if(internalMarkNotExistList.size() >0){
		 		    		writeToFile(internalMarkNotExistList,request);
		 		    		request.setAttribute("fileType","Excel");
		 		    		request.setAttribute("fileName", "ExamInternalMarkSupplementaryExcel");
		 		    	}
				      	}else{		//if the uploaded document is not excel/csv file.
				      		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_DOCUMENT);
				    		errors.add(CMSConstants.ERROR,message);
				    		saveErrors(request, errors);
					    }
						} catch (Exception exception) {
							String msg = super.handleApplicationException(exception);
							uInternalMarkSupplementaryForm.setErrorMessage(msg);
							uInternalMarkSupplementaryForm.setErrorStack(exception.getMessage());
							return mapping.findForward(CMSConstants.ERROR_PAGE);
						}
					} else {
						addErrors(request, errors);
						log.info("Exit  saveInternalMarkSupplementary  ");
						return mapping.findForward(CMSConstants.UPLOAD_INTERNAL_SUPPLEMENTARY_INFO);
					}
					log.info("Exit saveInternalMarkSupplementary - getCandidates");
					return mapping.findForward(CMSConstants.UPLOAD_EXAM_LINKS_PAGE);
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
		
		public void writeToFile(List<UploadExamInternalMarkSupplementaryTO> internalMarkNotExistList,HttpServletRequest request) throws Exception {
			 
			 Properties prop = new Properties();
				try {
					InputStream inputStream = CommonUtil.class.getClassLoader()
							.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
					prop.load(inputStream);
				} 
				catch (IOException e) {
					log.error("Error occured at  ExportAddlBiodataInfoAction ",e);
					throw new IOException(e);
				}
				String fileName=prop.getProperty(CMSConstants.UPLOAD_SUPPLEMENTARY_INFO_EXCEL_DESTINATION);
				File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);	
				if(excelFile.exists()){
					excelFile.delete();
				}
				FileOutputStream fos = null;
				HSSFWorkbook wb = null;
				HSSFSheet sheet =null;
				HSSFRow row = null;
				HSSFCell cell = null;
				int count = 0;
				try	{
					wb = new HSSFWorkbook();
					HSSFCellStyle cellStyle = wb.createCellStyle();
					cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
					sheet = wb.createSheet("Supplementary Details");
					count = sheet.getFirstRowNum();
					// Create cells in the row and put some data in it.
					Iterator<UploadExamInternalMarkSupplementaryTO> stuNotExistList = internalMarkNotExistList.iterator();
				      while (stuNotExistList.hasNext()) {
				    	  row = sheet.createRow(count);
				    	  UploadExamInternalMarkSupplementaryTO studentNotExistTO = (UploadExamInternalMarkSupplementaryTO) stuNotExistList.next();
						row.createCell((short)0).setCellValue(studentNotExistTO.getExamName());
						row.createCell((short)1).setCellValue(studentNotExistTO.getRegNo());
						row.createCell((short)2).setCellValue(studentNotExistTO.getYear());
						row.createCell((short)3).setCellValue(studentNotExistTO.getClassName());
						row.createCell((short)4).setCellValue(studentNotExistTO.getSubjectCode());
						count++;
				      }
					fos = new FileOutputStream(excelFile,true);
					wb.write(fos);
					HttpSession session = request.getSession(false);
					if(session!=null)
					session.setAttribute(CMSConstants.EXCEL_BYTES, wb.getBytes());
					fos.flush();
					fos.close();
				}catch(Exception e){
					//log
				}
			  }
}
