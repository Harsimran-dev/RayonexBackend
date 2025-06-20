package com.healthbackend.services;

import com.healthbackend.entities.TreatmentPlan;
import com.healthbackend.entities.TreatmentPlanItem;
import com.healthbackend.repositories.TreatmentPlanItemRepository;
import com.healthbackend.repositories.TreatmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentPlanItemService {

    @Autowired
    private TreatmentPlanItemRepository itemRepository;


    public List<TreatmentPlanItem> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<TreatmentPlanItem> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public TreatmentPlanItem saveItem(TreatmentPlanItem item) {
        return itemRepository.save(item);
    }

    public List<TreatmentPlanItem> saveItems(List<TreatmentPlanItem> items) {
        return itemRepository.saveAll(items);
    }

  

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    
    public List<TreatmentPlanItem> getItemsByTreatmentPlanId(Long planId) {
        return itemRepository.findByTreatmentPlanId(planId);
    }

    

    
}
