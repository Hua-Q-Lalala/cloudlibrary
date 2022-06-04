package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.domain.Record;

public interface RecordMapper {
	
	//新增借阅记录
	Integer addRecord(Record record);

	//查询借阅记录
	Page<Record> searchRecords(Record record);
	
}
