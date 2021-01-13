package com.example.test.service.imp;

import com.example.test.dao.EventDao;
import com.example.test.dao.FaultDao;
import com.example.test.entity.AEvent;
import com.example.test.entity.AFault;
import com.example.test.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

@Service("MongoDBService")
public class MongoDBServiceImpl implements MongoDBService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    EventDao eventDao;

    @Autowired
    FaultDao faultDao;

    @Override
    public void importAEvent() {
        List<AEvent> aEventList = eventDao.findAEventList();
        if (StringUtils.isEmpty(aEventList)) {
            return;
        }
        mongoTemplate.insert(aEventList, AEvent.class);
    }

    @Override
    public void importAFault() {
        List<AFault> aFaultList = faultDao.findAFaultList();
        if (StringUtils.isEmpty(aFaultList)) {
            return;
        }
        mongoTemplate.insert(aFaultList, AFault.class);
    }

    @Override
    public List<AEvent> findAEventAll() {
        List<AEvent> all = mongoTemplate.findAll(AEvent.class);
        return all;
    }

    @Override
    public List<AFault> findAFaultAll() {
        List<AFault> all = mongoTemplate.findAll(AFault.class);
        return all;
    }

    @Override
    public void deleteAEventAll() {
        mongoTemplate.remove(AEvent.class).all();
    }

    @Override
    public void deleteAFaultAll() {
        mongoTemplate.remove(AFault.class).all();
    }

    @Override
    public void deleteAEventById(Integer eventid) {
        Query query = new Query(Criteria.where("eventid").is(eventid));
        mongoTemplate.remove(query, AEvent.class);
    }

    @Override
    public void deleteAFaultById(Integer faultid) {
        Query query = new Query(Criteria.where("faultid").is(faultid));
        mongoTemplate.remove(query, AFault.class);
    }

    @Override
    public PageImpl<AEvent> findAEvent(AEvent aEvent, int page, int size) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (!StringUtils.isEmpty(aEvent.getEventid())) {
            criteria.and("eventid").is(aEvent.getEventid());
        }
        if (!StringUtils.isEmpty(aEvent.getDeviceid())) {
            criteria.and("deviceid").is(aEvent.getDeviceid());
        }
        if (!StringUtils.isEmpty(aEvent.getDevicename())) {
            Pattern pattern = Pattern.compile("^.*" + aEvent.getDevicename() + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("devicename").regex(pattern);
        }
        if (!StringUtils.isEmpty(aEvent.getEventstatus())) {
            criteria.and("eventstatus").is(aEvent.getEventstatus());
        }
        if (!StringUtils.isEmpty(aEvent.getEventtypeid())) {
            criteria.and("eventtypeid").is(aEvent.getEventtypeid());
        }
        if (!StringUtils.isEmpty(aEvent.getSend())) {
            criteria.and("send").is(aEvent.getSend());
        }
        if (!StringUtils.isEmpty(aEvent.getCompanyid())) {
            criteria.and("companyid").is(aEvent.getCompanyid());
        }
        if (!StringUtils.isEmpty(aEvent.getIstask())) {
            criteria.and("istask").is(aEvent.getIstask());
        }
        if (!StringUtils.isEmpty(aEvent.getCreatetime())) {
            criteria.and("createtime").gt(aEvent.getCreatetime());
        }
        query.addCriteria(criteria);
        Pageable pageable = PageRequest.of(page, size);
        //排序
        query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "eventid")));
        // 查询总数
        int count = (int) mongoTemplate.count(query, AEvent.class);
        query.with(pageable);
        List<AEvent> aEvents = mongoTemplate.find(query, AEvent.class);
        return (PageImpl<AEvent>) PageableExecutionUtils.getPage(aEvents, pageable, () -> count);
    }

    @Override
    public PageImpl<AFault> findAFault(AFault aFault, int page, int size) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (!StringUtils.isEmpty(aFault.getFaultid())) {
            criteria.and("faultid").is(aFault.getFaultid());
        }
        if (!StringUtils.isEmpty(aFault.getDeviceid())) {
            criteria.and("deviceid").is(aFault.getDeviceid());
        }
        if (!StringUtils.isEmpty(aFault.getDevicename())) {
            Pattern pattern = Pattern.compile("^.*" + aFault.getDevicename() + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("devicename").regex(pattern);
        }
        if (!StringUtils.isEmpty(aFault.getStatus())) {
            criteria.and("status").is(aFault.getStatus());
        }
        if (!StringUtils.isEmpty(aFault.getCompanyid())) {
            criteria.and("companyid").is(aFault.getCompanyid());
        }
        if (!StringUtils.isEmpty(aFault.getIstask())) {
            criteria.and("istask").is(aFault.getIstask());
        }
        if (!StringUtils.isEmpty(aFault.getStarttime())) {
            criteria.and("starttime").gt(aFault.getStarttime());
        }
        query.addCriteria(criteria);
        Pageable pageable = PageRequest.of(page, size);
        //排序
        query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "faultid")));
        // 查询总数
        int count = (int) mongoTemplate.count(query, AFault.class);
        query.with(pageable);
        List<AFault> aFaults = mongoTemplate.find(query, AFault.class);
        return (PageImpl<AFault>) PageableExecutionUtils.getPage(aFaults, pageable, () -> count);
    }

    /**
     * 根据eventid更新AEvent表对象
     *
     * @param aEvent
     * @return
     */
    public void updateAEvent(AEvent aEvent) {
        if (StringUtils.isEmpty(aEvent) || StringUtils.isEmpty(aEvent.getEventid())) {
            return;
        }
        Query query = new Query(Criteria.where("eventid").is(aEvent.getEventid()));
        Update update = new Update();
        if (StringUtils.isEmpty(aEvent.getEventtypeid())) {
            update.set("eventtypeid", aEvent.getEventtypeid());
        }
        if (StringUtils.isEmpty(aEvent.getEventstatus())) {
            update.set("eventstatus", aEvent.getEventstatus());
        }
        if (StringUtils.isEmpty(aEvent.getEndtime())) {
            update.set("endtime", aEvent.getEndtime());
        }
        if (StringUtils.isEmpty(aEvent.getCofirmuserid())) {
            update.set("cofirmuserid", aEvent.getCofirmuserid());
        }
        if (StringUtils.isEmpty(aEvent.getConfirmusername())) {
            update.set("confirmusername", aEvent.getConfirmusername());
        }
        if (StringUtils.isEmpty(aEvent.getConfirmsource())) {
            update.set("confirmsource", aEvent.getConfirmsource());
        }
        if (StringUtils.isEmpty(aEvent.getFalsealarmid())) {
            update.set("falsealarmid", aEvent.getFalsealarmid());
        }
        if (StringUtils.isEmpty(aEvent.getUpdatetime())) {
            update.set("updatetime", aEvent.getUpdatetime());
        }
        if (StringUtils.isEmpty(aEvent.getSend())) {
            update.set("send", aEvent.getSend());
        }
        if (StringUtils.isEmpty(aEvent.getIstask())) {
            update.set("istask", aEvent.getIstask());
        }
        mongoTemplate.updateMulti(query, update, AEvent.class);

        // updateFirst 更新查询返回结果集的第一条
        // mongoTemplate.updateFirst(query, update, AEvent.class);
        // updateMulti 更新查询返回结果集的全部
        // mongoTemplate.updateMulti(query,update, AEvent.class);
        // upsert 更新对象不存在则去添加
        // mongoTemplate.upsert(query,update,Book.class);
    }

    /**
     * 根据faultid更新AFault表对象
     *
     * @param aFault
     * @return
     */
    public void updateAFault(AFault aFault) {
        if (StringUtils.isEmpty(aFault) || StringUtils.isEmpty(aFault.getFaultid())) {
            return;
        }
        Query query = new Query(Criteria.where("faultid").is(aFault.getFaultid()));
        Update update = new Update();
        if (StringUtils.isEmpty(aFault.getStatus())) {
            update.set("status", aFault.getStatus());
        }
        if (StringUtils.isEmpty(aFault.getResponsetime())) {
            update.set("responsetime", aFault.getResponsetime());
        }
        if (StringUtils.isEmpty(aFault.getIstask())) {
            update.set("istask", aFault.getIstask());
        }
        mongoTemplate.updateMulti(query, update, AFault.class);
    }

    /**
     * AEvent表分页查询
     */
    public PageImpl<AEvent> pageAEventList(int page, int size) {
        Query query = new Query();
        Pageable pageable = PageRequest.of(page, size);
        //排序
        query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "eventid")));
        // 查询总数
        int count = (int) mongoTemplate.count(query, AEvent.class);
        query.with(pageable);
        List<AEvent> items = mongoTemplate.find(query, AEvent.class);
        return (PageImpl<AEvent>) PageableExecutionUtils.getPage(items, pageable, () -> count);
    }

    /**
     * AFault表分页查询
     */
    public PageImpl<AFault> pageAFaultList(int page, int size) {
        Query query = new Query();
        Pageable pageable = PageRequest.of(page, size);
        //排序
        query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "faultid")));
        // 查询总数
        int count = (int) mongoTemplate.count(query, AFault.class);
        query.with(pageable);
        List<AFault> items = mongoTemplate.find(query, AFault.class);
        return (PageImpl<AFault>) PageableExecutionUtils.getPage(items, pageable, () -> count);
    }

    @Override
    public <T> List<T> findT(String key, String value, Class<T> tClass) {
        Query query = new Query(Criteria.where(key).is(value));
        List<T> list = mongoTemplate.find(query, tClass);
        return list;
    }

    @Override
    public AEvent findAEventOne(String key, String value) {
        Query query = new Query(Criteria.where("eventid").is(35));
        AEvent aEvent = mongoTemplate.findOne(query, AEvent.class);
        return aEvent;
    }

    @Override
    public List<AEvent> getAEvent() {
        Query query = new Query();
        query.limit(2);
        query.addCriteria(Criteria.where("eventstatus").is("0").and("send").is("0"));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createtime")));
        return mongoTemplate.find(query, AEvent.class);
    }

}
