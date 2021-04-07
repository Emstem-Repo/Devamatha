package com.kp.cms.transactionsimpl.hostel;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ApplicantFeedback;
import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.bo.admin.CCGroup;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.EmpAgeofRetirement;
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.EmpFunctionalArea;
import com.kp.cms.bo.admin.EmpIndustryType;
import com.kp.cms.bo.admin.EmpJobType;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.EmpWorkType;
import com.kp.cms.bo.admin.EmployeeCategory;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.HlComplaintType;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlLeaveType;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.InterviewStatus;
import com.kp.cms.bo.admin.InvCampus;
import com.kp.cms.bo.admin.InvCompany;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvUom;
import com.kp.cms.bo.admin.LeaveType;
import com.kp.cms.bo.admin.MeritSet;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.Prerequisite;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.Region;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.Sports;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.exam.IExamGenBO;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.bo.hostel.FineCategoryBo;
import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.LocationBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.FineCategoryForm;
import com.kp.cms.transactions.hostel.IFineCategoryTransaction;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.HibernateUtil;

public class FineCategoryTransactionImpl implements IFineCategoryTransaction{
	public static volatile FineCategoryTransactionImpl fineCategoryTransactionImpl = null;
	private static Log log = LogFactory.getLog(FineCategoryTransactionImpl.class);

public static FineCategoryTransactionImpl getInstance() {
		if (fineCategoryTransactionImpl == null) {
			fineCategoryTransactionImpl = new FineCategoryTransactionImpl();
			return fineCategoryTransactionImpl;
		}
		return fineCategoryTransactionImpl;
	}

	@Override
public boolean addFineCategoryDetails(FineCategoryBo fineCategoryBo,
			String mode) throws Exception {
		log.debug("impl: inside addFineCategoryDetails");
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(fineCategoryBo);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(fineCategoryBo);
			}
			tx.commit();
			session.flush();
			// session.close();
			log.debug("impl: leaving addFineCategoryDetails");
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;}

	@Override
public List<FineCategoryBo> getFineCategory() throws Exception {
		log.debug("impl: inside getFineCategory");
	Session session = null;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from FineCategoryBo a where a.isActive=1");
		List<FineCategoryBo> list = query.list();
		session.flush();
		return list;
	} catch (Exception e) {
		log.error("Error during getting Admitted Through...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
}

	@Override
public FineCategoryBo isFineCategoryDuplcated(FineCategoryBo oldfineCategoryBo)
			throws Exception {
		log.debug("impl: inside isApplicantFeedbackDuplcated");
		Session session = null;
		FineCategoryBo fineCategoryBo;
		try {
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from FineCategoryBo a where name = :name");
		query.setString("name", oldfineCategoryBo.getName());
		fineCategoryBo = (FineCategoryBo) query.uniqueResult();
		session.flush();
		log.debug("impl: leaving isCourseSchemeDuplcated");
		} catch (Exception e) {
		log.error("Error during duplcation checking..." + e);
		session.flush();
		throw new ApplicationException(e);
		}
		return fineCategoryBo;
}

	@Override
	public boolean deleteFineCategory(int id, Boolean activate,FineCategoryForm fineCategoryForm) throws Exception {
		log.debug("impl: inside deleteSingleFieldMaster");
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				FineCategoryBo fineCategoryBo = (FineCategoryBo) session.get(FineCategoryBo.class, id);
				if (activate) {
					fineCategoryBo.setIsActive(true);
				} else {
					fineCategoryBo.setIsActive(false);
				}
				fineCategoryBo.setModifiedBy(fineCategoryForm.getUserId());
				fineCategoryBo.setLastModifiedDate(new Date());
				session.update(fineCategoryBo);
			tx.commit();
			session.flush();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during deleting single master data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during deleting single master data..." + e);
			throw new ApplicationException(e);
		}
		log.debug("impl: leaving deleteSingleFieldMaster");
		return result;
	}

	@Override
	public FineCategoryBo getFineCategory(int id) throws Exception {
		log.debug("impl: inside getFineCategory");
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from FineCategoryBo a where a.isActive=1 and a.id="+id);
			FineCategoryBo fineCategoryBo=(FineCategoryBo)query.uniqueResult();
			session.flush();
			return fineCategoryBo;
		} catch (Exception e) {
			log.error("Error during getting Admitted Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}
}
