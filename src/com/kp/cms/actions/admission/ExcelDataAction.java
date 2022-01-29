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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.ExcelDataForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.ExcelDataHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.NewUpdateProccessHandler;
import com.kp.cms.helpers.admin.AcademicYearHelper;
import com.kp.cms.to.admission.ExcelDataTO;
import com.kp.cms.transactions.admission.IExcelTransaction;
import com.kp.cms.transactionsimpl.admission.ExcelTransactionImpl;
import com.kp.cms.utilities.AdmissionCSVUpdater;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExcelDataAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(ExcelDataAction.class);
	
	/**
	 * This method is used to display the program type when u click on link.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExcelDataForm excelDataForm = (ExcelDataForm)form; 
		try {
			if(ProgramTypeHandler.getInstance().getProgramType() != null){
				excelDataForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
			}
			setUserId(request,excelDataForm);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			excelDataForm.setErrorMessage(msgKey);
			excelDataForm.setErrorStack(businessException.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			excelDataForm.setErrorMessage(msg);
			excelDataForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		resetFields(excelDataForm);
		return mapping.findForward(CMSConstants.EXCEL_UPLOAD);
	}

	/**
	 * This method is used to clear the form.
	 * @param excelDataForm
	 */
	
	public void resetFields(ExcelDataForm excelDataForm) {
		excelDataForm.setApplicationYear(null);
		excelDataForm.setProgramTypeId(null);
		excelDataForm.setProgramId(null);
		excelDataForm.setCourseId(null);
		excelDataForm.setSemister(null);
	}
	
	/**
	 * This method is used to save the data from excel and UI.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward saveUploadData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExcelDataForm excelDataForm = (ExcelDataForm)form; 
		log.info("call of saveUploadData method in ExcelDataAction class.");
		ActionMessages messages = new ActionMessages();
		try {
			 ActionErrors errors = excelDataForm.validate(mapping, request);
			//setUserId(request,excelDataForm);
			if (errors.isEmpty()) {
			FormFile myFile = excelDataForm.getThefile();
		    String contentType = myFile.getContentType();
		    	 
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    List<ExcelDataTO> excelResults; 
		    int year = Integer.parseInt(excelDataForm.getApplicationYear());
			int courseId = Integer.parseInt(excelDataForm.getCourseId());
			int csDurationId = 0;
			if(excelDataForm.getSemister()!=null && !excelDataForm.getSemister().isEmpty()){
				csDurationId=Integer.parseInt(excelDataForm.getSemister());
			}
			Map<Integer, Integer> admMap = ExcelDataHandler.getInstance().getAdmAppDetails(year,csDurationId,courseId);
			Map<String, Integer> classesMap = ExcelDataHandler.getInstance().getClasses(courseId,csDurationId,year);
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
						}if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
							if(classesMap.containsKey(cell.toString().trim())){
								excelDataTO.setClassId(classesMap.get(cell.toString().trim()));
							}else{
								continue;
							}
	                   	}if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
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
	    		isAdded = ExcelDataHandler.getInstance().isDataUploaded(excelResults,excelDataForm);
	    		if(isAdded) {
	    			// if success
	    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_SUCCESS);
	    			messages.add("messages", message);
	    			saveMessages(request, messages);
	    			resetFields(excelDataForm);
	    		}else {
	    			// if failure
	    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_FAILURE);
	    			errors.add("error",message);
		      		saveErrors(request, errors);
		      		resetFields(excelDataForm);
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
					if(parser.getValueByLabel("ApplicationNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("ApplicationNumber"))){
						String applnNo=parser.getValueByLabel("ApplicationNumber");
						char[] strArray = applnNo.toCharArray();  
				        StringBuffer stringBuffer = new StringBuffer();  
				        for (int i = 0; i < strArray.length; i++) {  
				            if ((strArray[i] != ' ') && (strArray[i] != '\t')) {  
				                stringBuffer.append(strArray[i]);  
				            }  
				        }  
				         String appln = stringBuffer.toString();
			      		if(admMap!=null && admMap.containsKey(Integer.parseInt(appln))){
			      			excelDataTO.setApplicationId(admMap.get(Integer.parseInt(appln)));
			      		}else{
			      			continue;
			      		}
					}
					if(parser.getValueByLabel("RegistrationNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("RegistrationNumber"))){
						if(regNoList!=null && regNoList.contains(parser.getValueByLabel("RegistrationNumber"))){
							continue;
						}else{
							excelDataTO.setRegistrationNumber(parser.getValueByLabel("RegistrationNumber"));
						}
					}
					String className=parser.getValueByLabel("ClassName");
					className = className.trim();
					String[] words = className.split("\\s+");
					StringBuffer stringBuffer1 = new StringBuffer();  
					String firstWord = words[0];
					char[] first = firstWord.toCharArray();
					for (int i = 0; i < first.length; i++) {  
			            if ((first[i] != ' ') && (first[i] != '\t')) {  
			            	stringBuffer1.append(first[i]);  
			            }  
			        } 
					firstWord= stringBuffer1.toString();
					String finalClass=firstWord;
					/*for(int i = 0; i < words.length; i++){
						finalClass+=" " +words[i];
					}*/
					if(classesMap != null && classesMap.containsKey(finalClass) && parser.getValueByLabel("ClassName") !=null && !StringUtils.isEmpty(parser.getValueByLabel("ClassName"))){
						excelDataTO.setClassId(classesMap.get(finalClass));
					}
					if(parser.getValueByLabel("RollNumber") !=null && !StringUtils.isEmpty(parser.getValueByLabel("RollNumber"))){
						excelDataTO.setRollNumber(parser.getValueByLabel("RollNumber"));
					}
		      		if(excelDataTO != null){
		      			excelResults.add(excelDataTO);
		      		}else{
		      			continue;
		      		}
				}
				if(excelResults!= null && !excelResults.isEmpty()){
		    		isAdded = ExcelDataHandler.getInstance().isDataUploaded(excelResults,excelDataForm);
		    		if(isAdded) {
		    			// if success
		    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_SUCCESS);
		    			messages.add("messages", message);
		    			saveMessages(request, messages);
		    			resetFields(excelDataForm);
		    		}else {
		    			// if failure
		    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_FAILURE);
		    			errors.add("error",message);
			      		saveErrors(request, errors);
			      		resetFields(excelDataForm);
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
					excelDataForm.setSemister(excelDataForm.getTermNo());	
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
				excelDataForm.setErrorMessage(msg);
				excelDataForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			//to get Program Type details.
			if(ProgramTypeHandler.getInstance().getProgramType() != null){
				excelDataForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
			}
			getSemisterMap(excelDataForm, request);
			log.info("end of saveUploadData method in ExcelDataAction class.");
			return mapping.findForward(CMSConstants.EXCEL_UPLOAD);
		}	
	
	
	
	
	public ActionForward initChallanUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExcelDataForm excelDataForm = (ExcelDataForm)form; 
		try {
				setUserId(request,excelDataForm);
		}
			catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			excelDataForm.setErrorMessage(msg);
			excelDataForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//resetFields(excelDataForm);
		return mapping.findForward(CMSConstants.CHALLAN_UPLOAD);
	}
	
	
	
	
	
	/**
	 * This method is used to save the data from excel and UI.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward saveChallanUploadData(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ExcelDataForm excelDataForm = (ExcelDataForm)form; 
		log.info("call of saveUploadData method in ExcelDataAction class.");
		ActionMessages messages = new ActionMessages();
		try {
			 ActionErrors errors = excelDataForm.validate(mapping, request);
			//setUserId(request,excelDataForm);
			if (errors.isEmpty()) {
			FormFile myFile = excelDataForm.getThefile();
		    String contentType = myFile.getContentType();
		    	 
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    List<ExcelDataTO> excelResults; 
		   	boolean isAdded = false;
			ExcelDataTO excelDataTO = null;
			
			IExcelTransaction excelTransaction = new ExcelTransactionImpl();
			List<String> challanList= excelTransaction.getChallanList(excelDataForm.getApplicationYear());
			
			String extn="";
			int indx=myFile.getFileName().lastIndexOf(".");
			if(indx!=-1){
				extn=myFile.getFileName().substring(indx, myFile.getFileName().length());
			}
			
			//this is for xls format
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
	        	boolean duplicate=false;
	            for(int c = 0; c < cols;c++) {
	                cell = row.getCell((byte)c);
	                
	                if(cell != null && !StringUtils.isEmpty(cell.toString().trim())) {
	                	
	                	//this cell is for challan number is first cell
						if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())) {
							
							if(challanList!=null && challanList.size()!=0 && challanList.contains(cell.toString().trim())){
								duplicate=true;
								continue;
							}else{
								
								excelDataTO.setChallanNumber(cell.toString().trim());
								
							}	
							
						}
						
						//this cell is for challan date is second cell
						if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim()) && !duplicate) {
							excelDataTO.setChallanDate(CommonUtil.ConvertStringToSQLDate(cell.toString().trim()));
						}
	                   	
	                   	
	                 }
	            }
	            
	            
	            
		            if(excelDataTO!=null && excelDataTO.getChallanNumber()!=null && excelDataTO.getChallanDate()!=null){
		            	
		            	excelDataTO.setYear(excelDataForm.getApplicationYear());
	                   	excelDataTO.setUserId(excelDataForm.getUserId());
	                   	
		            	excelResults.add(excelDataTO);
		            }else{
		            	continue;
		            }
		    	}
		      	}//for loop close
	    
	    
	    
	    
		    	//	call of handler.
	    		isAdded = excelTransaction.updateChallan(excelResults);
	    		if(isAdded) {
	    			// if success
	    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_SUCCESS);
	    			messages.add("messages", message);
	    			saveMessages(request, messages);
	    			resetFields(excelDataForm);
	    		}else {
	    			// if failure
	    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_FAILURE);
	    			errors.add("error",message);
		      		saveErrors(request, errors);
		      		resetFields(excelDataForm);
	    		}
	    		
	    		
		    }
			//close xls format
			
			
			
			
			//this is for csv format
			else if(extn.equalsIgnoreCase(".CSV")){
		     	  
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
					
					if(parser.getValueByLabel("ChallanNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("ChallanNumber"))){
		      		if(challanList!=null && challanList.size()!=0 && challanList.contains(parser.getValueByLabel("ChallanNumber"))){
		      			continue;
		      		}else{
		      			
		      			excelDataTO.setChallanNumber(parser.getValueByLabel("ChallanNumber").toString().trim());
		      			
		      		}
					}else{
		      			continue;
		      		}
					
					if(parser.getValueByLabel("ChallanDate") != null && !StringUtils.isEmpty(parser.getValueByLabel("ChallanDate"))){
						
							excelDataTO.setChallanDate(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("ChallanDate").toString().trim()));
						
					}else{
		      			continue;
		      		}
					
					
					
					
					if(excelDataTO!=null && excelDataTO.getChallanNumber()!=null && excelDataTO.getChallanDate()!=null){
		      			
		      			excelDataTO.setYear(excelDataForm.getApplicationYear());
	                   	excelDataTO.setUserId(excelDataForm.getUserId());
	                   	
		      			excelResults.add(excelDataTO);
		      		}else{
		      			continue;
		      		}
		      		
				}//while loop close
				
				
				
				if(excelResults!= null && !excelResults.isEmpty()){
		    		isAdded = excelTransaction.updateChallan(excelResults);
		    		if(isAdded) {
		    			// if success
		    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_SUCCESS);
		    			messages.add("messages", message);
		    			saveMessages(request, messages);
		    			resetFields(excelDataForm);
		    		}else {
		    			// if failure
		    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_FAILURE);
		    			errors.add("error",message);
			      		saveErrors(request, errors);
			      		resetFields(excelDataForm);
		    		}
				}
				
				
				
			    }
			//close csv format
			
			
			else{
			    	// if upload doc is not excel and csv.
			      		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC);
			      		errors.add("error",message);
			      		saveErrors(request, errors);
				    }
				}else{
					//if errors are present.
					saveErrors(request, errors);
					excelDataForm.setSemister(excelDataForm.getTermNo());	
				}
			}catch (BusinessException businessException) {
				log.info("Exception saveUploadData");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				excelDataForm.setErrorMessage(msg);
				excelDataForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			
			log.info("end of saveUploadData method in ExcelDataAction class.");
			return mapping.findForward(CMSConstants.CHALLAN_UPLOAD);
			
		}	
	
	
	
	
	
	
	
	/**
	 * This method is used to convert file data based on content type.
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
		public void getSemisterMap(ExcelDataForm excelDataForm, HttpServletRequest request){
			if(excelDataForm.getCourseId() != null && !excelDataForm.getCourseId().isEmpty() && excelDataForm.getApplicationYear() != null && !excelDataForm.getApplicationYear().isEmpty()){
				int year = Integer.parseInt(excelDataForm.getApplicationYear());
				int courseId =  Integer.parseInt(excelDataForm.getCourseId());
				HttpSession session=request.getSession(false);
				Map<Integer,Integer> semisterMap = CommonAjaxHandler.getInstance().getSemistersByYearAndCourse(year,courseId);
					session.setAttribute("semisterMap", semisterMap);
				
			}
				
		}
		
		public ActionForward initChallanUploadForExam(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExcelDataForm excelDataForm = (ExcelDataForm)form; 
			try {
				excelDataForm.setExamType("Regular");
					setUserId(request,excelDataForm);
			}
				catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				excelDataForm.setErrorMessage(msg);
				excelDataForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			//resetFields(excelDataForm);
			return mapping.findForward(CMSConstants.CHALLAN_UPLOAD_EXAM);
		}
		
		public ActionForward saveChallanUploadDataForExam(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			ExcelDataForm excelDataForm = (ExcelDataForm)form; 
			log.info("call of saveChallanUploadDataForExam method in ExcelDataAction class.");
			ActionMessages messages = new ActionMessages();
			try {
				 ActionErrors errors = excelDataForm.validate(mapping, request);
						/*if(excelDataForm.getExamid()==0){
						errors.add("error", new ActionError("admissionFormForm.education.exam.required"));
						saveErrors(request, errors);
						}*/
				 
				//setUserId(request,excelDataForm);
				if (errors.isEmpty()) {
					/*if(excelDataForm.getExamid()==0){
					errors.add("error", new ActionError("admissionFormForm.education.exam.required"));
					saveErrors(request, errors);
					}*/
				FormFile myFile = excelDataForm.getThefile();
			    String contentType = myFile.getContentType();
			    	 
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<ExcelDataTO> excelResults; 
			   	boolean isAdded = false;
				ExcelDataTO excelDataTO = null;
				
				IExcelTransaction excelTransaction = new ExcelTransactionImpl();
				List<String> challanList= excelTransaction.getChallanListForExam(excelDataForm.getApplicationYear(),excelDataForm.getExamid());
				
				String extn="";
				int indx=myFile.getFileName().lastIndexOf(".");
				if(indx!=-1){
					extn=myFile.getFileName().substring(indx, myFile.getFileName().length());
				}
				
				//this is for xls format
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
		        	boolean duplicate=false;
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                
		                if(cell != null && !StringUtils.isEmpty(cell.toString().trim())) {
		                	
		                	//this cell is for challan number is first cell
							if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())) {
								
								if(challanList!=null && challanList.size()!=0 && challanList.contains(cell.toString().trim())){
									duplicate=true;
									continue;
								}else{
									
									excelDataTO.setChallanNumber(cell.toString().trim());
									
								}	
								
							}
							
							//this cell is for challan date is second cell
							if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim()) && !duplicate) {
								excelDataTO.setChallanDate(CommonUtil.ConvertStringToSQLDate(cell.toString().trim()));
							}
							//this cell is for challan date is second cell
							if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim()) && !duplicate) {
								excelDataTO.setAmount(Double.parseDouble(cell.toString().trim()));
							}
		                   	
		                   	
		                 }
		            }
		            
		            
		            
			            if(excelDataTO!=null && excelDataTO.getChallanNumber()!=null && excelDataTO.getChallanDate()!=null && excelDataTO.getAmount()!=0){
			            	
			            	excelDataTO.setYear(excelDataForm.getApplicationYear());
		                   	excelDataTO.setUserId(excelDataForm.getUserId());
		                   	/*excelDataTO.setExamId(excelDataForm.getExamid()+"");*/
		                   	
			            	excelResults.add(excelDataTO);
			            }else{
			            	continue;
			            }
			    	}
			      	}//for loop close
		    
		    
		    
		    
			    	//	call of handler.
		    		isAdded = ExcelDataHandler.getInstance().isAddedChallanExamFee(excelDataForm, excelResults);
		    		
		    		if(isAdded) {
		    			// if success
		    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_SUCCESS);
		    			messages.add("messages", message);
		    			saveMessages(request, messages);
		    			resetFields(excelDataForm);
		    		}else {
		    			// if failure
		    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_FAILURE);
		    			errors.add("error",message);
			      		saveErrors(request, errors);
			      		resetFields(excelDataForm);
		    		}
		    		
		    		
			    }
				//close xls format
				
				
				
				
				//this is for csv format
				else if(extn.equalsIgnoreCase(".CSV")){
			     	  
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
						
						if(parser.getValueByLabel("ChallanNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("ChallanNumber"))){
			      		if(challanList!=null && challanList.size()!=0 && challanList.contains(parser.getValueByLabel("ChallanNumber"))){
			      			continue;
			      		}else{
			      			
			      			excelDataTO.setChallanNumber(parser.getValueByLabel("ChallanNumber").toString().trim());
			      			
			      		}
						}else{
			      			continue;
			      		}
						
						if(parser.getValueByLabel("ChallanDate") != null && !StringUtils.isEmpty(parser.getValueByLabel("ChallanDate"))){
							
								excelDataTO.setChallanDate(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("ChallanDate").toString().trim()));
							
						}else{
			      			continue;
			      		}
						
						if(parser.getValueByLabel("Amount") != null && !StringUtils.isEmpty(parser.getValueByLabel("Amount"))){
							
							excelDataTO.setAmount(Double.parseDouble(parser.getValueByLabel("ChallanDate").toString().trim()));
						
					}else{
		      			continue;
		      		}
						
						
						
						
						if(excelDataTO!=null && excelDataTO.getChallanNumber()!=null && excelDataTO.getChallanDate()!=null && excelDataTO.getAmount()!=0){
			      			
			      			excelDataTO.setYear(excelDataForm.getApplicationYear());
		                   	excelDataTO.setUserId(excelDataForm.getUserId());
		                	excelDataTO.setExamId(excelDataForm.getExamid()+"");
		                   	
			      			excelResults.add(excelDataTO);
			      		}else{
			      			continue;
			      		}
			      		
					}//while loop close
					
					
					
					if(excelResults!= null && !excelResults.isEmpty()){
						isAdded = ExcelDataHandler.getInstance().isAddedChallanExamFee(excelDataForm, excelResults);
			    		if(isAdded) {
			    			// if success
			    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_SUCCESS);
			    			messages.add("messages", message);
			    			saveMessages(request, messages);
			    			resetFields(excelDataForm);
			    		}else {
			    			// if failure
			    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_FAILURE);
			    			errors.add("error",message);
				      		saveErrors(request, errors);
				      		resetFields(excelDataForm);
			    		}
					}
					
					
					
				    }
				//close csv format
				
				
				else{
				    	// if upload doc is not excel and csv.
				      		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC);
				      		errors.add("error",message);
				      		saveErrors(request, errors);
					    }
					}else{
						//if errors are present.
						saveErrors(request, errors);
						excelDataForm.setSemister(excelDataForm.getTermNo());	
					}
				}catch (BusinessException businessException) {
					log.info("Exception saveChallanUploadDataForExam");
					businessException.printStackTrace();
					String msgKey = super.handleBusinessException(businessException);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add(CMSConstants.MESSAGES, message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					exception.printStackTrace();
					excelDataForm.setErrorMessage(msg);
					excelDataForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				
				log.info("end of saveChallanUploadDataForExam method in ExcelDataAction class.");
				return mapping.findForward(CMSConstants.CHALLAN_UPLOAD_EXAM);
				
			}	
		
		public ActionForward initChallanUploadForFee(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExcelDataForm excelDataForm = (ExcelDataForm)form; 
			try {
				setUserId(request,excelDataForm);
			}
				catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				excelDataForm.setErrorMessage(msg);
				excelDataForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			//resetFields(excelDataForm);
			return mapping.findForward(CMSConstants.CHALLAN_UPLOAD_FEE);
		}
		
		public ActionForward saveChallanUploadDataForFee(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			ExcelDataForm excelDataForm = (ExcelDataForm)form; 
			log.info("call of saveChallanUploadDataForFee method in ExcelDataAction class.");
			ActionMessages messages = new ActionMessages();
			try {
				 ActionErrors errors = excelDataForm.validate(mapping, request);
						/*if(excelDataForm.getExamid()==0){
						errors.add("error", new ActionError("admissionFormForm.education.exam.required"));
						saveErrors(request, errors);
						}*/
				 
				//setUserId(request,excelDataForm);
				if (errors.isEmpty()) {
					/*if(excelDataForm.getExamid()==0){
					errors.add("error", new ActionError("admissionFormForm.education.exam.required"));
					saveErrors(request, errors);
					}*/
				FormFile myFile = excelDataForm.getThefile();
			    String contentType = myFile.getContentType();
			    	 
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    List<ExcelDataTO> excelResults; 
			   	boolean isAdded = false;
				ExcelDataTO excelDataTO = null;
				
				IExcelTransaction excelTransaction = new ExcelTransactionImpl();
				List<String> challanList= excelTransaction.getChallanListForFee();
				
				String extn="";
				int indx=myFile.getFileName().lastIndexOf(".");
				if(indx!=-1){
					extn=myFile.getFileName().substring(indx, myFile.getFileName().length());
				}
				
				//this is for xls format
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
		        	boolean duplicate=false;
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                
		                if(cell != null && !StringUtils.isEmpty(cell.toString().trim())) {
		                	
		                	//this cell is for challan number is first cell
							if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())) {
								
								if(challanList!=null && challanList.size()!=0 && challanList.contains(cell.toString().trim())){
									duplicate=true;
									continue;
								}else{
									
									excelDataTO.setChallanNumber(cell.toString().trim());
									
								}	
								
							}
							
							//this cell is for challan date is second cell
							if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim()) && !duplicate) {
								excelDataTO.setChallanDate(CommonUtil.ConvertStringToSQLDate(cell.toString().trim()));
							}
							//this cell is for challan date is second cell
							if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim()) && !duplicate) {
								excelDataTO.setAmount(Double.parseDouble(cell.toString().trim()));
							}
		                   	
		                   	
		                 }
		            }
		            
		            
		            
			            if(excelDataTO!=null && excelDataTO.getChallanNumber()!=null && excelDataTO.getChallanDate()!=null && excelDataTO.getAmount()!=0){
			            	
			            	//excelDataTO.setYear(excelDataForm.getApplicationYear());
		                   	excelDataTO.setUserId(excelDataForm.getUserId());
		                   	/*excelDataTO.setExamId(excelDataForm.getExamid()+"");*/
		                   	
			            	excelResults.add(excelDataTO);
			            }else{
			            	continue;
			            }
			    	}
			      	}//for loop close
		    
		    
		    
		    
			    	//	call of handler.
		    		isAdded = ExcelDataHandler.getInstance().isAddedChallanFee(excelDataForm, excelResults);
		    		
		    		if(isAdded) {
		    			// if success
		    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_SUCCESS);
		    			messages.add("messages", message);
		    			saveMessages(request, messages);
		    			resetFields(excelDataForm);
		    		}else {
		    			// if failure
		    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_FAILURE);
		    			errors.add("error",message);
			      		saveErrors(request, errors);
			      		resetFields(excelDataForm);
		    		}
		    		
		    		
			    }
				//close xls format
				
				
				
				
				//this is for csv format
				else if(extn.equalsIgnoreCase(".CSV")){
			     	  
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
						
						if(parser.getValueByLabel("ChallanNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("ChallanNumber"))){
			      		if(challanList!=null && challanList.size()!=0 && challanList.contains(parser.getValueByLabel("ChallanNumber"))){
			      			continue;
			      		}else{
			      			
			      			excelDataTO.setChallanNumber(parser.getValueByLabel("ChallanNumber").toString().trim());
			      			
			      		}
						}else{
			      			continue;
			      		}
						
						if(parser.getValueByLabel("ChallanDate") != null && !StringUtils.isEmpty(parser.getValueByLabel("ChallanDate"))){
							
								excelDataTO.setChallanDate(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("ChallanDate").toString().trim()));
							
						}else{
			      			continue;
			      		}
						
						if(parser.getValueByLabel("Amount") != null && !StringUtils.isEmpty(parser.getValueByLabel("Amount"))){
							
							excelDataTO.setAmount(Double.parseDouble(parser.getValueByLabel("ChallanDate").toString().trim()));
						
					}else{
		      			continue;
		      		}
						
						
						
						
						if(excelDataTO!=null && excelDataTO.getChallanNumber()!=null && excelDataTO.getChallanDate()!=null && excelDataTO.getAmount()!=0){
			      			
			      			excelDataTO.setYear(excelDataForm.getApplicationYear());
		                   	excelDataTO.setUserId(excelDataForm.getUserId());
		                	excelDataTO.setExamId(excelDataForm.getExamid()+"");
		                   	
			      			excelResults.add(excelDataTO);
			      		}else{
			      			continue;
			      		}
			      		
					}//while loop close
					
					
					
					if(excelResults!= null && !excelResults.isEmpty()){
						isAdded = ExcelDataHandler.getInstance().isAddedChallanExamFee(excelDataForm, excelResults);
			    		if(isAdded) {
			    			// if success
			    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_SUCCESS);
			    			messages.add("messages", message);
			    			saveMessages(request, messages);
			    			resetFields(excelDataForm);
			    		}else {
			    			// if failure
			    			ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC_FAILURE);
			    			errors.add("error",message);
				      		saveErrors(request, errors);
				      		resetFields(excelDataForm);
			    		}
					}
					
					
					
				    }
				//close csv format
				
				
				else{
				    	// if upload doc is not excel and csv.
				      		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_DOC);
				      		errors.add("error",message);
				      		saveErrors(request, errors);
					    }
					}else{
						//if errors are present.
						saveErrors(request, errors);
						excelDataForm.setSemister(excelDataForm.getTermNo());	
					}
				}catch (BusinessException businessException) {
					log.info("Exception saveChallanUploadDataForFee");
					businessException.printStackTrace();
					String msgKey = super.handleBusinessException(businessException);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add(CMSConstants.MESSAGES, message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					exception.printStackTrace();
					excelDataForm.setErrorMessage(msg);
					excelDataForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				
				log.info("end of saveChallanUploadDataForFee method in ExcelDataAction class.");
				return mapping.findForward(CMSConstants.CHALLAN_UPLOAD_FEE);
				
			}
}