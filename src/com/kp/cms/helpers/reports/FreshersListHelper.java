package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.forms.reports.FreshersListForm;
import com.kp.cms.to.admin.StudentTO;

public class FreshersListHelper {
private static final Log log = LogFactory.getLog(FreshersListHelper.class);
	
	public static volatile FreshersListHelper freshersListHelper = null;

	private FreshersListHelper(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static FreshersListHelper getInstance() {
		if (freshersListHelper == null) {
			freshersListHelper = new FreshersListHelper();
		}
		return freshersListHelper;
	}
	
	/**
	 * 
	 * @param listForm
	 * @returns search criteria
	 */

	public String getSearchCriteria(FreshersListForm listForm) throws Exception{
		log.info("Entering into getSearchCriteria of FreshersListHelper");
		String searchCriteria ="select st.classSchemewise.classes.id, "
		+ "st.classSchemewise.classes.name, "
		+ "st.admAppln.applnNo, "
		+ "st.admAppln.personalData "
		+ "from FeePayment fm, Student st " 
		+ "where st.admAppln.applnNo=fm.applicationNo " 
		+ "and st.classSchemewise.curriculumSchemeDuration.academicYear= fm.academicYear ";
		searchCriteria = searchCriteria + commonSearch(listForm);
		log.info("Leaving into getSearchCriteria of FreshersListHelper");
		return searchCriteria;
	}
	/**
	 * 
	 * @param listForm
	 * @returns common search query
	 * @throws Exception
	 */
	private String commonSearch(FreshersListForm listForm) throws Exception{
		log.info("Entering into commonSearch of FreshersListHelper");
		StringBuffer commonSearch = new StringBuffer();
		
		String programId = "and st.classSchemewise.classes.course.program.id = " + Integer.parseInt(listForm.getProgramId());
		commonSearch.append(programId);
		
		String programTypeId = " and st.classSchemewise.classes.course.program.programType.id = " + Integer.parseInt(listForm.getProgramTypeId());
		commonSearch.append(programTypeId);
		
		String feeStatus ="";
		
		if(listForm.getFeeStatus().equals("1")){
			feeStatus =  " and fm.isFeePaid = 1 ";
			commonSearch.append(feeStatus);
		}
		else if(listForm.getFeeStatus().equals("2")){
			feeStatus = " and fm.isChallenPrinted = 1 ";
			commonSearch.append(feeStatus);
		}
		else if(listForm.getFeeStatus().equals("3")){
			feeStatus = " and fm.isCompletlyPaid = 1 ";
			commonSearch.append(feeStatus);
		}
		if(listForm.getClassId().length() > 0){
			String classId = " and st.classSchemewise.classes.id = " +Integer.parseInt(listForm.getClassId());
			commonSearch.append(classId);
		}
		commonSearch.append(" order by st.classSchemewise.classes.id");
		log.info("Leaving into commonSearch of FreshersListHelper");
		return commonSearch.toString();
	}
	/**
	 * 
	 * @param fresherBOList
	 * @returns list of fresher students
	 * converts BOS to TOS
	 * @throws Exception
	 */
	public List<StudentTO> populateStudentListByClassWise(
			List<Object[]> fresherBOList)throws Exception {
		log.info("Entering into populateStudentListByClassWise of FreshersListHelper");
		List<StudentTO> studentList = new ArrayList<StudentTO>();
		Set<Integer> classIdSet = new HashSet<Integer>();
		StudentTO studentTO = null;
		Map<Integer, List<StudentTO>> studentMap = new HashMap<Integer, List<StudentTO>>();
		int slNo= 1;
		StringBuffer buffer = null;
		if(fresherBOList!=null){
			Iterator<Object[]> iterator = fresherBOList.iterator();
			while (iterator.hasNext()) {
				Object[] object = (Object[]) iterator.next();
				int classId=0;
				String className="";
				int applicationNO = 0;
				PersonalData personalData= null;
				if(object[0]!=null){
					classId = (Integer)object[0];
				}
				if(object[1]!=null){
					className = (String)object[1];
				}
				if(object[2]!=null){
					applicationNO = (Integer)object[2];
				}
				if(object[3]!=null){
					personalData = (PersonalData)object[3];
				}
				double marksObtained = 0;
				double totalMarks = 0;
				double percentage = 0;
				//Prepares the data based on the class. Works for new class
			if(!classIdSet.contains(classId)){
				studentList = new ArrayList<StudentTO>();
				buffer = new StringBuffer();
				slNo= 1;
				studentTO = new StudentTO();
				studentTO.setSlNo(slNo);
				if(className!=null){
					studentTO.setClassName(className);
				}
				if(applicationNO!=0){
					studentTO.setApplicationNo(applicationNO);
				}
				if(personalData!=null){
					if(personalData.getFirstName()!=null){
						buffer.append(personalData.getFirstName());
					}
					if(personalData.getMiddleName()!=null){
						buffer.append(" " + personalData.getMiddleName());
					}
					if(personalData.getLastName()!=null){
						buffer.append(" " + personalData.getLastName());
					}
					if(personalData.getGender()!=null){
						studentTO.setGender(personalData.getGender());
					}
					if(personalData.getSecondLanguage()!=null){
						studentTO.setSecondLanguage(personalData.getSecondLanguage());
					}
					if(personalData.getEdnQualifications()!=null){
					Iterator<EdnQualification> ednIterator = personalData.getEdnQualifications().iterator();
						while (ednIterator.hasNext()) {
							EdnQualification ednQualification = ednIterator.next();
							if(ednQualification.getDocChecklist() != null && 
							ednQualification.getDocChecklist().getIsPreviousExam()!=null && ednQualification.getDocChecklist().getIsPreviousExam()){
								if(ednQualification.getMarksObtained()!=null){
									marksObtained = ednQualification.getMarksObtained().doubleValue();
								}
								if(ednQualification.getTotalMarks()!=null){
									totalMarks = ednQualification.getTotalMarks().doubleValue();
								}
								if(marksObtained != 0 && totalMarks != 0){
								percentage = marksObtained/totalMarks*100;
								}
								studentTO.setPercentageOfMarks(String.valueOf(Integer.valueOf((int)percentage).intValue()));
							}
						}
					}					
				}
				studentTO.setStudentName(buffer.toString());
				studentList.add(studentTO);
				++slNo;
			}
			//Works for old classes
			else{
				studentList = studentMap.get(classId);
				studentTO = new StudentTO();
				studentTO.setSlNo(slNo);
				if(className!=null){
					studentTO.setClassName(className);
				}
				if(applicationNO!=0){
					studentTO.setApplicationNo(applicationNO);
				}
				if(personalData!=null){
					if(personalData.getFirstName()!=null){
						buffer.append(personalData.getFirstName());
					}
					if(personalData.getMiddleName()!=null){
						buffer.append(" " + personalData.getMiddleName());
					}
					if(personalData.getLastName()!=null){
						buffer.append(" " + personalData.getLastName());
					}
					if(personalData.getGender()!=null){
						studentTO.setGender(personalData.getGender());
					}
					if(personalData.getSecondLanguage()!=null){
						studentTO.setSecondLanguage(personalData.getSecondLanguage());
					}
					if(personalData.getEdnQualifications()!=null){
					Iterator<EdnQualification> ednIterator = personalData.getEdnQualifications().iterator();
						while (ednIterator.hasNext()) {
							EdnQualification ednQualification = ednIterator.next();
							if(ednQualification.getDocChecklist() != null && 
							ednQualification.getDocChecklist().getIsPreviousExam()!=null && ednQualification.getDocChecklist().getIsPreviousExam()){
								if(ednQualification.getMarksObtained()!=null){
									marksObtained = ednQualification.getMarksObtained().doubleValue();
								}
								if(ednQualification.getTotalMarks()!=null){
									totalMarks = ednQualification.getTotalMarks().doubleValue();
								}
								if(marksObtained != 0 && totalMarks != 0){
								percentage = marksObtained/totalMarks*100;
								}
								studentTO.setPercentageOfMarks(String.valueOf(Integer.valueOf((int)percentage).intValue()));
							}
						}
					}					
				}
				studentTO.setStudentName(buffer.toString());
				studentList.add(studentTO);
				++slNo;
			}			
			studentMap.put(classId, studentList);								
			classIdSet.add(classId);				
			}
		}
		List<StudentTO> freshStudentList = new ArrayList<StudentTO>();
		if(studentMap!=null && !studentMap.isEmpty()){
			Iterator stIterator = studentMap.keySet().iterator();
			while (stIterator.hasNext()) {
				int classId = (Integer) stIterator.next();
				List<StudentTO> eachStudentList = studentMap.get(classId);
				freshStudentList.addAll(eachStudentList);
			}
		}		
		log.info("Leaving into populateStudentListByClassWise of FreshersListHelper");
		return freshStudentList;
	}
}
