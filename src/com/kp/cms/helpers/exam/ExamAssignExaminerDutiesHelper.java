package com.kp.cms.helpers.exam;

/**
 * Dec 31, 2009
 * Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ExamInvigilationDutyBO;
import com.kp.cms.to.exam.ExamAssignExaminerTO;
import com.kp.cms.to.exam.ExaminerDutiesTO;
import com.kp.cms.to.exam.InvDutyDetailsTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.KeyValueTOComparator;

public class ExamAssignExaminerDutiesHelper extends ExamGenHelper {

	// To get Exam Invigilation Duty Type List
	public ArrayList<KeyValueTO> convertInvDuty(
			ArrayList<ExamInvigilationDutyBO> listBO) throws Exception {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamInvigilationDutyBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	public ArrayList<InvDutyDetailsTO> convertExaminerDetails(
			List listExaminers, ArrayList<KeyValueTO> invList, int invigilatorId) throws Exception {

		int sizeOfValues = invList.size();
		ArrayList<InvDutyDetailsTO> retutnList = new ArrayList<InvDutyDetailsTO>();
		int val = 0;
		int invigilatorTypeId = 0;
		String value = "on";
		Iterator itr = listExaminers.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			int id = 0;
			int employerId = 0;
			if (row[2] != null) {
				employerId = Integer.parseInt(row[2].toString());
			}

			String name = (String) row[4];

			if (row[5] != null) {
				name = name.concat(" ").concat((String) row[5]);
			}
			ArrayList<ExaminerDutiesTO> list = new ArrayList<ExaminerDutiesTO>();

			for (int i = 1; i <= sizeOfValues; i++) {
				if (row[10] != null) {
					val = (Integer) row[10];
				}
				int a = 11 + i;

				if ((row[a]).toString().equals("0")) {

					value = "off";
				} else {
					value = "on";

				}
				list.add(new ExaminerDutiesTO(val, employerId, value));
			}

			if (row[9] != null) {
				invigilatorTypeId = (Integer) row[9];
			}
			if (row[10] != null) {
				id = (Integer) row[10];
			} else {
				id = 0;
			}

			retutnList.add(new InvDutyDetailsTO(employerId, name,
					(Integer) row[1], (String) row[6], (String) row[7],
					(String) row[8], invigilatorTypeId, id, list,(String)row[11]));

		}
		retutnList = convertList(retutnList, invList, invigilatorId);
		return retutnList;

	}

	private ArrayList<InvDutyDetailsTO> convertList(
			ArrayList<InvDutyDetailsTO> invList_input,
			ArrayList<KeyValueTO> invIdList, int invigilatorId) throws Exception {

		ArrayList<ExaminerDutiesTO> l;
		int size;
		ArrayList<ExaminerDutiesTO> finalListInv;
		for (InvDutyDetailsTO iTO : invList_input) {
			l = iTO.getListInvigilator();
			size = l.size();
			finalListInv = new ArrayList<ExaminerDutiesTO>();
			if (iTO.getInvigilatorType() != null) {
				for (int i = 0; i < size; i++) {
					finalListInv.add(new ExaminerDutiesTO(invIdList.get(i)
							.getId(), l.get(i).getEmployeeId(), l.get(i)
							.getDisplay()));

				}
			} else {
				for (int i = 0; i < size; i++) {
					if (invigilatorId == invIdList.get(i).getId()) {
						finalListInv.add(new ExaminerDutiesTO(invIdList.get(i)
								.getId(), l.get(i).getEmployeeId(), "on"));
					} else {
						finalListInv.add(new ExaminerDutiesTO(invIdList.get(i)
								.getId(), l.get(i).getEmployeeId(), l.get(i)
								.getDisplay()));
					}
				}
			}

			iTO.setListInvigilator(finalListInv);
		}

		return invList_input;
	}

	public List<ExamAssignExaminerTO> convertBOtoTO_course(
			List listAssignExaminer) throws Exception {
		ArrayList<ExamAssignExaminerTO> list = new ArrayList<ExamAssignExaminerTO>();
		Iterator itr = listAssignExaminer.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();

			String name = (String) row[1];
			if (row[2] != null) {
				name = name.concat(" ").concat((String) row[2]);
			}
			list.add(new ExamAssignExaminerTO((Integer) row[0], name,
					(String) row[3], (String) row[4], (String) row[5],(String)row[6]));

		}
		//Collections.sort(list,new KeyValueTOComparator());
		return list;
	}

	public List<KeyValueTO> convertBOToTO_ExamName(List<Object[]> listExamName) throws Exception {
		ArrayList<KeyValueTO> list = new ArrayList<KeyValueTO>();
		if (listExamName != null) {
			Iterator<Object[]> itr = listExamName.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				int id = 0;
				String name = "";
				if (object[0] != null) {
					id = Integer.parseInt(object[0].toString());
				}
				if (object[1] != null) {
					name = object[1].toString();
				}
				list.add(new KeyValueTO(id, name));
			}
		}
		Collections.sort(list,new KeyValueTOComparator());
		return list;
	}

}
