package com.webapp.buildPC.controller;

import com.webapp.buildPC.domain.Component;
import com.webapp.buildPC.service.interf.ComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/component")
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/getAllComponent")
    public ResponseEntity<List<Component>> getAllComponent(){
        return ResponseEntity.ok().body(componentService.getAllComponent());
    }

    @GetMapping("/getComponentByName")
    public ResponseEntity<List<Component>> getComponentByName(@RequestParam String componentName){
        return ResponseEntity.ok().body(componentService.getComponentByName(componentName));
    }

    @GetMapping("/getComponentByBrand")
    public ResponseEntity<List<Component>> getComponentByBrand(@RequestParam String brandID){
        return ResponseEntity.ok().body(componentService.getComponentByBrand(brandID));
    }

}
