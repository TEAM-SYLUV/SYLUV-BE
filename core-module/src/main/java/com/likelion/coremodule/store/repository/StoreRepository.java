package com.likelion.coremodule.store.repository;

import com.likelion.coremodule.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
