$(function() {
	var registerUrl = '/schoolSell_boot/local/register';
	$('#submit').click(function() {
		var localAuth = {};
		var personInfo = {};
		localAuth.userName = $('#userName').val();
		localAuth.password = $('#password').val();
		personInfo.email = $('#email').val();
		personInfo.userType=$('#userType').val()
		personInfo.userName = $('#name').val();
		personInfo.gender = $('#sex').val();
		localAuth.personInfo = personInfo;
		var formData = new FormData();
		formData.append('localAuthStr', JSON.stringify(localAuth));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : registerUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					window.location.href = '/schoolSell_boot/local/login';
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/schoolSell_boot/local/login';
	});
});
