package com.kp.cms.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import com.kp.cms.bo.admin.StudentQualifyexamDetail;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.University;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.admission.PersonalDataTO;

public class AdmissionDataReUploadCSVUpdater {
	/**
	 * get ID for CSV Matching data
	 * @param data
	 * @param boName
	 * @param isActive
	 * @return
	 */
	public static Map<Integer,Student> getStudentByCourse(int courseId,int appliedYear)throws Exception{
		Map<Integer,Student> finalMap=new HashMap<Integer, Student>();
		Session session = null;
		try {
			 session =HibernateUtil.getSession();
			 Query	query = session.createQuery("select s.admAppln.applnNo,s from Student s where s.isActive=1  " +
			 		"and s.admAppln.courseBySelectedCourseId.id=:courseId " +
			 		"and s.admAppln.appliedYear=:year");
			 query.setInteger("courseId", courseId); 
			 query.setInteger("year", appliedYear);
			 List<Object[]> stuList= query.list();
			 if(stuList!=null && !stuList.isEmpty()){
				 Iterator<Object[]> iterator=stuList.iterator();
				 while (iterator.hasNext()) {
					 Object[] student = (Object[]) iterator.next();
					 if(student[0]!=null && student[1]!=null)
					finalMap.put(Integer.parseInt(student[0].toString()), (Student)student[1]);
				}
			 }
			 
		 }catch (Exception e) {
			 throw e;
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return finalMap;
	}
	/**
	 * parse file content
	 * @param inStream
	 * @param academicYear
	 * @param courseID
	 * @param userID
	 * @return
	 */
	public static List<Student> parseCSVFile(Map<Integer,Student> stuMap,InputStream inStream,int academicYear,int courseID, String userID, int programId, int programTypeId) throws Exception{
		List<Student> stuList= new ArrayList<Student>();
		try {
			LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(inStream));
			while(parser.getLine()!=null){
				int applno=0;
				if(parser.getValueByLabel("Application_Number")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Application_Number")) && StringUtils.isNumeric(parser.getValueByLabel("Application_Number"))){
					applno=Integer.parseInt(parser.getValueByLabel("Application_Number"));
				}
				if(applno>0){
					Student student=stuMap.get(applno);
					String regNo="";
					if(parser.getValueByLabel("Registration_Number")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Registration_Number"))){
						regNo=parser.getValueByLabel("Registration_Number");
					}
					if(student!=null && student.getRegisterNo().equals(regNo)){
						AdmAppln applnBo=student.getAdmAppln();
						//int temp = applnBo.getApplnNo();
						applnBo.setCreatedBy(userID);
						applnBo.setCreatedDate(new Date());
						
						applnBo.setChallanRefNo(parser.getValueByLabel("Challan_NO"));
						applnBo.setJournalNo(parser.getValueByLabel("Journal_No"));
						if(parser.getValueByLabel("Challan_date")!=null && CommonUtil.isValidDate(parser.getValueByLabel("Challan_date"))){
							applnBo.setDate(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("Challan_date")));
						}
						if(parser.getValueByLabel("Challan_Amount")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Challan_Amount")) && StringUtils.isNumeric(parser.getValueByLabel("Challan_Amount"))){
							applnBo.setAmount(new BigDecimal(parser.getValueByLabel("Challan_Amount")));
						}
						if(parser.getValueByLabel("Application_Number")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Application_Number")) && StringUtils.isNumeric(parser.getValueByLabel("Application_Number"))){
							applnBo.setApplnNo(Integer.parseInt(parser.getValueByLabel("Application_Number")));
						}
						if(academicYear!=0){
							applnBo.setAppliedYear(academicYear);	
						}	
						applnBo.setBankBranch(parser.getValueByLabel("Bank_BranchCode"));
						applnBo.setIsSelected(true);
						applnBo.setIsCancelled(false);
						applnBo.setIsBypassed(true);
						applnBo.setIsApproved(true);
						applnBo.setIsFreeShip(Boolean.valueOf(parser.getValueByLabel("Is_Freeship")));
						if(parser.getValueByLabel("Admission_date") !=null && !StringUtils.isEmpty("Admission_date")){
							if(CommonUtil.isValidDate(parser.getValueByLabel("Admission_date")))
								applnBo.setAdmissionDate(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("Admission_date")));
						}
						if(parser.getValueByLabel("admitted_through")!=null && !StringUtils.isEmpty(parser.getValueByLabel("admitted_through"))){
							int admittedThroughId = AdmissionCSVUpdater.getDataId(parser.getValueByLabel("admitted_through").trim(), "AdmittedThrough",true);
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
						Set<StudentQualifyexamDetail> detailSet = applnBo.getStudentQualifyexamDetails();
						Set<StudentQualifyexamDetail> finalSet=new HashSet<StudentQualifyexamDetail>();
					
						if(detailSet!=null && !detailSet.isEmpty()){
							Iterator<StudentQualifyexamDetail> itr=detailSet.iterator();
							if (itr.hasNext()) {
								StudentQualifyexamDetail studentQualifyexamDetail = (StudentQualifyexamDetail) itr.next();
								if(parser.getValueByLabel("Second_Language") != null && !StringUtils.isEmpty(parser.getValueByLabel("Second_Language"))){
									studentQualifyexamDetail.setSecondLanguage(parser.getValueByLabel("Second_Language"));
									finalSet.add(studentQualifyexamDetail);
								}
								
							}
						}
						if(!finalSet.isEmpty()){
							applnBo.setStudentQualifyexamDetails(finalSet);
						}
						Set<CandidatePreference> preferenceset = applnBo.getCandidatePreferences();
						Set<CandidatePreference> finalCPSet=new HashSet<CandidatePreference>();
						if(preferenceset!=null && !preferenceset.isEmpty()){
							Iterator<CandidatePreference> itr=preferenceset.iterator();
							if (itr.hasNext()) {
								CandidatePreference candidatePreference = (CandidatePreference) itr.next();
								candidatePreference.setCourse(crs);
								candidatePreference.setPrefNo(1);
								finalCPSet.add(candidatePreference);
							}
						}
						applnBo.setCandidatePreferences(finalCPSet);
						//personal data
						PersonalData personBo= applnBo.getPersonalData();
						personBo.setCreatedBy(userID);
						personBo.setCreatedDate(new Date());
						String firstname=null;
						String lastname=null;
						if(parser.getValueByLabel("Name")!=null){
								firstname=parser.getValueByLabel("Name");
						}
						personBo.setFirstName(firstname);
						personBo.setLastName(lastname);
						
						if(parser.getValueByLabel("Date_of_Birth")!=null){
						if(CommonUtil.isValidDate(parser.getValueByLabel("Date_of_Birth")))
						personBo.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("Date_of_Birth")));
						}
						personBo.setBirthPlace(parser.getValueByLabel("Place_of_Birth"));
						int birthcountry=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("Country_of_Birth"), "Country",true);
						if(birthcountry>0){
							Country brtcnt= new Country();
							brtcnt.setId(birthcountry);
							personBo.setCountryByCountryId(brtcnt);
						}else{
							personBo.setCountryOthers(parser.getValueByLabel("Country_of_Birth"));
						}
						int birthstate=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("State_of_Birth"), "State",true);
						if(birthstate>0){
							State brtcnt= new State();
							brtcnt.setId(birthstate);
							personBo.setStateByStateId(brtcnt);
						}else{
							personBo.setStateOthers(parser.getValueByLabel("State_of_Birth"));
						}
						int nationality=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("Nationality"), "Nationality",false);
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
						
						int rescat=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("resident_category"), "ResidentCategory",true);
						if(rescat>0){
							ResidentCategory brtcnt= new ResidentCategory();
							brtcnt.setId(rescat);
							personBo.setResidentCategory(brtcnt);
						}
						
						int religion=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("Religion"), "Religion",true);
						if(religion>0){
							Religion brtcnt= new Religion();
							brtcnt.setId(religion);
							personBo.setReligion(brtcnt);
						}else{
							personBo.setReligionOthers(parser.getValueByLabel("Religion"));
						}
						int subreligion=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("Sub-religion"), "ReligionSection",false);
						if(subreligion>0){
							ReligionSection brtcnt= new ReligionSection();
							brtcnt.setId(subreligion);
							personBo.setReligionSection(brtcnt);
						}else{
							personBo.setReligionSectionOthers(parser.getValueByLabel("Sub-religion"));
						}
						if (parser.getValueByLabel("Caste")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Caste"))) {
							int caste = AdmissionCSVUpdater.getDataId(parser.getValueByLabel("Caste"),"Caste",true);
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
						if(parser.getValueByLabel("passport_validity")!=null){
							if(CommonUtil.isValidDate(parser.getValueByLabel("passport_validity")))
								personBo.setPassportValidity(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("passport_validity")));
						}
						int passportcountry=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("passport_country"), "Country",true);
						if(passportcountry>0){
							Country brtcnt= new Country();
							brtcnt.setId(passportcountry);
							personBo.setCountryByPassportCountryId(brtcnt);
						}
						
						personBo.setPermanentAddressLine1(parser.getValueByLabel("permanent_Adderss1"));
						personBo.setPermanentAddressLine2(parser.getValueByLabel("permanent_Adderss2"));
						personBo.setCityByPermanentAddressCityId(parser.getValueByLabel("Permanent_address_city"));
						int permstate=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("permanent_address_state"), "State",true);
						if(permstate>0){
							State brtcnt= new State();
							brtcnt.setId(permstate);
							personBo.setStateByPermanentAddressStateId(brtcnt);
						}else{
							personBo.setPermanentAddressStateOthers(parser.getValueByLabel("permanent_address_state"));
						}
						int permcountry=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("permanent_address_country"), "Country",true);
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
						int currstate=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("current_address_state"), "State",true);
						if(currstate>0){
							State brtcnt= new State();
							brtcnt.setId(currstate);
							personBo.setStateByCurrentAddressStateId(brtcnt);
						}else{
							personBo.setCurrentAddressStateOthers(parser.getValueByLabel("current_address_state"));
						}
						int currcountry=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("current_address_country"), "Country",true);
						if(currcountry>0){
							Country brtcnt= new Country();
							brtcnt.setId(currcountry);
							personBo.setCountryByCurrentAddressCountryId(brtcnt);
						}else{
							personBo.setCurrentAddressCountryOthers(parser.getValueByLabel("current_address_country"));
						}
						
						personBo.setCurrentAddressZipCode(parser.getValueByLabel("current_address_pincode"));
						// set parent data
						personBo.setFatherName(parser.getValueByLabel("Father_name"));
						personBo.setFatherEducation(parser.getValueByLabel("Father_Education"));
						personBo.setFatherEmail(parser.getValueByLabel("father_email"));
						int f_occupation=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("Father_Occupation"), "Occupation",true);
						if(f_occupation>0){
							Occupation brtcnt= new Occupation();
							brtcnt.setId(f_occupation);
							personBo.setOccupationByFatherOccupationId(brtcnt);
						}
						int f_income=AdmissionCSVUpdater.getIncomeId(parser.getValueByLabel("father_Income"), "Income",true);
						if(f_income>0){
							Income brtcnt= new Income();
							brtcnt.setId(f_income);
							
							personBo.setIncomeByFatherIncomeId(brtcnt);
						}
						int f_curr=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("Father_Income_Currencytype"), "Currency",true);
						if(f_curr>0){
							Currency fcur= new Currency();
							fcur.setId(f_curr);
							personBo.setCurrencyByFatherIncomeCurrencyId(fcur);
						}
						
						personBo.setMotherName(parser.getValueByLabel("Mother_name"));
						personBo.setMotherEducation(parser.getValueByLabel("Mother_Education"));
						personBo.setMotherEmail(parser.getValueByLabel("Mother_email"));
						int m_occupation=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("mother_Occupation"), "Occupation",true);
						if(m_occupation>0){
							Occupation brtcnt= new Occupation();
							brtcnt.setId(m_occupation);
							personBo.setOccupationByMotherOccupationId(brtcnt);
						}
						int m_income=AdmissionCSVUpdater.getIncomeId(parser.getValueByLabel("Mother_Income"), "Income",true);
						if(m_income>0){
							Income brtcnt= new Income();
							brtcnt.setId(m_income);
							
							personBo.setIncomeByMotherIncomeId(brtcnt);
						}
						int m_curr=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("Mother_Income_Currencytype"), "Currency",true);
						if(m_curr>0){
							Currency fcur= new Currency();
							fcur.setId(m_curr);
							personBo.setCurrencyByMotherIncomeCurrencyId(fcur);
						}
						personBo.setParentAddressLine1(parser.getValueByLabel("parent_Adderss1"));
						personBo.setParentAddressLine2(parser.getValueByLabel("parent_Adderss2"));
						personBo.setParentAddressLine3(parser.getValueByLabel("parent_Adderss3"));
						personBo.setCityByParentAddressCityId(parser.getValueByLabel("parent_address_city"));
						int parestate=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("Parent_address_state"), "State",true);
						if(parestate>0){
							State brtcnt= new State();
							brtcnt.setId(parestate);
							personBo.setStateByParentAddressStateId(brtcnt);
						}else{
							personBo.setParentAddressStateOthers(parser.getValueByLabel("Parent_address_state"));
						}
						int parecountry=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("parent_address_country"), "Country",true);
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
						
						
						if(parser.getValueByLabel("Registration_Number")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Registration_Number"))){
							student.setRegisterNo(parser.getValueByLabel("Registration_Number"));
						}
						if(parser.getValueByLabel("Roll_No")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Roll_No"))){
							student.setRollNo(parser.getValueByLabel("Roll_No"));
						}
						int exlSheetAcademicYear = 0; 
						if(parser.getValueByLabel("Academic_year")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Academic_year")) && StringUtils.isNumeric(parser.getValueByLabel("Academic_year"))){
							exlSheetAcademicYear = Integer.parseInt(parser.getValueByLabel("Academic_year"));
						}
						int classSchemwiseId=AdmissionCSVUpdater.getclassSchemwiseId(parser.getValueByLabel("Class_Name"),exlSheetAcademicYear/*academicYear*/);
						if(classSchemwiseId>0){
							ClassSchemewise classSchemewise = new ClassSchemewise();
							classSchemewise.setId(classSchemwiseId);
							student.setClassSchemewise(classSchemewise);
						}else{
							student.setClassSchemewise(null);
						}
						//set all objects to parent
						// edn qualification
						
						
						
						Set<EdnQualification> quals= personBo.getEdnQualifications();
						Set<EdnQualification> finalquals=new HashSet<EdnQualification>();
						if(quals!=null && !quals.isEmpty()){
							Iterator<EdnQualification> itr=quals.iterator();
							if (itr.hasNext()) {
								EdnQualification qual = (EdnQualification) itr.next();
								qual.setCreatedBy(userID);
								qual.setCreatedDate(new Date());
								DocChecklist checklist=AdmissionCSVUpdater.getDocchecklist(parser.getValueByLabel("Exam_Name"),applnBo.getCourseBySelectedCourseId().getId(),applnBo.getAppliedYear());
								qual.setDocChecklist(checklist);
								String docTypeName=parser.getValueByLabel("Exam_Name");
								int university=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("University-board")+"#"+docTypeName, "University",true);
								if(university>0){
									University brtcnt= new University();
									brtcnt.setId(university);
									qual.setUniversity(brtcnt);
								}else{
									qual.setUniversityOthers(parser.getValueByLabel("University-board"));
								}
								int institute=AdmissionCSVUpdater.getDataId(parser.getValueByLabel("Institute_Name"), "College",true);
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
								finalquals.add(qual);
							}
						}
						personBo.setEdnQualifications(quals);
						applnBo.setPersonalData(personBo);
						student.setAdmAppln(applnBo);
						stuList.add(student);
					}
				}
			}
		} catch (IOException e) {
			throw e;
		}
		return stuList;
	}
	/**
	 * @param results
	 * @return
	 * @throws Exception
	 */
	public static boolean updatePersonalData(List<Student> results) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<Student> tcIterator = results.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				Student  student = tcIterator.next();
				session.merge(student);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
}
}
