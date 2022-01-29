<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" language="javascript">

//Functions for AJAX 
function getPrograms(programTypeId) {
getProgramsByType("programMap",programTypeId,"program",updatePrograms);
resetOption("course");
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

function enableIsMarksCardYes(count) {
	document.getElementById("CM1_"+count).disabled=false;
	document.getElementById("CM2_"+count).disabled=false;
	document.getElementById("SEM1_"+count).disabled=false;
	document.getElementById("SEM2_"+count).disabled=false;
	document.getElementById("PEXAM1_"+count).disabled=false;
	document.getElementById("PEXAM2_"+count).disabled=false;
	document.getElementById("examReq1_"+count).disabled=false;
	document.getElementById("examReq2_"+count).disabled=false;
}	

function disableIsMarksCardNo(count) {
	document.getElementById("CM1_"+count).disabled=true;
	document.getElementById("CM2_"+count).disabled=true;
	document.getElementById("CM2_"+count).checked = true;
	document.getElementById("SEM1_"+count).disabled=true;
	document.getElementById("SEM2_"+count).disabled=true;
	document.getElementById("SEM2_"+count).checked = true;
	document.getElementById("PEXAM1_"+count).disabled=true;
	document.getElementById("PEXAM2_"+count).disabled=true;
	document.getElementById("PEXAM2_"+count).checked = true;
	document.getElementById("examReq1_"+count).disabled=true;
	document.getElementById("examReq2_"+count).disabled=true;
	document.getElementById("examReq2_"+count).checked = true;
	
	document.getElementById("LAN1_"+count).disabled = true;
	document.getElementById("LAN2_"+count).disabled = true;
	document.getElementById("LAN2_"+count).checked = true;
}

function enableIsSemMarks(count) {
	document.getElementById("SEM1_"+count).disabled=false;
	document.getElementById("SEM2_"+count).disabled=false;
	document.getElementById("LAN1_"+count).disabled=false;
	document.getElementById("LAN2_"+count).disabled=false;
}

function disableIsSemMarks(count) {
	document.getElementById("SEM1_"+count).disabled=true;
	document.getElementById("SEM2_"+count).disabled=true;
	document.getElementById("LAN1_"+count).disabled=true;
	document.getElementById("LAN2_"+count).disabled=true;
	document.getElementById("LAN2_"+count).checked=true;
	document.getElementById("SEM2_"+count).checked=true;
	
}


function enableRadioButtons(count){
	var sel="select_"+count;
	if(sel!=null){
	document.getElementById("NTP1_"+count).disabled=false;
	document.getElementById("NTP2_"+count).disabled=false;
	document.getElementById("MC1_"+count).disabled=false;
	document.getElementById("MC2_"+count).disabled=false;
	}
}
function redirectCancel(){
	document.location.href="checkListDocs.do?method=initCheckListEntry";
}

function add(){
	if(validateCheckBox()) {
		document.getElementById("method").value="addCheckList";
		document.checkListForm.submit();
	}
}

function update(){
	if(validateCheckBox()) {
		document.getElementById("method").value="updateCheckList";
		document.checkListForm.submit();
	}
}

function validateCheckBox() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   	}
		}
    }

	var count = document.getElementById("count").value;
	var prevExamCount = 0;
	var marksCardCount = 0;
    for(var j=0;j<count;j++) {
    	var isMarSelected = document.getElementById("Marks1_"+j).checked;
    	if(isMarSelected == true) {
    		marksCardCount++;
    	}	
    	var isPrevSelected = document.getElementById("PEXAM1_"+j).checked;
    	if(isPrevSelected == true) {
    		prevExamCount++;
    	}
    }    
    if(marksCardCount != 0) {
		if(prevExamCount == 0) {
			 document.getElementById("err").innerHTML = "Please select previous exam for which markscard selected.";
			 return false;
		}	
		//if(prevExamCount > 1){
		//	 document.getElementById("err").innerHTML = "Please select only one previous exam for which markscard selected.";
		//	 return false;
		//}	
	}    

    if(checkBoxselectedCount == 0) {
        document.getElementById("err").innerHTML = "Please select at least one document.";
        document.getElementById("errorMessage").style.display = "none";
    	return false;
    }    
    else { 
        return true;
    }    
}

function changeValue(count) {
    var checkObj = document.getElementById("selected1_"+count);
	if(checkObj.checked == true){
		checkObj.checked = true;
		checkObj.value = "true";
	}else{
		checkObj.checked = false;
		checkObj.value = "false";
	}
}

function reActivate(){
	var courseId = document.getElementById("cid").value;
	var year = document.getElementById("year").value
	document.location.href = "checkList.do?method=reActivateSetCheckListEntry&course="+courseId+"&year="+year;
}

function resetDocument(id) {
	var obj = document.getElementById("selected_"+id);
	if(!obj.checked) {
		document.getElementById("needToProduce2_"+id).checked = true;
		document.getElementById("Marks2_"+id).checked = true;
		document.getElementById("CM2_"+id).checked = true;
		document.getElementById("SEM2_"+id).checked = true;
		document.getElementById("PEXAM2_"+id).checked = true;
		document.getElementById("LAN2_"+id).checked = true;
		
		document.getElementById("CM1_"+id).disabled = true;
	    document.getElementById("CM2_"+id).disabled = true;
	    document.getElementById("SEM1_"+id).disabled = true;
	    document.getElementById("SEM2_"+id).disabled = true;
	    document.getElementById("PEXAM1_"+id).disabled = true;
	    document.getElementById("PEXAM2_"+id).disabled = true;
	    document.getElementById("LAN1_"+id).disabled = true;
		document.getElementById("LAN2_"+id).disabled = true;
		document.getElementById("needToProduce1_mc_"+id).disabled = true;
		 document.getElementById("needToProduce2_mc_"+id).disabled = true;
	}

}

function enableIsLanguage(id){
	 document.getElementById("LAN1_"+id).disabled = false;
	 document.getElementById("LAN2_"+id).disabled = false;
}
function disableIsLanguage(id){
	 document.getElementById("LAN1_"+id).disabled = true;
	 document.getElementById("LAN2_"+id).disabled = true;
	 document.getElementById("LAN2_"+id).checked = true;
}

// semwise added by balaji
function enableNeedSemWiseYes(count) {
	document.getElementById("needToProduce1_mc_"+count).disabled=false;
	document.getElementById("needToProduce2_mc_"+count).disabled=false;
}	

function disableNeedSemWiseNo(count) {
	 document.getElementById("needToProduce1_mc_"+count).disabled = true;
	 document.getElementById("needToProduce2_mc_"+count).disabled = true;
	 document.getElementById("needToProduce2_mc_"+count).checked = true;
}

</script>
</head>

<html:form action="/checkListDocs" method="POST">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" styleId="formName" value="checkListForm" />
<html:hidden property="pageType" styleId="pageType" value="1" />
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
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"> <bean:message key="knowledgepro.admission.checklistDocuments"/></strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td >
               
			   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			          	<tr>
							<td  colspan="3" align="left">
								<div id="err" style="color:red;font-family:arial;font-size:11px;">
		               			</div>
								<div id="errorMessage">
								<FONT color="red"><html:errors /></FONT>
								</div>
							</td>
						</tr>	          	
			          	<tr>
			              <td ><img src="images/01.gif" width="5" height="5" /></td>
			              <td width="914" background="images/02.gif"></td>
			              <td><img src="images/03.gif" width="5" height="5" /></td>
			            </tr>
			            <tr>
			              <td width="5"  background="images/left.gif"></td>
			              <td valign="top">
			              
		           		 <c:choose>
							<c:when test="${checkListOperation == 'edit'}">
					              <table width="100%" cellspacing="1" cellpadding="2">
					                  <tr >
					                    <td width="16%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>
					                    <bean:message key="knowledgepro.admission.programtype" />:</div></td>
					                    <td width="16%" class="row-even" ><label>
											<input type="hidden" name="programTId" id="programTId" value='<bean:write name="checkListForm" property="programTypeId"/>'/>
					                       	<html:select property="programTypeId" styleClass="combo" styleId="programType" onchange="getPrograms(this.value)">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
												<cms:renderProgramTypes></cms:renderProgramTypes>
											</html:select></label></td>
					                    <td width="17%" class="row-odd" >
					                   <div align="right"><span class="Mandatory">*</span>
					                   <bean:message key="knowledgepro.admission.program" />:</div></td>
					                    <td width="17%" class="row-even" ><span class="row-even">
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
					                    <td height="25" class="row-odd" align="right"><div align="right">
					                    <span class="Mandatory">*</span><bean:message key="knowledgepro.admission.course" />:</div></td>
											<td width="17%" class="row-even">
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
					                    <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>
					                    		<bean:message key="knowledgepro.admin.year" />:</div></td>
											<td class="row-even">
											<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="checkListForm" property="year"/>"/>
											<html:select property="year" styleId="year"  styleClass="combo">
												<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
												<cms:renderYear></cms:renderYear>
											</html:select></td>
					                 </tr>
					              </table>
					              
						</c:when>
						<c:otherwise>
						            <table width="100%" cellspacing="1" cellpadding="2">
						                <tr>
						                  <td width="12%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
						                  <td width="14%" class="row-even"><bean:write name="checkListForm" property="programTypeName"/></td>
						                  <td width="10%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.program"/>:</div></td>
						                  <td width="14%" class="row-even"><bean:write name="checkListForm" property="programName"/></td>
						                </tr>
						                <tr>
						                  <td class="row-odd" height="25"><div align="right"><bean:message key="knowledgepro.admission.course"/>:</div></td>
						                  <td class="row-even" height="25"><bean:write name="checkListForm" property="courseName"/></td>
						                  <td class="row-odd" height="25"><div align="right"><bean:message key="knowledgepro.admission.year"/>:</div></td>
						                  <td class="row-even" height="25"><bean:write name="checkListForm" property="year"/></td>
						                </tr>
						            </table>
						</c:otherwise>
		             </c:choose>

                     </td>
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
        <td class="heading"><bean:message key="knowledgepro.admission.checklist.applicableDocs"/>:</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><bean:message key="knowledgepro.admission.checklist.educationalInfo"/>:</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
                    <td width="15%" height="20" class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.document"/></div></td>
                    <td width="10%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admission.needToProduce"/> </td>
                    <td width="10%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admission.needToProduce.semwise.mc"/> </td>
                    <td width="10%" class="row-odd" align="center"><bean:message key="knowledgepro.admission.markscard"/></td>
                    <td width="15%" class="row-odd" align="center"><bean:message key="knowledgepro.admission.consolidatedMarks"/></td>
                    <td width="13%" class="row-odd" align="center"><bean:message key="knowledgepro.admission.semsterwise"/></td>
                    <td width="15%" class="row-odd" align="center">Include Language</td>
                    <td width="15%" class="row-odd" align="center"><bean:message key="knowledgepro.admission.previousexam"/></td>
					<td width="8%" class="row-odd" align="center">Exam Required</td>
                  </tr>
           <c:set var="globalcount" value="0"/>       
            <nested:iterate id="DocList" name="checkListForm" property="doclist" indexId="count">
           <c:if test="${DocList.isEducationalInfo == 'Yes'}">
			<c:set var="globalcount" value="${count + 1}"/> 
			<%
				String dynamicStyle = "";
				if (count % 2 != 0) {
				dynamicStyle = "row-white";
				} else {
				dynamicStyle = "row-even";
				}
			%>	
		<tr>
			<td class="<%=dynamicStyle%>">
			<div align="left">
			    <input type="hidden" id="selected_<c:out value='${count}'/>" name="selected_<c:out value='${count}'/>" value="<nested:write name='DocList' property='tempSelect'/>"/>
				<input type="checkbox" id="selected1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].select" id="select_<c:out value='${count}'/>" onclick="resetDocument('<c:out value="${count}"/>')"/>
				<nested:write property="name"/></div>
				<script type="text/javascript">
					var selectedId = document.getElementById("selected_<c:out value='${count}'/>").value;
					if(selectedId == "on") {
							document.getElementById("selected1_<c:out value='${count}'/>").checked = true;
					}		
				</script>
				</td>
				
			<td class="<%=dynamicStyle%>">
				<input type="hidden" id="needToProduce_<c:out value='${count}'/>" name="MC1_<c:out value='${count}'/>" value="<nested:write name='DocList' property='needToProduce'/>"/>
				<input type="radio" id="needToProduce1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].needToProduce" value="true"  id="NTP1_<c:out value='${count}'/>" onclick="enableNeedSemWiseYes('<c:out value='${count}'/>')"/>Yes 
				<input type="radio" id="needToProduce2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].needToProduce" checked="checked" value="false" id="NTP2_<c:out value='${count}'/>" onclick="disableNeedSemWiseNo('<c:out value='${count}'/>')"/>No
				<script type="text/javascript">
					var needToProduceId = "needToProduce_<c:out value='${count}'/>";
					var needToProduce = document.getElementById(needToProduceId).value;
					if(needToProduce == "true") {
	                        document.getElementById("needToProduce1_<c:out value='${count}'/>").checked = true;
					}	
				</script>
				
			</td>
			<td class="<%=dynamicStyle%>">
				<input type="hidden" id="needToProduce_mc_<c:out value='${count}'/>" name="NMC1_<c:out value='${count}'/>" value="<nested:write name='DocList' property='needToProduceSemwiseMc'/>"/>
				<input type="radio" id="needToProduce1_mc_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].needToProduceSemwiseMc" value="true"   />Yes 
				<input type="radio" id="needToProduce2_mc_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].needToProduceSemwiseMc" checked="checked" value="false"  />No
				<script type="text/javascript">
					var needToProduceMCId = "needToProduce_mc_<c:out value='${count}'/>";
					var needToProduceMC = document.getElementById(needToProduceMCId).value;
					if(needToProduceMC == "true") {
	                        document.getElementById("needToProduce1_mc_<c:out value='${count}'/>").checked = true;
					}	
					var needToProduceId1 = "needToProduce_<c:out value='${count}'/>";
					var needToProduce1 = document.getElementById(needToProduceId1).value;
					if(needToProduce1 == "true") {
						 document.getElementById("needToProduce1_mc_<c:out value='${count}'/>").disabled = false;
	                     document.getElementById("needToProduce2_mc_<c:out value='${count}'/>").disabled = false;
					}else{
						 document.getElementById("needToProduce1_mc_<c:out value='${count}'/>").disabled = true;
	                     document.getElementById("needToProduce2_mc_<c:out value='${count}'/>").disabled = true;	
						}
				</script>
				
			</td>
			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="marksCard_<c:out value='${count}'/>" name="marksCard_<c:out value='${count}'/>" value="<nested:write name='DocList' property='isMarksCard'/>"/>
				<input type="radio" id="Marks1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isMarksCard" value="true" onclick="enableIsMarksCardYes('<c:out value='${count}'/>')" /> Yes
				<input type="radio" id="Marks2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isMarksCard" value="false" checked="checked" onclick="disableIsMarksCardNo('<c:out value='${count}'/>')" /> No
				<script type="text/javascript">
					var marksCard = document.getElementById("marksCard_<c:out value='${count}'/>").value;
					if(marksCard == "true") {
						  document.getElementById("Marks1_<c:out value='${count}'/>").checked = true;
					}	  
				</script>
			</td>	
			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="consolidatedMarks_<c:out value='${count}'/>" name="consolidatedMarks_<c:out value='${count}'/>" value="<nested:write name='DocList' property='isConsolidatedMarks'/>"/>
				<input type="radio" id="CM1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isConsolidatedMarks" value="true" disabled="disabled" onclick="disableIsSemMarks('<c:out value='${count}'/>')"/> Yes
				<input type="radio" id="CM2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isConsolidatedMarks" value="false" checked="checked" disabled="disabled" onclick="enableIsSemMarks('<c:out value='${count}'/>')"/> No
				<script type="text/javascript">
					var consolidatedMarks = document.getElementById("consolidatedMarks_<c:out value='${count}'/>").value;
					var marksCard1 = document.getElementById("marksCard_<c:out value='${count}'/>").value;
					if(consolidatedMarks == "true"){
							document.getElementById("CM1_<c:out value='${count}'/>").checked = true;
					}	
					if(marksCard == "true") {
	                        document.getElementById("CM1_<c:out value='${count}'/>").disabled = false;
	                        document.getElementById("CM2_<c:out value='${count}'/>").disabled = false;
					}	
				</script>
			</td>
			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="semesterWise_<c:out value='${count}'/>" name="semesterWise_<c:out value='${count}'/>" value="<nested:write name='DocList' property='semesterWise'/>"/>
				<input type="radio" id="SEM1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].semesterWise" value="true" disabled="disabled" onclick="enableIsLanguage('<c:out value='${count}'/>')"/> Yes
				<input type="radio" id="SEM2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].semesterWise" value="false" checked="checked" disabled="disabled" onclick="disableIsLanguage('<c:out value='${count}'/>')"/> No
				<script type="text/javascript">
					var consolidatedMarks1 = document.getElementById("consolidatedMarks_<c:out value='${count}'/>").value;
					var semesterWise = document.getElementById("semesterWise_<c:out value='${count}'/>").value;
					if(marksCard == "true") {
                        document.getElementById("SEM1_<c:out value='${count}'/>").disabled = false;
                        document.getElementById("SEM2_<c:out value='${count}'/>").disabled = false;
					}	
					if(consolidatedMarks1 == "true" && marksCard == "true") {
	                        document.getElementById("SEM1_<c:out value='${count}'/>").disabled = true;
	                        document.getElementById("SEM2_<c:out value='${count}'/>").disabled = true;
					}
					if(semesterWise == "true") {
						document.getElementById("SEM1_<c:out value='${count}'/>").checked = true;
					}	
				</script>
			</td>
			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="includeLanguage_<c:out value='${count}'/>" name="isIncludeLanguage_<c:out value='${count}'/>" value="<nested:write name='DocList' property='isIncludeLanguage'/>"/>
				<input type="radio" id="LAN1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isIncludeLanguage" value="true" disabled="disabled"/> Yes
				<input type="radio" id="LAN2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isIncludeLanguage" value="false" checked="checked" disabled="disabled"/> No
				<script type="text/javascript">
					
					var semesterWise1 = document.getElementById("semesterWise_<c:out value='${count}'/>").value;
					var isIncludeLanguage = document.getElementById("includeLanguage_<c:out value='${count}'/>").value;
					if(semesterWise1 == "true") {
						if(isIncludeLanguage == "true") {
							document.getElementById("LAN1_<c:out value='${count}'/>").checked = true;								
						}	
						document.getElementById("LAN1_<c:out value='${count}'/>").disabled = false;
                        document.getElementById("LAN2_<c:out value='${count}'/>").disabled = false;
					} else {
						document.getElementById("LAN1_<c:out value='${count}'/>").disabled = true;
                        document.getElementById("LAN2_<c:out value='${count}'/>").disabled = true;
                        document.getElementById("LAN1_<c:out value='${count}'/>").checked = false;
					}		
				</script>
			</td>
			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="previousExam_<c:out value='${count}'/>" name="previousExam_<c:out value='${count}'/>" value="<nested:write name='DocList' property='previousExam'/>"/>
				<input type="radio" id="PEXAM1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].previousExam" value="true" disabled="disabled"/> Yes
				<input type="radio" id="PEXAM2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].previousExam" value="false" checked="checked" disabled="disabled"/> No
				<script type="text/javascript">
					var previousExam = document.getElementById("previousExam_<c:out value='${count}'/>").value;
					if(previousExam == "true") {
	                        document.getElementById("PEXAM1_<c:out value='${count}'/>").checked = true;
					}	
					if(marksCard == "true") {
                        document.getElementById("PEXAM1_<c:out value='${count}'/>").disabled = false;
                        document.getElementById("PEXAM2_<c:out value='${count}'/>").disabled = false;
			    	}
				</script>
			</td>

			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="examReq_<c:out value='${count}'/>" name="examReq_<c:out value='${count}'/>" value="<nested:write name='DocList' property='isExamRequired'/>"/>
				<input type="radio" id="examReq1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isExamRequired" value="true" disabled="disabled"/> Yes
				<input type="radio" id="examReq2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isExamRequired" value="false" checked="checked" disabled="disabled"/> No
				<script type="text/javascript">
					var ExamReq = document.getElementById("examReq_<c:out value='${count}'/>").value;
					if(ExamReq == "true") {
	                        document.getElementById("examReq1_<c:out value='${count}'/>").checked = true;
					}	
					if(marksCard == "true") {
                        document.getElementById("examReq1_<c:out value='${count}'/>").disabled = false;
                        document.getElementById("examReq2_<c:out value='${count}'/>").disabled = false;
			    	}
				</script>
			</td>
	</tr>
		
		</c:if>
            </nested:iterate>
              </table></td>
              <td width="5" height="30"  background="images/right.gif">
              <input type="hidden" id="count" name="count" value='<c:out value="${globalcount}"/>'/>
              </td>
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
        <td class="heading"><bean:message key="knowledgepro.admission.checklist.otherdocuments"/>:</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
                    <td width="15%" height="20" class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.document"/></div></td>
                    <td width="10%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admission.needToProduce"/> </td>
                    <td width="10%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admission.needToProduce.semwise.mc"/> </td>
                    <td width="10%" class="row-odd" align="center"><bean:message key="knowledgepro.admission.markscard"/></td>
                    <td width="15%" class="row-odd" align="center"><bean:message key="knowledgepro.admission.consolidatedMarks"/></td>
                    <td width="13%" class="row-odd" align="center"><bean:message key="knowledgepro.admission.semsterwise"/></td>
                    <td width="15%" class="row-odd" align="center">Include Language</td>
                    <td width="15%" class="row-odd" align="center"><bean:message key="knowledgepro.admission.previousexam"/></td>
					<td width="8%" class="row-odd" align="center">Exam Required</td>
                  </tr>
           <c:set var="globalcount" value="0"/>    
           <nested:iterate id="DocList" name="checkListForm" property="doclist" indexId="count">
           <c:if test="${DocList.isEducationalInfo == 'No'}">   
			<c:set var="globalcount" value="${count + 1}"/> 
			<%
				String dynamicStyle = "";
				if (count % 2 != 0) {
				dynamicStyle = "row-white";
				} else {
				dynamicStyle = "row-even";
				}
			%>	
		<tr>
			<td class="<%=dynamicStyle%>">
			<div align="left">
			    <input type="hidden" id="selected_<c:out value='${count}'/>" name="selected_<c:out value='${count}'/>" value="<nested:write name='DocList' property='tempSelect'/>"/>
				<input type="checkbox" id="selected1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].select" id="select_<c:out value='${count}'/>" onclick="resetDocument('<c:out value="${count}"/>')"/>
				<nested:write property="name"/></div>
				<script type="text/javascript">
					var selectedId = document.getElementById("selected_<c:out value='${count}'/>").value;
					if(selectedId == "on") {
							document.getElementById("selected1_<c:out value='${count}'/>").checked = true;
					}		
				</script>
				</td>
				
			<td class="<%=dynamicStyle%>">
				<input type="hidden" id="needToProduce_<c:out value='${count}'/>" name="MC1_<c:out value='${count}'/>" value="<nested:write name='DocList' property='needToProduce'/>"/>
				<input type="radio" id="needToProduce1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].needToProduce" value="true"  id="NTP1_<c:out value='${count}'/>" onclick="enableNeedSemWiseYes('<c:out value='${count}'/>')"/>Yes 
				<input type="radio" id="needToProduce2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].needToProduce" checked="checked" value="false" id="NTP2_<c:out value='${count}'/>" onclick="disableNeedSemWiseNo('<c:out value='${count}'/>')"/>No
				<script type="text/javascript">
					var needToProduceId = "needToProduce_<c:out value='${count}'/>";
					var needToProduce = document.getElementById(needToProduceId).value;
					if(needToProduce == "true") {
	                        document.getElementById("needToProduce1_<c:out value='${count}'/>").checked = true;
					}	
				</script>
				
			</td>
			<td class="<%=dynamicStyle%>">
				<input type="hidden" id="needToProduce_mc_<c:out value='${count}'/>" name="NMC1_<c:out value='${count}'/>" value="<nested:write name='DocList' property='needToProduceSemwiseMc'/>"/>
				<input type="radio" id="needToProduce1_mc_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].needToProduceSemwiseMc" value="true"   />Yes 
				<input type="radio" id="needToProduce2_mc_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].needToProduceSemwiseMc" checked="checked" value="false"  />No
				<script type="text/javascript">
					var needToProduceMCId = "needToProduce_mc_<c:out value='${count}'/>";
					var needToProduceMC = document.getElementById(needToProduceMCId).value;
					if(needToProduceMC == "true") {
	                        document.getElementById("needToProduce1_mc_<c:out value='${count}'/>").checked = true;
					}	
					var needToProduceId1 = "needToProduce_<c:out value='${count}'/>";
					var needToProduce1 = document.getElementById(needToProduceId1).value;
					if(needToProduce1 == "true") {
						 document.getElementById("needToProduce1_mc_<c:out value='${count}'/>").disabled = false;
	                     document.getElementById("needToProduce2_mc_<c:out value='${count}'/>").disabled = false;
					}else{
						 document.getElementById("needToProduce1_mc_<c:out value='${count}'/>").disabled = true;
	                     document.getElementById("needToProduce2_mc_<c:out value='${count}'/>").disabled = true;	
						}
				</script>
				
			</td>
			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="marksCard_<c:out value='${count}'/>" name="marksCard_<c:out value='${count}'/>" value="<nested:write name='DocList' property='isMarksCard'/>"/>
				<input type="radio" id="Marks1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isMarksCard" value="true" onclick="enableIsMarksCardYes('<c:out value='${count}'/>')" /> Yes
				<input type="radio" id="Marks2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isMarksCard" value="false" checked="checked" onclick="disableIsMarksCardNo('<c:out value='${count}'/>')" /> No
				<script type="text/javascript">
					var marksCard = document.getElementById("marksCard_<c:out value='${count}'/>").value;
					if(marksCard == "true") {
						  document.getElementById("Marks1_<c:out value='${count}'/>").checked = true;
					}	  
				</script>
			</td>	
			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="consolidatedMarks_<c:out value='${count}'/>" name="consolidatedMarks_<c:out value='${count}'/>" value="<nested:write name='DocList' property='isConsolidatedMarks'/>"/>
				<input type="radio" id="CM1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isConsolidatedMarks" value="true" disabled="disabled" onclick="disableIsSemMarks('<c:out value='${count}'/>')"/> Yes
				<input type="radio" id="CM2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isConsolidatedMarks" value="false" checked="checked" disabled="disabled" onclick="enableIsSemMarks('<c:out value='${count}'/>')"/> No
				<script type="text/javascript">
					var consolidatedMarks = document.getElementById("consolidatedMarks_<c:out value='${count}'/>").value;
					var marksCard1 = document.getElementById("marksCard_<c:out value='${count}'/>").value;
					if(consolidatedMarks == "true"){
							document.getElementById("CM1_<c:out value='${count}'/>").checked = true;
					}	
					if(marksCard == "true") {
	                        document.getElementById("CM1_<c:out value='${count}'/>").disabled = false;
	                        document.getElementById("CM2_<c:out value='${count}'/>").disabled = false;
					}	
				</script>
			</td>
			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="semesterWise_<c:out value='${count}'/>" name="semesterWise_<c:out value='${count}'/>" value="<nested:write name='DocList' property='semesterWise'/>"/>
				<input type="radio" id="SEM1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].semesterWise" value="true" disabled="disabled" onclick="enableIsLanguage('<c:out value='${count}'/>')"/> Yes
				<input type="radio" id="SEM2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].semesterWise" value="false" checked="checked" disabled="disabled" onclick="disableIsLanguage('<c:out value='${count}'/>')"/> No
				<script type="text/javascript">
					var consolidatedMarks1 = document.getElementById("consolidatedMarks_<c:out value='${count}'/>").value;
					var semesterWise = document.getElementById("semesterWise_<c:out value='${count}'/>").value;
					if(marksCard == "true") {
                        document.getElementById("SEM1_<c:out value='${count}'/>").disabled = false;
                        document.getElementById("SEM2_<c:out value='${count}'/>").disabled = false;
					}	
					if(consolidatedMarks1 == "true" && marksCard == "true") {
	                        document.getElementById("SEM1_<c:out value='${count}'/>").disabled = true;
	                        document.getElementById("SEM2_<c:out value='${count}'/>").disabled = true;
					}
					if(semesterWise == "true") {
						document.getElementById("SEM1_<c:out value='${count}'/>").checked = true;
					}	
				</script>
			</td>
			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="includeLanguage_<c:out value='${count}'/>" name="isIncludeLanguage_<c:out value='${count}'/>" value="<nested:write name='DocList' property='isIncludeLanguage'/>"/>
				<input type="radio" id="LAN1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isIncludeLanguage" value="true" disabled="disabled"/> Yes
				<input type="radio" id="LAN2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isIncludeLanguage" value="false" checked="checked" disabled="disabled"/> No
				<script type="text/javascript">
					
					var semesterWise1 = document.getElementById("semesterWise_<c:out value='${count}'/>").value;
					var isIncludeLanguage = document.getElementById("includeLanguage_<c:out value='${count}'/>").value;
					if(semesterWise1 == "true") {
						if(isIncludeLanguage == "true") {
							document.getElementById("LAN1_<c:out value='${count}'/>").checked = true;								
						}	
						document.getElementById("LAN1_<c:out value='${count}'/>").disabled = false;
                        document.getElementById("LAN2_<c:out value='${count}'/>").disabled = false;
					} else {
						document.getElementById("LAN1_<c:out value='${count}'/>").disabled = true;
                        document.getElementById("LAN2_<c:out value='${count}'/>").disabled = true;
                        document.getElementById("LAN1_<c:out value='${count}'/>").checked = false;
					}		
				</script>
			</td>
			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="previousExam_<c:out value='${count}'/>" name="previousExam_<c:out value='${count}'/>" value="<nested:write name='DocList' property='previousExam'/>"/>
				<input type="radio" id="PEXAM1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].previousExam" value="true" disabled="disabled"/> Yes
				<input type="radio" id="PEXAM2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].previousExam" value="false" checked="checked" disabled="disabled"/> No
				<script type="text/javascript">
					var previousExam = document.getElementById("previousExam_<c:out value='${count}'/>").value;
					if(previousExam == "true") {
	                        document.getElementById("PEXAM1_<c:out value='${count}'/>").checked = true;
					}	
					if(marksCard == "true") {
                        document.getElementById("PEXAM1_<c:out value='${count}'/>").disabled = false;
                        document.getElementById("PEXAM2_<c:out value='${count}'/>").disabled = false;
			    	}
				</script>
			</td>

			<td class="<%=dynamicStyle%>">
			    <input type="hidden" id="examReq_<c:out value='${count}'/>" name="examReq_<c:out value='${count}'/>" value="<nested:write name='DocList' property='isExamRequired'/>"/>
				<input type="radio" id="examReq1_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isExamRequired" value="true" disabled="disabled"/> Yes
				<input type="radio" id="examReq2_<c:out value='${count}'/>" name="doclist[<c:out value='${count}'/>].isExamRequired" value="false" checked="checked" disabled="disabled"/> No
				<script type="text/javascript">
					var ExamReq = document.getElementById("examReq_<c:out value='${count}'/>").value;
					if(ExamReq == "true") {
	                        document.getElementById("examReq1_<c:out value='${count}'/>").checked = true;
					}	
					if(marksCard == "true") {
                        document.getElementById("examReq1_<c:out value='${count}'/>").disabled = false;
                        document.getElementById("examReq2_<c:out value='${count}'/>").disabled = false;
			    	}
				</script>
			</td>
	</tr>
		
		</c:if>
            </nested:iterate>
              </table></td>
              <td width="5" height="30"  background="images/right.gif">
              <input type="hidden" id="count" name="count" value='<c:out value="${globalcount}"/>'/>
              </td>
             
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
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="45%" height="35"><div align="right">
              
              <c:choose>
                 <c:when test="${checkListOperation == 'edit'}">
                    <html:button property="" styleClass="formbutton" onclick="update()"><bean:message key="knowledgepro.update"/></html:button>
                 </c:when>
             	 <c:otherwise>
              		<html:button property="" styleId="submitButton" styleClass="formbutton" onclick="add()" ><bean:message key="knowledgepro.admin.add"/></html:button>
               	 </c:otherwise>
             </c:choose> 
            	          
	          </div></td>
			<td width="2%"></td>
			<td width="40%"><html:reset styleClass="formbutton" onclick="redirectCancel()">
			<bean:message key="knowledgepro.cancel"/></html:reset>
			</td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
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
var operation = "<c:out value='${checkListOperation}'/>";
if(operation == 'edit') {
	var programTypeId = document.getElementById("programTId").value;
	if(programTypeId.length != 0){
		document.getElementById("programType").value= programTypeId;
	}
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
	}
}
</script>