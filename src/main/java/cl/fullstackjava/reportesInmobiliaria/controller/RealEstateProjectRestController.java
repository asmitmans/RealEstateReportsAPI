package cl.fullstackjava.reportesInmobiliaria.controller;

import cl.fullstackjava.reportesInmobiliaria.model.entities.RealEstateProject;
import cl.fullstackjava.reportesInmobiliaria.model.service.RealEstateProjectService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class RealEstateProjectRestController {
    private final RealEstateProjectService realEstateProjectService;

    public RealEstateProjectRestController(RealEstateProjectService realEstateProjectService) {
        this.realEstateProjectService = realEstateProjectService;
    }

    @GetMapping
    public List<RealEstateProject> getAllProjects() {
        return realEstateProjectService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<RealEstateProject> getProjectById(@PathVariable int id) {
        try {
            RealEstateProject project = realEstateProjectService.findOne(id);
            return ResponseEntity.ok(project);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<RealEstateProject> createProject(@RequestBody RealEstateProject r) {
        try {
            RealEstateProject createdProject = realEstateProjectService.create(r);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstateProject> updateProject(@PathVariable int id, @RequestBody RealEstateProject r) {
        try {
            r.setId(id);
            RealEstateProject updatedProject = realEstateProjectService.update(r);
            return ResponseEntity.ok(updatedProject);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable int id) {
        try {
            realEstateProjectService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}