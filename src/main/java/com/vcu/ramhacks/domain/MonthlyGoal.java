package com.vcu.ramhacks.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MonthlyGoal.
 */
@Entity
@Table(name = "monthly_goal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MonthlyGoal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="categories")
    private String [] categoriesChosen;

    @Column(name = "month")
    private String month;

    @Column(name = "percentage")
    private Double percentage;

    @OneToMany(mappedBy = "monthlyGoal")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Category> categories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String [] getCat() {return categoriesChosen;}

    public void setCat(String [] categories){this.categoriesChosen = categories;}

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MonthlyGoal monthlyGoal = (MonthlyGoal) o;
        if(monthlyGoal.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, monthlyGoal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MonthlyGoal{" +
            "id=" + id +
            ", month='" + month + "'" +
            ", percentage='" + percentage + "'" +
            ", categories='" + categoriesChosen + "'" +
            '}';
    }
}
