package com.zto.crawler.usertools;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class ExcelWriter {
    public static void main(String[] args) {
        String fileName = "C:\\Users\\dearc\\Downloads\\chromedriver_win32\\demo_2.xlsx";
        EasyExcel.write(fileName, Bill.class).sheet("模板").doWrite(data());
    }

    private static List<Bill> data() {
        List<Bill> list = new ArrayList<Bill>();
        for (int i = 0; i < 10; i++) {
            Bill data = new Bill();
            data.setBillCode(Integer.toString(i));
            list.add(data);
        }
        return list;
    }
}
