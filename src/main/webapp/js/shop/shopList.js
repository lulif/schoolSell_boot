$(function() {
	getList();
	// 根据用户信息获取店铺列表
	function getList(e) {
		$.ajax({
			url : "/schoolSell_boot/shopadmin/getshoplist",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					// 渲染shopList
					handleList(data.shopList);
					// 显示用户名
					handleUser(data);
				}
			}
		});
	}

	function handleList(data) {
		var html = '';
		data.map(function(item, index) {
			html += '<div class="row row-shop"><div class="col-40">'
					+ item.shopName + '</div><div class="col-40">'
					+ shopStatus(item.enableStatus)
					+ '</div><div class="col-20">'
					+ goShop(item.enableStatus, item.shopId) + '</div></div>'
		});
		$('.shop-wrap').html(html);
	}

	function handleUser(data) {
		$('#user-name').text(data.user.userName);
	}

	function shopStatus(status) {
		if (status == 0) {
			return "审核中";
		} else if (status == -1) {
			return "店铺非法";
		} else if (status == 1) {
			return "审核通过";
		}
	}
	
	function goShop(enableStatus, shopId) {
		if (enableStatus == 1) {
			return '<a href="/schoolSell_boot/shopadmin/shopmanager?shopid='+ shopId +'">进入</a>'
		} else {
			return '<a href="" style="color:gray">暂无</a>';
		}
	}
});