
function changeEnabled(chbox, id) {
    var enabled = chbox.is(':checked');
    chbox.parent().parent().css("color",enabled ? "green" : "red");
    $.ajax({
        type: "POST",
        url: ajaxUrl + "enabled",
        data: {id: id, enabled: enabled},
        success: function () {
            successNoty(enabled ? "Enable" : "Disable");
        }
    });
}

// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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
                        "asc"
                    ]
                ]
            })
        }
    );
});