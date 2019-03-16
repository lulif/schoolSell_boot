$(function() {
	var awardId = getQueryString('awardId');
	var infoUrl = '/schoolSell_boot/shopadmin/getawardbyid?awardId=' + awardId;
	var awardPostUrl = '/schoolSell_boot/shopadmin/modifyaward';
	var isEdit = false;
	if (awardId) {
		getInfo(awardId);
		isEdit = true;
	} else {
		awardPostUrl = '/schoolSell_boot/shopadmin/addaward';
	}

	$("#pass-date").calendar({
	    value: [new Date().Format('yyyy-MM-dd')]
	});

	function getInfo(id) {
		$.getJSON(infoUrl, function(data) {
			if (data.success) {
				var award = data.award;
				$('#award-name').val(award.awardName);
				$('#priority').val(award.priority);
				$('#award-desc').val(award.awardDesc);
				$('#point').val(award.point);
				$('#pass-date').val(new Date(award.expireTime).Format('yyyy-MM-dd'));
			}
		});
	}

	$('#submit').click(function() {
		var award = {};
		award.awardName = $('#award-name').val();
		award.priority = $('#priority').val();
		award.awardDesc = $('#award-desc').val();
		award.point = $('#point').val();
		award.awardId = awardId ? awardId : '';
		award.expireTime = $('#pass-date').val();
		var thumbnail = $('#small-img')[0].files[0];
		var formData = new FormData();
		formData.append('thumbnail', thumbnail);
		formData.append('awardStr', JSON.stringify(award));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : awardPostUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					$('#captcha_img').click();
					setTimeout(function(){
						window.location.href="/schoolSell_boot/shopadmin/awardmanager";
					},800);
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

});