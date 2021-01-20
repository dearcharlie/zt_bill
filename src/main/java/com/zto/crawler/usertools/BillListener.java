package com.zto.crawler.usertools;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.List;

public class BillListener extends AnalysisEventListener<Bill> {

    private List<Bill> billList;

    public BillListener(List<Bill> billList){
        this.billList = billList;
    }

    @Override
    public void invoke(Bill bill, AnalysisContext analysisContext) {
        billList.add(bill);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
