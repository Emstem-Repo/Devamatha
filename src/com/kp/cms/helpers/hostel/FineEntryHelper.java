package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.hostel.FineCategoryBo;
import com.kp.cms.bo.hostel.FineEntryBo;
import com.kp.cms.bo.hostel.HostelVisitorsInfoBo;
import com.kp.cms.forms.hostel.FineEntryForm;
import com.kp.cms.forms.hostel.HostelVisitorsInfoForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.hostel.FineCategoryTo;
import com.kp.cms.to.hostel.FineEntryTo;
import com.kp.cms.transactions.hostel.IFineEntryTransaction;
import com.kp.cms.transactionsimpl.hostel.FineEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class FineEntryHelper {
	public static volatile FineEntryHelper fineEntryHelper = null;
	IFineEntryTransaction transaction=FineEntryTransactionImpl.getInstance();
	private static Log log = LogFactory.getLog(FineEntryHelper.class);
	public static FineEntryHelper getInstance() {
		if (fineEntryHelper == null) {
			fineEntryHelper = new FineEntryHelper();
			return fineEntryHelper;
		}
		return fineEntryHelper;
	}
	/**
	 * convert fineEntryForm to FineEntryBo
	 */
	public FineEntryBo convertFormToBo(FineEntryForm fineEntryForm,String add)throws Exception{
		FineEntryBo fineEntryBo=null;
			if(add.equalsIgnoreCase("add")){
				fineEntryBo=new FineEntryBo();
				HlAdmissionBo hlAdmissionBo=new HlAdmissionBo();
				hlAdmissionBo.setId(Integer.parseInt(fineEntryForm.getHlAdminId()));
				fineEntryBo.setHlAdmissionId(hlAdmissionBo);
				fineEntryBo.setAmount(fineEntryForm.getAmount());
				if(fineEntryForm.getRemarks()!=null && !fineEntryForm.getRemarks().isEmpty()){
					fineEntryBo.setRemarks(fineEntryForm.getRemarks());
				}
				FineCategoryBo fineCategoryBo=new FineCategoryBo();
				fineCategoryBo.setId(Integer.parseInt(fineEntryForm.getCategoryId()));
				fineEntryBo.setFineCategoryId(fineCategoryBo);
				fineEntryBo.setPaid(false);
				fineEntryBo.setDate(new Date());
				fineEntryBo.setCreatedDate(new Date());
				fineEntryBo.setLastModifiedDate(new Date());
				fineEntryBo.setCreatedBy(fineEntryForm.getUserId());
				fineEntryBo.setModifiedBy(fineEntryForm.getUserId());
				fineEntryBo.setIsActive(true);
			}else if(add.equalsIgnoreCase("update")){
				fineEntryBo=transaction.getFineEntryById(fineEntryForm.getId());
				fineEntryBo.setAmount(fineEntryForm.getAmount());
				if(fineEntryForm.getRemarks()!=null && !fineEntryForm.getRemarks().isEmpty()){
					fineEntryBo.setRemarks(fineEntryForm.getRemarks());
				}
				FineCategoryBo fineCategoryBo=new FineCategoryBo();
				fineCategoryBo.setId(Integer.parseInt(fineEntryForm.getCategoryId()));
				fineEntryBo.setFineCategoryId(fineCategoryBo);
				fineEntryBo.setLastModifiedDate(new Date());
				fineEntryBo.setModifiedBy(fineEntryForm.getUserId());
			}
		return fineEntryBo;
	}
	/**
	 * convert BoList to ToList
	 * @param fineCategoryBos
	 * @return
	 * @throws Exception
	 */
	public List<FineEntryTo> ConvertBosListToTosList(List<FineEntryBo> fineEntryBos)throws Exception{
		List<FineEntryTo> fineEntrysList=new ArrayList<FineEntryTo>();
		FineEntryTo fineEntryTo=null;
		FineEntryBo fineEntryBo=null;
		Iterator<FineEntryBo> iterator=fineEntryBos.iterator();
		while (iterator.hasNext()) {
			fineEntryTo=new FineEntryTo();
			fineEntryBo = (FineEntryBo) iterator.next();
			fineEntryTo.setId(fineEntryBo.getId());
			fineEntryTo.setCategory(fineEntryBo.getFineCategoryId().getName());
			fineEntryTo.setAmount(fineEntryBo.getAmount());
			fineEntryTo.setDate(CommonUtil.formatDates(fineEntryBo.getDate()));
			fineEntryTo.setPaid(fineEntryBo.getPaid());
			fineEntryTo.setRegisterNo(fineEntryBo.getHlAdmissionId().getStudentId().getRegisterNo());
			fineEntrysList.add(fineEntryTo);
			
		}
		return fineEntrysList;
	}
	/**
	 * @param fineEntryForm
	 * @param fineEntryBo
	 * @throws Exception
	 */
	public void setBotoForm(FineEntryForm fineEntryForm,FineEntryBo fineEntryBo)throws Exception{
		if(fineEntryBo!=null){
			fineEntryForm.setHostelId(String.valueOf(fineEntryBo.getHlAdmissionId().getHostelId().getId()));
			fineEntryForm.setAcademicYear(fineEntryBo.getHlAdmissionId().getAcademicYear());
			fineEntryForm.setRegNo(fineEntryBo.getHlAdmissionId().getStudentId().getRegisterNo());
			fineEntryForm.setCategoryId(String.valueOf(fineEntryBo.getFineCategoryId().getId()));
			fineEntryForm.setCategoryName(fineEntryBo.getFineCategoryId().getName());
			if(fineEntryBo.getRemarks()!=null && !fineEntryBo.getRemarks().isEmpty()){
				fineEntryForm.setRemarks(fineEntryBo.getRemarks());
			}else{
				fineEntryForm.setRemarks(null);
			}
			fineEntryForm.setAmount(fineEntryBo.getAmount());
			String regNo=fineEntryBo.getHlAdmissionId().getStudentId().getRegisterNo();
			String academicYear=fineEntryBo.getHlAdmissionId().getAcademicYear();
			getStudentDetails(fineEntryForm, regNo, academicYear);
		}
		
	}
	/**
	 * @param fineEntryForm
	 * @param regNo
	 * @param academicYear
	 * @throws Exception
	 */
	public void getStudentDetails(FineEntryForm fineEntryForm,String regNo,String academicYear)throws Exception{
		HlAdmissionBo hlAdmissionBo=CommonAjaxHandler.getInstance().getStudentDetailsForVisitors(academicYear,regNo);
		if(hlAdmissionBo!=null)
		{
				fineEntryForm.setStudentName(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
				if(hlAdmissionBo.getCourseId()!=null){
					fineEntryForm.setStudentCourse(hlAdmissionBo.getCourseId().getName());
				}else {
					fineEntryForm.setStudentCourse(null);
				}
				if(hlAdmissionBo.getRoomId()!=null){
					fineEntryForm.setStudentRoom(hlAdmissionBo.getRoomId().getName());
					fineEntryForm.setStudentBlock(hlAdmissionBo.getRoomId().getHlBlock().getName());
					fineEntryForm.setStudentUnit(hlAdmissionBo.getRoomId().getHlUnit().getName());
				}else{
					fineEntryForm.setStudentRoom(null);
					fineEntryForm.setStudentBlock(null);
					fineEntryForm.setStudentUnit(null);
				}
				if(hlAdmissionBo.getBedId()!=null){
					fineEntryForm.setStudentBed(hlAdmissionBo.getBedId().getBedNo());
				}else {
					fineEntryForm.setStudentBed(null);
				}
				if(hlAdmissionBo.getHostelId().getName()!=null){
					fineEntryForm.setStudentHostel(hlAdmissionBo.getHostelId().getName());
				}else{
					fineEntryForm.setStudentHostel(null);
				}
				/* code added by sudhir*/
		}else {
			fineEntryForm.setStudentName(null);
			fineEntryForm.setStudentCourse(null);
			fineEntryForm.setStudentRoom(null);
			fineEntryForm.setStudentBlock(null);
			fineEntryForm.setStudentUnit(null);
			fineEntryForm.setStudentBed(null);
			fineEntryForm.setStudentHostel(null);
		}
	}
}
