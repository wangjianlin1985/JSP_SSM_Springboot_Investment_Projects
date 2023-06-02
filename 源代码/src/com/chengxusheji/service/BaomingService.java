package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Project;
import com.chengxusheji.po.Company;
import com.chengxusheji.po.Baoming;

import com.chengxusheji.mapper.BaomingMapper;
@Service
public class BaomingService {

	@Resource BaomingMapper baomingMapper;
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

    /*添加项目报名记录*/
    public void addBaoming(Baoming baoming) throws Exception {
    	baomingMapper.addBaoming(baoming);
    }

    /*按照查询条件分页查询项目报名记录*/
    public ArrayList<Baoming> queryBaoming(Project projectObj,Company companyObj,String baomingTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != projectObj && projectObj.getProjectId()!= null && projectObj.getProjectId()!= 0)  where += " and t_baoming.projectObj=" + projectObj.getProjectId();
    	if(null != companyObj &&  companyObj.getCompanyUserName() != null  && !companyObj.getCompanyUserName().equals(""))  where += " and t_baoming.companyObj='" + companyObj.getCompanyUserName() + "'";
    	if(!baomingTime.equals("")) where = where + " and t_baoming.baomingTime like '%" + baomingTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return baomingMapper.queryBaoming(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Baoming> queryBaoming(Project projectObj,Company companyObj,String baomingTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != projectObj && projectObj.getProjectId()!= null && projectObj.getProjectId()!= 0)  where += " and t_baoming.projectObj=" + projectObj.getProjectId();
    	if(null != companyObj &&  companyObj.getCompanyUserName() != null && !companyObj.getCompanyUserName().equals(""))  where += " and t_baoming.companyObj='" + companyObj.getCompanyUserName() + "'";
    	if(!baomingTime.equals("")) where = where + " and t_baoming.baomingTime like '%" + baomingTime + "%'";
    	return baomingMapper.queryBaomingList(where);
    }

    /*查询所有项目报名记录*/
    public ArrayList<Baoming> queryAllBaoming()  throws Exception {
        return baomingMapper.queryBaomingList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Project projectObj,Company companyObj,String baomingTime) throws Exception {
     	String where = "where 1=1";
    	if(null != projectObj && projectObj.getProjectId()!= null && projectObj.getProjectId()!= 0)  where += " and t_baoming.projectObj=" + projectObj.getProjectId();
    	if(null != companyObj &&  companyObj.getCompanyUserName() != null && !companyObj.getCompanyUserName().equals(""))  where += " and t_baoming.companyObj='" + companyObj.getCompanyUserName() + "'";
    	if(!baomingTime.equals("")) where = where + " and t_baoming.baomingTime like '%" + baomingTime + "%'";
        recordNumber = baomingMapper.queryBaomingCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取项目报名记录*/
    public Baoming getBaoming(int baomingId) throws Exception  {
        Baoming baoming = baomingMapper.getBaoming(baomingId);
        return baoming;
    }

    /*更新项目报名记录*/
    public void updateBaoming(Baoming baoming) throws Exception {
        baomingMapper.updateBaoming(baoming);
    }

    /*删除一条项目报名记录*/
    public void deleteBaoming (int baomingId) throws Exception {
        baomingMapper.deleteBaoming(baomingId);
    }

    /*删除多条项目报名信息*/
    public int deleteBaomings (String baomingIds) throws Exception {
    	String _baomingIds[] = baomingIds.split(",");
    	for(String _baomingId: _baomingIds) {
    		baomingMapper.deleteBaoming(Integer.parseInt(_baomingId));
    	}
    	return _baomingIds.length;
    }
}
