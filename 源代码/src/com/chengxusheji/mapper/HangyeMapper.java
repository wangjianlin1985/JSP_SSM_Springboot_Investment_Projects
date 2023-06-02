package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Hangye;

public interface HangyeMapper {
	/*添加行业信息*/
	public void addHangye(Hangye hangye) throws Exception;

	/*按照查询条件分页查询行业记录*/
	public ArrayList<Hangye> queryHangye(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有行业记录*/
	public ArrayList<Hangye> queryHangyeList(@Param("where") String where) throws Exception;

	/*按照查询条件的行业记录数*/
	public int queryHangyeCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条行业记录*/
	public Hangye getHangye(int hangyeId) throws Exception;

	/*更新行业记录*/
	public void updateHangye(Hangye hangye) throws Exception;

	/*删除行业记录*/
	public void deleteHangye(int hangyeId) throws Exception;

}
