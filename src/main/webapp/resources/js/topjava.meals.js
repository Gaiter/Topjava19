
function updateMealTable() {
    var form = $("#dateTimeForm");
    $.ajax({
        type: "POST",
        url: context.ajaxUrl + "filter",
        data: form.serialize(),
        success: function (data) {
            updateTableByData(data);
            successNoty("Updated");
        }
    });
}

function clearForm() {
    $("#dateTimeForm")[0].reset();
    updateTable();
}

function updateTableByData(data) {
    context.datatableApi.clear().rows.add(data).draw();
}

// $(document).ready(function () {
$(function () {
    makeEditable({
        ajaxUrl: "ajax/profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "dsc"
                ]
            ]
        })
    });
});