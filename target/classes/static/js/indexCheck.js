$(function () {
    $("#validateCode").keyup(function () {
        checkLoginValidateCode($(this).val());
    });

});

function uploadLoginValidateCode() {
    $("#loginValidateCode").attr("src", "/loginValidateCode?random=" + new Date().getMilliseconds());
}

function checkLoginValidateCode(validateCode) {

    console.log(validateCode);

    var error = $("#validateCode").parent().next();
    if (validateCode != null && validateCode != "") {
        $.ajax({
            type: "POST",
            async: false,
            url: "/checkLoginValidateCode?validateCode=" + validateCode,
            success: function (json) {
                if (json != null && json.code == 200 && json.status != null) {
                    if (json.status == true) {
                        error.html("恭喜你验证码，正确！！！！！");
                    } else if (json.status == false) {
                        error.html("验证码错误，请重新输入");
                    } else {
                        error.html("验证码过期，请重新输入");
                        uploadLoginValidateCode();
                    }
                }
                return false;
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                layer.msg("服务器错误！状态码：" + XMLHttpRequest.status);
                // 状态
                console.log(XMLHttpRequest.readyState);
                // 错误信息
                console.log(textStatus);
                return false;
            }
        });
    } else {
        error.html("请输入验证码！");
    }
}

var flag = true;

function check(obj) {
    switch ($(obj).attr('name')) {
        case "id":
            if (obj.value == "") {
                layer.msg("账号不能为空");
                flag = false;
                return flag;
            } else {
                flag = true;
            }
            if (obj.value != "") {
                var url = "useridcheck?id=" + obj.value + "&" + new Date().getTime();
                $.get(url, function (data) {
                    if (data == "false") {
                        layer.msg("此账号已被使用!");
                        flag = false;
                        return flag;
                    }
                });
            } else {
                flag = true;
            }
            re = /^[0-9]+$/;
            if (!re.test(obj.value)) {
                layer.msg("错误：账号必须只包含数字！");
                flag = false;
                return flag;
            } else {
                flag = true;
            }
            break;
        case "username":
            re = /^\w+$/;
            if (obj.value == "") {
                layer.msg("用户名不能为空");
                flag = false;
                return flag;

            } else {
                flag = true;
            }
            if (!re.test(obj.value)) {
                layer.msg("错误：用户名必须只包含字母、数字和下划线！");
                flag = false;
                return flag;

            } else {
                flag = true;
            }
            break;
        case "sex":
            if (obj.value == "") {
                layer.msg("请输入性别");
                flag = false;
                return flag;

            } else {
                flag = true;
            }
            if (obj.value != "男" && obj.value != "女") {
                layer.msg("请按要求输入性别");
                flag = false;
                return flag;
            } else {
                flag = true;
            }
            break;
        case "password":
            if (obj.value == "") {
                layer.msg("密码不能为空");
                flag = false;
                return flag;
            } else {
                flag = true;
            }
            if (obj.value.length < 6) {
                layer.msg("密码不能小于6位");
                flag = false;
                return flag;
            } else {
                flag = true;
            }
            re = /[0-9]/;
            if (!re.test(obj.value)) {
                layer.msg("密码至少包含一个数字");
                flag = false;
                return flag;
            } else {
                flag = true;
            }
            re = /[a-z]/;
            if (!re.test(obj.value)) {
                layer.msg("密码至少包含一个小写字母");
                flag = false;
                return flag;
            } else {
                flag = true;
            }
            break;
        case "repassword":
            var form = document.getElementById("myform");
            if (form.password.value != obj.value) {
                layer.msg("再次输入密码错误");
                flag = false;
                return flag;
            } else {
                flag = true;
            }
            break;
        case "email":
            re = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
            if (!re.test(obj.value)) {
                layer.msg("邮箱格式错误");
                flag = false;
                return flag;
            } else {
                flag = true;
            }
            break;
        case "validateCode":
            if (obj.value == "") {
                layer.msg("请输入验证码");
                flag = false;
                return flag;
            } else {
                flag = true;
            }
            if (obj.value != "") {
                $.ajax({
                    type: "POST",
                    async: false,
                    url: "/checkLoginValidateCode?validateCode=" + obj.value,
                    success: function (json) {
                        if (json != null && json.code == 200 && json.status != null) {
                            if (json.status == true) {
                                layer.msg("验证码正确");
                            } else if (json.status == false) {
                                layer.msg("验证码错误");
                            } else {
                                layer.msg("验证码过期，请重新输入");
                                uploadLoginValidateCode();
                            }
                        }
                        return false;
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        layer.msg("验证码错误");
                        // 状态
                        console.log(XMLHttpRequest.readyState);
                        // 错误信息
                        console.log(textStatus);
                        return false;
                    }
                });
            } else {
                layer.msg("请输入验证码！");
            }
            break;
    }
}

function checkForm(form) {
    var els = form.getElementsByTagName('input');
    for (var i = 0; i < els.length; i++) {
        if (els[i] != null) {
            if (els[i].getAttribute("onmouseleave")) {
                check(els[i]);
            }
        }
    }
    return flag;
}

function changeCode(obj) {
    obj.src = "getCode?" + new Date().getTime();
}