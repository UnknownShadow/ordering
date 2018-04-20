package com.juunew.admin.controller;


import com.juunew.admin.services.ReportService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("report")
public class ReportController extends BaseController {

    @Autowired
    ReportService reportService;

    //运营日报统计
    @RequestMapping(value = "operationDaily")
    public String operationDaily(@RequestParam(value = "date",required = false) String date) throws ParseException {
        if (StringUtils.isEmpty(date)){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date = df.format(new Date());
        }
        reportService.operationDaily(date);
        return "success";
    }


    //流失统计
    @RequestMapping(value = "flowAwayDaily")
    public String halfPriceDaily(@RequestParam(value = "date",required = false) String date) throws ParseException {
        if (StringUtils.isEmpty(date)){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date = df.format(new Date());
        }
        reportService.flowAwayDaily(date);
        return "success";
    }

    //流失统计
    @RequestMapping(value = "partnerSubAgent")
    public String partnerSubAgent(@RequestParam(value = "date",required = false) String date) throws ParseException {
        if (StringUtils.isEmpty(date)){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date = df.format(new Date());
        }
        reportService.partnerData();
        return "success";
    }
}
