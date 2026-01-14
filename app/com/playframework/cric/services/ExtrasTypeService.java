package com.playframework.cric.services;

import com.google.inject.Inject;

import com.playframework.cric.repositories.ExtrasTypeRepository;
import com.playframework.cric.models.ExtrasType;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ExtrasTypeService {
    private final ExtrasTypeRepository extrasTypeRepository;

    @Inject
    public ExtrasTypeService(ExtrasTypeRepository extrasTypeRepository) {
        this.extrasTypeRepository = extrasTypeRepository;
    }

    public List<ExtrasType> getAll() {
        return extrasTypeRepository.getAll();
    }

    public List<ExtrasType> getAll(EntityManager em) {
        return extrasTypeRepository.getAll(em);
    }
}