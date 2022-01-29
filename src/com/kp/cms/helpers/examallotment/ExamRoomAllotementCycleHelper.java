package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycleDetails;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentCycleForm;
import com.kp.cms.to.examallotment.ExamRoomAllotmentCycleTO;



public class ExamRoomAllotementCycleHelper {
	private static volatile ExamRoomAllotementCycleHelper helper = null;
	public static ExamRoomAllotementCycleHelper getInstance(){
		if(helper == null){
			helper = new ExamRoomAllotementCycleHelper();
		}
		return helper;
	}
	/**
	 * @param midOrEndSem
	 * @param schemeNo
	 * @return
	 */
	public String getExistedRoomCycleHqlQuery( String cycleId, String schemeNo) {
		String query = "from Course course where course.isActive=1 and course.onlyForApplication=0 and course.id not in " +
						" (select cycleDetails.course.id from ExamRoomAllotmentCycleDetails cycleDetails " +
						" where cycleDetails.isActive=1 and cycleDetails.examRoomAllotmentCycle.id="+cycleId+" " +
						" and cycleDetails.schemeNo="+schemeNo+")";
		return query;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamRoomAllotmentCycleDetails> convertFormToBOList( ExamRoomAllotmentCycleForm objForm) throws Exception{
		List<ExamRoomAllotmentCycleDetails> allotmentCycleDetailsList = new ArrayList<ExamRoomAllotmentCycleDetails>();
		for (String  courseId : objForm.getCourseIds()) {
			ExamRoomAllotmentCycleDetails bo = new ExamRoomAllotmentCycleDetails();
			bo.setSchemeNo(objForm.getSchemeNo());
			Course course = new Course();
			course.setId(Integer.parseInt(courseId));
			bo.setCourse(course);
			ExamRoomAllotmentCycle cycleBo = new ExamRoomAllotmentCycle();
			cycleBo.setId(Integer.parseInt(objForm.getCycleId()));
			bo.setExamRoomAllotmentCycle(cycleBo);
			bo.setCreatedBy(objForm.getUserId());
			bo.setCreatedDate(new Date());
			bo.setModifiedBy(objForm.getUserId());
			bo.setLastModifiedDate(new Date());
			bo.setIsActive(true);
			allotmentCycleDetailsList.add(bo);
		}
		return allotmentCycleDetailsList;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public String getRoomAllotCycleDetailsQuery() throws Exception{
		String hqlQuery = "from ExamRoomAllotmentCycle cycle where cycle.isActive=1";
		return hqlQuery;
	}
	/**
	 * @param allotmentCyclesList
	 * @return
	 * @throws Exception
	 */
	public  List<ExamRoomAllotmentCycleTO> convertBoListTOToList( List<ExamRoomAllotmentCycle> allotmentCyclesList) throws Exception{
		List<ExamRoomAllotmentCycleTO> toList = new ArrayList<ExamRoomAllotmentCycleTO>();
		if(allotmentCyclesList!=null && !allotmentCyclesList.isEmpty()){
			Iterator<ExamRoomAllotmentCycle> iterator= allotmentCyclesList.iterator();
			while (iterator.hasNext()) {
				ExamRoomAllotmentCycle bo = (ExamRoomAllotmentCycle) iterator .next();
				ExamRoomAllotmentCycleTO to = new ExamRoomAllotmentCycleTO();
				to.setId(bo.getId());
				String midEndSem= setMidOrEndSem1(bo);
				to.setMidOrEndSem(midEndSem);
				to.setCycleName(bo.getCycle());
				to.setSessionName(bo.getSession());
				if(bo.getExaminationSessions() != null && bo.getExaminationSessions().getId() != 0){
					to.setSessionId(String.valueOf(bo.getExaminationSessions().getId()));
					to.setSessionName(bo.getExaminationSessions().getSession());
				}
				toList.add(to);
			}
		}
		return toList;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public String getDuplicateCheckQuery(ExamRoomAllotmentCycleForm objForm) throws Exception{
		String midEndSem= setMidOrEndSem(objForm);
		String duplicateCheckQuery = "from ExamRoomAllotmentCycle c where c.isActive=1" +
										" and c.midOrEnd='"+midEndSem+"'"+
										" and c.cycle='"+objForm.getCycleName()+"'"+
										" and c.examinationSessions.id='"+objForm.getSessionId()+"'";
		return duplicateCheckQuery;
	}
	/**
	 * @param objForm
	 * @param mode 
	 * @return
	 * @throws Exception
	 */
	public ExamRoomAllotmentCycle convertFormToBO( ExamRoomAllotmentCycleForm objForm, String mode) throws Exception{
		String midEndSem= setMidOrEndSem(objForm);
		ExamRoomAllotmentCycle bo = new ExamRoomAllotmentCycle();
		bo.setId(objForm.getId());
		bo.setCycle(objForm.getCycleName());
		bo.setMidOrEnd(midEndSem);
		bo.setSession(objForm.getSessionName());
		bo.setIsActive(true);
		if(mode.equalsIgnoreCase("Add")){
			bo.setCreatedBy(objForm.getUserId());
			bo.setCreatedDate(new Date());
		}else if(mode.equalsIgnoreCase("Edit")){
			bo.setModifiedBy(objForm.getUserId());
			bo.setLastModifiedDate(new Date());
		}
		if(objForm.getSessionId() != null){
			ExaminationSessions sessions = new ExaminationSessions();
			sessions.setId(Integer.parseInt(objForm.getSessionId()));
			bo.setExaminationSessions(sessions);
		}
		return bo;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public String setMidOrEndSem(ExamRoomAllotmentCycleForm objForm)throws Exception {
		String midEndSem = null;
		if(objForm.getMidOrEndSem().equalsIgnoreCase("Mid Sem")){
			midEndSem = "M";
		}else if(objForm.getMidOrEndSem().equalsIgnoreCase("End Sem")){
			midEndSem = "E";
		}
		return midEndSem;
	}
	/**
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public String setMidOrEndSem1(ExamRoomAllotmentCycle bo)throws Exception {
		String midEndSem = null;
		if(bo.getMidOrEnd().equalsIgnoreCase("M")){
			midEndSem = "Mid Sem";
		}else if(bo.getMidOrEnd().equalsIgnoreCase("E")){
			midEndSem = "End Sem";
		}
		return midEndSem;
	}
	/**
	 * @return
	 * @throws Exception
	 *//*
	public String getCycleDetailsQuery()throws Exception {
		String hqlQuery = "from ExamRoomAllotmentCycle cycle where cycle.isActive=1";
		return hqlQuery;
	}*/
	/**
	 * @param boList
	 * @return
	 */
	public Map<Integer, String> convertBoListToMap( List<ExamRoomAllotmentCycle> boList) {
		Map<Integer, String> cycleMap = new HashMap<Integer, String>();		
		if(boList!=null && !boList.isEmpty()){
			Iterator<ExamRoomAllotmentCycle> iterator = boList.iterator();
			while (iterator.hasNext()) {
				ExamRoomAllotmentCycle bo = (ExamRoomAllotmentCycle) iterator .next();
				cycleMap.put(bo.getId(), bo.getCycle());
			}
		}
		return cycleMap;
	}
	/**
	 * @param cycleDetailsBoList
	 * @return
	 */
	public Map<Integer, Map<String, Map<String, List<Integer>>>> convertBoListToMapList( List<ExamRoomAllotmentCycleDetails> cycleDetailsBoList) throws Exception{
		Map<Integer, Map<String, Map<String, List<Integer>>>>  cycleDetailMap =null;
		if(cycleDetailsBoList!= null && !cycleDetailsBoList.isEmpty()){
			cycleDetailMap = new HashMap<Integer, Map<String,Map<String,List<Integer>>>>();
			Iterator<ExamRoomAllotmentCycleDetails> iterator = cycleDetailsBoList.iterator();
			while (iterator.hasNext()) {
				ExamRoomAllotmentCycleDetails bo = (ExamRoomAllotmentCycleDetails) iterator .next();
				if(!cycleDetailMap.containsKey(bo.getExamRoomAllotmentCycle().getId())){
					Map<String, Map<String, List<Integer>>> cycleMap = new HashMap<String, Map<String,List<Integer>>>();
					Map<String,List<Integer>>  schemeMap  = new HashMap<String, List<Integer>>();
					List<Integer> courseIds = new ArrayList<Integer>();
					courseIds.add(bo.getCourse().getId());
					schemeMap.put(bo.getSchemeNo(),courseIds );
					cycleMap.put(bo.getExamRoomAllotmentCycle().getCycle(), schemeMap);
					cycleDetailMap.put(bo.getExamRoomAllotmentCycle().getId(), cycleMap);
				}else{
					Map<String, Map<String, List<Integer>>> cycleMap = cycleDetailMap.get(bo.getExamRoomAllotmentCycle().getId());
					if(!cycleMap.containsKey(bo.getExamRoomAllotmentCycle().getCycle())){
						Map<String, Map<String, List<Integer>>> cycleMap1 = new HashMap<String, Map<String,List<Integer>>>();
						Map<String, List<Integer>> schemeMap = new HashMap<String, List<Integer>>();
						List<Integer> courseIds = new ArrayList<Integer>();
						courseIds.add(bo.getCourse().getId());
						schemeMap.put(bo.getSchemeNo(),courseIds );
						cycleMap1.put(bo.getExamRoomAllotmentCycle().getCycle(), schemeMap);
						cycleDetailMap.put(bo.getExamRoomAllotmentCycle().getId(), cycleMap1);
					}else{
						Map<String, List<Integer>> schemeMap = cycleMap.get(bo.getExamRoomAllotmentCycle().getCycle());
						if(!schemeMap.containsKey(bo.getSchemeNo())){
//							Map<String, List<Integer>> schemeMap1 = new HashMap<String, List<Integer>>();
							 List<Integer> courseIds = new ArrayList<Integer>();
							 courseIds.add(bo.getCourse().getId());
							 schemeMap.put(bo.getSchemeNo(), courseIds);
							 cycleMap.put(bo.getExamRoomAllotmentCycle().getCycle(), schemeMap);
							 cycleDetailMap.put(bo.getExamRoomAllotmentCycle().getId(), cycleMap);
						}else{
							List<Integer> courseIds = schemeMap.get(bo.getSchemeNo());
							if(!courseIds.contains(bo.getCourse().getId())){
								courseIds.add(bo.getCourse().getId());
								schemeMap.put(bo.getSchemeNo(), courseIds);
								cycleMap.put(bo.getExamRoomAllotmentCycle().getCycle(), schemeMap);
								cycleDetailMap.put(bo.getExamRoomAllotmentCycle().getId(), cycleMap);
							}
						}
					}
				}
			}
		}
		return cycleDetailMap;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public String getCycleDetailsQuery(ExamRoomAllotmentCycleForm objForm)throws Exception {
		String query = "from ExamRoomAllotmentCycleDetails cycleDetails " +
		" where cycleDetails.isActive=1 and cycleDetails.examRoomAllotmentCycle.id="+Integer.parseInt(objForm.getCycleId())+" " +
		" and cycleDetails.schemeNo="+Integer.parseInt(objForm.getSchemeNo());
		return query;
	}
	/**
	 * @param existBoList
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamRoomAllotmentCycleDetails> updateCycleDetailsList( List<ExamRoomAllotmentCycleDetails> existBoList,
			ExamRoomAllotmentCycleForm objForm)throws Exception {
		List<ExamRoomAllotmentCycleDetails> updateBoList = new ArrayList<ExamRoomAllotmentCycleDetails>();
		String[] courseIds = objForm.getCourseIds();
		if(courseIds!=null && !courseIds.toString().isEmpty()){
			List<String> courseList = new ArrayList<String>(Arrays.asList(courseIds));
			if(existBoList!=null && !existBoList.isEmpty()){
				Iterator<ExamRoomAllotmentCycleDetails> iterator = existBoList.iterator();
				while (iterator.hasNext()) {
					ExamRoomAllotmentCycleDetails bo = (ExamRoomAllotmentCycleDetails) iterator .next();
					if(!courseList.contains(String.valueOf(bo.getCourse().getId()))){
						bo.setIsActive(false);
						bo.setModifiedBy(objForm.getUserId());
						bo.setLastModifiedDate(new Date());
					}else{
						bo.setModifiedBy(objForm.getUserId());
						bo.setLastModifiedDate(new Date());
						courseList.remove(String.valueOf(bo.getCourse().getId()));
					}
					updateBoList.add(bo);
				}
			}
			if(courseList!=null && !courseList.isEmpty()){
				for (String courseId : courseList) {
					ExamRoomAllotmentCycleDetails bo1 =  new ExamRoomAllotmentCycleDetails();
					Course course = new Course();
					course.setId(Integer.parseInt(courseId));
					bo1.setCourse(course);
					ExamRoomAllotmentCycle cycle = new ExamRoomAllotmentCycle();
					cycle.setId(Integer.parseInt(objForm.getCycleId()));
					bo1.setExamRoomAllotmentCycle(cycle);
					bo1.setSchemeNo(objForm.getSchemeNo());
					bo1.setCreatedBy(objForm.getUserId());
					bo1.setCreatedDate(new Date());
					bo1.setModifiedBy(objForm.getUserId());
					bo1.setLastModifiedDate(new Date());
					bo1.setIsActive(true);
					updateBoList.add(bo1);
				}
			}
		}else if(existBoList!=null && !existBoList.isEmpty()){
			Iterator<ExamRoomAllotmentCycleDetails> iterator = existBoList.iterator();
			while (iterator.hasNext()) {
				ExamRoomAllotmentCycleDetails cycleBo = (ExamRoomAllotmentCycleDetails) iterator .next();
				cycleBo.setIsActive(false);
				cycleBo.setModifiedBy(objForm.getUserId());
				cycleBo.setLastModifiedDate(new Date());
				updateBoList.add(cycleBo);
			}
		}
		return updateBoList;
	}
}
