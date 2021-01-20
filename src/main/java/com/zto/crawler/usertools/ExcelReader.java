package com.zto.crawler.usertools;


import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public static void main(String[] args) {
        String fileName = "C:\\Users\\dearc\\Downloads\\chromedriver_win32\\demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭

        List<Bill> billList = new ArrayList<>();
        EasyExcel.read(fileName, Bill.class, new BillListener(billList)).sheet().headRowNumber(1).doRead();
    }
}
