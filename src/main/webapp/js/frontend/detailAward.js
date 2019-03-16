$(function() {
	var awardId = getQueryString('awardId');
	var userAwardId = getQueryString('userAwardId');
	var awardUrl = '/schoolSell_boot/frontend/getawardbyid?awardId='+awardId;
	var nowDate=new Date();
	var isPre=getQueryString('ispre');
	$
			.getJSON(
					awardUrl,
					function(data) {
						if (data.success) {
							var award = data.award;
							var shopId=data.shopId;
							$('#award-img').attr('src',
									getContextPath() + award.awardImg);
							if(isPre==1){
								$('#award-time').text("更多");
							}else{
								$('#award-time').text("兑换有效期至"+
										new Date(award.expireTime)
												.Format("yyyy-MM-dd hh:mm:ss"));
							}
							$('#award-name').text("奖品名称："+award.awardName);
							$('#award-desc').text("奖品描述："+award.awardDesc);
							if (award.point != undefined&&isPre==1) {
								$('#award-point').html("兑换需消耗"+award.point+"积分");
							}else{
								$('#award-point').html("已兑换，操作员扫码即可领取");
							}
							var imgListHtml = '';
							// 生成购买商品的二维码供商家扫描
							if (data.needQRCode&&isPre!=1&&award.expireTime>nowDate.getTime()) {
								imgListHtml += '<div style="text-align:center;background:wheat;">'
									+'<img src="/schoolSell_boot/frontend/generateqrcodetoexchange?awardId='
										+ award.awardId +'&userAwardId='+userAwardId+'"/></div>';
							}
							

							$('#imgList').html(imgListHtml);
						}
					});
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
