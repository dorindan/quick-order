package ro.quickorder.backend.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.Bill;
import ro.quickorder.backend.repository.BillRepository;

import javax.ws.rs.Produces;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BillResource {
    @Autowired
    BillRepository billRepository;

    @RequestMapping(path = "/bill", method = RequestMethod.GET)
    @Produces("text/plain")
    public Optional<Bill> findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String valueAsString = objectMapper.writeValueAsString(billRepository.findById(id));
            System.out.println(valueAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return billRepository.findById(id);
    }

    @PostMapping(path = "/bill")
    public void createBill(@RequestBody Bill bill) {
        System.out.println(bill.getTotal());
    }
}
