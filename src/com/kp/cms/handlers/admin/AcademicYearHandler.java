package com.kp.cms.handlers.admin;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.AcademicYearForm;
import com.kp.cms.helpers.admin.AcademicYearHelper;
import com.kp.cms.to.admin.AcademicYearTO;
import com.kp.cms.transactions.admin.IAcademicYearTransaction;
import com.kp.cms.transactionsimpl.admin.AcademicYearTransactionImpl;
import common.Logger;
import java.util.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

public class AcademicYearHandler
{

    private static final Logger log = Logger.getLogger(AcademicYearHandler.class);
    private static volatile AcademicYearHandler academicYearHandler = null;
    IAcademicYearTransaction iAcademicYearTransaction;

    private AcademicYearHandler()
    {
        iAcademicYearTransaction = new AcademicYearTransactionImpl();
    }

    public static AcademicYearHandler getInstance()
    {
        if(academicYearHandler == null)
        {
            academicYearHandler = new AcademicYearHandler();
        }
        return academicYearHandler;
    }

    @SuppressWarnings("unchecked")
	public List<AcademicYearTO> getAcademicYearDetails()
        throws Exception
    {
        log.info("In getAcademicYearDetails() method of AcademicYearHandler class");
        if(iAcademicYearTransaction != null)
        {
            List<AcademicYear> academicYearList = iAcademicYearTransaction.getAcademicYearDetails();
            if(academicYearList != null)
            {
                return AcademicYearHelper.getInstance().poupulateBOtoTO(academicYearList);
            }
        }
        log.info("End of getAcademicYearDetails() of AcademicYearHandler");
        return new ArrayList();
    }

    public AcademicYear getAcademicYear(String academicYearValue)
        throws Exception
    {
        log.info("Start of getAcademicYear of AcademicYearHandler");
        if(iAcademicYearTransaction != null)
        {
            int academicYear = splitYear(academicYearValue);
            return iAcademicYearTransaction.getAcademicYear(academicYear);
        } else
        {
            log.info("End of getAcademicYear of AcademicYearHandler");
            return new AcademicYear();
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

    @SuppressWarnings("deprecation")
	public boolean addAcademicYear(AcademicYearTO academicYrTO, ActionErrors errors)
        throws Exception
    {
        log.info("Start of addAcademicYear  of AcademicYearHandler");
        AcademicYear academicYear = null;
        List<AcademicYearTO> academicYearTOList = getAcademicYearDetails();
        Iterator<AcademicYearTO> academicYearTOListIterator = academicYearTOList.iterator();
        if(academicYrTO != null)
        {
            while(academicYearTOListIterator.hasNext()) 
            {
            	 AcademicYearTO yearTO = new AcademicYearTO();
                yearTO = (AcademicYearTO)academicYearTOListIterator.next();
                if(academicYrTO.getIsCurrent() != null && academicYrTO.getIsCurrent().equals("true") && yearTO.getIsCurrent().equals("Yes"))
                {
                    errors.add("error", new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_MORETHAN_ONE));
                    throw new BusinessException();
                }
                if(academicYrTO.getIsCurrentForAdmission()!=null && academicYrTO.getIsCurrentForAdmission().equals("true") && yearTO.getIsCurrentForAdmission().equals("Yes"))
                {
                	 errors.add("error", new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_MORETHAN_ONE_FOR_ADMISSION));
                     throw new BusinessException();
                }
            }
            
            academicYear = AcademicYearHelper.getInstance().populateTOtoBO(academicYrTO);
            if(academicYear != null && iAcademicYearTransaction != null)
            {
                return iAcademicYearTransaction.addAcademicYear(academicYear);
            }
        }
        log.info("End of addAcademicYear  of AcademicYearHandler");
        return false;
    }

    public AcademicYearTO getAcademicYearDetailsWithId(int yearId)
        throws Exception
    {
        log.info("Start of  getAcademicYearDetailsWithId  of AcademicYearHandler");
        if(iAcademicYearTransaction != null)
        {
            AcademicYear academicYear = iAcademicYearTransaction.getAcademicYearDetailsWithId(yearId);
            if(academicYear != null)
            {
                return AcademicYearHelper.getInstance().populateBOtoTOEdit(academicYear);
            }
        }
        log.info("End of  getAcademicYearDetailsWithId  of AcademicYearHandler");
        return new AcademicYearTO();
    }

    @SuppressWarnings("deprecation")
	public boolean updateAcademicYear(AcademicYearForm academicYearForm, ActionErrors errors)
        throws Exception
    {
        log.info("Start of updateAcademicYear  of AcademicYearHandler");
        AcademicYear academicYear = null;
        AcademicYearTO yearTO = new AcademicYearTO();
        List<AcademicYearTO> academicYearTOList = getAcademicYearDetails();
        Iterator<AcademicYearTO> academicYearTOIterator = academicYearTOList.iterator();
        if(yearTO != null)
        {
            while(academicYearTOIterator.hasNext()) 
            {
                yearTO = (AcademicYearTO)academicYearTOIterator.next();
                if(academicYearForm.getIsCurrent().equals("true") && yearTO.getIsCurrent().equals("Yes"))
                {
                    if(academicYearForm.getAcademicYear().equals(yearTO.getYear()))
                    {
//                        errors.add("error", new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_IS_ALREADY_CURRENT));
//                        throw new BusinessException();
                    } else
                    {
                        errors.add("error", new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_MORETHAN_ONE));
                        throw new BusinessException();
                    }
                }
                if(academicYearForm.getIsCurrentForAdmission().equals("true") && yearTO.getIsCurrentForAdmission().equals("Yes")){
                	if(academicYearForm.getAcademicYear().equals(yearTO.getYear()))
                    {
//                        errors.add("error", new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_IS_ALREADY_CURRENT_FOR_ADMISSION));
//                        throw new BusinessException();
                    } else
                    {
                        errors.add("error", new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_MORETHAN_ONE_FOR_ADMISSION));
                        throw new BusinessException();
                    }
                }
            }
            if(academicYearForm != null)
            {
                yearTO.setId(academicYearForm.getId());
                yearTO.setIsCurrent(academicYearForm.getIsCurrent());
                yearTO.setYear(academicYearForm.getAcademicYear());
                yearTO.setModifiedBy(academicYearForm.getUserId());
                yearTO.setCreatedBy(academicYearForm.getUserId());
                //code added by sudhir
                yearTO.setIsCurrentForAdmission(academicYearForm.getIsCurrentForAdmission());
                //
            }
            academicYear = AcademicYearHelper.getInstance().populateTOtoBOUpdate(yearTO);
            if(iAcademicYearTransaction != null && academicYear != null)
            {
                return iAcademicYearTransaction.updateAcademicYear(academicYear);
            }
        }
        log.info("End of updateAcademicYear  of AcademicYearHandler");
        return false;
    }

    public boolean deleteAcademicYearDetails(int yearId, String userId)
        throws Exception
    {
        log.info("Start of deleteAcademicYearDetails of AcademicYearHandler");
        if(iAcademicYearTransaction != null)
        {
            return iAcademicYearTransaction.deleteAcademicYearDetails(yearId, userId);
        } else
        {
            log.info("End of deleteAcademicYearDetails of AcademicYearHandler");
            return false;
        }
    }

    public boolean reActivateAcademicYear(String year, String userId)
        throws Exception
    {
        log.info("Start of reActivateAcademicYear of AcademicYearHandler");
        if(iAcademicYearTransaction != null)
        {
            return iAcademicYearTransaction.reActivateAcademicYear(splitYear(year), userId);
        } else
        {
            log.info("End of reActivateAcademicYear of AcademicYearHandler");
            return false;
        }
    }

}
