package ru.example.department.service;

import ru.example.department.model.OfficeEntity;
import ru.example.department.util.NoEntryException;

import java.util.List;
import java.util.UUID;

public interface OfficeService {

    OfficeEntity findById(UUID id) throws NoEntryException;

    List<OfficeEntity> findAllOffices();

    void saveOffice(OfficeEntity office);

    void updateOffice(OfficeEntity office, OfficeEntity currentOffice);

    void deleteOfficeById(UUID id) throws NoEntryException;

    void deleteAllOffice();



}
