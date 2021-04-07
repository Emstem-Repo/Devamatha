package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.StudentUploadPhoto;
import com.kp.cms.bo.studentLogin.StudentPhotoUpload;
import com.kp.cms.forms.admin.StudentUploadPhotoForm;


public interface IStudentUploadPhotoTransaction {
	public int uploadStudentPhotos(List<StudentUploadPhoto> studentUploadPhotoBOList, String year, StudentUploadPhotoForm studentUploadPhotoForm) throws Exception;

	public AdmAppln validateAdmAppln(String applnNo, String year) throws Exception;

	public boolean uploadFinalYearStudentPhoto(StudentPhotoUpload upload)throws Exception;

	public List<Object[]> getClassesFromStudentUploadPhotos() throws Exception;

	public List<StudentPhotoUpload> getStudentIdByClassId(StudentUploadPhotoForm photoForm)throws Exception;

	public boolean updateFinalYearStudentPhotos(StudentUploadPhotoForm uploadPhotoForm)throws Exception;

	public StudentPhotoUpload getStudentPhotoUploadByStudentId(int studentId)throws Exception;

	public boolean updateStudentPhotoUploadList(List<StudentPhotoUpload> photoUploadList)throws Exception;

	public List<StudentPhotoUpload> getRejectedStudentPhotos()throws Exception;

	public StudentPhotoUpload checkStudentPhotoIsApprovedOrRejected(String studentId)throws Exception;
}
