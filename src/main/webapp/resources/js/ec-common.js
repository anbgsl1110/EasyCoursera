function request(url, type, data) {
    var rtn = 0;
    $.ajax({
        type: type,
        url: url,
        data: data,
        async: false,
        success: function (result) {
            if (result.error == 0) {
                rtn = result.data.items;
            } else {
                rtn = result;
            }
        },
        dataType: "json"
    });
    return rtn;
}
var log;
function getOne(url, type, data) {
    var rtn = request(url, type, data);
    log = rtn;
    if (rtn.length > 0) return rtn[0];
    return 0;
}

function tableDataSerialize(resp) {
    if (resp.error) return [];

    return {
        rows: resp.data.items,
        total: resp.data.total
    };
}

function submitModal(url) {
    var create = $('#ec-modal-form').attr("_create") == "true";

    $('#ec-modal-form').find(".input-checkbox").each(function () {
        var obj = $(this);
        if (obj.attr("checked")) obj.val("true");
    });

    var data = $('#ec-modal-form').serialize();
    var resp;
    if (create) {
        if (url) resp = request(url, "POST", data);
        else resp = request("/user/api/" + VIEW_TYPE + "/create", "POST", data);
        if (resp.error) sweetAlert("操作失败", resp.message, "error");
        else {
            $('#ec-modal-close').click();
            swal("操作成功", "操作成功", "success")
        }
    } else {
        data = "id=" + $('#ec-modal-form').attr("_id") + "&" + data;
        console.log(data);
        if (url) resp = request(url, "POST", data);
        else resp = request("/user/api/" + VIEW_TYPE + "/modify", "POST", data);
        if (resp.error) sweetAlert("操作失败", resp.message, "error");
        else {
            $('#ec-modal-close').click();
            swal("操作成功", "操作成功", "success")
        }
    }

}

function tableDataOnClickRow(row, element, filed) {
    var data = {id: row.id};
    var obj = getOne("/user/api/" + VIEW_TYPE + "/get", "GET", data);
    $('#ec-modal-open').click();
    $('#ec-modal-form').attr("_create", "false").attr("_id", row.id);
    SetWebControls(obj);
}

function SetWebControls(data) {
    $('#ec-modal-form').find("input,textarea").each(function () {
        for (var key in data) {
            if ($(this).attr("name") == key) $(this).val(data[key]);
            else continue;

            if ($(this).attr("type") == "checkbox") {
                if (data[key] == true) $(this).attr("checked", "checked");
                else $(this).removeAttr("checked");
            }
        }
    });

}

$(document).ready(function () {

    $('#ec-modal-add-btn').click(function () {
        $('#ec-modal-form').attr("_create", "true");

        $('#ec-modal-open').click();
    });

    $('#ec-teacher-table').bootstrapTable({
        url: '/user/api/' + VIEW_TYPE + '/list',
        pagination: true,
        sidePagination: "server",
        showRefresh: true,
        toolbar: '#toolbar',
        responseHandler: tableDataSerialize,
        queryParams: tableDataQuery,
        onClickRow: tableDataOnClickRow,
        pageNumber: 1,
        pageSize: 10,
        columns: columns
    });
});