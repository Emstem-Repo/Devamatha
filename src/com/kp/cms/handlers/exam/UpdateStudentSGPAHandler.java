package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.CourseSchemeDetails;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.bo.exam.ExamStudentCCPA;
import com.kp.cms.bo.exam.ExamStudentSgpa;
import com.kp.cms.bo.exam.SemesterWiseExamResults;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ConsolidatedMarksCardForm;
import com.kp.cms.forms.exam.ExamStudentSGPAForm;
import com.kp.cms.helpers.exam.AdminMarksCardHelper;
import com.kp.cms.helpers.exam.ConsolidatedMarksCardHelper;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.helpers.exam.ExamUpdateProcessHelper;
import com.kp.cms.helpers.exam.UpdateStudentSGPAHelper;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.exam.StudentMarkDetailsTO;
import com.kp.cms.transactions.exam.IConsolidatedMarksCardTransaction;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.IUpdateStudentSGPATxn;
import com.kp.cms.transactionsimpl.exam.ConsolidatedMarksCardTransactionImpl;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamUpdateProcessImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.UpdateExamStudentSGPAImpl;
import com.kp.cms.utilities.CommonUtil;
import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;


public class UpdateStudentSGPAHandler {
	private static final Log log = LogFactory.getLog(UpdateStudentSGPAHandler.class);
	public static volatile UpdateStudentSGPAHandler updateStudentSGPAHandler = null;
	ExamUpdateProcessImpl updateImpl = new ExamUpdateProcessImpl();
	ExamUpdateProcessHandler updateHandler = new ExamUpdateProcessHandler();
	ExamUpdateProcessHelper updateHelper = new ExamUpdateProcessHelper();
	private Iterator<ExamStudentSgpa> itr;

	public static UpdateStudentSGPAHandler getInstance() {
		if (updateStudentSGPAHandler == null) {
			updateStudentSGPAHandler = new UpdateStudentSGPAHandler();
			return updateStudentSGPAHandler;
		}
		return updateStudentSGPAHandler;
	}

	/**
	 * 
	 * @param courseId
	 * @param schemeNo
	 * @param academicYear
	 * @return
	 * @throws Exception
	 */
	public boolean updateSGPA(int courseId, int schemeNo, Integer academicYear, String programType, String programId, String userId) throws Exception {
		log.debug("getClassIdsByCourseAndSchemeNo");
		IUpdateStudentSGPATxn iUpdateStudentSGPATxn = UpdateExamStudentSGPAImpl.getInstance();
		Integer classId=0;
		String appliedYear = null;
		boolean isUpdated = false;
		HttpServletRequest request=null;
		ArrayList<Integer> classIdList = iUpdateStudentSGPATxn.getClassIdsByCourseAndScheme(courseId, schemeNo, academicYear);
		if(classIdList.size()>0){

			for(int i=0;i<classIdList.size();i++){
				classId = classIdList.get(i);

				if(classId>0){
					Iterator<Integer> itr = classIdList.iterator();
					ArrayList<Integer> studentList = new ArrayList<Integer>() ;
					Map<Integer,Map<String, ConsolidateMarksCard>> boMap = null;
					String query ="";
					List<Integer> studentIds=new ArrayList<Integer>();
					ArrayList<ExamStudentSgpa> studentSGPAList = new ArrayList<ExamStudentSgpa>();
					ArrayList<SemesterWiseExamResults> studentSGPAListForSemesterResultslist = new ArrayList<SemesterWiseExamResults>(); 
					IDownloadHallTicketTransaction transaction1= DownloadHallTicketTransactionImpl.getInstance();
					INewExamMarksEntryTransaction txn = NewExamMarksEntryTransactionImpl.getInstance();
					Map<Integer,byte[]> photoMap=txn.getStudentPhtosMap(studentIds,false);

					String qry = "from CourseSchemeDetails c where c.program.id = "+programId+" " +
					"and c.schemeNo = "+schemeNo+" and c.course.id= "+courseId+" and c.examDefinition.examType.id = 1 and c.isActive=1 and c.examDefinition.academicYear = "+academicYear+" ";
					List examIDList = txn.getDataForQueryClasses(qry);
					int examId = 0;
					int year =0;
					int month =0;
					String examType = null;
					if(examIDList.size()>0){
						CourseSchemeDetails examIdBo = (CourseSchemeDetails) examIDList.get(0);
						examId = examIdBo.getExamDefinition().getId();
						examType = examIdBo.getExamDefinition().getExamType().getName();
						year = examIdBo.getExamDefinition().getYear();
						month = Integer.parseInt(examIdBo.getExamDefinition().getMonth());
					}
					String  marksCardQuery2014 = null;
					MarksCardTO mto = new MarksCardTO();
					MarksCardTO marksCardTo = null;
					Map<Integer, ExamStudentSgpa> sgpaMap =null;
					ExamStudentSgpa examStudentSgpa = null;
					int sid=0;
					if(examId>0){

						String previousQuery="select s.student.id from StudentPreviousClassHistory s where " +
						"(s.student.isHide = 0 or s.student.isHide is null) and s.student.admAppln.isCancelled=0 and " +
						"  s.classes.id="+classId+" and  s.student.registerNo is not null ";
						List previousList= txn.getDataForQuery(previousQuery);
						if(previousList!=null && !previousList.isEmpty()){
							studentList.addAll(previousList);
						}
						String currentStudentsQuery="select s.id from Student s where (s.isHide = 0 or s.isHide is null) and s.admAppln.isCancelled=0 " +
						"and s.classSchemewise.classes.id="+classId+" and s.registerNo is not null " ; 
						List list=txn.getDataForQuery(currentStudentsQuery);
						if(list!=null && !list.isEmpty()){
							studentList.addAll(list);
						}
						//studentList.add(9021);
						String supMarksCardQuery =null;
						ConsolidatedMarksCardForm consolidatedMarksCardForm = new ConsolidatedMarksCardForm();
						String programTypeqry = "select p.programType.id from Program p where p.id="+programId;
						List<Integer> programTypeIds = txn.getDataForQuery(programTypeqry);
						consolidatedMarksCardForm.setProgramTypeId(String.valueOf(programTypeIds.get(0)));
						if(programType.equalsIgnoreCase("M Phil")&& schemeNo==2)
							supMarksCardQuery= DownloadHallTicketHelper.getInstance().getQueryForMPhilCourse(examId, classId, schemeNo, 0);
						else
							supMarksCardQuery=ConsolidatedMarksCardHelper.getInstance().getConsolidateQueryForSGPA(schemeNo);
						if(boMap == null ){
							IConsolidatedMarksCardTransaction transaction2= ConsolidatedMarksCardTransactionImpl.getInstance();
							List<Object[]> studlist=transaction2.getStudentMarks(supMarksCardQuery,studentList);
							String certificateCourseQuery="select subject_id,is_optional,scheme_no,student_id from student_certificate_course  where is_cancelled=0 and  student_id in (:ids)";
							List certificateList=transaction2.getStudentMarks(certificateCourseQuery,studentList);
							Map<Integer,Map<Integer,Map<Integer,Boolean>>> certificateMap=getCertificateSubjectMap(certificateList);
							List<String> appearedList=transaction2.getSupplimentaryAppeared(studentList);
							if(studlist.size()>0)
							   boMap=ConsolidatedMarksCardHelper.getInstance().getBoListForInput(studlist,consolidatedMarksCardForm,certificateMap,appearedList,String.valueOf(courseId));
							//boMap=ConsolidatedMarksCardHelper.getInstance().getBoListForInputSupplementary(studlist,userId,certificateMap,appearedList,String.valueOf(courseId),programType);
						}

						iUpdateStudentSGPATxn.deleleAlreadyExistedRecords(classId, schemeNo);
						if(studentList!= null && studentList.size() > 0){
							for (Integer stuId : studentList) {
								//studentTotalMark = 0;
								SemesterWiseExamResults semResults=null;
								mto = new MarksCardTO();
								if(appliedYear==null){
									String appliedYears=AdminMarksCardHelper.getInstance().getCurrentClassStudentsYearQuery(stuId);
									List list1=txn.getDataForQuery(appliedYears);
									appliedYear=list1.get(0).toString();
								}
								if(programType.equalsIgnoreCase("PG")|| programType.equalsIgnoreCase("M Phil")){
									mto = null;
									query=DownloadHallTicketHelper.getInstance().getQueryForPGStudentMarksCard(examId,classId,stuId,schemeNo,academicYear);
									List<Object[]> pgMarksCardData=transaction1.getStudentHallTicket(query);
									if(pgMarksCardData.size()>0)
										mto= DownloadHallTicketHelper.getInstance().getMarksCardForPg(pgMarksCardData,stuId,photoMap,request,new HashMap<Integer,String>(),0);
									if(mto!=null){
										semResults = new SemesterWiseExamResults();
										Classes classes = new Classes();
										classes.setId(classId);
										semResults.setClasses(classes);

										ExamDefinitionBO examDefinitionBO = new ExamDefinitionBO();
										examDefinitionBO.setId(examId);
										semResults.setExamDef(examDefinitionBO);

										Student studentUtilBO = new Student();
										studentUtilBO.setId(stuId);
										semResults.setStudent(studentUtilBO);
										semResults.setAcademicYear(academicYear);
										semResults.setSemNo(String.valueOf(schemeNo));
										if(mto.getResultForSgpa()!=null){
											if(!mto.getResultForSgpa().trim().equalsIgnoreCase("") && !mto.getResultForSgpa().trim().equalsIgnoreCase("-"))
												semResults.setResult(mto.getResultForSgpa());
										}
										if(mto.getGradeForSgpa()!=null){
											if(!mto.getGradeForSgpa().trim().equalsIgnoreCase("") )
												semResults.setOverallGrade(mto.getGradeForSgpa());
										}
										if(mto.getSemesterSgpa()!=null){
											if(!mto.getSemesterSgpa().trim().equalsIgnoreCase("-")) 
												semResults.setSgpa(Double.parseDouble(mto.getSemesterSgpa()));
										}
										else
											semResults.setSgpa(0.0);
										if(mto.getTotalGradePointsForSgpa()!=null)
											semResults.setTotalGradePoints(Double.parseDouble(mto.getTotalGradePointsForSgpa()));
										if(mto.getTotalCreditsForSgpa()!=null)
											semResults.setTotalCretits(Double.parseDouble(mto.getTotalCreditsForSgpa()));

										if(mto.getTotalMarksAwardedForSgpa()!=null)
											semResults.setTotalMarksAwarded(Integer.parseInt(mto.getTotalMarksAwardedForSgpa()));
										if(mto.getTotalMaxMarksForSgpa()!=null)
											semResults.setTotalMaxMarks(Integer.parseInt(mto.getTotalMaxMarksForSgpa()));
										semResults.setModifiedBy(userId);
										semResults.setLastModifiedDate(new Date());
										semResults.setCreatedBy(userId);
										semResults.setCreatedDate(new Date());

										studentSGPAListForSemesterResultslist.add(semResults);
										mto=null;
									}
									// for supplimentary marks card

									if(boMap!=null && boMap.size()>0){
										if(sgpaMap==null){
											if(appliedYear.equalsIgnoreCase("2014"))
												sgpaMap = calculatesgpaForPG2014(boMap,schemeNo,examId,classId,courseId);
											else
												sgpaMap = calculateSgpaForPGCourse2015(boMap,schemeNo,examId,classId,courseId);
										}
									}
									examStudentSgpa = null ;
									if(sgpaMap!=null){
										if( sgpaMap.containsKey(stuId))
											examStudentSgpa = sgpaMap.get(stuId);
										if(examStudentSgpa!=null){
											String appliedYears=AdminMarksCardHelper.getInstance().getCurrentClassStudentsYearQuery(stuId);
											List list1=txn.getDataForQuery(appliedYears);
											appliedYear=list1.get(0).toString();
											examStudentSgpa.setAppliedYear(Integer.parseInt(appliedYear));
											examStudentSgpa.setCreatedBy(userId);
											examStudentSgpa.setCreatedDate(new Date());
										}
									}

									if(examStudentSgpa!=null )
										studentSGPAList.add(examStudentSgpa);
								}
								else{
									mto = null;
									examStudentSgpa = null;
									String marksCardQuery=DownloadHallTicketHelper.getInstance().getQueryForUGStudentMarksCard(examId,classId,schemeNo,stuId,academicYear);
									List<Object[]> ugMarksCardData=transaction1.getStudentHallTicket(marksCardQuery);
									if(ugMarksCardData.size()>0)
										//mto = getMarksCardForUg(ugMarksCardData,schemeNo,stuId,photoMap,request,new HashMap<Integer, String>());
									if(mto!=null){
										semResults = new SemesterWiseExamResults();
										Classes classes = new Classes();
										classes.setId(classId);
										semResults.setClasses(classes);

										ExamDefinitionBO examDefinitionBO = new ExamDefinitionBO();
										examDefinitionBO.setId(examId);
										semResults.setExamDef(examDefinitionBO);
 
										Student studentUtilBO = new Student();
										studentUtilBO.setId(stuId);
										semResults.setStudent(studentUtilBO);
										semResults.setAcademicYear(academicYear);
										semResults.setSemNo(String.valueOf(schemeNo));
										if(mto.getResultClass()!=null && !mto.getResultClass().trim().equalsIgnoreCase("") && !mto.getResultClass().trim().equalsIgnoreCase("-")
										)
											semResults.setResult(mto.getResultClass());

										if(mto.getTotalGrade()!=null){
											if(!mto.getTotalGrade().trim().equalsIgnoreCase("") )
												semResults.setOverallGrade(mto.getTotalGrade());
										}
										if(mto.getSgpa()!=null && !mto.getSgpa().trim().equalsIgnoreCase(""))
											semResults.setSgpa(Double.parseDouble(mto.getSgpa()));
										else
											semResults.setSgpa(0.0);
										if(mto.getTotalCredits()!=null && !mto.getTotalCredits().trim().equalsIgnoreCase(""))
											semResults.setTotalCretits(Double.parseDouble(mto.getTotalCredits()));

										if(mto.getTotalGradePoints()!=null && !mto.getTotalGradePoints().trim().equalsIgnoreCase(""))
											semResults.setTotalGradePoints(Double.parseDouble(mto.getTotalGradePoints()));
										if(mto.getTotalMarksAwarded()!=null)
											semResults.setTotalMarksAwarded(Integer.parseInt(mto.getTotalMarksAwarded()));
										if(mto.getTotalMaxmarks()!=null)
											semResults.setTotalMaxMarks(Integer.parseInt(mto.getTotalMaxmarks()));
										semResults.setModifiedBy(userId);
										semResults.setLastModifiedDate(new Date());
										semResults.setCreatedBy(userId);
										semResults.setCreatedDate(new Date());

										studentSGPAListForSemesterResultslist.add(semResults);
										mto=null;

									}

									// suppl marks data

									if(boMap!=null && boMap.size()>0){
										if(sgpaMap==null){
											sgpaMap = calculateSgpaForUGCourse(boMap,schemeNo,examId,classId,courseId,year,month);
										}
										if(sgpaMap!=null){
											if(sgpaMap.containsKey(stuId)){
												examStudentSgpa = sgpaMap.get(stuId);
												String appliedYears=AdminMarksCardHelper.getInstance().getCurrentClassStudentsYearQuery(stuId);
												List list1=txn.getDataForQuery(appliedYears);
												appliedYear=list1.get(0).toString();
												examStudentSgpa.setAppliedYear(Integer.parseInt(appliedYear));
												examStudentSgpa.setCreatedBy(userId);
												examStudentSgpa.setCreatedDate(new Date());
											}
										}

									}

									if(examStudentSgpa!=null )
										studentSGPAList.add(examStudentSgpa);

								}
							}
						}


						if(studentSGPAListForSemesterResultslist!=null && studentSGPAListForSemesterResultslist.size()>0)
							isUpdated = iUpdateStudentSGPATxn.saveSemesterWiseMarksDetails(studentSGPAListForSemesterResultslist);
						if(studentSGPAList!= null && studentSGPAList.size()>0){
							isUpdated = iUpdateStudentSGPATxn.updateSgpa(studentSGPAList);
						}
					}
				}
			}
		}
		return isUpdated;
	}
	
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
	private Map<Integer, ExamStudentSgpa> calculatesgpaForPG2014(Map<Integer,Map<String, ConsolidateMarksCard>> boMap, int schemeNo,int examId, int classId, int courseId) throws Exception{
		int  studentIdFromBoMap ;

		Map<String, ConsolidateMarksCard> conSemMap;
		MarksCardTO tos ;
		Map<Integer, MarksCardTO> consolidateMarksMap=new HashMap<Integer, MarksCardTO>();
		Map<Integer, ExamStudentSgpa> examStudentSgpaMap = new HashMap<Integer, ExamStudentSgpa>();
		ConsolidateMarksCard consolidateMarksCard;
		Iterator entries = boMap.entrySet().iterator();
		String examMonthYear = null;
		String[] examMonthYearArr = new String[2];
		Map<Integer, String> examIdMap = new HashMap<Integer,String>();
		while (entries.hasNext()) {


			tos = new MarksCardTO();
			boolean subfailed=false;
			double totalcreditpoint=0;
			int totalMarksAwarded=0;
			double theoryInternalMinMark = 0;
			double practicalInternalMinMark = 0;
			double theoryEseMinMark = 0;
			double practicalEseMinMark = 0; 
			int totalMaxMarks = 0;
			int totcredit=0;
			String grade="";
			Entry mainMap = (Entry) entries.next();
			studentIdFromBoMap = (Integer) mainMap.getKey();
			conSemMap = (Map<String, ConsolidateMarksCard>) mainMap.getValue();
			Iterator conSemMapEntry = conSemMap.entrySet().iterator();
			examIdMap = new HashMap<Integer,String>();
			while(conSemMapEntry.hasNext()){
				int gdpoint=0;
				double internalmark=0;
				double externalmark=0;
				examMonthYear = null;
				examMonthYearArr = new String[2];

				Entry semMap = (Entry) conSemMapEntry.next();
				consolidateMarksCard= (ConsolidateMarksCard) semMap.getValue();

				if(!examIdMap.containsKey(consolidateMarksCard.getStudent().getId())){
					examMonthYear = consolidateMarksCard.getExam().getId()+"_"+consolidateMarksCard.getYear()+"_"+consolidateMarksCard.getMonth();
					examIdMap.put(consolidateMarksCard.getStudent().getId(), examMonthYear);
				}
				else{
					examMonthYear = examIdMap.get(consolidateMarksCard.getStudent().getId());
					examMonthYearArr = examMonthYear.split("_");
					int maxExamId = Integer.parseInt(examMonthYearArr[0]);
					if(consolidateMarksCard.getExam().getId()> maxExamId){
						examIdMap.remove(examMonthYear);
						examMonthYear = consolidateMarksCard.getExam().getId()+"_"+consolidateMarksCard.getYear()+"_"+consolidateMarksCard.getMonth();
						examIdMap.put(consolidateMarksCard.getStudent().getId(), examMonthYear);
					}
				}

				if(consolidateMarksCard.getSubType().equalsIgnoreCase("Theory")){
					// for external
					if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("external")){

						if(consolidateMarksCard.getTheoryeseMinimumMark()!=null)
							theoryEseMinMark = consolidateMarksCard.getTheoryeseMinimumMark().doubleValue();

						if(consolidateMarksCard.getTheoryMax()!=null)
							totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getTheoryMax().intValue();

						totalMarksAwarded = (int) (totalMarksAwarded + consolidateMarksCard.getTheoryObtain());
						if(consolidateMarksCard.getStudentTheoryMark()!=null && !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase("AB") && !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase(" ")
								&& !consolidateMarksCard.getStudentTheoryMark().equalsIgnoreCase("WTH")){

							if(consolidateMarksCard.getStudentTheoryMark()!=null)
								externalmark = Double.parseDouble(consolidateMarksCard.getStudentTheoryMark());

						}
						else{
							subfailed=true;
						}

						if(externalmark<theoryEseMinMark){
							subfailed=true;
						}
						//else{
						BigDecimal ct=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
						int credit=ct.intValue();
						double gppoint = 0;
						if(consolidateMarksCard.getGradePoint()!=null){
							BigDecimal pnt=new BigDecimal(String.valueOf(consolidateMarksCard.getGradePoint()));
							gppoint=pnt.intValue();
						}
						double cp=CommonUtil.Round(gppoint,2)*credit;
						totalcreditpoint=totalcreditpoint+cp;
						totcredit=totcredit+credit;
						//}
					}
					// for internal
					else if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("internal")){

						if(consolidateMarksCard.getFinalTheoryInternalMinimumMark()!=null)
							theoryInternalMinMark = consolidateMarksCard.getFinalTheoryInternalMinimumMark().doubleValue();

						if(consolidateMarksCard.getTheoryMax()!=null)
							totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getTheoryMax().intValue();

						totalMarksAwarded = (int) (totalMarksAwarded + consolidateMarksCard.getTheoryObtain());

						if(consolidateMarksCard.getTheoryTotalSubInternalMark()!=null && !consolidateMarksCard.getTheoryTotalSubInternalMark().equalsIgnoreCase("AB")
								&& !consolidateMarksCard.getTheoryTotalSubInternalMark().equalsIgnoreCase("WTH"))
							internalmark = Double.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark());
						if(internalmark<theoryInternalMinMark){
							subfailed=true;
						}
						//else{
						BigDecimal ct=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
						int credit=ct.intValue();
						double gppoint = 0;
						if(consolidateMarksCard.getGradePoint()!=null){
							BigDecimal pnt=new BigDecimal(String.valueOf(consolidateMarksCard.getGradePoint()));
							gppoint=pnt.intValue();
						}
						double cp=CommonUtil.Round(gppoint,2)*credit;
						totalcreditpoint=totalcreditpoint+cp;
						totcredit=totcredit+credit;
						//}
					}
					else{

						if(consolidateMarksCard.getFinalTheoryInternalMinimumMark()!=null)
							theoryInternalMinMark = consolidateMarksCard.getFinalTheoryInternalMinimumMark().doubleValue();

						if(consolidateMarksCard.getTheoryeseMinimumMark()!=null)
							theoryEseMinMark = consolidateMarksCard.getTheoryeseMinimumMark().doubleValue();

						if(consolidateMarksCard.getTheoryMax()!=null)
							totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getTheoryMax().intValue();

						totalMarksAwarded = (int) (totalMarksAwarded + consolidateMarksCard.getTheoryObtain());
						if(consolidateMarksCard.getStudentTheoryMark()!=null && !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase("AB") && !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase(" ")
								&& !consolidateMarksCard.getStudentTheoryMark().equalsIgnoreCase("WTH")){

							if(consolidateMarksCard.getStudentTheoryMark()!=null)
								externalmark = Double.parseDouble(consolidateMarksCard.getStudentTheoryMark());

						}
						else{
							subfailed=true;
						}
						if(consolidateMarksCard.getTheoryTotalSubInternalMark()!=null && !consolidateMarksCard.getTheoryTotalSubInternalMark().equalsIgnoreCase("AB")
								&& !consolidateMarksCard.getTheoryTotalSubInternalMark().equalsIgnoreCase("WTH"))
							internalmark = Double.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark());
						if(internalmark<theoryInternalMinMark || externalmark<theoryEseMinMark){
							subfailed=true;
						}
						//else{
						BigDecimal ct=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
						int credit=ct.intValue();
						double gppoint = 0;
						if(consolidateMarksCard.getGradePoint()!=null){
							BigDecimal pnt=new BigDecimal(String.valueOf(consolidateMarksCard.getGradePoint()));
							gppoint=pnt.intValue();
						}
						double cp=CommonUtil.Round(gppoint,2)*credit;
						totalcreditpoint=totalcreditpoint+cp;
						totcredit=totcredit+credit;
						//}

					}

				}
				if(consolidateMarksCard.getSubType().equalsIgnoreCase("Practical")){
					if(consolidateMarksCard.getPracticalMax()!=null)
						totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getPracticalMax().intValue();
					// for external
					if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("external")){

						if((consolidateMarksCard.getStudentPracticalMark()!=null && !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase("AB")) && 
								!String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase(" ")){
							externalmark = (consolidateMarksCard.getPracticalObtain())-(internalmark);
							totalMarksAwarded = (int) (totalMarksAwarded + externalmark);
							if(consolidateMarksCard.getPracticaleseMinimumMark()!=null)
								practicalEseMinMark = consolidateMarksCard.getPracticaleseMinimumMark().doubleValue();
							if(externalmark<practicalEseMinMark){	
								subfailed=true;

							}
							//else{
							BigDecimal ct=	new BigDecimal(String.valueOf(consolidateMarksCard.getPracticalCredit()));
							int credit=ct.intValue();

							double gppoint = 0;
							if(consolidateMarksCard.getGradePoint()!=null){
								BigDecimal pnt=new BigDecimal(String.valueOf(consolidateMarksCard.getGradePoint()));
								gppoint=pnt.intValue();
							}
							double cp=CommonUtil.Round(gppoint,2)*credit;

							totalcreditpoint=totalcreditpoint+cp;
							totcredit=totcredit+credit;

							//}
						}
						else{
							subfailed=true;
						}



					}
					// internal only subjects
					else if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("internal")){
						if((consolidateMarksCard.getPracticalTotalSubInternalMark()!=null && !String.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark()).equalsIgnoreCase("AB")) && !String.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark()).equalsIgnoreCase(" ")
								&& !consolidateMarksCard.getPracticalTotalSubInternalMark().equalsIgnoreCase("WTH")){
							if(consolidateMarksCard.getPracticalTotalSubInternalMark()!=null)
								internalmark = Double.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark());

							totalMarksAwarded = (int) (totalMarksAwarded + consolidateMarksCard.getPracticalObtain());
							if(consolidateMarksCard.getFinalPracticalInternalMinimumMark()!=null)
								practicalInternalMinMark = consolidateMarksCard.getFinalPracticalInternalMinimumMark().doubleValue();

							if(internalmark<practicalInternalMinMark){	
								subfailed=true;

							}
							//else{
							BigDecimal ct=	new BigDecimal(String.valueOf(consolidateMarksCard.getPracticalCredit()));
							int credit=ct.intValue();
							double gppoint = 0;
							if(consolidateMarksCard.getGradePoint()!=null){
								BigDecimal pnt=new BigDecimal(String.valueOf(consolidateMarksCard.getGradePoint()));
								gppoint=pnt.intValue();
							}
							double cp=CommonUtil.Round(gppoint,2)*credit;
							totalcreditpoint=totalcreditpoint+cp;
							totcredit=totcredit+credit;
							//}
						}
						else{
							subfailed =true;
						}}
					// for both internal marks as well as ese marks
					else{

						if((consolidateMarksCard.getStudentPracticalMark()!=null && !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase("AB")) && !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase(" ")
								&& !consolidateMarksCard.getStudentPracticalMark().equalsIgnoreCase("WTH")){
							externalmark = Double.parseDouble(consolidateMarksCard.getStudentPracticalMark());
						}
						if(consolidateMarksCard.getPracticalTotalSubInternalMark()!=null && !consolidateMarksCard.getPracticalTotalSubInternalMark().equalsIgnoreCase("AB"))
							internalmark = Double.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark());
						else
							subfailed=true;

						totalMarksAwarded = (int) (totalMarksAwarded + consolidateMarksCard.getPracticalObtain());
						if(consolidateMarksCard.getFinalPracticalInternalMinimumMark()!=null)
							practicalInternalMinMark = consolidateMarksCard.getFinalPracticalInternalMinimumMark().doubleValue();

						if(consolidateMarksCard.getPracticaleseMinimumMark()!=null)
							practicalEseMinMark = consolidateMarksCard.getPracticaleseMinimumMark().doubleValue();

						if(externalmark<practicalEseMinMark || internalmark<practicalInternalMinMark){	
							subfailed=true;

						}
						//else{
						BigDecimal ct=	new BigDecimal(String.valueOf(consolidateMarksCard.getPracticalCredit()));
						int credit=ct.intValue();
						double gppoint = 0;
						if(consolidateMarksCard.getGradePoint()!=null){
							BigDecimal pnt=new BigDecimal(String.valueOf(consolidateMarksCard.getGradePoint()));
							gppoint=pnt.intValue();
						}
						double cp=CommonUtil.Round(gppoint,2)*credit;
						totalcreditpoint=totalcreditpoint+cp;
						totcredit=totcredit+credit;
						//}
						//}
						/*else{
							subfailed =true;
						}*/
					}
				}

			}

			double sgpa=0;
			if(totalcreditpoint>0 && totcredit>0)
				sgpa=(CommonUtil.Round((totalcreditpoint/totcredit), 2));
			grade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(courseId, String.valueOf(sgpa), 0,studentIdFromBoMap);

			ExamStudentSgpa examStudentSgpa = new ExamStudentSgpa();

			examMonthYear = examIdMap.get(studentIdFromBoMap);
			examMonthYearArr = examMonthYear.split("_");
			examStudentSgpa.setYear(Integer.parseInt(examMonthYearArr[1]));
			examStudentSgpa.setMonth(Integer.parseInt(examMonthYearArr[2]));

			Student student = new Student();
			Classes classes = new Classes();
			Course course = new Course();
			course.setId(courseId);
			examStudentSgpa.setCourse(course);
			student.setId(studentIdFromBoMap);
			examStudentSgpa.setStudent(student);
			classes.setId(classId);
			examStudentSgpa.setClasses(classes);
			examStudentSgpa.setSchemeNo(schemeNo);
			if(grade!=null)
				examStudentSgpa.setGrade(grade);
			if(subfailed)
				examStudentSgpa.setResult("Failed");
			else
				examStudentSgpa.setResult("Passed");
			examStudentSgpa.setSgpa(sgpa);
			examStudentSgpa.setCredit(totcredit);
			examStudentSgpa.setCreditGradePoint(totalcreditpoint);
			examStudentSgpa.setTotalMarksAwarded(Integer.parseInt(String.valueOf(totalMarksAwarded)));
			examStudentSgpa.setTotalMaxMarks(totalMaxMarks);
			examStudentSgpaMap.put(studentIdFromBoMap, examStudentSgpa);

		}
		return examStudentSgpaMap;
	}



	private Map<Integer, ExamStudentSgpa> calculateSgpaForUGCourse(Map<Integer,Map<String, ConsolidateMarksCard>> boMap, int schemeNo,int examId, int classId, int courseId,int year, int month) throws Exception{
		int  studId ;
		Map<String, ConsolidateMarksCard> conSemMap;
		MarksCardTO tos ;
		Map<Integer, MarksCardTO> consolidateMarksMap=new HashMap<Integer, MarksCardTO>();
		Map<Integer, ExamStudentSgpa> examStudentSgpaMap = new HashMap<Integer, ExamStudentSgpa>();
		ConsolidateMarksCard consolidateMarksCard;
		Iterator entries = boMap.entrySet().iterator();
		String examMonthYear = null;
		String[] examMonthYearArr = new String[2];
		Map<Integer, String> examIdMap = new HashMap<Integer,String>();
		while (entries.hasNext()) {
			tos = new MarksCardTO();
			boolean subFailed=false;
			double totalcreditpoint=0;
			double gradePoint=0;
			int totcredit=0;
			int totalMaxMarks=0;
			int totalmark=0;
			int totalGraceMarks=0;
			double subFinMinMarks=0;
			String grade="";
			Entry mainMap = (Entry) entries.next();
			studId = (Integer) mainMap.getKey();
			System.out.println(studId);
			conSemMap = (Map<String, ConsolidateMarksCard>) mainMap.getValue();
			Iterator conSemMapEntry = conSemMap.entrySet().iterator();
			examIdMap = new HashMap<Integer,String>();
           
			while(conSemMapEntry.hasNext()){
				double internalMark=0;
				double externalMark=0;
				double internalMinMark =0;
				double eseMinMark =0;
				double subjectFinalMinMarks=0;
				double externalGraceMarks=0;
				double internalGraceMarks=0;
				Entry semMap = (Entry) conSemMapEntry.next();
				examMonthYear = null;
				examMonthYearArr = new String[2];
				consolidateMarksCard= (ConsolidateMarksCard) semMap.getValue();

				if(!examIdMap.containsKey(consolidateMarksCard.getStudent().getId())){
					examMonthYear = consolidateMarksCard.getExam().getId()+"_"+consolidateMarksCard.getYear()+"_"+consolidateMarksCard.getMonth();
					examIdMap.put(consolidateMarksCard.getStudent().getId(), examMonthYear);
				}
				else{
					examMonthYear = examIdMap.get(consolidateMarksCard.getStudent().getId());
					examMonthYearArr = examMonthYear.split("_");
					int maxExamId = Integer.parseInt(examMonthYearArr[0]);
					if(consolidateMarksCard.getExam().getId()> maxExamId){
						examIdMap.remove(examMonthYear);
						examMonthYear = consolidateMarksCard.getExam().getId()+"_"+consolidateMarksCard.getYear()+"_"+consolidateMarksCard.getMonth();
						examIdMap.put(consolidateMarksCard.getStudent().getId(), examMonthYear);
					}
				}
				
				if(consolidateMarksCard.getSubType().equalsIgnoreCase("Theory")){
					// external only subjects
					if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("external")){

						if((consolidateMarksCard.getStudentTheoryMark()!=null && !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase("AB")) && !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase(" ")
								&& consolidateMarksCard.getStudentTheoryMark()!=null){
					
							if(consolidateMarksCard.getStudentTheoryMark()!=null &&!consolidateMarksCard.getStudentTheoryMark().equalsIgnoreCase("WTH"))
								externalMark = Double.parseDouble(consolidateMarksCard.getStudentTheoryMark());
							else
								subFailed=true;
							totalmark=(int) (totalmark+externalMark);	
							
							if(consolidateMarksCard.getTheoryMarksBeforeGrace()!=null)
							totalGraceMarks = (int)(totalGraceMarks+(externalMark-Double.parseDouble(consolidateMarksCard.getTheoryMarksBeforeGrace())));

							if(consolidateMarksCard.getTheoryMax()!=null)
								totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getTheoryMax().intValue();

							if(consolidateMarksCard.getTheoryeseMinimumMark()!=null)
								eseMinMark = consolidateMarksCard.getTheoryeseMinimumMark().doubleValue();
							if(externalMark<eseMinMark){
								subFailed=true;
							}

							if(consolidateMarksCard.getGradePoint()!=null){
								gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
								BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
								int credit=pnt.intValue();

								double cp=gradePoint*credit;
								totalcreditpoint=totalcreditpoint+cp;
								totcredit=totcredit+credit;
							}

						}	
						else{
							BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}
					}
					// internal only subjects
					else if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("internal")){

						if((consolidateMarksCard.getTheoryTotalSubInternalMark()!=null && !String.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark()).equalsIgnoreCase("AB")) 
								&& !String.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark()).equalsIgnoreCase(" ")){
							if(consolidateMarksCard.getTheoryTotalSubInternalMark()!=null && !consolidateMarksCard.getTheoryTotalSubInternalMark().equalsIgnoreCase("WTH")){
								internalMark = Double.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark());}
							else
								subFailed=true;
							totalmark=(int) (totalmark+internalMark);
							// for gracing 
							if(consolidateMarksCard.getInternalMarksBeforeGrace()!=null)
								totalGraceMarks = (int)(totalGraceMarks+(internalMark-Double.parseDouble(consolidateMarksCard.getInternalMarksBeforeGrace())));
							
							if(consolidateMarksCard.getFinalTheoryInternalMinimumMark()!=null)
								internalMinMark = consolidateMarksCard.getFinalTheoryInternalMinimumMark().doubleValue();

							if(consolidateMarksCard.getTheoryMax()!=null)
								totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getTheoryMax().intValue();

							if(internalMark<internalMinMark){
								subFailed=true;
							}

							if(consolidateMarksCard.getGradePoint()!=null)
								gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
							BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
							int credit=pnt.intValue();

							double cp=gradePoint*credit;
							totalcreditpoint=totalcreditpoint+cp;
							totcredit=totcredit+credit;


						}	
						else{
							BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}
					
					}
					// both internal and external marks will be there
					else{
						if((consolidateMarksCard.getStudentTheoryMark()!=null && !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase("AB")) && !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase(" ")
								&& consolidateMarksCard.getStudentTheoryMark()!=null){
							if((consolidateMarksCard.getStudentTheoryMark()!=null && !consolidateMarksCard.getStudentTheoryMark().equalsIgnoreCase("WTH")))
								externalMark = Double.parseDouble(consolidateMarksCard.getStudentTheoryMark());
						}
						else
							subFailed=true;
						if(consolidateMarksCard.getTheoryTotalSubInternalMark()!=null && !consolidateMarksCard.getTheoryTotalSubInternalMark().equalsIgnoreCase("WTH")){
							internalMark = Double.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark());}
						else
							subFailed=true;
						totalmark=(int) (totalmark+internalMark+externalMark);
						// marks without grace
						if(consolidateMarksCard.getInternalMarksBeforeGrace()!=null)
							totalGraceMarks = (int)(totalGraceMarks+(internalMark-Double.parseDouble(consolidateMarksCard.getInternalMarksBeforeGrace())));
						if(consolidateMarksCard.getTheoryMarksBeforeGrace()!=null)
							totalGraceMarks = (int)(totalGraceMarks+(externalMark-Double.parseDouble(consolidateMarksCard.getTheoryMarksBeforeGrace())));

					
						if(consolidateMarksCard.getFinalTheoryInternalMinimumMark()!=null)
							internalMinMark = consolidateMarksCard.getFinalTheoryInternalMinimumMark().doubleValue();

						if(consolidateMarksCard.getTheoryMax()!=null)
							totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getTheoryMax().intValue();

						if(consolidateMarksCard.getTheoryeseMinimumMark()!=null)
							eseMinMark = consolidateMarksCard.getTheoryeseMinimumMark().doubleValue();
						if(consolidateMarksCard.getTheoryMin()!=null)
							subjectFinalMinMarks=consolidateMarksCard.getTheoryMin().doubleValue();

						if(internalMark<internalMinMark || externalMark<eseMinMark ||(internalMark+externalMark)<subjectFinalMinMarks){
							subFailed=true;
						}

						if(consolidateMarksCard.getGradePoint()!=null)
							gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
						BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
						int credit=pnt.intValue();

						double cp=gradePoint*credit;
						totalcreditpoint=totalcreditpoint+cp;
						totcredit=totcredit+credit;

						/*else{
							BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}*/

					}

				}
				if(consolidateMarksCard.getSubType().equalsIgnoreCase("Practical")){
					// external subjects
					if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("external")){
						if(consolidateMarksCard.getStudentPracticalMark()!=null && !consolidateMarksCard.getStudentPracticalMark().equalsIgnoreCase("AB") && !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase(" ")){

							if(consolidateMarksCard.getStudentPracticalMark()!=null && !consolidateMarksCard.getStudentPracticalMark().equalsIgnoreCase("WTH"))
								externalMark = Double.parseDouble(consolidateMarksCard.getStudentPracticalMark());
							else
								subFailed=true;
							if(consolidateMarksCard.getPracticaleseMinimumMark()!=null)
								eseMinMark = consolidateMarksCard.getPracticaleseMinimumMark().doubleValue();
							if(consolidateMarksCard.getPracticalMax()!=null)
								totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getPracticalMax().intValue();

							if(externalMark<eseMinMark){	
								subFailed=true;

							}

							totalmark=(int) (totalmark+externalMark);
				
							if(consolidateMarksCard.getGradePoint()!=null)
								gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
							BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
							int credit=pnt.intValue();

							double cp=gradePoint*credit;
							totalcreditpoint=totalcreditpoint+cp;
							totcredit=totcredit+credit;


						}
						else{
							BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}

					}
					// internal only subjects
					else if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("internal")){

						if((consolidateMarksCard.getPracticalTotalSubInternalMark()!=null && !String.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark()).equalsIgnoreCase("AB")) && !String.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark()).equalsIgnoreCase(" ")){
							if(consolidateMarksCard.getPracticalTotalSubInternalMark()!=null && !consolidateMarksCard.getPracticalTotalSubInternalMark().equalsIgnoreCase("WTH"))
								internalMark = Double.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark());
							else
								subFailed=true;

							if(consolidateMarksCard.getFinalPracticalInternalMinimumMark()!=null)
								internalMinMark = consolidateMarksCard.getFinalPracticalInternalMinimumMark().doubleValue();

							if(consolidateMarksCard.getPracticalMax()!=null)
								totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getPracticalMax().intValue();

							if(internalMark<internalMinMark){	
								subFailed=true;

							}

							totalmark=(int) (totalmark+internalMark);

							if(consolidateMarksCard.getGradePoint()!=null)
								gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
							BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
							int credit=pnt.intValue();

							double cp=gradePoint*credit;
							totalcreditpoint=totalcreditpoint+cp;
							totcredit=totcredit+credit;


						}
						else{
							BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}


					}
					else{


						if((consolidateMarksCard.getStudentPracticalMark()!=null && !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase("AB")) && !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase(" ")){
							if(consolidateMarksCard.getStudentPracticalMark()!=null && !consolidateMarksCard.getStudentPracticalMark().equalsIgnoreCase("WTH"))
								externalMark = Double.parseDouble(consolidateMarksCard.getStudentPracticalMark());
						}
						else
							subFailed=true;
						if(consolidateMarksCard.getPracticalTotalSubInternalMark()!=null && !consolidateMarksCard.getPracticalTotalSubInternalMark().equalsIgnoreCase("WTH"))
							internalMark = Double.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark());
						else
							subFailed=true;
						if(consolidateMarksCard.getFinalPracticalInternalMinimumMark()!=null)
							internalMinMark = consolidateMarksCard.getFinalPracticalInternalMinimumMark().doubleValue();

						if(consolidateMarksCard.getPracticaleseMinimumMark()!=null)
							eseMinMark = consolidateMarksCard.getPracticaleseMinimumMark().doubleValue();
						if(consolidateMarksCard.getPracticalMax()!=null)
							totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getPracticalMax().intValue();
						if(consolidateMarksCard.getPracticalMin()!=null)
							subjectFinalMinMarks=consolidateMarksCard.getPracticalMin().doubleValue();

						if(externalMark<eseMinMark || internalMark<internalMinMark || (externalMark+internalMark)<subjectFinalMinMarks){	
							subFailed=true;

						}

						totalmark=(int) (totalmark+internalMark+externalMark);
						if(consolidateMarksCard.getGradePoint()!=null)
							gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
						BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
						int credit=pnt.intValue();

						double cp=gradePoint*credit;
						totalcreditpoint=totalcreditpoint+cp;
						totcredit=totcredit+credit;

						/*else{
							BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}*/



					}

				}
				if(consolidateMarksCard.getSubType().equalsIgnoreCase("Both")){

					// practical part
					if((consolidateMarksCard.getStudentPracticalMark()!=null && !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase("AB")) && !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase(" ")){
						if(consolidateMarksCard.getStudentPracticalMark()!=null && !consolidateMarksCard.getStudentPracticalMark().equalsIgnoreCase("WTH"))
							externalMark = Double.parseDouble(consolidateMarksCard.getStudentPracticalMark());
					}
					else
						subFailed=true;
					if(consolidateMarksCard.getPracticalTotalSubInternalMark()!=null && !consolidateMarksCard.getPracticalTotalSubInternalMark().equalsIgnoreCase("WTH"))
						internalMark = Double.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark());
					else
						subFailed=true;
					if(consolidateMarksCard.getFinalPracticalInternalMinimumMark()!=null)
						internalMinMark = consolidateMarksCard.getFinalPracticalInternalMinimumMark().doubleValue();

					if(consolidateMarksCard.getPracticaleseMinimumMark()!=null)
						eseMinMark = consolidateMarksCard.getPracticaleseMinimumMark().doubleValue();
					if(consolidateMarksCard.getPracticalMax()!=null)
						totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getPracticalMax().intValue();

					if(externalMark<eseMinMark || internalMark<internalMinMark){	
						subFailed=true;

					}
					internalMark=0;
					externalMark=0;
					eseMinMark=0;
					internalMinMark=0;
					// theory part
					if(consolidateMarksCard.getTheoryTotalSubInternalMark()!=null && !consolidateMarksCard.getTheoryTotalSubInternalMark().equalsIgnoreCase("AB")){
						internalMark = Double.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark());}
					if(consolidateMarksCard.getStudentTheoryMark()!=null && !consolidateMarksCard.getStudentTheoryMark().equalsIgnoreCase("AB") && !consolidateMarksCard.getStudentTheoryMark().equalsIgnoreCase("WTH"))
						externalMark = Double.parseDouble(consolidateMarksCard.getStudentTheoryMark());
					//totalmark=(int) (totalmark+internalMark+externalMark);
					if(consolidateMarksCard.getFinalTheoryInternalMinimumMark()!=null)
						internalMinMark = consolidateMarksCard.getFinalTheoryInternalMinimumMark().doubleValue();

					if(consolidateMarksCard.getTheoryMax()!=null)
						totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getTheoryMax().intValue();

					if(consolidateMarksCard.getTheoryeseMinimumMark()!=null)
						eseMinMark = consolidateMarksCard.getTheoryeseMinimumMark().doubleValue();
					if(internalMark<internalMinMark || externalMark<eseMinMark){
						subFailed=true;
					}

					if(consolidateMarksCard.getSubjectFinalMin()!=null){
						subFinMinMarks=Double.parseDouble(consolidateMarksCard.getSubjectFinalMin());
					}

					totalmark=(int) (totalmark+consolidateMarksCard.getTheoryObtain()+consolidateMarksCard.getPracticalObtain());
					
		
					// marks without grace
					if(consolidateMarksCard.getInternalMarksBeforeGrace()!=null)
						totalGraceMarks = (int)(totalGraceMarks+(internalMark-Double.parseDouble(consolidateMarksCard.getInternalMarksBeforeGrace())));
					if(consolidateMarksCard.getTheoryMarksBeforeGrace()!=null)
						totalGraceMarks = (int)(totalGraceMarks+(externalMark-Double.parseDouble(consolidateMarksCard.getTheoryMarksBeforeGrace())));

					subjectFinalMinMarks=0;
					if(consolidateMarksCard.getSubjectFinalMin()!=null)
						subjectFinalMinMarks = Double.parseDouble(consolidateMarksCard.getSubjectFinalMin());
					if(subjectFinalMinMarks> (consolidateMarksCard.getTheoryObtain()+consolidateMarksCard.getPracticalObtain()))
						subFailed = true;

					if(consolidateMarksCard.getGradePoint()!=null)
						gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
					int credit=0;
					if(consolidateMarksCard.getPracticalCredit()>0)
						credit=consolidateMarksCard.getPracticalCredit();
					else if(consolidateMarksCard.getTheoryCredit()>0)
						credit=consolidateMarksCard.getTheoryCredit();

					double cp=gradePoint*credit;
					totalcreditpoint=totalcreditpoint+cp;
					totcredit=totcredit+credit;

					/*else{
						BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
						int credit=pnt.intValue();
						totcredit=totcredit+credit;
						subFailed = true;
					}*/





				}
				

			}
            
			double sgpa=0;
			if(totalcreditpoint>0 && totcredit>0)
			 sgpa=(CommonUtil.Round((totalcreditpoint/totcredit), 2));
			String overallGrade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(courseId, String.valueOf(sgpa), 0,studId);
			ExamStudentSgpa examStudentSgpa = new ExamStudentSgpa();
			
			examMonthYear = examIdMap.get(studId);
			examMonthYearArr = examMonthYear.split("_");
			if(examMonthYearArr.length>0){
				examStudentSgpa.setYear(Integer.parseInt(examMonthYearArr[1]));
				examStudentSgpa.setMonth(Integer.parseInt(examMonthYearArr[2]));
			}
			
			Student student = new Student();
			Classes classes = new Classes();
			Course course = new Course();
			course.setId(courseId);
			examStudentSgpa.setCourse(course);
			student.setId(studId);
			examStudentSgpa.setStudent(student);
			classes.setId(classId);

			examStudentSgpa.setClasses(classes);
			examStudentSgpa.setSchemeNo(schemeNo);
			

			if(subFailed){
				examStudentSgpa.setGrade("F");
				examStudentSgpa.setResult("Failed");
			}
			else{
				if(overallGrade!=null && !overallGrade.equalsIgnoreCase(""))
					examStudentSgpa.setGrade(overallGrade);
				examStudentSgpa.setResult("Passed");
			}
			// for gracing
			
			if(totalGraceMarks>0){
				examStudentSgpa.setIsGraced(true);
				examStudentSgpa.setTotalGraceMarks(String.valueOf(totalGraceMarks));
			}
			examStudentSgpa.setTotalMaxMarks(totalMaxMarks);
			examStudentSgpa.setTotalMarksAwarded(totalmark);
			if(sgpa>0){
				examStudentSgpa.setSgpa(sgpa);
				examStudentSgpa.setCredit(totcredit);
				examStudentSgpa.setCreditGradePoint(totalcreditpoint);

			}
			examStudentSgpaMap.put(studId, examStudentSgpa);
		}

		return examStudentSgpaMap;
	}
	
	
	private Map<Integer, ExamStudentSgpa> calculateSgpaForPGCourse2015(Map<Integer,Map<String, ConsolidateMarksCard>> boMap, int schemeNo,int examId, int classId, int courseId) throws Exception{
		int  studId ;
		Map<String, ConsolidateMarksCard> conSemMap;
		MarksCardTO tos ;
		Map<Integer, MarksCardTO> consolidateMarksMap=new HashMap<Integer, MarksCardTO>();
		Map<Integer, ExamStudentSgpa> examStudentSgpaMap = new HashMap<Integer, ExamStudentSgpa>();
		ConsolidateMarksCard consolidateMarksCard;
		Iterator entries = boMap.entrySet().iterator();
		String examMonthYear = null;
		String[] examMonthYearArr = new String[2];
		Map<Integer, String> examIdMap = new HashMap<Integer,String>();
		while (entries.hasNext()) {
			tos = new MarksCardTO();
			boolean subFailed=false;
			double totalcreditpoint=0;
			double gradePoint=0;
			int totcredit=0;
			int totalMaxMarks=0;
			int totalmark=0;
			double subFinMinMarks=0;
			int totalGraceMarks=0;
			String grade="";
			Entry mainMap = (Entry) entries.next();
			studId = (Integer) mainMap.getKey();
			conSemMap = (Map<String, ConsolidateMarksCard>) mainMap.getValue();
			Iterator conSemMapEntry = conSemMap.entrySet().iterator();
			examIdMap = new HashMap<Integer,String>();

			while(conSemMapEntry.hasNext()){
				double internalMark=0;
				double externalMark=0;
				double internalMinMark =0;
				double eseMinMark =0;
				double subjectFinalMinMarks=0;
				double externalGraceMarks=0;
				double internalGraceMarks=0;
				Entry semMap = (Entry) conSemMapEntry.next();
				examMonthYear = null;
				examMonthYearArr = new String[2];
				consolidateMarksCard= (ConsolidateMarksCard) semMap.getValue();

				if(!examIdMap.containsKey(consolidateMarksCard.getStudent().getId())){
					examMonthYear = consolidateMarksCard.getExam().getId()+"_"+consolidateMarksCard.getYear()+"_"+consolidateMarksCard.getMonth();
					examIdMap.put(consolidateMarksCard.getStudent().getId(), examMonthYear);
				}
				else{
					examMonthYear = examIdMap.get(consolidateMarksCard.getStudent().getId());
					examMonthYearArr = examMonthYear.split("_");
					int maxExamId = Integer.parseInt(examMonthYearArr[0]);
					if(consolidateMarksCard.getExam().getId()> maxExamId){
						examIdMap.remove(examMonthYear);
						examMonthYear = consolidateMarksCard.getExam().getId()+"_"+consolidateMarksCard.getYear()+"_"+consolidateMarksCard.getMonth();
						examIdMap.put(consolidateMarksCard.getStudent().getId(), examMonthYear);
					}
				}

				if(consolidateMarksCard.getSubType().equalsIgnoreCase("Theory")){
					// external only subjects
					if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("external")){

						if((consolidateMarksCard.getStudentTheoryMark()!=null && !consolidateMarksCard.getStudentTheoryMark().equalsIgnoreCase("WTH") 
								&& !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase("AB")) 
								&& !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase(" ")){

							externalMark = Double.parseDouble(consolidateMarksCard.getStudentTheoryMark());
							totalmark=(int) (totalmark+externalMark);
							// gracing marks
							if(consolidateMarksCard.getTheoryMarksBeforeGrace()!=null)
								totalGraceMarks = (int)(totalGraceMarks+(externalMark-Double.parseDouble(consolidateMarksCard.getTheoryMarksBeforeGrace())));

							if(consolidateMarksCard.getTheoryMax()!=null)
								totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getTheoryMax().intValue();

							if(consolidateMarksCard.getTheoryeseMinimumMark()!=null)
								eseMinMark = consolidateMarksCard.getTheoryeseMinimumMark().doubleValue();
							if(externalMark<eseMinMark){
								subFailed=true;
							}

							if(consolidateMarksCard.getGradePoint()!=null){
								gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
								BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
								int credit=pnt.intValue();

								double cp=gradePoint*credit;
								totalcreditpoint=totalcreditpoint+cp;
								totcredit=totcredit+credit;
							}

						}	
						else{
							BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}
					}
					// internal only subjects
					else if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("internal")){

						if((consolidateMarksCard.getTheoryTotalSubInternalMark()!=null && !consolidateMarksCard.getTheoryTotalSubInternalMark().equalsIgnoreCase("WTH") 
								&& !String.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark()).equalsIgnoreCase("AB")) 
								&& !String.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark()).equalsIgnoreCase(" ")){

							internalMark = Double.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark());
							totalmark=(int) (totalmark+internalMark);
							// for gracing 
							if(consolidateMarksCard.getInternalMarksBeforeGrace()!=null)
								totalGraceMarks = (int)(totalGraceMarks+(internalMark-Double.parseDouble(consolidateMarksCard.getInternalMarksBeforeGrace())));
							
							if(consolidateMarksCard.getFinalTheoryInternalMinimumMark()!=null)
								internalMinMark = consolidateMarksCard.getFinalTheoryInternalMinimumMark().doubleValue();

							if(consolidateMarksCard.getTheoryMax()!=null)
								totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getTheoryMax().intValue();

							if(internalMark<internalMinMark){
								subFailed=true;
							}

							if(consolidateMarksCard.getGradePoint()!=null)
								gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
							BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
							int credit=pnt.intValue();

							double cp=gradePoint*credit;
							totalcreditpoint=totalcreditpoint+cp;
							totcredit=totcredit+credit;


						}	
						else{
							BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}

					}
					// both internal and external marks will be there
					else{
						if((consolidateMarksCard.getStudentTheoryMark()!=null && !consolidateMarksCard.getStudentTheoryMark().equalsIgnoreCase("WTH")
								&& !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase("AB")) 
								&& !String.valueOf(consolidateMarksCard.getStudentTheoryMark()).equalsIgnoreCase(" ")){
							externalMark = Double.parseDouble(consolidateMarksCard.getStudentTheoryMark());
						}
						else
							subFailed=true;
						if(consolidateMarksCard.getTheoryTotalSubInternalMark()!=null && !consolidateMarksCard.getTheoryTotalSubInternalMark().equalsIgnoreCase("WTH")){
							internalMark = Double.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark());}
						else
							subFailed=true;
						totalmark=(int) (totalmark+internalMark+externalMark);
						
						// marks without grace
						if(consolidateMarksCard.getInternalMarksBeforeGrace()!=null)
							totalGraceMarks = (int)(totalGraceMarks+(internalMark-Double.parseDouble(consolidateMarksCard.getInternalMarksBeforeGrace())));
						if(consolidateMarksCard.getTheoryMarksBeforeGrace()!=null)
							totalGraceMarks = (int)(totalGraceMarks+(externalMark-Double.parseDouble(consolidateMarksCard.getTheoryMarksBeforeGrace())));

						
						if(consolidateMarksCard.getFinalTheoryInternalMinimumMark()!=null)
							internalMinMark = consolidateMarksCard.getFinalTheoryInternalMinimumMark().doubleValue();

						if(consolidateMarksCard.getTheoryMax()!=null)
							totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getTheoryMax().intValue();

						if(consolidateMarksCard.getTheoryeseMinimumMark()!=null)
							eseMinMark = consolidateMarksCard.getTheoryeseMinimumMark().doubleValue();
						if(consolidateMarksCard.getTheoryMin()!=null)
							subjectFinalMinMarks=consolidateMarksCard.getTheoryMin().doubleValue();

						if(internalMark<internalMinMark || externalMark<eseMinMark ||(internalMark+externalMark)<subjectFinalMinMarks){
							subFailed=true;
						}

						if(consolidateMarksCard.getGradePoint()!=null)
							gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
						BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
						int credit=pnt.intValue();

						double cp=gradePoint*credit;
						totalcreditpoint=totalcreditpoint+cp;
						totcredit=totcredit+credit;

						/*else{
							BigDecimal pnt=	new BigDecimal(String.valueOf(consolidateMarksCard.getTheoryCredit()));
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}*/

					}

				}
				if(consolidateMarksCard.getSubType().equalsIgnoreCase("Practical")){
					// external subjects
					if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("external")){
						if(consolidateMarksCard.getStudentPracticalMark()!=null && !consolidateMarksCard.getStudentPracticalMark().equalsIgnoreCase("WTH")
								&& !consolidateMarksCard.getStudentPracticalMark().equalsIgnoreCase("AB") 
								&& !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase(" ")){

							externalMark = Double.parseDouble(consolidateMarksCard.getStudentPracticalMark());

							if(consolidateMarksCard.getPracticaleseMinimumMark()!=null)
								eseMinMark = consolidateMarksCard.getPracticaleseMinimumMark().doubleValue();
							if(consolidateMarksCard.getPracticalMax()!=null)
								totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getPracticalMax().intValue();

							if(externalMark<eseMinMark){	
								subFailed=true;

							}

							totalmark=(int) (totalmark+externalMark);

							if(consolidateMarksCard.getGradePoint()!=null)
								gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
							BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
							int credit=pnt.intValue();

							double cp=gradePoint*credit;
							totalcreditpoint=totalcreditpoint+cp;
							totcredit=totcredit+credit;


						}
						else{
							BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}

					}
					// internal only subjects
					else if(consolidateMarksCard.getIsExternalOrInternalSubject()!=null && consolidateMarksCard.getIsExternalOrInternalSubject().equalsIgnoreCase("internal")){

						if((consolidateMarksCard.getPracticalTotalSubInternalMark()!=null && !consolidateMarksCard.getPracticalTotalSubInternalMark().equalsIgnoreCase("WTH")
								&& !String.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark()).equalsIgnoreCase("AB")) 
								&& !String.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark()).equalsIgnoreCase(" ")){

							internalMark = Double.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark());
							if(consolidateMarksCard.getFinalPracticalInternalMinimumMark()!=null)
								internalMinMark = consolidateMarksCard.getFinalPracticalInternalMinimumMark().doubleValue();

							if(consolidateMarksCard.getPracticalMax()!=null)
								totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getPracticalMax().intValue();

							if(internalMark<internalMinMark){	
								subFailed=true;

							}

							totalmark=(int) (totalmark+internalMark);

							if(consolidateMarksCard.getGradePoint()!=null)
								gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
							BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
							int credit=pnt.intValue();

							double cp=gradePoint*credit;
							totalcreditpoint=totalcreditpoint+cp;
							totcredit=totcredit+credit;


						}
						else{
							BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}


					}
					else{


						if((consolidateMarksCard.getStudentPracticalMark()!=null && !consolidateMarksCard.getStudentPracticalMark().equalsIgnoreCase("WTH")
								&& !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase("AB")) 
								&& !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase(" ")){
							if(consolidateMarksCard.getStudentPracticalMark()!=null)
								externalMark = Double.parseDouble(consolidateMarksCard.getStudentPracticalMark());
						}
						else subFailed=true;
						if(consolidateMarksCard.getPracticalTotalSubInternalMark()!=null && !consolidateMarksCard.getPracticalTotalSubInternalMark().equalsIgnoreCase("WTH"))
							internalMark = Double.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark());
						else subFailed=true;
						if(consolidateMarksCard.getFinalPracticalInternalMinimumMark()!=null)
							internalMinMark = consolidateMarksCard.getFinalPracticalInternalMinimumMark().doubleValue();

						if(consolidateMarksCard.getPracticaleseMinimumMark()!=null)
							eseMinMark = consolidateMarksCard.getPracticaleseMinimumMark().doubleValue();
						if(consolidateMarksCard.getPracticalMax()!=null)
							totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getPracticalMax().intValue();
						if(consolidateMarksCard.getPracticalMin()!=null)
							subjectFinalMinMarks=consolidateMarksCard.getPracticalMin().doubleValue();

						if(externalMark<eseMinMark || internalMark<internalMinMark || (externalMark+internalMark)<subjectFinalMinMarks){	
							subFailed=true;

						}

						totalmark=(int) (totalmark+internalMark+externalMark);

						if(consolidateMarksCard.getGradePoint()!=null)
							gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
						BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
						int credit=pnt.intValue();

						double cp=gradePoint*credit;
						totalcreditpoint=totalcreditpoint+cp;
						totcredit=totcredit+credit;

						/*else{
							BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
							int credit=pnt.intValue();
							totcredit=totcredit+credit;
							subFailed = true;
						}*/



					}

				}
				if(consolidateMarksCard.getSubType().equalsIgnoreCase("Both")){

					// practical part
					if((consolidateMarksCard.getStudentPracticalMark()!=null  && !consolidateMarksCard.getStudentPracticalMark().equalsIgnoreCase("WTH")
							&& !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase("AB"))
							&& !String.valueOf(consolidateMarksCard.getStudentPracticalMark()).equalsIgnoreCase(" ")){
						externalMark = Double.parseDouble(consolidateMarksCard.getStudentPracticalMark());
					}
					else subFailed=true;

					if(consolidateMarksCard.getPracticalTotalSubInternalMark()!=null)
						internalMark = Double.valueOf(consolidateMarksCard.getPracticalTotalSubInternalMark());
					else subFailed=true;

					if(consolidateMarksCard.getFinalPracticalInternalMinimumMark()!=null)
						internalMinMark = consolidateMarksCard.getFinalPracticalInternalMinimumMark().doubleValue();

					if(consolidateMarksCard.getPracticaleseMinimumMark()!=null)
						eseMinMark = consolidateMarksCard.getPracticaleseMinimumMark().doubleValue();
					if(consolidateMarksCard.getPracticalMax()!=null)
						totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getPracticalMax().intValue();

					if(externalMark<eseMinMark || internalMark<internalMinMark){	
						subFailed=true;

					}
					internalMark=0;
					externalMark=0;
					eseMinMark=0;
					internalMinMark=0;
					// theory part
					if(consolidateMarksCard.getTheoryTotalSubInternalMark()!=null && !consolidateMarksCard.getTheoryTotalSubInternalMark().equalsIgnoreCase("AB")){
						internalMark = Double.valueOf(consolidateMarksCard.getTheoryTotalSubInternalMark());}
					if(consolidateMarksCard.getStudentTheoryMark()!=null  && !consolidateMarksCard.getStudentTheoryMark().equalsIgnoreCase("WTH")
							&& !consolidateMarksCard.getStudentTheoryMark().equalsIgnoreCase("AB") 
							&& !consolidateMarksCard.getStudentTheoryMark().equalsIgnoreCase("WTH"))
						externalMark = Double.parseDouble(consolidateMarksCard.getStudentTheoryMark());
					else subFailed=true;
					//totalmark=(int) (totalmark+internalMark+externalMark);
					if(consolidateMarksCard.getFinalTheoryInternalMinimumMark()!=null)
						internalMinMark = consolidateMarksCard.getFinalTheoryInternalMinimumMark().doubleValue();

					if(consolidateMarksCard.getTheoryMax()!=null)
						totalMaxMarks =  totalMaxMarks + consolidateMarksCard.getTheoryMax().intValue();

					if(consolidateMarksCard.getTheoryeseMinimumMark()!=null)
						eseMinMark = consolidateMarksCard.getTheoryeseMinimumMark().doubleValue();
					if(internalMark<internalMinMark || externalMark<eseMinMark){
						subFailed=true;
					}

					if(consolidateMarksCard.getSubjectFinalMin()!=null){
						subFinMinMarks=Double.parseDouble(consolidateMarksCard.getSubjectFinalMin());
					}

					totalmark=(int) (totalmark+consolidateMarksCard.getTheoryObtain()+consolidateMarksCard.getPracticalObtain());
					
					// marks without grace
					if(consolidateMarksCard.getInternalMarksBeforeGrace()!=null)
						totalGraceMarks = (int)(totalGraceMarks+(internalMark-Double.parseDouble(consolidateMarksCard.getInternalMarksBeforeGrace())));
					if(consolidateMarksCard.getTheoryMarksBeforeGrace()!=null)
						totalGraceMarks = (int)(totalGraceMarks+(externalMark-Double.parseDouble(consolidateMarksCard.getTheoryMarksBeforeGrace())));
 
					subjectFinalMinMarks=0;
					if(consolidateMarksCard.getSubjectFinalMin()!=null)
						subjectFinalMinMarks = Double.parseDouble(consolidateMarksCard.getSubjectFinalMin());
					if(subjectFinalMinMarks> (consolidateMarksCard.getTheoryObtain()+consolidateMarksCard.getPracticalObtain()))
						subFailed = true;

					if(consolidateMarksCard.getGradePoint()!=null)
						gradePoint=Double.parseDouble(String.valueOf(consolidateMarksCard.getGradePoint()));
					int credit=0;
					if(consolidateMarksCard.getPracticalCredit()>0)
						credit=consolidateMarksCard.getPracticalCredit();
					else if(consolidateMarksCard.getTheoryCredit()>0)
						credit=consolidateMarksCard.getTheoryCredit();

					double cp=gradePoint*credit;
					totalcreditpoint=totalcreditpoint+cp;
					totcredit=totcredit+credit;

					/*else{
						BigDecimal pnt=	new BigDecimal(consolidateMarksCard.getPracticalCredit());
						int credit=pnt.intValue();
						totcredit=totcredit+credit;
						subFailed = true;
					}*/





				}


			}

			double sgpa=0;
			if(totalcreditpoint>0 && totcredit>0)
				sgpa=(CommonUtil.Round((totalcreditpoint/totcredit), 2));
			String overallGrade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(courseId, String.valueOf(sgpa), 0,studId);
			ExamStudentSgpa examStudentSgpa = new ExamStudentSgpa();

			examMonthYear = examIdMap.get(studId);
			examMonthYearArr = examMonthYear.split("_");
			if(examMonthYearArr.length>0){
				examStudentSgpa.setYear(Integer.parseInt(examMonthYearArr[1]));
				examStudentSgpa.setMonth(Integer.parseInt(examMonthYearArr[2]));
			}

			Student student = new Student();
			Classes classes = new Classes();
			Course course = new Course();
			course.setId(courseId);
			examStudentSgpa.setCourse(course);
			student.setId(studId);
			examStudentSgpa.setStudent(student);
			classes.setId(classId);

			examStudentSgpa.setClasses(classes);
			examStudentSgpa.setSchemeNo(schemeNo);


			if(subFailed){
				examStudentSgpa.setGrade("F");
				examStudentSgpa.setResult("Failed");
			}
			else{
				if(overallGrade!=null && !overallGrade.equalsIgnoreCase(""))
					examStudentSgpa.setGrade(overallGrade);
				examStudentSgpa.setResult("Passed");
			}
			// for gracing
			
			if(totalGraceMarks>0){
				examStudentSgpa.setIsGraced(true);
				examStudentSgpa.setTotalGraceMarks(String.valueOf(totalGraceMarks));
			}
			examStudentSgpa.setTotalMaxMarks(totalMaxMarks);
			examStudentSgpa.setTotalMarksAwarded(totalmark);
			if(sgpa>0){
				examStudentSgpa.setSgpa(sgpa);
				examStudentSgpa.setCredit(totcredit);
				examStudentSgpa.setCreditGradePoint(totalcreditpoint);

			}
			examStudentSgpaMap.put(studId, examStudentSgpa);
		}

		return examStudentSgpaMap;
	}



	public boolean updateCCPA(ExamStudentSGPAForm examStudentSGPAForm ,int courseId, Integer batchYear,String programType, String programId, String userId) throws Exception {
		log.debug("getClassIdsByCourseAndSchemeNo");
		IUpdateStudentSGPATxn iUpdateStudentSGPATxn = UpdateExamStudentSGPAImpl.getInstance();
		boolean isUpdated = false;
		MarksCardTO marksCardTo = null;
		double totalCreditPoints = 0;
		int totalCredits=0;
		int totalMarksAwarded = 0;
		int totalMaxMarks = 0;
		Integer count =0;
		ExamStudentSgpa examStudentSgpa = null;
		ExamStudentSgpa examStudentSgpaFromMap = null;
		ExamStudentCCPA examStudentCCPA = new ExamStudentCCPA();
		Integer stuId=0;
		ArrayList<ExamStudentCCPA> examStudentCCPAList = new ArrayList<ExamStudentCCPA>();
		ArrayList<ExamStudentSgpa> resultList = iUpdateStudentSGPATxn.getStudentSemesterCount(courseId);
		if(resultList.size()>0){
			ArrayList<ExamStudentSgpa> studentDetailsList = new ArrayList<ExamStudentSgpa>();
			Map<Integer, Integer> countMap = new HashMap<Integer, Integer>();
			Map<Integer, ExamStudentSgpa> resultMap = new HashMap<Integer, ExamStudentSgpa>();
			Map<Integer, ExamStudentCCPA> resultMap1 = new HashMap<Integer, ExamStudentCCPA>();
			Map<Integer, String> learningMap = new HashMap<Integer, String>();
			Map<Integer, String> minMarkMap = new HashMap<Integer, String>();
			ExamStudentCCPA	examStudentCCPAFromMap = null;
			Course course = new Course();
			course.setId(courseId);
			if(resultList!=null) {
				Iterator itr = resultList.iterator();
				while(itr.hasNext()){
					Object[] obj =  (Object[]) itr.next();
					count =  Integer.parseInt(obj[0].toString());
					stuId = (Integer) obj[1];;
					if(!countMap.containsKey(stuId)){
						countMap.put(stuId, count);
					}
				}
			}
			if(programType.equalsIgnoreCase("PG")|| programType.equalsIgnoreCase("M Phil")){
				examStudentSgpa = new ExamStudentSgpa();
				double ccpa =0;
				DecimalFormat twoDForm = new DecimalFormat("#.##");
				Iterator countEntries = countMap.entrySet().iterator();
				boolean isGraced=false;
				double totalGraceMarks=0;;
				while(countEntries.hasNext()){
					examStudentCCPA = new ExamStudentCCPA();
					examStudentSgpa = new ExamStudentSgpa();
					Entry countEntry = (Entry) countEntries.next();
					stuId = (Integer) countEntry.getKey();
					totalCreditPoints = 0;
					totalCredits = 0;
					totalMarksAwarded = 0;
					totalMaxMarks = 0;
					ccpa =0;
					isGraced=false;
					totalGraceMarks=0;
					int semCount=0;
					int passMonth = 0;
					int passYear = 0;
					boolean isFailed = false;
					studentDetailsList = iUpdateStudentSGPATxn.getStudentResultDetails(stuId,batchYear);
					if(studentDetailsList!=null && studentDetailsList.size()>0){
						Iterator listItr = studentDetailsList.iterator();
						while(listItr.hasNext()){
							semCount++;
							examStudentSgpa = new ExamStudentSgpa();
							examStudentCCPA = new ExamStudentCCPA();
							examStudentSgpa = (ExamStudentSgpa) listItr.next();
							if(examStudentSgpa.getCreditGradePoint()!=null)
								totalCreditPoints = totalCreditPoints+examStudentSgpa.getCreditGradePoint();
							totalCredits = totalCredits+examStudentSgpa.getCredit();
							totalMarksAwarded = totalMarksAwarded + examStudentSgpa.getTotalMarksAwarded();
							totalMaxMarks = totalMaxMarks + examStudentSgpa.getTotalMaxMarks();
							// for gracing
							if(examStudentSgpa.getTotalGraceMarks()!=null){
								totalGraceMarks= totalGraceMarks+Double.parseDouble(examStudentSgpa.getTotalGraceMarks());
							}
							if(examStudentSgpa.getIsGraced())
								isGraced=true;
							
							if(examStudentSgpa.getResult().equalsIgnoreCase("failed"))
								isFailed = true;
							if(examStudentSgpa.getYear()>0 && examStudentSgpa.getYear()>passYear){
								passYear = examStudentSgpa.getYear();
								passMonth = examStudentSgpa.getMonth();
							}
							else if(examStudentSgpa.getYear()==passYear){
								if(examStudentSgpa.getMonth()>0 && examStudentSgpa.getMonth()>=passMonth){
									passMonth = examStudentSgpa.getMonth();
								}
							}
						}
						examStudentCCPA.setTotalMarksAwarded(totalMarksAwarded);
						examStudentCCPA.setTotalMaxMarks(totalMaxMarks);						
						if(totalCreditPoints>0 && totalCredits>0){
							//ccpa = Double.parseDouble(String.valueOf(f.format(totalCreditPoints/totalCredits)));
							BigDecimal a = new BigDecimal(String.valueOf(totalCreditPoints/totalCredits));
							ccpa = Double.parseDouble(String.valueOf(a.setScale(2, BigDecimal.ROUND_HALF_UP)));
						}
						String overallGrade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(courseId, String.valueOf(ccpa), 0,stuId);
						examStudentCCPA.setCcpa(ccpa);
						examStudentCCPA.setCourse(course);
						Student student = new Student();
						student.setId(stuId);
						examStudentCCPA.setStudent(student);
						examStudentCCPA.setCredit(totalCredits);
						examStudentCCPA.setCreditGradePoint(CommonUtil.Round(totalCreditPoints,2));
						if(programType.equalsIgnoreCase("M Phil")){
							if(isFailed || semCount<2){
								examStudentCCPA.setGrade("F");
								examStudentCCPA.setResult("failed");
							}
							else{
								if(overallGrade!=null){
									examStudentCCPA.setGrade(overallGrade);
								}
								examStudentCCPA.setPassOutYear(passYear);
								examStudentCCPA.setPassOutMonth(passMonth);
								examStudentCCPA.setResult("passed");
							}
						}
						else{
							if(isFailed || semCount<4){
								examStudentCCPA.setGrade("F");
								examStudentCCPA.setResult("failed");
							}
							else{
								if(overallGrade!=null){
									examStudentCCPA.setGrade(overallGrade);
								}
								examStudentCCPA.setPassOutYear(passYear);
								examStudentCCPA.setPassOutMonth(passMonth);
								examStudentCCPA.setResult("passed");
							}
							
						}
						examStudentCCPA.setTotalGraceMarks(String.valueOf(totalGraceMarks));
						examStudentCCPA.setIsGraced(isGraced);
						
						examStudentCCPA.setAppliedYear(batchYear);
						examStudentCCPA.setModifiedBy(userId);
						examStudentCCPA.setLastModifiedDate(new Date());
						examStudentCCPA.setCreatedBy(userId);
						examStudentCCPA.setCreatedDate(new Date());
						if(examStudentCCPA!=null)
							examStudentCCPAList.add(examStudentCCPA);
						//bala
						if(!examStudentSGPAForm.getYear().equalsIgnoreCase("2014") && !examStudentSGPAForm.getYear().equalsIgnoreCase("2015") ){
							learningMap=iUpdateStudentSGPATxn.getExtraLearning(stuId,courseId);
							if(learningMap!=null && !learningMap.isEmpty()){
								minMarkMap=iUpdateStudentSGPATxn.getminMarks(courseId,examStudentSGPAForm.getYear());
								for(int key:learningMap.keySet()){
									if(minMarkMap.containsKey(key)){
										
										String markSecured=learningMap.get(key);
										String markMin=minMarkMap.get(key);
										if(Integer.parseInt(markSecured) < Integer.parseInt(markMin)){
											examStudentCCPA.setIsExraCreditFailed(true);									
										}
									}
								}
							}
						}
					}
				}
				
			}else{
				examStudentSgpa = new ExamStudentSgpa();
				double ccpa =0;
				double totalGraceMarks=0;;
				DecimalFormat twoDForm = new DecimalFormat("#.##");
				Iterator countEntries = countMap.entrySet().iterator();
				while(countEntries.hasNext()){
					examStudentCCPA = new ExamStudentCCPA();
					examStudentSgpa = new ExamStudentSgpa();
					Entry countEntry = (Entry) countEntries.next();
					stuId = (Integer) countEntry.getKey();
					//if(stuId==3013){
					totalCreditPoints = 0;
					totalCredits = 0;
					totalMaxMarks = 0;
					totalMarksAwarded = 0;
					totalGraceMarks=0;
					ccpa =0;
					int semCount=0;
					boolean isFailed = false;
					boolean isGraced=false;
					int passMonth = 0;
					int passYear = 0;
					studentDetailsList = iUpdateStudentSGPATxn.getStudentResultDetails(stuId,batchYear);
					if(studentDetailsList!=null&& studentDetailsList.size()>0){
						Iterator listItr = studentDetailsList.iterator();
						while(listItr.hasNext()){
							semCount++;
							examStudentSgpa = new ExamStudentSgpa();
							examStudentCCPA = new ExamStudentCCPA();
							examStudentSgpa = (ExamStudentSgpa) listItr.next();
							totalMaxMarks = totalMaxMarks + examStudentSgpa.getTotalMaxMarks();
							totalMarksAwarded = totalMarksAwarded + examStudentSgpa.getTotalMarksAwarded();
							if(examStudentSgpa.getCreditGradePoint()!=null)
								totalCreditPoints = totalCreditPoints+examStudentSgpa.getCreditGradePoint();
							totalCredits = totalCredits+examStudentSgpa.getCredit();
							// for gracing
							if(examStudentSgpa.getTotalGraceMarks()!=null){
								totalGraceMarks= totalGraceMarks+Double.parseDouble(examStudentSgpa.getTotalGraceMarks());
							}
							if(examStudentSgpa.getIsGraced())
								isGraced=true;
							
							if(examStudentSgpa.getResult().equalsIgnoreCase("failed"))
								isFailed = true;
							if(examStudentSgpa.getYear()>0 && examStudentSgpa.getYear()>passYear){
								passYear = examStudentSgpa.getYear();
								passMonth = examStudentSgpa.getMonth();
							}
							else if(examStudentSgpa.getYear()==passYear){
								if(examStudentSgpa.getMonth()>0 && examStudentSgpa.getMonth()>=passMonth){
									passMonth = examStudentSgpa.getMonth();
								}
							}

						}
						
						if(totalCreditPoints>0 && totalCredits>0){
							//ccpa = Double.parseDouble(String.valueOf(f.format(totalCreditPoints/totalCredits)));
							BigDecimal a = new BigDecimal(String.valueOf(totalCreditPoints/totalCredits));
							ccpa = Double.parseDouble(String.valueOf(a.setScale(2, BigDecimal.ROUND_HALF_UP)));
						}
						
						//if(totalCreditPoints>0 && totalCredits>0)
						//	ccpa = Double.parseDouble(String.valueOf(f.format(totalCreditPoints/totalCredits)));
						
						String overallGrade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(courseId, String.valueOf(ccpa), 0,stuId);
						examStudentCCPA.setCcpa(Double.parseDouble(twoDForm.format(ccpa)));
						examStudentCCPA.setCourse(course);
						Student student = new Student();
						student.setId(stuId);
						examStudentCCPA.setStudent(student);
						examStudentCCPA.setTotalMaxMarks(totalMaxMarks);
						examStudentCCPA.setTotalMarksAwarded(totalMarksAwarded);
						examStudentCCPA.setCredit(totalCredits);
						examStudentCCPA.setCreditGradePoint(CommonUtil.Round(totalCreditPoints,2));
						if(isFailed || semCount<6){
							examStudentCCPA.setGrade("F");
							examStudentCCPA.setResult("failed");
							examStudentCCPA.setIsFailed(true);
						}
						else{
							if(overallGrade!=null){
								examStudentCCPA.setGrade(overallGrade);
							}
							examStudentCCPA.setPassOutYear(passYear);
							examStudentCCPA.setPassOutMonth(passMonth);
							examStudentCCPA.setResult("passed");
						}
						
						examStudentCCPA.setTotalGraceMarks(String.valueOf(totalGraceMarks));
						examStudentCCPA.setIsGraced(isGraced);
						examStudentCCPA.setAppliedYear(batchYear);
						examStudentCCPA.setModifiedBy(userId);
						examStudentCCPA.setLastModifiedDate(new Date());
						examStudentCCPA.setCreatedBy(userId);
						examStudentCCPA.setCreatedDate(new Date());
						if(examStudentCCPA!=null)
							examStudentCCPAList.add(examStudentCCPA);
						//bala
						if(!examStudentSGPAForm.getYear().equalsIgnoreCase("2014")){
							learningMap=iUpdateStudentSGPATxn.getExtraLearning(stuId,courseId);
							if(learningMap!=null && !learningMap.isEmpty()){
								minMarkMap=iUpdateStudentSGPATxn.getminMarks(courseId,examStudentSGPAForm.getYear());
								for(int key:learningMap.keySet()){
									if(minMarkMap.containsKey(key)){
										
										String markSecured=learningMap.get(key);
										String markMin=minMarkMap.get(key);
										if(Double.parseDouble(markSecured) < Double.parseDouble(markMin)){
											examStudentCCPA.setIsExraCreditFailed(true);									
										}
									}
								}
							}
						}
					}
				  //}
				}
			}
			iUpdateStudentSGPATxn.deleleAlreadyExistingCCPARecords(courseId,batchYear);
			if(examStudentCCPAList!=null && examStudentCCPAList.size()>0){
				isUpdated = iUpdateStudentSGPATxn.updateCCPA(examStudentCCPAList,false);
			}
			ArrayList<ExamStudentCCPA> ccpaRankListNew= new ArrayList<ExamStudentCCPA>();
			if(isUpdated){ 
				ArrayList<ExamStudentCCPA> ccpaRankList = iUpdateStudentSGPATxn.getStudentCCPAForRank(batchYear,courseId);
				int rank=0;
				double maxCgpa=0;
				for(int i=0;i<ccpaRankList.size();i++){
					ExamStudentCCPA examStudentCCPABo =new ExamStudentCCPA();
				
					if(i==0){
						rank=1;
						maxCgpa=ccpaRankList.get(i).getCcpa();
						examStudentCCPABo= ccpaRankList.get(0);
						examStudentCCPABo.setRank(String.valueOf(rank));
						ccpaRankListNew.add(examStudentCCPABo);
					}
					else{
						if(ccpaRankList.get(i).getCcpa()== maxCgpa){
							examStudentCCPABo= ccpaRankList.get(i);
							examStudentCCPABo.setRank(String.valueOf(rank));
							ccpaRankListNew.add(examStudentCCPABo);
						}
						else{
							rank++;
							maxCgpa = ccpaRankList.get(i).getCcpa();
							examStudentCCPABo= ccpaRankList.get(i);
							examStudentCCPABo.setRank(String.valueOf(rank));
							ccpaRankListNew.add(examStudentCCPABo);
						}
					}

				}
				// save ccpa records with rank
				
				if(ccpaRankListNew.size()>0){
					isUpdated = iUpdateStudentSGPATxn.updateCCPA(ccpaRankListNew,true);
				}
				
			}
			
		}
		return isUpdated;

	}
	
	public MarksCardTO getMarksCardForUg(List<Object[]> ugMarksCardData,int schemeNo,int sid,Map<Integer,byte[]> studentPhotos,HttpServletRequest request,Map<Integer,String> revaluationSubjects) throws Exception {

		MarksCardTO to= new MarksCardTO();
		IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
		if(ugMarksCardData!=null && !ugMarksCardData.isEmpty()){
			Iterator<Object[]> itr=ugMarksCardData.iterator();
			int cid=0;
			int examId=0;
			int studentId=0;
			int totalMarksAwarded=0;
			int totalMaxMarks=0;
			int totalCredits=0;
			double tcForCal=0;
			double gpForCal=0;
			double totalgradepoints=0;
			double totcredits=0;
			String resultClass="Pass";
			boolean isFailed=false;
			boolean isWithHeld=false;
			boolean isReappear=false;
		
			int star=0;
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				boolean dontConsiderFail=false;
				int ciaMarkAwarded = 0;
				int eseMarks = 0;
	
				if(obj[10]!=null)
					cid = Integer.parseInt(obj[10].toString());
				if(obj[52]!=null && obj[52].toString().equalsIgnoreCase("1")){
					dontConsiderFail=true;
				}

				if(obj[33]!=null && obj[20]!=null){
								if(obj[33].toString().equalsIgnoreCase("Theory")){
									double ciaMarksAwarded=0;
									if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
										ciaMarksAwarded=ciaMarksAwarded+Double.parseDouble(obj[6].toString());
										if(obj[7]!=null)
											ciaMarksAwarded= CommonUtil.Round(ciaMarksAwarded+Double.parseDouble(obj[7].toString()),2);
									}
							
									if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
										int m=(int)Math.ceil(Double.parseDouble(obj[15].toString()));
									}
								
									if(obj[26]!=null){
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
									}
									if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
											totalMarksAwarded=totalMarksAwarded+(int)Math.ceil(Double.parseDouble(obj[28].toString()));
									}
									if(obj[6]!=null && obj[53]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WithHeld") && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WithHeld")){
										if(((new BigDecimal(obj[6].toString())).intValue()>(new BigDecimal(obj[53].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[53].toString())).intValue()) &&
												((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
											if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;

													}
												}

												if(obj[43]!=null){
													BigDecimal pnt=	new BigDecimal(obj[43].toString());
													int point=pnt.intValue();
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
													}
												}
											}
											else{
												isFailed=true;
												isReappear=true;
											}
										}
										else{
											isFailed=true;
											isReappear=true;
										}
									} else if(obj[15].toString().equalsIgnoreCase("WithHeld") || obj[6].toString().equalsIgnoreCase("WithHeld")){
										isFailed=true;
										isWithHeld=true;
									}
									else{
										isFailed=true;
										isReappear=true;
									}
								}
								if(obj[33].toString().equalsIgnoreCase("Practical")){
									double ciaMarksAwarded=0;
									if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
										ciaMarksAwarded=ciaMarksAwarded+Double.parseDouble(obj[8].toString());
									}
									if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
										int  m=(int)Math.ceil(Double.parseDouble(obj[16].toString()));
									}
									
									if(obj[27]!=null){
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
									}
									if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
											totalMarksAwarded=totalMarksAwarded+(int)Math.ceil(Double.parseDouble(obj[29].toString()));
									}
									if(obj[8]!=null && obj[54]!=null && !obj[8].toString().equalsIgnoreCase("ab") && obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab")){
										if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[54].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue()) ||
												((new BigDecimal(obj[8].toString())).intValue()==(new BigDecimal(obj[54].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()==(new BigDecimal(obj[13].toString())).intValue())){
											if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;

													}
												}

												if(obj[43]!=null){
													BigDecimal pnt=	new BigDecimal(obj[43].toString());
													int point=pnt.intValue();
													if(obj[30]!=null){
														BigDecimal ct=new BigDecimal(obj[30].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
													}
												}
											}
											else{
												isReappear=true;
												isFailed=true;
											}
										}
										else{
											isReappear=true;
											isFailed=true;
										}
									} 
									else{
										isReappear=true;
										isFailed=true;
									}
								}
							}
			}
			to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
			to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			if(isFailed==true || isWithHeld ){
				to.setResultClass("WITHHELD");
			}
				if(totalgradepoints>0 && totcredits>0){
					double sgpa=(totalgradepoints/totcredits);
					to.setSgpa(String.valueOf(twoDForm.format(sgpa)));
				}
				int tcredits=(int)totcredits;
				to.setTotalCredits(String.valueOf(tcredits));
				int tcreditPoints=(int)totalgradepoints;
				to.setTotalGradePoints(String.valueOf(tcreditPoints));
				String grade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(cid, to.getSgpa(), 0,sid);
				to.setTotalGrade(grade);
				to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
				if(isWithHeld){
					to.setResultClass("WITHHELD");
				}else if(isReappear){
					to.setResultClass("REAPPEAR");
				}else{
				to.setResultClass("PASS");
				}

			String countQuery="select count(*) from EXAM_update_exclude_withheld " +
			" where student_id = " +studentId+
			" and exam_id ="+examId;
			int count=0;
			List<BigInteger> countList=transaction.getStudentHallTicket(countQuery);
			if(countList!=null && !countList.isEmpty()){
				BigInteger b=countList.get(0);
				count=b.intValue();
			}
			String aggQuery="select  aggregate_pass_prcntg from EXAM_exam_settings_course where course_id ="+cid;
			double agg=0;
			List<java.math.BigDecimal> aggList=transaction.getStudentHallTicket(aggQuery);
			if(aggList!=null && !aggList.isEmpty()){
				agg=Double.parseDouble(aggList.get(0).toString());
			}

			if(count<1){
				double r=((double)(totalMarksAwarded)/(double)(totalMaxMarks))*100;
				if(r>=agg){
					String robj = UpdateExamStudentSGPAImpl.getInstance().getResultClass(cid, r, 0,sid);
					if(robj!=null && resultClass.equals("PASS"))
						to.setResult(robj);
					else if(isWithHeld){
						to.setResult("WITHHELD");
					}else{
						to.setResult("REAPPEAR");	
					}
				}else{
					to.setResult("REAPPEAR");
				}
			}else{
				to.setResult("WITH HELD");
			}
		
		}
		return to;

	}
}
