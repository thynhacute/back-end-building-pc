package com.webapp.buildPC.domain.Transaction;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListComponentAddToCart {
    @ApiModelProperty(example = "PhuongThai")
    private String userID;
    @ApiModelProperty(example = "ComponentID&Amount")
    private List<ListComponent> listComponentList;
}
