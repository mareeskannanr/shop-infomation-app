package com.rakuten.app;

import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    private final static String FILE = "file";
    private final static String PLAIN_TEXT = "text/plain";
    private final static String CSV_TEXT = "text/csv";
    private final static String TEXT_FILE_NAME = "shop-infos.txt";
    private final static String CSV_FILE_NAME = "shop-infos.csv";


    public static MockMultipartFile createInvalidFile() {
        return new MockMultipartFile(FILE, TEXT_FILE_NAME, PLAIN_TEXT, "".getBytes());
    }

    public static MockMultipartFile createEmptyFile() {
        return new MockMultipartFile(FILE, CSV_FILE_NAME, PLAIN_TEXT, "".getBytes());
    }

    public static MockMultipartFile createFile(String type) {
        List<String> lineList = new ArrayList<>();
        lineList.add("shop,start_date,end_date");

        switch (type) {
            case "Invalid": {
                lineList.add("1,20190101,20191111");
                lineList.add(",,");
                lineList.add("2,1234656,4322");
                break;
            }
            case "Duplicate": {
                lineList.add("1,20190101,20191111");
                lineList.add("1,20190101,20191111");
                lineList.add("2,20190101,20191111");
                break;
            }
            case "Incorrect_Date": {
                lineList.add("1,20190101,20191111");
                lineList.add("2,20190711,20190701");
                lineList.add("3,20190101,20191111");
                break;
            }
            case "Future_Date": {
                lineList.add("1,20190101,20191111");
                lineList.add("2,20180711,20190101");
                lineList.add("3,20190101,20191111");
                break;
            }
            case "Valid_Data": {
                lineList.add("1,20190101,20191111");
                lineList.add("2,20180711,20190501");
                lineList.add("3,20190101,20191111");
                break;
            }
        }

        String content = String.join("\n", lineList);
        return new MockMultipartFile(FILE, CSV_FILE_NAME, CSV_TEXT, content.getBytes());
    }
}
