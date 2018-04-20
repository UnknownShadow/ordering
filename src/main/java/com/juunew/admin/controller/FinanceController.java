package com.juunew.admin.controller;

import com.juunew.admin.dao.*;
import com.juunew.admin.entity.*;
import com.juunew.admin.services.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by juunew on 2017/7/10.
 */
@Controller
public class FinanceController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    UserDao userdao;
    @Autowired
    PermissionDao permissionDao;
    @Autowired
    AllotDao allotDao;
    @Autowired
    BankService bankService;
    @Autowired
    LocalUserDao localUserDao;
    @Autowired
    IncomeDao incomeDao;
    @Autowired
    NoticeDao noticeDao;
    @Autowired
    Status_EditorDao status_editorDao;
    @Autowired
    AuditDao auditDao;
    @Autowired
    Financial_DailyDao financial_dailyDao;
    @Autowired
    Diamond_consumeDao diamond_consumeDao;
    @Autowired
    Diamond_outputDao diamond_outputDao;
    @Autowired
    DiamondsDao diamondsDao;



    /**---------------以下为财务模块部分--------------**/

/**
 * 进入财务审核页面
 * */
    @RequestMapping("auditing")
    public ModelAndView finance_review(String page, HttpSession session){

        ModelAndView mv = new ModelAndView();

        //获取用户名；
        String username = (String)session.getAttribute("username");

        int pages = Integer.parseInt(page);

        //y为自己定义分为一页显示几条数据；这里一页显示3条数据；
        int y=10;

        //查询得到数据条数：total
        List<AuditEntity> auditTotal = auditDao.findAllAudit();
        int sum = auditTotal.size();           //查询所有需要审核的数据

        int xx = (pages-1)*y;
        int  yy=pages*y;

        //对所有需审核数据进行模糊加分页查询；
        List<AuditEntity> auditList = auditDao.AuditPaging(xx, 10);

        mv.addObject("auditList",auditList);

        int total = disposeTotal(sum,10);

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台

        mv.setViewName("auditing");
        return mv;
    }


/**
 * 对数据的审核
 * */
    @RequestMapping("audit")
    public void finance_audit(String status, String id, String[] arrayObj,String DiamondAndMoney,String receive_id, HttpServletRequest req, HttpServletResponse resp,HttpSession session)throws Exception{
        ModelAndView mv = new ModelAndView();
        String json = "1";

        String username = (String)session.getAttribute("username");
        int moneys=0;
        int diamonds=0;
        if(status.equals("1")&&(!DiamondAndMoney.equals(""))){
            String[]he =DiamondAndMoney.split(",");
            //String money = he[0];
            String diamond = he[1];
            //moneys=Integer.parseInt(money);
            diamonds = Integer.parseInt(diamond);
        }


        int receive_ID = Integer.parseInt(receive_id);

        if(arrayObj!=null){
            for(int i=0;i<arrayObj.length;i++){
                System.out.println(arrayObj[i]);
            }
        }

        //根据status来判断该ID的数据是否通过，1代表通过 0代表拒绝通过

        if(status.equals("1")){
            if(arrayObj!=null){
                for(int i=0;i<arrayObj.length;i++){
                    //根据ID查询Audit表中单一的数据；
                    AuditEntity auditById = auditDao.findAuditById(Integer.parseInt(arrayObj[i]));

                    /*if(auditById.getMoney()!=0){
                        //修改金币
                        try{
                            int money_s = bankService.addMoney(1,auditById.getReceive_id(), auditById.getMoney(),2);
                            System.out.println("API调用返回结果：返回为总金币数量：："+money_s);
                        }catch (Exception e){
                            logger.error("用户不存在或金币数量不足", e);
                        }

                    }*/
                    if(auditById.getDiamond()!=0){
                        //修改钻石
                        try{
                            int diamond_s = bankService.addDiamond(1,auditById.getReceive_id(),auditById.getDiamond(),1,"平台充值",301);

                            System.out.println("API调用返回结果：返回为总钻石数量：："+diamond_s);
                        }catch (Exception e){
                            json = e+"";
                            logger.error("用户不存在或钻石数量不足{}", e);
                        }
                    }
                    auditDao.updateAudit(1,Integer.parseInt(arrayObj[i]),username);
                }
            }else{
                try{
                   /* if(moneys!=0){
                        //修改金币
                        int money_s = bankService.addMoney(1,receive_ID, moneys,2);
                        System.out.println("API调用返回结果：返回为总金币数量：："+money_s);
                    }*/
                    if(diamonds!=0){
                        //修改钻石
                        int diamond_s = bankService.addDiamond(1,receive_ID,diamonds,1,"平台充值",301);
                        System.out.println("API调用返回结果：返回为总钻石数量：："+diamond_s);
                    }
                    int ID = Integer.parseInt(id);
                    auditDao.updateAudit(1,ID,username);
                }catch (Exception e){
                    logger.error("接口错误信息：{}"+e);
                    json = e+"";
                    mv.addObject("erro","钻石数量不足");
                }
            }
        }else if(status.equals("0")){
            if(arrayObj!=null){
                for(int i=0;i<arrayObj.length;i++){
                    auditDao.updateAudit(0,Integer.parseInt(arrayObj[i]),username);
                }
            }else{
                int ID = Integer.parseInt(id);
                auditDao.updateAudit(0,ID,username);
            }
            json = "0";
        }

        //req.getRequestDispatcher("auditing?page=1").forward(req,resp);

        PrintWriter out = resp.getWriter();
        out.print(json);
    }



    /**
     * 审批记录
     *approval_record
     * */
    @RequestMapping("approval_record")
    public ModelAndView records(String page,String proxy_date,String proxy_date_end,String send_id){

        ModelAndView mv = new ModelAndView();

        mv.addObject("proxy_date",proxy_date);
        mv.addObject("proxy_date_end",proxy_date_end);
        mv.addObject("send_id",send_id);

        int pages = Integer.parseInt(page);

        //y为自己定义分为一页显示几条数据；这里一页显示3条数据；
        int y=10;

        List<AuditEntity> allAuditTotal=null;
        if(!proxy_date.equals("")){
            //分两种情况，第一，有日期的情况，第二，没有日期的情况
           allAuditTotal = auditDao.AllAuditDataOther(send_id, proxy_date, proxy_date_end);
        }else{
            //查询得到数据条数：total
            allAuditTotal = auditDao.AllAuditData(send_id);
        }

        int sum = allAuditTotal.size();           //查询所有需要审核的数据

        int xx = (pages-1)*y;
        int  yy=pages*y;

        List<AuditEntity> auditDataList = null;
        if(proxy_date!=""){
           auditDataList = auditDao.AuditDataPagingOther(send_id, proxy_date, proxy_date_end, xx, 10);
        }else{
             //对所有需审核数据进行模糊加分页查询；
           auditDataList = auditDao.AuditDataPaging(xx,yy,send_id);
        }
        mv.addObject("auditDataList",auditDataList);

        int total = disposeTotal(sum,10);

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);//将page传到前台

        mv.setViewName("approval_record");
        return mv;
    }



/**
 * 进入财务日报
 * */
    @RequestMapping("financial_daily")
    public ModelAndView daily(String page){
        ModelAndView mv = new ModelAndView();
        int pages = Integer.parseInt(page);

        //y为自己定义分为一页显示几条数据；这里一页显示3条数据；
        int y=10;

        //查询得到数据条数：total
        List<Financial_DailyEntity> financialTotal = financial_dailyDao.findFinancialTotal();

        int sum = financialTotal.size();

        int xx = (pages-1)*y;
        int  yy=pages*y;


        //对所有财务日报进行分页查询；
        List<Financial_DailyEntity> financialAll = financial_dailyDao.findFinancialAll(xx, 10);

        for (Financial_DailyEntity financial_dailyEntity : financialAll) {

            double wechat_recharge = 0.0;
            double apple_recharge = 0.0;
            double platform_recharge =0.0;
            double total_revenue =0.0;

            wechat_recharge = financial_dailyEntity.getWechat_recharge();
            apple_recharge = financial_dailyEntity.getApple_recharge();
            platform_recharge = financial_dailyEntity.getPlatform_recharge();
            total_revenue = financial_dailyEntity.getTotal_revenue();

            if(wechat_recharge != 0 ){
                financial_dailyEntity.setWechatRecharge(wechat_recharge*1.0/100);
            }
            if(apple_recharge != 0){
                financial_dailyEntity.setAppleRecharge(apple_recharge*1.0/100);
            }
            if(platform_recharge != 0){
                financial_dailyEntity.setPlatformRecharge(platform_recharge*1.0/100);
            }
            if(total_revenue != 0){
                financial_dailyEntity.setTotalRevenue(total_revenue*1.0/100);
            }
        }


        int total = disposeTotal(sum,10);

        mv.addObject("financialAll",financialAll);
        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);//将page传到前台

        mv.setViewName("financial_daily");
        return mv;
    }



    /**
     * 进入消耗详情页面
     * */
    @RequestMapping("consumption_details")
    public ModelAndView consumeption(String page,String start_date,String end_date){
        ModelAndView mv = new ModelAndView();
        int pages = Integer.parseInt(page);

        //y为自己定义分为一页显示几条数据；这里一页显示3条数据；
        int y=10;

        //查询总条数，
        List<Diamond_consumeEntity> consumeTotal = diamond_consumeDao.findBydateTotal();
        if(!start_date.equals("")){
            consumeTotal = diamond_consumeDao.findBydateTotalByDate(start_date,end_date);
        }


        //查询得到数据条数：total
        int sum = consumeTotal.size();

        int xx = (pages-1)*y;
        int  yy=pages*y;

        List<Diamond_consumeEntity> consumeLists = diamond_consumeDao.findPaging(xx,10);
        if(!start_date.equals("")){
            consumeLists = diamond_consumeDao.findPagingBydate(start_date,end_date,xx,10);
        }

        int total = disposeTotal(sum,10);

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台

        mv.addObject("start_date",start_date);
        mv.addObject("end_date",end_date);
        mv.addObject("consumeLists",consumeLists);
        mv.setViewName("consumption_details");
        return mv;
    }


/**
 * 进入钻石产生详情页面
 * */
    @RequestMapping("diamond_production")
    public ModelAndView production(String page,String start_date,String end_date){

        ModelAndView mv = new ModelAndView();

        int pages = Integer.parseInt(page);

        //y为自己定义分为一页显示几条数据；这里一页显示3条数据；
        int y=10;


        //查询总条数，
        List<Diamond_outputEntity> diamondOutputTotal = diamond_outputDao.diamondOutputTotal();
        if(!start_date.equals("")){
            diamondOutputTotal = diamond_outputDao.diamondOutputTotalByDate(start_date,end_date);
        }


        //查询得到数据条数：total
        int sum = diamondOutputTotal.size();

        int xx = (pages-1)*y;
        int  yy=pages*y;


        //根据日期进行分组函数将数据累加统计总销量;并进行分页查询
        List<Diamond_outputEntity> diamondOutputLists = diamond_outputDao.diamondOutputTotalPaging(xx,10);
        if(!start_date.equals("")){
            diamondOutputLists = diamond_outputDao.diamondOutputTotalPagingByDate(start_date,end_date,xx,10);
        }

        int total = disposeTotal(sum,10);

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台

        mv.addObject("start_date",start_date);
        mv.addObject("end_date",end_date);
        mv.addObject("diamondOutputLists",diamondOutputLists);
        mv.setViewName("diamond_production");
        return mv;
    }


}
