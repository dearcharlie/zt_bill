package com.zto.crawler.usertools;

import com.alibaba.excel.EasyExcel;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class UserToolsApplication implements ApplicationRunner {
    public static void main(String[] args) {
//        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "C:\\Users\\dearc\\Downloads\\chromedriver_win32\\chromedriver.exe");
        SpringApplication.run(UserToolsApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String input = args.getOptionValues("input").get(0);
        String output = args.getOptionValues("output").get(0);

        List<Bill> billList = new ArrayList<>();
        EasyExcel.read(input, Bill.class, new BillListener(billList)).sheet().headRowNumber(1).doRead();

        // 谷歌浏览器参数设置
        ChromeOptions options = new ChromeOptions();
        // 禁用 CPU
        options.addArguments("--disable-gpu");
        // 禁用沙盒
        options.addArguments("--no-sandbox");
        // 是否显示 UI 界面
        options.setHeadless(false);

        // 启动浏览器
        ChromeDriver webDriver = new ChromeDriver(options);

        // 访问主页, 没登录的话就会自动跳转登录
        webDriver.get("https://newbill.zt-express.com/prod/index.html");

        // 扫码登录

        // 等待主页元素 60 秒, 循环扫描 id 为 indexUserContent, 如果有就表示登录成功, 登录成功会自动跳到主页
        new WebDriverWait(webDriver, 6000).until(new ExpectedCondition<WebElement>() {
            @NullableDecl
            @Override
            public WebElement apply(@NullableDecl WebDriver webDriver) {
                return webDriver.findElement(By.id("indexUserContent"));
            }
        });

        for (int i = 0; i < billList.size(); i++) {
            Bill bill = billList.get(i);
            System.out.println("========================================================================");
            System.out.println("运单: " + bill.getBillCode() );

            // 获取查询输入框元素, 输入单号
            webDriver.findElementById("billClear").click();
            webDriver.findElementById("searchBillNo").sendKeys(bill.getBillCode());
            // 获取查询按钮, 点击
            webDriver.findElementById("billQuery").click();
            Thread.sleep(1000);
            // 等待"订单信息"按钮出现
            new WebDriverWait(webDriver, 60).until(new ExpectedCondition<WebElement>() {
                @NullableDecl
                @Override
                public WebElement apply(@NullableDecl WebDriver webDriver) {
                    return webDriver.findElement(By.cssSelector("div[data-type=orderInfo]"));
                }
            });
            // 点击订单信息按钮
            webDriver.findElementByCssSelector("div[data-type=orderInfo]").click();

            // 等待订单信息表格出现
            new WebDriverWait(webDriver, 60).until(new ExpectedCondition<WebElement>() {
                @NullableDecl
                @Override
                public WebElement apply(@NullableDecl WebDriver webDriver) {
                    return webDriver.findElement(By.cssSelector(".orderInfo .z_table"));
                }
            });

            // 获取表格
            WebElement orderInfo = webDriver.findElementByCssSelector(".orderInfo .z_table tbody tr");
            // 获取信息
            String user = orderInfo.findElement(By.cssSelector("td:nth-child(5)")).getText();
            String address = orderInfo.findElement(By.cssSelector("td:nth-child(6)")).getText();

            // 按指定模式在字符串查找
            String userRegx = "^(.+?)\\s+(\\d+)\\n.*$";

            // 创建 Pattern 对象
            Pattern userPattern = Pattern.compile(userRegx);

            // 现在创建 matcher 对象
            Matcher userMatcher = userPattern.matcher(user);
            if (userMatcher.find( )) {
                System.out.println("姓名: " + userMatcher.group(1) );
                System.out.println("电话: " + userMatcher.group(2) );
                bill.setName(userMatcher.group(1));
                bill.setMobile(userMatcher.group(2));
            } else {
                System.out.println("NO MATCH");
            }

            // 按指定模式在字符串查找
            String addrRegx = "^(.+?)查看原始地址$";

            // 创建 Pattern 对象
            Pattern addrPattern = Pattern.compile(addrRegx);

            // 现在创建 matcher 对象
            Matcher addrMatcher = addrPattern.matcher(address);
            if (addrMatcher.find( )) {
                System.out.println("地址: " + addrMatcher.group(1) );
                bill.setAddress(addrMatcher.group(1));
            } else {
                System.out.println("NO MATCH");
            }
        }

        // 退出浏览器
        webDriver.quit();

        EasyExcel.write(output, Bill.class).sheet("模板").doWrite(billList);
        System.out.println("                                                                       ");
        System.out.println("                                                                       ");
        System.out.println("采集结束,处理运单:"+billList.size());
        System.out.println("                                                                       ");
        System.out.println("                                                                       ");
    }
}
