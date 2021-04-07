<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
	
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<style>
body{
	font-size: 200.5%;
}
<!--

-->
</style>
<script type="text/javascript">
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}

function getHideSuppExam()
{
	document.getElementById("dataRegular").style.display="block";
	document.getElementById("dataRegularLabel").style.display="block";
	document.getElementById("dataSupp").style.display="none";
	document.getElementById("dataSuppLabel").style.display="none";
}
function getHideRegularExam()
{
	document.getElementById("dataRegular").style.display="none";
	document.getElementById("dataRegularLabel").style.display="none";
	 document.getElementById("dataSupp").style.display="block";
	 document.getElementById("dataSuppLabel").style.display="block";
}

function getMarkscard()
{
	var regular=null;
	var supp=null;
	regular=document.getElementById("regularExamId").value;
	supp=document.getElementById("suppExamId").value;
	if(supp !=null && supp!=""){
		document.getElementById("method").value="SupplementaryMarksCard";
		document.loginform.submit();
	}
	else if(regular !=null && regular!=""){
		document.getElementById("method").value="MarksCardDisplay";
		document.loginform.submit();
		
	}
}

</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/StudentLoginAction">
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method" value="" />
	 <div class="pageheader">
                        <div class="media">
                            <div class="Container">
                                <ul class="breadcrumb">
                                    <li><a href="#"><i class="glyphicon glyphicon-log-in"></i></a></li>
                                    <li><a href="#">Student Login</a></li>
                                    <li>Marks Card</li>
                                </ul>
                                
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->


<div class="panel-body">  
	<div class="panel panel-primary">
	<div class="panel-heading">
	<h1 class="panel-title">Marks Card</h1>
	</div>
	  </div>
                          <div class="col-sm-12"  style="border-style: solid;border-color: #29953b;">
					<div class="form-group">
	                <div class="col-sm-4">
							
								
	               	    				<!--<div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
	               	   						-->
	               	   						<!--<div id="errorMessage" style="color:red;font-family:arial;font-size:11px;">
	                       				-->
	                       				<div id="errorMessage">
	                       				<p>
										<span id="err"><html:errors/></span> 
										</p>
	                       					<FONT color="green">
											<html:messages id="msg" property="messages" message="true">
											<c:out value="${msg}" escapeXml="false"></c:out><br>
											</html:messages>
						 				 </FONT>
						  			</div>
						  			</div>
						</div>
						
							<div class="form-group">
							<div class="col-sm-12">
	                <div class="col-sm-4">
									<span class="Mandatory">*</span>Exam Type
									</div>
									 <div class="col-sm-2"><!--
                                      
    									  <input type="radio" property="examType" id="examType" class="rdio rdio-primary" value=" " name="loginform" onclick="getHideSuppExam()">Regular
    
									  -->
									  <input type="radio" id="examType" class="rdio rdio-primary" value=" " name="loginform" onclick="getHideSuppExam()">Regular
									 </div>
									 <div class="col-sm-4">
									 <input type="radio" id="examType" class="rdio rdio-primary" value=" " name="loginform" onclick="getHideRegularExam()">Supplementary
								</div>
								</div>
								</div>
									<div class="form-group">
									   <div class="col-sm-12">
	                                 <div class="col-sm-4">
									<div id="dataRegularLabel">
										<span class="Mandatory">*</span>Regular Exam List</div>
										</div>
									 <div class="col-sm-4">
									 <div id="dataRegular">
									 
										<html:select property="regularExamId" styleId="regularExamId" styleClass="btn btn-white dropdown-toggle">
											<html:option value="">Select</html:option>
												<logic:notEmpty property="regularExamList" name="loginform">
											  		<html:optionsCollection property="regularExamList" label="data" value="newExamId" name="loginform"/>
											   </logic:notEmpty>
										 </html:select>
										<!--  <button class="btn btn-primary dropdown-toggle" type="button" id="suppExamId" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                     Select
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                       <a class="dropdown-item" href="#">Select</a>
                      </div>-->
                    </div>
												</div>
							 	    </div>
							 	  </div>
								 
								 <div class="form-group">
								    <div class="col-sm-12">
	                <div class="col-sm-4">
									<div id="dataSuppLabel">
									 
										<span class="Mandatory">*</span>Supplementary Exam List
										</div>
										</div>
											<div id="dataSupp">
											 <div class="col-sm-4">
											 
											 
												<!-- <div class="dropdown">
                      <button class="btn btn-primary dropdown-toggle" type="button" id="suppExamId" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                     Select
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                       <a class="dropdown-item" href="#">Select</a>
                      </div>-->
                   
								 	<html:select property="suppExamId" styleId="suppExamId" styleClass="btn btn-white dropdown-toggle">
											<html:option value="">Select</html:option>
												<logic:notEmpty property="suppExamList" name="loginform" >
											  		<html:optionsCollection property="suppExamList" label="data" value="newExamId" name="loginform"/>
											   </logic:notEmpty>
										 </html:select>
										 </div>
												</div>
							 	    </div>
							 	    </div>
				 <div class="form-group">
							<div class="col-sm-10 text-center">
				         
                              <div class="btn btn-success "><span class="form-controler" onclick="getMarkscard()">Submit</span>
                               </div>
                              <div class="btn btn-danger "><span class="form-controler" onclick="goToHomePage()">Cancel</span>
                               </div>
							</div>
					</div>
		</div>
	
		</div>
		
	
</html:form>
<script>
var SelectedRadio=document.getElementById("examType").value;

if(SelectedRadio=="Regular")
{
document.getElementById("dataRegular").style.display="block";
document.getElementById("dataRegularLabel").style.display="block";
document.getElementById("dataSupp").style.display="none";
document.getElementById("dataSuppLabel").style.display="none";
}else
{
document.getElementById("dataRegular").style.display="none";
document.getElementById("dataRegularLabel").style.display="none";
 document.getElementById("dataSupp").style.display="block";
 document.getElementById("dataSuppLabel").style.display="block";
}
</script>