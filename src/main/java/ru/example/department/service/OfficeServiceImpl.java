package ru.example.department.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.department.model.entity.DepartmentEntity;
import ru.example.department.model.entity.OfficeEntity;
import ru.example.department.repository.DepartmentRepository;
import ru.example.department.repository.OfficeRepository;
import ru.example.department.util.NoEntryException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service("officeService")
@Transactional
public class OfficeServiceImpl implements OfficeService {

    private OfficeRepository officeRepository;
    private DepartmentRepository departmentRepository;

    public OfficeServiceImpl(OfficeRepository officeRepository, DepartmentRepository departmentRepository) {
        this.officeRepository = officeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public OfficeEntity findById(UUID id) throws NoEntryException {
        return officeRepository
                .findById(id)
                .orElseThrow(() -> new NoEntryException(id));
    }

    @Override
    public List<OfficeEntity> findAllOffices() {
        return officeRepository.findAll();
    }

    @Override
    public void saveOffice(OfficeEntity office) {
        officeRepository.save(office);
    }

    @Override
    public void updateOffice(OfficeEntity office, OfficeEntity currentOffice) {
        currentOffice.setAddress(office.getAddress());
        currentOffice.setCategory(office.getCategory());
        currentOffice.setCity(office.getCity());
        currentOffice.setPropertyType(office.getPropertyType());
        currentOffice.setValue(office.getValue());

        Set<DepartmentEntity> departments = new HashSet<>();
        for (DepartmentEntity department : office.getDepartments()) {
            try {
                departments.add(departmentRepository.findById(department.getId())
                        .orElseThrow(() -> new NoEntryException(department.getId())));
            } catch (NoEntryException e) {
                log.error(e.getErrorMessageEng());
            }
        }

        currentOffice.setDepartments(departments);

        saveOffice(currentOffice);
    }

    @Override
    public void deleteOfficeById(UUID id) throws NoEntryException {
        OfficeEntity office = officeRepository
                .findById(id)
                .orElseThrow(() -> new NoEntryException());
        officeRepository.delete(office);
    }

    @Override
    public void deleteAllOffice() {
        officeRepository.deleteAll();
    }
}
