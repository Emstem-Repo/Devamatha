package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admission.PrerequisitsYearMonth;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.PrerequisiteYearMonthForm;

import com.kp.cms.helpers.admin.AcademicYearHelper;
import com.kp.cms.helpers.admission.PrerequisiteYearMonthHelper;

import com.kp.cms.to.admin.AcademicYearTO;
import com.kp.cms.to.admission.PrerequisiteYearMonthTO;

import com.kp.cms.transactions.admission.IPrerequisitesYearMonth;
import com.kp.cms.transactionsimpl.admission.PrerequisiteYearMonthTransactionImpl;

import common.Logger;

public class PrerequisiteYearMonthHandler {


    private static final Logger log = Logger.getLogger(PrerequisiteYearMonthHandler.class);
    private static volatile PrerequisiteYearMonthHandler prerequisiteYearMonthHandler = null;
    IPrerequisitesYearMonth iPrerequisitesYearMonth;

    private PrerequisiteYearMonthHandler()
    {
        iPrerequisitesYearMonth = new PrerequisiteYearMonthTransactionImpl();
    }

    public static PrerequisiteYearMonthHandler getInstance()
    {
        if(prerequisiteYearMonthHandler == null)
        {
        	prerequisiteYearMonthHandler = new PrerequisiteYearMonthHandler();
        }
        return prerequisiteYearMonthHandler;
    }

    @SuppressWarnings("unchecked")
	public List<PrerequisiteYearMonthTO> getPrerequisiteYearMonthDetails()
        throws Exception
    {
        if(iPrerequisitesYearMonth != null)
        {
            List<PrerequisitsYearMonth> yearList = iPrerequisitesYearMonth.getPrerequisiteYearMonthDetails();
            if(yearList != null)
            {
                return PrerequisiteYearMonthHelper.getInstance().poupulateBOtoTO(yearList);
            }
        }
        log.info("End of getAcademicYearDetails() of PrerequisiteYearMonthHandler");
        return new ArrayList();
    }

    public PrerequisitsYearMonth getPrereqYear(String Year, String Month)
        throws Exception
    {
        if(iPrerequisitesYearMonth != null)
        {
           
            return iPrerequisitesYearMonth.getPrereqYear(Integer.parseInt(Year),Integer.parseInt(Month));
        } else
        {
            log.info("End of getAcademicYear of PrerequisiteYearMonthHandler");
            return new PrerequisitsYearMonth();
        }
    }

    public static int splitYear(String academicYearValue)
    {
        return (academicYearValue!= null && academicYearValue.trim().length() >= 0 ? Integer.parseInt(academicYearValue.substring(0, 4)):0);
    }

    public static String getacademicYear(int academicYear)
    {
        return Integer.toString(academicYear).concat("-").concat(Integer.toString(academicYear + 1));
    }

   
	public boolean addAcademicYear(PrerequisiteYearMonthTO prereqYearMonthTO, ActionErrors errors)
        throws Exception
    {
        PrerequisitsYearMonth prereqYearMonth = null;
        PrerequisiteYearMonthTO yearTO;
        List<PrerequisiteYearMonthTO> academicYearTOList = getPrerequisiteYearMonthDetails();
        Iterator<PrerequisiteYearMonthTO> academicYearTOListIterator = academicYearTOList.iterator();
        if(prereqYearMonthTO != null)
        {
            while(academicYearTOListIterator.hasNext()) 
            {
                yearTO = (PrerequisiteYearMonthTO)academicYearTOListIterator.next();
               /*if(prereqYearMonthTO.getMonth() != null && !prereqYearMonthTO.getMonth().isEmpty())
                {
                    errors.add("error", new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_MORETHAN_ONE));
                    throw new BusinessException();
                }
                if(prereqYearMonthTO.getYear()!=null && !prereqYearMonthTO.getYear().isEmpty())
                {
                	 errors.add("error", new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_MORETHAN_ONE_FOR_ADMISSION));
                     throw new BusinessException();
                }*/
            }
            
            prereqYearMonth = PrerequisiteYearMonthHelper.getInstance().populateTOtoBO(prereqYearMonthTO);
            if(iPrerequisitesYearMonth != null)
            {
                return iPrerequisitesYearMonth.addAcademicYear(prereqYearMonth);
            }
        }
        log.info("End of addAcademicYear  of PrerequisiteYearMonthHandler");
        return false;
    }

	
	
    public PrerequisiteYearMonthTO getYearDetailsWithId(int yearId)throws Exception
    {
        if(iPrerequisitesYearMonth != null)
        {
        	PrerequisitsYearMonth prereqYearMonth = iPrerequisitesYearMonth.getPreReqYearDetailsWithId(yearId);
            if(prereqYearMonth != null)
            {
                return PrerequisiteYearMonthHelper.getInstance().populateBOtoTOEdit(prereqYearMonth);
            }
        }
        log.info("End of  getAcademicYearDetailsWithId  of PrerequisiteYearMonthHandler");
        return new PrerequisiteYearMonthTO();
    }

   
	public boolean updateAcademicYear(PrerequisiteYearMonthForm preYearForm, ActionErrors errors)
        throws Exception
    {
        PrerequisitsYearMonth prereqYearMonth = null;
        PrerequisiteYearMonthTO yearTO = new PrerequisiteYearMonthTO();
           if(preYearForm != null)
            {
                yearTO.setId(preYearForm.getId());
                yearTO.setYear(preYearForm.getYear());
                yearTO.setMonth(preYearForm.getMonth());
                yearTO.setModifiedBy(preYearForm.getUserId());
                yearTO.setCreatedBy(preYearForm.getUserId());
            }
            prereqYearMonth = PrerequisiteYearMonthHelper.getInstance().populateTOtoBOUpdate(yearTO);
            if(prereqYearMonth != null)
            {
                return iPrerequisitesYearMonth.updatePreYearMonth(prereqYearMonth);
            }
        log.info("End of updateAcademicYear  of PrerequisiteYearMonthHandler");
        return false;
    }

    public boolean deleteAcademicYearDetails(int yearId, String userId)
        throws Exception
    {
        if(iPrerequisitesYearMonth != null)
        {
            return iPrerequisitesYearMonth.deleteAcademicYearDetails(yearId, userId);
        } else
        {
            log.info("End of deleteAcademicYearDetails of PrerequisiteYearMonthHandler");
            return false;
        }
    }

    public boolean reActivateAcademicYear(String year,String month, String userId)
        throws Exception
    {
        if(iPrerequisitesYearMonth != null)
        {
            return iPrerequisitesYearMonth.reActivateAcademicYear(Integer.parseInt(year),Integer.parseInt(month), userId);
        } else
        {
            log.info("End of reActivateAcademicYear of PrerequisiteYearMonthHandler");
            return false;
        }
    }

}



