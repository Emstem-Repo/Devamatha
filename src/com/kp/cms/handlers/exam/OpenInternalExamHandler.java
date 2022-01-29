package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.OpenInternalExamForClasses;
import com.kp.cms.bo.exam.OpenInternalMark;
import com.kp.cms.forms.exam.OpenInternalExamForm;
import com.kp.cms.helpers.exam.ExamGenHelper;
import com.kp.cms.to.exam.ExamDefinitionTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.OpenInternalExamImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class OpenInternalExamHandler {
	private static final Log log = LogFactory.getLog(OpenInternalExamHandler.class);
	private static volatile OpenInternalExamHandler instance=null;
	ExamGenHelper helper = new ExamGenHelper();
	public static OpenInternalExamHandler getInstance(){
		log.info("Start getInstance of ExamDefinitionHandler");
		if(instance==null){
			instance=new OpenInternalExamHandler();
		}
		log.info("End getInstance of ExamDefinitionHandler");
		return instance;
	}
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	OpenInternalExamImpl impl = OpenInternalExamImpl.getInstance();
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveDetails(OpenInternalExamForm objForm) throws Exception{
		String[] stayClass = objForm.getStayClass();
		String[] classId=stayClass[0].split(",");
		Set<OpenInternalExamForClasses> set = new HashSet<OpenInternalExamForClasses>();
		OpenInternalMark bo = new OpenInternalMark();
		ExamDefinition exam = new ExamDefinition();
		exam.setId(Integer.parseInt(objForm.getExamId()));
		bo.setExam(exam);
		ProgramType type = new ProgramType();
		type.setId(Integer.parseInt(objForm.getNewProgramTypeId()));
		bo.setProgramType(type);
		bo.setStartDate(CommonUtil.ConvertStringToSQLDate(objForm.getStartDate()));
		bo.setEndDate(CommonUtil.ConvertStringToSQLDate(objForm.getEndDate()));
		bo.setCreatedBy(objForm.getUserId());
		bo.setModifiedBy(objForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setLastModifiedDate(new Date());
		bo.setIsActive(true);
		bo.setTheoryPractical(objForm.getTheoryPractical());
		for (int i = 0; i < classId.length; i++) {
			OpenInternalExamForClasses classes = new OpenInternalExamForClasses();
			Classes cls = new Classes();
			cls.setId(Integer.parseInt(classId[i]));
			classes.setClasses(cls);
			classes.setCreatedBy(objForm.getUserId());
			classes.setModifiedBy(objForm.getUserId());
			classes.setCreatedDate(new Date());
			classes.setLastModifiedDate(new Date());
			classes.setIsActive(true);
			set.add(classes);
		}
		/* new property is added by sudhir*/
		bo.setDisplayName(objForm.getDisplayName());
		/*--------------------------------*/
		bo.setClassesSet(set);
		return impl.saveExam(bo,"add");
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ExamDefinitionTO> getExamList() throws Exception{
		List<ExamDefinitionTO> examList = new ArrayList<ExamDefinitionTO>();
		List<OpenInternalMark> boList = impl.getExamList();
		if(boList != null){
			Iterator<OpenInternalMark> iterator = boList.iterator();
			while (iterator.hasNext()) {
				OpenInternalMark openInternalMark = (OpenInternalMark) iterator.next();
				ExamDefinitionTO to = new ExamDefinitionTO();
				to.setId(openInternalMark.getId());
				to.setExamName(openInternalMark.getExam().getName());
				to.setStartDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(openInternalMark.getStartDate()), OpenInternalExamHandler.SQL_DATEFORMAT,	OpenInternalExamHandler.FROM_DATEFORMAT));
				to.setEndDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(openInternalMark.getEndDate()), OpenInternalExamHandler.SQL_DATEFORMAT,	OpenInternalExamHandler.FROM_DATEFORMAT));
				if(openInternalMark.getTheoryPractical().equalsIgnoreCase("T")){
					to.setTheoryPractical("Theory");
				}else if(openInternalMark.getTheoryPractical().equalsIgnoreCase("P")){
					to.setTheoryPractical("Practical");
				}else{
					to.setTheoryPractical("Theory And Practical");
				}
				if(openInternalMark.getProgramType() != null && openInternalMark.getProgramType().getName() != null){
					to.setProgramType(openInternalMark.getProgramType().getName());
				}
				/* new property added by sudhir */
				if(openInternalMark.getDisplayName()!=null && !openInternalMark.getDisplayName().isEmpty()){
					to.setDisplayExamName(openInternalMark.getDisplayName());
				}
				examList.add(to);
			}
		}
		return examList;
	}
	/**
	 * @param objForm
	 * @param session 
	 * @return
	 * @throws Exception
	 */
	public String checkDuplicate(OpenInternalExamForm objForm, HttpSession session) throws Exception{
		String msg = "";
		/*if(objForm.getClasses() != null && objForm.getClasses().length !=0){
			msg = impl.checkDuplicate(objForm,msg);
		}*/		 
		String[] stayClass = objForm.getStayClass();
		if(stayClass != null && stayClass.length !=0){
			msg = impl.checkDuplicate(objForm,msg,session);
		}
		return msg;
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void setExamDetailsToForm(OpenInternalExamForm objForm) throws Exception{
		Map<Integer,String> selClassesMap=new HashMap<Integer, String>();
		OpenInternalMark openInternalMark  = impl.getExam(objForm.getId());
		if(openInternalMark != null){
			objForm.setExamId(String.valueOf(openInternalMark.getExam().getId()));
			objForm.setStartDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(openInternalMark.getStartDate()), OpenInternalExamHandler.SQL_DATEFORMAT,	OpenInternalExamHandler.FROM_DATEFORMAT));
			objForm.setEndDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(openInternalMark.getEndDate()), OpenInternalExamHandler.SQL_DATEFORMAT,	OpenInternalExamHandler.FROM_DATEFORMAT));
			objForm.setAcademicYear(String.valueOf(openInternalMark.getExam().getAcademicYear()));
			objForm.setTheoryPractical(openInternalMark.getTheoryPractical());
			if(openInternalMark.getProgramType() != null && openInternalMark.getProgramType().getName() != null){
				objForm.setNewProgramTypeId(String.valueOf(openInternalMark.getProgramType().getId()));
			}
			if(openInternalMark.getClassesSet() != null && !openInternalMark.getClassesSet().isEmpty()){
				Iterator<OpenInternalExamForClasses> iterator = openInternalMark.getClassesSet().iterator();
				StringBuilder classes = new StringBuilder();
				boolean isFirst = true;
				Map<Integer, Integer> classesMap = new HashMap<Integer, Integer>(); 
				Map<Integer, Integer> totClassesMap = new HashMap<Integer, Integer>(); 
				while (iterator.hasNext()) {
					OpenInternalExamForClasses openInternalExamForClasses = (OpenInternalExamForClasses) iterator.next();
					totClassesMap.put(openInternalExamForClasses.getClasses().getId(), openInternalExamForClasses.getId());
					if(openInternalExamForClasses.getIsActive()){
						classesMap.put(openInternalExamForClasses.getClasses().getId(), openInternalExamForClasses.getId());
						if(isFirst){
							classes.append(openInternalExamForClasses.getClasses().getId());
							selClassesMap.put(openInternalExamForClasses.getClasses().getId(), openInternalExamForClasses.getClasses().getName());
							isFirst = false;
						}else{
							classes.append(",").append(openInternalExamForClasses.getClasses().getId());
							selClassesMap.put(openInternalExamForClasses.getClasses().getId(), openInternalExamForClasses.getClasses().getName());
						}
					}
				}
				objForm.setClasses(classes.toString().split(","));
				objForm.setOldClasses(classes.toString().split(","));
				objForm.setClasMap(classesMap);
				objForm.setSelClassesMap(CommonUtil.sortMapByValue(selClassesMap));
				objForm.setTotalClassesMap(totClassesMap);
				/* new property added by sudhir */
				objForm.setDisplayName(openInternalMark.getDisplayName());
			}
		}
		
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteExam(OpenInternalExamForm objForm) throws Exception{
		return impl.deleteExam(objForm.getId());
	}
	/**
	 * @return
	 * @throws Exception 
	 */
	public Map<Integer, String> getProgramTypeMap() throws Exception {
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<ProgramType> programTypeList = impl.getProgramTypeList();
		if(programTypeList != null && !programTypeList.isEmpty()){
			Iterator<ProgramType> iterator = programTypeList.iterator();
			while (iterator.hasNext()) {
				ProgramType programType = (ProgramType) iterator.next();
				if(programType.getId() != 0 && programType.getName() != null){
					map.put(programType.getId(), programType.getName());
				}
			}
		}
		return map;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateDetails(OpenInternalExamForm objForm) throws Exception{
		List<Integer> classList = new ArrayList<Integer>();
		String[] stayClass = objForm.getStayClass();
		String[] classId=stayClass[0].split(",");
		for (int i = 0; i < classId.length; i++) {
			if(classId[i] != null && !classId[i].trim().isEmpty()){
				classList.add(Integer.parseInt(classId[i]));
			}
		}
		Set<OpenInternalExamForClasses> set = new HashSet<OpenInternalExamForClasses>();
		OpenInternalMark bo = new OpenInternalMark();
		if(objForm.getId() != 0){
			bo.setId(objForm.getId());
		}
		ExamDefinition exam = new ExamDefinition();
		exam.setId(Integer.parseInt(objForm.getExamId()));
		bo.setExam(exam);
		ProgramType type = new ProgramType();
		type.setId(Integer.parseInt(objForm.getNewProgramTypeId()));
		bo.setProgramType(type);
		bo.setStartDate(CommonUtil.ConvertStringToSQLDate(objForm.getStartDate()));
		bo.setEndDate(CommonUtil.ConvertStringToSQLDate(objForm.getEndDate()));
		bo.setCreatedBy(objForm.getUserId());
		bo.setModifiedBy(objForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setLastModifiedDate(new Date());
		bo.setIsActive(true);
		bo.setTheoryPractical(objForm.getTheoryPractical());
		Map<Integer, Integer> totClasMap = objForm.getTotalClassesMap();
		if(classId!=null && !classId.toString().isEmpty()){
			for (int i = 0; i <classId.length; i++) {
				//if the record are there in the db 
				if(totClasMap!=null && totClasMap.containsKey(Integer.parseInt(classId[i]))){
						OpenInternalExamForClasses classes = new OpenInternalExamForClasses();
						classes.setId(totClasMap.get(Integer.parseInt(classId[i])));
						OpenInternalMark openExam = new OpenInternalMark();
						openExam.setId(objForm.getId());
						classes.setOpenExam(openExam);
						Classes cls = new Classes();
						cls.setId(Integer.parseInt(classId[i]));
						classes.setClasses(cls);
						classes.setCreatedBy(objForm.getUserId());
						classes.setModifiedBy(objForm.getUserId());
						classes.setCreatedDate(new Date());
						classes.setLastModifiedDate(new Date());
						classes.setIsActive(true);
						set.add(classes);
						totClasMap.remove(Integer.parseInt(classId[i]));
					
				}else{
					//if the record is new
					OpenInternalExamForClasses classes = new OpenInternalExamForClasses();
					OpenInternalMark openExam = new OpenInternalMark();
					openExam.setId(objForm.getId());
					classes.setOpenExam(openExam);
					Classes cls = new Classes();
					cls.setId(Integer.parseInt(classId[i]));
					classes.setClasses(cls);
					classes.setCreatedBy(objForm.getUserId());
					classes.setModifiedBy(objForm.getUserId());
					classes.setCreatedDate(new Date());
					classes.setLastModifiedDate(new Date());
					classes.setIsActive(true);
					set.add(classes);
				}
			}
		}
		//make isactivate is 0 which are in db
		if(totClasMap!=null && !totClasMap.isEmpty()){
			Iterator<Map.Entry<Integer, Integer>> iterator=totClasMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<java.lang.Integer, java.lang.Integer> entry = (Map.Entry<java.lang.Integer, java.lang.Integer>) iterator.next();
				OpenInternalExamForClasses classes = new OpenInternalExamForClasses();
				classes.setId(entry.getValue());
				OpenInternalMark openExam = new OpenInternalMark();
				openExam.setId(objForm.getId());
				classes.setOpenExam(openExam);
				Classes cls = new Classes();
				cls.setId(entry.getKey());
				classes.setClasses(cls);
				classes.setCreatedBy(objForm.getUserId());
				classes.setModifiedBy(objForm.getUserId());
				classes.setCreatedDate(new Date());
				classes.setLastModifiedDate(new Date());
				classes.setIsActive(false);
				set.add(classes);
			}
		}
		/*if(objForm.getOldClasses() != null && objForm.getOldClasses().length>classId.length){
			for (int i = 0; i <objForm.getOldClasses().length; i++) {
				if(objForm.getOldClasses()[i] != null && !objForm.getOldClasses()[i].trim().isEmpty()){
					if(classList.contains(Integer.parseInt(objForm.getOldClasses()[i]))){
						OpenInternalExamForClasses classes = new OpenInternalExamForClasses();
						if(clasMap != null && clasMap.get(Integer.parseInt(objForm.getOldClasses()[i])) != 0){
							classes.setId(clasMap.get(Integer.parseInt(objForm.getOldClasses()[i])));
						}
						OpenInternalMark openExam = new OpenInternalMark();
						openExam.setId(objForm.getId());
						classes.setOpenExam(openExam);
						Classes cls = new Classes();
						cls.setId(Integer.parseInt(objForm.getOldClasses()[i]));
						classes.setClasses(cls);
						classes.setCreatedBy(objForm.getUserId());
						classes.setModifiedBy(objForm.getUserId());
						classes.setCreatedDate(new Date());
						classes.setLastModifiedDate(new Date());
						classes.setIsActive(true);
						set.add(classes);
					}else{
						OpenInternalExamForClasses classes = new OpenInternalExamForClasses();
						if(clasMap != null && clasMap.get(Integer.parseInt(objForm.getOldClasses()[i]))!=null && clasMap.get(Integer.parseInt(objForm.getOldClasses()[i])) != 0){
							classes.setId(clasMap.get(Integer.parseInt(objForm.getOldClasses()[i])));
						}
						OpenInternalMark openExam = new OpenInternalMark();
						openExam.setId(objForm.getId());
						classes.setOpenExam(openExam);
						Classes cls = new Classes();
						cls.setId(Integer.parseInt(objForm.getOldClasses()[i]));
						classes.setClasses(cls);
						classes.setCreatedBy(objForm.getUserId());
						classes.setModifiedBy(objForm.getUserId());
						classes.setCreatedDate(new Date());
						classes.setLastModifiedDate(new Date());
						classes.setIsActive(false);
						set.add(classes);
					}
				}
			}
		}else{
			for (int i = 0; i < classId.length; i++) {
				if(classId[i] != null && !classId[i].trim().isEmpty()){
					if(classList.contains(Integer.parseInt(classId[i]))){
						OpenInternalExamForClasses classes = new OpenInternalExamForClasses();
						if(clasMap != null && clasMap.get(Integer.parseInt(classId[i])) != null && clasMap.get(Integer.parseInt(classId[i])) != 0){
							classes.setId(clasMap.get(Integer.parseInt(classId[i])));
						}
						OpenInternalMark openExam = new OpenInternalMark();
						openExam.setId(objForm.getId());
						classes.setOpenExam(openExam);
						Classes cls = new Classes();
						cls.setId(Integer.parseInt(classId[i]));
						classes.setClasses(cls);
						classes.setCreatedBy(objForm.getUserId());
						classes.setModifiedBy(objForm.getUserId());
						classes.setCreatedDate(new Date());
						classes.setLastModifiedDate(new Date());
						classes.setIsActive(true);
						set.add(classes);
					}else{
						OpenInternalExamForClasses classes = new OpenInternalExamForClasses();
						if(clasMap != null && clasMap.get(Integer.parseInt(classId[i])) != null && clasMap.get(Integer.parseInt(classId[i])) != 0){
							classes.setId(clasMap.get(Integer.parseInt(classId[i])));
						}
						OpenInternalMark openExam = new OpenInternalMark();
						openExam.setId(objForm.getId());
						classes.setOpenExam(openExam);
						Classes cls = new Classes();
						cls.setId(Integer.parseInt(classId[i]));
						classes.setClasses(cls);
						classes.setCreatedBy(objForm.getUserId());
						classes.setModifiedBy(objForm.getUserId());
						classes.setCreatedDate(new Date());
						classes.setLastModifiedDate(new Date());
						classes.setIsActive(false);
						set.add(classes);
					}
				}
			}
		}*/
		/* new property added by sudhir */
		bo.setDisplayName(objForm.getDisplayName());
		//		
		bo.setClassesSet(set);
		return impl.saveExam(bo,"update");
	}
	public Map<Integer, String> getInternalExamClassesMap(int examNameId, int programTypeId) throws Exception{
		List<KeyValueTO> listValues = new ArrayList();
		listValues=impl.getClassValuesByCourseIdNew(examNameId,programTypeId,listValues);
		return helper.convertTOClassMap(listValues);
	}
}
