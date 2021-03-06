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
        }, error: function () {
            rtn = {
                error: 1,
                message: "请求不能被接受"
            };
        },
        dataType: "json"
    });
    return rtn;
}

function getOne(url, type, data) {
    var rtn = request(url, type, data);
    if (rtn.length > 0) return rtn[0];
    return 0;
}

function tableDataSerialize(resp) {
    if (resp.error) return [];

    var items = resp.data.items;
    for (var index in items) {
        var item = items[index];
        for (var filed in item) {
            if (item[filed] == null) continue;
            if (item[filed].length > 32) item[filed] = item[filed].substr(0, 32) + "..."
        }
    }
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
        if (url) resp = request(url, "POST", data);
        else resp = request("/user/api/" + VIEW_TYPE + "/modify", "POST", data);
        if (resp.error) sweetAlert("操作失败", resp.message, "error");
        else {
            $('#ec-modal-close').click();
            updateRow($('#ec-teacher-table'), resp[0]);
            swal("操作成功", "操作成功", "success")
        }
    }
}

function updateRow(obj, data) {
    obj.bootstrapTable('updateRow', {index: data.id, row: data});
}

function fillData(formObj, data) {
    formObj.find("input,textarea,select").each(function () {
        for (var key in data) {

            if ($(this).attr("name") == key) {
                if ($(this).attr("type") == "text" || $(this).prop("tagName") == 'TEXTAREA')
                    $(this).val(data[key]);
            } else continue;

            if ($(this).attr("type") == "radio") {
                if (data[key] == $(this).val()) $(this).prop('checked', 'checked');
                else $(this).removeAttr('checked');
                continue;
            }

            if ($(this).hasClass("input-select")) {
                $(this).val(data[key]);
                continue;
            }

            if ($(this).attr("type") == "checkbox") {
                if (data[key] == true) $(this).attr("checked", "checked");
                else $(this).removeAttr("checked");
            }
        }
    });
}

function tableDataOnClickRow(row, element, filed) {
    if ((typeof customTableDataOnClickRow) != 'undefined') {
        return customTableDataOnClickRow(row, element, filed);
    }

    var data = {id: row.id};
    var obj = getOne("/user/api/" + VIEW_TYPE + "/get", "GET", data);

    if ((typeof beforeFill) != 'undefined') {
        if (!beforeFill(row.id, obj)) return;
    }

    $('#ec-modal-open').click();
    SetWebControls(obj);

    if ((typeof afterFill) != 'undefined') afterFill(row.id, obj);
}

function SetWebControls(data) {
    var obj = $('#ec-modal-form').attr("_create", "false").attr("_id", data.id);
    fillData(obj, data);
}

function formatterDate(data) {
    var time = new Date(data);
    return time.toLocaleDateString() + " " + time.toLocaleTimeString();
}


function formatterBoolean(data) {
    if (data) return "是";
    else return "-";
}

function rowStyleFunc(row, index) {
    if ((typeof customRowStyleFunc) == 'undefined') return {};

    return customRowStyleFunc(row, index);
}

$(document).ready(function () {
    if ((typeof tableDataQuery) == 'undefined') return;

    $('#ec-modal-add-btn').click(function () {
        $('#ec-modal').modal('show');

        $('#ec-modal-form').attr("_create", "true").attr("_id", 0)
            .find("input,textarea,select").each(function () {
            if ($(this).attr("type") == "text" || $(this).prop("tagName") == "TEXTAREA") $(this).val("")
        });

    });

    $('#ec-teacher-table').bootstrapTable({
        url: LIST_URL,
        pagination: true,
        sidePagination: "server",
        showRefresh: true,
        toolbar: '#toolbar',
        height: 480,
        idField: 'id',
        responseHandler: tableDataSerialize,
        queryParams: tableDataQuery,
        onClickRow: tableDataOnClickRow,
        pageNumber: 1,
        pageSize: 8,
        escape: true,
        columns: columns,
        rowStyle: rowStyleFunc
    });

});