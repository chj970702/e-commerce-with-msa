package com.example.catalogservice.service;

import com.example.catalogservice.entity.Catalog;
import com.example.catalogservice.vo.CatalogResponse;

import java.util.List;

public interface CatalogService {
    List<CatalogResponse> getCatalogs();
}
