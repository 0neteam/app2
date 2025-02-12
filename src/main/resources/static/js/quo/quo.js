$(() => {

    $("#category").on("change", () => {
        // SELECT 기능
        var index = $("#category option").index( $("#category option:selected") );
        const categorys = [
			'검색어를 입력하세요', 
			'숫자를 입력하세요', 
			'업체명을 입력하세요', 
			'ex)yyyymmdd', 
			'ex)yyyymmdd', 
			'상태값을 입력하세요'];
        $("input[name='search']").attr("placeholder", categorys[index]);
    });

    $("tbody tr").on("click", function() {
        var quoNo = $(this).attr("data-quoNo");
        var _csrf = document.querySelector('input[name="_csrf"]').value;
        var params = {quoNo, _csrf};

        $.ajax({
            url: '/quo',
            method: 'POST',
            data: params
        }).done(data => {
			console.log(data);
			let  quoCustomer = `<tbody>
								<tr>
									<th rowspan = "5">공급받는자</th>
									<th>사업자등록번호</th>
									<td>${data.bizNum}</td>
								</tr>
								<tr>
									<th>상호</th>
									<td>${data.bizName}</td>
								</tr>
								<tr>
									<th>주소</th>
									<td>${data.adr}</td>
								</tr></tbody>`;
			let  quoSeller = `<tbody>
								<tr>
									<th rowspan = "5">공급자</th>
									<th>사업자등록번호</th>
									<td>220-81-62517</td>
								</tr>
								<tr>
									<th>상호</th>
									<td>제조제조제조업체</td>
								</tr>
								<tr>
									<th>주소</th>
									<td>신촌버티고타워7층</td>
								</tr>
								<tr>
									<th>Tel.</th>
									<td>02-323-3223</td>
								</tr>
								<tr>
									<th>Fax.</th>
									<td>02-323-3224</td>
								</tr></tbody>`;

            let quoDetailTable = `<thead>
									<tr>
			                            <th>물품 도착지</th>
			                            <td colspan="4">${data.dstn}</td>
									</tr>
                       			 	<tr>
			                            <th>견적번호</th>
			                            <td>${data.quoNo}</td>
			                            <th>작성일자</th>
			                            <td>${data.quoDate}</td>
                    				</tr>
								<thead>
								<tbody>
									<tr>
									    <th>품목코드</th>
									    <th>품목명</th>
									    <th>발주수량</th>
									    <th>단가</th>
									    <th>합계</th>
									</tr></thead>
			                        <tr>
			                            <td>${data.itemCode}</td>
			                            <td>${data.name}</td>
			                            <td>${data.qty}</td>
			                            <td>${data.price}</td>
			                            <td>${data.qty * data.price}</td>
			                        </tr>
								</tbody>
								<tfoot>
			                        <tr>
										<th>납기일자</th>
			                            <td>${data.deliDate}</td>
										<th>총액</th>
			                        </tr>
								</tfoot>
								`;
            let tbody = `<tbody>
						<tr>
						    <th>품목코드</th>
						    <th>품목명</th>
						    <th>발주수량</th>
						    <th>단가</th>
						    <th>합계</th>
						</tr></thead>
                        <tr>
                            <td>${data.itemCode}</td>
                            <td>${data.name}</td>
                            <td>${data.qty}</td>
                            <td>${data.price}</td>
                            <td>${data.qty * data.price}</td>
                        </tr></tbody>`;
			let tfoot = `<tfoot>
                        <tr>
							<th>납기일자</th>
                            <td>${data.deliDate}</td>
							<th>총액</th>
                        </tr></tfoot>`;
            $("#quoCustomer").html(quoCustomer);
            $("#quoSeller").html(quoSeller);
            $("#quoDetailTable").html(quoDetailTable);
           
            $("#supplierModal").modal("show");
        }).fail(error => {
            console.log(error);
        });

    });

});