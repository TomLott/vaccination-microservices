package ru.tfs.person.repository.specification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.tfs.person.model.entity.Person;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class PersonSpecification implements Specification<Person> {

    private SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (searchCriteria.getOperation().equals(":")) {
            return criteriaBuilder.like(root.<String>get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");
        }
        return null;
    }
}
