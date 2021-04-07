package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admission.PgAdmSubjectForRank;

public interface IPgAdmSubjectForRank {

	public List<PgAdmSubjectForRank> getSubject();

	public List<PgAdmSubjectForRank> isDuplicated(List<PgAdmSubjectForRank> admsbjctbo) throws Exception;

	public boolean add(PgAdmSubjectForRank admsbjctbo);

	public boolean edit(PgAdmSubjectForRank admsbjctbo);

	public boolean delete(int id, String userId);

	public boolean reActivate(PgAdmSubjectForRank subj, String userId);

	List<PgAdmSubjectForRank> isDuplicatedforUpdate(List<PgAdmSubjectForRank> admsbjctbo) throws Exception;

	

}
