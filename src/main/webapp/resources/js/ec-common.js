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

function submitModal() {
    $('#ec-modal-form').find(".input-checkbox").each(function () {
        var obj = $(this);
        if (obj.attr("checked")) obj.val("true");
    });
    var data = $('#ec-modal-form').serialize();
    var resp = request("/user/api/" + VIEW_TYPE + "/create", "POST", data);
    console.log(resp);
    if (resp.error) sweetAlert("操作失败", resp.message, "error");
    else {
        $('#ec-modal-close').click();
        swal("操作成功", "操作成功", "success")
    }
}

function tableDataOnClickRow(row, element, filed) {
    var data = {id:row.id};
    var obj = getOne("/user/api/" + VIEW_TYPE + "/get", "GET", data);

}

$('#ec-modal-add-btn').click(function () {
    $('#ec-modal-open').click();
});


$(document).ready(function () {
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