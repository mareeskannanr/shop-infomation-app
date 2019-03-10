package com.rakuten.app.utils;

public class AppConstants {

    public final static String INVALID_FILE = "Shop Information File Format is invalid";
    public final static String EMPTY_FILE = "Shop Information File is Empty";
    public final static String FILE_UPLOAD_MSG = "Shop Information uploaded successfully!";
    public final static String INTERNAL_SERVER_ERR_MSG = "Sorry, Something went wrong.";
    public final static String SHOP_TEMPLATE = "{shops}";
    public final static String INVALID_DATE_MESSAGE = "End Date must be greater than Start Date for the following shops : " + SHOP_TEMPLATE;
    public final static String FUTURE_DATE_ERROR_MESSAGE = "End Date must be a Future Date for the following shops : " + SHOP_TEMPLATE;
    public final static String DUPLICATE_SHOP_ERROR_MESSAGE = "Duplicated Shops Information follows : " + SHOP_TEMPLATE;
    public final static String INVALID_LINE_MESSAGE = "Following Lines are invalid to process : " + SHOP_TEMPLATE;

}