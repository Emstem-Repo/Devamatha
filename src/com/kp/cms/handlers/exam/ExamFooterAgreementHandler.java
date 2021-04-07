package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.forms.exam.ExamFooterAgreementForm;
import com.kp.cms.helpers.exam.ExamFooterAgreementHelper;
import com.kp.cms.to.exam.ExamFooterAgreementTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamFooterAgreementImpl;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
@SuppressWarnings("unchecked")
public class ExamFooterAgreementHandler extends ExamGenHandler {

	ExamFooterAgreementHelper helper = new ExamFooterAgreementHelper();
	ExamFooterAgreementImpl impl = new ExamFooterAgreementImpl();

	// To display the main grid
	public ArrayList<ExamFooterAgreementTO> init() throws Exception {
		List<ExamFooterAgreementBO> listBO = new ArrayList(new ExamGenImpl()
				.select_ActiveOnly(ExamFooterAgreementBO.class));
		return helper.convertBOtoTO(listBO);
	}

	// To get Program Type List
	public List<KeyValueTO> getProgramTypeList() throws Exception {
		ArrayList<ExamProgramTypeUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamProgramTypeUtilBO.class));
		return helper.convertBOToTO_ProgramType(listBO);
	}

	// On SAVE
	public void add(String name, String typeId, String programID, String desc,
			String date, String userId, String hallTicketOrmarksCard, String academicYear) throws Exception {

		int programTypeID = 0;
		if (programID.length() != 0) {
			programTypeID = Integer.parseInt(programID);
		}

		impl.isDuplicate(0, name, programTypeID);

		ExamFooterAgreementBO bo = helper.convertFormToBO(0,
				name, typeId, programID, desc, date, userId, hallTicketOrmarksCard,academicYear);
		impl.insert(bo);

	}

	// On EDIT
	public void update(int id, String name, String typeId, String programID,
			String desc, String date, String userId, String hallTicketorMarksCard, String academicYear) throws Exception {
		int programTypeID = 0;
		if (programID.length() != 0) {
			programTypeID = Integer.parseInt(programID);
		}

		impl.isDuplicate(id, name, programTypeID);

		ExamFooterAgreementBO bo= helper.convertFormToBO(id,
				name, typeId, programID, desc, date, userId, hallTicketorMarksCard,academicYear);

		impl.update(bo);

	}

	// On DELETE
	public void delete(int id, String userId) throws Exception {

		impl.delete_IExamGenBO(id, userId, ExamFooterAgreementBO.class);

	}

	// Updatable Form
	public ExamFooterAgreementForm getUpdatableForm(
			ExamFooterAgreementForm objform) throws Exception {

		return helper.convertBOToForm((ExamFooterAgreementBO) impl
				.select_Unique(objform.getId(), ExamFooterAgreementBO.class),
				objform);
	}

}
