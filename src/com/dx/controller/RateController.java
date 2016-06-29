package com.dx.controller;

import com.dx.common.Common;
import com.dx.entity.ResultBean;
import com.dx.entity.StoreRateBean;
import com.dx.entity.SupplerBean;
import com.dx.service.StoreRateService;
import com.dx.service.StoreService;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 16/6/22.
 */
@Controller
public class RateController {

    private static final Logger logger = Logger.getLogger(RateController.class);

    @Autowired
    StoreService storeService;

    @Autowired
    StoreRateService storeRateService;

    private static final String[] CURRENCY = {
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


    @RequestMapping("/sp/downloadTemplat")
    public void downloadTemplat(HttpServletRequest request, HttpServletResponse response) {
        SupplerBean bean = (SupplerBean) request.getSession().getAttribute(Common.SUPPLER_SESSIOIN_BEAN);
        if (bean == null) {
//            rb.setErrCode(Common.ERR_CODE_NOLOGIN_MP);
//            rb.setErrMsg(Common.ERR_MSG_NOLOGIN);
            return;
        }

        List<Map<String, String>> stores = storeService.getStoreList(Integer.parseInt(bean.getId()));
        if (stores == null || stores.isEmpty()) {
            return;
        }

        String templatFilePath = this.getClass().getClassLoader().getResource("/").getPath() + "config" + File.separator + "templat.xlsx";
        logger.debug("templatFilePath=====>" + templatFilePath);
        File templatFile = new File(templatFilePath);
        if (templatFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(templatFile);
                XSSFWorkbook excel = new XSSFWorkbook(fis);
                XSSFSheet sheet = excel.getSheet("网点及汇率报价");
                XSSFCellStyle style=sheet.getRow(0).getCell(0).getCellStyle();
                for (int i = 0; i < stores.size(); i++) {
                    Map<String, String> store = stores.get(i);
                    XSSFRow row = sheet.createRow(i + 1);
                    XSSFCell cell = row.createCell(0);
                    cell.setCellStyle(style);
                    cell.setCellValue(store.get("id"));
                    cell.setCellType(Cell.CELL_TYPE_STRING);

                    cell = row.createCell(1);
                    cell.setCellStyle(style);
                    cell.setCellValue(store.get("name"));
                    cell.setCellType(Cell.CELL_TYPE_STRING);


                    for (int a = 0; a < CURRENCY.length; a++) {
                        cell=row.createCell((a * 2) + 2);
                        cell.setCellStyle(style);
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);

                        cell=row.createCell((a * 2) + 3);
                        cell.setCellStyle(style);
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    }

                }

                String fileName = bean.getName() + "汇率报价.xlsx";
                String userAgent = request.getHeader("User-Agent");
                //针对IE或者以IE为内核的浏览器：
                if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                    fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
                } else {
                //非IE浏览器的处理：
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                }
                response.reset();
                response.setContentType("application/octet-stream; charset=utf-8");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                OutputStream os = response.getOutputStream();
                excel.write(os);
                os.flush();

            } catch (Exception e) {
                logger.error("", e);
            }
        }

    }

    @RequestMapping("/sp/uploadRate")
    @ResponseBody
    public ResultBean uploadRateFile(@RequestParam("file") CommonsMultipartFile upload, HttpServletRequest request) {

        ResultBean rb = new ResultBean();
        SupplerBean bean = (SupplerBean) request.getSession().getAttribute(Common.SUPPLER_SESSIOIN_BEAN);
        if (bean == null) {
            rb.setErrCode(Common.ERR_CODE_NOLOGIN_SP);
            rb.setErrMsg(Common.ERR_MSG_NOLOGIN);
        }

        if (upload == null || upload.isEmpty()) {
            rb.setErrCode(1);
            rb.setErrMsg("上传失败");
            return rb;
        }
        System.out.println("content-type====>" + upload.getFileItem().getContentType());


            XSSFWorkbook excel =null;

        try {
            excel=new XSSFWorkbook(upload.getInputStream());
        }catch (Exception e){
            logger.error("上传文件名称===>"+upload.getOriginalFilename()+ "  ====>"+upload.getContentType(),e);
        }
        if(excel==null){
            rb.setErrCode(1);
            rb.setErrMsg("大神,别啥都上传好不好,我要Excel,麻烦来点诚意!");
            return rb;
        }

        try{
            XSSFSheet sheet = excel.getSheet("网点及汇率报价");
            if (sheet == null) {
                sheet = excel.getSheetAt(1);
            }
            if (sheet == null) {
                rb.setErrCode(1);
                rb.setErrMsg("上传文件格式不正确");
                return rb;
            }

            List<StoreRateBean> rates = new ArrayList<StoreRateBean>();
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row1 = sheet.getRow(rowIndex);

                String storeId = getCellValue(row1.getCell(0));
                String storeName = getCellValue(row1.getCell(1));
                if(StringUtils.isEmpty(storeId) || StringUtils.isEmpty(storeName)){
                    continue;
                }
                if (storeService.isExistStore(storeId, storeName, bean.getId()) == 0) {
                    rb.setErrCode(1);
                    rb.setErrMsg("系统中没有找到编号为【" + storeId + "】,名称为【" + storeName + "】的门店信息,上传失败,请仔细检查并重新上传汇率文件");
                    return rb;
                }

                for (int i = 0; i < CURRENCY.length; i++) {
                    String strRate=getCellValue(row1.getCell((i * 2) + 2));
                    double rate = Double.parseDouble(StringUtils.isEmpty(strRate)?"0":strRate);
                    if (rate != 0.0) {
                        StoreRateBean rateBean = new StoreRateBean();
                        rateBean.setsId(storeId);
                        rateBean.setsName(storeName);
                        rateBean.setBuy("CNY");
                        rateBean.setSell(CURRENCY[i]);
                        rateBean.setRate(String.valueOf(new BigDecimal(rate).setScale(4, BigDecimal.ROUND_DOWN)));
                        rateBean.setSupplierId(String.valueOf(bean.getId()));
                        rates.add(rateBean);
                    }

                    strRate=getCellValue(row1.getCell((i * 2) + 3));
                    rate = Double.parseDouble(StringUtils.isEmpty(strRate)?"0":strRate);
                    if (rate != 0.0) {
                        StoreRateBean rateBean = new StoreRateBean();
                        rateBean.setsId(storeId);
                        rateBean.setsName(storeName);
                        rateBean.setBuy(CURRENCY[i]);
                        rateBean.setSell("CNY");
                        rateBean.setRate(String.valueOf(new BigDecimal(rate).setScale(4, BigDecimal.ROUND_DOWN)));
                        rateBean.setSupplierId(String.valueOf(bean.getId()));
                        rates.add(rateBean);
                    }
                }
            }
            if(rates.size()==0){
                rb.setErrCode(1);
                rb.setErrMsg("大神,你上传了个空的东东,来点诚意好不好");
                return rb;
            }
            if (!storeRateService.addRate(Integer.parseInt(bean.getId()), rates)) {
                rb.setErrCode(1);
                rb.setErrMsg("上传失败");
                return rb;
            }

        } catch (Exception e) {
            logger.error("", e);
            rb.setErrCode(1);
            rb.setErrMsg("上传失败");
            return rb;
        }

        return rb;

    }


    private String getCellValue(XSSFCell cell){
        switch (cell.getCellType()){
            case XSSFCell.CELL_TYPE_NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return String.valueOf(cell.getStringCellValue());
        }
    }
}
