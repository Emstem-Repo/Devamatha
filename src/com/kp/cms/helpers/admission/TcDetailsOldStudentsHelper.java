package com.kp.cms.helpers.admission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Category;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.admission.TcDetailsOldStudents;
import com.kp.cms.forms.admission.TcDetailsOldStudentsForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CategoryTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.to.admission.TcDetailsOldStudentsTo;
import com.kp.cms.transactionsimpl.admission.TCDetailsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.NumberToWordConvertor;

public class TcDetailsOldStudentsHelper {
	
	private static Map<String, String> monthMap = null;
	static {
		monthMap = new HashMap<String, String>();
		monthMap.put("JAN", "JANUARY");
		monthMap.put("FEB", "FEBRUARY");
		monthMap.put("MAR", "MARCH");
		monthMap.put("APR", "APRIL");
		monthMap.put("MAY", "MAY");
		monthMap.put("JUN", "JUNE");
		monthMap.put("JUL", "JULY");
		monthMap.put("AUG", "AUGUST");
		monthMap.put("SEPT", "SEPTEMBER");
		monthMap.put("OCT", "OCTOBER");
		monthMap.put("NOV", "NOVEMBER");
		monthMap.put("DEC", "DECEMBER");
		
	}
	
	private static TcDetailsOldStudentsHelper tcDetailsOldStudentsHelper=new TcDetailsOldStudentsHelper();
	private TcDetailsOldStudentsHelper(){
		
	}
	
	/**
	 * @return
	 */
	public static TcDetailsOldStudentsHelper getInstance(){
		return tcDetailsOldStudentsHelper;
	}
	
	/**
	 * @param oldStudetntsListByYear
	 * @param tcDetailsOldStudetnsForm
	 * @throws Exception
	 */
	public void convertTcDetailsOldStudentsBoToTo(List<TcDetailsOldStudents> oldStudetntsListByYear, TcDetailsOldStudentsForm tcDetailsOldStudetnsForm)throws Exception { 
		// TODO Auto-generated method stub
		List<TcDetailsOldStudentsTo> tcDetailsOldStudentsTosList=new ArrayList<TcDetailsOldStudentsTo>();
		if(oldStudetntsListByYear!=null){
			Iterator< TcDetailsOldStudents> tcDetailsOldStudentsIterator=oldStudetntsListByYear.iterator();
			while(tcDetailsOldStudentsIterator.hasNext()){
				TcDetailsOldStudentsTo tcDetailsOldStudentsTo=new TcDetailsOldStudentsTo();
				TcDetailsOldStudents tcDetailsOldStudents=tcDetailsOldStudentsIterator.next();
				if(tcDetailsOldStudents.getTcNo()!=null && !tcDetailsOldStudents.getTcNo().isEmpty()){
					tcDetailsOldStudentsTo.setTcNo(tcDetailsOldStudents.getTcNo());
				}
				
				if(tcDetailsOldStudents.getRegisterNo()!=null){
					tcDetailsOldStudentsTo.setRegisterNo(tcDetailsOldStudents.getRegisterNo());
				}
				
				if(tcDetailsOldStudents.getName()!=null){
					tcDetailsOldStudentsTo.setName(tcDetailsOldStudents.getName());
				}
				
				if(tcDetailsOldStudents.getId()!=0){
					tcDetailsOldStudentsTo.setId(tcDetailsOldStudents.getId()+"");
				}
				
				
				if(tcDetailsOldStudents.getTcFor()!=null){
					tcDetailsOldStudentsTo.setTcFor(tcDetailsOldStudents.getTcFor());
				}
				
				tcDetailsOldStudentsTosList.add(tcDetailsOldStudentsTo);
			}
			tcDetailsOldStudetnsForm.setTcDetailsOldStudentsToList(tcDetailsOldStudentsTosList);
		}
	}	
	
	/**
	 * @param characterAndConductList
	 * @return
	 * @throws Exception
	 */
	public List<CharacterAndConductTO> convertCharacterAndConductBotoTo(List<CharacterAndConduct> characterAndConductList)throws Exception {
		// TODO Auto-generated method stub
		List<CharacterAndConductTO> characterAndConductToList=new ArrayList<CharacterAndConductTO>();
		if(characterAndConductList!=null){
			Iterator<CharacterAndConduct> characterAndConductIterator=characterAndConductList.iterator();
			while(characterAndConductIterator.hasNext()){
				CharacterAndConduct characterAndConduct=characterAndConductIterator.next();
				CharacterAndConductTO characterAndConductTO=new CharacterAndConductTO();
				if(characterAndConduct.getId()!=0){
					characterAndConductTO.setId(characterAndConduct.getId());
				}
				if(characterAndConduct.getName()!=null && !characterAndConduct.getName().isEmpty()){
					characterAndConductTO.setName(characterAndConduct.getName());
				}
				characterAndConductToList.add(characterAndConductTO);
			}
		}
		return characterAndConductToList;
	}
	
	/**
	 * @param categoryList
	 * @return
	 * @throws Exception
	 */
	public List<CasteTO> convertCategoryBotoTo(List<Caste> categoryList)throws Exception {
		// TODO Auto-generated method stub
		List<CasteTO> categoryToList=new ArrayList<CasteTO>();
		if(categoryList!=null){
			Iterator<Caste> categoryIterator=categoryList.iterator();
			while(categoryIterator.hasNext()){
				CasteTO categoryTO=new CasteTO();
				Caste category=categoryIterator.next();
				if(category.getId()!=0){
					categoryTO.setCasteId(category.getId());
				}
				if(category.getName()!=null && !category.getName().isEmpty()){
					categoryTO.setCasteName(category.getName());
				}
				categoryToList.add(categoryTO);
			}
		}
		return categoryToList;
	}
	
	
	/**
	 * @param religionList
	 * @return
	 * @throws Exception
	 */
	public List<ReligionTO> convertReligionBoToTo(List<Religion> religionList)throws Exception {
		// TODO Auto-generated method stub
		List<ReligionTO> religionTOList=new ArrayList<ReligionTO>();
		if(religionList!=null){
			Iterator<Religion> religionIterator=religionList.iterator();
			while(religionIterator.hasNext()){
				ReligionTO religionTO=new ReligionTO();
				Religion religion=religionIterator.next();
				if(religion.getId()!=0){
					religionTO.setReligionId(religion.getId());
				}
				if(religion.getName()!=null && !religion.getName().isEmpty()){
					religionTO.setReligionName(religion.getName());
				}
				religionTOList.add(religionTO);
			}
		}
		return religionTOList;
	}
	
	
	/**
	 * @param nationalityList
	 * @return
	 * @throws Exception
	 */
	public List<NationalityTO> convertNationalityBotoTo(List<Nationality> nationalityList)throws Exception {
		// TODO Auto-generated method stub
		List<NationalityTO> nationalityTOList=new ArrayList<NationalityTO>();
		if(nationalityList!=null){
			Iterator<Nationality> nationalityIterator=nationalityList.iterator();
			while(nationalityIterator.hasNext()){
				NationalityTO nationalityTO=new NationalityTO();
				Nationality nationality=nationalityIterator.next();
				if(nationality.getId()!=0){
					nationalityTO.setId(nationality.getId()+"");
				}
				if(nationality.getName()!=null && !nationality.getName().isEmpty()){
					nationalityTO.setName(nationality.getName());
				}
				nationalityTOList.add(nationalityTO);
			}
		}
		return nationalityTOList;
	}
	
	/**
	 * @param tcDetailsOldStudentsForm
	 * @param stuMap
	 * @param tcNumber
	 * @param regnoList
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public TcDetailsOldStudents addOldStudentToBo(TcDetailsOldStudentsForm tcDetailsOldStudentsForm,Map<String, TcDetailsOldStudents> stuMap, TCNumber tcNumber, List<String> regnoList) throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
		TcDetailsOldStudents tcDetailsOldStudents=null;
		if(stuMap!=null && regnoList!=null)
		if(!regnoList.contains(tcDetailsOldStudentsForm.getRegisterNo()) || tcDetailsOldStudentsForm.getId()!=null && !tcDetailsOldStudentsForm.getId().isEmpty()){ 
			
			tcDetailsOldStudents=new TcDetailsOldStudents();
			
			if(tcDetailsOldStudentsForm.getId()!=null && !tcDetailsOldStudentsForm.getId().isEmpty()){
				tcDetailsOldStudents=stuMap.get(tcDetailsOldStudentsForm.getId());
				tcDetailsOldStudents.setId(Integer.parseInt(tcDetailsOldStudentsForm.getId()));
			}
			
			if(tcDetailsOldStudentsForm.getTcFor()!=null && !tcDetailsOldStudentsForm.getTcFor().isEmpty()){
				tcDetailsOldStudents.setTcFor(tcDetailsOldStudentsForm.getTcFor());
			}
			
			if(tcDetailsOldStudentsForm.getTcType()!=null && !tcDetailsOldStudentsForm.getTcType().isEmpty()){
				tcDetailsOldStudents.setTcType(tcDetailsOldStudentsForm.getTcType());
				tcDetailsOldStudentsForm.setTempTcType(tcDetailsOldStudentsForm.getTcType());
				if(tcDetailsOldStudentsForm.getTcType().equalsIgnoreCase("normal")){
					tcDetailsOldStudentsForm.setTempTcType("");
				}
			}
			
			if(tcDetailsOldStudentsForm.getYear()!=null && !tcDetailsOldStudentsForm.getYear().isEmpty()){
				tcDetailsOldStudents.setAcadamicYear(Integer.parseInt(tcDetailsOldStudentsForm.getYear()));
			}
			
			if(!regnoList.contains(tcDetailsOldStudentsForm.getRegisterNo())&& tcDetailsOldStudentsForm.getRegisterNo()!=null && !tcDetailsOldStudentsForm.getRegisterNo().isEmpty()){
				tcDetailsOldStudents.setRegisterNo(tcDetailsOldStudentsForm.getRegisterNo());
				tcDetailsOldStudentsForm.setRegNo(tcDetailsOldStudentsForm.getRegisterNo());
			}else if(tcDetailsOldStudentsForm.getId()==null){
				return null;
			}
			
			if(tcDetailsOldStudentsForm.getName()!=null && !tcDetailsOldStudentsForm.getName().isEmpty()){
				tcDetailsOldStudents.setName(tcDetailsOldStudentsForm.getName());
				tcDetailsOldStudentsForm.setName(tcDetailsOldStudentsForm.getName().toUpperCase());
			}
			
			if(tcDetailsOldStudentsForm.getStudentNo()!=null && !tcDetailsOldStudentsForm.getStudentNo().isEmpty()){
				tcDetailsOldStudents.setStudentNo(tcDetailsOldStudentsForm.getStudentNo());
			}
			
			if(tcDetailsOldStudentsForm.getDateOfBirth()!=null && !tcDetailsOldStudentsForm.getDateOfBirth().isEmpty()){
				tcDetailsOldStudents.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(tcDetailsOldStudentsForm.getDateOfBirth()));
				String dob=CommonUtil.ConvertStringToDateFormat(tcDetailsOldStudents.getDateOfBirth().toString(), "yyyy-mm-dd", "dd/mm/yyyy");
				//code added by sudhir
				String dobInwords = convertIntegerToWordForCJC(dob);
				tcDetailsOldStudentsForm.setDobInWords(dobInwords);
				//
				//tcDetailsOldStudentsForm.setDobInWords(convertIntegerToWord(dob));
			}
			
			if(tcDetailsOldStudentsForm.getGender()!=null && !tcDetailsOldStudentsForm.getGender().isEmpty()){
				tcDetailsOldStudents.setGender(tcDetailsOldStudentsForm.getGender());
				tcDetailsOldStudentsForm.setGender(tcDetailsOldStudentsForm.getGender().toUpperCase());
			}
			
			if(tcDetailsOldStudentsForm.getReligionId()!=null && !tcDetailsOldStudentsForm.getReligionId().isEmpty()&& 
					!tcDetailsOldStudentsForm.getReligionId().equalsIgnoreCase("Other")){
				Religion religion=new Religion();
				religion.setId(Integer.parseInt(tcDetailsOldStudentsForm.getReligionId()));
				tcDetailsOldStudents.setReligion(religion);
				tcDetailsOldStudents.setReligionOthers(null);
				tcDetailsOldStudentsForm.setReligionName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(tcDetailsOldStudentsForm.getReligionId()),"Religion",true,"name").toUpperCase());
			}else if(tcDetailsOldStudentsForm.getReligionOthers()!=null && !tcDetailsOldStudentsForm.getReligionOthers().isEmpty()){
				tcDetailsOldStudents.setReligionOthers(tcDetailsOldStudentsForm.getReligionOthers());
				tcDetailsOldStudents.setReligion(null);
				tcDetailsOldStudentsForm.setReligionName(tcDetailsOldStudentsForm.getReligionOthers().toUpperCase());
			}
			
			if(tcDetailsOldStudentsForm.getCaste()!=null && !tcDetailsOldStudentsForm.getCaste().isEmpty()){
				tcDetailsOldStudents.setCaste(tcDetailsOldStudentsForm.getCaste());
				tcDetailsOldStudentsForm.setCaste(tcDetailsOldStudentsForm.getCaste().toUpperCase());
			}else{
				tcDetailsOldStudents.setCaste(null);
			}
			String subCaste="";
			String castecate="";
			if(tcDetailsOldStudentsForm.getSubCaste()!=null && !tcDetailsOldStudentsForm.getSubCaste().isEmpty()){
				tcDetailsOldStudents.setSubCaste(tcDetailsOldStudentsForm.getSubCaste());
				subCaste=tcDetailsOldStudentsForm.getSubCaste().toUpperCase();
			}else{
				tcDetailsOldStudents.setSubCaste(null);
			}
			if(tcDetailsOldStudentsForm.getCaste()!=null && !tcDetailsOldStudentsForm.getCaste().isEmpty()){
				tcDetailsOldStudentsForm.setReligionName(tcDetailsOldStudentsForm.getReligionName()+" - "+tcDetailsOldStudentsForm.getCaste());
			}
			if(subCaste!=null && !subCaste.isEmpty()){
				tcDetailsOldStudentsForm.setReligionName(tcDetailsOldStudentsForm.getReligionName()+" - "+subCaste);
			}
			
			if(tcDetailsOldStudentsForm.getCategoryId()!=null && !tcDetailsOldStudentsForm.getCategoryId().isEmpty() && 
					!tcDetailsOldStudentsForm.getCategoryId().equalsIgnoreCase("Other")){
				Caste category=new Caste();
				category.setId(Integer.parseInt(tcDetailsOldStudentsForm.getCategoryId()));
				tcDetailsOldStudents.setCategory(category);
				tcDetailsOldStudents.setCategoryOthers(null);
				castecate=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(tcDetailsOldStudentsForm.getCategoryId()),"Caste",true,"name");
				if(castecate.equalsIgnoreCase("sc") || castecate.equalsIgnoreCase("st") ||castecate.equalsIgnoreCase("Nomadic Tribe") 
						||castecate.equalsIgnoreCase("Semi-Nomadic Tribe")  ||castecate.equalsIgnoreCase("CAT I") ||castecate.equalsIgnoreCase("CAT IIIB")
						||castecate.equalsIgnoreCase("CAT IIIA") ||castecate.equalsIgnoreCase("CAT IIA") ||castecate.equalsIgnoreCase("CAT IIB")){
					if(!subCaste.isEmpty()){
						tcDetailsOldStudentsForm.setCasteCategoryName("YES, "+" "+castecate.toUpperCase()+" ("+subCaste.toUpperCase()+")");
					}else{
						tcDetailsOldStudentsForm.setCasteCategoryName("YES, "+" "+castecate.toUpperCase());
					}
				}else{
					tcDetailsOldStudentsForm.setCasteCategoryName("NO");
				}
			}else if(tcDetailsOldStudentsForm.getCasteOthers()!=null && !tcDetailsOldStudentsForm.getCasteOthers().isEmpty()){
				tcDetailsOldStudents.setCategoryOthers(tcDetailsOldStudentsForm.getCasteOthers());
				tcDetailsOldStudents.setCategory(null);
				tcDetailsOldStudentsForm.setCasteCategoryName("NO"+", "+tcDetailsOldStudentsForm.getCasteOthers().toUpperCase());
			}
			
			if(tcDetailsOldStudentsForm.getNationalityId()!=null && !tcDetailsOldStudentsForm.getNationalityId().isEmpty()
					&& !tcDetailsOldStudentsForm.getNationalityId().equalsIgnoreCase("Other")){
				Nationality nationality=new Nationality();
				nationality.setId(Integer.parseInt(tcDetailsOldStudentsForm.getNationalityId()));
				tcDetailsOldStudents.setNationality(nationality);
				tcDetailsOldStudents.setNationalityOthers(null);
				tcDetailsOldStudentsForm.setNationalityName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(tcDetailsOldStudentsForm.getNationalityId()),"Nationality",true,"name").toUpperCase());
			}else if(tcDetailsOldStudentsForm.getNationalityOthers()!=null && !tcDetailsOldStudentsForm.getNationalityOthers().isEmpty()){
				tcDetailsOldStudents.setNationalityOthers(tcDetailsOldStudentsForm.getNationalityOthers());
				tcDetailsOldStudents.setNationality(null);
				tcDetailsOldStudentsForm.setNationalityName(tcDetailsOldStudentsForm.getNationalityOthers().toUpperCase());
			}
			
			if(tcDetailsOldStudentsForm.getFatherName()!=null && !tcDetailsOldStudentsForm.getFatherName().isEmpty()){
				tcDetailsOldStudents.setFatherName(tcDetailsOldStudentsForm.getFatherName());
				tcDetailsOldStudentsForm.setFatherName(tcDetailsOldStudentsForm.getFatherName().toUpperCase());
			}else{
				tcDetailsOldStudents.setFatherName(tcDetailsOldStudentsForm.getFatherName());
			}
			
			if(tcDetailsOldStudentsForm.getMotherName()!=null && !tcDetailsOldStudentsForm.getMotherName().isEmpty()){
				tcDetailsOldStudents.setMotherName(tcDetailsOldStudentsForm.getMotherName());
				tcDetailsOldStudentsForm.setMotherName(tcDetailsOldStudentsForm.getMotherName().toUpperCase());
			}else{
				tcDetailsOldStudents.setMotherName(tcDetailsOldStudentsForm.getMotherName());
			}
			if(tcDetailsOldStudentsForm.getAdmissionDate()!=null && !tcDetailsOldStudentsForm.getAdmissionDate().isEmpty()){
				tcDetailsOldStudents.setAdmissionDate(CommonUtil.ConvertStringToSQLDate(tcDetailsOldStudentsForm.getAdmissionDate()));
			}
			if(tcDetailsOldStudentsForm.getDateOfLeaving()!=null && !tcDetailsOldStudentsForm.getDateOfLeaving().isEmpty()){
				tcDetailsOldStudents.setDateOfLeaving(CommonUtil.ConvertStringToSQLDate(tcDetailsOldStudentsForm.getDateOfLeaving()));
			}
			
			if(tcDetailsOldStudentsForm.getClassName()!=null && !tcDetailsOldStudentsForm.getClassName().isEmpty()){
				tcDetailsOldStudents.setClassName(tcDetailsOldStudentsForm.getClassName());
				tcDetailsOldStudentsForm.setClassName(tcDetailsOldStudentsForm.getClassName().toUpperCase());
			}
			
			if(tcDetailsOldStudentsForm.getPart1Subjects()!=null && !tcDetailsOldStudentsForm.getPart1Subjects().isEmpty()){
				tcDetailsOldStudents.setPart1Subjects(tcDetailsOldStudentsForm.getPart1Subjects());
				tcDetailsOldStudentsForm.setPart1Subjects(tcDetailsOldStudentsForm.getPart1Subjects().toUpperCase());
			}
			
			if(tcDetailsOldStudentsForm.getPart2Subjects()!=null && !tcDetailsOldStudentsForm.getPart2Subjects().isEmpty()){
				tcDetailsOldStudents.setPart2Subjects(tcDetailsOldStudentsForm.getPart2Subjects());
				tcDetailsOldStudentsForm.setPart2Subjects(tcDetailsOldStudentsForm.getPart2Subjects().toUpperCase());
			}
			
			if(tcDetailsOldStudentsForm.getPassed()!=null && !tcDetailsOldStudentsForm.getPassed().isEmpty()){
				if(tcDetailsOldStudentsForm.getPassed().equalsIgnoreCase("yes")){
					tcDetailsOldStudents.setPassed(tcDetailsOldStudentsForm.getPassed());
					tcDetailsOldStudentsForm.setPassed(tcDetailsOldStudentsForm.getPassed().toUpperCase());
					tcDetailsOldStudentsForm.setPassDetails(tcDetailsOldStudentsForm.getPassed().toUpperCase());
				}else{
					tcDetailsOldStudents.setPassed(tcDetailsOldStudentsForm.getPassed());
					tcDetailsOldStudentsForm.setPassed(tcDetailsOldStudentsForm.getPassed().toUpperCase());
					if(tcDetailsOldStudentsForm.getSubjectsPassed()!=null && !tcDetailsOldStudentsForm.getSubjectsPassed().isEmpty()){
						tcDetailsOldStudents.setSubjectsPassed(tcDetailsOldStudentsForm.getSubjectsPassed());
						tcDetailsOldStudentsForm.setSubjectsPassed(tcDetailsOldStudentsForm.getSubjectsPassed().toUpperCase());
						tcDetailsOldStudentsForm.setPassDetails(tcDetailsOldStudentsForm.getPassed()+", "+tcDetailsOldStudentsForm.getSubjectsPassed().toUpperCase());
					}else{
						tcDetailsOldStudents.setSubjectsPassed(null);
						tcDetailsOldStudentsForm.setPassDetails(tcDetailsOldStudentsForm.getPassed());
					}
				}
			}
			
				tcDetailsOldStudents.setPublicExamName(tcDetailsOldStudentsForm.getPublicExamName());
				tcDetailsOldStudentsForm.setPublicExamName(tcDetailsOldStudentsForm.getPublicExamName().toUpperCase());
			
				tcDetailsOldStudents.setExamRegisterNo(tcDetailsOldStudentsForm.getExamRegisterNo());
			if(tcDetailsOldStudentsForm.getYr()!=null && !tcDetailsOldStudentsForm.getYr().isEmpty()){
				tcDetailsOldStudents.setYear(Integer.parseInt(tcDetailsOldStudentsForm.getYr()));
			}else{
				tcDetailsOldStudents.setYear(null);
			}
			
				tcDetailsOldStudents.setMonth(tcDetailsOldStudentsForm.getMonth());
			
				tcDetailsOldStudentsForm.setMonth1(monthMap.get(tcDetailsOldStudentsForm.getMonth()));
			
			tcDetailsOldStudentsForm.setRegMonthYear(tcDetailsOldStudentsForm.getExamRegisterNo()+", "+
					monthMap.get(tcDetailsOldStudentsForm.getMonth())+" "+tcDetailsOldStudentsForm.getYr());
			if(tcDetailsOldStudentsForm.getScolorship()!=null && !tcDetailsOldStudentsForm.getScolorship().isEmpty()){
				tcDetailsOldStudents.setScolorship(tcDetailsOldStudentsForm.getScolorship());
				tcDetailsOldStudentsForm.setScolorship(tcDetailsOldStudentsForm.getScolorship().toUpperCase());
			}
			
			if(tcDetailsOldStudentsForm.getFeePaid()!=null && !tcDetailsOldStudentsForm.getFeePaid().isEmpty()){
				tcDetailsOldStudents.setFeePaid(tcDetailsOldStudentsForm.getFeePaid());
				tcDetailsOldStudentsForm.setFeePaid(tcDetailsOldStudentsForm.getFeePaid().toUpperCase());
			}
			
			if(tcDetailsOldStudentsForm.getCharacterAndConductId()!=null && !tcDetailsOldStudentsForm.getCharacterAndConductId().isEmpty()){
				CharacterAndConduct characterAndConduct=new CharacterAndConduct();
				characterAndConduct.setId(Integer.parseInt(tcDetailsOldStudentsForm.getCharacterAndConductId()));
				tcDetailsOldStudents.setCharacterAndConduct(characterAndConduct);
//				tcDetailsOldStudentsForm.setCharacterAndConduct(tcDetailsOldStudentsForm.getCharAndConductToList().get(ccid-1).getName().toUpperCase());
				tcDetailsOldStudentsForm.setCharacterAndConduct(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(tcDetailsOldStudentsForm.getCharacterAndConductId()),"CharacterAndConduct",true,"name").toUpperCase());
			}
			
			if(tcDetailsOldStudentsForm.getDateOfApplication()!=null && !tcDetailsOldStudentsForm.getDateOfApplication().isEmpty()){
				tcDetailsOldStudents.setDateOfApplication(CommonUtil.ConvertStringToSQLDate(tcDetailsOldStudentsForm.getDateOfApplication()));
			}
			
			if(tcDetailsOldStudentsForm.getDateOfIssue()!=null && !tcDetailsOldStudentsForm.getDateOfIssue().isEmpty()){
				tcDetailsOldStudents.setDateOfIssue(CommonUtil.ConvertStringToSQLDate(tcDetailsOldStudentsForm.getDateOfIssue()));
			}
			
			int siNo=0;
			int yearSuffix=0;
			String ysuffix="";
			String tcNo="";
			if(tcDetailsOldStudentsForm.getId()==null || (tcDetailsOldStudentsForm.getId()!=null 
					&& (!tcDetailsOldStudentsForm.getTcFor().equalsIgnoreCase(tcDetailsOldStudentsForm.getTempTcFor()) ||
							!tcDetailsOldStudentsForm.getTempYear().equalsIgnoreCase(tcDetailsOldStudentsForm.getYear())))){
				siNo=tcNumber.getSlNo();
				siNo=siNo+1;
				yearSuffix=Integer.parseInt(tcDetailsOldStudentsForm.getYear().substring(2,4))+1;
				if(yearSuffix<10){
					ysuffix="0"+String.valueOf(yearSuffix);
				}else{
					ysuffix=String.valueOf(yearSuffix);
				}
				Date date=CommonUtil.ConvertStringToSQLDate(tcDetailsOldStudentsForm.getAdmissionDate());
				Date date1=CommonUtil.ConvertStringToSQLDate(tcDetailsOldStudentsForm.getDateOfLeaving());
				SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
				int admitYear=Integer.parseInt(simpleDateformat.format(date));
				int endYear = Integer.parseInt(simpleDateformat.format(date1));
				tcNo=tcDetailsOldStudentsForm.getTcFor()+siNo+"/"+admitYear+"-"+endYear;
				tcNumber.setSlNo(siNo);
				tcDetailsOldStudents.setTcNo(tcNo);
				tcDetailsOldStudentsForm.setTcNumber(tcNo.toUpperCase());
			}
			return tcDetailsOldStudents;
		}
		return tcDetailsOldStudents;
	}
	
	/**
	 * @param tcDetailsOldStudents
	 * @param tcDetailsOldStudentsForm
	 * @throws Exception
	 */
	public void convertOldStudentBoToForm(TcDetailsOldStudents tcDetailsOldStudents,TcDetailsOldStudentsForm tcDetailsOldStudentsForm)throws Exception {
		// TODO Auto-generated method stub
		if(tcDetailsOldStudents!=null){
			if(tcDetailsOldStudents.getId()>0){
				tcDetailsOldStudentsForm.setId(String.valueOf(tcDetailsOldStudents.getId()).toUpperCase());
			}
			
			if(tcDetailsOldStudents.getTcFor()!=null && !tcDetailsOldStudents.getTcFor().isEmpty()){
				tcDetailsOldStudentsForm.setTcFor(tcDetailsOldStudents.getTcFor().toUpperCase());
				tcDetailsOldStudentsForm.setTempTcFor(tcDetailsOldStudents.getTcFor().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getTcNo()!=null && !tcDetailsOldStudents.getTcNo().isEmpty()){
				tcDetailsOldStudentsForm.setTcNumber(tcDetailsOldStudents.getTcNo().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getTcType()!=null && !tcDetailsOldStudents.getTcType().isEmpty()){
				tcDetailsOldStudentsForm.setTcType(tcDetailsOldStudents.getTcType());
				tcDetailsOldStudentsForm.setTempTcType(tcDetailsOldStudents.getTcType());
				if(tcDetailsOldStudents.getTcType().equalsIgnoreCase("normal")){
					tcDetailsOldStudentsForm.setTempTcType("");
				}
			}
			
			if(tcDetailsOldStudents.getAcadamicYear()!=0){
//				tcDetailsOldStudentsForm.setAcadamicYear(tcDetailsOldStudents.getAcadamicYear());
				tcDetailsOldStudentsForm.setYear(tcDetailsOldStudents.getAcadamicYear().toString());
				tcDetailsOldStudentsForm.setTempYear(tcDetailsOldStudents.getAcadamicYear().toString());
			}
			
			if(tcDetailsOldStudents.getRegisterNo()!=null && !tcDetailsOldStudents.getRegisterNo().isEmpty()){
					tcDetailsOldStudentsForm.setRegisterNo(tcDetailsOldStudents.getRegisterNo().toUpperCase());
					tcDetailsOldStudentsForm.setRegNo(tcDetailsOldStudents.getRegisterNo().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getName()!=null && !tcDetailsOldStudents.getName().isEmpty()){
				tcDetailsOldStudentsForm.setName(tcDetailsOldStudents.getName().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getStudentNo()!=null && !tcDetailsOldStudents.getStudentNo().isEmpty()){
				tcDetailsOldStudentsForm.setStudentNo(tcDetailsOldStudents.getStudentNo().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getDateOfBirth()!=null){
				tcDetailsOldStudentsForm.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(tcDetailsOldStudents.getDateOfBirth().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				String dob=CommonUtil.ConvertStringToDateFormat(tcDetailsOldStudents.getDateOfBirth().toString(), "yyyy-mm-dd", "dd/mm/yyyy");
				//code added by sudhir
				String dobInwords = convertIntegerToWordForCJC(dob);
				tcDetailsOldStudentsForm.setDobInWords(dobInwords);
				//
			//tcDetailsOldStudentsForm.setDobInWords(convertIntegerToWord(dob).toUpperCase());
			}
			if(tcDetailsOldStudents.getGender()!=null && !tcDetailsOldStudents.getGender().isEmpty()){
				tcDetailsOldStudentsForm.setGender(tcDetailsOldStudents.getGender().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getReligion()!=null && tcDetailsOldStudents.getReligion().getId()>0){
				tcDetailsOldStudentsForm.setReligionId(String.valueOf(tcDetailsOldStudents.getReligion().getId()));
//				tcDetailsOldStudentsForm.setReligionOthers(tcDetailsOldStudents.getReligion().getName());
			}
			if(tcDetailsOldStudents.getReligionOthers()!=null && !tcDetailsOldStudents.getReligionOthers().isEmpty()){
				tcDetailsOldStudentsForm.setReligionId("Other");
				tcDetailsOldStudentsForm.setReligionOthers(tcDetailsOldStudents.getReligionOthers().toUpperCase());
			}
//			code added later by priyatham
			if(tcDetailsOldStudentsForm.getReligionId()!=null && !tcDetailsOldStudentsForm.getReligionId().isEmpty()&& 
					!tcDetailsOldStudentsForm.getReligionId().equalsIgnoreCase("Other")){
				tcDetailsOldStudentsForm.setReligionName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(tcDetailsOldStudentsForm.getReligionId()),"Religion",true,"name").toUpperCase());
			}else if(tcDetailsOldStudentsForm.getReligionOthers()!=null && !tcDetailsOldStudentsForm.getReligionOthers().isEmpty()){
				tcDetailsOldStudentsForm.setReligionName(tcDetailsOldStudentsForm.getReligionOthers().toUpperCase());
			}
//			end
			if(tcDetailsOldStudents.getCaste()!=null && !tcDetailsOldStudents.getCaste().isEmpty()){
				tcDetailsOldStudentsForm.setCaste(tcDetailsOldStudents.getCaste().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getSubCaste()!=null && !tcDetailsOldStudents.getSubCaste().isEmpty()){
				tcDetailsOldStudentsForm.setSubCaste(tcDetailsOldStudents.getSubCaste().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getCategory()!=null && tcDetailsOldStudents.getCategory().getId()>0){
				tcDetailsOldStudentsForm.setCategoryId(String.valueOf(tcDetailsOldStudents.getCategory().getId()));
//				tcDetailsOldStudentsForm.setCasteOthers(tcDetailsOldStudents.getCategory().getName());
			}
			
			if(tcDetailsOldStudents.getCategoryOthers()!=null && !tcDetailsOldStudents.getCategoryOthers().isEmpty()){
				tcDetailsOldStudentsForm.setCategoryId("Other");
				tcDetailsOldStudentsForm.setCasteOthers(tcDetailsOldStudents.getCategoryOthers().toUpperCase());
			}
			
//code added later by priyatham
			if(tcDetailsOldStudentsForm.getCaste()!=null && !tcDetailsOldStudentsForm.getCaste().isEmpty()){
				tcDetailsOldStudentsForm.setCaste(tcDetailsOldStudentsForm.getCaste().toUpperCase());
			}
			String subCaste="";
			String castecate="";
			if(tcDetailsOldStudentsForm.getSubCaste()!=null && !tcDetailsOldStudentsForm.getSubCaste().isEmpty()){
				subCaste=tcDetailsOldStudentsForm.getSubCaste().toUpperCase();
			}
			if(tcDetailsOldStudentsForm.getCaste()!=null && !tcDetailsOldStudentsForm.getCaste().isEmpty()){
				tcDetailsOldStudentsForm.setReligionName(tcDetailsOldStudentsForm.getReligionName()+" - "+tcDetailsOldStudentsForm.getCaste());
			}// code added by sudhir
			else{
				tcDetailsOldStudentsForm.setReligionName(tcDetailsOldStudentsForm.getReligionName());
			}
			//
			if(subCaste!=null && !subCaste.isEmpty()){
				tcDetailsOldStudentsForm.setReligionName(tcDetailsOldStudentsForm.getReligionName()+" - "+subCaste);
			}// code added by sudhir
			tcDetailsOldStudentsForm.setReligionName(tcDetailsOldStudentsForm.getReligionName());
			//
			
			if(tcDetailsOldStudentsForm.getCategoryId()!=null && !tcDetailsOldStudentsForm.getCategoryId().isEmpty() && 
					!tcDetailsOldStudentsForm.getCategoryId().equalsIgnoreCase("Other")){
				castecate=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(tcDetailsOldStudentsForm.getCategoryId()),"Caste",true,"name");
				if(castecate.equalsIgnoreCase("sc") || castecate.equalsIgnoreCase("st") ||castecate.equalsIgnoreCase("Nomadic Tribe") 
						||castecate.equalsIgnoreCase("Semi-Nomadic Tribe")  ||castecate.equalsIgnoreCase("CAT I") ||castecate.equalsIgnoreCase("CAT IIIB")
						||castecate.equalsIgnoreCase("CAT IIIA") ||castecate.equalsIgnoreCase("CAT IIA") ||castecate.equalsIgnoreCase("CAT IIB")){
					if(!subCaste.isEmpty()){
						tcDetailsOldStudentsForm.setCasteCategoryName("YES, "+" "+castecate.toUpperCase()+" ("+subCaste.toUpperCase()+")");
					}else{
						tcDetailsOldStudentsForm.setCasteCategoryName("YES, "+" "+castecate.toUpperCase());
					}
				}else{
					tcDetailsOldStudentsForm.setCasteCategoryName("NO");
				}
			}else if(tcDetailsOldStudentsForm.getCasteOthers()!=null && !tcDetailsOldStudentsForm.getCasteOthers().isEmpty()){
				tcDetailsOldStudentsForm.setCasteCategoryName("NO"+", "+tcDetailsOldStudentsForm.getCasteOthers().toUpperCase());
			}
//			code added later end
			
			if(tcDetailsOldStudents.getNationality()!=null && tcDetailsOldStudents.getNationality().getId()>0){
				tcDetailsOldStudentsForm.setNationalityId(String.valueOf(tcDetailsOldStudents.getNationality().getId()));
//				tcDetailsOldStudentsForm.setNationalityOthers(tcDetailsOldStudents.getNationality().getName());
			}
			
			if(tcDetailsOldStudents.getNationalityOthers()!=null && !tcDetailsOldStudents.getNationalityOthers().isEmpty()){
				tcDetailsOldStudentsForm.setNationalityId("Other");
				tcDetailsOldStudentsForm.setNationalityOthers(tcDetailsOldStudents.getNationalityOthers().toUpperCase());
			}
			
//			code added later by priyatham
			if(tcDetailsOldStudentsForm.getNationalityId()!=null && !tcDetailsOldStudentsForm.getNationalityId().isEmpty()
					&& !tcDetailsOldStudentsForm.getNationalityId().equalsIgnoreCase("Other")){
				tcDetailsOldStudentsForm.setNationalityName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(tcDetailsOldStudentsForm.getNationalityId()),"Nationality",true,"name").toUpperCase());
			}else if(tcDetailsOldStudentsForm.getNationalityOthers()!=null && !tcDetailsOldStudentsForm.getNationalityOthers().isEmpty()){
				tcDetailsOldStudentsForm.setNationalityName(tcDetailsOldStudentsForm.getNationalityOthers().toUpperCase());
			}
//			end
			
			if(tcDetailsOldStudents.getFatherName()!=null && !tcDetailsOldStudents.getFatherName().isEmpty()){
				tcDetailsOldStudentsForm.setFatherName(tcDetailsOldStudents.getFatherName().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getMotherName()!=null && !tcDetailsOldStudents.getMotherName().isEmpty()){
				tcDetailsOldStudentsForm.setMotherName(tcDetailsOldStudents.getMotherName().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getAdmissionDate()!=null){
				tcDetailsOldStudentsForm.setAdmissionDate(CommonUtil.ConvertStringToDateFormat(tcDetailsOldStudents.getAdmissionDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
			}
			
			if(tcDetailsOldStudents.getDateOfLeaving()!=null){
				tcDetailsOldStudentsForm.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(tcDetailsOldStudents.getDateOfLeaving().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
			}
			
			if(tcDetailsOldStudents.getClassName()!=null && !tcDetailsOldStudents.getClassName().isEmpty()){
				tcDetailsOldStudentsForm.setClassName(tcDetailsOldStudents.getClassName().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getPart1Subjects()!=null && !tcDetailsOldStudents.getPart1Subjects().isEmpty()){
				tcDetailsOldStudentsForm.setPart1Subjects(tcDetailsOldStudents.getPart1Subjects().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getPart2Subjects()!=null && !tcDetailsOldStudents.getPart2Subjects().isEmpty()){
				tcDetailsOldStudentsForm.setPart2Subjects(tcDetailsOldStudents.getPart2Subjects().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getPassed()!=null && !tcDetailsOldStudents.getPassed().isEmpty()){
				if(tcDetailsOldStudents.getPassed().equalsIgnoreCase("yes")){
				tcDetailsOldStudentsForm.setPassed(tcDetailsOldStudents.getPassed().toUpperCase());
				tcDetailsOldStudentsForm.setPassDetails(tcDetailsOldStudentsForm.getPassed().toUpperCase());
				}else{
					tcDetailsOldStudentsForm.setPassed(tcDetailsOldStudents.getPassed().toUpperCase());
					if(tcDetailsOldStudents.getSubjectsPassed()!=null && !tcDetailsOldStudents.getSubjectsPassed().isEmpty()){
					tcDetailsOldStudentsForm.setSubjectsPassed(tcDetailsOldStudents.getSubjectsPassed().toUpperCase());
					tcDetailsOldStudentsForm.setPassDetails(tcDetailsOldStudentsForm.getPassed()+", "+tcDetailsOldStudentsForm.getSubjectsPassed().toUpperCase());
				}else {
					tcDetailsOldStudentsForm.setPassDetails(tcDetailsOldStudentsForm.getPassed());
				}
				}
			}
			
			if(tcDetailsOldStudents.getPublicExamName()!=null && !tcDetailsOldStudents.getPublicExamName().isEmpty()){
				tcDetailsOldStudentsForm.setPublicExamName(tcDetailsOldStudents.getPublicExamName().toUpperCase());
			}
			String examRegno="";
			if(tcDetailsOldStudents.getExamRegisterNo()!=null && !tcDetailsOldStudents.getExamRegisterNo().isEmpty()){
				tcDetailsOldStudentsForm.setExamRegisterNo(tcDetailsOldStudents.getExamRegisterNo());
				examRegno=tcDetailsOldStudents.getExamRegisterNo().toUpperCase();
			}
			
			if(tcDetailsOldStudents.getYear()!=null){
				tcDetailsOldStudentsForm.setYr(String.valueOf(tcDetailsOldStudents.getYear()));
			}
			
			if(tcDetailsOldStudents.getMonth()!=null && !tcDetailsOldStudents.getMonth().isEmpty()){
				tcDetailsOldStudentsForm.setMonth(tcDetailsOldStudents.getMonth());
			}
			// code added by sudhir
			if(tcDetailsOldStudents.getMonth()!=null && !tcDetailsOldStudents.getMonth().isEmpty()){
			tcDetailsOldStudentsForm.setMonth1(monthMap.get(tcDetailsOldStudentsForm.getMonth().toUpperCase()));
			}
			//
			if(tcDetailsOldStudents.getMonth()!=null && !tcDetailsOldStudents.getMonth().isEmpty() && tcDetailsOldStudents.getYear()!=null && tcDetailsOldStudents.getYear()!=0){
				tcDetailsOldStudentsForm.setRegMonthYear(examRegno+", "+monthMap.get(tcDetailsOldStudents.getMonth())+" "+tcDetailsOldStudents.getYear());
				tcDetailsOldStudentsForm.setRegMonthYear(tcDetailsOldStudentsForm.getRegMonthYear().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getScolorship()!=null && !tcDetailsOldStudents.getScolorship().isEmpty()){
				tcDetailsOldStudentsForm.setScolorship(tcDetailsOldStudents.getScolorship().toUpperCase());
			}
			
			if(tcDetailsOldStudents.getFeePaid()!=null && !tcDetailsOldStudents.getFeePaid().isEmpty()){
				tcDetailsOldStudentsForm.setFeePaid(tcDetailsOldStudents.getFeePaid().toUpperCase());
			}
			
//			code added later by priyatham
			if(tcDetailsOldStudentsForm.getFeePaid()!=null && !tcDetailsOldStudentsForm.getFeePaid().isEmpty()){
				tcDetailsOldStudents.setFeePaid(tcDetailsOldStudentsForm.getFeePaid());
				tcDetailsOldStudentsForm.setFeePaid(tcDetailsOldStudentsForm.getFeePaid().toUpperCase());
			}
//			end
			
			if(tcDetailsOldStudents.getCharacterAndConduct()!=null && tcDetailsOldStudents.getCharacterAndConduct().getId()>0){
				CharacterAndConduct characterAndConduct=tcDetailsOldStudents.getCharacterAndConduct();
				tcDetailsOldStudentsForm.setCharacterAndConductId(String.valueOf(characterAndConduct.getId()));
			}
			
//			code added later by priyatham
			if(tcDetailsOldStudentsForm.getCharacterAndConductId()!=null && !tcDetailsOldStudentsForm.getCharacterAndConductId().isEmpty()){
				CharacterAndConduct characterAndConduct=new CharacterAndConduct();
				characterAndConduct.setId(Integer.parseInt(tcDetailsOldStudentsForm.getCharacterAndConductId()));
				tcDetailsOldStudents.setCharacterAndConduct(characterAndConduct);
				tcDetailsOldStudentsForm.setCharacterAndConduct(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(tcDetailsOldStudentsForm.getCharacterAndConductId()),"CharacterAndConduct",true,"name").toUpperCase());
			}
//			end
			
			if(tcDetailsOldStudents.getDateOfApplication()!=null){
				tcDetailsOldStudentsForm.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(tcDetailsOldStudents.getDateOfApplication().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
			}
			
			if(tcDetailsOldStudents.getDateOfIssue()!=null){
				tcDetailsOldStudentsForm.setDateOfIssue(CommonUtil.ConvertStringToDateFormat(tcDetailsOldStudents.getDateOfIssue().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
			}
		}
	}
	
	/**
	 * @param dobFigures
	 * @return
	 * @throws Exception
	 */
	private String convertIntegerToWord(String dobFigures) throws Exception
	{
		String inWords="";
		StringTokenizer str=new StringTokenizer(dobFigures,"/");
		int date=Integer.parseInt(str.nextToken());
		int month=Integer.parseInt(str.nextToken());
		String year=str.nextToken();
		/*String year1=year.substring(0, 2);
		String year2=year.substring(2);*/
//		inWords=NumberToWordConvertor.getDate(date)+", "+CommonUtil.getMonthForNumber(month)+", "+NumberToWordConvertor.convertNumber(year1).toUpperCase()+" "+NumberToWordConvertor.convertNumber(year2).toUpperCase();
		inWords=NumberToWordConvertor.getDate(date)+", "+CommonUtil.getMonthForNumber(month)+", "+NumberToWordConvertor.convertNumber(year).toUpperCase();
		return inWords;
	}
	/**
	 * @param dobFigures
	 * @return
	 */
	private String convertIntegerToWordForCJC(String dobFigures) 
	{
		String inWords="";
		StringTokenizer str=new StringTokenizer(dobFigures,"/");
		int date=Integer.parseInt(str.nextToken());
		int month=Integer.parseInt(str.nextToken());
		String year=str.nextToken();
		String year1=year.substring(0, 2);
		String year2=year.substring(2);
		inWords=NumberToWordConvertor.getDate(date)+", "+CommonUtil.getMonthForNumber(month)+", "+NumberToWordConvertor.convertNumber(year1).toUpperCase()+" HUNDRED "+NumberToWordConvertor.convertNumber(year2).toUpperCase();
		return inWords;
	}
}
