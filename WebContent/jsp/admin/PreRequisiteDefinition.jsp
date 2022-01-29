<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);		
	resetOption("course");
}
function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");	
	document.getElementById("programTypeName").value =document.getElementById("programType").options[document.getElementById("programType").selectedIndex].text
}

function getCourses(programId) {
	getCoursesByProgram("courseMap",programId,"course",updateCourses);	
}
function updateCourses(req) {
	updateOptionsFromMap(req,"course","- Select -");
	document.getElementById("programName").value =	document.getElementById("program").options[document.getElementById("program").selectedIndex].text
}
function deleteRow(id)
{
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm) {
		document.location.href = "PreRequisiteDefinition.do?method=deletePreReqDef&courseId="
				+ id;
	}
}

function readonlytext()
{
	if(document.getElementById("prereqid2").options[document.getElementById("prereqid2").selectedIndex].value <= 0){
		document.getElementById("percentage2").readOnly =true;
		document.getElementById("totalMark2").readOnly =true;
		document.getElementById("prereqid2").disabled = true;
//		document.preRequisiteDefinition.prereqid2.disabled = true;
	}
	
}
function enablePerc(){
	if(document.getElementById("percentage1").value <= 0){
		document.getElementById("percentage2").value = "";
		document.getElementById("prereqid2").value = "";
		document.getElementById("percentage2").readOnly =true;
		document.getElementById("totalMark2").readOnly =true;
//		document.preRequisiteDefinition.prereqid2.disabled = true;
		document.getElementById("prereqid2").disabled = true;
	}else{
		if(document.getElementById("prereqid2").options[document.getElementById("prereqid2").selectedIndex].value > 0){
			document.getElementById("percentage2").readOnly =false;
			document.getElementById("totalMark2").readOnly =false;
		}
		document.getElementById("prereqid2").disabled = false;		
//	document.preRequisiteDefinition.prereqid2.disabled = false;
	}
}

function enablePerc2(){
	if(document.getElementById("prereqid2").options[document.getElementById("prereqid2").selectedIndex].value > 0){
		document.getElementById("percentage2").readOnly =false;
		document.getElementById("totalMark2").readOnly =false;
		
	}
	else{
		document.getElementById("percentage2").readOnly =true;
		document.getElementById("totalMark2").readOnly =true;
	}
	
}

function clearField(field) {
	if (field.value == "0.0")
		field.value = "";
}
function checkForEmpty(field) {
	if (isNaN(field.value)) {
		field.value = "";
	}
}
	
</script>
<html:form action="/PreRequisiteDefinition" styleId="preRequisiteDefinition">
<html:hidden property="formName" value="preRequisiteDefinitionForm" />
<html:hidden property="pageType" value="1" />
<c:choose>
<c:when test="${Update != null}">
<html:hidden property="method" styleId="method" value="updatePreReqDef"/>
</c:when>
<c:otherwise>
<html:hidden property="method" styleId="method" value="addPreReqDef"/>
</c:otherwise>
</c:choose>
<html:hidden property="programTypeName" styleId="programTypeName" value=""/>
<html:hidden property="programName" styleId="programName" value=""/>
<html:hidden property="courseName" styleId="courseName" value=""/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.pre.req.def"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.admin.pre.req.def"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news">
		<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
		<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
                <tr class="row-white">
                  <td width="161" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div></td>
                  <td width="164" class="row-even">
                  <html:select property="programTypeId"
					 styleId="programType" styleClass="combo"
					onchange="getPrograms(this.value)">
					<html:option value="">
						<bean:message key="knowledgepro.admin.select" />
					</html:option>
					<html:optionsCollection name="programTypeList"
						label="programTypeName" value="programTypeId" />
					</html:select> </td>
                  <td width="140" class="row-odd"><div align="right" class="row-odd"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program" />:</div></td>
                  <td width="167" class="row-even">
				<html:select
				property="programId"  styleId="program" styleClass="combo"
				onchange="getCourses(this.value) ">
				<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
				<c:if
					test="${preRequisiteDefinitionForm.programTypeId != null && preRequisiteDefinitionForm.programTypeId != ''}">
					<c:set var="programMap"
						value="${baseActionForm.collectionMap['programMap']}" />
					<c:if test="${programMap != null}">
						<html:optionsCollection name="programMap" label="value"
							value="key" />
					</c:if>
				</c:if>
				</html:select></td>
                  <td width="127" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div></td>
                  <td width="160" class="row-even">
					<html:select property="courseId"
					styleId="course" styleClass="combo">
					<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>

					<c:if
						test="${preRequisiteDefinitionForm.programId != null && preRequisiteDefinitionForm.programId != ''}">
						<c:set var="courseMap"
							value="${baseActionForm.collectionMap['courseMap']}" />
						<c:if test="${courseMap != null}">
							<html:optionsCollection name="courseMap" label="value"
								value="key" />
						</c:if>
					</c:if>
				 </html:select></td>
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
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading"><div align="center"><bean:message key="knowledgepro.admin.or"/></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td width="477" valign="top" class="news"><table width="98%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" height="53" border="0" cellpadding="0" cellspacing="1">
                <tr class="row-white">
                  <td width="191" height="25" class="row-odd"><div align="center"><span class="Mandatory">*</span>&nbsp;<span class="row-odd"><bean:message key="knowledgepro.admin.pre.requisite"/></span></div></td>
                  <td width="210" class="row-odd"><div align="center"><span class="Mandatory">*</span>&nbsp; <bean:message key="knowledgepro.admin.pre.requisite.mark"/></div></td>
				  <td width="150" class="row-odd"><div align="center"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.prereqdef.total.mark"/></div></td>
                  </tr>
                <tr class="row-white">
                  <td height="25" class="row-even"><div align="center"><span class="row-even">
					
                    <html:select property="prereqid1"
					styleClass="combo" styleId="prereqid1">
                           <html:option value="">
                             <bean:message key="knowledgepro.admin.select" />
                           </html:option>
                           <html:optionsCollection name="prereqexam" label="name"
						value="id" />
                         </html:select>
                  </span></div></td>
                  <td class="row-even"><div align="center">
                    <html:text property="percentage1" styleId="percentage1" maxlength="7" size="10"
				     onkeypress="return isDecimalNumberKey(this.value,event)"
					 onkeyup="onlyTwoFractions(this,event)"
					  onblur="checkForEmpty(this)"  onchange="enablePerc()" />
                  </div></td>
					<td class="row-even"><div align="center">
                    <html:text property="totalMark1" styleId="totalMark1" maxlength="7" size = "10"
				     onkeypress="return isDecimalNumberKey(this.value,event)"
					 onkeyup="onlyTwoFractions(this,event)"
					 onblur="checkForEmpty(this)"  onchange="enablePerc()"/>
                  </div></td>
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
        <td width="477" valign="top" class="news"><table width="98%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" height="53" border="0" cellpadding="0" cellspacing="1">
                <tr class="row-white">
                  <td width="191" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admin.pre.requisite"/></div></td>
                  <td width="150" class="row-odd"><div align="center"><bean:message key="knowledgepro.admin.pre.requisite.mark"/></div></td>
				  <td width="150" class="row-odd"><div align="center"><bean:message key="knowledgepro.admin.prereqdef.total.mark"/></div></td>
                </tr>
                <tr class="row-white">
                  <td height="25" class="row-even"><div align="center">
                      <html:select property="prereqid2"
					styleClass="combo" styleId="prereqid2" onchange="enablePerc2()">
                           <html:option value="">
                             <bean:message key="knowledgepro.admin.select" />
                           </html:option>
                           <html:optionsCollection name="prereqexam" label="name"
						value="id" />
                         </html:select>
                  </div></td>
                  <td class="row-even"><div align="center">
					<html:text property="percentage2" styleId="percentage2" maxlength="7" size = "10"
				     onkeypress="return isDecimalNumberKey(this.value,event)"
					 onkeyup="onlyTwoFractions(this,event)"
					 onblur="checkForEmpty(this)" />
                  </div></td>

				<td class="row-even"><div align="center">
                    <html:text property="totalMark2" styleId="totalMark2" maxlength="7" size = "10"
				     onkeypress="return isDecimalNumberKey(this.value,event)"
					 onkeyup="onlyTwoFractions(this,event)"
					 onblur="checkForEmpty(this)" />
                  </div></td>
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
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="48%" height="35"><div align="right">
				<html:submit property="" styleClass="formbutton" value="Submit" />
               </div></td>
            <td width="3%"></td>
            <td width="49%"><html:cancel  styleClass="formbutton" value="Reset" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.programtype"/></td>
                    <td class="row-odd"  align="center"><bean:message key="knowledgepro.admin.program"/></td>
                    <td class="row-odd" align="center"><bean:message key="knowledgepro.admin.course"/></td>
                    <td class="row-odd" align="center"><bean:message key="knowledgepro.admin.pre.requisite"/></td>
                    <td class="row-odd" align="center"><bean:message key="knowledgepro.admin.pre.requisite.mark"/></td>
                    <td class="row-odd" align="center"><bean:message key="knowledgepro.admin.prereqdef.total.mark"/></td>
                    <td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
				<logic:iterate name="preRequisiteDefinitionForm" property="prerequsitedeflist"
								id="pList" indexId="count">
				<c:choose>
					<c:when test="${temp == 0}">
                  <tr >
                    <td width="6%" height="25" class="row-even"><div align="center"><c:out value="${count+1}" /></div></td>
                    <td width="17%" height="25" class="row-even" align="center" ><bean:write
					name="pList" property="programTypeName" /></td>
                    <td width="18%" class="row-even"  align="center"><bean:write
					name="pList" property="programName" /></td>
                    <td width="15%" class="row-even" align="center" ><bean:write
					name="pList" property="courseName" /></td>
                    <td width="15%" class="row-even" align="center"><bean:write
					name="pList" property="prereqexamName1" /></td>

                    <td width="16%" class="row-even" align="center"><bean:write
					name="pList" property="percentage1" /></td>
                    <td width="16%" class="row-even" align="center"><bean:write
					name="pList" property="totalMark1" /></td>
                    <td width="6%" height="25" class="row-even" align="center" ><div align="center">
					<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteRow('<bean:write name="pList" property="courseid" />')"></div></td>
                  </tr>
				<c:set var="temp" value="1" />
				</c:when>
				<c:otherwise>
                  <tr >
                    <td height="25" class="row-white"><div align="center"><c:out value="${count+1}" /></div></td>
                    <td height="25" class="row-white"  align="center"><bean:write
					name="pList" property="programTypeName" /></td>
                    <td class="row-white" align="center"><bean:write
					name="pList" property="programName" /></td>
                    <td class="row-white" align="center" ><bean:write
					name="pList" property="courseName" /></td>
                    <td class="row-white" align="center"><bean:write
					name="pList" property="prereqexamName1" /></td>
                    <td class="row-white" align="center"><bean:write
					name="pList" property="percentage1" /></td>
                    <td width="16%" class="row-white" align="center"><bean:write
					name="pList" property="totalMark1" /></td>
                    <td height="25" class="row-white" align="center"><div align="center"><img src="images/delete_icon.gif" 
					width="16" height="16" style="cursor:pointer" onclick="deleteRow('<bean:write name="pList" property="courseid" />')"></div></td>
                  </tr>
				<c:set var="temp" value="0" />
				</c:otherwise>
			</c:choose>
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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news">&nbsp;</td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td colspan="2" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
readonlytext();
</script>