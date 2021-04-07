<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<head>


<script type="text/javascript" language="javascript">
function editElegSetUp(id,classID,examTypeID) {
	document.location.href = "ExamExamEligibilitySetUp.do?method=editExamEligibilitySetUp&id="+id+"&classId="+classID+"&examtypeId="+examTypeID;
	document.getElementById("submit").value="Update";
	
	
}

function deleteElegSetUp(id,className,examType) {
	deleteConfirm =confirm("Are you sure to delete class '"+ className +"' and exam Type '"+examType+"' this entry?");
	if(deleteConfirm)
	{
	document.location.href = "ExamExamEligibilitySetUp.do?method=deleteExamExamEligibilitySetUp&id="+id;
	}
}
function reActivate(id) {
	document.location.href = "ExamExamEligibilitySetUp.do?method=reActivateEligibilitySetUp&id="+id;
}


function resetValues(){
	document.location.href = "ExamStudentEligibilityEntry.do?method=initStudentEligibilityEntry";
}
function moveoutid()
	{
	
		var mapFrom = document.getElementById('mapClass');
		var len = mapFrom.length;
		var mapTo = document.getElementById('selsubMap');
			
		if(mapTo.length == 0) {
			document.getElementById("moveIn").disabled = false;
		}
		for(var j=0; j<len; j++)
		{
			if(mapFrom[j].selected)
			{
				var tmp = mapFrom.options[j].text;
				var tmp1 = mapFrom.options[j].value;
				mapFrom.remove(j);
				len--;
				j--;
				if(j<0){
					document.getElementById("moveOut").disabled = true;
					document.getElementById("moveIn").disabled = false;
				}
				if(mapFrom.length <= 0)
					document.getElementById("moveOut").disabled = true;
				else
					document.getElementById("moveOut").disabled = false;
				var y=document.createElement('option');
				
				y.text=tmp;
				y.value = tmp1;
				y.setAttribute("class","comboBig");
				try
				{
					mapTo.add(y,null);
				}
				catch(ex)
				{
					mapTo.add(y);
				}
			}
		}
		
		
	}

function getClassValues(){
	var listClasses=new Array(); 
	var mapTo1 = document.getElementById('selsubMap');
	var len1 = mapTo1.length;
	for(var k=0; k<len1; k++)
	{
		listClasses.push(mapTo1[k].value);
	}
	document.getElementById("classValues").value=listClasses;
	
}


function moveinid()
{
	
	var mapFrom = document.getElementById('mapClass');
	var mapTo = document.getElementById('selsubMap');
	var len = mapTo.length;
	
	for(var j=0; j<len; j++)
	{
		if(mapTo[j].selected)
		{
			var tmp = mapTo.options[j].text;
			var tmp1 = mapTo.options[j].value;
		
			mapTo.remove(j);
			len--;
			j--;
			if(j<0){
				document.getElementById("moveIn").disabled = true;
				document.getElementById("moveOut").disabled = false;
			}
			if(mapTo.length != 0) {
				document.getElementById("moveOut").disabled = false;
				document.getElementById("moveIn").disabled = false;
			}
			else
				document.getElementById("moveOut").disabled = false;
			var y=document.createElement('option');
			y.setAttribute("class","comboBig");
			y.text=tmp;
			y.value = tmp1;
			try
			{
			mapFrom.add(y,null);
			}
			catch(ex){
			mapFrom.add(y);	
			}
		}
	}

}




function getChkBoxValues() {
	var finalValue = ""
	var courseCount = document.getElementById("addEligibilityCount").value;

	for ( var i = 0; i < courseCount; i++) {

		
			if (document.getElementById("checkbox_" + i.checked == true)) {
				
				finalValue = finalValue + i + ",";
			
			}
		
	}

}
checked = false;
function checkedAll () {
	ids="";
  if (checked == false){checked = true}else{checked = false}
for (var i = 0; i < document.getElementById('myform').elements.length; i++) {
document.getElementById('myform').elements[i].checked = checked;



}
 
}
</script>



<script type="text/javascript" language="javascript">
		
	function getClass(accodemicYear) {
		getClassesByYearForMuliSelect("classMap", accodemicYear, "mapClass", updateClass);
	}
	
	function updateClass(req) {
		updateOptionsFromMapMultiselect(req, "mapClass", "");
	}
	
		
	
</script>


</head>
<html:form action="/ExamStudentEligibilityEntry.do" styleId="myform">

<html:hidden property="formName" value="ExamStudentEligibilityEntryForm" />
<html:hidden property="classValues" styleId="classValues" />	
	
	<c:choose>
		<c:when
			test="${StudentEligibilityEntryOperation != null && StudentEligibilityEntryOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="setStudentEligibilityEntry" />
			<html:hidden property="year" styleId="year"  />
			<html:hidden property="eligibilityCriteria" styleId="eligibilityCriteria"  />
			 <html:hidden property="pageType" value="2" />

		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="getStudentEligibilityEntry" />
	         <html:hidden property="pageType" value="1" />
	
		</c:otherwise>
	</c:choose>
	
	
	
	
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.studentEligibilityEntry" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.exam.studentEligibilityEntry" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news">
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
        
        <div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatoryfields" /></div></td>
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
            <tr>
            <td width="22%" class="row-odd"><div align="right"><span class="mandatoryfield">*</span><bean:message key="knowledgepro.exam.studentEligibilityEntry.academicYear" />:</div></td>
             <td width="34%" class="row-even" colspan="3"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="ExamStudentEligibilityEntryForm" property="year"/>' />
             <html:select
										property="year" styleId="yearId" styleClass="combo"
										onchange="getClass(this.value)">
										
										
										<cms:renderYear></cms:renderYear>
									</html:select></td>
                  </tr>
            <tr>
                  <td width="25%" height="25" valign="top"  class="row-odd" ><div align="right"><span class="Mandatory">*</span>Class  Code :</div></td>
                  <td  colspan="3"  height="25"   class="row-even">
                  <table width="440" border="0">
                  <tr><td width="118">
                 <label>
                 
                  <nested:select
												property="classIdsFrom" styleClass="body"
												multiple="multiple" size="8" styleId="mapClass"
												style="width:200px">
												
												<c:if
													test="${ExamStudentEligibilityEntryForm.mapClass!=null && ExamStudentEligibilityEntryForm.mapClass.size!=0}">
													<nested:optionsCollection name="ExamStudentEligibilityEntryForm"
														property="mapClass" label="value" value="key"
														styleClass="comboBig" />
												</c:if>
												
												
													<c:set var="mapClass"
														value="${baseActionForm.collectionMap['mapClass']}" />
													<c:if test="${mapClass != null}">
														<html:optionsCollection name="mapClass" label="value"
															value="key" />
													</c:if>
												
												
												
												
											</nested:select>
                 
                
                
                  </label>
                  </td>
                  
                    
                  <td width="52">


 					 <c:choose>
								<c:when
									test="${StudentEligibilityEntryOperation != null && StudentEligibilityEntryOperation == 'edit'}">
				<table border="0">
                  <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
                  <tr><td>
                  <input type="button" 
														id="moveOut" value=">>" disabled="disabled"></td></tr>
                  <tr><td><input type="button" value="<<" 
													id="moveIn" disabled="disabled"></td></tr>
				<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
				</table>

									
								</c:when>
								<c:otherwise>
									<table border="0">
                  				<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
                  <tr><td>
                  <input type="button" onClick="moveoutid()"
														id="moveOut" value=">>"></td></tr>
                  <tr><td><input type="button" value="<<" 
													id="moveIn" onclick="moveinid()"></td></tr>
				  <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
				</table>

								</c:otherwise>
							</c:choose>

                  </td>
                 
                
                  <td width="256">
                    <label>
                      <nested:select
												property="classIdsTo" styleId="selsubMap" styleClass="body"
												multiple="multiple" size="8" style="width:200px;">
												<c:if
													test="${ExamStudentEligibilityEntryForm.mapSelectedClass!=null && ExamStudentEligibilityEntryForm.mapSelectedClass.size!=0}">
													<nested:optionsCollection
														name="ExamStudentEligibilityEntryForm"
														property="mapSelectedClass" label="value" value="key"
														styleClass="comboBig" />
												</c:if>
											</nested:select> 
                    
                    </label></td></tr></table>                    </td>
                </tr>
              <tr >
                <td width="45%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span> <bean:message key="knowledgepro.exam.studentEligibilityEntry.eligibilityCriteriar" />:</div></td>
                <td width="55%" class="row-even">
                 <html:select property="eligibilityCriteria" styleClass="combo"
												styleId="eligibilityCriteria" name="ExamStudentEligibilityEntryForm"
												style="width:200px">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="ExamStudentEligibilityEntryForm"
													property="listEligibilityCriteria">
													<html:optionsCollection property="listEligibilityCriteria"
														name="ExamStudentEligibilityEntryForm" label="display"
														value="id" />
												</logic:notEmpty>
											</html:select>
                
              
                
                
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
     <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
     
      <tr>
        <td height="30" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
        
        
        <c:choose>
								<c:when
									test="${StudentEligibilityEntryOperation != null && StudentEligibilityEntryOperation == 'edit'}">
<input name="submit" type="submit" class="formbutton"
										value="Submit" disabled="disabled"/>

									
								</c:when>
								<c:otherwise>
									<input name="submit" type="submit" class="formbutton"
										value="Submit" onclick="getClassValues()"/>

								</c:otherwise>
							</c:choose>
        
        
         
        </div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>				
      <tr><td style="height: 10px" align="center" colspan="1"></td></tr>

                <logic:equal name="ExamStudentEligibilityEntryForm" property="studentSize" value="1">
					


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
              <tr>
                <td class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno" /> </div></td>
                <td class="row-odd" ><bean:message key="knowledgepro.exam.studentEligibilityEntry.rollNo" /></td>
                <td class="row-odd" ><bean:message key="knowledgepro.exam.studentEligibilityEntry.registerNo" /></td>
                <td height="25" class="row-odd" ><bean:message key="knowledgepro.exam.studentEligibilityEntry.studentname" /></td>
                <td class="row-odd"><p><bean:message key="knowledgepro.exam.studentEligibilityEntry.select" /> 
                 <input
										type='checkbox' name='selectcheckall' onClick="checkedAll()" />
                </p>  </tr>
             
             
                  <logic:iterate name="ExamStudentEligibilityEntryForm"
									property="listStudent" id="listStudent" indexId="count"
									type="com.kp.cms.to.exam.ExamStudentEligibilityEntryTO">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td class="bodytext"><div align="center"><c:out value="${count + 1}" /></div></td>
									<td  align="center" class="bodytext"><bean:write name="listStudent" property="rollNo" /></td>
									<td  align="center" class="bodytext"><bean:write name="listStudent" property="regNO" /></td>
									<td  align="center" class="bodytext"><bean:write name="listStudent" property="studentName" /></td>
									<td  align="center" class="bodytext">
                      <logic:equal name="listStudent" property="eligibilityCheck" value="on">
					<input type="checkbox" name='studentId' checked value='<bean:write name="listStudent" property="studentId" />-<bean:write name="listStudent" property="classId" />' >
                      </logic:equal>
								 <logic:equal name="listStudent" property="eligibilityCheck" value="off">
						<input type="checkbox" name='studentId' value='<bean:write name="listStudent" property="studentId" />-<bean:write name="listStudent" property="classId" />' >
                      </logic:equal>	
									
									</td>
									
									
									
									
									</tr>
								</logic:iterate>  
           
             
             
             
             
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>


        <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr><td style="height: 10px" align="center" colspan="1"></td></tr>            
            <tr>
              <td width="45%" height="35"><div align="right">
                  <input name="button" type="submit" class="formbutton" value="Submit" onclick="getClassValues()" />
              </div></td>
              <td width="2%"></td>
              <td width="53%"><input type="Reset" class="formbutton" value="Reset" onClick="resetValues();" /></td>
            </tr>				
            <tr><td style="height: 10px" align="center" colspan="1"></td></tr>            
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
					
				</logic:equal>
  
  
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("yearId").value = yearId;
	}
	</script>

</html:form>



