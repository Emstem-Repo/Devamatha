package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlComplaint;
import com.kp.cms.bo.admin.HlComplaintType;
import com.kp.cms.forms.hostel.ComplaintsForm;

public interface IComplaintsTransaction {

	public List<HlComplaintType> getComplaintsList() throws Exception;
	public boolean saveComplaintDetails(HlComplaint complaint) throws Exception;
	public HlApplicationForm StudentCheck(ComplaintsForm complaintsForm)throws Exception;
	public List<HlComplaint> getComplaintsDetails() throws Exception;
	public boolean deleteComplaintsDetails(int id, String userId) throws Exception ;
	public boolean duplicateStudentCheck(ComplaintsForm complaintsForm)throws Exception;
}