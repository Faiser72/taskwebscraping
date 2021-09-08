package com.aspiresys.task1.model.service.dataFromWeb;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspiresys.task1.beans.Channel.ChannelBean;
import com.aspiresys.task1.model.dao.dataFromWeb.DataFromWebDao;

@Service
public class DataFromWebServiceImpl implements DataFromWebService {

	@Autowired
	private DataFromWebDao dataFromWebDao;

	@Override
	public int save(Object object) {
		return dataFromWebDao.save(object);
	}

	@Override
	public Boolean isPresent(String chan, String bean) {
		return dataFromWebDao.isPresent(chan, bean);
	}

	@Override
	public List<?> getAll(String beanClassName) {
		return dataFromWebDao.getAll(beanClassName);
	}

	@Transactional
	@Override
	public boolean deleteChannel(ChannelBean channel) {
		return dataFromWebDao.deleteChannel(channel);
	}

	@Override
	public Object getById(String beanClassName, int id) {
		return dataFromWebDao.getById(beanClassName, id);
	}

	@Override
	public boolean update(Object object) {
		return dataFromWebDao.update(object);
	}

}
