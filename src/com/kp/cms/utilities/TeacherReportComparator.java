package com.kp.cms.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.kp.cms.to.attendance.PeriodTO;

public class TeacherReportComparator implements Comparator<PeriodTO> {
	private int compare=0;

	public int getCompare() {
		return compare;
	}
	public void setCompare(int compare) {
		this.compare = compare;
	}


	@Override
	public int compare(PeriodTO arg0, PeriodTO arg1) {
		if(arg0!=null && arg1!=null){
			if(compare==0){
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				
				Date startTime;
				try {
					startTime = dateFormat.parse(arg0.getDate());
					Date startTime1 = dateFormat.parse(arg1.getDate());
					if(startTime1.compareTo(startTime) > 0)
						return 1;
				} catch (ParseException e) {
				}
			}else if(compare==1){
				if (arg0.getPosition() != 0 && arg1.getPosition()!=0){
					if(arg0.getPosition() ==  arg1.getPosition())
						return 0;
					else if(arg0.getPosition() <  arg1.getPosition())
						return -1;
					else
					   return 1;
				}
			}else if(compare==2){
				if (arg0.getPeriodName() != null
						&& arg1.getPeriodName() != null) {
					return arg0.getPeriodName().compareTo(arg1.getPeriodName());
				}
			}
		}	
		return 0;
	}
}
