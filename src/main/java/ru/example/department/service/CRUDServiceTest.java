package ru.example.department.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.example.department.model.dto.base.Filter;
import ru.example.department.model.dto.base.ReadRequest;
import ru.example.department.model.dto.base.SortData;
import ru.example.department.model.dto.base.QueryResult;
import ru.example.department.repository.BaseRepository;
import ru.example.department.repository.specification.BaseSpecification;

import java.util.LinkedList;
import java.util.List;

public interface CRUDServiceTest<ENTITY, ID> {


    default QueryResult<ENTITY> getByParams(ReadRequest readRequest, BaseRepository<ENTITY, ID> repository) {

        PageRequest pageRequest = composePageRequest(readRequest);
        Sort sort = (pageRequest == null ? null : pageRequest.getSort());

        BaseSpecification<ENTITY> baseSpecification = null;
        List<Filter> filterList = readRequest.getFilterList();
        if (filterList != null) {
            baseSpecification = composeBaseSpecification(filterList);
        }

        Page<ENTITY> onePageList = null;
        List<ENTITY> content = null;
        if (baseSpecification != null) {
            if (readRequest.getSize() < 0) {
                if (sort != null) {
                    content = repository.findAll(baseSpecification, sort);
                } else {
                    content = repository.findAll(baseSpecification);
                }
            } else {
                onePageList = repository.findAll(baseSpecification, pageRequest);
            }
        } else {
            if (readRequest.getSize() < 0) {
                if (sort != null) {
                    content = repository.findAll(sort);
                } else {
                    content = repository.findAll();
                }
            } else {
                onePageList = repository.findAll(pageRequest);
            }
        }

        long totalCount = (onePageList != null ? onePageList.getTotalElements() : -1);
        if(totalCount == -1) {
            if (baseSpecification != null) {
                totalCount = repository.count(baseSpecification);
            } else {
                totalCount = repository.count();
            }
        }

        return new QueryResult<>(totalCount, onePageList == null ? content : onePageList.getContent());
    }

    default BaseSpecification<ENTITY> composeBaseSpecification(List<Filter> filterList) {
        return new BaseSpecification<>(filterList);
    }

    default PageRequest composePageRequest(ReadRequest readRequest) {
        PageRequest pageRequest;
        Sort sort = null;
        if (readRequest.getSortList() != null) {
            List<Sort.Order> orderList = new LinkedList<>();
            for (SortData sortData : readRequest.getSortList()) {
                orderList.add(new Sort.Order(Sort.Direction.valueOf(sortData.getDirection()), sortData.getProperty()));
            }
            sort = new Sort(orderList);
        }

        // set dummy page size = 1, it won't be used, but sort is needed
        if (sort != null) {
            pageRequest = new PageRequest(readRequest.getPage() - 1, (readRequest.getSize() < 0 ? 1 : readRequest.getSize()), sort);
        } else {
            pageRequest = new PageRequest(readRequest.getPage() - 1, (readRequest.getSize() < 0 ? 1 : readRequest.getSize()));
        }
        return pageRequest;
    }

}
