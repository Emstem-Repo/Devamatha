package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.studentLogin.StudentPhotoUpload;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentUploadPhotoTO;

@SuppressWarnings("serial")
public class StudentUploadPhotoForm extends BaseActionForm {

	private String method;
	private String isApplicationNo;
	private FormFile studentPhoto;
	private byte[] photo;
	private String studentClassId;
	private Map<Integer, String> photoClassMap;
	private List<StudentUploadPhotoTO> uploadPhotoTOList;
	private String rejectedReason;
	private String uploadPhotoDetails;
	private String studentImageName;
	private List<Integer> studentIdList;
	private int lengthOfStudentPhotos;
	
	



	public int getLengthOfStudentPhotos() {
		return lengthOfStudentPhotos;
	}

	public void setLengthOfStudentPhotos(int lengthOfStudentPhotos) {
		this.lengthOfStudentPhotos = lengthOfStudentPhotos;
	}

	public List<Integer> getStudentIdList() {
		return studentIdList;
	}

	public void setStudentIdList(List<Integer> studentIdList) {
		this.studentIdList = studentIdList;
	}

	public String getStudentImageName() {
		return studentImageName;
	}

	public void setStudentImageName(String studentImageName) {
		this.studentImageName = studentImageName;
	}

	public String getUploadPhotoDetails() {
		return uploadPhotoDetails;
	}

	public void setUploadPhotoDetails(String uploadPhotoDetails) {
		this.uploadPhotoDetails = uploadPhotoDetails;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public List<StudentUploadPhotoTO> getUploadPhotoTOList() {
		return uploadPhotoTOList;
	}

	public void setUploadPhotoTOList(List<StudentUploadPhotoTO> uploadPhotoTOList) {
		this.uploadPhotoTOList = uploadPhotoTOList;
	}

	public Map<Integer, String> getPhotoClassMap() {
		return photoClassMap;
	}

	public void setPhotoClassMap(Map<Integer, String> photoClassMap) {
		this.photoClassMap = photoClassMap;
	}

	public String getStudentClassId() {
		return studentClassId;
	}

	public void setStudentClassId(String studentClassId) {
		this.studentClassId = studentClassId;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public FormFile getStudentPhoto() {
		return studentPhoto;
	}

	public void setStudentPhoto(FormFile studentPhoto) {
		this.studentPhoto = studentPhoto;
	}

	public String getIsApplicationNo() {
		return isApplicationNo;
	}

	public void setIsApplicationNo(String isApplicationNo) {
		this.isApplicationNo = isApplicationNo;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
