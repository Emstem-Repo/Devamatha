<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script type="text/javascript" src="js/jquery.js"></script>

<script type="text/javascript">
	function closeWindow(){
		document.location.href = "LoginAction.do?method=loginAction";
	}
</script>

<script type="text/javascript">
$(document).ready(function(){
$(".designation").hide();
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".Working").click(function(){
$(".designation").show();
});
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".NotWorking").click(function(){
$(".designation").hide();
});
});
</script>

<script type="text/javascript">
$(document).ready(function(){
$(".removeFee").hide();
});


$(document).ready(function(){
$(".FeeConcession").click(function(){
$(".removeFee").show();
});
});

$(document).ready(function(){
$(".removeFee").click(function(){
$(".removeFee").hide();
});
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".removeRemark").hide();
});


$(document).ready(function(){
$(".remark").click(function(){
$(".removeRemark").show();
});
});

$(document).ready(function(){
$(".removeRemark").click(function(){
$(".removeRemark").hide();
});
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".removeIncentives").hide();
});


$(document).ready(function(){
$(".Incentives").click(function(){
$(".removeIncentives").show();
});
});

$(document).ready(function(){
$(".removeIncentives").click(function(){
$(".removeIncentives").hide();
});
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".removeLoan").hide();
});


$(document).ready(function(){
$(".LoanDetails").click(function(){
$(".removeLoan").show();
});
});

$(document).ready(function(){
$(".removeLoan").click(function(){
$(".removeLoan").hide();
});
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".remove").hide();
});


$(document).ready(function(){
$(".morerows").click(function(){
$(".remove").show();
});
});

$(document).ready(function(){
$(".remove").click(function(){
$(".remove").hide();
});
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".remove1").hide();
});


$(document).ready(function(){
$(".morerows1").click(function(){
$(".remove1").show();
});
});

$(document).ready(function(){
$(".remove1").click(function(){
$(".remove1").hide();
});
});
</script>


<script type="text/javascript">
$(document).ready(function(){
$(".educationshow").hide();
});

$(document).ready(function(){
$(".educationAddMoreRows").click(function(){
$(".educationshow").show();
});
});

$(document).ready(function(){
$(".educationremove").click(function(){
$(".educationshow").hide();
});
});

</script>
<script type="text/javascript">
$(document).ready(function(){
$(".removeFinancial").hide();
});

$(document).ready(function(){
$(".Financial").click(function(){
$(".removeFinancial").show();
});
});

$(document).ready(function(){
$(".removeFinancial").click(function(){
$(".removeFinancial").hide();
});
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".removeApproved").hide();
});

$(document).ready(function(){
$(".ApprovedAchievements").click(function(){
$(".removeApproved").show();
});
});

$(document).ready(function(){
$(".removeApproved").click(function(){
$(".removeApproved").hide();
});
});
</script>
</head>

<body>
<table width="98%" border="0">
  <tr>
    <td><span class="heading">
				EmployeeInfo
			<span class="Bredcrumbs">&gt;&gt; Employee Information &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
<td width="1271" background="images/Tcenter.gif" class="body" >
<div align="left">
<strong class="boxheader">Employee Info</strong>
</div>
</td>
<td width="15" >
<img src="images/Tright_1_01.gif" width="9" height="29"/>	   </td>
</tr>
<tr>
<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
<td valign="top" class="news"><div align="center">
<table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
<tr>
<td height="20" colspan="6" align="left">
<div align="right" style="color:red"> <span class='MandatoryMark'>mandatoryfields</span></div>
</td>
</tr>
<tr>
<td height="20" colspan="6" valign="top" class="body" >
<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left">&nbsp;Personal Info</td>
		<td align="right"><a href="">Search Resume</a></Search></td>
	</tr>
	<tr>

      
      
        <td colspan="3" valign="top" class="news">
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
              <tr >
               <td class="row-odd"><div align="right" >Teaching Staff:
                  </div></td>
                <td height="25" class="row-even" >
                 <input name="staff" type="radio"/>Yes
                   <input name="staff" type="radio"/>No
                    </td></tr>
                    <tr>
                <td class="row-odd"><div align="right" >ID: </div></td>
                <td  class="row-even" colspan="3"><span class="star">
					<input type="text" name="id" /></span></td></tr>
              <tr>
			    <td class="row-odd"><div align="right" >Name: </div></td>
                <td class="row-even" colspan="3"><span class="star">
					<input type="text" name="name" size="35" /></span>				</td>
		      </tr>
           
              <tr>
                 <td class="row-odd"><div align="right" >Gender:</div></td>
               <td class="row-even" ><input type="radio" name="gender" />
               Male&nbsp;&nbsp; <input type="radio" name="gender1" />
               Female </td>
			  </tr>
				<tr>
				 <td class="row-odd"><div align="right" >Date of Birth:</div></td>
               <td class="row-even" ><span class="star"> <input type="text" name="dob" /></span></td>
				</tr>
				
				<tr>
				<td height="25" class="row-odd"><div align="right" >Email-Id:</div></td>
                  <td class="row-even"  ><span class="star">
				  <input type="text" name="email-Id"  /></span>				 </td>
				</tr>
				<tr>
				<td class="row-odd"><div align="right" > Official Email-Id:</div></td>
                  <td class="row-even"  ><span class="star">
				  <input type="text" name="email-Id1"  /></span>				 </td>
				</tr>
				<tr>
				<td class="row-odd"><div align="right" > PAN NO:</div></td>
                  <td class="row-even"  ><span class="star">
				  <input type="text" name="panno"  /></span>				 </td>
				</tr>
				<tr>
				<td class="row-odd"><div align="right" >Blood Group: </div></td>
                <td class="row-even" >
				<select>
				<option>O+ve</option>
				<option>O-ve</option>
				<option>B+ve</option>
				</select>				</td>
				</tr>
				<tr>
				<td class="row-odd"><div align="right" >Marital Status:</div></td>
                <td height="25" class="row-even" ><span class="star">
                  <select name="select">
                    <option>Single</option>
                    <option>Married</option>
                    <option>Widow</option>
                    <option>Divorced</option>
                  </select>
                </span></td></tr>
				
				<tr>
				<td class="row-odd"><div align="right" >Current Address:</div></td>
				 <td height="25" class="row-even" >Address1:&nbsp;
		           <input type="text" name="address1" />
				 Address2:<input type="text" name="address2" />				 </td>
				</tr>
				<tr>
				<td class="row-odd"><div align="right" ></div></td>
				 <td height="25" class="row-even" >City:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <input type="text"/>
				 	State: &nbsp;&nbsp;&nbsp;&nbsp; <input type="text"/>		     
				 </td>
				</tr>
				<tr>
				<td class="row-odd"><div align="right" ></div></td>
				 <td height="25" class="row-even" >Pin Code:&nbsp;
				   <input name="text" type="text"/></td>
				</tr>
				
				
				<tr>
				<td class="row-odd"><div align="right" >Permanent Address :</div></td>
				 <td height="25" class="row-even" >Street1:&nbsp;&nbsp;&nbsp;
				   <input type="text" name="address" />
				   Street2:&nbsp;&nbsp;&nbsp;<input type="text" /></td>
				</tr>
				<tr>
				<td class="row-odd"><div align="left" ></div></td>
				 <td height="25" class="row-even" >City:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			      <input type="text"/>
			      State:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <input type="text"/> </td>
				</tr>
				<tr>
				<td class="row-odd"><div align="left" ></div></td>
				 <td height="25" class="row-even" >Country:&nbsp;&nbsp;
				   <input type="text"/>
				   Zip:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <input type="text"/></td>
				</tr>
				<tr>
				<td class="row-odd"><div align="left" ></div></td>
				
                  <td class="row-even" colspan="3"  >

					Home Telephone:
				  <input type="text" name="add1" size="3" maxlength="3"/>&nbsp;
				<input type="text" name="add1" size="3" maxlength="3"/>&nbsp;
                <input type="text" name="add1" size="10" maxlength="10"/>&nbsp;  
				  </td>
				</tr>
				<tr>
				<td class="row-odd"><div align="right" >Religion:</div></td>
				 <td class="row-even"  ><span class="star">
				 <select>
				 <option>Hinduism</option>
				 <option>Muslim</option>
				 <option>Buddism</option>
				 </select>
				 </span>				
				  </td>
				</tr>
				<tr>
				<td class="row-odd"> <div align="right" >
			<label>Reservation Category :</label></div>
  		</td>
		<td class="row-even">
			<label>GM:</label>
			<input name="gm" type="checkbox" value="GM" />
			<label>SC:</label>
			<input name="sc" type="checkbox" value="SC" />
			<label>ST:</label>
			<input name="st" type="checkbox" value="ST" />
			<label>OBC:</label>
			<input name="obc" type="checkbox" value="OBC" />
			<label>Minority:</label>
			<input name="minority" type="checkbox" value="MINORITY" />
			<label>Person with Disability:</label>
			<input name="disability" type="checkbox" value="Person with Disability Phone" />
		</td> 
  		</tr>
				<tr>
				<td class="row-odd"><div align="right" >Nationality:</div></td>
				 <td class="row-even"  ><span class="star">
				 <select>
				 <option>--select--</option>
				 <option>Indian</option>
				 <option>US Citizen</option>
				 <option>Afghan</option>
				 </select>
				 </span>				
				  </td>
				</tr>
				<tr>
					<td class="row-odd"><div align="right" >Mobile:</div></td>
				 <td class="row-even"  >
				 <input type="text" name="mobile" />				 </td>
				</tr>
				<tr>
					<td class="row-odd"><div align="right" >Telephone Number:</div></td>
				 <td class="row-even"  >
				 <input type="text" name="telnno" />				
				  </td>
				</tr>
				<tr>
					<td class="row-odd"><div align="right" >Bank Account Details:</div></td>
				 <td class="row-even"  >
				 <input type="text" name="bankaccno" />				
				  </td>
				</tr>
				<tr>
					<td class="row-odd"><div align="right" >PF Account Number:</div></td>
				 <td class="row-even"  >
				 <input type="text" name="pfNumber" />				
				  </td>
				</tr>
				<tr>
					<td class="row-odd"><div align="right" >Vehicle Number:</div></td>
				 <td class="row-even"  >
				 <input type="text" name="vehicleno" />	
				  </td>
				</tr>
				<tr>
					<td class="row-odd"><div align="right" >Two Wheeler Number:</div></td>
				 <td class="row-even"  >
				 <input type="text" name="twoWheeler" />	
				  </td>
				</tr>
				<tr>
					<td class="row-odd"><div align="right" >Four Wheeler Number:</div></td>
				 <td class="row-even"  >
				 <input type="text" name="fourWheeler" />	
				  </td>
				</tr>
				<tr>
					<td class="row-odd"><div align="right" >Photograph:</div></td>
				 <td class="row-even"  >
				 <input type="text" name="photograph" size="35" />	
				<input type="button" value="Browse">			
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
</table>

<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left">Job</td>
	</tr>
	<tr>

      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="28%" height="25" class="row-odd"><div align="right" >Employee Type: </div></td>
                <td width="22%" class="row-even"><select name="select2">
                    <option>Aided Teaching</option>
                    <option>Unaided Teaching</option>
                    <option>Aided Non Teaching</option>
                    <option>Unaided Non Teaching</option>
                </select>                </td>
                <td width="18%" class="row-odd"><div align="right" > Joined  Date
                  : </div></td>
                <td width="32%" class="row-even"><span class="star">
                  <input type="text" name="joinedDate" />
                </span> </td>
              </tr>
              <tr >
                <td height="25" class="row-odd"><div align="right" > Rejoin  Date: </div></td>
                <td class="row-even"><span class="star">
                  <input type="text" name="dor" />
                </span></td>
                <td class="row-odd"><div align="right" > Date of Retirement
                  :</div></td>
                <td height="25" class="row-even" ><input type="text" name="dateOfExpiry2" />                </td>
              </tr>
              <tr >
                <td height="25" class="row-odd"><div align="right" > Active: </div></td>
                <td class="row-even"><span class="star">
                 <input name="active" type="radio"/>Yes
                   <input name="active" type="radio"/>No
                </span></td>
                <td width="28%" class="row-odd"><div align="right" >Stream Details:</div></td>
                <td height="25" class="row-even" ><span class="star">
                  <select name="select3">
                    <option>-select-</option>
                    <option></option>
                  </select>
                </span>				
                </td>
              </tr>
              <tr>
                <td width="18%" class="row-odd"><div align="right" >Work Location:</div></td>
                <td height="25" class="row-even" ><span class="star">
                <select><option>kengeri Campus</option>
				<option>Main Campus</option></select>
                </span></td>
                <td width="28%" class="row-odd"><div align="right" >Designation: </div></td>
                <td width="22%" class="row-even">
				<select>
				<option>Manager</option>
				<option>Staff</option>
				<option>Director</option>
				<option>Lecturer</option>
				<option>Seniour Lecturer</option>
				</select>
				Grade<select>
				<option>1</option>
				<option>2</option>
				<option>3</option>
				<option>4</option>
				<option>5 </option>
				</select>			
					</td>
              </tr>
             <tr >
			    <td width="18%"  class="row-odd"><div align="right" >Department: </div></td>
                <td width="25%" class="row-even">
                <select>
                <option>Account Departments</option>
                <option>Education Departments</option>
                <option>MBA Departments</option>
                <option>Admission Office</option>
                </select>
				</td>
				<td width="28%" class="row-odd"><div align="right" >Report To:</div> </td>
                <td width="22%" class="row-even">
				<select>
				<option>PROF.JEEVAN</option>
				<option>Uma</option>
				</select>
				</td>
		      </tr>
           		 <tr>
                
               <td width="18%"  class="row-odd"><div align="right" >Super Annuation date: </div></td>
                <td width="25%" class="row-even" colspan="3">
                <input type="text"> 
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
</table>

<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left">
   Leave Details </td>
	</tr>
	<tr>

      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
             <tr>
											<td height="25" class="row-odd">
											<div align="center">slno</div>
											</td>
											<td class="row-odd">Leave Type
											</td>
											<td class="row-odd">Leave Allocated(days)</td>
											<td class="row-odd"> 	Leave Sanctioned(days)</td>
											<td height="25" class="row-odd"> 	Leave Remaining(days)</td>
			  </tr>
			   											 <tr>
																<td height="25" class="row-even">
																<div align="center"></div>1</td>
																<td class="row-even">Sick Leave</td>
																<td class="row-even">3</td>
																<td class="row-even">2</td>
																<td height="25" class="row-even">1</td>
														  </tr>
														  <tr>
																<td height="25" class="row-even">
																<div align="center"></div>2</td>
																<td class="row-even">CL</td>
																<td class="row-even">4</td>
																<td class="row-even">3</td>
																<td height="25" class="row-even">1</td>
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
</table>

<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left">PayScale Details </td>
	</tr>
	<tr>

      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td   class="row-odd"><div align="right" >Pay Scale : </div></td>
                <td  class="row-even">
                  <select>
                  <option>Assistant Lecturer level 1</option>
                  </select>
               </td>
              </tr>
              <tr>
              	<td   class="row-odd"><div align="right" >scale : </div></td>
                <td  class="row-even"><span class="star">
                  <input type="text" name="allowance" value="1600-600-1800" />
                </span></td>
              </tr>
              <tr>
              	<td  class="row-odd"><div align="right" >Basic Pay : </div></td>
                <td  class="row-even"><span class="star">
                  <input type="text" name="basicPay" />
                </span></td>
              </tr>
              <tr>
              	<td   class="row-odd"><div align="right" >HRA : </div></td>
                <td  class="row-even"><span class="star">
                  <input type="text" name="hri" />
                </span></td>
              </tr>
              <tr>
              	<td  class="row-odd"><div align="right" >DA : </div></td>
                <td  class="row-even"><span class="star">
                  <input type="text" name="da" />
                </span></td>
              </tr>
              <tr>
              	<td  class="row-odd"><div align="right" >Gross Pay : </div></td>
                <td  class="row-even"><span class="star">
                  <input type="text" name="grossPay" />
                </span></td>
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
</table>
       <table width="100%" cellspacing="1" cellpadding="2">
         <tr>
           <td colspan="2" class="heading" align="left">Dependent details </td>
         </tr>
         <tr>
           <td colspan="3" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
               <tr>
                 <td ><img src="images/01.gif" width="5" height="5" /></td>
                 <td width="914" background="images/02.gif"></td>
                 <td><img src="images/03.gif" width="5" height="5" /></td>
               </tr>
               <tr>
                 <td width="5"  background="images/left.gif"></td>
                 <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                     <tr >
                       <td class="row-odd"><div align="left" >Dependents:</div></td>
                       <td class="row-even" colspan="3">
                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         Relationship:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           Date of Birth:<br></br>
                          <input type="text" name="name"> 
                          <input type="text" name="name"> 
                          <input type="text" name="name"> 
                       </td>
                     </tr>
                     <tr class="morerows1">
                       <td class="row-odd"><input name="Input" type="button" value="click to add more rows" />
                       </td>
                       <td class="row-even">
                           <input name="desc2" type="text" size="20" />
                           
                           <input name="desc2" type="text" size="20" />
                           
                           <input name="dob2" type="text" size="20" />
                       </td>
                     </tr>
                     <tr class="remove1">
                       <td class="row-odd"><input name="Input" type="button" value="Remove" />
                       </td>
                       <td class="row-even">
                           <input name="desc2" type="text" />
                         
                           <input name="desc2" type="text" />
                          
                           <input name="dob2" type="text" />
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
       </table>
       <table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left">Emergency Contact </td>
	</tr>
	<tr>

      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
			<table width="100%" height="43" border="0" cellpadding="0" cellspacing="1">
                      <tr >
                             <td width="28%" height="25" class="row-odd"><div align="right" >Name:</div></td>
                             <td width="22" class="row-even"><input type="text" name="name2" /></td>
                             <td width="18%" class="row-odd"><div align="right" >Relation Ship: </div></td>
                           <td width="32" class="row-even"><input type="text" name="relationShip" /></td>
              </tr>
                        
                          <tr >
                            <td width="18%" height="25" class="row-odd"><div align="right" >Home Telephone:</div></td>
                            <td width="22"  class="row-even">
                           
                         <input type="text" name="tel" size="3" maxlength="3" />&nbsp;
						 <input type="text" name="tel" size="3" maxlength="3" />&nbsp;
                             <input type="text" name="tel" size="10" maxlength="10" />                            </td>
                           <td width="28%"  class="row-odd"><div align="right" >Mobile:</div></td>
                            <td width="32"  class="row-even">
                           
                         <input type="text" name="tel" size="3" maxlength="3" />&nbsp;
						 <input type="text" name="tel" size="3" maxlength="3" />&nbsp;
                             <input type="text" name="tel" size="10" maxlength="10" />                            </td>
                          </tr>
                          <tr class="row-even">
                           <td width="28%" height="25" class="row-odd"><div align="right" >Work Telephone:</div></td>
                          
                            <td class="row-even" colspan="3"  >
                         <input type="text" name="tel" size="3" maxlength="3" />&nbsp;
						 <input type="text" name="tel" size="3" maxlength="3" />&nbsp;
                             <input type="text" name="tel" size="10" maxlength="10" />                            </td>
                          </tr>
              </table>
			</td>
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
</table>				
				
<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left">Passport Details </td>
	</tr>
	<tr>

      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="28%" height="25" class="row-odd"><div align="right" >Passport No </div></td>
                <td width="22%" class="row-even"><span class="star">
					<input type="text" name="id" /></span>
				</td>
                <td width="18%" class="row-odd"><div align="right" >Issued Date: </div></td>
                <td width="32%" class="row-even"><span class="star">
				<input type="text" name="nationality" />
				</span>
				</td>
              </tr>
              <tr >
                <td height="25" class="row-odd"><div align="right" >Status: </div></td>
                <td class="row-even"><span class="star"> <input type="text" name="panNo" /></span></td>
                <td class="row-odd"><div align="right" >Date of Expiry :</div></td>
                <td height="25" class="row-even" >
				<input type="text" name="dateOfExpiry" />
 				</td>
              </tr>
               
                <tr>
                <td width="18%" class="row-odd"><div align="right" >Review Status:</div></td>
                <td height="25" class="row-even" >  <input type="text" name="status" /> 
                  </td><td width="18%" class="row-odd"><div align="right" >Comments:</div></td>
               <td height="25" class="row-even" ><span class="star"> <input type="text" name="comments" /></span></td>
                </tr>
                
               
                <tr><td  width="28%" height="25" class="row-odd"><div align="right" >CitizenShip:</div></td>
                  <td class="row-even" colspan="3"  ><span class="star">
				<select>
				<option>India</option>
				<option>SriLanka</option>
				<option>Nepal</option>
				</select></span>
				  </td>   
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
</table>



<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left">Visa</td>
	</tr>
	<tr>
  
      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="28%" height="25" class="row-odd"><div align="right" >Visa No </div></td>
                <td width="22%" class="row-even"><span class="star">
					<input type="text" name="id" /></span>
				</td>
                <td width="18%" class="row-odd"><div align="right" >Issued Date: </div></td>
                <td width="32%" class="row-even"><span class="star">
				<input type="text" name="nationality" />
				</span>
				</td>
              </tr>
              <tr >
                <td height="25" class="row-odd"><div align="right" >Status: </div></td>
                <td class="row-even"><span class="star"> <input type="text" name="panNo" /></span></td>
                <td class="row-odd"><div align="right" >Date of Expiry :</div></td>
                <td height="25" class="row-even" >
				<input type="text" name="dateOfExpiry" />
 				</td>
              </tr>
               
                <tr>
                <td width="18%" class="row-odd"><div align="right" >Review Status:</div></td>
                <td height="25" class="row-even" >  <input type="text" name="status" /> 
                  </td><td width="18%" class="row-odd"><div align="right" >Comments:</div></td>
               <td height="25" class="row-even" ><span class="star"> <input type="text" name="comments" /></span></td>
                </tr>
                
               
                <tr><td  width="28%" height="25" class="row-odd"><div align="right" >CitizenShip:</div></td>
                  <td class="row-even" colspan="3"  ><span class="star">
				<select>
				<option>India</option>
				<option>SriLanka</option>
				<option>Nepal</option>
				</select></span>
				  </td>   
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
</table>

<table><tr height="5px">
							<td colspan="2" class="heading" align="left">Work Time Entry </td>
							</tr></table>
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							
							<tr>
								<td><img src="images/01.gif" width="5" height="5"></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5"></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td width="100%" height="29" valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
										              <tr>
									<td width="28%" class="row-odd">
									<div align="right">&nbsp; Time In:</div>
									</td>
									<td width="22%" class="row-even">
									<html:text  property="startingTimeHours" styleClass="Timings" value="00" styleId="startingTimeHours" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
									<html:text  property="startingTimeMins" styleClass="Timings" value="00" styleId="startingTimeMins" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
									<td width="18%" class="row-odd">
									<div align="right">Time In Ends :</div>
									</td>
									<td width="32%" class="row-even">
									<html:text  property="endingTimeMins1" styleClass="Timings" value="00" styleId="endingTimeMins" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
									<html:text  property="endingTimeMins" styleClass="Timings" value="00" styleId="startingTimeMins" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
									</tr>
									<tr>
									<td width="28%" class="row-odd">
									<div align="right">Time Out:</div>
									</td>
									<td width="22%" class="row-even">
									<html:text  property="endingTimeHours" styleClass="Timings" value="00" styleId="endingTimeHours" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
									<html:text  property="endingTimeMins" styleClass="Timings" value="00" styleId="endingTimeMins" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
									<td width="18%" class="rows-odd"><div align="right" >Saturday Time Out:</div></td>
									<td width="32%" class="row-even">
									<html:text  property="endingTimeHours" styleClass="Timings" value="00" styleId="endingTimeHours" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
									<html:text  property="endingTimeMins" styleClass="Timings" value="00" styleId="endingTimeMins" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
								</tr>
										 </table>									
								</td>
								<td background="images/right.gif" width="5" height="29"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5"></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif"></td>
							</tr>
						</table>

<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left">
Resignation Details </td>
	</tr>
	<tr>

      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td height="25" class="row-odd"><div align="right" >
                  Date of  Resignation
                  : </div></td>
                <td width="22%" class="row-even"><span class="star">
                  <input type="text" name="dor" />
                </span></td>
                <td class="row-odd"><div align="right" > Date of Leaving
                  :</div></td>
                <td width="32%" height="25" class="row-even" ><input type="text" name="dateOfExpiry2" />                </td>
              </tr>
              <tr>
                <td width="28%" class="row-odd"><div align="right" >Reason for Leaving:</div></td>
                <td height="25" class="row-even" colspan="3"><span class="star">
                  <input type="text" name="grosspay" size="30" />
                </span>				</td>
                
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
</table>
<table width="100%" cellspacing="1" cellpadding="2">
  <tr>
    <td colspan="2" class="heading" align="left">Professional Experience </td>
  </tr>
  <tr>
    <td colspan="3" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td ><img src="images/01.gif" width="5" height="5" /></td>
        <td width="914" background="images/02.gif"></td>
        <td><img src="images/03.gif" width="5" height="5" /></td>
      </tr>
      <tr>
        <td width="5"  background="images/left.gif"></td>
        <td valign="top">
         <table width="100%" cellspacing="1" cellpadding="2">
              
              
              <tr >
                 <td width="28%" class="row-odd"><div align="left" >Teaching Experience:</div></td>
               <td height="22" class="row-even" >
			<label>Years:</label>
			<input name="expYears" type="text" size="5px" value="1"/>
			<label>Months:</label>
			<input name="expMonths" type="text" size="5px" value="10"/>
			<label>Designation:</label>
			<input name="desc" type="text" />
			<label>Institution:</label>
			<input name="desc" type="text" />
			   </td>
			  </tr>
			  
			  <tr class="morerows">
		<td class="row-odd" width="28">
			<input name="" type="button" value="click to add more rows" />
		</td>
		<td class="row-even"  width="22">
			<label>Years:</label>
			<input name="expYears" type="text" size="5px"/>
			<label>Months:</label>
			<input name="expMonths" type="text" size="5px"/>
			<label>Designation:</label>
			<input name="desc" type="text" />
			<label>Institution:</label>
			<input name="desc" type="text" />
		</td>
	</tr>
	<tr class="remove" >
		<td class="row-odd" width="28">
			<input name="" type="button" value="Remove" />
		</td>
		<td class="row-even" width="22">
			<label>Years:</label>
			<input name="expYears" type="text" size="5px"/>
			<label>Months:</label>
			<input name="expMonths" type="text" size="5px"/>
			<label>Designation:</label>
			<input name="desc" type="text" />
			<label>Institution:</label>
			<input name="desc" type="text" />
		</td>
	</tr>
	<tr>
		<td class="row-odd" width="28">
			<label>Industry Experience Years :</label>
		</td>
		<td class="row-even"  width="22">
			<label>Years:</label>
			<input name="expYears" type="text" size="5px" value="2	"/>
			<label> Months:</label>
			<input name="expMonths" type="text" size="5px" value="4"/>
			<label>Designation:</label>
			<input name="desc" type="text" />
			<label>Organisation:</label>
			<input name="desc" type="text" />
		</td>
	</tr>
	<tr class="morerows1">
		<td class="row-odd" width="28">
			<input name="" type="button" value="click to add more rows" />
		</td>
		<td class="row-even" width="22">
			<label>Years:</label>
			<input name="expYears" type="text" size="5px"/>
			<label>Months:</label>
			<input name="expMonths" type="text" size="5px"/>
			<label>Designation:</label>
			<input name="desc" type="text" />
			<label>Organisation:</label>
			<input name="desc" type="text" />
		</td>
	</tr>
	<tr class="remove1">
		<td class="row-odd" width="28">
			<input name="" type="button" value="Remove" />
		</td>
		<td class="row-even" width="22">
		<label>Years:</label>
			<input name="expYears" type="text" size="5px" value=""/>
			<label>Months:</label>
			<input name="expMonths" type="text" size="5px" value=""/>
			<label>Designation:</label>
			<input name="desc" type="text" />
			<label>Organisation:</label>
			<input name="desc" type="text" />
		</td>
	</tr>
	<tr>
  	<td class="row-odd" width="28"> 
		<label>Total Experience:</label>
  	</td>
	<td class="row-even" width="22">
		<label>4</label>
	</td> 
  </tr>
  <tr> 
  	 <td class="row-odd" width="28">
      	<label>Qualification level: </label>
     </td>
	 <td  class="row-even" width="22">
	 	 <select name="qulification">
	   		<option>Certificate </option>
	   		<option>Diploma</option>
			<option>Degree/PG </option>
	   		<option>Prof.Degree</option>
			<option>Mphil </option>
	   		<option>Diploma</option>
    	 </select>
	 </td>
  </tr>
   <tr> 
  	 <td class="row-odd" width="28">
      	<label>Subject area: </label>
     </td>
	 <td  class="row-even" width="22">
	 	 <select name="subjectArea">
	   		<option>Accounts/Finance/Tax/CS/Audit</option>
	   		<option>Architecture/Interior Design</option>
			<option>Banking/Insurance </option>
	   		<option>Content/Journalism</option>
			<option>Corporate Planning /Consulting </option>
	   		<option>Engineering Design/R&D </option>
    	 </select>
			 </td>
  			</tr>
  			<tr>
  				<td class="row-odd" width="28">
      				<label>Recognised Experience: </label>
      				
    			 </td>
    			  <td  class="row-even" width="22">
				 	 <select name="subjectArea">
				   		<option>01</option>
				   		<option>02</option>
						<option>03</option>
				   		<option>04</option>
						<option>05</option>
				   		<option>06</option>
				   		<option>07</option>
				   		<option>08</option>
						<option>09</option>
				   		<option>10</option>
						<option>11</option>
				   		<option>12</option>
			    	 </select>&nbsp;&nbsp;&nbsp;
				 	 <select name="subjectArea">
				   		<option>01</option>
				   		<option>02</option>
						<option>03</option>
				   		<option>04</option>
						<option>05</option>
				   		<option>06</option>
			    	 </select>
				 </td>
  			</tr>
            </table>
		 </td>
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
</table>

<table width="100%" cellspacing="1" cellpadding="2">
  <tr>
    <td colspan="2" class="heading" align="left">Education details </td>
  </tr>
  <tr>
    <td colspan="3" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
		<td class="row-odd">
			<label> Qualification Level </label>		</td>
		<td class="row-odd">
			<label> Course </label>		</td>
		<td class="row-odd">
			<label> Specialization </label>		</td>
		<td class="row-odd">
			<label> Year of completion </label>		</td>
		<td class="row-odd">
			<label> Grade % </label>		</td>
		<td class="row-odd">
			<label>  Institute/University </label>		</td>
	</tr>
	<tr>
		<td class="row-odd">
			<label> 10th </label>		</td>
		<td class="row-even">
			<input name="course" type="text" />		</td>
		<td class="row-even">
			<input name="specialization" type="text" />		</td>
		<td class="row-even">
			<select name="yearOfCompletions">
	   		<option>2000 </option>
	   		<option>2001</option>
			<option>2002 </option>
	   		<option>2003</option>
			<option>2004</option>
    	 </select>		</td>
		<td class="row-even">
			<input name="grade" type="text" size="8"/>		</td>
		<td class="row-even">
			<input name="institute" type="text" />		</td>
	</tr>
	<tr>
		<td class="row-odd">
			<label> 12th/Equivalent </label>		</td>
		<td class="row-even">
			<input name="course" type="text" />		</td>
		<td class="row-even">
			<input name="specialization" type="text" />		</td>
		<td class="row-even">
			<select name="yearOfCompletions">
	   		<option>2000 </option>
	   		<option>2001</option>
			<option>2002 </option>
	   		<option>2003</option>
			<option>2004</option>
    	 </select>		</td>
		<td class="row-even">
			<input name="grade" type="text" size="8"/>		</td>
		<td class="row-even">
			<input name="institute" type="text" />		</td>
	</tr>
	<tr>
		<td class="row-odd">
			<label> Graduation* </label>		</td>
		<td class="row-even">
			<input name="course" type="text" />		</td>
		<td class="row-even">
			<input name="specialization" type="text" />		</td>
		<td class="row-even">
			<select name="yearOfCompletions">
	   		<option>2000 </option>
	   		<option>2001</option>
			<option>2002 </option>
	   		<option>2003</option>
			<option>2004</option>
    	 </select>		</td>
		<td class="row-even">
			<input name="grade" type="text" size="8"/>		</td>
		<td class="row-even">
			<input name="institute" type="text" />		</td>
	</tr>
	<tr>
		<td class="row-odd">
			<label> Post Graduation* </label>		</td>
		<td class="row-even">
			<input name="course" type="text" />		</td>
		<td class="row-even">
			<input name="specialization" type="text" />		</td>
		<td class="row-even">
			<select name="yearOfCompletions">
	   		<option>2000 </option>
	   		<option>2001</option>
			<option>2002 </option>
	   		<option>2003</option>
			<option>2004</option>
    	 </select>		</td>
		<td class="row-even">
			<input name="grade" type="text" size="8"/>		</td>
		<td class="row-even">
			<input name="institute" type="text" />		</td>
		<td align="center" class="educationAddMoreRows">  
			<input name="" type="button" value="click to add more rows" />		</td>
	</tr>
	<tr class="educationshow">
		<td class="row-odd">		
			<select name="educationList">
				<option>select </option>
				<option>Certificate </option>
				<option>Diploma</option>
				<option>Degree/PG </option>
				<option>Prof.Degree</option>
				<option>Mphil </option>
				<option>Diploma</option>
    	 	</select>
		</td>
		<td class="row-even">
			<input name="course" type="text" />		</td>
		<td class="row-even">
			<input name="specialization" type="text" />		</td>
		<td class="row-even">
			<select name="yearOfCompletions">
	   		<option>2000 </option>
	   		<option>2001</option>
			<option>2002 </option>
	   		<option>2003</option>
			<option>2004</option>
    	 </select>		</td>
		<td class="row-even">
			<input name="grade" type="text" size="8"/>		</td>
		<td class="row-even">
			<input name="institute" type="text" />		</td>
			<td class="educationremove" align="center">
			<input  type="button" value="Remove"/>		</td>
	</tr>
	<tr>
	<td class="row-odd">
	<label>Highest Qualification(for staff Album):</label>
	</td>
	<td class="row-even" colspan="5">
	<input type="text" name="highestQuali">
	</td>
	</tr>
	 <tr>
  		<td class="row-odd"> 
			<label>No of Publications Refereed:</label>  		</td>
		<td class="row-even">
			<select name="refereed">
			<option>1</option>
			<option>2</option>
			<option>3</option>
			<option>4</option>
			<option>5</option>
		    <option>6</option>
			<option>7</option>
			<option>8</option>
			<option>9</option>
			<option>10</option>
			<option>11</option>
			<option>12</option>
			<option>13</option>
			<option>14</option>
		    <option>15</option>
			<option>16</option>
			<option>17</option>
			<option>18</option>
			<option>19</option>
			<option>20</option>
			<option>more than 20</option>
			</select>	</td>
		<td class="row-odd">
			<label>Non-refereed:</label>		</td>
		<td class="row-even">
			<select>
			<option>1</option>
			<option>2</option>
			<option>3</option>
			<option>4</option>
			<option>5</option>
		    <option>6</option>
			<option>7</option>
			<option>8</option>
			<option>9</option>
			<option>10</option>
			<option>more than 20</option>
			</select>
	</td>
		<td class="row-odd">
			<label>Books:</label>		</td>
		<td class="row-even">
			<select>
			<option>1</option>
			<option>2</option>
			<option>3</option>
			<option>4</option>
			<option>5</option>
		    <option>6</option>
			<option>7</option>
			<option>8</option>
			<option>9</option>
			<option>10</option>
			<option>more than 20</option>
			</select>		</td>
	</tr>
</table>
<table width="100%"  cellspacing="1" cellpadding="2">
	<tr>
		<td class="row-odd">
			<label>Any other Information:</label>
		</td> 
		<td>
			<textarea name="otherInformation" cols="50" rows="5"></textarea>
		</td>
  </tr>
</table>
</td>
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
</table>

<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left"><p>Approved Achievements Details</p></td>
	</tr>
	<tr>
  
      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
            <tr>
											<td width="33%" height="20" class="row-odd">Name of the Achievements</td>
											<td width="33%" class="row-odd">Description </td>
				 <td class="row-odd" align="center"><input name="Input" type="button" value="click to add more rows" class="ApprovedAchievements"/>
											</td>
											
			  </tr>
              <tr>
											<td width="33%" height="20" class="row-even">Gold Medal</td>
											<td width="33%" class="row-even">First Rank</td>
											<td width="33%" class="row-even"></td>
			  </tr>
			   <tr>
											<td width="33%" height="20" class="row-even">Silver medal</td>
											<td width="33%" class="row-even">Second Rank</td>
											<td width="33%" class="row-even"></td>
			  </tr>
			  
			   <tr class="removeApproved">
				 <td width="33%" height="20" class="row-even"></td>
				<td width="33%" class="row-even"></td>
				<td class="row-even" align="center">
				<input name="Input" type="button" value="Remove" />
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
</table>





<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left"> Financial Assistance
</td>
	</tr>
	<tr>
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
             <tr class="row-odd">
                <td  class="row-odd"><div align="center" >Date: </div></td>
                <td class="row-odd"><div align="center" > Amount:</div></td>
                <td  class="row-odd"><div align="center" >Details:</div></td>
                <td class="row-odd" align="center"> <input name="Input" type="button" value="click to add more rows" class="Financial"/></td>
				</tr>
				<tr>
				 <td  class="row-even"><span class="star">
                  <input type="text" name="date" /></span></td>
				 <td  class="row-even" ><input type="text" name="amount" /></td>
				 <td   class="row-even">
				<textarea name="description" cols="15" rows="1"></textarea> 
				</td>
				<td class="row-even" align="center">
				</td>
                
              </tr>
              <tr class="removeFinancial">
				 <td  class="row-even"><span class="star">
                  <input type="text" name="date" /></span></td>
				 <td   class="row-even" ><input type="text" name="amount" /></td>
				 <td  width="30" class="row-even" >
				<textarea name="description" cols="15" rows="1"></textarea> 
				</td>
				<td class="row-even" align="center">
				<input name="Input" type="button" value="Remove" />
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
</table>


<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left"> Loan Details </td>
	</tr>
	<tr>
  
      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
             <tr class="row-odd">
                <td  class="row-odd"><div align="center" >Date: </div></td>
                <td class="row-odd"><div align="center" > Amount:</div></td>
                <td  class="row-odd"><div align="center" >Details:</div></td>
                <td class="row-odd" align="center"> <input name="Input" type="button" value="click to add more rows" class="LoanDetails"/></td>
				</tr>
				<tr>
				 <td  class="row-even"><span class="star">
                  <input type="text" name="date" /></span></td>
				 <td  class="row-even" ><input type="text" name="amount" /></td>
				 <td   class="row-even">
				<textarea name="description" cols="15" rows="1"></textarea> 
				</td>
				<td class="row-even" align="center">
				</td>
                
              </tr>
              <tr class="removeLoan">
				 <td  class="row-even"><span class="star">
                  <input type="text" name="date" /></span></td>
				 <td   class="row-even" ><input type="text" name="amount" /></td>
				 <td  width="30" class="row-even" >
				<textarea name="description" cols="15" rows="1"></textarea> 
				</td>
				<td class="row-even" align="center">
				<input name="Input" type="button" value="Remove" />
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
</table>

<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left">Fee Concession </td>
	</tr>
	<tr>
  
      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr >
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr class="row-odd">
                <td  class="row-odd"><div align="center" >Date: </div></td>
                <td class="row-odd"><div align="center" > Amount:</div></td>
                <td  class="row-odd"><div align="center" >Details:</div></td>
                <td class="row-odd" align="center"> <input name="Input" type="button" value="click to add more rows" class="FeeConcession"/></td>
				</tr>
				<tr>
				 <td  class="row-even"><span class="star">
                  <input type="text" name="date" /></span></td>
				 <td  class="row-even" ><input type="text" name="amount" /></td>
				 <td   class="row-even">
				<textarea name="description" cols="15" rows="1"></textarea> 
				</td>
				<td class="row-even" align="center">
				</td>
                
              </tr>
              <tr class="removeFee">
				 <td  class="row-even"><span class="star">
                  <input type="text" name="date" /></span></td>
				 <td   class="row-even" ><input type="text" name="amount" /></td>
				 <td  width="30" class="row-even" >
				<textarea name="description" cols="15" rows="1"></textarea> 
				</td>
				<td class="row-even" align="center">
				<input name="Input" type="button" value="Remove" />
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
</table>

<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left">Incentives</td>
	</tr>
	<tr>
 
      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr class="row-odd">
                <td  class="row-odd"><div align="center" >Date: </div></td>
                <td class="row-odd"><div align="center" > Amount:</div></td>
                <td  class="row-odd"><div align="center" >Details:</div></td>
                <td class="row-odd" align="center"> <input name="Input" type="button" value="click to add more rows" class="Incentives"/></td>
				</tr>
				<tr>
				 <td  class="row-even"><span class="star">
                  <input type="text" name="date" /></span></td>
				 <td  class="row-even" ><input type="text" name="amount" /></td>
				 <td   class="row-even">
				<textarea name="description" cols="15" rows="1"></textarea> 
				</td>
				<td class="row-even" align="center">
				</td>
                
              </tr>
              <tr class="removeIncentives">
				 <td  class="row-even"><span class="star">
                  <input type="text" name="date" /></span></td>
				 <td   class="row-even" ><input type="text" name="amount" /></td>
				 <td  width="30" class="row-even" >
				<textarea name="description" cols="15" rows="1"></textarea> 
				</td>
				<td class="row-even" align="center">
				<input name="Input" type="button" value="Remove" />
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
</table>

<table width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td colspan="2" class="heading" align="left"> Remarks
</td>
	</tr>
	<tr>
  
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr class="row-odd">
                <td  class="row-odd"><div align="center" >Date: </div></td>
                <td class="row-odd"><div align="center" > Remarks:</div></td>
                <td  class="row-odd"><div align="center" >Entered By:</div></td>
                <td class="row-odd" align="center"> <input name="Input" type="button" value="click to add more rows" class="remark"/></td>
				</tr>
				<tr>
				 <td  class="row-even"><span class="star">
                  <input type="text" name="date" /></span></td>
				 <td  class="row-even" ><textarea name="description" cols="15" rows="1"></textarea></td>
				 <td   class="row-even">
				<input type="text" name="amount" />
				</td>
				<td class="row-even" align="center">
				</td>
                
              </tr>
              <tr class="removeRemark">
				 <td  class="row-even"><span class="star">
                  <input type="text" name="date" /></span></td>
				 <td   class="row-even" ><textarea name="description" cols="15" rows="1"></textarea></td>
				 <td  width="30" class="row-even" >
				<input type="text" name="amount" />
				</td>
				<td class="row-even" align="center">
				<input name="Input" type="button" value="Remove" />
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
</table>
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" height="29" valign="top">
							<table width="100%"  cellspacing="1" cellpadding="2">
							<tr>
							
							<td align="right"> 
							<html:button property="" styleId="print" styleClass="formbutton" value="Submit"></html:button>
							</td>
							<td align="left">
							 <html:button property="" styleClass="formbutton" value="Reset"></html:button>
							&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Cancel" onclick="closeWindow()"></html:button>
							</td>
							<td align="left">
							
							</td>
							</tr>
							</table> 								
							</td>
							<td background="images/right.gif" width="5" height="29"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>	



</td>
</tr>
<tr>
<td height="10" colspan="6" class="body" ></td>
</tr>
</table>
</div></td>
<td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
</tr>
<tr>
<td><img src="images/Tright_03_05.gif" width="9" height="29"/></td>
<td width="100%" background="images/TcenterD.gif"></td>
<td><img src="images/Tright_02.gif" width="9" height="29"/></td>
</tr>
</table>
</td>
</tr>
</table>
</body>
</html>	
