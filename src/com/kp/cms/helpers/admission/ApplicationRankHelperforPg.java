package com.kp.cms.helpers.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmSubjectMarkForRank;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePreferenceEntranceDetails;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.SeatAllocation;
import com.kp.cms.bo.admin.StudentCommonRank;
import com.kp.cms.bo.admin.StudentCourseAllotment;
import com.kp.cms.bo.admin.StudentIndexMark;
import com.kp.cms.bo.admin.StudentRank;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.ApplicationEditForm;
import com.kp.cms.to.admin.CandidatePreferenceEntranceDetailsTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.SeatAllocationTO;
import com.kp.cms.to.admin.StudentCourseAllotmentTo;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.transactions.admission.IApplicationEditTransaction;
import com.kp.cms.transactionsimpl.admin.CourseTransactionImpl;
import com.kp.cms.transactionsimpl.admission.ApplicationEditTransactionimpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ApplicationRankHelperforPg {
	/**
	 * Singleton object of ApplicationRankHelper
	 */
	private static volatile ApplicationRankHelperforPg applicationRankHelperforPg = null;
	private static final Log log = LogFactory.getLog(ApplicationRankHelperforPg.class);
	/*private static final String OTHER="Other";
	private static final String PHOTO="Photo";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final String SQL_DATEFORMAT1="yyyy-MM-dd";*/
	private ApplicationRankHelperforPg() {

	}
	/**
	 * return singleton object of ApplicationRankHelper.
	 * @return
	 */
	public static ApplicationRankHelperforPg getInstance() {
		if (applicationRankHelperforPg == null) {
			applicationRankHelperforPg = new ApplicationRankHelperforPg();
		}
		return applicationRankHelperforPg;
	}
	//For rank
	@SuppressWarnings("unchecked")
	public List<StudentRank> calculateRank(ApplicationEditForm form)throws Exception{


		List<StudentRank> rankList= new ArrayList<StudentRank>();
		List<StudentIndexMark> pgMarksCardData = new ArrayList<StudentIndexMark>();
		List<StudentIndexMark> pgMarksCardData1 = new ArrayList<StudentIndexMark>();
		List<StudentIndexMark> pgMarksCardData2 = new ArrayList<StudentIndexMark>();
		List<StudentIndexMark> pgMarksCardData3 = new ArrayList<StudentIndexMark>();
		List<StudentIndexMark> pgMarksCardData4 = new ArrayList<StudentIndexMark>();
		List<StudentIndexMark> pgMarksCardData5 = new ArrayList<StudentIndexMark>();
		/**List<StudentIndexMark> pgMarksCardData6 = new ArrayList<StudentIndexMark>();
		List<StudentIndexMark> pgMarksCardData7 = new ArrayList<StudentIndexMark>();
		List<StudentIndexMark> pgMarksCardData8 = new ArrayList<StudentIndexMark>();**/
		Session session = null;


		try{



			session = HibernateUtil.getSession();

			// get all student index marks based on courses
			String hqlQuery = " from StudentIndexMark s where s.course.id="+form.getCourseId()+" and s.admAppln.appliedYear="+form.getAcademicYear()+" and s.remark='Eligible' order by s.indexMark desc";
			Query query = session.createQuery(hqlQuery);
			pgMarksCardData = query.list();


			// main list if empty or not
			if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
				//int studentId=0;
				//studentId=objForm.getStudentId();
				Iterator<StudentIndexMark> itr=pgMarksCardData.iterator();

				Map<Integer,Integer> rankMap=new LinkedHashMap<Integer, Integer>();
				Integer rank=0;
				//double lastMarks=0;

				//main while data start
				while (itr.hasNext()) {

					StudentIndexMark studentmark = (StudentIndexMark) itr.next();

					//check admid and index mark null 
					if(studentmark.getAdmAppln().getId()!=0){

						//if ckeck for single admid testing
						// if(1136==Integer.parseInt(obj[0].toString())){

						// sample duplicate 1 rank map
						if(!rankMap.containsKey(studentmark.getAdmAppln().getId())){

							// get students  based on courses and index marks
							String hqlQuery1 = " from StudentIndexMark s where s.course.id="+studentmark.getCourse().getId()+" and s.indexMark="+studentmark.getIndexMark()+" and s.admAppln.appliedYear="+form.getAcademicYear()+" and s.remark='Eligible' order by s.electivesubmark desc";
							Query query1 = session.createQuery(hqlQuery1);
							pgMarksCardData = query1.list();

							if(pgMarksCardData!=null && pgMarksCardData.size()!=0 && pgMarksCardData.size()==1){

								if(!rankMap.containsKey(studentmark.getAdmAppln().getId())){	

									rank++;

									rankMap.put(studentmark.getAdmAppln().getId(),rank);

									StudentRank studentRank =new StudentRank();
									AdmAppln appln=new AdmAppln();
									appln.setId(studentmark.getAdmAppln().getId());
									studentRank.setAdmAppln(appln);
									Course course=new Course();
									course.setId(studentmark.getCourse().getId());
									studentRank.setCourse(course);
									studentRank.setIndexMark(studentmark.getIndexMark());
									studentRank.setRank(rank);
									studentRank.setPrefNo(studentmark.getPrefNo());
									studentRank.setTiePreference("Based on index Marks");
									studentRank.setCreatedDate(new Date());
									studentRank.setCreatedBy(form.getUserId());



									rankList.add(studentRank);

								}//dup map close

							}//if

							// check remaining conditions for 1 tie
							else{
								Iterator<StudentIndexMark> itr1=pgMarksCardData.iterator();

								// 1st tie while check
								while(itr1.hasNext()){

									StudentIndexMark stumark = (StudentIndexMark) itr1.next();

									// get students  based on courses and index marks	and electivesubmark		
									String hqlQuery2 = " from StudentIndexMark s where s.course.id="+stumark.getCourse().getId()+" and s.indexMark="+stumark.getIndexMark()+" and s.electivesubmark="+stumark.getElectivesubmark()+" and s.admAppln.appliedYear="+form.getAcademicYear()+" and s.remark='Eligible' order by s.groupMarks desc";
									Query query2 = session.createQuery(hqlQuery2);
									pgMarksCardData1 = query2.list();

									// duplicate 2 for 1 tie rank map
									if(!rankMap.containsKey(stumark.getAdmAppln().getId())){


										if(pgMarksCardData1!=null && pgMarksCardData1.size()!=0 &&  pgMarksCardData1.size()==1){

											//raghu 	
											if(!rankMap.containsKey(stumark.getAdmAppln().getId())){	

												rank++;
												rankMap.put(stumark.getAdmAppln().getId(),rank);

												StudentRank studentRank =new StudentRank();
												AdmAppln appln=new AdmAppln();
												appln.setId(stumark.getAdmAppln().getId());
												studentRank.setAdmAppln(appln);
												Course course=new Course();
												course.setId(stumark.getCourse().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(stumark.getIndexMark());
												studentRank.setRank(rank);
												studentRank.setPrefNo(stumark.getPrefNo());
												studentRank.setTiePreference("Based on elective Subject Marks");
												studentRank.setCreatedDate(new Date());
												studentRank.setCreatedBy(form.getUserId());


												rankList.add(studentRank);

											}// dup map close

										}//if



										else {

											Iterator<StudentIndexMark> itre=pgMarksCardData1.iterator();	
											// 2 tie while check
											while(itre.hasNext()){


												StudentIndexMark stmark = (StudentIndexMark) itre.next();

												// duplicate 3 for 2 tie rank map
												if(!rankMap.containsKey(stmark.getAdmAppln().getId())){

													// get students  based on courses and index marks	and electivesubmark	and groupMarks		
													String hqlQuery3 = " from StudentIndexMark s where s.course.id="+stmark.getCourse().getId()+" and s.indexMark="+stmark.getIndexMark()+" and s.groupMarks="+stmark.getGroupMarks()+" and s.electivesubmark="+stmark.getElectivesubmark()+" and s.admAppln.appliedYear="+form.getAcademicYear()+" and s.remark='Eligible' order by s.language1Marks desc";
													Query query3 = session.createQuery(hqlQuery3);
													pgMarksCardData2 = query3.list();
													//if(!rankMap.containsKey(Integer.parseInt(obj5[0].toString()))){

													if(pgMarksCardData2!=null && pgMarksCardData2.size()!=0 && pgMarksCardData2.size()==1){

														//raghu 	
														if(!rankMap.containsKey(stmark.getAdmAppln().getId())){	

															rank++;

															rankMap.put(stmark.getAdmAppln().getId(),rank);	


															StudentRank studentRank =new StudentRank();
															AdmAppln appln=new AdmAppln();
															appln.setId(stmark.getAdmAppln().getId());
															studentRank.setAdmAppln(appln);
															Course course=new Course();
															course.setId(stmark.getCourse().getId());
															studentRank.setCourse(course);

															studentRank.setIndexMark(stmark.getIndexMark());
															studentRank.setRank(rank);
															studentRank.setPrefNo(stmark.getPrefNo());
															studentRank.setTiePreference("Based on group Subject Marks");
															studentRank.setCreatedDate(new Date());
															studentRank.setCreatedBy(form.getUserId());


															rankList.add(studentRank);

														}// dup map close

													}
													else{


														Iterator<StudentIndexMark> itrr=pgMarksCardData2.iterator();

														while(itrr.hasNext()){

															StudentIndexMark studmark = (StudentIndexMark) itrr.next();
															if(!rankMap.containsKey(studmark.getAdmAppln().getId())){

																// get students  based on courses and index marks	and electivesubmark	and groupMarks	and language1Marks		
																String hqlQuery4 = " from StudentIndexMark s where s.course.id="+studmark.getCourse().getId()+" and s.indexMark="+studmark.getIndexMark()+" and s.language1Marks="+studmark.getLanguage1Marks()+" and s.groupMarks="+studmark.getGroupMarks()+" and s.electivesubmark="+studmark.getElectivesubmark()+" and s.admAppln.appliedYear="+form.getAcademicYear()+" and s.remark='Eligible' order by s.language2Marks desc";
																Query query4 = session.createQuery(hqlQuery4);
																pgMarksCardData3 = query4.list();

																if(pgMarksCardData3!=null && pgMarksCardData3.size()!=0 && pgMarksCardData3.size()==1){


																	//raghu 	
																	if(!rankMap.containsKey(studmark.getAdmAppln().getId())){	

																		rank++;

																		rankMap.put(studmark.getAdmAppln().getId(),rank);	


																		StudentRank studentRank =new StudentRank();
																		AdmAppln appln=new AdmAppln();
																		appln.setId(studmark.getAdmAppln().getId());
																		studentRank.setAdmAppln(appln);
																		Course course=new Course();
																		course.setId(studmark.getCourse().getId());
																		studentRank.setCourse(course);

																		studentRank.setIndexMark(studmark.getIndexMark());
																		studentRank.setRank(rank);
																		studentRank.setPrefNo(studmark.getPrefNo());
																		studentRank.setTiePreference("Based on  Language 1 Marks");
																		studentRank.setCreatedDate(new Date());
																		studentRank.setCreatedBy(form.getUserId());


																		rankList.add(studentRank);

																	}// dup map close
																}
																else{
																	Iterator<StudentIndexMark> itrr1=pgMarksCardData3.iterator();
																	while(itrr1.hasNext()){

																		StudentIndexMark stu = (StudentIndexMark) itrr1.next();
																		if(!rankMap.containsKey(stu.getAdmAppln().getId())){

																			// get students  based on courses and index marks	and electivesubmark	and groupMarks	and language1Marks	and language2Marks
																			String hqlQuery5 = " from StudentIndexMark s where s.course.id="+stu.getCourse().getId()+" and s.indexMark="+stu.getIndexMark()+" and s.language1Marks="+stu.getLanguage1Marks()+" and s.language2Marks="+stu.getLanguage2Marks()+" and s.electivesubmark="+stu.getElectivesubmark()+" and s.groupMarks="+stu.getGroupMarks()+" and s.admAppln.appliedYear="+form.getAcademicYear()+" and s.remark='Eligible' order by s.language2Marks desc";
																			Query query5 = session.createQuery(hqlQuery5);
																			pgMarksCardData4 = query5.list();
																			if(pgMarksCardData4!=null && pgMarksCardData4.size()!=0 && pgMarksCardData4.size()==1){

																				//raghu 	
																				if(!rankMap.containsKey(stu.getAdmAppln().getId())){	

																					rank++;
																					rankMap.put(stu.getAdmAppln().getId(),rank);


																					StudentRank studentRank =new StudentRank();
																					AdmAppln appln=new AdmAppln();
																					appln.setId(stu.getAdmAppln().getId());
																					studentRank.setAdmAppln(appln);
																					Course course=new Course();
																					course.setId(stu.getCourse().getId());
																					studentRank.setCourse(course);

																					studentRank.setIndexMark(stu.getIndexMark());
																					studentRank.setRank(rank);
																					studentRank.setPrefNo(stu.getPrefNo());
																					studentRank.setTiePreference("Based on Language2 Subject Marks");
																					studentRank.setCreatedDate(new Date());
																					studentRank.setCreatedBy(form.getUserId());



																					rankList.add(studentRank);
																				}//dup map close

																			}//if


																			else{
																				Iterator<StudentIndexMark> it=pgMarksCardData4.iterator();
																				while(it.hasNext()){

																					StudentIndexMark st = (StudentIndexMark) it.next();
																					if(!rankMap.containsKey(st.getAdmAppln().getId())){

																						// get students  based on courses and index marks	and electivesubmark	and groupMarks	and language1Marks	and language2Marks
																						String hqlQuery56 = " from StudentIndexMark s where s.course.id="+st.getCourse().getId()+" and s.indexMark="+st.getIndexMark()+" and s.language1Marks="+st.getLanguage1Marks()+" and s.language2Marks="+st.getLanguage2Marks()+" and s.electivesubmark="+st.getElectivesubmark()+" and s.admAppln.appliedYear="+form.getAcademicYear()+" and s.remark='Eligible' order by s.admAppln.personalData.firstName asc";
																						Query query55 = session.createQuery(hqlQuery56);
																						pgMarksCardData5 = query55.list();
																						if(pgMarksCardData5!=null && pgMarksCardData5.size()!=0){
																							Iterator<StudentIndexMark> itrr7=pgMarksCardData5.iterator();

																							while(itrr7.hasNext()){
																								StudentIndexMark obj10 = (StudentIndexMark) itrr7.next();
																								if(!rankMap.containsKey(obj10.getAdmAppln().getId())){		
																									rank++;

																									rankMap.put(obj10.getAdmAppln().getId(),rank);

																									StudentRank studentRank =new StudentRank();
																									AdmAppln appln=new AdmAppln();
																									appln.setId(obj10.getAdmAppln().getId());
																									studentRank.setAdmAppln(appln);
																									Course course=new Course();
																									course.setId(obj10.getCourse().getId());
																									studentRank.setCourse(course);

																									studentRank.setIndexMark(obj10.getIndexMark());
																									studentRank.setRank(rank);
																									studentRank.setPrefNo(obj10.getPrefNo());
																									studentRank.setTiePreference("Based on Alphabet");
																									studentRank.setCreatedDate(new Date());
																									studentRank.setCreatedBy(form.getUserId());

																									rankList.add(studentRank);

																								}



																							}//fifth else
																						}//while

																					}
																				}//else fourth
																			}//while
																		}
																	}// duplicate 3 for 2 tie rank map close

																}// 2 tie while check close

															}//2 if close

														}// // duplicate 2 for 1 tie rank map close

													}//1 tie while close

												} // 1 tie check close
												//rank=rankMap.get(studentId);

											}// duplicate rank map close

											//}// if check for single admid	testing		   

										}//if close admid and rank



									}// main while close

								} //main list if close


							}//try close
						}
					}
				}
			}
		}
		catch (Exception e) {

			System.out.println("========== error in rank calc helper"+e);
			throw e;
		}

		return rankList;
	}


	//For mark
	@SuppressWarnings("unchecked")
	public List<StudentIndexMark> calculateMark( List<AdmAppln> applDetails,ApplicationEditForm form)throws Exception{

		List<StudentIndexMark> indexmarkList=new ArrayList<StudentIndexMark>();
		DecimalFormat twoDForm = new DecimalFormat("#.#");

		try{
			Iterator<AdmAppln> iter=applDetails.iterator();

			while(iter.hasNext()){
				
				AdmAppln admAppln=(AdmAppln)iter.next() ;
				if (admAppln.getApplnNo()!=0) {
				System.out.println("before================"+admAppln.getId());
		/*if(admAppln.getId()==3527 || admAppln.getId()==3570){
			System.out.println("before================"+admAppln.getId());
			continue;
		}*/
				int penalty=0;

				//form.setAccoId(admAppln.getId());
                //int id = admAppln.getId();
				int bonus=0;
				boolean feepaid=false;
				double percentage=0;
				double totalmark=0;
				boolean type1=false;
				boolean type2=false;
				boolean type3=false;
				boolean type4=false;
				boolean type5=false;
				
			    if(admAppln.getApplnNo()!=0 && admAppln.getMode()!=null && admAppln.getMode().equalsIgnoreCase("CHALLAN")){
	                if(admAppln.getIsChallanRecieved()!=null && admAppln.getIsChallanRecieved()){
	                	 feepaid=true;
	                }else{
	                	 feepaid=false;
	                }
	                }else if(admAppln.getApplnNo()!=0 && admAppln.getMode()!=null && admAppln.getMode().equalsIgnoreCase("NEFT")){
	                	  if(admAppln.getIsChallanRecieved()!=null && admAppln.getIsChallanRecieved()){
	                    	  feepaid=true;
	                      }else{
	                    	  feepaid=false;
	                      }
	                }else if(admAppln.getApplnNo()!=0 && admAppln.getMode()!=null && admAppln.getMode().equalsIgnoreCase("Online")){
	                	 feepaid=true;
	                      
	                }

				Iterator<EdnQualification> itr=admAppln.getPersonalData().getEdnQualifications().iterator();

				Iterator<CandidatePreference> ittr=admAppln.getCandidatePreferences().iterator();

				Iterator<AdmapplnAdditionalInfo> iterator=admAppln.getAdmapplnAdditionalInfo().iterator();
				feepaid=true;
				while(iterator.hasNext()){
					AdmapplnAdditionalInfo admapplnAdditionalInfo=iterator.next();


					// check backlogs there are not
					if(feepaid){
						int ugcourseid=admAppln.getPersonalData().getUgcourse().getId();
						if(ugcourseid==18 || ugcourseid==19 || ugcourseid==20 || ugcourseid==21 || ugcourseid==22 || ugcourseid==31 || ugcourseid==32){
							type1=true;
						}
						else if(ugcourseid==23 || ugcourseid==24 || ugcourseid==25 || ugcourseid==26 || ugcourseid==27 || ugcourseid==28){
							type2=true;
						}
						/*else if(ugcourseid==40){
							type3=true;
						}*/
						else if(ugcourseid==29){
							type4=true;
						}

						else if(ugcourseid==30){
							type5=true;
						}
						// educationinfo while
						while(itr.hasNext()){
							EdnQualification ednQualificationBO= itr.next() ;


							String ugPattern=ednQualificationBO.getUgPattern();
							if(ednQualificationBO.getDocChecklist()!=null && ednQualificationBO.getDocChecklist().getIsActive()==true && ednQualificationBO.getDocChecklist().getDocType().getName().equalsIgnoreCase("DEG")){

								Set<AdmSubjectMarkForRank> detailMarks=ednQualificationBO.getAdmSubjectMarkForRank();
								//penalty based on attempt
								if( ednQualificationBO.getNoOfAttempts()!=0){
									penalty=penalty+(ednQualificationBO.getNoOfAttempts()-1)*5;//1st chance & this year pass out 


									//Bonus
								}
								if(ugPattern.equalsIgnoreCase("CBCSS")){
									totalmark=(Double.parseDouble(ednQualificationBO.getMarksObtained().toString())/Double.parseDouble(ednQualificationBO.getTotalMarks().toString()))*4;
								}
								if(ugPattern.equalsIgnoreCase("CBCSS NEW")){
									totalmark=(Double.parseDouble(ednQualificationBO.getMarksObtained().toString())/Double.parseDouble(ednQualificationBO.getTotalMarks().toString()))*4;
								}
								else if(ugPattern.equalsIgnoreCase("OTHER")){
									percentage=(Double.parseDouble(ednQualificationBO.getMarksObtained().toString())/Double.parseDouble(ednQualificationBO.getTotalMarks().toString()))*100;
								}

								System.out.println(percentage+"between"+admAppln.getId()+"----------tot"+totalmark);
								//15 mark for ncc & 3 mark for A grade,5 mark for B grade and 5 mark for C grade
								if(admAppln.getPersonalData().getIsNcccertificate()){
										bonus=bonus+10;
								}



								else if(admAppln.getPersonalData().getIsNsscertificate()){
									bonus=bonus+5;
								}

								if(admAppln.getPersonalData().getIsExcervice()){
									bonus=bonus+15;
								}
								// detailmarks if check
								if(detailMarks!=null && !detailMarks.isEmpty())
								{
									AdmSubjectMarkForRank detailMarkBO=null;
									while(ittr.hasNext()){
										CandidatePreference candidatePreference= ittr.next(); 
										double total=0;
										String remark="not Eligible";
										boolean eligibility=false;
										
										//MA ENGLISH
										//else if(candidatePreference.getCourse().getName().equalsIgnoreCase("MA ENGLISH")){
										if(candidatePreference.getCourse().getId()==14){
											double maxmark=0;
											double language1Mark=0;                          
											double language2Mark=0;
											double groupMarks=0;
											double electiveSubjectMark=0;
											//double cgpamarks=0;
											double extramark=0;
											double cgpa=0;
											double credit=0;
											double mult=0;
											double totalcredit=0;
											double totalcgpa=0;
											double langperc=0;
											double coremarks=0;
											eligibility=false;
											double maxcoremark=0;
											double convmark1=0;
											double convmark2=0;
											double weightage=0;
											remark="not Eligible";
											if(type1==true){
												if((ugPattern.equalsIgnoreCase("CBCSS")|| ugPattern.equalsIgnoreCase("CBCSS NEW") )){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														
														/*if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core") && (detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 ) ){
															cgpa=Double.parseDouble(detailMarkBO.getConversionmark());
															credit=Double.parseDouble(detailMarkBO.getCredit());
															mult=cgpa*credit;
															totalcgpa=totalcgpa+mult;
															totalcredit=totalcredit+credit;
														}*/
														//lang1 mark part 1 English
														// cgpa scored for common course should secure 5.0 out of 10
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language1Mark=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
															if(language1Mark>2){
																eligibility=true;
															}
														}
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")/* && (detailMarkBO.getAdmSubjectForRank().getId()==414 || detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 )*/ ){
															coremarks=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
														}								
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(totalmark>=1.8 && eligibility==true){
														remark="Eligible";
														if(coremarks>=language1Mark){
															total=coremarks*100;
															weightage=total*.06;
															total=total+bonus-penalty+weightage;
															electiveSubjectMark=coremarks;
														}
														else{
															total=language1Mark*100;
															weightage=.06*total;
															// extramark=total/10;
															total=total+bonus-penalty+weightage;   
															electiveSubjectMark=language1Mark;
														}
													}
												}
												else if(ugPattern.equalsIgnoreCase("OTHER")){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														// part 3 English 
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core") /*&& (detailMarkBO.getAdmSubjectForRank().getId()==414 || detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 ) */){
															coremarks=Double.parseDouble(detailMarkBO.getObtainedmark());
															maxcoremark=Double.parseDouble(detailMarkBO.getMaxmark());
															convmark1=Double.parseDouble(detailMarkBO.getConversionmark());
														}										
														//lang1 mark  part 1 English
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language1Mark=Double.parseDouble(detailMarkBO.getObtainedmark());
															maxmark=Double.parseDouble(detailMarkBO.getMaxmark());
															convmark2=Double.parseDouble(detailMarkBO.getConversionmark());
															langperc=(language1Mark/maxmark)*100;
															if(langperc>50){

																eligibility=true;
															}
														}
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(percentage>=45 && eligibility==true){
														remark="Eligible";
														if(coremarks>language1Mark){
															total=(coremarks/maxcoremark)*1000;
															//extramark=total/10;
															weightage=.06*total;
															total=total+bonus-penalty+weightage;
															electiveSubjectMark=convmark1;
														}
														else{
															total=(language1Mark/maxmark)*1000;
															weightage=.06*total;
															//extramark=total/10;
															total=total+bonus-penalty+weightage;  
															electiveSubjectMark=convmark2;
														}
													}
												} 
											}
											else if(type2==true){
												if(ugPattern.equalsIgnoreCase("CBCSS")|| ugPattern.equalsIgnoreCase("CBCSS NEW") ){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){
															cgpa=Double.parseDouble(detailMarkBO.getConversionmark());
															credit=Double.parseDouble(detailMarkBO.getCredit());
															mult=cgpa*credit;
															totalcgpa=totalcgpa+cgpa;
															totalcredit=totalcredit+credit;
														}
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")/* && (detailMarkBO.getAdmSubjectForRank().getId()==414 || detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 ) */){
															coremarks=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
														}
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
															if(language1Mark>2){
																eligibility=true;
															}
														}
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(totalmark>=1.8 &&eligibility){
														remark="Eligible";
														total=coremarks*100;
														weightage=.06*total;
														total=total+bonus-penalty+weightage;
														electiveSubjectMark=coremarks;
													}
												}
												else if(ugPattern.equalsIgnoreCase("OTHER")){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")/* && (detailMarkBO.getAdmSubjectForRank().getId()==414 || detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 ) */){
															coremarks=Double.parseDouble(detailMarkBO.getObtainedmark());
															maxcoremark=Double.parseDouble(detailMarkBO.getMaxmark());
															convmark1=Double.parseDouble(detailMarkBO.getConversionmark());
														}										
														//lang1 mark 
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(percentage>=45){
														remark="Eligible";
														if(maxcoremark>0)
															total=(coremarks/maxcoremark)*1000;
														weightage=.06*total;
														total=total+bonus-penalty+weightage;
														if(maxcoremark>0)
															electiveSubjectMark=(coremarks/maxcoremark);
													}
												}
											}
											else if(type3==true){
												if(ugPattern.equalsIgnoreCase("CBCSS")|| ugPattern.equalsIgnoreCase("CBCSS NEW") ){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")/* && (detailMarkBO.getAdmSubjectForRank().getId()==414 || detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 )*/ ){
															cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*4;
															credit=Double.parseDouble(detailMarkBO.getCredit());
															//mult=cgpa*credit;
															//totalcgpa=totalcgpa+mult;
															totalcgpa=totalcgpa+cgpa;
															totalcredit=totalcredit+4;
															coremarks=Double.parseDouble(detailMarkBO.getConversionmark());
														}
														//index mark calculation based on part 1 English
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
															if(language1Mark>2){
																eligibility=true;
															}
														}								
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(totalmark>=1.8){
														remark="Eligible";
														total=(totalcgpa/totalcredit)*1000;
														//weightage=.1*total*60;
														total=total+bonus-penalty+weightage;
														electiveSubjectMark=coremarks;
													}
												}
												else if(ugPattern.equalsIgnoreCase("OTHER")){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														//lang1 mark 
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")/* && (detailMarkBO.getAdmSubjectForRank().getId()==414 || detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 ) */){
															language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
															coremarks=coremarks+Double.parseDouble(detailMarkBO.getObtainedmark());
															maxcoremark=maxcoremark+Double.parseDouble(detailMarkBO.getMaxmark());
															convmark1=Double.parseDouble(detailMarkBO.getConversionmark());
														}
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(percentage>=45){
														remark="Eligible";
														if(coremarks>0 && maxcoremark>0)
															total=(coremarks/maxcoremark)*1000;
														weightage=.06*total;
														total=total+bonus-penalty+weightage;
														electiveSubjectMark=convmark1;
													}
												}
											}
											else if(type4==true || type5==true){
												if(ugPattern.equalsIgnoreCase("CBCSS")|| ugPattern.equalsIgnoreCase("CBCSS NEW") ){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){//&& (detailMarkBO.getAdmSubjectForRank().getId()==414 || detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 ) 
															cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
															credit=Double.parseDouble(detailMarkBO.getCredit());
															mult=cgpa*credit;
															totalcgpa=totalcgpa+mult;
															//totalcgpa=totalcgpa+cgpa;
															totalcredit=totalcredit+credit;
															electiveSubjectMark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
															if(language1Mark>2){
																eligibility=true;
															}
														}								
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(totalmark>=1.8){
														if(totalcgpa>0 && totalcredit>0){
															total=(totalcgpa/totalcredit)*100;
															weightage=.03*total;
															total=total+bonus-penalty+weightage;
															remark="Eligible";
														}
													}
												}
												else if(ugPattern.equalsIgnoreCase("OTHER")){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){// && (detailMarkBO.getAdmSubjectForRank().getId()==414 || detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 ) ){
															coremarks=Double.parseDouble(detailMarkBO.getObtainedmark());
															totalcgpa=totalcgpa+coremarks;
															maxcoremark=Double.parseDouble(detailMarkBO.getMaxmark());
															maxmark=maxmark+maxcoremark;
															convmark1=Double.parseDouble(detailMarkBO.getConversionmark());
															electiveSubjectMark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
														//lang1 mark 
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(percentage>=45){
														remark="Eligible";
														total=(totalcgpa/maxmark)*1000;
														weightage=.03*total;
														total=total+bonus-penalty+weightage;
													}
												}
											}
											else {
												if(ugPattern.equalsIgnoreCase("CBCSS")|| ugPattern.equalsIgnoreCase("CBCSS NEW") ){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){
															cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
															credit=Double.parseDouble(detailMarkBO.getCredit());
															//mult=cgpa*credit;
															totalcgpa=totalcgpa+cgpa;
															totalcredit=totalcredit+credit;
														}
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															coremarks=Double.parseDouble(detailMarkBO.getConversionmark());
															language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
															if(language1Mark>2){
																eligibility=true;
															}
														}								
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(totalmark>=1.8){
														remark="Eligible";
														total=coremarks*100;
														weightage=.06*total;
														total=total+bonus-penalty+extramark;
														electiveSubjectMark=coremarks;
													}
												}
												else if(ugPattern.equalsIgnoreCase("OTHER")){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														//lang1 mark 
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
															coremarks=coremarks+Double.parseDouble(detailMarkBO.getObtainedmark());
															maxcoremark=maxcoremark+Double.parseDouble(detailMarkBO.getMaxmark());
															convmark1=Double.parseDouble(detailMarkBO.getConversionmark());
														}
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(percentage>=45){
														remark="Eligible";
														if(coremarks>0 && maxcoremark>0)
															total=(coremarks/maxcoremark)*1000;
														weightage=.06*total;
														total=total+bonus-penalty+extramark;
														electiveSubjectMark=convmark1;
													}
												}
											}

											// adding indexmark object
											StudentIndexMark si=new StudentIndexMark();

											AdmAppln appln=new AdmAppln();
											appln.setId(admAppln.getId());
											si.setAdmAppln(appln);
											Course course=new Course();
											course.setId(candidatePreference.getCourse().getId());
											si.setCourse(course);

											si.setIndexMark(Double.parseDouble(twoDForm.format(total)));
											si.setRemark(remark);
											si.setCreatedDate(new Date());
											si.setLastModifiedDate(new Date());
											si.setCreatedBy(form.getUserId());
											si.setModifiedBy(form.getUserId());	
											
											si.setLanguage1Marks(language1Mark);
											si.setLanguage2Marks(language2Mark);
											si.setGroupMarks(groupMarks);
											si.setElectivesubmark(electiveSubjectMark);
											si.setActive(true);
											si.setPrefNo(candidatePreference.getPrefNo());
											si.setGenerateCourseId(Integer.parseInt(form.getCourseId()));
											
											indexmarkList.add(si);
										}// while close


										//MA English over
										
										//MA malayalam
										if(candidatePreference.getCourse().getId()==13){
											Session session=null;
											Map<Integer,String> eligMap=new HashMap<Integer, String>();
											session = HibernateUtil.getSession();
											String str="select p.uGCoursesBO.id, p.uGCoursesBO.name from PgAdmSubjectForRank p where p.course.id="+candidatePreference.getCourse().getId();
											Query query =session.createQuery(str);
											List<Object[]> list=query.list();
											Iterator<Object[]> it=list.iterator();
											while (it.hasNext()) {
												Object[] objects = (Object[]) it.next();
												eligMap.put(Integer.parseInt(objects[0].toString()),(objects[1].toString()));
											}
											session.flush();
											double maxmark=0;
											double language1Mark=0;                          
											double language2Mark=0;
											double groupMarks=0;
											double electiveSubjectMark=0;
											//double cgpamarks=0;
											double extramark=0;
											double cgpa=0;
											double credit=0;
											double mult=0;
											double totalcredit=0;
											double totalcgpa=0;
											double langperc=0;
											double coremarks=0;
											eligibility=false;
											double maxcoremark=0;
											double convmark1=0;
											double convmark2=0;
											double weightage=0;
											remark="not Eligible";
											if(eligMap.containsKey(ugcourseid)){
												if((ugPattern.equalsIgnoreCase("CBCSS")|| ugPattern.equalsIgnoreCase("CBCSS NEW") )){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														
														/*if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core") && (detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 ) ){
															cgpa=Double.parseDouble(detailMarkBO.getConversionmark());
															credit=Double.parseDouble(detailMarkBO.getCredit());
															mult=cgpa*credit;
															totalcgpa=totalcgpa+mult;
															totalcredit=totalcredit+credit;
														}*/
														//lang1 mark part 1 English
														// cgpa scored for common course should secure 5.0 out of 10
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("Malayalam")){
															language1Mark=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
															if(language1Mark>2){
																eligibility=true;
															}
														}
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){// && (detailMarkBO.getAdmSubjectForRank().getId()==414 || detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 ) ){
															coremarks=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
														}								
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("Malayalam")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(totalmark>=1.8 && eligibility==true){
														remark="Eligible";
														if(coremarks>=language1Mark){
															
															total=coremarks*100;
															weightage=.06*total;
															total=total+bonus-penalty+weightage;
															electiveSubjectMark=coremarks;
														}
														else{
															weightage=coremarks*100*.06;
															total=language1Mark*100;
															total=total+bonus-penalty+weightage;   
															electiveSubjectMark=language1Mark;
														}
													}
												}
												else if(ugPattern.equalsIgnoreCase("OTHER")){
													Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
													while (markItr.hasNext()) {
														detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
														// part 3 English 
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){// && (detailMarkBO.getAdmSubjectForRank().getId()==414 || detailMarkBO.getAdmSubjectForRank().getId()==240 || detailMarkBO.getAdmSubjectForRank().getId()==261 || detailMarkBO.getAdmSubjectForRank().getId()==401 ) ){
															coremarks=Double.parseDouble(detailMarkBO.getObtainedmark());
															maxcoremark=Double.parseDouble(detailMarkBO.getMaxmark());
															convmark1=Double.parseDouble(detailMarkBO.getConversionmark());
														}										
														//lang1 mark  part 1 English
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language1Mark=Double.parseDouble(detailMarkBO.getObtainedmark());
															maxmark=Double.parseDouble(detailMarkBO.getMaxmark());
															convmark2=Double.parseDouble(detailMarkBO.getConversionmark());
															langperc=(language1Mark/maxmark)*100;
															if(langperc>50){

																eligibility=true;
															}
														}
														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
													}
													if(percentage>=45 && eligibility==true){
														remark="Eligible";
														if(coremarks>language1Mark){
															total=(coremarks/maxcoremark)*1000;
															//extramark=total/10;
															weightage=.06*total;
															total=total+bonus-penalty+weightage;
															electiveSubjectMark=convmark1;
														}
														else{
															total=(language1Mark/maxmark)*1000;
															weightage=.06*total;
															//extramark=total/10;
															total=total+bonus-penalty+weightage;  
															electiveSubjectMark=convmark2;
														}
													}
												} 
											}

											// adding indexmark object
											StudentIndexMark si=new StudentIndexMark();

											AdmAppln appln=new AdmAppln();
											appln.setId(admAppln.getId());
											si.setAdmAppln(appln);
											Course course=new Course();
											course.setId(candidatePreference.getCourse().getId());
											si.setCourse(course);

											si.setIndexMark(Double.parseDouble(twoDForm.format(total)));
											si.setRemark(remark);
											si.setCreatedDate(new Date());
											si.setLastModifiedDate(new Date());
											si.setCreatedBy(form.getUserId());
											si.setModifiedBy(form.getUserId());	
											
											si.setLanguage1Marks(language1Mark);
											si.setLanguage2Marks(language2Mark);
											si.setGroupMarks(groupMarks);
											si.setElectivesubmark(electiveSubjectMark);
											si.setActive(true);
											si.setPrefNo(candidatePreference.getPrefNo());
											si.setGenerateCourseId(Integer.parseInt(form.getCourseId()));
											
											indexmarkList.add(si);
										}
										

										//MSc Chemistry & MSc Mathematics
										else if(CMSConstants.UG_ELIGIBLE_COURSE_LIST_FOR_MSC.contains(candidatePreference.getCourse().getId())){
											Session session=null;
											Map<Integer,String> eligMap=new HashMap<Integer, String>();
											session = HibernateUtil.getSession();
											String str="select p.uGCoursesBO.id, p.uGCoursesBO.name from PgAdmSubjectForRank p where p.course.id="+candidatePreference.getCourse().getId();
											Query query =session.createQuery(str);
											List<Object[]> list=query.list();
											Iterator<Object[]> it=list.iterator();
											while (it.hasNext()) {
												Object[] objects = (Object[]) it.next();
												eligMap.put(Integer.parseInt(objects[0].toString()),(objects[1].toString()));
											}
											session.flush();
											remark="not Eligible";
											//double maxmark=0;
											double language1Mark=0;                          
											double language2Mark=0;
											double groupMarks=0;
											double electiveSubjectMark=0;
											//double cgpamarks=0;
											double extramark=0;
											double cgpa=0;
											double credit=0;
											double mult=0;
											double totalcredit=0;
											double totalcgpa=0;
											//double electiveSubjectMathsMark=0;
											//double comonalMark=0;
											double coremalMark=0;
											//double electiveSubjecSttMark=0;
											//double marks=0;
											eligibility=false;
											double totalcore=0;
											//double coreMarks = 0;
											//double totalcommon=0;
											//double maxcorecredit=0;
											//double product=0;
											double totalcorecredit=0;
											//double coreMaxMarks = 0;
                                            double totalCoreCgpa=0;
											if(eligMap.containsKey(ugcourseid) && totalmark>=2 && (ugPattern.equalsIgnoreCase("CBCSS")|| ugPattern.equalsIgnoreCase("CBCSS NEW") ) ){

												remark="Eligible";
												Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
												while (markItr.hasNext()) {
													detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core") || detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Complimentary")){
														cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
														credit=Double.parseDouble(detailMarkBO.getCredit());
														mult=cgpa*credit;
														totalcgpa=totalcgpa+mult;
														totalcredit=totalcredit+credit;
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){
															cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
															//coreMarks = coreMarks+CommonUtil.Round(cgpa,2);
															//coreMaxMarks = coreMaxMarks+4;
															credit=Double.parseDouble(detailMarkBO.getCredit());
															mult=cgpa*credit;
															totalCoreCgpa=totalCoreCgpa+mult;
															totalcorecredit=totalcorecredit+credit;
															
															//botany
															if(detailMarkBO.getAdmSubjectForRank().getId()==228 || detailMarkBO.getAdmSubjectForRank().getId()==196){
																electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
																electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

															}
															//physics
															if(detailMarkBO.getAdmSubjectForRank().getId()==246 || detailMarkBO.getAdmSubjectForRank().getId()==238){	
																electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
																electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

															}
															//zoology
															if(detailMarkBO.getAdmSubjectForRank().getId()==247 || detailMarkBO.getAdmSubjectForRank().getId()==237){	
																electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
																electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

															}
															//Chemistry
															if(detailMarkBO.getAdmSubjectForRank().getId()==235 || detailMarkBO.getAdmSubjectForRank().getId()==248){	
																electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
																electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

															}
															//Mathematics
															
															if(detailMarkBO.getAdmSubjectForRank().getId()==245 || detailMarkBO.getAdmSubjectForRank().getId()==252){	
																electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
																electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

															}
															// msc environment Science
															/*if(detailMarkBO.getAdmSubjectForRank().getId()==241){										
																electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
																electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

															}
															//Msc AquaCulture
															if(detailMarkBO.getAdmSubjectForRank().getId()==232 || detailMarkBO.getAdmSubjectForRank().getId()==253 || detailMarkBO.getAdmSubjectForRank().getId()==242 || detailMarkBO.getAdmSubjectForRank().getId()==264){										
																
																electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
																electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

															}*/
														}
													}

													//lang1 mark
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													
													}

													//lang2 mark
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}

												}

                                                // doubt
												if(totalcredit>0)
												total=(totalcgpa/totalcredit)*100; // 1000/4 
												if(totalcorecredit>0)
												totalcore=(totalCoreCgpa/totalcorecredit)*600/10;  // 60*10/4 standardized to 600
												total=((total+totalcore)*1000)/1600;
												//total=totalcore+total;
												//total = total*1000/1600;
												total=total+bonus-penalty+extramark;
											}

											else if(eligMap.containsKey(ugcourseid) && percentage>=50 && ugPattern.equalsIgnoreCase("OTHER") ){

												remark="Eligible";
												Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
												while (markItr.hasNext()) {
													detailMarkBO= (AdmSubjectMarkForRank) markItr.next();

													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core") || detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Complimentary")){
														cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
														//coreMarks = coreMarks+CommonUtil.Round(cgpa,2);
														//coreMaxMarks = coreMaxMarks+4;
														/*credit=Double.parseDouble(detailMarkBO.getCredit());
														mult=cgpa*credit;
														totalCoreCgpa=totalCoreCgpa+mult;
														totalcorecredit=totalcorecredit+credit;*/

														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){
															coremalMark=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*4;
															//maxcorecredit=Double.parseDouble(detailMarkBO.getMaxmark());
															totalcore=totalcore+coremalMark;
															totalcorecredit=totalcorecredit+4;
														}							
														//lang1 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}

														//lang2 mark
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
															language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
														}
														
														
														//botany
														if(detailMarkBO.getAdmSubjectForRank().getId()==228 || detailMarkBO.getAdmSubjectForRank().getId()==196){
															electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
															electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

														}
														//physics
														if(detailMarkBO.getAdmSubjectForRank().getId()==246 || detailMarkBO.getAdmSubjectForRank().getId()==238){	
															electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
															electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

														}
														//zoology
														if(detailMarkBO.getAdmSubjectForRank().getId()==247 || detailMarkBO.getAdmSubjectForRank().getId()==237){	
															electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
															electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

														}
														//Chemistry
														if(detailMarkBO.getAdmSubjectForRank().getId()==235 || detailMarkBO.getAdmSubjectForRank().getId()==248){	
															electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
															electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

														}
														//Mathematics
														
														if(detailMarkBO.getAdmSubjectForRank().getId()==245 || detailMarkBO.getAdmSubjectForRank().getId()==252){	
															electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
															electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

														}
														// msc environment Science
														if(detailMarkBO.getAdmSubjectForRank().getId()==241){										
															electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
															electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

														}
														//Msc AquaCulture
														if(detailMarkBO.getAdmSubjectForRank().getId()==232 || detailMarkBO.getAdmSubjectForRank().getId()==253 || detailMarkBO.getAdmSubjectForRank().getId()==242 || detailMarkBO.getAdmSubjectForRank().getId()==264){										
															electiveSubjectMark = Double.parseDouble(detailMarkBO.getObtainedmark());
															electiveSubjectMark = CommonUtil.Round((electiveSubjectMark*4)/Double.parseDouble(detailMarkBO.getMaxmark()),2);

														}

													}

												}  
												if(totalcredit>0)
												total=(totalcgpa/totalcredit)*1000;
												//electiveSubjectMark=totalcore/totalcorecredit;
												if(totalcorecredit>0)
												totalcore=(totalcore/totalcorecredit)*600;
												total=((total+totalcore)*1000)/1600;
												total=total+totalcore;
												total = total*1000/1600;
												total=total+bonus-penalty+extramark;
												
												
											}



											// adding indexmark object
											StudentIndexMark si=new StudentIndexMark();

											AdmAppln appln=new AdmAppln();
											appln.setId(admAppln.getId());
											si.setAdmAppln(appln);
											Course course=new Course();
											course.setId(candidatePreference.getCourse().getId());
											si.setCourse(course);

											si.setIndexMark(Double.parseDouble(twoDForm.format(total)));
											si.setRemark(remark);
											si.setCreatedDate(new Date());
											si.setLastModifiedDate(new Date());
											si.setCreatedBy(form.getUserId());
											si.setModifiedBy(form.getUserId());		

											si.setLanguage1Marks(language1Mark);
											si.setLanguage2Marks(language2Mark);
											si.setGroupMarks(groupMarks);
											si.setElectivesubmark(electiveSubjectMark);
											si.setActive(true);
											si.setPrefNo(candidatePreference.getPrefNo());
											si.setGenerateCourseId(Integer.parseInt(form.getCourseId()));

											indexmarkList.add(si);


										}//OVER
										//MA ECONOMETRICS
										else if(candidatePreference.getCourse().getId()==22){
											int courseBonus=0;
											if (ugcourseid==4) {
												courseBonus=10;
											}
											Session session=null;
											Map<Integer,String> eligMap=new HashMap<Integer, String>();
											session = HibernateUtil.getSession();
											String str="select p.uGCoursesBO.id, p.uGCoursesBO.name from PgAdmSubjectForRank p where p.course.id="+candidatePreference.getCourse().getId();
											Query query =session.createQuery(str);
											List<Object[]> list=query.list();
											Iterator<Object[]> it=list.iterator();
											while (it.hasNext()) {
												Object[] objects = (Object[]) it.next();
												eligMap.put(Integer.parseInt(objects[0].toString()),(objects[1].toString()));
											}
											session.flush();
											remark="not Eligible";
											//double maxmark=0;
											double coremalMark=0;
											double language1Mark=0;                          
											double language2Mark=0;
											double groupMarks=0;
											double electiveSubjectMark=0;
											double extramark=0;
											double cgpa=0;
											double credit=0;
											double mult=0;
											double totalcredit=0;
											double totalcgpa=0;
											double marks=0;
											eligibility=false;
											double totalcore=0;
											double totalcorecredit=0;
											double totalCoreCgpa=0;
											double maxcorecredit=0;
											if(eligMap.containsKey(ugcourseid) && totalmark>=1.8 && (ugPattern.equalsIgnoreCase("CBCSS") || ugPattern.equalsIgnoreCase("CBCSS NEW") )){
												remark="Eligible";
												Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
												while (markItr.hasNext()) {
													detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core") || detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Complimentary")){cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
													credit=Double.parseDouble(detailMarkBO.getCredit());
													mult=cgpa*credit;
													totalcgpa=totalcgpa+mult;
													totalcredit=totalcredit+credit;}
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){
														cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
														credit=Double.parseDouble(detailMarkBO.getCredit());
														mult=cgpa*credit;
														totalCoreCgpa=totalCoreCgpa+mult;
														totalcorecredit=totalcorecredit+credit;
													}
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}							
													//lang2 mark
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}



												}
												
												if(totalcredit>0)
													total=(totalcgpa/totalcredit)*100; // 1000/4 
													if(totalcorecredit>0)
													totalcore=(totalCoreCgpa/totalcorecredit)*600/10;  // 60*10/4 standardized to 600
													total=((total+totalcore)*1000)/1600;
													total=total+bonus-penalty+extramark;
											}

											
											else if(eligMap.containsKey(ugcourseid) && percentage>=45 && ugPattern.equalsIgnoreCase("OTHER")){
												remark="Eligible";

												Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
												while (markItr.hasNext()) {
													detailMarkBO= (AdmSubjectMarkForRank) markItr.next();

													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core") ||detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Complimentary")){
														cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
														credit=Double.parseDouble(detailMarkBO.getMaxmark());
														//mult=cgpa*credit;
														totalCoreCgpa=totalCoreCgpa+cgpa;
														totalcredit=totalcredit+10;
														
													}		
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){
														coremalMark=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
														maxcorecredit=Double.parseDouble(detailMarkBO.getMaxmark());
														totalcore=totalcore+coremalMark;
														totalcorecredit=totalcorecredit+10;
													}	
													//lang1 mark 
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}

													//lang2 mark
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}

												}
												//if(totalcredit>0)
													total=(totalCoreCgpa/totalcredit)*1000;
													//electiveSubjectMark=totalcore/totalcorecredit;
													//if(totalcorecredit>0)
													totalcore=(totalcore/totalcorecredit)*600;
													total=((total+totalcore)*1000)/1600;
													//total=total+totalcore;
													//total = total*1000/1600;
													total=total+bonus-penalty+extramark+courseBonus;
												
											}


											// adding indexmark object
											StudentIndexMark si=new StudentIndexMark();

											AdmAppln appln=new AdmAppln();
											appln.setId(admAppln.getId());
											si.setAdmAppln(appln);
											Course course=new Course();
											course.setId(candidatePreference.getCourse().getId());
											si.setCourse(course);

											si.setIndexMark(Double.parseDouble(twoDForm.format(total)));
											si.setRemark(remark);
											si.setCreatedDate(new Date());
											si.setLastModifiedDate(new Date());
											si.setCreatedBy(form.getUserId());
											si.setModifiedBy(form.getUserId());		

											si.setLanguage1Marks(language1Mark);
											si.setLanguage2Marks(language2Mark);
											si.setGroupMarks(groupMarks);
											si.setElectivesubmark(electiveSubjectMark);
											si.setActive(true);
											si.setPrefNo(candidatePreference.getPrefNo());
											si.setGenerateCourseId(Integer.parseInt(form.getCourseId()));

											indexmarkList.add(si);

										}
										//M.COM
										else if(candidatePreference.getCourse().getId()==15){

											Session session=null;
											Map<Integer,String> eligMap=new HashMap<Integer, String>();
											session = HibernateUtil.getSession();
											String str="select p.uGCoursesBO.id, p.uGCoursesBO.name from PgAdmSubjectForRank p where p.course.id="+candidatePreference.getCourse().getId();
											Query query =session.createQuery(str);
											List<Object[]> list=query.list();
											Iterator<Object[]> it=list.iterator();
											while (it.hasNext()) {
												Object[] objects = (Object[]) it.next();
												eligMap.put(Integer.parseInt(objects[0].toString()),(objects[1].toString()));
											}
											session.flush();
											remark="not Eligible";
											//double maxmark=0;
											double language1Mark=0;                          
											double language2Mark=0;
											double groupMarks=0;
											double electiveSubjectMark=0;
											//double cgpamarks=0;
											double extramark=0;
											double cgpa=0;
											double credit=0;
											double mult=0;
											double totalcredit=0;
											double totalcgpa=0;
											//double electiveSubjectMathsMark=0;
											//double comonalMark=0;
											//double coremalMark=0;
											//double electiveSubjecSttMark=0;
											double marks=0;
											eligibility=false;
											double totalcore=0;
											//double totalcommon=0;
											//double maxcorecredit=0;
											//double product=0;
											double totalcorecredit=0;
											//double totalCoreMarks=0;
                                            //double coreMaxMarks=0;
											if(eligMap.containsKey(ugcourseid) && totalmark>=1.8 && (ugPattern.equalsIgnoreCase("CBCSS")|| ugPattern.equalsIgnoreCase("CBCSS NEW") ) && CMSConstants.UG_ELIGIBLE_COURSE_LIST_FOR_BCOMMODI.contains(ugcourseid)){

												Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
												while (markItr.hasNext()) {
													detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")||detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Complimentary")){
														cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
														credit=Double.parseDouble(detailMarkBO.getCredit());
														mult=cgpa*credit;
														totalcgpa=totalcgpa+mult;
														totalcredit=totalcredit+credit;
													}
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}							
													//lang2 mark
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}



												}
												
												if(totalcgpa>0 && totalcredit>0 ){
													total=(totalcgpa/totalcredit)*100;
													remark="Eligible";
													total=total+bonus-penalty+extramark;
													if(totalcgpa!=0 && totalcredit!=0){
														electiveSubjectMark=(totalcgpa/totalcredit);
													}
													}
											}
											// (45/100)*4 = 1.8
											else if(/*eligMap.containsKey(ugcourseid) &&*/ totalmark>=1.8 && (ugPattern.equalsIgnoreCase("CBCSS")|| ugPattern.equalsIgnoreCase("CBCSS NEW") )&& CMSConstants.UG_ELIGIBLE_COURSE_LIST_FOR_BCOMMODII.contains(ugcourseid)){

												Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
												while (markItr.hasNext()) {
													detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")||detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Complimentary")){
														cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*10;
														credit=Double.parseDouble(detailMarkBO.getCredit());
														//totalCoreMarks = totalCoreMarks+cgpa;
														//coreMaxMarks = coreMaxMarks+4;
														mult=cgpa*credit;
														totalcgpa=totalcgpa+mult;
														totalcredit=totalcredit+credit;
													}
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}							
													//lang2 mark
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}



												}
												if(totalcgpa>0 && totalcredit>0 ){
												total=(totalcgpa/totalcredit)*100;
												remark="Eligible";
												total=total+bonus-penalty+extramark;
												if(totalcgpa!=0 && totalcredit!=0){
													electiveSubjectMark=(totalcgpa/totalcredit);
												}
												}
											}



											else if(eligMap.containsKey(ugcourseid) && percentage>=45 && ugPattern.equalsIgnoreCase("OTHER") && CMSConstants.UG_ELIGIBLE_COURSE_LIST_FOR_BCOMMODI.contains(ugcourseid)){


												Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
												while (markItr.hasNext()) {
													detailMarkBO= (AdmSubjectMarkForRank) markItr.next();

													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")||detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Complimentary") ){
														marks=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*4;
														totalcore=totalcore+marks;
														//maxcorecredit=Double.parseDouble(detailMarkBO.getMaxmark());
														totalcorecredit=totalcorecredit+4;
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core"))
														electiveSubjectMark=marks;
													}										
													//lang1 mark 
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}

													//lang2 mark
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}

												}
												if(totalcore!=0 && totalcorecredit!=0){
													total=(totalcore/totalcorecredit)*1000;
													total=total+bonus-penalty+extramark;
													remark="Eligible";
												}
												
											}
											else if(eligMap.containsKey(ugcourseid) && percentage>=45 && ugPattern.equalsIgnoreCase("OTHER") && CMSConstants.UG_ELIGIBLE_COURSE_LIST_FOR_BCOMMODII.contains(ugcourseid)){


												Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
												while (markItr.hasNext()) {
													detailMarkBO= (AdmSubjectMarkForRank) markItr.next();

													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core") ||detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Complimentary")){
														marks=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*4;
														totalcore=totalcore+marks;
														//maxcorecredit=Double.parseDouble(detailMarkBO.getMaxmark());
														totalcorecredit=totalcorecredit+4;
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core"))
														electiveSubjectMark=marks;
													}										
													//lang1 mark 
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}

													//lang2 mark
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}

												}
												if(totalcore!=0 && totalcorecredit!=0){
													total=(totalcore/totalcorecredit)*1000;
													total=total+bonus-penalty+extramark;
													remark="Eligible";
												}
												
											}


											// adding indexmark object
											StudentIndexMark si=new StudentIndexMark();

											AdmAppln appln=new AdmAppln();
											appln.setId(admAppln.getId());
											si.setAdmAppln(appln);
											Course course=new Course();
											course.setId(candidatePreference.getCourse().getId());
											si.setCourse(course);

											si.setIndexMark(Double.parseDouble(twoDForm.format(total)));
											si.setRemark(remark);
											si.setCreatedDate(new Date());
											si.setLastModifiedDate(new Date());
											si.setCreatedBy(form.getUserId());
											si.setModifiedBy(form.getUserId());		

											si.setLanguage1Marks(language1Mark);
											si.setLanguage2Marks(language2Mark);
											si.setGroupMarks(groupMarks);
											si.setElectivesubmark(electiveSubjectMark);
											si.setActive(true);
											si.setPrefNo(candidatePreference.getPrefNo());
											si.setGenerateCourseId(Integer.parseInt(form.getCourseId()));

											indexmarkList.add(si);

										}//MCom OVER


										// MSc Environment Science need to get course id

										/*if(candidatePreference.getCourse().getId()==36){
											Session session=null;
											Map<Integer,String> eligMap=new HashMap<Integer, String>();
											session = HibernateUtil.getSession();
											String str="select p.uGCoursesBO.id, p.uGCoursesBO.name from PgAdmSubjectForRank p where p.course.id="+candidatePreference.getCourse().getId();
											Query query =session.createQuery(str);
											List<Object[]> list=query.list();
											Iterator<Object[]> it=list.iterator();
											while (it.hasNext()) {
												Object[] objects = (Object[]) it.next();
												eligMap.put(Integer.parseInt(objects[0].toString()),(objects[1].toString()));
											}
											session.flush();
											remark="not Eligible";
											//double maxmark=0;
											double language1Mark=0;                          
											double language2Mark=0;
											double groupMarks=0;
											double electiveSubjectMark=0;
											//double cgpamarks=0;
											double extramark=0;
											double cgpa=0;
											double credit=0;
											double mult=0;
											double totalcredit=0;
											double totalcgpa=0;
											double totalcgpaForCore = 0;
											double totalcreditForCore=0;
											//double electiveSubjectMathsMark=0;
											//double electiveSubjectIndustrialFishMark=0;
											//double electiveSubjecAquatureMark=0;
											//double marks=0;
											//double totalcore =0;
											//double coreCgpa=0;


											if( totalmark>=2 && (ugPattern.equalsIgnoreCase("CBCSS")|| ugPattern.equalsIgnoreCase("CBCSS NEW") )){
												remark="Eligible";
												Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
												while (markItr.hasNext()) {
													detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core") || detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Complimentary") ){
														cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*4;
														credit=Double.parseDouble(detailMarkBO.getCredit());
														mult=cgpa*credit;
														totalcgpa=totalcgpa+mult;
														totalcredit=totalcredit+credit;
													}
													// total marks scored for part||| core/main subjects
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){
														cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*4;
														credit=Double.parseDouble(detailMarkBO.getCredit());
														mult=cgpa*credit;
														totalcgpaForCore=totalcgpaForCore+mult;
														totalcreditForCore=totalcreditForCore+credit;
													
														//mult=cgpa*credit;

													}

													//lang1 mark
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}

													//lang2 mark
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}

												}

													remark="Eligible";
													//electiveSubjectMark=totalcgpa/totalcredit;
													if(totalcredit>0)
													total=(totalcgpa/totalcredit)*250;
													if(totalcreditForCore>0)
													totalcgpaForCore=(totalcgpaForCore/totalcreditForCore)*150;
													total=totalcgpaForCore+total;
													total=total+bonus-penalty+extramark;
											}

											else if(eligMap.containsKey(ugcourseid)&& percentage>=50 && ugPattern.equalsIgnoreCase("OTHER")){

												Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
												while (markItr.hasNext()) {
													detailMarkBO= (AdmSubjectMarkForRank) markItr.next();

													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core") || detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Complimentary") ){
														cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*4;
														//marks=Double.parseDouble(detailMarkBO.getMaxmark());
														totalcgpa=totalcgpa+cgpa;
														totalcredit=totalcredit+4;
														
														
														if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){
															cgpa=(Double.parseDouble(detailMarkBO.getObtainedmark())/Double.parseDouble(detailMarkBO.getMaxmark()))*4;
															totalcgpaForCore=totalcgpaForCore+cgpa;
															totalcreditForCore=totalcreditForCore+4;
														}

													}

													// total marks scored for part||| core/main subjects
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){
														cgpa=Double.parseDouble(detailMarkBO.getConversionmark());
														credit=Double.parseDouble(detailMarkBO.getCredit());
														totalcreditForCore=totalcreditForCore+credit;
														mult=cgpa*credit;
														totalcgpaForCore=totalcgpaForCore+mult;
													}

													//lang1 mark 
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}

													//lang2 mark
													if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
														language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
													}
												}


													remark="Eligible";
													if(totalcredit>0)
													total=(totalcgpa/totalcredit)*1000;
													if(totalcreditForCore>0)
													totalcgpaForCore=(totalcgpaForCore/totalcreditForCore)*600;
													total=totalcgpaForCore+total;
													total=total+bonus-penalty+extramark;
											}

											// adding indexmark object
											StudentIndexMark si=new StudentIndexMark();
											AdmAppln appln=new AdmAppln();
											appln.setId(admAppln.getId());
											si.setAdmAppln(appln);
											Course course=new Course();
											course.setId(candidatePreference.getCourse().getId());
											si.setCourse(course);

											si.setIndexMark(Double.parseDouble(twoDForm.format(total)));
											si.setRemark(remark);
											si.setCreatedDate(new Date());
											si.setLastModifiedDate(new Date());
											si.setCreatedBy(form.getUserId());
											si.setModifiedBy(form.getUserId());		

											si.setLanguage1Marks(language1Mark);
											si.setLanguage2Marks(language2Mark);
											si.setGroupMarks(groupMarks);
											si.setElectivesubmark(electiveSubjectMark);
											si.setActive(true);
											si.setPrefNo(candidatePreference.getPrefNo());
											si.setGenerateCourseId(Integer.parseInt(form.getCourseId()));

											indexmarkList.add(si);


										}*/	 		 



										/*  else if(candidatePreference.getCourse().getId()==20){

	                        	   Session session=null;
	                               Map<Integer,String> eligMap=new HashMap<Integer, String>();
	                               session = HibernateUtil.getSession();
	                       		String str="select p.uGCoursesBO.id, p.uGCoursesBO.name from PgAdmSubjectForRank p where p.course.id="+candidatePreference.getCourse().getId();
	                       		Query query =session.createQuery(str);
	                       		List<Object[]> list=query.list();
	                       		Iterator<Object[]> it=list.iterator();
	                       		while (it.hasNext()) {
	                       			Object[] objects = (Object[]) it.next();
	                       			eligMap.put(Integer.parseInt(objects[0].toString()),(objects[1].toString()));
	                       		}
	                       			session.flush();
	                       		  remark="not Eligible";
	                       		 double maxmark=0;
	                      	   double language1Mark=0;                          
	                      	   double language2Mark=0;
	                      	   double groupMarks=0;
	                      	   double electiveSubjectMark=0;
	                      	   double cgpamarks=0;
	                      	   double extramark=0;
	                      	   double cgpa=0;
	                      	   double credit=0;
	                      	   double mult=0;
	                      	   double totalcredit=0;
	                      	   double totalcgpa=0;
	                      	   double electiveSubjectMathsMark=0;
	                      	   double comonalMark=0;
	                      	   double coremalMark=0;
	                      	   double electiveSubjecSttMark=0;
	                      	   double marks=0;
	                      	   eligibility=false;
	                      	   double totalcore=0;
	                      	   double totalcommon=0;
	                      	   double maxcorecredit=0;
	                      	   double product=0;
	                      	   double totalcorecredit=0;
	                      	 	if(eligMap.containsKey(ugcourseid) && totalmark>=1.8 && ugPattern.equalsIgnoreCase("CBCSS") && CMSConstants.UG_ELIGIBLE_COURSE_LIST_FOR_BCOMMODII.contains(ugcourseid)){

	                      	   Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
	                      	   while (markItr.hasNext()) {
										detailMarkBO= (AdmSubjectMarkForRank) markItr.next();
									if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core")){
										cgpa=Double.parseDouble(detailMarkBO.getConversionmark());
											credit=Double.parseDouble(detailMarkBO.getCredit());
											mult=cgpa*credit;
											totalcgpa=totalcgpa+mult;
											totalcredit=totalcredit+credit;
									}
											if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
												language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
												   }							
										//lang2 mark
										if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
										language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
										   }



	                      	 }

	                      	   total=(totalcgpa/totalcredit)*250;
	                      	 remark="Eligible";
	                      	 total=total+bonus-penalty+extramark;
	                      	electiveSubjectMark=(totalcgpa/totalcredit);
	                      	 }



	                        else if(eligMap.containsKey(ugcourseid) && percentage>=50 && ugPattern.equalsIgnoreCase("OTHER") && CMSConstants.UG_ELIGIBLE_COURSE_LIST_FOR_BCOMMODII.contains(ugcourseid)){


			                        	   Iterator<AdmSubjectMarkForRank> markItr=detailMarks.iterator();
			                        	   while (markItr.hasNext()) {
												detailMarkBO= (AdmSubjectMarkForRank) markItr.next();

												if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Core") ){
													marks=Double.parseDouble(detailMarkBO.getObtainedmark());
													totalcore=totalcore+marks;
													maxcorecredit=Double.parseDouble(detailMarkBO.getMaxmark());
													totalcorecredit=totalcorecredit+maxcorecredit;
													   }										
												//lang1 mark 
												if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
												language1Mark=Double.parseDouble(detailMarkBO.getConversionmark());
												  }

												//lang2 mark
												if(detailMarkBO.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Common") && !detailMarkBO.getAdmSubjectForRank().getName().equalsIgnoreCase("English")){
												language2Mark=Double.parseDouble(detailMarkBO.getConversionmark());
												   }

			                       		}

			                        	   total=(totalcore/totalcorecredit)*1000;
			                        	   electiveSubjectMark=(totalcore/totalcorecredit); 
			                        	   total=total+bonus-penalty+extramark;
			                        	   remark="Eligible";
			                        	   }

	                     // adding indexmark object
	                 	   StudentIndexMark si=new StudentIndexMark();

								AdmAppln appln=new AdmAppln();
								appln.setId(admAppln.getId());
								si.setAdmAppln(appln);
								Course course=new Course();
								course.setId(candidatePreference.getCourse().getId());
								si.setCourse(course);

								si.setIndexMark(Double.parseDouble(twoDForm.format(total)));
								si.setRemark(remark);
								si.setCreatedDate(new Date());
								si.setLastModifiedDate(new Date());
								si.setCreatedBy(form.getUserId());
								si.setModifiedBy(form.getUserId());		

								si.setLanguage1Marks(language1Mark);
								si.setLanguage2Marks(language2Mark);
								si.setGroupMarks(groupMarks);
								si.setElectivesubmark(electiveSubjectMark);
								si.setActive(true);
								si.setPrefNo(candidatePreference.getPrefNo());
								si.setGenerateCourseId(Integer.parseInt(form.getCourseId()));

								indexmarkList.add(si);

	                           }//OVER
										 */								}
								}

							}
						}
					}

					// M ScAqua Culture and Fish Processing




					// this else part is backlogs fellows storing
					else{


						StudentIndexMark si=new StudentIndexMark();

						AdmAppln appln=new AdmAppln();
						appln.setId(admAppln.getId());
						si.setAdmAppln(appln);

						Course course=new Course();
						course.setId(admAppln.getCourse().getId());
						si.setCourse(course);

						si.setIndexMark(0.0);
						si.setRemark("Backlog is there or didn't pay fee");
						si.setCreatedDate(new Date());
						si.setLastModifiedDate(new Date());
						si.setCreatedBy(form.getUserId());
						si.setModifiedBy(form.getUserId());

						si.setLanguage1Marks(0.0);
						si.setLanguage2Marks(0.0);
						si.setGroupMarks(0.0);
						si.setElectivesubmark(0.0);
						si.setActive(true);
						si.setPrefNo(1);
						si.setGenerateCourseId(Integer.parseInt(form.getCourseId()));

						indexmarkList.add(si);
					}

				}// aditional info while close

			//}// main adm while close

		//}// student if close check
		//}
			}
		}
		}
		catch(Exception e)	{
			System.out.println("helper==================index mark ======================="+e);
			throw e;
		}
		return indexmarkList;

	}








	//allotting courses for all people based on student highest index mark priority
	public boolean generateCourseAllotmentOnIndexMark(ApplicationEditForm form)throws Exception{




		boolean isAdded=false;
		/*
		try{


		IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
		List<Course> coursesList=CourseTransactionImpl.getInstance().getCourses(Integer.parseInt(form.getProgramTypeId()));

		int size=0;
		size=coursesList.size();


		//loop for checking preferences based on course size 

		for(int i=1;i<=size;i++){



		// 	getting admapl list
		List admList=txn.getAdmApplonStudentIndexMark(form);
		Iterator admIterator=admList.iterator();

		// loop for getting student on admappl
		while(admIterator.hasNext()){	
			AdmAppln a=null;
			Object[] object = (Object[]) admIterator.next();

			if(object[0]!=null){
				a=(AdmAppln)object[0];
			}


		// get student rank based in admid and preno
		StudentRank rank=txn.getRankOnAdmApplPreference(a.getId(), i);


		//check student have rank or not 
		if(rank!=null){


		Course course1=rank.getCourse();

		form.setCourseId(new Integer(course1.getId()).toString());

		int totseats=0;
		int scseat=0;
		int stseat=0;
		int bcseat=0;
		int museat=0;
		int obxseat=0;
		int obhseat=0;
		int lcseat=0;
		int genseat=0;
		int remainseats=0;
		int rankcutoff=0;
		//OBC		SC		ST		MU		OBX		OBH		LC

		int sccount=0;
		int stcount=0;
		int bccount=0;
		int mucount=0;
		int obxcount=0;
		int obhcount=0;
		int lccount=0;
		int gencount=0;
		int totcount=0;


		//for testing single course wise
		if(course1.getId()==12){



		//getting SeatAllocation for different addmitted through caste
		List<SeatAllocation> seatlist=txn.getSeatAllocation(form);

		//assign seats based on caste
		Iterator<SeatAllocation> it=seatlist.iterator();
		while(it.hasNext()){
			SeatAllocation st=it.next();

			if("SC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				scseat=st.getNoOfSeats();
			}else if("ST".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				stseat=st.getNoOfSeats();
			}else if("OBC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				bcseat=st.getNoOfSeats();
			}else if("General".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				genseat=st.getNoOfSeats();
			}else if("OBX".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				obxseat=st.getNoOfSeats();
			}else if("OBH".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				obhseat=st.getNoOfSeats();
			}else if("MU".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				museat=st.getNoOfSeats();
			}else if("LC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				lcseat=st.getNoOfSeats();
			}


		}// seat while



		// create new 	StudentCourseAllotment list object based on course wise
		List<StudentCourseAllotment> rankList= new ArrayList<StudentCourseAllotment>();

		//getting allotted students from StudentCourseAllotment for duplication checking
		Map<Integer,Integer> allotedmap=txn.getAllotedStudent(form);

		//calculate total seats allotement and rank cutoff and preference based on course
		totseats=course1.getMaxIntake();

		int cut=(totseats*20)/100;

		if(1<i){
			remainseats=totseats+(cut*i);
			rankcutoff=rankcutoff+remainseats;
		}else{
			rankcutoff=rankcutoff+totseats+remainseats;
		}



		form.setTotalSeats(totseats);
		form.setCutoffRank(rankcutoff);
		form.setPreNo(i);

		System.out.println("1st time pre="+i+"======remining seats="+remainseats+"=====cut of="+rankcutoff+"======course="+course1.getId()+course1.getName()+"======admid="+a.getId()+"=======rank="+rank.getRank()+"========index="+rank.getIndexMark());


		//getting ranks based on above conditions
		List<StudentRank> rankDetails = txn.getStudentRank(form,a.getId());

		//convert properties StudentRank  to StudentCourseAllotmentTo 
		List<StudentCourseAllotmentTo> marktolist=ApplicationRankHelper.getInstance().convertStudentRankBOToTO(rankDetails);


		// check rank list size is students there are not
			if(marktolist!=null && marktolist.size()!=0){








		//old fillup seats count based on course wise
		List generalCountList=txn.getSeatCountOnCourse("General","", course1.getId(),form);
		if(generalCountList.size()!=0){
			gencount=generalCountList.size();
		}

		List scCountList=txn.getSeatCountOnCourse("","SC", course1.getId(),form);
		if(scCountList.size()!=0){
			sccount=scCountList.size();
		}


		List stCountList=txn.getSeatCountOnCourse("","ST", course1.getId(),form);
		if(stCountList.size()!=0){
			stcount=stCountList.size();
		}


		List obcCountList=txn.getSeatCountOnCourse("","OBC", course1.getId(),form);
		if(obcCountList.size()!=0){
			bccount=obcCountList.size();
		}


		List obhCountList=txn.getSeatCountOnCourse("","OBH", course1.getId(),form);
		if(obhCountList.size()!=0){
			obhcount=obhCountList.size();
		}


		List obxCountList=txn.getSeatCountOnCourse("","OBX", course1.getId(),form);
		if(obxCountList.size()!=0){
			obxcount=obxCountList.size();
		}


		List muCountList=txn.getSeatCountOnCourse("","MU", course1.getId(),form);
		if(muCountList.size()!=0){
			mucount=muCountList.size();
		}


		List lcCountList=txn.getSeatCountOnCourse("","LC", course1.getId());
		if(lcCountList.size()!=0){
			lccount=lcCountList.size();
		}


		System.out.println("main gen="+gencount+"======sc="+sccount+"=====st="+stcount+"======obc="+bccount+"======obh="+obhcount+"=======obx="+obxcount+"========mu="+mucount);


		//add old count to tot count
		totcount=lccount+mucount+obxcount+obhcount+bccount+stcount+sccount+gencount;

		AdmApplnTO at=null;
		PersonalDataTO pt=null;
		CourseTO cto=null;
		String caste="";


		//check condition between total count and total seats count
		if(totcount<totseats) {


		//start general
		Iterator<StudentCourseAllotmentTo> i4=marktolist.iterator();
		while(i4.hasNext()){
			StudentCourseAllotmentTo allotmentTo=i4.next();

			at=allotmentTo.getAdmApplnTO();
			pt=at.getPersonalData();
			cto=allotmentTo.getCourseTO();

			//totseats=cto.getMaxInTake();
			caste=pt.getSubregligionName();


			//add seat to bo
			if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

			if(!allotmentTo.getIsAlloted()){
			if(gencount<genseat){
				allotmentTo.setAlloted(true);



				StudentCourseAllotment studentRank =new StudentCourseAllotment();

				   AdmAppln appln=new AdmAppln();
				   appln.setId(allotmentTo.getAdmApplnTO().getId());
				   studentRank.setAdmAppln(appln);

				   Course course=new Course();
				   course.setId(allotmentTo.getCourseTO().getId());
				   studentRank.setCourse(course);

				   studentRank.setIndexMark(allotmentTo.getIndexMark());
				   studentRank.setRank(allotmentTo.getRank());
				   studentRank.setPrefNo(allotmentTo.getPrefNo());
				   studentRank.setIsAlloted(true);
				   studentRank.setActive(true);
				   studentRank.setIsAssigned(false);
				   studentRank.setIsCaste(false);
				   studentRank.setIsGeneral(true);
				   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
				   studentRank.setAllotmentNo(1);



				   gencount++;
				   totcount++;
				   rankList.add(studentRank);
				}


			}


		}// main if


		}// general close





		//sc allot
		Iterator< StudentCourseAllotmentTo> iterator=marktolist.iterator();
		while(iterator.hasNext()){
			StudentCourseAllotmentTo allotmentTo=iterator.next();

			at=allotmentTo.getAdmApplnTO();
			pt=at.getPersonalData();
			cto=allotmentTo.getCourseTO();

			//totseats=cto.getMaxInTake();
			caste=pt.getSubregligionName();


			//add seat to bo
			if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

			if(!allotmentTo.getIsAlloted()){
				if(caste.equalsIgnoreCase("SC")){
					if(sccount<scseat){
						allotmentTo.setAlloted(true);



							StudentCourseAllotment studentRank =new StudentCourseAllotment();

							   AdmAppln appln=new AdmAppln();
							   appln.setId(allotmentTo.getAdmApplnTO().getId());
							   studentRank.setAdmAppln(appln);

							   Course course=new Course();
							   course.setId(allotmentTo.getCourseTO().getId());
							   studentRank.setCourse(course);

							   studentRank.setIndexMark(allotmentTo.getIndexMark());
							   studentRank.setRank(allotmentTo.getRank());
							   studentRank.setPrefNo(allotmentTo.getPrefNo());
							   studentRank.setIsAlloted(true);
							   studentRank.setActive(true);
							   studentRank.setIsAssigned(false);
							   studentRank.setIsCaste(true);
							   studentRank.setIsGeneral(false);
							   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
							   studentRank.setAllotmentNo(1);



							   sccount++;
							   totcount++;
							   rankList.add(studentRank);
						}



					}
				}




			}// main if


			}//sc close






			//st allot
				Iterator<StudentCourseAllotmentTo> i1=marktolist.iterator();
				while(i1.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i1.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();
					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("ST")){
					if(stcount<stseat){
						allotmentTo.setAlloted(true);



						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(1);




							stcount++;
							totcount++;
							rankList.add(studentRank);
						}



					}
				}



					}// main if





				}//st close








				//obc allot
				Iterator<StudentCourseAllotmentTo> i2=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i2.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("OBC")){
					if(bccount<bcseat){
						allotmentTo.setAlloted(true);


						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(1);



						   bccount++;
						   totcount++;
						   rankList.add(studentRank);
						}

					}

					}




				}// main if



				}	 //bc close






				//mu allot
				Iterator<StudentCourseAllotmentTo> i3=marktolist.iterator();
				while(i2.hasNext()){

					StudentCourseAllotmentTo allotmentTo=i3.next();
					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
					if(!allotmentTo.getIsAlloted()){

					if(caste.equalsIgnoreCase("MU")){
					if(mucount<museat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(1);




						mucount++;
						totcount++;
						rankList.add(studentRank);
					}

					}


					}
				}// main if



				}	 //mu close





				//obx allot
				Iterator<StudentCourseAllotmentTo> i5=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i5.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("OBX")){
					if(obxcount<obxseat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(1);


						obxcount++;
						totcount++;
						rankList.add(studentRank);
					}

					}

					}
				}// contain close	



				}	 //bc close







				//obh allot
				Iterator<StudentCourseAllotmentTo> i6=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i6.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();


					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("OBH")){
					if(obhcount<obhseat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(1);




						obhcount++;
						totcount++;
						 rankList.add(studentRank);
					}

					}

				}// main if

				}// contain close	


				}	 //bc close






				//lc allot
				Iterator<StudentCourseAllotmentTo> i7=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i7.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("LC")){
					if(lccount<lcseat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();


						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
							studentRank.setAllotmentNo(1);

						lccount++;
						totcount++;
						rankList.add(studentRank);
					}

					}

					}// main if

					}// contain close		


				}	 //lc close


			}// totseat if close	



		// adding records course wise in database
		isAdded=txn.generateCourseAllotment(rankList);

		}// rank list size if check close
			else{ 

				System.out.println("already alloted pre="+i+"======remining seats="+remainseats+"=====cut of="+rankcutoff+"======course="+course1.getId()+course1.getName()+"======admid="+a.getId()+"=======rank="+rank.getRank()+"========index="+rank.getIndexMark());

			}

		}// close testing course wise

		}// student have rank or not	

		}// // loop for getting student on admappl	



		}// preference for loop close	



	}// try close
		catch(Exception e){

			isAdded=false;
			Session session = null;
			Transaction transaction = null;
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query1=session.createQuery("delete StudentCourseAllotment s where s.createdDate = current_date");
			//query.setString("current_date1",date);
			query1.executeUpdate();
			transaction.commit();
			session.close();
			System.out.println("helper===============Error occured in while alloting courses to student ================="+e);
			throw e;
		}finally{

		}
		 */
		return isAdded;


	}










	// allotting courses for all people based on preferences priority

	public boolean generateCourseAllotmentOnPreference(ApplicationEditForm form)throws Exception{


		boolean isAdded=false;
		/*
		try{


		IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
		List<Course> coursesList=CourseTransactionImpl.getInstance().getCourses(Integer.parseInt(form.getProgramTypeId()));

		int size=0;
		size=coursesList.size();


		//loop for checking preferences based on course size 

		for(int i=1;i<=size;i++){




		//loop for alloted seats based on courses 	
		Iterator<Course> courseIterator=coursesList.iterator();
		while(courseIterator.hasNext()){
		Course course1=courseIterator.next();

		form.setCourseId(new Integer(course1.getId()).toString());

		int totseats=0;
		int scseat=0;
		int stseat=0;
		int bcseat=0;
		int museat=0;
		int obxseat=0;
		int obhseat=0;
		int lcseat=0;
		int genseat=0;
		int remainseats=0;
		int rankcutoff=0;
		//OBC		SC		ST		MU		OBX		OBH		LC

		int sccount=0;
		int stcount=0;
		int bccount=0;
		int mucount=0;
		int obxcount=0;
		int obhcount=0;
		int lccount=0;
		int gencount=0;
		int totcount=0;




		//getting SeatAllocation for different addmitted through caste
		List<SeatAllocation> seatlist=txn.getSeatAllocation(form);

		//assign seats based on caste
		Iterator<SeatAllocation> it=seatlist.iterator();
		while(it.hasNext()){
			SeatAllocation st=it.next();

			if("SC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				scseat=st.getNoOfSeats();
			}else if("ST".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				stseat=st.getNoOfSeats();
			}else if("OBC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				bcseat=st.getNoOfSeats();
			}else if("General".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				genseat=st.getNoOfSeats();
			}else if("OBX".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				obxseat=st.getNoOfSeats();
			}else if("OBH".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				obhseat=st.getNoOfSeats();
			}else if("MU".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				museat=st.getNoOfSeats();
			}else if("LC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				lcseat=st.getNoOfSeats();
			}


		}// seat while



		// create new 	StudentCourseAllotment list object based on course wise
		List<StudentCourseAllotment> rankList= new ArrayList<StudentCourseAllotment>();

		//getting allotted students from StudentCourseAllotment for duplication checking
		Map<Integer,Integer> allotedmap=txn.getAllotedStudent(form);

		//calculate total seats allotement and rank cutoff and preference based on course
		totseats=course1.getMaxIntake();

		int cut=(totseats*20)/100;

		if(1<i){
			remainseats=totseats+(cut*i);
			rankcutoff=rankcutoff+remainseats;
		}else{
			rankcutoff=rankcutoff+totseats+remainseats;
		}



		form.setTotalSeats(totseats);
		form.setCutoffRank(rankcutoff);
		form.setPreNo(i);

		System.out.println("1st time pre="+i+"======remining seats="+remainseats+"=====cut of="+rankcutoff+"======course="+course1.getId()+course1.getName());

		//getting ranks based on above conditions
		List<StudentRank> rankDetails = txn.getStudentRank(form,0);

		//convert properties StudentRank  to StudentCourseAllotmentTo 
		List<StudentCourseAllotmentTo> marktolist=ApplicationRankHelper.getInstance().convertStudentRankBOToTO(rankDetails);


		// check rank list size is students there are not
			if(marktolist!=null && marktolist.size()!=0){








		//old fillup seats count based on course wise
		List generalCountList=txn.getSeatCountOnCourse("General","", course1.getId(),form);
		if(generalCountList.size()!=0){
			gencount=generalCountList.size();
		}

		List scCountList=txn.getSeatCountOnCourse("","SC", course1.getId(),form);
		if(scCountList.size()!=0){
			sccount=scCountList.size();
		}


		List stCountList=txn.getSeatCountOnCourse("","ST", course1.getId(),form);
		if(stCountList.size()!=0){
			stcount=stCountList.size();
		}


		List obcCountList=txn.getSeatCountOnCourse("","OBC", course1.getId(),form);
		if(obcCountList.size()!=0){
			bccount=obcCountList.size();
		}


		List obhCountList=txn.getSeatCountOnCourse("","OBH", course1.getId(),form);
		if(obhCountList.size()!=0){
			obhcount=obhCountList.size();
		}


		List obxCountList=txn.getSeatCountOnCourse("","OBX", course1.getId(),form);
		if(obxCountList.size()!=0){
			obxcount=obxCountList.size();
		}


		List muCountList=txn.getSeatCountOnCourse("","MU", course1.getId(),form);
		if(muCountList.size()!=0){
			mucount=muCountList.size();
		}


		List lcCountList=txn.getSeatCountOnCourse("","LC", course1.getId());
		if(lcCountList.size()!=0){
			lccount=lcCountList.size();
		}

		System.out.println("main gen="+gencount+"======sc="+sccount+"=====st="+stcount+"======obc="+bccount+"======obh="+obhcount+"=======obx="+obxcount+"========mu="+mucount);

		//add old count to tot count
		totcount=lccount+mucount+obxcount+obhcount+bccount+stcount+sccount+gencount;

		AdmApplnTO at=null;
		PersonalDataTO pt=null;
		CourseTO cto=null;
		String caste="";


		//check condition between total count and total seats count
		if(totcount<totseats) {


		//start general
		Iterator<StudentCourseAllotmentTo> i4=marktolist.iterator();
		while(i4.hasNext()){
			StudentCourseAllotmentTo allotmentTo=i4.next();

			at=allotmentTo.getAdmApplnTO();
			pt=at.getPersonalData();
			cto=allotmentTo.getCourseTO();

			//totseats=cto.getMaxInTake();
			caste=pt.getSubregligionName();


			//add seat to bo
			if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

			if(!allotmentTo.getIsAlloted()){
			if(gencount<genseat){
				allotmentTo.setAlloted(true);



				StudentCourseAllotment studentRank =new StudentCourseAllotment();

				   AdmAppln appln=new AdmAppln();
				   appln.setId(allotmentTo.getAdmApplnTO().getId());
				   studentRank.setAdmAppln(appln);

				   Course course=new Course();
				   course.setId(allotmentTo.getCourseTO().getId());
				   studentRank.setCourse(course);

				   studentRank.setIndexMark(allotmentTo.getIndexMark());
				   studentRank.setRank(allotmentTo.getRank());
				   studentRank.setPrefNo(allotmentTo.getPrefNo());
				   studentRank.setIsAlloted(true);
				   studentRank.setActive(true);
				   studentRank.setIsAssigned(false);
				   studentRank.setIsCaste(false);
				   studentRank.setIsGeneral(true);
				   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
				   studentRank.setAllotmentNo(1);



				   gencount++;
				   totcount++;
				   rankList.add(studentRank);
				}


			}


		}// main if


		}// general close








		//sc allot
		Iterator< StudentCourseAllotmentTo> iterator=marktolist.iterator();
		while(iterator.hasNext()){
			StudentCourseAllotmentTo allotmentTo=iterator.next();

			at=allotmentTo.getAdmApplnTO();
			pt=at.getPersonalData();
			cto=allotmentTo.getCourseTO();

			//totseats=cto.getMaxInTake();
			caste=pt.getSubregligionName();


			//add seat to bo
			if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

			if(!allotmentTo.getIsAlloted()){
				if(caste.equalsIgnoreCase("SC")){
					if(sccount<scseat){
						allotmentTo.setAlloted(true);



							StudentCourseAllotment studentRank =new StudentCourseAllotment();

							   AdmAppln appln=new AdmAppln();
							   appln.setId(allotmentTo.getAdmApplnTO().getId());
							   studentRank.setAdmAppln(appln);

							   Course course=new Course();
							   course.setId(allotmentTo.getCourseTO().getId());
							   studentRank.setCourse(course);

							   studentRank.setIndexMark(allotmentTo.getIndexMark());
							   studentRank.setRank(allotmentTo.getRank());
							   studentRank.setPrefNo(allotmentTo.getPrefNo());
							   studentRank.setIsAlloted(true);
							   studentRank.setActive(true);
							   studentRank.setIsAssigned(false);
							   studentRank.setIsCaste(true);
							   studentRank.setIsGeneral(false);
							   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
							   studentRank.setAllotmentNo(1);



							   sccount++;
							   totcount++;
							   rankList.add(studentRank);
						}



					}
				}




			}// main if


			}//sc close









			//st allot
				Iterator<StudentCourseAllotmentTo> i1=marktolist.iterator();
				while(i1.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i1.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();
					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("ST")){
					if(stcount<stseat){
						allotmentTo.setAlloted(true);



						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(1);




							stcount++;
							totcount++;
							rankList.add(studentRank);
						}



					}
				}



					}// main if


				}//st close








				//obc allot
				Iterator<StudentCourseAllotmentTo> i2=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i2.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("OBC")){
					if(bccount<bcseat){
						allotmentTo.setAlloted(true);


						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(1);



						   bccount++;
						   totcount++;
						   rankList.add(studentRank);
						}

					}

					}




				}// main if


				}	 //bc close






				//mu allot
				Iterator<StudentCourseAllotmentTo> i3=marktolist.iterator();
				while(i2.hasNext()){

					StudentCourseAllotmentTo allotmentTo=i3.next();
					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
					if(!allotmentTo.getIsAlloted()){

					if(caste.equalsIgnoreCase("MU")){
					if(mucount<museat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(1);




						mucount++;
						totcount++;
						rankList.add(studentRank);
					}

					}


					}
				}// main if


				}	 //mu close





				//obx allot
				Iterator<StudentCourseAllotmentTo> i5=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i5.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("OBX")){
					if(obxcount<obxseat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(1);


						obxcount++;
						totcount++;
						rankList.add(studentRank);
					}

					}

					}
				}// contain close	



				}	 //bc close







				//obh allot
				Iterator<StudentCourseAllotmentTo> i6=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i6.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();


					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("OBH")){
					if(obhcount<obhseat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(1);




						obhcount++;
						totcount++;
						 rankList.add(studentRank);
					}

					}

				}// main if

				}// contain close	


				}	 //bc close






				//lc allot
				Iterator<StudentCourseAllotmentTo> i7=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i7.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("LC")){
					if(lccount<lcseat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();


						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
							studentRank.setAllotmentNo(1);

						lccount++;
						totcount++;
						rankList.add(studentRank);
					}

					}

					}// main if

					}// contain close		


				}	 //lc close


			}// totseat if close	



		// adding records course wise in database
		isAdded=txn.generateCourseAllotment(rankList);

		}// rank list size if check close

		}// course alloted  while loop close	



		}// preference for loop close	



	}// try close
		catch(Exception e){

			isAdded=false;
			Session session = null;
			Transaction transaction = null;
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query1=session.createQuery("delete StudentCourseAllotment s where s.createdDate = current_date");
			//query.setString("current_date1",date);
			query1.executeUpdate();
			transaction.commit();

			System.out.println("helper===============Error occured in while alloting courses to student ================="+e);
			throw e;
		}finally{
			//session.flush();
		}
		 */
		return isAdded;
	}






	// convert bos to tos

	public List<StudentCourseAllotmentTo> convertStudentRankBOToTO(List<StudentRank> studentRank) {
		log.info("enter convertIncomeBOToTO" );
		List<StudentCourseAllotmentTo> courseallotlist= new ArrayList<StudentCourseAllotmentTo>();
		if(studentRank!=null){
			Iterator<StudentRank> itr= studentRank.iterator();
			while (itr.hasNext()) {

				StudentRank studentRank1 = (StudentRank) itr.next();
				StudentCourseAllotmentTo to= new StudentCourseAllotmentTo();
				to.setId(studentRank1.getId());

				AdmAppln appln=studentRank1.getAdmAppln();
				AdmApplnTO applnTO=new AdmApplnTO();
				applnTO.setId(appln.getId());
				applnTO.setApplnNo(appln.getApplnNo());
				applnTO.setAppliedYear(appln.getAppliedYear());

				PersonalData data=appln.getPersonalData();
				PersonalDataTO dataTO=new PersonalDataTO();
				dataTO.setId(data.getId());
				dataTO.setFirstName(data.getFirstName());
				dataTO.setGender(data.getGender());
				if(data.getCaste()!=null){
					dataTO.setCasteId(new Integer(data.getCaste().getId()).toString());
					dataTO.setCasteCategory(data.getCaste().getName());

				}
				if(data.getReligion()!=null){
					dataTO.setReligionId(new Integer(data.getReligion().getId()).toString());
					dataTO.setReligionName(data.getReligion().getName());
				}
				if(data.getReligionSection()!=null){
					dataTO.setSubregligionName(data.getReligionSection().getName());
					dataTO.setSubReligionId(new Integer(data.getReligionSection().getId()).toString());
				}
				dataTO.setRuralUrban(data.getRuralUrban());
				if(data.getIsSportsPerson()!=null){
					dataTO.setSportsPerson(data.getIsSportsPerson());
				}
				if(data.getIsHandicapped()!=null){
					dataTO.setHandicapped(data.getIsHandicapped());
				}
				if(data.getIsNsscertificate()!=null){
					dataTO.setNcccertificate(data.getIsNcccertificate());
				}
				if(data.getIsNsscertificate()!=null){
					dataTO.setNsscertificate(data.getIsNsscertificate());
				}
				if(data.getIsExcervice()!=null){
					dataTO.setExservice(data.getIsExcervice());
				}


				applnTO.setPersonalData(dataTO);

				Course c=studentRank1.getCourse();
				CourseTO courseTO=new CourseTO();
				courseTO.setId(c.getId());
				courseTO.setMaxInTake(c.getMaxIntake());
				List<SeatAllocationTO> seatlist=new ArrayList<SeatAllocationTO>();
				Set<SeatAllocation> set=c.getSeatAllocations();
				Iterator<SeatAllocation> i=set.iterator();

				while(i.hasNext()){
					SeatAllocation s=i.next();
					SeatAllocationTO st=new SeatAllocationTO();
					st.setId(s.getId());
					st.setNoofSeats(s.getNoOfSeats());
					st.setAdmittedThroughId(s.getAdmittedThrough().getId());
					st.setAdmittedThroughName(s.getAdmittedThrough().getName());
					seatlist.add(st);
				}

				courseTO.setSeatAllocation(seatlist);
				applnTO.setCourse(courseTO);

				to.setAdmApplnTO(applnTO);

				to.setAlloted(false);
				to.setRank(studentRank1.getRank());
				to.setPrefNo(studentRank1.getPrefNo());
				to.setIndexMark(studentRank1.getIndexMark());
				to.setCourseTO(courseTO);

				courseallotlist.add(to);

			}
		}
		log.info("exit convertIncomeBOToTO" );
		return courseallotlist;
	}





	// get student list for course assignment
	public List<StudentCourseAllotmentTo> copyBoToTO(List<StudentCourseAllotment> studentList, HashMap<Integer, Integer> existMap) {

		List<StudentCourseAllotmentTo> studentDetailsToList=new LinkedList<StudentCourseAllotmentTo>();

		Iterator<StudentCourseAllotment> it= studentList.iterator();
		while(it.hasNext()){

			StudentCourseAllotmentTo studentDetailsTo=new StudentCourseAllotmentTo();
			StudentCourseAllotment rank=it.next();

			if(existMap.containsKey(rank.getAdmAppln().getId())){
				studentDetailsTo.setTempChecked("on");
				studentDetailsTo.setChecked("on");
			}

			studentDetailsTo.setStudentId(String.valueOf(rank.getAdmAppln().getId()));
			studentDetailsTo.setStudentName(rank.getAdmAppln().getPersonalData().getFirstName());
			studentDetailsTo.setAlloted(rank.getIsAlloted());
			studentDetailsTo.setGroupMarks(rank.getGroupMarks());
			studentDetailsTo.setId(rank.getId());
			studentDetailsTo.setPrefNo(rank.getPrefNo());
			studentDetailsTo.setRank(rank.getRank());
			studentDetailsTo.setIndexMark(rank.getIndexMark());
			studentDetailsTo.setLanguage1Marks(rank.getLanguage1Marks());
			studentDetailsTo.setLanguage2Marks(rank.getLanguage2Marks());
			studentDetailsTo.setActive(rank.getIsActive());	
			studentDetailsTo.setRemark(rank.getRemark());
			studentDetailsTo.setIsSatisfied(rank.getIsSatisfied());

			CourseTO courseTO=new CourseTO();
			courseTO.setId(rank.getCourse().getId());
			courseTO.setName(rank.getCourse().getName());
			studentDetailsTo.setCourseTO(courseTO);

			AdmApplnTO applnTO=new AdmApplnTO();
			applnTO.setId(rank.getAdmAppln().getId());
			studentDetailsTo.setAdmApplnTO(applnTO);


			studentDetailsToList.add(studentDetailsTo);

		}


		return studentDetailsToList;

	}




	//allotting courses multiple times for all people based on student highest index mark priority
	public boolean generateCourseAllotmentOnIndexMarkMultipleTimes(ApplicationEditForm form)throws Exception{




		boolean isAdded=false;
		/*
		try{


		IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
		List<Course> coursesList=CourseTransactionImpl.getInstance().getCourses(Integer.parseInt(form.getProgramTypeId()));

		int size=0;
		size=coursesList.size();


		//loop for checking preferences based on course size 

		for(int i=1;i<=size;i++){



		// 	getting admapl list
		List admList=txn.getAdmApplonStudentIndexMarkMultipleTimes(form);
		Iterator admIterator=admList.iterator();

		// loop for getting student on admappl
		while(admIterator.hasNext()){	
			AdmAppln a=null;
			Object[] object = (Object[]) admIterator.next();

			if(object[0]!=null){
				a=(AdmAppln)object[0];
			}

			//check single student for testing
		//if(a.getId()==1421 || a.getId()==1422 || a.getId()==1427 || a.getId()==1206 || a.getId()==1236){


		// get student rank based in admid and preno and avoid who got already 1 pre seat
		StudentRank rank=txn.getRankOnAdmApplPreference(a.getId(), i);


		//check student have rank or not 
		if(rank!=null){


		Course course1=rank.getCourse();

		form.setCourseId(new Integer(course1.getId()).toString());

		int totseats=0;
		int scseat=0;
		int stseat=0;
		int bcseat=0;
		int museat=0;
		int obxseat=0;
		int obhseat=0;
		int lcseat=0;
		int genseat=0;
		int remainseats=0;
		int rankcutoff=0;
		//OBC		SC		ST		MU		OBX		OBH		LC

		int sccount=0;
		int stcount=0;
		int bccount=0;
		int mucount=0;
		int obxcount=0;
		int obhcount=0;
		int lccount=0;
		int gencount=0;
		int totcount=0;


		//for testing single course wise
		if(course1.getId()==12){


		//getting SeatAllocation for different addmitted through caste
		List<SeatAllocation> seatlist=txn.getSeatAllocation(form);

		//assign seats based on caste
		Iterator<SeatAllocation> it=seatlist.iterator();
		while(it.hasNext()){
			SeatAllocation st=it.next();

			if("SC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				scseat=st.getNoOfSeats();
			}else if("ST".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				stseat=st.getNoOfSeats();
			}else if("OBC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				bcseat=st.getNoOfSeats();
			}else if("General".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				genseat=st.getNoOfSeats();
			}else if("OBX".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				obxseat=st.getNoOfSeats();
			}else if("OBH".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				obhseat=st.getNoOfSeats();
			}else if("MU".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				museat=st.getNoOfSeats();
			}else if("LC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				lcseat=st.getNoOfSeats();
			}


		}// seat while



		// create new 	StudentCourseAllotment list object based on course wise
		List<StudentCourseAllotment> rankList= new ArrayList<StudentCourseAllotment>();

		//getting allotted students from StudentCourseAllotment for duplication checking
		Map<Integer,Integer> allotedmap=txn.getAllotedStudentMultipleTime(form);

		//calculate total seats allotement and rank cutoff and preference based on course
		totseats=course1.getMaxIntake();

		int cut=(totseats*20)/100;

		if(1<i){
			remainseats=totseats+(cut*i);
			rankcutoff=rankcutoff+remainseats;
		}else{
			rankcutoff=rankcutoff+totseats+remainseats;
		}



		form.setTotalSeats(totseats);
		form.setCutoffRank(rankcutoff);
		form.setPreNo(i);

		System.out.println("pre="+i+"======remining seats="+remainseats+"=====cut of="+rankcutoff+"======course="+course1.getId()+course1.getName()+"======admid="+a.getId()+"=======rank="+rank.getRank()+"========index="+rank.getIndexMark());

		//getting ranks based on above conditions
		List<StudentRank> rankDetails = txn.getStudentRankMultipleTime(form,a.getId());

		//convert properties StudentRank  to StudentCourseAllotmentTo 
		List<StudentCourseAllotmentTo> marktolist=ApplicationRankHelper.getInstance().convertStudentRankBOToTO(rankDetails);


		// check rank list size is students there are not
			if(marktolist!=null && marktolist.size()!=0){








		//old fillup seats count based on course wise multiple times
		List generalCountList=txn.getSeatCountOnCourseMultipleTime("General","", course1.getId(),form);
		if(generalCountList.size()!=0){
			gencount=generalCountList.size();
		}

		List scCountList=txn.getSeatCountOnCourseMultipleTime("","SC", course1.getId(),form);
		if(scCountList.size()!=0){
			sccount=scCountList.size();
		}


		List stCountList=txn.getSeatCountOnCourseMultipleTime("","ST", course1.getId(),form);
		if(stCountList.size()!=0){
			stcount=stCountList.size();
		}


		List obcCountList=txn.getSeatCountOnCourseMultipleTime("","OBC", course1.getId(),form);
		if(obcCountList.size()!=0){
			bccount=obcCountList.size();
		}


		List obhCountList=txn.getSeatCountOnCourseMultipleTime("","OBH", course1.getId(),form);
		if(obhCountList.size()!=0){
			obhcount=obhCountList.size();
		}


		List obxCountList=txn.getSeatCountOnCourseMultipleTime("","OBX", course1.getId(),form);
		if(obxCountList.size()!=0){
			obxcount=obxCountList.size();
		}


		List muCountList=txn.getSeatCountOnCourseMultipleTime("","MU", course1.getId(),form);
		if(muCountList.size()!=0){
			mucount=muCountList.size();
		}


		List lcCountList=txn.getSeatCountOnCourseMultipleTime("","LC", course1.getId());
		if(lcCountList.size()!=0){
			lccount=lcCountList.size();
		}


		System.out.println("pre="+i+"======remining seats="+remainseats+"=====cut of="+rankcutoff+"======course="+course1.getId()+course1.getName());
		System.out.println("1time gen="+gencount+"======sc="+sccount+"=====st="+stcount+"======obc="+bccount+"======obh="+obhcount+"=======obx="+obxcount+"========mu="+mucount);


		//old fillup seats count based on course wise alloted no
		generalCountList=txn.getSeatCountOnCourseAllotedNo("General","", course1.getId(),form);
		if(generalCountList.size()!=0){
			gencount=gencount+generalCountList.size();
		}

		scCountList=txn.getSeatCountOnCourseAllotedNo("","SC", course1.getId(),form);
		if(scCountList.size()!=0){
			sccount=sccount+scCountList.size();
		}


		stCountList=txn.getSeatCountOnCourseAllotedNo("","ST", course1.getId(),form);
		if(stCountList.size()!=0){
			stcount=stcount+stCountList.size();
		}


		obcCountList=txn.getSeatCountOnCourseAllotedNo("","OBC", course1.getId(),form);
		if(obcCountList.size()!=0){
			bccount=bccount+obcCountList.size();
		}


		obhCountList=txn.getSeatCountOnCourseAllotedNo("","OBH", course1.getId(),form);
		if(obhCountList.size()!=0){
			obhcount=obhcount+obhCountList.size();
		}


		obxCountList=txn.getSeatCountOnCourseAllotedNo("","OBX", course1.getId(),form);
		if(obxCountList.size()!=0){
			obxcount=obxcount+obxCountList.size();
		}


		muCountList=txn.getSeatCountOnCourseAllotedNo("","MU", course1.getId(),form);
		if(muCountList.size()!=0){
			mucount=mucount+muCountList.size();
		}


		lcCountList=txn.getSeatCountOnCourseAllotedNo("","LC", course1.getId());
		if(lcCountList.size()!=0){
			lccount=lccount+lcCountList.size();
		}



		System.out.println("2time gen="+gencount+"======sc="+sccount+"=====st="+stcount+"======obc="+bccount+"======obh="+obhcount+"=======obx="+obxcount+"========mu="+mucount);

		//add old count to tot count
		totcount=lccount+mucount+obxcount+obhcount+bccount+stcount+sccount+gencount;

		AdmApplnTO at=null;
		PersonalDataTO pt=null;
		CourseTO cto=null;
		String caste="";


		//check condition between total count and total seats count
		if(totcount<totseats) {


		//start general
		Iterator<StudentCourseAllotmentTo> i4=marktolist.iterator();
		while(i4.hasNext()){
			StudentCourseAllotmentTo allotmentTo=i4.next();

			at=allotmentTo.getAdmApplnTO();
			pt=at.getPersonalData();
			cto=allotmentTo.getCourseTO();

			//totseats=cto.getMaxInTake();
			caste=pt.getSubregligionName();


			//add seat to bo
			if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

			if(!allotmentTo.getIsAlloted()){
			if(gencount<genseat){
				allotmentTo.setAlloted(true);



				StudentCourseAllotment studentRank =new StudentCourseAllotment();

				   AdmAppln appln=new AdmAppln();
				   appln.setId(allotmentTo.getAdmApplnTO().getId());
				   studentRank.setAdmAppln(appln);

				   Course course=new Course();
				   course.setId(allotmentTo.getCourseTO().getId());
				   studentRank.setCourse(course);

				   studentRank.setIndexMark(allotmentTo.getIndexMark());
				   studentRank.setRank(allotmentTo.getRank());
				   studentRank.setPrefNo(allotmentTo.getPrefNo());
				   studentRank.setIsAlloted(true);
				   studentRank.setActive(true);
				   studentRank.setIsAssigned(false);
				   studentRank.setIsCaste(false);
				   studentRank.setIsGeneral(true);
				   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
				   studentRank.setAllotmentNo(form.getAllotedNo()+1);



				   gencount++;
				   totcount++;
				   rankList.add(studentRank);
				}


			}


		}// main if


		}// general close





		//sc allot
		Iterator< StudentCourseAllotmentTo> iterator=marktolist.iterator();
		while(iterator.hasNext()){
			StudentCourseAllotmentTo allotmentTo=iterator.next();

			at=allotmentTo.getAdmApplnTO();
			pt=at.getPersonalData();
			cto=allotmentTo.getCourseTO();

			//totseats=cto.getMaxInTake();
			caste=pt.getSubregligionName();


			//add seat to bo
			if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

			if(!allotmentTo.getIsAlloted()){
				if(caste.equalsIgnoreCase("SC")){
					if(sccount<scseat){
						allotmentTo.setAlloted(true);



							StudentCourseAllotment studentRank =new StudentCourseAllotment();

							   AdmAppln appln=new AdmAppln();
							   appln.setId(allotmentTo.getAdmApplnTO().getId());
							   studentRank.setAdmAppln(appln);

							   Course course=new Course();
							   course.setId(allotmentTo.getCourseTO().getId());
							   studentRank.setCourse(course);

							   studentRank.setIndexMark(allotmentTo.getIndexMark());
							   studentRank.setRank(allotmentTo.getRank());
							   studentRank.setPrefNo(allotmentTo.getPrefNo());
							   studentRank.setIsAlloted(true);
							   studentRank.setActive(true);
							   studentRank.setIsAssigned(false);
							   studentRank.setIsCaste(true);
							   studentRank.setIsGeneral(false);
							   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
							   studentRank.setAllotmentNo(form.getAllotedNo()+1);



							   sccount++;
							   totcount++;
							   rankList.add(studentRank);
						}



					}
				}




			}// main if


			}//sc close






			//st allot
				Iterator<StudentCourseAllotmentTo> i1=marktolist.iterator();
				while(i1.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i1.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();
					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("ST")){
					if(stcount<stseat){
						allotmentTo.setAlloted(true);



						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(form.getAllotedNo()+1);




							stcount++;
							totcount++;
							rankList.add(studentRank);
						}



					}
				}



					}// main if





				}//st close








				//obc allot
				Iterator<StudentCourseAllotmentTo> i2=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i2.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("OBC")){
					if(bccount<bcseat){
						allotmentTo.setAlloted(true);


						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(form.getAllotedNo()+1);



						   bccount++;
						   totcount++;
						   rankList.add(studentRank);
						}

					}

					}




				}// main if



				}	 //bc close






				//mu allot
				Iterator<StudentCourseAllotmentTo> i3=marktolist.iterator();
				while(i2.hasNext()){

					StudentCourseAllotmentTo allotmentTo=i3.next();
					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
					if(!allotmentTo.getIsAlloted()){

					if(caste.equalsIgnoreCase("MU")){
					if(mucount<museat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(form.getAllotedNo()+1);




						mucount++;
						totcount++;
						rankList.add(studentRank);
					}

					}


					}
				}// main if



				}	 //mu close





				//obx allot
				Iterator<StudentCourseAllotmentTo> i5=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i5.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("OBX")){
					if(obxcount<obxseat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(form.getAllotedNo()+1);


						obxcount++;
						totcount++;
						rankList.add(studentRank);
					}

					}

					}
				}// contain close	



				}	 //bc close







				//obh allot
				Iterator<StudentCourseAllotmentTo> i6=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i6.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();


					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("OBH")){
					if(obhcount<obhseat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();

						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						   studentRank.setAllotmentNo(form.getAllotedNo()+1);




						obhcount++;
						totcount++;
						 rankList.add(studentRank);
					}

					}

				}// main if

				}// contain close	


				}	 //bc close






				//lc allot
				Iterator<StudentCourseAllotmentTo> i7=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i7.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("LC")){
					if(lccount<lcseat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();


						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
						 	studentRank.setAllotmentNo(form.getAllotedNo()+1);

						lccount++;
						totcount++;
						rankList.add(studentRank);
					}

					}

					}// main if

					}// contain close		


				}	 //lc close


			}// totseat if close	



		// adding records course wise in database
		isAdded=txn.generateCourseAllotment(rankList);

		}// rank list size if check close
			else{ 

				System.out.println("already alloted pre="+i+"======remining seats="+remainseats+"=====cut of="+rankcutoff+"======course="+course1.getId()+course1.getName()+"======admid="+a.getId()+"=======rank="+rank.getRank()+"========index="+rank.getIndexMark());

			}

		}// course wise testing
		}// student have rank or not	

		//}// close if single student checking

		}// // loop for getting student on admappl	



		}// preference for loop close	



	}// try close
		catch(Exception e){

			isAdded=false;
			Session session = null;
			Transaction transaction = null;
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query1=session.createQuery("delete StudentCourseAllotment s where s.createdDate = current_date");
			//query.setString("current_date1",date);
			query1.executeUpdate();
			transaction.commit();
			session.close();
			System.out.println(form.getAllotedNo()+"helper multiple===============Error occured in while alloting courses to student ================="+e);
			throw e;
		}finally{

		}
		 */
		return isAdded;


	}








	//allotting courses for all people based on Rank and Preference
	@SuppressWarnings("unchecked")
	public boolean generateCourseAllotmentOnRankPreference(ApplicationEditForm form)throws Exception{




		boolean isAdded=false;

		try{


			IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
			List<Course> coursesList=CourseTransactionImpl.getInstance().getCourses(Integer.parseInt(form.getProgramTypeId()));

			int size=0;
			size=coursesList.size();


			int mrank=txn.getMaxRank(form);
			// main loop for rank loop
			for(int srank=1;srank<=mrank;srank++){

				//loop for checking preferences based on course size 

				for(int pre=1;pre<=size;pre++){



					// 	getting rank list
					List<StudentRank> rankList1=txn.getAdmApplonStudentOnRankPreference(form,pre,srank);

					// check rank list size is empty
					if(rankList1!=null && rankList1.size()!=0){

						// create new 	StudentCourseAllotment list object based on course wise
						List<StudentCourseAllotment> rankList= new ArrayList<StudentCourseAllotment>();


						Iterator<StudentRank> rankIterator=rankList1.iterator();

						// loop for getting StudentRank on rank and preference
						while(rankIterator.hasNext()){	

							//AdmAppln a=null;
							StudentRank rank = (StudentRank) rankIterator.next();




							// get student rank based in admid and preno
							//StudentRank rank=txn.getRankOnAdmApplPreference(a.getId(), pre);


							//check student have rank or not 
							//if(rank!=null){


							Course course1=rank.getCourse();

							form.setCourseId(new Integer(course1.getId()).toString());

							int totseats=0;
							int scseat=0;
							int stseat=0;
							int bcseat=0;
							int museat=0;
							int obxseat=0;
							int obhseat=0;
							//int lcseat=0;
							int genseat=0;
							int oecseat=0;
							int remainseats=0;
							int rankcutoff=0;
							//OBC		SC		ST		MU		OBX		OBH		LC

							int sccount=0;
							int stcount=0;
							int bccount=0;
							int mucount=0;
							int obxcount=0;
							int obhcount=0;
							int lccount=0;
							int oeccount=0;
							int gencount=0;
							int totcount=0;


							//for testing single course wise
							//if(course1.getId()==12){



							//getting SeatAllocation for different addmitted through caste
							List<SeatAllocation> seatlist=txn.getSeatAllocation(form);

							//assign seats based on caste
							Iterator<SeatAllocation> it=seatlist.iterator();
							while(it.hasNext()){
								SeatAllocation st=it.next();

								if("SC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									scseat=st.getNoOfSeats();
								}else if("ST".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									stseat=st.getNoOfSeats();
								}else if("OBC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									bcseat=st.getNoOfSeats();
								}else if("General".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									genseat=st.getNoOfSeats();
								}else if("OBX".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									obxseat=st.getNoOfSeats();
								}else if("OBH".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									obhseat=st.getNoOfSeats();
								}else if("MU".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									museat=st.getNoOfSeats();
								}/*else if("LC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				lcseat=st.getNoOfSeats();
			}*/
								else if("OEC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									oecseat=st.getNoOfSeats();
								}

							}// seat while



							// create new 	StudentCourseAllotment list object based on course wise
							//List<StudentCourseAllotment> rankList= new ArrayList<StudentCourseAllotment>();


							//calculate total seats allotement and rank cutoff and preference based on course
							totseats=course1.getMaxIntake();

							int cut=(totseats*20)/100;

							if(1<pre){
								remainseats=totseats+(cut*pre);
								rankcutoff=rankcutoff+remainseats;
							}else{
								rankcutoff=rankcutoff+totseats+remainseats;
							}



							form.setTotalSeats(totseats);
							form.setCutoffRank(rankcutoff);
							form.setPreNo(pre);

							System.out.println("1st time pre="+pre+"======course="+course1.getId()+course1.getName()+"======admid="+rank.getAdmAppln().getId()+"=======rank="+rank.getRank()+"========index="+rank.getIndexMark());


							//getting ranks based on above conditions
							//List<StudentRank> rankDetails = txn.getStudentRank(form,a.getId());

							//convert properties StudentRank  to StudentCourseAllotmentTo 
							//List<StudentCourseAllotmentTo> marktolist=ApplicationRankHelper.getInstance().convertStudentRankBOToTO1(rankDetails);

							//convert properties StudentRank  to StudentCourseAllotmentTo 
							StudentCourseAllotmentTo allotmentTo=ApplicationRankHelper.getInstance().convertStudentRankBOToTO1(rank);


							// check rank list size is students there are not
							//if(marktolist!=null && marktolist.size()!=0){








							//old fillup seats count based on course wise
							List generalCountList=txn.getSeatCountOnCourse("General","", course1.getId(),form);
							if(generalCountList.size()!=0){
								gencount=generalCountList.size();
							}

							List scCountList=txn.getSeatCountOnCourse("","SC", course1.getId(),form);
							if(scCountList.size()!=0){
								sccount=scCountList.size();
							}


							List stCountList=txn.getSeatCountOnCourse("","ST", course1.getId(),form);
							if(stCountList.size()!=0){
								stcount=stCountList.size();
							}


							List obcCountList=txn.getSeatCountOnCourse("","OBC", course1.getId(),form);
							if(obcCountList.size()!=0){
								bccount=obcCountList.size();
							}


							List obhCountList=txn.getSeatCountOnCourse("","OBH", course1.getId(),form);
							if(obhCountList.size()!=0){
								obhcount=obhCountList.size();
							}


							List obxCountList=txn.getSeatCountOnCourse("","OBX", course1.getId(),form);
							if(obxCountList.size()!=0){
								obxcount=obxCountList.size();
							}


							List muCountList=txn.getSeatCountOnCourse("","MU", course1.getId(),form);
							if(muCountList.size()!=0){
								mucount=muCountList.size();
							}


							/*List lcCountList=txn.getSeatCountOnCourse("","LC", course1.getId());
		if(lcCountList.size()!=0){
			lccount=lcCountList.size();
		}
							 */

							List oecCountList=txn.getSeatCountOnCourse("","OEC", course1.getId(),form);
							if(oecCountList.size()!=0){
								oeccount=oecCountList.size();
							}


							//add old count to tot count
							totcount=lccount+mucount+obxcount+obhcount+bccount+stcount+sccount+gencount+oeccount;

							System.out.println("tot seats="+totseats+"=========filled seats="+totcount+"=========main gen="+gencount+"======sc="+sccount+"=====st="+stcount+"======obc="+bccount+"======obh="+obhcount+"=======obx="+obxcount+"========mu="+mucount+"========oec="+oeccount);

							AdmApplnTO at=null;
							PersonalDataTO pt=null;
							//CourseTO cto=null;
							String caste="";


							//check condition between total count and total seats count
							if(totcount<totseats) {



								//start general
								//Iterator<StudentCourseAllotmentTo> i4=marktolist.iterator();
								//while(i4.hasNext()){
								//	StudentCourseAllotmentTo allotmentTo=i4.next();

								at=allotmentTo.getAdmApplnTO();
								pt=at.getPersonalData();
								//cto=allotmentTo.getCourseTO();

								//totseats=cto.getMaxInTake();
								caste=pt.getSubregligionName();

								// testing based on caste
								//if(caste.equalsIgnoreCase("MU")){

								//testing for single student
								//if(allotmentTo.getAdmApplnTO().getId()==1055){

								//getting allotted students from StudentCourseAllotment for duplication checking
								Map<Integer,Integer> allotedmap=txn.getAllotedStudent(form);


								//add seat to bo
								if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

									System.out.println("alloted ========"+allotedmap);
									if(!allotmentTo.getIsAlloted()){
										if(gencount<genseat){
											allotmentTo.setAlloted(true);



											StudentCourseAllotment studentRank =new StudentCourseAllotment();

											AdmAppln appln=new AdmAppln();
											appln.setId(allotmentTo.getAdmApplnTO().getId());
											studentRank.setAdmAppln(appln);

											Course course=new Course();
											course.setId(allotmentTo.getCourseTO().getId());
											studentRank.setCourse(course);

											studentRank.setIndexMark(allotmentTo.getIndexMark());
											studentRank.setRank(allotmentTo.getRank());
											studentRank.setPrefNo(allotmentTo.getPrefNo());
											studentRank.setIsAlloted(true);
											studentRank.setActive(true);
											studentRank.setIsAssigned(false);
											studentRank.setIsCaste(false);
											studentRank.setIsGeneral(true);
											studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
											studentRank.setAllotmentNo(1);



											gencount++;
											totcount++;
											rankList.add(studentRank);
										}


									}


									//}// main if


									//}// general close





									//sc allot
									//Iterator< StudentCourseAllotmentTo> iterator=marktolist.iterator();
									//while(iterator.hasNext()){
									//StudentCourseAllotmentTo allotmentTo=iterator.next();

									//at=allotmentTo.getAdmApplnTO();
									//pt=at.getPersonalData();
									//cto=allotmentTo.getCourseTO();

									//totseats=cto.getMaxInTake();
									//caste=pt.getSubregligionName();


									//add seat to bo
									//if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("SC")){
											if(sccount<scseat){
												allotmentTo.setAlloted(true);



												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(1);



												sccount++;
												totcount++;
												rankList.add(studentRank);
											}



										}
									}




									//	}// main if


									//}//sc close






									//st allot
									//Iterator<StudentCourseAllotmentTo> i1=marktolist.iterator();
									//while(i1.hasNext()){
									//StudentCourseAllotmentTo allotmentTo=i1.next();

									//	at=allotmentTo.getAdmApplnTO();
									//	pt=at.getPersonalData();
									//	cto=allotmentTo.getCourseTO();

									//	totseats=cto.getMaxInTake();
									//	caste=pt.getSubregligionName();
									//add seat to bo
									//	if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("ST")){
											if(stcount<stseat){
												allotmentTo.setAlloted(true);



												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(1);




												stcount++;
												totcount++;
												rankList.add(studentRank);
											}



										}
									}



									//	}// main if





									//}//st close








									//obc allot
									//Iterator<StudentCourseAllotmentTo> i2=marktolist.iterator();
									//while(i2.hasNext()){
									//	StudentCourseAllotmentTo allotmentTo=i2.next();

									//	at=allotmentTo.getAdmApplnTO();
									//	pt=at.getPersonalData();
									//	cto=allotmentTo.getCourseTO();

									//	totseats=cto.getMaxInTake();
									//	caste=pt.getSubregligionName();

									//add seat to bo
									//	if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("OBC")){
											if(bccount<bcseat){
												allotmentTo.setAlloted(true);


												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(1);



												bccount++;
												totcount++;
												rankList.add(studentRank);
											}

										}

									}




									//	}// main if



									//}	 //bc close






									//mu allot
									//Iterator<StudentCourseAllotmentTo> i3=marktolist.iterator();
									//while(i2.hasNext()){

									//	StudentCourseAllotmentTo allotmentTo=i3.next();
									//	at=allotmentTo.getAdmApplnTO();
									//	pt=at.getPersonalData();
									//	cto=allotmentTo.getCourseTO();

									//	totseats=cto.getMaxInTake();
									//	caste=pt.getSubregligionName();

									//add seat to bo
									//	if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
									if(!allotmentTo.getIsAlloted()){

										if(caste.equalsIgnoreCase("MU")){
											if(mucount<museat){
												allotmentTo.setAlloted(true);
												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(1);




												mucount++;
												totcount++;
												rankList.add(studentRank);
											}

										}


									}
									//	}// main if



									//}	 //mu close





									//obx allot
									//Iterator<StudentCourseAllotmentTo> i5=marktolist.iterator();
									//while(i2.hasNext()){
									//	StudentCourseAllotmentTo allotmentTo=i5.next();

									//at=allotmentTo.getAdmApplnTO();
									//pt=at.getPersonalData();
									//cto=allotmentTo.getCourseTO();

									//totseats=cto.getMaxInTake();
									//caste=pt.getSubregligionName();

									//add seat to bo
									//	if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("OBX")){
											if(obxcount<obxseat){
												allotmentTo.setAlloted(true);
												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(1);


												obxcount++;
												totcount++;
												rankList.add(studentRank);
											}

										}

									}
									//}// contain close	



									//}	 //bc close







									//obh allot
									//Iterator<StudentCourseAllotmentTo> i6=marktolist.iterator();
									//while(i2.hasNext()){
									//	StudentCourseAllotmentTo allotmentTo=i6.next();

									//at=allotmentTo.getAdmApplnTO();
									//pt=at.getPersonalData();
									//cto=allotmentTo.getCourseTO();

									//totseats=cto.getMaxInTake();
									//caste=pt.getSubregligionName();


									//add seat to bo
									//	if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("OBH")){
											if(obhcount<obhseat){
												allotmentTo.setAlloted(true);
												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(1);




												obhcount++;
												totcount++;
												rankList.add(studentRank);
											}

										}

										//}// main if

									}// contain close	


									//}	 //bc close





									//oec allot
									//Iterator<StudentCourseAllotmentTo> i6=marktolist.iterator();
									//while(i2.hasNext()){
									//	StudentCourseAllotmentTo allotmentTo=i6.next();

									//at=allotmentTo.getAdmApplnTO();
									//pt=at.getPersonalData();
									//cto=allotmentTo.getCourseTO();

									//totseats=cto.getMaxInTake();
									//caste=pt.getSubregligionName();


									//add seat to bo
									//	if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("OEC")){
											if(oeccount<oecseat){
												allotmentTo.setAlloted(true);
												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(1);




												oeccount++;
												totcount++;
												rankList.add(studentRank);
											}

										}

										//}// main if

									}// contain close	


									//}	 //bc close







									//lc allot
									/*Iterator<StudentCourseAllotmentTo> i7=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i7.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
				//	if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("LC")){
					if(lccount<lcseat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();


						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
							studentRank.setAllotmentNo(1);

						lccount++;
						totcount++;
						rankList.add(studentRank);
					}

					}

				//	}// main if

					}// contain close		


				}	 //lc close
									 */

								}//alloted map close	


								//}// testing student close

							}// totseat if close	



							// adding records course wise in database
							//isAdded=txn.generateCourseAllotment(rankList);

							//}// rank list size if check close
							//else{ 

							//	System.out.println("already alloted pre="+pre+"======remining seats="+remainseats+"=====cut of="+rankcutoff+"======course="+course1.getId()+course1.getName()+"======admid="+a.getId()+"=======rank="+rank.getRank()+"========index="+rank.getIndexMark());

							//}

							//}// close testing course wise

							//}// student have rank or not	

							//}// testing close caste

						}//  loop for getting student on admappl	


						// adding records course wise in database
						isAdded=txn.generateCourseAllotment(rankList);



					}// check rank list size is empty

				}// preference for loop close	

			}// main loop course

		}// try close
		catch(Exception e){

			isAdded=false;
			Session session = null;
			Transaction transaction = null;
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query1=session.createQuery("delete StudentCourseAllotment s where s.createdDate = current_date");
			//query.setString("current_date1",date);
			query1.executeUpdate();
			transaction.commit();
			session.close();
			System.out.println("helper===============Error occured in while alloting courses to student ================="+e);
			throw e;
		}finally{

		}

		return isAdded;


	}





	// convert bos to tos

	public StudentCourseAllotmentTo convertStudentRankBOToTO1(StudentRank studentRank1) {
		log.info("enter convertIncomeBOToTO" );
		//List<StudentCourseAllotmentTo> courseallotlist= new ArrayList<StudentCourseAllotmentTo>();
		//if(studentRank1!=null){
		StudentCourseAllotmentTo to= new StudentCourseAllotmentTo();
		to.setId(studentRank1.getId());

		AdmAppln appln=studentRank1.getAdmAppln();
		AdmApplnTO applnTO=new AdmApplnTO();
		applnTO.setId(appln.getId());
		applnTO.setApplnNo(appln.getApplnNo());
		applnTO.setAppliedYear(appln.getAppliedYear());

		PersonalData data=appln.getPersonalData();
		PersonalDataTO dataTO=new PersonalDataTO();
		dataTO.setId(data.getId());
		dataTO.setFirstName(data.getFirstName());
		dataTO.setGender(data.getGender());
		if(data.getCaste()!=null){
			dataTO.setCasteId(new Integer(data.getCaste().getId()).toString());
			dataTO.setCasteCategory(data.getCaste().getName());

		}
		if(data.getReligion()!=null){
			dataTO.setReligionId(new Integer(data.getReligion().getId()).toString());
			dataTO.setReligionName(data.getReligion().getName());
		}
		if(data.getReligionSection()!=null){
			dataTO.setSubregligionName(data.getReligionSection().getName());
			dataTO.setSubReligionId(new Integer(data.getReligionSection().getId()).toString());
		}
		dataTO.setRuralUrban(data.getRuralUrban());
		if(data.getIsSportsPerson()!=null){
			dataTO.setSportsPerson(data.getIsSportsPerson());
		}
		if(data.getIsHandicapped()!=null){
			dataTO.setHandicapped(data.getIsHandicapped());
		}
		if(data.getIsNsscertificate()!=null){
			dataTO.setNcccertificate(data.getIsNcccertificate());
		}
		if(data.getIsNsscertificate()!=null){
			dataTO.setNsscertificate(data.getIsNsscertificate());
		}
		if(data.getIsExcervice()!=null){
			dataTO.setExservice(data.getIsExcervice());
		}


		applnTO.setPersonalData(dataTO);

		Course c=studentRank1.getCourse();
		CourseTO courseTO=new CourseTO();
		courseTO.setId(c.getId());
		courseTO.setMaxInTake(c.getMaxIntake());
		List<SeatAllocationTO> seatlist=new ArrayList<SeatAllocationTO>();
		Set<SeatAllocation> set=c.getSeatAllocations();
		Iterator<SeatAllocation> i=set.iterator();

		while(i.hasNext()){
			SeatAllocation s=i.next();
			SeatAllocationTO st=new SeatAllocationTO();
			st.setId(s.getId());
			st.setNoofSeats(s.getNoOfSeats());
			st.setAdmittedThroughId(s.getAdmittedThrough().getId());
			st.setAdmittedThroughName(s.getAdmittedThrough().getName());
			seatlist.add(st);
		}

		courseTO.setSeatAllocation(seatlist);
		applnTO.setCourse(courseTO);

		to.setAdmApplnTO(applnTO);

		to.setAlloted(false);
		to.setRank(studentRank1.getRank());
		to.setPrefNo(studentRank1.getPrefNo());
		to.setIndexMark(studentRank1.getIndexMark());
		to.setCourseTO(courseTO);

		//courseallotlist.add(to);


		//}
	log.info("exit convertIncomeBOToTO" );
	return to;
	}





	//allotting courses for all people based on Rank and Preference
	@SuppressWarnings("unchecked")
	public boolean generateCourseAllotmentOnRankPreferenceMultipleTime(ApplicationEditForm form)throws Exception{

		boolean isAdded=false;

		try{


			IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
			List<Course> coursesList=CourseTransactionImpl.getInstance().getCourses(Integer.parseInt(form.getProgramTypeId()));

			int size=0;
			size=coursesList.size();

			int mrank=txn.getMaxRank(form);
			// main loop for rank loop
			for(int srank=1;srank<=mrank;srank++){

				//loop for checking preferences based on course size 

				for(int pre=1;pre<=size;pre++){



					// 	getting rank list
					List<StudentRank> rankList1=txn.getAdmApplonStudentOnRankPreferenceMultiple(form,pre,srank);

					// check rank list size is empty
					if(rankList1!=null && rankList1.size()!=0){

						// create new 	StudentCourseAllotment list object based on course wise
						List<StudentCourseAllotment> rankList= new ArrayList<StudentCourseAllotment>();


						Iterator<StudentRank> rankIterator=rankList1.iterator();

						// loop for getting StudentRank on rank and preference
						while(rankIterator.hasNext()){	

							//AdmAppln a=null;
							StudentRank rank = (StudentRank) rankIterator.next();


							// check for single student
							if(rank.getAdmAppln().getId()==1589 || rank.getAdmAppln().getId()==1591){
							}


							// get student rank based in admid and preno
							//StudentRank rank=txn.getRankOnAdmApplPreference(a.getId(), pre);


							//check student have rank or not 
							//if(rank!=null){


							Course course1=rank.getCourse();

							form.setCourseId(new Integer(course1.getId()).toString());

							int totseats=0;
							int scseat=0;
							int stseat=0;
							int bcseat=0;
							int museat=0;
							int obxseat=0;
							int obhseat=0;
							//int lcseat=0;
							int genseat=0;
							int oecseat=0;
							//int remainseats=0;
							int rankcutoff=0;
							//OBC		SC		ST		MU		OBX		OBH		LC   OEC

							int sccount=0;
							int stcount=0;
							int bccount=0;
							int mucount=0;
							int obxcount=0;
							int obhcount=0;
							int lccount=0;
							int oeccount=0;
							int gencount=0;
							int totcount=0;


							//for testing single course wise
							//if(course1.getId()==12){



							//getting SeatAllocation for different addmitted through caste
							List<SeatAllocation> seatlist=txn.getSeatAllocation(form);

							//assign seats based on caste
							Iterator<SeatAllocation> it=seatlist.iterator();
							while(it.hasNext()){
								SeatAllocation st=it.next();

								if("SC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									scseat=st.getNoOfSeats();
								}else if("ST".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									stseat=st.getNoOfSeats();
								}else if("OBC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									bcseat=st.getNoOfSeats();
								}else if("General".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									genseat=st.getNoOfSeats();
								}else if("OBX".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									obxseat=st.getNoOfSeats();
								}else if("OBH".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									obhseat=st.getNoOfSeats();
								}else if("MU".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									museat=st.getNoOfSeats();
								}/*else if("LC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
				lcseat=st.getNoOfSeats();
			}*/
								else if("OEC".equalsIgnoreCase(st.getAdmittedThrough().getName())){
									oecseat=st.getNoOfSeats();
								}

							}// seat while



							// create new 	StudentCourseAllotment list object based on course wise
							//List<StudentCourseAllotment> rankList= new ArrayList<StudentCourseAllotment>();


							//calculate total seats allotement and rank cutoff and preference based on course
							totseats=course1.getMaxIntake();

							/*int cut=(totseats*20)/100;

		if(1<pre){
			remainseats=totseats+(cut*pre);
			rankcutoff=rankcutoff+remainseats;
		}else{
			rankcutoff=rankcutoff+totseats+remainseats;
		}*/



							form.setTotalSeats(totseats);
							form.setCutoffRank(rankcutoff);
							form.setPreNo(pre);

							System.out.println("1st time pre="+pre+"=========course="+course1.getId()+course1.getName()+"======admid="+rank.getAdmAppln().getId()+"=======rank="+rank.getRank()+"========index="+rank.getIndexMark());


							//getting ranks based on above conditions
							//List<StudentRank> rankDetails = txn.getStudentRank(form,a.getId());

							//convert properties StudentRank  to StudentCourseAllotmentTo 
							//List<StudentCourseAllotmentTo> marktolist=ApplicationRankHelper.getInstance().convertStudentRankBOToTO1(rankDetails);

							//convert properties StudentRank  to StudentCourseAllotmentTo 
							StudentCourseAllotmentTo allotmentTo=ApplicationRankHelper.getInstance().convertStudentRankBOToTO1(rank);


							// check rank list size is students there are not
							//if(marktolist!=null && marktolist.size()!=0){








							//old fillup seats count based on course wise
							List generalCountList=txn.getSeatCountOnCourseMultipleTime("General","", course1.getId(),form);
							if(generalCountList.size()!=0){
								gencount=generalCountList.size();
							}

							List scCountList=txn.getSeatCountOnCourseMultipleTime("","SC", course1.getId(),form);
							if(scCountList.size()!=0){
								sccount=scCountList.size();
							}


							List stCountList=txn.getSeatCountOnCourseMultipleTime("","ST", course1.getId(),form);
							if(stCountList.size()!=0){
								stcount=stCountList.size();
							}


							List obcCountList=txn.getSeatCountOnCourseMultipleTime("","OBC", course1.getId(),form);
							if(obcCountList.size()!=0){
								bccount=obcCountList.size();
							}


							List obhCountList=txn.getSeatCountOnCourseMultipleTime("","OBH", course1.getId(),form);
							if(obhCountList.size()!=0){
								obhcount=obhCountList.size();
							}


							List obxCountList=txn.getSeatCountOnCourseMultipleTime("","OBX", course1.getId(),form);
							if(obxCountList.size()!=0){
								obxcount=obxCountList.size();
							}


							List muCountList=txn.getSeatCountOnCourseMultipleTime("","MU", course1.getId(),form);
							if(muCountList.size()!=0){
								mucount=muCountList.size();
							}


							/*List lcCountList=txn.getSeatCountOnCourseMultipleTime("","LC", course1.getId());
		if(lcCountList.size()!=0){
			lccount=lcCountList.size();
		}
							 */


							List oecCountList=txn.getSeatCountOnCourseMultipleTime("","OEC", course1.getId(),form);
							if(oecCountList.size()!=0){
								oeccount=oecCountList.size();
							}


							System.out.println("1time gen="+gencount+"======sc="+sccount+"=====st="+stcount+"======obc="+bccount+"======obh="+obhcount+"=======obx="+obxcount+"========mu="+mucount+"========oec="+oeccount);


							//old fillup seats count based on course wise alloted no
							generalCountList=txn.getSeatCountOnCourseAllotedNo("General","", course1.getId(),form);
							if(generalCountList.size()!=0){
								gencount=gencount+generalCountList.size();
							}

							scCountList=txn.getSeatCountOnCourseAllotedNo("","SC", course1.getId(),form);
							if(scCountList.size()!=0){
								sccount=sccount+scCountList.size();
							}


							stCountList=txn.getSeatCountOnCourseAllotedNo("","ST", course1.getId(),form);
							if(stCountList.size()!=0){
								stcount=stcount+stCountList.size();
							}


							obcCountList=txn.getSeatCountOnCourseAllotedNo("","OBC", course1.getId(),form);
							if(obcCountList.size()!=0){
								bccount=bccount+obcCountList.size();
							}


							obhCountList=txn.getSeatCountOnCourseAllotedNo("","OBH", course1.getId(),form);
							if(obhCountList.size()!=0){
								obhcount=obhcount+obhCountList.size();
							}


							obxCountList=txn.getSeatCountOnCourseAllotedNo("","OBX", course1.getId(),form);
							if(obxCountList.size()!=0){
								obxcount=obxcount+obxCountList.size();
							}


							muCountList=txn.getSeatCountOnCourseAllotedNo("","MU", course1.getId(),form);
							if(muCountList.size()!=0){
								mucount=mucount+muCountList.size();
							}


							/*lcCountList=txn.getSeatCountOnCourseAllotedNo("","LC", course1.getId());
		if(lcCountList.size()!=0){
			lccount=lccount+lcCountList.size();
		}
							 */

							oecCountList=txn.getSeatCountOnCourseAllotedNo("","OEC", course1.getId(),form);
							if(oecCountList.size()!=0){
								oeccount=oeccount+oecCountList.size();
							}





							//add old count to tot count
							totcount=lccount+mucount+obxcount+obhcount+bccount+stcount+sccount+gencount+oeccount;

							System.out.println("2 time tot seats="+totseats+"=========filled seats="+totcount+"=========main gen="+gencount+"======sc="+sccount+"=====st="+stcount+"======obc="+bccount+"======obh="+obhcount+"=======obx="+obxcount+"========mu="+mucount+"========oec="+oeccount);


							AdmApplnTO at=null;
							PersonalDataTO pt=null;
							//CourseTO cto=null;
							String caste="";


							//check condition between total count and total seats count
							if(totcount<totseats) {

								//getting allotted students from StudentCourseAllotment for duplication checking
								Map<Integer,Integer> allotedmap=txn.getAllotedStudentMultipleTimeOnRank(form,pre,srank);


								//start general
								//Iterator<StudentCourseAllotmentTo> i4=marktolist.iterator();
								//while(i4.hasNext()){
								//	StudentCourseAllotmentTo allotmentTo=i4.next();

								at=allotmentTo.getAdmApplnTO();
								pt=at.getPersonalData();
								//cto=allotmentTo.getCourseTO();

								//totseats=cto.getMaxInTake();
								caste=pt.getSubregligionName();

								// testing based on caste
								//if(caste.equalsIgnoreCase("MU")){


								//add seat to bo
								if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

									if(!allotmentTo.getIsAlloted()){
										if(gencount<genseat){
											allotmentTo.setAlloted(true);



											StudentCourseAllotment studentRank =new StudentCourseAllotment();

											AdmAppln appln=new AdmAppln();
											appln.setId(allotmentTo.getAdmApplnTO().getId());
											studentRank.setAdmAppln(appln);

											Course course=new Course();
											course.setId(allotmentTo.getCourseTO().getId());
											studentRank.setCourse(course);

											studentRank.setIndexMark(allotmentTo.getIndexMark());
											studentRank.setRank(allotmentTo.getRank());
											studentRank.setPrefNo(allotmentTo.getPrefNo());
											studentRank.setIsAlloted(true);
											studentRank.setActive(true);
											studentRank.setIsAssigned(false);
											studentRank.setIsCaste(false);
											studentRank.setIsGeneral(true);
											studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
											studentRank.setAllotmentNo(form.getAllotedNo()+1);




											gencount++;
											totcount++;
											rankList.add(studentRank);
										}


									}


									//}// main if


									//}// general close





									//sc allot
									//Iterator< StudentCourseAllotmentTo> iterator=marktolist.iterator();
									//while(iterator.hasNext()){
									//StudentCourseAllotmentTo allotmentTo=iterator.next();

									//at=allotmentTo.getAdmApplnTO();
									//pt=at.getPersonalData();
									//cto=allotmentTo.getCourseTO();

									//totseats=cto.getMaxInTake();
									//caste=pt.getSubregligionName();


									//add seat to bo
									//if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("SC")){
											if(sccount<scseat){
												allotmentTo.setAlloted(true);



												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(form.getAllotedNo()+1);




												sccount++;
												totcount++;
												rankList.add(studentRank);
											}



										}
									}




									//}// main if


									//}//sc close






									//st allot
									//Iterator<StudentCourseAllotmentTo> i1=marktolist.iterator();
									//while(i1.hasNext()){
									//StudentCourseAllotmentTo allotmentTo=i1.next();

									//	at=allotmentTo.getAdmApplnTO();
									//	pt=at.getPersonalData();
									//	cto=allotmentTo.getCourseTO();

									//	totseats=cto.getMaxInTake();
									//	caste=pt.getSubregligionName();
									//add seat to bo
									//if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("ST")){
											if(stcount<stseat){
												allotmentTo.setAlloted(true);



												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(form.getAllotedNo()+1);





												stcount++;
												totcount++;
												rankList.add(studentRank);
											}



										}
									}



									//}// main if





									//}//st close








									//obc allot
									//Iterator<StudentCourseAllotmentTo> i2=marktolist.iterator();
									//while(i2.hasNext()){
									//	StudentCourseAllotmentTo allotmentTo=i2.next();

									//	at=allotmentTo.getAdmApplnTO();
									//	pt=at.getPersonalData();
									//	cto=allotmentTo.getCourseTO();

									//	totseats=cto.getMaxInTake();
									//	caste=pt.getSubregligionName();

									//add seat to bo
									//if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("OBC")){
											if(bccount<bcseat){
												allotmentTo.setAlloted(true);


												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(form.getAllotedNo()+1);




												bccount++;
												totcount++;
												rankList.add(studentRank);
											}

										}

									}




									//	}// main if



									//}	 //bc close






									//mu allot
									//Iterator<StudentCourseAllotmentTo> i3=marktolist.iterator();
									//while(i2.hasNext()){

									//	StudentCourseAllotmentTo allotmentTo=i3.next();
									//	at=allotmentTo.getAdmApplnTO();
									//	pt=at.getPersonalData();
									//	cto=allotmentTo.getCourseTO();

									//	totseats=cto.getMaxInTake();
									//	caste=pt.getSubregligionName();

									//add seat to bo
									//	if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
									if(!allotmentTo.getIsAlloted()){

										if(caste.equalsIgnoreCase("MU")){
											if(mucount<museat){
												allotmentTo.setAlloted(true);
												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(form.getAllotedNo()+1);





												mucount++;
												totcount++;
												rankList.add(studentRank);
											}

										}


									}
									//}// main if



									//}	 //mu close





									//obx allot
									//Iterator<StudentCourseAllotmentTo> i5=marktolist.iterator();
									//while(i2.hasNext()){
									//	StudentCourseAllotmentTo allotmentTo=i5.next();

									//at=allotmentTo.getAdmApplnTO();
									//pt=at.getPersonalData();
									//cto=allotmentTo.getCourseTO();

									//totseats=cto.getMaxInTake();
									//caste=pt.getSubregligionName();

									//add seat to bo
									//if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("OBX")){
											if(obxcount<obxseat){
												allotmentTo.setAlloted(true);
												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(form.getAllotedNo()+1);



												obxcount++;
												totcount++;
												rankList.add(studentRank);
											}

										}

									}
									//	}// contain close	



									//}	 //bc close







									//obh allot
									//Iterator<StudentCourseAllotmentTo> i6=marktolist.iterator();
									//while(i2.hasNext()){
									//	StudentCourseAllotmentTo allotmentTo=i6.next();

									//at=allotmentTo.getAdmApplnTO();
									//pt=at.getPersonalData();
									//cto=allotmentTo.getCourseTO();

									//totseats=cto.getMaxInTake();
									//caste=pt.getSubregligionName();


									//add seat to bo
									//if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("OBH")){
											if(obhcount<obhseat){
												allotmentTo.setAlloted(true);
												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(form.getAllotedNo()+1);





												obhcount++;
												totcount++;
												rankList.add(studentRank);
											}

										}

										//}// main if

									}// contain close	


									//}	 //bc close



									//oec allot
									//Iterator<StudentCourseAllotmentTo> i6=marktolist.iterator();
									//while(i2.hasNext()){
									//	StudentCourseAllotmentTo allotmentTo=i6.next();

									//at=allotmentTo.getAdmApplnTO();
									//pt=at.getPersonalData();
									//cto=allotmentTo.getCourseTO();

									//totseats=cto.getMaxInTake();
									//caste=pt.getSubregligionName();


									//add seat to bo
									//	if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){
									if(!allotmentTo.getIsAlloted()){
										if(caste.equalsIgnoreCase("OEC")){
											if(oeccount<oecseat){
												allotmentTo.setAlloted(true);
												StudentCourseAllotment studentRank =new StudentCourseAllotment();

												AdmAppln appln=new AdmAppln();
												appln.setId(allotmentTo.getAdmApplnTO().getId());
												studentRank.setAdmAppln(appln);

												Course course=new Course();
												course.setId(allotmentTo.getCourseTO().getId());
												studentRank.setCourse(course);

												studentRank.setIndexMark(allotmentTo.getIndexMark());
												studentRank.setRank(allotmentTo.getRank());
												studentRank.setPrefNo(allotmentTo.getPrefNo());
												studentRank.setIsAlloted(true);
												studentRank.setActive(true);
												studentRank.setIsAssigned(false);
												studentRank.setIsCaste(true);
												studentRank.setIsGeneral(false);
												studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
												studentRank.setAllotmentNo(1);




												oeccount++;
												totcount++;
												rankList.add(studentRank);
											}

										}

										//}// main if

									}// contain close	


									//}	 //bc close








									//lc allot
									/*Iterator<StudentCourseAllotmentTo> i7=marktolist.iterator();
				while(i2.hasNext()){
					StudentCourseAllotmentTo allotmentTo=i7.next();

					at=allotmentTo.getAdmApplnTO();
					pt=at.getPersonalData();
					cto=allotmentTo.getCourseTO();

					totseats=cto.getMaxInTake();
					caste=pt.getSubregligionName();

					//add seat to bo
					if(!allotedmap.containsKey(allotmentTo.getAdmApplnTO().getId())){

					if(!allotmentTo.getIsAlloted()){
					if(caste.equalsIgnoreCase("LC")){
					if(lccount<lcseat){
						allotmentTo.setAlloted(true);
						StudentCourseAllotment studentRank =new StudentCourseAllotment();


						   AdmAppln appln=new AdmAppln();
						   appln.setId(allotmentTo.getAdmApplnTO().getId());
						   studentRank.setAdmAppln(appln);

						   Course course=new Course();
						   course.setId(allotmentTo.getCourseTO().getId());
						   studentRank.setCourse(course);

						   studentRank.setIndexMark(allotmentTo.getIndexMark());
						   studentRank.setRank(allotmentTo.getRank());
						   studentRank.setPrefNo(allotmentTo.getPrefNo());
						   studentRank.setIsAlloted(true);
						   studentRank.setActive(true);
						   studentRank.setIsAssigned(false);
						   studentRank.setIsCaste(true);
						   studentRank.setIsGeneral(false);
						   studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
							studentRank.setAllotmentNo(form.getAllotedNo()+1);


						lccount++;
						totcount++;
						rankList.add(studentRank);
					}

					}

					}// main if

					}// contain close		


				}	 //lc close
									 */

								}	// allotmap teseting close

							} 	// totseat if close

							// adding records course wise in database
							//isAdded=txn.generateCourseAllotment(rankList);

							//}// rank list size if check close
							//else{ 

							//	System.out.println("already alloted pre="+pre+"======remining seats="+remainseats+"=====cut of="+rankcutoff+"======course="+course1.getId()+course1.getName()+"======admid="+a.getId()+"=======rank="+rank.getRank()+"========index="+rank.getIndexMark());

							//}

							//}// close testing course wise

							//}// student have rank or not	

							//}// testing based on caste

						}//  loop for getting student on admappl	


						// adding records course wise in database
						isAdded=txn.generateCourseAllotment(rankList);



					}// check rank list size is empty

				}// preference for loop close	

			}// main loop course

		}// try close
		catch(Exception e){

			isAdded=false;
			Session session = null;
			Transaction transaction = null;
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query1=session.createQuery("delete StudentCourseAllotment s where s.createdDate = current_date");
			//query.setString("current_date1",date);
			query1.executeUpdate();
			transaction.commit();
			session.close();
			System.out.println("helper===============Error occured in while alloting courses to student ================="+e);
			throw e;
		}finally{

		}

		return isAdded;



	}



	// get student list for course assignment
	public List<StudentCourseAllotmentTo> copyBoToTO1(List<CandidatePreference> studentList, HashMap<Integer, Integer> existMap) {

		List<StudentCourseAllotmentTo> studentDetailsToList=new LinkedList<StudentCourseAllotmentTo>();

		Iterator<CandidatePreference> it= studentList.iterator();
		while(it.hasNext()){

			StudentCourseAllotmentTo studentDetailsTo=new StudentCourseAllotmentTo();
			CandidatePreference rank=it.next();

			if(existMap.containsKey(rank.getAdmAppln().getId())){
				studentDetailsTo.setTempChecked("on");
				studentDetailsTo.setChecked("on");
			}

			studentDetailsTo.setStudentId(String.valueOf(rank.getAdmAppln().getId()));
			studentDetailsTo.setStudentName(rank.getAdmAppln().getPersonalData().getFirstName());
			studentDetailsTo.setId(rank.getId());
			studentDetailsTo.setPrefNo(rank.getPrefNo());

			CourseTO courseTO=new CourseTO();
			courseTO.setId(rank.getCourse().getId());
			courseTO.setName(rank.getCourse().getName());
			studentDetailsTo.setCourseTO(courseTO);

			AdmApplnTO applnTO=new AdmApplnTO();
			applnTO.setId(rank.getAdmAppln().getId());
			studentDetailsTo.setAdmApplnTO(applnTO);


			studentDetailsToList.add(studentDetailsTo);

		}


		return studentDetailsToList;

	}



	// get student list for course assignment
	public List<CandidatePreferenceEntranceDetailsTO> copyBoToTO2(List<CandidatePreferenceEntranceDetails> studentList) {

		List<CandidatePreferenceEntranceDetailsTO> studentDetailsToList=new LinkedList<CandidatePreferenceEntranceDetailsTO>();

		Iterator<CandidatePreferenceEntranceDetails> it= studentList.iterator();
		while(it.hasNext()){

			CandidatePreferenceEntranceDetailsTO studentDetailsTo=new CandidatePreferenceEntranceDetailsTO();
			CandidatePreferenceEntranceDetails rank=it.next();

			studentDetailsTo.setStudentName(rank.getAdmAppln().getPersonalData().getFirstName());
			studentDetailsTo.setId(rank.getId());
			studentDetailsTo.setPrefNo(rank.getPrefNo());
			if(rank.getMarksObtained()!=null)
				studentDetailsTo.setMarksObtained(rank.getMarksObtained().toString());
			if(rank.getTotalMarks()!=null)
				studentDetailsTo.setTotalMarks(rank.getTotalMarks().toString());
			CourseTO courseTO=new CourseTO();
			courseTO.setId(rank.getCourse().getId());
			courseTO.setName(rank.getCourse().getName());
			studentDetailsTo.setCourseTO(courseTO);

			AdmApplnTO applnTO=new AdmApplnTO();
			applnTO.setId(rank.getAdmAppln().getId());
			studentDetailsTo.setAdmApplnTO(applnTO);
			
			studentDetailsToList.add(studentDetailsTo);
		}
		return studentDetailsToList;

	}




	//For mark
	public List<StudentCommonRank> calculateGroupMarks(List<EdnQualification> ednList,ApplicationEditForm form)throws Exception{


		Set<AdmSubjectMarkForRank> SubmarkList=new HashSet<AdmSubjectMarkForRank>();
		Iterator<EdnQualification> iterator = ednList.iterator();
		List<StudentCommonRank> admList=new ArrayList<StudentCommonRank>();
		while (iterator.hasNext()) {

			double totalmarks=0;
			double groupmarks=0;
			double totalmaxmarks=200;
			double groupmaxmarks=200;
			double percentage=0;
			double grouppercentage=0;
			int admid=0;
			//int courseid=0;

			EdnQualification ednQualificationBO = iterator.next();

			SubmarkList=ednQualificationBO.getAdmSubjectMarkForRank();
			Set<AdmAppln> aset=ednQualificationBO.getPersonalData().getAdmApplns();
			Iterator<AdmSubjectMarkForRank> itr=SubmarkList.iterator();
			Iterator<AdmAppln> itr1=aset.iterator();

			while(itr1.hasNext()){
				AdmAppln a=itr1.next();
				admid=a.getId();
				// courseid=a.getCourse().getId();
			}

			while(itr.hasNext()){
				AdmSubjectMarkForRank admSubjectMarkForRank=(AdmSubjectMarkForRank) itr.next();

				if(admSubjectMarkForRank.getConversionmark()!=null && !admSubjectMarkForRank.getConversionmark().equalsIgnoreCase("")){
					totalmarks=totalmarks+Double.parseDouble(admSubjectMarkForRank.getConversionmark());
					totalmaxmarks=totalmaxmarks+200;
				}
				if(!admSubjectMarkForRank.getAdmSubjectForRank().getGroupName().equalsIgnoreCase("Language")){
					groupmarks=groupmarks+Double.parseDouble(admSubjectMarkForRank.getConversionmark());
					groupmaxmarks=groupmaxmarks+200;
				}

			}

			percentage=(totalmarks/totalmaxmarks)*100;
			grouppercentage=(groupmarks/groupmaxmarks)*100;
			StudentCommonRank studentCommonRank=new StudentCommonRank();
			studentCommonRank.setGroupMarks(groupmarks);
			studentCommonRank.setTotalMark(totalmarks);
			studentCommonRank.setGroupPercentage(grouppercentage);
			studentCommonRank.setCreatedBy(form.getUserId());
			studentCommonRank.setCreatedDate(new Date());
			studentCommonRank.setPercentage(percentage);

			AdmAppln admAppln=new AdmAppln();
			admAppln.setId(admid);
			studentCommonRank.setAdmAppln(admAppln);

			Course c=new Course();
			c.setId(Integer.parseInt(form.getCourseId()));
			studentCommonRank.setCourse(c);

			admList.add(studentCommonRank);


		}
		return admList;

	}







	//allotting courses for all people based on Rank and Course
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public boolean generateCourseAllotmentOnCourseRank(ApplicationEditForm form)throws Exception{

		boolean isAdded=false;
		boolean isStop=true;

		try
		{	

			// create new 	StudentCourseAllotment list object based on course wise and caste

			/*

 	List<StudentCourseAllotment> genrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> scrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> strankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> obcrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> obxrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> obhrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> murankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> lcrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> lctarankList= new LinkedList<StudentCourseAllotment>();

			 */


			//set allotment no	
			form.setAllotedNo(1);	

			// create new 	allotmentMap with student id and StudentCourseAllotment object for storing db
			Map<Integer,StudentCourseAllotment> allotmentMap=new HashMap<Integer,StudentCourseAllotment>();
			Map<Integer,CourseTO> courseMap=new HashMap<Integer,CourseTO>();
			Map<Integer,List<StudentRank>> genRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteSCRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteSTRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteBCRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteOBXRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteOBHRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteMURankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteLCRankMap=new HashMap<Integer,List<StudentRank>>();

			IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
			//get all courses
			List<Course> courseList=CourseTransactionImpl.getInstance().getCourses(Integer.parseInt(form.getProgramTypeId()));

			int courseSize=0;
			courseSize=courseList.size();

			//convert course bos to tos list
			List<CourseTO> courseToList=ApplicationRankHelper.getInstance().copyCourseBoTO(courseList);
			//put general details of course into map
			Iterator<CourseTO> courseIterator1=courseToList.iterator();
			while(courseIterator1.hasNext())
			{
				CourseTO courseTO=courseIterator1.next();


				//getting rank list on Course and General rank
				List<StudentRank> rankListOnCourseGeneral=txn.getAdmApplonStudentOnCourseCategory(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"GENERAL");
				if(rankListOnCourseGeneral.size()!=0)
				{
					//setting genmap as key course id , value gen rank list
					genRankMap.put(courseTO.getId(), rankListOnCourseGeneral);
					int genSize=rankListOnCourseGeneral.size();
					StudentRank sr=rankListOnCourseGeneral.get(genSize-1);
					courseTO.setHighgenrank(sr.getRank());	
				}
				else
				{
					courseTO.setHighgenrank(0);
				}

				//getting rank list on Course and SC caste rank
				List<StudentRank> rankListOnCourseCasteSC=txn.getAdmApplonStudentOnCourseCategory(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"SC");

				if(rankListOnCourseCasteSC.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteSCRankMap.put(courseTO.getId(), rankListOnCourseCasteSC);
					int casteSize=rankListOnCourseCasteSC.size();
					StudentRank sr=rankListOnCourseCasteSC.get(casteSize-1);
					courseTO.setHighscrank(sr.getRank());

				}
				else
				{
					courseTO.setHighscrank(0);
				}


				//getting rank list on Course and ST caste rank
				List<StudentRank> rankListOnCourseCasteST=txn.getAdmApplonStudentOnCourseCategory(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"ST");

				if(rankListOnCourseCasteST.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteSTRankMap.put(courseTO.getId(), rankListOnCourseCasteST);
					int casteSize=rankListOnCourseCasteST.size();
					StudentRank sr=rankListOnCourseCasteST.get(casteSize-1);
					courseTO.setHighstrank(sr.getRank());

				}
				else
				{
					courseTO.setHighstrank(0);
				}


				//getting rank list on Course and LC  and LCTA caste rank
				List<StudentRank> rankListOnCourseCasteLC=txn.getAdmApplonStudentOnCourseCategory(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"LC");

				if(rankListOnCourseCasteLC.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteLCRankMap.put(courseTO.getId(), rankListOnCourseCasteLC);
					int casteSize=rankListOnCourseCasteLC.size();
					StudentRank sr=rankListOnCourseCasteLC.get(casteSize-1);
					courseTO.setHighlcrank(sr.getRank());

				}
				else
				{
					courseTO.setHighlcrank(0);
				}

				//getting rank list on Course and BC caste rank BC mens EZHAVA,THIYYA,BILLAVA
				List<StudentRank> rankListOnCourseCasteBC=txn.getAdmApplonStudentOnCourseCategory(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"BC");

				if(rankListOnCourseCasteBC.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteBCRankMap.put(courseTO.getId(), rankListOnCourseCasteBC);
					int casteSize=rankListOnCourseCasteBC.size();
					StudentRank sr=rankListOnCourseCasteBC.get(casteSize-1);
					courseTO.setHighbcrank(sr.getRank());

				}
				else
				{
					courseTO.setHighbcrank(0);
				}

				//getting rank list on Course and MU caste rank
				List<StudentRank> rankListOnCourseCasteMU=txn.getAdmApplonStudentOnCourseCategory(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"MU");

				if(rankListOnCourseCasteMU.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteMURankMap.put(courseTO.getId(), rankListOnCourseCasteMU);
					int casteSize=rankListOnCourseCasteMU.size();
					StudentRank sr=rankListOnCourseCasteMU.get(casteSize-1);
					courseTO.setHighmurank(sr.getRank());

				}
				else
				{
					courseTO.setHighmurank(0);
				}

				//getting rank list on Course and LCTA caste rank
				/*List<StudentRank> rankListOnCourseCasteLCTA=txn.getAdmApplonStudentOnCourseCategory(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"LCTA");

	if(rankListOnCourseCasteLCTA.size()!=0)
	{
		//setting castemap as key course id , value caste rank list
		 casteRankMap.put(courseTO.getId(), rankListOnCourseCasteLCTA);
		 int casteSize=rankListOnCourseCasteLCTA.size();
		 StudentRank sr=rankListOnCourseCasteLCTA.get(casteSize-1);
		 courseTO.setHighlctarank(sr.getRank());

	}
	else
	{
		courseTO.setHighlctarank(0);
	}
				 */

				//getting rank list on Course and OBH caste rank
				List<StudentRank> rankListOnCourseCasteOBH=txn.getAdmApplonStudentOnCourseCategory(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"OBH");

				if(rankListOnCourseCasteOBH.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteOBHRankMap.put(courseTO.getId(), rankListOnCourseCasteOBH);
					int casteSize=rankListOnCourseCasteOBH.size();
					StudentRank sr=rankListOnCourseCasteOBH.get(casteSize-1);
					courseTO.setHighobhrank(sr.getRank());

				}
				else
				{
					courseTO.setHighobhrank(0);
				}

				//getting rank list on Course and OBX caste rank
				List<StudentRank> rankListOnCourseCasteOBX=txn.getAdmApplonStudentOnCourseCategory(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"OBX");

				if(rankListOnCourseCasteOBX.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteOBXRankMap.put(courseTO.getId(), rankListOnCourseCasteOBX);
					int casteSize=rankListOnCourseCasteOBX.size();
					StudentRank sr=rankListOnCourseCasteOBX.get(casteSize-1);
					courseTO.setHighobxrank(sr.getRank());

				}
				else
				{
					courseTO.setHighobxrank(0);
				}


				courseTO.setGencurrank(0);
				courseTO.setSccurrank(0);
				courseTO.setStcurrank(0);
				courseTO.setBccurrank(0);
				courseTO.setObhcurrank(0);
				courseTO.setObxcurrank(0);
				courseTO.setLccurrank(0);
				courseTO.setMucurrank(0);

				courseMap.put(courseTO.getId(), courseTO);
			}
			//close course iterator map


			//start with infinite loop upto fill all seats 
			while(isStop)
			{

				Iterator<CourseTO> courseIterator=courseToList.iterator();	
				// start first course loop for fill caste seats
				while(courseIterator.hasNext())
				{

					CourseTO courseTO=courseIterator.next();

					// check Course and caste SC rank list size is empty
					List<StudentRank> rankListOnCourseCasteSC=casteSCRankMap.get(courseTO.getId());

					// check sc setas zero or not, if zero stop course
					if(courseTO.getScremain()==null || courseTO.getScremain()==0){
						//course over
						courseTO.setScover(true);
						courseTO.setScseat(0);
						courseTO.setScremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}

					else if(rankListOnCourseCasteSC!=null && rankListOnCourseCasteSC.size()!=0)
					{	 
						// add sc allotment list and map
						addAllotmentlist(rankListOnCourseCasteSC, "SC",allotmentMap,courseMap,courseTO,form.getAllotedNo());
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setScover(true);
						courseTO.setScseat(0);
						courseTO.setScremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// SC caste allot over


					// check Course and caste ST rank list size is empty
					List<StudentRank> rankListOnCourseCasteST=casteSTRankMap.get(courseTO.getId());

					// check st setas zero or not, if zero stop course
					if(courseTO.getStremain()==null || courseTO.getStremain()==0){
						//course over
						courseTO.setStover(true);
						courseTO.setStseat(0);
						courseTO.setStremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}

					else if(rankListOnCourseCasteST!=null && rankListOnCourseCasteST.size()!=0)
					{	 
						// add st allotment list and map
						addAllotmentlist(rankListOnCourseCasteST, "ST",allotmentMap,courseMap,courseTO,form.getAllotedNo());
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setStover(true);
						courseTO.setStseat(0);
						courseTO.setStremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// ST caste allot over



					// check Course and caste LC rank list size is empty
					List<StudentRank> rankListOnCourseCasteLC=casteLCRankMap.get(courseTO.getId());

					// check lc setas zero or not, if zero stop course
					if(courseTO.getLcremain()==null || courseTO.getLcremain()==0){
						//course over
						courseTO.setLcover(true);
						courseTO.setLcseat(0);
						courseTO.setLcremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					else if(rankListOnCourseCasteLC!=null && rankListOnCourseCasteLC.size()!=0)
					{	 
						// add lc allotment list and map
						addAllotmentlist(rankListOnCourseCasteLC, "LC",allotmentMap,courseMap,courseTO,form.getAllotedNo());
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setLcover(true);
						courseTO.setLcseat(0);
						courseTO.setLcremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// LC caste allot over



					// check Course and caste BC rank list size is empty
					List<StudentRank> rankListOnCourseCasteBC=casteBCRankMap.get(courseTO.getId());


					// check bc setas zero or not, if zero stop course
					if(courseTO.getBcremain()==null || courseTO.getBcremain()==0){
						//course over
						courseTO.setBcover(true);
						courseTO.setBcseat(0);
						courseTO.setBcremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);

					}
					else if(rankListOnCourseCasteBC!=null && rankListOnCourseCasteBC.size()!=0)
					{	 
						// add bc allotment list and map
						addAllotmentlist(rankListOnCourseCasteBC, "BC",allotmentMap,courseMap,courseTO,form.getAllotedNo());
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setBcover(true);
						courseTO.setBcseat(0);
						courseTO.setBcremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// BC caste allot over




					// check Course and caste MU rank list size is empty
					List<StudentRank> rankListOnCourseCasteMU=casteMURankMap.get(courseTO.getId());

					// check mu setas zero or not, if zero stop course
					if(courseTO.getMuremain()==null || courseTO.getMuremain()==0){
						//course over
						courseTO.setMuover(true);
						courseTO.setMuseat(0);
						courseTO.setMuremain(0);
						//update course map
						courseMap.put(courseTO.getId(), courseTO);

					}
					else if(rankListOnCourseCasteMU!=null && rankListOnCourseCasteMU.size()!=0)
					{	 
						// add mu allotment list and map
						addAllotmentlist(rankListOnCourseCasteMU, "MU",allotmentMap,courseMap,courseTO,form.getAllotedNo());
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setMuover(true);
						courseTO.setMuseat(0);
						courseTO.setMuremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// MU caste allot over


					// check Course and caste OBH rank list size is empty
					List<StudentRank> rankListOnCourseCasteOBH=casteOBHRankMap.get(courseTO.getId());

					// check obh setas zero or not, if zero stop course
					if(courseTO.getObhremain()==null || courseTO.getObhremain()==0){
						//course over
						courseTO.setObhover(true);
						courseTO.setObhseat(0);
						courseTO.setObhremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);

					}
					else if(rankListOnCourseCasteOBH!=null && rankListOnCourseCasteOBH.size()!=0)
					{	 
						// add obh allotment list and map
						addAllotmentlist(rankListOnCourseCasteOBH, "OBH",allotmentMap,courseMap,courseTO,form.getAllotedNo());
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setObhover(true);
						courseTO.setObhseat(0);
						courseTO.setObhremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// OBH caste allot over


					// check Course and caste OBX rank list size is empty
					List<StudentRank> rankListOnCourseCasteOBX=casteOBXRankMap.get(courseTO.getId());
					// check obx setas zero or not, if zero stop course
					if(courseTO.getObxremain()==null || courseTO.getObxremain()==0){
						//course over
						courseTO.setObxover(true);
						courseTO.setObxseat(0);
						courseTO.setObxremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);

					}
					else if(rankListOnCourseCasteOBX!=null && rankListOnCourseCasteOBX.size()!=0)
					{	 
						// add obx allotment list and map
						addAllotmentlist(rankListOnCourseCasteOBX, "OBX",allotmentMap,courseMap,courseTO,form.getAllotedNo());
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setObxover(true);
						courseTO.setObxseat(0);
						courseTO.setObxremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// OBX caste allot over



				}// first caste course loop close



				Iterator<CourseTO> courseIterator2=courseToList.iterator();
				// start second course loop for fill general seats
				while(courseIterator2.hasNext())
				{
					CourseTO courseTO=courseIterator2.next();

					//getting rank list on Course and General rank
					List<StudentRank> rankListOnCourseGeneral=genRankMap.get(courseTO.getId());// check Course and caste rank list size is empty
					// check st setas zero or not, if zero stop course
					if(courseTO.getGenremain()==null || courseTO.getGenremain()==0){
						//course over
						courseTO.setGenover(true);
						courseTO.setGenseat(0);
						courseTO.setGenremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);

					}
					else if(rankListOnCourseGeneral!=null && rankListOnCourseGeneral.size()!=0)
					{
						// add general allotment list and map
						addAllotmentlist(rankListOnCourseGeneral, "General",allotmentMap,courseMap,courseTO,form.getAllotedNo());			
					}// general ranklist size close
					else
					{
						//course over
						courseTO.setGenover(true);
						courseTO.setGenseat(0);
						courseTO.setGenremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}


				}
				//second general course loop close

				// condition to check all seats fill are not
				int i=0;
				Set<Entry<Integer, CourseTO>> courseSet= courseMap.entrySet();
				Iterator<Entry<Integer, CourseTO>> icourse=courseSet.iterator();
				while(icourse.hasNext())
				{
					Entry<Integer, CourseTO> e=icourse.next();
					CourseTO courseTO=e.getValue();
					if(courseTO.isGenover() && courseTO.isScover() && courseTO.isStover() && courseTO.isBcover() && courseTO.isObhover() && courseTO.isObxover() && courseTO.isMuover() && courseTO.isLcover() )
					{
						i++;
						System.out.println(courseTO.getName()+"=======  over   ========="+i);
					}
				}


				// course size course over equals stop infinte loop
				if(courseSize==i)
				{
					isStop=false;
				}

			}// infinite loop close




			//getting values from allotmap and add values to list   
			List<StudentCourseAllotment> allotmentList=new ArrayList<StudentCourseAllotment>();
			Set<Entry<Integer, StudentCourseAllotment>> studentCourseAllotmentSet= allotmentMap.entrySet();

			Iterator<Entry<Integer, StudentCourseAllotment>> i=studentCourseAllotmentSet.iterator();
			while(i.hasNext())
			{
				Entry<Integer, StudentCourseAllotment> e=i.next();
				StudentCourseAllotment allotment=e.getValue();
				allotmentList.add(allotment);
			}

			// adding records course wise in database
			isAdded=txn.generateCourseAllotment(allotmentList);

		}// try close
		catch(Exception e)
		{
			isAdded=false;
			Session session = null;
			Transaction transaction = null;
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query1=session.createQuery("delete StudentCourseAllotment s where s.createdDate = current_date");
			//query.setString("current_date1",date);
			query1.executeUpdate();
			transaction.commit();
			session.close();
			System.out.println("helper===============Error occured in while alloting courses to student ================="+e);
			throw e;
		}
		finally
		{

		}

		return isAdded;


	}







	// add students category wise to allotment list and map
	/**
	 * @param rankList
	 * @param category
	 * @param genrankList
	 * @param allotmentMap
	 */
	public void addAllotmentlist(List<StudentRank> rankList,String category,Map<Integer,StudentCourseAllotment> allotmentMap,Map<Integer,CourseTO> courseMap,CourseTO itrcourseTO,Integer allotmentNo ) 
	{



		CourseTO courseTO=courseMap.get(itrcourseTO.getId());
		System.out.println();
		System.out.println("==========================  "+ courseTO.getName() + " ============================="+category);
		System.out.println("before gencurrent=="+courseTO.getGencurrank());
		System.out.println("before sccurrent=="+courseTO.getSccurrank());
		System.out.println("before stcurrent=="+courseTO.getStcurrank());
		System.out.println("before bccurrent=="+courseTO.getBccurrank());
		System.out.println("before lccurrent=="+courseTO.getLccurrank());
		System.out.println("before mucurrent=="+courseTO.getMucurrank());
		System.out.println("before obhcurrent=="+courseTO.getObhcurrank());
		System.out.println("before obxcurrent=="+courseTO.getObxcurrank());

		System.out.println("before genremain=="+courseTO.getGenremain());
		System.out.println("before scremain=="+courseTO.getScremain());
		System.out.println("before stremain=="+courseTO.getStremain());
		System.out.println("before bcremain=="+courseTO.getBcremain());
		System.out.println("before muremain=="+courseTO.getMuremain());
		System.out.println("before lcremain=="+courseTO.getLcremain());
		System.out.println("before obhremain=="+courseTO.getObhremain());
		System.out.println("before obxremain=="+courseTO.getObxremain());

		System.out.println("before higen=="+courseTO.getHighgenrank());
		System.out.println("before higsc=="+courseTO.getHighscrank());
		System.out.println("before hist=="+courseTO.getHighstrank());
		System.out.println("before higbc=="+courseTO.getHighbcrank());
		System.out.println("before himu=="+courseTO.getHighmurank());
		System.out.println("before higlc=="+courseTO.getHighlcrank());
		System.out.println("before higobh=="+courseTO.getHighobhrank());
		System.out.println("before higobx=="+courseTO.getHighobxrank());

		System.out.println("==========================  "+ category+ " =============================");



		Properties prop = new Properties();
		try 
		{
			InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream("resources/application.properties");
			prop.load(in);
		} 
		catch (FileNotFoundException e)
		{
			log.error("Unable to read properties file...", e);

		} 
		catch (IOException e)
		{
			log.error("Unable to read properties file...", e);

		}

		//sc
		Integer scId=0;	
		String sc = prop.getProperty ("knowledgepro.admission.religionsection.sc");
		if(sc !=null && ! StringUtils.isEmpty(sc)){ 
			scId=Integer.parseInt(sc);
		}

		//ezhava_thiyya_billava
		Integer ezhava_thiyya_billava_Id=0;	
		String ezhava_thiyya_billava = prop.getProperty ("knowledgepro.admission.religionsection.ezhava_thiyya_billava");
		if(ezhava_thiyya_billava !=null && ! StringUtils.isEmpty(ezhava_thiyya_billava)){ 
			ezhava_thiyya_billava_Id=Integer.parseInt(ezhava_thiyya_billava);
		}

		//mu
		Integer muId=0;	
		String mu = prop.getProperty ("knowledgepro.admission.religionsection.mu");
		if(mu !=null && ! StringUtils.isEmpty(mu)){ 
			muId=Integer.parseInt(mu);
		}

		//st
		Integer stId=0;	
		String st = prop.getProperty ("knowledgepro.admission.religionsection.st");
		if(st !=null && ! StringUtils.isEmpty(st)){ 
			stId=Integer.parseInt(st);
		}

		//lc
		Integer lcId=0;	
		String lc = prop.getProperty ("knowledgepro.admission.religionsection.lc");
		if(lc !=null && ! StringUtils.isEmpty(lc)){ 
			lcId=Integer.parseInt(lc);
		}

		//lc
		Integer lctaId=0;	
		String lcta = prop.getProperty ("knowledgepro.admission.religionsection.lcta");
		if(lcta !=null && ! StringUtils.isEmpty(lcta)){ 
			lctaId=Integer.parseInt(lcta);
		}

		//obh
		Integer obhId=0;	
		String obh = prop.getProperty ("knowledgepro.admission.religionsection.obh");
		if(obh !=null && ! StringUtils.isEmpty(obh)){ 
			obhId=Integer.parseInt(obh);
		}

		//obx
		Integer obxId=0;	
		String obx = prop.getProperty ("knowledgepro.admission.religionsection.obx");
		if(obx !=null && ! StringUtils.isEmpty(obx)){ 
			obxId=Integer.parseInt(obx);
		}

		//ezhava
		//Integer ezhavaId=0;	
		String ezhava = prop.getProperty ("knowledgepro.admission.religionsection.ezhava");
		if(ezhava !=null && ! StringUtils.isEmpty(ezhava)){ 
			//ezhavaId=Integer.parseInt(ezhava);
		}


		//billava
		//Integer billavaId=0;	
		String billava = prop.getProperty ("knowledgepro.admission.religionsection.billava");
		if(billava !=null && ! StringUtils.isEmpty(billava)){ 
			//billavaId=Integer.parseInt(billava);
		}

		//thiyya
		//Integer thiyyaId=0;	
		String thiyya = prop.getProperty ("knowledgepro.admission.religionsection.thiyya");
		if(thiyya !=null && ! StringUtils.isEmpty(thiyya)){ 
			//thiyyaId=Integer.parseInt(thiyya);
		}







		Iterator<StudentRank> rankIterator=rankList.iterator();
		// loop for getting StudentRank on rank and preference
		while(rankIterator.hasNext()){	

			StudentRank currank = (StudentRank) rankIterator.next();




			// general check start
			if(category.equalsIgnoreCase("General"))
			{
				// check student rank is more than gencurcommon rank
				if(currank.getRank() > courseTO.getGencurrank())
				{
					// check gencurcommon rank less than highest gen rank and gen seats not equal zero
					if (courseTO.getGencurrank() < courseTO.getHighgenrank() && courseTO.getGenremain() != 0)
					{
						// check map contains student or not
						if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
						{
							StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());
							// check map contain student pre less than with student rank pre
							if(allotment.getPrefNo() < currank.getPrefNo() )
							{
								// update-- course general details
								courseTO.setGencurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);
							}

							// check map contain student pre is greater more than equal with student rank pre
							if(allotment.getPrefNo() >= currank.getPrefNo() )
							{
								//update old course general details
								CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
								//check map contain student seat is caste or not and add caste seat
								if(allotment.getIsCaste())
								{

									//update old course of caste seats
									updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

								}
								//check map contain student seat is gen or not	and add genremain seat
								else if(allotment.getIsGeneral())
								{
									updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
								}

								allotment.setIsCaste(false);
								allotment.setIsGeneral(true);   

								// update--map details
								allotment.setPrefNo(currank.getPrefNo());
								allotment.setRank(currank.getRank());
								allotment.setIndexMark(currank.getIndexMark());

								Course course=new Course();
								course.setId(courseTO.getId());
								allotment.setCourse(course);

								//update allotment map
								allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

								// update--course generaldetails
								courseTO.setGencurrank( currank.getRank());

								courseTO.setGenremain(courseTO.getGenremain()-1);
								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println(allotment.getIsGeneral()+" modified data in general id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

							}

						}
						//add new student if map does contains
						else
						{

							//get allotment object seat to store db
							StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,false,allotmentNo);

							//genrankList.add(studentRank);
							allotmentMap.put(currank.getAdmAppln().getId(), allotment);

							// update--course general details
							courseTO.setGencurrank( currank.getRank());
							courseTO.setGenremain(courseTO.getGenremain()-1);

							//update course map
							courseMap.put(courseTO.getId(), courseTO);

							System.out.println("fresh data in general id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

						}				  							
					}//main if over
				}				  
			}// general check over






			// caste sc check start

			//SC start
			else if(category.equalsIgnoreCase("SC"))
			{
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==scId)		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getSccurrank()){

						// check gencurcommon rank less than highest sc caste rank and sc caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighscrank() && courseTO.getScremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());



								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo() < currank.getPrefNo() )
								{
									// update-- course general details
									courseTO.setSccurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}


								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo() > currank.getPrefNo() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste scremain seat
									if(allotment.getIsCaste())
									{

										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

										/*oldCourseTO.setScremain(oldCourseTO.getScremain()+1);
									oldCourseTO.setScover(false);
									System.out.println(oldCourseTO.getName()+"old--------caste in add casterem=="+oldCourseTO.getScremain());
									//update course map
									courseMap.put(oldCourseTO.getId(), oldCourseTO);
										 */
									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setScremain(courseTO.getScremain()-1);
									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									// update-- course general details
									courseTO.setSccurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}	
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setScremain(courseTO.getScremain()-1);
								courseTO.setSccurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			// SC over




			//ST start
			else if(category.equalsIgnoreCase("ST"))
			{
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==stId )		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getStcurrank()){

						// check gencurcommon rank less than highest st caste rank and st caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighstrank() && courseTO.getStremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo() < currank.getPrefNo() )
								{
									// update-- course general details
									courseTO.setStcurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}


								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo() > currank.getPrefNo() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Stremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setStremain(courseTO.getStremain()-1);
									courseTO.setStcurrank( currank.getRank());
									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}	
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setStremain(courseTO.getStremain()-1);
								courseTO.setStcurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//ST Over



			//LC ,LCTAstart
			else if(category.equalsIgnoreCase("LC"))
			{	
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==lcId || currank.getAdmAppln().getPersonalData().getReligionSection().getId()==lctaId)		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getLccurrank()){

						// check gencurcommon rank less than highest lc caste rank and lc caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighlcrank() && courseTO.getLcremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo() < currank.getPrefNo() )
								{
									// update-- course general details
									courseTO.setLccurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}

								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo() > currank.getPrefNo() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Lcremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setLcremain(courseTO.getLcremain()-1);
									courseTO.setLccurrank( currank.getRank());

									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}	
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setLcremain(courseTO.getLcremain()-1);
								courseTO.setLccurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//LC Over


			//BC start
			else if(category.equalsIgnoreCase("BC"))
			{
				//if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==ezhavaId || currank.getAdmAppln().getPersonalData().getReligionSection().getId()==billavaId || currank.getAdmAppln().getPersonalData().getReligionSection().getId()==thiyyaId)		
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==ezhava_thiyya_billava_Id)		

				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getBccurrank()){

						// check gencurcommon rank less than highest Bc caste rank and Bc caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighbcrank() && courseTO.getBcremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo() < currank.getPrefNo() )
								{
									// update-- course general details
									courseTO.setBccurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}

								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo() > currank.getPrefNo() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Bcremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setBcremain(courseTO.getBcremain()-1);
									courseTO.setBccurrank( currank.getRank());

									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}	
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setBcremain(courseTO.getBcremain()-1);
								courseTO.setBccurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//BC Over


			//MU srart
			else if(category.equalsIgnoreCase("MU"))
			{	
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==muId)		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getMucurrank()){

						// check gencurcommon rank less than highest Mu caste rank and Mu caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighmurank() && courseTO.getMuremain() != 0)
						{


							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo() < currank.getPrefNo() )
								{
									// update-- course general details
									courseTO.setMucurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}

								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo() > currank.getPrefNo() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Muremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setMuremain(courseTO.getMuremain()-1);
									courseTO.setMucurrank( currank.getRank());

									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}	
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setMuremain(courseTO.getMuremain()-1);
								courseTO.setMucurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//MU Over


			//OBH start
			else if(category.equalsIgnoreCase("OBH"))
			{
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==obhId)		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getObhcurrank()){

						// check gencurcommon rank less than highest Obh caste rank and Obh caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighobhrank() && courseTO.getObhremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo() < currank.getPrefNo() )
								{
									// update-- course general details
									courseTO.setObhcurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}

								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo() > currank.getPrefNo() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Obhremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setObhremain(courseTO.getObhremain()-1);
									courseTO.setObhcurrank( currank.getRank());

									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}	
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setObhremain(courseTO.getObhremain()-1);
								courseTO.setObhcurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//OBH Over


			//OBX
			else if(category.equalsIgnoreCase("OBX"))
			{

				//OBX	
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==obxId)		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getObxcurrank()){

						// check gencurcommon rank less than highest Obx caste rank and Obx caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighobxrank() && courseTO.getObxremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo() < currank.getPrefNo() )
								{
									// update-- course general details
									courseTO.setObxcurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}

								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo() > currank.getPrefNo() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Obxremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setObxremain(courseTO.getObxremain()-1);
									courseTO.setObxcurrank( currank.getRank());

									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}	
							}
							//add new student if map does contains
							else
							{

								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setObxremain(courseTO.getObxremain()-1);
								courseTO.setObxcurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//OBX Over



			// caste check over





		}// student rank loop close



		///after
		System.out.println("after gencurrent=="+courseTO.getGencurrank());
		System.out.println("after sccurrent=="+courseTO.getSccurrank());
		System.out.println("after stcurrent=="+courseTO.getStcurrank());
		System.out.println("after bccurrent=="+courseTO.getBccurrank());
		System.out.println("after lccurrent=="+courseTO.getLccurrank());
		System.out.println("after mucurrent=="+courseTO.getMucurrank());
		System.out.println("after obhcurrent=="+courseTO.getObhcurrank());
		System.out.println("after obxcurrent=="+courseTO.getObxcurrank());

		System.out.println("after genremain=="+courseTO.getGenremain());
		System.out.println("after scremain=="+courseTO.getScremain());
		System.out.println("after stremain=="+courseTO.getStremain());
		System.out.println("after bcremain=="+courseTO.getBcremain());
		System.out.println("after muremain=="+courseTO.getMuremain());
		System.out.println("after lcremain=="+courseTO.getLcremain());
		System.out.println("after obhremain=="+courseTO.getObhremain());
		System.out.println("after obxremain=="+courseTO.getObxremain());

		System.out.println("after higen=="+courseTO.getHighgenrank());
		System.out.println("after higsc=="+courseTO.getHighscrank());
		System.out.println("after hist=="+courseTO.getHighstrank());
		System.out.println("after higbc=="+courseTO.getHighbcrank());
		System.out.println("after himu=="+courseTO.getHighmurank());
		System.out.println("after higlc=="+courseTO.getHighlcrank());
		System.out.println("after higobh=="+courseTO.getHighobhrank());
		System.out.println("after higobx=="+courseTO.getHighobxrank());

		System.out.println("=========================="+ category+ " =============================");


		//checking all caste and gen over conditions

		//general over
		if (courseTO.getGencurrank() >= courseTO.getHighgenrank() || courseTO.getGenremain() == 0)
		{
			courseTO.setGenover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//sc over
		if(courseTO.getGencurrank() >= courseTO.getHighscrank() || courseTO.getScremain() == 0 || courseTO.getSccurrank() >= courseTO.getHighscrank())
		{
			courseTO.setScover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//st over
		if(courseTO.getGencurrank() >= courseTO.getHighstrank() || courseTO.getStremain() == 0 || courseTO.getStcurrank() >= courseTO.getHighstrank())
		{
			courseTO.setStover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//lc over
		if(courseTO.getGencurrank() >= courseTO.getHighlcrank() || courseTO.getLcremain() == 0 || courseTO.getLccurrank() >= courseTO.getHighlcrank())
		{
			courseTO.setLcover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//bc over
		if(courseTO.getGencurrank() >= courseTO.getHighbcrank() || courseTO.getBcremain() == 0 || courseTO.getBccurrank() >= courseTO.getHighbcrank())
		{
			courseTO.setBcover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//mu over
		if(courseTO.getGencurrank() >= courseTO.getHighmurank() || courseTO.getMuremain() == 0 || courseTO.getMucurrank() >= courseTO.getHighmurank())
		{
			courseTO.setMuover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//obh over
		if(courseTO.getGencurrank() >= courseTO.getHighobhrank() || courseTO.getObhremain() == 0 || courseTO.getObhcurrank() >= courseTO.getHighobhrank())
		{
			courseTO.setObhover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//obx over
		if(courseTO.getGencurrank() >= courseTO.getHighobxrank() || courseTO.getObxremain() == 0 || courseTO.getObxcurrank() >= courseTO.getHighobxrank() )
		{
			courseTO.setObxover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}





	}//end method




	/**
	 * @param allotment
	 * @param courseMap
	 * @param oldCourseTO
	 */
	public void updateCourseMapOnOldCourse(StudentCourseAllotment allotment,Map<Integer,CourseTO> courseMap,CourseTO oldCourseTO) 
	{





		Properties prop = new Properties();
		try 
		{
			InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream("resources/application.properties");
			prop.load(in);
		} 
		catch (FileNotFoundException e)
		{
			log.error("Unable to read properties file...", e);

		} 
		catch (IOException e)
		{
			log.error("Unable to read properties file...", e);

		}

		//sc
		Integer scId=0;	
		String sc = prop.getProperty ("knowledgepro.admission.religionsection.sc");
		if(sc !=null && ! StringUtils.isEmpty(sc)){ 
			scId=Integer.parseInt(sc);
		}

		//ezhava_thiyya_billava
		Integer ezhava_thiyya_billava_Id=0;	
		String ezhava_thiyya_billava = prop.getProperty ("knowledgepro.admission.religionsection.ezhava_thiyya_billava");
		if(ezhava_thiyya_billava !=null && ! StringUtils.isEmpty(ezhava_thiyya_billava)){ 
			ezhava_thiyya_billava_Id=Integer.parseInt(ezhava_thiyya_billava);
		}

		//mu
		Integer muId=0;	
		String mu = prop.getProperty ("knowledgepro.admission.religionsection.mu");
		if(mu !=null && ! StringUtils.isEmpty(mu)){ 
			muId=Integer.parseInt(mu);
		}

		//st
		Integer stId=0;	
		String st = prop.getProperty ("knowledgepro.admission.religionsection.st");
		if(st !=null && ! StringUtils.isEmpty(st)){ 
			stId=Integer.parseInt(st);
		}

		//lc
		Integer lcId=0;	
		String lc = prop.getProperty ("knowledgepro.admission.religionsection.lc");
		if(lc !=null && ! StringUtils.isEmpty(lc)){ 
			lcId=Integer.parseInt(lc);
		}

		//lc
		Integer lctaId=0;	
		String lcta = prop.getProperty ("knowledgepro.admission.religionsection.lcta");
		if(lcta !=null && ! StringUtils.isEmpty(lcta)){ 
			lctaId=Integer.parseInt(lcta);
		}

		//obh
		Integer obhId=0;	
		String obh = prop.getProperty ("knowledgepro.admission.religionsection.obh");
		if(obh !=null && ! StringUtils.isEmpty(obh)){ 
			obhId=Integer.parseInt(obh);
		}

		//obx
		Integer obxId=0;	
		String obx = prop.getProperty ("knowledgepro.admission.religionsection.obx");
		if(obx !=null && ! StringUtils.isEmpty(obx)){ 
			obxId=Integer.parseInt(obx);
		}

		//ezhava
		//Integer ezhavaId=0;	
		String ezhava = prop.getProperty ("knowledgepro.admission.religionsection.ezhava");
		if(ezhava !=null && ! StringUtils.isEmpty(ezhava)){ 
			//ezhavaId=Integer.parseInt(ezhava);
		}


		//billava
		//Integer billavaId=0;	
		String billava = prop.getProperty ("knowledgepro.admission.religionsection.billava");
		if(billava !=null && ! StringUtils.isEmpty(billava)){ 
			//billavaId=Integer.parseInt(billava);
		}

		//thiyya
		//Integer thiyyaId=0;	
		String thiyya = prop.getProperty ("knowledgepro.admission.religionsection.thiyya");
		if(thiyya !=null && ! StringUtils.isEmpty(thiyya)){ 
			//thiyyaId=Integer.parseInt(thiyya);
		}





		// add SC Remain Seat
		if(allotment.getAdmAppln().getPersonalData().getReligionSection().getId()==scId)
		{
			oldCourseTO.setScremain(oldCourseTO.getScremain()+1);
			oldCourseTO.setScover(false);
			//System.out.println(oldCourseTO.getName()+"old--------general in add casterem=="+oldCourseTO.getScremain());

		}
		// add ST Remain Seat
		else if(allotment.getAdmAppln().getPersonalData().getReligionSection().getId()==stId)
		{
			oldCourseTO.setStremain(oldCourseTO.getStremain()+1);
			oldCourseTO.setStover(false);
			//System.out.println(oldCourseTO.getName()+"old--------general in add casterem=="+oldCourseTO.getScremain());

		}
		// add LC ,LCTA Remain Seat
		else if(allotment.getAdmAppln().getPersonalData().getReligionSection().getId()==lcId || allotment.getAdmAppln().getPersonalData().getReligionSection().getId()==lctaId)
		{
			oldCourseTO.setLcremain(oldCourseTO.getLcremain()+1);
			oldCourseTO.setLcover(false);
			//System.out.println(oldCourseTO.getName()+"old--------general in add casterem=="+oldCourseTO.getScremain());

		}

		// add BC Remain Seat BC mens EZHAVA,THIYYA,BILLAVA
		//else if(allotment.getAdmAppln().getPersonalData().getReligionSection().getId()==ezhavaId || allotment.getAdmAppln().getPersonalData().getReligionSection().getId()==billavaId || allotment.getAdmAppln().getPersonalData().getReligionSection().getId()==thiyyaId)
		//{
		//oldCourseTO.setBcremain(oldCourseTO.getBcremain()+1);
		//oldCourseTO.setBcover(false);
		//System.out.println(oldCourseTO.getName()+"old--------general in add casterem=="+oldCourseTO.getScremain());

		//}

		// add BC Remain Seat BC mens EZHAVA,THIYYA,BILLAVA
		else if(allotment.getAdmAppln().getPersonalData().getReligionSection().getId()==ezhava_thiyya_billava_Id)
		{
			oldCourseTO.setBcremain(oldCourseTO.getBcremain()+1);
			oldCourseTO.setBcover(false);
			//System.out.println(oldCourseTO.getName()+"old--------general in add casterem=="+oldCourseTO.getScremain());

		}

		// add MU Remain Seat
		else if(allotment.getAdmAppln().getPersonalData().getReligionSection().getId()==muId)
		{
			oldCourseTO.setMuremain(oldCourseTO.getMuremain()+1);
			oldCourseTO.setMuover(false);
			//System.out.println(oldCourseTO.getName()+"old--------general in add casterem=="+oldCourseTO.getScremain());

		}
		// add OBH Remain Seat
		else if(allotment.getAdmAppln().getPersonalData().getReligionSection().getId()==obhId)
		{
			oldCourseTO.setObhremain(oldCourseTO.getObhremain()+1);
			oldCourseTO.setObhover(false);
			//System.out.println(oldCourseTO.getName()+"old--------general in add casterem=="+oldCourseTO.getScremain());

		}
		// add OBX Remain Seat
		else if(allotment.getAdmAppln().getPersonalData().getReligionSection().getId()==obxId)
		{
			oldCourseTO.setObxremain(oldCourseTO.getObxremain()+1);
			oldCourseTO.setObxover(false);
			//System.out.println(oldCourseTO.getName()+"old--------general in add casterem=="+oldCourseTO.getScremain());

		}




		//update course map
		courseMap.put(oldCourseTO.getId(), oldCourseTO);





	}


	public void updateCourseMapOnOldCourseGeneral(Map<Integer,CourseTO> courseMap,CourseTO oldCourseTO) 
	{
		oldCourseTO.setGenremain(oldCourseTO.getGenremain()+1);
		oldCourseTO.setGenover(false);
		//System.out.println(oldCourseTO.getName()+"old----------caste in add generalrem=="+oldCourseTO.getGenremain());
		//update course map
		courseMap.put(oldCourseTO.getId(), oldCourseTO);

	}


	//get StudentCourseAllotment object
	/**
	 * @param currank
	 * @param category
	 * @return
	 */
	public StudentCourseAllotment getStudetCourseAllotmentBO(StudentRank currank,boolean category,Integer allotmentNo) 
	{


		//create object seat to store db
		StudentCourseAllotment studentRank =new StudentCourseAllotment();

		//AdmAppln appln=new AdmAppln();
		//appln.setId(currank.getAdmAppln().getId());
		studentRank.setAdmAppln(currank.getAdmAppln());

		//Course course=new Course();
		// course.setId(currank.getCourse().getId());
		studentRank.setCourse(currank.getCourse());

		studentRank.setIndexMark(currank.getIndexMark());
		studentRank.setRank(currank.getRank());
		studentRank.setPrefNo(currank.getPrefNo());
		studentRank.setIsAlloted(true);
		studentRank.setActive(true);
		studentRank.setIsAssigned(false);
		studentRank.setIsSatisfied(false);

		if(category)
		{
			studentRank.setIsCaste(true);
			studentRank.setIsGeneral(false);  
		}
		else
		{
			studentRank.setIsCaste(false);
			studentRank.setIsGeneral(true);  
		}

		studentRank.setCreatedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
		studentRank.setAllotmentNo(allotmentNo);

		return studentRank;
	}




	// get general and caste seats based on course and convert course bo to to

	/**
	 * @param courseList
	 * @return
	 */
	public List<CourseTO> copyCourseBoTO(List<Course> courseList) 
	{

		Properties prop = new Properties();
		try 
		{
			InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream("resources/application.properties");
			prop.load(in);
		} 
		catch (FileNotFoundException e)
		{
			log.error("Unable to read properties file...", e);

		} 
		catch (IOException e)
		{
			log.error("Unable to read properties file...", e);

		}

		List<CourseTO> courseToList=new LinkedList<CourseTO>();
		Iterator<Course> it= courseList.iterator();
		while(it.hasNext())
		{


			//sc
			Integer scId=0;	
			String sc = prop.getProperty ("knowledgepro.admission.admittedthrough.sc");
			if(sc !=null && ! StringUtils.isEmpty(sc)){ 
				scId=Integer.parseInt(sc);
			}

			//ezhava_thiyya_billava
			Integer ezhava_thiyya_billava_Id=0;	
			String ezhava_thiyya_billava = prop.getProperty ("knowledgepro.admission.ezhava_thiyya_billava");
			if(ezhava_thiyya_billava !=null && ! StringUtils.isEmpty(ezhava_thiyya_billava)){ 
				ezhava_thiyya_billava_Id=Integer.parseInt(ezhava_thiyya_billava);
			}

			//mu
			Integer muId=0;	
			String mu = prop.getProperty ("knowledgepro.admission.admittedthrough.mu");
			if(mu !=null && ! StringUtils.isEmpty(mu)){ 
				muId=Integer.parseInt(mu);
			}

			//st
			Integer stId=0;	
			String st = prop.getProperty ("knowledgepro.admission.admittedthrough.st");
			if(st !=null && ! StringUtils.isEmpty(st)){ 
				stId=Integer.parseInt(st);
			}

			//lc
			Integer lcId=0;	
			String lc = prop.getProperty ("knowledgepro.admission.admittedthrough.lc");
			if(lc !=null && ! StringUtils.isEmpty(lc)){ 
				lcId=Integer.parseInt(lc);
			}

			//obh
			Integer obhId=0;	
			String obh = prop.getProperty ("knowledgepro.admission.admittedthrough.obh");
			if(obh !=null && ! StringUtils.isEmpty(obh)){ 
				obhId=Integer.parseInt(obh);
			}

			//obx
			Integer obxId=0;	
			String obx = prop.getProperty ("knowledgepro.admission.admittedthrough.obx");
			if(obx !=null && ! StringUtils.isEmpty(obx)){ 
				obxId=Integer.parseInt(obx);
			}

			//general
			Integer generalId=0;	
			String general = prop.getProperty ("knowledgepro.admission.admittedthrough.general");
			if(general !=null && ! StringUtils.isEmpty(general)){ 
				generalId=Integer.parseInt(general);
			}

			CourseTO  courseTo=new CourseTO();
			int totcount=0;

			Course course=it.next();
			Set<SeatAllocation> seatAllocation= course.getSeatAllocations();
			Iterator<SeatAllocation> seatIterator=seatAllocation.iterator();

			while(seatIterator.hasNext())
			{

				SeatAllocation seat=seatIterator.next();
				//GENERAL
				if(seat.getAdmittedThrough().getId()==generalId)
				{
					courseTo.setGenseat(seat.getNoOfSeats());
					courseTo.setGenremain(seat.getNoOfSeats());
					totcount=totcount+seat.getNoOfSeats();
				}

				//SC
				else if(seat.getAdmittedThrough().getId()==scId)
				{
					courseTo.setScseat(seat.getNoOfSeats());
					courseTo.setScremain(seat.getNoOfSeats());
					totcount=totcount+seat.getNoOfSeats();
				}
				//ST
				else if(seat.getAdmittedThrough().getId()==stId)
				{
					courseTo.setStseat(seat.getNoOfSeats());
					courseTo.setStremain(seat.getNoOfSeats());
					totcount=totcount+seat.getNoOfSeats();
				}
				//OBC
				else if(seat.getAdmittedThrough().getId()==ezhava_thiyya_billava_Id)
				{
					courseTo.setBcseat(seat.getNoOfSeats());
					courseTo.setBcremain(seat.getNoOfSeats());
					totcount=totcount+seat.getNoOfSeats();
				}
				//OBX
				else if(seat.getAdmittedThrough().getId()==obxId)
				{
					courseTo.setObxseat(seat.getNoOfSeats());
					courseTo.setObxremain(seat.getNoOfSeats());
					totcount=totcount+seat.getNoOfSeats();
				}
				//OBH
				else if(seat.getAdmittedThrough().getId()==obhId){
					courseTo.setObhseat(seat.getNoOfSeats());
					courseTo.setObhremain(seat.getNoOfSeats());
					totcount=totcount+seat.getNoOfSeats();
				}
				//LC and LCTA
				else if(seat.getAdmittedThrough().getId()==lcId)
				{
					courseTo.setLcseat(seat.getNoOfSeats());
					courseTo.setLcremain(seat.getNoOfSeats());
					totcount=totcount+seat.getNoOfSeats();
				}
				//MU
				else if(seat.getAdmittedThrough().getId()==muId)
				{
					courseTo.setMuseat(seat.getNoOfSeats());
					courseTo.setMuremain(seat.getNoOfSeats());
					totcount=totcount+seat.getNoOfSeats();
				}
				//LCTA
				/*else if(seat.getAdmittedThrough().getId()==18)
					{
						courseTo.setOecseat(seat.getNoOfSeats());
						courseTo.setOecremain(seat.getNoOfSeats());
						totcount=totcount+seat.getNoOfSeats();
					}
				 */
			}// seat while close

			courseTo.setTotcount(totcount);
			courseTo.setTotseats(course.getMaxIntake());
			courseTo.setId(course.getId());
			courseTo.setName(course.getName());


			courseToList.add(courseTo);

		}


		return courseToList;

	}




	//allotting courses for all people based on Rank and Course for Multiple allotment
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public boolean generateCourseAllotmentOnCourseRankForMultipleAllotment(ApplicationEditForm form)throws Exception{

		boolean isAdded=false;
		boolean isStop=true;

		try
		{	

			// create new 	StudentCourseAllotment list object based on course wise and caste

			/*
 	List<StudentCourseAllotment> genrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> scrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> strankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> obcrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> obxrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> obhrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> murankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> lcrankList= new LinkedList<StudentCourseAllotment>();
	List<StudentCourseAllotment> lctarankList= new LinkedList<StudentCourseAllotment>();
			 */
			IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();

			// create new 	allotmentMap with student id and StudentCourseAllotment object for storing db
			Map<Integer,StudentCourseAllotment> allotmentMap=txn.getallotmentMap(Integer.parseInt(form.getProgramTypeId()),Integer.parseInt(form.getAcademicYear()));
			Map<Integer,CourseTO> courseMap=new HashMap<Integer,CourseTO>();
			Map<Integer,List<StudentRank>> genRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteSCRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteSTRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteBCRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteOBXRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteOBHRankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteMURankMap=new HashMap<Integer,List<StudentRank>>();
			Map<Integer,List<StudentRank>> casteLCRankMap=new HashMap<Integer,List<StudentRank>>();


			//get all courses
			List<Course> courseList=CourseTransactionImpl.getInstance().getCourses(Integer.parseInt(form.getProgramTypeId()));

			int courseSize=0;
			courseSize=courseList.size();

			//convert course bos to tos list
			List<CourseTO> courseToList=ApplicationRankHelper.getInstance().copyCourseBoTOForMultipleAllotment(courseList, form);
			//put general details of course into map
			Iterator<CourseTO> courseIterator1=courseToList.iterator();
			while(courseIterator1.hasNext())
			{
				CourseTO courseTO=courseIterator1.next();


				//getting rank list on Course and General rank
				List<StudentRank> rankListOnCourseGeneral=txn.getAdmApplonStudentOnCourseCategoryForMultipleAllotment(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"GENERAL");
				if(rankListOnCourseGeneral.size()!=0)
				{
					//setting genmap as key course id , value gen rank list
					genRankMap.put(courseTO.getId(), rankListOnCourseGeneral);
					int genSize=rankListOnCourseGeneral.size();
					StudentRank sr=rankListOnCourseGeneral.get(genSize-1);
					courseTO.setHighgenrank(sr.getRank());	
				}
				else
				{
					courseTO.setHighgenrank(0);
				}

				//getting rank list on Course and SC caste rank
				List<StudentRank> rankListOnCourseCasteSC=txn.getAdmApplonStudentOnCourseCategoryForMultipleAllotment(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"SC");

				if(rankListOnCourseCasteSC.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteSCRankMap.put(courseTO.getId(), rankListOnCourseCasteSC);
					int casteSize=rankListOnCourseCasteSC.size();
					StudentRank sr=rankListOnCourseCasteSC.get(casteSize-1);
					courseTO.setHighscrank(sr.getRank());

				}
				else
				{
					courseTO.setHighscrank(0);
				}


				//getting rank list on Course and ST caste rank
				List<StudentRank> rankListOnCourseCasteST=txn.getAdmApplonStudentOnCourseCategoryForMultipleAllotment(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"ST");

				if(rankListOnCourseCasteST.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteSTRankMap.put(courseTO.getId(), rankListOnCourseCasteST);
					int casteSize=rankListOnCourseCasteST.size();
					StudentRank sr=rankListOnCourseCasteST.get(casteSize-1);
					courseTO.setHighstrank(sr.getRank());

				}
				else
				{
					courseTO.setHighstrank(0);
				}


				//getting rank list on Course and LC  and LCTA caste rank
				List<StudentRank> rankListOnCourseCasteLC=txn.getAdmApplonStudentOnCourseCategoryForMultipleAllotment(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"LC");

				if(rankListOnCourseCasteLC.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteLCRankMap.put(courseTO.getId(), rankListOnCourseCasteLC);
					int casteSize=rankListOnCourseCasteLC.size();
					StudentRank sr=rankListOnCourseCasteLC.get(casteSize-1);
					courseTO.setHighlcrank(sr.getRank());

				}
				else
				{
					courseTO.setHighlcrank(0);
				}

				//getting rank list on Course and BC caste rank BC mens EZHAVA,THIYYA,BILLAVA
				List<StudentRank> rankListOnCourseCasteBC=txn.getAdmApplonStudentOnCourseCategoryForMultipleAllotment(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"BC");

				if(rankListOnCourseCasteBC.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteBCRankMap.put(courseTO.getId(), rankListOnCourseCasteBC);
					int casteSize=rankListOnCourseCasteBC.size();
					StudentRank sr=rankListOnCourseCasteBC.get(casteSize-1);
					courseTO.setHighbcrank(sr.getRank());

				}
				else
				{
					courseTO.setHighbcrank(0);
				}

				//getting rank list on Course and MU caste rank
				List<StudentRank> rankListOnCourseCasteMU=txn.getAdmApplonStudentOnCourseCategoryForMultipleAllotment(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"MU");

				if(rankListOnCourseCasteMU.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteMURankMap.put(courseTO.getId(), rankListOnCourseCasteMU);
					int casteSize=rankListOnCourseCasteMU.size();
					StudentRank sr=rankListOnCourseCasteMU.get(casteSize-1);
					courseTO.setHighmurank(sr.getRank());

				}
				else
				{
					courseTO.setHighmurank(0);
				}

				//getting rank list on Course and LCTA caste rank
				/*List<StudentRank> rankListOnCourseCasteLCTA=getAdmApplonStudentOnCourseCategoryForMultipleAllotment(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"LCTA");

	if(rankListOnCourseCasteLCTA.size()!=0)
	{
		//setting castemap as key course id , value caste rank list
		 casteRankMap.put(courseTO.getId(), rankListOnCourseCasteLCTA);
		 int casteSize=rankListOnCourseCasteLCTA.size();
		 StudentRank sr=rankListOnCourseCasteLCTA.get(casteSize-1);
		 courseTO.setHighlctarank(sr.getRank());

	}
	else
	{
		courseTO.setHighlctarank(0);
	}
				 */

				//getting rank list on Course and OBH caste rank
				List<StudentRank> rankListOnCourseCasteOBH=txn.getAdmApplonStudentOnCourseCategoryForMultipleAllotment(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"OBH");

				if(rankListOnCourseCasteOBH.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteOBHRankMap.put(courseTO.getId(), rankListOnCourseCasteOBH);
					int casteSize=rankListOnCourseCasteOBH.size();
					StudentRank sr=rankListOnCourseCasteOBH.get(casteSize-1);
					courseTO.setHighobhrank(sr.getRank());

				}
				else
				{
					courseTO.setHighobhrank(0);
				}

				//getting rank list on Course and OBX caste rank
				List<StudentRank> rankListOnCourseCasteOBX=txn.getAdmApplonStudentOnCourseCategoryForMultipleAllotment(courseTO.getId(), Integer.parseInt(form.getAcademicYear()),"OBX");

				if(rankListOnCourseCasteOBX.size()!=0)
				{
					//setting castemap as key course id , value caste rank list
					casteOBXRankMap.put(courseTO.getId(), rankListOnCourseCasteOBX);
					int casteSize=rankListOnCourseCasteOBX.size();
					StudentRank sr=rankListOnCourseCasteOBX.get(casteSize-1);
					courseTO.setHighobxrank(sr.getRank());

				}
				else
				{
					courseTO.setHighobxrank(0);
				}


				courseTO.setGencurrank(0);
				courseTO.setSccurrank(0);
				courseTO.setStcurrank(0);
				courseTO.setBccurrank(0);
				courseTO.setObhcurrank(0);
				courseTO.setObxcurrank(0);
				courseTO.setLccurrank(0);
				courseTO.setMucurrank(0);

				courseMap.put(courseTO.getId(), courseTO);
			}
			//close course iterator map


			//start with infinite loop upto fill all seats 
			while(isStop)
			{

				Iterator<CourseTO> courseIterator=courseToList.iterator();	
				// start first course loop for fill caste seats
				while(courseIterator.hasNext())
				{

					CourseTO courseTO=courseIterator.next();

					// check Course and caste SC rank list size is empty
					List<StudentRank> rankListOnCourseCasteSC=casteSCRankMap.get(courseTO.getId());

					// check sc setas zero or not, if zero stop course
					if(courseTO.getScremain()==null || courseTO.getScremain()==0){
						//course over
						courseTO.setScover(true);
						courseTO.setScseat(0);
						courseTO.setScremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}

					else if(rankListOnCourseCasteSC!=null && rankListOnCourseCasteSC.size()!=0)
					{	 
						// add sc allotment list and map
						addAllotmentlistForMultiple(rankListOnCourseCasteSC, "SC",allotmentMap,courseMap,courseTO,form.getAllotedNo()+1);
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setScover(true);
						courseTO.setScseat(0);
						courseTO.setScremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// SC caste allot over


					// check Course and caste ST rank list size is empty
					List<StudentRank> rankListOnCourseCasteST=casteSTRankMap.get(courseTO.getId());

					// check st setas zero or not, if zero stop course
					if(courseTO.getStremain()==null || courseTO.getStremain()==0){
						//course over
						courseTO.setStover(true);
						courseTO.setStseat(0);
						courseTO.setStremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}

					else if(rankListOnCourseCasteST!=null && rankListOnCourseCasteST.size()!=0)
					{	 
						// add st allotment list and map
						addAllotmentlistForMultiple(rankListOnCourseCasteST, "ST",allotmentMap,courseMap,courseTO,form.getAllotedNo()+1);
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setStover(true);
						courseTO.setStseat(0);
						courseTO.setStremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// ST caste allot over



					// check Course and caste LC rank list size is empty
					List<StudentRank> rankListOnCourseCasteLC=casteLCRankMap.get(courseTO.getId());

					// check lc setas zero or not, if zero stop course
					if(courseTO.getLcremain()==null || courseTO.getLcremain()==0){
						//course over
						courseTO.setLcover(true);
						courseTO.setLcremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					else if(rankListOnCourseCasteLC!=null && rankListOnCourseCasteLC.size()!=0)
					{	 
						// add lc allotment list and map
						addAllotmentlistForMultiple(rankListOnCourseCasteLC, "LC",allotmentMap,courseMap,courseTO,form.getAllotedNo()+1);
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setLcover(true);
						courseTO.setLcseat(0);
						courseTO.setLcremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// LC caste allot over



					// check Course and caste BC rank list size is empty
					List<StudentRank> rankListOnCourseCasteBC=casteBCRankMap.get(courseTO.getId());


					// check bc setas zero or not, if zero stop course
					if(courseTO.getBcremain()==null || courseTO.getBcremain()==0){
						//course over
						courseTO.setBcover(true);
						courseTO.setBcseat(0);
						courseTO.setBcremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);

					}
					else if(rankListOnCourseCasteBC!=null && rankListOnCourseCasteBC.size()!=0)
					{	 
						// add bc allotment list and map
						addAllotmentlistForMultiple(rankListOnCourseCasteBC, "BC",allotmentMap,courseMap,courseTO,form.getAllotedNo()+1);
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setBcover(true);
						courseTO.setBcseat(0);
						courseTO.setBcremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// BC caste allot over




					// check Course and caste MU rank list size is empty
					List<StudentRank> rankListOnCourseCasteMU=casteMURankMap.get(courseTO.getId());

					// check mu setas zero or not, if zero stop course
					if(courseTO.getMuremain()==null || courseTO.getMuremain()==0){
						//course over
						courseTO.setMuover(true);
						courseTO.setMuseat(0);
						courseTO.setMuremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);

					}
					else if(rankListOnCourseCasteMU!=null && rankListOnCourseCasteMU.size()!=0)
					{	 
						// add mu allotment list and map
						addAllotmentlistForMultiple(rankListOnCourseCasteMU, "MU",allotmentMap,courseMap,courseTO,form.getAllotedNo()+1);
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setMuover(true);
						courseTO.setMuseat(0);
						courseTO.setMuremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// MU caste allot over


					// check Course and caste OBH rank list size is empty
					List<StudentRank> rankListOnCourseCasteOBH=casteOBHRankMap.get(courseTO.getId());

					// check obh setas zero or not, if zero stop course
					if(courseTO.getObhremain()==null || courseTO.getObhremain()==0){
						//course over
						courseTO.setObhover(true);
						courseTO.setObhseat(0);
						courseTO.setObhremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);

					}
					else if(rankListOnCourseCasteOBH!=null && rankListOnCourseCasteOBH.size()!=0)
					{	 
						// add obh allotment list and map
						addAllotmentlistForMultiple(rankListOnCourseCasteOBH, "OBH",allotmentMap,courseMap,courseTO,form.getAllotedNo()+1);
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setObhover(true);
						courseTO.setObhseat(0);
						courseTO.setObhremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// OBH caste allot over


					// check Course and caste OBX rank list size is empty
					List<StudentRank> rankListOnCourseCasteOBX=casteOBXRankMap.get(courseTO.getId());
					// check obx setas zero or not, if zero stop course
					if(courseTO.getObxremain()==null || courseTO.getObxremain()==0){
						//course over
						courseTO.setObxover(true);
						courseTO.setObxseat(0);
						courseTO.setObxremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);

					}
					else if(rankListOnCourseCasteOBX!=null && rankListOnCourseCasteOBX.size()!=0)
					{	 
						// add obx allotment list and map
						addAllotmentlistForMultiple(rankListOnCourseCasteOBX, "OBX",allotmentMap,courseMap,courseTO,form.getAllotedNo()+1);
					}// caste ranklist size close
					else
					{
						//course over
						courseTO.setObxover(true);
						courseTO.setObxseat(0);
						courseTO.setObxremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}
					// OBX caste allot over



				}// first caste course loop close



				Iterator<CourseTO> courseIterator2=courseToList.iterator();
				// start second course loop for fill general seats
				while(courseIterator2.hasNext())
				{
					CourseTO courseTO=courseIterator2.next();

					//getting rank list on Course and General rank
					List<StudentRank> rankListOnCourseGeneral=genRankMap.get(courseTO.getId());// check Course and caste rank list size is empty
					// check st setas zero or not, if zero stop course
					if(courseTO.getGenremain()==null || courseTO.getGenremain()==0){
						//course over
						courseTO.setGenover(true);
						courseTO.setGenseat(0);
						courseTO.setGenremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);

					}
					else if(rankListOnCourseGeneral!=null && rankListOnCourseGeneral.size()!=0)
					{
						// add general allotment list and map
						addAllotmentlistForMultiple(rankListOnCourseGeneral, "General",allotmentMap,courseMap,courseTO,form.getAllotedNo()+1);			
					}// general ranklist size close
					else
					{
						//course over
						courseTO.setGenover(true);
						courseTO.setGenseat(0);
						courseTO.setGenremain(0);

						//update course map
						courseMap.put(courseTO.getId(), courseTO);
					}


				}
				//second general course loop close

				// condition to check all seats fill are not
				int i=0;
				Set<Entry<Integer, CourseTO>> courseSet= courseMap.entrySet();
				Iterator<Entry<Integer, CourseTO>> icourse=courseSet.iterator();
				while(icourse.hasNext())
				{
					Entry<Integer, CourseTO> e=icourse.next();
					CourseTO courseTO=e.getValue();
					if(courseTO.isGenover() && courseTO.isScover() && courseTO.isStover() && courseTO.isBcover() && courseTO.isObhover() && courseTO.isObxover() && courseTO.isMuover() && courseTO.isLcover() )
					{
						i++;
						System.out.println(courseTO.getName()+"=======  over   ========="+i);
					}
				}


				// course size course over equals stop infinte loop
				if(courseSize==i)
				{
					isStop=false;
				}

			}// infinite loop close




			//getting values from allotmap and add values to list   
			List<StudentCourseAllotment> allotmentList=new ArrayList<StudentCourseAllotment>();
			Set<Entry<Integer, StudentCourseAllotment>> studentCourseAllotmentSet= allotmentMap.entrySet();

			Iterator<Entry<Integer, StudentCourseAllotment>> i=studentCourseAllotmentSet.iterator();
			while(i.hasNext())
			{
				Entry<Integer, StudentCourseAllotment> e=i.next();
				StudentCourseAllotment allotment=e.getValue();
				allotmentList.add(allotment);
			}

			// adding records course wise in database
			isAdded=txn.generateCourseAllotment(allotmentList);

		}// try close
		catch(Exception e)
		{
			isAdded=false;
			Session session = null;
			Transaction transaction = null;
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query1=session.createQuery("delete StudentCourseAllotment s where s.createdDate = current_date");
			//query.setString("current_date1",date);
			query1.executeUpdate();
			transaction.commit();
			session.close();
			System.out.println("helper===============Error occured in while alloting courses to student ================="+e);
			throw e;
		}
		finally
		{

		}

		return isAdded;


	}




	// get general and caste seats based on course and convert course bo to to for Multiple allotment

	/**
	 * @param courseList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CourseTO> copyCourseBoTOForMultipleAllotment(List<Course> courseList,ApplicationEditForm admForm) 
	{

		List list=null;
		Session session = null;
		Query hqlQuery=null;
		Properties prop = new Properties();
		try 
		{
			InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream("resources/application.properties");
			prop.load(in);
			session = HibernateUtil.getSession();

		} 
		catch (FileNotFoundException e)
		{
			log.error("Unable to read properties file...", e);

		} 
		catch (IOException e)
		{
			log.error("Unable to read properties file...", e);

		}
		catch (Exception e)
		{
			log.error("Unable to read properties file...", e);

		}


		List<CourseTO> courseToList=new LinkedList<CourseTO>();
		Iterator<Course> it= courseList.iterator();
		while(it.hasNext())
		{




			//sc
			Integer scId=0;	
			String sc = prop.getProperty ("knowledgepro.admission.admittedthrough.sc");
			if(sc !=null && ! StringUtils.isEmpty(sc)){ 
				scId=Integer.parseInt(sc);
			}

			//ezhava_thiyya_billava
			Integer ezhava_thiyya_billava_Id=0;	
			String ezhava_thiyya_billava = prop.getProperty ("knowledgepro.admission.ezhava_thiyya_billava");
			if(ezhava_thiyya_billava !=null && ! StringUtils.isEmpty(ezhava_thiyya_billava)){ 
				ezhava_thiyya_billava_Id=Integer.parseInt(ezhava_thiyya_billava);
			}

			//mu
			Integer muId=0;	
			String mu = prop.getProperty ("knowledgepro.admission.admittedthrough.mu");
			if(mu !=null && ! StringUtils.isEmpty(mu)){ 
				muId=Integer.parseInt(mu);
			}

			//st
			Integer stId=0;	
			String st = prop.getProperty ("knowledgepro.admission.admittedthrough.st");
			if(st !=null && ! StringUtils.isEmpty(st)){ 
				stId=Integer.parseInt(st);
			}

			//lc
			Integer lcId=0;	
			String lc = prop.getProperty ("knowledgepro.admission.admittedthrough.lc");
			if(lc !=null && ! StringUtils.isEmpty(lc)){ 
				lcId=Integer.parseInt(lc);
			}

			//obh
			Integer obhId=0;	
			String obh = prop.getProperty ("knowledgepro.admission.admittedthrough.obh");
			if(obh !=null && ! StringUtils.isEmpty(obh)){ 
				obhId=Integer.parseInt(obh);
			}

			//obx
			Integer obxId=0;	
			String obx = prop.getProperty ("knowledgepro.admission.admittedthrough.obx");
			if(obx !=null && ! StringUtils.isEmpty(obx)){ 
				obxId=Integer.parseInt(obx);
			}

			//general
			Integer generalId=0;	
			String general = prop.getProperty ("knowledgepro.admission.admittedthrough.general");
			if(general !=null && ! StringUtils.isEmpty(general)){ 
				generalId=Integer.parseInt(general);
			}




			//getting religionsection id from application.properties

			//sc
			Integer scIdr=0;	
			String scr = prop.getProperty ("knowledgepro.admission.religionsection.sc");
			if(scr !=null && ! StringUtils.isEmpty(scr)){ 
				scIdr=Integer.parseInt(scr);
			}

			//ezhava_thiyya_billava
			Integer ezhava_thiyya_billava_Idr=0;	
			String ezhava_thiyya_billavar = prop.getProperty ("knowledgepro.admission.religionsection.ezhava_thiyya_billava");
			if(ezhava_thiyya_billavar !=null && ! StringUtils.isEmpty(ezhava_thiyya_billavar)){ 
				ezhava_thiyya_billava_Idr=Integer.parseInt(ezhava_thiyya_billavar);
			}

			//mu
			Integer muIdr=0;	
			String mur = prop.getProperty ("knowledgepro.admission.religionsection.mu");
			if(mur !=null && ! StringUtils.isEmpty(mur)){ 
				muIdr=Integer.parseInt(mur);
			}

			//st
			Integer stIdr=0;	
			String str = prop.getProperty ("knowledgepro.admission.religionsection.st");
			if(str !=null && ! StringUtils.isEmpty(str)){ 
				stIdr=Integer.parseInt(str);
			}

			//lc
			Integer lcIdr=0;	
			String lcr = prop.getProperty ("knowledgepro.admission.religionsection.lc");
			if(lcr !=null && ! StringUtils.isEmpty(lcr)){ 
				lcIdr=Integer.parseInt(lcr);
			}

			//lc
			//Integer lctaIdr=0;	
			String lctar = prop.getProperty ("knowledgepro.admission.religionsection.lcta");
			if(lctar !=null && ! StringUtils.isEmpty(lctar)){ 
				//lctaIdr=Integer.parseInt(lctar);
			}

			//obh
			Integer obhIdr=0;	
			String obhr = prop.getProperty ("knowledgepro.admission.religionsection.obh");
			if(obhr !=null && ! StringUtils.isEmpty(obhr)){ 
				obhIdr=Integer.parseInt(obhr);
			}

			//obx
			Integer obxIdr=0;	
			String obxr= prop.getProperty ("knowledgepro.admission.religionsection.obx");
			if(obxr !=null && ! StringUtils.isEmpty(obxr)){ 
				obxIdr=Integer.parseInt(obxr);
			}

			//ezhava
			//Integer ezhavaIdr=0;	
			String ezhavar = prop.getProperty ("knowledgepro.admission.religionsection.ezhava");
			if(ezhavar !=null && ! StringUtils.isEmpty(ezhavar)){ 
				//ezhavaIdr=Integer.parseInt(ezhavar);
			}


			//billava
			//Integer billavaIdr=0;	
			String billavar = prop.getProperty ("knowledgepro.admission.religionsection.billava");
			if(billavar !=null && ! StringUtils.isEmpty(billavar)){ 
				//billavaIdr=Integer.parseInt(billavar);
			}

			//thiyya
			//Integer thiyyaIdr=0;	
			String thiyyar = prop.getProperty ("knowledgepro.admission.religionsection.thiyya");
			if(thiyyar !=null && ! StringUtils.isEmpty(thiyyar)){ 
				//thiyyaIdr=Integer.parseInt(thiyyar);
			}




			CourseTO  courseTo=new CourseTO();
			int totcount=0;

			Course course=it.next();
			Set<SeatAllocation> seatAllocation= course.getSeatAllocations();
			Iterator<SeatAllocation> seatIterator=seatAllocation.iterator();


			while(seatIterator.hasNext())
			{

				SeatAllocation seat=seatIterator.next();
				//GENERAL
				if(seat.getAdmittedThrough().getId()==generalId)
				{
					courseTo.setGenseat(seat.getNoOfSeats());
					//getting alloted seats
					hqlQuery = session.createQuery("from StudentCourseAllotment s where  s.course.id=:courseId and s.isGeneral=1 and (s.isAssigned=1 or s.isSatisfied=1)  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
					hqlQuery.setInteger("courseId",course.getId());
					list=hqlQuery.list();
					courseTo.setGenremain(seat.getNoOfSeats()-list.size());
					totcount=totcount+courseTo.getGenremain();
				}

				//SC
				else if(seat.getAdmittedThrough().getId()==scId)
				{
					courseTo.setScseat(seat.getNoOfSeats());
					//getting alloted seats
					hqlQuery = session.createQuery("from StudentCourseAllotment s where s.admAppln.personalData.religionSection.id=:casteId and s.course.id=:courseId and s.isCaste=1 and (s.isAssigned=1 or s.isSatisfied=1)  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
					hqlQuery.setInteger("courseId",course.getId());
					hqlQuery.setInteger("casteId",scIdr);
					list=hqlQuery.list();
					courseTo.setScremain(seat.getNoOfSeats()-list.size());
					totcount=totcount+courseTo.getScremain();
				}
				//ST
				else if(seat.getAdmittedThrough().getId()==stId)
				{
					courseTo.setStseat(seat.getNoOfSeats());
					//getting alloted seats
					hqlQuery = session.createQuery("from StudentCourseAllotment s where s.admAppln.personalData.religionSection.id=:casteId and s.course.id=:courseId and s.isCaste=1 and (s.isAssigned=1 or s.isSatisfied=1)  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
					hqlQuery.setInteger("courseId",course.getId());
					hqlQuery.setInteger("casteId",stIdr);
					list=hqlQuery.list();
					courseTo.setStremain(seat.getNoOfSeats()-list.size());
					totcount=totcount+courseTo.getStremain();
				}
				//OBC
				else if(seat.getAdmittedThrough().getId()==ezhava_thiyya_billava_Id)
				{
					courseTo.setBcseat(seat.getNoOfSeats());
					//getting alloted seats
					hqlQuery = session.createQuery("from StudentCourseAllotment s where s.admAppln.personalData.religionSection.id=:casteId and s.course.id=:courseId and s.isCaste=1 and (s.isAssigned=1 or s.isSatisfied=1)  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
					hqlQuery.setInteger("courseId",course.getId());
					hqlQuery.setInteger("casteId",ezhava_thiyya_billava_Idr);
					list=hqlQuery.list();
					courseTo.setBcremain(seat.getNoOfSeats()-list.size());

					totcount=totcount+courseTo.getBcremain();
				}
				//OBX
				else if(seat.getAdmittedThrough().getId()==obxId)
				{
					courseTo.setObxseat(seat.getNoOfSeats());
					//getting alloted seats
					hqlQuery = session.createQuery("from StudentCourseAllotment s where s.admAppln.personalData.religionSection.id=:casteId and s.course.id=:courseId and s.isCaste=1 and (s.isAssigned=1 or s.isSatisfied=1)  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
					hqlQuery.setInteger("courseId",course.getId());
					hqlQuery.setInteger("casteId",obxIdr);
					list=hqlQuery.list();
					courseTo.setObxremain(seat.getNoOfSeats()-list.size());

					totcount=totcount+courseTo.getObxremain();
				}
				//OBH
				else if(seat.getAdmittedThrough().getId()==obhId){
					courseTo.setObhseat(seat.getNoOfSeats());
					//getting alloted seats
					hqlQuery = session.createQuery("from StudentCourseAllotment s where s.admAppln.personalData.religionSection.id=:casteId and s.course.id=:courseId and s.isCaste=1 and (s.isAssigned=1 or s.isSatisfied=1)  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
					hqlQuery.setInteger("courseId",course.getId());
					hqlQuery.setInteger("casteId",obhIdr);
					list=hqlQuery.list();
					courseTo.setObhremain(seat.getNoOfSeats()-list.size());

					totcount=totcount+courseTo.getObhremain();
				}
				//LC and LCTA
				else if(seat.getAdmittedThrough().getId()==lcId)
				{
					courseTo.setLcseat(seat.getNoOfSeats());
					//getting alloted seats
					hqlQuery = session.createQuery("from StudentCourseAllotment s where s.admAppln.personalData.religionSection.id=:casteId and s.course.id=:courseId and s.isCaste=1 and (s.isAssigned=1 or s.isSatisfied=1)  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
					hqlQuery.setInteger("courseId",course.getId());
					hqlQuery.setInteger("casteId",lcIdr);
					list=hqlQuery.list();
					courseTo.setLcremain(seat.getNoOfSeats()-list.size());

					totcount=totcount+courseTo.getLcremain();
				}
				//MU
				else if(seat.getAdmittedThrough().getId()==muId)
				{
					courseTo.setMuseat(seat.getNoOfSeats());
					//getting alloted seats
					hqlQuery = session.createQuery("from StudentCourseAllotment s where s.admAppln.personalData.religionSection.id=:casteId and s.course.id=:courseId and s.isCaste=1 and(s.isAssigned=1 or s.isSatisfied=1)  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
					hqlQuery.setInteger("courseId",course.getId());
					hqlQuery.setInteger("casteId",muIdr);
					list=hqlQuery.list();
					courseTo.setMuremain(seat.getNoOfSeats()-list.size());

					totcount=totcount+courseTo.getMuremain();
				}
				//LCTA
				/*else if(seat.getAdmittedThrough().getId()==18)
				{
					courseTo.setOecseat(seat.getNoOfSeats());
					courseTo.setOecremain(seat.getNoOfSeats());
					totcount=totcount+courseTo.getMuremain();
				}
				 */
			}// seat while close

			courseTo.setTotcount(totcount);
			courseTo.setTotseats(course.getMaxIntake());
			courseTo.setId(course.getId());
			courseTo.setName(course.getName());


			courseToList.add(courseTo);

		}


		return courseToList;

	}




	public void addAllotmentlistForMultiple(List<StudentRank> rankList,String category,Map<Integer,StudentCourseAllotment> allotmentMap,Map<Integer,CourseTO> courseMap,CourseTO itrcourseTO,Integer allotmentNo ) 
	{

		CourseTO courseTO=courseMap.get(itrcourseTO.getId());
		System.out.println();
		System.out.println("==========================  "+ courseTO.getName() + " ============================="+category);
		System.out.println("before gencurrent=="+courseTO.getGencurrank());
		System.out.println("before sccurrent=="+courseTO.getSccurrank());
		System.out.println("before stcurrent=="+courseTO.getStcurrank());
		System.out.println("before bccurrent=="+courseTO.getBccurrank());
		System.out.println("before lccurrent=="+courseTO.getLccurrank());
		System.out.println("before mucurrent=="+courseTO.getMucurrank());
		System.out.println("before obhcurrent=="+courseTO.getObhcurrank());
		System.out.println("before obxcurrent=="+courseTO.getObxcurrank());

		System.out.println("before genremain=="+courseTO.getGenremain());
		System.out.println("before scremain=="+courseTO.getScremain());
		System.out.println("before stremain=="+courseTO.getStremain());
		System.out.println("before bcremain=="+courseTO.getBcremain());
		System.out.println("before muremain=="+courseTO.getMuremain());
		System.out.println("before lcremain=="+courseTO.getLcremain());
		System.out.println("before obhremain=="+courseTO.getObhremain());
		System.out.println("before obxremain=="+courseTO.getObxremain());

		System.out.println("before higen=="+courseTO.getHighgenrank());
		System.out.println("before higsc=="+courseTO.getHighscrank());
		System.out.println("before hist=="+courseTO.getHighstrank());
		System.out.println("before higbc=="+courseTO.getHighbcrank());
		System.out.println("before himu=="+courseTO.getHighmurank());
		System.out.println("before higlc=="+courseTO.getHighlcrank());
		System.out.println("before higobh=="+courseTO.getHighobhrank());
		System.out.println("before higobx=="+courseTO.getHighobxrank());


		System.out.println("==========================  "+ category+ " =============================");



		Properties prop = new Properties();
		try 
		{
			InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream("resources/application.properties");
			prop.load(in);
		} 
		catch (FileNotFoundException e)
		{
			log.error("Unable to read properties file...", e);

		} 
		catch (IOException e)
		{
			log.error("Unable to read properties file...", e);

		}

		//sc
		Integer scId=0;	
		String sc = prop.getProperty ("knowledgepro.admission.religionsection.sc");
		if(sc !=null && ! StringUtils.isEmpty(sc)){ 
			scId=Integer.parseInt(sc);
		}

		//ezhava_thiyya_billava
		Integer ezhava_thiyya_billava_Id=0;	
		String ezhava_thiyya_billava = prop.getProperty ("knowledgepro.admission.religionsection.ezhava_thiyya_billava");
		if(ezhava_thiyya_billava !=null && ! StringUtils.isEmpty(ezhava_thiyya_billava)){ 
			ezhava_thiyya_billava_Id=Integer.parseInt(ezhava_thiyya_billava);
		}

		//mu
		Integer muId=0;	
		String mu = prop.getProperty ("knowledgepro.admission.religionsection.mu");
		if(mu !=null && ! StringUtils.isEmpty(mu)){ 
			muId=Integer.parseInt(mu);
		}

		//st
		Integer stId=0;	
		String st = prop.getProperty ("knowledgepro.admission.religionsection.st");
		if(st !=null && ! StringUtils.isEmpty(st)){ 
			stId=Integer.parseInt(st);
		}

		//lc
		Integer lcId=0;	
		String lc = prop.getProperty ("knowledgepro.admission.religionsection.lc");
		if(lc !=null && ! StringUtils.isEmpty(lc)){ 
			lcId=Integer.parseInt(lc);
		}

		//lc
		Integer lctaId=0;	
		String lcta = prop.getProperty ("knowledgepro.admission.religionsection.lcta");
		if(lcta !=null && ! StringUtils.isEmpty(lcta)){ 
			lctaId=Integer.parseInt(lcta);
		}

		//obh
		Integer obhId=0;	
		String obh = prop.getProperty ("knowledgepro.admission.religionsection.obh");
		if(obh !=null && ! StringUtils.isEmpty(obh)){ 
			obhId=Integer.parseInt(obh);
		}

		//obx
		Integer obxId=0;	
		String obx = prop.getProperty ("knowledgepro.admission.religionsection.obx");
		if(obx !=null && ! StringUtils.isEmpty(obx)){ 
			obxId=Integer.parseInt(obx);
		}

		//ezhava
		//Integer ezhavaId=0;	
		String ezhava = prop.getProperty ("knowledgepro.admission.religionsection.ezhava");
		if(ezhava !=null && ! StringUtils.isEmpty(ezhava)){ 
			//ezhavaId=Integer.parseInt(ezhava);
		}


		//billava
		//Integer billavaId=0;	
		String billava = prop.getProperty ("knowledgepro.admission.religionsection.billava");
		if(billava !=null && ! StringUtils.isEmpty(billava)){ 
			//billavaId=Integer.parseInt(billava);
		}

		//thiyya
		//Integer thiyyaId=0;	
		String thiyya = prop.getProperty ("knowledgepro.admission.religionsection.thiyya");
		if(thiyya !=null && ! StringUtils.isEmpty(thiyya)){ 
			//thiyyaId=Integer.parseInt(thiyya);
		}









		Iterator<StudentRank> rankIterator=rankList.iterator();
		// loop for getting StudentRank on rank and preference
		while(rankIterator.hasNext()){	

			StudentRank currank = (StudentRank) rankIterator.next();




			// general check start
			if(category.equalsIgnoreCase("General"))
			{
				// check student rank is more than gencurcommon rank
				if(currank.getRank() > courseTO.getGencurrank())
				{
					// check gencurcommon rank less than highest gen rank and gen seats not equal zero
					if (courseTO.getGencurrank() < courseTO.getHighgenrank() && courseTO.getGenremain() != 0)
					{
						// check map contains student or not
						if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
						{
							StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());
							// check map contain student pre less than with student rank pre
							if(allotment.getPrefNo().intValue() < currank.getPrefNo().intValue()  )
							{
								// update-- course general details
								courseTO.setGencurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);
							}



							// for multiple allotment
							if(allotment.getPrefNo().intValue() == currank.getPrefNo().intValue() )
							{


								//update old course general details
								CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
								//check map contain student seat is caste or not and add caste seat
								if(allotment.getIsCaste())
								{

									//update old course of caste seats
									updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);


									allotment.setIsCaste(false);
									allotment.setIsGeneral(true);   

									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());
									allotment.setAllotmentNo(allotmentNo);


									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);


									// update--course generaldetails
									courseTO.setGencurrank( currank.getRank());

									courseTO.setGenremain(courseTO.getGenremain()-1);
									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsGeneral()+" modified data in general id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}
								//check map contain student seat is gen or not	and add genremain seat
								else if(allotment.getIsGeneral())
								{
									//updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									courseTO.setGencurrank( currank.getRank());
									courseMap.put(courseTO.getId(), courseTO);
								}





							}



							// check map contain student pre is greater more than equal with student rank pre
							if(allotment.getPrefNo().intValue() > currank.getPrefNo().intValue() && !allotment.getIsSatisfied())
							{


								//update old course general details
								CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
								//check map contain student seat is caste or not and add caste seat
								if(allotment.getIsCaste())
								{

									//update old course of caste seats
									updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

								}
								//check map contain student seat is gen or not	and add genremain seat
								else if(allotment.getIsGeneral())
								{
									updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
								}

								allotment.setIsCaste(false);
								allotment.setIsGeneral(true);   

								// update--map details
								allotment.setPrefNo(currank.getPrefNo());
								allotment.setRank(currank.getRank());
								allotment.setIndexMark(currank.getIndexMark());
								allotment.setAllotmentNo(allotmentNo);
								allotment.setIsAssigned(false);
								allotment.setIsSatisfied(false);


								Course course=new Course();
								course.setId(courseTO.getId());
								allotment.setCourse(course);

								//update allotment map
								allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

								// update--course generaldetails
								courseTO.setGencurrank( currank.getRank());

								courseTO.setGenremain(courseTO.getGenremain()-1);
								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println(allotment.getIsGeneral()+" modified data in general id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());




							}

							if(allotment.getIsSatisfied()){

								courseTO.setGencurrank( currank.getRank());
								courseMap.put(courseTO.getId(), courseTO);

							}

						}
						//add new student if map does contains
						else
						{

							//get allotment object seat to store db
							StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,false,allotmentNo);

							//genrankList.add(studentRank);
							allotmentMap.put(currank.getAdmAppln().getId(), allotment);

							// update--course general details
							courseTO.setGencurrank( currank.getRank());
							courseTO.setGenremain(courseTO.getGenremain()-1);

							//update course map
							courseMap.put(courseTO.getId(), courseTO);

							System.out.println("fresh data in general id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

						}				  							
					}//main if over
				}				  
			}// general check over






			// caste sc check start

			//SC start
			else if(category.equalsIgnoreCase("SC"))
			{
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==scId)		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getSccurrank()){

						// check gencurcommon rank less than highest sc caste rank and sc caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighscrank() && courseTO.getScremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());



								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo().intValue() < currank.getPrefNo().intValue() )
								{
									// update-- course general details
									courseTO.setSccurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}


								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo().intValue() > currank.getPrefNo().intValue() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste scremain seat
									if(allotment.getIsCaste())
									{

										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

										/*oldCourseTO.setScremain(oldCourseTO.getScremain()+1);
								oldCourseTO.setScover(false);
								System.out.println(oldCourseTO.getName()+"old--------caste in add casterem=="+oldCourseTO.getScremain());
								//update course map
								courseMap.put(oldCourseTO.getId(), oldCourseTO);
										 */
									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());
									allotment.setAllotmentNo(allotmentNo);
									allotment.setIsAssigned(false);
									allotment.setIsSatisfied(false);


									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setScremain(courseTO.getScremain()-1);
									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									// update-- course general details
									courseTO.setSccurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}	

								if(allotment.getPrefNo().intValue()==currank.getPrefNo().intValue()){
									courseTO.setSccurrank( currank.getRank());
									courseMap.put(courseTO.getId(), courseTO);

								}
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setScremain(courseTO.getScremain()-1);
								courseTO.setSccurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			// SC over




			//ST start
			else if(category.equalsIgnoreCase("ST"))
			{
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==stId )		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getStcurrank()){

						// check gencurcommon rank less than highest st caste rank and st caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighstrank() && courseTO.getStremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo().intValue() < currank.getPrefNo().intValue() )
								{
									// update-- course general details
									courseTO.setStcurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}


								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo().intValue() > currank.getPrefNo().intValue() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Stremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());
									allotment.setAllotmentNo(allotmentNo);
									allotment.setIsAssigned(false);
									allotment.setIsSatisfied(false);

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setStremain(courseTO.getStremain()-1);
									courseTO.setStcurrank( currank.getRank());
									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}

								if(allotment.getPrefNo().intValue()==currank.getPrefNo().intValue()){

									courseTO.setStcurrank( currank.getRank());
									courseMap.put(courseTO.getId(), courseTO);
								}
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setStremain(courseTO.getStremain()-1);
								courseTO.setStcurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//ST Over



			//LC ,LCTAstart
			else if(category.equalsIgnoreCase("LC"))
			{	
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==lcId || currank.getAdmAppln().getPersonalData().getReligionSection().getId()==lctaId)		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getLccurrank()){

						// check gencurcommon rank less than highest lc caste rank and lc caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighlcrank() && courseTO.getLcremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo().intValue() < currank.getPrefNo().intValue() )
								{
									// update-- course general details
									courseTO.setLccurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}

								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo().intValue() > currank.getPrefNo().intValue() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Lcremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());


									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setLcremain(courseTO.getLcremain()-1);
									courseTO.setLccurrank( currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());
									allotment.setAllotmentNo(allotmentNo);
									allotment.setIsAssigned(false);
									allotment.setIsSatisfied(false);

									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}

								if(allotment.getPrefNo().intValue()==currank.getPrefNo().intValue()){

									courseTO.setLccurrank( currank.getRank());
									courseMap.put(courseTO.getId(), courseTO);
								}
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setLcremain(courseTO.getLcremain()-1);
								courseTO.setLccurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//LC Over


			//BC start
			else if(category.equalsIgnoreCase("BC"))
			{
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==ezhava_thiyya_billava_Id)		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getBccurrank()){

						// check gencurcommon rank less than highest Bc caste rank and Bc caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighbcrank() && courseTO.getBcremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo().intValue() < currank.getPrefNo().intValue() )
								{
									// update-- course general details
									courseTO.setBccurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}

								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo().intValue() > currank.getPrefNo().intValue() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Bcremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());
									allotment.setAllotmentNo(allotmentNo);
									allotment.setIsAssigned(false);
									allotment.setIsSatisfied(false);

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setBcremain(courseTO.getBcremain()-1);
									courseTO.setBccurrank( currank.getRank());

									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}	

								if(allotment.getPrefNo().intValue()==currank.getPrefNo().intValue()){

									courseTO.setBccurrank( currank.getRank());
									courseMap.put(courseTO.getId(), courseTO);
								}
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setBcremain(courseTO.getBcremain()-1);
								courseTO.setBccurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//BC Over


			//MU srart
			else if(category.equalsIgnoreCase("MU"))
			{	
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==muId)		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getMucurrank()){

						// check gencurcommon rank less than highest Mu caste rank and Mu caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighmurank() && courseTO.getMuremain() != 0)
						{


							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo().intValue() < currank.getPrefNo().intValue() )
								{
									// update-- course general details
									courseTO.setMucurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}

								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo().intValue() > currank.getPrefNo().intValue() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Muremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());
									allotment.setAllotmentNo(allotmentNo);
									allotment.setIsAssigned(false);
									allotment.setIsSatisfied(false);

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setMuremain(courseTO.getMuremain()-1);
									courseTO.setMucurrank( currank.getRank());

									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}

								if(allotment.getPrefNo().intValue()==currank.getPrefNo().intValue()){

									courseTO.setMucurrank( currank.getRank());
									courseMap.put(courseTO.getId(), courseTO);
								}
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setMuremain(courseTO.getMuremain()-1);
								courseTO.setMucurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//MU Over


			//OBH start
			else if(category.equalsIgnoreCase("OBH"))
			{
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==obhId)		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getObhcurrank()){

						// check gencurcommon rank less than highest Obh caste rank and Obh caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighobhrank() && courseTO.getObhremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo().intValue() < currank.getPrefNo().intValue() )
								{
									// update-- course general details
									courseTO.setObhcurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}

								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo().intValue() > currank.getPrefNo().intValue() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Obhremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());
									allotment.setAllotmentNo(allotmentNo);
									allotment.setIsAssigned(false);
									allotment.setIsSatisfied(false);

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setObhremain(courseTO.getObhremain()-1);
									courseTO.setObhcurrank( currank.getRank());

									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}	
								if(allotment.getPrefNo().intValue()==currank.getPrefNo().intValue()){

									courseTO.setObhcurrank( currank.getRank());
									courseMap.put(courseTO.getId(), courseTO);
								}
							}
							//add new student if map does contains
							else
							{
								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setObhremain(courseTO.getObhremain()-1);
								courseTO.setObhcurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//OBH Over


			//OBX
			else if(category.equalsIgnoreCase("OBX"))
			{

				//OBX	
				if(currank.getAdmAppln().getPersonalData().getReligionSection().getId()==obxId)		
				{
					// check student rank is more than gencurcommon rank
					if(currank.getRank() > courseTO.getGencurrank() && currank.getRank() > courseTO.getObxcurrank()){

						// check gencurcommon rank less than highest Obx caste rank and Obx caste seats not equal zero
						if(courseTO.getGencurrank() < courseTO.getHighobxrank() && courseTO.getObxremain() != 0)
						{
							// check map contains student or not
							if(allotmentMap.containsKey(currank.getAdmAppln().getId()))
							{
								StudentCourseAllotment allotment=allotmentMap.get(currank.getAdmAppln().getId());

								// check map contain student pre less than with student rank pre
								if(allotment.getPrefNo().intValue() < currank.getPrefNo().intValue() )
								{
									// update-- course general details
									courseTO.setObxcurrank( currank.getRank());

									//update course map
									courseMap.put(courseTO.getId(), courseTO);
								}

								// check map contain student pre is greater more than equal with student rank pre
								if(allotment.getPrefNo().intValue() > currank.getPrefNo().intValue() )
								{
									//update old course general details
									CourseTO oldCourseTO=courseMap.get(allotment.getCourse().getId());
									//check map contain student seat is caste or not and add caste Obxremain seat
									if(allotment.getIsCaste())
									{
										//update old course of caste seats
										updateCourseMapOnOldCourse(allotment, courseMap, oldCourseTO);

									}
									//check map contain student seat is gen or not and add genremain seat
									else if(allotment.getIsGeneral())
									{
										updateCourseMapOnOldCourseGeneral(courseMap, oldCourseTO);
									}

									allotment.setIsCaste(true);
									allotment.setIsGeneral(false); 
									// update--map details
									allotment.setPrefNo(currank.getPrefNo());
									allotment.setRank(currank.getRank());
									allotment.setIndexMark(currank.getIndexMark());
									allotment.setAllotmentNo(allotmentNo);
									allotment.setIsAssigned(false);
									allotment.setIsSatisfied(false);

									Course course=new Course();
									course.setId(courseTO.getId());
									allotment.setCourse(course);

									courseTO.setObxremain(courseTO.getObxremain()-1);
									courseTO.setObxcurrank( currank.getRank());

									//update allotment map
									allotmentMap.put(allotment.getAdmAppln().getId(), allotment);

									//update course map
									courseMap.put(courseTO.getId(), courseTO);

									System.out.println(allotment.getIsCaste()+" modified data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());

								}	

								if(allotment.getPrefNo().intValue()==currank.getPrefNo().intValue()){

									courseTO.setObxcurrank( currank.getRank());
									courseMap.put(courseTO.getId(), courseTO);
								}
							}
							//add new student if map does contains
							else
							{

								//get allotment object seat to store db
								StudentCourseAllotment allotment =getStudetCourseAllotmentBO(currank,true,allotmentNo);

								//genrankList.add(studentRank);
								allotmentMap.put(currank.getAdmAppln().getId(), allotment);

								// update-- course general details
								courseTO.setObxremain(courseTO.getObxremain()-1);
								courseTO.setObxcurrank( currank.getRank());

								//update course map
								courseMap.put(courseTO.getId(), courseTO);

								System.out.println("fresh data in caste id=="+currank.getAdmAppln().getId()+" pre=="+currank.getPrefNo()+" rank=="+currank.getRank());
							}	
						}// main if over 
					}	  
				}

			}
			//OBX Over







			// caste check over





		}// student rank loop close



		///after
		System.out.println("after gencurrent=="+courseTO.getGencurrank());
		System.out.println("after sccurrent=="+courseTO.getSccurrank());
		System.out.println("after stcurrent=="+courseTO.getStcurrank());
		System.out.println("after bccurrent=="+courseTO.getBccurrank());
		System.out.println("after lccurrent=="+courseTO.getLccurrank());
		System.out.println("after mucurrent=="+courseTO.getMucurrank());
		System.out.println("after obhcurrent=="+courseTO.getObhcurrank());
		System.out.println("after obxcurrent=="+courseTO.getObxcurrank());


		System.out.println("after genremain=="+courseTO.getGenremain());
		System.out.println("after scremain=="+courseTO.getScremain());
		System.out.println("after stremain=="+courseTO.getStremain());
		System.out.println("after bcremain=="+courseTO.getBcremain());
		System.out.println("after muremain=="+courseTO.getMuremain());
		System.out.println("after lcremain=="+courseTO.getLcremain());
		System.out.println("after obhremain=="+courseTO.getObhremain());
		System.out.println("after obxremain=="+courseTO.getObxremain());


		System.out.println("after higen=="+courseTO.getHighgenrank());
		System.out.println("after higsc=="+courseTO.getHighscrank());
		System.out.println("after hist=="+courseTO.getHighstrank());
		System.out.println("after higbc=="+courseTO.getHighbcrank());
		System.out.println("after himu=="+courseTO.getHighmurank());
		System.out.println("after higlc=="+courseTO.getHighlcrank());
		System.out.println("after higobh=="+courseTO.getHighobhrank());
		System.out.println("after higobx=="+courseTO.getHighobxrank());


		System.out.println("=========================="+ category+ " =============================");


		//checking all caste and gen over conditions

		//general over
		if (courseTO.getGencurrank() >= courseTO.getHighgenrank() || courseTO.getGenremain() == 0)
		{
			courseTO.setGenover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//sc over
		if(courseTO.getGencurrank() >= courseTO.getHighscrank() || courseTO.getScremain() == 0 || courseTO.getSccurrank() >= courseTO.getHighscrank())
		{
			courseTO.setScover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//st over
		if(courseTO.getGencurrank() >= courseTO.getHighstrank() || courseTO.getStremain() == 0 || courseTO.getStcurrank() >= courseTO.getHighstrank())
		{
			courseTO.setStover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//lc over
		if(courseTO.getGencurrank() >= courseTO.getHighlcrank() || courseTO.getLcremain() == 0 || courseTO.getLccurrank() >= courseTO.getHighlcrank())
		{
			courseTO.setLcover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//bc over
		if(courseTO.getGencurrank() >= courseTO.getHighbcrank() || courseTO.getBcremain() == 0 || courseTO.getBccurrank() >= courseTO.getHighbcrank())
		{
			courseTO.setBcover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//mu over
		if(courseTO.getGencurrank() >= courseTO.getHighmurank() || courseTO.getMuremain() == 0 || courseTO.getMucurrank() >= courseTO.getHighmurank())
		{
			courseTO.setMuover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//obh over
		if(courseTO.getGencurrank() >= courseTO.getHighobhrank() || courseTO.getObhremain() == 0 || courseTO.getObhcurrank() >= courseTO.getHighobhrank())
		{
			courseTO.setObhover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}

		//obx over
		if(courseTO.getGencurrank() >= courseTO.getHighobxrank() || courseTO.getObxremain() == 0 || courseTO.getObxcurrank() >= courseTO.getHighobxrank() )
		{
			courseTO.setObxover(true);
			//update course map
			courseMap.put(courseTO.getId(), courseTO);
		}




	}



} 