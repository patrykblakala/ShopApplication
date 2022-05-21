package com.patryk.shop.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Data@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Table(indexes = @Index(name = "idx_name", columnList = "name", unique = true))
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @Lob
    private String body;

    private String name;

    @Version
    private Long version;

}
