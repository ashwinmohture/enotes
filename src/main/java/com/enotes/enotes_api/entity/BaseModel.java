package com.enotes.enotes_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {

    @CreatedBy
    @Column(updatable = false)
    private Integer createdBy;
    @CreatedDate
    @Column(updatable = false)
    private Date createdOn;
    @LastModifiedBy
    @Column(insertable = false)
    private Integer updateBy;
    @Column(insertable = true)
    @LastModifiedDate
    private Date updatedOn;

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                ", createdBy=" + createdBy +
                ", createdOn=" + createdOn +
                ", updateBy=" + updateBy +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
