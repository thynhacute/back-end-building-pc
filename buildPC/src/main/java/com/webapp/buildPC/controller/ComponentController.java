package com.webapp.buildPC.controller;

import com.webapp.buildPC.domain.Component;
import com.webapp.buildPC.domain.Transaction.RequestComponentByAttribute;
import com.webapp.buildPC.service.interf.ComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/component")
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/allComponent")
    public ResponseEntity<List<Component>> getAllComponent(){
        return ResponseEntity.ok().body(componentService.getAllComponent());
    }

    @GetMapping("/componentByName")
    public ResponseEntity<List<Component>> getComponentByName(@RequestParam String componentName){
        return ResponseEntity.ok().body(componentService.getComponentByName(componentName));
    }

    @GetMapping("/componentByBrand")
    public ResponseEntity<List<Component>> getComponentByBrand(@RequestParam String brandID){
        return ResponseEntity.ok().body(componentService.getComponentByBrand(brandID));
    }

    @PostMapping("/customComponentByAttribute")
    public ResponseEntity<List<Component>> customComponentByAttribute(@RequestBody RequestComponentByAttribute requestComponentByAttribute){
        return ResponseEntity.ok().body(componentService.customComponentByAttribute(requestComponentByAttribute));
    }

    @PostMapping("/addComponent")
    public ResponseEntity<Component> addComponent(@RequestParam String componentName, @RequestParam("image")  MultipartFile image,
                                                  @RequestParam int price, @RequestParam int amount,@RequestParam String description,
                                                  @RequestParam int brandID, @RequestParam String categoryID) throws IOException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/component/addComponent")
                .toUriString());
        return ResponseEntity.created(uri).body(componentService.addComponent(componentName, price ,amount, image, description, brandID, categoryID));
    }

    @PostMapping("/editComponent")
    public ResponseEntity<Component> editComponent(@RequestBody Component component) throws IOException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/component/editComponent")
                .toUriString());
        return ResponseEntity.created(uri).body(componentService.editComponent(component));
    }

    @PostMapping("/buyComponent")
    public String buyComponent(int componentID) {
        return "helloword";
    }

    @GetMapping("/componentDetail")
    public ResponseEntity<Component> getComponentDetail(@RequestParam int componentID){
        return ResponseEntity.ok().body(componentService.getComponentDetail(componentID));
    }

    @GetMapping("/componentListByCategoryTypeID")
    public ResponseEntity<List<Component>> getComponentListByCategoryTypeID(@RequestParam String categoryTypeID){
        return ResponseEntity.ok().body(componentService.getComponentListByCategoryTypeID(categoryTypeID));
    }


}
