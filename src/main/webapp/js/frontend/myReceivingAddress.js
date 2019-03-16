$(function() {
	var listReceivingAddressUrl = '/schoolSell_boot/frontend/listaddress';
	var modifyToDefaultAddress = '/schoolSell_boot/frontend/modifytodefault';
	var loading = false;
	var maxItems = 999;
	// 一页几项
	var pageSize = 10;
	// 第几页
	var pageIndex = 1;

	listAddress();

	function listAddress() {
		loading = true;
		var listUrl = listReceivingAddressUrl + '/' + pageIndex + '/'
				+ pageSize;
		$
				.getJSON(
						listUrl,
						function(data) {
							if (data.success) {
								maxItems = data.count;
								var html = '';
								data.receAddressList
										.map(function(item, index) {
											html += ' <div class="card" >'
													+ '<div class="card-content">'
													+ ' <div class="new-item-badge" data-address-id="'
													+ item.addressId
													+ '" style="background:'
											if (item.priority == 1) {
												html += 'red;">默认地址';
											} else {
												html += 'gray;">设为默认';
											}
											html += '</div>'
													+ '<div class="card-content-inner">'
													+ '<span style="font-size:18px;font-weight:400">'
													+ item.contactName
													+ '&nbsp;&nbsp;&nbsp;&nbsp;'
													+ item.contactPhone
													+ '</span>'
													+ '<span id="modify" class="icon icon-edit pull-right" data-address-id="'
													+ item.addressId
													+ '" style="color:red;font-size:20px;margin-top:-15px"></span><br><span style="color:gray">'
													+ item.location
													+ '&nbsp;&nbsp;'
													+ item.locationDetails
													+ '</span> <span class="icon icon-remove pull-right" id="remove" data-address-id="'
													+ item.addressId
													+ '" style="font-size:15px;margin-left:-20px"></span></div></div></div>';
										});

								$('.list-div').append(html);
								var total = $('.list-div .card').length;
								if (total >= maxItems) {
									$('.infinite-scroll-preloader').hide();
								} else {
									$('.infinite-scroll-preloader').show();
								}
								pageIndex += 1;
								loading = false;
								$.refreshScroller();
							}
						});
	}

	$('.list-div').on('click', '.new-item-badge', function(e) {
		var addressId = e.currentTarget.dataset.addressId;
		console.log(addressId);
		$.ajax({
			url : modifyToDefaultAddress,
			type : 'POST',
			data : {
				'addressId' : addressId
			},
			success : function() {
				window.location.reload();
			}
		});
	});

	$('.list-div')
			.on(
					'click',
					'#modify',
					function(e) {
						var addressId = e.currentTarget.dataset.addressId;
						window.location.href = '/schoolSell_boot/frontend/addreceivingaddress?addressId='
								+ addressId + '&modify=1';

					});

	$('.list-div').on('click', '#remove', function(e) {
		var addressId = e.currentTarget.dataset.addressId;
		$.ajax({
			url : '/schoolSell_boot/frontend/removeaddress',
			type : 'POST',
			data : {
				'addressId' : addressId
			},
			success : function(data) {
				if (data.success) {
					$.toast('删除成功！')
					setInterval(function() {
						window.location.reload();
					}, 500);
				} else {
					$.toast('内部错误！');
				}
			}
		})
	});

	$("#down").on('click', function() {
		window.location.href = "/schoolSell_boot/frontend/addreceivingaddress"
	});

});