package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.to.admission.AdmissionStatusTO;

public interface IGenerateProcessTransaction {

	List<AdmAppln> getCandidates(String query) throws Exception;

	Map<Integer, String> getTemplateDescriptions(String templateName, int courseId,
			int programId) throws Exception;

	boolean saveDataToDatabase(List<AdmissionStatusTO> selectedCandidatesList,String user) throws Exception;

	List<AdmissionStatusTO> getDetailsAdmAppln(String applicationNo) throws Exception;

	void setPhoto(HttpServletRequest request, int id) throws Exception;

	List getAdmAppln(String query1, List<Integer> studentIds) throws Exception;

}
