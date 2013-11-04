<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="include.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file="htmlhead.jspf" %>
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Addresses</title>
        <style type="text/css" title="currentStyle">
            @import "css/reset.css";
            @import "css/Help/helpStyle.css";
            @import "css/login/popupStyle.css";
            @import "css/design.css";
            @import "css/datatable/demo_page.css";
            @import "css/datatable/demo_table.css";
            @import "css/datatable/TableTools.css";
	</style>
        <script type="text/javascript" language="javascript" src="js/jquery.js"></script>
        <script type="text/javascript" language="javascript" src="js/jquery.dataTables.js"></script>

        <script type="text/javascript" charset="utf-8">
var asInitVals = new Array();
        $(document).ready(function()
        {
            $('#example').dataTable({
            "sPaginationType": "full_numbers",
            "bServerSide": true,
            "sAjaxSource": "${data.URL}",
            "BJQueryUI": true,
             "aoColumns": [
                            ${data.javascriptColumnDefinition}
                            ],
            "oLanguage": {
            "sSearch": "Rechercher dans toutes les colonnes:"
        }
} );
	$("tfoot input").keyup( function () {
		/* Filter on the column (the index) of this element */
		oTable.fnFilter( this.value, $("tfoot input").index(this) );
	} );
	/*
	 * Support functions to provide a little bit of 'user friendlyness' to the textboxes in
	 * the footer
	 */
	$("tfoot input").each( function (i) {
		asInitVals[i] = this.value;
	} );

	$("tfoot input").focus( function () {
		if ( this.className == "search_init" )
		{
			this.className = "";
			this.value = "";
		}
	} );

	$("tfoot input").blur( function (i) {
		if ( this.value == "" )
		{
			this.className = "search_init";
			this.value = asInitVals[$("tfoot input").index(this)];
		}
            });
} );
        </script>
          <script type="text/javascript" charset="utf-8">

        var oTable;
var giRedraw = false;

$(document).ready(function() {
	/* Add a click handler to the rows - this could be used as a callback */
	$("#example tbody").click(function(event) {
		$(oTable.fnSettings().aoData).each(function (){
			$(this.nTr).removeClass('row_selected');
		});
		$(event.target.parentNode).addClass('row_selected');
	});
	/* Add a click handler for the delete row */
	$('#delete').click( function() {
		var anSelected = fnGetSelected( oTable );
		oTable.fnDeleteRow( anSelected[0] );
	} );
	/* Init the table */
	oTable = $('#example').dataTable( );
} );
/* Get the rows which are currently selected */
function fnGetSelected( oTableLocal )
{
	var aReturn = new Array();
	var aTrs = oTableLocal.fnGetNodes();

	for ( var i=0 ; i<aTrs.length ; i++ )
	{
		if ( $(aTrs[i]).hasClass('row_selected') )
		{
			aReturn.push( aTrs[i] );
		}
	}
	return aReturn;
}
</script>
    </head>
    <body id="dt_example">
        <%@ include file="header.jspf" %>
        <div id="contai">
        <div id="demo">
            <a href="addAddress.form"><img alt="Ajouter une addresse" title="Ajouter une addresse"src="images/button_add_address.png"style="border: 0;"/></a></div>
        <c:if test="${not empty data}">
            <table class="display" cellspacing="0" cellpadding="0" border="0" class="display" id="example" width="100%">
             <thead>
                <tr>
                    <c:forEach var="columnNames" items="${data.columnNames}">
                        <th>${columnNames}</th>
                    </c:forEach>
                    <c:forEach var="columnExtras" items="${data.columnExtras}">
                        <th>${columnExtras}</th>
                    </c:forEach>
                </tr>
             </thead>
            <tbody>
                <c:forEach var="rows" items="${data.data}">
                    <tr>
                    <c:forEach var="cols" items="${rows}">
                        <td>${cols}</td>
                    </c:forEach>
                </tr>
                </c:forEach>
            </tbody>

             <tfoot>
                <tr>
                    <c:forEach var="search" items="${data.javascriptSearchDefinition}">
                        <th>${search}</th>
                    </c:forEach>
                </tr>
            </tfoot>
            </table>
        </c:if>

        </div>
       
<script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral2.jpg)"; document.body.style.backgroundRepeat = "repeat-x";document.body.backgroundPosition = "top";document.body.style.top = "50px";document.body.style.opacity = "1";}</script>
<script type="text/javascript" language="javascript" src="js/ExportDocuments/ExportDocuments.js"></script>
    </body>
</html>
