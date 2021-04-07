<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	
	function resetValue() {
		document.getElementById("ratingFactorId").value = "";
		document.getElementById("maxScoreId").value = "";
		document.getElementById("displayOrder").value = "";
		var id=document.getElementById("teachingId").selected = "";
		id.checked = false;
		if (document.getElementById("method").value == "updateInterviewRatingFactor") {
			document.getElementById("ratingFactorId").value = document.getElementById("orgRatingFactor").value;
			document.getElementById("maxScoreId").value = document.getElementById("orgMaxScore").value;
			document.getElementById("displayOrder").value = document.getElementById("orgDisplayOrder").value;
			document.getElementById("teaching").selected = document.getElementById("orgTeaching").selected;
		}
		resetErrMsgs();
	}
	
	 function editInterviewRatingFactor(id){
		document.location.href = "interviewRatingFactor.do?method=editInterviewRatingFactor&id=" + id;
		}
     function reActivate() {
        
		//var id = document.getElementById("reactivateid").value;
		document.location.href = "interviewRatingFactor.do?method=activateInterviewRatingFactor";
	}		
	function deleteInterviewRatingFactor(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "interviewRatingFactor.do?method=deleteInterviewRatingFactor&id="
					+ id ;
		}
	}
	
	
</script>
<html:form action="/interviewRatingFactor">
	<c:choose>
		<c:when test="${InterviewRatingFactorOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value = "updateInterviewRatingFactor"/>
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addInterviewRatingFactor" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="interviewRatingFactorForm" />
	<html:hidden property="reactivateid" value="reactivateid" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="orgRatingFactor" styleId="orgRatingFactor"/>
	<html:hidden property="orgMaxScore" styleId="orgMaxScore"/>
	<html:hidden property="orgDisplayOrder" styleId="orgDisplayOrder"/>
	<html:hidden property="orgTeaching" styleId="orgTeaching"/>
	<html:hidden property="id" styleId="id"/>
	  
	<table width="100%" border="0">
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory"/> &gt;&gt; Interview Rating Factor &gt;&gt;</span></span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> Interview Rating Factor</strong></td>
	
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td  class="news">
			<div align="right">
				<FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
				</html:messages> </FONT></div></td>
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
	                <td width="24%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span> Rating Factor:</div></td>
	                <td width="26%" class="row-even">
	                <%--<html:textarea property="ratingFactor" styleId="ratingFactorId" name="interviewRatingFactorForm" cols="10" rows="5"></html:textarea>--%>
	                <html:text property="ratingFactor" styleId="ratingFactorId" name="interviewRatingFactorForm" size="50" maxlength="50"></html:text>
					</td>
	                <td width="17%" class="row-odd"><div align="right"><span class="Mandatory">*</span>Maximum Score:</div></td>
	                <td width="33%" class="row-even"><span class="star">
	                  <html:text property="maxScore" styleId="maxScoreId" maxlength="10" size="10" onkeypress="return isNumberKey(event)"/>
	                </span></td>
	              </tr>
	              <tr>
	                <td width="%" class="row-odd"><div align="right"><span class="Mandatory">*</span>Display Order:</div></td>
	                <td width="%" class="row-even"><span class="star">
	                  <html:text property="displayOrder" styleId="displayOrder" maxlength="10" size="10" onkeypress="return isNumberKey(event)"/>
	                </span></td>
	                <td width="17%" height="25" class="row-odd"><div align="right" ><span class="Mandatory"></span> Teaching:</div></td>
	                <td width="33%" class="row-even">
					<html:radio property="teaching" name="interviewRatingFactorForm" value="true" styleId="teachingId">Yes</html:radio>
					<html:radio property="teaching" name="interviewRatingFactorForm" value="false" styleId="teachingId" >No</html:radio>
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
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="45%" height="35"><div align="right">
	              <c:choose>
					<c:when
						test="${InterviewRatingFactorOperation != null && InterviewRatingFactorOperation == 'edit'}">
						<html:submit property="" styleClass="formbutton" value="Update"
							styleId="submitbutton">
						</html:submit>
					</c:when>
					<c:otherwise>
						<html:submit property="" styleClass="formbutton" value="Submit"
							styleId="submitbutton">
						</html:submit>
					</c:otherwise>
				</c:choose>
	            </div></td>
	            <td width="2%"></td>
	
	            <td width="53%"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetValue()"></html:button></td>
	          </tr>
	        </table> </td>
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
	
	                <td class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
	                <td class="row-odd" align="center">Rating Factor</td>
	                <td class="row-odd" align="center">Max.Score</td>
	                <td class="row-odd" align="center">Display Order</td>
	                 <td class="row-odd" align="center">Teaching</td>
					<td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
	                <td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
	              </tr>
				<logic:notEmpty name="InterviewRatingFactorList">
					<logic:iterate id="InterviewRatingFactorList" name ="InterviewRatingFactorList" indexId="count">
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>				
			                <td width="5%" height="25"><div align="center"><c:out value="${count + 1}"/></div></td>
			                <td width="25%" height="25" align="center"><bean:write name = "InterviewRatingFactorList" property="ratingFactor"/></td>
			                <td width="25%" align="center"><bean:write name = "InterviewRatingFactorList" property="maxScore"/></td>
			                <td width="25%" align="center"><bean:write name = "InterviewRatingFactorList" property="displayOrder"/></td>
			                <td width="25%" align="center"><bean:write name = "InterviewRatingFactorList" property="teaching"/></td>
 		                    <td><div align="center"><img src="images/edit_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="editInterviewRatingFactor('<bean:write name="InterviewRatingFactorList" property="id"/>')"></div></td>
			                <td width="25%" height="25"><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
							onclick="deleteInterviewRatingFactor('<bean:write name="InterviewRatingFactorList" property="id"/>')"></div></td>
			              </tr>
					</logic:iterate>
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
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">&nbsp;</td>
	
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