//package com.example.test.controller;
//
//import com.example.test.entity.AEvent;
//import com.example.test.entity.AFault;
//import com.example.test.entity.Msg;
//import com.example.test.service.MongoDBService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//@RequestMapping("MongoDB")
//public class MongoDBController {
//
//    @Autowired
//    MongoDBService mongoDBService;
//
//    /**
//     * 将a_event表 数据导入 mongoDB
//     * @return
//     */
//    @RequestMapping("importAEvent")
//    @ResponseBody
//    public Msg importAEvent(){
//        mongoDBService.importAEvent();
//        return new Msg("OK","导入数据成功！");
//    }
//
//    /**
//     * 将a_fault表 数据导入 mongoDB
//     * @return
//     */
//    @RequestMapping("importAFault")
//    @ResponseBody
//    public Msg importAFault(){
//        mongoDBService.importAFault();
//        return new Msg("OK","导入数据成功！");
//    }
//
////    /**
////     * 删除mongoDB中 a_event表 数据
////     * @return
////     */
////    @RequestMapping("deleteAEventAll")
////    @ResponseBody
////    public Msg deleteAEventAll(){
////        mongoDBService.deleteAEventAll();
////        return new Msg("OK","删除数据成功！");
////    }
////
////    /**
////     * 删除mongoDB中 a_fault 数据
////     * @return
////     */
////    @RequestMapping("deleteAFaultAll")
////    @ResponseBody
////    public Msg deleteAFaultAll(){
////        mongoDBService.deleteAFaultAll();
////        return new Msg("OK","删除数据成功！");
////    }
//
//    /**
//     * 根据 a_event表 中 eventid 删除mongoDB中数据
//     * @param eventid
//     * @return
//     */
//    @RequestMapping("deleteAEventById")
//    @ResponseBody
//    public Msg deleteAEventById(@RequestParam Integer eventid){
//        mongoDBService.deleteAEventById(eventid);
//        return new Msg("OK","删除数据成功！");
//    }
//
//    /**
//     * 根据 a_fault表 中 faultid 删除mongoDB中数据
//     * @param faultid
//     * @return
//     */
//    @RequestMapping("deleteAFaultById")
//    @ResponseBody
//    public Msg deleteAFaultById(@RequestParam Integer faultid){
//        mongoDBService.deleteAFaultById(faultid);
//        return new Msg("OK","删除数据成功！");
//    }
//
//    /**
//     * 条件分页查询 mongoDB中 a_event表 数据
//     * @param aEvent 条件为相等，设备名模糊查询
//     * @param page  页数
//     * @param size  每页的数量
//     * @return
//     */
//    @RequestMapping("findAEvent")
//    @ResponseBody
//    public PageImpl<AEvent> findAEvent(AEvent aEvent,@RequestParam int page, @RequestParam int size){
//        PageImpl<AEvent> list = mongoDBService.findAEvent(aEvent,page,size);
//        return list;
//    }
//
//    /**
//     * 条件分页查询 mongoDB中 a_fault表 数据
//     * @param aFault 条件为相等，设备名模糊查询
//     * @param page  页数
//     * @param size  每页的数量
//     * @return
//     */
//    @RequestMapping("findAFault")
//    @ResponseBody
//    public PageImpl<AFault> findAFault(AFault aFault, @RequestParam int page,@RequestParam int size){
//        PageImpl<AFault> list = mongoDBService.findAFault(aFault,page,size);
//        return list;
//    }
//
//    /**
//     * 根据eventid更新AEvent表对象
//     *
//     * @param aEvent
//     * @return
//     */
//    @RequestMapping("updateAEvent")
//    @ResponseBody
//    public Msg updateAEvent(AEvent aEvent){
//        mongoDBService.updateAEvent(aEvent);
//        return new Msg("OK","更新成功！");
//    }
//
//    /**
//     * 根据faultid更新AFault表对象
//     *
//     * @param aFault
//     * @return
//     */
//    @RequestMapping("updateAFault")
//    @ResponseBody
//    public Msg updateAFault(AFault aFault){
//        mongoDBService.updateAFault(aFault);
//        return new Msg("OK","更新成功！");
//    }
//
///*    @RequestMapping("pageAEventList")
//    @ResponseBody
//    public PageImpl<AEvent> pageAEventList(int page, int size){
//        return mongoDBService.pageAEventList(page,size);
//    }
//
//    @RequestMapping("pageAFaultList")
//    @ResponseBody
//    public PageImpl<AFault> pageAFaultList(int page, int size){
//        return mongoDBService.pageAFaultList(page,size);
//    }
//
//
//    @RequestMapping("findAEventAll")
//    @ResponseBody
//    public List findAEventAll(){
//        List<AEvent> all = mongoDBService.findAEventAll();
//        return all;
//    }
//
//    @RequestMapping("findAFaultAll")
//    @ResponseBody
//    public List findAFaultAll(){
//        List<AFault> all = mongoDBService.findAFaultAll();
//        return all;
//    }
//
//    @RequestMapping("findAEventOne")
//    @ResponseBody
//    public AEvent findAEventOne(String key, String value){
//        AEvent aEvent = mongoDBService.findAEventOne(key, value);
//        return aEvent;
//    }
//
//    @RequestMapping("findT")
//    @ResponseBody
//    public <T> List<T> findT(String key, String value, String name){
//        Class tClass=null;
//        if ("a_event".equals(name)){
//            tClass=AEvent.class;
//        }
//        if ("a_fault".equals(name)){
//            tClass=AFault.class;
//        }
//        if (tClass==null){
//            return null;
//        }
//        List<T> list = mongoDBService.findT(key, value, tClass);
//        return list;
//    }
//
//    @RequestMapping("getAEvent")
//    @ResponseBody
//    public List<AEvent> getAEvent(){
//        return mongoDBService.getAEvent();
//    }*/
//}
