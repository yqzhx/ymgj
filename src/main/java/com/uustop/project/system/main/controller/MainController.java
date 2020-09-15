//package com.uustop.project.system.main.controller;
//
//
//import com.uustop.project.system.main.domain.Main;
//import com.uustop.project.system.main.domain.Results;
//import com.uustop.project.system.main.service.IMainService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/main")
//public class MainController {
//
//    @Autowired
//    public IMainService mainService;
//
//
//
//    @GetMapping("/selectContractCount")
//    @ResponseBody
//    public Results selectMfrsContractCount(Results result){
//        result.setMfrs(mainService.selectMfrsContractCount());
//        //result.setCdc(mainService.selectCdcContractCount());
//        result.setCompany(mainService.selectCompanyContractCount());
//        return result;
//    }
//
//
//    @GetMapping("/count")
//    @ResponseBody
//    public Integer[] count(){
//        List<Main>mainList=mainService.selectContractCount();
//        Integer[] ContractCountNumber={0,0,0,0,0,0,0,0,0,0,0,0};
//        for (Main mains : mainList) {
//            Integer skuCountNumber = mains.getCountContract();
//            Integer yuefen = Integer.parseInt(mains.getMonth());
//            ContractCountNumber[yuefen-1]=skuCountNumber;
//        }
//        return ContractCountNumber;
//    }
//
//    @GetMapping("/contract")
//    @ResponseBody
//    public Results contract(Results result){
//        result.setDaiFaHuo(mainService.selectDaiFaHuoContract());
//        result.setWeiHuiKuan(mainService.selectWeiHuiKuanContract());
//        result.setTodayContract(mainService.selectTodayContract());
//        result.setHistoryContract(mainService.selectHistoryContract());
//        return result;
//    }
//
//    @GetMapping("/account")
//    @ResponseBody
//    public List<Integer> account(){
//        return mainService.selectAllAccount();
//    }
//
//
//
//
//
//
//
//
//
//
//}
