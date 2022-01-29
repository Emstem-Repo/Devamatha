package com.kp.cms.handlers.exam;

/**
 * Dec 31, 2009 Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.exam.ExamAssignExaminerDutiesBO;
import com.kp.cms.bo.exam.ExamInvigilationDutyBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamAssignExaminerForm;
import com.kp.cms.helpers.exam.ExamAssignExaminerDutiesHelper;
import com.kp.cms.to.exam.InvDutyDetailsTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamAssignExaminerDutiesImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamAssignExaminerDutiesHandler extends ExamGenHandler {

	ExamAssignExaminerDutiesImpl impl = new ExamAssignExaminerDutiesImpl();

	ExamAssignExaminerDutiesHelper helper = new ExamAssignExaminerDutiesHelper();

	public String getDateTimeByExamId(int examId) {
		String date = "";
		date = impl.getDateTimeByExamId(examId);
		if (!date.equals("0")) {
			date = CommonUtil.formatSqlDateTimeToString(date);
		}
		return date;
	}

	// To get Exam invigilator duty type List
	@SuppressWarnings("unchecked")
	public ArrayList<KeyValueTO> getInvigilatorList() throws Exception {
		ArrayList<ExamInvigilationDutyBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamInvigilationDutyBO.class));
		return helper.convertInvDuty(listBO);
	}

	public List<InvDutyDetailsTO> getDetails(int examNameId, String date,
			String time, int invigilatorId, ArrayList<KeyValueTO> invList) throws Exception {
		Date dateTime = CommonUtil.ConvertStringToSQLDateTime(date + " " + time
				+ ":00");

		return helper.convertExaminerDetails(impl.getEmployeeDetails(
				examNameId, dateTime, invList), invList, invigilatorId);
	}

	// Get Invigilator Name for the particular InvigilatorId
	public String getInvigilatorName(int invigilatorId) {
		return ((ExamInvigilationDutyBO) impl.select_Unique(invigilatorId,
				ExamInvigilationDutyBO.class)).getName();
	}

	public ExamAssignExaminerForm getExaminerDetails(
			ExamAssignExaminerForm objform) throws Exception {

		String hour = objform.getHr();
		String min = objform.getMin();
		String time = hour.concat(":".concat(min));
		String date = objform.getDate();
		int examNameId = Integer.parseInt(objform.getExamName());
		int invId = objform.getInvigilatorId();

		ArrayList<KeyValueTO> invList = getInvigilatorList();
		List<InvDutyDetailsTO> list = getDetails(examNameId, date, time, invId,
				invList);
		objform.setListAssignExaminer(helper
				.convertBOtoTO_course(impl.getListAssignExaminer(examNameId,
						CommonUtil.ConvertStringToSQLDateTime(date + " " + time
								+ ":00"))));
		objform.setInvDutyListMain(list);
		objform.setInvDutyDetailsListSize(list.size());

		objform.setInvigilatorSize(invList.size());
		objform.setInvigilatorList(invList);
//		objform.setExamName(getExamNameList(examNameId));
		objform.setExName(getExamNameList(examNameId));
		String invigilator = "";
		if (invId > 0) {
			invigilator = getInvigilatorName(invId);
		}
		objform.setInvigilatorName(invigilator);
		objform.setInvigilatorId(invId);
		objform.setDate(date);
		objform.setHr(hour);
		objform.setMin(min);
		objform.setExamNameId(examNameId);

		return objform;
	}

	public void deleteExamDefinition(int examinardutyID, String userId) throws Exception {
		impl.delete_AssignExaminer(examinardutyID);
	}

	public void updateAssignExaminer(ArrayList<InvDutyDetailsTO> list,
			String date, String hr, String min) throws Exception {
		ArrayList<ExamAssignExaminerDutiesBO> listExaminarBO = new ArrayList<ExamAssignExaminerDutiesBO>();
		ArrayList<ExamAssignExaminerDutiesBO> listAddBO = new ArrayList<ExamAssignExaminerDutiesBO>();

		Date date1 = CommonUtil.ConvertStringToSQLDateTime(date + " "
				+ hr.concat(":".concat(min)).concat(":00"));
		for (InvDutyDetailsTO objTO : list) {
			if (objTO.getRoomNo() != null) {
				String[] values = objTO.getRoomNo().split(",");
				for (int i = 0; i < values.length; i++) {
					if (values[i] != null && values[i].trim().length() > 0)
						if (!impl.validateRoom(values[i])) {
							throw new DuplicateException();
						}
				}
			}

			if (objTO.getId() != 0) {
				listExaminarBO.add(new ExamAssignExaminerDutiesBO(
						objTO.getId(), objTO.getInvigilatorTypeId(), objTO
								.getRoomNo(), objTO.getRemarks()));
			} else {
				listAddBO.add(new ExamAssignExaminerDutiesBO(date1, objTO
						.getInvigilatorTypeId(), objTO.getRoomNo(), objTO
						.getRemarks(), objTO.getEmployerId()));
			}

		}
		if (listExaminarBO.size() > 0)
			impl.updateAssignExaminer_List(listExaminarBO);
		if (listAddBO.size() > 0)
			impl.insert_List(listAddBO);

	}

	public boolean isDateTimeValid(Date formatDate, int examId) throws Exception {
		return impl.isDateTimeValid(formatDate, examId);
	}

	public int getCurrentExamId() throws Exception {
		return impl.getCurrentExamId();
	}

	public List<KeyValueTO> getExamName() throws Exception {
		return helper.convertBOToTO_ExamName(impl.select_getExamName());
	}

	public String getDate(String date, int hr, int min) throws Exception {
		String dateValue = CommonUtil.ConvertStringToDateFormat(date + " " + hr
				+ ":" + min + ":00", "dd/MM/yyyy hh:mm:ss",
				"yyyy-MM-dd HH:mm:ss");
		if (hr > 12) {
			dateValue = dateValue + ".1";
		} else {
			dateValue = dateValue + ".0";
		}

		return dateValue;
	}

}
