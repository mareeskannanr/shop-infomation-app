package com.rakuten.app.service;

import com.rakuten.app.model.ShopInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShopInfoService {

    void uploadFile(MultipartFile shopCSV) throws Exception;

    List<ShopInfo> getActiveShops();

}