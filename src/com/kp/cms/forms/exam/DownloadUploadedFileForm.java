package com.kp.cms.forms.exam;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.DownloadUploadedFileTO;

public class DownloadUploadedFileForm extends BaseActionForm{
	private String formatName;
	private List<DownloadUploadedFileTO> downloadFiles;
	public String getFormatName() {
		return formatName;
	}
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	public List<DownloadUploadedFileTO> getDownloadFiles() {
		return downloadFiles;
	}
	public void setDownloadFiles(List<DownloadUploadedFileTO> downloadFiles) {
		this.downloadFiles = downloadFiles;
	}
}
