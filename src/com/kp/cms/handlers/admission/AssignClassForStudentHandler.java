package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.AssignClassForStudentForm;
import com.kp.cms.helpers.admission.AssignClassForStudentHelper;
import com.kp.cms.to.admission.AssingClassForStudentTO;
import com.kp.cms.transactions.admission.IAssignClassForStudentTransaction;
import com.kp.cms.transactionsimpl.admission.AssignClassForStudentTransactionImpl;

public class AssignClassForStudentHandler {
	/**
	 * Singleton object of AssignClassForStudentHandler
	 */
	private static volatile AssignClassForStudentHandler assignSecondLanguageHandler = null;
	private static final Log log = LogFactory.getLog(AssignClassForStudentHandler.class);
	private AssignClassForStudentHandler() {
		
	}
	/**
	 * return singleton object of assignSecondLanguageHandler.
	 * @return
	 */
	public static AssignClassForStudentHandler getInstance() {
		if (assignSecondLanguageHandler == null) {
			assignSecondLanguageHandler = new AssignClassForStudentHandler();
		}
		return assignSecondLanguageHandler;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getProgramMap() throws Exception{
		
		IAssignClassForStudentTransaction transaction = AssignClassForStudentTransactionImpl.getInstance();
		Map<Integer, String> programMap = transaction.getProgramMap();
		return programMap;
	}
	
	/**
	 * @param assignClassForStudentForm
	 * @return
	 * @throws Exception
	 */
	public List<AssingClassForStudentTO> getStudentList(AssignClassForStudentForm assignClassForStudentForm) throws Exception{
		
		List<AssingClassForStudentTO> tos = new ArrayList<AssingClassForStudentTO>();
		IAssignClassForStudentTransaction transaction = AssignClassForStudentTransactionImpl.getInstance();
		if(assignClassForStudentForm.getAdmissionYear() != null && !assignClassForStudentForm.getAdmissionYear().isEmpty() && assignClassForStudentForm.getCourseId() != null && !assignClassForStudentForm.getCourseId().isEmpty()){
			String query=getQueryForStudentList(assignClassForStudentForm);
			List<Student> stuList = transaction.getStudentList(query);
			tos = AssignClassForStudentHelper.getInstance().convertBOtoTO(stuList);
		}
		return tos;
	}
	/**
	 * @param assignClassForStudentForm
	 * @return
	 */
	private String getQueryForStudentList(
			AssignClassForStudentForm assignClassForStudentForm) {
		String query="";
		query=query + "select fee.student from FeePayment fee left join fee.student.classSchemewise c left join c.classes classes where fee.isFeePaid = 1 " +
					  " and fee.isCancelChallan=0" +
					  //" and fee.student.isHide=0" +
					  " and fee.student.isActive=1" +
					  " and fee.student.admAppln.isCancelled=0" +
					  " and fee.student.admAppln.appliedYear='" +assignClassForStudentForm.getAdmissionYear()+"'"+																   
					  " and fee.student.admAppln.courseBySelectedCourseId = "+assignClassForStudentForm.getCourseId()+" group by fee.student.id";
		if(assignClassForStudentForm.getLanguageNo().equals("1") ){
			query=query + " order by fee.student.admAppln.personalData.secondLanguage";
		}else if(assignClassForStudentForm.getPercentageNo().equals("1")){
			query=query + " order by fee.student.admAppln.totalWeightage desc";
		}else if(assignClassForStudentForm.getGenderNo().equals("1")){
			query=query + " order by fee.student.admAppln.personalData.gender";
		}else if(assignClassForStudentForm.getNameNo().equals("1")){
			query=query + " order by fee.student.admAppln.personalData.firstName";
		}else if(assignClassForStudentForm.getCategoryNo().equals("1")){
			query=query + " order by fee.student.admAppln.personalData.residentCategory.name";
		}else if(assignClassForStudentForm.getSectionNo().equals("1")){
			query=query + " order by fee.student.admAppln.personalData.caste.name";
		}//code added by sudhir
		else if(assignClassForStudentForm.getClassNo().equals("1")){
			query=query + " order by classes.name";
		}//
		else {
			query=query + " order by fee.student.id";
		}
		
		if(assignClassForStudentForm.getLanguageNo().equals("2") ){
			query=query + ",fee.student.admAppln.personalData.secondLanguage";
		}else if(assignClassForStudentForm.getPercentageNo().equals("2")){
			query=query + ",fee.student.admAppln.totalWeightage desc";
		}else if(assignClassForStudentForm.getGenderNo().equals("2")){
			query=query + ",fee.student.admAppln.personalData.gender";
		}else if(assignClassForStudentForm.getNameNo().equals("2")){
			query=query + ",fee.student.admAppln.personalData.firstName";
		}else if(assignClassForStudentForm.getCategoryNo().equals("2")){
			query=query + ",fee.student.admAppln.personalData.residentCategory.name";
		}else if(assignClassForStudentForm.getSectionNo().equals("2")){
			query=query + ",fee.student.admAppln.personalData.caste.name";
		}//code added by sudhir
		else if(assignClassForStudentForm.getClassNo().equals("2")){
			query=query + ",classes.name";
		}//
		
		if(assignClassForStudentForm.getLanguageNo().equals("3") ){
			query=query + ",fee.student.admAppln.personalData.secondLanguage";
		}else if(assignClassForStudentForm.getPercentageNo().equals("3")){
			query=query + ",fee.student.admAppln.totalWeightage desc";
		}else if(assignClassForStudentForm.getGenderNo().equals("3")){
			query=query + ",fee.student.admAppln.personalData.gender";
		}else if(assignClassForStudentForm.getNameNo().equals("3")){
			query=query + ",fee.student.admAppln.personalData.firstName";
		}else if(assignClassForStudentForm.getCategoryNo().equals("3")){
			query=query + ",fee.student.admAppln.personalData.residentCategory.name";
		}else if(assignClassForStudentForm.getSectionNo().equals("3")){
			query=query + ",fee.student.admAppln.personalData.caste.name";
		}//code added by sudhir
		else if(assignClassForStudentForm.getClassNo().equals("3")){
			query=query + ",classes.name";
		}//
		
		if(assignClassForStudentForm.getLanguageNo().equals("4") ){
			query=query + ",fee.student.admAppln.personalData.secondLanguage";
		}else if(assignClassForStudentForm.getPercentageNo().equals("4")){
			query=query + ",fee.student.admAppln.totalWeightage desc";
		}else if(assignClassForStudentForm.getGenderNo().equals("4")){
			query=query + ",fee.student.admAppln.personalData.gender";
		}else if(assignClassForStudentForm.getNameNo().equals("4")){
			query=query + ",fee.student.admAppln.personalData.firstName";
		}else if(assignClassForStudentForm.getCategoryNo().equals("4")){
			query=query + ",fee.student.admAppln.personalData.residentCategory.name";
		}else if(assignClassForStudentForm.getSectionNo().equals("4")){
			query=query + ",fee.student.admAppln.personalData.caste.name";
		}//code added by sudhir
		else if(assignClassForStudentForm.getClassNo().equals("4")){
			query=query + ",classes.name";
		}//
		
		if(assignClassForStudentForm.getLanguageNo().equals("5") ){
			query=query + ",fee.student.admAppln.personalData.secondLanguage";
		}else if(assignClassForStudentForm.getPercentageNo().equals("5")){
			query=query + ",fee.student.admAppln.totalWeightage desc";
		}else if(assignClassForStudentForm.getGenderNo().equals("5")){
			query=query + ",fee.student.admAppln.personalData.gender";
		}else if(assignClassForStudentForm.getNameNo().equals("5")){
			query=query + ",fee.student.admAppln.personalData.firstName";
		}else if(assignClassForStudentForm.getCategoryNo().equals("5")){
			query=query + ",fee.student.admAppln.personalData.residentCategory.name";
		}else if(assignClassForStudentForm.getSectionNo().equals("5")){
			query=query + ",fee.student.admAppln.personalData.caste.name";
		}//code added by sudhir
		else if(assignClassForStudentForm.getClassNo().equals("5")){
			query=query + ",classes.name";
		}//
		
		if(assignClassForStudentForm.getLanguageNo().equals("6") ){
			query=query + ",fee.student.admAppln.personalData.secondLanguage";
		}else if(assignClassForStudentForm.getPercentageNo().equals("6")){
			query=query + ",fee.student.admAppln.totalWeightage desc";
		}else if(assignClassForStudentForm.getGenderNo().equals("6")){
			query=query + ",fee.student.admAppln.personalData.gender";
		}else if(assignClassForStudentForm.getNameNo().equals("6")){
			query=query + ",fee.student.admAppln.personalData.firstName";
		}else if(assignClassForStudentForm.getCategoryNo().equals("6")){
			query=query + ",fee.student.admAppln.personalData.residentCategory.name";
		}else if(assignClassForStudentForm.getSectionNo().equals("6")){
			query=query + ",fee.student.admAppln.personalData.caste.name";
		}//code added by sudhir
		else if(assignClassForStudentForm.getClassNo().equals("6")){
			query=query + ",classes.name";
		}//
		
		//code added by sudhir
		if(assignClassForStudentForm.getLanguageNo().equals("7") ){
			query=query + ",fee.student.admAppln.personalData.secondLanguage";
		}else if(assignClassForStudentForm.getPercentageNo().equals("7")){
			query=query + ",fee.student.admAppln.totalWeightage desc";
		}else if(assignClassForStudentForm.getGenderNo().equals("7")){
			query=query + ",fee.student.admAppln.personalData.gender";
		}else if(assignClassForStudentForm.getNameNo().equals("7")){
			query=query + ",fee.student.admAppln.personalData.firstName";
		}else if(assignClassForStudentForm.getCategoryNo().equals("7")){
			query=query + ",fee.student.admAppln.personalData.residentCategory.name";
		}else if(assignClassForStudentForm.getSectionNo().equals("7")){
			query=query + ",fee.student.admAppln.personalData.caste.name";
		} else if(assignClassForStudentForm.getClassNo().equals("7")){
			query=query + ",classes.name";
		}//
		return query;
	}
	/**
	 * @param programId
	 * @param sortBy
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<AssingClassForStudentTO> getSortedStudentList(String programId,
			String sortBy, List<AssingClassForStudentTO> list) throws Exception {
		List<AssingClassForStudentTO> tos = new ArrayList<AssingClassForStudentTO>();
		if(list != null){
			Iterator<AssingClassForStudentTO> iterator =list.iterator();
			while (iterator.hasNext()) {
				AssingClassForStudentTO assingClassForStudentTO = (AssingClassForStudentTO) iterator.next();
				if(sortBy != null && !sortBy.isEmpty()){
					assingClassForStudentTO.setSortBy(sortBy);
				}
				tos.add(assingClassForStudentTO);
			}
		}
		return tos;
	}
	/**
	 * @param year
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getClassMap(String year, String courseId) throws Exception{
		IAssignClassForStudentTransaction transaction = AssignClassForStudentTransactionImpl.getInstance();
		Map<Integer, String> classMap = transaction.getClassMap(year,courseId);
		return classMap;
	}
	/**
	 * @param assignClassForStudentForm
	 * @return
	 * @throws Exception
	 */
	public boolean assignClass(
			AssignClassForStudentForm assignClassForStudentForm) throws Exception{
		IAssignClassForStudentTransaction transaction = AssignClassForStudentTransactionImpl.getInstance();
		//List<Student> stuList = AssignClassForStudentHelper.getInstance().getStudentListWithClass(assignClassForStudentForm);
		boolean success = transaction.assignClass(assignClassForStudentForm);
		return success;
	}
}
