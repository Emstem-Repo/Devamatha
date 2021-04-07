package com.kp.cms.helpers.admin;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentUploadPhoto;
import com.kp.cms.bo.studentLogin.StudentPhotoUpload;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.StudentUploadPhotoForm;
import com.kp.cms.to.admin.StudentUploadPhotoTO;
public class StudentUploadPhotoHelper {
	
		public static volatile StudentUploadPhotoHelper studentUploadPhotoHelper = null;

		public static StudentUploadPhotoHelper getInstance() {
			if (studentUploadPhotoHelper == null) {
				studentUploadPhotoHelper = new StudentUploadPhotoHelper();
				return studentUploadPhotoHelper;
			}
			return studentUploadPhotoHelper;
		}

		/**
		 * Creating TO's from BO
		 * @param admittedThroughList
		 * @return
		 */
		public List<StudentUploadPhotoTO> copyAdmittedThroughBosToTos(List<StudentUploadPhoto> studentUploadPhotoList) {
			List<StudentUploadPhotoTO> studentUploadPhotoTOList = new ArrayList<StudentUploadPhotoTO>();
			Iterator<StudentUploadPhoto> i = studentUploadPhotoList.iterator();
			StudentUploadPhoto studentUploadPhoto;
			StudentUploadPhotoTO studentUploadPhotoTO;
			while (i.hasNext()) {
				studentUploadPhotoTO = new StudentUploadPhotoTO();
				studentUploadPhoto = i.next();
				studentUploadPhotoTO.setId(studentUploadPhoto.getId());
				
				studentUploadPhotoTOList.add(studentUploadPhotoTO);
			}
			return studentUploadPhotoTOList;
		}
		
		/**
		 * 
		 * @param  AdmittedThroughForm creates BO from admittedThroughForm 
		 *            
		 * @return AdmittedThrough BO object
		 */

		public StudentUploadPhoto populateAdmittedThroughDataFormForm(
				StudentUploadPhotoForm studentUploadPhotoForm ) throws Exception {
			StudentUploadPhoto studentUploadPhoto = new StudentUploadPhoto();
			studentUploadPhoto.setCreatedBy(studentUploadPhotoForm.getUserId());
			studentUploadPhoto.setCreatedDate(new Date());
			studentUploadPhoto.setIsActive(true);  //in add and edit we can set this as true
			return studentUploadPhoto;
		}
		
		public List<StudentUploadPhoto> copyAdmittedThroughTosToBos(List<StudentUploadPhotoTO> studentUploadPhotoList, StudentUploadPhotoForm studentUploadPhotoForm) {
			List<StudentUploadPhoto> studentUploadPhotoBOList = new ArrayList<StudentUploadPhoto>();
			Iterator<StudentUploadPhotoTO> i = studentUploadPhotoList.iterator();
			StudentUploadPhotoTO studentUploadPhotoTO;
			StudentUploadPhoto studentUploadPhoto;
			while (i.hasNext()) {
				studentUploadPhoto = new StudentUploadPhoto();
				studentUploadPhotoTO = i.next();
				if(studentUploadPhotoForm.getIsApplicationNo().equalsIgnoreCase("ApplicationNo"))
					studentUploadPhoto.setApplnNo(String.valueOf(studentUploadPhotoTO.getApplnNo()));
				else
					studentUploadPhoto.setApplnNo(studentUploadPhotoTO.getRegNo());
				studentUploadPhoto.setContentType(studentUploadPhotoTO.getContentType());
				studentUploadPhoto.setCreatedBy(studentUploadPhotoTO.getCreatedBy());
				studentUploadPhoto.setCreatedDate(studentUploadPhotoTO.getCreatedDate());
				studentUploadPhoto.setDoc(studentUploadPhotoTO.getDoc());
				studentUploadPhoto.setFileName(studentUploadPhotoTO.getFileName());
				studentUploadPhoto.setIsActive(studentUploadPhotoTO.getIsActive());
				studentUploadPhoto.setLastModifiedDate(studentUploadPhotoTO.getLastModifiedDate());
				
				
				studentUploadPhotoBOList.add(studentUploadPhoto);
			}
			return studentUploadPhotoBOList;
		}

		/**
		 * @param studentUploadPhotoForm
		 * @param studentId
		 * @return
		 * @throws Exception
		 */
		public StudentPhotoUpload createStudentPhotoUploadBO(StudentUploadPhotoForm studentUploadPhotoForm, String studentId)throws Exception {
					StudentPhotoUpload upload=new StudentPhotoUpload();
				    FileOutputStream fos = new FileOutputStream(CMSConstants.FINAL_YEAR_STUDENT_PHOTO_FOLDER_PATH+studentId+".jpg");
					fos.write(studentUploadPhotoForm.getPhoto());
					fos.close();
					Student student=new Student();
					student.setId(Integer.parseInt(studentId));
					upload.setStudent(student);
					upload.setIsApproved(false);
					upload.setIsRejected(false);
					upload.setIsActive(true);
					upload.setCreatedBy(studentUploadPhotoForm.getUserId());
					upload.setCreatedDate(new Date());
			 return upload;
		}

}
