package ru.example.department.service.core.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ru.example.department.repository.core.BaseRepository;
import ru.example.department.service.core.converter.DtoEntityConverter;
import ru.example.department.service.core.log.LogChanges;
import ru.example.department.util.CRUDException;

import javax.servlet.http.HttpSession;

@Slf4j
public class EntityCreateServiceBase<ENTITY, DTO ,ID> implements EntityCreateService<ENTITY, DTO> {

    private BaseRepository<ENTITY, ID> repository;
    private DtoEntityConverter<DTO, ENTITY> dtoEntityConverter;
    private LogChanges logChanges;
    private String entityName;

    private static final String USERNAME_PARAM_NAME = "username";
    private String DATABASE_FLUSH_ERROR = "Ошибка при сохранении изменений в базе";
    private String DATA_INTEGRITY_ERROR            = "Данные не удовлетворяют ограничению целостности.";
    private String CONFLICT_DATABASE_FLUSH_ERROR    = "Не удалось сохранить изменения из-за конфликта.";
    private String PARAM_UNIQUENESS_VIOLATION_ERROR = "Нарушено требование уникальности параметра при сохранении";


    public EntityCreateServiceBase(BaseRepository<ENTITY, ID> repository, DtoEntityConverter<DTO, ENTITY> dtoEntityConverter, String entityName) {
        this.repository = repository;
        this.dtoEntityConverter = dtoEntityConverter;
        this.entityName = entityName;
    }

    @Override
    public ENTITY createFromDto(DTO dto, HttpSession session) throws CRUDException {
        DTO createdDto;
        try {
            ENTITY entity = dtoEntityConverter.dtoToEntity(dto);
            ENTITY createdEntity = repository.saveAndFlush(entity);
            createdDto = dtoEntityConverter.entityToDto(createdEntity);
            logChanges.writeChangeLog(entityName, "Создание", (String) session.getAttribute(USERNAME_PARAM_NAME), null,  createdDto);
            return createdEntity;
        } catch (Exception e) {
            try {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } catch (Exception e1) {
                log.error(e1.getMessage());
            }
            String message = DATABASE_FLUSH_ERROR;
            if (e.getMessage() != null && (e.getMessage().contains("unique") || e.getMessage().contains("constraint"))) {
                message = PARAM_UNIQUENESS_VIOLATION_ERROR;
            }
            throw new CRUDException(message);
        }
    }



    @Autowired
    public void setLogChanges(LogChanges logChanges) {
        this.logChanges = logChanges;
    }

}
