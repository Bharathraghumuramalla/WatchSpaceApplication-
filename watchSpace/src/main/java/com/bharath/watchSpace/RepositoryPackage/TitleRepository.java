package com.bharath.watchSpace.RepositoryPackage;

import com.bharath.watchSpace.Entities.Title;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Integer> {
}
