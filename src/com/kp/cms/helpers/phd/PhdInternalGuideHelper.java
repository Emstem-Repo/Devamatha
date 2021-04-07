package com.kp.cms.helpers.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.PhdInternalGuideBo;
import com.kp.cms.forms.phd.PhdInternalGuideForm;
import com.kp.cms.to.phd.PhdInternalGuideTO;
import com.kp.cms.utilities.CommonUtil;

public class PhdInternalGuideHelper {
	private static final Log log = LogFactory.getLog(PhdInternalGuideHelper.class);
	public static volatile PhdInternalGuideHelper examCceFactorHelper = null;

	public static PhdInternalGuideHelper getInstance() {
		if (examCceFactorHelper == null) {
			examCceFactorHelper = new PhdInternalGuideHelper();
		}
		return examCceFactorHelper;
	}

	/**
	 * @param internalBOs
	 * @return
	 */
	public List<PhdInternalGuideTO> convertBOsTo(List<PhdInternalGuideBo> internalBOs) {
		List<PhdInternalGuideTO> guideTOList=new ArrayList<PhdInternalGuideTO>();
	    Iterator<PhdInternalGuideBo> iterator=internalBOs.iterator();
	    while(iterator.hasNext())
	    {
	    	PhdInternalGuideBo guideBO=(PhdInternalGuideBo) iterator.next();
	    	PhdInternalGuideTO guideTO=new PhdInternalGuideTO();
	    	if(guideBO.getId()>0)
	    	guideTO.setId(guideBO.getId());
	    	if(guideBO.getEmployeeId().getId()>0)
	    	guideTO.setEmployeeId(Integer.toString(guideBO.getEmployeeId().getId()));
	    	if(guideBO.getEmployeeId().getFirstName()!=null && !guideBO.getEmployeeId().getFirstName().isEmpty())
	    	guideTO.setEmployeeName(guideBO.getEmployeeId().getFirstName());
	    	if(guideBO.getDisciplineId().getId()>0)
	    	guideTO.setDisciplineId(Integer.toString(guideBO.getDisciplineId().getId()));
	    	if(guideBO.getDisciplineId().getName()!=null && !guideBO.getDisciplineId().getName().isEmpty())
	    	guideTO.setDisciplineName(guideBO.getDisciplineId().getName());
	    	if(guideBO.getDateAward()!=null && !guideBO.getDateAward().toString().isEmpty())
	    	guideTO.setDateOfAward(CommonUtil.ConvertStringToDateFormat(guideBO.getDateAward().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	    	if(guideBO.getEmpanelmentNo()!=null)
	    	guideTO.setEmpanelmentNo(guideBO.getEmpanelmentNo());
	    	guideTO.setNoMphilScolars(guideBO.getNoMphilScolars());
	    	guideTO.setNoPhdScolars(guideBO.getNoPhdScolars());
	    	guideTO.setNoPhdScolarOutside(guideBO.getNoPhdScolarOutside());
	    	guideTOList.add(guideTO);
	    }
		return guideTOList;
     }

	/**
	 * @param objForm
	 * @return
	 */
	public PhdInternalGuideBo guideFormToBO(PhdInternalGuideForm objForm) {
		PhdInternalGuideBo bolist=new PhdInternalGuideBo();
		
		if(objForm.getId()>0){
			bolist.setId(objForm.getId());
		}
		Employee emp=new Employee();
		emp.setId(Integer.parseInt(objForm.getEmployeeId()));
		bolist.setEmployeeId(emp);
		DisciplineBo dici=new DisciplineBo();
		dici.setId(Integer.parseInt(objForm.getDisciplineId()));
		bolist.setDisciplineId(dici);
		if(objForm.getDateOfAward()!=null && !objForm.getDateOfAward().isEmpty()){
		bolist.setDateAward(CommonUtil.ConvertStringToDate(objForm.getDateOfAward()));
		}
		bolist.setEmpanelmentNo(objForm.getEmpanelmentNo());
		bolist.setNoMphilScolars(objForm.getNoMphilScolars());
		bolist.setNoPhdScolars(objForm.getNoPhdScolars());
		bolist.setNoPhdScolarOutside(objForm.getNoPhdScolarOutside());
		bolist.setCreatedBy(objForm.getUserId());
		bolist.setCreatedDate(new Date());
		bolist.setLastModifiedDate(new Date());
		bolist.setModifiedBy(objForm.getUserId());
		bolist.setIsActive(Boolean.valueOf(true));
		
		return bolist;
	}
	/**
	 * @param objForm
	 * @param guideBO
	 */
	public void setDataBoToForm(PhdInternalGuideForm objForm,PhdInternalGuideBo guideBO) {
    	if(guideBO.getId()>0)
		objForm.setId(guideBO.getId());
    	if(guideBO.getEmployeeId().getId()>0)
    	objForm.setEmployeeId(Integer.toString(guideBO.getEmployeeId().getId()));
    	if(guideBO.getDisciplineId().getId()>0)
    	objForm.setDisciplineId(Integer.toString(guideBO.getDisciplineId().getId()));
    	if(guideBO.getDateAward()!=null)
    	objForm.setDateOfAward(CommonUtil.ConvertStringToDateFormat(guideBO.getDateAward().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
    	if(guideBO.getEmpanelmentNo()!=null && !guideBO.getEmpanelmentNo().isEmpty())
    	objForm.setEmpanelmentNo(guideBO.getEmpanelmentNo());
    	objForm.setNoMphilScolars(guideBO.getNoMphilScolars());
    	objForm.setNoPhdScolars(guideBO.getNoPhdScolars());
    	objForm.setNoPhdScolarOutside(guideBO.getNoPhdScolarOutside());
    	if(guideBO.getEmployeeId().getDepartment().getId()>0)
    	objForm.setDepartmentId(Integer.toString(guideBO.getEmployeeId().getDepartment().getId()));
		
	}
	
}
