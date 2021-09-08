package com.aspiresys.task1.model.service.channelContent;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aspiresys.task1.beans.Channel.ChannelBean;
import com.aspiresys.task1.beans.Channel.ChannelContentBean;
import com.aspiresys.task1.model.dao.channelContent.ChannelContentDao;

@Service
public class ChannelContentServiceImpl implements ChannelContentService {

	@Autowired
	private ChannelContentDao channelContectDao;

	@Override
	public int save(Object object) {
		return channelContectDao.save(object);
	}

	@Override
	public Boolean isContentPresent(String text, ChannelBean channelBean, String bean) {
		return channelContectDao.isContentPresent(text, channelBean, bean);
	}

	@Override
	public List<?> getAll(String beanClassName) {
		return channelContectDao.getAll(beanClassName);
	}

	@Transactional
	@Override
	public boolean deleteChannelContent(ChannelContentBean content) {
		return channelContectDao.deleteChannelContent(content);
	}

	@Override
	public Object getById(String beanClassName, int id) {
		return channelContectDao.getById(beanClassName, id);
	}

	@Override
	public boolean update(Object object) {
		return channelContectDao.update(object);
	}

	@Override
	public List<?> getContentByChannelId(String beanClassName, int id) {
		return channelContectDao.getContentByChannelId(beanClassName, id);
	}

	@Override
	public List<?> getChannelContentBtwnTime(String beanClassName, Date fromTime, Date toTime) {
		return channelContectDao.getChannelContentBtwnTime(beanClassName, fromTime, toTime);
	}

}
