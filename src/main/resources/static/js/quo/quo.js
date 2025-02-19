$(() => {

    $("#category").on("change", () => {
        // SELECT 기능
        var index = $("#category option").index( $("#category option:selected") );
        const categorys = [
			'상태값을 입력하세요', 
			'숫자를 입력하세요', 
			'업체명을 입력하세요', 
			'ex)yyyy-mm-dd', 
			'ex)yyyy-mm-dd'];
        $("input[name='search']").attr("placeholder", categorys[index]);
    });

    $("tbody tr").on("click", function() {
        var quoStatus = $(this).attr("data-quoStatus");
        var quoDate = $(this).attr("data-quoDate");
        var dstn = $(this).attr("data-dstn");
        var bizNum = $(this).attr("data-bizNum");
        var bizName = $(this).attr("data-bizName");
        var deliDate = $(this).attr("data-deliDate");
        var adr = $(this).attr("data-adr");
        var quoNo = $(this).attr("data-quoNo");
		
        var orderNo = $(this).attr("data-orderNo");
        var _csrf = document.querySelector('input[name="_csrf"]').value;
        var params = {quoNo, _csrf};

		var totalPrice = 0;
		
        $.ajax({
            url: '/quo',
            method: 'POST',
            data: params
        }).done(data => {
			let quoCustomer = `<tbody>
								<tr>
									<th rowspan = "5">공급받는자</th>
									<th>사업자등록번호</th>
									<td>${bizNum}</td>
								</tr>
								<tr>
									<th>상호</th>
									<td>${bizName}</td>
								</tr>
								<tr>
									<th>주소</th>
									<td>${adr}</td>
								</tr></tbody>`;
			let quoDetailTable = 
									`<thead>
                       			 	<tr>
			                            <th>수주번호</th>
			                            <td>${quoNo}</td>
			                            <th>발주번호</th>
			                            <td>${orderNo}</td>
			                            <th>작성일자</th>
			                            <td>${quoDate}</td>
                    				</tr>
									<tr>
			                            <th>물품 도착지</th>
			                            <td colspan="4">${dstn}</td>
									</tr>
								</thead>
								<tbody>
									<tr>
									    <th>품목코드</th>
									    <th>품목명</th>
									    <th>발주수량</th>
									    <th>단가</th>
									    <th>합계</th>
									</tr></thead>`;
			$.each(data, function(index, item){	
				
				totalPrice += (item.qty*item.price);
				quoDetailTable += `<tr>     
										<td>${item.itemCode}</td>
			                            <td>${item.name}</td>
			                            <td>${item.qty}</td>
			                            <td>${item.price}</td>
			                            <td>${item.qty * item.price}</td>
			                        </tr></tbody>`;
				});
			quoDetailTable += `<tfoot>
		                        <tr>
									<th>납기일자</th>
		                            <td>${deliDate}</td>
									<th>총액</th>
		                            <td>${totalPrice}</td>
		                        </tr>
							</tfoot>`;
			
            
            $("#quoCustomer").html(quoCustomer);
            $("#quoDetailTable").html(quoDetailTable);
			$("#delBtn").off().on("click", () => window.location.href = "/quo/del?quoNo="+quoNo);
			if(quoStatus !== "견적취소" && quoStatus !== "견적검토") {
				$("#transpBtn").off().on("click", () => window.location.href = "/transpSendEmail?quoNo="+quoNo);
			}
            $("#supplierModal").modal("show");
        }).fail(error => {
            console.log(error);
        });

    });

});