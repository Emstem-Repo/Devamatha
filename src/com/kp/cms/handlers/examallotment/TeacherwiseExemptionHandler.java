package com.kp.cms.handlers.examallotment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.emf.ecore.xmi.impl.EMOFHandler.Helper;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.forms.examallotment.TeacherwiseExemptionForm;
import com.kp.cms.helpers.examallotment.TeacherwiseExemptionHelper;
import com.kp.cms.to.examallotment.ExamInvigilatorDutyEditTo;
import com.kp.cms.to.examallotment.TeacherwiseExemptionTo;
import com.kp.cms.transactions.examallotment.IInvigilatorsForExamTrans;
import com.kp.cms.transactions.examallotment.ITeacherwiseExemptionTrans;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.examallotment.InvigilatorsForExamTransImpl;
import com.kp.cms.transactionsimpl.examallotment.TeacherwiseExemptionTransImpl;
import com.kp.cms.transactionsimpl.studentfeedback.BlockBoImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class TeacherwiseExemptionHandler {
	 IInvigilatorsForExamTrans transaction=InvigilatorsForExamTransImpl.getInstance();
	 TeacherwiseExemptionHelper teacherwiseExemptionHelper=TeacherwiseExemptionHelper.getInstance();
	 ITeacherwiseExemptionTrans teacherwiseTransactions=TeacherwiseExemptionTransImpl.getInstance();
	private static volatile TeacherwiseExemptionHandler teacherwiseExemptionHandler=null;
	/**
	 * instance()
	 * @return
	 */
	private TeacherwiseExemptionHandler(){
		
	}
	public static TeacherwiseExemptionHandler getInstance(){
		if(teacherwiseExemptionHandler == null){
			teacherwiseExemptionHandler=new TeacherwiseExemptionHandler();
		}
		return teacherwiseExemptionHandler;
	}
	public void getAllMaps(TeacherwiseExemptionForm teacherwiseExemptionForm) throws Exception{
		//get academic year
		int academicYear = CurrentAcademicYear.getInstance().getCurrentAcademicYearforTeacher();
		teacherwiseExemptionForm.setAcademicYear(String.valueOf(academicYear));
		teacherwiseExemptionForm.setTempAcademicYear(String.valueOf(academicYear));
		//exam map by academic year
		Map<Integer,String> examMap=transaction.getExam(teacherwiseExemptionForm.getAcademicYear());
		if(examMap!=null && !examMap.isEmpty()){
			teacherwiseExemptionForm.setExamMap(CommonUtil.sortMapByValue(examMap));
		}
		//deanary map
		Map<Integer,String> deanaryMap=transaction.geDeanaryMap();
		if(deanaryMap!=null && !deanaryMap.isEmpty()){
			teacherwiseExemptionForm.setDeanaryMap(CommonUtil.sortMapByValue(deanaryMap));
		}
		//department map
		Map<Integer,String> deptMap=transaction.getDepartmentMap();
		if(deptMap!=null && !deptMap.isEmpty()){
			teacherwiseExemptionForm.setDeptMap(CommonUtil.sortMapByValue(deptMap));
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
			teacherwiseExemptionForm.setWorkLocationMap(CommonUtil.sortMapByValue(workLocationMap));
		}
		
	}
	public void getTeachersList(
			TeacherwiseExemptionForm teacherwiseExemptionForm) throws Exception{
		String query=teacherwiseExemptionHelper.createQuery(teacherwiseExemptionForm);
		List<ExamInviligatorExemptionDatewise> list=teacherwiseTransactions.getTeachersList(query);
		if(list!=null && !list.isEmpty()){
			teacherwiseExemptionForm.setCount(null);
			List<TeacherwiseExemptionTo> teacherwiseExemptionTos=teacherwiseExemptionHelper.convertBosToTos(list);
			teacherwiseExemptionForm.setList(teacherwiseExemptionTos);
		}else{
			teacherwiseExemptionForm.setCount("count");
		}
	}
	public boolean deleteInvigilators(
			TeacherwiseExemptionForm teacherwiseExemptionForm) throws Exception{
			ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise=teacherwiseTransactions.getExamInviligatorExemptionDatewise(teacherwiseExemptionForm.getId());
			ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise1=teacherwiseExemptionHelper.convertBotoBo(examInviligatorExemptionDatewise,teacherwiseExemptionForm.getUserId());
			boolean flag=teacherwiseTransactions.update(examInviligatorExemptionDatewise1);
			if(flag){
				teacherwiseExemptionHelper.removeFromlist(teacherwiseExemptionForm);
			}
		return flag;
	}
	public void addMore(TeacherwiseExemptionForm teacherwiseExemptionForm) throws Exception{
		teacherwiseExemptionHelper.addmore(teacherwiseExemptionForm);
		
	}
	public void deleteMore(TeacherwiseExemptionForm teacherwiseExemptionForm) throws Exception{

		List<TeacherwiseExemptionTo> list=teacherwiseExemptionForm.getAddMorelist();
		if(list.size()>0){
			list.remove(list.size()-1);
			teacherwiseExemptionForm.setFocus("dt_"+(list.size()-1));
		}
		if(list.size()==0){
			teacherwiseExemptionForm.setAddMoreFlag(false);
		}
	}
	public void addTeacherOrUpdate(
			TeacherwiseExemptionForm teacherwiseExemptionForm,
			HttpServletRequest request) throws Exception{
		List<ExamInviligatorExemptionDatewise> list=null;
		if(teacherwiseExemptionForm.getList()!=null && !teacherwiseExemptionForm.getList().isEmpty()){
			list=teacherwiseExemptionHelper.convertTosToBos(teacherwiseExemptionForm,request);
		}
		List<ExamInviligatorExemptionDatewise> list1=null;
		if(teacherwiseExemptionForm.getAddMorelist()!=null && teacherwiseExemptionForm.getAddMorelist().size()>0){
			//check duplicate
			boolean flag1=teacherwiseExemptionHelper.checkDuplicate(teacherwiseExemptionForm,request);
			list1=teacherwiseExemptionHelper.convertAddMoreTosToBos(teacherwiseExemptionForm,request);
		}
		if(list!=null && !list.isEmpty()){
			boolean flag=teacherwiseTransactions.update(list,"update");
			if(flag){
				request.setAttribute("list","success");
			}else{
				request.setAttribute("list","fail");
			}
			
		}
		if(list1!=null && !list1.isEmpty()){
			boolean flag=teacherwiseTransactions.update(list1,"add");
			if(flag){
				request.setAttribute("addMoreList","success");
			}else{
				request.setAttribute("addMoreList","fail");
			}
		}
	}
}
