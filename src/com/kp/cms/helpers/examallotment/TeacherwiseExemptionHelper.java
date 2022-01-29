package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.forms.examallotment.TeacherwiseExemptionForm;
import com.kp.cms.to.examallotment.TeacherwiseExemptionTo;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.examallotment.ExamInvigilatorExemptionDatewiseImpl;
import com.kp.cms.utilities.CommonUtil;

public class TeacherwiseExemptionHelper {
	private static volatile TeacherwiseExemptionHelper teacherwiseExemptionHelper=null;
	/**
	 * instance()
	 * @return
	 */
	private TeacherwiseExemptionHelper(){
		
	}
	public static TeacherwiseExemptionHelper getInstance(){
		if(teacherwiseExemptionHelper == null){
			teacherwiseExemptionHelper=new TeacherwiseExemptionHelper();
		}
		return teacherwiseExemptionHelper;
	}
	public String createQuery(TeacherwiseExemptionForm teacherwiseExemptionForm) throws Exception{
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("from ExamInviligatorExemptionDatewise e where e.isActive=1 and e.examId.id="+Integer.parseInt(teacherwiseExemptionForm.getExamId())+
				" and e.teacherId.id="+Integer.parseInt(teacherwiseExemptionForm.getFacultyId()));
		return stringBuilder.toString();
	}
	public List<TeacherwiseExemptionTo> convertBosToTos(
			List<ExamInviligatorExemptionDatewise> list) throws Exception{
		List<TeacherwiseExemptionTo> teacherwiseExemptionTos=new ArrayList<TeacherwiseExemptionTo>();
		Map<Integer,String> map=ExamInvigilatorExemptionDatewiseImpl.getInstance().getsessionMap();
		Iterator<ExamInviligatorExemptionDatewise> iterator=list.iterator();
		TeacherwiseExemptionTo teacherwiseExemptionTo=null;
		while (iterator.hasNext()) {
			ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise = (ExamInviligatorExemptionDatewise) iterator.next();
			teacherwiseExemptionTo=new TeacherwiseExemptionTo();
			teacherwiseExemptionTo.setId(examInviligatorExemptionDatewise.getId());
			teacherwiseExemptionTo.setDate(CommonUtil.formatDates(examInviligatorExemptionDatewise.getDate()));
			teacherwiseExemptionTo.setHiddenDate(CommonUtil.formatDates(examInviligatorExemptionDatewise.getDate()));
			if(examInviligatorExemptionDatewise.getExaminationSessions()!=null){
				teacherwiseExemptionTo.setSession(String.valueOf(examInviligatorExemptionDatewise.getExaminationSessions().getId()));
				teacherwiseExemptionTo.setHiddenSession(String.valueOf(examInviligatorExemptionDatewise.getExaminationSessions().getId()));
			}
			teacherwiseExemptionTo.setFacId(examInviligatorExemptionDatewise.getTeacherId().getId());
			teacherwiseExemptionTo.setSessionMap(map);
			teacherwiseExemptionTo.setCreatedBy(examInviligatorExemptionDatewise.getCreatedBy());
			teacherwiseExemptionTo.setCreatedDate(CommonUtil.formatDates(examInviligatorExemptionDatewise.getCreatedDate()));
			teacherwiseExemptionTos.add(teacherwiseExemptionTo);
		}
		return teacherwiseExemptionTos;
	}
	public ExamInviligatorExemptionDatewise convertBotoBo(
			ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise, String string) throws Exception{
		examInviligatorExemptionDatewise.setIsActive(false);
		examInviligatorExemptionDatewise.setLastModifiedDate(new Date());
		examInviligatorExemptionDatewise.setModifiedBy(string);
		return examInviligatorExemptionDatewise;
	}
	public void removeFromlist(TeacherwiseExemptionForm teacherwiseExemptionForm) {
		List<TeacherwiseExemptionTo> teacherwiseExemptionTos=new ArrayList<TeacherwiseExemptionTo>();
		List<TeacherwiseExemptionTo> list=teacherwiseExemptionForm.getList();
		Iterator<TeacherwiseExemptionTo> iterator=list.iterator();
		while (iterator.hasNext()) {
			TeacherwiseExemptionTo teacherwiseExemptionTo = (TeacherwiseExemptionTo) iterator.next();
			if(teacherwiseExemptionTo.getId()!=teacherwiseExemptionForm.getId()){
				teacherwiseExemptionTos.add(teacherwiseExemptionTo);
			}
		}
		teacherwiseExemptionForm.setList(null);
		teacherwiseExemptionForm.setList(teacherwiseExemptionTos);
	}
	public void addmore(TeacherwiseExemptionForm teacherwiseExemptionForm) throws Exception{
		List<TeacherwiseExemptionTo> list=null;
		if(teacherwiseExemptionForm.getAddMorelist()!=null && !teacherwiseExemptionForm.getAddMorelist().isEmpty()){
			list=teacherwiseExemptionForm.getAddMorelist();
		}else{
			list=new ArrayList<TeacherwiseExemptionTo>();
		}
		TeacherwiseExemptionTo teacherwiseExemptionTo=new TeacherwiseExemptionTo();
		Map<Integer,String> sessionMap=ExamInvigilatorExemptionDatewiseImpl.getInstance().getsessionMap();
		sessionMap=CommonUtil.sortMapByValue(sessionMap);
		teacherwiseExemptionTo.setSessionMap(sessionMap);
		list.add(teacherwiseExemptionTo);
		teacherwiseExemptionForm.setAddMorelist(list);
		teacherwiseExemptionForm.setAddMoreFlag(true);
		teacherwiseExemptionForm.setFocus("dt_"+(list.size()-1));
	}
	public List<ExamInviligatorExemptionDatewise> convertTosToBos(
			TeacherwiseExemptionForm teacherwiseExemptionForm, HttpServletRequest request) throws Exception{
		List<ExamInviligatorExemptionDatewise> list=new ArrayList<ExamInviligatorExemptionDatewise>();
		ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise=null;
		ExamDefinition examDefinition=null;
		Users users=null;
			List<TeacherwiseExemptionTo> list1=teacherwiseExemptionForm.getList();;
			Iterator<TeacherwiseExemptionTo> iterator=list1.iterator();
			while (iterator.hasNext()) {
				TeacherwiseExemptionTo teacherwiseExemptionTo = (TeacherwiseExemptionTo) iterator.next();
				examInviligatorExemptionDatewise=new ExamInviligatorExemptionDatewise();
				examInviligatorExemptionDatewise.setId(teacherwiseExemptionTo.getId());
				examInviligatorExemptionDatewise.setCreatedBy(teacherwiseExemptionTo.getCreatedBy());
				examInviligatorExemptionDatewise.setCreatedDate(CommonUtil.ConvertStringToSQLDate(teacherwiseExemptionTo.getCreatedDate()));
				if(teacherwiseExemptionTo.getDate()!=null && !teacherwiseExemptionTo.getDate().isEmpty()){
					examInviligatorExemptionDatewise.setDate(CommonUtil.ConvertStringToSQLDate(teacherwiseExemptionTo.getDate()));
				}else{
					request.setAttribute("date","date");
					throw new Exception();
				}
				if(teacherwiseExemptionTo.getSession()!=null && !teacherwiseExemptionTo.getSession().isEmpty()){
					ExaminationSessions examinationSessions=new ExaminationSessions();
					examinationSessions.setId(Integer.parseInt(teacherwiseExemptionTo.getSession()));
					examInviligatorExemptionDatewise.setExaminationSessions(examinationSessions);
				}else{
					request.setAttribute("session","session");
					throw new Exception();
				}
				examInviligatorExemptionDatewise.setIsActive(true);
				examInviligatorExemptionDatewise.setLastModifiedDate(new Date());
				examInviligatorExemptionDatewise.setModifiedBy(teacherwiseExemptionForm.getUserId());
				examInviligatorExemptionDatewise.setModifiedBy(teacherwiseExemptionForm.getUserId());
				examDefinition=new ExamDefinition();
				examDefinition.setId(Integer.parseInt(teacherwiseExemptionForm.getExamId()));
				examInviligatorExemptionDatewise.setExamId(examDefinition);
				users=new Users();
				users.setId(Integer.parseInt(teacherwiseExemptionForm.getFacultyId()));
				examInviligatorExemptionDatewise.setTeacherId(users);
				list.add(examInviligatorExemptionDatewise);
			}
		return list;
	}
	public List<ExamInviligatorExemptionDatewise> convertAddMoreTosToBos(
			TeacherwiseExemptionForm teacherwiseExemptionForm, HttpServletRequest request) throws Exception{
		List<ExamInviligatorExemptionDatewise> list=new ArrayList<ExamInviligatorExemptionDatewise>();
		ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise=null;
		ExamDefinition examDefinition=null;
		Users users=null;
			List<TeacherwiseExemptionTo> list1=teacherwiseExemptionForm.getAddMorelist();;
			Iterator<TeacherwiseExemptionTo> iterator=list1.iterator();
			while (iterator.hasNext()) {
				TeacherwiseExemptionTo teacherwiseExemptionTo = (TeacherwiseExemptionTo) iterator.next();
				if(teacherwiseExemptionTo.getDate()!=null && !teacherwiseExemptionTo.getDate().isEmpty()
						&& teacherwiseExemptionTo.getSession()!=null && !teacherwiseExemptionTo.getSession().isEmpty()){
					StringBuilder stringBuilder=new StringBuilder();
					stringBuilder.append("from ExamInviligatorExemptionDatewise e where e.isActive=1 and e.date='"+CommonUtil.ConvertStringToSQLDate(teacherwiseExemptionTo.getDate())+
							"' and e.teacherId.id="+Integer.parseInt(teacherwiseExemptionForm.getFacultyId())+" and e.session='"+teacherwiseExemptionTo.getSession()+"'");
					ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise1=CommonAjaxImpl.getInstance().checkDuplicateExemption(stringBuilder.toString());
					if(examInviligatorExemptionDatewise1!=null){
						request.setAttribute("date", teacherwiseExemptionTo.getDate());
						request.setAttribute("session", teacherwiseExemptionTo.getSession());
						request.setAttribute("list","list");
						throw new Exception();
					}else{
						examInviligatorExemptionDatewise=new ExamInviligatorExemptionDatewise();
						examInviligatorExemptionDatewise.setCreatedBy(teacherwiseExemptionForm.getUserId());
						examInviligatorExemptionDatewise.setCreatedDate(new Date());
						examInviligatorExemptionDatewise.setDate(CommonUtil.ConvertStringToSQLDate(teacherwiseExemptionTo.getDate()));
						if(teacherwiseExemptionTo.getSession()!=null){
							ExaminationSessions examinationSessions=new ExaminationSessions();
							examinationSessions.setId(Integer.parseInt(teacherwiseExemptionTo.getSession()));
							examInviligatorExemptionDatewise.setExaminationSessions(examinationSessions);
						}
						examInviligatorExemptionDatewise.setIsActive(true);
						examInviligatorExemptionDatewise.setLastModifiedDate(new Date());
						examInviligatorExemptionDatewise.setModifiedBy(teacherwiseExemptionForm.getUserId());
						examDefinition=new ExamDefinition();
						examDefinition.setId(Integer.parseInt(teacherwiseExemptionForm.getExamId()));
						examInviligatorExemptionDatewise.setExamId(examDefinition);
						users=new Users();
						users.setId(Integer.parseInt(teacherwiseExemptionForm.getFacultyId()));
						examInviligatorExemptionDatewise.setTeacherId(users);
						list.add(examInviligatorExemptionDatewise);
					}
				}
			}
		return list;
	}
	public boolean checkDuplicate(
			TeacherwiseExemptionForm teacherwiseExemptionForm, HttpServletRequest request) throws Exception{
		boolean flag=false;
		List<TeacherwiseExemptionTo> addMoreList=teacherwiseExemptionForm.getAddMorelist();
		TeacherwiseExemptionTo teacherwiseExemptionTo=null;
		TeacherwiseExemptionTo teacherwiseExemptionTo1=null;
		for(int i=0;i<=addMoreList.size();i++){
			if(addMoreList.size()>i){
				teacherwiseExemptionTo=addMoreList.get(i);
				if(teacherwiseExemptionTo!=null){
					for(int j=i+1;j<=addMoreList.size();j++){
						if(addMoreList.size()>j){
							teacherwiseExemptionTo1=addMoreList.get(j);
							if(teacherwiseExemptionTo1!=null){
								if(teacherwiseExemptionTo.getDate()!=null && teacherwiseExemptionTo.getSession()!=null &&
										teacherwiseExemptionTo1.getDate()!=null && teacherwiseExemptionTo1.getSession()!=null &&
										teacherwiseExemptionTo.getDate().equalsIgnoreCase(teacherwiseExemptionTo1.getDate()) &&
										teacherwiseExemptionTo.getSession().equalsIgnoreCase(teacherwiseExemptionTo1.getSession())){
									request.setAttribute("date", teacherwiseExemptionTo.getDate());
									request.setAttribute("session", teacherwiseExemptionTo.getSession());
									request.setAttribute("duplicate","duplicate");
									throw new Exception();
								}
							}
						}
					}
				}
			}
		}
		flag=true;
		return flag;
	}
}
