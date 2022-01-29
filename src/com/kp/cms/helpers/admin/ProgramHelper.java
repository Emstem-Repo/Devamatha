package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.forms.admin.ProgramForm;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.utilities.CommonUtil;

public class ProgramHelper {
	private static final Log log = LogFactory.getLog(ProgramHelper.class);
	public static volatile ProgramHelper programHelper = null;

	public static ProgramHelper getInstance() {
		if (programHelper == null) {
			programHelper = new ProgramHelper();
			return programHelper;
		}
		return programHelper;
	}

	/**
	 * 
	 * @param programList
	 *            this will copy the program BO to TO
	 * @return programList having programTO objects. this is used to display all the programs in the UI
	 */
	public List<ProgramTO> copyProgramBosToTos(List<Program> programList) {
		log.debug("inside copyProgramBosToTos");
		List<ProgramTO> programToList = new ArrayList<ProgramTO>();
		Iterator<Program> iterator = programList.iterator();
		Program program;
		ProgramTO programTo;
		ProgramTypeTO programTypeTO;
		while (iterator.hasNext()) {
			programTo = new ProgramTO();
			programTypeTO = new ProgramTypeTO();
			program = (Program) iterator.next();
			programTo.setId(program.getId());
			programTo.setName(program.getName());
			programTo.setCode(program.getCode());
			programTypeTO.setProgramTypeId(program.getProgramType().getId());
			programTypeTO.setProgramTypeName(program.getProgramType().getName());
			programTo.setProgramTypeTo(programTypeTO);
			programTo.setCertificateProgramName(program.getProgramNameCertificate());
			programToList.add(programTo);
		}
		log.debug("leaving copyProgramBosToTos");
		return programToList;
	}

	
	
	
	public Map<Integer,String> copyProgramBosToMap(List<Program> programList) {
		log.debug("inside copyProgramBosToTos");
		Map<Integer,String> programMap = new HashMap<Integer,String>();
		Iterator<Program> iterator = programList.iterator();
		Program program;
		while (iterator.hasNext()) {
			program = (Program) iterator.next();
			if (program.getIsActive()) {
				programMap.put(program.getId(), program.getName());
			}
			
		}
		programMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(programMap);
		log.debug("leaving copyProgramBosToTos");
		return programMap;
	}
	/**
	 * 
	 * @param programForm
	 *            creates BO from programForm. Program(BO) is used for saving data to the table
	 * 
	 * @return Program BO object
	 */

	public Program populateProgramDataFormForm(ProgramForm programForm, String mode) throws Exception {
		log.debug("inside populateProgramDataFormForm");
		Program program = new Program();
		ProgramType programType = new ProgramType();
		if (mode.equals("Edit")) {
			program.setId(Integer.parseInt(programForm.getProgramId()));
		}
		program.setName(programForm.getName().trim());
		program.setCode(programForm.getProgramCode().trim());
		program.setIsActive(true);
		programType.setId(Integer.parseInt(programForm.getProgramTypeId()));
		program.setProgramType(programType);
		program.setIsMotherTongue(Boolean.valueOf(programForm.getMotherTongue()));
		program.setIsSecondLanguage(Boolean.valueOf(programForm.getSecondLanguage()));
		program.setIsDisplayLanguageKnown(Boolean.valueOf(programForm.getDisplayLanguageKnown()));
		program.setIsHeightWeight(Boolean.valueOf(programForm.getHeightWeight()));
		program.setIsFamilyBackground(Boolean.valueOf(programForm.getFamilyBackground()));
		program.setIsEntranceDetails(Boolean.valueOf(programForm.getEntranceDetails()));
		program.setIsLateralDetails(Boolean.valueOf(programForm.getLateralDetails()));
		program.setIsDisplayTrainingCourse(Boolean.valueOf(programForm.getDisplayTrainingCourse()));
		program.setIsTransferCourse(Boolean.valueOf(programForm.getTransferCourse()));
		program.setIsAdditionalInfo(Boolean.valueOf(programForm.getAdditionalInfo()));
		program.setIsExtraDetails(Boolean.valueOf(programForm.getExtraDetails()));
		program.setIsTCDetails(Boolean.valueOf(programForm.getIsTcDisplay()));
		program.setIsRegistrationNo(Boolean.valueOf(programForm.getIsRegistartionNo()));
		program.setIsOpen(Boolean.valueOf(programForm.getIsOpen()));
		program.setAcademicYear(Integer.parseInt(programForm.getYear()));
		program.setIsExamCenterRequired(Boolean.valueOf(programForm.getIsExamCenterRequired()));
		program.setProgramNameCertificate(programForm.getProgramNameCertificate());
		log.debug("leaving populateProgramDataFormForm");
		return program;
	}
	
	/**
	 * this method will copy data from BO to form.
	 * @param programForm
	 * @param program
	 * @throws Exception
	 */
	public void copyBosToForm(ProgramForm programForm,Program program) throws Exception {
		programForm.setProgramId(String.valueOf(program.getId()));
		programForm.setProgramTypeId(String.valueOf(program.getProgramType().getId()));
		programForm.setName(program.getName());
		programForm.setProgramCode(program.getCode());
		programForm.setMotherTongue(String.valueOf(program.getIsMotherTongue()));
		programForm.setSecondLanguage(String.valueOf(program.getIsSecondLanguage()));
		programForm.setDisplayLanguageKnown(String.valueOf(program.getIsDisplayLanguageKnown()));
		programForm.setHeightWeight(String.valueOf(program.getIsHeightWeight()));
		programForm.setFamilyBackground(String.valueOf(program.getIsFamilyBackground()));
		programForm.setEntranceDetails(String.valueOf(program.getIsEntranceDetails()));
		programForm.setLateralDetails(String.valueOf(program.getIsLateralDetails()));
		programForm.setDisplayTrainingCourse(String.valueOf(program.getIsDisplayTrainingCourse()));
		programForm.setTransferCourse(String.valueOf(program.getIsTransferCourse()));
		programForm.setAdditionalInfo(String.valueOf(program.getIsAdditionalInfo()));
		programForm.setExtraDetails(String.valueOf(program.getIsExtraDetails()));
		programForm.setIsTcDisplay(String.valueOf(program.getIsTCDetails()));
		programForm.setIsRegistartionNo(String.valueOf(program.getIsRegistrationNo()));
		programForm.setIsOpen(String.valueOf(program.getIsOpen()));
		programForm.setIsExamCenterRequired(String.valueOf(program.getIsExamCenterRequired()));
		if(program.getAcademicYear()!= null){
			programForm.setYear(program.getAcademicYear().toString());
		}
		
		// use ful while update in checking duplicate.
		programForm.setOrigProgramCode(program.getCode());
		programForm.setOrigProgramName(program.getName());
		programForm.setOrigprogramTypeId(String.valueOf(program.getProgramType().getId()));
		programForm.setProgramTypeName(program.getProgramType().getName());
		programForm.setStream(program.getStream());
		programForm.setProgramNameCertificate(program.getProgramNameCertificate());
	}
	
	/**
	 * using to show in the view option
	 * @param programForm
	 * @param program
	 * @throws Exception
	 */
	public void copyBosToFormForView(ProgramForm programForm,Program program) throws Exception {
		programForm.setProgramId(String.valueOf(program.getId()));  
		programForm.setProgramTypeId(String.valueOf(program.getProgramType().getId()));
		programForm.setName(program.getName());
		programForm.setProgramCode(program.getCode());
		programForm.setProgramNameCertificate(program.getProgramNameCertificate());
		if(program.getIsMotherTongue().equals(true)){
			programForm.setMotherTongue("Yes");
		}
		else
		{
			programForm.setMotherTongue("No");
		}
			
		
		if(program.getIsSecondLanguage().equals(true)){
			programForm.setSecondLanguage("Yes");
		}
		else {
			programForm.setSecondLanguage("No");
		}
		
		
		if(program.getIsDisplayLanguageKnown().equals(true)){
			programForm.setDisplayLanguageKnown("Yes");
		}
		else
		{
			programForm.setDisplayLanguageKnown("No");
		}
			
		
		if(program.getIsHeightWeight().equals(true)){
			programForm.setHeightWeight("Yes");
		}
		else
		{
			programForm.setHeightWeight("No");
		}
		
		if(program.getIsFamilyBackground().equals(true)){
			programForm.setFamilyBackground("Yes");
		}
		else
		{
			programForm.setFamilyBackground("No");
		}
		
		if(program.getIsEntranceDetails().equals(true)){
			programForm.setEntranceDetails("Yes");
		}
		else
		{
			programForm.setEntranceDetails("No");
		}
		
		if(program.getIsLateralDetails().equals(true)){
			programForm.setLateralDetails("Yes");
		}
		else{
			programForm.setLateralDetails("No");
		}
		
		
		if(program.getIsDisplayTrainingCourse().equals(true)){
			programForm.setDisplayTrainingCourse("Yes");
		}
		else
		{
			programForm.setDisplayTrainingCourse("No");
		}
		
		if(program.getIsTransferCourse().equals(true)){
			programForm.setTransferCourse("Yes");
		}
		else
		{
			programForm.setTransferCourse("No");
		}
		
		if(program.getIsAdditionalInfo().equals(true)){
			programForm.setAdditionalInfo("Yes");
		}
		else
		{
			programForm.setAdditionalInfo("No");
		}
		
		if(program.getIsExtraDetails().equals(true)){
			programForm.setExtraDetails("Yes");
		}
		else
		{
			programForm.setExtraDetails("No");
		}
			
		
		if(program.getIsTCDetails().equals(true)){
			programForm.setIsTcDisplay("Yes");
		}
		else
		{
			programForm.setIsTcDisplay("No");
		}

		if(program.getIsRegistrationNo().equals(true)){
			programForm.setIsRegistartionNo("Yes");
		}
		else
		{
			programForm.setIsRegistartionNo("No");
		}
		
		if(program.getIsOpen().equals(true)){
			programForm.setIsOpen("Yes");
		}
		else
		{
			programForm.setIsOpen("No");
		}
		if(program.getIsExamCenterRequired().equals(true)){
			programForm.setIsExamCenterRequired("Yes");
		}
		else
		{
			programForm.setIsExamCenterRequired("No");
		}
		
		// use ful while update in checking duplicate.
		programForm.setOrigProgramCode(program.getCode());
		programForm.setOrigProgramName(program.getName());
		programForm.setOrigprogramTypeId(String.valueOf(program.getProgramType().getId()));
		programForm.setProgramTypeName(program.getProgramType().getName());
		
	}
	public ProgramTO copyBosToForm(Program program) throws Exception {
		ProgramTO pgmto=new ProgramTO();
		pgmto.setId(program.getId());  
		pgmto.setName(program.getName());
		pgmto.setAcademicYear(String.valueOf(program.getAcademicYear()));
		if(program.getIsMotherTongue().equals(true)){
			pgmto.setMotherTongue("Yes");
		}
		else
		{
			pgmto.setMotherTongue("No");
		}
			
		
		if(program.getIsSecondLanguage().equals(true)){
			pgmto.setSecondLanguage("Yes");
		}
		else {
			pgmto.setSecondLanguage("No");
		}
		
		
		if(program.getIsDisplayLanguageKnown().equals(true)){
			pgmto.setDisplayLanguageKnown("Yes");
		}
		else
		{
			pgmto.setDisplayLanguageKnown("No");
		}
			
		
		if(program.getIsHeightWeight().equals(true)){
			pgmto.setHeightWeight("Yes");
		}
		else
		{
			pgmto.setHeightWeight("No");
		}
		
		if(program.getIsFamilyBackground().equals(true)){
			pgmto.setFamilyBackground("Yes");
		}
		else
		{
			pgmto.setFamilyBackground("No");
		}
		
		if(program.getIsEntranceDetails().equals(true)){
			pgmto.setEntranceDetails("Yes");
		}
		else
		{
			pgmto.setEntranceDetails("No");
		}
		
		if(program.getIsLateralDetails().equals(true)){
			pgmto.setLateralDetails("Yes");
		}
		else{
			pgmto.setLateralDetails("No");
		}
		
		
		if(program.getIsDisplayTrainingCourse().equals(true)){
			pgmto.setDisplayTrainingCourse("Yes");
		}
		else
		{
			pgmto.setDisplayTrainingCourse("No");
		}
		
		if(program.getIsTransferCourse().equals(true)){
			pgmto.setTransferCourse("Yes");
		}
		else
		{
			pgmto.setTransferCourse("No");
		}
		
		if(program.getIsAdditionalInfo().equals(true)){
			pgmto.setAdditionalInfo("Yes");
		}
		else
		{
			pgmto.setAdditionalInfo("No");
		}
		
		if(program.getIsExtraDetails().equals(true)){
			pgmto.setExtraDetails("Yes");
		}
		else
		{
			pgmto.setExtraDetails("No");
		}
			
		
		if(program.getIsTCDetails().equals(true)){
			pgmto.setIsTCDetails(true);
		}
		else
		{
			pgmto.setIsTCDetails(false);
		}

		
		
		if(program.getIsOpen().equals(true)){
			pgmto.setIsOpen(true);
		}
		else
		{
			pgmto.setIsOpen(false);
		}
		if(program.getIsExamCenterRequired().equals(true)){
			pgmto.setIsExamCenterRequired(true);
		}
		else
		{
			pgmto.setIsExamCenterRequired(false);
		}
		return pgmto;
		
		
		
		
	}
	
}
