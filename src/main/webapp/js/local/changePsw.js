$(function() {
	var url = '/schoolSell_boot/local/changelocalpassword';
	var userType = getQueryString('usertype');
	var userUrl = '/schoolSell_boot/shopadmin/shoplist';
	if (userType == 1) {
		userUrl = '/schoolSell_boot/frontend/index';
	}
	
	$('#submit').click(function() {
		var userName = $('#userName').val();
		var password = $('#password').val();
		var newPassword = $('#newPassword').val();
		var formData = new FormData();
		formData.append('userName', userName);
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					window.location.href = userUrl;
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = userUrl;
	});
});
