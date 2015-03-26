/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.statistics.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-7 下午3:04:34
 */
@Repository
public class MerchantCardCountStatisticsDao extends BaseDAO {

	public List<Map<String, Object>> merchantCardCountStatistics(int type, int count, int quantity) {
		String selectCardTypeName = "SELECT id , cardtype from trade.cardtype  where status = 0";
		List<Map<String, Object>> cardTypeList = jdbcTemplateCRMTrade.queryForList(selectCardTypeName);

		String selectMerchantIdName = "SELECT  merchant_id,merchant_name from trade.merchant  where status = 1  order by merchant_id desc";
		List<Map<String, Object>> merchantIdList = jdbcTemplateCRMTrade.queryForList(selectMerchantIdName);

		// 查询所有商户卡余量信息
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT  sum_a.merchant_id ,");
		sql.append(" 	cbh.cardtype_id  ,");
		sql.append(" 	sum_a.no_use ");
		if (type == 3) {
			sql.append(" 	, cbh.quantity ");
		}
		sql.append(" from  trade.card_batch  cbh,");
		sql.append(" ( SELECT");
		sql.append(" 	b.merchant_id,");
		sql.append("   b.batch_id,");
		if (type == 3) {
			sql.append("  b.cardtype_id,");
		}
		sql.append(" 	COUNT (*) no_use");
		sql.append(" FROM trade.card_record r,");
		sql.append(" 	( SELECT");
		sql.append(" 			MAX (batch_id) AS batch_id,");
		if (type == 3) {
			sql.append(" CASE when cardtype_id is null then 0 else cardtype_id end as cardtype_id, ");
		}
		sql.append(" 			merchant_id");
		sql.append(" 		FROM");
		sql.append(" 			trade.card_batch");
		sql.append(" 		WHERE");
		sql.append(" 			card_batch = " + type);
		if (type == 3) {
			sql.append(" 			and quantity >= " + quantity);
		}
		sql.append(" 		GROUP BY");
		sql.append(" 			merchant_id");
		if (type == 3) {
			sql.append(" 	, cardtype_id");
		}
		sql.append(" 	) AS b");
		sql.append(" WHERE");
		sql.append(" 	r.batch_id = b.batch_id");
		sql.append(" AND r.activation_flag = FALSE");
		sql.append(" GROUP BY");
		sql.append(" 	b.merchant_id,");
		if (type == 3) {
			sql.append("  b.cardtype_id,");
		}
		sql.append(" b.batch_id");
		sql.append(" HAVING");
		sql.append(" 	COUNT (r.card_no) <= " + count);
		sql.append(" ORDER BY b.merchant_id");
		sql.append(" ) as sum_a");
		sql.append(" where sum_a.batch_id = cbh.batch_id");
		sql.append(" ORDER BY sum_a.merchant_id");

		List<Map<String, Object>> cardTypeCountList = jdbcTemplateCRMTrade.queryForList(sql.toString());

		Map<String, String> merchantIdName = new HashMap<String, String>();
		for (Map<String, Object> map : merchantIdList) {
			merchantIdName.put(map.get("merchant_id").toString(), map.get("merchant_name").toString());
		}

		Map<String, String> cardTypeName = new HashMap<String, String>();
		for (Map<String, Object> map : cardTypeList) {
			cardTypeName.put(map.get("id").toString(), map.get("cardtype").toString());
		}

		List<Map<String, Object>> remove = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : cardTypeCountList) {
			if (null == map.get("cardtype_id")) {
				map.put("cardtype_name", "无卡类型");
			} else {
				map.put("cardtype_name", cardTypeName.get(map.get("cardtype_id").toString()));
			}
			if (merchantIdName.get(map.get("merchant_id").toString()) == null) {
				remove.add(map);
			} else {
				map.put("merchant_name", merchantIdName.get(map.get("merchant_id").toString()));
			}
		}
		cardTypeCountList.removeAll(remove);
		return cardTypeCountList;
	}

}