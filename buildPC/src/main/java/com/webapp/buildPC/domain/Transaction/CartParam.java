package com.webapp.buildPC.domain.Transaction;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartParam {
    @ApiModelProperty(example = "PhuongThai")
    private String userID;

    @ApiModelProperty(example = "1")
    private int componentID;

    @ApiModelProperty(example = "")
    private String productID;

    @ApiModelProperty(example = "2")
    private int amount;
}
