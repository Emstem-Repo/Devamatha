package com.kp.cms.handlers.attendance;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.attendance.ClassEntryForm;
import com.kp.cms.helpers.attendance.ClassesEntryHelper;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.transactions.attandance.IClassEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.ClassEntryTransImpl;

public class ClassEntryHandler {
	private static volatile ClassEntryHandler classEntryHandler = null;
	private static final Log log = LogFactory.getLog(ClassEntryHandler.class);

	private ClassEntryHandler() {
	}

	/**
	 * This method returns the single instance of this ClassEntryHandler.
	 * 
	 * @return
	 */
	public static ClassEntryHandler getInstance() {
		if (classEntryHandler == null) {
			classEntryHandler = new ClassEntryHandler();
		}
		return classEntryHandler;
	}

	/**
	 * This method returns all classes which is used in ui to display.
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ClassesTO> getAllClasses(int currentYear) throws Exception {
		log.debug("Handler : Entering getAllClasses ");
		IClassEntryTransaction classEntryTransaction = ClassEntryTransImpl
				.getInstance();
		List<ClassSchemewise> classEntry = classEntryTransaction
				.getAllClass(currentYear);
		List<ClassesTO> classEntryTos = ClassesEntryHelper.getInstance()
				.copyBosToTos(classEntry);
		log.debug("Handler : Leaving getAllClasses ");
		return classEntryTos;
	}

	/**
	 * This method adds new classEntry to database.
	 * 
	 * @param classEntryForm
	 * @return
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	public boolean addClassEntry(ClassEntryForm classEntryForm)
			throws ConstraintViolationException, Exception {
		log.debug("Handler : Entering addClass ");
		Classes classEntry = ClassesEntryHelper.getInstance().copyFormDataToBo(
				classEntryForm);
		IClassEntryTransaction classEntryTransaction = ClassEntryTransImpl
				.getInstance();
		boolean isAdded = false;
		isAdded = classEntryTransaction.addClass(classEntry);
		log.debug("Handler : Leaving addClass ");
		return isAdded;
	}

	/**
	 * This methods deletes the single class entry.
	 * 
	 * @param classEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteClassEntry(ClassEntryForm classEntryForm)
			throws Exception {
		log.debug("Handler : Entering deleteDetailedSubject ");
		Classes classEntry = new Classes();
		classEntry.setId(Integer.parseInt(classEntryForm.getId()));
		classEntry.setModifiedBy(classEntryForm.getUserId());
		classEntry.setLastModifiedDate(new Date());
		IClassEntryTransaction classEntryTransaction = ClassEntryTransImpl
				.getInstance();
		boolean isDeleted = false;
		isDeleted = classEntryTransaction.deleteClass(classEntry);
		log.debug("Handler : Leaving deleteDetailedSubject ");
		return isDeleted;
	}

	/**
	 * This methods updates the single classentry.
	 * 
	 * @param classEntryForm
	 * @return
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	public boolean updateClassEntry(ClassEntryForm classEntryForm)
			throws ConstraintViolationException, Exception {
		log.debug("Handler : Entering updateDetailedSubjects ");
		Classes classes = ClassesEntryHelper.getInstance().copyFormDataToBo(
				classEntryForm);
		IClassEntryTransaction classEntryTransaction = ClassEntryTransImpl
				.getInstance();
		boolean isAdded = false;
		isAdded = classEntryTransaction.updateClass(classes);
		log.debug("Handler : Leaving updateDetailedSubjects ");
		return isAdded;
	}

	/**
	 * This method activates the singles class entry which is in deleted status.
	 * 
	 * @param classEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean activateClassEntry(ClassEntryForm classEntryForm)
			throws Exception {
		log.debug("Handler : Entering activateDetailedSubject ");
		Classes classEntry = new Classes();
		classEntry.setId(Integer.parseInt(classEntryForm.getActivateId()));
		IClassEntryTransaction classEntryTransaction = ClassEntryTransImpl
				.getInstance();
		boolean isActivated = false;
		isActivated = classEntryTransaction.actiavateClass(classEntry);
		log.debug("Handler : Leaving activateDetailedSubject ");
		return isActivated;
	}

	/**
	 * This method check for duplicate entry in the database. This method throws
	 * exception based on the error.
	 * 
	 * @param classEntryForm
	 * @return
	 * @throws DuplicateException
	 * @throws ReActivateException
	 * @throws Exception
	 *//*
	public boolean checkForDuplicate(ClassEntryForm classEntryForm)
			throws DuplicateException, ReActivateException, Exception {
		log.debug("Handler : Entering checkForDuplicate ");
		IClassEntryTransaction classEntryTransaction = ClassEntryTransImpl
				.getInstance();
		List<ClassSchemewise> classEntry = classEntryTransaction
				.getClassesByIds();
		for (int i = 0; i < classEntry.size(); i++) {
			ClassSchemewise schemewise = classEntry.get(i);
			if (schemewise.getClasses().getName().equals(
					classEntryForm.getClassName())) {
				if ((classEntryForm.getId().equals("0")
						|| classEntryForm.getId().length() == 0) && 
						(schemewise.getCurriculumSchemeDuration()
								.getAcademicYear() == Integer
								.parseInt(classEntryForm.getYear().trim()))) {
					if (schemewise.getClasses().getIsActive() == true) {
						throw new DuplicateException("class");
					} else if (schemewise.getClasses().getIsActive() == false) {
						classEntryForm.setActivateId(String.valueOf(schemewise
								.getClasses().getId()));
						throw new ReActivateException();
					}
				}
			} else if ((schemewise.getClasses().getCourse().getId() == Integer
					.valueOf(classEntryForm.getCourseId().trim()))
					&& (schemewise.getCurriculumSchemeDuration()
							.getAcademicYear() == Integer
							.parseInt(classEntryForm.getYear().trim()))
					&& (schemewise.getCurriculumSchemeDuration()
							.getSemesterYearNo() == Integer
							.parseInt(classEntryForm.getSemesterNo().trim()))
					&& (schemewise.getClasses().getSectionName()
							.equals(classEntryForm.getSectionName().trim()))) {
				if (schemewise.getClasses().getIsActive() == true) {
					throw new DuplicateException("combinations");
				} else if (schemewise.getClasses().getIsActive() == false) {
					classEntryForm.setActivateId(String.valueOf(schemewise
							.getClasses().getId()));
					throw new ReActivateException();
				}
			}
		}
		log.debug("Handler : Leaving checkForDuplicate ");
		return true;
	}
*/
	/**
	 * This method will give boolean based on the class field duplicate.
	 * 
	 * @param classEntryForm
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public void checkDuplicateFields(ClassEntryForm classEntryForm)
			throws DuplicateException, Exception {
		IClassEntryTransaction classEntryTransaction = ClassEntryTransImpl
				.getInstance();
		List<ClassSchemewise> classEntry2 = classEntryTransaction
				.getClassesByClassName(classEntryForm.getClassName(),classEntryForm.getYear());
		if (!classEntry2.isEmpty()) {
			throw new DuplicateException("class");
		}
	}

	/**
	 * This method decides whether duplicate check required.
	 * 
	 * @param classEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean isDuplicateCheckRequired(ClassEntryForm classEntryForm)
			throws Exception {
		ClassesTO classTo = classEntryForm.getClassTo();
		if ((classEntryForm.getCourseId().equals(
				String.valueOf(classTo.getCourseTo().getId()))
				&& (classEntryForm.getYear().equalsIgnoreCase(classTo.getYear()
						.toString())) && (classEntryForm.getSectionName()
				.equalsIgnoreCase(classTo.getSectionName())))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method decides whether duplicate check required.
	 * 
	 * @param classEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean isDuplicateClassCheckRequired(ClassEntryForm classEntryForm)
			throws Exception {
		ClassesTO classTo = classEntryForm.getClassTo();
		if (classEntryForm.getClassName().equals(
				classTo.getClassName())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method will load the classEntry for edit.
	 * 
	 * @param classesEntryForm
	 * @throws Exception
	 */
	public void getClassdetails(ClassEntryForm classesEntryForm)
			throws Exception {
		log.debug("Handler : Entering getClassdetails ");
		IClassEntryTransaction classEntryTransaction = ClassEntryTransImpl
				.getInstance();
		ClassSchemewise classEntry = classEntryTransaction.getClassById(Integer
				.valueOf(classesEntryForm.getId()));
		classesEntryForm.setId(String.valueOf(classEntry.getClasses().getId()));
		classesEntryForm.setOriginalId(String.valueOf(classEntry.getClasses().getId()));
		classesEntryForm.setClassSchemewiseId(String.valueOf(classEntry.getId()));
		classesEntryForm.setOriginalClassSchemewiseId(String.valueOf(classEntry.getId()));
		classesEntryForm.setCourseId(String.valueOf(classEntry.getClasses().getCourse().getId()));
		classesEntryForm.setOriginalCourseId(String.valueOf(classEntry.getClasses().getCourse().getId()));
		classesEntryForm.setYear(classEntry.getCurriculumSchemeDuration().getAcademicYear().toString());
		classesEntryForm.setOriginalYear(classEntry.getCurriculumSchemeDuration().getAcademicYear().toString());
		classesEntryForm.setTermNo(String.valueOf(classEntry.getCurriculumSchemeDuration().getId()));
		classesEntryForm.setOriginalTermNo(String.valueOf(classEntry.getCurriculumSchemeDuration().getId()));
		classesEntryForm.setSectionName(classEntry.getClasses().getSectionName());
		classesEntryForm.setOriginalSectionName(classEntry.getClasses().getSectionName());
		classesEntryForm.setClassName(classEntry.getClasses().getName());
		classesEntryForm.setOriginalClassName(classEntry.getClasses().getName());
		classesEntryForm.setCourseGroupCodeId(classEntry.getClasses().getCourseGroupCodeId());
		classesEntryForm.setOriginalCourseGroupId(classEntry.getClasses().getCourseGroupCodeId());
		ClassesTO classTo = new ClassesTO();
		CourseTO courseTo = new CourseTO();
		courseTo.setId(classEntry.getClasses().getCourse().getId());
		classTo.setCourseTo(courseTo);
		classTo.setYear(classEntry.getCurriculumSchemeDuration()
				.getAcademicYear());
		classTo.setTermNo(classEntry.getCurriculumSchemeDuration().getId());
		classTo.setClassName(classEntry.getClasses().getName());
		classTo.setSectionName(classEntry.getClasses().getSectionName());
		classesEntryForm.setClassTo(classTo);

		log.debug("Handler : Leaving getClassdetails ");
		return;
	}
	/**
	 * This method check for duplicate entry in the database. This method throws
	 * exception based on the error.
	 * 
	 * @param classEntryForm
	 * @return
	 * @throws DuplicateException
	 * @throws ReActivateException
	 * @throws Exception
	 */
	public boolean checkForDuplicate(ClassEntryForm classEntryForm)
			throws DuplicateException, ReActivateException, Exception {
		log.debug("Handler : Entering checkForDuplicate ");
		IClassEntryTransaction classEntryTransaction = ClassEntryTransImpl.getInstance();
		String duplicateQuery=ClassesEntryHelper.getInstance().getQuery(classEntryForm);
		ClassSchemewise classSchemewise=classEntryTransaction.checkDuplicateThroughQuery(duplicateQuery);
		if(classSchemewise!=null){
			if(classSchemewise.getClasses().getIsActive()){
				if(classEntryForm.getSectionName()!=null && !classEntryForm.getSectionName().isEmpty()){
					throw new DuplicateException("combinations");
				}else{
					throw new DuplicateException("class");
				}
				
			}else{
				classEntryForm.setActivateId(String.valueOf(classSchemewise.getClasses().getId()));
				throw new ReActivateException();
			}
		}
		log.debug("Handler : Leaving checkForDuplicate ");
		return true;
	}
}