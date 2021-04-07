package com.kp.cms.helpers.phd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.phd.DocumentDetailsBO;
import com.kp.cms.forms.phd.DocumentDetailsForm;
import com.kp.cms.to.phd.DocumentDetailsTO;

public class DocumentDetailsHelper {
	
public static volatile DocumentDetailsHelper documentDetailsHelper=null;
	
	public static DocumentDetailsHelper getInstance() {
		if (documentDetailsHelper == null) {
			documentDetailsHelper = new DocumentDetailsHelper();
			return documentDetailsHelper;
		}
		return documentDetailsHelper;
	}

	public List<DocumentDetailsTO> convertBOsToTO(List<DocumentDetailsBO> documentDetailsBOs)
	{
		List<DocumentDetailsTO> documentDetailsTOList=new ArrayList<DocumentDetailsTO>();
        Iterator iterator=documentDetailsBOs.iterator();
        while(iterator.hasNext())
        {
        DocumentDetailsBO documentDetailsBO=(DocumentDetailsBO) iterator.next()	;
        DocumentDetailsTO documentDetailsTO=new DocumentDetailsTO();
        documentDetailsTO.setId(documentDetailsBO.getId());
        documentDetailsTO.setDocumentName(documentDetailsBO.getDocumentName());
        documentDetailsTO.setSubmissionOrder(documentDetailsBO.getSubmissionOrder());
        if(documentDetailsBO.getGuidesFees()!=null && documentDetailsBO.getGuidesFees().intValue()>0){
        documentDetailsTO.setGuideFees(documentDetailsBO.getGuidesFees().toString());
        }
        documentDetailsTOList.add(documentDetailsTO);
        }
		return documentDetailsTOList;
	}
	
	public DocumentDetailsBO convertFormTOBO(DocumentDetailsForm form,String mode) {
		DocumentDetailsBO detailsBO=new DocumentDetailsBO();
      detailsBO.setId(form.getId());
      detailsBO.setDocumentName(form.getDocumentName());
      detailsBO.setSubmissionOrder(Integer.parseInt(form.getSubmissionOrder()));
      if(form.getGuideFees()!=null && !form.getGuideFees().isEmpty()){
    	  detailsBO.setGuidesFees(new BigDecimal(form.getGuideFees()));
      }
      detailsBO.setIsActive(true);
      if(mode.equalsIgnoreCase("add"))
      {
      detailsBO.setCreatedBy(form.getUserId());
      detailsBO.setCreatedDate(new Date());
      }
      else
      {
    	  detailsBO.setModifiedBy(form.getUserId());
    	  detailsBO.setLastModifiedDate(new Date());
      }
		
		return detailsBO;
	}
	
	public void setBoToForm(DocumentDetailsForm detailsForm,DocumentDetailsBO detailsBO)
	{
		if(detailsBO!=null)
		{
			detailsForm.setDocumentName(detailsBO.getDocumentName());
			detailsForm.setSubmissionOrder(String.valueOf(detailsBO.getSubmissionOrder()));
			detailsForm.setId(detailsBO.getId());
			detailsForm.setOrigDocumentName(detailsBO.getDocumentName());
			detailsForm.setOrigSubmissionOrder(String.valueOf(detailsBO.getSubmissionOrder()));
			if(detailsBO.getGuidesFees()!=null && detailsBO.getGuidesFees().intValue()>0){
			detailsForm.setGuideFees(detailsBO.getGuidesFees().toString());
			}
		}
	}
}
