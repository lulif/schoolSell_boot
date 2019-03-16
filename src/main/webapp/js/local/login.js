$(function() {
	var loginUrl = '/schoolSell_boot/local/logincheck';
	var loginCount = 0;
	var customerUrl = '/schoolSell_boot/frontend/index';
	var shopUrl = '/schoolSell_boot/shopadmin/shoplist';
	var superAdminUrl = '/schoolSell_boot/superadmin/...'

	$('#submit').click(function() {
		var userType = $('#userType').val();
		var userName = $('#userName').val();
		var password = $('#password').val();
		var verifyCodeActual = $('#j_captcha').val();
		var needVerify = false;
		if (loginCount >= 3) {
			if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		$.ajax({
			url : loginUrl,
			type : "POST",
			async : false,
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			cache : false,
			dataType : 'json',
			data : {
				userType : userType,
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual,
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					$.toast('登录成功！');
					if (userType == 1) {
						window.location.href = customerUrl;
					}
					if (userType == 2) {
						window.location.href = shopUrl;
					}
					if (userType == 3) {
						window.location.href = superAdminUrl;
					}
				} else {
					$.toast('登录失败！' + data.errMsg);
					loginCount++;
					if (loginCount >= 3) {
						$('#verifyPart').show();
					}
				}
			}
		});
	});

	$('#register').click(function() {
		window.location.href = '/schoolSell_boot/local/register';
	});
});