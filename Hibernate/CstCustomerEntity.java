package com.mhh.hibernate.demo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cst_customer", schema = "hibernate", catalog ="")
public class CstCustomerEntity {
    private long custId;
    private String custName;
    private String custSource;
    private String custIndustry;
    private String custLevel;
    private String custPhone;
    private String custMobile;

    @Id
    @Column(name = "cust_id")
    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    @Basic
    @Column(name = "cust_name")
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    @Basic
    @Column(name = "cust_source")
    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    @Basic
    @Column(name = "cust_industry")
    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    @Basic
    @Column(name = "cust_level")
    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    @Basic
    @Column(name = "cust_phone")
    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    @Basic
    @Column(name = "cust_mobile")
    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CstCustomerEntity that = (CstCustomerEntity) o;
        return custId == that.custId &&
                Objects.equals(custName, that.custName) &&
                Objects.equals(custSource, that.custSource) &&
                Objects.equals(custIndustry, that.custIndustry) &&
                Objects.equals(custLevel, that.custLevel) &&
                Objects.equals(custPhone, that.custPhone) &&
                Objects.equals(custMobile, that.custMobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(custId, custName, custSource, custIndustry, custLevel, custPhone, custMobile);
    }
}
