package com.ushine.solr.service;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
/**
 * 操作solr索引的通用接口<br>
 * 1:solr中要查询必须要进行分词<br>
 * 2:solr中的高亮直接取document没有效果，需要通过下面的方式：<br>
 * Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//对应的id的值
		Map<String, List<String>> map=highlighting.get("4028318158b444400158b49746570001");
		//取高亮的字段
		List<String> list=map.get("sex");
		for (String string : list) {
			//取高亮的值
			System.err.println(string);
		}
 * @author ococat
 * @param <T>
 */
public interface ISolrService<T> {
	/**
	 * 添加solr索引
	 * @param server HttpSolrServer
	 * @param daoStore dao层的bean模型
	 * @return 成功0，失败-1
	 */
	int addDocumentByStore(HttpSolrServer server, T daoStore);
	/**
	 * 添加索引集合
	 * @param server HttpSolrServer
	 * @param daoStore daoStore的List
	 * @return 成功返回list的size，失败-1
	 */
	int addDocumentByStores(HttpSolrServer server, List<T> daoStore);
	/**
	 * 创建新的solr索引
	 * @param server HttpSolrServer
	 */
	void createNewIndexByStores(HttpSolrServer server);
	/**
	 * 依据id删除一条索引
	 * @param server HttpSolrServer
	 * @param id 对象id
	 */
	void deleteDocumentById(HttpSolrServer server, String id);
	/**
	 * 根据id数组删除多条索引
	 * @param server HttpSolrServer
	 * @param ids id数组
	 */
	void deleteDocumentByIds(HttpSolrServer server, String[] ids);
	/**
	 * 删除所有索引记录
	 * @param server
	 */
	void deleteAll(HttpSolrServer server);

	void updateDocumentByStore(HttpSolrServer server, String id, T daoStore);
	
	long getDocumentsCount(HttpSolrServer server,Map<String, String> queryMap,String startDate,String endDate);
}
