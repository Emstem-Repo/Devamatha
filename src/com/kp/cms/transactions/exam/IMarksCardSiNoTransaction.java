package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.MarksCardSiNo;
import com.kp.cms.bo.exam.MarksCardSlNo;
import com.kp.cms.forms.exam.MarksCardSiNoForm;

public interface IMarksCardSiNoTransaction {

	boolean save(MarksCardSlNo bo)throws Exception;

	List<MarksCardSlNo> getData()throws Exception;

	boolean getDataAvailable(MarksCardSiNoForm cardSiNoForm)throws Exception;

}
