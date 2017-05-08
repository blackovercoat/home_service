var providerList;
var worksheetName;
var tableName;
$(function () {
    var providerId = document.getElementById('provider').value;
    var month = document.getElementById('month').value;
    getReport(providerId,month);
});
function sort() {
    var table, rows, switching, i, x, y, shouldSwitch;
    table = document.getElementById("reportTable");
    switching = true;
    while (switching) {
        switching = false;
        rows = table.getElementsByTagName("TR");
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("TD")[0];
            y = rows[i + 1].getElementsByTagName("TD")[0];
            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                shouldSwitch= true;
                break;
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
}

$(function () {
    $.ajax({
        url: "provider_list",
        async: true,
        type: "GET",
        dataType: "json",
        success: function (result, status, xhr) {
            providerList = result;
        },
        error: function (xhr, status, error) {
        }
    });
});

function getReport(providerId,month) {
    $.ajax({
        url: "load_report",
        async: true,
        type: "GET",
        dataType: "json",
        data: {
            providerId: providerId,
            month: month
        },
        success: function (result, status, xhr) {
            reportStatistic = result;
            loadTableReport(reportStatistic);
        },
        error: function (xhr, status, error) {
        }
    });
}

function loadTableReport(reportStatistic) {

    worksheetName =[];
    tableName = [];
    var monthName = document.getElementById('month').options[document.getElementById('month').selectedIndex].text;
    var providerName = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].text;
    var providerId = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].value;
    if(monthName==='All')
        monthName='Total';
    var total = 0;
    $("#report_form div").remove();
    if(providerId==0){
        document.getElementById("report_title").textContent = 'Report for '+providerName+' Services in '+ monthName;
        $.each(providerList, function (key, value) {
            total = 0;
            worksheetName.push(value.name);
            tableName.push("reportTable"+value.id);
            $('#report_form').append(
                '<div class="table-responsive">'+
                '<label class="text-primary" >'+value.name+'\'s Services</label>'+
                '<table id="reportTable'+value.id+'" class="table table-striped table-hover">'+
                '<thead>' +
                '<tr>' +
                '<th>Service</th>' +
                '<th>Booking Times</th>' +
                '<th>Price</th>' +
                '<th>Total</th>' +
                '</tr>' +
                '</thead>' +
                '<tbody>'
            );
            $.each(reportStatistic, function (key, valueReport) {
                if(value.id==valueReport.providerId){
                    $('#reportTable'+value.id+' tbody').append(
                        '<tr>' +
                        '<td >' + valueReport.serviceName + '</td>' +
                        '<td>' + valueReport.bookingTimes + '</td>' +
                        '<td>' + valueReport.price + '</td>' +
                        '<td>' + valueReport.price * valueReport.bookingTimes + '</td>' +
                        '</tr>'
                    );
                    total = total + valueReport.price * valueReport.bookingTimes;
                }
            });
            $('#report_form').append(
                '</tbody>' +
                '</table>' +
                '</div>' +
                '<div>' +
                '<a style="margin: 20px;margin-left: 30px;color: inherit;">Total price: </a>' +
                '<a id="total'+value.id+'" th:text="${defaultTotal}" style="text-decoration: none;color: inherit;"></a>' +
                '</div>' +
                '</div>'
            );
            document.getElementById("total"+value.id).textContent = total+' $';
        });
    }
    else{
        document.getElementById("report_title").textContent = 'Report for '+providerName+'\'s Services in '+ monthName;
        worksheetName.push("Report");
        tableName.push("reportTable");
        $('#report_form').append(
            '<div class="table-responsive">'+
            '<table id="reportTable" class="table table-striped table-hover">'+
            '<thead>' +
            '<tr>' +
            '<th>Service</th>' +
            '<th>Booking Times</th>' +
            '<th>Price</th>' +
            '<th>Total</th>' +
            '</tr>' +
            '</thead>' +
            '<tbody>'
        );
        $('#report_form').append('<tbody>');
        $.each(reportStatistic, function (key, value) {
            $('#reportTable tbody').append(
                '<tr>' +
                '<td >'+value.serviceName+'</td>' +
                '<td>'+value.bookingTimes+'</td>' +
                '<td>'+value.price+'</td>' +
                '<td>'+value.price*value.bookingTimes+'</td>' +
                '</tr>');
            total = total + value.price*value.bookingTimes;
        });
        $('#report_form').append(
            '</tbody>' +
            '</table>' +
            '</div>' +
            '<div>' +
            '<a style="margin: 20px;margin-left: 30px;color: inherit;">Total price: </a>' +
            '<a id="total" th:text="${defaultTotal}" style="text-decoration: none;color: inherit;"></a>' +
            '</div>' +
            '</div>'
        );
        document.getElementById("total").textContent = total+' $';
    }
}

$('#btnPng').click(function () {
    var monthName = document.getElementById('month').options[document.getElementById('month').selectedIndex].text;
    var providerName = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].text;
    var providerId = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].value;
    var year = new Date().getFullYear();
    if(monthName==='All')
        monthName='Total';
    var filename;
    if(providerId==0)
        filename = 'Report_for_'+providerName+'_Services_in_'+ monthName+'_'+year;
    else
        filename = 'Report_for_'+providerName+'\'s_Services_in_'+ monthName+'_'+year;
    html2canvas($("#content"), {
        onrendered: function(canvas) {
            var url = canvas.toDataURL();
            $("<a>", {
                href: url,
                download: filename
            })
                .on("click", function() {$(this).remove()})
                .appendTo("body")[0].click()
            //
            document.body.removeChild(canvas);
        }
    });
});

function exportToExcel() {
    var monthName = document.getElementById('month').options[document.getElementById('month').selectedIndex].text;
    var providerName = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].text;
    var providerId = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].value;
    var year = new Date().getFullYear();
    var filename;
    if(monthName==='All')
        monthName='Total';
    if(providerId==0)
        filename = 'Report_for_All_Services_in_'+ monthName+'_'+year+'.xls';
    else{
        filename = 'Report_for_'+providerName+'\'s_Services_in_'+ monthName+'_'+year+'.xls';
    }
    tablesToExcel(tableName,worksheetName,filename,'Excel');
}

var tablesToExcel = (function() {
        var uri = 'data:application/vnd.ms-excel;base64,'
            , tmplWorkbookXML = '<?xml version="1.0"?><?mso-application progid="Excel.Sheet"?><Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet" xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet">'
            + '<DocumentProperties xmlns="urn:schemas-microsoft-com:office:office"><Author>Axel Richter</Author><Created>{created}</Created></DocumentProperties>'
            + '<Styles>'
            + '<Style ss:ID="Currency"><NumberFormat ss:Format="Currency"></NumberFormat></Style>'
            + '<Style ss:ID="Date"><NumberFormat ss:Format="Medium Date"></NumberFormat></Style>'
            + '</Styles>'
            + '{worksheets}</Workbook>'

            , tmplWorksheetXML = '<Worksheet ss:Name="{nameWS}"><Table>{rows}</Table></Worksheet>'
            , tmplCellXML = '<Cell{attributeStyleID}{attributeFormula}><Data ss:Type="{nameType}">{data}</Data></Cell>'
            , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
            , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
        return function(tables, wsnames, wbname, appname) {
            var ctx = "";
            var workbookXML = "";
            var worksheetsXML = "";
            var rowsXML = "";

            for (var i = 0; i < tables.length; i++) {
                if (!tables[i].nodeType) tables[i] = document.getElementById(tables[i]);
                for (var j = 0; j < tables[i].rows.length; j++) {
                    rowsXML += '<Row>'
                    for (var k = 0; k < tables[i].rows[j].cells.length; k++) {
                        var dataType = tables[i].rows[j].cells[k].getAttribute("data-type");
                        var dataStyle = tables[i].rows[j].cells[k].getAttribute("data-style");
                        var dataValue = tables[i].rows[j].cells[k].getAttribute("data-value");
                        dataValue = (dataValue)?dataValue:tables[i].rows[j].cells[k].innerHTML;
                        var dataFormula = tables[i].rows[j].cells[k].getAttribute("data-formula");
                        dataFormula = (dataFormula)?dataFormula:(appname=='Calc' && dataType=='DateTime')?dataValue:null;
                        ctx = {  attributeStyleID: (dataStyle=='Currency' || dataStyle=='Date')?' ss:StyleID="'+dataStyle+'"':''
                            , nameType: (dataType=='Number' || dataType=='DateTime' || dataType=='Boolean' || dataType=='Error')?dataType:'String'
                            , data: (dataFormula)?'':dataValue
                            , attributeFormula: (dataFormula)?' ss:Formula="'+dataFormula+'"':''
                        };
                        rowsXML += format(tmplCellXML, ctx);
                    }
                    rowsXML += '</Row>'
                }
                ctx = {rows: rowsXML, nameWS: wsnames[i] || 'Sheet' + i};
                worksheetsXML += format(tmplWorksheetXML, ctx);
                rowsXML = "";
            }

            ctx = {created: (new Date()).getTime(), worksheets: worksheetsXML};
            workbookXML = format(tmplWorkbookXML, ctx);

            var link = document.createElement("A");
            link.href = uri + base64(workbookXML);
            link.download = wbname || 'Workbook.xls';
            link.target = '_blank';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
})();

var tableToExcel = (function() {
    var uri = 'data:application/vnd.ms-excel;base64,'
        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
        , base64 = function(s) { return window.btoa(unescape((encodeURIComponent(s)))) }
        , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p];      }) }
    return function(table, name) {
        var monthName = document.getElementById('month').options[document.getElementById('month').selectedIndex].text;
        var providerName = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].text;
        var providerId = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].value;
        var year = new Date().getFullYear();
        if(monthName==='All')
            monthName='Total';
        var ctx;
        for (var i = 0; i < table.length; i++) {
            if (!table[i].nodeType) table[i] = document.getElementById(table[i])
            ctx = ctx + {worksheet: name[i] || 'Worksheet', table: table[i].innerHTML}
        }
        var link = document.createElement("a");
        if(providerId==0)
            link.download = 'Report_for_All_Services_in_'+ monthName+'_'+year+'.xls';
        else
            link.download = 'Report_for_'+providerName+'\'s_Services_in_'+ monthName+'_'+year+'.xls';
        link.href = uri + base64(format(template, ctx));
        link.click();
    }
})();
