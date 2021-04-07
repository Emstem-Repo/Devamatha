package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.AdmSubjectForRank;

public interface IAdmSubjectForRank {

	public List<AdmSubjectForRank> getSubject();

	public AdmSubjectForRank isDuplicated(AdmSubjectForRank admsbjctbo) throws Exception;

	public boolean add(AdmSubjectForRank admsbjctbo);

	public boolean edit(AdmSubjectForRank admsbjctbo);

	public boolean delete(int id, String userId);

	public boolean reActivate(AdmSubjectForRank subj, String userId);
	
	

}
