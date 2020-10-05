package com.aiz.notify.util;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.*;

/**
 * @ClassName PDFReport
 * @Description
 * @Author Yao
 * @Date Create in 12:18 上午 2020/10/5
 * @Version 1.0
 */
@Data
@Slf4j
public class PDFReport {
    private final static String REPORT_PATH = System.getProperty("user.dir") + File.separator + "pdf";

    private static BaseFont bf;
    private static Font font = null;
    private static Font font1 = null;
    private static Font font2 = null;
    static{
        try {
            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);//创建字体
            font = new Font(bf, 12);//使用字体
            font1 = new Font(bf, 10);//使用字体
            font2 = new Font(bf, 12, Font.BOLD);//使用字体
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ExportReport(Map params,String code){
        String filePdfPath = null;
        if("02".equals(code)){
            filePdfPath = exportCallReport(params);
        }else if("03".equals(code)){
            filePdfPath = exportPaidReport(params);
        }
        return filePdfPath;
    }

    private void testExportReport() {
        try{
            //Step 1—Create a Document.
            Document document = new Document();
            //Step 2—Get a PdfWriter instance.
            PdfWriter.getInstance(document, new FileOutputStream(REPORT_PATH + File.separator + "createSamplePDF.pdf"));
            //Step 3—Open the Document.
            document.open();
            //Step 4—Add content.
            document.add(new Paragraph("Hello World"));
            //Step 5—Close the Document.
            document.close();
        }catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map initCallData(){
        Map<String, Object> map = new HashMap();
        String consNo = "1000000001";//客户编号
        String consName = "Customer1";//客户名称
        String instlLoc = "CIE星创大厦";//用电地址
        String dataDate = "202008";//电费年月
        String factTotal = "未结清";//实付金额
        map.put("consNo",consNo);
        map.put("consName",consName);
        map.put("instlLoc",instlLoc);
        map.put("dataDate",dataDate);
        map.put("factTotal",factTotal);

        List<Map<String, Object>> meterReadouts = new ArrayList<>();
        for (int count = 0; count < 4; count++) {
            Map<String, Object> meterReadout = new HashMap();
            String serialNo = "095666777931";//电表编号
            String readoutType = "usual";//示数类型
            String lastMonthReadingNumber = "200.00";//上次抄表示数(kWh)
            String readingNumber = "299.00";//本次抄表示数(kWh)
            String ctpt = "1.00";//倍率
            String readingElectricQuantity = "99.00";//抄见电量(kWh)
            String refundElectricQuantity = "0.00";//退补电量(kWh)
            String totalElectric = "99.00";//合计电量(kWh)
            meterReadout.put("serialNo",serialNo);
            meterReadout.put("readoutType",readoutType);
            meterReadout.put("lastMonthReadingNumber",lastMonthReadingNumber);
            meterReadout.put("readingNumber",readingNumber);
            meterReadout.put("ctpt",ctpt);
            meterReadout.put("readingElectricQuantity",readingElectricQuantity);
            meterReadout.put("refundElectricQuantity",refundElectricQuantity);
            meterReadout.put("totalElectric",totalElectric);

            meterReadouts.add(meterReadout);
        }
        map.put("meterReadouts",meterReadouts);

        List<Map<String, Object>> billPrices = new ArrayList<>();
        for (int count = 0; count < 4; count++) {
            Map<String, Object> billPrice = new HashMap();
            String priceNature = "居民生活用电";//用电性质
            String tariffName = "shangbinz-勿删";//电价名称
            String totalEq = "99.00";//结算电量(kWh)
            String unitPrice = "1.36";//单价(RMB)
            String totalCharge = "134.64";//电费(RMB)
            billPrice.put("priceNature",priceNature);
            billPrice.put("tariffName",tariffName);
            billPrice.put("totalEq",totalEq);
            billPrice.put("unitPrice",unitPrice);
            billPrice.put("totalCharge",totalCharge);
            billPrices.add(billPrice);
        }
        map.put("billPrices",billPrices);

        return map;
    }

    public void tesCallReport() {
        Map<String, Object> map = initCallData();
        System.out.println(JSON.toJSONString(map));
        String consNo = (String)map.get("consNo");//客户编号
        String consName = (String)map.get("consName");//客户名称
        String instlLoc = (String)map.get("instlLoc");//用电地址
        String dataDate = (String)map.get("dataDate");//电费年月
        String factTotal = (String)map.get("factTotal");//实付金额
        List<Map<String, Object>> meterReadouts = (List<Map<String, Object>>)map.get("meterReadouts");
        List<Map<String, Object>> billPrices = (List<Map<String, Object>>)map.get("billPrices");
        System.out.print(consNo + " " + consName + " " + instlLoc + " " + dataDate + " " + factTotal);
        for (Map<String, Object> meterReadout : meterReadouts) {
            String serialNo = (String)meterReadout.get("serialNo");//电表编号
            String readoutType = (String)meterReadout.get("readoutType");//示数类型
            String lastMonthReadingNumber = (String)meterReadout.get("lastMonthReadingNumber");//上次抄表示数(kWh)
            String readingNumber = (String)meterReadout.get("readingNumber");//本次抄表示数(kWh)
            String ctpt = (String)meterReadout.get("ctpt");//倍率
            String readingElectricQuantity = (String)meterReadout.get("readingElectricQuantity");//抄见电量(kWh)
            String refundElectricQuantity = (String)meterReadout.get("refundElectricQuantity");//退补电量(kWh)
            String totalElectric = (String)meterReadout.get("totalElectric");//合计电量(kWh)
            System.out.println(JSON.toJSONString(meterReadout));
        }
        for (Map<String, Object> billPrice : billPrices) {
            String priceNature = (String)billPrice.get("priceNature");//用电性质
            String tariffName = (String)billPrice.get("tariffName");//电价名称
            String totalEq = (String)billPrice.get("totalEq");//结算电量(kWh)
            String unitPrice = (String)billPrice.get("unitPrice");//单价(RMB)
            String totalCharge = (String)billPrice.get("totalCharge");//电费(RMB)
            System.out.println(JSON.toJSONString(billPrice));
        }
        //生成pdf
        String pdfPath = new PDFReport().exportCallReport(map);
        System.out.println(pdfPath);
    }

    /**
     * 返回未缴费的pdf附件
     * @param map
     * @return
     */
    private String exportCallReport(Map map) {
        String pdfPath = null;

        Document document = new Document();
        try {
            File dir = new File(REPORT_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString();
            File file = new File(REPORT_PATH + File.separator + uuid + ".pdf");
            if (!file.exists()) {
                file.createNewFile();
            }
            PdfWriter.getInstance(document, new FileOutputStream(REPORT_PATH + File.separator + uuid + ".pdf"));
            document.open();

            //页面头部内容
            Paragraph elements = new Paragraph("账单", font2);
            elements.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(elements);

            PdfPTable table = createPdfPTable(8);

            String consNo = (String)map.get("consNo");//客户编号
            String consName = (String)map.get("consName");//客户名称
            String instlLoc = (String)map.get("instlLoc");//用电地址
            String dataDate = (String)map.get("dataDate");//电费年月
            String factTotal = (String)map.get("factTotal");//实付金额
            //第一行
            PdfPCell cell11 = createPdfPCell("客户编号", font);
            table.addCell(cell11);
            PdfPCell cell12 = createPdfPCell("客户名称", font,2);
            table.addCell(cell12);
            PdfPCell cell13 = createPdfPCell("用电地址", font,3);
            table.addCell(cell13);
            PdfPCell cell14 = createPdfPCell("电费年月", font);
            table.addCell(cell14);
            PdfPCell cell15 = createPdfPCell("实付金额", font);
            table.addCell(cell15);
            //第二行
            PdfPCell cell21 = createPdfPCell(consNo, font);
            table.addCell(cell21);
            PdfPCell cell22 = createPdfPCell(consName, font,2);
            table.addCell(cell22);
            PdfPCell cell23 = createPdfPCell(instlLoc, font,3);
            table.addCell(cell23);
            PdfPCell cell24 = createPdfPCell(dataDate, font);
            table.addCell(cell24);
            PdfPCell cell25 = createPdfPCell(factTotal, font);
            table.addCell(cell25);

            //第三大行
            PdfPCell cell31 = createPdfPCell("电表编号", font);
            table.addCell(cell31);
            PdfPCell cell32 = createPdfPCell("示数类型", font);
            table.addCell(cell32);
            PdfPCell cell33 = createPdfPCell("上次抄表示数(kWh)", font);
            table.addCell(cell33);
            PdfPCell cell34 = createPdfPCell("本次抄表示数(kWh)", font);
            table.addCell(cell34);
            PdfPCell cell35 = createPdfPCell("倍率", font);
            table.addCell(cell35);
            PdfPCell cell36 = createPdfPCell("抄见电量(kWh)", font);
            table.addCell(cell36);
            PdfPCell cell37 = createPdfPCell("退补电量(kWh)", font);
            table.addCell(cell37);
            PdfPCell cell38 = createPdfPCell("合计电量(kWh)", font);
            table.addCell(cell38);

            List<Map<String, Object>> meterReadouts = (List<Map<String, Object>>)map.get("meterReadouts");
            for (Map<String, Object> meterReadout : meterReadouts) {
                String serialNo = (String)meterReadout.get("serialNo");//电表编号
                String readoutType = (String)meterReadout.get("readoutType");//示数类型
                String lastMonthReadingNumber = (String)meterReadout.get("lastMonthReadingNumber");//上次抄表示数(kWh)
                String readingNumber = (String)meterReadout.get("readingNumber");//本次抄表示数(kWh)
                String ctpt = (String)meterReadout.get("ctpt");//倍率
                String readingElectricQuantity = (String)meterReadout.get("readingElectricQuantity");//抄见电量(kWh)
                String refundElectricQuantity = (String)meterReadout.get("refundElectricQuantity");//退补电量(kWh)
                String totalElectric = (String)meterReadout.get("totalElectric");//合计电量(kWh)

                PdfPCell celln1 = createPdfPCell(serialNo, font1);
                table.addCell(celln1);
                PdfPCell celln2 = createPdfPCell(readoutType, font);
                table.addCell(celln2);
                PdfPCell celln3 = createPdfPCell(lastMonthReadingNumber, font);
                table.addCell(celln3);
                PdfPCell celln4 = createPdfPCell(readingNumber, font);
                table.addCell(celln4);
                PdfPCell celln5 = createPdfPCell(ctpt, font);
                table.addCell(celln5);
                PdfPCell celln6 = createPdfPCell(readingElectricQuantity, font);
                table.addCell(celln6);
                PdfPCell celln7 = createPdfPCell(refundElectricQuantity, font);
                table.addCell(celln7);
                PdfPCell celln8 = createPdfPCell(totalElectric, font);
                table.addCell(celln8);
            }

            //第四大行
            PdfPCell cell41 = createPdfPCell("用电性质", font,2);
            table.addCell(cell41);
            PdfPCell cell42 = createPdfPCell("电价名称", font,3);
            table.addCell(cell42);
            PdfPCell cell43 = createPdfPCell("结算电量(kWh)", font);
            table.addCell(cell43);
            PdfPCell cell44 = createPdfPCell("单价(RMB)", font);
            table.addCell(cell44);
            PdfPCell cell45 = createPdfPCell("电费(RMB)", font);
            table.addCell(cell45);

            List<Map<String, Object>> billPrices = (List<Map<String, Object>>)map.get("billPrices");
            for (Map<String, Object> billPrice : billPrices) {
                String priceNature = (String)billPrice.get("priceNature");//用电性质
                String tariffName = (String)billPrice.get("tariffName");//电价名称
                String totalEq = (String)billPrice.get("totalEq");//结算电量(kWh)
                String unitPrice = (String)billPrice.get("unitPrice");//单价(RMB)
                String totalCharge = (String)billPrice.get("totalCharge");//电费(RMB)

                PdfPCell celln1 = createPdfPCell(priceNature, font,2);
                table.addCell(celln1);
                PdfPCell celln2 = createPdfPCell(tariffName, font,3);
                table.addCell(celln2);
                PdfPCell celln3 = createPdfPCell(totalEq, font);
                table.addCell(celln3);
                PdfPCell celln4 = createPdfPCell(unitPrice, font);
                table.addCell(celln4);
                PdfPCell celln5 = createPdfPCell(totalCharge, font);
                table.addCell(celln5);
            }

            document.add(table);
            document.addCreationDate();
            document.close();
            pdfPath = file.getAbsolutePath();
        }catch (Exception e){
            e.printStackTrace();
            log.error("file create exception" + e.getMessage());
        }
        return pdfPath;
    }

    public Map initPaidData(){
        Map<String, Object> map = new HashMap();
        String consNo = "1000000001";//客户编号
        String consName = "Customer1";//客户名称
        String instlLoc = "CIE星创大厦";//用电地址
        String dataDate = "202008";//电费年月
        String refundCharge = "0.00";//退费金额(RMB)
        String deductionCharge = "0.00";//预存账户余额(RMB)
        String factTotal = "275.64";//实付金额

        map.put("consNo",consNo);
        map.put("consName",consName);
        map.put("instlLoc",instlLoc);
        map.put("refundCharge",refundCharge);
        map.put("deductionCharge",deductionCharge);
        map.put("dataDate",dataDate);
        map.put("factTotal",factTotal);

        List<Map<String, Object>> meterReadouts = new ArrayList<>();
        for (int count = 0; count < 4; count++) {
            Map<String, Object> meterReadoutMap = new HashMap();
            String serialNo = "095666777931";//电表编号
            String readoutType = "usual";//示数类型
            String lastMonthReadingNumber = "200.00";//上次抄表示数(kWh)
            String readingNumber = "299.00";//本次抄表示数(kWh)
            String ctpt = "1.00";//倍率
            String readingElectricQuantity = "99.00";//抄见电量(kWh)
            String refundElectricQuantity = "0.00";//退补电量(kWh)
            String totalElectric = "99.00";//合计电量(kWh)
            meterReadoutMap.put("serialNo",serialNo);
            meterReadoutMap.put("readoutType",readoutType);
            meterReadoutMap.put("lastMonthReadingNumber",lastMonthReadingNumber);
            meterReadoutMap.put("readingNumber",readingNumber);
            meterReadoutMap.put("ctpt",ctpt);
            meterReadoutMap.put("readingElectricQuantity",readingElectricQuantity);
            meterReadoutMap.put("refundElectricQuantity",refundElectricQuantity);
            meterReadoutMap.put("totalElectric",totalElectric);

            meterReadouts.add(meterReadoutMap);
        }
        map.put("meterReadouts",meterReadouts);

        List<Map<String, Object>> billPrices = new ArrayList<>();
        for (int count = 0; count < 4; count++) {
            Map<String, Object> billPriceMap = new HashMap();
            String priceNature = "居民生活用电";//用电性质
            String tariffName = "shangbinz-勿删";//电价名称
            String totalEq = "99.00";//结算电量(kWh)
            String unitPrice = "1.36";//单价(RMB)
            String totalCharge = "134.64";//电费(RMB)
            billPriceMap.put("priceNature",priceNature);
            billPriceMap.put("tariffName",tariffName);
            billPriceMap.put("totalEq",totalEq);
            billPriceMap.put("unitPrice",unitPrice);
            billPriceMap.put("totalCharge",totalCharge);
            billPrices.add(billPriceMap);
        }
        map.put("billPrices",billPrices);

        map.put("totalEq","199.00");//用电量(kWh)
        map.put("totalCharge","270.64");//电费合计(RMB)
        map.put("taxes","5.00");//税费(RMB)
        map.put("materialFee","0.00");//材料费(RMB)
        map.put("serviceCharge","0.00");//服务费(RMB)
        map.put("liquidatedDamages","0.00");//违约金(RMB)
        map.put("elecDebt","0.00");//债务电费(RMB)
        map.put("totalArrearage","0.00");//欠费合计(RMB)
        map.put("payableExpenses","275.64");//应付费用(RMB)

        return map;
    }

    public void testPaidReport() {
        Map<String, Object> map = initPaidData();
        System.out.println(JSON.toJSONString(map));
        String consNo = (String)map.get("consNo");//客户编号
        String consName = (String)map.get("consName");//客户名称
        String instlLoc = (String)map.get("instlLoc");//用电地址
        String dataDate = (String)map.get("dataDate");//电费年月
        String refundCharge = (String)map.get("refundCharge");//退费金额(RMB)
        String deductionCharge = (String)map.get("deductionCharge");//预存账户余额(RMB)
        String factTotal = (String)map.get("factTotal");//实付金额
        List<Map<String, Object>> meterReadouts = (List<Map<String, Object>>)map.get("meterReadouts");
        List<Map<String, Object>> billPrices = (List<Map<String, Object>>)map.get("billPrices");
        System.out.print(consNo + " " + consName + " " + instlLoc + " " + dataDate + " " + factTotal + refundCharge + deductionCharge);
        for (Map<String, Object> meterReadout : meterReadouts) {
            String serialNo = (String)meterReadout.get("serialNo");//电表编号
            String readoutType = (String)meterReadout.get("readoutType");//示数类型
            String lastMonthReadingNumber = (String)meterReadout.get("lastMonthReadingNumber");//上次抄表示数(kWh)
            String readingNumber = (String)meterReadout.get("readingNumber");//本次抄表示数(kWh)
            String ctpt = (String)meterReadout.get("ctpt");//倍率
            String readingElectricQuantity = (String)meterReadout.get("readingElectricQuantity");//抄见电量(kWh)
            String refundElectricQuantity = (String)meterReadout.get("refundElectricQuantity");//退补电量(kWh)
            String totalElectric = (String)meterReadout.get("totalElectric");//合计电量(kWh)
            System.out.println(JSON.toJSONString(meterReadout));
        }
        for (Map<String, Object> billPrice : billPrices) {
            String priceNature = (String)billPrice.get("priceNature");//用电性质
            String tariffName = (String)billPrice.get("tariffName");//电价名称
            String totalEq = (String)billPrice.get("totalEq");//结算电量(kWh)
            String unitPrice = (String)billPrice.get("unitPrice");//单价(RMB)
            String totalCharge = (String)billPrice.get("totalCharge");//电费(RMB)
            System.out.println(JSON.toJSONString(billPrice));
        }

        String totalEq = (String)map.get("totalEq");//用电量(kWh)
        String totalCharge = (String)map.get("totalCharge");//电费合计(RMB)
        String taxes = (String)map.get("taxes");//税费(RMB)
        String materialFee = (String)map.get("materialFee");//材料费(RMB)
        String serviceCharge = (String)map.get("serviceCharge");//服务费(RMB)
        String liquidatedDamages = (String)map.get("liquidatedDamages");//违约金(RMB)
        String elecDebt = (String)map.get("elecDebt");//债务电费(RMB)
        String totalArrearage = (String)map.get("totalArrearage");//欠费合计(RMB)
        String payableExpenses = (String)map.get("payableExpenses");//应付费用(RMB)

        //生成pdf
        String pdfPath = new PDFReport().exportPaidReport(map);
        System.out.println(pdfPath);
    }

    /**
     * 返回已缴费的pdf附件
     * @param map
     * @return
     */
    private String exportPaidReport(Map map) {
        String pdfPath = null;

        Document document = new Document();
        try {
            File dir = new File(REPORT_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString();
            File file = new File(REPORT_PATH + File.separator + uuid + ".pdf");
            if (!file.exists()) {
                file.createNewFile();
            }
            PdfWriter.getInstance(document, new FileOutputStream(REPORT_PATH + File.separator + uuid + ".pdf"));
            document.open();
            //页面头部内容
            Paragraph elements = new Paragraph("账单", font2);
            elements.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(elements);

            PdfPTable table = createPdfPTable(9);

            //第一行
            String consNo = (String)map.get("consNo");//客户编号
            String consName = (String)map.get("consName");//客户名称
            String instlLoc = (String)map.get("instlLoc");//用电地址
            String dataDate = (String)map.get("dataDate");//电费年月
            String refundCharge = (String)map.get("refundCharge");//退费金额(RMB)
            String deductionCharge = (String)map.get("deductionCharge");//预存账户余额(RMB)
            String factTotal = (String)map.get("factTotal");//实付金额

            PdfPCell cell11 = createPdfPCell("客户编号", font);
            table.addCell(cell11);
            PdfPCell cell12 = createPdfPCell("客户名称", font);
            table.addCell(cell12);
            PdfPCell cell13 = createPdfPCell("用电地址", font,3);
            table.addCell(cell13);
            PdfPCell cell14 = createPdfPCell("电费年月", font);
            table.addCell(cell14);
            PdfPCell cell15 = createPdfPCell("退费金额(RMB)", font);
            table.addCell(cell15);
            PdfPCell cell16 = createPdfPCell("预存账户余额(RMB)", font);
            table.addCell(cell16);
            PdfPCell cell17 = createPdfPCell("实付金额(RMB)", font);
            table.addCell(cell17);

            //第二行
            PdfPCell cell21 = createPdfPCell(consNo, font1);
            table.addCell(cell21);
            PdfPCell cell22 = createPdfPCell(consName, font);
            table.addCell(cell22);
            PdfPCell cell23 = createPdfPCell(instlLoc, font,3);
            table.addCell(cell23);
            PdfPCell cell24 = createPdfPCell(dataDate, font);
            table.addCell(cell24);
            PdfPCell cell25 = createPdfPCell(refundCharge, font);
            table.addCell(cell25);
            PdfPCell cell26 = createPdfPCell(deductionCharge, font);
            table.addCell(cell26);
            PdfPCell cell27 = createPdfPCell(factTotal, font);
            table.addCell(cell27);

            //第三大行
            PdfPCell cell31 = createPdfPCell("电表编号", font,2);
            table.addCell(cell31);
            PdfPCell cell32 = createPdfPCell("示数类型", font);
            table.addCell(cell32);
            PdfPCell cell33 = createPdfPCell("上次抄表示数(kWh)", font);
            table.addCell(cell33);
            PdfPCell cell34 = createPdfPCell("本次抄表示数(kWh)", font);
            table.addCell(cell34);
            PdfPCell cell35 = createPdfPCell("倍率", font);
            table.addCell(cell35);
            PdfPCell cell36 = createPdfPCell("抄见电量(kWh)", font);
            table.addCell(cell36);
            PdfPCell cell37 = createPdfPCell("退补电量(kWh)", font);
            table.addCell(cell37);
            PdfPCell cell38 = createPdfPCell("合计电量(kWh)", font);
            table.addCell(cell38);

            List<Map<String, Object>> meterReadouts = (List<Map<String, Object>>)map.get("meterReadouts");
            for (Map<String, Object> meterReadout : meterReadouts) {
                String serialNo = (String)meterReadout.get("serialNo");//电表编号
                String readoutType = (String)meterReadout.get("readoutType");//示数类型
                String lastMonthReadingNumber = (String)meterReadout.get("lastMonthReadingNumber");//上次抄表示数(kWh)
                String readingNumber = (String)meterReadout.get("readingNumber");//本次抄表示数(kWh)
                String ctpt = (String)meterReadout.get("ctpt");//倍率
                String readingElectricQuantity = (String)meterReadout.get("readingElectricQuantity");//抄见电量(kWh)
                String refundElectricQuantity = (String)meterReadout.get("refundElectricQuantity");//退补电量(kWh)
                String totalElectric = (String)meterReadout.get("totalElectric");//合计电量(kWh)

                PdfPCell celln1 = createPdfPCell(serialNo, font,2);
                table.addCell(celln1);
                PdfPCell celln2 = createPdfPCell(readoutType, font);
                table.addCell(celln2);
                PdfPCell celln3 = createPdfPCell(lastMonthReadingNumber, font);
                table.addCell(celln3);
                PdfPCell celln4 = createPdfPCell(readingNumber, font);
                table.addCell(celln4);
                PdfPCell celln5 = createPdfPCell(ctpt, font);
                table.addCell(celln5);
                PdfPCell celln6 = createPdfPCell(readingElectricQuantity, font);
                table.addCell(celln6);
                PdfPCell celln7 = createPdfPCell(refundElectricQuantity, font);
                table.addCell(celln7);
                PdfPCell celln8 = createPdfPCell(totalElectric, font);
                table.addCell(celln8);
            }

            //第四大行
            PdfPCell cell41 = createPdfPCell("用电性质", font,3);
            table.addCell(cell41);
            PdfPCell cell42 = createPdfPCell("电价名称", font,3);
            table.addCell(cell42);
            PdfPCell cell43 = createPdfPCell("结算电量(kWh)", font);
            table.addCell(cell43);
            PdfPCell cell44 = createPdfPCell("单价(RMB)", font);
            table.addCell(cell44);
            PdfPCell cell45 = createPdfPCell("电费(RMB)", font);
            table.addCell(cell45);

            List<Map<String, Object>> billPrices = (List<Map<String, Object>>)map.get("billPrices");
            for (Map<String, Object> billPrice : billPrices) {
                String priceNature = (String)billPrice.get("priceNature");//用电性质
                String tariffName = (String)billPrice.get("tariffName");//电价名称
                String totalEq = (String)billPrice.get("totalEq");//结算电量(kWh)
                String unitPrice = (String)billPrice.get("unitPrice");//单价(RMB)
                String totalCharge = (String)billPrice.get("totalCharge");//电费(RMB)

                PdfPCell celln1 = createPdfPCell(priceNature, font,3);
                table.addCell(celln1);
                PdfPCell celln2 = createPdfPCell(tariffName, font,3);
                table.addCell(celln2);
                PdfPCell celln3 = createPdfPCell(totalEq, font);
                table.addCell(celln3);
                PdfPCell celln4 = createPdfPCell(unitPrice, font);
                table.addCell(celln4);
                PdfPCell celln5 = createPdfPCell(totalCharge, font);
                table.addCell(celln5);
            }

            //第五大行
            PdfPCell cell51 = createPdfPCell("用电量(kWh)", font);
            table.addCell(cell51);
            PdfPCell cell52 = createPdfPCell("电费合计(RMB)", font);
            table.addCell(cell52);
            PdfPCell cell53 = createPdfPCell("税费(RMB)", font);
            table.addCell(cell53);
            PdfPCell cell54 = createPdfPCell("材料费(RMB)", font);
            table.addCell(cell54);
            PdfPCell cell55 = createPdfPCell("服务费(RMB)", font);
            table.addCell(cell55);
            PdfPCell cell56 = createPdfPCell("违约金(RMB)", font);
            table.addCell(cell56);
            PdfPCell cell57 = createPdfPCell("债务电费(RMB)", font);
            table.addCell(cell57);
            PdfPCell cell58 = createPdfPCell("欠费合计(RMB)", font);
            table.addCell(cell58);
            PdfPCell cell59 = createPdfPCell("应付费用(RMB)", font);
            table.addCell(cell59);

            String totalEq = (String)map.get("totalEq");//用电量(kWh)
            String totalCharge = (String)map.get("totalCharge");//电费合计(RMB)
            String taxes = (String)map.get("taxes");//税费(RMB)
            String materialFee = (String)map.get("materialFee");//材料费(RMB)
            String serviceCharge = (String)map.get("serviceCharge");//服务费(RMB)
            String liquidatedDamages = (String)map.get("liquidatedDamages");//违约金(RMB)
            String elecDebt = (String)map.get("elecDebt");//债务电费(RMB)
            String totalArrearage = (String)map.get("totalArrearage");//欠费合计(RMB)
            String payableExpenses = (String)map.get("payableExpenses");//应付费用(RMB)

            PdfPCell cell61 = createPdfPCell(totalEq, font);
            table.addCell(cell61);
            PdfPCell cell62 = createPdfPCell(totalCharge, font);
            table.addCell(cell62);
            PdfPCell cell63 = createPdfPCell(taxes, font);
            table.addCell(cell63);
            PdfPCell cell64 = createPdfPCell(materialFee, font);
            table.addCell(cell64);
            PdfPCell cell65 = createPdfPCell(serviceCharge, font);
            table.addCell(cell65);
            PdfPCell cell66 = createPdfPCell(liquidatedDamages, font);
            table.addCell(cell66);
            PdfPCell cell67 = createPdfPCell(elecDebt, font);
            table.addCell(cell67);
            PdfPCell cell68 = createPdfPCell(totalArrearage, font);
            table.addCell(cell68);
            PdfPCell cell69 = createPdfPCell(payableExpenses, font);
            table.addCell(cell69);

            document.add(table);
            document.addCreationDate();
            document.close();

            pdfPath = file.getAbsolutePath();
        }catch (Exception e){
            e.printStackTrace();
            log.error("file create exception" + e.getMessage());
        }
        return pdfPath;
    }

    /**
     *  自定义创建PdfPCell
     * @param content 内容
     * @param font 字体
     */
    private PdfPCell createPdfPCell(String content,Font font){
        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPhrase(new Paragraph(content, font));
        return cell;
    }

    /**
     * 自定义创建PdfPCell
     * @param content 内容
     * @param font 字体
     * @param colspan
     * @return
     */
    private PdfPCell createPdfPCell(String content,Font font,int colspan){
        PdfPCell cell = new PdfPCell();
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPhrase(new Paragraph(content, font));
        return cell;
    }

    /**
     *  自定义创建PdfPTable
     * @param numColumns
     * @return
     */
    private PdfPTable createPdfPTable(int numColumns){
        PdfPTable table = new PdfPTable(numColumns);
        table.setWidthPercentage(100);//设置表格宽度比例为%100
        table.setSpacingBefore(10f);// 设置表格上面空白宽度
        table.setSpacingAfter(10f);// 设置表格下面空白宽度
        return table;
    }

    public static void main(String[] args) {
        PDFReport pdfReport = new PDFReport();
        //pdfReport.testExportReport();
        pdfReport.testPaidReport();
        pdfReport.tesCallReport();
    }

}
