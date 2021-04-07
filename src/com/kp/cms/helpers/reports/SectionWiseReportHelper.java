package com.kp.cms.helpers.reports;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.forms.reports.SectionWiseForm;
import com.kp.cms.to.reports.SectionWiseReportTO;

public class SectionWiseReportHelper {
	
	private static final Log log = LogFactory.getLog(SectionWiseReportHelper.class);
		
		/**
		 * @param leaveReportForm
		 * @return
		 * This method will build dynamic query
		 */

		private static String commonSearch(SectionWiseForm sectionWiseForm) {
			log.info("entered commonSearch method in SectionWiseReportHelper class");
			
			String query = "";
			if (sectionWiseForm.getProgramTypeId().trim().length() > 0 && !StringUtils.isEmpty(sectionWiseForm.getProgramTypeId())) {
				String programType = " and st.admAppln.courseBySelectedCourseId.program.programType.id = "+ sectionWiseForm.getProgramTypeId();
					query = query + programType;
			}
			if(sectionWiseForm.getProgramId().trim().length() > 0 && !StringUtils.isEmpty(sectionWiseForm.getProgramId())){
				String program = " and st.admAppln.courseBySelectedCourseId.program.id = "+ sectionWiseForm.getProgramId();	
					query = query + program;
			}	
			if(sectionWiseForm.getCourseId().trim().length() > 0 && !StringUtils.isEmpty(sectionWiseForm.getCourseId())){
				String course = " and st.admAppln.courseBySelectedCourseId.id = "+sectionWiseForm.getCourseId();	
					query = query + course;
			}	
			if(sectionWiseForm.getSemister().trim().length() > 0 && !StringUtils.isEmpty(sectionWiseForm.getSemister())){
				String semister = " and st.classSchemewise.curriculumSchemeDuration.semesterYearNo = "+ sectionWiseForm.getSemister();
					query = query + semister;
			}
			if(sectionWiseForm.getYear()!=null && !sectionWiseForm.getYear().isEmpty())
			query = query+ " and st.admAppln.appliedYear ="+sectionWiseForm.getYear();
			query = query+" order by st.admAppln.courseBySelectedCourseId.id";
			log.info("exit of commonSearch method in SectionWiseReportHelper class");
			return query;
		}

		/**
		 * @param sectionWiseForm
		 * @return value of type string.
		 * This method will give final query
		 */
		/*public static String getSelectionSearchCriteria(SectionWiseForm sectionWiseForm) {
			log.info("entered getSelectionSearchCriteria method in SectionWiseReportHelper class");
			String statusCriteria = commonSearch(sectionWiseForm);
			
			String searchCriteria= "select st.admAppln.applnNo,"+
			" st.registerNo,"+
			" st.rollNo,"+
			" st.admAppln.personalData.gender,"+
			" st.admAppln.personalData.firstName,"+
			" st.admAppln.personalData.middleName,"+
			" st.admAppln.personalData.lastName,"+
			" stqual.percentage,"+ 
			" st.classSchemewise.classes.sectionName,"+
			" stqual.secondLanguage,"+
			" st.classSchemewise.classes.id,"+
			" st.classSchemewise.classes.name,"+
			" st.classSchemewise.curriculumSchemeDuration.academicYear"+
			" from Student st join st.admAppln.studentQualifyexamDetails stqual"+
			" where st.isAdmitted = 1"+ statusCriteria;
			log.info("exit of getSelectionSearchCriteria method in SectionWiseReportHelper class");
			return searchCriteria;

		}*/
		
		/**
		 * This method is used to convert BOs to TOs from list
		 * @param list
		 * @param leaveReportForm
		 * @return
		 */

		public static Map<Integer,List<SectionWiseReportTO>> convertBOstoTOs(
				List<Object[]> list, SectionWiseForm sectionWiseForm,Map<Integer,ExamStudentDetentionRejoinDetails> detantionMap) {
			Map<Integer,List<SectionWiseReportTO>> sectionMap = new LinkedHashMap<Integer, List<SectionWiseReportTO>>();
			List<SectionWiseReportTO> sectionWiseList = new ArrayList<SectionWiseReportTO>();
			SectionWiseReportTO wiseReportTO;
			int sno = 0;
			if(list != null && list.size() != 0){
				Iterator<Object[]> iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					wiseReportTO = new SectionWiseReportTO();
					++sno;
					wiseReportTO.setSlno(sno);
					if(obj[0] != null){
						wiseReportTO.setApplnNumber(String.valueOf((Integer)obj[0]));
					}
					if(obj[1] != null){
						wiseReportTO.setRegNo((String)obj[1]);
					}
					if(obj[2] != null){
						wiseReportTO.setRollNo((String)obj[2]);
					}
					if(obj[3] != null){
						wiseReportTO.setGender((String)obj[3]);
					}
					String applicantName = "";
					if (obj[4] != null) {
						applicantName = applicantName + obj[4]+ " ";
					}
					if (obj[5] != null) {
						applicantName = applicantName + obj[5]+ " ";
					}
					if (obj[6] != null) {
						applicantName = applicantName + obj[6];
					}
					wiseReportTO.setStudentName(applicantName);
					if(obj[7] != null){
						PersonalData personalData=(PersonalData)obj[7];
						Set<EdnQualification> ednSet=personalData.getEdnQualifications();
						if(ednSet!=null){
							Iterator<EdnQualification> itr=ednSet.iterator();
							while (itr.hasNext()) {
								EdnQualification ednQualification = (EdnQualification) itr.next();
								if(ednQualification.getDocChecklist() != null){
								if(ednQualification.getDocChecklist().getIsPreviousExam()!=null && ednQualification.getDocChecklist().getIsPreviousExam()){
									if(ednQualification.getPercentage()!=null)
									wiseReportTO.setPercentage(ednQualification.getPercentage().toString());
								}
								}
							}
						}
					}
					if(obj[8] != null){
						wiseReportTO.setSectionName((String)obj[8]);
					}
					if(obj[9] != null){
						wiseReportTO.setSecondLanguage((String)obj[9]);
					}
					if(obj[10] != null){
						wiseReportTO.setClassId((Integer)obj[10]);
					}
					if(obj[11] != null){
						wiseReportTO.setClassName((String)obj[11]);
					}
					if(obj[12] != null){
						wiseReportTO.setAcedamicYear(String.valueOf((Integer)obj[12])+"-"+String.valueOf((Integer)obj[12]+1));
						sectionWiseForm.setAcademicYear(String.valueOf((Integer)obj[12])+"-"+String.valueOf((Integer)obj[12]+1));
					}
					if(obj[13]!=null){
						ExamStudentDetentionRejoinDetails bo=detantionMap.get(Integer.parseInt(obj[13].toString()));
						if(bo!=null){
							if(bo.getRejoin()!=null && bo.getRejoin()){
								sectionWiseList.add(wiseReportTO);
							}
						}else{
							sectionWiseList.add(wiseReportTO);
						}
					}
					
				}
			}
			Iterator<SectionWiseReportTO> iterator1 = sectionWiseList.iterator();
			while (iterator1.hasNext()) {
				SectionWiseReportTO wiseReportTO2 = (SectionWiseReportTO) iterator1.next();
				addSectionDataToMap(wiseReportTO2,sectionMap);
			}
			return sectionMap;
		}

		private static void addSectionDataToMap(
				SectionWiseReportTO wiseReportTO2,
				Map<Integer, List<SectionWiseReportTO>> sectionMap) {
			List<SectionWiseReportTO> sectionWiseList;
			if (sectionMap.containsKey(wiseReportTO2.getClassId())) {
				sectionWiseList = sectionMap.get(wiseReportTO2.getClassId());
				sectionWiseList.add(wiseReportTO2);
			} else {
				sectionWiseList = new ArrayList<SectionWiseReportTO>();
				sectionWiseList.add(wiseReportTO2);
			}
			sectionMap.put(wiseReportTO2.getClassId(), sectionWiseList);
		}				
		/**
		 * @param sectionWiseForm
		 * @return value of type string.
		 * This method will give final query
		 */
		public static String getSelectionSearchCriteria(SectionWiseForm sectionWiseForm) {
			log.info("entered getSelectionSearchCriteria method in SectionWiseReportHelper class");
			String statusCriteria = commonSearch(sectionWiseForm);
			String searchCriteria= "select st.admAppln.applnNo," +
					" st.registerNo,st.rollNo," +
					" st.admAppln.personalData.gender,st.admAppln.personalData.firstName," +
					" st.admAppln.personalData.middleName,st.admAppln.personalData.lastName," +
					" st.admAppln.personalData,st.classSchemewise.classes.sectionName," +
					" st.admAppln.personalData.secondLanguage,st.classSchemewise.classes.id," +
					" st.classSchemewise.classes.name,st.classSchemewise.curriculumSchemeDuration.academicYear,st.id"+
					" from Student st"+
					" where st.isAdmitted = 1 and st.admAppln.isCancelled=0 and st.isActive=1"+ statusCriteria;
			log.info("exit of getSelectionSearchCriteria method in SectionWiseReportHelper class");
			return searchCriteria;

		}
}