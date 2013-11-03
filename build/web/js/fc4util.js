
var user_id = null;
var emp_id = null;
var rol_id = null;
var comboUserID = null;
var comboEmpID = null;
var comboRolID = null;

function sendRequest(URI, URLAsk,user,action)
    {

        var xmlhttp;
        if (window.XMLHttpRequest)
          {
            xmlhttp=new XMLHttpRequest();
          }
        else
          {
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
          }
        xmlhttp.onreadystatechange=function()
          {
          if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
                var request = xmlhttp.responseText;
                var parseRequest = JSON.parse(request);
                var error = parseRequest.error;
                var correct = parseRequest.correct;
                var user = parseRequest.user;
                var action = parseRequest.action;

                    if(error != null)
                     {
                        alert(error);
                     }
                 else if(correct != null)
                     {
                         if(action=="modify")
                         {
                             document.getElementById(user+"Modify").click();
                         }
                         else if(action=="delete")
                         {
                             document.getElementById(user+"Delete").click();
                         }
                         else if(action=="add")
                         {
                             window.location.href="addUser.form";
                         }
                         
                     }
            }
          }
        var send="AjaxRequestChildTasks=true&sModo=consulta&URI="+URLAsk+"&user="+user+"&action="+action;
        xmlhttp.open("POST",URI,true);
        xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xmlhttp.send(send);
    }
     
