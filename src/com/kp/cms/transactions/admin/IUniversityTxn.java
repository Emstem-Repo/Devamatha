package com.kp.cms.transactions.admin;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.University;
import com.kp.cms.to.admin.CollegeTO;

import java.util.List;

public interface IUniversityTxn {
	public List<University> getUniversity();
	public List<College> getCollegeByUniversity(int uniId);
	public List<CollegeTO> getCollegeByUniversityList(int uniId);
	public boolean addUniversity(University university)throws Exception;
	public University getUniversityDetails(Integer id)throws Exception;
	public boolean deleteOrReactivate(Integer id, String action)throws Exception;
	public University checkDuplicate(String university, int docType)throws Exception;
}
