package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
