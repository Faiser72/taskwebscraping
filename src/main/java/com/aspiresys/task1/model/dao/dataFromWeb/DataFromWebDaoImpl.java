package com.aspiresys.task1.model.dao.dataFromWeb;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aspiresys.task1.beans.Channel.ChannelBean;

@Repository
public class DataFromWebDaoImpl implements DataFromWebDao {

	@Autowired
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public int save(Object object) {
		Serializable serializable = 0;
		Session session = getSession();
		try {
			serializable = session.save(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) serializable;
	}

	@Override
	public Boolean isPresent(String chan, String bean) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery("FROM " + bean + " WHERE channelName = ?0");
			query.setParameter(0, chan);
			listOfObjects = query.getResultList();
			return listOfObjects.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			return listOfObjects.isEmpty();
		}
	}

	@Override
	public List<?> getAll(String beanClassName) {
		Session session = getSession();
		List<?> listOfObjects = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE activeFlag = ?0");
			query.setParameter(0, 1);
			listOfObjects = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	@Override
	public boolean deleteChannel(ChannelBean channel) {
		Session session = getSession();
		try {
			session.delete(channel);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object getById(String beanClassName, int id) {
		Session session = getSession();
		Object object = null;
		try {
			Query<?> query = session.createQuery("FROM " + beanClassName + " WHERE activeFlag = ?0 AND channelId = ?1");
			query.setParameter(0, 1);
			query.setParameter(1, id);
			object = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public boolean update(Object object) {
		Session session = getSession();
		try {
			session.update(object);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
