package com.java.prod;


import java.util.Date;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdDTO {
    private int itemCode;
    private String name;
    private int qty;
    private int price;
    private String regDate;
    private String searchType;
    private String searchInput;
}
