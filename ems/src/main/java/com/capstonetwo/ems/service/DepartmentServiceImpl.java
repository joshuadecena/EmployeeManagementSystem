package com.capstonetwo.ems.service;

import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.repository.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        messageSource.getMessage("department.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department update(Long id, Department department) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        messageSource.getMessage("department.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        existing.setName(department.getName());
        return departmentRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException(
                messageSource.getMessage("department.notfound", new Object[]{id}, LocaleContextHolder.getLocale())
            );
        }
        departmentRepository.deleteById(id);
    }
}
