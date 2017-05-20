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
