package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Category;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.admission.TcDetailsOldStudents;
import com.kp.cms.to.admission.TcDetailsOldStudentsTo;

public interface ITcDetailsOldStudents {

	public String getTcDetails(String year, String string)throws Exception;

	public List<TcDetailsOldStudents> getOldStudentsList(String query)throws Exception;

	public String getReligionQuery()throws Exception;

	public List<Religion> getReligionList(String query)throws Exception;

	public String getCharacterAndConductQuery()throws Exception;

	public List<CharacterAndConduct> getCharacterAndConductList(String query)throws Exception;

	public String getCategoryQuery()throws Exception;

	public List<Caste> getCategoryList(String query)throws Exception;

	public String getNationalityQuery()throws Exception;

	public List<Nationality> getNationalityList(String query)throws Exception;

	public List<TcDetailsOldStudents> getOldStudentsList()throws Exception;

	public TCNumber getTcNoByYear(String year, String tcFor)throws Exception;

	public void updateTcSLNO(TCNumber tcNumber)throws Exception;

	public int saveOldStudentTcDetails(TcDetailsOldStudents tcDetailsOldStudents)throws Exception;

	public TcDetailsOldStudents editTcDetailsOldStudents(String string)throws Exception;

	public boolean deleteTc(String id)throws Exception;

	public TcDetailsOldStudents getTcOldStudentByRegNo(String registerNo)throws Exception; 

	public List<TCNumber> getTcNo(String year)throws Exception;
	

}
