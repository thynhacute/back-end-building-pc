package com.webapp.buildPC.controller;

import com.webapp.buildPC.domain.Transaction.RequestCustomPC;
import com.webapp.buildPC.domain.Transaction.ResponseProductDetail;
import com.webapp.buildPC.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody RequestCustomPC requestCustomPC){
        productService.addProduct(requestCustomPC);
    }

    @PostMapping("/updateProduct")
    public void updateProduct(@RequestBody RequestCustomPC requestCustomPC){
        productService.updateProduct(requestCustomPC);
    }

    @GetMapping("/getAllProducts")
    public List<ResponseProductDetail> getAllProducts(){
        return productService.getAllProduct();
    }

    @GetMapping("/getProductDetail")
    public ResponseProductDetail getProductDetail(@RequestParam String productID){
        return productService.getProductDetail(productID);
    }

    @GetMapping("/getProductOfUser")
    public List<ResponseProductDetail> getProductOfUser(@RequestParam String userID){
        return productService.getProductOfUser(userID);
    }

    @PostMapping("/removeProduct")
    public void removeProduct(@RequestParam String productID){
        productService.removeProduct(productID);
    }


}
