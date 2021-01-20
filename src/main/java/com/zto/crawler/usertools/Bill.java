package com.zto.crawler.usertools;

import com.alibaba.excel.annotation.ExcelProperty;

public class Bill {
    @ExcelProperty(value = "运单号", index = 0)
    private String billCode;

    @ExcelProperty(value = "姓名", index = 1)
    private String name;

    @ExcelProperty(value = "手机", index = 2)
    private String mobile;

    @ExcelProperty(value = "地址", index = 3)
    private String address;

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
