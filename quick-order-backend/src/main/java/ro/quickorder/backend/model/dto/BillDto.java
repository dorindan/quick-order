package ro.quickorder.backend.model.dto;

import java.math.BigDecimal;

/**
 *  Data transfer object for {@link ro.quickorder.backend.model.Bill}
 *
 *  *@author R. Lupoaie
 */
public class BillDto {

    private boolean voucher;
    private int salePercentage;
    private BigDecimal total;

    public BillDto() {

    }

    public BillDto(boolean voucher, int salePercentage, BigDecimal total) {
        this.voucher = voucher;
        this.salePercentage = salePercentage;
        this.total = total;
    }

    public boolean hasVoucher() {
        return voucher;
    }

    public void setVoucher(boolean voucher) {
        this.voucher = voucher;
    }

    public int getSalePercentage() {
        return salePercentage;
    }

    public void setSalePercentage(int salePercentage) {
        this.salePercentage = salePercentage;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
