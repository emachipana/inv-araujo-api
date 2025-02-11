package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.AdvanceDTO;
import com.inversionesaraujo.api.business.service.IAdvance;
import com.inversionesaraujo.api.model.Advance;
import com.inversionesaraujo.api.repository.AdvanceRepository;

@Service
public class AdvanceImpl implements IAdvance {
	@Autowired
	private AdvanceRepository advanceRepo;

	@Transactional
	@Override
	public AdvanceDTO save(AdvanceDTO advance) {
		Advance advanceSaved = advanceRepo.save(AdvanceDTO.toEntity(advance));

		return AdvanceDTO.toDTO(advanceSaved);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		advanceRepo.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public AdvanceDTO findById(Long id) {
		Advance advance = advanceRepo.findById(id).orElseThrow(() -> new DataAccessException("El adelanto no existe") {});

		return AdvanceDTO.toDTO(advance);
	}
}
