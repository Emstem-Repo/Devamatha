package com.kp.cms.to.employee;
import java.util.Date;

import org.apache.struts.upload.FormFile;

public class GuestImagesTO {
		private int id;
//		private GuestFaculty guest;
		private FormFile empPhoto;
		private byte[] photoBytes;
		private String createdBy;
		private Date createdDate;
		private String modifiedBy;
		private Date lastModifiedDate;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public String getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public Date getLastModifiedDate() {
			return lastModifiedDate;
		}
		public void setLastModifiedDate(Date lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
		}
		public byte[] getPhotoBytes() {
			return photoBytes;
		}
		public void setPhotoBytes(byte[] photoBytes) {
			this.photoBytes = photoBytes;
		}
		public FormFile getEmpPhoto() {
			return empPhoto;
		}
		public void setEmpPhoto(FormFile empPhoto) {
			this.empPhoto = empPhoto;
		}
//		public GuestFaculty getGuest() {
//			return guest;
//		}
//		public void setGuest(GuestFaculty guest) {
//			this.guest = guest;
//		}
		

	}



