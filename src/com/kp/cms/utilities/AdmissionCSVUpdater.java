package com.kp.cms.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.University;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;

public class AdmissionCSVUpdater {

	
		/**
		 * parse file content
		 * @param inStream
		 * @param academicYear
		 * @param courseID
		 * @param userID
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static List<AdmAppln> parseFile(InputStream inStream,int academicYear,int courseID, String userID, int programId, int programTypeId) throws Exception{
			List<AdmAppln> applications= new ArrayList<AdmAppln>();
			try {
				LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(inStream));
				Session session = null;
				Integer generatedNo = null;
				String onlineFrom = null;
				String onlineTo = null;
				int onlineStart = 0;
				int onlineEnd = 0;
				Integer maxNo =null;
				int generatedInt = 0;
				SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = sessionFactory.openSession();
				Query query = session.createQuery("select c from CourseApplicationNumber a " +
						" join a.applicationNumber c " +
						" where a.applicationNumber.year= :year and a.course.id= :courseID " +
				" and a.isActive=1 and a.applicationNumber.isActive=1");
				query.setInteger("year", academicYear);
				query.setInteger("courseID", courseID);
				//query.setLockMode("c", LockMode.UPGRADE);
				ApplicationNumber appNo =(ApplicationNumber) query.uniqueResult();

				if(appNo!=null){
					if (appNo!= null) {
						onlineFrom = appNo.getOnlineApplnNoFrom();
						onlineTo = appNo.getOnlineApplnNoTo();
						onlineStart = Integer.parseInt(onlineFrom);
						onlineEnd = Integer.parseInt(onlineTo);
						maxNo = Integer.parseInt(appNo.getCurrentOnlineApplicationNo());
					}
					if (maxNo == null) {
						generatedNo = onlineStart;
						maxNo =onlineStart;
					} else if (onlineEnd == maxNo) {
						generatedNo = null;
						maxNo=null;
					} else 
						generatedInt = ++maxNo;

					boolean isExist=false;

					do{
						List<AdmAppln> bos=session.createQuery("from AdmAppln a where a.applnNo="+generatedInt+" and a.appliedYear="+academicYear).list();
						if(bos!=null && !bos.isEmpty()){
							isExist=true;
							generatedInt=generatedInt+1;
							maxNo=maxNo+1;
						}else{
							isExist=false;
						}
					}while(isExist);
				}
				generatedNo = generatedInt;
				while(parser.getLine()!=null){
					
					AdmAppln applnBo=new AdmAppln();
					Set<ApplicantSubjectGroup> applicantSubjectGroupSet = new HashSet<ApplicantSubjectGroup>();
					Set<Student> student = new HashSet<Student>();
					applnBo.setCreatedBy(userID);
					applnBo.setCreatedDate(new Date());
					
					applnBo.setChallanRefNo(parser.getValueByLabel("Challan_NO"));
					applnBo.setJournalNo(parser.getValueByLabel("Journal_No"));
					if(parser.getValueByLabel("Challan_date")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Challan_date"))){
						if(CommonUtil.validateddMMyyDate(parser.getValueByLabel("Challan_date"))){
						if(CommonUtil.isValidDate(CommonUtil.ConvertStringToddMMyyyyFormat(parser.getValueByLabel("Challan_date")))){
						applnBo.setDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToddMMyyyyFormat(parser.getValueByLabel("Challan_date"))));
						}
						}else if(CommonUtil.validateddMMyyyyDate(parser.getValueByLabel("Challan_date"))){
							if(CommonUtil.isValidDate(parser.getValueByLabel("Challan_date"))){
							applnBo.setDate(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("Challan_date")));
							}
						}
					}
					if(parser.getValueByLabel("Challan_Amount")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Challan_Amount")) && StringUtils.isNumeric(parser.getValueByLabel("Challan_Amount"))){
						applnBo.setAmount(new BigDecimal(parser.getValueByLabel("Challan_Amount")));
					}
					/**if(parser.getValueByLabel("Application_Number")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Application_Number")) && StringUtils.isNumeric(parser.getValueByLabel("Application_Number"))){
						applnBo.setApplnNo(Integer.parseInt(parser.getValueByLabel("Application_Number")));
					}**/
					
					if(generatedNo != null && generatedNo > 0)
						applnBo.setApplnNo(generatedNo++);
					if(academicYear!=0){
						applnBo.setAppliedYear(academicYear);	
					}	
					int exlSheetAcademicYear = 0; 
					if(parser.getValueByLabel("Academic_year")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Academic_year")) && StringUtils.isNumeric(parser.getValueByLabel("Academic_year"))){
						exlSheetAcademicYear = Integer.parseInt(parser.getValueByLabel("Academic_year"));
					}
					applnBo.setBankBranch(parser.getValueByLabel("Bank_BranchCode"));
					applnBo.setIsSelected(true);
					applnBo.setIsCancelled(false);
					applnBo.setIsBypassed(true);
					applnBo.setIsApproved(true);
					applnBo.setIsFreeShip(Boolean.valueOf(parser.getValueByLabel("Is_Freeship")));
					
					if(parser.getValueByLabel("Admission_date") !=null && !StringUtils.isEmpty(parser.getValueByLabel("Admission_date") )){
						
						if(CommonUtil.validateddMMyyDate(parser.getValueByLabel("Admission_date"))){
							if(CommonUtil.isValidDate(CommonUtil.ConvertStringToddMMyyyyFormat(parser.getValueByLabel("Admission_date"))))
								applnBo.setAdmissionDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToddMMyyyyFormat(parser.getValueByLabel("Admission_date"))));
						}else if(CommonUtil.validateddMMyyyyDate(parser.getValueByLabel("Admission_date"))){
							if(CommonUtil.isValidDate(parser.getValueByLabel("Admission_date")))
								applnBo.setAdmissionDate(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("Admission_date")));
						}
						
						
					}
					if(parser.getValueByLabel("Subject_group1")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Subject_group1"))){
						int subjectGroup1Id = getSubjectGroupId(parser.getValueByLabel("Subject_group1").trim(), courseID, "SubjectGroup",true);
						if(subjectGroup1Id>0){
							ApplicantSubjectGroup applicantSubjectGroup = new ApplicantSubjectGroup();
							SubjectGroup group = new SubjectGroup();
							group.setId(subjectGroup1Id);
							applicantSubjectGroup.setSubjectGroup(group);
							applicantSubjectGroupSet.add(applicantSubjectGroup);
							
							applnBo.setApplicantSubjectGroups(applicantSubjectGroupSet);
						}
					}
					if(parser.getValueByLabel("Subject_group2")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Subject_group2"))){
						int subjectGroup1Id = getSubjectGroupId(parser.getValueByLabel("Subject_group2").trim(), courseID, "SubjectGroup",true);
						if(subjectGroup1Id>0){
							ApplicantSubjectGroup applicantSubjectGroup = new ApplicantSubjectGroup();
							SubjectGroup group = new SubjectGroup();
							group.setId(subjectGroup1Id);
							applicantSubjectGroup.setSubjectGroup(group);
							applicantSubjectGroupSet.add(applicantSubjectGroup);
							
							applnBo.setApplicantSubjectGroups(applicantSubjectGroupSet);
						}
					}
					if(parser.getValueByLabel("Subject_group3")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Subject_group3"))){
						int subjectGroup1Id = getSubjectGroupId(parser.getValueByLabel("Subject_group3").trim(), courseID, "SubjectGroup",true);
						if(subjectGroup1Id>0){
							ApplicantSubjectGroup applicantSubjectGroup = new ApplicantSubjectGroup();
							SubjectGroup group = new SubjectGroup();
							group.setId(subjectGroup1Id);
							applicantSubjectGroup.setSubjectGroup(group);
							applicantSubjectGroupSet.add(applicantSubjectGroup);
							
							applnBo.setApplicantSubjectGroups(applicantSubjectGroupSet);
						}
					}
					if(parser.getValueByLabel("Subject_group4")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Subject_group4"))){
						int subjectGroup1Id = getSubjectGroupId(parser.getValueByLabel("Subject_group4").trim(), courseID, "SubjectGroup",true);
						if(subjectGroup1Id>0){
							ApplicantSubjectGroup applicantSubjectGroup = new ApplicantSubjectGroup();
							SubjectGroup group = new SubjectGroup();
							group.setId(subjectGroup1Id);
							applicantSubjectGroup.setSubjectGroup(group);
							applicantSubjectGroupSet.add(applicantSubjectGroup);
							
							applnBo.setApplicantSubjectGroups(applicantSubjectGroupSet);
						}
					}
					if(parser.getValueByLabel("Subject_group5")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Subject_group5"))){
						int subjectGroup1Id = getSubjectGroupId(parser.getValueByLabel("Subject_group5").trim(), courseID, "SubjectGroup",true);
						if(subjectGroup1Id>0){
							ApplicantSubjectGroup applicantSubjectGroup = new ApplicantSubjectGroup();
							SubjectGroup group = new SubjectGroup();
							group.setId(subjectGroup1Id);
							applicantSubjectGroup.setSubjectGroup(group);
							applicantSubjectGroupSet.add(applicantSubjectGroup);
							
							applnBo.setApplicantSubjectGroups(applicantSubjectGroupSet);
						}
					}
					if(parser.getValueByLabel("admitted_through")!=null && !StringUtils.isEmpty(parser.getValueByLabel("admitted_through"))){
						int admittedThroughId = getDataId(parser.getValueByLabel("admitted_through").trim(), "AdmittedThrough",true);
						if(admittedThroughId>0){
							AdmittedThrough admittedThrough = new AdmittedThrough();
							admittedThrough.setId(admittedThroughId);
							applnBo.setAdmittedThrough(admittedThrough);
						}
					}
					
					ProgramType programType = new ProgramType();
					programType.setId(programTypeId);
						
					Program program = new Program();
					program.setId(programId);
					program.setProgramType(programType);	
					
					Course crs= new Course();
					// id to be selected from ui.
					crs.setId(courseID);
					crs.setProgram(program);
					applnBo.setCourse(crs);
					
					applnBo.setCourseBySelectedCourseId(crs);
					applnBo.setAdmittedCourseId(crs);
					Set<CandidatePreference> preferenceset = new HashSet<CandidatePreference>();
					CandidatePreference pref1= new CandidatePreference();
					pref1.setCourse(crs);
					pref1.setPrefNo(1);
					preferenceset.add(pref1);
					applnBo.setCandidatePreferences(preferenceset);
					
					//personal data
					PersonalData personBo= new PersonalData();
					personBo.setCreatedBy(userID);
					personBo.setCreatedDate(new Date());
					String firstname=null;
					String lastname=null;
					if(parser.getValueByLabel("Name")!=null){
//						int spaceindex=parser.getValueByLabel("Name").indexOf(" ");
//						if(spaceindex!=-1){
//						firstname=parser.getValueByLabel("Name").substring(0, spaceindex);
//						lastname=parser.getValueByLabel("Name").substring(spaceindex+1,parser.getValueByLabel("Name").length());
//						}else{
							firstname=parser.getValueByLabel("Name");
//						}
					}
					personBo.setFirstName(firstname);
					personBo.setLastName(lastname);
					if(parser.getValueByLabel("Second_Language") != null && !StringUtils.isEmpty(parser.getValueByLabel("Second_Language"))){
						personBo.setSecondLanguage(parser.getValueByLabel("Second_Language"));
					}
					if(parser.getValueByLabel("Date_of_Birth")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Date_of_Birth") )){  
					if(CommonUtil.validateddMMyyDate(parser.getValueByLabel("Date_of_Birth"))){
						
						if(CommonUtil.isValidDate(CommonUtil.ConvertStringToddMMyyyyFormat(parser.getValueByLabel("Date_of_Birth"))))
					personBo.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToddMMyyyyFormat(parser.getValueByLabel("Date_of_Birth"))));
					}else if(CommonUtil.validateddMMyyyyDate(parser.getValueByLabel("Date_of_Birth"))){
						if(CommonUtil.isValidDate(parser.getValueByLabel("Date_of_Birth")))
							personBo.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("Date_of_Birth")));
					}
					
					}
					personBo.setBirthPlace(parser.getValueByLabel("Place_of_Birth"));
					int birthcountry=getDataId(parser.getValueByLabel("Country_of_Birth"), "Country",true);
					if(birthcountry>0){
						Country brtcnt= new Country();
						brtcnt.setId(birthcountry);
						personBo.setCountryByCountryId(brtcnt);
					}else{
						personBo.setCountryOthers(parser.getValueByLabel("Country_of_Birth"));
					}
					int birthstate=getDataId(parser.getValueByLabel("State_of_Birth"), "State",true);
					if(birthstate>0){
						State brtcnt= new State();
						brtcnt.setId(birthstate);
						personBo.setStateByStateId(brtcnt);
					}else{
						personBo.setStateOthers(parser.getValueByLabel("State_of_Birth"));
					}
					int nationality=getDataId(parser.getValueByLabel("Nationality"), "Nationality",false);
					if(nationality>0){
						Nationality brtcnt= new Nationality();
						brtcnt.setId(nationality);
						personBo.setNationality(brtcnt);
					}else{
						personBo.setNationalityOthers(parser.getValueByLabel("Nationality"));
					}
					if(parser.getValueByLabel("Gender")!=null)
					personBo.setGender(parser.getValueByLabel("Gender").toUpperCase());
					personBo.setIsSportsPerson(Boolean.valueOf(parser.getValueByLabel("Is_SportsPerson")));
					personBo.setIsHandicapped(Boolean.valueOf(parser.getValueByLabel("Is_Handicapped")));
					personBo.setBloodGroup(parser.getValueByLabel("Blood_group"));
					if(parser.getValueByLabel("Area_type")!=null && parser.getValueByLabel("Area_type").equalsIgnoreCase("RURAL"))
					personBo.setRuralUrban('R');
					else if(parser.getValueByLabel("Area_type")!=null && parser.getValueByLabel("Area_type").equalsIgnoreCase("URBAN"))
						personBo.setRuralUrban('U');
					
					int rescat=getDataId(parser.getValueByLabel("resident_category"), "ResidentCategory",true);
					if(rescat>0){
						ResidentCategory brtcnt= new ResidentCategory();
						brtcnt.setId(rescat);
						personBo.setResidentCategory(brtcnt);
					}
					
					int religion=getDataId(parser.getValueByLabel("Religion"), "Religion",true);
					if(religion>0){
						Religion brtcnt= new Religion();
						brtcnt.setId(religion);
						personBo.setReligion(brtcnt);
					}else{
						personBo.setReligionOthers(parser.getValueByLabel("Religion"));
					}
					int subreligion=getDataId(parser.getValueByLabel("Sub-religion"), "ReligionSection",false);
					if(subreligion>0){
						ReligionSection brtcnt= new ReligionSection();
						brtcnt.setId(subreligion);
						personBo.setReligionSection(brtcnt);
					}else{
						personBo.setReligionSectionOthers(parser.getValueByLabel("Sub-religion"));
					}
					if (parser.getValueByLabel("Caste")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Caste"))) {
						int caste = getDataId(parser.getValueByLabel("Caste"),"Caste",true);
						if (caste > 0) {
							Caste brtcnt = new Caste();
							brtcnt.setId(caste);
							personBo.setCaste(brtcnt);
						} else {
							personBo.setCasteOthers(parser.getValueByLabel("Caste"));
						}
					}else{
						personBo.setCaste(null);
					}
					String ph1=null;
					String ph2=null;
					String ph3=null;
					if(parser.getValueByLabel("PhoneCountryCode")!=null)
					{
						ph1=parser.getValueByLabel("PhoneCountryCode");						
					}
					if(parser.getValueByLabel("PhoneAreaCode")!=null)
					{
						ph2=parser.getValueByLabel("PhoneAreaCode");						
					}
					if(parser.getValueByLabel("Phone")!=null)
					{
						ph3=parser.getValueByLabel("Phone");						
					}
					personBo.setPhNo1(ph1);
					personBo.setPhNo2(ph2);
					personBo.setPhNo3(ph3);
					String mob1=null;
					String mob2=null;
					String mob3=null;
					if(parser.getValueByLabel("MobileCountryCode")!=null)
					{
						mob1=parser.getValueByLabel("MobileCountryCode");
					}
					if(parser.getValueByLabel("Mobile")!=null)
					{
						mob2=parser.getValueByLabel("Mobile");
					}
					personBo.setMobileNo1(mob1);
					personBo.setMobileNo2(mob2);
					personBo.setMobileNo3(mob3);
					
					personBo.setEmail(parser.getValueByLabel("email"));
					personBo.setPassportNo(parser.getValueByLabel("passportno"));
					if(parser.getValueByLabel("passport_validity")!=null && !StringUtils.isEmpty(parser.getValueByLabel("passport_validity") )){
						if(CommonUtil.validateddMMyyDate(parser.getValueByLabel("passport_validity"))){
						if(CommonUtil.isValidDate(CommonUtil.ConvertStringToddMMyyyyFormat(parser.getValueByLabel("passport_validity"))))
							personBo.setPassportValidity(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToddMMyyyyFormat(parser.getValueByLabel("passport_validity"))));
						}else if(CommonUtil.validateddMMyyyyDate(parser.getValueByLabel("passport_validity"))){
							if(CommonUtil.isValidDate(parser.getValueByLabel("passport_validity")))
								personBo.setPassportValidity(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("passport_validity")));
							}
					}
					int passportcountry=getDataId(parser.getValueByLabel("passport_country"), "Country",true);
					if(passportcountry>0){
						Country brtcnt= new Country();
						brtcnt.setId(passportcountry);
						personBo.setCountryByPassportCountryId(brtcnt);
					}
					
					personBo.setPermanentAddressLine1(parser.getValueByLabel("permanent_Adderss1"));
					personBo.setPermanentAddressLine2(parser.getValueByLabel("permanent_Adderss2"));
					personBo.setCityByPermanentAddressCityId(parser.getValueByLabel("Permanent_address_city"));
					int permstate=getDataId(parser.getValueByLabel("permanent_address_state"), "State",true);
					if(permstate>0){
						State brtcnt= new State();
						brtcnt.setId(permstate);
						personBo.setStateByPermanentAddressStateId(brtcnt);
					}else{
						personBo.setPermanentAddressStateOthers(parser.getValueByLabel("permanent_address_state"));
					}
					int permcountry=getDataId(parser.getValueByLabel("permanent_address_country"), "Country",true);
					if(permcountry>0){
						Country brtcnt= new Country();
						brtcnt.setId(permcountry);
						personBo.setCountryByPermanentAddressCountryId(brtcnt);
					}else{
						personBo.setPermanentAddressCountryOthers(parser.getValueByLabel("permanent_address_country"));
					}
					
					personBo.setPermanentAddressZipCode(parser.getValueByLabel("permanent_address_pincode"));
					
					personBo.setCurrentAddressLine1(parser.getValueByLabel("current_Adderss1"));
					personBo.setCurrentAddressLine2(parser.getValueByLabel("current_Adderss2"));
					personBo.setCityByCurrentAddressCityId(parser.getValueByLabel("current_address_city"));
					int currstate=getDataId(parser.getValueByLabel("current_address_state"), "State",true);
					if(currstate>0){
						State brtcnt= new State();
						brtcnt.setId(currstate);
						personBo.setStateByCurrentAddressStateId(brtcnt);
					}else{
						personBo.setCurrentAddressStateOthers(parser.getValueByLabel("current_address_state"));
					}
					int currcountry=getDataId(parser.getValueByLabel("current_address_country"), "Country",true);
					if(currcountry>0){
						Country brtcnt= new Country();
						brtcnt.setId(currcountry);
						personBo.setCountryByCurrentAddressCountryId(brtcnt);
					}else{
						personBo.setCurrentAddressCountryOthers(parser.getValueByLabel("current_address_country"));
					}
					
					personBo.setCurrentAddressZipCode(parser.getValueByLabel("current_address_pincode"));
					
					
					// edn qualification
					EdnQualification qual= new EdnQualification();
					qual.setCreatedBy(userID);
					qual.setCreatedDate(new Date());
					DocChecklist checklist=getDocchecklist(parser.getValueByLabel("Exam_Name"),applnBo.getCourseBySelectedCourseId().getId(),applnBo.getAppliedYear());
					qual.setDocChecklist(checklist);
					//added by smitha - passing doc type name also along with university for getting the university id
					String docTypeName=parser.getValueByLabel("Exam_Name");
					int university=getDataId(parser.getValueByLabel("University-board")+"#"+docTypeName, "University",true);
					if(university>0){
						University brtcnt= new University();
						brtcnt.setId(university);
						qual.setUniversity(brtcnt);
					}else{
						qual.setUniversityOthers(parser.getValueByLabel("University-board"));
					}
					int institute=getDataId(parser.getValueByLabel("Institute_Name"), "College",true);
					if(institute>0){
						College brtcnt= new College();
						brtcnt.setId(institute);
						qual.setCollege(brtcnt);
					}else{
						qual.setInstitutionNameOthers(parser.getValueByLabel("Institute_Name"));
					}
					if(parser.getValueByLabel("Year_Of_passing")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Year_Of_passing")) && StringUtils.isNumeric(parser.getValueByLabel("Year_Of_passing"))){
					qual.setYearPassing(Integer.parseInt(parser.getValueByLabel("Year_Of_passing")));
					}
					
					if(parser.getValueByLabel("month_of_passing")!=null && !StringUtils.isEmpty(parser.getValueByLabel("month_of_passing"))&& StringUtils.isNumeric(parser.getValueByLabel("month_of_passing"))){
						qual.setMonthPassing(Integer.parseInt(parser.getValueByLabel("month_of_passing")));
					}
					
					if(parser.getValueByLabel("No_Of_attempts")!=null && !StringUtils.isEmpty(parser.getValueByLabel("No_Of_attempts")) && StringUtils.isNumeric(parser.getValueByLabel("No_Of_attempts"))){
						qual.setNoOfAttempts(Integer.parseInt(parser.getValueByLabel("No_Of_attempts")));
					}

					if(parser.getValueByLabel("Max_Marks")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Max_Marks")) && StringUtils.isNumeric(parser.getValueByLabel("Max_Marks"))){
						qual.setTotalMarks(new BigDecimal(parser.getValueByLabel("Max_Marks")));
					}

					if(parser.getValueByLabel("obtained_Marks")!=null && !StringUtils.isEmpty(parser.getValueByLabel("obtained_Marks")) && StringUtils.isNumeric(parser.getValueByLabel("obtained_Marks"))){
						qual.setMarksObtained(new BigDecimal(parser.getValueByLabel("obtained_Marks")));
					}
					
					//set all objects to parent
					Set<EdnQualification> quals= new HashSet<EdnQualification>();
					quals.add(qual);
					personBo.setEdnQualifications(quals);
					// set parent data
					personBo.setFatherName(parser.getValueByLabel("Father_name"));
					personBo.setFatherEducation(parser.getValueByLabel("Father_Education"));
					personBo.setFatherEmail(parser.getValueByLabel("father_email"));
					int f_occupation=getDataId(parser.getValueByLabel("Father_Occupation"), "Occupation",true);
					if(f_occupation>0){
						Occupation brtcnt= new Occupation();
						brtcnt.setId(f_occupation);
						personBo.setOccupationByFatherOccupationId(brtcnt);
					}
					int f_income=getIncomeId(parser.getValueByLabel("father_Income"), "Income",true);
					if(f_income>0){
						Income brtcnt= new Income();
						brtcnt.setId(f_income);
						
						personBo.setIncomeByFatherIncomeId(brtcnt);
					}
					if(parser.getValueByLabel("father_Income")!=null && !StringUtils.isEmpty(parser.getValueByLabel("father_Income"))){
						personBo.setFamilyIncome(parser.getValueByLabel("father_Income"));
					}
					int f_curr=getDataId(parser.getValueByLabel("Father_Income_Currencytype"), "Currency",true);
					if(f_curr>0){
						Currency fcur= new Currency();
						fcur.setId(f_curr);
						personBo.setCurrencyByFatherIncomeCurrencyId(fcur);
					}
					
					personBo.setMotherName(parser.getValueByLabel("Mother_name"));
					personBo.setMotherEducation(parser.getValueByLabel("Mother_Education"));
					personBo.setMotherEmail(parser.getValueByLabel("Mother_email"));
					int m_occupation=getDataId(parser.getValueByLabel("mother_Occupation"), "Occupation",true);
					if(m_occupation>0){
						Occupation brtcnt= new Occupation();
						brtcnt.setId(m_occupation);
						personBo.setOccupationByMotherOccupationId(brtcnt);
					}
					int m_income=getIncomeId(parser.getValueByLabel("Mother_Income"), "Income",true);
					if(m_income>0){
						Income brtcnt= new Income();
						brtcnt.setId(m_income);
						
						personBo.setIncomeByMotherIncomeId(brtcnt);
					}
					int m_curr=getDataId(parser.getValueByLabel("Mother_Income_Currencytype"), "Currency",true);
					if(m_curr>0){
						Currency fcur= new Currency();
						fcur.setId(m_curr);
						personBo.setCurrencyByMotherIncomeCurrencyId(fcur);
					}
					personBo.setParentAddressLine1(parser.getValueByLabel("parent_Adderss1"));
					personBo.setParentAddressLine2(parser.getValueByLabel("parent_Adderss2"));
					personBo.setParentAddressLine3(parser.getValueByLabel("parent_Adderss3"));
					personBo.setCityByParentAddressCityId(parser.getValueByLabel("parent_address_city"));
					int parestate=getDataId(parser.getValueByLabel("Parent_address_state"), "State",true);
					if(parestate>0){
						State brtcnt= new State();
						brtcnt.setId(parestate);
						personBo.setStateByParentAddressStateId(brtcnt);
					}else{
						personBo.setParentAddressStateOthers(parser.getValueByLabel("Parent_address_state"));
					}
					int parecountry=getDataId(parser.getValueByLabel("parent_address_country"), "Country",true);
					if(parecountry>0){
						Country brtcnt= new Country();
						brtcnt.setId(parecountry);
						personBo.setCountryByParentAddressCountryId(brtcnt);
					}else{
						personBo.setParentAddressCountryOthers(parser.getValueByLabel("parent_address_country"));
					}
					
					personBo.setParentAddressZipCode(parser.getValueByLabel("parent_address_pincode"));
					ph1=null;
					ph2=null;
					ph3=null;
					if(parser.getValueByLabel("Parent_PhoneCountryCode")!=null)
					{
						ph1=parser.getValueByLabel("Parent_PhoneCountryCode");
					}
					if(parser.getValueByLabel("Parent_PhoneAreaCode")!=null)
					{
						ph2=parser.getValueByLabel("Parent_PhoneAreaCode");
					}
					if(parser.getValueByLabel("Parent_Phone")!=null)
					{
						ph3=parser.getValueByLabel("Parent_Phone");
					}
					personBo.setParentPh1(ph1);
					personBo.setParentPh2(ph2);
					personBo.setParentPh3(ph3);
					mob1=null;
					mob2=null;
					mob3=null;
					if(parser.getValueByLabel("parent_MobileCountryCode")!=null)
					{
						mob1=parser.getValueByLabel("parent_MobileCountryCode");
					}
					if(parser.getValueByLabel("parent_Mobile")!=null)
					{
						mob2=parser.getValueByLabel("parent_Mobile");
					}
					personBo.setParentMob1(mob1);
					personBo.setParentMob2(mob2);
					personBo.setParentMob3(mob3);
					applnBo.setPersonalData(personBo);
					
					Student newStudent= new Student();
					
					if(parser.getValueByLabel("Registration_Number")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Registration_Number"))){
						newStudent.setRegisterNo(parser.getValueByLabel("Registration_Number"));
					}
					if(parser.getValueByLabel("Roll_No")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Roll_No"))){
						newStudent.setRollNo(parser.getValueByLabel("Roll_No"));
					}
					
					int classSchemwiseId=getclassSchemwiseId(parser.getValueByLabel("Class_Name"),exlSheetAcademicYear/*academicYear*/);
					if(classSchemwiseId>0){
						ClassSchemewise classSchemewise = new ClassSchemewise();
						classSchemewise.setId(classSchemwiseId);
						newStudent.setClassSchemewise(classSchemewise);
					}else{
						newStudent.setClassSchemewise(null);
					}
					
					student.add(newStudent);
					applnBo.setStudents(student);
					
					applications.add(applnBo);
				}
			} catch (IOException e) {
				throw e;
			}
			return applications;
		}
		/**
		 * @param data
		 * @param courseID
		 * @param year
		 * @return
		 */
		public static DocChecklist getDocchecklist(String data,int courseID,int year)throws Exception {
			Session session = null;
			 DocChecklist doc=null;
			try {
				 SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session =sessionFactory.openSession();
				 Query qr=session.createQuery("from DocChecklist d where d.course.id = :courseId and d.year = :academicYear and upper(d.docType.name)=upper(:Data) and d.isActive=1");
				 qr.setString("Data", data);
				 qr.setInteger("courseId", courseID);
				 qr.setInteger("academicYear", year);
				 
				 doc=(DocChecklist)qr.uniqueResult();
			 }catch (Exception e) {
				 throw e;
			 }finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			return doc;
		}

		/**
		 * persists all data
		 * @param applications
		 * @return
		 */
		public static Map<String, List<String>> persistCompleteApplicationData(List<AdmAppln> applications)throws Exception{
			Session session = null;
			Transaction txn=null;
			IAdmissionFormTransaction admTxn= new AdmissionFormTransactionImpl();
			Map<String, List<String>> duplicateNumberMap = new HashMap<String, List<String>>();
			List<String> duplicateAppNumberList = new ArrayList<String>();
			List<String> duplicateRegNumberList = new ArrayList<String>();
			List<String> duplicateRollNumberList = new ArrayList<String>();
			
			try {
				 SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session =sessionFactory.openSession();
				
				 if(applications!=null && !applications.isEmpty()){
					 
					 Iterator<AdmAppln> appItr=applications.iterator();
					 
					 while (appItr.hasNext()) {
						AdmAppln admAppln = (AdmAppln) appItr.next();
						int appno=admAppln.getApplnNo();
						int year=admAppln.getAppliedYear();
						int classSchemewiseId = 0;
						
						List<Integer> registerNoList = getRegisterNoList(admAppln.getAppliedYear());
						
						Iterator<Student> it = admAppln.getStudents().iterator();
						
						while (it.hasNext()){
							Student student = (Student) it.next();
							if(student.getClassSchemewise() != null)
							classSchemewiseId = student.getClassSchemewise().getId();
						} 
						
						List<Integer> rollNoList = getRollNoList(admAppln.getAppliedYear(), classSchemewiseId);
						
						Student newStudent = new Student();
						
						if (admAppln.getStudents() != null && !admAppln.getStudents().isEmpty()) {
							Iterator<Student> studentItr = admAppln.getStudents().iterator();
							while (studentItr.hasNext()) {
								Student student = (Student) studentItr.next();
								if(!registerNoList.contains(student.getRegisterNo()) && !rollNoList.contains(student.getRollNo())){
									
									newStudent.setCreatedBy(admAppln.getCreatedBy());
									newStudent.setCreatedDate(new Date());
									newStudent.setAdmAppln(admAppln);
									newStudent.setIsAdmitted(true);
									newStudent.setIsCurrent(true);
									newStudent.setIsActive(true);
									
									if( admAppln != null && admAppln.getCourseBySelectedCourseId()!=null && admAppln.getCourseBySelectedCourseId().getProgram() != null && admAppln.getCourseBySelectedCourseId().getProgram().getProgramType()!=null){
										int maxNo = getProgramTypeSlNo(admAppln.getCourseBySelectedCourseId().getProgram().getProgramType().getId(), admAppln.getAppliedYear());
										newStudent.setProgramTypeSlNo(maxNo);
									}
									
									newStudent.setRegisterNo(student.getRegisterNo());
									newStudent.setRollNo(student.getRollNo());
									newStudent.setClassSchemewise(student.getClassSchemewise());
								}else{
									if(registerNoList.contains(student.getRegisterNo())){
										duplicateRegNumberList.add(student.getRegisterNo());
									}
									if(rollNoList.contains(student.getRollNo())){
										duplicateRollNumberList.add(student.getRollNo());
									}
									newStudent = null;
								}
							}
						}	
						
						// duplicate not allowed
						if(admTxn.checkApplicationNoUniqueForYear(appno, year) && newStudent != null){
							 txn= session.beginTransaction();
							 session.save(newStudent);
							 txn.commit();
						}else if(!admTxn.checkApplicationNoUniqueForYear(appno, year)){
							duplicateAppNumberList.add(String.valueOf(appno));
							continue;
						}	
					}
					 if(!duplicateAppNumberList.isEmpty()){
						 duplicateNumberMap.put(CMSConstants.ADMISSIONFORM_APPLICATION_NUMBER, duplicateAppNumberList); 
					 }
					 if(!duplicateRegNumberList.isEmpty()){
						 duplicateNumberMap.put(CMSConstants.ADMISSIONFORM_REGISTER_NUMBER, duplicateRegNumberList);
					 }
					 if(!duplicateRollNumberList.isEmpty()){
						 duplicateNumberMap.put(CMSConstants.ADMISSIONFORM_ROLL_NUMBER, duplicateRollNumberList);
					 }
				 }
			 }catch (ConstraintViolationException e) {
				txn.rollback();
			}catch (Exception e) {
				 txn.rollback();
			 }finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			return duplicateNumberMap;
		}

	/**
	 * get classschemewise id wrt classname and academic year
	 * @param className
	 * @param academicYear
	 * @return
	 * @throws Exception
	 */
		public static int getclassSchemwiseId(String className, int academicYear)throws Exception{
			int result= -1;
			Session session = null;
			try {
				 SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session =sessionFactory.openSession();
				 String query="";
				 
				 query="select classSchemewise.id from ClassSchemewise classSchemewise where upper(classSchemewise.classes.name)=upper('"+className+"') and classSchemewise.curriculumSchemeDuration.academicYear = "+academicYear+"";
				 
				 Query qr= session.createQuery(query);
				 Integer obj=(Integer)qr.uniqueResult();
				 if(obj != null && obj.intValue()>0)
					 result=obj.intValue();
			 }catch (Exception e) {
				 throw e;
			 }finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			return result;
		}
		
		/**
		 * get ID for CSV Matching data
		 * @param data
		 * @param boName
		 * @param isActive
		 * @return
		 */
		public static int getDataId(String data, String boName, boolean isActive)throws Exception{
			int result= -1;
			Session session = null;
			try {
				 SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session =sessionFactory.openSession();
				 String query="";
				 if(boName.equalsIgnoreCase("University")){
					 String[] str=data.split("#");
					 query="select bo.id from University bo where upper(bo.name)=upper(:Data)";
					 if(isActive){
							 query=query+" and bo.isActive=1";
					 }
					 if(str.length>1 && str[1]!=null && !str[1].trim().isEmpty()){
						query=query+ " and bo.docType.name='"+str[1]+"'";
						data=str[0];
					 }
					 else {
						 data="";
					 }
				 }
				 else {
					 if(isActive)
						 query="select bo.id from "+boName+" bo where upper(bo.name)=upper(:Data) and bo.isActive=1";
					 else{
						 query="select bo.id from "+boName+" bo where upper(bo.name)=upper(:Data)";
					 }
				 }
				 Query qr = session.createQuery(query);
				 qr.setString("Data", data);
				 
				 Integer obj=(Integer)qr.uniqueResult();
				 
				 if(obj!=null && obj.intValue()>0){
					 result=obj.intValue();
				 } 
			 }catch (Exception e) {
				 throw e; 
			 }finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			return result;
		}

		/**
		 * get ID for CSV Matching data
		 * @param data
		 * @param boName
		 * @param isActive
		 * @return
		 */
		public static int getSubjectGroupId(String data, int courseID, String boName, boolean isActive)throws Exception{
			int result= -1;
			Session session = null;
			try {
				 SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session =sessionFactory.openSession();
				 String query="";
				 if(isActive)
					 query="select bo.id from "+boName+" bo where upper(bo.name)=upper(:Data) and bo.course.id = :courseId and bo.isActive=1";
				 else{
					 query="select bo.id from "+boName+" bo where upper(bo.name)=upper(:Data) and bo.course.id = :courseId";
				 }
				 Query qr= session.createQuery(query);
				 qr.setString("Data", data);
				 qr.setInteger("courseId", courseID);
				 
				 Integer obj=(Integer)qr.uniqueResult();
				 
				 if(obj!=null && obj.intValue()>0){
					 result=obj.intValue();
				 } 
			 }catch (Exception e) {
				throw e; 
			 }finally{
				if (session != null) {
					session.flush();
					session.close();
				}
			 }
			return result;
		}
		
		/**
		 * get ID for CSV Matching data
		 * @param data
		 * @param boName
		 * @param isActive
		 * @return
		 */
		public static int getProgramTypeSlNo(int programTypeId, Integer appliedYear)throws Exception{
			Integer maxNo = 1;
			Session session = null;
			try {
				 SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session =sessionFactory.openSession();
				 
				 Query	query = session.createQuery("select max(student.programTypeSlNo)" +
				 		" from Student student " +
				 		" where student.admAppln.courseBySelectedCourseId.program.programType.id = :programTypeId " +
				 		" and student.admAppln.appliedYear = :year");
				 query.setInteger("programTypeId", programTypeId); 
				 query.setInteger("year", appliedYear.intValue());
				 if(query.uniqueResult()!= null && !query.uniqueResult().equals(null)){
					 maxNo = (Integer)query.uniqueResult();
					 maxNo = maxNo +1;
				 }
			 }catch (Exception e) {
				 throw e;
			 }finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			return maxNo;
		}

		/**
		 * gets income ID for CSV Matching DATA
		 * @param data
		 * @return
		 */
		public static int getIncomeId(String data, String boName, boolean isActive)throws Exception{
			int result= -1;
			Session session = null;
			try {
				 SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session =sessionFactory.openSession();
				 String query="";
				 if(isActive)
					 query="select bo.id from "+boName+" bo where upper(bo.incomeRange)=upper(:Data) and bo.isActive=1";
				 else{
					 query="select bo.id from "+boName+" bo where upper(bo.incomeRange)=upper(:Data)";
				 }
				 Query qr = session.createQuery(query);
				 qr.setString("Data", data);
				 
				 Integer obj=(Integer)qr.uniqueResult();
				 
				 if(obj!=null && obj.intValue()>0){
					 result=obj.intValue();
				 } 
			 }catch (Exception e) {
				 throw e; 
			 }finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			return result;
		}

		/**
		 * get all the register nos for an applied year
		 * @param appliedYear
		 * @return
		 * @throws Exception
		 */
		public static List<Integer> getRegisterNoList(int appliedYear)throws Exception{
			List<Integer> registerNoList;
			Session session = null;
			try {
				 SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session =sessionFactory.openSession();
				 Query query = session.createQuery(" select student.registerNo " +
				 		" from Student student " +
				 		" where student.admAppln.appliedYear = :appliedYear " +
				 		" and student.registerNo <> null " +
				 		" and student.registerNo <> ''");
				 query.setInteger("appliedYear", appliedYear);
				 registerNoList = query.list();
			 }catch (Exception e) {
				 throw e;
			 }finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			return registerNoList;
		}
		
		/**
		 * get all the roll nos for a class
		 * @param appliedYear
		 * @param classSchemewiseId
		 * @return
		 * @throws Exception
		 */
		public static List<Integer> getRollNoList(int appliedYear, int classSchemewiseId)throws Exception{
			List<Integer> rollNoList;
			Session session = null;
			try {
				 SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session =sessionFactory.openSession();
				 Query query = session.createQuery(" select student.rollNo " +
				 		" from Student student " +
				 		" where student.admAppln.appliedYear = :appliedYear " +
				 		" and student.classSchemewise.id = :classSchemewiseId " +
				 		" and student.rollNo <> null " +
				 		" and student.rollNo <> '' ");
				 query.setInteger("appliedYear", appliedYear);
				 query.setInteger("classSchemewiseId", classSchemewiseId);
				 rollNoList = query.list();
			 }catch (Exception e) {
				 throw e;
			 }finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			return rollNoList;
		}
	}