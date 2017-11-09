package com.loopme.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Igor Holiak
 */
@Entity
@Table(name = "app")
@Getter
@Setter
public class App implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotEmpty(message = "name must not be empty")
    @Column(name = "name")
    private String name;

    @NotNull(message = "type must not be null")
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AppType type;

    @ElementCollection(targetClass = ContentType.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "app_content_types")
    @Column(name = "content_type") // Column name in app_content_types
    private Set<ContentType> contentTypes;

    @OneToOne(mappedBy = "app")
    private User user;
}
