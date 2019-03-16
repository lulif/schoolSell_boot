$(function() {
    var loading = false;
    var maxItems = 999;
    var pageSize = 5;
    var listUrl = '/schoolSell_boot/frontend/listusershopmapsbycustomer';

    var pageIndex = 1;
    var shopName = '';

    addItems();
    
    function addItems() {
        // 生成新条目的HTML
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize=' + pageSize + '&shopName=' + shopName;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                maxItems = data.count;
                var html = '';
                data.userShopMapList.map(function (item, index) {
                    html += ''
                    +   '<div class="card" data-shop-id="'+ item.shop.shopId +'">'
                    +       '<div class="card-header">'+ item.shop.shopName +'</div>'
                    +       '<div class="card-content">'
                    +            '<div class="list-block media-list">'
                    +                '<ul>'
                    +                    '<li class="item-content">'
                    +                       '<div class="item-inner">'
                    +                           '<div class="item-subtitle">本店积分:'+ item.point +'</div>'
                    +                       '</div>'
                    +                   '</li>'
                    +               '</ul>'
                    +           '</div>'
                    +       '</div>'
                    +       '<div class="card-footer">'
							                    + '<p class="color-gray">更新时间：'
							+ new Date(item.createTime).Format("yyyy-MM-dd")
							+'</p>'
                    +       '</div>'
                    +   '</div>';
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
   

    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading) return;
        addItems();
    });

    $('#search').on('change', function (e) {
        shopName = e.target.value;
        $('.list-div').empty();
        pageIndex = 1;
        addItems();
    });

    
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });

    $.init();
});
