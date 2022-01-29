package com.kp.cms.actions.admin;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.SyllabusUploadForm;
import com.kp.cms.handlers.admin.SyllabusUploadHandler;
import com.kp.cms.to.admin.SyllabusUploadTo;

@SuppressWarnings("deprecation")
public class SyllabusUploadAction  extends BaseDispatchAction{

	private static Log log = LogFactory.getLog(SyllabusUploadAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSyllabusUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusUploadForm uploadForm=(SyllabusUploadForm) form;
		uploadForm.reset();
		log.info("init method called");
		return mapping.findForward(CMSConstants.INIT_SYLLABUS_UPLOAD);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward syllabusUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusUploadForm uploadForm=(SyllabusUploadForm) form;
		setUserId(request, uploadForm);
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		log.info("syllabusUpload method called");
		try{
			
			if(uploadForm.getThefile().getFileName()==null || uploadForm.getThefile().getFileName().isEmpty()){
				ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	     }else{
				FormFile myFile = uploadForm.getThefile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = SyllabusUploadAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    Map<Integer, List<SyllabusUploadTo>> syllabusEntryMap=new HashMap<Integer, List<SyllabusUploadTo>>();
			    List<Integer> subjectIdList=new ArrayList<Integer>();
			    //if the uploading file is excel file
			    if(fileName.endsWith(".xls")){
			    	byte[] fileData = myFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.SYLLABUS_UPLOAD);
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
				String source=prop.getProperty(CMSConstants.SYLLABUS_UPLOAD);
				file = new File(filePath+source);
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
			    List<String> notUploadList=new ArrayList<String>();
			    String academicYear=null;
			    String previousSubjectCode=null;
			    int unitNo=1;
			    for(int r = 1; r < rows; r++) {
			        row = sheet.getRow(r);
			        SyllabusUploadTo uploadTo=new SyllabusUploadTo();
			        if(row != null) {
			            for(int c = 0; c < cols;c++) {
			                cell = row.getCell((byte)c);
			                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
			                	 String cell2=cell.toString();
								
								if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell2)){
									uploadTo.setAcademicYear(removeFileExtension(cell2.trim()));
									academicYear=removeFileExtension(cell2.trim());
								}
								
								if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell2)){
									uploadTo.setSubjectCode(removeFileExtension(cell2.trim()));
									previousSubjectCode=removeFileExtension(cell2.trim());
								}
								
								if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell2)){
									uploadTo.setSubjectName(cell2.trim());
								}
								if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell2)){
									uploadTo.setTeachingHoursPerSem(removeFileExtension(cell2.trim()));
								}
								if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell2)){
									uploadTo.setTeachingHoursPerWeek(removeFileExtension(cell2.trim()));
								}
								if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell2)){
									uploadTo.setCourseDescription(cell2.trim());
								}
								if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell2)){
									uploadTo.setLearningOutcome(cell2.trim());
								}
								if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell2)){
									uploadTo.setTextBook(cell2.trim());
								}
								if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell2)){
									uploadTo.setUnitNo(cell2.trim());
								}
								if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell2)){
									uploadTo.setTeachingHours(removeFileExtension(cell2.trim()));
								}
								if(cell.getCellNum() == 10 && !StringUtils.isEmpty(cell2)){
									uploadTo.setHeadings(cell2.trim());
								}
								if(cell.getCellNum() == 11 && !StringUtils.isEmpty(cell2)){
									uploadTo.setContents(cell2.trim());
								}
			                }
		                }
			            
			            if(uploadTo.getAcademicYear()!=null && uploadTo.getSubjectCode()!=null){
			            	Integer subjectId=SyllabusUploadHandler.getInstance().getSubjectIdByYearAndSubjectCode(uploadTo.getAcademicYear(),uploadTo.getSubjectCode());
			            	if(subjectId!=null && subjectId!=0){
			            		if(!subjectIdList.contains(subjectId)){
			            		   subjectIdList.add(subjectId);
			            		}
			            		if(syllabusEntryMap.containsKey(subjectId)){
			            			List<SyllabusUploadTo> uploadToList=syllabusEntryMap.get(subjectId);
			            			unitNo=unitNo+1;
			            			uploadTo.setSyllabusEntryNo(unitNo);
			            			uploadToList.add(uploadTo);
			            			syllabusEntryMap.put(subjectId, uploadToList);
			            		}else{
			            			List<SyllabusUploadTo> uploadToList=new ArrayList<SyllabusUploadTo>();
			            			unitNo=1;
			            			uploadTo.setSyllabusEntryNo(unitNo);
			            			uploadToList.add(uploadTo);
			            			syllabusEntryMap.put(subjectId, uploadToList);
			            		}
			            	}else{
			            		if(!notUploadList.contains(uploadTo.getSubjectCode())){
			            			notUploadList.add(uploadTo.getSubjectCode());
			            		}
			            	}
			            }else if(academicYear!=null && previousSubjectCode!=null){
			            	    uploadTo.setAcademicYear(academicYear);
			            	    uploadTo.setSubjectCode(previousSubjectCode);
				            	Integer subjectId=SyllabusUploadHandler.getInstance().getSubjectIdByYearAndSubjectCode(uploadTo.getAcademicYear(),uploadTo.getSubjectCode());
				            	if(subjectId!=null && subjectId!=0){
				            		if(!subjectIdList.contains(subjectId)){
				            		   subjectIdList.add(subjectId);
				            		}
				            		if(syllabusEntryMap.containsKey(subjectId)){
				            			List<SyllabusUploadTo> uploadToList=syllabusEntryMap.get(subjectId);
				            			unitNo=unitNo+1;
				            			uploadTo.setSyllabusEntryNo(unitNo);
				            			uploadToList.add(uploadTo);
				            			syllabusEntryMap.put(subjectId, uploadToList);
				            		}else{
				            			List<SyllabusUploadTo> uploadToList=new ArrayList<SyllabusUploadTo>();
				            			unitNo=1;
				            			uploadTo.setSyllabusEntryNo(unitNo);
				            			uploadToList.add(uploadTo);
				            			syllabusEntryMap.put(subjectId, uploadToList);
				            		}
				            	}else{
				            		if(!notUploadList.contains(uploadTo.getSubjectCode())){
				            			notUploadList.add(uploadTo.getSubjectCode());
				            		}
				            	}
			            }
		    	}else {
		    			continue;		        	
						}
		    	}
			    if(!syllabusEntryMap.isEmpty()){
			    	isAdded=SyllabusUploadHandler.getInstance().uploadSyllabus(syllabusEntryMap,uploadForm,subjectIdList);
			    }
			    if(isAdded){
			    	if(!notUploadList.isEmpty()){
			    		String subjectNotUploaded="";
			    		int count=0;
			    		   for (String subjectCode : notUploadList) {
			    			   if(count==12){
			    				   subjectNotUploaded=subjectNotUploaded+",<br>"+subjectCode;
			    				   count=0;
			    			   }else{
			    				   subjectNotUploaded=subjectNotUploaded+","+subjectCode;
			    			   }
			    			   count++;
						}
			    		    subjectNotUploaded=subjectNotUploaded.substring(1);
			    		    ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_SUCCESS);
					    	messages.add("messages", message);
					    	saveMessages(request, messages);
			    		    errors.add("error", new ActionError("knowledgepro.admin.syllabus.upload.not.uploaded.subject.code",subjectNotUploaded));
				    		addErrors(request, errors);
			    	   }else{
			    		   ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_SUCCESS);
					    	messages.add("messages", message);
					    	saveMessages(request, messages);
			    	   }
			    		
		    	}else{
		    		//if adding is failure
		    		if(!notUploadList.isEmpty()){
			    		String subjectNotUploaded="";
			    		int count=0;
			    		   for (String subjectCode : notUploadList) {
			    			   if(count==12){
			    				   subjectNotUploaded=subjectNotUploaded+",<br>"+subjectCode;
			    				   count=0;
			    			   }else{
			    				   subjectNotUploaded=subjectNotUploaded+","+subjectCode;
			    			   }
			    			   count++;
						}
			    		   subjectNotUploaded=subjectNotUploaded.substring(1);	   
			               errors.add("error", new ActionError("knowledgepro.admission.upload.syllabus.failure",subjectNotUploaded));
		    		       addErrors(request, errors);
		    	 }	
		    	}
			    }else{
			    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
		    		errors.add(CMSConstants.ERROR, message);
		    		addErrors(request, errors);
			    }
			}
			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				uploadForm.setErrorMessage(msg);
				uploadForm.setErrorStack(exception.getMessage());
				log.info("syllabusUpload method called exception occured");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		
		return mapping.findForward(CMSConstants.INIT_SYLLABUS_UPLOAD);
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	public String removeFileExtension(String fileName)
	{ 
	if(null != fileName && fileName.contains("."))
	{
	return fileName.substring(0, fileName.lastIndexOf("."));
	}
	return fileName;
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
