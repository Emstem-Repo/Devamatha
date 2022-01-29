
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>
<title>:: CMS ::</title>
<script type="text/javascript" language="javascript">

	//function changeValueMarksCard(ismcard, isconm,count) {
	//	if (ismcard.value=="false") {
	//		document.getElementById("isCM").disabled=true;
	//		document.getElementById("isCM").disabled=true;
	//	} else {
	//		document.getElementById("isCM").disabled=false;
	//		document.getElementById("isCM").disabled=false;
	//	}
	// }

	function enableConsolidated(count) {
		document.getElementById("CM1_"+count).disabled=false;
		document.getElementById("CM2_"+count).disabled=false;
	}	

	function disableConsolidated(count) {
		document.getElementById("CM1_"+count).disabled=true;
		document.getElementById("CM2_"+count).disabled=true;
	}
	// Functions for AJAX 
	function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);
	}
	
	function updatePrograms(req) {
		updateOptionsFromMap(req,"program","- Select -");
	}
	
	function getCourses(programId) {
		getCoursesByProgram("coursesMap",programId,"course",updateCourses);
	}
	
	function updateCourses(req) {
		updateOptionsFromMap(req,"course","- Select -");
	}


	function deleteChecklist(courseId,year) {
		deleteConfirm =confirm("Are you sure to delete this entry?");
		if(deleteConfirm == true)
		document.location.href = "checkList.do?method=deleteCheckList&year="+year+"&course="+courseId;
	}

	function redirectControl(){
		document.getElementById("method").value="initCheckListEntry";
	}

	function viewChecklist(course,year){
		var url="checkList.do?method=viewCheckListDetails&course="+course+"&year="+year;
		myRef=window.open(url,"checkListDetails","width=600,height=350,left=20,top=20,toolbar=1,resizable=0,scrollbars=1");
	
	}
	function reActivate(){
		var courseId = document.getElementById("cid").value;
		var year = document.getElementById("year").value
		document.location.href = "checkList.do?method=reActivateSetCheckListEntry&course="+courseId+"&year="+year;
	}	
	function getNames(){
		document.getElementById("programTypeName").value = document.getElementById("programType").options[document.getElementById("programType").selectedIndex].text;
		document.getElementById("courseName").value = document.getElementById("course").options[document.getElementById("course").selectedIndex].text;
		document.getElementById("programName").value = document.getElementById("program").options[document.getElementById("program").selectedIndex].text;
	}

    function updateCheckList() {
		document.getElementById("method").value = "editSetCheckListEntry";
		document.checkListForm.submit();
    }    
	
	function editCheckList(programTypeId, programId, courseId, year) {
		document.location.href = "checkList.do?method=editSetCheckListEntry&course="+courseId+"&year="+year+"&programTypeId="+programTypeId+"&program="+programId;		
	}

	function resetMessages() {
		document.getElementById("programType").selectedIndex = 0;
		document.getElementById("program").selectedIndex = 0;
		document.getElementById("course").selectedIndex = 0;
		resetErrMsgs();
	}	
	function getCheckList(year) {
		document.getElementById("year").value = year;
		document.getElementById("method").value = "setCheckList";
		document.checkListForm.submit();
	}
	
</script>
</head>
<html:form action="/checkList" method="POST">
	<html:hidden property="method" styleId="method" value="setCheckListEntry" />
	<html:hidden property="mode" styleId="mode" value="add"/>
	<html:hidden property="docChecklistId" styleId="docChecklistId" />
	<html:hidden property="formName" value="checkListForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="programTypeName" styleId="programTypeName" value=""/>
	<html:hidden property="courseName" styleId="courseName" value=""/>
	<html:hidden property="programName" styleId="programName" value=""/>
	<input type="hidden" name="programTId" id="programTId" value='<bean:write name="checkListForm" property="programTypeId"/>'/>
	<c:if test="${checkListOperation == 'reactivate'}">
		<input type="hidden" id="cid" value="<bean:write name="checkListForm" property="course"/>"/>
		<input type="hidden" id="year" value="<bean:write name="checkListForm" property="year"/>"/>
	</c:if>	
<table width="100%" border="0">
<tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/>
    <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.checklistEntry"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top">
    
    <table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"> <bean:message key="knowledgepro.admission.checklistEntry"/></strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <%-- 
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      --%>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          	<tr>
				<td height="20" colspan="6">
				<div align="right" style="color:red"> <span class='MandatoryMark'>* Mandatory fields</span></div>
				<div id="errorMessage">
				<FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
				</html:messages> </FONT>
			 </tr>
        
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td width="15%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>
                    <bean:message key="knowledgepro.admission.programtype" />:</div></td>
                    <td width="20%" class="row-even" ><label>
						
                       	<html:select property="programTypeId" styleClass="combo" styleId="programType" onchange="getPrograms(this.value)">
							<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
							<cms:renderProgramTypes></cms:renderProgramTypes>
						</html:select></label></td>
                    <td width="15%" class="row-odd" >
                   <div align="right"><span class="Mandatory">*</span>
                   <bean:message key="knowledgepro.admission.program" />:</div></td>
                    <td width="30%" class="row-even" ><span class="row-even">
                    	<html:select name="checkListForm" property="program" styleId="program"  styleClass="combo" onchange="getCourses(this.value)">
							<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
							<c:choose>
                       				<c:when test="${checkListOperation == 'edit'}">
                       					<html:optionsCollection name="programMap" label="value" value="key"/>
                       				</c:when>
                   			   		<c:otherwise>
                    			    	<c:if test="${checkListForm.programTypeId != null && checkListForm.programTypeId != ''}">
						                <c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
					                        <c:if test="${programMap != null}">
					                            <html:optionsCollection name="programMap" label="value" value="key"/>
					                       </c:if>	 
						                </c:if>
                       		   		</c:otherwise>
                      		</c:choose>
						</html:select> </span>
						</td>
                    </tr>
                  <tr >
                    <td width="15%" height="25" class="row-odd" align="right"><div align="right">
                    <span class="Mandatory">*</span><bean:message key="knowledgepro.admission.course" />:</div></td>
						<td width="20%" class="row-even">
						<html:select name="checkListForm" property="course" styleId="course"  styleClass="combo">
							<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<c:choose>
                    				 	<c:when test="${checkListOperation == 'edit'}">
                    				 		<html:optionsCollection name="courseMap" label="value" value="key"/>
                  				    	</c:when>
	                  				   	<c:otherwise>
				                  		<c:if test="${checkListForm.program != null && checkListForm.program != ''}">
						                    <c:set var="courseMap" value="${baseActionForm.collectionMap['coursesMap']}"/>
					                          	<c:if test="${courseMap != null}">
					                           		<html:optionsCollection name="courseMap" label="value" value="key"/>
					                       		</c:if>	 
						                    </c:if>
	                      		   		</c:otherwise>
                     		   		</c:choose>
					</html:select></td>
                    <td width="15%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>
                    		<bean:message key="knowledgepro.admin.year" />:</div></td>
						<td width="30%" class="row-even">
						<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="checkListForm" property="year"/>"/>
						<html:select property="year" styleId="year"  styleClass="combo" onchange="getCheckList(this.value)">
							<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
						<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
						</html:select></td>
                 </tr>
              </table></td>
              <td width="5" height="54"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
        </table>
        
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="45%" height="35"><div align="right">
              		<html:submit property="" styleId="submitButton" styleClass="formbutton" onclick="getNames()" value="Add Checklist"/>

	          </div></td>
			<td width="2%"></td>
			<td width="40%"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetMessages()">
			<bean:message key="knowledgepro.cancel"/></html:button>
			</td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
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
              <tr>
					<td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
					<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.programtype" /></td>
					<td class="row-odd" align="center"><bean:message key="knowledgepro.admin.program" /></td>
					<td class="row-odd" align="center"><bean:message key="knowledgepro.admin.course" /></td>
					<td class="row-odd" align="center"><bean:message key="knowledgepro.admin.year" /></td>
					<td class="row-odd" align="center"><bean:message key="knowledgepro.view"/></td>
					<td class="row-odd" ><div align="center"><bean:message key="knowledgepro.edit" /></div></td>
					<td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete" /></div></td>
			  </tr>
	
			<logic:notEmpty name="checkListForm" property="checkList">
				<nested:iterate id="clist" name="checkListForm" property="checkList" indexId="sno">
					<%
						String dynamicStyle = "";
						if (sno % 2 != 0) {
						dynamicStyle = "row-white";
						} else {
						dynamicStyle = "row-even";
						}
					%>
				<tr>
					<td width="4%" height="25" align="center" class="<%=dynamicStyle%>"><%=(sno + 1)%></td>
					<td width="10%" height="25" class="<%=dynamicStyle%>" align="center">
					<nested:write name="clist" property="courseTo.programTo.programTypeTo.programTypeName" /></td>
					<td width="9%" align="center" class="<%=dynamicStyle%>">
					<nested:write name="clist" property="courseTo.programTo.name" /></td>
					<td width="9%" align="center" class="<%=dynamicStyle%>">
					<nested:write name="clist" property="courseTo.code" /></td>
					<td width="9%" align="center" class="<%=dynamicStyle%>">
					<nested:write name="clist" property="combinedYear" /></td>
					<td width="10%" align="center" class="<%=dynamicStyle%>">
					<div align="center"><img src="images/View_icon.gif" width="16" height="16" style="cursor:pointer"
					onclick="viewChecklist('<nested:write name="clist" property="courseTo.id" />','<nested:write name="clist" property="year" />')"></div>
					</td>
					<td width="8%" height="25" class="<%=dynamicStyle%>">
					<div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer"
					onclick="editCheckList('<nested:write name="clist" property="courseTo.programTo.programTypeTo.programTypeId" />',
					'<nested:write name="clist" property="courseTo.programTo.id" />',
					'<nested:write name="clist" property="courseTo.id" />',
					'<nested:write name="clist" property="year" />')" /></div></td>
					<td width="9%" height="25" class="<%=dynamicStyle%>">
					<div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
					onclick="deleteChecklist('<nested:write name="clist" property="courseTo.id" />','<nested:write name="clist" property="year" />')"></div></td>
				</tr>

				</nested:iterate>
			</logic:notEmpty>
					
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
        			<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        			<td valign="top" class="news">&nbsp;</td>
        			<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
    		  	</tr>
   		 	  	<tr>
        			<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        			<td width="100%" background="images/TcenterD.gif"></td>
        			<td><img src="images/Tright_02.gif" width="9" height="29"></td>
      	  		</tr>
    	</table>
    	</td>
  </tr>
</table>
	
</html:form>
<script type="text/javascript" language="javascript">
var programTypeId = document.getElementById("programTId").value;
if(programTypeId.length != 0){
	document.getElementById("programType").value= programTypeId;
}


var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}

var operation = "<c:out value='${checkListOperation}'/>";
if(operation.length != 0 && operation == 'edit') {
	document.getElementById("programType").disabled = true;
	document.getElementById("program").disabled = true;
	document.getElementById("course").disabled = true;
	document.getElementById("year").disabled = true;
}

</script>
