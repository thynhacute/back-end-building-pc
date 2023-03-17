package com.webapp.buildPC.domain.Transaction;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListComponent {
    @ApiModelProperty(example = "1")
    private int componentID;
    @ApiModelProperty(example = "2")
    private int amount;
    @ApiModelProperty(example = "noneed")
    private String productID;
}
