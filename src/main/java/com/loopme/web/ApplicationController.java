package com.loopme.web;

import com.loopme.model.*;
import com.loopme.service.AppService;
import com.loopme.service.UserService;
import com.loopme.exceptions.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author Igor Holiak.
 */
@RestController
@RequestMapping(value = "/applications")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationController {

    private final AppService appService;
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ADOPS', 'PUBLISHER')")
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getAll() {
        return ResponseEntity.ok(appService.findAll());
    }

    @PreAuthorize("hasAnyAuthority('ADOPS', 'PUBLISHER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> find(@PathVariable("id") final Integer id) {
        App app = appService.findOne(id);
        return ResponseEntity.ok(app);
    }

    @PreAuthorize("hasAnyAuthority('ADOPS', 'PUBLISHER')")
    @RequestMapping(value = "/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> getAppTypes() {
        return ResponseEntity.ok(AppType.values());
    }

    @PreAuthorize("hasAnyAuthority('ADOPS', 'PUBLISHER')")
    @RequestMapping(value = "/content-types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> getContentTypes() {
        return ResponseEntity.ok(ContentType.values());
    }

    @PreAuthorize("hasAnyAuthority('ADOPS', 'PUBLISHER')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> create(@RequestBody @Valid final App app, final BindingResult bindingResult,
                                    final Principal principal) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(bindingResult.getFieldError().getDefaultMessage());
        }
        User current = userService.findOneByEmail(principal.getName()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("username %s not found", principal.getName())));
        if (current.getRole() == UserRole.PUBLISHER) {
            current.setApp(app);
            userService.save(current);
        } else if (current.getRole() == UserRole.ADOPS && app.getUser().getEmail() != null) {
            User publisher = userService.findOneByEmail(app.getUser().getEmail()).orElseThrow(() ->
                    new UsernameNotFoundException(String.format("username %s not found", principal.getName())));
            publisher.setApp(app);
            userService.save(publisher);
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ADOPS', 'PUBLISHER')")
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<?> edit(@RequestBody @Valid final App updated, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(bindingResult.getFieldError().getDefaultMessage());
        }
        appService.saveOrUpdate(updated);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ADOPS', 'PUBLISHER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") final Integer id) {
        appService.delete(id);
        return ResponseEntity.ok().build();
    }
}
