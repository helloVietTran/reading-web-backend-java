package com.viettran.reading_story_web.scheduler;

import com.viettran.reading_story_web.entity.mysql.Inventory;
import com.viettran.reading_story_web.repository.InventoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryJobScheduler {
    InventoryRepository inventoryRepository;

    // dọn dẹp bản ghi
    @Scheduled(cron = "0 0 2 * * *")
    public void cleanExpiredInventories() {
        List<Inventory> expiredInventories = inventoryRepository.findByExpirationDateBefore(Instant.now());
        if (!expiredInventories.isEmpty()) {
            inventoryRepository.deleteAll(expiredInventories);
        }
    }
}