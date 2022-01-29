package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamAssignExaminerToExamBO;
import com.kp.cms.helpers.exam.ExamAssignExaminerHelper;
import com.kp.cms.to.exam.EmployeeTO;
import com.kp.cms.transactionsimpl.exam.ExamAssignExaminerImpl;

public class ExamAssignExaminerToExamHandler extends ExamGenHandler {

	ExamAssignExaminerHelper helper = new ExamAssignExaminerHelper();
	ExamAssignExaminerImpl impl = new ExamAssignExaminerImpl();

	public void addExaminerToExam(List<Integer> employeeId, int examId)
			throws Exception {

		ArrayList<ExamAssignExaminerToExamBO> e = new ArrayList<ExamAssignExaminerToExamBO>();

		if (employeeId.size() > 0) {
			// delete from duties and then from assign for those examiners who
			// are not in the list
			impl.delete_from_duties(examId, employeeId);
			impl.delete_from_assign(examId, employeeId);

			for (Integer examinersList : employeeId) {
				if (!impl.isDuplicate(examinersList.intValue(), examId)) {
					e.add(new ExamAssignExaminerToExamBO(examinersList
							.intValue(), examId));

				}

			}
			impl.insert_List(e);
		} else {

			if (impl.isDeletePossible(examId)) {
				impl.delete_Exam(examId);
			} else {

			}

		}

	}

	public List<EmployeeTO> getDetails(int examNameId) throws Exception {
		List<EmployeeTO> list = (List<EmployeeTO>) helper
				.convertExaminerDetails(impl.getEmployeeDetails(examNameId));

		return list;
	}

}
