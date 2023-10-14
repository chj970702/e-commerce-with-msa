package com.example.catalogservice.service;

import com.example.catalogservice.entity.Catalog;
import com.example.catalogservice.entity.CatalogRepository;
import com.example.catalogservice.vo.CatalogResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService{
    private final CatalogRepository catalogRepository;

    @Override
    public List<CatalogResponse> getCatalogs() {
        List<Catalog> catalogs = catalogRepository.findAll();
        List<CatalogResponse> catalogResponses = new ArrayList<>();
        catalogs.forEach(catalog -> {
            System.out.println(catalog.getStock());
            catalogResponses.add(new ModelMapper().map(catalog, CatalogResponse.class));
            System.out.println(catalogResponses.get(0).getStock());
        });

        return catalogResponses;
    }
}
