package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.Product;
import com.webapp.buildPC.domain.Transaction.RequestCustomPC;
import com.webapp.buildPC.domain.Transaction.ResponseProductDetail;

import java.util.List;

public interface ProductService {

    void addProduct(RequestCustomPC requestCustomPC);

    List<ResponseProductDetail> getAllProduct();

    ResponseProductDetail getProductDetail(String productID);
}
