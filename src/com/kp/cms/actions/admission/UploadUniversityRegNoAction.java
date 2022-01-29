package com.kp.cms.actions.admission;

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
import com.kp.cms.actions.admission.ExcelDataAction.ByteArrayStreamInfo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.ExcelDataForm;
import com.kp.cms.forms.admission.UploadUniversityRegNoForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.ExcelDataHandler;
import com.kp.cms.to.admission.ExcelDataTO;
import com.kp.cms.utilities.AdmissionCSVUpdater;

public class UploadUniversityRegNoAction  extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(UploadUniversityRegNoAction.class);
	
	public ActionForward initUploadRegNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		UploadUniversityRegNoForm uploadUniversityRegNoForm = (UploadUniversityRegNoForm)form;
		return mapping.findForward(CMSConstants.UPLOAD_UNIVERSITY_REG_NO);
	}
	
	public ActionForward updateUploadData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UploadUniversityRegNoForm uploadUniversityRegNoForm = (UploadUniversityRegNoForm)form; 
		log.info("call of saveUploadData method in ExcelDataAction class.");
		ActionMessages messages = new ActionMessages();
		try {
			 ActionErrors errors = uploadUniversityRegNoForm.validate(mapping, request);
			//setUserId(request,excelDataForm);
			if (errors.isEmpty()) {
			FormFile myFile = uploadUniversityRegNoForm.getCsvFile();
		    String contentType = myFile.getContentType();
		    	 
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    List<ExcelDataTO> excelResults; 
		    int year = Integer.parseInt(uploadUniversityRegNoForm.getAcademicYear());
			int classId = Integer.parseInt(uploadUniversityRegNoForm.getClassId());
			int csDurationId = 0;
			
			//Map<Integer, Integer> admMap = ExcelDataHandler.getInstance().getAdmAppDetails(year,csDurationId,courseId);
			//Map<String, Integer> classesMap = ExcelDataHandler.getInstance().getClasses(courseId,csDurationId,year);
			boolean isAdded = false;
			ExcelDataTO excelDataTO = null;
			List<Integer> regNoList = AdmissionCSVUpdater.getRegisterNoList(year);
			String extn="";
			int indx=myFile.getFileName().lastIndexOf(".");
			if(indx!=-1){
				extn=myFile.getFileName().substring(indx, myFile.getFileName().length());
			}
			if(extn.equalsIgnoreCase(".XLS")){
		    	 byte[] fileData    = myFile.getFileData();
		    
		    	 String source1=prop.getProperty(CMSConstants.UPLOAD_EXCEL);
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
			String source=prop.getProperty(CMSConstants.UPLOAD_EXCEL);
			
			file = new File(filePath+source);
		
		POIFSFileSystem fsSystem = new POIFSFileSystem(new FileInputStream(file));
	    HSSFWorkbook workbook = new HSSFWorkbook(fsSystem);
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
	    excelResults = new ArrayList<ExcelDataTO>();
	    
	    for(int r = 1; r < rows; r++) {
	    	excelDataTO = new ExcelDataTO();
	        row = sheet.getRow(r);
	        int appno = 0;
	        if(row != null) {
	            for(int c = 0; c < cols;c++) {
	                cell = row.getCell((byte)c);
	                if(cell != null && !StringUtils.isEmpty(cell.toString().trim())) {
						if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())) {
							if(cell.toString().endsWith(".0")){
								String value = StringUtils.chop(cell.toString().trim());
								appno = Integer.parseInt(StringUtils.chop(value));
							}else{
								continue;
							}
//							if(admMap != null && admMap.containsKey(appno)&& admMap.containsKey((int)Float.parseFloat(cell.toString()))){
//								excelDataTO.setApplicationId((Integer)admMap.get((int)Float.parseFloat(cell.toString())));
//							}else{
//								continue;
//							}
						}if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())) {
							if(cell.toString().endsWith(".0")){
							String value = StringUtils.chop(cell.toString().trim());
							value = StringUtils.chop(value);
							if(regNoList!=null && regNoList.contains(value)){
								continue;
							}else{
								excelDataTO.setRegistrationNumber(value);
							}
							}else{
							if(regNoList!=null && regNoList.contains(cell.toString().trim())){
								continue;
							}else{
								excelDataTO.setRegistrationNumber(cell.toString().trim());
							}
							}
						}
//						if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
//							if(classesMap.containsKey(cell.toString().trim())){
//								excelDataTO.setClassId(classesMap.get(cell.toString().trim()));
//							}else{
//								continue;
//							}
//	                   	}
						if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
	                   		if(cell.toString().endsWith(".0")){
	                   		String value = StringUtils.chop(cell.toString().trim());
							value = StringUtils.chop(value);
	                   			excelDataTO.setRollNumber(value);
	                   		}else{
	                   			excelDataTO.setRollNumber(cell.toString().trim());
	                   		}
	                   	 }
	                 }
	            }
		            if(excelDataTO!=null){
		            	excelResults.add(excelDataTO);
		            }else{
		            	continue;
		            }
		    	}
		      	}
		    	//	call of handler.
	    		//isAdded = ExcelDataHandler.getInstance().isDataUploaded(excelResults,uploadUniversityRegNoForm);
	    		if(isAdded) {
	    			// if success
	    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_SUCCESS);
	    			messages.add("messages", message);
	    			saveMessages(request, messages);
	    			
	    		}else {
	    			// if failure
	    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_FAILURE);
	    			errors.add("error",message);
		      		saveErrors(request, errors);
		      		
	    		}
		    }else if(extn.equalsIgnoreCase(".CSV")){
		     	  
		     	byte[] fileData    = myFile.getFileData();
			    String source1=prop.getProperty(CMSConstants.UPLOAD_CSV);
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
				String source=prop.getProperty(CMSConstants.UPLOAD_CSV);
				
				file = new File(filePath+source);
				FileInputStream stream1 = new FileInputStream(file);
				LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(stream1));
				
				excelResults = new ArrayList<ExcelDataTO>();
				while(parser.getLine()!=null){
					excelDataTO = new ExcelDataTO();
					if(parser.getValueByLabel("Application_no") != null && !StringUtils.isEmpty(parser.getValueByLabel("Application_no"))){
						String applnNo=parser.getValueByLabel("Application_no");
						char[] strArray = applnNo.toCharArray();  
				        StringBuffer stringBuffer = new StringBuffer();  
				        for (int i = 1; i < strArray.length; i++) {  
				            if ((strArray[i] != ' ') && (strArray[i] != '\t')) {  
				                stringBuffer.append(strArray[i]);  
				            }  
				        }  
				         excelDataTO.setApplicationId(Integer.parseInt(applnNo));
//			      		if(admMap!=null && admMap.containsKey(Integer.parseInt(appln))){
//			      			excelDataTO.setApplicationId(admMap.get(Integer.parseInt(appln)));
//			      		}else{
//			      			continue;
//			      		}
					}
					if(parser.getValueByLabel("Register_no") != null && !StringUtils.isEmpty(parser.getValueByLabel("Register_no"))){
							excelDataTO.setRegistrationNumber(parser.getValueByLabel("Register_no"));
					}
					if(parser.getValueByLabel("University_Regno") != null && !StringUtils.isEmpty(parser.getValueByLabel("University_Regno"))){
							excelDataTO.setUniversityRegNo(parser.getValueByLabel("University_Regno"));
						
					}
//					String className=parser.getValueByLabel("ClassName");
//					className = className.trim();
//					String[] words = className.split("\\s+");
//					StringBuffer stringBuffer1 = new StringBuffer();  
//					String firstWord = words[0];
//					char[] first = firstWord.toCharArray();
//					for (int i = 1; i < first.length; i++) {  
//			            if ((first[i] != ' ') && (first[i] != '\t')) {  
//			            	stringBuffer1.append(first[i]);  
//			            }  
//			        } 
//					firstWord= stringBuffer1.toString();
//					String finalClass=firstWord;
//					for(int i = 1; i < words.length; i++){
//						finalClass+=" " +words[i];
//					}
					if(classId != 0 ){
						excelDataTO.setClassId(classId);
					}
					
		      		if(excelDataTO != null){
		      			excelResults.add(excelDataTO);
		      		}else{
		      			continue;
		      		}
				}
				if(excelResults!= null && !excelResults.isEmpty()){
		    		//isAdded = ExcelDataHandler.getInstance().isDataUploaded(excelResults,excelDataForm);
		    		isAdded = ExcelDataHandler.getInstance().dataUpload(excelResults,uploadUniversityRegNoForm);
		    		if(isAdded) {
		    			// if success
		    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_SUCCESS);
		    			messages.add("messages", message);
		    			saveMessages(request, messages);
		    			
		    		}else {
		    			// if failure
		    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_FAILURE);
		    			errors.add("error",message);
			      		saveErrors(request, errors);
			      		
		    		}
				}
			    }else{
			    	// if upload doc is not excel and csv.
			      		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC);
			      		errors.add("error",message);
			      		saveErrors(request, errors);
				    }
				}else{
					//if errors are present.
					saveErrors(request, errors);
					
				}
			}catch (BusinessException businessException) {
				businessException.printStackTrace();
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {
				exception.printStackTrace();
				String msg = super.handleApplicationException(exception);
				uploadUniversityRegNoForm.setErrorMessage(msg);
				uploadUniversityRegNoForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			//to get Program Type details.
			
			
			log.info("end of saveUploadData method in ExcelDataAction class.");
			return mapping.findForward(CMSConstants.UPLOAD_UNIVERSITY_REG_NO);
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
