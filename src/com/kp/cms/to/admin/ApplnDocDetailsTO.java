package com.kp.cms.to.admin;

import java.io.Serializable;

public class ApplnDocDetailsTO implements Serializable,Comparable<ApplnDocDetailsTO> {
	
	private int id;
	private String semNo;
	private boolean isHardCopySubmitted;
	private int applnDocId;
	private boolean tempHardCopySubmitted;
	private String checked;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSemNo() {
		return semNo;
	}
	public void setSemNo(String semNo) {
		this.semNo = semNo;
	}
	public Boolean getIsHardCopySubmitted() {
		return isHardCopySubmitted;
	}
	public void setIsHardCopySubmitted(boolean isHardCopySubmitted) {
		this.isHardCopySubmitted = isHardCopySubmitted;
	}
	public int getApplnDocId() {
		return applnDocId;
	}
	public void setApplnDocId(int applnDocId) {
		this.applnDocId = applnDocId;
	}
	public boolean isTempHardCopySubmitted() {
		return tempHardCopySubmitted;
	}
	public void setTempHardCopySubmitted(boolean tempHardCopySubmitted) {
		this.tempHardCopySubmitted = tempHardCopySubmitted;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	@Override
	public int compareTo(ApplnDocDetailsTO arg0) {
        String firstString = this.getSemNo();
        String secondString = arg0.getSemNo();
 
        if (secondString == null || firstString == null) {
            return 0;
        }
 
        int lengthFirstStr = firstString.length();
        int lengthSecondStr = secondString.length();
 
        int index1 = 0;
        int index2 = 0;
 
        while (index1 < lengthFirstStr && index2 < lengthSecondStr) {
            char ch1 = firstString.charAt(index1);
            char ch2 = secondString.charAt(index2);
 
            char[] space1 = new char[lengthFirstStr];
            char[] space2 = new char[lengthSecondStr];
 
            int loc1 = 0;
            int loc2 = 0;
 
            do {
                space1[loc1++] = ch1;
                index1++;
 
                if (index1 < lengthFirstStr) {
                    ch1 = firstString.charAt(index1);
                } else {
                    break;
                }
            } while (Character.isDigit(ch1) == Character.isDigit(space1[0]));
 
            do {
                space2[loc2++] = ch2;
                index2++;
 
                if (index2 < lengthSecondStr) {
                    ch2 = secondString.charAt(index2);
                } else {
                    break;
                }
            } while (Character.isDigit(ch2) == Character.isDigit(space2[0]));
 
            String str1 = new String(space1);
            String str2 = new String(space2);
 
            int result;
 
            if (Character.isDigit(space1[0]) && Character.isDigit(space2[0])) {
                Integer firstNumberToCompare = Integer.valueOf(Integer
                        .parseInt(str1.trim()));
                Integer secondNumberToCompare = Integer.valueOf(Integer
                        .parseInt(str2.trim()));
                result = firstNumberToCompare.compareTo(secondNumberToCompare);
            } else {
                result = str1.compareTo(str2);
            }
 
            if (result != 0) {
                return result;
            }
        }
        return lengthFirstStr - lengthSecondStr;
    }
}