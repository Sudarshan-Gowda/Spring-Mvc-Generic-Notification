package com.star.sud.gen.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.star.sud.gen.dao.GenericDao;

@SuppressWarnings("unchecked")
@Repository
public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K> {

	// Static Attributes
	///////////////////////
	private static Logger log = Logger.getLogger(GenericDaoImpl.class);

	// Attributes
	/////////////
	@Autowired
	protected SessionFactory sessionFactory;
	protected Class<? extends E> daoType;

	// Constructor
	//////////////
	@SuppressWarnings("rawtypes")
	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		daoType = (Class) pt.getActualTypeArguments()[0];
	}

	// Interface Methods
	/////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#add(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void add(E entity) throws Exception {
		log.debug("add():...");
		try {
			currentSession().save(entity);
		} catch (Exception ex) {
			log.error("add", ex);
			throw ex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#saveOrUpdate(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void saveOrUpdate(E entity) throws Exception {
		log.debug("saveOrUpdate");
		try {
			currentSession().saveOrUpdate(entity);
			log.debug("saveOrUpdate done...");

		} catch (Exception ex) {
			log.error("saveOrUpdate", ex);
			throw ex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#update(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void update(E entity) throws Exception {
		log.debug("update():...");
		try {
			currentSession().saveOrUpdate(entity);
		} catch (Exception ex) {
			log.error("update", ex);
			throw ex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#remove(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

	public void remove(E entity) throws Exception {
		log.debug("remove():...");
		try {
			currentSession().delete(entity);
		} catch (Exception ex) {
			log.error("remove", ex);
			throw ex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#find(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public E find(K key) throws Exception {
		try {
			log.debug("find():...");
			return (E) currentSession().get(daoType, key);
		} catch (Exception ex) {
			log.error("find", ex);
			throw ex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#getAll()
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<E> getAll() throws Exception {
		log.debug("getAll():...");
		try {
			return currentSession().createCriteria(daoType).list();
		} catch (Exception ex) {
			log.error("getAll", ex);
			throw ex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#getAll(java.util.List, java.util.List)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<E> getAll(List<String> fieldsAsc, List<String> fieldsDesc) throws Exception {
		log.debug("getAllOrdered():...");
		try {
			Criteria crit = currentSession().createCriteria(daoType);
			if (null != fieldsAsc) {
				for (String field : fieldsAsc) {
					crit.addOrder(Order.asc(field));
				}
			}
			if (null != fieldsDesc) {
				for (String field : fieldsDesc) {
					crit.addOrder(Order.desc(field));
				}
			}
			List<E> entities = crit.list();
			return entities;
		} catch (Exception ex) {
			log.error("getAllOrdered", ex);
			throw ex;
		}
	}

	/**
	 * @param criterias
	 * @param fieldDateStartCriteria
	 * @param fieldDateEndCriteria
	 * @param fieldsAsc
	 * @param fieldsDesc
	 * @param limit
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<E> getByLikeCriteria(Map<String, Object> criterias, Map<String, Object> fieldDateStartCriteria,
			Map<String, Object> fieldDateEndCriteria, List<String> fieldsAsc, List<String> fieldsDesc, int limit,
			int offset) throws Exception {
		log.debug("getByCriteria():...");
		try {
			String rootAlias = daoType.getSimpleName();
			Criteria crit = currentSession().createCriteria(daoType, rootAlias);
			for (String key : criterias.keySet()) {

				if (key.contains(".")) {
					String subTypeName = key.substring(0, key.indexOf("."));
					String rootPlusSubTypeName = rootAlias + "." + subTypeName;
					String attr = subTypeName + "." + key.substring(key.indexOf(".") + 1);
					crit.createAlias(rootPlusSubTypeName, subTypeName);

					if (StringUtils.contains(String.valueOf(criterias.get(key)), "%")) {
						crit.add(Restrictions.like(attr, criterias.get(key)));
					} else {
						crit.add(Restrictions.eq(attr, criterias.get(key)));
					}

				} else {
					if (criterias.get(key) instanceof Date) {
					} else {
						if (StringUtils.contains(String.valueOf(criterias.get(key)), "%")) {
							crit.add(Restrictions.like(key, criterias.get(key)));
						} else {
							crit.add(Restrictions.eq(key, criterias.get(key)));
						}
					}
				}

			}

			if (null != fieldDateStartCriteria) {
				for (String key : fieldDateStartCriteria.keySet()) {
					crit.add(Restrictions.ge(key, fieldDateStartCriteria.get(key)));
				}
			}

			if (null != fieldDateEndCriteria) {
				for (String key : fieldDateEndCriteria.keySet()) {
					crit.add(Restrictions.le(key, fieldDateEndCriteria.get(key)));
				}
			}

			if (null != fieldsAsc) {
				for (String field : fieldsAsc) {
					crit.addOrder(Order.asc(field));
				}
			}
			if (null != fieldsDesc) {
				for (String field : fieldsDesc) {
					crit.addOrder(Order.desc(field));
				}
			}
			if (limit != 0 || limit > 0) {
				crit.setMaxResults(limit);
			}
			if (offset > 0) {
				crit.setFirstResult(offset);
			}

			List<E> entities = crit.list();
			return entities;
		} catch (Exception ex) {
			log.error("getByCriteria", ex);
			throw ex;
		}
	}

	/**
	 * @param hql
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int count(String hql, Map<String, Object> parameters) throws Exception {
		log.debug("count by hql...");
		try {

			Query query = currentSession().createQuery(hql);
			if (null != parameters) {
				for (String param : parameters.keySet()) {
					Object value = parameters.get(param);
					if (value instanceof Collection) {
						query.setParameterList(param, (Collection) value);
					} else {
						query.setParameter(param, value);
					}
				}
			}

			Number number = (Number) query.uniqueResult();

			return number.intValue();

		} catch (Exception ex) {
			log.error("getByQuery", ex);
			throw ex;
		}
	}

	/**
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int count(String hql) throws Exception {
		log.debug("count by hql...");
		try {
			Query query = currentSession().createQuery(hql);
			Number number = (Number) query.uniqueResult();

			return number.intValue();

		} catch (Exception ex) {
			log.error("getByQuery", ex);
			throw ex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#getByQuery(java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

	public List<E> getByQuery(String hql) throws Exception {
		log.debug("getByQuery():...");
		try {
			Query query = currentSession().createQuery(hql);
			List<E> results = query.list();
			return results;
		} catch (Exception ex) {
			log.error("getByQuery", ex);
			throw ex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#getByQuery(java.lang.String,
	 * java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<E> getByQuery(String hql, Map<String, Object> parameters) throws Exception {
		log.debug("getByQuery():...");
		try {
			Query query = currentSession().createQuery(hql);
			if (null != parameters) {
				for (String param : parameters.keySet()) {
					Object value = parameters.get(param);
					if (value instanceof Collection) {
						query.setParameterList(param, (Collection) value);
					} else {
						query.setParameter(param, value);
					}
				}
			}
			List<E> results = query.list();
			return results;
		} catch (Exception ex) {
			log.error("getByQuery", ex);
			throw ex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#getByQuery(java.lang.String,
	 * java.util.Map, int, int)
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<E> getByQuery(String hql, Map<String, Object> parameters, int limit, int offset) {

		Query query = currentSession().createQuery(hql);
		if (null != parameters) {
			for (String param : parameters.keySet()) {
				Object value = parameters.get(param);
				if (value instanceof Collection) {
					query.setParameterList(param, (Collection) value);
				} else if (value instanceof Date) {
					query.setTimestamp(param, (Date) value);
				} else {
					query.setParameter(param, value);
				}
			}
		}

		if (limit != 0 || limit > 0) {
			query.setMaxResults(limit);
		}
		if (offset > 0) {
			query.setFirstResult(offset);
		}

		List<E> results = query.list();
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#executeNativeSQL(java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int executeNativeSQL(String nativeSQL) {
		SQLQuery query = currentSession().createSQLQuery(nativeSQL);
		return query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.gen.dao.GenericDao#getByNativeSQL(java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<E> getByNativeSQL(String nativeSQL) {
		SQLQuery query = currentSession().createSQLQuery(nativeSQL);
		return query.list();
	}

	// Properties
	/////////////
	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the currentSession
	 */
	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

}