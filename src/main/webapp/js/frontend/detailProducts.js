$(function() {
	var productId = getQueryString('productid');
	var productUrl = '/schoolSell_boot/frontend/productdetailpageinfo?productid='
			+ productId;
	var addUrl = '/schoolSell_boot/frontend/addusercollectionmap';
	var addIntoCart='/schoolSell_boot/frontend/addintoshopppingcart';
	var product = null;
	var shop = null;
	$
			.getJSON(
					productUrl,
					function(data) {
						if (data.success) {
							product = data.product;
							shop = data.product.shop;
							$('#product-img').attr('src',
									getContextPath() + product.imgAddr);
							$('#product-time').text(
									"更新时间："
											+ new Date(product.lastEditTime)
													.Format("yyyy-MM-dd"));
							$('#product-name').text(
									"商品名称:" + product.productName);
							$('#product-desc').text(
									"商品描述:" + product.productDesc);
							$('#normalPrice').text(product.normalPrice);
							$('#promotionPrice').text(
									'现价：' + product.promotionPrice);
							if (product.point != undefined) {
								$('#product-point')
										.text(
												"购买可得" + product.point
														+ '积分，操作员扫码即可购买');
							}
							var imgListHtml = '';
							product.productImgList
									.map(function(item, index) {
										imgListHtml += '<div style="text-align:center;background:wheat;"> <img src="'
												+ getContextPath()
												+ item.imgAddr
												+ '"/></div><hr/>';
									});
							// 生成购买商品的二维码供商家扫描
							if (data.needQRCode) {
								imgListHtml += '<div style="text-align:center;background:wheat;">'
										+ '<img src="/schoolSell_boot/frontend/generateqrcodetobuy?productId='
										+ product.productId + '"/></div>';
							}

							$('#imgList').html(imgListHtml);
						}

						$('#collection').click(
								function() {
									var userCollectionMap = {};
									userCollectionMap.product = product;
									userCollectionMap.shop = shop;
									var formData = new FormData();
									formData.append('userCollectionMap', JSON
											.stringify(userCollectionMap));
									$.ajax({
										url : addUrl,
										type : 'POST',
										data : formData,
										contentType : false,
										processData : false,
										cache : false,
										success : function(data) {
											if (data.success) {
												$.toast('收藏成功');
											} else {
												if (data.had_collect) {
													$.toast("此商品已收藏");
												} else {
													$.toast('收藏失敗');
												}
											}
										}
									});
								});

					});
	$('#comment')
			.click(
					function() {
						window.location.href = '/schoolSell_boot/frontend/commentbyproductid?productId='
								+ product.productId;
					});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();

	$(document).on(
			'click',
			'.create-actions',
			function() {
				var buttons1 = [
						{
							text : '请选择',
							label : true
						},
						{
							text : '立即下单',
							bold : true,
							color : 'danger',
							onClick : function() {
								localStorage.setItem("productId",product.productId);
								window.location.href='/schoolSell_boot/frontend/addorder';
							}
						},
						{
							text : '加入购物车',
							onClick : function() {
								var userShopppingCart = {};
								userShopppingCart.product = product;
								userShopppingCart.shop = shop;
								var fromData = new FormData();
								fromData.append("userShoppingCart", JSON
										.stringify(userShopppingCart));
								$.ajax({
									url:addIntoCart,
									type:'POST',
									data:fromData,
									contentType : false,
									processData : false,
									cache : false,
									success:function(data){
										if (data.success) {
											$.toast('成功加入购物车');
										} else {
											if (data.had_add) {
												$.toast("此商品已在购物车列表");
											} else {
												$.toast('加入购物车失败');
											}
										}
									}
									
								});
							}
						} ];
				var buttons2 = [ {
					text : '取消',
					bg : 'danger'
				} ];
				var groups = [ buttons1, buttons2 ];
				$.actions(groups);
			});
});
