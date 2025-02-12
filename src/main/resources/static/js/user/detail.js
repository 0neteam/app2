// 직원 삭제 함수
function deleteEmployee(button) {
	
	var userNo = button.getAttribute('data-user-no'); // 삭제 대상 userNo	
	
    // 삭제 전에 확인 메시지 표시
    const isConfirmed = window.confirm('정말로 이 직원을 삭제하시겠습니까?');
	
	// 사용자가 확인을 누르면 삭제 진행
	if (isConfirmed) {
		var _csrf = document.querySelector('input[name="_csrf"]').value;
		var params = { userNo, _csrf }
		console.log(params);
	
		$.ajax({
			url: '/user/delete',
			method: 'POST',
			data: params
		}).done(data => {
			if (data.status === "OK") {					
				alert("삭제 완료");				
				window.location.href = '/user/list';
			} else {					
				alert("삭제 오류");
			}
		}).fail(error => {
			console.log(error);
		});
	}
	
	
}