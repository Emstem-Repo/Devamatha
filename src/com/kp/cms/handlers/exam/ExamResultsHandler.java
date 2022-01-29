package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamPublishExamResultsBO;
import com.kp.cms.forms.exam.ExamExamResultsForm;
import com.kp.cms.forms.exam.ExamPublishHallTicketForm;
import com.kp.cms.helpers.exam.ExamResultsHelper;
import com.kp.cms.to.exam.ExamExamResultsTO;
import com.kp.cms.transactionsimpl.exam.ExamPublishHallTicketImpl;
import com.kp.cms.transactionsimpl.exam.ExamResultsImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamResultsHandler extends ExamGenHandler {
	ExamResultsImpl impl = new ExamResultsImpl();
	ExamResultsHelper helper = new ExamResultsHelper();

	public List<ExamExamResultsTO> getListExamResult(ExamExamResultsForm objform) throws Exception {
		return helper.convertBOTOTO(impl.select_ExamResults(objform));
	}

	public void addExamResults(int examId, String publishDate,
			ArrayList<Integer> listClass, int publishComponents, String userId)
			throws Exception {
		ArrayList<ExamPublishExamResultsBO> list = new ArrayList<ExamPublishExamResultsBO>();
		ExamPublishExamResultsBO bo = null;
		for (Integer classid : listClass) {
			impl.isDuplicated_ExamResults(0, examId, classid.intValue(),
					CommonUtil.ConvertStringToSQLDate(publishDate),
					publishComponents);
			bo = new ExamPublishExamResultsBO(examId, classid, CommonUtil
					.ConvertStringToSQLDate(publishDate), publishComponents,
					userId);
			list.add(bo);
		}
		impl.insert_List(list);

	}

	public void delete(int id) throws Exception {
		impl.delete_ExamResults(id);
	}

	public ExamExamResultsForm getUpdatableForm(ExamExamResultsForm objform,
			int id) throws Exception {
		objform = helper.setTOForm(objform, impl.select_ExamResults(id));

		return objform;
	}

	public void update(int id, int examId, String publishDate, int classid,
			int publishComponents, String userId) throws Exception {

		impl.isDuplicated_ExamResults(id, examId, classid, CommonUtil
				.ConvertStringToSQLDate(publishDate), publishComponents);
		impl.update_ExamResults(id, examId, CommonUtil
				.ConvertStringToSQLDate(publishDate), classid,
				publishComponents, userId);

	}

	public int getInternalComponentsByClasses(int examId) {
		int id = impl.selectInternalComponents(examId);
		return id;
	}

	public String isDateTimeValid(String publushDate, int examId) throws Exception {
		String msg = "";
		String maxDate = impl.getMaxdate(examId);

		maxDate = CommonUtil.ConvertStringToDateFormat(maxDate,
				"yyyy-M-d hh:mm:ss.0", "MM/dd/yyyy h:mm:ss a");

		String formattedDate1 = CommonUtil.ConvertStringToDateFormat(
				publushDate, "MM/dd/yyyy h:mm:ss a", "dd/MM/yyyy");
		Date date1 = CommonUtil.ConvertStringToDate(formattedDate1);
		if (maxDate != null && maxDate.length() != 0) {
			String formattedDate2 = CommonUtil.ConvertStringToDateFormat(
					maxDate, "MM/dd/yyyy h:mm:ss a", "dd/MM/yyyy");
			Date date2 = CommonUtil.ConvertStringToDate(formattedDate2);
			if (date1.before(date2)) {

				msg = "NotValidDate";
			}
		}
		if (maxDate == null || maxDate.length() == 0) {
			msg = "TimetableNotreated";
		}
		return msg;

	}

	public Map<Integer, String> getClassCodeByExamName() throws Exception {
		return getClassCodeByExamName(impl.getcurrentExamID());
	}

	public int getinternalComponents() throws Exception {
		return getInternalComponentsByClasses(impl.getcurrentExamID());
	}
	
	public void getDeaneryMap(ExamExamResultsForm objform) throws Exception{
		ExamPublishHallTicketImpl impl1 = new ExamPublishHallTicketImpl();
		Map<String,String> map=impl1.getDeaneryMap();
		objform.setDeaneryMap(map);
	}

}
