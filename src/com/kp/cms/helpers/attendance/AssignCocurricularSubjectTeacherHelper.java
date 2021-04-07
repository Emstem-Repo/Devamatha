package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.mapping.Array;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.CoCurricularTeacherBO;
import com.kp.cms.bo.admin.CoCurricularTeacherSubjectsBO;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AssignCocurricularSubjectTeacherForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AssignCocurricularSubjectTeacherHandler;
import com.kp.cms.to.attendance.AssignCocurricularSubjectTeacherTO;
import com.sun.corba.se.pept.transport.InboundConnectionCache;

public class AssignCocurricularSubjectTeacherHelper {
	private static volatile AssignCocurricularSubjectTeacherHelper assignCocurricularSubjectTeacherHelper = null;
	private static Log log = LogFactory.getLog(AssignCocurricularSubjectTeacherHelper.class);
	public static AssignCocurricularSubjectTeacherHelper getInstance()
	{
		if(assignCocurricularSubjectTeacherHelper==null)
		{
			assignCocurricularSubjectTeacherHelper = new AssignCocurricularSubjectTeacherHelper();
		}
		return assignCocurricularSubjectTeacherHelper;
	}
	public CoCurricularTeacherBO convertFormToBO(AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm) throws Exception{
		log.debug("call of convertFormToBO method in AssignCocurricularSubjectTeacherHelper.class");
		CoCurricularTeacherBO coCurricularTeacherBO = new CoCurricularTeacherBO();
		try
		{
			
			if(assignCocurricularSubjectTeacherForm.getTeacherID()!=null && !assignCocurricularSubjectTeacherForm.getTeacherID().isEmpty())
			{
				Users users = new Users();
				users.setId(Integer.parseInt(assignCocurricularSubjectTeacherForm.getTeacherID()));
				coCurricularTeacherBO.setUsers(users);
			}
			coCurricularTeacherBO.setCreatedBy(assignCocurricularSubjectTeacherForm.getUserId());
			coCurricularTeacherBO.setCreatedDate(new Date());
			coCurricularTeacherBO.setIsActive(Boolean.TRUE);
			coCurricularTeacherBO.setLastModifiedDate(new Date());
			coCurricularTeacherBO.setModifiedBy(assignCocurricularSubjectTeacherForm.getUserId());
			Set<CoCurricularTeacherSubjectsBO> coCurricularTeacherSubjectsBOs = new HashSet<CoCurricularTeacherSubjectsBO>();
			if(assignCocurricularSubjectTeacherForm.getActivityIds()!= null )
			{
				for(String id : assignCocurricularSubjectTeacherForm.getActivityIds())
				{
					CoCurricularTeacherSubjectsBO subjectsBO = new CoCurricularTeacherSubjectsBO();
					Activity activity = new Activity();
					activity.setId(Integer.parseInt(id));
					subjectsBO.setActivity(activity);
					subjectsBO.setIsActive(Boolean.TRUE);
					subjectsBO.setCreatedBy(assignCocurricularSubjectTeacherForm.getUserId());
					subjectsBO.setCreatedDate(new Date());
					subjectsBO.setLastModifiedDate(new Date());
					subjectsBO.setModifiedBy(assignCocurricularSubjectTeacherForm.getUserId());
					subjectsBO.setCoCurricularTeacherBO(coCurricularTeacherBO);
					coCurricularTeacherSubjectsBOs.add(subjectsBO);
				}
			}
			coCurricularTeacherBO.setCoCurricularTeacherSubjectsBO(coCurricularTeacherSubjectsBOs);
		}
		catch (Exception e) {
			log.error("Error in convertFormToBO method in AssignCocurricularSubjectTeacherHelper.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		
		log.debug("end of convertFormToBO method in AssignCocurricularSubjectTeacherHelper.class");
		return coCurricularTeacherBO;
	}
	public List<AssignCocurricularSubjectTeacherTO> copyBOlisttoTOList(List<CoCurricularTeacherBO> bolist) throws Exception {
		log.debug("call of copyBOlisttoTOList method in AssignCocurricularSubjectTeacherHelper.class");
		List<AssignCocurricularSubjectTeacherTO> toList = new ArrayList<AssignCocurricularSubjectTeacherTO>();
		if(bolist!=null && bolist.size()>0)
		{
			Iterator<CoCurricularTeacherBO> iterator = bolist.iterator();
			while(iterator.hasNext())
			{
				CoCurricularTeacherBO bo = iterator.next();
				String activityName = "";
				AssignCocurricularSubjectTeacherTO to = new AssignCocurricularSubjectTeacherTO();
				to.setId(bo.getId());
				to.setUserName(bo.getUsers().getUserName());
				Set<CoCurricularTeacherSubjectsBO> activitySet = new HashSet<CoCurricularTeacherSubjectsBO>();
				activitySet = bo.getCoCurricularTeacherSubjectsBO();
				Iterator<CoCurricularTeacherSubjectsBO> setIterator = activitySet.iterator();
				while(setIterator.hasNext())
				{
					CoCurricularTeacherSubjectsBO subjectBo = setIterator.next();
					activityName = activityName+ subjectBo.getActivity().getName()+", ";
				}
				to.setActivityNames(activityName);
				toList.add(to);
			}
		}
		log.debug("end of copyBOlisttoTOList method in AssignCocurricularSubjectTeacherHelper.class");
		return toList;
	}
	public void populateDataToForm(CoCurricularTeacherBO coCurricularTeacherBO,	AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm)throws Exception {
		log.debug("call of populateDataToForm method in  AssignCocurricularSubjectTeacherHelper.class");
		Map<Integer, String> userMap = new HashMap<Integer, String>();
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		userMap = AssignCocurricularSubjectTeacherHandler.getInstance().getUsers();
		activityMap = AssignCocurricularSubjectTeacherHandler.getInstance().getActivityMap();
		String[] activityId;
		
		Set<CoCurricularTeacherSubjectsBO> activitySet  = coCurricularTeacherBO.getCoCurricularTeacherSubjectsBO();
		Iterator<CoCurricularTeacherSubjectsBO> iterator = activitySet.iterator();
		activityId = new String[activitySet.size()];
		int count=0;
		while(iterator.hasNext())
		{
			CoCurricularTeacherSubjectsBO subjectsBO = iterator.next();
			activityId[count] = Integer.toString(subjectsBO.getActivity().getId());
			count=count+1;
		}
		assignCocurricularSubjectTeacherForm.setUserMap(userMap);
		assignCocurricularSubjectTeacherForm.setActivityMap(activityMap);
		assignCocurricularSubjectTeacherForm.setTeacherID(Integer.toString(coCurricularTeacherBO.getUsers().getId()));
		assignCocurricularSubjectTeacherForm.setActivityIds(activityId);
		log.debug("end of populateDataToForm method in  AssignCocurricularSubjectTeacherHelper.class");
		
	}

}
