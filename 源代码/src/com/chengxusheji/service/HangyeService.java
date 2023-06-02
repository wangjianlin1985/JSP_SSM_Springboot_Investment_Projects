package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Hangye;

import com.chengxusheji.mapper.HangyeMapper;
@Service
public class HangyeService {

	@Resource HangyeMapper hangyeMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加行业记录*/
    public void addHangye(Hangye hangye) throws Exception {
    	hangyeMapper.addHangye(hangye);
    }

    /*按照查询条件分页查询行业记录*/
    public ArrayList<Hangye> queryHangye(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return hangyeMapper.queryHangye(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Hangye> queryHangye() throws Exception  { 
     	String where = "where 1=1";
    	return hangyeMapper.queryHangyeList(where);
    }

    /*查询所有行业记录*/
    public ArrayList<Hangye> queryAllHangye()  throws Exception {
        return hangyeMapper.queryHangyeList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = hangyeMapper.queryHangyeCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取行业记录*/
    public Hangye getHangye(int hangyeId) throws Exception  {
        Hangye hangye = hangyeMapper.getHangye(hangyeId);
        return hangye;
    }

    /*更新行业记录*/
    public void updateHangye(Hangye hangye) throws Exception {
        hangyeMapper.updateHangye(hangye);
    }

    /*删除一条行业记录*/
    public void deleteHangye (int hangyeId) throws Exception {
        hangyeMapper.deleteHangye(hangyeId);
    }

    /*删除多条行业信息*/
    public int deleteHangyes (String hangyeIds) throws Exception {
    	String _hangyeIds[] = hangyeIds.split(",");
    	for(String _hangyeId: _hangyeIds) {
    		hangyeMapper.deleteHangye(Integer.parseInt(_hangyeId));
    	}
    	return _hangyeIds.length;
    }
}
