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
@RequestMapping(value = "/bill")
public class BillResource {

}
