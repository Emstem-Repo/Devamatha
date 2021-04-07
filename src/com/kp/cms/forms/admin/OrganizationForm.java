package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.OrganizationTO;


public class OrganizationForm extends BaseActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String organizationName;
	private FormFile organizationLogo;
	private FormFile organizationtopBar;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String method;
	private boolean needApproval;
	private List<OrganizationTO> organizationList;
	private int id;
	private int value;
	private boolean sameUseridPassword;
	private boolean changePassword;
	private Organisation organisation;
	private boolean finalMeritListApproval;
	private FormFile organizationLogo1;
	private FormFile organizationtopBar1;
	private String timeLimit;
	private boolean openHonoursCourseLink;
	private boolean convocationRegistration;
	private boolean studentPhotoUpload;
	
	public boolean isStudentPhotoUpload() {
		return studentPhotoUpload;
	}

	public void setStudentPhotoUpload(boolean studentPhotoUpload) {
		this.studentPhotoUpload = studentPhotoUpload;
	}

	public int getValue() {
		return value;
	}
	
	public boolean isNeedApproval() {
		return needApproval;
	}

	public void setNeedApproval(boolean needApproval) {
		this.needApproval = needApproval;
	}

	public void setValue(int value) {
		this.value = value;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public List<OrganizationTO> getOrganizationList() {
		return organizationList;
	}
	public void setOrganizationList(List<OrganizationTO> organizationList) {
		this.organizationList = organizationList;
	}
	/**
	 * @return the sameUsertidPassword
	 */
	public boolean isSameUseridPassword() {
		return sameUseridPassword;
	}

	/**
	 * @param sameUsertidPassword the sameUsertidPassword to set
	 */
	public void setSameUseridPassword(boolean sameUseridPassword) {
		this.sameUseridPassword = sameUseridPassword;
	}

	/**
	 * @return the changePassword
	 */
	public boolean isChangePassword() {
		return changePassword;
	}

	/**
	 * @param changePassword the changePassword to set
	 */
	public void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

	
	public boolean isFinalMeritListApproval() {
		return finalMeritListApproval;
	}

	public void setFinalMeritListApproval(boolean finalMeritListApproval) {
		this.finalMeritListApproval = finalMeritListApproval;
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

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
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

	public void clear()
	{
		this.organizationName=null;
		this.organizationLogo=null;
		this.organizationtopBar=null;
		this.addressLine1=null;
		this.addressLine2=null;
		this.addressLine3=null;
		this.needApproval = false;
		this.changePassword = false;
		this.sameUseridPassword = false;
		this.finalMeritListApproval = false;
		this.organizationLogo1=null;
		this.organizationtopBar1=null;
		this.timeLimit=null;
		this.openHonoursCourseLink=false;
		this.convocationRegistration=false;
		this.studentPhotoUpload=false;
	}	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

}
