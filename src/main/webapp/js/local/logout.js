$(function() {
	$('#log-out').click(function() {
		$.ajax({
			url : '/schoolSell_boot/local/logout',
			type : "post",
			async : false,
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					window.location.href = '/schoolSell_boot/local/login';
				}
			},
			error : function(data, error) {
				$.toast(error);
			}
		})
	})
})