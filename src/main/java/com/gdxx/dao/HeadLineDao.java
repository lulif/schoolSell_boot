package com.gdxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdxx.entity.HeadLine;

public interface HeadLineDao {
	List<HeadLine> queryHeadLineList(@Param("headLineCondition") HeadLine headLineCondition);
}
