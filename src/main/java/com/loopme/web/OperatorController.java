package com.loopme.web;

import com.loopme.model.User;
import com.loopme.model.UserRole;
import com.loopme.service.UserService;
import com.loopme.exceptions.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Igor Holiak.
 */
@RestController
@RequestMapping(value = "/operators")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OperatorController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.findByRole(UserRole.ADOPS));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> find(@PathVariable("id") final Integer id) {
        User publisher = userService.findOne(id);
        return ResponseEntity.ok(publisher);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> create(@RequestBody @Valid final User user, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(bindingResult.getFieldError().getDefaultMessage());
        }
        user.setRole(UserRole.ADOPS);
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> edit(@RequestBody @Valid final User updated, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(bindingResult.getFieldError().getDefaultMessage());
        }
        userService.update(updated);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") final Integer id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
