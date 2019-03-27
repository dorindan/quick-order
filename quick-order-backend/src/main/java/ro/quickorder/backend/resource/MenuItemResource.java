package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.repository.BillRepository;
import ro.quickorder.backend.repository.MenuItemRepository;

import javax.ws.rs.PathParam;
import java.util.List;


@RestController
@RequestMapping(value = "/menuItem")
public class MenuItemResource {

}
