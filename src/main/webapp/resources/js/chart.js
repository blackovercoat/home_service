google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawDefaultChart);

function drawDefaultChart() {
    var providerId = document.getElementById('provider').value;
    var month = document.getElementById('month').value;
    getServiceStatistic(providerId);
    getServiceTime(providerId,month);
}

function getServiceTime(providerId,month) {
    $.ajax({
        url: "service_times",
        async: true,
        type: "GET",
        dataType: "json",
        data: {
            providerId: providerId,
            month: month
        },
        success: function (result, status, xhr) {
            serviceTime = result;
            drawServiceTimes(serviceTime);
        },
        error: function (xhr, status, error) {
        }
    });
}
function drawServiceTimes(serviceTime) {
    var monthName = document.getElementById('month').options[document.getElementById('month').selectedIndex].text;
    var providerName = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].text;
    var max = 0;
    var min = 0;
    if(monthName==='All')
        monthName='Total';
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Service');
    data.addColumn('number', 'Times');
    $.each(serviceTime, function (key, value) {
        data.addRows([
            [value.serviceName, value.bookingTimes]
        ]);
        max = max + value.maxPrice;
        min = min + value.minPrice;
    });
    document.getElementById("total").value = min+' $ - '+max +' $';
    var options = {title:providerName+'\''+'s Services'+' in '+monthName,
        width:500,
        height:300};
    var chart = new google.visualization.ColumnChart(document.getElementById('service_times_chart'));
    chart.draw(data, options);
}
function getServiceStatistic(providerId) {
    $.ajax({
        url: "service_statistic",
        async: true,
        type: "GET",
        dataType: "json",
        data: {
            providerId: providerId
        },
        success: function (result, status, xhr) {
            serviceStatistic = result;
            drawServiceStatistic(serviceStatistic);
        },
        error: function (xhr, status, error) {
        }
    });
}
function drawServiceStatistic(serviceStatistic) {
    var data = new google.visualization.DataTable();
    var providerName = document.getElementById('provider').options[document.getElementById('provider').selectedIndex].text;
    data.addColumn('string', 'Service');
    data.addColumn('number', 'Times');
    $.each(serviceStatistic, function (key, value) {
        data.addRows([
            [value.serviceName, value.bookingTimes]
        ]);
    });
    var options = {title:'Overview services of '+providerName,
        width:500,
        height:350};
    var chart = new google.visualization.PieChart(document.getElementById('service_statistic_chart'));
    chart.draw(data, options);
}
function getStatistic(providerId,month) {
    getServiceStatistic(providerId);
    getServiceTime(providerId,month);
}