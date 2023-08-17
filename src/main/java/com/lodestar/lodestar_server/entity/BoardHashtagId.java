package com.lodestar.lodestar_server.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BoardHashtagId implements Serializable {

    private Long boardId;

    @Column(name = "hashtag_name")
    private String hashtagName;
}
