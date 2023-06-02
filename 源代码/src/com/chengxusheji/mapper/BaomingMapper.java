package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Baoming;

public interface BaomingMapper {
	/*添加项目报名信息*/
	public void addBaoming(Baoming baoming) throws Exception;

	/*按照查询条件分页查询项目报名记录*/
	public ArrayList<Baoming> queryBaoming(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有项目报名记录*/
	public ArrayList<Baoming> queryBaomingList(@Param("where") String where) throws Exception;

	/*按照查询条件的项目报名记录数*/
	public int queryBaomingCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条项目报名记录*/
	public Baoming getBaoming(int baomingId) throws Exception;

	/*更新项目报名记录*/
	public void updateBaoming(Baoming baoming) throws Exception;

	/*删除项目报名记录*/
	public void deleteBaoming(int baomingId) throws Exception;

}
