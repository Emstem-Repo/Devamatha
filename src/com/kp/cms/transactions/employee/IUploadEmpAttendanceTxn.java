package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.to.employee.UploadEmpAttendanceTO;

public interface IUploadEmpAttendanceTxn {

	boolean saveUploadEmpAttendance(List<UploadEmpAttendanceTO> results, String userId) throws Exception;

}
