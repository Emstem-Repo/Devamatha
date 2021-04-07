	package com.kp.cms.handlers.employee;
	
	import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.employee.EmployeeUploadPhotoAction;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmployeeUploadPhoto;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.EmployeeUploadPhotoForm;
import com.kp.cms.helpers.employee.EmployeeUploadPhotoHelper;
import com.kp.cms.to.employee.EmployeeUploadPhotoTO;
import com.kp.cms.transactionsimpl.employee.EmployeeUploadPhotoTxnImpl;
import com.kp.cms.transactionsimpl.employee.IEmployeeUploadPhotoTransaction;
	
	public class EmployeeUploadPhotoHandler {
		private static final Log log = LogFactory.getLog(EmployeeUploadPhotoHandler.class);
	public static volatile EmployeeUploadPhotoHandler employeeUploadPhotoHandler=null;
	public static EmployeeUploadPhotoHandler getInstance(){
			if(employeeUploadPhotoHandler == null){
				employeeUploadPhotoHandler = new EmployeeUploadPhotoHandler();
				return employeeUploadPhotoHandler;
			}
			return employeeUploadPhotoHandler;
			
		}
	/**
	 * @param employeeUploadPhotoForm
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public boolean uploadPhotos(EmployeeUploadPhotoForm employeeUploadPhotoForm,HttpServletRequest request) throws Exception {
		boolean flag=false;
		List<EmployeeUploadPhotoTO> uploadPhotoTOs= new ArrayList<EmployeeUploadPhotoTO>();
		EmployeeUploadPhotoTO employeeUploadPhotoTO;
		String relativePath;
		
		Properties prop = new Properties();
		InputStream in = EmployeeUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		 prop.load(in);
	        String path=prop.getProperty("knowledgepro.employee.upload.image.path");
	        relativePath=path;
	        File f = new File(relativePath);
	        
	        File[] filearr =  f.listFiles();
	        log.error("relativePath********" + relativePath );
	        log.error("filearr.length   " + filearr);
	        if(filearr.length!=0){
	        	
	    		int maxPhotoSize = 0;
    			maxPhotoSize = Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
	    		
	        	for(int j=0;j<filearr.length;j++){
	        		File f3=filearr[j];
	        		if(maxPhotoSize > f3.length()){
	        			employeeUploadPhotoTO= new EmployeeUploadPhotoTO();
	        			byte[] b = new byte[(int)f3.length ()];;
	        			FileInputStream fis=new FileInputStream(f3);
	        			// convert image into byte array
	        			fis.read(b);
	        			employeeUploadPhotoTO.setFingerPrintId(removeFileExtension(f3.getName()));
	        			employeeUploadPhotoTO.setPhoto(b);
	        			employeeUploadPhotoTO.setModifiedBy(employeeUploadPhotoForm.getUserId());
	        			employeeUploadPhotoTO.setLastModifiedDate(new Date());			
	        			uploadPhotoTOs.add(employeeUploadPhotoTO);
	        		}
	        	}
	        	 log.error(uploadPhotoTOs + "uploadPhotoTOs" + "size" + uploadPhotoTOs.size());
	        	 int uploadPhotos=uploadEmployeePhotos(uploadPhotoTOs);
	        	 log.error(uploadPhotos + "uploadPhotos");
	        	 IEmployeeUploadPhotoTransaction transaction= EmployeeUploadPhotoTxnImpl.getInstance();
	        	 
	             if (uploadPhotos!=0) {
	      			// success .
	          	   File f1 = new File(relativePath);
	      	       File[] filearr1 =  f1.listFiles();
	      	       for(int j=0;j<filearr1.length;j++){
	      	   		File f3=filearr[j];
	      	   		Employee employee = transaction.getEmployee(removeFileExtension(f3.getName()));
	      	   		if(employee != null){
	      	   			if(maxPhotoSize > f3.length()){
	      	   				if(f3.delete()){
	      	   					int i=0;
	      	   				}		   		
	      	   			}
	      	   		}
	      	       }
	      	       flag=true;
	      	       }
	             }
	              else{
	              	throw new NullPointerException();
	              }
	        	return flag;
	}
	/**
	 * @param uploadPhotoTOs
	 * to insert images into the database
	 * @return
	 */
	private int uploadEmployeePhotos(List<EmployeeUploadPhotoTO> uploadPhotoTOs) throws Exception{
		List<EmployeeUploadPhoto> employeeUploadPhotoList=EmployeeUploadPhotoHelper.getInstance().copyTOToBO(uploadPhotoTOs);
		log.error("employeeUploadPhotoList" + employeeUploadPhotoList.size());
		IEmployeeUploadPhotoTransaction transaction= EmployeeUploadPhotoTxnImpl.getInstance();
		return transaction.uploadEmployeePhotos(employeeUploadPhotoList);
	}
	/**
	 * @param name
	 * @return
	 */
	public String removeFileExtension(String fileName)
	{ 
	if(null != fileName && fileName.contains("."))
	{
	return fileName.substring(0, fileName.lastIndexOf("."));
	}
	return null;
	}
	}
