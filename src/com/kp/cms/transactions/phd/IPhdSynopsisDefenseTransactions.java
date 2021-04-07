package com.kp.cms.transactions.phd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.exam.ExamCceFactorBO;
import com.kp.cms.bo.phd.PhdSynopsisDefenseBO;
import com.kp.cms.forms.exam.ExamCceFactorForm;
import com.kp.cms.forms.phd.PhdSynopsisDefenseForm;


public interface IPhdSynopsisDefenseTransactions {

	public boolean deleteSynopsisDefense(int id) throws Exception;

	public List<Object[]> getStudentDetails(PhdSynopsisDefenseForm objForm, ActionErrors errors) throws Exception;

	public List<PhdSynopsisDefenseBO> getPhdSynopsisdefence(PhdSynopsisDefenseForm objForm) throws Exception;

	public PhdSynopsisDefenseBO getSynopsisDefenseById(int id) throws Exception;

	public boolean duplicateCheck(PhdSynopsisDefenseForm objForm,ActionErrors errors, HttpSession session);

	public boolean addSynopsisDefense(PhdSynopsisDefenseBO synopsisBO,String mode) throws Exception;

	public boolean reactivateSynopsisDefense(PhdSynopsisDefenseForm objForm) throws Exception;

}
