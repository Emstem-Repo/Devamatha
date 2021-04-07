package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamPublishExamResultsBO;
import com.kp.cms.forms.exam.ExamExamResultsForm;
import com.kp.cms.handlers.exam.ExamInternalRetestApplicationHandler;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.handlers.exam.ExamResultsHandler;
import com.kp.cms.to.exam.ExamExamResultsTO;
import com.kp.cms.utilities.CommonUtil;
@SuppressWarnings("unchecked")
public class ExamResultsHelper {

	public ExamExamResultsForm setTOForm(ExamExamResultsForm objform,
			ExamPublishExamResultsBO objBO) throws Exception {
		objform.setId(objBO.getId());
		objform.setExamName(Integer.toString(objBO.getExamId()));
		objform.setClassName(Integer.toString(objBO.getClassId()));
		objform.setCourseIdsTo(Integer.toString(objBO.getClassId()));
		objform.setPublishDate(CommonUtil.formatSqlDate(objBO.getPublishDate()
				.toString()));
		objform.setYear(String.valueOf(objBO.getExamDefinitionBO().getAcademicYear()));
		objform.setExname(Integer.toString(objBO.getExamDefinitionBO().getId()));
		objform.setExtype(Integer.toString(objBO.getExamDefinitionBO().getExamTypeUtilBO().getId()));
		String a = "off";
		if (objBO.getIsPublishOverallInternalCompOnly() == 1) {
			a = "on";
		}
		objform.setPublishOverallInternalComponentsOnly(a);

		ExamResultsHandler h1 = new ExamResultsHandler();
		/* String programId=null;
		  Map<Integer, String> mapClas = ExamPublishHallTicketHandler.getInstance().getclassesMap(String.valueOf(objBO.getExamId()),
				objform.getExtype(),programId,objform.getDeanaryName());
		mapClas.remove(objBO.getClassId());
		objform.setMapClass(mapClas);*/
		
		Map<Integer, String> mapSelectedClass = new HashMap<Integer, String>();
		ExamInternalRetestApplicationHandler h = new ExamInternalRetestApplicationHandler();
		mapSelectedClass.put(objBO.getClassId(), h.getClassNameById(objBO
				.getClassId()));
		objform.setMapSelectedClass(mapSelectedClass);
		
		objform.setInternalComponents(h1.getInternalComponentsByClasses(objBO
				.getExamId()));
		
		return objform;

	}

	
	public List<ExamExamResultsTO> convertBOTOTO(List listRows) throws Exception {
		ArrayList<ExamExamResultsTO> listOfTO = new ArrayList<ExamExamResultsTO>();
		if (listRows != null && listRows.size() > 0) {
			Iterator itr = listRows.iterator();
			ExamExamResultsTO to = null;
			while (itr.hasNext()) {
				to = new ExamExamResultsTO();
				Object[] row = (Object[]) itr.next();
				if(row[0]!=null){
				to.setId((Integer) row[0]);
				}if(row[1]!=null && row[5]!=null){
				to.setClassName((String) row[1]+"("+row[5]+")");
				}if(row[2]!=null){
				to.setExamName((String) row[2]);
				}if(row[3]!=null){
				to.setPublishDate(CommonUtil.formatSqlDate(row[3].toString()));
				}
				String publish = "no";
				if(row[4]!=null){
				     if ((Integer) row[4] == 1) {
					    publish = "yes";
				      }     
				     }
				to.setPublishOverallInternalComponentsOnly(publish);
				listOfTO.add(to);
			}
		}
		Collections.sort(listOfTO);

		return listOfTO;
	}

}
