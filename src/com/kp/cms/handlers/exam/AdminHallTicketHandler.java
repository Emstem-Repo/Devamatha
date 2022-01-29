package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.forms.exam.AdminHallTicketForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.helpers.exam.AdminHallTicketHelper;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.to.exam.HallTicketTo;
import com.kp.cms.to.examallotment.ExamRoomAllotmentDetailsTO;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.PropertyUtil;

public class AdminHallTicketHandler {
	/**
	 * Singleton object of AdminHallTicketHandler
	 */
	private static volatile AdminHallTicketHandler adminHallTicketHandler = null;
	IDownloadHallTicketTransaction downloadTxn= new DownloadHallTicketTransactionImpl();
	private AdminHallTicketHandler() {
		
	}
	/**
	 * return singleton object of AdminHallTicketHandler.
	 * @return
	 */
	public static AdminHallTicketHandler getInstance() {
		if (adminHallTicketHandler == null) {
			adminHallTicketHandler = new AdminHallTicketHandler();
		}
		return adminHallTicketHandler;
	}
	/**
	 * @param adminHallTicketForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HallTicketTo> getStudentForInput( AdminHallTicketForm adminHallTicketForm, HttpServletRequest request) throws Exception {
		String programTypeId=	NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(adminHallTicketForm.getClassId()),"Classes",true,"course.program.programType.id");
		IDownloadHallTicketTransaction txn= new DownloadHallTicketTransactionImpl();
		List<ExamFooterAgreementBO> footer=txn.getFooterDetails(programTypeId,"H",adminHallTicketForm.getClassId());
		if(footer!=null && !footer.isEmpty()){
 			ExamFooterAgreementBO obj=footer.get(0);
			if(obj.getDescription()!=null)
				adminHallTicketForm.setDescription(obj.getDescription());
		}else{
			adminHallTicketForm.setDescription(null);
		}
		
		List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
		List<Integer> studentIds=new ArrayList<Integer>();
		String query=AdminHallTicketHelper.getInstance().getCurrentClassStudentsQuery(adminHallTicketForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			studentIds.addAll(list);
		}
		String previousQuery=AdminHallTicketHelper.getInstance().getPreviousClassStudentsQuery(adminHallTicketForm);
		List previousList=transaction.getDataForQuery(previousQuery);
		if(previousList!=null && !previousList.isEmpty()){
			studentIds.addAll(previousList);
		}
		HallTicketTo to=null;
		List<HallTicketTo> studentList=new ArrayList<HallTicketTo>();
		if(studentIds!=null && !studentIds.isEmpty()){
			Map<Integer, Map<String,ExamRoomAllotmentDetailsTO>> examDatesMap = downloadTxn.getRoomNoDetailsForStudents(studentIds,adminHallTicketForm.getExamId(),adminHallTicketForm.getClassId());
			
			for (Integer studentId : studentIds) {
				if(!listOfDetainedStudents.contains(studentId)){
					 String hallTicketQuery= DownloadHallTicketHelper.getInstance().getQueryForStudentHallTicket(studentId,Integer.parseInt(adminHallTicketForm.getExamId()));
					 List hallTicketData=downloadTxn.getStudentHallTicket(hallTicketQuery);
					 if(hallTicketData==null||hallTicketData.size()==0){
						 	IDownloadHallTicketTransaction txnClass = DownloadHallTicketTransactionImpl.getInstance();
							int schemeNoByClassId = txnClass.getTermNumber(Integer.parseInt(adminHallTicketForm.getClassId()));
							hallTicketQuery= DownloadHallTicketHelper.getInstance().getQueryForStudentPrevHallTicket(studentId, Integer.parseInt(adminHallTicketForm.getExamId()), schemeNoByClassId);
							hallTicketData=downloadTxn.getStudentHallTicket(hallTicketQuery);
							
					 }
					 Map<String,ExamRoomAllotmentDetailsTO> examSessionMap = new HashMap<String, ExamRoomAllotmentDetailsTO>();
					 if(hallTicketData!=null && !hallTicketData.isEmpty()){
						 if(examDatesMap != null && !examDatesMap.isEmpty()){
							 examSessionMap = examDatesMap.get(studentId);
						 }
						 to=DownloadHallTicketHelper.getInstance().hallTicketListtoTo(hallTicketData,examSessionMap);
						 
						 to.setStudentPhoto("images/StudentPhotos/"+studentId+".jpg?"+new Date());
						 studentList.add(to);
					 }
				}
			}
		}
		/*Map<String,byte[]> photoMap=transaction.getStudentPhtos(ids,true);
		HttpSession session=request.getSession();
		for (Iterator it = photoMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			session.setAttribute(entry.getKey().toString(),(byte[])entry.getValue());
		}*/
		
		return studentList;
	}
	
	
	
	
	
	/**
	 * @param adminHallTicketForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HallTicketTo> getStudentForSupplementaryInput( AdminHallTicketForm adminHallTicketForm, HttpServletRequest request) throws Exception {
		
		
	//	List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
		List<Object[]> studentIds=new ArrayList<Object[]>();
		String query= AdminHallTicketHelper.getInstance().getCurrStudentIdListByRegisterNo(adminHallTicketForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		if(list==null || list.isEmpty()){
		  query= "select s.id,s.registerNo from Student s where (s.isHide = 0 or s.isHide is null) and s.admAppln.isCancelled=0 and s.classSchemewise.classes.id = "+adminHallTicketForm.getClassId();
		  list=transaction.getDataForQuery(query);
		}
		if(list!=null && !list.isEmpty()){
			studentIds.addAll(list);
		}
		/*String previousQuery=AdminHallTicketHelper.getInstance().getPrevStudentIdListByRegisterNo(adminHallTicketForm);
		List previousList=transaction.getDataForQuery(previousQuery);
		if(previousList!=null && !previousList.isEmpty()){
			studentIds.addAll(previousList);
		}*/
		HallTicketTo to=null;
		
		List<Integer> classList= null;
		List<HallTicketTo> studentList=new ArrayList<HallTicketTo>();
		List<Integer> ids=new ArrayList<Integer>();
		if(studentIds!=null && !studentIds.isEmpty()){
			Iterator<Object[]> itr=studentIds.iterator();
			while (itr.hasNext()) {
				Object[] obj =itr.next();
				if(obj[0]!=null && obj[1]!=null){
					    //if(!listOfDetainedStudents.contains(Integer.parseInt(obj[0].toString()))){
					    int studId= Integer.parseInt(obj[0].toString());
					    
					    
					    //vibin for sbc....
					   
							if(adminHallTicketForm.getRegNoFrom()!=null && !adminHallTicketForm.getRegNoFrom().isEmpty()){
								
								String classQuery=AdminHallTicketHelper.getInstance().getClassListByStudId(studId);
								classList=transaction.getDataForQueryClasses(classQuery);
								
							}else{
								classList = new ArrayList<Integer>();
								classList.add(Integer.parseInt(adminHallTicketForm.getClassId()));
							}
						//end...
					    
					    
						
						
						
						
						if(classList!=null && !classList.isEmpty())
						{
						String hallTicketQuery= DownloadHallTicketHelper.getInstance().getQueryForSupHallTicketAdmin(studId,Integer.parseInt(adminHallTicketForm.getExamId()));
						List hallTicketData=downloadTxn.getStudentHallTicketNew(hallTicketQuery,classList);
						if(hallTicketData!=null && !hallTicketData.isEmpty()){
						to=DownloadHallTicketHelper.getInstance().getSupHallTicketForStudent(hallTicketData);
						to.setStudentPhoto("images/StudentPhotos/"+studId+".jpg?"+new Date());
					    studentList.add(to);
					    ids.add(Integer.parseInt(obj[0].toString()));
						}
						// }
					}
				}
			}
		
		int classIds=0;
		for (Integer val : classList) {
			classIds=val;
			
		}
		if(classIds>0)
		{
		String programTypeId=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(classIds,"Classes",true,"course.program.programType.id");
		IDownloadHallTicketTransaction txn= new DownloadHallTicketTransactionImpl();
		List<ExamFooterAgreementBO> footer=txn.getFooterDetails(programTypeId,"H",String.valueOf(classIds));
		
		if(footer!=null && !footer.isEmpty()){
			ExamFooterAgreementBO obj=footer.get(0);
			if(obj.getDescription()!=null)
				adminHallTicketForm.setDescription(obj.getDescription());
		}else{
			adminHallTicketForm.setDescription(null);
		}
		}
		
		/*Map<String,byte[]> photoMap=transaction.getStudentPhtos(ids,true);
		HttpSession session=request.getSession();
		for (Iterator it = photoMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			session.setAttribute(entry.getKey().toString(),(byte[])entry.getValue());
		}*/
		}
		return studentList;
	}
	/**
	 * @param adminHallTicketForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HallTicketTo> getStudentForInternalInput(AdminHallTicketForm adminHallTicketForm, HttpServletRequest request) throws Exception{
		String programTypeId=	NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(adminHallTicketForm.getClassId()),"Classes",true,"course.program.programType.id");
		IDownloadHallTicketTransaction txn= new DownloadHallTicketTransactionImpl();
		List<ExamFooterAgreementBO> footer=txn.getFooterDetails(programTypeId,"H",adminHallTicketForm.getClassId());
		if(footer!=null && !footer.isEmpty()){
			ExamFooterAgreementBO obj=footer.get(0);
			if(obj.getDescription()!=null)
				adminHallTicketForm.setDescription(obj.getDescription());
		}else{
			adminHallTicketForm.setDescription(null);
		}
		
		List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
		List<Integer> studentIds=new ArrayList<Integer>();
		String query=AdminHallTicketHelper.getInstance().getCurrentClassStudentsQuery(adminHallTicketForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			studentIds.addAll(list);
		}
		String previousQuery=AdminHallTicketHelper.getInstance().getPreviousClassStudentsQuery(adminHallTicketForm);
		List previousList=transaction.getDataForQuery(previousQuery);
		if(previousList!=null && !previousList.isEmpty()){
			studentIds.addAll(previousList);
		}
		HallTicketTo to=null;
		List<HallTicketTo> studentList=new ArrayList<HallTicketTo>();
		if(studentIds!=null && !studentIds.isEmpty()){
			Map<Integer, Map<String,ExamRoomAllotmentDetailsTO>> examDatesMap = downloadTxn.getRoomNoDetailsForStudents(studentIds,adminHallTicketForm.getExamId(),adminHallTicketForm.getClassId());
			for (Integer studentId : studentIds) {
				if(!listOfDetainedStudents.contains(studentId)){
					 String hallTicketQuery= DownloadHallTicketHelper.getInstance().getQueryForStudentHallTicket(studentId,Integer.parseInt(adminHallTicketForm.getExamId()));
					 List hallTicketData=downloadTxn.getStudentHallTicket(hallTicketQuery);
					 Map<String,ExamRoomAllotmentDetailsTO> examSessionMap = new HashMap<String, ExamRoomAllotmentDetailsTO>();
					 /* code added by sudhir */
					 if(hallTicketData!=null && !hallTicketData.isEmpty()){
						 if(examDatesMap != null && !examDatesMap.isEmpty()){
							 examSessionMap = examDatesMap.get(studentId);
						 }
						 to=DownloadHallTicketHelper.getInstance().hallTicketListtoTo(hallTicketData,examSessionMap);
						 to.setStudentPhoto("images/StudentPhotos/"+studentId+".jpg?"+new Date());
						 studentList.add(to);
					 }
				}
			}
		}
		
		/*Map<String,byte[]> photoMap=transaction.getStudentPhtos(ids,true);
		HttpSession session=request.getSession();
		for (Iterator it = photoMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			session.setAttribute(entry.getKey().toString(),(byte[])entry.getValue());
		}*/
		
		return studentList;
	}
	/**
	 * @param examId
	 * @param year 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getClasesByExamName(String examId, String year) throws Exception{
		
		return downloadTxn.getClasesByExamName(examId,year);
	}
	/**
	 * @param adminHallTicketForm
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HallTicketTo getHallticketForStudent(AdminHallTicketForm adminHallTicketForm) throws Exception {
		Student stu =  PropertyUtil.getInstance().getStudent(adminHallTicketForm.getRegNoFrom());
		String hallTicketQuery="";
		List hallTicketData=null;
		IDownloadHallTicketTransaction txn = DownloadHallTicketTransactionImpl.getInstance();
		if(adminHallTicketForm.getExamType()!=null && adminHallTicketForm.getExamType().equalsIgnoreCase("Supplementary")){
			INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
			String classQuery=AdminHallTicketHelper.getInstance().getClassListByStudId(stu.getId());
			List<Integer> classList=transaction.getDataForQueryClasses(classQuery);
			if(classList!=null && !classList.isEmpty())	{
				hallTicketQuery= DownloadHallTicketHelper.getInstance().getQueryForSupHallTicketAdmin(stu.getId(),Integer.parseInt(adminHallTicketForm.getExamId()));
				hallTicketData=downloadTxn.getStudentHallTicketNew(hallTicketQuery,classList);
			}
		}else{
			hallTicketQuery= DownloadHallTicketHelper.getInstance().getQueryForStudentHallTicket(stu.getId(),Integer.parseInt(adminHallTicketForm.getExamId()));
			hallTicketData=downloadTxn.getStudentHallTicket(hallTicketQuery);
			if(hallTicketData==null||hallTicketData.size()==0){
			 	IDownloadHallTicketTransaction txnClass = DownloadHallTicketTransactionImpl.getInstance();
				//int schemeNoByClassId = txnClass.getTermNumber(Integer.parseInt(adminHallTicketForm.getClassId()));
				int schemeNoByClassId = DownloadHallTicketHandler.getInstance().getPreClassId(stu.getId(), new LoginForm());
				hallTicketQuery= DownloadHallTicketHelper.getInstance().getQueryForStudentPrevHallTicket(stu.getId(),Integer.parseInt(adminHallTicketForm.getExamId()),schemeNoByClassId);
				hallTicketData=downloadTxn.getStudentHallTicket(hallTicketQuery);
			}
		}
		Map<String,ExamRoomAllotmentDetailsTO> examSessionMap = new HashMap<String, ExamRoomAllotmentDetailsTO>();
		downloadTxn.getRoomNoAndFloorNoAndBlockNo(stu.getId(),Integer.parseInt(adminHallTicketForm.getExamId()),stu.getClassSchemewise().getClasses().getId(),examSessionMap);
		HallTicketTo to = null;
		if(hallTicketData!=null && !hallTicketData.isEmpty()){
			if(adminHallTicketForm.getExamType()!=null && adminHallTicketForm.getExamType().equalsIgnoreCase("Supplementary")){
				to=DownloadHallTicketHelper.getInstance().getSupHallTicketForStudent(hallTicketData);
			}else{
				to = DownloadHallTicketHelper.getInstance().hallTicketListtoTo(hallTicketData,examSessionMap);
			}
			to.setStudentPhoto("images/StudentPhotos/"+stu.getId()+".jpg?"+new Date());
			String programTypeId=	NewSecuredMarksEntryHandler.getInstance().getPropertyValue(stu.getClassSchemewise().getClasses().getId(),"Classes",true,"course.program.programType.id");
			List<ExamFooterAgreementBO> footer=txn.getFooterDetails(programTypeId,"H",adminHallTicketForm.getClassId());
			if(footer!=null && !footer.isEmpty()){
				ExamFooterAgreementBO obj=footer.get(0);
				if(obj.getDescription()!=null)
					adminHallTicketForm.setDescription(obj.getDescription());
			}else{
				adminHallTicketForm.setDescription(null);
			}
		}
		return to;
	}
	
	
}
