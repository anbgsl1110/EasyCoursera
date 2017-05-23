<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/jsp/include/common.jsp" %>
<%@include file="/jsp/include/header.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>

<body>
<div id="ec-modal" class="modal fade" tabindex="-1" role="dialog" data-ec-id="0">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button id="ec-modal-close" type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 id="ec-modal-title" class="modal-title">二维码管理</h4>
            </div>
            <div class="modal-body">
                <form id="ec-modal-form" class="form-inline">
                    <label for="ec-course-input-name">二维码名称：</label>
                    <input type="text" class="form-control"  id="ec-course-input-name" name="name">
                    <br><br>

                    <label>二维码有效期：</label>
                    <input id="ec-course-input-ttl" class="form-control" type="text" name="ttl" placeholder="单位(秒)，默认为 3 天"/>
                    (秒)
                    <br><br>

                    <label>绑定对象类型：</label>
                    <select class="input-select form-control" name="objType">
                        <option value="1">获取题目</option>
                        <option value="2">获取知识点</option>
                        <option value="3">知识点随机题目</option>
                        <option value="4">选课</option>
                    </select>
                    <br><br>

                    <label>二维码绑定ID：</label>
                    <input class="form-control" type="text" name="objId"/>
                    <br><br>

                    <label>二维码失效时间：</label>
                    <input id="ec-qrcode-refresh-time" class="form-control" type="text" disabled/>
                    <br><br>

                    <div style="position: absolute;top: 30px;right: 80px;display: none">
                        <label>二维码如下</label>
                        <br>
                        <label id="ec-qrcode-div"></label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="customSubmitModal()">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<button id="ec-modal-open" type="button" style="display: none" class="btn btn-primary btn-lg" data-toggle="modal"
        data-target="#ec-modal">
</button>

<script>
    function beforeFill(id, row) {
        $('#ec-qrcode-div').parent().css("display", "block");
        $('#ec-qrcode-div').html("");
        $('#ec-qrcode-div').qrcode({
            width: 140,
            height: 140,
            text: row.url
        });

        $('#ec-qrcode-refresh-time').val(formatterDate(row.refreshTime + row.ttl * 1000));

        if (checkExpire(row)) {
            swal({
                    title: "二维码已过期！",
                    text: "请输入新的有效期以刷新二维码",
                    type: "input",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    animation: "slide-from-top",
                    inputPlaceholder: "单位(秒), 原有效期为：" + row.ttl
                },
                function (inputValue) {
                    if (inputValue === false) return false;

                    if (inputValue === "") {
                        swal.showInputError("你需要输入有效期！");
                        return false
                    }

                    var param = {ttl: inputValue, id: id};
                    var data = request("/user/api/qrcode/refresh", "POST", param);

                    if (data.error) sweetAlert("操作失败", resp.message, "error");
                    else {
                        SetWebControls(data[0]);
                        $('#ec-modal-open').click();
                        swal("操作成功", "操作成功", "success");
                    }
                });
            return false;
        }
        return true;
    }

    function customSubmitModal() {
        var create = $('#ec-modal-form').attr("_create") == "true";

        var data = $('#ec-modal-form').serialize();
        var resp;
        if (create) {
            resp = getOne("/user/api/qrcode/generate", "POST", data);
            if (!resp || resp.error) sweetAlert("操作失败", resp.message, "error");
            else {
                swal("操作成功", "操作成功", "success");

                $('#ec-qrcode-div').qrcode({
                    width: 140,
                    height: 140,
                    text: resp.url
                });
                $('#ec-qrcode-div').parent().css("display", "block");
            }
        } else {
            data = "id=" + $('#ec-modal-form').attr("_id") + "&" + data;
            resp = request("/user/api/qrcode/patch", "POST", data);
            if (resp.error) sweetAlert("操作失败", resp.message, "error");
            else {
                $('#ec-modal-close').click();
                swal("操作成功", "操作成功", "success")
            }
        }

    }

    function checkExpire(row) {
        if (row.url == "null" || row.url == "") return true;

        var refresh = row.refreshTime;
        var ttl = row.ttl * 1000;
        var now = new Date().getTime();

        return now > (refresh + ttl);
    }

    function tableDataQuery(param) {
        return {
            size: param.limit,
            page: ((param.offset / param.limit) + 1),
        }
    }

    function formatterQrcodeType(data) {
        if (data == 1) return '获取题目';
        if (data == 2) return '获取知识点';
        if (data == 3) return '知识点随机题目';
        if (data == 4) return '选课';
    }

    function customRowStyleFunc(row, index) {
        if(!checkExpire(row)) return {};

        return {classes:"danger"};
    }

    function formatterQrcodeDate(data, row) {
        return formatterDate(data + row.ttl * 1000)
    }

    var columns = [{
        field: 'id',
        title: '二维码 ID'
    }, {
        field: 'name',
        title: '二维码名称'
    }, {
        field: 'ttl',
        title: '有效期'
    }, {
        field: 'refreshTime',
        title: '失效时间',
        formatter: formatterQrcodeDate
    }, {
        field: 'objType',
        title: '绑定类型',
        formatter: formatterQrcodeType
    }, {
        field: 'objId',
        title: '绑定对象 id'
    }];

</script>

<div id="wrapper">
    <%@include file="/jsp/include/nav.jsp" %>
    <%@include file="/jsp/include/leftside.jsp" %>

    <div class="main">
        <div class="main-content">
            <div class="container-fluid">

                <%--MAIN--%>
                <div id="toolbar" class="btn-group">
                    <button id="ec-modal-add-btn" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                    </button>
                </div>

                <div class="panel">
                    <div class="panel-heading">
                        <h3 class="panel-title">二维码管理</h3>
                        <p>(以下标红为已过期二维码，点击可刷新)</p>
                    </div>
                    <div class="panel-body">
                        <table id="ec-teacher-table">
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
