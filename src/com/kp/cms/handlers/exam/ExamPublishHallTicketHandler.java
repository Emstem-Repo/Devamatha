package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ibm.icu.util.StringTokenizer;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.ExamPublishExamResultsBO;
import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamPublishHallTicketForm;
import com.kp.cms.helpers.exam.ExamPublishHallTicketHelper;
import com.kp.cms.to.exam.ExamClassTO;
import com.kp.cms.to.exam.ExamPublishHallTicketTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamPublishHallTicketImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamPublishHallTicketHandler extends ExamGenHandler {

	ExamPublishHallTicketHelper helper = new ExamPublishHallTicketHelper();
	ExamPublishHallTicketImpl impl = new ExamPublishHallTicketImpl();

	// To get Program Type List
	public List<KeyValueTO> getProgramTypeList() throws Exception {
		ExamDefinitionHandler edh = new ExamDefinitionHandler();
		return edh.getProgramTypeList();
	}

	// To get Exam Type List

	public ArrayList<KeyValueTO> getExamTypeList(ExamPublishHallTicketForm objform) throws Exception {
		ArrayList<ExamTypeUtilBO> listBO = new ArrayList(impl
				.select_All(ExamTypeUtilBO.class));
		return helper.convertBOToTO_ExamType(listBO,objform);

	}

	// To get PublishFor drop down
	public List<KeyValueTO> getPublishForList() throws Exception {
		List<KeyValueTO> listTo = new ArrayList<KeyValueTO>();
		listTo.add(new KeyValueTO(1, "Hall Ticket"));
		listTo.add(new KeyValueTO(2, "Marks Card"));
		return listTo;
	}

	// To get the Main Grid
	public ArrayList<ExamPublishHallTicketTO> getList(ExamPublishHallTicketForm objform) throws Exception {

		List<Object> lBO = impl.getListByExamType(objform);
		ArrayList<ExamPublishHallTicketTO> listTo = helper
				.convertBOToTO_mainList(lBO);
		return listTo;

	}

	public void addPublish(ExamPublishHallTicketForm objform) throws Exception {
		String[] str = objform.getClassValues().split(",");
		if (str.length != 0) {
			List<Integer> listCourses = new ArrayList<Integer>();
			for (int x = 0; x < str.length; x++) {
				listCourses.add(Integer.parseInt(str[x]));
			}
		}

	}

	// On - PUBLISH
	public void addList(int examId, int programTypeId, String[] classId,
			Integer agreementId, Integer footerId, String publishFor,
			String downloadStartDate, String downloadEndDate, String userId, String examCenterCode, String examCenter,String revaluationDate)
			throws Exception {
		 List<ExamPublishHallTicketMarksCardBO> list=new ArrayList<ExamPublishHallTicketMarksCardBO>();
		 Map<Integer, String> classMap = impl.getClassMap(classId);
		Integer val = 0;
		boolean flag=false;
		if (classId != null && classId.length > 0){
			StringBuffer classNames=new StringBuffer();
			StringBuffer publishedClasses=new StringBuffer();
			for (String i : classId) {
				StringTokenizer tokens = new StringTokenizer(i, ",");
				while (tokens.hasMoreElements()) {
					val = Integer.parseInt(tokens.nextElement().toString());
					// var = 0;
					// if (i != null && i.length() > 0) {
					// var = Integer.parseInt(i);
					//
				

				ExamPublishHallTicketMarksCardBO objBO = new ExamPublishHallTicketMarksCardBO(
						examId, val, programTypeId, agreementId, footerId,
						publishFor, CommonUtil
								.ConvertStringToDate(downloadStartDate),
						CommonUtil.ConvertStringToDate(downloadEndDate), userId, examCenterCode, examCenter,(revaluationDate!=null && !revaluationDate.isEmpty() && CommonUtil.isValidDate(revaluationDate))?CommonUtil.ConvertStringToSQLDate(revaluationDate):null);
				Date toDate = CommonUtil.ConvertStringToDate(downloadStartDate);
				Date endDate = CommonUtil.ConvertStringToDate(downloadEndDate);
				
				String className=impl.isDuplicate(0, examId, val, agreementId, footerId, toDate,
						endDate, publishFor);
				if(!className.isEmpty()){
					if(classNames.length()>0){
					classNames.append(","+className);
					} else classNames.append(className);
				}
				//added by mahi
					if(publishFor.equalsIgnoreCase("Marks Card")){
						ExamPublishExamResultsBO examResultsBO= impl.checkClassByPublish(examId,val);
						if(examResultsBO!=null){
							//count++;
							list.add(objBO);
						}else{
							flag=true;
						}
						if(publishedClasses.length()>0){
							publishedClasses.append(","+classMap.get(val));
						}else{
							publishedClasses.append(classMap.get(val));
						}
					}
					if(publishFor.equalsIgnoreCase("Hall Ticket")){
						if(className==null || className.isEmpty())
						{
					 impl.insert(objBO);
						}
					}					
					
					//raghu
					if(publishFor.equalsIgnoreCase("Regular Application")){
						if(className==null || className.isEmpty())
						{
					 impl.insert(objBO);
						}
					}
					//basim
					if(publishFor.equalsIgnoreCase("Internal Marks Entry")){
						if(className==null || className.isEmpty()){
							impl.insert(objBO);
						}
					}
					// for student login consolidate marks card
					if(publishFor.equalsIgnoreCase("Consolidate Marks Card")){
						if(className==null || className.isEmpty()){
							impl.insert(objBO);
						}
					}
					// for revaluation application 
					if(publishFor.equalsIgnoreCase("revaluation/scrutiny")){
						if(className==null || className.isEmpty()){
							impl.insert(objBO);
						}
					}
					
					// for internal marks card 
					if(publishFor.equalsIgnoreCase("Internal Marks Card")){
						if(className==null || className.isEmpty()){
							impl.insert(objBO);
						}
					}

				}
			}
			if(classNames.length()>0){
				throw new DuplicateException(classNames.toString());
			}
			if(flag)
			{
				throw new ApplicationException(publishedClasses.toString());
			}
			else{
				Iterator iterator=list.iterator();
					while(iterator.hasNext())
					{
						ExamPublishHallTicketMarksCardBO objBO1 = (ExamPublishHallTicketMarksCardBO) iterator.next();
						impl.insert(objBO1);
					}
			}
			//ended
		}
	}

	// On - EDIT
	public void update(int id, int examId, String[] classId,
			Integer agreementId, Integer footerId, String publishFor,
			String downloadStartDate, String downloadEndDate, int programTypeId, String examCenterCode, String examCenter,String revaluationDate)
			throws Exception {
		Integer val = 0;
		if (classId != null && classId.length > 0)
			for (String i : classId) {
				StringTokenizer tokens = new StringTokenizer(i, ",");
				while (tokens.hasMoreElements()) {
					val = Integer.parseInt(tokens.nextElement().toString());
					// var = 0;
					// if (i != null && i.length() > 0) {
					// var = Integer.parseInt(i);
					//
				}
				Date toDate = CommonUtil.ConvertStringToDate(downloadStartDate);
				Date endDate = CommonUtil.ConvertStringToDate(downloadEndDate);

				String className=impl.isDuplicate(id, examId, val, agreementId, footerId,
						toDate, endDate, publishFor);
				if(!className.isEmpty())
					throw new DuplicateException(className);
				impl.update(id, examId, val, agreementId, footerId, publishFor,
						CommonUtil.ConvertStringToDate(downloadStartDate),
						CommonUtil.ConvertStringToDate(downloadEndDate),
						programTypeId, examCenterCode, examCenter,(revaluationDate!=null && !revaluationDate.isEmpty() && CommonUtil.isValidDate(revaluationDate))?CommonUtil.ConvertStringToSQLDate(revaluationDate):null);

			}
	}

	// On - DELETE
	public void delete(int id) throws Exception {
		impl.delete(id);
	}

	// On - EDIT to set the values to form
	public ExamPublishHallTicketForm getUpdatableForm(
			ExamPublishHallTicketForm form) throws Exception {

		return helper.createFormObjcet(form, impl.select_Unique(form.getId()),
				getProgramTypeList());
	}

	// On - EDIT (To retain all values)
	public ExamPublishHallTicketForm getUpdatableFormAfterErrorMessages(
			ExamPublishHallTicketForm form) throws Exception {

		return helper.createFormObjcet(form, getProgramTypeList());
	}

	// To get class name for a particular classId
	public String getClassNameById(int classId) throws Exception {
		ClassSchemewise c = ((ClassSchemewise) impl.getClassNameYearById(classId,
				ClassUtilBO.class));
		if (c != null)
			return c.getClasses().getName()+"("+c.getCurriculumSchemeDuration().getAcademicYear()+")";
		return "";
	}

	public String getCurrentExamName(int examTypeID) throws Exception {
		String currentExamName = impl.getCurrentExamName(examTypeID);
		return currentExamName;
	}

	/**
	 * getting exam type and exam name for the id to set retain all values in the form
	 * @param id
	 * @throws Exception
	 */
	public void getExamTypeAndExamName(String id,ExamPublishHallTicketForm form)throws Exception{
		ExamPublishHallTicketMarksCardBO bo=impl.getExamTypeAndExamName(Integer.parseInt(id));
		if(bo!=null){
		if(bo.getExamDefinitionBO()!=null && bo.getExamDefinitionBO().getExamTypeUtilBO()!=null){
			form.setExamType(String.valueOf(bo.getExamDefinitionBO().getExamTypeUtilBO().getId()));
			form.setExamName(String.valueOf(bo.getExamDefinitionBO().getId()));
		}
	}
	}

	/**
	 * @param parseInt
	 * @param programTypeId
	 * @param deaneryname 
	 * @param examType 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getClassCodeByExamNameProgramType(Integer examId,
			Integer programTypeId, String deaneryname) throws Exception{
		ArrayList<ExamClassTO> list = helper.convertTOTO(impl
				.getCourseIdSchemeNoByProgramType(examId,programTypeId,deaneryname));
		List<KeyValueTO> listValues = new ArrayList();
		for (ExamClassTO examClassTO : list) {
			impl.getClassValuesByCourseId(examClassTO.getCourseId(),
					examClassTO.getCourseSchemeId(), examClassTO.getSchemeNo(),
					listValues, examId);

		}
		return helper.convertTOClassMap(listValues);

	}

	public void getDeaneryMap(ExamPublishHallTicketForm objform) throws Exception{
		Map<String,String> map=impl.getDeaneryMap();
		objform.setDeaneryMap(map);
	}

	public void mapSelectedClassByStyleClass(
			ExamPublishHallTicketForm objform) throws Exception{
		Map<Integer,String> MapSelectedClass=new HashMap<Integer, String>();
		String[] styleClass=objform.getStayClass();
		Map<Integer,String> map=objform.getMapClass();
		for (String string : styleClass) {
			StringTokenizer st=new StringTokenizer(string, ",");
			while(st.hasMoreElements()){
				try{
					int key=Integer.parseInt(st.nextElement().toString());
					if(map.containsKey(key)){
						MapSelectedClass.put(key, map.get(key));
						map.remove(key);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		objform.setMapSelectedClass(CommonUtil.sortMapByValue(MapSelectedClass));
		objform.setMapClass(map);
	}
	
	
	
	public void sendingSMStoStudents(String[] classId,ExamPublishHallTicketForm objform,String examType)throws Exception {
		Set<Integer> classIds=new HashSet<Integer>();
		Set<ExamPublishHallTicketTO> studentMobileNos=new HashSet<ExamPublishHallTicketTO>();
		Integer val = 0;
		if (classId != null && classId.length > 0){
			for (String i : classId) {
				StringTokenizer tokens = new StringTokenizer(i, ",");
				while (tokens.hasMoreElements()) {
					val = Integer.parseInt(tokens.nextElement().toString());
					classIds.add(val);
				}
			}
		}
		
		if(classIds !=null && !classIds.isEmpty()){
			List<Object[]> getCurrentStudentList=impl.getStudentListByClassIds(classIds);
			if(getCurrentStudentList !=null && !getCurrentStudentList.isEmpty()){
				Iterator<Object[]> itr = getCurrentStudentList.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					ExamPublishHallTicketTO to=new ExamPublishHallTicketTO();
					if (obj[1].toString() != null && !obj[1].toString().isEmpty()) {
						to.setMobileNo1(obj[0].toString());
						to.setMobileNo2(obj[1].toString());
						studentMobileNos.add(to);
					}
				}
			}
			List<Object[]> getPreviousStudentList=impl.getPreviousStudentList(classIds);
			if(getPreviousStudentList !=null && !getPreviousStudentList.isEmpty()){
				Iterator<Object[]> itr = getPreviousStudentList.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					ExamPublishHallTicketTO to=new ExamPublishHallTicketTO();
					if (obj[1].toString() != null && !obj[1].toString().isEmpty()) {
						to.setMobileNo1(obj[0].toString());
						to.setMobileNo2(obj[1].toString());
						studentMobileNos.add(to);
					}
				}
			}
			helper.sendSMSToStudent(studentMobileNos,objform,examType);
			
		}
	}
	
	public String getExamTypeBYExamTypeId(String examTypeId)throws Exception{
		String examType=impl.getExamType(examTypeId);
		return examType;
	}
	
	public void sendingSMSForSupplimentoryStudents(String[] classId,ExamPublishHallTicketForm objform,String examType)throws Exception {
		Set<Integer> classIds=new HashSet<Integer>();
		Set<ExamPublishHallTicketTO> studentMobileNos=new HashSet<ExamPublishHallTicketTO>();
		Integer val = 0;
		if (classId != null && classId.length > 0){
			for (String i : classId) {
				StringTokenizer tokens = new StringTokenizer(i, ",");
				while (tokens.hasMoreElements()) {
					val = Integer.parseInt(tokens.nextElement().toString());
					classIds.add(val);
				}
			}
		}
		
		if(classIds !=null && !classIds.isEmpty()){
			List<Object[]> getCurrentStudentList=impl.getSupplimentoryStudentListByClassIds(classIds,objform);
			if(getCurrentStudentList !=null && !getCurrentStudentList.isEmpty()){
				Iterator<Object[]> itr = getCurrentStudentList.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					ExamPublishHallTicketTO to=new ExamPublishHallTicketTO();
					if (obj[1].toString() != null && !obj[1].toString().isEmpty()) {
						to.setMobileNo1(obj[0].toString());
						to.setMobileNo2(obj[1].toString());
						studentMobileNos.add(to);
					}
				}
			}
			
			helper.sendSMSToStudent(studentMobileNos,objform,examType);
			
		}
	}

}
