package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.to.exam.EmployeeTO;

public class ExamAssignExaminerHelper extends ExamGenHelper {

	@SuppressWarnings("unchecked")
	public ArrayList<EmployeeTO> convertExaminerDetails(List listExaminers) {

		ArrayList<EmployeeTO> retutnList = new ArrayList<EmployeeTO>();
		Iterator itr = listExaminers.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			int id = 0;
			if (row[5] != null) {

				id = (Integer) row[5];
			}
			retutnList.add(new EmployeeTO((Integer) row[0], (String) row[1],
					(String) row[2], id, (String) row[4]));
		}
		return retutnList;

	}
}
