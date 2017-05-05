$(function () {
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
});
$(function () {
    var providerId = document.getElementById('provider').value;
    var month = document.getElementById('month').value;
    getReport(providerId,month);
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
    var monthName = document.getElementById('month').options[document.getElementById('month').selectedIndex].text;
    var providerName = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].text;
    if(monthName==='All')
        monthName='Total';
    document.getElementById("report_title").textContent = 'Report for '+providerName+'\'s Services in '+ monthName;
    $("#reportTable tbody tr").remove();
    var max = 0;
    var min = 0;
    $.each(reportStatistic, function (key, value) {
        $('#reportTable').append('<tr>' +
            '<td >'+value.serviceName+'</td>' +
            '<td>'+value.bookingTimes+'</td>' +
            '<td>'+value.minPrice*value.bookingTimes+'</td>' +
            '<td>'+value.maxPrice*value.bookingTimes+'</td>' +
            '</tr>');
        max = max + value.maxPrice*value.bookingTimes;
        min = min + value.minPrice*value.bookingTimes;
    });
    document.getElementById("total").textContent = min+' $ - '+max +' $';
}

$('#btnGenerate').click(function () {
    var monthName = document.getElementById('month').options[document.getElementById('month').selectedIndex].text;
    var providerName = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].text;
    var year = new Date().getFullYear();
    if(monthName==='All')
        monthName='Total';
    html2canvas($("#content"), {
        onrendered: function(canvas) {
            var url = canvas.toDataURL();
            $("<a>", {
                href: url,
                download: 'Report_for_'+providerName+'\'s_Services_in_'+ monthName+'_'+year
            })
                .on("click", function() {$(this).remove()})
                .appendTo("body")[0].click()
            // Clean up
            document.body.removeChild(canvas);
        }
    });
});
