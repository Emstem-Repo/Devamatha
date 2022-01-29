package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamRevaluationApp;
import com.kp.cms.bo.exam.ExamRevaluationApplicationNew;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.DDStatusForm;
import com.kp.cms.forms.exam.NewSupplementaryImpApplicationForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.NewExamMarksEntryHandler;
import com.kp.cms.to.admission.SessionAttendnceToAm;
import com.kp.cms.to.admission.SessionAttendnceToPm;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationSubjectTO;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.INewSupplementaryImpApplicationTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewSupplementaryImpApplicationTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class NewSupplementaryImpApplicationHelper {
	/**
	 * Singleton object of NewSupplementaryImpApplicationHelper
	 */
	private static volatile NewSupplementaryImpApplicationHelper newSupplementaryImpApplicationHelper = null;
	private static final Log log = LogFactory.getLog(NewSupplementaryImpApplicationHelper.class);
	private NewSupplementaryImpApplicationHelper() {
		
	}
	/**
	 * return singleton object of NewSupplementaryImpApplicationHelper.
	 * @return
	 */
	public static NewSupplementaryImpApplicationHelper getInstance() {
		if (newSupplementaryImpApplicationHelper == null) {
			newSupplementaryImpApplicationHelper = new NewSupplementaryImpApplicationHelper();
		}
		return newSupplementaryImpApplicationHelper;
	}
	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentListQuery(NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm,Integer studentId) throws Exception {
		String query="select s.student,s.classes,s.schemeNo from StudentSupplementaryImprovementApplication s where " +
				" s.examDefinition.id=" +newSupplementaryImpApplicationForm.getExamId();
		if(newSupplementaryImpApplicationForm.getCourseId()!=null && !newSupplementaryImpApplicationForm.getCourseId().isEmpty()){
			query=query+" and s.classes.course.id=" +newSupplementaryImpApplicationForm.getCourseId();
		}
		if(newSupplementaryImpApplicationForm.getSchemeNo()!=null && !newSupplementaryImpApplicationForm.getSchemeNo().isEmpty()){
			query=query+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo();
		}
		if(studentId!=null && studentId>0){
			query=query+" and s.student.id="+studentId;
		}
		if (newSupplementaryImpApplicationForm .getSupplementaryImprovement().equalsIgnoreCase( "Supplementary")) {
			query=query+" and s.isSupplementary=1";
		}else{
			query=query+" and s.isImprovement=1";
		}
		return query+" group by s.student.id,s.classes.id order by s.student.registerNo";
	}
	/**
	 * @param boList
	 * @return
	 * @throws Exception
	 */
	public Map<String,ExamSupplementaryImpApplicationTO> convertBotoToList(List<Object[]> boList,NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		boolean isSemPre=false;
		if(newSupplementaryImpApplicationForm.getSchemeNo()!=null && !newSupplementaryImpApplicationForm.getSchemeNo().isEmpty()){
			isSemPre=true;
		}
		Map<String,String> detainList=new HashMap<String, String>(); 
		Map<Integer,String> detainMap=new HashMap<Integer, String>();
		if(!isSemPre){
			detainList=NewSupplementaryImpApplicationHelper.getInstance().getDetainStudents(newSupplementaryImpApplicationForm.getExamId());
		}else{
			detainMap=NewExamMarksEntryHandler.getInstance().getOldRegisterNo(newSupplementaryImpApplicationForm.getSchemeNo());
		}
		
		Map<String,ExamSupplementaryImpApplicationTO> toMap=new LinkedHashMap<String, ExamSupplementaryImpApplicationTO>();
		if(boList!=null && !boList.isEmpty()){
			Iterator<Object[]> itr=boList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if (obj[0]!=null && obj[1]!=null) {
					Student student=(Student)obj[0];
					Classes classes=(Classes)obj[1];
					ExamSupplementaryImpApplicationTO to=new ExamSupplementaryImpApplicationTO();
					to.setClassName(classes.getName());
					to.setCourseId(classes.getCourse().getId());
					to.setCourseName(classes.getCourse().getName());
					to.setClassId(classes.getId());
					to.setSection(classes.getSectionName());
					to.setStudentId(student.getId());
					StringBuffer stuName=new StringBuffer();
					if(student.getAdmAppln().getPersonalData().getFirstName()!=null)
						stuName.append(student.getAdmAppln().getPersonalData().getFirstName());
					if(student.getAdmAppln().getPersonalData().getMiddleName()!=null)
						stuName.append(student.getAdmAppln().getPersonalData().getMiddleName());
					if(student.getAdmAppln().getPersonalData().getLastName()!=null)
						stuName.append(student.getAdmAppln().getPersonalData().getLastName());
					
					
					// raghu added from mounts
					
					newSupplementaryImpApplicationForm.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					newSupplementaryImpApplicationForm.setApplicationNumber(Integer.toString(student.getAdmAppln().getApplnNo()));
				
					to.setStudentName(stuName.toString());
					to.setRegNumber(student.getRegisterNo());
					String regNo="";
					if(isSemPre){
						if(detainMap.containsKey(student.getId())){
							regNo=detainMap.get(student.getId());
						}
					}else{
						if(detainList.containsKey(student.getId()+"_"+classes.getTermNumber())){
							regNo=detainList.get(student.getId()+"_"+classes.getTermNumber());
						}
					}
					if(regNo!=null && !regNo.isEmpty()){
//						System.out.println(regNo +"      ***current RegNo:"+student.getRegisterNo());
						to.setRegNumber(regNo);
					}
					to.setRollNumber(student.getRollNo());
					to.setSemester_year_no(Integer.parseInt(obj[2].toString()));
					to.setExamId(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()));
					toMap.put(student.getId()+"_"+classes.getTermNumber(), to);
				}
			}
		}
		return toMap;
	}
	/**
	 * @param id
	 * @param termNumber
	 * @return
	 * @throws Exception
	 */
	private String getOldRegNo(int id, Integer termNumber) throws Exception {
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		return transaction.getOldRegNo(id,termNumber);
	}
	/**
	 * @return
	 */
	private Map<String,String> getDetainStudents(String examId) throws Exception {
		String detainQuery="select e.schemeNo from ExamExamCourseSchemeDetailsBO e where e.examId="+examId+" group by e.schemeNo";
		Map<String,String> map=new HashMap<String, String>();
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Integer> schemeList=transaction.getDataForQuery(detainQuery);
		if(schemeList!=null && !schemeList.isEmpty()){
			Iterator<Integer> itr=schemeList.iterator();
			while (itr.hasNext()) {
				Integer schemeNo = (Integer) itr.next();
				String query="select e1.student.id,e1.registerNo from ExamStudentDetentionRejoinDetails e1,StudentSupplementaryImprovementApplication s where e1.schemeNo= (select min(e.schemeNo) from ExamStudentDetentionRejoinDetails e " +
								"left join e.rejoinClassSchemewise classSche left join classSche.classes c where e.student.id = e1.student.id and ((c.termNumber <> "+schemeNo+") or (c.termNumber is null)) and e.schemeNo>="+schemeNo+")" +
								" and s.schemeNo=" +schemeNo+
								" and s.student.id=e1.student.id and s.examDefinition.id="+examId+" group by e1.student.id order by e1.id ";
				List<Object[]> list=transaction.getDataForQuery(query);
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> litr=list.iterator();
					while (litr.hasNext()) {
						Object[] obj = (Object[]) litr.next();
						map.put(obj[0].toString()+"_"+schemeNo,obj[1].toString());
					}
				}
			}
		}
		return map;
	}
	/**
	 * @param to
	 * @return
	 * @throws Exception
	 */
	public String getQueryForEdit(ExamSupplementaryImpApplicationTO to) throws Exception {
		/*String query="from StudentSupplementaryImprovementApplication s" +
				" where s.student.id=" +to.getStudentId()+
				" and s.classes.id=" +to.getClassId()+
				" and s.schemeNo=" +to.getSemester_year_no()+
				" and s.examDefinition.id="+to.getExamId();
		*/
		
		String query="SELECT  *  FROM    EXAM_supplementary_improvement_application EXAM_supplementary_improvement_application" +
		" INNER JOIN EXAM_sub_definition_coursewise EXAM_sub_definition_coursewise " +
		" ON (EXAM_supplementary_improvement_application.subject_id =  EXAM_sub_definition_coursewise.subject_id)" +
		" where EXAM_supplementary_improvement_application.student_id="+to.getStudentId()+" and EXAM_supplementary_improvement_application.class_id="+to.getClassId()+
		" and EXAM_supplementary_improvement_application.scheme_no="+to.getSemester_year_no()+" and EXAM_supplementary_improvement_application.exam_id=" +to.getExamId()+
		" group by EXAM_supplementary_improvement_application.subject_id order by EXAM_sub_definition_coursewise.subject_order";
		
		return query;
	}
	/**
	 * @param boList
	 * @param to
	 * @throws Exception
	 */
	public void convertBotoToList(List<StudentSupplementaryImprovementApplication> boList, ExamSupplementaryImpApplicationTO to) throws Exception {
		List<ExamSupplementaryImpApplicationSubjectTO> subjectList=new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
		if(boList!=null && !boList.isEmpty()){
			Iterator<StudentSupplementaryImprovementApplication> itr=boList.iterator();
			while (itr.hasNext()) {
				StudentSupplementaryImprovementApplication bo = itr .next();
				ExamSupplementaryImpApplicationSubjectTO sto=new ExamSupplementaryImpApplicationSubjectTO();
				sto.setId(bo.getId());
				sto.setSubjectId(bo.getSubject().getId());
				sto.setSubjectCode(bo.getSubject().getCode());
				sto.setSubjectName(bo.getSubject().getName());
				if(!bo.getIsFailedTheory() && !bo.getIsFailedPractical()){
					sto.setIsOverallTheoryFailed(bo.getIsTheoryOverallFailed());
					sto.setIsOverallPracticalFailed(bo.getIsPracticalOverallFailed());
				}else{
					sto.setIsOverallTheoryFailed(false);
					sto.setIsOverallPracticalFailed(false);
				}
				sto.setChance(bo.getChance());
				sto.setFees(bo.getFees());
				
				
				//raghu added from mounts
				
				if(bo.getCiaExam()!=null)
				{
					
					if(bo.getCiaExam())
					{
						
						if(bo.getIsFailedTheory()!=null && !bo.getIsFailedTheory().toString().isEmpty()){
							sto.setIsFailedTheory(bo.getIsFailedTheory());
						}
						if(bo.getIsFailedPractical()!=null && !bo.getIsFailedPractical().toString().isEmpty()){
							sto.setIsFailedPractical(bo.getIsFailedPractical());
						}
						if(bo.getIsCIAAppearedTheory()!=null && !bo.getIsCIAAppearedTheory().toString().isEmpty()){
							sto.setTempChecked(bo.getIsCIAAppearedTheory());
						}
						if(bo.getIsCIAAppearedPractical()!=null && !bo.getIsCIAAppearedPractical().toString().isEmpty()){
							sto.setTempPracticalChecked(bo.getIsCIAAppearedPractical());
						}
						boolean controlDisable = false;
						if(bo.getIsCIAFailedPractical()!=null && !bo.getIsCIAFailedPractical().toString().isEmpty()){
							if(bo.getIsCIAFailedPractical() == true){
								controlDisable = true;
							}
						}
						if(bo.getIsCIAFailedTheory()!=null && !bo.getIsCIAFailedTheory().toString().isEmpty()){
							if(bo.getIsCIAFailedTheory() == true){
								controlDisable = true;
							}
						}
						//sto.setCiaExam();
						sto.setTempCIAExamChecked(bo.getCiaExam());
						if(bo.getIsCIAFailedPractical()!=null)
						{
							sto.setIsCIAFailedPractical(bo.getIsCIAFailedPractical());
							sto.setIsFailedPractical(bo.getIsCIAFailedPractical());
						}
						else
						{
							sto.setIsCIAFailedPractical(false);
						}
						if(bo.getIsCIAFailedTheory()!=null)
						{
							sto.setIsCIAFailedTheory(bo.getIsCIAFailedTheory());
							sto.setIsFailedTheory(bo.getIsCIAFailedTheory());
						}
						else
						{
							sto.setIsCIAFailedTheory(false);
						}
						if(bo.getIsCIAAppearedPractical()!=null)
						{
							sto.setIsCIAAppearedPractical(bo.getIsCIAAppearedPractical());
						}
						else
						{
							sto.setIsCIAAppearedPractical(false);
						}
						if(bo.getIsCIAAppearedTheory()!=null)
						{
							sto.setIsCIAAppearedTheory(bo.getIsCIAAppearedTheory());
						}
						else
						{
							sto.setIsCIAAppearedTheory(false);
						}
						
						
						//raghu added newly
						sto.setIsImprovement(bo.getIsImprovement());
						sto.setIsSupplementary(bo.getIsSupplementary());
						
					}
					else
						
					{
			
				
				
				/* code modified by sudhir */
				if(bo.getIsFailedTheory()!=null && !bo.getIsFailedTheory().toString().isEmpty()){
					sto.setIsFailedTheory(bo.getIsFailedTheory());
				}
				if(bo.getIsFailedPractical()!=null && !bo.getIsFailedPractical().toString().isEmpty()){
					sto.setIsFailedPractical(bo.getIsFailedPractical());
				}
				if(bo.getIsAppearedTheory()!=null && !bo.getIsAppearedTheory().toString().isEmpty()){
					sto.setTempChecked(bo.getIsAppearedTheory());
				}
				if(bo.getIsAppearedPractical()!=null && !bo.getIsAppearedPractical().toString().isEmpty()){
					sto.setTempPracticalChecked(bo.getIsAppearedPractical());
				}
				boolean controlDisable = false;
				if(bo.getIsFailedPractical()!=null && !bo.getIsFailedPractical().toString().isEmpty()){
					if(bo.getIsFailedPractical() == true){
						controlDisable = true;
					}
				}
				if(bo.getIsFailedTheory()!=null && !bo.getIsFailedTheory().toString().isEmpty()){
					if(bo.getIsFailedTheory() == true){
						controlDisable = true;
					}
				}
					/*if ((bo.getIsFailedPractical() == true) || (bo.getIsFailedTheory() == true)) {
						controlDisable = true;
					}*/
				/* code modified by sudhir */
				sto.setControlDisable(controlDisable);
				sto.setClassId(bo.getClasses().getId());
				
				
				
				//raghu added from mounts
				
				
				sto.setCiaExam(false);
				sto.setTempCIAExamChecked(false);
				sto.setIsCIAAppearedPractical(false);
				sto.setIsCIAAppearedTheory(false);
				sto.setIsCIAFailedPractical(false);
				sto.setIsCIAFailedTheory(false);
				
				//raghu added newly
				sto.setIsImprovement(bo.getIsImprovement());
				sto.setIsSupplementary(bo.getIsSupplementary());
			}
				
			
		}
		else
		{
			if(bo.getIsFailedTheory()!=null && !bo.getIsFailedTheory().toString().isEmpty()){
				sto.setIsFailedTheory(bo.getIsFailedTheory());
			}
			if(bo.getIsFailedPractical()!=null && !bo.getIsFailedPractical().toString().isEmpty()){
				sto.setIsFailedPractical(bo.getIsFailedPractical());
			}
			if(bo.getIsAppearedTheory()!=null && !bo.getIsAppearedTheory().toString().isEmpty()){
				sto.setTempChecked(bo.getIsAppearedTheory());
			}
			if(bo.getIsAppearedPractical()!=null && !bo.getIsAppearedPractical().toString().isEmpty()){
				sto.setTempPracticalChecked(bo.getIsAppearedPractical());
			}
			boolean controlDisable = false;
			if(bo.getIsFailedPractical()!=null && !bo.getIsFailedPractical().toString().isEmpty()){
				if(bo.getIsFailedPractical() == true){
					controlDisable = true;
				}
			}
			if(bo.getIsFailedTheory()!=null && !bo.getIsFailedTheory().toString().isEmpty()){
				if(bo.getIsFailedTheory() == true){
					controlDisable = true;
				}
			}
				/*if ((bo.getIsFailedPractical() == true) || (bo.getIsFailedTheory() == true)) {
					controlDisable = true;
				}*/
			/* code modified by sudhir */
			sto.setControlDisable(controlDisable);
			sto.setClassId(bo.getClasses().getId());
			sto.setCiaExam(false);
			sto.setTempCIAExamChecked(false);
			sto.setIsCIAAppearedPractical(false);
			sto.setIsCIAAppearedTheory(false);
			sto.setIsCIAFailedPractical(false);
			sto.setIsCIAFailedTheory(false);
			
			//raghu added newly
			sto.setIsImprovement(bo.getIsImprovement());
			sto.setIsSupplementary(bo.getIsSupplementary());
			
		}
		
		
				
				
				
				subjectList.add(sto);
			}
		}
		to.setSubjectList(subjectList);
	}
	/**
	 * @param subList
	 * @param studentDetails
	 * @return
	 */
	public ExamSupplementaryImpApplicationTO convertBoListsToTo(List<Subject> subList, List<Object[]> studentDetails,NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) {
		ExamSupplementaryImpApplicationTO to =null;
		if(!studentDetails.isEmpty()){
			Iterator<Object[]> itr=studentDetails.iterator();
			if (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				Student student=(Student)objects[0];
				Classes classes=(Classes)objects[1];
				to=new ExamSupplementaryImpApplicationTO();
				to.setClassName(classes.getName());
				to.setCourseId(classes.getCourse().getId());
				to.setCourseName(classes.getCourse().getName());
				to.setClassId(classes.getId());
				to.setSection(classes.getSectionName());
				to.setStudentId(student.getId());
				StringBuffer stuName=new StringBuffer();
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getFirstName());
				if(student.getAdmAppln().getPersonalData().getMiddleName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getMiddleName());
				if(student.getAdmAppln().getPersonalData().getLastName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getLastName());
				
				to.setStudentName(stuName.toString());
				to.setRegNumber(student.getRegisterNo());
				to.setRollNumber(student.getRollNo());
				to.setSemester_year_no(classes.getTermNumber());
				to.setExamId(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()));
				List<ExamSupplementaryImpApplicationSubjectTO> subjectList=new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
				Iterator<Subject> subitr=subList.iterator();
				while (subitr.hasNext()) {
					Subject bo = subitr .next();
					ExamSupplementaryImpApplicationSubjectTO sto=new ExamSupplementaryImpApplicationSubjectTO();
					sto.setSubjectId(bo.getId());
					sto.setSubjectCode(bo.getCode());
					sto.setSubjectName(bo.getName());
					sto.setIsOverallPracticalFailed(false);
					sto.setIsOverallTheoryFailed(false);
					sto.setChance(0);
					sto.setIsFailedTheory(false);
					sto.setIsFailedPractical(false);
					sto.setTempChecked(false);
					sto.setTempPracticalChecked(false);
					sto.setControlDisable(false);
					sto.setClassId(to.getClassId());
					subjectList.add(sto);
				}
				to.setSubjectList(subjectList);
			}
		}
		return to;
	}
	/**
	 * @param bo
	 * @param list
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getPrintData(OnlinePaymentReciepts bo, List<GroupTemplate> list, HttpServletRequest request) throws Exception {
		
		String desc ="";
		if(bo!=null && list != null && !list.isEmpty()) {
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			
			desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME,bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
			desc=desc.replace(CMSConstants.TEMPLATE_REGISTER_NO,bo.getStudent().getRegisterNo());
			
			if(bo.getTransactionDate()!=null)
				desc=desc.replace(CMSConstants.TEMPLATE_SMS_DATE,CommonUtil.formatSqlDate1(bo.getTransactionDate().toString()));
			
			desc=desc.replace(CMSConstants.FEE_RECEIPTNO,bo.getRecieptNo().toString());
			desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_FOR,bo.getApplicationFor());
			
			desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_NO, Integer.toString(bo.getStudent().getAdmAppln().getApplnNo()));
			desc=desc.replace(CMSConstants.TEMPLATE_COURSE, bo.getStudent().getClassSchemewise().getClasses().getCourse().getName());
		
			
			if(bo.getTotalAmount()!=null){
				desc=desc.replace(CMSConstants.TEMPLATE_AMOUNT,String.valueOf(bo.getTotalAmount()));
				desc=desc.replace(CMSConstants.TEMPLATE_AMOUNT_IN_WORDS,CommonUtil.numberToWord1(bo.getTotalAmount().intValue()));
			}
			
			byte[] logo = null;
			byte[] logo1 = null;
			String logoPath = "";
			String logoPath1= "";
			HttpSession session = request.getSession(false);
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if (organisation != null) {
				logo = organisation.getLogo();
				logo1 = organisation.getLogo1();
			}
			if (session != null) {
				if(session.getAttribute("LogoBytes")==null)
					session.setAttribute("LogoBytes", logo);
				if(session.getAttribute("LogoBytes1")==null)
					session.setAttribute("LogoBytes1", logo1);
			}
			
			logoPath = request.getContextPath();
			logoPath = "<img src="
					+ logoPath
					+ "/LogoServlet?count=1 alt='Logo not available' width='210' height='100' >";
			
			logoPath1 = request.getContextPath();
			logoPath1 = "<img src="
					+ logoPath1
					+ "/LogoServlet?count=2 alt='Logo not available' width='210' height='100' >";
			desc = desc.replace(CMSConstants.TEMPLATE_LOGO, logoPath);
			desc = desc.replace(CMSConstants.TEMPLATE_LOGO1, logoPath1);
		}
		return desc;
	}
	
	
	
	
	//raghu added for all students improvement
	
	/**
	 * @param subList
	 * @param studentDetails
	 * @return
	 */
	public ExamSupplementaryImpApplicationTO convertBoListsToToAll(List<Subject> subList, List<Object[]> studentDetails,NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) {
		ExamSupplementaryImpApplicationTO to =null;
		if(!studentDetails.isEmpty()){
			Iterator<Object[]> itr=studentDetails.iterator();
			if (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				Student student=(Student)objects[0];
				Classes classes=(Classes)objects[1];
				to=new ExamSupplementaryImpApplicationTO();
				to.setClassName(classes.getName());
				to.setCourseId(classes.getCourse().getId());
				to.setCourseName(classes.getCourse().getName());
				to.setClassId(classes.getId());
				to.setSection(classes.getSectionName());
				to.setStudentId(student.getId());
				StringBuffer stuName=new StringBuffer();
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getFirstName());
				if(student.getAdmAppln().getPersonalData().getMiddleName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getMiddleName());
				if(student.getAdmAppln().getPersonalData().getLastName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getLastName());
				
				to.setStudentName(stuName.toString());
				to.setRegNumber(student.getRegisterNo());
				to.setRollNumber(student.getRollNo());
				to.setSemester_year_no(classes.getTermNumber());
				to.setExamId(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()));
				List<ExamSupplementaryImpApplicationSubjectTO> subjectList=new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
				Iterator<Subject> subitr=subList.iterator();
				while (subitr.hasNext()) {
					Subject bo = subitr .next();
					ExamSupplementaryImpApplicationSubjectTO sto=new ExamSupplementaryImpApplicationSubjectTO();
					sto.setSubjectId(bo.getId());
					sto.setSubjectCode(bo.getCode());
					sto.setSubjectName(bo.getName());
					sto.setIsOverallPracticalFailed(false);
					sto.setIsOverallTheoryFailed(false);
					sto.setChance(0);
					sto.setIsFailedTheory(false);
					sto.setIsFailedPractical(false);
					sto.setTempChecked(false);
					sto.setTempPracticalChecked(false);
					sto.setControlDisable(false);
					sto.setClassId(to.getClassId());
					subjectList.add(sto);
				}
				to.setSubjectList(subjectList);
			}
		}
		return to;
	}
	
	
	
	public float getAttendancePercentage(List dateList,List sessionAttendanceList,List clsdateList) throws Exception {
		
		int amsestot=0;
		int pmsestot=0;
		int amsesheld=0;
		int pmsesheld=0;
		int amattabs=0;
		int pmattabs=0;
		int amattabsheld=0;
		int pmattabsheld=0;
		
		int amattpr=0;
		int pmattpr=0;
		
		List<SessionAttendnceToAm> lam=new LinkedList<SessionAttendnceToAm>();
		List<SessionAttendnceToPm> lpm=new LinkedList<SessionAttendnceToPm>();
		
		String periodsam="";
		String leavesam="";
		String periodspm="";
		String leavespm="";
		
		Iterator itr=dateList.iterator();
		while(itr.hasNext()){
			
			
			Object[] dtobj = (Object[]) itr.next();
			String date=dtobj[0].toString();
			amsesheld=0;
			pmsesheld=0;
			amattabsheld=0;
			pmattabsheld=0;
			
			
			SessionAttendnceToAm toam=new SessionAttendnceToAm();
			toam.setAttdate(date);
			
			List<String> pnamesam=new LinkedList<String>();
			List<String> coLeavesam=new LinkedList<String>();
			
			SessionAttendnceToPm topm=new SessionAttendnceToPm();
			topm.setAttdate(date);
			
			List<String> pnamespm=new LinkedList<String>();
			List<String> coLeavespm=new LinkedList<String>();
			
			
			
			Iterator i=sessionAttendanceList.iterator();
			while(i.hasNext()){
				
			
				Object[] attobj = (Object[]) i.next();
				
				if(date.equalsIgnoreCase(attobj[0].toString())){
					
					if(attobj[4].toString().equalsIgnoreCase("am")){
						amsesheld=1;
						
						if((Boolean)attobj[5]){
							
						}else if((Boolean)attobj[6]||(Boolean)attobj[7]){
							
							if((Boolean)attobj[7]){
								String l="Co-CurricullarLeave";
								coLeavesam.add(l);
								String p=attobj[2].toString();
								pnamesam.add(p);
								
								periodsam=periodsam+p+",";
								leavesam=leavesam+"Co-CurricullarLeave,";
							}
							if((Boolean)attobj[6]){
								String l="On-Leave";
								coLeavesam.add(l);
								String p=attobj[2].toString();
								pnamesam.add(p);
								
								periodsam=periodsam+p+",";
								leavesam=leavesam+"On-Leave,";
							}
							
							
						}else{
							amattabsheld=1;
							System.out.println("-----absent am----- ");
							System.out.println(attobj[0].toString());
							
							String p=attobj[2].toString();
							pnamesam.add(p);
							periodsam=periodsam+p+",";
							leavesam=leavesam+"-,";
						}
						
						
					}else{
						pmsesheld=1;
						
							if((Boolean)attobj[5]){
								
							}else if((Boolean)attobj[6]||(Boolean)attobj[7]){
								
								if((Boolean)attobj[7]){
									String l="CoCurricullarLeave";
									coLeavespm.add(l);
									String p=attobj[2].toString();
									pnamespm.add(p);
									
									periodspm=periodspm+p+",";
									leavespm=leavespm+"Co-CurricullarLeave,";
								}
								if((Boolean)attobj[6]){
									String l="On-Leave";
									coLeavespm.add(l);
									String p=attobj[2].toString();
									pnamespm.add(p);
									
									periodspm=periodspm+p+",";
									leavespm=leavespm+"On-Leave,";
								}
							}else{
								pmattabsheld=1;
								System.out.println("-----absent pm----- ");
								System.out.println(attobj[0].toString());
								
								String p=attobj[2].toString();
								pnamespm.add(p);
								
								periodspm=periodspm+p+",";
								leavespm=leavespm+"-,";
							}
							
					}
					
					
						if(pnamesam.size()!=0)
						toam.setPnames(pnamesam);
						if(coLeavesam.size()!=0)
						toam.setCoLeaves(coLeavesam);
						if(pnamespm.size()!=0)
						topm.setPnames(pnamespm);
						if(coLeavespm.size()!=0)
						topm.setCoLeaves(coLeavespm);
						
						if(!periodsam.equalsIgnoreCase(""))
						toam.setPeriods(periodsam);
						if(!periodspm.equalsIgnoreCase(""))
						topm.setPeriods(periodspm);
						if(!leavesam.equalsIgnoreCase(""))
						toam.setLeaves(leavesam);
						if(!periodspm.equalsIgnoreCase(""))
						topm.setLeaves(leavespm);
				}
			
			}
			
			//raghu write newly if they didnot take attendance for any session we consider student was present for that session
			
			if(amsesheld!=1){
				amsesheld=1;
			}
			if(pmsesheld!=1){
				pmsesheld=1;
			}
			
			
			
			amattabs=amattabs+amattabsheld;
			pmattabs=pmattabs+pmattabsheld;
			amsestot=amsestot+amsesheld;
			pmsestot=pmsestot+pmsesheld;
			
			if((toam.getPnames()!=null && toam.getPnames().size()!=0 )|| (toam.getCoLeaves()!=null && toam.getCoLeaves().size()!=0))
				lam.add(toam);
			if((topm.getPnames()!=null && topm.getPnames().size()!=0 )|| (topm.getCoLeaves()!=null && topm.getCoLeaves().size()!=0))
				lpm.add(topm);
			
			periodsam="";
			leavesam="";
			periodspm="";
			leavespm="";
		}
		
		int size=0;
		size=clsdateList.size()-dateList.size();
		if(size>0){
			amsesheld=size;
			amsestot=amsestot+size;
			pmsesheld=size;
			pmsestot=pmsestot+size;
		}
		
		amattpr=amsestot-amattabs;
		pmattpr=pmsestot-pmattabs;
		float amper=CommonUtil.roundToDecimal((((float)amattpr/amsestot)*100),2);
		float pmper=CommonUtil.roundToDecimal((((float)pmattpr/pmsestot)*100),2);
		float totper=CommonUtil.roundToDecimal((amper+pmper)/2,2);
		float totper1=CommonUtil.roundToDecimal(((float)(amattpr+pmattpr)/(amsestot+pmsestot))*100, 2);
		
	System.out.println(amsestot+"-----tot----- "+pmsestot);
	System.out.println(amattpr+"-----pr----- "+pmattpr);
	System.out.println(amattabs+"----abs------ "+pmattabs);
	System.out.println((amper)+"----per------ "+(pmper));
	System.out.println((totper)+"----totper------ "+(totper1));

	  return totper1;

		}
	
	public String getStudentsForRevaluationEdit(NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm,Integer studentId) throws Exception {

	
			String query = "select e.student,e.classes,e.classes.termNumber from ExamRevaluationApplicationNew e where  e.exam.id="+newSupplementaryImpApplicationForm.getExamId();
			
			if(newSupplementaryImpApplicationForm.getCourseId()!=null && !newSupplementaryImpApplicationForm.getCourseId().isEmpty()){
				query=query+" and e.classes.course.id=" +newSupplementaryImpApplicationForm.getCourseId();
			}
			if(newSupplementaryImpApplicationForm.getSchemeNo()!=null && !newSupplementaryImpApplicationForm.getSchemeNo().isEmpty()){
				query=query+" and e.classes.termNumber="+newSupplementaryImpApplicationForm.getSchemeNo();
			}
			if(studentId!=null && studentId>0){
				query=query+" and e.student.id="+studentId;
			}
			if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation"))
				query += "and e.isRevaluation=1";
			else if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny"))
				query += "and e.isScrutiny=1";
			else{
				query += "and e.isChallengeValuation=1";
			}
	
			return query+" group by e.student.id,e.classes.id order by e.student.registerNo";
		
		
	}
	
	public String getQueryForEditRevaluation(ExamSupplementaryImpApplicationTO to) throws Exception {

		String query="select * from exam_revaluation_application_new e "+
		" inner join EXAM_sub_definition_coursewise on EXAM_sub_definition_coursewise.subject_id = e.subject_id "+
		" where e.class_id=" +to.getClassId()+ 
		" and e.student_id="+to.getStudentId()+ 
		" and e.exam_id= "+to.getExamId()+
		" group by e.subject_id ";

		return query;
	}
	
	public void convertBotoToListForRevaluationEdit(List<ExamRevaluationApplicationNew> boList, ExamSupplementaryImpApplicationTO to) throws Exception {
		List<ExamSupplementaryImpApplicationSubjectTO> subjectList=new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
		if(boList!=null && !boList.isEmpty()){
			Iterator<ExamRevaluationApplicationNew> itr=boList.iterator();
			while (itr.hasNext()) {
				ExamRevaluationApplicationNew bo = itr .next();
				ExamSupplementaryImpApplicationSubjectTO sto=new ExamSupplementaryImpApplicationSubjectTO();
				sto.setId(bo.getId());
				sto.setSubjectId(bo.getSubject().getId());
				sto.setSubjectCode(bo.getSubject().getCode());
				sto.setSubjectName(bo.getSubject().getName());
				if(bo.getMarks()!=null)
				sto.setMarks(bo.getMarks());
				if(bo.getIsRevaluation() && bo.getIsApplied()){
					//sto.setIsAppearedTheory(true);
					sto.setTempChecked(true);
				}
				if(bo.getIsChallengeValuation() && bo.getIsApplied()){
					//sto.setIsAppearedTheory(true);
					sto.setTempChecked(true);
				}
				if(bo.getIsScrutiny() && bo.getIsApplied()){
					//sto.setIsAppearedTheory(true);
					sto.setTempChecked(true);
				}
	
				subjectList.add(sto);
			}
		}
		to.setSubjectList(subjectList);
	}
	
	public String getQueryForDeletingRevaluationRecords(ExamSupplementaryImpApplicationTO to,NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {

		String query=" delete e from exam_revaluation_application_new e "+
		" where e.class_id=" +to.getClassId()+ 
		" and e.student_id="+to.getStudentId()+ 
		" and e.exam_id= "+to.getExamId();
		if(newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation"))
		query = query+ "and e.is_revaluation=1";
		else if(newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){
			query = query+" and e.is_scrutiny=1";
		}
		else
			query = query+ "and e.is_challenge_valuation=1";
	

		return query;
	}
	
	public String getQueryForDeletingRevaluationAppRecords(ExamSupplementaryImpApplicationTO to,NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {

		String query=" delete e from exam_revaluation_app e "+
		" where e.class_id=" +to.getClassId()+ 
		" and e.student_id="+to.getStudentId()+ 
		" and e.exam_id= "+to.getExamId();
		if(newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation"))
		query = query+ "and e.is_revaluation=1";
		else if(newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){
			query = query+" and e.is_scrutiny=1";
		}
		else
			query = query+ "and e.is_challenge_valuation=1";
	

		return query;
	}
		
	
}
