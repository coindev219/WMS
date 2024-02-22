package com.warehouse.administration.applicationconfiguration;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "default_config_parameters", schema = "public")
public class DefaultConfigParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "config_key", unique = true)
    private String configKey;

    @NotNull
    @Column(name = "config_value")
    private String configValue;

    @Column(name = "is_primary_company_config")
    private Boolean isPrimaryCompanyConfig = false;

    @Column(name = "is_warehouse_config")
    private Boolean isWarehouseConfig = false;

    @Column(name = "is_organization_config")
    private Boolean isOrganizationConfig = false;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private Timestamp modifiedAt;

    public DefaultConfigParameters() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public Boolean getIsPrimaryCompanyConfig() {
        return isPrimaryCompanyConfig;
    }

    public void setIsPrimaryCompanyConfig(Boolean isPrimaryCompanyConfig) {
        this.isPrimaryCompanyConfig = isPrimaryCompanyConfig;
    }

    public Boolean getIsWarehouseConfig() {
        return isWarehouseConfig;
    }

    public void setIsWarehouseConfig(Boolean isWarehouseConfig) {
        this.isWarehouseConfig = isWarehouseConfig;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return "DefaultConfigParameters [configKey=" + configKey + ", configValue=" + configValue + ", createdAt="
                + createdAt + ", id=" + id + ", isPrimaryCompanyConfig=" + isPrimaryCompanyConfig
                + ", isWarehouseConfig=" + isWarehouseConfig + ", modifiedAt=" + modifiedAt + "]";
    }

}
