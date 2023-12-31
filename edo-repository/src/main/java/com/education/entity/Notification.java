package com.education.entity;

import com.education.model.enumEntity.EnumNotification;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Хафизов Ильмир
 * Класс, описывающий оповещения пользователя
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
@Table(name = "notification")
public class Notification extends BaseEntity {

    /**
     * Id работника, для которого уведомление
     */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Employee.class)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    /**
     * Тип оповещения
     */
    @Column(name = "notification_name")
    @Enumerated(EnumType.STRING)
    private EnumNotification enumNotification;
}
