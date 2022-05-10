package rest;
import model.Performance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.interfaces.RepositoryInterfacePerformance;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/music-festival/performances")
public class PerformanceController {

    @Autowired
    private RepositoryInterfacePerformance repositoryPerformance;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Performance performance){
        Optional<Performance> optionalPerformance = repositoryPerformance.save(performance);
        if(optionalPerformance.isPresent())
            return new ResponseEntity<Performance>(performance, HttpStatus.OK);
        else return new ResponseEntity<String>("The performance cannot be added!", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Performance performance){
        if(repositoryPerformance.find(id).isEmpty())
            return new ResponseEntity<String>("Performance not found!", HttpStatus.BAD_REQUEST);
        performance.setID(id);
        Optional<Performance> optionalPerformance = repositoryPerformance.update(performance);
        if(optionalPerformance.isPresent())
            return new ResponseEntity<Performance>(performance, HttpStatus.OK);
        else return new ResponseEntity<String>("The performance cannot be modified!", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        System.out.println("Deleting performance... " + id);
        Optional<Performance> optionalPerformance = repositoryPerformance.find(id);
        if(optionalPerformance.isPresent()) {
            repositoryPerformance.delete(optionalPerformance.get());
            return new ResponseEntity<Performance>(HttpStatus.OK);
        }
        return new ResponseEntity<String>("Performance not found!", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Long id){
        System.out.println("Getting by ID: " + id);
        Optional<Performance> optionalPerformance = repositoryPerformance.find(id);
        if(optionalPerformance.isPresent())
            return new ResponseEntity<Performance>(optionalPerformance.get(), HttpStatus.OK);
        return new ResponseEntity<String>("Performance not found!", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Performance[] getAll(){
        System.out.println("Getting all performances ...");
        List<Performance> performancesList = repositoryPerformance.findAll();
        return performancesList.toArray(new Performance[0]);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {
        return e.getMessage();
    }
}
