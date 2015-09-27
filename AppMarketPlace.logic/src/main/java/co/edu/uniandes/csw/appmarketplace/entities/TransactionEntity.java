/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.appmarketplace.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ac.rojas13
 */
@Entity
@Table(name = "transactionentity")
@NamedQueries({
    @NamedQuery(name = "TransactionEntity.findAll", query = "SELECT t FROM TransactionEntity t"),
    @NamedQuery(name = "TransactionEntity.findById", query = "SELECT t FROM TransactionEntity t WHERE t.id = :id"),
    @NamedQuery(name = "TransactionEntity.findByApp", query = "SELECT count(t) FROM TransactionEntity t WHERE t.recipient.id = :app_id"),
    @NamedQuery(name = "TransactionEntity.findByTotal", query = "SELECT t FROM TransactionEntity t WHERE t.total = :total"),
    @NamedQuery(name = "TransactionEntity.countByClient", query = "SELECT count(t) FROM TransactionEntity t WHERE t.payer.id = :payer_id AND t.recipient.id = :app_id"),
    @NamedQuery(name = "TransactionEntity.findByStatus", query = "SELECT t FROM TransactionEntity t WHERE t.status = :status")})
public class TransactionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(generator = "Transaction")
    private Long id;
    @JoinColumn(name = "payer", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClientEntity payer;
    @JoinColumn(name = "recipient", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AppEntity recipient;    
    @Basic(optional = false)
    @NotNull
    @Column(name = "total")
    private int total;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "payment_card", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PaymentCardEntity paymentCard;

    public TransactionEntity() {
    }

    public TransactionEntity(Long id) {
        this.id = id;
    }

    public TransactionEntity(Long id, int total, String status) {
        this.id = id;
        this.total = total;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaymentCardEntity getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCardEntity paymentCard) {
        this.paymentCard = paymentCard;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionEntity)) {
            return false;
        }
        TransactionEntity other = (TransactionEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.uniandes.csw.appmarketplace.entities.TransactionEntity[ id=" + id + " ]";
    }

    /**
     * @return the payer
     */
    public ClientEntity getPayer() {
        return payer;
    }

    /**
     * @param payer the payer to set
     */
    public void setPayer(ClientEntity payer) {
        this.payer = payer;
    }

    /**
     * @return the recipient
     */
    public AppEntity getRecipient() {
        return recipient;
    }

    /**
     * @param recipient the recipient to set
     */
    public void setRecipient(AppEntity recipient) {
        this.recipient = recipient;
    }
    
}
