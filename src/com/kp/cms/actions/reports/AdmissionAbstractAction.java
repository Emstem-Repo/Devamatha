package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.AdmissionAbstractForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.AdmissionAbstractHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.AdmAbstractCatgMapTO;
import com.kp.cms.to.reports.AdmissionAbstractTO;

@SuppressWarnings("deprecation")
public class AdmissionAbstractAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(AdmissionAbstractAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdmissionAbstract(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Entered initAdmissionAbstract");
		setRequiredDataToForm(request);
		HttpSession session = request.getSession(false);
		session.removeAttribute("finalStudentList");
		log.info("Exit initAdmissionAbstract");
		return mapping.findForward(CMSConstants.ADMISSION_ABSTRACT_REPORT_SEARCH);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchAdmAbstract(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("inside searchAdmAbstract");
		AdmissionAbstractForm admAbstractForm = (AdmissionAbstractForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("finalStudentList")==null){
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = admAbstractForm.validate(mapping, request);
			try {
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					setRequiredDataToForm(request);
					return mapping.findForward(CMSConstants.ADMISSION_ABSTRACT_REPORT_SEARCH);
				}		
				if(admAbstractForm.getYear()!= null && !admAbstractForm.getYear().trim().isEmpty()){
					int newYear = Integer.parseInt(admAbstractForm.getYear())+ 1; 
					admAbstractForm.setAcademicYear(admAbstractForm.getYear() + "-" +  (Integer.toString(newYear).substring(2, 4)));
				}
				
				List<CasteTO> castList = CasteHandler.getInstance().getCastes();
				Map<Integer, AdmissionAbstractTO> admAbstractMap = AdmissionAbstractHandler.getInstance().getAdmissionAbstract(admAbstractForm);
	
				if (!admAbstractMap.isEmpty()) {
					List<AdmissionAbstractTO> finalList = new ArrayList<AdmissionAbstractTO>();
					finalList.addAll(admAbstractMap.values());
					//assigning empty categories. if there no student we have to assign the category as nil. this we can take only from the master table
					if(finalList != null && !finalList.isEmpty()){
						Iterator<AdmissionAbstractTO> finalListItr = finalList.iterator();
						
						while (finalListItr.hasNext()) {
							AdmissionAbstractTO object = (AdmissionAbstractTO) finalListItr.next();
							List<AdmAbstractCatgMapTO> categoryList = new ArrayList<AdmAbstractCatgMapTO>();
							
							Iterator<CasteTO> categoryListItr = castList.iterator();
							
							while (categoryListItr.hasNext()) {
								CasteTO casteTO = (CasteTO) categoryListItr.next();
								
								if(object.getCategoryMap().containsKey(casteTO.getCasteId())){
									categoryList.add(object.getCategoryMap().get(casteTO.getCasteId()));
								}else{
									AdmAbstractCatgMapTO abstractCatgMapTO = new AdmAbstractCatgMapTO();
									
									abstractCatgMapTO.setCategoryName(casteTO.getCasteName());
									abstractCatgMapTO.setNoOfStudents(0);
									categoryList.add(abstractCatgMapTO);
								}
							}
							Collections.sort(categoryList);
							object.setAdmCatgList(categoryList);
						}
					}
	
					Collections.sort(finalList);
	
					List<Integer> templist = new ArrayList<Integer>();
					List<Integer> grTotaltemplist = new ArrayList<Integer>();
					
					for(int i = 1; i<= castList.size();i++){
						templist.add(0);
						grTotaltemplist.add(0);
					}
					Iterator<AdmissionAbstractTO> itr = finalList.iterator();
					AdmissionAbstractTO admissionAbstractTO = new AdmissionAbstractTO();
					List<AdmissionAbstractTO> finalAdmList = new ArrayList<AdmissionAbstractTO>();
					AdmissionAbstractTO tempAdmissionAbstractTO = new AdmissionAbstractTO();
					AdmAbstractCatgMapTO tempAbstractCatgMapTO = new AdmAbstractCatgMapTO();
					String programCode = "";
					List<AdmAbstractCatgMapTO> tempAdmCatgList = new ArrayList<AdmAbstractCatgMapTO>();
					
					int len = finalList.size();
					int j = 0;
					int karTot = 0;
					int otherKarTot = 0;
					int otherIndTot = 0;
					int boysTot = 0;
					int girlsTot = 0;
	
					int grKarTot = 0;
					int grOtherKarTot = 0;
					int grOtherIndTot = 0;
					int grBoysTot = 0;
					int grGirlsTot = 0;
					//finalList is iterating here and assigning another list to print sub toal and grand total
					while(j  < len){
						if(j == 0){
							admissionAbstractTO = itr.next();
						}
						karTot = 0;
						otherKarTot = 0;
						otherIndTot = 0;
						boysTot = 0;
						girlsTot = 0;
						int groupCount = 0;
						programCode = admissionAbstractTO.getProgramCode();
						admissionAbstractTO.setTempprogramCode(programCode);
						while(programCode.equalsIgnoreCase(admissionAbstractTO.getTempprogramCode())){
							if(groupCount != 0){
								admissionAbstractTO.setProgramCode(null);
							}
							finalAdmList.add(admissionAbstractTO);
							Iterator<AdmAbstractCatgMapTO> catgItr = admissionAbstractTO.getAdmCatgList().iterator();
							karTot = karTot + admissionAbstractTO.getKarStudents();
							otherKarTot = otherKarTot + admissionAbstractTO.getOtherThanKar();
							otherIndTot = otherIndTot + admissionAbstractTO.getOtherThanInd();
							boysTot = boysTot + admissionAbstractTO.getBoysCount();
							girlsTot = girlsTot + admissionAbstractTO.getGirlsCount();
							int i = 0;
							while(catgItr.hasNext()){ 
								AdmAbstractCatgMapTO abstractCatgMapTO = catgItr.next();
								templist.set(i, templist.get(i) + abstractCatgMapTO.getNoOfStudents()); //putting category total to templist based on the program for sub total
								grTotaltemplist.set(i, grTotaltemplist.get(i) + abstractCatgMapTO.getNoOfStudents()); //putting category total to grTotaltemplist based on the program for grand total
								
								i++;
							}
							admissionAbstractTO = new AdmissionAbstractTO();
							if(j+1 != len){
								admissionAbstractTO = itr.next();
								admissionAbstractTO.setTempprogramCode(admissionAbstractTO.getProgramCode());
							}
							j++;
							groupCount++;
						}
						tempAdmCatgList = new ArrayList<AdmAbstractCatgMapTO>();
						for(int l = 1; l<= templist.size();l++){
							tempAbstractCatgMapTO = new AdmAbstractCatgMapTO();
							tempAbstractCatgMapTO.setCategoryName("");
							tempAbstractCatgMapTO.setNoOfStudents(templist.get(l-1));
							tempAdmCatgList.add(tempAbstractCatgMapTO);
						}
						tempAdmissionAbstractTO = new AdmissionAbstractTO();
						tempAdmissionAbstractTO.setAdmCatgList(tempAdmCatgList);
						tempAdmissionAbstractTO.setKarStudents(karTot);
						tempAdmissionAbstractTO.setOtherThanKar(otherKarTot);
						tempAdmissionAbstractTO.setOtherThanInd(otherIndTot);
						tempAdmissionAbstractTO.setBoysCount(boysTot);
						tempAdmissionAbstractTO.setGirlsCount(girlsTot);
						tempAdmissionAbstractTO.setTotal(boysTot + girlsTot);
						tempAdmissionAbstractTO.setProgramCode("Sub Total");
						if(admAbstractForm.getProgramId() == null || admAbstractForm.getProgramId().trim().isEmpty()){
							if( j < len){
								finalAdmList.add(tempAdmissionAbstractTO);
							}
						}
						
						grKarTot = grKarTot + karTot;
						grOtherKarTot = grOtherKarTot + otherKarTot;
						grOtherIndTot = grOtherIndTot + otherIndTot;
						grBoysTot = grBoysTot + boysTot;
						grGirlsTot = grGirlsTot + girlsTot;
						
						
						for(int i = 1; i<= castList.size();i++){
							templist.set(i- 1, 0);
						}
						
					}
					
					tempAdmCatgList = new ArrayList<AdmAbstractCatgMapTO>();
					for(int l = 1; l<= grTotaltemplist.size();l++){
						tempAbstractCatgMapTO = new AdmAbstractCatgMapTO();
						tempAbstractCatgMapTO.setCategoryName("");
						tempAbstractCatgMapTO.setNoOfStudents(grTotaltemplist.get(l-1));
						tempAdmCatgList.add(tempAbstractCatgMapTO);
					}
					tempAdmissionAbstractTO = new AdmissionAbstractTO();
					tempAdmissionAbstractTO.setAdmCatgList(tempAdmCatgList);
					tempAdmissionAbstractTO.setKarStudents(grKarTot);
					tempAdmissionAbstractTO.setOtherThanKar(grOtherKarTot);
					tempAdmissionAbstractTO.setOtherThanInd(grOtherIndTot);
					tempAdmissionAbstractTO.setBoysCount(grBoysTot);
					tempAdmissionAbstractTO.setGirlsCount(grGirlsTot);
					tempAdmissionAbstractTO.setTotal(grBoysTot + grGirlsTot);
					tempAdmissionAbstractTO.setProgramCode("Grand Total");
					if(finalAdmList!= null && finalAdmList.size() > 0){
						finalAdmList.add(tempAdmissionAbstractTO);
					}
					session.setAttribute("finalStudentList",finalAdmList);
				}			
			}catch (BusinessException businessException) {
				log.info("Exception searchCancelledAdmissions");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				admAbstractForm.setErrorMessage(msg);
				admAbstractForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			admAbstractForm.setProgramTypeId(null);
			admAbstractForm.setProgramId(null);
		}	
		log.debug("exit searchSubjectGroup");
		return mapping.findForward(CMSConstants.ADMISSION_ABSTRACT_REPORT_SUBMIT);
	}	

	/**
	 * This method sets the required data to the form
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(HttpServletRequest request) throws Exception{
		log.info("start setRequiredDataToForm");	
	    
		//setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);

		log.info("Exit setRequiredDataToForm");	
	}	
}