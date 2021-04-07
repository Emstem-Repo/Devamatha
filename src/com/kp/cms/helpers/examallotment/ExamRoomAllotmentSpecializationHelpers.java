package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentSpecialization;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentSpecializationForm;
import com.kp.cms.to.examallotment.ExamRoomAllotmentSpecializationTo;

public class ExamRoomAllotmentSpecializationHelpers {
	private static volatile ExamRoomAllotmentSpecializationHelpers helpers = null;
	public static ExamRoomAllotmentSpecializationHelpers getInstance(){
		if(helpers == null){
			helpers = new ExamRoomAllotmentSpecializationHelpers();
		}
		return helpers;
	}
	/**
	 * @param objForm
	 * @param existDataMap 
	 * @return
	 * @throws Exception
	 */
	public List<ExamRoomAllotmentSpecialization> createSpecializationBOList( ExamRoomAllotmentSpecializationForm objForm, Map<Integer, ExamRoomAllotmentSpecialization> existDataMap) 
		throws Exception{
		List<ExamRoomAllotmentSpecialization> boList = new ArrayList<ExamRoomAllotmentSpecialization>();
		String[] coursesList = objForm.getSelectedCourses();
		String midOrEnd = null;
		if(objForm.getMidEndSem().equalsIgnoreCase("Mid Sem")){
			midOrEnd = "M";
		}else if(objForm.getMidEndSem().equalsIgnoreCase("End Sem")){
			midOrEnd = "E";
		}
		if(coursesList!=null && !coursesList.toString().isEmpty()){
				for(int i=0;i<coursesList.length;i++){
					ExamRoomAllotmentSpecialization bo = null;
					int courseId = Integer.parseInt(coursesList[i]);
					if(existDataMap!=null && !existDataMap.isEmpty()){
						if(existDataMap.containsKey(courseId)){
							 bo = existDataMap.get(courseId);
							 bo.setLastModifiedDate(new Date());
							 bo.setModifiedBy(objForm.getUserId());
							 boList.add(bo);
						}
						else{
							bo = new ExamRoomAllotmentSpecialization();
							bo.setMidOrEndSem(midOrEnd);
							bo.setSchemeNo(Integer.parseInt(objForm.getSchemeNo()));
							Course course = new Course();
							course.setId(courseId);
							bo.setCourse(course);
							bo.setCreatedBy(objForm.getUserId());
							bo.setCreatedDate(new Date());
							bo.setModifiedBy(objForm.getUserId());
							bo.setLastModifiedDate(new Date());
							bo.setIsActive(true);
							boList.add(bo);
						}
					}else{
						bo = new ExamRoomAllotmentSpecialization();
						bo.setMidOrEndSem(midOrEnd);
						bo.setSchemeNo(Integer.parseInt(objForm.getSchemeNo()));
						Course course = new Course();
						course.setId(courseId);
						bo.setCourse(course);
						bo.setCreatedBy(objForm.getUserId());
						bo.setCreatedDate(new Date());
						bo.setModifiedBy(objForm.getUserId());
						bo.setLastModifiedDate(new Date());
						bo.setIsActive(true);
						boList.add(bo);
					}
				}
			}
		return boList;
	}
	/**
	 * @param existedBOList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, ExamRoomAllotmentSpecialization> convertBOListToMap( List<ExamRoomAllotmentSpecialization> existedBOList) throws Exception{
		Map<Integer, ExamRoomAllotmentSpecialization> existsMap = new HashMap<Integer, ExamRoomAllotmentSpecialization>();
		if(existedBOList!=null && !existedBOList.isEmpty()){
			Iterator<ExamRoomAllotmentSpecialization> iterator = existedBOList.iterator();
			while (iterator.hasNext()) {
				ExamRoomAllotmentSpecialization bo = (ExamRoomAllotmentSpecialization) iterator .next();
				existsMap.put(bo.getCourse().getId(), bo);
			}
		}
		return existsMap;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public String getExamSpecializationDataQuery(
			ExamRoomAllotmentSpecializationForm objForm) throws Exception {
		String hqlQuery = "from ExamRoomAllotmentSpecialization spe where spe.isActive=1 and spe.midOrEndSem='"
							+ objForm.getMidEndSem() + "'"+" order by spe.schemeNo";
		return hqlQuery;
	}
	/**
	 * @param boList
	 * @return
	 * @throws Exception
	 */
	public Map<String, Map<Integer,ExamRoomAllotmentSpecializationTo>> populateBOListTOMap( List<ExamRoomAllotmentSpecialization> boList) throws Exception{
		Map<String, Map<Integer, ExamRoomAllotmentSpecializationTo>> midOrEndsemMap = new HashMap<String, Map<Integer,ExamRoomAllotmentSpecializationTo>>();
		String midorEnd = null;
		if(boList!=null && !boList.isEmpty()){
			Iterator<ExamRoomAllotmentSpecialization> iterator = boList.iterator();
			while (iterator.hasNext()) {
				Map<Integer, ExamRoomAllotmentSpecializationTo> schemeMap = null;
				ExamRoomAllotmentSpecialization bo = (ExamRoomAllotmentSpecialization) iterator .next();
				if(bo.getMidOrEndSem().equalsIgnoreCase("M")){
					midorEnd = "Mid Sem";
				}else if(bo.getMidOrEndSem().equalsIgnoreCase("E")){
					midorEnd= "End Sem";
				}
				if(!midOrEndsemMap.containsKey(midorEnd)){
					schemeMap = new HashMap<Integer, ExamRoomAllotmentSpecializationTo>();
					ExamRoomAllotmentSpecializationTo to = new ExamRoomAllotmentSpecializationTo();
					to.setMidEndSem(midorEnd);
					to.setSchemeNo(bo.getSchemeNo());
					List<String> courseName = new ArrayList<String>();
					courseName.add(bo.getCourse().getName());
					to.setCourseNames(courseName);
					List<Integer> courseIds = new ArrayList<Integer>();
					courseIds.add(bo.getCourse().getId());
					to.setCourseIds(courseIds);
					schemeMap.put(bo.getSchemeNo(), to);
				}else{
					ExamRoomAllotmentSpecializationTo to = null;
					schemeMap = midOrEndsemMap.get(midorEnd);
					if(!schemeMap.containsKey(bo.getSchemeNo())){
						to = new ExamRoomAllotmentSpecializationTo();
						to.setMidEndSem(midorEnd);
						to.setSchemeNo(bo.getSchemeNo());
						List<String> courseName = new ArrayList<String>();
						courseName.add(bo.getCourse().getName());
						to.setCourseNames(courseName);
						List<Integer> courseIds = new ArrayList<Integer>();
						courseIds.add(bo.getCourse().getId());
						to.setCourseIds(courseIds);
					}else{
						to = schemeMap.get(bo.getSchemeNo());
						List<String> courseName = to.getCourseNames();
						courseName.add(bo.getCourse().getName());
						to.setCourseNames(courseName);
						List<Integer> courseIds = to.getCourseIds();
						courseIds.add(bo.getCourse().getId());
						to.setCourseIds(courseIds);
					}
					schemeMap.put(bo.getSchemeNo(), to);
				}
				
				
				midOrEndsemMap.put(midorEnd, schemeMap);
				
			}
		}
		return midOrEndsemMap;
	}
	/**
	 * @param semWiseMap
	 * @return
	 * @throws Exception
	 */
	public List<ExamRoomAllotmentSpecialization> updateSpecializationBoList( ExamRoomAllotmentSpecializationForm objForm,
			List<ExamRoomAllotmentSpecialization> existedBOList) throws Exception{
		List<ExamRoomAllotmentSpecialization> boList = new ArrayList<ExamRoomAllotmentSpecialization>();
		String [] strings = objForm.getSelectedCourses();
		if(strings!=null && !strings.toString().isEmpty()){
			List<String> coursesList = new ArrayList<String>(Arrays.asList(strings)); 
			if(existedBOList!=null && !existedBOList.isEmpty()){
				Iterator<ExamRoomAllotmentSpecialization> iterator = existedBOList.iterator();
				while (iterator.hasNext()) {
					ExamRoomAllotmentSpecialization bo = (ExamRoomAllotmentSpecialization) iterator .next();
					if(!coursesList.contains(String.valueOf(bo.getCourse().getId()))){
						bo.setIsActive(false);
						bo.setModifiedBy(objForm.getUserId());
						bo.setLastModifiedDate(new Date());
					}else{
						bo.setModifiedBy(objForm.getUserId());
						bo.setLastModifiedDate(new Date());
						coursesList.remove(String.valueOf(bo.getCourse().getId()));
					}
					boList.add(bo);
				}
			}
			
			if(coursesList!=null && !coursesList.isEmpty()){
				String midOrEnd = null;
				if(objForm.getMidEndSem().equalsIgnoreCase("Mid Sem")){
					midOrEnd = "M";
				}else if(objForm.getMidEndSem().equalsIgnoreCase("End Sem")){
					midOrEnd = "E";
				}
				Iterator<String> iterator = coursesList.iterator();
				while (iterator.hasNext()) {
					ExamRoomAllotmentSpecialization bo = null;
					String courseId = (String) iterator.next();
					bo = new ExamRoomAllotmentSpecialization();
					bo.setMidOrEndSem(midOrEnd);
					bo.setSchemeNo(Integer.parseInt(objForm.getSchemeNo()));
					Course course = new Course();
					course.setId(Integer.parseInt(courseId));
					bo.setCourse(course);
					bo.setCreatedBy(objForm.getUserId());
					bo.setCreatedDate(new Date());
					bo.setModifiedBy(objForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bo.setIsActive(true);
					boList.add(bo);
				}
			}
		}else if(existedBOList!=null && !existedBOList.isEmpty()){
			Iterator<ExamRoomAllotmentSpecialization> iterator = existedBOList.iterator();
			while (iterator.hasNext()) {
				ExamRoomAllotmentSpecialization bo = (ExamRoomAllotmentSpecialization) iterator .next();
				bo.setIsActive(false);
				bo.setLastModifiedDate(new Date());
				bo.setModifiedBy(objForm.getUserId());
				boList.add(bo);
			}
		}
		return boList;
	}
}
