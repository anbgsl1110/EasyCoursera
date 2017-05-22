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
                <h4 id="ec-modal-title" class="modal-title">题库管理</h4>
            </div>
            <div class="modal-body">
                <form id="ec-modal-form" class="form-inline">
                    <label>题目类型：</label>
                    <input class="input-checkbox" type="radio" name="type" value="1"/> 主观题
                    <input class="input-checkbox" type="radio" name="type" value="2"/> 选择题
                    <br><br>

                    <label for="ec-exam-input-content">题目内容：</label>
                    <textarea class="form-control" id="ec-exam-input-content" rows="8" cols="75"
                              name="content"></textarea>
                    <br><br>

                    <label for="ec-exam-input-answer">参考答案：</label>
                    <textarea class="form-control" id="ec-exam-input-answer" rows="8" cols="75"
                              name="answer"></textarea>

                    <label>已绑定知识点：</label>
                    <br>
                    <label id="ec-exam-tag"></label>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitModal()">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div id="ec-modal-2" class="modal fade" tabindex="-1" role="dialog" data-ec-id="0">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button id="ec-modal-close-2" type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 id="ec-modal-title-2" class="modal-title">绑定知识点</h4>
            </div>
            <div class="modal-body">
                <form id="ec-modal-form-2" class="form-inline">
                    <label for="ec-tag-input-root">题目ID：</label>
                    <input class="form-control" id="ec-tag-exam-id" name="examId"></textarea>
                    <br><br>

                    <label for="ec-tag-input-root">知识点ID：</label>
                    <input class="form-control" id="ec-tag-input-root" name="tagId"></textarea>
                    <br><br>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitModal2()">保存</button>
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
    <link rel="stylesheet" href="/resources/dist/bootstrap-table.css">
    <script src="/resources/dist/bootstrap-table.js"></script>
    <script src="/resources/dist/locale/bootstrap-table-zh-CN.js"></script>


    <div class="main">
        <div class="main-content">
            <div class="container-fluid">

                <%--MAIN--%>
                <div id="toolbar" class="btn-group">
                    <button id="ec-modal-add-btn" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                    </button>

                    <button id="ec-modal-tag-bind-btn" type="button" class="btn btn-default" style="margin-left: 20px">
                        <span class="glyphicon glyphicon-pushpin" aria-hidden="true"></span>绑定知识点
                    </button>
                </div>

                <div class="panel">
                    <div class="panel-heading">
                        <h3 class="panel-title">题库管理</h3>
                    </div>
                    <div class="panel-body">
                        <table id="ec-teacher-table">
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        function tableDataQuery(param) {
            return {
                size: param.limit,
                page: ((param.offset / param.limit) + 1),
                creator: thisUserId
            }
        }

        function submitModal2() {
            var data = $('#ec-modal-form-2').serialize();

            var resp = request("/user/api/tagexam/create", "POST", data);
            if (resp.error) sweetAlert("操作失败", resp.message, "error");
            else {
                $('#ec-modal-close').click();
                swal("操作成功", "操作成功", "success");
                $('#ec-modal-close-2').click();
            }
        }

        function afterFill(id) {
            var param = {
                examId: id
            };
            var data = request("/user/api/tagexam/all", 'GET', param);

            if (data.error) return;

            var label = "";
            for (var index in data) {
                var item = data[index];
                label = label + item.name + ";\n"
            }

            $('#ec-exam-tag').text(label);
        }

        $('#ec-modal-tag-bind-btn').click(function () {
            $('#ec-modal-2').modal("show");
        });

        var columns = [{
            checkbox: false
        }, {
            field: 'id',
            title: '题目 ID'
        }, {
            field: 'content',
            title: '题目内容'
        }, {
            field: 'answer',
            title: '参考答案'
        }, {
            field: 'createTime',
            title: '创建时间',
            formatter: formatterDate
        }];

    </script>
</body>
</html>
