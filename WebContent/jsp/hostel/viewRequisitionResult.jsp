	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
	<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
	<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
		<script type="text/javascript">

		function getDetails(hlAppId) {

			 document.location.href = "viewRequisitions.do?method=getrequisitionDetails&hlAppId="+hlAppId;
			//var url  = "viewRequisitions.do?method=getrequisitionDetails&applicationNumber="+applicationNumber;
	    	//myRef = window.open(url,"ViewApplicantDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	   }
		function cancelAction()
		{
			 document.location.href = "viewRequisitions.do?method=initViewRequisitions";
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
	
		    if(checkBoxselectedCount == 0) {
		        document.getElementById("err").innerHTML = "Please select at least one record.";
		        document.getElementById("errorMessage").innerHTML = "";
		    	return false;
		    }    
		    else { 
		        return true;
		    }    
		            
		}
	
		function selectAll(obj) {
			value = obj.checked;
			var inputs = document.getElementsByTagName("input");
		    var inputObj;
		    var checkBoxselectedCount = 0;
		    for(var count1 = 0;count1<inputs.length;count1++) {
			    inputObj = inputs[count1];
			    var type = inputObj.getAttribute("type");
			   	if (type == 'checkbox') {
			   		inputObj.checked = value;
				}
		    }
		}
		
	</script>
	<html:form action="/viewRequisitions">	
			<html:hidden property="method" styleId="method" value="approveRequisitions" />
			<html:hidden property="formName" value="viewRequisitionsForm"/>
		 <html:hidden property="pageType" value="2" /> 
		<table width="100%" border="0">
			
			
			<tr>
				<td><span class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/>
				<span class="Bredcrumbs">&gt;&gt;
				<bean:message key="knowledgepro.hostel.viewRequisitions"/>
				 &gt;&gt;</span></span></td>
			</tr>
			<tr>
				<td valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9"
							height="29"></td>
						<td background="images/Tcenter.gif" class="body"><strong
							class="boxheader"> <bean:message key="knowledgepro.hostel.viewRequisitions"/> </strong></td>
	
						<td width="10"><img src="images/Tright_1_01.gif" width="9"
							height="29"></td>
					</tr>
					<tr>
						<td height="41" valign="top" background="images/Tright_03_03.gif"></td>
	
						<td valign="top" class="news">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
							<td colspan="6" align="left">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>			
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top"> <div style="overflow: auto; width: 800px; ">
								<table width="100%" cellspacing="1" cellpadding="2">
								
								 <tr>
								
	                   <td height="35" colspan="8" >
	                    <logic:notEmpty name="viewRequisitionsForm" property="requisitionsList">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">	                  
	                     <tr>	            
	                       <td valign="top">	                       
		                   <table width="100%" cellspacing="1" cellpadding="2">
		                       <tr >
		                           <td width="12%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.hostel.roomtype"/></div></td>
		                           <td width="12%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.hostel.appregstaffno"/></div></td>
			                       <td width="20%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.interview.CandidateName"/></div></td>
			                       <td width="15%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.admission.status"/></div></td>
			                       <td width="15%" class="row-odd"><div align="center"><bean:message key="knowledgepro.hostel.viewapplication"/></div></td>
			                       <td width="15%"  class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.approve"/></div></td>
			                      
		                       </tr>
		                       <c:set var="temp" value="0"/>
		                       <nested:iterate id="requisitionsid" name="viewRequisitionsForm" property="requisitionsList" type="com.kp.cms.to.hostel.VRequisitionsTO" indexId="count">
			                       <c:choose>
		                           	 <c:when test="${temp == 0}">
		                           		<tr><nested:hidden property="id"></nested:hidden>
			                               <td class="row-even" ><div align="center"><nested:write  property="roomType"/></div></td>
					                       <td class="row-even" ><div align="center"><nested:write  property="applno"/></div></td>
					                       <td class="row-even" ><div align="center"><nested:write  property="studentName"/></div></td>
					                       <td class="row-even" ><div align="center"><nested:write  property="roomStatus"/></div></td>
					                       
					                       <td class="row-even">
                      						 <div align="center"> 
                      						 <c:if test="${requisitionsid.roomStatus=='Approved' || requisitionsid.roomStatus=='Pending' || requisitionsid.roomStatus=='Applied' || requisitionsid.roomStatus=='Rejected'}">
					                        <a href="javascript:void(0)" onclick="getDetails('<nested:write name="requisitionsid" property="id"/>')"><bean:message key="knowledgepro.view"/></a>
					                        </c:if>
					                        </div>
              								</td>
					                          
					         <td class="row-even" ><div align="center">
					         <c:choose>
					         <c:when test="${requisitionsid.roomStatus=='Approved' || requisitionsid.roomStatus=='Pending' || requisitionsid.roomStatus=='Applied' || requisitionsid.roomStatus=='Rejected'}">
					         <nested:select property="status"  styleId="status" styleClass="combo">
	          		   		<html:option value="Select">Select</html:option>
	        			    <html:option value="Approved">Approved</html:option>
	        			    <html:option value="Pending">Pending</html:option>
	        			    <html:option value="Rejected">Rejected</html:option>
	        			       	    	           		</nested:select>
					         </c:when>
					         <c:otherwise>
					         <nested:select property="status"  styleId="status" styleClass="combo" disabled="true">
	          		   		<html:option value="Select">Select</html:option>
	        			    <html:option value="Approved">Approved</html:option>
	        			    <html:option value="Pending">Pending</html:option>
	        			    <html:option value="Rejected">Rejected</html:option>
	        			       	    	           		</nested:select>
					         </c:otherwise>
					         </c:choose>
					         
	        			       	    	           			</div></td>
					                    
  									<c:set var="temp" value="1"/>
					                         </tr>
		                      		   <c:set var="temp" value="1"/>
		                   		 	</c:when>
		                    	    <c:otherwise>
				                    <tr><nested:hidden property="id"></nested:hidden>
			                               <td class="row-white" ><div align="center"><nested:write  property="roomType"/></div></td>
					                       <td class="row-white" ><div align="center"><nested:write  property="applno"/></div></td>
					                       <td class="row-white" ><div align="center"><nested:write  property="studentName"/></div></td>
					                       <td class="row-white" ><div align="center"><nested:write  property="roomStatus"/></div></td>
					                     	<nested:hidden property="roomStatus"></nested:hidden>
					                       <td class="row-white">
							                       <div align="center"> 
							                        <c:if test="${requisitionsid.roomStatus=='Approved' || requisitionsid.roomStatus=='Pending' || requisitionsid.roomStatus=='Applied' || requisitionsid.roomStatus=='Rejected'}">
							                        <a href="javascript:void(0)" onclick="getDetails('<nested:write name="requisitionsid" property="id"/>')"><bean:message key="knowledgepro.view"/></a>
							                        </c:if>
							                        </div>
							              </td>
					                      
					                     <td class="row-white" ><div align="center"> <c:choose>
					         <c:when test="${requisitionsid.roomStatus=='Approved' || requisitionsid.roomStatus=='Pending' || requisitionsid.roomStatus=='Applied' || requisitionsid.roomStatus=='Rejected'}">
					         <nested:select property="status"  styleId="status" styleClass="combo">
	          		   		<html:option value="Select">Select</html:option>
	        			    <html:option value="Approved">Approved</html:option>
	        			    <html:option value="Pending">Pending</html:option>
	        			    <html:option value="Rejected">Rejected</html:option>
	        			       	    	           		</nested:select>
					         </c:when>
					         <c:otherwise>
					         <nested:select property="status"  styleId="status" styleClass="combo" disabled="true">
	          		   		<html:option value="Select">Select</html:option>
	        			    <html:option value="Approved">Approved</html:option>
	        			    <html:option value="Pending">Pending</html:option>
	        			    <html:option value="Rejected">Rejected</html:option>
	        			       	    	           		</nested:select>
					         </c:otherwise>
					         </c:choose></div></td>
					                      				                         </tr>
		                           <c:set var="temp" value="0"/>
		                    	  </c:otherwise>
		                        </c:choose>
		                      </nested:iterate>
		                      <tr>
							   <td height="2" class="row-even" ><div align="center"></div></td>
		                       <td class="row-even"><div align="center"></div></td>
		                       <td class="row-even" ><div align="center"></div></td>
		                       <td class="row-even" ><div align="center"></div></td>
		                       <td class="row-even" ><div align="center"></div></td>
		                       <td class="row-even" ><div align="center"></div></td>
		                        </tr>
		                     
		                      <tr>
	            <td width="45%" height="35" colspan="3"><div align="right">
	                <html:submit styleClass="formbutton"><bean:message key="knowledgepro.submit" />
							</html:submit>
	           </div></td>
	               <td width="53%" colspan="3"><html:button property="" styleClass="formbutton" onclick="cancelAction()">
										<bean:message key="knowledgepro.cancel" /></html:button></td>
	          </tr>
		                     
		                     	                     
		                     
		                   </table>
		                  
	                      </td>
 					      </tr>
	                                        </table>
	                    </logic:notEmpty>
	                   </td>
	                  
	                 </tr> 
	               
							</table>
							  </div>
								</td>
								<td width="5" height="29" background="images/right.gif"></td>
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
	
						<td valign="top" class="news">&nbsp;</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
						<td width="0" background="images/TcenterD.gif"></td>
						<td><img src="images/Tright_02.gif" width="9" height="29"></td>
					</tr>
				</table>
				</td>
	
			</tr>
		</table>
	</html:form>
						