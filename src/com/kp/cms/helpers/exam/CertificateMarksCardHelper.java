package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.ConsolidatedMarksCardProgrammePart;
import com.kp.cms.bo.exam.ExamStudentSgpa;
import com.kp.cms.forms.exam.CertificateMarksCardForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.ConsolidateMarksCardTO;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.IUpdateStudentSGPATxn;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.UpdateExamStudentSGPAImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.MarkComparator;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SubjectGroupDetailsComparator;

public class CertificateMarksCardHelper {
	
	private static Map<Integer, String> semMap = null;
	private static Map<String, String> monthMap = null;
	static IDownloadHallTicketTransaction transaction=DownloadHallTicketTransactionImpl.getInstance();
	static INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
	
	static {
		semMap = new HashMap<Integer, String>();
		semMap.put(1, "I");
		semMap.put(2, "II");
		semMap.put(3, "III");
		semMap.put(4, "IV");
		semMap.put(5, "V");
		semMap.put(6, "VI");
		semMap.put(7, "VII");
		semMap.put(8, "VIII");
		semMap.put(9, "IX");
		semMap.put(10, "X");
	}
	static {
		monthMap = new HashMap<String, String>();
		monthMap.put("0", "JANUARY");
		monthMap.put("1", "FEBRUARY");
		monthMap.put("2", "MARCH");
		monthMap.put("3", "APRIL");
		monthMap.put("4", "MAY");
		monthMap.put("5", "JUNE");
		monthMap.put("6", "JULY");
		monthMap.put("7", "AUGUST");
		monthMap.put("8", "SEPTEMBER");
		monthMap.put("9", "OCTOBER");
		monthMap.put("10", "NOVEMBER");
		monthMap.put("11", "DECEMBER");

	}
	
	/**
	 * Singleton object of CertificateMarksCardHelper
	 */
	private static volatile CertificateMarksCardHelper certificateMarksCardHelper = null;
	private static final Log log = LogFactory.getLog(CertificateMarksCardHelper.class);
	private CertificateMarksCardHelper() {
		
	}
	/**
	 * return singleton object of CertificateMarksCardHelper.
	 * @return
	 */
	public static CertificateMarksCardHelper getInstance() {
		if (certificateMarksCardHelper == null) {
			certificateMarksCardHelper = new CertificateMarksCardHelper();
		}
		return certificateMarksCardHelper;
	}
	/**
	 * @param studentId
	 * @return
	 */
	public String getStudentCertificateMarksCardQuery(int studentId) {
		String query="from ConsolidateMarksCard c where c.student.id="+studentId;
		return query;
	}
	/**
	 * @param boList
	 * @return
	 */
	public ConsolidateMarksCardTO convertBotoTo( List<ConsolidateMarksCard> boList,int sid, boolean checkAggregate) throws Exception {
		
		ConsolidateMarksCardTO to=null;
		Map<String,Map<String,Map<Integer,SubjectTO>>> mainMap=new HashMap<String, Map<String,Map<Integer,SubjectTO>>>();
		Map<String,Map<Integer,SubjectTO>> schemeMap;
		Map<Integer,SubjectTO> subjectList;
		SubjectTO subTo;
		boolean isFail=false;
		int count=0;
		double totalMaxMarks=0;
		double totalMarksAwarded=0;
		double totalCredits=0;
		double totCredits=0;
		double gradePoint=0;
		int cid=0;
		/*String certificateCourseQuery="select s.subject.id,s.isOptional,s.schemeNo from StudentCertificateCourse s where s.isCancelled=0 " +
			" and  s.student.id=" +sid;*/
		
		String resultClass="Pass";
		// Getting Grade for Fail from database
		String gradeForFail="";
		List<String> gradeList=transaction.getStudentHallTicket("SELECT EXAM_exam_settings.grade_for_fail FROM EXAM_exam_settings EXAM_exam_settings");
		if(gradeList!=null && !gradeList.isEmpty()){
			gradeForFail=gradeList.get(0);
		}
		int star=0;

		if(boList!=null && !boList.isEmpty()){
			Iterator<ConsolidateMarksCard> itr=boList.iterator();
			to=new ConsolidateMarksCardTO();
			while (itr.hasNext()) {
				ConsolidateMarksCard bo = itr .next();
				if(bo.getSection()==null)
					continue;
				if(count==0){
					to.setCourseName(bo.getStudent().getAdmAppln().getCourseBySelectedCourseId().getCertificateCourseName());
					to.setName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName()+
							(bo.getStudent().getAdmAppln().getPersonalData().getMiddleName()!=null?" "+bo.getStudent().getAdmAppln().getPersonalData().getMiddleName():"")+
								(bo.getStudent().getAdmAppln().getPersonalData().getLastName()!=null?" "+bo.getStudent().getAdmAppln().getPersonalData().getLastName():""));
					cid=bo.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId();
					String firstYear=PropertyUtil.getDataForUnique("select min(e.batch) from ExamStudentDetentionRejoinDetails e where e.student.id="+sid);
					if(firstYear==null)
						firstYear=bo.getStudent().getAdmAppln().getAppliedYear().toString();
					String endYear=null;
					if(bo.getStudent().getClassSchemewise()!=null && bo.getStudent().getClassSchemewise().getCurriculumSchemeDuration()!=null)
						endYear=String.valueOf(bo.getStudent().getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear()+1);
					if(endYear==null)
						endYear=PropertyUtil.getDataForUnique("select concat(max(c.exam.academicYear),'') from ConsolidateMarksCard c where c.student.id="+sid);
					
					to.setYearOfStudy(firstYear+"-"+endYear);
					List<String> regList=PropertyUtil.getInstance().getListOfData("select e.registerNo from StudentOldRegisterNumber e where e.isActive=1 and e.registerNo is not null and e.registerNo <> '' and e.student.id="+sid+" group by e.registerNo");
					if(regList!=null){
					if(!regList.contains(bo.getStudent().getRegisterNo()))
						regList.add(bo.getStudent().getRegisterNo());
					}else{
						regList=new ArrayList<String>();
						regList.add(bo.getStudent().getRegisterNo());
					}
					Comparator comparator = Collections.reverseOrder();
					Collections.sort(regList,comparator);
					to.setRegNos(regList);
					List<String> specList=PropertyUtil.getInstance().getListOfData("select e.examSpecializationBO.name from ExamStudentBioDataBO e where e.examSpecializationBO.isActive=1 and e.studentId="+sid);
					if(specList!=null && !specList.isEmpty()){
						String sp="";
						for (Iterator iterator = specList.iterator(); iterator
								.hasNext();) {
							String spec = (String) iterator.next();
							if(sp.isEmpty())
								sp=spec;
							else
								sp=sp+","+spec;
							
						}
						to.setCourseName(to.getCourseName()+" with Specialization in "+sp);
					}
					to.setRegisterNo(bo.getStudent().getRegisterNo());
				}
				count++;
				boolean isAdd=true;
				boolean isTheoryStar=false;
				boolean isPracticalStar=false;
				boolean showMaxMarks=true;
				boolean displaySubject=true;
				boolean certificateCourse=bo.getIsCertificateCourse();
				boolean dontConsiderFail=false;
				boolean showOnlyGrade=false;
				boolean addCredits=true;
				
				if(bo.getDontConsiderFailureTotalResult()!=null && bo.getDontConsiderFailureTotalResult()){
					dontConsiderFail=true;
				}
				
				// Calculating Grade For a Subject according to type
				String gradeForSubject="";
				if(bo.getCourse().getId()==18 && bo.getGrade()!=null && bo.getGrade().equalsIgnoreCase("F"))
					gradeForSubject="E";
				else
					gradeForSubject=bo.getGrade();
				
				if(bo.getPassOrFail()!=null && bo.getPassOrFail().equalsIgnoreCase("Fail")){
					if(!dontConsiderFail)
						resultClass="Fail";
					if(certificateCourse)
						displaySubject=false;
				}
				if(bo.getIsTheoryAppeared()!=null && bo.getIsTheoryAppeared()){
					star=star+1;
					isTheoryStar=true;
				}
				if(bo.getIsPracticalAppeared()!=null && bo.getIsPracticalAppeared()){
					star=star+1;
					isPracticalStar=true;
				}
				
				if((bo.getDontAddInTotal()!=null && bo.getDontAddInTotal()) || (bo.getSection().equalsIgnoreCase("Add On Course")) || (bo.getSection().equalsIgnoreCase("  Part-1"))){
					isAdd=false;
				}
				if(bo.getDontShowMaxMarks()!=null && bo.getDontShowMaxMarks()){
					showMaxMarks=false;
				}
				if(bo.getShowOnlyGrade()!=null && bo.getShowOnlyGrade())
					showOnlyGrade=bo.getShowOnlyGrade();
				
				
				if(mainMap.containsKey(bo.getName()+"_"+bo.getTermNumber()))
					schemeMap=mainMap.get(bo.getName()+"_"+bo.getTermNumber());
				else
					schemeMap=new HashMap<String, Map<Integer,SubjectTO>>();
				
				if(schemeMap.containsKey(bo.getSection()))
					subjectList=schemeMap.get(bo.getSection());
				else
					subjectList=new HashMap<Integer, SubjectTO>();
				
				if(subjectList.containsKey(bo.getSubject().getId()))
					subTo=subjectList.get(bo.getSubject().getId());
				else
					subTo=new SubjectTO();
				subTo.setTheoryPractical(bo.getSubject().getIsTheoryPractical());
				subTo.setName(bo.getSubjectName());
				subTo.setCode(bo.getSubjectCode());
				subTo.setSubOrder(bo.getSubjectOrder());
//				if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B")){
//					subTo.setTheory(true);
//					subTo.setPractical(true);
//				}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("T")){
//					subTo.setTheory(true);
//					subTo.setPractical(false);
//				}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B")){
//					subTo.setTheory(false);
//					subTo.setPractical(true);
//				}
				if(bo.getGrade().equalsIgnoreCase("F") || bo.getGrade().equalsIgnoreCase("H") || bo.getGrade().equalsIgnoreCase("E"))
					addCredits = false;
				
				if(bo.getSubType().equalsIgnoreCase("Theory")){
					if(showMaxMarks && !showOnlyGrade && bo.getTheoryMax()!=null)
						subTo.setTotalMaxMarks(String.valueOf((bo.getTheoryMax().intValue())));
					if(!showOnlyGrade){
						subTo.setTotalMarksAwarded(String.valueOf((int)bo.getTheoryObtain()));
					}
					
					if(bo.getTheoryMax()!=null && CommonUtil.isValidDecimal(bo.getTheoryMax().toString()) && isAdd)
						totalMaxMarks+=bo.getTheoryMax().doubleValue();
					if(isAdd)
						totalMarksAwarded+=bo.getTheoryObtain();
					if(bo.getTheoryCredit()>0)
						subTo.setCredits(String.valueOf(Math.round(bo.getTheoryCredit())));
					else
						subTo.setCredits("-");
					if(displaySubject){
						totalCredits+=bo.getTheoryCredit();
						if(addCredits)
							totCredits+=bo.getTheoryCredit();
					}
					if(bo.getGradePoint()!=null)
						if(displaySubject)
						gradePoint+=(bo.getTheoryCredit()*bo.getGradePoint().doubleValue());
					subTo.setGrade(gradeForSubject);
					subTo.setAppearedTheory(isTheoryStar);
					subTo.setTheory(true);
				}else{
					if(showMaxMarks && !showOnlyGrade && bo.getPracticalMax()!=null)
						subTo.setPracticalTotalMaxMarks(String.valueOf(bo.getPracticalMax().intValue()));
					if(!showOnlyGrade)
						subTo.setPracticalTotalMarksAwarded(String.valueOf((int)bo.getPracticalObtain()));
					
					if(bo.getPracticalMax()!=null && CommonUtil.isValidDecimal(bo.getPracticalMax().toString()) && isAdd)
						totalMaxMarks+=bo.getPracticalMax().doubleValue();
					if(isAdd)
						totalMarksAwarded+=bo.getPracticalObtain();
					if(bo.getPracticalCredit()>0)
					subTo.setPracticalCredits(String.valueOf(Math.round(bo.getPracticalCredit())));
					else
						subTo.setCredits("-");
					if(displaySubject)
						totalCredits+=bo.getPracticalCredit();
						if(addCredits)
							totCredits+=bo.getPracticalCredit();
					if(bo.getGradePoint()!=null)
						if(displaySubject)
						gradePoint+=(bo.getPracticalCredit()*bo.getGradePoint().doubleValue());
					subTo.setPracticalGrade(gradeForSubject);
					subTo.setAppearedPractical(isPracticalStar);
					subTo.setPractical(true);
				}
				
				if(bo.getPassOrFail().equalsIgnoreCase("fail") && !dontConsiderFail)
					isFail=true;
				
				if(displaySubject){
					// we have to remove the old data if subject to be display
					mainMap.remove(bo.getName()+"_"+bo.getTermNumber());
					schemeMap.remove(bo.getSection());
					subjectList.remove(bo.getSubject().getId());
					
					
					subjectList.put(bo.getSubject().getId(), subTo);
					schemeMap.put(bo.getSection(),subjectList);
					mainMap.put(bo.getName()+"_"+bo.getTermNumber(), schemeMap);
				}
			}
		}
//		to.setMainMap(mainMap);
		List<MarksCardTO> toList=new ArrayList<MarksCardTO>();
		List<SubjectTO> sub;
		for(Map.Entry<String,Map<String,Map<Integer,SubjectTO>>> entry:mainMap.entrySet()){
			String[] s=entry.getKey().split("_");
			MarksCardTO mto=new MarksCardTO();
			mto.setTermNo(Integer.parseInt(s[1]));
			mto.setSemNo(s[0]+" "+semMap.get(mto.getTermNo()));
			Map<String,List<SubjectTO>> addOnCourseMap=new HashMap<String, List<SubjectTO>>();
			Map<String,List<SubjectTO>> subMap=new HashMap<String, List<SubjectTO>>();
			for(Map.Entry<String,Map<Integer,SubjectTO>> pair:entry. getValue().entrySet()){
				sub=new ArrayList<SubjectTO>();
				for(Map.Entry<Integer,SubjectTO> map:pair.getValue().entrySet())
					sub.add(map.getValue());
				SubjectGroupDetailsComparator comparator=new SubjectGroupDetailsComparator();
				comparator.setCompare(1);
				Collections.sort(sub,comparator);
				if(pair.getKey().equalsIgnoreCase("Add On Course")){
					addOnCourseMap.put(pair.getKey(),sub);
				}else{
					
					subMap.put(pair.getKey(),sub);
				}
			}
			mto.setAddOnCoursesubMap(addOnCourseMap);
			mto.setSubMap(subMap);
			toList.add(mto);
		}
		if(to!=null){
		to.setTotalCredits(String.valueOf(Math.round(totCredits)));
		to.setTotalMarksAwarded(String.valueOf((int)totalMarksAwarded));
		to.setTotalMaxMarks(String.valueOf((int)totalMaxMarks));
		MarkComparator mc=new MarkComparator();
		mc.setCompare(1);
		Collections.sort(toList,mc);
		to.setToList(toList);
		double gradePointAvg=(gradePoint/totalCredits);
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		to.setGradePointAvg(String.valueOf(twoDForm.format(CommonUtil.Round(gradePointAvg,2))));
		// Not Required
		
		/*List<ExamStudentPassFail> failList=NewExamMarksEntryTransactionImpl.getInstance().getDataForQuery("from ExamStudentPassFail e where e.passFail='F' and e.student.id="+sid);
		if(failList!=null && !failList.isEmpty())
			isFail=true;*/
		// to check aggregate percentage class wise .
		if(checkAggregate){
			boolean result = txn.checkAggregateResultClassWise(sid);
			if(result){
				isFail=result;
			}
		}
		if(isFail)
			to.setResult("Failed");
		else{
			String aggQuery="select  aggregate_pass_prcntg from EXAM_exam_settings_course where course_id ="+cid;
			double agg=0;
			List<java.math.BigDecimal> aggList=transaction.getStudentHallTicket(aggQuery);
			if(aggList!=null && !aggList.isEmpty()){
				agg=Double.parseDouble(aggList.get(0).toString());
			}
			double r=((double)(totalMarksAwarded)/(double)(totalMaxMarks))*100;
			if(r>agg){
				String robj = UpdateExamStudentSGPAImpl.getInstance().getResultClass(cid, r, 0,sid);
				if(robj!=null && resultClass.equals("Pass"))
					to.setResult(robj);
				else
					to.setResult("Failed");
				
				/*String query="select result_class from EXAM_grade_class_definition where course_id="+cid+" and "+r+" between start_percentage and end_percentage";
				List<String> rList=transaction.getStudentHallTicket(query);
				if(rList!=null && !rList.isEmpty()){
					Iterator<String> rItr=rList.iterator();
					while (rItr.hasNext()) {
						String robj = rItr.next();
						if(robj!=null){
							if(resultClass.equalsIgnoreCase("Pass"))
								to.setResult(robj);
							else
								to.setResult("Failed");
						}
					}
				}*/
			}else{
				to.setResult("Failed");
			}
		
		}
		}
		return to;
	}
	
	public List<ConsolidateMarksCardTO> convertBotoToNew( List<ExamStudentSgpa> boList,int sid, boolean checkAggregate,LoginForm loginForm) throws Exception {
		List<ConsolidateMarksCardTO> consolidateMarksCardTOList = new ArrayList<ConsolidateMarksCardTO>();
		 //int finalCredit=0;
		 //int totalMarkAwarded=0;
		 //int totalMaxMark=0;
		if(boList!=null && !boList.isEmpty()){
			Iterator<ExamStudentSgpa> itr=boList.iterator();
			
			while (itr.hasNext()) {
				ConsolidateMarksCardTO to=new ConsolidateMarksCardTO();
				ExamStudentSgpa bo = itr .next();
				String month="";	
				String sem="";
				month=monthMap.get(String.valueOf(bo.getMonth()));
				sem=semMap.get(bo.getSchemeNo());
				to.setCourseName(bo.getStudent().getAdmAppln().getCourseBySelectedCourseId().getCertificateCourseName());
				to.setSemNo(sem);
				loginForm.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
				loginForm.setRegNo(bo.getStudent().getRegisterNo());
				to.setTotalMarksAwarded(String.valueOf(bo.getTotalMarksAwarded()));
				to.setTotalMaxMarks(String.valueOf(bo.getTotalMaxMarks()));
				to.setTotalCredits(String.valueOf(bo.getCredit()));
				if(bo.getCreditGradePoint()!=null)
				to.setCreditPoint(Double.toString(bo.getCreditGradePoint()));
				if(bo.getSgpa()!=null)
				to.setSgpa(Double.toString(bo.getSgpa()));
				//finalCredit=finalCredit+bo.getCredit();
				//totalMarkAwarded=totalMarkAwarded+bo.getTotalMarksAwarded();
				//totalMaxMark=totalMaxMark+bo.getTotalMaxMarks();
				to.setGrade(bo.getGrade());
				to.setResult(bo.getResult());
				to.setYearOfPassing(month + "_" + bo.getYear() );
				consolidateMarksCardTOList.add(to);
			}
		}
		//loginForm.setFinalCredit(String.valueOf(finalCredit));
		//loginForm.setTotalMaxMark(String.valueOf(totalMaxMark));
		//loginForm.setTotalMarkAwarded(String.valueOf(totalMarkAwarded));
		return consolidateMarksCardTOList;
	}
	
	public List<ConsolidateMarksCardTO> convertBotoToNew1( List<Object[]> boList,int sid, boolean checkAggregate,LoginForm loginForm) throws Exception {
		List<ConsolidateMarksCardTO> consolidateMarksCardTOList = new ArrayList<ConsolidateMarksCardTO>();
		if(boList!=null && !boList.isEmpty()){
			Iterator<Object[]> itr=boList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ConsolidateMarksCardTO to=new ConsolidateMarksCardTO();
				String str = "";
				if(obj[4].toString().equalsIgnoreCase("OPEN COURSE")){
					String openCourseName = txn.getOpenCourseName(Integer.parseInt(obj[0].toString()),Integer.parseInt(obj[12].toString()),Integer.parseInt(obj[13].toString()));
					if(obj[4].toString()!=null)
						str = obj[4].toString()+ " : " ;
					if(obj[5].toString()!=null)
						str =str+ openCourseName;
					to.setCourseName(str);
				}else{
					if(obj[4].toString()!=null)
						str = obj[4].toString()+ " : " ;
					if(obj[5].toString()!=null)
						str =str+ obj[5].toString();
					to.setCourseName(str);
				}
				loginForm.setStudentName(obj[10].toString());
				loginForm.setRegNo(obj[9].toString());
				to.setTotalMarksAwarded(obj[7].toString());
				to.setTotalMaxMarks(obj[6].toString());
				to.setTotalCredits(obj[11].toString());
				to.setGrade(obj[3].toString());
				to.setSgpa(obj[1].toString());
				to.setCreditPoint(obj[2].toString());
				consolidateMarksCardTOList.add(to);
			}
		}
		//int finalCredit=0;
		//double totalMarkAwarded=0.0;
		//double totalMaxMark=0.0;
		/*if(boList!=null && !boList.isEmpty()){
			Iterator<ConsolidatedMarksCardProgrammePart> itr=boList.iterator();
			while (itr.hasNext()) {
				ConsolidateMarksCardTO to=new ConsolidateMarksCardTO();
				ConsolidatedMarksCardProgrammePart bo = itr .next();
				String str = "";
				if(bo.getExamSubjectSection().getName()!=null)
					str = bo.getExamSubjectSection().getName()+ " : " ;
				if(bo.getSubjectStream()!=null)
					str =str+ bo.getSubjectStream().getStreamName();
				to.setCourseName(str);
				loginForm.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
				loginForm.setRegNo(bo.getStudent().getRegisterNo());
				to.setTotalMarksAwarded(String.valueOf(bo.getMaxMarksObtained()));
				to.setTotalMaxMarks(String.valueOf(bo.getMaxMarksAwarded()));
				to.setTotalCredits(String.valueOf(bo.getCredit()));
				//finalCredit=finalCredit+bo.getCredit();
				//totalMarkAwarded=totalMarkAwarded+bo.getMaxMarksObtained();
				//totalMaxMark=totalMaxMark+(bo.getMaxMarksAwarded()).doubleValue();
				to.setGrade(bo.getGrade());
				to.setSgpa(bo.getCcpa());
				to.setCreditPoint(bo.getCreditPoints());
				consolidateMarksCardTOList.add(to);
			}
		}*/
		//loginForm.setFinalCredit(String.valueOf(finalCredit));
		//loginForm.setTotalMaxMark(String.valueOf(totalMaxMark));
		//loginForm.setTotalMarkAwarded(String.valueOf(totalMarkAwarded));
		return consolidateMarksCardTOList;
	}
	
	/**
	 * @param certificateList
	 * @return
	 */
	private Map<Integer, Map<Integer, Boolean>> getCertificateSubjectMap( List<Object[]> certificateList) {
		Map<Integer,Map<Integer,Boolean>> map=new HashMap<Integer, Map<Integer,Boolean>>();
		Map<Integer,Boolean> innerMap;
		if(certificateList!=null && !certificateList.isEmpty()){
			Iterator<Object[]> itr=certificateList.iterator();
			while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				if(objects[0]!=null && objects[1]!=null && objects[2]!=null){
					if(map.containsKey(Integer.parseInt(objects[2].toString()))){
						innerMap=map.remove(Integer.parseInt(objects[2].toString()));
					}else
						innerMap=new HashMap<Integer, Boolean>();
					
					innerMap.put(Integer.parseInt(objects[0].toString()),(Boolean)objects[1]);
					map.put(Integer.parseInt(objects[2].toString()),innerMap);
				}
			}
		}
		return map;
	}
	/**
	 * @param certificateMarksCardForm
	 * @return
	 */
	public String getStudentIdsQueryForInput( CertificateMarksCardForm certificateMarksCardForm) throws Exception  {
		String query="select s.id from Student s where s.admAppln.isCancelled=0 and s.isAdmitted=1 and (s.isHide is null or s.isHide=0) and s.admAppln.courseBySelectedCourseId.id="+certificateMarksCardForm.getCourseId()+" and s.admAppln.appliedYear="+certificateMarksCardForm.getYear();
		if(certificateMarksCardForm.getRegFrom()!=null && !certificateMarksCardForm.getRegFrom().isEmpty()){
			query=query+" and s.registerNo>='"+certificateMarksCardForm.getRegFrom()+"' ";
		}
		if(certificateMarksCardForm.getRegTo()!=null && !certificateMarksCardForm.getRegTo().isEmpty()){
			query=query+" and s.registerNo<='"+certificateMarksCardForm.getRegTo()+"' ";
		}
		return query+" order by s.registerNo";
	}
	
	public String getStudentCertificateMarksCardQueryNew(int studentId,LoginForm loginForm) {
		String query="";
			
				query=	"from ExamStudentSgpa e where e.student.id="+studentId+" order by e.schemeNo";
			
		
		return query;
	}
	
	public String getStudentCertificateMarksCardQuerySQL(int studentId,LoginForm loginForm) {
		String sqlquery="";
			
		   sqlquery=" select cp.student_id, "+
		   " cp.ccpa, "+
		   " cp.credit_points, "+
		   " cp.grade, "+
		   " subject_type.name, "+
		   " consolidated_subject_stream.stream_name, "+
		   " cp.max_marks_awarded, "+
		   " cp.max_marks_obtained, "+
		   " cp.course_id , "+
		   " student.register_no, "+
		   " personal_data.first_name,  "+
		   " cp.credit ,"+
		   " cp.exam_subject_section_id,"+
		   " cp.subject_stream_id "+
		   " from consolidated_marks_card_programme_part_result cp  "+
		   " inner join subject_type ON cp.exam_subject_section_id = subject_type.id "+
		   " inner join student   ON cp.student_id =student.id "+
		   " inner join adm_appln ON adm_appln_id = adm_appln.id "+
		   " inner join personal_data ON adm_appln.personal_data_id = personal_data.id "+
		   " inner join consolidated_subject_stream ON cp.subject_stream_id = consolidated_subject_stream.id" +
		   " where cp.student_id= "+studentId+
		   " order by subject_type.order_type ";
		    
			
		
		return sqlquery;
	}
	
}