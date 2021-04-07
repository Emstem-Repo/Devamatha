<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function getDetails() {
	document.location.href = "admBioData.do?method=admBioDataEntry";
}
function getAttenMarks(){
	document.location.href = "attnMarksUpload.do?method=initAttnMarksUpload";
}
function getMeritList(){
	document.location.href = "admMeritUpload.do?method=initAdmMeritListUpload";
}
function getTcDetails(){
	document.location.href = "admTcDetails.do?method=initAdmTcDetails";
}
function getSubject(){
	document.location.href = "AdmissionSubject.do?method=initAdmissionSubject";
}
function getFeemain(){
	document.location.href = "admMeritUpload.do?method=initAdmFeeMain";
}
function getProBioData(){
	document.location.href = "promoteBioData.do?method=initPromoteBioDataUpload";
}
function getPromoteMarks(){
	document.location.href = "promotemarks.do?method=initPromoteMarksUpload";
}
function getPromoteSubjects(){
	document.location.href = "promoteSubjects.do?method=initPromoteSubjectsUpload";
}
function getPromoteSecondLanguage(){
	document.location.href = "admissionPromote.do?method=initPromoteSecondLang";
}
function getPromoteSuplimarks(){
	document.location.href = "admissionPromote.do?method=initPromoteSupliMarks";
}
function getPucttnAttendance(){
	document.location.href = "attnMarksUpload.do?method=initUploadPucttnAttendance";
}
function getAttenClassheld(){
	document.location.href = "pucAttedance.do?method=initPucClassHeld";
}
function getAttDefineClassSubjects(){
	document.location.href = "pucAttedance.do?method=initPucDefineClassSubjects";
}
function getPucttnSubjects(){
	document.location.href = "attnSubjectsUpload.do?method=initAttnPucSubjectUpload";
}
function getPucttnLeaves(){
	document.location.href = "pucAttedance.do?method=initPucattnApprovedLeaves";
}
function getAdditionalFees(){
	document.location.href ="additionalFees.do?method=initAddtionalFeesUpload";
}
function getFeeDetails(){
	document.location.href ="additionalFees.do?method=initFeesDetails";
}
function getClassFees(){
	document.location.href ="additionalFees.do?method=initUploadClassFees";
}
function getAmountHeads(){
	document.location.href ="amountHeads.do?method=initAccountHeadsUpload";
}
function getpettyCashCollections(){
	document.location.href ="amountHeads.do?method=initUploadPettyCashCollection";
}
function getCollectionDetails(){
	document.location.href ="amountHeads.do?method=initPettyCashCollectionsDetails";
}
function getPucttnBioData(){
	document.location.href ="attnBioData.do?method=initAttnBioDataUpload";
}
function getPucttnCetMarks(){
	document.location.href ="attnSubjectsUpload.do?method=initAttnCetMarksUpload";
}
function getPucttnDefineRange(){
	document.location.href ="attnSubjectsUpload.do?method=initAttnDefineRangeUpload";
}
function getPucttnInternalMarks(){
	document.location.href ="attnMarksUpload.do?method=initUploadPucattnInternalMarks";
}
  </script>
<html:form action="/admBioData" method="post">
<html:hidden property="formName" value="admBioDataCJCForm" />
<table width="99%" border="0"> 
  <tr>
    <td><span class="heading">Data Migration&gt;&gt;<span class="Bredcrumbs">Data Migration&gt;&gt;</span> </span></td>

  </tr>
 
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Data Migration</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="99" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%">
        	<tr bgcolor="#FFFFFF">
					<td height="20" colspan="4">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					</html:messages> </FONT></div>
					</td>
					
			</tr>
           		 <tr>
           		 <td align="left"><span class="heading">Admission</span></td>
			</tr>
			<tr>
			<td align="left"><p><a href="#"  onclick="getDetails()"><b>Migration Adm BioData</b></a></p></td>		
			 <td align="left"><p><a href="#"  onclick="getMeritList()"><b>Migration Adm MeritList</b></a></p></td>
			 <td align="left"><p><a href="#"  onclick="getTcDetails()"><b>Admission TcDetails</b></a></p></td>
			 <td align="left"><p><a href="#"  onclick="getSubject()"><b>Admission Subject</b></a></p></td>
			 </tr>
			 <tr>
			 <td align="left"><span class="heading">Attendance</span></td>
			 </tr>
			 <tr>
			 <td align="left"><p><a href="#"  onclick="getAttenMarks()"><b>PUC Attendance Marks</b></a></p></td>
			  <td align="left"><p><a href="#"  onclick="getPucttnAttendance()"><b>Pucttn Attendance </b></a></p></td>
			  <td align="left"><p><a href="#"  onclick="getAttenClassheld()"><b>PUC Attendance ClassHeld</b></a></p></td>
			  <td align="left"><p><a href="#"  onclick="getPucttnSubjects()"><b>Pucttn Subjects </b></a></p></td>
			 </tr>
			 <tr>
			  <td align="left"><p><a href="#"  onclick="getAttDefineClassSubjects()"><b>PUC Attendance Define ClassSubjects</b></a></p></td>
			 <td align="left"><p><a href="#"  onclick="getPucttnLeaves()"><b>Pucttn Approved Leaves </b></a></p></td>
			  <td align="left"><p><a href="#"  onclick="getPucttnBioData()"><b>Pucttn BioData </b></a></p></td>
			  <td align="left"><p><a href="#"  onclick="getPucttnCetMarks()"><b>Pucttn CetMarks </b></a></p></td>
			 </tr>
			<tr>
			    <td align="left"><p><a href="#"  onclick="getPucttnDefineRange()"><b>PucAttn Define Range </b></a></p></td>
			    <td align="left"><p><a href="#"  onclick="getPucttnInternalMarks()"><b>Pucttn Internal Marks </b></a></p></td>
			 </tr>
			    
			 <tr>
			    <td align="left"><span class="heading">Fee</span></td>
			 </tr>
			 <tr>
			  <td align="left"><p><a href="#"  onclick="getFeemain()"><b>Migration FeeMain</b></a></p></td>
			  <td align="left"><p><a href="#"  onclick="getAdditionalFees()"><b>Migration AdditionalFees</b></a></p></td>
			  <td align="left"><p><a href="#"  onclick="getFeeDetails()"><b>Migration FeeDetails</b></a></p></td>
			  <td align="left"><p><a href="#"  onclick="getClassFees()"><b>Migration ClassFee</b></a></p></td>
			 </tr>
			 <tr>
			 <td align="left"><span class="heading">Promote</span></td>
			 </tr>
			 <tr>
			 <td align="left"><p><a href="#"  onclick="getProBioData()"><b>Promote Bio Data</b></a></p></td>
			 <td align="left"><p><a href="#"  onclick="getPromoteMarks()"><b>Promote Marks</b></a></p></td>
			 <td align="left"><p><a href="#"  onclick="getPromoteSubjects()"><b>Promote Subjects</b></a></p></td>
			 </tr>
			 <tr>
			 <td align="left"><p><a href="#"  onclick="getPromoteSecondLanguage()"><b>Promotion SecondLanguage</b></a></p></td>
			 <td align="left"><p><a href="#"  onclick="getPromoteSuplimarks()"><b>Promotion SupliMarks</b></a></p></td>
			 </tr>
			  <tr>
			 <td align="left"><span class="heading">Petty Cash</span></td>
			 </tr>
			 <tr>
				<td align="left"><p><a href="#"  onclick="getAmountHeads()"><b>PettyCash AmountHeads</b></a></p></td>
				<td align="left"><p><a href="#"  onclick="getpettyCashCollections()"><b>PettyCash Collections</b></a></p></td>
			 	<td align="left"><p><a href="#"  onclick="getCollectionDetails()"><b>PettyCash CollectionDetails</b></a></p></td>
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
</html:form>
