package com.kp.cms.actions.admission;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.StudentSpecializationPrefered;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.InterviewResultEntryForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.UploadInterviewResultHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.admission.InterviewBatchEntryHelper;
import com.kp.cms.to.admin.GradeTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.to.admission.InterviewResultDetailTO;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.to.admission.InterviewStatusTO;
import com.kp.cms.transactions.admission.IInterviewBatchEntryTransaction;
import com.kp.cms.transactionsimpl.admission.InterviewBatchEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")

public class UploadInterviewResultAction extends BaseDispatchAction{
	
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
	
	public ActionForward initInterviewResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm)form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","upload_interview_grade");
		
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
		return mapping.findForward(CMSConstants.UPLOAD_INTERVIEW_RESULT);
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
		interviewResultEntryForm.setExport(false);
		interviewResultEntryForm.setInterviewDate(null);
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
	
	public ActionForward uploadInterviewResultEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm)form;
		log.info("call of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
		 ActionErrors errors = interviewResultEntryForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request,interviewResultEntryForm);
			if(interviewResultEntryForm.getCourses() == null || interviewResultEntryForm.getCourses().length ==0){
				errors.add("error", new ActionError("admissionFormForm.courseId.required"));
			}
			if(interviewResultEntryForm.getCourses() != null && interviewResultEntryForm.getCourses().length !=0){
				validatesubroundsData(interviewResultEntryForm,errors);
			}
			if(!StringUtils.isEmpty(interviewResultEntryForm.getSubroundCount()) && Integer.parseInt(interviewResultEntryForm.getSubroundCount()) != 0){
				if(interviewResultEntryForm.getInterviewSubroundId() !=null && interviewResultEntryForm.getInterviewSubroundId().length()== 0) {
					errors.add("error", new ActionError(CMSConstants.UPLOAD_INTERVIEW_SUBROUND));
				}	
			}
			 if (errors!= null && errors.isEmpty()) {
				int applicationYear = Integer.parseInt(interviewResultEntryForm.getApplicationYear());
				//int courseId = Integer.parseInt(interviewResultEntryForm.getCourseId());
				
				//changes done for  for course multi select
				//start
				String[] courseIds = interviewResultEntryForm.getCourses();
//				String interviewType = interviewResultEntryForm.getInterviewTypeId();
				//End
				
//				int interviewTypeId = Integer.parseInt(interviewResultEntryForm.getInterviewTypeId());
				/*int interviewSubRoundId = 0;
				if(interviewResultEntryForm.getInterviewSubroundId()!=null && !interviewResultEntryForm.getInterviewSubroundId().isEmpty()){
					interviewSubRoundId = Integer.parseInt(interviewResultEntryForm.getInterviewSubroundId());
				}*/
				
			FormFile myFile = interviewResultEntryForm.getThefile();
		    String contentType = myFile.getContentType();
		    	 
		   String fileName    = myFile.getFileName();
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = UploadInterviewResultAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		     //getting adm_appln, grade, interview status details.
//		     Map<Integer,Integer> admMap = UploadInterviewResultHandler.getInstance().getAdmAppDetails(applicationYear,courseId);
		    Map<Integer,Integer> admMap = UploadInterviewResultHandler.getInstance().getAdmAppDetailsForSelectedCourses(applicationYear,courseIds,interviewResultEntryForm);
		     Map<String,Integer> gradeMap = UploadInterviewResultHandler.getInstance().getGradeDetails();
		     Map<String,Integer> statusMap = UploadInterviewResultHandler.getInstance().getInterviewStatusDetails();
		     Set<InterviewResultTO> results = null;
		    /* if(interviewResultEntryForm.getInterviewTypeMap() != null && !interviewResultEntryForm.getInterviewTypeMap().isEmpty()){
		    	 Map<String, Map<Integer, Integer>> mainMap = interviewResultEntryForm.getInterviewTypeMap();
		    	 Map<Integer, Integer> subMap = mainMap.get(interviewResultEntryForm.getInterviewTypeMap());
		    	 
		     }
		    int interviewTypeId = 0;
		    int mainroundId = 0;
			int subroundId = 0;
			int interviewersPerPanel = 1;
			if( interviewResultEntryForm.getInterviewSubroundId() != null && !interviewResultEntryForm.getInterviewSubroundId().trim().isEmpty()){
				subroundId = Integer.parseInt(interviewResultEntryForm.getInterviewSubroundId());
			}
			if( interviewResultEntryForm.getInterviewTypeId() != null && !interviewResultEntryForm.getInterviewTypeId().trim().isEmpty()){
				mainroundId = Integer.parseInt(interviewResultEntryForm.getInterviewTypeId());
			}
			
			interviewersPerPanel = UploadInterviewResultHandler.getInstance().getInterviewersPerPanel(mainroundId, subroundId);*/
				
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
	    AdmApplnTO admApplnTO=null;
	    GradeTO gradeTO;
	    InterviewStatusTO interviewStatusTO;
	    InterviewProgramCourseTO interviewProgramCourseTO;
	    InterviewResultDetailTO intDetailTo;
	    
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
	    int commentsCount=0;
	    results = new HashSet<InterviewResultTO>();
	    StringBuffer duplicateApplnNos=new StringBuffer();
	    List<StudentSpecializationPrefered> studentSpecializationBoList=new ArrayList<StudentSpecializationPrefered>();
	    int count = 1;
	    int tempCount=0;
	    for(int r = 1; r < rows; r++) {
	 //   	List<GradeTO> gradeList = new ArrayList<GradeTO>();
	    //	List<InterviewResultDetailTO> intCommentsList=new ArrayList<InterviewResultDetailTO>();
	    	Map<Integer, InterviewResultDetailTO> intResultDetailMap=new HashMap<Integer, InterviewResultDetailTO>();
	    	 interviewResultTO = new InterviewResultTO();
	    	 AdmAppln admBo=null;
	    	 StudentSpecializationPrefered specializationBo=null;
	        row = sheet.getRow(r);
	        if(row != null) {
	        	 int mainroundId = 0;
					int subroundId = 0;
					int interviewersPerPanel = 1;
					int interviewTypeId = 0;
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
	                		}
	                		
	                		if( interviewResultEntryForm.getInterviewSubroundId() != null && !interviewResultEntryForm.getInterviewSubroundId().trim().isEmpty()){
	                			if(interviewResultEntryForm.getSubroundMap() != null && !interviewResultEntryForm.getSubroundMap().isEmpty()){
	                				subroundId = interviewResultEntryForm.getSubroundMap().get(interviewTypeId);
	                			}
	                		}
	                		if( interviewResultEntryForm.getInterviewTypeId() != null && !interviewResultEntryForm.getInterviewTypeId().trim().isEmpty()){
	                			mainroundId = interviewTypeId;
	                		}
	                		interviewersPerPanel = UploadInterviewResultHandler.getInstance().getInterviewersPerPanel(mainroundId, subroundId);
	                		commentsCount=interviewersPerPanel+1;
						} 
						
						interviewProgramCourseTO = new InterviewProgramCourseTO();
		        	    interviewProgramCourseTO.setId(interviewTypeId);
		        	    interviewResultTO.setInterviewProgramCourseTO(interviewProgramCourseTO);
		   	    	    interviewResultTO.setInterviewSubroundId(subroundId);
						if(cell.getCellNum() == count && count <= interviewersPerPanel && !StringUtils.isEmpty(cell.toString())){
	                   			if(gradeMap!=null && gradeMap.containsKey(cell.toString().trim())){
	                   				 intDetailTo=new InterviewResultDetailTO();
	                   				intDetailTo.setGradeObtainedId((Integer)gradeMap.get(cell.toString().trim()));
	                   				intResultDetailMap.put(count, intDetailTo);
		                		}else{
		                			break;
		               			}
	                   	//		interviewResultTO.setGradeList(gradeList);
//	                   			if(count > interviewersPerPanel){
//	                   				break;
//	                   			}
	                   			++count;
	                   	}
						if(cell.getCellNum() == commentsCount && commentsCount <= (interviewersPerPanel*2)){
							if(!StringUtils.isEmpty(cell.toString())){
							int keyForMap=commentsCount-interviewersPerPanel;
							if(intResultDetailMap.containsKey(keyForMap)){
							intDetailTo=intResultDetailMap.remove(keyForMap);
							if(intDetailTo!=null)
							intDetailTo.setComments(cell.toString());
							intResultDetailMap.put(keyForMap, intDetailTo);
							}
							}
							++commentsCount;
                   	}
						if(intResultDetailMap!=null && !intResultDetailMap.isEmpty()){
						List<InterviewResultDetailTO> interviewResultDetailList=new ArrayList<InterviewResultDetailTO>(intResultDetailMap.values());
						interviewResultTO.setInterviewResultDetail(interviewResultDetailList);
						}
						if(cell.getCellNum() == (interviewersPerPanel*2)+1 && !StringUtils.isEmpty(cell.toString())){
	                   			if(statusMap!=null && statusMap.containsKey(cell.toString().trim())){
	                   				interviewStatusTO = new InterviewStatusTO();
	                   				interviewStatusTO.setId((Integer)statusMap.get(cell.toString().trim()));
		                			interviewResultTO.setInterviewStatusTO(interviewStatusTO);
		                		}else{
		                			break;
		               			}
	                   	}
						if(cell.getCellNum() == (interviewersPerPanel*2)+2 && !StringUtils.isEmpty(cell.toString())){
							admBo=new AdmAppln();
							specializationBo=new StudentSpecializationPrefered();
							admBo.setId(admApplnTO.getId());
							specializationBo.setAdmAppln(admBo);
							specializationBo.setSpecializationPrefered(cell.toString().trim());
                   	}
					if(cell.getCellNum() == (interviewersPerPanel*2)+3 && !StringUtils.isEmpty(cell.toString())){
						if(admBo==null ){
							admBo=new AdmAppln();
							specializationBo=new StudentSpecializationPrefered();
							admBo.setId(admApplnTO.getId());
							specializationBo.setAdmAppln(admBo);
						}
						if(cell.toString().trim().equalsIgnoreCase("Yes"))
							specializationBo.setBackLogs(true);
						else specializationBo.setBackLogs(false);
                   	}
					if(specializationBo!=null)
					studentSpecializationBoList.add(specializationBo);
						/* if(cell.getCellNum() == (interviewersPerPanel*2)+2 && !StringUtils.isEmpty(cell.toString())){
	                   		interviewResultTO.setComments(cell.toString());
	                   	}*/
	                }
	            }
	            count = 1;
	            
	            if(interviewResultTO !=null && interviewResultTO.getAdmApplnTO()!=null && interviewResultTO.getAdmApplnTO().getId() !=0 && interviewResultTO.getInterviewProgramCourseTO()!=null && !interviewResultTO.getInterviewResultDetail().isEmpty()){
	           boolean addedToset= results.add(interviewResultTO);
	           if(!addedToset){
	        	   if(duplicateApplnNos.length()>0)
	        		   duplicateApplnNos.append(", "+(int)Float.parseFloat(row.getCell((byte)0).toString().trim()));
	        	   else duplicateApplnNos.append((int)Float.parseFloat(row.getCell((byte)0).toString().trim()));
	           }
	            }
	        	}else{
	        		continue;
	        	}
	        tempCount++;
	    	}
	    	if(!results.isEmpty()){
	    		String user = interviewResultEntryForm.getUserId();
	    		isAdded = UploadInterviewResultHandler.getInstance().addUploadedData(results, user,studentSpecializationBoList);
	    	if(isAdded){
	    		//if adding is success
	    		ActionMessage message;
	    		if(duplicateApplnNos.length()>0){
	    			message = new ActionMessage("knowledgepro.admissionForm.upload.successWithDuplicates",duplicateApplnNos.toString());
	    		}else
	    		 message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_SUCCESS);
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
	    	}else{
	    		//if adding is failure
	    		ActionMessage message;
	    		if(duplicateApplnNos.length()>0){
	    			message = new ActionMessage("knowledgepro.admissionForm.upload.failureWithDuplicates",duplicateApplnNos.toString());
	    		}else
	    		 message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_FAILURE);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    	}
	    	}
	      	}else if(fileName.endsWith(".csv")){         //if the uploaded document is csv file.
	      		InterviewResultTO interviewResultTO = null;
	     	    AdmApplnTO admApplnTO=null;
	     	    GradeTO gradeTO = null;
	     	    InterviewStatusTO interviewStatusTO;
	     	    InterviewProgramCourseTO interviewProgramCourseTO;
	     	    InterviewResultDetailTO intDetailTo;
	     	  
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
			List<StudentSpecializationPrefered> studentSpecializationBoList=new ArrayList<StudentSpecializationPrefered>();
	//		List<GradeTO> gradeList = null;
			 int interviewTypeId = 0;
			 int mainroundId = 0;
			 int subroundId = 0;
			 int interviewersPerPanel = 1;
			while(parser.getLine()!=null){
				interviewResultTO = new InterviewResultTO();
				AdmAppln admBo=null;
		    	StudentSpecializationPrefered specializationBo=null;
				Map<Integer, InterviewResultDetailTO> intResultDetailMap=new HashMap<Integer, InterviewResultDetailTO>();
				if(parser.getValueByLabel("ApplicationNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("ApplicationNumber")))
				//	if(parser.getValueByLabel("ApplicationNumber").equalsIgnoreCase("12550415")){
						if(admMap!=null && admMap.containsKey(Integer.parseInt(parser.getValueByLabel("ApplicationNumber")))){
							admApplnTO = new AdmApplnTO();
							admApplnTO.setId(admMap.get(Integer.parseInt(parser.getValueByLabel("ApplicationNumber"))));
							interviewResultTO.setAdmApplnTO(admApplnTO);
							if(interviewResultEntryForm.getInterviewTypeMap() != null && !interviewResultEntryForm.getInterviewTypeMap().isEmpty()){
								Map<String, Map<Integer, Integer>> mainMap = interviewResultEntryForm.getInterviewTypeMap();
								Map<Integer, Integer> subMap = mainMap.get(interviewResultEntryForm.getInterviewTypeId());
								Map<Integer, Integer> studentCourseMap = interviewResultEntryForm.getIntPrgCourseMap();
								int courseId = studentCourseMap.get(Integer.parseInt(parser.getValueByLabel("ApplicationNumber")));
								interviewTypeId = subMap.get(courseId);
							}
							if( interviewResultEntryForm.getInterviewSubroundId() != null && !interviewResultEntryForm.getInterviewSubroundId().trim().isEmpty()){
								subroundId = Integer.parseInt(interviewResultEntryForm.getInterviewSubroundId());
							}
							if( interviewResultEntryForm.getInterviewTypeId() != null && !interviewResultEntryForm.getInterviewTypeId().trim().isEmpty()){
								mainroundId = interviewTypeId;
							}
							interviewersPerPanel = UploadInterviewResultHandler.getInstance().getInterviewersPerPanel(mainroundId, subroundId);
							interviewProgramCourseTO = new InterviewProgramCourseTO();
							interviewProgramCourseTO.setId(interviewTypeId);
							interviewResultTO.setInterviewProgramCourseTO(interviewProgramCourseTO);
							interviewResultTO.setInterviewSubroundId(subroundId);
							
							
						}else{
							continue;
						}
			//		}
				 
			//	gradeList = new ArrayList<GradeTO>();
				for(int count= 1;count <= interviewersPerPanel;++count){
					if(parser.getValueByLabel("Grade"+count) != null && !StringUtils.isEmpty(parser.getValueByLabel("Grade"+count)))
		      		if(gradeMap!=null && gradeMap.containsKey(parser.getValueByLabel("Grade"+count))){
		      			 intDetailTo=new InterviewResultDetailTO();
            				intDetailTo.setGradeObtainedId((Integer)gradeMap.get(parser.getValueByLabel("Grade"+count)));
            				intResultDetailMap.put(count, intDetailTo);
		      		}else{
		      			continue;
		      		}
				}
			//	interviewResultTO.setGradeList(gradeList);
				for(int count= 1;count <= interviewersPerPanel;++count){
					if(parser.getValueByLabel("Comments"+count) != null && !StringUtils.isEmpty(parser.getValueByLabel("Comments"+count)))
						if(intResultDetailMap.containsKey(count)){
							intDetailTo=intResultDetailMap.remove(count);
							if(intDetailTo!=null)
							intDetailTo.setComments(parser.getValueByLabel("Comments"+count));
							intResultDetailMap.put(count, intDetailTo);
							}
				}
				List<InterviewResultDetailTO> interviewResultDetailList=new ArrayList<InterviewResultDetailTO>(intResultDetailMap.values());
				interviewResultTO.setInterviewResultDetail(interviewResultDetailList);
				
				if(parser.getValueByLabel("Status") != null && !StringUtils.isEmpty(parser.getValueByLabel("Status"))){
	      		if(statusMap!=null && statusMap.containsKey(parser.getValueByLabel("Status"))){
	      			interviewStatusTO = new InterviewStatusTO();
					interviewStatusTO.setId((Integer)statusMap.get(parser.getValueByLabel("Status")));
					interviewResultTO.setInterviewStatusTO(interviewStatusTO);
					
				}else{
					continue;
				}
				}
				if(parser.getValueByLabel("SpecializationPrefered") != null && !StringUtils.isEmpty(parser.getValueByLabel("SpecializationPrefered"))){
					admBo=new AdmAppln();
					specializationBo=new StudentSpecializationPrefered();
					admBo.setId(admApplnTO.getId());
					specializationBo.setAdmAppln(admBo);
					specializationBo.setSpecializationPrefered(parser.getValueByLabel("SpecializationPrefered"));
					}
				if(parser.getValueByLabel("BackLogs") != null && !StringUtils.isEmpty(parser.getValueByLabel("BackLogs"))){
					if(admBo==null ){
						admBo=new AdmAppln();
						specializationBo=new StudentSpecializationPrefered();
						admBo.setId(admApplnTO.getId());
						specializationBo.setAdmAppln(admBo);
					}
					if(parser.getValueByLabel("BackLogs").equalsIgnoreCase("Yes"))
					specializationBo.setBackLogs(true);
					else specializationBo.setBackLogs(false);
					}
				if(specializationBo!=null)
					studentSpecializationBoList.add(specializationBo);
	      	/*	if(parser.getValueByLabel("Comments")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Comments")))
	      			interviewResultTO.setComments(parser.getValueByLabel("Comments"));*/
	      		if(interviewResultTO != null && interviewResultTO.getAdmApplnTO() != null && interviewResultTO.getInterviewProgramCourseTO() != null
	      				&& !interviewResultTO.getInterviewResultDetail().isEmpty()){	
	      		boolean addedToSet=	results.add(interviewResultTO);
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
				if(results!= null && !results.isEmpty()){
		    		String user = interviewResultEntryForm.getUserId();
		    		isAdded = UploadInterviewResultHandler.getInstance().addUploadedData(results, user,studentSpecializationBoList);
		    	if(isAdded){
		    		ActionMessage message;
		    		//if adding is success.
		    		if(duplicateApplnNos.length()>0){
		    			message = new ActionMessage("knowledgepro.admissionForm.upload.successWithDuplicates",duplicateApplnNos.toString());
		    		}else
		    		 message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_SUCCESS);
//		    		resetFields(interviewResultEntryForm);
		    		messages.add("messages", message);
		    		saveMessages(request, messages);
		    	}else{
		    		//if adding is failure.
		    		ActionMessage message;
		    		if(duplicateApplnNos.length()>0){
		    			message = new ActionMessage("knowledgepro.admissionForm.upload.failureWithDuplicates",duplicateApplnNos.toString());
		    		}else
		    		 message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_FAILURE);
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
				log.error("Exception uploadInterviewResultEntry",businessException);
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {	
				log.error("Exception uploadInterviewResultEntry",exception);
				String msg = super.handleApplicationException(exception);
				interviewResultEntryForm.setErrorMessage(msg);
				interviewResultEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			//setting the programType details to form
			setRequiredDatatoForm(interviewResultEntryForm,request);
			interviewResultEntryForm.setSubroundCount(null);
			log.info("end of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
			return mapping.findForward(CMSConstants.UPLOAD_INTERVIEW_RESULT);
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
					interviewResultEntryForm.getCourses().length>0 && interviewResultEntryForm.getApplicationYear().length()>0){
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
		
		
		/**
		 * This method is used to save the upload document.
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		
		public ActionForward downloadInterviewResultEntry(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm)form;
			log.info("call of uploadInterviewResultEntry method in UploadInterviewResultAction class.");
			 ActionErrors errors = interviewResultEntryForm.validate(mapping, request);
			ActionMessages messages = new ActionMessages();
			try {
				interviewResultEntryForm.setExport(false);
				setUserId(request,interviewResultEntryForm);
				validateTime(interviewResultEntryForm, errors);
				if(!StringUtils.isEmpty(interviewResultEntryForm.getSubroundCount()) && Integer.parseInt(interviewResultEntryForm.getSubroundCount()) != 0){
					if(interviewResultEntryForm.getInterviewSubroundId() !=null && interviewResultEntryForm.getInterviewSubroundId().length()== 0) {
						errors.add("error", new ActionError(CMSConstants.UPLOAD_INTERVIEW_SUBROUND));
					}	
				}
				if (errors.isEmpty()) {
				    int mainroundId = 0;
					int subroundId = 0;
					int interviewersPerPanel = 1;
					if( interviewResultEntryForm.getInterviewSubroundId() != null && !interviewResultEntryForm.getInterviewSubroundId().trim().isEmpty()){
						subroundId = Integer.parseInt(interviewResultEntryForm.getInterviewSubroundId());
					}
					if( interviewResultEntryForm.getInterviewTypeId() != null && !interviewResultEntryForm.getInterviewTypeId().trim().isEmpty()){
						mainroundId = Integer.parseInt(interviewResultEntryForm.getInterviewTypeId());
					}
					
					interviewersPerPanel = UploadInterviewResultHandler.getInstance().getInterviewersPerPanel(mainroundId, subroundId);
					InterviewResultTO interviewBatchEntryTO = UploadInterviewResultHandler.getInstance().convertFormtoTo(interviewResultEntryForm);
					IInterviewBatchEntryTransaction iInterviewBatchEntryTransaction = new InterviewBatchEntryTransactionImpl();
					InterviewBatchEntryHelper interviewBatchEntryHelper = new InterviewBatchEntryHelper();
					List getSelectedCandidatesList = iInterviewBatchEntryTransaction.getSelectedCandidates(interviewBatchEntryTO);
					List<InterviewResultTO> selectedCandidates = interviewBatchEntryHelper.convertBotoTo(getSelectedCandidatesList,interviewResultEntryForm.getInterviewTypeId(), interviewResultEntryForm.getInterviewSubroundId(),interviewersPerPanel);
					resetFields(interviewResultEntryForm);
					if(selectedCandidates==null || selectedCandidates.isEmpty()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
						setRequiredDatatoForm(interviewResultEntryForm,request);
						return mapping.findForward(CMSConstants.UPLOAD_INTERVIEW_RESULT);
					}else{
						UploadInterviewResultHandler.getInstance().exportToCSV(interviewersPerPanel,selectedCandidates,request);
//						request.getSession().setAttribute(CMSConstants.ID_DATA_ATTR, myFileBytes);
						interviewResultEntryForm.setExport(true);
					}
					}else{
					 saveErrors(request, errors);
				 }
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
				return mapping.findForward(CMSConstants.UPLOAD_INTERVIEW_RESULT);
			}
		
		/**
		 * Method to validate the time format
		 * @param interviewBatchEntryForm
		 * @param errors
		 */
		private void validateTime(InterviewResultEntryForm interviewBatchEntryForm, ActionErrors errors) {
			
			if (interviewBatchEntryForm.getInterviewDate() != null && !StringUtils.isEmpty(interviewBatchEntryForm.getInterviewDate())) {
				if (!CommonUtil.isValidDate(interviewBatchEntryForm.getInterviewDate())) {
					if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID) != null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID).hasNext()) {
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID));
					}
				} 
			}
			
			if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getStartingTimeHours())){
				if(!StringUtils.isNumeric(interviewBatchEntryForm.getStartingTimeHours())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			}
			if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getStartingTimeMins())){
				if(!StringUtils.isNumeric(interviewBatchEntryForm.getStartingTimeMins())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getStartingTimeHours())){
				if(Integer.parseInt(interviewBatchEntryForm.getStartingTimeHours())>=24){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getStartingTimeMins())){
				if(Integer.parseInt(interviewBatchEntryForm.getStartingTimeMins())>=60){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getEndingTimeHours())){
				if(!StringUtils.isNumeric(interviewBatchEntryForm.getEndingTimeHours())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			}
			if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getEndingTimeMins())){
				if(!StringUtils.isNumeric(interviewBatchEntryForm.getEndingTimeMins())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getEndingTimeHours())){
				if(Integer.parseInt(interviewBatchEntryForm.getEndingTimeHours())>=24){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getEndingTimeMins())){
				if(Integer.parseInt(interviewBatchEntryForm.getEndingTimeMins())>=60){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
		}

		/**
		 * Method to compare the entered date with future date
		 */
		private boolean validatefutureDate(String dateString) {
			String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, "dd/MM/yyyy","MM/dd/yyyy");
			Date date = new Date(formattedString);
			Date curdate = new Date();
			if (date.compareTo(curdate) == 1){
				return false;
			}else{
				return true;
			}	
		}
}