package com.kp.cms.actions.employee;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admission.SmartCardNumberUploadAction;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.EmployeeSmartCardUploadForm;
import com.kp.cms.handlers.employee.EmployeeSmartCardUploadHandler;

public class EmployeeSmartCardUploadAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(SmartCardNumberUploadAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEmpSmartCardUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initEmpSmartCardUpload input");
		EmployeeSmartCardUploadForm empSmartCardUploadForm = (EmployeeSmartCardUploadForm)form;
		empSmartCardUploadForm.resetFields();
		log.info("Leaving initEmpSmartCardUpload input");
		return mapping.findForward(CMSConstants.EMP_SMART_CARD_UPLOAD_NO);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadEmpSmartCardNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered uploadEmpSmartCardUpload input");
		EmployeeSmartCardUploadForm empSmartCardUploadForm = (EmployeeSmartCardUploadForm)form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request, empSmartCardUploadForm);
		try{
			if(empSmartCardUploadForm.getTheFile() == null){
				ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    		return mapping.findForward(CMSConstants.EMP_SMART_CARD_UPLOAD_NO);
			}
			FormFile myFile = empSmartCardUploadForm.getTheFile();
		    String contentType = myFile.getContentType();
		    String fileName    = myFile.getFileName();
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = EmployeeSmartCardUploadAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    List<Employee> list= null;
		    //if the uploading file is Excel file
		    if(fileName.endsWith(".xls")){
		    	Map<String,Employee> map=EmployeeSmartCardUploadHandler.getInstance().getFingerPrintIds();
		    	byte[] fileData = myFile.getFileData();
		    	String source1=prop.getProperty(CMSConstants.UPLOAD_SMART_CARD_NUMBER);
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
			String source=prop.getProperty(CMSConstants.UPLOAD_SMART_CARD_NUMBER);
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
		    list= new ArrayList<Employee>();
		    for(int r = 1; r < rows; r++) {
		    	  row = sheet.getRow(r);
		    	  Employee employee= new Employee();
		    	  if(row != null) {
			            for(int c = 0; c < cols;c++) {
			                cell = row.getCell((byte)c);
			                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
			                	String cell2=cell.toString();
			                	
								if(cell.getCellNum() == 0){
									if(!StringUtils.isEmpty(cell2)){
										if(map.containsKey(removeFileExtension(cell2.trim()))){
		                   			    Employee emp= map.get(removeFileExtension(cell2.trim()));
		                   			    employee.setId(emp.getId());
		                   			    employee.setFingerPrintId(removeFileExtension(cell2.trim()));
										}
		                   			else{
			        					break;
			                		}
									}
			                   	}
								if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell2)){
									employee.setBankAccNo(cell2.trim());
								}
								if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell2)){
									employee.setSmartCardNo(cell2.trim());
								}
								
			                }
			            }
			            	if(employee!=null && employee.getId()!=0){
			            		list.add(employee);
			            		}
			          
			        	}else {
		        		continue;		        	
		        		}
		    }
		    String UserId=empSmartCardUploadForm.getUserId();
		    if(list!=null && list.size()>0){
		    	isAdded=EmployeeSmartCardUploadHandler.getInstance().addEmpSmartCardNo(list,UserId);
		    }
		    if(isAdded){
		    	//if adding is success
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_SUCCESS);
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
		    }else{
		    	//if adding is failure
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_FAILURE);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
		    }
		    }else{
		    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
		    
		    }
		}catch (BusinessException businessException) {
			log.info("Exception uploadEmpSmartCardUpload");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			empSmartCardUploadForm.setErrorMessage(msg);
			empSmartCardUploadForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving uploadEmpSmartCardUpload input");
		return mapping.findForward(CMSConstants.EMP_SMART_CARD_UPLOAD_NO);
	}
	
/**
 * @author christ
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
}
