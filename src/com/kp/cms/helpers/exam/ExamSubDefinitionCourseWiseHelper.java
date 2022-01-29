package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamSubCoursewiseAttendanceMarksBO;
import com.kp.cms.bo.exam.ExamSubCoursewiseGradeDefnBO;
import com.kp.cms.bo.exam.ExamSubDefinitionCourseWiseBO;
import com.kp.cms.bo.exam.ExamSubjectSectionMasterBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.bo.exam.SubjectRuleSettingsAssignment;
import com.kp.cms.bo.exam.SubjectRuleSettingsAttendance;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulAnsScript;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulEvaluator;
import com.kp.cms.bo.exam.SubjectRuleSettingsSubInternal;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamSubjectDefCourseForm;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.to.exam.ExamSubCoursewiseAttendanceMarksTO;
import com.kp.cms.to.exam.ExamSubCoursewiseGradeDefnTO;
import com.kp.cms.to.exam.ExamSubDefinitionCourseWiseDisplayTO;
import com.kp.cms.to.exam.ExamSubDefinitionCourseWiseTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.InitSessionFactory;
import com.kp.cms.utilities.KeyValueTOComparator;

public class ExamSubDefinitionCourseWiseHelper {

	public ArrayList<ExamSubDefinitionCourseWiseTO> convertBOToTO(
			List<ExamSubDefinitionCourseWiseBO> listBO) {
		ArrayList<ExamSubDefinitionCourseWiseTO> listTO = new ArrayList<ExamSubDefinitionCourseWiseTO>();
		ExamSubDefinitionCourseWiseTO eTO;

		Iterator it = listBO.iterator();
		while (it.hasNext()) {
			Object[] row = (Object[]) it.next();
			eTO = new ExamSubDefinitionCourseWiseTO();
			eTO.setSubjectId(Integer.parseInt(row[0].toString()));
			eTO.setSubjectName(row[1].toString());
			eTO.setSubjectCode(row[2].toString());
			if(row[3]!= null){
				eTO.setSubjectOrder(row[3].toString());
			}
			if(row[4]!=null){
				eTO.setTheoryCredit(row[4].toString());
			}
			if(row[5]!=null){
				eTO.setSubjectSection(row[5].toString());
			}
			if(row[6]!=null){
				eTO.setPracticalCredit(row[6].toString());
			}
			listTO.add(eTO);
		}
		Collections.sort(listTO);
		return listTO;
	}

	public ExamSubDefinitionCourseWiseTO createBOToTO(SubjectUtilBO sbo) {

		ExamSubDefinitionCourseWiseTO to = null;
		if (sbo != null) {
			to = new ExamSubDefinitionCourseWiseTO();
			to.setSubjectCode(sbo.getCode());
			to.setSubjectName(sbo.getName());
		
			to.setSubjectId(sbo.getId());
			return to;

		}
		
		return new ExamSubDefinitionCourseWiseTO();

	}

	public ArrayList<KeyValueTO> convertBOToTO_SubjectSection_List(
			List<ExamSubjectSectionMasterBO> listBO) {
		ArrayList<KeyValueTO> l = new ArrayList<KeyValueTO>();
		Iterator<ExamSubjectSectionMasterBO> itr = listBO.iterator();
		while (itr.hasNext()) {
			ExamSubjectSectionMasterBO bo = (ExamSubjectSectionMasterBO) itr
					.next();
			l.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(l,new KeyValueTOComparator());
		return l;

	}

	public ArrayList<ExamSubCoursewiseGradeDefnTO> convertBOToTO_GradeDetails(
			List<ExamSubCoursewiseGradeDefnBO> listBO) {
		ArrayList<ExamSubCoursewiseGradeDefnTO> listTO = new ArrayList<ExamSubCoursewiseGradeDefnTO>();
		ExamSubCoursewiseGradeDefnTO eTO;
		ExamGenHandler handler = new ExamGenHandler();
		for (ExamSubCoursewiseGradeDefnBO bo : listBO) {

			int subjectId = bo.getSubjectId();

			eTO = new ExamSubCoursewiseGradeDefnTO(bo.getStartPrcntgGrade()
					.toString(), bo.getEndPrcntgGrade().toString(), bo
					.getGrade(), bo.getGradeInterpretation(), bo
					.getResultClass(), bo.getGradePoint().toString(),
					subjectId,

					handler.getSubjectName(subjectId), bo.getId());

			listTO.add(eTO);

		}
		
		//Collections.sort(listTO);
		Collections.sort(listTO,new ExamSubCoursewiseGradeDefnTO());
		return listTO;
	}

	public ArrayList<ExamSubCoursewiseGradeDefnTO> convertBOToTO(
			ArrayList<ExamSubCoursewiseGradeDefnBO> listBO) {
		ArrayList<ExamSubCoursewiseGradeDefnTO> listTO = new ArrayList<ExamSubCoursewiseGradeDefnTO>();
		ExamGenHandler handler = new ExamGenHandler();
		for (ExamSubCoursewiseGradeDefnBO bo : listBO) {

			int subjectId = bo.getSubjectId();

			ExamSubCoursewiseGradeDefnTO eTO = new ExamSubCoursewiseGradeDefnTO(bo.getStartPrcntgGrade()
					.toString(), bo.getEndPrcntgGrade().toString(), bo
					.getGrade(), bo.getGradeInterpretation(), bo
					.getResultClass(), bo.getGradePoint().toString(),
					subjectId,

					handler.getSubjectName(subjectId), bo.getId());

			listTO.add(eTO);
		}
		Collections.sort(listTO);
		return listTO;
	}

	public ExamSubjectDefCourseForm createFormObjcet(
			ExamSubjectDefCourseForm objform, ExamSubCoursewiseGradeDefnBO objbo) {
		objform.setSubId(Integer.toString(objbo.getSubjectId()));

		objform.setId(Integer.toString(objbo.getId()));
		objform.setStartPercentage(objbo.getStartPrcntgGrade().toString());
		objform.setEndPercentage(objbo.getEndPrcntgGrade().toString());
		objform.setGrade(objbo.getGrade());
		objform.setInterpretation(objbo.getGradeInterpretation());
		objform.setResultClass(objbo.getResultClass());
		objform.setGradePoint(objbo.getGradePoint().toString());
		objform.setSubId(Integer.toString(objbo.getSubjectId()));
		objform.setOrgStartPercentage(objbo.getStartPrcntgGrade().toString());
		objform.setOrgEndPercentage(objbo.getEndPrcntgGrade().toString());
		objform.setOrgGrade(objbo.getGrade());
		objform.setOrgInterpretation(objbo.getGradeInterpretation());
		objform.setOrgResultClass(objbo.getResultClass());
		objform.setOrgGradePoint(objbo.getGradePoint().toString());
		objform.setOrgSubid(objbo.getSubjectId());
		objform.setSubjectCode(objbo.getSubjectUtilBO().getCode());
		objform.setSubjectName(objbo.getSubjectUtilBO().getName());

		return objform;
	}

	public ArrayList<ExamSubCoursewiseAttendanceMarksTO> convertBOToTOAttendance(
			ArrayList<ExamSubCoursewiseAttendanceMarksBO> listBO) {
		ArrayList<ExamSubCoursewiseAttendanceMarksTO> listTO = new ArrayList<ExamSubCoursewiseAttendanceMarksTO>();
		for (ExamSubCoursewiseAttendanceMarksBO bo : listBO) {

			int subjectId = bo.getSubjectId();

			ExamSubCoursewiseAttendanceMarksTO eTO = new ExamSubCoursewiseAttendanceMarksTO(bo.getId().intValue(),
					bo.getAttendanceMarks().toString(), bo
							.getFromPrcntgAttndnc().toString(), bo
							.getToPrcntgAttndnc().toString(), bo
							.getSubjectUtilBO().getCode(), subjectId, bo
							.getSubjectUtilBO().getName());

			listTO.add(eTO);
		}
		Collections.sort(listTO);
		return listTO;
	}

	// ---------Define Attendance---------------

	public ExamSubjectDefCourseForm createFormObjcetAttendance(
			ExamSubjectDefCourseForm form,
			ExamSubCoursewiseAttendanceMarksBO objBO) {
		form.setId(objBO.getId().toString());
		form.setSubId(Integer.toString(objBO.getSubjectId()));
		form.setStartPercentage(objBO.getFromPrcntgAttndnc().toString());
		form.setEndPercentage(objBO.getToPrcntgAttndnc().toString());
		form.setMarks(objBO.getAttendanceMarks().toString());

		form.setOrgSubid(objBO.getSubjectId());
		form.setOrgStartPercentage(objBO.getFromPrcntgAttndnc().toString());
		form.setOrgEndPercentage(objBO.getToPrcntgAttndnc().toString());
		form.setOrgMarks(objBO.getAttendanceMarks().toString());

		return form;
	}

	public ArrayList<ExamSubDefinitionCourseWiseDisplayTO> convertBOToDisplayTO(
			List<ExamSubDefinitionCourseWiseBO> listBO) {

		ArrayList<ExamSubDefinitionCourseWiseDisplayTO> listTO = new ArrayList<ExamSubDefinitionCourseWiseDisplayTO>();
		ExamSubDefinitionCourseWiseDisplayTO to = null;

		Iterator it = listBO.iterator();
		while (it.hasNext()) {
			Object[] row = (Object[]) it.next();
			to = new ExamSubDefinitionCourseWiseDisplayTO();
			String value = (Integer.parseInt(row[12].toString().trim()) > 0) ? "on"
					: "off";
			to.setDontAddTotMarkClsDecln(value);

			value = (Integer.parseInt(row[13].toString()) > 0) ? "on" : "off";
			to.setDontShowSubType(value);

			value = (Integer.parseInt(row[14].toString()) > 0) ? "on" : "off";
			to.setDontShowMaxMarks(value);

			value = (Integer.parseInt(row[15].toString()) > 0) ? "on" : "off";
			to.setDontShowAttMarks(value);

			value = (Integer.parseInt(row[16].toString()) > 0) ? "on" : "off";
			to.setDontShowMinMarks(value);

			value = (Integer.parseInt(row[17].toString()) > 0) ? "on" : "off";
			to.setDontConsiderFailureTotalResult(value);

			value = (Integer.parseInt(row[18].toString()) > 0) ? "on" : "off";
			to.setShowInternalFinalMarkAdded(value);

			value = (Integer.parseInt(row[19].toString()) > 0) ? "on" : "off";
			to.setShowOnlyGrade(value);

			to.setSubjectId(Integer.parseInt(row[0].toString()));

			to.setSubjectName(row[1].toString());
			to.setSubjectCode(row[2].toString());
			to.setId(Integer.parseInt(row[3].toString()));
			to.setSubjectOrder(Integer.parseInt(row[4].toString()));
			to.setUniversitySubjectCode(row[5].toString());
			to.setSubjectSectionId(Integer.parseInt(row[6].toString()));
			to.setSectionName(row[7].toString());
			to.setTheoryHours(Integer.parseInt(row[8].toString()));
			to.setTheoryCredit(Integer.parseInt(row[9].toString()));
			to.setPracticalHours(Integer.parseInt(row[10].toString()));

			to.setPracticalCredit(Integer.parseInt(row[11].toString()));
			listTO.add(to);

		}
		Collections.sort(listTO);
		return listTO;
	}
	
	public String getCheckExistedQuery(ExamSubjectDefCourseForm objform) throws Exception {
		String schemeNo="";
		String[] termNo;
		if(objform.getScheme()!=null && !objform.getScheme().isEmpty()){
			schemeNo=objform.getScheme();
			termNo=schemeNo.split("_");
			schemeNo=termNo[1];
		}else{
			if(!objform.getSchemeType().isEmpty()){
				if(objform.getSchemeType().equalsIgnoreCase("1")){
					schemeNo="1,3,5,7,9";
				}else if(objform.getSchemeType().equalsIgnoreCase("2")){
					schemeNo="2,4,6,8,10";
				}else{
					schemeNo="1,2,3,4,5,6,7,8,9,10";
				}
			}
		}
		String [] tempArray = objform.getCourseIds();
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<tempArray.length;i++){
			intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		String query="select e.courseId from ExamSubDefinitionCourseWiseBO e" +
				" where e.academicYear= " +objform.getAcademicYear()+
				" and e.courseId in ("+intType+") and e.schemeNo in ("+schemeNo+") group by e.courseId";
		return query;
	}
	
	public String getCheckExistedQueryCopy(ExamSubjectDefCourseForm objform) throws Exception {
		String schemeNo="";
		String[] termNo;
		if(objform.getScheme()!=null && !objform.getScheme().isEmpty()){
			schemeNo=objform.getScheme();
			termNo=schemeNo.split("_");
			schemeNo=termNo[1];
		}else{
			if(!objform.getSchemeType().isEmpty()){
				if(objform.getSchemeType().equalsIgnoreCase("1")){
					schemeNo="1,3,5,7,9";
				}else if(objform.getSchemeType().equalsIgnoreCase("2")){
					schemeNo="2,4,6,8,10";
				}else{
					schemeNo="1,2,3,4,5,6,7,8,9,10";
				}
			}
		}
		String [] tempArray = objform.getCourseIds();
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<tempArray.length;i++){
			intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		String query="select e.courseId from ExamSubDefinitionCourseWiseBO e" +
				" where e.academicYear=" +Integer.parseInt(objform.getToYear())+
				" and e.courseId in ("+intType+") and e.schemeNo in ("+schemeNo+") group by e.courseId";
		return query;
	}
	
	public boolean copySubjectDefinitionCourseWise(List<String> list, String query,
			ExamSubjectDefCourseForm objform) throws Exception {
		Session session = null;
		Transaction transaction = null;
		List<ExamSubDefinitionCourseWiseBO> boList = null;
		ExamSubDefinitionCourseWiseBO bo;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query selectedCandidatesQuery=session.createQuery(query);
			boList = selectedCandidatesQuery.list();
			Iterator<ExamSubDefinitionCourseWiseBO> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				bo = tcIterator.next();	
				ExamSubDefinitionCourseWiseBO bo1 = new ExamSubDefinitionCourseWiseBO();
				bo1.setSubjectId(bo.getSubjectId());
				bo1.setSubjectOrder(bo.getSubjectOrder());
				bo1.setUniversitySubjectCode(bo.getUniversitySubjectCode());
				bo1.setSubjectSectionId(bo.getSubjectSectionId());
				if(bo.getSubjectUtilBO().getIsTheoryPractical().equalsIgnoreCase("T"))
				bo1.setTheoryHours(bo.getTheoryHours());
				else
					bo1.setPracticalHours(bo.getPracticalHours());
				if(bo.getSubjectUtilBO().getIsTheoryPractical().equalsIgnoreCase("T"))
				bo1.setTheoryCredit(bo.getTheoryCredit());
				else
					bo1.setPracticalCredit(bo.getPracticalCredit());	
				bo1.setDontShowSubType(bo.getDontShowSubType());
				bo1.setDontShowMaxMarks(bo.getDontShowMaxMarks());
				bo1.setDontShowMinMarks(bo.getDontShowMinMarks());
				bo1.setDontShowAttMarks(bo.getDontShowAttMarks());
				bo1.setDontAddTotMarkClsDecln(bo.getDontAddTotMarkClsDecln());
				bo1.setShowInternalFinalMarkAdded(bo.getShowInternalFinalMarkAdded());
				bo1.setShowOnlyGrade(bo.getShowOnlyGrade());
				bo1.setIsActiveGradeDefn(bo.getIsActiveGradeDefn());
				bo1.setIsActiveAttndnc(bo.getIsActiveAttndnc());
				bo1.setCourseId(bo.getCourseId());
				bo1.setSchemeNo(bo.getSchemeNo());
				bo1.setCreatedDate(new Date());
				bo1.setCreatedBy(objform.getUserId());
				bo1.setLastModifiedDate(new Date());
				bo1.setModifiedBy(objform.getUserId());
				bo1.setAcademicYear(Integer.parseInt(objform.getYear()));
				session.save(bo1);
				if(++count % 20 == 0){
					session.flush();
//					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}
}
