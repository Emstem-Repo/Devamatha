package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.AdminMarksCardForm;
import com.kp.cms.helpers.exam.AdminMarksCardHelper;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.MarkComparator;

public class AdminMarksCardHandler {
	/**
	 * Singleton object of AdminMarksCardHandler
	 */
	private static volatile AdminMarksCardHandler adminMarksCardHandler = null;
	private static final Log log = LogFactory.getLog(AdminMarksCardHandler.class);
	private AdminMarksCardHandler() {
		
	}
	/**
	 * return singleton object of AdminMarksCardHandler.
	 * @return
	 */
	public static AdminMarksCardHandler getInstance() {
		if (adminMarksCardHandler == null) {
			adminMarksCardHandler = new AdminMarksCardHandler();
		}
		return adminMarksCardHandler;
	}
	/**
	 * @param adminMarksCardForm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MarksCardTO> getStudentForInput( AdminMarksCardForm adminMarksCardForm,HttpServletRequest request) throws Exception{
		List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
		String schemeNo=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(adminMarksCardForm.getClassId()),"Classes",true,"termNumber");
		String courseId=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(adminMarksCardForm.getClassId()),"Classes",true,"course.id");
		String programTypeId=	NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(adminMarksCardForm.getClassId()),"Classes",true,"course.program.programType.id");
		boolean pgMarksCard=false;
		if(programTypeId!=null && CMSConstants.PG_ID.contains(Integer.parseInt(programTypeId)))
			pgMarksCard=true;
		adminMarksCardForm.setPg(pgMarksCard);
		adminMarksCardForm.setProgramTypeIdNew(programTypeId);
		String batch="";
		List<Integer> studentIds=new ArrayList<Integer>();
		String query=AdminMarksCardHelper.getInstance().getCurrentClassStudentsQuery(adminMarksCardForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		IDownloadHallTicketTransaction transaction1= DownloadHallTicketTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			studentIds.addAll(list);
		}
		String previousQuery=AdminMarksCardHelper.getInstance().getPreviousClassStudentsQuery(adminMarksCardForm);
		List previousList=transaction.getDataForQuery(previousQuery);
		if(previousList!=null && !previousList.isEmpty()){
			studentIds.addAll(previousList);
		}
		Map<Integer,byte[]> photoMap=transaction.getStudentPhtosMap(studentIds,false);
		List<MarksCardTO> marksCardList=new ArrayList<MarksCardTO>();
		if(studentIds!=null && !studentIds.isEmpty()){
			Iterator<Integer> itr=studentIds.iterator();
			while (itr.hasNext()) {
				Integer studentId = (Integer) itr.next();
				if(adminMarksCardForm.getExamType().equalsIgnoreCase("Regular")){
					if(!listOfDetainedStudents.contains(studentId)){
						if(!pgMarksCard){
							batch=transaction1.getStudentBatch(studentId);
							if(!batch.isEmpty())
								adminMarksCardForm.setBatch(batch);
							MarksCardTO mto =null;
							String marksCardQuery=DownloadHallTicketHelper.getInstance()
																		  .getQueryForUGStudentMarksCard(Integer.parseInt(adminMarksCardForm.getExamId()),
																				  						 Integer.parseInt(adminMarksCardForm.getClassId()),
																				  						 Integer.parseInt(schemeNo),
																				  						 studentId,
																				  						 Integer.parseInt(adminMarksCardForm.getYear()));
							List<Object[]> ugMarksCardData=transaction1.getStudentHallTicket(marksCardQuery);
							
							if(ugMarksCardData.size()>0)
								mto= DownloadHallTicketHelper.getInstance()
															 .getMarksCardForUg(ugMarksCardData,
																	 			Integer.parseInt(schemeNo),
																	 			studentId,
																	 			photoMap,
																	 			request,
																	 			new HashMap<Integer, String>());
							if(mto!=null){
								if(mto.getExamSubCoursewiseGradeDefnTOList()!=null && mto.getExamSubCoursewiseGradeDefnTOList().size()>0)
									adminMarksCardForm.setExamSubCoursewiseGradeDefnTOList(mto.getExamSubCoursewiseGradeDefnTOList());
								if(mto.getCourseWiseGradeList()!=null && mto.getCourseWiseGradeList().size()>0)
									adminMarksCardForm.setCourseWiseGradeList(mto.getCourseWiseGradeList());
								if((adminMarksCardForm.getBatch()!=null && !adminMarksCardForm.getBatch().isEmpty()) && adminMarksCardForm.getProgramTypeIdNew()!=null)
									mto.setDispalySem1and2("false");	
								mto.setStudentId(studentId);
								marksCardList.add(mto);
							}							
						}else{
							batch=transaction1.getStudentBatch(studentId);
							String marksCardQuery=DownloadHallTicketHelper.getInstance()
																		  .getQueryForPGStudentMarksCard(Integer.parseInt(adminMarksCardForm.getExamId()),
																				  						 Integer.parseInt(adminMarksCardForm.getClassId()),
																				  						 studentId,
																				  						 Integer.parseInt(schemeNo),
																				  						 Integer.parseInt(adminMarksCardForm.getYear()));
							if(!batch.isEmpty())
								adminMarksCardForm.setBatch(batch);
							List<Object[]> pgMarksCardData=transaction1.getStudentHallTicket(marksCardQuery);
							MarksCardTO mto= DownloadHallTicketHelper.getInstance()
																	 .getMarksCardForPg(pgMarksCardData,
																			 			studentId,photoMap,
																			 			request,
																			 			new HashMap<Integer,String>(),
																			 			Integer.parseInt(programTypeId));
			
							if(mto!=null){
								if(mto.getExamSubCoursewiseGradeDefnTOList()!=null && mto.getExamSubCoursewiseGradeDefnTOList().size()>0)
									adminMarksCardForm.setExamSubCoursewiseGradeDefnTOList(mto.getExamSubCoursewiseGradeDefnTOList());
								if(mto.getCourseWiseGradeList()!=null && mto.getCourseWiseGradeList().size()>0)
									adminMarksCardForm.setCourseWiseGradeList(mto.getCourseWiseGradeList());
								
								mto.setDispalySem1and2("false");
								mto.setStudentId(studentId);
								marksCardList.add(mto);
							}
						}
					}
				}else{
					if(!pgMarksCard){
						String marksCardQuery=DownloadHallTicketHelper.getInstance()
																	  .getQueryForUGStudentSupMarksCard(Integer.parseInt(adminMarksCardForm.getExamId()),
																			  							Integer.parseInt(adminMarksCardForm.getClassId())
								,Integer.parseInt(schemeNo),studentId,Integer.parseInt(adminMarksCardForm.getYear()));
						List<Object[]> ugMarksCardData=transaction1.getStudentHallTicket(marksCardQuery);
						MarksCardTO mto= DownloadHallTicketHelper.getInstance()
																 .getSupMarksCardForUg(ugMarksCardData,
																		 			   Integer.parseInt(schemeNo),
																		 			   studentId,
																		 			   photoMap,
																		 			   request,
																		 			   Integer.parseInt(adminMarksCardForm.getExamId()),
																		 			   new HashMap<Integer,String>());
				
						if(mto!=null){
							if(mto.getExamSubCoursewiseGradeDefnTOList()!=null && mto.getExamSubCoursewiseGradeDefnTOList().size()>0)
								adminMarksCardForm.setExamSubCoursewiseGradeDefnTOList(mto.getExamSubCoursewiseGradeDefnTOList());
							if(mto.getCourseWiseGradeList()!=null && mto.getCourseWiseGradeList().size()>0)
								adminMarksCardForm.setCourseWiseGradeList(mto.getCourseWiseGradeList());
							mto.setStudentId(studentId);
							marksCardList.add(mto);
						}
					}else{
						String marksCardQuery=DownloadHallTicketHelper.getInstance()
																	  .getQueryForPGStudentSupMarksCard(Integer.parseInt(adminMarksCardForm.getExamId()),
																			  							Integer.parseInt(adminMarksCardForm.getClassId()),
																			  							Integer.parseInt(schemeNo),
																			  							studentId,
																			  							Integer.parseInt(adminMarksCardForm.getYear()));
						List<Object[]> pgMarksCardData=transaction1.getStudentHallTicket(marksCardQuery);
						MarksCardTO mto= DownloadHallTicketHelper.getInstance()
																 .getSupMarksCardForPg(pgMarksCardData,
																		 			   Integer.parseInt(schemeNo),
																		 			   studentId,
																		 			   photoMap,
																		 			   request,
																		 			   Integer.parseInt(adminMarksCardForm.getExamId()),
																		 			   new HashMap<Integer,String>());			
						if(mto!=null){
							if(mto.getExamSubCoursewiseGradeDefnTOList()!=null && mto.getExamSubCoursewiseGradeDefnTOList().size()>0)
								adminMarksCardForm.setExamSubCoursewiseGradeDefnTOList(mto.getExamSubCoursewiseGradeDefnTOList());
							if(mto.getCourseWiseGradeList()!=null && mto.getCourseWiseGradeList().size()>0)
								adminMarksCardForm.setCourseWiseGradeList(mto.getCourseWiseGradeList());
							mto.setStudentId(studentId);
							marksCardList.add(mto);
						}
					}
				}
			}
		}
		MarkComparator mc=new MarkComparator();
		mc.setCompare(1);
		Collections.sort(marksCardList,mc);
		if(courseId!=null && !courseId.equalsIgnoreCase("18")){			
			List<ExamFooterAgreementBO> footer=transaction1.getFooterDetails(programTypeId,"M",adminMarksCardForm.getClassId());
			if(footer!=null && !footer.isEmpty()) {
				ExamFooterAgreementBO obj=footer.get(0);
				if(obj.getDescription()!=null)
					adminMarksCardForm.setDescription(obj.getDescription());
			}else{
				adminMarksCardForm.setDescription(null);
			}
		}else{
			adminMarksCardForm.setDescription(CMSConstants.MARKS_CARD_DESCRIPTION);
		}
		return marksCardList;
	}
}
