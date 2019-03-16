$(function() {
	var bindUrl = '/schoolSell_boot/local/bindlocalauth';
	var userType = getQueryString('usertype');
	var userType = getQueryString('usertype');
	var userUrl = '/schoolSell_boot/shopadmin/shoplist';
	if (userType == 1) {
		userUrl = '/schoolSell_boot/frontend/index';
	}

	$('#submit').click(function() {
		var userName = $('#username').val();
		var password = $('#password').val();
		var verifyCodeActual = $('#j_captcha').val();
		var needVerify = false;
		if (!verifyCodeActual) {
			$.toast('请输入验证码');
			return;
		}
		$.ajax({
			url : bindUrl,
			async : false,
			cache : false,
			type : 'post',
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual
			},
			success : function(data) {
				if (data.success) {
					$.toast('绑定成功');
					window.location.href = userUrl;

				} else {
					$.toast('提交失败' + data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	})
	$('#back').click(function() {
		window.location.href = userUrl;
	});

})