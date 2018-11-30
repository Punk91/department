package ru.example.department.service.core.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import ru.example.department.model.core.dto.Filter;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BaseSpecification<ENTITY> implements Specification<ENTITY> {

    private List<Filter> filters;

    public BaseSpecification(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = null;
        List<Predicate> predicates = new ArrayList<>();

        for (Filter filter : filters) {
            if (filter != null && filter.getType() != null) {
                switch (filter.getType()) {
                    case NUMERIC: {
                        predicate = this.toNumericPredicate(root, criteriaBuilder, filter);
                        break;
                    }
                    case STRING: {
                        predicate = this.toStringPredicate(root, criteriaBuilder, filter);
                        break;
                    }
                    default:
                        log.warn("Unable to create predicate for filter : ", filter);
                }
            }

            if (predicate != null) {
                predicates.add(predicate);
            }
        }

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate toNumericPredicate(Root root, CriteriaBuilder criteriaBuilder, Filter filter) {
        Predicate predicate = null;
        switch (filter.getComparison()) {
            case GREATER_THAN: {
                Long value = Long.parseLong(filter.getValue().toString());
                Path path = root.get(filter.getField());
                predicate = criteriaBuilder.greaterThan(path, value);
                break;
            }
            case LESS_THAN: {
                Long value = Long.parseLong(filter.getValue().toString());
                Path path = root.get(filter.getField());
                predicate = criteriaBuilder.lessThan(path, value);
                break;
            }
            case EQUAL: {
                Long value = Long.parseLong(filter.getValue().toString());
                Path path = root.get(filter.getField());
                predicate = criteriaBuilder.equal(path, value);
                break;
            }
            default:
            log.warn("Wrong comparison, filter : {}", filter);
        }
        return predicate;
    }

    private Predicate toStringPredicate(Root root, CriteriaBuilder criteriaBuilder, Filter filter) {
        Path path = root.get(filter.getField());
        return criteriaBuilder.like(path, "%" + filter.getValue() + "%");
    }


}
