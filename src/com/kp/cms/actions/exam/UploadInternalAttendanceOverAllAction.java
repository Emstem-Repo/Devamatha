
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
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
import com.kp.cms.actions.exam.UploadExamStudentFinalMarksAction.ByteArrayStreamInfo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.UploadInternalOverAllForm;
import com.kp.cms.handlers.exam.UploadExamStudentFinalMarksHandler;
import com.kp.cms.handlers.exam.UploadInternalOverAllHandler;
import com.kp.cms.to.exam.UploadInternalOverAllTo;
import com.kp.cms.utilities.CommonUtil;

public class UploadInternalAttendanceOverAllAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(UploadInternalAttendanceOverAllAction.class);
	
	/**
	 * Method to set the required data to the form to display it in uploadInternalOverAll.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadInternalOverAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initUploadInternalOverAll input");
		UploadInternalOverAllForm uploadInternalOverAllForm = (UploadInternalOverAllForm) form;
		uploadInternalOverAllForm.resetFields();
		log.info("Exit initUploadInternalOverAll input");
		
		return mapping.findForward(CMSConstants.UPLOAD_INTERNAL_OVER_ALL_ATTENDANCE);
	}
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateInternalOverAllMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		UploadInternalOverAllForm uploadInternalOverAllForm = (UploadInternalOverAllForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = uploadInternalOverAllForm.validate(mapping, request);
		setUserId(request, uploadInternalOverAllForm);
		if (errors.isEmpty()) {
		
			try {
				Map<String,Integer> studnetMap=UploadInternalOverAllHandler.getInstance().getStudentMap();
				Map<String,Integer> classMap=UploadInternalOverAllHandler.getInstance().getClassMap();
				Map<String,Integer> subjectMap=UploadInternalOverAllHandler.getInstance().getSubjectCodeMap();
				Map<String,Integer> examMap=UploadInternalOverAllHandler.getInstance().getExamMap();
				
				// reading xlsx code from here
				FormFile myFile = uploadInternalOverAllForm.getTheFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
				File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<UploadInternalOverAllTo> result=new ArrayList<UploadInternalOverAllTo>();
			    List<String>duplicateList=new ArrayList<String>();
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
					
					XSSFWorkbook work=new XSSFWorkbook(new FileInputStream(request.getRealPath("")+ "//TempFiles//"+source));
	            	XSSFSheet sh=work.getSheetAt(0);
	            	Iterator rows = sh.rowIterator();
	            	 while (rows.hasNext()) {
	                 	XSSFRow row = (XSSFRow) rows.next();
	                     Iterator cells = row.cellIterator();
	                     UploadInternalOverAllTo uploadInternalOverAllTo=null;
	                     String year="";
	                     while (cells.hasNext()) {
	                         XSSFCell cell = (XSSFCell) cells.next();
	                         
	                         if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	 		                	if(cell.getColumnIndex() == 1 && !StringUtils.isEmpty(cell.toString())){
	 		                		uploadInternalOverAllTo = new UploadInternalOverAllTo();
	 		                		String examName=cell.toString().trim();
	 		                		if(examName.endsWith(".0"))
	 		                		{
	 		                			examName=StringUtils.chop(examName);
	 		                			examName=StringUtils.chop(examName);
	 		                		}
	 		                		if(examMap!=null && examMap.containsKey(examName)){
	 		                			uploadInternalOverAllTo.setExamId(String.valueOf(examMap.get(examName)));
	 		                		}
	 							} 
	 		                	if(cell.getColumnIndex() == 2 && !StringUtils.isEmpty(cell.toString()))
	 		                	{	
	 		                		if(cell.toString().trim().length()>0)
	 		                		{	
	 			                		String regNo=cell.toString().trim();
	 			                		if(regNo.startsWith("9"))
	 			                			regNo="0"+regNo;
	 			                		if(regNo.endsWith(".0"))
	 			                		{
	 			                			regNo=StringUtils.chop(regNo);
	 			                			regNo=StringUtils.chop(regNo);
	 			                		}
	 		                			if(studnetMap!=null && studnetMap.containsKey(regNo)){
	 			                			uploadInternalOverAllTo.setStudentId(String.valueOf(studnetMap.get(regNo)));
	 			                		}
	 		                		}
	 							}
	 		                	if(cell.getColumnIndex() ==3 && !StringUtils.isEmpty(cell.toString())){
	 		                		uploadInternalOverAllTo.setSemesterNo(cell.toString().trim());
	 		                	}
	 		                	if(cell.getColumnIndex() == 4 && !StringUtils.isEmpty(cell.toString())){
	 		                		year=cell.toString().trim();
	 		                		year=year.substring(0, 4);
	 		                	}
	 		                	if(cell.getColumnIndex() == 5 && !StringUtils.isEmpty(cell.toString())){
	 		                		if(classMap!=null && classMap.containsKey(cell.toString().trim()+"_"+year)){
	 		                			uploadInternalOverAllTo.setClassId(String.valueOf(classMap.get(cell.toString().trim()+"_"+year)));
	 		                		}
	 							} 
	 		                	if(cell.getColumnIndex() == 6 && !StringUtils.isEmpty(cell.toString())){
	 		                		if(subjectMap!=null && subjectMap.containsKey(cell.toString().trim())){
	 		                			uploadInternalOverAllTo.setSubjectId(String.valueOf(subjectMap.get(cell.toString().trim())));
	 		                		}
	 							} 
	 		                	if(cell.getColumnIndex() == 7)
	 		                	{
	 		                		if(!StringUtils.isEmpty(cell.toString()) && CommonUtil.isValidDecimal(cell.toString()))
	 		                		{
	 		                			uploadInternalOverAllTo.setTheoryAttendance(cell.toString().trim());
	 		                		}
	 		                		else
	 		                		{
	 		                			if(uploadInternalOverAllTo!=null)
	 		                				uploadInternalOverAllTo.setTheoryAttendance("0");
	 		                		}
	 		                	}	
	 		                	if(cell.getColumnIndex() == 8)
	 		                	{	
	 		                		if(!StringUtils.isEmpty(cell.toString()) && CommonUtil.isValidDecimal(cell.toString()))
	 		                		{
	 		                			uploadInternalOverAllTo.setPracticalAttendance(cell.toString().trim());
	 		                		}
	 		                		else
	 		                		{	
	 		                			if(uploadInternalOverAllTo!=null)
	 		                				uploadInternalOverAllTo.setPracticalAttendance("0");
	 		                		}
	 		                	}	
	 		                	if(cell.getColumnIndex() == 9 && !StringUtils.isEmpty(cell.toString())){
	 		                		uploadInternalOverAllTo.setMarksType(cell.toString().trim());
	 		                	}
	 		                	if(cell.getColumnIndex() == 10 && !StringUtils.isEmpty(cell.toString())){
	 		                		if(cell.toString().trim().equalsIgnoreCase("pass"))
	 		                			uploadInternalOverAllTo.setPass(true);
	 		                		else
	 		                			uploadInternalOverAllTo.setPass(false);
	 		                	}
	 		                }
	 		            }
	 		            if(uploadInternalOverAllTo !=null 
	 		            		&& uploadInternalOverAllTo.getClassId()!=null 
	 		            		&& uploadInternalOverAllTo.getStudentId()!=null 
	 		            		&& uploadInternalOverAllTo.getExamId()!=null 
	 		            		&& uploadInternalOverAllTo.getSubjectId()!=null)
	 		            {
	 		            	String checkDuplicate=uploadInternalOverAllTo.getStudentId()+"_"+uploadInternalOverAllTo.getExamId()+"_"+uploadInternalOverAllTo.getClassId()+"_"+uploadInternalOverAllTo.getSubjectId();
	 		            	if(!duplicateList.contains(checkDuplicate))
	 		            	{	
	 		            		result.add(uploadInternalOverAllTo);
	 		            		duplicateList.add(checkDuplicate);
	 		            	}
	 		            }
	               }
	            	 if(result!= null && result.size()!=0){
				    		String user = uploadInternalOverAllForm.getUserId();
				    		boolean isAdded = UploadInternalOverAllHandler.getInstance().addUploadedData(result, user,"attendance");
				    	if(isAdded){
				    		//if adding is success.
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_OVERALL_ATTENDANCE_SUCCESS);
				    		messages.add("messages", message);
				    		saveMessages(request, messages);
				    	}else{
				    		//if adding is failure.
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_OVERALL_ATTENDANCE_FAILURE);
				    		errors.add(CMSConstants.ERROR, message);
				    		addErrors(request, errors);
				    	}
				    	}  
			    }else if(fileName.endsWith(".xls")){
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
			
			POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
		    HSSFWorkbook workbook = new HSSFWorkbook(system);
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    HSSFRow row;
		    HSSFCell cell;

		    int rows = sheet.getPhysicalNumberOfRows();

		    int cols = 0; // No of columns
		    int tmp = 0;
		    UploadInternalOverAllTo uploadInternalOverAllTo=null;
		    
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
		                		String examName=cell.toString().trim();
 		                		if(examName.endsWith(".0"))
 		                		{
 		                			examName=StringUtils.chop(examName);
 		                			examName=StringUtils.chop(examName);
 		                		}
		                		if(examMap!=null && examMap.containsKey(examName)){
		                			uploadInternalOverAllTo = new UploadInternalOverAllTo();
		                			uploadInternalOverAllTo.setExamId(String.valueOf(examMap.get(examName)));
		                		}else{
		        					break;
		                		}
							} 
		                	if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString())){
		                		
		                		if(cell.toString().trim().length()>0)
		                		{	
			                		String regNo=cell.toString().trim();
			                		if(regNo.startsWith("9"))
 			                			regNo="0"+regNo;
			                		if(regNo.endsWith(".0"))
			                		{
			                			regNo=StringUtils.chop(regNo);
			                			regNo=StringUtils.chop(regNo);
			                		}
		                			if(studnetMap!=null && studnetMap.containsKey(regNo)){
			                			uploadInternalOverAllTo.setStudentId(String.valueOf(studnetMap.get(regNo)));
			                		}else{
			        					break;
			                		}
		                		}
		                		else
		                		{
		                			break;
		                		}
							}
		                	if(cell.getCellNum() ==3 && !StringUtils.isEmpty(cell.toString())){
		                		uploadInternalOverAllTo.setSemesterNo(cell.toString().trim());
		                	}
		                	if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString())){
		                		year=cell.toString().trim();
		                		year=year.substring(0, 4);
		                	}
		                	if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString())){
		                		if(classMap!=null && classMap.containsKey(cell.toString().trim()+"_"+year)){
		                			uploadInternalOverAllTo.setClassId(String.valueOf(classMap.get(cell.toString().trim()+"_"+year)));
		                		}else{
		        					break;
		                		}
							} 
		                	if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString())){
		                		if(subjectMap!=null && subjectMap.containsKey(cell.toString().trim())){
		                			uploadInternalOverAllTo.setSubjectId(String.valueOf(subjectMap.get(cell.toString().trim())));
		                		}else{
		        					break;
		                		}
							} 
		                	if(cell.getCellNum() == 7)
 		                	{
 		                		if(!StringUtils.isEmpty(cell.toString()) && CommonUtil.isValidDecimal(cell.toString()))
 		                		{
 		                			uploadInternalOverAllTo.setTheoryAttendance(cell.toString().trim());
 		                		}
 		                		else
 		                		{
 		                			if(uploadInternalOverAllTo!=null)
 		                				uploadInternalOverAllTo.setTheoryAttendance("0");
 		                		}
 		                	}	
 		                	if(cell.getCellNum() == 8)
 		                	{	
 		                		if(!StringUtils.isEmpty(cell.toString()) && CommonUtil.isValidDecimal(cell.toString()))
 		                		{
 		                			uploadInternalOverAllTo.setPracticalAttendance(cell.toString().trim());
 		                		}
 		                		else
 		                		{	
 		                			if(uploadInternalOverAllTo!=null)
 		                				uploadInternalOverAllTo.setPracticalAttendance("0");
 		                		}
 		                	}	
		                	if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell.toString())){
		                		uploadInternalOverAllTo.setMarksType(cell.toString().trim());
		                	}
		                	if(cell.getCellNum() == 10 && !StringUtils.isEmpty(cell.toString())){
		                		if(cell.toString().trim().equalsIgnoreCase("pass"))
		                			uploadInternalOverAllTo.setPass(true);
		                		else
		                			uploadInternalOverAllTo.setPass(false);
		                	}
		                }
		            }
		            if(uploadInternalOverAllTo !=null 
		            	&& uploadInternalOverAllTo.getClassId()!=null 
		            	&& uploadInternalOverAllTo.getStudentId()!=null 
		            	&& uploadInternalOverAllTo.getExamId()!=null 
		            	&& uploadInternalOverAllTo.getSubjectId()!=null)
		            {
		            	String checkDuplicate=uploadInternalOverAllTo.getStudentId()+"_"+uploadInternalOverAllTo.getExamId()+"_"+uploadInternalOverAllTo.getClassId()+"_"+uploadInternalOverAllTo.getSubjectId();
 		            	if(!duplicateList.contains(checkDuplicate))
 		            	{	
 		            		result.add(uploadInternalOverAllTo);
 		            		duplicateList.add(checkDuplicate);
 		            	}
		            }
		        	}else{
		        		continue;
		        	}
		    	}
		    	if(result!= null && result.size()!=0){
		    		String user = uploadInternalOverAllForm.getUserId();
		    		isAdded = UploadInternalOverAllHandler.getInstance().addUploadedData(result, user,"attendance");
		    	if(isAdded){
		    		//if adding is success
		    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_OVERALL_ATTENDANCE_SUCCESS);
		    		messages.add("messages", message);
		    		saveMessages(request, messages);
		    	}else{
		    		//if adding is failure
		    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_OVERALL_ATTENDANCE_FAILURE);
		    		errors.add(CMSConstants.ERROR, message);
		    		addErrors(request, errors);
		    	}
		    	}
		      	}else if(fileName.endsWith(".csv")){         //if the uploaded document is csv file.
		     	  
		     	byte[] fileData    = myFile.getFileData();
			    String source1=prop.getProperty(CMSConstants.UPLOAD_INTERVIEW_CSV);
				
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
				String source=prop.getProperty(CMSConstants.UPLOAD_INTERVIEW_CSV);
				
				file = new File(request.getRealPath("")+ "//TempFiles//"+source);
				FileInputStream stream1 = new FileInputStream(file);
				LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(stream1));
				
				
				while(parser.getLine()!=null){
					 UploadInternalOverAllTo uploadInternalOverAllTo = new UploadInternalOverAllTo();
					if(parser.getValueByLabel("ExamName") != null && !StringUtils.isEmpty(parser.getValueByLabel("ExamName"))){
		      		if(examMap!=null && examMap.containsKey(parser.getValueByLabel("ExamName"))){
            			uploadInternalOverAllTo.setExamId(String.valueOf(examMap.get(parser.getValueByLabel("ExamName"))));
		      		}else{
		      			continue;
		      		}
					}
					if(parser.getValueByLabel("RegisterNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("RegisterNumber"))){
			      		if(studnetMap!=null && studnetMap.containsKey(parser.getValueByLabel("RegisterNumber"))){
			      			uploadInternalOverAllTo.setStudentId(String.valueOf(studnetMap.get(parser.getValueByLabel("RegisterNumber"))));
			      		}else{
			      			continue;
			      		}
					}
					if(parser.getValueByLabel("Semester") != null && !StringUtils.isEmpty(parser.getValueByLabel("Semester"))){
			      			uploadInternalOverAllTo.setSemesterNo(parser.getValueByLabel("Semester"));
					}
					String year="";
					if(parser.getValueByLabel("AcademicYear") != null && !StringUtils.isEmpty(parser.getValueByLabel("AcademicYear")) && parser.getValueByLabel("Class") != null && !StringUtils.isEmpty(parser.getValueByLabel("Class"))){
						year=parser.getValueByLabel("AcademicYear").trim();
                		year=year.substring(0, 4);
			      		if(classMap!=null && classMap.containsKey(parser.getValueByLabel("Class")+"_"+year)){
			      			uploadInternalOverAllTo.setClassId(String.valueOf(classMap.get(parser.getValueByLabel("Class")+"_"+year)));
			      		}else{
			      			continue;
			      		}
					}
					if(parser.getValueByLabel("SubjectCode") != null && !StringUtils.isEmpty(parser.getValueByLabel("SubjectCode"))){
			      		if(subjectMap!=null && subjectMap.containsKey(parser.getValueByLabel("SubjectCode"))){
			      			uploadInternalOverAllTo.setSubjectId(String.valueOf(subjectMap.get(parser.getValueByLabel("SubjectCode"))));
			      		}else{
			      			continue;
			      		}
					}
					if(parser.getValueByLabel("Theorymark") != null && !StringUtils.isEmpty(parser.getValueByLabel("Theorymark"))){
		      			uploadInternalOverAllTo.setTheoryAttendance(parser.getValueByLabel("Theorymark"));
					}
					if(parser.getValueByLabel("PracticalMark") != null && !StringUtils.isEmpty(parser.getValueByLabel("PracticalMark"))){
		      			uploadInternalOverAllTo.setPracticalAttendance(parser.getValueByLabel("PracticalMark"));
					}
					if(parser.getValueByLabel("MarkType") != null && !StringUtils.isEmpty(parser.getValueByLabel("MarkType"))){
		      			uploadInternalOverAllTo.setMarksType(parser.getValueByLabel("MarkType"));
					}
					
					if(parser.getValueByLabel("PassFail") != null && !StringUtils.isEmpty(parser.getValueByLabel("PassFail"))){
						if(parser.getValueByLabel("PassFail").trim().equals("pass"))
	            			uploadInternalOverAllTo.setPass(true);
	            		else
	            			uploadInternalOverAllTo.setPass(false);
					}
					 if(uploadInternalOverAllTo !=null 
						&& uploadInternalOverAllTo.getClassId()!=null 
			            && uploadInternalOverAllTo.getStudentId()!=null 
			            && uploadInternalOverAllTo.getExamId()!=null 
			            && uploadInternalOverAllTo.getSubjectId()!=null)
					 {
						 String checkDuplicate=uploadInternalOverAllTo.getStudentId()+"_"+uploadInternalOverAllTo.getExamId()+"_"+uploadInternalOverAllTo.getClassId()+"_"+uploadInternalOverAllTo.getSubjectId();
	 		            	if(!duplicateList.contains(checkDuplicate))
	 		            	{	
	 		            		result.add(uploadInternalOverAllTo);
	 		            		duplicateList.add(checkDuplicate);
	 		            	}
			         }
		      	}
				boolean isAdded = false;
					if(result!= null && result.size()!=0){
			    		String user = uploadInternalOverAllForm.getUserId();
			    		isAdded = UploadInternalOverAllHandler.getInstance().addUploadedData(result, user,"attendance");
			    	if(isAdded){
			    		//if adding is success.
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_OVERALL_ATTENDANCE_SUCCESS);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    	}else{
			    		//if adding is failure.
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_INTENAL_OVERALL_ATTENDANCE_FAILURE);
			    		errors.add(CMSConstants.ERROR, message);
			    		addErrors(request, errors);
			    	}
			    	}
			      	}else{		//if the uploaded document is not excel/csv file.
			      		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_DOCUMENT);
			    		errors.add(CMSConstants.ERROR,message);
			    		saveErrors(request, errors);
				    }
					} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				uploadInternalOverAllForm.setErrorMessage(msg);
				uploadInternalOverAllForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.UPLOAD_INTERNAL_OVER_ALL_ATTENDANCE);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.UPLOAD_INTERNAL_OVER_ALL_ATTENDANCE);
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
