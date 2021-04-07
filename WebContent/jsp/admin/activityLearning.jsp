<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript">
		function afterSubmitMethod(id){
			document.getElementById("method").value="afterSubmitMethod";
		}
		function resetErrorMsgs() {  
			resetErrMsgs();
			document.getElementById("submitbutton").value="Submit";
			document.getElementById("year").value="";
			document.getElementById("minmark").value=null;
			document.getElementById("maxmark").value=null;
			document.getElementById("extraCreditActivityType").value="";
			document.getElementById("method").value="afterSubmitMethod";
			document.getElementById("creditInfo").value=null;
		}		
	 	function editDetails(activityLearningId){
		 	document.getElementById("activityLearningId").value=activityLearningId;
			document.location.href = "ActivityLearning.do?method=editDetails&activityLearningId="+activityLearningId;
		}
		function updateDetails(){
			document.location.href = "ActivityLearning.do?method=updateDetails&activityLearningId="+activityLearningId;
		}
	 	function deleteDetails(activityLearningId){
			deleteConfirm = confirm("Are you sure you want to delete this entry?");
			if(deleteConfirm){
			document.getElementById("activityLearningId").value=activityLearningId;
			   document.location.href = "ActivityLearning.do?method=deleteDetails&activityLearningId="+activityLearningId;
			}
		}
	 	function reActivate(){
	 		document.getElementById("activityLearningId").value=activityLearningId;
	 		alert(document.getElementById("activityLearningId").value);
	 	   document.location.href = "ActivityLearning.do?method=reActivateActivity&activityLearningId="+activityLearningId;
	    }	
</script>
<html:form action="/ActivityLearning" method="POST">
<c:choose>
	<c:when test="${learningOperation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateDetails" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="afterSubmitMethod" />
	</c:otherwise>
</c:choose>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="activityLearningForm"/>
<html:hidden property="activityLearningId" styleId="activityLearningId" />
<html:hidden property="method" value="afterSubmitMethod" styleId="method"/>
<table width="98%" border="0">
  <tr>
     <td class="heading"><html:link href="AdminHome.do" styleClass="Bredcrumbs"><bean:message key="knowledgepro.admin"/></html:link> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.activityentry"/> &gt;&gt;</span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admin.activityLearning"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
           	<td colspan="6" align="left"><br>
				<div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
        		<div id="errorMessage"><FONT color="red"><html:errors/></FONT>
               	<FONT color="green"><html:messages id="msg" property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out><br>
				</html:messages></FONT></div>
          	</td>
        </tr>
         <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              
              	<tr>
            	     <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.appliedyear"/>:</div></td>
									<td width="16%" class="row-even" align="left">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="activityLearningForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" >
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
					 <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.ActivityName"/>:</div></td>
	                    <td width="44%" height="25" class="row-even" ><span class="star">
	                     <html:select property="extraCreditActivityType" styleId="extraCreditActivityType" styleClass="combo">
	                     <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
	                     <html:optionsCollection property="list" label="activityName" value="activityTypeId"/>
	                     </html:select> 
				</label></td>
				</tr>
                 <tr>
					<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Min Marks:</div></td>
	              	<td align="left" height="25" class="row-even">
	              		<html:text property="minmark" styleId="minmark" onkeypress="return isNumberKey(event)"></html:text>
	              	</td>
	              	<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Max Marks:</div></td>
	              	<td align="left" height="25" class="row-even">
	              		<html:text property="maxmark" styleId="maxmark" onkeypress="return isNumberKey(event)"></html:text>
	              	</td>
	              	</tr>
	             <tr >
						<td width="19%" height="25" class="row-odd" ><div align="right">Credits<span class="star"></span>:</div></td>
			             <td class="row-even"><html:textarea property="creditInfo" styleId="creditInfo" cols="30" rows="5"/></td>
			             <c:choose>
	                     <c:when test="${learningOperation == 'edit'}">
		                    <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Courses:</div></td>
			              	<td align="left" height="25" class="row-even">
			              		<html:select property="courseIds"  styleId="courseId" styleClass="body" multiple="multiple" size="10" style="width:350px" disabled="true">
								  <html:optionsCollection name="activityLearningForm" property="courseMap" label="value" value="key" />
							  	</html:select>
			              	</td>
		              	</c:when>
		              	<c:otherwise>
		              	 <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Courses:</div></td>
		              	<td align="left" height="25" class="row-even">
		              		<html:select property="courseIds"  styleId="courseId" styleClass="body" multiple="multiple" size="10" style="width:350px">
							  <html:optionsCollection name="activityLearningForm" property="courseMap" label="value" value="key" />
						  </html:select>
		              	</td>
		              	</c:otherwise>
		             </c:choose>		
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
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
			<td>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
	            <td width="45%" height="35"><div align="right">
	            <c:choose>
	            <c:when test="${learningOperation == 'edit'}">
	                     <html:submit property="" styleClass="formbutton" value="Update" onclick="updateDetails()" styleId="submitbutton"></html:submit>
	            </c:when>
	            <c:otherwise>
	            		 <html:submit property="" styleClass="formbutton" value="Submit" onclick="afterSubmitMethod()" styleId="submitbutton"></html:submit>
	            </c:otherwise>
	            </c:choose>
				</div>
				</td>
				<td width="2%"></td>
					<td width="53%">
					<html:button property="" styleClass="formbutton" value="Reset" onclick="resetErrorMsgs()"></html:button>
				</td>
			</tr>
				</table>
			</td>
	          </tr> 
	          
	       <logic:notEmpty name="activityLearningForm" property="activityLearningToList">
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
                  <tr >
                    <td width="5"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                     <td width="15" height="30%" class="row-odd" align="center" >Applied Year</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Activity Name</td>
                    <td width="15" height="30%" class="row-odd" align="center" >Course Name</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Minimum Mark</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Maximum Mark</td>
                    <td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="to" name="activityLearningForm" property="activityLearningToList" indexId="count">
                		<c:choose>
							<c:when test="${count%2 == 0}">
									<tr class="row-even">
							</c:when>
							<c:otherwise>
									<tr class="row-white">
							</c:otherwise>
						</c:choose>
                		<td  height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="appliedYear" /></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="activityName"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="courseNames"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="minMark"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="to" property="maxMark"/></td>
                   		<td  height="25" class="row-even" ><div align="center">
                   			<img src="images/edit_icon.gif" width="16" height="16" style="cursor:pointer" onclick="editDetails('<bean:write name="to" property="activityId"/>')"></div></td>
                   		<td  height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="to" property="activityId"/>')"></div></td>
                   
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
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var sessionId = document.getElementById("tempSession").value;
	if (sessionId != null && sessionId.length != 0) {
		document.getElementById("sessionId").value = sessionId;
	}
</script>