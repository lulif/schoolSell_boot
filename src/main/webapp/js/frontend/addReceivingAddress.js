$(function() {
	var addReceivingAddressUrl = '/schoolSell_boot/frontend/insertaddress';
	var getAddressInfo = '/schoolSell_boot/frontend/getreceivingaddressinfo';
	var modifyAddressInfo = '/schoolSell_boot/frontend/modifyaddressinfo';
	var addressId = getQueryString("addressId");
	var isModify = getQueryString("modify");
	modifyAddress();

	function modifyAddress() {
		if (isModify == 1) {
			$("#submit").html('修改');

			$.getJSON(getAddressInfo + '?addressId=' + addressId,
					function(data) {
						if (data.success) {
							$('#location').val(data.receivingAddress.location);
							$('#locationDetails').val(
									data.receivingAddress.locationDetails);
							$('#contactName').val(
									data.receivingAddress.contactName);
							$('#contactPhone').val(
									data.receivingAddress.contactPhone);
						} else {
							$.toast('内部异常');
						}
					})
		}
	}

	$('#submit')
			.click(
					function() {
						var operateUrl = addReceivingAddressUrl;
						var receivingAddress = {};
						var formData = new FormData();
						receivingAddress.location = $('#location').val();
						receivingAddress.locationDetails = $('#locationDetails')
								.val();
						receivingAddress.contactName = $('#contactName').val();
						receivingAddress.contactPhone = $('#contactPhone')
								.val();
						if (isModify == 1) {
							receivingAddress.addressId = addressId;
							operateUrl = modifyAddressInfo;
						}
						var verifyCodeActual = $('#j_captcha').val();
						if (!verifyCodeActual) {
							$.toast('请输入验证码！');
							return;
						}

						formData.append('receivingAddress', JSON
								.stringify(receivingAddress));
						formData.append('verifyCodeActual', verifyCodeActual);

						$
								.ajax({
									url : operateUrl,
									type : 'POST',
									data : formData,
									contentType : false,
									processData : false,
									cache : false,
									success : function(data) {
										if (data.success) {
											if (isModify == 1) {
												$.toast('修改地址成功！');
											} else {
												$.toast('新增地址成功！');
											}
											setTimeout(
													function() {
														window.location.href = '/schoolSell_boot/frontend/myreceivingaddress';
													}, 300);
										} else {
											$.toast(data.errMsg)
										}
									}
								});
					});
	// ---------------------------------------------------------------------微信接口调用↓

	configWx();

	function configWx() {
		var thisPageUrl = location.href.split('#')[0];
		$.ajax({
			url : "/schoolSell_boot/wechatlocation/getticket",
			type : 'POST',
			data : {
				url : thisPageUrl
			},
			success : function(data) {
				if (data != null) {
					configWeiXin(data.appId, data.timestamp, data.nonceStr,
							data.signature);
				} else {
					console.log("配置weixin jsapi失败");
				}
			}
		});
	}

	function configWeiXin(appId, timestamp, nonceStr, signature) {
		wx.config({
			debug : false,// 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : appId,
			timestamp : timestamp,
			nonceStr : nonceStr,
			signature : signature,
			jsApiList : [ 'openLocation', 'getLocation' ]
		});
	}
	function getLocation() {
		wx.getLocation({
			type : 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			success : function(res) {
				// 使用微信内置地图查看位置接口
				wx.openLocation({
					latitude : res.latitude, // 纬度，浮点数，范围为90 ~ -90
					longitude : res.longitude, // 经度，浮点数，范围为180 ~ -180。
					scale : 28, // 地图缩放级别,整形值,范围从1~28。默认为最大
					infoUrl : 'http://www.gongjuji.net', // 在查看位置界面底部显示的超链接,可点击跳转（测试好像不可用）
				});
			},
			cancel : function(res) {

			}
		});
	}

	// ---------------------------------------------------------------------------------------微信接口调用↑

	$('#map').click(function() {
		getLocation()
	})

});