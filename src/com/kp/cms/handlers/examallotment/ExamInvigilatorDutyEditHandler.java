package com.kp.cms.handlers.examallotment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.forms.examallotment.ExamInvigilatorDutyEditForm;
import com.kp.cms.helpers.examallotment.ExamInvigilatorDutyEditHelper;
import com.kp.cms.to.examallotment.ExamInvigilatorDutyEditTo;
import com.kp.cms.transactions.examallotment.IExamInvigilatorDutyEditTransaction;
import com.kp.cms.transactions.examallotment.IInvigilatorsForExamTrans;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.examallotment.ExamInvigilatorDutyEditTransImpl;
import com.kp.cms.transactionsimpl.examallotment.InvigilatorsForExamTransImpl;
import com.kp.cms.transactionsimpl.studentfeedback.BlockBoImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExamInvigilatorDutyEditHandler {
	 IInvigilatorsForExamTrans transaction=InvigilatorsForExamTransImpl.getInstance();
	 IExamInvigilatorDutyEditTransaction exmDutyEditTransaction=ExamInvigilatorDutyEditTransImpl.getInstance();
	 ExamInvigilatorDutyEditHelper examInvigilatorDutyEditHelper=ExamInvigilatorDutyEditHelper.getInstance();
	private static volatile ExamInvigilatorDutyEditHandler examInvigilatorDutyEditHandler=null;
	/**
	 * instance()
	 * @return
	 */
	private ExamInvigilatorDutyEditHandler(){
		
	}
	public static ExamInvigilatorDutyEditHandler getInstance(){
		if(examInvigilatorDutyEditHandler == null){
			examInvigilatorDutyEditHandler=new ExamInvigilatorDutyEditHandler();
		}
		return examInvigilatorDutyEditHandler;
	}
	public void getAllMaps(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		//get academic year
		int academicYear = CurrentAcademicYear.getInstance().getCurrentAcademicYearforTeacher();
		examInvigilatorDutyEditForm.setAcademicYear(String.valueOf(academicYear));
		examInvigilatorDutyEditForm.setTempAcademicYear(String.valueOf(academicYear));
		//exam map by academic year
		Map<Integer,String> examMap=transaction.getExam(examInvigilatorDutyEditForm.getAcademicYear());
		if(examMap!=null && !examMap.isEmpty()){
			examInvigilatorDutyEditForm.setExamMap(CommonUtil.sortMapByValue(examMap));
		}
		//work location map
		List<EmployeeWorkLocationBO> locationBo = BlockBoImpl.getInstance().getEmpLocation();
		Map<Integer,String> workLocationMap=null;
		if(locationBo!=null){
			workLocationMap=new HashMap<Integer, String>();
			Iterator<EmployeeWorkLocationBO> iterator=locationBo.iterator();
			while (iterator.hasNext()) {
				EmployeeWorkLocationBO employeeWorkLocationBO = (EmployeeWorkLocationBO) iterator.next();
				workLocationMap.put(employeeWorkLocationBO.getId(), employeeWorkLocationBO.getName());
			}
		}
		if(workLocationMap!=null && !workLocationMap.isEmpty()){
			examInvigilatorDutyEditForm.setWorkLocationMap(CommonUtil.sortMapByValue(workLocationMap));
		}
		Map<Integer,String> examSession=transaction.getExamSession();
		if(examSession!=null && !examSession.isEmpty())
			examInvigilatorDutyEditForm.setExamSessionMap(examSession);
	}
	public void getInvigilatorsListToEdit(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		List<ExamInvigilatorDutyEditTo> examInvigilatorDutyEditTos=null;
		String query=examInvigilatorDutyEditHelper.createQuery(examInvigilatorDutyEditForm);
		List<ExamInviligatorDuties> examInviligatorDuties=exmDutyEditTransaction.getInvigilators(query);
		Map<Integer,String> facultyMap=CommonAjaxImpl.getInstance().getFacultyByCampus(examInvigilatorDutyEditForm.getLocationId());
		Map<Integer,String> roomNoMap=CommonAjaxImpl.getInstance().getRoomNosByCampus(examInvigilatorDutyEditForm.getLocationId());
		if(examInviligatorDuties!=null && !examInviligatorDuties.isEmpty()){
			examInvigilatorDutyEditTos=examInvigilatorDutyEditHelper.convertBosToTos(examInviligatorDuties,facultyMap,roomNoMap);
			examInvigilatorDutyEditForm.setList(examInvigilatorDutyEditTos);
		}
	}
	public void getFacultyAndRoomNoByCampus(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		Map<Integer,String> facultyMap=CommonAjaxImpl.getInstance().getFacultyByCampus(examInvigilatorDutyEditForm.getLocationId());
		Map<Integer,String> roomNoMap=CommonAjaxImpl.getInstance().getRoomNosByCampus(examInvigilatorDutyEditForm.getLocationId());
		examInvigilatorDutyEditForm.setFacultyMap(facultyMap);
		examInvigilatorDutyEditForm.setRoomNoMap(roomNoMap);
	}
	public boolean deleteInvigilators(ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		ExamInviligatorDuties examInviligatorDuties=exmDutyEditTransaction.getExamInviligatorDutiesById(examInvigilatorDutyEditForm.getId());
		ExamInviligatorDuties examInviligatorDuties1=convertBoToUpdate(examInviligatorDuties,examInvigilatorDutyEditForm);
		boolean flag=exmDutyEditTransaction.updateInvigilator(examInviligatorDuties1);
		if(flag){
			examInvigilatorDutyEditHandler.removeRecordFromList(examInvigilatorDutyEditForm);
		}
		return flag;
	}
	private ExamInviligatorDuties convertBoToUpdate(
			ExamInviligatorDuties examInviligatorDuties,ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
			examInviligatorDuties.setIsActive(false);
		return examInviligatorDuties;
	}
	private void removeRecordFromList(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		List<ExamInvigilatorDutyEditTo> examInvigilatorDutyEditTos=new ArrayList<ExamInvigilatorDutyEditTo>();
		List<ExamInvigilatorDutyEditTo> list=examInvigilatorDutyEditForm.getList();
		Iterator<ExamInvigilatorDutyEditTo> iterator=list.iterator();
		while (iterator.hasNext()) {
			ExamInvigilatorDutyEditTo examInvigilatorDutyEditTo = (ExamInvigilatorDutyEditTo) iterator.next();
			if(examInvigilatorDutyEditTo.getId()!=examInvigilatorDutyEditForm.getId()){
				examInvigilatorDutyEditTos.add(examInvigilatorDutyEditTo);
			}
		}
		examInvigilatorDutyEditForm.setList(null);
		examInvigilatorDutyEditForm.setList(examInvigilatorDutyEditTos);
	}
	
	public void addMore(ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		Map<Integer,String> facultyMap=CommonAjaxImpl.getInstance().getFacultyByCampus(examInvigilatorDutyEditForm.getLocationId());
		Map<Integer,String> roomNoMap=CommonAjaxImpl.getInstance().getRoomNosByCampus(examInvigilatorDutyEditForm.getLocationId());
		examInvigilatorDutyEditHelper.addMoreList(facultyMap,roomNoMap,examInvigilatorDutyEditForm);
		//examInvigilatorDutyEditHelper.changeTheFlagValue(examInvigilatorDutyEditForm);
	}
	
	public void deleteMore(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		//examInvigilatorDutyEditHelper.changeTheFlagValue(examInvigilatorDutyEditForm);
		List<ExamInvigilatorDutyEditTo> list=examInvigilatorDutyEditForm.getAddMorelist();
		if(list.size()>0){
			list.remove(list.size()-1);
			examInvigilatorDutyEditForm.setFocus("dt_"+(list.size()-1));
		}
		if(list.size()==0){
			examInvigilatorDutyEditForm.setAddMoreFlag(false);
		}
		examInvigilatorDutyEditForm.setAddMoreListSize(examInvigilatorDutyEditForm.getAddMorelist().size());
	}
	public void addInvigilatorOrUpdateIsApproved(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm, HttpServletRequest request) throws Exception{
		List<ExamInviligatorDuties> list=null;
		if(examInvigilatorDutyEditForm.getList()!=null && !examInvigilatorDutyEditForm.getList().isEmpty()){
			list=new ArrayList<ExamInviligatorDuties>();
			ExamInviligatorDuties examInviligatorDuties=null;
			List<ExamInvigilatorDutyEditTo> list2=examInvigilatorDutyEditForm.getList();
			Iterator<ExamInvigilatorDutyEditTo> iterator=list2.iterator();
			while (iterator.hasNext()) {
				ExamInvigilatorDutyEditTo examInvigilatorDutyEditTo = (ExamInvigilatorDutyEditTo) iterator.next();
				examInviligatorDuties=examInvigilatorDutyEditHelper.convertToToBo(examInvigilatorDutyEditTo,examInvigilatorDutyEditForm);
				list.add(examInviligatorDuties);
			}
		}
		List<ExamInviligatorDuties> list1=null;
		if(examInvigilatorDutyEditForm.getAddMorelist()!=null && examInvigilatorDutyEditForm.getAddMorelist().size()>0){
			list1=examInvigilatorDutyEditHelper.convertformToBos(examInvigilatorDutyEditForm,request);
		}
		if(list!=null && !list.isEmpty()){
			boolean flag=exmDutyEditTransaction.update(list,"update");
			if(flag){
				request.setAttribute("list","success");
			}else{
				request.setAttribute("list","fail");
			}
			
		}
		if(list1!=null && !list1.isEmpty()){
			boolean flag=exmDutyEditTransaction.update(list1,"add");
			if(flag){
				request.setAttribute("addMoreList","success");
			}else{
				request.setAttribute("addMoreList","fail");
			}
		}
		examInvigilatorDutyEditForm.setAddNewType(null);
	}
	public boolean approveTheInvigilatorsDuty(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm, HttpServletRequest request) throws Exception{
		boolean flag = false;
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("from ExamInviligatorDuties e where e.isActive=1 and (e.isApproved=0 or e.isApproved is null) and e.examId.id="+Integer.parseInt(examInvigilatorDutyEditForm.getExamId()) +
				" and e.workLocationId.id="+Integer.parseInt(examInvigilatorDutyEditForm.getLocationId()));
		List<ExamInviligatorDuties> examInviligatorDuties=exmDutyEditTransaction.getInvigilators(stringBuilder.toString());
		if(examInviligatorDuties!=null && !examInviligatorDuties.isEmpty()){
			List<ExamInviligatorDuties> list=examInvigilatorDutyEditHelper.changeIsApproved(examInviligatorDuties,examInvigilatorDutyEditForm.getUserId());
			flag=exmDutyEditTransaction.update(list, "update");
		}else{
			request.setAttribute("list","list");
			throw new Exception();
		}
		return flag;
	}
}
