<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">
function getDetails(reportName) {
	 document.location.href = reportName;
	   }
</script>
<html:form action="/birtFeeReport" method="post">
<html:hidden property="method" styleId="method" value="initBirtFeeReport" />
<html:hidden property="formName" value="birtFeeReportForm" />
<table width="99%" border="0"> 
  <tr>
    <td><span class="heading">Exam<span class="Bredcrumbs">&gt;&gt;Upload Exam Data &gt;&gt;</span> </span></td>

  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Upload Exam Data</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="99" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <table width="100%">
		<tr>
		<td><p><a href="#"  onclick="getDetails('uploadExamInternal.do?method=initUploadInternalOverAll')"><b>Upload Exam Internal Over All Marks</b></a></p></td>
		<td><p><a href="#"  onclick="getDetails('uploadExamAttendance.do?method=initUploadInternalOverAll')"><b>Upload Exam Internal Over All Attendnce Marks</b></a></p></td>
		<td><p><a href="#"  onclick="getDetails('uploadExamStudentFinalMark.do?method=initUploadInternalOverAll')"><b>Upload Exam Student Final Marks</b></a></p></td>
		</tr>
		<tr>
		<td><p><a href="#"  onclick="getDetails('uploadExamStudentSubjectGroup.do?method=initUploadStudentSubjectGroup')"><b>Upload Student Subject Group History</b></a></p></td>
		<td><p><a href="#"  onclick="getDetails('uploadAddlBioData.do?method=uploadBioData')"><b>Upload Student Additional BioData Information</b></a></p></td>
		<td><p><a href="#"  onclick="getDetails('uploadPreviousClasses.do?method=uploadPreviousClass')"><b>Upload Student Previous Classes History</b></a></p></td>
		</tr>
		<tr>
		<td><p><a href="#"  onclick="getDetails('uploadSupplementaryDetails.do?method=uploadSupplementaryApllication')"><b>Upload Exam Supplementary Improvement Application</b></a></p></td>
		<td><p><a href="#"  onclick="getDetails('uploadExaminternalMark.do?method=initUploadInternalMarkSupplementary')"><b>Upload Exam Internal Mark Supplementary Details</b></a></p></td>
		<td><p></p></td>
		</tr>
		</table>
		</td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	</tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table>
</td>
</tr>
</table>
<SCRIPT type="text/javascript">	
var type ="<c:out value='${fileType}'/>";
if(type!=null && type =="Excel"){
var filename = "<c:out value='${fileName}'/>";
if(filename!=null){
	var url ="DownloadStudentsDataExcelAction.do?fileName="+filename;
	myRef = window.open(url,"downloadReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
}else if(type!=null && type=="CSV"){
	var filename = "<c:out value='${fileName}'/>";
	if(filename!=null){
		var url ="DownloadStudentsDataCSVAction.do?fileName="+filename;
		myRef = window.open(url,"downloadReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
}
</SCRIPT>

</html:form>