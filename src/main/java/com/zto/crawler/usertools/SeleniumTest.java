package com.zto.crawler.usertools;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeleniumTest {
    public static void main(String[] args) throws InterruptedException {
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
        webDriver.get("file:///C:/Users/dearc/Desktop/%E4%B8%AD%E5%A4%A9%E7%B3%BB%E7%BB%9F%E5%BF%AB%E4%BB%B6%E8%B7%9F%E8%B8%AA1.html");

        try {
            // 等待订单信息表格出现
            new WebDriverWait(webDriver, 1).until(new ExpectedCondition<WebElement>() {
                @NullableDecl
                @Override
                public WebElement apply(@NullableDecl WebDriver webDriver) {
                    return webDriver.findElement(By.cssSelector(".z_table"));
                }
            });

            // 获取表格
            WebElement orderInfo = webDriver.findElementByCssSelector(".z_table tbody tr");
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
            } else {
                System.out.println("NO MATCH");
            }
        }catch (Exception e){
            System.out.println("dddddddddddddddddddddddddddddddddd");
//            webDriver.findElementById("ztosec-msg-modal-btn").click();
            webDriver.findElement(By.cssSelector(".ztosec-msg-modal-btn")).click();

            Thread.sleep(10000);
        }

        // 退出浏览器
        webDriver.quit();
    }
}
