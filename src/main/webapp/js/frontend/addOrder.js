$(function() {
	var productId = localStorage.getItem("productId");
	var isCart = getQueryString("iscart");
	var productUrl = '/schoolSell_boot/frontend/productdetailpageinfo?productid='
			+ productId;
	var defaultAddressUrl = '/schoolSell_boot/frontend/getdefaultaddress';
	var addOrderUrl = '/schoolSell_boot/frontend/insertorderinfo';
	var defaultAddres = null;
	var orderMsg = null;
	var totalMoney = null;
	$.ajaxSettings.async = false;

	$
			.getJSON(
					defaultAddressUrl,
					function(data) {
						if (data.success) {
							defaultAddres = data.defaultAddress;
							var html = '';
							html += ' <div class="card default-address" >'
									+ '<div class="card-content">'
									+ ' <div class="new-product-badge" data-address-id="'
									+ defaultAddres.addressId
									+ '" </div>'
									+ '<div class="card-content-inner"> <span class="icon icon-card" style="font-size:30px"></span>&nbsp;&nbsp;&nbsp;'
									+ '<span style="font-size:20px;font-weight:400">'
									+ defaultAddres.contactName
									+ '&nbsp;&nbsp;&nbsp;&nbsp;'
									+ defaultAddres.contactPhone
									+ '</span>'
									+ '<br><span style="color:gray">'
									+ defaultAddres.location
									+ '&nbsp;&nbsp;'
									+ defaultAddres.locationDetails
									+ '</span> <span class="icon icon-right pull-right" '
									+ 'style="font-size:35px; display:inline-block;position:relative;top:-40px;"></span></div></div></div>';

							$('.list-div').append(html);
						} else {
							$.toast(data.errMsg);
						}
					});

	if (isCart == 1) {
		orderMsg = localStorage.getItem("cartMsg");
		var oneGroup = orderMsg.split(",");
		for (var i = 0; i < oneGroup.length - 1; i++) {
			var id = (oneGroup[i].split('-'))[0];
			var proNum = (oneGroup[i].split('-'))[1];
			var url = '/schoolSell_boot/frontend/productdetailpageinfo?productid=';
			$
					.getJSON(
							url + id,
							function(data) {
								if (data.success) {
									var html = '';
									var product = data.product;
									html += '' + '<div class="card" >'
											+ '<div class="card-header">'
											+ product.shop.shopName
											+ '</div>'
											+ '<div class="card-content" >'
											+ '<div class="list-block media-list">'
											+ '<ul>'
											+ '<li class="item-content">'
											+ '<div class="item-media" data-product-id="'
											+ product.productId
											+ '">'
											+ '<img src="'
											+ getContextPath()
											+ product.imgAddr
											+ '" width="50">'
											+ '</div>'
											+ '<div class="item-inner">'
											+ '<div class="item-subtitle" '
											+ '<span>'
											+ product.productName
											+ '</span>'
											+ '<div class="pull-right" style="display:inline-block"> 购买数量：'
											+ '&nbsp;&nbsp;<input id="number" style="width:25px;height:35px;" value="'
											+ proNum
											+ '" disabled="disabled"/></div>'
											+ '</div>'
											+ '</div>'
											+ '</li>'
											+ '</ul>'
											+ '</div>'
											+ '</div>'
											+ '<div class="card-footer">'
											+ '<p class="color-gray">购买可获取'
											+ product.point
											+ '积分&nbsp;&nbsp;&nbsp;'
											+ '<div class="pull-right"><span style="font-size:15px;font-weight:800;color:gray;" class="num">共'
											+ proNum
											+ '件</span>&nbsp;'
											+ '<span style="color:black">小计:<span>'
											+ '<span style="font-size:20px;font-weight:800;color:red;">￥</span>'
											+ '<span class="acc" style="font-size:20px;font-weight:600;color:red">'
											+ product.promotionPrice
											* proNum
											+ '</span></div>'
											+ '</p>'
											+ '</div></div>';
									$('.list-div').append(html);
								} else {
									$.toast('系统异常');
								}

							});

		}

	} else {
		$
				.getJSON(
						productUrl,
						function(data) {
							if (data.success) {
								var html = '';
								var product = data.product;
								html += '' + '<div class="card" >'
										+ '<div class="card-header">'
										+ product.shop.shopName
										+ '</div>'
										+ '<div class="card-content" >'
										+ '<div class="list-block media-list">'
										+ '<ul>'
										+ '<li class="item-content">'
										+ '<div class="item-media" data-product-id="'
										+ product.productId
										+ '">'
										+ '<img src="'
										+ getContextPath()
										+ product.imgAddr
										+ '" width="50">'
										+ '</div>'
										+ '<div class="item-inner">'
										+ '<div class="item-subtitle" '
										+ '<span>'
										+ product.productName
										+ '</span>'
										+ '<div class="pull-right" style="display:inline-block"> 购买数量：<button id="sub" data-promotion-price="'
										+ product.promotionPrice
										+ '" >-</button>'
										+ '&nbsp;&nbsp;<input id="number" style="width:40px;height:35px;" value="1"/>&nbsp;&nbsp;<button id="add" data-promotion-price="'
										+ product.promotionPrice
										+ '" >+</button></div>'
										+ '</div>'
										+ '</div>'
										+ '</li>'
										+ '</ul>'
										+ '</div>'
										+ '</div>'
										+ '<div class="card-footer">'
										+ '<p class="color-gray">购买可获取'
										+ product.point
										+ '积分&nbsp;&nbsp;&nbsp;'
										+ '<div class="pull-right"><span style="font-size:15px;font-weight:800;color:gray;" class="num">共1件</span>&nbsp;'
										+ '<span style="color:black">小计:<span>'
										+ '<span style="font-size:20px;font-weight:800;color:red;">￥</span>'
										+ '<span class="acc" style="font-size:20px;font-weight:600;color:red">'
										+ product.promotionPrice
										+ '</span></div>'
										+ '</p>'
										+ '</div></div>';
								$('.list-div').append(html);

							} else {
								$.toast('内部错误');
							}
						});

		$('.content').on(
				'click',
				'#add',
				function(e) {
					var number = $(e.currentTarget).siblings()[1];
					number.value++;
					$(e.currentTarget).parents(".card-content").next().find(
							".num").text("共" + number.value + "件");
					$(e.currentTarget).parents(".card-content").next().find(
							".acc").text(
							e.currentTarget.dataset.promotionPrice
									* number.value);

				});
		$('.content').on(
				'click',
				'#sub',
				function(e) {
					var number = $(e.currentTarget).siblings()[0];
					if (number.value > 1) {
						number.value--;
					}
					$(e.currentTarget).parents(".card-content").next().find(
							".num").text("共" + number.value + "件");
					$(e.currentTarget).parents(".card-content").next().find(
							".acc").text(
							e.currentTarget.dataset.promotionPrice
									* number.value);

				});
	}

	$(".content").on('click', '.default-address', function() {
		window.location.href = '/schoolSell_boot/frontend/myreceivingaddress';
	});

	setInterval(function() {
		var numMoney = 0;
		var accArr = $('.acc');
		for (var i = 0; i < accArr.length; i++) {
			numMoney += Number($(accArr[i]).text());
		}
		$('#numMoney').text(numMoney);
		totalMoney = $('#numMoney').text();
	}, 250);

	$('#submit').click(function() {
		if (isCart != 1) {
			orderMsg = productId + '-' + $('#number').val() + ',';
		}
		$.confirm('您共需支付' + totalMoney + '元', function() {
			var formData = new FormData();
			formData.append('orderMessage', orderMsg);
			formData.append('totalMon', totalMoney);
			formData.append('defaultAddres', JSON.stringify(defaultAddres));
			$.ajax({
				url : addOrderUrl,
				type : 'POST',
				data : formData,
				contentType : false,
				processData : false,
				cache : false,
				success : function(data) {
					if (data.success) {
						$.toast("订单支付成功!");
						setTimeout(function() {
							window.history.back();
						}, 450);
					} else {
						$.toast("系统错误");
					}
				}
			});
		}, function() {

			var formData = new FormData();
			formData.append('orderMessage', orderMsg);
			formData.append('totalMon', totalMoney);
			formData.append('defaultAddres', JSON.stringify(defaultAddres));
			formData.append('isCancel', true);
			$.ajax({
				url : addOrderUrl,
				type : 'POST',
				data : formData,
				contentType : false,
				processData : false,
				cache : false,
				success : function(data) {
					if (data.success) {
						$.toast('交易已中止,订单信息可在"我的订单"中查看');
						setTimeout(function() {
							window.history.back();
						}, 450);
					} else {
						$.toast("系统错误");
					}
				}
			});
		});

	})

});
