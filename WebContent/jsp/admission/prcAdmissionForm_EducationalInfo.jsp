<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/prcadmissionform.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function detailSubmit(count)
	{
		document.getElementById("countID").value=count;
		document.admissionFormForm.method.value="initDetailMarkPage";
		document.admissionFormForm.submit();
	}
	function detailSemesterSubmit(count)
	{
		document.getElementById("countID").value=count;
		document.admissionFormForm.method.value="initSemesterMarkPage";
		document.admissionFormForm.submit();
	}
	function detailLateralSubmit()
	{
		document.admissionFormForm.method.value="initlateralEntryPage";
		document.admissionFormForm.submit();
	}
	function detailTransferSubmit()
	{
		document.admissionFormForm.method.value="initTransferEntryPage";
		document.admissionFormForm.submit();
	}
</script>

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<html:form action="/presidanceadmissionFormSubmit" method="POST">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="3"/>
<html:hidden property="formName" value="admissionFormForm"/>
<logic:equal value="false" property="onlineApply" name="admissionFormForm">
<div style="overflow: auto; width: 800px;"></logic:equal> 
<table width="98%" border="0">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" ></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><span class="boxheader"><bean:message key="knowledgepro.admission"/> &gt;&gt; <bean:message key="knowledgepro.applicationform"/> &gt;&gt;</span></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td height="47" class="heading"><img src="images/Educational_tab.jpg" width="664" height="33" border="0" ></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
			<tr><td colspan="10" valign="top" align="left"><div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div><div id="errorMessage"><html:errors/></div></td></tr>
	<logic:notEmpty name="admissionFormForm" property="qualifications">
          <tr >
            <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
            <td height="25" class="row-odd" ><bean:message key="knowledgepro.admission.qualification"/></td>
			<td class="row-odd" ><div align="center">Exam Name</div></td>
			
            <td class="row-odd" ><div align="center"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.universityBoard"/></div></td>
            <td class="row-odd" ><div align="center"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.instituteName"/></div></td>
			<td class="row-odd" ><div align="center"><bean:message key="admissionForm.education.State.label"/></div></td>
            <td class="row-odd" ><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.passingYear"/></td>
            <td class="row-odd" ><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.passingmonth"/></td>
            <td class="row-odd" ><div align="center"><span class="Mandatory">*</span><bean:message key="admissionForm.education.attempt.label"/></div></td>
			<td class="row-odd" ><div align="center"><span class="Mandatory"></span><bean:message key="knowledgepro.applicationform.prevregno.label"/></div></td>
			<td class="row-odd" ><div align="center"><span class="Mandatory"></span><bean:message key="admissionForm.education.markObtained.label"/></div></td>
            <td class="row-odd" ><div align="center"><span class="Mandatory"></span><bean:message key="knowledgepro.admission.totalMarks"/></div></td>
            <td class="row-odd" ><div align="center"><span class="Mandatory">*</span>Percentage</div></td>
            </tr>
	</logic:notEmpty>
          <nested:iterate  name="admissionFormForm" property="qualifications" indexId="count" id="qualDoc">
			<%
				String dynamicStyle="";
				String oppStyle="";
				String dynaid="UniversitySelect"+count;
				String dynaYearId="YOP"+count;
				String dynamonthId="Month"+count;
				String dynaexamId="Exam"+count;
				String dynaAttemptId="Attempt"+count;
				String dynaStateId="State"+count;
				String dynarow1="University"+count;
				String dynarow2="Institute"+count;
				String instituteId=count+"Institute";
				String collegeJsMethod="getColleges('Map"+count+"',this,"+count+");";
				String instituteJsMethod="funcOtherInstituteShowHide('"+instituteId+"','"+dynarow2+"','"+count+"');";
				String showhide=collegeJsMethod+"funcOtherUniversityShowHide('"+dynarow1+"','"+dynaid+"','"+dynarow2+"','"+instituteId +"');";
				//String dateproperty="qualifications["+count+"].yearPassing";
				if(count%2!=0){
					dynamicStyle="row-white";
					oppStyle="row-even";
				}else{
					dynamicStyle="row-even";
					oppStyle="row-white";
				}
				String dynaMap="Map"+count;
				
			%>
			<bean:define id="countIndex" name="qualDoc" property="countId"></bean:define>
			<input type="hidden" id="countID" name="countID" >
          <tr >
            <td height="25" class='<%=dynamicStyle %>'><div align="center"><%=count+1 %></div></td>
            <td height="25" class='<%=dynamicStyle %>'><nested:write property="docName"/> </td>
			
			<td class='<%=dynamicStyle %>'  >
			<span class='<%=oppStyle %>'>
				<logic:equal value="true" name="qualDoc" property="examConfigured">
				<c:set var="dexamid"><%=dynaexamId %></c:set>
			<nested:select property="selectedExamId" styleId='<%=dynaexamId %>' styleClass="comboSmall">
				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
				<logic:notEmpty name="qualDoc" property="examTos">
					<html:optionsCollection name="qualDoc" property="examTos" label="name" value="id"/>
				</logic:notEmpty>				

			</nested:select>
				<script type="text/javascript">
					var exmid= '<nested:write property="selectedExamId"/>';
					document.getElementById("<c:out value='${dexamid}'/>").value = exmid;
				</script>
				</logic:equal>

             </span></td>
            <td class='<%=dynamicStyle %>' >
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
				<td  style=" padding-top: 30Px;">
               <c:set var="dunid"><%=dynaid %></c:set>
               <nested:select property="universityId" styleId="<%=dynaid %>" styleClass="comboLarge" onchange="<%=showhide %>">
					<option value=""><bean:message key="knowledgepro.admin.select"/></option>
					<logic:iterate id="option" property="universities" name="admissionFormForm">
						<option value='<bean:write name="option" property="id"/>'><bean:write name="option" property="name"/> </option>
					</logic:iterate>
						<option value="Other"><bean:message key="knowledgepro.admin.Other"/> </option>
               </nested:select>
					<script type="text/javascript">
					var id= '<nested:write property="universityId"/>';
					document.getElementById("<c:out value='${dunid}'/>").value = id;
					</script>
            	</td>
				</tr>
				<tr>
					<%String dynaStyle="" ;%>
                  <td width="123" height="30">
					<logic:equal value="Other" name="qualDoc" property="universityId">
                	<%dynaStyle="display: block;" ;%>
					</logic:equal>
					<logic:notEqual value="Other" name="qualDoc" property="universityId">
                  	<%dynaStyle="display: none;" ;%>
					</logic:notEqual>
  					<nested:text property="universityOthers" size="10" maxlength="50" style='<%=dynaStyle %>' styleId='<%=dynarow1 %>'></nested:text>
                  </td>
                  <td width="217">&nbsp;</td>
                </tr>
			</table></td>
            <td class='<%=dynamicStyle %>' ><table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
				<td  style=" padding-top: 30Px;">
				<c:set var="dinid"><%=instituteId %></c:set>
                <c:set var="temp"><nested:write property="universityId"/></c:set>
	                <nested:select property="institutionId" styleClass="combo" styleId='<%=instituteId %>' onchange='<%=instituteJsMethod %>' >
					<option value=""><bean:message key="knowledgepro.admin.select"/></option>
						<c:set var="tempKey">Map<c:out value="${count}"/></c:set>	
						<c:if test="${temp != null && temp != '' && temp != 'Other'}">
                             <c:set var="Map" value="${baseActionForm.collectionMap[tempKey]}"/>
							<html:optionsCollection name="Map" label="value" value="key"/>
							
						</c:if>
						<html:option value="Other"><bean:message key="knowledgepro.admin.Other"/></html:option>
					</nested:select>
					<script type="text/javascript">
					var inId= '<nested:write property="institutionId"/>';
					document.getElementById("<c:out value='${dinid}'/>").value = inId;
					</script>
	           </td>
				</tr>
			
				 <tr >
                  <td width="150" height="30">
					<logic:equal value="Other" name="qualDoc" property="institutionId">
                  	<%dynaStyle="display: block;" ;%>
					</logic:equal>
					<logic:notEqual value="Other" name="qualDoc" property="institutionId">
                  	<%dynaStyle="display: none;" ;%>
					</logic:notEqual>
					<nested:text property="otherInstitute" size="10" maxlength="50" style='<%=dynaStyle %>' styleId='<%=dynarow2 %>'></nested:text>
                  </td>
                
                </tr>
            </table>
			</td>
			
			<td class='<%=dynamicStyle %>' ><div align="center"><span class='<%=oppStyle %>'>
				<c:set var="dStateid"><%=dynaStateId %></c:set>
                <nested:select property="stateId" styleId='<%=dynaStateId %>' styleClass="comboMedium">
					 <option value=""><bean:message key="knowledgepro.admin.select"/></option>
					<logic:notEmpty name="admissionFormForm" property="ednStates">
					 <nested:optionsCollection name="admissionFormForm" property="ednStates" label="name" value="id"/>
					</logic:notEmpty>
					<option value="OUTSIDEINDIA"><bean:message key="admissionForm.education.outsideindia.label"/></option>
                </nested:select>
				<script type="text/javascript">
					var dStid= '<nested:write property="stateId"/>';
					document.getElementById("<c:out value='${dStateid}'/>").value = dStid;
				</script>
            </span></div></td>

            <td class='<%=dynamicStyle %>'  ><span class='<%=oppStyle %>'>
				<c:set var="dyopid"><%=dynaYearId %></c:set>
			<nested:select property="yearPassing" styleId='<%=dynaYearId %>' styleClass="comboSmall">
				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
              	<cms:renderYear normalYear="true"></cms:renderYear>
			</nested:select>
				<script type="text/javascript">
					var yopid= '<nested:write property="yearPassing"/>';
					document.getElementById("<c:out value='${dyopid}'/>").value = yopid;
				</script>
             </span></td>
			 <td class='<%=dynamicStyle %>'  ><span class='<%=oppStyle %>'>
				<c:set var="dmonid"><%=dynamonthId %></c:set>
			<nested:select property="monthPassing" styleId='<%=dynamonthId %>' styleClass="comboSmall">
				<html:option value="0"><bean:message key="knowledgepro.admin.select"/></html:option>
				<html:option value="1">JAN</html:option>
              	<html:option value="2">FEB</html:option>
				<html:option value="3">MAR</html:option>
				<html:option value="4">APR</html:option>
				<html:option value="5">MAY</html:option>
				<html:option value="6">JUN</html:option>
				<html:option value="7">JUL</html:option>
				<html:option value="8">AUG</html:option>
				<html:option value="9">SEPT</html:option>
				<html:option value="10">OCT</html:option>
				<html:option value="11">NOV</html:option>
				<html:option value="12">DEC</html:option>
			</nested:select>
				<script type="text/javascript">
					var monid= '<nested:write property="monthPassing"/>';
					document.getElementById("<c:out value='${dmonid}'/>").value = monid;
				</script>
             </span></td>
            <td class='<%=dynamicStyle %>' ><div align="center"><span class='<%=oppStyle %>'>
				<c:set var="dAttemptid"><%=dynaAttemptId %></c:set>
                <nested:select property="noOfAttempts" styleId='<%=dynaAttemptId %>' styleClass="comboSmall">
					 <option value=""><bean:message key="knowledgepro.admin.select"/></option>
					 <option value="1">1</option>
					 <option value="2">2</option>
					 <option value="3">3</option>
					 <option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
                </nested:select>
				<script type="text/javascript">
					var dAttemid= '<nested:write property="noOfAttempts"/>';
					document.getElementById("<c:out value='${dAttemptid}'/>").value = dAttemid;
				</script>
            </span></div></td>
			
				 <td class='<%=dynamicStyle %>' ><div align="center"><span class='<%=oppStyle %>'>
			<logic:equal value="true" name="qualDoc" property="lastExam">
				<nested:text property="previousRegNo" size="10" maxlength="15"/></logic:equal>
            </span></div></td>

			<nested:equal value="false"  property="consolidated">
				<nested:equal value="true"  property="semesterWise">
					<%String detailSemesterLink="admissionFormSubmit.do?method=initSemesterMarkPage&countID="+countIndex; %>
          		  	<td colspan="2" class='<%=dynamicStyle %>' ><div align="center" class="body"><span class="Mandatory">*</span><a href="#" onclick="detailSemesterSubmit('<%=countIndex %>')"><bean:message key="knowledgepro.applicationform.semsestermark.link"/></a></div></td>
				</nested:equal>
				<nested:equal value="false"  property="semesterWise">
				<%String detailMarkLink="admissionFormSubmit.do?method=initDetailMarkPage&countID="+countIndex; %>
	          		  <td colspan="2" class='<%=dynamicStyle %>' ><div align="center" class="body"><span class="Mandatory">*</span><a href="#" onclick="detailSubmit('<%=countIndex %>')" ><bean:message key="knowledgepro.applicationform.detailmark.link"/></a></div></td>
				</nested:equal>
			</nested:equal>
			
			<nested:equal value="true" property="consolidated">
          	<td width="9%" class='<%=dynamicStyle %>'><div align="center">
              <nested:text  property="marksObtained" size="5" maxlength="8"></nested:text>
            </div></td>
            <td width="8%" class='<%=dynamicStyle %>'><div align="center">
              <nested:text property="totalMarks" size="5" maxlength="8"></nested:text>
            </div></td>
			</nested:equal>
			<td width="8%" class='<%=dynamicStyle %>'><div align="center">
              <nested:text property="percentage" size="5" maxlength="8" onkeypress="return isDecimalNumberKey(this.value,event)"></nested:text>
            </div></td>
          </tr>
			
		</nested:iterate>
		<logic:equal value="true" property="displayTCDetails" name="admissionFormForm">
			 <tr >
			<td class="row-odd" ><div align="center">&nbsp;</div></td>
            <td height="25" colspan="2" class="row-odd" ><div align="center"><bean:message key="admissionForm.education.TCNO.label"/> </div></td>
            <td height="25" colspan="2" class="row-odd" ><div align="center"><bean:message key="admissionForm.education.TCDate.label"/><bean:message key="admissionForm.application.dateformat.label"/></div></td>
			<td class="row-odd" colspan="2"><div align="center"><bean:message key="admissionForm.education.markcard.label"/></div></td>
            <td class="row-odd" colspan="2"><div align="center"><bean:message key="admissionForm.education.markcarddate.label"/><bean:message key="admissionForm.application.dateformat.label"/></div></td>
            
            <td class="row-odd" ><div align="center">&nbsp;</div></td>
			<td class="row-odd" ><div align="center">&nbsp;</div></td>
            </tr>
			 <tr >
				<td class="row-even" ><div align="center">&nbsp;</div></td>
            	<td height="25" colspan="2" class="row-even" ><div align="center">
					<html:text property="tcNo" size="10" maxlength="15"></html:text>
				</div></td>
            	<td height="25" colspan="2" class="row-even" ><div align="center">
				<html:text property="tcDate" styleId="tcdate" size="10" maxlength="10"></html:text><script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'admissionFormForm',
								// input name
								'controlname' :'tcdate'
							});
						</script>
				</div></td>
				<td class="row-even" colspan="2"><div align="center">
				<html:text property="markcardNo" size="10" maxlength="15"></html:text>
				</div></td>
            	<td class="row-even" colspan="2"><div align="center">
				<html:text property="markcardDate" styleId="markdate" size="10" maxlength="10"></html:text><script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'admissionFormForm',
								// input name
								'controlname' :'markdate'
							});
						</script>
				</div></td>
            
            	<td class="row-even"><div align="center">&nbsp;</div></td>
				<td class="row-even"><div align="center">&nbsp;</div></td>
            </tr>
		</logic:equal>
		<logic:equal value="true" property="displayLateralTransfer" name="admissionFormForm">
			 <tr >
			<td class="row-even" ><div align="center">&nbsp;</div></td>
            <td height="25" colspan="2" class="row-even" >
				<logic:equal value="true" property="displayLateralDetails" name="admissionFormForm">
					<div align="center"><a href="#" onclick="detailLateralSubmit()"><bean:message key="admissionForm.education.laterallink.label"/> </a></div>

				</logic:equal>
			</td>
            <td height="25" colspan="2" class="row-even" ><div align="center">&nbsp;</div></td>
			<td class="row-even" colspan="2">
				
			<logic:equal value="true" property="displayTransferCourse" name="admissionFormForm">
			<div align="center"><a href="#" onclick="detailTransferSubmit()"><bean:message key="admissionForm.education.transferlink.label"/></a></div>
			</logic:equal>
			</td>
            <td class="row-even" colspan="2">
			<div align="center">&nbsp;</div>
			</td>
            
            <td class="row-even" ><div align="center">&nbsp;</div></td>
			<td class="row-even"><div align="center">&nbsp;</div></td>
            </tr>
			 
		</logic:equal>
        </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>


	<logic:equal value="true" property="displayEntranceDetails" name="admissionFormForm">

     <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
			<tr><td colspan="7" valign="top" align="left"></td></tr>
          <tr >
            <td height="25" class="row-odd" colspan="6" ><div align="center"><bean:message key="admissionForm.education.entrancedetails.label"/> </div></td>
            </tr>
          <tr >
            <td height="25" class="row-even"><div align="left"><bean:message key="admissionForm.education.entrance.label"/></div></td>
			<td height="25" class="row-even"><div align="left">
			<html:select property="entranceId" styleClass="comboLarge">
					<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					<logic:notEmpty property="entranceList" name="admissionFormForm">
					<html:optionsCollection property="entranceList" name="admissionFormForm" label="name" value="id"/>
					</logic:notEmpty>
										
			</html:select></div></td>
			<td height="25" class="row-even"><div align="left"><bean:message key="knowledgepro.admission.totalMarks"/>:</div></td>
			<td height="25" class="row-even"><div align="left"><html:text property="entranceTotalMark" size="20" maxlength="8"></html:text></div></td>
			<td height="25" class="row-even"><div align="left"><bean:message key="admissionForm.education.markObtained.label"/>:</div></td>
			<td height="25" class="row-even"><div align="left"><html:text property="entranceMarkObtained" size="20" maxlength="8"></html:text></div></td>
            </tr>
			<tr >
            <td height="25" class="row-even"><div align="left"><bean:message key="knowledgepro.applicationform.prereq.roll.label"/> </div></td>
			<td height="25" class="row-even"><div align="left"><html:text property="entranceRollNo" size="20" maxlength="25"></html:text></div></td>
			<td height="25" class="row-even"><div align="left"><bean:message key="knowledgepro.applicationform.passingmonth"/>: </div></td>
			<td height="25" class="row-even"><div align="left">
			<html:select property="entranceMonthPass"  styleClass="comboMedium">
				<html:option value="0"><bean:message key="knowledgepro.admin.select"/></html:option>
				<html:option value="1">JAN</html:option>
              	<html:option value="2">FEB</html:option>
				<html:option value="3">MAR</html:option>
				<html:option value="4">APR</html:option>
				<html:option value="5">MAY</html:option>
				<html:option value="6">JUN</html:option>
				<html:option value="7">JUL</html:option>
				<html:option value="8">AUG</html:option>
				<html:option value="9">SEPT</html:option>
				<html:option value="10">OCT</html:option>
				<html:option value="11">NOV</html:option>
				<html:option value="12">DEC</html:option>
			</html:select>
			</div></td>
			<td height="25" class="row-even"><div align="left"><bean:message key="knowledgepro.admission.passingYear"/>:</div></td>
			<td height="25" class="row-even"><div align="left">
			
			<nested:select property="entranceYearPass" styleId='entranceyear' styleClass="comboMedium">
				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
              	<cms:renderYear normalYear="true"></cms:renderYear>
			</nested:select>
				<script type="text/javascript">
					var entyopid= '<nested:write property="entranceYearPass"/>';
					document.getElementById("entranceyear").value = entyopid;
				</script>
			</div></td>
            </tr>
			
        </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>

	</logic:equal>


          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="48%" height="35"><div align="right">
                <html:button property="" onclick="submitAdmissionForm('submitEducationInfo')" styleClass="formbutton" value="Continue"></html:button>
            </div></td>
            <td width="1%"><div align="center"></div></td>
            <td width="51%"><div align="left"><html:button property=""  styleClass="formbutton" value="Reset" onclick="submitAdmissionForm('initEducationPage')"></html:button></div></td>
          </tr>
        </table>
              <div align="center"></div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" ></td>
        <td  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
<logic:equal value="false" property="onlineApply" name="admissionFormForm"></div></logic:equal> 

</html:form>
</body>
</html:html>
