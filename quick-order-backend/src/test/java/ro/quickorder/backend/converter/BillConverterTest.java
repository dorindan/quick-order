package ro.quickorder.backend.converter;

import org.junit.Test;
import ro.quickorder.backend.model.Bill;
import ro.quickorder.backend.model.dto.BillDto;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for {@link BillConverter}
 *
 * @author R. Lupoaie
 */
public class BillConverterTest {

    BillConverter billConverter = new BillConverter();

    @Test
    public void testConvertBillToDto() {
        Bill bill = new Bill(false, 20, new BigDecimal(20), null, null);

        BillDto billDto = billConverter.toBillDto(bill);

        assertEquals(bill.getSalePercentage(), billDto.getSalePercentage());
        assertEquals(bill.getTotal(), billDto.getTotal());
        assertEquals(bill.hasVoucher(), billDto.hasVoucher());
    }

    @Test
    public void testConvertBillToDtoWhenBillIsNull() {
        BillDto billDto = billConverter.toBillDto(null);

        assertNull(billDto);
    }

    @Test
    public void testConvertDtoToBill() {
        BillDto billDto = new BillDto(false, 20, new BigDecimal(20));

        Bill bill = billConverter.toBill(billDto);

        assertEquals(billDto.getSalePercentage(), bill.getSalePercentage());
        assertEquals(billDto.getTotal(), bill.getTotal());
        assertEquals(billDto.hasVoucher(), bill.hasVoucher());
        assertNull(bill.getCommand());
        assertNull(bill.getUser());
    }

    @Test
    public void testConvertDtoToBillWhenDtoIsNull() {
        Bill bill = billConverter.toBill(null);

        assertNull(bill);
    }
}
