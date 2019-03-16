$(function() {
	var awardName = '';

	getList();
	function getList() {
		var listUrl = '/schoolSell_boot/shopadmin/listuserawardmapsbyshop?pageIndex=1&pageSize=999&&awardName=' + awardName;
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var userAwardMapList = data.userAwardMapList;
				var tempHtml = '';
				userAwardMapList.map(function(item, index) {
					tempHtml += ''
							+ '<div class="row row-awarddeliver">'
							+ '<div class="col-30">'
							+ item.award.awardName
							+ '</div>'
							+ '<div class="col-20 awarddeliver-time">'
							+ new Date(item.createTime)
									.Format("yyyy-MM-dd hh:mm:ss") + '</div>'
							+ '<div class="col-20">' + item.user.userName + '</div>'
							+ '<div class="col-10">' + item.point+ '</div>'
							+ '<div class="col-20">' + item.operator.userName + '</div>'
							+ '</div>';
				});
				$('.awarddeliver-wrap').html(tempHtml);
			}
		});
	}

	$('#search').on('input', function(e) {
		awardName = e.target.value;
		$('.awarddeliver-wrap').empty();
		getList();
	});

});