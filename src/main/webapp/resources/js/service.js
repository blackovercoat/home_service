var services;

function setTitle(service) {
    var name = document.getElementById('catalogId').options[document.getElementById('catalogId').selectedIndex].text
    document.getElementById("service-title").textContent = name;
    $("#serviceTable tbody tr").remove();

    $.each(service, function (key, value) {
        $('#serviceTable').append('<tr th:id="'+value.id+'">' +
            '<td >'+value.name+'</td><' +
            'td>'+value.description+'</td>' +
            '<td>' +
            '<a th:href="@{/catalog/edit/} + ${service.getId()}"  class="btn btn-md btn-primary">Edit</a>'+
            '<a href="#" class="btn btn-md btn-danger" onclick="onClickDeleteService('+value.id+',this)" >Delete</a>'+
            '</td>' +
            '</tr>');
    });
}

function getServices(catalogId) {
    $.ajax({
        url: "service_list",
        async: true,
        type: "GET",
        dataType: "json",
        data: {catalogId: catalogId},
        success: function (result, status, xhr) {
            services = result;
            setTitle(services);
        },
        error: function (xhr, status, error) {
        }
    });
}
$("a[id^='delete_']").click(function () {
    var btn = (this);
    var id = this.id + "";
    if (id != "") {
        var serviceId = id.substr("delete_".length);
        deleteServiceAsync(serviceId,btn);
    }
});

function onClickDeleteService(serviceId,btn) {
    deleteServiceAsync(serviceId,btn);
}
function showProcessingDialog() {
    return bootbox.dialog({
        message: '<div class="text-center"><i class="fa fa-spin fa-spinner"></i> Processing...</div>',
        onEscape: false,
        closeButton: false
    });
}

function deleteRow(serviceId)
{
    var row = document.getElementById(serviceId);
    row.parentNode.removeChild(row);
}

function deleteRowx(btn)
{
    var row = btn.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

function onProcessSuccess(serviceId,btn,dialog) {
    dialog.modal("hide");
    deleteRowx(btn);
    deleteRow(serviceId);
}

function showErrorMessage(message, dialog) {
    dialog.find(".bootbox-body").html('<div class="alert alert-warning"><strong>Unsuccessful!</strong> '.concat(message).concat('</div>'));
    var modelContent = dialog.find(".modal-content");
    modelContent.html(modelContent.html() + '<div class="modal-footer"><button data-bb-handler="ok" type="button" class="btn btn-primary">OK</button></div>');
}

function onProcessFail(status, dialog) {
    showErrorMessage(status, dialog);
}

function deleteServiceAsync(serviceId,btn) {
    var dialog = showProcessingDialog();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var data = {id: serviceId};
    $.ajax({
        url: "delete",
        async: true,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        success: function (result, status, xhr) {
            onProcessSuccess(serviceId,btn,dialog);
        },
        error: function (xhr, status, error) {
            onProcessFail(status, dialog);
        }
    });
}