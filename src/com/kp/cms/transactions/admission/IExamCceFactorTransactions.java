package com.kp.cms.transactions.admission;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.exam.ExamCceFactorBO;
import com.kp.cms.forms.exam.ExamCceFactorForm;


public interface IExamCceFactorTransactions {

	public boolean addExamCceFactor(List<ExamCceFactorBO> examCceFactorBO, ActionErrors errors, ExamCceFactorForm objForm, HttpSession session) throws Exception;

	public List<ExamCceFactorBO> getExamCceFactorList(String year) throws Exception;

	public ExamCceFactorBO getExamCceFactorById(int id) throws Exception;

	public boolean deleteExamCceFactor(int id) throws Exception;

	public boolean updateExamCceFactor(List<ExamCceFactorBO> examCceFactor,	ActionErrors errors, ExamCceFactorForm objForm, HttpSession session);

}
