package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpAcheivement;
import com.kp.cms.bo.admin.EmpEducationDetails;
import com.kp.cms.bo.admin.EmpEducationMaster;
import com.kp.cms.bo.admin.EmpFunctionalArea;
import com.kp.cms.bo.admin.EmpJobType;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpPreviousOrg;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.State;
import com.kp.cms.forms.employee.OnlineResumerSubmissionForm;
import com.kp.cms.to.employee.AchievementsTO;
import com.kp.cms.to.employee.EdicationDetailsTO;
import com.kp.cms.to.employee.JobTypeTO;
import com.kp.cms.to.employee.ProfessionalExperienceTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class OnlineResumerSubmissionHelper {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(OnlineResumerSubmissionHelper.class);
	public static volatile OnlineResumerSubmissionHelper objHelper = null;

	public static OnlineResumerSubmissionHelper getInstance() {
		if (objHelper == null) {
			objHelper = new OnlineResumerSubmissionHelper();
			return objHelper;
		}
		return objHelper;
	}

	public List<EdicationDetailsTO> getEdicationDetails(Map<Integer, String> map)
			throws Exception {
		ArrayList<EdicationDetailsTO> list = new ArrayList<EdicationDetailsTO>();
		EdicationDetailsTO objTo = null;
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			objTo = new EdicationDetailsTO();
			objTo.setId(Integer.parseInt(pairs.getKey().toString()));
			objTo.setCourseName(pairs.getValue().toString());
			objTo.setCourse(null);
			objTo.setYearOfPassing(null);
			objTo.setGrade(null);
			objTo.setInstituteUniversity(null);
			list.add(objTo);

		}

		return list;
	}

	public Map<Integer, String> getListNationalityMap() throws Exception {
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();

		Map<Integer, String> listMap = new HashMap<Integer, String>();
		List<Nationality> listBo = txn.getNationalityFields();
		if (listBo != null) {
			Nationality objBO = null;
			Iterator<Nationality> itr = listBo.iterator();
			while (itr.hasNext()) {
				objBO = (Nationality) itr.next();
				listMap.put(objBO.getId(), objBO.getName());
			}
		}
		return listMap;

	}

	public Map<Integer, String> getCountryMap() throws Exception {
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();

		Map<Integer, String> listMap = new HashMap<Integer, String>();
		List<Country> listBo = txn.getCountryMasterFields();
		if (listBo != null) {
			Country objBO = null;
			Iterator<Country> itr = listBo.iterator();
			while (itr.hasNext()) {
				objBO = (Country) itr.next();
				listMap.put(objBO.getId(), objBO.getName());
			}
		}
		return listMap;
	}

	public List<JobTypeTO> getJobType() throws Exception {
		ArrayList<JobTypeTO> list = new ArrayList<JobTypeTO>();
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();

		List<EmpJobType> listBo = txn.getEmpJobType();
		JobTypeTO to = null;
		if (listBo != null) {
			EmpJobType objBO = null;
			Iterator<EmpJobType> itr = listBo.iterator();
			while (itr.hasNext()) {
				objBO = (EmpJobType) itr.next();
				to = new JobTypeTO();
				to.setId(objBO.getId());
				to.setName(objBO.getName());
				to.setValue("0");
				list.add(to);
			}
		}
		return list;

	}

	public Map<Integer, String> getDepartmentMap() throws Exception {
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();
		Map<Integer, String> listMap = new HashMap<Integer, String>();
		List<Department> listBo = txn.getDepartmentFields();
		if (listBo != null) {
			Department objBO = null;
			Iterator<Department> itr = listBo.iterator();
			while (itr.hasNext()) {
				objBO = (Department) itr.next();
				listMap.put(objBO.getId(), objBO.getName());
			}
		}
		return listMap;
	}

	public Map<Integer, String> getDesignationMap() throws Exception {

		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();
		Map<Integer, String> listMap = new HashMap<Integer, String>();
		List<Designation> listBo = txn.getDesignationFields();
		if (listBo != null) {
			Designation objBO = null;
			Iterator<Designation> itr = listBo.iterator();
			while (itr.hasNext()) {
				objBO = (Designation) itr.next();
				listMap.put(objBO.getId(), objBO.getName());
			}
		}
		return listMap;
	}

	public Map<Integer, String> getQualificationMap() throws Exception {

		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();
		Map<Integer, String> listMap = new HashMap<Integer, String>();
		List<EmpQualificationLevel> listBo = txn.getEmpQualidication();
		if (listBo != null) {
			EmpQualificationLevel objBO = null;
			Iterator<EmpQualificationLevel> itr = listBo.iterator();
			while (itr.hasNext()) {
				objBO = (EmpQualificationLevel) itr.next();
				listMap.put(objBO.getId(), objBO.getName());
			}
		}
		return listMap;
	}

	public Map<Integer, String> getFunctionalArea() throws Exception {

		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();
		Map<Integer, String> listMap = new HashMap<Integer, String>();
		List<EmpFunctionalArea> listBo = txn.getFunctionalArea();
		if (listBo != null) {
			EmpFunctionalArea objBO = null;
			Iterator<EmpFunctionalArea> itr = listBo.iterator();
			while (itr.hasNext()) {
				objBO = (EmpFunctionalArea) itr.next();
				listMap.put(objBO.getId(), objBO.getName());
			}
		}
		return listMap;
	}

	public EmpOnlineResume convertFormToBO(OnlineResumerSubmissionForm objform)
			throws Exception {
		EmpOnlineResume objBO = new EmpOnlineResume();
		objBO.setName(objform.getName() != null
				&& objform.getName().trim().length() > 0 ? objform.getName()
				: null);
		objBO.setAddressLine1(objform.getAddressLine1() != null
				&& objform.getAddressLine1().trim().length() > 0 ? objform
				.getAddressLine1() : null);

		objBO.setAddressLine2(objform.getAddressLine2() != null
				&& objform.getAddressLine2().trim().length() > 0 ? objform
				.getAddressLine2() : null);
		objBO.setAddressLine3(objform.getAddressLine3() != null
				&& objform.getAddressLine3().trim().length() > 0 ? objform
				.getAddressLine3() : null);
		if (objform.getNationalityId() != null
				&& objform.getNationalityId().trim().length() > 0) {
			Nationality nbo = new Nationality();
			nbo.setId(Integer.parseInt(objform.getNationalityId()));
			objBO.setNationality(nbo);

		}
		objBO.setCode(objform.getZipCode() != null
				&& objform.getZipCode().trim().length() > 0 ? objform
				.getZipCode() : null);

		objBO.setGender(objform.getGender() != null
				&& objform.getGender().trim().length() > 0 ? objform
				.getGender() : null);
		if (objform.getCountryId() != null
				&& objform.getCountryId().trim().length() > 0) {
			Country cobj = new Country();
			cobj.setId(Integer.parseInt(objform.getCountryId()));
			objBO.setCountry(cobj);
		}
		objBO.setMaritalStatus(objform.getMaritalStatus() != null
				&& objform.getMaritalStatus().trim().length() > 0 ? objform
				.getMaritalStatus() : null);

		objBO.setCity(objform.getCity() != null
				&& objform.getCity().trim().length() > 0 ? objform.getCity()
				: null);
		objBO
				.setDateOfBirth(objform.getDateOfBirth() != null
						&& objform.getDateOfBirth().trim().length() > 0
						&& CommonUtil.isValidDate(objform.getDateOfBirth()) ? CommonUtil
						.ConvertStringToSQLDate(objform.getDateOfBirth())
						: null);
		objBO.setPhNo1(objform.getPhone1() != null
				&& objform.getPhone1().trim().length() > 0 ? objform
				.getPhone1() : null);

		objBO.setPhNo2(objform.getPhone2() != null
				&& objform.getPhone2().trim().length() > 0 ? objform
				.getPhone2() : null);
		objBO.setPhNo3(objform.getPhone3() != null
				&& objform.getPhone3().trim().length() > 0 ? objform
				.getPhone3() : null);
		objBO.setAge(objform.getAge() != null
				&& objform.getAge().trim().length() > 0 ? Integer
				.parseInt(objform.getAge()) : null);

		objBO.setMobileNo1(objform.getMobPhone1() != null
				&& objform.getMobPhone1().trim().length() > 0 ? objform
				.getMobPhone1() : null);
		objBO.setMobileNo2(objform.getMobPhone2() != null
				&& objform.getMobPhone2().trim().length() > 0 ? objform
				.getMobPhone2() : null);
		objBO.setMobileNo3(objform.getMobPhone3() != null
				&& objform.getMobPhone3().trim().length() > 0 ? objform
				.getMobPhone3() : null);
		objBO.setEmail(objform.getEmail() != null
				&& objform.getEmail().trim().length() > 0 ? objform.getEmail()
				: null);
		if (objform.getJobType() != null
				&& objform.getJobType().trim().length() > 0) {
			EmpJobType jtobj = new EmpJobType();
			jtobj.setId(Integer.parseInt(objform.getJobType()));
			//objBO.setEmpJobType(jtobj);
		}
		objBO.setEmploymentStatus(objform.getEmploymentStatus() != null
				&& objform.getEmploymentStatus().trim().length() > 0 ? objform
				.getEmploymentStatus() : null);

		objBO
				.setExpectedSalaryLakhs(objform.getExpectedSalaryLack() != null
						&& objform.getExpectedSalaryLack().trim().length() > 0 ? Integer
						.parseInt(objform.getExpectedSalaryLack())
						: null);
		objBO
				.setExpectedSalaryThousands(objform
						.getExpectedSalaryThousands() != null
						&& objform.getExpectedSalaryThousands().trim().length() > 0 ? Integer
						.parseInt(objform.getExpectedSalaryThousands())
						: null);
		objBO.setDesiredPost(getValue(objform.getDesiredPost()));
		if (objform.getDepartmentAppliedFor() != null
				&& objform.getDepartmentAppliedFor().trim().length() > 0) {
			Department dobj = new Department();
			dobj.setId(Integer.parseInt(objform.getDepartmentAppliedFor()));
			objBO.setDepartment(dobj);

		}

		objBO
				.setDateOfJoining(objform.getDateOfJoining() != null
						&& objform.getDateOfJoining().trim().length() > 0
						&& CommonUtil.isValidDate(objform.getDateOfJoining()) ? CommonUtil
						.ConvertStringToSQLDate(objform.getDateOfJoining())
						: null);

		objBO.setInformationKnown(getValue(objform.getVacancyType()));
		objBO.setRecommendedBy(getValue(objform.getRecommendedBy()));

		if (objform.getPhoto() != null
				&& objform.getPhoto().getFileData() != null
				&& objform.getPhoto().getFileName() != null
				&& !objform.getPhoto().getFileName().isEmpty()
				&& objform.getPhoto().getContentType() != null
				&& !objform.getPhoto().getContentType().isEmpty()) {
			objBO.setEmpPhoto(objform.getPhoto().getFileData());
		}
		if (objform.getListOfEdicationDetails() != null
				&& objform.getListOfEdicationDetails().size() > 0) {
			List<EdicationDetailsTO> eDetails = objform
					.getListOfEdicationDetails();
			Set<EmpEducationDetails> eduDetailSet = new HashSet<EmpEducationDetails>();
			EmpEducationDetails objEDBO = null;
			for (EdicationDetailsTO objTO : eDetails) {
				objEDBO = new EmpEducationDetails();
				if (objTO.getId() != 0 && objTO.getId() > 0) {
					EmpQualificationLevel objQL = new EmpQualificationLevel();
					objQL.setId(objTO.getId());
					objEDBO.setEmpQualificationLevel(objQL);
				}
				objEDBO.setCourseName(getValue(objTO.getCourse()));
//				objEDBO.setCourseName(getValue(objTO.getCourseName()));
				objEDBO.setPassingYear(objTO.getYearOfPassing());

				objEDBO.setGrade(getValue(objTO.getGrade()));
				objEDBO.setNameOfInstitute(getValue(objTO
						.getInstituteUniversity()));
				objEDBO.setCreatedBy(objform.getUserId());
				objEDBO.setCreatedDate(new Date());
				objEDBO.setModifiedBy(objform.getUserId());
				objEDBO.setLastModifiedDate(new Date());
				objEDBO.setIsActive(true);
				objEDBO.setEmpOnlineResume(objBO);
				if((objEDBO.getCourseName()!=null && !objEDBO.getCourseName().isEmpty() )|| (objEDBO.getGrade()!=null && !objEDBO.getGrade().isEmpty()) || (objEDBO.getNameOfInstitute()!=null && !objEDBO.getNameOfInstitute().isEmpty()))
				eduDetailSet.add(objEDBO);

			}
			objBO.setEducationDetailsSet(eduDetailSet);
		}
		if (objform.getListOfAchievements() != null
				&& objform.getListOfAchievements().size() > 0) {
			List<AchievementsTO> listAchievements = objform
					.getListOfAchievements();
			Set<EmpAcheivement> acheivementSet = new HashSet<EmpAcheivement>();
			EmpAcheivement obja = null;
			for (AchievementsTO aTO : listAchievements) {
				if(aTO.getAchievements()!=null && !aTO.getAchievements().isEmpty() && aTO.getDetails()!=null && !aTO.getDetails().isEmpty()){
					obja = new EmpAcheivement();
					obja.setAcheivementName(getValue(aTO.getAchievements()));
					obja.setDetails(getValue(aTO.getDetails()));
					obja.setCreatedBy(objform.getUserId());
					obja.setCreatedDate(new Date());
					obja.setModifiedBy(objform.getUserId());
					obja.setLastModifiedDate(new Date());
					obja.setIsActive(true);
					obja.setEmpOnlineResume(objBO);
					acheivementSet.add(obja);
				}
			}
			objBO.setAcheivementSet(acheivementSet);
		}

		if (objform.getListOfProfessionalExperience() != null
				&& objform.getListOfProfessionalExperience().size() > 0) {
			List<ProfessionalExperienceTO> listOfProExp = objform
					.getListOfProfessionalExperience();
			Set<EmpPreviousOrg> previousOrgSet = new HashSet<EmpPreviousOrg>();
			EmpPreviousOrg pOBO = null;
			for (ProfessionalExperienceTO proExpTO : listOfProExp) {
				pOBO = new EmpPreviousOrg();

				if (proExpTO.getCurrentlyWorking() != null
						&& proExpTO.getCurrentlyWorking().trim().length() > 0) {
					if (proExpTO.getCurrentlyWorking().contains("Yes")) {
						pOBO.setIsCurrentlyWorking(true);
					} else {
						pOBO.setIsCurrentlyWorking(false);
					}
				}
				pOBO
						.setTeachingExpYears(proExpTO
								.getTeachingExperienceYear() != null
								&& proExpTO.getTeachingExperienceYear().trim()
										.length() > 0 ? Integer
								.parseInt(proExpTO.getTeachingExperienceYear())
								: null);

				pOBO
						.setTeachingExpMonths(proExpTO
								.getTeachingExperienceMonth() != null
								&& proExpTO.getTeachingExperienceMonth().trim()
										.length() > 0 ? Integer
								.parseInt(proExpTO.getTeachingExperienceMonth())
								: null);

				if (proExpTO.getQualificationLevel() != null
						&& proExpTO.getQualificationLevel().trim().length() > 0) {
					EmpQualificationLevel objQLBO = new EmpQualificationLevel();
					objQLBO.setId(Integer.parseInt(proExpTO
							.getQualificationLevel()));
					pOBO.setEmpQualificationLevel(objQLBO);
				}
				pOBO
						.setIndustryExpYears(proExpTO
								.getIndustryExperienceYear() != null
								&& proExpTO.getIndustryExperienceYear().trim()
										.length() > 0 ? Integer
								.parseInt(proExpTO.getIndustryExperienceYear())
								: null);

				pOBO
						.setIndustryExpMonths(proExpTO
								.getIndustryExperienceMonth() != null
								&& proExpTO.getIndustryExperienceMonth().trim()
										.length() > 0 ? Integer
								.parseInt(proExpTO.getIndustryExperienceMonth())
								: null);

				if (proExpTO.getEducation() != null
						&& proExpTO.getEducation().trim().length() > 0) {
					EmpEducationMaster objEBO = new EmpEducationMaster();
					objEBO.setId(Integer.parseInt(proExpTO.getEducation()));
					pOBO.setEmpEducationMaster(objEBO);
				}

				pOBO
						.setTotalExpYears(proExpTO.getTotalExperienceYear() != null
								&& proExpTO.getTotalExperienceYear().trim()
										.length() > 0 ? Integer
								.parseInt(proExpTO.getTotalExperienceYear())
								: null);
				pOBO
						.setTotalExpMonths(proExpTO.getTotalExperienceMonth() != null
								&& proExpTO.getTotalExperienceMonth().trim()
										.length() > 0 ? Integer
								.parseInt(proExpTO.getTotalExperienceMonth())
								: null);
				if (proExpTO.getFunctionalArea() != null
						&& proExpTO.getFunctionalArea().trim().length() > 0) {
					EmpFunctionalArea objfaBO = new EmpFunctionalArea();
					objfaBO.setId(Integer
							.parseInt(proExpTO.getFunctionalArea()));
					pOBO.setEmpFunctionalArea(objfaBO);
				}

				pOBO.setCurrentDesignation(getValue(proExpTO
						.getCurrentDesignation()));
				pOBO.setCurrentOrganisation(getValue(proExpTO
						.getCurrentOrganisation()));
				pOBO
						.setCurrentSalaryLakhs(proExpTO.getCurrentSalaryLack() != null
								&& proExpTO.getCurrentSalaryLack().trim()
										.length() > 0 ? Integer
								.parseInt(proExpTO.getCurrentSalaryLack())
								: null);
				pOBO
				.setCurrentSalaryThousands(proExpTO.getCurrentSalaryThosound() != null
						&& proExpTO.getCurrentSalaryThosound().trim()
								.length() > 0 ? Integer
						.parseInt(proExpTO.getCurrentSalaryThosound())
						: null);
				pOBO.setPreviousOrgName(getValue(proExpTO.getPreviousOrganisation()));
				pOBO.setCreatedBy(objform.getUserId());
				pOBO.setCreatedDate(new Date());
				pOBO.setModifiedBy(objform.getUserId());
				pOBO.setLastModifiedDate(new Date());
				pOBO.setIsActive(true);
				pOBO.setEmpOnlineResume(objBO);
				previousOrgSet.add(pOBO);
			}
			objBO.setPreviousOrgSet(previousOrgSet);
		}
		objBO.setCreatedBy(objform.getUserId());
		objBO.setCreatedDate(new Date());
		objBO.setModifiedBy(objform.getUserId());
		objBO.setLastModifiedDate(new Date());
		objBO.setIsActive(true);
		if (objform.getState() != null
				&& objform.getState().trim().length() > 0) {
			State sobj = new State();
			sobj.setId(Integer.parseInt(objform.getState()));
			objBO.setState(sobj);
		}
		return objBO;
	}

	private String getValue(String value) {
		if (value != null && value.trim().length() > 0) {
			return value;
		} else {
			return null;
		}

	}
}
