package com.rakuten.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rakuten.app.model.ShopInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommonUtils {

    private static final Set<String> shopSet = new HashSet<>();
    private static final Set<String> duplicateShopSet = new HashSet<>();
    private static final Set<String> invalidDateSet = new HashSet<>();
    private static final Set<String> futureDateExceptionSet = new HashSet<>();
    private static final Set<String> invalidObject = new HashSet<>();


    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String SHOP = "shop";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";

    public static void clear() {
        shopSet.clear();
        duplicateShopSet.clear();
        invalidDateSet.clear();
        futureDateExceptionSet.clear();
        invalidObject.clear();
    }

    public static void createShopInfoObject(ObjectNode shopInfoJson, List<ShopInfo> shopInfoList) {

        try {

            String shop = shopInfoJson.get(SHOP).asText();

            if(shop.isEmpty()) {
                throw new Exception();
            }

            if(!shopSet.add(shop)) {
                duplicateShopSet.add(shop);
            }

            String startDate = shopInfoJson.get(START_DATE).asText();
            startDate = formatDate(startDate);
            shopInfoJson.put(START_DATE, startDate);

            String endDate = shopInfoJson.get(END_DATE).asText();
            endDate = formatDate(endDate);
            shopInfoJson.put(END_DATE, endDate);

            LocalDate startDateValue = LocalDate.parse(startDate);
            LocalDate endDateValue = LocalDate.parse(endDate);

            if(endDateValue.compareTo(LocalDate.now()) <= 0) {
                futureDateExceptionSet.add(shop);
            } else if(endDateValue.compareTo(startDateValue) <= 0) {
                invalidDateSet.add(shop);
            }

            ShopInfo shopInfo = objectMapper.readValue(shopInfoJson.toString(), ShopInfo.class);
            shopInfoList.add(shopInfo);

        } catch (Exception e) {
            String[] lineArray = new String[] {
                    shopInfoJson.get(SHOP).asText(),
                    shopInfoJson.get(START_DATE).asText(),
                    shopInfoJson.get(END_DATE).asText()
            };
            invalidObject.add("[" + String.join(",", lineArray) + "]");
        }
    }

    private static String formatDate(String dateString) throws Exception {

        if(dateString.isEmpty() || !dateString.matches("\\d{8}")){
            throw new Exception();
        }

        String[] tempArray = dateString.split("");
        String result = "";
        for(int i=0, length=tempArray.length; i<length; i++) {
            result += tempArray[i];

            if(i == 3 || i == 5) {
                result += '-';
            }
        }

        return result;
    }

    public static Set<String> getInvalidObject() {
        return invalidObject;
    }

    public static Set<String> getDuplicateShopInfo() {
        return duplicateShopSet;
    }

    public static List<String> getErrors() {
        String message = "";
        List<String> errorList = new ArrayList<>();

        if(invalidDateSet.size() > 0) {
            message = AppConstants.INVALID_DATE_MESSAGE.replace(AppConstants.SHOP_TEMPLATE, String.join(", ", invalidDateSet));
            errorList.add(message);
        }

        if(futureDateExceptionSet.size() > 0) {
            message = AppConstants.FUTURE_DATE_ERROR_MESSAGE.replace(AppConstants.SHOP_TEMPLATE, String.join(", ", futureDateExceptionSet));
            errorList.add(message);
        }

        return errorList;
    }

}
