package com.kp.cms.forms.fee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeAdditionalAccountAssignmentTO;
import com.kp.cms.to.fee.FeeAdditionalTO;

/**
 * 
 * Date 20/jan/2009
 * This is a Form for Fee additional.
 */
public class FeeAdditionalForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;
	private String id;
	private String feeGroupId;
	private Map<Integer,String> feeGroupMap;
	private Map<Integer,String> feeAccountsMap;
	private Map<Integer,String> feeHeadingsMap;
	private String feeGroupName;
	private FeeAdditionalTO FeeAdditionalTo;
	private List<FeeAdditionalAccountAssignmentTO> feeAssignmentList;
	private List<FeeAdditionalTO> feeList;
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the feeAccountsMap
	 */
	public Map<Integer, String> getFeeAccountsMap() {
		return feeAccountsMap;
	}

	/**
	 * @param feeAccountsMap the feeAccountsMap to set
	 */
	public void setFeeAccountsMap(Map<Integer, String> feeAccountsMap) {
		this.feeAccountsMap = feeAccountsMap;
	}

	/**
	 * @return the feeHeadingsMap
	 */
	public Map<Integer, String> getFeeHeadingsMap() {
		return feeHeadingsMap;
	}

	/**
	 * @param feeHeadingsMap the feeHeadingsMap to set
	 */
	public void setFeeHeadingsMap(Map<Integer, String> feeHeadingsMap) {
		this.feeHeadingsMap = feeHeadingsMap;
	}
	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	/**
	 * 
	 * @param mapping
	 * @param request
	 */
	public void clear() {
		// TODO Auto-generated method stub
		this.feeGroupId = null;
				
	}
	
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
	return actionErrors;
	}

	/**
	 * @return the feeGroupId
	 */
	public String getFeeGroupId() {
		return feeGroupId;
	}

	/**
	 * @param feeGroupId the feeGroupId to set
	 */
	public void setFeeGroupId(String feeGroupId) {
		this.feeGroupId = feeGroupId;
	}

	/**
	 * @return the feeGroupMap
	 */
	public Map<Integer,String> getFeeGroupMap() {
		return feeGroupMap;
	}

	/**
	 * @param feeGroupMap the feeGroupMap to set
	 */
	public void setFeeGroupMap(Map<Integer,String> feeGroupMap) {
		this.feeGroupMap = feeGroupMap;
	}

	/**
	 * @return the feeGroupName
	 */
	public String getFeeGroupName() {
		return feeGroupName;
	}

	/**
	 * @param feeGroupName the feeGroupName to set
	 */
	public void setFeeGroupName(String feeGroupName) {
		this.feeGroupName = feeGroupName;
	}

	/**
	 * @return the feeAdditionalTo
	 */
	public FeeAdditionalTO getFeeAdditionalTo() {
		return FeeAdditionalTo;
	}

	/**
	 * @param feeAdditionalTo the feeAdditionalTo to set
	 */
	public void setFeeAdditionalTo(FeeAdditionalTO feeAdditionalTo) {
		FeeAdditionalTo = feeAdditionalTo;
	}

	/**
	 * @return the feeAssignmentList
	 */
	public List<FeeAdditionalAccountAssignmentTO> getFeeAssignmentList() {
		return feeAssignmentList;
	}

	/**
	 * @param feeAssignmentList the feeAssignmentList to set
	 */
	public void setFeeAssignmentList(
			List<FeeAdditionalAccountAssignmentTO> feeAssignmentList) {
		this.feeAssignmentList = feeAssignmentList;
	}

	/**
	 * @return the feeList
	 */
	public List<FeeAdditionalTO> getFeeList() {
		return feeList;
	}

	/**
	 * @param feeList the feeList to set
	 */
	public void setFeeList(List<FeeAdditionalTO> feeList) {
		this.feeList = feeList;
	}
}
