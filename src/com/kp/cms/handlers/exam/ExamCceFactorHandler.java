package com.kp.cms.handlers.exam;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.exam.ExamCceFactorBO;
import com.kp.cms.forms.exam.ExamCceFactorForm;
import com.kp.cms.helpers.exam.ExamCceFactorHelper;
import com.kp.cms.to.exam.ExamCceFactorTO;
import com.kp.cms.transactions.admission.IExamCceFactorTransactions;
import com.kp.cms.transactionsimpl.exam.ExamCceFactorImpl;

public class ExamCceFactorHandler {
	private static final Log log = LogFactory.getLog(ExamCceFactorHandler.class);
	private static volatile ExamCceFactorHandler examCceFactorHandler = null;

	public static ExamCceFactorHandler getInstance() {
		if (examCceFactorHandler == null) {
			examCceFactorHandler = new ExamCceFactorHandler();
		}
		return examCceFactorHandler;
	}
   
	IExamCceFactorTransactions trancation=new ExamCceFactorImpl();
   /**
 * @param objForm
 * @param errors
 * @param session 
 * @return
 */
    public boolean addExamCceFactor(ExamCceFactorForm objForm, ActionErrors errors, HttpSession session) throws Exception{
    	List<ExamCceFactorBO> examCceFactorBO = ExamCceFactorHelper.getInstance().convertFormToBos(objForm);
        boolean isAdded = trancation.addExamCceFactor(examCceFactorBO, errors,objForm,session);
        return isAdded;
    }

	/**
	 * @param year 
	 * @return
	 * @throws Exception
	 */
	public List<ExamCceFactorTO> getExamCceFactorList(String year) throws Exception{
        List<ExamCceFactorBO> examCceFactorBO = trancation.getExamCceFactorList(year);
        List<ExamCceFactorTO> examCceFactorTO = ExamCceFactorHelper.getInstance().convertBoToTos(examCceFactorBO);
        return examCceFactorTO;
    }

	/**
	 * @param objForm
	 * @param mode
	 * @throws Exception
	 */
	public void editExamCceFactor(ExamCceFactorForm objForm, String mode) throws Exception{
		ExamCceFactorBO examCceFactorBO = trancation.getExamCceFactorById(objForm.getId());
		ExamCceFactorHelper.getInstance().setDataBoToForm(objForm, examCceFactorBO);
    }
	
	  /**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteExamCceFactor(ExamCceFactorForm objForm)
      throws Exception{
      boolean isDeleted = trancation.deleteExamCceFactor(objForm.getId());
      return isDeleted;
  }

	public boolean updateExamCceFactor(ExamCceFactorForm objForm,ActionErrors errors, HttpSession session) {
    	List<ExamCceFactorBO> examCceFactor = ExamCceFactorHelper.getInstance().convertToBos(objForm);
        boolean isUpdated = trancation.updateExamCceFactor(examCceFactor, errors,objForm,session);
        return isUpdated;
    }

}
