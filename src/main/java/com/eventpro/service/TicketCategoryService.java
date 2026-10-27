package com.eventpro.service;

import com.eventpro.model.tickets.TicketCategory;
import com.eventpro.repository.TicketCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketCategoryService {
    public record FindOrCreateTicketCategory(TicketCategory category, boolean exist) {}

    private final TicketCategoryRepository repository;
    private final TransactionTemplate transactions;

    @Transactional(readOnly = true)
    public List<TicketCategory> findAll() {
        return this.repository.findAll(Sort.by("name"));
    }

    public FindOrCreateTicketCategory findOrCreate(String unnormalizedName) {
        try {
            return transactions.execute(status -> this.lookupOrInsert(unnormalizedName));
        } catch (DataIntegrityViolationException raceLost) {
            return transactions.execute(status -> lookupOrInsert(unnormalizedName));
        }
    }

    private FindOrCreateTicketCategory lookupOrInsert(String unnormalizedName) {
        String name = TicketCategory.normalize(unnormalizedName);
        return this.repository.findByName(name)
                .map(category -> new FindOrCreateTicketCategory(category, true))
                .orElseGet(() -> new FindOrCreateTicketCategory(
                        this.repository.save(TicketCategory.builder().name(name).build()),
                        false
                        ));
    }
}
