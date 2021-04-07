package com.kp.cms.handlers.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.forms.employee.GuestFacultyExcelReportForm;
import com.kp.cms.helpers.employee.GuestFacultyExcelReportHelper;
import com.kp.cms.to.employee.EmployeeReportTO;
import com.kp.cms.to.employee.GuestFacultyTO;
import com.kp.cms.transactions.employee.IEmployeeReportTransaction;
import com.kp.cms.transactions.employee.IGuestFacultyExcelReportTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeReportTxnImpl;
import com.kp.cms.transactionsimpl.employee.GuestFacultyExcelReportTransImpl;

public class GuestFacultyExcelReportHandler {
	 GuestFacultyExcelReportHelper guestFacultyExcelReportHelper = GuestFacultyExcelReportHelper.getInstance();
	 IGuestFacultyExcelReportTransaction transaction=GuestFacultyExcelReportTransImpl.getInstance();
	IEmployeeReportTransaction reportTransaction = EmployeeReportTxnImpl.getInstance();
	private static volatile GuestFacultyExcelReportHandler guestFacultyExcelReportHandler = null;
	public static GuestFacultyExcelReportHandler getInstance(){
		if(guestFacultyExcelReportHandler == null){
			guestFacultyExcelReportHandler = new GuestFacultyExcelReportHandler();
		return guestFacultyExcelReportHandler;
		}
		return guestFacultyExcelReportHandler;
}
	public void getInitialData(GuestFacultyExcelReportForm guestFacultyExcelReportForm) throws Exception{

		Map<String,String> streamDetailsMap=reportTransaction.getStreamMap();
		if(streamDetailsMap!=null && !streamDetailsMap.isEmpty()){
			guestFacultyExcelReportForm.setStreamMap(streamDetailsMap);
		}
		Map<String,String> departmentMap=reportTransaction.getDepartmentMap();
		if(departmentMap!=null && !departmentMap.isEmpty()){
			guestFacultyExcelReportForm.setDepartmentMap(departmentMap);
		}
		Map<String,String> designationMap=reportTransaction.getDesignation();
		if(designationMap!=null && !designationMap.isEmpty()){
			guestFacultyExcelReportForm.setDesignationMap(designationMap);
		}
		Map<String,String> workLocationMap=reportTransaction.getWorkLocationMap();
		if(workLocationMap!=null && !workLocationMap.isEmpty()){
			guestFacultyExcelReportForm.setWorkLocationMap(workLocationMap);
		}

	}
	public List<GuestFacultyTO> getGuestSearchedResults(GuestFacultyExcelReportForm guestFacultyExcelReportForm) throws Exception{
		 int streamId = 0;
		 int departmentId = 0;
		 int designationId = 0;
		 int workLocationId = 0;
		  	if(guestFacultyExcelReportForm.getStreamDetails()!=null && !guestFacultyExcelReportForm.getStreamDetails().isEmpty()){
		  		streamId = Integer.parseInt(guestFacultyExcelReportForm.getStreamDetails());
		  	}
		  	if(guestFacultyExcelReportForm.getDepartment()!=null  && !guestFacultyExcelReportForm.getDepartment().isEmpty()){
		  		departmentId = Integer.parseInt(guestFacultyExcelReportForm.getDepartment());
		  	}
		  	if(guestFacultyExcelReportForm.getDesignation()!=null && !guestFacultyExcelReportForm.getDesignation().isEmpty()){
		  		designationId = Integer.parseInt(guestFacultyExcelReportForm.getDesignation());
		  	}
		  	if(guestFacultyExcelReportForm.getWorkLocation()!=null && !guestFacultyExcelReportForm.getWorkLocation().isEmpty()){
		  		workLocationId = Integer.parseInt(guestFacultyExcelReportForm.getWorkLocation());
		  	}
		  		
		  	StringBuffer query = guestFacultyExcelReportHelper.getSelectionSearchCriteria(guestFacultyExcelReportForm,streamId,departmentId,designationId,workLocationId);
		  	List<GuestFaculty> guestFaculties=transaction.getSearchedEmployee(query);
		  	List<GuestFacultyTO> guestFacultyTOs=guestFacultyExcelReportHelper.convertEmployeeBoTOTo(guestFaculties);
		return guestFacultyTOs;
}
	/**
	 * @param employeeReportForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean exportTOExcel(GuestFacultyExcelReportForm guestFacultyExcelReportForm, HttpServletRequest request)throws Exception {
		boolean isUpdated= false;
		GuestFacultyExcelReportHelper guestFacultyExcelReportHelper = GuestFacultyExcelReportHelper.getInstance();
		 int streamId = 0;
		 int departmentId = 0;
		 int designationId = 0;
		 int workLocationId = 0;
		  	if(guestFacultyExcelReportForm.getStreamDetails()!=null && !guestFacultyExcelReportForm.getStreamDetails().isEmpty()){
		  		streamId = Integer.parseInt(guestFacultyExcelReportForm.getStreamDetails());
		  	}
		  	if(guestFacultyExcelReportForm.getDepartment()!=null  && !guestFacultyExcelReportForm.getDepartment().isEmpty()){
		  		departmentId = Integer.parseInt(guestFacultyExcelReportForm.getDepartment());
		  	}
		  	if(guestFacultyExcelReportForm.getDesignation()!=null && !guestFacultyExcelReportForm.getDesignation().isEmpty()){
		  		designationId = Integer.parseInt(guestFacultyExcelReportForm.getDesignation());
		  	}
		  	if(guestFacultyExcelReportForm.getWorkLocation()!=null && !guestFacultyExcelReportForm.getWorkLocation().isEmpty()){
		  		workLocationId = Integer.parseInt(guestFacultyExcelReportForm.getWorkLocation());
		  	}
		  	StringBuffer query = guestFacultyExcelReportHelper.getSelectionSearchCriteria(guestFacultyExcelReportForm,streamId,departmentId,designationId,workLocationId);
		  	List<GuestFaculty> guestFaculties=transaction.getSearchedEmployee(query);
		List<GuestFacultyTO> guestFacultyTOs =guestFacultyExcelReportHelper.convertBOToExcel(guestFaculties,guestFacultyExcelReportForm);
		EmployeeReportTO empReportTo=guestFacultyExcelReportHelper.getSelectedColumns(guestFacultyExcelReportForm);
		isUpdated=guestFacultyExcelReportHelper.convertToExcel(guestFacultyTOs,empReportTo,request,guestFacultyExcelReportForm);
		return isUpdated;
	}

}
