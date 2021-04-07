package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.bo.exam.ExamStudentPassFail;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.RevaluationMarksUpdateForm;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.RevaluationMarksUpdateTo;
import com.kp.cms.to.exam.StudentMarkDetailsTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.to.exam.SubjectMarksTO;
import com.kp.cms.to.exam.SubjectRuleSettingsTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.INewUpdateProccessTransaction;
import com.kp.cms.transactions.exam.IRevaluationMarksUpdateTransaction;
import com.kp.cms.transactions.exam.IUpdateStudentSGPATxn;
import com.kp.cms.transactionsimpl.exam.ExamUpdateProcessImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewUpdateProccessTransactionImpl;
import com.kp.cms.transactionsimpl.exam.RevaluationMarksUpdateTransactionImpl;
import com.kp.cms.transactionsimpl.exam.UpdateExamStudentSGPAImpl;
import com.kp.cms.utilities.CommonUtil;


public class RevaluationMarksUpdateHelper {
	static DecimalFormat df = new DecimalFormat("#.##");
	public static List<Integer> avoidExamIds=new ArrayList<Integer>();
//	static{
//		avoidExamIds.add(143);
//		avoidExamIds.add(144);
//		avoidExamIds.add(149);
//		avoidExamIds.add(151);
//		avoidExamIds.add(157);
//		avoidExamIds.add(132);
//		avoidExamIds.add(137);
//		avoidExamIds.add(147);
//		avoidExamIds.add(148);
//		avoidExamIds.add(159);
//		avoidExamIds.add(160);
//	}
	private static final Log log = LogFactory.getLog(RevaluationMarksUpdateHelper.class);
	private static volatile RevaluationMarksUpdateHelper revaluationMarksUpdateHelper = null;
		
		/**
		 * @return
		 */
		public static RevaluationMarksUpdateHelper getInstance() {
			if (revaluationMarksUpdateHelper == null) {
				revaluationMarksUpdateHelper = new RevaluationMarksUpdateHelper();
			}
			return revaluationMarksUpdateHelper;
		}
		
		
		public Map<Integer, Map<Integer,RevaluationMarksUpdateTo>> convertBotoTo(List<Object> boList,RevaluationMarksUpdateForm  form) throws Exception{
			Map<Integer, Map<Integer,RevaluationMarksUpdateTo>> mainMap = new HashMap<Integer, Map<Integer,RevaluationMarksUpdateTo>>();
			Map<Integer,RevaluationMarksUpdateTo> subMap=null;
			//IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
			String rev="Revaluation";
				if(boList != null && !boList.isEmpty()){
				Iterator<Object> iterator = boList.iterator();
				while (iterator.hasNext()) {
					Object[] bo = (Object[]) iterator.next();
					String type=String.valueOf(bo[7]);
					if(bo[0] != null && !bo[0].toString().isEmpty()){
						
						//check main map already data contain or not
						if(mainMap.containsKey(bo[0])){
							// if the map already contais studentId
							Map<Integer,RevaluationMarksUpdateTo> to2=mainMap.get(bo[0]);
								Iterator<Entry<Integer,RevaluationMarksUpdateTo>> iterator2 = to2.entrySet().iterator();
								int count=0;
								while (iterator2 .hasNext()) {
									Map.Entry<Integer,RevaluationMarksUpdateTo> entry1 = (Map.Entry<Integer,RevaluationMarksUpdateTo>) iterator2.next();
									RevaluationMarksUpdateTo submap2=entry1.getValue();
									//checking same subjects or not
								    if((Integer.parseInt(bo[11].toString()))== submap2.getSubjectId()){
								    	
								    	count ++;
								    	
								    	//if the subject having three evaluators
								    	if(type.equalsIgnoreCase("3")){
								    	RevaluationMarksUpdateTo to1=mainMap.get(bo[0]).get(bo[11]);
								    		to1.setExamRevaluationAppIdForEvL3(Integer.parseInt(bo[14].toString()));
								    		to1.setExamMarksEntryIdForThirdEvl3(Integer.parseInt(bo[15].toString()));
								    		to1.setExamMarksEntryDetailsIdForThirdEvl3(Integer.parseInt(bo[16].toString()));
								    	/*if(bo[9]!=null)
								    		to1.setNewMark2(bo[9].toString());
										if(bo[6]!=null){
											to1.setOldMark2(bo[6].toString().trim());
										}*/
										
										//new
										if(bo[21]!=null){
											to1.setNewMark3(bo[21].toString().trim());
										}
										if(bo[6]!=null){
											to1.setOldMark3(bo[6].toString().trim());
										}
										
										if(bo[21] !=null &&  bo[6]!=null ){
											 if(Integer.parseInt(bo[21].toString())== Integer.parseInt(bo[6].toString())){
												String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[21].toString()));
												to1.setDiffOfMarks3(diffOfMarks);
												}else if(Integer.parseInt(bo[21].toString())> Integer.parseInt(bo[6].toString())){
												String diffOfMarks=String.valueOf(Integer.parseInt(bo[21].toString())- Integer.parseInt(bo[6].toString()));
												to1.setDiffOfMarks3("+"+diffOfMarks);
											}else if(Integer.parseInt(bo[21].toString())< Integer.parseInt(bo[6].toString())){
												String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[21].toString()));
												to1.setDiffOfMarks3("-"+diffOfMarks);
												}
											}
										if(bo[20]!=null && !bo[20].toString().isEmpty()){
											if(bo[20].equals(true)){
												to1.setGracedMark3(true);
											}else{
												to1.setGracedMark3(false);
											}
										}else{
											to1.setGracedMark3(false);
										}
											to1.setCourseId(Integer.parseInt(bo[17].toString()));
											to1.setSchemeNumber(Integer.parseInt(bo[18].toString()));
											to1.setIsUpdated(false);
											subMap = mainMap.remove(bo[0]);	
											subMap.put(Integer.valueOf(bo[11].toString()),to1);
											mainMap.put(Integer.parseInt(bo[0].toString()),subMap);
								    	}
								    	
								    	//if the subject having two evaluators
								    	if(type.equalsIgnoreCase("2")){
								    	RevaluationMarksUpdateTo to1=mainMap.get(bo[0]).get(bo[11]);
								    		to1.setExamRevaluationAppIdForEvL2(Integer.parseInt(bo[14].toString()));
								    		to1.setExamMarksEntryIdForSecondEvl2(Integer.parseInt(bo[15].toString()));
								    		to1.setExamMarksEntryDetailsIdForSecondEvl2(Integer.parseInt(bo[16].toString()));
								    	if(bo[9]!=null)
								    		to1.setNewMark2(bo[9].toString());
										if(bo[6]!=null){
											to1.setOldMark2(bo[6].toString().trim());
										}
										
										if(bo[9] !=null &&  bo[6]!=null ){
											 if(Integer.parseInt(bo[9].toString())== Integer.parseInt(bo[6].toString())){
												String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[9].toString()));
												to1.setDiffOfMarks2(diffOfMarks);
												}else if(Integer.parseInt(bo[9].toString())> Integer.parseInt(bo[6].toString())){
												String diffOfMarks=String.valueOf(Integer.parseInt(bo[9].toString())- Integer.parseInt(bo[6].toString()));
												to1.setDiffOfMarks2("+"+diffOfMarks);
											}else if(Integer.parseInt(bo[9].toString())< Integer.parseInt(bo[6].toString())){
												String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[9].toString()));
												to1.setDiffOfMarks2("-"+diffOfMarks);
												}
											}
										if(bo[20]!=null && !bo[20].toString().isEmpty()){
											if(bo[20].equals(true)){
												to1.setGracedMark2(true);
											}else{
												to1.setGracedMark2(false);
											}
										}else{
											to1.setGracedMark2(false);
										}
											to1.setCourseId(Integer.parseInt(bo[17].toString()));
											to1.setSchemeNumber(Integer.parseInt(bo[18].toString()));
											to1.setIsUpdated(false);
											subMap = mainMap.remove(bo[0]);	
											subMap.put(Integer.valueOf(bo[11].toString()),to1);
											mainMap.put(Integer.parseInt(bo[0].toString()),subMap);
								    	}
								    	//if the subject having one evaluators
								    	else if(type.equalsIgnoreCase("1")){
								    		RevaluationMarksUpdateTo to1=mainMap.get(bo[0]).get(bo[11]);
								    		to1.setExamRevaluationAppIdForEvL1(Integer.parseInt(bo[14].toString()));
								    		to1.setExamMarksEntryId(Integer.parseInt(bo[15].toString()));
											to1.setExamMarksEntryDetailsId(Integer.parseInt(bo[16].toString()));
								    		if(bo[8]!=null){
												to1.setNewMark1(bo[8].toString());
								    		    to1.setMarks(bo[8].toString().trim());
								    		}
												if(bo[6]!=null){
													to1.setOldMark1(bo[6].toString().trim());
												}
												if(bo[8]!=null && bo[6]!=null){
													 if(Integer.parseInt(bo[8].toString())== Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[8].toString()));
														to1.setDiffOfMarks1(diffOfMarks);
													}else if(Integer.parseInt(bo[8].toString())> Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[8].toString())- Integer.parseInt(bo[6].toString()));
														to1.setDiffOfMarks1("+"+diffOfMarks);
													}else if(Integer.parseInt(bo[8].toString())< Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[8].toString()));
														to1.setDiffOfMarks1("-"+diffOfMarks);
													}
												}
												if(bo[20]!=null && !bo[20].toString().isEmpty()){
													if(bo[20].equals(true)){
														to1.setGracedMark1(true);
													}else{
														to1.setGracedMark1(false);
													}
												}else{
													to1.setGracedMark1(false);
												}
												to1.setCourseId(Integer.parseInt(bo[17].toString()));
												to1.setSchemeNumber(Integer.parseInt(bo[18].toString()));
												to1.setIsUpdated(false);
												subMap = mainMap.remove(bo[0]);
												subMap.put(Integer.valueOf(bo[11].toString()),to1);
												mainMap.put(Integer.parseInt(bo[0].toString()),subMap);
								    			}
								    	
								    		}//if check
								    }//while over
								
								// check with new subject if count = 0
								if(count == 0){
										// if student have no evalutor
								    	//if one student having multiple subjects
										if(type.equalsIgnoreCase("null")){
											subMap = mainMap.remove(bo[0]);
											RevaluationMarksUpdateTo to= new RevaluationMarksUpdateTo();
											to.setStudentId(Integer.parseInt(bo[0].toString()));
											to.setRegisterNo(bo[1].toString());
											to.setClassName(bo[3].toString());
											to.setClassId(Integer.parseInt(bo[4].toString()));
											to.setStudentName(bo[5].toString());
											if(bo[6]!=null){
												to.setOldMarks(bo[6].toString().trim());
											}
											if(bo[10]!=null){
												to.setNewMarks(bo[10].toString().trim());
												to.setMarks(bo[10].toString().trim());
											}
											to.setSubjectId(Integer.parseInt(bo[11].toString()));
											to.setSubjectCode(bo[13].toString()+"("+ bo[12]+")");
											to.setExamRevaluationAppId(Integer.parseInt(bo[14].toString()));
											to.setExamMarksEntryIdForNoEvl(Integer.parseInt(bo[15].toString()));
											to.setExamMarksEntryDetailsIdForNoEvl(Integer.parseInt(bo[16].toString()));
											to.setCourseId(Integer.parseInt(bo[17].toString()));
											to.setSchemeNumber(Integer.parseInt(bo[18].toString()));
											if(bo[19]!=null)
											to.setThirdEvlMarks(bo[19].toString());
											to.setComment(form.getRevaluation());
											if(bo[10] !=null && bo[6]!=null ){
												if(form.getRevaluation().equalsIgnoreCase(rev)){
													String avgMarks= String.valueOf( Math.ceil((Float.parseFloat(bo[6].toString()) + Float.parseFloat(bo[10].toString()))/ 2));
													avgMarks=avgMarks.substring(0, (avgMarks.length() - 2));
													if(form.getOption().equalsIgnoreCase("NotUpdated")){
													 to.setAvgMarks(avgMarks);
													}else if(form.getOption().equalsIgnoreCase("Requested_for_thirdEvaluation")){
														//to.setAvgMarks(avgMarks);
													}
													to.setOldAvgMarks(avgMarks);
												}	
												 if(Integer.parseInt(bo[10].toString().trim())== Integer.parseInt(bo[6].toString().trim())){
													String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString().trim()) - Integer.parseInt(bo[10].toString().trim()));
													to.setDiffOfMarks(diffOfMarks);
												}else if(Integer.parseInt(bo[10].toString())> Integer.parseInt(bo[6].toString())){
													String diffOfMarks=String.valueOf(Integer.parseInt(bo[10].toString())- Integer.parseInt(bo[6].toString()));
													to.setDiffOfMarks("+"+diffOfMarks);
												}else if(Integer.parseInt(bo[10].toString().trim())< Integer.parseInt(bo[6].toString().trim())){
													String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString().trim()) - Integer.parseInt(bo[10].toString().trim()));
													to.setDiffOfMarks("-"+diffOfMarks);
												}
											}
											if(bo[20]!=null && !bo[20].toString().isEmpty()){
												if(bo[20].equals(true)){
													to.setGracedMark(true);
												}else{
													to.setGracedMark(false);
												}
											}else{
												to.setGracedMark(false);
											}
											to.setIsUpdated(false);
											subMap.put(Integer.valueOf(bo[11].toString()),to);
											mainMap.put(Integer.parseInt(bo[0].toString()),subMap);
										}
										// if student have  evalutors
										else{
											// if student have  one evalutors
											if(type.equalsIgnoreCase("1")){
												subMap = mainMap.remove(bo[0]);
												RevaluationMarksUpdateTo to= new RevaluationMarksUpdateTo();
												
												to.setExamRevaluationAppIdForEvL1(Integer.parseInt(bo[14].toString()));
												to.setExamMarksEntryId(Integer.parseInt(bo[15].toString()));
												to.setExamMarksEntryDetailsId(Integer.parseInt(bo[16].toString()));
												to.setClassId(Integer.parseInt(bo[4].toString()));
												to.setSubjectId(Integer.parseInt(bo[11].toString()));
												to.setStudentId(Integer.parseInt(bo[0].toString()));
												to.setClassName(bo[3].toString());
												to.setSubjectCode(bo[13].toString()+"("+ bo[12]+")");
												if(bo[8]!=null){
												to.setNewMark1(bo[8].toString().trim());
											    to.setMarks(bo[8].toString().trim());
												}
												if(bo[6]!=null){
													to.setOldMark1(bo[6].toString().trim());
												}
												to.setStudentName(bo[5].toString());
												to.setRegisterNo(bo[1].toString());
												to.setComment(form.getRevaluation());
												if(bo[8]!=null && bo[6]!=null){
													 if(Integer.parseInt(bo[8].toString())== Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[8].toString()));
														to.setDiffOfMarks1(diffOfMarks);
													}else if(Integer.parseInt(bo[8].toString())> Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[8].toString())- Integer.parseInt(bo[6].toString()));
														to.setDiffOfMarks1("+"+diffOfMarks);
													}else if(Integer.parseInt(bo[8].toString())< Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[8].toString()));
														to.setDiffOfMarks1("-"+diffOfMarks);
													}
												}
												if(bo[20]!=null && !bo[20].toString().isEmpty()){
													if(bo[20].equals(true)){
														to.setGracedMark1(true);
													}else{
														to.setGracedMark1(false);
													}
												}else{
													to.setGracedMark1(false);
												}
												to.setCourseId(Integer.parseInt(bo[17].toString()));
												to.setSchemeNumber(Integer.parseInt(bo[18].toString()));
												to.setIsUpdated(false);
												subMap.put(Integer.valueOf(bo[11].toString()),to);
												mainMap.put(Integer.parseInt(bo[0].toString()),subMap);
											}
											// if student have two evalutors
											else if(type.equalsIgnoreCase("2")){
												subMap = mainMap.remove(bo[0]);
												RevaluationMarksUpdateTo to= new RevaluationMarksUpdateTo();
												
												to.setExamRevaluationAppIdForEvL2(Integer.parseInt(bo[14].toString()));
												to.setExamMarksEntryIdForSecondEvl2(Integer.parseInt(bo[15].toString()));
									    		to.setExamMarksEntryDetailsIdForSecondEvl2(Integer.parseInt(bo[16].toString()));
												to.setClassId(Integer.parseInt(bo[4].toString()));
												to.setSubjectId(Integer.parseInt(bo[11].toString()));
												to.setStudentId(Integer.parseInt(bo[0].toString()));
												to.setClassName(bo[3].toString());
												to.setSubjectCode(bo[13].toString()+"("+ bo[12]+")");
												if(bo[9]!=null)
												to.setNewMark2(bo[9].toString().trim());
												if(bo[6]!=null){
													to.setOldMark2(bo[6].toString().trim());
												}
												to.setStudentName(bo[5].toString());
												to.setRegisterNo(bo[1].toString());
												to.setComment(form.getRevaluation());
												if(bo[9] !=null && bo[6]!=null ){
													 if(Integer.parseInt(bo[9].toString()) == Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[9].toString()));
														to.setDiffOfMarks2(diffOfMarks);
													}else if(Integer.parseInt(bo[9].toString())> Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[9].toString())- Integer.parseInt(bo[6].toString()));
														to.setDiffOfMarks2("+"+diffOfMarks);
													}else if(Integer.parseInt(bo[9].toString())< Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[9].toString()));
														to.setDiffOfMarks2("-"+diffOfMarks);
													}
												}
												if(bo[20]!=null && !bo[20].toString().isEmpty()){
													if(bo[20].equals(true)){
														to.setGracedMark2(true);
													}else{
														to.setGracedMark2(false);
													}
												}else{
													to.setGracedMark2(false);
												}
												to.setCourseId(Integer.parseInt(bo[17].toString()));
												to.setSchemeNumber(Integer.parseInt(bo[18].toString()));
												to.setIsUpdated(false);
												subMap.put(Integer.valueOf(bo[11].toString()),to);
												mainMap.put(Integer.parseInt(bo[0].toString()),subMap);
											}
											
											// if student have three evalutors
											else if(type.equalsIgnoreCase("3")){
												subMap = mainMap.remove(bo[0]);
												RevaluationMarksUpdateTo to= new RevaluationMarksUpdateTo();
												
												to.setExamRevaluationAppIdForEvL3(Integer.parseInt(bo[14].toString()));
												to.setExamMarksEntryIdForThirdEvl3(Integer.parseInt(bo[15].toString()));
									    		to.setExamMarksEntryDetailsIdForThirdEvl3(Integer.parseInt(bo[16].toString()));
												to.setClassId(Integer.parseInt(bo[4].toString()));
												to.setSubjectId(Integer.parseInt(bo[11].toString()));
												to.setStudentId(Integer.parseInt(bo[0].toString()));
												to.setClassName(bo[3].toString());
												to.setSubjectCode(bo[13].toString()+"("+ bo[12]+")");
												if(bo[21]!=null)
												to.setNewMark3(bo[21].toString().trim());
												if(bo[6]!=null){
													to.setOldMark3(bo[6].toString().trim());
												}
												to.setStudentName(bo[5].toString());
												to.setRegisterNo(bo[1].toString());
												to.setComment(form.getRevaluation());
												if(bo[21] !=null && bo[6]!=null ){
													 if(Integer.parseInt(bo[21].toString()) == Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[21].toString()));
														to.setDiffOfMarks3(diffOfMarks);
													}else if(Integer.parseInt(bo[21].toString())> Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[21].toString())- Integer.parseInt(bo[6].toString()));
														to.setDiffOfMarks3("+"+diffOfMarks);
													}else if(Integer.parseInt(bo[21].toString())< Integer.parseInt(bo[6].toString())){
														String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[21].toString()));
														to.setDiffOfMarks3("-"+diffOfMarks);
													}
												}
												if(bo[20]!=null && !bo[20].toString().isEmpty()){
													if(bo[20].equals(true)){
														to.setGracedMark3(true);
													}else{
														to.setGracedMark3(false);
													}
												}else{
													to.setGracedMark3(false);
												}
												to.setCourseId(Integer.parseInt(bo[17].toString()));
												to.setSchemeNumber(Integer.parseInt(bo[18].toString()));
												to.setIsUpdated(false);
												subMap.put(Integer.valueOf(bo[11].toString()),to);
												mainMap.put(Integer.parseInt(bo[0].toString()),subMap);
											}
											
											
										 }//evaluator check over
								    }// count check over
									  
								    
						}//================= main map already created data check over
						
						
						 // addding fresh map to here with fresh data to main map
						else{
							//if studentId is not their in mainMap and evaluatior is null
							if(type.equalsIgnoreCase("null")){
								//if subject is having no evaluator
								subMap = new HashMap<Integer, RevaluationMarksUpdateTo>();
								RevaluationMarksUpdateTo to= new RevaluationMarksUpdateTo();
											to.setStudentId(Integer.parseInt(bo[0].toString()));
											to.setRegisterNo(bo[1].toString());
											to.setClassName(bo[3].toString());
											to.setClassId(Integer.parseInt(bo[4].toString()));
											to.setStudentName(bo[5].toString());
											if(bo[6]!=null){
												to.setOldMarks(bo[6].toString().trim());
											}
											if(bo[10]!=null){
											to.setNewMarks(bo[10].toString().trim());
											to.setMarks(bo[10].toString().trim());
											}
											to.setSubjectId(Integer.parseInt(bo[11].toString()));
											to.setExamRevaluationAppId(Integer.parseInt(bo[14].toString()));
											to.setSubjectCode(bo[13].toString()+"("+ bo[12]+")");
											to.setExamMarksEntryIdForNoEvl(Integer.parseInt(bo[15].toString()));
											to.setExamMarksEntryDetailsIdForNoEvl(Integer.parseInt(bo[16].toString()));
											to.setCourseId(Integer.parseInt(bo[17].toString()));
											to.setSchemeNumber(Integer.parseInt(bo[18].toString()));
											if(bo[19]!=null)
											to.setThirdEvlMarks(bo[19].toString());
											to.setComment(form.getRevaluation());
											if(bo[10] !=null && bo[6]!=null ){
												if(form.getRevaluation().equalsIgnoreCase(rev)){
													String avgMarks= String.valueOf( Math.ceil((Float.parseFloat(bo[6].toString().trim()) + Float.parseFloat(bo[10].toString().trim()))/ 2));
													avgMarks=avgMarks.substring(0, (avgMarks.length() - 2));
													if(form.getOption().equalsIgnoreCase("NotUpdated")){
														 to.setAvgMarks(avgMarks);
														}else if(form.getOption().equalsIgnoreCase("Requested_for_thirdEvaluation")){
															//to.setAvgMarks(avgMarks);
														}
													to.setOldAvgMarks(avgMarks);
												}	
												 if(Integer.parseInt(bo[10].toString().trim())== Integer.parseInt(bo[6].toString().trim())){
													String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString().trim()) - Integer.parseInt(bo[10].toString().trim()));
													to.setDiffOfMarks(diffOfMarks);
												}else if(Integer.parseInt(bo[10].toString().trim())> Integer.parseInt(bo[6].toString().trim())){
													String diffOfMarks=String.valueOf(Integer.parseInt(bo[10].toString().trim())- Integer.parseInt(bo[6].toString().trim()));
													to.setDiffOfMarks("+"+diffOfMarks);
												}else if(Integer.parseInt(bo[10].toString().trim())< Integer.parseInt(bo[6].toString().trim())){
													String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString().trim()) - Integer.parseInt(bo[10].toString().trim()));
													to.setDiffOfMarks("-"+diffOfMarks);
												}
											}
											if(bo[20]!=null && !bo[20].toString().isEmpty()){
												if(bo[20].equals(true)){
													to.setGracedMark(true);
												}else{
													to.setGracedMark(false);
												}
											}else{
												to.setGracedMark(false);
											}
											to.setIsUpdated(false);
											subMap.put(Integer.valueOf(bo[11].toString()),to);
											mainMap.put(Integer.parseInt(bo[0].toString()),subMap);
										}
							else{
								// evaluatior are there
								//if subject is having one evaluator
								if(type.equalsIgnoreCase("1")){
									subMap = new HashMap<Integer, RevaluationMarksUpdateTo>();
									RevaluationMarksUpdateTo to= new RevaluationMarksUpdateTo();
									
									to.setExamMarksEntryId(Integer.parseInt(bo[15].toString()));
									to.setExamMarksEntryDetailsId(Integer.parseInt(bo[16].toString()));
									to.setExamRevaluationAppIdForEvL1(Integer.parseInt(bo[14].toString()));
									to.setClassId(Integer.parseInt(bo[4].toString()));
									to.setSubjectId(Integer.parseInt(bo[11].toString()));
									to.setStudentId(Integer.parseInt(bo[0].toString()));
									to.setClassName(bo[3].toString());
									to.setSubjectCode(bo[13].toString()+"("+ bo[12]+")");
									if(bo[8]!=null){
									to.setNewMark1(bo[8].toString().trim());
									to.setMarks(bo[8].toString().trim());
									}
									if(bo[6]!=null){
										to.setOldMark1(bo[6].toString().trim());
									}
									to.setStudentName(bo[5].toString());
									to.setRegisterNo(bo[1].toString());
									to.setComment(form.getRevaluation());
									if(bo[8]!=null && bo[6]!=null){
										 if(Integer.parseInt(bo[8].toString())== Integer.parseInt(bo[6].toString())){
											String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[8].toString()));
											to.setDiffOfMarks1(diffOfMarks);
										}else if(Integer.parseInt(bo[8].toString())> Integer.parseInt(bo[6].toString())){
											String diffOfMarks=String.valueOf(Integer.parseInt(bo[8].toString())- Integer.parseInt(bo[6].toString()));
											to.setDiffOfMarks1("+"+diffOfMarks);
										}else if(Integer.parseInt(bo[8].toString())< Integer.parseInt(bo[6].toString())){
											String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[8].toString()));
											to.setDiffOfMarks1("-"+diffOfMarks);
										}
									}
									if(bo[20]!=null && !bo[20].toString().isEmpty()){
										if(bo[20].equals(true)){
											to.setGracedMark1(true);
										}else{
											to.setGracedMark1(false);
										}
									}else{
										to.setGracedMark1(false);
									}
									to.setCourseId(Integer.parseInt(bo[17].toString()));
									to.setSchemeNumber(Integer.parseInt(bo[18].toString()));
									to.setIsUpdated(false);
									subMap.put(Integer.valueOf(bo[11].toString()),to);
									mainMap.put(Integer.parseInt(bo[0].toString()),subMap);
								}
								//if subject is having two evaluator
								else if(type.equalsIgnoreCase("2")){
									subMap = new HashMap<Integer, RevaluationMarksUpdateTo>();
									RevaluationMarksUpdateTo to= new RevaluationMarksUpdateTo();
									
									to.setExamRevaluationAppIdForEvL2(Integer.parseInt(bo[14].toString()));
									to.setExamMarksEntryIdForSecondEvl2(Integer.parseInt(bo[15].toString()));
						    		to.setExamMarksEntryDetailsIdForSecondEvl2(Integer.parseInt(bo[16].toString()));
									to.setClassId(Integer.parseInt(bo[4].toString()));
									to.setSubjectId(Integer.parseInt(bo[11].toString()));
									to.setStudentId(Integer.parseInt(bo[0].toString()));
									to.setClassName(bo[3].toString());
									to.setSubjectCode(bo[13].toString()+"("+ bo[12]+")");
									if(bo[9]!=null)
									to.setNewMark2(bo[9].toString().trim());
									if(bo[6]!=null){
										to.setOldMark2(bo[6].toString().trim());
									}
									to.setStudentName(bo[5].toString());
									to.setRegisterNo(bo[1].toString());
									to.setComment(form.getRevaluation());
									if(bo[9] !=null && bo[6]!=null ){
										 if(Integer.parseInt(bo[9].toString()) == Integer.parseInt(bo[6].toString())){
											String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[9].toString()));
											to.setDiffOfMarks2(diffOfMarks);
										}else if(Integer.parseInt(bo[9].toString())> Integer.parseInt(bo[6].toString())){
											String diffOfMarks=String.valueOf(Integer.parseInt(bo[9].toString())- Integer.parseInt(bo[6].toString()));
											to.setDiffOfMarks2("+"+diffOfMarks);
										}else if(Integer.parseInt(bo[9].toString())< Integer.parseInt(bo[6].toString())){
											String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[9].toString()));
											to.setDiffOfMarks2("-"+diffOfMarks);
										}
									}
									if(bo[20]!=null && !bo[20].toString().isEmpty()){
										if(bo[20].equals(true)){
											to.setGracedMark2(true);
										}else{
											to.setGracedMark2(false);
										}
									}else{
										to.setGracedMark2(false);
									}
									to.setCourseId(Integer.parseInt(bo[17].toString()));
									to.setSchemeNumber(Integer.parseInt(bo[18].toString()));
									to.setIsUpdated(false);
									subMap.put(Integer.valueOf(bo[11].toString()),to);
									mainMap.put(Integer.parseInt(bo[0].toString()),subMap);
								}
								//if subject is having three evaluator
								else if(type.equalsIgnoreCase("3")){
									subMap = new HashMap<Integer, RevaluationMarksUpdateTo>();
									RevaluationMarksUpdateTo to= new RevaluationMarksUpdateTo();
									
									to.setExamRevaluationAppIdForEvL3(Integer.parseInt(bo[14].toString()));
									to.setExamMarksEntryIdForThirdEvl3(Integer.parseInt(bo[15].toString()));
						    		to.setExamMarksEntryDetailsIdForThirdEvl3(Integer.parseInt(bo[16].toString()));
									to.setClassId(Integer.parseInt(bo[4].toString()));
									to.setSubjectId(Integer.parseInt(bo[11].toString()));
									to.setStudentId(Integer.parseInt(bo[0].toString()));
									to.setClassName(bo[3].toString());
									to.setSubjectCode(bo[13].toString()+"("+ bo[12]+")");
									if(bo[21]!=null)
									to.setNewMark3(bo[21].toString().trim());
									if(bo[6]!=null){
										to.setOldMark3(bo[6].toString().trim());
									}
									to.setStudentName(bo[5].toString());
									to.setRegisterNo(bo[1].toString());
									to.setComment(form.getRevaluation());
									if(bo[21] !=null && bo[6]!=null ){
										 if(Integer.parseInt(bo[21].toString()) == Integer.parseInt(bo[6].toString())){
											String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[21].toString()));
											to.setDiffOfMarks3(diffOfMarks);
										}else if(Integer.parseInt(bo[21].toString())> Integer.parseInt(bo[6].toString())){
											String diffOfMarks=String.valueOf(Integer.parseInt(bo[21].toString())- Integer.parseInt(bo[6].toString()));
											to.setDiffOfMarks3("+"+diffOfMarks);
										}else if(Integer.parseInt(bo[21].toString())< Integer.parseInt(bo[6].toString())){
											String diffOfMarks=String.valueOf(Integer.parseInt(bo[6].toString()) - Integer.parseInt(bo[21].toString()));
											to.setDiffOfMarks3("-"+diffOfMarks);
										}
									}
									if(bo[20]!=null && !bo[20].toString().isEmpty()){
										if(bo[20].equals(true)){
											to.setGracedMark3(true);
										}else{
											to.setGracedMark3(false);
										}
									}else{
										to.setGracedMark3(false);
									}
									to.setCourseId(Integer.parseInt(bo[17].toString()));
									to.setSchemeNumber(Integer.parseInt(bo[18].toString()));
									to.setIsUpdated(false);
									subMap.put(Integer.valueOf(bo[11].toString()),to);
									mainMap.put(Integer.parseInt(bo[0].toString()),subMap);
								}
							 }//===========evaluator check close
						  }// add fresh main map over
					  }//obj null check over
				    }//obj while over
				 }
				
		 return mainMap;
		}
		
		public List<RevaluationMarksUpdateTo> convertMaptoList(Map<Integer, Map<Integer,RevaluationMarksUpdateTo>> map,RevaluationMarksUpdateForm form) throws Exception{
			 List<RevaluationMarksUpdateTo> verificationList=new ArrayList<RevaluationMarksUpdateTo>();
			 List<RevaluationMarksUpdateTo> verificationListHavingMarks=new ArrayList<RevaluationMarksUpdateTo>();
			 List<RevaluationMarksUpdateTo> verificationListNotHavingMarks=new ArrayList<RevaluationMarksUpdateTo>();
			 Iterator<Entry<Integer, Map<Integer,RevaluationMarksUpdateTo>>> iterator = map.entrySet().iterator();
			 while (iterator.hasNext()) {
				Map.Entry<Integer, Map<Integer,RevaluationMarksUpdateTo>> entry = (Map.Entry<Integer, Map<Integer,RevaluationMarksUpdateTo>>) iterator.next();
				Map<Integer,RevaluationMarksUpdateTo> subMap=entry.getValue();
				
				Iterator<Entry<Integer,RevaluationMarksUpdateTo>> iterator1 = subMap.entrySet().iterator();
				while (iterator1.hasNext()) {
					Map.Entry<Integer,RevaluationMarksUpdateTo> entry1 = (Map.Entry<Integer,RevaluationMarksUpdateTo>) iterator1.next();
					RevaluationMarksUpdateTo to=entry1.getValue();
					verificationList.add(to);
					}
				}
			 Iterator<RevaluationMarksUpdateTo> iterator1 = verificationList.iterator();
				while (iterator1.hasNext()) {
					RevaluationMarksUpdateTo to1 = (RevaluationMarksUpdateTo) iterator1.next();
						if(to1.getMarks()!=null && !to1.getMarks().isEmpty()){
			 				verificationListHavingMarks.add(to1);
						}else{
							verificationListNotHavingMarks.add(to1);
						}
				}
				Collections.sort(verificationListHavingMarks);
				if(verificationListHavingMarks ==null || verificationListHavingMarks.isEmpty()){
						form.setVerificationListHavingMarksForUpdateButton(null);
					}else{
						form.setVerificationListHavingMarksForUpdateButton(verificationListHavingMarks);
					}
				Collections.sort(verificationListNotHavingMarks);
				verificationListHavingMarks.addAll(verificationListNotHavingMarks);
				return verificationListHavingMarks;
			}

		/**
		 * @param form
		 * @return
		 * @throws Exception
		 */
		//save the old marks in retotaling time
		public List<MarksEntryCorrectionDetails> convertTotoBoListForRetotaling(RevaluationMarksUpdateForm form) throws Exception {
			List<MarksEntryCorrectionDetails> boList=new ArrayList<MarksEntryCorrectionDetails>();
					
					
				if(form.getExamMarksEntryIdForNoEvl() > 0){
					MarksEntryCorrectionDetails bo=new MarksEntryCorrectionDetails();
						MarksEntry marksEntry=new MarksEntry();
						marksEntry.setId(form.getExamMarksEntryIdForNoEvl());
						bo.setMarksEntry(marksEntry);
						bo.setTheoryMarks(form.getOldMarks());
						Subject subject=new Subject();
						subject.setId(form.getSubjectid());
						bo.setSubject(subject);
						bo.setComments(form.getComment());
						bo.setCreatedBy(form.getUserId());
						bo.setCreatedDate(new Date());
						bo.setModifiedBy(form.getUserId());
						bo.setLastModifiedDate(new Date());
						boList.add(bo);
				}
				if(form.getExamMarksEntryId()> 0){
					MarksEntryCorrectionDetails bo=new MarksEntryCorrectionDetails();
					MarksEntry marksEntry=new MarksEntry();
					marksEntry.setId(form.getExamMarksEntryId());
					bo.setMarksEntry(marksEntry);
					bo.setTheoryMarks(form.getOldMark1());
					Subject subject=new Subject();
					subject.setId(form.getSubjectid());
					bo.setSubject(subject);
					bo.setComments(form.getComment());
					bo.setCreatedBy(form.getUserId());
					bo.setCreatedDate(new Date());
					bo.setModifiedBy(form.getUserId());
					bo.setLastModifiedDate(new Date());
					boList.add(bo);
					
				}
				if(form.getExamMarksEntryIdForSecondEvl2()> 0){
					MarksEntryCorrectionDetails bo=new MarksEntryCorrectionDetails();
					MarksEntry marksEntry=new MarksEntry();
					marksEntry.setId(form.getExamMarksEntryIdForSecondEvl2());
					bo.setMarksEntry(marksEntry);
					bo.setTheoryMarks(form.getOldMark2());
					Subject subject=new Subject();
					subject.setId(form.getSubjectid());
					bo.setSubject(subject);
					bo.setComments(form.getComment());
					bo.setCreatedBy(form.getUserId());
					bo.setCreatedDate(new Date());
					bo.setModifiedBy(form.getUserId());
					bo.setLastModifiedDate(new Date());
					boList.add(bo);
				}
			
			return boList;
		}
		
		/**
		 * @param form
		 * @return
		 * @throws Exception
		 */
		//save the old marks in revaluation time
		public List<MarksEntryCorrectionDetails> convertTotoBoListForRevaluation(RevaluationMarksUpdateForm form) throws Exception {
			List<MarksEntryCorrectionDetails> boList=new ArrayList<MarksEntryCorrectionDetails>();
				if(form.getExamMarksEntryIdForNoEvl() > 0){
					MarksEntryCorrectionDetails bo=new MarksEntryCorrectionDetails();
						MarksEntry marksEntry=new MarksEntry();
						marksEntry.setId(form.getExamMarksEntryIdForNoEvl());
						bo.setMarksEntry(marksEntry);
						bo.setTheoryMarks(form.getOldMarks());
						Subject subject=new Subject();
						subject.setId(form.getSubjectid());
						bo.setSubject(subject);
						bo.setComments(form.getComment());
						bo.setCreatedBy(form.getUserId());
						bo.setCreatedDate(new Date());
						bo.setModifiedBy(form.getUserId());
						bo.setLastModifiedDate(new Date());
						boList.add(bo);
				}
			return boList;
		}
		
		public List<MarksEntryCorrectionDetails> convertTotoBoListForUpdateAll(RevaluationMarksUpdateForm form) throws Exception {
			List<MarksEntryCorrectionDetails> boList=new ArrayList<MarksEntryCorrectionDetails>();
			Iterator<RevaluationMarksUpdateTo> iterator = form.getStudentDetailsList().iterator();
			Map<Integer, List<Integer>> studentListClassWise = new HashMap<Integer, List<Integer>>();
			List<Integer> studentList = null;
			List<Integer> classList = new ArrayList<Integer>();
			
			while (iterator.hasNext()) {
				RevaluationMarksUpdateTo to = (RevaluationMarksUpdateTo) iterator.next();
				if(to.getMarks()!=null && !to.getMarks().isEmpty()){
				  if(!to.getIsUpdated()){	
					if(to.getExamMarksEntryIdForNoEvl() > 0){
						if( (to.getOldMarks()!=null && !to.getOldMarks().isEmpty())   && (to.getNewMarks()!=null && !to.getNewMarks().isEmpty())){
							if( Integer.parseInt(to.getOldMarks()) != Integer.parseInt(to.getNewMarks())){	
								MarksEntryCorrectionDetails bo=new MarksEntryCorrectionDetails();
									MarksEntry marksEntry=new MarksEntry();
									marksEntry.setId(to.getExamMarksEntryIdForNoEvl());
									bo.setMarksEntry(marksEntry);
									bo.setTheoryMarks(to.getOldMarks());
									Subject subject=new Subject();
									subject.setId(to.getSubjectId());
									bo.setSubject(subject);
									bo.setComments(to.getComment());
									bo.setCreatedBy(form.getUserId());
									bo.setCreatedDate(new Date());
									bo.setModifiedBy(form.getUserId());
									bo.setLastModifiedDate(new Date());
									boList.add(bo);
							
						}
					}
				}
				if( (to.getOldMark1()!=null && !to.getOldMark1().isEmpty()) && (to.getOldMark2() !=null && !to.getOldMark2().isEmpty()) && (to.getNewMark1() !=null && !to.getNewMark1().isEmpty()) && (to.getNewMark2() !=null && !to.getNewMark2().isEmpty())){
				if( (Integer.parseInt(to.getOldMark1()) != Integer.parseInt(to.getNewMark1()))  ||  (Integer.parseInt(to.getOldMark2()) != Integer.parseInt(to.getNewMark2()))){	
							if(to.getExamMarksEntryId()> 0){
								MarksEntryCorrectionDetails bo=new MarksEntryCorrectionDetails();
								MarksEntry marksEntry=new MarksEntry();
								marksEntry.setId(to.getExamMarksEntryId());
								bo.setMarksEntry(marksEntry);
								bo.setTheoryMarks(to.getOldMark1());
								Subject subject=new Subject();
								subject.setId(to.getSubjectId());
								bo.setSubject(subject);
								bo.setComments(to.getComment());
								bo.setCreatedBy(form.getUserId());
								bo.setCreatedDate(new Date());
								bo.setModifiedBy(form.getUserId());
								bo.setLastModifiedDate(new Date());
								boList.add(bo);
								
							}
							if(to.getExamMarksEntryIdForSecondEvl2()> 0){
								MarksEntryCorrectionDetails bo=new MarksEntryCorrectionDetails();
								MarksEntry marksEntry=new MarksEntry();
								marksEntry.setId(to.getExamMarksEntryIdForSecondEvl2());
								bo.setMarksEntry(marksEntry);
								bo.setTheoryMarks(to.getOldMark2());
								Subject subject=new Subject();
								subject.setId(to.getSubjectId());
								bo.setSubject(subject);
								bo.setComments(to.getComment());
								bo.setCreatedBy(form.getUserId());
								bo.setCreatedDate(new Date());
								bo.setModifiedBy(form.getUserId());
								bo.setLastModifiedDate(new Date());
								boList.add(bo);
								}
						}
				  }
					// code added by Nagarjuna
					
					boolean runProcess = false;
					if(to.getOldMarks() != null && !to.getOldMarks().isEmpty()
							&& to.getNewMarks() != null && !to.getNewMarks().isEmpty()){
						if(Integer.parseInt(to.getOldMarks())!=Integer.parseInt(to.getNewMarks())){
							runProcess = true;
						}
					}
					if(!runProcess && to.getOldMark1() != null && !to.getOldMark1().isEmpty()
							&& to.getNewMark1() != null && !to.getNewMark1().isEmpty()){
						if(Integer.parseInt(to.getOldMark1())!=Integer.parseInt(to.getNewMark1())){
							runProcess = true;
						}
					}
					if(!runProcess && to.getOldMark2() != null && !to.getOldMark2().isEmpty()
							&& to.getNewMark2() != null && !to.getNewMark2().isEmpty()){
						if(Integer.parseInt(to.getOldMark2())!=Integer.parseInt(to.getNewMark2())){
							runProcess = true;
						}
					}
					if(runProcess){
						if(studentListClassWise.containsKey(to.getClassId())){
							studentList = studentListClassWise.remove(to.getClassId());
						}else{
							studentList = new ArrayList<Integer>();
						}
						studentList.add(to.getStudentId());
						studentListClassWise.put(to.getClassId(), studentList);
						classList.add(to.getClassId());
					}
				form.setClassList(classList);
				form.setStudentListClassWise(studentListClassWise);
				}
			  }
			}
			return boList;
		}


		/**
		 * @param revaluationMarksUpdateForm
		 * @return
		 */
		public String getQueryForStudentClassDetails(RevaluationMarksUpdateForm revaluationMarksUpdateForm) {
			
			return "select classwise.curriculumSchemeDuration.academicYear," +
			" c.year,classes" +
			" from ExamDefinition e" +
			" join e.courseSchemeDetails courseDetails" +
			" join courseDetails.course.classes classes" +
			" join classes.classSchemewises classwise" +
			" join classwise.curriculumSchemeDuration.curriculumScheme c" +
			" where  e.delIsActive=1 and e.id=" +revaluationMarksUpdateForm.getExamId()+
			" and classes.termNumber=courseDetails.schemeNo" +
			" and classes.isActive=1 and classes.id=" +revaluationMarksUpdateForm.getClassid()+
			" order by classes.name";
		}
		/**
		 * @param revaluationMarksUpdateForm
		 * @return
		 */
		public String getQueryForStudentClassDetailsForAllClasses(RevaluationMarksUpdateForm revaluationMarksUpdateForm) {
			
			return "select classwise.curriculumSchemeDuration.academicYear," +
			" c.year,classes" +
			" from ExamDefinition e" +
			" join e.courseSchemeDetails courseDetails" +
			" join courseDetails.course.classes classes" +
			" join classes.classSchemewises classwise" +
			" join classwise.curriculumSchemeDuration.curriculumScheme c" +
			" where  e.delIsActive=1 and e.id=" +revaluationMarksUpdateForm.getExamId()+
			" and classes.termNumber=courseDetails.schemeNo" +
			" and classes.isActive=1 and classes.id in(:classesList)"+
			" order by classes.name";
		}
		/**
		 * @param list
		 * @return
		 * @throws Exception
		 */
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
					to.setChecked("on");
					mainList.add(to);	
				}
			}
			return mainList;
		}
		/**
		 * @param revaluationMarksUpdateForm
		 * @param classList
		 * @return
		 * @throws Exception
		 */
		public boolean calculateRegularOverAllAndSaveData(RevaluationMarksUpdateForm revaluationMarksUpdateForm,List<ClassesTO> classList) throws Exception {
			try {
				ExamDefinition exam=new ExamDefinition();
				int examId=Integer.parseInt(revaluationMarksUpdateForm.getExamId());
				exam.setId(examId);
				List<String> errorList=new ArrayList<String>();
				List<StudentFinalMarkDetails> boList=new ArrayList<StudentFinalMarkDetails>();
				IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
				if(classList!=null && !classList.isEmpty()){
					Iterator<ClassesTO> itr=classList.iterator();
					while (itr.hasNext()) {
						ClassesTO to = (ClassesTO) itr.next();
						Classes classes=new Classes();
						classes.setId(to.getId());
						//int SemNo=to.getTermNo();
						if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
//						if(to.getId()==620){// remove this
							
							Map<Integer,SubjectRuleSettingsTO> subRuleMap=NewUpdateProccessTransactionImpl.getInstance().getSubjectRuleSettingsForRegularOverAll(to.getCourseId(),to.getYear(),to.getTermNo());
							List<StudentTO> studentList=getStudentListForClass(to.getId(),revaluationMarksUpdateForm.getStudentid());
//							System.out.println("class Id:"+to.getId());
							if(studentList!=null && !studentList.isEmpty()){
								Iterator<StudentTO> sitr=studentList.iterator();
								while (sitr.hasNext()) {
									StudentTO sto=sitr.next();
									//if(sto.getId()==40780){//remove this
									//System.out.println("student Id"+sto.getId());
									if(sto.getSubjectIdList()!=null && !sto.getSubjectIdList().isEmpty()){
										Map<Integer,List<StudentMarksTO>> stuMarksMap=getStudentRegularMarks(sto.getId(),sto.getSubjectIdList(),examId,transaction, to.getId(), revaluationMarksUpdateForm);
										if(revaluationMarksUpdateForm.getErrorMessage() == null || revaluationMarksUpdateForm.getErrorMessage().isEmpty()){
											Iterator<Integer> subItr=sto.getSubjectIdList().iterator();
											while (subItr.hasNext()) {
												Integer subId = (Integer) subItr.next();
												if(subRuleMap.containsKey(subId) && stuMarksMap.containsKey(subId)){
													SubjectRuleSettingsTO subSetTo=subRuleMap.get(subId);
													List<StudentMarksTO> marksList=stuMarksMap.get(subId);
													if(subSetTo.isTheoryRegularCheck() || subSetTo.isPracticalRegularCheck()){
														StudentFinalMarkDetails bo=getRegularFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,revaluationMarksUpdateForm.getUserId());
														bo.setExam(exam);
														boList.add(bo);
													}else if((subSetTo.isTheoryAnsCheck() && subSetTo.isTheoryEvalCheck()) || (subSetTo.isPracticalAnsCheck() && subSetTo.isPracticalEvalCheck())){
														int noOfAns=0;
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfAns=noOfAns+subSetTo.getTheoryNoOfAns();
														}
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfAns=noOfAns+subSetTo.getPracticalNoOfEval();
														}
														int noOfEvaluation=0;
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfEvaluation=noOfEvaluation+subSetTo.getTheoryNoOfEval();
														}
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfEvaluation=noOfEvaluation+subSetTo.getPracticalNoOfEval();
														}
														if(marksList.size()==(noOfAns*noOfEvaluation)){
															StudentFinalMarkDetails bo=getRegularEvalAndAnsFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,revaluationMarksUpdateForm.getUserId());
															bo.setExam(exam);
															boList.add(bo);
														}else{
															errorList.add(sto.getRegisterNo()+"-"+subSetTo.getSubjectName());
														}
													}else if(subSetTo.isTheoryAnsCheck() || subSetTo.isPracticalAnsCheck()){
														int noOfAns=0;
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfAns=noOfAns+subSetTo.getTheoryNoOfAns();
														}
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfAns=noOfAns+subSetTo.getPracticalNoOfEval();
														}
														if(marksList.size()==noOfAns){
															StudentFinalMarkDetails bo=getRegularAnsFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,revaluationMarksUpdateForm.getUserId());
															bo.setExam(exam);
															boList.add(bo);
														}else{
															errorList.add(sto.getRegisterNo()+"-"+subSetTo.getSubjectName());
														}
													}else if(subSetTo.isTheoryEvalCheck() || subSetTo.isPracticalEvalCheck()){
														int noOfEvaluation=0;
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfEvaluation=noOfEvaluation+subSetTo.getTheoryNoOfEval();
														}
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfEvaluation=noOfEvaluation+subSetTo.getPracticalNoOfEval();
														}
														if(marksList.size()==noOfEvaluation){
															StudentFinalMarkDetails bo=getRegularEvalFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,revaluationMarksUpdateForm.getUserId());
															bo.setExam(exam);
															boList.add(bo);
														}else{
															errorList.add(sto.getRegisterNo()+"-"+subSetTo.getSubjectName());
														}
													}
												}
											}
										}
									}
								}//remove this
								}
							}
//						}//remove THis
					//	}
					}
				}
				if(errorList.isEmpty() && (revaluationMarksUpdateForm.getErrorMessage() == null || revaluationMarksUpdateForm.getErrorMessage().isEmpty())){
					return transaction.saveRegularOverAll(boList);
				}else{
					revaluationMarksUpdateForm.setErrorList(errorList);
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
		}
		/**
		 * @param classId
		 * @return
		 * @throws Exception
		 */
		private List<StudentTO> getStudentListForClass(int classId,int studentId) throws Exception {
			List<StudentTO> studentList=new ArrayList<StudentTO>();
			String query=getCurrentClassQuery(classId,studentId);// Getting Current Class Students Query
			INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
			List<Student> currentStudentList=transaction.getDataForQuery(query);
			getFinalStudentsForCurrentClass(currentStudentList,studentList);//Adding current Class Students for StudentList
			String preQuery=getPreviousClassQuery(classId,studentId);
			List<Object[]> previousStudentList=transaction.getDataForQuery(preQuery);
			getFinalStudentsForPreviousClass(previousStudentList,studentList);
			return studentList;
		}
		/**
		 * @param classId
		 * @return
		 * @throws Exception
		 */
		private List<StudentTO> getStudentListForClass1(int classId,RevaluationMarksUpdateForm revaluationMarksUpdateForm) throws Exception {
			List<StudentTO> studentList=new ArrayList<StudentTO>();
			List<Integer> StudentList = revaluationMarksUpdateForm.getStudentListClassWise().get(classId);
			String query=getCurrentClassQuery1(classId);// Getting Current Class Students Query
			IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
			List<Student> currentStudentList=transaction.getDataByQueryForAllClasses(query,StudentList);
			getFinalStudentsForCurrentClass(currentStudentList,studentList);//Adding current Class Students for StudentList
			String preQuery=getPreviousClassQuery1(classId);
			List<Object[]> previousStudentList=transaction.getDataByQueryForAllClasses(preQuery,StudentList);
			getFinalStudentsForPreviousClass(previousStudentList,studentList);
			return studentList;
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
		/**
		 * @param classId
		 * @return
		 * @throws Exception
		 */
		private String getCurrentClassQuery(int classId,int studentId) throws Exception{
			String query="from Student s" +
					" where s.admAppln.isCancelled=0 and s.isAdmitted=1 " +
					" and s.classSchemewise.classes.id="+classId+" and s.id="+studentId;
			return query;
		}
		/**
		 * @param classId
		 * @return
		 * @throws Exception
		 */
		private String getPreviousClassQuery(int classId,int studentId)  throws Exception{
			String query="select s.id,subSet.subject,s.registerNo,s.admAppln.appliedYear from Student s" +
					" join s.studentPreviousClassesHistory classHis" +
					" join classHis.classes.classSchemewises classSchemewise" +
					" join classSchemewise.curriculumSchemeDuration cd" +
					" join s.studentSubjectGroupHistory subjHist " +
					" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
					" where s.admAppln.isCancelled=0 and s.isAdmitted=1 and subSet.isActive=1 and classHis.classes.id=" +classId+
					" and s.classSchemewise.classes.id <> "+classId+
					" and classHis.schemeNo=subjHist.schemeNo and s.id="+studentId ;
			return query;
		}
		/**
		 * @param classId
		 * @return
		 * @throws Exception
		 */
		private String getCurrentClassQuery1(int classId) throws Exception{
			String query="from Student s" +
					" where s.admAppln.isCancelled=0 and s.isAdmitted=1 " +
					" and s.classSchemewise.classes.id="+classId+" and s.id in(:classesList)";
			return query;
		}
		/**
		 * @param classId
		 * @return
		 * @throws Exception
		 */
		private String getPreviousClassQuery1(int classId)  throws Exception{
			String query="select s.id,subSet.subject,s.registerNo,s.admAppln.appliedYear from Student s" +
					" join s.studentPreviousClassesHistory classHis" +
					" join classHis.classes.classSchemewises classSchemewise" +
					" join classSchemewise.curriculumSchemeDuration cd" +
					" join s.studentSubjectGroupHistory subjHist " +
					" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
					" where s.admAppln.isCancelled=0 and s.isAdmitted=1 and subSet.isActive=1 and classHis.classes.id=" +classId+
					" and s.classSchemewise.classes.id <> "+classId+
					" and classHis.schemeNo=subjHist.schemeNo and s.id in(:classesList)" ;
			return query;
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
		 * @param studentId
		 * @param subjectIdList
		 * @param examId
		 * @param transaction
		 * @param newUpdateProccessForm 
		 * @return
		 * @throws Exception
		 */
		private Map<Integer, List<StudentMarksTO>> getStudentRegularMarks(int studentId, List<Integer> subjectIdList, 
				int examId, IRevaluationMarksUpdateTransaction transaction, int classId, RevaluationMarksUpdateForm revaluationMarksUpdateForm) throws Exception {
			return transaction.getStudentRegularMarks(studentId,subjectIdList,examId, classId, revaluationMarksUpdateForm);
		}
		/**
		 * @param subSetTo
		 * @param marksList
		 * @param to
		 * @param id
		 * @param exam
		 * @param subId
		 * @return
		 * @throws Exception
		 */
		private StudentFinalMarkDetails getRegularFinalMarkDetailsBo( SubjectRuleSettingsTO subSetTo, List<StudentMarksTO> marksList, ClassesTO to, int studentId, ExamDefinition exam, Integer subId,Classes classes,String userId) throws Exception{/*
			StudentFinalMarkDetails bo=new StudentFinalMarkDetails();
			StudentMarksTO mto=null;
			mto=marksList.get(0);
			Student student=new Student();
			student.setId(studentId);
			bo.setStudent(student);
			bo.setClasses(classes);
			Subject subject=new Subject();
			subject.setId(subId);
			bo.setSubject(subject);
			bo.setCreatedBy(userId);
			bo.setCreatedDate(new Date());
			bo.setModifiedBy(userId);
			bo.setLastModifiedDate(new Date());
			
			if(subSetTo.getTheoryEseMinMarks()>0)
				bo.setSubjectTheoryMark(String.valueOf(subSetTo.getTheoryEseMinMarks()));
			if(subSetTo.getPracticalEseMinMarks()>0)
				bo.setSubjectPracticalMark(String.valueOf(subSetTo.getPracticalEseMinMarks()));
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(StringUtils.isAlpha(mto.getTheoryMarks())){
					bo.setPassOrFail("fail");
					bo.setStudentTheoryMarks(mto.getTheoryMarks());
				}else{
					double theoryMarks=0;
					if(mto.getTheoryMarks()!=null){
					 theoryMarks= (Double.parseDouble(mto.getTheoryMarks())/subSetTo.getTheoryEseEnteredMaxMarks())*subSetTo.getTheoryEseMaxMarks();
//					 bo.setStudentTheoryMarks(String.valueOf(Math.round(theoryMarks)));
					 if(!avoidExamIds.contains(exam.getId()))
						 bo.setStudentTheoryMarks(String.valueOf(Math.round(Double.parseDouble(df.format(theoryMarks)))));
					 else
						 bo.setStudentTheoryMarks(String.valueOf(Math.round(theoryMarks)));
					 if(theoryMarks<subSetTo.getTheoryEseMinMarks())
						 bo.setPassOrFail("fail");
					 else
						 bo.setPassOrFail("pass");
					}else{
						bo.setPassOrFail("pass");
					}
				}
				
			}
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(StringUtils.isAlpha(mto.getPracticalMarks())){
					bo.setPassOrFail("fail");
					bo.setStudentPracticalMarks(mto.getPracticalMarks());
				}else{
					double PracticalMarks=0;
					if(mto.getPracticalMarks()!=null){
					 PracticalMarks= (Double.parseDouble(mto.getPracticalMarks())/subSetTo.getPracticalEseEnteredMaxMarks())*subSetTo.getPracticalEseMaxMarks();
					if(PracticalMarks<subSetTo.getPracticalEseMinMarks())
						bo.setPassOrFail("fail");
					else
						bo.setPassOrFail("pass");
					if(!avoidExamIds.contains(exam.getId()))
						bo.setStudentPracticalMarks(String.valueOf(Math.round(Double.parseDouble(df.format(PracticalMarks)))));
					else
						bo.setStudentPracticalMarks(String.valueOf(Math.round(PracticalMarks)));
						
					}else{
						bo.setPassOrFail("pass");
					}
				}
			}
			
			return bo;
			
		*/
			
		
		

			StudentFinalMarkDetails bo=new StudentFinalMarkDetails();
			//StudentMarksTO mto=null;
			//mto=marksList.get(0);
			Student student=new Student();
			student.setId(studentId);
			bo.setStudent(student);
			bo.setClasses(classes);
			Subject subject=new Subject();
			subject.setId(subId);
			bo.setSubject(subject);
			bo.setCreatedBy(userId);
			bo.setCreatedDate(new Date());
			bo.setModifiedBy(userId);
			bo.setLastModifiedDate(new Date());
			
			
			
			
			if(subSetTo.getTheoryEseMinMarks()>0)
				bo.setSubjectTheoryMark(String.valueOf(subSetTo.getTheoryEseMinMarks()));
			if(subSetTo.getPracticalEseMinMarks()>0)
				bo.setSubjectPracticalMark(String.valueOf(subSetTo.getPracticalEseMinMarks()));
			
			//raghu
			if(marksList!=null && !marksList.isEmpty()){
				Iterator<StudentMarksTO> itr=marksList.iterator();
				while (itr.hasNext()) {
					StudentMarksTO mto = (StudentMarksTO) itr.next();
			
			
			
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(StringUtils.isAlpha(mto.getTheoryMarks())){
					bo.setPassOrFail("fail");
					bo.setStudentTheoryMarks(mto.getTheoryMarks());
				}else{
					double theoryMarks=0;
					if(mto.getTheoryMarks()!=null){
					 theoryMarks= (Double.parseDouble(mto.getTheoryMarks().trim())/subSetTo.getTheoryEseEnteredMaxMarks())*subSetTo.getTheoryEseMaxMarks();
//					 bo.setStudentTheoryMarks(String.valueOf(Math.round(theoryMarks)));
					 if(!avoidExamIds.contains(exam.getId()))
						 bo.setStudentTheoryMarks(String.valueOf(Math.round(Double.parseDouble(df.format(theoryMarks)))));
					 else
						 bo.setStudentTheoryMarks(String.valueOf(Math.round(theoryMarks)));
					 if(theoryMarks<subSetTo.getTheoryEseMinMarks())
						 bo.setPassOrFail("fail");
					 else
						 bo.setPassOrFail("pass");
					}else{
						bo.setPassOrFail("pass");
					}
				}
				
			}
			
			
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(StringUtils.isAlpha(mto.getPracticalMarks())){
					bo.setPassOrFail("fail");
					bo.setStudentPracticalMarks(mto.getPracticalMarks());
				}else{
					double PracticalMarks=0;
					if(mto.getPracticalMarks()!=null){
					 PracticalMarks= (Double.parseDouble(mto.getPracticalMarks())/subSetTo.getPracticalEseEnteredMaxMarks())*subSetTo.getPracticalEseMaxMarks();
					if(PracticalMarks<subSetTo.getPracticalEseMinMarks())
						bo.setPassOrFail("fail");
					else
						bo.setPassOrFail("pass");
					if(!avoidExamIds.contains(exam.getId()))
						bo.setStudentPracticalMarks(String.valueOf(Math.round(Double.parseDouble(df.format(PracticalMarks)))));
					else
						bo.setStudentPracticalMarks(String.valueOf(Math.round(PracticalMarks)));
						
					}else{
						bo.setPassOrFail("pass");
					}
				}
			}
			
			// /* code added by chandra for checking the gracing
			if(mto.getIsGracing() != null && mto.getIsGracing())
				bo.setIsGracing(true);
			else
				bo.setIsGracing(false);
			// */
			
			
			
				}//while close
			}//if close
			
			
			
			
			
			return bo;
		
		
		}
		/**
		 * @param subSetTo
		 * @param marksList
		 * @param to
		 * @param studentId
		 * @param exam
		 * @param subId
		 * @param classes
		 * @param userId
		 * @return
		 */
		private StudentFinalMarkDetails getRegularEvalAndAnsFinalMarkDetailsBo(
				SubjectRuleSettingsTO subSetTo, List<StudentMarksTO> marksList,
				ClassesTO to, int studentId, ExamDefinition exam, Integer subId,
				Classes classes, String userId) {
			StudentFinalMarkDetails bo=new StudentFinalMarkDetails();
			StudentMarksTO mto=new StudentMarksTO();
			Map<String,Double> marksMap=new HashMap<String, Double>();
			boolean isPass=true;
			String theoryAlphaMarks="";
			String practicalAlphaMarks="";
			if(marksList!=null && !marksList.isEmpty()){
				Iterator<StudentMarksTO> itr=marksList.iterator();
				while (itr.hasNext()) {
					StudentMarksTO sto = (StudentMarksTO) itr.next();
					if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
						double theoryMarks=0;
						if(marksMap.containsKey(sto.getEvaId()+"_t")){
							theoryMarks=marksMap.remove(sto.getEvaId()+"_t");
						}
						if(sto.getTheoryMarks()!=null && !sto.getTheoryMarks().isEmpty()){
							if(!CommonUtil.isValidDecimal(sto.getTheoryMarks())){
								isPass=false;
								if(theoryAlphaMarks.isEmpty()){
									theoryAlphaMarks=sto.getTheoryMarks();
								}
							}else{
								theoryMarks= theoryMarks+Double.parseDouble(sto.getTheoryMarks());
							}
						}
						marksMap.put(sto.getEvaId()+"_t",theoryMarks);
					}
					if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
						double practicalMarks=0;
						if(marksMap.containsKey(sto.getEvaId()+"_p")){
							practicalMarks=marksMap.remove(sto.getEvaId()+"_p");
						}
						if(sto.getPracticalMarks()!=null && !sto.getPracticalMarks().isEmpty()){
							if(!CommonUtil.isValidDecimal(sto.getPracticalMarks())){
								isPass=false;
								if(practicalAlphaMarks.isEmpty()){
									practicalAlphaMarks=sto.getPracticalMarks();
								}
							}else{
								practicalMarks= practicalMarks+Double.parseDouble(sto.getPracticalMarks());
							}
						}
						marksMap.put(sto.getEvaId()+"_p",practicalMarks);
					}
				}
			}
			List<Double> theoryList=new ArrayList<Double>();
			List<Double> practicalList=new ArrayList<Double>();
			for (Map.Entry<String, Double> entry : marksMap.entrySet()) {
				if(entry.getKey().contains("_t")){
					theoryList.add(entry.getValue());
				}
				if(entry.getKey().contains("_p")){
					practicalList.add(entry.getValue());
				}
			}
			
			double theoryMarks=0;
			double practicalMarks=0;
			if(isPass){
				if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
					if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Highest")){
						theoryMarks=Collections.max(theoryList);
					}else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Lowest")){
						theoryMarks=Collections.min(theoryList);
					}else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Average")){
						int count=0;
						double tMarks=0;
						Iterator<Double> thItr=theoryList.iterator();
						while (thItr.hasNext()) {
							tMarks+= (Double) thItr.next();
							count+=1;
						}
						theoryMarks=tMarks/count;
					}
				}
				if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
					if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Highest")){
						practicalMarks=Collections.max(practicalList);
					}else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Lowest")){
						practicalMarks=Collections.min(practicalList);
					}else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Average")){
						int count=0;
						double pMarks=0;
						Iterator<Double> thItr=practicalList.iterator();
						while (thItr.hasNext()) {
							pMarks+= (Double) thItr.next();
							count+=1;
						}
						practicalMarks=pMarks/count;
					}
				}
			}
			mto.setTheoryMarks(String.valueOf(theoryMarks));
			mto.setPracticalMarks(String.valueOf(practicalMarks));
			Student student=new Student();
			student.setId(studentId);
			bo.setStudent(student);
			bo.setClasses(classes);
			Subject subject=new Subject();
			subject.setId(subId);
			bo.setSubject(subject);
			bo.setCreatedBy(userId);
			bo.setCreatedDate(new Date());
			bo.setModifiedBy(userId);
			bo.setLastModifiedDate(new Date());
			
			if(subSetTo.getTheoryEseMinMarks()>0)
				bo.setSubjectTheoryMark(String.valueOf(subSetTo.getTheoryEseMinMarks()));
			if(subSetTo.getPracticalEseMinMarks()>0)
				bo.setSubjectPracticalMark(String.valueOf(subSetTo.getPracticalEseMinMarks()));
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(!isPass){
					bo.setPassOrFail("fail");
					bo.setStudentTheoryMarks(theoryAlphaMarks);
				}else{
					double tMarks= (Double.parseDouble(mto.getTheoryMarks())/subSetTo.getTheoryEseEnteredMaxMarks())*subSetTo.getTheoryEseMaxMarks();
					if(tMarks<subSetTo.getTheoryEseMinMarks())
						bo.setPassOrFail("fail");
					else
						bo.setPassOrFail("pass");
					if(!avoidExamIds.contains(exam.getId())){
						bo.setStudentTheoryMarks(String.valueOf(Math.round(Double.parseDouble(df.format(tMarks)))));
					}else{
						bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
					}
				}
			}
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(!isPass){
					bo.setPassOrFail("fail");
					bo.setStudentPracticalMarks(practicalAlphaMarks);
				}else{
					double pMarks= (Double.parseDouble(mto.getPracticalMarks())/subSetTo.getPracticalEseEnteredMaxMarks())*subSetTo.getPracticalEseMaxMarks();
					if(pMarks<subSetTo.getPracticalEseMinMarks())
						bo.setPassOrFail("fail");
					else
						bo.setPassOrFail("pass");
//					
					if(!avoidExamIds.contains(exam.getId()))
						bo.setStudentPracticalMarks(String.valueOf(Math.round(Double.parseDouble(df.format(pMarks)))));
					else
						bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
				}
			}
			return bo;
		}
		/**
		 * @param subSetTo
		 * @param marksList
		 * @param to
		 * @param id
		 * @param exam
		 * @param subId
		 * @param classes
		 * @param userId
		 * @return
		 * @throws Exception
		 */
		private StudentFinalMarkDetails getRegularAnsFinalMarkDetailsBo(SubjectRuleSettingsTO subSetTo, List<StudentMarksTO> marksList, ClassesTO to, int studentId, ExamDefinition exam, 
				Integer subId, Classes classes, String userId) throws Exception {
			StudentFinalMarkDetails bo=new StudentFinalMarkDetails();
			StudentMarksTO mto=new StudentMarksTO();
			double theoryMarks=0;
			double practicalMarks=0;
			boolean isPass=true;
			String theoryAlphaMarks="";
			String practicalAlphaMarks="";
			if(marksList!=null && !marksList.isEmpty()){
				Iterator<StudentMarksTO> itr=marksList.iterator();
				while (itr.hasNext()) {
					StudentMarksTO sto = (StudentMarksTO) itr.next();
					if(sto.getTheoryMarks()!=null && !sto.getTheoryMarks().isEmpty()){
						if(!CommonUtil.isValidDecimal(sto.getTheoryMarks())){
							isPass=false;
							if(theoryAlphaMarks.isEmpty())
								theoryAlphaMarks=sto.getTheoryMarks();
						}else{
							theoryMarks=theoryMarks+Double.parseDouble(sto.getTheoryMarks());
						}
					}
					if(sto.getPracticalMarks()!=null && !sto.getPracticalMarks().isEmpty()){
						if(!CommonUtil.isValidDecimal(sto.getPracticalMarks())){
							isPass=false;
							if(practicalAlphaMarks.isEmpty())
								practicalAlphaMarks=sto.getPracticalMarks();
						}else{
							practicalMarks=practicalMarks+Double.parseDouble(sto.getPracticalMarks());
						}
					}
				}
			}
			mto.setTheoryMarks(String.valueOf(theoryMarks));
			mto.setPracticalMarks(String.valueOf(practicalMarks));
			Student student=new Student();
			student.setId(studentId);
			bo.setStudent(student);
			bo.setClasses(classes);
			Subject subject=new Subject();
			subject.setId(subId);
			bo.setSubject(subject);
			bo.setCreatedBy(userId);
			bo.setCreatedDate(new Date());
			bo.setModifiedBy(userId);
			bo.setLastModifiedDate(new Date());
			
			if(subSetTo.getTheoryEseMinMarks()>0)
				bo.setSubjectTheoryMark(String.valueOf(subSetTo.getTheoryEseMinMarks()));
			if(subSetTo.getPracticalEseMinMarks()>0)
				bo.setSubjectPracticalMark(String.valueOf(subSetTo.getPracticalEseMinMarks()));
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(!isPass){
					bo.setPassOrFail("fail");
					bo.setStudentTheoryMarks(theoryAlphaMarks);
				}else{
					double tMarks= (Double.parseDouble(mto.getTheoryMarks())/subSetTo.getTheoryEseEnteredMaxMarks())*subSetTo.getTheoryEseMaxMarks();
					if(tMarks<subSetTo.getTheoryEseMinMarks())
						bo.setPassOrFail("fail");
					else
						bo.setPassOrFail("pass");
//					bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
					if(!avoidExamIds.contains(exam.getId()))
						bo.setStudentTheoryMarks(String.valueOf(Math.round(Double.parseDouble(df.format(tMarks)))));
					else
						bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
				}
			}
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(!isPass){
					bo.setPassOrFail("fail");
					bo.setStudentPracticalMarks(practicalAlphaMarks);
				}else{
					double pMarks= (Double.parseDouble(mto.getPracticalMarks())/subSetTo.getPracticalEseEnteredMaxMarks())*subSetTo.getPracticalEseMaxMarks();
					if(pMarks<subSetTo.getPracticalEseMinMarks())
						bo.setPassOrFail("fail");
					else
						bo.setPassOrFail("pass");
//					bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
					if(!avoidExamIds.contains(exam.getId()))
						bo.setStudentPracticalMarks(String.valueOf(Math.round(Double.parseDouble(df.format(pMarks)))));
					else
						bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
						
				}
			}
			return bo;
		}
		/**
		 * @param subSetTo
		 * @param marksList
		 * @param to
		 * @param studentId
		 * @param exam
		 * @param subId
		 * @param classes
		 * @param userId
		 * @return
		 * @throws Exception
		 */
		private StudentFinalMarkDetails getRegularEvalFinalMarkDetailsBo(SubjectRuleSettingsTO subSetTo, List<StudentMarksTO> marksList, ClassesTO to, int studentId, ExamDefinition exam, Integer subId, Classes classes, String userId) throws Exception {/*
			StudentFinalMarkDetails bo=new StudentFinalMarkDetails();
			StudentMarksTO mto=new StudentMarksTO();
			double theoryMarks=0;
			double practicalMarks=0;
			boolean isPass=true;
			List<Double> theoryList=new ArrayList<Double>();
			List<Double> practicalList=new ArrayList<Double>();
			int count=0;
			String theoryAlphaMarks="";
			String practicalAlphaMarks="";
			if(marksList!=null && !marksList.isEmpty()){
				Iterator<StudentMarksTO> itr=marksList.iterator();
				while (itr.hasNext()) {
					StudentMarksTO sto = (StudentMarksTO) itr.next();
					count+=1;
					if(sto.getTheoryMarks()!=null && !sto.getTheoryMarks().isEmpty()){
						if(!CommonUtil.isValidDecimal(sto.getTheoryMarks())){
							isPass=false;
							if(theoryAlphaMarks.isEmpty()){
								theoryAlphaMarks=sto.getTheoryMarks();
							}
						}else{
							theoryMarks=theoryMarks+Double.parseDouble(sto.getTheoryMarks());
							theoryList.add(Double.parseDouble(sto.getTheoryMarks()));
						}
					}
					if(sto.getPracticalMarks()!=null && !sto.getPracticalMarks().isEmpty()){
						if(!CommonUtil.isValidDecimal(sto.getPracticalMarks())){
							isPass=false;
							if(practicalAlphaMarks.isEmpty()){
								practicalAlphaMarks=sto.getPracticalMarks();
							}
						}else{
							practicalMarks=practicalMarks+Double.parseDouble(sto.getPracticalMarks());
							practicalList.add(Double.parseDouble(sto.getPracticalMarks()));
						}
					}
				}
			}
			if(isPass){
				if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
					if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Highest")){
						theoryMarks=Collections.max(theoryList);
					}else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Lowest")){
						theoryMarks=Collections.min(theoryList);
					}else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Average")){
						theoryMarks=theoryMarks/count;
					}
				}
				if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
					if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Highest")){
						practicalMarks=Collections.max(practicalList);
					}else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Lowest")){
						practicalMarks=Collections.min(practicalList);
					}else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Average")){
						practicalMarks=practicalMarks/count;
					}
				}
			}
			mto.setTheoryMarks(String.valueOf(theoryMarks));
			mto.setPracticalMarks(String.valueOf(practicalMarks));
			Student student=new Student();
			student.setId(studentId);
			bo.setStudent(student);
			bo.setClasses(classes);
			Subject subject=new Subject();
			subject.setId(subId);
			bo.setSubject(subject);
			bo.setCreatedBy(userId);
			bo.setCreatedDate(new Date());
			bo.setModifiedBy(userId);
			bo.setLastModifiedDate(new Date());
			
			if(subSetTo.getTheoryEseMinMarks()>0)
				bo.setSubjectTheoryMark(String.valueOf(subSetTo.getTheoryEseMinMarks()));
			if(subSetTo.getPracticalEseMinMarks()>0)
				bo.setSubjectPracticalMark(String.valueOf(subSetTo.getPracticalEseMinMarks()));
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(!isPass){
					bo.setPassOrFail("fail");
					bo.setStudentTheoryMarks(theoryAlphaMarks);
				}else{
					double tMarks= (Double.parseDouble(mto.getTheoryMarks())/subSetTo.getTheoryEseEnteredMaxMarks())*subSetTo.getTheoryEseMaxMarks();
					if(tMarks<subSetTo.getTheoryEseMinMarks())
						bo.setPassOrFail("fail");
					else
						bo.setPassOrFail("pass");
//					bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
					if(!avoidExamIds.contains(exam.getId()))
						bo.setStudentTheoryMarks(String.valueOf(Math.round(Double.parseDouble(df.format(tMarks)))));
					else
						bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
						
				}
			}
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(!isPass){
					bo.setPassOrFail("fail");
					bo.setStudentPracticalMarks(practicalAlphaMarks);
				}else{
					double pMarks= (Double.parseDouble(mto.getPracticalMarks())/subSetTo.getPracticalEseEnteredMaxMarks())*subSetTo.getPracticalEseMaxMarks();
					if(pMarks<subSetTo.getPracticalEseMinMarks())
						bo.setPassOrFail("fail");
					else
						bo.setPassOrFail("pass");
//					bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
					if(!avoidExamIds.contains(exam.getId()))
						bo.setStudentPracticalMarks(String.valueOf(Math.round(Double.parseDouble(df.format(pMarks)))));
					else
						bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
						
				}
			}
			return bo;
		*/
			
		
		
		

			StudentFinalMarkDetails bo=new StudentFinalMarkDetails();
			StudentMarksTO mto=new StudentMarksTO();
			double theoryMarks=0;
			double practicalMarks=0;
			
			double theoryMarks1=0;
			double practicalMarks1=0;
			double theoryMarks2=0;
			double practicalMarks2=0;
			double theoryMarks3=0;
			double practicalMarks3=0;
			double dif1=0;
			double dif2=0;
			
			boolean isPass=true;
			List<Double> theoryList=new ArrayList<Double>();
			List<Double> practicalList=new ArrayList<Double>();
			double reviewerTheoryMark = 0;
			double reviewerPracticalMark = 0;
			int count=0;
			int countP=0;
			int countT=0;
			String theoryAlphaMarks="";
			String practicalAlphaMarks="";
			
			boolean isGracingForEvl1=false;
			boolean isGracingForEvl2=false;
			
			if(marksList!=null && !marksList.isEmpty()){
				Iterator<StudentMarksTO> itr=marksList.iterator();
				while (itr.hasNext()) {
					StudentMarksTO sto = (StudentMarksTO) itr.next();
					count+=1;
					if(sto.getTheoryMarks()!=null && !sto.getTheoryMarks().isEmpty()){
						if(!CommonUtil.isValidDecimal(sto.getTheoryMarks())){
							isPass=false;
							if(theoryAlphaMarks.isEmpty()){
								theoryAlphaMarks=sto.getTheoryMarks();
							}
						}else{
							
							//raghu
							countT+=1;
							if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==1){
								theoryMarks1=theoryMarks1+Double.parseDouble(sto.getTheoryMarks());
								
							}else if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==2){
								theoryMarks2=theoryMarks2+Double.parseDouble(sto.getTheoryMarks());
								
							}else if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==3){
								theoryMarks3=theoryMarks3+Double.parseDouble(sto.getTheoryMarks());
								
							}
							theoryMarks=theoryMarks+Double.parseDouble(sto.getTheoryMarks());
							theoryList.add(Double.parseDouble(sto.getTheoryMarks()));
							if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty()){
								if(subSetTo.getReviewerId() == Integer.parseInt(sto.getEvaId())){
									if(Double.parseDouble(sto.getTheoryMarks()) > 0){
										reviewerTheoryMark = Double.parseDouble(sto.getTheoryMarks());
									}
								}
							}
						}
					}
					if(sto.getPracticalMarks()!=null && !sto.getPracticalMarks().isEmpty()){
						if(!CommonUtil.isValidDecimal(sto.getPracticalMarks())){
							isPass=false;
							if(practicalAlphaMarks.isEmpty()){
								practicalAlphaMarks=sto.getPracticalMarks();
							}
						}else{
							//raghu
							countP+=1;
							if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==1){
								theoryMarks1=practicalMarks1+Double.parseDouble(sto.getPracticalMarks());
								
							}else if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==2){
								theoryMarks2=practicalMarks2+Double.parseDouble(sto.getPracticalMarks());
								
							}else if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==3){
								theoryMarks3=practicalMarks3+Double.parseDouble(sto.getPracticalMarks());
								
							}
							practicalMarks=practicalMarks+Double.parseDouble(sto.getPracticalMarks());
							practicalList.add(Double.parseDouble(sto.getPracticalMarks()));
							if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty()){
								if(subSetTo.getReviewerId() == Integer.parseInt(sto.getEvaId())){
									if(Double.parseDouble(sto.getPracticalMarks()) > 0){
										reviewerPracticalMark = Double.parseDouble(sto.getPracticalMarks());
									}
								}
							}
						}
					}
					// /* code added by chandra
					if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty() && sto.getEvaId().equalsIgnoreCase("1")){
						if(sto.getIsGracing() != null && sto.getIsGracing())
							isGracingForEvl1=true;
					}else if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty() && sto.getEvaId().equalsIgnoreCase("2")){
						if(sto.getIsGracing() != null && sto.getIsGracing())
							isGracingForEvl2=true;
					}
					// */
				}
			}
			if(isPass){
				if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
					if(reviewerTheoryMark > 0){
						
						//theoryMarks = reviewerTheoryMark;
						//raghu code changed
						
						if(theoryMarks3<theoryMarks1){
							dif1=theoryMarks1-theoryMarks3;
							System.out.println("dif1=first"+dif1);
						}else{
							dif1=theoryMarks3-theoryMarks1;
							System.out.println("dif1=second"+dif1);
						}
						
						
						if(theoryMarks3<theoryMarks2){
							dif2=theoryMarks2-theoryMarks3;
							System.out.println("dif2=first"+dif2);
						}else{
							dif2=theoryMarks3-theoryMarks2;
							System.out.println("dif2=second"+dif2);
						}
						
						
						
						if(dif1==dif2){
							if(theoryMarks2<=theoryMarks1){
								theoryMarks=(theoryMarks3+theoryMarks1)/2;
							}else{
								theoryMarks=(theoryMarks3+theoryMarks2)/2;
							}
							System.out.println("theoryMarks=first"+theoryMarks);
						}else if(dif1<dif2){
							
								theoryMarks=(theoryMarks3+theoryMarks1)/2;
							
							System.out.println("theoryMarks=first"+theoryMarks);
						}else{
							
								theoryMarks=(theoryMarks2+theoryMarks3)/2;
							
							System.out.println("theoryMarks=second"+theoryMarks);
						}
						
						
					}
					else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Highest")){
						theoryMarks=Collections.max(theoryList);
					}else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Lowest")){
						theoryMarks=Collections.min(theoryList);
					}else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Average")){
						//raghu
						if(count==countT){
							theoryMarks=theoryMarks/count;
						}else{
							theoryMarks=theoryMarks/countT;
						}
						
					}
				}
				if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
					if(reviewerPracticalMark > 0){
						//practicalMarks = reviewerPracticalMark;
						//raghu code changed
						
						if(practicalMarks3<practicalMarks1){
							dif1=practicalMarks1-practicalMarks3;
							System.out.println("dif1=first"+dif1);
						}else{
							dif1=practicalMarks3-practicalMarks1;
							System.out.println("dif1=second"+dif1);
						}
						
						
						if(practicalMarks3<practicalMarks2){
							dif2=practicalMarks2-practicalMarks3;
							System.out.println("dif2=first"+dif2);
						}else{
							dif2=practicalMarks3-practicalMarks2;
							System.out.println("dif2=second"+dif2);
						}
						
						

						if(dif1==dif2){
							if(practicalMarks2<=practicalMarks1){
								practicalMarks=(practicalMarks3+practicalMarks1)/2;
							}else{
								practicalMarks=(practicalMarks3+practicalMarks2)/2;
							}
							System.out.println("practicalMarks=first"+practicalMarks);
						}else if(dif1<dif2){
							
								practicalMarks=(practicalMarks3+practicalMarks1)/2;
							
							System.out.println("practicalMarks=first"+practicalMarks);
						}else{
								practicalMarks=(practicalMarks2+practicalMarks3)/2;
							
							System.out.println("practicalMarks=second"+practicalMarks);
						}
						
					}
					else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Highest")){
						practicalMarks=Collections.max(practicalList);
					}else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Lowest")){
						practicalMarks=Collections.min(practicalList);
					}else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Average")){
						//raghu
						if(count==countP){
							practicalMarks=practicalMarks/count;
						}else{
							practicalMarks=practicalMarks/countP;
						}
						
					}
				}
			}
			mto.setTheoryMarks(String.valueOf(theoryMarks));
			mto.setPracticalMarks(String.valueOf(practicalMarks));
			Student student=new Student();
			student.setId(studentId);
			bo.setStudent(student);
			bo.setClasses(classes);
			Subject subject=new Subject();
			subject.setId(subId);
			bo.setSubject(subject);
			bo.setCreatedBy(userId);
			bo.setCreatedDate(new Date());
			bo.setModifiedBy(userId);
			bo.setLastModifiedDate(new Date());
			
			if(subSetTo.getTheoryEseMinMarks()>0)
				bo.setSubjectTheoryMark(String.valueOf(subSetTo.getTheoryEseMinMarks()));
			if(subSetTo.getPracticalEseMinMarks()>0)
				bo.setSubjectPracticalMark(String.valueOf(subSetTo.getPracticalEseMinMarks()));
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(!isPass){
					bo.setPassOrFail("fail");
					bo.setStudentTheoryMarks(theoryAlphaMarks);
				}else{
					double tMarks= (Double.parseDouble(mto.getTheoryMarks())/subSetTo.getTheoryEseEnteredMaxMarks())*subSetTo.getTheoryEseMaxMarks();
					if(tMarks<subSetTo.getTheoryEseMinMarks())
						bo.setPassOrFail("fail");
					else
						bo.setPassOrFail("pass");
//					bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
					if(!avoidExamIds.contains(exam.getId()))
						bo.setStudentTheoryMarks(String.valueOf(Math.round(Double.parseDouble(df.format(tMarks)))));
					else
						bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
						
				}
			}
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(!isPass){
					bo.setPassOrFail("fail");
					bo.setStudentPracticalMarks(practicalAlphaMarks);
				}else{
					double pMarks= (Double.parseDouble(mto.getPracticalMarks())/subSetTo.getPracticalEseEnteredMaxMarks())*subSetTo.getPracticalEseMaxMarks();
					if(pMarks<subSetTo.getPracticalEseMinMarks())
						bo.setPassOrFail("fail");
					else
						bo.setPassOrFail("pass");
//					bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
					if(!avoidExamIds.contains(exam.getId()))
						bo.setStudentPracticalMarks(String.valueOf(Math.round(Double.parseDouble(df.format(pMarks)))));
					else
						bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
						
				}
			}
			// /* code added by chandra
			if(isGracingForEvl1==true || isGracingForEvl2==true){
				bo.setIsGracing(true);
			}else{
				bo.setIsGracing(false);
			}
			// */
			return bo;
		
		
		
		
		}


		/**
		 * @param revaluationMarksUpdateForm
		 * @param classList 
		 * @throws Exception
		 */
		public void updatePassOrFail(RevaluationMarksUpdateForm revaluationMarksUpdateForm, List<ClassesTO> classList) throws Exception{
			try {
				List<ExamStudentPassFail> boList=new ArrayList<ExamStudentPassFail>();
				INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
				ExamUpdateProcessImpl impl = new ExamUpdateProcessImpl();
				boolean isImprovement=false;
				if(classList!=null && !classList.isEmpty()){
					Iterator<ClassesTO> itr=classList.iterator();
					while (itr.hasNext()) {
						ClassesTO to = (ClassesTO) itr.next();
						if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
							Classes classes=new Classes();
							classes.setId(to.getId());
							Map<String,SubjectMarksTO> minMarks=transaction.getMinMarksMap(to);
							List<Integer> excludedList =  impl.getExcludedFromResultSubjects(to.getCourseId(), to.getTermNo(),to.getYear());
							List<Integer> failureExcludeList = impl.getExcludedFromTotResultSubjects(to.getCourseId(), to.getTermNo(),to.getYear());
							
							boolean isMaxRecord=false;
							if(to.getCourseId()!=18){
								isMaxRecord=true;
							}
							List<StudentTO> studentList=getStudentListForClass(to.getId(),revaluationMarksUpdateForm.getStudentid());
							if(studentList!=null && !studentList.isEmpty()){
								// The Real Code Here Only Starts
								Iterator<StudentTO> stuItr=studentList.iterator();
								while (stuItr.hasNext()) {
									StudentTO studentTO = (StudentTO) stuItr.next();
//									System.out.println("pass or fail:"+studentTO.getId());
//						if(studentTO.getId()==3282){// remove this
									Map<Integer,StudentMarkDetailsTO> totSubMap=new HashMap<Integer, StudentMarkDetailsTO>();//subject and marks details to verify max or latest and keep in the map at last
									BigDecimal requiredAggrePercentage = impl.getAggregatePassPercentage(to.getCourseId());
									boolean checkTotal=true;
									double stuTotalMarks=0;
									double subTotalMarks=0;
									List<Object[]> list=transaction.getDataByStudentAndClassId(studentTO.getId(),to.getId(),studentTO.getSubjectIdList(),studentTO.getAppliedYear());
									if(list!=null && !list.isEmpty()){
										Iterator<Object[]> marksItr=list.iterator();
										while (marksItr.hasNext()) {
											Object[] objects = (Object[]) marksItr.next();
											if(objects[9]!=null && minMarks.containsKey(objects[9].toString())){
												boolean isAddTotal=true;
												if(excludedList.contains(Integer.parseInt(objects[9].toString()))){
													isAddTotal=false;
												}
												SubjectMarksTO subTo=minMarks.get(objects[9].toString());
												StudentMarkDetailsTO markDetailsTO= new StudentMarkDetailsTO();
												markDetailsTO.setAddTotal(isAddTotal);
												if(objects[4]!=null)
													markDetailsTO.setStudentId(Integer.parseInt(objects[4].toString()));
												if(objects[9]!=null)
													markDetailsTO.setSubjectId(Integer.parseInt(objects[9].toString()));
												double theoryRegMark=0;
												boolean isTheoryAlpha=false;
												if (objects[15] != null || objects[16] != null) {
													if(objects[15]!=null){
														if(!StringUtils.isAlpha(objects[15].toString()))
															theoryRegMark=theoryRegMark+Double.parseDouble(objects[15].toString());
														else
															isTheoryAlpha=true;
													}
													if(objects[16]!=null){
														if(!StringUtils.isAlpha(objects[16].toString()))
															theoryRegMark=theoryRegMark+Double.parseDouble(objects[16].toString());
														else
															isTheoryAlpha=true;
													}
												}else{
													if(objects[11]!=null){
														if(!StringUtils.isAlpha(objects[11].toString()))
															theoryRegMark=theoryRegMark+Double.parseDouble(objects[11].toString());
														else
															isTheoryAlpha=true;
													}
													if(objects[12]!=null){
														if(!StringUtils.isAlpha(objects[12].toString()))
															theoryRegMark=theoryRegMark+Double.parseDouble(objects[12].toString());
														else
															isTheoryAlpha=true;
													}
												}
												if(!isTheoryAlpha)
													markDetailsTO.setStuTheoryIntMark(String.valueOf(theoryRegMark));
												else
													markDetailsTO.setStuTheoryIntMark("AA");
												double practicalRegMark=0;
												boolean isPracticalAlpha=false;
												if (objects[17] != null || objects[18] != null) {
													if(objects[17]!=null){
														if(!StringUtils.isAlpha(objects[17].toString()))
															practicalRegMark=practicalRegMark+Double.parseDouble(objects[17].toString());
														else
															isPracticalAlpha=true;
													}if(objects[18]!=null){
														if(!StringUtils.isAlpha(objects[18].toString()))
															practicalRegMark=practicalRegMark+Double.parseDouble(objects[18].toString());
														else
															isPracticalAlpha=true;
													}
												}else{
													if(objects[13]!=null){
														if(!StringUtils.isAlpha(objects[13].toString()))
															practicalRegMark=practicalRegMark+Double.parseDouble(objects[13].toString());
														else
															isPracticalAlpha=true;
													}
													if(objects[14]!=null){
														if(!StringUtils.isAlpha(objects[14].toString()))
															practicalRegMark=practicalRegMark+Double.parseDouble(objects[14].toString());
														else
															isPracticalAlpha=true;
													}
												}
												if(!isPracticalAlpha)
													markDetailsTO.setStuPracIntMark(String.valueOf(practicalRegMark));
												else
													markDetailsTO.setStuPracIntMark("AA");
												
												if (objects[19] != null) {
													markDetailsTO.setStuTheoryRegMark(objects[19].toString());
												}
												if (objects[20] != null) {
													markDetailsTO.setStuPracRegMark(objects[20].toString());
												}
												if (objects[22] != null) {
													markDetailsTO.setIs_theory_practical(objects[22].toString());
												}
												
												//if the student has written improvement for the subject then we should check max
												isImprovement=false;
												if (objects[23] != null) {
													isImprovement=Boolean.parseBoolean(objects[23].toString());
												}
												if(isMaxRecord){
													if(totSubMap.containsKey(markDetailsTO.getSubjectId())){
														StudentMarkDetailsTO markDetailsTO2=totSubMap.remove(markDetailsTO.getSubjectId());
														StudentMarkDetailsTO maxMarks=checkMaxBetweenTOs(markDetailsTO,markDetailsTO2,subTo,subTotalMarks,stuTotalMarks,isAddTotal,isImprovement);
														totSubMap.put(markDetailsTO.getSubjectId(),maxMarks);
													}else{
														totSubMap.put(markDetailsTO.getSubjectId(),markDetailsTO);
													}
												}else{
													if(!totSubMap.containsKey(markDetailsTO.getSubjectId())){
														totSubMap.put(markDetailsTO.getSubjectId(),markDetailsTO);
													}
												}
											}
										}
										
										
										// The Real Logic comes now ( New Logic has Implemented)
										List<StudentMarkDetailsTO> totalList = new ArrayList<StudentMarkDetailsTO>(totSubMap.values());

										if(!totalList.isEmpty()){
											Iterator<StudentMarkDetailsTO> totalitr=totalList.iterator();
											while (totalitr.hasNext()) {
												StudentMarkDetailsTO markDetailsTO = totalitr .next();
												if(minMarks.containsKey(String.valueOf(markDetailsTO.getSubjectId()))){
													boolean isAddTotal=true;
													boolean isfalse=false;
													if(excludedList.contains(markDetailsTO.getSubjectId())){
														isAddTotal=false;
													}
													SubjectMarksTO subTo=minMarks.get(String.valueOf(markDetailsTO.getSubjectId()));
													// The Real Logic has to implement Here
													if(subTo.getTheoryIntMin()!=null && !subTo.getTheoryIntMin().isEmpty()){
														if (markDetailsTO.getStuTheoryIntMark() != null){
															if(StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
																isfalse=true;
																checkTotal=false;
																markDetailsTO.setIsTheoryFailed(true);
															}
															else if (Double.parseDouble(markDetailsTO.getStuTheoryIntMark()) < Double
																	.parseDouble(subTo.getTheoryIntMin())) {
																isfalse=true;
																checkTotal=false;
																markDetailsTO.setIsTheoryFailed(true);
															}
														}
														else{
															isfalse=true;
															checkTotal=false;
															markDetailsTO.setIsTheoryFailed(true);
														}
													}
													if(subTo.getTheoryRegMin()!=null && !subTo.getTheoryRegMin().isEmpty()){
														if (markDetailsTO.getStuTheoryRegMark() != null){
															if(StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
																isfalse=true;
																markDetailsTO.setIsTheoryFailed(true);
															}
															else if (Double.parseDouble(markDetailsTO.getStuTheoryRegMark()) < Double
																	.parseDouble(subTo.getTheoryRegMin())) {
																isfalse=true;
																markDetailsTO.setIsTheoryFailed(true);
															}
														}
														else{
															isfalse=true;	
															markDetailsTO.setIsTheoryFailed(true);
														}
													}
													if(subTo.getPracticalIntMin()!=null && !subTo.getPracticalIntMin().isEmpty()){
														if (markDetailsTO.getStuPracIntMark() != null){
															if(StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
																isfalse=true;
																markDetailsTO.setIsTheoryFailed(true);
															}
															else if (Double.parseDouble(markDetailsTO.getStuPracIntMark()) < Double
																	.parseDouble(subTo.getPracticalIntMin())) {
																isfalse=true;
																markDetailsTO.setIsPracticalFailed(true);
															}
														}
														else{
															isfalse=true;
															markDetailsTO.setIsPracticalFailed(true);
														}
													}
													if(subTo.getPracticalRegMin()!=null && !subTo.getPracticalRegMin().isEmpty()){
														if (markDetailsTO.getStuPracRegMark() != null){
															if(StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
																isfalse=true;
																markDetailsTO.setIsPracticalFailed(true);
															}
															else if (Double.parseDouble(markDetailsTO.getStuPracRegMark()) < Double
																	.parseDouble(subTo.getPracticalRegMin())) {
																isfalse=true;
																markDetailsTO.setIsPracticalFailed(true);
															}
														}
														else{
															isfalse=true;	
															markDetailsTO.setIsPracticalFailed(true);
														}
													}
													
													if (subTo.getFinalTheoryMin() != null) {
														if(isAddTotal){
//															if(!isMaxRecord){
																if(!totSubMap.containsKey(markDetailsTO.getSubjectId())){
																	if(subTo.getFinalTheoryMarks()!=null)
																		subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalTheoryMarks());
																	if(markDetailsTO.getStuTheoryRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
																		stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryRegMark());
																	}
																	if(markDetailsTO.getStuTheoryIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
																		stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryIntMark());
																	}
//																}
															}else{
																if(subTo.getFinalTheoryMarks()!=null)
																	subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalTheoryMarks());
																if(markDetailsTO.getStuTheoryRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
																	stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryRegMark());
																}
																if(markDetailsTO.getStuTheoryIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
																	stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryIntMark());
																}
															}
														}
														if (markDetailsTO.getStuTheoryRegMark() != null
																&& markDetailsTO.getStuTheoryIntMark() != null){
															if(StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim()) || StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim()) ){
																isfalse=true;
																markDetailsTO.setIsTheoryFailed(true);
															}
															else if (Double.parseDouble(markDetailsTO.getStuTheoryRegMark())+ 
																	Double.parseDouble(markDetailsTO.getStuTheoryIntMark()) < Double.parseDouble(subTo.getFinalTheoryMin())) {
																isfalse=true;
																markDetailsTO.setIsTheoryFailed(true);
															}
														}
													}
													if (subTo.getFinalPracticalMin() != null) {
														if(isAddTotal){
//															if(!isMaxRecord){
																if(!totSubMap.containsKey(markDetailsTO.getSubjectId())){
																	if(subTo.getFinalPracticalMarks()!=null)
																		subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalPracticalMarks());
																	if(markDetailsTO.getStuPracRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
																		stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracRegMark());
																	}
																	if(markDetailsTO.getStuPracIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
																		stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracIntMark());
																	}
//																}
															}else{
																if(subTo.getFinalPracticalMarks()!=null)
																	subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalPracticalMarks());
																if(markDetailsTO.getStuPracRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
																	stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracRegMark());
																}
																if(markDetailsTO.getStuPracIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
																	stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracIntMark());
																}
															}
														}
														if (markDetailsTO.getStuPracRegMark() != null && markDetailsTO.getStuPracIntMark() != null){
															if(StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim()) || StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
																isfalse=true;
																markDetailsTO.setIsPracticalFailed(true);
															}
															else if (Double.parseDouble(markDetailsTO.getStuPracRegMark())
																	+ Double.parseDouble(markDetailsTO.getStuPracIntMark()) < Double
																	.parseDouble(subTo.getFinalPracticalMin())) {
																isfalse=true;
																markDetailsTO.setIsPracticalFailed(true);
															}
														}
													}
													if(((markDetailsTO.getIsTheoryFailed()!=null && markDetailsTO.getIsTheoryFailed()) || (markDetailsTO.getIsPracticalFailed()!=null && markDetailsTO.getIsPracticalFailed())) && !failureExcludeList.contains(markDetailsTO.getSubjectId())){
														checkTotal=false;
													}
												}
											}
										}
										
										
										
										if(!totSubMap.isEmpty()){
											Double studentPercentage =Double.valueOf(0);
											if(subTotalMarks>0 && stuTotalMarks>0)
											 studentPercentage = Double.valueOf((stuTotalMarks * 100) / subTotalMarks);
											if (requiredAggrePercentage != null	&& studentPercentage != null) {
												if ((new BigDecimal(studentPercentage.toString()).intValue()) < (new BigDecimal(requiredAggrePercentage.toString()).intValue())) {
													checkTotal =false;	
												}
											}
											ExamStudentPassFail examStudentPassFail=new ExamStudentPassFail();
											Student student=new Student();
											student.setId(studentTO.getId());
											examStudentPassFail.setStudent(student);
											examStudentPassFail.setSchemeNo(to.getTermNo());
											examStudentPassFail.setClasses(classes);
											if(checkTotal)
												examStudentPassFail.setPassFail('P');
											else
												examStudentPassFail.setPassFail('F'); // modified by Nagarjun
											examStudentPassFail.setPercentage(new BigDecimal(studentPercentage));
											if(examStudentPassFail.getPassFail().equals('F')){
												examStudentPassFail.setResult("Fail");
											}else{
												IUpdateStudentSGPATxn iUpdateStudentSGPATxn = UpdateExamStudentSGPAImpl.getInstance();
												String result = iUpdateStudentSGPATxn.getResultClass(to.getCourseId(), Double.valueOf(studentPercentage),studentTO.getAppliedYear(),studentTO.getId());
												examStudentPassFail.setResult(result);
											}
											boList.add(examStudentPassFail);
										}
									}
//						}//remove this
								}
							}
						}
					}
				}
				impl.updatePassFail(boList);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
		}
		/**
		 * @param markDetailsTO1
		 * @param markDetailsTO2
		 * @throws Exception
		 */
		public StudentMarkDetailsTO checkMaxBetweenTOs(StudentMarkDetailsTO to1,StudentMarkDetailsTO to2,SubjectMarksTO subTo,double subTotalMarks,double stuTotalMarks,boolean isAddTotal,boolean isImprovement) throws Exception {
			
			StudentMarkDetailsTO  markDetailsTO =new StudentMarkDetailsTO();
			if(isAddTotal){
				//everything two times we have to deticate in subTotalMarks  because we are adding two times for two TO'S
				if(subTo.getFinalPracticalMarks()!=null && !subTo.getFinalPracticalMarks().isEmpty()){
					subTotalMarks=subTotalMarks-Double.parseDouble(subTo.getFinalPracticalMarks());// For To1 subraction
					subTotalMarks=subTotalMarks-Double.parseDouble(subTo.getFinalPracticalMarks());// For To2 subraction
					if (to1.getStuPracIntMark() != null && !StringUtils.isAlpha(to1.getStuPracIntMark().trim())){
						stuTotalMarks=stuTotalMarks-Double.parseDouble(to1.getStuPracIntMark());
					}
					if (to2.getStuPracIntMark() != null && !StringUtils.isAlpha(to2.getStuPracIntMark().trim())){
						stuTotalMarks=stuTotalMarks-Double.parseDouble(to2.getStuPracIntMark());
					}
					if(to1.getStuPracRegMark() != null && !StringUtils.isAlpha(to1.getStuPracRegMark().trim())){
						stuTotalMarks=stuTotalMarks-Double.parseDouble(to1.getStuPracRegMark());
					}
					if(to2.getStuPracRegMark() != null && !StringUtils.isAlpha(to2.getStuPracRegMark().trim())){
						stuTotalMarks=stuTotalMarks-Double.parseDouble(to2.getStuPracRegMark());
					}
				}
				if(subTo.getFinalTheoryMarks()!=null && !subTo.getFinalTheoryMarks().isEmpty()){
					subTotalMarks=subTotalMarks-Double.parseDouble(subTo.getFinalTheoryMarks());// For To1 subraction
					subTotalMarks=subTotalMarks-Double.parseDouble(subTo.getFinalTheoryMarks());// For To2 subraction
					if (to1.getStuTheoryIntMark() != null && !StringUtils.isAlpha(to1.getStuTheoryIntMark().trim())){
						stuTotalMarks=stuTotalMarks-Double.parseDouble(to1.getStuTheoryIntMark());
					}
					if (to2.getStuTheoryIntMark() != null && !StringUtils.isAlpha(to2.getStuTheoryIntMark().trim())){
						stuTotalMarks=stuTotalMarks-Double.parseDouble(to2.getStuTheoryIntMark());
					}
					if(to1.getStuTheoryRegMark() != null && !StringUtils.isAlpha(to1.getStuTheoryRegMark().trim())){
						stuTotalMarks=stuTotalMarks-Double.parseDouble(to1.getStuTheoryRegMark());
					}
					if(to2.getStuTheoryRegMark() != null && !StringUtils.isAlpha(to2.getStuTheoryRegMark().trim())){
						stuTotalMarks=stuTotalMarks-Double.parseDouble(to2.getStuTheoryRegMark());
					}
				}
			}
			// The Real Logic has to implement Here
			if(to1!=null && to2!=null){
				markDetailsTO.setSubjectId(to1.getSubjectId());
				markDetailsTO.setIs_theory_practical(to1.getIs_theory_practical());
				if(isImprovement){
					// If it is improvement then we have to get max between the two records
					// Theory Int Marks
					if(to1.getStuTheoryIntMark()!=null && !to1.getStuTheoryIntMark().isEmpty()){
						if(StringUtils.isAlpha(to1.getStuTheoryIntMark().trim())){
							if(to2.getStuTheoryIntMark()!=null && !to2.getStuTheoryIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryIntMark().trim())){
								markDetailsTO.setStuTheoryIntMark(to2.getStuTheoryIntMark());
							}else{
								markDetailsTO.setStuTheoryIntMark(to1.getStuTheoryIntMark());
							}
						}else{
							if(to2.getStuTheoryIntMark()!=null && !to2.getStuTheoryIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryIntMark().trim())){
								if(Double.parseDouble(to1.getStuTheoryIntMark()) < Double.parseDouble(to2.getStuTheoryIntMark()))
									markDetailsTO.setStuTheoryIntMark(to2.getStuTheoryIntMark());
								else
									markDetailsTO.setStuTheoryIntMark(to1.getStuTheoryIntMark());
							}else{
								markDetailsTO.setStuTheoryIntMark(to1.getStuTheoryIntMark());
							}
						}
					}else{
						if(to2.getStuTheoryIntMark()!=null && !to2.getStuTheoryIntMark().isEmpty()){
							markDetailsTO.setStuTheoryIntMark(to2.getStuTheoryIntMark());
						}
					}
					
					// Practical Int Mark
					if(to1.getStuPracIntMark()!=null && !to1.getStuPracIntMark().isEmpty()){
						if(StringUtils.isAlpha(to1.getStuPracIntMark().trim())){
							if(to2.getStuPracIntMark()!=null && !to2.getStuPracIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracIntMark().trim())){
								markDetailsTO.setStuPracIntMark(to2.getStuPracIntMark());
							}else{
								markDetailsTO.setStuPracIntMark(to1.getStuPracIntMark());
							}
						}else{
							if(to2.getStuPracIntMark()!=null && !to2.getStuPracIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracIntMark().trim())){
								if(Double.parseDouble(to1.getStuPracIntMark()) < Double.parseDouble(to2.getStuPracIntMark()))
									markDetailsTO.setStuPracIntMark(to2.getStuPracIntMark());
								else
									markDetailsTO.setStuPracIntMark(to1.getStuPracIntMark());
							}else{
								stuTotalMarks=stuTotalMarks-Double.parseDouble(to1.getStuPracIntMark());
								markDetailsTO.setStuPracIntMark(to1.getStuPracIntMark());
							}
						}
					}else{
						if(to2.getStuPracIntMark()!=null && !to2.getStuPracIntMark().isEmpty()){
							markDetailsTO.setStuPracIntMark(to2.getStuPracIntMark());
						}
					}
					
					// Theory Reg Mark
					if(to1.getStuTheoryRegMark()!=null && !to1.getStuTheoryRegMark().isEmpty()){
						if(StringUtils.isAlpha(to1.getStuTheoryRegMark().trim())){
							if(to2.getStuTheoryRegMark()!=null && !to2.getStuTheoryRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryRegMark().trim())){
								markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
							}else{
								markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
							}
						}else{
							if(to2.getStuTheoryRegMark()!=null && !to2.getStuTheoryRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryRegMark().trim())){
								if(Double.parseDouble(to1.getStuTheoryRegMark()) < Double.parseDouble(to2.getStuTheoryRegMark()))
									markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
								else
									markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
							}else{
								markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
							}
						}
					}else{
						if(to2.getStuTheoryRegMark()!=null && !to2.getStuTheoryRegMark().isEmpty()){
							markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
						}
					}
					
					
					// practical Reg Mark
					if(to1.getStuPracRegMark()!=null && !to1.getStuPracRegMark().isEmpty()){
						if(StringUtils.isAlpha(to1.getStuPracRegMark().trim())){
							if(to2.getStuPracRegMark()!=null && !to2.getStuPracRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracRegMark().trim())){
								markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
							}else{
								markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
							}
						}else{
							if(to2.getStuPracRegMark()!=null && !to2.getStuPracRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracRegMark().trim())){
								if(Double.parseDouble(to1.getStuPracRegMark()) < Double.parseDouble(to2.getStuPracRegMark()))
									markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
								else
									markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
							}else{
								markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
							}
						}
					}else{
						if(to2.getStuPracRegMark()!=null && !to2.getStuPracRegMark().isEmpty()){
							markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
						}
					}
				}else{
					// If it is supplementary then we have to get latest availablity data
					
					// Theory Int Marks
					if(to2.getStuTheoryIntMark()==null || to2.getStuTheoryIntMark().isEmpty()){
						markDetailsTO.setStuTheoryIntMark(to1.getStuTheoryIntMark());
					}else{
						markDetailsTO.setStuTheoryIntMark(to2.getStuTheoryIntMark());
					}
					
					// Practical Int Mark
					if(to2.getStuPracIntMark()==null || to2.getStuPracIntMark().isEmpty()){
						markDetailsTO.setStuPracIntMark(to1.getStuPracIntMark());
					}else{
						markDetailsTO.setStuPracIntMark(to2.getStuPracIntMark());
					}
					
					// Theory Reg Mark
					if(to2.getStuTheoryRegMark()==null || to2.getStuTheoryRegMark().isEmpty()){
						markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
					}else{
						markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
					}
					
					
					// practical Reg Mark
					if(to2.getStuPracRegMark()==null || to2.getStuPracRegMark().isEmpty()){
						markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
					}else{
						markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
					}
					
				}
				if(subTo.getTheoryIntMin()!=null && !subTo.getTheoryIntMin().isEmpty()){
					if (markDetailsTO.getStuTheoryIntMark() != null){
						if(StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
							markDetailsTO.setIsTheoryFailed(true);
						}
						else if (Double.parseDouble(markDetailsTO.getStuTheoryIntMark()) < Double
								.parseDouble(subTo.getTheoryIntMin())) {
							markDetailsTO.setIsTheoryFailed(true);
						}
					}
					else{
						markDetailsTO.setIsTheoryFailed(true);
					}
				}
				if(subTo.getTheoryRegMin()!=null && !subTo.getTheoryRegMin().isEmpty()){
					if (markDetailsTO.getStuTheoryRegMark() != null){
						if(StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
							markDetailsTO.setIsTheoryFailed(true);
						}
						else if (Double.parseDouble(markDetailsTO.getStuTheoryRegMark()) < Double
								.parseDouble(subTo.getTheoryRegMin())) {
							markDetailsTO.setIsTheoryFailed(true);
						}
					}
					else{
						markDetailsTO.setIsTheoryFailed(true);
					}
				}
				if(subTo.getPracticalIntMin()!=null && !subTo.getPracticalIntMin().isEmpty()){
					if (markDetailsTO.getStuPracIntMark() != null){
						if(StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
							markDetailsTO.setIsPracticalFailed(true);
						}
						else if (Double.parseDouble(markDetailsTO.getStuPracIntMark()) < Double
								.parseDouble(subTo.getPracticalIntMin())) {
							markDetailsTO.setIsPracticalFailed(true);
						}
					}
					else{
						markDetailsTO.setIsPracticalFailed(true);
					}
				}
				if(subTo.getPracticalRegMin()!=null && !subTo.getPracticalRegMin().isEmpty()){
					if (markDetailsTO.getStuPracRegMark() != null){
						if(StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
							markDetailsTO.setIsPracticalFailed(true);
						}
						else if (Double.parseDouble(markDetailsTO.getStuPracRegMark()) < Double
								.parseDouble(subTo.getPracticalRegMin())) {
							markDetailsTO.setIsPracticalFailed(true);
						}
					}
					else{
						markDetailsTO.setIsPracticalFailed(true);
					}
				}
				
				if (subTo.getFinalTheoryMin() != null) {
					if(isAddTotal){
						if(subTo.getFinalTheoryMarks()!=null)
							subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalTheoryMarks());
						if(markDetailsTO.getStuTheoryRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
							stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryRegMark());
						}
						if(markDetailsTO.getStuTheoryIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
							stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryIntMark());
						}
					}
					if (markDetailsTO.getStuTheoryRegMark() != null
							&& markDetailsTO.getStuTheoryIntMark() != null){
						if(StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim()) || StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim()) ){
							markDetailsTO.setIsTheoryFailed(true);
						}
						else if (Double.parseDouble(markDetailsTO.getStuTheoryRegMark())+ 
								Double.parseDouble(markDetailsTO.getStuTheoryIntMark()) < Double.parseDouble(subTo.getFinalTheoryMin())) {
							markDetailsTO.setIsTheoryFailed(true);
						}
					}
				 }
				if (subTo.getFinalPracticalMin() != null) {
					if(isAddTotal){
						if(subTo.getFinalPracticalMarks()!=null)
							subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalPracticalMarks());
						if(markDetailsTO.getStuPracRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
							stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracRegMark());
						}
						if(markDetailsTO.getStuPracIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
							stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracIntMark());
						}
					}
					if (markDetailsTO.getStuPracRegMark() != null && markDetailsTO.getStuPracIntMark() != null){
						if(StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim()) || StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
							markDetailsTO.setIsPracticalFailed(true);
						}
						else if (Double.parseDouble(markDetailsTO.getStuPracRegMark())
								+ Double.parseDouble(markDetailsTO.getStuPracIntMark()) < Double
								.parseDouble(subTo.getFinalPracticalMin())) {
							markDetailsTO.setIsPracticalFailed(true);
						}
					}
				}
			}
			markDetailsTO.setTempStuTotal(stuTotalMarks);
			markDetailsTO.setTempSubTotal(subTotalMarks);
			return markDetailsTO;
		}


		/**
		 * @param revaluationMarksUpdateForm
		 * @param transaction
		 * @throws Exception
		 */
		public void updateRevaluationApllicationStatus(RevaluationMarksUpdateForm revaluationMarksUpdateForm,
				IRevaluationMarksUpdateTransaction transaction) throws Exception{
			ExamRevaluationAppDetails revaluation = transaction.getExamRevaluationAppDetails(revaluationMarksUpdateForm);
			if(revaluation != null && revaluationMarksUpdateForm.getOldMarks() != null && !revaluationMarksUpdateForm.getOldMarks().isEmpty()
					&& revaluationMarksUpdateForm.getAvgMarks() != null && !revaluationMarksUpdateForm.getAvgMarks().isEmpty()){
				if(Integer.parseInt(revaluationMarksUpdateForm.getOldMarks())==Integer.parseInt(revaluationMarksUpdateForm.getAvgMarks())){
					revaluation.setStatus("No change in marks");
				}else{
					revaluation.setStatus("Marks changed and updated");
				}
				String mobileNo="";
				if(revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1()!=null 
						&& !revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
					if(revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || 
							revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
							|| revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || 
							revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
						mobileNo = "91";
					else
						mobileNo=revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1();
				}else{
					mobileNo="91";
				}
				if(revaluation.getExamRevApp() != null && revaluation.getExamRevApp().getStudent() != null && 
						revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo2()!=null && !revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
					mobileNo=mobileNo+revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo2();
				}
				revaluation.setModifiedBy(revaluationMarksUpdateForm.getUserId());
				revaluation.setLastModifiedDate(new Date());
				transaction.updateRevaluationApllicationStatus(revaluation);
				if(mobileNo.length()==12){
					UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_REVALUATION_STATUS_TEMPLATE,null);
				}
			}
		}


		/**
		 * @param revaluationMarksUpdateForm
		 * @param transaction
		 * @throws Exception
		 */
		public void updateRevaluationApllicationStatusRetoaling(
				RevaluationMarksUpdateForm revaluationMarksUpdateForm,
				IRevaluationMarksUpdateTransaction transaction) throws Exception{
			ExamRevaluationAppDetails revaluation = transaction.getExamRevaluationAppDetails(revaluationMarksUpdateForm);
			if(revaluation != null){
				boolean runProcess = false;
				if(revaluationMarksUpdateForm.getOldMarks() != null && !revaluationMarksUpdateForm.getOldMarks().isEmpty()
						&& revaluationMarksUpdateForm.getNewMarks() != null && !revaluationMarksUpdateForm.getNewMarks().isEmpty()){
					if(Integer.parseInt(revaluationMarksUpdateForm.getOldMarks())!=Integer.parseInt(revaluationMarksUpdateForm.getNewMarks())){
						runProcess = true;
					}
				}
				if(!runProcess && revaluationMarksUpdateForm.getOldMark1() != null && !revaluationMarksUpdateForm.getOldMark1().isEmpty()
						&& revaluationMarksUpdateForm.getNewMark1() != null && !revaluationMarksUpdateForm.getNewMark1().isEmpty()){
					if(Integer.parseInt(revaluationMarksUpdateForm.getOldMark1())!=Integer.parseInt(revaluationMarksUpdateForm.getNewMark1())){
						runProcess = true;
					}
				}
				if(!runProcess && revaluationMarksUpdateForm.getOldMark2() != null && !revaluationMarksUpdateForm.getOldMark2().isEmpty()
						&& revaluationMarksUpdateForm.getNewMark2() != null && !revaluationMarksUpdateForm.getNewMark2().isEmpty()){
					if(Integer.parseInt(revaluationMarksUpdateForm.getOldMark2())!=Integer.parseInt(revaluationMarksUpdateForm.getNewMark2())){
						runProcess = true;
					}
				}
				if(!runProcess){
					revaluation.setStatus("No change in marks");
				}else{
					revaluation.setStatus("Marks changed and updated");
				}
				String mobileNo="";
				if(revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1()!=null 
						&& !revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
					if(revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || 
							revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
							|| revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || 
							revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
						mobileNo = "91";
					else
						mobileNo=revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1();
				}else{
					mobileNo="91";
				}
				if(revaluation.getExamRevApp() != null && revaluation.getExamRevApp().getStudent() != null && 
						revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo2()!=null && !revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
					mobileNo=mobileNo+revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo2();
				}
				revaluation.setModifiedBy(revaluationMarksUpdateForm.getUserId());
				revaluation.setLastModifiedDate(new Date());
				transaction.updateRevaluationApllicationStatus(revaluation);
				if(mobileNo.length()==12){
					UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_REVALUATION_STATUS_TEMPLATE,null);
				}
			}
		}
		/**
		 * @param revaluationMarksUpdateForm
		 * @param classList
		 * @return
		 * @throws Exception
		 */
		public boolean calculateRegularOverAllAndSaveDataForAllClasses(RevaluationMarksUpdateForm revaluationMarksUpdateForm,List<ClassesTO> classList) throws Exception {
			try {
				ExamDefinition exam=new ExamDefinition();
				int examId=Integer.parseInt(revaluationMarksUpdateForm.getExamId());
				exam.setId(examId);
				List<String> errorList=new ArrayList<String>();
				List<StudentFinalMarkDetails> boList=new ArrayList<StudentFinalMarkDetails>();
				IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
				if(classList!=null && !classList.isEmpty()){
					Iterator<ClassesTO> itr=classList.iterator();
					while (itr.hasNext()) {
						ClassesTO to = (ClassesTO) itr.next();
						Classes classes=new Classes();
						classes.setId(to.getId());
						//int SemNo=to.getTermNo();
						if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
//						if(to.getId()==620){// remove this
							
							Map<Integer,SubjectRuleSettingsTO> subRuleMap=NewUpdateProccessTransactionImpl.getInstance().getSubjectRuleSettingsForRegularOverAll(to.getCourseId(),to.getYear(),to.getTermNo());
							List<StudentTO> studentList=getStudentListForClass1(to.getId(),revaluationMarksUpdateForm);
//							System.out.println("class Id:"+to.getId());
							if(studentList!=null && !studentList.isEmpty()){
								Iterator<StudentTO> sitr=studentList.iterator();
								while (sitr.hasNext()) {
									StudentTO sto=sitr.next();
									//if(sto.getId()==40780){//remove this
									//System.out.println("student Id"+sto.getId());
									if(sto.getSubjectIdList()!=null && !sto.getSubjectIdList().isEmpty()){
										Map<Integer,List<StudentMarksTO>> stuMarksMap=getStudentRegularMarks(sto.getId(),sto.getSubjectIdList(),examId,transaction, to.getId(), revaluationMarksUpdateForm);
										if(revaluationMarksUpdateForm.getErrorMessage() == null || revaluationMarksUpdateForm.getErrorMessage().isEmpty()){
											Iterator<Integer> subItr=sto.getSubjectIdList().iterator();
											while (subItr.hasNext()) {
												Integer subId = (Integer) subItr.next();
												if(subRuleMap.containsKey(subId) && stuMarksMap.containsKey(subId)){
													SubjectRuleSettingsTO subSetTo=subRuleMap.get(subId);
													List<StudentMarksTO> marksList=stuMarksMap.get(subId);
													if(subSetTo.isTheoryRegularCheck() || subSetTo.isPracticalRegularCheck()){
														StudentFinalMarkDetails bo=getRegularFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,revaluationMarksUpdateForm.getUserId());
														bo.setExam(exam);
														boList.add(bo);
													}else if((subSetTo.isTheoryAnsCheck() && subSetTo.isTheoryEvalCheck()) || (subSetTo.isPracticalAnsCheck() && subSetTo.isPracticalEvalCheck())){
														int noOfAns=0;
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfAns=noOfAns+subSetTo.getTheoryNoOfAns();
														}
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfAns=noOfAns+subSetTo.getPracticalNoOfEval();
														}
														int noOfEvaluation=0;
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfEvaluation=noOfEvaluation+subSetTo.getTheoryNoOfEval();
														}
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfEvaluation=noOfEvaluation+subSetTo.getPracticalNoOfEval();
														}
														if(marksList.size()==(noOfAns*noOfEvaluation)){
															StudentFinalMarkDetails bo=getRegularEvalAndAnsFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,revaluationMarksUpdateForm.getUserId());
															bo.setExam(exam);
															boList.add(bo);
														}else{
															errorList.add(sto.getRegisterNo()+"-"+subSetTo.getSubjectName());
														}
													}else if(subSetTo.isTheoryAnsCheck() || subSetTo.isPracticalAnsCheck()){
														int noOfAns=0;
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfAns=noOfAns+subSetTo.getTheoryNoOfAns();
														}
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfAns=noOfAns+subSetTo.getPracticalNoOfEval();
														}
														if(marksList.size()==noOfAns){
															StudentFinalMarkDetails bo=getRegularAnsFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,revaluationMarksUpdateForm.getUserId());
															bo.setExam(exam);
															boList.add(bo);
														}else{
															errorList.add(sto.getRegisterNo()+"-"+subSetTo.getSubjectName());
														}
													}else if(subSetTo.isTheoryEvalCheck() || subSetTo.isPracticalEvalCheck()){
														int noOfEvaluation=0;
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfEvaluation=noOfEvaluation+subSetTo.getTheoryNoOfEval();
														}
														if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
															noOfEvaluation=noOfEvaluation+subSetTo.getPracticalNoOfEval();
														}
														if(marksList.size()==noOfEvaluation){
															StudentFinalMarkDetails bo=getRegularEvalFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,revaluationMarksUpdateForm.getUserId());
															bo.setExam(exam);
															boList.add(bo);
														}else{
															errorList.add(sto.getRegisterNo()+"-"+subSetTo.getSubjectName());
														}
													}
												}
											}
										}
									}
								}//remove this
								}
							}
//						}//remove THis
					//	}
					}
				}
				if(errorList.isEmpty() && (revaluationMarksUpdateForm.getErrorMessage() == null || revaluationMarksUpdateForm.getErrorMessage().isEmpty())){
					return transaction.saveRegularOverAll(boList);
				}else{
					revaluationMarksUpdateForm.setErrorList(errorList);
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
		}
}
