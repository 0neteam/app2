document.addEventListener("DOMContentLoaded", function () {
	const table = document.querySelector("table"); // 테이블을 선택

	const saveBtn = document.querySelector(".modal-content button[type='submit']"); // 추가버튼 > 저장버튼

	const itemNameInput = document.getElementById("item-name");

	const itemQuantityInput = document.getElementById("item-qty");
	    const itemPriceInput = document.getElementById("item-price");
	 
	const trToggleEvent = tr => {
		tr.find(".btn_group button").each((i, e) => {
			$(e).toggleClass("d-none");
		} );
		// 입력 활성화 이벤트
		tr.find("td").each((i, e) => {
		   if(i == 1 || i == 2 || i == 3) {
			   $(e).find("span").toggleClass("d-none");
			   $(e).find("input").toggleClass("d-none");
		   }
	   } );
	}

	 // 수정 버튼 클릭 시 이벤트 처리
	 table.addEventListener("click", function (event) {
	     if (event.target && event.target.matches(".edit-btn")) {
	         const $tr = $( event.target.closest("tr") );
			 trToggleEvent($tr);
			 // 취소 이벤트
	         $tr.find(".btn_group button.cancel-btn").off().on("click", () => {
				trToggleEvent($tr);
			 });
			 
	     }

	     // 저장 버튼 클릭 시 이벤트 처리
	     if (event.target && event.target.matches(".save-btn")) {
			 const isConfirmed = window.confirm('저장 하시겠습니까?');

			 if(isConfirmed) {
				const row = event.target.closest("tr");

				const itemCode = row.querySelector('.prod-itemCode').getAttribute('data-item-code');
				const name = row.querySelector('.prod-name-input').value;
				const qty = row.querySelector('.prod-qty-input').value;
				const price = row.querySelector('.prod-price-input').value;

				var _csrf = document.querySelector('input[name="_csrf"]').value;
				var params = { itemCode, name, qty, price, _csrf };

				$.ajax({
					url: '/prod/update',
					method: 'POST',
					data: params,
				}).done(data => {
					if (data.status === "OK") {
						alert("수정 완료");
						window.location.reload();
					} else {
						alert("수정 오류");
					}
				}).fail(error => {
					console.log(error);
				});
			}
	     }

	     // 삭제 버튼 클릭 시 이벤트 처리
	     if (event.target && event.target.matches(".delete-btn")) {
	         const isConfirmed = window.confirm('삭제 하시겠습니까?');
	         
	         if (isConfirmed) {
				 var itemCode = event.target.dataset.itemCode;
				 var _csrf = document.querySelector('input[name="_csrf"]').value;
				 var params = { itemCode, _csrf };
				 
	             $.ajax({
	                 url: '/prod/delete',
	                 method: 'POST',
	                 data: params,
	             }).done(data => {
	                 if (data.status === "OK") {
	                     alert("삭제 완료");
	                     window.location.reload();
	                 } else {
	                     alert("삭제 오류");
	                 }
	             }).fail(error => {
	                 console.log(error);
	             });
	         }
	     }
	 });

	// "저장" 버튼 클릭 시 실행
	 saveBtn.addEventListener("click", function (event) {
		
		event.preventDefault();  // 기본 폼 제출을 막음
		
		var _csrf = document.querySelector('input[name="_csrf"]').value;
		
		
	     const itemName = itemNameInput.value;
	     const itemQuantity = itemQuantityInput.value;
	     const itemPrice = itemPriceInput.value;

	     if (saveBtn.textContent === "저장") {
	         // 새 품목 추가
	         const formData = new FormData();
			formData.append('_csrf', _csrf); 
			
	         formData.append("name", itemName); // 변수 이름 수정
	         formData.append("qty", itemQuantity); // 변수 이름 수정
	         formData.append("price", itemPrice); // 변수 이름 수정

	         // AJAX 요청을 사용하여 서버에 폼 데이터 전송 
			$.ajax({
				url: '/prod/add',
				method: 'POST',
				data: formData,  // 폼 데이터
	            processData: false,  // FormData를 사용할 때는 false로 설정
	            contentType: false,  // FormData를 사용할 때는 false로 설정
			}).done(data => {
				
				if (data.status === "OK") {					
					alert("추가 완료");
					window.location.href = '/prod';
				} else {					
					alert("추가 오류");					
				}
			}).fail(error => {
				console.log(error);
			});
			 
	     }
	 });

	

    // 검색 버튼 처리
    document.getElementById("search-btn").addEventListener("click", function(e) {
        e.preventDefault();

        const searchType = document.getElementById("search-type").value;
        const searchInput = document.getElementById("search-input").value;

        var _csrf = document.querySelector('input[name="_csrf"]').value;
        var params = { searchType, searchInput, _csrf };

        $.ajax({
            url: '/prod/search',
            method: 'POST',
            data: params,
        }).done(data => {
            const tableBody = document.querySelector("table tbody");
            tableBody.innerHTML = "";

            data.forEach(prod => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td class="prod-itemCode" data-item-code="${prod.itemCode}">${prod.itemCode}</td>
                    <td>
                        <span class="prod-name">${prod.name}</span>
                        <input type="text" class="prod-name-input" value="${prod.name}" style="display:none;">
                    </td>
                    <td>
                        <span class="prod-qty">${prod.qty}</span>
                        <input type="number" class="prod-qty-input" value="${prod.qty}" style="display:none;">
                    </td>
                    <td>
                        <span class="prod-price">${prod.price}</span>
                        <input type="number" class="prod-price-input" value="${prod.price}" style="display:none;">
                    </td>
                    <td>${prod.regDate}</td>
                    <td>
                        <button class="btn btn-primary btn-sm edit-btn" data-item-code="${prod.itemCode}">
                            <i class="fas fa-edit"></i> 수정
                        </button>
						<!-- 저장 버튼 (수정 완료 후 저장) -->
		                <button class="btn btn-success btn-sm save-btn" style="display:none;" th:data-item-code="${prod.itemCode}">
		                    <i class="fas fa-save"></i> 저장
		                </button>
                        <button class="btn btn-danger btn-sm delete-btn" data-item-code="${prod.itemCode}">
                            <i class="fas fa-trash-alt"></i> 삭제
                        </button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        }).fail(error => {
            console.log(error);
        });
    });
});
