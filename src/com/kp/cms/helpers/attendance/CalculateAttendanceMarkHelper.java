package com.kp.cms.helpers.attendance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamStudentAttendanceMarksCjcBO;
import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.forms.attendance.CalculateAttendanceMarkCjcForm;
import com.kp.cms.forms.exam.NewUpdateProccessForm;
import com.kp.cms.helpers.exam.NewUpdateProccessHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.SubjectRuleSettingsTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.transactions.attandance.ICalculateAttendanceMarkTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.INewUpdateProccessTransaction;
import com.kp.cms.transactions.exam.IUpdateStudentSGPATxn;
import com.kp.cms.transactionsimpl.attendance.CalculateAttendanceMarkImpl;
import com.kp.cms.transactionsimpl.exam.ExamUpdateProcessImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewUpdateProccessTransactionImpl;
import com.kp.cms.transactionsimpl.exam.UpdateExamStudentSGPAImpl;
import com.kp.cms.utilities.CommonUtil;

public class CalculateAttendanceMarkHelper {
	
	static DecimalFormat df = new DecimalFormat("#.##");
	public static List<Integer> avoidExamIds=new ArrayList<Integer>();

	IUpdateStudentSGPATxn iUpdateStudentSGPATxn = UpdateExamStudentSGPAImpl.getInstance();
	ICalculateAttendanceMarkTransaction transaction=CalculateAttendanceMarkImpl.getInstance();
	/**
	 * Singleton object of NewUpdateProccessHelper
	 */
	private static volatile CalculateAttendanceMarkHelper calculateAttMarkHelper = null;
	private static final Log log = LogFactory.getLog(CalculateAttendanceMarkHelper.class);
	private CalculateAttendanceMarkHelper() {
		
	}
	/**
	 * return singleton object of NewUpdateProccessHelper.
	 * @return
	 */
	public static CalculateAttendanceMarkHelper getInstance() {
		if (calculateAttMarkHelper == null) {
			calculateAttMarkHelper = new CalculateAttendanceMarkHelper();
		}
		return calculateAttMarkHelper;
	}
	ExamUpdateProcessImpl impl = new ExamUpdateProcessImpl();
	

	public String getQueryForBatchYear() throws Exception{
		String query="select c.year" +
	" from ExamDefinition e" +
	" join e.courseSchemeDetails courseDetails" +
	" join courseDetails.course.classes classes" +
	" join classes.classSchemewises classwise" +
	" join classwise.curriculumSchemeDuration.curriculumScheme c" +
	" where e.delIsActive=1 group by c.year order by c.year";
	return query;
	}
	
	public String getClassesQuery(CalculateAttendanceMarkCjcForm calAttMarkCjcForm) throws Exception{
		String query="select c.curriculumSchemeDuration.academicYear, cs.year, c.classes from ClassSchemewise c "+
					 "join c.curriculumSchemeDuration.curriculumScheme cs " +
				     "where c.classes.course.isActive = 1 and c.classes.isActive = 1";
		
		if(calAttMarkCjcForm.getAcademicYear()!=null && !calAttMarkCjcForm.getAcademicYear().isEmpty()){
			query=query+" and c.curriculumSchemeDuration.academicYear="+calAttMarkCjcForm.getAcademicYear();
		}
		query=query+" order by c.classes.name";
		return query;
	}
	
	public List<ClassesTO> convertBoListToTOList(List list) throws Exception {
		List<ClassesTO> mainList=new ArrayList<ClassesTO>();
		if(list!=null && !list.isEmpty()){
			Iterator itr=list.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				ClassesTO to=new ClassesTO();
				if(object[0]!=null)
					to.setYear(Integer.parseInt(object[0].toString()));
				if(object[1]!=null)
					to.setBatchYear(Integer.parseInt(object[1].toString()));
				if(object[2]!=null){
					Classes c=(Classes)object[2];
					to.setId(c.getId());
					to.setClassName(c.getName());
					to.setTermNo(c.getTermNumber());
					to.setCourseId(c.getCourse().getId());
				}
				mainList.add(to);	
			}
		}
		return mainList;
	}
	
	public boolean calculateAttendanceMarks(CalculateAttendanceMarkCjcForm calAttMarkCjcForm) throws Exception {
		double points=0;
		List<ClassesTO> classList=calAttMarkCjcForm.getClassesList();
		List<ExamStudentAttendanceMarksCjcBO> boList=new ArrayList<ExamStudentAttendanceMarksCjcBO>();
		if(classList!=null && !classList.isEmpty()){
			Iterator<ClassesTO> itr=classList.iterator();
			while (itr.hasNext()) {
				ClassesTO to = (ClassesTO) itr.next();
				if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
					List<StudentTO> studentList=getStudentListForClass(to.getId());
					if(studentList!=null && !studentList.isEmpty()){
						Iterator<StudentTO> sitr=studentList.iterator();
						while (sitr.hasNext()) {
							StudentTO sto = (StudentTO) sitr.next();
						//	if(sto.getId()==12073 ){
							Map<String,StudentAttendanceTO> stuAttMap=transaction.getAttendanceForStudent(to.getId(),sto.getId(),sto.getSubjectIdList());
							if(sto.getSubjectIdList()!=null && !sto.getSubjectIdList().isEmpty()){
								Iterator<Integer> subItr=sto.getSubjectIdList().iterator();
								while (subItr.hasNext()) {
									Integer subId = (Integer) subItr.next();
//									if(subId==902){//remove this
									ExamStudentAttendanceMarksCjcBO bo=new ExamStudentAttendanceMarksCjcBO();
									Student student=new Student();
									student.setId(sto.getId());
									bo.setStudent(student);
									Classes classes=new Classes();
									classes.setId(to.getId());
									bo.setClasses(classes);
									Subject subject=new Subject();
									subject.setId(subId);
									bo.setSubject(subject);
										if(stuAttMap.containsKey(String.valueOf(subId))){
											StudentAttendanceTO std=stuAttMap.get(String.valueOf(subId));
											float hoursHeld=std.getSubjectHoursHeld();
											float hoursAtt=std.getPresentHoursAtt();
											float percentage=(hoursAtt/hoursHeld)*100;
											percentage=CommonUtil.roundToDecimal(percentage, 2);
											points=transaction.getAttendanceMarksForPercentage(to.getCourseId(),subId,percentage);
											bo.setAttendanceMarks(String.valueOf(points));
											bo.setAttendancePercentage(String.valueOf(percentage));
											bo.setIsActive(true);
											bo.setCreatedBy(calAttMarkCjcForm.getUserId());
											bo.setCreatedDate(new Date());
											bo.setModifiedBy(calAttMarkCjcForm.getUserId());
											bo.setLastModifiedDate(new Date());
											
										}
										else
										{
											bo.setAttendanceMarks("0");
											bo.setAttendancePercentage("0");
											bo.setIsActive(true);
											bo.setCreatedBy(calAttMarkCjcForm.getUserId());
											bo.setCreatedDate(new Date());
											bo.setModifiedBy(calAttMarkCjcForm.getUserId());
											bo.setLastModifiedDate(new Date());
										}
										boList.add(bo);
								}
//							}//remove this
							}
//						}// remove this
						}
					}
				}
			}
		}
		return transaction.saveAttendanceMarks(boList);
	}
	
	/**
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> getStudentListForClass(int classId) throws Exception {
		List<StudentTO> studentList=new ArrayList<StudentTO>();
		String query=getCurrentClassQuery(classId);// Getting Current Class Students Query
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Student> currentStudentList=transaction.getDataForQuery(query);
		getFinalStudentsForCurrentClass(currentStudentList,studentList);//Adding current Class Students for StudentList
		String preQuery=getPreviousClassQuery(classId);
		List<Object[]> previousStudentList=transaction.getDataForQuery(preQuery);
		getFinalStudentsForPreviousClass(previousStudentList,studentList);
		return studentList;
	}
	/**
	 * @param previousStudentList
	 * @param studentList
	 * @throws Exception
	 */
	private void getFinalStudentsForPreviousClass(List<Object[]> previousStudentList, List<StudentTO> studentList) throws Exception{
		Map<Integer,StudentTO> studentMap=new HashMap<Integer, StudentTO>();
		if(previousStudentList!=null && !previousStudentList.isEmpty()){
			Iterator<Object[]> preItr=previousStudentList.iterator();
			while (preItr.hasNext()) {
				Object[] obj = (Object[]) preItr.next();
				if(obj[0]!=null && obj[1]!=null){
					if(studentMap.containsKey(Integer.parseInt(obj[0].toString()))){
						StudentTO to=studentMap.remove(Integer.parseInt(obj[0].toString()));
						to.setId(Integer.parseInt(obj[0].toString()));
						if(obj[3]!=null)
						to.setAppliedYear(Integer.parseInt(obj[3].toString()));
						if(obj[2]!=null)
						to.setRegisterNo(obj[2].toString());
						List<SubjectTO> subList=to.getSubjectList();
						List<Integer> subIdList=to.getSubjectIdList();
						Subject subject=(Subject)obj[1];
						SubjectTO subTo=new SubjectTO();
						subTo.setId(subject.getId());
						subTo.setName(subject.getName());
						subList.add(subTo);
						subIdList.add(subject.getId());
						to.setSubjectList(subList);
						to.setSubjectIdList(subIdList);
						studentMap.put(to.getId(),to);
					}else{
						StudentTO to=new StudentTO();
						to.setId(Integer.parseInt(obj[0].toString()));
						if(obj[2]!=null)
							to.setRegisterNo(obj[2].toString());
						if(obj[3]!=null)
							to.setAppliedYear(Integer.parseInt(obj[3].toString()));
						List<SubjectTO> subList=new ArrayList<SubjectTO>();
						List<Integer> subIdList=new ArrayList<Integer>();
						Subject subject=(Subject)obj[1];
						SubjectTO subTo=new SubjectTO();
						subTo.setId(subject.getId());
						subTo.setName(subject.getName());
						subList.add(subTo);
						subIdList.add(subject.getId());
						to.setSubjectIdList(subIdList);
						to.setSubjectList(subList);
						studentMap.put(to.getId(),to);
					}
				}
			}
			studentList.addAll(studentMap.values());
		}
	}
	/**
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	private String getPreviousClassQuery(int classId)  throws Exception{
		String query="select s.id,subSet.subject,s.registerNo,s.admAppln.appliedYear from Student s" +
				" join s.studentPreviousClassesHistory classHis" +
				" join classHis.classes.classSchemewises classSchemewise" +
				" join classSchemewise.curriculumSchemeDuration cd" +
				" join s.studentSubjectGroupHistory subjHist " +
				" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
				" where s.admAppln.isCancelled=0 and s.isAdmitted=1 and subSet.isActive=1 and classHis.classes.id=" +classId+
				" and s.classSchemewise.classes.id <> "+classId+
				" and classHis.schemeNo=subjHist.schemeNo" ;
		return query;
	}
	/**
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	private String getCurrentClassQuery(int classId) throws Exception{
		String query="from Student s" +
				" where s.admAppln.isCancelled=0 and s.isAdmitted=1 " +
				" and s.classSchemewise.classes.id="+classId;
		return query;
	}
	/**
	 * @param currentStudentList
	 * @param studentList
	 * @throws Exception
	 */
	private void getFinalStudentsForCurrentClass(List<Student> currentStudentList, List<StudentTO> studentList) throws Exception{
		if(currentStudentList!=null && !currentStudentList.isEmpty()){
			Iterator<Student> itr=currentStudentList.iterator();
			while (itr.hasNext()) {
				Student bo = (Student) itr.next();
				StudentTO to=new StudentTO();
				to.setId(bo.getId());
				to.setAppliedYear(bo.getAdmAppln().getAppliedYear());
				to.setRegisterNo(bo.getRegisterNo());
				Set<ApplicantSubjectGroup> subSet=bo.getAdmAppln().getApplicantSubjectGroups();
				List<SubjectTO> subList=new ArrayList<SubjectTO>();
				List<Integer> subIdList=new ArrayList<Integer>();
				if(subSet!=null && !subSet.isEmpty()){
					Iterator<ApplicantSubjectGroup> subItr=subSet.iterator();
					while (subItr.hasNext()) {
						ApplicantSubjectGroup subGrp = (ApplicantSubjectGroup) subItr.next();
						Set<SubjectGroupSubjects> sub=subGrp.getSubjectGroup().getSubjectGroupSubjectses();
						if (sub!=null && !sub.isEmpty()) {
							Iterator<SubjectGroupSubjects> subGrpSubItr=sub.iterator();
							while (subGrpSubItr.hasNext()) {
								SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subGrpSubItr.next();
								if(subjectGroupSubjects.getIsActive()){
									SubjectTO subTo=new SubjectTO();
									subTo.setId(subjectGroupSubjects.getSubject().getId());
									subTo.setName(subjectGroupSubjects.getSubject().getName());
									subList.add(subTo);
									subIdList.add(subjectGroupSubjects.getSubject().getId());
								}
							}
						}
						
					}
				}
				to.setSubjectList(subList);
				to.setSubjectIdList(subIdList);
				studentList.add(to);
			}
		}
	}

}
