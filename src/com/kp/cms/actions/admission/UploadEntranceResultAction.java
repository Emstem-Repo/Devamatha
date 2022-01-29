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
import java.util.HashSet;
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

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.InterviewResultEntryForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.UploadInterviewResultHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.GradeTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.to.admission.InterviewStatusTO;

public class UploadEntranceResultAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UploadInterviewResultAction.class);
	
	/**
	 * This method is called when u click on the link.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initEntranceResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm)form;
		log.info("call of initInterviewResult method in UploadInterviewResultAction class.");
		try {
			if(ProgramTypeHandler.getInstance().getProgramType() != null){
				interviewResultEntryForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
			}
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			interviewResultEntryForm.setErrorMessage(msgKey);
			interviewResultEntryForm.setErrorStack(businessException.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewResultEntryForm.setErrorMessage(msg);
			interviewResultEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		resetFields(interviewResultEntryForm);
		log.info("end of initInterviewResult method in UploadInterviewResultAction class.");
		return mapping.findForward(CMSConstants.UPLOAD_ENTRANCE_RESULT);
	}
	
	/**
	 * Method to reset the form fields.
	 * @param interviewResultEntryForm
	 */
	
	public void resetFields(InterviewResultEntryForm interviewResultEntryForm) {
		log.info("call of resetFields method in UploadInterviewResultAction class.");
		interviewResultEntryForm.setGradeObtainedId(null);
		interviewResultEntryForm.setComments(null);
		interviewResultEntryForm.setInterviewTypeId(null);
		interviewResultEntryForm.setInterviewStatusId(null);
		interviewResultEntryForm.setReferredById(null);
		interviewResultEntryForm.setApplicationNumber(null);
		interviewResultEntryForm.setApplicationYear(null);
		interviewResultEntryForm.setProgramTypeId(null);
		interviewResultEntryForm.setProgramId(null);
		interviewResultEntryForm.setCourseId(null);
		interviewResultEntryForm.setSubroundCount(null);
		interviewResultEntryForm.setInterviewSubroundId(null); 
		interviewResultEntryForm.setCourses(null);
		log.info("end of resetFields method in UploadInterviewResultAction class.");
	}
	
	/**
	 * This method is used to save the upload document.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward uploadEntranceResultEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm)form;
		log.info("call of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
		 ActionErrors errors = interviewResultEntryForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request,interviewResultEntryForm);
			if(!StringUtils.isEmpty(interviewResultEntryForm.getSubroundCount()) && Integer.parseInt(interviewResultEntryForm.getSubroundCount()) != 0){
				if(interviewResultEntryForm.getInterviewSubroundId() !=null && interviewResultEntryForm.getInterviewSubroundId().length()== 0) {
					errors.add("error", new ActionError(CMSConstants.UPLOAD_INTERVIEW_SUBROUND));
				}	
			}
			if(interviewResultEntryForm.getCourses() == null || interviewResultEntryForm.getCourses().length ==0){
				errors.add("error", new ActionError("admissionFormForm.courseId.required"));
			}
			if(interviewResultEntryForm.getCourses() != null && interviewResultEntryForm.getCourses().length !=0){
				validatesubroundsData(interviewResultEntryForm,errors);
			}
			 if (errors.isEmpty()) {
				int applicationYear = Integer.parseInt(interviewResultEntryForm.getApplicationYear());
//				int courseId = Integer.parseInt(interviewResultEntryForm.getCourseId());
				String[] courseIds = interviewResultEntryForm.getCourses();
//				int interviewTypeId = Integer.parseInt(interviewResultEntryForm.getInterviewTypeId());
				int interviewSubRoundId = 0;
				if(interviewResultEntryForm.getInterviewSubroundId()!=null && !interviewResultEntryForm.getInterviewSubroundId().isEmpty()){
					interviewSubRoundId = Integer.parseInt(interviewResultEntryForm.getInterviewSubroundId());
				}
			FormFile myFile = interviewResultEntryForm.getThefile();
		    String contentType = myFile.getContentType();
		    	 
		   String fileName    = myFile.getFileName();
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = UploadEntranceResultAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		     //getting adm_appln, grade, interview status details.
		     Map<Integer,Integer> admMap = UploadInterviewResultHandler.getInstance().getAdmAppDetailsForSelectedCourses(applicationYear,courseIds,interviewResultEntryForm);
		     Map<String,Integer> statusMap = UploadInterviewResultHandler.getInstance().getInterviewStatusDetails();
		     Set<InterviewResultTO> results = null;
		     
		    
			
			
				
		     //if the uploaded document is excel file.
		     if(fileName.endsWith(".xls")){
		    	 byte[] fileData = myFile.getFileData();
		    
		    	 String source1=prop.getProperty(CMSConstants.UPLOAD_INTERVIEW);
		    	 String filePath=request.getRealPath("");
			    	filePath = filePath + "//TempFiles//";	
				File file1 = new File(filePath+source1);
		    	 
			InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
			OutputStream out = new FileOutputStream(file1);
			byte buffer[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, len);
			}
			out.close();
			inputStream.close();
			String source=prop.getProperty(CMSConstants.UPLOAD_INTERVIEW);
			
			file = new File(filePath+source);
		
		POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
	    HSSFWorkbook workbook = new HSSFWorkbook(system);
	    HSSFSheet sheet = workbook.getSheetAt(0);
	    HSSFRow row;
	    HSSFCell cell;

	    int rows = sheet.getPhysicalNumberOfRows();

	    int cols = 0; // No of columns
	    int tmp = 0;
	    InterviewResultTO interviewResultTO = null;
	    AdmApplnTO admApplnTO;
	    InterviewStatusTO interviewStatusTO;
	    InterviewProgramCourseTO interviewProgramCourseTO;
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
	   
	    int interviewTypeId = 0;
		 int mainroundId = 0;
		 int subroundId = 0;
	    
	    StringBuffer duplicateApplnNos=new StringBuffer();
	    results = new HashSet<InterviewResultTO>();
	    int count = 1;
	    for(int r = 1; r < rows; r++) {
	    	//List<GradeTO> gradeList = new ArrayList<GradeTO>();
	        row = sheet.getRow(r);
	        if(row != null) {
	        	interviewResultTO = new InterviewResultTO();
	            for(int c = 0; c < cols;c++) {
	                cell = row.getCell((byte)c);
	                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
						if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString())){
	                		if(admMap!=null && admMap.containsKey((int)Float.parseFloat(cell.toString().trim()))){
	                			admApplnTO = new AdmApplnTO();
	                			admApplnTO.setId((Integer)admMap.get((int)Float.parseFloat(cell.toString().trim())));
	                			interviewResultTO.setAdmApplnTO(admApplnTO);
	                		}else{
	        					break;
	                		}
	                		
	                		if(interviewResultEntryForm.getInterviewTypeMap() != null && !interviewResultEntryForm.getInterviewTypeMap().isEmpty()){
								Map<String, Map<Integer, Integer>> mainMap = interviewResultEntryForm.getInterviewTypeMap();
								Map<Integer, Integer> subMap = mainMap.get(interviewResultEntryForm.getInterviewTypeId());
								Map<Integer, Integer> studentCourseMap = interviewResultEntryForm.getIntPrgCourseMap();
								int courseId = studentCourseMap.get((int)Float.parseFloat(cell.toString().trim()));
								interviewTypeId = subMap.get(courseId);
		                		interviewProgramCourseTO = new InterviewProgramCourseTO();
		                		interviewProgramCourseTO.setId(interviewTypeId);
		           	    	    interviewResultTO.setInterviewProgramCourseTO(interviewProgramCourseTO);
		           	    	    interviewResultTO.setInterviewSubroundId(interviewSubRoundId);
							}
							if( interviewResultEntryForm.getInterviewSubroundId() != null && !interviewResultEntryForm.getInterviewSubroundId().trim().isEmpty()){
								subroundId = Integer.parseInt(interviewResultEntryForm.getInterviewSubroundId());
							}
							if( interviewResultEntryForm.getInterviewTypeId() != null && !interviewResultEntryForm.getInterviewTypeId().trim().isEmpty()){
								mainroundId = interviewTypeId;
							}
					    	 interviewResultTO.setInterviewSubroundId(subroundId);
						} 
						if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString())){
	                   			interviewResultTO.setPercentage(cell.toString().trim());
	                   			++count;
	                   	} if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString())){
	                   			if(statusMap!=null && statusMap.containsKey(cell.toString().trim())){
	                   				interviewStatusTO = new InterviewStatusTO();
	                   				interviewStatusTO.setId((Integer)statusMap.get(cell.toString().trim()));
		                			interviewResultTO.setInterviewStatusTO(interviewStatusTO);
		                		}else{
		                			break;
		               			}
	                   	} if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString())){
	                   		interviewResultTO.setComments(cell.toString());
	                   	}
	                }
	            }
	            count = 1;
	            if(interviewResultTO !=null && interviewResultTO.getAdmApplnTO()!=null && interviewResultTO.getAdmApplnTO().getId() !=0 && interviewResultTO.getInterviewProgramCourseTO()!=null && interviewResultTO.getPercentage() != null && !interviewResultTO.getPercentage().isEmpty()){
	            boolean addedToset=	results.add(interviewResultTO);
	                if(!addedToset){
	 	        	   if(duplicateApplnNos.length()>0)
	 	        		   duplicateApplnNos.append(", "+(int)Float.parseFloat(row.getCell((byte)0).toString().trim()));
	 	        	   else duplicateApplnNos.append((int)Float.parseFloat(row.getCell((byte)0).toString().trim()));
	 	           }
	            }
	        	}else{
	        		continue;
	        	}
	    	}
	    	if(results!= null){
	    		String user = interviewResultEntryForm.getUserId();
	    		isAdded = UploadInterviewResultHandler.getInstance().addEntranceUploadedData(results, user);
	    	if(isAdded){
	    		//if adding is success
	    		ActionMessage message;
	    		if(duplicateApplnNos.length()>0){
	    			message = new ActionMessage("knowledgepro.admissionForm.uploadEntrance.successWithDuplicates",duplicateApplnNos.toString());
	    		}else
	    		 message = new ActionMessage(CMSConstants.UPLOAD_ENTRANCE_SUCCESS);
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
	    	//	resetFields(interviewResultEntryForm);
	    	}else{
	    		//if adding is failure
	    		ActionMessage message;
	    		if(duplicateApplnNos.length()>0){
	    			message = new ActionMessage("knowledgepro.admissionForm.uploadEntrance.failureWithDuplicates",duplicateApplnNos.toString());
	    		}else
	    		 message = new ActionMessage(CMSConstants.UPLOAD_ENTRANCE_FAILURE);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    	}
	    	}
	      	}else if(fileName.endsWith(".csv")){         //if the uploaded document is csv file.
	      		InterviewResultTO interviewResultTO = null;
	     	    AdmApplnTO admApplnTO;
	     	    GradeTO gradeTO = null;
	     	    InterviewStatusTO interviewStatusTO;
	     	    InterviewProgramCourseTO interviewProgramCourseTO;
	     	  
	     	byte[] fileData    = myFile.getFileData();
		    String source1=prop.getProperty(CMSConstants.UPLOAD_INTERVIEW_CSV);
		    String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			File file1 = new File(filePath+source1);
		    	 
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
			
			file = new File(filePath+source);
			FileInputStream stream1 = new FileInputStream(file);
			LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(stream1));
			
			results = new HashSet<InterviewResultTO>();
			StringBuffer duplicateApplnNos=new StringBuffer();
			int interviewTypeId = 0;
			 int mainroundId = 0;
			 int subroundId = 0;
			while(parser.getLine()!=null){
				if(parser.getValueByLabel("ApplicationNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("ApplicationNumber")))
	      		if(admMap!=null && admMap.containsKey(Integer.parseInt(parser.getValueByLabel("ApplicationNumber")))){
	      			admApplnTO = new AdmApplnTO();
	      			admApplnTO.setId(admMap.get(Integer.parseInt(parser.getValueByLabel("ApplicationNumber"))));
	      			interviewResultTO = new InterviewResultTO();
	      			interviewResultTO.setAdmApplnTO(admApplnTO);
	      			
	      			if(interviewResultEntryForm.getInterviewTypeMap() != null && !interviewResultEntryForm.getInterviewTypeMap().isEmpty()){
						Map<String, Map<Integer, Integer>> mainMap = interviewResultEntryForm.getInterviewTypeMap();
						Map<Integer, Integer> subMap = mainMap.get(interviewResultEntryForm.getInterviewTypeId());
						Map<Integer, Integer> studentCourseMap = interviewResultEntryForm.getIntPrgCourseMap();
						int courseId = studentCourseMap.get(Integer.parseInt(parser.getValueByLabel("ApplicationNumber")));
						interviewTypeId = subMap.get(courseId);
                		interviewProgramCourseTO = new InterviewProgramCourseTO();
                		interviewProgramCourseTO.setId(interviewTypeId);
           	    	    interviewResultTO.setInterviewProgramCourseTO(interviewProgramCourseTO);
					}
					if( interviewResultEntryForm.getInterviewSubroundId() != null && !interviewResultEntryForm.getInterviewSubroundId().trim().isEmpty()){
						subroundId = Integer.parseInt(interviewResultEntryForm.getInterviewSubroundId());
					}
					if( interviewResultEntryForm.getInterviewTypeId() != null && !interviewResultEntryForm.getInterviewTypeId().trim().isEmpty()){
						mainroundId = interviewTypeId;
					}
			    	interviewResultTO.setInterviewSubroundId(subroundId);
	      		}else{
	      			continue;
	      		}
				
				if(parser.getValueByLabel("Percentage") != null && !StringUtils.isEmpty(parser.getValueByLabel("Percentage"))){
	      			interviewResultTO.setPercentage(parser.getValueByLabel("Percentage"));
	      		}				
				
				if(parser.getValueByLabel("Status") != null && !StringUtils.isEmpty(parser.getValueByLabel("Status"))){
	      		if(statusMap!=null && statusMap.containsKey(parser.getValueByLabel("Status"))){
	      			interviewStatusTO = new InterviewStatusTO();
					interviewStatusTO.setId((Integer)statusMap.get(parser.getValueByLabel("Status")));
					interviewResultTO.setInterviewStatusTO(interviewStatusTO);
					
				}else{
					continue;
				}
				}
	      		if(parser.getValueByLabel("Comments")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Comments")))
	      			interviewResultTO.setComments(parser.getValueByLabel("Comments"));
	      		if(interviewResultTO != null && interviewResultTO.getAdmApplnTO() != null && interviewResultTO.getInterviewProgramCourseTO() != null
	      				&& interviewResultTO.getPercentage() != null && !interviewResultTO.getPercentage().isEmpty() ){	
	      		boolean addedToSet=results.add(interviewResultTO);
	      		if(!addedToSet){
	      			if(duplicateApplnNos.length()>0)
	      			duplicateApplnNos.append(", "+parser.getValueByLabel("ApplicationNumber"));
	      			else duplicateApplnNos.append(parser.getValueByLabel("ApplicationNumber"));
	      		}
	      		}else{
	      			continue;
	      		}
			}
			boolean isAdded = false;
			if(results!= null){
	    		String user = interviewResultEntryForm.getUserId();
	    		isAdded = UploadInterviewResultHandler.getInstance().addEntranceUploadedData(results, user);
	    	if(isAdded){
	    		//if adding is success.
	    		ActionMessage message;
	    		if(duplicateApplnNos.length()>0){
	    			message = new ActionMessage("knowledgepro.admissionForm.uploadEntrance.successWithDuplicates",duplicateApplnNos.toString());
	    		}else
	    		 message = new ActionMessage(CMSConstants.UPLOAD_ENTRANCE_SUCCESS);
	    	//	resetFields(interviewResultEntryForm);
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
	    	}else{
	    		//if adding is failure.
	    		ActionMessage message;
	    		if(duplicateApplnNos.length()>0){
	    			message = new ActionMessage("knowledgepro.admissionForm.uploadEntrance.failureWithDuplicates",duplicateApplnNos.toString());
	    		}else
	    		 message = new ActionMessage(CMSConstants.UPLOAD_ENTRANCE_FAILURE);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    	}
	    	}
	      	}else{		//if the uploaded document is not excel/csv file.
	      		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_DOCUMENT);
	    		errors.add(CMSConstants.ERROR,message);
	    		saveErrors(request, errors);
		    }
			}
			else
				saveErrors(request, errors);
			} catch (BusinessException businessException) {
				log.info("Exception uploadInterviewResultEntry");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				interviewResultEntryForm.setErrorMessage(msg);
				interviewResultEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			//setting the programType details to form
			setRequiredDatatoForm(interviewResultEntryForm,request);
			
			log.info("end of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
			return mapping.findForward(CMSConstants.UPLOAD_ENTRANCE_RESULT);
		}	
	
		/**
		 * @param interviewResultEntryForm
		 * @param errors
		 * @throws Exception 
		 */
		public void validatesubroundsData(InterviewResultEntryForm interviewResultEntryForm, ActionErrors errors) throws Exception {
			boolean isSubrountExist = UploadInterviewResultHandler.getInstance().checkSubroundData(interviewResultEntryForm);
			if(isSubrountExist){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.upload.interview.grade.not.match"));
			}
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
		 * Method to set all active Program Types to the form
		 * @param interviewBatchEntryForm
		 * @throws Exception
		 */
		private void setRequiredDatatoForm(InterviewResultEntryForm interviewResultEntryForm, HttpServletRequest request) throws Exception {
			if(ProgramTypeHandler.getInstance().getProgramType() != null){
				interviewResultEntryForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
			}
			Map collegeMap;
			if(interviewResultEntryForm.getCourses()!=null && interviewResultEntryForm.getApplicationYear()!=null &&
					interviewResultEntryForm.getCourses().length >0 && interviewResultEntryForm.getApplicationYear().length()>0){
				collegeMap = CommonAjaxHandler.getInstance().getInterviewTypeByCoursees
				(interviewResultEntryForm.getCourses(),Integer.valueOf(interviewResultEntryForm.getApplicationYear()));
				request.setAttribute("interviewMap", collegeMap);
			}
			if(interviewResultEntryForm.getProgramId()!= null && interviewResultEntryForm.getApplicationYear() != null &&
					interviewResultEntryForm.getProgramId().length() >0 && interviewResultEntryForm.getApplicationYear().length()>0){
				Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
						Integer.valueOf(interviewResultEntryForm.getProgramId()));
				request.setAttribute("coursesMap", courseMap);
			}
			if(interviewResultEntryForm.getProgramTypeId()!=null &&
					interviewResultEntryForm.getProgramTypeId().length()>0 ){
				collegeMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(interviewResultEntryForm.getProgramTypeId()));
				request.setAttribute("programMap", collegeMap);
			}
			if(interviewResultEntryForm.getCourses()!=null && interviewResultEntryForm.getInterviewTypeId()!=null && interviewResultEntryForm.getApplicationYear()!=null &&
					interviewResultEntryForm.getInterviewTypeId().length()>0 && interviewResultEntryForm.getApplicationYear().length()>0){
				collegeMap = CommonAjaxHandler.getInstance().getInterviewSubroundsByInterviewTypeNew(interviewResultEntryForm.getCourses(),interviewResultEntryForm.getInterviewTypeId(),interviewResultEntryForm.getApplicationYear());
				request.setAttribute("interviewSubroundsMap", collegeMap);
			}
		}

}
