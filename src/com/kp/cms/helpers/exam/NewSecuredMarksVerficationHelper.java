package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.forms.exam.NewSecuredMarksVerficationForm;
import com.kp.cms.to.exam.StudentMarksTO;

public class NewSecuredMarksVerficationHelper {
	/**
	 * Singleton object of NewSecuredMarksVerficationHelper
	 */
	private static volatile NewSecuredMarksVerficationHelper newSecuredMarksVerficationHelper = null;
	private static final Log log = LogFactory.getLog(NewSecuredMarksVerficationHelper.class);
	private NewSecuredMarksVerficationHelper() {
		
	}
	/**
	 * return singleton object of NewSecuredMarksVerficationHelper.
	 * @return
	 */
	public static NewSecuredMarksVerficationHelper getInstance() {
		if (newSecuredMarksVerficationHelper == null) {
			newSecuredMarksVerficationHelper = new NewSecuredMarksVerficationHelper();
		}
		return newSecuredMarksVerficationHelper;
	}
	/**
	 * @param newSecuredMarksVerficationForm
	 * @return
	 */
	public String getQueryForAlreadyEnteredMarks(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm) throws Exception {
		String query="from MarksEntryDetails m" +
		" where m.subject.id=" +newSecuredMarksVerficationForm.getSubjectId()+
		" and m.marksEntry.exam.id="+newSecuredMarksVerficationForm.getExamId();
	if(newSecuredMarksVerficationForm.getEvaluatorType()!=null && !newSecuredMarksVerficationForm.getEvaluatorType().isEmpty()){
		query=query+" and m.marksEntry.evaluatorType="+newSecuredMarksVerficationForm.getEvaluatorType();
	}
	if(newSecuredMarksVerficationForm.getAnswerScriptType()!=null && !newSecuredMarksVerficationForm.getAnswerScriptType().isEmpty()){
		query=query+" and m.marksEntry.answerScript="+newSecuredMarksVerficationForm.getAnswerScriptType();
	}
	if(newSecuredMarksVerficationForm.getSubjectType()!=null && !newSecuredMarksVerficationForm.getSubjectType().isEmpty()){
		if(newSecuredMarksVerficationForm.getSubjectType().equalsIgnoreCase("t")){
			query=query+" and m.theoryMarks is not null ";
		}else if(newSecuredMarksVerficationForm.getSubjectType().equalsIgnoreCase("p")){
			query=query+" and m.practicalMarks is not null ";
		}else{
			query=query+" and m.theoryMarks is not null and m.practicalMarks is not null ";
		}
	}
	return query;
	}
	/**
	 * @param marksList
	 * @return
	 */
	public List<StudentMarksTO> convertBoDataToMarksMap(List marksList,Map<Integer,String> detainMap) throws Exception{
		List<StudentMarksTO> marksMap=new ArrayList<StudentMarksTO>();
		Iterator<MarksEntryDetails> itr=marksList.iterator();
		while (itr.hasNext()) {
			MarksEntryDetails marksEntryDetails = (MarksEntryDetails) itr.next();
			StudentMarksTO to=new StudentMarksTO();
			to.setId(marksEntryDetails.getId());
			to.setTheoryMarks(marksEntryDetails.getTheoryMarks());
			to.setPracticalMarks(marksEntryDetails.getPracticalMarks());
			if(marksEntryDetails.getMarksEntry().getClasses()!=null)
				to.setClassId(marksEntryDetails.getMarksEntry().getClasses().getId());
			to.setMarksId(marksEntryDetails.getMarksEntry().getId());
			to.setStudentId(marksEntryDetails.getMarksEntry().getStudent().getId());
			to.setName(marksEntryDetails.getMarksEntry().getStudent().getAdmAppln().getPersonalData().getFirstName());
			to.setRegisterNo(marksEntryDetails.getMarksEntry().getStudent().getRegisterNo());
			if(detainMap.containsKey(marksEntryDetails.getMarksEntry().getStudent().getId())){
				to.setRegisterNo(detainMap.get(marksEntryDetails.getMarksEntry().getStudent().getId()));
			}
			if(marksEntryDetails.getIsMistake()!=null && marksEntryDetails.getIsMistake())
				to.setMistake("on");
			else
				to.setMistake("off");
			marksMap.add(to);
		}
		return marksMap;
	}
}
