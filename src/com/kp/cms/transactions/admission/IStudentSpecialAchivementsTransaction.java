package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.SpecialAchievement;
import com.kp.cms.bo.admin.Student;

	public interface IStudentSpecialAchivementsTransaction {

		public boolean addAchivements(SpecialAchievement achivementBO) throws Exception;

		public Student validRegNo(String regNo) throws Exception;

		public List<SpecialAchievement> getSpecialAchivementList()throws Exception;

		public boolean deleteStudentAchivements(Integer id)throws Exception	;

		public SpecialAchievement duplicateCheck(Student student, String termNumber)throws Exception;

		public SpecialAchievement getSpecialAchivement(Integer id)throws Exception;
		
		public boolean updateAchivements(SpecialAchievement achievement)throws Exception;
		
		public boolean reActivateAchivement(Integer id)throws Exception;

		public Map<Integer, String> getTermNumberMap() throws Exception;

		public String getCurrentTermNumber(String regNo) throws Exception;
}
