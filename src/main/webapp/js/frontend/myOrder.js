$(function() {
	var queryOrderMsgListUrl = '/schoolSell_boot/frontend/getordermsglist';
	var modifyOrderStatusUrl = '/schoolSell_boot/frontend/modifyorderstatus';
	var pay4OrderHadCancelUrl = '/schoolSell_boot/frontend/pay4orderhadcancel';
	var orderStatus = null;

	function loadOrderList(str, status) {
		$
				.getJSON(
						queryOrderMsgListUrl + '/' + orderStatus,
						function(data) {
							if (data.success) {
								var orderList = data.orderList;
								var orderProductMap = data.orderProductMap;
								orderList
										.map(function(item, index) {
											var orderDetailList = item.orderDetailList;
											var html = '';
											var productList = orderProductMap[item.orderId];
											html += '<div class="card">'
													+ '<div class="card-header"><span style="font-size:15px;font-weight:600">单号:</span><span style="font-size:15px">'
													+ item.orderId
													+ '</span><span style="font-size:15px;font-weight:600;color:red">￥</span><span style="font-size:15px;color:red">'
													+ item.moneyAccount
													+ '</span></div>'
													+ '<div class="card-content">'
													+ '<div class="card-content-inner">';
											productList
													.map(function(item1, index) {
														html += item1.shop.shopName
																+ ':'
																+ item1.productName
																+ "&nbsp;&nbsp;单价￥"
																+ item1.promotionPrice
																+ '-----'
																+ orderDetailList[index].productNumber
																+ '份' + '<br>';
													});
											html += '</div></div>'
													+ '<div class="card-footer row"><div class="col-85">'
													+ item.receivingAddress.contactName
													+ '&nbsp;&nbsp;&nbsp;'
													+ item.receivingAddress.contactPhone
													+ '<br/>'
													+ item.receivingAddress.location
													+ '&nbsp;&nbsp;&nbsp;'
													+ item.receivingAddress.locationDetails
													+ '<br/>订单创建:'
													+ new Date(item.createTime)
															.Format("yyyy-MM-dd hh:mm:ss")
													+ '</div>'
											if (status == 1) {
												html += '<div class="col-15"><button class="cancel button button-dark" style="width:50px;height:30px;font-weight:600;position:relative;left:-5px" data-order-id="'
														+ item.orderId
														+ '">撤销</button><br><button class="pay button button-danger" style="width:50px;height:30px;font-weight:600;position:relative;left:-5px" data-order-id="'
														+ item.orderId
														+ '">付款</button></div>'
											}
											if (status == 3) {
												html += '<div class="col-15" style="margin:0">'
														+ '<button class="checked button button-danger"'
														+ ' style="width:50px;height:50px;font-weight:600;font-size:15px;position:relative;left:-5px" data-order-id="'
														+ item.orderId
														+ '">确认<br>收货</button></div>'
											}

											html += '</div></div>';
											$(str).append(html);
										});

							}

						});
	}
	$("#unpay").click(function() {
		orderStatus = 1;
		$('#tab1').empty();
		loadOrderList('#tab1', orderStatus);
	});

	$("#unsend").click(function() {
		orderStatus = 2;
		$('#tab2').empty();
		loadOrderList('#tab2', orderStatus);
	});

	$("#unget").click(function() {
		orderStatus = 3;
		$('#tab3').empty();
		loadOrderList('#tab3', orderStatus);
	});

	$('#tab1').on('click', '.cancel', function(e) {
		var orderId = e.currentTarget.dataset.orderId;
		$.ajax({
			url : modifyOrderStatusUrl,
			type : 'POST',
			data : {
				orderId : orderId,
				operationType : "cancel"
			},
			success : function(data) {
				if (data.success) {
					$.toast("订单已撤销")
					setTimeout(function() {
						window.location.reload();
					}, 450);
				} else {
					$.toast("系统错误");
				}
			}
		});
	})

	$('#tab1').on('click', '.pay', function(e) {
		var orderId = e.currentTarget.dataset.orderId;
		$.ajax({
			url : pay4OrderHadCancelUrl,
			type : 'POST',
			data : {
				orderId : orderId
			},
			success : function(data) {
				if (data.success) {
					$.toast("订单支付成功")
					setTimeout(function() {
						window.location.reload();
					}, 450);
				} else {
					$.toast("系统错误");
				}
			}
		});
	})

	$('#tab3').on('click', '.checked', function(e) {
		var orderId = e.currentTarget.dataset.orderId;
		$.ajax({
			url : modifyOrderStatusUrl,
			type : 'POST',
			data : {
				orderId : orderId,
				operationType : "cancel"
			},
			success : function(data) {
				if (data.success) {
					$.toast("确认收货成功")
					setTimeout(function() {
						window.location.reload();
					}, 450);
				} else {
					$.toast("系统错误");
				}
			}
		});
	})

});