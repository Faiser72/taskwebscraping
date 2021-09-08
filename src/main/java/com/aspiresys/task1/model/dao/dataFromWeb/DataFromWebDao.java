package com.aspiresys.task1.model.dao.dataFromWeb;

import java.util.List;

import com.aspiresys.task1.beans.Channel.ChannelBean;

public interface DataFromWebDao {

	int save(Object object);

	Boolean isPresent(String chan, String bean);

	List<?> getAll(String beanClassName);

	boolean deleteChannel(ChannelBean channel);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

}
