<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript">

function cancelAction() {
	document.location.href="LoginAction.do?method=cancelLoginAction";
}

function getExamsByExamTypeAndYear() {
	
	var examType="Supplementary";
	var year=document.getElementById("year").value;
	getExamNameByExamTypeAndYear("examMap", examType,year, "examNameId", updateExamName);
}

function updateExamName(req) {
	updateOptionsFromMap(req, "examNameId", "- Select -");
	updateCurrentExam(req, "examNameId");
}
function getProgram(deanaryName) {
	var examId=document.getElementById("examNameId").value;
	getProgramBydeanaryName(examId,deanaryName,"programId",updateProgramName);
}
function updateProgramName(req) {
	updateOptionsFromMap(req, "programId", "- Select -");
}

function getProgramByExam(examId) {
	var deanaryName=document.getElementById("deaneryId").value;
	if(deanaryName !=null && deanaryName !=""){
		getProgramBydeanaryName(examId,deanaryName,"programId",updateProgramName);
	}
}
function getDownloadFile(){
	var url="syllabusDisplayForSupplimentoryDownload.do?method=getStreamInfo";
		win2 = window.open(url, "Download File", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}

function closeAction(){
	document.location.href = "SyllabusDisplayForSupplimentory.do?method=initSyllabusTrackerForSupplementary";
}
</script>
<html:form action="/SyllabusDisplayForSupplimentory" method="POST">
<html:hidden property="formName" value="syllabusTrackerForSupplementaryForm" />
<html:hidden property="method" value="getStudentBacklocks" />
<html:hidden property="pageType" value="1" />
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/> <span class="Bredcrumbs">&gt;&gt; Syllabus tracker for Supplementary &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Syllabus tracker for Supplementary </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              
              <tr >
 				<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="syllabusTrackerForSupplementaryForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> Exam
									Name :</div>
									</td>

									<td class="row-even"><html:select property="examId"
										styleClass="combo" styleId="examNameId"
										name="syllabusTrackerForSupplementaryForm" onchange="getProgramByExam(this.value)"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="syllabusTrackerForSupplementaryForm" property="examNameList">
											<html:optionsCollection property="examNameList"
												name="syllabusTrackerForSupplementaryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
									</td>
               </tr>
               <tr>
               <td class="row-odd">
				<div align="right"><span class="Mandatory">*</span> <bean:message
					key="knowledgepro.syllabus.supplimentary.display.deanery" />:</div>
			   </td>
			   <td class="row-even"><html:select property="deanery"
										styleClass="combo" styleId="deaneryId"
										name="syllabusTrackerForSupplementaryForm" onchange="getProgram(this.value)"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="syllabusTrackerForSupplementaryForm" property="deanaryList">
											<html:optionsCollection property="deanaryList"
												name="syllabusTrackerForSupplementaryForm" label="value" value="key" />
										
										</logic:notEmpty>
									</html:select>
				</td>
               <td class="row-odd">
				<div align="right"> <bean:message
					key="knowledgepro.admin.program" />:</div>
			   </td>
			   <td class="row-even"><html:select property="program"
										styleClass="combo" styleId="programId"
										name="syllabusTrackerForSupplementaryForm" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="syllabusTrackerForSupplementaryForm" property="programList">
											<html:optionsCollection property="programList"
												name="syllabusTrackerForSupplementaryForm" label="value" value="key" />
										</logic:notEmpty>		
									</html:select>
									</td>
               
               </tr>
               
               
               
               
               
               <tr>
					<td class="row-odd" width="15%"> 
					<div align="right">
					<bean:message key="knowledgepro.holidays.from" />:</div>
					</td>
					<td   class="row-even" width="35%" align="left">
						<html:text name="syllabusTrackerForSupplementaryForm" property="fromDate" styleId="fromDate" size="10" maxlength="16" />
						<script language="JavaScript">
											$(function(){
		 					var pickerOpts = {
		        			dateFormat:"dd/mm/yy"
		       				};  
		  					$.datepicker.setDefaults(
		   					$.extend($.datepicker.regional[""])
		  					);
		  					$("#fromDate").datepicker(pickerOpts);
							});
           				</script>
					</td> 
					<td class="row-odd" width="15%"> 
					<div align="right">
					<bean:message key="knowledgepro.holidays.to" />:</div> 
					</td>
					<td   class="row-even" width="35%" align="left" colspan="2">
					<html:text name="syllabusTrackerForSupplementaryForm" property="toDate" styleId="toDate" size="10" maxlength="16" />
					<script language="JavaScript">
											$(function(){
		 					var pickerOpts = {
		        			dateFormat:"dd/mm/yy"
		       				};  
		  					$.datepicker.setDefaults(
		   					$.extend($.datepicker.regional[""])
		  					);
		  				$("#toDate").datepicker(pickerOpts);
							});
       				</script>
				</td> 
			</tr>
               
               
               
               
               
               
               
               <tr>
               <td height="25"  colspan="2"  class="row-even" align="center">
               
               <html:radio property="examResult" value="Applied" styleId="applied" ></html:radio>Applied
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property="examResult" value="Failed" styleId="failed" ></html:radio>Failed
               </td>
               <td height="25" colspan="2" class="row-even" align="center">
               
               <html:radio property="examType" value="Theory" styleId="theory" ></html:radio>Theory
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property="examType" value="Practical" styleId="practical" ></html:radio>Practical
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property="examType" value="TheoryPractical" styleId="theoryPractical" ></html:radio>Theory And Practical
               </td>
               </tr>

            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          <tr bgcolor="#FFFFFF">
            <td width="100%" height="20" colspan="4">
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
              <tr> 
				<td align="center">
				<html:submit styleClass="formbutton" ><bean:message key="knowledgepro.submit" /></html:submit>&nbsp;
				<html:button  property="" styleClass="formbutton" value="Close" onclick="cancelAction()" />						
				</td>
			</tr>
			<tr><td style="height: 10px" align="center" colspan="1"></td></tr>			
            </table>
            </td>
          </tr>          
          
          <logic:notEmpty name="syllabusTrackerForSupplementaryForm" property="syllabusTrackerForSupplementaryTo">	
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr>
					<td height="25" width="4%" class="row-odd">
					<div align="center"><bean:message key="knowledgepro.slno"/></div>
					</td>
					<td height="25" width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.hostel.student.regNo"/></div> </td>
					<td height="25" width="8%" class="row-odd"><div align="center"><bean:message key="knowledgepro.syllabus.supplimentary.display.dateofjoining.year"/></div> </td>
					<td height="25" width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.syllabus.supplimentary.display.exam.attempted.year"/></div> </td>
					<td height="25" width="8%" class="row-odd"><div align="center"><bean:message key="knowledgepro.syllabus.display.semester"/></div> </td>
					<td height="25" width="9%" class="row-odd"><div align="center"><bean:message key="knowledgepro.student.syllabus.display.paper.code"/></div> </td>
					<td height="25" width="20%" class="row-odd"><div align="center"><bean:message key="knowledgepro.student.syllabus.display.paper.name"/></div> </td>
					<td height="25" width="8%" class="row-odd"><div align="center"><bean:message key="knowledgepro.student.syllabus.display.num.chances.left"/></div> </td>
					<td height="25" width="8%" class="row-odd"><div align="center"><bean:message key="knowledgepro.syllabus.display.syllabusyear"/></div> </td>
					
					<td height="25" width="15%" class="row-odd"><div align="center"><bean:message key="knowledgepro.student.syllabus.display.syllabus.link"/></div> </td>
				</tr>
				
				
				
				
                <logic:iterate name="syllabusTrackerForSupplementaryForm" property="syllabusTrackerForSupplementaryTo"
									id="id" indexId="count">
					<tr>
					<c:choose>
						<c:when test="${count%2 == 0}">
							<tr class="row-even">
						</c:when>
						<c:otherwise>
							<tr class="row-odd">
						</c:otherwise>
					</c:choose>
					
						<td width="4%" height="25">
						<div align="center"><c:out value="${count+1}" /></div>
						</td>
						<td width="10%" height="20"><div align="center"><bean:write name="id" property="registerNo" /></div></td>
						<td width="8%" height="20"><div align="center"><bean:write name="id" property="joiningYear" /></div></td>
						<td width="10%" height="20"><div align="center"><bean:write name="id" property="examFirstAttemptedYear" /></div></td>
						<td width="8%" height="20"><div align="center"><bean:write name="id" property="semNo" /></div></td>
						<td width="9%" height="20"><bean:write name="id" property="paperCode" /></td>
						<td width="20%" height="20"><bean:write name="id" property="paperName" /></td>
						<td width="8%" height="20"><div align="center"><bean:write name="id" property="numOfChancesLeft" /></div></td>
						<td width="8%" height="20"><div align="center"><bean:write name="id" property="admitedYear" /></div></td>
						<td>
						
						<logic:equal value="true" name="id" property="isChancesAvailable">
						<div align="center"><a href="#" onclick="getDownloadFile('<bean:write name="id" property="paperCode" />','<bean:write name="id" property="admitedYear" />')">
							<bean:message key="knowledgepro.student.syllabus.display.syllabus.link.message"/></a></div>
						</logic:equal>	
						<logic:equal value="false" name="id" property="isChancesAvailable">
						<bean:message key="knowledgepro.student.syllabus.display.nochances.message"/>
						</logic:equal>			
						</td>
						</tr>
				<c:set var="temp" value="1"/>
				</logic:iterate> 
                
                
                </table></td>
                <td width="5" height="30"  background="images/right.gif"></td>
              </tr>
              <tr>
                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                <td background="images/05.gif"></td>
                <td><img src="images/06.gif" /></td>
              </tr>
              
              <tr bgcolor="#FFFFFF">
	            <td width="100%" height="20" colspan="4">
	            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	              <tr>
					<td align="left">
					Export options:&nbsp;<a href="#" onclick="getDownloadFile()" >Excel</a>
					</td>
				</tr>
            	</table>
            	</td>
          	</tr>
          	
          	<tr bgcolor="#FFFFFF">
	            <td width="100%" height="20" colspan="4">
	            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	              <tr>
					<td align="center">
					<html:button  property="" styleClass="formbutton" value="Cancel" onclick="closeAction()" />
						
				</td>
				</tr>
            	</table>
            	</td>
          	</tr>
          	
            </table></td>
          </tr>
          </logic:notEmpty>
          
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if (year.length != 0) {
	document.getElementById("year").value = year;
}
	</script>