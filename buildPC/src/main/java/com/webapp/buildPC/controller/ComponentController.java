package com.webapp.buildPC.controller;

import com.webapp.buildPC.domain.Component;
import com.webapp.buildPC.service.interf.ComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addComponent")
    public String addComponent() {
        return "The page for admin";
    }

    @PostMapping("/buyComponent")
    public String buyComponent() {
        return "The page for user";
    }
}
