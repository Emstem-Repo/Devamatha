package com.kp.cms.utilities;


import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.DocChecklist;

import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Student;

import com.kp.cms.bo.admin.University;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.handlers.admin.OrganizationHandler;

import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;



public class CSVUpdater {
	private static final Log log = LogFactory.getLog(CSVUpdater.class);
	/**
	 * parse file content
	 * @param inStream
	 * @param academicYear
	 * @param courseID
	 * @param userID
	 * @return
	 */
	public static List<AdmAppln>  parseFile(InputStream inStream,int academicYear,Map<String,Integer> courseMap, String userID) throws Exception{
		List<AdmAppln> applications= new ArrayList<AdmAppln>();
		try {
//				InputStream inStream=new FileInputStream(filePath);
			LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(inStream));
			while(parser.getLine()!=null){
				float totalmarks=0;
				float marksObtained=0;
				AdmAppln applnBo=new AdmAppln();
				applnBo.setCreatedBy(userID);
				applnBo.setCreatedDate(new Date());
				
				applnBo.setChallanRefNo(parser.getValueByLabel("Challan_NO"));
				applnBo.setJournalNo(parser.getValueByLabel("journal_ No"));
				if(parser.getValueByLabel("Challan_date")!=null && CommonUtil.isValidDate(parser.getValueByLabel("Challan_date")))
				applnBo.setDate(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("Challan_date")));
				if(parser.getValueByLabel("Challan_ Amount")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Challan_ Amount")) && StringUtils.isNumeric(parser.getValueByLabel("Challan_ Amount")))
				applnBo.setAmount(new BigDecimal(parser.getValueByLabel("Challan_ Amount")));
				if(parser.getValueByLabel("Application_Number")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Application_Number")) && StringUtils.isNumeric(parser.getValueByLabel("Application_Number")))
				applnBo.setApplnNo(Integer.parseInt(parser.getValueByLabel("Application_Number")));
				if(parser.getValueByLabel("Academic_year")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Academic_year")) && StringUtils.isNumeric(parser.getValueByLabel("Academic_year")))
					applnBo.setAppliedYear(Integer.parseInt(parser.getValueByLabel("Academic_year")));
				else if(academicYear!=0)
					applnBo.setAppliedYear(academicYear);
				applnBo.setBankBranch(parser.getValueByLabel("Bank_BranchCode"));
				applnBo.setIsSelected(false);
				applnBo.setIsFinalMeritApproved(false);
				applnBo.setIsCancelled(false);
				applnBo.setIsBypassed(false);
				applnBo.setIsLig(false);
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
					if(tos!=null && !tos.isEmpty())
					{
						OrganizationTO orgTO=tos.get(0);
						applnBo.setIsApproved(!orgTO.isNeedApproval());
					}
					Course crs= new Course();
				if(parser.getValueByLabel("Course_Code")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Course_Code")) && courseMap.containsKey(parser.getValueByLabel("Course_Code"))){
				// id to be selected from ui.
				crs.setId(courseMap.get(parser.getValueByLabel("Course_Code")));
				applnBo.setCourse(crs);
				}
				applnBo.setCourseBySelectedCourseId(crs);
				
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
				//String firstname=null;
				//String lastname=null;
				/*if(parser.getValueByLabel("Name")!=null){
					int spaceindex=parser.getValueByLabel("Name").indexOf(" ");
					if(spaceindex!=-1){
					firstname=parser.getValueByLabel("Name").substring(0, spaceindex);
					lastname=parser.getValueByLabel("Name").substring(spaceindex+1,parser.getValueByLabel("Name").length());
					}else{
						firstname=parser.getValueByLabel("Name");
					}
				}*/
				personBo.setFirstName(parser.getValueByLabel("Name"));
				
				/*personBo.setFirstName(firstname);
				personBo.setLastName(lastname);*/
				
				if(parser.getValueByLabel("Date_of_Birth")!=null){
				if(CommonUtil.isValidDate(parser.getValueByLabel("Date_of_Birth")))
				personBo.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("Date_of_Birth")));
				}
				StringBuffer tempBirthDate =  new StringBuffer();
				if(parser.getValueByLabel("Day")!=null){
					String day = parser.getValueByLabel("Day").trim();
					if(day.length() == 1){
						day = "0" + day + "/";
					}
					else{
						day = day + "/";
					}
					tempBirthDate.append(day);
				}
				if(parser.getValueByLabel("Month")!=null){
					String month = parser.getValueByLabel("Month").trim();
					if(month.length() == 1){
						month = "0" + month + "/";
					}
					else{
						month = month + "/";
					}
					tempBirthDate.append(month);
				}
				
				if(parser.getValueByLabel("Year")!=null){
					String year = parser.getValueByLabel("Year").trim();
					tempBirthDate.append(year);
				}
				
				personBo.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(tempBirthDate.toString()));
				
				if(parser.getValueByLabel("Place_of_Birth") == null || parser.getValueByLabel("Place_of_Birth").trim().isEmpty() ){
					personBo.setBirthPlace("Other");
				}else{
					personBo.setBirthPlace(parser.getValueByLabel("Place_of_Birth"));
				}
				
				int birthcountry = 0;
				if(parser.getValueByLabel("Country_of_Birth")!= null && !parser.getValueByLabel("Country_of_Birth").trim().isEmpty()){
					birthcountry=getDataId(parser.getValueByLabel("Country_of_Birth"), "Country",true);
				}
				
				if(birthcountry>0){
					Country brtcnt= new Country();
					brtcnt.setId(birthcountry);
					personBo.setCountryByCountryId(brtcnt);
				}else{
					if(parser.getValueByLabel("Country_of_Birth") == null || parser.getValueByLabel("Country_of_Birth").trim().isEmpty()){
						personBo.setCountryOthers("Other");
					}
					else
					{
						personBo.setCountryOthers(parser.getValueByLabel("Country_of_Birth"));
					}
				}
				int birthstate= 0;
				if(parser.getValueByLabel("State_of_Birth")!= null && !parser.getValueByLabel("State_of_Birth").trim().isEmpty()){
					birthstate=getDataId(parser.getValueByLabel("State_of_Birth"), "State",true);
				}
				
				
				if(birthstate>0){
					State brtcnt= new State();
					brtcnt.setId(birthstate);
					personBo.setStateByStateId(brtcnt);
				}else{
					if(parser.getValueByLabel("State_of_Birth") == null || parser.getValueByLabel("State_of_Birth").trim().isEmpty()){
						personBo.setStateOthers("Other");
					}
					else
					{
						personBo.setStateOthers(parser.getValueByLabel("State_of_Birth"));
					}
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
				
				if(parser.getValueByLabel("Is_SportsPerson")!= null && !parser.getValueByLabel("Is_SportsPerson").trim().isEmpty()){
					personBo.setIsSportsPerson(Boolean.valueOf((parser.getValueByLabel("Is_SportsPerson"))));
				}
				else{
					personBo.setIsSportsPerson(false);
				}
				if(parser.getValueByLabel("Is_Handicapped")!= null && !parser.getValueByLabel("Is_Handicapped").trim().isEmpty()){
					personBo.setIsHandicapped(Boolean.valueOf((parser.getValueByLabel("Is_Handicapped"))));
				}
				else{
					personBo.setIsHandicapped(false);
				}
				
				
				personBo.setBloodGroup(parser.getValueByLabel("Blood_group"));
				if(parser.getValueByLabel("Area_type")!=null && parser.getValueByLabel("Area_type").equalsIgnoreCase("RURAL"))
				personBo.setRuralUrban('R');
				else if(parser.getValueByLabel("Area_type")!=null && parser.getValueByLabel("Area_type").equalsIgnoreCase("URBAN"))
					personBo.setRuralUrban('U');
				else if(parser.getValueByLabel("Area_type") == null || parser.getValueByLabel("Area_type").trim().isEmpty()){
					personBo.setRuralUrban('U');
				}
					
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
				if(parser.getValueByLabel("passport_validity")!=null){
					if(CommonUtil.isValidDate(parser.getValueByLabel("passport_validity")))
						personBo.setPassportValidity(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("passport_validity")));
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
				
				int permcountry= 0;
				if(parser.getValueByLabel("permanent_address_country")!= null && !parser.getValueByLabel("permanent_address_country").trim().isEmpty()){
					permcountry = getDataId(parser.getValueByLabel("permanent_address_country"), "Country",true);
				}
				if(permcountry>0){
					Country brtcnt= new Country();
					brtcnt.setId(permcountry);
					personBo.setCountryByPermanentAddressCountryId(brtcnt);
				}else{
					if(parser.getValueByLabel("permanent_address_country") == null || parser.getValueByLabel("permanent_address_country").trim().isEmpty()){
						personBo.setPermanentAddressCountryOthers("Other");
					}
					else
					{
						personBo.setPermanentAddressCountryOthers(parser.getValueByLabel("permanent_address_country"));
					}
				}
				
				personBo.setPermanentAddressZipCode(parser.getValueByLabel("permanent_address_pincode"));
				
				personBo.setCurrentAddressLine1(parser.getValueByLabel("current_Adderss1"));
				personBo.setCurrentAddressLine2(parser.getValueByLabel("current_ Adderss2"));
				personBo.setCityByCurrentAddressCityId(parser.getValueByLabel("current_address_city"));
				int currstate=getDataId(parser.getValueByLabel("current_address_state"), "State",true);
				if(currstate>0){
					State brtcnt= new State();
					brtcnt.setId(currstate);
					personBo.setStateByCurrentAddressStateId(brtcnt);
				}else{
					personBo.setCurrentAddressStateOthers(parser.getValueByLabel("current_address_state"));
				}
				
				int currcountry=0;
				if(parser.getValueByLabel("current_address_country")!= null && !parser.getValueByLabel("current_address_country").trim().isEmpty()){
					currcountry = getDataId(parser.getValueByLabel("current_address_country"), "Country",true);
				}
				if(currcountry>0){
					Country brtcnt= new Country();
					brtcnt.setId(currcountry);
					personBo.setCountryByCurrentAddressCountryId(brtcnt);
				}else{
					if(parser.getValueByLabel("current_address_country") == null || parser.getValueByLabel("current_address_country").trim().isEmpty()){
						personBo.setCurrentAddressCountryOthers("Other");
					}
					else{
						personBo.setCurrentAddressCountryOthers(parser.getValueByLabel("current_address_country"));
					}
				}
				
				personBo.setCurrentAddressZipCode(parser.getValueByLabel("current_address_pincode"));
				if(parser.getValueByLabel("Second_Language")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Second_Language")))
					personBo.setSecondLanguage(parser.getValueByLabel("Second_Language"));
				
				// edn qualification
				EdnQualification qual= new EdnQualification();
				qual.setCreatedBy(userID);
				qual.setCreatedDate(new Date());
				if(applnBo.getCourseBySelectedCourseId()!=null){
				DocChecklist checklist=getDocchecklist(parser.getValueByLabel("Exam_Name"),applnBo.getCourseBySelectedCourseId().getId(),applnBo.getAppliedYear());
				qual.setDocChecklist(checklist);
				}
				int university=getDataId(parser.getValueByLabel("University-board"), "University",true);
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
				if(parser.getValueByLabel("No_Of_attempts")!=null && !StringUtils.isEmpty(parser.getValueByLabel("No_Of_attempts")) && StringUtils.isNumeric(parser.getValueByLabel("No_Of_attempts"))){
					qual.setNoOfAttempts(Integer.parseInt(parser.getValueByLabel("No_Of_attempts")));
				}

				if(parser.getValueByLabel("Max_Marks")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Max_Marks")) && StringUtils.isNumeric(parser.getValueByLabel("Max_Marks"))){
					qual.setTotalMarks(new BigDecimal(parser.getValueByLabel("Max_Marks")));
					totalmarks=Float.parseFloat(parser.getValueByLabel("Max_Marks"));
				}

				if(parser.getValueByLabel("obtained_Marks")!=null && !StringUtils.isEmpty(parser.getValueByLabel("obtained_Marks")) && StringUtils.isNumeric(parser.getValueByLabel("obtained_Marks"))){
					qual.setMarksObtained(new BigDecimal(parser.getValueByLabel("obtained_Marks")));
					marksObtained=Float.parseFloat(parser.getValueByLabel("obtained_Marks"));
				}
				if(totalmarks>0 && marksObtained>0){
					float percentageMarks=(marksObtained/totalmarks)*100;
					qual.setPercentage(new BigDecimal(percentageMarks));
				}
				
				//set all objects to parent
				Set<EdnQualification> quals= new HashSet<EdnQualification>();
				quals.add(qual);
				personBo.setEdnQualifications(quals);
				// set parent data
				personBo.setFatherName(parser.getValueByLabel("Father_name"));
				personBo.setFatherEducation(parser.getValueByLabel("Father_Education"));
				personBo.setFatherEmail(parser.getValueByLabel("father_email"));
				int fOccupation=getDataId(parser.getValueByLabel("Father_Occupation"), "Occupation",true);
				if(fOccupation>0){
					Occupation brtcnt= new Occupation();
					brtcnt.setId(fOccupation);
					personBo.setOccupationByFatherOccupationId(brtcnt);
				}
				int fIncome=getIncomeId(parser.getValueByLabel("father_Income"), "Income",true);
				if(fIncome>0){
					Income brtcnt= new Income();
					brtcnt.setId(fIncome);
					
					personBo.setIncomeByFatherIncomeId(brtcnt);
				}
				int fCurr=getDataId(parser.getValueByLabel("Father_Income_ Currencytype"), "Currency",true);
				if(fCurr>0){
					Currency fcur= new Currency();
					fcur.setId(fCurr);
					personBo.setCurrencyByFatherIncomeCurrencyId(fcur);
				}
				
				personBo.setMotherName(parser.getValueByLabel("Mother_name"));
				personBo.setMotherEducation(parser.getValueByLabel("Mother_Education"));
				personBo.setMotherEmail(parser.getValueByLabel("Mother_email"));
				int mOccupation=getDataId(parser.getValueByLabel("mother_Occupation"), "Occupation",true);
				if(mOccupation>0){
					Occupation brtcnt= new Occupation();
					brtcnt.setId(mOccupation);
					personBo.setOccupationByMotherOccupationId(brtcnt);
				}
				int mIncome=getIncomeId(parser.getValueByLabel("Mother_Income"), "Income",true);
				if(mIncome>0){
					Income brtcnt= new Income();
					brtcnt.setId(mIncome);
					
					personBo.setIncomeByMotherIncomeId(brtcnt);
				}
				int mCurr=getDataId(parser.getValueByLabel("Mother_Income_Currencytype"), "Currency",true);
				if(mCurr>0){
					Currency fcur= new Currency();
					fcur.setId(mCurr);
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
				if(applnBo.getApplnNo()!=0 && applnBo.getAppliedYear()!=0 && applnBo.getCourse()!=null){
					if(applnBo.getCourse().getId()>0)
					applications.add(applnBo);
				}
			}
		} catch (IOException e) {
			log.error(e);
		}
		return applications;
	}
	/**
	 * @param data
	 * @param courseID
	 * @param year
	 * @return
	 */
	private static DocChecklist getDocchecklist(String data,int courseID,int year) {
		Session session = null;
		 DocChecklist doc=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 Query qry=session.createQuery("from DocChecklist d where d.course.id="+courseID+" and d.year="+year+" and upper(trim(d.docType.name))=upper('"+data.trim()+"') and d.isActive=1");
			doc=(DocChecklist)qry.uniqueResult();

			 session.flush();
			 session.close();
			 //sessionFactory.close();

		 }catch (Exception e) {
			 log.error(e);
		 }
		return doc;
	}

	/**
	 * persists all data
	 * @param applications
	 * @return
	 */
	public static boolean persistCompleteApplicationData(List<AdmAppln> applications,AdmissionFormForm admForm){
		boolean result= false;
		Session session = null;
		Transaction txn=null;
		IAdmissionFormTransaction admTxn= new AdmissionFormTransactionImpl();
		List<Integer> applnNos=new ArrayList<Integer>();
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			
			 if(applications!=null && !applications.isEmpty()){
				 Iterator<AdmAppln> appItr=applications.iterator();
				 while (appItr.hasNext()) {
					AdmAppln admAppln = (AdmAppln) appItr.next();
					int appno=admAppln.getApplnNo();
					int year=admAppln.getAppliedYear();
					Student newStudent= new Student();
					newStudent.setCreatedBy(admAppln.getCreatedBy());
					newStudent.setCreatedDate(new Date());
					newStudent.setAdmAppln(admAppln);
					newStudent.setIsAdmitted(false);
					newStudent.setIsActive(true);
					// duplicate not allowed
					if(admTxn.checkApplicationNoUniqueForYear(appno, year)){
						 //txn= session.beginTransaction();
						 //session.save(newStudent);
						 //txn.commit();
						try{
							applnNos=save(newStudent,applnNos,appno);
						}catch(Exception e){
							
						}
					}
					else
						continue;
				}
				 
				 admForm.setApplnNos(applnNos);
			 }
			
			 session.flush();
			 session.close();
			 //sessionFactory.close();
			 result=true;
		 }catch (ConstraintViolationException e) {
			 	txn.rollback();
				log.error(e);
				
		}catch (Exception e) {
			 txn.rollback();
			 log.error(e);
			
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
	public static int getDataId(String data,String boName, boolean isActive){
		int result= -1;
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
			 if(isActive){
				 if(boName.equalsIgnoreCase("University") && !CMSConstants.LINK_FOR_CJC)
					 query="select bo.id from "+boName+" bo where upper(bo.name)=upper('"+data+"') and bo.isActive=1 and bo.docType.id is not null";
				 else if(boName.equalsIgnoreCase("College")){
					 String[] str=data.split("_");
					 query="select bo.id from "+boName+" bo where upper(bo.name)=upper('"+str[0]+"') and bo.isActive=1 and bo.university.id="+str[1]; 
				 }
				 else
				 query="select bo.id from "+boName+" bo where upper(bo.name)=upper('"+data+"') and bo.isActive=1";
			 }
			 else{
				 if(boName.equalsIgnoreCase("University") && !CMSConstants.LINK_FOR_CJC)
					 query="select bo.id from "+boName+" bo where upper(bo.name)=upper('"+data+"') and bo.docType.id is not null";
				 else if(boName.equalsIgnoreCase("College")){
					 String[] str=data.split("_");
					 query="select bo.id from "+boName+" bo where upper(bo.name)=upper('"+str[0]+"') and bo.university.id="+str[1]; 
				 }
				 else
				 query="select bo.id from "+boName+" bo where upper(bo.name)=upper('"+data+"')";
			 }
			 Query qry= session.createQuery(query);
			 Integer obj=(Integer)qry.uniqueResult();
			 if(obj!=null && obj.intValue()>0)
				 result=obj.intValue();
			 session.flush();
			 session.close();
			 //sessionFactory.close();

		 }catch (Exception e) {
			log.error(e);
		 }
		return result;
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
	public static List<Integer>  save(Object object,List<Integer> applnNos,int appno){
		Session session = null;
		Transaction transaction = null;
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(object);
			transaction.commit();
			session.flush();
			session.close();
			return applnNos;
		} catch (Exception e) {
			if ( transaction != null){
				if( appno!=0)
				applnNos.add(appno);
				transaction.rollback();
			}
//			if (session != null){
//				session.flush();
//				session.close();
//			}
			return applnNos;
		}
	}
	public static List<AdmAppln> parseOMRData(InputStream inStream,AdmissionFormForm admForm,Map<String, Integer> courseMap){
		List<AdmAppln> applications= new ArrayList<AdmAppln>();
		try{
		   LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(inStream));
		   while(parser.getLine()!=null){
				float totalmarks=0;
				float marksObtained=0;
				AdmAppln applnBo=new AdmAppln();
				applnBo.setCreatedBy(admForm.getUserId());
				applnBo.setCreatedDate(new Date());
				if(parser.getValueByLabel("APPLN_NO")!=null && !StringUtils.isEmpty(parser.getValueByLabel("APPLN_NO")) && StringUtils.isNumeric(parser.getValueByLabel("APPLN_NO")))
				applnBo.setApplnNo(Integer.parseInt(parser.getValueByLabel("APPLN_NO")));
				//if(parser.getValueByLabel("Academic_year")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Academic_year")) && StringUtils.isNumeric(parser.getValueByLabel("Academic_year")))
					//applnBo.setAppliedYear(Integer.parseInt(parser.getValueByLabel("Academic_year")));
				applnBo.setIsSelected(false);
				applnBo.setIsFinalMeritApproved(false);
				applnBo.setIsCancelled(false);
				applnBo.setIsBypassed(false);
				applnBo.setIsLig(false);
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
					if(tos!=null && !tos.isEmpty())
					{
						OrganizationTO orgTO=tos.get(0);
						applnBo.setIsApproved(!orgTO.isNeedApproval());
					}
					Course crs= new Course();
				if(parser.getValueByLabel("COURSE_APPLIED")!=null && !StringUtils.isEmpty(parser.getValueByLabel("COURSE_APPLIED")) && courseMap.containsKey(parser.getValueByLabel("COURSE_APPLIED"))){
				// id to be selected from ui.
				crs.setId(courseMap.get(parser.getValueByLabel("COURSE_APPLIED")));
				applnBo.setCourse(crs);
				}
				applnBo.setCourseBySelectedCourseId(crs);
				Set<CandidatePreference> preferenceset = new HashSet<CandidatePreference>();
				CandidatePreference pref1= new CandidatePreference();
				pref1.setCourse(crs);
				pref1.setPrefNo(1);
				preferenceset.add(pref1);
				applnBo.setCandidatePreferences(preferenceset);
			//	int academicYear=CurrentAcademicYear.getInstance().getCurrentAcademicyear();
				applnBo.setAppliedYear(Integer.parseInt(admForm.getYear()));
				//personal data
				PersonalData personBo= new PersonalData();
				personBo.setCreatedBy(admForm.getUserId());
				personBo.setCreatedDate(new Date());
				personBo.setRuralUrban('U');
				//String firstname=null;
				//String lastname=null;
				/*if(parser.getValueByLabel("Name")!=null){
					int spaceindex=parser.getValueByLabel("Name").indexOf(" ");
					if(spaceindex!=-1){
					firstname=parser.getValueByLabel("Name").substring(0, spaceindex);
					lastname=parser.getValueByLabel("Name").substring(spaceindex+1,parser.getValueByLabel("Name").length());
					}else{
						firstname=parser.getValueByLabel("Name");
					}
				}*/
				personBo.setFirstName(parser.getValueByLabel("STUDENT_NAME"));
				
				/*personBo.setFirstName(firstname);
				personBo.setLastName(lastname);*/
				
				if(parser.getValueByLabel("Date_of_Birth")!=null){
				if(CommonUtil.isValidDate(parser.getValueByLabel("Date_of_Birth")))
				personBo.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(parser.getValueByLabel("Date_of_Birth")));
				}
				StringBuffer tempBirthDate =  new StringBuffer();
				if(parser.getValueByLabel("DATE")!=null){
					String day = parser.getValueByLabel("DATE").trim();
					if(day.length() == 1){
						day = "0" + day + "/";
					}
					else{
						day = day + "/";
					}
					tempBirthDate.append(day);
				}
				if(parser.getValueByLabel("MONTH")!=null){
					String month = parser.getValueByLabel("MONTH").trim();
					if(month.length() == 1){
						month = "0" + month + "/";
					}
					else{
						month = month + "/";
					}
					tempBirthDate.append(month);
				}
				
				if(parser.getValueByLabel("YEAR")!=null){
					String year = parser.getValueByLabel("YEAR").trim();
					tempBirthDate.append(year);
				}
				personBo.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(tempBirthDate.toString()));
				int nationality=getDataId(parser.getValueByLabel("NATIONALITY"), "Nationality",false);
				if(nationality>0){
					Nationality brtcnt= new Nationality();
					brtcnt.setId(nationality);
					personBo.setNationality(brtcnt);
				}else{
					personBo.setNationalityOthers(parser.getValueByLabel("NATIONALITY"));
				}
				if(parser.getValueByLabel("GENDER")!=null)
				personBo.setGender(parser.getValueByLabel("GENDER").toUpperCase());
				if(parser.getValueByLabel("BLOOD_GROUP")!=null && !parser.getValueByLabel("BLOOD_GROUP").isEmpty())
				personBo.setBloodGroup(parser.getValueByLabel("BLOOD_GROUP").toUpperCase());
				else personBo.setBloodGroup("NOT KNOWN");
				if (parser.getValueByLabel("CASTE/CATEGORY")!=null && !StringUtils.isEmpty(parser.getValueByLabel("CASTE/CATEGORY"))) {
					int caste = getDataId(parser.getValueByLabel("CASTE/CATEGORY"),"Caste",true);
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
				int religion=getDataId(parser.getValueByLabel("RELIGION"), "Religion",true);
				if(religion>0){
					Religion brtcnt= new Religion();
					brtcnt.setId(religion);
					personBo.setReligion(brtcnt);
				}else{
					personBo.setReligionOthers(parser.getValueByLabel("RELIGION"));
				}
				String mob=null;
				if(parser.getValueByLabel("MOBILE")!=null)
				{
					mob=parser.getValueByLabel("MOBILE");
				}
				personBo.setMobileNo2(mob);
				String address1=parser.getValueByLabel("FIRST_LINE_OF_ADDRESS");
				personBo.setPermanentAddressLine1(address1);
				// adding the same address to current address 
				personBo.setCurrentAddressLine1(address1);
				String address2=parser.getValueByLabel("SECOND_LINE_OF_ADDRESS");
				String address="";
				if(address2!=null && !address2.isEmpty()){
					address=address2+",";
				}
				String address3=parser.getValueByLabel("THIRD_LINE_OF_ADDRESS");
				if(address3!=null && !address3.isEmpty())
					address=address+address3;
				personBo.setPermanentAddressLine2(address);
				// adding the same address to current address 
				personBo.setCurrentAddressLine2(address);
				personBo.setCityByPermanentAddressCityId(parser.getValueByLabel("FOURTH_LINE_OF_ADDRESS"));
				personBo.setCityByCurrentAddressCityId(parser.getValueByLabel("FOURTH_LINE_OF_ADDRESS"));
				if(parser.getValueByLabel("FOURTH_LINE_OF_ADDRESS")!=null && parser.getValueByLabel("FOURTH_LINE_OF_ADDRESS").equalsIgnoreCase("BANGALORE")){
					State state=new State();
					state.setId(1);
					personBo.setStateByPermanentAddressStateId(state);
					personBo.setStateByCurrentAddressStateId(state);
				}
				else{
					personBo.setPermanentAddressStateOthers("Other");
					personBo.setCurrentAddressStateOthers("Other");
				}
				if(parser.getValueByLabel("FOURTH_LINE_OF_ADDRESS")!=null && parser.getValueByLabel("FOURTH_LINE_OF_ADDRESS").equalsIgnoreCase("BANGALORE")){
					Country country=new Country();
					country.setId(1);
					personBo.setCountryByCurrentAddressCountryId(country);
					personBo.setCountryByPermanentAddressCountryId(country);
				}
				else{
					personBo.setCurrentAddressCountryOthers("Other");
					personBo.setPermanentAddressCountryOthers("Other");
				}
				personBo.setPermanentAddressZipCode(parser.getValueByLabel("PIN_CODE"));
				personBo.setCurrentAddressZipCode(parser.getValueByLabel("PIN_CODE"));
			
				int residentCategoryId=getDataId(parser.getValueByLabel("DOMICILE/STATUS"), "ResidentCategory",true);
				if(residentCategoryId>0){
					ResidentCategory resident=new ResidentCategory();
					resident.setId(residentCategoryId);
					personBo.setResidentCategory(resident);
				}
				if(parser.getValueByLabel("SECOND_LANGUAGE")!=null && !StringUtils.isEmpty(parser.getValueByLabel("SECOND_LANGUAGE")))
					personBo.setSecondLanguage(parser.getValueByLabel("SECOND_LANGUAGE"));
				
				// edn qualification
				EdnQualification qual= new EdnQualification();
				qual.setCreatedBy(admForm.getUserId());
				qual.setCreatedDate(new Date());
				if(applnBo.getCourseBySelectedCourseId()!=null){
					DocChecklist checklist;
					if(admForm.isCjc())
					 checklist=getDocchecklist("Class 10",applnBo.getCourseBySelectedCourseId().getId(),applnBo.getAppliedYear());
					else  checklist=getDocchecklist("Class 12",applnBo.getCourseBySelectedCourseId().getId(),applnBo.getAppliedYear());
					qual.setDocChecklist(checklist);
					}
				int university=getDataId(parser.getValueByLabel("QUALIFYING_EXAM"), "University",true);
				if(university>0){
					University brtcnt= new University();
					brtcnt.setId(university);
					qual.setUniversity(brtcnt);
				}else{
					qual.setUniversityOthers(parser.getValueByLabel("QUALIFYING_EXAM"));
				}
				if(university>0){
					int institute=getDataId(parser.getValueByLabel("INSTITUTION_LAST_STUDIED")+"_"+university, "College",true);
					if(institute>0){
						College brtcnt= new College();
						brtcnt.setId(institute);
						qual.setCollege(brtcnt);
					}
					else{
						qual.setInstitutionNameOthers(parser.getValueByLabel("INSTITUTION_LAST_STUDIED"));
					}
				}
				else{
					qual.setInstitutionNameOthers(parser.getValueByLabel("INSTITUTION_LAST_STUDIED"));
				}
				if(parser.getValueByLabel("YEAR_OF_PASSING")!=null && !StringUtils.isEmpty(parser.getValueByLabel("YEAR_OF_PASSING")) && StringUtils.isNumeric(parser.getValueByLabel("YEAR_OF_PASSING"))){
				qual.setYearPassing(Integer.parseInt(parser.getValueByLabel("YEAR_OF_PASSING")));
				}
				if(parser.getValueByLabel("NUMBER_OF_ATTEMPTS")!=null && !StringUtils.isEmpty(parser.getValueByLabel("NUMBER_OF_ATTEMPTS")) && StringUtils.isNumeric(parser.getValueByLabel("NUMBER_OF_ATTEMPTS"))){
					qual.setNoOfAttempts(Integer.parseInt(parser.getValueByLabel("NUMBER_OF_ATTEMPTS")));
				}

				if(parser.getValueByLabel("MAXIMUM_MARKS")!=null && !StringUtils.isEmpty(parser.getValueByLabel("MAXIMUM_MARKS")) && StringUtils.isNumeric(parser.getValueByLabel("MAXIMUM_MARKS"))){
					qual.setTotalMarks(new BigDecimal(parser.getValueByLabel("MAXIMUM_MARKS")));
					totalmarks=Float.parseFloat(parser.getValueByLabel("MAXIMUM_MARKS"));
				}

				if(parser.getValueByLabel("MARKS_OBTAINED")!=null && !StringUtils.isEmpty(parser.getValueByLabel("MARKS_OBTAINED")) && StringUtils.isNumeric(parser.getValueByLabel("MARKS_OBTAINED"))){
					qual.setMarksObtained(new BigDecimal(parser.getValueByLabel("MARKS_OBTAINED")));
					marksObtained=Float.parseFloat(parser.getValueByLabel("MARKS_OBTAINED"));
				}
				if(totalmarks>0 && marksObtained>0){
					float percentageMarks=(marksObtained/totalmarks)*100;
					qual.setPercentage(new BigDecimal(percentageMarks));
				}
				
				//set all objects to parent
				Set<EdnQualification> quals= new HashSet<EdnQualification>();
				quals.add(qual);
				personBo.setEdnQualifications(quals);
				// set parent data
				applnBo.setPersonalData(personBo);
				if(applnBo.getApplnNo()!=0 && applnBo.getAppliedYear()!=0 && applnBo.getCourse()!=null){
					if(applnBo.getCourse().getId()>0)
					applications.add(applnBo);
				}
			}
		}catch(Exception exception){
			log.error(exception);
		}
		return applications;
	}
}