package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Bill;
import ro.quickorder.backend.model.dto.BillDto;

@Component
public class BillConverter {

    public Bill convertBillDtoToBill(BillDto billDto){
        Bill bill= new Bill();
        bill.setVoucher(billDto.hasVoucher());
        bill.setSalePercentage(billDto.getSalePercentage());
        bill.setTotal(billDto.getTotal());
        return bill;
    }

    public BillDto convertBillDtoToBill(Bill bill){
        BillDto billDto= new BillDto();
        billDto.setVoucher(bill.hasVoucher());
        billDto.setSalePercentage(bill.getSalePercentage());
        billDto.setTotal(bill.getTotal());
        return billDto;
    }


}
