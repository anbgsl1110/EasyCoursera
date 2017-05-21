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
                <h4 id="ec-modal-title" class="modal-title">知识点管理</h4>
            </div>
            <div class="modal-body">
                <form id="ec-modal-form" class="form-inline">
                    <label for="ec-tag-input-root">父级知识点ID：</label>
                    <input class="form-control" id="ec-tag-input-root" name="root"></textarea>
                    <br><br>

                    <label for="ec-tag-input-content">知识点名称：</label>
                    <input class="form-control" id="ec-tag-input-name" name="name"></textarea>
                    <br><br>

                    <label for="ec-tag-input-content">知识点内容：</label>
                    <textarea class="form-control" id="ec-tag-input-content" rows="10" cols="70"
                              name="detail"></textarea>
                    <button id="ec-modal-save" style="display: none"></button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitModal()">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<button id="ec-modal-open" type="button" style="display: none" class="btn btn-primary btn-lg" data-toggle="modal"
        data-target="#ec-modal">
</button>

<div id="wrapper">
    <%@include file="/jsp/include/nav.jsp" %>
    <%@include file="/jsp/include/leftside.jsp" %>
    <link rel="stylesheet" href="/resources/css/bootstrap-table.min.css">
    <script src="/resources/js/bootstrap-table.min.js"></script>
    <script src="/resources/js/bootstrap-table-zh-CN.js"></script>


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
                        <h3 class="panel-title">知识点管理</h3>
                    </div>
                    <div class="panel-body">
                        <table id="ec-teacher-table">
                        </table>
                    </div>
                </div>

                <script>

                    function afterFill(id) {

                    }

                    function tableDataQuery(param) {
                        return {
                            size: param.limit,
                            page: ((param.offset / param.limit) + 1),
                            creator: thisUserId
                        }
                    }

                    var columns = [{
                        checkbox: false
                    }, {
                        field: 'id',
                        title: '知识点 ID'
                    }, {
                        field: 'name',
                        title: '知识点名称'
                    }, {
                        field: 'detail',
                        title: '知识点内容'
                    }, {
                        field: 'root',
                        visible: false
                    }];

                </script>

            </div>
        </div>
    </div>
</body>
</html>
