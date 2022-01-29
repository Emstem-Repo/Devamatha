<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.utilities.CommonUtil"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="java.util.Date"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Employee Information</title>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script language="JavaScript" src="js/admissionform.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<logic:notEmpty property="employeeInfoEditNewTO.empqualification"
							name="EmployeeInfoViewForm">
							<tr>
								<td colspan="2" class="heading" align="left">Academic Qualifications</td>
							</tr>
							
							<tr>
								<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no</div></td>
               			<td   class="row-odd"><div align="center" >Course(Degre Onwards)</div></td> 
               			<td   class="row-odd"><div align="center" >University</div></td>
               			<td   class="row-odd"><div align="center" >Year</div></td>
               			<td   class="row-odd"><div align="center" >Grade/Rank</div></td>
             		 </tr>
             		 <nested:iterate property="employeeInfoEditNewTO.empqualification" name="EmployeeInfoViewForm" id="empqualification" indexId="count">
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><bean:write name="empqualification"  property="courseName" ></bean:write></td>
                		<td  class="row-even"><bean:write name="empqualification" property="universityName"></bean:write></td>
                		<td  class="row-even"><bean:write name="empqualification" property="year" ></bean:write></td>
                		<td  class="row-even"><bean:write  property="grade" name="empqualification"></bean:write></td>
             		 </tr>
                   </nested:iterate>
             
               </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
         </logic:notEmpty>
         
         
         
         <logic:notEmpty property="employeeInfoEditNewTO.interest" name="EmployeeInfoViewForm">
           <tr> 
									<td colspan="2" class="heading" align="left">
						Area Of Interests
					</td>
				</tr>
				
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="50%" border="0"  cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no</div></td>
               			<td   class="row-odd"><div align="left" >Topic</div></td> 
               			
             		 </tr>	
             		 
             		 <nested:iterate property="employeeInfoEditNewTO.interest" name="EmployeeInfoViewForm" id="interest" indexId="count">
                		
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" height="10px"><bean:write name="interest" property="topic"></bean:write></td>
             		 </tr>
             </nested:iterate>
             
             </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
         
         </logic:notEmpty>
         
           <logic:notEmpty property="employeeInfoEditNewTO.research" name="EmployeeInfoViewForm">
              <tr> 
									<td colspan="2" class="heading" align="left">
						Field Of Research
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="50%" border="0"  cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no</div></td>
               			<td   class="row-odd"><div align="left" >Topic</div></td> 
               			
             		 </tr>
             		 
             		 <nested:iterate property="employeeInfoEditNewTO.research" name="EmployeeInfoViewForm" id="research" indexId="count">
                		<tr>
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" height="10px" ><bean:write name="research" property="topic" ></bean:write></td>
             		 </tr>
             </nested:iterate>
             </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
           
           </logic:notEmpty>
           
           
               <logic:notEmpty property="employeeInfoEditNewTO.guideship" name="EmployeeInfoViewForm">
               <tr> 
									<td colspan="2" class="heading" align="left">
						Guideship Details
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Name of the Ph.D Scholars</div></td>
               			<td   class="row-odd"><div align="center" >Year of Registration of the scholar</div></td> 
               			<td   class="row-odd"><div align="center" >Award Ongoing</div></td>
               			<td   class="row-odd"><div align="center" >(If Awarded) Year</div></td>
               			<td   class="row-odd"><div align="center" >(If awarded) Title of the Thesis</div></td>
             		 </tr>	
             		 
					<nested:iterate property="employeeInfoEditNewTO.guideship" name="EmployeeInfoViewForm" id="guideship" indexId="count">
                		
					
                		<tr>
                		
                		
                		<td  class="row-even" ><bean:write name="guideship" property="phdScholarName"></bean:write></td>
                		<td  class="row-even" ><bean:write name="guideship"  property="registrationYear"></bean:write></td>
                		<td  class="row-even"><bean:write name="guideship"  property="awarded"></bean:write></td>
                		<td  class="row-even"><bean:write name="guideship"  property="year" ></bean:write></td>
                		<td  class="row-even"><bean:write name="guideship"  property="thesisName"></bean:write></td>
								 	       		
							    	
             		 </tr>
             </nested:iterate>
             
               </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
          
               
               
               </logic:notEmpty>
               
                  <logic:notEmpty property="employeeInfoEditNewTO.duties" name="EmployeeInfoViewForm">
                  
                  <tr> 
									<td colspan="2" class="heading" align="left">
						Duties Performed/Academic Responsibilities Undertaken
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
               			<td   class="row-odd"><div align="center" >Position Held</div></td> 
               			<td   class="row-odd"><div align="center" >Duration(From - to & Still)</div></td>
               			
             		 </tr>	
             		 
             		 <nested:iterate property="employeeInfoEditNewTO.duties" name="EmployeeInfoViewForm" id="duties" indexId="count">
                		
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><bean:write name="duties" property="positionName"></bean:write></td>
                		<td  class="row-even">
                		<table align="center">
                		<tr>
                		<td class="row-even"><bean:write name="duties" property="fromDate"></bean:write>
                		
                		</td>
                		<td>-</td>
                		<td class="row-even" ><bean:write name="duties" property="toDate"></bean:write>
                		</tr>
                		</table>
                		</td>
								 	       		
							    	
             		 </tr>
             </nested:iterate>
             </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
                  
                  </logic:notEmpty>
                  
                  
                     <logic:notEmpty property="employeeInfoEditNewTO.researchProject" name="EmployeeInfoViewForm">
                     <tr> 
									<td colspan="2" class="heading" align="left">
						Research Projects Undertaken
					</td>
				</tr>
				
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Title</div></td> 
               			<td   class="row-odd"><div align="center" >Name of Project(Minor/Major etc)</div></td> 
               			<td   class="row-odd"><div align="center" >Funding Agency</div></td> 
               			<td   class="row-odd"><div align="center" >Amount Sanctioned</div></td> 
               			<td   class="row-odd"><div align="center" >Duration(From - to & Still)</div></td>
               			
             		 </tr>	
             		 
             		 <nested:iterate property="employeeInfoEditNewTO.researchProject" name="EmployeeInfoViewForm" id="researchProject" indexId="count">
                		
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><bean:write name="researchProject" property="title" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="researchProject"  property="projectName"></bean:write></td>
                		<td  class="row-even" ><bean:write name="researchProject"  property="findingAgencyName" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="researchProject"  property="amount" ></bean:write></td>
                		<td  class="row-even">
                		<table align="center">
                		<tr>
                		<td class="row-even"><bean:write name="researchProject"   property="fromDate1"></bean:write>
                
                		</td>
                		<td>-</td>
                		<td class="row-even" ><bean:write name="researchProject"   property="toDate1"></bean:write>
                		</td>
                		</tr>
                		</table>
                		</td>
								 	       		
							    	
             		 </tr>
             </nested:iterate>
             
              </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
                     
                     </logic:notEmpty>
                     
                      <logic:notEmpty property="employeeInfoEditNewTO.researchPubliction" name="EmployeeInfoViewForm">
                      	<tr> 
									<td colspan="2" class="heading" align="left">
						Research Publication
					</td>
				</tr>
				
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Title of the Paper</div></td> 
               			<td   class="row-odd"><div align="center" >Name of Journal</div></td> 
               			<td   class="row-odd"><div align="center" >Year</div></td> 
               			<td   class="row-odd"><div align="center" >ISBN/ISSN No.</div></td> 
               			<td   class="row-odd"><div align="center" >UGC/Non UGC</div></td> 
               			
               			
             		 </tr>	
             		 <nested:iterate property="employeeInfoEditNewTO.researchPubliction" name="EmployeeInfoViewForm" id="researchPubliction" indexId="count">
                		
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><bean:write name="researchPubliction"  property="paperTitle" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="researchPubliction"  property="journalName" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="researchPubliction"  property="year"  ></bean:write></td>
                		<td  class="row-even" ><bean:write name="researchPubliction"  property="ISBNISSNNo"  ></bean:write></td>
                		<td  class="row-even" ><bean:write name="researchPubliction"  property="UGCNonUGC" ></bean:write></td>
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
              </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
                      
                      </logic:notEmpty>
                      
                      <logic:notEmpty property="employeeInfoEditNewTO.empBooks" name="EmployeeInfoViewForm">
                      
                      <tr> 
									<td colspan="2" class="heading" align="left">
						Books/Book Chapters Published
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Title of the Book/Chapter</div></td> 
               			<td   class="row-odd"><div align="center" >Year of Publication</div></td> 
               			<td   class="row-odd"><div align="center" >Name of Publisher</div></td> 
               			<td   class="row-odd"><div align="center" >ISBN/ISSN No.</div></td> 
               			<td   class="row-odd"><div align="center" >Nature of Contribution</div></td> 
               			
               			
             		 </tr>	
             		 
					<nested:iterate property="employeeInfoEditNewTO.empBooks" name="EmployeeInfoViewForm" id="empBooks" indexId="count">
                	
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><bean:write name="empBooks"  property="titleName" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="empBooks"   property="publisherName" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="empBooks"   property="year"></bean:write></td>
                		<td  class="row-even" ><bean:write name="empBooks"   property="ISBNISSN" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="empBooks"  property="contibutionName"></bean:write></td>
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
             </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
                      
                      </logic:notEmpty>
                      
                      
               <logic:notEmpty property="employeeInfoEditNewTO.empPaper" name="EmployeeInfoViewForm">
               
               <tr> 
									<td colspan="2" class="heading" align="left">
						Paper Presentation
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Title of the Paper</div></td> 
               			<td   class="row-odd"><div align="center" >Title of Paper/Seminar/Conference</div></td> 
               			<td   class="row-odd"><div align="center" >Date</div></td> 
               			<td   class="row-odd"><div align="center" >Organising Institution</div></td> 
               			<td   class="row-odd"><div align="center" >International/National/Regional</div></td> 
               			
               			
             		 </tr>	
             		 
             		 <nested:iterate property="employeeInfoEditNewTO.empPaper" name="EmployeeInfoViewForm" id="empPaper" indexId="count">
                		
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><bean:write name="empPaper" property="paperName" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="empPaper"  property="seminarName"></bean:write></td>
                		<td class="row-even"><bean:write name="empPaper"  property="date1"></bean:write>
                		</td>
                		<td  class="row-even" ><bean:write name="empPaper"  property="organisation" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="empPaper"  property="regional" ></bean:write></td>
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
             </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
               
               
               </logic:notEmpty>
               
               <logic:notEmpty property="employeeInfoEditNewTO.empSeminarAttendedDetails" name="EmployeeInfoViewForm">
               	<tr> 
									<td colspan="2" class="heading" align="left">
						Seminar/Workshop/Conference Attended
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Seminar/Workshop/Conference</div></td> 
               			<td   class="row-odd"><div align="center" >Title</div></td> 
               			<td   class="row-odd"><div align="center" >Chair/Partcipation</div></td> 
               			<td   class="row-odd"><div align="center" >Date(from-to)</div></td> 
               			<td   class="row-odd"><div align="center" >Orgainising Institution</div></td> 
               			<td   class="row-odd"><div align="center" >International/National/Regional</div></td> 
               			
               			
             		 </tr>	
             	       		<nested:iterate property="employeeInfoEditNewTO.empSeminarAttendedDetails" name="EmployeeInfoViewForm" id="empSeminarAttendedDetails" indexId="count">
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><bean:write name="empSeminarAttendedDetails" property="seminar" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="empSeminarAttendedDetails"  property="seminarName"></bean:write></td>
                		<td  class="row-even" ><bean:write name="empSeminarAttendedDetails"  property="participation" ></bean:write></td>
                		<td  class="row-even">
                		<table align="center">
                		<tr>
                		<td class="row-even"><bean:write name="empSeminarAttendedDetails"  property="fromDate2" ></bean:write>
                		
                		</td>
                		<td>-</td>
                		<td class="row-even" ><bean:write name="empSeminarAttendedDetails"  property="toDate2"></bean:write>
                	</td>
                		</tr>
                		</table>
                		</td>
                		<td  class="row-even" ><bean:write name="empSeminarAttendedDetails"  property="organisation" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="empSeminarAttendedDetails"  property="interRegional"></bean:write></td>
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
             
             </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
               </logic:notEmpty>
               
                <logic:notEmpty property="employeeInfoEditNewTO.empPersonaldevelopment" name="EmployeeInfoViewForm">
                
                <tr> 
									<td colspan="2" class="heading" align="left">
						Course Attended for Professional Development
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Name of the Course</div></td> 
               			<td   class="row-odd"><div align="center" >Organising Institute</div></td> 
               			<td   class="row-odd"><div align="center" >Period(from--to)</div></td> 
               			
               			
               			
             		 </tr>
             		 	<nested:iterate property="employeeInfoEditNewTO.empPersonaldevelopment" name="EmployeeInfoViewForm" id="empPersonaldevelopment" indexId="count">
                		
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><bean:write name="empPersonaldevelopment"  property="name"></bean:write></td>
                		<td  class="row-even" ><bean:write name="empPersonaldevelopment"  property="organisation"></bean:write></td>
                		
                		<td  class="row-even">
                		<table align="center">
                		<tr>
                		<td class="row-even"><bean:write name="empPersonaldevelopment"  property="fromDate3" ></bean:write>
          
                		</td>
                		<td>-</td>
                		<td class="row-even" ><bean:write name="empPersonaldevelopment"  property="toDate3" ></bean:write>
                		</td>
                		</tr>
                		</table>
                		</td>
                		
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
              </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
                </logic:notEmpty>
                
                <logic:notEmpty property="employeeInfoEditNewTO.empAward" name="EmployeeInfoViewForm">
                <tr> 
									<td colspan="2" class="heading" align="left">
						Awards/Recognition/Patents Confered
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Name of the Recognition</div></td> 
               			<td   class="row-odd"><div align="center" >Name of the Activity</div></td> 
               			<td   class="row-odd"><div align="center" >Name of the Awards/Recognition/Patents</div></td>
               			<td   class="row-odd"><div align="center" >Name of the Awarding Body</div></td>
               			<td   class="row-odd"><div align="center" >Year</div></td>
               			
               			
               			
               			
             		 </tr>
             		 
             		 <nested:iterate property="employeeInfoEditNewTO.empAward" name="EmployeeInfoViewForm" id="empAward" indexId="count">
                		
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><bean:write name="empAward" property="awardName" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="empAward"  property="activityname" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="empAward"  property="recognitionName" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="empAward"  property="awardbodyName" ></bean:write></td>
                		<td  class="row-even" ><bean:write name="empAward"  property="year"></bean:write></td>
                		
                		
                		
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
             
             
             </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
				
                
                </logic:notEmpty>
                
                  <logic:notEmpty property="employeeInfoEditNewTO.empMemberShip" name="EmployeeInfoViewForm">
                  	<tr> 
									<td colspan="2" class="heading" align="left">
						Membership in Academic Bodies
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
      		
                     <tr>
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Name of the Body</div></td> 
               			<td   class="row-odd"><div align="center" >Period(from--to)</div></td> 
               			
               			
               			
             		 </tr>	
             		 
             		 <nested:iterate property="employeeInfoEditNewTO.empMemberShip" name="EmployeeInfoViewForm" id="empMemberShip" indexId="count">
                		
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><bean:write name="empMemberShip"  property="name" ></bean:write></td>
                		<td  class="row-even">
                		<table align="center">
                		<tr>
                		<td class="row-even"><bean:write name="empMemberShip"  property="fromDate4"></bean:write>
                		
                		</td>
                		<td>-</td>
                		<td class="row-even" ><bean:write name="empMemberShip"  property="todate4"></bean:write>
                		</td>
                		</tr>
                		</table>
                		</td>
                		
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
             
             </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
			<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
                  
                  </logic:notEmpty>