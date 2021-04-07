	package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.employee.EmployeeUploadPhoto;
import com.kp.cms.to.employee.EmployeeUploadPhotoTO;
	
	public class EmployeeUploadPhotoHelper {
		private static final Log log = LogFactory.getLog(EmployeeUploadPhotoHelper.class);
		public static volatile EmployeeUploadPhotoHelper employeeUploadPhotoHelper=null;
		public static EmployeeUploadPhotoHelper getInstance(){
			if(employeeUploadPhotoHelper == null){
				employeeUploadPhotoHelper = new EmployeeUploadPhotoHelper();
				return employeeUploadPhotoHelper;
			}
			return employeeUploadPhotoHelper;
			
		}
		/**
		 * @param uploadPhotoTOs
		 * @return
		 */
		public List<EmployeeUploadPhoto> copyTOToBO(List<EmployeeUploadPhotoTO> uploadPhotoTOs) {
			 List<EmployeeUploadPhoto> employeeUploadPhotos =new ArrayList<EmployeeUploadPhoto>();
			 Iterator<EmployeeUploadPhotoTO> iterator=uploadPhotoTOs.iterator();
			 EmployeeUploadPhotoTO to;
			 EmployeeUploadPhoto employeeUploadPhoto;
			 while (iterator.hasNext()) {
				  employeeUploadPhoto=new EmployeeUploadPhoto();
				  to=iterator.next();
				  employeeUploadPhoto.setFingerPrintId(to.getFingerPrintId());
				  employeeUploadPhoto.setPhoto(to.getPhoto());
				  employeeUploadPhoto.setModifiedBy(to.getModifiedBy());
				  employeeUploadPhoto.setLastModifiedDate(to.getLastModifiedDate());
				  employeeUploadPhotos.add(employeeUploadPhoto);
			}
			return employeeUploadPhotos;
		}
		
	}
