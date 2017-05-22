<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/jsp/include/common.jsp" %>
<%@include file="/jsp/include/header.jsp" %>
<body>
<div id="ec-modal" class="modal fade" tabindex="-1" role="dialog" data-ec-id="0">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button id="ec-modal-close" type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 id="ec-modal-title" class="modal-title">消息</h4>
            </div>
            <div class="modal-body">
                <form id="ec-modal-form" class="form-inline">
                    <label>目标用户：</label>
                    <input class="form-control" type="text" name="creatorName" disabled>
                    <br><br>

                    <input id="ec-message-target" type="hidden" name="target">

                    <label>消息类型：</label>
                    <select class="input-select form-control" name="type" disabled>
                        <option value="1">即时消息</option>
                    </select>
                    <br><br>

                    <label for="ec-course-input-content">消息内容：</label>
                    <textarea class="form-control" rows="10" cols="70" name="content"></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitModal('/user/api/message/send')">发送</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="ec-modal-show" class="modal fade" tabindex="-1" role="dialog" data-ec-id="0">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button id="ec-modal-show-close" type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 id="ec-modal-show-title" class="modal-title">消息</h4>
            </div>
            <div class="modal-body">
                <form id="ec-modal-show-form" class="form-inline">
                    <label>消息来源：</label>
                    <input class="form-control" type="text" name="creatorName" disabled>
                    <br><br>

                    <label>消息类型：</label>
                    <select class="input-select form-control" name="type" disabled>
                        <option value="1">即时消息</option>
                    </select>
                    <br><br>

                    <label for="ec-course-input-content">消息内容：</label>
                    <textarea class="form-control" id="ec-course-input-content" rows="10" cols="70"
                              name="content" disabled></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="replyMessage()">点击回复</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
    function tableDataQuery(param) {
        return {
            size: param.limit,
            page: ((param.offset / param.limit) + 1)
        }
    }

    function messageTypeFormatter(data) {
        if (data == 1) return '即时消息';
        return data;
    }

    function replyMessage() {
        $('#ec-modal-show').modal('hide');

        setTimeout(function () {
            $('#ec-modal').modal('show');
        }, 100);
    }

    function customTableDataOnClickRow(row, element, filed) {
        if (!row.read) request("/user/api/message/read", "POST", {id: row.id});

        $('#ec-modal-show').modal('show');
        fillData($('#ec-modal-show-form'), row);
        fillData($('#ec-modal-form'), row);

        $('#ec-message-target').val(row.creator);
    }
    
    function customRowStyleFunc(row, index) {
        if (!row.read) return {classes:"warning"};
        return {};
    }

    var columns = [{
        field: 'id',
        title: '消息 ID'
    }, {
        field: 'type',
        title: '消息类型',
        formatter: messageTypeFormatter
    }, {
        field: 'content',
        title: '消息内容'
    }, {
        field: 'read',
        visible: false
    }, {
        field: 'createTime',
        title: '创建时间',
        formatter: formatterDate
    }, {
        field: 'creator',
        visible: false
    }, {
        field: 'creatorName',
        title: '发送者'
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
                        <h3 class="panel-title">消息管理</h3>
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
