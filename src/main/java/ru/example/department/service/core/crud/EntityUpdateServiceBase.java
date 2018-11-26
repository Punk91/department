package ru.example.department.service.core.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ru.example.department.repository.core.BaseRepository;
import ru.example.department.service.core.converter.DtoEntityConverter;
import ru.example.department.service.core.log.LogChanges;
import ru.example.department.util.CRUDException;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpSession;
import java.util.Collections;

@Slf4j
public class EntityUpdateServiceBase<ENTITY, DTO, ID> implements EntityUpdateService<ENTITY, DTO, ID> {

    private BaseRepository<ENTITY, ID> repository;
    private DtoEntityConverter<DTO, ENTITY> dtoEntityConverter;
    private LogChanges logChanges;
    private String entityName;

    private static final String USERNAME_PARAM_NAME = "username";
    private String DATA_INTEGRITY_ERROR            = "Данные не удовлетворяют ограничению целостности.";
    private String CONFLICT_DATABASE_FLUSH_ERROR    = "Не удалось сохранить изменения из-за конфликта.";

    public EntityUpdateServiceBase(BaseRepository<ENTITY, ID> repository, DtoEntityConverter<DTO, ENTITY> dtoEntityConverter, LogChanges logChanges, String entityName) {
        this.repository = repository;
        this.dtoEntityConverter = dtoEntityConverter;
        this.logChanges = logChanges;
        this.entityName = entityName;
    }

    @Override
    public DTO updateFromDto(DTO dto, ID id, HttpSession session) throws CRUDException {
        ENTITY oldEntity = repository.getOne(id);
        DTO oldDto = dtoEntityConverter.entityToDto(oldEntity);
        ENTITY entity = dtoEntityConverter.updateEntityFromDto(dto, oldEntity);
        ENTITY updatedEntity;
        try {
            updatedEntity = repository.saveAndFlush(entity);
        } catch (OptimisticLockException | OptimisticLockingFailureException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new CRUDException(CONFLICT_DATABASE_FLUSH_ERROR);
        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new CRUDException(DATA_INTEGRITY_ERROR);
        }
        DTO updatedDto = dtoEntityConverter.entityToDto(updatedEntity);
        logChanges.writeChangeLog(entityName, "Изменение", (String) session.getAttribute(USERNAME_PARAM_NAME), oldDto, updatedDto);
        return updatedDto;
    }
}
