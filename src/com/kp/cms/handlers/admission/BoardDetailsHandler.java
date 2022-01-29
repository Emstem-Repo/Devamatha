package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.BoardDetailsForm;
import com.kp.cms.helpers.admission.BoardDetailsHelper;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.transactions.admission.IBoardDetailsTransaction;
import com.kp.cms.transactionsimpl.admission.BoardDetailsTransactionImpl;

public class BoardDetailsHandler {
	IBoardDetailsTransaction transaction=new BoardDetailsTransactionImpl();
	/**
	 * Singleton object of BoardDetailsHandler
	 */
	private static volatile BoardDetailsHandler boardDetailsHandler = null;
	private static final Log log = LogFactory.getLog(BoardDetailsHandler.class);
	private BoardDetailsHandler() {
		
	}
	/**
	 * return singleton object of boardDetailsHandler.
	 * @return
	 */
	public static BoardDetailsHandler getInstance() {
		if (boardDetailsHandler == null) {
			boardDetailsHandler = new BoardDetailsHandler();
		}
		return boardDetailsHandler;
	}
	/**
	 * @param boardDetailsForm
	 * @return
	 * @throws Exception
	 */
	public List<BoardDetailsTO> getListOfCandidates(BoardDetailsForm boardDetailsForm)  throws Exception{
		log.info("Entered into getListOfCandidates- BoardDetailsHandler");
		String classSchemwiseids=transaction.getClassSchemwiseIds(BoardDetailsHelper.getInstance().getSearchQueryForClassScheme(boardDetailsForm));
		String query=BoardDetailsHelper.getInstance().getSearchQuery(boardDetailsForm,classSchemwiseids);
		List<Student> studentList=transaction.getStudentDetails(query);
		List detainedList=transaction.getDetainedOrDiscontinuedStudents();
		log.info("Exit from getListOfCandidates- BoardDetailsHandler");
		if(boardDetailsForm.getCourseName().equalsIgnoreCase("-Select-") || boardDetailsForm.getCourseName().equalsIgnoreCase("- Select -")){
			boardDetailsForm.setCourseName("");
		}
		if(boardDetailsForm.getClassName().equalsIgnoreCase("-Select-") || boardDetailsForm.getClassName().equalsIgnoreCase("- Select -")){
			boardDetailsForm.setClassName("");
		}
		if(boardDetailsForm.getProgramName().equalsIgnoreCase("-Select-") || boardDetailsForm.getProgramName().equalsIgnoreCase("- Select -")){
			boardDetailsForm.setProgramName("");
		}
		return BoardDetailsHelper.getInstance().convertBotoToList(studentList,detainedList);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> getExistsExamRegNo() throws Exception{
		String query=BoardDetailsHelper.getInstance().getExistsExamRegNoQuery();
		return transaction.getList(query);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> getExistStudentNo() throws Exception {
		String query=BoardDetailsHelper.getInstance().getExistsStuNoQuery();
		return transaction.getList(query);
	}
	/**
	 * @param boardDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateDetails(BoardDetailsForm boardDetailsForm) throws Exception {
		return transaction.updateDetails(boardDetailsForm);
	}
	
	public Map<Integer,Integer> getyearMap()throws Exception{
		Map<Integer,Integer> yearMap=transaction.getYearMap();
		return yearMap;
	}
	
	public Map<Integer,String> getProgramMap()throws Exception{
		Map<Integer,String> programMap=transaction.getProgramMap();
		return programMap;
	}
}
