/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * @author Jijo Raju
 * @param <T>
 * @Since May 16, 2021 1:57:01 PM
 *
 */
public class CommonSearchSpecification<T> implements Specification<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient List<SearchCriteria> criterias;

	public CommonSearchSpecification() {
		this.criterias = new ArrayList<>();
	}

	public void add(SearchCriteria criteria) {
		criterias.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		List<Predicate> predicates = new ArrayList<>();

		for (SearchCriteria criteria : criterias) {
			if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
				predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			}
			else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
				predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			}
			else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
				predicates
						.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
			}
			else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
				predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
			}
			else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
				predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
			}
			else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
				if (criteria.getKey().equals("subject") || criteria.getKey().equals("unit")
						|| criteria.getKey().equals("chapter")) {
					predicates.add(builder.equal(root.get(criteria.getKey()).get("uniqueId"), criteria.getValue()));
				}
				else {
					predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
				}

			}
			else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase() + "%"));
			}
			else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						criteria.getValue().toString().toLowerCase() + "%"));
			}
			else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase()));
			}
			else if (criteria.getOperation().equals(SearchOperation.IN)) {
				predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
			}
			else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
				predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
			}
			else if (criteria.getOperation().equals(SearchOperation.BETWEEN) && criteria.getValue() instanceof Date[]) {
				Date[] dates = (Date[]) criteria.getValue();
				predicates.add(builder.between(root.get(criteria.getKey()), dates[0], dates[1]));
			}
		}
		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
