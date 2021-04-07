package com.kp.cms.helpers.employee;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpExceptionTypeBO;
import com.kp.cms.bo.admin.EmpReadAttendanceFileBO;
import com.kp.cms.bo.admin.EmpWorkDairy;
import com.kp.cms.forms.employee.ExceptionTypesForm;
import com.kp.cms.forms.employee.ReadAttendanceFileForm;
import com.kp.cms.to.employee.AttendanceFileTO;
import com.kp.cms.to.employee.ExceptionTypeTO;
import com.kp.cms.utilities.CommonUtil;

public class ExceptionTypesHelper {
	private static final Log log = LogFactory
			.getLog(ExceptionTypesHelper.class);
	public static volatile ExceptionTypesHelper objHelper = null;

	public static ExceptionTypesHelper getInstance() {
		if (objHelper == null) {
			objHelper = new ExceptionTypesHelper();
			return objHelper;
		}
		return objHelper;
	}

	public EmpExceptionTypeBO copyDataFromFormToBO(ExceptionTypesForm objForm,String mode)
			throws Exception {
		EmpExceptionTypeBO objBO = new EmpExceptionTypeBO();
		if(objForm.getId()!=0){
			objBO.setId(objForm.getId());	
		}
		objBO.setExceptionType(objForm.getExceptionType());
		objBO.setExceptionShortName(objForm.getExceptionShortName());
		if(!mode.equals("update")){
			objBO.setCreatedBy(objForm.getUserId());
			objBO.setCreatedDate(new Date());
		}
		objBO.setModifiedBy(objForm.getUserId());
		objBO.setLastModifiedDate(new Date());
		objBO.setIsActive(true);
		return objBO;
	}

	public List<ExceptionTypeTO> convertBOToTO(
			List<EmpExceptionTypeBO> exceptionTypes) {
		ArrayList<ExceptionTypeTO> list = new ArrayList<ExceptionTypeTO>();
		ExceptionTypeTO objTo = null;
		if(exceptionTypes!=null){
		for (EmpExceptionTypeBO objBO : exceptionTypes) {
			objTo = new ExceptionTypeTO();
			objTo.setId(objBO.getId());
			if (objBO.getExceptionType() != null) {
				objTo.setExceptionType(objBO.getExceptionType());
			}
			if (objBO.getExceptionShortName() != null) {
				objTo.setExceptionShortName(objBO.getExceptionShortName());
			}
			list.add(objTo);

		}
		}

		return list;
	}

	public ExceptionTypesForm convertBOToForm(EmpExceptionTypeBO exception,
			ExceptionTypesForm objForm) {
		objForm.setId(exception.getId());
		if(exception.getExceptionType()!=null){
			objForm.setExceptionType(exception.getExceptionType());
		}
		if(exception.getExceptionShortName()!=null){
			objForm.setExceptionShortName(exception.getExceptionShortName());
		}
		
		return objForm;
	}

}
