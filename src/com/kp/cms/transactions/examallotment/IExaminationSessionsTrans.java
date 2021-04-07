package com.kp.cms.transactions.examallotment;

import java.util.List;

import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.forms.examallotment.ExaminationSessionsForm;

public interface IExaminationSessionsTrans {

	boolean add(ExaminationSessions examinationSessions)throws Exception;

	List<ExaminationSessions> getListOfExaminationSessions()throws Exception;

	boolean delete(int id, String string)throws Exception;

	ExaminationSessions getExaminationSessionById(int id)throws Exception;

	boolean update(ExaminationSessions examinationSessions)throws Exception;

	boolean checkDuplicate(ExaminationSessionsForm examinationSessionsForm,
			String string)throws Exception;

}
