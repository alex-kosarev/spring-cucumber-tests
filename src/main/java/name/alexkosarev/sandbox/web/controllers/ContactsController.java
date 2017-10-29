/*
 *  Copyright 2017 Alexander Kosarev
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package name.alexkosarev.sandbox.web.controllers;

import lombok.RequiredArgsConstructor;
import name.alexkosarev.sandbox.entities.Contact;
import name.alexkosarev.sandbox.repositories.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("contacts")
@RequiredArgsConstructor
public class ContactsController {

    private final ContactRepository repository;

    @GetMapping
    public ResponseEntity<List<Contact>> findAll() {
        return ResponseEntity.ok((List<Contact>) repository.findAll());
    }
}
