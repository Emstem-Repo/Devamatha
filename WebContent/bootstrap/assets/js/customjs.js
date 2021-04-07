//Student attendance wise
function display_attendance(id) {
    id=id-10;
    var x=document.getElementById(id);

    if (x.style.visibility === "visible") {
        x.style.visibility = "collapse";
    } else {
        x.style.visibility = "visible";
    }
}
function cancelAction() {
    document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
function winOpen(attendanceID, attendanceTypeID, subjectId, studentId, classesAbsent,attendanceTypeName) {
    var url = "studentWiseAttendanceSummary.do?method=getAbsencePeriodDetails&attendanceID="
        + attendanceID
        + "&attendanceTypeId="
        + attendanceTypeID
        + "&subjectId="
        + subjectId
        + "&studentID="
        + studentId
        + "&classesAbsent="
        + classesAbsent
        + "&attendanceTypeName="
        + attendanceTypeName
    ;
    myRef = window
        .open(url,"StudentAbsencePeriodDetails",
            "left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
}

function activityOpens(activityId, studentId, classesAbsent,attendanceTypeName) {
    var url = "studentWiseAttendanceSummary.do?method=getActivityAbsencePeriodDetails&activityId="
        + activityId
        + "&studentID="
        + studentId
        + "&classesAbsent="
        + classesAbsent
        +"&attendanceTypeName="
        + attendanceTypeName;
    myRef = window
        .open(url,"StudentAbsencePeriodDetails",
            "left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
}
//Student attendance wise  end
//Student Absent start
function print() {
    var url = "studentWiseAttendanceSummary.do?method=printStudentAbsenceDetails";
    myRef = window.open(url, "", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
//togle for prev-class -at
function prev_at_drop_disp() {
    var x=document.getElementById("prev-at-drop");

    if (x.style.visibility === "visible") {
        x.style.visibility = "collapse";
    } else {
        x.style.visibility = "visible";
    }
}
function set_prev_at_classes(val){
    document.getElementById("classesId").value=val;
    document.getElementById("form_id").submit();

}
/*st-leave application start*/
function disp_lev_reasons(val){
    var y=val+'d';
    var x=document.getElementById(y);
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}
function disp_lev_reasons_sm(val){
    var a=val+'sd';
    var b=document.getElementById(a);
    if (b.style.display === "none") {
        b.style.display = "contents";
    } else {
        b.style.display = "none";
    }
}
function set_leave_reason(val,label,id) {
    var pname=id+'sp';
    var pnamesm=id+'spsm';
    var formcheck=id+'frmcheck';
    var formact=id+'frmact';
    document.getElementById(pname).textContent=label;
    document.getElementById(pnamesm).textContent=label;
    document.getElementById(id).style.backgroundColor="#1B4569";
    document.getElementById(formcheck).value="true";
    document.getElementById(formact).value=val;

}
/*marks card*/
function change_rad_val_reg(value){
    document.getElementById("sem-selector").textContent = "Regular Exam List";
    document.getElementById("st-mrk-card-reg").style.visibility = "collapse";
    document.getElementById("st-mrk-card-sup").style.visibility = "collapse";
    document.getElementById("ex-rad").value=value;
}
function change_rad_val_sup(value){
    document.getElementById("sem-selector").textContent = "Supplementary Exam List";
    document.getElementById("st-mrk-card-reg").style.visibility = "collapse";
    document.getElementById("st-mrk-card-sup").style.visibility = "collapse";
    document.getElementById("ex-rad").value=value;
}
function disp_hd_selector() {
    var SelectedRadio=document.getElementById("ex-rad").value;
    if(SelectedRadio==="Supplementary")
    {
        var y=document.getElementById("st-mrk-card-sup");
        document.getElementById("st-mrk-card-reg").style.visibility = "collapse";
        if (y.style.visibility === "visible") {
            document.getElementById("st-mrk-card-sup").style.visibility = "collapse";
        }
        else {
            document.getElementById("st-mrk-card-sup").style.visibility = "visible";
            document.getElementById("sem-selector").textContent = "Supplimentary Exam List";
        }
    }else
    {
        var x=document.getElementById("st-mrk-card-reg");
        document.getElementById("st-mrk-card-sup").style.visibility = "collapse";
        if (x.style.visibility === "visible") {
            document.getElementById("st-mrk-card-reg").style.visibility = "collapse";
        }
        else {
            document.getElementById("st-mrk-card-reg").style.visibility = "visible";
        }

    }

}
function set_reg_sem_id(val) {
    document.getElementById("regularExamId").value=val;
    console.log( document.getElementById("regularExamId").value)
}
function set_sup_sem_id(val) {
    document.getElementById("suppExamId").value=val;
}
function getMarkscard() {
    var regular = null;
    var supp = null;
    regular = document.getElementById("regularExamId").value;
    supp = document.getElementById("suppExamId").value;
    if (supp != null && supp != "") {
        document.getElementById("method").value = "SupplementaryMarksCard";
        document.loginform.submit();
    } else if (regular != null && regular != "") {
        document.getElementById("method").value = "MarksCardDisplay";
        document.loginform.submit();

    }
}
/*Admission forms*/
/*login*/

$(document).ready(function(){
    /*date star*/
    function dateTo(date_from, date_to) {
        $(date_to).datepicker({
            dateFormat: "mm/dd/yy",
            prevText: '<i class="fa fa-caret-left"></i>',
            nextText: '<i class="fa fa-caret-right"></i>',
            onClose: function(selectedDate) {
                $(date_from).datepicker("option", "maxDate", selectedDate);
            }
        });
    }

// Destroy date
    function destroyDate(date) {
        $(date).datepicker("destroy");
    }

// Initialize date range
    dateFrom("#date_from", "#date_to");
    dateTo("#date_from", "#date_to");
    /*date end*/



    $('#login_validate').click(function(){
        var uname = $("#udob").val();
        var pwd = $("#uniqueId").val();
        if (uname === "" || pwd === ""){
            console.log('poda patty');
            document.getElementById("frm-border").style.borderColor="red";
            document.getElementById("frm-border").style.opacity = "0.7";
            return false;
        }
        else {
            var dateArray=uname.split('-');
            var month=dateArray[1];
            var day=dateArray[2];
            var year=dateArray[0];
            var dob=day+'/'+month+'/'+year;
            $('#uname').val(dob);
            $('#method').val("loginOnlineApplication");
        }
    });
    $('#frm-border').click(function(){
        document.getElementById("frm-border").style.opacity = "1";
    });
    $("#udob").blur(function(){
        var uname = $("#udob").val();
        if (uname === "") {
            $('#udob').css("borderColor","red");
        }
        else {
            $('#udob').css("borderColor","white");
        }
    });
    $("#uniqueId").blur(function(){
        var pwd = $("#uniqueId").val();
        if (pwd === "") {
            $('#uniqueId').css("borderColor","red");
        }else {
            $('#uniqueId').css("borderColor","white");
        }
    });
});
/*register link*/
function register(){
    var flag= document.getElementById("offlinePage").value;
    if(flag=='true'){
        document.getElementById("method").value="initOfflineApplicationRegistration";
        document.uniqueIdRegistrationForm.submit();
    }else{
        document.getElementById("method").value="initOnlineApplicationRegistration";
        document.uniqueIdRegistrationForm.submit();
    }
}
/*forgot pwd link*/
function forgotUniqueId(){
    document.getElementById("method").value="initOnlineApplicationForgotUniqueId";
    document.uniqueIdRegistrationForm.submit();
}
/*forgot password*/
$(document).ready(function(){
    $("#email").blur(function(){
        var mail = $("#email").val();
        if (mail     === "") {
            $('#uniqueId').css("borderColor","red");
        }else {
            $('#uniqueId').css("borderColor","white");
        }
    });
});
