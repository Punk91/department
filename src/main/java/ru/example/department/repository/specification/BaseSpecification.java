package ru.example.department.repository.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import ru.example.department.model.core.Filter;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @since 19.09.2014
 */
public class BaseSpecification<ENTITY_TYPE> implements Specification<ENTITY_TYPE> {

    protected static final ThreadLocal<SimpleDateFormat> simpleDateFormatHolder = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            return dateFormat;
        }
    };
    protected static final ThreadLocal<SimpleDateFormat> simpleDateTimeFormatHolder = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            return dateFormat;
        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseSpecification.class);

    private List<Filter> filterList;

    public BaseSpecification(List<Filter> filterList) {
        this.filterList = filterList;
    }

    protected Predicate generateCustomPredicate(Filter filter, Root<ENTITY_TYPE> root, CriteriaBuilder criteriaBuilder) {
        return null;
    }

    @Override
    public Predicate toPredicate(Root<ENTITY_TYPE> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = null;
        List<Predicate> predicates = new LinkedList<>();
        for (Filter filter : filterList) {

            Predicate customPredicate = generateCustomPredicate(filter, root, criteriaBuilder);
            if (customPredicate != null) {
                predicate = customPredicate;
            } else {
                if (filter != null && filter.getType() != null) {
                    switch (filter.getType()) {
                        case "numeric":
                            switch (filter.getComparison()) {
                                case "gt":
                                    predicate = criteriaBuilder.greaterThan(root.<Long>get(filter.getField()), Long.parseLong(filter.getValue().toString()));
                                    break;
                                case "lt":
                                    predicate = criteriaBuilder.lessThan(root.<Long>get(filter.getField()), Long.parseLong(filter.getValue().toString()));
                                    break;
                                case "eq":
                                    predicate = criteriaBuilder.equal(root.get(filter.getField()), filter.getValue());
                                    break;
                                default:
                                    predicate = null;
                            }
                            break;
                        case "string":
                            if (filter.getField().equals("id")) {
                                Expression<String> filterKeyExp = root.get(filter.getField()).as(String.class);
                                filterKeyExp = criteriaBuilder.lower(filterKeyExp);
                                predicate = criteriaBuilder.like(filterKeyExp, "%" + filter.getValue().toString().trim() + "%");
                            } else {
                                predicate = criteriaBuilder.like(root.<String>get(filter.getField()), "%" + filter.getValue() + "%");
                            }

                            break;
                        case "date":
                            try {
                                Date dateValue = simpleDateFormatHolder.get().parse((String) filter.getValue());

                                switch (filter.getComparison()) {

                                    case "gt":
                                        predicate = criteriaBuilder.greaterThan(root.<Date>get(filter.getField()), dateValue);
                                        break;
                                    case "lt":
                                        predicate = criteriaBuilder.lessThan(root.<Date>get(filter.getField()), dateValue);
                                        break;
                                    case "eq":
                                        predicate = criteriaBuilder.between(root.<Date>get(filter.getField()), dateValue, new Date(dateValue.getTime()+24*60*60*1000));
                                        break;
                                    default:
                                        predicate = null;
                                }
                            } catch (ParseException e) {
                                LOGGER.error("Error in BaseSpecification", e);
                            }
                            break;
                        case "datetime":
                            try {
                                Date dateValue = simpleDateTimeFormatHolder.get().parse((String) filter.getValue());

                                switch (filter.getComparison()) {

                                    case "gt":
                                        predicate = criteriaBuilder.greaterThan(root.<Date>get(filter.getField()), dateValue);
                                        break;
                                    case "lt":
                                        predicate = criteriaBuilder.lessThan(root.<Date>get(filter.getField()), dateValue);
                                        break;
                                    case "eq":
                                        predicate = criteriaBuilder.between(root.<Date>get(filter.getField()), dateValue, new Date(dateValue.getTime()+24*60*60*1000));
                                        break;
                                    default:
                                        predicate = null;
                                }
                            } catch (ParseException e) {
                                LOGGER.error("Error in BaseSpecification", e);
                            }
                            break;
                        case "list":
                            List<Object> filterValues = (List<Object>) filter.getValue();
                            Expression exp = root.get(filter.getField());
                            if (exp.getJavaType().isEnum()) {
                                List enumList = new ArrayList<>();
                                for (Object value : filterValues) {
                                    try {
                                        Object obj = exp.getJavaType().getDeclaredMethod("valueOf", String.class).invoke(null, value);
                                        enumList.add(obj);
                                    } catch (Exception e) {
                                        LOGGER.error("Error in BaseSpecification", e);
                                    }
                                }
                                filterValues = enumList;
                            }
                            predicate = exp.in(filterValues);
                            break;
                        case "boolean":
                            predicate = criteriaBuilder.equal(root.<Boolean>get(filter.getField()), filter.getValue());
                            break;
                        default:
                            LOGGER.warn("Unable to create predicate for filter type {}, field {}.", filter.getType(), filter.getField());
                    }
                }

                if (predicate != null)
                    predicates.add(predicate);
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
