package com.rakuten.app.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.rakuten.app.exception.AppException;
import com.rakuten.app.exception.ValidationException;
import com.rakuten.app.model.ShopInfo;
import com.rakuten.app.utils.AppConstants;
import com.rakuten.app.utils.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class ShopInfoServiceImpl implements ShopInfoService {

    private final static List<ShopInfo> shopInfoList = new ArrayList<>();

    /***
     * This Methods Reads CSV file and convert each line to ShopInfo Object
     * @param employeeFile
     * @throws Exception
     */
    @Override
    public void uploadFile(MultipartFile employeeFile) throws Exception {

        shopInfoList.clear();

        CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();

        List jsonList = new CsvMapper().readerFor(ObjectNode.class)
                .with(csvSchema.withColumnSeparator(CsvSchema.DEFAULT_COLUMN_SEPARATOR))
                .readValues(employeeFile.getInputStream()).readAll();

        if(jsonList.size() == 0) {
            throw new AppException(AppConstants.EMPTY_FILE);
        }

        CommonUtils.clear();

        jsonList.forEach(item -> CommonUtils.createShopInfoObject((ObjectNode) item, shopInfoList));

        //Invalid lines in the csv file
        Set<String> invalidObject = CommonUtils.getInvalidObject();
        if (invalidObject.size() > 0) {
            String message = AppConstants.INVALID_LINE_MESSAGE.replace(AppConstants.SHOP_TEMPLATE, String.join(", ", invalidObject));
            throw new ValidationException(Arrays.asList(message));
        }

        //Duplicate Validation
        Set<String> duplicateShops = CommonUtils.getDuplicateShopInfo();
        if (duplicateShops.size() > 0) {
            String message = AppConstants.DUPLICATE_SHOP_ERROR_MESSAGE.replace(AppConstants.SHOP_TEMPLATE, String.join(", ", duplicateShops));
            throw new ValidationException(Arrays.asList(message));
        }

        //Date Validations
        List<String> errors = CommonUtils.getErrors();
        if (errors.size() > 0) {
            throw new ValidationException(errors);
        }
    }

    /**
     * This method is used to send List of ShopInfos
     * @return List<ShopInfo>
     */
    @Override
    public List<ShopInfo> getActiveShops() {
        return shopInfoList;
    }

}