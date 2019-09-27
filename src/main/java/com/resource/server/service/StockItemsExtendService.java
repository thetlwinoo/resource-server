package com.resource.server.service;

import com.resource.server.service.dto.PhotosDTO;

import java.security.Principal;

public interface StockItemsExtendService {
    PhotosDTO addPhotos(PhotosDTO photosDTO);
    PhotosDTO updatePhotos(PhotosDTO photosDTO);
}
