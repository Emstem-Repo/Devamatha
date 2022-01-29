package com.kp.cms.handlers.timetable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.timetable.ViewTeacherWiseTimeTableForm;
import com.kp.cms.helpers.timetable.ViewTeacherWiseTimeTableHelper;
import com.kp.cms.to.timetable.DepartentTimeTableClassTo;
import com.kp.cms.to.timetable.DepartmentTimeTablePeriodTo;
import com.kp.cms.to.timetable.TimeTableClassTo;
import com.kp.cms.to.timetable.TimeTablePeriodTo;
import com.kp.cms.transactions.timetable.IViewTeacherWiseTimeTableTransactions;
import com.kp.cms.transactionsimpl.timetable.ViewTeacherWiseTimeTableTxnImpl;

public class ViewTeacherWiseTimeTableHandler {
	
	private static volatile ViewTeacherWiseTimeTableHandler timeTableHandler =null;
	/**
	 * private Constructor is defined, avoid to create object anywhere for these class.
	 */
	private ViewTeacherWiseTimeTableHandler(){
		
	}
	/** SingleTon Object Created for Class.
	 * @return
	 */
	public static  ViewTeacherWiseTimeTableHandler getInstance(){
		if(timeTableHandler == null){
			timeTableHandler = new ViewTeacherWiseTimeTableHandler();
		}
		return timeTableHandler;
	}
	IViewTeacherWiseTimeTableTransactions transaction = ViewTeacherWiseTimeTableTxnImpl.getInstance();
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void getTeacherWiseTimeTableDetails( ViewTeacherWiseTimeTableForm objForm) throws Exception{
		String query = ViewTeacherWiseTimeTableHelper.getInstance().getTimeTableQueryByTeacherWise(objForm);
		List<Object[]> list = transaction.getListDataByQuery(query);
		Map<String,Map<Integer,TimeTablePeriodTo>> weekDayMap = ViewTeacherWiseTimeTableHelper.getInstance().convertListToMap(list,objForm,null);
		
		List<Period> periodList=setRequiredTimeTableData(objForm);
		
		List<TimeTableClassTo> classTOList = ViewTeacherWiseTimeTableHelper.getInstance().createTimeTableList(weekDayMap,periodList,objForm.getPeriodList());
		if(classTOList!=null && !classTOList.isEmpty()){
			objForm.setTimeTableList(classTOList);
		}
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	private List<Period> setRequiredTimeTableData( ViewTeacherWiseTimeTableForm objForm) throws Exception{
		List<Period> periodList = null;
		List<Period> periodListGroup = null;
		if(objForm.getPeriodId()!=null && !objForm.getPeriodId().isEmpty()){
			periodList = transaction.getPeriodLists(objForm.getPeriodId());
			periodListGroup = transaction.getPeriodGroupList(objForm.getPeriodId());
		}
		if(periodListGroup!=null && !periodListGroup.isEmpty()){
			
			Collections.sort(periodListGroup);
		}
		objForm.setPeriodList(periodListGroup);
		return periodList;
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void getStudentTimeTableDetails(String studentId,String classId,ViewTeacherWiseTimeTableForm objForm) throws Exception{
		String query = ViewTeacherWiseTimeTableHelper.getInstance().getStudentTimeTableQuery(studentId,classId);
		List<Object[]> list = transaction.getListDataByQuery(query);
		Map<String,Map<Integer,TimeTablePeriodTo>> weekDayMap = ViewTeacherWiseTimeTableHelper.getInstance().convertListToMap(list,objForm,"student");
		List<Period> periodList=setRequiredTimeTableData(objForm);
		String studentBatchQuery = ViewTeacherWiseTimeTableHelper.getInstance().getStudentBatchSubjectsQuery(studentId,classId);
		List<Object[]> batchList = transaction.getListDataByQuery(studentBatchQuery);
		Map<String,Map<Integer,TimeTablePeriodTo>> batchWeekDayMap = ViewTeacherWiseTimeTableHelper.getInstance().convertListToMap(batchList,objForm,"student");
		List<TimeTableClassTo> classTOList = ViewTeacherWiseTimeTableHelper.getInstance().convertMapToTOList(weekDayMap,periodList,batchWeekDayMap);
		if(classTOList!=null && !classTOList.isEmpty()){
			objForm.setTimeTableList(classTOList);
		}
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void getClassTimeTableData(ViewTeacherWiseTimeTableForm objForm) throws Exception{
		
		String classTimeTableQuery = ViewTeacherWiseTimeTableHelper.getInstance().getClassTimeTableQuery(objForm);
		List<Object[]> list = transaction.getListDataByQuery1(classTimeTableQuery);
		Map<String,Map<Integer,TimeTablePeriodTo>> weekDayMap = ViewTeacherWiseTimeTableHelper.getInstance().convertListToMap(list,objForm,"teacher");
		
		List<Period> periodList=setRequiredTimeTableData(objForm);
		
		List<TimeTableClassTo> classTOList = ViewTeacherWiseTimeTableHelper.getInstance().createTimeTableList(weekDayMap,periodList,objForm.getPeriodList());
		if(classTOList!=null && !classTOList.isEmpty()){
			objForm.setTimeTableList(classTOList);
		}
	}
	
public void getDeptTimeTableData(ViewTeacherWiseTimeTableForm objForm) throws Exception{
		
		String deptTimeTableQuery = ViewTeacherWiseTimeTableHelper.getInstance().getDeptTimeTableQuery(objForm);
		List<Object[]> list = transaction.getDeptWiseTimeTable(deptTimeTableQuery);
		Map<String,Map<Integer,DepartmentTimeTablePeriodTo>> weekDayMap = ViewTeacherWiseTimeTableHelper.getInstance().convertListToMapForDept(list,objForm,"teacher");
		
		List<Period> periodList=setRequiredTimeTableData(objForm);
		
		List<DepartentTimeTableClassTo> classTOList = ViewTeacherWiseTimeTableHelper.getInstance().createTimeTableListForDepartment(weekDayMap,periodList,objForm.getPeriodList());
		if(classTOList!=null && !classTOList.isEmpty()){
			objForm.setDepartmentTimeTableList(classTOList);
		}
	}
}
