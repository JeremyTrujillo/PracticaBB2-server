package com.example.practicabitboxer2.repositories;

import com.example.practicabitboxer2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
