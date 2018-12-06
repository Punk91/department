package ru.example.department.service.core.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import ru.example.department.model.core.dto.Filter;
import ru.example.department.util.BasicUtils;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
                predicate = this.getPredicate(root, criteriaBuilder, filter);
            }
            if (predicate != null) {
                predicates.add(predicate);
            }
        }
        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


    private Predicate getPredicate(Root root, CriteriaBuilder criteriaBuilder, Filter filter) {
        Predicate predicate = null;
        switch (filter.getType()) {
            case NUMERIC: {
                predicate = this.toNumericPredicate(root, criteriaBuilder, filter);
                break;
            }
            case STRING: {
                predicate = this.toStringPredicate(root, criteriaBuilder, filter);
                break;
            }
            case DATE: {
                try {
                    Date date = BasicUtils.simpleDateFormat.get().parse((String) filter.getValues().get(0));
                    predicate = this.toDatePredicate(root, criteriaBuilder, filter, date);
                } catch (ParseException e) {
                    log.error("Parsing error : {}", filter.getValues().get(0), e);
                }
                break;
            }
            case DATE_TIME: {
                try {
                    Date date = BasicUtils.simpleDateTimeFormat.get().parse((String) filter.getValues().get(0));
                    predicate = this.toDatePredicate(root, criteriaBuilder, filter, date);
                } catch (ParseException e) {
                    log.error("Parsing error : {}", filter.getValues().get(0), e);
                }
                break;
            }
            case LIST: {
                predicate = this.toPredicateFromList(root, filter);
                break;
            }
            case BOOLEAN: {
                Path<Boolean> path = root.get(filter.getField());
                predicate = criteriaBuilder.equal(path, filter.getValues().get(0));
                break;
            }
            default:
                log.warn("Unable to create predicate for filter : ", filter);
        }
        return predicate;
    }


    private Predicate toNumericPredicate(Root root, CriteriaBuilder criteriaBuilder, Filter filter) {
        Predicate predicate = null;
        switch (filter.getComparison()) {
            case GT: {
                Long value = Long.parseLong((String) filter.getValues().get(0));
                Path<Long> path = root.get(filter.getField());
                predicate = criteriaBuilder.greaterThan(path, value);
                break;
            }
            case LT: {
                Long value = Long.parseLong((String) filter.getValues().get(0));
                Path<Long> path = root.get(filter.getField());
                predicate = criteriaBuilder.lessThan(path, value);
                break;
            }
            case EQ: {
                Long value = Long.parseLong((String) filter.getValues().get(0));
                Path<Long> path = root.get(filter.getField());
                predicate = criteriaBuilder.equal(path, value);
                break;
            }
            default:
            log.warn("Wrong comparison, filter : {}", filter);
        }
        return predicate;
    }


    private Predicate toStringPredicate(Root root, CriteriaBuilder criteriaBuilder, Filter filter) {
        Path<String> path = root.get(filter.getField());
        return criteriaBuilder.like(path, "%" + filter.getValues().get(0) + "%");
    }


    private Predicate toDatePredicate(Root root, CriteriaBuilder criteriaBuilder, Filter filter, Date date) {
        Predicate predicate = null;
        switch (filter.getComparison()) {
            case GT: {
                Path<Date> path = root.get(filter.getField());
                predicate = criteriaBuilder.greaterThan(path, date);
                break;
            }
            case LT: {
                Path<Date> path = root.get(filter.getField());
                predicate = criteriaBuilder.lessThan(path, date);
                break;
            }
            case EQ: {
                Path<Date> path = root.get(filter.getField());
                predicate = criteriaBuilder.between(path, date, new Date(date.getTime()+24*60*60*1000));
                break;
            }
            default:
                log.warn("Wrong comparison, filter : {}", filter);
        }
        return predicate;
    }


    private Predicate toPredicateFromList(Root root, Filter filter) {
        Predicate predicate = null;
        List<Object> filterValues = (List<Object>) filter.getValues();
        Expression expression = root.get(filter.getField());
        if (expression.getJavaType().isEnum()) {
            List enumList = new ArrayList<>();
            for (Object value : filterValues) {
                try {
                    Object obj = expression.getJavaType().getDeclaredMethod("valueOf", String.class).invoke(null, value);
                    enumList.add(obj);
                } catch (Exception e) {
                    log.error("Error in BaseSpecification", e);
                }
            }
            filterValues = enumList;
        }
        predicate = expression.in(filterValues);
        return predicate;
    }

}
