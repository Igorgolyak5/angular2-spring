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

import static java.util.stream.Collectors.toList;

/**
 * @author Igor Holiak.
 */
@RestController
@RequestMapping(value = "/publishers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublisherController {

    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADOPS')")
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.findByRole(UserRole.PUBLISHER));
    }

    @PreAuthorize("hasAnyAuthority('PUBLISHER', 'ADOPS')")
    @RequestMapping(value = "/free/", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getFreePublisher() {
        return ResponseEntity.ok(userService.findFreePublisher().stream().map(user -> user.getEmail()).collect(toList()));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADOPS')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> find(@PathVariable("id") final Integer id) {
        User publisher = userService.findOne(id);
        return ResponseEntity.ok(publisher);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADOPS')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> create(@RequestBody @Valid final User user, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(bindingResult.getFieldError().getDefaultMessage());
        }
        user.setRole(UserRole.PUBLISHER);
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADOPS')")
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> edit(@RequestBody @Valid final User updated, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(bindingResult.getFieldError().getDefaultMessage());
        }
        userService.update(updated);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADOPS')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") final Integer id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
