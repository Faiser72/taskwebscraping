package com.aspiresys.task1.model.dao.channelContent;

import java.util.Date;
import java.util.List;

import com.aspiresys.task1.beans.Channel.ChannelBean;
import com.aspiresys.task1.beans.Channel.ChannelContentBean;

public interface ChannelContentDao {

	int save(Object object);

	Boolean isContentPresent(String text, ChannelBean channelBean, String bean);

	List<?> getAll(String beanClassName);

	boolean deleteChannelContent(ChannelContentBean content);

	Object getById(String beanClassName, int id);

	boolean update(Object object);

	List<?> getContentByChannelId(String beanClassName, int id);

	List<?> getChannelContentBtwnTime(String beanClassName, Date fromTime, Date toTime);

}
