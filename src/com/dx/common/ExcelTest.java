package com.dx.common;

import com.dx.entity.StoreRateBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.view.brush.DoubleStroke;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 16/6/22.
 */
public class ExcelTest {
    private static final String[] CURRENCY={
            "USD", /*美元 USD*/
            "JPY", /*日元 JPY*/
            "AUD", /*澳大利亚元 AUD*/
            "EUR", /*欧元 EUR*/
            "HKD", /*港币 HKD*/
            "AED", /*阿联酋迪拉姆 AED*/
            "DKK", /*丹麦克朗 DKK*/
            "CHF", /*瑞士法郎 CHF*/
            "CAD", /*加拿大元 CAD*/
            "BRL", /*巴西里亚尔 BRL*/
            "GBP", /*英镑 GBP*/
            "INR", /*印度卢比 INR*/
            "IDR", /*印尼卢比 IDR*/
            "ZAR", /*南非兰特 ZAR*/
            "TWD", /*新台币 TWD*/
            "THB", /*泰国铢 THB*/
            "SGD", /*新加坡元 SGD*/
            "SEK", /*瑞典克朗 SEK*/
            "RUB", /*卢布 RUB*/
            "PHP", /*菲律宾比索 PHP*/
            "NZD", /*新西兰元 NZD*/
    };

    public static void main(String args[]){
        int youNumber = 122;
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        String str = String.format("%06d", youNumber);
        System.out.println(str);
    }

}
