package com.webapp.buildPC.controller;

import com.webapp.buildPC.domain.Attribute;
import com.webapp.buildPC.service.interf.AttributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/attribute")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping("/allAttribute")
    public ResponseEntity<List<Attribute>> getAllAttribute(){
        log.debug("Show the list all brands");
        return ResponseEntity.ok().body(attributeService.getAllAttribute());
    }

}
