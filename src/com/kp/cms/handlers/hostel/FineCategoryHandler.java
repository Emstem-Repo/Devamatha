package com.kp.cms.handlers.hostel;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import com.kp.cms.bo.admin.Employee;
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
import com.kp.cms.bo.exam.ExamGenBO;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.bo.hostel.FineCategoryBo;
import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.LocationBo;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.SingleFieldMasterForm;
import com.kp.cms.forms.hostel.FineCategoryForm;
import com.kp.cms.helpers.admin.SingleFieldMasterHelper;
import com.kp.cms.helpers.hostel.FineCategoryHelper;
import com.kp.cms.to.hostel.FineCategoryTo;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.hostel.IFineCategoryTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.transactionsimpl.hostel.FineCategoryTransactionImpl;

public class FineCategoryHandler {
	public static volatile FineCategoryHandler fineCategoryHandler = null;
	private static Log log = LogFactory.getLog(FineCategoryHandler.class);
	IFineCategoryTransaction transaction=FineCategoryTransactionImpl.getInstance();
	public static FineCategoryHandler getInstance() {
		if (fineCategoryHandler == null) {
			fineCategoryHandler = new FineCategoryHandler();
			return fineCategoryHandler;
		}
		return fineCategoryHandler;
	}
	public List<FineCategoryTo> getFineCategoryList(FineCategoryForm fineCategoryForm)throws Exception{
		List<FineCategoryBo> fineCategoryBos = transaction.getFineCategory();
		List<FineCategoryTo> listFineCategoryTos=FineCategoryHelper.getInstance().ConvertBosListToTosList(fineCategoryBos);
		return listFineCategoryTos;
	}
	public boolean addFineCategory(FineCategoryForm fineCategoryForm,String mode)throws Exception{

		log.debug("inside addSingleFieldMaster");
		boolean isAdded = false;
		Boolean originalNotChanged = false;
		String name = "";
		String origianlValue = "";
		if (fineCategoryForm.getName() != null
				&& !fineCategoryForm.getName().isEmpty()) {
			name = fineCategoryForm.getName().trim();
		}
		if (fineCategoryForm.getOriginalValue() != null
				&& !fineCategoryForm.getOriginalValue().isEmpty()) {
			origianlValue = fineCategoryForm.getOriginalValue().trim();
		}
		FineCategoryBo fineCategoryBo=null;
		if (name.equalsIgnoreCase(origianlValue)) {
			originalNotChanged = true;
		}
		if (mode.equals("Add")) {
			originalNotChanged = false; // for add no need to check original
			// changed
		}
			if (mode.equalsIgnoreCase("Add")) {
				fineCategoryBo = FineCategoryHelper.getInstance().populateFormToFineCategoryBo(fineCategoryForm);
				fineCategoryBo = transaction.isFineCategoryDuplcated(fineCategoryBo);
				if (fineCategoryBo != null && fineCategoryBo.getIsActive()) {
					throw new DuplicateException();
				}else if (fineCategoryBo != null && !fineCategoryBo.getIsActive()) {
					fineCategoryForm.setReactivateid(fineCategoryBo.getId());
					throw new ReActivateException();
				}
				fineCategoryBo = FineCategoryHelper.getInstance().populateFormToFineCategoryBo(fineCategoryForm);
				isAdded = transaction.addFineCategoryDetails(fineCategoryBo, mode);
			}else{
				FineCategoryBo fineCategoryBo1=new FineCategoryBo();
				fineCategoryBo1.setName(fineCategoryForm.getName()) ;
				fineCategoryBo = transaction.isFineCategoryDuplcated(fineCategoryBo1);
				if (fineCategoryBo != null && fineCategoryBo.getIsActive() && fineCategoryBo.getId()!=fineCategoryForm.getId()) {
					throw new DuplicateException();
				}else if (fineCategoryBo != null && !fineCategoryBo.getIsActive()) {
					fineCategoryForm.setReactivateid(fineCategoryBo.getId());
					throw new ReActivateException();
				}else{
					fineCategoryBo=transaction.getFineCategory(fineCategoryForm.getId());
					fineCategoryBo.setId(fineCategoryForm.getId());
					fineCategoryBo.setName(fineCategoryForm.getName());
					fineCategoryBo.setAmount(fineCategoryForm.getAmount());
					fineCategoryBo.setLastModifiedDate(new Date());
					fineCategoryBo.setModifiedBy(fineCategoryForm.getUserId());
					isAdded = transaction.addFineCategoryDetails(fineCategoryBo, mode);
				}
			}
		return isAdded;
	}
	public boolean deleteFineCategory(int id, Boolean activate,FineCategoryForm fineCategoryForm)
			throws Exception {
		boolean result = transaction.deleteFineCategory(id, activate, fineCategoryForm);
		log.debug("Handler: deleteSingleFieldMaster");
		return result;
	}
	/**
	 * edit fine Category
	 */
	public void editFineCategory(int id,FineCategoryForm fineCategoryForm)throws Exception{
		FineCategoryBo fineCategoryBo=transaction.getFineCategory(id);
		if(fineCategoryBo!=null){
			fineCategoryForm.setName(fineCategoryBo.getName());
			fineCategoryForm.setAmount(fineCategoryBo.getAmount());
		}
	}
}
