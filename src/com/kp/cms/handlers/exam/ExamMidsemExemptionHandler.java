package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMidsemExemption;
import com.kp.cms.forms.exam.ExamMidsemExemptionForm;
import com.kp.cms.helpers.exam.ExamMidsemExemptionHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.IExamMidsemExemptionTransaction;
import com.kp.cms.transactionsimpl.exam.ExamMidsemExemptionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
public class ExamMidsemExemptionHandler {
	
	private static volatile ExamMidsemExemptionHandler examMidsemExemptionHandler = null;
	private static final Log log = LogFactory.getLog(ExamMidsemExemptionHandler.class);
	
	private ExamMidsemExemptionHandler() {
		
	}
	/**
	 * return singleton object of examMidsemExemptionHandler.
	 * @return
	 */
	public static ExamMidsemExemptionHandler getInstance() {
		if (examMidsemExemptionHandler == null) {
			examMidsemExemptionHandler = new ExamMidsemExemptionHandler();
		}
		return examMidsemExemptionHandler;
	}
	
	/**
	 * @param academicYear
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getExamNameList(int academicYear) throws Exception {
		
		IExamMidsemExemptionTransaction transaction = ExamMidsemExemptionTransactionImpl.getInstance();
		ArrayList<KeyValueTO> examByyear = transaction.getExamByYear(academicYear);
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (KeyValueTO keyValueTO : examByyear) {
			map.put(keyValueTO.getId(), keyValueTO.getDisplay());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	
	
	
	/**
	 * @param regNo
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getClassListAccReg(int regNo) throws Exception {
		
		IExamMidsemExemptionTransaction transaction = ExamMidsemExemptionTransactionImpl.getInstance();
		ArrayList<KeyValueTO> classByReg = transaction.getclassByRegNo(regNo);
		
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		//HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (KeyValueTO keyValueTO : classByReg) {
			map.put(keyValueTO.getId(), keyValueTO.getDisplay());
		}
		//map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	
	/**
	 * @param exemptionForm
	 * @return
	 * @throws Exception
	 */
	public ExamDefinitionBO getExamName(ExamMidsemExemptionForm exemptionForm) throws Exception 
	{
		IExamMidsemExemptionTransaction transaction = ExamMidsemExemptionTransactionImpl.getInstance();
		 ExamDefinitionBO examName = transaction.getExamName(exemptionForm);
		return examName;
		
	}
	
	
	/**
	 * @param exemptionForm
	 * @return
	 * @throws Exception
	 */
	public StudentTO getStudentClassandSubjects(ExamMidsemExemptionForm exemptionForm) throws Exception {
		
		IExamMidsemExemptionTransaction transaction = ExamMidsemExemptionTransactionImpl.getInstance();
		Student stuBo= transaction.getStudentBO(exemptionForm);
		ExamMidsemExemption exemption = transaction.getPreviousSelected(exemptionForm, stuBo.getId());
		Map<Integer,SubjectTO> map = new HashMap<Integer, SubjectTO>();
		if(exemption!=null){
		map = ExamMidsemExemptionHelper.getInstance().convertBOTOMap(exemption, exemptionForm);
		}
		StudentTO stuTO= ExamMidsemExemptionHelper.getInstance().convertBOsToTOs(stuBo, exemptionForm, map);
		return stuTO;
	}
	
	/**
	 * @param exemptionForm
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getAnySelected(ExamMidsemExemptionForm exemptionForm) throws Exception{
		List<Integer> list= new ArrayList<Integer>();
		if(exemptionForm.getStudent()!=null && exemptionForm.getStudent().getSubjectList()!=null){
			Iterator<SubjectTO> subTOItr = exemptionForm.getStudent().getSubjectList().iterator();
			while(subTOItr.hasNext()){
				SubjectTO subTO = subTOItr.next();
				if((subTO.getChecked1()!=null && subTO.getChecked1().equalsIgnoreCase("on"))
						|| (subTO.getTempChecked1()!=null && subTO.getTempChecked1().equalsIgnoreCase("on"))){
					list.add(subTO.getId());
				}
			}
			return list;
		}
		
		return list;
	}
	
	/**
	 * @param exemptionForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveSubjects(ExamMidsemExemptionForm exemptionForm) throws Exception {
		
		IExamMidsemExemptionTransaction transaction = ExamMidsemExemptionTransactionImpl.getInstance();
				
		ExamMidsemExemption exBO = ExamMidsemExemptionHelper.getInstance().convertFormToBO(exemptionForm);
		return transaction.save(exBO);

	}
	
public Map<Integer, String> getExamByYearOnly(int academicYear) throws Exception {
		
		IExamMidsemExemptionTransaction transaction = ExamMidsemExemptionTransactionImpl.getInstance();
		ArrayList<KeyValueTO> examByyear = transaction.getExamByYearOnly(academicYear);
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (KeyValueTO keyValueTO : examByyear) {
			map.put(keyValueTO.getId(), keyValueTO.getDisplay());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	
	

}
