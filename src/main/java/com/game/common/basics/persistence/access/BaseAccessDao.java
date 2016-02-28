package com.game.common.basics.persistence.access;

import java.util.List;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;

public interface BaseAccessDao<T> {
	public List<T> pageQuery(PageQuery querys) throws DataAccessException;

	public List<T> selectAll() throws DataAccessException;
	
	public List<T> selectByEntity(T bean) throws DataAccessException;
	
	public T select(Integer id) throws DataAccessException;

	public int insert(T entity) throws DataAccessException;

	public int update(T entity) throws DataAccessException;

	public int delete(Integer id) throws DataAccessException;

	public void deleteSelect(Integer[] ids) throws DataAccessException;
}
