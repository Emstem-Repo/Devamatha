<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<html:form action="/syllabusEntryHodApproval" >
<html:hidden property="formName" value="syllabusEntryHodApprovalForm" />
<body>
<table width="98%" border="0">
		<tr>
			<td colspan="2" align="right">SYLLABUS-<bean:write name="syllabusEntryHodApprovalForm" property="batchYear"/></td>
		</tr>
  		<tr>
             <td height="49" class="body" width="100%">
	             <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	               <tr>
	                 <td ><img src="images/01.gif" width="5" height="5" /></td>
	                 <td width="914" background="images/02.gif"></td>
	                 <td><img src="images/03.gif" width="5" height="5" /></td>
	               </tr>
	               <tr>
	                 <td width="5"  background="images/left.gif"></td>
	                 <td valign="top">
	                 <table width="100%" cellspacing="1" cellpadding="2">
	                 <!--start-->
	                 	<tr height="5">
	                 		<td align="center" colspan="4"><b><font ><bean:write name="syllabusEntryHodApprovalForm" property="subjectCode"/>-<bean:write name="syllabusEntryHodApprovalForm" property="subjectName"/></font></b><br/><br/></td>
	                 	</tr>
	                 	<tr height="5">
	                 		<td width="50%"><b><font size="3">Total Teaching Hours For Semester:<bean:write name="syllabusEntryHodApprovalForm" property="totTeachHrsPerSem"/></font></b><br/> </td>
	                 		<td width="50%" align="right"><b><font size="3">No of Lecture Hours/Week:<bean:write name="syllabusEntryHodApprovalForm" property="noOfLectureHrsPerWeek"/></font></b><br/> </td>
	                 	</tr>
	                 	<tr height="5">
	                 		<td width="50%"><b><font size="3">Max Marks:<bean:write name="syllabusEntryHodApprovalForm" property="maxMarks"/></font></b><br/> </td>
	                 		<td width="50%" align="right"><b><font size="3">Credits:<bean:write name="syllabusEntryHodApprovalForm" property="credits"/></font></b><br/> </td>
	                 	</tr>
	                 	<logic:notEmpty name="syllabusEntryHodApprovalForm" property="courseObjective">
	                 	<tr height="5">
	                 		<td  colspan="2">
	                 			<c:out value='${syllabusEntryHodApprovalForm.courseObjective}' escapeXml='false' />
	                 		</td>
	                 	</tr>
	                 	</logic:notEmpty>
	                 	<logic:notEmpty name="syllabusEntryHodApprovalForm" property="lerningOutcome">
	                 	<tr height="5">
	                 		<td colspan="2"><c:out value='${syllabusEntryHodApprovalForm.lerningOutcome}' escapeXml='false' /></td>
	                 	</tr>
	                 	</logic:notEmpty>
	                 	<nested:iterate id="CME" name="syllabusEntryHodApprovalForm" property="syllabusEntryUnitsHoursTos" indexId="count" type="com.kp.cms.to.admin.SyllabusEntryUnitsHoursTo">
	                 	<tr height="5">
	                 		<td><b><font size="3"><bean:write name="CME" property="units"/></font></b></td>
	                 		<td align="right"><b><font size="2"><bean:write name="CME" property="teachingHoursTemplate"/>:<bean:write name="CME" property="teachingHours"/></font></b><br/></td>
	                 	</tr>
	                 	<tr height="2"></tr>
	                 	<tr height="2"> 
				                 <td colspan="2">
	                 				<nested:iterate id="CME1" property="syllabusEntryHeadingDescTos" indexId="cnt">
		                 				<logic:notEmpty property="heading" name="CME1">
			                 				<table width="100%">
			                 					<tr height="1">
			                 						 <td>&nbsp;<b><font size="2"><bean:write name="CME1" property="heading"/></font></b></td>
			                 					</tr>
			                 				</table>
					                 	</logic:notEmpty>
					                 	<logic:notEmpty property="description" name="CME1">
				                 				<table width="100%">
				                 					<tr height="1">
				                 						<td width="1%"></td>
				                 						<td>
				                 							<font size="2">&emsp;&emsp;<bean:write name="CME1" property="description"/></font>
				                 						</td>
				                 					</tr>
				                 				</table>
					                 	</logic:notEmpty>
			                 		</nested:iterate>
			                 </td>
				           </tr>
				           <tr height="2"></tr>
	                 	</nested:iterate>
	                 	<logic:notEmpty name="syllabusEntryHodApprovalForm" property="textBooksAndRefBooks">
	                 	<tr height="5">
	                 		<td colspan="2">
	                 			<c:out value='${syllabusEntryHodApprovalForm.textBooksAndRefBooks}' escapeXml='false' />
	                 		</td>
	                 	</tr>
	                 	</logic:notEmpty>
	                 	<logic:notEmpty name="syllabusEntryHodApprovalForm" property="recommendedReading">
	                 	<tr height="5">
	                 		<td colspan="2">
	                 			<c:out value='${syllabusEntryHodApprovalForm.recommendedReading}' escapeXml='false' />
	                 		</td>
	                 	</tr>
	                 	</logic:notEmpty>
	                 	<logic:notEmpty name="syllabusEntryHodApprovalForm" property="freeText">
	                 	<tr height="5">
	                 		<td colspan="2">
	                 			<c:out value='${syllabusEntryHodApprovalForm.freeText}' escapeXml='false' />
	                 		</td>
	                 	</tr>
	                 	</logic:notEmpty>
	            	 <!--end-->
	                 </table></td>
	                 <td width="5" height="30"  background="images/right.gif"></td>
	               </tr>
	               <tr>
	                 <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	                 <td background="images/05.gif"></td>
	                 <td><img src="images/06.gif" /></td>
	               </tr>
	             </table>
           </td>
      </tr>
      <tr>
			<td colspan="2"><bean:write name="syllabusEntryHodApprovalForm" property="departmentName"/>,MOUNT CARMEL</td>
		</tr>
</table>
</body>
</html:form>