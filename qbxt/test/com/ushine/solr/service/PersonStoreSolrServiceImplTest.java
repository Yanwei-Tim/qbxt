package com.ushine.solr.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ushine.common.utils.SpringUtils;
import com.ushine.dao.IBaseDao;
import com.ushine.solr.factory.SolrServerFactory;
import com.ushine.storesinfo.model.PersonStore;

/**
 * 测试人员的索引是否添加成功
 * 
 * @author Administrator
 *
 */

@Component("PersonStoreSolrServiceImplTest")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class PersonStoreSolrServiceImplTest {
	private Logger logger = Logger.getLogger(PersonStoreSolrServiceImplTest.class);
	ISolrService<PersonStore> solrService = null;
	String id = "4028318158b444400158b49746570001";
	/**
	 * 测试添加一个
	 */
	@Autowired
	IBaseDao<PersonStore, Serializable> baseDao;

	@Test
	@Ignore
	public void testAddDocumentByStore() throws Exception {
		solrService = getSolrService();
		PersonStore store = baseDao.findById(PersonStore.class, id);
		logger.warn(store.getCreateDate());
		assertEquals(id, store.getId());
		// 添加solr
		assertTrue(solrService != null);
		HttpSolrServer server = getSolrServer();
		solrService.addDocumentByStore(server, store);
	}
	/**
	 * 测试添加集合
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testAddDocumentByStores() throws Exception {
		solrService = getSolrService();
		HttpSolrServer server = getSolrServer();
		List<PersonStore> list = baseDao.findAll(PersonStore.class);
		int count = solrService.addDocumentByStores(server, list);
		assertEquals(list.size(), count);
	}
	/**
	 * 测试删除所有
	 */
	@Test
	@Ignore
	public void testDeleteAll() {
		solrService = getSolrService();
		solrService.deleteAll(getSolrServer());
	}
	
	/**
	 * 测试创建新的
	 */
	@Test
	//@Ignore
	public void testCreateNewIndexByStores() {
		getSolrService().createNewIndexByStores(getSolrServer());
	}
	/**
	 * 测试删除一个或多个
	 */
	@Test
	@Ignore
	public void testDeleteByIds() {
		getSolrService().deleteDocumentByIds(getSolrServer(), new String[] { id });
	}
	/**
	 * 测试更新
	 */
	@Test
	@Ignore
	public void testUpdate(){
		PersonStore store=new PersonStore();
		store.setPersonName("谷歌公司");
		store.setCreateDate("2017-02-17 00:00:00");
		getSolrService().updateDocumentByStore(getSolrServer(), id, store);
	}
	/**
	 * 测试数量
	 */
	@Test
	public void testGetCount(){
		Map<String, String> queryMap=new HashMap<>();
		queryMap.put("personstoreAll", "*");
		queryMap.put("uid", "40288a625668a0f6015668a151a00004");
		queryMap.put("oid", null);
		queryMap.put("did", null);
		long result=getSolrService().getDocumentsCount(getSolrServer(), queryMap, "2010-02-17", "2017-02-18");
		assertEquals(2, result);
		//
		queryMap.put("uid", null);
		queryMap.put("oid", "40288a625668a0f6015668a151940002");
		queryMap.put("did", null);
		assertEquals(1, getSolrService().getDocumentsCount(getSolrServer(), queryMap, "2010-02-17", "2017-02-18"));
		
		queryMap.put("uid", null);
		queryMap.put("oid", null);
		queryMap.put("did", null);
		assertEquals(1, getSolrService().getDocumentsCount(getSolrServer(), queryMap, "2010-01-25", "2017-01-25"));
		assertEquals(0, getSolrService().getDocumentsCount(getSolrServer(), queryMap, "2010-01-25", "2011-01-25"));
	}

	private ISolrService<PersonStore> getSolrService() {
		return (ISolrService<PersonStore>) SpringUtils.getBean("personStoreSolrServiceImpl");
	}

	private HttpSolrServer getSolrServer() {
		HttpSolrServer server = SolrServerFactory.getPSSolrServerInstance();
		return server;
	}
}