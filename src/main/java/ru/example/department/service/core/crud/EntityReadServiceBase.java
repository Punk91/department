package ru.example.department.service.core.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.example.department.model.core.dto.Filter;
import ru.example.department.model.core.dto.QueryResult;
import ru.example.department.model.core.dto.ReadRequest;
import ru.example.department.repository.core.BaseRepository;

import java.util.List;

public class EntityReadServiceBase<ENTITY, ID>  implements EntityReadService<ENTITY, ID>  {

    private BaseRepository<ENTITY, ID> repository;

    public EntityReadServiceBase(BaseRepository<ENTITY, ID> repository) {
        this.repository = repository;
    }

    @Override
    public QueryResult<ENTITY> findAllByParams(ReadRequest readRequest) {
        PageRequest pageRequest = preparePageRequest(readRequest);
        Sort sort = (pageRequest == null ? null : pageRequest.getSort());

        BaseSpecification<ENTITY> baseSpecification = null;
        List<Filter> filterList = readRequest.getFilterList();
        if (filterList != null) {
            baseSpecification = new BaseSpecification<>(filterList);
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

    private PageRequest preparePageRequest(ReadRequest readRequest) {
        PageRequest pageRequest;
        Sort sort = null;

        if (readRequest.getSortData() != null) {
            sort = new Sort(Sort.Direction.valueOf(readRequest.getSortData().getDirection()), readRequest.getSortData().getProperty());
        }

        if (sort != null) {
            pageRequest = PageRequest.of(readRequest.getPage() - 1, (readRequest.getSize() < 0 ? 1 : readRequest.getSize()), sort);
        } else {
            pageRequest = PageRequest.of(readRequest.getPage() - 1, (readRequest.getSize() < 0 ? 1 : readRequest.getSize()));
        }
        return pageRequest;
    }

    @Override
    public List<ENTITY> findAll() {
        return null;
    }

    @Override
    public ENTITY findById(ID id) {
        return repository.getOne(id);
    }
}
