package com.ps.cff.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ps.cff.entity.ForecastManager;

@SuppressWarnings("serial")
public class ForecastManagerSpecification implements Specification<ForecastManager> {

	private SearchCriteria criteria;
	
	@Override
	public Predicate toPredicate(Root<ForecastManager> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
            	
                return criteriaBuilder.equal(
                  root.<String>get(criteria.getKey()),criteria.getValue());
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
		}
		return null;
	}

	public ForecastManagerSpecification(SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}
	
}
