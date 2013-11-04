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

function dates() {

var calendar = document.getElementById("calendar");

        var date = calendar.value.split("/");
        
        var year = parseInt(date[0]);
        var day = parseInt(date[1]);
        var month = parseInt(date[2]);

        if (isNaN(year) || isNan(day)|| isNan(month)){
            alert("Ici");
            document.getElementById('consult').disabled = true;
            document.getElementById('message').innerHTML = "Format incorrect";
        }
        else {
            
         document.getElementById('consult').disabled = false;
         document.getElementById('message').innerHTML = "";
        }
    }

     
