package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmpAttendanceTo;
import com.kp.cms.to.employee.EmpApplyLeaveTO;

public class ViewMyAttendanceForm extends BaseActionForm {
		private List<EmpAttendanceTo> empAttTo;
		private String empCode;
		private String empName;
		private String fingerPrintId;
		private List<EmpApplyLeaveTO> empLeaveTo;
		private List<EmpAttendanceTo> empPreviousAttTo;
		private Boolean viewEmpAttendance;
		private List<EmpApplyLeaveTO> empPreviousLeaveTo;
		private boolean previous;
		private String departmentId;
		private String teachingStaff;
		private String months;

		public String getEmpCode() {
			return empCode;
		}

		public void setEmpCode(String empCode) {
			this.empCode = empCode;
		}

		public String getEmpName() {
			return empName;
		}

		public void setEmpName(String empName) {
			this.empName = empName;
		}

		public String getFingerPrintId() {
			return fingerPrintId;
		}

		public void setFingerPrintId(String fingerPrintId) {
			this.fingerPrintId = fingerPrintId;
		}

		public List<EmpApplyLeaveTO> getEmpLeaveTo() {
			return empLeaveTo;
		}

		public void setEmpLeaveTo(List<EmpApplyLeaveTO> empLeaveTo) {
			this.empLeaveTo = empLeaveTo;
		}

		public List<EmpAttendanceTo> getEmpPreviousAttTo() {
			return empPreviousAttTo;
		}

		public void setEmpPreviousAttTo(List<EmpAttendanceTo> empPreviousAttTo) {
			this.empPreviousAttTo = empPreviousAttTo;
		}

		public List<EmpApplyLeaveTO> getEmpPreviousLeaveTo() {
			return empPreviousLeaveTo;
		}

		public void setEmpPreviousLeaveTo(List<EmpApplyLeaveTO> empPreviousLeaveTo) {
			this.empPreviousLeaveTo = empPreviousLeaveTo;
		}

		public Boolean getViewEmpAttendance() {
			return viewEmpAttendance;
		}

		public void setViewEmpAttendance(Boolean viewEmpAttendance) {
			this.viewEmpAttendance = viewEmpAttendance;
		}

		public boolean isPrevious() {
			return previous;
		}

		public void setPrevious(boolean previous) {
			this.previous = previous;
		}

		public List<EmpAttendanceTo> getEmpAttTo() {
			return empAttTo;
		}

		public void setEmpAttTo(List<EmpAttendanceTo> empAttTo) {
			this.empAttTo = empAttTo;
		}

		public String getDepartmentId() {
			return departmentId;
		}

		public void setDepartmentId(String departmentId) {
			this.departmentId = departmentId;
		}


		public String getTeachingStaff() {
			return teachingStaff;
		}

		public void setTeachingStaff(String teachingStaff) {
			this.teachingStaff = teachingStaff;
		}

		public void resetFields() {
			this.setEmpAttTo(null);
			this.setEmpLeaveTo(null);
			this.setEmpPreviousAttTo(null);
			this.setEmpPreviousLeaveTo(null);
			this.setPrevious(false);
		}

		public void setMonths(String months) {
			this.months = months;
		}

		public String getMonths() {
			return months;
		}

}
