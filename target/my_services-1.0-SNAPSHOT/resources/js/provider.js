function showProcessingDialog() {
    return bootbox.dialog({
        message: '<div class="text-center"><i class="fa fa-spin fa-spinner"></i> Processing...</div>',
        onEscape: false,
        closeButton: false
    });
}

function deleteRow(rowid)
{
    var row = document.getElementById(rowid);
    row.parentNode.removeChild(row);
}

function onProcessSuccess(providerId,dialog) {
    dialog.modal("hide");
    deleteRow(providerId);
}

function showErrorMessage(message, dialog) {
    dialog.find(".bootbox-body").html('<div class="alert alert-warning"><strong>Unsuccessful!</strong> '.concat(message).concat('</div>'));
    var modelContent = dialog.find(".modal-content");
    modelContent.html(modelContent.html() + '<div class="modal-footer"><button data-bb-handler="ok" type="button" class="btn btn-primary">OK</button></div>');
}

function onProcessFail(status, dialog) {
    showErrorMessage(status, dialog);
}

function deleteProviderAsync(providerId) {
    var dialog = showProcessingDialog();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var data = {id: providerId};
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
            onProcessSuccess(providerId,dialog);
        },
        error: function (xhr, status, error) {
            onProcessFail(status, dialog);
        }
    });
}

function deleteProvider(providerId) {
    deleteProviderAsync(providerId);
}

$("a[id^='delete_']").click(function () {
    var id = this.id + "";
    if (id != "") {
        var providerId = id.substr("delete_".length);
        deleteProvider(providerId);
    }
});


