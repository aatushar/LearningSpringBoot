package com.khajana.setting.entity.currency;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "2F4_sv_currency_exc_rate")
public class CurrencyExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "currency_info_id", nullable = false, updatable = true)
    private Long currencyInfoId;

    // @ManyToOne(optional=false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_info_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Currency currency;

    @Column(name = "exch_rate_date", nullable = false, updatable = true)
    private Date exchangeRateDate;

    @Column(name = "source", nullable = false, updatable = true)
    private String source;

    @Column(name = "exch_rate", nullable = false, updatable = true)
    private String exchangeRate;

    @Column(name = "is_active", nullable = false, updatable = true)
    private Boolean active;

    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true, updatable = true)
    private Date updatedAt;

    @Column(name = "created_by", nullable = true, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = true, updatable = true)
    private Long updatedBy;

}
