package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;

public interface IHostelApplicationByAdminTransactions {
	
	public AdmAppln getAdmApplnByQuery(String query)throws Exception;
	public Employee getEmployee(String employeeId)throws Exception;
	public List<HlRoomType> getRoomTypesForHostel(int hostelId) throws Exception;
	public boolean saveHlApplicationForm(HlApplicationForm hlApplicationForm)throws Exception;
	public Integer getMaxRequisitionNo() throws Exception;
	public HlHostel getTermsConditionforHostel(int hostelId)throws Exception;
	public HlApplicationForm getHlapplicationByQuery(String query)throws Exception;
	public HlApplicationForm getHlapplicationByStudent(int admApplnId)throws Exception;
	public HlApplicationForm getHlapplicationByStaff(int staffId)throws Exception;
	public List<HlFees> getRoomTypewiseHostelFeesonHostelId(int hostelId) throws Exception;

}
