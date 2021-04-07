package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.struts.upload.FormFile;


@SuppressWarnings("serial")
public class OrganizationTO implements Serializable{
	
	private String organizationName;
	private FormFile organizationLogo;
	private FormFile organizationtopBar;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private int id;
	private String organizationTopBarName;
	private String organizationLogoName;
	private boolean logoPresent;
	private boolean topbarPresent;
	private boolean needApproval;
	private String createdBy;
	private String modifiedBy;
	private Boolean sameUseridPassword;
	private Boolean changePassword;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private String logoName;
	private String topbarName;
	private String logoContentType;
	private String topbarContentType;
	private String isActive;
	private String needFinalApprival;
	private String useridPassword;
	private String enableChangePassword;
	private boolean finalMeritListApproval;
	private String dispFinalMeritApproval;
	private FormFile organizationLogo1;
	private FormFile organizationtopBar1;
	private String organizationTopBarName1;
	private String organizationLogoName1;
	private boolean logoPresent1;
	private boolean topbarPresent1;
	private List<ExtracurricularActivityTO> extracurriculars;
	private byte[] logo;
	private int timeLimit;
	private boolean openHonoursCourseLink;
	private boolean convocationRegistration;
	private boolean studentPhotoUpload;

	
	
	public OrganizationTO(){
		
	}
	
	public OrganizationTO(String organizationName, FormFile organizationLogo,
			FormFile organizationtopBar, String addressLine1,
			String addressLine2, String addressLine3, int id,
			String organizationTopBarName, String organizationLogoName,
			boolean logoPresent, boolean topbarPresent, boolean needApproval,
			String createdBy, String modifiedBy, Boolean sameUseridPassword,
			Boolean changePassword, String name, Date createdDate,
			Date lastModifiedDate, String logoName, String topbarName,
			String logoContentType, String topbarContentType, String isActive,
			String needFinalApprival, String useridPassword,
			String enableChangePassword, boolean finalMeritListApproval,
			String dispFinalMeritApproval, FormFile organizationLogo1,
			FormFile organizationtopBar1, String organizationTopBarName1,
			String organizationLogoName1, boolean logoPresent1,
			boolean topbarPresent1,
			List<ExtracurricularActivityTO> extracurriculars, byte[] logo,
			int timeLimit, boolean openHonoursCourseLink,
			boolean convocationRegistration, boolean studentPhotoUpload) {
		super();
		this.organizationName = organizationName;
		this.organizationLogo = organizationLogo;
		this.organizationtopBar = organizationtopBar;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.id = id;
		this.organizationTopBarName = organizationTopBarName;
		this.organizationLogoName = organizationLogoName;
		this.logoPresent = logoPresent;
		this.topbarPresent = topbarPresent;
		this.needApproval = needApproval;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.sameUseridPassword = sameUseridPassword;
		this.changePassword = changePassword;
		this.name = name;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.logoName = logoName;
		this.topbarName = topbarName;
		this.logoContentType = logoContentType;
		this.topbarContentType = topbarContentType;
		this.isActive = isActive;
		this.needFinalApprival = needFinalApprival;
		this.useridPassword = useridPassword;
		this.enableChangePassword = enableChangePassword;
		this.finalMeritListApproval = finalMeritListApproval;
		this.dispFinalMeritApproval = dispFinalMeritApproval;
		this.organizationLogo1 = organizationLogo1;
		this.organizationtopBar1 = organizationtopBar1;
		this.organizationTopBarName1 = organizationTopBarName1;
		this.organizationLogoName1 = organizationLogoName1;
		this.logoPresent1 = logoPresent1;
		this.topbarPresent1 = topbarPresent1;
		this.extracurriculars = extracurriculars;
		this.logo = logo;
		this.timeLimit = timeLimit;
		this.openHonoursCourseLink = openHonoursCourseLink;
		this.convocationRegistration = convocationRegistration;
		this.studentPhotoUpload = studentPhotoUpload;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public FormFile getOrganizationLogo() {
		return organizationLogo;
	}
	public void setOrganizationLogo(FormFile organizationLogo) {
		this.organizationLogo = organizationLogo;
	}
	public FormFile getOrganizationtopBar() {
		return organizationtopBar;
	}
	public void setOrganizationtopBar(FormFile organizationtopBar) {
		this.organizationtopBar = organizationtopBar;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrganizationTopBarName() {
		return organizationTopBarName;
	}
	public void setOrganizationTopBarName(String organizationTopBarName) {
		this.organizationTopBarName = organizationTopBarName;
	}
	public String getOrganizationLogoName() {
		return organizationLogoName;
	}
	public void setOrganizationLogoName(String organizationLogoName) {
		this.organizationLogoName = organizationLogoName;
	}
	public boolean isLogoPresent() {
		return logoPresent;
	}
	public void setLogoPresent(boolean logoPresent) {
		this.logoPresent = logoPresent;
	}
	public boolean isTopbarPresent() {
		return topbarPresent;
	}
	public void setTopbarPresent(boolean topbarPresent) {
		this.topbarPresent = topbarPresent;
	}
	public List<ExtracurricularActivityTO> getExtracurriculars() {
		return extracurriculars;
	}
	public void setExtracurriculars(List<ExtracurricularActivityTO> extracurriculars) {
		this.extracurriculars = extracurriculars;
	}
	public boolean isNeedApproval() {
		return needApproval;
	}
	public void setNeedApproval(boolean needApproval) {
		this.needApproval = needApproval;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public Date getLastModifiedDate() {
		return (Date)lastModifiedDate.clone();
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}
	public String getLogoName() {
		return logoName;
	}
	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}
	public String getTopbarName() {
		return topbarName;
	}
	public void setTopbarName(String topbarName) {
		this.topbarName = topbarName;
	}
	public String getLogoContentType() {
		return logoContentType;
	}
	public void setLogoContentType(String logoContentType) {
		this.logoContentType = logoContentType;
	}
	public String getTopbarContentType() {
		return topbarContentType;
	}
	public void setTopbarContentType(String topbarContentType) {
		this.topbarContentType = topbarContentType;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getNeedFinalApprival() {
		return needFinalApprival;
	}
	public void setNeedFinalApprival(String needFinalApprival) {
		this.needFinalApprival = needFinalApprival;
	}
	/**
	 * @return the sameUseridPassword
	 */
	public Boolean getSameUseridPassword() {
		return sameUseridPassword;
	}
	/**
	 * @param sameUseridPassword the sameUseridPassword to set
	 */
	public void setSameUseridPassword(Boolean sameUseridPassword) {
		this.sameUseridPassword = sameUseridPassword;
	}
	/**
	 * @return the changePassword
	 */
	public Boolean getChangePassword() {
		return changePassword;
	}
	/**
	 * @param changePassword the changePassword to set
	 */
	public void setChangePassword(Boolean changePassword) {
		this.changePassword = changePassword;
	}
	public String getUseridPassword() {
		return useridPassword;
	}
	public String getEnableChangePassword() {
		return enableChangePassword;
	}
	public void setUseridPassword(String useridPassword) {
		this.useridPassword = useridPassword;
	}
	public void setEnableChangePassword(String enableChangePassword) {
		this.enableChangePassword = enableChangePassword;
	}
	public boolean isFinalMeritListApproval() {
		return finalMeritListApproval;
	}
	public void setFinalMeritListApproval(boolean finalMeritListApproval) {
		this.finalMeritListApproval = finalMeritListApproval;
	}
	public String getDispFinalMeritApproval() {
		return dispFinalMeritApproval;
	}
	public void setDispFinalMeritApproval(String dispFinalMeritApproval) {
		this.dispFinalMeritApproval = dispFinalMeritApproval;
	}
	public FormFile getOrganizationLogo1() {
		return organizationLogo1;
	}
	public void setOrganizationLogo1(FormFile organizationLogo1) {
		this.organizationLogo1 = organizationLogo1;
	}
	public FormFile getOrganizationtopBar1() {
		return organizationtopBar1;
	}
	public void setOrganizationtopBar1(FormFile organizationtopBar1) {
		this.organizationtopBar1 = organizationtopBar1;
	}
	public String getOrganizationTopBarName1() {
		return organizationTopBarName1;
	}
	public void setOrganizationTopBarName1(String organizationTopBarName1) {
		this.organizationTopBarName1 = organizationTopBarName1;
	}
	public String getOrganizationLogoName1() {
		return organizationLogoName1;
	}
	public void setOrganizationLogoName1(String organizationLogoName1) {
		this.organizationLogoName1 = organizationLogoName1;
	}
	public boolean isLogoPresent1() {
		return logoPresent1;
	}
	public void setLogoPresent1(boolean logoPresent1) {
		this.logoPresent1 = logoPresent1;
	}
	public boolean isTopbarPresent1() {
		return topbarPresent1;
	}
	public void setTopbarPresent1(boolean topbarPresent1) {
		this.topbarPresent1 = topbarPresent1;
	}
	public byte[] getLogo() {
		return (byte[])logo.clone();
	}
	public void setLogo(byte[] logo) {
		this.logo = (byte[])logo.clone();
	}
	public int getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	public void setOpenHonoursCourseLink(boolean openHonoursCourseLink) {
		this.openHonoursCourseLink = openHonoursCourseLink;
	}
	public boolean isOpenHonoursCourseLink() {
		return openHonoursCourseLink;
	}
	public boolean isConvocationRegistration() {
		return convocationRegistration;
	}
	public void setConvocationRegistration(boolean convocationRegistration) {
		this.convocationRegistration = convocationRegistration;
	}
	public boolean isStudentPhotoUpload() {
		return studentPhotoUpload;
	}
	public void setStudentPhotoUpload(boolean studentPhotoUpload) {
		this.studentPhotoUpload = studentPhotoUpload;
	}


}