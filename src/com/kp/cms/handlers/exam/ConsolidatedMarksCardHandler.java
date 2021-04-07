package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.ConsolidatedMarksCardProgrammePart;
import com.kp.cms.bo.exam.ExamGradeDefinitionBO;
import com.kp.cms.bo.exam.UGConsolidateMarksCardBO;
import com.kp.cms.forms.exam.ConsolidatedMarksCardForm;
import com.kp.cms.helpers.exam.ConsolidatedMarksCardHelper;
import com.kp.cms.to.exam.NewConsolidatedMarksCardTo;
import com.kp.cms.transactions.exam.IConsolidatedMarksCardTransaction;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.ConsolidatedMarksCardTransactionImpl;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;

public class ConsolidatedMarksCardHandler {
	/**
	 * Singleton object of ConsolidatedMarksCardHandler
	 */
	private static volatile ConsolidatedMarksCardHandler consolidatedMarksCardHandler = null;
	private static final Log log = LogFactory.getLog(ConsolidatedMarksCardHandler.class);
	private ConsolidatedMarksCardHandler() {
		
	}
	/**
	 * return singleton object of ConsolidatedMarksCardHandler.
	 * @return
	 */
	public static ConsolidatedMarksCardHandler getInstance() {
		if (consolidatedMarksCardHandler == null) {
			consolidatedMarksCardHandler = new ConsolidatedMarksCardHandler();
		}
		return consolidatedMarksCardHandler;
	}
	/**
	 * @param consolidatedMarksCardForm
	 * @return
	 * @throws Exception
	 */
	public boolean generateConsolidateMarksCard( ConsolidatedMarksCardForm consolidatedMarksCardForm) throws Exception {
		log.info("entered into generateConsolidateMarksCard");
		List<String> courseIds;
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		if(consolidatedMarksCardForm.getCoursesId()==null || consolidatedMarksCardForm.getCoursesId().length==0){
			String courseQuery="select concat(c.id,'') from Course c where c.isActive=1 and c.program.id="+consolidatedMarksCardForm.getProgramId();
			courseIds=txn.getDataForQuery(courseQuery);
		}else{
			courseIds=Arrays.asList(consolidatedMarksCardForm.getCoursesId());
		}
		
		String qry = "select p.programType.id from Program p where p.id="+consolidatedMarksCardForm.getProgramId();
		List<Integer> programTypeIds = txn.getDataForQuery(qry);
		consolidatedMarksCardForm.setProgramTypeId(String.valueOf(programTypeIds.get(0)));
		boolean isFlag=false;
		if(courseIds!=null && !courseIds.isEmpty()){
			for (String courseId : courseIds) {
				String query=ConsolidatedMarksCardHelper.getInstance().getStudentsQuery(courseId,consolidatedMarksCardForm.getYear());
				List<Integer> studentIds=txn.getDataForQuery(query);
				//List<Integer> studentIds = new ArrayList<Integer>();
				//studentIds.add(1510);
				if(studentIds!=null && !studentIds.isEmpty()){
					String consolidateQuery=ConsolidatedMarksCardHelper.getInstance().getConsolidateQuery(consolidatedMarksCardForm);
					IConsolidatedMarksCardTransaction transaction1= ConsolidatedMarksCardTransactionImpl.getInstance();
					List<Object[]> list=transaction1.getStudentMarks(consolidateQuery,studentIds);
					String certificateCourseQuery="select subject_id,is_optional,scheme_no,student_id from student_certificate_course  where is_cancelled=0 and  student_id in (:ids)";
					List certificateList=transaction1.getStudentMarks(certificateCourseQuery,studentIds);
					Map<Integer,Map<Integer,Map<Integer,Boolean>>> certificateMap=getCertificateSubjectMap(certificateList);
					List<String> appearedList=transaction1.getSupplimentaryAppeared(studentIds);
					Map<Integer,Map<String, ConsolidateMarksCard>> boMap=ConsolidatedMarksCardHelper.getInstance().getBoListForInput(list,consolidatedMarksCardForm,certificateMap,appearedList,courseId);
					if(!boMap.isEmpty())
						isFlag=transaction1.saveConsolidateMarks(boMap,Integer.parseInt(courseId),Integer.parseInt(consolidatedMarksCardForm.getYear()));
					else
						isFlag=false;
					if(isFlag)
					{
						List<ExamGradeDefinitionBO> gradeList = transaction1.getGradeDefenitionsForACourse(Integer.parseInt(courseId));
						Map<String, ConsolidatedMarksCardProgrammePart> programPartMap = ConsolidatedMarksCardHelper.getInstance().getMapForProgramPartSectionInMarksCard(boMap, consolidatedMarksCardForm, gradeList);
						if(!programPartMap.isEmpty())
						{
							isFlag = transaction1.saveConsolidateMarksProgrammePart(programPartMap, Integer.parseInt(courseId), Integer.parseInt(consolidatedMarksCardForm.getYear()));
						}
						else
						{
							isFlag = false;
						}
					}
				}
			}
		}
		log.info("exit from generateConsolidateMarksCard");
		return isFlag;
	}
	/**
	 * @param certificateList
	 * @return
	 */
	private Map<Integer, Map<Integer, Map<Integer, Boolean>>> getCertificateSubjectMap( List certificateList)  throws Exception{
		Map<Integer,Map<Integer,Map<Integer,Boolean>>> map=new HashMap<Integer,Map<Integer, Map<Integer,Boolean>>>();
		Map<Integer,Map<Integer,Boolean>> outerMap;
		Map<Integer,Boolean> innerMap;
		if(certificateList!=null && !certificateList.isEmpty()){
			Iterator<Object[]> itr=certificateList.iterator();
			while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				if(objects[0]!=null && objects[1]!=null && objects[2]!=null  && objects[3]!=null){
					if(map.containsKey(Integer.parseInt(objects[3].toString())))
						outerMap=map.get(Integer.parseInt(objects[3].toString()));
					else
						outerMap=new HashMap<Integer, Map<Integer,Boolean>>();
						
							
					if(outerMap.containsKey(Integer.parseInt(objects[2].toString()))){
						innerMap=outerMap.remove(Integer.parseInt(objects[2].toString()));
					}else
						innerMap=new HashMap<Integer, Boolean>();
					
					innerMap.put(Integer.parseInt(objects[0].toString()),(Boolean)objects[1]);
					outerMap.put(Integer.parseInt(objects[2].toString()),innerMap);
					map.put(Integer.parseInt(objects[3].toString()), outerMap);
				}
			}
		}
		return map;
	}
}
