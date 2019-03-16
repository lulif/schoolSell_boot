$(function() {
	var productName = '';

	getList();
	getProductSellDailyList()

	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.productbuycheck-wrap').empty();
		getList();
	});

	function getList() {
		var listUrl = '/schoolSell_boot/shopadmin/listuserproductmapsbyshop?pageIndex=1&pageSize=999&'
				+ '&productName=' + productName;
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var userProductMapList = data.userProductMapList;
				var tempHtml = '';
				userProductMapList.map(function(item, index) {
					tempHtml += ''
							+ '<div class="row row-productbuycheck">'
							+ '<div class="col-30">'
							+ item.product.productName
							+ '</div>'
							+ '<div class="col-20 productbuycheck-time" >'
							+ new Date(item.createTime)
									.Format('yyyy-MM-dd：hh:mm:ss') + '</div>'
							+ '<div class="col-20">' + item.user.userName
							+ '</div>' + '<div class="col-10">' + item.point
							+ '</div>' + '<div class="col-20">'
							+ item.operator.userName + '</div>' + '</div>';
					console.log(item.operator);
				});
				$('.productbuycheck-wrap').html(tempHtml);
			}
		});
	}
	/*
	 * 获取7天的销量
	 */
	function getProductSellDailyList() {
		var listProductSellDailyUrl = '/schoolSell_boot/shopadmin/listproductselldailyinfobyshop';
		$.getJSON(listProductSellDailyUrl, function(data) {
			if (data.success) {
				// 初始化echarts
				var myChart = echarts.init(document.getElementById('chart'));
				var option = generateStaticEchartPart();
				option.legend.data = data.legendData;
				option.xAxis = data.xAxis;
				option.series = data.series;
				myChart.setOption(option);
			}
		});
	}

	/*
	 * 模拟数据
	 */function generateStaticEchartPart() {
		var option = {
			// 提示框，鼠标悬浮交互时的信息提示
			tooltip : {
				trigger : 'axis',// 柱状图
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			// 直角坐标系内绘图网格
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},

			yAxis : [ {
				type : 'value'
			} ],
			/** ********************************** */
			// 图例
			legend : {
				// 图例内容数组，数组为string，每一项代表一个系列的name
				data : [ '茉香奶茶', '拿铁咖啡', '百加得' ]
			},
			// 直角坐标系横轴数组，每一项代表一条横轴坐标轴
			xAxis : [ {
				// 类项目：需要指定类目列表，
				type : 'category',
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			} ],
			series : [ {
				name : '茉香奶茶',
				type : 'bar',
				data : [ 120, 132, 101, 134, 290, 230, 220 ]
			}, {
				name : '拿铁咖啡',
				type : 'bar',
				data : [ 60, 72, 71, 74, 190, 130, 110 ]
			}, {
				name : '百加得',
				type : 'bar',
				data : [ 62, 82, 91, 84, 109, 110, 120 ]
			} ]
		};
		return option;
	}
});