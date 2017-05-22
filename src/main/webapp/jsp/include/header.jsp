<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh">
<head>
    <title>${title}</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!-- VENDOR CSS -->
    <link rel="stylesheet" href="/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/vendor/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/resources/vendor/linearicons/style.css">
    <link rel="stylesheet" href="/resources/css/sweetalert.css">

    <!-- MAIN CSS -->
    <link rel="stylesheet" href="/resources/css/main.css">
    <!-- ICONS -->
    <link rel="icon" type="image/png" sizes="96x96" href="/resources/img/icon.png">

    <!-- SCRIPTS -->
    <script src="/resources/vendor/jquery/jquery.min.js"></script>
    <script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resources/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
    <script src="/resources/js/klorofil-common.js"></script>
    <script src="/resources/js/sweetalert.min.js"></script>
    <script src="/resources/js/ec-common.js"></script>

    <link rel="stylesheet" href="/resources/css/bootstrap-table.min.css">
    <script src="/resources/js/bootstrap-table.min.js"></script>
    <script src="/resources/js/bootstrap-table-zh-CN.js"></script>

    <script>
        var CONST_LANGUAGE = {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            };
        var VIEW_TYPE = "${viewType}";
        var thisUserId = "${userId}";
        var LIST_URL = "${listUrl}"
    </script>

    <style>
        label {
            width: 130px;
        }
    </style>
</head>