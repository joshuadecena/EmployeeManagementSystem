package com.capstonetwo.ems.service;

import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.repository.DepartmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
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
		String msg = messageSource.getMessage("department.notfound", new Object[]{id}, LocaleContextHolder.getLocale());
		return departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(msg));
	}

	@Override
	public Department save(Department department) {
		String msg = messageSource.getMessage("department.saved", new Object[]{department.getName()}, LocaleContextHolder.getLocale());
		return departmentRepository.save(department);
	}

	@Override
	public Department update(Long id, Department department) {
		String msg = messageSource.getMessage("department.notfound", new Object[]{id}, LocaleContextHolder.getLocale());
		Department existing = departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(msg));
		existing.setName(department.getName());
		return departmentRepository.save(existing);
	}

	@Override
	public void delete(Long id) {
		departmentRepository.deleteById(id);
	}
}
