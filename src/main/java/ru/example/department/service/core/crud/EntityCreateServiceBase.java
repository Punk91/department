package ru.example.department.service.core.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ru.example.department.repository.core.BaseRepository;
import ru.example.department.service.core.converter.DtoEntityConverter;
import ru.example.department.service.core.log.LogChanges;

import javax.servlet.http.HttpSession;

@Slf4j
public class EntityCreateServiceBase<ENTITY, DTO ,ID> implements EntityCreateService<ENTITY, DTO> {

    private BaseRepository<ENTITY, ID> repository;
    private DtoEntityConverter<DTO, ENTITY> dtoEntityConverter;
    private LogChanges logChanges;
    private String entityName;

    private static final String USERNAME_PARAM_NAME = "username";
    private String databaseFlushError            = "Ошибка при сохранении изменений в базе";
    private String dataIntegrityError            = "Данные не удовлетворяют ограничению целостности.";
    private String conflictDatabaseFlushError    = "Не удалось сохранить изменения из-за конфликта.";
    private String paramUniquenessViolationError = "Нарушено требование уникальности параметра при сохранении";


    public EntityCreateServiceBase(BaseRepository<ENTITY, ID> repository, DtoEntityConverter<DTO, ENTITY> dtoEntityConverter, String entityName) {
        this.repository = repository;
        this.dtoEntityConverter = dtoEntityConverter;
        this.entityName = entityName;
    }

    @Override
    public ENTITY create(DTO dto, HttpSession session) {
        DTO createdDto;
        try {
            ENTITY entity = dtoEntityConverter.dtoToEntity(dto);
            ENTITY createdEntity = repository.saveAndFlush(entity);
            createdDto = dtoEntityConverter.entityToDto(createdEntity);
            logChanges.writeChangeLog(entityName, "Создание", (String) session.getAttribute(USERNAME_PARAM_NAME), null,  createdDto);
        } catch (Exception e) {
            try {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } catch (Exception e1) {
                log.error(e1.getMessage());
            }
            String message = databaseFlushError;
            if (e.getMessage() != null && (e.getMessage().contains("unique") || e.getMessage().contains("constraint"))) {
                message = paramUniquenessViolationError;
            }
        }

        return null;
    }



    @Autowired
    public void setLogChanges(LogChanges logChanges) {
        this.logChanges = logChanges;
    }

}
