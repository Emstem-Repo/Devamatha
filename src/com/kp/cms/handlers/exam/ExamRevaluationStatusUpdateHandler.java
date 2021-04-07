package com.kp.cms.handlers.exam;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamRevaluationStatusUpdateForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.helpers.admission.InterviewHelper;
import com.kp.cms.helpers.exam.ExamRevaluationStatusUpdateHelper;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.transactions.exam.IExamRevaluationStatusUpdateTransaction;
import com.kp.cms.transactionsimpl.exam.ExamRevaluationStatusUpdateTransactionimpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamRevaluationStatusUpdateHandler {
	/**
	 * Singleton object of ExamRevaluationStatusUpdateHandler
	 */
	private static volatile ExamRevaluationStatusUpdateHandler examRevaluationStatusUpdateHandler = null;
	private static final Log log = LogFactory.getLog(ExamRevaluationStatusUpdateHandler.class);
	private ExamRevaluationStatusUpdateHandler() {
		
	}
	/**
	 * return singleton object of ExamRevaluationStatusUpdateHandler.
	 * @return
	 */
	public static ExamRevaluationStatusUpdateHandler getInstance() {
		if (examRevaluationStatusUpdateHandler == null) {
			examRevaluationStatusUpdateHandler = new ExamRevaluationStatusUpdateHandler();
		}
		return examRevaluationStatusUpdateHandler;
	}
	
	IExamRevaluationStatusUpdateTransaction transaction=ExamRevaluationStatusUpdateTransactionimpl.getInstance();
	/**
	 * students revaluation application are set to list to display in the form
	 * @param examRevaluationStatusUpdateForm
	 * @return
	 * @throws Exception
	 */
	
	public List<ExamRevaluationApplicationTO> getStudentsRevaluationApp(ExamRevaluationStatusUpdateForm examRevaluationStatusUpdateForm)
	throws Exception{
			List<ExamRevaluationAppDetails> revaluationAppDetailsBo=transaction.getRevaluationAppDetailsList(examRevaluationStatusUpdateForm.getRegisterNo());
			List<ExamRevaluationApplicationTO> revaluationAppTo=ExamRevaluationStatusUpdateHelper.getInstance().convertBOtoTO(revaluationAppDetailsBo,examRevaluationStatusUpdateForm);
			return revaluationAppTo;
	}
	/**
	 * @param examRevaluationStatusUpdateForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateNewStatus(ExamRevaluationStatusUpdateForm examRevaluationStatusUpdateForm) throws Exception {
		boolean isUpdated=false;
		isUpdated=transaction.saveNewStatus(examRevaluationStatusUpdateForm);
		return isUpdated;
	}
	/**
	 * @param examRevaluationStatusUpdateForm
	 * @throws Exception
	 */
	public boolean sendMailToStudent(ExamRevaluationStatusUpdateForm examRevaluationStatusUpdateForm) throws Exception {
		log.info("entered sendMailToStudent in InterviewHelper");
		boolean sent = false;
		if(examRevaluationStatusUpdateForm.getContactEmail()!=null && !examRevaluationStatusUpdateForm.getContactEmail().isEmpty()){
		List<GroupTemplate> templateList= TemplateHandler.getInstance().getDuplicateCheckList(0,CMSConstants.REVALUATION_STATUS_TEMPLATE);
		if (templateList != null && !templateList.isEmpty()) {

			String desc = templateList.get(0).getTemplateDescription();
			
			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class.getClassLoader()
						.getResourceAsStream(
								CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {
				log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
			String message = desc;
			message = message.replace(CMSConstants.TEMPLATE_COURSE,examRevaluationStatusUpdateForm.getCourseName()!=null?examRevaluationStatusUpdateForm.getCourseName():"");
			message = message.replace(CMSConstants.TEMPLATE_REGISTER_NO,examRevaluationStatusUpdateForm.getRegisterNo()!=null?examRevaluationStatusUpdateForm.getRegisterNo():"");
			message = message.replace(CMSConstants.TEMPLATE_STUDENT_NAME,examRevaluationStatusUpdateForm.getStudentName()!=null?examRevaluationStatusUpdateForm.getStudentName():"");
			message = message.replace(CMSConstants.TEMPLATE_SEM_TYPE,examRevaluationStatusUpdateForm.getSemType()!=null?examRevaluationStatusUpdateForm.getSemType():"");
			message = message.replace(CMSConstants.TEMPLATE_TERM_NO,examRevaluationStatusUpdateForm.getTermNo()!=null?examRevaluationStatusUpdateForm.getTermNo():"");
			
			sent=InterviewHelper.sendMail(examRevaluationStatusUpdateForm.getContactEmail(), "Revaluation/Re-totaling Status Updated", message, prop);
		}
		}
		return sent;
	}
}
