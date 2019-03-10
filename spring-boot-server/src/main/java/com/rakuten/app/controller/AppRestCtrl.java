package com.rakuten.app.controller;

import com.rakuten.app.exception.AppException;
import com.rakuten.app.service.ShopInfoService;
import com.rakuten.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AppRestCtrl {

    @Autowired
    private ShopInfoService shopInfoService;

    @PostMapping("/upload")
    public ResponseEntity uploadEmployeeFile(@RequestParam("file") MultipartFile shopCSV) throws Exception {

        if (!shopCSV.getOriginalFilename().endsWith(".csv")) {
            throw new AppException(AppConstants.INVALID_FILE);
        }

        if (shopCSV.isEmpty()) {
            throw new AppException(AppConstants.EMPTY_FILE);
        }

        shopInfoService.uploadFile(shopCSV);

        return ResponseEntity.ok(AppConstants.FILE_UPLOAD_MSG);
    }

    @GetMapping("/shop-infos")
    public ResponseEntity getShopInfos() {
        return ResponseEntity.ok(shopInfoService.getActiveShops());
    }

}
