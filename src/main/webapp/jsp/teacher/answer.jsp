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
                <h4 id="ec-modal-title" class="modal-title">作业批阅</h4>
            </div>
            <div class="modal-body">
                <form id="ec-modal-form" class="form-inline">
                    <label>学生：</label>
                    <input class="form-control" type="text" name="userName" disabled>
                    <br><br>

                    <label>题目：</label>
                    <textarea class="form-control" name="examination" disabled rows="5" cols="75"></textarea>
                    <br><br>

                    <label>回答：</label>
                    <textarea class="form-control" name="content" rows="5" cols="75" disabled></textarea>
                    <br><br>

                    <label>批阅意见：</label>
                    <textarea id="ec-answer-reply" class="form-control" name="reply" placeholder="选填" rows="5" cols="75"></textarea>
                    <br><br>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-success" onclick="checkExam(true)">正确</button>
                <button type="button" class="btn btn-danger" onclick="checkExam(false)">错误</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<button id="ec-modal-open" type="button" style="display: none" class="btn btn-primary btn-lg" data-toggle="modal"
        data-target="#ec-modal">
</button>

<script>
    function tableDataQuery(param) {
        return {
            size: param.limit,
            page: ((param.offset / param.limit) + 1),
        }
    }

    function customTableDataOnClickRow(row, element, filed) {
        $('#ec-modal').modal('show');
        var data = getOne("/user/api/answer/get/" + row.id, "GET");

        data.examination = data.examination.content;

        var obj = $('#ec-modal-form').attr("_create", "false").attr("_id", data.id);
        fillData(obj, data);
    }

    function formatExamination(data) {
        if (data.length > 32) return data.substr(0, 32) + "...";

        return data;
    }

    var columns = [{
        field: 'id',
        visible: false
    }, {
        field: 'userName',
        title: '学生姓名'
    }, {
        field: 'examination.content',
        title: '题目内容',
        formatter: formatExamination
    }, {
        field: 'content',
        title: '回答内容'
    }];

    function checkExam(right) {
        var reply = $('#ec-answer-reply').val();

        var param = {
            reply: reply,
            id: $('#ec-modal-form').attr("_id"),
            judge: right
        };

        var resp = request("/user/api/answer/reply", "POST", param);

        if (resp.error) sweetAlert("操作失败", resp.message, "error");
        else {
            $('#ec-modal-close').click();
            swal("操作成功", "操作成功", "success")
        }
    }
</script>

<div id="wrapper">
    <%@include file="/jsp/include/nav.jsp" %>
    <%@include file="/jsp/include/leftside.jsp" %>

    <div class="main">
        <div class="main-content">
            <div class="container-fluid">

                <%--MAIN--%>
                <div class="panel">
                    <div class="panel-heading">
                        <h3 class="panel-title">答题批阅</h3>
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
