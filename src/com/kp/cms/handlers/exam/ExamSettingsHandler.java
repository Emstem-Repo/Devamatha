package com.kp.cms.handlers.exam;

/**
 * Dec 25, 2009 Created By 9Elements
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamSettingsBO;
import com.kp.cms.bo.exam.OrganisationUtilBO;
import com.kp.cms.forms.exam.ExamSettingsForm;
import com.kp.cms.helpers.exam.ExamSettingsHelper;
import com.kp.cms.to.exam.ExamSettingsTO;
import com.kp.cms.transactionsimpl.exam.ExamSettingsImpl;
@SuppressWarnings("unchecked") 
public class ExamSettingsHandler {

	ExamSettingsImpl impl = new ExamSettingsImpl();
	ExamSettingsHelper helper = new ExamSettingsHelper();

	
	public List<ExamSettingsTO> init() throws Exception {
		OrganisationUtilBO o = ((OrganisationUtilBO) impl
				.select_OneRes_Only(OrganisationUtilBO.class));
		String organizationName = "";
		if (o != null) {
			organizationName = o.getName();
		}
		ArrayList<ExamSettingsBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamSettingsBO.class));
		return helper.convertBOtoTO(listBO, organizationName);
	}

	public void update(int id, String absentCodeMarkEntry,
			String notProcedCodeMarkEntry, String withHeldCodeMarkEntry,String securedMarkEntryBy,
			String maxAllwdDiffPrcntgMultiEvaluator, String gradePointForFail,
			String gradeForFail, String userId) throws Exception {
		if(maxAllwdDiffPrcntgMultiEvaluator.length()<=0){
			maxAllwdDiffPrcntgMultiEvaluator="0";
		}
		if(gradePointForFail.length()<=0){
			gradePointForFail="0";
		}
		BigDecimal maxAllwdBD = new BigDecimal(maxAllwdDiffPrcntgMultiEvaluator);
		BigDecimal gradePointForFailBD = new BigDecimal(gradePointForFail);
//		if (!impl.isDuplicated(id, absentCodeMarkEntry, notProcedCodeMarkEntry,
//				securedMarkEntryBy, maxAllwdBD, gradePointForFailBD,
//				gradeForFail)) {
			impl.update(id, absentCodeMarkEntry, notProcedCodeMarkEntry,withHeldCodeMarkEntry,
					securedMarkEntryBy, maxAllwdBD, gradePointForFailBD,
					gradeForFail, userId);
//		}
	}

	public void add(String absentCodeMarkEntry, String notProcedCodeMarkEntry,String withHeldCodeMarkEntry,
			String securedMarkEntryBy, String maxAllwdDiffPrcntgMultiEvaluator,
			String gradePointForFail, String gradeForFail, String userId)
			throws Exception {
		if(maxAllwdDiffPrcntgMultiEvaluator.length()<=0){
			maxAllwdDiffPrcntgMultiEvaluator="0";
		}
		if(gradePointForFail.length()<=0){
			gradePointForFail="0";
		}
		BigDecimal maxAllwdBD = new BigDecimal(maxAllwdDiffPrcntgMultiEvaluator);
		BigDecimal gradePointForFailBD = new BigDecimal(gradePointForFail);

		if (!impl.isDuplicated(0, absentCodeMarkEntry, notProcedCodeMarkEntry,withHeldCodeMarkEntry,
				securedMarkEntryBy, maxAllwdBD, gradePointForFailBD,
				gradeForFail)) {

			ExamSettingsBO objBO = new ExamSettingsBO(absentCodeMarkEntry,
					notProcedCodeMarkEntry,withHeldCodeMarkEntry, securedMarkEntryBy, maxAllwdBD,
					gradePointForFailBD, gradeForFail, userId);
			impl.insert(objBO);
		}
	}

	public ExamSettingsForm getUpdatableForm(ExamSettingsForm form, String mode) throws Exception {
		form = helper.createFormObject(form, (ExamSettingsBO) impl
				.select_Unique(form.getId(), ExamSettingsBO.class));
		return form;
	}

	public void delete(int id, String userId) throws Exception {
		impl.deleteExamSettings();
	}

	public void reactivate(int id, String userId) throws Exception {
		impl.reActivate_IExamGenBO(id, userId, ExamSettingsBO.class);
	}

}
