package com.lodestar.lodestar_server.hashtag.repository;

import com.lodestar.lodestar_server.hashtag.entity.BoardHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<BoardHashtag, Long> {

}
