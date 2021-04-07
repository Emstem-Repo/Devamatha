package com.kp.cms.to.fee;

import java.io.Serializable;

/**
 * 
 * Date 19/jan/2009
 * This is an TO class for FeeAccount.
 */
public class FeeAccountTO implements Serializable{
       
		/**
	 	* 
	 	*/
		private static final long serialVersionUID = 1L;
		private int id;
		private String name;
		private String divisionname;
		private String divisionid;
		private String code;
		private FeeDivisionTO feeDivisionTO;
		private String createdBy;
		private String modifiedBy;
		private String createdDate;
		private String lastModifiedDate;
		private String isActive;
		private String description1;
		private String description2;
		private String bankInfo;
		private String printAccName;
		private String verifiedBy;
		private String fileName;
		private String contentType;
		private String printPosition;
		
		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		public String getDivisionname() {
			return divisionname;
		}
		public void setDivisionname(String divisionname) {
			this.divisionname = divisionname;
		}
		public String getDivisionid() {
			return divisionid;
		}
		public void setDivisionid(String divisionid) {
			this.divisionid = divisionid;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public static long getSerialVersionUID() {
			return serialVersionUID;
		}
		public FeeDivisionTO getFeeDivisionTO() {
			return feeDivisionTO;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public String getModifiedBy() {
			return modifiedBy;
		}
		public String getCreatedDate() {
			return createdDate;
		}
		public String getLastModifiedDate() {
			return lastModifiedDate;
		}
		public String getIsActive() {
			return isActive;
		}
		public void setFeeDivisionTO(FeeDivisionTO feeDivisionTO) {
			this.feeDivisionTO = feeDivisionTO;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
		public void setLastModifiedDate(String lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
		}
		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}
		/**
		 * @return the bankInfo
		 */
		public String getBankInfo() {
			return bankInfo;
		}
		/**
		 * @param bankInfo the bankInfo to set
		 */
		public void setBankInfo(String bankInfo) {
			this.bankInfo = bankInfo;
		}
		/**
		 * @return the printAccName
		 */
		public String getPrintAccName() {
			return printAccName;
		}
		/**
		 * @param printAccName the printAccName to set
		 */
		public void setPrintAccName(String printAccName) {
			this.printAccName = printAccName;
		}
		/**
		 * @return the fileName
		 */
		public String getFileName() {
			return fileName;
		}
		/**
		 * @param fileName the fileName to set
		 */
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getPrintPosition() {
			return printPosition;
		}
		public void setPrintPosition(String printPosition) {
			this.printPosition = printPosition;
		}
		/**
		 * @return the contentType
		 */
		public String getContentType() {
			return contentType;
		}
		/**
		 * @param contentType the contentType to set
		 */
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		/**
		 * @return the verifiedBy
		 */
		public String getVerifiedBy() {
			return verifiedBy;
		}
		/**
		 * @param verifiedBy the verifiedBy to set
		 */
		public void setVerifiedBy(String verifiedBy) {
			this.verifiedBy = verifiedBy;
		}
		/**
		 * @return the description1
		 */
		public String getDescription1() {
			return description1;
		}
		/**
		 * @param description1 the description1 to set
		 */
		public void setDescription1(String description1) {
			this.description1 = description1;
		}
		/**
		 * @return the description2
		 */
		public String getDescription2() {
			return description2;
		}
		/**
		 * @param description2 the description2 to set
		 */
		public void setDescription2(String description2) {
			this.description2 = description2;
		}
		
}
