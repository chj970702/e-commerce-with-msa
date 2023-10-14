package com.example.catalogservice.controller;

import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.CatalogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;
    private final Environment env;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It is working in catalog-service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<CatalogResponse>> getCatalogs() {
        return ResponseEntity.ok().body(catalogService.getCatalogs());
    }
}
