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

package com.yazuo.erp.mkt.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.mkt.vo.MktTeamVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface MktTeamService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveMktTeam (MktTeamVO mktTeam);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertMktTeams (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateMktTeam (MktTeamVO mktTeam, SysUserVO user);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateMktTeamsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateMktTeamsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteMktTeamById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteMktTeamByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	MktTeamVO getMktTeamById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<MktTeamVO> getMktTeams (MktTeamVO mktTeam);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getMktTeamsMap (MktTeamVO mktTeam);
	
	/**根据teamId取支持该队的人员*/
	List<SysUserVO> getSupportUserByTeamId(Integer teamId);
	
	/**根据团队id和支持者id删除支持信息*/
	int deleteSupportByTeamIdAndUserId(Map<String, Object> paramMap);
	
	/**给某个团队添加支持者*/
	int saveSupporter(List<SysUserVO> supportList, SysUserVO user, Integer teamId);
	
	/**根据用户姓名取查询条件*/
	List<SysUserVO> getNoSupportCurrentTeamUser(SysUserVO user);
	
	long getMktTeamSupportCount(Integer teamId);
}