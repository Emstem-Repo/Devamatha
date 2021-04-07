<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>


<script type="text/javascript" language="javascript">

function resetValues(){
	document.location.href = "ExamStudentEligibilityCheck.do?method=initStudentEligibilityCheck";
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
				
				//listClasses.push(mapFrom[j].value);
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


function moveinid() {
	var mapFrom = document.getElementById('mapClass');
	var mapTo = document.getElementById('selsubMap');
	var len = mapTo.length;

	for ( var j = 0; j < len; j++) {
		if (mapTo[j].selected) {
			var tmp = mapTo.options[j].text;
			var tmp1 = mapTo.options[j].value;
			mapTo.remove(j);
			len--;
			j--;
			if (j < 0) {
				document.getElementById("moveIn").disabled = true;
				document.getElementById("moveOut").disabled = false;
			}
			if (mapTo.length != 0) {
				document.getElementById("moveOut").disabled = false;
				document.getElementById("moveIn").disabled = false;
			} else
				document.getElementById("moveOut").disabled = false;
			var y = document.createElement('option');
			y.setAttribute("class", "comboBig");
			y.text = tmp;
			y.value = tmp1;
			try {
				mapFrom.add(y, null);
			} catch (ex) {
				mapFrom.add(y);
			}
		}
	}

}// Functions for AJAX  first Method
	//for second method
	function getClass(examName) {
		
	getClassCodeByExamName("classMap", examName, "mapClass", updateClass1);
	}
	function updateClass1(req){
		updateOptionsFromMapMultiselect(req, "mapClass", "");
	}
	function getExamsByExamTypeAndYear() {
		var examType=document.getElementById("examType").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
</script>


</head>
<html:form action="/ExamStudentEligibilityCheck.do" styleId="myform">

	<html:hidden property="formName"
		value="ExamStudentEligibilityCheckForm" />
	<html:hidden property="classValues" styleId="classValues" />	
	<html:hidden property="method" styleId="method" value="getStudentEligibilityCheck" />
	<html:hidden property="pageType" value="1" />
	
	
	
	
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.StudentEligibilityForExams" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.exam.StudentEligibilityForExams" /></strong></td>
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
            <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="ExamStudentEligibilityCheckForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td class="row-odd"></td>
									<td class="row-even"></td>
            </tr>
            <tr>
                     <td  height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span> <bean:message key="knowledgepro.exam.examEligibilitySetUp.ExamType" />:</div></td>
                <td  class="row-even">
                 <html:select property="examType" styleClass="combo"
												styleId="examType" name="ExamStudentEligibilityCheckForm"
												style="width:200px" onchange="getExamsByExamTypeAndYear()">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="ExamStudentEligibilityCheckForm"
													property="listExamType">
													<html:optionsCollection property="listExamType"
														name="ExamStudentEligibilityCheckForm" label="display"
														value="id" />
												</logic:notEmpty>
											</html:select></td>
            
            <td width="22%" class="row-odd"><div align="right"><span class="mandatoryfield">*</span><bean:message key="knowledgepro.exam.revaluationApplication.examName" />:</div></td>
             <td width="34%" class="row-even" colspan="3">

									<html:select property="examName" styleClass="combo"
												styleId="examName" name="ExamStudentEligibilityCheckForm"
												style="width:200px" onchange="getClass(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="ExamStudentEligibilityCheckForm"
													property="examNameMap">
													<html:optionsCollection property="examNameMap"
														name="ExamStudentEligibilityCheckForm" label="value"
														value="key" />
												</logic:notEmpty>
											</html:select>

             </td>
                  </tr>
            <tr>
                  <td width="25%" height="25" valign="top"  class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.exam.StudentEligibilityForExams.ClassCode" /> :</div></td>
                  <td  colspan="3"  height="25"   class="row-even">
                  <table width="440" border="0">
                  <tr><td width="118">
                 <label>
                 
       <nested:select
		property="classIdsFrom" styleClass="body"
		multiple="multiple" size="8" styleId="mapClass"
		style="width:200px">
			
			<c:if test="${classMap != null}">
					<logic:notEmpty name="ExamStudentEligibilityCheckForm"
					property="mapClass">
					<nested:optionsCollection name="ExamStudentEligibilityCheckForm"
					property="mapClass" label="value" value="key"
					styleClass="comboBig" />
					</logic:notEmpty>
				</c:if>
		</nested:select>
       
                
                
                  </label>
                  </td>
                  
                    
                  <td width="52">
                  <table border="0">
                  <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
                  <tr><td>
                  <input type="button" onClick="moveoutid()"
														id="moveOut" value=">>"></td></tr>
                  <tr><td><input type="button" value="<<" 
													id="moveIn" onclick="moveinid()"></td></tr>
													
				  <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
				  </table></td>
                  <td width="256">
                    <label>
                      <nested:select
						property="classIdsTo" styleId="selsubMap" styleClass="body"
						multiple="multiple" size="8" style="width:200px;">
						<c:if
							test="${ExamStudentEligibilityCheckForm.mapSelectedClass!=null && ExamStudentEligibilityCheckForm.mapSelectedClass.size!=0}">
							<nested:optionsCollection
								name="ExamStudentEligibilityCheckForm"
								property="mapSelectedClass" label="value" value="key"
								styleClass="comboBig" />
						</c:if>
					</nested:select> 
                    
                    </label></td></tr></table>                    </td>
                </tr>
              <tr >
			<td  height="25" class="row-odd"><div align="right" > <bean:message key="knowledgepro.exam.StudentEligibilityForExams.displayFor" />:</div></td>
                <td  class="row-even">
                 <html:select
										property="displayFor" styleId="displayFor">
										<html:option value="">Select</html:option>
										<html:option value="E">Eligible</html:option>
										<html:option value="N">Not Eligible</html:option>
										<html:option value="B">Both</html:option>
									</html:select></td>
				<td class="row-odd"></td>
				<td class="row-even"></td>

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
               	<input name="submit" type="submit" class="formbutton" value="Submit" onclick="getClassValues()"/>
		 </div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr><td style="height: 10px" align="center" colspan="3"></td></tr>  
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
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>


