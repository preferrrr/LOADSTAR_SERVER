package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.BoardHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface HashtagRepository extends JpaRepository<BoardHashtag, Long> {

}
