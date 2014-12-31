package com.fw.entity.support;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fw.entity.GraphData;
import com.fw.entity.User;

@Transactional
public class Repository {
	
	@Resource
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
	public User findUserByEmail(String email){
		return (User) getSession().createQuery("FROM "+User.class.getName()+" where email=:email")
				.uniqueResult();
	}
	
	
	public List<GraphData> listGraphData(){
		return getSession().createQuery("FROM "+GraphData.class.getName())
				.list();
	}	
}
