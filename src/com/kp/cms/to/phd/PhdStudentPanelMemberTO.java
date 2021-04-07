package com.kp.cms.to.phd;


public class PhdStudentPanelMemberTO implements Comparable<PhdStudentPanelMemberTO>{
	
	private Integer id;
	private String synopsisName;
	private String synopsisId;
	private String vivaName;
	private String vivaId;
	private String synopsisVivaPanel;
	private String synopsisPanel;
	private String vivaPanel;
	private String selectedPanel;
	private String tempChecked;
	private String checked;
	private String tempChecked1;
	private String checked1;
	private String tempChecked2;
	private String checked2;
	private boolean checkPanel;
	private boolean checkViva;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSynopsisName() {
		return synopsisName;
	}
	public void setSynopsisName(String synopsisName) {
		this.synopsisName = synopsisName;
	}
	public String getVivaName() {
		return vivaName;
	}
	public void setVivaName(String vivaName) {
		this.vivaName = vivaName;
	}
	public String getSynopsisVivaPanel() {
		return synopsisVivaPanel;
	}
	public void setSynopsisVivaPanel(String synopsisVivaPanel) {
		this.synopsisVivaPanel = synopsisVivaPanel;
	}
	public String getSynopsisPanel() {
		return synopsisPanel;
	}
	public void setSynopsisPanel(String synopsisPanel) {
		this.synopsisPanel = synopsisPanel;
	}
	public String getVivaPanel() {
		return vivaPanel;
	}
	public void setVivaPanel(String vivaPanel) {
		this.vivaPanel = vivaPanel;
	}
	public String getSelectedPanel() {
		return selectedPanel;
	}
	public void setSelectedPanel(String selectedPanel) {
		this.selectedPanel = selectedPanel;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getTempChecked1() {
		return tempChecked1;
	}
	public void setTempChecked1(String tempChecked1) {
		this.tempChecked1 = tempChecked1;
	}
	public String getChecked1() {
		return checked1;
	}
	public void setChecked1(String checked1) {
		this.checked1 = checked1;
	}
	public String getTempChecked2() {
		return tempChecked2;
	}
	public void setTempChecked2(String tempChecked2) {
		this.tempChecked2 = tempChecked2;
	}
	public String getChecked2() {
		return checked2;
	}
	public void setChecked2(String checked2) {
		this.checked2 = checked2;
	}
	public String getSynopsisId() {
		return synopsisId;
	}
	public void setSynopsisId(String synopsisId) {
		this.synopsisId = synopsisId;
	}
	public String getVivaId() {
		return vivaId;
	}
	public void setVivaId(String vivaId) {
		this.vivaId = vivaId;
	}
	public boolean isCheckPanel() {
		return checkPanel;
	}
	public void setCheckPanel(boolean checkPanel) {
		this.checkPanel = checkPanel;
	}
	public boolean isCheckViva() {
		return checkViva;
	}
	public void setCheckViva(boolean checkViva) {
		this.checkViva = checkViva;
	}
	@Override
	public int compareTo(PhdStudentPanelMemberTO arg0) {
		if(arg0 instanceof PhdStudentPanelMemberTO && arg0.getId()>0){
			return this.getId().compareTo(arg0.id);
		}
		return 0;
	}
	
}
