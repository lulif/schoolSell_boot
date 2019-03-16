$(function() {
	var listUrl = '/schoolSell_boot/shopadmin/listshopauthmapsbyshop?pageIndex=1&pageSize=999';
	var modifyUrl = '/schoolSell_boot/shopadmin/modifyshopauthmap';
	getList();

	function getList() {
		$
				.getJSON(
						listUrl,
						function(data) {
							if (data.success) {
								var shopauthList = data.shopAuthMapList;
								var tempHtml = '';
								shopauthList
										.map(function(item, index) {
											var textOp = '恢复';
											var contraryStatus = 0;
											if (item.enableStatus == 1) {
												textOp = '删除';
												contraryStatus = 0;
											} else {
												contraryStatus = 1;
											}

											if (item.titleFlag != 0) {
												tempHtml += ''
														+ '<div class="row row-shopauth">'
														+ '<div class="col-40">'
														+ item.employee.userName
														+ '</div>'
														+ '<div class="col-20">'
														+ item.title
														+ '</div>'
														+ '<div class="col-40">'
														+ '<a href="#" class="edit" data-employee-id="'
														+ item.employee.userId
														+ '" data-auth-id="'
														+ item.shopAuthId
														+ '">编辑</a>'
														+ '<a href="#" class="status" data-employee-id="'
														+ item.employee.userId
														+ '" data-auth-id="'
														+ item.shopAuthId
														+ '" data-status="'
														+ contraryStatus
														+ '">'
														+ textOp
														+ '</a>'
														+ '</div>' + '</div>';
											} else {
												tempHtml += ''
														+ '<div class="row row-shopauth">'
														+ '<div class="col-40">'
														+ item.employee.userName
														+ '</div>'
														+ '<div class="col-20">'
														+ item.title
														+ '</div>'
														+ '<div class="col-40">'
														+ '<span>不可操作</span>'
														+ '</div>' + '</div>';
											}
										});
								$('.shopauth-wrap').html(tempHtml);
							}
						});
	}

	$('.shopauth-wrap')
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							window.location.href = '/schoolSell_boot/shopadmin/shopauthoperation?shopAuthId='
									+ e.currentTarget.dataset.authId;
						} else if (target.hasClass('status')) {
							changeStatus(e.currentTarget.dataset.authId,
									e.currentTarget.dataset.status);
						}
					});

	function changeStatus(id, status) {
		var shopAuth = {};
		shopAuth.shopAuthId = id;
		shopAuth.enableStatus = status;
		$.confirm('确定么?', function() {
			$.ajax({
				url : modifyUrl,
				type : 'POST',
				data : {
					shopAuthMapStr : JSON.stringify(shopAuth),
					statusChange : true,
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('操作成功！');
						getList();
					} else {
						$.toast('操作失败！');
					}
				}
			});
		});
	}

});