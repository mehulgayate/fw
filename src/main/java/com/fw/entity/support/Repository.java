package com.fw.entity.support;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fw.entity.FileAttachment;
import com.fw.entity.GraphData;
import com.fw.entity.GraphData.GraphType;
import com.fw.entity.Post;
import com.fw.entity.User;
import com.fw.entity.User.Status;
import com.fw.web.AdminController;
import com.fw.web.support.DateTimeUtil;

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
		return (User) getSession().createQuery("FROM "+User.class.getName()+" where email=:email AND status!=:status")
				.setParameter("email", email)
				.setParameter("status", Status.DELETED)
				.uniqueResult();
	}

	public User findUserById(Long id){
		return (User) getSession().createQuery("FROM "+User.class.getName()+" where id=:id")
				.setParameter("id", id)
				.uniqueResult();
	}
	
	public Post findPostById(Long id){
		return (Post) getSession().createQuery("FROM "+Post.class.getName()+" where id=:id")
				.setParameter("id", id)
				.uniqueResult();
	}
	
	public FileAttachment findFileAttachmentById(Long id){
		return (FileAttachment) getSession().createQuery("FROM "+FileAttachment.class.getName()+" where id=:id")
				.setParameter("id", id)
				.uniqueResult();
	}
	
	public List<User> listUsersByName(String name){
		return getSession().createQuery("FROM "+User.class.getName()+" where name=:name")
				.setParameter("name", name)
				.list();
	}

	public List<User> listUsers(){
		return  getSession().createQuery("FROM "+User.class.getName())				
				.list();
	}

	public List<User> listLatestUsers(){
		return  getSession().createQuery("FROM "+User.class.getName()+" where date > :date")
				.setParameter("date", new Date((new Date().getTime()-(1000*60*60*24*7))))
				.list();
	}

	public List<User> listBlockedUsers(){
		return  getSession().createQuery("FROM "+User.class.getName()+" where status=:status")
				.setParameter("status", Status.BLACK_LISTED)
				.list();
	}


	public List<GraphData> listGraphData(){
		return getSession().createQuery("FROM "+GraphData.class.getName())
				.list();
	}

	public List<Post> listPostsByUser(User user){
		return getSession().createQuery("FROM "+Post.class.getName()+" p where p.user=:user AND p.status!=:status")
				.setParameter("user", user)
				.setParameter("status", com.fw.entity.Post.Status.ADMIN_VERIFICATION)
				.list();
	}
	
	
	public List<Post> listPostsForAdmin(){
		return getSession().createQuery("FROM "+Post.class.getName()+" p where p.status=:status")				
				.setParameter("status", com.fw.entity.Post.Status.ADMIN_VERIFICATION)
				.list();
	}

	public GraphData findGraphData(Date date, GraphType graphType){

		Date startTime=DateTimeUtil.getStartOfDay(date);
		Date endTime=DateTimeUtil.getLastHourDate(date);

		return (GraphData) getSession().createQuery("FROM "+GraphData.class.getName()+" gd where gd.date > :startTime AND gd.date < :endTime AND gd.graphType=:graphType")
				.setParameter("startTime", startTime)
				.setParameter("endTime", endTime)
				.setParameter("graphType", graphType)
				.uniqueResult();
	}

	public List<GraphData> listGraphData(GraphType graphType){	

		return getSession().createQuery("FROM "+GraphData.class.getName()+" where graphType=:graphType")				
				.setParameter("graphType", graphType)
				.list();
	}
}
