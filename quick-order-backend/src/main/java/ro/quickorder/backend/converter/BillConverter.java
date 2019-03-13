package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Bill;
import ro.quickorder.backend.model.dto.BillDto;

@Component
public class BillConverter {

    public Bill toBill(BillDto billDto) {
        if (billDto == null)
            return null;
        Bill bill = new Bill();
        bill.setVoucher(billDto.hasVoucher());
        bill.setSalePercentage(billDto.getSalePercentage());
        bill.setTotal(billDto.getTotal());
        return bill;
    }

    public BillDto toBillDto(Bill bill) {
        if (bill == null)
            return null;
        BillDto billDto = new BillDto();
        billDto.setVoucher(bill.hasVoucher());
        billDto.setSalePercentage(bill.getSalePercentage());
        billDto.setTotal(bill.getTotal());
        return billDto;
    }

}
